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
class RgbColor
    @Deprecated("Use factory function or Int.col extension.", ReplaceWith("Colors.rgb(rgb,a)", "io.data2viz.colors.Colors"))
    constructor(override val rgb: Int, a: Percent = 100.pct) : Color {

    override val alpha = a.coerceToDefault()

    override val r: Int
        get() = (rgb shr 16) and 0xff

    override val g: Int
        get() = (rgb shr 8) and 0xff

    override val b: Int
        get() = rgb and 0xff

    override fun luminance() = toLuminance()
    override fun contrast(other:Color): Double {
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
    override fun withHue(hue: Angle) = toHcl().withHue(hue)

    fun withRed(red: Int): RgbColor {
        val rgb = (rgb and 0x00ffff) + (red.coerceIn(0, 255) shl 16)
        return Colors.rgb(rgb, alpha)
    }

    fun withGreen(green: Int): RgbColor {
        val rgb = (rgb and 0xff00ff) + (green.coerceIn(0, 255) shl 8)
        return Colors.rgb(rgb, alpha)
    }

    fun withBlue(blue: Int): RgbColor {
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

    override fun withAlpha(alpha: Percent) = Colors.rgb(rgb, alpha)

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

    override fun toString() = "RGB($r, $g, $b, alpha=$alpha) - $rgbHex"
}


/**
 * Instantiate a color from an Int. It should be used
 * using the HEX code like this : `0x0b0b0b.col`
 */
val Int.col: RgbColor
    get() = Colors.rgb(this)

@Deprecated("Use the 3 characters version of it.", ReplaceWith("this.col"))
val Int.color: RgbColor
    get() = this.col

/**
 * Instantiate a color from an String representing its hexadecimal value.
 * Ex: "#12abCD".col
 */
val String.col: RgbColor
    get():RgbColor {
        val regex = """^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$""".toRegex()
        require(this.matches(regex)) {
            "Conversion of string to io.data2viz.col.RgbColor works for encoded colors like #12abCD"
        }
        return Colors.rgb(substring(1).toInt(16))
    }

@Deprecated("Use the 3 characters version of it.", ReplaceWith("this.col"))
val String.color: RgbColor
    get() = this.col