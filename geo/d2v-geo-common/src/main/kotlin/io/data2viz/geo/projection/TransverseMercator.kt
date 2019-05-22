package io.data2viz.geo.projection


import io.data2viz.geo.projection.common.Projector
import io.data2viz.math.Angle
import io.data2viz.math.HALFPI
import io.data2viz.math.deg
import kotlin.math.atan
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.tan


fun transverseMercatorProjection() = transverseMercatorProjection {}

fun transverseMercatorProjection(init: TransverseMercatorProjection.() -> Unit) = TransverseMercatorProjection().also {

    it.rotate = arrayOf(0.deg, 0.deg, 90.deg)
    it.scale = 159.155
}.also(init)

class TransverseMercatorProjector() : Projector {

    override fun invert(lambda: Double, phi: Double) = doubleArrayOf(-phi, 2 * atan(exp(lambda)) - HALFPI)

    override fun projectLambda(lambda: Double, phi: Double): Double = ln(tan((HALFPI + phi) / 2))
    override fun projectPhi(lambda: Double, phi: Double): Double = -lambda

}

class TransverseMercatorProjection() : MercatorProjection(TransverseMercatorProjector()) {


    override var center: Array<Angle>
        get() {
            val it = super.center
            val t = it[0]
            it[0] = it[1]
            it[1] = -t
            return it
        }
        set(value) {
            val it = value
            val t = it[0]
            it[0] = -it[1]
            it[1] = t
            super.center = it
        }

    override var rotate: Array<Angle>
        get() {

            val original = super.rotate
            return if (original.size > 2) {
                arrayOf(original[0], original[1], original[2] - (90.0).deg)
            } else {

                arrayOf(original[0], original[1], (-90.0).deg)
            }
        }
        set(value) {
            val original = value
            super.rotate = if (original.size > 2) {
                arrayOf(original[0], original[1], original[2] + 90.0.deg)
            } else {
                arrayOf(original[0], original[1], (+90.0).deg)
            }
        }
}