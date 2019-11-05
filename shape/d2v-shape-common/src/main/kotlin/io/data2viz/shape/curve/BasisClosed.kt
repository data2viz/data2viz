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

package io.data2viz.shape.curve

import io.data2viz.geom.Path
import io.data2viz.shape.Curve

class BasisClosed(override val path: Path): Curve {

    private var x0 = -1.0
    private var y0 = -1.0
    private var x1 = -1.0
    private var y1 = -1.0
    private var x2 = -1.0
    private var y2 = -1.0
    private var x3 = -1.0
    private var y3 = -1.0
    private var x4 = -1.0
    private var y4 = -1.0

    private var pointStatus = -1

    override fun areaStart() {}

    override fun areaEnd() {}

    override fun lineStart() {
        x0 = -1.0
        y0 = -1.0
        x1 = -1.0
        y1 = -1.0
        x2 = -1.0
        y2 = -1.0
        x3 = -1.0
        y3 = -1.0
        x4 = -1.0
        y4 = -1.0
        pointStatus = 0
    }

    override fun lineEnd() {
        when (pointStatus) {
            1 -> {
                path.moveTo(x2, y2)
                path.closePath()
            }
            2 -> {
                path.moveTo((x2 + 2 * x3) / 3, (y2 + 2 * y3) / 3)
                path.lineTo((x3 + 2 * x2) / 3, (y3 + 2 * y2) / 3)
                path.closePath()
            }
            3 -> {
                point(x2, y2)
                point(x3, y3)
                point(x4, y4)
            }
        }
    }

    // TODO : non specific, inherit from basis
    private fun curve(x: Double, y: Double){
        path.bezierCurveTo(
                (2 * x0 + x1) / 3,
                (2 * y0 + y1) / 3,
                (x0 + 2 * x1) / 3,
                (y0 + 2 * y1) / 3,
                (x0 + 4 * x1 + x) / 6,
                (y0 + 4 * y1 + y) / 6
        )
    }

    override fun point(x: Double, y: Double) {
        when (pointStatus) {
            0 -> {
               pointStatus = 1
                x2 = x
                y2 = y
            }
            1 -> {
                pointStatus = 2
                x3 = x
                y3 = y
            }
            2 -> {
                pointStatus = 3
                x4 = x
                y4 = y
                path.moveTo((x0 + 4 * x1 + x) / 6, (y0 + 4 * y1 + y) / 6)
            }
            else -> {
                curve(x, y)
            }
        }
        x0 = x1
        x1 = x
        y0 = y1
        y1 = y
    }
}