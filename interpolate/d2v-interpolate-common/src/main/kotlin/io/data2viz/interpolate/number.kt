package io.data2viz.interpolate

import io.data2viz.math.*
import kotlin.math.round

/**
 * An interpolator transforms a normalized continuous range (0% -> 100%) to an object T
 */
typealias Interpolator<T> = (Percent) -> T

/**
 * An un-interpolator transforms an object T to a normalized continuous range (0% -> 100%)
 */
typealias UnInterpolator<T> = (T) -> Percent

fun interpolateNumber(start: Double, end: Double): Interpolator<Double>{
    val diff = end - start
    return { t -> start + t.value * diff }
}


fun interpolateRound(start: Double, end: Double): Interpolator<Double> {
    val diff = end - start
    return { t -> round(start + t.value * diff) }
}

fun uninterpolateNumber(start: Double, end: Double): UnInterpolator<Double> {
    val diff = end - start
    return if (diff != .0) { t -> Percent((t - start) / diff) }  else { t -> 0.pct }
}

fun identity(t: Double) = t