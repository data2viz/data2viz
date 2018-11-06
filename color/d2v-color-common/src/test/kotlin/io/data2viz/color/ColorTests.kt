package io.data2viz.color

import io.data2viz.color.Colors.Web.aliceblue
import io.data2viz.color.Colors.Web.black
import io.data2viz.color.Colors.Web.darkseagreen
import io.data2viz.color.Colors.Web.hotpink
import io.data2viz.color.Colors.Web.slategray
import io.data2viz.color.Colors.Web.teal
import io.data2viz.color.Colors.Web.white
import io.data2viz.geom.Point
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

        color = color.withGreen(0xab)
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
    fun rgba() {
        "#000000".color.rgba shouldBe "rgba(0, 0, 0, 1.0)"
        "#000000".color.withAlpha(.5).rgba shouldBe "rgba(0, 0, 0, 0.5)"
    }

    @Test
    fun color_to_hex_should_be_7_char() {
        black.rgbHex shouldBe "#000000"
    }

    @Test
    fun RGB_coercing_under_0_and_over_255_color_channel() {
        Colors.rgb(300, -300, 256, -2.5) shouldBe Colors.rgb(255, 0, 255, .0)
        Colors.rgb(-300, 300, -256, 2.5) shouldBe Colors.rgb(0, 255, 0, 1.0)
    }

    /**
     * REFERENCE: https://www.w3schools.com/colors/colors_converter.asp)
     */
    @Test
    fun HSLA_to_RGBA_reference() {
        Colors.hsl(0.deg, .0, .0).toRgb() shouldBe black
        Colors.hsl(240.deg, .0, .0).toRgb() shouldBe black
        Colors.hsl(126.deg, .0, .0).toRgb() shouldBe black
        Colors.hsl(6800.deg, .0, .0).toRgb() shouldBe black

        Colors.hsl(0.deg, .0, 1.0).toRgb() shouldBe white
        Colors.hsl(240.deg, .0, 1.0).toRgb() shouldBe white
        Colors.hsl(126.deg, .0, 1.0).toRgb() shouldBe white
        Colors.hsl(680.deg, .0, 1.0).toRgb() shouldBe white

        Colors.hsl(32.deg, 0.80, 0.80, .0).toRgb() shouldBe RgbColor(0xf5cfa3, 0.0)
        Colors.hsl(260.deg, 0.20, 0.44, 1.0).toRgb() shouldBe RgbColor(0x695a87, 1.0)
        Colors.hsl(300.deg, 0.98, 0.16, .3).toRgb() shouldBe RgbColor(0x510151, .3)
        Colors.hsl(16.deg, 0.75, 0.23, .5).toRgb() shouldBe RgbColor(0x67260f, .5)

        Colors.hsl(580.deg, .3, .2).toRgb() shouldBe RgbColor(0x242e42)
        Colors.hsl((-200).deg, .3, .2).toRgb() shouldBe RgbColor(0x244238)
        Colors.hsl((-120).deg, .7, .6).toRgb() shouldBe RgbColor(0x5252e0)
        Colors.hsl(400.deg, .2, .5).toRgb() shouldBe RgbColor(0x998866)
        Colors.hsl(512.deg, 7.0, 2.0).toRgb() shouldBe RgbColor(0xffffff)
        Colors.hsl(124.deg, .1, 2.0).toRgb() shouldBe RgbColor(0xffffff)
    }

    // TODO and check who's right (chroma.js, w3schools converter or data2viz) ?
//    @Test
//    fun HSLA_to_RGBA_reference_over_limits() {
//        Colors.hsl(480.deg, .2, .5).toRgb() shouldBe RgbColor(0x996666)
//        Colors.hsl(700.deg, .2, .5).toRgb() shouldBe RgbColor(0x996666)
//        Colors.hsl(700.deg, .8, .8).toRgb() shouldBe RgbColor(0xf5a3a3)
//    }

    /**
     * REFERENCE: https://gka.github.io/chroma.js/
     */
    @Test
    fun HCL_to_RGB_reference() {
        Colors.hcl(0.deg, .0, .0).toRgb() shouldBe black
        Colors.hcl(240.deg, .0, .0).toRgb() shouldBe black
        Colors.hcl(126.deg, .0, .0).toRgb() shouldBe black
        Colors.hcl(6800.deg, .0, .0).toRgb() shouldBe black

        Colors.hcl(0.deg, .0, 100.0).toRgb() shouldBe white
        Colors.hcl(240.deg, .0, 100.0).toRgb() shouldBe white
        Colors.hcl(126.deg, .0, 100.0).toRgb() shouldBe white
        Colors.hcl(680.deg, .0, 100.0).toRgb() shouldBe white

        Colors.hcl(32.deg, 80.0, 80.0, .0).toRgb() shouldBe RgbColor(0xff897c, 0.0)
        Colors.hcl(260.deg, 20.0, 44.0, 1.0).toRgb() shouldBe RgbColor(0x4b6b88, 1.0)
        Colors.hcl(300.deg, 98.0, 16.0, .3).toRgb() shouldBe RgbColor(0x0014a8, .3)
        Colors.hcl(16.deg, 75.0, 23.0, .5).toRgb() shouldBe RgbColor(0x8f001e, .5)

        Colors.hcl(580.deg, 30.0, 20.0).toRgb() shouldBe RgbColor(0x003a4c)
        Colors.hcl((-200).deg, 30.0, 20.0).toRgb() shouldBe RgbColor(0x003a20)
        Colors.hcl((-120).deg, 70.0, 60.0).toRgb() shouldBe RgbColor(0x00a6fb)
        Colors.hcl(400.deg, 20.0, 50.0).toRgb() shouldBe RgbColor(0x976d62)
        Colors.hcl(512.deg, 70.0, 20.0).toRgb() shouldBe RgbColor(0x003f00)
        Colors.hcl(124.deg, 1.0, 20.0).toRgb() shouldBe RgbColor(0x30312f)

        Colors.hcl(124.deg, -240.0, 50.0).toRgb() shouldBe RgbColor(0x0000ff)
        Colors.hcl(124.deg, -12.0, 50.0).toRgb() shouldBe RgbColor(0x7b7488)

        // first check HCL to LAB
        Colors.hcl(124.deg, 63.0, .0).toLab() shouldBe LabColor(4.4, -9.0, 6.43)
        Colors.hcl(36.deg, 155.0, 20.0).toLab() shouldBe LabColor(39.61, 64.32, 53.97)
        Colors.hcl(124.deg, 63.0, -20.0).toLab() shouldBe LabColor(.0, .0, .0)

        // then check LAB to RGB
        Colors.lab(4.4, -9.0, 6.43).toRgb() shouldBe RgbColor(0x001400)
        Colors.lab(39.61, 64.32, 53.97).toRgb() shouldBe RgbColor(0xbf0000)
        Colors.lab(.0, .0, .0).toRgb() shouldBe RgbColor(0x000000)

        // so HCL to RGB should be ok
        Colors.hcl(124.deg, 63.0, .0).toRgb() shouldBe RgbColor(0x001400)
        Colors.hcl(36.deg, 155.0, 20.0).toRgb() shouldBe RgbColor(0xbf0000)
        Colors.hcl(124.deg, 63.0, -20.0).toRgb() shouldBe RgbColor(0x000000)
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
        black.toHsla() shouldBe Colors.hsl(0.deg, 0.0, 0.0)
        white.toHsla() shouldBe Colors.hsl(0.deg, 0.0, 1.0)

        Colors.hsl(Angle(0.0), 0.0, 0.0).toRgb() shouldBe black
        Colors.hsl(Angle(0.0), 0.0, 1.0).toRgb() shouldBe white
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
        Colors.hsl(0.deg, 0.0, 0.742).toRgb().rgbHex shouldBe "#bdbdbd"
        Colors.hsl(120.deg, 0.5, 0.5).toRgb().rgbHex shouldBe "#40bf40"
        Colors.hsl(180.deg, 0.3, 0.6).toRgb().rgbHex shouldBe "#7ab8b8"
        Colors.hsl(63.deg, 0.22, 0.46).toRgb().rgbHex shouldBe "#8d8f5b"
        Colors.hsl(32.0.deg, 0.8, 0.8).toRgb().rgbHex shouldBe "#f5cfa3"
        Colors.hsl(272.deg, 0.56, 0.67).toRgb().rgbHex shouldBe "#ae7cda"
        Colors.hsl(300.deg, 0.2, 0.4).toRgb().rgbHex shouldBe "#7a527a"
        Colors.hsl(265.deg, 0.51, 0.42).toRgb().rgbHex shouldBe "#6234a2"
        Colors.hsl(300.deg, 1.0, 0.5).toRgb().rgbHex shouldBe "#ff00ff"
        Colors.hsl(208.deg, 1.0, 0.9705882).toRgb().rgbHex shouldBe aliceblue.rgbHex
    }

    @Test
    fun RGB_conversion_of_HSL_120_05_05_05f_should_be_40BF40_with_05_alpha() {
        val color = Colors.hsl(120.deg, 0.5, 0.5, 0.5).toRgb()
        color.rgbHex shouldBe "#40bf40"
        color.alpha shouldBe 0.5
    }

    @Test
    fun HSL_coercing_under_0_and_over_1_luminance() {
        Colors.hsl(488.deg, .5, .5).toRgb() shouldBe Colors.hsl(128.deg, .5, .5).toRgb()
        Colors.hsl(120.deg, -.5, .5) shouldBe Colors.hsl(120.deg, .0, .5)
        Colors.hsl(120.deg, .5, 1.5) shouldBe Colors.hsl(120.deg, .5, 1.0)
    }

// no more default color
//    @Test
//    fun LAB_CONVERSION() {
//        lab(100, 0, 0).toRgba() shouldBe RgbColor()
//    }

    @Test
    fun RGB_conversion_of_multiples_LAB_colors() {
        Colors.lab(76.61, 0.0, .0).toRgb().rgbHex shouldBe "#bdbdbd"
        Colors.lab(68.55, -58.98, 52.11).toRgb().rgbHex shouldBe "#40bf40"
        Colors.lab(70.79, -19.78, -6.34).toRgb().rgbHex shouldBe "#7ab8b8"
        Colors.lab(58.10, -9.19, 27.46).toRgb().rgbHex shouldBe "#8d8f5b"
        Colors.lab(85.34, 7.23, 26.85).toRgb().rgbHex shouldBe "#f5cfa3"
        Colors.lab(60.32, 37.18, -40.92).toRgb().rgbHex shouldBe "#ae7cda"
        Colors.lab(40.54, 23.82, -15.98).toRgb().rgbHex shouldBe "#7a527a"
        Colors.lab(33.27, 43.84, -52.04).toRgb().rgbHex shouldBe "#6234a2"
        val color = Colors.lab(68.54923, -58.98131, 52.11442, 0.5).toRgb()
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
        val color1 = Colors.hcl(75.deg, 28.0, 85.0, .0).toLab().toRgb()
        color1.rgbHex shouldBe "#f4cea2"
        color1.alpha shouldBe 0.0

        val color2 = Colors.hcl(305.deg, 28.0, 41.0, .0).toLab().toRgb()
        color2.rgbHex shouldBe "#685986"
        color2.alpha shouldBe .0

        val color3 = Colors.hcl(328.deg, 50.0, 17.0, .3).toLab().toRgb()
        color3.rgbHex shouldBe "#500051"
        color3.alpha shouldBe .3

        val color4 = Colors.hcl(46.deg, 40.0, 25.0, .5).toLab().toRgb()
        color4.rgbHex shouldBe "#682710"
        color4.alpha shouldBe .5

        val color5 = Colors.hcl(267.deg, 0.0, 45.0, .5).toLab().toRgb()
        color5.rgbHex shouldBe "#6a6a6a"
        color5.alpha shouldBe .5

        val color6 = Colors.hcl(0.deg, 0.0, 100.0).toLab().toRgb()
        color6.rgbHex shouldBe "#ffffff"

        val color7 = Colors.hcl(0.deg, 0.0, 0.0).toLab().toRgb()
        color7.rgbHex shouldBe "#000000"
    }

    @Test
    fun brighten_RGB() {
        hotpink.brighten().rgbHex shouldBe "#ff9ce6"
        hotpink.brighten(2.0).rgbHex shouldBe "#ffd1ff"
        hotpink.brighten(3.0).rgbHex shouldBe "#ffffff"

        darkseagreen.brighten().rgbHex shouldBe "#c0efbf"
        darkseagreen.brighten(2.0).rgbHex shouldBe "#f3fff2"
        darkseagreen.brighten(3.0).rgbHex shouldBe "#ffffff"

        teal.brighten().rgbHex shouldBe "#4cb0af"
        teal.brighten(2.0).rgbHex shouldBe "#81e2e1"
        teal.brighten(3.0).rgbHex shouldBe "#b5ffff"
    }

    @Test
    fun darken_RGB() {
        hotpink.darken().rgbHex shouldBe "#c93384"
        hotpink.darken(2.0).rgbHex shouldBe "#930058"
        hotpink.darken(2.6).rgbHex shouldBe "#74003f"

        darkseagreen.darken().rgbHex shouldBe "#608c61"
        darkseagreen.darken(2.0).rgbHex shouldBe "#345e37"
        darkseagreen.darken(2.6).rgbHex shouldBe "#1b441f"

        teal.darken().rgbHex shouldBe "#005354"
        teal.darken(2.0).rgbHex shouldBe "#00292b"
        teal.darken(2.6).rgbHex shouldBe "#001715"
    }

    @Test
    fun saturate_RGB() {
        slategray.saturate().rgbHex shouldBe "#4b83ae"
        slategray.saturate(2.0).rgbHex shouldBe "#0087cd"
        slategray.saturate(3.0).rgbHex shouldBe "#008bec"
    }

    @Test
    fun desaturate_RGB() {
        hotpink.desaturate().rgbHex shouldBe "#e77dae"
        hotpink.desaturate(2.0).rgbHex shouldBe "#cd8ca8"
        hotpink.desaturate(3.0).rgbHex shouldBe "#b199a3"
    }

    @Test
    fun linear_gradient_builder() {
        val builder = Colors.Gradient.linear(Point(.0, .0), Point(100.0, .0))

        val gradient = builder.withColor(aliceblue, .0).andColor(darkseagreen, .6)
        gradient.colorStops.size shouldBe 2

        gradient.andColor(hotpink, 1.0).colorStops.size shouldBe 3
    }

    @Test
    fun radial_gradient_builder() {
        val builder = Colors.Gradient.radial(Point(.0, .0), 100.0)

        val gradient = builder.withColor(aliceblue, .0).andColor(darkseagreen, .6)
        gradient.colorStops.size shouldBe 2

        gradient.andColor(hotpink, 1.0).colorStops.size shouldBe 3
    }
}
