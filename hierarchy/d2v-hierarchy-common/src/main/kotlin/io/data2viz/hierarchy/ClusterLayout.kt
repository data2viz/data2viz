package io.data2viz.hierarchy

var nodeSize = false
var dx = 1.0
var dy = 1.0

data class ClusterNode<D>(
    val data: D,
    var depth: Int,
    var height: Int,
    override var value: Double?,
    override val children: MutableList<ClusterNode<D>> = mutableListOf(),
    override var parent: ClusterNode<D>? = null,
    var x: Double = .0,
    var y: Double = .0
): ParentValued<ClusterNode<D>>, Children<ClusterNode<D>>

/**
 * Lays out the specified root hierarchy, assigning the following properties on root and its descendants:
 * node.x - the x-coordinate of the node
 * node.y - the y-coordinate of the node
 *
 * The coordinates x and y represent an arbitrary coordinate system; for example, you can treat x as an angle
 * and y as a radius to produce a radial layout.
 * You may want to call root.sort before passing the hierarchy to the cluster layout.
 *
 * The cluster layout produces dendrograms: node-link diagrams that place leaf nodes of the tree at the same depth.
 * Dendograms are typically less compact than tidy trees, but are useful when all the leaves should be at
 * the same level, such as for hierarchical clustering or phylogenetic tree diagrams.
 */
fun <D> cluster(root: Node<D>): ClusterNode<D> {

    val rootCluster = makeCluster(root)

    var previousNode: ClusterNode<D>? = null
    var x = .0

    // First walk, computing the initial x & y values
    rootCluster.eachAfter({ node: ClusterNode<D> ->
        val children = node.children
        if (children.isNotEmpty()) {
            node.x = children.sumByDouble { it.x } / children.size
            node.y = children.maxBy { it.y }!!.y + 1
        } else {
            if (previousNode != null) {
                x += separation(node, previousNode!!)
                node.x = x
            } else node.x = .0
            node.y = .0
            previousNode = node
        }
    })

    val left = leafLeft(rootCluster)
    val right = leafRight(rootCluster)
    val x0 = left.x - separation(left, right) / 2
    val x1 = right.x + separation(right, left) / 2

    // Second walk, normalizing x & y to the desired size.
    return if (nodeSize) {
        rootCluster.eachAfter({ node: ClusterNode<D> ->
            node.x = (node.x - rootCluster.x) * dx
            node.y = (rootCluster.y - node.y) * dy
        })
    } else {
        rootCluster.eachAfter({ node: ClusterNode<D> ->
            node.x = (node.x - x0) / (x1 - x0) * dx
            node.y = if (rootCluster.y == .0) .0 else (1 - (node.y / rootCluster.y)) * dy
        })
    }
}

private fun <D> makeCluster(root: Node<D>): ClusterNode<D> {
    val rootCluster = ClusterNode(root.data, root.depth, root.height, root.value)
    val nodes = mutableListOf(rootCluster)
    while (nodes.isNotEmpty()) {
        val clusterNode = nodes.removeAt(nodes.lastIndex)
        clusterNode.children.clear()
        clusterNode.children.forEach { child ->
            val c = ClusterNode(child.data, child.depth, child.height, child.value)
            c.parent = clusterNode
            clusterNode.children.add(c)
            nodes.add(c)
        }
    }
    return rootCluster
}

private fun <D> leafLeft(node: ClusterNode<D>): ClusterNode<D> {
    var children = node.children
    var current: ClusterNode<D> = node
    while (children.isNotEmpty()) {
        current = children[0]
        children = current.children
    }
    return current
}

private fun <D> leafRight(node: ClusterNode<D>): ClusterNode<D> {
    var children = node.children
    var current: ClusterNode<D> = node
    while (children.isNotEmpty()) {
        current = children[children.lastIndex]
        children = current.children
    }
    return current
}