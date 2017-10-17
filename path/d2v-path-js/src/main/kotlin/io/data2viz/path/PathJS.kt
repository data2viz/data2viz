package io.data2viz.path

import org.w3c.dom.CanvasPath
import kotlin.math.*


fun path(canvas: CanvasPath): PathAdapter = CanvasDrawContext(canvas)


class CanvasDrawContext(val canvas: CanvasPath): PathAdapter {
    override fun moveTo(x: Double, y: Double) { canvas.moveTo(x,y) }
    override fun lineTo(x: Double, y: Double) { canvas.lineTo(x,y) }
    override fun closePath() { canvas.closePath() }
    override fun quadraticCurveTo(x1: Double, y1: Double, x: Double, y: Double) { canvas.quadraticCurveTo(x1, y1, x, y) }
    override fun bezierCurveTo(x1: Double, y1: Double, x2: Double, y2: Double, x: Double, y: Double) { canvas.bezierCurveTo(x1, y1. toDouble(), x2, y2,x, y)}
    override fun arcTo(fromX: Double, fromY: Double, toX: Double, toY: Double, radius: Double) { canvas.arcTo(fromX, fromY, toX, toY, radius ) }
    override fun arc(centerX: Double, centerY: Double, radius: Double, startAngle: Double, endAngle: Double, counterClockWise: Boolean) { canvas.arc(centerX, centerY, radius, startAngle, endAngle, counterClockWise) }
    override fun rect(x: Double, y: Double, w: Double, h: Double) { canvas.rect(x, y, w, h) }
}
