/*
 * Copyright (c) 2018-2021. data2viz sàrl.
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

package io.data2viz.quadtree

/**
 * Visits each node in the quadtree in pre-order traversal, invoking the specified callback with arguments
 * node, x0, y0, x1, y1 for each node, where node is the node being visited, ⟨x0, y0⟩ are the lower bounds of the
 * node, and ⟨x1, y1⟩ are the upper bounds.
 * (Assuming that positive x is right and positive y is down, as is typically the case in Canvas and SVG,
 * ⟨x0, y0⟩ is the top-left corner and ⟨x1, y1⟩ is the lower-right corner; however, the coordinate system is arbitrary,
 * so more formally x0 <= x1 and y0 <= y1).
 *
 * If the callback returns true for a given node, then the children of that node are not visited; otherwise,
 * all child nodes are visited. This can be used to quickly visit only parts of the tree, for example when
 * using the Barnes–Hut approximation.
 * Note, however, that child quadrants are always visited in sibling order: top-left, top-right, bottom-left,
 * bottom-right. In cases such as search, visiting siblings in a specific order may be faster.
 */
public fun <D> Quadtree<D>.visit(callback: (QuadtreeNode<D>, Double, Double, Double, Double) -> Boolean) {
    val quads = mutableListOf<Quad<D>>()
    var node = root
    if (node != null) quads.add(Quad(node, extent.x0, extent.y0, extent.x1, extent.y1))
    while (quads.isNotEmpty()) {
        val q = quads.removeAt(quads.lastIndex)
        node = q.node
        val x0 = q.x0
        val y0 = q.y0
        val x1 = q.x1
        val y1 = q.y1
        if (!callback(node!!, x0, y0, x1, y1) && node is InternalNode) {
            val xm = (x0 + x1) / 2
            val ym = (y0 + y1) / 2
            if (node.SW_3 != null) quads.add(Quad(node.SW_3, xm, ym, x1, y1))
            if (node.SE_2 != null) quads.add(Quad(node.SE_2, x0, ym, xm, y1))
            if (node.NW_1 != null) quads.add(Quad(node.NW_1, xm, y0, x1, ym))
            if (node.NE_0 != null) quads.add(Quad(node.NE_0, x0, y0, xm, ym))
        }
    }
}

/**
 * Visits each node in the quadtree in post-order traversal, invoking the specified callback with arguments
 * node, x0, y0, x1, y1 for each node, where node is the node being visited, ⟨x0, y0⟩ are the lower bounds of the node,
 * and ⟨x1, y1⟩ are the upper bounds.
 * (Assuming that positive x is right and positive y is down, as is typically the case in Canvas and SVG, ⟨x0, y0⟩
 * is the top-left corner and ⟨x1, y1⟩ is the lower-right corner; however, the coordinate system is arbitrary,
 * so more formally x0 <= x1 and y0 <= y1.) Returns root.
 */
fun <D> Quadtree<D>.visitAfter(callback: (QuadtreeNode<D>, Double, Double, Double, Double) -> Unit) {
    val quads = mutableListOf<Quad<D>>()
    val next = mutableListOf<Quad<D>>()
    if (root != null) quads.add(Quad(root, extent.x0, extent.y0, extent.x1, extent.y1))
    while (quads.isNotEmpty()) {
        val q = quads.removeAt(quads.lastIndex)
        val node = q.node
        if (node is InternalNode) {
            val x0 = q.x0
            val y0 = q.y0
            val x1 = q.x1
            val y1 = q.y1
            val xm = (x0 + x1) / 2
            val ym = (y0 + y1) / 2
            if (node.NE_0 != null) quads.add(Quad(node.NE_0, x0, y0, xm, ym))
            if (node.NW_1 != null) quads.add(Quad(node.NW_1, xm, y0, x1, ym))
            if (node.SE_2 != null) quads.add(Quad(node.SE_2, x0, ym, xm, y1))
            if (node.SW_3 != null) quads.add(Quad(node.SW_3, xm, ym, x1, y1))
        }
        next.add(q)
    }
    while (next.isNotEmpty()) {
        val q = next.removeAt(next.lastIndex)
        if (q.node != null) callback(q.node, q.x0, q.y0, q.x1, q.y1)
    }
}
