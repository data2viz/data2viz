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

class ParseTest : TestDate() {

    @Test
    fun parse_string() {
        val parser = parse("%c")

        parser("1/1/1990, 12:00:00 AM") shouldBe LocalDateTime(1990, 1, 1, 0, 0)
        parser("1/2/1990, 12:00:00 AM") shouldBe LocalDateTime(1990, 1, 2, 0, 0)
        parser("1/3/1990, 12:00:00 AM") shouldBe LocalDateTime(1990, 1, 3, 0, 0)
        parser("1/4/1990, 12:00:00 AM") shouldBe LocalDateTime(1990, 1, 4, 0, 0)
        parser("1/5/1990, 12:00:00 AM") shouldBe LocalDateTime(1990, 1, 5, 0, 0)
    }

    
    @Test
    fun parse_amdY_parses_abbreviated_weekday_and_date() {
        val parser = parse("%a %m/%d/%Y")

        parser("Sun 01/01/1990") shouldBe LocalDateTime(1990, 1, 1, 0, 0)
        parser("Wed 02/03/1991") shouldBe LocalDateTime(1991, 2, 3, 0, 0)
        parser("XXX 03/10/2010") shouldBe null
    }

    
    @Test
    fun parse_AmdY_parses_weekday_and_date() {
        val parser = parse("%A %m/%d/%Y")

        // note : ignores the day name
        parser("Sunday 01/01/1990") shouldBe LocalDateTime(1990, 1, 1, 0, 0)
        parser("Monday 01/01/1990") shouldBe LocalDateTime(1990, 1, 1, 0, 0)
        parser("Tuesday 01/01/1990") shouldBe LocalDateTime(1990, 1, 1, 0, 0)
        parser("Wednesday 01/01/1990") shouldBe LocalDateTime(1990, 1, 1, 0, 0)

        parser("Wednesday 02/03/1991") shouldBe LocalDateTime(1991, 2, 3, 0, 0)
        parser("Caturday 03/10/2010") shouldBe null
    }

    
    @Test
    fun parse_UY_parses_week_number_sunday_and_year() {
        val parser = parse("%U %Y")

        parser("00 1990") shouldBe LocalDateTime(1989, 12, 31, 0, 0)
        parser("05 1991") shouldBe LocalDateTime(1991, 2, 3, 0, 0)
        parser("01 1995") shouldBe LocalDateTime(1995, 1, 1, 0, 0)
    }

    
    @Test
    fun parse_aUY_parses_abbreviated_weekday_week_number_sunday_and_year() {
        val parser = parse("%a %U %Y")

        parser("Mon 00 1990") shouldBe LocalDateTime(1990, 1, 1, 0, 0)
        parser("Sun 05 1991") shouldBe LocalDateTime(1991, 2, 3, 0, 0)
        parser("Sun 01 1995") shouldBe LocalDateTime(1995, 1, 1, 0, 0)
        parser("XXX 01 1995") shouldBe null
    }

    
    @Test
    fun parse_AUY_parses_weekday_week_number_sunday_and_year() {
        val parser = parse("%A %U %Y")

        parser("Monday 00 1990") shouldBe LocalDateTime(1990, 1, 1, 0, 0)
        parser("Sunday 05 1991") shouldBe LocalDateTime(1991, 2, 3, 0, 0)
        parser("Sunday 01 1995") shouldBe LocalDateTime(1995, 1, 1, 0, 0)
        parser("Plop 01 1995") shouldBe null
    }

    
    @Test
    fun parse_wUY_parses_numeric_weekday_week_number_sunday_and_year() {
        val parser = parse("%w %U %Y")

        parser("1 00 1990") shouldBe LocalDateTime(1990, 1, 1, 0, 0)
        parser("0 05 1991") shouldBe LocalDateTime(1991, 2, 3, 0, 0)
        parser("0 01 1995") shouldBe LocalDateTime(1995, 1, 1, 0, 0)
        parser("X 01 1995") shouldBe null
    }

    
    @Test
    fun parse_WY_parses_week_number_monday_and_year() {
        val parser = parse("%W %Y")

        parser("01 1990") shouldBe LocalDateTime(1990, 1, 1, 0, 0)
        parser("04 1991") shouldBe LocalDateTime(1991, 1, 28, 0, 0)
        parser("00 1995") shouldBe LocalDateTime(1994, 12, 26, 0, 0)
    }

    
    @Test
    fun parse_aWY_parses_abbreviated_weekday_week_number_monday_and_year() {
        val parser = parse("%a %W %Y")

        parser("Mon 01 1990") shouldBe LocalDateTime(1990, 1, 1, 0, 0)
        parser("Sun 04 1991") shouldBe LocalDateTime(1991, 2, 3, 0, 0)
        parser("Sun 00 1995") shouldBe LocalDateTime(1995, 1, 1, 0, 0)
        parser("XXX 00 1995") shouldBe null
    }

    
    @Test
    fun parse_AWY_parses_weekday_week_number_monday_and_year() {
        val parser = parse("%A %W %Y")

        parser("Monday 01 1990") shouldBe LocalDateTime(1990, 1, 1, 0, 0)
        parser("Sunday 04 1991") shouldBe LocalDateTime(1991, 2, 3, 0, 0)
        parser("Sunday 00 1995") shouldBe LocalDateTime(1995, 1, 1, 0, 0)
        parser("Caturday 00 1995") shouldBe null
    }

    
    @Test
    fun parse_AWY_parses_numeric_weekday_week_number_monday_and_year() {
        val parser = parse("%w %W %Y")

        parser("1 01 1990") shouldBe LocalDateTime(1990, 1, 1, 0, 0)
        parser("0 04 1991") shouldBe LocalDateTime(1991, 2, 3, 0, 0)
        parser("0 00 1995") shouldBe LocalDateTime(1995, 1, 1, 0, 0)
        parser("X 03 2010") shouldBe null
    }

    
    @Test
    fun parse_mdy_parses_month_date_and_2_digits_year() {
        val parser = parse("%m/%d/%y")

        parser("02/03/69") shouldBe LocalDateTime(1969, 2, 3, 0, 0)
        parser("01/01/90") shouldBe LocalDateTime(1990, 1, 1, 0, 0)
        parser("02/03/91") shouldBe LocalDateTime(1991, 2, 3, 0, 0)
        parser("02/03/68") shouldBe LocalDateTime(2068, 2, 3, 0, 0)
        parser("03/10/2010") shouldBe null
    }

    
    @Test
    fun parse_x_parses_local_date() {
        val parser = parse("%x")

        parser("1/1/1990") shouldBe LocalDateTime(1990, 1, 1, 0, 0)
        parser("2/3/1991") shouldBe LocalDateTime(1991, 2, 3, 0, 0)
        parser("3/10/2010") shouldBe LocalDateTime(2010, 3, 10, 0, 0)
    }

    
    @Test
    fun parse_bdY_parses_abbreviated_month_date_and_year() {
        val parser = parse("%b %d, %Y")

        parser("jan 01, 1990") shouldBe LocalDateTime(1990, 1, 1, 0, 0)
        parser("feb  2, 2010") shouldBe LocalDateTime(2010, 2, 2, 0, 0)
        parser("jan. 1, 1990") shouldBe null
    }

    
    @Test
    fun parse_bdY_parses_month_date_and_year() {
        val parser = parse("%B %d, %Y")

        parser("January 01, 1990") shouldBe LocalDateTime(1990, 1, 1, 0, 0)
        parser("February  2, 2010") shouldBe LocalDateTime(2010, 2, 2, 0, 0)
        parser("jan 1, 1990") shouldBe null
    }

    
    @Test
    fun parse_jmdY_parses_day_of_year_and_date() {
        val parser = parse("%j %m/%d/%Y")

        parser("001 01/01/1990") shouldBe LocalDateTime(1990, 1, 1, 0, 0)
        parser("034 02/03/1991") shouldBe LocalDateTime(1991, 2, 3, 0, 0)
        parser("2012 03/10/2010") shouldBe null
    }

    
    @Test
    fun parse_c_parses_localdate_and_time() {
        val parser = parse("%c")

        parser("1/1/1990, 12:00:00 AM") shouldBe LocalDateTime(1990, 1, 1, 0, 0)
    }

    
    @Test
    fun parse_HMS_parses_24_hour_minute_second() {
        val parser = parse("%H:%M:%S")

        parser("00:00:00") shouldBe LocalDateTime(1900, 1, 1, 0, 0, 0)
        parser("11:59:59") shouldBe LocalDateTime(1900, 1, 1, 11, 59, 59)
        parser("12:00:00") shouldBe LocalDateTime(1900, 1, 1, 12, 0, 0)
        parser("12:00:01") shouldBe LocalDateTime(1900, 1, 1, 12, 0, 1)
        parser("23:59:59") shouldBe LocalDateTime(1900, 1, 1, 23, 59, 59)
    }

    
    @Test
    fun parse_X_parses_locale_date_time() {
        val parser = parse("%X")

        parser("12:00:00 AM") shouldBe LocalDateTime(1900, 1, 1, 0, 0, 0)
        parser("11:59:59 AM") shouldBe LocalDateTime(1900, 1, 1, 11, 59, 59)
        parser("12:00:00 PM") shouldBe LocalDateTime(1900, 1, 1, 12, 0, 0)
        parser("12:00:01 PM") shouldBe LocalDateTime(1900, 1, 1, 12, 0, 1)
        parser("11:59:59 PM") shouldBe LocalDateTime(1900, 1, 1, 23, 59, 59)
    }

    
    @Test
    fun parse_IMSp_parses_12_hour_minute_second() {
        val parser = parse("%I:%M:%S %p")

        parser("12:00:00 am") shouldBe LocalDateTime(1900, 1, 1, 0, 0, 0)
        parser("11:59:59 AM") shouldBe LocalDateTime(1900, 1, 1, 11, 59, 59)
        parser("12:00:00 pm") shouldBe LocalDateTime(1900, 1, 1, 12, 0, 0)
        parser("12:00:01 pm") shouldBe LocalDateTime(1900, 1, 1, 12, 0, 1)
        parser("11:59:59 PM") shouldBe LocalDateTime(1900, 1, 1, 23, 59, 59)
    }

    
    @Test
    fun parse_IMSp_parses_period_in_non_english_locale() {
        val parser = Locale(Locales.fi_FI()).parse("%I:%M:%S %p")

        parser("12:00:00 a.m.") shouldBe LocalDateTime(1900, 1, 1, 0, 0, 0)
        parser("11:59:59 A.M.") shouldBe LocalDateTime(1900, 1, 1, 11, 59, 59)
        parser("12:00:00 p.m.") shouldBe LocalDateTime(1900, 1, 1, 12, 0, 0)
        parser("12:00:01 p.m.") shouldBe LocalDateTime(1900, 1, 1, 12, 0, 1)
        parser("11:59:59 P.M.") shouldBe LocalDateTime(1900, 1, 1, 23, 59, 59)
    }

    
    @Test
    fun parse_percentSign_mdY_parses_literal_percent() {
        val parser = parse("%% %m/%d/%Y")

        parser("% 01/01/1990") shouldBe LocalDateTime(1990, 1, 1, 0, 0)
        parser("% 02/03/1991") shouldBe LocalDateTime(1991, 2, 3, 0, 0)
        parser("%% 03/10/2010") shouldBe null
    }

    
    @Test
    fun parse_minus_m_0d_underscoreY_ignores_optional_padding_modifier_skipping_zero_and_spaces() {
        val parser = parse("%-m/%0d/%_Y")

        parser("01/ 1/1990") shouldBe LocalDateTime(1990, 1, 1, 0, 0)
    }

    
    @Test
    fun parse_bdY_doent_crash_when_given_weird_strings() {
        val parser = parse("%b %d, %Y")

        parser("foo 1/1990") shouldBe null
    }
}

/**
tape("timeParse(\"%m/%d/%Y %Z\")(date) parses timezone offset", function(test) {
var p = timeFormat.timeParse("%m/%d/%Y %Z");
test.deepEqual(p("01/02/1990 +0000"), date.local(1990, 0, 1, 16));
test.deepEqual(p("01/02/1990 +0100"), date.local(1990, 0, 1, 15));
test.deepEqual(p("01/02/1990 +0130"), date.local(1990, 0, 1, 14, 30));
test.deepEqual(p("01/02/1990 -0100"), date.local(1990, 0, 1, 17));
test.deepEqual(p("01/02/1990 -0130"), date.local(1990, 0, 1, 17, 30));
test.deepEqual(p("01/02/1990 -0800"), date.local(1990, 0, 2, 0));
test.end();
});

tape("timeParse(\"%m/%d/%Y %Z\")(date) parses timezone offset in the form '+-hh:mm'", function(test) {
var p = timeFormat.timeParse("%m/%d/%Y %Z");
test.deepEqual(p("01/02/1990 +01:30"), date.local(1990, 0, 1, 14, 30));
test.deepEqual(p("01/02/1990 -01:30"), date.local(1990, 0, 1, 17, 30));
test.end();
});

tape("timeParse(\"%m/%d/%Y %Z\")(date) parses timezone offset in the form '+-hh'", function(test) {
var p = timeFormat.timeParse("%m/%d/%Y %Z");
test.deepEqual(p("01/02/1990 +01"), date.local(1990, 0, 1, 15));
test.deepEqual(p("01/02/1990 -01"), date.local(1990, 0, 1, 17));
test.end();
});

tape("timeParse(\"%m/%d/%Y %Z\")(date) parses timezone offset in the form 'Z'", function(test) {
var p = timeFormat.timeParse("%m/%d/%Y %Z");
test.deepEqual(p("01/02/1990 Z"), date.local(1990, 0, 1, 16));
test.end();
});
 */