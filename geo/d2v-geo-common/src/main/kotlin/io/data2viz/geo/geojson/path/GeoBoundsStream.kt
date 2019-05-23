package io.data2viz.geo.geojson.path


import io.data2viz.geo.geojson.stream
import io.data2viz.geo.geometry.cartesian
import io.data2viz.geo.geometry.cartesianCross
import io.data2viz.geo.geometry.cartesianNormalize
import io.data2viz.geo.geometry.spherical
import io.data2viz.geo.stream.Stream
import io.data2viz.geom.Extent
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.math.EPSILON
import io.data2viz.math.toDegrees
import io.data2viz.math.toRadians
import kotlin.math.abs
import io.data2viz.geo.geometry.path.BoundsStream

fun geoBounds(geo: GeoJsonObject) = GeoBoundsStream().result(geo)



/**
 * Returns the spherical bounding box for the specified GeoJSON object.
 * The bounding box is represented by an Extent where:
 *  - x0 is the minimum longitude,
 *  - phi0 is the minimum latitude,
 *  - x1 is maximum longitude,
 *  - and phi1 is the maximum latitude.
 *
 * All coordinates are given in degrees.
 *
 * (Note that in projected planar coordinates, the minimum latitude is typically the maximum translateY-value, and the
 * maximum latitude is typically the minimum translateY-value.)
 * This is the spherical equivalent of [BoundsStream]
 */
class GeoBoundsStream : Stream {
    // TODO refactor function references :: to objects like in ProjectorResambleStream.
//  Function references have poor performance due to GC & memory allocation

    private val areaStream = GeoAreaStream()

    // drawBounds
    private var lambda0 = Double.NaN
    private var phi0 = Double.NaN
    private var lambda1 = Double.NaN
    private var phi1 = Double.NaN

    // previous lambda-coordinate
    private var lambda2 = Double.NaN

    // first point
    private var lambda00 = Double.NaN
    private var phi00 = Double.NaN

    // previous 3D point
    private var p0: DoubleArray? = null

    private var deltaSum = .0

    private var range: DoubleArray = doubleArrayOf(Double.NaN, Double.NaN)
    private val ranges = mutableListOf<DoubleArray>()

    private var currentPoint: (Double, Double) -> Unit = ::boundsPoint
    private var currentLineStart: () -> Unit = ::boundsLineStart
    private var currentLineEnd: () -> Unit = ::boundsLineEnd

    fun result(geo: GeoJsonObject): Extent {
        phi0 = Double.POSITIVE_INFINITY
        lambda0 = phi0
        phi1 = -lambda0
        lambda1 = phi1
        ranges.clear()
        geo.stream(this)

        // First, sort ranges by their minimum longitudes.
        if (ranges.isNotEmpty()) {
            ranges.sortBy { it[0] }

            // Then, merge any ranges that overlap.
            var a = ranges[0]
            val merged = mutableListOf(a)
            (1 .. ranges.lastIndex).forEach {index ->
                val b = ranges[index]
                if (rangeContains(a, b[0]) || rangeContains(a, b[1])) {
                    if (angle(a[0], b[1]) > angle(a[0], a[1])) a[1] = b[1]
                    if (angle(b[0], a[1]) > angle(a[0], a[1])) a[0] = b[0]
                } else {
                    a = b
                    merged.add(a)
                }
            }

            // Finally, find the largest gap between the merged ranges.
            // The final bounding box will be the inverse of this gap.
            var deltaMax = Double.NEGATIVE_INFINITY
            a = merged.last()
            (0..merged.lastIndex).forEach { index ->
                val b = merged[index]
                val delta = angle(a[1], b[0])
                if (delta > deltaMax) {
                    deltaMax = delta
                    lambda0 = b[0]
                    lambda1 = a[1]
                }
                a = b
            }
        }

        ranges.clear()

        return if (lambda0 == Double.POSITIVE_INFINITY || phi0 == Double.POSITIVE_INFINITY)
            Extent(Double.NaN, Double.NaN, Double.NaN, Double.NaN)
        else Extent(lambda0, phi0,lambda1, phi1)
    }

    override fun point(x: Double, y: Double, z: Double) = currentPoint(x, y)
    override fun lineStart() = currentLineStart()
    override fun lineEnd() = currentLineEnd()
    override fun polygonStart() {
        currentPoint = ::boundsRingPoint
        currentLineStart = ::boundsRingStart
        currentLineEnd = ::boundsRingEnd
        deltaSum = .0
        areaStream.polygonStart()
    }

    override fun polygonEnd() {
        areaStream.polygonEnd()
        currentPoint = ::boundsPoint
        currentLineStart = ::boundsLineStart
        currentLineEnd = ::boundsLineEnd

        if (areaStream.areaRingSum < 0) {
            lambda0 = -180.0
            lambda1 = 180.0
            phi0 = -90.0
            phi1 = 90.0
        } else if (deltaSum > EPSILON) phi1 = 90.0
        else if (deltaSum < -EPSILON) phi0 = -90.0
        range[0] = lambda0
        range[1] = lambda1
    }

    private fun rangeContains(range:DoubleArray, x:Double): Boolean {
        return if (range[0] <= range[1]) range[0] <= x && x <= range[1] else x < range[0] || range[1] < x
    }

    private fun boundsPoint(x: Double, y: Double) {
        lambda0 = x
        lambda1 = x
        range = doubleArrayOf(lambda0, lambda1)
        ranges.add(range)
        if (y < phi0) phi0 = y
        if (y > phi1) phi1 = y
    }

    private fun linePoint(x: Double, y: Double) {
        val p = cartesian(doubleArrayOf(x.toRadians(), y.toRadians()))

        if (p0 != null) {
            val normal = cartesianCross(p0!!, p)
            val equatorial = doubleArrayOf(normal[1], -normal[0], .0)

            var inflection = cartesianCross(equatorial, normal)
            inflection = cartesianNormalize(inflection)
            inflection = spherical(inflection)

            val delta = x - lambda2
            val sign = if (delta > .0) 1 else -1
            var lambdai = inflection[0].toDegrees() * sign
            val phii: Double
            val antimeridian = abs(delta) > 180.0

            if (antimeridian xor (sign * lambda2 < lambdai && lambdai < sign * x)) {
                phii = inflection[1].toDegrees()
                if (phii > phi1) phi1 = phii
            } else {
                lambdai = ((lambdai + 360.0) % 360.0) - 180.0
                if (antimeridian xor (sign * lambda2 < lambdai && lambdai < sign * x)) {
                    phii = -inflection[1].toDegrees()
                    if (phii < phi0) phi0 = phii
                } else {
                    if (y < phi0) phi0 = y
                    if (y > phi1) phi1 = y
                }
            }
            if (antimeridian) {
                if (x < lambda2) {
                    if (angle(lambda0, x) > angle(lambda0, lambda1)) lambda1 = x
                } else {
                    if (angle(x, lambda1) > angle(lambda0, lambda1)) lambda0 = x
                }
            } else {
                if (lambda1 >= lambda0) {
                    if (x < lambda0) lambda0 = x
                    if (x > lambda1) lambda1 = x
                } else {
                    if (x > lambda2) {
                        if (angle(lambda0, x) > angle(lambda0, lambda1)) lambda1 = x
                    } else {
                        if (angle(x, lambda1) > angle(lambda0, lambda1)) lambda0 = x
                    }
                }
            }
        } else {
            lambda0 = x
            lambda1 = x
            range = doubleArrayOf(lambda0, lambda1)
            ranges.add(range)
        }
        if (y < phi0) phi0 = y
        if (y > phi1) phi1 = y
        p0 = p
        lambda2 = x
    }

    private fun boundsLineStart() {
        currentPoint = ::linePoint
    }

    private fun boundsLineEnd() {
        range[0] = lambda0
        range[1] = lambda1
        currentPoint = ::boundsPoint
        p0 = null
    }

    private fun boundsRingPoint(x: Double, y: Double) {
        if (p0 != null) {
            val delta = x - lambda2
            deltaSum += if (abs(delta) > 180.0) {
                delta + if (delta > 0) 360.0 else -360.0
            } else delta
        } else {
            lambda00 = x
            phi00 = y
        }
        areaStream.point(x, y, .0)
        linePoint(x, y)
    }

    private fun boundsRingStart() {
        areaStream.lineStart()
    }

    private fun boundsRingEnd() {
        boundsRingPoint(lambda00, phi00)
        areaStream.lineEnd()
        if (abs(deltaSum) > EPSILON) {
            lambda1 = 180.0
            lambda0 = -180.0
        }
        range[0] = lambda0
        range[1] = lambda1
        p0 = null
    }

    // Finds the left-right distance between two longitudes.
    // This is almost the same as (lambda1 - lambda0 + 360°) % 360°, except that we want
    // the distance between ±180° to be 360°.
    private fun angle(lambda0: Double, lambda1: Double): Double {
        val diff = lambda1 - lambda0
        return if (diff < .0) diff + 360.0 else diff
    }
}