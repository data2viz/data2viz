import io.data2viz.color.*
import io.data2viz.geom.*
import io.data2viz.viz.*
import io.data2viz.force.*
import io.data2viz.math.deg
import io.data2viz.math.pct
import io.data2viz.random.RandomDistribution
import io.data2viz.timer.now
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.layout.VBox
import javafx.stage.Stage

//data class Item(val name:String, val position:Point)

val timer = now()

class ForceExJFX: Application() {
    val canvas = Canvas(1000.0, 1000.0)
    val viz = graph()
    val renderer = JFxVizRenderer(canvas, viz)

    init {
        viz.renderer = renderer
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(ForceExJFX::class.java)
        }
    }

    fun renderChart() {
        viz.render()
        viz.startAnimations()
    }


    override fun start(primaryStage: Stage?) {
        println("Building viz")
        val root = VBox()

        primaryStage?.let {
            it.scene = (Scene(root, 1000.0, 1000.0))
            it.show()
            root.children.add(canvas)
            renderChart()
        }
    }

}

data class NamedPoint(val position:Point, val layer:Int)

fun graph(): Viz {

    val vizSize = 400.0
    val randPos = RandomDistribution.uniform(.0, vizSize)
    val viewCenter = point(vizSize / 2, vizSize / 2)
    val items = (0..2000).map { NamedPoint(point(randPos(), randPos()), it%12 ) }

    val simulation = forceSimulation<NamedPoint> {
        forceRadial {
            centerGet = { viewCenter }
            radiusGet = { domain.layer * 17.0 }
        }
        domainObjects = items

        intensity = 40.pct
        intensityDecay = 0.2.pct
        initForceNode = {
            position = domain.position
        }
    }

    val particles = mutableListOf<CircleNode>()
    return viz {
        size = size(vizSize, vizSize)
        simulation.nodes.forEach { forceNode ->
            particles += circle {
                radius = 5.0
                fill = Colors.hsl((forceNode.domain.layer * 30).deg, 100.pct, 50.pct)
            }
        }
        animation {
            simulation.nodes.forEach { forceNode ->
                particles[forceNode.index].apply {
                    x = forceNode.x
                    y = forceNode.y
                }
            }
        }
    }
}