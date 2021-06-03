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

public class LinearClosed(

    override val path: Path) : Curve {

    private var pointStatus = -1

    override fun areaStart() {}
    override fun areaEnd() {}

    override fun lineStart() {
        pointStatus = 0
    }

    override fun lineEnd() {
        if (pointStatus > 0) {
            path.closePath()
        }
    }

    override fun point(x: Double, y: Double) {
        if (pointStatus > 0) {
            path.lineTo(x, y)
        } else {
            pointStatus = 1
            path.moveTo(x,y)
        }
    }
}
