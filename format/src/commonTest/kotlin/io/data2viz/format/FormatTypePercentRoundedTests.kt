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

class FormatTypePercentRoundedTests : TestBase() {

    @Test
    fun format_p_can_output_a_percentage () {
        val f = formatter("p")
        f(.00123)   shouldBe "0.123000%"
        f(.0123)    shouldBe "1.23000%"
        f(.123)     shouldBe "12.3000%"
        f(.234)     shouldBe "23.4000%"
        f(1.23)     shouldBe "123.000%"
        f(-.00123)  shouldBe "-0.123000%"
        f(-.0123)   shouldBe "-1.23000%"
        f(-.123)    shouldBe "-12.3000%"
        f(-1.23)    shouldBe "-123.000%"
    }

    @Test
    fun format_p_typed_can_output_a_percentage () {
        val f = formatter(Type.PERCENT_ROUNDED)
        f(.00123)   shouldBe "0.123000%"
        f(.0123)    shouldBe "1.23000%"
        f(.123)     shouldBe "12.3000%"
        f(.234)     shouldBe "23.4000%"
        f(1.23)     shouldBe "123.000%"
        f(-.00123)  shouldBe "-0.123000%"
        f(-.0123)   shouldBe "-1.23000%"
        f(-.123)    shouldBe "-12.3000%"
        f(-1.23)    shouldBe "-123.000%"
    }

    @Test
    fun format_p_can_output_a_percentage_with_rounding_and_sign () {
        val f = formatter("+.2p")
        f(.00123)   shouldBe "+0.12%"
        f(.0123)    shouldBe "+1.2%"
        f(.123)     shouldBe "+12%"
        f(1.23)     shouldBe "+120%"
        f(-.00123)  shouldBe "-0.12%"
        f(-.0123)   shouldBe "-1.2%"
        f(-.123)    shouldBe "-12%"
        f(-1.23)    shouldBe "-120%"
    }

    @Test fun format_p_typed_can_output_a_percentage_with_rounding_and_sign () {
        val f = formatter(Type.PERCENT_ROUNDED, sign = Sign.PLUS, precision = 2)
        f(.00123)   shouldBe "+0.12%"
        f(.0123)    shouldBe "+1.2%"
        f(.123)     shouldBe "+12%"
        f(1.23)     shouldBe "+120%"
        f(-.00123)  shouldBe "-0.12%"
        f(-.0123)   shouldBe "-1.2%"
        f(-.123)    shouldBe "-12%"
        f(-1.23)    shouldBe "-120%"
    }

}
