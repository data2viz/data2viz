package io.data2viz.geo.projection

import io.data2viz.geo.*
import io.data2viz.geo.path.geoPath
import io.data2viz.path.svgPath
import io.data2viz.test.TestBase
import kotlin.math.PI
import kotlin.test.Test

class GeoCentroidTests : TestBase() {

    @Test
    fun geocentroid_of_a_point_is_itself_LEGACY() {
        GeoCentroid().result(Point(pt(.0, .0))) shouldBeClose pt(.0, .0)
        GeoCentroid().result(Point(pt(1.0, 1.0))) shouldBeClose pt(1.0, 1.0)
        GeoCentroid().result(Point(pt(2.0, 3.0))) shouldBeClose pt(2.0, 3.0)
        GeoCentroid().result(Point(pt(-4.0, -5.0))) shouldBeClose pt(-4.0, -5.0)
    }

    @Test
    fun geocentroid_of_a_set_of_points_is_the_spherical_average_of_its_constituent_members_LEGACY() {
        GeoCentroid().result(
            MultiPoint(
                listOf(
                    pt(.0, .0),
                    pt(1.0, 2.0)
                )
            )
        ) shouldBeClose pt(0.499847, 1.000038)

        GeoCentroid().result(
            MultiPoint(
                listOf(
                    pt(179.0, .0),
                    pt(-179.0, .0)
                )
            )
        ) shouldBeClose pt(180.0, .0)
    }

    @Test
    fun geocentroid_of_a_set_of_points_and_their_antipodes_is_ambiguous_LEGACY() {
        GeoCentroid().result(
            MultiPoint(
                listOf(
                    pt(.0, .0),
                    pt(180.0, .0)
                )
            )
        ) shouldBe pt(Double.NaN, Double.NaN)

        GeoCentroid().result(
            MultiPoint(
                listOf(
                    pt(.0, .0),
                    pt(90.0, .0),
                    pt(180.0, .0),
                    pt(-90.0, .0)
                )
            )
        ) shouldBe pt(Double.NaN, Double.NaN)

        GeoCentroid().result(
            MultiPoint(
                listOf(
                    pt(.0, .0),
                    pt(.0, 90.0),
                    pt(180.0, .0),
                    pt(.0, -90.0)
                )
            )
        ) shouldBe pt(Double.NaN, Double.NaN)
    }

    @Test
    fun geocentroid_of_an_empty_set_of_points_is_ambiguous_LEGACY() {
        GeoCentroid().result(MultiPoint(listOf())) shouldBe pt(Double.NaN, Double.NaN)
    }

    @Test
    fun geocentroid_of_a_linestring_is_the_spherical_average_of_its_constituent_great_arc_segments_LEGACY() {
        GeoCentroid().result(
            LineString(
                listOf(
                    pt(.0, .0),
                    pt(1.0, .0)
                )
            )
        ) shouldBeClose pt(0.5, .0)

        GeoCentroid().result(
            LineString(
                listOf(
                    pt(.0, .0),
                    pt(.0, 90.0)
                )
            )
        ) shouldBeClose pt(.0, 45.0)

        GeoCentroid().result(
            LineString(
                listOf(
                    pt(.0, .0),
                    pt(.0, 45.0),
                    pt(.0, 90.0)
                )
            )
        ) shouldBeClose pt(.0, 45.0)

        GeoCentroid().result(
            LineString(
                listOf(
                    pt(-1.0, -1.0),
                    pt(1.0, 1.0)
                )
            )
        ) shouldBeClose pt(.0, .0)

        GeoCentroid().result(
            LineString(
                listOf(
                    pt(-60.0, -1.0),
                    pt(60.0, 1.0)
                )
            )
        ) shouldBeClose pt(.0, .0)

        GeoCentroid().result(
            LineString(
                listOf(
                    pt(179.0, -1.0),
                    pt(-179.0, 1.0)
                )
            )
        ) shouldBeClose pt(180.0, .0)

        GeoCentroid().result(
            LineString(
                listOf(
                    pt(-179.0, .0),
                    pt(.0, .0),
                    pt(179.0, .0)
                )
            )
        ) shouldBeClose pt(.0, .0)

        GeoCentroid().result(
            LineString(
                listOf(
                    pt(-180.0, -90.0),
                    pt(.0, .0),
                    pt(.0, 90.0)
                )
            )
        ) shouldBeClose pt(.0, .0)
    }

    @Test
    fun geocentroid_of_a_great_arc_from_a_point_to_its_antipode_is_ambiguous_LEGACY() {
        GeoCentroid().result(
            LineString(
                listOf(
                    pt(180.0, .0),
                    pt(.0, .0)
                )
            )
        ) shouldBe pt(Double.NaN, Double.NaN)

        GeoCentroid().result(
            MultiLineString(
                listOf(
                    listOf(
                        pt(180.0, .0),
                        pt(.0, .0)
                    )
                )
            )
        ) shouldBe pt(Double.NaN, Double.NaN)
    }

    @Test
    fun geocentroid_of_a_set_of_linestrings_is_the_spherical_average_of_its_constituent_great_arc_segments_LEGACY() {
        GeoCentroid().result(
            MultiLineString(
                listOf(
                    listOf(
                        pt(.0, .0),
                        pt(.0, 2.0)
                    )
                )
            )
        ) shouldBeClose pt(.0, 1.0)
    }

    @Test
    fun geocentroid_a_line_of_zero_length_is_treated_as_points_LEGACY() {
        GeoCentroid().result(
            LineString(
                listOf(
                    pt(1.0, 1.0),
                    pt(1.0, 1.0)
                )
            )
        ) shouldBeClose pt(1.0, 1.0)

        // TODO
//        test.inDelta(d3.geoCentroid({type: "GeometryCollection", geometries: [{type: "Point", coordinates: [0, 0]}, {type: "LineString", coordinates: [[1, 2], [1, 2]]}]}), [0.666534, 1.333408], 1e-6);
    }

    @Test
    fun geocentroid_an_empty_polygon_with_non_zero_extent_is_treated_as_a_line_LEGACY() {
        GeoCentroid().result(
            Polygon(
                listOf(
                    listOf(
                        pt(1.0, 1.0),
                        pt(2.0, 1.0),
                        pt(3.0, 1.0),
                        pt(2.0, 1.0),
                        pt(1.0, 1.0)
                    )
                )
            )
        ) shouldBeClose pt(2.0, 1.000076)

        // TODO
//        test.inDelta(d3.geoCentroid({type: "GeometryCollection", geometries: [{type: "Point", coordinates: [0, 0]}, {type: "Polygon", coordinates: [[[1, 2], [1, 2], [1, 2], [1, 2]]]}]}), [0.799907, 1.600077], 1e-6);
    }

    @Test
    fun geocentroid_an_empty_polygon_with_zero_extent_is_treated_as_a_point_LEGACY() {
        GeoCentroid().result(
            Polygon(
                listOf(
                    listOf(
                        pt(1.0, 1.0),
                        pt(1.0, 1.0),
                        pt(1.0, 1.0),
                        pt(1.0, 1.0),
                        pt(1.0, 1.0)
                    )
                )
            )
        ) shouldBeClose pt(1.0, 1.0)

        // TODO
//        test.inDelta(d3.geoCentroid({type: "GeometryCollection", geometries: [{type: "Point", coordinates: [0, 0]}, {type: "Polygon", coordinates: [[[1, 2], [1, 2], [1, 2], [1, 2]]]}]}), [0.799907, 1.600077], 1e-6);
    }

    @Test
    fun geocentroid_of_the_equator_is_ambiguous_LEGACY() {
        GeoCentroid().result(
            LineString(
                listOf(
                    pt(0.0, .0),
                    pt(120.0, .0),
                    pt(-120.0, .0),
                    pt(.0, .0)
                )
            )
        ) shouldBe pt(Double.NaN, Double.NaN)
    }

    @Test
    fun geocentroid_of_a_polygon_is_the_spherical_average_of_its_surface_LEGACY() {
        GeoCentroid().result(
            Polygon(
                listOf(
                    listOf(
                        pt(.0, -90.0),
                        pt(.0, .0),
                        pt(.0, 90.0),
                        pt(1.0, .0),
                        pt(.0, -90.0)
                    )
                )
            )
        ) shouldBeClose pt(0.5, .0)

        GeoCentroid().result(
            Polygon(
                listOf(
                    (-180..180).map { pt(it.toDouble(), -60.0) }
                )
            )
        )[1] shouldBeClose -90.0

        GeoCentroid().result(
            Polygon(
                listOf(
                    listOf(
                        pt(.0, -10.0),
                        pt(.0, 10.0),
                        pt(10.0, 10.0),
                        pt(10.0, -10.0),
                        pt(.0, -10.0)
                    )
                )
            )
        ) shouldBeClose pt(5.0, .0)
    }

    @Test
    fun geocentroid_of_a_spherical_square_touching_the_antimeridian_LEGACY() {
        GeoCentroid().result(
            Polygon(
                listOf(
                    listOf(
                        pt(-180.0, .0),
                        pt(-180.0, 10.0),
                        pt(-179.0, 10.0),
                        pt(-179.0, .0),
                        pt(-180.0, .0)
                    )
                )
            )
        ) shouldBeClose pt(-179.5, 4.987448)
    }

    @Test
    fun geocentroid_of_a_sphere_is_ambiguous_LEGACY() {
        GeoCentroid().result(Sphere()) shouldBe pt(Double.NaN, Double.NaN)
    }

    @Test
    fun geocentroid_of_a_small_circle_is_its_center_south_pole_LEGACY() {
        GeoCentroid().result(
            Polygon(
                listOf(
                    (-180..180).map { pt(it.toDouble(), -60.0) }
                )
            )
        )[1] shouldBe -90.0
    }

    @Test
    fun geocentroid_of_a_small_circle_is_its_center_equator_LEGACY() {
        GeoCentroid().result(
            Polygon(
                listOf(
                    listOf(
                        pt(.0, -10.0),
                        pt(.0, 10.0),
                        pt(10.0, 10.0),
                        pt(10.0, -10.0),
                        pt(.0, -10.0)
                    )
                )
            )
        ) shouldBeClose pt(5.0, .0)
    }

    @Test
    fun geocentroid_of_a_small_circle_is_its_center_equator_with_coincident_points_LEGACY() {
        GeoCentroid().result(
            Polygon(
                listOf(
                    listOf(
                        pt(.0, -10.0),
                        pt(.0, 10.0),
                        pt(.0, 10.0),
                        pt(10.0, 10.0),
                        pt(10.0, -10.0),
                        pt(.0, -10.0)
                    )
                )
            )
        ) shouldBeClose pt(5.0, .0)
    }

    @Test
    fun geocentroid_of_a_small_circle_is_its_center_other_LEGACY() {
        GeoCentroid().result(
            Polygon(
                listOf(
                    listOf(
                        pt(-180.0, .0),
                        pt(-180.0, 10.0),
                        pt(-179.0, 10.0),
                        pt(-179.0, .0),
                        pt(-180.0, .0)
                    )
                )
            )
        ) shouldBeClose pt(-179.5, 4.987448)
    }

    @Test
    fun geocentroid_of_a_feature_is_the_center_of_its_constituent_geometry_LEGACY() {
        GeoCentroid().result(
            Feature(
                LineString(
                    listOf(
                        pt(1.0, 1.0),
                        pt(1.0, 1.0)
                    )
                )
            )
        ) shouldBeClose pt(1.0, 1.0)

        GeoCentroid().result(
            Feature(
                Point(
                    pt(1.0, 1.0)
                )
            )
        ) shouldBeClose pt(1.0, 1.0)

        GeoCentroid().result(
            Feature(
                Polygon(
                    listOf(
                        listOf(
                            pt(.0, -90.0),
                            pt(.0, .0),
                            pt(.0, 90.0),
                            pt(1.0, .0),
                            pt(.0, -90.0)
                        )
                    )
                )
            )
        ) shouldBeClose pt(.5, .0)
    }

    @Test
    fun geocentroid_of_a_featureCollection_is_the_center_of_its_constituent_geometry_LEGACY() {
        GeoCentroid().result(
            FeatureCollection(
                listOf(
                    LineString(
                        listOf(
                            pt(179.0, .0),
                            pt(180.0, .0)
                        )
                    ),
                    Point(pt(.0, .0))
                )
            )
        ) shouldBeClose pt(179.5, .0)
    }

    @Test
    fun geocentroid_of_a_non_empty_linestring_and_a_point_only_considers_the_line_string_LEGACY() {
        GeoCentroid().result(
            GeometryCollection(
                listOf(
                    LineString(
                        listOf(
                            pt(179.0, .0),
                            pt(180.0, .0)
                        )
                    ),
                    Point(pt(.0, .0))
                )
            )
        ) shouldBeClose pt(179.5, .0)
    }

    @Test
    fun geocentroid_of_a_non_empty_polygon_a_non_empty_linestring_and_a_point_only_considers_the_polygon_LEGACY() {
        GeoCentroid().result(
            GeometryCollection(
                listOf(
                    Polygon(
                        listOf(
                            listOf(
                                pt(-180.0, .0),
                                pt(-180.0, 1.0),
                                pt(-179.0, 1.0),
                                pt(-179.0, .0),
                                pt(-180.0, .0)
                            )
                        )
                    ),
                    LineString(
                        listOf(
                            pt(179.0, .0),
                            pt(180.0, .0)
                        )
                    ),
                    Point(pt(.0, .0))
                )
            )
        ) shouldBeClose pt(-179.5, 0.500006)

        GeoCentroid().result(
            GeometryCollection(
                listOf(
                    Point(pt(.0, .0)),
                    LineString(
                        listOf(
                            pt(179.0, .0),
                            pt(180.0, .0)
                        )
                    ),
                    Polygon(
                        listOf(
                            listOf(
                                pt(-180.0, .0),
                                pt(-180.0, 1.0),
                                pt(-179.0, 1.0),
                                pt(-179.0, .0),
                                pt(-180.0, .0)
                            )
                        )
                    )
                )
            )
        ) shouldBeClose pt(-179.5, 0.500006)
    }

    @Test
    fun geocentroid_of_a_the_sphere_and_a_point_is_the_point_LEGACY() {
        GeoCentroid().result(
            FeatureCollection(
                listOf(
                    Sphere(),
                    Point(pt(1.0, 2.0))
                )
            )
        ) shouldBeClose pt(1.0, 2.0)

        GeoCentroid().result(
            FeatureCollection(
                listOf(
                    Point(pt(2.0, 3.0)),
                    Sphere()
                )
            )
        ) shouldBeClose pt(2.0, 3.0)
    }

    /*
tape("the centroid of a detailed feature is correct", function(test) {
  var ny = require("./data/ny.json");
  test.inDelta(d3.geoCentroid(ny), [-73.93079, 40.69447], 1e-5);
  test.end();
});

tape("the centroid of a set of polygons is the (spherical) average of its surface", function(test) {
  var circle = d3.geoCircle();
  test.inDelta(d3.geoCentroid({
    type: "MultiPolygon",
    coordinates: [
      circle.radius(45).center([90, 0])().coordinates,
      circle.radius(60).center([-90, 0])().coordinates
    ]
  }), [-90, 0], 1e-6);
  test.end();
});

tape("the centroid of a small circle is its center: 5°", function(test) {
  test.inDelta(d3.geoCentroid(d3.geoCircle().radius(5).center([30, 45])()), [30, 45], 1e-6);
  test.end();
});

tape("the centroid of a small circle is its center: 135°", function(test) {
  test.inDelta(d3.geoCentroid(d3.geoCircle().radius(135).center([30, 45])()), [30, 45], 1e-6);
  test.end();
});

tape("the centroid of a small circle is its center: concentric rings", function(test) {
  var circle = d3.geoCircle().center([0, 45]),
      coordinates = circle.radius(60)().coordinates;
  coordinates.push(circle.radius(45)().coordinates[0].reverse());
  test.inDelta(d3.geoCentroid({type: "Polygon", coordinates: coordinates}), [0, 45], 1e-6);
  test.end();
});

tape("concentric rings", function(test) {
  var circle = d3.geoCircle().center([0, 45]),
      coordinates = circle.radius(60)().coordinates;
  coordinates.push(circle.radius(45)().coordinates[0].reverse());
  test.inDelta(d3.geoCentroid({type: "Polygon", coordinates: coordinates}), [0, 45], 1e-6);
  test.end();
});
     */
}