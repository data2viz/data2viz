import curve.Point
import io.data2viz.path.CanvasDrawContext
import io.data2viz.path.SvgPath
import io.data2viz.shape.line
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.Element
import org.w3c.dom.HTMLCanvasElement
import kotlin.browser.document
import kotlin.browser.window


val canvas = initalizeCanvas()

fun initalizeCanvas(): HTMLCanvasElement {
    val canvas = document.createElement("canvas") as HTMLCanvasElement
    val context = canvas.getContext("2d") as CanvasRenderingContext2D
    context.canvas.width  = 200
    context.canvas.height = 200;
    document.body!!.appendChild(canvas)
    return canvas
}

val context: CanvasRenderingContext2D
    get() {
        return canvas.getContext("2d") as CanvasRenderingContext2D
    }


val lineGenerator = line<Point> {
    x = { it.x.toDouble() }
    y = { it.y.toDouble() }
}

val points = (1..10).map {
    Point(10 * it, 20 * it)
}.toTypedArray()



@JsName("showLines")
fun showLines() {

    //SVG
    fun createSvgElement(name: String): Element {
        val namespaceSvg = "http://www.w3.org/2000/svg"
       return document.createElementNS(namespaceSvg, name)
    }

    with(document.body!!){
        appendChild(createSvgElement("svg").apply {
            setAttribute("width", "200")
            setAttribute("height", "200")
            setAttribute("stroke", "black")
            appendChild(createSvgElement("path").apply {
                val line = lineGenerator.line(points, SvgPath()).path
                setAttribute("d", line)
            })
        })
    }

    //Canvas
    context.beginPath();
    context.lineWidth = 1.5;
    context.strokeStyle = "steelblue";
    lineGenerator.line(points, CanvasDrawContext(context))
    context.stroke();
}
