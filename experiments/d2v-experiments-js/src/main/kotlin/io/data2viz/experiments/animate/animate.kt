@file:Suppress("unused")

package io.data2viz.experiments.animate

import io.data2viz.interpolate.*
import io.data2viz.interpolate.identity
import io.data2viz.interpolate.interpolateNumber
import io.data2viz.core.namespace
import io.data2viz.timer.timer
import org.w3c.dom.Element
import kotlin.browser.document


fun animate() {

    val easeFunctions = listOf(::identity, ::cubicInOut, ::cubicIn, ::cubicOut, ::quad, ::sin, ::circleIn, ::circleOut )
    val barHeight = 20

    svg {
        width = 1000
        height = barHeight * easeFunctions.size

        easeFunctions.forEachIndexed { index, easeFunction ->

            rect {
                height = barHeight
                width = 100

                transform {
                    translate(y = barHeight * index)
                }

                transition(2000, easeFunction) {
                    width = this@svg.width
                }
            }
        }
    }
}


//------------   API test -----------------------

fun svg(init: SVGElement.() -> Unit = {}): Selection<SVGElement> {
    var svgElement = document.querySelector("svg")
    if (svgElement == null){
        svgElement = svg.createSVGElement("svg")
        document.querySelector("body")!!.append(svgElement)
    }
    return Selection(SVGElement(svgElement), init)
}


class Transform {
    private val commands = mutableMapOf<String, String>()

    fun translate(x:Number = 0, y:Number = 0) { commands.put("translate", "translate($x, $y)") }
    fun scale(x:Number = 1, y:Number = x) { commands.put("scale", "scale($x, $y)") }
    fun skewX(a:Number) { commands.put("skewX", "skewX($a)") }
    fun skewY(a:Number) { commands.put("skewY", "skewX($a)") }
    fun rotate(angle:Number, x: Number = 0, y: Number = 0) { commands.put("rotate", "rotate($angle, $x, $y") }

    fun toCommand(): String  = commands.values.joinToString(" ")
}

class Selection<out T : ElementWrapper>(val element: T, init: T.() -> Unit = {}) {
    init {
        init(element)
    }
}

interface ElementWrapper {
    val element: Element
}



/**
 * The root of an SVG visualization
 */
class SVGElement(override var element: Element) : Has2D, ElementWrapper {

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

    fun rect(init: RectElement.() -> Unit){
        val rect = svg.rect()
        init(rect)
        element.append(rect.element)
    }

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

        fun createSVGElement(name:String) = document.createElementNS(namespace.svg, name)

        fun rect(width: Double = 100.0, height: Double = 100.0, stroke: String = "#555", fill: String = "#fff") =
                RectElement(createSVGElement("rect")).apply {
                    this.width = width
                    this.height = height
                    this.stroke = stroke
                    this.fill = fill
                }

    }
}

class RectElement(override val element: Element) : ElementWrapper {

    var stroke: String  = ""
        set(value) = setAttribute("stroke", value)

    var fill: String = ""
        set(value) = setAttribute("fill", value)

    init {
        stroke = "#555"
        fill = "#777"
    }

    var width: Number
        set(value) = setAttribute("width", value = value.toString())
        get() = element.getAttribute("width")!!.toDouble()

    var height: Number = 1.0
        set(value) = setAttribute("height", value = value.toString())

    fun transform(init: Transform.() -> Unit) {
        setAttribute("transform", Transform().apply(init).toCommand())
    }

    private var record = false
    private val recorded by lazy { mutableListOf<Pair<String,String>>() }

    private fun setAttribute(name: String, value: String) {
        if(record)
                recorded.add(Pair(name, value))
            else
                this.element.setAttribute(name, value)
    }

    private fun recorder():RectElement{
        val recorder = RectElement(element)
        recorder.record = true
        return recorder
    }

    fun  transition(duration: Int, timeFunction:(Double) -> Double, endState: RectElement.() -> Unit) {

        val recorder = recorder()
        endState(recorder)

        val endWidth = recorder.recorded.first { p -> p.first == "width" }.second.toDouble()

        val widthTransition = interpolateNumber(width, endWidth)
        val time = uninterpolateNumber(0, duration)

        timer { elapsed ->
            if (elapsed > duration){
                width = endWidth
                stop()
            }
            width = widthTransition(timeFunction(time(elapsed).toDouble()))
        }
    }
}


data class Point(val x: Double = 0.0, val y: Double = 0.0) {

    companion object {
        val origin = Point()
    }
    operator fun plus(speed: Speed) = Point(x + speed.vx, y + speed.vy)
}

data class Speed(val vx: Double = 0.0, val vy: Double = 0.0) {
    operator fun plus(speed: Speed) = Speed(vx + speed.vx, vy + speed.vy)
}
