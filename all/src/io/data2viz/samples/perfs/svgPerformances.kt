package io.data2viz.samples.perfs

import org.w3c.dom.Element
import kotlin.browser.document
import kotlin.browser.window
import kotlin.js.Date
import kotlin.js.Math

fun svgPerfs() {

    val viz = selectOrCreateSVG {
        width = 800
        height = 800
    }

    val particules = Array(2500, { RandomParticule() })

    val circles = particules.map { particule ->
        val circle = svg.circle(stroke = "#555", fill = "#aaa")
        circle.datum(particule)
        viz.append(circle)
        circle
    }

    val domainToSvg = scale.linear.pointsToPoints(
            Point.origin    to  viz.element.center,
            Point(5.0, 5.0) to  viz.element.topRight
    )

    //Display number of particules, and FPS
    selectTextElement("#num span") { text = particules.size.toString()}
    val fpsCalculator = FpsCalculator("#fps span")

    fun render() {
        circles.forEach { circle ->
            val particule:RandomParticule = circle.datum()
            particule.updatePositionAndSpeed()
            circle.transform(translate(domainToSvg(particule.position)))
            circle.radius = (1.0 + 1000.0 * Math.abs(particule.speed.vx * particule.speed.vy)).coerceAtMost(10.0)
        }
        fpsCalculator.updateFPS()
        window.requestAnimationFrame { render() }
    }
    render()
}

/**
 * A particule with a 2D position that will move randomly beetween approximatively -5,-5 and +5,+5
 */
class RandomParticule {

    var position = Point()
    var speed = Speed()

    fun updatePositionAndSpeed() {
        position += speed
        speed += Speed(
                .04 * (Math.random() - .5) - .05 * speed.vx - .0005 * position.x,
                .04 * (Math.random() - .5) - .05 * speed.vy - .0005 * position.y
        )
    }
}

//------------   API test -----------------------

inline fun <reified T> ElementWrapper.datum(): T = this.element.asDynamic().__data__
fun ElementWrapper.datum(value: Any) { this.element.asDynamic().__data__ = value}



// scale ----------------
data class DomainToViz<out A, out B>(
        val domain: A,
        val viz: B
)

infix fun <A, B> A.to(that: B): DomainToViz<A, B> = DomainToViz(this, that)

class scale {

    object linear {

        fun pointsToPoints(start: DomainToViz<Point, Point>, end: DomainToViz<Point, Point>) =
                { pt:Point -> Point(
                    doublesToDoubles(start.domain.x to start.viz.x, end.domain.x to end.viz.x)(pt.x),
                    doublesToDoubles(start.domain.y to start.viz.y, end.domain.y to end.viz.y)(pt.y)) }

        fun doublesToDoubles(start: DomainToViz<Double, Double>, end: DomainToViz<Double, Double>) =
            {domain:Double -> domain * (end.viz - start.viz) / (end.domain - start.domain) + start.viz}

    }
}

fun selectTextElement(selector:String, init: TextElement.() -> Unit = {}): Selection<TextElement> {
    val element = document.querySelector(selector)!!
    return Selection(TextElement(element), init)
}

fun selectOrCreateSVG(init: SVGElement.() -> Unit): Selection<SVGElement> {
    var svgElement = document.querySelector("svg")
    if (svgElement == null){
        console.log("creating element")
        svgElement = document.createElementNS(namespace.svg, "svg")
        document.querySelector("body")!!.append(svgElement)
    }
    return Selection(SVGElement(svgElement), init)
}


class Selection<out T : ElementWrapper>(val element: T, init: T.() -> Unit = {}) {
    init {
        init(element)
    }
    fun append(element: ElementWrapper) {
        this.element.element.appendChild(element.element)
    }
}

interface ElementWrapper {
    val element: Element
}

class TextElement(override val element: Element): HasText, ElementWrapper{
    override var text: String?
        get() = element.textContent
        set(value) { element.textContent = value}
}

class SVGElement(override val element: Element) : Has2D, ElementWrapper {
    override var width: Number
        get() = element.getAttribute("width")!!.toDouble()
        set(value) {
            element.setAttribute("width", value.toString())
        }
    override var height: Number
        get() = element.getAttribute("height")!!.toDouble()
        set(value) {
            element.setAttribute("height", value.toString())
        }
}

interface HasText {
    var text: String?
}


interface HasWidth {
    var width: Number
}

interface HasHeight {
    var height: Number
}

interface Has2D : HasHeight, HasWidth {
    val center: Point
    get() = Point(width.toDouble()/2, height.toDouble()/2)

    val topRight: Point
    get() = Point(width.toDouble(), 0.0)
}

class svg {
    companion object {
        fun circle(radius: Double = 1.0, stroke: String = "#555", fill: String = "#fff") =
                CircleElement(document.createElementNS(namespace.svg, "circle")).apply {
                    this.radius = radius
                    this.stroke = stroke
                    this.fill = fill
                }
    }
}

class CircleElement(override val element: Element) : ElementWrapper {

    var stroke: String  = ""
        set(value) = setAttribute("stroke", value)

    var fill: String = ""
        set(value) = setAttribute("fill", value)

    init {
        stroke = "#555"
        fill = "#777"
    }

    companion object {
    }

    var radius: Double = 1.0
        set(value) = setAttribute("r", value = value.toString())

    fun transform(value: String) {
        setAttribute("transform", value)
    }

    fun transform(transformFunction: () -> String) {
        setAttribute("transform", transformFunction())
    }

    private fun setAttribute(name: String, value: String) {
        this.element.setAttribute(name, value)
    }
}

fun translate(point: Point) = translate(point.x, point.y)
fun translate(x: Double, y: Double) = "translate($x,$y)"

data class Point(val x: Double = 0.0, val y: Double = 0.0) {

    companion object {
        val origin = Point()
    }
    operator fun plus(speed: Speed) = Point(x + speed.vx, y + speed.vy)
}

data class Speed(val vx: Double = 0.0, val vy: Double = 0.0) {
    operator fun plus(speed: Speed) = Speed(vx + speed.vx, vy + speed.vy)
}

/**
 * Utility class used to calculate the FPS.
 * updateAndShowFPS() must be called at each update.
 * The given fps element is set to the current FPS each 10 calls to updateFPS.
 */
class FpsCalculator(val fpsSelector: String) {
    private val fps = selectTextElement(fpsSelector)
    private val average_fps = mutableListOf<Int>();
    private var time0 = Date().getTime()
    private var time1 = Date().getTime()

    fun updateFPS() {
        time1 = Date().getTime()
        if (time1 != time0) {
            val curFps = Math.round(1000.0 / (time1 - time0))
            average_fps.add(curFps)
            if (average_fps.size == 10) {
                fps.element.text = average_fps.average().toString()
                average_fps.clear()
            }
        }
        time0 = time1
    }
}
