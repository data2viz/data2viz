package io.data2viz.viz

import io.data2viz.color.Color
import io.data2viz.color.ColorOrGradient
import io.data2viz.color.Colors
import io.data2viz.viz.TextAnchor.*

class TextNode : Node(),
        HasFill,
        HasStroke,
        HasTransform {

    override var transform: Transform? = null

    var x: Double = .0
    var y: Double = .0
    var textContent: String = "Type something"
    var fontSize: Double = 20.0
    var fontFamily: Font.DefaultFamily  = Font.DefaultFamily.SANS_SERIF
    var fontWeight: Font.DefaultWeight  = Font.DefaultWeight.NORMAL
    var fontStyle:  Font.DefaultStyle   = Font.DefaultStyle.NORMAL

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



class Font {

    enum class DefaultFamily {
        MONOSPACE,
        SANS_SERIF,
        SERIF,
    }

    enum class DefaultWeight {
        BOLD,
        NORMAL,
    }

    enum class DefaultStyle {
        ITALIC,
        NORMAL,
    }
}
