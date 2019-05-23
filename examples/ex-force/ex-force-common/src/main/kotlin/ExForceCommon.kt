package io.data2viz.examples.force

import io.data2viz.color.*
import io.data2viz.force.*
import io.data2viz.geom.Circle
import io.data2viz.geom.Point
import io.data2viz.geom.Size
import io.data2viz.math.pct
import io.data2viz.viz.Viz
import io.data2viz.viz.viz

const val width = 800.0
const val height = 500.0

const val pointCount = 800

val simulation: ForceSimulation<Int> = forceSimulation {
    intensityDecay = 1.pct
    //nodes = (0 until pointCount).map { ForceNode(it, it, Random.nextDouble() * width, Random.nextDouble() * height) }
    on(SimulationEvent.TICK, "tickEvent", ::refresh)
    on(SimulationEvent.END, "endEvent") { println("SIMULATION ENDS") }
}

val olympicColors = listOf(
    0x0081C8.col,
    0x000000.col,
    0xEE334E.col,
    0xFCB131.col,
    0x00A651.col
)

val forces = listOf<Force<Int>>()
//        "radial" to radialForces(),
//        "diagonal" to diagonalForces(),
//        "olympic" to olympicForces(),
//        "sprite" to spriteForces()
//)

var forceIndex = 0

val forcesViz:Viz = viz {

    size = Size(800.0, 500.0)

    simulation.nodes.forEach { node ->
        circle {
            stroke = null
            radius = 2.0 + (node.index / 150)
            fill = olympicColors[node.index % 5]
            x = node.x
            y = node.y
        }
    }

    animation {
        refresh(simulation)
    }
}.also {
    simulationLoop()
}

fun refresh(sim: ForceSimulation<Int>) {
    forcesViz.activeLayer.children.forEachIndexed { index, node ->
        val forceNode = sim.nodes[index]
        val circle = node as Circle
        circle.x = forceNode.x
        circle.y = forceNode.y
    }
}

/**
 * Change simulation forces every 3,5 secondes
 */
private fun simulationLoop() {
//    println(forces[forceIndex].first)
//    (0 until forces[forceIndex].second.size).forEach { index ->
//        simulation.addForce("${forces[forceIndex].first}_$index", forces[forceIndex].second[index])
//    }
//    simulation.intensity = 1.0
//    simulation.velocityDecay = if (forceIndex == 2) 0.05 else 0.4
//    timer(delay = 3500.0) {
//        (0 until forces[forceIndex].second.size).forEach { index ->
//            simulation.removeForce("${forces[forceIndex].first}_$index")
//        }
//        forceIndex = (forceIndex + 1) % forces.size
//        stop()
//        simulationLoop()
//    }
}


// olympic colors and centers positions
val olympicCenters =
    listOf(
            Point(160.0, 180.0),
            Point(400.0, 180.0),
            Point(640.0, 180.0),
            Point(280.0, 320.0),
            Point(520.0, 320.0)
    )

val sprite = listOf(
    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3,
    0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 0, 0, 3, 3, 3,
    0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3,
    0, 0, 0, 0, 0, 1, 1, 1, 3, 3, 1, 3, 0, 1, 1, 1,
    0, 0, 0, 0, 1, 3, 1, 3, 3, 3, 1, 3, 3, 3, 1, 1,
    0, 0, 0, 0, 1, 3, 1, 1, 3, 3, 3, 1, 3, 3, 3, 1,
    0, 0, 0, 0, 1, 1, 3, 3, 3, 3, 1, 1, 1, 1, 1, 0,
    0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 1, 0, 0,
    0, 0, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 0, 0, 0,
    0, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 0, 0, 1,
    3, 3, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 0, 0, 1,
    3, 3, 3, 0, 0, 2, 1, 2, 2, 2, 3, 2, 3, 2, 1, 1,
    0, 3, 0, 1, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1,
    0, 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1,
    0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0,
    0, 1, 0, 0, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0,
    4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
    4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
    4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
    4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4
)

val spriteIndexes = olympicColors.mapIndexed { index, _ ->
    sprite.mapIndexed { idx, i ->
        if (i == index) idx else null
    }.filterNotNull()
}

//fun olympicForces() = listOf(
//    Forces.forceRadial<Int> {
//        center = { _, index, _ -> olympicCenters[index % 5] }
//        radius = { _, _, _ -> 110.0 }
//        strength = { _, _, _ -> 8.5 }
//    },
//    Forces.forceNBody<Int> {
//        strength = { _, _, _ -> -80.0 }
//        distanceMax = 30.0
//    }
//)
//
//fun radialForces() = listOf(
//    Forces.forceRadial<Int> {
//        center = { _, _, _ -> Point(width / 2, height / 2) }
//        radius = { _, _, _ -> 200.0 }
//        strength = { _, _, _ -> 0.3 }
//    },
//    Forces.forceNBody<Int> {
//        strength = { _, _, _ -> -20.0 }
//        distanceMax = 30.0
//    }
//)
//
//fun diagonalForces() = listOf(
//    Forces.forceNBody<Int> {
//        strength = { _, _, _ -> -1.0 }
//        distanceMax = 30.0
//    },
//    Forces.forceY<Int> {
//        translateY = { _, index, _ ->
//            val pos = ((index % 10) + .5) * height / 10
//            if (index % 4 < 2) pos else height - pos
//        }
//        strength = { _, _, _ -> .12 }
//    },
//    Forces.forceX<Int> {
//        xAccessor = { _, index, _ ->
//            val pos = ((index % 10) + .5) * width / 10
//            pos
////            if (index % 5 == 0) pos else width - pos
//        }
//        strengthAccessor = { _, _, _ -> .12 }
//    }
//)
//
//fun spriteForces() = listOf(
//    Forces.forceNBody<Int> {
//        strength = { _, _, _ -> -.2 }
//        distanceMax = 15.0
//    },
//    Forces.forceY<Int> {
//        translateY = { _, index, _ ->
//            val indexList = spriteIndexes[index % 5]
//            if (indexList.isEmpty()) -1000.0
//            else {
//                val posIndex = index % indexList.size
//                val spritePosition = indexList[posIndex]
//                170.0 + (((spritePosition % sprite.size) / 16) * height) / 70.0
//            }
//        }
//        strength = { _, _, _ -> .08 }
//    },
//    Forces.forceX<Int> {
//        xAccessor = { _, index, _ ->
//            val indexList = spriteIndexes[index % 5]
//            if (indexList.isEmpty()) -1000.0
//            else {
//                val posIndex = index % indexList.size
//                val spritePosition = indexList[posIndex]
//                300.0 + ((spritePosition % 16) * width) / 70.0
//            }
//        }
//        strengthAccessor = { _, _, _ -> .08 }
//    }
//)