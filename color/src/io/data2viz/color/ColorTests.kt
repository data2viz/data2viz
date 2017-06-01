package io.data2viz.color

import io.data2viz.color.colors.black
import io.data2viz.math.Angle
import io.data2viz.math.deg
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

        "HSL CONVERSION REFERENCE https://www.w3schools.com/colors/colors_converter.asp : RGB conversion of HSL(0, 0, 0) should be black" {
            val color = HSL(Angle(0.0), 0f, 0f).toRgba()
            color.rgbHex shouldBe black.rgbHex
        }

        "Default HSL color should be the same as default RGB color (white)" {
            val color = HSL().toRgba()
            val color2 = Color()
            color.rgbHex shouldBe color2.rgbHex
        }

        "RGB conversion of HSL(0, 0, 0.742) should be #BDBDBD" {
            val color = HSL(0.deg, 0f, 0.742f).toRgba()
            color.rgbHex shouldBe "#bdbdbd"
        }

        "RGB conversion of HSL(120, 0.5, 0.5) should be #40BF40" {
            val color = HSL(120.deg, 0.5f, 0.5f).toRgba()
            color.rgbHex shouldBe "#40bf40"
        }

        "RGB conversion of HSL(180, 0.3, 0.6) should be #7AB8B8" {
            val color = HSL(180.deg, 0.3f, 0.6f).toRgba()
            color.rgbHex shouldBe "#7ab8b8"
        }

        "RGB conversion of HSL(63, 0.22, 0.46) should be #8D8F5B" {
            val color = HSL(63.deg, 0.22f, 0.46f).toRgba()
            color.rgbHex shouldBe "#8d8f5b"
        }

        "RGB conversion of HSL(32, 0.80, 0.80) should be #F5CFA3" {
            val color = HSL(32.0.deg, 0.8f, 0.8f).toRgba()
            color.rgbHex shouldBe "#f5cfa3"
        }

        "RGB conversion of HSL(256, 0.78, 0.48) should be #AE7CDA" {
            val color = HSL(272.deg, 0.56f, 0.67f).toRgba()
            color.rgbHex shouldBe "#ae7cda"
        }

        "RGB conversion of HSL(300, 0.2, 0.4) should be #7A527A" {
            val color = HSL(300.deg, 0.2f, 0.4f).toRgba()
            color.rgbHex shouldBe "#7a527a"
        }

        "RGB conversion of HSL(265, 0.51, 0.42) should be #6234A2" {
            val color = HSL(265.deg, 0.51f, 0.42f).toRgba()
            color.rgbHex shouldBe "#6234a2"
        }

        "RGB conversion of HSL(120, 0.5, 0.5, 0.5f) should be #40BF40 with 0.5 alpha" {
            val color = HSL(120.deg, 0.5f, 0.5f, 0.5f).toRgba()
            color.rgbHex shouldBe "#40bf40"
            color.alpha shouldBe 0.5f
        }

        // TODO : a revoir on passe pas par les setters dans ces cas, donc pas de coerce !!
        "HSL(488, 0.5, 0.5) should be equal to HSL(128, 0.5, 0.5)" {
            val color = HSL(488.deg, .5f, .5f).toRgba()
            val color2 = HSL(128.deg, .5f, .5f).toRgba()

            color.rgbHex shouldBe color2.rgbHex
        }

        "HSL(120, -0.5, 0.5) should be coerced to HSL(120, 0, 0.5)" {
            val color = HSL(120.deg, -.5f, .5f).toRgba()
            val color2 = HSL(120.deg, 0f, .5f).toRgba()

            color.rgbHex shouldBe color2.rgbHex
        }

        "HSL(120, 0.5, 1.5) should be coerced to HSL(120, 0.5, 1)" {
            val color = HSL(120.deg, .5f, 1.5f).toRgba()
            val color2 = HSL(120.deg, .5f, 1f).toRgba()

            color.rgbHex shouldBe color2.rgbHex
        }


        "LAB CONVERSION REFERENCE http://colormine.org/convert/rgb-to-lab : Default LAB color should be the same as default RGB color (white)" {
            val color = LAB().toRgba()
            val color2 = Color()

            color.rgbHex shouldBe color2.rgbHex
        }

        "RGB conversion of LAB(76.61, 0, 0) should be #BDBDBD" {
            val color = LAB(76.61f, 0f, 0f).toRgba()

            color.rgbHex shouldBe "#bdbdbd"
        }

        "RGB conversion of LAB(68.55, -58.98, 52.11) should be #40BF40" {
            val color = LAB(68.55f, -58.98f, 52.11f).toRgba()

            color.rgbHex shouldBe "#40bf40"
        }

        "RGB conversion of LAB(70.79, -19.78, -6.34) should be #7AB8B8" {
            val color = LAB(70.79f, -19.78f, -6.34f).toRgba()

            color.rgbHex shouldBe "#7ab8b8"
        }

        "RGB conversion of LAB(58.10, -9.19, 27.46) should be #8D8F5B" {
            val color = LAB(58.10f, -9.19f, 27.46f).toRgba()

            color.rgbHex shouldBe "#8d8f5b"
        }

        "RGB conversion of LAB(85.34, 7.23, 26.85) should be #F5CFA3" {
            val color = LAB(85.34f, 7.23f, 26.85f).toRgba()

            color.rgbHex shouldBe "#f5cfa3"
        }

        "RGB conversion of LAB(60.32, 37.18, -40.92) should be #AE7CDA" {
            val color = LAB(60.32f, 37.18f, -40.92f).toRgba()

            color.rgbHex shouldBe "#ae7cda"
        }

        "RGB conversion of LAB(40.54, 23.82, -15.98) should be #7A527A" {
            val color = LAB(40.54f, 23.82f, -15.98f).toRgba()

            color.rgbHex shouldBe "#7a527a"
        }

        "RGB conversion of LAB(33.27, 43.84, -52.04) should be #6234A2" {
            val color = LAB(33.27f, 43.84f, -52.04f).toRgba()

            color.rgbHex shouldBe "#6234a2"
        }

        "RGB conversion of LAB(68.54923, -58.98131, 52.11442, 0.5) should be #40BF40 with 0.5 alpha" {
            val color = LAB(68.54923f, -58.98131f, 52.11442f, 0.5f).toRgba()

            color.rgbHex shouldBe "#40bf40"
            color.alpha shouldBe 0.5f
        }
    }

}
