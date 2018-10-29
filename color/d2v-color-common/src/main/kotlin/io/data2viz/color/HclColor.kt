package io.data2viz.color

import io.data2viz.math.Angle
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

/**
 * Create a color in the HCL color space (CIELCH)
 *
 * @param h hue:Angle in degree
 * @param c chroma:Double, the upper bound for chroma depends on hue and luminance
 * @param l luminance:Float a value in the range [0,100] giving the luminance of the colour (in percent)
 * @param alpha:Float between 0 and 1
 */
class HclColor(val h: Angle = Angle(0.0), c: Number = 1, l: Number = 100, alpha: Number = 1) : Color {

    val c = c.toDouble()
    val l = l.toFloat().coerceIn(0f, 100f)
    override val alpha = alpha.toFloat().coerceIn(0f, 1f)

    override val rgb = toLab().toRgba().rgb
    override val rgba = toLab().toRgba().rgba
    override val r = toLab().toRgba().r
    override val g = toLab().toRgba().g
    override val b = toLab().toRgba().b
    override val rgbHex: String = toLab().toRgba().rgbHex

    override fun brighten(strength: Double): Color = HclColor(h, c, (l + (Kn * strength)).toFloat(), alpha)
    override fun darken(strength: Double): Color = HclColor(h, c, (l - (Kn * strength)).toFloat(), alpha)
    override fun saturate(strength: Double): Color = HclColor(h, max(.0, (c + (Kn * strength))).toFloat(), l, alpha)
    override fun desaturate(strength: Double): Color = HclColor(h, max(.0, (c - (Kn * strength))).toFloat(), l, alpha)

    /*val displayable: Boolean
        get() = (s in 0..1) && (l in 0..1) && (alpha in 0..1)*/

    override fun equals(other: Any?): Boolean = (other != null && other is HclColor && h == other.h && c == other.c && l == other.l && alpha == other.alpha)

    override fun toString() = "hcla(${h.deg}°, $c, ${l * 100}%, $alpha)"
    override fun hashCode(): Int {
        var result = h.hashCode()
        result = 31 * result + c.hashCode()
        result = 31 * result + l.hashCode()
        result = 31 * result + alpha.hashCode()
        result = 31 * result + rgb
        result = 31 * result + rgba.hashCode()
        result = 31 * result + r
        result = 31 * result + g
        result = 31 * result + b
        result = 31 * result + rgbHex.hashCode()
        return result
    }
}