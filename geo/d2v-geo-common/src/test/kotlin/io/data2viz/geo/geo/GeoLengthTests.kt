package io.data2viz.geo.geo

import io.data2viz.geo.projection.pt
import io.data2viz.geo.geojson.path.GeoLength
import io.data2viz.geojson.*
import io.data2viz.test.TestBase
import kotlin.math.PI
import kotlin.test.Test

class GeoLengthTests : TestBase() {

    @Test
    fun geolength_point_returns_zero() {
        GeoLength().result(Point(pt(.0, .0))) shouldBeClose .0
    }

    @Test
    fun geolength_multipoint_returns_zero() {
        GeoLength().result(MultiPoint(arrayOf(pt(.0, 1.0), pt(2.0, 3.0)))) shouldBeClose .0
    }

    @Test
    fun geolength_linestring_returns_the_sum_of_its_great_arc_segments() {
        GeoLength().result(LineString(arrayOf(pt(-45.0, .0), pt(45.0, .0)))) shouldBeClose PI / 2
        GeoLength().result(
            LineString(
                arrayOf(
                    pt(-45.0, .0),
                    pt(-30.0, .0),
                    pt(-15.0, .0),
                    pt(.0, .0)
                )
            )
        ) shouldBeClose PI / 4
    }

    @Test
    fun geolength_multilinestring_returns_the_sum_of_its_great_arc_segments() {
        GeoLength().result(
            MultiLineString(
                arrayOf(
                    arrayOf(pt(-45.0, .0), pt(-30.0, .0)),
                    arrayOf(pt(-15.0, .0), pt(.0, .0))
                )
            )
        ) shouldBeClose PI / 6
    }

    @Test
    fun geolength_polygons_returns_the_length_of_its_perimeter() {
        GeoLength().result(
            Polygon(
                arrayOf(
                    arrayOf(
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
    fun geolength_polygons_returns_the_length_of_its_perimeter_including_holes() {
        GeoLength().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(.0, .0),
                        pt(3.0, .0),
                        pt(3.0, 3.0),
                        pt(.0, 3.0),
                        pt(.0, .0)
                    ),
                    arrayOf(
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
    fun geolength_multipolygons_returns_the_summedlength_of_its_perimeter_including_holes() {
        GeoLength().result(
            MultiPolygon(
                arrayOf(
                    arrayOf(
                        arrayOf(
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
                arrayOf(
                    arrayOf(
                        arrayOf(
                            pt(.0, .0),
                            pt(3.0, .0),
                            pt(3.0, 3.0),
                            pt(.0, 3.0),
                            pt(.0, .0)
                        )
                    ), arrayOf(
                        arrayOf(
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
    fun geolength_featurecollection_returns_the_sum_of_its_features_lengths() {
        GeoLength().result(
            FeatureCollection(
                arrayOf(
                    Feature(
                        LineString(
                            arrayOf(
                                pt(-45.0, .0),
                                pt(.0, .0)
                            )
                        )
                    ),
                    Feature(
                        LineString(
                            arrayOf(
                                pt(.0, .0),
                                pt(45.0, .0)
                            )
                        )
                    )
                )
            )
        ) shouldBeClose PI / 2
    }

    @Test
    fun geolength_geometrycollection_returns_the_sum_of_its_geometries_lengths() {
        GeoLength().result(
            GeometryCollection(
                arrayOf(
                    LineString(
                        arrayOf(
                            pt(-45.0, .0),
                            pt(.0, .0)
                        )
                    ),
                    LineString(
                        arrayOf(
                            pt(.0, .0),
                            pt(45.0, .0)
                        )
                    )
                )
            )
        ) shouldBeClose PI / 2
    }
}