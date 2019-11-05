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

package io.data2viz.interpolate

import io.data2viz.color.*
import io.data2viz.math.*

// TODO use type for parameter percent ?
// TODO use gamma ?
private fun interpolateHcl(start: Color, end:Color, long:Boolean): Interpolator<Color> {
    var startHCL = start.toHcl()
    var endHCL = end.toHcl()
    val colorInterpolator = gamma()

    if (startHCL.isAchromatic()) startHCL = Colors.hcl(endHCL.h, startHCL.c, startHCL.l, startHCL.alpha)
    if (endHCL.isAchromatic()) endHCL = Colors.hcl(startHCL.h, endHCL.c, endHCL.l, endHCL.alpha)

    val h = interpolateHue(startHCL.h, endHCL.h, long)
    val c = colorInterpolator(startHCL.c, endHCL.c)
    val l = colorInterpolator(startHCL.l.value, endHCL.l.value)

    return fun(percent:Percent) = Colors.hcl(Angle(h(percent)), c(percent), Percent(l(percent)))
}

fun hclLongInterpolator(start:Color, end: Color) = interpolateHcl(start, end, long = true)
fun hclInterpolator(start:Color, end:Color) = interpolateHcl(start, end, long = false)

