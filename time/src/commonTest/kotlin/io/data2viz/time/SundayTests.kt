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

// TODO change date() to LocalDateTime when version > 8.3
@Suppress("DEPRECATION")
class SundayTests : TestDate() {

    @Test
    fun timeSunday_floor_returns_Sundays() {
        val time = timeSunday

        time.floor(date(2010, 12, 31, 23, 59, 59)) shouldBe date(2010, 12, 26)
        time.floor(date(2011,  1,  1,  0,  0,  0)) shouldBe date(2010, 12, 26)
        time.floor(date(2011,  1,  1,  0,  0,  1)) shouldBe date(2010, 12, 26)
        time.floor(date(2011,  1,  1, 23, 59, 59)) shouldBe date(2010, 12, 26)
        time.floor(date(2011,  1,  2,  0,  0,  0)) shouldBe date(2011,  1,  2)
        time.floor(date(2011,  1,  2,  0,  0,  1)) shouldBe date(2011,  1,  2)
    }

    // TODO not working
//    @Test
//    fun timeSunday_floor_handles_years_in_the_first_century() {
//        val time = timeSunday
//
//        val date = LocalDateTime(11, 11, 6, 7, 0)
//        date.dayOfWeek shouldBe DayOfWeek.WEDNESDAY
//
//        time.floor(date(11, 11, 6, 7)) shouldBe date(11, 11, 1)
//    }

    @Test
    fun timeSunday_ceil_returns_Sundays() {
        val time = timeSunday

        time.ceil(date(2010, 12, 31, 23, 59, 59)) shouldBe date(2011, 1, 2)
        time.ceil(date(2011,  1,  1,  0,  0,  0)) shouldBe date(2011, 1, 2)
        time.ceil(date(2011,  1,  1,  0,  0,  1)) shouldBe date(2011, 1, 2)
        time.ceil(date(2011,  1,  1, 23, 59, 59)) shouldBe date(2011, 1, 2)
        time.ceil(date(2011,  1,  2,  0,  0,  0)) shouldBe date(2011, 1, 2)
        time.ceil(date(2011,  1,  2,  0,  0,  1)) shouldBe date(2011, 1, 9)
    }

    @Test
    fun timeSunday_offset_allows_negative_step() {
        val time = timeSunday

        time.offset(date(2010, 12, 1), -1) shouldBe date(2010, 11, 24)
        time.offset(date(2011,  1,  1), -2) shouldBe date(2010, 12, 18)
        time.offset(date(2011,  1,  1), -1) shouldBe date(2010, 12, 25)
    }

    @Test
    fun timeSunday_offset_allows_positive_step() {
        val time = timeSunday

        time.offset(date(2010, 11, 24), 1) shouldBe date(2010, 12, 1)
        time.offset(date(2010,  12,  18), 2) shouldBe date(2011, 1, 1)
        time.offset(date(2010,  12,  25), 1) shouldBe date(2011, 1, 1)
    }

    @Test
    fun timeSunday_offset_allows_zero_step() {
        val time = timeSunday

        time.offset(date(2010, 12, 31, 23, 59, 59, 999), 0) shouldBe date(2010, 12, 31, 23, 59, 59, 999)
        time.offset(date(2010, 12, 31, 23, 59, 58,   0), 0) shouldBe date(2010, 12, 31, 23, 59, 58,   0)
    }

    @Test
    fun timeSunday_range_start_stop_returns_Sundays_between_start_inclusive_and_stop_exclusive() {
        val time = timeSunday

        time.range(date(2011, 12, 1), date(2012, 1, 15)) shouldBe listOf(
            date(2011, 12,  4),
            date(2011, 12, 11),
            date(2011, 12, 18),
            date(2011, 12, 25),
            date(2012,  1,  1),
            date(2012,  1,  8)
        )
    }

    @Test
    fun timeSunday_range_start_stop_returns_Sundays() {
        val time = timeSunday

        time.range(date(2011, 12, 1, 12, 23), date(2012, 1, 14, 12, 23)) shouldBe listOf(
            date(2011, 12,  4),
            date(2011, 12, 11),
            date(2011, 12, 18),
            date(2011, 12, 25),
            date(2012,  1,  1),
            date(2012,  1,  8)
        )
    }

    @Test
    fun issue_fix_specific_sunday_over_several_months() {
        val time = timeSunday
        time.range(date(2019,7,6,8,53,42,715), date(2019,9,25,22,0,48,33)).size shouldBe 12
    }

    /**

    tape("timeSunday.floor(date) handles years in the first century", function(test) {
    test.deepEqual(time.timeSunday.floor(date.local(0011, 10, 06, 07)), date.local(0011, 10, 01));
    test.end();
    });

    tape("timeSunday.floor(date) observes daylight saving", function(test) {
    test.deepEqual(time.timeSunday.floor(date.local(2011, 02, 13, 01)), date.local(2011, 02, 13));
    test.deepEqual(time.timeSunday.floor(date.local(2011, 10, 06, 01)), date.local(2011, 10, 06));
    test.end();
    });

    tape("timeSunday.ceil(date) observes daylight saving", function(test) {
    test.deepEqual(time.timeSunday.ceil(date.local(2011, 02, 13, 01)), date.local(2011, 02, 20));
    test.deepEqual(time.timeSunday.ceil(date.local(2011, 10, 06, 01)), date.local(2011, 10, 13));
    test.end();
    });

    tape("timeSunday.offset(date) is an alias for timeSunday.offset(date, 1)", function(test) {
    test.deepEqual(time.timeSunday.offset(date.local(2010, 11, 31, 23, 59, 59, 999)), date.local(2011, 00, 07, 23, 59, 59, 999));
    test.end();
    });

    tape("timeSunday.offset(date, step) does not modify the passed-in date", function(test) {
    var d = date.local(2010, 11, 31, 23, 59, 59, 999);
    time.timeSunday.offset(d, +1);
    test.deepEqual(d, date.local(2010, 11, 31, 23, 59, 59, 999));
    test.end();
    });

    tape("timeSunday.offset(date, step) does not round the passed-in date", function(test) {
    test.deepEqual(time.timeSunday.offset(date.local(2010, 11, 31, 23, 59, 59, 999), +1), date.local(2011, 00, 07, 23, 59, 59, 999));
    test.deepEqual(time.timeSunday.offset(date.local(2010, 11, 31, 23, 59, 59, 456), -2), date.local(2010, 11, 17, 23, 59, 59, 456));
    test.end();
    });

    tape("timeSunday.range(start, stop) returns the empty array for invalid dates", function(test) {
    test.deepEqual(time.timeSunday.range(new Date(NaN), Infinity), []);
    test.end();
    });

    tape("timeSunday.range(start, stop) returns the empty array if start >= stop", function(test) {
    test.deepEqual(time.timeSunday.range(date.local(2011, 11, 10), date.local(2011, 10, 04)), []);
    test.deepEqual(time.timeSunday.range(date.local(2011, 10, 01), date.local(2011, 10, 01)), []);
    test.end();
    });

    tape("timeSunday.range(start, stop, step) returns every step Sunday", function(test) {
    test.deepEqual(time.timeSunday.range(date.local(2011, 11, 01), date.local(2012, 00, 15), 2), [
    date.local(2011, 11, 04),
    date.local(2011, 11, 18),
    date.local(2012, 00, 01)
    ]);
    test.end();
    });

    tape("timeSunday.count(start, end) counts Sundays after start (exclusive) and before end (inclusive)", function(test) {
    //     January 2014
    // Su Mo Tu We Th Fr Sa
    //           1  2  3  4
    //  5  6  7  8  9 10 11
    // 12 13 14 15 16 17 18
    // 19 20 21 22 23 24 25
    // 26 27 28 29 30 31
    test.equal(time.timeSunday.count(date.local(2014, 00, 01), date.local(2014, 00, 04)), 0);
    test.equal(time.timeSunday.count(date.local(2014, 00, 01), date.local(2014, 00, 05)), 1);
    test.equal(time.timeSunday.count(date.local(2014, 00, 01), date.local(2014, 00, 06)), 1);
    test.equal(time.timeSunday.count(date.local(2014, 00, 01), date.local(2014, 00, 12)), 2);

    //       January 2012
    // Su Mo Tu We Th Fr Sa
    //  1  2  3  4  5  6  7
    //  8  9 10 11 12 13 14
    // 15 16 17 18 19 20 21
    // 22 23 24 25 26 27 28
    // 29 30 31
    test.equal(time.timeSunday.count(date.local(2012, 00, 01), date.local(2012, 00, 07)), 0);
    test.equal(time.timeSunday.count(date.local(2012, 00, 01), date.local(2012, 00, 08)), 1);
    test.equal(time.timeSunday.count(date.local(2012, 00, 01), date.local(2012, 00, 09)), 1);
    test.end();
    });

    tape("timeSunday.count(start, end) observes daylight saving", function(test) {
    test.equal(time.timeSunday.count(date.local(2011, 00, 01), date.local(2011, 02, 13, 01)), 11);
    test.equal(time.timeSunday.count(date.local(2011, 00, 01), date.local(2011, 02, 13, 03)), 11);
    test.equal(time.timeSunday.count(date.local(2011, 00, 01), date.local(2011, 02, 13, 04)), 11);
    test.equal(time.timeSunday.count(date.local(2011, 00, 01), date.local(2011, 10, 06, 00)), 45);
    test.equal(time.timeSunday.count(date.local(2011, 00, 01), date.local(2011, 10, 06, 01)), 45);
    test.equal(time.timeSunday.count(date.local(2011, 00, 01), date.local(2011, 10, 06, 02)), 45);
    test.end();
    });
     */

}