package io.data2viz.geo.projection

import io.data2viz.geo.geojson.Sphere
import io.data2viz.geo.geojson.geoPath
import io.data2viz.geojson.Polygon
import io.data2viz.test.TestBase
import kotlin.math.PI
import kotlin.test.Test

class PathAreaTests : TestBase() {

    val equirectangular = equirectangularProjection {
        scale = 900.0 / PI
        precision = .0
    }

    @Test
    fun geopath_area_of_a_polygon_with_no_holes() {
        val geoPath = geoPath(equirectangular)
        geoPath.area(
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
        ) shouldBeClose 25.0
    }

    @Test
    fun geopath_area_of_a_polygon_with_holes() {
        val geoPath = geoPath(equirectangular)
        geoPath.area(
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
        ) shouldBeClose 16.0
    }

    @Test
    fun geopath_area_of_a_sphere() {
        val geoPath = geoPath(equirectangular)
        geoPath.area(Sphere()) shouldBeClose 1620000.0
    }
}