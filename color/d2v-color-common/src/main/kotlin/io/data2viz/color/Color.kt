package io.data2viz.color

interface Color : ColorOrGradient {
    val rgb:Int
    val rgba:String
    val r:Int
    val g:Int
    val b:Int
    val alpha:Float
    val rgbHex:String
    fun brighten(strength: Double = 1.0):Color
    fun darken(strength: Double = 1.0):Color
    fun saturate(strength: Double = 1.0):Color
    fun desaturate(strength: Double = 1.0):Color
}
