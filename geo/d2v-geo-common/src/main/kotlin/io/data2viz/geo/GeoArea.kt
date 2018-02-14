package io.data2viz.geo

import io.data2viz.geo.projection.Stream
import io.data2viz.math.toRadians
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

const val quarterPi = PI / 4.0

class GeoArea : Stream {

    private var areaSum = .0
    private var areaRingSum = .0
    private var lambda00 = Double.NaN
    private var phi00 = Double.NaN
    private var lambda0 = Double.NaN
    private var phi0 = Double.NaN
    private var cosPhi0 = Double.NaN
    private var sinPhi0 = Double.NaN

    private val noop: () -> Unit = { }
    private val noop3: (Double, Double, Double) -> Unit = { x, y, z -> }

    private var currentPoint: (Double, Double, Double) -> Unit = noop3
    private var currentLineStart: () -> Unit = noop
    private var currentLineEnd: () -> Unit = noop

    fun result(geo:GeoJSON): Double {
        areaSum = .0
        stream(geo, this)
        return areaSum * 2
    }

    override fun point(x: Double, y: Double, z: Double) = currentPoint(x, y, z)
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
        currentPoint = noop3
        areaSum += areaRingSum + if (areaRingSum < 0) (2.0 * PI) else .0
    }

    override fun sphere() {
        areaSum += 2.0 * PI
    }

    private fun areaRingStart() {
        currentPoint = ::areaPointFirst
    }

    private fun areaPointFirst(x: Double, y: Double, z: Double) {
        currentPoint = ::areaPoint
        lambda00 = x
        phi00 = y
        lambda0 = x.toRadians()
        phi0 = y.toRadians()

        val phi = y.toRadians() / 2.0 + quarterPi
        cosPhi0 = cos(phi)
        sinPhi0 = sin(phi)
    }

    private fun areaPoint(x: Double, y: Double, z: Double) {
        val lambda = x.toRadians()
        val phi = y.toRadians() / 2.0 + quarterPi // half the angular distance from south pole

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
        areaPoint(lambda00, phi00, .0)
    }
}