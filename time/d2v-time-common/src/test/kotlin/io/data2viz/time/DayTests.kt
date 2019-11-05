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

import kotlin.test.Test

class DayTests : TestDate() {


    @Test
    fun day_floor_date_returns_days() {
        val time = timeDay

        time.floor(date(2010, 12, 31, 23)) shouldBe date(2010, 12, 31)
        time.floor(date(2011, 1, 1, 0)) shouldBe date(2011, 1, 1)
        time.floor(date(2011, 1, 1, 1)) shouldBe date(2011, 1, 1)
    }

    @Test
    fun day_handles_years_in_first_century() {
        val time = timeDay

        time.floor(date(11, 10, 6, 7)) shouldBe date(11, 10, 6)
    }

    @Test
    fun day_round_date_returns_days() {
        val time = timeDay

        time.round(date(2010, 12, 30, 13)) shouldBe date(2010, 12, 31)
        time.round(date(2010, 12, 30, 11)) shouldBe date(2010, 12, 30)
    }

    @Test
    fun day_round_date_handles_midnight_in_leap_years_days() {
        val time = timeDay

        time.round(date(2012, 3, 1, 0)) shouldBe date(2012, 3, 1)
        time.round(date(2012, 3, 1, 0)) shouldBe date(2012, 3, 1)
    }

    @Test
    fun day_ceil_returns_day() {
        val time = timeDay

        time.ceil(date(2010, 12, 30, 23)) shouldBe date(2010, 12, 31)
        time.ceil(date(2010, 12, 31, 0)) shouldBe date(2010, 12, 31)
        time.ceil(date(2010, 12, 31, 1)) shouldBe date(2011, 1, 1)
    }

    @Test
    fun day_offset_null_is_an_alias_for_offset_1() {
        val time = timeDay

        val date1 = time.offset(date(2010, 12, 31, 23, 59, 59, 999))
        val date2 = date(2011, 1, 1, 23, 59, 59, 999)
        date1 shouldBe date2
    }

    @Test
    fun day_offset_date_step_does_not_modify_the_passed_date() {
        val time = timeDay
        val date = date(2010, 12, 31, 23, 59, 59, 999)

        time.offset(date, 1)
        date shouldBe date(2010, 12, 31, 23, 59, 59, 999)
    }

    @Test
    fun day_offset_date_step_does_not_round_the_passed_date() {
        val time = timeDay
        val date1 = date(2010, 12, 31, 23, 59, 59, 456)

        val date2 = date(2011, 1, 1, 23, 59, 59, 456)
        time.offset(date1, 1) shouldBe date2

        val date3 = date(2010, 12, 29, 23, 59, 59, 456)
        time.offset(date1, -2) shouldBe date3
    }

    @Test
    fun day_offset_allows_negative_positive_zero_step() {
        val time = timeDay

        time.offset(date(2010, 12, 31), -1) shouldBe date(2010, 12, 30)
        time.offset(date(2011, 1, 1), -2) shouldBe date(2010, 12, 30)
        time.offset(date(2011, 1, 1), -1) shouldBe date(2010, 12, 31)

        time.offset(date(2010, 12, 30), 1) shouldBe date(2010, 12, 31)
        time.offset(date(2010, 12, 30), 2) shouldBe date(2011, 1, 1)
        time.offset(date(2010, 12, 31), 1) shouldBe date(2011, 1, 1)

        val date1 = date(2010, 12, 31, 23, 59, 59, 999)
        time.offset(date1, 0) shouldBe date1
        val date2 = date(2010, 12, 31, 23, 59, 58, 0)
        time.offset(date2, 0) shouldBe date2
    }

    @Test
    fun day_range_start_stop_returns_days_between_start_inclusive_and_stop_exclusive() {
        val time = timeDay
        val result = listOf(
                date(2011, 11, 4),
                date(2011, 11, 5),
                date(2011, 11, 6),
                date(2011, 11, 7),
                date(2011, 11, 8),
                date(2011, 11, 9)
        )

        val range = time.range(date(2011, 11, 4), date(2011, 11, 10))
        range.forEachIndexed { index, r ->
            r shouldBe result[index]
        }
    }

    @Test
    fun day_range_start_stop_returns_days() {
        val time = timeDay
        val result = listOf(
                date(2011, 11, 5),
                date(2011, 11, 6),
                date(2011, 11, 7),
                date(2011, 11, 8),
                date(2011, 11, 9),
                date(2011, 11, 10)
        )

        val range = time.range(date(2011, 11, 4, 2), date(2011, 11, 10, 13))
        range.forEachIndexed { index, r ->
            r shouldBe result[index]
        }
    }

    @Test
    fun day_range_return_empty_array_if_start_later_or_equal_than_stop() {
        val time = timeDay

        time.range(date(2011, 11, 10), date(2011, 11, 4)) shouldBe listOf<Date>()
        time.range(date(2011, 11, 10), date(2011, 11, 10)) shouldBe listOf<Date>()
    }

    @Test
    fun day_range_start_stop_step_returns_every_step_days() {
        val time = timeDay
        val result = listOf(
                date(2011, 11, 5),
                date(2011, 11, 8),
                date(2011, 11, 11),
                date(2011, 11, 14)
        )

        val range = time.range(date(2011, 11, 4, 2), date(2011, 11, 14, 13), 3)
        range.forEachIndexed { index, r ->
            r shouldBe result[index]
        }
    }

    @Test
    fun day_range_return_empty_array_if_step_is_zero_or_negative() {
        val time = timeDay

        time.range(date(2011, 1, 1, 0), date(2011, 5, 9, 0), 0) shouldBe listOf<Date>()
        time.range(date(2011, 1, 1, 0), date(2011, 5, 9, 0), -1) shouldBe listOf<Date>()
    }

    @Test
    fun day_count_start_end_counts_days_after_start_exclusive_and_before_end_inclusive() {
        val time = timeDay

        time.count(date(2011, 1, 1, 0), date(2011, 5, 9, 0)) shouldBe 128
        time.count(date(2011, 1, 1, 1), date(2011, 5, 9, 0)) shouldBe 128
        time.count(date(2010, 12, 31, 23), date(2011, 5, 9, 0)) shouldBe 129
        time.count(date(2011, 1, 1, 0), date(2011, 5, 8, 23)) shouldBe 127
        time.count(date(2011, 1, 1, 0), date(2011, 5, 9, 1)) shouldBe 128
    }

    @Test
    fun day_count_start_end_observes_daylight_saving() {
        val time = timeDay

        time.count(date(2011, 1, 1), date(2011, 3, 13, 1)) shouldBe 71
        time.count(date(2011, 1, 1), date(2011, 3, 13, 3)) shouldBe 71
        time.count(date(2011, 1, 1), date(2011, 3, 13, 4)) shouldBe 71
        time.count(date(2011, 1, 1), date(2011, 11, 6, 0)) shouldBe 309
        time.count(date(2011, 1, 1), date(2011, 11, 6, 1)) shouldBe 309
        time.count(date(2011, 1, 1), date(2011, 11, 6, 2)) shouldBe 309
    }

    // TODO : finish
    @Test
    fun day_count_start_end_does_not_exhibit_floating_point_error() {
//        val timeDay = timeDay
//        val timeYear = timeYear()
//        val date = date(2011, 5, 9)

//        timeDay.count(timeYear.floor(date), date) shouldBe 128
    }

    @Test
    fun day_count_start_end_returns_364_or_365_for_a_full_year() {
        val time = timeDay

        time.count(date(1999, 1, 1), date(1999, 12, 31)) shouldBe 364
        time.count(date(2000, 1, 1), date(2000, 12, 31)) shouldBe 365 // leap year
        time.count(date(2001, 1, 1), date(2001, 12, 31)) shouldBe 364
        time.count(date(2002, 1, 1), date(2002, 12, 31)) shouldBe 364
        time.count(date(2003, 1, 1), date(2003, 12, 31)) shouldBe 364
        time.count(date(2004, 1, 1), date(2004, 12, 31)) shouldBe 365 // leap year
        time.count(date(2005, 1, 1), date(2005, 12, 31)) shouldBe 364
        time.count(date(2006, 1, 1), date(2006, 12, 31)) shouldBe 364
        time.count(date(2007, 1, 1), date(2007, 12, 31)) shouldBe 364
        time.count(date(2008, 1, 1), date(2008, 12, 31)) shouldBe 365 // leap year
        time.count(date(2009, 1, 1), date(2009, 12, 31)) shouldBe 364
        time.count(date(2010, 1, 1), date(2010, 12, 31)) shouldBe 364
        time.count(date(2011, 1, 1), date(2011, 12, 31)) shouldBe 364
    }

    @Test
    fun day_every_step_returns_every_stepth_day_starting_with_the_first_day_of_the_month() {
        val time = timeDay

        var result = listOf(
                date(2008, 12, 31),
                date(2009, 1, 1),
                date(2009, 1, 4)
        )
        var range = time.every(3).range(date(2008, 12, 30, 0, 12), date(2009, 1, 5, 23, 48))
        range.forEachIndexed { index, r ->
            r shouldBe result[index]
        }

        result = listOf(
                date(2008, 12, 31),
                date(2009, 1, 1),
                date(2009, 1, 6)
        )
        range = time.every(5).range(date(2008, 12, 30, 0, 12), date(2009, 1, 6, 23, 48))
        range.forEachIndexed { index, r ->
            r shouldBe result[index]
        }

        result = listOf(
                date(2009, 1, 1),
                date(2009, 1, 8)
        )
        range = time.every(7).range(date(2008, 12, 30, 0, 12), date(2009, 1, 8, 23, 48))
        range.forEachIndexed { index, r ->
            r shouldBe result[index]
        }
    }

    /*@Test
    fun day_floor_date_observes_daylight_saving() {
        val time = timeDay()

//        time.floor(date(2011, 3, 13, 7)) shouldBe date(2011, 3, 12)
    }*/

        /**

    tape("timeDay.floor(date) observes daylight saving", function(test) {
    test.deepEqual(time.timeDay.floor(date.utc(2011, 02, 13, 07)), date.local(2011, 02, 12));
    test.deepEqual(time.timeDay.floor(date.utc(2011, 02, 13, 08)), date.local(2011, 02, 13));
    test.deepEqual(time.timeDay.floor(date.utc(2011, 02, 13, 09)), date.local(2011, 02, 13));
    test.deepEqual(time.timeDay.floor(date.utc(2011, 02, 13, 10)), date.local(2011, 02, 13));
    test.deepEqual(time.timeDay.floor(date.utc(2011, 10, 06, 07)), date.local(2011, 10, 06));
    test.deepEqual(time.timeDay.floor(date.utc(2011, 10, 06, 08)), date.local(2011, 10, 06));
    test.deepEqual(time.timeDay.floor(date.utc(2011, 10, 06, 09)), date.local(2011, 10, 06));
    test.deepEqual(time.timeDay.floor(date.utc(2011, 10, 06, 10)), date.local(2011, 10, 06));
    test.end();
    });

    tape("timeDay.round(date) observes daylight saving", function(test) {
    test.deepEqual(time.timeDay.round(date.utc(2011, 02, 13, 07)), date.local(2011, 02, 13));
    test.deepEqual(time.timeDay.round(date.utc(2011, 02, 13, 08)), date.local(2011, 02, 13));
    test.deepEqual(time.timeDay.round(date.utc(2011, 02, 13, 09)), date.local(2011, 02, 13));
    test.deepEqual(time.timeDay.round(date.utc(2011, 02, 13, 20)), date.local(2011, 02, 14));
    test.deepEqual(time.timeDay.round(date.utc(2011, 10, 06, 07)), date.local(2011, 10, 06));
    test.deepEqual(time.timeDay.round(date.utc(2011, 10, 06, 08)), date.local(2011, 10, 06));
    test.deepEqual(time.timeDay.round(date.utc(2011, 10, 06, 09)), date.local(2011, 10, 06));
    test.deepEqual(time.timeDay.round(date.utc(2011, 10, 06, 20)), date.local(2011, 10, 07));
    test.end();
    });

    tape("timeDay.ceil(date) observes start of daylight saving", function(test) {
    test.deepEqual(time.timeDay.ceil(date.utc(2011, 02, 13, 07)), date.local(2011, 02, 13));
    test.deepEqual(time.timeDay.ceil(date.utc(2011, 02, 13, 08)), date.local(2011, 02, 13));
    test.deepEqual(time.timeDay.ceil(date.utc(2011, 02, 13, 09)), date.local(2011, 02, 14));
    test.deepEqual(time.timeDay.ceil(date.utc(2011, 02, 13, 10)), date.local(2011, 02, 14));
    test.end();
    });



    tape("timeDay.ceil(date) observes end of daylight saving", function(test) {
    test.deepEqual(time.timeDay.ceil(date.utc(2011, 10, 06, 07)), date.local(2011, 10, 06));
    test.deepEqual(time.timeDay.ceil(date.utc(2011, 10, 06, 08)), date.local(2011, 10, 07));
    test.deepEqual(time.timeDay.ceil(date.utc(2011, 10, 06, 09)), date.local(2011, 10, 07));
    test.deepEqual(time.timeDay.ceil(date.utc(2011, 10, 06, 10)), date.local(2011, 10, 07));
    test.end();
    });

    tape("timeDay.ceil(date) handles midnight for leap years", function(test) {
    test.deepEqual(time.timeDay.ceil(date.utc(2012, 02, 01, 00)), date.local(2012, 02, 01));
    test.deepEqual(time.timeDay.ceil(date.utc(2012, 02, 01, 00)), date.local(2012, 02, 01));
    test.end();
    });
     */

}