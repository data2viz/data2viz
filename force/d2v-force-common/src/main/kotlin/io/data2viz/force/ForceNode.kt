package io.data2viz.force

import io.data2viz.geom.Point
import io.data2viz.geom.Vector
import io.data2viz.geom.point


/**
 * The position ⟨x,y⟩ and velocity ⟨vx,vy⟩ may be subsequently modified by forces and by the simulation.
 * If either vx or vy is NaN, the velocity is initialized to ⟨0,0⟩. If either x or y is NaN, the position
 * is initialized in a phyllotaxis arrangement, so chosen to ensure a deterministic, uniform distribution around the origin.
 *
 * To fix a node in a given position, you may specify two additional properties:
 *      fx - the node’s fixed x-position
 *      fy - the node’s fixed y-position
 *
 * At the end of each tick, after the application of any forces, a node with a defined node.fx
 * has node.x reset to this value and node.vx set to zero; likewise, a node with a defined node.fy
 * has node.y reset to this value and node.vy set to zero. To unfix a node that was previously fixed,
 * set node.fx and node.fy to null, or delete these properties.
 *
 * If the specified array of nodes is modified, such as when nodes are added to or removed from the
 * simulation, this method must be called again with the new (or changed) array to notify the simulation and bound
 * forces of the change; the simulation does not make a defensive copy of the specified array.
 */

data class ForceNode<D> (
    val index: Int,
    val domain: D,
    var x: Double = Double.NaN,
    var y: Double = Double.NaN,
    var vx: Double = Double.NaN,
    var vy: Double = Double.NaN,
    var fixedX: Double? = null,
    var fixedY: Double? = null
) {
    var position:Point
        get() = point(x, y)
        set(value) {
            x = value.x
            y = value.y
        }

    var velocity:Vector
        get() = Vector(vx, vy)
        set(value) {
            vx = value.vx
            vy = value.vy
        }
}