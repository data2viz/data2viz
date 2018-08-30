package io.data2viz.viz

import io.data2viz.color.Color
import io.data2viz.color.ColorOrGradient


class Circle : Node, HasStroke {
    var x: Double = 10.0
    var y: Double = 10.0
    var radius: Double = 10.0

    var fill: Color? = null

    override var stroke: ColorOrGradient? = null
    override var strokeWidth: Double? = .0

}