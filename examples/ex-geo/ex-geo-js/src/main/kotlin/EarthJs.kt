package io.data2viz.examples.geo

import io.data2viz.color.colors
import io.data2viz.geo.path.GeoPath
import io.data2viz.geo.path.geoPath
import io.data2viz.geo.projection.orthographic
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.geojson.toGeoJsonObject
import io.data2viz.timer.timer
import io.data2viz.viz.JsCanvasRenderer
import io.data2viz.viz.PathNode
import io.data2viz.viz.Viz
import kotlinx.coroutines.experimental.await
import kotlinx.coroutines.experimental.promise
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.Element
import org.w3c.dom.HTMLCanvasElement
import org.w3c.fetch.Request
import kotlin.browser.document
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


lateinit var outer: PathNode
lateinit var inner: PathNode
lateinit var geoPathOuter: GeoPath
lateinit var geoPathInner: GeoPath
lateinit var world: GeoJsonObject

fun main(args: Array<String>) {
    val (canvas, context) = newCanvas(width.toInt(), height.toInt())
    val viz = Viz()
    viz.renderer = JsCanvasRenderer(context)

    timer {
        viz.render()
    }

    promise {
        val request = window.fetch(Request("world-110m-30percent.json"))
        val response = request.await()
        world = response.text().await().toGeoJsonObject()

        // INNER GLOBE
        val projectionInner = orthographic {
            translate = doubleArrayOf(400.0, 300.0)
            scale = 250.0
            clipAngle = Double.NaN
        }

        inner = PathNode().apply {
            stroke = null
            fill = colors.darkgray
        }

        geoPathInner = geoPath(projectionInner, inner)
        geoPathInner.path(world)



        // OUTER GLOBE
        val projectionOuter = orthographic {
            translate = doubleArrayOf(400.0, 300.0)
            scale = 250.0
        }

        outer = PathNode().apply {
            stroke = colors.black
            fill = colors.whitesmoke
        }

        geoPathOuter = geoPath(projectionOuter, outer)
        geoPathOuter.path(world)


//        var initX = .0
//        var initY = .0
//        var initRotate: DoubleArray = geoPathOuter.projection.rotate

        viz.add(inner)
        viz.add(outer)

        io.data2viz.timer.timer { now ->
            val rotate = geoPathOuter.projection.rotate
            rotate[0] += .5
            rotate[1] = -10.0

            outer.clearPath()
            geoPathOuter.projection.rotate = rotate
            geoPathOuter.path(world)

            inner.clearPath()
            geoPathInner.projection.rotate = rotate
            geoPathInner.path(world)

            viz.render()
        }
    }

}

data class CanvasContext(val canvas: HTMLCanvasElement, val context2D: CanvasRenderingContext2D)

private fun newCanvas(width: Int, height: Int): CanvasContext {
    val canvas = document.createElement("canvas") as HTMLCanvasElement
    val context = canvas.getContext("2d") as CanvasRenderingContext2D
    context.canvas.width = width
    context.canvas.height = height
    document.querySelector("body")!!.appendChild(canvas)
    return CanvasContext(canvas, context)
}
