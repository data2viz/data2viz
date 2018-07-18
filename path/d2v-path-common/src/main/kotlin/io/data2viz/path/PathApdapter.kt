package io.data2viz.path

import kotlin.math.*

fun svgPath(): SvgPath = SvgPath()

internal const val TAU = 2 * PI
internal const val EPSILON = 1e-6
internal const val tauEpsilon = TAU - EPSILON

/**
 * Common denominator between Canvas, SVG, JavaFX
 */
interface PathAdapter {
    fun moveTo(x: Double, y: Double)
    fun lineTo(x: Double, y: Double)
    fun closePath()
    fun quadraticCurveTo(x1: Double, y1: Double, x: Double, y: Double)
    fun bezierCurveTo(x1: Double, y1: Double, x2: Double, y2: Double, x: Double, y: Double)
    fun arcTo(fromX: Double, fromY: Double, toX: Double, toY: Double, radius: Double)
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


