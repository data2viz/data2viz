/*
 * Copyright (c) 2018-2022. data2viz sàrl.
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

import io.data2viz.color.Color
import io.data2viz.color.ColorOrGradient
import io.data2viz.color.Colors
import io.data2viz.geom.Size

/**
 * This class is used to create rich text by composing different text nodes.
 * It manages new lignes, change of font size, font weight, font posture.
 *
 *
 * Add text nodes using the [text] function
 *
 *
 * result of the composition is the bounding size of the composed text and a
 * group that holds the different nodes.
 *
 * Use it to define a rich text block.
 *
 *     val richTextBuilder = RichTextBuilder {
 *         text("Hello "); text("Big", fontSize = 20.0, bold = true)
 *         newLine()
 *         text("World!!!", italic = true)
 *     }
 *
 * and then generate the corresponding group to add it to a [Viz].
 *
 *     val richtext = richTextBuilder.buildGroup()
 *     viz.add(richtext)
 *
 */
public class RichTextBuilder(internal val init: RichTextBuilder.() -> Unit = {}) {

    /**
     * Lazy execution of the lambda. Called only by getSize of buildGroup
     */
    private fun lazyBuild() {
        if (initialized.not()){
            init()
            initialized = true
        }
    }

    private var initialized = false


    /**
     * Builds the group containing all the rich-text nodes.
     * It's build lazily after all node have been added to the rich text builder.
     */
    public fun buildGroup(): GroupNode {
        lazyBuild()
        return GroupNode().apply {
            var lineHeights = .0
            lines.forEach { line ->
                var y =  lineHeights + line.height * .75
                line.nodes.forEach {
                    it.y = y
                    this.add(it)
                }
                lineHeights += line.height
            }
        }
    }

    public fun computeSize(): Size {
        lazyBuild()
        if (boundSize == null){
            val width = lines.maxOf { it.starts.last() }
            val height = lines.fold(0.0) { acc: Double, line: Line ->
                acc + line.fontSize * line.lineSize
            }
            boundSize = Size(width, height)
        }
        return boundSize!!
    }

    /**
     * The bounds of the generated rich text.
     */
    private var boundSize: Size? = null


    /**
     * Defines the current line size. The line height will be
     * the max font size of the line multiplied by this value.
     */
    public var lineSize: Double = 1.15
        set(value) {
            field = value
            currentLine.lineSize = value
        }

    /**
     * The current font size, used for the nexd added text node.
     */
    public var fontSize: Double = 12.0

    /**
     * The current text color, used for the nexd added text node.
     */
    public var textColor: Color = Colors.Web.black

    /**
     * The current font family, used for the nexd added text node.
     */
    public var fontFamily: FontFamily = FontFamily.SANS_SERIF

    /**
     * The current fontWeight, used for the next added text node.
     */
    public var fontWeight: FontWeight = FontWeight.NORMAL


    /**
     * The current fontStyle, used for the next added text node.
     */
    public var fontStyle: FontPosture = FontPosture.NORMAL


    /**
     * Adds a line return
     */
    public fun newLine() {
        currentLine = Line(lineSize)
        lines.add(currentLine)
    }

    /**
     * Adds a text node to the current line.
     *
     * uses the size defined by [RichTextBuilder.fontSize], unless overriden by the [fontSize] parameter.
     *
     * uses the style defined by [RichTextBuilder.fontStyle], unless overriden by the [italic] parameter.
     *
     * uses the weight defined by [RichTextBuilder.fontWeight], unless overriden by the [bold] parameter.
     *
     *     text("hello ")
     *     text("world!!", fontSize = 20.0, bold = true, italic = true, textColor = Colors.Web.red)
     */
    public fun text(textContent: String,
                    fontSize: Double? = null,
                    bold: Boolean? = null,
                    italic: Boolean? = null,
                    textColor: Color? = null,
                    fontFamily: FontFamily? = null,
    ){
        val textNode = TextNode()
        textNode.textContent = textContent
        textNode.textColor = if (textColor != null) textColor else this.textColor
        textNode.fontFamily = if (fontFamily != null) fontFamily else this.fontFamily
        textNode.hAlign = TextHAlign.LEFT
        textNode.vAlign = TextVAlign.BASELINE
        textNode.fontSize = if (fontSize != null) fontSize else this.fontSize
        textNode.fontWeight = when (bold) {
            null -> fontWeight
            true -> FontWeight.BOLD
            false -> FontWeight.NORMAL
        }
        textNode.fontStyle = when (italic){
            null -> fontStyle
            true -> FontPosture.ITALIC
            false -> FontPosture.NORMAL
        }
        currentLine.add(textNode)
    }


    /**
     * Holds the different TextNodes of a line.
     */
    private inner class Line(var lineSize: Double) {
        var nodes: MutableList<TextNode> = mutableListOf()

        /**
         * Keeps the total size of already added nodes.
         */
        val starts: MutableList<Double> = mutableListOf(.0)

        private var _fontSize = .0
        val fontSize:Double
            get() = _fontSize

        val height: Double
            get() = fontSize * lineSize

        fun add(textNode: TextNode) {
            _fontSize = maxOf(_fontSize, textNode.fontSize)
            boundSize   = null
            textNode.x = starts.last()
            nodes.add(textNode)
            val measureText = textNode.measureText()
            starts.add(starts.last() + measureText.width)
        }

    }

    private var currentBaseLine = fontSize
    private var currentLine = Line(lineSize)
    private val lines = mutableListOf(currentLine)

}
