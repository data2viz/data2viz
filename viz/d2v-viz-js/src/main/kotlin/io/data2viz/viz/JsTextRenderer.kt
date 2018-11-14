package io.data2viz.viz

import org.w3c.dom.*

fun TextNode.render(context: CanvasRenderingContext2D) {
	context.textAlign = style.anchor.js
	context.textBaseline = style.baseline.js

	context.font = "${fontStyle.js} ${fontWeight.js} ${fontSize}px ${fontFamily.js}"

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

val Font.DefaultFamily.js: String
	get() = when(this) {
		Font.DefaultFamily.MONOSPACE	-> "monospace"
		Font.DefaultFamily.SANS_SERIF  	-> "sans-serif"
		Font.DefaultFamily.SERIF  		-> "serif"
	}

val Font.DefaultWeight.js: String
	get() = when(this) {
		Font.DefaultWeight.NORMAL	-> "normal"
		Font.DefaultWeight.BOLD  	-> "bold"
	}

val Font.DefaultStyle.js: String
	get() = when(this) {
		Font.DefaultStyle.ITALIC 	-> "italic"
		Font.DefaultStyle.NORMAL	-> "normal"
	}
