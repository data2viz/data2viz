package io.data2viz.geo.projection

import io.data2viz.test.TestBase
import kotlin.test.Test

// TODO: Check

class AngleTests: TestBase() {

    val util = ProjectionTests()

    @Test
    fun angle_defaults_to_zero() {
        val projection = gnomonicProjection()

//        util.checkProjection(projection, 0.0, 0.0, doubleArrayOf(0.0, 0.0));
//        util.checkProjection(projection, 10.0, 0.0, doubleArrayOf(0.017632698070846498, 0.0));
//        util.checkProjection(projection, -10.0, 0.0, doubleArrayOf(-0.017632698070846498, 0.0));
//        util.checkProjection(projection, 0.0, 10.0, doubleArrayOf(0.0, -0.017632698070846498));
//        util.checkProjection(projection, 0.0, -10.0, doubleArrayOf(0.0, 0.017632698070846498));
//        util.checkProjection(projection, 10.0, 10.0, doubleArrayOf(0.17632698070846495, -0.17904710860483972));
//        util.checkProjection(projection, 10.0, -10.0, doubleArrayOf(0.17632698070846495, 0.17904710860483972));
//        util.checkProjection(projection, -10.0, 10.0, doubleArrayOf(-0.17632698070846495, -0.17904710860483972));
//        util.checkProjection(projection, -10.0, -10.0, doubleArrayOf(-0.17632698070846495, 0.17904710860483972));


        }


}

//var_tape_=_require("tape"),
//d3_=_require("../../");
//
//require("../inDelta");
//require("./projectionEqual");
//
//tape("projection.angle(…)_defaults_to_zero",_function(test)_{
//____var_projection_=_d3.geoGnomonic().scale(1).translate([0,_0]);
//____test.equal(projection.angle(),_0);
//____test.projectionEqual(projection,_[0,_0],_[0,_0]);
//____test.projectionEqual(projection,_[10,_0],_[0.17632698070846498,_0]);
//____test.projectionEqual(projection,_[-10,_0],_[-0.17632698070846498,_0]);
//____test.projectionEqual(projection,_[0,_10],_[0,_-0.17632698070846498]);
//____test.projectionEqual(projection,_[0,_-10],_[0,_0.17632698070846498]);
//____test.projectionEqual(projection,_[10,_10],_[0.17632698070846495,_-0.17904710860483972]);
//____test.projectionEqual(projection,_[10,_-10],_[0.17632698070846495,_0.17904710860483972]);
//____test.projectionEqual(projection,_[-10,_10],_[-0.17632698070846495,_-0.17904710860483972]);
//____test.projectionEqual(projection,_[-10,_-10],_[-0.17632698070846495,_0.17904710860483972]);
//____test.end();
//});
//
//tape("projection.angle(…)_rotates_by_the_specified_degrees_after_projecting",_function(test)_{
//____var_projection_=_d3.geoGnomonic().scale(1).translate([0,_0]).angle(30);
//____test.inDelta(projection.angle(),_30);
//____test.projectionEqual(projection,_[0,_0],_[0,_0]);
//____test.projectionEqual(projection,_[10,_0],_[0.1527036446661393,_-0.08816349035423247]);
//____test.projectionEqual(projection,_[-10,_0],_[-0.1527036446661393,_0.08816349035423247]);
//____test.projectionEqual(projection,_[0,_10],_[-0.08816349035423247,_-0.1527036446661393]);
//____test.projectionEqual(projection,_[0,_-10],_[0.08816349035423247,_0.1527036446661393]);
//____test.projectionEqual(projection,_[10,_10],_[0.06318009036371944,_-0.24322283488017502]);
//____test.projectionEqual(projection,_[10,_-10],_[0.24222719896855913,_0.0668958541717101]);
//____test.projectionEqual(projection,_[-10,_10],_[-0.24222719896855913,_-0.0668958541717101]);
//____test.projectionEqual(projection,_[-10,_-10],_[-0.06318009036371944,_0.24322283488017502]);
//____test.end();
//});
//
//tape("projection.angle(…)_rotates_by_the_specified_degrees_after_projecting",_function(test)_{
//____var_projection_=_d3.geoGnomonic().scale(1).translate([0,_0]).angle(-30);
//____test.inDelta(projection.angle(),_-30);
//____test.projectionEqual(projection,_[0,_0],_[0,_0]);
//____test.projectionEqual(projection,_[10,_0],_[0.1527036446661393,_0.08816349035423247]);
//____test.projectionEqual(projection,_[-10,_0],_[-0.1527036446661393,_-0.08816349035423247]);
//____test.projectionEqual(projection,_[0,_10],_[0.08816349035423247,_-0.1527036446661393]);
//____test.projectionEqual(projection,_[0,_-10],_[-0.08816349035423247,_0.1527036446661393]);
//____test.projectionEqual(projection,_[10,_10],_[0.24222719896855913,_-0.0668958541717101]);
//____test.projectionEqual(projection,_[10,_-10],_[0.06318009036371944,_0.24322283488017502]);
//____test.projectionEqual(projection,_[-10,_10],_[-0.06318009036371944,_-0.24322283488017502]);
//____test.projectionEqual(projection,_[-10,_-10],_[-0.24222719896855913,_0.0668958541717101]);
//____test.end();
//});
//
//tape("projection.angle(…)_wraps_around_360°",_function(test)_{
//____var_projection_=_d3.geoGnomonic().scale(1).translate([0,_0]).angle(360);
//____test.equal(projection.angle(),_0);
//____test.end();
//});