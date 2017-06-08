package io.data2viz.experiments.voronoi

import io.data2viz.color.colors
import io.data2viz.color.colors.darkblue
import io.data2viz.color.colors.lightyellow
import io.data2viz.interpolate.linkedTo
import io.data2viz.interpolate.interpolateRgb
import io.data2viz.interpolate.scale
import io.data2viz.math.Angle
import io.data2viz.math.deg
import io.data2viz.svg.ElementWrapper
import io.data2viz.svg.LineElement
import io.data2viz.svg.ParentElement
import io.data2viz.svg.svg
import io.data2viz.voronoi.Diagram
import io.data2viz.voronoi.Edge
import io.data2viz.voronoi.Point
import io.data2viz.voronoi.Site
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.asList
import kotlin.browser.window
import kotlin.js.Date
import kotlin.js.Math.random

fun voronoiSphere() {

    val size = 600
    val pointCount = 400
    val randomPoints = (1..pointCount).map { GeoPoint((random() * 360).deg, (random() * 360).deg) }

    val circleRadius = scale.linear.numberToNumber(
            -1 linkedTo 3,
            1 linkedTo 1
    )

    val circleAlpha = scale.linear.numberToNumber(
            -1 linkedTo 1,
            1 linkedTo .2
    )

    val pointToScreen = scale.linear.numberToNumber(
            -1.0 linkedTo -200.0,
            1.0 linkedTo 200.0
    )

    fun List<GeoPoint>.sites() = mapIndexed { index, point -> Site(Point(pointToScreen(point.x), pointToScreen(point.y)), index) }.toTypedArray()

    val it = interpolateRgb(darkblue, lightyellow, 0.7)

    svg {
        width = size
        height = size

        val rotationAnimation = RotationAnimation(15.0)
        rotationAnimation { rotation ->
            randomPoints.forEach { geoPoint -> geoPoint.rotation = rotation }
        }

//        rect {
//            transform {
//                translate(0, 0)
//            }
//            width = size
//            height = size
//            fill = colors.black
//        }

        g {
            transform {
                translate(size / 2, size / 2)
                rotate((-20).deg)
            }

            randomPoints.forEach { geoPoint ->
                circle {
                    r = circleRadius(geoPoint.z)
                    cx = pointToScreen(geoPoint.x)
                    cy = pointToScreen(geoPoint.y)
                    rotationAnimation { rotation ->
                        fill = it(((geoPoint.x+1)/2).toFloat())
                        r = circleRadius(geoPoint.z)
                        cx = pointToScreen(geoPoint.x)
                    }
                }
            }

            g {
                rotationAnimation { rotation ->
                    val diagram = Diagram(randomPoints.sites(), Point(-1000.0, -1000.0), Point(1000.0, 1000.0))
                    selectAll<LineElement, Edge>("line", diagram.edges.filterNotNull()) {
                        add = { edge ->
                            if (edge.start != null && edge.end != null)
                                line {
                                    x1 = edge.start!!.x
                                    y1 = edge.start!!.y
                                    x2 = edge.end!!.x
                                    y2 = edge.end!!.y
                                    stroke = colors.black
                                }
                        }
                        update = { line, edge ->
                            if (edge.start != null && edge.end != null) {
                                line.x1 = edge.start!!.x
                                line.y1 = edge.start!!.y
                                line.x2 = edge.end!!.x
                                line.y2 = edge.end!!.y
                                line. stroke = colors.black
                            } else {
                                removeChild(line.element)
                            }
                        }
                        remove = { removeChild(it) }
                    }
                }
            }
        }
    }

}

data class GeoPoint(val lat: Angle, val long: Angle) {
    var rotation: Angle = 0.deg
    val y = lat.sin
    val r = lat.cos
    val x: Double
        get() {
            return r * (rotation + long).cos
        }
    val z: Double get() = r * (rotation + long).sin
}


//------------   API test -----------------------

class Selection<E:ElementWrapper,T> {
    var add: ((T) -> Unit) = {}
    var update: ((E,T) -> Unit) = { e, t -> }
    var remove: ((Node) -> Unit) = {}
}

class ElementWrappers {

    val _wrappers = mapOf<Any, (Element) -> ElementWrapper>(
            LineElement::class to {element:Element -> LineElement(element)}
    )

    inline fun <reified E:ElementWrapper> wrap(element: Element): E {
        val c = E::class
        return (_wrappers[c]!!(element) as E)
    }
}

val nodeWrappers = ElementWrappers()

inline fun <reified E:ElementWrapper, T> ParentElement.selectAll(selector: String, datas: List<T>, init: Selection<E,T>.() -> Unit) {
    val selection = Selection<E,T>()
    selection.init()
    val elements = element.querySelectorAll(selector).asList().filterNotNull().map { it as Element }
    if (elements.size > datas.size)  elements.drop(datas.size).forEach { selection.remove(it) }
    if (elements.size < datas.size) datas.drop(elements.size).forEachIndexed { i,t -> selection.add(t)}
    datas.take(elements.size).forEachIndexed { i,t -> selection.update(nodeWrappers.wrap<E>(elements[i]), t) }
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
