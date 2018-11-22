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

	fill?.let {
		gc.fillText(textContent, x, y)
	}

	stroke?.let {
		gc.strokeText(textContent, x, y)
	}
}

private val TVAlign.jfx: VPos
	get() = when(this){
		TVAlign.BASELINE  -> VPos.BASELINE
		TVAlign.HANGING   -> VPos.TOP
		TVAlign.MIDDLE    -> VPos.CENTER
	}

private val THAlign.jfx: TextAlignment
	get() = when(this){
		THAlign.START,THAlign.LEFT    	-> TextAlignment.LEFT
		THAlign.END, THAlign.RIGHT      -> TextAlignment.RIGHT
		THAlign.MIDDLE   				-> TextAlignment.CENTER
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
