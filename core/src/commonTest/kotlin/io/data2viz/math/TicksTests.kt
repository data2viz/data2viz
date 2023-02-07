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

package io.data2viz.math

import io.data2viz.test.TestBase
import io.data2viz.test.matchers.Matchers
import io.data2viz.test.shouldThrow
import kotlin.math.sqrt
import kotlin.js.JsName
import kotlin.test.Test

@Suppress("FunctionName", "NonAsciiCharacters")
class TicksTests : TestBase() {

    @Test
    @JsName("TicksTests1")
    fun `ticks returns the empty array if any argument is NaN`() {
        ticks(Double.NaN, 1.0, 1) shouldBe listOf()
        ticks(0.0, Double.NaN, 1) shouldBe listOf()
        ticks(Double.NaN, Double.NaN, 1) shouldBe listOf()
    }

    @Test
    @JsName("TicksTests2")
    fun `ticks returns the start value if start == stop`() {
        ticks(1.0, 1.0, -1) shouldBe listOf()
        ticks(1.0, 1.0, 0) shouldBe listOf()
        ticks(1.0, 1.0, 1) shouldBe listOf(1.0)
        ticks(1.0, 1.0, 10) shouldBe listOf(1.0)
    }

    @Test
    @JsName("TicksTests3")
    fun `ticks returns the empty array if count is not positive`() {
        ticks(0.0, 1.0, 0) shouldBe listOf()
        ticks(0.0, 1.0, -1) shouldBe listOf()
    }

    @Test
    @JsName("TicksTests4")
    fun `ticks returns approximately count + 1 ticks when start lt stop`() {
        ticks(  0.0,  1.0, 10) shouldBe listOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)
        ticks(  0.0,  1.0,  9) shouldBe listOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)
        ticks(  0.0,  1.0,  8) shouldBe listOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)
        ticks(  0.0,  1.0,  7) shouldBe listOf(0.0,      0.2,      0.4,      0.6,      0.8,      1.0)
        ticks(  0.0,  1.0,  6) shouldBe listOf(0.0,      0.2,      0.4,      0.6,      0.8,      1.0)
        ticks(  0.0,  1.0,  5) shouldBe listOf(0.0,      0.2,      0.4,      0.6,      0.8,      1.0)
        ticks(  0.0,  1.0,  4) shouldBe listOf(0.0,      0.2,      0.4,      0.6,      0.8,      1.0)
        ticks(  0.0,  1.0,  3) shouldBe listOf(0.0,                     0.5,                     1.0)
        ticks(  0.0,  1.0,  2) shouldBe listOf(0.0,                     0.5,                     1.0)
        ticks(  0.0,  1.0,  1) shouldBe listOf(0.0,                                              1.0)
        ticks(  0.0, 10.0, 10) shouldBe listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        ticks(  0.0, 10.0,  9) shouldBe listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        ticks(  0.0, 10.0,  8) shouldBe listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        ticks(  0.0, 10.0,  7) shouldBe listOf(0,    2,    4,    6,    8,    10)
        ticks(  0.0, 10.0,  6) shouldBe listOf(0,    2,    4,    6,    8,    10)
        ticks(  0.0, 10.0,  5) shouldBe listOf(0,    2,    4,    6,    8,    10)
        ticks(  0.0, 10.0,  4) shouldBe listOf(0,    2,    4,    6,    8,    10)
        ticks(  0.0, 10.0,  3) shouldBe listOf(0,             5,             10)
        ticks(  0.0, 10.0,  2) shouldBe listOf(0,             5,             10)
        ticks(  0.0, 10.0,  1) shouldBe listOf(0,                            10)
        ticks(-10.0, 10.0, 10) shouldBe listOf(-10, -8, -6, -4, -2, 0, 2, 4, 6, 8, 10)
        ticks(-10.0, 10.0,  9) shouldBe listOf(-10, -8, -6, -4, -2, 0, 2, 4, 6, 8, 10)
        ticks(-10.0, 10.0,  8) shouldBe listOf(-10, -8, -6, -4, -2, 0, 2, 4, 6, 8, 10)
        ticks(-10.0, 10.0,  7) shouldBe listOf(-10, -8, -6, -4, -2, 0, 2, 4, 6, 8, 10)
        ticks(-10.0, 10.0,  6) shouldBe listOf(-10,       -5,       0,      5,     10)
        ticks(-10.0, 10.0,  5) shouldBe listOf(-10,       -5,       0,      5,     10)
        ticks(-10.0, 10.0,  4) shouldBe listOf(-10,       -5,       0,      5,     10)
        ticks(-10.0, 10.0,  3) shouldBe listOf(-10,       -5,       0,      5,     10)
        ticks(-10.0, 10.0,  2) shouldBe listOf(-10,                 0,             10)
        ticks(-10.0, 10.0,  1) shouldBe listOf(                     0                )
    }

    @Test
    @JsName("TicksTests5")
    fun `ticks returns the reverse of ticks`() {
        ticks( 1.0,   0.0, 10) shouldBe ticks(  0.0,  1.0, 10).reversed()
        ticks( 1.0,   0.0,  9) shouldBe ticks(  0.0,  1.0,  9).reversed()
        ticks( 1.0,   0.0,  8) shouldBe ticks(  0.0,  1.0,  8).reversed()
        ticks( 1.0,   0.0,  7) shouldBe ticks(  0.0,  1.0,  7).reversed()
        ticks( 1.0,   0.0,  6) shouldBe ticks(  0.0,  1.0,  6).reversed()
        ticks( 1.0,   0.0,  5) shouldBe ticks(  0.0,  1.0,  5).reversed()
        ticks( 1.0,   0.0,  4) shouldBe ticks(  0.0,  1.0,  4).reversed()
        ticks( 1.0,   0.0,  3) shouldBe ticks(  0.0,  1.0,  3).reversed()
        ticks( 1.0,   0.0,  2) shouldBe ticks(  0.0,  1.0,  2).reversed()
        ticks( 1.0,   0.0,  1) shouldBe ticks(  0.0,  1.0,  1).reversed()
        ticks(10.0,   0.0, 10) shouldBe ticks(  0.0, 10.0, 10).reversed()
        ticks(10.0,   0.0,  9) shouldBe ticks(  0.0, 10.0,  9).reversed()
        ticks(10.0,   0.0,  8) shouldBe ticks(  0.0, 10.0,  8).reversed()
        ticks(10.0,   0.0,  7) shouldBe ticks(  0.0, 10.0,  7).reversed()
        ticks(10.0,   0.0,  6) shouldBe ticks(  0.0, 10.0,  6).reversed()
        ticks(10.0,   0.0,  5) shouldBe ticks(  0.0, 10.0,  5).reversed()
        ticks(10.0,   0.0,  4) shouldBe ticks(  0.0, 10.0,  4).reversed()
        ticks(10.0,   0.0,  3) shouldBe ticks(  0.0, 10.0,  3).reversed()
        ticks(10.0,   0.0,  2) shouldBe ticks(  0.0, 10.0,  2).reversed()
        ticks(10.0,   0.0,  1) shouldBe ticks(  0.0, 10.0,  1).reversed()
        ticks(10.0, -10.0, 10) shouldBe ticks(-10.0, 10.0, 10).reversed()
        ticks(10.0, -10.0,  9) shouldBe ticks(-10.0, 10.0,  9).reversed()
        ticks(10.0, -10.0,  8) shouldBe ticks(-10.0, 10.0,  8).reversed()
        ticks(10.0, -10.0,  7) shouldBe ticks(-10.0, 10.0,  7).reversed()
        ticks(10.0, -10.0,  6) shouldBe ticks(-10.0, 10.0,  6).reversed()
        ticks(10.0, -10.0,  5) shouldBe ticks(-10.0, 10.0,  5).reversed()
        ticks(10.0, -10.0,  4) shouldBe ticks(-10.0, 10.0,  4).reversed()
        ticks(10.0, -10.0,  3) shouldBe ticks(-10.0, 10.0,  3).reversed()
        ticks(10.0, -10.0,  2) shouldBe ticks(-10.0, 10.0,  2).reversed()
        ticks(10.0, -10.0,  1) shouldBe ticks(-10.0, 10.0,  1).reversed()
    }

    @Test
    @JsName("TicksTests6")
    fun `Verification of a found bug on library v0_8`() {
        ticks(7.2, 8.6, 7) shouldBe listOf(7.2, 7.4, 7.6, 7.8, 8.0, 8.2, 8.4, 8.6)
    }
}
