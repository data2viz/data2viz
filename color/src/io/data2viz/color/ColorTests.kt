package io.data2viz.color

import io.data2viz.color.colors.aliceblue
import io.data2viz.color.colors.black
import io.data2viz.color.colors.darkolivegreen
import io.data2viz.color.colors.hsla
import io.data2viz.color.colors.lightcoral
import io.data2viz.color.colors.rgba
import io.data2viz.color.colors.white
import io.data2viz.math.Angle
import io.data2viz.math.deg
import io.data2viz.test.StringSpec
import kotlin.js.Math

class ColorTests : StringSpec() {

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

        "RGB coercing (under 0 and over 255 color channel...)" {
            rgba(300, -300, 256.8, -2.5) shouldBe rgba(255, 0, 255, 0)
            rgba(-300, 300, -256.8, 2.5) shouldBe rgba(0, 255, 0, 1)
        }

        "HSLA to RGBA (reference https://www.w3schools.com/colors/colors_converter.asp)" {
            hsla(0.deg, 0, 0).toRgba() shouldBe black
            hsla(0.deg, 0, 1).toRgba() shouldBe white
            hsla(32.deg, 0.80, 0.80, 0).toRgba() shouldBe Color(0xf5cfa3, 0)
            hsla(260.deg, 0.20, 0.44, 1).toRgba() shouldBe Color(0x695a87, 1)
            hsla(300.deg, 0.98, 0.16, .3).toRgba() shouldBe Color(0x510151, .3)
            hsla(16.deg, 0.75, 0.23, .5).toRgba() shouldBe Color(0x67260f, .5)
        }

        "RGBA to HSLA (rounded)" {
            //Color(0xf5cfa3, 0).toHsla() shouldBe hsla(32.deg, 0.80, 0.80, 0)
            val color1 = Color(0xf5cfa3, 0)
            Math.round(color1.toHsla().h.deg) shouldBe 32
            Math.round(color1.toHsla().s*100) shouldBe 80
            Math.round(color1.toHsla().l*100) shouldBe 80
            color1.toHsla().alpha shouldBe 0

//            hsla(260.deg, 0.20, 0.44, 1).toRgba() shouldBe Color(0x695a87, 1)
            val color2 = Color(0x695a87, 1)
            Math.round(color2.toHsla().h.deg) shouldBe 260
            Math.round(color2.toHsla().s*100) shouldBe 20
            Math.round(color2.toHsla().l*100) shouldBe 44
            color2.toHsla().alpha shouldBe 1

//            hsla(300.deg, 0.98, 0.16, .3).toRgba() shouldBe Color(0x510151, .3)
            val color3 = Color(0x510151, .3)
            Math.round(color3.toHsla().h.deg) shouldBe 300
            Math.round(color3.toHsla().s*100) shouldBe 98
            Math.round(color3.toHsla().l*100) shouldBe 16
            color3.toHsla().alpha shouldBe .3

//            hsla(16.deg, 0.75, 0.23, .5).toRgba() shouldBe Color(0x67260f, .5)*/
            val color4 = Color(0x67260f, .5)
            Math.round(color4.toHsla().h.deg) shouldBe 16
            Math.round(color4.toHsla().s*100) shouldBe 75
            Math.round(color4.toHsla().l*100) shouldBe 23
            color4.toHsla().alpha shouldBe .5

//            hsla(0.deg, 0, 0, .42).toRgba() shouldBe Color(0x6a6a6a, .2)*/
            val color5 = Color(0x6a6a6a, .2)
            Math.round(color5.toHsla().h.deg) shouldBe 0
            Math.round(color5.toHsla().s*100) shouldBe 0
            Math.round(color5.toHsla().l*100) shouldBe 42
            color5.toHsla().alpha shouldBe .2
        }

        "HSL REFERENCES : HSL(0, 0, 0) should be black" {
            black.toHsla() shouldBe hsla(0.deg, 0, 0)
            white.toHsla() shouldBe hsla(0.deg, 0, 1)

            HSL(Angle(0.0), 0f, 0f).toRgba() shouldBe black
            HSL(Angle(0.0), 0f, 1f).toRgba() shouldBe white
        }

        "Default HSL color should be the same as default RGB color (white)" {
            val color = HSL().toRgba()
            color shouldBe Color()
            color shouldBe white
        }

        "RGB conversion of multiples HSL colors" {
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

        "RGB conversion of HSL(120, 0.5, 0.5, 0.5f) should be #40BF40 with 0.5 alpha" {
            val color = HSL(120.deg, 0.5, 0.5, 0.5).toRgba()
            color.rgbHex shouldBe "#40bf40"
            color.alpha shouldBe 0.5f
        }

        "HSL coercing (under 0 and over 1 luminance...)" {
            HSL(488.deg, .5, .5).toRgba() shouldBe HSL(128.deg, .5, .5).toRgba()
            HSL(120.deg, -.5f, .5f) shouldBe HSL(120.deg, 0f, .5f)
            HSL(120.deg, .5f, 1.5f) shouldBe HSL(120.deg, .5f, 1f)
        }

        "LAB CONVERSION REFERENCE http://colormine.org/convert/rgb-to-lab : Default LAB color should be the same as default RGB color (white)" {
            LAB().toRgba() shouldBe Color()
        }

        "RGB conversion of multiples LAB colors" {
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
    }

}
