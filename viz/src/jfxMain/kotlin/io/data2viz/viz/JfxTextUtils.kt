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
import javafx.geometry.VPos
import javafx.scene.text.Text
import javafx.scene.text.TextAlignment
import javafx.scene.text.Font as JFont

internal actual fun textMeasure(
    text: String,
    fontSize: Double,
    fontFamily: FontFamily,
    fontWeight: FontWeight,
    fontStyle: FontPosture
): Rect {

    val textNode = Text(text)
    textNode.font = JFont.font(fontFamily.name, fontWeight.jfx, fontStyle.jfx, fontSize)
    val bounds = textNode.layoutBounds

    return RectGeom(
        Point(bounds.minX, bounds.minY),
        Point(bounds.maxX, bounds.maxY))
}

private val TextVAlign.jfx: VPos
    get() = when (this) {
        TextVAlign.BASELINE -> VPos.BASELINE
        TextVAlign.HANGING  -> VPos.TOP
        TextVAlign.MIDDLE   -> VPos.CENTER
    }

@Suppress("DEPRECATION")
private val TextHAlign.jfx: TextAlignment
    get() = when (this) {
        TextHAlign.START, TextHAlign.LEFT   -> TextAlignment.LEFT
        TextHAlign.END, TextHAlign.RIGHT    -> TextAlignment.RIGHT
        TextHAlign.MIDDLE                   -> TextAlignment.CENTER
    }


private val FontWeight.jfx: javafx.scene.text.FontWeight
    get() = when (this) {
        FontWeight.NORMAL   -> javafx.scene.text.FontWeight.NORMAL
        FontWeight.BOLD     -> javafx.scene.text.FontWeight.BOLD
    }

private val FontPosture.jfx: javafx.scene.text.FontPosture
    get() = when (this) {
        FontPosture.ITALIC -> javafx.scene.text.FontPosture.ITALIC
        FontPosture.NORMAL -> javafx.scene.text.FontPosture.REGULAR
    }
