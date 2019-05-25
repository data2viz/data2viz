package io.data2viz.geo.geometry.clip

import io.data2viz.geo.projection.common.Projection
import io.data2viz.geo.stream.Stream
import io.data2viz.math.Angle

private class AnglePreClip(val angle: Angle) : StreamPreClip {

    val clipCircle = CirclePreClip(angle.rad)
    override fun preClip(stream: Stream): Stream {
        return clipCircle.preClip(stream)
    }

}


/**
 * If angle is specified, sets the projection’s clipping circle radius to the specified angle in degrees and returns the projection. If angle is null, switches to antimeridian cutting rather than small-circle clipping. If angle is not specified, returns the current clip angle which defaults to null. Small-circle clipping is independent of viewport clipping via projection.clipExtent.
 */
var Projection.anglePreClip: Angle?
    get() = (preClip as? AnglePreClip)?.angle
    set(value) {

        if (value != null) {
            preClip = AnglePreClip(value)
        } else {
            preClip = antimeridianPreClip
        }
    }