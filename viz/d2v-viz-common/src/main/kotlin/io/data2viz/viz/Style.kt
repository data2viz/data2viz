package io.data2viz.viz

import io.data2viz.color.ColorOrGradient


/**
 * Holds the styling property.
 */
interface Style {
    var fill: ColorOrGradient?
    var stroke: ColorOrGradient?
    var strokeWidth: Double?
    var anchor: TextAnchor
    var baseline: TextAlignmentBaseline
}

internal class StyleImpl: Style {
    override var fill: ColorOrGradient? = null
    override var stroke: ColorOrGradient? = null
    override var strokeWidth: Double? = 1.0
    override var anchor: TextAnchor = TextAnchor.START
    override var baseline: TextAlignmentBaseline = TextAlignmentBaseline.BASELINE
}

class HierarchicalStyle(var parent:Style?): Style {
    private var style:Style? = null

    private var fillSet = false
    override var fill: ColorOrGradient?
        get() = if (fillSet) style!!.fill else parent?.fill
        set(value) {
            if (style == null)
                style = StyleImpl()
            fillSet = true
            style?.fill = value
        }

    private var strokeSet = false
    override var stroke: ColorOrGradient?
        get() = if (strokeSet) style!!.stroke else parent?.stroke
        set(value) {
            if (style == null)
                style = StyleImpl()
            strokeSet = true
            style?.stroke = value
        }

    private var strokeWidthSet = false
    override var strokeWidth: Double?
        get() = if (strokeWidthSet) style?.strokeWidth else parent?.strokeWidth
        set(value) {
            if (style == null)
                style = StyleImpl()
            strokeWidthSet = true
            style?.strokeWidth = value
        }

    private var anchorSet = false
    override var anchor: TextAnchor
        get() = if (anchorSet) style?.anchor!! else parent?.anchor!!
        set(value) {
            if (style == null)
                style = StyleImpl()
            anchorSet = true
            style?.anchor = value
        }

    private var baselineSet = false
    override var baseline: TextAlignmentBaseline
        get() = if (baselineSet) style?.baseline!! else parent?.baseline!!
        set(value) {
            if (style == null)
                style = StyleImpl()
            baselineSet = true
            style?.baseline = value
        }

}

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
