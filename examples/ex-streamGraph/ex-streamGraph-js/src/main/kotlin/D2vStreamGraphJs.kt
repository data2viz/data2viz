import io.data2viz.examples.streamGraph.*
import io.data2viz.viz.JsCanvasRenderer
import kotlinx.html.*
import kotlinx.html.dom.append
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.HTMLSelectElement
import kotlin.browser.document

@Suppress("unused")

fun main(args: Array<String>) {

    val (canvas, context) = newCanvas(600, 600)

    fun redraw() {
        val viz = streamGraph()
        viz.renderer = JsCanvasRenderer(context)
        viz.render()
    }

    fun String.onSelect(selectChange: HTMLSelectElement.() -> Unit) {
        (document.querySelector(this)!! as HTMLSelectElement).apply {
            addEventListener("change", {
                selectChange()
                redraw()
            })

        }
    }

    val body = document.body!!

    body.append.div {
        label { +"Area spline interpolation" }
        select {
            id = "curve"
            curveOptions.forEach { option ->
                option {
                    selected = (option.first == "Basis")
                    +option.first
                }
            }
        }
    }
    body.append.div {

        label { +"Stacks offset" }
        select {
            id = "offset"
            offsetOptions.forEach { option ->
                option {
                    selected = (option.first == "Wigle")
                    +option.first
                }
            }
        }
    }
    body.append.div {
        label { +"Stacks order" }
        select {
            id = "order"
            orderOptions.forEach { option ->
                option {
                    selected = (option.first == "None")
                    +option.first
                }
            }
        }

    }


    "#curve".onSelect { vizConfig.curve = curveOptions[selectedIndex].second }
    "#offset".onSelect { vizConfig.offset = offsetOptions[selectedIndex].second }
    "#order".onSelect { vizConfig.order = orderOptions[selectedIndex].second }

    redraw()
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
