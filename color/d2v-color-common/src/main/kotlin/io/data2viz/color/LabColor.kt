package io.data2viz.color

import io.data2viz.math.*

/**
 * Create a color in the LAB color space (CIE L*a*b* D65 whitepoint)
 *
 * @param lightness: Percent, value between 0% and 100%
 * @param aComponent "a"-component: Double for green-red between -128 and +128
 * @param bComponent "b"-component: Double for blue-yellow between -128 and +128
 * @param alpha: Opacity, value between 0% and 100%
 */
class LabColor
@Deprecated("Deprecated", ReplaceWith("Colors.lab(labL,labA,labB,alpha)", "io.data2viz.colors.Colors"))
internal constructor(lightness: Percent, aComponent: Double, bComponent: Double, a: Percent = 100.pct) : Color {

    // TODO : need to choose behavior: if values are coerced the results are not what expected when referring to test values from chroma.js
    val labL = lightness//.coerceIn(.0, 100.0)
    val labA = aComponent//.coerceIn(-128.0, 128.0)
    val labB = bComponent//.coerceIn(-128.0, 128.0)
    override val alpha = a.coerceToDefault()

    override val rgb = toRgb().rgb
    override val rgba = toRgb().rgba
    override val r = toRgb().r
    override val g = toRgb().g
    override val b = toRgb().b
    override val rgbHex: String = toRgb().rgbHex

    override fun luminance() = toRgb().luminance()
    override fun contrast(other:Color) = toRgb().contrast(other)

    override fun toRgb(): RgbColor = toRgba()
    override fun toLab(): LabColor = this
    override fun toHcl(): HclColor = toHcla()
    override fun toHsl(): HslColor = toRgb().toHsl()

    override fun brighten(strength: Double): Color = Colors.lab((labL + (Kn * strength).pct), labA, labB, alpha)
    override fun darken(strength: Double): Color = Colors.lab((labL - (Kn * strength).pct), labA, labB, alpha)
    override fun saturate(strength: Double): Color = toHcl().saturate(strength)
    override fun desaturate(strength: Double): Color = toHcl().desaturate(strength)
    override fun withAlpha(alpha: Percent) = Colors.lab(labL, labA, labB, alpha)
    override fun withHue(hue: Angle) = toHcl().withHue(hue)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is Color) return false

        if (rgb != other.rgb) return false
        if (alpha != other.alpha) return false

        return true
    }

    override fun hashCode(): Int {
        var result = rgb
        result = 31 * result + alpha.hashCode()
        return result
    }

    override fun toString() = "LAB($labL%, $labA, $labB, alpha=$alpha)"
}