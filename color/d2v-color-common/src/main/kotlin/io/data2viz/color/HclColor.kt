package io.data2viz.color

import io.data2viz.math.Angle

/**
 * Create a color in the HCL color space (CIELCH)
 *
 * @param h hue:Angle in degree
 * @param c chroma:Double, the upper bound for chroma depends on hue and luminance
 * @param l luminance:Float a value in the range [0,100] giving the luminance of the colour (in percent)
 * @param alpha:Float between 0 and 1
 */
class HclColor(val h: Angle = Angle(0.0), c: Number = 1, l: Number = 100, alpha: Number = 1) {

    val c = c.toDouble()
    val l = l.toFloat().coerceIn(0f, 100f)
    val alpha = alpha.toFloat().coerceIn(0f, 1f)

    /*val displayable: Boolean
        get() = (s in 0..1) && (l in 0..1) && (alpha in 0..1)*/

    fun brighter(strength: Double = 1.0) = HclColor(h, c, (l + (Kn * strength)).toFloat(), alpha)
    fun darker(strength: Double = 1.0) = HclColor(h, c, (l - (Kn * strength)).toFloat(), alpha)

    override operator fun equals(other: Any?):Boolean = (other != null && other is HclColor && h == other.h && c == other.c && l == other.l && alpha == other.alpha)

    override fun toString() = "hcla(${h.deg}°, $c, ${l*100}%, $alpha)"
}