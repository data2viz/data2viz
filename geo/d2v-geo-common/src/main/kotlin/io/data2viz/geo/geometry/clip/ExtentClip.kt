package io.data2viz.geo.geometry.clip

import io.data2viz.geo.projection.common.Projection
import io.data2viz.geo.stream.Stream
import io.data2viz.geom.Extent


class ExtentPostClip(val extent: Extent) : StreamPostClip {
    val clipRectangle = ClipRectangle(extent)
    override fun postClip(stream: Stream): Stream {
        return clipRectangle.clipLine(stream)
    }

}



var Projection.extentPostClip: Extent?
    get() = (postClip as? ExtentPostClip)?.extent
    set(value) {

        if (value != null) {
            postClip = ExtentPostClip(value)
        } else {
            postClip = noPostClip
        }
    }