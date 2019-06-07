package io.data2viz.geo.projection.common



interface Invertable {
    fun invert(lambda: Double, phi: Double): DoubleArray
}

interface Projectable {
    fun project(lambda: Double, phi: Double): DoubleArray
}


interface Projector: Projectable, Invertable