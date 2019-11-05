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

import kotlin.math.*

val e10 = sqrt(50.0)
val e5 = sqrt(10.0)
val e2 = sqrt(2.0)
val ln10:Double by lazy { ln(10.0) }

fun ticks(start:Number, stop: Number, count :Int): List<Double> {
    // require(count > 0, { "Ticks count must be greater than 0." }) // TODO check or return empty array ?
    val step = tickStep(start.toDouble(), stop.toDouble(), count)
    return range(
            ceil(start.toDouble() / step) * step,
            floor(stop.toDouble() / step) * step + step / 2, // inclusive
            step
    )
}

fun tickStep(start:Double, stop:Double, count:Int): Double {
    val step0 = abs(stop -start) / count
    var step1 = 10.0.pow(floor(ln(step0) / ln10))
    val error = step0 / step1
    if(error >= e10) step1 *=10
    else if(error >= e5) step1 *=5
    else if(error >= e2) step1 *=2
    return if (stop < start) -step1 else step1
}

fun range (start:Double, stop:Double, step: Double = 1.0): List<Double> {
    val n = maxOf(0, ceil((stop - start) / step).toInt())
    return  (0..n-1).map { start + it*step }
}
