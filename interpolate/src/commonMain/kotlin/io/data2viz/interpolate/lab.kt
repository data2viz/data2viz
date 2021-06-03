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

package io.data2viz.interpolate

import io.data2viz.color.*
import io.data2viz.math.*

// TODO use type for parameter percent ?
// TODO use gamma ?
private fun interpolateLab(start: Color, end:Color): Interpolator<Color> {
    val startLab = start.toLab()
    val endLab = end.toLab()
    val colorInterpolator = gamma()

    val l = colorInterpolator(startLab.labL.value, endLab.labL.value)
    val a = colorInterpolator(startLab.labA, endLab.labA)
    val b = colorInterpolator(startLab.labB, endLab.labB)

    return fun(percent:Percent) = Colors.lab(Percent(l(percent)), a(percent), b(percent))
}

public fun labInterpolator(start:Color, end:Color): (Percent) -> Color = interpolateLab(start, end)
