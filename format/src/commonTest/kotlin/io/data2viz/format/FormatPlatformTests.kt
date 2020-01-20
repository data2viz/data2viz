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

@file:Suppress("FunctionName", "unused")

package io.data2viz.format

import io.data2viz.test.TestBase
import kotlin.test.Test

class FormatPlatformTests : TestBase() {

    @Test
    fun check_platform_dependent_formatters_toFixed() {
        156333.6845233.toFixed(0) shouldBe "156334"
        156333.6845233.toFixed(4) shouldBe "156333.6845"
        156333.6845233.toFixed(6) shouldBe "156333.684523"

        // TODO this is not right, we can avoid this type of error by using BigDecimal(num.toString) here, we maintain same behavior as D3
        //156333.6845233.toFixed(20) shouldBe "156333.68452330000000000000"
        156333.6845233.toFixed(20) shouldBe "156333.68452330000582151115"


        0.6845233.toFixed(0)    shouldBe "1"
        0.6845233.toFixed(3)    shouldBe "0.685"
        0.6845233.toFixed(4)    shouldBe "0.6845"
        0.6845233.toFixed(6)    shouldBe "0.684523"
        0.6845233.toFixed(12)   shouldBe "0.684523300000"
    }

    // TODO check exceptions (digits < 2...)
    @Test
    fun check_platform_dependent_formatters_toStringDigits() {
        156333.6845233.toStringDigits(10)   shouldBe "156334"
        156333.6845233.toStringDigits(6)    shouldBe "3203434"
        156333.6845233.toStringDigits(3)    shouldBe "21221110011"
        156333.6845233.toStringDigits(2)    shouldBe "100110001010101110"

        156334.0.toStringDigits(10)         shouldBe "156334"
        156334.0.toStringDigits(6)          shouldBe "3203434"
        156334.0.toStringDigits(3)          shouldBe "21221110011"
        156334.0.toStringDigits(2)          shouldBe "100110001010101110"

        // testing big numbers
        1234567890123.0.toStringDigits(10)  shouldBe "1234567890123"
        1e21.toStringDigits(10)             shouldBe "1e+21"
    }

    // TODO check exceptions (digits < 1...)
    @Test
    fun check_platform_dependent_formatters_toPrecision() {
        0.000123.toPrecision(12)        shouldBe "0.000123000000000"
        0.000123.toPrecision(10)        shouldBe "0.0001230000000"
        0.000123.toPrecision(6)         shouldBe "0.000123000"
        0.000123.toPrecision(3)         shouldBe "0.000123"
        0.000123.toPrecision(2)         shouldBe "0.00012"
        0.000123.toPrecision(1)         shouldBe "0.0001"

        156333.6845233.toPrecision(12)  shouldBe "156333.684523"
        156333.6845233.toPrecision(10)  shouldBe "156333.6845"
        156333.6845233.toPrecision(6)   shouldBe "156334"
        156333.6845233.toPrecision(3)   shouldBe "1.56e+5"
        156333.6845233.toPrecision(2)   shouldBe "1.6e+5"
        156333.6845233.toPrecision(1)   shouldBe "2e+5"

        1234.5.toPrecision(1)           shouldBe "1e+3"
        1234.5.toPrecision(2)           shouldBe "1.2e+3"
        1234.5.toPrecision(4)           shouldBe "1235"
        1234.5.toPrecision(8)           shouldBe "1234.5000"
    }

    @Test
    fun check_platform_dependent_formatters_toExponential() {
        1.6845233.toExponential()           shouldBe "1.6845233e+0"
        (-156333.6845233).toExponential()   shouldBe "-1.563336845233e+5"
        156333.6845233.toExponential()      shouldBe "1.563336845233e+5"
        77.1234.toExponential()             shouldBe "7.71234e+1"
        77.0.toExponential()                shouldBe "7.7e+1"
        77.1234.toExponential(2)            shouldBe  "7.71e+1"
        77.1234.toExponential(4)            shouldBe "7.7123e+1"
        77.1234.toExponential(6)            shouldBe "7.712340e+1"
        77.1234.toExponential(8)            shouldBe "7.71234000e+1"
        000077.1234.toExponential()         shouldBe "7.71234e+1"

        1e21.toExponential()                shouldBe "1e+21"

        1.26190788E8.toExponential()        shouldBe "1.26190788e+8"
        1.26190788e8.toExponential()        shouldBe "1.26190788e+8"
        2600.12345E4.toExponential()        shouldBe "2.60012345e+7"
        260.012345E5.toExponential()        shouldBe "2.60012345e+7"
        260.012345e5.toExponential()        shouldBe "2.60012345e+7"
    }
}
