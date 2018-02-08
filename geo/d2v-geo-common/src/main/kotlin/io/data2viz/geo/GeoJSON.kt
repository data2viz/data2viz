package io.data2viz.geo

interface GeoJSON

/*interface Geometry<out T> : GeoJSON {
    val coordinates: T
}*/

data class Point(val coordinates: DoubleArray) : GeoJSON
data class Sphere(val coordinates: DoubleArray) : GeoJSON