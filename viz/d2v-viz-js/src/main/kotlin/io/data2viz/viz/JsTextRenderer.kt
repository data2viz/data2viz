package io.data2viz.viz

import org.w3c.dom.*

internal fun TextNode.render(context: CanvasRenderingContext2D) {
	context.textAlign = hAlign.js
	context.textBaseline = vAlign.js

	context.font = "${fontStyle.js} ${fontWeight.js} ${fontSize}px ${fontFamily.name}"

	fill?.let {
		context.fillText(textContent, x, y)
	}

	stroke?.let {
		context.strokeText(textContent, x, y)
	}
}

private val TVAlign.js: CanvasTextBaseline
	get() = when(this){
		TVAlign.BASELINE  -> CanvasTextBaseline.ALPHABETIC
		TVAlign.HANGING   -> CanvasTextBaseline.HANGING
		TVAlign.MIDDLE    -> CanvasTextBaseline.MIDDLE
	}

private val THAlign.js: CanvasTextAlign
	get() = when(this){
		THAlign.START, 	THAlign.LEFT    	-> CanvasTextAlign.LEFT
		THAlign.END,	THAlign.RIGHT      	-> CanvasTextAlign.RIGHT
		THAlign.MIDDLE   					-> CanvasTextAlign.CENTER
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
