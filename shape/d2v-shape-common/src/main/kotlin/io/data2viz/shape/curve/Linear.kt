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


class Linear(override val path: Path) : Curve {

    private var pointStatus = -1
    private var lineStatus: Int = -1

    override fun areaStart() {
        lineStatus = 0
    }

    override fun areaEnd() {
        lineStatus = -1
    }

    override fun lineStart() {
        pointStatus = 0
    }

    override fun lineEnd() {
        if (lineStatus > 0 || (lineStatus != 0 && pointStatus == 1)) {
            path.closePath()
        }
        if (lineStatus != -1) lineStatus = 1 - lineStatus
    }

    override fun point(x: Double, y: Double) {
        if (pointStatus == 0) {
            pointStatus = 1
            if (lineStatus > 0) path.lineTo(x, y) else path.moveTo(x, y)
            return
        }
        if (pointStatus == 1) {
            pointStatus = 2
        }
        path.lineTo(x, y)
    }
}

