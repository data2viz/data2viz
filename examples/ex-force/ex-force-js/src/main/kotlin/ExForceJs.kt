
import io.data2viz.timer.timer
import io.data2viz.viz.JsCanvasRenderer
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import kotlin.browser.document

fun main(args: Array<String>) {
    val (canvas, context) = newCanvas(width.toInt(), height.toInt())
    val viz = forcesViz
    viz.renderer = JsCanvasRenderer(context)

    timer {
        viz.render()
    }

}

data class CanvasContext(val canvas: HTMLCanvasElement, val context2D: CanvasRenderingContext2D)

private fun newCanvas(width: Int, height: Int): CanvasContext {
    val canvas = document.createElement("canvas") as HTMLCanvasElement
    val context = canvas.getContext("2d") as CanvasRenderingContext2D
    context.canvas.width = width
    context.canvas.height = height
    document.querySelector("body")!!.appendChild(canvas)
    return CanvasContext(canvas, context)
}
