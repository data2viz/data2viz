package io.data2viz.viz

import io.data2viz.color.ColorOrGradient


class Line : Node(), HasStroke {

    var x1: Double = .0
    var y1: Double = .0
    var x2: Double = .0
    var y2: Double = .0

    override var stroke: ColorOrGradient? = null
    override var strokeWidth: Double? = .0
}