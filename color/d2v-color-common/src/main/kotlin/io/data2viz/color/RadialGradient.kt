package io.data2viz.color

// TODO : center : Point ?
// TODO : set all properties in constructor ?

class RadialGradient

@Deprecated("Deprecated", ReplaceWith("Colors.Gradient.radial()", "io.data2viz.colors.Colors"))
constructor(): Gradient {
    var cx:Double = .0
    var cy:Double = .0
    var r:Double = .0

    override val colorStops = mutableListOf<ColorStop>()
}