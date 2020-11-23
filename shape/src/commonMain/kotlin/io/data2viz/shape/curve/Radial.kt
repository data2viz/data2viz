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
import kotlin.math.cos
import kotlin.math.sin

public abstract class AbstractRadial(


    override val path: Path,

    public val curve: Curve) : Curve {

    override fun areaEnd() {
        curve.areaEnd()
    }

    override fun lineStart() {
        curve.lineStart()
    }

    override fun lineEnd() {
        curve.lineEnd()
    }

    override fun areaStart() {
        curve.areaStart()
    }

    // TODO : rename a and r instead of x and y ?
    override fun point(x: Double, y: Double) {
        curve.point(y * sin(x), y * -cos(x));
    }
}

class RadialLinear(path: Path) : AbstractRadial(path, Linear(path))
class Radial(path: Path, curve: Curve) : AbstractRadial(path, curve)