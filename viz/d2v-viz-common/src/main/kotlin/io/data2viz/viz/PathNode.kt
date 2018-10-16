package io.data2viz.viz

import io.data2viz.color.ColorOrGradient
import io.data2viz.path.Path
import io.data2viz.path.PathAdapter

class PathNode(val path: Path = Path()): Node(), HasStroke, HasFill, PathAdapter by path {


    /**
     * Remove all segments of the path.
     * Todo should it be defined as a function of PathAdapter.
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

