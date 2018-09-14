package io.data2viz.examples.letsMakeABarchart

import io.data2viz.viz.JsCanvasRenderer
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import kotlin.browser.document


@Suppress("unused")

fun main(args: Array<String>) {
    val (canvas, context) = newCanvas(600, 600)
    val viz = barchartViz()
    viz.renderer = JsCanvasRenderer(context)
    viz.render()
    println("barchart rendered")

}


data class CanvasContext(val canvas:HTMLCanvasElement, val context2D: CanvasRenderingContext2D)

private fun newCanvas(width: Int, height: Int): CanvasContext {
    val canvas = document.createElement("canvas") as HTMLCanvasElement
    val context = canvas.getContext("2d") as CanvasRenderingContext2D
    context.canvas.width = width
    context.canvas.height = height
    document.querySelector("body")!!.appendChild(canvas)
    return CanvasContext(canvas, context)
}
