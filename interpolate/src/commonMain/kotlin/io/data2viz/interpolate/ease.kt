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

package io.data2viz.interpolate

import io.data2viz.math.HALFPI_ANGLE
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sqrt

// TODO should be interpolators
// TODO : should also use Percent
public fun quad(x:Double): Double = x * x
public fun cubicIn(x:Double): Double = x * x * x
public fun cubicOut(t:Double): Double = (t-1)*(t-1)*(t-1) + 1
public fun cubicInOut(t:Double): Double =  (if(t <= .5) (8*t*t*t) else ((2*t-2)*(2*t-2)*(2*t-2)+2))/2


public fun sin(x:Double): Double = 1 - cos(x * HALFPI_ANGLE.rad)
public fun poly(e:Double): (Double) -> Double = { t:Double -> t.pow(e)}


public fun circleIn(t:Double): Double = 1 - sqrt(1 - (t * t).coerceAtMost(1.0))
public fun circleOut(t:Double): Double = sqrt(1 - (t-1)*(t-1));
