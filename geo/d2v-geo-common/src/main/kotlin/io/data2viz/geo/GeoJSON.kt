package io.data2viz.geo

interface GeoJSON

/*interface Geometry<out T> : GeoJSON {
    val coordinates: T
}*/

data class Point(val coordinates: DoubleArray) : GeoJSON
data class MultiPoint(val coordinates: List<DoubleArray>) : GeoJSON
data class Polygon(val coordinates: List<List<DoubleArray>>) : GeoJSON
data class MultiPolygon(val coordinates: List<List<List<DoubleArray>>>) : GeoJSON
data class LineString(val coordinates: List<DoubleArray>) : GeoJSON
data class MultiLineString(val coordinates: List<List<DoubleArray>>) : GeoJSON
data class Sphere(val coordinates: DoubleArray) : GeoJSON