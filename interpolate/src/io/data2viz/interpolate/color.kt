package io.data2viz.interpolate

import io.data2viz.math.Angle
import kotlin.js.Math


// TODO no more constant needed ?
// = interpolate.color.gamma & interpolate.color.nogamma in D3
internal fun gamma(y: Double = 1.0): (List<Number>) -> ((Double) -> Double) {
    return { a -> if (y == 1.0) linear(a) else exponential(a, y) }
    //return { a, b -> if (a == b) constant(a) else exponential(a, b, y) }*/
}

// TODO no more constant needed ?
// hue interpolation (in degrees)
internal fun hue(a: Angle, b:Angle): (Double) -> Double {                           // 38, 300 ---> on veut 38, -60
    val a2 = a.normalize()
    val b2 = b.normalize()
    val degreesTo = b2.deg - a2.deg                                                   // 262
    val angles = when {
        degreesTo < -180    -> arrayListOf(a.deg, b.deg + 360)
        degreesTo > 180     -> arrayListOf(a.deg, b.deg - 360)
        else                -> arrayListOf(a.deg, b.deg)
    }
    return { t -> linear(angles)(t) }
}


/*fun hue(a:Int, b:Int) {
    val d = b - a;
    return d ? linear(a, d > 180 || d < -180 ? d - 360 * Math.round(d / 360) : d) : constant(isNaN(a) ? b : a);
}*/


// constant interpolation
private fun constant(a: Int) = fun(_: Double) = a

// linear interpolation
private fun linear(values: List<Number>): (Double) -> Double {
    val n = values.size - 1
    return fun(t: Double): Double {
        val currentIndex: Int = if (t <= 0) 0 else if (t >= 1) n - 1 else Math.floor(t * n)
        val newT = t.coerceIn(0.0, 1.0)

        val t1 = (newT - currentIndex.toDouble() / n) * n
        return values[currentIndex].toDouble() * (1.0 - t1) + values[currentIndex + 1].toDouble() * t1
    }
}

// exponential interpolation
private fun exponential(values: List<Number>, y: Double): (Double) -> Double {
    val ny = 1 / y
    val n = values.size - 1

    return fun(t): Double {
        val currentIndex: Int = if (t <= 0) 0 else if (t >= 1) n - 1 else Math.floor(t * n)

        val na = Math.pow(values[currentIndex].toDouble(), y)
        val nb = Math.pow(values[currentIndex + 1].toDouble(), y) - na

        return Math.pow(na + t * nb, ny)
    }
}

internal fun getSplineInterpolator(cyclical: Boolean): (List<Int>) -> ((Double) -> Double) {
    return if (cyclical) { a -> basisClosed(a) } else { a -> basis(a) }
}

// uniform nonrational B-spline interpolation
fun basis(values: List<Int>): (Double) -> Double {
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
private fun basisClosed(values: List<Int>): (Double) -> Double {
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