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

// TODO : test radials
// TODO : add KT tests on generated svg shape (test the SVG string path by comparing to d3 path)

val lineGenerator = line<Point> {
    x = { it.x.toDouble() }
    y = { it.y.toDouble() }
}

val lineGeneratorWithHoles = line<Point> {
    x = { it.x.toDouble() }
    y = { it.y.toDouble() }
    defined = {it.x != 70 }
}

val areaGenerator = area<Point> {
    x {it.x.toDouble()}
    y0 = { 60.0 }
    y1 = { it.y.toDouble() }
}

val points = arrayOf(
        Point(20,20),
        Point(50, 50),
        Point(70, 70),
        Point(80,20),
        Point(70,40),
        Point(100,60),
        Point(150, 80),
        Point(155, 80),
        Point(180,20))
val radialPoints = arrayOf(Point(0,10), Point(1, 20), Point(2,10), Point(3,30), Point(4, 30), Point(5,20))


@JsName("showLines")
fun showLines() {
    render("Basis",                     lineGenerator, curves.basis, points)
    render("BasisClosed",               lineGenerator, curves.basisClosed, points)
    render("BasisOpen",                 lineGenerator, curves.basisOpen, points)
    render("Bundle (NO AREA IN D3)",    lineGenerator, curves.bundle, points)
    render("Cardinal",                  lineGenerator, curves.cardinal, points)
    render("CardinalClosed",            lineGenerator, curves.cardinalClosed, points)
    render("CardinalOpen",              lineGenerator, curves.cardinalOpen, points)
    render("CatmullRom",                lineGenerator, curves.catmullRom, points)
    render("CatmullRomClosed",          lineGenerator, curves.catmullRomClosed, points)
    render("CatmullRomOpen",            lineGenerator, curves.catmullRomOpen, points)
    render("Linear",                    lineGenerator, curves.linear, points)
    render("LinearClosed",              lineGenerator, curves.linearClosed, points)
    render("MonotoneX",                 lineGenerator, curves.monotoneX, points)
    render("MonotoneY",                 lineGenerator, curves.monotoneY, points)
    render("Natural",                   lineGenerator, curves.natural, points)
    render("Step",                      lineGenerator, curves.step, points)
    render("StepBefore",                lineGenerator, curves.stepBefore, points)
    render("StepAfter",                 lineGenerator, curves.stepAfter, points)

    render("Basis (MISSING POINTS)",                     lineGeneratorWithHoles, curves.basis, points)
    render("BasisClosed (MISSING POINTS)",               lineGeneratorWithHoles, curves.basisClosed, points)
    render("BasisOpen (MISSING POINTS)",                 lineGeneratorWithHoles, curves.basisOpen, points)
    render("Bundle (MISSING POINTS)",                    lineGeneratorWithHoles, curves.bundle, points)
    render("Cardinal (MISSING POINTS)",                  lineGeneratorWithHoles, curves.cardinal, points)
    render("CardinalClosed (MISSING POINTS)",            lineGeneratorWithHoles, curves.cardinalClosed, points)
    render("CardinalOpen (MISSING POINTS)",              lineGeneratorWithHoles, curves.cardinalOpen, points)
    render("CatmullRom (MISSING POINTS)",                lineGeneratorWithHoles, curves.catmullRom, points)
    render("CatmullRomClosed (MISSING POINTS)",          lineGeneratorWithHoles, curves.catmullRomClosed, points)
    render("CatmullRomOpen (MISSING POINTS)",            lineGeneratorWithHoles, curves.catmullRomOpen, points)
    render("Linear (MISSING POINTS)",                    lineGeneratorWithHoles, curves.linear, points)
    render("LinearClosed (MISSING POINTS)",              lineGeneratorWithHoles, curves.linearClosed, points)
    render("MonotoneX (MISSING POINTS)",                 lineGeneratorWithHoles, curves.monotoneX, points)
    render("MonotoneY (MISSING POINTS)",                 lineGeneratorWithHoles, curves.monotoneY, points)
    render("Natural (MISSING POINTS)",                   lineGeneratorWithHoles, curves.natural, points)
    render("Step (MISSING POINTS)",                      lineGeneratorWithHoles, curves.step, points)
    render("StepBefore (MISSING POINTS)",                lineGeneratorWithHoles, curves.stepBefore, points)
    render("StepAfter (MISSING POINTS)",                 lineGeneratorWithHoles, curves.stepAfter, points)

//    render("RadialLinear", curves.radialLinear, radialPoints)
//    render("RadialLinearClosed", curves.radialLinearClosed, radialPoints)
//    render("RadialBasis", curves.radialBasis, radialPoints)

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

private fun render(title: String, lineGen:LineGenerator<Point>, curve: (PathAdapter) -> Curve, arrayOfPoints: Array<Point>) {
    document.getElementById("d2vSamples")!!.appendElement("h2") {
        textContent = title
    }
    lineGen.curve = curve
    renderCanvas(lineGen, arrayOfPoints)
    renderSvg(lineGen.line(arrayOfPoints, SvgPath()), "none", "d2vSamples")
}

private fun renderArea(title: String, curve: (PathAdapter) -> Curve, arrayOfPoints: Array<Point>) {
    document.getElementById("d2vSamplesArea")!!.appendElement("h2") {
        textContent = " "
    }
    areaGenerator.curve = curve
    renderAreaCanvas(arrayOfPoints)
    renderSvg(areaGenerator.area(arrayOfPoints, SvgPath()), "#cfc", "d2vSamplesArea")
}

private fun newCanvas(elementId: String): HTMLCanvasElement {
    val canvas = document.createElement("canvas") as HTMLCanvasElement
    val context = canvas.getContext("2d") as CanvasRenderingContext2D
    context.canvas.width  = 200
    context.canvas.height = 100
    document.getElementById(elementId)!!.appendChild(canvas)
    return canvas
}

private fun renderCanvas(lineGen: LineGenerator<Point>, arrayOfPoints: Array<Point>) {
    with(newCanvas("d2vSamples").getContext("2d") as CanvasRenderingContext2D) {
        beginPath()
        lineWidth = 1.0
        strokeStyle = "blue"
        lineGen.line(arrayOfPoints, CanvasDrawContext(this))
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

