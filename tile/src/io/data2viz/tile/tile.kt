package io.data2viz.tile

import io.data2viz.core.Point
import kotlin.js.Math

val LN2 = Math.log(2.0)
val INFINITY: Int = 2147483647

fun tilesLayout(init: TilesLayout.() -> Unit): TilesLayout = TilesLayout().apply(init)

/**
 * A layout for determining which 256x256 quadtree tiles to display in a rectangular viewport
 */
class TilesLayout {


    private var stale = true

    private var origin = Point(0, 0)
    private var end = Point(960, 500)

    var translation = (origin + end) / 2

    var zoomDelta = 0.0
    set(value) {
        field = value
        stale = true
    }

    private var wrap = true

    var width
        get() = end.x.toDouble() - origin.x.toDouble()
        set(value) {
            origin = Point(0, origin.y)
            end = Point(value, end.y)
            stale = true
        }

    var height
        get() = end.y.toDouble() - origin.y.toDouble()
        set(value) {
            origin = Point(origin.x, 0)
            end = Point(end.x, value)
            stale = true
        }

    /**
     * The number of tiles used to represent the planet.
     * See http://wiki.openstreetmap.org/wiki/Zoom_levels
     * Determines zoom level and tileSize.
     */
    var tilesCount: Double = 256.0

    private var _tileSize: Double = 256.0
    val tileSize:Double
        get() {
            eventualyRecompute()
            return _tileSize
        }

    var zoom = 1
        get() {
            eventualyRecompute()
            return field
        }

    var translate:Point? = null


    private fun eventualyRecompute(){

        if (!stale) return
        stale = false

        // scale = 200_000 => z = 9,61
        val z = Math.max(Math.log(tilesCount) / LN2 - 8.0, 0.0)

        //zoom. http://wiki.openstreetmap.org/wiki/Zoom_levels
        //zoom = 0 => 1 tiles, zoom = 1 => 4 tiles, ...
        // scale = 200_000 => z = 10
        zoom = Math.round(z + zoomDelta)

        // k = 195,3125 (tile size)
        _tileSize = Math.pow(2.0, z - zoom + 8.0)
    }

    /**
     * Returns the list of tiles defined by the current layout
     */
    fun tiles(): List<Tile> {

        if(stale) eventualyRecompute()

        //number of horizontal tile. zoom = 10 => 1024 horizontal tiles
        val j = 1 shl zoom

        val x = translation.x.toDouble() - (tilesCount / 2)
        val y = translation.y.toDouble() - (tilesCount / 2)
        translate = Point(x, y) / _tileSize

        val minCols = Math.max(if (wrap) -INFINITY else 0, Math.floor((origin.x.toDouble() - x) / _tileSize))
        val maxCols = Math.min(Math.ceil((end.x.toDouble() - x) / _tileSize), if (wrap) INFINITY else j)
        val minRows = Math.max(0, Math.floor((origin.y.toDouble() - y) / _tileSize))
        val maxRows = Math.min(Math.ceil((end.y.toDouble() - y) / _tileSize), j)

        val tiles = mutableListOf<Tile>()
        (minRows..maxRows).forEach { row ->
            (minCols..maxCols).forEach { col ->
                tiles.add(
                        Tile(
                                layout = this,
                                tileX = (col % j + j) % j,
                                tileY = row))
            }
        }

        return tiles
    }
}

data class Tile(

        /**
         * The current layout context of this title.
         */
        val layout: TilesLayout,

        /**
         * The integer X coordinate of the tile address. Periodic if wrap is set to true.
         */
        val tileX: Int,

        /**
         * The integer Y coordinate of the tile address.
         */
        val tileY: Int) {


    /**
     * The integer Z coordinate of the tile address (zoom level).
     */
    val zoom: Int
        get() = layout.zoom

    val x:Double
        get() = layout.tileSize * (layout.translate!!.x.toDouble() + tileX)


    val y:Double
        get() = layout.tileSize * (layout.translate!!.y.toDouble() + tileY)

}
