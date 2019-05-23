package io.data2viz.geo.geometry.path

// TODO: Check
//var tape = require("tape"),
//d3_geo = require("../../"),
//testContext = require("./test-context");
//
//var equirectangular = d3_geo.geoEquirectangular()
//    .scale(900 / Math.PI)
//    .precision(0);
//
//function testPath(projection, object) {
//    var context = testContext();
//
//    d3_geo.geoPath()
//    .projection(projection)
//    .context(context)
//    (object);
//
//    return context.result();
//}
//
//tape("geoPath.projection() defaults to null", function(test) {
//    var drawPath = d3_geo.geoPath();
//    test.strictEqual(drawPath.projection(), null);
//    test.end();
//});
//
//tape("geoPath.context() defaults to null", function(test) {
//    var drawPath = d3_geo.geoPath();
//    test.strictEqual(drawPath.context(), null);
//    test.end();
//});
//
//tape("d3.geoPath(projection) sets the initial projection", function(test) {
//    var projection = d3_geo.geoAlbers(), drawPath = d3_geo.geoPath(projection);
//    test.strictEqual(drawPath.projection(), projection);
//    test.end();
//});
//
//tape("d3.geoPath(projection, context) sets the initial projection and context", function(test) {
//    var context = testContext(), projection = d3_geo.geoAlbers(), drawPath = d3_geo.geoPath(projection, context);
//    test.strictEqual(drawPath.projection(), projection);
//    test.strictEqual(drawPath.context(), context);
//    test.end();
//});
//
//tape("geoPath(Point) renders a point", function(test) {
//    test.deepEqual(testPath(equirectangular, {
//        type: "Point",
//        coordinates: [-63, 18]
//    }), [
//        {type: "moveTo", translateX: 170, translateY: 160},
//        {type: "arc", translateX: 165, translateY: 160, r: 4.5}
//    ]);
//    test.end();
//});
//
//tape("geoPath(MultiPoint) renders a point", function(test) {
//    test.deepEqual(testPath(equirectangular, {
//        type: "MultiPoint",
//        coordinates: [[-63, 18], [-62, 18], [-62, 17]]
//    }), [
//        {type: "moveTo", translateX: 170, translateY: 160}, {type: "arc", translateX: 165, translateY: 160, r: 4.5},
//        {type: "moveTo", translateX: 175, translateY: 160}, {type: "arc", translateX: 170, translateY: 160, r: 4.5},
//        {type: "moveTo", translateX: 175, translateY: 165}, {type: "arc", translateX: 170, translateY: 165, r: 4.5}
//    ]);
//    test.end();
//});
//
//tape("geoPath(LineString) renders a line string", function(test) {
//    test.deepEqual(testPath(equirectangular, {
//        type: "LineString",
//        coordinates: [[-63, 18], [-62, 18], [-62, 17]]
//    }), [
//        {type: "moveTo", translateX: 165, translateY: 160},
//        {type: "lineTo", translateX: 170, translateY: 160},
//        {type: "lineTo", translateX: 170, translateY: 165}
//    ]);
//    test.end();
//});
//
//tape("geoPath(Polygon) renders a polygon", function(test) {
//    test.deepEqual(testPath(equirectangular, {
//        type: "Polygon",
//        coordinates: [[[-63, 18], [-62, 18], [-62, 17], [-63, 18]]]
//    }), [
//        {type: "moveTo", translateX: 165, translateY: 160},
//        {type: "lineTo", translateX: 170, translateY: 160},
//        {type: "lineTo", translateX: 170, translateY: 165},
//        {type: "closePath"}
//    ]);
//    test.end();
//});
//
//tape("geoPath(GeometryCollection) renders a geometry collection", function(test) {
//    test.deepEqual(testPath(equirectangular, {
//        type: "GeometryCollection",
//        geometries: [{
//        type: "Polygon",
//        coordinates: [[[-63, 18], [-62, 18], [-62, 17], [-63, 18]]]
//    }]
//    }), [
//        {type: "moveTo", translateX: 165, translateY: 160},
//        {type: "lineTo", translateX: 170, translateY: 160},
//        {type: "lineTo", translateX: 170, translateY: 165},
//        {type: "closePath"}
//    ]);
//    test.end();
//});
//
//tape("geoPath(Feature) renders a feature", function(test) {
//    test.deepEqual(testPath(equirectangular, {
//        type: "Feature",
//        geometry: {
//        type: "Polygon",
//        coordinates: [[[-63, 18], [-62, 18], [-62, 17], [-63, 18]]]
//    }
//    }), [
//        {type: "moveTo", translateX: 165, translateY: 160},
//        {type: "lineTo", translateX: 170, translateY: 160},
//        {type: "lineTo", translateX: 170, translateY: 165},
//        {type: "closePath"}
//    ]);
//    test.end();
//});
//
//tape("geoPath(FeatureCollection) renders a feature collection", function(test) {
//    test.deepEqual(testPath(equirectangular, {
//        type: "FeatureCollection",
//        features: [{
//        type: "Feature",
//        geometry: {
//        type: "Polygon",
//        coordinates: [[[-63, 18], [-62, 18], [-62, 17], [-63, 18]]]
//    }
//    }]
//    }), [
//        {type: "moveTo", translateX: 165, translateY: 160},
//        {type: "lineTo", translateX: 170, translateY: 160},
//        {type: "lineTo", translateX: 170, translateY: 165},
//        {type: "closePath"}
//    ]);
//    test.end();
//});
//
//tape("geoPath(…) wraps longitudes outside of ±180°", function(test) {
//    test.deepEqual(testPath(equirectangular, {
//        type: "Point",
//        coordinates: [180 + 1e-6, 0]
//    }), [
//        {type: "moveTo", translateX: -415, translateY: 250},
//        {type: "arc", translateX: -420, translateY: 250, r: 4.5}
//    ]);
//    test.end();
//});
//
//tape("geoPath(…) observes the correct winding order of a tiny polygon", function(test) {
//    test.deepEqual(testPath(equirectangular, {
//        type: "Polygon",
//        coordinates: [[
//        [-0.06904102953339501, 0.346043661846373],
//        [-6.725674252975136e-15, 0.3981303360336475],
//        [-6.742247658534323e-15, -0.08812465346531581],
//        [-0.17301258217724075, -0.12278150669440671],
//        [-0.06904102953339501, 0.346043661846373]
//        ]]
//    }), [
//        {type: "moveTo", translateX: 480, translateY: 248},
//        {type: "lineTo", translateX: 480, translateY: 248},
//        {type: "lineTo", translateX: 480, translateY: 250},
//        {type: "lineTo", translateX: 479, translateY: 251},
//        {type: "closePath"}
//    ]);
//    test.end();
//});
//
//tape("geoPath.projection(null)(…) does not transform coordinates", function(test) {
//    test.deepEqual(testPath(null, {
//        type: "Polygon",
//        coordinates: [[[-63, 18], [-62, 18], [-62, 17], [-63, 18]]]
//    }), [
//        {type: "moveTo", translateX: -63, translateY: 18},
//        {type: "lineTo", translateX: -62, translateY: 18},
//        {type: "lineTo", translateX: -62, translateY: 17},
//        {type: "closePath"}
//    ]);
//    test.end();
//});
//
//tape("geoPath.context(null)(null) returns null", function(test) {
//    var drawPath = d3_geo.geoPath();
//    test.strictEqual(drawPath(), null);
//    test.strictEqual(drawPath(null), null);
//    test.strictEqual(drawPath(undefined), null);
//    test.end();
//});
//
//tape("geoPath.context(null)(Unknown) returns null", function(test) {
//    var drawPath = d3_geo.geoPath();
//    test.strictEqual(drawPath({type: "Unknown"}), null);
//    test.strictEqual(drawPath({type: "__proto__"}), null);
//    test.end();
//});
//
//tape("geoPath(LineString) then geoPath(Point) does not treat the point as part of a line", function(test) {
//    var context = testContext(), drawPath = d3_geo.geoPath().projection(equirectangular).context(context);
//    drawPath({
//        type: "LineString",
//        coordinates: [[-63, 18], [-62, 18], [-62, 17]]
//    });
//    test.deepEqual(context.result(), [
//        {type: "moveTo", translateX: 165, translateY: 160},
//        {type: "lineTo", translateX: 170, translateY: 160},
//        {type: "lineTo", translateX: 170, translateY: 165}
//    ]);
//    drawPath({
//        type: "Point",
//        coordinates: [-63, 18]
//    });
//    test.deepEqual(context.result(), [
//        {type: "moveTo", translateX: 170, translateY: 160},
//        {type: "arc", translateX: 165, translateY: 160, r: 4.5}
//    ]);
//    test.end();
//});