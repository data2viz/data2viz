package io.data2viz.scale

import io.data2viz.color.*
import io.data2viz.test.TestBase
import kotlin.test.Test

class ScaleCategoryTests : TestBase() {

    @Test
    fun category_10_Int() {
        val scale = ScalesChromatic.Discrete.category10<Int>()
        scale(0) shouldBe 0x1f77b4.col
        scale(1) shouldBe 0xff7f0e.col
        scale(2) shouldBe 0x2ca02c.col
        scale(3) shouldBe 0xd62728.col
        scale(4) shouldBe 0x9467bd.col
        scale(5) shouldBe 0x8c564b.col
        scale(6) shouldBe 0xe377c2.col
        scale(7) shouldBe 0x7f7f7f.col
        scale(8) shouldBe 0xbcbd22.col
        scale(9) shouldBe 0x17becf.col
        scale(10) shouldBe scale(0)
        scale(11) shouldBe scale(1)
//        scale(-1) shouldBe scale(9)
    }

    @Test
    fun category_10() {
        val scale = ScalesChromatic.Discrete.category10<String>()
        scale("a") shouldBe 0x1f77b4.col
        scale("b") shouldBe 0xff7f0e.col
        scale("c") shouldBe 0x2ca02c.col
        scale("d") shouldBe 0xd62728.col
        scale("e") shouldBe 0x9467bd.col
        scale("f") shouldBe 0x8c564b.col
        scale("g") shouldBe 0xe377c2.col
        scale("h") shouldBe 0x7f7f7f.col
        scale("i") shouldBe 0xbcbd22.col
        scale("j") shouldBe 0x17becf.col

//        scale(-1) shouldBe scale(9)
    }

    // TODO : add more tests

}