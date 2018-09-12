package io.data2viz.viz

import io.data2viz.color.ColorOrGradient


class Rect : Node(), HasStroke, HasFill {

    var x: Double = .0
    var y: Double = .0
    var width: Double = .0
    var height: Double = .0

    override var fill: ColorOrGradient? = null
    override var stroke: ColorOrGradient? = null
    override var strokeWidth: Double? = .0

}