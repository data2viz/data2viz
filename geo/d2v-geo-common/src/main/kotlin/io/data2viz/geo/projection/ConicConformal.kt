package io.data2viz.geo.projection

import io.data2viz.math.EPSILON
import io.data2viz.math.HALFPI
import io.data2viz.math.deg
import kotlin.math.*

fun tany(y: Double): Double {
    return tan((HALFPI + y) / 2);
}


class ConicConformalProjector : ConicProjectable, ProjectableInvertable {



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
        log(cy0, cos(phi1)) / log(tany(phi1) , tany(phi0))
    }
    var f = cy0 * (tany(phi0).pow(n)) / n
    private var isPossibleToUseBaseProjection = (n == 0.0 || n == Double.NaN)



    private fun recalculate() {

        cy0 = cos(phi0)
        n = if (phi0.equals(phi1)) {
            sin(phi0)
        } else {
            log(cy0, cos(phi1)) / log(tany(phi1) , tany(phi0))
        }
        f = cy0 * (tany(phi0).pow(n)) / n
        isPossibleToUseBaseProjection = (n == 0.0 || n == Double.NaN)
//        isPossibleToUseBaseProjection = false

//        println("""ConicConformalProjector
//            phi0 =$phi0
//            phi1 =$phi1
//            n = $n
//            f = $f
//            isPossibleToUseBaseProjection = $isPossibleToUseBaseProjection """.trimIndent())

    }


    // TODO refactor
    val mercatorProjector = MercatorProjector()

    override fun invert(x: Double, y: Double): DoubleArray {
        return if (isPossibleToUseBaseProjection) {
            mercatorProjector.invert(x, y)
        } else {

            val fy = f - y
            val r = sign(n) * sqrt(x * x + fy * fy);
            return doubleArrayOf(
                atan2(x, abs(fy)) / n * sign(fy),
                2 * atan((f / r).pow(1 / n)) - HALFPI);
        }
    }

    //    export function conicConformalRaw(y0, y1) {
//        var cy0 = cos(y0),
//        n = y0 === y1 ? sin(y0) : log(cy0 / cos(y1)) / log(tany(y1) / tany(y0)),
//        f = cy0 * pow(tany(y0), n) / n;
//
//        if (!n) return mercatorRaw;
//
//        function project(x, y) {
//            if (f > 0) { if (y < -halfPi + epsilon) y = -halfPi + epsilon; }
//            else { if (y > halfPi - epsilon) y = halfPi - epsilon; }
//            var r = f / pow(tany(y), n);
//            return [r * sin(n * x), f - r * cos(n * x)];
//        }
//
//        project.invert = function(x, y) {
//            var fy = f - y, r = sign(n) * sqrt(x * x + fy * fy);
//            return [atan2(x, abs(fy)) / n * sign(fy), 2 * atan(pow(f / r, 1 / n)) - halfPi];
//        };
//
//        return project;
//    }


    override fun project(x: Double, y: Double): DoubleArray {

        return if (isPossibleToUseBaseProjection) {
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
        return if (isPossibleToUseBaseProjection) {
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
        return if (isPossibleToUseBaseProjection) {
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


fun conicConformalProjection() = conicConformalProjection {
}

fun conicConformalProjection(init: ConicProjection.() -> Unit) = conicProjection(ConicConformalProjector()) {
    scale = 109.5
    parallels = arrayOf(30.0.deg, 30.0.deg)
    init()
}
