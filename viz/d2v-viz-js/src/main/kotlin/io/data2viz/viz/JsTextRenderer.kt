package io.data2viz.viz

import org.w3c.dom.*

fun TextNode.render(context: CanvasRenderingContext2D) {
	context.textAlign = style.anchor.js
	context.textBaseline = style.baseline.js

	context.font = "${fontStyle.js} ${fontWeight.js} ${fontSize}px ${fontFamily.name}"

	style.fill?.let {
		context.fillText(textContent, x, y)
	}

	style.stroke?.let {
		context.strokeText(textContent, x, y)
	}
}

val TextAlignmentBaseline.js: CanvasTextBaseline
	get() = when(this){
		TextAlignmentBaseline.BASELINE  -> CanvasTextBaseline.ALPHABETIC
		TextAlignmentBaseline.HANGING   -> CanvasTextBaseline.HANGING
		TextAlignmentBaseline.MIDDLE    -> CanvasTextBaseline.MIDDLE
	}

val TextAnchor.js: CanvasTextAlign
	get() = when(this){
		TextAnchor.START    -> CanvasTextAlign.LEFT
		TextAnchor.END      -> CanvasTextAlign.RIGHT
		TextAnchor.MIDDLE   -> CanvasTextAlign.CENTER
	}

val FontWeight.js: String
	get() = when(this) {
		FontWeight.NORMAL	-> "normal"
		FontWeight.BOLD  	-> "bold"
	}

val FontPosture.js: String
	get() = when(this) {
		FontPosture.ITALIC 	-> "italic"
		FontPosture.NORMAL	-> "normal"
	}
