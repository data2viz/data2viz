/*
 * Copyright (c) 2018-2019. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.geo.geojson

import io.data2viz.geo.geometry.clip.extentPostClip
import io.data2viz.geo.geometry.path.BoundsStream
import io.data2viz.geo.projection.common.Projection
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.geom.Extent
import kotlin.math.min

private const val defaultScale = 150.0

private fun fit(projection: Projection, fitBounds: (Extent) -> Unit, geo: GeoJsonObject): Projection {
    val clip = projection.extentPostClip

    projection.scale = defaultScale
    projection.translate(.0, .0)
    if (clip != null) projection.extentPostClip = null

    val boundsStream = BoundsStream()
    geo.stream(projection.bindTo(boundsStream))
    fitBounds(boundsStream.result())
    if (clip != null) projection.extentPostClip = clip

    return projection
}


/**
 * Sets the projection’s scale and translate to fit the specified GeoJSON object in the center of the given extent.
 * The extent is [[translateX₀, translateY₀], [translateX₁, translateY₁]],
 * where translateX₀ is the left side of the bounding box,
 * translateY₀ is the top, translateX₁ is the right and translateY₁ is the bottom.
 * Any postClip extent is ignored when determining the new scale and translate.
 * The precision used to compute the bounding box of the given object is computed at an effective scale of 150.
 */
fun Projection.fitExtent(extent: Extent, geo: GeoJsonObject): Projection {
    val fitBounds = { size: Extent ->
        val w = extent.width
        val h = extent.height
        val k = min(w / (size.width), h / (size.height))
        val x = extent.x0 + (w - (k * (size.x1 + size.x0))) / 2
        val y = extent.y0 + (h - (k * (size.y1 + size.y0))) / 2
        this.scale = k * defaultScale
        this.translate(x, y)
    }
    return fit(this, fitBounds, geo)
}


/**
 * A convenience method for projection.fitSize where the height is automatically chosen
 * from the aspect ratio of object and the given constraint on width.
 */
fun Projection.fitWidth(width: Double, geo: GeoJsonObject): Projection {
    val fitBounds = { size: Extent ->
        val k = width / size.width
        val x = (width - (k * (size.x1 + size.x0))) / 2
        val y = -k * size.y0
        this.scale = k * defaultScale
        this.translate(x, y)
    }
    return fit(this, fitBounds, geo)
}

/**
 * A convenience method for projection.fitSize where the width is automatically chosen
 * from the aspect ratio of object and the given contraint on height.
 */
fun Projection.fitHeight(height: Double, geo: GeoJsonObject): Projection {
    val fitBounds = { size: Extent ->
        val k = height / size.height
        val x = -k * size.x0
        val y = (height - (k * (size.y1 + size.y0))) / 2
        this.scale = k * defaultScale
        this.translate(x, y)
    }
    return fit(this, fitBounds, geo)
}

/**
 *
 * A convenience method for projection.fitExtent where the top-left corner of the extent is [0, 0].
 * The following two statements are equivalent:
 * fitExtent([[0, 0], [width, height]], object)
 * fitSize([width, height], object);
 */
fun Projection.fitSize(width: Double, height: Double, geo: GeoJsonObject): Projection {
    return this.fitExtent(Extent(.0, .0, width, height), geo)
}

