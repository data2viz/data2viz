/*
 * Copyright (c) 2018-2021. data2viz sàrl.
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

package io.data2viz.scale.intervals

import kotlinx.datetime.*
import kotlin.test.Test
import kotlin.time.Duration.Companion.hours

class IntervalTests : TestDate() {

    @Test
    fun interval_floor_offset_returns_a_custom_time_interval() {
        val interval = Interval(
                fun TimeZone.(date:Instant): Instant {
                    val d = date.toLocalDateTime(TimeZone.UTC)
                    return LocalDateTime(d.year, d.monthNumber, d.dayOfMonth, d.hour, 0, 0, 0).toInstant(TimeZone.UTC)
                },
                fun TimeZone.(date:Instant, step:Int): Instant = date + step.hours,
        )

        val date1 = interval.floor(TimeZone.UTC, LocalDateTime(2015, 1, 1, 12, 34, 56, 789).toInstant(TimeZone.UTC))
        val date2 = LocalDateTime(2015, 1, 1, 12, 0).toInstant(TimeZone.UTC)
        date1 shouldBe date2
    }
}
