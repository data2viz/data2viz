package io.data2viz.geo

import io.data2viz.geo.projection.Stream
import io.data2viz.math.toRadians
import kotlin.math.*

/**
 * Returns the great-arc distance in radians between the two points from and to.
 * Each point must be specified as a two-element array [longitude, latitude] in degrees.
 * This is the spherical equivalent of PathMeasure given a LineString of two points.
 */
fun geoDistance(from:DoubleArray, to:DoubleArray): Double {
    val line = LineString(listOf(from, to))
    return GeoLength().result(line)
}

// TODO : fun geoLength(geo) = GeoLength().result(geo) and generalizes to all measurements classes

/**
 * Returns the great-arc length of the specified GeoJSON object in radians.
 * For polygons, returns the perimeter of the exterior ring plus that of any interior rings.
 * This is the spherical equivalent of PathMeasure.
 */
class GeoLength : Stream {

    private var lengthSum = .0
    private var lambda0 = Double.NaN
    private var cosPhi0 = Double.NaN
    private var sinPhi0 = Double.NaN

    private val noop: () -> Unit = { }
    private val noop3: (Double, Double, Double) -> Unit = { x, y, z -> }

    private var currentPoint: (Double, Double, Double) -> Unit = noop3
    private var currentLineEnd: () -> Unit = noop

    // TODO : invoke ?
    fun result(geo:GeoJSON): Double {
        lengthSum = .0
        stream(geo, this)
        return lengthSum
    }

    override fun point(x: Double, y: Double, z: Double) = currentPoint(x, y, z)
    override fun lineStart() {
        currentPoint = ::lengthPointFirst
        currentLineEnd = ::lengthLineEnd
    }
    override fun lineEnd() = currentLineEnd()

    private fun lengthPointFirst(x: Double, y: Double, z: Double) {
        val lambda = x.toRadians()
        val phi = y.toRadians()
        lambda0 = lambda
        sinPhi0 = sin(phi)
        cosPhi0 = cos(phi)
        currentPoint = ::lengthPoint
    }

    private fun lengthLineEnd() {
        currentPoint = noop3
        currentLineEnd = noop
    }

    private fun lengthPoint(x:Double, y:Double, z:Double) {
        val lambda = x.toRadians()
        val phi = y.toRadians()
        val sinPhi = sin(phi)
        val cosPhi = cos(phi)
        val delta = abs(lambda - lambda0)
        val cosDelta = cos(delta)
        val sinDelta = sin(delta)
        val a = cosPhi * sinDelta
        val b = cosPhi0 * sinPhi - sinPhi0 * cosPhi * cosDelta
        val c = sinPhi0 * sinPhi + cosPhi0 * cosPhi * cosDelta
        lengthSum += atan2(sqrt(a * a + b * b), c)
        lambda0 = lambda
        sinPhi0 = sinPhi
        cosPhi0 = cosPhi
    }
}