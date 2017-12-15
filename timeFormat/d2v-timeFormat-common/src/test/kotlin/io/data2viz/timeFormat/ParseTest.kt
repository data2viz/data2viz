package io.data2viz.timeFormat

import io.data2viz.time.date
import kotlin.test.Test

class ParseTest : TestDate() {

    @Test
    fun parse_string() {
        val parser = parse("%c")
        
        parser("1/1/1990, 12:00:00 AM") shouldBe date(1990, 1, 1)
        parser("1/2/1990, 12:00:00 AM") shouldBe date(1990, 1, 2)
        parser("1/3/1990, 12:00:00 AM") shouldBe date(1990, 1, 3)
        parser("1/4/1990, 12:00:00 AM") shouldBe date(1990, 1, 4)
        parser("1/5/1990, 12:00:00 AM") shouldBe date(1990, 1, 5)
    }

    @Test
    fun parse_amdY_parses_abbreviated_weekday_and_date_LEGACY() {
        val parser = parse("%a %m/%d/%Y")

        parser("Sun 01/01/1990") shouldBe date(1990,1,1)
        parser("Wed 02/03/1991") shouldBe date(1991,2,3)
        parser("XXX 03/10/2010") shouldBe null
    }

    @Test
    fun parse_AmdY_parses_weekday_and_date_LEGACY() {
        val parser = parse("%A %m/%d/%Y")

        // note : ignores the day name
        parser("Sunday 01/01/1990") shouldBe date(1990,1,1)
        parser("Monday 01/01/1990") shouldBe date(1990,1,1)
        parser("Tuesday 01/01/1990") shouldBe date(1990,1,1)
        parser("Wednesday 01/01/1990") shouldBe date(1990,1,1)

        parser("Wednesday 02/03/1991") shouldBe date(1991,2,3)
        parser("Caturday 03/10/2010") shouldBe null
    }

    @Test
    fun parse_UY_parses_week_number_sunday_and_year_LEGACY() {
        val parser = parse("%U %Y")

        parser("00 1990") shouldBe date(1989,12,31)
        parser("05 1991") shouldBe date(1991,2,3)
        parser("01 1995") shouldBe date(1995,1,1)
    }

    @Test
    fun parse_aUY_parses_abbreviated_weekday_week_number_sunday_and_year_LEGACY() {
        val parser = parse("%a %U %Y")

        parser("Mon 00 1990") shouldBe date(1990,1,1)
        parser("Sun 05 1991") shouldBe date(1991,2,3)
        parser("Sun 01 1995") shouldBe date(1995,1,1)
        parser("XXX 01 1995") shouldBe null
    }

    @Test
    fun parse_AUY_parses_weekday_week_number_sunday_and_year_LEGACY() {
        val parser = parse("%A %U %Y")

        parser("Monday 00 1990") shouldBe date(1990,1,1)
        parser("Sunday 05 1991") shouldBe date(1991,2,3)
        parser("Sunday 01 1995") shouldBe date(1995,1,1)
        parser("Plop 01 1995") shouldBe null
    }

    @Test
    fun parse_wUY_parses_numeric_weekday_week_number_sunday_and_year_LEGACY() {
        val parser = parse("%w %U %Y")

        parser("1 00 1990") shouldBe date(1990,1,1)
        parser("0 05 1991") shouldBe date(1991,2,3)
        parser("0 01 1995") shouldBe date(1995,1,1)
        parser("X 01 1995") shouldBe null
    }

    @Test
    fun parse_WY_parses_week_number_monday_and_year_LEGACY() {
        val parser = parse("%W %Y")

        parser("01 1990") shouldBe date(1990,1,1)
        parser("04 1991") shouldBe date(1991,1,28)
        parser("00 1995") shouldBe date(1994,12,26)
    }

    @Test
    fun parse_aWY_parses_abbreviated_weekday_week_number_monday_and_year_LEGACY() {
        val parser = parse("%a %W %Y")

        parser("Mon 01 1990") shouldBe date(1990,1,1)
        parser("Sun 04 1991") shouldBe date(1991,2,3)
        parser("Sun 00 1995") shouldBe date(1995,1,1)
        parser("XXX 00 1995") shouldBe null
    }

    @Test
    fun parse_AWY_parses_weekday_week_number_monday_and_year_LEGACY() {
        val parser = parse("%A %W %Y")

        parser("Monday 01 1990") shouldBe date(1990,1,1)
        parser("Sunday 04 1991") shouldBe date(1991,2,3)
        parser("Sunday 00 1995") shouldBe date(1995,1,1)
        parser("Caturday 00 1995") shouldBe null
    }

    @Test
    fun parse_AWY_parses_numeric_weekday_week_number_monday_and_year_LEGACY() {
        val parser = parse("%w %W %Y")

        parser("1 01 1990") shouldBe date(1990,1,1)
        parser("0 04 1991") shouldBe date(1991,2,3)
        parser("0 00 1995") shouldBe date(1995,1,1)
        parser("X 03 2010") shouldBe null
    }

    @Test
    fun parse_mdy_parses_month_date_and_2_digits_year_LEGACY() {
        val parser = parse("%m/%d/%y")

        parser("02/03/69") shouldBe date(1969,2,3)
        parser("01/01/90") shouldBe date(1990,1,1)
        parser("02/03/91") shouldBe date(1991,2,3)
        parser("02/03/68") shouldBe date(2068,2,3)
        parser("03/10/2010") shouldBe null
    }

    @Test
    fun parse_x_parses_local_date_LEGACY() {
        val parser = parse("%x")

        parser("1/1/1990") shouldBe date(1990,1,1)
        parser("2/3/1991") shouldBe date(1991,2,3)
        parser("3/10/2010") shouldBe date(2010,3,10)
    }

    @Test
    fun parse_bdY_parses_abbreviated_month_date_and_year_LEGACY() {
        val parser = parse("%b %d, %Y")

        parser("jan 01, 1990") shouldBe date(1990,1,1)
        parser("feb  2, 2010") shouldBe date(2010,2,2)
        parser("jan. 1, 1990") shouldBe null
    }

    @Test
    fun parse_bdY_parses_month_date_and_year_LEGACY() {
        val parser = parse("%B %d, %Y")

        parser("January 01, 1990") shouldBe date(1990,1,1)
        parser("February  2, 2010") shouldBe date(2010,2,2)
        parser("jan 1, 1990") shouldBe null
    }

    @Test
    fun parse_jmdY_parses_day_of_year_and_date_LEGACY() {
        val parser = parse("%j %m/%d/%Y")

        parser("001 01/01/1990") shouldBe date(1990,1,1)
        parser("034 02/03/1991") shouldBe date(1991,2,3)
        parser("2012 03/10/2010") shouldBe null
    }

    @Test
    fun parse_c_parses_localdate_and_time_LEGACY() {
        val parser = parse("%c")

        parser("1/1/1990, 12:00:00 AM") shouldBe date(1990,1,1)
    }

    @Test
    fun parse_HMS_parses_24_hour_minute_second_LEGACY() {
        val parser = parse("%H:%M:%S")

        parser("00:00:00") shouldBe date(1900,1,1, 0, 0, 0)
        parser("11:59:59") shouldBe date(1900,1,1, 11, 59, 59)
        parser("12:00:00") shouldBe date(1900,1,1, 12, 0, 0)
        parser("12:00:01") shouldBe date(1900,1,1, 12, 0, 1)
        parser("23:59:59") shouldBe date(1900,1,1, 23, 59, 59)
    }

    @Test
    fun parse_X_parses_locale_date_time_LEGACY() {
        val parser = parse("%X")

        parser("12:00:00 AM") shouldBe date(1900,1,1, 0, 0, 0)
        parser("11:59:59 AM") shouldBe date(1900,1,1, 11, 59, 59)
        parser("12:00:00 PM") shouldBe date(1900,1,1, 12, 0, 0)
        parser("12:00:01 PM") shouldBe date(1900,1,1, 12, 0, 1)
        parser("11:59:59 PM") shouldBe date(1900,1,1, 23, 59, 59)
    }

    @Test
    fun parse_IMSp_parses_12_hour_minute_second_LEGACY() {
        val parser = parse("%I:%M:%S %p")

        parser("12:00:00 am") shouldBe date(1900,1,1, 0, 0, 0)
        parser("11:59:59 AM") shouldBe date(1900,1,1, 11, 59, 59)
        parser("12:00:00 pm") shouldBe date(1900,1,1, 12, 0, 0)
        parser("12:00:01 pm") shouldBe date(1900,1,1, 12, 0, 1)
        parser("11:59:59 PM") shouldBe date(1900,1,1, 23, 59, 59)
    }

    @Test
    fun parse_IMSp_parses_period_in_non_english_locale_LEGACY() {
        val parser = Locale(Locales.fi_FI()).parse("%I:%M:%S %p")

        parser("12:00:00 a.m.") shouldBe date(1900,1,1, 0, 0, 0)
        parser("11:59:59 A.M.") shouldBe date(1900,1,1, 11, 59, 59)
        parser("12:00:00 p.m.") shouldBe date(1900,1,1, 12, 0, 0)
        parser("12:00:01 p.m.") shouldBe date(1900,1,1, 12, 0, 1)
        parser("11:59:59 P.M.") shouldBe date(1900,1,1, 23, 59, 59)
    }

    @Test
    fun parse_percentSign_mdY_parses_literal_percent_LEGACY() {
        val parser = parse("%% %m/%d/%Y")

        parser("% 01/01/1990") shouldBe date(1990,1,1)
        parser("% 02/03/1991") shouldBe date(1991,2,3)
        parser("%% 03/10/2010") shouldBe null
    }

    @Test
    fun parse_minus_m_0d_underscoreY_ignores_optional_padding_modifier_skipping_zero_and_spaces_LEGACY() {
        val parser = parse("%-m/%0d/%_Y")

        parser("01/ 1/1990") shouldBe date(1990,1,1)
    }

    @Test
    fun parse_bdY_doent_crash_when_given_weird_strings_LEGACY() {
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