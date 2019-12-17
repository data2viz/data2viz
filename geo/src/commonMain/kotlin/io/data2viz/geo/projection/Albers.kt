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

import io.data2viz.math.deg


/**
 * The Albers’ equal area-conic projection.
 * This is a U.S.-centric configuration of [ConicEqualAreaProjector]
 */
fun albersProjection(init: ConicProjection.() -> Unit = {}) = conicEqualAreaProjection {
    parallels(29.5.deg, 45.5.deg)
    scale = 1070.0
    translate(480.0, 250.0)
    rotate(96.0.deg, 0.0.deg)
    center((-0.6).deg, 38.7.deg)
    init()
}
