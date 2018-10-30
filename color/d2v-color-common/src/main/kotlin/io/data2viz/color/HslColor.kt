package io.data2viz.color

import io.data2viz.math.Angle

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
internal constructor(hue: Angle, saturation: Double, lightness: Double, a: Double = 1.0):Color {

    val h = hue
    val s = saturation.coerceIn(.0, 1.0)
    val l = lightness.coerceIn(.0, 1.0)
    override val alpha = a.coerceIn(.0, 1.0)

    override val rgb = toRgbColor().rgb
    override val rgba = toRgbColor().rgba
    override val r = toRgbColor().r
    override val g = toRgbColor().g
    override val b = toRgbColor().b
    override val rgbHex:String = toRgbColor().rgbHex

    override fun toRgbColor():RgbColor = toRgba()
    override fun brighten(strength: Double):Color = toRgbColor().brighten(strength)
    override fun darken(strength: Double):Color = toRgbColor().darken(strength)
    override fun saturate(strength: Double):Color = toRgbColor().saturate(strength)
    override fun desaturate(strength: Double):Color = toRgbColor().desaturate(strength)
    override fun withAlpha(alpha: Double) = Colors.hsl(h, s, l, alpha)

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