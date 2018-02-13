package io.data2viz.geo.projection

import io.data2viz.geo.*
import io.data2viz.geo.path.geoPath
import io.data2viz.path.svgPath
import io.data2viz.test.TestBase
import kotlin.math.PI
import kotlin.test.Test

class PathAreaTests : TestBase() {

    val equirectangular = io.data2viz.geo.projection.equirectangular() {
        scale = 900.0 / PI
        precision = .0
    }

    @Test
    fun geopath_area_of_a_polygon_with_no_holes_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())
        geoPath.area(
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
        ) shouldBe 25.0
    }

    @Test
    fun geopath_area_of_a_polygon_with_holes_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())
        geoPath.area(
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
        ) shouldBe 16.0
    }

    /*

tape("geoPath.area(…) of a sphere", function(test) {
  test.equal(testArea(equirectangular, {
    type: "Sphere",
  }), 1620000);
  test.end();
});;
});
     */
}