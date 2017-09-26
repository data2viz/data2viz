package io.data2viz.path

import org.w3c.dom.CanvasPath
import kotlin.math.*


fun path(canvas: CanvasPath): PathAdapter = CanvasDrawContext(canvas)


class CanvasDrawContext(val canvas: CanvasPath): PathAdapter {
    override fun moveTo(x: Number, y: Number) { canvas.moveTo(x.toDouble(), y as Double) }
    override fun lineTo(x: Number, y: Number) { canvas.lineTo(x.toDouble(),y.toDouble()) }
    override fun closePath() { canvas.closePath() }
    override fun quadraticCurveTo(x1: Number, y1: Number, x: Number, y: Number) { canvas.quadraticCurveTo(x1.toDouble(), y1.toDouble(), x.toDouble(), y.toDouble()) }
    override fun bezierCurveTo(x1: Number, y1: Number, x2: Number, y2: Number, x: Number, y: Number) { canvas.bezierCurveTo(x1.toDouble(), y1. toDouble(), x2.toDouble(), y2.toDouble(),x.toDouble(), y.toDouble())}
    override fun arcTo(fromX: Number, fromY: Number, toX: Number, toY: Number, radius: Number) { canvas.arcTo(fromX.toDouble(), fromY.toDouble(), toX.toDouble(), toY.toDouble(), radius.toDouble() ) }
    override fun arc(centerX: Number, centerY: Number, radius: Number, startAngle: Number, endAngle: Number, counterClockWise: Boolean) { canvas.arc(centerX.toDouble(), centerY.toDouble(), radius.toDouble(), startAngle.toDouble(), endAngle.toDouble(), counterClockWise) }
    override fun rect(x: Number, y: Number, w: Number, h: Number) { canvas.rect(x.toDouble(), y.toDouble(), w.toDouble(), h.toDouble()) }
}
