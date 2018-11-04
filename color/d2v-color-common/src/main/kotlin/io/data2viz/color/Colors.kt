@file:Suppress("unused", "DEPRECATION")

package io.data2viz.color

import io.data2viz.geom.Point
import io.data2viz.math.Angle



object Colors {

    /***************************** COLORSPACE CONSTRUCTORS *******************************************/

    fun rgb(red: Int, green: Int, blue: Int, alpha: Double = 1.0): RgbColor {
        val rgb = (red.coerceIn(0, 255) shl 16) + (green.coerceIn(0, 255) shl 8) + blue.coerceIn(0, 255)
        return RgbColor(rgb, alpha)
    }

    fun lab(lightness: Double, aComponent: Double, bComponent: Double, alpha: Double = 1.0) =
            LabColor(lightness, aComponent, bComponent, alpha)

    fun hsl(hue: Angle, saturation: Double, lightness: Double, alpha: Double = 1.0) =
            HslColor(hue, saturation, lightness, alpha)

    fun hcl(hue: Angle, chroma: Double, luminance: Double, alpha: Double = 1.0) =
            HclColor(hue, chroma, luminance, alpha)

    fun lch(luminance: Double, chroma: Double, hue: Angle, alpha: Double = 1.0) = hcl(hue, chroma, luminance, alpha)


    /***************************** GRADIENTS *********************************************************/

    object Gradient {
        fun linear(from:Point, to:Point) = LinearGradientFirstColorBuilder(from, to)
        fun radial(center:Point, radius:Double) = RadialGradientFirstColorBuilder(center, radius)
    }


    /***************************** NAMED WEB COLORS ******************************************************/

    object Web {
        val aliceblue               = 0xf0f8ff.color
        val antiquewhite            = 0xfaebd7.color
        val aqua                    = 0x00ffff.color
        val aquamarine              = 0x7fffd4.color
        val azure                   = 0xf0ffff.color
        val beige                   = 0xf5f5dc.color
        val bisque                  = 0xffe4c4.color
        val black                   = 0x000000.color
        val blanchedalmond          = 0xffebcd.color
        val blue                    = 0x0000ff.color
        val blueviolet              = 0x8a2be2.color
        val brown                   = 0xa52a2a.color
        val burlywood               = 0xdeb887.color
        val cadetblue               = 0x5f9ea0.color
        val chartreuse              = 0x7fff00.color
        val chocolate               = 0xd2691e.color
        val coral                   = 0xff7f50.color
        val cornflowerblue          = 0x6495ed.color
        val cornsilk                = 0xfff8dc.color
        val crimson                 = 0xdc143c.color
        val cyan                    = 0x00ffff.color
        val darkblue                = 0x00008b.color
        val darkcyan                = 0x008b8b.color
        val darkgoldenrod           = 0xb8860b.color
        val darkgray                = 0xa9a9a9.color
        val darkgreen               = 0x006400.color
        val darkgrey                = 0xa9a9a9.color
        val darkkhaki               = 0xbdb76b.color
        val darkmagenta             = 0x8b008b.color
        val darkolivegreen          = 0x556b2f.color
        val darkorange              = 0xff8c00.color
        val darkorchid              = 0x9932cc.color
        val darkred                 = 0x8b0000.color
        val darksalmon              = 0xe9967a.color
        val darkseagreen            = 0x8fbc8f.color
        val darkslateblue           = 0x483d8b.color
        val darkslategray           = 0x2f4f4f.color
        val darkslategrey           = 0x2f4f4f.color
        val darkturquoise           = 0x00ced1.color
        val darkviolet              = 0x9400d3.color
        val deeppink                = 0xff1493.color
        val deepskyblue             = 0x00bfff.color
        val dimgray                 = 0x696969.color
        val dimgrey                 = 0x696969.color
        val dodgerblue              = 0x1e90ff.color
        val firebrick               = 0xb22222.color
        val floralwhite             = 0xfffaf0.color
        val forestgreen             = 0x228b22.color
        val fuchsia                 = 0xff00ff.color
        val gainsboro               = 0xdcdcdc.color
        val ghostwhite              = 0xf8f8ff.color
        val gold                    = 0xffd700.color
        val goldenrod               = 0xdaa520.color
        val gray                    = 0x808080.color
        val green                   = 0x008000.color
        val greenyellow             = 0xadff2f.color
        val grey                    = 0x808080.color
        val honeydew                = 0xf0fff0.color
        val hotpink                 = 0xff69b4.color
        val indianred               = 0xcd5c5c.color
        val indigo                  = 0x4b0082.color
        val ivory                   = 0xfffff0.color
        val khaki                   = 0xf0e68c.color
        val lavender                = 0xe6e6fa.color
        val lavenderblush           = 0xfff0f5.color
        val lawngreen               = 0x7cfc00.color
        val lemonchiffon            = 0xfffacd.color
        val lightblue               = 0xadd8e6.color
        val lightcoral              = 0xf08080.color
        val lightcyan               = 0xe0ffff.color
        val lightgoldenrodyellow    = 0xfafad2.color
        val lightgray               = 0xd3d3d3.color
        val lightgreen              = 0x90ee90.color
        val lightgrey               = 0xd3d3d3.color
        val lightpink               = 0xffb6c1.color
        val lightsalmon             = 0xffa07a.color
        val lightseagreen           = 0x20b2aa.color
        val lightskyblue            = 0x87cefa.color
        val lightslategray          = 0x778899.color
        val lightslategrey          = 0x778899.color
        val lightsteelblue          = 0xb0c4de.color
        val lightyellow             = 0xffffe0.color
        val lime                    = 0x00ff00.color
        val limegreen               = 0x32cd32.color
        val linen                   = 0xfaf0e6.color
        val magenta                 = 0xff00ff.color
        val maroon                  = 0x800000.color
        val mediumaquamarine        = 0x66cdaa.color
        val mediumblue              = 0x0000cd.color
        val mediumorchid            = 0xba55d3.color
        val mediumpurple            = 0x9370db.color
        val mediumseagreen          = 0x3cb371.color
        val mediumslateblue         = 0x7b68ee.color
        val mediumspringgreen       = 0x00fa9a.color
        val mediumturquoise         = 0x48d1cc.color
        val mediumvioletred         = 0xc71585.color
        val midnightblue            = 0x191970.color
        val mintcream               = 0xf5fffa.color
        val mistyrose               = 0xffe4e1.color
        val moccasin                = 0xffe4b5.color
        val navajowhite             = 0xffdead.color
        val navy                    = 0x000080.color
        val oldlace                 = 0xfdf5e6.color
        val olive                   = 0x808000.color
        val olivedrab               = 0x6b8e23.color
        val orange                  = 0xffa500.color
        val orangered               = 0xff4500.color
        val orchid                  = 0xda70d6.color
        val palegoldenrod           = 0xeee8aa.color
        val palegreen               = 0x98fb98.color
        val paleturquoise           = 0xafeeee.color
        val palevioletred           = 0xdb7093.color
        val papayawhip              = 0xffefd5.color
        val peachpuff               = 0xffdab9.color
        val peru                    = 0xcd853f.color
        val pink                    = 0xffc0cb.color
        val plum                    = 0xdda0dd.color
        val powderblue              = 0xb0e0e6.color
        val purple                  = 0x800080.color
        val rebeccapurple           = 0x663399.color
        val red                     = 0xff0000.color
        val rosybrown               = 0xbc8f8f.color
        val royalblue               = 0x4169e1.color
        val saddlebrown             = 0x8b4513.color
        val salmon                  = 0xfa8072.color
        val sandybrown              = 0xf4a460.color
        val seagreen                = 0x2e8b57.color
        val seashell                = 0xfff5ee.color
        val sienna                  = 0xa0522d.color
        val silver                  = 0xc0c0c0.color
        val skyblue                 = 0x87ceeb.color
        val slateblue               = 0x6a5acd.color
        val slategray               = 0x708090.color
        val slategrey               = 0x708090.color
        val snow                    = 0xfffafa.color
        val springgreen             = 0x00ff7f.color
        val steelblue               = 0x4682b4.color
        val tan                     = 0xd2b48c.color
        val teal                    = 0x008080.color
        val thistle                 = 0xd8bfd8.color
        val tomato                  = 0xff6347.color
        val turquoise               = 0x40e0d0.color
        val violet                  = 0xee82ee.color
        val wheat                   = 0xf5deb3.color
        val white                   = 0xffffff.color
        val whitesmoke              = 0xf5f5f5.color
        val yellow                  = 0xffff00.color
        val yellowgreen             = 0x9acd32.color
    }
}

