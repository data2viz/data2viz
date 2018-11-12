package io.data2viz.interpolate

import io.data2viz.math.Percent
import kotlin.math.floor

/**
 * http://alvyray.com/Memos/CG/Pixar/spline77.pdf
 */
fun computeSpline(t1: Double, v0: Int, v1: Int, v2: Int, v3: Int): Double {
    val t2 = t1 * t1
    val t3 = t2 * t1
    return ((1 - 3 * t1 + 3 * t2 - t3) * v0
            + (4 - 6 * t2 + 3 * t3) * v1
            + (1 + 3 * t1 + 3 * t2 - 3 * t3) * v2
            + t3 * v3) / 6
}

/**
 * uniform nonrational B-spline interpolation
 */
fun basis(values: List<Int>): Interpolator<Double> {
    val n = values.size - 1
    return fun(t: Percent): Double {

        val newT = t.coerceToDefault().value
        val currentIndex: Int = if (t.value <= 0) 0 else if (t.value >= 1) n - 1 else floor(t.value * n).toInt()

        val v1 = values[currentIndex]
        val v2 = values[currentIndex + 1]

        // TODO : color need to accept values over 255 and under 0
        val v0 = if (currentIndex > 0) values[currentIndex - 1] else 2 * v1 - v2
        val v3 = if (currentIndex < n - 1) values[currentIndex + 2] else 2 * v2 - v1

        return computeSpline((newT - currentIndex.toDouble() / n) * n, v0, v1, v2, v3)
    }
}

/**
 * uniform nonrational cyclical B-spline interpolation
 */
fun basisClosed(values: List<Int>): Interpolator<Double> {
    val n = values.size
    return fun(t: Percent): Double {

        val newT = if (t.value < 0) t.value % 1 else (t.value % 1 + 1)
        val currentIndex = floor(newT * n).toInt()

        val v0 = values[(currentIndex + n - 1) % n]
        val v1 = values[currentIndex % n]
        val v2 = values[(currentIndex + 1) % n]
        val v3 = values[(currentIndex + 2) % n]

        return computeSpline((newT - currentIndex / n) * n, v0, v1, v2, v3)
    }
}