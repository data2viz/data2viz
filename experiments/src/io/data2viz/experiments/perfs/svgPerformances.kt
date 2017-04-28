package io.data2viz.experiments.perfs

import io.data2viz.color.colors
import io.data2viz.core.Point
import io.data2viz.core.Speed
import io.data2viz.interpolate.linkedTo
import io.data2viz.interpolate.scale
import io.data2viz.svg.CircleElement
import io.data2viz.svg.svg
import org.w3c.dom.Element
import kotlin.browser.document
import kotlin.browser.window
import kotlin.js.Date
import kotlin.js.Math

fun svgPerfs() {

    val viz = svg {
        width = 800
        height = 800
    }

    val domainToSvg = scale.linear.pointsToPoints(
            Point.origin linkedTo viz.center,
            Point(5.0, 5.0) linkedTo viz.topRight
    )

    inline fun <reified T> CircleElement.datum(): T = this.element.asDynamic().__data__
    fun CircleElement.datum(value: Any) { this.element.asDynamic().__data__ = value}

    val fpsCalculator = FpsCalculator(document.querySelector("#fps span")!!)

    val circles = mutableListOf<CircleElement>()

    fun render() {
        if(fpsCalculator.curFps > 24){
            circles +=  Array(10, { RandomParticule() }).map { particule ->
                viz.circle {
                    stroke = colors.grey
                    fill = colors.lightgray
                    datum(particule)
                }
            }
        }

        circles.forEach { circle ->
            val particule: RandomParticule = circle.datum()
            particule.updatePositionAndSpeed()
            //optimization: we don't use the DSL to eliminate instanciations and GC of Transform
            circle.setAttribute("transform", with(domainToSvg(particule.position)){"translate($x,$y)"})
            circle.r = (1.0 + 1000.0 * Math.abs(particule.speed.vx * particule.speed.vy)).coerceAtMost(10.0)
        }
        fpsCalculator.updateFPS()
        document.querySelector("#num span")?.textContent = circles.size.toString()
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
                .04 * (Math.random() - .5) - .05 * speed.vx - .0005 * position.x.toDouble(),
                .04 * (Math.random() - .5) - .05 * speed.vy - .0005 * position.y.toDouble()
        )
    }
}

/**
 * Utility class used to calculate the FPS and show the current FPS.
 * updateFPS() must be called at each update.
 * The given fps element is set to the current FPS each 10 calls to updateFPS.
 */
class FpsCalculator(val fps: Element) {
    var curFps = 100
    private val average_fps = mutableListOf<Int>();
    private var time0 = Date().getTime()
    private var time1 = Date().getTime()

    fun updateFPS() {
        time1 = Date().getTime()
        if (time1 != time0) {
            curFps = Math.round(1000.0 / (time1 - time0))
            average_fps.add(curFps)
            if (average_fps.size == 10) {
                fps.textContent = average_fps.average().toString()
                average_fps.clear()
            }
        }
        time0 = time1
    }
}
