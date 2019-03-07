package io.data2viz.viz

import io.data2viz.geom.*
import org.w3c.dom.*
import org.w3c.dom.events.*


fun HTMLCanvasElement.addEventListener() {

	addEventListener("mousemove", {}, null)
}


// https://developer.mozilla.org/en-US/docs/Web/Events/mouseenter

fun MouseEvent.toKMouseEvent(): KMouseMoveEvent =

	KMouseMoveEvent(Point(clientX.toDouble(), clientY.toDouble()))
