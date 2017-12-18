package io.data2viz.examples.streamGraph

import io.data2viz.axis.*
import io.data2viz.color.*
import io.data2viz.path.svgPath
import io.data2viz.scale.*
import io.data2viz.shape.area
import io.data2viz.shape.stack.*
import io.data2viz.viz.*
import kotlin.math.absoluteValue

val legendLineHeight = 30.0

val margins = Margins(50.0, bottom = 4 * legendLineHeight + 40.0)

val width = 1600.0 - margins.hMargins
val height = 450.0

//Colors from http://colorbrewer2.org/#type=diverging&scheme=RdBu&n=7
val colorExpected = "#dddddd".color
val colorTest = "#b2182b".color
val colorCommon = "#2166ac".color
val colorJs = "#67a9cf".color
val colorJVM = "#d1e5f0".color
val colorList = listOf(colorCommon, colorJs, colorJVM, colorExpected, colorTest)

fun VizContext.streamGraph() {

    transform {
        translate(x = margins.left, y = margins.top)
    }

    val yScale = scales.continuous.linear {
        domain = listOf(-100.0, 100.0)
        range = listOf(height, .0)
    }
    yScale.nice()

    //val y0 = yScale(0.0)

    //val moduleNames = modules.map { it.name }

    val xScale = scales.continuous.linear {
        domain = listOf(.0, (modules.size - 1).toDouble())
        range = listOf(height, .0)
    }

    axis(Orient.LEFT, scales.continuous.linear {
        domain = yScale.domain
        range = yScale.range
    }) {
        tickFormat = { it.absoluteValue.toInt().toString() }
    }


    // STACK LAYOUT
    val stackLayout = stack<vals> {
        offset = StackOffsets.WIGGLE            // we want to separate tests (counted as negative lines of code) and program code (positive)
        order = StackOrders.NONE                // we don't want to change the order defined by stack.series
        series = {
            arrayOf(it.val1, it.val2, it.val3, it.val4, it.val5, it.val6, it.val7)
        }
    }

    // the stack will give all stacked coordinates so we just need to pass them through our scale.
    // note : the stack is computed/ordered by SERIES (here the different types of LOC) not by module !
    val stack = stackLayout.stack(modules.toTypedArray())

    val area = area<StackSpace<vals>> {
        x1 = { xScale(.0) }
        y1 = { yScale(it.from) }
        x0 = { xScale(.0) }
        y0 = { yScale(.0) }
    }

    legend()

    stack.forEach { LOCtypeLayout ->

        val data = LOCtypeLayout.stackedValues.toTypedArray()
        group {
            path {
                area.render(data, this)
            }
        }

        println(area.render(data, svgPath()).path)
    }

    group {
        transform {
            translate(y = yScale(.0))
        }
        axis(Orient.BOTTOM, xScale)
    }
}

private fun VizContext.legend() {
    group {
        transform { translate(x = 20.0, y = height + 22.0) }
        colorLegend(colorJVM, "Current kotlin jvm LOC", line = 0)
        colorLegend(colorJs, "Current kotlin js LOC", line = 1)
        colorLegend(colorCommon, "Current kotlin common LOC", line = 2)
        colorLegend(colorTest, "Current total test LOC", line = 3)
    }
}

private fun ParentItem.colorLegend(color: Color, legend: String, line: Int = 0) =
        group {
            transform {
                translate(y = line * legendLineHeight)
            }
            rect {
                fill = color
                stroke = colors.black
                height = 20.0
                width = 20.0
            }

            text {
                transform { translate(x = 25.0, y = 15.0) }
                textContent = legend
            }
        }
