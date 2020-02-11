/*
 * Copyright (c) 2018-2019. data2viz sàrl.
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



const val EPSILON = 1e-6
const val EPSILON2 = EPSILON * EPSILON

const val PI = kotlin.math.PI
const val HALFPI = PI / 2.0
const val THIRDPI = PI / 3.0
const val QUARTERPI = PI / 4.0

val ANGLE_ZERO        = .0.rad
val ANGLE_EPSILON     = 1e-6.rad
val ANGLE_PI          = PI.rad
val ANGLE_HALFPI      = HALFPI.rad
val ANGLE_THIRDPI     = THIRDPI.rad
val ANGLE_QUARTERPI   = QUARTERPI.rad

const val TAU = PI * 2.0
const val TAU_EPSILON = TAU - EPSILON

const val DEG_TO_RAD = kotlin.math.PI / 180
const val RAD_TO_DEG = 180 / kotlin.math.PI


val PI_ANGLE = Angle(kotlin.math.PI)
val HALFPI_ANGLE = PI_ANGLE / 2
val TAU_ANGLE = PI_ANGLE * 2

