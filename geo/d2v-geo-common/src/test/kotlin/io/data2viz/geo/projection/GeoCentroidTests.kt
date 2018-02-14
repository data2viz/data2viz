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
        GeoCentroid().result(Point(doubleArrayOf(.0, .0))) shouldBeClose doubleArrayOf(.0, .0)
        GeoCentroid().result(Point(doubleArrayOf(1.0, 1.0))) shouldBeClose doubleArrayOf(1.0, 1.0)
        GeoCentroid().result(Point(doubleArrayOf(2.0, 3.0))) shouldBeClose doubleArrayOf(2.0, 3.0)
        GeoCentroid().result(Point(doubleArrayOf(-4.0, -5.0))) shouldBeClose doubleArrayOf(-4.0, -5.0)
    }

    @Test
    fun geocentroid_of_a_set_of_points_is_the_spherical_average_of_its_constituent_members_LEGACY() {
        GeoCentroid().result(
            MultiPoint(
                listOf(
                    doubleArrayOf(.0, .0),
                    doubleArrayOf(1.0, 2.0)
                )
            )
        ) shouldBeClose doubleArrayOf(0.499847, 1.000038)

        GeoCentroid().result(
            MultiPoint(
                listOf(
                    doubleArrayOf(179.0, .0),
                    doubleArrayOf(-179.0, .0)
                )
            )
        ) shouldBeClose doubleArrayOf(180.0, .0)
    }

    @Test
    fun geocentroid_of_a_set_of_points_and_their_antipodes_is_ambiguous_LEGACY() {
        GeoCentroid().result(
            MultiPoint(
                listOf(
                    doubleArrayOf(.0, .0),
                    doubleArrayOf(180.0, .0)
                )
            )
        ) shouldBe doubleArrayOf(Double.NaN, Double.NaN)

        GeoCentroid().result(
            MultiPoint(
                listOf(
                    doubleArrayOf(.0, .0),
                    doubleArrayOf(90.0, .0),
                    doubleArrayOf(180.0, .0),
                    doubleArrayOf(-90.0, .0)
                )
            )
        ) shouldBe doubleArrayOf(Double.NaN, Double.NaN)

        GeoCentroid().result(
            MultiPoint(
                listOf(
                    doubleArrayOf(.0, .0),
                    doubleArrayOf(.0, 90.0),
                    doubleArrayOf(180.0, .0),
                    doubleArrayOf(.0, -90.0)
                )
            )
        ) shouldBe doubleArrayOf(Double.NaN, Double.NaN)
    }

    @Test
    fun geocentroid_of_an_empty_set_of_points_is_ambiguous_LEGACY() {
        GeoCentroid().result(MultiPoint(listOf())) shouldBe doubleArrayOf(Double.NaN, Double.NaN)
    }

    @Test
    fun geocentroid_of_a_linestring_is_the_spherical_average_of_its_constituent_great_arc_segments_LEGACY() {
        GeoCentroid().result(
            LineString(
                listOf(
                    doubleArrayOf(.0, .0),
                    doubleArrayOf(1.0, .0)
                )
            )
        ) shouldBeClose doubleArrayOf(0.5, .0)

        GeoCentroid().result(
            LineString(
                listOf(
                    doubleArrayOf(.0, .0),
                    doubleArrayOf(.0, 90.0)
                )
            )
        ) shouldBeClose doubleArrayOf(.0, 45.0)

        GeoCentroid().result(
            LineString(
                listOf(
                    doubleArrayOf(.0, .0),
                    doubleArrayOf(.0, 45.0),
                    doubleArrayOf(.0, 90.0)
                )
            )
        ) shouldBeClose doubleArrayOf(.0, 45.0)

        GeoCentroid().result(
            LineString(
                listOf(
                    doubleArrayOf(-1.0, -1.0),
                    doubleArrayOf(1.0, 1.0)
                )
            )
        ) shouldBeClose doubleArrayOf(.0, .0)

        GeoCentroid().result(
            LineString(
                listOf(
                    doubleArrayOf(-60.0, -1.0),
                    doubleArrayOf(60.0, 1.0)
                )
            )
        ) shouldBeClose doubleArrayOf(.0, .0)

        GeoCentroid().result(
            LineString(
                listOf(
                    doubleArrayOf(179.0, -1.0),
                    doubleArrayOf(-179.0, 1.0)
                )
            )
        ) shouldBeClose doubleArrayOf(180.0, .0)

        GeoCentroid().result(
            LineString(
                listOf(
                    doubleArrayOf(-179.0, .0),
                    doubleArrayOf(.0, .0),
                    doubleArrayOf(179.0, .0)
                )
            )
        ) shouldBeClose doubleArrayOf(.0, .0)

        GeoCentroid().result(
            LineString(
                listOf(
                    doubleArrayOf(-180.0, -90.0),
                    doubleArrayOf(.0, .0),
                    doubleArrayOf(.0, 90.0)
                )
            )
        ) shouldBeClose doubleArrayOf(.0, .0)
    }

    @Test
    fun geocentroid_of_a_great_arc_from_a_point_to_its_antipode_is_ambiguous_LEGACY() {
        GeoCentroid().result(
            LineString(
                listOf(
                    doubleArrayOf(180.0, .0),
                    doubleArrayOf(.0, .0)
                )
            )
        ) shouldBe doubleArrayOf(Double.NaN, Double.NaN)

        GeoCentroid().result(
            MultiLineString(
                listOf(
                    listOf(
                        doubleArrayOf(180.0, .0),
                        doubleArrayOf(.0, .0)
                    )
                )
            )
        ) shouldBe doubleArrayOf(Double.NaN, Double.NaN)
    }

    @Test
    fun geocentroid_of_a_set_of_linestrings_is_the_spherical_average_of_its_constituent_great_arc_segments_LEGACY() {
        GeoCentroid().result(
            MultiLineString(
                listOf(
                    listOf(
                        doubleArrayOf(.0, .0),
                        doubleArrayOf(.0, 2.0)
                    )
                )
            )
        ) shouldBeClose doubleArrayOf(.0, 1.0)
    }

    @Test
    fun geocentroid_a_line_of_zero_length_is_treated_as_points_LEGACY() {
        GeoCentroid().result(
            LineString(
                listOf(
                    doubleArrayOf(1.0, 1.0),
                    doubleArrayOf(1.0, 1.0)
                )
            )
        ) shouldBeClose doubleArrayOf(1.0, 1.0)

        // TODO
//        test.inDelta(d3.geoCentroid({type: "GeometryCollection", geometries: [{type: "Point", coordinates: [0, 0]}, {type: "LineString", coordinates: [[1, 2], [1, 2]]}]}), [0.666534, 1.333408], 1e-6);
    }

    @Test
    fun geocentroid_an_empty_polygon_with_non_zero_extent_is_treated_as_a_line_LEGACY() {
        GeoCentroid().result(
            Polygon(
                listOf(
                    listOf(
                        doubleArrayOf(1.0, 1.0),
                        doubleArrayOf(2.0, 1.0),
                        doubleArrayOf(3.0, 1.0),
                        doubleArrayOf(2.0, 1.0),
                        doubleArrayOf(1.0, 1.0)
                    )
                )
            )
        ) shouldBeClose doubleArrayOf(2.0, 1.000076)

        // TODO
//        test.inDelta(d3.geoCentroid({type: "GeometryCollection", geometries: [{type: "Point", coordinates: [0, 0]}, {type: "Polygon", coordinates: [[[1, 2], [1, 2], [1, 2], [1, 2]]]}]}), [0.799907, 1.600077], 1e-6);
    }

    @Test
    fun geocentroid_an_empty_polygon_with_zero_extent_is_treated_as_a_point_LEGACY() {
        GeoCentroid().result(
            Polygon(
                listOf(
                    listOf(
                        doubleArrayOf(1.0, 1.0),
                        doubleArrayOf(1.0, 1.0),
                        doubleArrayOf(1.0, 1.0),
                        doubleArrayOf(1.0, 1.0),
                        doubleArrayOf(1.0, 1.0)
                    )
                )
            )
        ) shouldBeClose doubleArrayOf(1.0, 1.0)

        // TODO
//        test.inDelta(d3.geoCentroid({type: "GeometryCollection", geometries: [{type: "Point", coordinates: [0, 0]}, {type: "Polygon", coordinates: [[[1, 2], [1, 2], [1, 2], [1, 2]]]}]}), [0.799907, 1.600077], 1e-6);
    }

    @Test
    fun geocentroid_of_the_equator_is_ambiguous_LEGACY() {
        GeoCentroid().result(
            LineString(
                listOf(
                    doubleArrayOf(0.0, .0),
                    doubleArrayOf(120.0, .0),
                    doubleArrayOf(-120.0, .0),
                    doubleArrayOf(.0, .0)
                )
            )
        ) shouldBe doubleArrayOf(Double.NaN, Double.NaN)
    }

    @Test
    fun geocentroid_of_a_polygon_is_the_spherical_average_of_its_surface_LEGACY() {
        GeoCentroid().result(
            Polygon(
                listOf(
                    listOf(
                        doubleArrayOf(.0, -90.0),
                        doubleArrayOf(.0, .0),
                        doubleArrayOf(.0, 90.0),
                        doubleArrayOf(1.0, .0),
                        doubleArrayOf(.0, -90.0)
                    )
                )
            )
        ) shouldBeClose doubleArrayOf(0.5, .0)

        GeoCentroid().result(
            Polygon(
                listOf(
                    (-180 .. 180).map { doubleArrayOf(it.toDouble(), -60.0) }
                )
            )
        )[1] shouldBeClose -90.0

        GeoCentroid().result(
            Polygon(
                listOf(
                    listOf(
                        doubleArrayOf(.0, -10.0),
                        doubleArrayOf(.0, 10.0),
                        doubleArrayOf(10.0, 10.0),
                        doubleArrayOf(10.0, -10.0),
                        doubleArrayOf(.0, -10.0)
                    )
                )
            )
        ) shouldBeClose doubleArrayOf(5.0, .0)
    }

    @Test
    fun geocentroid_of_a_spherical_square_touching_the_antimeridian_LEGACY() {
        GeoCentroid().result(
            Polygon(
                listOf(
                    listOf(
                        doubleArrayOf(-180.0, .0),
                        doubleArrayOf(-180.0, 10.0),
                        doubleArrayOf(-179.0, 10.0),
                        doubleArrayOf(-179.0, .0),
                        doubleArrayOf(-180.0, .0)
                    )
                )
            )
        ) shouldBeClose doubleArrayOf(-179.5, 4.987448)
    }

    @Test
    fun geocentroid_of_a_sphere_is_ambiguous_LEGACY() {
        GeoCentroid().result(Sphere(doubleArrayOf())) shouldBe doubleArrayOf(Double.NaN, Double.NaN)
    }

    /*
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

tape("the centroid of a small circle is its center: South Pole", function(test) {
  test.equal(d3.geoCentroid({type: "Polygon", coordinates: [array.range(-180, 180 + 1 / 2, 1).map(function(x) { return [x, -60]; })]})[1], -90);
  test.end();
});

tape("the centroid of a small circle is its center: equator", function(test) {
  test.inDelta(d3.geoCentroid({type: "Polygon", coordinates: [[[0, -10], [0, 10], [10, 10], [10, -10], [0, -10]]]}), [5, 0], 1e-6);
  test.end();
});

tape("the centroid of a small circle is its center: equator with coincident points", function(test) {
  test.inDelta(d3.geoCentroid({type: "Polygon", coordinates: [[[0, -10], [0, 10], [0, 10], [10, 10], [10, -10], [0, -10]]]}), [5, 0], 1e-6);
  test.end();
});

tape("the centroid of a small circle is its center: other", function(test) {
  test.inDelta(d3.geoCentroid({type: "Polygon", coordinates: [[[-180, 0], [-180, 10], [-179, 10], [-179, 0], [-180, 0]]]}), [-179.5, 4.987448], 1e-6);
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

tape("the centroid of a feature is the centroid of its constituent geometry", function(test) {
  test.inDelta(d3.geoCentroid({type: "Feature", geometry: {type: "LineString", coordinates: [[1, 1], [1, 1]]}}), [1, 1], 1e-6);
  test.inDelta(d3.geoCentroid({type: "Feature", geometry: {type: "Point", coordinates: [1, 1]}}), [1, 1], 1e-6);
  test.inDelta(d3.geoCentroid({type: "Feature", geometry: {type: "Polygon", coordinates: [[[0, -90], [0, 0], [0, 90], [1, 0], [0, -90]]]}}), [0.5, 0], 1e-6);
  test.end();
});

tape("the centroid of a feature collection is the centroid of its constituent geometry", function(test) {
  test.inDelta(d3.geoCentroid({type: "FeatureCollection", features: [
    {type: "Feature", geometry: {type: "LineString", coordinates: [[179, 0], [180, 0]]}},
    {type: "Feature", geometry: {type: "Point", coordinates: [0, 0]}}
  ]}), [179.5, 0], 1e-6);
  test.end();
});

tape("the centroid of a non-empty line string and a point only considers the line string", function(test) {
  test.inDelta(d3.geoCentroid({type: "GeometryCollection", geometries: [
    {type: "LineString", coordinates: [[179, 0], [180, 0]]},
    {type: "Point", coordinates: [0, 0]}
  ]}), [179.5, 0], 1e-6);
  test.end();
});

tape("the centroid of a non-empty polygon, a non-empty line string and a point only considers the polygon", function(test) {
  test.inDelta(d3.geoCentroid({type: "GeometryCollection", geometries: [
    {type: "Polygon", coordinates: [[[-180, 0], [-180, 1], [-179, 1], [-179, 0], [-180, 0]]]},
    {type: "LineString", coordinates: [[179, 0], [180, 0]]},
    {type: "Point", coordinates: [0, 0]}
  ]}), [-179.5, 0.500006], 1e-6);
  test.inDelta(d3.geoCentroid({type: "GeometryCollection", geometries: [
    {type: "Point", coordinates: [0, 0]},
    {type: "LineString", coordinates: [[179, 0], [180, 0]]},
    {type: "Polygon", coordinates: [[[-180, 0], [-180, 1], [-179, 1], [-179, 0], [-180, 0]]]}
  ]}), [-179.5, 0.500006], 1e-6);
  test.end();
});

tape("the centroid of the sphere and a point is the point", function(test) {
  test.deepEqual(d3.geoCentroid({type: "GeometryCollection", geometries: [
    {type: "Sphere"},
    {type: "Point", coordinates: [0, 0]}
  ]}), [0, 0]);
  test.deepEqual(d3.geoCentroid({type: "GeometryCollection", geometries: [
    {type: "Point", coordinates: [0, 0]},
    {type: "Sphere"}
  ]}), [0, 0]);
  test.end();
});

tape("the centroid of a detailed feature is correct", function(test) {
  var ny = require("./data/ny.json");
  test.inDelta(d3.geoCentroid(ny), [-73.93079, 40.69447], 1e-5);
  test.end();
});
     */
}