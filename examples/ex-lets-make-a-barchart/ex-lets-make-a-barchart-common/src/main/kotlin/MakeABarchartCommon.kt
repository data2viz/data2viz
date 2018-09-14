package io.data2viz.examples.letsMakeABarchart

import io.data2viz.color.colors
import io.data2viz.scale.scales
import io.data2viz.viz.TextAlignmentBaseline
import io.data2viz.viz.TextAnchor
import io.data2viz.viz.Viz

data class NameValue(val name: String, val value: Double)

val myData = listOf<NameValue>(
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

    val xScale = scales.continuous.linear {
        domain = listOf(.0, myData.maxBy { it.value }!!.value)
        range = listOf(.0, vizWidth)
    }

    with(root) {
        myData.forEachIndexed { index, nameValue ->
            group {
                transform { translate( y = index * barHeight) }
                rect {
                    width = xScale(nameValue.value)
                    height = barHeight - 1.0
                    fill = colors.steelblue
                }
                text {
                    x = xScale(nameValue.value) - 3.0
                    y = barHeight / 2.0
                    textContent = nameValue.value.toString()
                    fill = colors.white
                    anchor = TextAnchor.END
                    baseline = TextAlignmentBaseline.MIDDLE
                }
            }
        }
    }
}