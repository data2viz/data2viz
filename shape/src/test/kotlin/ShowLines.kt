import curve.Point
import io.data2viz.path.SvgPath
import io.data2viz.shape.line
import org.w3c.dom.Element
import kotlin.browser.document


@JsName("showLines")
fun showLines() {

    fun createSvgElement(name: String): Element {
        val namespaceSvg = "http://www.w3.org/2000/svg"
       return document.createElementNS(namespaceSvg, name)
    }


    fun continuousLine(): Element {
        val points = (1..10).map {
            Point(10 * it, 20 * it)
        }.toTypedArray()

        return createSvgElement("path").apply {
            val lineGenerator = line<Point> {
                x = { it.x.toDouble() }
                y = { it.y.toDouble() }
            }
            val line = lineGenerator.line(points, SvgPath()).path
            setAttribute("d", line)
        }
    }

    with(document.body!!){
        appendChild(createSvgElement("svg").apply {
            setAttribute("width", "200")
            setAttribute("height", "200")
            setAttribute("stroke", "black")
            appendChild(continuousLine())
        })
    }

}
