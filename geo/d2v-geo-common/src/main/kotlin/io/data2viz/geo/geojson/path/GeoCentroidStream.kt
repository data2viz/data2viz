package io.data2viz.geo.geojson.path

import io.data2viz.geo.stream.Stream
import io.data2viz.geo.geojson.stream
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.math.EPSILON
import io.data2viz.math.EPSILON2
import io.data2viz.math.toDegrees
import io.data2viz.math.toRadians
import kotlin.math.*

fun geoCentroid(geo: GeoJsonObject) = GeoCentroidStream().result(geo)

/**
 * Returns the spherical centroid of the specified GeoJSON object.
 * This is the spherical equivalent of CentroidStream.
 */
class GeoCentroidStream : Stream {

    private var _W0 = .0
    private var _W1 = .0
    private var _X0 = .0
    private var _Y0 = .0
    private var _Z0 = .0
    private var _X1 = .0
    private var _Y1 = .0
    private var _Z1 = .0
    private var _X2 = .0
    private var _Y2 = .0
    private var _Z2 = .0
    private var lambda00 = Double.NaN
    private var phi00 = Double.NaN      // first point
    private var x0 = Double.NaN
    private var y0 = Double.NaN
    private var z0 = Double.NaN         // previous point

    private var currentPoint: (Double, Double) -> Unit = ::centroidPoint
    private var currentLineStart: () -> Unit = ::centroidLineStart
    private var currentLineEnd: () -> Unit = ::centroidLineEnd

    // TODO : invoke ?
    fun result(geo: GeoJsonObject): DoubleArray {
        _W0 = .0
        _W1 = .0
        _X0 = .0
        _Y0 = .0
        _Z0 = .0
        _X1 = .0
        _Y1 = .0
        _Z1 = .0
        _X2 = .0
        _Y2 = .0
        _Z2 = .0
        geo.stream(this)

        var x = _X2
        var y = _Y2
        var z = _Z2
        var m = x * x + y * y + z * z;

        // If the area-weighted centroid is undefined, fall back to length-weighted ccentroid.
        if (m < EPSILON2) {
            x = _X1
            y = _Y1
            z = _Z1

            // If the feature has zero length, fall back to arithmetic mean of point vectors.
            if (_W1 < EPSILON) {
                x = _X0
                y = _Y0
                z = _Z0
            }
            m = x * x + y * y + z * z

            // If the feature still has an undefined centroid, then return.
            if (m < EPSILON2) return doubleArrayOf(Double.NaN, Double.NaN)
        }

        return doubleArrayOf(atan2(y, x).toDegrees(), asin(z / sqrt(m)).toDegrees())
    }

    override fun point(x: Double, y: Double, z: Double) = currentPoint(x, y)
    override fun lineStart() = currentLineStart()
    override fun lineEnd() = currentLineEnd()
    override fun polygonStart() {
        currentLineStart = ::centroidRingStart
        currentLineEnd = ::centroidRingEnd
    }

    override fun polygonEnd() {
        currentLineStart = ::centroidLineStart
        currentLineEnd = ::centroidLineEnd
    }

    // Arithmetic mean of Cartesian vectors.
    private fun centroidPoint(x: Double, y: Double) {
        val lambda = x.toRadians()
        val phi = y.toRadians()
        val cosPhi = cos(phi)
        centroidPointCartesian(cosPhi * cos(lambda), cosPhi * sin(lambda), sin(phi))
    }

    private fun centroidPointCartesian(x: Double, y: Double, z: Double) {
        ++_W0
        _X0 += (x - _X0) / _W0
        _Y0 += (y - _Y0) / _W0
        _Z0 += (z - _Z0) / _W0
    }

    private fun centroidLineStart() {
        currentPoint = ::centroidLinePointFirst
    }

    private fun centroidLinePointFirst(x: Double, y: Double) {
        val lambda = x.toRadians()
        val phi = y.toRadians()
        val cosPhi = cos(phi)
        x0 = cosPhi * cos(lambda)
        y0 = cosPhi * sin(lambda)
        z0 = sin(phi)
        currentPoint = ::centroidLinePoint
        centroidPointCartesian(x0, y0, z0)
    }

    private fun centroidLinePoint(x: Double, y: Double) {
        val lambda = x.toRadians()
        val phi = y.toRadians()
        val cosPhi = cos(phi)
        val a = cosPhi * cos(lambda)
        val b = cosPhi * sin(lambda)
        val c = sin(phi)
        val w1 = y0 * c - z0 * b
        val w2 = z0 * a - x0 * c
        val w3 = x0 * b - y0 * a
        val w = atan2(sqrt((w1 * w1) + (w2 * w2) + (w3 * w3)), x0 * a + y0 * b + z0 * c)
        _W1 += w
        _X1 += w * (x0 + a)
        x0 = a
        _Y1 += w * (y0 + b)
        y0 = b
        _Z1 += w * (z0 + c)
        z0 = c
        centroidPointCartesian(x0, y0, z0)
    }

    private fun centroidLineEnd() {
        currentPoint = ::centroidPoint
    }

    // See J. E. Brock, The Inertia Tensor for a Spherical Triangle,
    // J. Applied Mechanics 42, 239 (1975).
    private fun centroidRingStart() {
        currentPoint = ::centroidRingPointFirst
    }

    private fun centroidRingEnd() {
        centroidRingPoint(lambda00, phi00)
        currentPoint = ::centroidPoint
    }

    private fun centroidRingPointFirst(x: Double, y: Double) {
        lambda00 = x
        phi00 = y
        val lambda = x.toRadians()
        val phi = y.toRadians()
        val cosPhi = cos(phi)
        currentPoint = ::centroidRingPoint
        x0 = cosPhi * cos(lambda)
        y0 = cosPhi * sin(lambda)
        z0 = sin(phi)
        centroidPointCartesian(x0, y0, z0)
    }

    private fun centroidRingPoint(x: Double, y: Double) {
        val lambda = x.toRadians()
        val phi = y.toRadians()
        val cosPhi = cos(phi)
        val a = cosPhi * cos(lambda)
        val b = cosPhi * sin(lambda)
        val c = sin(phi)
        val cx = y0 * c - z0 * b
        val cy = z0 * a - x0 * c
        val cz = x0 * b - y0 * a
        val m = sqrt(cx * cx + cy * cy + cz * cz)
        val w = asin(m)                             // line weight = angle
        val v = if (m == .0) .0 else -w / m         // area weight multiplier
        _X2 += v * cx
        _Y2 += v * cy
        _Z2 += v * cz
        _W1 += w
        _X1 += w * (x0 + a)
        x0 = a
        _Y1 += w * (y0 + b)
        y0 = b
        _Z1 += w * (z0 + c)
        z0 = c
        centroidPointCartesian(x0, y0, z0)
    }
}