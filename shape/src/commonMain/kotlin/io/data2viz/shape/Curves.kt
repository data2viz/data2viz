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

package io.data2viz.shape

import io.data2viz.geom.Path
import io.data2viz.shape.curve.*

public interface Curve {
    public val path: Path
    public fun areaStart()
    public fun areaEnd()
    public fun lineStart()
    public fun lineEnd()
    public fun point(x: Double, y: Double)
}

// TODO maybe give an alias name for a "(Path) -> Curve" object
public object curves {
    public val basis                   = {path: Path -> Basis(path) }
    public val basisClosed             = {path: Path -> BasisClosed(path) }
    public val basisOpen               = {path: Path -> BasisOpen(path) }
    public val bundle                  = {path: Path -> Bundle(path) }
    public val cardinal                = {path: Path -> Cardinal(path) }
    public val cardinalClosed          = {path: Path -> CardinalClosed(path) }
    public val cardinalOpen            = {path: Path -> CardinalOpen(path) }
    public val catmullRom              = {path: Path -> CatmullRom(path) }
    public val catmullRomClosed        = {path: Path -> CatmullRomClosed(path) }
    public val catmullRomOpen          = {path: Path -> CatmullRomOpen(path) }
    public val linear                  = {path: Path -> Linear(path) }
    public val linearClosed            = {path: Path -> LinearClosed(path) }
    public val monotoneX               = {path: Path -> MonotoneX(path) }
    public val monotoneY               = {path: Path -> MonotoneY(path) }
    public val natural                 = {path: Path -> Natural(path) }
//    val radialLinear            = {path: Path -> RadialLinear(path) }
//    val radialBasis             = {path: Path -> Radial(path, Basis(path)) }
//    val radialLinearClosed      = {path: Path -> Radial(path, LinearClosed(path)) }
    public val step                    = {path: Path -> Step(path) }
    public val stepBefore              = {path: Path -> StepBefore(path) }
    public val stepAfter               = {path: Path -> StepAfter(path) }
}

public object areas {
    public val default                 = { path: Path -> Linear(path) }
    public val basis                   = { path: Path -> Basis(path) }
}