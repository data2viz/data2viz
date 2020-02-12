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

package io.data2viz.geo.projection.common

import io.data2viz.geo.GeoJsonPoint
import io.data2viz.geo.Point3D

/**
 * Scale & translate projector based on values from [projector]
 *
 */
class TranslateAndScaleProjector(
    val projector: Projector<GeoJsonPoint, Point3D>,
    var scale: Double,
    var recenterDx: Double,
    var recenterDy: Double
) : Projector<GeoJsonPoint, Point3D> {

    override fun project(point: GeoJsonPoint): Point3D {
        val projected = projector.project(point)
        return projected.copy(
            x = recenterDx + projected.x * scale,
            y= recenterDy - projected.y * scale
        )
    }


    override fun invert(point: Point3D): GeoJsonPoint =
        projector.invert(
            point.copy(
                x = (point.x - recenterDx) / scale,
                y = -(point.y - recenterDy) / scale
            )
        )

}