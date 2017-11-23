package io.data2viz.viz

import io.data2viz.color.Color

/**
 * Common interface to bootstrap visualization into different platform contexts.
 */
interface VizContext : ParentItem

interface VizItem


interface ParentItem {
    fun group(init: ParentItem.() -> Unit): ParentItem
    fun circle(init: CircleVizItem.() -> Unit): CircleVizItem
    fun rect(init: RectVizItem.() -> Unit): RectVizItem
    fun line(init: LineVizItem.() -> Unit): LineVizItem
    fun text(init: TextVizItem.() -> Unit): TextVizItem
}

/**
 * Indicate an element on which we can apply a Transformation.
 * todo implement other transformation (rotate, ...)
 */
interface Transformable {
    fun transform(init: Transform.() -> Unit)
}

interface Transform {
    fun translate(x: Double = 0.0, y: Double = 0.0)
}


interface CircleVizItem : VizItem, Shape, Transformable {
    var cx: Double
    var cy: Double
    var radius: Double
}

interface LineVizItem : VizItem, Shape, Transformable {
    var x1: Double
    var y1: Double
    var x2: Double
    var y2: Double
}
interface RectVizItem : VizItem, Shape, Transformable {
    var x: Double
    var y: Double
    var width: Double
    var height: Double
    var rx: Double
    var ry: Double
}

interface TextVizItem : VizItem, Transformable {
    var x: Double
    var y: Double
    var textContent: String
}

interface Shape : HasFill, HasStroke


/**
 * All properties of stroke
 * Todo add remaining common properties
 */
interface HasStroke {
    var stroke: Color?
    var strokeWidth: Double?
}

interface HasFill {
    var fill: Color?
}

interface VizFactory<V : VizItem> {
    fun createVizItem(): V
}

//interface Binding<D>{
//    val data:List<D>
//    val datum:D
//    val index:Int
//}
//
//interface CircleBinding<D>: CircleVizItem, Binding<D>{
//
//}

//class CircleVizFactory : VizFactory<CircleVizItem> {
//    override fun createVizItem(): CircleVizItem = CircleVizJfx(Circle())
//}

