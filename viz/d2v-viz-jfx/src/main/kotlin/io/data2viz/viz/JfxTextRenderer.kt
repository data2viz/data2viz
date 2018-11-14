package io.data2viz.viz

import javafx.geometry.*
import javafx.scene.text.*




fun TextNode.render(renderer: JFxVizRenderer){
	val gc = renderer.gc
	gc.textAlign = style.anchor.jfx
	gc.textBaseline = style.baseline.jfx

	gc.font = when(fontFamily) {
		null -> Font(Font.getDefault().name, fontSize)
		else -> Font(fontFamily, fontSize)
	}

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
