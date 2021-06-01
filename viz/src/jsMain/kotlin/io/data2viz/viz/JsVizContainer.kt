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
import kotlinx.browser.document
import kotlinx.dom.appendElement
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.HTMLDivElement

/**
 * Attach a new [VizContainer] to a [HTMLDivElement].
 */
@ExperimentalD2V
public fun HTMLDivElement.newVizContainer(): VizContainer = JsVizContainer(this)

/**
 * Create a new VizContainer and append it to the document body.
 */
@ExperimentalD2V
public fun newVizContainer(): VizContainer {
    val div = document.createElement("div") as HTMLDivElement
    document.body?.appendChild(div)
    return div.newVizContainer()
}


internal class JsVizContainer(private val div: HTMLDivElement) : VizContainer {

    private val allViz = mutableListOf<Viz>()

    public override val density: Double = 1.0

    override var size: Size = Size(100.0, 100.0)
        set(value) {
            field = value
            updateDivStyle()
            allViz.forEach {
                it.size = value
                it.updatePlatformSize()
            }
        }


    init {
        updateDivStyle()
    }

    private fun updateDivStyle() {
        val newStyle = "position: relative;height: ${size.height}px;width: ${size.width}px"
        div.setAttribute("style", newStyle)
    }

    override fun newViz(init: Viz.() -> Unit): Viz {
        val viz = Viz()
        allViz.add(viz)

        val canvas = div.appendElement("canvas") {} as HTMLCanvasElement
        canvas.style.position = "absolute"
        canvas.style.left = "0"
        canvas.style.top = "0"
        canvas.style.zIndex = "${allViz.size}"


        init(viz)
        viz.size = size

        viz.bindRendererOn(canvas)
        viz.updatePlatformSize()
        viz.render()
        return viz
    }
}


private fun Viz.updatePlatformSize() {

    val jsRenderer = renderer as? JsCanvasRenderer

    jsRenderer?.run {
        context.canvas.width = width.toInt()
        context.canvas.height = height.toInt()
        val canvas = context.canvas

        val pixelRatio = getPixelRatio()
        //manage HDPi screens
        if (pixelRatio > 1.0) {
            canvas.style.width = "${canvas.width}px"
            canvas.style.height = "${canvas.height}px"
            canvas.width = (canvas.width * pixelRatio).toInt()
            canvas.height = (canvas.height * pixelRatio).toInt()
            context.scale(pixelRatio, pixelRatio)
        }

    }

}
