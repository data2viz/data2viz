package io.data2viz.geo.projection

import io.data2viz.geo.stream.Sphere
import io.data2viz.geo.path.geoPath
import io.data2viz.geojson.Polygon
import io.data2viz.test.TestBase
import kotlin.math.PI
import kotlin.test.Test

class PathBoundsTests : TestBase() {

    val equirectangular = equirectangularProjection {
        scale = 900.0 / PI
        precision = .0
    }

    @Test
    fun geopath_bounds_of_a_polygon_with_no_holes() {
        val geoPath = geoPath(equirectangular)
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
        bounds.x0 shouldBeClose 980.0
        bounds.y0 shouldBeClose 245.0
        bounds.x1 shouldBeClose 985.0
        bounds.y1 shouldBeClose 250.0
    }

    @Test
    fun geopath_bounds_of_a_polygon_with_holes() {
        val geoPath = geoPath(equirectangular)
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
        bounds.x0 shouldBeClose 980.0
        bounds.y0 shouldBeClose 245.0
        bounds.x1 shouldBeClose 985.0
        bounds.y1 shouldBeClose 250.0
    }

    @Test
    fun geopath_bounds_of_a_sphere() {
        val geoPath = geoPath(equirectangular)
        val bounds = geoPath.bounds(Sphere())
        bounds.x0 shouldBeClose -420.0
        bounds.y0 shouldBeClose -200.0
        bounds.x1 shouldBeClose 1380.0
        bounds.y1 shouldBeClose 700.0
    }
}