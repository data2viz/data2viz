package io.data2viz.force

import kotlin.math.min
import kotlin.math.sqrt

// TODO rename something more precise ?
data class Link<D>(
    val source: ForceNode<D>,
    val target: ForceNode<D>,
    val distance: Double = 30.0,
    var strength: Double = Double.NaN
)


/**
 * The link force pushes linked nodes together or apart according to the desired link distance.
 * The strength of the force is proportional to the difference between the linked nodes’ distance and the target
 * distance, similar to a spring force.
 */
class ForceLink<D> internal constructor(): Force<D> {

    private var nodes = listOf<ForceNode<D>>()

    private var _links = listOf<Link<D>>()
    val links: List<Link<D>>
        get() = _links

    private var bias: Array<Double> =  arrayOf()
    private var count:Array<Int> = arrayOf()

    /**
     * Number of iterations per application.
     * Increasing the number of iterations greatly increases the rigidity of the constraint and avoids partial overlap
     * of nodes, but also increases the runtime cost to evaluate the force.
     */
    var iterations = 1

    var linkGet: ForceNode<D>.()-> List<Link<D>> = { listOf() }

//    /**
//     * sets the strength accessor to the specified number or function, re-evaluates
//     * the strength accessor for each link, and returns this force.
//     *
//     * ```
//     * ```
//     */
//    var strengthGet: (List<Link<D>>) -> List<Double> = { links ->
//            links.map { link ->
//                1.0 / min(count[link.source.index], count[link.target.index])
//            }
//
//        }
//        set(value) {
//            field = value
//            initializeStrengths()
//        }
//
//    /**
//     * sets the distance accessor to the specified number or function,
//     * re-evaluates the distance accessor for each link, and returns this force.
//     *
//     * The distance accessor is invoked for each link, being passed the link and its zero-based index.
//     * The resulting number is then stored internally, such that the distance of each link is only
//     * recomputed when the force is initialized or when this method is called with a new distance,
//     * and not on every application of the force.
//     */
//    var distanceGet: (List<Link<D>>) -> List<Double> = { links -> (0 until links.size).map { 30.0 } }
//        set(value) {
//            field = value
//            initializeDistances()
//        }

    override fun assignNodes(nodes: List<ForceNode<D>>) {
        this.nodes = nodes
        val linksList = mutableListOf<Link<D>>()
        nodes.forEach {
            linksList += linkGet(it)
        }
        _links = linksList

        // count links for each nodes
        count = Array(nodes.size){ 0 }
        _links.forEachIndexed { index, link ->
            count[link.source.index] += 1
            count[link.target.index] += 1
        }

        // count bias
        bias = Array(_links.size) {.0}
        _links.forEachIndexed { index, link ->
            bias[index] = count[link.source.index].toDouble() / (count[link.source.index] + count[link.target.index])
        }

        initializeStrengths()
    }

    // TODO EXPLAIN!
    private fun initializeStrengths() {
        _links.filter { it.strength.isNaN() }
            .forEach { it.strength = 1.0 / min(count[it.source.index], count[it.target.index]) }
    }

    override fun applyForceToNodes(alpha: Double) {
        (0 until iterations).forEach {
            _links.forEachIndexed { index, link ->
                val source = link.source
                val target = link.target

                var x = target.x + target.vx - source.x - source.vx
                if (x == .0) x = jiggle()

                var y = target.y + target.vy - source.y - source.vy
                if (y == .0) y = jiggle()

                var l = sqrt(x * x + y * y)
                l = (l - link.distance) / l * alpha * link.strength
                x *= l
                y *= l

                var b = bias[index]
                target.vx -= x * b
                target.vy -= y * b

                b = 1 - b
                source.vx += x * b
                source.vy += y * b
            }
        }
    }
}