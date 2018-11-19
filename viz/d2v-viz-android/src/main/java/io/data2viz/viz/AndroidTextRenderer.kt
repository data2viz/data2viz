package io.data2viz.viz

import android.graphics.*

internal fun TextNode.render(renderer: AndroidCanvasRenderer) {
	val canvas = renderer.canvas
	paint.textAlign = style.anchor.android
	paint.textSize = fontSize.toFloat()

	paint.typeface = Typeface.create(fontFamily.name, getAndroidStyle(fontWeight, fontStyle))

	val dy = baseline.dy(paint.fontMetrics)

	style.fill?.let {
		paint.style = Paint.Style.FILL
		it.updatePaint(paint)
		canvas.drawText(
			textContent,
			x.toFloat(),
			y.toFloat() - dy,
			paint
		)
	}
	style.stroke?.let {
		paint.style = Paint.Style.STROKE
		it.updatePaint(paint)
		canvas.drawText(
			textContent,
			x.toFloat(),
			y.toFloat() - dy,
			paint
		)
	}
}


/**
 * The y distance to move the text from baseline in order to respect the wanted
 * alignment.
 *
 * The middle alignment is an approximation.
 * TODO resolve by implementing DV-105
 */
internal fun TextAlignmentBaseline.dy(fontMetrics: Paint.FontMetrics): Float =
	when(this@dy){
		TextAlignmentBaseline.BASELINE  -> 0F
		TextAlignmentBaseline.HANGING   -> fontMetrics.top
		TextAlignmentBaseline.MIDDLE    -> fontMetrics.ascent * .4f
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
