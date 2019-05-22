package io.data2viz.geo.projection

import io.data2viz.geom.Extent
import io.data2viz.geo.geojson.Sphere
import io.data2viz.geo.geojson.geoPath
import io.data2viz.geom.PathGeom
import io.data2viz.math.deg
import io.data2viz.test.TestBase
import kotlin.test.Test

class MercatorProjectionTests : TestBase() {

    val util = ProjectionTests()

    // TODO add more tests

    @Test
    fun mercator_clip_extent_null_sets_default_automatic_clip_extent() {
        val projection = MercatorProjection()
        projection.x = .0
        projection.y = .0
        projection.scale = 1.0
        projection.clipExtent = null
        projection.precision = .0

        projection.clipExtent shouldBe null
        val path = geoPath(projection, PathGeom()).path(Sphere()) as PathGeom
//        drawPath.drawPath.round() shouldBe  "M3.141593,-3.141593L3.141593,0L3.141593,3.141593L3.141593,3.141593L-3.141593,3.141593L-3.141593,3.141593L-3.141593,0L-3.141593,-3.141593L-3.141593,-3.141593L3.141593,-3.141593Z".round()
    }

    @Test
    fun mercator_center_center_sets_correct_automatic_clip_extent() {
        val projection = MercatorProjection()
        projection.x = .0
        projection.y = .0
        projection.scale = 1.0
        projection.center = arrayOf(10.0.deg, 10.0.deg)
        projection.precision = .0

        projection.clipExtent shouldBe null
        val path = geoPath(projection, PathGeom()).path(Sphere()) as PathGeom
//        drawPath.drawPath.round() shouldBe  "M2.967060,-2.966167L2.967060,0.175426L2.967060,3.317018L2.967060,3.317018L-3.316126,3.317018L-3.316126,3.317019L-3.316126,0.175426L-3.316126,-2.966167L-3.316126,-2.966167L2.967060,-2.966167Z".round()
    }

    @Test
    fun mercator_center_center_intersects_the_specified_clip_extent_with_the_automatic_clip_extent() {
        val projection = MercatorProjection()
        projection.x = .0
        projection.y = .0
        projection.scale = 1.0
        projection.center = arrayOf(10.0.deg, 10.0.deg)
        projection.clipExtent = Extent(-10.0, -10.0, 10.0, 10.0)
        projection.precision = .0

        projection.clipExtent!!.width shouldBe 20.0
        projection.clipExtent!!.height shouldBe 20.0
        projection.clipExtent!!.x0 shouldBe -10.0
        projection.clipExtent!!.y0 shouldBe -10.0
        val path = geoPath(projection, PathGeom()).path(Sphere()) as PathGeom
//        drawPath.drawPath.round() shouldBe  "M3.141593,-10L3.141593,0L3.141593,10L3.141593,10L-3.141593,10L-3.141593,10L-3.141593,0L-3.141593,-10L-3.141593,-10L3.141593,-10Z".round()
    }

    /*
tape("mercator.clipExtent(extent).scale(scale) updates the intersected clip extent", function(test) {
  var projection = d3.geoMercator().translate([0, 0]).clipExtent([[-10, -10], [10, 10]]).scale(1).precision(0);
  test.pathEqual(d3.geoPath(projection)({type: "Sphere"}), "M3.141593,-10L3.141593,0L3.141593,10L3.141593,10L-3.141593,10L-3.141593,10L-3.141593,0L-3.141593,-10L-3.141593,-10L3.141593,-10Z");
  test.deepEqual(projection.clipExtent(), [[-10, -10], [10, 10]]);
  test.end();
});

tape("mercator.clipExtent(extent).translate(translate) updates the intersected clip extent", function(test) {
  var projection = d3.geoMercator().scale(1).clipExtent([[-10, -10], [10, 10]]).translate([0, 0]).precision(0);
  test.pathEqual(d3.geoPath(projection)({type: "Sphere"}), "M3.141593,-10L3.141593,0L3.141593,10L3.141593,10L-3.141593,10L-3.141593,10L-3.141593,0L-3.141593,-10L-3.141593,-10L3.141593,-10Z");
  test.deepEqual(projection.clipExtent(), [[-10, -10], [10, 10]]);
  test.end();
});

tape("mercator.rotate(…) does not affect the automatic clip extent", function(test) {
  var projection = d3.geoMercator(), object = {
    type: "MultiPoint",
    coordinates: [
      [-82.35024908550241, 29.649391549778745],
      [-82.35014449996858, 29.65075946917633],
      [-82.34916073446641, 29.65070265688781],
      [-82.3492653331286, 29.64933474064504]
    ]
  };
  projection.fitExtent([[0, 0], [960, 600]], object);
  test.deepEqual(projection.scale(), 20969742.365692537);
  test.deepEqual(projection.translate(), [30139734.76760269, 11371473.949706702]);
  projection.rotate([0, 95]).fitExtent([[0, 0], [960, 600]], object);
  test.deepEqual(projection.scale(), 35781690.650920525);
  test.deepEqual(projection.translate(), [75115911.95344563, 2586046.4116968135]);
  test.end();
});
     */

    // TODO: revert
//
//    @Test
//    fun mercator_various_projects_1() {
//        val projection = mercatorProjection {
//            x = .0
//            y = .0
//            rotate = arrayOf(20.0.deg, 10.0.deg, 30.0.deg)
//        }
//
//        util.checkProjection(projection, 84.0, 59.0, doubleArrayOf(468.39738235470327, -301.9997264679594))
//        util.checkProjection(projection, -22.0, 16.0, doubleArrayOf(-41.232469642834104, -58.132750186605435))
//        util.checkProjection(projection, 800.0, -800.0, doubleArrayOf(206.79103709446065, 151.98632483005113))
//    }
//
//    @Test
//    fun mercator_various_projects_2() {
//        val projection = mercatorProjection {
//            x = 40.0
//            y = 200.0
//            rotate = arrayOf(5.0.deg, .0.deg, (-30.0).deg)
//        }
//
//        util.checkProjection(projection, 84.0, 59.0, doubleArrayOf(278.67805496267147, 119.04407321930081))
//        util.checkProjection(projection, -22.0, 16.0, doubleArrayOf(22.511032184577658, 138.94899164036798))
//        util.checkProjection(projection, 800.0, -800.0, doubleArrayOf(-193.4977306320785, 464.9972618113396))
//    }
//
//    @Test
//    fun mercator_various_projects_3() {
//        val projection = mercatorProjection {
//            x = -100.0
//            y = 20.0
//            rotate = arrayOf((-15.0).deg, 20.0.deg, .0.deg)
//            scale = 1.0
//            precision = .0
//        }
//
//        util.checkProjection(projection, 84.0, 59.0, doubleArrayOf(-98.18516654577843, 18.672644484723165))
//        util.checkProjection(projection, -22.0, 16.0, doubleArrayOf(-100.74508864271014, 19.421488829918328))
//        util.checkProjection(projection, 800.0, -800.0, doubleArrayOf(-99.63002236034055, 21.473889277898014))
//    }
}