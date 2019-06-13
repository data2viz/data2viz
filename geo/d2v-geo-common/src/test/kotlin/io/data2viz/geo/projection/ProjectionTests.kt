package io.data2viz.geo.projection

import io.data2viz.geo.projection.common.Projection
import io.data2viz.test.TestBase
import io.data2viz.test.matchers.ToleranceMatcher

internal fun pt(a: Double, b: Double) = arrayOf(a, b)
internal fun pt(a: Double, b: Double, c: Double) = arrayOf(a, b, c)

class ProjectionTests : TestBase() {

    fun checkProjection(projection: Projection, lambda: Double, phi: Double, screenX: Double, screenY: Double) {
        checkProject(projection, lambda, phi, screenX, screenY)

        checkInvert(projection, lambda, phi, screenX, screenY)
    }

    private fun checkProject(
        projection: Projection,
        lambda: Double,
        phi: Double,
        screenX: Double,
        screenY: Double
    ) {


        val projectPointResult = projection.project(lambda, phi)

        projectPointResult[0] shouldBeSimilar screenX
        projectPointResult[1] shouldBeSimilar screenY
    }


    private fun checkInvert(
        projection: Projection,
        lambda: Double,
        phi: Double,
        screenX: Double,
        screenY: Double
    ) {

        val invert = projection.invert(screenX, screenY)

        invert[0] shouldBeSimilar lambda
        invert[1] shouldBeSimilar phi
    }
}

// TODO precision is 0.1 instead of epsilion. Should be fixed. See shoudBeClose
infix fun Double.shouldBeSimilar(other: Double): Unit = ToleranceMatcher(other, 0.1).test(this)