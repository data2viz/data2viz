package io.data2viz.color

// TODO from + to : Point ?
// TODO : set all properties in constructor ?

class LinearGradient

@Deprecated("Deprecated", ReplaceWith("Colors.Gradient.linear()", "io.data2viz.colors.Colors"))
constructor() : Gradient {
    var x1: Double = .0
    var y1: Double = .0
    var x2: Double = .0
    var y2: Double = .0

    override val colorStops = mutableListOf<ColorStop>()
        get() {
            if (field.size < 2)
                println("[WARN] Color gradient does not have 2 or more colors, this may produce unexpected results.")
            return field
        }
}