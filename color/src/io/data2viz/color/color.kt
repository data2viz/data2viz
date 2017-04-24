package io.data2viz.color


val String.color: Color
    get():Color {
        val regex = """^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$""".toRegex()
        if (!this.matches(regex)) error("Conversion of string to io.data2viz.color.getColor works for encoded colors like #12abCD")
        return Color(this.substring(1).toInt(16))
    }

fun rgba(r: Number, g: Number, b: Number, a: Number) = Color().apply { rgba(r, g, b, a) }

class Color(var rgb: Int = 0xffffff, var _alpha: Float = 1.0f) {

    var r: Int
        get() = (rgb shr 16) and 0xff
        set(value) {
            require(value  < 256) {"r should be less or equal to 255"}
            require(value  > -1) {"r should be greater or equal to 0"}
            rgb = (rgb and 0x00ffff) + (value shl 16)
        }

    var g: Int
        get() = (rgb shr 8) and 0xff
        set(value) {
            require(value  < 256) {"g should be less or equal to 255"}
            require(value  > -1) {"g should be greater or equal to 0"}
            rgb = (rgb and 0xff00ff) + (value shl 8)
        }

    var b: Int
        get() = rgb and 0xff
        set(value) {
            require(value  < 256) {"b should be less or equal to 255"}
            require(value  > -1) {"b should be greater or equal to 0"}
            rgb = (rgb and 0xffff00) + value
        }

    var alpha: Number
        get() = _alpha
        set(value) {
            _alpha = value.toFloat()
        }

    fun rgba(r: Number, g: Number, b: Number, a: Number) {
        this.r = r.toInt()
        this.g = g.toInt()
        this.b = b.toInt()
        alpha = a.toFloat()
    }

    val rgbHex: String
        get() = "#" +
                ((rgb shr 20) and 0xf).toString(16) +
                ((rgb shr 16) and 0xf).toString(16) +
                ((rgb shr 12) and 0xf).toString(16) +
                ((rgb shr 8) and 0xf).toString(16) +
                ((rgb shr 4) and 0xf).toString(16) +
                (rgb  and 0xf).toString(16)

    @Suppress("UnsafeCastFromDynamic")
    fun Int.toString(radix: Int):String = asDynamic().toString(radix)

    override fun toString() = if(alpha.toFloat() < 1.0) "rgba($r,$g,$b,$alpha)" else rgbHex
}

object colors {

    val Int.col: Color
        get() = Color(this)

    val aliceblue       by lazy { 0xf0f8ff.col }
    val antiquewhite    by lazy { 0xfaebd7.col }
    val aqua            by lazy { 0x00ffff.col }
    val aquamarine      by lazy { 0x7fffd4.col }
    val azure           by lazy { 0xf0ffff.col }
    val beige           by lazy { 0xf5f5dc.col }
    val bisque          by lazy { 0xffe4c4.col }
    val black           by lazy { 0x000000.col }
    val blanchedalmond  by lazy { 0xffebcd.col }
    val blue            by lazy { 0x0000ff.col }
    val blueviolet      by lazy { 0x8a2be2.col }
    val brown           by lazy { 0xa52a2a.col }
    val burlywood       by lazy { 0xdeb887.col }
    val cadetblue       by lazy { 0x5f9ea0.col }
    val chartreuse      by lazy { 0x7fff00.col }
    val chocolate       by lazy { 0xd2691e.col }
    val coral           by lazy { 0xff7f50.col }
    val cornflowerblue  by lazy { 0x6495ed.col }
    val cornsilk        by lazy { 0xfff8dc.col }
    val crimson         by lazy { 0xdc143c.col }
    val cyan            by lazy { 0x00ffff.col }
    val darkblue        by lazy { 0x00008b.col }
    val darkcyan        by lazy { 0x008b8b.col }
    val darkgoldenrod   by lazy { 0xb8860b.col }
    val darkgray        by lazy { 0xa9a9a9.col }
    val darkgreen       by lazy { 0x006400.col }
    val darkgrey        by lazy { 0xa9a9a9.col }
    val darkkhaki       by lazy { 0xbdb76b.col }
    val darkmagenta     by lazy { 0x8b008b.col }
    val darkolivegreen  by lazy { 0x556b2f.col }
    val darkorange      by lazy { 0xff8c00.col }
    val darkorchid      by lazy { 0x9932cc.col }
    val darkred         by lazy { 0x8b0000.col }
    val darksalmon      by lazy { 0xe9967a.col }
    val darkseagreen    by lazy { 0x8fbc8f.col }
    val darkslateblue   by lazy { 0x483d8b.col }
    val darkslategray   by lazy { 0x2f4f4f.col }
    val darkslategrey   by lazy { 0x2f4f4f.col }
    val darkturquoise   by lazy { 0x00ced1.col }
    val darkviolet      by lazy { 0x9400d3.col }
    val deeppink        by lazy { 0xff1493.col }
    val deepskyblue     by lazy { 0x00bfff.col }
    val dimgray         by lazy { 0x696969.col }
    val dimgrey         by lazy { 0x696969.col }
    val dodgerblue      by lazy { 0x1e90ff.col }
    val firebrick       by lazy { 0xb22222.col }
    val floralwhite     by lazy { 0xfffaf0.col }
    val forestgreen     by lazy { 0x228b22.col }
    val fuchsia         by lazy { 0xff00ff.col }
    val gainsboro       by lazy { 0xdcdcdc.col }
    val ghostwhite      by lazy { 0xf8f8ff.col }
    val gold            by lazy { 0xffd700.col }
    val goldenrod       by lazy { 0xdaa520.col }
    val gray            by lazy { 0x808080.col }
    val green           by lazy { 0x008000.col }
    val greenyellow     by lazy { 0xadff2f.col }
    val grey            by lazy { 0x808080.col }
    val honeydew        by lazy { 0xf0fff0.col }
    val hotpink         by lazy { 0xff69b4.col }
    val indianred       by lazy { 0xcd5c5c.col }
    val indigo          by lazy { 0x4b0082.col }
    val ivory           by lazy { 0xfffff0.col }
    val khaki           by lazy { 0xf0e68c.col }
    val lavender        by lazy { 0xe6e6fa.col }
    val lavenderblush   by lazy { 0xfff0f5.col }
    val lawngreen       by lazy { 0x7cfc00.col }
    val lemonchiffon    by lazy { 0xfffacd.col }
    val lightblue       by lazy { 0xadd8e6.col }
    val lightcoral      by lazy { 0xf08080.col }
    val lightcyan       by lazy { 0xe0ffff.col }
    val lightgoldenrodyellow by lazy { 0xfafad2.col }
    val lightgray       by lazy { 0xd3d3d3.col }
    val lightgreen      by lazy { 0x90ee90.col }
    val lightgrey       by lazy { 0xd3d3d3.col }
    val lightpink       by lazy { 0xffb6c1.col }
    val lightsalmon     by lazy { 0xffa07a.col }
    val lightseagreen   by lazy { 0x20b2aa.col }
    val lightskyblue    by lazy { 0x87cefa.col }
    val lightslategray  by lazy { 0x778899.col }
    val lightslategrey  by lazy { 0x778899.col }
    val lightsteelblue  by lazy { 0xb0c4de.col }
    val lightyellow     by lazy { 0xffffe0.col }
    val lime            by lazy { 0x00ff00.col }
    val limegreen       by lazy { 0x32cd32.col }
    val linen           by lazy { 0xfaf0e6.col }
    val magenta         by lazy { 0xff00ff.col }
    val maroon          by lazy { 0x800000.col }
    val mediumaquamarine by lazy { 0x66cdaa.col }
    val mediumblue      by lazy { 0x0000cd.col }
    val mediumorchid    by lazy { 0xba55d3.col }
    val mediumpurple    by lazy { 0x9370db.col }
    val mediumseagreen  by lazy { 0x3cb371.col }
    val mediumslateblue by lazy { 0x7b68ee.col }
    val mediumspringgreen by lazy { 0x00fa9a.col }
    val mediumturquoise by lazy { 0x48d1cc.col }
    val mediumvioletred by lazy { 0xc71585.col }
    val midnightblue    by lazy { 0x191970.col }
    val mintcream       by lazy { 0xf5fffa.col }
    val mistyrose       by lazy { 0xffe4e1.col }
    val moccasin        by lazy { 0xffe4b5.col }
    val navajowhite     by lazy { 0xffdead.col }
    val navy            by lazy { 0x000080.col }
    val oldlace         by lazy { 0xfdf5e6.col }
    val olive           by lazy { 0x808000.col }
    val olivedrab       by lazy { 0x6b8e23.col }
    val orange          by lazy { 0xffa500.col }
    val orangered       by lazy { 0xff4500.col }
    val orchid          by lazy { 0xda70d6.col }
    val palegoldenrod   by lazy { 0xeee8aa.col }
    val palegreen       by lazy { 0x98fb98.col }
    val paleturquoise   by lazy { 0xafeeee.col }
    val palevioletred   by lazy { 0xdb7093.col }
    val papayawhip      by lazy { 0xffefd5.col }
    val peachpuff       by lazy { 0xffdab9.col }
    val peru            by lazy { 0xcd853f.col }
    val pink            by lazy { 0xffc0cb.col }
    val plum            by lazy { 0xdda0dd.col }
    val powderblue      by lazy { 0xb0e0e6.col }
    val purple          by lazy { 0x800080.col }
    val rebeccapurple   by lazy { 0x663399.col }
    val red             by lazy { 0xff0000.col }
    val rosybrown       by lazy { 0xbc8f8f.col }
    val royalblue       by lazy { 0x4169e1.col }
    val saddlebrown     by lazy { 0x8b4513.col }
    val salmon          by lazy { 0xfa8072.col }
    val sandybrown      by lazy { 0xf4a460.col }
    val seagreen        by lazy { 0x2e8b57.col }
    val seashell        by lazy { 0xfff5ee.col }
    val sienna          by lazy { 0xa0522d.col }
    val silver          by lazy { 0xc0c0c0.col }
    val skyblue         by lazy { 0x87ceeb.col }
    val slateblue       by lazy { 0x6a5acd.col }
    val slategray       by lazy { 0x708090.col }
    val slategrey       by lazy { 0x708090.col }
    val snow            by lazy { 0xfffafa.col }
    val springgreen     by lazy { 0x00ff7f.col }
    val steelblue       by lazy { 0x4682b4.col }
    val tan             by lazy { 0xd2b48c.col }
    val teal            by lazy { 0x008080.col }
    val thistle         by lazy { 0xd8bfd8.col }
    val tomato          by lazy { 0xff6347.col }
    val turquoise       by lazy { 0x40e0d0.col }
    val violet          by lazy { 0xee82ee.col }
    val wheat           by lazy { 0xf5deb3.col }
    val white           by lazy { 0xffffff.col }
    val whitesmoke      by lazy { 0xf5f5f5.col }
    val yellow          by lazy { 0xffff00.col }
    val yellowgreen     by lazy { 0x9acd32.col }
}
