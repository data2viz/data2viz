package io.data2viz.time

import io.data2viz.test.TestBase
import kotlin.test.Test

class IntervalTestsTests : TestBase() {

    /*@Test
    fun interval_floor_offset_returns_a_custom_time_interval() {
        val interval = Interval(
                fun (date:Date): Date {
                    return Date(date.setUTCMinutes(0, 0))
                },
                fun (date:Date, step:Int): Date {
                    return Date(date.setUTCHours(date.getUTCHours() + step, null, null))
                }
        )

        val date1 = interval.floor(Date(2015, 1, 1, 12, 34, 56, 789)).getTime()
        val date2 = Date(2015, 1, 1, 12, 0, 0, 0).getTime()
        date1 shouldBe date2
    }*/

}