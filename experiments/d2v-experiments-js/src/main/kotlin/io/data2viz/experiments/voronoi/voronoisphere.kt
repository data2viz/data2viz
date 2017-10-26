package io.data2viz.experiments.voronoi

import io.data2viz.color.colors
import io.data2viz.color.colors.black
import io.data2viz.color.colors.darkblue
import io.data2viz.color.colors.lightyellow
import io.data2viz.color.colors.white
import io.data2viz.core.Point
import io.data2viz.interpolate.linkedTo
import io.data2viz.interpolate.interpolateRgb
import io.data2viz.experiments.perfs.FpsCalculator
import io.data2viz.interpolate.scale
import io.data2viz.math.Angle
import io.data2viz.math.deg
import io.data2viz.svg.*
import io.data2viz.voronoi.*
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.asList
import kotlin.browser.document
import kotlin.browser.window
import kotlin.js.Date
import kotlin.js.Math.random
import kotlin.reflect.KMutableProperty0

fun voronoiSphere() {

    data class SphereParams(
            var clipping: Boolean = true,
            var showCircles: Boolean = true,
            var showPolygons: Boolean = true,
            var delaunay: Boolean = true,
            var pointNumber: Int = 20,
            val chunkSize: Int = 1) {

        fun eventuallyUpdatePointNumber(curFPS: Int, curPoint: Int) {
            if (isAllDisplayed() && curFPS > 25 && curPoint <= pointNumber - chunkSize + 5) {
                pointNumber += chunkSize
            }
        }

        fun isAllDisplayed() = showCircles && showPolygons && delaunay
    }

    val size = 600
    val commandHeight = 200

    val sphereParams = SphereParams()

    val fpsCalculator = FpsCalculator(document.querySelector("#fps span")!!)

    fun newPoints(count: Int = sphereParams.chunkSize) = Array(count) { GeoPoint((random() * 360).deg, (random() * 360).deg) }

    val randomPoints = newPoints(10).toMutableList()

    val circleRadius = scale.linear.numberToNumber(
            -1 linkedTo 3,
            1 linkedTo 1
    )

    val pointToScreen = scale.linear.numberToNumber(
            -1.0 linkedTo -200.0,
            1.0 linkedTo 200.0
    )

    fun List<GeoPoint>.sites() = mapIndexed { index, point -> Site(Point(pointToScreen(point.x).toDouble(), pointToScreen(point.y).toDouble()), index) }.toTypedArray()

    val darkToLight = interpolateRgb(darkblue, lightyellow, 0.7)

    svg {
        width = size
        height = size + commandHeight
        var diagram: Diagram? = null

        val rotationAnimation = RotationAnimation(15.0)
        rotationAnimation { rotation ->
            fpsCalculator.updateFPS()
            document.querySelector("#num span")?.textContent = randomPoints.size.toString()
            sphereParams.eventuallyUpdatePointNumber(fpsCalculator.curFps, randomPoints.size)
            if (randomPoints.size < sphereParams.pointNumber && fpsCalculator.curFps > 25) {
                randomPoints.addAll(newPoints())
            } else if (randomPoints.size > sphereParams.pointNumber) {
                val pointSize = randomPoints.size
                (1..sphereParams.chunkSize).forEach { randomPoints.removeAt(pointSize - it) }
            }
            randomPoints.forEach { geoPoint -> geoPoint.rotation = rotation }
            if (sphereParams.delaunay || sphereParams.showPolygons)
                diagram = if (sphereParams.clipping) Diagram(randomPoints.sites(), Point(-1000.0, -1000.0), Point(1000.0, 1000.0))
                else Diagram(randomPoints.sites())
        }

        g {
            transform {
                translate(size / 2, size / 2)
                rotate((-20).deg)
            }

            //circles
            g {
                rotationAnimation { rotation ->
                    val points:List<GeoPoint> = if (sphereParams.showCircles) randomPoints else emptyList()
                    selectAll<CircleElement, GeoPoint>("circle", points) {
                            addAndUpdate = { circle, geoPoint ->
                                circle.r = circleRadius(geoPoint.z)
                                circle.cx = pointToScreen(geoPoint.x)
                                circle.cy = pointToScreen(geoPoint.y)
                                circle.fill = darkToLight(((geoPoint.x + 1) / 2).toFloat())
                            }
                    }
                }
            }

            //polygons
            g {
                rotationAnimation { rotation ->
                    val edges = if (sphereParams.showPolygons) diagram!!.edges.filterNotNull() else emptyList()
                    selectAll<LineElement, Edge>("line", edges) {
                        addAndUpdate = { line, edge ->
                            if (edge.start != null && edge.end != null) {
                                line.x1 = edge.start!!.x
                                line.y1 = edge.start!!.y
                                line.x2 = edge.end!!.x
                                line.y2 = edge.end!!.y
                                line.stroke = colors.black
                            } else {
                                removeChild(line.element)
                            }
                        }
                    }
                }
            }

            //delaunay
            g {
                rotationAnimation { rotation ->
                    val links = if (sphereParams.delaunay) diagram!!.links().filterNotNull() else emptyList()
                    selectAll<LineElement, Diagram.Link>("line", links) {
                        addAndUpdate = { line, link ->
                            line.x1 = link.source.x
                            line.y1 = link.source.y
                            line.x2 = link.target.x
                            line.y2 = link.target.y
                            line.stroke = colors.red
                        }
                    }
                }
            }
        }

        g {
            transform {
                translate(y = size)
            }
            rect {
                width = size
                height = commandHeight
                fill = white
            }

            //first line of toggle buttons
            g {
                transform { translate(y = 30) }
                toggleButton("Clipping", sphereParams::clipping)
                toggleButton("Polygons", sphereParams::showPolygons).apply { transform { translate(150) } }
                toggleButton("Delaunay", sphereParams::delaunay).apply { transform { translate(300) } }
                toggleButton("Circles", sphereParams::showCircles).apply { transform { translate(450) } }
            }
        }
    }
}

data class GeoPoint(val lat: Angle, val long: Angle) {
    var rotation: Angle = 0.deg
    val y = lat.sin
    val r = lat.cos
    val x: Double get() = r * (rotation + long).cos
    val z: Double get() = r * (rotation + long).sin
}

//------------   API test -----------------------

fun GroupElement.toggleButton(name: String, property: KMutableProperty0<Boolean>): GroupElement {
    return g {
        setAttribute("style", "cursor:pointer")
        rect {
            //rounded border
            width = 125
            height = 22
            rx = 3
            ry = 3
            fill = white
            stroke = black
        }
        val label = text {
            x = 10
            y = 16
            text = "$name : ${property.get()}"
        }
        on("click") {
            property.set(!property.get())
            label.text = "$name : ${property.get()}"
        }
    }
}

class Selection<E : ElementWrapper, T> {
    var add: ((E, T) -> Unit) = { a, b -> }
    var addAndUpdate: ((E, T) -> Unit)
        get() = update
        set(value) {
            add = value
            update = value
        }
    var update: ((E, T) -> Unit) = { e, t -> }
    var remove: ((Node) -> Unit) = {}
}

inline fun <reified E : ElementWrapper> wrap(element: Element): E {
    val c = E::class
    return when {
        c == LineElement::class -> LineElement(element) as E
        c == CircleElement::class -> CircleElement(element) as E
        else -> error("Unknown type $c")
    }
}

inline fun <reified E : ElementWrapper> ParentElement.create(): E {
    val c = E::class
    return when {
        c == LineElement::class -> line {} as E
        c == CircleElement::class -> circle {} as E
        else -> error("Unknown type $c")
    }
}

inline fun <reified E : ElementWrapper, T> ParentElement.selectAll(selector: String, datas: List<T>, init: Selection<E, T>.() -> Unit) {
    val selection = Selection<E, T>()
    selection.remove = { e -> removeChild(e) } //By default, on removal the element is removed from the parent.
    selection.init()

    val elements = element.querySelectorAll(selector).asList().filterNotNull().map { it as Element }
    if (elements.size > datas.size) elements.drop(datas.size).forEach { selection.remove(it) }
    if (elements.size < datas.size) datas.drop(elements.size).forEachIndexed { i, t -> selection.add(create(), t) }
    datas.take(elements.size).forEachIndexed { i, t -> selection.update(wrap<E>(elements[i]), t) }
}

fun ParentElement.removeChild(child: Node) {
    element.removeChild(child)
}

class RotationAnimation(rotationTimeInSeconds: Double) {

    val startTime = Date().getTime()
    val rotationPerMs = (360.0 / (rotationTimeInSeconds * 1000)).deg
    val blocksOfAnimation = mutableListOf<(Angle) -> Unit>()

    init {
        fun animate() {
            val currentTime = Date().getTime()
            window.requestAnimationFrame {
                val rotation = rotationPerMs * (currentTime - startTime)
                blocksOfAnimation.forEach { it(rotation) }
                animate()
            }
        }
        animate()
    }

    operator fun invoke(animation: (Angle) -> Unit) {
        blocksOfAnimation.add(animation)
    }
}
