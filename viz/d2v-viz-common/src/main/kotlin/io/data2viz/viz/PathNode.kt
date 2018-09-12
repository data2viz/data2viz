package io.data2viz.viz

import io.data2viz.color.ColorOrGradient
import io.data2viz.color.colors
import io.data2viz.path.Path
import io.data2viz.path.PathAdapter

class PathNode(val path: Path = Path()): Node(), HasStroke, HasFill, PathAdapter by path {

    override var fill: ColorOrGradient? = colors.black
    override var stroke: ColorOrGradient? = colors.black
    override var strokeWidth: Double? = .0

    var strokeStyle: Double = .0

}

