package io.data2viz.color

import kotlin.math.pow
import kotlin.math.roundToInt

/**
 * Implementation of Color as an rgb integer and an alpha channel.
 *
 * Provides conversion with hex string notation.
 *
 * See https://developer.mozilla.org/en-US/docs/Web/CSS/color_value and
 * https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Colors/Color_picker_tool
 *
 * TODO extract a color interface including alpha (alpha is common to all color spaces)
 * TODO store color value in double
 * TODO : coerce in place of require check ??
 */
class Color(val rgb: Int = 0xffffff, val alpha: Float = 1.0f): ColorOrGradient {

    val r: Int
        get() = (rgb shr 16) and 0xff

    val g: Int
        get() = (rgb shr 8) and 0xff

    val b: Int
        get() = rgb and 0xff

    fun withRed(red:Int): Color {
        val rgb = (rgb and 0x00ffff) + (red.coerceIn(0, 255) shl 16)
        return Color(rgb, alpha)
    }

    fun withGreen(green:Int): Color {
        val rgb = (rgb and 0xff00ff) + (green.coerceIn(0, 255) shl 8)
        return Color(rgb, alpha)
    }

    fun withBlue(blue: Int): Color {
        val rgb = (rgb and 0xffff00) + blue.coerceIn(0, 255)
        return Color(rgb, alpha)
    }

    fun rgba(red: Int, green: Int, blue: Int, alpha: Float): Color {
        val rgb = (red.coerceIn(0, 255) shl 16) + (green.coerceIn(0, 255) shl 8) + blue.coerceIn(0, 255)
        return Color(rgb, alpha.coerceIn(0f, 1f))
    }

    fun brighter(strength: Double = 1.0):Color {
        val str = brighter.pow(strength)
        return rgba((r * str).roundToInt(), (g * str).roundToInt(), (b * str).roundToInt(), alpha)
    }

    fun darker(strength: Double = 1.0):Color {
        val str = darker.pow(strength)
        return rgba((r * str).roundToInt(), (g * str).roundToInt(), (b * str).roundToInt(), alpha)
    }

    val rgbHex: String
        get() = "#" +
                ((rgb shr 20) and 0xf).toString(16) +
                ((rgb shr 16) and 0xf).toString(16) +
                ((rgb shr 12) and 0xf).toString(16) +
                ((rgb shr 8) and 0xf).toString(16) +
                ((rgb shr 4) and 0xf).toString(16) +
                (rgb and 0xf).toString(16)

    val rgba: String
        get() = "rgba($r, $g, $b, $alpha)"

    fun withAlpha(alpha: Float): Color = Color(rgb, alpha)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Color

        if (rgb != other.rgb) return false
        if (alpha != other.alpha) return false

        return true
    }

    override fun hashCode(): Int {
        var result = rgb
        result = 31 * result + alpha.hashCode()
        return result
    }

    override fun toString() = if (alpha < 1.0) rgba else rgbHex

}
