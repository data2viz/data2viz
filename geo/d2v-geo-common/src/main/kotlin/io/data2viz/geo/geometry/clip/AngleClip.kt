package io.data2viz.geo.geometry.clip

import io.data2viz.geo.projection.common.Projection
import io.data2viz.geo.stream.Stream
import io.data2viz.math.Angle

//class AngleClip {
//
//    /**
//     * TODO: check
//     * If angle is specified, sets the projection’s clipping circle radius to the specified angle in degrees and returns the projection. If angle is null, switches to antimeridian cutting rather than small-circle clipping. If angle is not specified, returns the current postClip angle which defaults to null. Small-circle clipping is independent of viewport clipping via projection.extentPostClip.
//     */
//    var anglePreClip: Double
//
//
//
//    // TODO : manage angles-range (ex. -180..-90 & 90..180) to permit see-through ?
//    private var theta: Double = Double.NaN
//    override var anglePreClip: Double
//        get() = theta
//        set(value) {
//            if (value.isNaN()) {
//                theta = Double.NaN
//                preClip = clipAntimeridian()
//            } else {
//                theta = value.toRadians()
//                preClip = clipCircle(theta)
//            }
//        }
//}

class AngleClip(val angle: Angle) : StreamPreClip {

    val clipCircle = ClipCirclePreClip(angle.rad)
    override fun preClip(stream: Stream): Stream {
        return clipCircle.preClip(stream)
    }

}


var Projection.anglePreClip: Angle?
    get() = (preClip as? AngleClip)?.angle
    set(value) {

        if (value != null) {
            preClip = AngleClip(value)
        } else {
            preClip = antimeridianPreClip
        }
    }