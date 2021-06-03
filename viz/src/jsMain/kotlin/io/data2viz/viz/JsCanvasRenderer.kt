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

import io.data2viz.color.*
import io.data2viz.timer.Timer
import io.data2viz.timer.timer
import org.w3c.dom.*
import kotlinx.browser.document
import kotlin.math.PI


/**
 * Allows to quickly bind a renderer on an existing canvas in the current document.
 * This is a way to display multiple visualizations in a unique page. Each viz
 * location in the page can be prepared with by providing a `canvas` tag with a
 * unique id.
 *
 * Raises an error if there is no canvas element with the given id.
 *
 * The viz is immediately rendered if the autoUpdate property of the viz configuration
 * is set to true.
 */
public fun Viz.bindRendererOn(canvasId: String) {
    val canvas = requireNotNull(document.getElementById(canvasId) as HTMLCanvasElement?)
        {"No canvas in the document corresponding to $canvasId"}
    bindRendererOn(canvas)
}

/**
 * Add a new canvas directly as a body child and render the viz on it.
 * It can be interesting for the playground where the code is executed in an iframe.
 */
public fun Viz.bindRendererOnNewCanvas() {
    val canvas = document.createElement("canvas") as HTMLCanvasElement
    val body = requireNotNull(document.querySelector("body"))
    body.appendChild(canvas)
    bindRendererOn(canvas)
}


/**
 * Creates a JsCanvasRenderer on the given canvas. If config.autoUpdate is true,
 * do immediately the rendering.
 *
 * If the pixel ratio of the screen differs from 1.0 (HPDi screen for example) a bigger
 * canvas is used with a scale.
 */
public fun Viz.bindRendererOn(canvas: HTMLCanvasElement) {
    val context = canvas.getContext("2d") as CanvasRenderingContext2D
    context.canvas.width = width.toInt()
    context.canvas.height = height.toInt()

    val pixelRatio = getPixelRatio()
    //manage HDPi screens
    if (pixelRatio > 1.0) {
        canvas.style.width = "${canvas.width}px"
        canvas.style.height = "${canvas.height}px"
        canvas.width = (canvas.width * pixelRatio).toInt()
        canvas.height = (canvas.height * pixelRatio).toInt()
        context.scale(pixelRatio, pixelRatio)
    }

    this.renderer = JsCanvasRenderer(context, this)

    if (config.autoUpdate) {
        render()
        startAnimations()
    }

}

internal fun getPixelRatio(): Double{
    var pixelRatio = 1.0
    js("""
        if((typeof window.devicePixelRatio) !== 'undefined') {
            pixelRatio = window.devicePixelRatio
    }
    """)

    return pixelRatio
}


public class JsCanvasRenderer(
    public val context: CanvasRenderingContext2D,
    override val viz: Viz
) : VizRenderer {

    private val animationTimers = mutableListOf<Timer>()

    override fun render() {
        context.clearRect(.0, .0, context.canvas.width.toDouble(), context.canvas.height.toDouble())
        viz.layers.forEach { layer ->
            if (layer.visible)
                layer.render(context)
        }
    }

    override fun startAnimations() {
        if (viz.animationTimers.isNotEmpty()) {
            viz.animationTimers.forEach { anim ->
                animationTimers += timer { time ->
                    anim(time)
                }
            }
            animationTimers += timer {
                render()
            }
        }
    }

    override fun stopAnimations() {
        animationTimers.forEach { it.stop() }
    }

}

