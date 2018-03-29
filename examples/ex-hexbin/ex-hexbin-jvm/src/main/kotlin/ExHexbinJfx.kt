import io.data2viz.viz.GroupJfx
import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage

class ExHexbinJfx : Application() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(ExHexbinJfx::class.java)
        }
    }

    override fun start(primaryStage: Stage?) {
        primaryStage?.let {
            it.scene = (Scene((root as GroupJfx).jfxElement, vizWidth, vizHeight))
            it.show()
        }
    }
}
