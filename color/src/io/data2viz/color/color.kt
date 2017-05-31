package io.data2viz.color

import io.data2viz.math.Angle
import kotlin.js.Math


val String.color: Color
    get():Color {
        val regex = """^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$""".toRegex()
        if (!this.matches(regex)) error("Conversion of string to io.data2viz.color.getColor works for encoded colors like #12abCD")
        return Color(this.substring(1).toInt(16))
    }

/**
 * Implementation of Color as an rgb integer and an alpha channel.
 *
 * Provides conversion with hex string notation.
 *
 * See https://developer.mozilla.org/en-US/docs/Web/CSS/color_value and
 * https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Colors/Color_picker_tool
 */
class Color(var rgb: Int = 0xffffff, var _alpha: Float = 1.0f) {

    private val darker = 0.7
    private val brighter = 1 / darker

    // TODO : coerce in place of require check ??
    var r: Int
        get() = (rgb shr 16) and 0xff
        set(value) {
            require(value < 256) { "r should be less or equal to 255" }
            require(value > -1) { "r should be greater or equal to 0" }
            rgb = (rgb and 0x00ffff) + (value shl 16)
        }

    var g: Int
        get() = (rgb shr 8) and 0xff
        set(value) {
            require(value < 256) { "g should be less or equal to 255" }
            require(value > -1) { "g should be greater or equal to 0" }
            rgb = (rgb and 0xff00ff) + (value shl 8)
        }

    var b: Int
        get() = rgb and 0xff
        set(value) {
            require(value < 256) { "b should be less or equal to 255" }
            require(value > -1) { "b should be greater or equal to 0" }
            rgb = (rgb and 0xffff00) + value
        }

    var alpha: Number
        get() = _alpha
        set(value) {
            require(value.toFloat() <= 1f) { "alpha should be less or equal to 1" }
            require(value.toFloat() >= 0f) { "alpha should be greater or equal to 0" }
            _alpha = value.toFloat()
        }

    fun rgba(r: Number, g: Number, b: Number, a: Number) {
        this.r = r.toInt()
        this.g = g.toInt()
        this.b = b.toInt()
        alpha = a.toFloat()
    }

    val displayable: Boolean
        get() = (r in 0..255) && (g in 0..255) && (b in 0..255) && (alpha in 0..1)

    fun brighter(strength: Double = 1.0) {
        val str = Math.pow(brighter, strength)
        return rgba(r * str, g * str, b * str, alpha)
    }

    fun darker(strength: Double = 1.0) {
        val str = Math.pow(darker, strength)
        return rgba(r * str, g * str, b * str, alpha)
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

    override fun toString() = if (alpha.toFloat() < 1.0) "rgba($r,$g,$b,$alpha)" else rgbHex
}

/********************************************************/
/****************** HSL SPECIFIC ************************/
/********************************************************/

// TODO "lazify" this ?

/**
 * Create a color in the HSL color space
 *
 * @param _h hue:Angle in degree
 * @param _s saturation:Float between 0 and 1
 * @param _l lightness:Float between 0 and 1
 * @param _alpha:Float between 0 and 1
 */
class HSL(var _h: Angle = Angle(0.0), var _s: Float = 1f, var _l: Float = 1f, var _alpha: Float = 1.0f) {

    private val darker = 0.7
    private val brighter = 1 / darker

    var h: Angle
        get() = _h
        set(value) {
            _h = value
        }

    // TODO : require checks in place of coerce ??
    var s: Float
        get() = _s
        set(value) {
            _s = value.coerceIn(0f, 1f)
        }

    var l: Float
        get() = _l
        set(value) {
            _l = value.coerceIn(0f, 1f)
        }

    // TODO : place in interface alpha (=opacity in D3) is the same for all colorspaces
    var alpha: Number
        get() = _alpha
        set(value) {
            require(value.toFloat() <= 1f) { "alpha should be less or equal to 1" }
            require(value.toFloat() >= 0f) { "alpha should be greater or equal to 0" }
            _alpha = value.toFloat()
        }

    fun hsla(h: Angle, s: Number, l: Number, a: Number = 1) {
        this.h = h
        this.s = s.toFloat()
        this.l = l.toFloat()
        alpha = a.toFloat()
    }

    val displayable: Boolean
        get() = (s in 0..1) && (l in 0..1) && (alpha in 0..1)

    fun brighter(strength: Double = 1.0) {
        val str = Math.pow(brighter, strength)
        return hsla(h, s, (l * str).toFloat(), alpha)
    }

    fun darker(strength: Double = 1.0) {
        val str = Math.pow(darker, strength)
        return hsla(h, s, (l * str).toFloat(), alpha)
    }

    fun toRgba(): Color =
            if (s == 0f)     // achromatic
                colors.rgba(
                        r = (l * 255).toInt(),
                        g = (l * 255).toInt(),
                        b = (l * 255).toInt(),
                        a = _alpha)
            else {
                val q = if (l < 0.5f) l * (1 + s) else l + s - l * s
                val p = 2 * l - q;
                colors.rgba(
                        r = Math.round(hue2rgb(p, q, h.deg + 120.0) * 255),
                        g = Math.round(hue2rgb(p, q, h.deg) * 255),
                        b = Math.round(hue2rgb(p, q, h.deg - 120.0) * 255),
                        a = _alpha)
            }

    private fun hue2rgb(p: Float, q: Float, hueDeg: Double): Float {
        val hd = normalizeHueAngle(hueDeg)
        return  when {
            hd < 60 -> (p + (q - p) * (hd / 60.0)).toFloat()
            hd < 180 -> q
            hd < 240 -> (p + (q - p) * ((240 - hd) / 60.0)).toFloat()
            else -> p
        }
    }

    private fun normalizeHueAngle(hueDeg: Double) = if (hueDeg >= 0) hueDeg % 360 else hueDeg % 360 + 360

}

/********************************************************/
/****************** LAB SPECIFIC ************************/
/********************************************************/

// TODO in a java implementation of LAB they used Double
// TODO "lazify" this as LAB is rarely used ?

/**
 * Create a color in the LAB color space (CIE L*a*b* D65 whitepoint)
 *
 * @param _l lightness:Float between 0 and 100
 * @param _a "a"-component:Float for green-red between -128 and +128
 * @param _b "b"-component:Float for blue-yellow between -128 and +128
 * @param _alpha:Opacity between 0 and 1
 */
class LAB(var _l: Float = 100f, var _a: Float = 0f, var _b: Float = 0f, var _alpha: Float = 1.0f) {

    private val Kn = 18f
    private val Xn = 0.950470f                  // D65 standard referent
    private val Yn = 1f
    private val Zn = 1.088830f
    private val t0 = 4f / 29f
    private val t1 = 6f / 29f
    private val t2 = 3f * t1 * t1
    private val t3 = t1 * t1 * t1

    private val darker = 0.7
    private val brighter = 1 / darker

    // TODO check for coerce values (coerce needed ?)
    // TODO check for type
    var l: Float
        get() = _l
        set(value) {
            _l = value.coerceIn(0f, 100f)
        }

    // TODO : require checks in place of coerce ??
    var a: Float
        get() = _a
        set(value) {
            _a = value.coerceIn(-128f, 128f)
        }

    var b: Float
        get() = _b
        set(value) {
            _b = value.coerceIn(-128f, 128f)
        }

    // TODO : place in interface. alpha (=opacity in D3) is the same for all colorspaces
    var alpha: Number
        get() = _alpha
        set(value) {
            require(value.toFloat() <= 1f) { "alpha should be less or equal to 1" }
            require(value.toFloat() >= 0f) { "alpha should be greater or equal to 0" }
            _alpha = value.toFloat()
        }

    fun laba(l: Number, a: Number, b: Number, alpha: Number) {
        this.l = l.toFloat()
        this.a = a.toFloat()
        this.b = b.toFloat()
        this.alpha = alpha.toFloat()
    }

    fun brighter(strength: Double = 1.0) {
        return laba(l + (Kn * strength), a, b, alpha)
    }

    fun darker(strength: Double = 1.0) {
        return laba(l - (Kn * strength), a, b, alpha)
    }

    fun rgba(): Color {
        // map CIE LAB to CIE XYZ
        var y = (l + 16) / 116f
        var x = y + (a / 500f)
        var z = y - (b / 200f)
        y = Yn * lab2xyz(y)
        x = Xn * lab2xyz(x)
        z = Zn * lab2xyz(z)

        // map CIE XYZ to RGB
        return colors.rgba(
                r = xyz2rgb(3.2404542f * x - 1.5371385f * y - 0.4985314f * z),
                g = xyz2rgb(-0.9692660f * x + 1.8760108f * y + 0.0415560f * z),
                b = xyz2rgb(0.0556434f * x - 0.2040259f * y + 1.0572252f * z),
                a = _alpha)
    }

    private fun lab2xyz(t: Float): Float {
        return if (t > t1) (t * t * t) else (t2 * (t - t0))
    }

    private fun xyz2rgb(x: Float): Int {
        return if (x <= 0.0031308f) Math.round(12.92f * x * 255)
        else Math.round(255 * (1.055f * Math.pow(x.toDouble(), 1 / 2.4).toFloat() - 0.055f))
    }

}

object colors {


    fun rgba(r: Number, g: Number, b: Number, a: Number = 1f) = Color().apply { rgba(r, g, b, a) }

    val Int.col: Color
        get() = Color(this)

    val aliceblue               by lazy { 0xf0f8ff.col }
    val antiquewhite            by lazy { 0xfaebd7.col }
    val aqua                    by lazy { 0x00ffff.col }
    val aquamarine              by lazy { 0x7fffd4.col }
    val azure                   by lazy { 0xf0ffff.col }
    val beige                   by lazy { 0xf5f5dc.col }
    val bisque                  by lazy { 0xffe4c4.col }
    val black                   by lazy { 0x000000.col }
    val blanchedalmond          by lazy { 0xffebcd.col }
    val blue                    by lazy { 0x0000ff.col }
    val blueviolet              by lazy { 0x8a2be2.col }
    val brown                   by lazy { 0xa52a2a.col }
    val burlywood               by lazy { 0xdeb887.col }
    val cadetblue               by lazy { 0x5f9ea0.col }
    val chartreuse              by lazy { 0x7fff00.col }
    val chocolate               by lazy { 0xd2691e.col }
    val coral                   by lazy { 0xff7f50.col }
    val cornflowerblue          by lazy { 0x6495ed.col }
    val cornsilk                by lazy { 0xfff8dc.col }
    val crimson                 by lazy { 0xdc143c.col }
    val cyan                    by lazy { 0x00ffff.col }
    val darkblue                by lazy { 0x00008b.col }
    val darkcyan                by lazy { 0x008b8b.col }
    val darkgoldenrod           by lazy { 0xb8860b.col }
    val darkgray                by lazy { 0xa9a9a9.col }
    val darkgreen               by lazy { 0x006400.col }
    val darkgrey                by lazy { 0xa9a9a9.col }
    val darkkhaki               by lazy { 0xbdb76b.col }
    val darkmagenta             by lazy { 0x8b008b.col }
    val darkolivegreen          by lazy { 0x556b2f.col }
    val darkorange              by lazy { 0xff8c00.col }
    val darkorchid              by lazy { 0x9932cc.col }
    val darkred                 by lazy { 0x8b0000.col }
    val darksalmon              by lazy { 0xe9967a.col }
    val darkseagreen            by lazy { 0x8fbc8f.col }
    val darkslateblue           by lazy { 0x483d8b.col }
    val darkslategray           by lazy { 0x2f4f4f.col }
    val darkslategrey           by lazy { 0x2f4f4f.col }
    val darkturquoise           by lazy { 0x00ced1.col }
    val darkviolet              by lazy { 0x9400d3.col }
    val deeppink                by lazy { 0xff1493.col }
    val deepskyblue             by lazy { 0x00bfff.col }
    val dimgray                 by lazy { 0x696969.col }
    val dimgrey                 by lazy { 0x696969.col }
    val dodgerblue              by lazy { 0x1e90ff.col }
    val firebrick               by lazy { 0xb22222.col }
    val floralwhite             by lazy { 0xfffaf0.col }
    val forestgreen             by lazy { 0x228b22.col }
    val fuchsia                 by lazy { 0xff00ff.col }
    val gainsboro               by lazy { 0xdcdcdc.col }
    val ghostwhite              by lazy { 0xf8f8ff.col }
    val gold                    by lazy { 0xffd700.col }
    val goldenrod               by lazy { 0xdaa520.col }
    val gray                    by lazy { 0x808080.col }
    val green                   by lazy { 0x008000.col }
    val greenyellow             by lazy { 0xadff2f.col }
    val grey                    by lazy { 0x808080.col }
    val honeydew                by lazy { 0xf0fff0.col }
    val hotpink                 by lazy { 0xff69b4.col }
    val indianred               by lazy { 0xcd5c5c.col }
    val indigo                  by lazy { 0x4b0082.col }
    val ivory                   by lazy { 0xfffff0.col }
    val khaki                   by lazy { 0xf0e68c.col }
    val lavender                by lazy { 0xe6e6fa.col }
    val lavenderblush           by lazy { 0xfff0f5.col }
    val lawngreen               by lazy { 0x7cfc00.col }
    val lemonchiffon            by lazy { 0xfffacd.col }
    val lightblue               by lazy { 0xadd8e6.col }
    val lightcoral              by lazy { 0xf08080.col }
    val lightcyan               by lazy { 0xe0ffff.col }
    val lightgoldenrodyellow    by lazy { 0xfafad2.col }
    val lightgray               by lazy { 0xd3d3d3.col }
    val lightgreen              by lazy { 0x90ee90.col }
    val lightgrey               by lazy { 0xd3d3d3.col }
    val lightpink               by lazy { 0xffb6c1.col }
    val lightsalmon             by lazy { 0xffa07a.col }
    val lightseagreen           by lazy { 0x20b2aa.col }
    val lightskyblue            by lazy { 0x87cefa.col }
    val lightslategray          by lazy { 0x778899.col }
    val lightslategrey          by lazy { 0x778899.col }
    val lightsteelblue          by lazy { 0xb0c4de.col }
    val lightyellow             by lazy { 0xffffe0.col }
    val lime                    by lazy { 0x00ff00.col }
    val limegreen               by lazy { 0x32cd32.col }
    val linen                   by lazy { 0xfaf0e6.col }
    val magenta                 by lazy { 0xff00ff.col }
    val maroon                  by lazy { 0x800000.col }
    val mediumaquamarine        by lazy { 0x66cdaa.col }
    val mediumblue              by lazy { 0x0000cd.col }
    val mediumorchid            by lazy { 0xba55d3.col }
    val mediumpurple            by lazy { 0x9370db.col }
    val mediumseagreen          by lazy { 0x3cb371.col }
    val mediumslateblue         by lazy { 0x7b68ee.col }
    val mediumspringgreen       by lazy { 0x00fa9a.col }
    val mediumturquoise         by lazy { 0x48d1cc.col }
    val mediumvioletred         by lazy { 0xc71585.col }
    val midnightblue            by lazy { 0x191970.col }
    val mintcream               by lazy { 0xf5fffa.col }
    val mistyrose               by lazy { 0xffe4e1.col }
    val moccasin                by lazy { 0xffe4b5.col }
    val navajowhite             by lazy { 0xffdead.col }
    val navy                    by lazy { 0x000080.col }
    val oldlace                 by lazy { 0xfdf5e6.col }
    val olive                   by lazy { 0x808000.col }
    val olivedrab               by lazy { 0x6b8e23.col }
    val orange                  by lazy { 0xffa500.col }
    val orangered               by lazy { 0xff4500.col }
    val orchid                  by lazy { 0xda70d6.col }
    val palegoldenrod           by lazy { 0xeee8aa.col }
    val palegreen               by lazy { 0x98fb98.col }
    val paleturquoise           by lazy { 0xafeeee.col }
    val palevioletred           by lazy { 0xdb7093.col }
    val papayawhip              by lazy { 0xffefd5.col }
    val peachpuff               by lazy { 0xffdab9.col }
    val peru                    by lazy { 0xcd853f.col }
    val pink                    by lazy { 0xffc0cb.col }
    val plum                    by lazy { 0xdda0dd.col }
    val powderblue              by lazy { 0xb0e0e6.col }
    val purple                  by lazy { 0x800080.col }
    val rebeccapurple           by lazy { 0x663399.col }
    val red                     by lazy { 0xff0000.col }
    val rosybrown               by lazy { 0xbc8f8f.col }
    val royalblue               by lazy { 0x4169e1.col }
    val saddlebrown             by lazy { 0x8b4513.col }
    val salmon                  by lazy { 0xfa8072.col }
    val sandybrown              by lazy { 0xf4a460.col }
    val seagreen                by lazy { 0x2e8b57.col }
    val seashell                by lazy { 0xfff5ee.col }
    val sienna                  by lazy { 0xa0522d.col }
    val silver                  by lazy { 0xc0c0c0.col }
    val skyblue                 by lazy { 0x87ceeb.col }
    val slateblue               by lazy { 0x6a5acd.col }
    val slategray               by lazy { 0x708090.col }
    val slategrey               by lazy { 0x708090.col }
    val snow                    by lazy { 0xfffafa.col }
    val springgreen             by lazy { 0x00ff7f.col }
    val steelblue               by lazy { 0x4682b4.col }
    val tan                     by lazy { 0xd2b48c.col }
    val teal                    by lazy { 0x008080.col }
    val thistle                 by lazy { 0xd8bfd8.col }
    val tomato                  by lazy { 0xff6347.col }
    val turquoise               by lazy { 0x40e0d0.col }
    val violet                  by lazy { 0xee82ee.col }
    val wheat                   by lazy { 0xf5deb3.col }
    val white                   by lazy { 0xffffff.col }
    val whitesmoke              by lazy { 0xf5f5f5.col }
    val yellow                  by lazy { 0xffff00.col }
    val yellowgreen             by lazy { 0x9acd32.col }
}
