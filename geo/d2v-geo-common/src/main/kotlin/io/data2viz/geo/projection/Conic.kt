package io.data2viz.geo.projection

import io.data2viz.geo.projection.common.ProjectableProjection
import io.data2viz.geo.projection.common.Projector
import io.data2viz.math.PI
import io.data2viz.math.rad


fun conicProjection(projection: ConicProjector, init: ConicProjection.() -> Unit) = ConicProjection(projection).apply(init)


interface ConicProjector : Projector {
    var phi0: Double
    var phi1: Double
}

class ConicProjection(val conicProjector: ConicProjector) : ProjectableProjection(conicProjector) {
    var phi0: Double = 0.0
    var phi1: Double = PI / 3.0
    var parallels
        get() = arrayOf(phi0.rad, phi1.rad)
        set(value) {
            phi0 = value[0].rad
            phi1 = value[1].rad

            conicProjector.phi0 = phi0
            conicProjector.phi1 = phi1
        }
}
