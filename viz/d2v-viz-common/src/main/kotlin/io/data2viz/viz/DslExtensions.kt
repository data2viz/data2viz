package io.data2viz.viz


/**
 * Handle both horizontal and vertical alignments.
 */
data class TextAlign internal constructor(val horizontal: THAlign, val vertical: TVAlign)


/**
 * Extension property to get and set all text alignments properties in one expression.
 */
var TextNode.textAlign:TextAlign
    get() = textAlign(anchor, baseline)
    set(value) {
        anchor = value.horizontal
        baseline = value.vertical
    }


/**
 * Extension function to facilitate the alignment setting in a TextNode
 */
fun TextNode.textAlign(horizontal: THAlign = anchor, vertical: TVAlign = baseline) = TextAlign(horizontal, vertical)

