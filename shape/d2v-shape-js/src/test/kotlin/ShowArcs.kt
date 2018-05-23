import io.data2viz.path.CanvasDrawContext
import io.data2viz.path.SvgPath
import io.data2viz.shape.*
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.Element
import org.w3c.dom.HTMLCanvasElement
import kotlin.browser.document
import kotlin.dom.appendElement

data class ArcValues(
        val innerRadius: Double,
        val outerRadius: Double,
        val startAngle: Double,
        val endAngle: Double,
        val padAngle: Double,
        val cornerRadius: Double
)

data class WData(
        val age: Int,
        val name: String,
        val value: Int
)

@JsName("showArcs")
fun showArcs(arcValues: ArcValues) {
    val arc1 = ArcValues(.0, 50.0, .0, 1.8, .0, .0)
    val arc2 = ArcValues(30.0, 50.0, .0, 1.8, .0, .0)
    val arc2Bis = ArcValues(50.0, 30.0, .0, 1.8, .0, .0)
    val arc3 = ArcValues(.0, 30.0, .0, 1.8, .0, .0)
    val arc4 = ArcValues(.0, 50.0, .8, 6.0, .0, .0)
    val arc5 = ArcValues(.0, 50.0, .0, 1.8, .0, 5.0)
    val arc6 = ArcValues(30.0, 50.0, .0, 1.8, .0, 5.0)

    val generator = arc<ArcValues> {
        innerRadius = { it.innerRadius }
        outerRadius = { it.outerRadius }
        startAngle = { it.startAngle }
        endAngle = { it.endAngle }
//        padRadius = { it.padRadius }
        padAngle = { it.padAngle }
        cornerRadius = { it.cornerRadius }
    }

    render("REFERENCE", generator, arc1)
    render(" > innerRadius = 30", generator, arc2)
    render(" > innerRadius = 50, outerRadius = 30", generator, arc2Bis)
    render(" > outerRadius = 30", generator, arc3)
    render(" > startAngle = .8, endAngle = 6", generator, arc4)
    render(" > cornerRadius = 5", generator, arc5)
    render(" > cornerRadius = 5", generator, arc6)

    val workData = arrayOf(
            WData(20, "albert", 3),
            WData(30, "berenice", 5),
            WData(40, "charles", 9),
            WData(50, "dilbert", 16),
            WData(35, "eric", 22),
            WData(60, "frank", 15),
            WData(35, "georges", 2)
    )

    val pieGenerator = pie<WData> {
        value = { it.value.toDouble() }
    }
    renderPie("REFERENCE PIE", generator, pieGenerator, arc1, workData)
    renderPie(" > innerRadius = 30", generator, pieGenerator, arc2, workData)
    renderPie(" > innerRadius = 50, outerRadius = 30", generator, pieGenerator, arc2Bis, workData)
    renderPie(" > outerRadius = 30", generator, pieGenerator, arc3, workData)
    renderPie(" > cornerRadius = 5", generator, pieGenerator, arc5, workData)
    renderPie(" > cornerRadius = 5", generator, pieGenerator, arc6, workData)
}

private fun render(title: String, generator: ArcGenerator<ArcValues>, data: ArcValues) {
    document.getElementById("d2vSamples")!!.appendElement("h2") {
        textContent = title
    }
    renderSvg(generator.arc(data, SvgPath()), "#cfc", "d2vSamples")
    renderCanvas(generator, data)
}

private fun renderPie(title: String, arcGenerator: ArcGenerator<ArcValues>, generator: PieGenerator<WData>, arcParameters: ArcValues, pieData: Array<WData>) {
    document.getElementById("d2vSamples")!!.appendElement("h2") {
        textContent = title
    }
    val pie = generator.render(pieData)
    renderPieSvg(arcGenerator,pie,  arcParameters, "#cfc", "d2vSamples")
//    renderCanvas(generator, arcParameters)
}

private fun newCanvas(elementId: String): HTMLCanvasElement {
    val canvas = document.createElement("canvas") as HTMLCanvasElement
    val context = canvas.getContext("2d") as CanvasRenderingContext2D
    context.canvas.width = 200
    context.canvas.height = 100
    document.getElementById(elementId)!!.appendChild(canvas)
    return canvas
}

private fun renderCanvas(generator: ArcGenerator<ArcValues>, data: ArcValues) {
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

fun createSvgElement(name: String): Element {
    val namespaceSvg = "http://www.w3.org/2000/svg"
    return document.createElementNS(namespaceSvg, name)
}

private fun renderSvg(svgPath: SvgPath, fill: String, elementId: String) {
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

private fun renderPieSvg(arcGenerator: ArcGenerator<ArcValues>, arcParams: Array<ArcParams<WData>>, arcParameters: ArcValues, fill: String, elementId: String) {
    val svgPath = SvgPath()
    with(document.getElementById(elementId)!!) {
        appendChild(createSvgElement("svg").apply {
            setAttribute("width", "200")
            setAttribute("height", "100")
            setAttribute("stroke", "green")
            setAttribute("fill", fill)
            arcParams.forEach { arcParam ->
                appendChild(createSvgElement("path").apply {
                    arcGenerator.startAngle = {arcParam.startAngle}
                    arcGenerator.endAngle = {arcParam.endAngle}
                    val line = arcGenerator.arc(arcParameters, svgPath).path
                    setAttribute("d", line)
                    setAttribute("transform", "translate(100,50)");
                })
            }
        })
    }
}