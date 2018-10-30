package io.data2viz.interpolate

import io.data2viz.math.Angle
import kotlin.math.floor
import kotlin.math.pow


// TODO no more constant needed ?
// = interpolate.color.gamma & interpolate.color.nogamma in D3
internal fun gamma(y: Double = 1.0): (Double, Double) -> (Double) -> Double {
    return { a, b -> if (y == 1.0) linear(a, b - a) else exponential(a, b, y) }
    //return { a, b -> if (a == b) constant(a) else exponential(a, b, y) }*/
}

internal fun ungamma(y: Double = 1.0): (Double, Double) -> (Double) -> Double {
    return { a, b -> if (y == 1.0)
        uninterpolateNumber(a, b)
    else
        exponential(a, b, y)            // TODO unexponential ??
    }
}

// TODO no more constant needed ?
// hue interpolation (in degrees)
internal fun hue(a: Angle, b:Angle): (Double) -> Double {
    val a2 = a.normalize()
    val b2 = b.normalize()
    val degreesTo = b2.deg - a2.deg
    return { t ->
        when {
            degreesTo < -180    -> linear(a2.deg, degreesTo + 360)(t)
            degreesTo > 180     -> linear(a2.deg, degreesTo - 360)(t)
            else                -> linear(a2.deg, degreesTo)(t)
        }
    }
}

/**
 * Linear interpolation
 */
// TODO remove coerce, color (RGB) should be able to manage it !
// TODO why use this instead of standard linear not-clamped function ?
private fun linear(a:Double, b:Double): (Double) -> Double = {t -> a + t.coerceIn(.0, 1.0) * b }
/*private fun linear(values: List<Number>): (Double) -> Double {
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
private fun exponential(a:Double, b:Double, y: Double): (Double) -> Double {
    val ny = 1 / y
    val na = a.pow(y)
    val nb = b.pow(y) - na

    return fun(t:Double): Double {
        return (na + t * nb).pow(ny)
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

internal fun getSplineInterpolator(cyclical: Boolean): (List<Int>) -> ((Double) -> Double) {
    return if (cyclical) { a -> basisClosed(a) } else { a -> basis(a) }
}
