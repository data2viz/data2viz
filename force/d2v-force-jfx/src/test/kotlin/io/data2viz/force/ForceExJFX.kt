import io.data2viz.color.*
import io.data2viz.geom.*
import io.data2viz.viz.*
import io.data2viz.force.*
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.layout.VBox
import javafx.stage.Stage

data class Item(val name:String, val index:Int, val depth: Int, val parents:List<Int> = listOf())

val items = listOf(
    Item("ROOT", 0, 0),
    Item("A", 1, 1, listOf(0)),
    Item("A1", 2, 2, listOf(1)),
    Item("A2", 3, 2, listOf(1)),
    Item("A3", 4, 2, listOf(1)),
    Item("A4", 5, 2, listOf(1, 8)),
    Item("B", 6, 1, listOf(0)),
    Item("B1", 7, 2, listOf(6)),
    Item("B2", 8, 2, listOf(6)),
    Item("B3", 9, 2, listOf(6)),
    Item("C", 10, 1, listOf(0)),
    Item("C1", 11, 2, listOf(10)),
    Item("A31", 12, 3, listOf(4, 13, 14)),
    Item("A32", 13, 3, listOf(4)),
    Item("A33", 14, 3, listOf(4))
)

val vizSize = 600.0

class ForceExJFX: Application() {
    val canvas = Canvas(vizSize, vizSize)
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
            it.scene = (Scene(root, vizSize, vizSize))
            it.show()
            root.children.add(canvas)
            renderChart()
        }
    }

}

fun graph(): Viz {

    val particles = mutableListOf<TextNode>()
    val particleLinks = mutableListOf<LineNode>()

    lateinit var links: List<Link<Item>>

    val simulation = forceSimulation<Item> {
        domainObjects = items

        initForceNode = {

        }

        val f = forceLink {
            linkGet = { domain.parents.map { Link(this, nodes[it]) } }
            iterations = 10
        }
        links = f.links

        forceCenter {
            center = Point(vizSize / 2, vizSize / 2)
        }
        forceCollision {
            radiusGet = { (5 - domain.depth) * 15.0 }
            iterations = 10
        }
    }
    links.forEach { println(it) }

    val myViz = viz {
        size = size(vizSize, vizSize)
        links.forEach {
            particleLinks += line {
                x1 = it.source.x
                y1 = it.source.y
                x2 = it.target.x
                x2 = it.target.y
                stroke = Colors.Web.grey
            }
        }
        simulation.nodes.forEach {
            particles += text {
                fill = Colors.Web.black
                textContent = items[it.index].name
                textAlign = textAlign(TextHAlign.MIDDLE, TextVAlign.MIDDLE)
                fontWeight = FontWeight.BOLD
            }
        }

        animation {
            simulation.nodes.forEach { node ->
                particles[node.index].x = node.x
                particles[node.index].y = node.y
            }
            links.forEachIndexed { index, _ ->
                particleLinks[index].x1 = links[index].source.x
                particleLinks[index].x2 = links[index].target.x
                particleLinks[index].y1 = links[index].source.y
                particleLinks[index].y2 = links[index].target.y
            }
        }
    }

    return myViz
}