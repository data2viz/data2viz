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

import io.data2viz.geo.GeoPoint
import io.data2viz.geo.Point3D
import io.data2viz.geo.projection.*
/**
 * Abstract Projector which delegate projector & invert to another projection depending current settings
 * @see BaseConditionalProjector
 */
abstract class ConditionalProjector : Projector<GeoPoint, Point3D> {

    override fun invert(point: Point3D): GeoPoint = activeProjector.invert(point)

    override fun project(point: GeoPoint): Point3D = activeProjector.project(point)

    abstract val activeProjector: Projector<GeoPoint, Point3D>
}

/**
 * ConditionalProjector implementation for two projectors
 *
 * @see ConditionalProjector
 * @see ConicEqualAreaBaseConditionalProjector
 * @see ConicConformalBaseConditionalProjector
 * @see ConicEquidistantBaseConditionalProjector
 */
abstract class BaseConditionalProjector : ConditionalProjector() {

    abstract val baseProjector: Projector<GeoPoint, Point3D>
    abstract val nestedProjector: Projector<GeoPoint, Point3D>
    abstract val isNeedUseBaseProjector: Boolean


    override val activeProjector: Projector<GeoPoint, Point3D>
        get() = if (isNeedUseBaseProjector) {
            baseProjector
        } else {
            nestedProjector
        }
}