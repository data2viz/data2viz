package io.data2viz.color

import io.data2viz.color.colors.aliceblue
import io.data2viz.color.colors.black
import io.data2viz.color.colors.hsla
import io.data2viz.color.colors.lab
import io.data2viz.color.colors.rgba
import io.data2viz.color.colors.white
import io.data2viz.math.Angle
import io.data2viz.math.deg
import io.data2viz.test.TestBase
import kotlin.math.absoluteValue
import kotlin.math.round
import kotlin.test.Test

class ColorTests : TestBase() {

    @Test
    fun color_defaults() {
        val color = Color()
        color.rgb shouldBe 0xffffff
        color.r shouldBe 255
        color.g shouldBe 255
        color.b shouldBe 255
    }

    @Test
    fun set_r_g_b() {
        val color = Color()
        color.r = 0xab
        color.rgb shouldBe 0xabffff
        color.g = 0xab
        color.rgb shouldBe 0xababff
        color.b = 0xab
        color.rgb shouldBe 0xababab
        color.rgbHex shouldBe "#ababab"
    }

    @Test
    fun stringToColor() {
        "#0b0b0b".color.rgb shouldBe 0x0b0b0b
        "#0b0b0b".color.r shouldBe 11
    }

    @Test
    fun color_to_hex_should_be_7_char() {
        black.rgbHex shouldBe "#000000"
    }

    @Test
    fun RGB_coercing_under_0_and_over_255_color_channel() {
        rgba(300, -300, 256.8, -2.5) shouldBe rgba(255, 0, 255, 0)
        rgba(-300, 300, -256.8, 2.5) shouldBe rgba(0, 255, 0, 1)
    }

    /**
     * https://www.w3schools.com/colors/colors_converter.asp)
     */
    @Test
    fun HSLA_to_RGBA_reference() {
        hsla(0.deg, 0, 0).toRgba() shouldBe black
        hsla(0.deg, 0, 1).toRgba() shouldBe white
        hsla(32.deg, 0.80, 0.80, 0).toRgba() shouldBe Color(0xf5cfa3, 0.0f)
        hsla(260.deg, 0.20, 0.44, 1).toRgba() shouldBe Color(0x695a87, 1f)
        hsla(300.deg, 0.98, 0.16, .3).toRgba() shouldBe Color(0x510151, .3f)
        hsla(16.deg, 0.75, 0.23, .5).toRgba() shouldBe Color(0x67260f, .5f)
    }

    @Test
    fun RGBA_to_HSLA_rounded() {
        //Color(0xf5cfa3, 0).toHsla() shouldBe hsla(32.deg, 0.80, 0.80, 0)
        val color1 = Color(0xf5cfa3, 0).toHsla()
        round(color1.h.deg) shouldBe 32.0
        round(color1.s * 100) shouldBe 80.0
        round(color1.l * 100) shouldBe 80.0
        color1.alpha shouldBe 0.0f

//            hsla(260.deg, 0.20, 0.44, 1).toRgba() shouldBe Color(0x695a87, 1)
        val color2 = Color(0x695a87, 1).toHsla()
        round(color2.h.deg) shouldBe 260.0
        round(color2.s * 100) shouldBe 20.0
        round(color2.l * 100) shouldBe 44.0
        color2.alpha shouldBe 1f

//            hsla(300.deg, 0.98, 0.16, .3).toRgba() shouldBe Color(0x510151, .3)
        val color3 = Color(0x510151, .3).toHsla()
        round(color3.h.deg) shouldBe 300.0
        round(color3.s * 100) shouldBe 98.0
        round(color3.l * 100) shouldBe 16.0
        color3.alpha shouldBe .3f

//            hsla(16.deg, 0.75, 0.23, .5).toRgba() shouldBe Color(0x67260f, .5)*/
        val color4 = Color(0x67260f, .5).toHsla()
        round(color4.h.deg) shouldBe 16.0
        round(color4.s * 100) shouldBe 75.0
        round(color4.l * 100) shouldBe 23.0
        color4.alpha shouldBe .5f

//            hsla(0.deg, 0, 0, .42).toRgba() shouldBe Color(0x6a6a6a, .2)*/
        val color5 = Color(0x6a6a6a, .2).toHsla()
        round(color5.h.deg) shouldBe 0.0
        round(color5.s * 100) shouldBe 0.0
        round(color5.l * 100) shouldBe 42.0
        color5.alpha shouldBe .2f
    }

    @Test
    fun RGBA_to_LAB_rounded() {

        val color1 = Color(0xf5cfa3, 0).toLab()
        round(color1.l) shouldBe 85.0f
        round(color1.a) shouldBe 7f
        round(color1.b) shouldBe 27f
        color1.alpha shouldBe 0f

        val color2 = Color(0x695a87, 1).toLab()
        round(color2.l) shouldBe 41f
        round(color2.a) shouldBe 16f
        round(color2.b) shouldBe -23f
        color2.alpha shouldBe 1f

        val color3 = Color(0x510151, .3).toLab()
        round(color3.l) shouldBe 17f
        round(color3.a) shouldBe 42f
        round(color3.b) shouldBe -26f
        color3.alpha shouldBe .3f

        val color4 = Color(0x67260f, .5).toLab()
        round(color4.l) shouldBe 25f
        round(color4.a) shouldBe 28f
        round(color4.b) shouldBe 29f
        color4.alpha shouldBe .5f

        val color5 = Color(0x6a6a6a, .2).toLab()
        round(color5.l) shouldBe 45f
        round(color5.a) shouldBe -0f
        round(color5.b) shouldBe 0f
        color5.alpha shouldBe .2f

        val color6 = white.toLab()
        round(color6.l) shouldBe 100f
        round(color6.a).absoluteValue shouldBe 0f
        round(color6.b) shouldBe 0f

        val color7 = black.toLab()
        round(color7.l) shouldBe 0f
        round(color7.a) shouldBe 0f
        round(color7.b) shouldBe 0f
    }

    @Test
    fun HSL_REFERENCES_HSL_0_0_0_should_be_black() {
        black.toHsla() shouldBe hsla(0.deg, 0, 0)
        white.toHsla() shouldBe hsla(0.deg, 0, 1)

        HSL(Angle(0.0), 0f, 0f).toRgba() shouldBe black
        HSL(Angle(0.0), 0f, 1f).toRgba() shouldBe white
    }

    @Test
    fun Default_HSL_color_should_be_the_same_as_default_RGB_color_white() {
        val color = HSL().toRgba()
        color shouldBe Color()
        color shouldBe white
    }

    @Test
    fun RGB_conversion_of_multiples_HSL_colors() {
        HSL(0.deg, 0, 0.742).toRgba().rgbHex shouldBe "#bdbdbd"
        HSL(120.deg, 0.5, 0.5).toRgba().rgbHex shouldBe "#40bf40"
        HSL(180.deg, 0.3, 0.6).toRgba().rgbHex shouldBe "#7ab8b8"
        HSL(63.deg, 0.22, 0.46).toRgba().rgbHex shouldBe "#8d8f5b"
        HSL(32.0.deg, 0.8, 0.8).toRgba().rgbHex shouldBe "#f5cfa3"
        HSL(272.deg, 0.56, 0.67).toRgba().rgbHex shouldBe "#ae7cda"
        HSL(300.deg, 0.2, 0.4).toRgba().rgbHex shouldBe "#7a527a"
        HSL(265.deg, 0.51, 0.42).toRgba().rgbHex shouldBe "#6234a2"
        HSL(300.deg, 1, 0.5).toRgba().rgbHex shouldBe "#ff00ff"
        HSL(208.deg, 1, 0.9705882).toRgba().rgbHex shouldBe aliceblue.rgbHex
    }

    @Test
    fun RGB_conversion_of_HSL_120_05_05_05f_should_be_40BF40_with_05_alpha() {
        val color = HSL(120.deg, 0.5, 0.5, 0.5).toRgba()
        color.rgbHex shouldBe "#40bf40"
        color.alpha shouldBe 0.5f
    }

    @Test
    fun HSL_coercing_under_0_and_over_1_luminance() {
        HSL(488.deg, .5, .5).toRgba() shouldBe HSL(128.deg, .5, .5).toRgba()
        HSL(120.deg, -.5f, .5f) shouldBe HSL(120.deg, 0f, .5f)
        HSL(120.deg, .5f, 1.5f) shouldBe HSL(120.deg, .5f, 1f)
    }

    /**
     * http://colormine.org/convert/rgb-to-lab : Default LAB color should be the same as default RGB color (white)"
     */
    @Test
    fun LAB_CONVERSION() {
        lab().toRgba() shouldBe Color()
    }

    @Test
    fun RGB_conversion_of_multiples_LAB_colors() {
        LAB(76.61f, 0f, 0f).toRgba().rgbHex shouldBe "#bdbdbd"
        LAB(68.55f, -58.98f, 52.11f).toRgba().rgbHex shouldBe "#40bf40"
        LAB(70.79f, -19.78f, -6.34f).toRgba().rgbHex shouldBe "#7ab8b8"
        LAB(58.10f, -9.19f, 27.46f).toRgba().rgbHex shouldBe "#8d8f5b"
        LAB(85.34f, 7.23f, 26.85f).toRgba().rgbHex shouldBe "#f5cfa3"
        LAB(60.32f, 37.18f, -40.92f).toRgba().rgbHex shouldBe "#ae7cda"
        LAB(40.54f, 23.82f, -15.98f).toRgba().rgbHex shouldBe "#7a527a"
        LAB(33.27f, 43.84f, -52.04f).toRgba().rgbHex shouldBe "#6234a2"
        val color = LAB(68.54923f, -58.98131f, 52.11442f, 0.5f).toRgba()
        color.rgbHex shouldBe "#40bf40"
        color.alpha shouldBe 0.5f
    }

    @Test
    fun RGB_to_LAB_to_HCL_checks_for_multiple_colors() {
        val color1 = Color(0xf5cfa3, 0).toLab().toHcla()
        round(color1.h.deg) shouldBeClose 75.0
        round(color1.c) shouldBeClose  28.0
        round(color1.l) shouldBe 85f
        color1.alpha shouldBe 0.0f

        val color2 = Color(0x695a87, 1).toLab().toHcla()
        round(color2.h.deg) shouldBeClose  305.0
        round(color2.c) shouldBeClose 28.0
        round(color2.l) shouldBe 41.0f
        color2.alpha shouldBe 1.0f

        val color3 = Color(0x510151, .3).toLab().toHcla()
        round(color3.h.deg) shouldBeClose 328.0
        round(color3.c) shouldBeClose 50.0
        round(color3.l) shouldBe 17.0f
        color3.alpha shouldBe .3f

        val color4 = Color(0x67260f, .5).toLab().toHcla()
        round(color4.h.deg) shouldBeClose 46.0
        round(color4.c) shouldBeClose 40.0
        round(color4.l) shouldBe 25.0f
        color4.alpha shouldBe .5f

        val color5 = Color(0x6a6a6a, .2).toLab().toHcla()
        //round(color5.h.deg) shouldBe 267                     // achromatic, hue value irrelevant
        round(color5.c) shouldBeClose 0.0
        round(color5.l) shouldBe 45.0f
        color5.alpha shouldBe .2f

        val color6 = white.toLab().toHcla()
        //round(color6.h.deg) shouldBeClose 267                     // achromatic, hue value irrelevant
        round(color6.c) shouldBeClose 0.0
        round(color6.l) shouldBe 100.0f

        val color7 = black.toLab().toHcla()
        //round(color7.h.deg) shouldBe 0                       // achromatic, hue value irrelevant
        round(color7.c) shouldBe 0.0
        round(color7.l) shouldBe 0.0f
    }
}
