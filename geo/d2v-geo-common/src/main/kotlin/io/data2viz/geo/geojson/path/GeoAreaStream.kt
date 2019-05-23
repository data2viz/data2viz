package io.data2viz.geo.geojson.path

import io.data2viz.geo.stream.Stream
import io.data2viz.geo.geojson.noop
import io.data2viz.geo.geojson.noop2
import io.data2viz.geo.geojson.stream
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.math.QUARTERPI
import io.data2viz.math.TAU
import io.data2viz.math.toRadians
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

/**
 * Returns the spherical area of the specified GeoJSON object in steradians.
 * This is the spherical equivalent of AreaStream.
 */
class GeoAreaStream : Stream {

    private var areaSum = .0
    internal var areaRingSum = .0

    private var lambda00 = Double.NaN
    private var phi00 = Double.NaN
    private var lambda0 = Double.NaN
    private var phi0 = Double.NaN
    private var cosPhi0 = Double.NaN
    private var sinPhi0 = Double.NaN

    private var currentPoint: (Double, Double) -> Unit = noop2
    private var currentLineStart: () -> Unit = noop
    private var currentLineEnd: () -> Unit = noop

    fun result(geo: GeoJsonObject): Double {
        areaSum = .0
        geo.stream(this)
        return areaSum * 2
    }

    override fun point(x: Double, y: Double, z: Double) = currentPoint(x, y)
    override fun lineStart() = currentLineStart()
    override fun lineEnd() = currentLineEnd()

    override fun polygonStart() {
        areaRingSum = .0
        currentLineStart = ::areaRingStart
        currentLineEnd = ::areaRingEnd
    }

    override fun polygonEnd() {
        currentLineStart = noop
        currentLineEnd = noop
        currentPoint = noop2
        areaSum += areaRingSum + if (areaRingSum < 0) TAU else .0
    }

    override fun sphere() {
        areaSum += TAU
    }

    private fun areaRingStart() {
        currentPoint = ::areaPointFirst
    }

    private fun areaPointFirst(x: Double, y: Double) {
        currentPoint = ::areaPoint
        lambda00 = x
        phi00 = y
        lambda0 = x.toRadians()
        phi0 = y.toRadians()

        val phi = y.toRadians() / 2.0 + QUARTERPI
        cosPhi0 = cos(phi)
        sinPhi0 = sin(phi)
    }

    private fun areaPoint(x: Double, y: Double) {
        val lambda = x.toRadians()
        val phi = y.toRadians() / 2.0 + QUARTERPI // half the angular distance from south pole

        // Spherical excess E for a spherical triangle with vertices: south pole,
        // previous point, current point.  Uses a formula derived from Cagnoli’s
        // theorem.  See Todhunter, Spherical Trig. (1871), Sec. 103, Eq. (2).
        val dLambda = lambda - lambda0
        val sdLambda = if (dLambda >= .0) 1.0 else -1.0
        val adLambda = sdLambda * dLambda
        val cosPhi = cos(phi)
        val sinPhi = sin(phi)
        val k = sinPhi0 * sinPhi
        val u = cosPhi0 * cosPhi + k * cos(adLambda)
        val v = k * sdLambda * sin(adLambda)
        areaRingSum += atan2(v, u)

        // Advance the previous points.
        lambda0 = lambda
        cosPhi0 = cosPhi
        sinPhi0 = sinPhi
    }

    private fun areaRingEnd() {
        areaPoint(lambda00, phi00)
    }
}