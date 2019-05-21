package io.data2viz.geo.geo

import io.data2viz.geo.polygonContains
import io.data2viz.geojson.*
import io.data2viz.math.EPSILON
import io.data2viz.math.toRadians



class Sphere : Geometry


/**
 * Returns true if the GeoJsonObject contains the position
 * TODO add containsObjectType for features...
 */
fun GeoJsonObject.contains(point: Position): Boolean =
    when (this) {
        is Point                -> pos.contains(point)
        is MultiPoint           -> positions.any { it.contains(point) }
        is Polygon              -> lines.contains(point)
        is MultiPolygon         -> surface.any { it.contains(point) }
        is LineString           -> positions.contains(point)
        is MultiLineString      -> lines.any { it.contains(point) }
        is Sphere -> true
        is GeometryCollection   -> geometries.any { it.contains(point) }
        is FeatureCollection    -> features.any { it.contains(point) }
        is Feature              -> geometry.contains(point)
        else                    -> false
    }


private fun Lines.contains(point: Position): Boolean {
//    val coords = polygons.map { it.map { toRadians(it) } }.toMutableList()
//    coords.removeAt(coords.lastIndex)

    val radiansCoordinates = map { it.map { toRadians(it) } }
    val coords = radiansCoordinates.toMutableList()
    coords.removeAt(coords.lastIndex)
    return polygonContains(coords, toRadians(point))
}

private fun toRadians(pos: Position): DoubleArray = doubleArrayOf(pos.lon.toRadians(), pos.lat.toRadians())

private fun Positions.contains(point: Position): Boolean {
    val ab = geoDistance(this[0], this[1])
    val ao = geoDistance(this[0], point)
    val ob = geoDistance(point, this[1])
    return ao + ob <= ab + EPSILON
}

private fun Position.contains(point: Position): Boolean = geoDistance(this, point) == .0


//Cleaner API for GeoJson. Todo: improve GeoJson API

val io.data2viz.geojson.Point.pos: Position
    get() = coordinates

val io.data2viz.geojson.MultiPoint.positions: Positions
    get() = coordinates

val io.data2viz.geojson.LineString.positions: Positions
    get() = coordinates

val io.data2viz.geojson.Polygon.lines: Lines
    get() = coordinates

val io.data2viz.geojson.MultiLineString.lines: Lines
    get() = coordinates


val io.data2viz.geojson.MultiPolygon.surface: Surface
    get() = coordinates


