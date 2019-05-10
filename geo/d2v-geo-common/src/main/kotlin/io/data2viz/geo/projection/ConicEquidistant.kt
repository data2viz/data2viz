package io.data2viz.geo.projection

import io.data2viz.math.EPSILON
import io.data2viz.math.HALFPI
import io.data2viz.math.deg
import kotlin.math.*



class ConicEquidistantProjector(val y0: Double = 0.0,
                                val y1: Double = io.data2viz.math.PI / 3.0) : ProjectableInvertable {
    val cy0: Double = cos(y0)
    val n = if (y0 == y1) {
        sin(y0)
    } else {
        (cy0 - cos(y1)) / (y1 - y0)
    }
    val g = cy0 / n + y0;

    // TODO refactor
    val baseProjector = EquirectangularProjector()

    override fun invert(x: Double, y: Double): DoubleArray {
        return if (abs(n) < EPSILON) {
            baseProjector.invert(x, y)
        } else {
            var gy = g - y;
            return doubleArrayOf(atan2(x, abs(gy)) / n * sign(gy), g - sign(n) * sqrt(x * x + gy * gy))
        }
    }


    override fun project(x: Double, y: Double): DoubleArray {

        return if (abs(n) < EPSILON) {
            baseProjector.project(x, y)
        } else {
            val gy = g - y
            val nx = n * x;
            return doubleArrayOf(gy * sin(nx), g - gy * cos(nx));
        }


    }

    override fun projectLambda(x: Double, y: Double): Double {
        return if (abs(n) < EPSILON) {
            baseProjector.projectLambda(x, y)
        } else {
            val gy = g - y
            val nx = n * x;
            return gy * sin(nx)
        }
    }

    override fun projectPhi(x: Double, y: Double): Double {
        return if (abs(n) < EPSILON) {
            baseProjector.projectPhi(x, y)
        } else {
            val gy = g - y
            val nx = n * x;
            return g - gy * cos(nx)
        }
    }

}


fun conicEquidistantProjection(init: ConicProjection.() -> Unit) = conicProjection(ConicEquidistantProjector()) {
    scale = 131.154
    center = arrayOf(0.0.deg, 13.9389.deg)
    init()
}
