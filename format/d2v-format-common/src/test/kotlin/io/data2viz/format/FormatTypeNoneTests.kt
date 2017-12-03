@file:Suppress("FunctionName", "unused")

package io.data2viz.format

import io.data2viz.test.TestBase
import kotlin.math.PI
import kotlin.test.Ignore
import kotlin.test.Test

class FormatTypeNoneTests : TestBase() {
  
    @Test fun format_uses_significant_exponent_and_trims_insignificant_zeros () {
        formatter(".1")(4.9) shouldBe "5"
        formatter(".1")(0.49) shouldBe "0.5"
        formatter(".2")(4.8) shouldBe "4.8"
        formatter(".2")(0.49) shouldBe "0.49"
        formatter(".2")(0.449) shouldBe "0.45"
        formatter(".3")(4.9) shouldBe "4.9"
        formatter(".3")(0.49) shouldBe "0.49"
        formatter(".3")(0.449) shouldBe "0.449"
        formatter(".3")(0.4449) shouldBe "0.445"
        formatter(".5")(0.444449) shouldBe "0.44445"
    }

    @Test fun format_typed_uses_significant_exponent_and_trims_insignificant_zeros () {
        formatter(".1")(4.9) shouldBe "5"
        formatter(".1")(0.49) shouldBe "0.5"
        formatter(".2")(4.8) shouldBe "4.8"
        formatter(".2")(0.49) shouldBe "0.49"
        formatter(".2")(0.449) shouldBe "0.45"
        formatter(".3")(4.9) shouldBe "4.9"
        formatter(".3")(0.49) shouldBe "0.49"
        formatter(".3")(0.449) shouldBe "0.449"
        formatter(".3")(0.4449) shouldBe "0.445"
        formatter(".5")(0.444449) shouldBe "0.44445"
    }

    @Test fun format_does_not_trim_significant_zeros () {
        formatter(".5")(10.0) shouldBe "10"
        formatter(".5")(100.0) shouldBe "100"
        formatter(".5")(1000.0) shouldBe "1000"
        formatter(".5")(21010.0) shouldBe "21010"
        formatter(".5")(1.10001) shouldBe "1.1"
        formatter(".5")(1.10001e6) shouldBe "1.1e+6"
        formatter(".6")(1.10001) shouldBe "1.10001"
        formatter(".6")(1.10001e6) shouldBe "1.10001e+6"
    }

    @Test fun format_exponent_also_trims_the_coefficient_point_if_there_are_only_insignificant_zeros () {
        formatter(".5")(1.00001) shouldBe "1"
        formatter(".5")(1.00001e6) shouldBe "1e+6"
        formatter(".6")(1.00001) shouldBe "1.00001"
        formatter(".6")(1.00001e6) shouldBe "1.00001e+6"
    }

    @Test fun format_dollar_can_output_a_currency () {
        val f = formatter("$")
        f(0.0) shouldBe "$0"
        f(.042) shouldBe "$0.042"
        f(.42) shouldBe "$0.42"
        f(4.2) shouldBe "$4.2"
        f(-.042) shouldBe "-$0.042"
        f(-.42) shouldBe "-$0.42"
        f(-4.2) shouldBe "-$4.2"
    }

    @Test fun format_dollar_can_output_a_currency_with_parentheses_for_negative_values () {
        val f = formatter("($")
        f(0.0) shouldBe "$0"
        f(.042) shouldBe "$0.042"
        f(.42) shouldBe "$0.42"
        f(4.2) shouldBe "$4.2"
        f(-.042) shouldBe "($0.042)"
        f(-.42) shouldBe "($0.42)"
        f(-4.2) shouldBe "($4.2)"
    }

    @Test fun format__can_format_negative_zero_as_zero () {
        formatter("")(-0.0) shouldBe "0"
    }


 }
