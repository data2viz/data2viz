package io.data2viz.color

import io.data2viz.math.deg
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.sqrt

/**
 * Create a color in the LAB color space (CIE L*a*b* D65 whitepoint)
 *
 * @param l lightness:Float between 0 and 100
 * @param a "a"-component:Float for green-red between -128 and +128
 * @param b "b"-component:Float for blue-yellow between -128 and +128
 * @param alpha:Opacity between 0 and 1
 */
class LabColor(l: Number = 100, a: Number = 0, b: Number = 0, alpha: Number = 1) {

    // TODO check for coerce values (coerce needed ?)
    // TODO check for type
    val l: Float = l.toFloat().coerceIn(0f, 100f)
    val a: Float = a.toFloat().coerceIn(-128f, 128f)
    val b: Float = b.toFloat().coerceIn(-128f, 128f)
    val alpha: Float = alpha.toFloat().coerceIn(0f, 1f)

    fun brighter(strength: Double = 1.0) = LabColor((l + (Kn * strength)).toFloat(), a, b, alpha)
    fun darker(strength: Double = 1.0) = LabColor((l - (Kn * strength)).toFloat(), a, b, alpha)



    // TODO use RAD2DEG from angle ?
    fun toHcla(): HclColor {
        val h = atan2(b.toDouble(), a.toDouble()) * (180 / PI)
        val hue = (h.deg).normalize()
        return HclColor(hue, sqrt(a.toDouble() * a.toDouble() + b.toDouble() * b.toDouble()), l, alpha)
    }

    override fun toString() = "laba($l, $a, $b, $alpha)"
}