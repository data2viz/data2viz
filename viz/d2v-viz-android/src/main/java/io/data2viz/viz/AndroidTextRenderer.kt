package io.data2viz.viz

import android.graphics.*

internal fun TextNode.render(renderer: AndroidCanvasRenderer) {
	val canvas = renderer.canvas
	with(renderer) {
		paint.textAlign = anchor.android
		paint.textSize = fontSize.dp

		paint.typeface = Typeface.create(fontFamily.name, getAndroidStyle(fontWeight, fontStyle))

		val dy = baseline.dy(renderer, paint.fontMetrics)

		fill?.let {
			paint.style = Paint.Style.FILL
			it.updatePaint(paint, renderer)
			canvas.drawText(
				textContent,
				x.dp,
				y.dp - dy,
				paint )
		}
		stroke?.let {
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
internal fun TextAlignmentBaseline.dy(renderer: AndroidCanvasRenderer, fontMetrics: Paint.FontMetrics): Float =
	with(renderer){
		when(this@dy){
			TextAlignmentBaseline.BASELINE  -> 0F
			TextAlignmentBaseline.HANGING   -> fontMetrics.top
			TextAlignmentBaseline.MIDDLE    -> fontMetrics.ascent * .4f
		}
	}

internal val TextAnchor.android: Paint.Align
	get() = when(this){
		TextAnchor.START    -> Paint.Align.LEFT
		TextAnchor.END      -> Paint.Align.RIGHT
		TextAnchor.MIDDLE   -> Paint.Align.CENTER
	}


//TODO nba refactor code: make access to the android text Font more reachable
internal fun getAndroidStyle(fontWeight: FontWeight, fontStyle: FontPosture): Int {
	return when(fontWeight) {
		FontWeight.NORMAL ->
			when(fontStyle) {
				FontPosture.NORMAL -> Typeface.NORMAL
				FontPosture.ITALIC -> Typeface.ITALIC
			}

		FontWeight.BOLD ->
			when(fontStyle) {
				FontPosture.NORMAL -> Typeface.BOLD
				FontPosture.ITALIC -> Typeface.BOLD_ITALIC
			}
	}
}
