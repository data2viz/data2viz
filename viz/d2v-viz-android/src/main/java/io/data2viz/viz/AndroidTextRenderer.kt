package io.data2viz.viz

import android.graphics.*


fun TextNode.render(renderer: AndroidCanvasRenderer) {
	val canvas = renderer.canvas
	with(renderer) {
		paint.textAlign = style.anchor.android
		paint.textSize = fontSize.dp

		paint.typeface = Typeface.create(fontFamily.android, getAndroidStyle(fontWeight, fontStyle))

		val dy = baseline.dy(renderer, paint.fontMetrics)

		style.fill?.let {
			paint.style = Paint.Style.FILL
			it.updatePaint(paint, renderer)
			canvas.drawText(
				textContent,
				x.dp,
				y.dp - dy,
				paint )
		}
		style.stroke?.let {
			paint.style = Paint.Style.STROKE
			it.updatePaint(paint, renderer)
			canvas.drawText(
				textContent,
				x.dp,
				y.dp - dy,
				paint )
		}
	}
}


/**
 * The y distance to move the text from baseline in order to respect the wanted
 * alignment.
 *
 * The middle alignement is an approximation.
 * TODO resolve by implementing DV-105
 */
fun TextAlignmentBaseline.dy(renderer: AndroidCanvasRenderer, fontMetrics: Paint.FontMetrics): Float =
	with(renderer){
		when(this@dy){
			TextAlignmentBaseline.BASELINE  -> 0F
			TextAlignmentBaseline.HANGING   -> fontMetrics.top
			TextAlignmentBaseline.MIDDLE    -> fontMetrics.ascent * .4f
		}
	}

val TextAnchor.android: Paint.Align
	get() = when(this){
		TextAnchor.START    -> Paint.Align.LEFT
		TextAnchor.END      -> Paint.Align.RIGHT
		TextAnchor.MIDDLE   -> Paint.Align.CENTER
	}

val Font.DefaultFamily.android: String
	get() = when(this) {
		Font.DefaultFamily.MONOSPACE	-> "monospace"
		Font.DefaultFamily.SANS_SERIF  	-> "sans-serif"
		Font.DefaultFamily.SERIF  		-> "serif"
	}

//TODO nba refactor code: make access to the android text Font more reachable
fun getAndroidStyle(fontWeight: Font.DefaultWeight, fontStyle: Font.DefaultStyle): Int {
	return when(fontWeight) {
		Font.DefaultWeight.NORMAL ->
			when(fontStyle) {
				Font.DefaultStyle.NORMAL -> Typeface.NORMAL
				Font.DefaultStyle.ITALIC -> Typeface.ITALIC
			}

		Font.DefaultWeight.BOLD ->
			when(fontStyle) {
				Font.DefaultStyle.NORMAL -> Typeface.BOLD
				Font.DefaultStyle.ITALIC -> Typeface.BOLD_ITALIC
			}
	}
}
