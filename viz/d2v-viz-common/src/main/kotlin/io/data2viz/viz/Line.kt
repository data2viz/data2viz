package io.data2viz.viz

import io.data2viz.color.ColorOrGradient


class Line : Node(), HasStroke {

    var x1: Double = .0
    var y1: Double = .0
    var x2: Double = .0
    var y2: Double = .0

    @Deprecated("Use style.stroke", ReplaceWith("style.stroke"))
    override var stroke: ColorOrGradient?
        get() = style.stroke
        set(value) {
            style.stroke = value
        }

    @Deprecated("Use style.strokeWidth", ReplaceWith("style.strokeWidth"))
    override var strokeWidth: Double?
        get() = style.strokeWidth
        set(value) {
            style.strokeWidth = value
        }

}