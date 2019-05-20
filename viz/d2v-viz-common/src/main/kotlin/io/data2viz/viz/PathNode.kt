package io.data2viz.viz

import io.data2viz.geom.PathGeom
import io.data2viz.geom.Path

open class PathNode(val path: PathGeom = PathGeom()): Node(),
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

}

