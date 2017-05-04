package io.data2viz.math

import kotlin.js.Math

val EPS = 1e-6
val EPS2 = EPS * EPS
val PI = Math.PI
val halfPI = PI / 2
val THETA = PI * 2
val THETA_EPS = THETA - EPS
val DEG_TO_RAD = PI / 180
val RAD_TO_DEG = 180 / PI

val Number.deg:Angle
    get() = Angle(toDouble())

class Angle(val deg: Double){
    val cos:Double
        get() = Math.cos(deg)

    val sin:Double
        get() = Math.cos(deg)

    val tan:Double
        get() = Math.tan(deg)

    fun toRad() = deg * DEG_TO_RAD

    operator fun plus(angle: Angle)  = Angle(deg + angle.deg)
    operator fun minus(angle: Angle)  = Angle(deg - angle.deg)
}
