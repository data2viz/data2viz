package io.data2viz.viz

import io.data2viz.color.*

class TextNode : Node(),
        HasFill,
        HasStroke,
        HasTransform {

    override var transform: Transform? = null

    var x: Double = .0
    var y: Double = .0
    var textContent: String = "Type something"
    var fontSize: Double = 20.0
    var fontFamily: FontFamily          = FontFamily.SANS_SERIF
    var fontWeight: FontWeight          = FontWeight.NORMAL
    var fontStyle: FontPosture          = FontPosture.NORMAL

    fun textAlign(horizontal:TextAnchor = anchor, vertical: TextAlignmentBaseline = baseline) = TextAlign(horizontal, vertical)

    var textAlign:TextAlign
        get() = textAlign(anchor, baseline)
        set(value) {
            style.anchor = value.horizontal
            style.baseline = value.vertical
        }

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


class FontFamily private constructor(val name: String) {

    companion object {
        val MONOSPACE  = FontFamily("monospace")
        val SANS_SERIF = FontFamily("sans-serif")
        val SERIF 	   = FontFamily("serif")

        fun specifiedFont(name: String) = FontFamily(name)
    }

}

enum class FontWeight {
    BOLD,
    NORMAL,
}

enum class FontPosture {
    ITALIC,
    NORMAL,
}