package io.data2viz.path

import org.w3c.dom.CanvasPath


fun path(canvas: CanvasPath): PathAdapter = CanvasDrawContext(canvas)


class CanvasDrawContext(val canvas: CanvasPath): PathAdapter {
    override fun moveTo(x: Double, y: Double) { canvas.moveTo(x,y) }
    override fun lineTo(x: Double, y: Double) { canvas.lineTo(x,y) }
    override fun closePath() { canvas.closePath() }
    override fun quadraticCurveTo(cpx: Double, cpy: Double, x: Double, y: Double) { canvas.quadraticCurveTo(cpx, cpy, x, y) }
    override fun bezierCurveTo(cpx1: Double, cpy1: Double, cpx2: Double, cpy2: Double, x: Double, y: Double) { canvas.bezierCurveTo(cpx1, cpy1, cpx2, cpy2,x, y)}
    override fun arcTo(cpx: Double, cpy: Double, x: Double, y: Double, radius: Double) { canvas.arcTo(cpx, cpy, x, y, radius ) }
    override fun arc(centerX: Double, centerY: Double, radius: Double, startAngle: Double, endAngle: Double, counterClockWise: Boolean) { canvas.arc(centerX, centerY, radius, startAngle, endAngle, counterClockWise) }
    override fun rect(x: Double, y: Double, w: Double, h: Double) { canvas.rect(x, y, w, h) }
}
