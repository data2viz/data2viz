package io.data2viz.path

import kotlin.math.*

fun svgPath(): SvgPath = SvgPath()

internal const val TAU = 2 * PI
internal const val EPSILON = 1e-6
internal const val tauEpsilon = TAU - EPSILON

/**
 * Common path denominator between Canvas, SVG, JavaFX, Android
 */
interface PathAdapter {
    fun moveTo(x: Double, y: Double)
    fun lineTo(x: Double, y: Double)
    fun closePath()
    fun quadraticCurveTo(cpx: Double, cpy: Double, x: Double, y: Double)
    fun bezierCurveTo(cpx1: Double, cpy1: Double, cpx2: Double, cpy2: Double, x: Double, y: Double)
    fun arcTo(cpx: Double, cpy: Double, x: Double, y: Double, radius: Double)
    fun arc(
        centerX: Double,
        centerY: Double,
        radius: Double,
        startAngle: Double,
        endAngle: Double,
        counterClockWise: Boolean = false
    )

    fun rect(x: Double, y: Double, w: Double, h: Double)
}


