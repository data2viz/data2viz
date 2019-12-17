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

class HourTests : TestDate() {


    @Test
    fun hour_floor_date_returns_hours() {
        val time = timeHour

        time.floor(date(2010, 12, 31, 23, 59)) shouldBe date(2010, 12, 31, 23)
        time.floor(date(2011, 1, 1, 0, 0)) shouldBe date(2011, 1, 1, 0)
        time.floor(date(2011, 1, 1, 0, 1)) shouldBe date(2011, 1, 1, 0)
    }

    @Test
    fun hour_ceil_returns_hours() {
        val time = timeHour

        time.ceil(date(2010, 12, 31, 23, 59)) shouldBe date(2011, 1, 1, 0)
        time.ceil(date(2011, 1, 1, 0, 0)) shouldBe date(2011, 1, 1, 0)
        time.ceil(date(2011, 1, 1, 0, 1)) shouldBe date(2011, 1, 1, 1)
    }

    @Test
    fun hour_offset_date_step_does_not_modify_the_passed_date() {
        val time = timeHour
        val date = date(2010, 12, 31, 23, 59, 59, 999)

        time.offset(date, 1)
        date shouldBe date(2010, 12, 31, 23, 59, 59, 999)
    }

    @Test
    fun hour_offset_date_step_does_not_round_the_passed_date() {
        val time = timeHour
        val date1 = date(2010, 12, 31, 23, 59, 59, 456)

        val date2 = date(2011, 1, 1, 0, 59, 59, 456)
        time.offset(date1, 1) shouldBe date2

        val date3 = date(2010, 12, 31, 21, 59, 59, 456)
        time.offset(date1, -2) shouldBe date3
    }

    @Test
    fun hour_offset_allows_negative_positive_zero_step() {
        val time = timeHour

        time.offset(date(2010, 12, 31, 12), -1) shouldBe date(2010, 12, 31, 11)
        time.offset(date(2011, 1, 1, 1), -2) shouldBe date(2010, 12, 31, 23)
        time.offset(date(2011, 1, 1, 0), -1) shouldBe date(2010, 12, 31, 23)

        time.offset(date(2010, 12, 31, 11), 1) shouldBe date(2010, 12, 31, 12)
        time.offset(date(2010, 12, 31, 23), 2) shouldBe date(2011, 1, 1, 1)
        time.offset(date(2010, 12, 31, 23), 1) shouldBe date(2011, 1, 1, 0)

        val date1 = date(2010, 12, 31, 23, 59, 59, 999)
        time.offset(date1, 0) shouldBe date1
        val date2 = date(2010, 12, 31, 23, 59, 58, 0)
        time.offset(date2, 0) shouldBe date2
    }

    @Test
    fun hour_range_start_stop_cans_kip_hours() {
        val time = timeHour
        val result = listOf(
                date(2011, 2, 1, 1),
                date(2011, 2, 1, 4),
                date(2011, 2, 1, 7),
                date(2011, 2, 1, 10)
        )

        val range = time.range(date(2011, 2, 1, 1), date(2011, 2, 1, 13), 3)
        range.forEachIndexed { index, r ->
            r shouldBe result[index]
        }
    }

    @Test
    fun hour_range_start_stop_returns_hours_between_start_inclusive_and_stop_exclusive() {
        val time = timeHour
        val result = listOf(
                date(2010, 12, 31, 23),
                date(2011, 1, 1, 0),
                date(2011, 1, 1, 1)
        )

        val range = time.range(date(2010, 12, 31, 23), date(2011, 1, 1, 2))
        range.forEachIndexed { index, r ->
            r shouldBe result[index]
        }
    }

    @Test
    fun hour_range_start_stop_returns_hours() {
        val time = timeHour
        val result = listOf(
                date(2010, 12, 31, 23),
                date(2011, 1, 1, 0),
                date(2011, 1, 1, 1)
        )

        val range = time.range(date(2010, 12, 31, 23), date(2011, 1, 1, 2))
        range.forEachIndexed { index, r ->
            r shouldBe result[index]
        }
    }

    /*


tape("timeHour.floor(date) observes start of daylight savings time", function(test) {
  test.deepEqual(time.timeHour.floor(date.utc(2011, 02, 13, 08, 59)), date.utc(2011, 02, 13, 08));
  test.deepEqual(time.timeHour.floor(date.utc(2011, 02, 13, 09, 00)), date.utc(2011, 02, 13, 09));
  test.deepEqual(time.timeHour.floor(date.utc(2011, 02, 13, 09, 01)), date.utc(2011, 02, 13, 09));
  test.deepEqual(time.timeHour.floor(date.utc(2011, 02, 13, 09, 59)), date.utc(2011, 02, 13, 09));
  test.deepEqual(time.timeHour.floor(date.utc(2011, 02, 13, 10, 00)), date.utc(2011, 02, 13, 10));
  test.deepEqual(time.timeHour.floor(date.utc(2011, 02, 13, 10, 01)), date.utc(2011, 02, 13, 10));
  test.end();
});

tape("timeHour.floor(date) observes end of daylight savings time", function(test) {
  test.deepEqual(time.timeHour.floor(date.utc(2011, 10, 06, 07, 59)), date.utc(2011, 10, 06, 07));
  test.deepEqual(time.timeHour.floor(date.utc(2011, 10, 06, 08, 00)), date.utc(2011, 10, 06, 08));
  test.deepEqual(time.timeHour.floor(date.utc(2011, 10, 06, 08, 01)), date.utc(2011, 10, 06, 08));
  test.deepEqual(time.timeHour.floor(date.utc(2011, 10, 06, 08, 59)), date.utc(2011, 10, 06, 08));
  test.deepEqual(time.timeHour.floor(date.utc(2011, 10, 06, 09, 00)), date.utc(2011, 10, 06, 09));
  test.deepEqual(time.timeHour.floor(date.utc(2011, 10, 06, 09, 01)), date.utc(2011, 10, 06, 09));
  test.end();
});

tape("timeHour.ceil(date) observes start of daylight savings time", function(test) {
  test.deepEqual(time.timeHour.ceil(date.utc(2011, 02, 13, 08, 59)), date.utc(2011, 02, 13, 09));
  test.deepEqual(time.timeHour.ceil(date.utc(2011, 02, 13, 09, 00)), date.utc(2011, 02, 13, 09));
  test.deepEqual(time.timeHour.ceil(date.utc(2011, 02, 13, 09, 01)), date.utc(2011, 02, 13, 10));
  test.deepEqual(time.timeHour.ceil(date.utc(2011, 02, 13, 09, 59)), date.utc(2011, 02, 13, 10));
  test.deepEqual(time.timeHour.ceil(date.utc(2011, 02, 13, 10, 00)), date.utc(2011, 02, 13, 10));
  test.deepEqual(time.timeHour.ceil(date.utc(2011, 02, 13, 10, 01)), date.utc(2011, 02, 13, 11));
  test.end();
});

tape("timeHour.ceil(date) observes end of daylight savings time", function(test) {
  test.deepEqual(time.timeHour.ceil(date.utc(2011, 10, 06, 07, 59)), date.utc(2011, 10, 06, 08));
  test.deepEqual(time.timeHour.ceil(date.utc(2011, 10, 06, 08, 00)), date.utc(2011, 10, 06, 08));
  test.deepEqual(time.timeHour.ceil(date.utc(2011, 10, 06, 08, 01)), date.utc(2011, 10, 06, 09));
  test.deepEqual(time.timeHour.ceil(date.utc(2011, 10, 06, 08, 59)), date.utc(2011, 10, 06, 09));
  test.deepEqual(time.timeHour.ceil(date.utc(2011, 10, 06, 09, 00)), date.utc(2011, 10, 06, 09));
  test.deepEqual(time.timeHour.ceil(date.utc(2011, 10, 06, 09, 01)), date.utc(2011, 10, 06, 10));
  test.end();
});



tape("timeHour.range(start, stop) observes start of daylight savings time", function(test) {
  test.deepEqual(time.timeHour.range(date.local(2011, 2, 13, 1), date.local(2011, 2, 13, 5)), [
    date.utc(2011, 2, 13, 9),
    date.utc(2011, 2, 13, 10),
    date.utc(2011, 2, 13, 11)
  ]);
  test.end();
});

tape("timeHour.range(start, stop) observes end of daylight savings time", function(test) {
  test.deepEqual(time.timeHour.range(date.local(2011, 10, 6, 0), date.local(2011, 10, 6, 2)), [
    date.utc(2011, 10, 6, 7),
    date.utc(2011, 10, 6, 8),
    date.utc(2011, 10, 6, 9)
  ]);
  test.end();
});

tape("timeHour.every(step) returns every stepth hour, starting with the first hour of the day", function(test) {
  test.deepEqual(time.timeHour.every(4).range(date.local(2008, 11, 30, 12, 47), date.local(2008, 11, 31, 13, 57)), [date.local(2008, 11, 30, 16), date.local(2008, 11, 30, 20), date.local(2008, 11, 31, 0), date.local(2008, 11, 31, 4), date.local(2008, 11, 31, 8), date.local(2008, 11, 31, 12)]);
  test.deepEqual(time.timeHour.every(12).range(date.local(2008, 11, 30, 12, 47), date.local(2008, 11, 31, 13, 57)), [date.local(2008, 11, 31, 0), date.local(2008, 11, 31, 12)]);
  test.end();
});

tape("timeHour.range(start, stop) returns every hour crossing the daylight savings boundary", function(test) {
  test.deepEqual(time.timeHour.range(new Date(1478422800000 - 2 * 36e5), new Date(1478422800000 + 2 * 36e5)), [
    new Date(1478415600000), // Sun Nov 06 2016 00:00:00 GMT-0700 (PDT)
    new Date(1478419200000), // Sun Nov 06 2016 01:00:00 GMT-0700 (PDT)
    new Date(1478422800000), // Sun Nov 06 2016 01:00:00 GMT-0800 (PDT)
    new Date(1478426400000)  // Sun Nov 06 2016 02:00:00 GMT-0800 (PDT)
  ]);
  test.end();
});
     */

}