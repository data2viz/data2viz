package io.data2viz.tile

import io.data2viz.core.Point
import kotlin.js.Math

data class Tile(val x: Number, val y: Number, val z: Number, val tx: Number, val ty: Number)
data class Tiles(val tiles: MutableList<Tile>, val scale: Double, val translation: Point)

val LN2 = Math.log(2.0)
val INFINITY: Int = 2147483647

private var origin = Point(0, 0)
private var end = Point(960, 500)
private var _translation = (origin + end) / 2
private var _scale = 256.0
private var _zoomDelta = 0.0
private var _wrap = true

fun computeTiles(): Tiles {
    val z = Math.max(Math.log(_scale) / LN2 - 8.0, 0.0)
    val z0 = Math.round(z + _zoomDelta)
    val j = 1 shl z0
    val k = Math.pow(2.0, z - z0 + 8.0)
    val x = _translation.x.toDouble() - (_scale / 2)
    val y = _translation.y.toDouble() - (_scale / 2)
    val tilesList: MutableList<Tile> = arrayListOf<Tile>()

    val minCols = Math.max(if (_wrap) -INFINITY else 0, Math.floor((origin.x.toDouble() - x) / k))
    val maxCols = Math.min(Math.ceil((end.x.toDouble() - x) / k), if (_wrap) INFINITY else j)
    val minRows = Math.max(0, Math.floor((origin.y.toDouble() - y) / k))
    val maxRows = Math.min(Math.ceil((end.y.toDouble() - y) / k), j)

    (minRows..maxRows).forEach { y ->
        (minCols..maxCols).forEach { x ->
            tilesList.add(Tile(x = (x % j + j) % j, y = y, z = z0, tx = x * 256, ty = y * 256))
        }
    }

    return Tiles(tilesList, k, Point(x / k, y / k))
}


object tile {

    fun tiles() = computeTiles()

    var width
        get() = end.x.toDouble() - origin.x.toDouble()
        set(value) {
            origin = Point(0, origin.y)
            end = Point(value, end.y)
            //computeTiles()
        }

    var height
        get() = end.y.toDouble() - origin.y.toDouble()
        set(value) {
            origin = Point(origin.x, 0)
            end = Point(end.x, value)
            //computeTiles()
        }

    var scale
        get() = _scale
        set(value) {
            _scale = value
            //computeTiles()
        }

    var zoomDelta
        get() = _zoomDelta
        set(value) {
            _zoomDelta = value
            //computeTiles()
        }

    var wrap
        get() = _wrap
        set(value) {
            _wrap = value
            //computeTiles()
        }

    var translation
        get() = _translation
        set(value) {
            _translation = value
            //computeTiles()
        }

    fun setExtent(from: Point, to: Point) {
        origin = from
        end = to
        //computeTiles()
    }
}
