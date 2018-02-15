package io.data2viz.geo.projection

import io.data2viz.geo.*
import io.data2viz.geo.path.geoPath
import io.data2viz.path.svgPath
import io.data2viz.test.TestBase
import kotlin.math.PI
import kotlin.test.Ignore
import kotlin.test.Test

class PathAreaTests : TestBase() {

    val equirectangular = io.data2viz.geo.projection.equirectangular {
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
                        pt(100.0, .0),
                        pt(100.0, 1.0),
                        pt(101.0, 1.0),
                        pt(101.0, .0),
                        pt(100.0, .0)
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
                        pt(100.0, .0),
                        pt(100.0, 1.0),
                        pt(101.0, 1.0),
                        pt(101.0, .0),
                        pt(100.0, .0)
                    ),
                    listOf(
                        pt(100.2, .2),
                        pt(100.8, .2),
                        pt(100.8, .8),
                        pt(100.2, .8),
                        pt(100.2, .2)
                    )
                )
            )
        ) shouldBe 16.0
    }

    // TODO : when clipping will be OK activate this one
    @Test @Ignore
    fun geopath_area_of_a_sphere_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())
        geoPath.area(Sphere()) shouldBe 1620000.0
    }
}