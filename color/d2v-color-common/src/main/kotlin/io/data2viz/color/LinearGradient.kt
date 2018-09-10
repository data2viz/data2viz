package io.data2viz.color

class LinearGradient: ColorOrGradient {
    var x1:Double = .0
    var y1:Double = .0
    var x2:Double = .0
    var y2:Double = .0

    val colorStops = mutableListOf<ColorStop>()

    fun addColor(percent: Double, color: RgbColor){
        val checkedPercent = percent.coerceIn(.0, 1.0)
        colorStops.add(ColorStop(checkedPercent, color))
    }
}