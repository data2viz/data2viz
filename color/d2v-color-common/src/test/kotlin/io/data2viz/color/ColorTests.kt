package io.data2viz.color

import io.data2viz.color.colors.aliceblue
import io.data2viz.color.colors.black
import io.data2viz.color.colors.white
import io.data2viz.math.Angle
import io.data2viz.math.deg
import io.data2viz.test.TestBase
import kotlin.math.absoluteValue
import kotlin.math.round
import kotlin.test.Test

class ColorTests : TestBase() {

// no more default color
//    @Test
//    fun color_defaults() {
//        val color = RgbColor()
//        color.rgb shouldBe 0xffffff
//        color.r shouldBe 255
//        color.g shouldBe 255
//        color.b shouldBe 255
//    }

    @Test
    fun set_r_g_b() {
        var color = RgbColor(0xFFFFFF)
        color = color.withRed(0xab)
        color.rgb shouldBe 0xabffff

        color  = color.withGreen(0xab)
        color.rgb shouldBe 0xababff

        color = color.withBlue(0xab)
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
        colors.rgb(300, -300, 256, -2.5) shouldBe colors.rgb(255, 0, 255, .0)
        colors.rgb(-300, 300, -256, 2.5) shouldBe colors.rgb(0, 255, 0, 1.0)
    }

    /**
     * https://www.w3schools.com/colors/colors_converter.asp)
     */
    @Test
    fun HSLA_to_RGBA_reference() {
        colors.hsl(0.deg, .0, .0).toRgbColor() shouldBe black
        colors.hsl(0.deg, .0, 1.0).toRgbColor() shouldBe white
        colors.hsl(32.deg, 0.80, 0.80, .0).toRgbColor() shouldBe RgbColor(0xf5cfa3, 0.0)
        colors.hsl(260.deg, 0.20, 0.44, 1.0).toRgbColor() shouldBe RgbColor(0x695a87, 1.0)
        colors.hsl(300.deg, 0.98, 0.16, .3).toRgbColor() shouldBe RgbColor(0x510151, .3)
        colors.hsl(16.deg, 0.75, 0.23, .5).toRgbColor() shouldBe RgbColor(0x67260f, .5)
    }

    @Test
    fun RGBA_to_HSLA_rounded() {
        //Color(0xf5cfa3, 0).toHsla() shouldBe hsla(32.deg, 0.80, 0.80, 0)
        val color1 = RgbColor(0xf5cfa3, 0.0).toHsla()
        round(color1.h.deg) shouldBeClose 32.0
        round(color1.s * 100) shouldBeClose 80.0
        round(color1.l * 100) shouldBeClose 80.0
        color1.alpha shouldBe 0.0

//            hsla(260.deg, 0.20, 0.44, 1).toRgba() shouldBe Color(0x695a87, 1)
        val color2 = RgbColor(0x695a87, 1.0).toHsla()
        round(color2.h.deg) shouldBeClose 260.0
        round(color2.s * 100) shouldBeClose 20.0
        round(color2.l * 100) shouldBeClose 44.0
        color2.alpha shouldBe 1.0

//            hsla(300.deg, 0.98, 0.16, .3).toRgba() shouldBe Color(0x510151, .3)
        val color3 = RgbColor(0x510151, .3).toHsla()
        round(color3.h.deg) shouldBeClose 300.0
        round(color3.s * 100) shouldBeClose 98.0
        round(color3.l * 100) shouldBeClose 16.0
        color3.alpha shouldBe .3

//            hsla(16.deg, 0.75, 0.23, .5).toRgba() shouldBe Color(0x67260f, .5)*/
        val color4 = RgbColor(0x67260f, .5).toHsla()
        round(color4.h.deg) shouldBeClose 16.0
        round(color4.s * 100) shouldBeClose 75.0
        round(color4.l * 100) shouldBeClose 23.0
        color4.alpha shouldBe .5

//            hsla(0.deg, 0, 0, .42).toRgba() shouldBe Color(0x6a6a6a, .2)*/
        val color5 = RgbColor(0x6a6a6a, .2).toHsla()
        round(color5.h.deg) shouldBeClose 0.0
        round(color5.s * 100) shouldBeClose 0.0
        round(color5.l * 100) shouldBeClose 42.0
        color5.alpha shouldBe .2
    }

    @Test
    fun RGBA_to_LAB_rounded() {

        val color1 = RgbColor(0xf5cfa3, 0.0).toLab()
        round(color1.labL) shouldBe 85.0
        round(color1.labA) shouldBe 7.0
        round(color1.labB) shouldBe 27.0
        color1.alpha shouldBe 0.0

        val color2 = RgbColor(0x695a87, 1.0).toLab()
        round(color2.labL) shouldBe 41.0
        round(color2.labA) shouldBe 16.0
        round(color2.labB) shouldBe -23.0
        color2.alpha shouldBe 1.0

        val color3 = RgbColor(0x510151, .3).toLab()
        round(color3.labL) shouldBe 17.0
        round(color3.labA) shouldBe 42.0
        round(color3.labB) shouldBe -26.0
        color3.alpha shouldBe .3

        val color4 = RgbColor(0x67260f, .5).toLab()
        round(color4.labL) shouldBe 25.0
        round(color4.labA) shouldBe 28.0
        round(color4.labB) shouldBe 29.0
        color4.alpha shouldBe .5

        val color5 = RgbColor(0x6a6a6a, .2).toLab()
        round(color5.labL) shouldBe 45.0
        round(color5.labA) shouldBe -0.0
        round(color5.labB) shouldBe 0.0
        color5.alpha shouldBe .2

        val color6 = white.toLab()
        round(color6.labL) shouldBe 100.0
        round(color6.labA).absoluteValue shouldBe 0.0
        round(color6.labB) shouldBe 0.0

        val color7 = black.toLab()
        round(color7.labL) shouldBeClose 0.0         // give -0 in JVM
        round(color7.labA) shouldBe 0.0
        round(color7.labB) shouldBe 0.0
    }

    @Test
    fun HSL_REFERENCES_HSL_0_0_0_should_be_black() {
        black.toHsla() shouldBe colors.hsl(0.deg, 0.0, 0.0)
        white.toHsla() shouldBe colors.hsl(0.deg, 0.0, 1.0)

        HslColor(Angle(0.0), 0.0, 0.0).toRgbColor() shouldBe black
        HslColor(Angle(0.0), 0.0, 1.0).toRgbColor() shouldBe white
    }

// no more default color
//    @Test
//    fun Default_HSL_color_should_be_the_same_as_default_RGB_color_white() {
//        val color = HslColor().toRgba()
//        color shouldBe RgbColor()
//        color shouldBe white
//    }

    @Test
    fun RGB_conversion_of_multiples_HSL_colors() {
        HslColor(0.deg, 0.0, 0.742).toRgbColor().rgbHex shouldBe "#bdbdbd"
        HslColor(120.deg, 0.5, 0.5).toRgbColor().rgbHex shouldBe "#40bf40"
        HslColor(180.deg, 0.3, 0.6).toRgbColor().rgbHex shouldBe "#7ab8b8"
        HslColor(63.deg, 0.22, 0.46).toRgbColor().rgbHex shouldBe "#8d8f5b"
        HslColor(32.0.deg, 0.8, 0.8).toRgbColor().rgbHex shouldBe "#f5cfa3"
        HslColor(272.deg, 0.56, 0.67).toRgbColor().rgbHex shouldBe "#ae7cda"
        HslColor(300.deg, 0.2, 0.4).toRgbColor().rgbHex shouldBe "#7a527a"
        HslColor(265.deg, 0.51, 0.42).toRgbColor().rgbHex shouldBe "#6234a2"
        HslColor(300.deg, 1.0, 0.5).toRgbColor().rgbHex shouldBe "#ff00ff"
        HslColor(208.deg, 1.0, 0.9705882).toRgbColor().rgbHex shouldBe aliceblue.rgbHex
    }

    @Test
    fun RGB_conversion_of_HSL_120_05_05_05f_should_be_40BF40_with_05_alpha() {
        val color = HslColor(120.deg, 0.5, 0.5, 0.5).toRgbColor()
        color.rgbHex shouldBe "#40bf40"
        color.alpha shouldBe 0.5f
    }

    @Test
    fun HSL_coercing_under_0_and_over_1_luminance() {
        HslColor(488.deg, .5, .5).toRgbColor() shouldBe HslColor(128.deg, .5, .5).toRgbColor()
        HslColor(120.deg, -.5, .5) shouldBe HslColor(120.deg, .0, .5)
        HslColor(120.deg, .5, 1.5) shouldBe HslColor(120.deg, .5, 1.0)
    }

// no more default color
//    @Test
//    fun LAB_CONVERSION() {
//        lab(100, 0, 0).toRgba() shouldBe RgbColor()
//    }

    @Test
    fun RGB_conversion_of_multiples_LAB_colors() {
        LabColor(76.61, 0.0, .0).toRgbColor().rgbHex shouldBe "#bdbdbd"
        LabColor(68.55, -58.98, 52.11).toRgbColor().rgbHex shouldBe "#40bf40"
        LabColor(70.79, -19.78, -6.34).toRgbColor().rgbHex shouldBe "#7ab8b8"
        LabColor(58.10, -9.19, 27.46).toRgbColor().rgbHex shouldBe "#8d8f5b"
        LabColor(85.34, 7.23, 26.85).toRgbColor().rgbHex shouldBe "#f5cfa3"
        LabColor(60.32, 37.18, -40.92).toRgbColor().rgbHex shouldBe "#ae7cda"
        LabColor(40.54, 23.82, -15.98).toRgbColor().rgbHex shouldBe "#7a527a"
        LabColor(33.27, 43.84, -52.04).toRgbColor().rgbHex shouldBe "#6234a2"
        val color = LabColor(68.54923, -58.98131, 52.11442, 0.5).toRgbColor()
        color.rgbHex shouldBe "#40bf40"
        color.alpha shouldBe 0.5
    }

    @Test
    fun RGB_to_LAB_to_HCL_checks_for_multiple_colors() {
        val color1 = RgbColor(0xf5cfa3, 0.0).toLab().toHcla()
        round(color1.h.deg) shouldBeClose 75.0
        round(color1.c) shouldBeClose 28.0
        round(color1.l) shouldBe 85.0
        color1.alpha shouldBe 0.0

        val color2 = RgbColor(0x695a87, 1.0).toLab().toHcla()
        round(color2.h.deg) shouldBeClose 305.0
        round(color2.c) shouldBeClose 28.0
        round(color2.l) shouldBe 41.0
        color2.alpha shouldBe 1.0

        val color3 = RgbColor(0x510151, .3).toLab().toHcla()
        round(color3.h.deg) shouldBeClose 328.0
        round(color3.c) shouldBeClose 50.0
        round(color3.l) shouldBe 17.0
        color3.alpha shouldBe .3

        val color4 = RgbColor(0x67260f, .5).toLab().toHcla()
        round(color4.h.deg) shouldBeClose 46.0
        round(color4.c) shouldBeClose 40.0
        round(color4.l) shouldBe 25.0
        color4.alpha shouldBe .5

        val color5 = RgbColor(0x6a6a6a, .2).toLab().toHcla()
        //round(color5.h.deg) shouldBe 267                     // achromatic, hue value irrelevant
        round(color5.c) shouldBeClose 0.0
        round(color5.l) shouldBe 45.0
        color5.alpha shouldBe .2

        val color6 = white.toLab().toHcla()
        //round(color6.h.deg) shouldBeClose 267                     // achromatic, hue value irrelevant
        round(color6.c) shouldBeClose 0.0
        round(color6.l) shouldBe 100.0

        val color7 = black.toLab().toHcla()
        //round(color7.h.deg) shouldBe 0                       // achromatic, hue value irrelevant
        round(color7.c) shouldBeClose 0.0
        round(color7.l) shouldBe 0.0
    }

    @Test
    fun HCL_to_LAB_to_RGB_checks_for_multiple_colors() {
        val color1 = HclColor(75.deg, 28.0, 85.0, .0).toLab().toRgbColor()
        color1.rgbHex shouldBe "#f4cea2"
        color1.alpha shouldBe 0.0

        val color2 = HclColor(305.deg, 28.0, 41.0, .0).toLab().toRgbColor()
        color2.rgbHex shouldBe "#685986"
        color2.alpha shouldBe .0

        val color3 = HclColor(328.deg, 50.0, 17.0, .3).toLab().toRgbColor()
        color3.rgbHex shouldBe "#500051"
        color3.alpha shouldBe .3

        val color4 = HclColor(46.deg, 40.0, 25.0, .5).toLab().toRgbColor()
        color4.rgbHex shouldBe "#682710"
        color4.alpha shouldBe .5

        val color5 = HclColor(267.deg, 0.0, 45.0, .5).toLab().toRgbColor()
        color5.rgbHex shouldBe "#6a6a6a"
        color5.alpha shouldBe .5

        val color6 = HclColor(0.deg, 0.0, 100.0).toLab().toRgbColor()
        color6.rgbHex shouldBe "#ffffff"

        val color7 = HclColor(0.deg, 0.0, 0.0).toLab().toRgbColor()
        color7.rgbHex shouldBe "#000000"
    }

    @Test
    fun brighten_RGB() {
        colors.hotpink.brighten().rgbHex shouldBe "#ff9ce6"
        colors.hotpink.brighten(2.0).rgbHex shouldBe "#ffd1ff"
        colors.hotpink.brighten(3.0).rgbHex shouldBe "#ffffff"

        colors.darkseagreen.brighten().rgbHex shouldBe "#c0efbf"
        colors.darkseagreen.brighten(2.0).rgbHex shouldBe "#f3fff2"
        colors.darkseagreen.brighten(3.0).rgbHex shouldBe "#ffffff"

        colors.teal.brighten().rgbHex shouldBe "#4cb0af"
        colors.teal.brighten(2.0).rgbHex shouldBe "#81e2e1"
        colors.teal.brighten(3.0).rgbHex shouldBe "#b5ffff"
    }

    @Test
    fun darken_RGB() {
        colors.hotpink.darken().rgbHex shouldBe "#c93384"
        colors.hotpink.darken(2.0).rgbHex shouldBe "#930058"
        colors.hotpink.darken(2.6).rgbHex shouldBe "#74003f"

        colors.darkseagreen.darken().rgbHex shouldBe "#608c61"
        colors.darkseagreen.darken(2.0).rgbHex shouldBe "#345e37"
        colors.darkseagreen.darken(2.6).rgbHex shouldBe "#1b441f"

        colors.teal.darken().rgbHex shouldBe "#005354"
        colors.teal.darken(2.0).rgbHex shouldBe "#00292b"
        colors.teal.darken(2.6).rgbHex shouldBe "#001715"
    }

    @Test
    fun saturate_RGB() {
        colors.slategray.saturate().rgbHex shouldBe "#4b83ae"
        colors.slategray.saturate(2.0).rgbHex shouldBe "#0087cd"
        colors.slategray.saturate(3.0).rgbHex shouldBe "#008bec"
    }

    @Test
    fun desaturate_RGB() {
        colors.hotpink.desaturate().rgbHex shouldBe "#e77dae"
        colors.hotpink.desaturate(2.0).rgbHex shouldBe "#cd8ca8"
        colors.hotpink.desaturate(3.0).rgbHex shouldBe "#b199a3"
    }
}
