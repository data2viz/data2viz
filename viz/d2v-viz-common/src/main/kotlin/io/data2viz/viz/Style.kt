package io.data2viz.viz

import io.data2viz.color.ColorOrGradient


/**
 * Holds the styling property.
 */
class Style {

    var fill: ColorOrGradient? = null
    var stroke: ColorOrGradient? = null

    var strokeWidth: Double? = 1.0

    var anchor: TextAnchor = TextAnchor.START

    var baseline: TextAlignmentBaseline = TextAlignmentBaseline.BASELINE

}

data class TextAlign internal constructor(val horizontal:TextAnchor, val vertical: TextAlignmentBaseline)

/**
 * The text-anchor attribute is used to horizontally align ([START], [MIDDLE] or [END]-alignment) a string of
 * text relative to a given point.
 * See [CSS text-anchor][https://developer.mozilla.org/en-US/docs/Web/SVG/Attribute/text-anchor]
 */
enum class TextAnchor {
    START,
    MIDDLE,
    END
}


/**
 * Vertical alignment of a text
 */
enum class TextAlignmentBaseline {
    HANGING,
    MIDDLE,
    BASELINE
}
