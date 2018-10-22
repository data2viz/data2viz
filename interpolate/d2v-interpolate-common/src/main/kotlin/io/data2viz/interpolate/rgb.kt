package io.data2viz.interpolate

import io.data2viz.color.Color
import io.data2viz.color.rgba
import kotlin.math.roundToInt


// TODO must take all types of colors in args (currently RGB only)
// TODO add alpha interpolation
// TODO List instead of start, end ? (validate and check size !!)
// TODO rename interpolate
fun interpolateRgb(start:Color, end:Color, gamma: Double = 1.0): (Number) -> Color {
    val interpolator = gamma(gamma)

    val r = interpolator(start.r.toDouble(), end.r.toDouble())
    val g = interpolator(start.g.toDouble(), end.g.toDouble())
    val b = interpolator(start.b.toDouble(), end.b.toDouble())

    return fun(percent: Number) = rgba(
            r(percent.toDouble()).roundToInt(),
            g(percent.toDouble()).roundToInt(),
            b(percent.toDouble()).roundToInt())
}

// TODO add alpha interpolation (alpha is linear not spline ?)
fun interpolateRgbBasis(colors: List<Color>, cyclical: Boolean = false): (Number) -> Color {
    val spline = getSplineInterpolator(cyclical)

    val r = spline(colors.map { item -> item.r })
    val g = spline(colors.map { item -> item.g })
    val b = spline(colors.map { item -> item.b })

    return fun(percent: Number) = rgba(
            r(percent.toDouble()).roundToInt(),
            g(percent.toDouble()).roundToInt(),
            b(percent.toDouble()).roundToInt())
}

fun interpolateRgbBasisClosed(colors: List<Color>) = interpolateRgbBasis(colors, true)
fun interpolateRgbDefault(start:Color, end:Color) = interpolateRgb(start, end, 1.0)