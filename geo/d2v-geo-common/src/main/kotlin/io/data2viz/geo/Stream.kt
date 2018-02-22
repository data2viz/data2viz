package io.data2viz.geo

import io.data2viz.geo.projection.Stream
import io.data2viz.geojson.*

internal val noop: () -> Unit = { }
internal val noop3: (Double, Double, Double) -> Unit = { x, y, z -> }

fun stream(geo: GeoJsonObject, stream: Stream) {
    when (geo) {
        is FeatureCollection -> geo.features.forEach { stream(it, stream) }
        is Feature -> stream(geo.geometry, stream)
        is GeometryCollection -> geo.geometries.forEach { streamGeometry(it, stream) }
        is Geometry -> streamGeometry(geo, stream)              // keep it last !
    }
}

fun streamGeometry(geo: GeoJsonObject, stream: Stream) {
    when (geo) {
        is Point -> streamPoint(geo.coordinates, stream)
        is LineString -> streamLine(geo.coordinates, stream, false)
        is MultiPoint -> geo.coordinates.forEach { streamPoint(it, stream) }
        is MultiPolygon -> geo.coordinates.forEach { streamPolygon(it, stream) }
        is Polygon -> streamPolygon(geo.coordinates, stream)
        is MultiLineString -> geo.coordinates.forEach { streamLine(it, stream, false) }
        is Sphere -> streamSphere(stream)
    }
}

private fun streamSphere(stream: Stream) {
    stream.sphere()
}

private fun streamPoint(coordinates: Position, stream: Stream) {
    var z = coordinates.alt
    if (z == null) z = .0
    stream.point(coordinates.lon, coordinates.lat, z)
}

private fun streamPolygon(coords: Lines, stream: Stream) {
    stream.polygonStart()
    coords.forEach { streamLine(it, stream, true) }
    stream.polygonEnd()
}

private fun streamLine(coords: Positions, stream: Stream, closed: Boolean) {
    val size = if (closed) coords.size - 1 else coords.size

    stream.lineStart()
    for (i in 0 until size) {
        val p = coords[i]
        stream.point(p[0], p[1], if (p.size > 2) p[2] else .0)
    }
    stream.lineEnd()
}

open class ModifiedStream(val stream: Stream) : Stream {
    override fun point(x: Double, y: Double, z: Double) = stream.point(x, y, z)
    override fun lineStart() = stream.lineStart()
    override fun lineEnd() = stream.lineEnd()
    override fun polygonStart() = stream.polygonStart()
    override fun polygonEnd() = stream.polygonEnd()
    override fun sphere() = stream.sphere()
}