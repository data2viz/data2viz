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

package io.data2viz.quadtree

/**
 * Returns the total number of data in the quadtree.
 */
public fun <D> Quadtree<D>.size(): Int {
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
