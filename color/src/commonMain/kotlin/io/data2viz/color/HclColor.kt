/*
 * Copyright (c) 2018-2019. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.color

import io.data2viz.math.*
import kotlin.math.max

/**
 * Create a color in the HCL color space (CIELCH)
 *
 * This Colorspace is designed to accord with human perception of color.
 * HCL has been adopted by information visualization practitioners to present data without the bias implicit in
 * using varying saturation.
 *
 * @param h hue: Angle
 * @param c chroma: Double, the upper bound for chroma depends on hue and luminance (typically in 0..230)
 * @param lightness: Percent, value between 0% and 100%
 * @param alpha: Opacity, value between 0% and 100%
 */
public class HclColor

@Deprecated("Deprecated", ReplaceWith("Colors.hcl(h,c,l,alpha)", "io.data2viz.colors.Colors"))
internal constructor(
    public val h: Angle,
    public val c: Double, lightness: Percent, a: Percent = 100.pct) : Color {

    public val l: Percent = lightness//.coerceIn(.0, 100.0)
    override val alpha: Percent = a.coerceToDefault()

    override val rgb: Int = toRgb().rgb
    override val rgba: String = toRgb().rgba
    override val r: Int = toRgb().r
    override val g: Int = toRgb().g
    override val b: Int = toRgb().b
    override val rgbHex: String = toRgb().rgbHex

    override fun luminance(): Percent = toRgb().luminance()
    override fun contrast(other:Color): Double = toRgb().contrast(other)

    override fun toRgb():RgbColor = toLab().toRgb()
    override fun toLab(): LabColor = toLaba()
    override fun toHcl(): HclColor = this
    override fun toHsl(): HslColor = toLab().toHsl()

    override fun brighten(strength: Double): Color = Colors.hcl(h, c, (l + (Kn * strength).pct), alpha)
    override fun darken(strength: Double): Color = Colors.hcl(h, c, (l - (Kn * strength).pct), alpha)
    override fun saturate(strength: Double): Color = Colors.hcl(h, max(.0, (c + (Kn * strength))), l, alpha)
    override fun desaturate(strength: Double): Color = Colors.hcl(h, max(.0, (c - (Kn * strength))), l, alpha)
    override fun opacify(strength: Double): Color = withAlpha(Percent(alpha * strength))
    override fun withAlpha(alpha: Percent): HclColor = Colors.hcl(h, c, l, alpha)
    override fun withHue(hue: Angle): HclColor = Colors.hcl(hue, c, l, alpha)

    public fun isAchromatic(): Boolean = (c == .0) || (l.value <= .0) || (l.value >= 1.0)

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

    override fun toString(): String = "HCL(${h.deg}°, $c, $l%, alpha=$alpha)"
}