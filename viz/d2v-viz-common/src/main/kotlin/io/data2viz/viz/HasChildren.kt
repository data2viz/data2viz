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

interface HasChildren: Style {

    fun add(node: Node)
    fun remove(node: Node)
    fun clear()
    fun group(init: GroupNode.() -> Unit): GroupNode
    fun line(init: LineNode.() -> Unit): LineNode
    fun circle(init: CircleNode.() -> Unit): CircleNode
    fun rect(init: RectNode.() -> Unit): RectNode
    fun text(init: TextNode.() -> Unit): TextNode
    fun path(init: PathNode.() -> Unit): PathNode
}