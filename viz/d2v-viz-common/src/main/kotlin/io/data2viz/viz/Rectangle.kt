package io.data2viz.viz

import io.data2viz.color.Color
import io.data2viz.color.ColorOrGradient
import io.data2viz.color.colors


class Rectangle : Node, HasFill {
    var x: Double = 10.0
    var y: Double = 10.0
    var height: Double = 10.0
    var width: Double = 10.0
    override var fill: ColorOrGradient? = colors.black
}