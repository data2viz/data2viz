package io.data2viz.viz


/**
 * Common interface to bootstrap visualization into different platform contexts.
 */
interface VizContext


interface VizItem

interface CircleVizItem : VizItem {
    var cx: Double
    var cy: Double
    var radius: Double
}

interface VizFactory<V : VizItem> {
    fun createVizItem(): V
}

//class CircleVizFactory : VizFactory<CircleVizItem> {
//    override fun createVizItem(): CircleVizItem = CircleVizJfx(Circle())
//}

