package io.data2viz.geo.geojson

import io.data2viz.geo.geometry.clip.extentPostClip
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
    val clip = projection.extentPostClip

    projection.scale = 150.0
    projection.translate(.0, .0)
    if (clip != null) projection.extentPostClip = null

    val boundsStream = BoundsStream()
    geo.stream(projection.stream(boundsStream))
    fitBounds(boundsStream.result())
    if (clip != null) projection.extentPostClip = clip

    return projection
}


/**
 *
 * TODO: check
 * Sets the projection’s scale and translate to fit the specified GeoJSON object in the center of the given extent. The extent is specified as an array [[translateX₀, translateY₀], [translateX₁, translateY₁]], where translateX₀ is the left side of the bounding box, translateY₀ is the top, translateX₁ is the right and translateY₁ is the bottom. Returns the projection.

For example, to scale and translate the New Jersey State Plane projection to fit a GeoJSON object nj in the center of a 960×500 bounding box with 20 pixels of padding on each side:
var projection = d3.geoTransverseMercator()
.rotate([74 + 30 / 60, -38 - 50 / 60])
.fitExtent([[20, 20], [940, 480]], nj);
Any postClip extent is ignored when determining the new scale and translate. The precision used to compute the bounding box of the given object is computed at an effective scale of 150.
 */
fun Projection.fitExtent(extent: Extent, geo: GeoJsonObject): Projection {
    return io.data2viz.geo.geojson.fitExtent(this, extent, geo)
}


/**
 * TODO: check
 * A convenience method for projection.fitSize where the height is automatically chosen from the aspect ratio of object and the given constraint on width.
 *
 */
fun Projection.fitWidth(width: Double, geo: GeoJsonObject): Projection {
    return io.data2viz.geo.geojson.fitWidth(this, width, geo)
}

/**
 * * TODO: check
 * A convenience method for projection.fitSize where the width is automatically chosen from the aspect ratio of object and the given contraint on height.
 */
fun Projection.fitHeight(height: Double, geo: GeoJsonObject): Projection {
    return io.data2viz.geo.geojson.fitHeight(this, height, geo)
}

/**
 *
 * TODO: check
 * A convenience method for projection.fitExtent where the top-left corner of the extent is [0, 0]. The following two statements are equivalent:

projection.fitExtent([[0, 0], [width, height]], object);
projection.fitSize([width, height], object);
 */
fun Projection.fitSize(width: Double, height: Double, geo: GeoJsonObject): Projection {
    return io.data2viz.geo.geojson.fitSize(this, width, height, geo)
}

