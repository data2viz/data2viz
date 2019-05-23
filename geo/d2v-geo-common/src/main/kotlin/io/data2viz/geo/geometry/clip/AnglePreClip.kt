package io.data2viz.geo.geometry.clip

import io.data2viz.geo.projection.common.Projection
import io.data2viz.geo.stream.Stream
import io.data2viz.math.Angle

class AnglePreClip(val angle: Angle) : StreamPreClip {

    val clipCircle = CirclePreClip(angle.rad)
    override fun preClip(stream: Stream): Stream {
        return clipCircle.preClip(stream)
    }

}


var Projection.anglePreClip: Angle?
    get() = (preClip as? AnglePreClip)?.angle
    set(value) {

        if (value != null) {
            preClip = AnglePreClip(value)
        } else {
            preClip = antimeridianPreClip
        }
    }