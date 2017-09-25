package io.data2viz.shape

import kotlin.js.Math
import kotlin.js.Math.PI
import kotlin.js.Math.acos
import kotlin.js.Math.asin

val epsilon = 1e-12
val pi = PI
val halfPi = pi / 2
val tau = 2 * pi

fun acos(x: Double) = if (x > 1.0)  0.0     else if (x < -1) pi         else acos(x)
fun asin(x: Double) = if (x >= 1.0) halfPi  else if (x <= -1.0) -halfPi else asin(x)
