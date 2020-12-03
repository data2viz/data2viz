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

public class Month : Interval(
        floor = fun TimeZone.(date: Instant): Instant {
                val d = date.toLocalDateTime(this)
                return LocalDateTime(d.year, d.monthNumber, 1, 0, 0, 0, 0).toInstant(this)
        },
        offset = fun TimeZone.(date: Instant, step: Int): Instant =
                (date.toLocalDateTime(this) + DateTimePeriod(0, step)).toInstant(this),
        count = fun TimeZone.(start: Instant, end: Instant): Long = start.monthsUntil(end, this).toLong(),
        field = fun TimeZone.(date: Instant): Int = date.toLocalDateTime(this).monthNumber - 1
)

public val timeMonth: Month = Month()