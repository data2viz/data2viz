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

interface Curve {
    val path: Path
    fun areaStart()
    fun areaEnd()
    fun lineStart()
    fun lineEnd()
    fun point(x: Double, y: Double)
}

// TODO maybe give an alias name for a "(Path) -> Curve" object
object curves {
    val basis                   = {path: Path -> Basis(path) }
    val basisClosed             = {path: Path -> BasisClosed(path) }
    val basisOpen               = {path: Path -> BasisOpen(path) }
    val bundle                  = {path: Path -> Bundle(path) }
    val cardinal                = {path: Path -> Cardinal(path) }
    val cardinalClosed          = {path: Path -> CardinalClosed(path) }
    val cardinalOpen            = {path: Path -> CardinalOpen(path) }
    val catmullRom              = {path: Path -> CatmullRom(path) }
    val catmullRomClosed        = {path: Path -> CatmullRomClosed(path) }
    val catmullRomOpen          = {path: Path -> CatmullRomOpen(path) }
    val linear                  = {path: Path -> Linear(path) }
    val linearClosed            = {path: Path -> LinearClosed(path) }
    val monotoneX               = {path: Path -> MonotoneX(path) }
    val monotoneY               = {path: Path -> MonotoneY(path) }
    val natural                 = {path: Path -> Natural(path) }
//    val radialLinear            = {path: Path -> RadialLinear(path) }
//    val radialBasis             = {path: Path -> Radial(path, Basis(path)) }
//    val radialLinearClosed      = {path: Path -> Radial(path, LinearClosed(path)) }
    val step                    = {path: Path -> Step(path) }
    val stepBefore              = {path: Path -> StepBefore(path) }
    val stepAfter               = {path: Path -> StepAfter(path) }
}

object areas {
    val default                 = {path: Path -> Linear(path) }
    val basis                   = {path: Path -> Basis(path) }
}