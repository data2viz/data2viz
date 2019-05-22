package io.data2viz.geo.projection

import io.data2viz.geo.ConditionalProjector
import io.data2viz.geo.Projector
import io.data2viz.math.EPSILON
import io.data2viz.math.deg
import kotlin.math.*

fun conicEqualAreaProjection() = conicEqualAreaProjection {}

fun conicEqualAreaProjection(init: ConicProjection.() -> Unit) = conicProjection(ConicEqualAreaConditionalProjector()) {
    scale = 155.424
    center = arrayOf(0.0.deg, 33.6442.deg)
    init()
}

class ConicEqualAreaConditionalProjector(
    private val conicEqualAreaProjector: ConicEqualAreaProjector = ConicEqualAreaProjector(),
    private val cylindricalEqualAreaProjector: CylindricalEqualAreaProjector = CylindricalEqualAreaProjector(
        conicEqualAreaProjector.phi0
    )
) : ConicProjectable, ConditionalProjector() {
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


class ConicEqualAreaProjector : ConicProjectable, Projector {

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


    override fun invert(lambda: Double, phi: Double): DoubleArray {
        var r0y = r0 - phi;
        return doubleArrayOf(atan2(lambda, abs(r0y)) / n * sign(r0y), asin((c - (lambda * lambda + r0y * r0y) * n * n) / (2 * n)))
    }


    override fun projectLambda(lambda: Double, phi: Double): Double {
        var r = sqrt(c - 2 * n * sin(phi)) / n
        return r * sin(lambda * n)

    }

    override fun projectPhi(lambda: Double, phi: Double): Double {

        var r = sqrt(c - 2 * n * sin(phi)) / n
        val lambdaN = lambda * n
        return r0 - r * cos(lambdaN)

    }
}