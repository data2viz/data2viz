package io.data2viz.geo.projection.common

/**
 * Scale & translate projector based on values from [projector]
 *
 */
class TranslateAndScaleProjector(
    val projector: Projector,
    var scale: Double,
    var recenterDx: Double,
    var recenterDy: Double
) : Projector {

    override fun project(lambda: Double, phi: Double): DoubleArray {
        val projected = projector.project(lambda, phi)
        projected[0] = recenterDx + projected[0] * scale
        projected[1] = recenterDy - projected[1] * scale
        return projected
    }


    override fun invert(x: Double, y: Double): DoubleArray {

        return projector.invert(
            (x - recenterDx) / scale,
            -(y - recenterDy) / scale
        )
    }

}