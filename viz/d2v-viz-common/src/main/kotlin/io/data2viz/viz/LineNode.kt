package io.data2viz.viz

import io.data2viz.color.ColorOrGradient
import io.data2viz.geom.HasStartAndEnd


class LineNode : Node(), HasStroke, HasTransform, HasStartAndEnd {

    override var transform: Transform? = null

    override var x1: Double = .0
    override var y1: Double = .0
    override var x2: Double = .0
    override var y2: Double = .0

    override var stroke: ColorOrGradient?
        get() = style.stroke
        set(value) {
            style.stroke = value
        }

    override var strokeWidth: Double?
        get() = style.strokeWidth
        set(value) {
            style.strokeWidth = value
        }

}