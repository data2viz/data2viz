package io.data2viz.geo.projection

import io.data2viz.math.EPSILON
import io.data2viz.math.deg
import kotlin.math.*


class ConicEqualAreaProjector : ConicProjectable, ProjectableInvertable {


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

//

    private fun recalculate() {

        sy0 = sin(phi0)
        n = (sy0 + sin(phi1)) / 2;
        c = 1 + sy0 * (2 * n - sy0)
        r0 = sqrt(c) / n;
        cylindricalEqualProjector.phi0 = phi0
        isPossibleToUseBaseProjection = abs(n) < EPSILON

//
//        println("ConicEqualAreaProjector isPossibleToUseBaseProjection = $isPossibleToUseBaseProjection n = $n")
//
//        println("""ConicEqualAreaProjector
//            phi0 =$phi0
//            phi1 =$phi1
//            sy0 = $sy0
//            c = $c
//            r0 = $0
//            isPossibleToUseBaseProjection = $isPossibleToUseBaseProjection """.trimIndent())

    }



//    var sy0 = sin(y0), n = (sy0 + sin(y1)) / 2;
//
//    // Are the parallels symmetrical around the Equator?
//    if (abs(n) < epsilon) return cylindricalEqualAreaRaw(y0);
//
//    var c = 1 + sy0 * (2 * n - sy0), r0 = sqrt(c) / n;
//
//    function project(x, y) {
//        var r = sqrt(c - 2 * n * sin(y)) / n;
//        return [r * sin(x *= n), r0 - r * cos(x)];
//    }
//
//    project.invert = function(x, y) {
//        var r0y = r0 - y;
//        return [atan2(x, abs(r0y)) / n * sign(r0y), asin((c - (x * x + r0y * r0y) * n * n) / (2 * n))];
//    };
//
//    return project;


    // TODO refactor
    val cylindricalEqualProjector = CylindricalEqualAreaProjector(phi0)

    override fun invert(x: Double, y: Double): DoubleArray {
        return if (isPossibleToUseBaseProjection) {
            cylindricalEqualProjector.invert(x, y)
        } else {
            var r0y = r0 - y;
            doubleArrayOf(atan2(x, abs(r0y)) / n * sign(r0y), asin((c - (x * x + r0y * r0y) * n * n) / (2 * n)))

        }
    }


    override fun project(lambda: Double, phi: Double): DoubleArray {

        return if (isPossibleToUseBaseProjection) {
            cylindricalEqualProjector.project(lambda, phi)
        } else {
            var r = sqrt(c - 2 * n * sin(phi)) / n
            // TODO: check
//            return [r * sin(x *= n), r0 - r * cos(x)];
            val lambdaN = lambda * n
            doubleArrayOf(r * sin(lambda), r0 - r * cos(lambdaN));
        }


    }

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

fun conicEqualAreaProjection() = conicEqualAreaProjection {

}

fun conicEqualAreaProjection(init: ConicProjection.() -> Unit) = conicProjection(ConicEqualAreaProjector()) {
    scale = 155.424
    center = arrayOf(0.0.deg, 33.6442.deg)
    init()
}