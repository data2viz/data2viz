package io.data2viz.interpolate

import io.data2viz.color.RgbColor
import io.data2viz.color.colors.rgba


// TODO must take all types of colors in args (currently RGB only)
// TODO add alpha interpolation
// TODO List instead of start, end ? (validate and check size !!)
// TODO rename interpolate
fun interpolateRgb(start:RgbColor, end:RgbColor, gamma: Double = 1.0): (Number) -> RgbColor {
    val interpolator = gamma(gamma)

    val r = interpolator(start.r.toDouble(), end.r.toDouble())
    val g = interpolator(start.g.toDouble(), end.g.toDouble())
    val b = interpolator(start.b.toDouble(), end.b.toDouble())

    return fun(percent: Number) = rgba(r(percent.toDouble()), g(percent.toDouble()), b(percent.toDouble()))
}

// TODO add alpha interpolation (alpha is linear not spline ?)
fun interpolateRgbBasis(colors: List<RgbColor>, cyclical: Boolean = false): (Number) -> RgbColor {
    val spline = getSplineInterpolator(cyclical)

    val r = spline(colors.map { item -> item.r })
    val g = spline(colors.map { item -> item.g })
    val b = spline(colors.map { item -> item.b })

    return fun(percent: Number) = rgba(r(percent.toDouble()), g(percent.toDouble()), b(percent.toDouble()))
}

fun interpolateRgbBasisClosed(colors: List<RgbColor>) = interpolateRgbBasis(colors, true)