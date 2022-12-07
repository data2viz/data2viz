/*
 * Copyright (c) 2018-2021. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.contour

private const val geoJsonMultypolygonType = "MultiPolygon"

/**
 * A [MultiRing] stores the coordinates of a single contour.
 * It can be constituted of multiple [Ring] because it can contain holes.
 * The "external" surface of the ring is stored at index 0 and all "holes" are stored at indices 1+.
 */
public typealias MultiRing = Array<Ring>

/**
 * A [Ring] is an array of [RingPoint], one or multiple rings make a [MultiRing].
 * A [Ring] starts and ends at the same [RingPoint]: ring.first() == ring.last()
 */
public typealias Ring = Array<RingPoint>

/**
 * Simple class to store coordinates of the points of a [Ring].
 */
public data class RingPoint(val x: Double, val y: Double) {
    internal constructor(x: Int, y: Int): this(x.toDouble(), y.toDouble())
}

/**
 * Data class to store contour results, see the GeoJson specification for more information
 * (https://doc.arcgis.com/en/arcgis-online/reference/geojson.htm)
 *
 * A GeoJson Contour main content is stored in coordinates, as an Array of [MultiRing], one for each
 * non-intersected "external" contour line.
 * Each [MultiRing] contains an Array of [Ring], the first is the "external" ring, and subsequent optional [Ring]
 * are "holes" in this ring.
 * Then, each [Ring] contains an Array of [RingPoint] that store each coordinates to draw it.
 *
 * @param value: the threshold value that generates this contour
 * @param coordinates: all the contours for the threshold stored as [MultiRing]
 */
public data class GeoJson(val value: Double, val coordinates: Array<MultiRing>) {

    /**
     * The type of GeoJson for a Contour is always a "MultiPolygon".
     */
    public val type: String
        get() = geoJsonMultypolygonType

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as GeoJson

        if (value != other.value) return false
        if (!coordinates.contentDeepEquals(other.coordinates)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 31 * result + coordinates.contentDeepHashCode()
        return result
    }
}

/**
 * Get the contour (as a [MultiRing]) for the given index of this Contour GeoJson.
 */
public fun GeoJson.multiRing(index: Int): MultiRing = coordinates[index]

/**
 * Get the [Ring] for the given index of this [MultiRing].
 */
public fun MultiRing.ring(index: Int): Ring = this[index]

/**
 * Get the [RingPoint] for a given index of this [Ring].
 */
public fun Ring.point(index: Int): RingPoint = this[index]


public fun contour(init: Contour.() -> Unit): Contour = Contour().apply(init)

private typealias Stitch = Pair<RingPoint, RingPoint>

/**
 * Cases for stitching, contains 0, 1 or 2 possible "stitch case" as [Stitch], a Pair of ContourPoint
 */
private val cases: Array<Array<Stitch>> = arrayOf(
        arrayOf(),
        arrayOf(Stitch(rp(1.0, 1.5), rp(0.5, 1.0))),
        arrayOf(Stitch(rp(1.5, 1.0), rp(1.0, 1.5))),
        arrayOf(Stitch(rp(1.5, 1.0), rp(0.5, 1.0))),
        arrayOf(Stitch(rp(1.0, 0.5), rp(1.5, 1.0))),
        arrayOf(Stitch(rp(1.0, 1.5), rp(0.5, 1.0)), Stitch(rp(1.0, 0.5), rp(1.5, 1.0))),
        arrayOf(Stitch(rp(1.0, 0.5), rp(1.0, 1.5))),
        arrayOf(Stitch(rp(1.0, 0.5), rp(0.5, 1.0))),
        arrayOf(Stitch(rp(0.5, 1.0), rp(1.0, 0.5))),
        arrayOf(Stitch(rp(1.0, 1.5), rp(1.0, 0.5))),
        arrayOf(Stitch(rp(0.5, 1.0), rp(1.0, 0.5)), Stitch(rp(1.5, 1.0), rp(1.0, 1.5))),
        arrayOf(Stitch(rp(1.5, 1.0), rp(1.0, 0.5))),
        arrayOf(Stitch(rp(0.5, 1.0), rp(1.5, 1.0))),
        arrayOf(Stitch(rp(1.0, 1.5), rp(1.5, 1.0))),
        arrayOf(Stitch(rp(0.5, 1.0), rp(1.0, 1.5))),
        arrayOf()
)

private fun rp(x: Double, y: Double) = RingPoint(x, y)

public class Contour {

    public var thresholds: (Array<Double>) -> Array<Double> = { arrayOf() }
    private var dx: Int = 1
    private var dy: Int = 1


    public fun size(width: Int, height: Int) {
        require (width > 0 && height > 0) { "Invalid size, width and height must be positive integer values." }
        dx = width
        dy = height
    }

    /**
     * Return all contours as a [List<GeoJson>], one for each threshold value.
     */
    public fun contours(values: Array<Double>): List<GeoJson> {

        require(values.size == (dx * dy))
        { "Invalid values size, the array should contains precisely (size.width x size.height) elements." }

        val tz = thresholds(values).sortedArray()

        // TODO Convert number of thresholds (if not array, see d3.js) into uniform thresholds.

        val layers = tz.map { threshold ->
            val rings = mutableListOf<MutableList<List<RingPoint>>>()
            val holes = mutableListOf<List<RingPoint>>()

            isoRings(values, threshold) { ring: MutableList<RingPoint> ->
//                smoothLinear(ring, values, threshold)
                if (doubleArea(ring.toTypedArray()) > 0)
                    rings.add(mutableListOf(ring))
                else
                    holes.add(ring)
            }

            holes.forEach { hole ->
                for (i in (0 until rings.size)) {
                    val ring = rings[i]
                    if (contains(ring[0], hole) != -1) {
                        ring.add(hole)
                        return@forEach
                    }
                }
            }
            rings
        }

        return layers.mapIndexed { index, layer ->
            val coordinates = Array(layer.size) { layerIndex ->
                Array(layer[layerIndex].size) { rindex ->
                    Array(layer[layerIndex][rindex].size) { lindex ->
                        val pt = layer[layerIndex][rindex][lindex]
                        RingPoint(pt.x, pt.y)
                    }
                }
            }
            GeoJson(tz[index], coordinates)
        }
    }

    private fun contains(ring: List<RingPoint>, hole: List<RingPoint>): Int {
        var i = -1
        val n = hole.size
        while (++i < n) {
            val c = ringContains(ring, hole[i])
            if (c != 0)
                return c
        }
        return 0
    }

    private class Fragment(var start: Int, var end: Int, val ring: MutableList<RingPoint>)

    private fun isoRings(values: Array<Double>, threshold: Double, callback: (MutableList<RingPoint>) -> Unit) {
        var t0: Boolean
        var t1: Boolean
        var t2: Boolean
        var t3: Boolean

        fun index(point: RingPoint): Int = (point.x * 2 + point.y * (dx + 1) * 4).toInt()
        fun threshold(index: Int) = values[index] >= threshold
        fun Boolean.shl(bitCount: Int = 0) = (if (this) 1 else 0) shl bitCount

        val maxSize = index(RingPoint(dx, dy))
        val fragmentByStart: Array<Fragment?> = arrayOfNulls(maxSize)
        val fragmentByEnd: Array<Fragment?> = arrayOfNulls(maxSize)

        var x = -1
        var y = -1

        fun stitch(stitch: Stitch) {
            val start = rp(stitch.first.x + x, stitch.first.y + y)
            val end = rp(stitch.second.x + x, stitch.second.y + y)
            val startIndex = index(start)
            val endIndex = index(end)

            var f = fragmentByEnd[startIndex]
            var g = fragmentByStart[endIndex]
            if (f != null) {
                if (g != null) {
                    fragmentByEnd[f.end] = null
                    fragmentByStart[g.start] = null
                    if (f == g) {
                        f.ring.add(end)
                        callback(f.ring)
                    } else {
                        val startEnd = Fragment(f.start, g.end, (f.ring + g.ring).toMutableList())
                        fragmentByStart[f.start] = startEnd
                        fragmentByStart[g.end] = startEnd
                    }
                } else {
                    fragmentByEnd[f.end] = null
                    f.ring.add(end)
                    f.end = endIndex
                    fragmentByEnd[endIndex] = f
                }
            } else if (fragmentByStart[endIndex]?.let { f = it;true } == true) {
                if (fragmentByEnd[startIndex]?.let { g = it; true } == true) {
                    fragmentByStart[f!!.start] = null
                    fragmentByEnd[g!!.start] = null
                    if (f == g) {
                        f!!.ring.add(end)
                        callback(f!!.ring)
                    } else {
                        val startEnd = Fragment(g!!.start, f!!.end, (g!!.ring + f!!.ring).toMutableList())
                        fragmentByStart[g!!.start] = startEnd
                        fragmentByEnd[f!!.end] = startEnd
                    }
                } else {
                    fragmentByStart[f!!.start] = null
                    f!!.ring.add(0, start)
                    f!!.start = startIndex
                    fragmentByStart[startIndex] = f!!
                }
            } else {
                val startEnd = Fragment(startIndex, endIndex, mutableListOf(start, end))
                fragmentByStart[startIndex] = startEnd
                fragmentByEnd[endIndex] = startEnd
            }
        }

        // Special case for the first row (y = -1, t2 = t3 = 0).
        t1 = threshold(0)
        cases[t1.shl(1)].forEach(::stitch)
        while (++x < dx - 1) {
            t0 = t1
            t1 = threshold(x + 1)
            cases[(t0.shl() or t1.shl(1))].forEach(::stitch)
        }
        cases[t1.shl()].forEach(::stitch)

        // General case for the intermediate rows.
        while (++y < dy - 1) {
            x = -1
            t1 = threshold(y * dx + dx)
            t2 = threshold(y * dx)
            cases[t1.shl(1) or t2.shl(2)].forEach(::stitch)
            while (++x < dx - 1) {
                t0 = t1
                t1 = threshold(y * dx + dx + x + 1)
                t3 = t2
                t2 = threshold(y * dx + x + 1)
                cases[t0.shl() or t1.shl(1) or t2.shl(2) or t3.shl(3)].forEach(::stitch)
            }
            cases[t1.shl() or t2.shl(3)].forEach(::stitch)
        }

        // Special case for the last row (y = dy - 1, t0 = t1 = 0).
        x = -1
        t2 = threshold(y * dx)
        cases[t2.shl(2)].forEach(::stitch)
        while (++x < dx - 1) {
            t3 = t2
            t2 = threshold(y * dx + x + 1)
            cases[t2.shl(2) or t3.shl(3)].forEach(::stitch)
        }
        cases[t2.shl(3)].forEach(::stitch)


    }

    // TODO partial port, problem on v1 = values[yt * dx + xt]
//    fun smoothLinear(ring: MutableList<Array<Double>>, values: Array<Double>, value: Double) {
//        ring.forEach { pt ->
//            val x = pt[0]
//            val y = pt[1]
//            val xt = x.toInt()
//            val yt = y.toInt()
//            var v0 = .0
//            val v1 = values[yt * dx + xt]
//            if (x > 0 && x < dx && (xt.toDouble() == x)) {
//                v0 = values[yt * dx + xt - 1]
//                pt[0] = x + (value - v0) / (v1 - v0) - 0.5
//            }
//
//            if (y > 0 && y < dy && (yt.toDouble() == y)) {
//                v0 = values[(yt - 1) * dx + xt]
//                pt[1] = y + (value - v0) / (v1 - v0) - 0.5
//            }
//        }
//    }

}


/**
 * return the double of the area of the ring. Positive if points are
 * counter-clockwise negative otherwise.
 */
internal fun doubleArea(ring: Array<RingPoint>): Double {
    var i = 0
    val n = ring.size
    var area = ring[n - 1].y * ring[0].x - ring[n - 1].x * ring[0].y
    while (++i < n)
        area += ring[i - 1].y * ring[i].x - ring[i - 1].x * ring[i].y
    return area
}



/**
 * If point inside ring returns 1
 * If point on ring returns 0
 * If point outside ring returns -1
 */
internal fun ringContains(ring: List<RingPoint>, point: RingPoint): Int {
    val x = point.x
    val y = point.y
    var contains = -1
    val n = ring.size
    var j = n - 1
    var i = 0
    do {
        val pi = ring[i]
        val xi = pi.x
        val yi = pi.y
        val pj = ring[j]
        val xj = pj.x
        val yj = pj.y

        if (segmentContains(pi, pj, point)) return 0
        if (((yi > y) != (yj > y)) && ((x < (xj - xi) * (y - yi) / (yj - yi) + xi)))
            contains = -contains

        j = i++
    } while (i < n)
    return contains
}

//if vertical compare y
internal fun segmentContains(start: RingPoint, end: RingPoint, point: RingPoint): Boolean {
    return collinear(start, end, point) && (
        if (start.x == end.x)   within(start.y, point.y, end.y)
        else                    within(start.x, point.x, end.x)
    )
}

internal fun within(from: Double, within: Double, to: Double) = within in from..to || within in to..from

internal fun collinear(a: RingPoint, b: RingPoint, c: RingPoint) =
        (b.x - a.x) * (c.y - a.y) == (c.x - a.x) * (b.y - a.y)


