package io.data2viz.math



const val EPSILON = 1e-6
const val EPSILON2 = EPSILON * EPSILON

const val PI = kotlin.math.PI
const val HALFPI = PI / 2.0
const val THIRDPI = PI / 3.0
const val QUARTERPI = PI / 4.0

const val TAU = PI * 2.0
const val TAU_EPSILON = TAU - EPSILON

const val DEG_TO_RAD = kotlin.math.PI / 180
const val RAD_TO_DEG = 180 / kotlin.math.PI


val PI_ANGLE = Angle(kotlin.math.PI)
val HALFPI_ANGLE = PI_ANGLE / 2
val TAU_ANGLE = PI_ANGLE * 2

