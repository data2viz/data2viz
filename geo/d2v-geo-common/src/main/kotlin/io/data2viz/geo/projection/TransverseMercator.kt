package io.data2viz.geo.projection

import io.data2viz.math.Angle
import io.data2viz.math.HALFPI
import io.data2viz.math.deg
import kotlin.math.atan
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.tan


fun transverseMercatorProjection() = transverseMercatorProjection {

}

fun transverseMercatorProjection(init: Projection.() -> Unit) = projection(TransverseMercatorProjection()) {

    scale = 159.155
    rotate = arrayOf(0.deg, 0.deg, 90.deg)
    init()
}

class TransverseMercatorRawProjector : ProjectableInvertable {
    override fun project(lambda: Double, phi: Double) = doubleArrayOf(ln(tan((HALFPI + phi) / 2)), -lambda)
    override fun invert(x: Double, y: Double) = doubleArrayOf(-y, 2 * atan(exp(x)) - HALFPI)
    override fun projectLambda(lambda: Double, phi: Double): Double = ln(tan((HALFPI + phi) / 2))

    override fun projectPhi(lambda: Double, phi: Double): Double = -lambda
}

class TransverseMercatorProjection : MercatorProjection(TransverseMercatorRawProjector()) {

    override var center: Array<Angle>
        get() = reverse(super.center)
        set(value) {
            super.center = reverse(value)
        }

    private fun reverse(it: Array<Angle>): Array<Angle> {
        val t = it[0]
        it[0] = it[1]
        it[1] = t
        return it
    }

    // TODO: box/unbox perfroamnce
    override var rotate: Array<Angle>
        get() {

            val original = super.rotate
            return if (original.size > 2) {
                arrayOf(original[0], original[1], original[2] + 90.0.deg)
            } else {

                arrayOf(original[0], original[1], 90.0.deg)
            }
        }
        set(value) {
            val original = value
            super.rotate = if (original.size > 2) {
                arrayOf(original[0], original[1],original[2] - 90.0.deg)
            } else {

                arrayOf(original[0], original[1], (-90.0).deg)
            }

        }

}