package io.data2viz.geo.projection

import io.data2viz.geo.projection.common.Projection
import io.data2viz.test.TestBase
import io.data2viz.test.matchers.ToleranceMatcher
import kotlin.math.abs

internal fun pt(a: Double, b: Double) = arrayOf(a, b)
internal fun pt(a: Double, b: Double, c: Double) = arrayOf(a, b, c)

class ProjectionTests : TestBase() {

    fun checkProjection(
        projection: Projection,
        lambda: Double,
        phi: Double,
        screenX: Double,
        screenY: Double,
        deltaPrecision: Double? = null
    ) {
        checkProject(projection, lambda, phi, screenX, screenY, deltaPrecision ?: 0.000001) // 1e-6

        checkInvert(projection, lambda, phi, screenX, screenY, deltaPrecision ?: 0.001) // 1e-3
    }

    private fun checkProject(
        projection: Projection,
        lambda: Double,
        phi: Double,
        screenX: Double,
        screenY: Double,
        deltaPrecision: Double
    ) {


        val projectPointResult = projection.project(lambda, phi)

        inDelta(projectPointResult[0], screenX, deltaPrecision)
        inDelta(projectPointResult[1], screenY, deltaPrecision)
    }


    private fun checkInvert(
        projection: Projection,
        lambda: Double,
        phi: Double,
        screenX: Double,
        screenY: Double,
        deltaPrecision: Double
    ) {

        val invert = projection.invert(screenX, screenY)

        val delta = abs(lambda - invert[0]) % 360

        if (delta > deltaPrecision) {
            throw AssertionError("checkInvert lambda is invalid excepted = $lambda actual = ${invert[0]} delta = $delta")
        }

        inDelta(invert[1], phi, deltaPrecision)
    }

    fun inDelta(actual: Double, expected: Double, delta: Double) =
        abs(actual - expected) <= delta;

}

// TODO precision is 0.1 instead of epsilion. Should be fixed. See shoudBeClose
infix fun Double.shouldBeSimilar(other: Double): Unit = ToleranceMatcher(other, 0.1).test(this)