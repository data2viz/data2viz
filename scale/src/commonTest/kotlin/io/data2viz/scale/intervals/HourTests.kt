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

package io.data2viz.scale.intervals

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.test.Test

@Suppress("DEPRECATION")
class HourTests : TestDate() {

    private fun dateUTC(y: Int, mo: Int, d: Int, h: Int = 0, mi:Int = 0, s:Int = 0, ms: Int = 0) =
        LocalDateTime(y, mo, d, h, mi, s, ms).toInstant(TimeZone.UTC)

    @Test
    fun hour_floor_date_returns_hours() {
        val time = timeHour

        time.floor(TimeZone.UTC, dateUTC(2010, 12, 31, 23, 59)) shouldBe dateUTC(2010, 12, 31, 23, 0)
        time.floor(TimeZone.UTC, dateUTC(2011, 1, 1, 0, 0)) shouldBe dateUTC(2011, 1, 1, 0, 0)
        time.floor(TimeZone.UTC, dateUTC(2011, 1, 1, 0, 1)) shouldBe dateUTC(2011, 1, 1, 0, 0)
    }

    @Test
    fun hour_ceil_returns_hours() {
        with (timeHour) { TimeZone.UTC.ceil(dateUTC(2010, 12, 31, 23, 59)) } shouldBe dateUTC(2011, 1, 1, 0, 0)
        with (timeHour) { TimeZone.UTC.ceil(dateUTC(2011, 1, 1, 0, 0)) } shouldBe dateUTC(2011, 1, 1, 0, 0)
            with (timeHour) { TimeZone.UTC.ceil(dateUTC(2011, 1, 1, 0, 1)) } shouldBe dateUTC(2011, 1, 1, 1, 0)
    }

    @Test
    fun hour_offset_date_step_does_not_modify_the_passed_date() {
        val time = timeHour
        val date = dateUTC(2010, 12, 31, 23, 59, 59, 999)

        time.offset(TimeZone.UTC, date, 1)
        date shouldBe dateUTC(2010, 12, 31, 23, 59, 59, 999)
    }

    @Test
    fun hour_offset_date_step_does_not_round_the_passed_date() {
        val time = timeHour
        val date1 = dateUTC(2010, 12, 31, 23, 59, 59, 456)

        val date2 = dateUTC(2011, 1, 1, 0, 59, 59, 456)
        time.offset(TimeZone.UTC, date1, 1) shouldBe date2

        val date3 = dateUTC(2010, 12, 31, 21, 59, 59, 456)
        time.offset(TimeZone.UTC, date1, -2) shouldBe date3
    }

    @Test
    fun hour_offset_allows_negative_positive_zero_step() {
        val time = timeHour

        time.offset(TimeZone.UTC, dateUTC(2010, 12, 31, 12, 0), -1) shouldBe dateUTC(2010, 12, 31, 11, 0)
        time.offset(TimeZone.UTC, dateUTC(2011, 1, 1, 1, 0), -2) shouldBe dateUTC(2010, 12, 31, 23, 0)
        time.offset(TimeZone.UTC, dateUTC(2011, 1, 1, 0, 0), -1) shouldBe dateUTC(2010, 12, 31, 23, 0)

        time.offset(TimeZone.UTC, dateUTC(2010, 12, 31, 11, 0), 1) shouldBe dateUTC(2010, 12, 31, 12, 0)
        time.offset(TimeZone.UTC, dateUTC(2010, 12, 31, 23, 0), 2) shouldBe dateUTC(2011, 1, 1, 1, 0)
        time.offset(TimeZone.UTC, dateUTC(2010, 12, 31, 23, 0), 1) shouldBe dateUTC(2011, 1, 1, 0, 0)

        val date1 = dateUTC(2010, 12, 31, 23, 59, 59, 999)
        time.offset(TimeZone.UTC, date1, 0) shouldBe date1
        val date2 = dateUTC(2010, 12, 31, 23, 59, 58, 0)
        time.offset(TimeZone.UTC, date2, 0) shouldBe date2
    }

    @Test
    fun hour_range_start_stop_cans_kip_hours() {
        val time = timeHour
        val result = listOf(
                dateUTC(2011, 2, 1, 1, 0),
                dateUTC(2011, 2, 1, 4, 0),
                dateUTC(2011, 2, 1, 7, 0),
                dateUTC(2011, 2, 1, 10, 0)
        )

        val range = with(time) { TimeZone.UTC.range(dateUTC(2011, 2, 1, 1, 0), dateUTC(2011, 2, 1, 13, 0), 3) }
        range.forEachIndexed { index, r ->
            r shouldBe result[index]
        }
    }

    @Test
    fun hour_range_start_stop_returns_hours_between_start_inclusive_and_stop_exclusive() {
        val time = timeHour
        val result = listOf(
                dateUTC(2010, 12, 31, 23, 0),
                dateUTC(2011, 1, 1, 0, 0),
                dateUTC(2011, 1, 1, 1, 0)
        )

        val range = with(time) { TimeZone.UTC.range(dateUTC(2010, 12, 31, 23, 0), dateUTC(2011, 1, 1, 2, 0)) }
        range.forEachIndexed { index, r ->
            r shouldBe result[index]
        }
    }

    @Test
    fun hour_range_start_stop_returns_hours() {
        val time = timeHour
        val result = listOf(
                dateUTC(2010, 12, 31, 23, 0),
                dateUTC(2011, 1, 1, 0, 0),
                dateUTC(2011, 1, 1, 1, 0)
        )

        val range = with(time) { TimeZone.UTC.range(dateUTC(2010, 12, 31, 23, 0), dateUTC(2011, 1, 1, 2, 0)) }
        range.forEachIndexed { index, r ->
            r shouldBe result[index]
        }
    }

    @Test
    fun timeHour_floor_observes_start_of_DST() {
        timeHour.floor(TimeZone.UTC, dateUTC(2011, 3, 13,  8, 59)) shouldBe dateUTC(2011, 3, 13, 8)
        timeHour.floor(TimeZone.UTC, dateUTC(2011, 3, 13,  9, 0)) shouldBe dateUTC(2011, 3, 13, 9)
        timeHour.floor(TimeZone.UTC, dateUTC(2011, 3, 13,  9, 1)) shouldBe dateUTC(2011, 3, 13, 9)
        timeHour.floor(TimeZone.UTC, dateUTC(2011, 3, 13,  9, 59)) shouldBe dateUTC(2011, 3, 13, 9)
        timeHour.floor(TimeZone.UTC, dateUTC(2011, 3, 13, 10, 0)) shouldBe dateUTC(2011, 3, 13, 10)
        timeHour.floor(TimeZone.UTC, dateUTC(2011, 3, 13, 10, 1)) shouldBe dateUTC(2011, 3, 13, 10)
    }

    @Test
    fun timeHour_floor_observes_end_of_DST() {
        timeHour.floor(TimeZone.UTC, dateUTC(2011, 11, 6, 7, 59)) shouldBe dateUTC(2011, 11, 6, 7)
        timeHour.floor(TimeZone.UTC, dateUTC(2011, 11, 6, 8, 0)) shouldBe dateUTC(2011,  11, 6, 8)
        timeHour.floor(TimeZone.UTC, dateUTC(2011, 11, 6, 8, 1)) shouldBe dateUTC(2011,  11, 6, 8)
        timeHour.floor(TimeZone.UTC, dateUTC(2011, 11, 6, 8, 59)) shouldBe dateUTC(2011, 11, 6, 8)
        timeHour.floor(TimeZone.UTC, dateUTC(2011, 11, 6, 9, 0)) shouldBe dateUTC(2011,  11, 6, 9)
        timeHour.floor(TimeZone.UTC, dateUTC(2011, 11, 6, 9, 1)) shouldBe dateUTC(2011,  11, 6, 9)
    }

    @Test
    fun timeHour_ceil_observes_start_of_DST() {
        with (timeHour) { TimeZone.UTC.ceil(dateUTC(2011, 3, 13,  8, 59)) } shouldBe dateUTC(2011, 3, 13, 9)
        with (timeHour) { TimeZone.UTC.ceil(dateUTC(2011, 3, 13,  9, 0)) } shouldBe dateUTC(2011, 3, 13, 9)
        with (timeHour) { TimeZone.UTC.ceil(dateUTC(2011, 3, 13,  9, 1)) } shouldBe dateUTC(2011, 3, 13, 10)
        with (timeHour) { TimeZone.UTC.ceil(dateUTC(2011, 3, 13,  9, 59)) } shouldBe dateUTC(2011, 3, 13, 10)
        with (timeHour) { TimeZone.UTC.ceil(dateUTC(2011, 3, 13, 10, 0)) } shouldBe dateUTC(2011, 3, 13, 10)
        with (timeHour) { TimeZone.UTC.ceil(dateUTC(2011, 3, 13, 10, 1)) } shouldBe dateUTC(2011, 3, 13, 11)
    }

    @Test
    fun timeHour_ceil_observes_end_of_DST() {
        with (timeHour) { TimeZone.UTC.ceil(dateUTC(2011, 11, 6, 7, 59)) } shouldBe dateUTC(2011, 11, 6, 8)
        with (timeHour) { TimeZone.UTC.ceil(dateUTC(2011, 11, 6, 8, 0)) } shouldBe dateUTC(2011,  11, 6, 8)
        with (timeHour) { TimeZone.UTC.ceil(dateUTC(2011, 11, 6, 8, 1)) } shouldBe dateUTC(2011,  11, 6, 9)
        with (timeHour) { TimeZone.UTC.ceil(dateUTC(2011, 11, 6, 8, 59)) } shouldBe dateUTC(2011, 11, 6, 9)
        with (timeHour) { TimeZone.UTC.ceil(dateUTC(2011, 11, 6, 9, 0)) } shouldBe dateUTC(2011,  11, 6, 9)
        with (timeHour) { TimeZone.UTC.ceil(dateUTC(2011, 11, 6, 9, 1)) } shouldBe dateUTC(2011,  11, 6, 10)
    }

    @Test
    fun timeHour_range_observes_start_of_DST() {
        val start = LocalDateTime(2011, 3, 13, 1, 0).toInstant(TimeZone.of("America/Los_Angeles"))
        val end = LocalDateTime(2011, 3, 13, 5, 0).toInstant(TimeZone.of("America/Los_Angeles"))
        with(timeHour) { TimeZone.UTC.range(start, end) } shouldBe
                listOf(
                    dateUTC(2011, 3, 13, 9),
                    dateUTC(2011, 3, 13, 10),
                    dateUTC(2011, 3, 13, 11)
                )
    }

    @Test
    fun timeHour_range_observes_end_of_DST() {
        val start = LocalDateTime(2011, 11, 6, 0, 0).toInstant(TimeZone.of("America/Los_Angeles"))
        val end = LocalDateTime(2011, 11, 6, 2, 0).toInstant(TimeZone.of("America/Los_Angeles"))
        with(timeHour) { TimeZone.UTC.range(start, end) } shouldBe
                listOf(
                    dateUTC(2011, 11, 6, 7),
                    dateUTC(2011, 11, 6, 8),
                    dateUTC(2011, 11, 6, 9)
                )
    }

    @Test
    fun timeHour_step_returns_expected_result() {
        val start = dateUTC(2008, 12, 30, 12, 47)
        val end = dateUTC(2008, 12, 31, 13, 57)
        var every = with(timeHour) { TimeZone.UTC.every(4) }
        with(every) { TimeZone.UTC.range(start, end) } shouldBe
                listOf(
                    dateUTC(2008, 12, 30, 16),
                    dateUTC(2008, 12, 30, 20),
                    dateUTC(2008, 12, 31, 0),
                    dateUTC(2008, 12, 31, 4),
                    dateUTC(2008, 12, 31, 8),
                    dateUTC(2008, 12, 31, 12)
                )

        every = with(timeHour) { TimeZone.UTC.every(12) }
        with(every) { TimeZone.UTC.range(start, end) } shouldBe
                listOf(
                    dateUTC(2008, 12, 31, 0),
                    dateUTC(2008, 12, 31, 12)
                )
    }

    @Test
    fun timeHour_returns_hours_across_the_DST() {
        val start = Instant.fromEpochMilliseconds(1478422800000 - 2 * 3600000)
        val end = Instant.fromEpochMilliseconds(1478422800000 + 2 * 3600000)
        with (timeHour) { TimeZone.UTC.range(start, end) }.map { it.toEpochMilliseconds() } shouldBe
                listOf(
                    1478415600000L,     // Sun Nov 06 2016 00:00:00 GMT-0700 (PDT)
                    1478419200000L,     // Sun Nov 06 2016 01:00:00 GMT-0700 (PDT)
                    1478422800000L,     // Sun Nov 06 2016 01:00:00 GMT-0800 (PDT)
                    1478426400000L      // Sun Nov 06 2016 02:00:00 GMT-0800 (PDT)
                )
    }
}