package io.data2viz.color

import io.data2viz.test.TestBase
import kotlin.test.Test

class ColorJfxTests : TestBase() {

    @Test
    fun convert() {
        val color = colors.orange
        val jfxColor = color.jfxColor
        jfxColor.d2vColor shouldBe color
    }

}
