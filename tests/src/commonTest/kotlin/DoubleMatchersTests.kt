package io.data2viz.test

import io.data2viz.test.matchers.Matchers
import kotlin.test.Test

class DoubleMatchersTests : Matchers {
    @Test fun double_should_be_exactly_equals()     { 1.0 shouldBe exactly(1.0) }
    @Test fun double_should_be_plusOrMinus()        { 1.1 shouldBe (1.11 plusOrMinus 0.011) }
    @Test fun listOfNumber()                        { listOf(1.0, 2.0) shouldBe listOf(1.000_000_1, 2.000_000_1) }
}