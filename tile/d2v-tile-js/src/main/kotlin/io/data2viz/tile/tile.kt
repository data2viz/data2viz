package io.data2viz.tile

import io.data2viz.core.Point
import kotlin.math.*

val LN2 = ln(2.0)
val INFINITY: Int = 2147483647

fun tilesLayout(init: TilesLayout.() -> Unit): TilesLayout = TilesLayout().apply(init)

/**
 * A layout for determining which 256x256 quadtree tiles to display in a rectangular viewport
 */
class TilesLayout {


    private var stale = true

    private var origin = Point(0.0, 0.0)
    private var end = Point(960.0, 500.0)

    var translation = (origin + end) / 2

    var zoomDelta = 0.0
    set(value) {
        field = value
        stale = true
    }

    private var wrap = true

    var width
        get() = end.x - origin.x
        set(value) {
            origin = Point(0.0, origin.y)
            end = Point(value, end.y)
            stale = true
        }

    var height
        get() = end.y - origin.y
        set(value) {
            origin = Point(origin.x, 0.0)
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
        val z = max(ln(tilesCount) / LN2 - 8.0, 0.0)

        //zoom. http://wiki.openstreetmap.org/wiki/Zoom_levels
        //zoom = 0 => 1 tiles, zoom = 1 => 4 tiles, ...
        // scale = 200_000 => z = 10
        zoom = round(z + zoomDelta).toInt()

        // k = 195,3125 (tile size)
        _tileSize = 2.0.pow(z - zoom + 8.0)
    }

    /**
     * Returns the list of tiles defined by the current layout
     */
    fun tiles(): List<Tile> {

        if(stale) eventualyRecompute()

        //number of horizontal tile. zoom = 10 => 1024 horizontal tiles
        val j = 1 shl zoom

        val x = translation.x - (tilesCount / 2)
        val y = translation.y - (tilesCount / 2)
        translate = Point(x, y) / _tileSize

        val minCols = max(if (wrap) -INFINITY else 0, floor((origin.x - x) / _tileSize).toInt())
        val maxCols = min(ceil((end.x - x) / _tileSize).toInt(), if (wrap) INFINITY else j)
        val minRows = max(0, floor((origin.y - y) / _tileSize).toInt())
        val maxRows = min(ceil((end.y - y) / _tileSize).toInt(), j)

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
        get() = layout.tileSize * (layout.translate!!.x + tileX)


    val y:Double
        get() = layout.tileSize * (layout.translate!!.y + tileY)

}
