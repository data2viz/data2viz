package io.data2viz.geo.projection

import io.data2viz.geo.path.geoPath
import io.data2viz.geojson.*
import io.data2viz.path.svgPath
import io.data2viz.test.TestBase
import kotlin.math.PI
import kotlin.test.Ignore
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
                arrayOf(
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
                arrayOf(
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
                arrayOf(
                    arrayOf(
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
                arrayOf(
                    arrayOf(
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
                arrayOf(
                    arrayOf(
                        pt(-1.0, -1.0),
                        pt(-1.0, 2.0),
                        pt(2.0, 2.0),
                        pt(2.0, -1.0),
                        pt(-1.0, -1.0)
                    ),
                    arrayOf(
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

    @Test @Ignore
    fun geopath_measure_of_a_multipolygon_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())
        geoPath.measure(
            MultiPolygon(
                arrayOf(
                    arrayOf(
                        arrayOf(
                            pt(-1.0, -1.0),
                            pt(-1.0, 2.0),
                            pt(2.0, 2.0),
                            pt(2.0, -1.0),
                            pt(-1.0, -1.0)
                        )
                    ),
                    arrayOf(
                        arrayOf(
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