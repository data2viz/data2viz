package io.data2viz.interpolate

import io.data2viz.math.Percent
import io.data2viz.math.pct
import kotlin.math.round

/**
 * An interpolator transforms a normalized continuous range (0.0 -> 1.0)
 * to a Domain object R
 */
typealias Interpolator<R> = (Percent) -> R

// TODO : remove use only double
fun interpolateNumber(a: Number, b: Number): Interpolator<Double> {
    val diff = b.toDouble() - a.toDouble()
    return { t -> a.toDouble() + t.value * diff }
}

fun interpolateNumber(a: Double, b: Double): Interpolator<Double>{
    val diff = b - a
    return { t -> a + t.value * diff }
}


fun interpolateRound(a: Double, b: Double): Interpolator<Double> {
    val diff = b - a
    return { t -> round(a + t.value * diff) }
}

// TODO : remove use only double
// TODO warn : (end == start) -> crash
fun uninterpolateNumber(start: Float, end: Float): (Double) -> Float = { t -> (t.toFloat() - start) / (end - start) }

fun uninterpolateNumber(start: Double, end: Double): (Double) -> Percent = { if (end != start) Percent((it - start) / (end - start)) else 0.pct }

fun identity(t: Double) = t