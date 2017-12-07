package io.data2viz.examples.progression

import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.stage.Stage
import io.data2viz.viz.*


fun main(args: Array<String>) {
    Application.launch(AxisApp::class.java)
}

class AxisApp : Application() {



    override fun start(primaryStage: Stage?) {
        val root = Group()
        root.viz {
            progression()
        }

        primaryStage?.let {
            it.scene = (Scene(root, 1200.0, 800.0))
            it.show()
        }
    }

}
