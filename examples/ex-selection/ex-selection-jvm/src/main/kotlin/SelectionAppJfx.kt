

import io.data2viz.viz.GroupJfx
import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage

class SelectionAppJfx : Application() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(SelectionAppJfx::class.java)
        }
    }

    override fun start(primaryStage: Stage?) {
        primaryStage?.let {
            it.scene = (Scene((root as GroupJfx).jfxElement, widthHeight, widthHeight))
            it.show()
        }
    }
}

actual fun random(): Double = Math.random()
