package io.data2viz.viz

import io.data2viz.color.ColorOrGradient
import io.data2viz.core.CssClass
import io.data2viz.path.PathAdapter

/**
 * Common interface to bootstrap visualization into different platform contexts.
 */
//interface VizContext : Group


/**
 * Base class for holding both memory version of
 */
class Viz {

    /**
     * The root element. All the visual elements of the current Viz are
     * children of this root.
     */
    val root = Group()

    lateinit var renderer: VizRenderer

    fun render() {
        renderer.render(this)
    }

}


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
