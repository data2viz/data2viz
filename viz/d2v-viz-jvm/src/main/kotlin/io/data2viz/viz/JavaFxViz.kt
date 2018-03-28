package io.data2viz.viz

import io.data2viz.color.*
import io.data2viz.core.CssClass
import io.data2viz.path.PathAdapter
import io.data2viz.path.PathJfx
import javafx.beans.property.DoubleProperty
import javafx.scene.Node
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.Stop
import javafx.scene.shape.Path
import javafx.scene.shape.StrokeLineCap
import kotlin.reflect.KProperty

typealias JfxLinearGradient = javafx.scene.paint.LinearGradient
typealias JfxGroup          = javafx.scene.Group
typealias JfxShape          = javafx.scene.shape.Shape
typealias JfxPath           = javafx.scene.shape.Path
typealias JfxCircle         = javafx.scene.shape.Circle
typealias JfxLine           = javafx.scene.shape.Line
typealias JfxRectangle      = javafx.scene.shape.Rectangle
typealias JfxText           = javafx.scene.text.Text

actual fun newGroup(): Group        = GroupJfx()
actual fun newLine(): Line          = LineJfx()
actual fun newRect(): Rect          = RectJfx()
actual fun newCircle(): Circle      = CircleJfx()
actual fun newText(): Text          = TextJfx()
actual fun newPath(): PathVizElement          = PathVizJfx()

/**
 * Bootstrap a VizContext in JavaFx environment
 */
fun JfxGroup.viz(init: VizContext.() -> Unit): VizContext {
    val vizContext = GroupJfx(this)
    init(vizContext)
    return vizContext
}

class GroupJfx(override val jfxElement: JfxGroup = JfxGroup()) : VizContext, JfxVizElement,
        StyledElement by StyleDelegate(jfxElement),
        Transformable by TransformNodeDelegate(jfxElement){
    
    override fun add(vizElement: VizElement) {
        jfxElement.children.add((vizElement as JfxVizElement).jfxElement)
    }
    override fun remove(vizElement: VizElement) {
        jfxElement.children.remove((vizElement as JfxVizElement).jfxElement)
    }

    override fun path(init: PathVizElement.() -> Unit): PathVizElement {
        val pathJfx = PathVizJfx()
        jfxElement.children.add(pathJfx.jfxElement)
        init(pathJfx)
        return pathJfx
    }

    override fun setStyle(style: String) {
        jfxElement.style = style
    }


    override fun circle(init: Circle.() -> Unit): Circle {
        val circle = CircleJfx()
        jfxElement.children.add(circle.jfxElement)
        init(circle)
        return circle
    }

    override fun group(init: Group.() -> Unit): Group {
        val group = GroupJfx(JfxGroup())
        jfxElement.children.add(group.jfxElement)
        init(group)
        return  group
    }

    override fun line(init: Line.() -> Unit): Line {
        val line = LineJfx()
        jfxElement.children.add(line.jfxElement)
        init(line)
        return line
    }

    override fun rect(init: Rect.() -> Unit): Rect {
        val rectangle = RectJfx()
        jfxElement.children.add(rectangle.jfxElement)
        init(rectangle)
        return rectangle
    }

    override fun text(init: Text.() -> Unit): Text {
        val text = TextJfx()
        jfxElement.children.add(text.jfxElement)
        init(text)
        return text
    }
}


interface JfxVizElement {
    val jfxElement: Node
}


class PathVizJfx(internal val pathJfx: PathJfx = PathJfx(), override val jfxElement: JfxPath = pathJfx.path) : PathVizElement, JfxVizElement,
        PathAdapter by pathJfx,
        HasFill by FillDelegate(jfxElement),
        HasStroke by StrokeDelegate(jfxElement),
        Transformable by TransformNodeDelegate(jfxElement) {
    
    init {
        jfxElement.strokeLineCap = StrokeLineCap.BUTT 
    }
    
}
    

class CircleJfx(override val jfxElement: JfxCircle = JfxCircle()) : Circle, JfxVizElement,
        HasFill by FillDelegate(jfxElement),
        StyledElement by StyleDelegate(jfxElement),
        HasStroke by StrokeDelegate(jfxElement),
        Transformable by TransformNodeDelegate(jfxElement)
{

    override var cx: Double by DoublePropertyDelegate(jfxElement.centerXProperty())
    override var cy: Double by DoublePropertyDelegate(jfxElement.centerYProperty())
    override var radius: Double by DoublePropertyDelegate(jfxElement.radiusProperty())
}

class LineJfx(override val jfxElement: JfxLine = JfxLine()) : Line, JfxVizElement,
        StyledElement by StyleDelegate(jfxElement),
        HasFill by FillDelegate(jfxElement),
        HasStroke by StrokeDelegate(jfxElement),
        Transformable by TransformNodeDelegate(jfxElement) {
    override var x1: Double by DoublePropertyDelegate(jfxElement.startXProperty())
    override var y1: Double by DoublePropertyDelegate(jfxElement.startYProperty())
    override var x2: Double by DoublePropertyDelegate(jfxElement.endXProperty())
    override var y2: Double by DoublePropertyDelegate(jfxElement.endYProperty())
}

class RectJfx(override val jfxElement: JfxRectangle = JfxRectangle()) : Rect, JfxVizElement,
        StyledElement by StyleDelegate(jfxElement),
        HasFill by FillDelegate(jfxElement),
        HasStroke by StrokeDelegate(jfxElement),
        Transformable by TransformNodeDelegate(jfxElement)
{
    override fun addState(initState: Rect.() -> Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun percentToState(percent: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override var x: Double by DoublePropertyDelegate(jfxElement.xProperty())
    override var y: Double by DoublePropertyDelegate(jfxElement.yProperty())
    override var width: Double by DoublePropertyDelegate(jfxElement.widthProperty())
    override var height: Double by DoublePropertyDelegate(jfxElement.heightProperty())
    override var rx: Double by DoublePropertyDelegate(jfxElement.arcWidthProperty())
    override var ry: Double by DoublePropertyDelegate(jfxElement.arcHeightProperty())

}

class TransformNodeDelegate(val node:Node) : Transformable {

    class TransformFx(val node: Node) : Transform{
        override fun translate(x: Double, y: Double) {
            val transforms = node.transforms.filterIsInstance(javafx.scene.transform.Transform::class.java)
            node.transforms.removeAll(transforms)
            node.transforms.add(javafx.scene.transform.Transform.translate(x,y))
        }

    }

    override fun transform(init: Transform.() -> Unit) {
        TransformFx(node).apply(init)
    }

}

class StyleDelegate(val node: Node): StyledElement {
    override fun addClass(cssClass: CssClass) {
        node.styleClass.add(cssClass.name)
    }
}

fun LinearGradient.toLinearGradientJFX(): JfxLinearGradient  = JfxLinearGradient(x1, y1, x2, y2,
    false,
    CycleMethod.NO_CYCLE, colorStops.toStops())

private fun List<LinearGradient.ColorStop>.toStops(): List<Stop>? =  map { Stop(it.percent, it.color.jfxColor) }

class FillDelegate(val shape: JfxShape) : HasFill {
    override var fill: ColorOrGradient?

        get() = (shape.fill as javafx.scene.paint.Color?)?.d2vColor

        set(value) {
            shape.fill = when (value) {
                null -> null
                is Color -> value.jfxColor
                is LinearGradient -> value.toLinearGradientJFX()
                else ->  throw IllegalStateException("$value not managed")
            }
        }
}

class StrokeDelegate(val shape: JfxShape): HasStroke {

    override var stroke: ColorOrGradient?
        get() = (shape.stroke as javafx.scene.paint.Color?)?.d2vColor
        set(value) { 
            shape.stroke = when (value) {
                null -> null
                is Color -> value.jfxColor
                is LinearGradient -> value.toLinearGradientJFX()
                else -> throw IllegalStateException("$value not managed")
            }
        }


    override var strokeWidth: Double?
        get() = shape.strokeWidth
        set(value) {if (value != null) shape.strokeWidth = value}

    init {
//        stroke = colors.black
    }

}

class DoublePropertyDelegate(val property: DoubleProperty) {
    operator fun getValue(vizElement: VizElement, prop: KProperty<*>): Double = property.get()
    operator fun setValue(vizElement: VizElement, prop: KProperty<*>, d: Double) {
        property.set(d)
    }
}
