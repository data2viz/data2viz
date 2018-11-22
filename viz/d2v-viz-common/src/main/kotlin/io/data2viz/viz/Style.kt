@file:Suppress("OverridingDeprecatedMember")

package io.data2viz.viz

import io.data2viz.color.ColorOrGradient


/**
 * Holds the styling property.
 */
interface Style {
    var fill: ColorOrGradient?
    var stroke: ColorOrGradient?
    var strokeWidth: Double?
    @Deprecated("Use hAlign", ReplaceWith("hAlign"))
    var anchor: THAlign
    var hAlign: THAlign

    @Deprecated("Use vAlign", ReplaceWith("vAlign"))
    var baseline: TVAlign
    var vAlign: TVAlign
}

internal class StyleImpl: Style {
    override var fill: ColorOrGradient? = null
    override var stroke: ColorOrGradient? = null
    override var strokeWidth: Double? = 1.0
    override var hAlign: THAlign = THAlign.LEFT
    override var anchor: THAlign = hAlign
    override var vAlign: TVAlign = TVAlign.BASELINE
    override var baseline: TVAlign = vAlign
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

    private var hAlignSet = false
    override var hAlign: THAlign
        get() = if (hAlignSet) style?.hAlign!! else parent?.hAlign!!
        set(value) {
            if (style == null)
                style = StyleImpl()
            hAlignSet = true
            style?.hAlign = value
        }
    override var anchor: THAlign
        get() = hAlign
        set(value) {
            hAlign = value
        }

    private var vAlignSet = false
    override var vAlign: TVAlign
        get() = if (vAlignSet) style?.vAlign!! else parent?.vAlign!!
        set(value) {
            if (style == null)
                style = StyleImpl()
            vAlignSet = true
            style?.vAlign = value
        }
    @Deprecated("Use vAlign", ReplaceWith("vAlign"))
    override var baseline: TVAlign
        get() = vAlign
        set(value) {
            vAlign = value
        }

}

/**
 * The text-anchor attribute is used to horizontally align ([LEFT], [MIDDLE] or [RIGHT]-alignment) a string of
 * text relative to a given point.
 * See [CSS text-anchor][https://developer.mozilla.org/en-US/docs/Web/SVG/Attribute/text-anchor]
 */
enum class THAlign {
    LEFT,
    @Deprecated("Use LEFT", ReplaceWith("LEFT"))
    START,
    MIDDLE,
    RIGHT,
    @Deprecated("Use RIGHT", ReplaceWith("RIGHT"))
    END
}

@Deprecated("Use THAlign", ReplaceWith("THAlign"))
typealias TextAnchor = THAlign

/**
 * Vertical alignment of a text
 */
enum class TVAlign {
    HANGING,
    MIDDLE,
    BASELINE
}
@Deprecated("Use TVAlign", ReplaceWith("TVAlign"))
typealias TextAlignmentBaseline = TVAlign

