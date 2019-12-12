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

/**
 * Scale & translate projector based on values from [projector]
 *
 */
class TranslateAndScaleProjector(
    val projector: Projector,
    var scale: Double,
    var recenterDx: Double,
    var recenterDy: Double
) : Projector {

    override fun project(lambda: Double, phi: Double): DoubleArray {
        val projected = projector.project(lambda, phi)
        projected[0] = recenterDx + projected[0] * scale
        projected[1] = recenterDy - projected[1] * scale
        return projected
    }


    override fun invert(x: Double, y: Double): DoubleArray {

        return projector.invert(
            (x - recenterDx) / scale,
            -(y - recenterDy) / scale
        )
    }

}