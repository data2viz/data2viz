package io.data2viz.experiments.voronoi

import io.data2viz.color.colors
import io.data2viz.interpolate.linkedTo
import io.data2viz.interpolate.scale
import io.data2viz.math.Angle
import io.data2viz.math.deg
import io.data2viz.svg.svg
import io.data2viz.voronoi.Diagram
import io.data2viz.voronoi.Point
import io.data2viz.voronoi.Site
import kotlin.browser.window
import kotlin.js.Date
import kotlin.js.Math.random

fun voronoiSphere() {

    val size = 600
    val pointCount = 200

    val point = GeoPoint(0.deg, 0.deg)
    println(point)

    val randomPoints = (1..pointCount).map { GeoPoint((random() * 360).deg, (random() * 360).deg) }

    val circleRadius = scale.linear.numberToNumber(
            -1 linkedTo 4,
            1 linkedTo 1
    )

    val pointToScreen = scale.linear.numberToNumber(
            -1.0 linkedTo -200.0,
            1.0 linkedTo 200.0
    )

    fun List<GeoPoint>.sites() = mapIndexed { index, point -> Site(Point(pointToScreen(point.x), pointToScreen(point.y)), index) }.toTypedArray()

    svg {
        width = size
        height = size

        val rotationAnimation = RotationAnimation(15.0)
        rotationAnimation { rotation ->
            randomPoints.forEach { geoPoint -> geoPoint.rotation = rotation }
        }

        g {
            transform {
                translate(size / 2, size / 2)
                rotate((-20).deg)
            }

            randomPoints.forEach { geoPoint ->
                circle {
                    r = circleRadius(geoPoint.z)
                    cx = pointToScreen(geoPoint.x)
                    cy = pointToScreen(geoPoint.y)
                    rotationAnimation { rotation ->
                        r = circleRadius(geoPoint.z)
                        cx = pointToScreen(geoPoint.x)
                    }
                }
            }

            g {
                rotationAnimation { rotation ->
                    removeChildren()
                    val diagram = Diagram(randomPoints.sites()
//                            Point(-1000.0,-1000.0), Point(1000.0,1000.0)
                    )
                    diagram.edges
                            .filterNotNull()
                            .forEach { edge ->
                                if( edge.start != null && edge.end != null)
                                    line(edge.start!!.x, edge.start!!.y, edge.end!!.x, edge.end!!.y, colors.black)
                            }
                }

            }


        }
    }


}

data class GeoPoint(val lat: Angle, val long: Angle) {
    var rotation: Angle = 0.deg
    val y = lat.sin
    val r = lat.cos
    val x: Double
        get() {
            return r * (rotation + long).cos
        }
    val z: Double get() = r * (rotation + long).sin
}


//------------   API test -----------------------

class RotationAnimation(rotationTimeInSeconds: Double) {

    val startTime = Date().getTime()
    val rotationPerMs = (360.0 / (rotationTimeInSeconds * 1000)).deg

    val blocksOfAnimation = mutableListOf<(Angle) -> Unit>()

    init {
        fun animate() {
            val currentTime = Date().getTime()
            window.requestAnimationFrame {
                val rotation = rotationPerMs // * (currentTime - startTime)
                blocksOfAnimation.forEach { it(rotation) }
                animate()
            }
        }
        animate()
    }

    operator fun invoke(animation: (Angle) -> Unit) {
        blocksOfAnimation.add(animation)
    }



}
