/*
 * Copyright (c) 2018-2021. data2viz sàrl.
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

package io.data2viz.force

import io.data2viz.geom.Point
import io.data2viz.math.Percent
import io.data2viz.math.pct
import io.data2viz.timer.timer
import kotlin.math.*

private const val initialRadius = 10.0
private val initialAngle = PI * (3.0 - sqrt(5.0))

/**
 * Creates a new simulation with the specified array of nodes and no forces.
 * If nodes is not specified, it defaults to the empty list.
 * The simulator starts automatically; use simulation.on to listen for tick events as the simulation runs.
 * If you wish to run the simulation manually instead, call simulation.stop, and then call simulation.tick as desired.
 */
public class ForceSimulation<D> internal constructor() {

    private var started = false

    // AVAILABLE FORCES
    public fun forceX(init: ForceX<D>.() -> Unit = {}): ForceX<D> = addForce(ForceX<D>().apply(init)) as ForceX
    public fun forceY(init: ForceY<D>.() -> Unit = {}): ForceY<D> = addForce(ForceY<D>().apply(init)) as ForceY
    public fun forcePoint(init: ForcePoint<D>.() -> Unit = {}): ForcePoint<D> = addForce(ForcePoint<D>().apply(init)) as ForcePoint
    public fun forceRadial(init: ForceRadial<D>.() -> Unit): ForceRadial<D> = addForce(ForceRadial<D>().apply(init)) as ForceRadial
    public fun forceNBody(init: ForceNBody<D>.() -> Unit = {}): ForceNBody<D> = addForce(ForceNBody<D>().apply(init)) as ForceNBody
    public fun forceCollision(init: ForceCollision<D>.() -> Unit): ForceCollision<D> = addForce(ForceCollision<D>().apply(init)) as ForceCollision
    public fun forceCenter(init: ForceCenter<D>.() -> Unit): ForceCenter<D> = addForce(ForceCenter<D>().apply(init)) as ForceCenter
    public fun forceLink(init: ForceLink<D>.() -> Unit = {}): ForceLink<D> = addForce(ForceLink<D>().apply(init)) as ForceLink

    /**
     * The initForceNode lambda applies to each ForceNode.
     * A ForceNode is created for each domain objects passed to the simulation.
     * ForceNode already have an index, and a domain object, so you can use these 2 properties to initialize your nodes.
     */
    public var initForceNode: ForceNode<D>.() -> Unit = { }
        set(value) {
            field = value
            initSimulation(true)
        }

    /**
     * Pass your domain objects to the simulation and initialize the ForceNodes.
     * If there are no existing nodes, or the domain object associated to a node at a given index have changed,
     * then the node is initialized using the initForceNode lambda.
     * If at a given index, the "new" domain object is the same as the "old" one, the node will keep its
     * properties.
     * This allows you to add some nodes "on the fly" in the simulation just by setting the domainObjects var.
     * Just be careful, your domain objects must keep the same index, so new objects must be added at the end
     * of the list.
     */
    public var domainObjects: List<D> = listOf()
        set(value) {
            field = value
            initSimulation(true)
        }

    private var _nodes = listOf<ForceNode<D>>()
    public val nodes: List<ForceNode<D>>
        get() = _nodes

    private var _forces = listOf<Force<D>>()
    public val forces: List<Force<D>>
        get() = _forces


    private val tickEvents = mutableMapOf<String, (ForceSimulation<D>) -> Unit>()
    private val endEvents = mutableMapOf<String, (ForceSimulation<D>) -> Unit>()
    private val stepper = timer { step() }

    /**
     * Restarts current simulation
     */
    public fun play() {
        stepper.restart { step() }
    }

    /**
     * stops the current simulation
     */
    public fun stop() {
        stepper.stop()
    }

    private fun step() {
        tick()
        tickEvents.values.forEach { callback ->
            callback(this)
        }
        if (intensity < intensityMin) {
            stepper.stop()
            endEvents.values.forEach { callback ->
                callback(this)
            }
        }
    }


    /**
     * Sets the current intensity to the specified positive percentage in the range which defaults to 100%.
     */
    public var intensity: Percent = 100.pct
        set(value) {
            field = value.coerceAtLeast(0.pct)
        }

    /**
     * Sets the minimum intensity to the specified positive percentage (defaults to 0.1%).
     * The simulation’s internal timer stops when the current intensity is less than the minimum intensity.
     * The default intensity decay rate of ~2.28% corresponds to 300 iterations.
     */
    public var intensityMin: Percent = 0.1.pct
        set(value) {
            field = value.coerceAtLeast(0.pct)
        }

    /**
     * Sets the intensity decay rate to the specified positive percentage.
     * Defaults to 2.28…% = 1 - pow(0.1%, 1 / 300) where 0.1% is the default minimum intensity.
     *
     * The intensity decay rate determines how quickly the current intensity interpolates towards the desired target
     * intensity; since the default target intensity is zero, by default this controls how quickly the simulation cools.
     * Higher decay rates cause the simulation to stabilize more quickly, but risk getting stuck in a local minimum;
     * lower values cause the simulation to take longer to run, but typically converge on a better layout.
     *
     * To have the simulation run forever at the current intensity, set the decay rate to zero; alternatively, set a
     * target intensity greater than the minimum intensity.
     */
    public var intensityDecay: Percent = Percent(1.0 - intensityMin.value.pow(1.0 / 300.0))
        set(value) {
            field = value.coerceAtLeast(0.pct)
        }

    /**
     * Sets the current target intensity to the specified positive percentage (defaults to 0%).
     */
    public var intensityTarget: Percent = 0.pct
        set(value) {
            field = value.coerceAtLeast(0.pct)
        }

    private var _friction = 0.6

    /**
     * Sets the friction factor to the specified percentage in the range [0,100] which defaults to 40%.
     * As with lowering the intensity decay rate, less friction may converge on a better solution, but risks
     * numerical instabilities and oscillation.
     */
    public var friction: Percent
        get() = Percent(1 - _friction)
        set(value) {
            _friction = 1 - value.coerceToDefault().value
        }

    /**
     * Add the force in this simulation.
     */
    private fun addForce(force: Force<D>):Force<D> {
        initializeForce(force)
        _forces += force
        return force
    }

    /**
     * InitSimulation is called when the simulation starts.
     * Check if everything is initialized as some properties may have been set after some others.
     * updateNodes is set to false when the simulation starts (this erase all nodes values) else it is set to true
     * (this update all previous nodes values)
     */
    private fun initSimulation(updateNodes:Boolean) {
        initializeNodes(updateNodes)
        _forces.forEach { initializeForce(it) }
    }

    /**
     * Removes the force in this simulation.
     */
    public fun removeForce(force: Force<D>) {
        _forces -= force
    }

    private fun initializeForce(force: Force<D>) {
        force.assignNodes(nodes)
    }

    /**
     * Increments the current intensity by (intensityTarget - intensity) × intensityDecay;
     * then invokes each registered force, passing the new intensity;
     * then decrements each node’s velocity by velocity × friction;
     * lastly increments each node’s position by velocity.
     *
     * This method does not dispatch events;
     * events are only dispatched by the internal timer when the simulation is started automatically upon creation
     * or by calling simulation.restart.
     * The natural number of ticks when the simulation is started is ⌈log(intensityMin) / log(1 - intensityDecay)⌉;
     * by default, this is 300.
     *
     * This method can be used in conjunction with simulation.stop to compute a static force layout.
     */
    // TODO For large graphs, static layouts should be computed in a web worker to avoid freezing the user interface.
    // TODO private ?
    public fun tick() {
        if (!started) {
            started = true
            initSimulation(false)
        }

        intensity += (intensityTarget - intensity) * intensityDecay

        _forces.forEach { force ->
            force.applyForceToNodes(intensity.value)
        }

        nodes.forEach { node ->
            if (node.fixedX != null) {
                node.x = node.fixedX!!
                node.vx = .0
            } else {
                node.vx *= _friction
                node.x += node.vx
            }
            if (node.fixedY != null) {
                node.y = node.fixedY!!
                node.vy = .0
            } else {
                node.vy *= _friction
                node.y += node.vy
            }
        }
    }

    private fun initializeNodes(updateNodes:Boolean) {
        val oldNodes = _nodes.toList()
        val oldNodeSize = oldNodes.size
        _nodes = List(domainObjects.size) { ForceNode(it, domainObjects[it]) }
        domainObjects.forEachIndexed { index, domain ->
            val node = _nodes[index]
            if (updateNodes && index < oldNodeSize && oldNodes[index].domain == node.domain) {
                val oldNode = oldNodes[index]
                node.position = oldNode.position
                node.velocity = oldNode.velocity
                node.fixedX = oldNode.fixedX
                node.fixedY = oldNode.fixedY
            } else {
                node.initForceNode()
                if (node.x.isNaN() || node.y.isNaN()) {
                    val radius = initialRadius * sqrt(index.toDouble())
                    val angle = index * initialAngle
                    node.x = radius * cos(angle)
                    node.y = radius * sin(angle)
                }
                if (node.vx.isNaN() || node.vy.isNaN()) {
                    node.vx = .0
                    node.vy = .0
                }
            }
        }
    }

    /**
     * Returns the node closest to the position with the given search radius.
     * If radius is not specified, it defaults to infinity.
     * If there is no node within the search area, returns null.
     */
    public fun find(point: Point, radius: Double = Double.POSITIVE_INFINITY): ForceNode<D>? {
        var newRadius = if (radius < Double.POSITIVE_INFINITY) radius * radius else radius
        var closest: ForceNode<D>? = null

        nodes.forEach { node ->
            val dx = point.x - node.x
            val dy = point.y - node.y
            val d2 = dx * dx + dy * dy
            if (d2 < newRadius) {
                closest = node
                newRadius = d2
            }
        }

        return closest
    }

    /**
     * If listener is specified, sets the event listener for the specified typenames and returns this simulation.
     * If an event listener was already registered for the same type and name, the existing listener is removed
     * before the new listener is added.
     * If listener is null, removes the current event listeners for the specified typenames, if any.
     * If listener is not specified, returns the first currently-assigned listener matching the specified typenames, if any.
     * When a specified event is dispatched, each listener will be invoked with the this context as the simulation.
     *
     * The typenames is a string containing one or more typename separated by whitespace.
     * Each typename is a type, optionally followed by a period (.) and a name, such as tick.foo and tick.bar;
     * the name allows multiple listeners to be registered for the same type. The type must be one of the following:
     *
     * - tick - after each tick of the simulation’s internal timer.
     * - end - after the simulation’s timer stops when intensity < intensityMin.
     *
     * Note that tick events are not dispatched when simulation.tick is called manually;
     * events are only dispatched by the internal timer and are intended for interactive rendering of the simulation.
     *
     * To affect the simulation, register forces instead of modifying nodes’ positions or velocities inside a tick
     * event listener.
     */
    // TODO : change doc and plug to dispatch (?)
    public fun on(type: SimulationEvent, name: String, callback: (ForceSimulation<D>) -> Unit) {
        when (type) {
            SimulationEvent.TICK -> tickEvents[name] = callback
            SimulationEvent.END -> endEvents[name] = callback
        }
    }

}

public enum class SimulationEvent {
    TICK, END
}

