package io.data2viz.color

/**
 * Create a color in the LAB color space (CIE L*a*b* D65 whitepoint)
 *
 * @param labL lightness:Float between 0 and 100
 * @param labA "a"-component:Float for green-red between -128 and +128
 * @param labB "b"-component:Float for blue-yellow between -128 and +128
 * @param alpha:Opacity between 0 and 1
 */
class LabColor

@Deprecated("Deprecated", ReplaceWith("Colors.lab(labL,labA,labB,alpha)", "io.data2viz.colors.Colors"))
internal constructor(val labL: Double, val labA: Double, val labB: Double, a: Double = 1.0):Color {

    override val alpha = a.coerceIn(.0, 1.0)

    override val rgb = toRgbColor().rgb
    override val rgba = toRgbColor().rgba
    override val r = toRgbColor().r
    override val g = toRgbColor().g
    override val b = toRgbColor().b
    override val rgbHex:String = toRgbColor().rgbHex

    override fun toRgbColor():RgbColor = toRgba()
    override fun brighten(strength: Double):Color = Colors.lab((labL + (Kn * strength)), labA, labB, alpha)
    override fun darken(strength: Double):Color = Colors.lab((labL - (Kn * strength)), labA, labB, alpha)
    override fun saturate(strength: Double):Color = toHcla().saturate(strength)
    override fun desaturate(strength: Double):Color = toHcla().desaturate(strength)
    override fun withAlpha(alpha: Double) = Colors.lab(labL, labA, labB, alpha)

    override fun toString() = "laba($labL, $labA, $labB, $alpha)"
}