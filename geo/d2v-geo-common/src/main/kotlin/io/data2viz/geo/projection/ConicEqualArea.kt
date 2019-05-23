package io.data2viz.geo.projection

import io.data2viz.geo.projection.common.BaseConditionalProjector
import io.data2viz.geo.projection.common.Projector
import io.data2viz.math.EPSILON
import io.data2viz.math.deg
import kotlin.math.*

fun conicEqualAreaProjection() = conicEqualAreaProjection {}

fun conicEqualAreaProjection(init: ConicProjection.() -> Unit) =
    conicProjection(ConicEqualAreaBaseConditionalProjector()) {
        scale = 155.424
        center(0.0.deg, 33.6442.deg)
        init()
    }

/**
 * The Albers’ equal-area conic projection. See also conic.parallels.
 */
class ConicEqualAreaBaseConditionalProjector(
    private val conicEqualAreaProjector: ConicEqualAreaProjector = ConicEqualAreaProjector(),
    private val cylindricalEqualAreaProjector: CylindricalEqualAreaProjector = CylindricalEqualAreaProjector(
        conicEqualAreaProjector.phi0
    )
) : ConicProjector, BaseConditionalProjector() {
    override var phi0: Double
        get() = conicEqualAreaProjector.phi0
        set(value) {
            conicEqualAreaProjector.phi0 = value
        }
    override var phi1: Double
        get() = conicEqualAreaProjector.phi1
        set(value) {
            conicEqualAreaProjector.phi1 = value
        }

    override val baseProjector: Projector
        get() = cylindricalEqualAreaProjector
    override val nestedProjector: Projector
        get() = conicEqualAreaProjector
    override val isNeedUseBaseProjector: Boolean
        get() = conicEqualAreaProjector.isPossibleToUseProjector
}


class ConicEqualAreaProjector : ConicProjector, Projector {

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

    private var sy0 = sy0()
    private var n = n()
    private var c = c()
    private var r0 = r0()
    var isPossibleToUseProjector = isPossibleToUse()
        private set

    private fun recalculate() {
        sy0 = sy0()
        n = n();
        c = c()
        r0 = r0();
        isPossibleToUseProjector = isPossibleToUse()
    }

    private fun isPossibleToUse() = abs(n) < EPSILON

    private fun r0() = sqrt(c) / n

    private fun c() = 1 + sy0 * (2 * n - sy0)

    private fun n() = (sy0 + sin(phi1)) / 2

    private fun sy0() = sin(phi0)


    override fun invertLambda(lambda: Double, phi: Double): Double {
        var r0y = r0y(phi)
        return internalInvertLambda(lambda, r0y)
    }

    override fun invertPhi(lambda: Double, phi: Double): Double {
        var r0y = r0y(phi)
        return internalInvertPhi(lambda, r0y)
    }

    override fun invert(lambda: Double, phi: Double): DoubleArray {
        var r0y = r0y(phi)
        return doubleArrayOf(
            internalInvertLambda(lambda, r0y),
            internalInvertPhi(lambda, r0y)
        )
    }

    private fun internalInvertPhi(lambda: Double, r0y: Double) =
        asin((c - (lambda * lambda + r0y * r0y) * n * n) / (2 * n))

    private fun internalInvertLambda(lambda: Double, r0y: Double) = atan2(lambda, abs(r0y)) / n * sign(r0y)

    override fun project(lambda: Double, phi: Double): DoubleArray {
        var r = r(phi)
        val lambdaN = lambda * n
        return doubleArrayOf(
            internalProjectLambda(r, lambda),
            internalProjectPhi(r, lambdaN)
        )
    }

    override fun projectLambda(lambda: Double, phi: Double): Double {
        var r = r(phi)
        return internalProjectLambda(r, lambda)

    }

    private fun internalProjectLambda(r: Double, lambda: Double) = r * sin(lambda * n)

    override fun projectPhi(lambda: Double, phi: Double): Double {

        var r = r(phi)
        val lambdaN = lambda * n
        return internalProjectPhi(r, lambdaN)

    }

    private fun internalProjectPhi(r: Double, lambdaN: Double) = r0 - r * cos(lambdaN)

    private fun r0y(phi: Double) = r0 - phi

    private fun r(phi: Double) = sqrt(c - 2 * n * sin(phi)) / n
}