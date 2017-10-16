import io.data2viz.shape.curve.Point
import io.data2viz.path.CanvasDrawContext
import io.data2viz.path.PathAdapter
import io.data2viz.path.SvgPath
import io.data2viz.shape.Curve
import io.data2viz.shape.curves
import io.data2viz.shape.line
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.Element
import org.w3c.dom.HTMLCanvasElement
import kotlin.browser.document
import kotlin.dom.appendElement


val lineGenerator = line<Point> {
    x = { it.x.toDouble() }
    y = { it.y.toDouble() }
//    defined = {!(it.x == 50 && it.y == 50)}
}

val points = arrayOf(Point(0,0), Point(50, 50), Point(100,0), Point(150, 100), Point(200,0))


@JsName("showLines")
fun showLines() {
    render("Basis", curves.basis)
    render("BasisClosed", curves.basisClosed)
    render("BasisOpen", curves.basisOpen)
    render("Bundle", curves.bundle)
    render("Linear", curves.linear)
    render("LinearClosed", curves.linearClosed)
}

private fun render(title: String, curve: (PathAdapter) -> Curve) {
    document.getElementById("d2vSamples")!!.appendElement("h2") {
        textContent = title
    }
    lineGenerator.curve = curve
    renderCanvas()
    renderSvg()
}

private fun renderCanvas() {

    fun newCanvas(): HTMLCanvasElement {
        val canvas = document.createElement("canvas") as HTMLCanvasElement
        val context = canvas.getContext("2d") as CanvasRenderingContext2D
        context.canvas.width  = 200
        context.canvas.height = 200
        document.getElementById("d2vSamples")!!.appendChild(canvas)
        return canvas
    }


    with(newCanvas().getContext("2d") as CanvasRenderingContext2D) {
        beginPath()
        beginPath()
        lineWidth = 1.0
        strokeStyle = "blue"
        lineGenerator.line(points, CanvasDrawContext(this))
        stroke()
    }
}


private fun renderSvg() {

    fun createSvgElement(name: String): Element {
        val namespaceSvg = "http://www.w3.org/2000/svg"
        return document.createElementNS(namespaceSvg, name)
    }

    with(document.getElementById("d2vSamples")!!) {
        appendChild(createSvgElement("svg").apply {
            setAttribute("width", "200")
            setAttribute("height", "200")
            setAttribute("stroke", "green")
            setAttribute("fill", "none")
            appendChild(createSvgElement("path").apply {
                val line = lineGenerator.line(points, SvgPath()).path
                setAttribute("d", line)
            })
        })
    }
}


