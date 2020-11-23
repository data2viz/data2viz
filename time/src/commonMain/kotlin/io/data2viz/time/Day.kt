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
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toInstant

public class Day : Interval(
    floor = fun(date: LocalDateTime): LocalDateTime = LocalDateTime(date.year, date.monthNumber, date.dayOfMonth, 0, 0, 0, 0),
    offset = fun(date: LocalDateTime, step: Int): LocalDateTime = date + DateTimePeriod(0, 0, step),
    count = fun(start: LocalDateTime, end: LocalDateTime): Int = start.toInstant(defaultTZ).daysUntil(end.toInstant(defaultTZ), defaultTZ),
    field = fun(date: LocalDateTime): Int = date.dayOfMonth - 1
)

public val timeDay = Day()