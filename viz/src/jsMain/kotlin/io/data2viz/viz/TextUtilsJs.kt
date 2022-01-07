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

import io.data2viz.geom.Point
import io.data2viz.geom.Rect
import io.data2viz.geom.RectGeom
import io.data2viz.viz.FontPosture
import io.data2viz.viz.FontWeight
import io.data2viz.viz.TextHAlign
import io.data2viz.viz.TextVAlign
import kotlinx.browser.document
import org.w3c.dom.*


private val context: CanvasRenderingContext2D by lazy {
    val canvas = document.createElement("canvas") as HTMLCanvasElement
    canvas.getContext("2d") as CanvasRenderingContext2D
}


internal actual fun textMeasure(
    text: String,
    fontSize: Double,
    fontFamily: FontFamily,
    fontWeight: FontWeight,
    fontStyle: FontPosture): Rect {

    context.font = "${fontStyle.js} ${fontWeight.js} ${fontSize}px ${fontFamily.name}"
    val metrics: TextMetrics = context.measureText("x${text}x")
    val xxMetrics: TextMetrics = context.measureText("xx")
    return RectGeom(
        width = metrics.actualBoundingBoxRight - metrics.actualBoundingBoxLeft -
                (xxMetrics.actualBoundingBoxRight - xxMetrics.actualBoundingBoxLeft),
        height = metrics.actualBoundingBoxDescent - metrics.actualBoundingBoxAscent -
                (xxMetrics.actualBoundingBoxDescent - xxMetrics.actualBoundingBoxAscent)
    )
}

private val TextVAlign.js: CanvasTextBaseline
    get() = when (this) {
        TextVAlign.BASELINE     -> CanvasTextBaseline.ALPHABETIC
        TextVAlign.HANGING      -> CanvasTextBaseline.HANGING
        TextVAlign.MIDDLE       -> CanvasTextBaseline.MIDDLE
    }

@Suppress("DEPRECATION")
private val TextHAlign.js: CanvasTextAlign
    get() = when (this) {
        TextHAlign.START, TextHAlign.LEFT   -> CanvasTextAlign.LEFT
        TextHAlign.END, TextHAlign.RIGHT    -> CanvasTextAlign.RIGHT
        TextHAlign.MIDDLE                   -> CanvasTextAlign.CENTER
    }

private val FontWeight.js: String
    get() = when (this) {
        FontWeight.NORMAL -> "normal"
        FontWeight.BOLD     -> "bold"
    }

private val FontPosture.js: String
    get() = when (this) {
        FontPosture.ITALIC -> "italic"
        FontPosture.NORMAL -> "normal"
    }
