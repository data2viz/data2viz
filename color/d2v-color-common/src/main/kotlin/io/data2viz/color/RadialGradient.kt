package io.data2viz.color

class RadialGradient: Gradient {
    var cx:Double = .0
    var cy:Double = .0
    var r:Double = .0

    override val colorStops = mutableListOf<ColorStop>()
}