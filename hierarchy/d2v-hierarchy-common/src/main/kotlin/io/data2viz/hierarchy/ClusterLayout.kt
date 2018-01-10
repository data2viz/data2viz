package io.data2viz.hierarchy

var nodeSize = false
var dx = 1.0
var dy = 1.0

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
fun <D> cluster(root: Node<D>): Node<D> {
    var previousNode: Node<D>? = null
    var x = .0

    // First walk, computing the initial x & y values
    root.eachAfter({ node: Node<D> ->
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

    val left = leafLeft(root)
    val right = leafRight(root)
    val x0 = left.x - separation(left, right) / 2
    val x1 = right.x + separation(right, left) / 2

    // Second walk, normalizing x & y to the desired size.
    return if (nodeSize
    ) {
        root.eachAfter({ node: Node<D> ->
            node.x = (node.x - root.x) * dx
            node.y = (root.y - node.y) * dy
        })
    } else {
        root.eachAfter({ node: Node<D> ->
            node.x = (node.x - x0) / (x1 - x0) * dx
            node.y = if (root.y == .0) .0 else (1 - (node.y / root.y)) * dy
        })
    }
}

fun <D> separation(nodeA: Node<D>, nodeB: Node<D>) = if (nodeA.parent == nodeB.parent) 1 else 2

private fun <D> leafLeft(node: Node<D>): Node<D> {
    var children = node.children
    var current: Node<D> = node
    while (children.isNotEmpty()) {
        current = children[0]
        children = current.children
    }
    return current
}

private fun <D> leafRight(node: Node<D>): Node<D> {
    var children = node.children
    var current: Node<D> = node
    while (children.isNotEmpty()) {
        current = children[children.lastIndex]
        children = current.children
    }
    return current
}