package io.data2viz.geo



import io.data2viz.geo.stream.Sphere
import io.data2viz.geojson.*

internal val noop: () -> Unit = { }
internal val noop2: (Double, Double) -> Unit = { _, _ -> }
internal val noop3: (Double, Double, Double) -> Unit = { _, _, _ -> }

fun GeoJsonObject.stream(stream: Stream) {
    when (this) {
        is FeatureCollection    -> features.forEach { it.stream(stream) }
        is Feature              -> geometry.stream(stream)
        is GeometryCollection   -> geometries.forEach { streamGeometry(it, stream) }
        is Geometry             -> streamGeometry(this, stream)              // keep it last !
    }
}

private fun streamGeometry(geo: GeoJsonObject, stream: Stream) {
    when (geo) {
        is Point            -> streamPoint(geo.coordinates, stream)
        is LineString       -> streamLine(geo.coordinates, stream, false)
        is MultiPoint       -> geo.coordinates.forEach { streamPoint(it, stream) }
        is MultiPolygon     -> geo.coordinates.forEach { streamPolygon(it, stream) }
        is Polygon          -> streamPolygon(geo.coordinates, stream)
        is MultiLineString  -> geo.coordinates.forEach { streamLine(it, stream, false) }
        is Sphere -> streamSphere(stream)
    }
}

private fun streamSphere(stream: Stream) {
    stream.sphere()
}

private fun streamPoint(coordinates: Position, stream: Stream) {
    val z = coordinates.alt ?: .0
    stream.point(coordinates.lon, coordinates.lat, z)
}

private fun streamPolygon(coords: Lines, stream: Stream) {
    stream.polygonStart()
    coords.forEach {
        streamLine(it, stream, true)
    }
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

