package io.data2viz.geo.projection

import io.data2viz.geo.*
import io.data2viz.geo.path.geoPath
import io.data2viz.path.svgPath
import io.data2viz.test.TestBase
import kotlin.math.PI
import kotlin.test.Test

class GeoLengthTests : TestBase() {

    @Test
    fun geolength_point_returns_zero_LEGACY() {
        GeoLength().result(Point(pt(.0, .0))) shouldBeClose .0
    }

    @Test
    fun geolength_multipoint_returns_zero_LEGACY() {
        GeoLength().result(MultiPoint(listOf(pt(.0, 1.0), pt(2.0, 3.0)))) shouldBeClose .0
    }

    @Test
    fun geolength_linestring_returns_the_sum_of_its_great_arc_segments_LEGACY() {
        GeoLength().result(LineString(listOf(pt(-45.0, .0), pt(45.0, .0)))) shouldBeClose PI / 2
        GeoLength().result(
            LineString(
                listOf(
                    pt(-45.0, .0),
                    pt(-30.0, .0),
                    pt(-15.0, .0),
                    pt(.0, .0)
                )
            )
        ) shouldBeClose PI / 4
    }

    @Test
    fun geolength_multilinestring_returns_the_sum_of_its_great_arc_segments_LEGACY() {
        GeoLength().result(
            MultiLineString(
                listOf(
                    listOf(pt(-45.0, .0), pt(-30.0, .0)),
                    listOf(pt(-15.0, .0), pt(.0, .0))
                )
            )
        ) shouldBeClose PI / 6
    }

    @Test
    fun geolength_polygons_returns_the_length_of_its_perimeter_LEGACY() {
        GeoLength().result(
            Polygon(
                listOf(
                    listOf(
                        pt(.0, .0),
                        pt(3.0, .0),
                        pt(3.0, 3.0),
                        pt(.0, 3.0),
                        pt(.0, .0)
                    )
                )
            )
        ) shouldBeClose 0.157008
    }

    @Test
    fun geolength_polygons_returns_the_length_of_its_perimeter_including_holes_LEGACY() {
        GeoLength().result(
            Polygon(
                listOf(
                    listOf(
                        pt(.0, .0),
                        pt(3.0, .0),
                        pt(3.0, 3.0),
                        pt(.0, 3.0),
                        pt(.0, .0)
                    ),
                    listOf(
                        pt(1.0, 1.0),
                        pt(2.0, 1.0),
                        pt(2.0, 2.0),
                        pt(1.0, 2.0),
                        pt(1.0, 1.0)
                    )
                )
            )
        ) shouldBeClose 0.209354
    }

    @Test
    fun geolength_multipolygons_returns_the_summedlength_of_its_perimeter_including_holes_LEGACY() {
        GeoLength().result(
            MultiPolygon(
                listOf(
                    listOf(
                        listOf(
                            pt(.0, .0),
                            pt(3.0, .0),
                            pt(3.0, 3.0),
                            pt(.0, 3.0),
                            pt(.0, .0)
                        )
                    )
                )
            )
        ) shouldBeClose 0.157008

        GeoLength().result(
            MultiPolygon(
                listOf(
                    listOf(
                        listOf(
                            pt(.0, .0),
                            pt(3.0, .0),
                            pt(3.0, 3.0),
                            pt(.0, 3.0),
                            pt(.0, .0)
                        )
                    ), listOf(
                        listOf(
                            pt(1.0, 1.0),
                            pt(2.0, 1.0),
                            pt(2.0, 2.0),
                            pt(1.0, 2.0),
                            pt(1.0, 1.0)
                        )
                    )
                )
            )
        ) shouldBeClose 0.209354
    }

    @Test
    fun geolength_featurecollection_returns_the_sum_of_its_features_lengths_LEGACY() {
        GeoLength().result(
            FeatureCollection(
                listOf(
                    LineString(
                        listOf(
                            pt(-45.0, .0),
                            pt(.0, .0)
                        )
                    ),
                    LineString(
                        listOf(
                            pt(.0, .0),
                            pt(45.0, .0)
                        )
                    )
                )
            )
        ) shouldBeClose PI / 2
    }

    @Test
    fun geolength_geometrycollection_returns_the_sum_of_its_geometries_lengths_LEGACY() {
        GeoLength().result(
            GeometryCollection(
                listOf(
                    LineString(
                        listOf(
                            pt(-45.0, .0),
                            pt(.0, .0)
                        )
                    ),
                    LineString(
                        listOf(
                            pt(.0, .0),
                            pt(45.0, .0)
                        )
                    )
                )
            )
        ) shouldBeClose PI / 2
    }
}