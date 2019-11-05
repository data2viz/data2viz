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

package io.data2viz.shape

//import io.data2viz.math.Angle
import kotlin.math.PI


val epsilon = 1e-12
val pi = PI
val halfPi = pi / 2
val tau = 2 * pi

fun acos(x: Double) = if (x > 1.0)  0.0     else if (x < -1) pi         else kotlin.math.acos(x)
fun asin(x: Double) = if (x >= 1.0) halfPi  else if (x <= -1.0) -halfPi else kotlin.math.asin(x)

//fun acos(a: Angle) = if (a.rad > 1.0)  0.0     else if (a.rad < -1) pi         else a.acos
//fun asin(a: Angle) = if (a.rad >= 1.0) halfPi  else if (a.rad <= -1.0) -halfPi else a.asin