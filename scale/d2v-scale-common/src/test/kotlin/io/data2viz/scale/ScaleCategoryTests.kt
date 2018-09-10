package io.data2viz.scale

import io.data2viz.color.RgbColor
import io.data2viz.test.TestBase
import kotlin.test.Test

class ScaleCategoryTests : TestBase() {

    @Test
    fun category_10_Int() {
        val scale = scales.colors.category10<Int>()
        scale(0) shouldBe RgbColor(0x1f77b4)
        scale(1) shouldBe RgbColor(0xff7f0e)
        scale(2) shouldBe RgbColor(0x2ca02c)
        scale(3) shouldBe RgbColor(0xd62728)
        scale(4) shouldBe RgbColor(0x9467bd)
        scale(5) shouldBe RgbColor(0x8c564b)
        scale(6) shouldBe RgbColor(0xe377c2)
        scale(7) shouldBe RgbColor(0x7f7f7f)
        scale(8) shouldBe RgbColor(0xbcbd22)
        scale(9) shouldBe RgbColor(0x17becf)
        scale(10) shouldBe scale(0)
        scale(11) shouldBe scale(1)
//        scale(-1) shouldBe scale(9)
    }

    @Test
    fun category_10() {
        val scale = scales.colors.category10<String>()
        scale("a") shouldBe RgbColor(0x1f77b4)
        scale("b") shouldBe RgbColor(0xff7f0e)
        scale("c") shouldBe RgbColor(0x2ca02c)
        scale("d") shouldBe RgbColor(0xd62728)
        scale("e") shouldBe RgbColor(0x9467bd)
        scale("f") shouldBe RgbColor(0x8c564b)
        scale("g") shouldBe RgbColor(0xe377c2)
        scale("h") shouldBe RgbColor(0x7f7f7f)
        scale("i") shouldBe RgbColor(0xbcbd22)
        scale("j") shouldBe RgbColor(0x17becf)

//        scale(-1) shouldBe scale(9)
    }

    // TODO : add more tests

}