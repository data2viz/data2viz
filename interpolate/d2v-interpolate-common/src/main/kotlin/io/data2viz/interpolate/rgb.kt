package io.data2viz.interpolate

import io.data2viz.color.Color
import io.data2viz.color.colors.rgba


// TODO must take all types of colors in args (currently RGB only)
// TODO add alpha interpolation
// TODO List instead of start, end ? (validate and check size !!)
fun interpolateRgb(start:Color, end:Color, gamma: Double = 1.0): (Number) -> Color {
    val interpolator = gamma(gamma)

    val r = interpolator(start.r, end.r)
    val g = interpolator(start.g, end.g)
    val b = interpolator(start.b, end.b)

    return fun(percent: Number) = rgba(r(percent.toDouble()), g(percent.toDouble()), b(percent.toDouble()))
}

// TODO add alpha interpolation (alpha is linear not spline ?)
fun interpolateRgbBasis(colors: List<Color>, cyclical: Boolean = false): (Number) -> Color {
    val spline = getSplineInterpolator(cyclical)

    val r = spline(colors.map { item -> item.r })
    val g = spline(colors.map { item -> item.g })
    val b = spline(colors.map { item -> item.b })

    return fun(percent: Number) = rgba(r(percent.toDouble()), g(percent.toDouble()), b(percent.toDouble()))
}

fun interpolateRgbBasisClosed(colors: List<Color>) = interpolateRgbBasis(colors, true)