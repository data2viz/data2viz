package io.data2viz.interpolate

import kotlin.math.round

/**
 * An interpolator transforms a normalized continuous range (0.0 -> 1.0)
 * to a Domain object R
 */
typealias Interpolator<R> = (Double) -> R

// TODO : remove use only double
fun interpolateNumber(a: Number, b: Number): Interpolator<Double> {
    val diff = b.toDouble() - a.toDouble()
    return { t -> a.toDouble() + t * diff }
}

fun interpolateNumber(a: Double, b: Double): Interpolator<Double>{
    val diff = b - a
    return { t -> a + t * diff }
}


fun interpolateRound(a: Double, b: Double): Interpolator<Double> {
    val diff = b - a
    return { t -> round(a + t * diff) }
}

// TODO : remove use only double
// TODO warn : (end == start) -> crash
fun uninterpolateNumber(start: Float, end: Float): (Double) -> Float = { t -> (t.toFloat() - start) / (end - start) }

fun uninterpolateNumber(start: Double, end: Double): (Double) -> Double = { if (end != start) ((it - start) / (end - start)) else start }

fun identity(t: Double) = t