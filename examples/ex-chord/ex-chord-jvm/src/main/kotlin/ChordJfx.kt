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
            it.scene = (Scene(root, chordWidth, chordHeight))
            it.show()
            val canvas = Canvas(chordWidth, chordHeight)
            root.children.add(canvas)
            val renderer = JFxVizRenderer(canvas)

            val viz = chordViz()
            viz.renderer = renderer
            viz.render()
        }
    }

}
