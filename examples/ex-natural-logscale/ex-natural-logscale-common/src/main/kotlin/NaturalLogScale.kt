package io.data2viz.examples.naturalLogScale

import io.data2viz.axis.*
import io.data2viz.color.*
import io.data2viz.core.*
import io.data2viz.scale.*
import io.data2viz.viz.*
import kotlin.math.ln
import kotlin.math.round

var superscript = "⁰¹²³⁴⁵⁶⁷⁸⁹"

val margins = Margin(40.5, 40.5, 50.5, 60.5)

val width = 960.0 - margins.hMargins
val height = 500.0 - margins.vMargins


// linear scale domain 0..100 is mapped to 0..width
val x = scaleLinear {
    domain = listOf(.0, 100.0)
    range = listOf(.0, width)
}

// log scale
val y = scaleLog(kotlin.math.E) {
    domain = listOf(kotlin.math.exp(0.0), kotlin.math.exp(9.0))
    range = listOf(height, .0) // <- y is mapped in the reverse order (in SVG, javafx (0,0) is top left.
}

// the mathematical function
val functionToPlot = { x:Double -> x * x + x + 1}

//101  points to define the curve
val points = (0..100).map { i -> Point(i.toDouble(), functionToPlot(i.toDouble()))}


fun VizContext.naturalLogScale() {

    transform {
        translate(x = margins.right, y = margins.top)
    }

    group {
        transform {
            translate(x = -10.0)
        }
        axis(Orient.LEFT, y) {
            //we use our own formatter in order to provide exponents (ex: e¹)
            tickFormat = { "e${superscript[round(ln(it)).toInt()]}" }
        }
    }

    group {
        transform {
            translate(y = height + 10.0)
        }
        //default axis. Labels are provided by the x scale. 
        axis(Orient.BOTTOM, x)
    }

    group {
        path { //the curve is rendered with a path.
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

