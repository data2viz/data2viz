package io.data2viz.geo.geometry.clip

import io.data2viz.geo.projection.common.Projection
import io.data2viz.geo.stream.Stream
import io.data2viz.geom.Extent


class ExtentClip(val extent: Extent) : ClipStreamBuilder {

    val clipRectangle = ClipRectangle(extent)

    override fun bindTo(outputStream: Stream): Stream {
        return clipRectangle.clipLine(outputStream)
    }

}

/**
 * Enable to get or set a RectanglePostClip as an Extent (in pixels).
 */
var Projection.extentPostClip: Extent?
    get() = (postClip as? ExtentClip)?.extent

    set(value) {
        if (value != null) {
            postClip = ExtentClip(value)
        } else {
            postClip = NoClip
        }
    }