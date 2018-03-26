import io.data2viz.color.Color
import io.data2viz.core.Point
import io.data2viz.core.random
import io.data2viz.force.*
import io.data2viz.viz.circle
import io.data2viz.viz.newGroup
import io.data2viz.viz.selectElement

val width = 1200.0
val height = 900.0


// random points to init scene
val points = (0 until 1300).map { ForceNode(it, Point(random() * width, random() * height)) }


// olympic colors and centers positions
val olympicColors = listOf(Color(0x0081C8), Color(0x000000), Color(0xEE334E), Color(0xFCB131), Color(0x00A651))
val olympicCenters =
    listOf(Point(260.0, 350.0), Point(600.0, 350.0), Point(940.0, 350.0), Point(430.0, 550.0), Point(770.0, 550.0))


// force simulations and forces (radial for positionning and n-body force for element repulsion)
lateinit var simulation: ForceSimulation
val olympicForces = listOf(
    forceRadial {
        center = { _, index, _ -> olympicCenters[index % 5] }
        radius = { _, _, _ -> 150.0 }
        strength = { _, _, _ -> 0.9 }
    },
    forceNBody {
        strength = { _, index, _ -> -8.0 }
        distanceMax = 30.0
    }
)


val root = newGroup().apply {
    simulation = forceSimulation {
        alphaDecay = 0.003
        addForce("olympic0", olympicForces[0])
        addForce("olympic1", olympicForces[1])
        nodes = points
        on(SimulationEvent.TICK, "tickEvent", ::refresh)
        on(SimulationEvent.END, "endEvent", { println("SIMULATION ENDS") })
    }
}


fun refresh(sim: ForceSimulation) {
    root.selectElement(circle, sim.nodes) {
        onEnter = {
            element.apply {
                stroke = null
                radius = 2.0 + (index / 300)
                fill = olympicColors[index%5]
                cx = datum.position.x
                cy = datum.position.y
            }
            root.add(element)
        }

        onUpdate = {
            element.cx = datum.position.x
            element.cy = datum.position.y
        }

        onExit = {
            root.remove(element)
        }
    }
}