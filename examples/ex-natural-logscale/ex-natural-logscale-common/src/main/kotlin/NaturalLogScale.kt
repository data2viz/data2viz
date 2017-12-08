package io.data2viz.examples.naturalLogScale

import io.data2viz.axis.*
import io.data2viz.color.colors
import io.data2viz.core.Point
import io.data2viz.scale.scaleLinear
import io.data2viz.scale.scaleLog
import io.data2viz.viz.*
import kotlin.math.ln
import kotlin.math.round

var superscript = "⁰¹²³⁴⁵⁶⁷⁸⁹"

val margins = Margin(40.5, 40.5, 50.5, 60.5)

val width = 960.0 - margins.hMargins
val height = 500.0 - margins.vMargins

val x = scaleLinear() {
    domain = listOf(.0, 100.0)
    range = listOf(.0, width)
}

val y = scaleLog(kotlin.math.E) {
    domain = listOf(kotlin.math.exp(0.0), kotlin.math.exp(9.0))
    range = listOf(height, .0)
}

val points = (0..100).map { i -> with(i.toDouble()) { Point(this, this * this + this + 1) } }


fun VizContext.naturalLogScale() {

    transform {
        translate(x = margins.right, y = margins.top)
    }

    group {
        transform {
            translate(x = -10.0)
        }
        axis(Orient.LEFT, y) {
            tickFormat = { "e${superscript[round(ln(it)).toInt()]}" }
        }
    }

    group {
        transform {
            translate(y = height + 10.0)
        }
        axis(Orient.BOTTOM, x)
    }

    group {
        path {
            fill = null
            stroke = colors.steelblue
            strokeWidth = 1.5

            moveTo(x(points[0].x), y(points[0].y))
            (1..100).forEach {
                lineTo(x(points[it].x), y(points[it].y))
            }
        }
    }
}

