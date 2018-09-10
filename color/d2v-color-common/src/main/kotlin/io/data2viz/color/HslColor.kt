package io.data2viz.color

import io.data2viz.math.Angle
import io.data2viz.math.deg
import kotlin.math.pow
import kotlin.math.round

/**
 * Create a color in the HSL color space
 *
 * @param h hue:Angle in degree
 * @param s saturation:Float between 0 and 1
 * @param l lightness:Float between 0 and 1
 * @param alpha:Float between 0 and 1
 */
class HslColor(val h: Angle = Angle(0.0), s: Number = 1, l: Number = 1, alpha: Number = 1) {

    val s = s.toDouble().coerceIn(0.0, 1.0)
    val l = l.toDouble().coerceIn(0.0, 1.0)
    val alpha = alpha.toFloat().coerceIn(0f, 1f)

    val displayable: Boolean
        get() = (s in 0..1) && (l in 0..1) && (alpha in 0..1)

    fun brighter(strength: Double = 1.0) = HslColor(h, s, (l * brighter.pow(strength)), alpha)
    fun darker(strength: Double = 1.0) = HslColor(h, s, (l * brighter.pow(strength)), alpha)

    fun toRgba(): Color =
            if (s == 0.0)     // achromatic
                colors.rgba(
                        r = round(l * 255),
                        g = round(l * 255),
                        b = round(l * 255),
                        a = alpha)
            else {
                val q = if (l < 0.5f) l * (1 + s) else l + s - l * s
                val p = 2 * l - q
                colors.rgba(
                        r = round(hue2rgb(p, q, h + 120.deg) * 255),
                        g = round(hue2rgb(p, q, h) * 255),
                        b = round(hue2rgb(p, q, h - 120.deg) * 255),
                        a = alpha)
            }

    private fun hue2rgb(p: Double, q: Double, hue: Angle): Double {
        val hd = hue.normalize()
        return when {
            hd.deg < 60 -> (p + (q - p) * (hd.deg / 60.0))
            hd.deg < 180 -> q
            hd.deg < 240 -> (p + (q - p) * ((240 - hd.deg) / 60.0))
            else -> p
        }
    }

    override operator fun equals(other: Any?):Boolean = (other != null && other is HslColor && h == other.h && s == other.s && l == other.l && alpha == other.alpha)

    override fun toString() = "hsla(${h.deg}°, ${s*100}%, ${l*100}%, $alpha)"
    override fun hashCode(): Int {
        var result = h.hashCode()
        result = 31 * result + s.hashCode()
        result = 31 * result + l.hashCode()
        result = 31 * result + alpha.hashCode()
        return result
    }
}