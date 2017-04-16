import EncodedGradient.Companion.inferno
import EncodedGradient.Companion.magma
import EncodedGradient.Companion.plasma
import EncodedGradient.Companion.viridis
import test.StringSpec
import test.matchers.be
import kotlin.browser.document

class ColorTests: StringSpec(){

    init {
        "color defaults" {
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

        "string to color" {
//            "#000".color
        }
    }

}
