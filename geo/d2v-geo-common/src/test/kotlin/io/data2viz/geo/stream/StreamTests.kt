package io.data2viz.geo.stream

//var tape = require("tape"),
//d3 = require("../");
//
//tape("geoStream(object) ignores unknown types", function(test) {
//    d3.geoStream({type: "Unknown"}, {});
//    d3.geoStream({type: "Feature", geometry: {type: "Unknown"}}, {});
//    d3.geoStream({type: "FeatureCollection", features: [{type: "Feature", geometry: {type: "Unknown"}}]}, {});
//    d3.geoStream({type: "GeometryCollection", geometries: [{type: "Unknown"}]}, {});
//    test.end();
//});
//
//tape("geoStream(object) ignores null geometries", function(test) {
//    d3.geoStream(null, {});
//    d3.geoStream({type: "Feature", geometry: null }, {});
//    d3.geoStream({type: "FeatureCollection", features: [{type: "Feature", geometry: null }]}, {});
//    d3.geoStream({type: "GeometryCollection", geometries: [null]}, {});
//    test.end();
//});
//
//tape("geoStream(object) returns void", function(test) {
//    test.equal(d3.geoStream({type: "Point", coordinates: [1, 2]}, {point: function() { return true; }}), undefined);
//    test.end();
//});
//
//tape("geoStream(object) allows empty multi-geometries", function(test) {
//    d3.geoStream({type: "MultiPoint", coordinates: []}, {});
//    d3.geoStream({type: "MultiLineString", coordinates: []}, {});
//    d3.geoStream({type: "MultiPolygon", coordinates: []}, {});
//    test.end();
//});
//
//tape("geoStream(Sphere) ↦ sphere", function(test) {
//    var calls = 0;
//    d3.geoStream({type: "Sphere"}, {
//        sphere: function() {
//        test.equal(arguments.length, 0);
//        test.equal(++calls, 1);
//    }
//    });
//    test.equal(calls, 1);
//    test.end();
//});
//
//tape("geoStream(Point) ↦ point", function(test) {
//    var calls = 0, coordinates = 0;
//    d3.geoStream({type: "Point", coordinates: [1, 2, 3]}, {
//        point: function(translateX, translateY, z) {
//        test.equal(arguments.length, 3);
//        test.equal(translateX, ++coordinates);
//        test.equal(translateY, ++coordinates);
//        test.equal(z, ++coordinates);
//        test.equal(++calls, 1);
//    }
//    });
//    test.equal(calls, 1);
//    test.end();
//});
//
//tape("geoStream(MultiPoint) ↦ point*", function(test) {
//    var calls = 0, coordinates = 0;
//    d3.geoStream({type: "MultiPoint", coordinates: [[1, 2, 3], [4, 5, 6]]}, {
//        point: function(translateX, translateY, z) {
//        test.equal(arguments.length, 3);
//        test.equal(translateX, ++coordinates);
//        test.equal(translateY, ++coordinates);
//        test.equal(z, ++coordinates);
//        test.equal(1 <= ++calls && calls <= 2, true);
//    }
//    });
//    test.equal(calls, 2);
//    test.end();
//});
//
//tape("geoStream(LineString) ↦ lineStart, point{2,}, lineEnd", function(test) {
//    var calls = 0, coordinates = 0;
//    d3.geoStream({type: "LineString", coordinates: [[1, 2, 3], [4, 5, 6]]}, {
//        lineStart: function() {
//        test.equal(arguments.length, 0);
//        test.equal(++calls, 1);
//    },
//        point: function(translateX, translateY, z) {
//        test.equal(arguments.length, 3);
//        test.equal(translateX, ++coordinates);
//        test.equal(translateY, ++coordinates);
//        test.equal(z, ++coordinates);
//        test.equal(2 <= ++calls && calls <= 3, true);
//    },
//        lineEnd: function() {
//        test.equal(arguments.length, 0);
//        test.equal(++calls, 4);
//    }
//    });
//    test.equal(calls, 4);
//    test.end();
//});
//
//tape("geoStream(MultiLineString) ↦ (lineStart, point{2,}, lineEnd)*", function(test) {
//    var calls = 0, coordinates = 0;
//    d3.geoStream({type: "MultiLineString", coordinates: [[[1, 2, 3], [4, 5, 6]], [[7, 8, 9], [10, 11, 12]]]}, {
//        lineStart: function() {
//        test.equal(arguments.length, 0);
//        test.equal(++calls === 1 || calls === 5, true);
//    },
//        point: function(translateX, translateY, z) {
//        test.equal(arguments.length, 3);
//        test.equal(translateX, ++coordinates);
//        test.equal(translateY, ++coordinates);
//        test.equal(z, ++coordinates);
//        test.equal(2 <= ++calls && calls <= 3 || 6 <= calls && calls <= 7, true);
//    },
//        lineEnd: function() {
//        test.equal(arguments.length, 0);
//        test.equal(++calls === 4 || calls === 8, true);
//    }
//    });
//    test.equal(calls, 8);
//    test.end();
//});
//
//tape("geoStream(Polygon) ↦ polygonStart, lineStart, point{2,}, lineEnd, polygonEnd", function(test) {
//    var calls = 0, coordinates = 0;
//    d3.geoStream({type: "Polygon", coordinates: [[[1, 2, 3], [4, 5, 6], [1, 2, 3]], [[7, 8, 9], [10, 11, 12], [7, 8, 9]]]}, {
//        polygonStart: function() {
//        test.equal(arguments.length, 0);
//        test.equal(++calls === 1, true);
//    },
//        lineStart: function() {
//        test.equal(arguments.length, 0);
//        test.equal(++calls === 2 || calls === 6, true);
//    },
//        point: function(translateX, translateY, z) {
//        test.equal(arguments.length, 3);
//        test.equal(translateX, ++coordinates);
//        test.equal(translateY, ++coordinates);
//        test.equal(z, ++coordinates);
//        test.equal(3 <= ++calls && calls <= 4 || 7 <= calls && calls <= 8, true);
//    },
//        lineEnd: function() {
//        test.equal(arguments.length, 0);
//        test.equal(++calls === 5 || calls === 9, true);
//    },
//        polygonEnd: function() {
//        test.equal(arguments.length, 0);
//        test.equal(++calls === 10, true);
//    }
//    });
//    test.equal(calls, 10);
//    test.end();
//});
//
//tape("geoStream(MultiPolygon) ↦ (polygonStart, lineStart, point{2,}, lineEnd, polygonEnd)*", function(test) {
//    var calls = 0, coordinates = 0;
//    d3.geoStream({type: "MultiPolygon", coordinates: [[[[1, 2, 3], [4, 5, 6], [1, 2, 3]]], [[[7, 8, 9], [10, 11, 12], [7, 8, 9]]]]}, {
//        polygonStart: function() {
//        test.equal(arguments.length, 0);
//        test.equal(++calls === 1 || calls === 7, true);
//    },
//        lineStart: function() {
//        test.equal(arguments.length, 0);
//        test.equal(++calls === 2 || calls === 8, true);
//    },
//        point: function(translateX, translateY, z) {
//        test.equal(arguments.length, 3);
//        test.equal(translateX, ++coordinates);
//        test.equal(translateY, ++coordinates);
//        test.equal(z, ++coordinates);
//        test.equal(3 <= ++calls && calls <= 4 || 9 <= calls && calls <= 10, true);
//    },
//        lineEnd: function() {
//        test.equal(arguments.length, 0);
//        test.equal(++calls === 5 || calls === 11, true);
//    },
//        polygonEnd: function() {
//        test.equal(arguments.length, 0);
//        test.equal(++calls === 6 || calls === 12, true);
//    }
//    });
//    test.equal(calls, 12);
//    test.end();
//});
//
//tape("geoStream(Feature) ↦ .*", function(test) {
//    var calls = 0, coordinates = 0;
//    d3.geoStream({type: "Feature", geometry: {type: "Point", coordinates: [1, 2, 3]}}, {
//        point: function(translateX, translateY, z) {
//        test.equal(arguments.length, 3);
//        test.equal(translateX, ++coordinates);
//        test.equal(translateY, ++coordinates);
//        test.equal(z, ++coordinates);
//        test.equal(++calls, 1);
//    }
//    });
//    test.equal(calls, 1);
//    test.end();
//});
//
//tape("geoStream(FeatureCollection) ↦ .*", function(test) {
//    var calls = 0, coordinates = 0;
//    d3.geoStream({type: "FeatureCollection", features: [{type: "Feature", geometry: {type: "Point", coordinates: [1, 2, 3]}}]}, {
//        point: function(translateX, translateY, z) {
//        test.equal(arguments.length, 3);
//        test.equal(translateX, ++coordinates);
//        test.equal(translateY, ++coordinates);
//        test.equal(z, ++coordinates);
//        test.equal(++calls, 1);
//    }
//    });
//    test.equal(calls, 1);
//    test.end();
//});
//
//tape("geoStream(GeometryCollection) ↦ .*", function(test) {
//    var calls = 0, coordinates = 0;
//    d3.geoStream({type: "GeometryCollection", geometries: [{type: "Point", coordinates: [1, 2, 3]}]}, {
//        point: function(translateX, translateY, z) {
//        test.equal(arguments.length, 3);
//        test.equal(translateX, ++coordinates);
//        test.equal(translateY, ++coordinates);
//        test.equal(z, ++coordinates);
//        test.equal(++calls, 1);
//    }
//    });
//    test.equal(calls, 1);
//    test.end();
//});