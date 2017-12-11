package io.data2viz.scale

import io.data2viz.color.Color
import io.data2viz.test.TestBase
import kotlin.test.Test

class ScaleCategoryTests : TestBase() {

    @Test
    fun category_10_LEGACY() {
        val scale = scales.colors.category10<Int>()
        scale(0) shouldBe Color(0x1f77b4)
        scale(1) shouldBe Color(0xff7f0e)
        scale(2) shouldBe Color(0x2ca02c)
        scale(3) shouldBe Color(0xd62728)
        scale(4) shouldBe Color(0x9467bd)
        scale(5) shouldBe Color(0x8c564b)
        scale(6) shouldBe Color(0xe377c2)
        scale(7) shouldBe Color(0x7f7f7f)
        scale(8) shouldBe Color(0xbcbd22)
        scale(9) shouldBe Color(0x17becf)
        scale(10) shouldBe scale(0)
        scale(11) shouldBe scale(1)
//        scale(-1) shouldBe scale(9)
    }

    @Test
    fun category_10() {
        val scale = scales.colors.category10<String>()
        scale("a") shouldBe Color(0x1f77b4)
        scale("b") shouldBe Color(0xff7f0e)
        scale("c") shouldBe Color(0x2ca02c)
        scale("d") shouldBe Color(0xd62728)
        scale("e") shouldBe Color(0x9467bd)
        scale("f") shouldBe Color(0x8c564b)
        scale("g") shouldBe Color(0xe377c2)
        scale("h") shouldBe Color(0x7f7f7f)
        scale("i") shouldBe Color(0xbcbd22)
        scale("j") shouldBe Color(0x17becf)

//        scale(-1) shouldBe scale(9)
    }

    // TODO : add more tests

}