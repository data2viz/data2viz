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

package io.data2viz.timeFormat

import kotlinx.datetime.LocalDateTime
import kotlin.test.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class FormatTest : TestDate() {

    @Test
    fun format_auto() {
        val formatter = autoFormat()

        formatter(LocalDateTime(0, 1, 1, 0, 0, 0, 230000000)) shouldBe ".230"
        formatter(LocalDateTime(0, 1, 1, 0, 0, 16, 230000000)) shouldBe ":16"
        formatter(LocalDateTime(0, 1, 1, 0, 28, 16, 230000000)) shouldBe "12:28"
        formatter(LocalDateTime(0, 1, 1, 20, 28, 16, 230000000)) shouldBe "08 PM"
        formatter(LocalDateTime(0, 1, 16, 20, 28, 16, 230000000)) shouldBe "Jan 16"
        formatter(LocalDateTime(0, 6, 16, 20, 28, 16, 230000000)) shouldBe "June"
        formatter(LocalDateTime(1860, 1, 1, 0, 0)) shouldBe "1860"
    }

    
    @Test
    fun format_date() {
        val formatter = format("%c")

        formatter(LocalDateTime(1990, 1, 1, 0, 0)) shouldBe "1/1/1990, 12:00:00 AM"
        formatter(LocalDateTime(1990, 1, 2, 0, 0)) shouldBe "1/2/1990, 12:00:00 AM"
        formatter(LocalDateTime(1990, 2, 3, 0, 0)) shouldBe "2/3/1990, 12:00:00 AM"
        formatter(LocalDateTime(1990, 3, 4, 0, 0)) shouldBe "3/4/1990, 12:00:00 AM"
        formatter(LocalDateTime(1995, 1, 5, 0, 0)) shouldBe "1/5/1995, 12:00:00 AM"
    }

    
    @Test
    fun format_date_a_returns_abbreviated_weekdays() {
        val formatter = format("%a")

        formatter(LocalDateTime(1990, 1, 1, 0, 0)) shouldBe "Mon"
        formatter(LocalDateTime(1990, 1, 2, 0, 0)) shouldBe "Tue"
        formatter(LocalDateTime(1990, 1, 3, 0, 0)) shouldBe "Wed"
        formatter(LocalDateTime(1990, 1, 4, 0, 0)) shouldBe "Thu"
        formatter(LocalDateTime(1990, 1, 5, 0, 0)) shouldBe "Fri"
        formatter(LocalDateTime(1990, 1, 6, 0, 0)) shouldBe "Sat"
        formatter(LocalDateTime(1990, 1, 7, 0, 0)) shouldBe "Sun"
    }

    
    @Test
    fun format_date_A_returns_weekdays() {
        val formatter = format("%A")

        formatter(LocalDateTime(1990, 1, 1, 0, 0)) shouldBe "Monday"
        formatter(LocalDateTime(1990, 1, 2, 0, 0)) shouldBe "Tuesday"
        formatter(LocalDateTime(1990, 1, 3, 0, 0)) shouldBe "Wednesday"
        formatter(LocalDateTime(1990, 1, 4, 0, 0)) shouldBe "Thursday"
        formatter(LocalDateTime(1990, 1, 5, 0, 0)) shouldBe "Friday"
        formatter(LocalDateTime(1990, 1, 6, 0, 0)) shouldBe "Saturday"
        formatter(LocalDateTime(1990, 1, 7, 0, 0)) shouldBe "Sunday"
    }

    
    @Test
    fun format_date_b_returns_abbreviated_months() {
        val formatter = format("%b")

        formatter(LocalDateTime(1990, 1,  1, 0, 0)) shouldBe "Jan"
        formatter(LocalDateTime(1990, 2,  1, 0, 0)) shouldBe "Feb"
        formatter(LocalDateTime(1990, 3,  1, 0, 0)) shouldBe "Mar"
        formatter(LocalDateTime(1990, 4,  1, 0, 0)) shouldBe "Apr"
        formatter(LocalDateTime(1990, 5,  1, 0, 0)) shouldBe "May"
        formatter(LocalDateTime(1990, 6,  1, 0, 0)) shouldBe "Jun"
        formatter(LocalDateTime(1990, 7,  1, 0, 0)) shouldBe "Jul"
        formatter(LocalDateTime(1990, 8,  1, 0, 0)) shouldBe "Aug"
        formatter(LocalDateTime(1990, 9,  1, 0, 0)) shouldBe "Sep"
        formatter(LocalDateTime(1990, 10, 1, 0, 0)) shouldBe "Oct"
        formatter(LocalDateTime(1990, 11, 1, 0, 0)) shouldBe "Nov"
        formatter(LocalDateTime(1990, 12, 1, 0, 0)) shouldBe "Dec"
    }

    
    @Test
    fun format_date_B_returns_months() {
        val formatter = format("%B")

        formatter(LocalDateTime(1990, 1,  1, 0, 0)) shouldBe "January"
        formatter(LocalDateTime(1990, 2,  1, 0, 0)) shouldBe "February"
        formatter(LocalDateTime(1990, 3,  1, 0, 0)) shouldBe "March"
        formatter(LocalDateTime(1990, 4,  1, 0, 0)) shouldBe "April"
        formatter(LocalDateTime(1990, 5,  1, 0, 0)) shouldBe "May"
        formatter(LocalDateTime(1990, 6,  1, 0, 0)) shouldBe "June"
        formatter(LocalDateTime(1990, 7,  1, 0, 0)) shouldBe "July"
        formatter(LocalDateTime(1990, 8,  1, 0, 0)) shouldBe "August"
        formatter(LocalDateTime(1990, 9,  1, 0, 0)) shouldBe "September"
        formatter(LocalDateTime(1990, 10, 1, 0, 0)) shouldBe "October"
        formatter(LocalDateTime(1990, 11, 1, 0, 0)) shouldBe "November"
        formatter(LocalDateTime(1990, 12, 1, 0, 0)) shouldBe "December"
    }

    
    @Test
    fun format_date_c_returns_format_localized_date_and_time() {
        val formatter = format("%c")

        formatter(LocalDateTime(1990, 1, 1, 0, 0)) shouldBe "1/1/1990, 12:00:00 AM"
    }

    
    @Test
    fun format_date_d_returns_zero_padded_dates() {
        val formatter = format("%d")

        formatter(LocalDateTime(1990, 1, 1, 0, 0)) shouldBe "01"
    }

    
    @Test
    fun format_date_e_returns_space_padded_dates() {
        val formatter = format("%e")

        formatter(LocalDateTime(1990, 1, 1, 0, 0)) shouldBe " 1"
    }

    
    @Test
    fun format_date_H_returns_zero_padded_24_hours() {
        val formatter = format("%H")

        formatter(LocalDateTime(1990, 1, 1, 0, 0, 0)) shouldBe "00"
        formatter(LocalDateTime(1990, 1, 1, 13, 0, 0)) shouldBe "13"
    }

    
    @Test
    fun format_date_I_returns_zero_padded_12_hours() {
        val formatter = format("%I")

        formatter(LocalDateTime(1990, 1, 1, 0, 0, 0)) shouldBe "12"
        formatter(LocalDateTime(1990, 1, 1, 13, 0, 0)) shouldBe "01"
    }

    
    @Test
    fun format_date_j_returns_zero_padded_day_of_year_numbers() {
        val formatter = format("%j")

        formatter(LocalDateTime(1990, 1,  1, 0, 0)) shouldBe "001"
        formatter(LocalDateTime(1990, 6,  1, 0, 0)) shouldBe "152"
        formatter(LocalDateTime(2010, 3, 13, 0, 0)) shouldBe "072"
        formatter(LocalDateTime(2010, 3, 14, 0, 0)) shouldBe "073" // DST begins
        formatter(LocalDateTime(2010, 3, 15, 0, 0)) shouldBe "074"
        formatter(LocalDateTime(2010, 11, 6, 0, 0)) shouldBe "310"
        formatter(LocalDateTime(2010, 11, 7, 0, 0)) shouldBe "311" // DST ends
        formatter(LocalDateTime(2010, 11, 8, 0, 0)) shouldBe "312"
    }

    
    @Test
    fun format_date_m_returns_zero_padded_months() {
        val formatter = format("%m")

        formatter(LocalDateTime(1990, 1, 1, 0, 0)) shouldBe "01"
        formatter(LocalDateTime(1990, 10, 1, 0, 0)) shouldBe "10"
    }

    
    @Test
    fun format_date_M_returns_zero_padded_minutes() {
        val formatter = format("%M")

        formatter(LocalDateTime(1990, 1, 1, 0, 0)) shouldBe "00"
        formatter(LocalDateTime(1990, 10, 1, 0, 32)) shouldBe "32"
    }

    
    @Test
    fun format_date_p_returns_AM_or_PM() {
        val formatter = format("%p")

        formatter(LocalDateTime(1990, 1, 1, 0, 0, 0)) shouldBe "AM"
        formatter(LocalDateTime(1990, 10, 1, 13, 0, 0)) shouldBe "PM"
    }

    
    @Test
    fun format_date_S_returns_zero_padded_seconds() {
        val formatter = format("%S")
        formatter(LocalDateTime(1990, 1, 1, 0, 0, 0)) shouldBe "00"
        formatter(LocalDateTime(1990, 10, 1, 0, 0, 32)) shouldBe "32"

        val formatter2 = format("%0S")
        formatter2(LocalDateTime(1990, 1, 1, 0, 0, 0)) shouldBe "00"
        formatter2(LocalDateTime(1990, 10, 1, 0, 0, 32)) shouldBe "32"
    }

    
    @Test
    fun format_date_undescore_S_returns_space_padded_seconds() {
        val formatter = format("%_S")

        formatter(LocalDateTime(1990, 1, 1, 0, 0, 0)) shouldBe " 0"
        formatter(LocalDateTime(1990, 10, 1, 0, 0, 3)) shouldBe " 3"
        formatter(LocalDateTime(1990, 10, 1, 0, 0, 32)) shouldBe "32"
    }

    
    @Test
    fun format_date_minus_S_returns_no_padded_seconds() {
        val formatter = format("%-S")

        formatter(LocalDateTime(1990, 1, 1, 0, 0, 0)) shouldBe "0"
        formatter(LocalDateTime(1990, 10, 1, 0, 0, 3)) shouldBe "3"
        formatter(LocalDateTime(1990, 10, 1, 0, 0, 32)) shouldBe "32"
    }

    
    @Test
    fun format_date_L_returns_zero_padded_milliseconds() {
        val formatter = format("%L")

        formatter(LocalDateTime(1990, 1, 1, 0, 0, 0, 0)) shouldBe "000"
        formatter(LocalDateTime(1990, 10, 1, 0, 0, 32, 432000000)) shouldBe "432"
    }


    // TODO : incorrect ?? check http://week-number.net/calendar-with-week-numbers-1990.html
    // even with week starting on sunday...
    /*@Test
    fun format_date_U_returns_zero_padded_week_numbers() {
        val formatter = format("%U")

        formatter(LocalDateTime(1990, 1, 1, 0)) shouldBe "00"
        formatter(LocalDateTime(1990, 6, 1, 0)) shouldBe "21"        // TEST : 21 !!
        formatter(LocalDateTime(2010, 3, 13, 23)) shouldBe "10"
        formatter(LocalDateTime(2010, 3, 14, 0)) shouldBe "11"
        formatter(LocalDateTime(2010, 3, 15, 0)) shouldBe "11"
        formatter(LocalDateTime(2010, 11, 6, 23)) shouldBe "44"
        formatter(LocalDateTime(2010, 11, 7, 0)) shouldBe "45"
        formatter(LocalDateTime(2010, 11, 8, 0)) shouldBe "45"
    }*/

    
    @Test
    fun format_date_x_returns_format_localized_date() {
        val formatter = format("%x")

        formatter(LocalDateTime(1990, 1, 1, 0, 0)) shouldBe "1/1/1990"
        formatter(LocalDateTime(1990, 6, 1, 0, 0)) shouldBe "6/1/1990"
    }

    
    @Test
    fun format_date_X_returns_format_localized_time() {
        val formatter = format("%X")

        formatter(LocalDateTime(1990, 1, 1, 0, 0)) shouldBe "12:00:00 AM"
        formatter(LocalDateTime(1990, 1, 1, 13, 34, 59)) shouldBe "1:34:59 PM"
    }

    
    @Test
    fun format_date_y_returns_format_zero_padded_2_digits_years() {
        val formatter = format("%y")

        formatter(LocalDateTime(1990, 1, 1, 0, 0)) shouldBe "90"
        formatter(LocalDateTime(2002, 1, 1, 0, 0)) shouldBe "02"
        formatter(LocalDateTime(-2, 1, 1, 0, 0)) shouldBe "-02"
    }

    
    @Test
    fun format_date_Y_returns_format_zero_padded_4_digits_years() {
        val formatter = format("%Y")

        formatter(LocalDateTime(123, 1, 1, 0, 0)) shouldBe "0123"
        formatter(LocalDateTime(1990, 1, 1, 0, 0)) shouldBe "1990"
        formatter(LocalDateTime(2002, 1, 1, 0, 0)) shouldBe "2002"
        formatter(LocalDateTime(10002, 1, 1, 0, 0)) shouldBe "0002"
        formatter(LocalDateTime(-2, 1, 1, 0, 0)) shouldBe "-0002"
    }

    
    @Test
    fun format_date_percent_format_literal_percent_signs() {
        val formatter = format("%%")

        formatter(LocalDateTime(1990, 1, 1, 0, 0)) shouldBe "%"
    }
}

    /*
tape("timeFormat(\"%Z\")(date) formats time zones", function(test) {
  var f = timeFormat.timeFormat("%Z");
  test.equal(f(date.local(1990, 0, 1)), "-0800");
  test.end();
});

// NOTE multi is moved to Locale.autoFormat()
tape("timeFormat(…) can be used to create a conditional multi-format", function(test) {
  test.equal(multi(date.local(1990, 0, 1, 0, 0, 0, 12)), ".012");
  test.equal(multi(date.local(1990, 0, 1, 0, 0, 1,  0)), ":01");
  test.equal(multi(date.local(1990, 0, 1, 0, 1, 0,  0)), "12:01");
  test.equal(multi(date.local(1990, 0, 1, 1, 0, 0,  0)), "01 AM");
  test.equal(multi(date.local(1990, 0, 2, 0, 0, 0,  0)), "Tue 02");
  test.equal(multi(date.local(1990, 1, 1, 0, 0, 0,  0)), "February");
  test.equal(multi(date.local(1990, 0, 1, 0, 0, 0,  0)), "1990");
  test.end();
});
*/