package io.data2viz.path


open class Path : PathAdapter {

    val commands = mutableListOf<PathCommand>()

    fun clearPath(){
        commands.clear()
    }

    override fun moveTo(x: Double, y: Double) { commands += MoveTo(x, y) }
    override fun lineTo(x: Double, y: Double) { commands += LineTo(x, y) }
    override fun closePath() { commands += ClosePath() }

    override fun quadraticCurveTo(cpx: Double, cpy: Double, x: Double, y: Double) {
        commands += QuadraticCurveTo(cpx,cpy,x,y)
    }

    override fun bezierCurveTo(cpx1: Double, cpy1: Double, cpx2: Double, cpy2: Double, x: Double, y: Double) {
        commands += BezierCurveTo(cpx1, cpy1, cpx2, cpy2, x, y)
    }

    override fun arcTo(cpx: Double, cpy: Double, x: Double, y: Double, radius: Double) {
        require(radius >= .0) { "Negative radius:$radius" }
        commands += ArcTo(cpx, cpy, x, y, radius)
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

    override fun rect(x: Double, y: Double, w: Double, h: Double) {
        commands += Rect(x,y, w,h)
    }

}

interface PathCommand

data class MoveTo(val x: Double, val y: Double) : PathCommand
data class LineTo(val x: Double, val y: Double) : PathCommand
data class Rect(val x: Double, val y: Double, val w: Double, val h: Double) : PathCommand
data class QuadraticCurveTo(val cpx: Double, val cpy: Double, val x: Double, val y: Double) : PathCommand
data class BezierCurveTo(val cpx1: Double, val cpy1: Double, val cpx2: Double, val cpy2: Double, val x: Double, val y: Double) :
    PathCommand

class Arc(val centerX: Double, val centerY: Double, val radius: Double, val startAngle: Double, val endAngle: Double, val counterClockWise: Boolean): PathCommand

data class ArcTo(val fromX: Double, val fromY: Double, val x: Double, val y: Double, val radius: Double) :
    PathCommand

class ClosePath: PathCommand