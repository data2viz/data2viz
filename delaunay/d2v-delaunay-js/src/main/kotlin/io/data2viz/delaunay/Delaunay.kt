@file:Suppress("NOTHING_TO_INLINE")

package io.data2viz.delaunay

import org.khronos.webgl.Int32Array
import org.khronos.webgl.Uint32Array
import org.khronos.webgl.get
import org.khronos.webgl.set
import kotlin.math.*


data class Point(val x: Double, val y: Double)


@JsName("delaunator")
fun delaunautor(points: Array<Array<Double>>) = Delaunator(points)

class Delaunator(points: Array<Array<Double>>) {

    val getX = { point: Array<Double> -> point[0] }
    val getY = { point: Array<Double> -> point[1] }

    private val coords:Array<Double> = emptyArray()

    private val _cx: Double
    private var _cy: Double

    /**
     * ceil(sqrt(points.size))
     */
    private val _hashSize: Int

    var hull: Node

    private val _hash: Array<Node?>

    var triangles: Uint32Array

    private var halfedges: Int32Array

    private var trianglesLen: Int

    init {
        var minX = Double.POSITIVE_INFINITY
        var minY = Double.POSITIVE_INFINITY
        var maxX = Double.NEGATIVE_INFINITY
        var maxY = Double.NEGATIVE_INFINITY

        val ids = Uint32Array(points.size)

        //points -> coords
        // minX, minY, maxX, maxY
        for (i in 0 until points.size) {
            val p = points[i]
            val x = getX(p)
            val y = getY(p)
            ids[i] = i
            coords[2 * i] = x
            coords[2 * i + 1] = y
            if (x < minX) minX = x
            if (y < minY) minY = y
            if (x > maxX) maxX = x
            if (y > maxY) maxY = y
        }

        val cx = (minX + maxX) / 2
        val cy = (minY + maxY) / 2

        var minDist = Double.POSITIVE_INFINITY

        var i0: Int = -1
        var i1: Int = -1
        var i2: Int = -1

        // pick a seed point close to the centroid
        for (i in 0 until points.size) {
            val d = dist(cx, cy, coords[2 * i], coords[2 * i + 1])
            if (d < minDist) {
                i0 = i
                minDist = d
            }
        }

        minDist = Double.POSITIVE_INFINITY

        // find the point closest to the seed
        for (i in 0 until points.size) {
            if (i == i0) continue
            val d = dist(
                coords[2 * i0], coords[2 * i0 + 1],
                coords[2 * i], coords[2 * i + 1])
            if (d < minDist && d > 0) {
                i1 = i
                minDist = d
            }
        }

        var minRadius = Double.POSITIVE_INFINITY

        // find the third point which forms the smallest circumcircle with the first two
        for (i in 0 until points.size) {
            if (i == i0 || i == i1) continue

            val r = circumradius(
                coords[2 * i0], coords[2 * i0 + 1],
                coords[2 * i1], coords[2 * i1 + 1],
                coords[2 * i], coords[2 * i + 1]
            )

            if (r < minRadius) {
                i2 = i
                minRadius = r
            }
        }

        require(minRadius != Double.POSITIVE_INFINITY) { "No Delaunay triangulation exists for this input." }

        // swap the order of the seed points for counter-clockwise orientation
        if (area(
                coords[2 * i0], coords[2 * i0 + 1],
                coords[2 * i1], coords[2 * i1 + 1],
                coords[2 * i2], coords[2 * i2 + 1]
            ) < 0
        ) {

            val tmp = i1
            i1 = i2
            i2 = tmp
        }

        val i0x = coords[2 * i0]; val i0y = coords[2 * i0 + 1]
        val i1x = coords[2 * i1]; val i1y = coords[2 * i1 + 1]
        val i2x = coords[2 * i2]; val i2y = coords[2 * i2 + 1]

        val center = circumcenter(i0x, i0y, i1x, i1y, i2x, i2y)
        _cx = center.x
        _cy = center.y

        // sort the points by distance from the seed triangle circumcenter
        quicksort(ids, coords, 0, ids.length - 1, center.x, center.y)

        // initialize a hash table for storing edges of the advancing convex hull
        _hashSize = ceil(sqrt(points.size.toDouble())).toInt()
        _hash = arrayOfNulls(_hashSize)

        // initialize a circular doubly-linked list that will hold an advancing convex hull
        hull = insertNode(coords, i0)
        var e = hull
        this.hashEdge(e)
        e.t = 0
        e = insertNode(coords, i1, e)
        this.hashEdge(e)
        e.t = 1
        e = insertNode(coords, i2, e)
        this.hashEdge(e)
        e.t = 2

        val maxTriangles = 2 * points.size - 5
        val triangles = Uint32Array (maxTriangles * 3)
        val halfedges = Int32Array (maxTriangles * 3)
        this.halfedges = halfedges
        this.triangles = triangles

        this.trianglesLen = 0

        addTriangle(i0, i1, i2, -1, -1, -1)

        var xp:Double = Double.NEGATIVE_INFINITY
        var yp:Double = Double.NEGATIVE_INFINITY
        var i: Int
        var x: Double
        var y: Double
        for (k in 0 until ids.length) {
            i = ids[k]
            x = coords[2 * i]
            y = coords[2 * i + 1]

            // skip duplicate points
            if (x == xp && y == yp) continue
            xp = x
            yp = y

            // skip seed triangle points
            if ((x == i0x && y == i0y) ||
                (x == i1x && y == i1y) ||
                (x == i2x && y == i2y)
            ) continue

            // find a visible edge on the convex hull using edge hash
            val startKey = hashKey(x, y)
            var key = startKey
            var start:Node?
            do {
                start = _hash[key]
                key = (key + 1) % this._hashSize
            } while ((start == null || start.removed) && key != startKey)

            e = start!!
            while (area(x, y, e.x, e.y, e.next!!.x, e.next!!.y) >= 0) {
                e = e.next!!
                if (e === start) {
                    throw Error ("Something is wrong with the input points.")
                }
            }

            val walkBack = e === start

            // add the first triangle from the point
            var t = addTriangle(e.i, i, e.next!!.i, -1, -1, e.t)

            e.t = t // keep track of boundary triangles on the hull
            e = insertNode(coords, i, e)

            // recursively flip triangles from the point until they satisfy the Delaunay condition
            e.t = legalize(t + 2)
            if (e.prev!!.prev!!.t == halfedges[t + 1]) {
                e.prev!!.prev!!.t = t + 2
            }

            // walk forward through the hull, adding more triangles and flipping recursively
            var q = e.next
            while (area(x, y, q!!.x, q.y, q.next!!.x, q.next!!.y) < 0) {
                t = addTriangle(q.i, i, q.next!!.i, q.prev!!.t, -1, q.t)
                q.prev!!.t = legalize(t + 2)
                this.hull = q.removeNode()!!
                q = q.next
            }

            if (walkBack) {
                // walk backward from the other side, adding more triangles and flipping
                q = e.prev
                while (area(x, y, q!!.prev!!.x, q.prev!!.y, q.x, q.y) < 0) {
                    t = this.addTriangle(q.prev!!.i, i, q.i, -1, q.t, q.prev!!.t)
                    this.legalize(t + 2)
                    q.prev!!.t = t
                    this.hull = q.removeNode()!!
                    q = q.prev
                }
            }

            // save the two new edges in the hash table
            hashEdge(e)
            hashEdge(e.prev!!)
        }

        // trim typed triangle mesh arrays
        this.triangles = triangles.subarray(0, this.trianglesLen)
        this.halfedges = halfedges.subarray(0, this.trianglesLen)
    }


    private fun hashEdge(e: Node) {
        this._hash[hashKey(e.x, e.y)] = e
    }

    private fun hashKey(x: Double, y: Double):Int {
        val dx = x - _cx
        val dy = y - _cy
        // use pseudo-angle: a measure that monotonically increases
        // with real angle, but doesn't require expensive trigonometry
        val p: Double = 1 - dx / (abs(dx) + abs(dy))
        return floor((2.0 + if (dy < 0) -p else p) / 4 * this._hashSize).toInt()
    }

    private fun legalize(a: Int): Int {
        val triangles = this.triangles
        val coords = this.coords
        val halfedges = this.halfedges
        val b = halfedges[a]
        val a0 = a - a % 3
        val b0 = b - b % 3

        val al = a0 + (a + 1) % 3
        val ar = a0 + (a + 2) % 3
        val bl = b0 + (b + 2) % 3

        val p0 = triangles[ar]
        val pr = triangles[a]
        val pl = triangles[al]
        val p1 = triangles[bl]

        val illegal = inCircle(
            coords[2 * p0], coords[2 * p0 + 1],
            coords[2 * pr], coords[2 * pr + 1],
            coords[2 * pl], coords[2 * pl + 1],
            coords[2 * p1], coords[2 * p1 + 1]
        )

        if (illegal) {
            triangles[a] = p1
            triangles[b] = p0

            link(a, halfedges[bl])
            link(b, halfedges[ar])
            link(ar, bl)

            val br = b0 + (b + 1) % 3

            legalize(a)
            return legalize(br)
        }

        return ar
    }

    private fun link(a:Int, b:Int) {
        this.halfedges[a] = b
        if (b != -1) this.halfedges[b] = a
    }

    // add a new triangle given vertex indices and adjacent half-edge ids
    private fun addTriangle(i0:Int, i1:Int, i2:Int, a:Int, b:Int, c:Int): Int {
        val t = this.trianglesLen

        this.triangles[t] = i0
        this.triangles[t + 1] = i1
        this.triangles[t + 2] = i2

        link(t, a)
        link(t + 1, b)
        link(t + 2, c)

        this.trianglesLen += 3

        return t
    }
}


/**
 * Circular Double Linked List
 */
class Node(
    val i: Int,
    val x: Double,
    val y: Double,
    var t: Int,
    var prev: Node?,
    var next: Node?,
    var removed: Boolean
) {

    fun removeNode(): Node? {
        prev?.next = next
        next?.prev = prev
        removed = true
        return prev
    }

}


// create a new node in a doubly linked list
internal fun insertNode(coords: Array<Double>, i: Int, prev: Node? = null): Node {
    val node = Node(i, coords[2 * i], coords[2 * i + 1], 0, null, null, false)

    if (prev == null) {
        node.prev = node
        node.next = node

    } else {
        node.next = prev.next
        node.prev = prev
        prev.next?.prev = node
        prev.next = node
    }
    return node
}


fun area(ax: Double, ay: Double, bx: Double, by: Double, cx: Double, cy: Double) =
    (by - ay) * (cx - bx) - (bx - ax) * (cy - by)


fun inCircle(ax: Double, ay: Double,
             bx: Double, by: Double,
             cx: Double, cy: Double,
             px: Double, py: Double): Boolean {
    val pax = ax - px
    val pay = ay - py
    val pbx = bx - px
    val pby = by - py
    val pcx = cx - px
    val pcy = cy - py

    val ap = pax * pax + pay * pay
    val bp = pbx * pbx + pby * pby
    val cp = pcx * pcx + pcy * pcy

    return pax * (pby * cp - bp * pcy) -
            pay * (pbx * cp - bp * pcx) +
            ap * (pbx * pcy - pby * pcx) < 0
}

fun circumradius(
    ax: Double, ay: Double,
    bx: Double, by: Double,
    cx: Double, cy: Double): Double {

    val abx = bx - ax
    val aby = by - ay
    val acx = cx - ax
    val acy = cy - ay

    val bl = abx * abx + aby * aby
    val cl = acx * acx + acy * acy

    if (bl == 0.0 || cl == 0.0) return Double.POSITIVE_INFINITY

    val d = abx * acy - aby * acx
    if (d == 0.0) return Double.POSITIVE_INFINITY

    val x = (acy * bl - aby * cl) * 0.5 / d
    val y = (abx * cl - acx * bl) * 0.5 / d

    return x * x + y * y
}

fun circumcenter(ax: Double, ay: Double, bx: Double, by: Double, cx: Double, cy: Double): Point {
    val abx = bx - ax
    val aby = by - ay
    val acx = cx - ax
    val acy = cy - ay

    val bl = abx * abx + aby * aby
    val cl = acx * acx + acy * acy

    val d = abx * acy - aby * acx

    val x = (acy * bl - aby * cl) * 0.5 / d
    val y = (abx * cl - acx * bl) * 0.5 / d

    return Point(
        x = ax + x,
        y = ay + y
    )
}

inline fun Double?.isFalsy() = this == null || this == -0.0 || this == 0.0 || isNaN()
inline fun Double?.orNull(): Double? = if (this.isFalsy()) null else this


private inline fun compare(coords: Array<Double>, i: Int, j: Int, cx: Double, cy: Double): Double {
    val d1: Double = dist(coords[2 * i], coords[2 * i + 1], cx, cy)
    val d2: Double = dist(coords[2 * j], coords[2 * j + 1], cx, cy)
    return (d1 - d2).orNull() ?: (coords[2 * i] - coords[2 * j]) ?: (coords[2 * i + 1] - coords[2 * j + 1])
}

fun quicksort(ids: Uint32Array, coords: Array<Double>, left: Int, right: Int, cx: Double, cy: Double) {

    var j: Int
    var temp: Int

    if (right - left <= 20) {
        for (i in (left + 1)..right) {
            temp = ids[i]
            j = i - 1
            while (j >= left && compare(coords, ids[j], temp, cx, cy) > 0)
                ids[j + 1] = ids[j--]
            ids[j + 1] = temp
        }
    } else {
        var i: Int = left + 1
        val median = (0.5 * (left + right)).toInt()
        j = right
        swap(ids, median, i)
        if (compare(coords, ids[left], ids[right], cx, cy) > 0) swap(ids, left, right)
        if (compare(coords, ids[i], ids[right], cx, cy) > 0) swap(ids, i, right)
        if (compare(coords, ids[left], ids[i], cx, cy) > 0) swap(ids, left, i)

        temp = ids[i]
        while (true) {
            do {
                i++
            } while (compare(coords, ids[i], temp, cx, cy) < 0)

            do {
                j--
            } while (compare(coords, ids[j], temp, cx, cy) > 0)

            if (j < i) break
            swap(ids, i, j)
        }
        ids[left + 1] = ids[j]
        ids[j] = temp

        if (right - i + 1 >= j - left) {
            quicksort(ids, coords, i, right, cx, cy)
            quicksort(ids, coords, left, j - 1, cx, cy)
        } else {
            quicksort(ids, coords, left, j - 1, cx, cy)
            quicksort(ids, coords, i, right, cx, cy)
        }
    }
}

fun dist(ax: Double, ay: Double, bx: Double, by: Double): Double {
    val dx = ax - bx
    val dy = ay - by
    return dx * dx + dy * dy
}

private fun swap(arr:Uint32Array, i: Int, j: Int) {
    val tmp = arr[i]
    arr[i] = arr[j]
    arr[j] = tmp
}
