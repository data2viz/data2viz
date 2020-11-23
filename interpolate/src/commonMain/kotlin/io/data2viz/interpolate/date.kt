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

import io.data2viz.math.Percent
import io.data2viz.time.defaultTZ
import io.data2viz.time.plus
import kotlinx.datetime.*

// TODO optimize using DateTimePeriod ?
public fun interpolateDate(start: LocalDateTime, end: LocalDateTime): Interpolator<LocalDateTime>{
    val fromInstant = start.toInstant(defaultTZ)
    val range = fromInstant.until(end.toInstant(defaultTZ), DateTimeUnit.MILLISECOND, defaultTZ).toDouble()
    return { percent -> start + DateTimePeriod(0, 0, 0, 0, 0, 0, (range * percent.value * 1_000_000).toLong()) }
}

// TODO optimize using DateTimePeriod ?
public fun uninterpolateDate(start: LocalDateTime, end: LocalDateTime): UnInterpolator<LocalDateTime> {
    val fromInstant = start.toInstant(defaultTZ)
    val range = fromInstant.until(end.toInstant(defaultTZ), DateTimeUnit.MILLISECOND, defaultTZ).toDouble()
    val nullRange = (start == end)
    return { date ->
        if (nullRange) Percent(.0)
        else {
            val diff = fromInstant.until(date.toInstant(defaultTZ), DateTimeUnit.MILLISECOND, defaultTZ).toDouble()
            Percent(diff / range)
        }
    }
}