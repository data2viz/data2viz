package io.data2viz.geo.projection

import io.data2viz.geo.projection.common.Projection
import io.data2viz.test.TestBase
import io.data2viz.test.matchers.ToleranceMatcher
import io.data2viz.test.matchers.epsilon
import kotlin.math.abs

internal fun pt(a: Double, b: Double) = arrayOf(a, b)
internal fun pt(a: Double, b: Double, c: Double) = arrayOf(a, b, c)

class ProjectionTests : TestBase() {

    fun checkProjection(projection: Projection, lambda: Double, phi: Double, result: DoubleArray) {
        checkProject(projection, lambda, phi, result)
        // TODO: check invert too
//        checkInvert(projection, result, lambda, phi)
    }

    private fun checkProject(
        projection: Projection,
        lambda: Double,
        phi: Double,
        result: DoubleArray
    ) {
        projection.projectLambda(lambda, phi) shouldBeSimilar result[0]
        projection.projectPhi(lambda, phi) shouldBeSimilar result[1]


        val projectPointResult = projection.project(lambda, phi)

        projectPointResult[0] shouldBeSimilar result[0]
        projectPointResult[1] shouldBeSimilar result[1]
    }

//
//    private fun checkInvert(
//        projection: Projection,
//        result: DoubleArray,
//        lambda: Double,
//        phi: Double
//    ) {
//
//        val invertLambda = projection.invertLambda(lambda, phi)
//        val invertPhi = projection.invertPhi(lambda, phi)
//
//        val invert = projection.invert(lambda, phi)
////        invertLambda shouldBeSimilar invert[0]
////        invertPhi shouldBeSimilar invert[1]
//
//
//        // TODO: refactor
//        invert.forEachIndexed { index, val1 ->
//            val val2 = if (index == 0) lambda else phi
//            var delta = abs(val1 - val2) % 360
//            if (delta > 180) delta -= 360
//            delta shouldBeSimilar .0
//        }
//    }
}

// TODO precision is 0.1 instead of epsilion. Should be fixed. See shoudBeClose
infix fun Double.shouldBeSimilar(other: Double): Unit = ToleranceMatcher(other, 0.1).test(this)