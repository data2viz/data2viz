package io.data2viz.path


class Path : PathAdapter {

    val commands = mutableListOf<PathCommand>()

    override fun moveTo(x: Double, y: Double)                       { commands += MoveTo(x, y) }
    override fun lineTo(x: Double, y: Double)                       { commands += LineTo(x, y) }
    override fun closePath()                                        { commands += ClosePath() }
    override fun rect(x: Double, y: Double, w: Double, h: Double)   { commands += Rect(x, y, w, h) }


    override fun quadraticCurveTo(x1: Double, y1: Double, x: Double, y: Double) {
        commands += QuadraticCurveTo(x1, y1, x, y)
    }

    override fun bezierCurveTo(x1: Double, y1: Double, x2: Double, y2: Double, x: Double, y: Double) {
        commands += BezierCurveTo(x1, y1, x2, y2, x, y)
    }

    override fun arcTo(fromX: Double, fromY: Double, toX: Double, toY: Double, radius: Double) {
        require(radius >= .0) { "Negative radius:$radius" }
        commands += ArcTo(fromX, fromY, toX, toY, radius)
    }

    override fun arc(
        centerX: Double,
        centerY: Double,
        radius: Double,
        startAngle: Double,
        endAngle: Double,
        counterClockWise: Boolean
    ) {
        require(radius >= .0) { "Negative radius:$radius" }
        commands += Arc(centerX, centerY, radius, startAngle, endAngle, counterClockWise)
    }
}

interface PathCommand

data class MoveTo(val x: Double, val y: Double) : PathCommand
data class LineTo(val x: Double, val y: Double) : PathCommand
data class Rect(val x: Double, val y: Double, val w: Double, val h: Double) : PathCommand
data class QuadraticCurveTo(val x1: Double, val y1: Double, val x: Double, val y: Double) : PathCommand
data class BezierCurveTo(val x1: Double, val y1: Double, val x2: Double, val y2: Double, val x: Double, val y: Double) :
    PathCommand

data class Arc(
    val centerX: Double,
    val centerY: Double,
    val radius: Double,
    val startAngle: Double,
    val endAngle: Double,
    val counterClockWise: Boolean
) : PathCommand

data class ArcTo(val fromX: Double, val fromY: Double, val toX: Double, val toY: Double, val radius: Double) :
    PathCommand

class ClosePath : PathCommand
