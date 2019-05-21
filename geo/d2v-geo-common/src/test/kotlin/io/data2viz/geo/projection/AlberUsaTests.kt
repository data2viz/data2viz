package io.data2viz.geo.projection

//import io.data2viz.test.TestBase
//import kotlin.test.Test

// TODO: Check

//class AlberUsaTests : TestBase() {
//
//    val util = ProjectionTests()

//    @Test
//    fun point_invert_returns_the_expected_result() {
//        val albersUsa = alberUSAProjection()

//        util.checkProjection(albersUsa, -122.4194, 37.7749, doubleArrayOf(107.4, 214.1)); // San Francisco, CA
//        util.checkProjection(albersUsa, -74.0059, 40.7128, doubleArrayOf(794.6, 176.5)); // New York, NY
//        util.checkProjection(albersUsa, -95.9928, 36.1540, doubleArrayOf(488.8, 298.0)); // Tulsa, OK
//        util.checkProjection(albersUsa, -149.9003, 61.2181, doubleArrayOf(171.2, 446.9)); // Anchorage, AK
//        util.checkProjection(albersUsa, -157.8583, 21.3069, doubleArrayOf(298.5, 451.0)); // Honolulu, HI
//        test.equal(albersUsa([2.3522, 48.8566]), null); // Paris, France
//
//        util.checkProjection(projection, 84.0, 59.0, doubleArrayOf(3.06246363239589, -1.2949057330916178))
//        util.checkProjection(projection, -22.0, 16.0, doubleArrayOf(-0.2695850649725115, -0.3712480206334109))
////        util.checkProjection(projection, 800.0, -800.0, doubleArrayOf(1.3520358022147179, 0.8616854414915359))
//    }
//}

//
//var tape = require("tape"),
//d3 = require("../../");
//
//require("./projectionEqual");
//
//tape("albersUsa(point) and albersUsa.invert(point) returns the expected result", function(test) {
//    var albersUsa = d3.geoAlbersUsa();
//    test.projectionEqual(albersUsa, [-122.4194, 37.7749], [107.4, 214.1], 0.1); // San Francisco, CA
//    test.projectionEqual(albersUsa, [ -74.0059, 40.7128], [794.6, 176.5], 0.1); // New York, NY
//    test.projectionEqual(albersUsa, [ -95.9928, 36.1540], [488.8, 298.0], 0.1); // Tulsa, OK
//    test.projectionEqual(albersUsa, [-149.9003, 61.2181], [171.2, 446.9], 0.1); // Anchorage, AK
//    test.projectionEqual(albersUsa, [-157.8583, 21.3069], [298.5, 451.0], 0.1); // Honolulu, HI
//    test.equal(albersUsa([2.3522, 48.8566]), null); // Paris, France
//    test.end();
//});