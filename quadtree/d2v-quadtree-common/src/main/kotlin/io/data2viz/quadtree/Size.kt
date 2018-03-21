package io.data2viz.quadtree

/**
 * Returns the total number of data in the quadtree.
 */
fun <D> Quadtree<D>.size(): Int {
    var size = 0
    visit({ node, _, _, _, _ ->
        var newNode: QuadtreeNode<D>? = node
        if (newNode is LeafNode<*>) {
            do {
                ++size
                newNode = (newNode as LeafNode<D>).next
            } while (newNode != null)
        }
        false
    })
    return size
}