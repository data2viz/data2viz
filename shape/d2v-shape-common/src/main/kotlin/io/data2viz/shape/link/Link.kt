package io.data2viz.shape.link

import io.data2viz.geom.Path
import io.data2viz.shape.const


/**
 * Instanciate and configure an horizontal link builder
 */
fun <D> linkBuilderH(init: LinkBuilder<D>.() -> Unit) = LinkBuilder<D>().apply {
    curve = this::curveHorizontal
    init()
}

/**
 * Instanciates and configure a vertical link builder.
 */
fun <D> linkBuilderV(init: LinkBuilder<D>.() -> Unit) = LinkBuilder<D>().apply {
    curve = this::curveVertical
    init()
}

/**
 * The link shape generates a smooth cubic Bézier curve from a source point to a target point.
 * The tangents of the curve at the start and end are either vertical, horizontal or radial.
 */
class LinkBuilder<D> {

    var x0: (D) -> Double = const(.0)
    var x1: (D) -> Double = const(.0)
    var y0: (D) -> Double = const(.0)
    var y1: (D) -> Double = const(.0)
    var curve: (Path, Double, Double, Double, Double) -> Unit = ::curveHorizontal

    fun <C : Path> link(data:D, path:C) {
        curve(path, x0(data), y0(data), x1(data), y1(data))
    }

    internal fun <C : Path> curveHorizontal(path:C, x0:Double, y0:Double, x1:Double, y1:Double) {
        path.moveTo(x0, y0)
        val newX0 = (x0 + x1) / 2
        path.bezierCurveTo(newX0, y0, newX0, y1, x1, y1)
    }

    internal fun <C : Path> curveVertical(path:C, x0:Double, y0:Double, x1:Double, y1:Double) {
        path.moveTo(x0, y0)
        val newY0 = (y0 + y1) / 2
        path.bezierCurveTo(x0, newY0, x1, newY0, x1, y1)
    }
}