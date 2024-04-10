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

package io.data2viz.sankey

import io.data2viz.shape.link.LinkBuilder
import io.data2viz.shape.link.linkBuilderH
import io.data2viz.shape.link.linkBuilderV
import kotlin.math.max
import kotlin.math.min

public enum class SankeyAlignment {

    /**
     * Like LEFT, except that nodes without any incoming links are moved as right as possible.
     */
    CENTER,

    /**
     * Like LEFT, except that nodes without any outgoing links are moved to the far right.
     */
    JUSTIFY,

    /**
     * The node's column is its depth (starting right).
     */
    RIGHT,

    /**
     * The node's column is its depth (starting left).
     */
    LEFT
}

///// LINKS VISUALIZATIONS

public val sankeyLinkHorizontal: LinkBuilder<SankeyLink<*>> = linkBuilderH<SankeyLink<*>> {
    x0 = { it.source.x1 }
    y0 = { it.y0 }
    x1 = { it.target.x0 }
    y1 = { it.y1 }
}

public val sankeyLinkVertical: LinkBuilder<SankeyLink<*>> = linkBuilderV<SankeyLink<*>> {
    x0 = { it.y0 }
    y0 = { it.source.x0 }
    x1 = { it.y1 }
    y1 = { it.target.x1 }
}

/**
 * @param source - the link’s source node
 * @param target - the link’s target node
 * @param value - the link’s numeric value
 * @param y0 - the link’s vertical starting position (at source node)
 * @param y1 - the link’s vertical end position (at target node)
 * @param width - the link’s width (proportional to link.value)
 * @param index - the zero-based index of link within the array of links
 */
public data class SankeyLink<D>(
    val source: SankeyNode<D>,
    val target: SankeyNode<D>,
    var index: Int,
    var value: Double,
    var y0: Double = .0,
    var y1: Double = .0,
    var width: Double = .0
)

/**
 * @param data the domain object of the node
 * @param index - the node’s zero-based index within the array of nodes
 * @param sourceLinks - the array of outgoing links which have this node as their source
 * @param targetLinks - the array of incoming links which have this node as their target
 * @param value - the node’s value; the sum of link.value for the node’s incoming links
 * @param depth - the node’s zero-based graph depth, derived from the graph topology
 * @param height - the node’s zero-based graph height, derived from the graph topology
 * @param layer - the node’s zero-based column index, corresponding to its horizontal position
 * @param x0 - the node’s minimum horizontal position, derived from node.depth
 * @param x1 - the node’s maximum horizontal position (node.x0 + sankey.nodeWidth)
 * @param y0 - the node’s minimum vertical position
 * @param y1 - the node’s maximum vertical position (node.y1 - node.y0 is proportional to node.value)
 */
public data class SankeyNode<D>(
    val data: D,
    var index: Int,
    val sourceLinks: MutableList<SankeyLink<D>> = mutableListOf(),
    val targetLinks: MutableList<SankeyLink<D>> = mutableListOf(),
    var value: Double = .0,
    var depth: Int = 0,
    var height: Int = 0,
    var layer: Int = 0,
    var x0: Double = .0,
    var x1: Double = .0,
    var y0: Double = .0,
    var y1: Double = .0
) {
    /**
     * The difference between the starting X coordinate (x0) and the ending X Coordinate (x1)
     */
    public val dx: Double
        get() = x1 - x0

    /**
     * The difference between the starting Y coordinate (y0) and the ending Y Coordinate (y1)
     */
    public val dy: Double
        get() = y1 - y0
}

public data class SankeyGraph<D>(
    val nodes:List<SankeyNode<D>>,
    val links: List<SankeyLink<D>>
)

public class SankeyLayout<D> {

    // extent
    private var x0 = .0
    private var y0 = .0
    private var x1 = 1.0
    private var y1 = 1.0

    public var height: Double
        get() = y1 - y0
        set(value) {
            y0 = .0
            y1 = value
        }

    public var width: Double
        get() = x1 - x0
        set(value) {
            x0 = .0
            x1 = value
        }

    public fun extent(x0: Double, x1: Double, y0: Double, y1: Double) {
        this.x0 = x0
        this.x1 = x1
        this.y0 = y0
        this.y1 = y1
    }

    /**
     * Sets the node width to the specified number.
     * Defaults to 24.0.
     */
    public var nodeWidth: Double = 24.0

    /**
     * Sets the vertical separation between nodes at each column to the specified number.
     * Defaults to 8.0.
     */
    public var nodePadding: Double = 8.0
        set(value) {
            field = value
            limitedNodePadding = value
        }

    /**
     * Node padding can be limited depending on the extent and the nodes, so we use a limitedNodePadding for the layout.
     */
    private var limitedNodePadding = nodePadding

    /**
     * If align is specified, sets the node alignment method to the specified function.
     * Defaults to SankeyAlignment.JUSTIFY.
     * The specified function is evaluated for each input node in order, being passed the current node and the
     * total depth n of the graph (one plus the maximum node.depth), and must return an integer between 0 and n - 1
     * that indicates the desired horizontal position of the node in the generated Sankey diagram.
     * @see SankeyAlignment
     */
    public var align: SankeyAlignment = SankeyAlignment.JUSTIFY

    /**
     * Sets the number of relaxation iterations when generating the layout.
     * Defaults to 6.
     */
    public var iterations: Int = 6

    /**
     * Sets the link sort method.
     * The sort lambda determines the order; the lambda is passed two links and must return a value less
     * than .0 if the first link should be above the second, and a value greater than .0 if the second link
     * should be above the first, or .0 if the order is not specified.
     * If the lambda is null then the sort is determined by the layout.
     * Defaults to null.
     */
    public var linkSort: ((SankeyLink<D>, SankeyLink<D>) -> Double)? = null

    private val ascendingTargetComparator = compareBy<SankeyLink<D>>({ it.target.y0 }, { it.index })
    private val ascendingSourceComparator = compareBy<SankeyLink<D>>({ it.source.y0 }, { it.index })

    public val nodes: MutableList<SankeyNode<D>> = mutableListOf()
    public val links: MutableList<SankeyLink<D>> = mutableListOf()

    public fun sankey(data: List<D>, flow: (from: D, to: D) -> Double?): SankeyGraph<D> {
        nodes.clear()
        links.clear()
        computeNodeLinks(data, flow)
        computeNodeValues()
        computeNodeDepths()
        computeNodeHeights()
        computeNodeLayers()
        computeNodeBreadths()
        computeLinkBreadths()
        return SankeyGraph(nodes, links)
    }

    private fun computeLinkBreadths() {
        nodes.forEach { node ->
            node.sourceLinks.sortWith(ascendingTargetComparator)
            node.targetLinks.sortWith(ascendingSourceComparator)
        }
        nodes.forEach { node ->
            var y0 = node.y0
            var y1 = y0
            node.sourceLinks.forEach { link ->
                link.y0 = y0 + link.width / 2.0
                y0 += link.width
            }
            node.targetLinks.forEach { link ->
                link.y1 = y1 + link.width / 2.0
                y1 += link.width
            }
        }
    }

    /**
     * Iteratively assign the depth (x-position) for each node.
     * Nodes are assigned the maximum depth of incoming neighbors plus one; nodes with no incoming links are assigned
     * depth zero, while nodes with no outgoing links are assigned the maximum depth.
     */
    private fun computeNodeDepths() {
        var current = nodes.toList()
        val next = mutableListOf<SankeyNode<D>>()
        var nodeDepth = 0
        while (current.isNotEmpty()) {
            current.forEach { node ->
                node.depth = nodeDepth
                node.sourceLinks.forEach { link ->
                    if (next.indexOf(link.target) < 0) next.add(link.target)
                }
            }
            nodeDepth++

            // TODO manage circular links
            //if (++x > n) throw new Error("circular link");

            current = next.toList()
            next.clear()
        }
    }

    /**
     * Iteratively assign the height (x-position from the end) for each node.
     */
    private fun computeNodeHeights() {
        var current = nodes.toList()
        val next = mutableListOf<SankeyNode<D>>()
        var nodeHeight = 0
        while (current.isNotEmpty()) {
            current.forEach { node ->
                node.height = nodeHeight
                node.targetLinks.forEach { link ->
                    if (next.indexOf(link.source) < 0) next.add(link.source)
                }
            }
            nodeHeight++

            // TODO manage circular links
            // if (++x > n) throw new Error("circular link");

            current = next.toList()
            next.clear()
        }
    }

    private fun computeNodeLayers() {
        val maxDepth = nodes.maxBy { it.depth }.depth + 1
        val kx = (width - nodeWidth) / (maxDepth - 1)
        nodes.forEach { node ->
            val x = when (align) {
                SankeyAlignment.JUSTIFY -> justify(node, maxDepth)
                SankeyAlignment.CENTER -> center(node, maxDepth)
                SankeyAlignment.RIGHT -> right(node, maxDepth)
                SankeyAlignment.LEFT -> left(node, maxDepth)
            }
            node.x0 = x0 + max(.0, min(maxDepth - 1.0, x.toDouble())) * kx
            node.x1 = node.x0 + nodeWidth
            node.layer = x
        }
    }

    private fun relaxLeftToRight(columns: Map<Int, List<SankeyNode<D>>>, alpha: Double, beta: Double) {
        columns.keys.forEach { nodeKey ->
            if (nodeKey > 1) {
                var column = columns[nodeKey]
                column?.forEach { node ->
                    if (node.targetLinks.isNotEmpty()) {
                        var y = .0
                        var w = .0
                        node.targetLinks.forEach { link ->
                            val v = link.value * (link.target.layer - node.layer)
                            y += targetTop(node, link.target) * v
                            w += v
                        }
                        if (w > .0) {
                            val dy = (y / w - node.y0) * alpha
                            node.y0 += dy
                            node.y1 += dy
                            reorderNodeLinks(node)
                        }
                    }
                    //column = column?.sortedWith(defaultAscendingNodeComparator)
                    column?.let { resolveCollisions(it, beta) }
                }
            }
        }
    }

    private fun relaxRightToLeft(columns: Map<Int, List<SankeyNode<D>>>, alpha: Double, beta: Double) {
        val max = columns.keys.max()
        columns.keys.reversed().forEach { nodeKey ->
            if (nodeKey < max) {
                var column = columns[nodeKey]
                column?.forEach { node ->
                    if (node.sourceLinks.isNotEmpty()) {
                        var y = .0
                        var w = .0
                        node.sourceLinks.forEach { link ->
                            val v = link.value * (link.target.layer - node.layer)
                            y += sourceTop(node, link.target) * v
                            w += v
                        }
                        if (w > .0) {
                            val dy = (y / w - node.y0) * alpha
                            node.y0 += dy
                            node.y1 += dy
                            reorderNodeLinks(node)
                        }
                    }
                }
                //column = column?.sortedWith(defaultAscendingNodeComparator)
                column?.let { resolveCollisions(it, beta) }
            }
        }
    }

    private fun reorderNodeLinks(node: SankeyNode<D>) {
        node.targetLinks.forEach { link ->
            link.source.sourceLinks.sortWith(ascendingTargetComparator)
        }
        node.sourceLinks.forEach { link ->
            link.target.targetLinks.sortWith(ascendingSourceComparator)
        }
    }

    /**
     * Returns the source.y0 that would produce an ideal link from source to target.
     */
    private fun sourceTop(source: SankeyNode<D>, target: SankeyNode<D>): Double {
        var y = target.y0 - (target.targetLinks.size - 1) * limitedNodePadding / 2
        target.targetLinks.forEach { link ->
            if (link.source == source) return@forEach
            y += link.width + limitedNodePadding
        }
        target.sourceLinks.forEach { link ->
            if (link.target == target) return@forEach
            y -= link.width
        }
        return y
    }

    /**
     * Returns the target.y0 that would produce an ideal link from source to target.
     */
    private fun targetTop(source: SankeyNode<D>, target: SankeyNode<D>): Double {
        var y = source.y0 - (target.sourceLinks.size - 1) * limitedNodePadding / 2
        source.sourceLinks.forEach { link ->
            if (link.target == target) return@forEach
            y += link.width + limitedNodePadding
        }
        target.targetLinks.forEach { link ->
            if (link.source == source) return@forEach
            y -= link.width
        }
        return y
    }

    private fun resolveCollisions(column: List<SankeyNode<D>>, beta: Double) {
        val index = column.size.shr(1)
        val subject = column[index]
        resolveCollisionsBottomToTop(column, subject.y0 - limitedNodePadding, index - 1, beta)
        resolveCollisionsTopToBottom(column, subject.y1 + limitedNodePadding, index + 1, beta)
        resolveCollisionsBottomToTop(column, y1, column.size - 1, beta)
        resolveCollisionsTopToBottom(column, y0, 0, beta)
    }

    /**
     * Push any overlapping nodes up.
     */
    private fun resolveCollisionsBottomToTop(column: List<SankeyNode<D>>, yCoord: Double, index: Int, beta: Double) {
        var y = yCoord
        (index downTo 0).forEach {
            val node = column[it]
            val dy = (node.y1 - y) * beta
            if (dy > 1e-6) {
                node.y0 -= dy
                node.y1 -= dy
            }
            y = node.y0 - limitedNodePadding
        }
    }

    /**
     * Push any overlapping nodes down.
     */
    private fun resolveCollisionsTopToBottom(column: List<SankeyNode<D>>, yCoord: Double, index: Int, beta: Double) {
        var y = yCoord
        (index until column.size).forEach {
            val node = column[it]
            val dy = (y - node.y0) * beta
            if (dy > 1e-6) {
                node.y0 += dy
                node.y1 += dy
            }
            y = node.y1 + limitedNodePadding
        }
    }

    private fun computeNodeBreadths() {
        val columns = nodes.groupBy({ it.layer }, { it })
        initializeColumnsOrder(columns)
        initializeNodeBreadth(columns)

        var alpha = 1.0
        (1..iterations).forEach {
            alpha *= 0.9999
            val beta = max(1.0 - alpha, (it + 1.0) / iterations)

            // TODO re-activate relaxing and collisions management
            //relaxRightToLeft(columns, alpha, beta)
            //resolveCollisions(columns, beta)
            //relaxLeftToRight(columns, alpha, beta)
            //resolveCollisions(columns, beta)
        }
    }

    private fun initializeColumnsOrder(columns: Map<Int, List<SankeyNode<D>>>) {
        val sortColumns = columns.map { (nodeKey, nodeList) ->
            nodeList.map { node ->
                Triple(
                    node.index,
                    node.sourceLinks.map { it.target.index }.sorted(),
                    node.targetLinks.map { it.source.index }.sorted()
                )
            }.sortedBy { it.first }
        }
    }

    private fun initializeNodeBreadth(columns: Map<Int, List<SankeyNode<D>>>) {

        // Check if the padding is not too high to be able to draw the chart, if so, reduce it
        val columnsIntervals = columns.map { column -> column.value.size - 1 }
        val maxIntervals = columnsIntervals.max()
        if ((maxIntervals * nodePadding) > height) {
            limitedNodePadding = max(1.0, (height / maxIntervals) - 1.0)
        }

        // Computing scale factor from node value to Y coordinates
        val valueToY = columns.map { column ->
            val columnNodes = column.value
            val columnTotalValue = columnNodes.sumOf { it.value }
            val columnIntervals = columnNodes.size - 1
            (height - (columnIntervals * limitedNodePadding)) / columnTotalValue
        }.minOrNull()!!

        // Initialize each node position
        columns.keys.reversed().forEach { columnKey ->
            val columnNodes = columns[columnKey]!!.sortedBy {
                it.sourceLinks.firstOrNull()?.target?.index ?: Int.MAX_VALUE
            }
            val columnOuterIntervals = columnNodes.size + 1
            val columnTotalValue = columnNodes.sumOf { it.value }
            val totalColumnBreadth = columnTotalValue * valueToY
            val spacing = (height - totalColumnBreadth) / columnOuterIntervals
            var y = spacing
            columnNodes.forEach { node ->
                val nodeBreadth = node.value * valueToY
                node.y0 = y
                node.y1 = y + nodeBreadth
                y += nodeBreadth + spacing
            }
        }

        links.forEach { link ->
            link.width = (link.value * valueToY)
        }
    }

    /**
     * Compute the value (size) of each node by summing the associated links.
     */
    private fun computeNodeValues() {
        nodes.forEach { node ->
            node.value = max(node.sourceLinks.sumOf { it.value }, node.targetLinks.sumOf { it.value })
        }
    }

    /**
     * Populate the sourceLinks and targetLinks for each node.
     */
    private fun computeNodeLinks(data: List<D>, flow: (from: D, to: D) -> Double?) {
        nodes.addAll(data.mapIndexed { index, d -> SankeyNode(d, index) })
        var index = 0
        data.forEachIndexed { index1, d1 ->
            data.forEachIndexed { index2, d2 ->
                val linkValue = flow(d1, d2)
                if (linkValue != null && linkValue > .0) {
                    val node1 = nodes[index1]
                    val node2 = nodes[index2]
                    val link = SankeyLink(node1, node2, index, linkValue)
                    links.add(link)
                    node1.sourceLinks.add(link)
                    node2.targetLinks.add(link)
                    index++
                }
            }
        }
    }

    ///// ALIGNMENTS

    private fun justify(node: SankeyNode<*>, size: Int): Int {
        return if (node.sourceLinks.isEmpty()) size - 1 else node.depth
    }

    private fun left(node: SankeyNode<*>, size: Int): Int {
        return node.depth
    }

    private fun right(node: SankeyNode<*>, size: Int): Int {
        return size - 1 - node.height
    }

    private fun center(node: SankeyNode<*>, size: Int): Int {
        return if (node.targetLinks.isEmpty()) {
            if (node.sourceLinks.isEmpty()) 0 else node.sourceLinks.minByOrNull { it.target.depth }!!.target.depth - 1
        } else node.depth
    }

}
