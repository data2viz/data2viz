package io.data2viz.quadtree

fun <D> Quadtree<D>.data(): List<D> {
    val data = mutableListOf<D>()
    visit { node, _, _, _, _ ->
        var newNode:QuadtreeNode<D>? = node
        if (newNode is LeafNode) do {
            data.add((newNode as LeafNode).data)
            newNode = newNode.next
        } while (newNode != null)
        false
    }
    return data
}