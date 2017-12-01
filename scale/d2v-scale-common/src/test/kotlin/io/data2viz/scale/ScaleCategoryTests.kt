package io.data2viz.scale

import io.data2viz.color.Color
import io.data2viz.test.TestBase
import kotlin.test.Test

class ScaleCategoryTests : TestBase() {

    @Test
    fun category_10_LEGACY() {
        val scale = scaleCategory10<Int>()
        scale.range shouldBe listOf(Color(0x1f77b4), Color(0xff7f0e), Color(0x2ca02c), Color(0xd62728), Color(0x9467bd), Color(0x8c564b), Color(0xe377c2), Color(0x7f7f7f), Color(0xbcbd22), Color(0x17becf))
    }

    // TODO : add more tests

}