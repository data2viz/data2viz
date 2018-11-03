package io.data2viz.viz

import io.data2viz.color.ColorOrGradient


class LineNode : Node(),
        HasStroke,
        HasTransform {

    override var transform: Transform? = null

    var x1: Double = .0
    var y1: Double = .0
    var x2: Double = .0
    var y2: Double = .0

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