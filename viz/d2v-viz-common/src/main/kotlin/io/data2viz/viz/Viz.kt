package io.data2viz.viz

import io.data2viz.color.ColorOrGradient
import io.data2viz.geom.HasSize

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
class Viz(var activeLayer:Layer = Layer()): HasChildren by activeLayer, HasSize{

    init {
        activeLayer.parent = this
    }

    private val style:Style = StyleImpl()
    val config = VizConfig()

    override var width: Double = 100.0
    override var height: Double = 100.0

    val layers = mutableListOf(activeLayer)

    private var resizeBehavior:((Double, Double) -> Unit)? = null

    lateinit var renderer: VizRenderer

    fun render() {
        renderer.render()
    }

    fun startAnimations(){
        renderer.startAnimations()
    }

    fun stopAnimations(){
        renderer.stopAnimations()
    }

    internal val animations = mutableListOf<(Double)-> Unit>()

    fun onFrame(block: (Double) -> Unit) {
        animations.add(block)
    }

    fun onResize(block: (newWidth:Double, newHeight:Double) -> Unit) {
        resizeBehavior = block
    }

    fun resize(newWidth:Double, newHeight:Double) {
        resizeBehavior?.invoke(newWidth, newHeight)
    }

    fun layer(): Layer {
        val layer = Layer().also { it.parent = this }
        layers.add(layer)
        activeLayer = layer
        return layer
    }

    //Style delegation
    override var fill: ColorOrGradient?
        get() = style.fill
        set(value) {style.fill = value}
    override var stroke: ColorOrGradient?
        get() = style.stroke
        set(value) {style.stroke = value}
    override var strokeWidth: Double?
        get() = style.strokeWidth
        set(value) {style.strokeWidth = value}
    override var anchor: TextAnchor
        get() = style.anchor
        set(value) {style.anchor = value}
    override var baseline: TextAlignmentBaseline
        get() = style.baseline
        set(value) {style.baseline = value}


}


fun viz(init: Viz.() -> Unit): Viz  = Viz().apply(init)


interface StateableElement {
    var stateManager: StateManager?
}


/**
 * Indicate an element on which we can apply a Transformation.
 * todo implement other transformation (rotate, ...)
 */
class Transform {
    var translate:Translation? = null
    fun translate(x: Double = 0.0, y: Double = 0.0) {
        translate = Translation(x,y)
    }

    var rotate:Rotation? = null
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
data class Rotation(var delta:Double = 0.0)


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
    val transform:Transform?
}
