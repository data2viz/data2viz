package io.data2viz.examples.geo

import io.data2viz.color.colors
import io.data2viz.geo.path.GeoPath
import io.data2viz.geo.path.geoPath
import io.data2viz.geo.projection.orthographic
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.geojson.toGeoJsonObject
import io.data2viz.path.SvgPath
import io.data2viz.svg.SVGElement
import io.data2viz.viz.PathVizElement
import io.data2viz.viz.createSVGElement
import io.data2viz.viz.selectOrCreateSvg
import kotlinx.coroutines.experimental.await
import kotlinx.coroutines.experimental.promise
import org.w3c.dom.Element
import org.w3c.dom.events.MouseEvent
import org.w3c.fetch.Request
import kotlin.browser.window
import kotlin.js.Date


class Timer {
    var last = Date.now()

    fun log(msg: String) {
        val newTime = Date.now()
        println("$msg in ${newTime - last} ms")
        last = newTime
    }
}

private val timer = Timer()


lateinit var outer: SvgPath
lateinit var inner: SvgPath
lateinit var geoPathInner: GeoPath
lateinit var geoPathOuter: GeoPath
lateinit var pathInnerElement: Element
lateinit var pathOuterElement: Element
lateinit var world: GeoJsonObject


@Suppress("unused")
fun main(args: Array<String>) {

    promise {
        val request = window.fetch(Request("world-110m.geojson"))
        val response = request.await()
        world = response.text().await().toGeoJsonObject()

        // OUTER GLOBE
        val projectionOuter = orthographic {
            translate = doubleArrayOf(400.0, 300.0)
            scale = 250.0
        }


        // INNER GLOBE
        val projectionInner = orthographic {
            translate = doubleArrayOf(400.0, 300.0)
            scale = 250.0
            clipAngle = Double.NaN          // remove angle clipping in order to see-through
        }
        pathInnerElement = createSVGElement("path").apply {
            setAttribute("stroke", colors.grey.rgbHex)
            setAttribute("fill", colors.grey.rgbHex)
        }
        pathOuterElement = createSVGElement("path").apply {
            setAttribute("stroke", colors.black.rgbHex)
            setAttribute("fill", colors.white.rgbHex)
        }
        
        inner = SvgPath()
        outer = SvgPath()

        geoPathOuter = geoPath(projectionOuter, outer)
        geoPathInner = geoPath(projectionInner, inner)
        geoPathOuter.path(world)
        geoPathInner.path(world)
        
        pathInnerElement.setAttribute("d", inner.path)
        pathOuterElement.setAttribute("d", outer.path)

        var initX = .0
        var initY = .0
        var initRotate: DoubleArray = geoPathInner.projection.rotate

        val root = selectOrCreateSvg().apply {
            setAttribute("width", "$width")
            setAttribute("height", "$height")
            appendChild(pathInnerElement)
            appendChild(pathOuterElement)
        }

      
        timer.log("adding path")

        io.data2viz.timer.timer { now ->
            loop(now)
        }

        /*var drag = false

        root.addEventListener("mousedown", { event ->
            val mEvent = event as MouseEvent
            initX = mEvent.clientX.toDouble()
            initY = mEvent.clientY.toDouble()
            initRotate = geoPathInner.projection.rotate
            drag = true
        })

        root.addEventListener("mouseup", {
            drag = false
        })


        root.addEventListener("mousemove", {

            if (drag) {

                val timer = Timer()
                val mEvent = it as MouseEvent
                val rotate = doubleArrayOf(
                    initRotate[0] + .25 * (mEvent.clientX - initX),
                    initRotate[1] - .25 * (mEvent.clientY - initY)
                )

                outer.clearPath()
                inner.clearPath()

                geoPathInner.projection.rotate = rotate
                geoPathOuter.projection.rotate = rotate

                geoPathOuter.path(world)
                geoPathInner.path(world)

                pathInnerElement.setAttribute("d", inner.path)
                pathOuterElement.setAttribute("d", outer.path)

                timer.log("update paths")
            }
        })*/

    }

}

fun loop(now: Double) {
    val rotate = geoPathInner.projection.rotate

    rotate[0] += .5
    rotate[1] = -10.0

    outer.clearPath()
    inner.clearPath()

    geoPathInner.projection.rotate = rotate
    geoPathOuter.projection.rotate = rotate

    geoPathOuter.path(world)
    geoPathInner.path(world)

    pathInnerElement.setAttribute("d", inner.path)
    pathOuterElement.setAttribute("d", outer.path)

    timer.log("update paths")
}
