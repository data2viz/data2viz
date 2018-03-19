package io.data2viz.examples.geo

import io.data2viz.color.colors
import io.data2viz.core.Extent
import io.data2viz.geo.path.GeoPath
import io.data2viz.geo.path.geoPath
import io.data2viz.geo.projection.orthographic
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.geojson.toGeoJsonObject
import io.data2viz.timer.timer
import io.data2viz.viz.PathVizElement
import io.data2viz.viz.PathVizJfx
import io.data2viz.viz.newPath
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.stage.Stage

class EarthApplication : Application() {

    lateinit var geoPathInner: GeoPath
    lateinit var geoPathOuter: GeoPath
    lateinit var pathVizJfxInner: PathVizElement
    lateinit var pathOuter: PathVizElement
    lateinit var world: GeoJsonObject

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(EarthApplication::class.java)
        }
    }

    override fun start(primaryStage: Stage?) {

        val extent = Extent(.0, .0, 800.0, 600.0)

        world = this.javaClass.getResourceAsStream("/world-110m.geojson")
            .reader().readText().toGeoJsonObject()

        // OUTER GLOBE
        val projectionOuter = orthographic {
            translate = doubleArrayOf(400.0, 300.0)
            scale = 250.0
        }
        pathOuter = newPath().apply {
            stroke = colors.black
            fill = colors.white
        }
        geoPathOuter = geoPath(projectionOuter, pathOuter)
        geoPathOuter.path(world)

        // INNER GLOBE
        val projectionInner = orthographic {
            translate = doubleArrayOf(400.0, 300.0)
            scale = 250.0
            clipAngle = Double.NaN          // remove angle clipping in order to see-through
        }
        pathVizJfxInner = newPath().apply {
            stroke = colors.grey
            fill = colors.grey
        }
        geoPathInner = geoPath(projectionInner, pathVizJfxInner)
        geoPathInner.path(world)

        var initX = .0
        var initY = .0
        var initRotate: DoubleArray = geoPathInner.projection.rotate

        val root = Pane()
        root.children.add((pathVizJfxInner as PathVizJfx).jfxElement)                   // first the "see-through" globe
        root.children.add((pathOuter as PathVizJfx).jfxElement)                   // then the "outer" globe
        primaryStage!!.scene = (Scene(root, extent.width, extent.height))
        primaryStage.show()

        timer {now ->
            loop(root, now)
        }

        /*root.setOnMousePressed { event ->
            initX = event.x
            initY = event.y
            initRotate = geoPathInner.projection.rotate
        }


        root.setOnMouseDragged { event ->
            val rotate = doubleArrayOf(
                initRotate[0] + .25 * (event.x - initX),
                initRotate[1] - .25 * (event.y - initY)
            )

            pathVizJfxInner.jfxElement.elements.clear()
            pathOuter.jfxElement.elements.clear()

            geoPathInner.projection.rotate = rotate
            geoPathOuter.projection.rotate = rotate

            geoPathOuter.path(world)
            geoPathInner.path(world)
        }*/

    }

    fun loop(pane:Pane, now:Double) {
        val rotate = geoPathInner.projection.rotate

        rotate[0] += .5
        rotate[1] = -10.0
//        rotate[1] += .1

        (pathVizJfxInner as PathVizJfx).jfxElement.elements.clear()
        (pathOuter as PathVizJfx).jfxElement.elements.clear()

        geoPathInner.projection.rotate = rotate
        geoPathOuter.projection.rotate = rotate

        geoPathOuter.path(world)
        geoPathInner.path(world)
    }

}