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
 *
 */
class Viz(var activeLayer:Layer = Layer()): HasChildren by activeLayer{

    val config = VizConfig()

    var width: Double = 100.0
    var height: Double = 100.0

    val layers = mutableListOf(activeLayer)

    lateinit var renderer: VizRenderer

    fun render() {
        renderer.render(this)
    }

}

fun viz(init: Viz.() -> Unit): Viz  = Viz().apply(init)

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


interface Shape : HasFill, HasStroke


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

interface HasChildren {

    fun add(node: Node)
    fun remove(node: Node)
    fun group(init: Group.() -> Unit): Group
    fun line(init: Line.() -> Unit): Line
    fun circle(init: Circle.() -> Unit): Circle
    fun rect(init: Rect.() -> Unit): Rect
    fun text(init: Text.() -> Unit): Text
    fun path(init: PathNode.() -> Unit): PathNode
}
