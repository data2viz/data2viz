package io.data2viz.geo

import io.data2viz.geojson.*
import io.data2viz.math.EPSILON
import io.data2viz.math.toRadians

fun contains(geo: GeoJsonObject, point: Position): Boolean {
    // TODO add containsObjectType for features...
    return containsGeometry(geo, point)
}

private fun containsGeometry(geo: GeoJsonObject, point: Position): Boolean = when (geo) {
    is Point                -> containsPoint(geo.coordinates, point)
    is MultiPoint           -> geo.coordinates.any { containsPoint(it, point)}
    is Polygon              -> containsPolygon(geo.coordinates, point)
    is MultiPolygon         -> geo.coordinates.any { containsPolygon(it, point)}
    is LineString           -> containsLine(geo.coordinates, point)
    is MultiLineString      -> geo.coordinates.any { containsLine(it, point)}
    is Sphere               -> true
    is GeometryCollection   -> geo.geometries.any { containsGeometry(it, point)}
    is FeatureCollection    -> geo.features.any { containsGeometry(it, point) }
    is Feature              -> containsGeometry(geo.geometry, point)
    else                    -> false
}

private fun containsPolygon(coordinates: Lines, point: Position): Boolean {
    val coords = coordinates.map { it.map { toRadians(it) } }.toMutableList()
    coords.removeAt(coords.lastIndex)
    return polygonContains(coords, toRadians(point))
}

fun toRadians(array: Position): DoubleArray {
    return array.map { it.toRadians() }.toDoubleArray()
}

private fun containsLine(coordinates: Positions, point: Position): Boolean {
    val ab = geoDistance(coordinates[0], coordinates[1])
    val ao = geoDistance(coordinates[0], point)
    val ob = geoDistance(point, coordinates[1])
    return ao + ob <= ab + EPSILON
}

private fun containsPoint(coordinates: Position, point: Position): Boolean {
    return geoDistance(coordinates, point) == .0
}