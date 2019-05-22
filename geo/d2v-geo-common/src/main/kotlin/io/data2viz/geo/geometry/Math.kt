package io.data2viz.geo.geometry

import io.data2viz.math.HALFPI
import io.data2viz.math.PI
import kotlin.math.acos
import kotlin.math.asin

val Double.asin: Double
    get() = limitedAsin(this)

val Double.acos: Double
    get() = limitedAcos(this)

private fun limitedAsin(value: Double): Double = when {
    value > 1 -> HALFPI
    value < -1 -> -HALFPI
    else -> asin(value)
}

private fun limitedAcos(value: Double): Double = when {
    value > 1 -> .0
    value < -1 -> PI
    else -> acos(value)
}