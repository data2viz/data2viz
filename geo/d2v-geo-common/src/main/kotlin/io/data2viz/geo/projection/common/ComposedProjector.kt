package io.data2viz.geo.projection.common


/**
 * Create new Projector which combines both projectors transformations
 */
class ComposedProjector(val a: Projector, val b:Projector): Projector  {
    override fun projectLambda(lambda: Double, phi: Double): Double {
        val aX = a.projectLambda(lambda, phi)
        val aY = a.projectPhi(lambda, phi)
        return b.projectLambda(aX, aY)
    }

    override fun projectPhi(lambda: Double, phi: Double): Double {
        val aX = a.projectLambda(lambda, phi)
        val aY = a.projectPhi(lambda, phi)
        return b.projectPhi(aX, aY)
    }

    override fun invert(lambda: Double, phi: Double): DoubleArray {
        val p = b.invert(lambda, phi)
        return a.invert(p[0], p[1])
    }
}
