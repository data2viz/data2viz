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
public data class RingPoint(internal var xPos: Double, internal var yPos: Double) {
    internal constructor(x: Int, y: Int): this(x.toDouble(), y.toDouble())

    public val x: Double
        get() = xPos

    public val y: Double
        get() = yPos
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

public fun contour(init: Contour.() -> Unit): Contour = Contour().apply(init)

/**
 * This library computes contour polygons by applying marching squares to a rectangular array of
 * numeric values.
 *
 * For each threshold value, the contour generator constructs a GeoJSON MultiPolygon geometry
 * object representing the area where the input values are greater than or equal to the threshold value.
 * The geometry is in planar coordinates, where ⟨i + 0.5, j + 0.5⟩ corresponds to element i + jn in
 * the input values array.
 *
 * To instantiate a Contour object, call the contour(init: Contour.() -> Unit) function.
 * To compute contours call Contour.contours(values).
 */
public class Contour internal constructor() {

    /**
     * Thresholds are defined as an array of values [x0, x1, …], default value is an empty Array.
     *
     * The first generated contour corresponds to the area where the input values are greater than
     * or equal to x0; the second contour corresponds to the area where the input values are greater
     * than or equal to x1, and so on.
     * Thus, there is exactly one generated MultiPolygon geometry for each specified threshold
     * value; the threshold value is exposed as a [GeoJson] object.
     */
    public var thresholds: Array<Double> = emptyArray()

    /**
     * Sets whether the generated contour polygons are smoothed using linear interpolation.
     */
    public var smoothing: Boolean = true

    @Deprecated("Pass values Array size in the contours() function call.")
    public fun size(width: Int, height: Int) {}

    /**
     * Compute all contours for the given values array.
     *
     * @param values: the values array from which to draw the contours
     * @param width: the width of the array
     * @param height: the height of the array
     * @return a [List] of [GeoJson] objects, one for each threshold value.
     */
    public fun contours(values: Array<Double>, width: Int, height: Int): List<GeoJson> {

        require(values.size == (width * height))
        { "Invalid values size, the array should contains precisely (width x height) elements." }

        val sortedThresholds = thresholds.sortedArray()

        val geoJsons = sortedThresholds.map { threshold ->
            val rings = mutableListOf<MutableList<List<RingPoint>>>()
            val holes = mutableListOf<List<RingPoint>>()

            isoRings(values, width, height, threshold) { currentRing: MutableList<RingPoint> ->

                if (smoothing) smoothLinear(currentRing, values, width, height, threshold)
                if (doubleArea(currentRing.toTypedArray()) > 0)
                    rings.add(mutableListOf(currentRing))
                else
                    holes.add(currentRing)
            }

            // Adding found "holes" to their corresponding MultiRing container
            holes.forEach { hole ->
                for (i in (0 until rings.size)) {
                    val ring = rings[i]

                    // The "container ring" (or external ring), is always the first in the array
                    val container = ring[0]

                    if (contains(container, hole) != -1) {
                        ring.add(hole)
                        return@forEach
                    }
                }
            }
            rings
        }

        // Mapping "geoJson coordinates" to the expected format:
        // First build an array for each threshold
        return geoJsons.mapIndexed { thresholdIndex, geoJson ->

            // Then build an array for each multiRing
            val coordinates = Array(geoJson.size) { multiRingIndex ->

                // Then an array for each ring of this multiRing
                Array(geoJson[multiRingIndex].size) { ringIndex ->

                    // then an array for each point
                    Array(geoJson[multiRingIndex][ringIndex].size) { pointIndex ->
                        val pt = geoJson[multiRingIndex][ringIndex][pointIndex]
                        RingPoint(pt.x, pt.y)
                    }
                }
            }
            GeoJson(sortedThresholds[thresholdIndex], coordinates)
        }
    }

    private class Fragment(var start: Int, var end: Int, val ring: MutableList<RingPoint>)

    private fun isoRings(values: Array<Double>, width: Int, height: Int, threshold: Double, callback: (MutableList<RingPoint>) -> Unit) {
        var t0: Boolean
        var t1: Boolean
        var t2: Boolean
        var t3: Boolean
        var x = 0
        var y = 0

        fun index(point: RingPoint): Int = (point.x * 2 + point.y * (width + 1) * 4).toInt()
        fun threshold(index: Int) = values[index] >= threshold
        fun Boolean.shl(bitCount: Int = 0) = (if (this) 1 else 0) shl bitCount

        val maxSize = index(RingPoint(width, height))
        val fragmentByStart: Array<Fragment?> = arrayOfNulls(maxSize)
        val fragmentByEnd: Array<Fragment?> = arrayOfNulls(maxSize)

        fun stitch(stitch: Stitch) {
            val start = rp(stitch.first.x + x, stitch.first.y + y)
            val end = rp(stitch.second.x + x, stitch.second.y + y)
            val startIndex = index(start)
            val endIndex = index(end)
            var f = fragmentByEnd[startIndex]
            val g: Fragment?

            if (f != null) {
                g = fragmentByStart[endIndex]
                if (g != null) {
                    fragmentByEnd[f.end] = null
                    fragmentByStart[g.start] = null
                    if (f == g) {
                        f.ring.add(end)
                        callback(f.ring)
                    } else {
                        val startEnd = Fragment(f.start, g.end, (f.ring + g.ring).toMutableList())
                        fragmentByStart[f.start] = startEnd
                        fragmentByEnd[g.end] = startEnd
                    }
                } else {
                    fragmentByEnd[f.end] = null
                    f.ring.add(end)
                    f.end = endIndex
                    fragmentByEnd[endIndex] = f
                }
            } else {
                f = fragmentByStart[endIndex]
                if (f != null) {
                    g = fragmentByEnd[startIndex]
                    if (g != null) {
                        fragmentByStart[f.start] = null
                        fragmentByEnd[g.end] = null
                        if (f == g) {
                            f.ring.add(end)
                            callback(f.ring)
                        } else {
                            val startEnd = Fragment(g.start, f.end, (g.ring + f.ring).toMutableList())
                            fragmentByStart[g.start] = startEnd
                            fragmentByEnd[f.end] = startEnd
                        }
                    } else {
                        fragmentByStart[f.start] = null
                        f.ring.add(0, start)
                        f.start = startIndex
                        fragmentByStart[f.start] = f
                    }
                } else {
                    val startEnd = Fragment(startIndex, endIndex, mutableListOf(start, end))
                    fragmentByStart[startIndex] = startEnd
                    fragmentByEnd[endIndex] = startEnd
                }
            }
        }


        // Special case for the first row (y = -1, t2 = t3 = 0).
        x = -1
        y = -1
        t1 = threshold(0)
        cases[t1.shl(1)].forEach(::stitch)
        while (++x < (width - 1)) {
            t0 = t1
            t1 = threshold(x + 1)
            cases[(t0.shl()) or (t1.shl(1))].forEach(::stitch)
        }
        cases[t1.shl()].forEach(::stitch)


        // General case for the intermediate rows.
        while (++y < (height - 1)) {
            x = -1
            t1 = threshold(y * width + width)
            t2 = threshold(y * width)
            cases[(t1.shl(1)) or (t2.shl(2))].forEach(::stitch)
            while (++x < (width - 1)) {
                t0 = t1
                t1 = threshold(y * width + width + x + 1)
                t3 = t2
                t2 = threshold(y * width + x + 1)
                cases[(t0.shl()) or (t1.shl(1)) or (t2.shl(2)) or (t3.shl(3))].forEach(::stitch)
            }
            cases[(t1.shl()) or (t2.shl(3))].forEach(::stitch)
        }


        // Special case for the last row (y = dy - 1, t0 = t1 = 0).
        x = -1
        t2 = threshold(y * width)
        cases[t2.shl(2)].forEach(::stitch)
        while (++x < (width - 1)) {
            t3 = t2
            t2 = threshold(y * width + x + 1)
            cases[(t2.shl(2)) or (t3.shl(3))].forEach(::stitch)
        }
        cases[t2.shl(3)].forEach(::stitch)
    }

    /**
     * Linear smoothing of the point of a ring
     */
    private fun smoothLinear(ring: MutableList<RingPoint>, values: Array<Double>, width: Int, height: Int, value: Double) {
        ring.forEach { pt ->
            val x = pt.x
            val y = pt.y
            val xt = x.toInt()
            val yt = y.toInt()

            if (x > 0 && x < width && (xt.toDouble() == x)) {
                val pointIndex = yt * width + xt
                val v0 = values[pointIndex - 1]
                val v1 = values[pointIndex]
                pt.xPos = x + (value - v0) / (v1 - v0) - 0.5
            }

            if (y > 0 && y < height && (yt.toDouble() == y)) {
                val v0 = values[(yt - 1) * width + xt]
                val v1 = values[yt * width + xt]
                pt.yPos = y + (value - v0) / (v1 - v0) - 0.5
            }
        }
    }
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
 * Check if a "hole" is contained in a "ring".
 * If any point of hole is inside the ring returns 1
 * If any point of hole is outside the ring returns -1
 * Else returns 0 (all points are on the ring)
 */
internal fun contains(ring: List<RingPoint>, hole: List<RingPoint>): Int {
    var i = -1
    val n = hole.size
    while (++i < n) {
        val c = ringContains(ring, hole[i])
        if (c != 0)
            return c
    }
    return 0
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
