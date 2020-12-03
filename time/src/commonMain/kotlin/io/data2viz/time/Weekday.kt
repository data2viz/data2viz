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
import kotlin.time.days

public class Weekday(day: Int) : Interval(
    floor = fun TimeZone.(date: Instant): Instant {
        val d = date.toLocalDateTime(this)
        val weekFlooredDay = d.dayOfMonth - (d.dayOfWeek.ordinal + 7 - day) % 7
        return if (weekFlooredDay < 1)
            (LocalDateTime(d.year, d.month, 1, 0, 0) + DateTimePeriod(0, 0, weekFlooredDay - 1)).toInstant(this)
        else
            LocalDateTime(d.year, d.month, weekFlooredDay, 0, 0).toInstant(this)
    },
    offset = fun TimeZone.(date: Instant, step: Int): Instant = date + (step*7).days,
    count = fun TimeZone.(start: Instant, end: Instant): Long = (end - start).inDays.toLong() / 7
)

/**
 * Monday-based weeks (e.g., February 6, 2012 at 12:00 AM).
 */
public val timeMonday: Weekday = Weekday(0)

/**
 * Tuesday-based weeks (e.g., February 7, 2012 at 12:00 AM).
 */

public val timeTuesday: Weekday = Weekday(1)

/**
 * Wednesday-based weeks (e.g., February 8, 2012 at 12:00 AM).
 */
public val timeWednesday: Weekday = Weekday(2)

/**
 * Thursday-based weeks (e.g., February 9, 2012 at 12:00 AM).
 */
public val timeThursday: Weekday = Weekday(3)

/**
 * Friday-based weeks (e.g., February 10, 2012 at 12:00 AM).
 */
public val timeFriday: Weekday = Weekday(4)

/**
 * Saturday-based weeks (e.g., February 11, 2012 at 12:00 AM).
 */
public val timeSaturday: Weekday = Weekday(5)


/**
 * Sunday-based weeks (e.g., February 5, 2012 at 12:00 AM).
 */
public val timeSunday: Weekday = Weekday(6)
