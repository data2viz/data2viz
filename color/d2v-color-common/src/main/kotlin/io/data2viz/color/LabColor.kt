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
class LabColor(l: Number = 100, a: Number = 0, b: Number = 0, alpha: Number = 1):Color {

    // TODO check for coerce values (coerce needed ?)
    // TODO check for type
    val labL: Float = l.toFloat()
    val labA: Float = a.toFloat()
    val labB: Float = b.toFloat()

    override val rgb = toRgba().rgb
    override val rgba = toRgba().rgba
    override val r = toRgba().r
    override val g = toRgba().g
    override val b = toRgba().b
    override val rgbHex:String = toRgba().rgbHex

    override fun brighten(strength: Double):Color = LabColor((labL + (Kn * strength)).toFloat(), labA, labB, alpha)
    override fun darken(strength: Double):Color = LabColor((labL - (Kn * strength)).toFloat(), labA, labB, alpha)
    override fun saturate(strength: Double):Color = toHcla().saturate(strength)
    override fun desaturate(strength: Double):Color = toHcla().desaturate(strength)


    override val alpha: Float = alpha.toFloat().coerceIn(0f, 1f)

    override fun toString() = "laba($labL, $labA, $labB, $alpha)"
}