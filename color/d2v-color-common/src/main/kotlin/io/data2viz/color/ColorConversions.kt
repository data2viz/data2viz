package io.data2viz.color

import io.data2viz.math.*
import kotlin.math.*

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

internal const val deg60toRad = 1.047198
internal const val deg240toRad = 4.18879

internal val angle120deg = 120.deg


internal fun RgbColor.toLaba(): LabColor {
    val labB = rgb2xyz(r)
    val labA = rgb2xyz(g)
    val labL = rgb2xyz(b)
    val x = xyz2lab((0.4124564f * labB + 0.3575761f * labA + 0.1804375f * labL) / Xn)
    val y = xyz2lab((0.2126729f * labB + 0.7151522f * labA + 0.0721750f * labL) / Yn)
    val z = xyz2lab((0.0193339f * labB + 0.1191920f * labA + 0.9503041f * labL) / Zn)
    return Colors.lab((116.0 * y - 16).pct, 500.0 * (x - y), 200.0 * (y - z), alpha)
}

internal fun RgbColor.toHsla(): HslColor {
    val rPercent = r / 255.0
    val gPercent = g / 255.0
    val bPercent = b / 255.0
    val minPercent = minOf(rPercent, gPercent, bPercent)
    val maxPercent = maxOf(rPercent, gPercent, bPercent)

    var h = .0
    var s = maxPercent - minPercent
    val l = (maxPercent + minPercent) / 2f

    if (s != .0) {
        h = when {
            (rPercent == maxPercent) -> if (gPercent < bPercent) ((gPercent - bPercent) / s) + 6f else ((gPercent - bPercent) / s)
            (gPercent == maxPercent) -> (bPercent - rPercent) / s + 2f
            else -> (rPercent - gPercent) / s + 4f
        }
        s /= if (l < 0.5f) maxPercent + minPercent else 2 - maxPercent - minPercent
        h *= 60.0
    } else {
        s = if (l > 0 && l < 1) .0 else h
    }
    return Colors.hsl(h.deg, Percent(s), Percent(l), alpha)
}

internal fun LabColor.toRgba(): RgbColor {
    // map CIE LAB to CIE XYZ
    var y = ((labL.value * 100.0) + 16) / 116f
    var x = y + (labA / 500f)
    var z = y - (labB / 200f)
    y = Yn * lab2xyz(y)
    x = Xn * lab2xyz(x)
    z = Zn * lab2xyz(z)

    // map CIE XYZ to RGB
    return Colors.rgb(
        xyz2rgb(3.2404542f * x - 1.5371385f * y - 0.4985314f * z),
        xyz2rgb(-0.9692660f * x + 1.8760108f * y + 0.0415560f * z),
        xyz2rgb(0.0556434f * x - 0.2040259f * y + 1.0572252f * z),
        alpha
    )
}

// TODO : use rad (faster)
internal fun HslColor.toRgba(): RgbColor =
    if (isAchromatic())     // achromatic
        Colors.rgb(
            (l.value * 255).roundToInt(),
            (l.value * 255).roundToInt(),
            (l.value * 255).roundToInt(),
            alpha
        )
    else {
        val q = if (l < 50.pct) l * (100.pct + s) else l + s - l * s
        val p = Percent(2 * l.value - q.value)
        Colors.rgb(
            (hue2rgb(p.value, q.value, h + angle120deg) * 255).roundToInt(),
            (hue2rgb(p.value, q.value, h) * 255).roundToInt(),
            (hue2rgb(p.value, q.value, h - angle120deg) * 255).roundToInt(),
            alpha
        )
    }

internal fun HclColor.toLaba() = Colors.lab(l, (h.cos * c), (h.sin * c), alpha)

internal fun LabColor.toHcla(): HclColor {
    val hue = Angle(atan2(labB, labA)).normalize()
    val c = sqrt(labA * labA + labB * labB)
    return Colors.hcl(hue, c, labL, alpha)
}

// TODO use rad (faster)
private fun hue2rgb(p: Double, q: Double, hue: Angle): Double {
    val hd = hue.normalize()
    return when {
        hd.rad < deg60toRad -> (p + (q - p) * (hd.rad / deg60toRad))
        hd.rad < kotlin.math.PI -> q
        hd.rad < deg240toRad -> (p + (q - p) * ((deg240toRad - hd.rad) / deg60toRad))
        else -> p
    }
}

internal fun RgbColor.toLuminance(): Percent {

    fun Int.chan2Lumi(): Double {
        val x = this / 255.0
        return if (x <= 0.03928) x / 12.92
        else ((x+0.055)/1.055).pow(2.4)
    }

    return Percent(0.2126 * r.chan2Lumi() + 0.7152 * g.chan2Lumi() + 0.0722 * b.chan2Lumi()).coerceToDefault()
}


private fun xyz2lab(value: Float): Float =
    if (value > t3)
        value.pow(1 / 3f)
    else
        (value / t2 + t0)

private fun rgb2xyz(value: Int): Float {
    val percent = value.toFloat() / 255f
    return if (percent <= 0.04045f) (percent / 12.92f) else ((percent + 0.055f) / 1.055f).pow(2.4f)
}

private fun lab2xyz(value: Double): Double =
    if (value > t1)
        (value * value * value)
    else
        (t2 * (value - t0))

private fun xyz2rgb(value: Double): Int =
    if (value <= 0.0031308f)
        round(12.92f * value * 255).toInt()
    else
        round(255 * (1.055 * value.pow(1 / 2.4) - 0.055)).toInt()
