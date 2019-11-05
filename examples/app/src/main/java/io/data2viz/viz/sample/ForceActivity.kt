/*
 * Copyright (c) 2018-2019. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.viz.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.data2viz.color.Colors

import io.data2viz.force.*
import io.data2viz.geom.Point
import io.data2viz.geom.point
import io.data2viz.viz.*
import kotlin.math.sqrt
import kotlin.random.Random

const val nodeCount = 200
const val canvasWidth = 400.0
const val canvasHeight = 400.0

lateinit var sim: ForceSimulation<Point>
lateinit var forceLink:ForceLink<Point>

fun initSim() {
    sim = forceSimulation {
        forceNBody()
        forceCenter {
            center = point(canvasWidth / 2, canvasHeight / 2)
        }
        forceLink = forceLink {
            linkGet = {
                listOf(Link(this, nodes[sqrt(index.toDouble()).toInt()], 30.0, 1.0))
            }
        }
        on(SimulationEvent.END, "endEvent") {
            stopTimers()
        }
        domainObjects = randomPositions()
    }
}

val viz = viz {
    width = canvasWidth
    height = canvasHeight

    animation {
        activeLayer.clear()
        path {
            forceLink.links.forEach { link ->
                moveTo(link.source.x, link.source.y)
                lineTo(link.target.x, link.target.y)
            }
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

fun randomPositions() = (0 until nodeCount).map { point(Random.nextDouble() * canvasWidth, Random.nextDouble() * canvasHeight) }

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