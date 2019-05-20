package io.data2viz.viz

import org.w3c.dom.*

internal fun TextNode.render(context: CanvasRenderingContext2D) {
	context.textAlign = hAlign.js
	context.textBaseline = vAlign.js

	context.font = "${fontStyle.js} ${fontWeight.js} ${fontSize}px ${fontFamily.name}"

	textColor?.let {
		context.fillStyle = it.toCanvasPaint(context)
		context.fillText(textContent, x, y)
	}

	stroke?.let {
		context.strokeText(textContent, x, y)
	}
}

private val TextVAlign.js: CanvasTextBaseline
	get() = when(this){
		TextVAlign.BASELINE  -> CanvasTextBaseline.ALPHABETIC
		TextVAlign.HANGING   -> CanvasTextBaseline.HANGING
		TextVAlign.MIDDLE    -> CanvasTextBaseline.MIDDLE
	}

private val TextHAlign.js: CanvasTextAlign
	get() = when(this){
		TextHAlign.START, 	TextHAlign.LEFT    	-> CanvasTextAlign.LEFT
		TextHAlign.END,	TextHAlign.RIGHT      	-> CanvasTextAlign.RIGHT
		TextHAlign.MIDDLE   					-> CanvasTextAlign.CENTER
	}

private val FontWeight.js: String
	get() = when(this) {
		FontWeight.NORMAL	-> "normal"
		FontWeight.BOLD  	-> "bold"
	}

private val FontPosture.js: String
	get() = when(this) {
		FontPosture.ITALIC 	-> "italic"
		FontPosture.NORMAL	-> "normal"
	}
