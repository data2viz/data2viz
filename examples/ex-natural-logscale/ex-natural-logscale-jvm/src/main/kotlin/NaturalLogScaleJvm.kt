package io.data2viz.examples.naturalLogScale


import io.data2viz.viz.viz
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.stage.Stage

class NaturalLogScale : Application() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(NaturalLogScale::class.java)
        }
    }


    override fun start(primaryStage: Stage?) {
        val root = Group()

        root.viz {
            naturalLogScale()
        }


        primaryStage?.let {
            it.scene = (Scene(root, width + margins.hMargins, height + margins.vMargins))
            it.show()
        }
    }

}
