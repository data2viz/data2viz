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


object Colors {

    /***************************** COLORSPACE CONSTRUCTORS *******************************************/

    /**
     * Instantiate a color using its rgb Int value and alpha. Should be used with hexadecimal literal.
     * @sample: `Colors.rgb(0x0b0b0b, .5)`
     */
    fun rgb(rgb:Int, alpha: Percent = 100.pct): RgbColor = rgb(rgb, alpha)

    /**
     * Instantiate a color using its r,g,b Int values and alpha
     * @sample: `Colors.rgb(128, 128, 128, .5)`
     */
    fun rgb(red: Int, green: Int, blue: Int, alpha: Percent = 100.pct): RgbColor {
        val rgb = (red.coerceIn(0, 255) shl 16) + (green.coerceIn(0, 255) shl 8) + blue.coerceIn(0, 255)
        return rgb(rgb, alpha)
    }

    fun lab(lightness: Percent, aComponent: Double, bComponent: Double, alpha: Percent = 100.pct) =
            LabColor(lightness, aComponent, bComponent, alpha)

    fun lab(lightness: Percent, aComponent: Int, bComponent: Int, alpha: Percent = 100.pct) =
            LabColor(lightness, aComponent.toDouble(), bComponent.toDouble(), alpha)

    fun hsl(hue: Angle, saturation: Percent, lightness: Percent, alpha: Percent = 100.pct) =
            HslColor(hue, saturation, lightness, alpha)

    fun hcl(hue: Angle, chroma: Double, luminance: Percent, alpha: Percent = 100.pct) =
            HclColor(hue, chroma, luminance, alpha)

    fun hcl(hue: Angle, chroma: Int, luminance: Percent, alpha: Percent = 100.pct) =
            HclColor(hue, chroma.toDouble(), luminance, alpha)

    fun lch(luminance: Percent, chroma: Double, hue: Angle, alpha: Percent = 100.pct) =
            hcl(hue, chroma, luminance, alpha)

    fun lch(luminance: Percent, chroma: Int, hue: Angle, alpha: Percent = 100.pct) = hcl(hue, chroma, luminance, alpha)


    /***************************** GRADIENTS *********************************************************/

    object Gradient {
        fun linear(from:Point, to:Point) = LinearGradientFirstColorBuilder(from, to)
        fun radial(center:Point, radius:Double) = RadialGradientFirstColorBuilder(center, radius)
    }


    /***************************** NAMED WEB COLORS ******************************************************/

    object Web {
        val aliceblue               = 0xf0f8ff.col
        val antiquewhite            = 0xfaebd7.col
        val aqua                    = 0x00ffff.col
        val aquamarine              = 0x7fffd4.col
        val azure                   = 0xf0ffff.col
        val beige                   = 0xf5f5dc.col
        val bisque                  = 0xffe4c4.col
        val black                   = 0x000000.col
        val blanchedalmond          = 0xffebcd.col
        val blue                    = 0x0000ff.col
        val blueviolet              = 0x8a2be2.col
        val brown                   = 0xa52a2a.col
        val burlywood               = 0xdeb887.col
        val cadetblue               = 0x5f9ea0.col
        val chartreuse              = 0x7fff00.col
        val chocolate               = 0xd2691e.col
        val coral                   = 0xff7f50.col
        val cornflowerblue          = 0x6495ed.col
        val cornsilk                = 0xfff8dc.col
        val crimson                 = 0xdc143c.col
        val cyan                    = 0x00ffff.col
        val darkblue                = 0x00008b.col
        val darkcyan                = 0x008b8b.col
        val darkgoldenrod           = 0xb8860b.col
        val darkgray                = 0xa9a9a9.col
        val darkgreen               = 0x006400.col
        val darkgrey                = 0xa9a9a9.col
        val darkkhaki               = 0xbdb76b.col
        val darkmagenta             = 0x8b008b.col
        val darkolivegreen          = 0x556b2f.col
        val darkorange              = 0xff8c00.col
        val darkorchid              = 0x9932cc.col
        val darkred                 = 0x8b0000.col
        val darksalmon              = 0xe9967a.col
        val darkseagreen            = 0x8fbc8f.col
        val darkslateblue           = 0x483d8b.col
        val darkslategray           = 0x2f4f4f.col
        val darkslategrey           = 0x2f4f4f.col
        val darkturquoise           = 0x00ced1.col
        val darkviolet              = 0x9400d3.col
        val deeppink                = 0xff1493.col
        val deepskyblue             = 0x00bfff.col
        val dimgray                 = 0x696969.col
        val dimgrey                 = 0x696969.col
        val dodgerblue              = 0x1e90ff.col
        val firebrick               = 0xb22222.col
        val floralwhite             = 0xfffaf0.col
        val forestgreen             = 0x228b22.col
        val fuchsia                 = 0xff00ff.col
        val gainsboro               = 0xdcdcdc.col
        val ghostwhite              = 0xf8f8ff.col
        val gold                    = 0xffd700.col
        val goldenrod               = 0xdaa520.col
        val gray                    = 0x808080.col
        val green                   = 0x008000.col
        val greenyellow             = 0xadff2f.col
        val grey                    = 0x808080.col
        val honeydew                = 0xf0fff0.col
        val hotpink                 = 0xff69b4.col
        val indianred               = 0xcd5c5c.col
        val indigo                  = 0x4b0082.col
        val ivory                   = 0xfffff0.col
        val khaki                   = 0xf0e68c.col
        val lavender                = 0xe6e6fa.col
        val lavenderblush           = 0xfff0f5.col
        val lawngreen               = 0x7cfc00.col
        val lemonchiffon            = 0xfffacd.col
        val lightblue               = 0xadd8e6.col
        val lightcoral              = 0xf08080.col
        val lightcyan               = 0xe0ffff.col
        val lightgoldenrodyellow    = 0xfafad2.col
        val lightgray               = 0xd3d3d3.col
        val lightgreen              = 0x90ee90.col
        val lightgrey               = 0xd3d3d3.col
        val lightpink               = 0xffb6c1.col
        val lightsalmon             = 0xffa07a.col
        val lightseagreen           = 0x20b2aa.col
        val lightskyblue            = 0x87cefa.col
        val lightslategray          = 0x778899.col
        val lightslategrey          = 0x778899.col
        val lightsteelblue          = 0xb0c4de.col
        val lightyellow             = 0xffffe0.col
        val lime                    = 0x00ff00.col
        val limegreen               = 0x32cd32.col
        val linen                   = 0xfaf0e6.col
        val magenta                 = 0xff00ff.col
        val maroon                  = 0x800000.col
        val mediumaquamarine        = 0x66cdaa.col
        val mediumblue              = 0x0000cd.col
        val mediumorchid            = 0xba55d3.col
        val mediumpurple            = 0x9370db.col
        val mediumseagreen          = 0x3cb371.col
        val mediumslateblue         = 0x7b68ee.col
        val mediumspringgreen       = 0x00fa9a.col
        val mediumturquoise         = 0x48d1cc.col
        val mediumvioletred         = 0xc71585.col
        val midnightblue            = 0x191970.col
        val mintcream               = 0xf5fffa.col
        val mistyrose               = 0xffe4e1.col
        val moccasin                = 0xffe4b5.col
        val navajowhite             = 0xffdead.col
        val navy                    = 0x000080.col
        val oldlace                 = 0xfdf5e6.col
        val olive                   = 0x808000.col
        val olivedrab               = 0x6b8e23.col
        val orange                  = 0xffa500.col
        val orangered               = 0xff4500.col
        val orchid                  = 0xda70d6.col
        val palegoldenrod           = 0xeee8aa.col
        val palegreen               = 0x98fb98.col
        val paleturquoise           = 0xafeeee.col
        val palevioletred           = 0xdb7093.col
        val papayawhip              = 0xffefd5.col
        val peachpuff               = 0xffdab9.col
        val peru                    = 0xcd853f.col
        val pink                    = 0xffc0cb.col
        val plum                    = 0xdda0dd.col
        val powderblue              = 0xb0e0e6.col
        val purple                  = 0x800080.col
        val rebeccapurple           = 0x663399.col
        val red                     = 0xff0000.col
        val rosybrown               = 0xbc8f8f.col
        val royalblue               = 0x4169e1.col
        val saddlebrown             = 0x8b4513.col
        val salmon                  = 0xfa8072.col
        val sandybrown              = 0xf4a460.col
        val seagreen                = 0x2e8b57.col
        val seashell                = 0xfff5ee.col
        val sienna                  = 0xa0522d.col
        val silver                  = 0xc0c0c0.col
        val skyblue                 = 0x87ceeb.col
        val slateblue               = 0x6a5acd.col
        val slategray               = 0x708090.col
        val slategrey               = 0x708090.col
        val snow                    = 0xfffafa.col
        val springgreen             = 0x00ff7f.col
        val steelblue               = 0x4682b4.col
        val tan                     = 0xd2b48c.col
        val teal                    = 0x008080.col
        val thistle                 = 0xd8bfd8.col
        val tomato                  = 0xff6347.col
        val turquoise               = 0x40e0d0.col
        val violet                  = 0xee82ee.col
        val wheat                   = 0xf5deb3.col
        val white                   = 0xffffff.col
        val whitesmoke              = 0xf5f5f5.col
        val yellow                  = 0xffff00.col
        val yellowgreen             = 0x9acd32.col
    }
}

