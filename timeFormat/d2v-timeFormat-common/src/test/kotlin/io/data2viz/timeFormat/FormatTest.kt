package io.data2viz.timeFormat

import io.data2viz.time.*
import kotlin.test.Test



class FormatTest : TestDate() {

    @Test
    fun format_auto() {
        val formatter = autoFormat()

        formatter(date(0, 1, 1, 0, 0, 0, 230)) shouldBe ".230"
        formatter(date(0, 1, 1, 0, 0, 16, 230)) shouldBe ":16"
        formatter(date(0, 1, 1, 0, 28, 16, 230)) shouldBe "12:28"
        formatter(date(0, 1, 1, 20, 28, 16, 230)) shouldBe "08 PM"
        formatter(date(0, 1, 16, 20, 28, 16, 230)) shouldBe "Jan 16"
        formatter(date(0, 6, 16, 20, 28, 16, 230)) shouldBe "June"
        formatter(date(1860)) shouldBe "1860"
    }

    @Test
    fun format_date() {
        val formatter = format("%c")

        formatter(date(1990, 1, 1)) shouldBe "1/1/1990, 12:00:00 AM"
        formatter(date(1990, 1, 2)) shouldBe "1/2/1990, 12:00:00 AM"
        formatter(date(1990, 2, 3)) shouldBe "2/3/1990, 12:00:00 AM"
        formatter(date(1990, 3, 4)) shouldBe "3/4/1990, 12:00:00 AM"
        formatter(date(1995, 1, 5)) shouldBe "1/5/1995, 12:00:00 AM"
    }

    @Test
    fun format_date_a_returns_abbreviated_weekdays() {
        val formatter = format("%a")

        formatter(date(1990, 1, 1)) shouldBe "Mon"
        formatter(date(1990, 1, 2)) shouldBe "Tue"
        formatter(date(1990, 1, 3)) shouldBe "Wed"
        formatter(date(1990, 1, 4)) shouldBe "Thu"
        formatter(date(1990, 1, 5)) shouldBe "Fri"
        formatter(date(1990, 1, 6)) shouldBe "Sat"
        formatter(date(1990, 1, 7)) shouldBe "Sun"
    }

    @Test
    fun format_date_A_returns_weekdays() {
        val formatter = format("%A")

        formatter(date(1990, 1, 1)) shouldBe "Monday"
        formatter(date(1990, 1, 2)) shouldBe "Tuesday"
        formatter(date(1990, 1, 3)) shouldBe "Wednesday"
        formatter(date(1990, 1, 4)) shouldBe "Thursday"
        formatter(date(1990, 1, 5)) shouldBe "Friday"
        formatter(date(1990, 1, 6)) shouldBe "Saturday"
        formatter(date(1990, 1, 7)) shouldBe "Sunday"
    }

    @Test
    fun format_date_b_returns_abbreviated_months() {
        val formatter = format("%b")

        formatter(date(1990, 1, 1)) shouldBe "Jan"
        formatter(date(1990, 2, 1)) shouldBe "Feb"
        formatter(date(1990, 3, 1)) shouldBe "Mar"
        formatter(date(1990, 4, 1)) shouldBe "Apr"
        formatter(date(1990, 5, 1)) shouldBe "May"
        formatter(date(1990, 6, 1)) shouldBe "Jun"
        formatter(date(1990, 7, 1)) shouldBe "Jul"
        formatter(date(1990, 8, 1)) shouldBe "Aug"
        formatter(date(1990, 9, 1)) shouldBe "Sep"
        formatter(date(1990, 10, 1)) shouldBe "Oct"
        formatter(date(1990, 11, 1)) shouldBe "Nov"
        formatter(date(1990, 12, 1)) shouldBe "Dec"
    }

    @Test
    fun format_date_B_returns_months() {
        val formatter = format("%B")

        formatter(date(1990, 1, 1)) shouldBe "January"
        formatter(date(1990, 2, 1)) shouldBe "February"
        formatter(date(1990, 3, 1)) shouldBe "March"
        formatter(date(1990, 4, 1)) shouldBe "April"
        formatter(date(1990, 5, 1)) shouldBe "May"
        formatter(date(1990, 6, 1)) shouldBe "June"
        formatter(date(1990, 7, 1)) shouldBe "July"
        formatter(date(1990, 8, 1)) shouldBe "August"
        formatter(date(1990, 9, 1)) shouldBe "September"
        formatter(date(1990, 10, 1)) shouldBe "October"
        formatter(date(1990, 11, 1)) shouldBe "November"
        formatter(date(1990, 12, 1)) shouldBe "December"
    }

    @Test
    fun format_date_c_returns_format_localized_date_and_time() {
        val formatter = format("%c")

        formatter(date(1990, 1, 1)) shouldBe "1/1/1990, 12:00:00 AM"
    }

    @Test
    fun format_date_d_returns_zero_padded_dates() {
        val formatter = format("%d")

        formatter(date(1990, 1, 1)) shouldBe "01"
    }

    @Test
    fun format_date_e_returns_space_padded_dates() {
        val formatter = format("%e")

        formatter(date(1990, 1, 1)) shouldBe " 1"
    }

    @Test
    fun format_date_H_returns_zero_padded_24_hours() {
        val formatter = format("%H")

        formatter(date(1990, 1, 1, 0)) shouldBe "00"
        formatter(date(1990, 1, 1, 13)) shouldBe "13"
    }

    @Test
    fun format_date_I_returns_zero_padded_12_hours() {
        val formatter = format("%I")

        formatter(date(1990, 1, 1, 0)) shouldBe "12"
        formatter(date(1990, 1, 1, 13)) shouldBe "01"
    }

    @Test
    fun format_date_j_returns_zero_padded_day_of_year_numbers() {
        val formatter = format("%j")

        formatter(date(1990, 1, 1)) shouldBe "001"
        formatter(date(1990, 6, 1)) shouldBe "152"
        formatter(date(2010, 3, 13)) shouldBe "072"
        formatter(date(2010, 3, 14)) shouldBe "073" // DST begins
        formatter(date(2010, 3, 15)) shouldBe "074"
        formatter(date(2010, 11, 6)) shouldBe "310"
        formatter(date(2010, 11, 7)) shouldBe "311" // DST ends
        formatter(date(2010, 11, 8)) shouldBe "312"
    }

    @Test
    fun format_date_m_returns_zero_padded_months() {
        val formatter = format("%m")

        formatter(date(1990, 1, 1)) shouldBe "01"
        formatter(date(1990, 10, 1)) shouldBe "10"
    }

    @Test
    fun format_date_M_returns_zero_padded_minutes() {
        val formatter = format("%M")

        formatter(date(1990, 1, 1)) shouldBe "00"
        formatter(date(1990, 10, 1, 0, 32)) shouldBe "32"
    }

    @Test
    fun format_date_p_returns_AM_or_PM() {
        val formatter = format("%p")

        formatter(date(1990, 1, 1, 0)) shouldBe "AM"
        formatter(date(1990, 10, 1, 13)) shouldBe "PM"
    }

    @Test
    fun format_date_S_returns_zero_padded_seconds() {
        val formatter = format("%S")
        formatter(date(1990, 1, 1, 0, 0, 0)) shouldBe "00"
        formatter(date(1990, 10, 1, 0, 0, 32)) shouldBe "32"

        val formatter2 = format("%0S")
        formatter2(date(1990, 1, 1, 0, 0, 0)) shouldBe "00"
        formatter2(date(1990, 10, 1, 0, 0, 32)) shouldBe "32"
    }

    @Test
    fun format_date_undescore_S_returns_space_padded_seconds() {
        val formatter = format("%_S")

        formatter(date(1990, 1, 1, 0, 0, 0)) shouldBe " 0"
        formatter(date(1990, 10, 1, 0, 0, 3)) shouldBe " 3"
        formatter(date(1990, 10, 1, 0, 0, 32)) shouldBe "32"
    }

    @Test
    fun format_date_minus_S_returns_no_padded_seconds() {
        val formatter = format("%-S")

        formatter(date(1990, 1, 1, 0, 0, 0)) shouldBe "0"
        formatter(date(1990, 10, 1, 0, 0, 3)) shouldBe "3"
        formatter(date(1990, 10, 1, 0, 0, 32)) shouldBe "32"
    }

    @Test
    fun format_date_L_returns_zero_padded_milliseconds() {
        val formatter = format("%L")

        formatter(date(1990, 1, 1, 0, 0, 0, 0)) shouldBe "000"
        formatter(date(1990, 10, 1, 0, 0, 32, 432)) shouldBe "432"
    }


    // TODO : incorrect ?? check http://week-number.net/calendar-with-week-numbers-1990.html
    // even with week starting on sunday...
    /*@Test
    fun format_date_U_returns_zero_padded_week_numbers() {
        val formatter = format("%U")

        formatter(date(1990, 1, 1, 0)) shouldBe "00"
        formatter(date(1990, 6, 1, 0)) shouldBe "21"        // TEST : 21 !!
        formatter(date(2010, 3, 13, 23)) shouldBe "10"
        formatter(date(2010, 3, 14, 0)) shouldBe "11"
        formatter(date(2010, 3, 15, 0)) shouldBe "11"
        formatter(date(2010, 11, 6, 23)) shouldBe "44"
        formatter(date(2010, 11, 7, 0)) shouldBe "45"
        formatter(date(2010, 11, 8, 0)) shouldBe "45"
    }*/

    @Test
    fun format_date_x_returns_format_localized_date() {
        val formatter = format("%x")

        formatter(date(1990, 1, 1)) shouldBe "1/1/1990"
        formatter(date(1990, 6, 1)) shouldBe "6/1/1990"
    }

    @Test
    fun format_date_X_returns_format_localized_time() {
        val formatter = format("%X")

        formatter(date(1990, 1, 1)) shouldBe "12:00:00 AM"
        formatter(date(1990, 1, 1, 13, 34, 59)) shouldBe "1:34:59 PM"
    }

    @Test
    fun format_date_y_returns_format_zero_padded_2_digits_years() {
        val formatter = format("%y")

        formatter(date(1990, 1, 1)) shouldBe "90"
        formatter(date(2002, 1, 1)) shouldBe "02"
        formatter(date(-2, 1, 1)) shouldBe "-02"
    }

    @Test
    fun format_date_Y_returns_format_zero_padded_4_digits_years() {
        val formatter = format("%Y")

        formatter(date(123, 1, 1)) shouldBe "0123"
        formatter(date(1990, 1, 1)) shouldBe "1990"
        formatter(date(2002, 1, 1)) shouldBe "2002"
        formatter(date(10002, 1, 1)) shouldBe "0002"
        formatter(date(-2, 1, 1)) shouldBe "-0002"
    }

    @Test
    fun format_date_percent_format_literal_percent_signs() {
        val formatter = format("%%")

        formatter(date(1990, 1, 1)) shouldBe "%"
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