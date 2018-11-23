package io.data2viz.viz

import javafx.geometry.*
import javafx.scene.canvas.*
import javafx.scene.text.*

import javafx.scene.text.Font 			as JfxFont
import javafx.scene.text.FontPosture 	as JfxFontPosture
import javafx.scene.text.FontWeight 	as JfxFontWeight


internal fun TextNode.render(gc: GraphicsContext){

	gc.textAlign 	= hAlign.jfx
	gc.textBaseline = vAlign.jfx

	gc.font = JfxFont.font(fontFamily.name, fontWeight.jfx, fontStyle.jfx, fontSize)

	textColor?.let {
		gc.fill = it.toPaint()
		gc.fillText(textContent, x, y)
	}

	stroke?.let {
		gc.strokeText(textContent, x, y)
	}
}

private val TextVAlign.jfx: VPos
	get() = when(this){
		TextVAlign.BASELINE  -> VPos.BASELINE
		TextVAlign.HANGING   -> VPos.TOP
		TextVAlign.MIDDLE    -> VPos.CENTER
	}

private val TextHAlign.jfx: TextAlignment
	get() = when(this){
		TextHAlign.START,TextHAlign.LEFT    	-> TextAlignment.LEFT
		TextHAlign.END, TextHAlign.RIGHT      -> TextAlignment.RIGHT
		TextHAlign.MIDDLE   				-> TextAlignment.CENTER
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
