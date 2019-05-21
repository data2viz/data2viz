package io.data2viz.geo.projection

import io.data2viz.geo.Invertable
import io.data2viz.geo.Projectable
import io.data2viz.math.EPSILON
import io.data2viz.math.deg
import kotlin.math.*

fun conicEquidistantProjection() = conicEquidistantProjection {}

fun conicEquidistantProjection(init: ConicProjection.() -> Unit) = conicProjection(ConicEquidistantProjector()) {
    scale = 131.154
    center = arrayOf(0.0.deg, 13.9389.deg)
    init()
}

class ConicEquidistantProjector : ConicProjectable, Projectable, Invertable {

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

    private var cy0: Double = cos(phi0)
    private var n = if (phi0 == phi1) {
        sin(phi0)
    } else {
        (cy0 - cos(phi1)) / (phi1 - phi0)
    }
    private var g = cy0 / n + phi0;
    var isPossibleToUseBaseProjection = abs(n) < EPSILON


    private fun recalculate() {
        cy0 = cos(phi0)
        n = if (phi0 == phi1) {
            sin(phi0)
        } else {
            (cy0 - cos(phi1)) / (phi1 - phi0)
        }
        g = cy0 / n + phi0;
        isPossibleToUseBaseProjection = abs(n) < EPSILON

    }

    val baseProjector = EquirectangularProjector()

    override fun invert(x: Double, y: Double): DoubleArray {
        return if (isPossibleToUseBaseProjection) {
            baseProjector.invert(x, y)
        } else {
            var gy = g - y;
            return doubleArrayOf(atan2(x, abs(gy)) / n * sign(gy), g - sign(n) * sqrt(x * x + gy * gy))
        }
    }

//    override fun project(x: Double, y: Double): DoubleArray {
//
//        return if (isPossibleToUseBaseProjection) {
//            baseProjector.project(x, y)
//        } else {
//            val gy = g - y
//            val nx = n * x;
//            return doubleArrayOf(gy * sin(nx), g - gy * cos(nx));
//        }
//    }

    override fun projectLambda(lambda: Double, phi: Double): Double {
        return if (isPossibleToUseBaseProjection) {
            baseProjector.projectLambda(lambda, phi)
        } else {
            val gy = g - phi
            val nx = n * lambda;
            return gy * sin(nx)
        }
    }

    override fun projectPhi(lambda: Double, phi: Double): Double {
        return if (isPossibleToUseBaseProjection) {
            baseProjector.projectPhi(lambda, phi)
        } else {
            val gy = g - phi
            val nx = n * lambda;
            return g - gy * cos(nx)
        }
    }
}