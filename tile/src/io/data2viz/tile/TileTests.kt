package io.data2viz.random

import io.data2viz.core.Point
import io.data2viz.core.namespace
import io.data2viz.test.DOMExecutionContext
import io.data2viz.test.ExecutionContext
import io.data2viz.test.StringSpec
import io.data2viz.tile.Tiles
import io.data2viz.tile.tile
import kotlin.browser.document

class TileTests : StringSpec() {

    val width = 800.0
    val height = 200.0

    init {

        "Should display Geneva"  {
            context ->

            val tile = tile
            tile.width = width
            tile.height = height
            tile.translation = Point(-3000.0, 29100.0)
            tile.scale = 200000.0

            val tiles = tile.tiles()

            drawmap(context, tiles)
        }
    }

    private fun drawmap(context: ExecutionContext, tiles:Tiles) {

        if (context !is DOMExecutionContext) return

        context.element.appendChild(
                document.createElementNS(namespace.svg, "svg").apply {
                    setAttribute("width", "$width")
                    setAttribute("height", "$height")
                    tiles.tiles.forEach { tile ->
                        appendChild(
                                document.createElementNS(namespace.svg, "image").apply {
                                    setAttribute("href", "http://a.tile.openstreetmap.org/${tile.z}/${tile.x}/${tile.y}.png")
                                    setAttribute("x", "${(tile.x.toDouble() + tiles.translation.x.toDouble())*tiles.scale}")
                                    setAttribute("y", "${(tile.y.toDouble() + tiles.translation.y.toDouble())*tiles.scale}")
                                    setAttribute("width", "${tiles.scale}")
                                    setAttribute("height", "${tiles.scale}")
                                }

                        )
                    }
                }
        )
    }
}
