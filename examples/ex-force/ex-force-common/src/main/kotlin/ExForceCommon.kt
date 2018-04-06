import io.data2viz.color.Color
import io.data2viz.core.Point
import io.data2viz.core.random
import io.data2viz.force.*
import io.data2viz.timer.timer
import io.data2viz.viz.circle
import io.data2viz.viz.newGroup
import io.data2viz.viz.selectElement

val width = 800.0
val height = 500.0

// random points to init scene
val points = (0 until 800).map { ForceNode(it, Point(random() * width, random() * height)) }


// olympic colors and centers positions
val olympicColors = listOf(Color(0x0081C8), Color(0x000000), Color(0xEE334E), Color(0xFCB131), Color(0x00A651))
val olympicCenters =
    listOf(Point(160.0, 180.0), Point(400.0, 180.0), Point(640.0, 180.0), Point(280.0, 320.0), Point(520.0, 320.0))

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

val spriteIndexes = olympicColors.mapIndexed { index, color ->
    sprite.mapIndexed { idx, i ->
        if (i == index) idx else null
    }.filterNotNull()
}

// force simulations and forces (radial for positionning and n-body force for element repulsion)
lateinit var simulation: ForceSimulation
val olympicForces = listOf(
    forceRadial {
        center = { _, index, _ -> olympicCenters[index % 5] }
        radius = { _, _, _ -> 110.0 }
        strength = { _, _, _ -> 8.5 }
    },
    forceNBody {
        strength = { _, index, _ -> -80.0 }
        distanceMax = 30.0
    }
)

val radialForces = listOf(
    forceRadial {
        center = { _, _, _ -> Point(width / 2, height / 2) }
        radius = { _, _, _ -> 200.0 }
        strength = { _, _, _ -> 0.3 }
    },
    forceNBody {
        strength = { _, index, _ -> -20.0 }
        distanceMax = 30.0
    }
)

val diagonalForces = listOf(
    forceNBody {
        strength = { _, _, _ -> -1.0 }
        distanceMax = 30.0
    },
    forceY {
        y = { _, index, _ ->
            val pos = ((index % 10) + .5) * height / 10
            if (index % 4 < 2) pos else height - pos
        }
        strength = { _, index, _ -> .06 }
    },
    forceX {
        x = { _, index, _ ->
            val pos = ((index % 10) + .5) * width / 10
            pos
//            if (index % 5 == 0) pos else width - pos
        }
        strength = { _, index, _ -> .06 }
    }
)

val spriteForces = listOf(
    forceNBody {
        strength = { _, _, _ -> -.2 }
        distanceMax = 15.0
    },
    forceY {
        y = { _, index, _ ->
            val indexList = spriteIndexes[index % 5]
            if (indexList.isEmpty()) -1000.0
            else {
                val posIndex = index % indexList.size
                val spritePosition = indexList[posIndex]
                170.0 + (((spritePosition % sprite.size) / 16) * height) / 70.0
            }
        }
        strength = { _, index, _ -> .08 }
    },
    forceX {
        x = { _, index, _ ->
            val indexList = spriteIndexes[index % 5]
            if (indexList.isEmpty()) -1000.0
            else {
                val posIndex = index % indexList.size
                val spritePosition = indexList[posIndex]
                300.0 + ((spritePosition % 16) * width) / 70.0
            }
        }
        strength = { _, index, _ -> .08 }
    }
)

val forces = listOf(
    Pair("radial", radialForces),
    Pair("diagonal", diagonalForces),
    Pair("olympic", olympicForces),
    Pair("sprite", spriteForces)
)
var forceIndex = 0


val root = newGroup().apply {
    simulation = forceSimulation {
        alphaDecay = 0.01
//        velocityDecay = .3
        nodes = points
        on(SimulationEvent.TICK, "tickEvent", ::refresh)
        on(SimulationEvent.END, "endEvent", { println("SIMULATION ENDS") })
    }
    updateSimulation()
}

private fun updateSimulation() {
    (0 until forces[forceIndex].second.size).forEach { index ->
        simulation.addForce("${forces[forceIndex].first}_$index", forces[forceIndex].second[index])
    }
    simulation.alpha = 1.0
    simulation.velocityDecay = if (forceIndex == 2) 0.05 else 0.4
    timer(delay = 3500.0) {
        (0 until forces[forceIndex].second.size).forEach { index ->
            simulation.removeForce("${forces[forceIndex].first}_$index")
        }
        forceIndex = (forceIndex + 1) % forces.size
        stop()
        updateSimulation()
    }
}


fun refresh(sim: ForceSimulation) {
    root.selectElement(circle, sim.nodes) {
        onEnter = {
            element.apply {
                stroke = null
                radius = 2.0 + (index / 150)
                fill = olympicColors[index % 5]
                cx = datum.position.x
                cy = datum.position.y
            }
            root.add(element)
        }

        onUpdate = {
            element.cx = datum.position.x
            element.cy = datum.position.y
        }
    }
}