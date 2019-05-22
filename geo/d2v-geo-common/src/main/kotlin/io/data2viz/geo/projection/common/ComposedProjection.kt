package io.data2viz.geo.projection.common

import io.data2viz.geo.stream.MultiplexStream
import io.data2viz.geo.stream.Stream
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.geom.Extent
import io.data2viz.math.Angle

abstract class ComposedProjection() : CachedProjection() {

    abstract val mainProjection: Projection
    abstract val allProjections: Collection<Projection>


    override var center: Array<Angle>
        get() = mainProjection.center
        set(value) = allProjections.forEach { it.center = value }
    override var rotate: Array<Angle>
        get() = mainProjection.rotate
        set(value) = allProjections.forEach { it.rotate = value }
    override var preClip: (Stream) -> Stream
        get() = mainProjection.preClip
        set(value) = allProjections.forEach { it.preClip = value }
    override var postClip: (Stream) -> Stream
        get() = mainProjection.postClip
        set(value) = allProjections.forEach { it.postClip = value }
    override var clipAngle: Double
        get() = mainProjection.clipAngle
        set(value) = allProjections.forEach { it.clipAngle = value }
    override var clipExtent: Extent?
        get() = mainProjection.clipExtent
        set(value) = allProjections.forEach { it.clipExtent = value }


    override var precision: Double
        get() = mainProjection.precision
        set(value) {
            allProjections.forEach { it.precision = value }
            reset()
        }


    override var x: Double
        get() = mainProjection.x
        set(value) {
            allProjections.forEach { it.x = value }
            reset()
        }

    override var y: Double
        get() = mainProjection.y
        set(value) {
            allProjections.forEach { it.y = value }
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


    override fun projectLambda(lambda: Double, phi: Double): Double =
        chooseNestedProjection(lambda, phi).projectLambda(lambda, phi)


    override fun projectPhi(lambda: Double, phi: Double): Double =
        chooseNestedProjection(lambda, phi).projectPhi(lambda, phi)

    override fun invert(lambda: Double, phi: Double): DoubleArray =
        chooseNestedProjection(lambda, phi).invert(lambda, phi)

    abstract fun chooseNestedProjection(lambda: Double, phi: Double): Projection


    override fun fullCycleStream(stream: Stream): Stream =
        MultiplexStream(allProjections.map { it.stream(stream) })


    override fun fitExtent(extent: Extent, geo: GeoJsonObject): Projection =
        io.data2viz.geo.geojson.fitExtent(mainProjection, extent, geo)


    override fun fitWidth(width: Double, geo: GeoJsonObject): Projection =
        io.data2viz.geo.geojson.fitWidth(mainProjection, width, geo)


    override fun fitHeight(height: Double, geo: GeoJsonObject): Projection =
        io.data2viz.geo.geojson.fitHeight(mainProjection, height, geo)


    override fun fitSize(width: Double, height: Double, geo: GeoJsonObject): Projection =
        io.data2viz.geo.geojson.fitSize(mainProjection, width, height, geo)

}