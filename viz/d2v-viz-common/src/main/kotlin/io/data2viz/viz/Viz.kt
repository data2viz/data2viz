package io.data2viz.viz

import io.data2viz.color.ColorOrGradient


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
class Viz(var activeLayer:Layer = Layer()): HasChildren by activeLayer{

    val config = VizConfig()

    var width: Double = 100.0
    var height: Double = 100.0

    val layers = mutableListOf(activeLayer)

    private var resizeBehavior:((Double, Double) -> Unit)? = null

    lateinit var renderer: VizRenderer

    fun render() {
        renderer.render(this)
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
        val layer = Layer()
        layers.add(layer)
        activeLayer = layer
        return layer
    }
}

fun viz(init: Viz.() -> Unit): Viz  = Viz().apply(init)


@Deprecated("Old design, should be replaced.")
interface VizElement


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

    operator fun plusAssign(transform: Transform) {
        translate?.apply {
            x += transform.translate?.x ?: .0
            y += transform.translate?.y ?: .0
        }
    }

    operator fun minusAssign(transform: Transform) {
        translate?.apply {
            x -= transform.translate?.x ?: .0
            y -= transform.translate?.y ?: .0
        }
    }
}

data class Translation(var x: Double = 0.0, var y: Double = 0.0)


/**
 * All properties of stroke
 * Todo add remaining common properties
 */
interface HasStroke: HasStyle {

    @Deprecated("Use style.stroke", ReplaceWith("style.stroke"))
    var stroke: ColorOrGradient?

    @Deprecated("Use style.strokeWidth", ReplaceWith("style.strokeWidth"))
    var strokeWidth: Double?
}

interface HasFill: HasStyle {
    @Deprecated("Use style.fill", ReplaceWith("style.fill"))
    var fill: ColorOrGradient?
}


data class Margins(val top: Double, val right: Double = top, val bottom: Double = top, val left: Double = right) {
    val hMargins = right + left
    val vMargins = top + bottom
}

interface HasTransform {
    val transform:Transform?
}

interface HasChildren: HasStyle {

    fun add(node: Node)
    fun remove(node: Node)
    fun clear()
    fun group(init: Group.() -> Unit): Group
    fun line(init: Line.() -> Unit): Line
    fun circle(init: Circle.() -> Unit): Circle
    fun rect(init: Rect.() -> Unit): Rect
    fun text(init: Text.() -> Unit): Text
    fun path(init: PathNode.() -> Unit): PathNode
}
