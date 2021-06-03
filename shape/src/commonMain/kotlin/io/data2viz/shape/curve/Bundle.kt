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

package io.data2viz.shape.curve

import io.data2viz.geom.Path
import io.data2viz.shape.Curve

public class Bundle(
    override val path: Path,
    public val beta: Double = 0.85
) : Curve {

    private val basis = Basis(path)

    private var x = mutableListOf<Double>()
    private var y = mutableListOf<Double>()

    // TODO : not present in D3
    override fun areaStart() {}

    // TODO : not present in D3
    override fun areaEnd() {}

    override fun lineStart() {
        x.clear()
        y.clear()
        basis.lineStart()
    }

    override fun lineEnd() {
        val j = x.size - 1
        if (j > 0) {
            val x0 = x[0]
            val y0 = y[0]
            val dx = x[j] - x0
            val dy = y[j] - y0

            (0..j).forEach { index ->
                val t = index / j
                basis.point(
                        (beta * x[index]) + ((1 - beta) * (x0 + t * dx)),
                        (beta * y[index]) + ((1 - beta) * (y0 + t * dy))
                )
            }
        }
        basis.lineEnd()
    }

    override fun point(x: Double, y: Double) {
        this.x.add(x)
        this.y.add(y)
    }
}
