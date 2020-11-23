/*
 * Copyright (c) 2018-2019. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.hierarchy.treemap

import io.data2viz.hierarchy.ParentValued
import io.data2viz.hierarchy.Row
import io.data2viz.hierarchy.TreemapNode
import kotlin.math.max
import kotlin.math.sqrt

internal val phi = (1 + sqrt(5.0)) / 2

/**
 * Implements the squarified treemap algorithm by Bruls et al., which seeks to produce rectangles
 * of a given aspect ratio.
 */
public fun <D> treemapSquarify(parent: ParentValued<TreemapNode<D>>, x0:Double, y0:Double, x1:Double, y1:Double): List<Row<D>> {
    return squarifyRatio(phi, parent as TreemapNode<D>, x0, y0, x1, y1)
}

private fun <D> squarifyRatio(ratio: Double, parent: TreemapNode<D>, x0: Double, y0: Double, x1: Double, y1: Double): List<Row<D>> {

    val rows = mutableListOf<Row<D>>()
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
            if (nodeValue < minValue) minValue = nodeValue
            if (nodeValue > maxValue) maxValue = nodeValue
            beta = sumValue * sumValue * alpha
            val newRatio = max(maxValue / beta, beta / minValue)
            if (newRatio > minRatio) {
                sumValue -= nodeValue
                break
            }
            minRatio = newRatio
            i1++
        }

        // Position and record the row orientation.
        val row = Row(sumValue, dx < dy, nodes.slice(i0 until i1))
        rows.add(row)
        if (row.dice) {
            if (value != .0) {
                val temp = newY + dy * sumValue / value
                treemapDice(row, newx, newY, x1, temp)
                newY = temp
            } else treemapDice(row, newx, newY, x1, y1)
        } else {
            if (value != .0) {
                val temp = newx + dx * sumValue / value
                treemapSlice(row, newx, newY, temp, y1)
                newx = temp
            } else treemapSlice(row, newx, newY, x1, y1)
        }
        value -= sumValue
        i0 = i1
    }

    return rows.toList()
}

/**
 * Specifies the desired aspect ratio of the generated rectangles. The ratio must be specified as a number greater
 * than or equal to one.
 * Note that the orientation of the generated rectangles (tall or wide) is not implied by the ratio;
 * for example, a ratio of two will attempt to produce a mixture of rectangles whose width:height ratio
 * is either 2:1 or 1:2. (However, you can approximately achieve this result by generating a square treemap
 * at different dimensions, and then stretching the treemap to the desired aspect ratio.)
 * Furthermore, the specified ratio is merely a hint to the tiling algorithm; the rectangles are not guaranteed
 * to have the specified aspect ratio.
 * If not specified, the aspect ratio defaults to the golden ratio, φ = (1 + sqrt(5)) / 2, per Kong et al.
 */
/*treemapSquarify.ratio = function(x) {
    return custom((x = +x) > 1 ? x : 1);
}*/