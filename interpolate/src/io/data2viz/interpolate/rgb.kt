package io.data2viz.interpolate

import io.data2viz.color.Color
import kotlin.js.Math


// TODO must take all types of colors in args (currently RGB only)
// TODO add alpha interpolation
fun interpolateRgb(colors: List<Color>, gamma: Double = 1.0): (Number) -> Color {
    val interpolator = getGammaInterpolator(gamma)

    val r = interpolator(colors.map { item -> item.r })
    val g = interpolator(colors.map { item -> item.g })
    val b = interpolator(colors.map { item -> item.b })

    return fun(percent:Number) = io.data2viz.color.colors.rgba(r(percent.toDouble()), g(percent.toDouble()), b(percent.toDouble()))
}

// TODO add alpha interpolation (alpha is linear not spline ?)
fun interpolateRgbBasis(colors: List<Color>, cyclical: Boolean = false): (Number) -> Color {
    val spline = getSplineInterpolator(cyclical)

    val r = spline(colors.map { item -> item.r })
    val g = spline(colors.map { item -> item.g })
    val b = spline(colors.map { item -> item.b })

    return fun(percent:Number) = io.data2viz.color.colors.rgba(r(percent.toDouble()), g(percent.toDouble()), b(percent.toDouble()))
}

// TODO no more constant needed ?
private fun getGammaInterpolator(y: Double = 1.0): (List<Int>) -> ((Double) -> Double) {
    return {a -> if (y == 1.0) linear(a) else exponential(a, y)}
    //return { a, b -> if (a == b) constant(a) else exponential(a, b, y) }*/
}

private fun getSplineInterpolator(cyclical: Boolean): (List<Int>) -> ((Double) -> Double) {
    return if (cyclical) { a -> basisSplineClosed(a) } else { a -> basisSpline(a) }
}

// constant interpolation
private fun constant(a: Int) = fun(_: Double) = a

// linear interpolation
private fun linear(values: List<Int>): (Double) -> Double {
    val n = values.size - 1
    return fun(t: Double): Double {
        val currentIndex: Int = if (t <= 0) 0 else if (t >= 1) n - 1 else Math.floor(t * n)
        val newT = t.coerceIn(0.0, 1.0)

        val t1 = (newT - currentIndex.toDouble() / n) * n
        return values[currentIndex].toDouble() * (1.0 - t1) + values[currentIndex + 1].toDouble() * t1
    }
}

// exponential interpolation
private fun exponential(values: List<Int>, y: Double): (Double) -> Double {
    val ny = 1 / y
    val n = values.size - 1

    return fun(t): Double {
        val currentIndex: Int = if (t <= 0) 0 else if (t >= 1) n - 1 else Math.floor(t * n)

        val na = Math.pow(values[currentIndex].toDouble(), y)
        val nb = Math.pow(values[currentIndex + 1].toDouble(), y) - na

        return Math.pow(na + t * nb, ny)
    }
}

// uniform nonrational B-spline interpolation
fun basisSpline(values: List<Int>): (Double) -> Double {
    val n = values.size - 1
    return fun(t: Double): Double {

        val newT = t.coerceIn(0.0, 1.0)
        val currentIndex: Int = if (t <= 0) 0 else if (t >= 1) n - 1 else Math.floor(t * n)

        val v1 = values[currentIndex]
        val v2 = values[currentIndex + 1]

        // TODO : color need to accept values over 255 and under 0
        val v0 = if (currentIndex > 0) values[currentIndex - 1] else 2 * v1 - v2
        val v3 = if (currentIndex < n - 1) values[currentIndex + 2] else 2 * v2 - v1

        return computeSpline((newT - currentIndex.toDouble() / n) * n, v0, v1, v2, v3)
    }
}

// uniform nonrational cyclical B-spline interpolation
private fun basisSplineClosed(values: List<Int>): (Double) -> Double {
    val n = values.size
    return fun(t: Double): Double {

        val newT = if (t < 0) t % 1 else (t % 1 + 1)
        val currentIndex = Math.floor(newT * n)

        val v0 = values[(currentIndex + n - 1) % n]
        val v1 = values[currentIndex % n]
        val v2 = values[(currentIndex + 1) % n]
        val v3 = values[(currentIndex + 2) % n]

        return computeSpline((newT - currentIndex.toDouble() / n) * n, v0, v1, v2, v3)
    }
}

// http://alvyray.com/Memos/CG/Pixar/spline77.pdf
fun computeSpline(t1: Double, v0: Int, v1: Int, v2: Int, v3: Int): Double {
    val t2 = t1 * t1
    val t3 = t2 * t1
    val fl = ((1 - 3 * t1 + 3 * t2 - t3) * v0
            + (4 - 6 * t2 + 3 * t3) * v1
            + (1 + 3 * t1 + 3 * t2 - 3 * t3) * v2
            + t3 * v3) / 6
    return fl
}