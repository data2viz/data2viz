import io.data2viz.path.CanvasDrawContext
import io.data2viz.path.SvgPath
import io.data2viz.shape.*
import io.data2viz.shape.symbol.*
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.Element
import org.w3c.dom.HTMLCanvasElement
import kotlin.browser.document
import kotlin.dom.appendElement

val symbolGenerator = symbol<String> {
    size = { 1000.0 }
    type = {
        when (it) {
            "Circle" -> Circle()
            "Cross" -> Cross()
            "Diamond" -> Diamond()
            "Square" -> Square()
            "Star" -> Star()
            "Triangle" -> Triangle()
            "Wye" -> Wye()
            else -> Circle()
        }
    }
}

@JsName("showSymbols")
fun showSymbols() {
    symbols.forEach { symbolName ->
        render(symbolGenerator, symbolName)
    }
}

fun render(symbolGenerator: SymbolGenerator<String>, symbolName: String) {
    document.getElementById("d2vSamples")!!.appendElement("h2") {
        textContent = symbolName
    }

    with(newCanvas("d2vSamples").getContext("2d") as CanvasRenderingContext2D) {
        beginPath()
        lineWidth = 1.0
        strokeStyle = "blue"
        fillStyle = "#ccf"
        translate(32.0, 32.0)
        symbolGenerator.symbol(symbolName, CanvasDrawContext(this))
        fill()
        stroke()
    }

    fun createSvgElement(name: String): Element {
        val namespaceSvg = "http://www.w3.org/2000/svg"
        return document.createElementNS(namespaceSvg, name)
    }

    with(document.getElementById("d2vSamples")!!) {
        appendChild(createSvgElement("svg").apply {
            setAttribute("width", "200")
            setAttribute("height", "100")
            setAttribute("stroke", "green")
            appendChild(createSvgElement("path").apply {
                val line = symbolGenerator.symbol(symbolName, SvgPath()).path
                setAttribute("d", line)
                setAttribute("fill", "#cfc")
                setAttribute("transform", "translate(32,32)");
            })
        })
    }
}

private fun newCanvas(elementId: String): HTMLCanvasElement {
    val canvas = document.createElement("canvas") as HTMLCanvasElement
    val context = canvas.getContext("2d") as CanvasRenderingContext2D
    context.canvas.width = 200
    context.canvas.height = 100
    document.getElementById(elementId)!!.appendChild(canvas)
    return canvas
}