package io.data2viz.geo.projection


import io.data2viz.geo.geojson.path.GeoArea
import io.data2viz.geo.geojson.Sphere
import io.data2viz.geojson.*
import io.data2viz.test.TestBase
import kotlin.math.PI
import kotlin.test.Test

class AreaTests : TestBase() {

    @Test
    fun geoarea_of_a_point() {
        GeoArea().result(Point(pt(.0, .0))) shouldBeClose .0
    }

    @Test
    fun geoarea_of_a_multipoint() {
        GeoArea().result(MultiPoint(arrayOf(pt(.0, 1.0), pt(2.0, 3.0)))) shouldBeClose .0
    }

    @Test
    fun geoarea_of_a_tiny_polygon() {
        GeoArea().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(-64.66070178517852, 18.33986913231323),
                        pt(-64.66079715091509, 18.33994007490749),
                        pt(-64.66074946804680, 18.33994007490749),
                        pt(-64.66070178517852, 18.33986913231323)
                    )
                )
            )
//        ) shouldBeClose 4.890516e-13
        ) shouldBeClose .0
    }


    // TODO : pass in JVM not JS !! (shouldBeClose .0 but delta is 5e-13)
    @Test
    fun geoarea_of_a_zero_area_polygon() {
        GeoArea().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(96.79142432523281, 5.262704519048153),
                        pt(96.81065389253769, 5.272455576551362),
                        pt(96.82988345984256, 5.272455576551362),
                        pt(96.81065389253769, 5.272455576551362),
                        pt(96.79142432523281, 5.262704519048153)
                    )
                )
            )
        ) shouldBeClose .0
    }

    @Test
    fun geoarea_of_a_semilune_polygon() {
        GeoArea().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(.0, .0),
                        pt(.0, 90.0),
                        pt(90.0, .0),
                        pt(.0, .0)
                    )
                )
            )
        ) shouldBeClose PI / 2
    }

    @Test
    fun geoarea_of_a_lune_polygon() {
        GeoArea().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(.0, .0),
                        pt(.0, 90.0),
                        pt(90.0, .0),
                        pt(.0, -90.0),
                        pt(.0, .0)
                    )
                )
            )
        ) shouldBeClose PI
    }

    @Test
    fun geoarea_of_hemisphere_north() {
        GeoArea().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(.0, .0),
                        pt(-90.0, .0),
                        pt(180.0, .0),
                        pt(90.0, .0),
                        pt(.0, .0)
                    )
                )
            )
        ) shouldBeClose PI * 2
    }

    @Test
    fun geoarea_of_hemisphere_south() {
        GeoArea().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(.0, .0),
                        pt(90.0, .0),
                        pt(180.0, .0),
                        pt(-90.0, .0),
                        pt(.0, .0)
                    )
                )
            )
        ) shouldBeClose PI * 2
    }

    @Test
    fun geoarea_of_hemisphere_east() {
        GeoArea().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(.0, .0),
                        pt(.0, 90.0),
                        pt(180.0, .0),
                        pt(.0, -90.0),
                        pt(.0, .0)
                    )
                )
            )
        ) shouldBeClose PI * 2
    }

    @Test
    fun geoarea_of_hemisphere_west() {
        GeoArea().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(.0, .0),
                        pt(.0, -90.0),
                        pt(180.0, .0),
                        pt(.0, 90.0),
                        pt(.0, .0)
                    )
                )
            )
        ) shouldBeClose PI * 2
    }

    @Test
    fun geoarea_of_multipolygon_2_hemispheres() {
        GeoArea().result(
            MultiPolygon(
                arrayOf(
                    arrayOf(
                        arrayOf(
                            pt(.0, .0),
                            pt(-90.0, .0),
                            pt(180.0, .0),
                            pt(90.0, .0),
                            pt(.0, .0)
                        )
                    ),
                    arrayOf(
                        arrayOf(
                            pt(.0, .0),
                            pt(90.0, .0),
                            pt(180.0, .0),
                            pt(-90.0, .0),
                            pt(.0, .0)
                        )
                    )
                )
            )
        ) shouldBeClose PI * 4
    }

    @Test
    fun geoarea_of_sphere() {
        GeoArea().result(Sphere()) shouldBeClose PI * 4
    }

    @Test
    fun geoarea_of_geometryCollection_polygon() {
        GeoArea().result(
            GeometryCollection(
                arrayOf(
                    Polygon(
                        arrayOf(
                            arrayOf(
                                pt(.0, .0),
                                pt(.0, -90.0),
                                pt(180.0, .0),
                                pt(.0, 90.0),
                                pt(.0, .0)
                            )
                        )
                    )
                )
            )
        ) shouldBeClose PI * 2
    }

    @Test
    fun geoarea_of_featureCollection_sphere() {
        GeoArea().result(FeatureCollection(arrayOf(Feature(Sphere())))) shouldBeClose PI * 4
    }

    @Test
    fun geoarea_of_feature_sphere() {
        GeoArea().result(Feature(Sphere())) shouldBeClose PI * 4
    }

    @Test
    fun geoarea_of_linestring() {
        GeoArea().result(LineString(arrayOf(pt(.0, 1.0), pt(2.0, 3.0)))) shouldBeClose .0
    }

    @Test
    fun geoarea_of_multilinestring() {
        GeoArea().result(
            MultiLineString(
                arrayOf(
                    arrayOf(pt(.0, 1.0), pt(2.0, 3.0)),
                    arrayOf(pt(4.0, 5.0), pt(6.0, 7.0))
                )
            )
        ) shouldBeClose .0
    }

    /*
function stripes(a, b) {
  return {type: "Polygon", coordinates: [a, b].map(function(d, i) {
    var stripe = array.range(-180, 180, 0.1).map(function(translateX) { return [translateX, d]; });
    stripe.push(stripe[0]);
    return i ? stripe.reverse() : stripe;
  })};
}

tape("drawArea: Polygon - geoGraticule outline sphere", function(test) {
  test.inDelta(d3.geoArea(d3.geoGraticule().extent([[-180, -90], [180, 90]]).outline()), 4 * Math.PI, 1e-5);
  test.end();
});

tape("drawArea: Polygon - geoGraticule outline hemisphere", function(test) {
  test.inDelta(d3.geoArea(d3.geoGraticule().extent([[-180, 0], [180, 90]]).outline()), 2 * Math.PI, 1e-5);
  test.end();
});

tape("drawArea: Polygon - geoGraticule outline semilune", function(test) {
  test.inDelta(d3.geoArea(d3.geoGraticule().extent([[0, 0], [90, 90]]).outline()), Math.PI / 2, 1e-5);
  test.end();
});

tape("drawArea: Polygon - circles hemisphere", function(test) {
  test.inDelta(d3.geoArea(d3.geoCircle().radius(90)()), 2 * Math.PI, 1e-5);
  test.end();
});

tape("drawArea: Polygon - circles 60°", function(test) {
  test.inDelta(d3.geoArea(d3.geoCircle().radius(60).precision(0.1)()), Math.PI, 1e-5);
  test.end();
});

tape("drawArea: Polygon - circles 60° North", function(test) {
  test.inDelta(d3.geoArea(d3.geoCircle().radius(60).precision(0.1).center([0, 90])()), Math.PI, 1e-5);
  test.end();
});

tape("drawArea: Polygon - circles 45°", function(test) {
  test.inDelta(d3.geoArea(d3.geoCircle().radius(45).precision(0.1)()), Math.PI * (2 - Math.SQRT2), 1e-5);
  test.end();
});

tape("drawArea: Polygon - circles 45° North", function(test) {
  test.inDelta(d3.geoArea(d3.geoCircle().radius(45).precision(0.1).center([0, 90])()), Math.PI * (2 - Math.SQRT2), 1e-5);
  test.end();
});

tape("drawArea: Polygon - circles 45° South", function(test) {
  test.inDelta(d3.geoArea(d3.geoCircle().radius(45).precision(0.1).center([0, -90])()), Math.PI * (2 - Math.SQRT2), 1e-5);
  test.end();
});

tape("drawArea: Polygon - circles 135°", function(test) {
  test.inDelta(d3.geoArea(d3.geoCircle().radius(135).precision(0.1)()), Math.PI * (2 + Math.SQRT2), 1e-5);
  test.end();
});

tape("drawArea: Polygon - circles 135° North", function(test) {
  test.inDelta(d3.geoArea(d3.geoCircle().radius(135).precision(0.1).center([0, 90])()), Math.PI * (2 + Math.SQRT2), 1e-5);
  test.end();
});

tape("drawArea: Polygon - circles 135° South", function(test) {
  test.inDelta(d3.geoArea(d3.geoCircle().radius(135).precision(0.1).center([0, -90])()), Math.PI * (2 + Math.SQRT2), 1e-5);
  test.end();
});

tape("drawArea: Polygon - circles tiny", function(test) {
  test.inDelta(d3.geoArea(d3.geoCircle().radius(1e-6).precision(0.1)()), 0, 1e-6);
  test.end();
});

tape("drawArea: Polygon - circles huge", function(test) {
  test.inDelta(d3.geoArea(d3.geoCircle().radius(180 - 1e-6).precision(0.1)()), 4 * Math.PI, 1e-6);
  test.end();
});

tape("drawArea: Polygon - circles 60° with 45° hole", function(test) {
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

tape("drawArea: Polygon - circles 45° holes at [0°, 0°] and [0°, 90°]", function(test) {
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

tape("drawArea: Polygon - circles 45° holes at [0°, 90°] and [0°, 0°]", function(test) {
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

tape("drawArea: Polygon - stripes 45°, -45°", function(test) {
  test.inDelta(d3.geoArea(stripes(45, -45)), Math.PI * 2 * Math.SQRT2, 1e-5);
  test.end();
});

tape("drawArea: Polygon - stripes -45°, 45°", function(test) {
  test.inDelta(d3.geoArea(stripes(-45, 45)), Math.PI * 2 * (2 - Math.SQRT2), 1e-5);
  test.end();
});

tape("drawArea: Polygon - stripes 45°, 30°", function(test) {
  test.inDelta(d3.geoArea(stripes(45, 30)), Math.PI * (Math.SQRT2 - 1), 1e-5);
  test.end();
});
     */
}