package io.data2viz.color

/**
 * Implementation of Color as an rgb integer and an alpha channel.
 *
 * Provides conversion with hex string notation.
 *
 * See https://developer.mozilla.org/en-US/docs/Web/CSS/color_value and
 * https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Colors/Color_picker_tool
 */
class RgbColor(override val rgb: Int, a: Double = 1.0) : Color {

    override val alpha = a.coerceIn(.0, 1.0)

    override val r: Int
        get() = (rgb shr 16) and 0xff

    override val g: Int
        get() = (rgb shr 8) and 0xff

    override val b: Int
        get() = rgb and 0xff

    override fun toRgb(): RgbColor = this
    override fun brighten(strength: Double): Color = toLab().brighten(strength)
    override fun darken(strength: Double): Color = toLab().darken(strength)
    override fun saturate(strength: Double): Color = toLab().saturate(strength)
    override fun desaturate(strength: Double): Color = toLab().desaturate(strength)

    fun withRed(red: Int): RgbColor {
        val rgb = (rgb and 0x00ffff) + (red.coerceIn(0, 255) shl 16)
        return RgbColor(rgb, alpha)
    }

    fun withGreen(green: Int): RgbColor {
        val rgb = (rgb and 0xff00ff) + (green.coerceIn(0, 255) shl 8)
        return RgbColor(rgb, alpha)
    }

    fun withBlue(blue: Int): RgbColor {
        val rgb = (rgb and 0xffff00) + blue.coerceIn(0, 255)
        return RgbColor(rgb, alpha)
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
        get() = "RGB($r, $g, $b, $alpha)"

    override fun withAlpha(alpha: Double) = RgbColor(rgb, alpha)

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