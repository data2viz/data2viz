package io.data2viz.geo.path

import io.data2viz.geo.GeoJSON
import io.data2viz.geo.projection.Extent
import io.data2viz.geo.projection.Projection
import io.data2viz.geo.stream
import io.data2viz.path.PathAdapter

fun geoPath(projection: Projection, context: PathAdapter) = GeoPath(projection, context)

class GeoPath(val projection: Projection, val context: PathAdapter) {

    private val pathArea = Area()
    private val pathBounds = Bounds()
    private val pathCentroid = Centroid()
    private val pathMeasure = Measure()
    private val contextStream: PathContext = PathContext(context)

    fun path(geo: GeoJSON): PathAdapter {
        stream(geo, projection.stream(contextStream))
        return context
    }

    fun centroid(geo: GeoJSON): DoubleArray {
        stream(geo, projection.stream(pathCentroid))
        return pathCentroid.result()
    }

    fun area(geo: GeoJSON): Double {
        stream(geo, projection.stream(pathArea))
        return pathArea.result()
    }

    fun bounds(geo: GeoJSON): Extent {
        stream(geo, projection.stream(pathBounds))
        return pathBounds.result()
    }

    fun measure(geo: GeoJSON): Double {
        stream(geo, projection.stream(pathMeasure))
        return pathMeasure.result()
    }
}