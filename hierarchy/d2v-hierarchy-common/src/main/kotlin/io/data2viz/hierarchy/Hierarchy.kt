package io.data2viz.hierarchy

data class Node<D>(
    val data: D,
    var depth: Int = 0,
    var height: Int = 0,
    var value: Double? = null,
    var x: Double = .0,
    var y: Double = .0,
    var x0: Double = .0,
    var y0: Double = .0,
    var x1: Double = .0,
    var y1: Double = .0,
    val children: MutableList<Node<D>> = mutableListOf(),
    var parent: Node<D>? = null
)

fun <D> Node<D>.toString():String = "depth=$depth height=$height value=$value children=${children.size}"

data class Link<D>(
    val source: Node<D>?,
    var target: Node<D>
)

/**
 * Constructs a root node from the specified hierarchical data. The specified data must be an object
 * representing the root node.
 * The specified children accessor function is invoked for each datum, starting with the root data, and must
 * return an array of data representing the children, or null if the current datum has no children.
 * The specified value accessor function is invoked for each datum, starting with the root data, and must return
 * a Double value representing the data. If value is not specified, it defaults to null (no value for nodes).
 */
fun <D> hierarchy(data: D, children: (D) -> List<D>?, value: ((D) -> Double)? = null): Node<D> {
    val root = Node(data)
    val nodes = mutableListOf(root)

    while (nodes.size > 0) {
        val node = nodes.removeAt(nodes.lastIndex)
        val childs = children(node.data)
        if (childs != null) {
            childs.forEach { c ->
                val child = Node(c)
                child.parent = node
                child.depth = node.depth + 1
                //if (value != null) child.value = value(c)
                node.children.add(child)
                nodes.add(child)
            }
        }
    }
    return root.eachBefore(::computeHeight)
}

/**
 * Computes the number of leaves under this node and assigns it to node.value, and similarly for every
 * descendant of node.
 * If this node is a leaf, its count is one.
 * Returns this node.
 * See also node.sum.
 */
fun <D> Node<D>.count(): Node<D> {
    return this.eachAfter(::nodeCount)
}

/**
 * TODO check behavior (especially non-negative value)
 * Evaluates the specified value function for this node and each descendant in post-order traversal,
 * and returns this node.
 * The node.value property of each node is set to the numeric value returned by the specified function plus
 * the combined value of all descendants. The function is passed the node’s data, and must return a non-negative number.
 * The value accessor is evaluated for node and every descendant, including internal nodes; if you only want leaf
 * nodes to have internal value, then return zero for any node with children.
 */
fun <D> Node<D>.sum(value: ((D) -> Double)? = null): Node<D> {
    return this.eachAfter({ node: Node<D> ->
        var sum = if (value != null) value(node.data) else .0
        node.children.forEach { child -> if (child.value != null) sum += child.value!! }
        node.value = sum
    })
}

/**
 * Returns the array of ancestors nodes, starting with this node, then followed by each parent up to the root.
 */
fun <D> Node<D>.ancestors(): List<Node<D>> {
    val nodes: MutableList<Node<D>> = mutableListOf(this)
    var node: Node<D>? = this
    while (node != null
    ) {
        nodes.add(node)
        node = node.parent
    }
    return nodes.toList()
}

/**
 * Returns the array of descendant nodes, starting with this node, then followed by each child in topological order.
 */
fun <D> Node<D>.descendants(): List<Node<D>> {
    val nodes: MutableList<Node<D>> = mutableListOf()
    this.each({ node: Node<D> ->
        nodes.add(node)
    })
    return nodes
}

/**
 * Returns the array of leaf nodes in traversal order; leaves are nodes with no children.
 */
fun <D> Node<D>.leaves(): List<Node<D>> {
    val leaves: MutableList<Node<D>> = mutableListOf()
    this.eachBefore({ node: Node<D> ->
        if (node.children.isEmpty()) leaves.add(node)
    })
    return leaves
}

/**
 * Returns an array of links for this node, where each link is an object that defines source and target properties.
 * The source of each link is the parent node, and the target is a child node.
 */
fun <D> Node<D>.links(): List<Link<D>> {
    val root = this
    val links: MutableList<Link<D>> = mutableListOf()
    root.each({ node: Node<D> ->
        if (node != root) links.add(Link(node.parent, node))    // Don’t include the root’s parent, if any.
    })
    return links.toList()
}

/**
 * Invokes the specified function for node and each descendant in breadth-first order, such that a given
 * node is only visited if all nodes of lesser depth have already been visited, as well as all preceeding
 * nodes of the same depth.
 * The specified function is passed the current node.
 */
fun <D> Node<D>.each(callback: (Node<D>) -> Unit): Node<D> {
    val next = mutableListOf(this)
    while (next.size > 0) {
        val current = next.reversed().toMutableList()
        next.clear()
        val node = current.removeAt(current.lastIndex)
        callback(node)
        val children = node.children
        if (children.isNotEmpty()) {
            (children.lastIndex downTo 0).forEach {
                next.add(children[it])
            }
        }
    }
    return this
}

/**
 * Invokes the specified function for node and each descendant in pre-order traversal, such that a given node
 * is only visited after all of its ancestors have already been visited.
 * The specified function is passed the current node.
 */
fun <D> Node<D>.eachBefore(callback: (Node<D>) -> Unit): Node<D> {
    val nodes = mutableListOf(this)
    while (nodes.isNotEmpty()) {
        val node = nodes.removeAt(nodes.lastIndex)
        callback(node)
        val children = node.children
        if (children.isNotEmpty()) {
            (children.lastIndex downTo 0).forEach {
                nodes.add(children[it])
            }
        }
    }
    return this
}

/**
 * Invokes the specified function for node and each descendant in post-order traversal, such that a given node
 * is only visited after all of its descendants have already been visited.
 * The specified function is passed the current node.
 */
fun <D> Node<D>.eachAfter(callback: (Node<D>) -> Unit): Node<D> {
    val nodes = mutableListOf(this)
    val next = mutableListOf<Node<D>>()
    while (nodes.isNotEmpty()) {
        val node = nodes.removeAt(nodes.lastIndex)
        next.add(node)
        val children = node.children
        if (children.isNotEmpty()) {
            children.forEach {
                nodes.add(it)
            }
        }
    }
    next.reversed().forEach(callback)
    return this
}

private fun <D> computeHeight(node: Node<D>) {
    var n: Node<D> = node
    var height = 0
    n.height = height
    height++

    while (n.parent != null && n.parent!!.height < height) {
        n.parent!!.height = height
        n = n.parent!!
        height++
    }
}

private fun <D> nodeCount(node: Node<D>) {
    var sum = .0
    val children = node.children
    if (children.isEmpty()) sum = 1.0
    else {
        children.forEach { if (it.value != null) sum += it.value!! }
    }
    node.value = sum
}