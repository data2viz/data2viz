package io.data2viz.color

import io.data2viz.math.Angle
import io.data2viz.math.deg
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.roundToInt

/**
 * Create a color in the HSL color space
 *
 * @param h hue:Angle in degree
 * @param s saturation:Float between 0 and 1
 * @param l lightness:Float between 0 and 1
 * @param alpha:Float between 0 and 1
 */
class HslColor(val h: Angle = Angle(0.0), s: Number = 1, l: Number = 1, alpha: Number = 1):Color {

    val s = s.toDouble().coerceIn(0.0, 1.0)
    val l = l.toDouble().coerceIn(0.0, 1.0)
    override val alpha = alpha.toFloat().coerceIn(0f, 1f)

    override val rgb = toRgba().rgb
    override val rgba = toRgba().rgba
    override val r = toRgba().r
    override val g = toRgba().g
    override val b = toRgba().b
    override val rgbHex:String = toRgba().rgbHex

    override fun brighten(strength: Double):Color = toRgba().brighten(strength)
    override fun darken(strength: Double):Color = toRgba().darken(strength)
    override fun saturate(strength: Double):Color = toRgba().saturate(strength)
    override fun desaturate(strength: Double):Color = toRgba().desaturate(strength)
    override fun withAlpha(alpha: Float) = HslColor(h, s, l, alpha)

    /*val displayable: Boolean
        get() = (s in 0..1) && (l in 0..1) && (alpha in 0..1)

    fun brighter(strength: Double = 1.0) = HslColor(h, s, (l * brighter.pow(strength)), alpha)
    fun darker(strength: Double = 1.0) = HslColor(h, s, (l * brighter.pow(strength)), alpha)*/

    override fun equals(other: Any?):Boolean = (other != null && other is HslColor && h == other.h && s == other.s && l == other.l && alpha == other.alpha)

    override fun toString() = "hsla(${h.deg}°, ${s*100}%, ${l*100}%, $alpha)"
    override fun hashCode(): Int {
        var result = h.hashCode()
        result = 31 * result + s.hashCode()
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