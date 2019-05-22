package io.data2viz.geo.projection

import io.data2viz.geo.projection.common.BaseConditionalProjector
import io.data2viz.geo.projection.common.Projector
import io.data2viz.math.EPSILON
import io.data2viz.math.HALFPI
import io.data2viz.math.deg
import kotlin.math.*

fun conicConformalProjection() = conicConformalProjection {}

fun conicConformalProjection(init: ConicProjection.() -> Unit) =
    conicProjection(ConicConformalBaseConditionalProjector()) {
        scale = 109.5
        parallels = arrayOf(30.0.deg, 30.0.deg)
        init()
    }


fun tany(y: Double): Double {
    return tan((HALFPI + y) / 2);
}

class ConicConformalBaseConditionalProjector(
    private val conicConformalProjector: ConicConformalProjector = ConicConformalProjector(),
    private val mercatorProjector: MercatorProjector = MercatorProjector()
) : ConicProjector, BaseConditionalProjector() {
    override var phi0: Double
        get() = conicConformalProjector.phi0
        set(value) {
            conicConformalProjector.phi0 = value
        }
    override var phi1: Double
        get() = conicConformalProjector.phi1
        set(value) {
            conicConformalProjector.phi1 = value
        }

    override val baseProjector: Projector
        get() = mercatorProjector
    override val nestedProjector: Projector
        get() = conicConformalProjector
    override val isNeedUseBaseProjector: Boolean
        get() = conicConformalProjector.isPossibleToUseProjector
}

class ConicConformalProjector : ConicProjector, Projector {


    override var phi0: Double = 0.0
        set(value) {
            field = value
            recalculate()
        }
    override var phi1: Double = io.data2viz.math.PI / 3.0
        set(value) {
            field = value
            recalculate()
        }

    private var cy0 = cy0()
    private var n = n()
    private var f = f()
    var isPossibleToUseProjector = isPossibleToUse()
        private set


    private fun recalculate() {

        cy0 = cy0()
        n = n()
        f = f()
        isPossibleToUseProjector = isPossibleToUse()
    }

    private fun isPossibleToUse() = (n == 0.0 || n == Double.NaN)

    private fun f() = cy0 * (tany(phi0).pow(n)) / n

    private fun n(): Double {
        return if (phi0.equals(phi1)) {
            sin(phi0)
        } else {
            log(cy0, cos(phi1)) / log(tany(phi1), tany(phi0))
        }
    }

    private fun cy0() = cos(phi0)


    override fun invertLambda(lambda: Double, phi: Double): Double {
        val fy = fy(phi)
        return intervalInvertLambda(lambda, fy)

    }

    override fun invertPhi(lambda: Double, phi: Double): Double {
        val fy = fy(phi)
        val rInvert = rInvert(lambda, fy);
        return internalInvertPhi(rInvert)

    }

    override fun invert(lambda: Double, phi: Double): DoubleArray {

        val fy = fy(phi)
        val rInvert = rInvert(lambda, fy);
        return doubleArrayOf(
            intervalInvertLambda(lambda, fy),
            internalInvertPhi(rInvert)
        );

    }

    private fun internalInvertPhi(rInvert: Double) = 2 * atan((f / rInvert).pow(1 / n)) - HALFPI

    private fun intervalInvertLambda(lambda: Double, fy: Double) = atan2(lambda, abs(fy)) / n * sign(fy)

    private fun rInvert(lambda: Double, fy: Double) = sign(n) * sqrt(lambda * lambda + fy * fy)

    override fun project(lambda: Double, phi: Double): DoubleArray {
        val convertedPhi = convertPhi(phi)
        val r = r(convertedPhi);
        return doubleArrayOf(
            internalProjectLambda(r, lambda),
            internalProjectPhi(r, lambda)
        );

    }

    override fun projectLambda(lambda: Double, phi: Double): Double {

        var convertedPhi = convertPhi(phi)
        var r = r(convertedPhi);
        return internalProjectLambda(r, lambda)

    }

    private fun internalProjectLambda(r: Double, lambda: Double) = r * sin(n * lambda)

    override fun projectPhi(lambda: Double, phi: Double): Double {
        var convertedPhi = convertPhi(phi)
        var r = r(convertedPhi);
        return internalProjectPhi(r, lambda)
    }

    private fun internalProjectPhi(r: Double, lambda: Double) = f - r * cos(n * lambda)

    private fun fy(phi: Double) = f - phi

    private fun r(convertedPhi: Double) = f / tany(convertedPhi).pow(n)

    private fun convertPhi(phi: Double): Double {
        return if (f > 0) {
            if (phi < -HALFPI + EPSILON) {
                -HALFPI + EPSILON;
            } else {
                phi
            }
        } else {
            if (phi > HALFPI - EPSILON) {
                HALFPI - EPSILON;
            } else {
                phi
            }
        }
    }
}

