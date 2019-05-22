package io.data2viz.geo.projection

import io.data2viz.geo.geojson.geoPath
import io.data2viz.geojson.*
import io.data2viz.math.toRadians
import io.data2viz.test.TestBase
import kotlin.test.Test

class PathMeasureTests : TestBase() {

    @Test
    fun geopath_measure_of_a_point() {
        val geoPath = geoPath()
        geoPath.measure(Point(pt(.0, .0))) shouldBeClose .0
    }

    @Test
    fun geopath_measure_of_a_multipoint() {
        val geoPath = geoPath()
        geoPath.measure(
            MultiPoint(
                arrayOf(
                    pt(.0, .0),
                    pt(.0, 1.0),
                    pt(1.0, 1.0),
                    pt(1.0, .0)
                )
            )
        ) shouldBeClose .0
    }

    @Test
    fun geopath_measure_of_a_lineString() {
        val geoPath = geoPath()
        geoPath.measure(
            LineString(
                arrayOf(
                    pt(.0, .0),
                    pt(.0, 1.0),
                    pt(1.0, 1.0),
                    pt(1.0, .0)
                )
            )
        ) shouldBeClose 3.0
    }

    @Test
    fun geopath_measure_of_a_multilineString() {
        val geoPath = geoPath()
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
        ) shouldBeClose 3.0

        geoPath.measure(
            MultiLineString(
                arrayOf(
                    arrayOf(
                        pt(.0, .0),
                        pt(1.0, .0),
                        pt(1.0, 1.0),
                        pt(.0, 1.0)
                    )
                )
            )
        ) shouldBeClose 3.0
    }

    @Test
    fun geopath_measure_of_a_polygon() {
        val geoPath = geoPath()
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
        ) shouldBeClose 4.0

        geoPath.measure(
            Polygon(
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
        ) shouldBeClose 4.0
    }

    @Test
    fun geopath_measure_of_a_polygon_with_a_hole() {
        val geoPath = geoPath()
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
        ) shouldBeClose 16.0
    }

    @Test
    fun geopath_measure_of_a_multipolygon() {
        val geoPath = geoPath()
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
        ) shouldBeClose 16.0

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
                            pt(.0, 1.0),
                            pt(1.0, 1.0),
                            pt(1.0, .0),
                            pt(.0, .0)
                        )
                    )
                )
            )
        ) shouldBeClose 16.0
    }

    @Test
    fun geopath_measure_of_2_points() {
        val geoPath = geoPath()

        // distance from one site to the other of the bridge of the coullouvrenière Genève 272.28m
        val measure = geoPath.measure(LineString(arrayOf(pt(46.2041005, 6.1399304), pt(46.2065154, 6.1395255))))
        measure.toRadians() * 6371000 shouldBeClose 272.272898
    }
}