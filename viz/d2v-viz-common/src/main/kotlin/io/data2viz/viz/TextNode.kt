package io.data2viz.viz

import io.data2viz.color.ColorOrGradient
import io.data2viz.viz.TextAnchor.*

class TextNode : Node(),
        HasFill,
        HasStroke,
        HasTransform {

    override var transform: Transform? = null

    var x: Double = .0
    var y: Double = .0
    var textContent: String = ""


    var anchor: TextAnchor
        get() = style.anchor
        set(value) {
            style.anchor = value
        }

    var baseline: TextAlignmentBaseline
        get() = style.baseline
        set(value) {
            style.baseline = value
        }

    override var fill: ColorOrGradient?
        get() = style.fill
        set(value) {
            style.fill = value
        }

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


