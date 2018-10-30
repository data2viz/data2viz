package io.data2viz.color

class LinearGradient: Gradient {
    var x1:Double = .0
    var y1:Double = .0
    var x2:Double = .0
    var y2:Double = .0

    override val colorStops = mutableListOf<ColorStop>()
}