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

import javafx.geometry.VPos
import javafx.scene.canvas.GraphicsContext
import javafx.scene.text.TextAlignment
import javafx.scene.text.Font as JfxFont
import javafx.scene.text.FontPosture as JfxFontPosture
import javafx.scene.text.FontWeight as JfxFontWeight


internal fun TextNode.render(gc: GraphicsContext) {

    gc.textAlign = hAlign.jfx
    gc.textBaseline = vAlign.jfx

    gc.font = JfxFont.font(fontFamily.name, fontWeight.jfx, fontStyle.jfx, fontSize)

    textColor?.let {
        gc.fill = it.toPaint()
        gc.fillText(textContent, x, y)
    }

    strokeColor?.let {
        gc.strokeText(textContent, x, y)
    }
}

private val TextVAlign.jfx: VPos
    get() = when (this) {
        TextVAlign.BASELINE -> VPos.BASELINE
        TextVAlign.HANGING  -> VPos.TOP
        TextVAlign.MIDDLE   -> VPos.CENTER
    }

private val TextHAlign.jfx: TextAlignment
    get() = when (this) {
        TextHAlign.START, TextHAlign.LEFT   -> TextAlignment.LEFT
        TextHAlign.END, TextHAlign.RIGHT    -> TextAlignment.RIGHT
        TextHAlign.MIDDLE                   -> TextAlignment.CENTER
    }


private val FontWeight.jfx: JfxFontWeight
    get() = when (this) {
        FontWeight.NORMAL   -> JfxFontWeight.NORMAL
        FontWeight.BOLD     -> JfxFontWeight.BOLD
    }

private val FontPosture.jfx: JfxFontPosture
    get() = when (this) {
        FontPosture.ITALIC -> JfxFontPosture.ITALIC
        FontPosture.NORMAL -> JfxFontPosture.REGULAR
    }
