package io.data2viz.geo.projection

import io.data2viz.geo.projection.common.BaseConditionalProjector
import io.data2viz.geo.projection.common.Projector
import io.data2viz.math.EPSILON
import io.data2viz.math.HALFPI
import io.data2viz.math.deg
import kotlin.math.*

fun conicConformalProjection() = conicConformalProjection {}

fun conicConformalProjection(init: ConicProjection.() -> Unit) = conicProjection(ConicConformalBaseConditionalProjector()) {
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
) : ConicProjectable, BaseConditionalProjector() {
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

class ConicConformalProjector : ConicProjectable, Projector {


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


    override fun invert(lambda: Double, phi: Double): DoubleArray {

        val fy = f - phi
        val r = sign(n) * sqrt(lambda * lambda + fy * fy);
        return doubleArrayOf(
            atan2(lambda, abs(fy)) / n * sign(fy),
            2 * atan((f / r).pow(1 / n)) - HALFPI
        );

    }

    override fun project(lambda: Double, phi: Double): DoubleArray {
        var newY = if (f > 0) {
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
        var r = f / tany(newY).pow(n);
        return doubleArrayOf(r * sin(n * lambda), f - r * cos(n * lambda));

    }

    override fun projectLambda(lambda: Double, phi: Double): Double {

        var newY = if (f > 0) {
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
        var r = f / tany(newY).pow(n);
        return r * sin(n * lambda)

    }

    override fun projectPhi(lambda: Double, phi: Double): Double {
        var newY = if (f > 0) {
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
        var r = f / tany(newY).pow(n);
        return f - r * cos(n * lambda)
    }
}

