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
import kotlin.test.Test
import kotlin.time.ExperimentalTime

class MonthTests : TestDate() {


    @Test
    fun month_floor_date_returns_months() {
        val time = timeMonth

        time.floor(LocalDateTime(2010, 12, 31, 23, 59, 59)) shouldBe LocalDateTime(2010, 12, 1, 0, 0)
        time.floor(LocalDateTime(2011, 1, 1, 0, 0, 0)) shouldBe LocalDateTime(2011, 1, 1, 0, 0)
        time.floor(LocalDateTime(2011, 1, 1, 0, 0, 1)) shouldBe LocalDateTime(2011, 1, 1, 0, 0)
    }

    @Test
    fun month_floor_observes_start_of_DST() {
        val time = timeMonth

        time.floor(LocalDateTime(2011, 3, 13, 1, 0)) shouldBe LocalDateTime(2011, 3, 1, 0, 0)
    }

    @Test
    fun month_floor_observes_end_of_DST() {
        val time = timeMonth

        time.floor(LocalDateTime(2011, 11, 6, 1, 0)) shouldBe LocalDateTime(2011, 11, 1, 0, 0)
    }

    @Test
    fun month_floor_handles_years_in_first_century() {
        val time = timeMonth

        time.floor(LocalDateTime(11, 11, 6, 7, 0)) shouldBe LocalDateTime(11, 11, 1, 0, 0)
    }

    @Test
    fun month_ceil_returns_months() {
        val time = timeMonth

        time.ceil(LocalDateTime(2010, 12, 31, 23, 59, 59)) shouldBe LocalDateTime(2011, 1, 1, 0, 0)
        time.ceil(LocalDateTime(2011, 1, 1, 0, 0, 0)) shouldBe LocalDateTime(2011, 1, 1, 0, 0)
        time.ceil(LocalDateTime(2011, 1, 1, 0, 0, 1)) shouldBe LocalDateTime(2011, 2, 1, 0, 0)
    }

    @Test
    fun month_ceil_observes_start_of_DST() {
        val time = timeMonth

        time.ceil(LocalDateTime(2011, 3, 13, 1, 0)) shouldBe LocalDateTime(2011, 4, 1, 0, 0)
    }

    @Test
    fun month_ceil_observes_end_of_DST() {
        val time = timeMonth

        time.ceil(LocalDateTime(2011, 11, 6, 1, 0)) shouldBe LocalDateTime(2011, 12, 1, 0, 0)
    }

    @Test
    fun month_offset_does_not_modify_passed_in_date() {
        val time = timeMonth
        val date = LocalDateTime(2010, 12, 31, 23, 59, 59, 999)

        time.offset(date, 1) shouldBe LocalDateTime(2011, 1, 31, 23, 59, 59, 999)
        date shouldBe LocalDateTime(2010, 12, 31, 23, 59, 59, 999)
    }

    @Test
    fun month_offset_does_not_round_passed_in_date() {
        val time = timeMonth

        time.offset(LocalDateTime(2010, 12, 31, 23, 59, 59, 999), 1) shouldBe LocalDateTime(2011, 1, 31, 23, 59, 59, 999)
        time.offset(LocalDateTime(2010, 12, 31, 23, 59, 59, 456), -2) shouldBe LocalDateTime(2010, 10, 31, 23, 59, 59, 456)
    }

    @Test
    fun month_offset_allows_negative_offsets() {
        val time = timeMonth

        time.offset(LocalDateTime(2010, 12, 1, 0, 0), -1) shouldBe LocalDateTime(2010, 11, 1, 0, 0)
        time.offset(LocalDateTime(2011, 1, 1, 0, 0), -2) shouldBe LocalDateTime(2010, 11, 1, 0, 0)
        time.offset(LocalDateTime(2011, 1, 1, 0, 0), -1) shouldBe LocalDateTime(2010, 12, 1, 0, 0)
    }

    @Test
    fun month_offset_allows_positive_offsets() {
        val time = timeMonth

        time.offset(LocalDateTime(2010, 11, 1, 0, 0), 1) shouldBe LocalDateTime(2010, 12, 1, 0, 0)
        time.offset(LocalDateTime(2010, 11, 1, 0, 0), 2) shouldBe LocalDateTime(2011, 1, 1, 0, 0)
        time.offset(LocalDateTime(2010, 12, 1, 0, 0), 1) shouldBe LocalDateTime(2011, 1, 1, 0, 0)
    }

    @Test
    fun month_offset_allows_zero_offsets() {
        val time = timeMonth

        time.offset(LocalDateTime(2010, 12, 31, 23, 59, 59, 999), 0) shouldBe LocalDateTime(2010, 12, 31, 23, 59, 59, 999)
    }

    /*
tape("timeMonth.floor(date) returns months", function(test) {
  test.deepEqual(time.timeMonth.floor(date.local(2010, 11, 31, 23)), date.local(2010, 11, 01));
  test.deepEqual(time.timeMonth.floor(date.local(2011, 00, 01, 00)), date.local(2011, 00, 01));
  test.deepEqual(time.timeMonth.floor(date.local(2011, 00, 01, 01)), date.local(2011, 00, 01));
  test.end();
});

tape("timeMonth.floor(date) observes daylight saving", function(test) {
  test.deepEqual(time.timeMonth.floor(date.utc(2011, 02, 13, 07)), date.local(2011, 02, 01));
  test.deepEqual(time.timeMonth.floor(date.utc(2011, 02, 13, 08)), date.local(2011, 02, 01));
  test.deepEqual(time.timeMonth.floor(date.utc(2011, 02, 13, 09)), date.local(2011, 02, 01));
  test.deepEqual(time.timeMonth.floor(date.utc(2011, 02, 13, 10)), date.local(2011, 02, 01));
  test.deepEqual(time.timeMonth.floor(date.utc(2011, 10, 06, 07)), date.local(2011, 10, 01));
  test.deepEqual(time.timeMonth.floor(date.utc(2011, 10, 06, 08)), date.local(2011, 10, 01));
  test.deepEqual(time.timeMonth.floor(date.utc(2011, 10, 06, 09)), date.local(2011, 10, 01));
  test.deepEqual(time.timeMonth.floor(date.utc(2011, 10, 06, 10)), date.local(2011, 10, 01));
  test.end();
});

tape("timeMonth.floor(date) handles years in the first century", function(test) {
  test.deepEqual(time.timeMonth.floor(date.local(0011, 10, 06, 07)), date.local(0011, 10, 01));
  test.end();
});

tape("timeMonth.round(date) returns months", function(test) {
  test.deepEqual(time.timeMonth.round(date.local(2010, 11, 16, 12)), date.local(2011, 00, 01));
  test.deepEqual(time.timeMonth.round(date.local(2010, 11, 16, 11)), date.local(2010, 11, 01));
  test.end();
});

tape("timeMonth.round(date) observes daylight saving", function(test) {
  test.deepEqual(time.timeMonth.round(date.utc(2011, 02, 13, 07)), date.local(2011, 02, 01));
  test.deepEqual(time.timeMonth.round(date.utc(2011, 02, 13, 08)), date.local(2011, 02, 01));
  test.deepEqual(time.timeMonth.round(date.utc(2011, 02, 13, 09)), date.local(2011, 02, 01));
  test.deepEqual(time.timeMonth.round(date.utc(2011, 02, 13, 20)), date.local(2011, 02, 01));
  test.deepEqual(time.timeMonth.round(date.utc(2011, 10, 06, 07)), date.local(2011, 10, 01));
  test.deepEqual(time.timeMonth.round(date.utc(2011, 10, 06, 08)), date.local(2011, 10, 01));
  test.deepEqual(time.timeMonth.round(date.utc(2011, 10, 06, 09)), date.local(2011, 10, 01));
  test.deepEqual(time.timeMonth.round(date.utc(2011, 10, 06, 20)), date.local(2011, 10, 01));
  test.end();
});

tape("timeMonth.round(date) handles midnight for leap years", function(test) {
  test.deepEqual(time.timeMonth.round(date.utc(2012, 02, 01, 00)), date.local(2012, 02, 01));
  test.deepEqual(time.timeMonth.round(date.utc(2012, 02, 01, 00)), date.local(2012, 02, 01));
  test.end();
});

tape("timeMonth.ceil(date) returns months", function(test) {
  test.deepEqual(time.timeMonth.ceil(date.local(2010, 10, 30, 23)), date.local(2010, 11, 01));
  test.deepEqual(time.timeMonth.ceil(date.local(2010, 11, 01, 01)), date.local(2011, 00, 01));
  test.deepEqual(time.timeMonth.ceil(date.local(2011, 1, 1)), date.local(2011, 1, 1));
  test.deepEqual(time.timeMonth.ceil(date.local(2011, 2, 1)), date.local(2011, 2, 1));
  test.deepEqual(time.timeMonth.ceil(date.local(2011, 3, 1)), date.local(2011, 3, 1));
  test.end();
});

tape("timeMonth.ceil(date) observes daylight saving", function(test) {
  test.deepEqual(time.timeMonth.ceil(date.utc(2011, 02, 13, 07)), date.local(2011, 03, 01));
  test.deepEqual(time.timeMonth.ceil(date.utc(2011, 02, 13, 08)), date.local(2011, 03, 01));
  test.deepEqual(time.timeMonth.ceil(date.utc(2011, 02, 13, 09)), date.local(2011, 03, 01));
  test.deepEqual(time.timeMonth.ceil(date.utc(2011, 02, 13, 10)), date.local(2011, 03, 01));
  test.deepEqual(time.timeMonth.ceil(date.utc(2011, 10, 06, 07)), date.local(2011, 11, 01));
  test.deepEqual(time.timeMonth.ceil(date.utc(2011, 10, 06, 08)), date.local(2011, 11, 01));
  test.deepEqual(time.timeMonth.ceil(date.utc(2011, 10, 06, 09)), date.local(2011, 11, 01));
  test.deepEqual(time.timeMonth.ceil(date.utc(2011, 10, 06, 10)), date.local(2011, 11, 01));
  test.end();
});

tape("timeMonth.ceil(date) handles midnight for leap years", function(test) {
  test.deepEqual(time.timeMonth.ceil(date.utc(2012, 02, 01, 00)), date.local(2012, 02, 01));
  test.deepEqual(time.timeMonth.ceil(date.utc(2012, 02, 01, 00)), date.local(2012, 02, 01));
  test.end();
});

tape("timeMonth.offset(date) is an alias for timeMonth.offset(date, 1)", function(test) {
  test.deepEqual(time.timeMonth.offset(date.local(2010, 11, 31, 23, 59, 59, 999)), date.local(2011, 00, 31, 23, 59, 59, 999));
  test.end();
});

tape("timeMonth.offset(date, step) does not modify the passed-in date", function(test) {
  var d = date.local(2010, 11, 31, 23, 59, 59, 999);
  time.timeMonth.offset(date, +1);
  test.deepEqual(d, date.local(2010, 11, 31, 23, 59, 59, 999));
  test.end();
});

tape("timeMonth.offset(date, step) does not round the passed-in date", function(test) {
  test.deepEqual(time.timeMonth.offset(date.local(2010, 11, 31, 23, 59, 59, 999), +1), date.local(2011, 00, 31, 23, 59, 59, 999));
  test.deepEqual(time.timeMonth.offset(date.local(2010, 11, 31, 23, 59, 59, 456), -2), date.local(2010, 09, 31, 23, 59, 59, 456));
  test.end();
});

tape("timeMonth.offset(date, step) allows step to be negative", function(test) {
  test.deepEqual(time.timeMonth.offset(date.local(2010, 11, 31), -1), date.local(2010, 10, 31));
  test.deepEqual(time.timeMonth.offset(date.local(2011, 00, 01), -2), date.local(2010, 10, 01));
  test.deepEqual(time.timeMonth.offset(date.local(2011, 00, 01), -1), date.local(2010, 11, 01));
  test.end();
});

tape("timeMonth.offset(date, step) allows step to be positive", function(test) {
  test.deepEqual(time.timeMonth.offset(date.local(2010, 11, 31), +1), date.local(2011, 00, 31));
  test.deepEqual(time.timeMonth.offset(date.local(2010, 11, 30), +2), date.local(2011, 01, 30));
  test.deepEqual(time.timeMonth.offset(date.local(2010, 11, 30), +1), date.local(2011, 00, 30));
  test.end();
});

tape("timeMonth.offset(date, step) allows step to be zero", function(test) {
  test.deepEqual(time.timeMonth.offset(date.local(2010, 11, 31, 23, 59, 59, 999), 0), date.local(2010, 11, 31, 23, 59, 59, 999));
  test.deepEqual(time.timeMonth.offset(date.local(2010, 11, 31, 23, 59, 58, 000), 0), date.local(2010, 11, 31, 23, 59, 58, 000));
  test.end();
});

tape("timeMonth.range(start, stop) returns months between start (inclusive) and stop (exclusive)", function(test) {
  test.deepEqual(time.timeMonth.range(date.local(2011, 11, 01), date.local(2012, 05, 01)), [
    date.local(2011, 11, 01),
    date.local(2012, 00, 01),
    date.local(2012, 01, 01),
    date.local(2012, 02, 01),
    date.local(2012, 03, 01),
    date.local(2012, 04, 01)
  ]);
  test.end();
});

tape("timeMonth.range(start, stop) returns months", function(test) {
  test.deepEqual(time.timeMonth.range(date.local(2011, 10, 04, 02), date.local(2012, 04, 10, 13)), [
    date.local(2011, 11, 01),
    date.local(2012, 00, 01),
    date.local(2012, 01, 01),
    date.local(2012, 02, 01),
    date.local(2012, 03, 01),
    date.local(2012, 04, 01)
  ]);
  test.end();
});

tape("timeMonth.range(start, stop) coerces start and stop to dates", function(test) {
  test.deepEqual(time.timeMonth.range(+date.local(2011, 10, 04), +date.local(2012, 01, 07)), [
    date.local(2011, 11, 01),
    date.local(2012, 00, 01),
    date.local(2012, 01, 01)
  ]);
  test.end();
});

tape("timeMonth.range(start, stop) returns the empty array for invalid dates", function(test) {
  test.deepEqual(time.timeMonth.range(new Date(NaN), Infinity), []);
  test.end();
});

tape("timeMonth.range(start, stop) returns the empty array if start >= stop", function(test) {
  test.deepEqual(time.timeMonth.range(date.local(2011, 11, 10), date.local(2011, 10, 04)), []);
  test.deepEqual(time.timeMonth.range(date.local(2011, 10, 01), date.local(2011, 10, 01)), []);
  test.end();
});

tape("timeMonth.range(start, stop) returns months", function(test) {
  test.deepEqual(time.timeMonth.range(date.local(2010, 10, 31), date.local(2011, 2, 1)), [
    date.local(2010, 11, 1),
    date.local(2011, 0, 1),
    date.local(2011, 1, 1)
  ]);
  test.end();
});

tape("timeMonth.range(start, stop) has an inclusive lower bound", function(test) {
  test.deepEqual(time.timeMonth.range(date.local(2010, 10, 31), date.local(2011, 2, 1))[0], date.local(2010, 11, 1));
  test.end();
});

tape("timeMonth.range(start, stop) has an exclusive upper bound", function(test) {
  test.deepEqual(time.timeMonth.range(date.local(2010, 10, 31), date.local(2011, 2, 1))[2], date.local(2011, 1, 1));
  test.end();
});

tape("timeMonth.range(start, stop) can skip months", function(test) {
  test.deepEqual(time.timeMonth.range(date.local(2011, 1, 1), date.local(2012, 1, 1), 3), [
    date.local(2011, 1, 1),
    date.local(2011, 4, 1),
    date.local(2011, 7, 1),
    date.local(2011, 10, 1)
  ]);
  test.end();
});

tape("timeMonth.range(start, stop) observes start of daylight savings time", function(test) {
  test.deepEqual(time.timeMonth.range(date.local(2011, 0, 1), date.local(2011, 4, 1)), [
    date.local(2011, 0, 1),
    date.local(2011, 1, 1),
    date.local(2011, 2, 1),
    date.local(2011, 3, 1)
  ]);
  test.end();
});

tape("timeMonth.range(start, stop) observes end of daylight savings time", function(test) {
  test.deepEqual(time.timeMonth.range(date.local(2011, 9, 1), date.local(2012, 1, 1)), [
    date.local(2011, 9, 1),
    date.local(2011, 10, 1),
    date.local(2011, 11, 1),
    date.local(2012, 0, 1)
  ]);
  test.end();
});

tape("timeMonth.count(start, end) counts months after start (exclusive) and before end (inclusive)", function(test) {
  test.equal(time.timeMonth.count(date.local(2011, 00, 01), date.local(2011, 04, 01)), 4);
  test.equal(time.timeMonth.count(date.local(2011, 00, 01), date.local(2011, 03, 30)), 3);
  test.equal(time.timeMonth.count(date.local(2010, 11, 31), date.local(2011, 03, 30)), 4);
  test.equal(time.timeMonth.count(date.local(2010, 11, 31), date.local(2011, 04, 01)), 5);
  test.equal(time.timeMonth.count(date.local(2009, 11, 31), date.local(2012, 04, 01)), 29);
  test.equal(time.timeMonth.count(date.local(2012, 04, 01), date.local(2009, 11, 31)), -29);
  test.end();
});

tape("timeMonth.every(step) returns every stepth month, starting with the first month of the year", function(test) {
  test.deepEqual(time.timeMonth.every(3).range(date.local(2008, 11, 3), date.local(2010, 6, 5)), [date.local(2009, 0, 1), date.local(2009, 3, 1), date.local(2009, 6, 1), date.local(2009, 9, 1), date.local(2010, 0, 1), date.local(2010, 3, 1), date.local(2010, 6, 1)]);
  test.end();
});
     */

}