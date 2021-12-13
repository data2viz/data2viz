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

import io.data2viz.geo.projection.common.BaseConditionalProjector
import io.data2viz.geo.projection.common.Projector
import io.data2viz.math.EPSILON
import io.data2viz.math.deg
import kotlin.math.*

/**
 * The [ConicEqualAreaProjector]. See also conic.parallels.
 * For some parallels values use [CylindricalEqualAreaProjector]
 *
 * @see BaseConditionalProjector
 */
public fun conicEqualAreaProjection(init: ConicProjection.() -> Unit = {}): ConicProjection =
    conicProjection(ConicEqualAreaBaseConditionalProjector()) {
        scale = 155.424
        center(0.0.deg, 33.6442.deg)
        init()
    }


internal class ConicEqualAreaBaseConditionalProjector(
    private val conicEqualAreaProjector: ConicEqualAreaProjector = ConicEqualAreaProjector(),
    private val cylindricalEqualAreaProjector: CylindricalEqualAreaProjector = CylindricalEqualAreaProjector(
        conicEqualAreaProjector.phi0
    )
) : ConicProjector, BaseConditionalProjector() {
    override var phi0: Double
        get() = conicEqualAreaProjector.phi0
        set(value) {
            conicEqualAreaProjector.phi0 = value
        }
    override var phi1: Double
        get() = conicEqualAreaProjector.phi1
        set(value) {
            conicEqualAreaProjector.phi1 = value
        }

    override val baseProjector: Projector
        get() = cylindricalEqualAreaProjector
    override val nestedProjector: Projector
        get() = conicEqualAreaProjector
    override val isNeedUseBaseProjector: Boolean
        get() = conicEqualAreaProjector.isPossibleToUseProjector
}


public class ConicEqualAreaProjector : ConicProjector, Projector {

    override var phi0: Double = 0.0
        set(value) {
            field = value
            recalculate()
        }
    override var phi1: Double = PI / 3.0
        set(value) {
            field = value
            recalculate()
        }

    private var sy0 = sy0()
    private var n = n()
    private var c = c()
    private var r0 = r0()
    public var isPossibleToUseProjector: Boolean = isPossibleToUse()
        private set

    private fun recalculate() {
        sy0 = sy0()
        n = n()
        c = c()
        r0 = r0()
        isPossibleToUseProjector = isPossibleToUse()
    }

    private fun isPossibleToUse() = abs(n) < EPSILON

    private fun r0() = sqrt(c) / n

    private fun c() = 1 + sy0 * (2 * n - sy0)

    private fun n() = (sy0 + sin(phi1)) / 2

    private fun sy0() = sin(phi0)

    override fun invert(x: Double, y: Double): DoubleArray {
        val r0y = r0y(y)
        return doubleArrayOf(
            atan2(x, abs(r0y)) / n * sign(r0y),
            asin((c - (x * x + r0y * r0y) * n * n) / (2 * n))
        )
    }

    override fun project(lambda: Double, phi: Double): DoubleArray {
        val r = r(phi)

        return doubleArrayOf(
            r * sin(lambda * n),
            r0 - r * cos(lambda * n)
        )
    }

    private fun r0y(phi: Double) = r0 - phi

    private fun r(phi: Double) = sqrt(c - 2 * n * sin(phi)) / n
}
