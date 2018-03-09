package io.data2viz.examples.geo

import io.data2viz.color.colors
import io.data2viz.geo.path.geoPath
import io.data2viz.geo.projection.Extent
import io.data2viz.geo.projection.equirectangularProjection
import io.data2viz.geo.projection.orthographic
import io.data2viz.geojson.toGeoJsonObject
import io.data2viz.path.SvgPath
import io.data2viz.path.svgPath
import io.data2viz.viz.createSVGElement
import io.data2viz.viz.newPath
import io.data2viz.viz.selectOrCreateSvg
import io.data2viz.viz.viz
import kotlinx.coroutines.experimental.await
import kotlinx.coroutines.experimental.promise
import org.w3c.dom.Element
import org.w3c.dom.events.MouseEvent
import org.w3c.fetch.Request
import kotlin.browser.window
import kotlin.dom.appendElement
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


fun main(args: Array<String>) {

    promise {
        val request = window.fetch(Request("world-110m.geojson"))
        val response = request.await()
        val world = response.text().await().toGeoJsonObject()

        val extent = Extent(.0, .0, 800.0, 600.0)

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
        val pathInnerElement:Element = createSVGElement("path").apply {
            setAttribute("stroke", colors.grey.rgbHex)
            setAttribute("fill", colors.grey.rgbHex)
        }
        val pathOuterElement:Element = createSVGElement("path").apply {
            setAttribute("stroke", colors.black.rgbHex)
            setAttribute("fill", colors.white.rgbHex)
        }
        
        val inner = SvgPath()
        val outer = SvgPath()

        val geoPathOuter = geoPath(projectionOuter, outer)
        val geoPathInner = geoPath(projectionInner, inner)
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


        var drag = false

        root.addEventListener("mousedown", { event ->
            val mEvent = event as MouseEvent
            initX = mEvent.clientX.toDouble()
            initY = mEvent.clientY.toDouble()
            initRotate = geoPathInner.projection.rotate
            drag = true
        })

        root.addEventListener("mouseup", { event ->
            drag = false
        })


        root.addEventListener("mousemove", { event ->

            if (drag) {
                
                val timer = Timer()
                val mEvent = event as MouseEvent
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
        })

    }


}
