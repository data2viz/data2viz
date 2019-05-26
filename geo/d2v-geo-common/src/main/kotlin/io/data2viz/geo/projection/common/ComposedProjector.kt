package io.data2viz.geo.projection.common


/**
 * Create new Projector which combines both projectors transformations
 * For example can be used to combine Translate and Rotate transformations
 *
 * @see ProjectorProjection
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

    override fun project(lambda: Double, phi: Double): DoubleArray {
        val p = a.project(lambda, phi)
        return b.project(p[0], p[1])
    }

    override fun invertLambda(lambda: Double, phi: Double): Double {
        val aX = a.invertLambda(lambda, phi)
        val aY = a.invertPhi(lambda, phi)
        return b.invertLambda(aX, aY)
    }

    override fun invertPhi(lambda: Double, phi: Double): Double {
        val aX = a.invertLambda(lambda, phi)
        val aY = a.invertPhi(lambda, phi)
        return b.invertPhi(aX, aY)
    }

    override fun invert(lambda: Double, phi: Double): DoubleArray {
        val p = b.invert(lambda, phi)
        return a.invert(p[0], p[1])
    }
}
