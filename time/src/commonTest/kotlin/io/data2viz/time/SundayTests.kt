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

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.test.Test

@Suppress("DEPRECATION")
class SundayTests : TestDate() {

    private val zoneUTC = TimeZone.UTC
    private fun dateUTC(y: Int, mo: Int, d: Int, h: Int = 0, mi:Int = 0, s:Int = 0, ms: Int = 0) =
        LocalDateTime(y, mo, d, h, mi, s, ms).toInstant(zoneUTC)

    private val zoneLocal = TimeZone.of("America/Los_Angeles")
    private fun dateLocal(y: Int, mo: Int, d: Int, h: Int = 0, mi:Int = 0, s:Int = 0, ms: Int = 0) =
        LocalDateTime(y, mo, d, h, mi, s, ms).toInstant(zoneLocal)

    @Test
    fun timeSunday_floor_returns_Sundays() {
        timeSunday.floor(zoneUTC, dateUTC(2010, 12, 31, 23, 59, 59)) shouldBe dateUTC(2010, 12, 26)
        timeSunday.floor(zoneUTC, dateUTC(2011,  1,  1,  0,  0,  0)) shouldBe dateUTC(2010, 12, 26)
        timeSunday.floor(zoneUTC, dateUTC(2011,  1,  1,  0,  0,  1)) shouldBe dateUTC(2010, 12, 26)
        timeSunday.floor(zoneUTC, dateUTC(2011,  1,  1, 23, 59, 59)) shouldBe dateUTC(2010, 12, 26)
        timeSunday.floor(zoneUTC, dateUTC(2011,  1,  2,  0,  0,  0)) shouldBe dateUTC(2011,  1,  2)
        timeSunday.floor(zoneUTC, dateUTC(2011,  1,  2,  0,  0,  1)) shouldBe dateUTC(2011,  1,  2)
    }

    // TODO not working
//    @Test
//    fun timeSunday_floor_handles_years_first_century() {
//        timeSunday.floor(zoneLocal, dateLocal(11, 11, 6, 7, 0)) shouldBe dateLocal(11, 11, 1)
//    }

    @Test
    fun timeSunday_ceil_returns_Sundays() {
        with(timeSunday) {
            zoneUTC.ceil(dateUTC(2010, 12, 31, 23, 59, 59)) shouldBe dateUTC(2011, 1, 2)
            zoneUTC.ceil(dateUTC(2011, 1, 1, 0, 0, 0)) shouldBe dateUTC(2011, 1, 2)
            zoneUTC.ceil(dateUTC(2011, 1, 1, 0, 0, 1)) shouldBe dateUTC(2011, 1, 2)
            zoneUTC.ceil(dateUTC(2011, 1, 1, 23, 59, 59)) shouldBe dateUTC(2011, 1, 2)
            zoneUTC.ceil(dateUTC(2011, 1, 2, 0, 0, 0)) shouldBe dateUTC(2011, 1, 2)
            zoneUTC.ceil(dateUTC(2011, 1, 2, 0, 0, 1)) shouldBe dateUTC(2011, 1, 9)
        }
    }

    @Test
    fun timeSunday_offset_allows_negative_step() {
        timeSunday.offset(zoneUTC, dateUTC(2010, 12, 1), -1) shouldBe dateUTC(2010, 11, 24)
        timeSunday.offset(zoneUTC, dateUTC(2011,  1,  1), -2) shouldBe dateUTC(2010, 12, 18)
        timeSunday.offset(zoneUTC, dateUTC(2011,  1,  1), -1) shouldBe dateUTC(2010, 12, 25)
    }

    @Test
    fun timeSunday_offset_allows_positive_step() {
        timeSunday.offset(zoneUTC, dateUTC(2010, 11, 24), 1) shouldBe dateUTC(2010, 12, 1)
        timeSunday.offset(zoneUTC, dateUTC(2010,  12,  18), 2) shouldBe dateUTC(2011, 1, 1)
        timeSunday.offset(zoneUTC, dateUTC(2010,  12,  25), 1) shouldBe dateUTC(2011, 1, 1)
    }

    @Test
    fun timeSunday_offset_allows_zero_step() {
        timeSunday.offset(zoneUTC, dateUTC(2010, 12, 31, 23, 59, 59, 999), 0) shouldBe dateUTC(2010, 12, 31, 23, 59, 59, 999)
        timeSunday.offset(zoneUTC, dateUTC(2010, 12, 31, 23, 59, 58,   0), 0) shouldBe dateUTC(2010, 12, 31, 23, 59, 58,   0)
    }

    @Test
    fun timeSunday_range_start_stop_returns_Sundays_between_start_inclusive_and_stop_exclusive() {

        with(timeSunday) { zoneUTC.range(dateUTC(2011, 12, 1), dateUTC(2012, 1, 15)) } shouldBe listOf(
            dateUTC(2011, 12,  4),
            dateUTC(2011, 12, 11),
            dateUTC(2011, 12, 18),
            dateUTC(2011, 12, 25),
            dateUTC(2012,  1,  1),
            dateUTC(2012,  1,  8)
        )
    }

    @Test
    fun timeSunday_range_start_stop_returns_Sundays() {
         with(timeSunday) { zoneUTC.range(dateUTC(2011, 12, 1, 12, 23), dateUTC(2012, 1, 14, 12, 23)) } shouldBe listOf(
            dateUTC(2011, 12,  4),
            dateUTC(2011, 12, 11),
            dateUTC(2011, 12, 18),
            dateUTC(2011, 12, 25),
            dateUTC(2012,  1,  1),
            dateUTC(2012,  1,  8)
        )
    }

    /**
     * https://github.com/data2viz/data2viz/issues/225
     */
    @Test
    fun issue_fix_specific_sunday_over_several_months() {
         with(timeSunday) { zoneUTC.range(dateUTC(2019,7,6,8,53,42,715), dateUTC(2019,9,25,22,0,48,33)) }.size shouldBe 12
    }

    @Test
    fun timeSunday_floor_observes_DST() {
        with(timeSunday) { zoneLocal.floor(dateLocal(2011, 3, 13, 1)) } shouldBe dateLocal(2011, 3, 13)
        with(timeSunday) { zoneLocal.floor(dateLocal(2011, 11, 6, 1)) } shouldBe dateLocal(2011, 11, 6)
    }

    @Test
    fun timeSunday_ceil_observes_DST() {
        with(timeSunday) { zoneLocal.ceil(dateLocal(2011, 3, 13, 1)) } shouldBe dateLocal(2011, 3, 20)

        // TODO fix test
//        with(timeSunday) { zoneLocal.ceil(dateLocal(2011, 11, 6, 1)) } shouldBe dateLocal(2011, 11, 13)
    }

    @Test
    fun timeSunday_offset_does_not_round_the_date() {
        with(timeSunday) { zoneLocal.offset(dateLocal(2010, 12, 31, 23, 59, 59, 999), 1) } shouldBe dateLocal(2011, 1, 7, 23, 59, 59, 999)
        with(timeSunday) { zoneLocal.offset(dateLocal(2010, 12, 31, 23, 59, 59, 456), -2) } shouldBe dateLocal(2010, 12, 17, 23, 59, 59, 456)
    }

    @Test
    fun timeSunday_range_returns_empty_array_if_start_after_stop() {
        with(timeSunday) { zoneLocal.range(dateLocal(2011, 12, 10), dateLocal(2011, 11, 4)) } shouldBe listOf()
        with(timeSunday) { zoneLocal.range(dateLocal(2011, 11, 1), dateLocal(2011, 11, 1)) } shouldBe listOf()
    }

    @Test
    fun timeSunday_range_returns_every_step_sunday() {
        with(timeSunday) { zoneLocal.range(dateLocal(2011, 12, 1), dateLocal(2012, 1, 15), 2) } shouldBe listOf(
            dateLocal(2011, 12, 4),
            dateLocal(2011, 12, 18),
            dateLocal(2012, 1, 1)
        )
    }

    @Test
    fun timeSunday_count_returns_every_sunday_adter_start_excluse_and_before_end_inclusive() {

        //     January 2014
        // Su Mo Tu We Th Fr Sa
        //           1  2  3  4
        //  5  6  7  8  9 10 11
        // 12 13 14 15 16 17 18
        // 19 20 21 22 23 24 25
        // 26 27 28 29 30 31
        with(timeSunday) { zoneLocal.count(dateLocal(2014, 1, 1), dateLocal(2014, 1, 4)) } shouldBe 0
        with(timeSunday) { zoneLocal.count(dateLocal(2014, 1, 1), dateLocal(2014, 1, 5)) } shouldBe 1
        with(timeSunday) { zoneLocal.count(dateLocal(2014, 1, 1), dateLocal(2014, 1, 6)) } shouldBe 1
        with(timeSunday) { zoneLocal.count(dateLocal(2014, 1, 1), dateLocal(2014, 1, 12)) } shouldBe 2

        //       January 2012
        // Su Mo Tu We Th Fr Sa
        //  1  2  3  4  5  6  7
        //  8  9 10 11 12 13 14
        // 15 16 17 18 19 20 21
        // 22 23 24 25 26 27 28
        // 29 30 31
        with(timeSunday) { zoneLocal.count(dateLocal(2012, 1, 1), dateLocal(2012, 1, 7)) } shouldBe 0
        with(timeSunday) { zoneLocal.count(dateLocal(2012, 1, 1), dateLocal(2012, 1, 8)) } shouldBe 1
        with(timeSunday) { zoneLocal.count(dateLocal(2012, 1, 1), dateLocal(2012, 1, 9)) } shouldBe 1
    }

    @Test
    fun timeSunday_count_observes_DST() {
        with(timeSunday) { zoneLocal.count(dateLocal(2011, 1, 1), dateLocal(2011, 3, 13, 1)) } shouldBe 11
        with(timeSunday) { zoneLocal.count(dateLocal(2011, 1, 1), dateLocal(2011, 3, 13, 3)) } shouldBe 11
        with(timeSunday) { zoneLocal.count(dateLocal(2011, 1, 1), dateLocal(2011, 3, 13, 4)) } shouldBe 11

        // TODO fix test
//        with(timeSunday) { zoneLocal.count(dateLocal(2011, 1, 1), dateLocal(2011, 11, 6, 0)) } shouldBe 45
//        with(timeSunday) { zoneLocal.count(dateLocal(2011, 1, 1), dateLocal(2011, 11, 6, 1)) } shouldBe 45
//        with(timeSunday) { zoneLocal.count(dateLocal(2011, 1, 1), dateLocal(2011, 11, 6, 2)) } shouldBe 45
    }
}