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

open class GroupNode : Node(),
        HasChildren,
        HasTransform {


    override var transform: Transform? = null

    fun transform(init: Transform.() -> Unit){
        transform = Transform().apply(init)
    }

    val children = mutableListOf<Node>()

    override fun add(node: Node) {
        children.add(node)
        node.parent = this
    }

    override fun remove(node: Node) {
        node.parent = null
        children.remove(node)
    }

    override fun clear() {
        children.clear()
    }

    override fun group(init: GroupNode.() -> Unit): GroupNode = GroupNode()
            .apply(init)
            .also { add(it) }

    override fun line(init: LineNode.() -> Unit): LineNode = LineNode()
            .apply(init)
            .also { add(it) }

    override fun circle(init: CircleNode.() -> Unit): CircleNode = CircleNode()
            .apply(init)
            .also { add(it) }

    override fun rect(init: RectNode.() -> Unit): RectNode = RectNode()
            .apply(init)
            .also { add(it) }

    override fun text(init: TextNode.() -> Unit): TextNode = TextNode()
            .apply(init)
            .also { add(it) }

    override fun path(init: PathNode.() -> Unit): PathNode = PathNode()
            .apply(init)
            .also { add(it) }

}