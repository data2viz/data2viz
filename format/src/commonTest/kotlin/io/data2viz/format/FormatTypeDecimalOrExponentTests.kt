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

package io.data2viz.format

import io.data2viz.test.TestBase
import kotlin.test.Test

class FormatTypeDecimalOrExponentTests : TestBase() {


    @Test fun format_g_can_output_general_notation () {
        formatter(".1g")(0.049) shouldBe "0.05"
        formatter(".1g")(0.49) shouldBe "0.5"
        formatter(".2g")(0.449) shouldBe "0.45"
        formatter(".3g")(0.4449) shouldBe "0.445"
        formatter(".5g")(0.444449) shouldBe "0.44445"
        formatter(".1g")(100.0) shouldBe "1e+2"
        formatter(".2g")(100.0) shouldBe "1.0e+2"
        formatter(".3g")(100.0) shouldBe "100"
        formatter(".5g")(100.0) shouldBe "100.00"
        formatter(".5g")(100.2) shouldBe "100.20"
        formatter(".2g")(0.002) shouldBe "0.0020"
    }

    @Test fun format_g_typed_can_output_general_notation () {
        formatter(Type.DECIMAL_OR_EXPONENT, precision = 1)(0.049)   shouldBe "0.05"
        formatter(Type.DECIMAL_OR_EXPONENT, precision = 1)(0.49)    shouldBe "0.5"
        formatter(Type.DECIMAL_OR_EXPONENT, precision = 2)(0.449)   shouldBe "0.45"
        formatter(Type.DECIMAL_OR_EXPONENT, precision = 3)(0.4449)  shouldBe "0.445"
        formatter(Type.DECIMAL_OR_EXPONENT, precision = 5)(0.444449)shouldBe "0.44445"
        formatter(Type.DECIMAL_OR_EXPONENT, precision = 1)(100.0)   shouldBe "1e+2"
        formatter(Type.DECIMAL_OR_EXPONENT, precision = 2)(100.0)   shouldBe "1.0e+2"
        formatter(Type.DECIMAL_OR_EXPONENT, precision = 3)(100.0)   shouldBe "100"
        formatter(Type.DECIMAL_OR_EXPONENT, precision = 5)(100.0)   shouldBe "100.00"
        formatter(Type.DECIMAL_OR_EXPONENT, precision = 5)(100.2)   shouldBe "100.20"
        formatter(Type.DECIMAL_OR_EXPONENT, precision = 2)(0.002)   shouldBe "0.0020"
    }

    @Test fun format_g_can_group_thousands_with_general_notation () {
        val f = formatter(",.12g")
        f(0.0)          shouldBe "0.00000000000"
        f(42.0)         shouldBe "42.0000000000"
        f(42000000.0)   shouldBe "42,000,000.0000"
        f(420000000.0)  shouldBe "420,000,000.000"
        f(-4.0)         shouldBe "-4.00000000000"
        f(-42.0)        shouldBe "-42.0000000000"
        f(-4200000.0)   shouldBe "-4,200,000.00000"
        f(-42000000.0)  shouldBe "-42,000,000.0000"
    }

    @Test fun format_g_typed_can_group_thousands_with_general_notation () {
        val f = formatter(Type.DECIMAL_OR_EXPONENT, precision = 12, group = true)
        f(0.0)          shouldBe "0.00000000000"
        f(42.0)         shouldBe "42.0000000000"
        f(42000000.0)   shouldBe "42,000,000.0000"
        f(420000000.0)  shouldBe "420,000,000.000"
        f(-4.0)         shouldBe "-4.00000000000"
        f(-42.0)        shouldBe "-42.0000000000"
        f(-4200000.0)   shouldBe "-4,200,000.00000"
        f(-42000000.0)  shouldBe "-42,000,000.0000"
    }

    /**
     * TYPE N
     */

    @Test fun format_n_is_an_alias_for_g() {
        val f = formatter(".12n")
        f(0.0)          shouldBe "0.00000000000"
        f(42.0)         shouldBe "42.0000000000"
        f(42000000.0)   shouldBe "42,000,000.0000"
        f(420000000.0)  shouldBe "420,000,000.000"
        f(-4.0)         shouldBe "-4.00000000000"
        f(-42.0)        shouldBe "-42.0000000000"
        f(-4200000.0)   shouldBe "-4,200,000.00000"
        f(-42000000.0)  shouldBe "-42,000,000.0000"
        f(.0042)        shouldBe "0.00420000000000"
        f(.42)          shouldBe "0.420000000000"
        f(1e21)         shouldBe "1.00000000000e+21"
    }

    @Test fun format_n_uses_zero_padding () {
        formatter("01.0n")      (0.0)           shouldBe "0"
        formatter("02.0n")      (0.0)           shouldBe "00"
        formatter("03.0n")      (0.0)           shouldBe "000"
        formatter("05.0n")      (0.0)           shouldBe "0,000"
        formatter("08.0n")      (0.0)           shouldBe "0,000,000"
        formatter("013.0n")     (0.0)           shouldBe "0,000,000,000"
        formatter("021.0n")     (0.0)           shouldBe "0,000,000,000,000,000"
        formatter("013.8n")     (-42000000.0)   shouldBe "-0,042,000,000"
    }

}
