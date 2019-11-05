/*
 * Copyright (c) 2018-2019. data2viz sàrl.
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
 * Returns a copy of the quadtree. All nodes in the returned quadtree are identical copies of the corresponding
 * node in the quadtree; however, any data in the quadtree is shared by reference and not copied.
 */
fun <D> Quadtree<D>.copy(): Quadtree<D> {

    val copy = Quadtree(x, y)
    copy.extent = extent.copy()

    val node = root ?: return copy

    if (node is LeafNode) {
        copy.root = leafCopy(node)
        return copy
    }

    copy.root = InternalNode()
    val nodes = mutableListOf(NodePair(node, copy.root!!))
    while (nodes.isNotEmpty()) {
        val currentNode = nodes.removeAt(nodes.lastIndex)
        (0..3).forEach { index ->
            val child = getNodeFromIndex(currentNode.source as InternalNode<D>, index)
            if (child != null) {
                if (child is InternalNode) {
                    setNodeFromIndex(currentNode.target as InternalNode<D>, index, InternalNode())
                    nodes.add(NodePair(child, getNodeFromIndex(currentNode.target, index)!!))
                } else {
                    setNodeFromIndex(currentNode.target as InternalNode<D>, index, leafCopy(child as LeafNode<D>))
                }
            }
        }
    }

    return copy
}

private fun <D> leafCopy(leaf: LeafNode<D>): LeafNode<D> {
    val copy = LeafNode(leaf.data, null)
    var next = copy

    var newLeaf = leaf
    while (newLeaf.next != null) {
        newLeaf = newLeaf.next!!
        next.next = LeafNode(newLeaf.data, null)
        next = next.next!!
    }
    return copy
}