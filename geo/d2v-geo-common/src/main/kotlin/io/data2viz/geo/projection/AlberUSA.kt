package io.data2viz.geo.projection

import io.data2viz.geojson.GeoJsonObject
import io.data2viz.geom.Extent
import io.data2viz.math.HALFPI
import io.data2viz.math.deg
import kotlin.math.atan
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.tan

class MultiplexStream(val streams: Collection<Stream>) : Stream {
    override fun point(x: Double, y: Double, z: Double) = streams.forEach {
        it.point(x, y, z)
    }

    override fun lineStart() = streams.forEach {
        it.lineStart()
    }

    override fun lineEnd() = streams.forEach {
        it.lineEnd()
    }

    override fun polygonStart() = streams.forEach {
        it.polygonStart()
    }

    override fun polygonEnd() = streams.forEach {
        it.polygonEnd()
    }

    override fun sphere() = streams.forEach {
        it.sphere()
    }
}

class AlberUSAProjector : ProjectableInvertable {

    val lover48Projection = albersProjection()
    val alaska = conicEqualAreaProjection {
        rotate = arrayOf(154.0.deg, 0.0.deg)
        center = arrayOf(-2.0.deg, 58.5.deg)
        parallels = arrayOf(55.0.deg, 65.0.deg)
    }
    val hawaii = conicEqualAreaProjection {
        rotate = arrayOf(157.0.deg, 0.0.deg)
        center = arrayOf(-3.0.deg, 19.9.deg)
        parallels = arrayOf(8.0.deg, 18.0.deg)

    }

    override fun projectLambda(lambda: Double, phi: Double): Double = lambda

    override fun projectPhi(lambda: Double, phi: Double): Double = ln(tan((HALFPI + phi) / 2))

    override fun project(lambda: Double, phi: Double) = doubleArrayOf(lambda, ln(tan((HALFPI + phi) / 2)))
    override fun invert(x: Double, y: Double) = doubleArrayOf(x, 2 * atan(exp(y)) - HALFPI)

//    function albersUsa(coordinates) {
//        var x = coordinates[0], y = coordinates[1];
//        return point = null,
//        (lower48Point.point(x, y), point)
//        || (alaskaPoint.point(x, y), point)
//        || (hawaiiPoint.point(x, y), point);
//    }
//
//    albersUsa.invert = function(coordinates) {
//        var k = lower48.scale(),
//        t = lower48.translate(),
//        x = (coordinates[0] - t[0]) / k,
//        y = (coordinates[1] - t[1]) / k;
//        return (y >= 0.120 && y < 0.234 && x >= -0.425 && x < -0.214 ? alaska
//        : y >= 0.166 && y < 0.234 && x >= -0.214 && x < -0.115 ? hawaii
//        : lower48).invert(coordinates);
//    };
}

fun alberUSAProjection() = alberUSAProjection {

}

fun alberUSAProjection(init: Projection.() -> Unit) = projection(AlberUSAProjector()) {
    scale = 1070.0
}

//// A composite projection for the United States, configured by default for
//// 960×500. The projection also works quite well at 960×600 if you change the
//// scale to 1285 and adjust the translate accordingly. The set of standard
//// parallels for each region comes from USGS, which is published here:
//// http://egsc.usgs.gov/isb/pubs/MapProjections/projections.html#albers
class AlberUSAProjection : MutableProjection(AlberUSAProjector()) {

    //    lower48 = albers(), lower48Point,
//    alaska = conicEqualArea().rotate([154, 0]).center([-2, 58.5]).parallels([55, 65]), alaskaPoint, // EPSG:3338
//    hawaii = conicEqualArea().rotate([157, 0]).center([-3, 19.9]).parallels([8, 18]), hawaiiPoint, // ESRI:102007
//    point, pointStream = {point: function(x, y) { point = [x, y]; }};

    override var translate: DoubleArray
        get() = super.translate
        set(value) {}

//    if (!arguments.length) return lower48.translate();
//        var k = lower48.scale(), x = +_[0], y = +_[1];
//
//        lower48Point = lower48
//            .translate(_)
//            .clipExtent([[x - 0.455 * k, y - 0.238 * k], [x + 0.455 * k, y + 0.238 * k]])
//            .stream(pointStream);
//
//        alaskaPoint = alaska
//            .translate([x - 0.307 * k, y + 0.201 * k])
//            .clipExtent([[x - 0.425 * k + epsilon, y + 0.120 * k + epsilon], [x - 0.214 * k - epsilon, y + 0.234 * k - epsilon]])
//            .stream(pointStream);
//
//        hawaiiPoint = hawaii
//            .translate([x - 0.205 * k, y + 0.212 * k])
//            .clipExtent([[x - 0.214 * k + epsilon, y + 0.166 * k + epsilon], [x - 0.115 * k - epsilon, y + 0.234 * k - epsilon]])
//            .stream(pointStream);
//
//        return reset();

    override var scale: Double
        get() = super.scale
        set(value) {}

    //        if (!arguments.length) return lower48.scale();
//        lower48.scale(_), alaska.scale(_ * 0.35), hawaii.scale(_);
//        return albersUsa.translate(lower48.translate());
    override var precision: Double
        get() = super.precision
        set(value) {}

    //        if (!arguments.length) return lower48.precision();
//        lower48.precision(_), alaska.precision(_), hawaii.precision(_);
//        return reset();

    override fun stream(stream: Stream): Stream {
        return super.stream(stream)
        //        return cache && cacheStream === stream ? cache : cache = multiplex([lower48.stream(cacheStream = stream), alaska.stream(stream), hawaii.stream(stream)]);
    }

    override fun fitExtent(extent: Extent, geo: GeoJsonObject): Projection {
        return super.fitExtent(extent, geo)
        //        return fitExtent(albersUsa, extent, object);
    }

    override fun fitWidth(width: Double, geo: GeoJsonObject): Projection {
        return super.fitWidth(width, geo)
        //        return fitSize(albersUsa, size, object);
    }

    override fun fitHeight(height: Double, geo: GeoJsonObject): Projection {
        return super.fitHeight(height, geo)
        //        return fitWidth(albersUsa, width, object);
    }


    override fun fitSize(width: Double, height: Double, geo: GeoJsonObject): Projection {
        return super.fitSize(width, height, geo)
        //        return fitHeight(albersUsa, height, object);
    }

//    fun reset() {
//        cache = cacheStream = null;
//        return albersUsa;
//    }

}


//import {epsilon} from "../math";
//import albers from "./albers";
//import conicEqualArea from "./conicEqualArea";
//import {fitExtent, fitSize, fitWidth, fitHeight} from "./fit";
//
//// The projections must have mutually exclusive clip regions on the sphere,
//// as this will avoid emitting interleaving lines and polygons.
//function multiplex(streams) {
//    var n = streams.length;
//    return {
//        point: function(x, y) { var i = -1; while (++i < n) streams[i].point(x, y); },
//        sphere: function() { var i = -1; while (++i < n) streams[i].sphere(); },
//        lineStart: function() { var i = -1; while (++i < n) streams[i].lineStart(); },
//        lineEnd: function() { var i = -1; while (++i < n) streams[i].lineEnd(); },
//        polygonStart: function() { var i = -1; while (++i < n) streams[i].polygonStart(); },
//        polygonEnd: function() { var i = -1; while (++i < n) streams[i].polygonEnd(); }
//    };
//}
//

//export default function() {
//    var cache,
//    cacheStream,

//
//
//
//    albersUsa.stream = function(stream) {

//    };
//
//    albersUsa.precision = function(_) {

//    };
//
//    albersUsa.scale = function(_) {

//    };
//
//    albersUsa.translate = function(_) {
//
//    };
//
