/*
 * Copyright (c) 2018-2021. data2viz sàrl.
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

import io.data2viz.geo.projection.common.Projector
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin

/**
 * Simple cylindrical equal area
 * Used in [ConicEqualAreaBaseConditionalProjector]
 */
public class CylindricalEqualAreaProjector() : Projector {

    public constructor(phi:Double) : this() {
        phi0 = phi
    }

    public var phi0:Double = 0.0
    set(value) {
        field = value
        recalculate()
    }

    public var cosPhi0: Double = 0.0

    private fun recalculate() {
        cosPhi0 = cos(phi0)
    }

    override fun project(lambda: Double, phi: Double): DoubleArray = doubleArrayOf(lambda * cosPhi0, sin(phi) / cosPhi0)

    override fun invert(x: Double, y: Double): DoubleArray = doubleArrayOf( x / cosPhi0, asin(y * cosPhi0))

}
