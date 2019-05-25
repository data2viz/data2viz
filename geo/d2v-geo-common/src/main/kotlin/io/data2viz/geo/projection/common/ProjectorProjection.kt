package io.data2viz.geo.projection.common

import io.data2viz.geo.geometry.clip.StreamPostClip
import io.data2viz.geo.geometry.clip.StreamPreClip
import io.data2viz.geo.geometry.clip.antimeridianPreClip
import io.data2viz.geo.geometry.clip.noPostClip
import io.data2viz.geo.stream.DelegateStreamAdapter
import io.data2viz.geo.stream.Stream
import io.data2viz.math.Angle
import io.data2viz.math.rad
import io.data2viz.math.toDegrees
import io.data2viz.math.toRadians
import kotlin.math.sqrt


/**
 * Create [Projection] for give [Projector]
 */
fun projection(projection: Projector, init: ProjectorProjection.() -> Unit) = ProjectorProjection(
    projection
).apply(init)


private val transformRadians: (stream: Stream) -> DelegateStreamAdapter = { stream: Stream ->
    object : DelegateStreamAdapter(stream) {
        override fun point(x: Double, y: Double, z: Double) =
            stream.point(x.toRadians(), y.toRadians(), z.toRadians())
    }
}

private fun transformRotate(rotate: Projector): (stream: Stream) -> DelegateStreamAdapter = { stream: Stream ->
    object : DelegateStreamAdapter(stream) {
        override fun point(x: Double, y: Double, z: Double) {
            stream.point(rotate.projectLambda(x, y), rotate.projectPhi(x, y), 0.0)
        }
    }
}

/**
 * Base [Projection] implementation
 * Uses [CachedProjection]
 *
 * @see Projection
 * @see ComposedProjection
 */
open class ProjectorProjection(val projection: Projector) : CachedProjection() {

    protected var _translateX = 480.0
    protected var _translateY = 250.0


    // Center
    protected var _recenterDx = 0.0
    protected var _recenterDy = 0.0

    // in radians
    protected var _centerLat = 0.0
    // in radians
    protected var _centerLon = 0.0

    protected var _scale = 150.0

    /**
     * TODO: rework to affine matrix transformations or at least separate Scale & Translate phase
     */
    protected lateinit var composedTransformationsProjector: Projector

    protected val translateAndScaleProjector = TranslateAndScaleProjector(projection, _scale, _recenterDx, _recenterDy)

    // Precision
    private var _precisionDelta2 = 0.5

    // Rotate
    protected var _rotationLambda = 0.0
    protected var _rotationPhi = 0.0
    protected var _rotationGamma = 0.0
    protected lateinit var rotator: Projector

    override var preClip: StreamPreClip = antimeridianPreClip
    override var postClip: StreamPostClip = noPostClip

    private var resampleProjector = precisionResample(translateAndScaleProjector, _precisionDelta2)

    override var scale: Double
        get() = _scale
        set(value) {
            _scale = value
            recenter()
        }


    // Translate
    override var translateX
        get () = _translateX
        set(value) {
            _translateX = value
            recenter()
        }
    override var translateY
        get () = _translateY
        set(value) {
            _translateY = value
            recenter()
        }


    override fun translate(x: Double, y: Double) {
        _translateX = x;
        _translateY = y;
        recenter()
    }


    override var centerLat
        get() = _centerLat.rad
        set(value) {
            _centerLat = value.rad
            recenter()
        }
    override var centerLon
        get() = _centerLon.rad
        set(value) {
            _centerLon = value.rad
            recenter()
        }


    override fun center(lat: Angle, lon: Angle) {
        _centerLat = lat.rad
        _centerLon = lon.rad
        recenter()
    }

    override var rotateLambda
        get() = _rotationLambda.rad
        set(value) {
            _rotationLambda = value.rad
            recenter()
        }

    override var rotatePhi
        get() = _rotationPhi.rad
        set(value) {
            _rotationPhi = value.rad
            recenter()
        }


    override var rotateGamma
        get() = _rotationGamma.rad
        set(value) {
            _rotationGamma = value.rad
            recenter()
        }


    override fun rotate(lambda: Angle, phi: Angle, gamma: Angle?) {
        _rotationLambda = lambda.rad
        _rotationPhi = phi.rad
        _rotationGamma = gamma?.rad ?: 0.0
        recenter()
    }


    override var precision: Double
        get() = sqrt(_precisionDelta2)
        set(value) {
            _precisionDelta2 = value * value
            resampleProjector = precisionResample(translateAndScaleProjector, _precisionDelta2)
            reset()
        }


    override fun fullCycleStream(stream: Stream): Stream {
        return transformRadians(transformRotate(rotator)(preClip.preClip(resampleProjector(postClip.postClip(stream)))))
    }


//    override fun project(lambda: Double, phi: Double): DoubleArray {
//        val lambdaRadians = lambda.toRadians()
//        val phiRadians = phi.toRadians()
//        return composedTransformationsProjector.project(lambdaRadians, phiRadians)
//    }

    // TODO why translateAndScaleProjector? Maybe composedTransformationsProjector?
    override fun projectLambda(lambda: Double, phi: Double): Double =
        composedTransformationsProjector.projectLambda(lambda.toRadians(), phi.toRadians())

    // TODO why translateAndScaleProjector? Maybe composedTransformationsProjector?
    override fun projectPhi(lambda: Double, phi: Double): Double =
        composedTransformationsProjector.projectPhi(lambda.toRadians(), phi.toRadians())


    override fun invertLambda(lambda: Double, phi: Double): Double {
        val newLambda = (lambda - _recenterDx) / _scale
        val newPhi = (_recenterDy - phi) / _scale
        return composedTransformationsProjector.invertLambda(
            newLambda,
            newPhi
        )
    }

    override fun invertPhi(lambda: Double, phi: Double): Double {
        val newLambda = (lambda - _recenterDx) / _scale
        val newPhi = (_recenterDy - phi) / _scale
        return composedTransformationsProjector.invertPhi(
            newLambda,
            newPhi
        )
    }

    override fun invert(lambda: Double, phi: Double): DoubleArray {
        val newLambda = (lambda - _recenterDx) / _scale
        val newPhi = (_recenterDy - phi) / _scale
        val inverted = composedTransformationsProjector.invert(
            newLambda,
            newPhi
        )
        return doubleArrayOf(
            inverted[0].toDegrees(),
            inverted[1].toDegrees()
        )
    }

    private fun recenter() {
        rotator = createRotateRadiansProjector(_rotationLambda, _rotationPhi, _rotationGamma)

        composedTransformationsProjector = ComposedProjector(rotator, translateAndScaleProjector)

        _recenterDx = translateX - (projection.projectLambda(_centerLat, _centerLon) * _scale)
        _recenterDy = translateY + (projection.projectPhi(_centerLat, _centerLon) * _scale)

        translateAndScaleProjector.scale = _scale
        translateAndScaleProjector.recenterDx = _recenterDx
        translateAndScaleProjector.recenterDy = _recenterDy
    }


}

/**
 * Scale & translate projector based on values from [projection]
 *
 * @see RotationProjector
 */
class TranslateAndScaleProjector(
    val projection: Projector,
    var scale: Double,
    var recenterDx: Double,
    var recenterDy: Double
) : Projector {
    override fun invert(lambda: Double, phi: Double): DoubleArray {
        return projection.invert(lambda, phi)
    }

    override fun invertLambda(lambda: Double, phi: Double): Double {
        return projection.invertLambda(lambda, phi)
    }

    override fun invertPhi(lambda: Double, phi: Double): Double {
        return projection.invertPhi(lambda, phi)
    }

    private fun internalProjectLambda(lambda: Double) =
        lambda * scale + recenterDx

    private fun internalProjectPhi(phi: Double) =
        recenterDy - phi * scale

    override fun projectLambda(lambda: Double, phi: Double): Double =
        internalProjectLambda(projection.projectLambda(lambda, phi))

    override fun projectPhi(lambda: Double, phi: Double): Double =
        internalProjectPhi(projection.projectPhi(lambda, phi))

}

