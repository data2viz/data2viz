package io.data2viz.color

data class ColorStop(val percent:Double, val color: Color)

interface ColorOrGradient

interface Gradient : ColorOrGradient {
    val colorStops:List<ColorStop>
}

interface Color : ColorOrGradient {
    val rgb:Int

    /**
     * rgba string conforming to CSS specification `rgba(R, G, B, A)`
     * R (red), G (green), and B (blue) can be either <integer>s or <percentage>s,
     * where the number 255 corresponds to 100%.
     * A (alpha) can be a <number> between 0 and 1, or a <percentage>,
     * where the number 1 corresponds to 100% (full opacity).
     */
    val rgba:String
    val r:Int
    val g:Int
    val b:Int
    val alpha:Double
    val rgbHex:String
    fun toRgb():RgbColor
    fun brighten(strength: Double = 1.0):Color
    fun darken(strength: Double = 1.0):Color
    fun saturate(strength: Double = 1.0):Color
    fun desaturate(strength: Double = 1.0):Color
    fun withAlpha(alpha: Double):Color
}
