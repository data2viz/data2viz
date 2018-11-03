package io.data2viz.viz.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.data2viz.color.Colors

import io.data2viz.force.*
import io.data2viz.viz.*
import kotlin.math.floor
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.random.Random

const val nodeCount = 200
const val canvasWidth = 400.0
const val canvasHeight = 400.0

lateinit var sim: ForceSimulation

fun initSim() {
    sim = forceSimulation(randomForceNodes()) {
        addForce("charge", forceNBody())
        addForce("link", forceLink {
            linksAccessor = { nodes ->
                (0 until nodeCount - 1).map { Link(nodes[floor(sqrt(it.toDouble())).roundToInt()], nodes[it + 1]) }
            }
            distancesAccessor = { links -> (0 until links.size).map { 30.0 } }
            strengthsAccessor = { links -> (0 until links.size).map { 1.0 } }
        })
        addForce("x", forceX { x = { _, _, _ -> canvasWidth / 2 } })
        addForce("y", forceY { y = { _, _, _ -> canvasHeight / 2 } })
        on(SimulationEvent.END, "endEvent") {
            stopTimers()
        }
    }
}

val viz = viz {
    width = canvasWidth
    height = canvasHeight

    onFrame {
        activeLayer.clear()
        path {
            (sim.forces["link"] as ForceLink).links.forEach { link ->
                moveTo(link.source.x, link.source.y)
                lineTo(link.target.x, link.target.y)
            }
            strokeWidth = 1.0
            stroke = Colors.Web.grey
        }

        sim.nodes.forEach { d ->
            circle {
                x = d.x
                y = d.y
                radius = 3.0
                fill = Colors.Web.black
            }
        }
    }
}

fun randomForceNodes() = (0 until nodeCount).map { ForceNode(it, Random.nextDouble() * canvasWidth, Random.nextDouble() * canvasHeight) }

lateinit var view: VizView

fun stopTimers() {
    viz.stopAnimations()
    view.stopAnimations()
}

class ForceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSim()
        view = viz.toView(this)
        setContentView(view)
        view.startAnimations()
    }

    override fun onStop() {
        super.onStop()
        stopTimers()
    }

}