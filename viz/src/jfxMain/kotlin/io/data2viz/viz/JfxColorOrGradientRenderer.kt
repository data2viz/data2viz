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

package io.data2viz.viz

import io.data2viz.color.*
import io.data2viz.color.Color
import io.data2viz.color.LinearGradient
import io.data2viz.color.RadialGradient
import javafx.scene.paint.*


typealias JfxLinearGradient = javafx.scene.paint.LinearGradient
typealias JfxRadialGradient = javafx.scene.paint.RadialGradient

val Color.jfxColor: javafx.scene.paint.Color
    get() = javafx.scene.paint.Color.rgb(r, g, b, alpha.value)


fun ColorOrGradient.toPaint() = when(this) {
    is LinearGradient       -> toLinearGradientJFX()
    is RadialGradient       -> toRadialGradientJFX()
    is Color                -> jfxColor
    else                    -> error("Unknown type $this")
}


fun LinearGradient.toLinearGradientJFX(): JfxLinearGradient = JfxLinearGradient(
    x1, y1, x2, y2,
    false,
    CycleMethod.NO_CYCLE, colorStops.toStops()
)

fun RadialGradient.toRadialGradientJFX(): JfxRadialGradient = JfxRadialGradient(
    .0, .0, cx, cy, radius,
    false,
    CycleMethod.NO_CYCLE, colorStops.toStops()
)

private fun List<ColorStop>.toStops(): List<Stop>? = map { Stop(it.percent.value, it.color.jfxColor) }
