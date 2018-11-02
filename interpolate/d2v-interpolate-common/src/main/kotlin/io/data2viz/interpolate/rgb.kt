package io.data2viz.interpolate

import io.data2viz.color.Color
import io.data2viz.color.Colors
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt


// TODO add alpha interpolation
// TODO List instead of start, end ? (validate and check size !!)
// TODO rename interpolate
// TODO : check colors interpolation from chroma.js
// TODO : extractgamma function (check D3 last version)
private fun interpolateRgb(start: Color, end: Color, gamma: Double = 1.0): (Double) -> Color {
    val interpolator = gamma(gamma)

    val r = interpolator(start.r.toDouble(), end.r.toDouble())
    val g = interpolator(start.g.toDouble(), end.g.toDouble())
    val b = interpolator(start.b.toDouble(), end.b.toDouble())

    return fun(percent: Double) = Colors.rgb(
        r(percent).roundToInt(),
        g(percent).roundToInt(),
        b(percent).roundToInt()
    )
}

private fun lRGBInterpolator(start: Double, end: Double): (Double) -> Double = {
    val percent = it.coerceIn(.0, 1.0)
    sqrt(start.pow(2) * (1 - percent) + end.pow(2) * percent)
}


/**
 * lRGB interpolation produce a better result when mixing 2 colors (right "lightness" of the color) by using pow(2)
 * versions of RGB channels.
 * For more information check this cool short video: https://www.youtube.com/watch?v=LKnqECcg6Gw
 */
// TODO alpha
private fun interpolateLRgb(start: Color, end: Color): (Double) -> Color {

    val r = lRGBInterpolator(start.r.toDouble(), end.r.toDouble())
    val g = lRGBInterpolator(start.g.toDouble(), end.g.toDouble())
    val b = lRGBInterpolator(start.b.toDouble(), end.b.toDouble())

    return fun(percent: Double) = Colors.rgb(
        r(percent).roundToInt(),
        g(percent).roundToInt(),
        b(percent).roundToInt()
    )
}

// TODO add alpha interpolation (alpha is linear not spline ?)
private fun interpolateRgbBasis(colorsList: List<Color>, cyclical: Boolean = false): (Double) -> Color {
    val spline = getSplineInterpolator(cyclical)

    val r = spline(colorsList.map { it.r })
    val g = spline(colorsList.map { it.g })
    val b = spline(colorsList.map { it.b })

    return fun(percent: Double) = Colors.rgb(
        r(percent).roundToInt(),
        g(percent).roundToInt(),
        b(percent).roundToInt()
    )
}

fun rgbBasisInterpolator(colors: List<Color>) = interpolateRgbBasis(colors, false)
fun rgbBasisClosedInterpolator(colors: List<Color>) = interpolateRgbBasis(colors, true)
fun rgbDefaultInterpolator(start: Color, end: Color) = interpolateRgb(start, end, 1.0)
fun rgbLinearInterpolator(start: Color, end: Color) = interpolateLRgb(start, end)