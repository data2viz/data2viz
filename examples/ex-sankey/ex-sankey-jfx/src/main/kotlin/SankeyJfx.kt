package io.data2viz.examples.sankey

import io.data2viz.viz.JFxVizRenderer
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.stage.Stage


class SankeyJfx : Application() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(SankeyJfx::class.java)
        }
    }

    override fun start(stage: Stage?) {
        println("Building viz")
        val root = Group()
        stage?.let {
            it.scene = (Scene(root, vizWidth, vizHeight))
            it.show()
            val canvas = Canvas(vizWidth, vizHeight)
            root.children.add(canvas)
            val viz = sankeyViz()
            JFxVizRenderer(canvas, viz)
            viz.render()
        }
    }

}
