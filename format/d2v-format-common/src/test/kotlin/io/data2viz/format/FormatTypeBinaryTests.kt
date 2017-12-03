package io.data2viz.format

import io.data2viz.test.TestBase
import kotlin.test.Test


class FormatTypeBinaryTests: TestBase() {

    @Test
    fun format_b_binary() {
        formatter("b")(10.0) shouldBe "1010"
    }

    @Test
    fun format_binary_with_prefix() {
        formatter("#b")(10.0) shouldBe "0b1010"
    }

}