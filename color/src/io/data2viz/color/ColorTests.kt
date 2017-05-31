package io.data2viz.color

import io.data2viz.color.colors.black
import io.data2viz.math.Angle
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
            val color = HSL(Angle(0.0), 0f, 0.742f).toRgba()

            color.rgbHex shouldBe "#bdbdbd"
        }

        "RGB conversion of HSL(120, 0.5, 0.5) should be #40BF40" {
            val color = HSL(Angle(120.0), 0.5f, 0.5f).toRgba()

            color.rgbHex shouldBe "#40bf40"
        }

        "RGB conversion of HSL(180, 0.3, 0.6) should be #7AB8B8" {
            val color = HSL(Angle(180.0), 0.3f, 0.6f).toRgba()

            color.rgbHex shouldBe "#7ab8b8"
        }

        "RGB conversion of HSL(63, 0.22, 0.46) should be #8D8F5B" {
            val color = HSL(Angle(63.0), 0.22f, 0.46f).toRgba()

            color.rgbHex shouldBe "#8d8f5b"
        }

        "RGB conversion of HSL(32, 0.80, 0.80) should be #F5CFA3" {
            val color = HSL(Angle(32.0), 0.8f, 0.8f).toRgba()

            color.rgbHex shouldBe "#f5cfa3"
        }

        "RGB conversion of HSL(256, 0.78, 0.48) should be #AE7CDA" {
            val color = HSL(Angle(272.0), 0.56f, 0.67f).toRgba()

            color.rgbHex shouldBe "#ae7cda"
        }

        "RGB conversion of HSL(300, 0.2, 0.4) should be #7A527A" {
            val color = HSL(Angle(300.0), 0.2f, 0.4f).toRgba()

            color.rgbHex shouldBe "#7a527a"
        }

        "RGB conversion of HSL(265, 0.51, 0.42) should be #6234A2" {
            val color = HSL(Angle(265.0), 0.51f, 0.42f).toRgba()

            color.rgbHex shouldBe "#6234a2"
        }

        "RGB conversion of HSL(120, 0.5, 0.5, 0.5f) should be #40BF40 with 0.5 alpha" {
            val color = HSL(Angle(120.0), 0.5f, 0.5f, 0.5f).toRgba()

            color.rgbHex shouldBe "#40bf40"
            color.alpha shouldBe 0.5f
        }

        // TODO : a revoir on passe pas par les setters dans ces cas, donc pas de coerce !!
        /*"HSL(488, 0.5, 0.5) should be equal to HSL(128, 0.5, 0.5)" {
            val color = HSL(Angle(488.0), .5f, .5f).rgb()
            val color2 = HSL(Angle(128.0), .5f, .5f).rgb()

            color.rgbHex shouldBe color2.rgbHex
        }

        "HSL(120, -0.5, 0.5) should be coerced to HSL(120, 0, 0.5)" {
            val color = HSL(Angle(120.0), -.5f, .5f).rgb()
            val color2 = HSL(Angle(120.0), 0f, .5f).rgb()

            color.rgbHex shouldBe color2.rgbHex
        }

        "HSL(120, 0.5, 1.5) should be coerced to HSL(120, 0.5, 1)" {
            val color = HSL(Angle(120.0), .5f, 1.5f).rgb()
            val color2 = HSL(Angle(120.0), .5f, 1f).rgb()

            color.rgbHex shouldBe color2.rgbHex
        }*/


        "HSL CONVERSION REFERENCE http://colormine.org/convert/rgb-to-lab : Default LAB color should be the same as default RGB color (white)" {
            val color = LAB().rgba()
            val color2 = Color()

            color.rgbHex shouldBe color2.rgbHex
        }

        "RGB conversion of LAB(76.61119593527346, 0.004199837865148659, -0.008309607037970679) should be #BDBDBD" {
            val color = LAB(76.61119593527346f, 0.004199837865148659f, -0.008309607037970679f).rgba()

            color.rgbHex shouldBe "#bdbdbd"
        }

        "RGB conversion of LAB(68.54923, -58.98131, 52.11442) should be #40BF40" {
            val color = LAB(68.54923f, -58.98131f, 52.11442f).rgba()

            color.rgbHex shouldBe "#40bf40"
        }

        "RGB conversion of LAB(70.78758592250671, -19.781871234537117, -6.343933758420195) should be #7AB8B8" {
            val color = LAB(70.78758592250671f, -19.781871234537117f, -6.343933758420195f).rgba()

            color.rgbHex shouldBe "#7ab8b8"
        }

        "RGB conversion of LAB(58.09677475743865, -9.18693065835896, 27.45602894645598) should be #8D8F5B" {
            val color = LAB(58.09677475743865f, -9.18693065835896f, 27.45602894645598f).rgba()

            color.rgbHex shouldBe "#8d8f5b"
        }

        "RGB conversion of LAB(85.3433, 7.2317, 26.8528) should be #F5CFA3" {
            val color = LAB(85.3433f, 7.2317f, 26.8528f).rgba()

            color.rgbHex shouldBe "#f5cfa3"
        }

        "RGB conversion of LAB(60.32, 37.18, -40.92) should be #AE7CDA" {
            val color = LAB(60.32f, 37.18f, -40.92f).rgba()

            color.rgbHex shouldBe "#ae7cda"
        }

        "RGB conversion of LAB(40.54, 23.82, -15.98) should be #7A527A" {
            val color = LAB(40.54f, 23.82f, -15.98f).rgba()

            color.rgbHex shouldBe "#7a527a"
        }

        "RGB conversion of LAB(33.27, 43.84, -52.04) should be #6234A2" {
            val color = LAB(33.27f, 43.84f, -52.04f).rgba()

            color.rgbHex shouldBe "#6234a2"
        }

        "RGB conversion of LAB(68.54923, -58.98131, 52.11442, 0.5) should be #40BF40 with 0.5 alpha" {
            val color = LAB(68.54923f, -58.98131f, 52.11442f, 0.5f).rgba()

            color.rgbHex shouldBe "#40bf40"
            color.alpha shouldBe 0.5f
        }
    }

}
