package io.data2viz.viz

import io.data2viz.color.ColorOrGradient


class Rect : Node(), HasStroke, HasFill {

    var x: Double = .0
    var y: Double = .0
    var width: Double = .0
    var height: Double = .0

    @Deprecated("Use style.fill", ReplaceWith("style.fill"))
    override var fill: ColorOrGradient?
        get() = style.fill
        set(value) {
            style.fill = value
        }

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