package io.data2viz.examples.letsMakeABarchart

import io.data2viz.color.Colors
import io.data2viz.geom.Size
import io.data2viz.scale.Scales
import io.data2viz.viz.TextAlignmentBaseline
import io.data2viz.viz.TextAnchor
import io.data2viz.viz.Viz

data class NameValue(val name: String, val value: Double)

val myData = listOf(
    NameValue("Locke", 4.0),
    NameValue("Reyes", 8.0),
    NameValue("Ford", 15.0),
    NameValue("Jarrah", 16.0),
    NameValue("Shephard", 23.0),
    NameValue("Kwon", 42.0)
)

fun barchartViz() = Viz().apply {


    val vizWidth = 420.0
    val barHeight = 20.0

    size = Size(vizWidth, myData.size * barHeight)

    val xScale = Scales.continuous.linear {
        domain = listOf(.0, myData.maxBy { it.value }!!.value)
        range = listOf(.0, vizWidth)
    }

    myData.forEachIndexed { index, nameValue ->
        group {
            transform { translate(y = index * barHeight) }
            rect {
                width = xScale(nameValue.value)
                height = barHeight - 1.0
                style.fill = Colors.Web.steelblue
            }
            text {
                x = xScale(nameValue.value) - 3.0
                y = barHeight / 2.0
                textContent = nameValue.value.toString()
                style.fill = Colors.Web.white
                style.anchor = TextAnchor.END
                style.baseline = TextAlignmentBaseline.MIDDLE
            }
        }
    }
}