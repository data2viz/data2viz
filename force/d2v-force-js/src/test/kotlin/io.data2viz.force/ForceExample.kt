package io.data2viz.force

import io.data2viz.color.Colors
import io.data2viz.viz.*
import kotlin.math.PI
import kotlin.math.roundToInt
import kotlin.math.floor
import kotlin.math.sqrt
import kotlin.random.Random

const val nodeCount = 400
const val canvasWidth = 400.0
const val canvasHeight = 400.0

val fnodes = (0 until nodeCount).map { ForceNode<Int>(it, it, Random.nextDouble() * canvasWidth, Random.nextDouble() * canvasHeight) }
val links = (0 until nodeCount - 1).map { Link(fnodes[floor(sqrt(it.toDouble())).roundToInt()], fnodes[it + 1]) }

val sim = forceSimulation<Int> {
    nodes = fnodes
    addForce("charge", Forces.forceNBody())
    addForce("link", Forces.forceLink {
        linksAccessor = { links }
        distancesAccessor = { links -> (0 until links.size).map { 30.0 } }
        strengthsAccessor = { links -> (0 until links.size).map { 1.0 } }
    })
    addForce("x", Forces.forceX { xAccessor = { _, _, _ -> canvasWidth / 2 } })
    addForce("y", Forces.forceY { y = { _, _, _ -> canvasHeight / 2 } })
    on(SimulationEvent.END, "endEvent") {
        stopRenderer()
    }

}

val viz = viz {
    width = canvasWidth
    height = canvasHeight

    animation {
        activeLayer.clear()
        path {
            links.forEach { link ->
                moveTo(link.source.x, link.source.y)
                lineTo(link.target.x, link.target.y)
            }
            strokeWidth = 1.0
            stroke = Colors.Web.grey
        }
        path {
            sim.nodes.forEach { d ->
                moveTo(d.x + 3, d.y)
                arc(d.x, d.y, 3.0, 0.0, 2 * PI);
            }
            fill = Colors.Web.black
            stroke = Colors.Web.white
            strokeWidth = 1.0
        }
    }
}

fun stopRenderer(){
    viz.stopAnimations()
}


fun main(args: Array<String>) {
    viz.bindRendererOn("viz")
}
