package io.data2viz.force

import io.data2viz.geom.Vector
import kotlin.math.min
import kotlin.math.sqrt

data class Link(
    val source: ForceNode,
    val target: ForceNode,
    var index: Int = 0
)

fun forceLink(linker: (from: ForceNode, to:ForceNode) -> Boolean, init: ForceLink.() -> Unit) = ForceLink(linker).apply(init)
fun forceLink(linker: (from: ForceNode, to:ForceNode) -> Boolean) = ForceLink(linker)

/**
 * The link force pushes linked nodes together or apart according to the desired link distance.
 * The strength of the force is proportional to the difference between the linked nodes’ distance and the target
 * distance, similar to a spring force.
 */
class ForceLink(val linker: (from: ForceNode, to:ForceNode) -> Boolean) : Force {

    private var nodes: List<ForceNode> = listOf()
    private val links = mutableListOf<Link>()
    private val distances = mutableListOf<Double>()
    private val strengths = mutableListOf<Double>()
    private val bias = mutableListOf<Double>()
    private val count = mutableListOf<Int>()

    /**
     * If iterations is specified, sets the number of iterations per application to the specified number.
     * Increasing the number of iterations greatly increases the rigidity of the constraint and avoids partial overlap
     * of nodes, but also increases the runtime cost to evaluate the force.
     */
    var iterations = 1

    /**
     * TODO
     */
    var strength: (link: Link, index: Int, links: List<Link>) -> Double = { link, _, _ -> 1.0 / min(count[link.source.index], count[link.target.index]) }
        set(value) {
            field = value
            initialize(nodes)
        }

    /**
     * TODO
     */
    var distance: (link: Link, index: Int, links: List<Link>) -> Double = { _, _, _ -> 30.0 }
        set(value) {
            field = value
            initialize(nodes)
        }

    override fun initialize(nodes: List<ForceNode>) {
        this.nodes = nodes

        // build links
        links.clear()
        nodes.forEach { from ->
            nodes.forEach { to->
                if (linker(from, to)) links.add(Link(from, to))
            }
        }

        // count links for each nodes
        count.clear()
        count.addAll(nodes.map { 0 })
        links.forEachIndexed { index, link ->
            link.index = index
            count[link.source.index] += 1
            count[link.target.index] += 1
        }

        // count bias
        bias.clear()
        bias.addAll(links.map { .0 })
        links.forEachIndexed { index, link ->
            bias[index] = count[link.source.index].toDouble() / (count[link.source.index] + count[link.target.index])
        }

        // compute strengths and distances
        strengths.clear()
        distances.clear()
        links.forEachIndexed { index, link ->
            strengths.add(strength(link, index, links))
            distances.add(distance(link, index, links))
        }
    }

    override fun invoke(alpha: Double) {
        (0 until iterations).forEach {
            links.forEachIndexed { index, link ->
                val source = link.source
                val target = link.target

                var x = target.position.x + target.velocity.vx - source.position.x - source.velocity.vx
                if (x == .0) x = jiggle()

                var y = target.position.y + target.velocity.vy - source.position.y - source.velocity.vy
                if (y == .0) y = jiggle()

                var l = sqrt(x * x + y * y)
                l = (l - distances[index]) / l * alpha * strengths[index]
                x *= l
                y *= l

                var b = bias[index]
                target.velocity -= Vector(x * b, y * b)

                b = 1 - b
                source.velocity += Vector(x * b, y * b)
            }
        }
    }
}