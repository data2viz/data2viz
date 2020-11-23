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

package io.data2viz.geo.projection

import io.data2viz.geo.geometry.clip.NoClip
import io.data2viz.geo.projection.common.*
import io.data2viz.math.PI


/**
 * Projections without any transformations and clipping
 */
public fun identityProjection(init: Projection.() -> Unit = {}): Projection =
    projection(IdentityProjection()) {
        preClip = NoClip
        postClip = NoClip
        scale = 180 / PI
        init()
    }

internal class IdentityProjection : Projector {
    override fun invert(x: Double, y: Double): DoubleArray = doubleArrayOf(x, y)
    override fun project(lambda: Double, phi: Double): DoubleArray = doubleArrayOf(lambda, phi)
}