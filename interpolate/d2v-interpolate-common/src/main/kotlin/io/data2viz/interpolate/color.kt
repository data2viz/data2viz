package io.data2viz.interpolate

import io.data2viz.math.Angle
import io.data2viz.math.PI
import io.data2viz.math.Percent
import io.data2viz.math.TAU
import kotlin.math.pow

// TODO no more constant needed ?
// = interpolate.color.gamma & interpolate.color.nogamma in D3
internal fun gamma(gamma: Double = 1.0): (Double, Double) -> Interpolator<Double> {
    return { a, b -> if (gamma == 1.0) linearClamped(a, b - a) else exponential(a, b, gamma) }
}

// TODO : see if needed (gamma needs rework)
//internal fun ungamma(y: Double = 1.0): (Double, Double) -> (Double) -> Double {
//    return { a, b -> if (y == 1.0)
//        uninterpolateNumber(a, b)
//    else
//        exponential(a, b, y)            // TODO unexponential ??
//    }
//}

/**
 * Hue interpolation, take the shortest path between 2 hues if 'long' is not set to true.
 */
// TODO : interpolator<Angle> ?
internal fun interpolateHue(from: Angle, to: Angle, long: Boolean = false): Interpolator<Double> {
    val a2 = from.normalize()
    val b2 = to.normalize()
    val diff = b2.rad - a2.rad
    return { t ->
        when {
            !long && diff < -PI    -> linearClamped(a2.rad, diff + TAU)(t)
            !long && diff > PI     -> linearClamped(a2.rad, diff - TAU)(t)
            else                -> linearClamped(a2.rad, diff)(t)
        }
    }
}

/**
 * Clamped linear interpolation
 */
// TODO : note that this function is clamped, so we can't access a color outside of the range (ex. for asking 110%)
private fun linearClamped(a:Double, b:Double): Interpolator<Double> = { t -> a + t.coerceToDefault().value * b }

/*private fun linearClamped(values: List<Number>): (Double) -> Double {
    val n = values.size - 1
    return fun(t: Double): Double {
        val currentIndex: Int = if (t <= 0) 0 else if (t >= 1) n - 1 else Math.floor(t * n)
        val newT = t.coerceIn(0.0, 1.0)

        val t1 = (newT - currentIndex.toDouble() / n) * n
        return values[currentIndex].toDouble() * (1.0 - t1) + values[currentIndex + 1].toDouble() * t1
    }
}*/

/**
 * exponential interpolation
 */
private fun exponential(a:Double, b:Double, y: Double): Interpolator<Double> {
    val ny = 1 / y
    val na = a.pow(y)
    val nb = b.pow(y) - na

    return fun(t:Percent): Double {
        return (na + t.value * nb).pow(ny)
    }
}
/*private fun exponential(values: List<Number>, y: Double): (Double) -> Double {
    val ny = 1 / y
    val n = values.size - 1

    return fun(t): Double {
        val currentIndex: Int = if (t <= 0) 0 else if (t >= 1) n - 1 else Math.floor(t * n)

        val na = Math.pow(values[currentIndex].toDouble(), y)
        val nb = Math.pow(values[currentIndex + 1].toDouble(), y) - na

        return Math.pow(na + t * nb, ny)
    }
}*/

internal fun getSplineInterpolator(cyclical: Boolean): (List<Int>) -> Interpolator<Double> {
    return if (cyclical) { a -> basisClosed(a) } else { a -> basis(a) }
}
