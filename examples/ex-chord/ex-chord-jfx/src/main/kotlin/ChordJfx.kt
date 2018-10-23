package io.data2viz.examples.chord


import io.data2viz.viz.JFxVizRenderer
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.stage.Stage


class ChordJfx : Application() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(ChordJfx::class.java)
        }
    }

    override fun start(stage: Stage?) {
        println("Building viz")
        val root = Group()
        stage?.let {
            it.scene = (Scene(root, chordSize.width, chordSize.height))
            it.show()
            val canvas = Canvas(chordSize.width, chordSize.height)
            root.children.add(canvas)
            val viz = chordViz()
            JFxVizRenderer(canvas, viz)
            viz.render()
        }
    }

}
