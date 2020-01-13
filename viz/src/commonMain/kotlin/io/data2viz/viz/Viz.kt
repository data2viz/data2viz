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

import io.data2viz.color.ColorOrGradient
import io.data2viz.color.Colors
import io.data2viz.geom.HasSize
import io.data2viz.timer.Timer

/**
 * Viz is the base element of a visualization.
 *
 * It is associated with a renderer which is used to perform the rendering depending on
 * the current platform.
 *
 * It has at least one layer (the activeLayer). Layers provides a way of managing what is
 * drawn on the background and what is drawn on frontend. The rendering process start with
 * the layer with the lower index.
 *
 * Viz respects the `HasChildren` interface. It is possible to directly invoke some creation
 * function on its context. The created element are then added to the active layer. It provides
 * a very easy way to start a visualization.
 *
 * Viz width and height can be seen as the target size of the visualisation on a standard
 * screen (no hi resolution) for a web rendering. These sizes will then be used during the
 * rendering process on each platform to adapt the visualisation to the target device, taking
 * in account the resolution of the screen, and the configuration of the viz.
 */
class Viz(var activeLayer: Layer = Layer()) : HasChildren by activeLayer, HasSize {

    private val style: Style = StyleImpl()


    //Style delegation
    override var fill: ColorOrGradient?
        get() = style.fill
        set(value) {
            style.fill = value
        }

    override var stroke: ColorOrGradient?
        get() = style.stroke
        set(value) {
            style.stroke = value
        }

    override var strokeWidth: Double?
        get() = style.strokeWidth
        set(value) {
            style.strokeWidth = value
        }

    override var textColor: ColorOrGradient?
        get() = style.textColor
        set(value) {
            style.textColor = value
        }

    override var hAlign: TextHAlign
        get() = style.hAlign
        set(value) {
            style.hAlign = value
        }

    override var vAlign: TextVAlign
        get() = style.vAlign
        set(value) {
            style.vAlign = value
        }

    init {
        activeLayer.parent = this
        textColor = Colors.Web.black
    }

    val config = VizConfig()

    override var width: Double = 100.0
    override var height: Double = 100.0

    val layers = mutableListOf(activeLayer)

    private var resizeBehavior: ((Double, Double) -> Unit)? = null

    private val eventListeners = mutableListOf<KEventHandle<*>>()

    var renderer: VizRenderer? = null
    set(newValue) {
        val oldValue = field
        field = newValue
        eventListeners.forEach {
            oldValue?.removeEventHandle(it)
            newValue?.addEventHandle(it)
        }

    }

    /**
     * This is the common function call to add all type of events listener to a Viz.
     *
     * 	val handle = on(KPointerClick) { evt -> println("Pointer click:: ${evt.pos}")}
     *
     * 	A disposable handle is returned, allowing the caller to remove the listener.
     */
    fun <T : KEvent> on(eventListener: KEventListener<T>, listener: (T) -> Unit): Disposable {
        val eventHandle = KEventHandle(eventListener, listener) {
            eventListeners.remove(it) //todo why?

            renderer?.apply {
                removeEventHandle(it)
            }
        }
        eventListeners.add(eventHandle)
        renderer?.addEventHandle(eventHandle)

        return eventHandle
    }


    fun render() {
        renderer!!.render()
    }

    fun startAnimations() {
        renderer!!.startAnimations()
    }

    fun stopAnimations() {
        renderer!!.stopAnimations()
    }

    internal val animationTimers = mutableListOf<Timer.(Double) -> Unit>()

    /**
     * Add an animation timer. The given block is an extension function on a Timer.
     * It will be executed inside a timer with the elapsed time in ms as a parameter.
     * It is possible to stop the timer from the block by calling `stop()` function
     * from the block.
     */
    fun animation(block: Timer.(Double) -> Unit) {
        animationTimers.add(block)
    }

    @Deprecated("Should use an animation timer", ReplaceWith("animation(block)"))
    fun onFrame(block: (Double) -> Unit) {
        animation { block(it) }
    }

    fun onResize(block: (newWidth: Double, newHeight: Double) -> Unit) {
        resizeBehavior = block
    }

    fun resize(newWidth: Double, newHeight: Double) {
        resizeBehavior?.invoke(newWidth, newHeight)
    }

    fun layer(): Layer {
        val layer = Layer().also { it.parent = this }
        layers.add(layer)
        activeLayer = layer
        return layer
    }

}


fun viz(init: Viz.() -> Unit): Viz = Viz().apply(init)


interface StateableElement {
    var stateManager: StateManager?
}


/**
 * Indicate an element on which we can apply a Transformation.
 * todo implement other transformation (rotate, ...)
 */
class Transform {

    internal val transformations = mutableListOf<AtomicTransformation>()

    fun translate(x: Double = 0.0, y: Double = 0.0) {
        transformations.add(Translation(x, y))
    }

    fun rotate(delta: Double) {
        transformations.add(Rotation(delta))
    }

}

interface AtomicTransformation

data class Translation(var x: Double = 0.0, var y: Double = 0.0): AtomicTransformation
data class Rotation(var delta: Double = 0.0): AtomicTransformation


/**
 * All properties of stroke
 * Todo add remaining common properties
 */
interface HasStroke {

    var stroke: ColorOrGradient?
    var strokeWidth: Double?
}

interface HasFill {
    var fill: ColorOrGradient?
}


data class Margins(val top: Double, val right: Double = top, val bottom: Double = top, val left: Double = right) {
    val hMargins = right + left
    val vMargins = top + bottom
}

interface HasTransform {
    val transform: Transform?
}
