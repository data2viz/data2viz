package io.data2viz.color

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


val String.color: RgbColor
    get():RgbColor {
        val regex = """^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$""".toRegex()
        if (!this.matches(regex)) error("Conversion of string to io.data2viz.color.getColor works for encoded colors like #12abCD")
        return RgbColor(this.substring(1).toInt(16))
    }


fun RgbColor.toLab(): LabColor {
    val labB = rgb2xyz(r)
    val labA = rgb2xyz(g)
    val labL = rgb2xyz(b)
    val x = xyz2lab((0.4124564 * labB + 0.3575761 * labA + 0.1804375 * labL) / Xn)
    val y = xyz2lab((0.2126729 * labB + 0.7151522 * labA + 0.0721750 * labL) / Yn)
    val z = xyz2lab((0.0193339 * labB + 0.1191920 * labA + 0.9503041 * labL) / Zn)
    return LabColor(116 * y - 16, 500 * (x - y), 200 * (y - z), alpha)
}


fun LabColor.toRgba(): RgbColor {
    // map CIE LAB to CIE XYZ
    var y = (l + 16) / 116f
    var x = y + (a / 500f)
    var z = y - (b / 200f)
    y = Yn * lab2xyz(y)
    x = Xn * lab2xyz(x)
    z = Zn * lab2xyz(z)

    // map CIE XYZ to RGB
    return colors.rgba(
            r = xyz2rgb(3.2404542f * x - 1.5371385f * y - 0.4985314f * z),
            g = xyz2rgb(-0.9692660f * x + 1.8760108f * y + 0.0415560f * z),
            b = xyz2rgb(0.0556434f * x - 0.2040259f * y + 1.0572252f * z),
            a = alpha)
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
