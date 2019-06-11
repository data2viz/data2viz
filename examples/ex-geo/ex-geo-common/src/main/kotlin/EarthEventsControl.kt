import io.data2viz.examples.geo.geoPathNode
import io.data2viz.examples.geo.geoViz
import io.data2viz.examples.geo.isProjectionSupportTransformations
import io.data2viz.examples.geo.projection
import io.data2viz.geo.geometry.*
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.math.Angle
import io.data2viz.math.deg
import io.data2viz.math.toRadians
import io.data2viz.time.Date
import io.data2viz.viz.*
import kotlin.math.max


@ExperimentalKZoomEvent
fun geoVizEventsControl(
    world: GeoJsonObject,
    projectionName: String,
    vizWidth: Double = 500.0,
    vizHeight: Double = 500.0
): Viz {
    val viz = geoViz(world, projectionName, vizWidth, vizHeight)

    if (isProjectionSupportTransformations) {
        viz.addGeoControlEvents()
        viz.launchStartRotateAnimation()
    }

    return viz
}

lateinit var startDragCartesianPoint: DoubleArray
lateinit var startDragQuaternion: DoubleArray
lateinit var startDragRotationAngles: Array<Angle>


fun Viz.launchStartRotateAnimation() {

    val durationInMs = 3000L
    val totalLambdaAngleDiffDeg = 360
    val startRotationLambda = projection.rotateLambda

    val endTime = Date().apply { plusMilliseconds(durationInMs) }
    animation {
        val diffMilliseconds = endTime.getTime() - Date().getTime()

        if (diffMilliseconds > 0) {

            val percent = 1 - diffMilliseconds / durationInMs

            val deceleratedPercent = sqrtDecelerate(percent)

            val currentFrameRotationDegree = (deceleratedPercent * totalLambdaAngleDiffDeg).deg
            projection.rotateLambda = startRotationLambda + currentFrameRotationDegree
            geoPathNode.redrawPath()
        } else {
            stop()
        }
    }
}

fun sqrtDecelerate(originPercent: Double) = (1 - (1 - originPercent) * (1 - originPercent))

/**
 *
 * Adds Drag and Zoom events listeners to Rotate and Scale geo globe
 *
 * Rotate implementation ported from https://observablehq.com/@d3/quaternion-dragging
 */
@ExperimentalKZoomEvent
fun Viz.addGeoControlEvents() {

    on(KPointerDrag) { evt ->

        println("onEvent ${evt.action} ${evt.pos}")
        when (evt.action) {

            KDragEvent.KDragAction.Start -> {

                val inverted = projection.invert(evt.pos.x, evt.pos.y)
                startDragCartesianPoint = cartesian(doubleArrayOf(inverted[0].toRadians(), inverted[1].toRadians()))
                startDragRotationAngles = arrayOf(
                    projection.rotateLambda,
                    projection.rotatePhi,
                    projection.rotateGamma
                )
                startDragQuaternion = quaternion(
                    doubleArrayOf(
                        startDragRotationAngles[0].deg,
                        startDragRotationAngles[1].deg,
                        startDragRotationAngles[2].deg
                    )

                )
            }
            KDragEvent.KDragAction.Dragging -> {

                projection.rotate(
                    startDragRotationAngles[0],
                    startDragRotationAngles[1],
                    startDragRotationAngles[2]
                )

                val inverted = projection.invert(evt.pos.x, evt.pos.y)

                val currentDragCartesianPoint =
                    cartesian(doubleArrayOf(inverted[0].toRadians(), inverted[1].toRadians()))
                val currentDragQuaternion = quaternionMultiply(
                    startDragQuaternion,
                    quaternionDelta(startDragCartesianPoint, currentDragCartesianPoint)
                )

                val rotationAngles = eulerRotation(currentDragQuaternion)
                rotateByAngles(geoPathNode, rotationAngles[0].deg, rotationAngles[1].deg, rotationAngles[2].deg)

            }
        }
    }

    on(KZoom) { evt ->
        zoomByDelta(geoPathNode, evt.delta)
    }

}

// 0 scale remove all nodes, negative scale invert geo coordinates
val minProjectionScale = 1.0

private fun zoomByDelta(
    geoPathNode: GeoPathNode,
    delta: Double
) {
    projection.scale = max(projection.scale + delta, minProjectionScale)
    geoPathNode.redrawPath()
}


private fun rotateByAngles(
    geoPathNode: GeoPathNode,
    angleLambda: Angle,
    anglePhi: Angle,
    angleGamma: Angle
) {
    projection.rotate(angleLambda, anglePhi, angleGamma)
    geoPathNode.redrawPath()
}
