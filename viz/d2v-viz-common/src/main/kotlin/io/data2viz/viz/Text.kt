package io.data2viz.viz

import io.data2viz.color.ColorOrGradient
import io.data2viz.viz.TextAnchor.*

class Text : Node(), HasFill, HasStroke {

    var x: Double = .0
    var y: Double = .0
    var textContent: String = ""


    @Deprecated("Use style.anchor", ReplaceWith("style.anchor"))
    var anchor: TextAnchor
        get() = style.anchor
        set(value) {
            style.anchor = value
        }

    @Deprecated("Use style.baseline", ReplaceWith("style.baseline"))
    var baseline: TextAlignmentBaseline
        get() = style.baseline
        set(value) {
            style.baseline = value
        }

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


