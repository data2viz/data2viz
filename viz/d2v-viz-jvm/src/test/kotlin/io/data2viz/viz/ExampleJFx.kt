package io.data2viz.viz

import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.stage.Stage


fun main(args: Array<String>) {
    Application.launch(HelloApp::class.java)
}

data class Domain(val val1: Double, val val2: Double)


class HelloApp : Application() {


    override fun start(primaryStage: Stage?) {

        val data = listOf(
                Domain(10.0, 10.0),
                Domain(20.0, 40.0),
                Domain(30.0, 90.0)
        )

        val root = Group()
        root.viz {

            data.forEach { datum ->
                circle {
                    cx = datum.val1.toDouble()
                    cy = datum.val2.toDouble()
                    radius = 5.0
                }

            }

        }

        primaryStage!!.scene = (Scene(root, 300.0, 250.0))
        primaryStage.show()
    }
}


































infix inline fun <reified D, V : VizItem> List<D>.bindTo(that: VizFactory<V>): Binding<D, V> = Binding(this)


/**
 * Binding between a data and a visual item.
 *
 */
class Binding<D, V>(val data: List<D>) {
    var datum: D? = null
    var vizItem: V? = null

    var index: Int? = null

    var onCreate: V.() -> Unit = {}
    var onUpdate: V.() -> Unit = {}
    var onRemove: V.() -> Unit = {}
}
