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
import kotlin.test.Ignore
import kotlin.test.Test

class FormatTypeExponentTests : TestBase() {

    @Test
    fun format_e_can_output_exponent_notation () {
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

    @Test
    fun format_e_can_format_negative_zero_as_zero () {
        formatter("1e")(-0.0)   shouldBe "0.000000e+0"
        formatter("1e")(-1e-12) shouldBe "-1.000000e-12"
    }

    /*"format_,e_does not group Infinity" {
        format(",e")(Infinity) shouldBe "Infinity"
    }*/

}
