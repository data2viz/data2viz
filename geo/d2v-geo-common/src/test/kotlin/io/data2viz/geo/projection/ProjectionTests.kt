package io.data2viz.geo.projection

import io.data2viz.geo.projection.common.Projection
import io.data2viz.test.TestBase
import kotlin.math.abs

internal fun pt(a: Double, b: Double) = arrayOf(a, b)
internal fun pt(a: Double, b: Double, c: Double) = arrayOf(a, b, c)

class ProjectionTests : TestBase() {

    fun checkProjection(projection: Projection, lambda: Double, phi: Double, result: DoubleArray) {
        checkProject(projection, lambda, phi, result)
        checkInvert(projection, result, lambda, phi)
    }

    private fun checkProject(
        projection: Projection,
        lambda: Double,
        phi: Double,
        result: DoubleArray
    ) {
        projection.projectLambda(lambda, phi) shouldBeClose result[0]
        projection.projectPhi(lambda, phi) shouldBeClose result[1]

    }

    private fun checkInvert(
        projection: Projection,
        result: DoubleArray,
        lambda: Double,
        phi: Double
    ) {
        val invert = projection.invert(result[0], result[1])
        invert.forEachIndexed { index, val1 ->
            val val2 = if (index == 0) lambda else phi
            var delta = abs(val1 - val2) % 360
            if (delta > 180) delta -= 360
            delta shouldBeClose .0
        }
    }
}