package io.data2viz.geo.projection

import io.data2viz.geo.Sphere
import io.data2viz.geo.path.geoPath
import io.data2viz.geojson.Polygon
import io.data2viz.path.svgPath
import io.data2viz.test.TestBase
import kotlin.math.PI
import kotlin.test.Ignore
import kotlin.test.Test

class PathBoundsTests : TestBase() {

    val equirectangular = io.data2viz.geo.projection.equirectangular() {
        scale = 900.0 / PI
        precision = .0
    }

    @Test
    fun geopath_bounds_of_a_polygon_with_no_holes_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())
        val bounds = geoPath.bounds(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(100.0, .0),
                        pt(100.0, 1.0),
                        pt(101.0, 1.0),
                        pt(101.0, .0),
                        pt(100.0, .0)
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
                arrayOf(
                    arrayOf(
                        pt(100.0, .0),
                        pt(100.0, 1.0),
                        pt(101.0, 1.0),
                        pt(101.0, .0),
                        pt(100.0, .0)
                    ),
                    arrayOf(
                        pt(100.2, .2),
                        pt(100.8, .2),
                        pt(100.8, .8),
                        pt(100.2, .8),
                        pt(100.2, .2)
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
    fun geopath_bounds_of_a_sphere_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())
        val bounds = geoPath.bounds(Sphere())
        bounds.x0 shouldBe -420.0
        bounds.y0 shouldBe -200.0
        bounds.x1 shouldBe 1380.0
        bounds.y1 shouldBe 700.0
    }
}