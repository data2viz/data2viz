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
