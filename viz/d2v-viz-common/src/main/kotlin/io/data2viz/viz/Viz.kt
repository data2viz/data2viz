package io.data2viz.viz


/**
 * Common interface to bootstrap visualization into different platform contexts.
 */
interface VizContext {
    fun circle(init: CircleVizItem.() -> Unit): CircleVizItem
}


interface VizItem

interface CircleVizItem : VizItem {
    var cx: Double
    var cy: Double
    var radius: Double
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

