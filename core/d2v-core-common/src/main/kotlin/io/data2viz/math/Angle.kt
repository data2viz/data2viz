package io.data2viz.math

import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

/**
 * Abstraction of an angle to have some more typesafe angle manipulations.
 */
inline class Angle(val rad: Double){
    val cos:Double get() = cos(rad)
    val sin:Double get() = sin(rad)
    val tan:Double get() = tan(rad)
    val deg:Double get() = rad * RAD_TO_DEG

    fun normalize():Angle =
            if (rad >= 0) Angle(rad % TAU_ANGLE.rad)
            else Angle((rad % TAU_ANGLE.rad) + TAU_ANGLE.rad)

    operator fun plus(angle: Angle)     = Angle(rad + angle.rad)
    operator fun minus(angle: Angle)    = Angle(rad - angle.rad)
    operator fun times(d: Number)       = Angle(rad * d.toDouble())
    operator fun div(d: Number)         = Angle(rad / d.toDouble())
    operator fun div(other: Angle)      = rad / other.rad
    operator fun unaryMinus() = Angle(-rad)

}


/**
 * Assuming this represents a value in degrees, converts the value to radians.
 */
fun Double.toRadians() = this * DEG_TO_RAD

/**
 * Assuming this represents a value in radians, converts the value to degrees.
 */
fun Double.toDegrees() = this * RAD_TO_DEG

/**
 * Extension property to create easily an angle from a number representing degrees
 */
val Number.deg:Angle
    get() = Angle(toDouble() * DEG_TO_RAD)

/**
 * Extension property to create easily an angle from a number representing radians
 */
val Number.rad:Angle
    get() = Angle(toDouble())


/**
 * Extension function operator on Number to allow 2 * PI.rad
 */
operator fun Number.times(angle: Angle) = Angle(angle.rad * this.toDouble())
