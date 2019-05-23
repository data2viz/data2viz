package io.data2viz.geo.projection.common

import io.data2viz.geo.geometry.clip.StreamPostClip
import io.data2viz.geo.geometry.clip.StreamPreClip
import io.data2viz.geo.stream.MultiplexStream
import io.data2viz.geo.stream.Stream
import io.data2viz.math.Angle

/**
 * Composite consist of several projections that are composed into a single display. The constituent projections have fixed clip, center and rotation, and thus composite projections do not support projection.center, projection.rotate, projection.clipAngle, or projection.clipExtent.
 */
abstract class ComposedProjection() : CachedProjection() {

    abstract val mainProjection: Projection
    abstract val allProjections: Collection<Projection>

    override var centerLat: Angle
        get() = mainProjection.centerLat
        set(value) = allProjections.forEach { it.centerLat = value }
    override var centerLon: Angle
        get() = mainProjection.centerLon
        set(value) = allProjections.forEach { it.centerLon = value }
    override var rotateLambda: Angle
        get() = mainProjection.rotateLambda
        set(value) = allProjections.forEach { it.rotateLambda = value }
    override var rotatePhi: Angle
        get() = mainProjection.rotatePhi
        set(value) = allProjections.forEach { it.rotatePhi = value }
    override var rotateGamma: Angle
        get() = mainProjection.rotateGamma
        set(value) = allProjections.forEach { it.rotateGamma = value }


    override var preClip: StreamPreClip
        get() = mainProjection.preClip
        set(value) = allProjections.forEach { it.preClip = value }
    override var postClip: StreamPostClip
        get() = mainProjection.postClip
        set(value) = allProjections.forEach { it.postClip = value }


    override var precision: Double
        get() = mainProjection.precision
        set(value) {
            allProjections.forEach { it.precision = value }
            reset()
        }


    override var translateX: Double
        get() = mainProjection.translateX
        set(value) {
            allProjections.forEach { it.translateX = value }
            reset()
        }

    override var translateY: Double
        get() = mainProjection.translateY
        set(value) {
            allProjections.forEach { it.translateY = value }
            reset()
        }

    override var scale: Double
        get() = mainProjection.scale
        set(value) {
            allProjections.forEach { it.scale = value }
            reset()
        }

    override fun translate(x: Double, y: Double) {
        allProjections.forEach { it.translate(x, y) }
    }

    override fun center(lat: Angle, lon: Angle) {
        allProjections.forEach { it.center(lat, lon) }
    }

    override fun rotate(lambda: Angle, phi: Angle, gamma: Angle?) {
        allProjections.forEach { it.rotate(lambda, phi, gamma) }
    }


    override fun projectLambda(lambda: Double, phi: Double): Double =
        chooseNestedProjection(lambda, phi).projectLambda(lambda, phi)


    override fun projectPhi(lambda: Double, phi: Double): Double =
        chooseNestedProjection(lambda, phi).projectPhi(lambda, phi)

    override fun invert(lambda: Double, phi: Double): DoubleArray =
        chooseNestedProjection(lambda, phi).invert(lambda, phi)

    abstract fun chooseNestedProjection(lambda: Double, phi: Double): Projection


    override fun fullCycleStream(stream: Stream): Stream =
        MultiplexStream(allProjections.map { it.stream(stream) })

}