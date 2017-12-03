@file:Suppress("FunctionName", "unused")

package io.data2viz.format

import io.data2viz.test.TestBase
import kotlin.math.PI
import kotlin.test.Ignore
import kotlin.test.Test

class FormatTypePercentTests : TestBase() {
  
    @Test
    fun format_percent_can_output_a_whole_percentage() {
        val f = formatter(".0%")
        f(0.0) shouldBe "0%"
        f(.042) shouldBe "4%"
        f(.42) shouldBe "42%"
        f(4.2) shouldBe "420%"
        f(-.042) shouldBe "-4%"
        f(-.42) shouldBe "-42%"
        f(-4.2) shouldBe "-420%"
    }

    @Test
    fun format_percent_can_output_a_percentage_with_exponent() {
        var f = formatter(".1%")
        f(.234) shouldBe "23.4%"
        f = formatter(".2%")
        f(.234) shouldBe "23.40%"
    }

    @Test
    fun format_percent_fill_respects_suffix () {
        formatter("020.0%")(42.0) shouldBe "0000000000000004200%"
        formatter("20.0%")(42.0) shouldBe "               4200%"
    }

    //      -42,200%       did not equal       -42,200%
    //         42%           did not equal          42%

    @Test
    fun format_percent_align_center_puts_suffix_adjacent_to_number() {
        formatter("^21.0%")(.42) shouldBe "         42%         "
        formatter("^21,.0%")(422.0) shouldBe "       42,200%       "
        formatter("^21,.0%")(-422.0) shouldBe "      -42,200%       "
//        Locale()
    }



 }
