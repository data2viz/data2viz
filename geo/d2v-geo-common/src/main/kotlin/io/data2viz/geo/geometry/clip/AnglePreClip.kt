package io.data2viz.geo.geometry.clip

import io.data2viz.geo.projection.common.Projection
import io.data2viz.geo.stream.Stream
import io.data2viz.math.Angle
import io.data2viz.math.toRadians

private class AnglePreClip(val angle: Angle) : ClipStreamBuilder {

    val transformedAngleInDegrees = (angle.deg % 360)
    val clipCircle = CirclePreClip(transformedAngleInDegrees.toRadians())

    override fun bindTo(downstream: Stream): Stream {
        return clipCircle.bindTo(downstream)
    }

}

/**
 * Enable to set or get a small circle PreClip from an Angle.
 *
 * If set with null Angle, switches to antimeridian cutting rather than small-circle clipping.
 *
 * If angle is not specified, returns the current clip angle which defaults to null.
 *
 * Small-circle clipping is independent of viewport clipping via projection.clipExtent.
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