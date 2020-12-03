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

package io.data2viz.time

import kotlinx.datetime.*
import kotlin.time.hours

public class Hour : Interval(
    floor = fun TimeZone.(date: Instant): Instant {
        val d = date.toLocalDateTime(this)
        return LocalDateTime (d.year, d.monthNumber, d.dayOfMonth, d.hour, 0, 0, 0).toInstant(this)
    },
    offset = fun TimeZone.(date: Instant, step: Int): Instant = date + step.hours,
    count = fun TimeZone.(start: Instant, end: Instant): Long = start.until(end, DateTimeUnit.HOUR, this),
    field = fun TimeZone.(date: Instant): Int = date.toLocalDateTime(this).hour
)

public val timeHour: Hour = Hour()