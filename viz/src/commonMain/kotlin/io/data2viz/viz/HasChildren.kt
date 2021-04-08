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

package io.data2viz.viz


/**
 * Common interface of a parent node.
 * The parent node can add different type of children nodes:
 * Line, Circle, Rect, Text, Path, Image and Group.
 *
 * Viz, Group and Layer are parent nodes.
 */
public interface HasChildren {

    /**
     * Add a child node
     */
    public fun add(node: Node)

    /**
     * Remove a child node
     */
    public fun remove(node: Node)

    /**
     * Remove all children
     * TODO rename?
     */
    public fun clear()

    /**
     * Add a child group and init it with the `init` extension function
     */
    public fun group(init: GroupNode.() -> Unit): GroupNode

    /**
     * Add a child line and init it with the `init` extension function
     */
    public fun line(init: LineNode.() -> Unit): LineNode

    /**
     * Add a child circle and init it with the `init` extension function
     */
    public fun circle(init: CircleNode.() -> Unit): CircleNode

    /**
     * Add a child rectangle and init it with the `init` extension function
     */
    public fun rect(init: RectNode.() -> Unit): RectNode

    /**
     * Add a child text and init it with the `init` extension function
     */
    public fun text(init: TextNode.() -> Unit): TextNode

    /**
     * Add a child path and init it with the `init` extension function
     */
    public fun path(init: PathNode.() -> Unit): PathNode

    /**
     * Add a child image and init it with the `init` extension function
     */
    public fun image(init: ImageNode.() -> Unit): ImageNode

}