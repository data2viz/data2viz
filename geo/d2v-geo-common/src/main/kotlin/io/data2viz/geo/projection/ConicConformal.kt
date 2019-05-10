package io.data2viz.geo.projection

import io.data2viz.math.EPSILON
import io.data2viz.math.HALFPI
import io.data2viz.math.deg
import kotlin.math.*

fun tany(y: Double): Double {
    return tan((HALFPI + y) / 2);
}


class ConicConformalProjector(
    val y0: Double = 0.0,
    val y1: Double = io.data2viz.math.PI / 3.0
) : ProjectableInvertable {

    val cy0: Double = cos(y0)
    val n = if (y0 == y1) {
        sin(y0)
    } else {
        log(cy0, cos(y1)) / log(tany(y1), tany(y0))
    }
    val f = cy0 * tany(y0).pow(n) / n


    // TODO refactor
    val mercatorProjector = MercatorProjector()

    override fun invert(x: Double, y: Double): DoubleArray {
        return if (n != 0.0) {
            mercatorProjector.invert(x, y)
        } else {

            val fy = f - y
            val r = sign(n) * sqrt(x * x + fy * fy);
            return doubleArrayOf(atan2(x, abs(fy)) / n * sign(fy), 2 * atan((f / r).pow(1 / n)) - HALFPI);
        }
    }


    override fun project(x: Double, y: Double): DoubleArray {

        return if (n != 0.0) {
            mercatorProjector.project(x, y)
        } else {
            var newY = if (f > 0) {
                if (y < -HALFPI + EPSILON) {
                    -HALFPI + EPSILON;
                } else {
                    y
                }
            } else {
                if (y > HALFPI - EPSILON) {
                    HALFPI - EPSILON;
                } else {
                    y
                }
            }
            var r = f / tany(newY).pow(n);
            return doubleArrayOf(r * sin(n * x), f - r * cos(n * x));
        }


    }

    override fun projectLambda(x: Double, y: Double): Double {
        return if (n != 0.0) {
            mercatorProjector.projectLambda(x, y)
        } else {
            var newY = if (f > 0) {
                if (y < -HALFPI + EPSILON) {
                    -HALFPI + EPSILON;
                } else {
                    y
                }
            } else {
                if (y > HALFPI - EPSILON) {
                    HALFPI - EPSILON;
                } else {
                    y
                }
            }
            var r = f / tany(newY).pow(n);
            return r * sin(n * x)
        }
    }

    override fun projectPhi(x: Double, y: Double): Double {
        return if (n != 0.0) {
            mercatorProjector.projectPhi(x, y)
        } else {
            var newY = if (f > 0) {
                if (y < -HALFPI + EPSILON) {
                    -HALFPI + EPSILON;
                } else {
                    y
                }
            } else {
                if (y > HALFPI - EPSILON) {
                    HALFPI - EPSILON;
                } else {
                    y
                }
            }
            var r = f / tany(newY).pow(n);
            return f - r * cos(n * x)
        }
    }

}


fun conicConformalProjection(init: ConicProjection.() -> Unit) = conicProjection(ConicConformalProjector()) {
    scale = 109.5
    parallels = arrayOf(30.0.deg, 33.0.deg)
    init()
}

