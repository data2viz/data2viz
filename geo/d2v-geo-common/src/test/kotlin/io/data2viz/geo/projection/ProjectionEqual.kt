package io.data2viz.geo.projection

import io.data2viz.geo.projection.common.Projection
import kotlin.math.abs

internal fun pt(a: Double, b: Double) = arrayOf(a, b)
internal fun pt(a: Double, b: Double, c: Double) = arrayOf(a, b, c)

val projectTestPrecision = 0.000001 // 1e-6
val invertTestPrecision = 0.001 // 1e-3

fun checkProjectAndInvert(
    projection: Projection,
    lambda: Double,
    phi: Double,
    screenX: Double,
    screenY: Double,
    deltaPrecision: Double? = null
) {
    checkProject(projection, lambda, phi, screenX, screenY, deltaPrecision ?: projectTestPrecision)

    checkInvert(projection, lambda, phi, screenX, screenY, deltaPrecision ?: invertTestPrecision)
}

fun checkProject(
    projection: Projection,
    lambda: Double,
    phi: Double,
    screenX: Double,
    screenY: Double,
    deltaPrecision: Double? = null
) {


    val projectPointResult = projection.project(lambda, phi)

    inDelta(projectPointResult[0], screenX, deltaPrecision ?: projectTestPrecision)
    inDelta(projectPointResult[1], screenY, deltaPrecision ?: projectTestPrecision)
}


fun checkInvert(
    projection: Projection,
    lambda: Double,
    phi: Double,
    screenX: Double,
    screenY: Double,
    deltaPrecision: Double? = null
) {

    val invert = projection.invert(screenX, screenY)

    val delta = abs(lambda - invert[0]) % 360

    if (delta > deltaPrecision ?: invertTestPrecision) {
        throw AssertionError("checkInvert lambda is invalid excepted = $lambda actual = ${invert[0]} delta = $delta")
    }

    inDelta(invert[1], phi, deltaPrecision)
}

fun inDelta(actual: Double, expected: Double, delta: Double? = null) =
    abs(actual - expected) <= delta ?: 0.001
