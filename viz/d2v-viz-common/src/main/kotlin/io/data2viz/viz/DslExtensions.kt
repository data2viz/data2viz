package io.data2viz.viz


/**
 * Handle both horizontal and vertical alignments.
 */
data class TextAlign internal constructor(val horizontal:TextAnchor, val vertical: TextAlignmentBaseline)


/**
 * Extension property to get and set all text alignments properties in one expression.
 */
var TextNode.textAlign:TextAlign
    get() = textAlign(anchor, baseline)
    set(value) {
        style.anchor = value.horizontal
        style.baseline = value.vertical
    }


/**
 * Extension function to facilitate the alignment setting in a TextNode
 */
fun TextNode.textAlign(horizontal:TextAnchor = anchor, vertical: TextAlignmentBaseline = baseline) = TextAlign(horizontal, vertical)

