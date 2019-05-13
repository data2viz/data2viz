package io.data2viz.geo.projection

//var tape = require("tape"),
//d3 = require("../../");
//
//require("../inDelta");
//require("./projectionEqual");
//
//tape("projection.angle(…) defaults to zero", function(test) {
//    var projection = d3.geoGnomonic().scale(1).translate([0, 0]);
//    test.equal(projection.angle(), 0);
//    test.projectionEqual(projection, [0, 0], [0, 0]);
//    test.projectionEqual(projection, [10, 0], [0.17632698070846498, 0]);
//    test.projectionEqual(projection, [-10, 0], [-0.17632698070846498, 0]);
//    test.projectionEqual(projection, [0, 10], [0, -0.17632698070846498]);
//    test.projectionEqual(projection, [0, -10], [0, 0.17632698070846498]);
//    test.projectionEqual(projection, [10, 10], [0.17632698070846495, -0.17904710860483972]);
//    test.projectionEqual(projection, [10, -10], [0.17632698070846495, 0.17904710860483972]);
//    test.projectionEqual(projection, [-10, 10], [-0.17632698070846495, -0.17904710860483972]);
//    test.projectionEqual(projection, [-10, -10], [-0.17632698070846495, 0.17904710860483972]);
//    test.end();
//});
//
//tape("projection.angle(…) rotates by the specified degrees after projecting", function(test) {
//    var projection = d3.geoGnomonic().scale(1).translate([0, 0]).angle(30);
//    test.inDelta(projection.angle(), 30);
//    test.projectionEqual(projection, [0, 0], [0, 0]);
//    test.projectionEqual(projection, [10, 0], [0.1527036446661393, -0.08816349035423247]);
//    test.projectionEqual(projection, [-10, 0], [-0.1527036446661393, 0.08816349035423247]);
//    test.projectionEqual(projection, [0, 10], [-0.08816349035423247, -0.1527036446661393]);
//    test.projectionEqual(projection, [0, -10], [0.08816349035423247, 0.1527036446661393]);
//    test.projectionEqual(projection, [10, 10], [0.06318009036371944, -0.24322283488017502]);
//    test.projectionEqual(projection, [10, -10], [0.24222719896855913, 0.0668958541717101]);
//    test.projectionEqual(projection, [-10, 10], [-0.24222719896855913, -0.0668958541717101]);
//    test.projectionEqual(projection, [-10, -10], [-0.06318009036371944, 0.24322283488017502]);
//    test.end();
//});
//
//tape("projection.angle(…) rotates by the specified degrees after projecting", function(test) {
//    var projection = d3.geoGnomonic().scale(1).translate([0, 0]).angle(-30);
//    test.inDelta(projection.angle(), -30);
//    test.projectionEqual(projection, [0, 0], [0, 0]);
//    test.projectionEqual(projection, [10, 0], [0.1527036446661393, 0.08816349035423247]);
//    test.projectionEqual(projection, [-10, 0], [-0.1527036446661393, -0.08816349035423247]);
//    test.projectionEqual(projection, [0, 10], [0.08816349035423247, -0.1527036446661393]);
//    test.projectionEqual(projection, [0, -10], [-0.08816349035423247, 0.1527036446661393]);
//    test.projectionEqual(projection, [10, 10], [0.24222719896855913, -0.0668958541717101]);
//    test.projectionEqual(projection, [10, -10], [0.06318009036371944, 0.24322283488017502]);
//    test.projectionEqual(projection, [-10, 10], [-0.06318009036371944, -0.24322283488017502]);
//    test.projectionEqual(projection, [-10, -10], [-0.24222719896855913, 0.0668958541717101]);
//    test.end();
//});
//
//tape("projection.angle(…) wraps around 360°", function(test) {
//    var projection = d3.geoGnomonic().scale(1).translate([0, 0]).angle(360);
//    test.equal(projection.angle(), 0);
//    test.end();
//});