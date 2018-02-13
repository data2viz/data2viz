package io.data2viz.geo.projection

import io.data2viz.geo.*
import io.data2viz.geo.path.geoPath
import io.data2viz.path.svgPath
import io.data2viz.test.TestBase
import kotlin.math.PI
import kotlin.test.Test

class BoundsTests : TestBase() {

    val equirectangular = io.data2viz.geo.projection.equirectangular() {
        scale = 900.0 / PI
        precision = .0
    }

    @Test
    fun geopath_bounds_of_a_polygon_with_no_holes_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())
        val bounds = geoPath.bounds(
            Polygon(
                listOf(
                    listOf(
                        doubleArrayOf(100.0, .0),
                        doubleArrayOf(100.0, 1.0),
                        doubleArrayOf(101.0, 1.0),
                        doubleArrayOf(101.0, .0),
                        doubleArrayOf(100.0, .0)
                    )
                )
            )
        )
        bounds.x0 shouldBe 980.0
        bounds.y0 shouldBe 245.0
        bounds.x1 shouldBe 985.0
        bounds.y1 shouldBe 250.0
    }

    @Test
    fun geopath_bounds_of_a_polygon_with_holes_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())
        val bounds = geoPath.bounds(
            Polygon(
                listOf(
                    listOf(
                        doubleArrayOf(100.0, .0),
                        doubleArrayOf(100.0, 1.0),
                        doubleArrayOf(101.0, 1.0),
                        doubleArrayOf(101.0, .0),
                        doubleArrayOf(100.0, .0)
                    ),
                    listOf(
                        doubleArrayOf(100.2, .2),
                        doubleArrayOf(100.8, .2),
                        doubleArrayOf(100.8, .8),
                        doubleArrayOf(100.2, .8),
                        doubleArrayOf(100.2, .2)
                    )
                )
            )
        )
        bounds.x0 shouldBe 980.0
        bounds.y0 shouldBe 245.0
        bounds.x1 shouldBe 985.0
        bounds.y1 shouldBe 250.0
    }

    /*
tape("geoPath.bounds(…) of a sphere", function(test) {
  test.deepEqual(testBounds(equirectangular, {
    type: "Sphere"
  }), [[-420, -200], [1380, 700]]);
  test.end();
});
     */
}