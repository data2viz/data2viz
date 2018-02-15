package io.data2viz.geo.projection

import io.data2viz.geo.*
import io.data2viz.geo.path.geoPath
import io.data2viz.path.svgPath
import io.data2viz.test.TestBase
import kotlin.math.PI
import kotlin.test.Test

class PathMeasureTests : TestBase() {

    val equirectangular = io.data2viz.geo.projection.equirectangular {
        scale = 180.0 / PI
        precision = .0
    }

    @Test
    fun geopath_measure_of_a_point_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())
        geoPath.measure(Point(pt(.0, .0))) shouldBe .0
    }

    @Test
    fun geopath_measure_of_a_multipoint_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())
        geoPath.measure(
            MultiPoint(
                listOf(
                    pt(.0, .0),
                    pt(.0, 1.0),
                    pt(1.0, 1.0),
                    pt(1.0, .0)
                )
            )
        ) shouldBe .0
    }

    @Test
    fun geopath_measure_of_a_lineString_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())
        geoPath.measure(
            LineString(
                listOf(
                    pt(.0, .0),
                    pt(.0, 1.0),
                    pt(1.0, 1.0),
                    pt(1.0, .0)
                )
            )
        ) shouldBe 3.0
    }

    @Test
    fun geopath_measure_of_a_multilineString_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())
        geoPath.measure(
            MultiLineString(
                listOf(
                    listOf(
                        pt(.0, .0),
                        pt(.0, 1.0),
                        pt(1.0, 1.0),
                        pt(1.0, .0)
                    )
                )
            )
        ) shouldBe 3.0
    }

    @Test
    fun geopath_measure_of_a_polygon_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())
        geoPath.measure(
            Polygon(
                listOf(
                    listOf(
                        pt(.0, .0),
                        pt(.0, 1.0),
                        pt(1.0, 1.0),
                        pt(1.0, .0),
                        pt(.0, .0)
                    )
                )
            )
        ) shouldBe 4.0
    }

    @Test
    fun geopath_measure_of_a_polygon_with_a_hole_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())
        geoPath.measure(
            Polygon(
                listOf(
                    listOf(
                        pt(-1.0, -1.0),
                        pt(-1.0, 2.0),
                        pt(2.0, 2.0),
                        pt(2.0, -1.0),
                        pt(-1.0, -1.0)
                    ),
                    listOf(
                        pt(.0, .0),
                        pt(1.0, .0),
                        pt(1.0, 1.0),
                        pt(.0, 1.0),
                        pt(.0, .0)
                    )
                )
            )
        ) shouldBe 16.0
    }

    @Test
    fun geopath_measure_of_a_multipolygon_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())
        geoPath.measure(
            MultiPolygon(
                listOf(
                    listOf(
                        listOf(
                            pt(-1.0, -1.0),
                            pt(-1.0, 2.0),
                            pt(2.0, 2.0),
                            pt(2.0, -1.0),
                            pt(-1.0, -1.0)
                        )
                    ),
                    listOf(
                        listOf(
                            pt(.0, .0),
                            pt(1.0, .0),
                            pt(1.0, 1.0),
                            pt(.0, 1.0),
                            pt(.0, .0)
                        )
                    )
                )
            )
        ) shouldBe 16.0
    }
}