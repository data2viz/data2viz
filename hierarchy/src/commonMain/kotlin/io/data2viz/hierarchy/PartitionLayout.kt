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

import io.data2viz.hierarchy.treemap.treemapDice


public class PartitionLayout {

    public var round: Boolean = false
    private var dx = 1.0
    private var dy = 1.0
    public var padding: Double = .0

    /**
     * The partition layout produces adjacency diagrams: a space-filling variant of a node-link tree diagram.
     * Rather than drawing a link between parent and child in the hierarchy, nodes are drawn as solid areas
     * (either arcs or rectangles), and their placement relative to other nodes reveals their position in the hierarchy.
     * The size of the nodes encodes a quantitative dimension that would be difficult to show in a node-link diagram.
     *
     * Lays out the specified root hierarchy, assigning the following properties on root and its descendants:
     *  - node.x0 - the left edge of the rectangle
     *  - node.y0 - the top edge of the rectangle
     *  - node.x1 - the right edge of the rectangle
     *  - node.y1 - the bottom edge of the rectangle
     *
     *  You must call root.sum before passing the hierarchy to the partition layout.
     *  You probably also want to call root.sort to order the hierarchy before computing the layout.
     */
    public fun <D> partition(root: Node<D>): TreemapNode<D> {

        // TODO check for sum called

        val rootNode = makeTreemap(root)

        val n = rootNode.height + 1
        rootNode.x0 = padding
        rootNode.y0 = padding
        rootNode.x1 = dx
        rootNode.y1 = dy / n
        rootNode.eachBefore(positionNode(dy, n))

        if (round) rootNode.eachBefore(::roundNode)

        return rootNode
    }

    private fun <D> positionNode(dy: Double, n: Int):((TreemapNode<D>)->Unit) {
        return { node:TreemapNode<D> ->
            if (node.children.isNotEmpty()) {
                treemapDice(node, node.x0, dy * (node.depth + 1) / n, node.x1, dy * (node.depth + 2) / n);
            }
            var x0 = node.x0
            var y0 = node.y0
            var x1 = node.x1 - padding
            var y1 = node.y1 - padding
            if (x1 < x0) {
                x0 = (x0 + x1) / 2
                x1 = x0
            }
            if (y1 < y0) {
                y0 = (y0 + y1) / 2
                y1 = y0
            }
            node.x0 = x0
            node.y0 = y0
            node.x1 = x1
            node.y1 = y1
        }
    }

    public fun size(width: Double, height: Double) {
        dx = width
        dy = height
    }

    private fun <D> makeCluster(root: Node<D>): ClusterNode<D> {
        val rootCluster = ClusterNode(root.data, root.depth, root.height, root.value)
        val nodes = mutableListOf(root)
        val nodesC = mutableListOf(rootCluster)
        while (nodes.isNotEmpty()) {
            val node = nodes.removeAt(nodes.lastIndex)
            val nodeC = nodesC.removeAt(nodesC.lastIndex)
            node.children.forEach { child ->
                val c = ClusterNode(child.data, child.depth, child.height, child.value)
                c.parent = nodeC
                nodeC.children.add(c)
                nodes.add(child)
                nodesC.add(c)
            }
        }
        return rootCluster
    }
}
