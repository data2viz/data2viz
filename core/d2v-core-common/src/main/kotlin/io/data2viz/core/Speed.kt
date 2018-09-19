package io.data2viz.core


/**
 * TODO is there a reason to have a Speed class in a dataviz library? Vector should me more appropriate.
 */
data class Speed(val vx: Double = 0.0, val vy: Double = 0.0) {
    operator fun plus(speed: Speed) = Speed(vx + speed.vx, vy + speed.vy)
    operator fun minus(speed: Speed) = Speed(vx - speed.vx, vy - speed.vy)
}