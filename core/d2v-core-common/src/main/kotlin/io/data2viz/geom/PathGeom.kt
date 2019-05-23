package io.data2viz.geom

import io.data2viz.math.EPSILON
import io.data2viz.math.TAU
import io.data2viz.math.TAU_EPSILON
import kotlin.math.*

/**
 * PathGeom is the base implementation of path that contains only the geometry of the path (without any
 */
class PathGeom : Path {

    val commands = mutableListOf<PathCommand>()

    fun clearPath() {
        commands.clear()
    }

    override fun moveTo(x: Double, y: Double) {
        commands += MoveTo(x, y)
    }

    override fun lineTo(x: Double, y: Double) {
        commands += LineTo(x, y)
    }

    override fun closePath() {
        commands += ClosePath()
    }

    override fun quadraticCurveTo(cpx: Double, cpy: Double, x: Double, y: Double) {
        commands += QuadraticCurveTo(cpx, cpy, x, y)
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
        commands += RectCmd(x, y, w, h)
    }
}


interface PathCommand {
    val x: Double
    val y: Double
}

data class MoveTo(override val x: Double, override val y: Double) : PathCommand
data class LineTo(override val x: Double, override val y: Double) : PathCommand
data class RectCmd(override val x: Double, override val y: Double, val w: Double, val h: Double) : PathCommand
data class QuadraticCurveTo(val cpx: Double, val cpy: Double, override val x: Double, override val y: Double) :
    PathCommand

data class BezierCurveTo(
    val cpx1: Double,
    val cpy1: Double,
    val cpx2: Double,
    val cpy2: Double,
    override val x: Double,
    override val y: Double
) :
    PathCommand

data class Arc(
    val centerX: Double,
    val centerY: Double,
    val radius: Double,
    val startAngle: Double,
    val endAngle: Double,
    val counterClockWise: Boolean
) : PathCommand {
    override val x: Double
        get() = TODO("not implemented")
    override val y: Double
        get() = TODO("not implemented")
}

data class ArcTo(
    val fromX: Double,
    val fromY: Double,
    override val x: Double,
    override val y: Double,
    val radius: Double
) :
    PathCommand

class ClosePath : PathCommand {
    override val x: Double
        get() = .0
    override val y: Double
        get() = .0
}


/**
 * Transform a Path into a SVG Path by creating the SVG String that represents
 * this path.
 */
val PathGeom.svgPath: String
    get() {
        var tempX0 = 0.0
        var tempY0 = 0.0
        var tempX1: Double? = null
        var tempY1: Double? = null
        val sb = StringBuilder()

        commands.forEach { cmd ->
            when (cmd) {
                is MoveTo -> {
                    tempX0 = cmd.x
                    tempY0 = cmd.y
                    tempX1 = cmd.x
                    tempY1 = cmd.y
                    sb.append("M${cmd.x},${cmd.y}")
                }

                is LineTo -> {
                    tempX1 = cmd.x
                    tempY1 = cmd.y
                    sb.append("L${cmd.x},${cmd.y}")
                }

                is ClosePath -> {
                    if (tempX1 != null) {
                        tempX1 = tempX0
                        tempY1 = tempY0
                        sb.append("Z")
                    }
                }

                is QuadraticCurveTo -> {
                    tempX1 = cmd.x
                    tempY1 = cmd.y
                    sb.append("Q${cmd.cpx},${cmd.cpy},${cmd.x},${cmd.y}")
                }

                is RectCmd -> {
                    tempX0 = cmd.x
                    tempX1 = cmd.x
                    tempY0 = cmd.y
                    tempY1 = cmd.y
                    sb.append("M${cmd.x},${cmd.y}h${cmd.w}v${cmd.h}h${-cmd.w}Z")
                }

                is BezierCurveTo -> {
                    tempX1 = cmd.x
                    tempY1 = cmd.y
                    sb.append("C${cmd.cpx1},${cmd.cpy1},${cmd.cpx2},${cmd.cpy2},${cmd.x},${cmd.y}")

                }

                is ArcTo -> {
                    val X0 = tempX1 ?: .0
                    val Y0 = tempY1 ?: .0

                    val x21 = cmd.x - cmd.fromX
                    val y21 = cmd.y - cmd.fromY
                    val x01 = X0 - cmd.fromX
                    val y01 = Y0 - cmd.fromY
                    val l01_2 = x01 * x01 + y01 * y01

                    with(tempX1) {
                        //path is empty, introduce private function?
                        if (this == null) {
                            // Is this path empty? Move to (x1,y1).
                            tempX1 = cmd.fromX
                            tempY1 = cmd.fromY
                            sb.append("M${cmd.fromX},${cmd.fromY}")
                        }
                        // Or, is (x1,y1) coincident with (x0,y0)? Do nothing.
                        else if (l01_2 <= EPSILON) {
                        }

                        // Or, are (x0,y0), (x1,y1) and (x2,y2) collinear?
                        // Equivalently, is (x1,y1) coincident with (x2,y2)?
                        // Or, is the radius zero? Line to (x1,y1).
                        else if (abs(y01 * x21 - y21 * x01) <= EPSILON || cmd.radius == .0) {
                            tempX1 = cmd.fromX
                            tempY1 = cmd.fromY
                            sb.append("L${cmd.fromX},${cmd.fromY}")
                        }

                        // Otherwise, draw an arc!
                        else {
                            val x20 = cmd.x - X0
                            val y20 = cmd.y - Y0
                            val l21_2 = x21 * x21 + y21 * y21
                            val l20_2 = x20 * x20 + y20 * y20
                            val l21 = sqrt(l21_2)
                            val l01 = sqrt(l01_2)
                            val l = cmd.radius * tan((PI - acos((l21_2 + l01_2 - l20_2) / (2 * l21 * l01))) / 2)
                            val t01 = l / l01
                            val t21 = l / l21

                            // If the start tangent is not coincident with (x0,y0), line to.
                            if (abs(t01 - 1) > EPSILON) {
                                sb.append("L${cmd.fromX + t01 * x01},${cmd.fromY + t01 * y01}")
                            }

                            tempX1 = cmd.fromX + t21 * x21
                            tempY1 = cmd.fromY + t21 * y21
                            val yes = if (y01 * x20 > x01 * y20) 1 else 0
                            sb.append("A${cmd.radius},${cmd.radius},0,0,$yes,${tempX1},${tempY1}")
                        }
                    }
                }

                is Arc -> {
                    val dx = cmd.radius * cos(cmd.startAngle)
                    val dy = cmd.radius * sin(cmd.startAngle)
                    val x0 = cmd.centerX + dx
                    val y0 = cmd.centerY + dy
                    val cw = if (cmd.counterClockWise) 0 else 1
                    var da = if (cmd.counterClockWise) cmd.startAngle - cmd.endAngle else cmd.endAngle - cmd.startAngle

                    with(tempX1) {

                        //path is empty, introduce private function?
                        if (this == null) {
                            sb.append("M$x0,$y0")
                        } else if (abs(this.toDouble() - x0) > EPSILON || abs(
                                tempY1!!.toDouble() - y0
                            ) > EPSILON
                        ) {
                            sb.append("L$x0,$y0")
                        } else {
                        }
                    }

                    if (cmd.radius < EPSILON) return@forEach

                    if (da < 0) da = da % TAU + TAU

                    //complete circle
                    if (da > TAU_EPSILON) {
                        tempX1 = x0
                        tempY1 = y0
                        sb.append("A${cmd.radius},${cmd.radius},0,1,$cw,${cmd.centerX - dx},${cmd.centerY - dy}A${cmd.radius},${cmd.radius},0,1,$cw,$x0,$y0")
                    }

                    // Is this arc non-empty? Draw an arc!
                    else if (da > EPSILON) {
                        tempX1 = cmd.centerX + cmd.radius * cos(cmd.endAngle)
                        tempY1 = cmd.centerY + cmd.radius * sin(cmd.endAngle)
                        sb.append("A${cmd.radius},${cmd.radius},0,${if (da >= PI) 1 else 0},$cw,$tempX1,$tempY1")
                    }
                }
            }
        }
        return sb.toString()
    }
