package io.data2viz.geo

import io.data2viz.math.HALFPI

fun asin(x: Double) = when {
    x > 1 -> HALFPI
    x < -1 -> -HALFPI
    else -> kotlin.math.asin(x)
}