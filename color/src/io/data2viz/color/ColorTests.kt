package io.data2viz.color

import io.data2viz.color.colors.black
import io.data2viz.test.StringSpec

class ColorTests: StringSpec(){

    init {
        "io.data2viz.color.getColor defaults" {
            val color = Color()
            color.rgb shouldBe 0xffffff
            color.r shouldBe 255
            color.g shouldBe 255
            color.b shouldBe 255
        }

        "set r, g, b"{
            val color = Color()
            color.r = 0xab
            color.rgb shouldBe 0xabffff
            color.g = 0xab
            color.rgb shouldBe 0xababff
            color.b = 0xab
            color.rgb shouldBe 0xababab
            color.rgbHex shouldBe "#ababab"
        }

        "string to io.data2viz.color.getColor" {
            "#0b0b0b".color.rgb shouldBe 0x0b0b0b
            "#0b0b0b".color.r shouldBe 11
        }

        "color to hex should be 7 char" {
            black.rgbHex shouldBe "#000000"
        }
    }

}
