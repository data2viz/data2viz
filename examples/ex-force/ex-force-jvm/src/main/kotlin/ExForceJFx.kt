import io.data2viz.viz.GroupJfx
import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage

class ExForceJFx : Application() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(ExForceJFx::class.java)
        }
    }

    override fun start(primaryStage: Stage?) {
        primaryStage?.let {
            it.scene = (Scene((root as GroupJfx).jfxElement, width, height))
            it.show()
        }
    }
}
