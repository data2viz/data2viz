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

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlin.time.ExperimentalTime

@ExperimentalTime
class Weekday(day: Int) : Interval(
        fun(date: LocalDateTime): LocalDateTime {
            val dayofMonth = (date.dayOfMonth - (date.dayOfWeek.ordinal + 7 - day) % 7) + 1
            return if (dayofMonth >= 1) {
                LocalDateTime(date.year, date.monthNumber, dayofMonth, 0, 0, 0, 0)
            } else {
                date + (DateTimeUnit.HOUR.duration * 24 * (dayofMonth - 2))
            }
        },
        fun(date: LocalDateTime, step: Int): LocalDateTime = date + (DateTimeUnit.HOUR * 24 * 7 * step).duration,
        fun(start: LocalDateTime, end: LocalDateTime): Int = ((end - start).inDays.toInt() / 7)
)

// TODO TESTS seems to be a bug as timeSunday returns "mondays"

// The value follows the ISO-8601 standard, from 1 (Monday) to 7 (Sunday).
@ExperimentalTime
val timeMonday = Weekday(1)
@ExperimentalTime
val timeTuesday = Weekday(2)
@ExperimentalTime
val timeWednesday = Weekday(3)
@ExperimentalTime
val timeThursday = Weekday(4)
@ExperimentalTime
val timeFriday = Weekday(5)
@ExperimentalTime
val timeSaturday = Weekday(6)
@ExperimentalTime
val timeSunday = Weekday(7)
