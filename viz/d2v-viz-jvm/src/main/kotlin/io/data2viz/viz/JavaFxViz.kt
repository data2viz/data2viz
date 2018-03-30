package io.data2viz.viz

import io.data2viz.color.*
import io.data2viz.core.CssClass
import io.data2viz.path.PathAdapter
import io.data2viz.path.PathJfx
import javafx.scene.Node
import javafx.scene.shape.StrokeLineCap

typealias JfxLinearGradient = javafx.scene.paint.LinearGradient
typealias JfxRadialGradient = javafx.scene.paint.RadialGradient
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
        Transformable by TransformNodeDelegate(jfxElement) {
    
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
        return group
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
        HasStroke by StrokeDelegate(jfxElement),
        Transformable by TransformNodeDelegate(jfxElement) {

    override var fill: ColorOrGradient? by FillDelegate(this)

    init {
        jfxElement.strokeLineCap = StrokeLineCap.BUTT 
    }
    
}
    

class CircleJfx(override val jfxElement: JfxCircle = JfxCircle()) : Circle, JfxVizElement,
        StyledElement by StyleDelegate(jfxElement),
        HasStroke by StrokeDelegate(jfxElement),
        Transformable by TransformNodeDelegate(jfxElement)
{
    override var stateManager: StateManager? = null
    override var fill: ColorOrGradient? by FillDelegate(this)
    override var cx: Double by DoublePropertyDelegate(jfxElement.centerXProperty())
    override var cy: Double by DoublePropertyDelegate(jfxElement.centerYProperty())
    override var radius: Double by DoublePropertyDelegate(jfxElement.radiusProperty())
}

class LineJfx(override val jfxElement: JfxLine = JfxLine()) : Line, JfxVizElement,
        StyledElement by StyleDelegate(jfxElement),
        HasStroke by StrokeDelegate(jfxElement),
        Transformable by TransformNodeDelegate(jfxElement) {

    override var fill: ColorOrGradient? by FillDelegate(this)
    override var x1: Double by DoublePropertyDelegate(jfxElement.startXProperty())
    override var y1: Double by DoublePropertyDelegate(jfxElement.startYProperty())
    override var x2: Double by DoublePropertyDelegate(jfxElement.endXProperty())
    override var y2: Double by DoublePropertyDelegate(jfxElement.endYProperty())
}

class RectJfx(override val jfxElement: JfxRectangle = JfxRectangle()) : Rect, JfxVizElement,
        StyledElement by StyleDelegate(jfxElement),
        HasStroke by StrokeDelegate(jfxElement),
        Transformable by TransformNodeDelegate(jfxElement)
{
    override var fill: ColorOrGradient? by FillDelegate(this)
    override var stateManager: StateManager? = null

    override var x: Double by DoublePropertyDelegate(jfxElement.xProperty())
    override var y: Double by DoublePropertyDelegate(jfxElement.yProperty())
    override var width: Double by DoublePropertyDelegate(jfxElement.widthProperty())
    override var height: Double by DoublePropertyDelegate(jfxElement.heightProperty())
    override var rx: Double by DoublePropertyDelegate(jfxElement.arcWidthProperty())
    override var ry: Double by DoublePropertyDelegate(jfxElement.arcHeightProperty())

}

class TransformNodeDelegate(val node: Node) : Transformable {

    class TransformFx(val node: Node) : Transform {
        override fun rotate(degrees: Double, x: Double, y: Double) {
            node.transforms.add(javafx.scene.transform.Rotate.rotate(degrees, x, y))
        }

        override fun translate(x: Double, y: Double) {
            node.transforms.add(javafx.scene.transform.Transform.translate(x, y))
        }

    }

    override fun transform(init: Transform.() -> Unit) {
        TransformFx(node).apply {
            val transforms = node.transforms.filterIsInstance(javafx.scene.transform.Transform::class.java)
            node.transforms.removeAll(transforms)
            init(this)
        }

    }

}

class StyleDelegate(val node: Node) : StyledElement {
    override fun addClass(cssClass: CssClass) {
        node.styleClass.add(cssClass.name)
    }
}

