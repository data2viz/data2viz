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

import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.LocalDateTime

class Weekday(day: Int) : Interval(
    floor = fun(date: LocalDateTime): LocalDateTime {
        val weekFlooredDay = date.dayOfMonth - (date.dayOfWeek.ordinal + 7 - day) % 7
        return if (weekFlooredDay < 1) LocalDateTime(date.year, date.month, 1, 0, 0) + DateTimePeriod(0, 0, weekFlooredDay - 1)
        else LocalDateTime(date.year, date.month, weekFlooredDay, 0, 0)
    },
    offset = fun(date: LocalDateTime, step: Int): LocalDateTime = date + DateTimePeriod(0, 0, step * 7),
    count = fun(start: LocalDateTime, end: LocalDateTime): Int = (end - start).days
)

val timeMonday = Weekday(0)
val timeTuesday = Weekday(1)
val timeWednesday = Weekday(2)
val timeThursday = Weekday(3)
val timeFriday = Weekday(4)
val timeSaturday = Weekday(5)
val timeSunday = Weekday(6)
