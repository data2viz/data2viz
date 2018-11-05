package io.data2viz.color

import io.data2viz.math.Angle
import io.data2viz.math.Percent
import io.data2viz.math.pct

/**
 * Create a color in the HSL color space
 *
 * @param h hue:Angle in degree
 * @param s saturation:Float between 0 and 1
 * @param l lightness:Float between 0 and 1
 * @param alpha:Float between 0 and 1
 */
class HslColor
@Deprecated("Deprecated", ReplaceWith("Colors.hsl(hue,saturation,lightness,a)", "io.data2viz.colors.Colors"))
internal constructor(hue: Angle, saturation: Double, lightness: Double, a: Percent = 100.pct) : Color {

    val h = hue.normalize()
    val s = saturation.coerceIn(.0, 1.0)
    val l = lightness.coerceIn(.0, 1.0)
    override val alpha = a.coerceToDefault()

    override val rgb = toRgb().rgb
    override val rgba = toRgb().rgba
    override val r = toRgb().r
    override val g = toRgb().g
    override val b = toRgb().b
    override val rgbHex: String = toRgb().rgbHex

    override fun toRgb(): RgbColor = toRgba()
    override fun brighten(strength: Double): Color = toRgb().brighten(strength)
    override fun darken(strength: Double): Color = toRgb().darken(strength)
    override fun saturate(strength: Double): Color = toRgb().saturate(strength)
    override fun desaturate(strength: Double): Color = toRgb().desaturate(strength)
    override fun withAlpha(alpha: Percent) = Colors.hsl(h, s, l, alpha)

    fun isAchromatic() = (s == .0) || (l <= .0) || (l >= 1.0)

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

    override fun toString() = "HSL(${h.deg}°, ${s*100}%, ${l*100}%, alpha=$alpha)"
}