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
class DayTests : TestDate() {

    private val zoneUTC = TimeZone.UTC
    private fun dateUTC(y: Int, mo: Int, d: Int, h: Int = 0, mi:Int = 0, s:Int = 0, ms: Int = 0): Instant {
        return LocalDateTime(y, mo, d, h, mi, s, ms).toInstant(zoneUTC)
    }

    private val zoneLocal = zoneLocal()
    private fun dateLocal(y: Int, mo: Int, d: Int, h: Int = 0, mi:Int = 0, s:Int = 0, ms: Int = 0): Instant {
        return LocalDateTime(y, mo, d, h, mi, s, ms).toInstant(zoneLocal)
    }


    @Test
    fun day_floor_date_returns_days() {
        Intervals.timeDay.floor(zoneUTC, dateUTC(2010, 12, 31, 23, 0)) shouldBe dateUTC(2010, 12, 31, 0, 0)
        Intervals.timeDay.floor(zoneUTC, dateUTC(2011, 1, 1, 0, 0)) shouldBe dateUTC(2011, 1, 1, 0, 0)
        Intervals.timeDay.floor(zoneUTC, dateUTC(2011, 1, 1, 1, 0)) shouldBe dateUTC(2011, 1, 1, 0, 0)
    }

    @Test
    fun day_handles_years_in_first_century() {
        Intervals.timeDay.floor(zoneUTC, dateUTC(11, 10, 6, 7, 0)) shouldBe dateUTC(11, 10, 6, 0, 0)
    }

    @Test
    fun day_round_date_returns_days() {
        with(Intervals.timeDay) {
            zoneUTC.round(dateUTC(2010, 12, 30, 13, 0)) shouldBe dateUTC(2010, 12, 31, 0, 0)
            zoneUTC.round(dateUTC(2010, 12, 30, 11, 0)) shouldBe dateUTC(2010, 12, 30, 0, 0)
        }
    }

    @Test
    fun day_round_date_handles_midnight_in_leap_years_days() {
        with(Intervals.timeDay) {
            zoneUTC.round(dateUTC(2012, 3, 1, 0, 0)) shouldBe dateUTC(2012, 3, 1, 0, 0)
        }
    }

    @Test
    fun day_ceil_returns_day() {
        with(Intervals.timeDay) {
            zoneUTC.ceil(dateUTC(2010, 12, 30, 23, 0)) shouldBe dateUTC(2010, 12, 31, 0, 0)
            zoneUTC.ceil(dateUTC(2010, 12, 31, 0, 0)) shouldBe dateUTC(2010, 12, 31, 0, 0)
            zoneUTC.ceil(dateUTC(2010, 12, 31, 1, 0)) shouldBe dateUTC(2011, 1, 1, 0, 0)
        }
    }

    @Test
    fun day_offset_date_step_does_not_modify_the_passed_date() {
        val date = dateUTC(2010, 12, 31, 23, 59, 59, 999)

        Intervals.timeDay.offset(zoneUTC, date, 1)
        date shouldBe dateUTC(2010, 12, 31, 23, 59, 59, 999)
    }

    @Test
    fun day_offset_date_step_does_not_round_the_passed_date() {
        val date1 = dateUTC(2010, 12, 31, 23, 59, 59, 456)

        val date2 = dateUTC(2011, 1, 1, 23, 59, 59, 456)
        Intervals.timeDay.offset(zoneUTC, date1, 1) shouldBe date2

        val date3 = dateUTC(2010, 12, 29, 23, 59, 59, 456)
        Intervals.timeDay.offset(zoneUTC, date1, -2) shouldBe date3
    }

    @Test
    fun day_offset_allows_negative_positive_zero_step() {
        val time = Intervals.timeDay

        time.offset(zoneUTC, dateUTC(2010, 12, 31, 0, 0), -1) shouldBe dateUTC(2010, 12, 30, 0, 0)
        time.offset(zoneUTC, dateUTC(2011, 1, 1, 0, 0), -2) shouldBe dateUTC(2010, 12, 30, 0, 0)
        time.offset(zoneUTC, dateUTC(2011, 1, 1, 0, 0), -1) shouldBe dateUTC(2010, 12, 31, 0, 0)

        time.offset(zoneUTC, dateUTC(2010, 12, 30, 0, 0), 1) shouldBe dateUTC(2010, 12, 31, 0, 0)
        time.offset(zoneUTC, dateUTC(2010, 12, 30, 0, 0), 2) shouldBe dateUTC(2011, 1, 1, 0, 0)
        time.offset(zoneUTC, dateUTC(2010, 12, 31, 0, 0), 1) shouldBe dateUTC(2011, 1, 1, 0, 0)

        val date1 = dateUTC(2010, 12, 31, 23, 59, 59, 999)
        time.offset(zoneUTC, date1, 0) shouldBe date1
        val date2 = dateUTC(2010, 12, 31, 23, 59, 58, 0)
        time.offset(zoneUTC, date2, 0) shouldBe date2
    }

    @Test
    fun day_range_start_stop_returns_days_between_start_inclusive_and_stop_exclusive() {
        val result = listOf(
            dateUTC(2011, 11, 4, 0, 0),
            dateUTC(2011, 11, 5, 0, 0),
            dateUTC(2011, 11, 6, 0, 0),
            dateUTC(2011, 11, 7, 0, 0),
            dateUTC(2011, 11, 8, 0, 0),
            dateUTC(2011, 11, 9, 0, 0)
        )

        with(Intervals.timeDay) {
            val range = zoneUTC.range(dateUTC(2011, 11, 4, 0, 0), dateUTC(2011, 11, 10, 0, 0))
            range.forEachIndexed { index, r ->
                r shouldBe result[index]
            }
        }
    }

    @Test
    fun day_range_start_stop_returns_days() {
        val result = listOf(
            dateUTC(2011, 11,  5, 0, 0),
            dateUTC(2011, 11,  6, 0, 0),
            dateUTC(2011, 11,  7, 0, 0),
            dateUTC(2011, 11,  8, 0, 0),
            dateUTC(2011, 11,  9, 0, 0),
            dateUTC(2011, 11, 10, 0, 0)
        )

        with(Intervals.timeDay) {
            val range = zoneUTC.range(dateUTC(2011, 11, 4, 2, 0, 0), dateUTC(2011, 11, 10, 13, 0, 0))
            range.forEachIndexed { index, r ->
                r shouldBe result[index]
            }
        }
    }

    @Test
    fun day_range_return_empty_array_if_start_later_or_equal_than_stop() {
        with(Intervals.timeDay) {
            zoneUTC.range(dateUTC(2011, 11, 10, 0, 0), dateUTC(2011, 11, 4, 0, 0)) shouldBe listOf()
            zoneUTC.range(dateUTC(2011, 11, 10, 0, 0), dateUTC(2011, 11, 10, 0, 0)) shouldBe listOf()
        }
    }

    @Test
    fun day_range_start_stop_step_returns_every_step_days() {
        val result = listOf(
            dateUTC(2011, 11,  5, 0, 0),
            dateUTC(2011, 11,  8, 0, 0),
            dateUTC(2011, 11, 11, 0, 0),
            dateUTC(2011, 11, 14, 0, 0)
        )

        with(Intervals.timeDay) {
            val range = zoneUTC.range(dateUTC(2011, 11, 4, 2, 0, 0), dateUTC(2011, 11, 14, 13, 0), 3)
            range.forEachIndexed { index, r ->
                r shouldBe result[index]
            }
        }
    }

    @Test
    fun day_range_return_empty_array_if_step_is_zero_or_negative() {
        with(Intervals.timeDay) {
            zoneUTC.range(dateUTC(2011, 1, 1, 0, 0), dateUTC(2011, 5, 9, 0, 0), 0) shouldBe listOf()
            zoneUTC.range(dateUTC(2011, 1, 1, 0, 0), dateUTC(2011, 5, 9, 0, 0), -1) shouldBe listOf()
        }
    }

    // TODO fix the commented tests
    @Test
    fun day_count_start_end_counts_days_after_start_exclusive_and_before_end_inclusive() {
        Intervals.timeDay.count(zoneLocal, dateLocal(2011, 1, 1, 0), dateLocal(2011, 5, 9, 0, 0)) shouldBe 128
//        timeDay.count(zoneLocal, dateLocal(2011, 1, 1, 1, 0), dateLocal(2011, 5, 9, 0, 0)) shouldBe 128
//        timeDay.count(zoneLocal, dateLocal(2010, 12, 31, 23), dateLocal(2011, 5, 9, 0, 0)) shouldBe 129
        Intervals.timeDay.count(zoneLocal, dateLocal(2011, 1, 1, 0), dateLocal(2011, 5, 8, 23, 0)) shouldBe 127
        Intervals.timeDay.count(zoneLocal, dateLocal(2011, 1, 1, 0), dateLocal(2011, 5, 9, 1, 0)) shouldBe 128
    }

    @Test
    fun day_count_start_end_observes_daylight_saving() {
        Intervals.timeDay.count(zoneUTC, dateUTC(2011, 1, 1, 0, 0), dateUTC(2011, 3, 13, 1, 0)) shouldBe 71
        Intervals.timeDay.count(zoneUTC, dateUTC(2011, 1, 1, 0, 0), dateUTC(2011, 3, 13, 3, 0)) shouldBe 71
        Intervals.timeDay.count(zoneUTC, dateUTC(2011, 1, 1, 0, 0), dateUTC(2011, 3, 13, 4, 0)) shouldBe 71
        Intervals.timeDay.count(zoneUTC, dateUTC(2011, 1, 1, 0, 0), dateUTC(2011, 11, 6, 0, 0)) shouldBe 309
        Intervals.timeDay.count(zoneUTC, dateUTC(2011, 1, 1, 0, 0), dateUTC(2011, 11, 6, 1, 0)) shouldBe 309
        Intervals.timeDay.count(zoneUTC, dateUTC(2011, 1, 1, 0, 0), dateUTC(2011, 11, 6, 2, 0)) shouldBe 309
    }

    @Test
    fun day_count_start_end_does_not_exhibit_floating_point_error() {
        val timeDay = Intervals.timeDay
        val timeYear = Intervals.timeYear
        val date = dateUTC(2011, 5, 9, 0, 0)

        timeDay.count(zoneUTC, timeYear.floor(zoneUTC, date), date) shouldBe 128
    }

    @Test
    fun day_count_start_end_returns_364_or_365_for_a_full_year() {
        val time = Intervals.timeDay

        time.count(zoneUTC, dateUTC(1999, 1, 1, 0, 0), dateUTC(1999, 12, 31, 0, 0)) shouldBe 364
        time.count(zoneUTC, dateUTC(2000, 1, 1, 0, 0), dateUTC(2000, 12, 31, 0, 0)) shouldBe 365 // leap year
        time.count(zoneUTC, dateUTC(2001, 1, 1, 0, 0), dateUTC(2001, 12, 31, 0, 0)) shouldBe 364
        time.count(zoneUTC, dateUTC(2002, 1, 1, 0, 0), dateUTC(2002, 12, 31, 0, 0)) shouldBe 364
        time.count(zoneUTC, dateUTC(2003, 1, 1, 0, 0), dateUTC(2003, 12, 31, 0, 0)) shouldBe 364
        time.count(zoneUTC, dateUTC(2004, 1, 1, 0, 0), dateUTC(2004, 12, 31, 0, 0)) shouldBe 365 // leap year
        time.count(zoneUTC, dateUTC(2005, 1, 1, 0, 0), dateUTC(2005, 12, 31, 0, 0)) shouldBe 364
        time.count(zoneUTC, dateUTC(2006, 1, 1, 0, 0), dateUTC(2006, 12, 31, 0, 0)) shouldBe 364
        time.count(zoneUTC, dateUTC(2007, 1, 1, 0, 0), dateUTC(2007, 12, 31, 0, 0)) shouldBe 364
        time.count(zoneUTC, dateUTC(2008, 1, 1, 0, 0), dateUTC(2008, 12, 31, 0, 0)) shouldBe 365 // leap year
        time.count(zoneUTC, dateUTC(2009, 1, 1, 0, 0), dateUTC(2009, 12, 31, 0, 0)) shouldBe 364
        time.count(zoneUTC, dateUTC(2010, 1, 1, 0, 0), dateUTC(2010, 12, 31, 0, 0)) shouldBe 364
        time.count(zoneUTC, dateUTC(2011, 1, 1, 0, 0), dateUTC(2011, 12, 31, 0, 0)) shouldBe 364
    }

    @Test
    fun day_every_step_returns_every_stepth_day_starting_with_the_first_day_of_the_month() {
        with(Intervals.timeDay) {
            var result = listOf(
                dateUTC(2008, 12, 31, 0, 0),
                dateUTC(2009, 1, 1, 0, 0),
                dateUTC(2009, 1, 4, 0, 0)
            )
            var every = zoneUTC.every(3)
            var range = with(every) { zoneUTC.range(dateUTC(2008, 12, 30, 0, 12), dateUTC(2009, 1, 5, 23, 48)) }
            range.forEachIndexed { index, r ->
                r shouldBe result[index]
            }

            result = listOf(
                dateUTC(2008, 12, 31, 0, 0),
                dateUTC(2009, 1, 1, 0, 0),
                dateUTC(2009, 1, 6, 0, 0)
            )
            every = zoneUTC.every(5)
            range = with(every) { zoneUTC.range(dateUTC(2008, 12, 30, 0, 12), dateUTC(2009, 1, 6, 23, 48)) }
            range.forEachIndexed { index, r ->
                r shouldBe result[index]
            }

            result = listOf(
                dateUTC(2009, 1, 1, 0, 0),
                dateUTC(2009, 1, 8, 0, 0)
            )
            every = zoneUTC.every(7)
            range = with(every) { zoneUTC.range(dateUTC(2008, 12, 30, 0, 12), dateUTC(2009, 1, 8, 23, 48)) }
            range.forEachIndexed { index, r ->
                r shouldBe result[index]
            }
        }
    }

    @Test
    fun day_floor_handles_DST() {
        Intervals.timeDay.floor(zoneLocal, dateUTC(2011, 3, 13, 7, 0)) shouldBe dateLocal(2011, 3, 12, 0, 0)
        Intervals.timeDay.floor(zoneLocal, dateUTC(2011, 3, 13, 8, 0)) shouldBe dateLocal(2011, 3, 13, 0, 0)
        Intervals.timeDay.floor(zoneLocal, dateUTC(2011, 3, 13, 9, 0)) shouldBe dateLocal(2011, 3, 13, 0, 0)
        Intervals.timeDay.floor(zoneLocal, dateUTC(2011, 3, 13, 10, 0)) shouldBe dateLocal(2011, 3, 13, 0, 0)
        Intervals.timeDay.floor(zoneLocal, dateUTC(2011, 11, 6, 7, 0)) shouldBe dateLocal(2011, 11, 6, 0, 0)
        Intervals.timeDay.floor(zoneLocal, dateUTC(2011, 11, 6, 8, 0)) shouldBe dateLocal(2011, 11, 6, 0, 0)
        Intervals.timeDay.floor(zoneLocal, dateUTC(2011, 11, 6, 9, 0)) shouldBe dateLocal(2011, 11, 6, 0, 0)
        Intervals.timeDay.floor(zoneLocal, dateUTC(2011, 11, 6, 10, 0)) shouldBe dateLocal(2011, 11, 6, 0, 0)
    }

    // TODO fix the commented tests
    @Test
    fun day_round_handles_DST() {
        with(Intervals.timeDay) { zoneLocal.round(dateUTC(2011, 3, 13, 7, 0)) } shouldBe dateLocal(2011, 3, 13, 0, 0)
        with(Intervals.timeDay) { zoneLocal.round(dateUTC(2011, 3, 13, 8, 0)) } shouldBe dateLocal(2011, 3, 13, 0, 0)
        with(Intervals.timeDay) { zoneLocal.round(dateUTC(2011, 3, 13, 9, 0)) } shouldBe dateLocal(2011, 3, 13, 0, 0)
        with(Intervals.timeDay) { zoneLocal.round(dateUTC(2011, 3, 13, 20, 0)) } shouldBe dateLocal(2011, 3, 14, 0, 0)
        with(Intervals.timeDay) { zoneLocal.round(dateUTC(2011, 11, 6, 7, 0)) } shouldBe dateLocal(2011, 11, 6, 0, 0)
        with(Intervals.timeDay) { zoneLocal.round(dateUTC(2011, 11, 6, 8, 0)) } shouldBe dateLocal(2011, 11, 6, 0, 0)
        with(Intervals.timeDay) { zoneLocal.round(dateUTC(2011, 11, 6, 9, 0)) } shouldBe dateLocal(2011, 11, 6, 0, 0)
//        with(Intervals.timeDay) { zoneLocal.round(dateUTC(2011, 11, 6, 20, 0)) } shouldBe dateLocal(2011, 11, 7, 0, 0)
    }

    // TODO fix the commented tests
    @Test
    fun day_ceil_handles_DST() {
        with(Intervals.timeDay) { zoneLocal.ceil(dateUTC(2011, 3, 13, 7, 0)) } shouldBe dateLocal(2011, 3, 13, 0, 0)
        with(Intervals.timeDay) { zoneLocal.ceil(dateUTC(2011, 3, 13, 8, 0)) } shouldBe dateLocal(2011, 3, 13, 0, 0)
        with(Intervals.timeDay) { zoneLocal.ceil(dateUTC(2011, 3, 13, 9, 0)) } shouldBe dateLocal(2011, 3, 14, 0, 0)
        with(Intervals.timeDay) { zoneLocal.ceil(dateUTC(2011, 3, 13, 20, 0)) } shouldBe dateLocal(2011, 3, 14, 0, 0)

        with(Intervals.timeDay) { zoneLocal.ceil(dateUTC(2011, 11, 6, 7, 0)) } shouldBe dateLocal(2011, 11, 6, 0, 0)
//        with(Intervals.timeDay) { zoneLocal.ceil(dateUTC(2011, 11, 6, 8, 0)) } shouldBe dateLocal(2011, 11, 7, 0, 0)
//        with(Intervals.timeDay) { zoneLocal.ceil(dateUTC(2011, 11, 6, 9, 0)) } shouldBe dateLocal(2011, 11, 7, 0, 0)
//        with(Intervals.timeDay) { zoneLocal.ceil(dateUTC(2011, 11, 6, 10, 0)) } shouldBe dateLocal(2011, 11, 7, 0, 0)
    }

    @Test
    fun day_ceil_handles_midnight_leap_years() {
        with(Intervals.timeDay) { zoneLocal.ceil(dateUTC(2012, 3, 1, 0, 0)) } shouldBe dateLocal(2012, 3, 1, 0, 0)
    }

}