package io.data2viz.geo.projection

import io.data2viz.geo.*
import io.data2viz.geo.path.geoPath
import io.data2viz.path.svgPath
import io.data2viz.test.TestBase
import kotlin.math.PI
import kotlin.test.Test

class GraticuleTests : TestBase() {

    @Test
    fun graticule_extent_sets_extendMinor_and_extentMajor_LEGACY() {
        val graticule = graticule()
        graticule.extent = arrayOf(doubleArrayOf(90.0, -45.0), doubleArrayOf(90.0, 45.0))

        graticule.extentMajor[0] shouldBe doubleArrayOf(90.0, -45.0)
        graticule.extentMajor[1] shouldBe doubleArrayOf(90.0, 45.0)
        graticule.extentMinor[0] shouldBe doubleArrayOf(90.0, -45.0)
        graticule.extentMinor[1] shouldBe doubleArrayOf(90.0, 45.0)
    }

    @Test
    fun graticule_extent_gets_extendMinor_LEGACY() {
        val graticule = graticule()
        graticule.extentMinor = arrayOf(doubleArrayOf(-90.0, -45.0), doubleArrayOf(90.0, 45.0))

        graticule.extent[0] shouldBe doubleArrayOf(-90.0, -45.0)
        graticule.extent[1] shouldBe doubleArrayOf(90.0, 45.0)
    }

    @Test
    fun graticule_extentMajor_default_longitude_ranges_from_180W_inclusive_to_180E_exclusive_LEGACY() {
        val graticule = graticule().extentMajor

        graticule[0][0] shouldBe -180.0
        graticule[1][0] shouldBe 180.0
    }

    @Test
    fun graticule_extentMajor_default_latitude_ranges_from_90S_exclusive_to_90N_exclusive_LEGACY() {
        val graticule = graticule().extentMajor

        graticule[0][1] shouldBeClose  -90.0
        graticule[1][1] shouldBeClose 90.0
    }

    @Test
    fun graticule_extentMinor_default_longitude_ranges_from_180W_inclusive_to_180E_exclusive_LEGACY() {
        val graticule = graticule().extentMinor

        graticule[0][0] shouldBe -180.0
        graticule[1][0] shouldBe 180.0
    }

    @Test
    fun graticule_extentMinor_default_latitude_ranges_from_80S_exclusive_to_80N_inclusive_LEGACY() {
        val graticule = graticule().extentMinor

        graticule[0][1] shouldBeClose  -80.0
        graticule[1][1] shouldBeClose 80.0
    }


    /*
tape("graticule.step(…) sets the minor and major step", function(test) {
  var g = d3.geoGraticule().step([22.5, 22.5]);
  test.deepEqual(g.stepMinor(), [22.5, 22.5]);
  test.deepEqual(g.stepMajor(), [22.5, 22.5]);
  test.end();
});

tape("graticule.step() gets the minor step", function(test) {
  var g = d3.geoGraticule().stepMinor([22.5, 22.5]);
  test.deepEqual(g.step(), [22.5, 22.5]);
  test.end();
});

tape("graticule.stepMinor() defaults to 10°, 10°", function(test) {
  test.deepEqual(d3.geoGraticule().stepMinor(), [10, 10]);
  test.end();
});

tape("graticule.stepMinor(…) coerces input values to numbers", function(test) {
  var g = d3.geoGraticule().stepMinor(["45", "11.25"]),
      step = g.stepMinor();
  test.strictEqual(step[0], 45);
  test.strictEqual(step[1], 11.25);
  test.end();
});

tape("graticule.stepMajor() defaults to 90°, 360°", function(test) {
  test.deepEqual(d3.geoGraticule().stepMajor(), [90, 360]);
  test.end();
});

tape("graticule.stepMajor(…) coerces input values to numbers", function(test) {
  var g = d3.geoGraticule().stepMajor(["45", "11.25"]),
      step = g.stepMajor();
  test.strictEqual(step[0], 45);
  test.strictEqual(step[1], 11.25);
  test.end();
});

tape("graticule.lines() default longitude ranges from 180°W (inclusive) to 180°E (exclusive)", function(test) {
  var lines = d3.geoGraticule().lines()
      .filter(function(line) { return line.coordinates[0][0] === line.coordinates[1][0]; })
      .sort(function(a, b) { return a.coordinates[0][0] - b.coordinates[0][0]; });
  test.equal(lines[0].coordinates[0][0], -180);
  test.equal(lines[lines.length - 1].coordinates[0][0], +170);
  test.end();
});

tape("graticule.lines() default latitude ranges from 90°S (exclusive) to 90°N (exclusive)", function(test) {
  var lines = d3.geoGraticule().lines()
      .filter(function(line) { return line.coordinates[0][1] === line.coordinates[1][1]; })
      .sort(function(a, b) { return a.coordinates[0][1] - b.coordinates[0][1]; });
  test.equal(lines[0].coordinates[0][1], -80);
  test.equal(lines[lines.length - 1].coordinates[0][1], +80);
  test.end();
});

tape("graticule.lines() default minor longitude lines extend from 80°S to 80°N", function(test) {
  var lines = d3.geoGraticule().lines()
      .filter(function(line) { return line.coordinates[0][0] === line.coordinates[1][0]; })
      .filter(function(line) { return Math.abs(line.coordinates[0][0] % 90) > 1e-6; });
  lines.forEach(function(line) {
    test.deepEqual(array.extent(line.coordinates, function(p) { return p[1]; }), [-80 - 1e-6, +80 + 1e-6]);
  });
  test.end();
});

tape("graticule.lines() default major longitude lines extend from 90°S to 90°N", function(test) {
  var lines = d3.geoGraticule().lines()
      .filter(function(line) { return line.coordinates[0][0] === line.coordinates[1][0]; })
      .filter(function(line) { return Math.abs(line.coordinates[0][0] % 90) < 1e-6; });
  lines.forEach(function(line) {
    test.deepEqual(array.extent(line.coordinates, function(p) { return p[1]; }), [-90 + 1e-6, +90 - 1e-6]);
  });
  test.end();
});

tape("graticule.lines() default latitude lines extend from 180°W to 180°E", function(test) {
  var lines = d3.geoGraticule().lines()
      .filter(function(line) { return line.coordinates[0][1] === line.coordinates[1][1]; });
  lines.forEach(function(line) {
    test.deepEqual(array.extent(line.coordinates, function(p) { return p[0]; }), [-180, +180]);
  });
  test.end();
});

tape("graticule.lines() returns an array of LineStrings", function(test) {
  test.deepEqual(d3.geoGraticule()
      .extent([[-90, -45], [90, 45]])
      .step([45, 45])
      .precision(3)
      .lines(), [
    {type: "LineString", coordinates: [[-90,-45],[-90,45]]}, // meridian
    {type: "LineString", coordinates: [[-45,-45],[-45,45]]}, // meridian
    {type: "LineString", coordinates: [[0,-45],[0,45]]}, // meridian
    {type: "LineString", coordinates: [[45,-45],[45,45]]}, // meridian
    {type: "LineString", coordinates: [[-90,-45],[-87,-45],[-84,-45],[-81,-45],[-78,-45],[-75,-45],[-72,-45],[-69,-45],[-66,-45],[-63,-45],[-60,-45],[-57,-45],[-54,-45],[-51,-45],[-48,-45],[-45,-45],[-42,-45],[-39,-45],[-36,-45],[-33,-45],[-30,-45],[-27,-45],[-24,-45],[-21,-45],[-18,-45],[-15,-45],[-12,-45],[-9,-45],[-6,-45],[-3,-45],[0,-45],[3,-45],[6,-45],[9,-45],[12,-45],[15,-45],[18,-45],[21,-45],[24,-45],[27,-45],[30,-45],[33,-45],[36,-45],[39,-45],[42,-45],[45,-45],[48,-45],[51,-45],[54,-45],[57,-45],[60,-45],[63,-45],[66,-45],[69,-45],[72,-45],[75,-45],[78,-45],[81,-45],[84,-45],[87,-45],[90,-45]]},
    {type: "LineString", coordinates: [[-90,0],[-87,0],[-84,0],[-81,0],[-78,0],[-75,0],[-72,0],[-69,0],[-66,0],[-63,0],[-60,0],[-57,0],[-54,0],[-51,0],[-48,0],[-45,0],[-42,0],[-39,0],[-36,0],[-33,0],[-30,0],[-27,0],[-24,0],[-21,0],[-18,0],[-15,0],[-12,0],[-9,0],[-6,0],[-3,0],[0,0],[3,0],[6,0],[9,0],[12,0],[15,0],[18,0],[21,0],[24,0],[27,0],[30,0],[33,0],[36,0],[39,0],[42,0],[45,0],[48,0],[51,0],[54,0],[57,0],[60,0],[63,0],[66,0],[69,0],[72,0],[75,0],[78,0],[81,0],[84,0],[87,0],[90,0]]}
  ]);
  test.end();
});

tape("graticule() returns a MultiLineString of all lines", function(test) {
  var g = d3.geoGraticule()
      .extent([[-90, -45], [90, 45]])
      .step([45, 45])
      .precision(3);
  test.deepEqual(g(), {
    type: "MultiLineString",
    coordinates: g.lines().map(function(line) { return line.coordinates; })
  });
  test.end();
});

tape("graticule.outline() returns a Polygon encompassing the major extent", function(test) {
  test.deepEqual(d3.geoGraticule()
      .extentMajor([[-90, -45], [90, 45]])
      .precision(3)
      .outline(), {
    type: "Polygon",
    coordinates: [[
      [-90,-45],[-90,45], // meridian
      [-87,45],[-84,45],[-81,45],[-78,45],[-75,45],[-72,45],[-69,45],[-66,45],[-63,45],[-60,45],[-57,45],[-54,45],[-51,45],[-48,45],[-45,45],[-42,45],[-39,45],[-36,45],[-33,45],[-30,45],[-27,45],[-24,45],[-21,45],[-18,45],[-15,45],[-12,45],[-9,45],[-6,45],[-3,45],[0,45],[3,45],[6,45],[9,45],[12,45],[15,45],[18,45],[21,45],[24,45],[27,45],[30,45],[33,45],[36,45],[39,45],[42,45],[45,45],[48,45],[51,45],[54,45],[57,45],[60,45],[63,45],[66,45],[69,45],[72,45],[75,45],[78,45],[81,45],[84,45],[87,45],
      [90,45],[90,-45], // meridian
      [87,-45],[84,-45],[81,-45],[78,-45],[75,-45],[72,-45],[69,-45],[66,-45],[63,-45],[60,-45],[57,-45],[54,-45],[51,-45],[48,-45],[45,-45],[42,-45],[39,-45],[36,-45],[33,-45],[30,-45],[27,-45],[24,-45],[21,-45],[18,-45],[15,-45],[12,-45],[9,-45],[6,-45],[3,-45],[0,-45],[-3,-45],[-6,-45],[-9,-45],[-12,-45],[-15,-45],[-18,-45],[-21,-45],[-24,-45],[-27,-45],[-30,-45],[-33,-45],[-36,-45],[-39,-45],[-42,-45],[-45,-45],[-48,-45],[-51,-45],[-54,-45],[-57,-45],[-60,-45],[-63,-45],[-66,-45],[-69,-45],[-72,-45],[-75,-45],[-78,-45],[-81,-45],[-84,-45],[-87,-45],[-90,-45]
    ]]
  });
  test.end();
});
     */
}