/*
 * Copyright (c) 2018-2019. data2viz sàrl.
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


data class GeoJson(val type: String, val value: Double, val coordinates: Array<Array<Array<Array<Double>>>>)


fun contour(init: Contour.() -> Unit) = Contour().apply(init)


val cases = arrayOf(
        arrayOf(),
        arrayOf(line(pt(1.0, 1.5), pt(0.5, 1.0))),
        arrayOf(line(pt(1.5, 1.0), pt(1.0, 1.5))),
        arrayOf(line(pt(1.5, 1.0), pt(0.5, 1.0))),
        arrayOf(line(pt(1.0, 0.5), pt(1.5, 1.0))),
        arrayOf(line(pt(1.0, 0.5), pt(0.5, 1.0)), line(pt(1.0, 1.5), pt(1.5, 1.0))),
        arrayOf(line(pt(1.0, 0.5), pt(1.0, 1.5))),
        arrayOf(line(pt(1.0, 0.5), pt(0.5, 1.0))),
        arrayOf(line(pt(0.5, 1.0), pt(1.0, 0.5))),
        arrayOf(line(pt(1.0, 1.5), pt(1.0, 0.5))),
        arrayOf(line(pt(0.5, 1.0), pt(1.0, 1.5)), line(pt(1.5, 1.0), pt(1.0, 0.5))),
        arrayOf(line(pt(1.5, 1.0), pt(1.0, 0.5))),
        arrayOf(line(pt(0.5, 1.0), pt(1.5, 1.0))),
        arrayOf(line(pt(1.0, 1.5), pt(1.5, 1.0))),
        arrayOf(line(pt(0.5, 1.0), pt(1.0, 1.5))),
        arrayOf()
)

fun line(start: Array<Double>, end: Array<Double>) = arrayOf(start, end)
fun pt(x: Double, y: Double) = arrayOf(x, y)

class Contour {

    var thresholds: (Array<Double>) -> Array<Double> = { arrayOf() }
    var dx: Int = 1
    var dy: Int = 1


    fun size(dx: Int, dy: Int) {
        if (dx <= 0 || dy <= 0)
            error("invalid size")
        this.dx = dx
        this.dy = dy
    }

    fun contours(values: Array<Double>): List<GeoJson> {

        if (values.size != dx * dy)
            error("Wrong value size")
        val tz = thresholds(values).sortedArray()

        val layers = tz.map { thresold ->
            val polygons = mutableListOf<MutableList<List<Array<Double>>>>()
            val holes = mutableListOf<List<Array<Double>>>()

            isorings(values, thresold) { ring: MutableList<Array<Double>> ->
                //                smoothLinear(ring, values, thresold)
                if (doubleArea(ring.toTypedArray()) > 0)
                    polygons.add(mutableListOf(ring))
                else
                    holes.add(ring)
            }

            holes.forEach { hole ->
                for (i in (0 until polygons.size)) {
                    val polygon = polygons[i]
                    if (contains(polygon[0], hole) != -1) {
                        polygon.add(hole)
                        return@forEach
                    }
                }
            }
            polygons as List<List<List<Array<Double>>>>
        }

        return layers.mapIndexed { index, it ->
            val coordinates = Array(it.size) { pindex ->
                Array(it[pindex].size) { rindex ->
                    Array(it[pindex][rindex].size) { lindex ->
                        val pt = it[pindex][rindex][lindex]
                        arrayOf(pt.x, pt.y)
                    }
                }
            }
            GeoJson("MultiPolygon", tz[index], coordinates)
        }
    }

    private fun contains(ring: List<Array<Double>>, hole: List<Array<Double>>): Int {
        var i = -1
        val n = hole.size
        while (++i < n) {
            val c = ringContains(ring, hole[i])
            if (c != 0)
                return c
        }
        return 0
    }

    private class Fragment(var start: Int, var end: Int, val ring: MutableList<Array<Double>>)

    private fun isorings(values: Array<Double>, thresold: Double, callback: (MutableList<Array<Double>>) -> Unit) {
        var t0: Boolean
        var t1: Boolean
        var t2: Boolean
        var t3: Boolean
        fun index(point: Array<Double>): Int = (point.x * 2 + point.y * (dx + 1) * 4).toInt()


//        val fragmentByStart = mutableMapOf<Double, Fragment>()
//        val fragmentByEnd = mutableMapOf<Double, Fragment>()

        val maxSize = index(arrayOf(dx.toDouble(), dy.toDouble()))
        val fragmentByStart: Array<Fragment?> = arrayOfNulls(maxSize)
        val fragmentByEnd: Array<Fragment?> = arrayOfNulls(maxSize)

        var x = -1
        var y = -1

        fun threshold(index: Int) = values[index] >= thresold
        fun Boolean.shl(bitCount: Int = 0) = (if (this) 1 else 0) shl bitCount


        fun stitch(line: Array<Array<Double>>) {
            val start = pt(line.start.x + x, line.start.y + y)
            val end = pt(line.end.x + x, line.end.y + y)
            val startIndex = index(start)
            val endIndex = index(end)

            var f = fragmentByEnd.get(startIndex)
            var g = fragmentByStart.get(endIndex)
            if (f != null) {
                if (g != null) {
                    fragmentByEnd[f.end] = null
                    fragmentByStart[g.start] = null
                    if (f === g) {
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
                    if (f === g) {
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

    fun smoothLinear(ring: MutableList<Array<Double>>, values: Array<Double>, value: Double) {
        ring.forEach { pt ->
            val x = pt[0]
            val y = pt[1]
            val xt = if (x != 0.0) x else 0.0 //todo WTF
            val yt = if (y != 0.0) y else 0.0
//            val v1 = values[yt * dx - xt - 1]
            if (x > 0 && x < dx && xt === x) {

            }
        }
    }
}


/**
 * return the double of the area of the ring. Positive if points are
 * counter-clockwise negative otherwise.
 */
fun doubleArea(ring: Array<Array<Double>>): Double {
    var i = 0
    val n = ring.size
    var area = ring[n - 1][1] * ring[0][0] - ring[n - 1][0] * ring[0][1]
    while (++i < n)
        area += ring[i - 1][1] * ring[i][0] - ring[i - 1][0] * ring[i][1]
    return area
}


/**
 * If point inside ring returns 1
 * If point on ring returns 0
 * If point outside ring returns -1
 */
fun ringContains(ring: List<Array<Double>>, point: Array<Double>): Int {
    val x = point[0]
    val y = point[1]
    var contains = -1
    val n = ring.size
    var j = n - 1
    var i = 0
    do {
        val pi = ring[i]
        val xi = pi[0]
        val yi = pi[1]
        val pj = ring[j]
        val xj = pj[0]
        val yj = pj[1]
        if (segmentContains(pi, pj, point))
            return 0
        if (((yi > y) != (yj > y)) && ((x < (xj - xi) * (y - yi) / (yj - yi) + xi)))
            contains = -contains

        j = i++
    } while (i < n)
    return contains
}

fun segmentContains(start: Array<Double>, end: Array<Double>, point: Array<Double>): Boolean {
    val i = if (start[0] == end[0]) 1 else 0 //if vertical compare y
    return collinear(start, end, point) && within(start[i], point[i], end[i])
}

fun within(p: Double, q: Double, r: Double) = q in p..r || q in r..p


fun collinear(a: Array<Double>, b: Array<Double>, c: Array<Double>) =
        (b[0] - a[0]) * (c[1] - a[1]) == (c[0] - a[0]) * (b[1] - a[1])

//Pseudo Point
private val Array<Double>.x: Double
    get() = this[0]
private val Array<Double>.y: Double
    get() = this[1]

//Pseudo line
private val Array<Array<Double>>.start: Array<Double>
    get() = this[0]
private val Array<Array<Double>>.end: Array<Double>
    get() = this[1]


