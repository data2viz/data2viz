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

import io.data2viz.geom.Extent

inline internal fun Boolean.toInt() = if (this) 1 else 0

internal data class NodePair<D>(
    val source: QuadtreeNode<D>,
    val target: QuadtreeNode<D>
)

interface QuadtreeNode<D> {
    var value:Double?
    var x: Double
    var y: Double
}

data class InternalNode<D>(
    var NE_0: QuadtreeNode<D>? = null,
    var NW_1: QuadtreeNode<D>? = null,
    var SE_2: QuadtreeNode<D>? = null,
    var SW_3: QuadtreeNode<D>? = null,
    override var value:Double? = null,
    override var x: Double = Double.NaN,
    override var y: Double = Double.NaN
    ) : QuadtreeNode<D>

fun <D> InternalNode<D>.toList() = listOf(this.NE_0, this.NW_1, this.SE_2, this.SW_3)

data class LeafNode<D>(
        val data: D,
        var next: LeafNode<D>?,
        override var value:Double? = null,
        override var x: Double = Double.NaN,
        override var y: Double = Double.NaN
) : QuadtreeNode<D>

// TODO : still needed ?
internal data class Quad<D>(
    val node: QuadtreeNode<D>?,
    val x0:Double,
    val y0:Double,
    val x1:Double,
    val y1:Double
)

internal fun <D> getNodeFromIndex(node: InternalNode<D>, index: Int): QuadtreeNode<D>? {
    return when (index) {
        0 -> node.NE_0
        1 -> node.NW_1
        2 -> node.SE_2
        else -> node.SW_3
    }
}

internal fun <D> setNodeFromIndex(node: InternalNode<D>, index: Int, value: QuadtreeNode<D>?) {
    when (index) {
        0 -> node.NE_0 = value
        1 -> node.NW_1 = value
        2 -> node.SE_2 = value
        else -> node.SW_3 = value
    }
}

fun <D> quadtree(x: (D) -> Double, y: (D) -> Double) = Quadtree(x, y)

/**
 * Create quadtree and add all nodes.
 */
fun <D> quadtree(x: (D) -> Double, y: (D) -> Double, nodes: List<D>): Quadtree<D> = Quadtree(x,y).apply { addAll(nodes)}


// TODO : remove x and y from class constructor ?
/**
 * A quadtree recursively partitions two-dimensional space into squares, dividing each square into four equally-sized
 * squares. Each distinct point exists in a unique leaf node; coincident points are represented by a linked list.
 * Quadtrees can accelerate various spatial operations, such as the Barnes–Hut approximation for computing
 * many-body forces, collision detection, and searching for nearby points.
 *
 * x and y accessor are used to derive the coordinates of data when adding to and removing from the tree.
 * It is also used when finding to re-access the coordinates of data previously added to the tree;
 * therefore, the x- and y-accessors must be consistent, returning the same value given the same input.
*/
class Quadtree<D>(val x: (D) -> Double, val y: (D) -> Double) {

    /**
     * The root node of the quadtree.
     */
    var root: QuadtreeNode<D>? = null

    /**
     * Expands the quadtree to cover the specified points [x0, y0] / [x1, y1].
     * The extent may also be expanded by calling quadtree.cover or quadtree.add.
     */
    // TODO double.NaN ou null extent ?
    var extent: Extent = Extent(Double.NaN, Double.NaN, Double.NaN, Double.NaN)
        set(value) {
            cover(value.x0, value.y0)
            cover(value.x1, value.y1)
        }

}