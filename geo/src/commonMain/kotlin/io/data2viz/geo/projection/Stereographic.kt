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

import io.data2viz.geo.geometry.clip.anglePreClip
import io.data2viz.geo.projection.common.Projection
import io.data2viz.geo.projection.common.Projector
import io.data2viz.geo.projection.common.projection
import io.data2viz.math.deg
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin


/**
 * @see StereographicProjector
 */
public fun stereographicProjection(init: Projection.() -> Unit = {}): Projection =
    projection(StereographicProjector()) {
        scale = 250.0
        anglePreClip = (142.0).deg
        init()
    }


private fun doubleAtan(d: Double) = 2 * atan(d)

/**
 * The stereographic projection.
 */
public class StereographicProjector : Projector {

    override fun project(lambda: Double, phi: Double): DoubleArray {
        val cosPhi = cos(phi)
        val k = 1 + cos(lambda) * cosPhi
        return doubleArrayOf(
            cosPhi * sin(lambda) / k,
            sin(phi) / k
        )
    }

    override fun invert(x: Double, y: Double): DoubleArray
            = azimuthalInvert(::doubleAtan)(x, y)


}