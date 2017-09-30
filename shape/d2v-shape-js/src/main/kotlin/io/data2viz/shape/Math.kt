package io.data2viz.shape

import kotlin.math.PI


val epsilon = 1e-12
val pi = PI
val halfPi = pi / 2
val tau = 2 * pi

fun acos(x: Double) = if (x > 1.0)  0.0     else if (x < -1) pi         else kotlin.math.acos(x)
fun asin(x: Double) = if (x >= 1.0) halfPi  else if (x <= -1.0) -halfPi else kotlin.math.asin(x)
