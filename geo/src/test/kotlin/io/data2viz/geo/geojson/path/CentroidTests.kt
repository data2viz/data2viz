/*
 * Copyright (c) 2018-2021. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.geo.geojson.path


import io.data2viz.geo.geojson.Sphere
import io.data2viz.geo.projection.pt
import io.data2viz.geojson.*
import io.data2viz.test.TestBase
import kotlin.test.Test

class CentroidTests : TestBase() {

    @Test
    fun geocentroid_of_a_point_is_itself() {
        GeoCentroidStream().result(Point(pt(.0, .0))) shouldBeClose doubleArrayOf(.0, .0)
        GeoCentroidStream().result(Point(pt(1.0, 1.0))) shouldBeClose doubleArrayOf(1.0, 1.0)
        GeoCentroidStream().result(Point(pt(2.0, 3.0))) shouldBeClose doubleArrayOf(2.0, 3.0)
        GeoCentroidStream().result(Point(pt(-4.0, -5.0))) shouldBeClose doubleArrayOf(-4.0, -5.0)
    }

    @Test
    fun geocentroid_of_a_set_of_points_is_the_spherical_average_of_its_constituent_members() {
        GeoCentroidStream().result(
            MultiPoint(
                arrayOf(
                    pt(.0, .0),
                    pt(1.0, 2.0)
                )
            )
        ) shouldBeClose doubleArrayOf(0.499847, 1.000038)

        GeoCentroidStream().result(
            MultiPoint(
                arrayOf(
                    pt(179.0, .0),
                    pt(-179.0, .0)
                )
            )
        ) shouldBeClose doubleArrayOf(180.0, .0)
    }

    @Test
    fun geocentroid_of_a_set_of_points_and_their_antipodes_is_ambiguous() {
        GeoCentroidStream().result(
            MultiPoint(
                arrayOf(
                    pt(.0, .0),
                    pt(180.0, .0)
                )
            )
        ) shouldBe doubleArrayOf(Double.NaN, Double.NaN)

        GeoCentroidStream().result(
            MultiPoint(
                arrayOf(
                    pt(.0, .0),
                    pt(90.0, .0),
                    pt(180.0, .0),
                    pt(-90.0, .0)
                )
            )
        ) shouldBe doubleArrayOf(Double.NaN, Double.NaN)

        GeoCentroidStream().result(
            MultiPoint(
                arrayOf(
                    pt(.0, .0),
                    pt(.0, 90.0),
                    pt(180.0, .0),
                    pt(.0, -90.0)
                )
            )
        ) shouldBe doubleArrayOf(Double.NaN, Double.NaN)
    }

    @Test
    fun geocentroid_of_an_empty_set_of_points_is_ambiguous() {
        GeoCentroidStream().result(MultiPoint(arrayOf())) shouldBe doubleArrayOf(Double.NaN, Double.NaN)
    }

    @Test
    fun geocentroid_of_a_linestring_is_the_spherical_average_of_its_constituent_great_arc_segments() {
        GeoCentroidStream().result(
            LineString(
                arrayOf(
                    pt(.0, .0),
                    pt(1.0, .0)
                )
            )
        ) shouldBeClose doubleArrayOf(0.5, .0)

        GeoCentroidStream().result(
            LineString(
                arrayOf(
                    pt(.0, .0),
                    pt(.0, 90.0)
                )
            )
        ) shouldBeClose doubleArrayOf(.0, 45.0)

        GeoCentroidStream().result(
            LineString(
                arrayOf(
                    pt(.0, .0),
                    pt(.0, 45.0),
                    pt(.0, 90.0)
                )
            )
        ) shouldBeClose doubleArrayOf(.0, 45.0)

        GeoCentroidStream().result(
            LineString(
                arrayOf(
                    pt(-1.0, -1.0),
                    pt(1.0, 1.0)
                )
            )
        ) shouldBeClose doubleArrayOf(.0, .0)

        GeoCentroidStream().result(
            LineString(
                arrayOf(
                    pt(-60.0, -1.0),
                    pt(60.0, 1.0)
                )
            )
        ) shouldBeClose doubleArrayOf(.0, .0)

        GeoCentroidStream().result(
            LineString(
                arrayOf(
                    pt(179.0, -1.0),
                    pt(-179.0, 1.0)
                )
            )
        ) shouldBeClose doubleArrayOf(180.0, .0)

        GeoCentroidStream().result(
            LineString(
                arrayOf(
                    pt(-179.0, .0),
                    pt(.0, .0),
                    pt(179.0, .0)
                )
            )
        ) shouldBeClose doubleArrayOf(.0, .0)

        GeoCentroidStream().result(
            LineString(
                arrayOf(
                    pt(-180.0, -90.0),
                    pt(.0, .0),
                    pt(.0, 90.0)
                )
            )
        ) shouldBeClose doubleArrayOf(.0, .0)
    }

    @Test
    fun geocentroid_of_a_great_arc_from_a_point_to_its_antipode_is_ambiguous() {
        GeoCentroidStream().result(
            LineString(
                arrayOf(
                    pt(180.0, .0),
                    pt(.0, .0)
                )
            )
        ) shouldBe doubleArrayOf(Double.NaN, Double.NaN)

        GeoCentroidStream().result(
            MultiLineString(
                arrayOf(
                    arrayOf(
                        pt(180.0, .0),
                        pt(.0, .0)
                    )
                )
            )
        ) shouldBe doubleArrayOf(Double.NaN, Double.NaN)
    }

    @Test
    fun geocentroid_of_a_set_of_linestrings_is_the_spherical_average_of_its_constituent_great_arc_segments() {
        GeoCentroidStream().result(
            MultiLineString(
                arrayOf(
                    arrayOf(
                        pt(.0, .0),
                        pt(.0, 2.0)
                    )
                )
            )
        ) shouldBeClose doubleArrayOf(.0, 1.0)
    }

    @Test
    fun geocentroid_a_line_of_zero_length_is_treated_as_points() {
        GeoCentroidStream().result(
            LineString(
                arrayOf(
                    pt(1.0, 1.0),
                    pt(1.0, 1.0)
                )
            )
        ) shouldBeClose doubleArrayOf(1.0, 1.0)

        // TODO
//        test.inDelta(d3.geoCentroid({type: "GeometryCollection", geometries: [{type: "Point", coordinates: [0, 0]}, {type: "LineString", coordinates: [[1, 2], [1, 2]]}]}), [0.666534, 1.333408], 1e-6);
    }

    @Test
    fun geocentroid_an_empty_polygon_with_non_zero_extent_is_treated_as_a_line() {
        GeoCentroidStream().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(1.0, 1.0),
                        pt(2.0, 1.0),
                        pt(3.0, 1.0),
                        pt(2.0, 1.0),
                        pt(1.0, 1.0)
                    )
                )
            )
        ) shouldBeClose doubleArrayOf(2.0, 1.000076)

        // TODO
//        test.inDelta(d3.geoCentroid({type: "GeometryCollection", geometries: [{type: "Point", coordinates: [0, 0]}, {type: "Polygon", coordinates: [[[1, 2], [1, 2], [1, 2], [1, 2]]]}]}), [0.799907, 1.600077], 1e-6);
    }

    @Test
    fun geocentroid_an_empty_polygon_with_zero_extent_is_treated_as_a_point() {
        GeoCentroidStream().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(1.0, 1.0),
                        pt(1.0, 1.0),
                        pt(1.0, 1.0),
                        pt(1.0, 1.0),
                        pt(1.0, 1.0)
                    )
                )
            )
        ) shouldBeClose doubleArrayOf(1.0, 1.0)

        // TODO
//        test.inDelta(d3.geoCentroid({type: "GeometryCollection", geometries: [{type: "Point", coordinates: [0, 0]}, {type: "Polygon", coordinates: [[[1, 2], [1, 2], [1, 2], [1, 2]]]}]}), [0.799907, 1.600077], 1e-6);
    }

    @Test
    fun geocentroid_of_the_equator_is_ambiguous() {
        GeoCentroidStream().result(
            LineString(
                arrayOf(
                    pt(0.0, .0),
                    pt(120.0, .0),
                    pt(-120.0, .0),
                    pt(.0, .0)
                )
            )
        ) shouldBe doubleArrayOf(Double.NaN, Double.NaN)
    }

    @Test
    fun geocentroid_of_a_polygon_is_the_spherical_average_of_its_surface() {
        GeoCentroidStream().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(.0, -90.0),
                        pt(.0, .0),
                        pt(.0, 90.0),
                        pt(1.0, .0),
                        pt(.0, -90.0)
                    )
                )
            )
        ) shouldBeClose doubleArrayOf(0.5, .0)

        GeoCentroidStream().result(
            Polygon(
                arrayOf(
                    (-180..180).map { pt(it.toDouble(), -60.0) }.toTypedArray()
                )
            )
        )[1] shouldBeClose -90.0

        GeoCentroidStream().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(.0, -10.0),
                        pt(.0, 10.0),
                        pt(10.0, 10.0),
                        pt(10.0, -10.0),
                        pt(.0, -10.0)
                    )
                )
            )
        ) shouldBeClose doubleArrayOf(5.0, .0)
    }

    @Test
    fun geocentroid_of_a_spherical_square_touching_the_antimeridian() {
        GeoCentroidStream().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(-180.0, .0),
                        pt(-180.0, 10.0),
                        pt(-179.0, 10.0),
                        pt(-179.0, .0),
                        pt(-180.0, .0)
                    )
                )
            )
        ) shouldBeClose doubleArrayOf(-179.5, 4.987448)
    }

    @Test
    fun geocentroid_of_a_sphere_is_ambiguous() {
        GeoCentroidStream().result(Sphere()) shouldBe doubleArrayOf(Double.NaN, Double.NaN)
    }

    @Test
    fun geocentroid_of_a_small_circle_is_its_center_south_pole() {
        GeoCentroidStream().result(
            Polygon(
                arrayOf(
                    (-180..180).map { pt(it.toDouble(), -60.0) }.toTypedArray()
                )
            )
        )[1] shouldBeClose -90.0
    }

    @Test
    fun geocentroid_of_a_small_circle_is_its_center_equator() {
        GeoCentroidStream().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(.0, -10.0),
                        pt(.0, 10.0),
                        pt(10.0, 10.0),
                        pt(10.0, -10.0),
                        pt(.0, -10.0)
                    )
                )
            )
        ) shouldBeClose doubleArrayOf(5.0, .0)
    }

    @Test
    fun geocentroid_of_a_small_circle_is_its_center_equator_with_coincident_points() {
        GeoCentroidStream().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(.0, -10.0),
                        pt(.0, 10.0),
                        pt(.0, 10.0),
                        pt(10.0, 10.0),
                        pt(10.0, -10.0),
                        pt(.0, -10.0)
                    )
                )
            )
        ) shouldBeClose doubleArrayOf(5.0, .0)
    }

    @Test
    fun geocentroid_of_a_small_circle_is_its_center_other() {
        GeoCentroidStream().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(-180.0, .0),
                        pt(-180.0, 10.0),
                        pt(-179.0, 10.0),
                        pt(-179.0, .0),
                        pt(-180.0, .0)
                    )
                )
            )
        ) shouldBeClose doubleArrayOf(-179.5, 4.987448)
    }

    @Test
    fun geocentroid_of_a_feature_is_the_center_of_its_constituent_geometry() {
        GeoCentroidStream().result(
            Feature(
                LineString(
                    arrayOf(
                        pt(1.0, 1.0),
                        pt(1.0, 1.0)
                    )
                )
            )
        ) shouldBeClose doubleArrayOf(1.0, 1.0)

        GeoCentroidStream().result(
            Feature(
                Point(
                    pt(1.0, 1.0)
                )
            )
        ) shouldBeClose doubleArrayOf(1.0, 1.0)

        GeoCentroidStream().result(
            Feature(
                Polygon(
                    arrayOf(
                        arrayOf(
                            pt(.0, -90.0),
                            pt(.0, .0),
                            pt(.0, 90.0),
                            pt(1.0, .0),
                            pt(.0, -90.0)
                        )
                    )
                )
            )
        ) shouldBeClose doubleArrayOf(.5, .0)
    }

    @Test
    fun geocentroid_of_a_featureCollection_is_the_center_of_its_constituent_geometry() {
        GeoCentroidStream().result(
            FeatureCollection(
                arrayOf(
                    Feature(
                        LineString(
                            arrayOf(
                                pt(179.0, .0),
                                pt(180.0, .0)
                            )
                        )
                    ),
                    Feature(Point(pt(.0, .0)))
                )
            )
        ) shouldBeClose doubleArrayOf(179.5, .0)
    }

    @Test
    fun geocentroid_of_a_non_empty_linestring_and_a_point_only_considers_the_line_string() {
        GeoCentroidStream().result(
            GeometryCollection(
                arrayOf(
                    LineString(
                        arrayOf(
                            pt(179.0, .0),
                            pt(180.0, .0)
                        )
                    ),
                    Point(pt(.0, .0))
                )
            )
        ) shouldBeClose doubleArrayOf(179.5, .0)
    }

    @Test
    fun geocentroid_of_a_non_empty_polygon_a_non_empty_linestring_and_a_point_only_considers_the_polygon() {
        GeoCentroidStream().result(
            GeometryCollection(
                arrayOf(
                    Polygon(
                        arrayOf(
                            arrayOf(
                                pt(-180.0, .0),
                                pt(-180.0, 1.0),
                                pt(-179.0, 1.0),
                                pt(-179.0, .0),
                                pt(-180.0, .0)
                            )
                        )
                    ),
                    LineString(
                        arrayOf(
                            pt(179.0, .0),
                            pt(180.0, .0)
                        )
                    ),
                    Point(pt(.0, .0))
                )
            )
        ) shouldBeClose doubleArrayOf(-179.5, 0.500006)

        GeoCentroidStream().result(
            GeometryCollection(
                arrayOf(
                    Point(pt(.0, .0)),
                    LineString(
                        arrayOf(
                            pt(179.0, .0),
                            pt(180.0, .0)
                        )
                    ),
                    Polygon(
                        arrayOf(
                            arrayOf(
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
        ) shouldBeClose doubleArrayOf(-179.5, 0.500006)
    }

    @Test
    fun geocentroid_of_a_the_sphere_and_a_point_is_the_point() {
        GeoCentroidStream().result(
            FeatureCollection(
                arrayOf(
                    Feature(Sphere()),
                    Feature(Point(pt(1.0, 2.0)))
                )
            )
        ) shouldBeClose doubleArrayOf(1.0, 2.0)

        GeoCentroidStream().result(
            FeatureCollection(
                arrayOf(
                    Feature(Point(pt(2.0, 3.0))),
                    Feature(Sphere())
                )
            )
        ) shouldBeClose doubleArrayOf(2.0, 3.0)
    }

    /*
tape("the drawCentroid of a detailed feature is correct", function(test) {
  var ny = require("./data/ny.json");
  test.inDelta(d3.geoCentroid(ny), [-73.93079, 40.69447], 1e-5);
  test.end();
});

tape("the drawCentroid of a set of polygons is the (spherical) average of its surface", function(test) {
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

tape("the drawCentroid of a small circle is its center: 5°", function(test) {
  test.inDelta(d3.geoCentroid(d3.geoCircle().radius(5).center([30, 45])()), [30, 45], 1e-6);
  test.end();
});

tape("the drawCentroid of a small circle is its center: 135°", function(test) {
  test.inDelta(d3.geoCentroid(d3.geoCircle().radius(135).center([30, 45])()), [30, 45], 1e-6);
  test.end();
});

tape("the drawCentroid of a small circle is its center: concentric rings", function(test) {
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
