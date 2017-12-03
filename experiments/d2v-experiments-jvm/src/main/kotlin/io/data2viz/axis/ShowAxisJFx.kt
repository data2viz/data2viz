package io.data2viz.axis

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
            axisExample()
        }

        primaryStage?.let {
            it.scene = (Scene(root, 600.0, 600.0))
            it.show()
        }
    }

}
