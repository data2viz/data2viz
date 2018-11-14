package io.data2viz.viz

import org.w3c.dom.*

fun LineNode.render(context: CanvasRenderingContext2D) {
    context.beginPath()
    context.moveTo(x1, y1)
    context.lineTo(x2, y2)
    context.stroke()
}