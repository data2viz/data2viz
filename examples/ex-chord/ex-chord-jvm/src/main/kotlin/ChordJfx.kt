package io.data2viz.examples.chord


import io.data2viz.viz.viz
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
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

        root.viz {
            chordViz()
        }

        stage?.let {
            it.scene = (Scene(root, width, height))
            it.show()
            stage.title = "JavaFx - data2viz - ChordJfx.kt"
        }
    }

}
