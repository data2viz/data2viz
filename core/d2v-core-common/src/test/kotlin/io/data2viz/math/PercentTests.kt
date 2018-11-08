package io.data2viz.math

import io.data2viz.test.TestBase
import kotlin.test.Test

class PercentTests : TestBase() {

    @Test
    fun percentOperators() {

        63.pct shouldBe Percent(63.0 / 100)
        (63.pct == 63.pct) shouldBe true
        (63.pct == 52.pct) shouldBe false
        (63.pct != 52.pct) shouldBe true

        63.pct.coerceAtLeast(80.pct) shouldBe 80.pct
        63.pct.coerceAtLeast(20.pct) shouldBe 63.pct

        63.pct.coerceAtMost(80.pct) shouldBe 63.pct
        63.pct.coerceAtMost(20.pct) shouldBe 20.pct

        63.pct.coerceIn(20.pct, 80.pct) shouldBe 63.pct
        63.pct.coerceIn(0.pct, 20.pct) shouldBe 20.pct
        63.pct.coerceIn(80.pct, 100.pct) shouldBe 80.pct

        123.pct.coerceToDefault() shouldBe 100.pct
        23.pct.coerceToDefault() shouldBe 23.pct
        (-23.pct).coerceToDefault() shouldBe 0.pct

        63.pct * 50.pct shouldBe 31.5.pct
        63.pct * 200.pct shouldBe 126.pct
        63.pct * -200.pct shouldBe -126.pct

        63.pct + 63.pct shouldBe 126.pct
        63.pct - 63.pct shouldBe 0.pct
        63.pct - 25.pct shouldBe 38.pct
        63.pct + (-63.pct) shouldBe 0.pct

        +63.pct shouldBe 63.pct
        +63.pct shouldBe 63.pct
        63.pct shouldBe +63.pct

        -63.pct shouldBe -63.pct
        63.pct * -1 shouldBe -63.pct
        -2 * 63.pct shouldBe -126.pct

        63.pct / 2 shouldBe 31.5.pct
        -63.pct / .5 shouldBe -126.pct
    }
}
