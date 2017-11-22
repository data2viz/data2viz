package io.data2viz.viz

import io.data2viz.color.Color
import io.data2viz.color.d2vColor
import io.data2viz.color.jfxColor
import javafx.beans.property.DoubleProperty
import javafx.scene.Group
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.shape.Circle
import kotlin.reflect.KProperty

/**
 * Bootstrap a VizContext in JavaFx environment
 */
fun Group.viz(init: VizContext.() -> Unit): VizContext {
    val vizContext = JFxVizContext(this)
    init(vizContext)
    return vizContext
}

class JFxVizContext(val parent: Group) : VizContext {

    override fun circle(init: CircleVizItem.() -> Unit): CircleVizItem {
        val circle = Circle()
        parent.children.add(circle)
        val item = CircleVizJfx(parent, circle)
        init(item)
        return item
    }
}


class CircleVizJfx(val parent: Group, val circle: Circle) : CircleVizItem, 
        HasFill by FillDelegate(circle),
        HasStroke by StrokeDelegate(circle),
        Transformable by TransformNodeDelegate(circle)
{

    override var cx: Double by DoublePropertyDelegate(circle.centerXProperty())
    override var cy: Double by DoublePropertyDelegate(circle.centerYProperty())
    override var radius: Double by DoublePropertyDelegate(circle.radiusProperty())
}

class TransformNodeDelegate(val node:Node) : Transformable {
    
    class TransformFx(val node: Node) : Transform{
        override fun translate(x: Double, y: Double) {
            node.transforms.add(javafx.scene.transform.Transform.translate(x,y))
        }

    }
    
    override fun transform(init: Transform.() -> Unit) {
        TransformFx(node).apply(init)
    }

}


class FillDelegate(val circle: Circle): HasFill {
    override var fill: Color?
        get() = (circle.fill as javafx.scene.paint.Color?)?.d2vColor
        set(value) { circle.fill = value?.jfxColor}
}

class StrokeDelegate(val circle: Circle): HasStroke {

    override var stroke: Color?
        get() = (circle.stroke as javafx.scene.paint.Color?)?.d2vColor
        set(value) { circle.stroke = value?.jfxColor}


    override var strokeWidth: Double?
        get() = circle.strokeWidth
        set(value) {if (value != null) circle.strokeWidth = value}
    
}

class DoublePropertyDelegate(val property: DoubleProperty) {
    operator fun getValue(circleVizJfx: CircleVizJfx, prop: KProperty<*>): Double = property.get()
    operator fun setValue(circleVizJfx: CircleVizJfx, prop: KProperty<*>, d: Double) {
        property.set(d)
    }
}


//class CircleBindingJfx<D>(val parent: Group, val circle: Circle, data: List<D>) :
//        CircleVizItem by CircleVizJfx(parent, circle),
//        Binding<D, CircleVizItem> by BindingImpl(data) {
//
//}

//
///**
// * Binding between a data and a visual item.
// *
// */
//interface Binding<D, V> {
//    val data: List<D>
//    var datum: D?
//    var vizItem: V?
//
//    var index: Int?
//
//    var onCreate: V.() -> Unit
//    var onUpdate: V.() -> Unit
//    var onRemove: V.() -> Unit
//}
//
//
//class BindingImpl<D, V>(override val data: List<D>) : Binding<D, V> {
//    override var datum: D? = null
//    override var vizItem: V? = null
//    override var index: Int? = null
//    override var onCreate: V.() -> Unit = {}
//    override var onUpdate: V.() -> Unit = {}
//    override var onRemove: V.() -> Unit = {}
//
//}
//
//fun <D> circleBinding(parent: Group, data: List<D>, init: CircleBindingJfx<D>.() -> Unit) {
//
//    val circle = Circle()
//    val binding = CircleBindingJfx(parent, circle, data)
//    parent.children.add(circle)
//    init(binding)
//}
//
