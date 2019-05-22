package io.data2viz.geo.geojson

import io.data2viz.geo.projection.common.Projection
import io.data2viz.geom.Extent
import io.data2viz.geo.geometry.path.BoundsStream
import io.data2viz.geojson.GeoJsonObject
import kotlin.math.min

fun fitSize(projection: Projection, width: Double, height:Double, geo: GeoJsonObject): Projection {
    return fitExtent(projection, Extent(.0, .0, width, height), geo)
}

fun fitHeight(projection: Projection, height:Double, geo: GeoJsonObject): Projection {
    val fitBounds = { size: Extent ->
        val k = height / size.height
        val x = -k * size.x0
        val y = (height - (k * (size.y1 + size.y0))) / 2
        projection.scale = k * 150
        projection.translate(x, y)
    }
    return fit(projection, fitBounds, geo)
}

fun fitWidth(projection: Projection, width:Double, geo: GeoJsonObject): Projection {
    val fitBounds = { size: Extent ->
        val k = width / size.width
        val x = (width - (k * (size.x1 + size.x0))) / 2
        val y = -k * size.y0
        projection.scale = k * 150
        projection.translate(x, y)
    }
    return fit(projection, fitBounds, geo)
}

fun fitExtent(projection: Projection, extent: Extent, geo: GeoJsonObject): Projection {
    val fitBounds = { size: Extent ->
        val w = extent.width
        val h = extent.height
        val k = min(w / (size.width), h / (size.height))
        val x = extent.x0 + (w - (k * (size.x1 + size.x0))) / 2
        val y = extent.y0 + (h - (k * (size.y1 + size.y0))) / 2
        projection.scale = k * 150
        projection.translate(x, y)
    }
    return fit(projection, fitBounds, geo)
}

private fun fit(projection: Projection, fitBounds: (Extent) -> Unit, geo: GeoJsonObject): Projection {
    val clip = projection.clipExtent

    projection.scale = 150.0
    projection.translate(.0, .0)
    if (clip != null) projection.clipExtent = null

    val boundsStream = BoundsStream()
    geo.stream(projection.stream(boundsStream))
    fitBounds(boundsStream.result())
    if (clip != null) projection.clipExtent = clip

    return projection
}