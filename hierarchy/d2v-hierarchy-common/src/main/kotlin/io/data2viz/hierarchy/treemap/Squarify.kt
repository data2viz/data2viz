package io.data2viz.hierarchy.treemap

import io.data2viz.hierarchy.Node
import io.data2viz.hierarchy.Row
import kotlin.math.max
import kotlin.math.sqrt

val phi = (1 + sqrt(5.0)) / 2

fun treemapSquarify(parent: Node<*>, x0:Double, y0:Double, x1:Double, y1:Double) {
    squarifyRatio(phi, parent, x0, y0, x1, y1);
}

private fun squarifyRatio(ratio: Double, parent: Node<*>, x0: Double, y0: Double, x1: Double, y1: Double): MutableList<Row> {

    val rows = mutableListOf<Row>()
    val nodes = parent.children

    var newx = x0
    var newY = y0
    var i0 = 0
    var i1 = 0
    val size = nodes.size
    var dx: Double
    var dy: Double
    var value = parent.value!!          // should have been checked that root.sum() has been called and values are not null
    var sumValue:Double

    while (i0 < size) {
        dx = x1 - newx
        dy = y1 - newY

        // Find the next non-empty node.
        do sumValue = nodes[i1++].value!! while (sumValue == .0 && i1 < size)

        var minValue = sumValue
        var maxValue = sumValue
        val alpha = max(dy / dx, dx / dy) / (value * ratio)

        var beta = sumValue * sumValue * alpha;
        var minRatio = max(maxValue / beta, beta / minValue);

        // Keep adding nodes while the aspect ratio maintains or improves.
        while(i1 < size) {
            val nodeValue = nodes[i1].value!!
            sumValue += nodeValue
            if (nodeValue < minValue) minValue = nodeValue;
            if (nodeValue > maxValue) maxValue = nodeValue;
            beta = sumValue * sumValue * alpha;
            val newRatio = max(maxValue / beta, beta / minValue)
            if (newRatio > minRatio) sumValue -= nodeValue
            else minRatio = newRatio
            i1++
        }

        // Position and record the row orientation.
        val row = Row(sumValue, dx < dy, nodes.slice(IntRange(i0, i1)))
        rows.add(row)
        if (row.dice) {
            if (value != .0) newY += dy * sumValue / value
            treemapDice(row, newx, newY, x1, if (value != .0) newY else y1)
        } else {
            if (value != .0) newx += dx * sumValue / value
            treemapSlice(row, newx, newY, if (value != .0) newx else x1, y1)
        }
        value -= sumValue
        i0 = i1
    }

    return rows
}

/*treemapSquarify.ratio = function(x) {
    return custom((x = +x) > 1 ? x : 1);
}*/