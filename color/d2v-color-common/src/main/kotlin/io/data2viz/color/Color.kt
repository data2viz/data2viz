package io.data2viz.color

data class ColorStop(val percent:Double, val color: Color)

interface ColorOrGradient

interface Gradient : ColorOrGradient {
    val colorStops:MutableList<ColorStop>

    fun addColor(percent: Double, color: Color){
        val checkedPercent = percent.coerceIn(.0, 1.0)
        colorStops.add(ColorStop(checkedPercent, color))
    }
}

interface Color : ColorOrGradient {
    val rgb:Int
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
