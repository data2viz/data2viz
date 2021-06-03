/*
 * Copyright (c) 2018-2021. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.math

import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

/**
 * Abstraction of an angle to have some more typesafe angle manipulations.
 */
public inline class Angle(
    public val rad: Double
){
    public val cos:Double get() = cos(rad)
    public val sin:Double get() = sin(rad)
    public val tan:Double get() = tan(rad)
    public val deg:Double get() = rad * RAD_TO_DEG

    public fun normalize():Angle =
            if (rad >= 0) Angle(rad % TAU_ANGLE.rad)
            else Angle((rad % TAU_ANGLE.rad) + TAU_ANGLE.rad)

    public operator fun plus(angle: Angle)     : Angle = Angle(rad + angle.rad)
    public operator fun minus(angle: Angle)    : Angle = Angle(rad - angle.rad)
    public operator fun times(d: Number)       : Angle = Angle(rad * d.toDouble())
    public operator fun div(d: Number)         : Angle = Angle(rad / d.toDouble())
    public operator fun unaryMinus()           : Angle = Angle(-rad)
    public operator fun div(other: Angle)      : Double = rad / other.rad

}


/**
 * Assuming this represents a value in degrees, converts the value to radians.
 */
public fun Double.toRadians(): Double = this * DEG_TO_RAD

/**
 * Assuming this represents a value in radians, converts the value to degrees.
 */
public fun Double.toDegrees(): Double = this * RAD_TO_DEG

/**
 * Extension property to create easily an angle from a number representing degrees
 */
public val Number.deg:Angle
    get() = Angle(toDouble() * DEG_TO_RAD)

/**
 * Extension property to create easily an angle from a number representing radians
 */
public val Number.rad:Angle
    get() = Angle(toDouble())


/**
 * Extension function operator on Number to allow 2 * PI.rad
 */
public operator fun Number.times(angle: Angle): Angle = Angle(angle.rad * this.toDouble())
