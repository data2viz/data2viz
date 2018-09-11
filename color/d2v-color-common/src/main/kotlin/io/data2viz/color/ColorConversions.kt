package io.data2viz.color

import io.data2viz.math.deg
import kotlin.math.pow
import kotlin.math.round


internal const val darker = 0.7
internal const val brighter = 1 / darker

/**
 * Conversion references values
 */
internal const val Kn = 18f
internal const val Xn = 0.950470f                  // D65 standard referent
internal const val Yn = 1f
internal const val Zn = 1.088830f
internal const val t0 = 4f / 29f
internal const val t1 = 6f / 29f
internal const val t2 = 3f * t1 * t1
internal const val t3 = t1 * t1 * t1


val String.color: Color
    get():Color {
        val regex = """^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$""".toRegex()
        require(this.matches(regex)) {
            "Conversion of string to io.data2viz.color.getColor works for encoded colors like #12abCD"
        }
        return Color(substring(1).toInt(16))
    }


fun Color.toLab(): LabColor {
    val labB = rgb2xyz(r)
    val labA = rgb2xyz(g)
    val labL = rgb2xyz(b)
    val x = xyz2lab((0.4124564 * labB + 0.3575761 * labA + 0.1804375 * labL) / Xn)
    val y = xyz2lab((0.2126729 * labB + 0.7151522 * labA + 0.0721750 * labL) / Yn)
    val z = xyz2lab((0.0193339 * labB + 0.1191920 * labA + 0.9503041 * labL) / Zn)
    return LabColor(116 * y - 16, 500 * (x - y), 200 * (y - z), alpha)
}

fun Color.toHsla(): HslColor {
    val rPercent = r.toFloat() / 255f
    val gPercent = g.toFloat() / 255f
    val bPercent = b.toFloat() / 255f
    val minPercent = minOf(rPercent, gPercent, bPercent)
    val maxPercent = maxOf(rPercent, gPercent, bPercent)

    var h = 0f
    var s = maxPercent - minPercent
    val l = (maxPercent + minPercent) / 2f

    if (s != 0f) {
        h = when {
            (rPercent == maxPercent) -> if (gPercent < bPercent) ((gPercent - bPercent) / s) + 6f else ((gPercent - bPercent) / s)
            (gPercent == maxPercent) -> (bPercent - rPercent) / s + 2f
            else -> (rPercent - gPercent) / s + 4f
        }
        s /= if (l < 0.5f) maxPercent + minPercent else 2 - maxPercent - minPercent
        h *= 60f
    } else {
        s = if (l > 0 && l < 1) 0f else h
    }
    return HslColor(h.deg, s, l, alpha)
}

fun LabColor.toRgba(): Color {
    // map CIE LAB to CIE XYZ
    var y = (l + 16) / 116f
    var x = y + (a / 500f)
    var z = y - (b / 200f)
    y = Yn * lab2xyz(y)
    x = Xn * lab2xyz(x)
    z = Zn * lab2xyz(z)

    // map CIE XYZ to RGB
    return rgba(
            xyz2rgb(3.2404542f * x - 1.5371385f * y - 0.4985314f * z),
            xyz2rgb(-0.9692660f * x + 1.8760108f * y + 0.0415560f * z),
            xyz2rgb(0.0556434f * x - 0.2040259f * y + 1.0572252f * z),
            alpha)
}


internal fun xyz2lab(value: Double): Double =
        if (value > t3)
            value.pow(1 / 3.0)
        else
            (value / t2 + t0)

internal fun rgb2xyz(value: Int): Double {
    val percent = value.toFloat() / 255f
    return if (percent <= 0.04045f) (percent / 12.92) else ((percent + 0.055) / 1.055).pow(2.4)
}

internal fun lab2xyz(value: Float): Float =
        if (value > t1)
            (value * value * value)
        else
            (t2 * (value - t0))

internal fun xyz2rgb(value: Float): Int =
        if (value <= 0.0031308f)
            round(12.92f * value * 255).toInt()
        else
            round(255 * (1.055f * value.pow(1 / 2.4f) - 0.055f)).toInt()
