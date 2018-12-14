import io.data2viz.color.Colors
import io.data2viz.force.ForceLink
import io.data2viz.force.Link
import io.data2viz.force.SimulationEvent
import io.data2viz.force.forceSimulation
import io.data2viz.geom.Point
import io.data2viz.geom.point
import io.data2viz.geom.size
import io.data2viz.math.deg
import io.data2viz.math.pct
import io.data2viz.random.RandomDistribution
import io.data2viz.viz.*
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.layout.VBox
import javafx.stage.Stage

//data class Item(val name:String, val position:Point)

class ForceExJFX : Application() {
    val canvas = Canvas(600.0, 600.0)
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
            it.scene = (Scene(root, 600.0, 600.0))
            it.show()
            root.children.add(canvas)
            renderChart()
        }
    }

}

data class Stitch(var row:Int, var column:Int)

//val vizSize = 600.0
//val viewCenter = point(vizSize / 2, vizSize / 2)
//
//val networkSize = 17
//val totalNodes = networkSize * networkSize
//val linksDistance = 5.0
//
//fun graph(): Viz {
//
//    val particleVisuals = mutableListOf<CircleNode>()
//    val linkVisuals = mutableListOf<LineNode>()
//    val particles = (0 until totalNodes).map { Stitch(it % networkSize, it / networkSize) }
//
//    lateinit var constraintForce:ForceLink<Stitch>
//
//    val simulation = forceSimulation<Stitch> {
//        forceCenter {
//            center = viewCenter
//        }
//        forceNBody {
//            // each ForceNode will repel each other ForceNode
//            strengthGet = { -15.0 }
//        }
//        constraintForce = forceLink {
//            // link this ForceNode to other ForceNodes on the next row & column (if available)
//            linkGet = {
//                val links = mutableListOf<Link<Stitch>>()
//                if (domain.row < networkSize - 1) links.add(Link(this, nodes[index + 1], linksDistance))
//                if (domain.column < networkSize - 1) links.add(Link(this, nodes[index + networkSize], linksDistance))
//                links
//            }
//            iterations = 1
//        }
//        domainObjects = particles
//        intensityDecay = 2.pct
//    }
//
//    return viz {
//        size = size(vizSize, vizSize)
//        constraintForce.links.forEach { link ->
//            linkVisuals += line {
//                stroke = Colors.Web.lightslategrey
//            }
//        }
//        simulation.nodes.forEach { forceNode ->
//            particleVisuals += circle {
//                radius = 3.0
//                fill = Colors.Web.crimson
//            }
//        }
//
//        animation {
//            simulation.nodes.forEach { forceNode ->
//                particleVisuals[forceNode.index].apply {
//                    x = forceNode.x
//                    y = forceNode.y
//                }
//            }
//            constraintForce.links.forEachIndexed { index, link ->
//                linkVisuals[index].apply {
//                    x1 = link.source.x
//                    x2 = link.target.x
//                    y1 = link.source.y
//                    y2 = link.target.y
//                }
//            }
//            if (simulation.intensity < 1.pct) {
//                println("STOP ANIM")
//                stopAnimations()
//            }
//        }
//    }
//}



//data class Particle(var initialPosition:Point, val initialSpeed:Vector, var radius:Double)
//
//val vizSize = 600.0
//val totalNodes = 200
//val particleRadius = RandomDistribution.uniform(5.0, 20.0)
//val nodeRadius = 80.0
//val randPos = RandomDistribution.uniform(100.0, vizSize - 100.0)
//val randomSpeed = RandomDistribution.uniform(-1.0, 1.0)
//val randomAngle = RandomDistribution.uniform(.0, 360.0)
//val viewCenter = point(vizSize / 2, vizSize / 2)
//
//fun graph(): Viz {
//
//    val rAngle = randomAngle().deg
//    var nodeMovement = Vector(rAngle.cos * 5, rAngle.sin * 5)
//
//    val particleVisuals = mutableListOf<CircleNode>()
//    val particles = (0 until totalNodes).map {
//        Particle(point(randPos(), randPos()), Vector(randomSpeed(), randomSpeed()), particleRadius())
//    }
//    particles[0].initialPosition = viewCenter
//    particles[0].radius = nodeRadius
//
//    val simulation = forceSimulation<Particle> {
//        forcePoint {
//            pointGet = { viewCenter }
//        }
//        forceCollision {
//            radiusGet = { domain.radius }
//            iterations = 3
//        }
//        initForceNode = {
//            position = domain.initialPosition
//        }
//        domainObjects = particles
//        intensity = 6.pct
//        intensityDecay = 0.pct
//    }
//
//    return viz {
//        size = size(vizSize, vizSize)
//        simulation.nodes.forEach { forceNode ->
//            particleVisuals += circle {
//                radius = forceNode.domain.radius
//                fill = Colors.hsl(randomAngle().deg, 80.pct, 50.pct)
//            }
//        }
//        animation {
//            val bigNode = simulation.nodes[0]
//            bigNode.x += nodeMovement.vx
//            bigNode.y += nodeMovement.vy
//            if (bigNode.x < nodeRadius || bigNode.x > (vizSize - nodeRadius)) nodeMovement = Vector(-nodeMovement.vx, nodeMovement.vy)
//            if (bigNode.y < nodeRadius || bigNode.y > (vizSize - nodeRadius)) nodeMovement = Vector(nodeMovement.vx, -nodeMovement.vy)
//            simulation.nodes.forEach { forceNode ->
//                particleVisuals[forceNode.index].apply {
//                    x = forceNode.x
//                    y = forceNode.y
//                }
//            }
//        }
//    }
//}

//data class Charge(val initialPosition:Point, val attractor:Boolean)
//
//val vizSize = 600.0
//val randPos = RandomDistribution.uniform(100.0, vizSize - 100.0)
//
//fun graph(): Viz {
//
//    val items = (0..200).map { Charge(point(randPos(), randPos()), (it % 2) == 0) }
//    val particles = mutableListOf<CircleNode>()
//
//    val simulation = forceSimulation<Charge> {
//        forceCenter {
//            center = point(vizSize / 2, vizSize / 2)
//        }
//        forceNBody {
//            strengthGet = { if (domain.attractor) 30.0 else -50.0 }
//            distanceMin = 5.0
//        }
//        initForceNode = {
//            position = domain.initialPosition
//        }
//        domainObjects = items
////        intensity = 40.pct
////        intensityDecay = 0.pct
//    }
//
//    return viz {
//        size = size(vizSize, vizSize)
//        simulation.nodes.forEach { forceNode ->
//            particles += circle {
//                radius = 10.0
//                fill = if (forceNode.domain.attractor) Colors.Web.crimson else Colors.Web.mediumblue
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

data class LayeredPoint(val position: Point, val layer:Int)

fun graph(): Viz {

    val vizSize = 400.0
    val randPos = RandomDistribution.uniform(.0, vizSize)
    val viewCenter = point(vizSize / 2, vizSize / 2)
    val items = (0..2000).map { LayeredPoint(point(randPos(), randPos()), it%12 ) }

    val simulation = forceSimulation<LayeredPoint> {
        forceRadial {
            centerGet = { viewCenter }
            radiusGet = { domain.layer * 17.0 }
        }
        domainObjects = items

        intensityDecay = 0.1.pct
        friction = 90.pct

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