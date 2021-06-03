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

package io.data2viz.core

import io.data2viz.math.ticks
import io.data2viz.test.TestBase
import kotlin.test.Test

class TicksTests : TestBase() {

    @Test
    fun ticks_of_01() {
        ticks(0.0, 1.0, 10) shouldBe listOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)
        ticks(0.0, 1.0, 9) shouldBe listOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)
        ticks(0.0, 1.0, 8) shouldBe listOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)
    }

    @Test
    fun ticks_of_02() {
        ticks(0.0, 1.0, 7) shouldBe listOf(0.0, 0.2, 0.4, 0.6, 0.8, 1.0)
        ticks(0.0, 1.0, 6) shouldBe listOf(0.0, 0.2, 0.4, 0.6, 0.8, 1.0)
        ticks(0.0, 1.0, 5) shouldBe listOf(0.0, 0.2, 0.4, 0.6, 0.8, 1.0)
        ticks(0.0, 1.0, 4) shouldBe listOf(0.0, 0.2, 0.4, 0.6, 0.8, 1.0)
    }

    @Test
    fun ticks_of_05() {
        ticks(0.0, 1.0, 3) shouldBe listOf(0.0, 0.5, 1.0)
        ticks(0.0, 1.0, 2) shouldBe listOf(0.0, 0.5, 1.0)
    }

    @Test
    fun no_Ticks() {
        ticks(0.0, 1.0, 1) shouldBe listOf(0.0, 1.0)
    }

    @Test
    fun ticks_of_1() {
        ticks(0.0, 10.0, 10) shouldBe listOf(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0)
        ticks(0.0, 10.0, 9) shouldBe listOf(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0)
        ticks(0.0, 10.0, 8) shouldBe listOf(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0)
    }

    @Test
    fun ticks_of_2() {
        ticks(0.0, 10.0, 7) shouldBe listOf(0.0, 2.0, 4.0, 6.0, 8.0, 10.0)
        ticks(0.0, 10.0, 6) shouldBe listOf(0.0, 2.0, 4.0, 6.0, 8.0, 10.0)
        ticks(0.0, 10.0, 5) shouldBe listOf(0.0, 2.0, 4.0, 6.0, 8.0, 10.0)
        ticks(0.0, 10.0, 4) shouldBe listOf(0.0, 2.0, 4.0, 6.0, 8.0, 10.0)
    }

    @Test
    fun ticks_of_5() {
        ticks(0.0, 10.0, 3) shouldBe listOf(0.0, 5.0, 10.0)
        ticks(0.0, 10.0, 2) shouldBe listOf(0.0, 5.0, 10.0)
    }

    @Test
    fun ticks_of_10() {
        ticks(0.0, 10.0, 1) shouldBe listOf(0.0, 10.0)
    }

    @Test
    fun ticks_from_minus_of_1() {
        ticks(-10.0, 10.0, 10) shouldBe listOf(-10, -8.0, -6.0, -4.0, -2.0, 0.0, 2.0, 4.0, 6.0, 8.0, 10.0)
        ticks(-10.0, 10.0, 9) shouldBe listOf(-10, -8.0, -6.0, -4.0, -2.0, 0.0, 2.0, 4.0, 6.0, 8.0, 10.0)
        ticks(-10.0, 10.0, 8) shouldBe listOf(-10, -8.0, -6.0, -4.0, -2.0, 0.0, 2.0, 4.0, 6.0, 8.0, 10.0)
        ticks(-10.0, 10.0, 7) shouldBe listOf(-10, -8.0, -6.0, -4.0, -2.0, 0.0, 2.0, 4.0, 6.0, 8.0, 10.0)
    }

    @Test
    fun ticks_from_minus_of_5() {
        ticks(-10.0, 10.0, 6) shouldBe listOf(-10, -5.0, 0.0, 5.0, 10.0)
        ticks(-10.0, 10.0, 5) shouldBe listOf(-10, -5.0, 0.0, 5.0, 10.0)
        ticks(-10.0, 10.0, 4) shouldBe listOf(-10, -5.0, 0.0, 5.0, 10.0)
        ticks(-10.0, 10.0, 3) shouldBe listOf(-10, -5.0, 0.0, 5.0, 10.0)
    }

    @Test
    fun ticks_from_minus_of_10() {
        ticks(-10.0, 10.0, 2) shouldBe listOf(-10, 0.0, 10.0)
    }

    @Test
    fun no_ticks_from_minus() {
        ticks(-10.0, 10.0, 1) shouldBe listOf(0.0)
    }

}
