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

import io.data2viz.geo.geometry.asin
import io.data2viz.geo.geometry.limitedAsin
import io.data2viz.geo.projection.common.Projector
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

public fun azimuthalInvert(angle: (Double) -> Double): (Double, Double) -> DoubleArray = { x: Double, y: Double ->
    val z = sqrt(x * x + y * y)
    val c = angle(z)
    val sc = sin(c)
    doubleArrayOf(atan2(x * sc, z * cos(c)), (if (z == 0.0) z else y * sc / z).limitedAsin)
}

public open class Azimuthal(
    public val scale: (Double) -> Double,
    public val angle: (Double) -> Double) : Projector {
    override fun project(lambda: Double, phi: Double): DoubleArray {
        val cx = cos(lambda)
        val cy = cos(phi)
        val k = scale(cx * cy)
        return doubleArrayOf(k * cy * sin(lambda), k * sin(phi))
    }

    override fun invert(x: Double, y: Double): DoubleArray {
        val z = sqrt(x * x + y * y)
        val c = angle(z)
        val sc = sin(c)
        return doubleArrayOf(atan2(x * sc, z * cos(c)), (if (z != .0) y * sc / z else z).asin)
    }
}
