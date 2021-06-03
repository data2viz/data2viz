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

class FormatTypeDecimalTests : TestBase() {

    @Test fun format_r_can_round_to_significant_digits () {
        formatter(".2r")        (0.0)               shouldBe "0.0"
        formatter(".1r")        (0.049)             shouldBe "0.05"
        formatter(".1r")        (-0.049)            shouldBe "-0.05"
        formatter(".1r")        (0.49)              shouldBe "0.5"
        formatter(".1r")        (-0.49)             shouldBe "-0.5"
        formatter(".2r")        (0.449)             shouldBe "0.45"
        formatter(".3r")        (0.4449)            shouldBe "0.445"
        formatter(".3r")        (2.00)              shouldBe "2.00"
        formatter(".3r")        (3.9995)            shouldBe "4.00"
        formatter(".5r")        (0.444449)          shouldBe "0.44445"
        formatter("r")          (123.45)            shouldBe "123.450"
        formatter(".1r")        (123.45)            shouldBe "100"
        formatter(".2r")        (123.45)            shouldBe "120"
        formatter(".3r")        (123.45)            shouldBe "123"
        formatter(".4r")        (123.45)            shouldBe "123.5"
        formatter(".5r")        (123.45)            shouldBe "123.45"
        formatter(".6r")        (123.45)            shouldBe "123.450"
        formatter(".1r")        (.9)                shouldBe "0.9"
        formatter(".1r")        (.09)               shouldBe "0.09"
        formatter(".1r")        (.949)              shouldBe "0.9"
        formatter(".1r")        (.0949)             shouldBe "0.09"
        formatter(".1r")        (.0000000129)       shouldBe "0.00000001"
        formatter(".2r")        (.0000000129)       shouldBe "0.000000013"
        formatter(".2r")        (.00000000129)      shouldBe "0.0000000013"
        formatter(".3r")        (.00000000129)      shouldBe "0.00000000129"
        formatter(".4r")        (.00000000129)      shouldBe "0.000000001290"
        formatter(".10r")       (.9999999999)       shouldBe "0.9999999999"
        formatter(".15r")       (.999999999999999)  shouldBe "0.999999999999999"
    }

    @Test fun format_r_typed_can_round_to_significant_digits () {
        formatter(Type.DECIMAL, precision = 2)      (0.0)               shouldBe "0.0"
        formatter(Type.DECIMAL, precision = 1)      (0.049)             shouldBe "0.05"
        formatter(Type.DECIMAL, precision = 1)      (-0.049)            shouldBe "-0.05"
        formatter(Type.DECIMAL, precision = 1)      (0.49)              shouldBe "0.5"
        formatter(Type.DECIMAL, precision = 1)      (-0.49)             shouldBe "-0.5"
        formatter(Type.DECIMAL, precision = 2)      (0.449)             shouldBe "0.45"
        formatter(Type.DECIMAL, precision = 3)      (0.4449)            shouldBe "0.445"
        formatter(Type.DECIMAL, precision = 3)      (2.00)              shouldBe "2.00"
        formatter(Type.DECIMAL, precision = 3)      (3.9995)            shouldBe "4.00"
        formatter(Type.DECIMAL, precision = 5)      (0.444449)          shouldBe "0.44445"
        formatter(Type.DECIMAL)                     (123.45)            shouldBe "123.450"
        formatter(Type.DECIMAL, precision = 1)      (123.45)            shouldBe "100"
        formatter(Type.DECIMAL, precision = 2)      (123.45)            shouldBe "120"
        formatter(Type.DECIMAL, precision = 3)      (123.45)            shouldBe "123"
        formatter(Type.DECIMAL, precision = 4)      (123.45)            shouldBe "123.5"
        formatter(Type.DECIMAL, precision = 5)      (123.45)            shouldBe "123.45"
        formatter(Type.DECIMAL, precision = 6)      (123.45)            shouldBe "123.450"
        formatter(Type.DECIMAL, precision = 1)      (.9)                shouldBe "0.9"
        formatter(Type.DECIMAL, precision = 1)      (.09)               shouldBe "0.09"
        formatter(Type.DECIMAL, precision = 1)      (.949)              shouldBe "0.9"
        formatter(Type.DECIMAL, precision = 1)      (.0949)             shouldBe "0.09"
        formatter(Type.DECIMAL, precision = 1)      (.0000000129)       shouldBe "0.00000001"
        formatter(Type.DECIMAL, precision = 2)      (.0000000129)       shouldBe "0.000000013"
        formatter(Type.DECIMAL, precision = 2)      (.00000000129)      shouldBe "0.0000000013"
        formatter(Type.DECIMAL, precision = 3)      (.00000000129)      shouldBe "0.00000000129"
        formatter(Type.DECIMAL, precision = 4)      (.00000000129)      shouldBe "0.000000001290"
        formatter(Type.DECIMAL, precision = 10)     (.9999999999)       shouldBe "0.9999999999"
        formatter(Type.DECIMAL, precision = 15)     (.999999999999999)  shouldBe "0.999999999999999"
    }

    @Test fun format_r_can_round_very_small_numbers () {
        val f = formatter(".2r")
        f(1e-22) shouldBe "0.00000000000000000000010"
    }

}
