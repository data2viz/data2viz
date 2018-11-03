package io.data2viz.viz

import io.data2viz.color.ColorOrGradient
import io.data2viz.geom.PathGeom
import io.data2viz.geom.Path

class PathNode(val path: PathGeom = PathGeom()): Node(),
        HasStroke,
        HasFill,
        HasTransform,
        Path by path {

    override var transform: Transform? = null

    /**
     * Remove all segments of the path.
     * Todo should it be defined as a function of Path.
     */
    fun clearPath() {
        path.clearPath()
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

