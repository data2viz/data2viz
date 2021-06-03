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

import io.data2viz.math.Angle
import io.data2viz.math.Percent
import io.data2viz.math.pct

/**
 * Implementation of Color as an rgb integer and an alpha channel.
 *
 * Provides conversion with hex string notation.
 *
 * See https://developer.mozilla.org/en-US/docs/Web/CSS/color_value and
 * https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Colors/Color_picker_tool
 */
public class RgbColor
@Deprecated("Use factory function or Int.col extension.", ReplaceWith("Colors.rgb(rgb,a)", "io.data2viz.colors.Colors"))
constructor(override val rgb: Int, a: Percent = 100.pct) : Color {

    override val alpha: Percent = a.coerceToDefault()

    override val r: Int
        get() = (rgb shr 16) and 0xff

    override val g: Int
        get() = (rgb shr 8) and 0xff

    override val b: Int
        get() = rgb and 0xff

    override fun luminance(): Percent = toLuminance()
    override fun contrast(other: Color): Double {
        val lumA = luminance()
        val lumB = other.luminance()
        return if (lumA > lumB) (lumA.value + 0.05) / (lumB.value + 0.05) else (lumB.value + 0.05) / (lumA.value + 0.05)
    }

    override fun toRgb(): RgbColor = this
    override fun toLab(): LabColor = toLaba()
    override fun toHcl(): HclColor = toLab().toHcl()
    override fun toHsl(): HslColor = toHsla()

    override fun brighten(strength: Double): Color = toLab().brighten(strength)
    override fun darken(strength: Double): Color = toLab().darken(strength)
    override fun saturate(strength: Double): Color = toLab().saturate(strength)
    override fun desaturate(strength: Double): Color = toLab().desaturate(strength)
    override fun opacify(strength: Double): Color = withAlpha(Percent(alpha * strength))
    override fun withHue(hue: Angle): HclColor = toHcl().withHue(hue)

    public fun withRed(red: Int): RgbColor {
        val rgb = (rgb and 0x00ffff) + (red.coerceIn(0, 255) shl 16)
        return Colors.rgb(rgb, alpha)
    }

    public fun withGreen(green: Int): RgbColor {
        val rgb = (rgb and 0xff00ff) + (green.coerceIn(0, 255) shl 8)
        return Colors.rgb(rgb, alpha)
    }

    public fun withBlue(blue: Int): RgbColor {
        val rgb = (rgb and 0xffff00) + blue.coerceIn(0, 255)
        return Colors.rgb(rgb, alpha)
    }

    override val rgbHex: String
        get() = "#" +
                ((rgb shr 20) and 0xf).toString(16) +
                ((rgb shr 16) and 0xf).toString(16) +
                ((rgb shr 12) and 0xf).toString(16) +
                ((rgb shr 8) and 0xf).toString(16) +
                ((rgb shr 4) and 0xf).toString(16) +
                (rgb and 0xf).toString(16)

    override val rgba: String
        get() = "rgba($r, $g, $b, ${alpha.value})"

    override fun withAlpha(alpha: Percent): RgbColor = Colors.rgb(rgb, alpha)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is Color) return false

        if (rgb != other.rgb) return false
        if (alpha != other.alpha) return false

        return true
    }

    override fun hashCode(): Int {
        var result = rgb
        result = 31 * result + alpha.hashCode()
        return result
    }

    override fun toString(): String = "RGB($r, $g, $b, alpha=$alpha) - $rgbHex"
}


/**
 * Instantiate a color from an Int. It should be used
 * using the HEX code like this : `0x0b0b0b.col`
 */
public val Int.col: RgbColor
    get() = Colors.rgb(this)

@Deprecated("Use the 3 characters version of it.", ReplaceWith("this.col"))
public val Int.color: RgbColor
    get() = this.col


private val regex: Regex by lazy { """^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$""".toRegex() }

/**
 * Instantiate a color from an String representing its hexadecimal value.
 * Ex: "#12abCD".col
 */
public val String.col: RgbColor
    get():RgbColor {
        require(this.matches(regex)) {
            "Conversion of string to io.data2viz.col.RgbColor works for encoded colors like #12abCD"
        }
        return Colors.rgb(substring(1).toInt(16))
    }

@Deprecated("Use the 3 characters version of it.", ReplaceWith("this.col"))
public val String.color: RgbColor
    get() = this.col
