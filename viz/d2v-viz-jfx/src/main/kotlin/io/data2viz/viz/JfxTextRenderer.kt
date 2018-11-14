package io.data2viz.viz

import javafx.geometry.*
import javafx.scene.text.*

import javafx.scene.text.Font as Fontfx


fun TextNode.render(renderer: JFxVizRenderer){
	val gc = renderer.gc

	gc.textAlign 	= anchor.jfx
	gc.textBaseline = baseline.jfx

	//TODO nba font style name: font style vs font posture
	gc.font = Fontfx.font(fontFamily.jfx, fontWeight.jfx, fontStyle.jfx, fontSize)

	style.fill?.let {
		gc.fillText(textContent, x, y)
	}

	style.stroke?.let {
		gc.strokeText(textContent, x, y)
	}
}

val TextAlignmentBaseline.jfx: VPos
	get() = when(this){
		TextAlignmentBaseline.BASELINE  -> VPos.BASELINE
		TextAlignmentBaseline.HANGING   -> VPos.TOP
		TextAlignmentBaseline.MIDDLE    -> VPos.CENTER
	}

val TextAnchor.jfx: TextAlignment
	get() = when(this){
		TextAnchor.START    -> TextAlignment.LEFT
		TextAnchor.END      -> TextAlignment.RIGHT
		TextAnchor.MIDDLE   -> TextAlignment.CENTER
	}

val Font.DefaultFamily.jfx: String
	get() = when(this) {
		Font.DefaultFamily.MONOSPACE	-> "monospace"
		Font.DefaultFamily.SANS_SERIF  	-> "sans-serif"
		Font.DefaultFamily.SERIF  		-> "serif"
	}

val Font.DefaultWeight.jfx: FontWeight
	get() = when(this) {
		Font.DefaultWeight.NORMAL	-> FontWeight.NORMAL
		Font.DefaultWeight.BOLD  	-> FontWeight.BOLD
	}

val Font.DefaultStyle.jfx: FontPosture
	get() = when(this) {
		Font.DefaultStyle.ITALIC 	-> FontPosture.ITALIC
		Font.DefaultStyle.NORMAL	-> FontPosture.REGULAR
	}