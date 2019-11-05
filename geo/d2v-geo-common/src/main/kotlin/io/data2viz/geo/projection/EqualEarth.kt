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

import io.data2viz.geo.projection.common.Projection
import io.data2viz.geo.projection.common.Projector
import io.data2viz.geo.projection.common.projection
import io.data2viz.math.EPSILON2
import kotlin.math.*

const val A1 = 1.340264
const val A2 = -0.081106
const val A3 = 0.000893
const val A4 = 0.003796
val M = sqrt(3.0) / 2
const val iterations = 12

fun equalEarthProjection(init: Projection.() -> Unit = {}) =
    projection(EqualEarthProjector()) {
        scale = 177.158
        init()
    }

/**
 * The Equal Earth projection, by Bojan Šavrič et al., 2018.
 */
class EqualEarthProjector : Projector {

    override fun project(lambda: Double, phi: Double): DoubleArray {
        val l = asin(M * sin(phi))
        val l2 = l * l
        val l6 = l2 * l2 * l2
        return doubleArrayOf(
            lambda * cos(l) / (M * (A1 + 3 * A2 * l2 + l6 * (7 * A3 + 9 * A4 * l2))),
            l * (A1 + A2 * l2 + l6 * (A3 + A4 * l2))
        )
    }

    override fun invert(x: Double, y: Double): DoubleArray {
        var l = y
        var l2 = l * l
        var l6 = l2 * l2 * l2
        for (i in 0 until iterations) {
            val fy = l * (A1 + A2 * l2 + l6 * (A3 + A4 * l2)) - y
            val fpy = A1 + 3 * A2 * l2 + l6 * (7 * A3 + 9 * A4 * l2)
            val delta = fy / fpy
            l2 = l * l
            l6 = l2 * l2 * l2
            l -= delta
            if (abs(delta) < EPSILON2) break
        }
        return doubleArrayOf(
            M * x * (A1 + 3 * A2 * l2 + l6 * (7 * A3 + 9 * A4 * l2)) / cos(l),
            asin(sin(l) / M)
        )
    }

}