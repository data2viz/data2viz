package io.data2viz.examples.chord


import io.data2viz.transition.rectanglesWithTransition
import io.data2viz.viz.viz
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.stage.Stage


class ExTransitionJfx : Application() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(ExTransitionJfx::class.java)
        }
    }

    override fun start(stage: Stage?) {
        println("Building viz")
        val root = Group()

        root.viz {
            rectanglesWithTransition()
        }

        stage?.let {
            it.scene = (Scene(root, 600.0, 600.0))
            it.show()
            stage.title = "JavaFx - data2viz - ChordJfx.kt"
        }
    }

}
