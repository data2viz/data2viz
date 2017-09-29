package io.data2viz.tile

import io.data2viz.core.Point
import io.data2viz.core.namespace
import io.data2viz.math.Angle
import io.data2viz.math.PI
import io.data2viz.math.THETA
import io.data2viz.math.deg
import kotlin.browser.document
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.pow
import kotlin.math.tan

class TileTests {

    data class GeoPoint(val latitude: Angle, val longitude: Angle)

    val GENEVA = GeoPoint(latitude = 46.2.deg, longitude = 6.1667.deg)


    /**
     * http://wiki.openstreetmap.org/wiki/Slippy_map_tilenames
     */
    fun GeoPoint.long2tile(zoom: Int) = ((longitude + PI) / THETA) * 2.0.pow(zoom.toDouble())

    fun GeoPoint.lat2tile(zoom: Int) = (1 - ln(tan(latitude.rad) + 1 / cos(latitude.rad)) / kotlin.math.PI) / 2 * 2.0.pow(zoom.toDouble())


    fun shouldDisplayGeneva() {

        //see http://bl.ocks.org/mbostock/94b9fd26e12c586f342d
        val layout = tilesLayout {
            width = 800.0
            height = 200.0
            tilesCount = 200_000.0
        }
        layout.translation = Point(
                    (layout.tilesCount / 2) - GENEVA.long2tile(layout.zoom) * layout.tileSize,
                    (layout.tilesCount / 2) - GENEVA.lat2tile(layout.zoom) * layout.tileSize)
        drawmap(layout)
    }
}

private fun drawmap(layout: TilesLayout) {


    document.body?.appendChild(
            document.createElementNS(namespace.svg, "svg").apply {
                setAttribute("width", "${layout.width}")
                setAttribute("height", "${layout.height}")
                layout.tiles().forEach { tile ->
                    appendChild(
                            document.createElementNS(namespace.svg, "image").apply {
                                setAttribute("href", "http://a.tile.openstreetmap.org/${tile.zoom}/${tile.tileX}/${tile.tileY}.png")
                                setAttribute("x", "${tile.x}")
                                setAttribute("y", "${tile.y}")
                                setAttribute("width", "${layout.tileSize}")
                                setAttribute("height", "${layout.tileSize}")
                            }
                    )
                }
            }
    )
}
