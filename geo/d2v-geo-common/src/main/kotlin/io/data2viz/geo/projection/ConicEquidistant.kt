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


    override fun invertLambda(lambda: Double, phi: Double): Double {
        val gy = gy(phi)

        return internalInvertLambda(lambda, gy)

    }

    override fun invertPhi(lambda: Double, phi: Double): Double {
        val gy = gy(phi)
        return internalInvertPhi(lambda, gy)

    }

    override fun invert(lambda: Double, phi: Double): DoubleArray {

        val gy = gy(phi)
        return doubleArrayOf(
            internalInvertLambda(lambda, gy),
            internalInvertPhi(lambda, gy)
        )

    }

    private fun internalInvertPhi(lambda: Double, gy: Double) = g - sign(n) * sqrt(lambda * lambda + gy * gy)

    private fun internalInvertLambda(lambda: Double, gy: Double) = atan2(lambda, abs(gy)) / n * sign(gy)

    override fun project(lambda: Double, phi: Double): DoubleArray {

        val gy = gy(phi)
        val nx = nx(lambda)
        return doubleArrayOf(
            internalProjectLambda(gy, nx),
            internalProjectPhi(gy, nx)
        );

    }

    override fun projectLambda(lambda: Double, phi: Double): Double {

        val gy = gy(phi)
        val nx = nx(lambda)
        return internalProjectLambda(gy, nx)

    }

    private fun internalProjectLambda(gy: Double, nx: Double) = gy * sin(nx)

    override fun projectPhi(lambda: Double, phi: Double): Double {

        val gy = gy(phi)
        val nx = nx(lambda)
        return internalProjectPhi(gy, nx)

    }

    private fun internalProjectPhi(gy: Double, nx: Double) = g - gy * cos(nx)

    private fun nx(lambda: Double) = n * lambda

    private fun gy(phi: Double) = g - phi
}