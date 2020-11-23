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

package io.data2viz.interpolate

import io.data2viz.math.*
import kotlin.math.round

/**
 * An interpolator transforms a normalized continuous range (0% -> 100%) to an object T
 */
public typealias Interpolator<T> = (Percent) -> T

/**
 * An un-interpolator transforms an object T to a normalized continuous range (0% -> 100%)
 */
public typealias UnInterpolator<T> = (T) -> Percent

public fun interpolateNumber(start: Double, end: Double): Interpolator<Double>{
    val diff = end - start
    return { percent -> start + percent.value * diff }
}


public fun interpolateRound(start: Double, end: Double): Interpolator<Double> {
    val diff = end - start
    return { percent -> round(start + percent.value * diff) }
}

public fun uninterpolateNumber(start: Double, end: Double): UnInterpolator<Double> {
    val diff = end - start
    return if (diff != .0) { double -> Percent((double - start) / diff) }  else { _ -> 0.pct }
}

public fun identity(percent: Percent): Double = percent.value