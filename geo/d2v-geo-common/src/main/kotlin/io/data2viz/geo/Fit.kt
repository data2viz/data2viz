package io.data2viz.geo

import io.data2viz.geo.path.PathBounds
import io.data2viz.geo.projection.Extent
import io.data2viz.geo.projection.Projection
import io.data2viz.geojson.GeoJsonObject
import kotlin.math.min

fun fitExtent(projection: Projection, extent: Extent, geo: GeoJsonObject): Projection {
    val fitBounds = { size:Extent ->
        val w = extent.width
        val h = extent.height
        val k = min(w / (size.width), h / (size.height))
        val x = extent.x0 + (w - k * (size.x1 + size.x0)) / 2
        val y = extent.y0 + (h - k * (size.y1 + size.y0)) / 2
        projection.scale = k * 150
        projection.translate = doubleArrayOf(x, y)
    }
    return fit(projection, fitBounds, geo)
}

private fun fit(projection: Projection, fitBounds: (Extent) -> Unit, geo: GeoJsonObject): Projection {
    val clip = projection.clipExtent

    projection.scale = 150.0
    projection.translate = doubleArrayOf(.0, .0)
    if (clip != null) projection.clipExtent = null

    val boundsStream = PathBounds()
    stream(geo, projection.stream(boundsStream))
    fitBounds(boundsStream.result())
    if (clip != null) projection.clipExtent = clip

    return projection
}