@file:Suppress("FunctionName", "unused")

package io.data2viz.format

import io.data2viz.test.TestBase
import kotlin.math.PI
import kotlin.test.Ignore
import kotlin.test.Test

class FormatTypeOctalTests : TestBase() {
  

    @Test fun format_o_octal () {
        formatter("o")(10.0) shouldBe "12"
    }

    @Test fun format_o_octal_with_prefix () {
        formatter("#o")(10.0) shouldBe "0o12"
        formatter(Type.OCTAL, symbol = Symbol.NUMBER_BASE) (10.0) shouldBe "0o12"
    }


 }
