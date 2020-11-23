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

@file:Suppress("unused", "DEPRECATION")

package io.data2viz.color

import io.data2viz.geom.Point
import io.data2viz.math.Angle
import io.data2viz.math.Percent
import io.data2viz.math.pct


// TODO : move to common and remove expect when available
internal expect fun Int.toString(radix: Int): String


public object Colors {

    /***************************** COLORSPACE CONSTRUCTORS *******************************************/

    /**
     * Instantiate a color using its rgb Int value and alpha. Should be used with hexadecimal literal.
     * @sample: `Colors.rgb(0x0b0b0b, .5)`
     */
    public fun rgb(rgb:Int, alpha: Percent = 100.pct): RgbColor = RgbColor(rgb, alpha)

    /**
     * Instantiate a color using its r,g,b Int values and alpha
     * @sample: `Colors.rgb(128, 128, 128, .5)`
     */
    public fun rgb(red: Int, green: Int, blue: Int, alpha: Percent = 100.pct): RgbColor {
        val rgb = (red.coerceIn(0, 255) shl 16) + (green.coerceIn(0, 255) shl 8) + blue.coerceIn(0, 255)
        return rgb(rgb, alpha)
    }

    public fun lab(lightness: Percent, aComponent: Double, bComponent: Double, alpha: Percent = 100.pct): LabColor =
            LabColor(lightness, aComponent, bComponent, alpha)

    public fun lab(lightness: Percent, aComponent: Int, bComponent: Int, alpha: Percent = 100.pct): LabColor =
            LabColor(lightness, aComponent.toDouble(), bComponent.toDouble(), alpha)

    public fun hsl(hue: Angle, saturation: Percent, lightness: Percent, alpha: Percent = 100.pct): HslColor =
            HslColor(hue, saturation, lightness, alpha)

    public fun hcl(hue: Angle, chroma: Double, luminance: Percent, alpha: Percent = 100.pct): HclColor =
            HclColor(hue, chroma, luminance, alpha)

    public fun hcl(hue: Angle, chroma: Int, luminance: Percent, alpha: Percent = 100.pct): HclColor =
            HclColor(hue, chroma.toDouble(), luminance, alpha)

    public fun lch(luminance: Percent, chroma: Double, hue: Angle, alpha: Percent = 100.pct): HclColor =
            hcl(hue, chroma, luminance, alpha)

    public fun lch(luminance: Percent, chroma: Int, hue: Angle, alpha: Percent = 100.pct): HclColor = hcl(hue, chroma, luminance, alpha)


    /***************************** GRADIENTS *********************************************************/

    public object Gradient {
        public fun linear(from:Point, to:Point): LinearGradientFirstColorBuilder = LinearGradientFirstColorBuilder(from, to)
        public fun radial(center:Point, radius:Double): RadialGradientFirstColorBuilder = RadialGradientFirstColorBuilder(center, radius)
    }


    /***************************** NAMED WEB COLORS ******************************************************/

    public object Web {
        public val aliceblue               :Color = 0xf0f8ff.col
        public val antiquewhite            :Color = 0xfaebd7.col
        public val aqua                    :Color = 0x00ffff.col
        public val aquamarine              :Color = 0x7fffd4.col
        public val azure                   :Color = 0xf0ffff.col
        public val beige                   :Color = 0xf5f5dc.col
        public val bisque                  :Color = 0xffe4c4.col
        public val black                   :Color = 0x000000.col
        public val blanchedalmond          :Color = 0xffebcd.col
        public val blue                    :Color = 0x0000ff.col
        public val blueviolet              :Color = 0x8a2be2.col
        public val brown                   :Color = 0xa52a2a.col
        public val burlywood               :Color = 0xdeb887.col
        public val cadetblue               :Color = 0x5f9ea0.col
        public val chartreuse              :Color = 0x7fff00.col
        public val chocolate               :Color = 0xd2691e.col
        public val coral                   :Color = 0xff7f50.col
        public val cornflowerblue          :Color = 0x6495ed.col
        public val cornsilk                :Color = 0xfff8dc.col
        public val crimson                 :Color = 0xdc143c.col
        public val cyan                    :Color = 0x00ffff.col
        public val darkblue                :Color = 0x00008b.col
        public val darkcyan                :Color = 0x008b8b.col
        public val darkgoldenrod           :Color = 0xb8860b.col
        public val darkgray                :Color = 0xa9a9a9.col
        public val darkgreen               :Color = 0x006400.col
        public val darkgrey                :Color = 0xa9a9a9.col
        public val darkkhaki               :Color = 0xbdb76b.col
        public val darkmagenta             :Color = 0x8b008b.col
        public val darkolivegreen          :Color = 0x556b2f.col
        public val darkorange              :Color = 0xff8c00.col
        public val darkorchid              :Color = 0x9932cc.col
        public val darkred                 :Color = 0x8b0000.col
        public val darksalmon              :Color = 0xe9967a.col
        public val darkseagreen            :Color = 0x8fbc8f.col
        public val darkslateblue           :Color = 0x483d8b.col
        public val darkslategray           :Color = 0x2f4f4f.col
        public val darkslategrey           :Color = 0x2f4f4f.col
        public val darkturquoise           :Color = 0x00ced1.col
        public val darkviolet              :Color = 0x9400d3.col
        public val deeppink                :Color = 0xff1493.col
        public val deepskyblue             :Color = 0x00bfff.col
        public val dimgray                 :Color = 0x696969.col
        public val dimgrey                 :Color = 0x696969.col
        public val dodgerblue              :Color = 0x1e90ff.col
        public val firebrick               :Color = 0xb22222.col
        public val floralwhite             :Color = 0xfffaf0.col
        public val forestgreen             :Color = 0x228b22.col
        public val fuchsia                 :Color = 0xff00ff.col
        public val gainsboro               :Color = 0xdcdcdc.col
        public val ghostwhite              :Color = 0xf8f8ff.col
        public val gold                    :Color = 0xffd700.col
        public val goldenrod               :Color = 0xdaa520.col
        public val gray                    :Color = 0x808080.col
        public val green                   :Color = 0x008000.col
        public val greenyellow             :Color = 0xadff2f.col
        public val grey                    :Color = 0x808080.col
        public val honeydew                :Color = 0xf0fff0.col
        public val hotpink                 :Color = 0xff69b4.col
        public val indianred               :Color = 0xcd5c5c.col
        public val indigo                  :Color = 0x4b0082.col
        public val ivory                   :Color = 0xfffff0.col
        public val khaki                   :Color = 0xf0e68c.col
        public val lavender                :Color = 0xe6e6fa.col
        public val lavenderblush           :Color = 0xfff0f5.col
        public val lawngreen               :Color = 0x7cfc00.col
        public val lemonchiffon            :Color = 0xfffacd.col
        public val lightblue               :Color = 0xadd8e6.col
        public val lightcoral              :Color = 0xf08080.col
        public val lightcyan               :Color = 0xe0ffff.col
        public val lightgoldenrodyellow    :Color = 0xfafad2.col
        public val lightgray               :Color = 0xd3d3d3.col
        public val lightgreen              :Color = 0x90ee90.col
        public val lightgrey               :Color = 0xd3d3d3.col
        public val lightpink               :Color = 0xffb6c1.col
        public val lightsalmon             :Color = 0xffa07a.col
        public val lightseagreen           :Color = 0x20b2aa.col
        public val lightskyblue            :Color = 0x87cefa.col
        public val lightslategray          :Color = 0x778899.col
        public val lightslategrey          :Color = 0x778899.col
        public val lightsteelblue          :Color = 0xb0c4de.col
        public val lightyellow             :Color = 0xffffe0.col
        public val lime                    :Color = 0x00ff00.col
        public val limegreen               :Color = 0x32cd32.col
        public val linen                   :Color = 0xfaf0e6.col
        public val magenta                 :Color = 0xff00ff.col
        public val maroon                  :Color = 0x800000.col
        public val mediumaquamarine        :Color = 0x66cdaa.col
        public val mediumblue              :Color = 0x0000cd.col
        public val mediumorchid            :Color = 0xba55d3.col
        public val mediumpurple            :Color = 0x9370db.col
        public val mediumseagreen          :Color = 0x3cb371.col
        public val mediumslateblue         :Color = 0x7b68ee.col
        public val mediumspringgreen       :Color = 0x00fa9a.col
        public val mediumturquoise         :Color = 0x48d1cc.col
        public val mediumvioletred         :Color = 0xc71585.col
        public val midnightblue            :Color = 0x191970.col
        public val mintcream               :Color = 0xf5fffa.col
        public val mistyrose               :Color = 0xffe4e1.col
        public val moccasin                :Color = 0xffe4b5.col
        public val navajowhite             :Color = 0xffdead.col
        public val navy                    :Color = 0x000080.col
        public val oldlace                 :Color = 0xfdf5e6.col
        public val olive                   :Color = 0x808000.col
        public val olivedrab               :Color = 0x6b8e23.col
        public val orange                  :Color = 0xffa500.col
        public val orangered               :Color = 0xff4500.col
        public val orchid                  :Color = 0xda70d6.col
        public val palegoldenrod           :Color = 0xeee8aa.col
        public val palegreen               :Color = 0x98fb98.col
        public val paleturquoise           :Color = 0xafeeee.col
        public val palevioletred           :Color = 0xdb7093.col
        public val papayawhip              :Color = 0xffefd5.col
        public val peachpuff               :Color = 0xffdab9.col
        public val peru                    :Color = 0xcd853f.col
        public val pink                    :Color = 0xffc0cb.col
        public val plum                    :Color = 0xdda0dd.col
        public val powderblue              :Color = 0xb0e0e6.col
        public val purple                  :Color = 0x800080.col
        public val rebeccapurple           :Color = 0x663399.col
        public val red                     :Color = 0xff0000.col
        public val rosybrown               :Color = 0xbc8f8f.col
        public val royalblue               :Color = 0x4169e1.col
        public val saddlebrown             :Color = 0x8b4513.col
        public val salmon                  :Color = 0xfa8072.col
        public val sandybrown              :Color = 0xf4a460.col
        public val seagreen                :Color = 0x2e8b57.col
        public val seashell                :Color = 0xfff5ee.col
        public val sienna                  :Color = 0xa0522d.col
        public val silver                  :Color = 0xc0c0c0.col
        public val skyblue                 :Color = 0x87ceeb.col
        public val slateblue               :Color = 0x6a5acd.col
        public val slategray               :Color = 0x708090.col
        public val slategrey               :Color = 0x708090.col
        public val snow                    :Color = 0xfffafa.col
        public val springgreen             :Color = 0x00ff7f.col
        public val steelblue               :Color = 0x4682b4.col
        public val tan                     :Color = 0xd2b48c.col
        public val teal                    :Color = 0x008080.col
        public val thistle                 :Color = 0xd8bfd8.col
        public val tomato                  :Color = 0xff6347.col
        public val turquoise               :Color = 0x40e0d0.col
        public val violet                  :Color = 0xee82ee.col
        public val wheat                   :Color = 0xf5deb3.col
        public val white                   :Color = 0xffffff.col
        public val whitesmoke              :Color = 0xf5f5f5.col
        public val yellow                  :Color = 0xffff00.col
        public val yellowgreen             :Color = 0x9acd32.col
    }
}

