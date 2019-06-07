package io.data2viz.geo.projection.common


/**
 * The unitary mathematical function that transform geographic coordinates (lambda, phi) into
 * cartesian coordinates (x,y).
 */
interface Projectable {
    fun project(lambda: Double, phi: Double): DoubleArray
}


/**
 * The unitary mathematical function that transform cartesian coordinates (x,y) into
 * geographic coordinates (lambda,phi). Not all projections are invertable.
 */
interface Invertable {
    fun invert(x: Double, y: Double): DoubleArray
}


interface Projector: Projectable, Invertable