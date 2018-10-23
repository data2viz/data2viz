package io.data2viz.viz

import io.data2viz.color.ColorOrGradient
import io.data2viz.geom.PathGeom
import io.data2viz.geom.Path

class PathNode(val path: PathGeom = PathGeom()): Node(), HasStroke, HasFill, Path by path {


    /**
     * Remove all segments of the path.
     * Todo should it be defined as a function of Path.
     */
    fun clearPath() {
        path.clearPath()
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

