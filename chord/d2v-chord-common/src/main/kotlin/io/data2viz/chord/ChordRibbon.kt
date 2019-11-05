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

package io.data2viz.chord

import io.data2viz.geom.Path
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


private val halfPi = PI / 2


/**
 * Generates a ribbon for a Chord Diagram with the given radius.
 */
fun ribbon(radius: Double): (Chord, Path) -> Unit = { chord, path ->
    val s = chord.source
    val t = chord.target
    val sa0 = s.startAngle - halfPi
    val sa1 = s.endAngle - halfPi
    val sx0 = radius * cos(sa0)
    val sy0 = radius * sin(sa0)
    val ta0 = t.startAngle - halfPi
    val ta1 = t.endAngle - halfPi

    path.moveTo(sx0, sy0)
    path.arc(.0, .0, radius, sa0, sa1)
    if (sa0 != ta0 || sa1 != ta1) {
        path.quadraticCurveTo(.0, .0, radius * cos(ta0), radius * sin(ta0))
        path.arc(.0, .0, radius, ta0, ta1)
    }
    path.quadraticCurveTo(.0, .0, sx0, sy0)
    path.closePath()
}