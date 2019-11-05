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

package io.data2viz.format

import io.data2viz.test.TestBase
import kotlin.test.Test


class FormatTypeFixedPointTests : TestBase() {

    @Test
    fun format_f_can_output_fixed_point_notation() {
        formatter(".1f")    (0.49)      shouldBe "0.5"
        formatter(".2f")    (0.449)     shouldBe "0.45"
        formatter(".3f")    (0.4449)    shouldBe "0.445"
        formatter(".5f")    (0.444449)  shouldBe "0.44445"
        formatter(".1f")    (100.0)     shouldBe "100.0"
        formatter(".2f")    (100.0)     shouldBe "100.00"
        formatter(".3f")    (100.0)     shouldBe "100.000"
        formatter(".5f")    (100.0)     shouldBe "100.00000"
    }

    @Test
    fun format_currency_sign_with_comma_grouping_and_sign() {
        val f = formatter("+$,.2f")
        f(0.0)      shouldBe "+$0.00"
        f(0.429)    shouldBe "+$0.43"
        f(-0.429)   shouldBe "-$0.43"
        f(-1.0)     shouldBe "-$1.00"
        f(1e4)      shouldBe "+$10,000.00"
    }

    @Test
    fun format_f_can_grouping_thousands_space_fill_and_round_to_significant_digits () {
        formatter("10,.1f") (123456.49)         shouldBe " 123,456.5"
        formatter("10,.2f") (1234567.449)       shouldBe "1,234,567.45"
        formatter("10,.3f") (12345678.4449)     shouldBe "12,345,678.445"
        formatter("10,.5f") (123456789.444449)  shouldBe "123,456,789.44445"
        formatter("10,.1f") (123456.0)          shouldBe " 123,456.0"
        formatter("10,.2f") (1234567.0)         shouldBe "1,234,567.00"
        formatter("10,.3f") (12345678.0)        shouldBe "12,345,678.000"
        formatter("10,.5f") (123456789.0)       shouldBe "123,456,789.00000"
    }

    @Test
    fun  format_f_can_display_integers_in_fixed_point_notation (){
        formatter("f")(42.0) shouldBe "42.000000"
    }

    @Test
    fun format_f_can_format_negative_zero_as_zero() {
        formatter("f")(-0.0)    shouldBe "0.000000"
        formatter("f")(-1e-12)  shouldBe "0.000000"
    }

//        "format_f_can format negative infinity" {
//            format("f")(-Infinity) shouldBe "-Infinity"
//        }

//        "format_,f_does not grouping Infinity" {
//            format(",f")(Infinity) shouldBe "Infinity"
//        }

}