package io.data2viz.viz

import javafx.geometry.*
import javafx.scene.text.*

import javafx.scene.text.Font 			as JfxFont
import javafx.scene.text.FontPosture 	as JfxFontPosture
import javafx.scene.text.FontWeight 	as JfxFontWeight


internal fun TextNode.render(renderer: JFxVizRenderer){
	val gc = renderer.gc

	gc.textAlign 	= anchor.jfx
	gc.textBaseline = baseline.jfx

	gc.font = JfxFont.font(fontFamily.name, fontWeight.jfx, fontStyle.jfx, fontSize)

	style.fill?.let {
		gc.fillText(textContent, x, y)
	}

	style.stroke?.let {
		gc.strokeText(textContent, x, y)
	}
}

private val TextAlignmentBaseline.jfx: VPos
	get() = when(this){
		TextAlignmentBaseline.BASELINE  -> VPos.BASELINE
		TextAlignmentBaseline.HANGING   -> VPos.TOP
		TextAlignmentBaseline.MIDDLE    -> VPos.CENTER
	}

private val TextAnchor.jfx: TextAlignment
	get() = when(this){
		TextAnchor.START    -> TextAlignment.LEFT
		TextAnchor.END      -> TextAlignment.RIGHT
		TextAnchor.MIDDLE   -> TextAlignment.CENTER
	}


private val FontWeight.jfx: JfxFontWeight
	get() = when(this) {
		FontWeight.NORMAL	-> JfxFontWeight.NORMAL
		FontWeight.BOLD  	-> JfxFontWeight.BOLD
	}

private val FontPosture.jfx: JfxFontPosture
	get() = when(this) {
		FontPosture.ITALIC 	-> JfxFontPosture.ITALIC
		FontPosture.NORMAL	-> JfxFontPosture.REGULAR
	}
