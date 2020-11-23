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



public const val EPSILON        :Double = 1e-6
public const val EPSILON2       :Double = EPSILON * EPSILON
public const val PI             :Double = kotlin.math.PI
public const val HALFPI         :Double = PI / 2.0
public const val THIRDPI        :Double = PI / 3.0
public const val QUARTERPI      :Double = PI / 4.0
public const val TAU            :Double = PI * 2.0
public const val TAU_EPSILON    :Double = TAU - EPSILON
public const val DEG_TO_RAD     :Double = kotlin.math.PI / 180
public const val RAD_TO_DEG     :Double = 180 / kotlin.math.PI


public val PI_ANGLE             :Angle = Angle(kotlin.math.PI)
public val HALFPI_ANGLE         :Angle = PI_ANGLE / 2
public val TAU_ANGLE            :Angle = PI_ANGLE * 2

