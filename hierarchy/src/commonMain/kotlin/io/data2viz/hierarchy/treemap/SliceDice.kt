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

/**
 * Divides the rectangular area specified by x0, y0, x1, y1 vertically according the value of each
 * of the specified node’s children.
 * The children are positioned in order, starting with the top edge (y0) of the given rectangle.
 * If the sum of the children’s values is less than the specified node’s value (i.e., if the specified node
 * has a non-zero internal value), the remaining empty space will be positioned
 * on the bottom edge (y1) of the given rectangle.
 */
public fun <D> treemapSlice(parent: ParentValued<TreemapNode<D>>, x0: Double, y0: Double, x1: Double, y1: Double) {
    var newY = y0
    val nodes = parent.children
    var i = 0
    val n = nodes.size
    val k = if (parent.value != null && parent.value != .0) (y1 - newY) / parent.value!! else .0

    while (i < n) {
        val node = nodes[i] as TreemapNode<*>

        node.x0 = x0
        node.x1 = x1
        node.y0 = newY
        newY += k * node.value!!
        node.y1 = newY
        i++
    }
}

/**
 * Divides the rectangular area specified by x0, y0, x1, y1 horizontally according the value of each
 * of the specified node’s children. The children are positioned in order, starting with the left edge (x0)
 * of the given rectangle.
 * If the sum of the children’s values is less than the specified node’s value (i.e., if the specified node
 * has a non-zero internal value), the remaining empty space will be positioned
 * on the right edge (x1) of the given rectangle.
 */
public fun <D> treemapDice(parent: ParentValued<TreemapNode<D>>, x0: Double, y0: Double, x1: Double, y1: Double) {
    var newX = x0
    val nodes = parent.children
    var i = 0
    val n = nodes.size
    val k = if (parent.value != null && parent.value != .0) (x1 - newX) / parent.value!! else .0

    while (i < n) {
        val node = nodes[i] as TreemapNode<*>

        node.y0 = y0
        node.y1 = y1
        node.x0 = newX
        newX += k * node.value!!
        node.x1 = newX
        i++
    }
}

/**
 * If the specified node has odd depth, delegates to treemapSlice; otherwise delegates to treemapDice.
 */
public fun <D> treemapSliceDice(parent: ParentValued<TreemapNode<D>>, x0: Double, y0: Double, x1: Double, y1: Double) {
    if ((parent as TreemapNode<D>).depth % 2 == 1) treemapSlice(parent, x0, y0, x1, y1) else treemapDice(parent, x0, y0, x1, y1)
}