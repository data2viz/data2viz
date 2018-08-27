package io.data2viz.viz

import io.data2viz.color.ColorOrGradient
import io.data2viz.color.colors
import io.data2viz.path.Path

class PathNode: Path(), Node, HasStroke, HasFill {

    override var fill: ColorOrGradient? = colors.black
    override var stroke: ColorOrGradient? = colors.black

    override var strokeWidth: Double? = .0
    var strokeStyle: Double = .0

}

