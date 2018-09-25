package io.data2viz.examples.streamGraph

import io.data2viz.color.EncodedColors
import io.data2viz.color.EncodedColors.Companion.category20b
import io.data2viz.math.random
import io.data2viz.path.PathAdapter
import io.data2viz.scale.scales
import io.data2viz.shape.Curve
import io.data2viz.shape.area
import io.data2viz.shape.curves
import io.data2viz.shape.stack.StackOffset
import io.data2viz.shape.stack.StackOrder
import io.data2viz.shape.stack.StackSpace
import io.data2viz.shape.stack.stack
import io.data2viz.viz.Margins
import io.data2viz.viz.Viz
import io.data2viz.viz.viz


// Graphical bounds
val margins = Margins(50.0, .0)
val width = 1600.0 - margins.hMargins
val height = 450.0


// Domain object
data class Score(
        val index: Int,
        val lang1: Double = randomScore(1),
        val lang2: Double = randomScore(2),
        val lang3: Double = randomScore(3),
        val lang4: Double = randomScore(1),
        val lang5: Double = randomScore(4),
        val lang6: Double = randomScore(6),
        val lang7: Double = randomScore(6),
        val lang8: Double = randomScore(3),
        val lang9: Double = randomScore(15),
        val lang10: Double = randomScore(2)
)


// Visual configuration object
data class VizConfig(
        var curve: (PathAdapter) -> Curve = curves.basis,
        var offset: StackOffset = StackOffset.WIGGLE,
        var order: StackOrder = StackOrder.NONE,
        var colors: EncodedColors = category20b
)
val vizConfig = VizConfig()


// Random data generation
fun randomScore(size: Int): Double {
    var rand = random()
    rand *= rand * rand * size
    return if (rand < .1) .0 else rand
}
val data = (0..30).map { Score(it) }


val curveOptions = listOf(
        "Basis" to curves.basis,
        "Linear" to curves.linear,
        "Natural" to curves.natural,
        "Catmull-Rom" to curves.catmullRom,
        "Cardinal" to curves.cardinal,
        "Step" to curves.step
)

val offsetOptions = listOf(
        "None" to StackOffset.NONE,
        "Expand" to StackOffset.EXPAND,
        "Diverging" to StackOffset.DIVERGING,
        "Silhouette" to StackOffset.SILHOUETTE,
        "Wiggle" to StackOffset.WIGGLE
)


val orderOptions = listOf(
        "None" to StackOrder.NONE,
        "Ascending" to StackOrder.ASCENDING,
        "Descending" to StackOrder.DESCENDING,
        "Reverse" to StackOrder.REVERSE,
        "Inside-out" to StackOrder.INSIDEOUT
)

// Main function
fun streamGraph(): Viz = viz {
    group {
        // Add margins for visualization
        transform {
            translate(x = margins.left, y = margins.top)
        }


        // A streamgraph is basically just a "stack layout"
        // Here we plug StackOffset and StackOrder to our VizConfig choices,
        // and define the series (Double values) we want to stack
        val stackLayout = stack<Score> {
            offset = vizConfig.offset
            order = vizConfig.order
            series = {
                arrayOf(it.lang1, it.lang2, it.lang3, it.lang4, it.lang5, it.lang6, it.lang7, it.lang8, it.lang9, it.lang10)
            }
        }
        // Then we just need to pass our data for the stack to  to pre-compute curves positions
        val stack = stackLayout.stack(data)


        // Search for min and max of stackedValues, we'll use it for defining the extent of our domain (Y Scale)
        val max = (stack.map { it.stackedValues.map { it.to }.max()!! }).max()!!
        val min = (stack.map { it.stackedValues.map { it.from }.min()!! }).min()!!

        // The ordinate scale is a standard linear scale
        val yScale = scales.continuous.linear {
            domain = listOf(min, max)
            range = listOf(height, .0)
        }


        // The abciss scale is also linear scale (could have been a timeScale)
        val xScale = scales.continuous.linear {
            domain = listOf(.0, (data.size - 1).toDouble())
            range = listOf(.0, width)
        }


        // PathArea Generator for streams : X is the index of the serie, Y is defined by our stack
        // We plug the rendering curve to our Viz Config
        val area = area<StackSpace<Score>> {
            xBaseline = { xScale(it.paramIndex.toDouble()) }
            xTopline = { xScale(it.paramIndex.toDouble()) }
            yBaseline = { yScale(it.from) }
            yTopline = { yScale(it.to) }
            curve = vizConfig.curve
        }


        // Finally, we just need to render each serie as an area
        stack.forEach { serieStream ->
            val stackData = serieStream.stackedValues
            group {
                path {
                    area.render(stackData, this)
                    val color = vizConfig.colors.color(serieStream.index / stack.size.toDouble())
                    style.fill = color
                    style.stroke = color
                }
            }
        }

        transform {
            translate(x = .0, y = .0)
        }
    }
}