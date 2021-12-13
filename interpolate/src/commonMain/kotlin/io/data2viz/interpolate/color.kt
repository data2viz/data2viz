/*
 * Copyright (c) 2018-2021. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.interpolate

import io.data2viz.math.Angle
import io.data2viz.math.Percent
import io.data2viz.math.TAU
import kotlin.math.PI
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
            !long && diff < -PI -> linearClamped(a2.rad, diff + TAU)(t)
            !long && diff > PI -> linearClamped(a2.rad, diff - TAU)(t)
            else -> linearClamped(a2.rad, diff)(t)
        }
    }
}

/**
 * Clamped linear interpolation
 */
// TODO : note that this function is clamped, so we can't access a color outside of the range (ex. for asking 110%)
private fun linearClamped(a: Double, b: Double): Interpolator<Double> = { t -> a + t.coerceToDefault().value * b }

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
private fun exponential(a: Double, b: Double, y: Double): Interpolator<Double> {
    val ny = 1 / y
    val na = a.pow(y)
    val nb = b.pow(y) - na

    return fun(t: Percent) = (na + t.value * nb).pow(ny)
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

internal fun getSplineInterpolator(cyclical: Boolean): (List<Int>) -> Interpolator<Double> =
    when {
        cyclical -> { a -> basisClosed(a) }
        else ->     { a -> basis(a) }
    }
