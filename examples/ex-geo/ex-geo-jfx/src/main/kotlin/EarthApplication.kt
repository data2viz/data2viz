package io.data2viz.examples.geo

import io.data2viz.geojson.toGeoJsonObject
import io.data2viz.viz.JFxVizRenderer
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.stage.Stage

class EarthApplication : Application() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(EarthApplication::class.java)
        }
    }

    override fun start(stage: Stage?) {

        val vizWidth = 960.0
        val vizHeight = 700.0

        val root = Group()
        val canvas = Canvas(vizWidth, vizHeight)

        val world = this.javaClass.getResourceAsStream("/world-110m.geojson").reader().readText().toGeoJsonObject()

        val viz = geoViz(world)
        JFxVizRenderer(canvas,viz)
        root.children.add(canvas)

        stage?.let {
            it.scene = (Scene(root, vizWidth, vizHeight))
            it.show()
            stage.title = "JavaFx - data2viz - Earth Application.kt"
        }

        viz.startAnimations()

    }

}