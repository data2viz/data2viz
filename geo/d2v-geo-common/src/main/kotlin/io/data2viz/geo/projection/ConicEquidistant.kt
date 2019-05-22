package io.data2viz.geo.projection

import io.data2viz.geo.projection.common.BaseConditionalProjector
import io.data2viz.geo.projection.common.Projector
import io.data2viz.math.EPSILON
import io.data2viz.math.deg
import kotlin.math.*

fun conicEquidistantProjection() = conicEquidistantProjection {}

fun conicEquidistantProjection(init: ConicProjection.() -> Unit) =
    conicProjection(ConicEquidistantBaseConditionalProjector()) {
        scale = 131.154
        center = arrayOf(0.0.deg, 13.9389.deg)
        init()
    }

class ConicEquidistantBaseConditionalProjector(
    private val conicEquidistantProjector: ConicEquidistantProjector = ConicEquidistantProjector(),
    private val equirectangularProjector: EquirectangularProjector = EquirectangularProjector()
) : ConicProjector, BaseConditionalProjector() {

    override var phi0: Double
        get() = conicEquidistantProjector.phi0
        set(value) {
            conicEquidistantProjector.phi0 = value
        }
    override var phi1: Double
        get() = conicEquidistantProjector.phi1
        set(value) {
            conicEquidistantProjector.phi1 = value
        }

    override val baseProjector: Projector
        get() = equirectangularProjector
    override val nestedProjector: Projector
        get() = conicEquidistantProjector
    override val isNeedUseBaseProjector: Boolean
        get() = conicEquidistantProjector.isPossibleToUseProjector

}

class ConicEquidistantProjector : ConicProjector, Projector {

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
    private var g = g()
    var isPossibleToUseProjector = isPossibleToUse()
        private set

    private fun recalculate() {
        cy0 = cy0()
        n = n()
        g = g()
        isPossibleToUseProjector = isPossibleToUse()

    }

    private fun isPossibleToUse() = abs(n) < EPSILON

    private fun g() = cy0 / n + phi0

    private fun n(): Double {
        return if (phi0 == phi1) {
            sin(phi0)
        } else {
            (cy0 - cos(phi1)) / (phi1 - phi0)
        }
    }

    private fun cy0() = cos(phi0)


    override fun invert(lambda: Double, phi: Double): DoubleArray {

        var gy = g - phi;
        return doubleArrayOf(atan2(lambda, abs(gy)) / n * sign(gy), g - sign(n) * sqrt(lambda * lambda + gy * gy))

    }

    override fun project(lambda: Double, phi: Double): DoubleArray {

        val gy = g - phi
        val nx = n * lambda;
        return doubleArrayOf(gy * sin(nx), g - gy * cos(nx));

    }

    override fun projectLambda(lambda: Double, phi: Double): Double {

        val gy = g - phi
        val nx = n * lambda
        return gy * sin(nx)

    }

    override fun projectPhi(lambda: Double, phi: Double): Double {

        val gy = g - phi
        val nx = n * lambda
        return g - gy * cos(nx)

    }
}