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

import kotlin.math.floor

/**
 * Expands the quadtree to cover the specified point ⟨x,y⟩.
 * If the quadtree’s extent already covers the specified point, this method does nothing.
 * If the quadtree has an extent, the extent is repeatedly doubled to cover the specified point, wrapping the
 * root node as necessary; if the quadtree is empty, the extent is initialized to the extent (⌊x⌋, ⌊y⌋, ⌈x⌉, ⌈y⌉).
 * (Rounding is necessary such that if the extent is later doubled, the boundaries of existing quadrants do
 * not change due to floating point error.)
 */
fun <D> Quadtree<D>.cover(x: Double, y: Double) {
    if (x.isNaN() || y.isNaN()) return                 // ignore invalid points

    var x0 = extent.x0
    var y0 = extent.y0
    var x1 = extent.x1
    var y1 = extent.y1

    // If the quadtree has no extent, initialize them.
    // Integer extent are necessary so that if we later double the extent,
    // the existing quadrant boundaries don’t change due to floating point error!
    if (extent.x0.isNaN()) {
        x0 = floor(x)
        y0 = floor(y)
        x1 = x0 + 1
        y1 = y0 + 1
    }

    // Otherwise, double repeatedly to cover.
    else if (x0 > x || x > x1 || y0 > y || y > y1) {
        var z = x1 - x0
        var node = root
        var parent: InternalNode<D>?

        val vertical = (y < (y0 + y1) / 2).toInt()
        val horizontal = (x < (x0 + x1) / 2).toInt()
        val i = vertical shl 1 or horizontal
        when (i) {
            0 -> {
                do {
                    parent = InternalNode(node, null, null, null)
                    node = parent
                    z *= 2
                    x1 = x0 + z
                    y1 = y0 + z
                } while (x > x1 || y > y1)
            }
            1 -> {
                do {
                    parent = InternalNode(null, node, null, null)
                    node = parent
                    z *= 2
                    x0 = x1 - z
                    y1 = y0 + z
                } while (x0 > x || y > y1)
            }
            2 -> {
                do {
                    parent = InternalNode(null, null, node, null)
                    node = parent
                    z *= 2
                    x1 = x0 + z
                    y0 = y1 - z
                } while (x > x1 || y0 > y)
            }
            3 -> {
                do {
                    parent = InternalNode(null, null, null, node)
                    node = parent
                    z *= 2
                    x0 = x1 - z
                    y0 = y1 - z
                } while (x0 > x || y0 > y)
            }
        }

        if (root != null && root is InternalNode) root = node
    }

    // If the quadtree covers the point already, just return.
    else return

    extent.x0 = x0
    extent.y0 = y0
    extent.x1 = x1
    extent.y1 = y1
}