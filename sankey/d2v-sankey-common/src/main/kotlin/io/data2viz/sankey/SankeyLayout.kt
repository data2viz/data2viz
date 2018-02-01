package io.data2viz.sankey

import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

enum class SankeyAlignment { CENTER, JUSTIFY, RIGHT, LEFT }

/**
 * source - the link’s source node
 * target - the link’s target node
 * value - the link’s numeric value
 * y0 - the link’s vertical starting position (at source node)
 * y1 - the link’s vertical end position (at target node)
 * width - the link’s width (proportional to link.value)
 * index - the zero-based index of link within the array of links
 */
data class SankeyLink<D>(
    val source: SankeyNode<D>,
    val target: SankeyNode<D>,
    var index: Int,
    var value: Double,
    var y0: Double = .0,
    var y1: Double = .0,
    var width: Double = .0
)

/**
 * sourceLinks - the array of outgoing links which have this node as their source
 * targetLinks - the array of incoming links which have this node as their target
 * value - the node’s value; the sum of link.value for the node’s incoming links
 * index - the node’s zero-based index within the array of nodes
 * depth - the node’s zero-based graph depth, derived from the graph topology
 * height - the node’s zero-based graph height, derived from the graph topology
 * x0 - the node’s minimum horizontal position, derived from node.depth
 * x1 - the node’s maximum horizontal position (node.x0 + sankey.nodeWidth)
 * y0 - the node’s minimum vertical position
 * y1 - the node’s maximum vertical position (node.y1 - node.y0 is proportional to node.value)
 */
data class SankeyNode<D>(
    val data: D,
    var index: Int,
    val sourceLinks: MutableList<SankeyLink<D>> = mutableListOf(),
    val targetLinks: MutableList<SankeyLink<D>> = mutableListOf(),
    var value: Double = .0,
    var depth: Int = 0,
    var height: Int = 0,
    var x0: Double = .0,
    var x1: Double = .0,
    var y0: Double = .0,
    var y1: Double = .0
)

data class SankeyGraph<D>(
    val nodes:List<SankeyNode<D>>,
    val links: List<SankeyLink<D>>
)

class SankeyLayout<D> {

    // extent
    private var x0 = .0
    private var y0 = .0
    private var x1 = 1.0
    private var y1 = 1.0

    var height
        get() = y1 - y0
        set(value) {
            y0 = .0
            y1 = value
        }

    var width
        get() = x1 - x0
        set(value) {
            x0 = .0
            x1 = value
        }

    fun extent(x0:Double, x1: Double, y0:Double, y1:Double) {
        this.x0 = x0
        this.x1 = x1
        this.y0 = y0
        this.y1 = y1
    }

    var nodeWidth = 24.0
    var nodePadding = 8.0

    var align: SankeyAlignment = SankeyAlignment.JUSTIFY

    // the number of relaxation iterations when generating the layout
    var iterations = 32

    val nodes = mutableListOf<SankeyNode<D>>()
    val links = mutableListOf<SankeyLink<D>>()

    fun sankey(data: List<D>, flow: (from: D, to: D) -> Double?): SankeyGraph<D> {
        computeNodeLinks(data, flow)
        computeNodeValues()
        computeNodeDepths()
        computeNodeBreadths()
        computeLinkBreadths()
        return SankeyGraph(nodes, links)
    }

    private fun computeLinkBreadths() {
        nodes.forEach { node ->
            node.sourceLinks.sortWith(compareBy({ it.target.y0 }, { it.index }))
            node.targetLinks.sortWith(compareBy({ it.source.y0 }, { it.index }))
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
        var nodeList = nodes.toList()
        val next = mutableListOf<SankeyNode<D>>()
        var nodeDepth = 0
        while (nodeList.isNotEmpty()) {
            nodeList.forEach { node ->
                node.depth = nodeDepth
                node.sourceLinks.forEach { link ->
                    if (next.indexOf(link.target) < 0) next.add(link.target)
                }
            }
            nodeDepth++
            nodeList = next.toList()
            next.clear()
        }

        nodeList = nodes.toList()
        next.clear()
        var nodeHeight = 0
        while (nodeList.isNotEmpty()) {
            nodeList.forEach { node ->
                node.height = nodeHeight
                node.targetLinks.forEach { link ->
                    if (next.indexOf(link.source) < 0) next.add(link.source)
                }
            }
            nodeHeight++
            nodeList = next.toList()
            next.clear()
        }

        val kx = (width - nodeWidth) / (nodeHeight - 1)
        nodes.forEach { node ->
            val x = when (align) {
                SankeyAlignment.JUSTIFY -> justify(node, nodeHeight)
                SankeyAlignment.CENTER -> center(node, nodeHeight)
                SankeyAlignment.RIGHT -> right(node, nodeHeight)
                SankeyAlignment.LEFT -> left(node, nodeHeight)
            }.toDouble()
            node.x0 = x0 + max(.0, min(nodeHeight - 1.0, floor(x))) * kx
            node.x1 = node.x0 + nodeWidth
        }
    }

    private fun relaxLeftToRight(columns: Map<Double, List<SankeyNode<D>>>, alpha: Double) {
        columns.forEach { nodeList ->
            nodeList.value.forEach { node ->
                if (node.targetLinks.isNotEmpty()) {
                    val dy =
                        (node.targetLinks.sumByDouble(::weightedSource) / node.targetLinks.sumByDouble { it.value } - nodeCenter(
                            node
                        )) * alpha;
                    node.y0 += dy
                    node.y1 += dy
                }
            }
        }
    }

    private fun relaxRightToLeft(columns: Map<Double, List<SankeyNode<D>>>, alpha: Double) {
        columns.keys.reversed().forEach { nodeKey ->
            val nodeList = columns.get(nodeKey)!!
            nodeList.forEach { node ->
                if (node.sourceLinks.isNotEmpty()) {
                    val sum1 = node.sourceLinks.sumByDouble(::weightedTarget)
                    val sum2 = node.sourceLinks.sumByDouble { it.value }
                    val nodeCenter = nodeCenter(node)
                    val dy = (sum1 / sum2 - nodeCenter) * alpha
                    node.y0 += dy
                    node.y1 += dy
                }
            }
        }
    }

    private fun weightedTarget(link: SankeyLink<D>): Double {
        return nodeCenter(link.target) * link.value
    }

    private fun weightedSource(link: SankeyLink<D>): Double {
        return nodeCenter(link.source) * link.value
    }

    private fun nodeCenter(node: SankeyNode<D>): Double {
        return (node.y0 + node.y1) / 2.0
    }

    /*
    columns.forEach(function(nodes) {
                var node,
                    dy,
                    y = y0,
                    n = nodes.length,
                    i;

                // Push any overlapping nodes down.
                nodes.sort(ascendingBreadth);
                for (i = 0; i < n; ++i) {
                    node = nodes[i];
                    dy = y - node.y0;
                    if (dy > 0) node.y0 += dy, node.y1 += dy;
                    y = node.y1 + py;
                }

                // If the bottommost node goes outside the bounds, push it back up.
                dy = y - py - y1;
                if (dy > 0) {
                    y = (node.y0 -= dy), node.y1 -= dy;

                    // Push any overlapping nodes back up.
                    for (i = n - 2; i >= 0; --i) {
                        node = nodes[i];
                        dy = node.y1 + py - y;
                        if (dy > 0) node.y0 -= dy, node.y1 -= dy;
                        y = node.y0;
                    }
                }
            });
     */

    private fun resolveCollisions(columns: Map<Double, List<SankeyNode<D>>>) {
        columns.forEach { nodesList ->
            val nodes = nodesList.value.sortedBy { it.y0 }
            var dy: Double
            var y = y0

            // Push any overlapping nodes down.
            nodes.forEach { node ->
                dy = y - node.y0
                if (dy > 0) {
                    node.y0 += dy
                    node.y1 += dy
                }
                y = node.y1 + nodePadding
            }

            // If the bottommost node goes outside the bounds, push it back up.
            dy = y - nodePadding - y1
            if (dy > 0) {
                val lastNode = nodes.last()
                lastNode.y0 -= dy
                y = lastNode.y0
                lastNode.y1 -= dy

                // Push any overlapping nodes back up.
                (nodes.size - 2 downTo 0).forEach { index ->
                    val node = nodes[index]
                    dy = node.y1 + nodePadding - y
                    if (dy > 0) {
                        node.y0 -= dy
                        node.y1 -= dy
                    }
                    y = node.y0
                }
            }
        }
    }

    private fun computeNodeBreadths() {
        /*var columns = nest()
            .key(function(d) { return d.x0; })
            .sortKeys(ascending)
            .entries(graph.nodes)
            .map(function(d) { return d.values; });
        }*/
        val columns = nodes.groupBy({ it.x0 }, { it })
        initializeNodeBreadth(columns)
        resolveCollisions(columns)

        var alpha = 1.0
        (1..iterations).forEach {
            alpha *= 0.99
            relaxRightToLeft(columns, alpha)
            resolveCollisions(columns)
            relaxLeftToRight(columns, alpha)
            resolveCollisions(columns)
        }
    }

    private fun initializeNodeBreadth(columns: Map<Double, List<SankeyNode<D>>>) {
        val ky = columns.map { nodes ->
            (height - (nodes.value.size - 1) * nodePadding) / nodes.value.sumByDouble { it.value }
        }.min()!!

        columns.forEach { nodes ->
            nodes.value.forEachIndexed { i, node ->
                node.y0 = i.toDouble()
                node.y1 = node.y0 + node.value * ky
            }
        }

        links.forEach { link ->
            link.width = link.value * ky
        }
    }

    /**
     * Compute the value (size) of each node by summing the associated links.
     */
    private fun computeNodeValues() {
        nodes.forEach { node ->
            node.value = max(node.sourceLinks.sumByDouble { it.value }, node.targetLinks.sumByDouble { it.value })
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
            if (node.sourceLinks.isEmpty()) 0 else node.sourceLinks.minBy { it.target.depth }!!.target.depth - 1
        } else node.depth
    }

}