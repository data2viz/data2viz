package io.data2viz.color

class RadialGradient: ColorOrGradient {
    var cx:Double = .0
    var cy:Double = .0
    var r:Double = .0

    val colorStops = mutableListOf<ColorStop>()

    fun addColor(percent: Double, color: RgbColor){
        val checkedPercent = percent.coerceIn(.0, 1.0)
        colorStops.add(ColorStop(checkedPercent, color))
    }
}