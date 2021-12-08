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

package io.data2viz.viz

import io.data2viz.geom.Size

public class HeadlessVizContainer(
    override val density: Double = 1.0,
    private val resizableSupport: ResizableSupport = ResizableSupport()
):
    VizContainer,
    Resizable by resizableSupport{

    override var size: Size = Size(100.0, 100.0)
        set(value) {
            field = value
            vizList.forEach {
                it.size = value
            }
            resizableSupport.notifyNewSize(value)
        }

    private val _vizList = mutableListOf<Viz>()
    public val vizList: List<Viz>
        get() = _vizList

    override fun newViz(init: Viz.() -> Unit): Viz {
        val viz = Viz().apply(init)
        _vizList.add(viz)
        return viz
    }

}