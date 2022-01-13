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

// TODO : StepBefore, StepAfter, Step
public abstract class AbstractStep(

    override val path: Path,

    changePoint: Double = 0.5) : Curve {

    private var x = -1.0
    private var y = -1.0

    private var lineStatus = -1
    private var pointStatus = -1

    private var _changePoint = changePoint

    override fun areaStart() {
        lineStatus = 0
    }

    override fun areaEnd() {
        lineStatus = -1
    }

    override fun lineStart() {
        x = -1.0
        y = -1.0
        pointStatus = 0
    }

    override fun lineEnd() {
        if (0 < _changePoint && _changePoint < 1 && pointStatus == 2) path.lineTo(x, y)
        if (lineStatus > 0) path.closePath()
        if (lineStatus >= 0) {
            _changePoint = 1 - _changePoint
            lineStatus = 1 - lineStatus
        }
    }

    override fun point(x: Double, y: Double) {
        when (pointStatus) {
            0 -> {
                pointStatus = 1
                if (lineStatus > 0) path.lineTo(x, y) else path.moveTo(x, y)
                this.x = x
                this.y = y
                return
            }
            1 -> pointStatus = 2
            else -> {
            }
        }
        if (_changePoint <= 0) {
            path.lineTo(this.x, y)
            path.lineTo(x, y)
        } else {
            val x1 = this.x * (1 - _changePoint) + x * _changePoint
            path.lineTo(x1, this.y)
            path.lineTo(x1, y)
        }
        this.x = x
        this.y = y
    }
}

public class Step(override val path: Path) : AbstractStep(path, 0.5)
public class StepBefore(override val path: Path) : AbstractStep(path, 0.0)
public class StepAfter(override val path: Path) : AbstractStep(path, 1.0)
