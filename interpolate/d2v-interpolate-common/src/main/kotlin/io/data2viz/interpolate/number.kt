package io.data2viz.interpolate

import kotlin.math.round

// TODO : remove use only double
fun interpolateNumber(a: Number, b: Number): (Double) -> Double {
    val diff = b.toDouble() - a.toDouble()
    return { t -> a.toDouble() + t * diff }
}

fun interpolateNumber(a: Double, b: Double): (Double) -> Double {
    val diff = b - a
    return { t -> a + t * diff }
}


fun interpolateRound(a: Double, b: Double): (Double) -> Double {
    val diff = b - a
    return { t -> round(a + t * diff) }
}

// TODO : remove use only double
// TODO warn : (end == start) -> crash
fun uninterpolateNumber(start: Number, end: Number): (Double) -> Double = { t -> (t - start.toDouble()) / (end.toDouble() - start.toDouble()) }

fun uninterpolateNumber(start: Double, end: Double): (Double) -> Double = { if (end != start) ((it - start) / (end - start)) else start }

fun identity(t: Double) = t