package io.data2viz.geo.projection

import io.data2viz.geo.Invertable
import io.data2viz.geo.Projectable
import io.data2viz.math.EPSILON
import io.data2viz.math.deg
import kotlin.math.*

fun conicEqualAreaProjection() = conicEqualAreaProjection {}

fun conicEqualAreaProjection(init: ConicProjection.() -> Unit) = conicProjection(ConicEqualAreaProjector()) {
    scale = 155.424
    center = arrayOf(0.0.deg, 33.6442.deg)
    init()
}

class ConicEqualAreaProjector : ConicProjectable, Projectable, Invertable {

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

    private var sy0 = sin(phi0)
    private var n = (sy0 + sin(phi1)) / 2;
    private var c = 1 + sy0 * (2 * n - sy0)
    private var r0 = sqrt(c) / n;
    private var isPossibleToUseBaseProjection = abs(n) < EPSILON

    private fun recalculate() {

        sy0 = sin(phi0)
        n = (sy0 + sin(phi1)) / 2;
        c = 1 + sy0 * (2 * n - sy0)
        r0 = sqrt(c) / n;
        cylindricalEqualProjector.phi0 = phi0
        isPossibleToUseBaseProjection = abs(n) < EPSILON
    }

    val cylindricalEqualProjector = CylindricalEqualAreaProjector(phi0)

    override fun invert(x: Double, y: Double): DoubleArray {
        return if (isPossibleToUseBaseProjection) {
            cylindricalEqualProjector.invert(x, y)
        } else {
            var r0y = r0 - y;
            doubleArrayOf(atan2(x, abs(r0y)) / n * sign(r0y), asin((c - (x * x + r0y * r0y) * n * n) / (2 * n)))

        }
    }


//    override fun project(lambda: Double, phi: Double): DoubleArray {
//
//        return if (isPossibleToUseBaseProjection) {
//            cylindricalEqualProjector.project(lambda, phi)
//        } else {
//            var r = sqrt(c - 2 * n * sin(phi)) / n
//            val lambdaN = lambda * n
//            doubleArrayOf(r * sin(lambda), r0 - r * cos(lambdaN));
//        }
//
//
//    }

    override fun projectLambda(lambda: Double, phi: Double): Double {
        return if (isPossibleToUseBaseProjection) {
            cylindricalEqualProjector.projectLambda(lambda, phi)
        } else {

            var r = sqrt(c - 2 * n * sin(phi)) / n
            val lambdaN = lambda * n
            r * sin(lambda * n)
        }
    }

    override fun projectPhi(lambda: Double, phi: Double): Double {
        return if (isPossibleToUseBaseProjection) {
            cylindricalEqualProjector.projectPhi(lambda, phi)
        } else {
            var r = sqrt(c - 2 * n * sin(phi)) / n
            val lambdaN = lambda * n
            r0 - r * cos(lambdaN)
        }
    }
}