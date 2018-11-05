package io.data2viz.color

import io.data2viz.math.Percent

data class ColorStop(val percent:Percent, val color: Color)

interface ColorOrGradient

interface Gradient : ColorOrGradient {
    val colorStops:List<ColorStop>
}

interface Color : ColorOrGradient {
    val rgb:Int
    val rgba:String
    val r:Int
    val g:Int
    val b:Int
    val alpha:Percent
    val rgbHex:String
    fun toRgb():RgbColor
    fun brighten(strength: Double = 1.0):Color
    fun darken(strength: Double = 1.0):Color
    fun saturate(strength: Double = 1.0):Color
    fun desaturate(strength: Double = 1.0):Color
    fun withAlpha(alpha: Percent):Color
}
