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

class FormatTypeHexadecimalTests : TestBase() {

    @Test fun format_x_returns_the_expected_hexadecimal__lowercase_string () {
        formatter(Type.HEX_LOWERCASE)(0xdeadbeef.toDouble()) shouldBe "deadbeef"

    }

    @Test fun format_x_returns_the_expected_hexadecimal_lowercase_string_with_prefix () {
        formatter(Type.HEX_LOWERCASE, symbol = Symbol.NUMBER_BASE)(0xdeadbeef.toDouble()) shouldBe "0xdeadbeef"

    }

    @Test fun format_x_groups_thousands () {
        formatter(Type.HEX_LOWERCASE, group = true)(0xdeadbeef.toDouble()) shouldBe "de,adb,eef"

    }

    @Test fun format_x_does_not_group_the_prefix () {
        formatter(Type.HEX_LOWERCASE, group = true, symbol = Symbol.NUMBER_BASE)(0xadeadbeef.toDouble()) shouldBe "0xade,adb,eef"

    }

    /*@Test fun format_+#x_puts the sign before the prefix () {
        format("+#x")(0xdeadbeef.toDouble()) shouldBe  "+0xdeadbeef"
        format("+#x")(-(0xdeadbeef.toDouble())) shouldBe "-0xdeadbeef"
        format(" #x")(0xdeadbeef.toDouble()) shouldBe  " 0xdeadbeef"
        format(" #x")(-(0xdeadbeef.toDouble())) shouldBe "-0xdeadbeef"
    }*/

    @Test fun format_dollar_x_formats_hexadecimal_currency () {
        formatter(Type.HEX_LOWERCASE, group = true, symbol = Symbol.CURRENCY)(0xdeadbeef.toDouble()) shouldBe "\$de,adb,eef"

    }

    @Test fun format_exponent_x_always_has_exponent_zero () {
        formatter(".2x")(0xdeadbeef.toDouble()) shouldBe "deadbeef"
        formatter(".2x")(-4.2) shouldBe "-4"

    }

    @Test fun format_x_rounds_non_integers () {
        formatter("x")(2.4) shouldBe "2"

    }

    @Test fun format_x_can_format_negative_zero_as_zero () {
        formatter("x")(-0.0) shouldBe "0"
        formatter("x")(-1e-12) shouldBe "0"

    }

    /*"format_x_does not consider -0xeee to be positive" {
        format("x")(-0xeee.toDouble()) shouldBe "-eee"
    }*/

    @Test fun format_X_returns_the_expected_hexadecimal_uppercase_string () {
        formatter("X")(0xdeadbeef.toDouble()) shouldBe "DEADBEEF"
    }

    @Test fun format_X_returns_the_expected_hexadecimal_uppercase_string_with_prefix () {
        formatter("#X")(0xdeadbeef.toDouble()) shouldBe "0xDEADBEEF"
    }

    @Test fun format_X_can_format_negative_zero_as_zero () {
        formatter("X")(-0.0) shouldBe "0"
        formatter("X")(-1e-12) shouldBe "0"
    }

    /*"format_X_does not consider -0xeee to be positive" {
        format("X")(-0xeee.toDouble()) shouldBe "-EEE"
    }*/

    @Test fun format_width_x_considers_the_prefix () {
        formatter("20x")(0xdeadbeef.toDouble()) shouldBe "            deadbeef"
        formatter("#20x")(0xdeadbeef.toDouble()) shouldBe "          0xdeadbeef"
        formatter("020x")(0xdeadbeef.toDouble()) shouldBe "000000000000deadbeef"
        formatter("#020x")(0xdeadbeef.toDouble()) shouldBe "0x0000000000deadbeef"
    }

}
