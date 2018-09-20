package io.data2viz.geom


/**
 * TODO is there a reason to have a Vector class in a dataviz library? Vector should me more appropriate.
 */
data class Vector(val vx: Double = 0.0, val vy: Double = 0.0) {
    operator fun plus(vector: Vector) = Vector(vx + vector.vx, vy + vector.vy)
    operator fun minus(vector: Vector) = Vector(vx - vector.vx, vy - vector.vy)
}