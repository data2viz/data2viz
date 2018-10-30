package io.data2viz.viz

import io.data2viz.color.ColorOrGradient
import io.data2viz.geom.Rect
import io.data2viz.geom.RectGeom


class RectNode(rect: Rect = RectGeom()) : Node(),
        Rect by rect,
        HasFill,
        HasStroke {


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
