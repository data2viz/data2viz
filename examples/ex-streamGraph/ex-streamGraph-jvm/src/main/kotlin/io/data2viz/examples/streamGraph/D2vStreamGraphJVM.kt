package io.data2viz.examples.streamGraph

import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.stage.Stage
import io.data2viz.viz.*

class D2vStreamGraphJVM : Application() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(D2vStreamGraphJVM::class.java)
        }
    }


    override fun start(primaryStage: Stage?) {
        val root = Group()
        root.viz {
            streamGraph()
        }

        primaryStage?.let {
            it.scene = (Scene(root, width + margins.hMargins, height + margins.vMargins))
            it.show()
        }
    }

}
