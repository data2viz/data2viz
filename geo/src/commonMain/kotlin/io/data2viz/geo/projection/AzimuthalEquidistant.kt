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

import io.data2viz.geo.geometry.limitedAcos
import io.data2viz.geo.geometry.clip.anglePreClip
import io.data2viz.geo.projection.common.ProjectorProjection
import io.data2viz.geo.projection.common.projection
import io.data2viz.math.deg
import kotlin.math.sin


fun azimuthalEquidistant(init: ProjectorProjection.() -> Unit = {}) =
    projection(AzimuthalEquidistantProjection()) {
        scale = 79.4188
        anglePreClip = (180 - 1e-3).deg
        init()
    }

private val scale = { cxcy: Double ->
    val c = cxcy.limitedAcos
    if (c != .0) c / sin(c) else c
}
private val angle: (Double) -> Double = { z -> z }

internal class AzimuthalEquidistantProjection : Azimuthal(scale, angle)
