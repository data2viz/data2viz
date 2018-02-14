package io.data2viz.geo

interface GeoJSON

interface Geometry<out T> : GeoJSON {
    val coordinates: T
}

// TODO check sphere as D3 also considers it a "geometry"
class Sphere() : GeoJSON

data class Feature(val geometry: Geometry<*>) : GeoJSON
data class FeatureCollection(val features: List<GeoJSON>) : GeoJSON
data class GeometryCollection(val geometries: List<Geometry<*>>) : GeoJSON
data class Point(override val coordinates: DoubleArray) : Geometry<DoubleArray>
data class MultiPoint(override val coordinates: List<DoubleArray>) : Geometry<List<DoubleArray>>
data class Polygon(override val coordinates: List<List<DoubleArray>>) : Geometry<List<List<DoubleArray>>>
data class MultiPolygon(override val coordinates: List<List<List<DoubleArray>>>) : Geometry<List<List<List<DoubleArray>>>>
data class LineString(override val coordinates: List<DoubleArray>) : Geometry<List<DoubleArray>>
data class MultiLineString(override val coordinates: List<List<DoubleArray>>) : Geometry<List<List<DoubleArray>>>
