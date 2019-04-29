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
//
//        val listener = addEventListener(EventType.MouseMove) { evt ->
//            println(evt.pos)
//        }
//
    }

    val config = VizConfig()

    override var width: Double = 100.0
    override var height: Double = 100.0

    val layers = mutableListOf(activeLayer)

    private var resizeBehavior: ((Double, Double) -> Unit)? = null

    var renderer: VizRenderer? = null
    set(newValue) {
        val oldValue = field


        field = newValue

        eventListeners.forEach {
            if(oldValue != null) {
                oldValue.removeEventHandle(it)
            }

            if(newValue != null) {
                newValue.addNativeEventListener(it)
            }
        }

    }


    val eventListeners = mutableListOf<KEventHandle<*>>()

    fun <T> on(eventListener: KEventListener<T>, listener: (T) -> Unit): KEventHandle<T> where  T : KEvent {
        val eventHandle = KEventHandle(eventListener, listener) {
            eventListeners.remove(it)

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
    var translate: Translation? = null
    fun translate(x: Double = 0.0, y: Double = 0.0) {
        translate = Translation(x, y)
    }

    var rotate: Rotation? = null
    fun rotate(delta: Double) {
        rotate = Rotation(delta)
    }

    operator fun plusAssign(transform: Transform) {
        translate?.apply {
            x += transform.translate?.x ?: .0
            y += transform.translate?.y ?: .0
        }

        rotate?.apply {
            delta += transform.rotate?.delta ?: .0
        }

    }

    operator fun minusAssign(transform: Transform) {
        translate?.apply {
            x -= transform.translate?.x ?: .0
            y -= transform.translate?.y ?: .0
        }
        rotate?.apply {
            delta -= transform.rotate?.delta ?: .0
        }

    }
}

data class Translation(var x: Double = 0.0, var y: Double = 0.0)
data class Rotation(var delta: Double = 0.0)


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
