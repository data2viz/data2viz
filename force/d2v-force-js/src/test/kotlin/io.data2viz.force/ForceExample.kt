package io.data2viz.force

import io.data2viz.color.colors
import io.data2viz.geom.Point
import io.data2viz.math.random
import io.data2viz.viz.Circle
import io.data2viz.viz.bindRendererOn
import io.data2viz.viz.viz
import kotlin.math.roundToInt
import kotlin.math.floor
import kotlin.math.sqrt


const val nodeCount = 1000
const val width = 1000.0
const val height = 1000.0

val nodes = (0 until nodeCount).map { ForceNode(it, random() * width, random() * height) }
val links = (0 until nodeCount-1).map { Link(nodes[floor(sqrt(it.toDouble())).roundToInt()], nodes[it +1]) }

fun main(args: Array<String>) {

    val viz = viz {
        width = 1000.0
        height = 1000.0

        rect {
            x = 100.0
            y = 100.0
            width = 100.0
            height = 100.0
            fill = colors.red
        }
    }


    fun refresh(sim: ForceSimulation) {
        viz.activeLayer.children.forEachIndexed { index, node ->
            val forceNode = sim.nodes[index]
            val circle = node as Circle
            circle.x = forceNode.x
            circle.y = forceNode.y
        }
    }


    forceSimulation {
        addForce("charge", forceNBody {  })
//        addForce("link", forceLink { from, to ->  })
        addForce("x", forceX {  })
        addForce("y", forceY {  })
        on(SimulationEvent.TICK, "tickEvent", ::refresh)
        on(SimulationEvent.END, "endEvent") { println("SIMULATION ENDS") }
    }

    viz.bindRendererOn("viz")
    println("yo")

}
