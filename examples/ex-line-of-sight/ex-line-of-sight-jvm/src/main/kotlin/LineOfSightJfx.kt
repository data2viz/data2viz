package io.data2viz.examples.lineOfSight

import io.data2viz.viz.JFxVizRenderer
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.stage.Stage


class LineOfSightJfx : Application() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(LineOfSightJfx::class.java)
        }
    }

    override fun start(stage: Stage?) {
        println("Building viz")
        val root = Group()
        val canvas = Canvas(vizWidth, vizHeight)
        val viz = lineOfSightViz()
        JFxVizRenderer(canvas,viz)

        root.children.add(canvas)

        stage?.let {
            it.scene = (Scene(root, vizWidth, vizHeight))
            it.show()
            stage.title = "JavaFx - data2viz - Line Of SightJfx.kt"
        }

        viz.startAnimations()
    }

}
