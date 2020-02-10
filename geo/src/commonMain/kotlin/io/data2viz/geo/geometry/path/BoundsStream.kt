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

package io.data2viz.geo.geometry.path

import io.data2viz.geo.Point3D
import io.data2viz.geom.Extent
import io.data2viz.geo.stream.Stream
import io.data2viz.geo.geojson.path.GeoBoundsStream

/**
 * Returns the bounding box for the specified GeoJSON object.
 * All coordinates are given in degrees.
 *
 * This is the cartesian equivalent of [GeoBoundsStream]
 */
internal class BoundsStream : Stream<Point3D>() {

    private var bounds =
        Extent(
            Double.POSITIVE_INFINITY,
            Double.POSITIVE_INFINITY,
            Double.NEGATIVE_INFINITY,
            Double.NEGATIVE_INFINITY
        )

    fun bounds(): Extent {
        val result = bounds.copy()
        bounds = Extent(
            Double.POSITIVE_INFINITY,
            Double.POSITIVE_INFINITY,
            Double.NEGATIVE_INFINITY,
            Double.NEGATIVE_INFINITY
        )
        return result
    }

    override fun point(point: Point3D) {
        if (point.x < bounds.x0) bounds.x0 = point.x
        if (point.x > bounds.x1) bounds.x1 = point.x
        if (point.y < bounds.y0) bounds.y0 = point.y
        if (point.y > bounds.y1) bounds.y1 = point.y
    }
}