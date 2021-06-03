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

package io.data2viz.viz

import android.graphics.*
import io.data2viz.color.*
import io.data2viz.color.Color
import io.data2viz.color.LinearGradient
import io.data2viz.color.RadialGradient

public typealias ALinearGradient = android.graphics.LinearGradient
public typealias ARadialGradient = android.graphics.RadialGradient


public fun ColorOrGradient.updatePaint(paint: Paint, renderer: AndroidCanvasRenderer) {
    when (this) {
        is Color -> {
            paint.color = this.toColor()
            paint.shader = null
        }
        is LinearGradient -> paint.shader = this.toLinearGradient(renderer)
        is RadialGradient -> paint.shader = this.toRadialGradient(renderer)
        else -> error("Unknown type :: ${this::class}")
    }
}

private fun RadialGradient.toRadialGradient(renderer: AndroidCanvasRenderer) =
    with(renderer) {
        ARadialGradient(
            cx.dp,
            cy.dp,
            radius.dp,
            IntArray(colorStops.size) { colorStops[it].color.toColor() },
            FloatArray(colorStops.size) { colorStops[it].percent.value.toFloat() },
            Shader.TileMode.CLAMP
        )
    }


private fun LinearGradient.toLinearGradient(renderer: AndroidCanvasRenderer) =
    with(renderer) {
        ALinearGradient(
            x1.dp,
            y1.dp,
            x2.dp,
            y2.dp,
            IntArray(colorStops.size) { colorStops[it].color.toColor() },
            FloatArray(colorStops.size) { colorStops[it].percent.value.toFloat() },
            Shader.TileMode.CLAMP
        )
    }

public fun Color.toColor(): Int {
    return ((255 * this.alpha.value).toInt() and 0xff shl 24) or
            (this.r and 0xff shl 16) or
            (this.g and 0xff shl 8) or
            (this.b and 0xff)
}
