package io.data2viz.interpolate

import io.data2viz.color.Color
import io.data2viz.color.colors
import kotlin.math.roundToInt


// TODO must take all types of colors in args (currently RGB only)
// TODO add alpha interpolation
// TODO List instead of start, end ? (validate and check size !!)
// TODO rename interpolate
// TODO : check colors interpolation from chroma.js
fun interpolateRgb(start:Color, end:Color, gamma: Double = 1.0): (Double) -> Color {
    val interpolator = gamma(gamma)

    val r = interpolator(start.r.toDouble(), end.r.toDouble())
    val g = interpolator(start.g.toDouble(), end.g.toDouble())
    val b = interpolator(start.b.toDouble(), end.b.toDouble())

    return fun(percent: Double) = colors.rgb(
            r(percent).roundToInt(),
            g(percent).roundToInt(),
            b(percent).roundToInt())
}

// TODO add alpha interpolation (alpha is linear not spline ?)
fun interpolateRgbBasis(colorsList: List<Color>, cyclical: Boolean = false): (Double) -> Color {
    val spline = getSplineInterpolator(cyclical)

    val r = spline(colorsList.map { item -> item.r })
    val g = spline(colorsList.map { item -> item.g })
    val b = spline(colorsList.map { item -> item.b })

    return fun(percent: Double) = colors.rgb(
            r(percent).roundToInt(),
            g(percent).roundToInt(),
            b(percent).roundToInt())
}

fun interpolateRgbBasisClosed(colors: List<Color>) = interpolateRgbBasis(colors, true)
fun interpolateRgbDefault(start:Color, end:Color) = interpolateRgb(start, end, 1.0)