import io.data2viz.viz.JFxVizRenderer
import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.stage.Stage

class ExForceJFx : Application() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(ExForceJFx::class.java)
        }
    }

    override fun start(stage: Stage) {
        val root = Group()
        val canvas = Canvas(width, height)
        JFxVizRenderer(canvas, forcesViz)
        root.children.add(canvas)

        stage.let {
            it.scene = (Scene(root, width, height))
            it.show()
        }

        val timer = object : AnimationTimer() {
            override fun handle(now: Long) {
                forcesViz.render()
            }
        }

        timer.start()
    }

}
