package io.data2viz.geo.projection

import io.data2viz.geo.projection.common.BaseConditionalProjector
import io.data2viz.geo.projection.common.Projector
import io.data2viz.math.EPSILON
import io.data2viz.math.deg
import kotlin.math.*

/**
 * The [ConicEqualAreaProjector]. See also conic.parallels.
 * For some parallels values use [CylindricalEqualAreaProjector]
 *
 * @see BaseConditionalProjector
 */
fun conicEqualAreaProjection(init: ConicProjection.() -> Unit = {}) =
    conicProjection(ConicEqualAreaBaseConditionalProjector()) {
        scale = 155.424
        center(0.0.deg, 33.6442.deg)
        init()
    }


internal class ConicEqualAreaBaseConditionalProjector(
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
        n = n()
        c = c()
        r0 = r0()
        isPossibleToUseProjector = isPossibleToUse()
    }

    private fun isPossibleToUse() = abs(n) < EPSILON

    private fun r0() = sqrt(c) / n

    private fun c() = 1 + sy0 * (2 * n - sy0)

    private fun n() = (sy0 + sin(phi1)) / 2

    private fun sy0() = sin(phi0)

    override fun invert(x: Double, y: Double): DoubleArray {
        val r0y = r0y(y)
        return doubleArrayOf(
            atan2(x, abs(r0y)) / n * sign(r0y),
            asin((c - (x * x + r0y * r0y) * n * n) / (2 * n))
        )
    }

    override fun project(lambda: Double, phi: Double): DoubleArray {
        val r = r(phi)

        return doubleArrayOf(
            r * sin(lambda * n),
            r0 - r * cos(lambda * n)
        )
    }

    private fun r0y(phi: Double) = r0 - phi

    private fun r(phi: Double) = sqrt(c - 2 * n * sin(phi)) / n
}