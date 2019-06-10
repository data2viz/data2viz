import io.data2viz.examples.geo.geoPathNode
import io.data2viz.examples.geo.geoViz
import io.data2viz.examples.geo.isProjectionSupportTransformations

import io.data2viz.geojson.GeoJsonObject
import io.data2viz.math.deg
import io.data2viz.time.Date
import io.data2viz.viz.GeoPathNode
import io.data2viz.viz.Viz

fun geoVizAutoRotate(
    world: GeoJsonObject,
    projectionName: String,
    vizWidth: Double = 500.0,
    vizHeight: Double = 500.0
): Viz {
    val viz = geoViz(world, projectionName, vizWidth, vizHeight)

    viz.animation { _: Double ->
        if (isProjectionSupportTransformations) {
            rotateByTime(geoPathNode)
        }
    }
    return viz
}

private fun rotateByTime(
    geoPathNode: GeoPathNode
) {

    val projection = geoPathNode.geoProjection

    val unixTime = Date().getTime()
    // Full rotation cycles per minute
    val fullRotationCyclesPerMinute = 6

    val minute = 1000 * 60
    val ratio = (unixTime % minute) / minute
    val angle = ratio * 360 * fullRotationCyclesPerMinute % 360
    // Rotate only X axys
    projection.rotateLambda = angle.deg
    geoPathNode.redrawPath()
}
