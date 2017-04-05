import org.w3c.dom.Element
import kotlin.browser.document
import kotlin.browser.window
import kotlin.js.Date
import kotlin.js.Math

fun svgPerfs() {

    val width = 960
    val height = 500

    val nbElements = 2500

    val fps = document.querySelector("#fps span")!!

    document.querySelector("#num span")?.textContent = nbElements.toString()

    val svg = document.querySelector("svg")!!.apply {
        setAttribute("width", "$width")
        setAttribute("height", "$height")
    }

    val circles = Array(nbElements, { Circle() })

    fun Element.append(name:String, init:Element.() -> Unit): Element {
        val element = document.createElementNS(this.namespaceURI, name)
        appendChild(element)
        init(element)
        return element
    }

    val g  = svg.append("g") {
        circles.forEach {
            val svgCircle = this@append.append("circle") {
                setAttribute("cx", "10")
                setAttribute("cy", "10")
                setAttribute("fill", "#555")
                setAttribute("r", "1")
            }
            it.element = svgCircle
        }
    }

    val x = { domain: Double -> 96.0 * domain + 480 }
    val y = { domain: Double -> 50.0 * domain + 250 }

    fun Circle.toPosition() = "translate( ${x(this.x)} , ${y(this.y)})"
    fun Circle.speedToRadius() = "${(1.0 + 1000.0 * Math.abs(vx * vy)).coerceAtMost(10.0)}"

    val average_fps = mutableListOf<Int>();
    var fpsValue = 0;
    var time0 = Date().getTime()
    var time1 = Date().getTime()

    fun update() {
        circles.forEach { circle ->
            circle.updatePositionAndSpeed()
            circle.element.setAttribute("transform", circle.toPosition())
            circle.element.setAttribute("r", circle.speedToRadius() )
        }

        time1 = Date().getTime()
        if(time1 != time0){
            val curFps = Math.round(1000.0/ (time1 - time0))
            average_fps.add(curFps)
            if(average_fps.size == 10){
                fps.textContent =  average_fps.average().toString()
                average_fps.clear()
            }
        }

        time0 = time1
        window.requestAnimationFrame { update() }
    }
    update()
}

class Circle() {
    var x = 0.0
    var y = 0.0
    var vx = 0.0
    var vy = 0.0
    lateinit var element: Element

    fun updatePositionAndSpeed() {
        x += vx
        y += vy
        vx += .04 * (Math.random() - .5) - .05 * vx - .0005 * x
        vy += .04 * (Math.random() - .5) - .05 * vy - .0005 * y
    }
}
