package io.data2viz.format

import io.data2viz.test.TestBase
import kotlin.test.Test

class FormatTypeExponentTests : TestBase() {

    @Test fun format_e_can_output_exponent_notation () {
        val f = formatter("e")
        f(0.0)                          shouldBe "0.000000e+0"
        f(42.0)                         shouldBe "4.200000e+1"
        f(42000000.0)                   shouldBe "4.200000e+7"
        f(420000000.0)                  shouldBe "4.200000e+8"
        f(-4.0)                         shouldBe "-4.000000e+0"
        f(-42.0)                        shouldBe "-4.200000e+1"
        f(-4200000.0)                   shouldBe "-4.200000e+6"
        f(-42000000.0)                  shouldBe "-4.200000e+7"
        formatter(".0e")(42.0)  shouldBe "4e+1"
        formatter(".3e")(42.0)  shouldBe "4.200e+1"
    }

    @Test fun format_e_can_format_negative_zero_as_zero () {
        formatter("1e")(-0.0)   shouldBe "0.000000e+0"
        formatter("1e")(-1e-12) shouldBe "-1.000000e-12"
    }

    /*"format_,e_does not group Infinity" {
        format(",e")(Infinity) shouldBe "Infinity"
    }*/

}
  