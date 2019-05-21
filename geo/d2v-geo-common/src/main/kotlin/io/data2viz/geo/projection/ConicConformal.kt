package io.data2viz.geo.projection

import io.data2viz.geo.Invertable
import io.data2viz.geo.Projectable
import io.data2viz.math.EPSILON
import io.data2viz.math.HALFPI
import io.data2viz.math.deg
import kotlin.math.*

fun conicConformalProjection() = conicConformalProjection {}

fun conicConformalProjection(init: ConicProjection.() -> Unit) = conicProjection(ConicConformalProjector()) {
    scale = 109.5
    parallels = arrayOf(30.0.deg, 30.0.deg)
    init()
}


fun tany(y: Double): Double {
    return tan((HALFPI + y) / 2);
}

class ConicConformalProjector : ConicProjectable, Projectable, Invertable {
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

    var cy0 = cos(phi0)
    var n = if (phi0.equals(phi1)) {
        sin(phi0)
    } else {
        log(cy0, cos(phi1)) / log(tany(phi1), tany(phi0))
    }
    var f = cy0 * (tany(phi0).pow(n)) / n
    private var isPossibleToUseBaseProjection = (n == 0.0 || n == Double.NaN)


    private fun recalculate() {

        cy0 = cos(phi0)
        n = if (phi0.equals(phi1)) {
            sin(phi0)
        } else {
            log(cy0, cos(phi1)) / log(tany(phi1), tany(phi0))
        }
        f = cy0 * (tany(phi0).pow(n)) / n
        isPossibleToUseBaseProjection = (n == 0.0 || n == Double.NaN)
    }

    val mercatorProjector = MercatorProjector()

    override fun invert(x: Double, y: Double): DoubleArray {
        return if (isPossibleToUseBaseProjection) {
            mercatorProjector.invert(x, y)
        } else {

            val fy = f - y
            val r = sign(n) * sqrt(x * x + fy * fy);
            return doubleArrayOf(
                atan2(x, abs(fy)) / n * sign(fy),
                2 * atan((f / r).pow(1 / n)) - HALFPI
            );
        }
    }

//    override fun project(x: Double, y: Double): DoubleArray {
//
//        return if (isPossibleToUseBaseProjection) {
//            mercatorProjector.project(x, y)
//        } else {
//            var newY = if (f > 0) {
//                if (y < -HALFPI + EPSILON) {
//                    -HALFPI + EPSILON;
//                } else {
//                    y
//                }
//            } else {
//                if (y > HALFPI - EPSILON) {
//                    HALFPI - EPSILON;
//                } else {
//                    y
//                }
//            }
//            var r = f / tany(newY).pow(n);
//            return doubleArrayOf(r * sin(n * x), f - r * cos(n * x));
//        }
//
//
//    }

    override fun projectLambda(lambda: Double, phi: Double): Double {
        return if (isPossibleToUseBaseProjection) {
            mercatorProjector.projectLambda(lambda, phi)
        } else {
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
    }

    override fun projectPhi(lambda: Double, phi: Double): Double {
        return if (isPossibleToUseBaseProjection) {
            mercatorProjector.projectPhi(lambda, phi)
        } else {
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


}