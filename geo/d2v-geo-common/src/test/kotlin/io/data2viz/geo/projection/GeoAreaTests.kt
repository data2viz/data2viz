package io.data2viz.geo.projection

import io.data2viz.geo.*
import io.data2viz.geo.path.geoPath
import io.data2viz.path.svgPath
import io.data2viz.test.TestBase
import kotlin.math.PI
import kotlin.test.Test

class GeoAreaTests : TestBase() {

    @Test
    fun geoarea_of_a_point_LEGACY() {
        GeoArea().result(Point(doubleArrayOf(.0, .0))) shouldBe .0
    }

    @Test
    fun geoarea_of_a_multipoint_LEGACY() {
        GeoArea().result(MultiPoint(listOf(doubleArrayOf(.0, 1.0), doubleArrayOf(2.0, 3.0)))) shouldBe .0
    }

    @Test
    fun geoarea_of_a_tiny_polygon_LEGACY() {
        GeoArea().result(
            Polygon(
                listOf(
                    listOf(
                        doubleArrayOf(-64.66070178517852, 18.33986913231323),
                        doubleArrayOf(-64.66079715091509, 18.33994007490749),
                        doubleArrayOf(-64.66074946804680, 18.33994007490749),
                        doubleArrayOf(-64.66070178517852, 18.33986913231323)
                    )
                )
            )
        ) shouldBeClose 4.890516e-13
    }


    // TODO : pass in JVM not JS !!
    /*@Test
    fun geoarea_of_a_zero_area_polygon_LEGACY() {
        GeoArea().result(
            Polygon(
                listOf(
                    listOf(
                        doubleArrayOf(96.79142432523281, 5.262704519048153),
                        doubleArrayOf(96.81065389253769, 5.272455576551362),
                        doubleArrayOf(96.82988345984256, 5.272455576551362),
                        doubleArrayOf(96.81065389253769, 5.272455576551362),
                        doubleArrayOf(96.79142432523281, 5.262704519048153)
                    )
                )
            )
        ) shouldBe .0
    }*/

    @Test
    fun geoarea_of_a_semilune_polygon_LEGACY() {
        GeoArea().result(
            Polygon(
                listOf(
                    listOf(
                        doubleArrayOf(.0, .0),
                        doubleArrayOf(.0, 90.0),
                        doubleArrayOf(90.0, .0),
                        doubleArrayOf(.0, .0)
                    )
                )
            )
        ) shouldBeClose PI / 2
    }

    @Test
    fun geoarea_of_a_lune_polygon_LEGACY() {
        GeoArea().result(
            Polygon(
                listOf(
                    listOf(
                        doubleArrayOf(.0, .0),
                        doubleArrayOf(.0, 90.0),
                        doubleArrayOf(90.0, .0),
                        doubleArrayOf(.0, -90.0),
                        doubleArrayOf(.0, .0)
                    )
                )
            )
        ) shouldBeClose PI
    }

    @Test
    fun geoarea_of_hemisphere_north_LEGACY() {
        GeoArea().result(
            Polygon(
                listOf(
                    listOf(
                        doubleArrayOf(.0, .0),
                        doubleArrayOf(-90.0, .0),
                        doubleArrayOf(180.0, .0),
                        doubleArrayOf(90.0, .0),
                        doubleArrayOf(.0, .0)
                    )
                )
            )
        ) shouldBeClose PI * 2
    }

    @Test
    fun geoarea_of_hemisphere_south_LEGACY() {
        GeoArea().result(
            Polygon(
                listOf(
                    listOf(
                        doubleArrayOf(.0, .0),
                        doubleArrayOf(90.0, .0),
                        doubleArrayOf(180.0, .0),
                        doubleArrayOf(-90.0, .0),
                        doubleArrayOf(.0, .0)
                    )
                )
            )
        ) shouldBeClose PI * 2
    }

    @Test
    fun geoarea_of_hemisphere_east_LEGACY() {
        GeoArea().result(
            Polygon(
                listOf(
                    listOf(
                        doubleArrayOf(.0, .0),
                        doubleArrayOf(.0, 90.0),
                        doubleArrayOf(180.0, .0),
                        doubleArrayOf(.0, -90.0),
                        doubleArrayOf(.0, .0)
                    )
                )
            )
        ) shouldBeClose PI * 2
    }

    @Test
    fun geoarea_of_hemisphere_west_LEGACY() {
        GeoArea().result(
            Polygon(
                listOf(
                    listOf(
                        doubleArrayOf(.0, .0),
                        doubleArrayOf(.0, -90.0),
                        doubleArrayOf(180.0, .0),
                        doubleArrayOf(.0, 90.0),
                        doubleArrayOf(.0, .0)
                    )
                )
            )
        ) shouldBeClose PI * 2
    }

    /*
var tape = require("tape"),
    array = require("d3-array"),
    d3 = require("../");

require("./inDelta");

function stripes(a, b) {
  return {type: "Polygon", coordinates: [a, b].map(function(d, i) {
    var stripe = array.range(-180, 180, 0.1).map(function(x) { return [x, d]; });
    stripe.push(stripe[0]);
    return i ? stripe.reverse() : stripe;
  })};
}

tape("area: LineString", function(test) {
  test.equal(d3.geoArea({type: "LineString", coordinates: [[0, 1], [2, 3]]}), 0);
  test.end();
});

tape("area: MultiLineString", function(test) {
  test.equal(d3.geoArea({type: "MultiLineString", coordinates: [[[0, 1], [2, 3]], [[4, 5], [6, 7]]]}), 0);
  test.end();

tape("area: Polygon - graticule outline sphere", function(test) {
  test.inDelta(d3.geoArea(d3.geoGraticule().extent([[-180, -90], [180, 90]]).outline()), 4 * Math.PI, 1e-5);
  test.end();
});

tape("area: Polygon - graticule outline hemisphere", function(test) {
  test.inDelta(d3.geoArea(d3.geoGraticule().extent([[-180, 0], [180, 90]]).outline()), 2 * Math.PI, 1e-5);
  test.end();
});

tape("area: Polygon - graticule outline semilune", function(test) {
  test.inDelta(d3.geoArea(d3.geoGraticule().extent([[0, 0], [90, 90]]).outline()), Math.PI / 2, 1e-5);
  test.end();
});

tape("area: Polygon - circles hemisphere", function(test) {
  test.inDelta(d3.geoArea(d3.geoCircle().radius(90)()), 2 * Math.PI, 1e-5);
  test.end();
});

tape("area: Polygon - circles 60°", function(test) {
  test.inDelta(d3.geoArea(d3.geoCircle().radius(60).precision(0.1)()), Math.PI, 1e-5);
  test.end();
});

tape("area: Polygon - circles 60° North", function(test) {
  test.inDelta(d3.geoArea(d3.geoCircle().radius(60).precision(0.1).center([0, 90])()), Math.PI, 1e-5);
  test.end();
});

tape("area: Polygon - circles 45°", function(test) {
  test.inDelta(d3.geoArea(d3.geoCircle().radius(45).precision(0.1)()), Math.PI * (2 - Math.SQRT2), 1e-5);
  test.end();
});

tape("area: Polygon - circles 45° North", function(test) {
  test.inDelta(d3.geoArea(d3.geoCircle().radius(45).precision(0.1).center([0, 90])()), Math.PI * (2 - Math.SQRT2), 1e-5);
  test.end();
});

tape("area: Polygon - circles 45° South", function(test) {
  test.inDelta(d3.geoArea(d3.geoCircle().radius(45).precision(0.1).center([0, -90])()), Math.PI * (2 - Math.SQRT2), 1e-5);
  test.end();
});

tape("area: Polygon - circles 135°", function(test) {
  test.inDelta(d3.geoArea(d3.geoCircle().radius(135).precision(0.1)()), Math.PI * (2 + Math.SQRT2), 1e-5);
  test.end();
});

tape("area: Polygon - circles 135° North", function(test) {
  test.inDelta(d3.geoArea(d3.geoCircle().radius(135).precision(0.1).center([0, 90])()), Math.PI * (2 + Math.SQRT2), 1e-5);
  test.end();
});

tape("area: Polygon - circles 135° South", function(test) {
  test.inDelta(d3.geoArea(d3.geoCircle().radius(135).precision(0.1).center([0, -90])()), Math.PI * (2 + Math.SQRT2), 1e-5);
  test.end();
});

tape("area: Polygon - circles tiny", function(test) {
  test.inDelta(d3.geoArea(d3.geoCircle().radius(1e-6).precision(0.1)()), 0, 1e-6);
  test.end();
});

tape("area: Polygon - circles huge", function(test) {
  test.inDelta(d3.geoArea(d3.geoCircle().radius(180 - 1e-6).precision(0.1)()), 4 * Math.PI, 1e-6);
  test.end();
});

tape("area: Polygon - circles 60° with 45° hole", function(test) {
  var circle = d3.geoCircle().precision(0.1);
  test.inDelta(d3.geoArea({
    type: "Polygon",
    coordinates: [
      circle.radius(60)().coordinates[0],
      circle.radius(45)().coordinates[0].reverse()
    ]
  }), Math.PI * (Math.SQRT2 - 1), 1e-5);
  test.end();
});

tape("area: Polygon - circles 45° holes at [0°, 0°] and [0°, 90°]", function(test) {
  var circle = d3.geoCircle().precision(0.1).radius(45);
  test.inDelta(d3.geoArea({
    type: "Polygon",
    coordinates: [
      circle.center([0, 0])().coordinates[0].reverse(),
      circle.center([0, 90])().coordinates[0].reverse()
    ]
  }), Math.PI * 2 * Math.SQRT2, 1e-5);
  test.end();
});

tape("area: Polygon - circles 45° holes at [0°, 90°] and [0°, 0°]", function(test) {
  var circle = d3.geoCircle().precision(0.1).radius(45);
  test.inDelta(d3.geoArea({
    type: "Polygon",
    coordinates: [
      circle.center([0, 90])().coordinates[0].reverse(),
      circle.center([0, 0])().coordinates[0].reverse()
    ]
  }), Math.PI * 2 * Math.SQRT2, 1e-5);
  test.end();
});

tape("area: Polygon - stripes 45°, -45°", function(test) {
  test.inDelta(d3.geoArea(stripes(45, -45)), Math.PI * 2 * Math.SQRT2, 1e-5);
  test.end();
});

tape("area: Polygon - stripes -45°, 45°", function(test) {
  test.inDelta(d3.geoArea(stripes(-45, 45)), Math.PI * 2 * (2 - Math.SQRT2), 1e-5);
  test.end();
});

tape("area: Polygon - stripes 45°, 30°", function(test) {
  test.inDelta(d3.geoArea(stripes(45, 30)), Math.PI * (Math.SQRT2 - 1), 1e-5);
  test.end();
});

tape("area: MultiPolygon two hemispheres", function(test) {
  test.equal(d3.geoArea({type: "MultiPolygon", coordinates: [
    [[[0, 0], [-90, 0], [180, 0], [90, 0], [0, 0]]],
    [[[0, 0], [90, 0], [180, 0], [-90, 0], [0, 0]]]
  ]}), 4 * Math.PI);
  test.end();
});

tape("area: Sphere", function(test) {
  test.equal(d3.geoArea({type: "Sphere"}), 4 * Math.PI);
  test.end();
});

tape("area: GeometryCollection", function(test) {
  test.equal(d3.geoArea({type: "GeometryCollection", geometries: [{type: "Sphere"}]}), 4 * Math.PI);
  test.end();
});

tape("area: FeatureCollection", function(test) {
  test.equal(d3.geoArea({type: "FeatureCollection", features: [{type: "Feature", geometry: {type: "Sphere"}}]}), 4 * Math.PI);
  test.end();
});

tape("area: Feature", function(test) {
  test.equal(d3.geoArea({type: "Feature", geometry: {type: "Sphere"}}), 4 * Math.PI);
  test.end();
});
     */
}