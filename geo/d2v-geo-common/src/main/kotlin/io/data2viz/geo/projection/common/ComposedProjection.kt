package io.data2viz.geo.projection.common

import io.data2viz.geo.geometry.clip.StreamPostClip
import io.data2viz.geo.geometry.clip.StreamPreClip
import io.data2viz.geo.stream.MultiplexStream
import io.data2viz.geo.stream.Stream
import io.data2viz.math.Angle
import io.data2viz.geo.projection.AlbersUSAProjection

/**
 * Composite consist of several projections that are composed into a single display.
 * For base projection see [ProjectorProjection]
 * @see AlbersUSAProjection
 */
abstract class ComposedProjection : Projection {

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
        }

    override var translateX: Double
        get() = mainProjection.translateX
        set(value) {
            allProjections.forEach { it.translateX = value }
        }

    override var translateY: Double
        get() = mainProjection.translateY
        set(value) {
            allProjections.forEach { it.translateY = value }
        }

    override var scale: Double
        get() = mainProjection.scale
        set(value) {
            allProjections.forEach { it.scale = value }
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

    override fun project(lambda: Double, phi: Double): DoubleArray =
        chooseNestedProjection(lambda, phi).project(lambda, phi)

    override fun invert(x: Double, y: Double): DoubleArray =
        chooseNestedProjection(x, y).invert(x, y)

    /**
     * Chooses projection by (lambda, phi) coordinates
     */
    protected abstract fun chooseNestedProjection(lambda: Double, phi: Double): Projection


    override fun stream(stream: Stream): Stream =
        MultiplexStream(allProjections.map { it.stream(stream) })

}