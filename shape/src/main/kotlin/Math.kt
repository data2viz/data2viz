package io.data2viz.shape

import kotlin.js.Math

val epsilon = 1e-12;
val pi = Math.PI;
val halfPi = pi / 2;
val tau = 2 * pi;

fun acos(x: Double) = if (x > 1.0)  0.0     else if (x < -1) pi         else Math.acos(x)
fun asin(x: Double) = if (x >= 1.0) halfPi  else if (x <= -1.0) -halfPi else Math.asin(x)
