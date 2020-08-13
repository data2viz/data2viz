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

val e10: Double by lazy { sqrt(50.0) }
val e5: Double by lazy { sqrt(10.0) }
val e2: Double by lazy { sqrt(2.0) }
val ln10: Double by lazy { ln(10.0) }

fun ticks(start: Double, stop: Double, count: Int): List<Double> {
    if (count <= 0) return listOf()
    if (start.isNaN() || stop.isNaN()) return listOf()
    if (start == stop) return listOf(start)

    var from = start
    var to = stop
    val reverse = to < from
    if (reverse) {
        val temp = from
        from = to
        to = temp
    }
    var step = tickIncrement(from, to, count)
    if (step == .0) return listOf()

    lateinit var ticks: List<Double>
    val range = if (start <= stop) (start..stop) else (stop..start)
    if (step > 0) {
        from = ceil(from / step)
        to = floor(to / step)
        val numTicks = ceil(to - from + 1).toInt()
        ticks = (0 until numTicks).map { (from + it) * step }.filter { range.contains(it) }
    } else {
        step = -step
        from = floor(from * step)
        to = ceil(to * step)
        val numTicks = ceil(to - from + 1).toInt()
        ticks = (0 until numTicks).map { (from + it) / step }.filter { range.contains(it) }
    }
    return if (reverse) ticks.reversed() else ticks
}

fun tickIncrement(start: Double, stop: Double, count: Int): Double {
    val step = (stop - start) / count
    val power = floor(ln(step) / ln10)
    val error = step / 10.0.pow(power)
    return if (power >= 0) {
        getValueForError(error) * 10.0.pow(power)
    } else {
        -(10.0.pow(-power)) / getValueForError(error)
    }
}

fun tickStep(start: Double, stop: Double, count: Int): Double {
    val step0 = abs(stop - start) / count
    var step1 = 10.0.pow(floor(ln(step0) / ln10))
    val error = step0 / step1
    step1 *= getValueForError(error)
    return if (stop < start) -step1 else step1
}

fun range(start: Double, stop: Double, step: Double = 1.0): List<Double> {
    val n = if (step == .0) 0 else maxOf(0, ceil((stop - start) / step).toInt())
    return (0 until n).map { start + it * step }
}

private fun getValueForError(error: Double): Int {
    return if (error >= e10) 10 else if (error >= e5) 5 else if (error >= e2) 2 else 1
}