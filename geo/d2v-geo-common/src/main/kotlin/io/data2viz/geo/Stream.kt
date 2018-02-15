package io.data2viz.geo

import io.data2viz.geo.projection.Stream

fun stream(geo: GeoJSON, stream: Stream) {
    when (geo) {
        is Geometry<*> -> streamGeometry(geo, stream)
        is GeometryCollection -> geo.geometries.forEach { streamGeometry(it, stream) }
        is Feature -> stream(geo.geometry, stream)
        is FeatureCollection -> geo.features.forEach { stream(it, stream) }
        is Sphere -> streamSphere(stream)
    }
}

fun streamGeometry(geo: GeoJSON, stream: Stream) {
    if (geo is Point) streamPoint(geo.coordinates, stream)
    if (geo is MultiPoint) geo.coordinates.forEach { streamPoint(it, stream) }
    if (geo is MultiPolygon) geo.coordinates.forEach { streamPolygon(it, stream) }
    if (geo is Polygon) streamPolygon(geo.coordinates, stream)
    if (geo is LineString) streamLine(geo.coordinates, stream, false)
    if (geo is MultiLineString) geo.coordinates.forEach { streamLine(it, stream, false) }
}

private fun streamSphere(stream: Stream) {
    stream.sphere()
}

private fun streamPoint(coordinates: DoubleArray, stream: Stream) {
    stream.point(coordinates[0], coordinates[1], if (coordinates.size > 2) coordinates[2] else .0)
}

private fun streamPolygon(coords: List<List<DoubleArray>>, stream: Stream) {
    stream.polygonStart()
    coords.forEach { streamLine(it, stream, true) }
    stream.polygonEnd()
}

private fun streamLine(coords: List<DoubleArray>, stream: Stream, closed: Boolean) {
    val n = if (closed) coords.size - 1 else coords.size

    stream.lineStart()
    for (i in 0 until n) {
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