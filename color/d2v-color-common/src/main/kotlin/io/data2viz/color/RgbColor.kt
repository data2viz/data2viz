package io.data2viz.color

import io.data2viz.math.deg
import kotlin.math.pow
import kotlin.math.round

/**
 * Implementation of Color as an rgb integer and an alpha channel.
 *
 * Provides conversion with hex string notation.
 *
 * See https://developer.mozilla.org/en-US/docs/Web/CSS/color_value and
 * https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Colors/Color_picker_tool
 *
 * TODO must be immutable
 * TODO extract a color interface including alpha (alpha is common to all color spaces)
 */
class RgbColor(var rgb: Int = 0xffffff, var _alpha: Number = 1.0f): ColorOrGradient {

    // TODO : coerce in place of require check ??
    // TODO store color value in double
    var r: Int
        get() = (rgb shr 16) and 0xff
        set(value) {
            rgb = (rgb and 0x00ffff) + (value.coerceIn(0, 255) shl 16)
        }

    var g: Int
        get() = (rgb shr 8) and 0xff
        set(value) {
            rgb = (rgb and 0xff00ff) + (value.coerceIn(0, 255) shl 8)
        }

    var b: Int
        get() = rgb and 0xff
        set(value) {
            rgb = (rgb and 0xffff00) + value.coerceIn(0, 255)
        }

    var alpha: Number
        get() = _alpha
        set(value) {
            _alpha = value.toFloat().coerceIn(0f, 1f)
        }

    fun rgba(r: Number, g: Number, b: Number, a: Number) {
        this.r = round(r.toDouble()).toInt()
        this.g = round(g.toDouble()).toInt()
        this.b = round(b.toDouble()).toInt()
        alpha = a.toFloat()
    }

    // TODO : always true ?!
    val displayable: Boolean
        get() = (r in 0..255) && (g in 0..255) && (b in 0..255) && (alpha in 0..1)

    fun brighter(strength: Double = 1.0) {
        val str = brighter.pow(strength)
        return rgba(r * str, g * str, b * str, alpha)
    }

    fun darker(strength: Double = 1.0) {
        val str = darker.pow(strength)
        return rgba(r * str, g * str, b * str, alpha)
    }

    fun toHsla(): HslColor {
        val rPercent = r.toFloat() / 255f
        val gPercent = g.toFloat() / 255f
        val bPercent = b.toFloat() / 255f
        val minPercent = minOf(rPercent, gPercent, bPercent)
        val maxPercent = maxOf(rPercent, gPercent, bPercent)

        var h = 0f
        var s = maxPercent - minPercent
        val l = (maxPercent + minPercent) / 2f

        if (s != 0f) {
            when {
                (rPercent == maxPercent) -> h = if (gPercent < bPercent) ((gPercent - bPercent) / s) + 6f else ((gPercent - bPercent) / s)
                (gPercent == maxPercent) -> h = (bPercent - rPercent) / s + 2f
                else -> h = (rPercent - gPercent) / s + 4f
            }
            s /= if (l < 0.5f) maxPercent + minPercent else 2 - maxPercent - minPercent
            h *= 60f
        } else {
            s = if (l > 0 && l < 1) 0f else h
        }
        return HslColor(h.deg, s, l, alpha)
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

    fun withAlpha(alpha: Number): RgbColor = RgbColor(rgb, alpha)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as RgbColor

        if (rgb != other.rgb) return false
        if (_alpha != other._alpha) return false

        return true
    }

    override fun hashCode(): Int {
        var result = rgb
        result = 31 * result + _alpha.hashCode()
        return result
    }

    override fun toString() = if (alpha.toFloat() < 1.0) rgba else rgbHex

}