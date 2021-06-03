/*
 * Copyright (c) 2018-2021. data2viz sàrl.
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

@file:Suppress("FunctionName", "unused")

package io.data2viz.format

import io.data2viz.test.TestBase
import kotlin.math.PI
import kotlin.test.Ignore
import kotlin.test.Test

class FormatTypeDecimalWithSITests : TestBase() {


    @Test fun format_s_outputs_SI_prefix_notation_with_default_exponent_6 () {
        val f = formatter("s")
        f(0.0)      shouldBe "0.00000"
        f(1.0)      shouldBe "1.00000"
        f(10.0)     shouldBe "10.0000"
        f(100.0)    shouldBe "100.000"
        f(999.5)    shouldBe "999.500"
        f(999500.0) shouldBe "999.500k"
        f(1000.0)   shouldBe "1.00000k"
        f(100.0)    shouldBe "100.000"
        f(1400.0)   shouldBe "1.40000k"
        f(1500.5)   shouldBe "1.50050k"
        f(.00001)   shouldBe "10.0000µ"
        f(.000001)  shouldBe "1.00000µ"
    }

    @Test fun format_s_typed_outputs_SI_prefix_notation_with_default_exponent_6 () {
        val f = formatter(Type.DECIMAL_WITH_SI)
        f(0.0)      shouldBe "0.00000"
        f(1.0)      shouldBe "1.00000"
        f(10.0)     shouldBe "10.0000"
        f(100.0)    shouldBe "100.000"
        f(999.5)    shouldBe "999.500"
        f(999500.0) shouldBe "999.500k"
        f(1000.0)   shouldBe "1.00000k"
        f(100.0)    shouldBe "100.000"
        f(1400.0)   shouldBe "1.40000k"
        f(1500.5)   shouldBe "1.50050k"
        f(.00001)   shouldBe "10.0000µ"
        f(.000001)  shouldBe "1.00000µ"
    }

    @Test fun format_exponent_s_outputs_SI_prefix_notation_with_exponent_significant_digits () {
        var f = formatter(Type.DECIMAL_WITH_SI, precision = 3)
        f(0.0)                  shouldBe "0.00"
        f(1.0)                  shouldBe "1.00"
        f(10.0)                 shouldBe "10.0"
        f(100.0)                shouldBe "100"
        f(999.5)                shouldBe "1.00k"
        f(999500.0)             shouldBe "1.00M"
        f(1000.0)               shouldBe "1.00k"
        f(1500.5)               shouldBe "1.50k"
        f(145500000.0)          shouldBe "146M"
        f(145999999.999999347)  shouldBe "146M"
        f(1e26)                 shouldBe "100Y"
        f(.000001)              shouldBe "1.00µ"
        f(.009995)              shouldBe "10.0m"

        f = formatter(".4s")
        f(999.5)    shouldBe "999.5"
        f(999500.0) shouldBe "999.5k"
        f(.009995)  shouldBe "9.995m"
    }

    @Test fun format_s_formats_numbers_smaller_than_1e_24_with_yocto () {
        val f = formatter(Type.DECIMAL_WITH_SI, precision = 8)
        f(1.29e-30)     shouldBe "0.0000013y" // Note: rounded!
        f(1.29e-29)     shouldBe "0.0000129y"
        f(1.29e-28)     shouldBe "0.0001290y"
        f(1.29e-27)     shouldBe "0.0012900y"
        f(1.29e-26)     shouldBe "0.0129000y"
        f(1.29e-25)     shouldBe "0.1290000y"
        f(1.29e-24)     shouldBe "1.2900000y"
        f(1.29e-23)     shouldBe "12.900000y"
        f(1.29e-22)     shouldBe "129.00000y"
        f(1.29e-21)     shouldBe "1.2900000z"
        f(-1.29e-30)    shouldBe "-0.0000013y" // Note: rounded!
        f(-1.29e-29)    shouldBe "-0.0000129y"
        f(-1.29e-28)    shouldBe "-0.0001290y"
        f(-1.29e-27)    shouldBe "-0.0012900y"
        f(-1.29e-26)    shouldBe "-0.0129000y"
        f(-1.29e-25)    shouldBe "-0.1290000y"
        f(-1.29e-24)    shouldBe "-1.2900000y"
        f(-1.29e-23)    shouldBe "-12.900000y"
        f(-1.29e-22)    shouldBe "-129.00000y"
        f(-1.29e-21)    shouldBe "-1.2900000z"
    }

    @Test fun format_s_formats_numbers_larger_than_1e24_with_yotta () {
        val f = formatter(Type.DECIMAL_WITH_SI, precision = 8)
        f(1.23e+21)     shouldBe "1.2300000Z"
        f(1.23e+22)     shouldBe "12.300000Z"
        f(1.23e+23)     shouldBe "123.00000Z"
        f(1.23e+24)     shouldBe "1.2300000Y"
        f(1.23e+25)     shouldBe "12.300000Y"
        f(1.23e+26)     shouldBe "123.00000Y"
        f(1.23e+27)     shouldBe "1230.0000Y"
        f(1.23e+28)     shouldBe "12300.000Y"
        f(1.23e+29)     shouldBe "123000.00Y"
        f(1.23e+30)     shouldBe "1230000.0Y"
        f(-1.23e+21)    shouldBe "-1.2300000Z"
        f(-1.23e+22)    shouldBe "-12.300000Z"
        f(-1.23e+23)    shouldBe "-123.00000Z"
        f(-1.23e+24)    shouldBe "-1.2300000Y"
        f(-1.23e+25)    shouldBe "-12.300000Y"
        f(-1.23e+26)    shouldBe "-123.00000Y"
        f(-1.23e+27)    shouldBe "-1230.0000Y"
        f(-1.23e+28)    shouldBe "-12300.000Y"
        f(-1.23e+29)    shouldBe "-123000.00Y"
        f(-1.23e+30)    shouldBe "-1230000.0Y"
    }


    @Test fun format_s_outputs_SI_prefix_notation_with_a_currency_symbol () {
        var f = formatter(Type.DECIMAL_WITH_SI, symbol = Symbol.CURRENCY, precision = 2)
        f(0.0)      shouldBe "$0.0"
        f(2.5e5)    shouldBe "$250k"
        f(-2.5e8)   shouldBe "-$250M"
        f(2.5e11)   shouldBe "$250G"

        f = formatter("$.3s")
        f(0.0)                  shouldBe "$0.00"
        f(1.0)                  shouldBe "$1.00"
        f(10.0)                 shouldBe "$10.0"
        f(100.0)                shouldBe "$100"
        f(999.5)                shouldBe "$1.00k"
        f(999500.0)             shouldBe "$1.00M"
        f(1000.0)               shouldBe "$1.00k"
        f(1500.5)               shouldBe "$1.50k"
        f(145500000.0)          shouldBe "$146M"
        f(145999999.999999347)  shouldBe "$146M"
        f(1e26)                 shouldBe "$100Y"
        f(.000001)              shouldBe "$1.00µ"
        f(.009995)              shouldBe "$10.0m"

        f = formatter("$.4s")
        f(999.5)    shouldBe "$999.5"
        f(999500.0) shouldBe "$999.5k"
        f(.009995)  shouldBe "$9.995m"
    }

    @Test fun format_s_SI_prefix_notation_exponent_is_consistent_for_small_and_large_numbers  () {
        var f = formatter(Type.DECIMAL_WITH_SI, precision = 0)
        f(1e-5) shouldBe "10µ"
        f(1e-4) shouldBe "100µ"
        f(1e-3) shouldBe "1m"
        f(1e-2) shouldBe "10m"
        f(1e-1) shouldBe "100m"
        f(1e+0) shouldBe "1"
        f(1e+1) shouldBe "10"
        f(1e+2) shouldBe "100"
        f(1e+3) shouldBe "1k"
        f(1e+4) shouldBe "10k"
        f(1e+5) shouldBe "100k"

        f = formatter(Type.DECIMAL_WITH_SI, precision = 4)
        f(1e-5) shouldBe "10.00µ"
        f(1e-4) shouldBe "100.0µ"
        f(1e-3) shouldBe "1.000m"
        f(1e-2) shouldBe "10.00m"
        f(1e-1) shouldBe "100.0m"
        f(1e+0) shouldBe "1.000"
        f(1e+1) shouldBe "10.00"
        f(1e+2) shouldBe "100.0"
        f(1e+3) shouldBe "1.000k"
        f(1e+4) shouldBe "10.00k"
        f(1e+5) shouldBe "100.0k"
    }

    @Test fun format_0_width_s_will_group_thousands_due_to_zero_fill () {
        val f = formatter(Type.DECIMAL_WITH_SI, zero = true, fill = '0', width = 20, group = true)
        f(42.0)     shouldBe "000,000,000,042.0000"
        f(42e12)    shouldBe "00,000,000,042.0000T"
    }

    @Test fun format__s_will_group_thousands_for_very_large_numbers () {
        val f = formatter(",s")
        f(42e30) shouldBe "42,000,000Y"
    }


}
