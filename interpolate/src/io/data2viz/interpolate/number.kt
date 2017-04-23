package io.data2viz.interpolate

fun interpolateNumber(a:Number, b:Number) = {t:Double -> a.toDouble() + t * (b.toDouble() - a.toDouble()) }

//fun uninterpolaleNumber(a:Number, b: Number) =
fun identity(a:Double) = a
