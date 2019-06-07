package io.data2viz.geo.projection.common


/**
 * Create new Projector which combines both projectors transformations
 * For example can be used to combine Translate and Rotate transformations
 *
 * @see ProjectorProjection
 */
class ComposedProjector(val a: Projector, val b:Projector): Projector  {

    override fun project(lambda: Double, phi: Double): DoubleArray {
        val p = a.project(lambda, phi)
        return b.project(p[0], p[1])
    }

    override fun invert(lambda: Double, phi: Double): DoubleArray {
        val p = b.invert(lambda, phi)
        return a.invert(p[0], p[1])
    }
}
