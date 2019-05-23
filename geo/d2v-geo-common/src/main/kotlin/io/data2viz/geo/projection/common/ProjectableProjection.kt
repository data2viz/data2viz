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

fun projection(projection: Projector, init: ProjectableProjection.() -> Unit) = ProjectableProjection(
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

open class ProjectableProjection(val projection: Projector) : CachedProjection() {


//    private val clipAntimeridian: (Stream) -> Stream = clipAntimeridian()
//    val noClip: (Stream) -> Stream = { it }

    override var preClip: StreamPreClip = antimeridianPreClip


    override var postClip: StreamPostClip = noPostClip


    // Scale
    protected var k = 150.0

    override var scale: Double
        get() = k
        set(value) {
            k = value
            recenter()
        }

    private var _x = 480.0
    private var _y = 250.0

    // Translate
    override var translateX
        get () = _x
        set(value) {
            _x = value
            recenter()
        }
    override var translateY
        get () = _y
        set(value) {
            _y = value
            recenter()
        }


    override fun translate(x: Double, y: Double) {
        _x = x;
        _y = y;
        recenter()
    }

    // Center
    protected var dx = 0.0
    protected var dy = 0.0
    protected var lambda = 0.0
    protected var phi = 0.0

    override var centerLat
        get() = lambda.rad
        set(value) {
            lambda = value.rad
            recenter()
        }
    override var centerLon
        get() = phi.rad
        set(value) {
            phi = value.rad
            recenter()
        }


    override fun center(lat: Angle, lon: Angle) {
        lambda = lat.rad
        phi = lon.rad
        recenter()
    }


    // Rotate
    protected var deltaLambda = 0.0
    protected var deltaPhi = 0.0
    protected var deltaGamma = 0.0
    protected lateinit var rotator: Projector


    override var rotateLambda
        get() = deltaLambda.rad
        set(value) {
            deltaLambda = value.rad
            recenter()
        }

    override var rotatePhi
        get() = deltaPhi.rad
        set(value) {
            deltaPhi = value.rad
            recenter()
        }


    override var rotateGamma
        get() = deltaGamma.rad
        set(value) {
            deltaGamma = value.rad
            recenter()
        }


    override fun rotate(lambda: Angle, phi: Angle, gamma: Angle?) {
        deltaLambda = lambda.rad
        deltaPhi = phi.rad
        deltaGamma = gamma?.rad ?: 0.0
        recenter()
    }


    protected lateinit var projectRotate: Projector

    protected val projectTransform: Projector = createProjectTransform()


    // Precision
    private var delta2 = 0.5
    private var projectResample = resample(projectTransform, delta2)
    override var precision: Double
        get() = sqrt(delta2)
        set(value) {
            delta2 = value * value
            projectResample = resample(projectTransform, delta2)
            reset()
        }


    override fun fullCycleStream(stream: Stream): Stream {
        return transformRadians(transformRotate(rotator)(preClip.preClip(projectResample(postClip.postClip(stream)))))
    }

    fun createProjectTransform(): Projector {
        return object : Projector {
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
                lambda * k + dx

            private fun internalProjectPhi(phi: Double) =
                dy - phi * k

            override fun projectLambda(lambda: Double, phi: Double): Double =
                internalProjectLambda(projection.projectLambda(lambda, phi))

            override fun projectPhi(lambda: Double, phi: Double): Double =
                internalProjectPhi(projection.projectPhi(lambda, phi))


        }
    }

    override fun project(lambda: Double, phi: Double): DoubleArray {
        val lambdaRadians = lambda.toRadians()
        val phiRadians = phi.toRadians()
        return projectTransform.project(lambdaRadians, phiRadians)
    }

    override fun projectLambda(lambda: Double, phi: Double): Double =
        projectTransform.projectLambda(lambda.toRadians(), phi.toRadians())

    override fun projectPhi(lambda: Double, phi: Double): Double =
        projectTransform.projectPhi(lambda.toRadians(), phi.toRadians())


    override fun invertLambda(lambda: Double, phi: Double): Double {
        val newLambda = (lambda - dx) / k
        val newPhi = (dy - phi) / k
        return projectRotate.invertLambda(
            newLambda,
            newPhi
        )
    }

    override fun invertPhi(lambda: Double, phi: Double): Double {
        val newLambda = (lambda - dx) / k
        val newPhi = (dy - phi) / k
        return projectRotate.invertPhi(
            newLambda,
            newPhi
        )
    }

    override fun invert(lambda: Double, phi: Double): DoubleArray {
        val newLambda = (lambda - dx) / k
        val newPhi = (dy - phi) / k
        val inverted = projectRotate.invert(
            newLambda,
            newPhi
        )
        return doubleArrayOf(
            inverted[0].toDegrees(),
            inverted[1].toDegrees()
        )
    }

    fun recenter() {
        rotator = rotateRadians(deltaLambda, deltaPhi, deltaGamma)

        projectRotate = ComposedProjector(rotator, projection)

        dx = translateX - (projection.projectLambda(lambda, phi) * k)
        dy = translateY + (projection.projectPhi(lambda, phi) * k)
    }

}