/*
 * Copyright (c) 2018-2021. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

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
import io.data2viz.math.pct
import io.data2viz.test.TestBase
import kotlin.math.absoluteValue
import kotlin.math.round
import kotlin.test.Test

class ColorTests : TestBase() {

    @Test
    fun rgba() {
        listOf("rgba(0, 0, 0, 1.0)", "rgba(0, 0, 0, 1)") should contain("#000000".col.rgba)
        "#000000".col.withAlpha(50.pct).rgba shouldBe "rgba(0, 0, 0, 0.5)"
    }

    @Test
    fun set_r_g_b() {
        var color = Colors.rgb(0xFFFFFF)
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
        "#0b0b0b".col.rgb shouldBe 0x0b0b0b
        "#0b0b0b".col.r shouldBe 11
    }

    @Test
    fun color_to_hex_should_be_7_char() {
        black.rgbHex shouldBe "#000000"
    }

    @Test
    fun RGB_coercing_under_0_and_over_255_color_channel() {
        Colors.rgb(300, -300, 256, (-250).pct) shouldBe Colors.rgb(255, 0, 255, 0.pct)
        Colors.rgb(-300, 300, -256, 250.pct) shouldBe Colors.rgb(0, 255, 0, 100.pct)
    }

    /**
     * REFERENCE: https://www.w3schools.com/colors/colors_converter.asp)
     */
    @Test
    fun HSLA_to_RGBA_reference() {
        Colors.hsl(0.deg, 0.pct, 0.pct).toRgb() shouldBe black
        Colors.hsl(240.deg, 0.pct, 0.pct).toRgb() shouldBe black
        Colors.hsl(126.deg, 0.pct, 0.pct).toRgb() shouldBe black
        Colors.hsl(6800.deg, 0.pct, 0.pct).toRgb() shouldBe black

        Colors.hsl(0.deg, 0.pct, 100.pct).toRgb() shouldBe white
        Colors.hsl(240.deg, 0.pct, 100.pct).toRgb() shouldBe white
        Colors.hsl(126.deg, 0.pct, 100.pct).toRgb() shouldBe white
        Colors.hsl(680.deg, 0.pct, 100.pct).toRgb() shouldBe white

        Colors.hsl(32.deg, 80.pct, 80.pct, 0.pct).toRgb() shouldBe Colors.rgb(0xf5cfa3, 0.pct)
        Colors.hsl(260.deg, 20.pct, 44.pct, 100.pct).toRgb() shouldBe Colors.rgb(0x695a87, 100.pct)
        Colors.hsl(300.deg, 98.pct, 16.pct, 30.pct).toRgb() shouldBe Colors.rgb(0x510151, 30.pct)
        Colors.hsl(16.deg, 75.pct, 23.pct, 50.pct).toRgb() shouldBe Colors.rgb(0x67260f, 50.pct)

        Colors.hsl(580.deg, 30.pct, 20.pct).toRgb() shouldBe Colors.rgb(0x242e42)
        Colors.hsl((-200).deg, 30.pct, 20.pct).toRgb() shouldBe Colors.rgb(0x244238)
        Colors.hsl((-120).deg, 70.pct, 60.pct).toRgb() shouldBe Colors.rgb(0x5252e0)
        Colors.hsl(400.deg, 20.pct, 50.pct).toRgb() shouldBe Colors.rgb(0x998866)
        Colors.hsl(512.deg, 700.pct, 200.pct).toRgb() shouldBe Colors.rgb(0xffffff)
        Colors.hsl(124.deg, 10.pct, 200.pct).toRgb() shouldBe Colors.rgb(0xffffff)
    }

    // TODO and check who's right (chroma.js, w3schools converter or data2viz) ?
//    @Test
//    fun HSLA_to_RGBA_reference_over_limits() {
//        Colors.hsl(480.deg, .2, .5).toRgb() shouldBe Colors.rgb(0x996666)
//        Colors.hsl(700.deg, .2, .5).toRgb() shouldBe Colors.rgb(0x996666)
//        Colors.hsl(700.deg, .8, .8).toRgb() shouldBe Colors.rgb(0xf5a3a3)
//    }

    /**
     * REFERENCE: https://gka.github.io/chroma.js/
     */
    @Test
    fun HCL_to_RGB_reference() {
        Colors.hcl(0.deg, .0, .0.pct).toRgb() shouldBe black
        Colors.hcl(240.deg, .0, .0.pct).toRgb() shouldBe black
        Colors.hcl(126.deg, .0, .0.pct).toRgb() shouldBe black
        Colors.hcl(6800.deg, .0, .0.pct).toRgb() shouldBe black

        Colors.hcl(0.deg, .0, 100.0.pct).toRgb() shouldBe white
        Colors.hcl(240.deg, .0, 100.0.pct).toRgb() shouldBe white
        Colors.hcl(126.deg, .0, 100.0.pct).toRgb() shouldBe white
        Colors.hcl(680.deg, .0, 100.0.pct).toRgb() shouldBe white

        Colors.hcl(32.deg, 80.0, 80.0.pct, 0.pct).toRgb() shouldBe Colors.rgb(0xff897c, 0.pct)
        Colors.hcl(260.deg, 20.0, 44.0.pct, 100.pct).toRgb() shouldBe Colors.rgb(0x4b6b88, 100.pct)
        Colors.hcl(300.deg, 98.0, 16.0.pct, 30.pct).toRgb() shouldBe Colors.rgb(0x0014a8, 30.pct)
        Colors.hcl(16.deg, 75.0, 23.0.pct, 50.pct).toRgb() shouldBe Colors.rgb(0x8f001e, 50.pct)

        Colors.hcl(580.deg, 30.0, 20.0.pct).toRgb() shouldBe Colors.rgb(0x003a4c)
        Colors.hcl((-200).deg, 30.0, 20.0.pct).toRgb() shouldBe Colors.rgb(0x003a20)
        Colors.hcl((-120).deg, 70.0, 60.0.pct).toRgb() shouldBe Colors.rgb(0x00a6fb)
        Colors.hcl(400.deg, 20.0, 50.0.pct).toRgb() shouldBe Colors.rgb(0x976d62)
        Colors.hcl(512.deg, 70.0, 20.0.pct).toRgb() shouldBe Colors.rgb(0x003f00)
        Colors.hcl(124.deg, 1.0, 20.0.pct).toRgb() shouldBe Colors.rgb(0x30312f)

        Colors.hcl(124.deg, -240.0, 50.0.pct).toRgb() shouldBe Colors.rgb(0x0000ff)
        Colors.hcl(124.deg, -12.0, 50.0.pct).toRgb() shouldBe Colors.rgb(0x7b7488)

        // first check HCL to LAB
        Colors.hcl(124.deg, 63.0, .0.pct).toLab() shouldBe Colors.lab(4.4.pct, -9.0, 6.43)
        Colors.hcl(36.deg, 155.0, 20.0.pct).toLab() shouldBe Colors.lab(39.61.pct, 64.32, 53.97)
        Colors.hcl(124.deg, 63.0, -20.0.pct).toLab() shouldBe Colors.lab(.0.pct, .0, .0)

        // then check LAB to RGB
        Colors.lab(4.4.pct, -9.0, 6.43).toRgb() shouldBe Colors.rgb(0x001400)
        Colors.lab(39.61.pct, 64.32, 53.97).toRgb() shouldBe Colors.rgb(0xbf0000)
        Colors.lab(.0.pct, .0, .0).toRgb() shouldBe Colors.rgb(0x000000)

        // so HCL to RGB should be ok
        Colors.hcl(124.deg, 63.0, 0.pct).toRgb() shouldBe Colors.rgb(0x001400)
        Colors.hcl(36.deg, 155.0, 20.pct).toRgb() shouldBe Colors.rgb(0xbf0000)
        Colors.hcl(124.deg, 63.0, -20.pct).toRgb() shouldBe Colors.rgb(0x000000)
    }

    @Test
    fun RGBA_to_HSLA_rounded() {
        //Color(0xf5cfa3, 0).toHsl() shouldBe hsla(32.deg, 0.80, 0.80, 0)
        val color1 = Colors.rgb(0xf5cfa3, 0.pct).toHsl()
        round(color1.h.deg) shouldBeClose 32.0
        round(color1.s.value * 100) shouldBeClose 80.0
        round(color1.l.value * 100) shouldBeClose 80.0
        color1.alpha shouldBe 0.pct

//            hsla(260.deg, 0.20, 0.44, 1).toRgba() shouldBe Color(0x695a87, 1)
        val color2 = Colors.rgb(0x695a87, 100.pct).toHsl()
        round(color2.h.deg) shouldBeClose 260.0
        round(color2.s.value * 100) shouldBeClose 20.0
        round(color2.l.value * 100) shouldBeClose 44.0
        color2.alpha shouldBe 100.pct

//            hsla(300.deg, 0.98, 0.16, .3).toRgba() shouldBe Color(0x510151, .3)
        val color3 = Colors.rgb(0x510151, 30.pct).toHsl()
        round(color3.h.deg) shouldBeClose 300.0
        round(color3.s.value * 100) shouldBeClose 98.0
        round(color3.l.value * 100) shouldBeClose 16.0
        color3.alpha shouldBe 30.pct

//            hsla(16.deg, 0.75, 0.23, .5).toRgba() shouldBe Color(0x67260f, .5)*/
        val color4 = Colors.rgb(0x67260f, 50.pct).toHsl()
        round(color4.h.deg) shouldBeClose 16.0
        round(color4.s.value * 100) shouldBeClose 75.0
        round(color4.l.value * 100) shouldBeClose 23.0
        color4.alpha shouldBe 50.pct

//            hsla(0.deg, 0, 0, .42).toRgba() shouldBe Color(0x6a6a6a, .2)*/
        val color5 = Colors.rgb(0x6a6a6a, 20.pct).toHsl()
        round(color5.h.deg) shouldBeClose 0.0
        round(color5.s.value * 100) shouldBeClose 0.0
        round(color5.l.value * 100) shouldBeClose 42.0
        color5.alpha shouldBe 20.pct
    }

    @Test
    fun RGBA_to_LAB_rounded() {

        val color1 = Colors.rgb(0xf5cfa3, 0.pct).toLab()
        round(color1.labL.value * 100) shouldBe 85.0
        round(color1.labA) shouldBe 7.0
        round(color1.labB) shouldBe 27.0
        color1.alpha shouldBe 0.pct

        val color2 = Colors.rgb(0x695a87, 100.pct).toLab()
        round(color2.labL.value * 100) shouldBe 41.0
        round(color2.labA) shouldBe 16.0
        round(color2.labB) shouldBe -23.0
        color2.alpha shouldBe 100.pct

        val color3 = Colors.rgb(0x510151, 30.pct).toLab()
        round(color3.labL.value * 100) shouldBe 17.0
        round(color3.labA) shouldBe 42.0
        round(color3.labB) shouldBe -26.0
        color3.alpha shouldBe 30.pct

        val color4 = Colors.rgb(0x67260f, 50.pct).toLab()
        round(color4.labL.value * 100) shouldBe 25.0
        round(color4.labA) shouldBe 28.0
        round(color4.labB) shouldBe 29.0
        color4.alpha shouldBe 50.pct

        val color5 = Colors.rgb(0x6a6a6a, 20.pct).toLab()
        round(color5.labL.value * 100) shouldBe 45.0
        round(color5.labA) shouldBe -0.0
        round(color5.labB) shouldBe 0.0
        color5.alpha shouldBe 20.pct

        val color6 = white.toLab()
        round(color6.labL.value * 100) shouldBe 100.0
        round(color6.labA).absoluteValue shouldBe 0.0
        round(color6.labB) shouldBe 0.0

        val color7 = black.toLab()
        round(color7.labL.value * 100) shouldBeClose 0.0         // give -0 in JVM
        round(color7.labA) shouldBe 0.0
        round(color7.labB) shouldBe 0.0
    }

    @Test
    fun HSL_REFERENCES_HSL_0_0_0_should_be_black() {
        black.toHsl() shouldBe Colors.hsl(0.deg, 0.pct, 0.pct)
        white.toHsl() shouldBe Colors.hsl(0.deg, 0.pct, 100.pct)

        Colors.hsl(Angle(0.0), 0.pct, 0.pct).toRgb() shouldBe black
        Colors.hsl(Angle(0.0), 0.pct, 100.pct).toRgb() shouldBe white
    }

// no more default color
//    @Test
//    fun Default_HSL_color_should_be_the_same_as_default_RGB_color_white() {
//        val color = HslColor().toRgba()
//        color shouldBe Colors.rgb()
//        color shouldBe white
//    }

    @Test
    fun RGB_conversion_of_multiples_HSL_colors() {
        Colors.hsl(0.deg, 0.pct, 74.2.pct).toRgb().rgbHex shouldBe "#bdbdbd"
        Colors.hsl(120.deg, 50.pct, 50.pct).toRgb().rgbHex shouldBe "#40bf40"
        Colors.hsl(180.deg, 30.pct, 60.pct).toRgb().rgbHex shouldBe "#7ab8b8"
        Colors.hsl(63.deg, 22.pct, 46.pct).toRgb().rgbHex shouldBe "#8d8f5b"
        Colors.hsl(32.0.deg, 80.pct, 80.pct).toRgb().rgbHex shouldBe "#f5cfa3"
        Colors.hsl(272.deg, 56.pct, 67.pct).toRgb().rgbHex shouldBe "#ae7cda"
        Colors.hsl(300.deg, 20.pct, 40.pct).toRgb().rgbHex shouldBe "#7a527a"
        Colors.hsl(265.deg, 51.pct, 42.pct).toRgb().rgbHex shouldBe "#6234a2"
        Colors.hsl(300.deg, 100.pct, 50.pct).toRgb().rgbHex shouldBe "#ff00ff"
        Colors.hsl(208.deg, 100.pct, 97.05882.pct).toRgb().rgbHex shouldBe aliceblue.rgbHex
    }

    @Test
    fun RGB_conversion_of_HSL_120_05_05_05f_should_be_40BF40_with_05_alpha() {
        val color = Colors.hsl(120.deg, 50.pct, 50.pct, 50.pct).toRgb()
        color.rgbHex shouldBe "#40bf40"
        color.alpha shouldBe 50.pct
    }

    @Test
    fun HSL_coercing_under_0_and_over_1_luminance() {
        Colors.hsl(488.deg, 50.pct, 50.pct).toRgb() shouldBe Colors.hsl(128.deg, 50.pct, 50.pct).toRgb()
        Colors.hsl(120.deg, -50.pct, 50.pct) shouldBe Colors.hsl(120.deg, 0.pct, 50.pct)
        Colors.hsl(120.deg, 50.pct, 150.pct) shouldBe Colors.hsl(120.deg, 50.pct, 100.pct)
    }

// no more default color
//    @Test
//    fun LAB_CONVERSION() {
//        lab(100, 0, 0).toRgba() shouldBe Colors.rgb()
//    }

    @Test
    fun RGB_conversion_of_multiples_LAB_colors() {
        Colors.lab(76.61.pct, 0.0, .0).toRgb().rgbHex shouldBe "#bdbdbd"
        Colors.lab(68.55.pct, -58.98, 52.11).toRgb().rgbHex shouldBe "#40bf40"
        Colors.lab(70.79.pct, -19.78, -6.34).toRgb().rgbHex shouldBe "#7ab8b8"
        Colors.lab(58.10.pct, -9.19, 27.46).toRgb().rgbHex shouldBe "#8d8f5b"
        Colors.lab(85.34.pct, 7.23, 26.85).toRgb().rgbHex shouldBe "#f5cfa3"
        Colors.lab(60.32.pct, 37.18, -40.92).toRgb().rgbHex shouldBe "#ae7cda"
        Colors.lab(40.54.pct, 23.82, -15.98).toRgb().rgbHex shouldBe "#7a527a"
        Colors.lab(33.27.pct, 43.84, -52.04).toRgb().rgbHex shouldBe "#6234a2"
        val color = Colors.lab(68.54923.pct, -58.98131, 52.11442, 50.pct).toRgb()
        color.rgbHex shouldBe "#40bf40"
        color.alpha shouldBe 50.pct
    }

    // TODO do not round, use shouldBeClose + more precise values
    @Test
    fun RGB_to_LAB_to_HCL_checks_for_multiple_colors() {
        val color1 = Colors.rgb(0xf5cfa3, 0.pct).toLab().toHcl()
        round(color1.h.deg) shouldBeClose 75.0
        round(color1.c) shouldBeClose 28.0
        round(color1.l.value * 100) shouldBe 85.0
        color1.alpha shouldBe 0.pct

        val color2 = Colors.rgb(0x695a87, 100.pct).toLab().toHcl()
        round(color2.h.deg) shouldBeClose 305.0
        round(color2.c) shouldBeClose 28.0
        round(color2.l.value * 100) shouldBe 41.0
        color2.alpha shouldBe 100.pct

        val color3 = Colors.rgb(0x510151, 50.pct).toLab().toHcl()
        round(color3.h.deg) shouldBeClose 328.0
        round(color3.c) shouldBeClose 50.0
        round(color3.l.value * 100) shouldBe 17.0
        color3.alpha shouldBe 50.pct

        val color4 = Colors.rgb(0x67260f, 30.pct).toLab().toHcl()
        round(color4.h.deg) shouldBeClose 46.0
        round(color4.c) shouldBeClose 40.0
        round(color4.l.value * 100) shouldBe 25.0
        color4.alpha shouldBe 30.pct

        val color5 = Colors.rgb(0x6a6a6a, 20.pct).toLab().toHcl()
        //round(color5.h.deg) shouldBe 267                              // achromatic, hue value irrelevant
        round(color5.c) shouldBeClose 0.0
        round(color5.l.value * 100) shouldBe 45.0
        color5.alpha shouldBe 20.pct

        val color6 = white.toLab().toHcl()
        //round(color6.h.deg) shouldBeClose 267                         // achromatic, hue value irrelevant
        round(color6.c) shouldBeClose 0.0
        round(color6.l.value * 100) shouldBe 100.0

        val color7 = black.toLab().toHcl()
        //round(color7.h.deg) shouldBe 0                                // achromatic, hue value irrelevant
        round(color7.c) shouldBeClose 0.0
        round(color7.l.value * 100) shouldBe 0.0
    }

    @Test
    fun HCL_to_LAB_to_RGB_checks_for_multiple_colors() {
        val color1 = Colors.hcl(75.deg, 28.0, 85.pct, 0.pct).toLab().toRgb()
        color1.rgbHex shouldBe "#f4cea2"
        color1.alpha shouldBe 0.pct

        val color2 = Colors.hcl(305.deg, 28.0, 41.pct, 0.pct).toLab().toRgb()
        color2.rgbHex shouldBe "#685986"
        color2.alpha shouldBe 0.pct

        val color3 = Colors.hcl(328.deg, 50.0, 17.pct, 30.pct).toLab().toRgb()
        color3.rgbHex shouldBe "#500051"
        color3.alpha shouldBe 30.pct

        val color4 = Colors.hcl(46.deg, 40.0, 25.pct, 50.pct).toLab().toRgb()
        color4.rgbHex shouldBe "#682710"
        color4.alpha shouldBe 50.pct

        val color5 = Colors.hcl(267.deg, 0.0, 45.pct, 50.pct).toLab().toRgb()
        color5.rgbHex shouldBe "#6a6a6a"
        color5.alpha shouldBe 50.pct

        val color6 = Colors.hcl(0.deg, 0.0, 100.pct).toLab().toRgb()
        color6.rgbHex shouldBe "#ffffff"

        val color7 = Colors.hcl(0.deg, 0.0, 0.pct).toLab().toRgb()
        color7.rgbHex shouldBe "#000000"
    }

    // TODO check brighten and darken for HCL and LAB color !! seems wrong

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
    fun brighten_Gradient() {
        val linearGradient = Colors.Gradient.linear(Point.origin, Point.origin)
            .withColor(hotpink)
            .andColor(darkseagreen, 50.pct)
            .andColor(teal, 100.pct)

        val brighten1 = linearGradient.brighten()
        val brighten2 = linearGradient.brighten(2.0)
        val brighten3 = linearGradient.brighten(3.0)

        brighten1.colorStops[0].color.rgbHex shouldBe "#ff9ce6"
        brighten2.colorStops[0].color.rgbHex shouldBe "#ffd1ff"
        brighten3.colorStops[0].color.rgbHex shouldBe "#ffffff"

        brighten1.colorStops[1].color.rgbHex shouldBe "#c0efbf"
        brighten2.colorStops[1].color.rgbHex shouldBe "#f3fff2"
        brighten3.colorStops[1].color.rgbHex shouldBe "#ffffff"

        brighten1.colorStops[2].color.rgbHex shouldBe "#4cb0af"
        brighten2.colorStops[2].color.rgbHex shouldBe "#81e2e1"
        brighten3.colorStops[2].color.rgbHex shouldBe "#b5ffff"
    }

    @Test
    fun brighten_HCL() {
        hotpink.toLab().toHcl().brighten().rgbHex shouldBe "#ff9ce6"
        hotpink.toLab().toHcl().brighten(2.0).rgbHex shouldBe "#ffd1ff"
        hotpink.toLab().toHcl().brighten(3.0).rgbHex shouldBe "#ffffff"

        darkseagreen.toLab().toHcl().brighten().rgbHex shouldBe "#c0efbf"
        darkseagreen.toLab().toHcl().brighten(2.0).rgbHex shouldBe "#f3fff2"
        darkseagreen.toLab().toHcl().brighten(3.0).rgbHex shouldBe "#ffffff"

        teal.toLab().toHcl().brighten().rgbHex shouldBe "#4cb0af"
        teal.toLab().toHcl().brighten(2.0).rgbHex shouldBe "#81e2e1"
        teal.toLab().toHcl().brighten(3.0).rgbHex shouldBe "#b5ffff"
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
    fun darken_Gradient() {
        val linearGradient = Colors.Gradient.linear(Point.origin, Point.origin)
            .withColor(hotpink)
            .andColor(darkseagreen, 50.pct)
            .andColor(teal, 100.pct)

        val darken1 = linearGradient.darken()
        val darken2 = linearGradient.darken(2.0)
        val darken3 = linearGradient.darken(2.6)

        darken1.colorStops[0].color.rgbHex shouldBe "#c93384"
        darken2.colorStops[0].color.rgbHex shouldBe "#930058"
        darken3.colorStops[0].color.rgbHex shouldBe "#74003f"

        darken1.colorStops[1].color.rgbHex shouldBe "#608c61"
        darken2.colorStops[1].color.rgbHex shouldBe "#345e37"
        darken3.colorStops[1].color.rgbHex shouldBe "#1b441f"

        darken1.colorStops[2].color.rgbHex shouldBe "#005354"
        darken2.colorStops[2].color.rgbHex shouldBe "#00292b"
        darken3.colorStops[2].color.rgbHex shouldBe "#001715"
    }

    @Test
    fun darken_HCL() {
        hotpink.toLab().toHcl().darken().rgbHex shouldBe "#c93384"
        hotpink.toLab().toHcl().darken(2.0).rgbHex shouldBe "#930058"
        hotpink.toLab().toHcl().darken(2.6).rgbHex shouldBe "#74003f"

        darkseagreen.toLab().toHcl().darken().rgbHex shouldBe "#608c61"
        darkseagreen.toLab().toHcl().darken(2.0).rgbHex shouldBe "#345e37"
        darkseagreen.toLab().toHcl().darken(2.6).rgbHex shouldBe "#1b441f"

        teal.toLab().toHcl().darken().rgbHex shouldBe "#005354"
        teal.toLab().toHcl().darken(2.0).rgbHex shouldBe "#00292b"
        teal.toLab().toHcl().darken(2.6).rgbHex shouldBe "#001715"
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
    fun perceived_luminance() {
        black.luminance().value shouldBeClose .0
        white.luminance().value shouldBeClose 1.0
        aliceblue.luminance().value shouldBeClose .9288
        hotpink.luminance().value shouldBeClose .346584
        slategray.luminance().value shouldBeClose .208967
        teal.luminance().value shouldBeClose .1699685
        darkseagreen.luminance().value shouldBeClose .437892
    }

    @Test
    fun contrast() {
        hotpink.contrast(black) shouldBeClose 7.931687
        hotpink.contrast(white) shouldBeClose 2.647608
        hotpink.contrast(aliceblue) shouldBeClose 2.468076
        hotpink.contrast(hotpink) shouldBe 1.0
        hotpink.contrast(slategray) shouldBeClose 1.531408
        hotpink.contrast(teal) shouldBeClose 1.802913
        hotpink.contrast(darkseagreen) shouldBeClose 1.230236
    }

    @Test
    fun linear_gradient_builder() {
        val builder = Colors.Gradient.linear(Point(.0, .0), Point(100.0, .0))

        val gradient = builder.withColor(aliceblue, 0.pct).andColor(darkseagreen, 60.pct)
        gradient.colorStops.size shouldBe 2

        gradient.andColor(hotpink, 100.pct).colorStops.size shouldBe 3
    }

    @Test
    fun radial_gradient_builder() {
        val builder = Colors.Gradient.radial(Point(.0, .0), 100.0)

        val gradient = builder.withColor(aliceblue, 0.pct).andColor(darkseagreen, 60.pct)
        gradient.colorStops.size shouldBe 2

        gradient.andColor(hotpink, 100.pct).colorStops.size shouldBe 3
    }

    @Test
    fun opacify_RGB() {

        // fully opaque color *********************************************************
        hotpink.r shouldBe 255
        hotpink.g shouldBe 105
        hotpink.b shouldBe 180
        hotpink.alpha.value shouldBe 1.0

        var opacify = hotpink.opacify(2.0)
        opacify.r shouldBe 255
        opacify.g shouldBe 105
        opacify.b shouldBe 180
        opacify.alpha.value shouldBe 1.0

        opacify = hotpink.opacify(3.0)
        opacify.r shouldBe 255
        opacify.g shouldBe 105
        opacify.b shouldBe 180
        opacify.alpha.value shouldBe 1.0

        opacify = hotpink.opacify(0.5)
        opacify.r shouldBe 255
        opacify.g shouldBe 105
        opacify.b shouldBe 180
        opacify.alpha.value shouldBe 0.5

        opacify = hotpink.opacify(0.2)
        opacify.r shouldBe 255
        opacify.g shouldBe 105
        opacify.b shouldBe 180
        opacify.alpha.value shouldBe 0.2

        opacify = hotpink.opacify(.0)
        opacify.r shouldBe 255
        opacify.g shouldBe 105
        opacify.b shouldBe 180
        opacify.alpha.value shouldBe .0

        // color already transparent -> no change possible *******************************
        hotpink.withAlpha(0.pct).alpha.value shouldBe .0

        opacify = hotpink.withAlpha(0.pct).opacify(2.0)
        opacify.alpha.value shouldBe .0

        opacify = hotpink.withAlpha(0.pct).opacify(3.0)
        opacify.alpha.value shouldBe .0

        opacify = hotpink.withAlpha(0.pct).opacify(.5)
        opacify.alpha.value shouldBe .0

        opacify = hotpink.withAlpha(0.pct).opacify(.2)
        opacify.alpha.value shouldBe .0

        opacify = hotpink.withAlpha(0.pct).opacify(.0)
        opacify.alpha.value shouldBe .0

        // partially opaque color *********************************************************
        hotpink.withAlpha(20.pct).alpha.value shouldBe .2

        opacify = hotpink.withAlpha(20.pct).opacify(2.0)
        opacify.alpha.value shouldBe .4

        opacify = hotpink.withAlpha(20.pct).opacify(4.0)
        opacify.alpha.value shouldBe .8

        opacify = hotpink.withAlpha(20.pct).opacify(8.0)
        opacify.alpha.value shouldBe 1.0

        opacify = hotpink.withAlpha(80.pct).opacify(.5)
        opacify.alpha.value shouldBe .4

        opacify = hotpink.withAlpha(80.pct).opacify(.1)
        opacify.alpha.value shouldBeClose .08

        opacify = hotpink.withAlpha(80.pct).opacify(.0)
        opacify.alpha.value shouldBe .0
    }
}
