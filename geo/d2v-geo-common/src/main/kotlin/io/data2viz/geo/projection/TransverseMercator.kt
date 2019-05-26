package io.data2viz.geo.projection


import io.data2viz.geo.projection.common.NoCommonCalculationsProjector
import io.data2viz.geo.projection.common.Projector
import io.data2viz.math.Angle
import io.data2viz.math.HALFPI
import io.data2viz.math.deg
import kotlin.math.atan
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.tan


/**
 * @see TransverseMercatorProjector
 * @see TransverseMercatorProjection
 */
fun transverseMercatorProjection() = transverseMercatorProjection {}

/**
 * @see TransverseMercatorProjector
 * @see TransverseMercatorProjection
 */
fun transverseMercatorProjection(init: TransverseMercatorProjection.() -> Unit) = TransverseMercatorProjection().also {

    it.rotate(0.deg, 0.deg, 90.deg)
    it.scale = 159.155
}.also(init)


/**
 * @see MercatorProjector
 * @see TransverseMercatorProjector
 * @see TransverseMercatorProjection
 */
class TransverseMercatorProjector() : NoCommonCalculationsProjector {

    override fun invertLambda(lambda: Double, phi: Double): Double = -phi

    override fun invertPhi(lambda: Double, phi: Double): Double = 2 * atan(exp(lambda)) - HALFPI

    override fun projectLambda(lambda: Double, phi: Double): Double = ln(tan((HALFPI + phi) / 2))
    override fun projectPhi(lambda: Double, phi: Double): Double = -lambda

}


/**
 * The transverse spherical [MercatorProjection] projection.
 *
 * @see TransverseMercatorProjector
 */
class TransverseMercatorProjection() : MercatorProjection(TransverseMercatorProjector()) {

    override var centerLat: Angle
        get() = super.centerLon
        set(value) {
            super.centerLon = value
        }
    override var centerLon: Angle
        get() = -super.centerLat
        set(value) {
            super.centerLat = -value
        }

    override fun center(lat: Angle, lon: Angle) {
        super.center(-lon, lat)
    }

    override var rotateGamma: Angle
        get() = super.rotateGamma - 90.0.deg
        set(value) {
            super.rotateGamma = value + 90.0.deg
        }

    override fun rotate(lambda: Angle, phi: Angle, gamma: Angle?) {
        if (gamma != null) {
            super.rotate(lambda, phi, gamma + 90.0.deg)

        } else {
            super.rotate(lambda, phi, 90.0.deg)

        }
    }
}