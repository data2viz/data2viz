package io.data2viz.color

import io.data2viz.math.Angle
import io.data2viz.math.deg
import kotlin.js.Math


val String.color: Color
    get():Color {
        val regex = """^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$""".toRegex()
        if (!this.matches(regex)) error("Conversion of string to io.data2viz.color.getColor works for encoded colors like #12abCD")
        return Color(this.substring(1).toInt(16))
    }

/**
 * Conversion references values
 */
private object ConversionHelper {
    val Kn = 18f
    val Xn = 0.950470f                  // D65 standard referent
    val Yn = 1f
    val Zn = 1.088830f
    val t0 = 4f / 29f
    val t1 = 6f / 29f
    val t2 = 3f * t1 * t1
    val t3 = t1 * t1 * t1

    val darker = 0.7
    val brighter = 1 / darker

    fun xyz2lab(value: Double): Double {
        return if (value > t3) Math.pow(value, 1 / 3.0) else (value / t2 + t0)
    }

    fun rgb2xyz(value: Int): Double {
        val percent = value.toFloat() / 255f
        return if (percent <= 0.04045f) (percent / 12.92) else (Math.pow((percent + 0.055) / 1.055, 2.4))
    }

    fun lab2xyz(value: Float): Float {
        return if (value > t1) (value * value * value) else (t2 * (value - t0))
    }

    fun xyz2rgb(value: Float): Int {
        return if (value <= 0.0031308f) Math.round(12.92f * value * 255)
        else Math.round(255 * (1.055f * Math.pow(value.toDouble(), 1 / 2.4).toFloat() - 0.055f))
    }
}


/**
 * Implementation of Color as an rgb integer and an alpha channel.
 *
 * Provides conversion with hex string notation.
 *
 * See https://developer.mozilla.org/en-US/docs/Web/CSS/color_value and
 * https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Colors/Color_picker_tool
 *
 * TODO must be immutable
 * TODO extract a color interface including alpha (alpha is common to all color spaces)
 */
class Color(var rgb: Int = 0xffffff, var _alpha: Number = 1.0f) {

    // TODO : coerce in place of require check ??
    // TODO store color value in double
    var r: Int
        get() = (rgb shr 16) and 0xff
        set(value) {
            rgb = (rgb and 0x00ffff) + (Math.round(value).coerceIn(0, 255) shl 16)
        }

    var g: Int
        get() = (rgb shr 8) and 0xff
        set(value) {
            rgb = (rgb and 0xff00ff) + (Math.round(value).coerceIn(0, 255) shl 8)
        }

    var b: Int
        get() = rgb and 0xff
        set(value) {
            rgb = (rgb and 0xffff00) + Math.round(value).coerceIn(0, 255)
        }

    var alpha: Number
        get() = _alpha
        set(value) {
            _alpha = value.toFloat().coerceIn(0f, 1f)
        }

    fun rgba(r: Number, g: Number, b: Number, a: Number) {
        this.r = Math.round(r)
        this.g = Math.round(g)
        this.b = Math.round(b)
        alpha = a.toFloat()
    }

    // TODO : always true ?!
    val displayable: Boolean
        get() = (r in 0..255) && (g in 0..255) && (b in 0..255) && (alpha in 0..1)

    fun brighter(strength: Double = 1.0) {
        val str = Math.pow(ConversionHelper.brighter, strength)
        return rgba(r * str, g * str, b * str, alpha)
    }

    fun darker(strength: Double = 1.0) {
        val str = Math.pow(ConversionHelper.darker, strength)
        return rgba(r * str, g * str, b * str, alpha)
    }

    fun toHsla(): HSL {
        val rPercent = r.toFloat() / 255f
        val gPercent = g.toFloat() / 255f
        val bPercent = b.toFloat() / 255f
        val minPercent = Math.min(rPercent, gPercent, bPercent)
        val maxPercent = Math.max(rPercent, gPercent, bPercent)

        var h = 0f
        var s = maxPercent - minPercent
        val l = (maxPercent + minPercent) / 2f

        if (s != 0f) {
            when {
                (rPercent == maxPercent) -> h = if (gPercent < bPercent) ((gPercent - bPercent) / s) + 6f else ((gPercent - bPercent) / s)
                (gPercent == maxPercent) -> h = (bPercent - rPercent) / s + 2f
                else -> h = (rPercent - gPercent) / s + 4f
            }
            s /= if (l < 0.5f) maxPercent + minPercent else 2 - maxPercent - minPercent
            h *= 60f
        } else {
            s = if (l > 0 && l < 1) 0f else h
        }
        return HSL(h.deg, s, l, alpha)
    }

    fun toLab(): LAB {
        val labB = ConversionHelper.rgb2xyz(r)
        val labA = ConversionHelper.rgb2xyz(g)
        val labL = ConversionHelper.rgb2xyz(b)
        val x = ConversionHelper.xyz2lab((0.4124564 * labB + 0.3575761 * labA + 0.1804375 * labL) / ConversionHelper.Xn)
        val y = ConversionHelper.xyz2lab((0.2126729 * labB + 0.7151522 * labA + 0.0721750 * labL) /  ConversionHelper.Yn)
        val z = ConversionHelper.xyz2lab((0.0193339 * labB + 0.1191920 * labA + 0.9503041 * labL) /  ConversionHelper.Zn)
        return LAB(116 * y - 16, 500 * (x - y), 200 * (y - z), alpha)
    }

    val rgbHex: String
        get() = "#" +
                ((rgb shr 20) and 0xf).toString(16) +
                ((rgb shr 16) and 0xf).toString(16) +
                ((rgb shr 12) and 0xf).toString(16) +
                ((rgb shr 8) and 0xf).toString(16) +
                ((rgb shr 4) and 0xf).toString(16) +
                (rgb and 0xf).toString(16)

    @Suppress("UnsafeCastFromDynamic")
    fun Int.toString(radix: Int): String = asDynamic().toString(radix)

    override operator fun equals(other: Any?): Boolean = (other != null && other is Color && r == other.r && g == other.g && b == other.b && alpha == other.alpha)

    override fun toString() = if (alpha.toFloat() < 1.0) "rgba($r, $g, $b, $alpha)" else rgbHex
}

/********************************************************/
/****************** HSL SPECIFIC ************************/
/********************************************************/

/**
 * Create a color in the HSL color space
 *
 * @param _h hue:Angle in degree
 * @param _s saturation:Float between 0 and 1
 * @param _l lightness:Float between 0 and 1
 * @param _alpha:Float between 0 and 1
 */
class HSL(val h: Angle = Angle(0.0), s: Number = 1, l: Number = 1, alpha: Number = 1) {

    val s = s.toDouble().coerceIn(0.0, 1.0)
    val l = l.toDouble().coerceIn(0.0, 1.0)
    val alpha = alpha.toFloat().coerceIn(0f, 1f)

    val displayable: Boolean
        get() = (s in 0..1) && (l in 0..1) && (alpha in 0..1)

    fun brighter(strength: Double = 1.0) = HSL(h, s, (l * Math.pow(ConversionHelper.brighter, strength)), alpha)
    fun darker(strength: Double = 1.0) = HSL(h, s, (l * Math.pow(ConversionHelper.brighter, strength)), alpha)

    fun toRgba(): Color =
            if (s == 0.0)     // achromatic
                colors.rgba(
                        r = Math.round(l * 255),
                        g = Math.round(l * 255),
                        b = Math.round(l * 255),
                        a = alpha)
            else {
                val q = if (l < 0.5f) l * (1 + s) else l + s - l * s
                val p = 2 * l - q
                colors.rgba(
                        r = Math.round(hue2rgb(p, q, h + 120.deg) * 255),
                        g = Math.round(hue2rgb(p, q, h) * 255),
                        b = Math.round(hue2rgb(p, q, h - 120.deg) * 255),
                        a = alpha)
            }

    private fun hue2rgb(p: Double, q: Double, hue: Angle): Double {
        val hd = hue.normalize()
        return when {
            hd.deg < 60 -> (p + (q - p) * (hd.deg / 60.0))
            hd.deg < 180 -> q
            hd.deg < 240 -> (p + (q - p) * ((240 - hd.deg) / 60.0))
            else -> p
        }
    }

    override operator fun equals(other: Any?):Boolean = (other != null && other is HSL && h == other.h && s == other.s && l == other.l && alpha == other.alpha)

    override fun toString() = "hsla(${h.deg}°, ${s*100}%, ${l*100}%, $alpha)"
}

// TODO in a java implementation of LAB they used Double
/**
 * Create a color in the LAB color space (CIE L*a*b* D65 whitepoint)
 *
 * @param l lightness:Float between 0 and 100
 * @param a "a"-component:Float for green-red between -128 and +128
 * @param b "b"-component:Float for blue-yellow between -128 and +128
 * @param alpha:Opacity between 0 and 1
 */
class LAB(l: Number = 100, a: Number = 0, b: Number = 0, alpha: Number = 1) {

    // TODO check for coerce values (coerce needed ?)
    // TODO check for type
    val l: Float = l.toFloat().coerceIn(0f, 100f)
    val a: Float = a.toFloat().coerceIn(-128f, 128f)
    val b: Float = b.toFloat().coerceIn(-128f, 128f)
    val alpha: Float = alpha.toFloat().coerceIn(0f, 1f)

    fun brighter(strength: Double = 1.0) = LAB((l + (ConversionHelper.Kn * strength)).toFloat(), a, b, alpha)
    fun darker(strength: Double = 1.0) = LAB((l - (ConversionHelper.Kn * strength)).toFloat(), a, b, alpha)

    fun toRgba(): Color {
        // map CIE LAB to CIE XYZ
        var y = (l + 16) / 116f
        var x = y + (a / 500f)
        var z = y - (b / 200f)
        y = ConversionHelper.Yn * ConversionHelper.lab2xyz(y)
        x = ConversionHelper.Xn * ConversionHelper.lab2xyz(x)
        z = ConversionHelper.Zn * ConversionHelper.lab2xyz(z)

        // map CIE XYZ to RGB
        return colors.rgba(
                r = ConversionHelper.xyz2rgb(3.2404542f * x - 1.5371385f * y - 0.4985314f * z),
                g = ConversionHelper.xyz2rgb(-0.9692660f * x + 1.8760108f * y + 0.0415560f * z),
                b = ConversionHelper.xyz2rgb(0.0556434f * x - 0.2040259f * y + 1.0572252f * z),
                a = alpha)
    }

    // TODO use RAD2DEG from angle ?
    fun toHcla(): HCL {
        val h = Math.atan2(b.toDouble(), a.toDouble()) * (180 / Math.PI)
        val hue = (h.deg).normalize()
        return HCL(hue, Math.sqrt(a.toDouble() * a.toDouble() + b.toDouble() * b.toDouble()), l, alpha)
    }

    override fun toString() = "laba($l, $a, $b, $alpha)"
}

/********************************************************/
/****************** HCL SPECIFIC ************************/
/********************************************************/

/**
 * Create a color in the HCL color space (CIELCH)
 *
 * @param h hue:Angle in degree
 * @param c chroma:Double, the upper bound for chroma depends on hue and luminance
 * @param m luminance:Float a value in the range [0,100] giving the luminance of the colour (in percent)
 * @param alpha:Float between 0 and 1
 */
class HCL(val h: Angle = Angle(0.0), c: Number = 1, l: Number = 100, alpha: Number = 1) {

    val c = c.toDouble()
    val l = l.toFloat().coerceIn(0f, 100f)
    val alpha = alpha.toFloat().coerceIn(0f, 1f)

    /*val displayable: Boolean
        get() = (s in 0..1) && (l in 0..1) && (alpha in 0..1)*/

    fun brighter(strength: Double = 1.0) = HCL(h, c, (l + (ConversionHelper.Kn * strength)).toFloat(), alpha)
    fun darker(strength: Double = 1.0) = HCL(h, c, (l - (ConversionHelper.Kn * strength)).toFloat(), alpha)

    override operator fun equals(other: Any?):Boolean = (other != null && other is HCL && h == other.h && c == other.c && l == other.l && alpha == other.alpha)

    override fun toString() = "hcla(${h.deg}°, $c, ${l*100}%, $alpha)"
}

object colors {


    fun rgba(r: Number, g: Number, b: Number, a: Number = 1f) = Color().apply { rgba(r, g, b, a) }
    fun hsla(h: Angle, s: Number, l: Number, a: Number = 1f) = HSL(h, s, l, a)
    fun lab(l: Number = 100, a: Number = 0, b: Number = 0, alpha: Number = 1f) = LAB(l, a, b, alpha)

    val Int.col: Color
        get() = Color(this)

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
