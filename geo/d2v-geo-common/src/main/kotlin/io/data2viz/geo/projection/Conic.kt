package io.data2viz.geo.projection

import io.data2viz.math.PI
import io.data2viz.math.rad

interface ConicProjectable : Projectable {
    var phi0: Double
    var phi1: Double
}

class ConicProjection(val conicProjectable: ConicProjectable) : MutableProjection(conicProjectable) {


    var phi0: Double = 0.0
    var phi1: Double = PI / 3.0
    var parallels
        get() = arrayOf(phi0.rad, phi1.rad)
        set(value) {
            phi0 = value[0].rad
            phi1 = value[1].rad

            conicProjectable.phi0 = phi0
            conicProjectable.phi1 = phi1
        }



}

fun conicProjection(projection: ConicProjectable, init: ConicProjection.() -> Unit) = ConicProjection(projection).apply(init)
