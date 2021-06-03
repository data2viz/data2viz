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

package io.data2viz.tile

import io.data2viz.geom.Point
import kotlin.math.*

internal val LN2 = ln(2.0)
internal val INFINITY: Int = 2147483647

public fun tilesLayout(init: TilesLayout.() -> Unit): TilesLayout = TilesLayout().apply(init)

/**
 * A layout for determining which 256x256 quadtree tiles to display in a rectangular viewport
 */
public class TilesLayout {


    private var stale = true

    private var origin = Point(0.0, 0.0)
    private var end = Point(960.0, 500.0)

    public var translation: Point = (origin + end) / 2

    public var zoomDelta: Double = 0.0
    set(value) {
        field = value
        stale = true
    }

    private var wrap = true

    public var width: Double
        get() = end.x - origin.x
        set(value) {
            origin = Point(0.0, origin.y)
            end = Point(value, end.y)
            stale = true
        }

    public var height: Double
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
    public var tilesCount: Double = 256.0

    private var _tileSize: Double = 256.0
    public val tileSize:Double
        get() {
            eventualyRecompute()
            return _tileSize
        }

    public var zoom: Int = 1
        get() {
            eventualyRecompute()
            return field
        }

    public var translate: Point? = null


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
    public fun tiles(): List<Tile> {

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

public data class Tile(

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
