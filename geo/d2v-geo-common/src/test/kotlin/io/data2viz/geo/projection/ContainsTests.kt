package io.data2viz.geo.projection

import io.data2viz.geo.*
import io.data2viz.geo.path.geoPath
import io.data2viz.path.svgPath
import io.data2viz.test.TestBase
import kotlin.math.PI
import kotlin.test.Test

class ContainsTests : TestBase() {

    val equirectangular = io.data2viz.geo.projection.equirectangular() {
        scale = 900.0 / PI
        precision = .0
    }

    @Test
    fun a_sphere_contains_any_point_LEGACY() {
        contains(Sphere(), doubleArrayOf(.0, .0)) shouldBe true
        contains(Sphere(), doubleArrayOf(10000.0, .0)) shouldBe true
        contains(Sphere(), doubleArrayOf(10000.0, -964524.0)) shouldBe true
    }

    @Test
    fun a_point_contains_itself_and_not_some_other_point_LEGACY() {
        contains(Point(doubleArrayOf(.0, .0)), doubleArrayOf(.0, .0)) shouldBe true
        contains(Point(doubleArrayOf(1.0, 2.0)), doubleArrayOf(1.0, 2.0)) shouldBe true
        contains(Point(doubleArrayOf(.0, .0)), doubleArrayOf(.0, 1.0)) shouldBe false
        contains(Point(doubleArrayOf(1.0, 1.0)), doubleArrayOf(1.0, .0)) shouldBe false
    }

    @Test
    fun a_multipoint_contains_any_of_its_points_LEGACY() {
        contains(
            MultiPoint(listOf(doubleArrayOf(.0, .0), doubleArrayOf(1.0, 2.0))),
            doubleArrayOf(.0, .0)
        ) shouldBe true
        contains(
            MultiPoint(listOf(doubleArrayOf(.0, .0), doubleArrayOf(1.0, 2.0))),
            doubleArrayOf(1.0, 2.0)
        ) shouldBe true
        contains(
            MultiPoint(listOf(doubleArrayOf(.0, .0), doubleArrayOf(1.0, 2.0))),
            doubleArrayOf(1.0, 3.0)
        ) shouldBe false
    }

    @Test
    fun a_linestring_contains_any_point_on_the_great_circle_path_LEGACY() {
        contains(
            LineString(listOf(doubleArrayOf(.0, .0), doubleArrayOf(1.0, 2.0))),
            doubleArrayOf(.0, .0)
        ) shouldBe true
        contains(
            LineString(listOf(doubleArrayOf(.0, .0), doubleArrayOf(1.0, 2.0))),
            doubleArrayOf(1.0, 2.0)
        ) shouldBe true
        /*
        test.equal(d3.geoContains({type: "LineString", coordinates: [[0, 0], [1,2]]}, d3.geoInterpolate([0, 0], [1,2])(0.3)), true);
        test.equal(d3.geoContains({type: "LineString", coordinates: [[0, 0], [1,2]]}, d3.geoInterpolate([0, 0], [1,2])(1.3)), false);
        test.equal(d3.geoContains({type: "LineString", coordinates: [[0, 0], [1,2]]}, d3.geoInterpolate([0, 0], [1,2])(-0.3)), false);
         */
    }

    @Test
    fun a_multilinestring_contains_any__point_on_one_of_its_components_LEGACY() {
        contains(
            MultiLineString(
                listOf(
                    listOf(doubleArrayOf(.0, .0), doubleArrayOf(1.0, 2.0)),
                    listOf(doubleArrayOf(2.0, 3.0), doubleArrayOf(4.0, 5.0))
                )
            ), doubleArrayOf(2.0, 3.0)
        ) shouldBe true
        contains(
            MultiLineString(
                listOf(
                    listOf(doubleArrayOf(.0, .0), doubleArrayOf(1.0, 2.0)),
                    listOf(doubleArrayOf(2.0, 3.0), doubleArrayOf(4.0, 5.0))
                )
            ), doubleArrayOf(5.0, 6.0)
        ) shouldBe false
    }

    /*
tape("a Polygon contains a point", function(test) {
  var polygon = d3.geoCircle().radius(60)();
  test.equal(d3.geoContains(polygon, [1, 1]), true);
  test.equal(d3.geoContains(polygon, [-180, 0]), false);
  test.end();
});

tape("a Polygon with a hole doesn't contain a point", function(test) {
  var outer = d3.geoCircle().radius(60)().coordinates[0],
      inner = d3.geoCircle().radius(3)().coordinates[0],
      polygon = {type:"Polygon", coordinates: [outer, inner]};
  test.equal(d3.geoContains(polygon, [1, 1]), false);
  test.equal(d3.geoContains(polygon, [5, 0]), true);
  test.equal(d3.geoContains(polygon, [65, 0]), false);
  test.end();
});

tape("a MultiPolygon contains a point", function(test) {
  var p1 = d3.geoCircle().radius(6)().coordinates,
      p2 = d3.geoCircle().radius(6).center([90,0])().coordinates,
      polygon = {type:"MultiPolygon", coordinates: [p1, p2]};
  test.equal(d3.geoContains(polygon, [1, 0]), true);
  test.equal(d3.geoContains(polygon, [90, 1]), true);
  test.equal(d3.geoContains(polygon, [90, 45]), false);
  test.end();
});

tape("a GeometryCollection contains a point", function(test) {
  var collection = {
    type: "GeometryCollection", geometries: [
      {type: "GeometryCollection", geometries: [{type: "LineString", coordinates: [[-45, 0], [0, 0]]}]},
      {type: "LineString", coordinates: [[0, 0], [45, 0]]}
    ]
  };
  test.equal(d3.geoContains(collection, [-45, 0]), true);
  test.equal(d3.geoContains(collection, [45, 0]), true);
  test.equal(d3.geoContains(collection, [12, 25]), false);
  test.end();
});

tape("a Feature contains a point", function(test) {
  var feature = {
    type: "Feature", geometry: {
      type: "LineString", coordinates: [[0, 0], [45, 0]]
    }
  };
  test.equal(d3.geoContains(feature, [45, 0]), true);
  test.equal(d3.geoContains(feature, [12, 25]), false);
  test.end();
});

tape("a FeatureCollection contains a point", function(test) {
  var feature1 = {
    type: "Feature", geometry: {
      type: "LineString", coordinates: [[0, 0], [45, 0]]
    }
  },
  feature2 = {
    type: "Feature", geometry: {
      type: "LineString", coordinates: [[-45, 0], [0, 0]]
    }
  },
  featureCollection = {
    type: "FeatureCollection",
    features: [ feature1, feature2 ]
  };
  test.equal(d3.geoContains(featureCollection, [45, 0]), true);
  test.equal(d3.geoContains(featureCollection, [-45, 0]), true);
  test.equal(d3.geoContains(featureCollection, [12, 25]), false);
  test.end();
});

tape("null contains nothing", function(test) {
  test.equal(d3.geoContains(null, [0, 0]), false);
  test.end();
});
     */
}