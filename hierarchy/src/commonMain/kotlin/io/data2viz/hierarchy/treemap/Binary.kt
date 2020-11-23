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
import io.data2viz.hierarchy.TreemapNode

public fun <D> treemapBinary(parent: ParentValued<TreemapNode<D>>, x0: Double, y0: Double, x1: Double, y1: Double): Unit = TreemapBinary<D>().binary(parent, x0, y0, x1, y1)

/**
 * Recursively partitions the specified nodes into an approximately-balanced binary tree, choosing horizontal
 * partitioning for wide rectangles and vertical partitioning for tall rectangles.
 */
public class TreemapBinary<D> {

    public var nodes: MutableList<ParentValued<TreemapNode<D>>> = mutableListOf()
    public var sums: MutableList<Double> = mutableListOf()

    public fun binary(parent: ParentValued<TreemapNode<D>>, x0: Double, y0: Double, x1: Double, y1: Double) {

        nodes = parent.children.toMutableList()
        val size = nodes.size
        sums = MutableList(size + 1, { .0 })
        var sum = .0

        for (i in 0 until size) {
            sum += nodes[i].value!!
            sums[i + 1] = sum
        }

        partition(0, size, parent.value!!, x0, y0, x1, y1)
    }

    private fun partition(i: Int, j: Int, value: Double, x0: Double, y0: Double, x1: Double, y1: Double) {
        if (i >= j - 1) {
            val node = nodes[i] as TreemapNode
            node.x0 = x0
            node.y0 = y0
            node.x1 = x1
            node.y1 = y1
            return
        }

        val valueOffset = sums[i]
        val valueTarget = (value / 2) + valueOffset
        var k = i + 1
        var hi = j - 1

        while (k < hi) {
            val mid = (k + hi).ushr(1)     // TODO raw conversion from JS : k + hi >>> 1... need explanation (unsigned right shift .... WHY ?)
            if (sums[mid] < valueTarget) k = mid + 1
            else hi = mid
        }

        if ((valueTarget - sums[k - 1]) < (sums[k] - valueTarget) && i + 1 < k) --k

        val valueLeft = sums[k] - valueOffset
        val valueRight = value - valueLeft

        if ((x1 - x0) > (y1 - y0)) {
            val xk = (x0 * valueRight + x1 * valueLeft) / value
            partition(i, k, valueLeft, x0, y0, xk, y1)
            partition(k, j, valueRight, xk, y0, x1, y1)
        } else {
            val yk = (y0 * valueRight + y1 * valueLeft) / value
            partition(i, k, valueLeft, x0, y0, x1, yk)
            partition(k, j, valueRight, x0, yk, x1, y1)
        }
    }
}