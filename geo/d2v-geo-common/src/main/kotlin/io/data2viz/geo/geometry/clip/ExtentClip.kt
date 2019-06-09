package io.data2viz.geo.geometry.clip

import io.data2viz.geo.projection.common.Projection
import io.data2viz.geo.stream.Stream
import io.data2viz.geom.Extent


class ExtentClip(val extent: Extent) : StreamClip {
    val clipRectangle = ClipRectangle(extent)
    override fun clipStream(stream: Stream): Stream {
        return clipRectangle.clipLine(stream)
    }

}



/**
 * If extent is specified, sets the projection’s viewport clip extent to the specified bounds in pixels
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