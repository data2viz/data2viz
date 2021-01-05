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

import io.data2viz.math.Angle
import io.data2viz.math.Percent

public data class ColorStop(val percent:Percent, val color: Color)

public interface ColorOrGradient

public interface Gradient : ColorOrGradient {
    public val colorStops:List<ColorStop>
}

public interface Color : ColorOrGradient {
    public val rgb:Int

    /**
     * rgba string conforming to CSS specification `rgba(R, G, B, A)`
     * R (red), G (green), and B (blue) can be either <integer>s or <percentage>s,
     * where the number 255 corresponds to 100%.
     * A (alpha) can be a <number> between 0 and 1, or a <percentage>,
     * where the number 1 corresponds to 100% (full opacity).
     */
    public val rgba:String

    public val r:Int
    public val g:Int
    public val b:Int
    public val alpha:Percent
    public val rgbHex:String

    /**
     * The relative brightness normalized to 0% for darkest black and 100% for lightest white.
     * According to the WCAG definition: https://www.w3.org/TR/2008/REC-WCAG20-20081211/#relativeluminancedef
     */
    public fun luminance():Percent

    /**
     * The contrast ratio between 2 colors.
     * A minimum contrast of 4.5:1 is recommended to ensure that text is still readable against a background color.
     * According to the WCAG definition: https://www.w3.org/TR/2008/REC-WCAG20-20081211/#contrast-ratiodef
     */
    public fun contrast(other:Color):Double

    /**
     * The value of the hue in the HCL colorspace (red = 40.deg, green = 136.deg, blue = 306.deg)
     * HCL is designed to accord with human perception of color, so this colorspace provides better transition in term
     * of "hue perception".
     */
    //val hue: Angle

    public fun toRgb():RgbColor
    public fun toLab():LabColor
    public fun toHcl():HclColor
    public fun toHsl():HslColor

    public fun brighten(strength: Double = 1.0):Color
    public fun darken(strength: Double = 1.0):Color
    public fun saturate(strength: Double = 1.0):Color
    public fun desaturate(strength: Double = 1.0):Color

    /**
     * Make a color more (strength > 1.0) or less (strength < 1.0) transparent.
     * Note that a fully transparent color (alpha = 0%) will remain transparent.
     */
    public fun opacify(strength: Double = 1.0):Color
    public fun withAlpha(alpha: Percent):Color

    /**
     * Change the perceived lightness of the color and return a new Color.
     * TODO : when luminance() & contrast()  VS  darken() & brighten()   behavior will be simplified (seems to do the same thing now but produce different results)
     */
//    fun withLuminance(luminance: Percent):Color

    /**
     * Change the perceived hue of the color and return a new Color.
     * Based on the value of the hue in the HCL colorspace (red = 40.deg, green = 136.deg, blue = 306.deg)
     */
    public fun withHue(hue: Angle):Color
}
