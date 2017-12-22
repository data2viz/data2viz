package io.data2viz.scale

import io.data2viz.test.TestBase
import io.data2viz.time.Date
import io.data2viz.time.date
import kotlin.test.Test

class ScaleTimeTests : TestBase() {

    infix fun Date?.shouldBe(date: Date?) {
        if (this == null && date != null || this != null && date == null) throw AssertionError("$this did not equal $date")
        if (this == null && date == null) return
        if (!(date!!.year() == this!!.year()
                && date.month() == this.month()
                && date.dayOfMonth() == this.dayOfMonth()
                && date.hour() == this.hour()
                && date.minute() == this.minute()
                && date.second() == this.second()
                && date.millisecond() == this.millisecond()))
            throw AssertionError("$this did not equal $date")
    }

    @Test
    fun time_clamp_returns_limit_values() {
        val scale = scales.continuous.time()

        scale.domain = listOf(date(2009, 1, 1), date(2010, 1, 1))
        scale.range = listOf(.0, 100.0)

        scale.clamp shouldBe false
        scale(date(2015, 1, 1)) shouldBe 600.0
        scale(date(1998, 1, 1)) shouldBe -1100.0

        scale.clamp = true
        scale(date(2015, 1, 1)) shouldBe 100.0
        scale(date(9999, 1, 1)) shouldBe 100.0
        scale(date(1998, 1, 1)) shouldBe .0
        scale(date(2, 1, 1)) shouldBe .0
    }

    @Test
    fun time_clamp_invert_returns_limit_values() {
        val scale = scales.continuous.time()

        scale.domain = listOf(date(2009, 1, 1), date(2010, 1, 1))
        scale.range = listOf(.0, 100.0)

        scale.clamp shouldBe false
        scale.invert(200.0) shouldBe date(2011, 1, 1)
        scale.invert(600.0) shouldBe date(2014, 12, 31)     // 2012 is 366 days long
        scale.invert(-1100.0) shouldBe date(1998, 1, 4)     // 2000, 2004, 2008 are 364 days long

        scale.clamp = true
        scale.invert(.0) shouldBe date(2009, 1, 1)
        scale.invert(100.0) shouldBe date(2010, 1, 1)
        scale.invert(-200.0) shouldBe date(2009, 1, 1)
        scale.invert(1100.0) shouldBe date(2010, 1, 1)
    }

    @Test
    fun time_nice_can_nice_multi_year_domains_LEGACY() {
        val scale = scales.continuous.time()
        scale.range = listOf(.0, 1.0)

        scale.domain = listOf(date(2011, 3, 1), date(2020, 10, 1))
        scale.nice()
        scale.domain.first() shouldBe date(2011)
        scale.domain.last() shouldBe date(2021)

        scale.domain = listOf(date(2001, 1, 1), date(2138, 1, 1))
        scale.nice()

        scale.domain.first() shouldBe date(2000)
        scale.domain.last() shouldBe date(2140)
    }

    @Test
    fun time_nice_is_an_alias_for_nice_10_LEGACY() {
        val scale = scales.continuous.time()
        scale.range = listOf(.0, 1.0)

        scale.domain = listOf(date(2009, 1, 1, 0, 17), date(2009, 1, 1, 23, 42))
        scale.nice()
        scale.domain.first() shouldBe date(2009)
        scale.domain.last() shouldBe date(2009, 1, 2)
    }

    @Test
    fun time_nice_can_nice_subsecond_domains_LEGACY() {
        val scale = scales.continuous.time()
        scale.range = listOf(.0, 1.0)

        scale.domain = listOf(date(2013, 5, 6, 12, 44, 20, 0), date(2013, 5, 6, 12, 44, 20, 128))
        scale.nice()
        scale.domain.first() shouldBe date(2013, 5, 6, 12, 44, 20, 0)
        scale.domain.last() shouldBe date(2013, 5, 6, 12, 44, 20, 130)
    }

    @Test
    fun time_nice_can_nice_empty_domains_LEGACY() {
        val scale = scales.continuous.time()
        scale.range = listOf(.0, 1.0)

        scale.domain = listOf(date(2013, 5, 6, 12, 44), date(2013, 5, 6, 12, 44))
        scale.nice()
        scale.domain.first() shouldBe date(2013, 5, 6, 12, 44)
        scale.domain.last() shouldBe date(2013, 5, 6, 12, 44)
    }

    @Test
    fun time_nice_count_use_the_specified_tick_count_LEGACY() {
        val scale = scales.continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(date(2009, 1, 1, 0, 17), date(2009, 1, 1, 23, 42))

        scale.nice(100)
        scale.domain.first() shouldBe date(2009, 1, 1, 0, 15)
        scale.domain.last() shouldBe date(2009, 1, 1, 23, 45)

        scale.nice(10)
        scale.domain.first() shouldBe date(2009, 1, 1)
        scale.domain.last() shouldBe date(2009, 1, 2)
    }

    @Test
    fun time_ticks_count_can_generate_subsecond_ticks_LEGACY() {
        val scale = scales.continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(date(2011, 1, 1, 12, 0, 0), date(2011, 1, 1, 12, 0, 1))

        val tickList = listOf(
                date(2011, 1, 1, 12, 0, 0, 0),
                date(2011, 1, 1, 12, 0, 0, 200),
                date(2011, 1, 1, 12, 0, 0, 400),
                date(2011, 1, 1, 12, 0, 0, 600),
                date(2011, 1, 1, 12, 0, 0, 800),
                date(2011, 1, 1, 12, 0, 1, 0)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_1_second_ticks_LEGACY() {
        val scale = scales.continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(date(2011, 1, 1, 12, 0, 0), date(2011, 1, 1, 12, 0, 4))

        val tickList = listOf(
                date(2011, 1, 1, 12, 0, 0),
                date(2011, 1, 1, 12, 0, 1),
                date(2011, 1, 1, 12, 0, 2),
                date(2011, 1, 1, 12, 0, 3),
                date(2011, 1, 1, 12, 0, 4)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_5_seconds_ticks_LEGACY() {
        val scale = scales.continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(date(2011, 1, 1, 12, 0, 0), date(2011, 1, 1, 12, 0, 20))

        val tickList = listOf(
                date(2011, 1, 1, 12, 0, 0),
                date(2011, 1, 1, 12, 0, 5),
                date(2011, 1, 1, 12, 0, 10),
                date(2011, 1, 1, 12, 0, 15),
                date(2011, 1, 1, 12, 0, 20)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_15_seconds_ticks_LEGACY() {
        val scale = scales.continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(date(2011, 1, 1, 12, 0, 0), date(2011, 1, 1, 12, 0, 50))

        val tickList = listOf(
                date(2011, 1, 1, 12, 0, 0),
                date(2011, 1, 1, 12, 0, 15),
                date(2011, 1, 1, 12, 0, 30),
                date(2011, 1, 1, 12, 0, 45)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_30_seconds_ticks_LEGACY() {
        val scale = scales.continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(date(2011, 1, 1, 12, 0, 0), date(2011, 1, 1, 12, 1, 50))

        val tickList = listOf(
                date(2011, 1, 1, 12, 0, 0),
                date(2011, 1, 1, 12, 0, 30),
                date(2011, 1, 1, 12, 1, 0),
                date(2011, 1, 1, 12, 1, 30)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_1_minute_ticks_LEGACY() {
        val scale = scales.continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(date(2011, 1, 1, 12, 0, 27), date(2011, 1, 1, 12, 4, 12))

        val tickList = listOf(
                date(2011, 1, 1, 12, 1),
                date(2011, 1, 1, 12, 2),
                date(2011, 1, 1, 12, 3),
                date(2011, 1, 1, 12, 4)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_5_minutes_ticks_LEGACY() {
        val scale = scales.continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(date(2011, 1, 1, 12, 3, 27), date(2011, 1, 1, 12, 21, 12))

        val tickList = listOf(
                date(2011, 1, 1, 12, 5),
                date(2011, 1, 1, 12, 10),
                date(2011, 1, 1, 12, 15),
                date(2011, 1, 1, 12, 20)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_15_minutes_ticks_LEGACY() {
        val scale = scales.continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(date(2011, 1, 1, 12, 8, 27), date(2011, 1, 1, 13, 4, 12))

        val tickList = listOf(
                date(2011, 1, 1, 12, 15),
                date(2011, 1, 1, 12, 30),
                date(2011, 1, 1, 12, 45),
                date(2011, 1, 1, 13, 0)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_30_minutes_ticks_LEGACY() {
        val scale = scales.continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(date(2011, 1, 1, 12, 28, 27), date(2011, 1, 1, 14, 4, 12))

        val tickList = listOf(
                date(2011, 1, 1, 12, 30),
                date(2011, 1, 1, 13, 0),
                date(2011, 1, 1, 13, 30),
                date(2011, 1, 1, 14, 0)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_1_hour_ticks_LEGACY() {
        val scale = scales.continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(date(2011, 1, 1, 12, 28, 27), date(2011, 1, 1, 16, 34, 12))

        val tickList = listOf(
                date(2011, 1, 1, 13),
                date(2011, 1, 1, 14),
                date(2011, 1, 1, 15),
                date(2011, 1, 1, 16)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_3_hours_ticks_LEGACY() {
        val scale = scales.continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(
                date(2011, 1, 1, 14, 28, 27),
                date(2011, 1, 2, 1, 34, 12))

        val tickList = listOf(
                date(2011, 1, 1, 15),
                date(2011, 1, 1, 18),
                date(2011, 1, 1, 21),
                date(2011, 1, 2, 0)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_6_hours_ticks_LEGACY() {
        val scale = scales.continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(date(2011, 1, 1, 16, 28, 27), date(2011, 1, 2, 14, 34, 12))

        val tickList = listOf(
                date(2011, 1, 1, 18),
                date(2011, 1, 2, 0),
                date(2011, 1, 2, 6),
                date(2011, 1, 2, 12)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_12_hours_ticks_LEGACY() {
        val scale = scales.continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(date(2011, 1, 1, 16, 28, 27), date(2011, 1, 3, 21, 34, 12))

        val tickList = listOf(
                date(2011, 1, 2, 0),
                date(2011, 1, 2, 12),
                date(2011, 1, 3, 0),
                date(2011, 1, 3, 12)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_1_day_ticks_LEGACY() {
        val scale = scales.continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(date(2011, 1, 1, 16, 28, 27), date(2011, 1, 5, 21, 34, 12))

        val tickList = listOf(
                date(2011, 1, 2),
                date(2011, 1, 3),
                date(2011, 1, 4),
                date(2011, 1, 5)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_2_days_ticks_LEGACY() {
        val scale = scales.continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(date(2011, 1, 2, 16, 28, 27), date(2011, 1, 9, 21, 34, 12))

        val tickList = listOf(
                date(2011, 1, 3),
                date(2011, 1, 5),
                date(2011, 1, 7),
                date(2011, 1, 9)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    // TODO : actually test don't pass due to a bug in timeWeek (timeSunday used here)
    // check timeSunday as it seems to returns mondays !! :D
    /*@Test
    fun time_ticks_count_can_generate_1_week_ticks_LEGACY() {
        val scale = scales.continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(date(2011, 1, 1, 16, 28, 27), date(2011, 1, 23, 21, 34, 12))

        val tickList = listOf(
                date(2011, 1, 2),
                date(2011, 1, 9),
                date(2011, 1, 16),
                date(2011, 1, 23)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }*/

    /*@Test
    fun time_ticks_count_can_generate_1_month_ticks_LEGACY() {
        val scale = scales.continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(date(2011, 1, 18), date(2011, 5, 2))

        val tickList = listOf(
                date(2011, 2),
                date(2011, 3),
                date(2011, 4),
                date(2011, 5)
        )

        val ticks = scale.ticks(4)
        println(ticks)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }*/

    /*@Test
    fun time_ticks_count_can_generate_3_months_ticks_LEGACY() {
        val scale = scales.continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(date(2010, 11, 18), date(2011, 10, 2))

        val tickList = listOf(
                date(2011, 1),
                date(2011, 4),
                date(2011, 7),
                date(2011, 10)
        )

        val ticks = scale.ticks(4)
        println(ticks)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }*/

    @Test
    fun time_ticks_count_can_generate_1_year_ticks_LEGACY() {
        val scale = scales.continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(date(2010, 12, 18), date(2014, 3, 2))

        var tickList = listOf(
                date(2011),
                date(2012),
                date(2013),
                date(2014)
        )

        var ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }

        scale.domain = listOf(date(2010, 12, 18), date(2022, 3, 2))

        tickList = listOf(
                date(2012),
                date(2014),
                date(2016),
                date(2018),
                date(2020),
                date(2022)
        )

        ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_multi_years_ticks_LEGACY() {
        val scale = scales.continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(date(0, 12, 18), date(2014, 3, 2))

        val tickList = listOf(
                date(500),
                date(1000),
                date(1500),
                date(2000)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_returns_no_ticks_for_empty_domain_LEGACY() {
        val scale = scales.continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(date(2014, 3, 2), date(2014, 3, 2))

        val ticks = scale.ticks(6)
        ticks.size shouldBe 0
    }



    /*@Test
    fun time_ticks_count_returns_descending_ticks_for_descending_domain_LEGACY() {
        val scale = scales.continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(date(2014, 3, 2), date(2010, 12, 18))

        var tickList = listOf(
                date(2014),
                date(2013),
                date(2012),
                date(2011)
        )

        var ticks = scale.ticks(4)
        println(ticks)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }

        scale.domain = listOf(date(2011, 11, 2), date(2010, 12, 18))
        tickList = listOf(
                date(2011, 10),
                date(2011, 7),
                date(2011, 4),
                date(2011, 1)
        )

        ticks = scale.ticks(4)
        println(ticks)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }*/


    /**
    tape("time.domain([-1e50, 1e50]) is equivalent to time.domain([NaN, NaN])", function(test) {
    var x = scale.scaleTime().domain([-1e50, 1e50]);
    test.equal(isNaN(x.domain()[0]), true); // Note: also coerced on retrieval, so insufficient test!
    test.equal(isNaN(x.domain()[1]), true);
    test.deepEqual(x.ticks(10), []);
    test.end();
    });

    tape("time.copy() isolates changes to the domain", function(test) {
    var x = scale.scaleTime().domain([date.local(2009, 0, 1), date.local(2010, 0, 1)]), y = x.copy();
    x.domain([date.local(2010, 0, 1), date.local(2011, 0, 1)]);
    test.deepEqual(y.domain(), [date.local(2009, 0, 1), date.local(2010, 0, 1)]);
    test.equal(x(date.local(2010, 0, 1)), 0);
    test.equal(y(date.local(2010, 0, 1)), 1);
    y.domain([date.local(2011, 0, 1), date.local(2012, 0, 1)]);
    test.equal(x(date.local(2011, 0, 1)), 1);
    test.equal(y(date.local(2011, 0, 1)), 0);
    test.deepEqual(x.domain(), [date.local(2010, 0, 1), date.local(2011, 0, 1)]);
    test.deepEqual(y.domain(), [date.local(2011, 0, 1), date.local(2012, 0, 1)]);
    test.end();
    });

    tape("time.copy() isolates changes to the range", function(test) {
    var x = scale.scaleTime().domain([date.local(2009, 0, 1), date.local(2010, 0, 1)]), y = x.copy();
    x.range([1, 2]);
    test.deepEqual(x.invert(1), date.local(2009, 0, 1));
    test.deepEqual(y.invert(1), date.local(2010, 0, 1));
    test.deepEqual(y.range(), [0, 1]);
    y.range([2, 3]);
    test.deepEqual(x.invert(2), date.local(2010, 0, 1));
    test.deepEqual(y.invert(2), date.local(2009, 0, 1));
    test.deepEqual(x.range(), [1, 2]);
    test.deepEqual(y.range(), [2, 3]);
    test.end();
    });

    tape("time.copy() isolates changes to the interpolator", function(test) {
    var x = scale.scaleTime().domain([date.local(2009, 0, 1), date.local(2010, 0, 1)]).range(["red", "blue"]),
    i = x.interpolate(),
    y = x.copy();
    x.interpolate(interpolate.interpolateHsl);
    test.equal(x(date.local(2009, 6, 1)), "rgb(255, 0, 253)");
    test.equal(y(date.local(2009, 6, 1)), "rgb(129, 0, 126)");
    test.equal(y.interpolate(), i);
    test.end();
    });

    tape("time.copy() isolates changes to clamping", function(test) {
    var x = scale.scaleTime().domain([date.local(2009, 0, 1), date.local(2010, 0, 1)]).clamp(true), y = x.copy();
    x.clamp(false);
    test.equal(x(date.local(2011, 0, 1)), 2);
    test.equal(y(date.local(2011, 0, 1)), 1);
    test.equal(y.clamp(), true);
    y.clamp(false);
    test.equal(x(date.local(2011, 0, 1)), 2);
    test.equal(y(date.local(2011, 0, 1)), 2);
    test.equal(x.clamp(), false);
    test.end();
    });

    tape("time.nice(interval) nices using the specified time interval", function(test) {
    var x = scale.scaleTime().domain([date.local(2009, 0, 1, 0, 12), date.local(2009, 0, 1, 23, 48)]);
    test.deepEqual(x.nice(time.timeDay).domain(), [date.local(2009, 0, 1), date.local(2009, 0, 2)]);
    test.deepEqual(x.nice(time.timeWeek).domain(), [date.local(2008, 11, 28), date.local(2009, 0, 4)]);
    test.deepEqual(x.nice(time.timeMonth).domain(), [date.local(2008, 11, 1), date.local(2009, 1, 1)]);
    test.deepEqual(x.nice(time.timeYear).domain(), [date.local(2008, 0, 1), date.local(2010, 0, 1)]);
    test.end();
    });

    tape("time.nice(interval) can nice empty domains", function(test) {
    var x = scale.scaleTime().domain([date.local(2009, 0, 1, 0, 12), date.local(2009, 0, 1, 0, 12)]);
    test.deepEqual(x.nice(time.timeDay).domain(), [date.local(2009, 0, 1), date.local(2009, 0, 2)]);
    test.end();
    });

    tape("time.nice(interval) can nice a polylinear domain, only affecting its extent", function(test) {
    var x = scale.scaleTime().domain([date.local(2009, 0, 1, 0, 12), date.local(2009, 0, 1, 23, 48), date.local(2009, 0, 2, 23, 48)]).nice(time.timeDay);
    test.deepEqual(x.domain(), [date.local(2009, 0, 1), date.local(2009, 0, 1, 23, 48), date.local(2009, 0, 3)]);
    test.end();
    });

    tape("time.nice(interval, step) nices using the specified time interval and step", function(test) {
    var x = scale.scaleTime().domain([date.local(2009, 0, 1, 0, 12), date.local(2009, 0, 1, 23, 48)]);
    test.deepEqual(x.nice(time.timeDay, 3).domain(), [date.local(2009, 0, 1), date.local(2009, 0, 4)]);
    test.deepEqual(x.nice(time.timeWeek, 2).domain(), [date.local(2008, 11, 21), date.local(2009, 0, 4)]);
    test.deepEqual(x.nice(time.timeMonth, 3).domain(), [date.local(2008, 9, 1), date.local(2009, 3, 1)]);
    test.deepEqual(x.nice(time.timeYear, 10).domain(), [date.local(2000, 0, 1), date.local(2010, 0, 1)]);
    test.end();
    });


    tape("time.ticks(interval) observes the specified tick interval", function(test) {
    var x = scale.scaleTime().domain([date.local(2011, 0, 1, 12, 1, 0), date.local(2011, 0, 1, 12, 4, 4)]);
    test.deepEqual(x.ticks(time.timeMinute), [
    date.local(2011, 0, 1, 12, 1),
    date.local(2011, 0, 1, 12, 2),
    date.local(2011, 0, 1, 12, 3),
    date.local(2011, 0, 1, 12, 4)
    ]);
    test.end();
    });

    tape("time.ticks(interval, step) observes the specified tick interval and step", function(test) {
    var x = scale.scaleTime().domain([date.local(2011, 0, 1, 12, 0, 0), date.local(2011, 0, 1, 12, 33, 4)]);
    test.deepEqual(x.ticks(time.timeMinute, 10), [
    date.local(2011, 0, 1, 12, 0),
    date.local(2011, 0, 1, 12, 10),
    date.local(2011, 0, 1, 12, 20),
    date.local(2011, 0, 1, 12, 30)
    ]);
    test.end();
    });

    tape("time.tickFormat()(date) formats year on New Year's", function(test) {
    var f = scale.scaleTime().tickFormat();
    test.equal(f(date.local(2011, 0, 1)), "2011");
    test.equal(f(date.local(2012, 0, 1)), "2012");
    test.equal(f(date.local(2013, 0, 1)), "2013");
    test.end();
    });

    tape("time.tickFormat()(date) formats month on the 1st of each month", function(test) {
    var f = scale.scaleTime().tickFormat();
    test.equal(f(date.local(2011, 1, 1)), "February");
    test.equal(f(date.local(2011, 2, 1)), "March");
    test.equal(f(date.local(2011, 3, 1)), "April");
    test.end();
    });

    tape("time.tickFormat()(date) formats week on Sunday midnight", function(test) {
    var f = scale.scaleTime().tickFormat();
    test.equal(f(date.local(2011, 1, 6)), "Feb 06");
    test.equal(f(date.local(2011, 1, 13)), "Feb 13");
    test.equal(f(date.local(2011, 1, 20)), "Feb 20");
    test.end();
    });

    tape("time.tickFormat()(date) formats date on midnight", function(test) {
    var f = scale.scaleTime().tickFormat();
    test.equal(f(date.local(2011, 1, 2)), "Wed 02");
    test.equal(f(date.local(2011, 1, 3)), "Thu 03");
    test.equal(f(date.local(2011, 1, 4)), "Fri 04");
    test.end();
    });

    tape("time.tickFormat()(date) formats hour on minute zero", function(test) {
    var f = scale.scaleTime().tickFormat();
    test.equal(f(date.local(2011, 1, 2, 11)), "11 AM");
    test.equal(f(date.local(2011, 1, 2, 12)), "12 PM");
    test.equal(f(date.local(2011, 1, 2, 13)), "01 PM");
    test.end();
    });

    tape("time.tickFormat()(date) formats minute on second zero", function(test) {
    var f = scale.scaleTime().tickFormat();
    test.equal(f(date.local(2011, 1, 2, 11, 59)), "11:59");
    test.equal(f(date.local(2011, 1, 2, 12,  1)), "12:01");
    test.equal(f(date.local(2011, 1, 2, 12,  2)), "12:02");
    test.end();
    });

    tape("time.tickFormat()(date) otherwise, formats second", function(test) {
    var f = scale.scaleTime().tickFormat();
    test.equal(f(date.local(2011, 1, 2, 12,  1,  9)), ":09");
    test.equal(f(date.local(2011, 1, 2, 12,  1, 10)), ":10");
    test.equal(f(date.local(2011, 1, 2, 12,  1, 11)), ":11");
    test.end();
    });

    tape("time.tickFormat(count, specifier) returns a time format for the specified specifier", function(test) {
    var f = scale.scaleTime().tickFormat(10, "%c");
    test.equal(f(date.local(2011, 1, 2, 12)), "2/2/2011, 12:00:00 PM");
    test.end();
    });

     */
}