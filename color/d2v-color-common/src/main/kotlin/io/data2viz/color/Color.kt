package io.data2viz.color

interface ColorOrGradient

interface Color : ColorOrGradient {
    val rgb:Int
    val rgba:String
    val r:Int
    val g:Int
    val b:Int
    val alpha:Double
    val rgbHex:String
    fun toRgbColor():RgbColor
    fun brighten(strength: Double = 1.0):Color
    fun darken(strength: Double = 1.0):Color
    fun saturate(strength: Double = 1.0):Color
    fun desaturate(strength: Double = 1.0):Color
    fun withAlpha(alpha: Double):Color
}

data class ColorStop(val percent:Double, val color: Color)
