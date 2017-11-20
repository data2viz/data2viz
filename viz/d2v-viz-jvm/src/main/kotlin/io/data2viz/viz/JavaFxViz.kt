package io.data2viz.viz

import javafx.scene.Group
import javafx.scene.shape.Circle


/**
 * Bootstrap a VizContext in JavaFx environment
 */
fun Group.viz(init: JFxVizContext.() -> Unit): VizContext {
    val vizContext = JFxVizContext(this)
    init(vizContext)
    return vizContext
}


class JFxVizContext(val parent: Group) : VizContext {

//    internal inline fun <reified D> circle(clazz: String, init: Binding<D, CircleVizItem>.() -> Unit): VizFactory<CircleVizItem> {
//        return CircleVizFactory()
//    }

    fun circle(init: CircleVizItem.() -> Unit): CircleVizItem {
        val circle = Circle()
        parent.children.add(circle)
        val item = CircleVizJfx(circle)
        init(item)
        return item
    }

}


class CircleVizJfx(val circle: Circle) : CircleVizItem {
    override var cx: Double
        get() = circle.centerX
        set(value) {
            circle.centerX = value
        }
    override var cy: Double
        get() = circle.centerY
        set(value) {
            circle.centerY = value
        }
    override var radius: Double
        get() = circle.radius
        set(value) {
            circle.radius = value
        }
}

















/**
 * Binding between a data and a visual item.
 *
 */
class Binding<D, V>(val data: List<D>) {
    var datum: D? = null
    var vizItem: V? = null

    var index: Int? = null

    var onCreate: V.() -> Unit = {}
    var onUpdate: V.() -> Unit = {}
    var onRemove: V.() -> Unit = {}
}


