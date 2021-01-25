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

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.test.Test

@Suppress("DEPRECATION")
class MonthTests : TestDate() {

    private val zoneLocal = zoneLocal()

    private fun dateUtc(y: Int, mo: Int, d: Int, h: Int = 0, mi:Int = 0, s:Int = 0, ms: Int = 0) =
        LocalDateTime(y, mo, d, h, mi, s, ms).toInstant(TimeZone.UTC)

    private fun dateLocal(y: Int, mo: Int, d: Int, h: Int = 0, mi:Int = 0, s:Int = 0, ms: Int = 0) =
        LocalDateTime(y, mo, d, h, mi, s, ms).toInstant(zoneLocal)

    @Test
    fun month_floor_date_returns_months() {
        val time = Intervals.timeMonth

        time.floor(TimeZone.UTC, dateUtc(2010, 12, 31, 23, 59, 59)) shouldBe dateUtc(2010, 12, 1, 0, 0)
        time.floor(TimeZone.UTC, dateUtc(2011, 1, 1, 0, 0, 0)) shouldBe dateUtc(2011, 1, 1, 0, 0)
        time.floor(TimeZone.UTC, dateUtc(2011, 1, 1, 0, 0, 1)) shouldBe dateUtc(2011, 1, 1, 0, 0)
    }

    @Test
    fun month_floor_observes_start_of_DST() {
        val time = Intervals.timeMonth

        time.floor(TimeZone.UTC, dateUtc(2011, 3, 13, 1, 0)) shouldBe dateUtc(2011, 3, 1, 0, 0)
    }

    @Test
    fun month_floor_observes_end_of_DST() {
        val time = Intervals.timeMonth

        time.floor(TimeZone.UTC, dateUtc(2011, 11, 6, 1, 0)) shouldBe dateUtc(2011, 11, 1, 0, 0)
    }

    @Test
    fun month_floor_handles_years_in_first_century() {
        val time = Intervals.timeMonth

        time.floor(TimeZone.UTC, dateUtc(11, 11, 6, 7, 0)) shouldBe dateUtc(11, 11, 1, 0, 0)
    }

    @Test
    fun month_ceil_returns_months() {
        with (Intervals.timeMonth) { TimeZone.UTC.ceil(dateUtc(2010, 12, 31, 23, 59, 59)) } shouldBe dateUtc(2011, 1, 1, 0, 0)
        with (Intervals.timeMonth) { TimeZone.UTC.ceil(dateUtc(2011, 1, 1, 0, 0, 0)) } shouldBe dateUtc(2011, 1, 1, 0, 0)
        with (Intervals.timeMonth) { TimeZone.UTC.ceil(dateUtc(2011, 1, 1, 0, 0, 1)) } shouldBe dateUtc(2011, 2, 1, 0, 0)
    }

    @Test
    fun month_ceil_observes_start_of_DST() {
        with (Intervals.timeMonth) { TimeZone.UTC.ceil(dateUtc(2011, 3, 13, 1, 0)) } shouldBe dateUtc(2011, 4, 1, 0, 0)
    }

    @Test
    fun month_ceil_observes_end_of_DST() {
        with (Intervals.timeMonth) { TimeZone.UTC.ceil(dateUtc(2011, 11, 6, 1, 0)) } shouldBe dateUtc(2011, 12, 1, 0, 0)
    }

    @Test
    fun month_offset_does_not_modify_passed_in_date() {
        val time = Intervals.timeMonth
        val date = dateUtc(2010, 12, 31, 23, 59, 59, 999)

        time.offset(TimeZone.UTC, date, 1) shouldBe dateUtc(2011, 1, 31, 23, 59, 59, 999)
        date shouldBe dateUtc(2010, 12, 31, 23, 59, 59, 999)
    }

    @Test
    fun month_offset_does_not_round_passed_in_date() {
        Intervals.timeMonth.offset(TimeZone.UTC, dateUtc(2010, 12, 31, 23, 59, 59, 999), 1) shouldBe dateUtc(2011, 1, 31, 23, 59, 59, 999)
        Intervals.timeMonth.offset(TimeZone.UTC, dateUtc(2010, 12, 31, 23, 59, 59, 456), -2) shouldBe dateUtc(2010, 10, 31, 23, 59, 59, 456)
    }

    @Test
    fun month_offset_allows_negative_offsets() {
        Intervals.timeMonth.offset(TimeZone.UTC, dateUtc(2010, 12, 1, 0, 0), -1) shouldBe dateUtc(2010, 11, 1, 0, 0)
        Intervals.timeMonth.offset(TimeZone.UTC, dateUtc(2011, 1, 1, 0, 0), -2) shouldBe dateUtc(2010, 11, 1, 0, 0)
        Intervals.timeMonth.offset(TimeZone.UTC, dateUtc(2011, 1, 1, 0, 0), -1) shouldBe dateUtc(2010, 12, 1, 0, 0)
    }

    @Test
    fun month_offset_allows_positive_offsets() {
        Intervals.timeMonth.offset(TimeZone.UTC, dateUtc(2010, 11, 1, 0, 0), 1) shouldBe dateUtc(2010, 12, 1, 0, 0)
        Intervals.timeMonth.offset(TimeZone.UTC, dateUtc(2010, 11, 1, 0, 0), 2) shouldBe dateUtc(2011, 1, 1, 0, 0)
        Intervals.timeMonth.offset(TimeZone.UTC, dateUtc(2010, 12, 1, 0, 0), 1) shouldBe dateUtc(2011, 1, 1, 0, 0)
    }

    @Test
    fun month_offset_allows_zero_offsets() {
        Intervals.timeMonth.offset(TimeZone.UTC, dateUtc(2010, 12, 31, 23, 59, 59, 999), 0) shouldBe dateUtc(2010, 12, 31, 23, 59, 59, 999)
        Intervals.timeMonth.offset(TimeZone.UTC, dateUtc(2010, 12, 31, 23, 59, 58, 0), 0) shouldBe dateUtc(2010, 12, 31, 23, 59, 58, 0)
    }

    @Test
    fun timeMonth_floor_returns_months() {
        Intervals.timeMonth.floor(TimeZone.UTC, dateUtc(2010, 11, 30, 23, 0)) shouldBe dateUtc(2010, 11, 1, 0, 0)
        Intervals.timeMonth.floor(TimeZone.UTC, dateUtc(2010, 1, 1, 0, 0)) shouldBe dateUtc(2010, 1, 1, 0, 0)
        Intervals.timeMonth.floor(TimeZone.UTC, dateUtc(2010, 1, 1, 1, 0)) shouldBe dateUtc(2010, 1, 1, 0, 0)
    }

    @Test
    fun timeMonth_floor_handles_months_in_the_first_century() {
        Intervals.timeMonth.floor(TimeZone.UTC, dateUtc(11, 11, 6, 7, 0)) shouldBe dateUtc(11, 11, 1, 0, 0)
    }

    @Test
    fun timeMonth_round_returns_months() {
        with (Intervals.timeMonth) { TimeZone.UTC.round(dateUtc(2010, 12, 16, 12, 0)) } shouldBe dateUtc(2011, 1, 1, 0, 0)
        with (Intervals.timeMonth) { TimeZone.UTC.round(dateUtc(2010, 12, 16, 11, 0)) } shouldBe dateUtc(2010, 12, 1, 0, 0)
    }

    @Test
    fun timeMonth_ceil_returns_months() {
        with (Intervals.timeMonth) { TimeZone.UTC.ceil(dateUtc(2010, 11, 30, 23, 0)) } shouldBe dateUtc(2010, 12, 1, 0, 0)
        with (Intervals.timeMonth) { TimeZone.UTC.ceil(dateUtc(2010, 12, 1, 1, 0)) } shouldBe dateUtc(2011, 1, 1, 0, 0)
        with (Intervals.timeMonth) { TimeZone.UTC.ceil(dateUtc(2011, 2, 1, 0, 0)) } shouldBe dateUtc(2011, 2, 1, 0, 0)
        with (Intervals.timeMonth) { TimeZone.UTC.ceil(dateUtc(2011, 3, 1, 0, 0)) } shouldBe dateUtc(2011, 3, 1, 0, 0)
        with (Intervals.timeMonth) { TimeZone.UTC.ceil(dateUtc(2011, 4, 1, 0, 0)) } shouldBe dateUtc(2011, 4, 1, 0, 0)
    }

    @Test
    fun month_offset_does_not_modify_the_passed_in_date() {
        val time = Intervals.timeMonth
        val d = dateUtc(2010, 12, 31, 23, 59, 59, 999)

        time.offset(TimeZone.UTC, d, 1)
        d shouldBe dateUtc(2010, 12, 31, 23, 59, 59, 999)
    }

    @Test
    fun timeMonth_floor_observes_DST() {
        Intervals.timeMonth.floor(TimeZone.UTC, dateUtc(2011, 3, 13,  13, 7)) shouldBe dateUtc(2011, 3, 1)
        Intervals.timeMonth.floor(TimeZone.UTC, dateUtc(2011, 3, 13,  13, 8)) shouldBe dateUtc(2011, 3, 1)
        Intervals.timeMonth.floor(TimeZone.UTC, dateUtc(2011, 3, 13,  13, 9)) shouldBe dateUtc(2011, 3, 1)
        Intervals.timeMonth.floor(TimeZone.UTC, dateUtc(2011, 3, 13,  13, 10)) shouldBe dateUtc(2011, 3, 1)
        Intervals.timeMonth.floor(TimeZone.UTC, dateUtc(2011, 11, 6, 6, 7)) shouldBe dateUtc(2011, 11, 1)
        Intervals.timeMonth.floor(TimeZone.UTC, dateUtc(2011, 11, 6, 6, 8)) shouldBe dateUtc(2011, 11, 1)
        Intervals.timeMonth.floor(TimeZone.UTC, dateUtc(2011, 11, 6, 6, 9)) shouldBe dateUtc(2011, 11, 1)
        Intervals.timeMonth.floor(TimeZone.UTC, dateUtc(2011, 11, 6, 6, 10)) shouldBe dateUtc(2011, 11, 1)
    }

    @Test
    fun timeMonth_round_observes_DST() {
        with (Intervals.timeMonth) { TimeZone.UTC.round(dateUtc(2011, 3, 13,  13, 7)) } shouldBe dateUtc(2011, 3, 1)
        with (Intervals.timeMonth) { TimeZone.UTC.round(dateUtc(2011, 3, 13,  13, 8)) } shouldBe dateUtc(2011, 3, 1)
        with (Intervals.timeMonth) { TimeZone.UTC.round(dateUtc(2011, 3, 13,  13, 9)) } shouldBe dateUtc(2011, 3, 1)
        with (Intervals.timeMonth) { TimeZone.UTC.round(dateUtc(2011, 3, 13,  13, 10)) } shouldBe dateUtc(2011, 3, 1)
        with (Intervals.timeMonth) { TimeZone.UTC.round(dateUtc(2011, 11, 6, 6, 7)) } shouldBe dateUtc(2011, 11, 1)
        with (Intervals.timeMonth) { TimeZone.UTC.round(dateUtc(2011, 11, 6, 6, 8)) } shouldBe dateUtc(2011, 11, 1)
        with (Intervals.timeMonth) { TimeZone.UTC.round(dateUtc(2011, 11, 6, 6, 9)) } shouldBe dateUtc(2011, 11, 1)
        with (Intervals.timeMonth) { TimeZone.UTC.round(dateUtc(2011, 11, 6, 6, 10)) } shouldBe dateUtc(2011, 11, 1)
    }

    @Test
    fun timeMonth_ceil_observes_DST() {
        with (Intervals.timeMonth) { TimeZone.UTC.ceil(dateUtc(2011, 3, 13,  13, 7)) } shouldBe dateUtc(2011, 4, 1)
        with (Intervals.timeMonth) { TimeZone.UTC.ceil(dateUtc(2011, 3, 13,  13, 8)) } shouldBe dateUtc(2011, 4, 1)
        with (Intervals.timeMonth) { TimeZone.UTC.ceil(dateUtc(2011, 3, 13,  13, 9)) } shouldBe dateUtc(2011, 4, 1)
        with (Intervals.timeMonth) { TimeZone.UTC.ceil(dateUtc(2011, 3, 13,  13, 10)) } shouldBe dateUtc(2011, 4, 1)
        with (Intervals.timeMonth) { TimeZone.UTC.ceil(dateUtc(2011, 11, 6, 6, 7)) } shouldBe dateUtc(2011, 12, 1)
        with (Intervals.timeMonth) { TimeZone.UTC.ceil(dateUtc(2011, 11, 6, 6, 8)) } shouldBe dateUtc(2011, 12, 1)
        with (Intervals.timeMonth) { TimeZone.UTC.ceil(dateUtc(2011, 11, 6, 6, 9)) } shouldBe dateUtc(2011, 12, 1)
        with (Intervals.timeMonth) { TimeZone.UTC.ceil(dateUtc(2011, 11, 6, 6, 10)) } shouldBe dateUtc(2011, 12, 1)
    }

    @Test
    fun timeMonth_range_returns_expected() {
        with (Intervals.timeMonth) { TimeZone.UTC.range(dateUtc(2011, 12, 1), dateUtc(2012, 6, 1)) } shouldBe
                listOf(
                    dateUtc(2011, 12, 1),
                    dateUtc(2012, 1, 1),
                    dateUtc(2012, 2, 1),
                    dateUtc(2012, 3, 1),
                    dateUtc(2012, 4, 1),
                    dateUtc(2012, 5, 1)
                )

        with (Intervals.timeMonth) { zoneLocal.range(dateLocal(2011, 12, 1), dateLocal(2012, 6, 1)) } shouldBe
                listOf(
                    dateLocal(2011, 12, 1),
                    dateLocal(2012, 1, 1),
                    dateLocal(2012, 2, 1),
                    dateLocal(2012, 3, 1),
                    dateLocal(2012, 4, 1),
                    dateLocal(2012, 5, 1)
                )
    }

    /*

tape("Intervals.timeMonth.range(start, stop) returns months", function(test) {
  test.deepEqual(time.Intervals.timeMonth.range(date.local(2011, 10, 04, 02), date.local(2012, 04, 10, 13)), [
    date.local(2011, 11, 01),
    date.local(2012, 00, 01),
    date.local(2012, 01, 01),
    date.local(2012, 02, 01),
    date.local(2012, 03, 01),
    date.local(2012, 04, 01)
  ]);
  test.end();
});

tape("Intervals.timeMonth.range(start, stop) coerces start and stop to dates", function(test) {
  test.deepEqual(time.Intervals.timeMonth.range(+date.local(2011, 10, 04), +date.local(2012, 01, 07)), [
    date.local(2011, 11, 01),
    date.local(2012, 00, 01),
    date.local(2012, 01, 01)
  ]);
  test.end();
});

tape("Intervals.timeMonth.range(start, stop) returns the empty array for invalid dates", function(test) {
  test.deepEqual(time.Intervals.timeMonth.range(new Date(NaN), Infinity), []);
  test.end();
});

tape("Intervals.timeMonth.range(start, stop) returns the empty array if start >= stop", function(test) {
  test.deepEqual(time.Intervals.timeMonth.range(date.local(2011, 11, 10), date.local(2011, 10, 04)), []);
  test.deepEqual(time.Intervals.timeMonth.range(date.local(2011, 10, 01), date.local(2011, 10, 01)), []);
  test.end();
});

tape("Intervals.timeMonth.range(start, stop) returns months", function(test) {
  test.deepEqual(time.Intervals.timeMonth.range(date.local(2010, 10, 31), date.local(2011, 2, 1)), [
    date.local(2010, 11, 1),
    date.local(2011, 0, 1),
    date.local(2011, 1, 1)
  ]);
  test.end();
});

tape("Intervals.timeMonth.range(start, stop) has an inclusive lower bound", function(test) {
  test.deepEqual(time.Intervals.timeMonth.range(date.local(2010, 10, 31), date.local(2011, 2, 1))[0], date.local(2010, 11, 1));
  test.end();
});

tape("Intervals.timeMonth.range(start, stop) has an exclusive upper bound", function(test) {
  test.deepEqual(time.Intervals.timeMonth.range(date.local(2010, 10, 31), date.local(2011, 2, 1))[2], date.local(2011, 1, 1));
  test.end();
});

tape("Intervals.timeMonth.range(start, stop) can skip months", function(test) {
  test.deepEqual(time.Intervals.timeMonth.range(date.local(2011, 1, 1), date.local(2012, 1, 1), 3), [
    date.local(2011, 1, 1),
    date.local(2011, 4, 1),
    date.local(2011, 7, 1),
    date.local(2011, 10, 1)
  ]);
  test.end();
});

tape("Intervals.timeMonth.range(start, stop) observes start of daylight savings time", function(test) {
  test.deepEqual(time.Intervals.timeMonth.range(date.local(2011, 0, 1), date.local(2011, 4, 1)), [
    date.local(2011, 0, 1),
    date.local(2011, 1, 1),
    date.local(2011, 2, 1),
    date.local(2011, 3, 1)
  ]);
  test.end();
});

tape("Intervals.timeMonth.range(start, stop) observes end of daylight savings time", function(test) {
  test.deepEqual(time.Intervals.timeMonth.range(date.local(2011, 9, 1), date.local(2012, 1, 1)), [
    date.local(2011, 9, 1),
    date.local(2011, 10, 1),
    date.local(2011, 11, 1),
    date.local(2012, 0, 1)
  ]);
  test.end();
});

tape("Intervals.timeMonth.count(start, end) counts months after start (exclusive) and before end (inclusive)", function(test) {
  test.equal(time.Intervals.timeMonth.count(date.local(2011, 00, 01), date.local(2011, 04, 01)), 4);
  test.equal(time.Intervals.timeMonth.count(date.local(2011, 00, 01), date.local(2011, 03, 30)), 3);
  test.equal(time.Intervals.timeMonth.count(date.local(2010, 11, 31), date.local(2011, 03, 30)), 4);
  test.equal(time.Intervals.timeMonth.count(date.local(2010, 11, 31), date.local(2011, 04, 01)), 5);
  test.equal(time.Intervals.timeMonth.count(date.local(2009, 11, 31), date.local(2012, 04, 01)), 29);
  test.equal(time.Intervals.timeMonth.count(date.local(2012, 04, 01), date.local(2009, 11, 31)), -29);
  test.end();
});

tape("Intervals.timeMonth.every(step) returns every stepth month, starting with the first month of the year", function(test) {
  test.deepEqual(time.Intervals.timeMonth.every(3).range(date.local(2008, 11, 3), date.local(2010, 6, 5)), [date.local(2009, 0, 1), date.local(2009, 3, 1), date.local(2009, 6, 1), date.local(2009, 9, 1), date.local(2010, 0, 1), date.local(2010, 3, 1), date.local(2010, 6, 1)]);
  test.end();
});
     */

}