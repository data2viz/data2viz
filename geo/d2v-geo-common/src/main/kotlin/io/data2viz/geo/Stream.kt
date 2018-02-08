package io.data2viz.geo

import io.data2viz.geo.projection.Stream

fun stream(geo: GeoJSON, stream: Stream) {
    // TODO stream different GEOJSON
    streamGeometry(geo as Point, stream)
}

fun streamGeometry(g: Point, stream: Stream) {
    // TODO stream different elements
    streamPoint(g as Point, stream)
}

fun streamPoint(point: Point, stream: Stream) {
    val p = point.coordinates
    stream.point(p[0], p[1], p[2])
}

open class ModifiedStream(val stream: Stream) : Stream {
    override fun point(x: Double, y: Double, z: Double) = stream.point(x, y, z)
    override fun lineStart() = stream.lineStart()
    override fun lineEnd() = stream.lineEnd()
    override fun polygonStart() = stream.polygonStart()
    override fun polygonEnd() = stream.polygonEnd()
    override fun sphere() = stream.sphere()
}