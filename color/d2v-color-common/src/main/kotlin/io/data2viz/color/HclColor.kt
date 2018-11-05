package io.data2viz.color

import io.data2viz.math.Angle
import io.data2viz.math.Percent
import io.data2viz.math.pct
import kotlin.math.max

/**
 * Create a color in the HCL color space (CIELCH)
 *
 * @param h hue: Angle in degree
 * @param c chroma: Float, the upper bound for chroma depends on hue and luminance (typically in 0..230)
 * @param l: Float a value in the range [0,100] giving the luminance of the colour (in percent)
 * @param alpha: Float between 0 and 1
 */
class HclColor

@Deprecated("Deprecated", ReplaceWith("Colors.hcl(h,c,l,alpha)", "io.data2viz.colors.Colors"))
internal constructor(val h: Angle, val c: Double, val l: Percent, a: Percent = 100.pct) : Color {

    override val alpha = a.coerceToDefault()

    override val rgb = toRgb().rgb
    override val rgba = toRgb().rgba
    override val r = toRgb().r
    override val g = toRgb().g
    override val b = toRgb().b
    override val rgbHex: String = toRgb().rgbHex

    override fun toRgb():RgbColor = toLab().toRgb()
    override fun brighten(strength: Double): Color = Colors.hcl(h, c, (l + (Kn * strength).pct), alpha)
    override fun darken(strength: Double): Color = Colors.hcl(h, c, (l - (Kn * strength).pct), alpha)
    override fun saturate(strength: Double): Color = Colors.hcl(h, max(.0, (c + (Kn * strength))), l, alpha)
    override fun desaturate(strength: Double): Color = Colors.hcl(h, max(.0, (c - (Kn * strength))), l, alpha)
    override fun withAlpha(alpha: Percent) = Colors.hcl(h, c, l, alpha)

    fun isAchromatic() = (c == .0) || (l <= 0.pct) || (l >= 100.pct)

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