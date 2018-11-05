package io.data2viz.math

import io.data2viz.test.TestBase
import kotlin.test.Test

class PercentTests : TestBase() {

    @Test
    fun percentOperators() {
        val percent = 63.pct

        percent shouldBe Percent(63.0 / 100)

        percent.coerceAtLeast(80.pct) shouldBe 80.pct
        percent.coerceAtLeast(20.pct) shouldBe percent

        percent.coerceAtMost(80.pct) shouldBe percent
        percent.coerceAtMost(20.pct) shouldBe 20.pct

        percent.coerceIn(20.pct, 80.pct) shouldBe percent
        percent.coerceIn(0.pct, 20.pct) shouldBe 20.pct
        percent.coerceIn(80.pct, 100.pct) shouldBe 80.pct

        123.pct.coerceToDefault() shouldBe 100.pct
        23.pct.coerceToDefault() shouldBe 23.pct
        (-23.pct).coerceToDefault() shouldBe 0.pct

        percent * 50.pct shouldBe 31.5.pct
        percent * 200.pct shouldBe 126.pct
        percent * -200.pct shouldBe -126.pct

        percent + percent shouldBe 126.pct
        percent - percent shouldBe 0.pct
        percent - 25.pct shouldBe 38.pct
        percent + (-percent) shouldBe 0.pct

        +percent shouldBe percent
        +percent shouldBe 63.pct
        percent shouldBe +63.pct

        -percent shouldBe -63.pct
        percent * -1 shouldBe -63.pct
        -2 * percent shouldBe -126.pct

        percent / 2 shouldBe 31.5.pct
        -percent / .5 shouldBe -126.pct
    }
}
