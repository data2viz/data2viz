package io.data2viz.viz

import io.data2viz.color.ColorOrGradient
import io.data2viz.color.colors
import io.data2viz.viz.TextAnchor.*


class Text : Node, HasFill {
    var x: Double = .0
    var y: Double = .0
    var textContent: String = ""
    var anchor: TextAnchor = TextAnchor.START
    var baseline: TextAlignmentBaseline = TextAlignmentBaseline.BASELINE
    override var fill: ColorOrGradient? = colors.black
}


/**
 * The text-anchor attribute is used to horizontally align ([START], [MIDDLE] or [END]-alignment) a string of
 * text relative to a given point.
 * See [CSS text-anchor][https://developer.mozilla.org/en-US/docs/Web/SVG/Attribute/text-anchor]
 */
enum class TextAnchor {
    START,
    MIDDLE,
    END}


/**
 * Vertical alignment of a text
 */
enum class TextAlignmentBaseline {
    HANGING,
    MIDDLE,
    BASELINE
}
