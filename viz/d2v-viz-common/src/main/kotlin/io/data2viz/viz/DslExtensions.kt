package io.data2viz.viz


/**
 * Handle both horizontal and vertical alignments.
 */
data class TextAlign internal constructor(val horizontal: TextHAlign, val vertical: TextVAlign)


/**
 * Extension property to get and set all text alignments properties in one expression.
 */
var TextNode.textAlign:TextAlign
    get() = textAlign(hAlign, vAlign)
    set(value) {
        hAlign = value.horizontal
        vAlign = value.vertical
    }


/**
 * Extension function to facilitate the alignment setting in a TextNode
 */
fun TextNode.textAlign(horizontal: TextHAlign = TextHAlign.LEFT, vertical: TextVAlign = TextVAlign.BASELINE) =
    TextAlign(horizontal, vertical)

