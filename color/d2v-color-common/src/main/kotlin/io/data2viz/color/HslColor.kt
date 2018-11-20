package io.data2viz.color

import io.data2viz.math.*

/**
 * Create a color in the HSL color space
 *
 * @param h hue: Angle
 * @param s saturation: Percent, value between 0% and 100%
 * @param l lightness: Percent, value between 0% and 100%
 * @param alpha: Opacity, value between 0% and 100%
 */
class HslColor
@Deprecated("Deprecated", ReplaceWith("Colors.hsl(hue,saturation,luminance,a)", "io.data2viz.colors.Colors"))
internal constructor(hue: Angle, saturation: Percent, lightness: Percent, a: Percent = 100.pct) : Color {

    val h = hue.normalize()
    val s = saturation.coerceToDefault()
    val l = lightness.coerceToDefault()
    override val alpha = a.coerceToDefault()

    override val rgb = toRgb().rgb
    override val rgba = toRgb().rgba
    override val r = toRgb().r
    override val g = toRgb().g
    override val b = toRgb().b
    override val rgbHex: String = toRgb().rgbHex

    override fun luminance() = toRgb().luminance()
    override fun contrast(other:Color) = toRgb().contrast(other)

    override fun toRgb(): RgbColor = toRgba()
    override fun toLab(): LabColor = toRgb().toLab()
    override fun toHcl(): HclColor = toRgb().toHcl()
    override fun toHsl(): HslColor = this

    override fun brighten(strength: Double): Color = toRgb().brighten(strength)
    override fun darken(strength: Double): Color = toRgb().darken(strength)
    override fun saturate(strength: Double): Color = toRgb().saturate(strength)
    override fun desaturate(strength: Double): Color = toRgb().desaturate(strength)
    override fun withAlpha(alpha: Percent) = Colors.hsl(h, s, l, alpha)
    override fun withHue(hue: Angle) = toHcl().withHue(hue)

    fun isAchromatic() = (s.value == .0) || (l.value <= .0) || (l.value >= 1.0)

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

    override fun toString() = "HSL(${h.deg}°, $s, $l, alpha=$alpha)"
}