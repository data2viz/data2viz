import io.data2viz.shape.curve.Point
import io.data2viz.path.CanvasDrawContext
import io.data2viz.path.PathAdapter
import io.data2viz.path.SvgPath
import io.data2viz.shape.*
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.Element
import org.w3c.dom.HTMLCanvasElement
import kotlin.browser.document
import kotlin.dom.appendElement


class Person(val age:Int)

val populationLineGenerator = line<Person>{
    x = {it.age.toDouble()}
    y = {it.age.toDouble()}
    curve = curves.bundle
}


val lineGenerator = line<Point> {
    x = { it.x.toDouble() }
    y = { it.y.toDouble() }


//    defined = {!(it.x == 50 && it.y == 50)}
}

val areaGenerator = area<Point> {
    x {it.x.toDouble()}
    y0 = { 60.0 }
    y1 = { it.y.toDouble() }
}

val points = arrayOf(Point(20,20), Point(50, 50), Point(80,20), Point(70,40), Point(150, 80), Point(180,20))
val radialPoints = arrayOf(Point(0,10), Point(1, 20), Point(2,10), Point(3,30), Point(4, 30), Point(5,20))


@JsName("showLines")
fun showLines() {
    render("Basis", curves.basis, points)
    render("BasisClosed", curves.basisClosed, points)
    render("BasisOpen", curves.basisOpen, points)
    render("Bundle (NO AREA IN D3)", curves.bundle, points)
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

    renderArea("Basis", curves.basis, points)
    renderArea("BasisClosed", curves.basisClosed, points)
    renderArea("BasisOpen", curves.basisOpen, points)
    renderArea("Bundle", curves.bundle, points)
    renderArea("Cardinal", curves.cardinal, points)
    renderArea("CardinalClosed", curves.cardinalClosed, points)
    renderArea("CardinalOpen", curves.cardinalOpen, points)
    renderArea("CatmullRom", curves.catmullRom, points)
    renderArea("CatmullRomClosed", curves.catmullRomClosed, points)
    renderArea("CatmullRomOpen", curves.catmullRomOpen, points)
    renderArea("Linear", curves.linear, points)
    renderArea("LinearClosed", curves.linearClosed, points)
    renderArea("MonotoneX", curves.monotoneX, points)
    renderArea("MonotoneY", curves.monotoneY, points)
    renderArea("Natural", curves.natural, points)
//    renderArea("RadialLinear", curves.radialLinear, radialPoints)
//    renderArea("RadialLinearClosed", curves.radialLinearClosed, radialPoints)
//    renderArea("RadialBasis", curves.radialBasis, radialPoints)
    renderArea("Step", curves.step, points)
    renderArea("StepBefore", curves.stepBefore, points)
    renderArea("StepAfter", curves.stepAfter, points)
}

private fun render(title: String, curve: (PathAdapter) -> Curve, arrayOfPoints: Array<Point>) {
    document.getElementById("d2vSamples")!!.appendElement("h2") {
        textContent = title
    }
    lineGenerator.curve = curve
    renderCanvas(arrayOfPoints)
    renderSvg(lineGenerator.line(arrayOfPoints, SvgPath()), "none", "d2vSamples")
}

private fun renderArea(title: String, curve: (PathAdapter) -> Curve, arrayOfPoints: Array<Point>) {
    document.getElementById("d2vSamplesArea")!!.appendElement("h2") {
        textContent = " "
    }
    areaGenerator.curve = curve
    renderAreaCanvas(arrayOfPoints)
    renderSvg(areaGenerator.area(arrayOfPoints, SvgPath()), "#cfc", "d2vSamplesArea")
}

fun newCanvas(elementId: String): HTMLCanvasElement {
    val canvas = document.createElement("canvas") as HTMLCanvasElement
    val context = canvas.getContext("2d") as CanvasRenderingContext2D
    context.canvas.width  = 200
    context.canvas.height = 100
    document.getElementById(elementId)!!.appendChild(canvas)
    return canvas
}

private fun renderCanvas(arrayOfPoints: Array<Point>) {
    with(newCanvas("d2vSamples").getContext("2d") as CanvasRenderingContext2D) {
        beginPath()
        lineWidth = 1.0
        strokeStyle = "blue"
        lineGenerator.line(arrayOfPoints, CanvasDrawContext(this))
        stroke()
    }
}

private fun renderAreaCanvas(arrayOfPoints: Array<Point>) {
    with(newCanvas("d2vSamplesArea").getContext("2d") as CanvasRenderingContext2D) {
        beginPath()
        lineWidth = 1.0
        strokeStyle = "blue"
        fillStyle = "#ccf"
        areaGenerator.area(arrayOfPoints, CanvasDrawContext(this))
        fill()
        stroke()
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
            })
        })
    }
}

