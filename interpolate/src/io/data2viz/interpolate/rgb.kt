package io.data2viz.interpolate

import io.data2viz.color.Color


// TODO must take all types of colors in args (currently RGB only)
// TODO add alpha interpolation
// TODO List instead of start, end ? (validate and check size !!)
fun interpolateRgb(colors: List<Color>, gamma: Double = 1.0): (Number) -> Color {
    val interpolator = gamma(gamma)

    val r = interpolator(colors.map { item -> item.r })
    val g = interpolator(colors.map { item -> item.g })
    val b = interpolator(colors.map { item -> item.b })

    return fun(percent: Number) = io.data2viz.color.colors.rgba(r(percent.toDouble()), g(percent.toDouble()), b(percent.toDouble()))
}

// TODO add alpha interpolation (alpha is linear not spline ?)
fun interpolateRgbBasis(colors: List<Color>, cyclical: Boolean = false): (Number) -> Color {
    val spline = getSplineInterpolator(cyclical)

    val r = spline(colors.map { item -> item.r })
    val g = spline(colors.map { item -> item.g })
    val b = spline(colors.map { item -> item.b })

    return fun(percent: Number) = io.data2viz.color.colors.rgba(r(percent.toDouble()), g(percent.toDouble()), b(percent.toDouble()))
}

fun interpolateRgbBasisClosed(colors: List<Color>) = interpolateRgbBasis(colors, true)