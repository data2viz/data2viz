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

import io.data2viz.timer.Timer
import io.data2viz.timer.timer
import javafx.scene.canvas.*
import kotlin.math.PI


/**
 * JFx Canvas version. See https://docs.oracle.com/javafx/2/canvas/jfxpub-canvas.htm
 */
class JFxVizRenderer(
    val canvas: Canvas,
    override val viz: Viz) : VizRenderer {

    private val animationTimers = mutableListOf<Timer>()

    init {
        viz.renderer = this
    }

    override fun render() {
        val gc = canvas.graphicsContext2D
        gc.clearRect(.0, .0, canvas.width, canvas.height)
        viz.layers.forEach { layer ->
            if (layer.visible)
                layer.render(gc)
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

fun GraphicsContext.addTransform(transform: Transform) {
    translate(transform.translate?.x ?: .0, transform.translate?.y ?:.0)
    rotate(+ (transform.rotate?.delta ?: .0) * 180 / PI)
}

fun GraphicsContext.removeTransform(transform: Transform) {
    translate(-(transform.translate?.x ?:.0), -(transform.translate?.y ?:.0))
    rotate(- (transform.rotate?.delta ?: .0) * 180 / PI)
}
