package io.data2viz.force

import io.data2viz.color.colors
import io.data2viz.math.random
import io.data2viz.viz.*
import kotlin.js.Math
import kotlin.math.roundToInt
import kotlin.math.floor
import kotlin.math.sqrt


const val nodeCount = 1000
const val width = 1000.0
const val height = 1000.0

val fnodes = (0 until nodeCount).map { ForceNode(it, random() * width - width/2, random() * height - height/2) }
val links = (0 until nodeCount - 1).map { Link(fnodes[floor(sqrt(it.toDouble())).roundToInt()], fnodes[it + 1]) }

val sim = forceSimulation {

    nodes = fnodes

    addForce("charge", forceNBody())
    addForce("link", forceLink(links))
    addForce("x", forceX())
    addForce("y", forceY())
    on(SimulationEvent.TICK, "tickEvent", ::refresh)
    on(SimulationEvent.END, "endEvent") { println("SIMULATION ENDS") }
}

val renderGroup: Group = Group().apply {
    transform {
        translate( width / 2, height / 2)
    }
}

val viz = viz {
    width = 1000.0
    height = 1000.0
    add(renderGroup)

    onFrame {
        refresh(sim)
    }
}

fun refresh(sim: ForceSimulation) {
    renderGroup.apply {
        children.clear()
        path {
            links.forEach { link ->
                moveTo(link.source.x, link.source.y)
                lineTo(link.target.x, link.target.y)
            }
            strokeWidth = 1.0
            stroke = colors.grey
        }
        path {
            sim.nodes.forEach { d ->
                moveTo(d.x + 3, d.y)
                arc(d.x, d.y, 3.0, 0.0, 2 * Math.PI);
            }
            fill = colors.black
            stroke = colors.white
            strokeWidth = 1.0
        }
    }

}


fun main(args: Array<String>) {
    viz.bindRendererOn("viz")
}
