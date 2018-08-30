package io.data2viz.examples.geo

import io.data2viz.color.colors
import io.data2viz.core.Extent
import io.data2viz.geo.path.GeoPath
import io.data2viz.geo.path.geoPath
import io.data2viz.geo.projection.orthographic
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.geojson.toGeoJsonObject
import io.data2viz.timer.timer
import io.data2viz.viz.*
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.stage.Stage

class EarthApplication : Application() {

    lateinit var geoPathOuter: GeoPath
    lateinit var pathOuter: PathNode
    lateinit var world: GeoJsonObject

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(EarthApplication::class.java)
        }
    }

    override fun start(primaryStage: Stage?) {

        val root = Group()
        val canvas = Canvas(width, height)
        val renderer = JFxVizRenderer(canvas)
        val viz = Viz()
        viz.renderer = renderer
        root.children.add(canvas)

        val extent = Extent(.0, .0, 800.0, 600.0)

        world = this.javaClass.getResourceAsStream("/world-110m.geojson")
            .reader().readText().toGeoJsonObject()

        // OUTER GLOBE
        val projectionOuter = orthographic {
            translate = doubleArrayOf(400.0, 300.0)
            scale = 250.0
        }
        pathOuter = PathNode().apply {
            stroke = colors.black
            fill = colors.white
        }
        geoPathOuter = geoPath(projectionOuter, pathOuter)
        geoPathOuter.path(world)

        viz.root.add(pathOuter)
        primaryStage!!.scene = (Scene(root, extent.width, extent.height))
        primaryStage.show()
        viz.render()

        timer { _ ->
            val rotate = geoPathOuter.projection.rotate
            rotate[0] += .5
            rotate[1] = -10.0
            pathOuter.clearPath()
            geoPathOuter.projection.rotate = rotate
            viz.render()
        }

    }

}