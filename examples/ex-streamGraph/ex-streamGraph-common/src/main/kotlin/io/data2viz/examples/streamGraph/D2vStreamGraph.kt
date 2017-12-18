package io.data2viz.examples.streamGraph

import io.data2viz.color.EncodedColors.Companion.category20b
import io.data2viz.core.random
import io.data2viz.scale.scales
import io.data2viz.shape.area
import io.data2viz.shape.curves
import io.data2viz.shape.stack.StackOffsets
import io.data2viz.shape.stack.StackOrders
import io.data2viz.shape.stack.StackSpace
import io.data2viz.shape.stack.stack
import io.data2viz.viz.Margins
import io.data2viz.viz.VizContext

val legendLineHeight = 30.0

val margins = Margins(50.0, bottom = 4 * legendLineHeight + 40.0)

val width = 1600.0 - margins.hMargins
val height = 450.0

val colorList = category20b

data class score(
        val index: Int,
        val lang1: Double,
        val lang2: Double,
        val lang3: Double,
        val lang4: Double,
        val lang5: Double,
        val lang6: Double,
        val lang7: Double,
        val lang8: Double,
        val lang9: Double,
        val lang10: Double
)

fun randomScore(): Double {
    var rand = random()
    rand *= rand * rand
    return if (rand < .1) .0 else rand
}

val data = (0..30).map {
    score(it, randomScore(), randomScore(), randomScore(), randomScore(), randomScore(), randomScore(), randomScore(), randomScore(), randomScore(), randomScore())
}.toTypedArray()

fun VizContext.streamGraph() {

    transform {
        translate(x = margins.left, y = margins.top)
    }

    // STACK LAYOUT
    val stackLayout = stack<score> {
        offset = StackOffsets.WIGGLE
        order = StackOrders.INSIDEOUT
        series = {
            arrayOf(it.lang1, it.lang2, it.lang3, it.lang4, it.lang5, it.lang6, it.lang7, it.lang8, it.lang9, it.lang10)
        }
    }
    val stack = stackLayout.stack(data)

    val max = (stack.map { it.stackedValues.map { it.to }.max() } as List<Double>).max()
    val min = (stack.map { it.stackedValues.map { it.to }.min() } as List<Double>).min()

    val yScale = scales.continuous.linear {
        domain = listOf(min!!, max!!)
        range = listOf(height, .0)
    }

    val xScale = scales.continuous.linear {
        domain = listOf(.0, (data.size - 1).toDouble())
        range = listOf(.0, width)
    }

    val area = area<StackSpace<score>> {
        x0 = { xScale(it.paramIndex.toDouble()) }
        x1 = { xScale(it.paramIndex.toDouble()) }
        y0 = { yScale(it.from) }
        y1 = { yScale(it.to) }
        curve = curves.basis
    }

    stack.forEach { LOCtypeLayout ->

        val d = LOCtypeLayout.stackedValues.toTypedArray()
        group {
            path {
                area.render(d, this)
                fill = colorList.color(LOCtypeLayout.index / 10.0)
                stroke = colorList.color(LOCtypeLayout.index / 10.0)
            }
        }
    }

}