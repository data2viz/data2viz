import io.data2viz.color.*
import io.data2viz.geom.*
import io.data2viz.viz.*
import io.data2viz.force.*

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

fun main() {
    val vizSize = 600.0
    val particles = mutableListOf<TextNode>()
    val particleLinks = mutableListOf<LineNode>()

    lateinit var links: List<Link<Item>>

    val simulation = forceSimulation<Item> {

        initForceNode = {

        }

        domainObjects = items
//        val f = forceLink {
//            linkGet = { domain.parents.map { Link(this, nodes[(index + 1) % items.size], it * 5.0, it * 5.0) } }
//        }
//        links = force.links
        forceCenter {
            center = Point(vizSize / 2, vizSize / 2)
        }
        forceCollision {
            radiusGet = { (5 - domain.depth) * 15.0 }
        }
    }

    viz {
        size = size(vizSize, vizSize)
//        links.forEach {
//            particleLinks += line {
//                x1 = it.source.x
//                y1 = it.source.y
//                x2 = it.target.x
//                x2 = it.target.y
//                stroke = Colors.Web.grey
//            }
//        }
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
//            links.forEachIndexed { index, _ ->
//                particleLinks[index].x1 = links[index].source.x
//                particleLinks[index].x2 = links[index].target.x
//                particleLinks[index].y1 = links[index].source.y
//                particleLinks[index].y2 = links[index].target.y
//            }
        }
    }.bindRendererOnNewCanvas()
}