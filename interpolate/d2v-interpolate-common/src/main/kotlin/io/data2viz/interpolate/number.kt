package io.data2viz.interpolate

fun interpolateNumber(a: Number, b: Number): (Number) -> Number = { t -> a.toDouble() + t.toDouble() * (b.toDouble() - a.toDouble()) }
fun uninterpolate(start: Number, end: Number): (Number) -> Number = { t -> (t.toDouble() - start.toDouble()) / (end.toDouble() - start.toDouble()) }

// TODO : Number ?
fun identity(t: Double) = t