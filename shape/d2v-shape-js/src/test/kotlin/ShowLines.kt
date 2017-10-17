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

val points = arrayOf(Point(20,20), Point(50, 50), Point(80,20), Point(70,40), Point(150, 80), Point(180,20))
val radialPoints = arrayOf(Point(0,10), Point(1, 20), Point(2,10), Point(3,30), Point(4, 30), Point(5,20))


@JsName("showLines")
fun showLines() {
    render("Basis", curves.basis, points)
    render("BasisClosed", curves.basisClosed, points)
    render("BasisOpen", curves.basisOpen, points)
    render("Bundle", curves.bundle, points)
    render("Cardinal", curves.cardinal, points)
    render("CardinalClosed", curves.cardinalClosed, points)
    render("CardinalOpen", curves.cardinalOpen, points)
    render("CatmullRom", curves.catmullRom, points)
    render("CatmullRomClosed", curves.catmullRomClosed, points)
    render("CatmullRomOpen", curves.catmullRomOpen, points)
    render("Linear", curves.linear, points)
    render("LinearClosed", curves.linearClosed, points)
    render("MonotoneX", curves.monotoneX, points)
    render("MonotoneY", curves.monotoneY, points)
    render("Natural", curves.natural, points)
//    render("RadialLinear", curves.radialLinear, radialPoints)
//    render("RadialLinearClosed", curves.radialLinearClosed, radialPoints)
//    render("RadialBasis", curves.radialBasis, radialPoints)
    render("Step", curves.step, points)
    render("StepBefore", curves.stepBefore, points)
    render("StepAfter", curves.stepAfter, points)
}

private fun render(title: String, curve: (PathAdapter) -> Curve, arrayOfPoints: Array<Point>) {
    document.getElementById("d2vSamples")!!.appendElement("h2") {
        textContent = title
    }
    lineGenerator.curve = curve
    renderCanvas(arrayOfPoints)
    renderSvg(arrayOfPoints)
}

private fun renderCanvas(arrayOfPoints: Array<Point>) {

    fun newCanvas(): HTMLCanvasElement {
        val canvas = document.createElement("canvas") as HTMLCanvasElement
        val context = canvas.getContext("2d") as CanvasRenderingContext2D
        context.canvas.width  = 200
        context.canvas.height = 100
        document.getElementById("d2vSamples")!!.appendChild(canvas)
        return canvas
    }


    with(newCanvas().getContext("2d") as CanvasRenderingContext2D) {
        beginPath()
        beginPath()
        lineWidth = 1.0
        strokeStyle = "blue"
        lineGenerator.line(arrayOfPoints, CanvasDrawContext(this))
        stroke()
    }
}


private fun renderSvg(arrayOfPoints: Array<Point>) {

    fun createSvgElement(name: String): Element {
        val namespaceSvg = "http://www.w3.org/2000/svg"
        return document.createElementNS(namespaceSvg, name)
    }

    with(document.getElementById("d2vSamples")!!) {
        appendChild(createSvgElement("svg").apply {
            setAttribute("width", "200")
            setAttribute("height", "100")
            setAttribute("stroke", "green")
            setAttribute("fill", "none")
            appendChild(createSvgElement("path").apply {
                val line = lineGenerator.line(arrayOfPoints, SvgPath()).path
                setAttribute("d", line)
            })
        })
    }
}


