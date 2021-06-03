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

package io.data2viz.hierarchy

import io.data2viz.hierarchy.treemap.treemapSquarify
import kotlin.math.roundToInt

public data class Row<D>(
    override var value: Double?,
    val dice:Boolean,
    override val children:List<TreemapNode<D>>
) : ParentValued<TreemapNode<D>>

public class TreemapNode<D>(
    public val data: D,
    public var depth: Int,
    public var height: Int,
    override var value: Double?,                 // TODO differentiate value and SUM
    override val children: MutableList<TreemapNode<D>> = mutableListOf(),
    override var parent: TreemapNode<D>? = null,
    public var x0: Double = .0,
    public var y0: Double = .0,
    public var x1: Double = .0,
    public var y1: Double = .0
): ParentValued<TreemapNode<D>>, Children<TreemapNode<D>>

internal fun roundNode(node: TreemapNode<*>) {
    node.x0 = node.x0.roundToInt().toDouble()
    node.y0 = node.y0.roundToInt().toDouble()
    node.x1 = node.x1.roundToInt().toDouble()
    node.y1 = node.y1.roundToInt().toDouble()
}

internal fun <D> makeTreemap(root: Node<D>): TreemapNode<D> {
    val rootTreemap = TreemapNode(root.data, root.depth, root.height, root.value)
    val nodes = mutableListOf(root)
    val nodesTM = mutableListOf(rootTreemap)
    while (nodes.isNotEmpty()) {
        val node = nodes.removeAt(nodes.lastIndex)
        val nodeTM = nodesTM.removeAt(nodesTM.lastIndex)
        node.children.forEach { child ->
            val c = TreemapNode(child.data, child.depth, child.height, child.value)
            c.parent = nodeTM
            nodeTM.children.add(c)
            nodes.add(child)
            nodesTM.add(c)
        }
    }
    return rootTreemap
}

public class TreemapLayout<D> {

    private val constantZero: (TreemapNode<D>) -> Double = { .0 }

    public var tilingMethod: (ParentValued<TreemapNode<D>>, Double, Double, Double, Double) -> Any = {
            parent: ParentValued<TreemapNode<D>>, x0: Double, y0: Double, x1: Double, y1: Double -> treemapSquarify(parent, x0, y0, x1, y1)
    }
    public var round: Boolean = false
    public var width: Double = 1.0
    public var height: Double = 1.0

    private var paddingStack = mutableListOf(.0)
    public var paddingInner: (TreemapNode<D>) -> Double = constantZero
    public var paddingTop: (TreemapNode<D>) -> Double = constantZero
    public var paddingRight: (TreemapNode<D>) -> Double = constantZero
    public var paddingBottom: (TreemapNode<D>) -> Double = constantZero
    public var paddingLeft: (TreemapNode<D>) -> Double = constantZero
    public var paddingOuter: (TreemapNode<D>) -> Double = constantZero
        set(value) {
            paddingTop = value
            paddingRight = value
            paddingBottom = value
            paddingLeft = value
        }

    /**
     * Introduced by Ben Shneiderman in 1991, a treemap recursively subdivides area into rectangles according to
     * each node’s associated value.
     * Treemap implementation supports an extensible tiling method: the default squarified method seeks to generate
     * rectangles with a golden aspect ratio; this offers better readability and size estimation than slice-and-dice,
     * which simply alternates between horizontal and vertical subdivision by depth.
     *
     * Lays out the specified root hierarchy, assigning the following properties on root and its descendants:
     *
     * - node.x0 - the left edge of the rectangle
     * - node.y0 - the top edge of the rectangle
     * - node.x1 - the right edge of the rectangle
     * - node.y1 - the bottom edge of the rectangle
     *
     * You must call root.sum before passing the hierarchy to the treemap layout so each node as a positive value.
     * // TODO force a call on root.sum ?
     * You probably also want to call root.sort to order the hierarchy before computing the layout.
     */
    public fun treemap(root: Node<D>): TreemapNode<D> {

        // TODO : require a check on each node to verify that value != null and >0 ? (root.sum has been passed) ?

        val rootTreemap = makeTreemap(root)

        paddingStack = MutableList(root.height + 1, { .0 })
        rootTreemap.x0 = .0
        rootTreemap.y0 = .0
        rootTreemap.x1 = width
        rootTreemap.y1 = height

        rootTreemap.eachBefore(this::positionNode)
        paddingStack = mutableListOf(.0)

        if (round) rootTreemap.eachBefore(::roundNode)

        return rootTreemap
    }

    private fun positionNode(node: TreemapNode<D>) {
        var p = paddingStack[node.depth]
        var x0 = node.x0 + p
        var y0 = node.y0 + p
        var x1 = node.x1 - p
        var y1 = node.y1 - p

        if (x1 < x0) {
            val mid = (x0 + x1) / 2
            x0 = mid
            x1 = mid
        }
        if (y1 < y0) {
            val mid = (y0 + y1) / 2
            y0 = mid
            y1 = mid
        }
        node.x0 = x0
        node.y0 = y0
        node.x1 = x1
        node.y1 = y1

        if (node.children.isNotEmpty()) {
            paddingStack[node.depth + 1] = paddingInner(node) / 2
            p = paddingInner(node) / 2
            x0 += paddingLeft(node) - p
            y0 += paddingTop(node) - p
            x1 -= paddingRight(node) - p
            y1 -= paddingBottom(node) - p
            if (x1 < x0) {
                val mid = (x0 + x1) / 2
                x0 = mid
                x1 = mid
            }
            if (y1 < y0) {
                val mid = (y0 + y1) / 2
                y0 = mid
                y1 = mid
            }
            tilingMethod(node, x0, y0, x1, y1)
        }
    }
}
