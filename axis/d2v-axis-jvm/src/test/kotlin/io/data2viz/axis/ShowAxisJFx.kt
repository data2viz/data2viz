package io.data2viz.axis

import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.stage.Stage
import io.data2viz.viz.*
import io.data2viz.scale.scaleLinear


fun main(args: Array<String>) {
    Application.launch(AxisApp::class.java)
}

class AxisApp : Application() {

    val scaleX = scaleLinear {
        domain = listOf(.0, 15000.0)
        range = listOf(.0, 500.0)
    }

    val ticks = (1..14).map { it * 1000.0 }


    override fun start(primaryStage: Stage?) {
        val root = Group()
        root.viz {

            group {
                transform {
                    translate(x = 30.0, y = 10.0)
                }
                axis(Orient.TOP, scaleX) { tickValues = ticks }
            }
            group {
                transform {
                    translate(x = 30.0, y = 20.0)
                }
                axis(Orient.BOTTOM, scaleX) { tickValues = ticks }
            }
            group {
                transform {
                    translate(x = 10.0, y = 30.0)
                }
                axis(Orient.LEFT, scaleX) { tickValues = ticks }
            }
            group {
                transform {
                    translate(x = 20.0, y = 30.0)
                }
                axis(Orient.RIGHT, scaleX) { tickValues = ticks }
            }

        }

        primaryStage?.let {
            it.scene = (Scene(root, 600.0, 600.0))
            it.show()
        }
    }
}
