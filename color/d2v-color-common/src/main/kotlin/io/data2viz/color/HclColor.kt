package io.data2viz.color

import io.data2viz.math.*
import kotlin.math.max

/**
 * Create a color in the HCL color space (CIELCH)
 *
 * This Colorspace is designed to accord with human perception of color.
 * HCL has been adopted by information visualization practitioners to present data without the bias implicit in
 * using varying saturation.
 *
 * @param h hue: Angle
 * @param c chroma: Double, the upper bound for chroma depends on hue and luminance (typically in 0..230)
 * @param lightness: Percent, value between 0% and 100%
 * @param alpha: Opacity, value between 0% and 100%
 */
class HclColor

@Deprecated("Deprecated", ReplaceWith("Colors.hcl(h,c,l,alpha)", "io.data2viz.colors.Colors"))
internal constructor(val h: Angle, val c: Double, lightness: Percent, a: Percent = 100.pct) : Color {

    val l = lightness//.coerceIn(.0, 100.0)
    override val alpha = a.coerceToDefault()

    override val rgb = toRgb().rgb
    override val rgba = toRgb().rgba
    override val r = toRgb().r
    override val g = toRgb().g
    override val b = toRgb().b
    override val rgbHex: String = toRgb().rgbHex

    override fun luminance() = toRgb().luminance()
    override fun contrast(other:Color) = toRgb().contrast(other)

    override fun toRgb():RgbColor = toLab().toRgb()
    override fun toLab(): LabColor = toLaba()
    override fun toHcl(): HclColor = this
    override fun toHsl(): HslColor = toLab().toHsl()

    override fun brighten(strength: Double): Color = Colors.hcl(h, c, (l + (Kn * strength).pct), alpha)
    override fun darken(strength: Double): Color = Colors.hcl(h, c, (l - (Kn * strength).pct), alpha)
    override fun saturate(strength: Double): Color = Colors.hcl(h, max(.0, (c + (Kn * strength))), l, alpha)
    override fun desaturate(strength: Double): Color = Colors.hcl(h, max(.0, (c - (Kn * strength))), l, alpha)
    override fun withAlpha(alpha: Percent) = Colors.hcl(h, c, l, alpha)
    override fun withHue(hue: Angle) = Colors.hcl(hue, c, l, alpha)

    fun isAchromatic() = (c == .0) || (l.value <= .0) || (l.value >= 1.0)

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

    override fun toString() = "HCL(${h.deg}°, $c, $l%, alpha=$alpha)"
}