import io.data2viz.color.*
import io.data2viz.geom.*
import io.data2viz.viz.*
import io.data2viz.force.*
import io.data2viz.math.pct
import io.data2viz.random.RandomDistribution
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.layout.VBox
import javafx.stage.Stage

//data class Item(val name:String, val position:Point)

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

data class Charge(val initialPosition:Point, val attractor:Boolean)

val vizSize = 600.0
val randPos = RandomDistribution.uniform(100.0, vizSize - 100.0)

fun graph(): Viz {

    val items = (0..200).map { Charge(point(randPos(), randPos()), (it % 2) == 0) }
    val particles = mutableListOf<CircleNode>()

    val simulation = forceSimulation<Charge> {
        forceCenter {
            center = point(vizSize / 2, vizSize / 2)
        }
        forceNBody {
            strengthGet = { if (domain.attractor) 30.0 else -50.0 }
            distanceMin = 5.0
        }
        initForceNode = {
            position = domain.initialPosition
        }
        domainObjects = items
//        intensity = 40.pct
//        intensityDecay = 0.pct
    }

    return viz {
        size = size(vizSize, vizSize)
        simulation.nodes.forEach { forceNode ->
            particles += circle {
                radius = 10.0
                fill = if (forceNode.domain.attractor) Colors.Web.crimson else Colors.Web.mediumblue
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
//data class LayeredPoint(val position:Point, val layer:Int)
//fun randPos(a:Double) = RandomDistribution.uniform(.0, a)()
//
//fun graph(): Viz {
//
//    val vizWidth = 600.0
//    val vizHeight = 200.0
//
//    val items = (0..1000).map { LayeredPoint(point(randPos(vizWidth), randPos(vizHeight)), it%12 ) }
//    val particles = mutableListOf<CircleNode>()
//
//    val simulation = forceSimulation<LayeredPoint> {
//        forceX {
//            xGet = { domain.layer * 50.0 }
//        }
//        initForceNode = {
//            position = domain.position
//        }
//        domainObjects = items
//        intensity = 40.pct
//        intensityDecay = 0.2.pct
//    }
//
//    return viz {
//        size = size(vizWidth, vizHeight)
//        simulation.nodes.forEach { forceNode ->
//            particles += circle {
//                radius = 5.0
//                fill = Colors.hsl((forceNode.domain.layer * 30).deg, 100.pct, 50.pct)
//            }
//        }
//        animation {
//            simulation.nodes.forEach { forceNode ->
//                particles[forceNode.index].apply {
//                    x = forceNode.x
//                    y = forceNode.y
//                }
//            }
//        }
//    }
//}

//data class ColorPoint(val position:Point, val color:Color)
//
//fun graph(): Viz {
//
//    val vizSize = 600.0
//    val viewCenter = point(vizSize / 2, vizSize / 2)
//
//    val items = mutableListOf<ColorPoint>()
//    val particles = mutableListOf<CircleNode>()
//
//    val simulation = forceSimulation<ColorPoint> {
//        forceCenter {
//            center = viewCenter
//        }
//        initForceNode = {
//            position = domain.position
//        }
//    }
//
//    return viz {
//        size = size(vizSize, vizSize)
//        animation {
//            val itemCount = items.size
//            if (itemCount > 1200) stop()
//
//            val angle = (itemCount * 6).deg
//            val offset = itemCount * .2
//
//            val position = point(250.0 + angle.cos * offset, 350.0 + angle.sin * offset)
//            val color = Colors.hsl(angle, 100.pct, 50.pct)
//            val newPoint = ColorPoint(position, color)
//            items += newPoint
//
//            // adding a new node and a new visual particle on each animation frame
//            particles += circle {
//                fill = newPoint.color
//                radius = 10.0
//            }
//
//            // updating simulation then placing particles on screen
//            simulation.apply {
//                domainObjects = items
//                intensity = 10.pct
//            }
//            simulation.nodes.forEach { forceNode ->
//                particles[forceNode.index].apply {
//                    x = forceNode.x
//                    y = forceNode.y
//                }
//            }
//        }
//    }
//}

//fun graph(): Viz {
//
//    val vizSize = 400.0
//    val randPos = RandomDistribution.uniform(.0, vizSize)
//    val viewCenter = point(vizSize / 2, vizSize / 2)
//    val items = (0..2000).map { LayeredPoint(point(randPos(), randPos()), it%12 ) }
//
//    val simulation = forceSimulation<LayeredPoint> {
//        forceRadial {
//            centerGet = { viewCenter }
//            radiusGet = { domain.layer * 17.0 }
//        }
//        domainObjects = items
//
//        intensity = 40.pct
//        intensityDecay = 0.2.pct
//
//        initForceNode = {
//            position = domain.position
//        }
//    }
//
//    val particles = mutableListOf<CircleNode>()
//    return viz {
//        size = size(vizSize, vizSize)
//        simulation.nodes.forEach { forceNode ->
//            particles += circle {
//                radius = 5.0
//                fill = Colors.hsl((forceNode.domain.layer * 30).deg, 100.pct, 50.pct)
//            }
//        }
//        animation {
//            simulation.nodes.forEach { forceNode ->
//                particles[forceNode.index].apply {
//                    x = forceNode.x
//                    y = forceNode.y
//                }
//            }
//        }
//    }
//}