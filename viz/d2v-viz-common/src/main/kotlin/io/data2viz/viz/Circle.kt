package io.data2viz.viz

import io.data2viz.color.ColorOrGradient


class Circle : Node, HasStroke, HasFill {
    var x: Double = 10.0
    var y: Double = 10.0
    var radius: Double = 10.0

    override var fill: ColorOrGradient? = null
    override var stroke: ColorOrGradient? = null
    override var strokeWidth: Double? = .0

}