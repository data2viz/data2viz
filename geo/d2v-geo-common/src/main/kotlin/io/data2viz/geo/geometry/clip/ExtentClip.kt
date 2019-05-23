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


/**
 * If extent is specified, sets the projection’s viewport clip extent to the specified bounds in pixels and returns the projection. The extent bounds are specified as an array [[x₀, y₀], [x₁, y₁]], where x₀ is the left-side of the viewport, y₀ is the top, x₁ is the right and y₁ is the bottom. If extent is null, no viewport clipping is performed. If extent is not specified, returns the current viewport clip extent which defaults to null. Viewport clipping is independent of small-circle clipping via projection.clipAngle.
 */
var Projection.extentPostClip: Extent?
    get() = (postClip as? ExtentPostClip)?.extent
    set(value) {

        if (value != null) {
            postClip = ExtentPostClip(value)
        } else {
            postClip = noPostClip
        }
    }