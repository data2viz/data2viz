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

package io.data2viz.scale

import io.data2viz.test.TestBase
import kotlinx.datetime.LocalDateTime
import kotlin.math.roundToInt
import kotlin.test.Test

class ScaleTimeTests : TestBase() {

    infix fun LocalDateTime?.shouldBe(date: LocalDateTime?) {
        if (this == null && date != null || this != null && date == null) throw AssertionError("$this did not equal $date")
        if (this == null && date == null) return
        if (!(date!!.year == this!!.year
                && date.month == this.month
                && date.dayOfMonth == this.dayOfMonth
                && date.hour == this.hour
                && date.minute == this.minute
                && date.second == this.second
                && date.nanosecond == this.nanosecond))
            throw AssertionError("$this did not equal $date")
    }

    @Test
    fun time_scale_returns_limit_values() {
        val scale = Scales.Continuous.time()

        scale.domain = listOf(LocalDateTime(2000, 1, 1, 0, 0), LocalDateTime(2010, 1, 1, 0, 0))
        scale.range = listOf(.0, 100.0)

        scale.clamp shouldBe false

        scale(LocalDateTime(2001, 1, 1, 0, 0)).roundToInt() shouldBe 10
        scale(LocalDateTime(2002, 1, 1, 0, 0)).roundToInt() shouldBe 20
        scale(LocalDateTime(2003, 1, 1, 0, 0)).roundToInt() shouldBe 30
        scale(LocalDateTime(2004, 1, 1, 0, 0)).roundToInt() shouldBe 40
        scale(LocalDateTime(2005, 1, 1, 0, 0)).roundToInt() shouldBe 50
        scale(LocalDateTime(2006, 1, 1, 0, 0)).roundToInt() shouldBe 60
        scale(LocalDateTime(2007, 1, 1, 0, 0)).roundToInt() shouldBe 70
        scale(LocalDateTime(2008, 1, 1, 0, 0)).roundToInt() shouldBe 80
        scale(LocalDateTime(2009, 1, 1, 0, 0)).roundToInt() shouldBe 90
    }

    @Test
    fun time_clamp_returns_limit_values() {
        val scale = Scales.Continuous.time()

        scale.domain = listOf(LocalDateTime(2009, 1, 1, 0, 0), LocalDateTime(2010, 1, 1, 0, 0))
        scale.range = listOf(.0, 100.0)

        scale.clamp shouldBe false
        scale(LocalDateTime(2015, 1, 1, 0, 0)).roundToInt() shouldBe 600
        scale(LocalDateTime(1998, 1, 1, 0, 0)).roundToInt() shouldBe -1101

        scale.clamp = true
        scale(LocalDateTime(2015, 1, 1, 0, 0)).roundToInt() shouldBe 100
        scale(LocalDateTime(9999, 1, 1, 0, 0)).roundToInt() shouldBe 100
        scale(LocalDateTime(1998, 1, 1, 0, 0)).roundToInt() shouldBe 0
        scale(LocalDateTime(2, 1, 1, 0, 0)).roundToInt() shouldBe 0
    }

    @Test
    fun time_clamp_invert_returns_limit_values() {
        val scale = Scales.Continuous.time()

        scale.domain = listOf(LocalDateTime(2009, 1, 1, 0, 0), LocalDateTime(2010, 1, 1, 0, 0))
        scale.range = listOf(.0, 100.0)

        scale.clamp shouldBe false
        scale.invert(200.0) shouldBe LocalDateTime(2011, 1, 1, 0, 0)
        scale.invert(600.0) shouldBe LocalDateTime(2014, 12, 31, 0, 0)     // 2012 is 366 days long
        scale.invert(-1100.0) shouldBe LocalDateTime(1998, 1, 4, 0, 0)     // 2000, 2004, 2008 are 364 days long

        scale.clamp = true
        scale.invert(.0) shouldBe LocalDateTime(2009, 1, 1, 0, 0)
        scale.invert(100.0) shouldBe LocalDateTime(2010, 1, 1, 0, 0)
        scale.invert(-200.0) shouldBe LocalDateTime(2009, 1, 1, 0, 0)
        scale.invert(1100.0) shouldBe LocalDateTime(2010, 1, 1, 0, 0)
    }

    @Test
    fun time_nice_can_nice_multi_year_domains() {
        val scale = Scales.Continuous.time()
        scale.range = listOf(.0, 1.0)

        scale.domain = listOf(LocalDateTime(2011, 3, 1, 0, 0), LocalDateTime(2020, 10, 1, 0, 0))
        scale.nice()
        scale.domain.first() shouldBe LocalDateTime(2011, 1, 1, 0, 0)
        scale.domain.last() shouldBe LocalDateTime(2021, 1, 1, 0, 0)

        scale.domain = listOf(LocalDateTime(2001, 1, 1, 0, 0), LocalDateTime(2138, 1, 1, 0, 0))
        scale.nice()

        scale.domain.first() shouldBe LocalDateTime(2000, 1, 1, 0, 0)
        scale.domain.last() shouldBe LocalDateTime(2140, 1, 1, 0, 0)
    }

    @Test
    fun time_nice_is_an_alias_for_nice_10() {
        val scale = Scales.Continuous.time()
        scale.range = listOf(.0, 1.0)

        scale.domain = listOf(LocalDateTime(2009, 1, 1, 0, 17), LocalDateTime(2009, 1, 1, 23, 42))
        scale.nice()
        scale.domain.first() shouldBe LocalDateTime(2009, 1, 1, 0, 0)
        scale.domain.last() shouldBe LocalDateTime(2009, 1, 2, 0, 0)
    }

    @Test
    fun time_nice_can_nice_subsecond_domains() {
        val scale = Scales.Continuous.time()
        scale.range = listOf(.0, 1.0)

        scale.domain = listOf(LocalDateTime(2013, 5, 6, 12, 44, 20, 0), LocalDateTime(2013, 5, 6, 12, 44, 20, 128000000))
        scale.nice()
        scale.domain.first() shouldBe LocalDateTime(2013, 5, 6, 12, 44, 20, 0)
        scale.domain.last() shouldBe LocalDateTime(2013, 5, 6, 12, 44, 20, 130000000)
    }

    @Test
    fun time_nice_can_nice_empty_domains() {
        val scale = Scales.Continuous.time()
        scale.range = listOf(.0, 1.0)

        scale.domain = listOf(LocalDateTime(2013, 5, 6, 12, 44), LocalDateTime(2013, 5, 6, 12, 44))
        scale.nice()
        scale.domain.first() shouldBe LocalDateTime(2013, 5, 6, 12, 44)
        scale.domain.last() shouldBe LocalDateTime(2013, 5, 6, 12, 44)
    }

    @Test
    fun time_nice_count_use_the_specified_tick_count() {
        val scale = Scales.Continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(LocalDateTime(2009, 1, 1, 0, 17), LocalDateTime(2009, 1, 1, 23, 42))

        scale.nice(100)
        scale.domain.first() shouldBe LocalDateTime(2009, 1, 1, 0, 15)
        scale.domain.last() shouldBe LocalDateTime(2009, 1, 1, 23, 45)

        scale.nice(10)
        scale.domain.first() shouldBe LocalDateTime(2009, 1, 1, 0, 0)
        scale.domain.last() shouldBe LocalDateTime(2009, 1, 2, 0, 0)
    }

    @Test
    fun time_ticks_count_can_generate_subsecond_ticks() {
        val scale = Scales.Continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(LocalDateTime(2011, 1, 1, 12, 0, 0), LocalDateTime(2011, 1, 1, 12, 0, 1))

        val tickList = listOf(
                LocalDateTime(2011, 1, 1, 12, 0, 0, 0),
                LocalDateTime(2011, 1, 1, 12, 0, 0, 200000000),
                LocalDateTime(2011, 1, 1, 12, 0, 0, 400000000),
                LocalDateTime(2011, 1, 1, 12, 0, 0, 600000000),
                LocalDateTime(2011, 1, 1, 12, 0, 0, 800000000),
                LocalDateTime(2011, 1, 1, 12, 0, 1, 0)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_1_second_ticks() {
        val scale = Scales.Continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(LocalDateTime(2011, 1, 1, 12, 0, 0), LocalDateTime(2011, 1, 1, 12, 0, 4))

        val tickList = listOf(
                LocalDateTime(2011, 1, 1, 12, 0, 0),
                LocalDateTime(2011, 1, 1, 12, 0, 1),
                LocalDateTime(2011, 1, 1, 12, 0, 2),
                LocalDateTime(2011, 1, 1, 12, 0, 3),
                LocalDateTime(2011, 1, 1, 12, 0, 4)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_5_seconds_ticks() {
        val scale = Scales.Continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(LocalDateTime(2011, 1, 1, 12, 0, 0), LocalDateTime(2011, 1, 1, 12, 0, 20))

        val tickList = listOf(
                LocalDateTime(2011, 1, 1, 12, 0, 0),
                LocalDateTime(2011, 1, 1, 12, 0, 5),
                LocalDateTime(2011, 1, 1, 12, 0, 10),
                LocalDateTime(2011, 1, 1, 12, 0, 15),
                LocalDateTime(2011, 1, 1, 12, 0, 20)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_15_seconds_ticks() {
        val scale = Scales.Continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(LocalDateTime(2011, 1, 1, 12, 0, 0), LocalDateTime(2011, 1, 1, 12, 0, 50))

        val tickList = listOf(
                LocalDateTime(2011, 1, 1, 12, 0, 0),
                LocalDateTime(2011, 1, 1, 12, 0, 15),
                LocalDateTime(2011, 1, 1, 12, 0, 30),
                LocalDateTime(2011, 1, 1, 12, 0, 45)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_30_seconds_ticks() {
        val scale = Scales.Continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(LocalDateTime(2011, 1, 1, 12, 0, 0), LocalDateTime(2011, 1, 1, 12, 1, 50))

        val tickList = listOf(
                LocalDateTime(2011, 1, 1, 12, 0, 0),
                LocalDateTime(2011, 1, 1, 12, 0, 30),
                LocalDateTime(2011, 1, 1, 12, 1, 0),
                LocalDateTime(2011, 1, 1, 12, 1, 30)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_1_minute_ticks() {
        val scale = Scales.Continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(LocalDateTime(2011, 1, 1, 12, 0, 27), LocalDateTime(2011, 1, 1, 12, 4, 12))

        val tickList = listOf(
                LocalDateTime(2011, 1, 1, 12, 1),
                LocalDateTime(2011, 1, 1, 12, 2),
                LocalDateTime(2011, 1, 1, 12, 3),
                LocalDateTime(2011, 1, 1, 12, 4)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_5_minutes_ticks() {
        val scale = Scales.Continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(LocalDateTime(2011, 1, 1, 12, 3, 27), LocalDateTime(2011, 1, 1, 12, 21, 12))

        val tickList = listOf(
                LocalDateTime(2011, 1, 1, 12, 5),
                LocalDateTime(2011, 1, 1, 12, 10),
                LocalDateTime(2011, 1, 1, 12, 15),
                LocalDateTime(2011, 1, 1, 12, 20)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_15_minutes_ticks() {
        val scale = Scales.Continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(LocalDateTime(2011, 1, 1, 12, 8, 27), LocalDateTime(2011, 1, 1, 13, 4, 12))

        val tickList = listOf(
                LocalDateTime(2011, 1, 1, 12, 15),
                LocalDateTime(2011, 1, 1, 12, 30),
                LocalDateTime(2011, 1, 1, 12, 45),
                LocalDateTime(2011, 1, 1, 13, 0)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_30_minutes_ticks() {
        val scale = Scales.Continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(LocalDateTime(2011, 1, 1, 12, 28, 27), LocalDateTime(2011, 1, 1, 14, 4, 12))

        val tickList = listOf(
                LocalDateTime(2011, 1, 1, 12, 30),
                LocalDateTime(2011, 1, 1, 13, 0),
                LocalDateTime(2011, 1, 1, 13, 30),
                LocalDateTime(2011, 1, 1, 14, 0)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_1_hour_ticks() {
        val scale = Scales.Continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(LocalDateTime(2011, 1, 1, 12, 28, 27), LocalDateTime(2011, 1, 1, 16, 34, 12))

        val tickList = listOf(
                LocalDateTime(2011, 1, 1, 13, 0),
                LocalDateTime(2011, 1, 1, 14, 0),
                LocalDateTime(2011, 1, 1, 15, 0),
                LocalDateTime(2011, 1, 1, 16, 0)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_3_hours_ticks() {
        val scale = Scales.Continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(
                LocalDateTime(2011, 1, 1, 14, 28, 27),
                LocalDateTime(2011, 1, 2, 1, 34, 12))

        val tickList = listOf(
                LocalDateTime(2011, 1, 1, 15, 0),
                LocalDateTime(2011, 1, 1, 18, 0),
                LocalDateTime(2011, 1, 1, 21, 0),
                LocalDateTime(2011, 1, 2, 0, 0)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_6_hours_ticks() {
        val scale = Scales.Continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(LocalDateTime(2011, 1, 1, 16, 28, 27), LocalDateTime(2011, 1, 2, 14, 34, 12))

        val tickList = listOf(
                LocalDateTime(2011, 1, 1, 18, 0),
                LocalDateTime(2011, 1, 2, 0, 0),
                LocalDateTime(2011, 1, 2, 6, 0),
                LocalDateTime(2011, 1, 2, 12, 0)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_12_hours_ticks() {
        val scale = Scales.Continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(LocalDateTime(2011, 1, 1, 16, 28, 27), LocalDateTime(2011, 1, 3, 21, 34, 12))

        val tickList = listOf(
                LocalDateTime(2011, 1, 2, 0, 0),
                LocalDateTime(2011, 1, 2, 12, 0),
                LocalDateTime(2011, 1, 3, 0, 0),
                LocalDateTime(2011, 1, 3, 12, 0)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_1_day_ticks() {
        val scale = Scales.Continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(LocalDateTime(2011, 1, 1, 16, 28, 27), LocalDateTime(2011, 1, 5, 21, 34, 12))

        val tickList = listOf(
                LocalDateTime(2011, 1, 2, 0, 0),
                LocalDateTime(2011, 1, 3, 0, 0),
                LocalDateTime(2011, 1, 4, 0, 0),
                LocalDateTime(2011, 1, 5, 0, 0)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_2_days_ticks() {
        val scale = Scales.Continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(LocalDateTime(2011, 1, 2, 16, 28, 27), LocalDateTime(2011, 1, 9, 21, 34, 12))

        val tickList = listOf(
                LocalDateTime(2011, 1, 3, 0, 0),
                LocalDateTime(2011, 1, 5, 0, 0),
                LocalDateTime(2011, 1, 7, 0, 0),
                LocalDateTime(2011, 1, 9, 0, 0)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    // TODO : actually test don't pass due to a bug in timeWeek (timeSunday used here)
    // check timeSunday as it seems to returns mondays !! :D
//    //    @Test
//    fun time_ticks_count_can_generate_1_week_ticks() {
//        val scale = Scales.Continuous.time()
//        scale.range = listOf(.0, 1.0)
//        scale.domain = listOf(LocalDateTime(2011, 1, 1, 16, 28, 27), LocalDateTime(2011, 1, 23, 21, 34, 12))
//
//        val tickList = listOf(
//                LocalDateTime(2011, 1, 2, 0, 0),
//                LocalDateTime(2011, 1, 9, 0, 0),
//                LocalDateTime(2011, 1, 16, 0, 0),
//                LocalDateTime(2011, 1, 23, 0, 0)
//        )
//
//        val ticks = scale.ticks(4)
//        tickList.size shouldBe ticks.size
//        tickList.forEachIndexed { index, tick ->
//            tick shouldBe ticks[index]
//        }
//    }

    @Test
    fun time_ticks_count_can_generate_1_month_ticks() {
        val scale = Scales.Continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(LocalDateTime(2011, 1, 18, 0, 0), LocalDateTime(2011, 5, 2, 0, 0))

        val tickList = listOf(
                LocalDateTime(2011, 2, 1, 0, 0),
                LocalDateTime(2011, 3, 1, 0, 0),
                LocalDateTime(2011, 4, 1, 0, 0),
                LocalDateTime(2011, 5, 1, 0, 0)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_3_months_ticks() {
        val scale = Scales.Continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(LocalDateTime(2010, 11, 18, 0, 0), LocalDateTime(2011, 10, 2, 0, 0))

        val tickList = listOf(
                LocalDateTime(2011, 1, 1, 0, 0),
                LocalDateTime(2011, 4, 1, 0, 0),
                LocalDateTime(2011, 7, 1, 0, 0),
                LocalDateTime(2011, 10, 1, 0, 0)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_1_year_ticks() {
        val scale = Scales.Continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(LocalDateTime(2010, 12, 18, 0, 0), LocalDateTime(2014, 3, 2, 0, 0))

        var tickList = listOf(
                LocalDateTime(2011, 1, 1, 0, 0),
                LocalDateTime(2012, 1, 1, 0, 0),
                LocalDateTime(2013, 1, 1, 0, 0),
                LocalDateTime(2014, 1, 1, 0, 0)
        )

        var ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }

        scale.domain = listOf(LocalDateTime(2010, 12, 18, 0, 0), LocalDateTime(2022, 3, 2, 0, 0))

        tickList = listOf(
                LocalDateTime(2012, 1, 1, 0, 0),
                LocalDateTime(2014, 1, 1, 0, 0),
                LocalDateTime(2016, 1, 1, 0, 0),
                LocalDateTime(2018, 1, 1, 0, 0),
                LocalDateTime(2020, 1, 1, 0, 0),
                LocalDateTime(2022, 1, 1, 0, 0)
        )

        ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_can_generate_multi_years_ticks() {
        val scale = Scales.Continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(LocalDateTime(0, 12, 18, 0, 0), LocalDateTime(2014, 3, 2, 0, 0))

        val tickList = listOf(
                LocalDateTime(500,  1, 1, 0, 0),
                LocalDateTime(1000, 1, 1, 0, 0),
                LocalDateTime(1500, 1, 1, 0, 0),
                LocalDateTime(2000, 1, 1, 0, 0)
        )

        val ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }

    @Test
    fun time_ticks_count_returns_no_ticks_for_empty_domain() {
        val scale = Scales.Continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(LocalDateTime(2014, 3, 2, 0, 0), LocalDateTime(2014, 3, 2, 0, 0))

        val ticks = scale.ticks(6)
        ticks.size shouldBe 0
    }



    @Test
    fun time_ticks_count_returns_descending_ticks_for_descending_domain() {
        val scale = Scales.Continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(LocalDateTime(2014, 3, 2, 0, 0), LocalDateTime(2010, 12, 18, 0, 0))

        var tickList = listOf(
                LocalDateTime(2014, 1, 1, 0, 0),
                LocalDateTime(2013, 1, 1, 0, 0),
                LocalDateTime(2012, 1, 1, 0, 0),
                LocalDateTime(2011, 1, 1, 0, 0)
        )

        var ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }

        scale.domain = listOf(LocalDateTime(2010, 12, 18, 0, 0), LocalDateTime(2011, 11, 2, 0, 0))
        tickList = listOf(
                LocalDateTime(2011, 1, 1, 0, 0),
                LocalDateTime(2011, 4, 1, 0, 0),
                LocalDateTime(2011, 7, 1, 0, 0),
                LocalDateTime(2011, 10, 1, 0, 0)
        )

        ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }

        scale.domain = listOf(LocalDateTime(2011, 11, 2, 0, 0), LocalDateTime(2010, 12, 18, 0, 0))
        tickList = listOf(
                LocalDateTime(2011, 10, 1, 0, 0),
                LocalDateTime(2011, 7, 1, 0, 0),
                LocalDateTime(2011, 4, 1, 0, 0),
                LocalDateTime(2011, 1, 1, 0, 0)
        )

        ticks = scale.ticks(4)
        tickList.size shouldBe ticks.size
        tickList.forEachIndexed { index, tick ->
            tick shouldBe ticks[index]
        }
    }


    @Test
    fun additionnal_weekdays_tests() {
        val scale = Scales.Continuous.time()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(LocalDateTime(2020, 1, 1, 0, 0), LocalDateTime(2020, 2, 11, 15, 0))

        val ticks = scale.ticks()
        ticks.size shouldBe 6
    }

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