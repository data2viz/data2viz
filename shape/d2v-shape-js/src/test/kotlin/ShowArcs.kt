import io.data2viz.path.CanvasDrawContext
import io.data2viz.path.PathAdapter
import io.data2viz.path.SvgPath
import io.data2viz.shape.*
import io.data2viz.shape.curve.Point
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.Element
import org.w3c.dom.HTMLCanvasElement
import kotlin.browser.document
import kotlin.dom.appendElement

data class ArcValues(
        val innerRadius:Double,
        val outerRadius:Double,
        val startAngle:Double,
        val endAngle:Double
)

@JsName("showArcs")
fun showArcs(value:ArcValues) {
    val arc1 = ArcValues(.0, 50.0, .0, 1.8)
    val arc2 = ArcValues(30.0, 50.0, .0, 1.8)
    val arc2Bis = ArcValues(50.0, 30.0, .0, 1.8)
    val arc3 = ArcValues(.0, 30.0, .0, 1.8)
    val arc4 = ArcValues(.0, 50.0, .8, 6.0)

    val generator = arc<ArcValues> {
        innerRadius = { it.innerRadius }
        outerRadius = { it.outerRadius }
        startAngle = { it.startAngle }
        endAngle = { it.endAngle }
    }

    render("REFERENCE", generator, arc1)
    render(" > innerRadius = 30", generator, arc2)
    render(" > innerRadius = 50, outerRadius = 30", generator, arc2Bis)
    render(" > outerRadius = 30", generator, arc3)
    render(" > startAngle = .8, endAngle = 6", generator, arc4)
}

private fun render(title: String, generator:ArcGenerator<ArcValues>, data:ArcValues) {
    document.getElementById("d2vSamples")!!.appendElement("h2") {
        textContent = title
    }
    renderSvg(generator.arc(data, SvgPath()), "#cfc", "d2vSamples")
    renderCanvas(generator, data)
}

private fun newCanvas(elementId: String): HTMLCanvasElement {
    val canvas = document.createElement("canvas") as HTMLCanvasElement
    val context = canvas.getContext("2d") as CanvasRenderingContext2D
    context.canvas.width  = 200
    context.canvas.height = 100
    document.getElementById(elementId)!!.appendChild(canvas)
    return canvas
}

private fun renderCanvas(generator:ArcGenerator<ArcValues>, data:ArcValues) {
    with(newCanvas("d2vSamples").getContext("2d") as CanvasRenderingContext2D) {
        beginPath()
        lineWidth = 1.0
        strokeStyle = "blue"
        fillStyle = "#ccf"
        translate(100.0, 50.0)
        generator.arc(data, CanvasDrawContext(this))
        stroke()
        fill()
    }
}

private fun renderSvg(svgPath: SvgPath, fill: String, elementId: String) {

    fun createSvgElement(name: String): Element {
        val namespaceSvg = "http://www.w3.org/2000/svg"
        return document.createElementNS(namespaceSvg, name)
    }

    with(document.getElementById(elementId)!!) {
        appendChild(createSvgElement("svg").apply {
            setAttribute("width", "200")
            setAttribute("height", "100")
            setAttribute("stroke", "green")
            setAttribute("fill", fill)
            appendChild(createSvgElement("path").apply {
                val line = svgPath.path
                setAttribute("d", line)
                setAttribute("transform", "translate(100,50)");
            })
        })
    }
}