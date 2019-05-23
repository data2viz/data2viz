package io.data2viz.geo.projection

import io.data2viz.geo.projection.common.ProjectorProjection
import io.data2viz.geo.projection.common.Projector
import io.data2viz.math.Angle
import io.data2viz.math.PI
import io.data2viz.math.rad


fun conicProjection(projection: ConicProjector, init: ConicProjection.() -> Unit) = ConicProjection(projection).apply(init)


interface ConicProjector : Projector {
    var phi0: Double
    var phi1: Double
}

class ConicProjection(val conicProjector: ConicProjector) : ProjectorProjection(conicProjector) {
    var phi0: Double = 0.0
    var phi1: Double = PI / 3.0


    fun parallels(min:Angle, max:Angle) {
        parallelsMin = min
        parallelsMax = max
    }

    var parallelsMin: Angle
        get() = phi0.rad
        set(value) {
            phi0 = value.rad
            conicProjector.phi0 = phi0
        }

    var parallelsMax: Angle
        get() = phi1.rad
        set(value) {
            phi1 = value.rad
            conicProjector.phi1 = phi1
        }

}
