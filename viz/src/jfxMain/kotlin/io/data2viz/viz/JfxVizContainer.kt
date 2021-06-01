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

import io.data2viz.ExperimentalD2V
import io.data2viz.geom.Size

import javafx.scene.canvas.Canvas
import javafx.scene.layout.Pane

/**
 * Attach a new [VizContainer] to a [Pane].
 */
@ExperimentalD2V
public fun Pane.newVizContainer(): VizContainer = JfxVizContainer(this)



internal class JfxVizContainer(private val pane: Pane) : VizContainer {

    private val allViz = mutableListOf<Viz>()

    public override val density: Double = 1.0

    override fun newViz(init: Viz.() -> Unit): Viz {
        val canvas = Canvas()
        val viz = Viz()
        allViz.add(viz)
        init(viz)
        viz.size = size
        JFxVizRenderer(canvas, viz)
        viz.updatePlatformSize()
        pane.children.add(canvas)
        viz.render()
        return viz
    }


    override var size: Size = Size(pane.width, pane.height)
        set(value) {
            field = value
            pane.setPrefSize(value.width, value.height)
            allViz.forEach {
                it.size = value
                it.updatePlatformSize()
            }
        }
}


private fun Viz.updatePlatformSize() {
    (renderer as? JFxVizRenderer)?.run {
        canvas.height = this@updatePlatformSize.height
        canvas.width = this@updatePlatformSize.width
    }
}

