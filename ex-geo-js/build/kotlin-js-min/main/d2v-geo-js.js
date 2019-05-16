(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin', 'geojson-js', 'd2v-core-js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'), require('geojson-js'), require('d2v-core-js'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'd2v-geo-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'd2v-geo-js'.");
    }
    if (typeof this['geojson-js'] === 'undefined') {
      throw new Error("Error loading module 'd2v-geo-js'. Its dependency 'geojson-js' was not found. Please, check whether 'geojson-js' is loaded prior to 'd2v-geo-js'.");
    }
    if (typeof this['d2v-core-js'] === 'undefined') {
      throw new Error("Error loading module 'd2v-geo-js'. Its dependency 'd2v-core-js' was not found. Please, check whether 'd2v-core-js' is loaded prior to 'd2v-geo-js'.");
    }
    root['d2v-geo-js'] = factory(typeof this['d2v-geo-js'] === 'undefined' ? {} : this['d2v-geo-js'], kotlin, this['geojson-js'], this['d2v-core-js']);
  }
}(this, function (_, Kotlin, $module$geojson_js, $module$d2v_core_js) {
  'use strict';
  var Point = $module$geojson_js.io.data2viz.geojson.Point;
  var MultiPoint = $module$geojson_js.io.data2viz.geojson.MultiPoint;
  var Polygon = $module$geojson_js.io.data2viz.geojson.Polygon;
  var MultiPolygon = $module$geojson_js.io.data2viz.geojson.MultiPolygon;
  var LineString = $module$geojson_js.io.data2viz.geojson.LineString;
  var MultiLineString = $module$geojson_js.io.data2viz.geojson.MultiLineString;
  var GeometryCollection = $module$geojson_js.io.data2viz.geojson.GeometryCollection;
  var FeatureCollection = $module$geojson_js.io.data2viz.geojson.FeatureCollection;
  var Feature = $module$geojson_js.io.data2viz.geojson.Feature;
  var toMutableList = Kotlin.kotlin.collections.toMutableList_4c7yge$;
  var get_lastIndex = Kotlin.kotlin.collections.get_lastIndex_55thoc$;
  var toRadians = $module$d2v_core_js.io.data2viz.math.toRadians_yrwdxr$;
  var toDoubleArray = Kotlin.kotlin.collections.toDoubleArray_tcduak$;
  var math = $module$d2v_core_js.io.data2viz.math;
  var Extent = $module$d2v_core_js.io.data2viz.geom.Extent;
  var Unit = Kotlin.kotlin.Unit;
  var getCallableRef = Kotlin.getCallableRef;
  var kotlin_js_internal_DoubleCompanionObject = Kotlin.kotlin.js.internal.DoubleCompanionObject;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var mutableListOf = Kotlin.kotlin.collections.mutableListOf_i5x0yv$;
  var IntRange = Kotlin.kotlin.ranges.IntRange;
  var last = Kotlin.kotlin.collections.last_2p1efm$;
  var ensureNotNull = Kotlin.ensureNotNull;
  var toDegrees = $module$d2v_core_js.io.data2viz.math.toDegrees_yrwdxr$;
  var Geometry = $module$geojson_js.io.data2viz.geojson.Geometry;
  var throwUPAE = Kotlin.throwUPAE;
  var asReversed = Kotlin.kotlin.collections.asReversed_2p1efm$;
  var range = $module$d2v_core_js.io.data2viz.math.range_yvo9jy$;
  var math_0 = Kotlin.kotlin.math;
  var isNaN_0 = Kotlin.kotlin.isNaN_yrwdxr$;
  var get_alt = $module$geojson_js.io.data2viz.geojson.get_alt_hb77y9$;
  var get_lon = $module$geojson_js.io.data2viz.geojson.get_lon_hb77y9$;
  var get_lat = $module$geojson_js.io.data2viz.geojson.get_lat_hb77y9$;
  var Kind_INTERFACE = Kotlin.Kind.INTERFACE;
  var Kind_OBJECT = Kotlin.Kind.OBJECT;
  var until = Kotlin.kotlin.ranges.until_dqglrj$;
  var flatten = Kotlin.kotlin.collections.flatten_u0ad8z$;
  var rangeTo = Kotlin.kotlin.ranges.rangeTo_38ydlf$;
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  var coerceIn = Kotlin.kotlin.ranges.coerceIn_nig4hr$;
  var IllegalStateException_init = Kotlin.kotlin.IllegalStateException_init;
  var sortWith = Kotlin.kotlin.collections.sortWith_nqfjgj$;
  var first = Kotlin.kotlin.collections.first_2p1efm$;
  var equals = Kotlin.equals;
  var listOf = Kotlin.kotlin.collections.listOf_i5x0yv$;
  var get_deg = $module$d2v_core_js.io.data2viz.math.get_deg_rcaex3$;
  var get_rad = $module$d2v_core_js.io.data2viz.math.get_rad_rcaex3$;
  var log = Kotlin.kotlin.math.log_lu1900$;
  var throwCCE = Kotlin.throwCCE;
  AzimuthalEqualArea.prototype = Object.create(Azimuthal.prototype);
  AzimuthalEqualArea.prototype.constructor = AzimuthalEqualArea;
  AzimuthalEquidistantProjection.prototype = Object.create(Azimuthal.prototype);
  AzimuthalEquidistantProjection.prototype.constructor = AzimuthalEquidistantProjection;
  ConicProjection.prototype = Object.create(MutableProjection.prototype);
  ConicProjection.prototype.constructor = ConicProjection;
  MercatorProjection.prototype = Object.create(MutableProjection.prototype);
  MercatorProjection.prototype.constructor = MercatorProjection;
  TransformRadians.prototype = Object.create(ModifiedStream.prototype);
  TransformRadians.prototype.constructor = TransformRadians;
  MutableProjection$transformRotate$lambda$ObjectLiteral.prototype = Object.create(ModifiedStream.prototype);
  MutableProjection$transformRotate$lambda$ObjectLiteral.prototype.constructor = MutableProjection$transformRotate$lambda$ObjectLiteral;
  MutableProjection$transformRadians$lambda$ObjectLiteral.prototype = Object.create(ModifiedStream.prototype);
  MutableProjection$transformRadians$lambda$ObjectLiteral.prototype.constructor = MutableProjection$transformRadians$lambda$ObjectLiteral;
  resampleNone$lambda$ObjectLiteral.prototype = Object.create(ModifiedStream.prototype);
  resampleNone$lambda$ObjectLiteral.prototype.constructor = resampleNone$lambda$ObjectLiteral;
  TransverseMercatorProjection.prototype = Object.create(MercatorProjection.prototype);
  TransverseMercatorProjection.prototype.constructor = TransverseMercatorProjection;
  var Math_0 = Math;
  function spherical(cartesian) {
    var tmp$ = Float64Array;
    var y = cartesian[1];
    var x = cartesian[0];
    var tmp$_0 = Math_0.atan2(y, x);
    var x_0 = cartesian[2];
    return new tmp$([tmp$_0, Math_0.asin(x_0)]);
  }
  function cartesian(spherical) {
    var lambda = spherical[0];
    var phi = spherical[1];
    var cosPhi = Math_0.cos(phi);
    return new Float64Array([cosPhi * Math_0.cos(lambda), cosPhi * Math_0.sin(lambda), Math_0.sin(phi)]);
  }
  function cartesianDot(a, b) {
    return a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
  }
  function cartesianCross(a, b) {
    return new Float64Array([a[1] * b[2] - a[2] * b[1], a[2] * b[0] - a[0] * b[2], a[0] * b[1] - a[1] * b[0]]);
  }
  function cartesianScale(vector, k) {
    return new Float64Array([vector[0] * k, vector[1] * k, vector[2] * k]);
  }
  function cartesianAdd(a, b) {
    a[0] = a[0] + b[0];
    a[1] = a[1] + b[1];
    a[2] = a[2] + b[2];
    return a;
  }
  function cartesianNormalize(d) {
    var x = d[0] * d[0] + d[1] * d[1] + d[2] * d[2];
    var l = Math_0.sqrt(x);
    d[0] = d[0] / l;
    d[1] = d[1] / l;
    d[2] = d[2] / l;
    return d;
  }
  function contains(geo, point) {
    return containsGeometry(geo, point);
  }
  function containsGeometry(geo, point) {
    if (Kotlin.isType(geo, Point))
      return containsPoint(geo.coordinates, point);
    else if (Kotlin.isType(geo, MultiPoint)) {
      var $receiver = geo.coordinates;
      var any$result;
      any$break: do {
        var tmp$;
        for (tmp$ = 0; tmp$ !== $receiver.length; ++tmp$) {
          var element = $receiver[tmp$];
          if (containsPoint(element, point)) {
            any$result = true;
            break any$break;
          }
        }
        any$result = false;
      }
       while (false);
      return any$result;
    }
     else if (Kotlin.isType(geo, Polygon))
      return containsPolygon(geo.coordinates, point);
    else if (Kotlin.isType(geo, MultiPolygon)) {
      var $receiver_0 = geo.coordinates;
      var any$result_0;
      any$break: do {
        var tmp$_0;
        for (tmp$_0 = 0; tmp$_0 !== $receiver_0.length; ++tmp$_0) {
          var element_0 = $receiver_0[tmp$_0];
          if (containsPolygon(element_0, point)) {
            any$result_0 = true;
            break any$break;
          }
        }
        any$result_0 = false;
      }
       while (false);
      return any$result_0;
    }
     else if (Kotlin.isType(geo, LineString))
      return containsLine(geo.coordinates, point);
    else if (Kotlin.isType(geo, MultiLineString)) {
      var $receiver_1 = geo.coordinates;
      var any$result_1;
      any$break: do {
        var tmp$_1;
        for (tmp$_1 = 0; tmp$_1 !== $receiver_1.length; ++tmp$_1) {
          var element_1 = $receiver_1[tmp$_1];
          if (containsLine(element_1, point)) {
            any$result_1 = true;
            break any$break;
          }
        }
        any$result_1 = false;
      }
       while (false);
      return any$result_1;
    }
     else if (Kotlin.isType(geo, Sphere))
      return true;
    else if (Kotlin.isType(geo, GeometryCollection)) {
      var $receiver_2 = geo.geometries;
      var any$result_2;
      any$break: do {
        var tmp$_2;
        for (tmp$_2 = 0; tmp$_2 !== $receiver_2.length; ++tmp$_2) {
          var element_2 = $receiver_2[tmp$_2];
          if (containsGeometry(element_2, point)) {
            any$result_2 = true;
            break any$break;
          }
        }
        any$result_2 = false;
      }
       while (false);
      return any$result_2;
    }
     else if (Kotlin.isType(geo, FeatureCollection)) {
      var $receiver_3 = geo.features;
      var any$result_3;
      any$break: do {
        var tmp$_3;
        for (tmp$_3 = 0; tmp$_3 !== $receiver_3.length; ++tmp$_3) {
          var element_3 = $receiver_3[tmp$_3];
          if (containsGeometry(element_3, point)) {
            any$result_3 = true;
            break any$break;
          }
        }
        any$result_3 = false;
      }
       while (false);
      return any$result_3;
    }
     else if (Kotlin.isType(geo, Feature))
      return containsGeometry(geo.geometry, point);
    else
      return false;
  }
  var ArrayList_init_0 = Kotlin.kotlin.collections.ArrayList_init_ww73n8$;
  function containsPolygon(coordinates, point) {
    var destination = ArrayList_init_0(coordinates.length);
    var tmp$;
    for (tmp$ = 0; tmp$ !== coordinates.length; ++tmp$) {
      var item = coordinates[tmp$];
      var tmp$_0 = destination.add_11rb$;
      var destination_0 = ArrayList_init_0(item.length);
      var tmp$_1;
      for (tmp$_1 = 0; tmp$_1 !== item.length; ++tmp$_1) {
        var item_0 = item[tmp$_1];
        destination_0.add_11rb$(toRadians_0(item_0));
      }
      tmp$_0.call(destination, destination_0);
    }
    var radiansCoordinates = destination;
    var coords = toMutableList(radiansCoordinates);
    coords.removeAt_za3lpa$(get_lastIndex(coords));
    return polygonContains(coords, toRadians_0(point));
  }
  function toRadians_0(array) {
    var destination = ArrayList_init_0(array.length);
    var tmp$;
    for (tmp$ = 0; tmp$ !== array.length; ++tmp$) {
      var item = array[tmp$];
      destination.add_11rb$(toRadians(item));
    }
    return toDoubleArray(destination);
  }
  function containsLine(coordinates, point) {
    var ab = geoDistance(coordinates[0], coordinates[1]);
    var ao = geoDistance(coordinates[0], point);
    var ob = geoDistance(point, coordinates[1]);
    return ao + ob <= ab + math.EPSILON;
  }
  function containsPoint(coordinates, point) {
    return geoDistance(coordinates, point) === 0.0;
  }
  function fitSize(projection, width, height, geo) {
    return fitExtent(projection, new Extent(0.0, 0.0, width, height), geo);
  }
  function fitHeight$lambda(closure$height, closure$projection) {
    return function (size) {
      var k = closure$height / size.height;
      var x = -k * size.x0;
      var y = (closure$height - k * (size.y1 + size.y0)) / 2;
      closure$projection.scale = k * 150;
      closure$projection.translate = new Float64Array([x, y]);
      return Unit;
    };
  }
  function fitHeight(projection, height, geo) {
    var fitBounds = fitHeight$lambda(height, projection);
    return fit(projection, fitBounds, geo);
  }
  function fitWidth$lambda(closure$width, closure$projection) {
    return function (size) {
      var k = closure$width / size.width;
      var x = (closure$width - k * (size.x1 + size.x0)) / 2;
      var y = -k * size.y0;
      closure$projection.scale = k * 150;
      closure$projection.translate = new Float64Array([x, y]);
      return Unit;
    };
  }
  function fitWidth(projection, width, geo) {
    var fitBounds = fitWidth$lambda(width, projection);
    return fit(projection, fitBounds, geo);
  }
  function fitExtent$lambda(closure$extent, closure$projection) {
    return function (size) {
      var w = closure$extent.width;
      var h = closure$extent.height;
      var a = w / size.width;
      var b = h / size.height;
      var k = Math_0.min(a, b);
      var x = closure$extent.x0 + (w - k * (size.x1 + size.x0)) / 2;
      var y = closure$extent.y0 + (h - k * (size.y1 + size.y0)) / 2;
      closure$projection.scale = k * 150;
      closure$projection.translate = new Float64Array([x, y]);
      return Unit;
    };
  }
  function fitExtent(projection, extent, geo) {
    var fitBounds = fitExtent$lambda(extent, projection);
    return fit(projection, fitBounds, geo);
  }
  function fit(projection, fitBounds, geo) {
    var clip = projection.clipExtent;
    projection.scale = 150.0;
    projection.translate = new Float64Array([0.0, 0.0]);
    if (clip != null)
      projection.clipExtent = null;
    var boundsStream = new PathBounds();
    stream(geo, projection.stream_k25lbv$(boundsStream));
    fitBounds(boundsStream.result());
    if (clip != null)
      projection.clipExtent = clip;
    return projection;
  }
  function GeoArea() {
    this.areaSum_0 = 0.0;
    this.areaRingSum_8be2vx$ = 0.0;
    this.lambda00_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.phi00_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.lambda0_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.phi0_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.cosPhi0_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.sinPhi0_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.currentPoint_0 = noop2;
    this.currentLineStart_0 = noop;
    this.currentLineEnd_0 = noop;
  }
  GeoArea.prototype.result_6ux19g$ = function (geo) {
    this.areaSum_0 = 0.0;
    stream(geo, this);
    return this.areaSum_0 * 2;
  };
  GeoArea.prototype.point_yvo9jy$ = function (x, y, z) {
    this.currentPoint_0(x, y);
  };
  GeoArea.prototype.lineStart = function () {
    this.currentLineStart_0();
  };
  GeoArea.prototype.lineEnd = function () {
    this.currentLineEnd_0();
  };
  GeoArea.prototype.polygonStart = function () {
    this.areaRingSum_8be2vx$ = 0.0;
    this.currentLineStart_0 = getCallableRef('areaRingStart', function ($receiver) {
      return $receiver.areaRingStart_0(), Unit;
    }.bind(null, this));
    this.currentLineEnd_0 = getCallableRef('areaRingEnd', function ($receiver) {
      return $receiver.areaRingEnd_0(), Unit;
    }.bind(null, this));
  };
  GeoArea.prototype.polygonEnd = function () {
    this.currentLineStart_0 = noop;
    this.currentLineEnd_0 = noop;
    this.currentPoint_0 = noop2;
    this.areaSum_0 += this.areaRingSum_8be2vx$ + (this.areaRingSum_8be2vx$ < 0 ? math.TAU : 0.0);
  };
  GeoArea.prototype.sphere = function () {
    this.areaSum_0 += math.TAU;
  };
  GeoArea.prototype.areaRingStart_0 = function () {
    this.currentPoint_0 = getCallableRef('areaPointFirst', function ($receiver, x, y) {
      return $receiver.areaPointFirst_0(x, y), Unit;
    }.bind(null, this));
  };
  GeoArea.prototype.areaPointFirst_0 = function (x, y) {
    this.currentPoint_0 = getCallableRef('areaPoint', function ($receiver, x, y) {
      return $receiver.areaPoint_0(x, y), Unit;
    }.bind(null, this));
    this.lambda00_0 = x;
    this.phi00_0 = y;
    this.lambda0_0 = toRadians(x);
    this.phi0_0 = toRadians(y);
    var phi = toRadians(y) / 2.0 + math.QUARTERPI;
    this.cosPhi0_0 = Math_0.cos(phi);
    this.sinPhi0_0 = Math_0.sin(phi);
  };
  GeoArea.prototype.areaPoint_0 = function (x, y) {
    var lambda = toRadians(x);
    var phi = toRadians(y) / 2.0 + math.QUARTERPI;
    var dLambda = lambda - this.lambda0_0;
    var sdLambda = dLambda >= 0.0 ? 1.0 : -1.0;
    var adLambda = sdLambda * dLambda;
    var cosPhi = Math_0.cos(phi);
    var sinPhi = Math_0.sin(phi);
    var k = this.sinPhi0_0 * sinPhi;
    var u = this.cosPhi0_0 * cosPhi + k * Math_0.cos(adLambda);
    var v = k * sdLambda * Math_0.sin(adLambda);
    this.areaRingSum_8be2vx$ += Math_0.atan2(v, u);
    this.lambda0_0 = lambda;
    this.cosPhi0_0 = cosPhi;
    this.sinPhi0_0 = sinPhi;
  };
  GeoArea.prototype.areaRingEnd_0 = function () {
    this.areaPoint_0(this.lambda00_0, this.phi00_0);
  };
  GeoArea.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'GeoArea',
    interfaces: [Stream]
  };
  function GeoBounds() {
    this.areaStream_0 = new GeoArea();
    this.lambda0_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.phi0_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.lambda1_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.phi1_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.lambda2_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.lambda00_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.phi00_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.p0_0 = null;
    this.deltaSum_0 = 0.0;
    this.range_0 = new Float64Array([kotlin_js_internal_DoubleCompanionObject.NaN, kotlin_js_internal_DoubleCompanionObject.NaN]);
    this.ranges_0 = ArrayList_init();
    this.currentPoint_0 = getCallableRef('boundsPoint', function ($receiver, x, y) {
      return $receiver.boundsPoint_0(x, y), Unit;
    }.bind(null, this));
    this.currentLineStart_0 = getCallableRef('boundsLineStart', function ($receiver) {
      return $receiver.boundsLineStart_0(), Unit;
    }.bind(null, this));
    this.currentLineEnd_0 = getCallableRef('boundsLineEnd', function ($receiver) {
      return $receiver.boundsLineEnd_0(), Unit;
    }.bind(null, this));
  }
  function GeoBounds$result$lambda(it) {
    return it[0];
  }
  var wrapFunction = Kotlin.wrapFunction;
  var compareBy$lambda = wrapFunction(function () {
    var compareValues = Kotlin.kotlin.comparisons.compareValues_s00gnj$;
    return function (closure$selector) {
      return function (a, b) {
        var selector = closure$selector;
        return compareValues(selector(a), selector(b));
      };
    };
  });
  var Comparator = Kotlin.kotlin.Comparator;
  function Comparator$ObjectLiteral(closure$comparison) {
    this.closure$comparison = closure$comparison;
  }
  Comparator$ObjectLiteral.prototype.compare = function (a, b) {
    return this.closure$comparison(a, b);
  };
  Comparator$ObjectLiteral.$metadata$ = {kind: Kind_CLASS, interfaces: [Comparator]};
  GeoBounds.prototype.result_6ux19g$ = function (geo) {
    this.phi0_0 = kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY;
    this.lambda0_0 = this.phi0_0;
    this.phi1_0 = -this.lambda0_0;
    this.lambda1_0 = this.phi1_0;
    this.ranges_0.clear();
    stream(geo, this);
    if (!this.ranges_0.isEmpty()) {
      var $receiver = this.ranges_0;
      if ($receiver.size > 1) {
        sortWith($receiver, new Comparator$ObjectLiteral(compareBy$lambda(GeoBounds$result$lambda)));
      }
      var a = {v: this.ranges_0.get_za3lpa$(0)};
      var merged = mutableListOf([a.v]);
      var tmp$;
      tmp$ = (new IntRange(1, get_lastIndex(this.ranges_0))).iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        var b = this.ranges_0.get_za3lpa$(element);
        if (this.rangeContains_0(a.v, b[0]) || this.rangeContains_0(a.v, b[1])) {
          if (this.angle_0(a.v[0], b[1]) > this.angle_0(a.v[0], a.v[1]))
            a.v[1] = b[1];
          if (this.angle_0(b[0], a.v[1]) > this.angle_0(a.v[0], a.v[1]))
            a.v[0] = b[0];
        }
         else {
          a.v = b;
          merged.add_11rb$(a.v);
        }
      }
      var deltaMax = {v: kotlin_js_internal_DoubleCompanionObject.NEGATIVE_INFINITY};
      a.v = last(merged);
      var tmp$_0;
      tmp$_0 = (new IntRange(0, get_lastIndex(merged))).iterator();
      while (tmp$_0.hasNext()) {
        var element_0 = tmp$_0.next();
        var b_0 = merged.get_za3lpa$(element_0);
        var delta = this.angle_0(a.v[1], b_0[0]);
        if (delta > deltaMax.v) {
          deltaMax.v = delta;
          this.lambda0_0 = b_0[0];
          this.lambda1_0 = a.v[1];
        }
        a.v = b_0;
      }
    }
    this.ranges_0.clear();
    return this.lambda0_0 === kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY || this.phi0_0 === kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY ? new Extent(kotlin_js_internal_DoubleCompanionObject.NaN, kotlin_js_internal_DoubleCompanionObject.NaN, kotlin_js_internal_DoubleCompanionObject.NaN, kotlin_js_internal_DoubleCompanionObject.NaN) : new Extent(this.lambda0_0, this.phi0_0, this.lambda1_0, this.phi1_0);
  };
  GeoBounds.prototype.point_yvo9jy$ = function (x, y, z) {
    this.currentPoint_0(x, y);
  };
  GeoBounds.prototype.lineStart = function () {
    this.currentLineStart_0();
  };
  GeoBounds.prototype.lineEnd = function () {
    this.currentLineEnd_0();
  };
  GeoBounds.prototype.polygonStart = function () {
    this.currentPoint_0 = getCallableRef('boundsRingPoint', function ($receiver, x, y) {
      return $receiver.boundsRingPoint_0(x, y), Unit;
    }.bind(null, this));
    this.currentLineStart_0 = getCallableRef('boundsRingStart', function ($receiver) {
      return $receiver.boundsRingStart_0(), Unit;
    }.bind(null, this));
    this.currentLineEnd_0 = getCallableRef('boundsRingEnd', function ($receiver) {
      return $receiver.boundsRingEnd_0(), Unit;
    }.bind(null, this));
    this.deltaSum_0 = 0.0;
    this.areaStream_0.polygonStart();
  };
  GeoBounds.prototype.polygonEnd = function () {
    this.areaStream_0.polygonEnd();
    this.currentPoint_0 = getCallableRef('boundsPoint', function ($receiver, x, y) {
      return $receiver.boundsPoint_0(x, y), Unit;
    }.bind(null, this));
    this.currentLineStart_0 = getCallableRef('boundsLineStart', function ($receiver) {
      return $receiver.boundsLineStart_0(), Unit;
    }.bind(null, this));
    this.currentLineEnd_0 = getCallableRef('boundsLineEnd', function ($receiver) {
      return $receiver.boundsLineEnd_0(), Unit;
    }.bind(null, this));
    if (this.areaStream_0.areaRingSum_8be2vx$ < 0) {
      this.lambda0_0 = -180.0;
      this.lambda1_0 = 180.0;
      this.phi0_0 = -90.0;
      this.phi1_0 = 90.0;
    }
     else if (this.deltaSum_0 > math.EPSILON)
      this.phi1_0 = 90.0;
    else if (this.deltaSum_0 < -math.EPSILON)
      this.phi0_0 = -90.0;
    this.range_0[0] = this.lambda0_0;
    this.range_0[1] = this.lambda1_0;
  };
  GeoBounds.prototype.rangeContains_0 = function (range, x) {
    return range[0] <= range[1] ? range[0] <= x && x <= range[1] : x < range[0] || range[1] < x;
  };
  GeoBounds.prototype.boundsPoint_0 = function (x, y) {
    this.lambda0_0 = x;
    this.lambda1_0 = x;
    this.range_0 = new Float64Array([this.lambda0_0, this.lambda1_0]);
    this.ranges_0.add_11rb$(this.range_0);
    if (y < this.phi0_0)
      this.phi0_0 = y;
    if (y > this.phi1_0)
      this.phi1_0 = y;
  };
  GeoBounds.prototype.linePoint_0 = function (x, y) {
    var p = cartesian(new Float64Array([toRadians(x), toRadians(y)]));
    if (this.p0_0 != null) {
      var normal = cartesianCross(ensureNotNull(this.p0_0), p);
      var equatorial = new Float64Array([normal[1], -normal[0], 0.0]);
      var inflection = cartesianCross(equatorial, normal);
      inflection = cartesianNormalize(inflection);
      inflection = spherical(inflection);
      var delta = x - this.lambda2_0;
      var sign = delta > 0.0 ? 1 : -1;
      var lambdai = toDegrees(inflection[0]) * sign;
      var phii;
      var antimeridian = Math_0.abs(delta) > 180.0;
      if (antimeridian ^ (sign * this.lambda2_0 < lambdai && lambdai < sign * x)) {
        phii = toDegrees(inflection[1]);
        if (phii > this.phi1_0)
          this.phi1_0 = phii;
      }
       else {
        lambdai = (lambdai + 360.0) % 360.0 - 180.0;
        if (antimeridian ^ (sign * this.lambda2_0 < lambdai && lambdai < sign * x)) {
          phii = -toDegrees(inflection[1]);
          if (phii < this.phi0_0)
            this.phi0_0 = phii;
        }
         else {
          if (y < this.phi0_0)
            this.phi0_0 = y;
          if (y > this.phi1_0)
            this.phi1_0 = y;
        }
      }
      if (antimeridian) {
        if (x < this.lambda2_0) {
          if (this.angle_0(this.lambda0_0, x) > this.angle_0(this.lambda0_0, this.lambda1_0))
            this.lambda1_0 = x;
        }
         else {
          if (this.angle_0(x, this.lambda1_0) > this.angle_0(this.lambda0_0, this.lambda1_0))
            this.lambda0_0 = x;
        }
      }
       else {
        if (this.lambda1_0 >= this.lambda0_0) {
          if (x < this.lambda0_0)
            this.lambda0_0 = x;
          if (x > this.lambda1_0)
            this.lambda1_0 = x;
        }
         else {
          if (x > this.lambda2_0) {
            if (this.angle_0(this.lambda0_0, x) > this.angle_0(this.lambda0_0, this.lambda1_0))
              this.lambda1_0 = x;
          }
           else {
            if (this.angle_0(x, this.lambda1_0) > this.angle_0(this.lambda0_0, this.lambda1_0))
              this.lambda0_0 = x;
          }
        }
      }
    }
     else {
      this.lambda0_0 = x;
      this.lambda1_0 = x;
      this.range_0 = new Float64Array([this.lambda0_0, this.lambda1_0]);
      this.ranges_0.add_11rb$(this.range_0);
    }
    if (y < this.phi0_0)
      this.phi0_0 = y;
    if (y > this.phi1_0)
      this.phi1_0 = y;
    this.p0_0 = p;
    this.lambda2_0 = x;
  };
  GeoBounds.prototype.boundsLineStart_0 = function () {
    this.currentPoint_0 = getCallableRef('linePoint', function ($receiver, x, y) {
      return $receiver.linePoint_0(x, y), Unit;
    }.bind(null, this));
  };
  GeoBounds.prototype.boundsLineEnd_0 = function () {
    this.range_0[0] = this.lambda0_0;
    this.range_0[1] = this.lambda1_0;
    this.currentPoint_0 = getCallableRef('boundsPoint', function ($receiver, x, y) {
      return $receiver.boundsPoint_0(x, y), Unit;
    }.bind(null, this));
    this.p0_0 = null;
  };
  GeoBounds.prototype.boundsRingPoint_0 = function (x, y) {
    var tmp$, tmp$_0;
    if (this.p0_0 != null) {
      var delta = x - this.lambda2_0;
      tmp$_0 = this.deltaSum_0;
      if (Math_0.abs(delta) > 180.0) {
        tmp$ = delta + (delta > 0 ? 360.0 : -360.0);
      }
       else
        tmp$ = delta;
      this.deltaSum_0 = tmp$_0 + tmp$;
    }
     else {
      this.lambda00_0 = x;
      this.phi00_0 = y;
    }
    this.areaStream_0.point_yvo9jy$(x, y, 0.0);
    this.linePoint_0(x, y);
  };
  GeoBounds.prototype.boundsRingStart_0 = function () {
    this.areaStream_0.lineStart();
  };
  GeoBounds.prototype.boundsRingEnd_0 = function () {
    this.boundsRingPoint_0(this.lambda00_0, this.phi00_0);
    this.areaStream_0.lineEnd();
    var x = this.deltaSum_0;
    if (Math_0.abs(x) > math.EPSILON) {
      this.lambda1_0 = 180.0;
      this.lambda0_0 = -180.0;
    }
    this.range_0[0] = this.lambda0_0;
    this.range_0[1] = this.lambda1_0;
    this.p0_0 = null;
  };
  GeoBounds.prototype.angle_0 = function (lambda0, lambda1) {
    var diff = lambda1 - lambda0;
    return diff < 0.0 ? diff + 360.0 : diff;
  };
  GeoBounds.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'GeoBounds',
    interfaces: [Stream]
  };
  function GeoCentroid() {
    this._W0_0 = 0.0;
    this._W1_0 = 0.0;
    this._X0_0 = 0.0;
    this._Y0_0 = 0.0;
    this._Z0_0 = 0.0;
    this._X1_0 = 0.0;
    this._Y1_0 = 0.0;
    this._Z1_0 = 0.0;
    this._X2_0 = 0.0;
    this._Y2_0 = 0.0;
    this._Z2_0 = 0.0;
    this.lambda00_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.phi00_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.x0_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.y0_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.z0_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.currentPoint_0 = getCallableRef('centroidPoint', function ($receiver, x, y) {
      return $receiver.centroidPoint_0(x, y), Unit;
    }.bind(null, this));
    this.currentLineStart_0 = getCallableRef('centroidLineStart', function ($receiver) {
      return $receiver.centroidLineStart_0(), Unit;
    }.bind(null, this));
    this.currentLineEnd_0 = getCallableRef('centroidLineEnd', function ($receiver) {
      return $receiver.centroidLineEnd_0(), Unit;
    }.bind(null, this));
  }
  GeoCentroid.prototype.result_6ux19g$ = function (geo) {
    this._W0_0 = 0.0;
    this._W1_0 = 0.0;
    this._X0_0 = 0.0;
    this._Y0_0 = 0.0;
    this._Z0_0 = 0.0;
    this._X1_0 = 0.0;
    this._Y1_0 = 0.0;
    this._Z1_0 = 0.0;
    this._X2_0 = 0.0;
    this._Y2_0 = 0.0;
    this._Z2_0 = 0.0;
    stream(geo, this);
    var x = this._X2_0;
    var y = this._Y2_0;
    var z = this._Z2_0;
    var m = x * x + y * y + z * z;
    if (m < math.EPSILON2) {
      x = this._X1_0;
      y = this._Y1_0;
      z = this._Z1_0;
      if (this._W1_0 < math.EPSILON) {
        x = this._X0_0;
        y = this._Y0_0;
        z = this._Z0_0;
      }
      m = x * x + y * y + z * z;
      if (m < math.EPSILON2)
        return new Float64Array([kotlin_js_internal_DoubleCompanionObject.NaN, kotlin_js_internal_DoubleCompanionObject.NaN]);
    }
    var tmp$ = Float64Array;
    var y_0 = y;
    var x_0 = x;
    var tmp$_0 = toDegrees(Math_0.atan2(y_0, x_0));
    var tmp$_1 = z;
    var x_1 = m;
    var x_2 = tmp$_1 / Math_0.sqrt(x_1);
    return new tmp$([tmp$_0, toDegrees(Math_0.asin(x_2))]);
  };
  GeoCentroid.prototype.point_yvo9jy$ = function (x, y, z) {
    this.currentPoint_0(x, y);
  };
  GeoCentroid.prototype.lineStart = function () {
    this.currentLineStart_0();
  };
  GeoCentroid.prototype.lineEnd = function () {
    this.currentLineEnd_0();
  };
  GeoCentroid.prototype.polygonStart = function () {
    this.currentLineStart_0 = getCallableRef('centroidRingStart', function ($receiver) {
      return $receiver.centroidRingStart_0(), Unit;
    }.bind(null, this));
    this.currentLineEnd_0 = getCallableRef('centroidRingEnd', function ($receiver) {
      return $receiver.centroidRingEnd_0(), Unit;
    }.bind(null, this));
  };
  GeoCentroid.prototype.polygonEnd = function () {
    this.currentLineStart_0 = getCallableRef('centroidLineStart', function ($receiver) {
      return $receiver.centroidLineStart_0(), Unit;
    }.bind(null, this));
    this.currentLineEnd_0 = getCallableRef('centroidLineEnd', function ($receiver) {
      return $receiver.centroidLineEnd_0(), Unit;
    }.bind(null, this));
  };
  GeoCentroid.prototype.centroidPoint_0 = function (x, y) {
    var lambda = toRadians(x);
    var phi = toRadians(y);
    var cosPhi = Math_0.cos(phi);
    this.centroidPointCartesian_0(cosPhi * Math_0.cos(lambda), cosPhi * Math_0.sin(lambda), Math_0.sin(phi));
  };
  GeoCentroid.prototype.centroidPointCartesian_0 = function (x, y, z) {
    this._W0_0 = this._W0_0 + 1;
    this._X0_0 += (x - this._X0_0) / this._W0_0;
    this._Y0_0 += (y - this._Y0_0) / this._W0_0;
    this._Z0_0 += (z - this._Z0_0) / this._W0_0;
  };
  GeoCentroid.prototype.centroidLineStart_0 = function () {
    this.currentPoint_0 = getCallableRef('centroidLinePointFirst', function ($receiver, x, y) {
      return $receiver.centroidLinePointFirst_0(x, y), Unit;
    }.bind(null, this));
  };
  GeoCentroid.prototype.centroidLinePointFirst_0 = function (x, y) {
    var lambda = toRadians(x);
    var phi = toRadians(y);
    var cosPhi = Math_0.cos(phi);
    this.x0_0 = cosPhi * Math_0.cos(lambda);
    this.y0_0 = cosPhi * Math_0.sin(lambda);
    this.z0_0 = Math_0.sin(phi);
    this.currentPoint_0 = getCallableRef('centroidLinePoint', function ($receiver, x, y) {
      return $receiver.centroidLinePoint_0(x, y), Unit;
    }.bind(null, this));
    this.centroidPointCartesian_0(this.x0_0, this.y0_0, this.z0_0);
  };
  GeoCentroid.prototype.centroidLinePoint_0 = function (x, y) {
    var lambda = toRadians(x);
    var phi = toRadians(y);
    var cosPhi = Math_0.cos(phi);
    var a = cosPhi * Math_0.cos(lambda);
    var b = cosPhi * Math_0.sin(lambda);
    var c = Math_0.sin(phi);
    var w1 = this.y0_0 * c - this.z0_0 * b;
    var w2 = this.z0_0 * a - this.x0_0 * c;
    var w3 = this.x0_0 * b - this.y0_0 * a;
    var x_0 = w1 * w1 + w2 * w2 + w3 * w3;
    var y_0 = Math_0.sqrt(x_0);
    var x_1 = this.x0_0 * a + this.y0_0 * b + this.z0_0 * c;
    var w = Math_0.atan2(y_0, x_1);
    this._W1_0 += w;
    this._X1_0 += w * (this.x0_0 + a);
    this.x0_0 = a;
    this._Y1_0 += w * (this.y0_0 + b);
    this.y0_0 = b;
    this._Z1_0 += w * (this.z0_0 + c);
    this.z0_0 = c;
    this.centroidPointCartesian_0(this.x0_0, this.y0_0, this.z0_0);
  };
  GeoCentroid.prototype.centroidLineEnd_0 = function () {
    this.currentPoint_0 = getCallableRef('centroidPoint', function ($receiver, x, y) {
      return $receiver.centroidPoint_0(x, y), Unit;
    }.bind(null, this));
  };
  GeoCentroid.prototype.centroidRingStart_0 = function () {
    this.currentPoint_0 = getCallableRef('centroidRingPointFirst', function ($receiver, x, y) {
      return $receiver.centroidRingPointFirst_0(x, y), Unit;
    }.bind(null, this));
  };
  GeoCentroid.prototype.centroidRingEnd_0 = function () {
    this.centroidRingPoint_0(this.lambda00_0, this.phi00_0);
    this.currentPoint_0 = getCallableRef('centroidPoint', function ($receiver, x, y) {
      return $receiver.centroidPoint_0(x, y), Unit;
    }.bind(null, this));
  };
  GeoCentroid.prototype.centroidRingPointFirst_0 = function (x, y) {
    this.lambda00_0 = x;
    this.phi00_0 = y;
    var lambda = toRadians(x);
    var phi = toRadians(y);
    var cosPhi = Math_0.cos(phi);
    this.currentPoint_0 = getCallableRef('centroidRingPoint', function ($receiver, x, y) {
      return $receiver.centroidRingPoint_0(x, y), Unit;
    }.bind(null, this));
    this.x0_0 = cosPhi * Math_0.cos(lambda);
    this.y0_0 = cosPhi * Math_0.sin(lambda);
    this.z0_0 = Math_0.sin(phi);
    this.centroidPointCartesian_0(this.x0_0, this.y0_0, this.z0_0);
  };
  GeoCentroid.prototype.centroidRingPoint_0 = function (x, y) {
    var lambda = toRadians(x);
    var phi = toRadians(y);
    var cosPhi = Math_0.cos(phi);
    var a = cosPhi * Math_0.cos(lambda);
    var b = cosPhi * Math_0.sin(lambda);
    var c = Math_0.sin(phi);
    var cx = this.y0_0 * c - this.z0_0 * b;
    var cy = this.z0_0 * a - this.x0_0 * c;
    var cz = this.x0_0 * b - this.y0_0 * a;
    var x_0 = cx * cx + cy * cy + cz * cz;
    var m = Math_0.sqrt(x_0);
    var w = Math_0.asin(m);
    var v = m === 0.0 ? 0.0 : -w / m;
    this._X2_0 += v * cx;
    this._Y2_0 += v * cy;
    this._Z2_0 += v * cz;
    this._W1_0 += w;
    this._X1_0 += w * (this.x0_0 + a);
    this.x0_0 = a;
    this._Y1_0 += w * (this.y0_0 + b);
    this.y0_0 = b;
    this._Z1_0 += w * (this.z0_0 + c);
    this.z0_0 = c;
    this.centroidPointCartesian_0(this.x0_0, this.y0_0, this.z0_0);
  };
  GeoCentroid.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'GeoCentroid',
    interfaces: [Stream]
  };
  function GeoCircle() {
    this.ring_0 = ArrayList_init();
    this.rotate_0 = null;
    this.circleStream_0 = new GeoCircle$circleStream$ObjectLiteral(this);
    this.center = GeoCircle$center$lambda;
    this.radius = GeoCircle$radius$lambda;
    this.precision = GeoCircle$precision$lambda;
  }
  var copyToArray = Kotlin.kotlin.collections.copyToArray;
  GeoCircle.prototype.circle_11rb$ = function (data) {
    if (data === void 0)
      data = null;
    var $receiver = this.center(data);
    var destination = ArrayList_init_0($receiver.length);
    var tmp$;
    for (tmp$ = 0; tmp$ !== $receiver.length; ++tmp$) {
      var item = $receiver[tmp$];
      destination.add_11rb$(-toRadians(item));
    }
    var c = toDoubleArray(destination);
    var r = toRadians(this.radius(data));
    var p = toRadians(this.precision(data));
    this.rotate_0 = getCallableRef('invert', function ($receiver, x, y) {
      return $receiver.invert_lu1900$(x, y);
    }.bind(null, rotateRadians(c[0], c[1], 0.0)));
    geoCircle(this.circleStream_0, r, p, 1);
    var result = new Polygon([copyToArray(this.ring_0)]);
    this.ring_0.clear();
    this.rotate_0 = null;
    return result;
  };
  function GeoCircle$circleStream$ObjectLiteral(this$GeoCircle) {
    this.this$GeoCircle = this$GeoCircle;
  }
  GeoCircle$circleStream$ObjectLiteral.prototype.point_yvo9jy$ = function (x, y, z) {
    var value = ensureNotNull(this.this$GeoCircle.rotate_0)(x, y);
    this.this$GeoCircle.ring_0.add_11rb$([toDegrees(value[0]), toDegrees(value[1])]);
  };
  GeoCircle$circleStream$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [Stream]
  };
  function GeoCircle$center$lambda(it) {
    return new Float64Array([0.0, 0.0]);
  }
  function GeoCircle$radius$lambda(it) {
    return 90.0;
  }
  function GeoCircle$precision$lambda(it) {
    return 6.0;
  }
  GeoCircle.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'GeoCircle',
    interfaces: []
  };
  function geoCircle(stream, radius, delta, direction, t0, t1) {
    if (t0 === void 0)
      t0 = null;
    if (t1 === void 0)
      t1 = null;
    if (delta === 0.0)
      return;
    var cosRadius = Math_0.cos(radius);
    var sinRadius = Math_0.sin(radius);
    var step = direction * delta;
    var newT0;
    var newT1;
    if (t0 == null) {
      newT0 = radius + direction * math.TAU;
      newT1 = radius - step / 2;
    }
     else {
      newT0 = circleRadius(cosRadius, t0);
      newT1 = circleRadius(cosRadius, ensureNotNull(t1));
      if (direction > 0 && newT0 < newT1 || (direction < 0 && newT0 > newT1)) {
        newT0 += direction * math.TAU;
      }
    }
    var t = newT0;
    while (direction > 0 ? t > newT1 : t < newT1) {
      var cartesian0 = cosRadius;
      var cartesian1 = -sinRadius * Math_0.cos(t);
      var cartesian2 = -sinRadius * Math_0.sin(t);
      var spher0 = Math_0.atan2(cartesian1, cartesian0);
      var spher1 = Math_0.asin(cartesian2);
      stream.point_yvo9jy$(spher0, spher1, 0.0);
      t -= step;
    }
  }
  function circleRadius(cosRadius, point) {
    var p = cartesian(point);
    p[0] = p[0] - cosRadius;
    p = cartesianNormalize(p);
    var x = -p[1];
    var radius = Math_0.acos(x);
    return ((-p[2] < 0 ? -radius : radius) + math.TAU - math.EPSILON) % math.TAU;
  }
  function GeoInterpolate(distance, k, kx0, kx1, ky0, ky1, sy0, sy1, x0, y0) {
    this.distance = distance;
    this.k_0 = k;
    this.kx0_0 = kx0;
    this.kx1_0 = kx1;
    this.ky0_0 = ky0;
    this.ky1_0 = ky1;
    this.sy0_0 = sy0;
    this.sy1_0 = sy1;
    this.x0_0 = x0;
    this.y0_0 = y0;
    this.interpolate = this.distance !== 0.0 ? GeoInterpolate$interpolate$lambda(this) : GeoInterpolate$interpolate$lambda_0(this);
  }
  GeoInterpolate.prototype.invoke_14dthe$ = function (t) {
    return this.interpolate(t);
  };
  function GeoInterpolate$interpolate$lambda(this$GeoInterpolate) {
    return function (t) {
      var td = t * this$GeoInterpolate.distance;
      var B = Math_0.sin(td) / this$GeoInterpolate.k_0;
      var x = this$GeoInterpolate.distance - td;
      var A = Math_0.sin(x) / this$GeoInterpolate.k_0;
      var x_0 = A * this$GeoInterpolate.kx0_0 + B * this$GeoInterpolate.kx1_0;
      var y = A * this$GeoInterpolate.ky0_0 + B * this$GeoInterpolate.ky1_0;
      var z = A * this$GeoInterpolate.sy0_0 + B * this$GeoInterpolate.sy1_0;
      var tmp$ = Float64Array;
      var tmp$_0 = toDegrees(Math_0.atan2(y, x_0));
      var x_1 = x_0 * x_0 + y * y;
      var x_2 = Math_0.sqrt(x_1);
      return new tmp$([tmp$_0, toDegrees(Math_0.atan2(z, x_2))]);
    };
  }
  function GeoInterpolate$interpolate$lambda_0(this$GeoInterpolate) {
    return function (f) {
      return new Float64Array([toDegrees(this$GeoInterpolate.x0_0), toDegrees(this$GeoInterpolate.y0_0)]);
    };
  }
  GeoInterpolate.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'GeoInterpolate',
    interfaces: []
  };
  function haversin(x) {
    var x_0 = x / 2;
    var y = Math_0.sin(x_0);
    return y * y;
  }
  function geoInterpolate(a, b) {
    var x0 = toRadians(a[0]);
    var y0 = toRadians(a[1]);
    var x1 = toRadians(b[0]);
    var y1 = toRadians(b[1]);
    var cy0 = Math_0.cos(y0);
    var sy0 = Math_0.sin(y0);
    var cy1 = Math_0.cos(y1);
    var sy1 = Math_0.sin(y1);
    var kx0 = cy0 * Math_0.cos(x0);
    var ky0 = cy0 * Math_0.sin(x0);
    var kx1 = cy1 * Math_0.cos(x1);
    var ky1 = cy1 * Math_0.sin(x1);
    var x = haversin(y1 - y0) + cy0 * cy1 * haversin(x1 - x0);
    var x_0 = Math_0.sqrt(x);
    var d = 2 * Math_0.asin(x_0);
    var k = Math_0.sin(d);
    return new GeoInterpolate(d, k, kx0, kx1, ky0, ky1, sy0, sy1, x0, y0);
  }
  function Sphere() {
  }
  Sphere.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Sphere',
    interfaces: [Geometry]
  };
  function geoDistance(from, to) {
    var line = new LineString([from, to]);
    return (new GeoLength()).result_6ux19g$(line);
  }
  function GeoLength() {
    this.lengthSum_0 = 0.0;
    this.lambda0_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.cosPhi0_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.sinPhi0_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.currentPoint_0 = noop2;
    this.currentLineEnd_0 = noop;
  }
  GeoLength.prototype.result_6ux19g$ = function (geo) {
    this.lengthSum_0 = 0.0;
    stream(geo, this);
    return this.lengthSum_0;
  };
  GeoLength.prototype.point_yvo9jy$ = function (x, y, z) {
    this.currentPoint_0(x, y);
  };
  GeoLength.prototype.lineStart = function () {
    this.currentPoint_0 = getCallableRef('lengthPointFirst', function ($receiver, x, y) {
      return $receiver.lengthPointFirst_0(x, y), Unit;
    }.bind(null, this));
    this.currentLineEnd_0 = getCallableRef('lengthLineEnd', function ($receiver) {
      return $receiver.lengthLineEnd_0(), Unit;
    }.bind(null, this));
  };
  GeoLength.prototype.lineEnd = function () {
    this.currentLineEnd_0();
  };
  GeoLength.prototype.lengthPointFirst_0 = function (x, y) {
    var lambda = toRadians(x);
    var phi = toRadians(y);
    this.lambda0_0 = lambda;
    this.sinPhi0_0 = Math_0.sin(phi);
    this.cosPhi0_0 = Math_0.cos(phi);
    this.currentPoint_0 = getCallableRef('lengthPoint', function ($receiver, x, y) {
      return $receiver.lengthPoint_0(x, y), Unit;
    }.bind(null, this));
  };
  GeoLength.prototype.lengthLineEnd_0 = function () {
    this.currentPoint_0 = noop2;
    this.currentLineEnd_0 = noop;
  };
  GeoLength.prototype.lengthPoint_0 = function (x, y) {
    var lambda = toRadians(x);
    var phi = toRadians(y);
    var sinPhi = Math_0.sin(phi);
    var cosPhi = Math_0.cos(phi);
    var x_0 = lambda - this.lambda0_0;
    var delta = Math_0.abs(x_0);
    var cosDelta = Math_0.cos(delta);
    var sinDelta = Math_0.sin(delta);
    var a = cosPhi * sinDelta;
    var b = this.cosPhi0_0 * sinPhi - this.sinPhi0_0 * cosPhi * cosDelta;
    var c = this.sinPhi0_0 * sinPhi + this.cosPhi0_0 * cosPhi * cosDelta;
    var x_1 = a * a + b * b;
    var y_0 = Math_0.sqrt(x_1);
    this.lengthSum_0 += Math_0.atan2(y_0, c);
    this.lambda0_0 = lambda;
    this.sinPhi0_0 = sinPhi;
    this.cosPhi0_0 = cosPhi;
  };
  GeoLength.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'GeoLength',
    interfaces: [Stream]
  };
  function geoGraticule$lambda($receiver) {
    return Unit;
  }
  function geoGraticule() {
    return geoGraticule_0(geoGraticule$lambda);
  }
  function geoGraticule_0(init) {
    var g = new Graticule();
    g.extentMajor = new Extent(-180.0, -90.0 + math.EPSILON, 180.0, 90.0 - math.EPSILON);
    g.extentMinor = new Extent(-180.0, -80.0 - math.EPSILON, 180.0, 80.0 + math.EPSILON);
    init(g);
    return g;
  }
  function reorderExtent(extent) {
    if (extent.x0 > extent.x1) {
      var t = extent.x0;
      extent.x0 = extent.x1;
      extent.x1 = t;
    }
    if (extent.y0 > extent.y1) {
      var t_0 = extent.y0;
      extent.y0 = extent.y1;
      extent.y1 = t_0;
    }
  }
  function Graticule() {
    this.minorExtent_0 = new Extent(kotlin_js_internal_DoubleCompanionObject.NaN, kotlin_js_internal_DoubleCompanionObject.NaN, kotlin_js_internal_DoubleCompanionObject.NaN, kotlin_js_internal_DoubleCompanionObject.NaN);
    this.majorExtent_0 = new Extent(kotlin_js_internal_DoubleCompanionObject.NaN, kotlin_js_internal_DoubleCompanionObject.NaN, kotlin_js_internal_DoubleCompanionObject.NaN, kotlin_js_internal_DoubleCompanionObject.NaN);
    this.minorStepX_0 = 10.0;
    this.minorStepY_0 = 10.0;
    this.majorStepX_0 = 90.0;
    this.majorStepY_0 = 360.0;
    this.minorX_ayzyzp$_0 = this.minorX_ayzyzp$_0;
    this.minorY_ayzyyu$_0 = this.minorY_ayzyyu$_0;
    this.majorX_etk4ip$_0 = this.majorX_etk4ip$_0;
    this.majorY_etk4hu$_0 = this.majorY_etk4hu$_0;
    this.precision_3km248$_0 = 2.5;
  }
  Object.defineProperty(Graticule.prototype, 'minorX_0', {
    get: function () {
      if (this.minorX_ayzyzp$_0 == null)
        return throwUPAE('minorX');
      return this.minorX_ayzyzp$_0;
    },
    set: function (minorX) {
      this.minorX_ayzyzp$_0 = minorX;
    }
  });
  Object.defineProperty(Graticule.prototype, 'minorY_0', {
    get: function () {
      if (this.minorY_ayzyyu$_0 == null)
        return throwUPAE('minorY');
      return this.minorY_ayzyyu$_0;
    },
    set: function (minorY) {
      this.minorY_ayzyyu$_0 = minorY;
    }
  });
  Object.defineProperty(Graticule.prototype, 'majorX_0', {
    get: function () {
      if (this.majorX_etk4ip$_0 == null)
        return throwUPAE('majorX');
      return this.majorX_etk4ip$_0;
    },
    set: function (majorX) {
      this.majorX_etk4ip$_0 = majorX;
    }
  });
  Object.defineProperty(Graticule.prototype, 'majorY_0', {
    get: function () {
      if (this.majorY_etk4hu$_0 == null)
        return throwUPAE('majorY');
      return this.majorY_etk4hu$_0;
    },
    set: function (majorY) {
      this.majorY_etk4hu$_0 = majorY;
    }
  });
  Object.defineProperty(Graticule.prototype, 'precision', {
    get: function () {
      return this.precision_3km248$_0;
    },
    set: function (value) {
      this.precision_3km248$_0 = value;
      this.minorX_0 = this.graticuleX_0(this.minorExtent_0.y0, this.minorExtent_0.y1, 90.0);
      this.minorY_0 = this.graticuleY_0(this.minorExtent_0.x0, this.minorExtent_0.x1, this.precision);
      this.majorX_0 = this.graticuleX_0(this.majorExtent_0.y0, this.majorExtent_0.y1, 90.0);
      this.majorY_0 = this.graticuleY_0(this.majorExtent_0.x0, this.majorExtent_0.x1, this.precision);
    }
  });
  Object.defineProperty(Graticule.prototype, 'extent', {
    get: function () {
      return this.minorExtent_0;
    },
    set: function (value) {
      this.extentMajor = value;
      this.extentMinor = value;
    }
  });
  Object.defineProperty(Graticule.prototype, 'extentMajor', {
    get: function () {
      return this.majorExtent_0;
    },
    set: function (value) {
      this.majorExtent_0 = value;
      reorderExtent(this.majorExtent_0);
      this.precision = this.precision;
    }
  });
  Object.defineProperty(Graticule.prototype, 'extentMinor', {
    get: function () {
      return this.minorExtent_0;
    },
    set: function (value) {
      this.minorExtent_0 = value;
      reorderExtent(this.minorExtent_0);
      this.precision = this.precision;
    }
  });
  Object.defineProperty(Graticule.prototype, 'stepMajor', {
    get: function () {
      return new Float64Array([this.majorStepX_0, this.majorStepY_0]);
    },
    set: function (value) {
      this.majorStepX_0 = value[0];
      this.majorStepY_0 = value[1];
    }
  });
  Object.defineProperty(Graticule.prototype, 'stepMinor', {
    get: function () {
      return new Float64Array([this.minorStepX_0, this.minorStepY_0]);
    },
    set: function (value) {
      this.minorStepX_0 = value[0];
      this.minorStepY_0 = value[1];
    }
  });
  Object.defineProperty(Graticule.prototype, 'step', {
    get: function () {
      return this.stepMinor;
    },
    set: function (value) {
      this.stepMajor = value;
      this.stepMinor = value;
    }
  });
  var collectionSizeOrDefault = Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$;
  Graticule.prototype.graticule = function () {
    var $receiver = this.buildLines_0();
    var destination = ArrayList_init_0(collectionSizeOrDefault($receiver, 10));
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      var tmp$_0 = destination.add_11rb$;
      var destination_0 = ArrayList_init_0(collectionSizeOrDefault(item, 10));
      var tmp$_1;
      tmp$_1 = item.iterator();
      while (tmp$_1.hasNext()) {
        var item_0 = tmp$_1.next();
        destination_0.add_11rb$([item_0[0], item_0[1]]);
      }
      tmp$_0.call(destination, copyToArray(destination_0));
    }
    return new MultiLineString(copyToArray(destination));
  };
  Graticule.prototype.lines = function () {
    var $receiver = this.buildLines_0();
    var destination = ArrayList_init_0(collectionSizeOrDefault($receiver, 10));
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      var tmp$_0 = destination.add_11rb$;
      var destination_0 = ArrayList_init_0(collectionSizeOrDefault(item, 10));
      var tmp$_1;
      tmp$_1 = item.iterator();
      while (tmp$_1.hasNext()) {
        var item_0 = tmp$_1.next();
        destination_0.add_11rb$([item_0[0], item_0[1]]);
      }
      tmp$_0.call(destination, new LineString(copyToArray(destination_0)));
    }
    return destination;
  };
  var addAll = Kotlin.kotlin.collections.addAll_ipc267$;
  Graticule.prototype.outline = function () {
    var coords = toMutableList(this.majorX_0(this.majorExtent_0.x0));
    addAll(coords, this.majorY_0(this.majorExtent_0.y1).subList_vux9f0$(1, get_lastIndex(this.majorY_0(this.majorExtent_0.y1))));
    addAll(coords, asReversed(this.majorX_0(this.majorExtent_0.x1)).subList_vux9f0$(1, get_lastIndex(this.majorX_0(this.majorExtent_0.x1))));
    addAll(coords, asReversed(this.majorY_0(this.majorExtent_0.y0)).subList_vux9f0$(1, get_lastIndex(this.majorY_0(this.majorExtent_0.y0))));
    var destination = ArrayList_init_0(collectionSizeOrDefault(coords, 10));
    var tmp$;
    tmp$ = coords.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$([item[0], item[1]]);
    }
    return new Polygon([copyToArray(destination)]);
  };
  Graticule.prototype.buildLines_0 = function () {
    var x = this.majorExtent_0.x0 / this.majorStepX_0;
    var $receiver = range(Math_0.ceil(x) * this.majorStepX_0, this.majorExtent_0.x1, this.majorStepX_0);
    var transform = this.majorX_0;
    var destination = ArrayList_init_0(collectionSizeOrDefault($receiver, 10));
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(transform(item));
    }
    var lines = toMutableList(destination);
    var x_0 = this.majorExtent_0.y0 / this.majorStepY_0;
    var $receiver_0 = range(Math_0.ceil(x_0) * this.majorStepY_0, this.majorExtent_0.y1, this.majorStepY_0);
    var transform_0 = this.majorY_0;
    var destination_0 = ArrayList_init_0(collectionSizeOrDefault($receiver_0, 10));
    var tmp$_0;
    tmp$_0 = $receiver_0.iterator();
    while (tmp$_0.hasNext()) {
      var item_0 = tmp$_0.next();
      destination_0.add_11rb$(transform_0(item_0));
    }
    addAll(lines, destination_0);
    var x_1 = this.minorExtent_0.x0 / this.minorStepX_0;
    var $receiver_1 = range(Math_0.ceil(x_1) * this.minorStepX_0, this.minorExtent_0.x1, this.minorStepX_0);
    var destination_1 = ArrayList_init();
    var tmp$_1;
    tmp$_1 = $receiver_1.iterator();
    while (tmp$_1.hasNext()) {
      var element = tmp$_1.next();
      var x_2 = element % this.majorStepX_0;
      if (Math_0.abs(x_2) > math.EPSILON)
        destination_1.add_11rb$(element);
    }
    var transform_1 = this.minorX_0;
    var destination_2 = ArrayList_init_0(collectionSizeOrDefault(destination_1, 10));
    var tmp$_2;
    tmp$_2 = destination_1.iterator();
    while (tmp$_2.hasNext()) {
      var item_1 = tmp$_2.next();
      destination_2.add_11rb$(transform_1(item_1));
    }
    addAll(lines, destination_2);
    var x_3 = this.minorExtent_0.y0 / this.minorStepY_0;
    var $receiver_2 = range(Math_0.ceil(x_3) * this.minorStepY_0, this.minorExtent_0.y1, this.minorStepY_0);
    var destination_3 = ArrayList_init();
    var tmp$_3;
    tmp$_3 = $receiver_2.iterator();
    while (tmp$_3.hasNext()) {
      var element_0 = tmp$_3.next();
      var x_4 = element_0 % this.majorStepY_0;
      if (Math_0.abs(x_4) > math.EPSILON)
        destination_3.add_11rb$(element_0);
    }
    var transform_2 = this.minorY_0;
    var destination_4 = ArrayList_init_0(collectionSizeOrDefault(destination_3, 10));
    var tmp$_4;
    tmp$_4 = destination_3.iterator();
    while (tmp$_4.hasNext()) {
      var item_2 = tmp$_4.next();
      destination_4.add_11rb$(transform_2(item_2));
    }
    addAll(lines, destination_4);
    return lines;
  };
  function Graticule$graticuleX$lambda(closure$y) {
    return function (x) {
      var $receiver = closure$y;
      var destination = ArrayList_init_0(collectionSizeOrDefault($receiver, 10));
      var tmp$;
      tmp$ = $receiver.iterator();
      while (tmp$.hasNext()) {
        var item = tmp$.next();
        destination.add_11rb$(new Float64Array([x, item]));
      }
      return destination;
    };
  }
  Graticule.prototype.graticuleX_0 = function (y0, y1, dy) {
    var y = toMutableList(range(y0, y1 - math.EPSILON, dy));
    y.add_11rb$(y1);
    return Graticule$graticuleX$lambda(y);
  };
  function Graticule$graticuleY$lambda(closure$x) {
    return function (y) {
      var $receiver = closure$x;
      var destination = ArrayList_init_0(collectionSizeOrDefault($receiver, 10));
      var tmp$;
      tmp$ = $receiver.iterator();
      while (tmp$.hasNext()) {
        var item = tmp$.next();
        destination.add_11rb$(new Float64Array([item, y]));
      }
      return destination;
    };
  }
  Graticule.prototype.graticuleY_0 = function (x0, x1, dx) {
    var x = toMutableList(range(x0, x1 - math.EPSILON, dx));
    x.add_11rb$(x1);
    return Graticule$graticuleY$lambda(x);
  };
  Graticule.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Graticule',
    interfaces: []
  };
  function get_asin($receiver) {
    return limitedAsin($receiver);
  }
  function get_acos($receiver) {
    return limitedAcos($receiver);
  }
  function limitedAsin(value) {
    var tmp$;
    if (value > 1)
      tmp$ = math.HALFPI;
    else if (value < -1)
      tmp$ = -math.HALFPI;
    else {
      tmp$ = Math_0.asin(value);
    }
    return tmp$;
  }
  function limitedAcos(value) {
    var tmp$;
    if (value > 1)
      tmp$ = 0.0;
    else if (value < -1)
      tmp$ = math.PI;
    else {
      tmp$ = Math_0.acos(value);
    }
    return tmp$;
  }
  function polygonContains(polygon, point) {
    var lambda = point[0];
    var phi = point[1];
    var normal0 = Math_0.sin(lambda);
    var normal1 = -Math_0.cos(lambda);
    var normal2 = 0.0;
    var angle = 0.0;
    var winding = 0;
    var sum = 0.0;
    for (var i = 0; i !== polygon.size; ++i) {
      var ring = polygon.get_za3lpa$(i);
      if (ring.isEmpty())
        continue;
      var point0 = last(ring);
      var lambda0 = point0[0];
      var phi0 = point0[1] / 2 + math.QUARTERPI;
      var x = phi0;
      var sinPhi0 = Math_0.sin(x);
      var x_0 = phi0;
      var cosPhi0 = Math_0.cos(x_0);
      for (var j = 0; j !== ring.size; ++j) {
        var point1 = ring.get_za3lpa$(j);
        var lambda1 = point1[0];
        var phi1 = point1[1] / 2 + math.QUARTERPI;
        var sinPhi1 = Math_0.sin(phi1);
        var cosPhi1 = Math_0.cos(phi1);
        var delta = lambda1 - lambda0;
        var sign = delta >= 0 ? 1 : -1;
        var absDelta = sign * delta;
        var antimeridian = absDelta > math_0.PI;
        var k = sinPhi0 * sinPhi1;
        var tmp$ = k * sign * Math_0.sin(absDelta);
        var x_1 = cosPhi0 * cosPhi1 + k * Math_0.cos(absDelta);
        sum += Math_0.atan2(tmp$, x_1);
        angle += antimeridian ? delta + sign * math.TAU : delta;
        if (antimeridian ^ lambda0 >= lambda ^ lambda1 >= lambda) {
          var lambdaA0 = point0[0];
          var phiA0 = point0[1];
          var cosPhiA = Math_0.cos(phiA0);
          var a0 = cosPhiA * Math_0.cos(lambdaA0);
          var a1 = cosPhiA * Math_0.sin(lambdaA0);
          var a2 = Math_0.sin(phiA0);
          var lambdaB0 = point1[0];
          var phiB0 = point1[1];
          var cosPhiB = Math_0.cos(phiB0);
          var b0 = cosPhiB * Math_0.cos(lambdaB0);
          var b1 = cosPhiB * Math_0.sin(lambdaB0);
          var b2 = Math_0.sin(phiB0);
          var cross0 = a1 * b2 - a2 * b1;
          var cross1 = a2 * b0 - a0 * b2;
          var cross2 = a0 * b1 - a1 * b0;
          var x_2 = cross0 * cross0 + cross1 * cross1 + cross2 * cross2;
          var normalize = Math_0.sqrt(x_2);
          var d0 = cross0 / normalize;
          var d1 = cross1 / normalize;
          var d2 = cross2 / normalize;
          var intersectionD0 = normal1 * d2 - normal2 * d1;
          var intersectionD1 = normal2 * d0 - normal0 * d2;
          var intersectionD2 = normal0 * d1 - normal1 * d0;
          var x_3 = intersectionD0 * intersectionD0 + intersectionD1 * intersectionD1 + intersectionD2 * intersectionD2;
          var intersectionNormalize = Math_0.sqrt(x_3);
          intersectionD2 /= intersectionNormalize;
          var phiArc = (antimeridian ^ delta >= 0 ? -1 : 1) * Math_0.asin(intersectionD2);
          if (phi > phiArc || (phi === phiArc && (d0 !== 0.0 && !isNaN_0(d0) || (d1 !== 0.0 && !isNaN_0(d1))))) {
            winding = winding + (antimeridian ^ delta >= 0 ? 1 : -1) | 0;
          }
        }
        lambda0 = lambda1;
        sinPhi0 = sinPhi1;
        cosPhi0 = cosPhi1;
        phi0 = phi1;
        point0 = point1;
      }
    }
    return (angle < -math.EPSILON || (angle < math.EPSILON && sum < -math.EPSILON)) ^ (winding & 1) !== 0;
  }
  function noop$lambda() {
    return Unit;
  }
  var noop;
  function noop2$lambda(f, f_0) {
    return Unit;
  }
  var noop2;
  function noop3$lambda(f, f_0, f_1) {
    return Unit;
  }
  var noop3;
  function stream(geo, stream_0) {
    if (Kotlin.isType(geo, FeatureCollection)) {
      var $receiver = geo.features;
      var tmp$;
      for (tmp$ = 0; tmp$ !== $receiver.length; ++tmp$) {
        var element = $receiver[tmp$];
        stream(element, stream_0);
      }
    }
     else if (Kotlin.isType(geo, Feature))
      stream(geo.geometry, stream_0);
    else if (Kotlin.isType(geo, GeometryCollection)) {
      var $receiver_0 = geo.geometries;
      var tmp$_0;
      for (tmp$_0 = 0; tmp$_0 !== $receiver_0.length; ++tmp$_0) {
        var element_0 = $receiver_0[tmp$_0];
        streamGeometry(element_0, stream_0);
      }
    }
     else if (Kotlin.isType(geo, Geometry))
      streamGeometry(geo, stream_0);
  }
  function streamGeometry(geo, stream) {
    if (Kotlin.isType(geo, Point))
      streamPoint(geo.coordinates, stream);
    else if (Kotlin.isType(geo, LineString))
      streamLine(geo.coordinates, stream, false);
    else if (Kotlin.isType(geo, MultiPoint)) {
      var $receiver = geo.coordinates;
      var tmp$;
      for (tmp$ = 0; tmp$ !== $receiver.length; ++tmp$) {
        var element = $receiver[tmp$];
        streamPoint(element, stream);
      }
    }
     else if (Kotlin.isType(geo, MultiPolygon)) {
      var $receiver_0 = geo.coordinates;
      var tmp$_0;
      for (tmp$_0 = 0; tmp$_0 !== $receiver_0.length; ++tmp$_0) {
        var element_0 = $receiver_0[tmp$_0];
        streamPolygon(element_0, stream);
      }
    }
     else if (Kotlin.isType(geo, Polygon))
      streamPolygon(geo.coordinates, stream);
    else if (Kotlin.isType(geo, MultiLineString)) {
      var $receiver_1 = geo.coordinates;
      var tmp$_1;
      for (tmp$_1 = 0; tmp$_1 !== $receiver_1.length; ++tmp$_1) {
        var element_1 = $receiver_1[tmp$_1];
        streamLine(element_1, stream, false);
      }
    }
     else if (Kotlin.isType(geo, Sphere))
      streamSphere(stream);
  }
  function streamSphere(stream) {
    stream.sphere();
  }
  function streamPoint(coordinates, stream) {
    var z = get_alt(coordinates);
    if (z == null)
      z = 0.0;
    stream.point_yvo9jy$(get_lon(coordinates), get_lat(coordinates), z);
  }
  function streamPolygon(coords, stream) {
    stream.polygonStart();
    var tmp$;
    for (tmp$ = 0; tmp$ !== coords.length; ++tmp$) {
      var element = coords[tmp$];
      streamLine(element, stream, true);
    }
    stream.polygonEnd();
  }
  function streamLine(coords, stream, closed) {
    var size = closed ? coords.length - 1 | 0 : coords.length;
    stream.lineStart();
    for (var i = 0; i < size; i++) {
      var p = coords[i];
      stream.point_yvo9jy$(p[0], p[1], p.length > 2 ? p[2] : 0.0);
    }
    stream.lineEnd();
  }
  function ModifiedStream(stream) {
    this.stream = stream;
  }
  ModifiedStream.prototype.point_yvo9jy$ = function (x, y, z) {
    this.stream.point_yvo9jy$(x, y, z);
  };
  ModifiedStream.prototype.lineStart = function () {
    this.stream.lineStart();
  };
  ModifiedStream.prototype.lineEnd = function () {
    this.stream.lineEnd();
  };
  ModifiedStream.prototype.polygonStart = function () {
    this.stream.polygonStart();
  };
  ModifiedStream.prototype.polygonEnd = function () {
    this.stream.polygonEnd();
  };
  ModifiedStream.prototype.sphere = function () {
    this.stream.sphere();
  };
  ModifiedStream.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ModifiedStream',
    interfaces: [Stream]
  };
  function clipAntimeridian$lambda(stream) {
    return new Clip(new AntimeridianClip(), stream);
  }
  function clipAntimeridian() {
    return clipAntimeridian$lambda;
  }
  function AntimeridianClip() {
    this.start_f45s7b$_0 = new Float64Array([-math.PI, -math.HALFPI]);
  }
  Object.defineProperty(AntimeridianClip.prototype, 'start', {
    get: function () {
      return this.start_f45s7b$_0;
    },
    set: function (start) {
      this.start_f45s7b$_0 = start;
    }
  });
  AntimeridianClip.prototype.pointVisible_lu1900$ = function (x, y) {
    return true;
  };
  function AntimeridianClip$clipLine$ObjectLiteral(closure$stream, closure$lambda0, closure$phi0, closure$sign0) {
    this.closure$stream = closure$stream;
    this.closure$lambda0 = closure$lambda0;
    this.closure$phi0 = closure$phi0;
    this.closure$sign0 = closure$sign0;
    this.currentClean_0 = 0;
  }
  Object.defineProperty(AntimeridianClip$clipLine$ObjectLiteral.prototype, 'clean', {
    get: function () {
      return 2 - this.currentClean_0 | 0;
    },
    set: function (value) {
      this.currentClean_0 = value;
    }
  });
  AntimeridianClip$clipLine$ObjectLiteral.prototype.lineStart = function () {
    this.closure$stream.lineStart();
    this.clean = 1;
  };
  AntimeridianClip$clipLine$ObjectLiteral.prototype.point_yvo9jy$ = function (x, y, z) {
    var lambda1 = x;
    var phi1 = y;
    var sign1 = lambda1 > 0 ? math.PI : -math.PI;
    var x_0 = lambda1 - this.closure$lambda0.v;
    var delta = Math_0.abs(x_0);
    var x_1 = delta - math.PI;
    if (Math_0.abs(x_1) < math.EPSILON) {
      this.closure$phi0.v = (this.closure$phi0.v + phi1) / 2 > 0 ? math.HALFPI : -math.HALFPI;
      this.closure$stream.point_yvo9jy$(this.closure$lambda0.v, this.closure$phi0.v, 0.0);
      this.closure$stream.point_yvo9jy$(this.closure$sign0.v, this.closure$phi0.v, 0.0);
      this.closure$stream.lineEnd();
      this.closure$stream.lineStart();
      this.closure$stream.point_yvo9jy$(sign1, this.closure$phi0.v, 0.0);
      this.closure$stream.point_yvo9jy$(lambda1, this.closure$phi0.v, 0.0);
      this.clean = 0;
    }
     else if (this.closure$sign0.v !== sign1 && delta >= math.PI) {
      var x_2 = this.closure$lambda0.v - this.closure$sign0.v;
      if (Math_0.abs(x_2) < math.EPSILON)
        this.closure$lambda0.v -= this.closure$sign0.v * math.EPSILON;
      var x_3 = lambda1 - sign1;
      if (Math_0.abs(x_3) < math.EPSILON)
        lambda1 -= sign1 * math.EPSILON;
      this.closure$phi0.v = this.intersect_0(this.closure$lambda0.v, this.closure$phi0.v, lambda1, phi1);
      this.closure$stream.point_yvo9jy$(this.closure$sign0.v, this.closure$phi0.v, 0.0);
      this.closure$stream.lineEnd();
      this.closure$stream.lineStart();
      this.closure$stream.point_yvo9jy$(sign1, this.closure$phi0.v, 0.0);
      this.clean = 0;
    }
    this.closure$lambda0.v = lambda1;
    this.closure$phi0.v = phi1;
    this.closure$stream.point_yvo9jy$(this.closure$lambda0.v, this.closure$phi0.v, 0.0);
    this.closure$sign0.v = sign1;
  };
  AntimeridianClip$clipLine$ObjectLiteral.prototype.lineEnd = function () {
    this.closure$stream.lineEnd();
    this.closure$lambda0.v = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.closure$phi0.v = kotlin_js_internal_DoubleCompanionObject.NaN;
  };
  AntimeridianClip$clipLine$ObjectLiteral.prototype.intersect_0 = function (lambda0, phi0, lambda1, phi1) {
    var tmp$;
    var x = lambda0 - lambda1;
    var sinLambda0Lambda1 = Math_0.sin(x);
    if (Math_0.abs(sinLambda0Lambda1) > math.EPSILON) {
      var cosPhi0 = Math_0.cos(phi0);
      var cosPhi1 = Math_0.cos(phi1);
      var x_0 = (Math_0.sin(phi0) * cosPhi1 * Math_0.sin(lambda1) - Math_0.sin(phi1) * cosPhi0 * Math_0.sin(lambda0)) / (cosPhi0 * cosPhi1 * sinLambda0Lambda1);
      tmp$ = Math_0.atan(x_0);
    }
     else
      tmp$ = (phi0 + phi1) / 2;
    return tmp$;
  };
  AntimeridianClip$clipLine$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [ClipStream]
  };
  AntimeridianClip.prototype.clipLine_k25lbv$ = function (stream) {
    var lambda0 = {v: kotlin_js_internal_DoubleCompanionObject.NaN};
    var phi0 = {v: kotlin_js_internal_DoubleCompanionObject.NaN};
    var sign0 = {v: kotlin_js_internal_DoubleCompanionObject.NaN};
    return new AntimeridianClip$clipLine$ObjectLiteral(stream, lambda0, phi0, sign0);
  };
  AntimeridianClip.prototype.interpolate_ak3pfj$ = function (from, to, direction, stream) {
    if (from == null || to == null) {
      var phi = direction * math.HALFPI;
      stream.point_yvo9jy$(-math.PI, phi, 0.0);
      stream.point_yvo9jy$(0.0, phi, 0.0);
      stream.point_yvo9jy$(math.PI, phi, 0.0);
      stream.point_yvo9jy$(math.PI, 0.0, 0.0);
      stream.point_yvo9jy$(math.PI, -phi, 0.0);
      stream.point_yvo9jy$(0.0, -phi, 0.0);
      stream.point_yvo9jy$(-math.PI, -phi, 0.0);
      stream.point_yvo9jy$(-math.PI, 0.0, 0.0);
      stream.point_yvo9jy$(-math.PI, phi, 0.0);
    }
     else {
      var x = from[0] - to[0];
      if (Math_0.abs(x) > math.EPSILON) {
        var lambda = from[0] < to[0] ? math.PI : -math.PI;
        var phi_0 = direction * lambda / 2;
        stream.point_yvo9jy$(-lambda, phi_0, 0.0);
        stream.point_yvo9jy$(0.0, phi_0, 0.0);
        stream.point_yvo9jy$(lambda, phi_0, 0.0);
      }
       else
        stream.point_yvo9jy$(to[0], to[1], 0.0);
    }
  };
  AntimeridianClip.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'AntimeridianClip',
    interfaces: [ClippableHasStart]
  };
  function ClipStream() {
  }
  ClipStream.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'ClipStream',
    interfaces: [Stream]
  };
  function Clippable() {
  }
  Clippable.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Clippable',
    interfaces: []
  };
  function ClippableHasStart() {
  }
  ClippableHasStart.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'ClippableHasStart',
    interfaces: [Clippable]
  };
  function PointFunction() {
  }
  PointFunction.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'PointFunction',
    interfaces: []
  };
  function LineStartFunction() {
  }
  LineStartFunction.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'LineStartFunction',
    interfaces: []
  };
  function LineEndFunction() {
  }
  LineEndFunction.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'LineEndFunction',
    interfaces: []
  };
  function DefaultPointFunction() {
    DefaultPointFunction_instance = this;
  }
  DefaultPointFunction.prototype.invoke_6i9qg0$ = function (clip, x, y, z) {
    if (clip.clip.pointVisible_lu1900$(x, y))
      clip.sink.point_yvo9jy$(x, y, z);
  };
  DefaultPointFunction.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'DefaultPointFunction',
    interfaces: [PointFunction]
  };
  var DefaultPointFunction_instance = null;
  function DefaultPointFunction_getInstance() {
    if (DefaultPointFunction_instance === null) {
      new DefaultPointFunction();
    }
    return DefaultPointFunction_instance;
  }
  function RingPointFunction() {
    RingPointFunction_instance = this;
  }
  RingPointFunction.prototype.invoke_6i9qg0$ = function (clip, x, y, z) {
    ensureNotNull(clip.ring_8be2vx$).add_11rb$(new Float64Array([x, y]));
    clip.ringSink_8be2vx$.point_yvo9jy$(x, y, z);
  };
  RingPointFunction.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'RingPointFunction',
    interfaces: [PointFunction]
  };
  var RingPointFunction_instance = null;
  function RingPointFunction_getInstance() {
    if (RingPointFunction_instance === null) {
      new RingPointFunction();
    }
    return RingPointFunction_instance;
  }
  function DefaultLineStartFunction() {
    DefaultLineStartFunction_instance = this;
  }
  DefaultLineStartFunction.prototype.invoke_sraowm$ = function (clip) {
    clip.currentPoint_8be2vx$ = LinePointFunction_getInstance();
    clip.line_8be2vx$.lineStart();
  };
  DefaultLineStartFunction.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'DefaultLineStartFunction',
    interfaces: [LineStartFunction]
  };
  var DefaultLineStartFunction_instance = null;
  function DefaultLineStartFunction_getInstance() {
    if (DefaultLineStartFunction_instance === null) {
      new DefaultLineStartFunction();
    }
    return DefaultLineStartFunction_instance;
  }
  function DefaultLineEndFunction() {
    DefaultLineEndFunction_instance = this;
  }
  DefaultLineEndFunction.prototype.invoke_sraowm$ = function (clip) {
    clip.currentPoint_8be2vx$ = DefaultPointFunction_getInstance();
    clip.line_8be2vx$.lineEnd();
  };
  DefaultLineEndFunction.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'DefaultLineEndFunction',
    interfaces: [LineEndFunction]
  };
  var DefaultLineEndFunction_instance = null;
  function DefaultLineEndFunction_getInstance() {
    if (DefaultLineEndFunction_instance === null) {
      new DefaultLineEndFunction();
    }
    return DefaultLineEndFunction_instance;
  }
  function RingLineStartFunction() {
    RingLineStartFunction_instance = this;
  }
  RingLineStartFunction.prototype.invoke_sraowm$ = function (clip) {
    clip.ringSink_8be2vx$.lineStart();
    clip.ring_8be2vx$ = ArrayList_init();
  };
  RingLineStartFunction.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'RingLineStartFunction',
    interfaces: [LineStartFunction]
  };
  var RingLineStartFunction_instance = null;
  function RingLineStartFunction_getInstance() {
    if (RingLineStartFunction_instance === null) {
      new RingLineStartFunction();
    }
    return RingLineStartFunction_instance;
  }
  function RingLineEndFunction() {
    RingLineEndFunction_instance = this;
  }
  var IllegalArgumentException_init = Kotlin.kotlin.IllegalArgumentException_init_pdl1vj$;
  RingLineEndFunction.prototype.invoke_sraowm$ = function (clip) {
    if (clip.ring_8be2vx$ == null) {
      var message = "Error on Clip.ringEnd, ring can't be null.";
      throw IllegalArgumentException_init(message.toString());
    }
    var ringList = ensureNotNull(clip.ring_8be2vx$);
    RingPointFunction_getInstance().invoke_6i9qg0$(clip, ringList.get_za3lpa$(0)[0], ringList.get_za3lpa$(0)[1], 0.0);
    clip.ringSink_8be2vx$.lineEnd();
    var clean = clip.ringSink_8be2vx$.clean;
    var ringSegments = clip.ringBuffer_8be2vx$.result();
    ringList.removeAt_za3lpa$(get_lastIndex(ringList));
    clip.polygon_8be2vx$.add_11rb$(ringList);
    clip.ring_8be2vx$ = null;
    if (ringSegments.isEmpty())
      return;
    if ((clean & 1) !== 0) {
      var segment = ringSegments.get_za3lpa$(0);
      var m = get_lastIndex(segment);
      if (m > 0) {
        if (!clip.polygonStarted_8be2vx$) {
          clip.sink.polygonStart();
          clip.polygonStarted_8be2vx$ = true;
        }
        clip.sink.lineStart();
        var tmp$;
        tmp$ = until(0, m).iterator();
        while (tmp$.hasNext()) {
          var element = tmp$.next();
          var currentSegmentPiece = segment.get_za3lpa$(element);
          var x = currentSegmentPiece[0];
          var y = currentSegmentPiece[1];
          clip.sink.point_yvo9jy$(x, y, 0.0);
        }
        clip.sink.lineEnd();
      }
      return;
    }
    if (ringSegments.size > 1 && (clean & 2) !== 0) {
      var concat = toMutableList(ringSegments.removeAt_za3lpa$(get_lastIndex(ringSegments)));
      concat.addAll_brywnq$(ringSegments.removeAt_za3lpa$(0));
      ringSegments.add_11rb$(concat);
    }
    var tmp$_0 = clip.segments_8be2vx$;
    var destination = ArrayList_init();
    var tmp$_1;
    tmp$_1 = ringSegments.iterator();
    while (tmp$_1.hasNext()) {
      var element_0 = tmp$_1.next();
      if (element_0.size > 1)
        destination.add_11rb$(element_0);
    }
    tmp$_0.add_11rb$(destination);
  };
  RingLineEndFunction.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'RingLineEndFunction',
    interfaces: [LineEndFunction]
  };
  var RingLineEndFunction_instance = null;
  function RingLineEndFunction_getInstance() {
    if (RingLineEndFunction_instance === null) {
      new RingLineEndFunction();
    }
    return RingLineEndFunction_instance;
  }
  function LinePointFunction() {
    LinePointFunction_instance = this;
  }
  LinePointFunction.prototype.invoke_6i9qg0$ = function (clip, x, y, z) {
    clip.line_8be2vx$.point_yvo9jy$(x, y, z);
  };
  LinePointFunction.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'LinePointFunction',
    interfaces: [PointFunction]
  };
  var LinePointFunction_instance = null;
  function LinePointFunction_getInstance() {
    if (LinePointFunction_instance === null) {
      new LinePointFunction();
    }
    return LinePointFunction_instance;
  }
  function PointRingPointFunction() {
    PointRingPointFunction_instance = this;
  }
  PointRingPointFunction.prototype.invoke_6i9qg0$ = function (clip, x, y, z) {
    ensureNotNull(clip.ring_8be2vx$).add_11rb$(new Float64Array([x, y]));
    clip.ringSink_8be2vx$.point_yvo9jy$(x, y, z);
  };
  PointRingPointFunction.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'PointRingPointFunction',
    interfaces: [PointFunction]
  };
  var PointRingPointFunction_instance = null;
  function PointRingPointFunction_getInstance() {
    if (PointRingPointFunction_instance === null) {
      new PointRingPointFunction();
    }
    return PointRingPointFunction_instance;
  }
  function Comparator$ObjectLiteral_0(closure$comparison) {
    this.closure$comparison = closure$comparison;
  }
  Comparator$ObjectLiteral_0.prototype.compare = function (a, b) {
    return this.closure$comparison(a, b);
  };
  Comparator$ObjectLiteral_0.$metadata$ = {kind: Kind_CLASS, interfaces: [Comparator]};
  function Clip(clip, sink) {
    this.clip = clip;
    this.sink = sink;
    this.line_8be2vx$ = this.clip.clipLine_k25lbv$(this.sink);
    this.ringBuffer_8be2vx$ = new ClipBuffer();
    this.ringSink_8be2vx$ = this.clip.clipLine_k25lbv$(this.ringBuffer_8be2vx$);
    this.polygonStarted_8be2vx$ = false;
    this.segments_8be2vx$ = ArrayList_init();
    this.polygon_8be2vx$ = ArrayList_init();
    this.ring_8be2vx$ = null;
    this.currentPoint_8be2vx$ = DefaultPointFunction_getInstance();
    this.currentLineStart_8be2vx$ = DefaultLineStartFunction_getInstance();
    this.currentLineEnd_8be2vx$ = DefaultLineEndFunction_getInstance();
    this.compareIntersection_0 = new Comparator$ObjectLiteral_0(Clip$compareIntersection$lambda);
    this.interpolateFunction = new Clip$interpolateFunction$ObjectLiteral(this);
  }
  Clip.prototype.point_yvo9jy$ = function (x, y, z) {
    this.currentPoint_8be2vx$.invoke_6i9qg0$(this, x, y, z);
  };
  Clip.prototype.lineStart = function () {
    this.currentLineStart_8be2vx$.invoke_sraowm$(this);
  };
  Clip.prototype.lineEnd = function () {
    this.currentLineEnd_8be2vx$.invoke_sraowm$(this);
  };
  Clip.prototype.polygonStart = function () {
    this.currentPoint_8be2vx$ = PointRingPointFunction_getInstance();
    this.currentLineStart_8be2vx$ = RingLineStartFunction_getInstance();
    this.currentLineEnd_8be2vx$ = RingLineEndFunction_getInstance();
  };
  Clip.prototype.polygonEnd = function () {
    this.currentPoint_8be2vx$ = DefaultPointFunction_getInstance();
    this.currentLineStart_8be2vx$ = DefaultLineStartFunction_getInstance();
    this.currentLineEnd_8be2vx$ = DefaultLineEndFunction_getInstance();
    var startInside = polygonContains(this.polygon_8be2vx$, this.clip.start);
    if (!this.segments_8be2vx$.isEmpty()) {
      if (!this.polygonStarted_8be2vx$) {
        this.sink.polygonStart();
        this.polygonStarted_8be2vx$ = true;
      }
      rejoin(flatten(this.segments_8be2vx$), this.compareIntersection_0, startInside, this.interpolateFunction, this.sink);
    }
     else if (startInside) {
      if (!this.polygonStarted_8be2vx$) {
        this.sink.polygonStart();
        this.polygonStarted_8be2vx$ = true;
      }
      this.sink.lineStart();
      this.clip.interpolate_ak3pfj$(null, null, 1, this.sink);
      this.sink.lineEnd();
    }
    if (this.polygonStarted_8be2vx$) {
      this.sink.polygonEnd();
      this.polygonStarted_8be2vx$ = false;
    }
    this.segments_8be2vx$.clear();
    this.polygon_8be2vx$.clear();
  };
  Clip.prototype.sphere = function () {
    this.sink.polygonStart();
    this.sink.lineStart();
    this.clip.interpolate_ak3pfj$(null, null, 1, this.sink);
    this.sink.lineEnd();
    this.sink.polygonEnd();
  };
  function Clip$compareIntersection$lambda(i1, i2) {
    var a = i1.point;
    var b = i2.point;
    var ca = a[0] < 0 ? a[1] - math.HALFPI - math.EPSILON : math.HALFPI - a[1];
    var cb = b[0] < 0 ? b[1] - math.HALFPI - math.EPSILON : math.HALFPI - b[1];
    return Kotlin.compareTo(ca, cb);
  }
  function Clip$interpolateFunction$ObjectLiteral(this$Clip) {
    this.this$Clip = this$Clip;
  }
  Clip$interpolateFunction$ObjectLiteral.prototype.invoke_3h98hb$ = function (from, to, direction, stream) {
    this.this$Clip.clip.interpolate_ak3pfj$(from, to, direction, stream);
  };
  Clip$interpolateFunction$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [InterpolateFunction]
  };
  Clip.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Clip',
    interfaces: [Stream]
  };
  function ClipBuffer() {
    this.lines_0 = ArrayList_init();
    this.line_q7xsf6$_0 = this.line_q7xsf6$_0;
  }
  Object.defineProperty(ClipBuffer.prototype, 'line_0', {
    get: function () {
      if (this.line_q7xsf6$_0 == null)
        return throwUPAE('line');
      return this.line_q7xsf6$_0;
    },
    set: function (line) {
      this.line_q7xsf6$_0 = line;
    }
  });
  ClipBuffer.prototype.point_yvo9jy$ = function (x, y, z) {
    this.line_0.add_11rb$(new Float64Array([x, y]));
  };
  ClipBuffer.prototype.lineStart = function () {
    this.line_0 = ArrayList_init();
    this.lines_0.add_11rb$(this.line_0);
  };
  ClipBuffer.prototype.rejoin = function () {
    if (this.lines_0.size > 1) {
      var l = ArrayList_init();
      l.add_11rb$(this.lines_0.removeAt_za3lpa$(get_lastIndex(this.lines_0)));
      l.add_11rb$(this.lines_0.removeAt_za3lpa$(0));
      this.lines_0.addAll_brywnq$(l);
    }
  };
  ClipBuffer.prototype.result = function () {
    var oldLines = this.lines_0;
    this.lines_0 = ArrayList_init();
    return oldLines;
  };
  ClipBuffer.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ClipBuffer',
    interfaces: [Stream]
  };
  function clipCircle$lambda(closure$radius) {
    return function (stream) {
      return new Clip(new ClipCircle(closure$radius), stream);
    };
  }
  function clipCircle(radius) {
    return clipCircle$lambda(radius);
  }
  function ClipCircle(radius) {
    this.radius = radius;
    var x = this.radius;
    this.cr_0 = Math_0.cos(x);
    this.delta_0 = toRadians(6.0);
    this.smallRadius_0 = this.cr_0 > 0;
    var x_0 = this.cr_0;
    this.notHemisphere_0 = Math_0.abs(x_0) > math.EPSILON;
  }
  Object.defineProperty(ClipCircle.prototype, 'start', {
    get: function () {
      return this.smallRadius_0 ? new Float64Array([0.0, -this.radius]) : new Float64Array([-math.PI, this.radius - math.PI]);
    }
  });
  ClipCircle.prototype.pointVisible_lu1900$ = function (x, y) {
    return Math_0.cos(x) * Math_0.cos(y) > this.cr_0;
  };
  function ClipCircle$clipLine$ObjectLiteral(this$ClipCircle, closure$stream) {
    this.this$ClipCircle = this$ClipCircle;
    this.closure$stream = closure$stream;
    this._clean_0 = 0;
    this.point0_0 = null;
    this.c0_0 = 0;
    this.v0_0 = false;
    this.v00_0 = false;
    this.clean_qgac74$_0 = 0;
  }
  Object.defineProperty(ClipCircle$clipLine$ObjectLiteral.prototype, 'clean', {
    get: function () {
      return this._clean_0 | (this.v00_0 && this.v0_0 ? 1 : 0) << 1;
    },
    set: function (clean) {
      this.clean_qgac74$_0 = clean;
    }
  });
  ClipCircle$clipLine$ObjectLiteral.prototype.point_yvo9jy$ = function (x, y, z) {
    var tmp$;
    var point1 = new Float64Array([x, y]);
    var point2;
    var v = this.this$ClipCircle.pointVisible_lu1900$(x, y);
    if (this.this$ClipCircle.smallRadius_0) {
      tmp$ = v ? 0 : this.this$ClipCircle.code_lu1900$(x, y);
    }
     else {
      tmp$ = v ? this.this$ClipCircle.code_lu1900$(x + (x < 0 ? math.PI : -math.PI), y) : 0;
    }
    var c = tmp$;
    if (this.point0_0 == null) {
      this.v00_0 = v;
      this.v0_0 = v;
      if (v)
        this.closure$stream.lineStart();
    }
    if (v !== this.v0_0) {
      point2 = this.this$ClipCircle.intersect_0(ensureNotNull(this.point0_0), point1);
      if (point2 == null || pointEqual(ensureNotNull(this.point0_0), point2) || pointEqual(point1, point2)) {
        point1[0] = point1[0] + math.EPSILON;
        point1[1] = point1[1] + math.EPSILON;
        v = this.this$ClipCircle.pointVisible_lu1900$(point1[0], point1[1]);
      }
    }
    if (v !== this.v0_0) {
      this._clean_0 = 0;
      if (v) {
        this.closure$stream.lineStart();
        point2 = this.this$ClipCircle.intersect_0(point1, ensureNotNull(this.point0_0));
        this.closure$stream.point_yvo9jy$(ensureNotNull(point2)[0], point2[1], 0.0);
      }
       else {
        point2 = this.this$ClipCircle.intersect_0(ensureNotNull(this.point0_0), point1);
        this.closure$stream.point_yvo9jy$(ensureNotNull(point2)[0], point2[1], 0.0);
        this.closure$stream.lineEnd();
      }
      this.point0_0 = point2;
    }
     else if (this.this$ClipCircle.notHemisphere_0 && this.point0_0 != null && this.this$ClipCircle.smallRadius_0 ^ v) {
      if ((c & this.c0_0) === 0) {
        var t = this.this$ClipCircle.intersects_0(point1, ensureNotNull(this.point0_0));
        if (t != null) {
          this._clean_0 = 0;
          if (this.this$ClipCircle.smallRadius_0) {
            this.closure$stream.lineStart();
            this.closure$stream.point_yvo9jy$(t[0][0], t[0][1], 0.0);
            this.closure$stream.point_yvo9jy$(t[1][0], t[1][1], 0.0);
            this.closure$stream.lineEnd();
          }
           else {
            this.closure$stream.point_yvo9jy$(t[1][0], t[1][1], 0.0);
            this.closure$stream.lineEnd();
            this.closure$stream.lineStart();
            this.closure$stream.point_yvo9jy$(t[0][0], t[0][1], 0.0);
          }
        }
      }
    }
    if (v && (this.point0_0 == null || !pointEqual(ensureNotNull(this.point0_0), point1))) {
      this.closure$stream.point_yvo9jy$(point1[0], point1[1], 0.0);
    }
    this.point0_0 = point1;
    this.v0_0 = v;
    this.c0_0 = c;
  };
  ClipCircle$clipLine$ObjectLiteral.prototype.lineStart = function () {
    this.v00_0 = false;
    this.v0_0 = false;
    this._clean_0 = 1;
  };
  ClipCircle$clipLine$ObjectLiteral.prototype.lineEnd = function () {
    if (this.v0_0)
      this.closure$stream.lineEnd();
    this.point0_0 = null;
  };
  ClipCircle$clipLine$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [ClipStream]
  };
  ClipCircle.prototype.clipLine_k25lbv$ = function (stream) {
    return new ClipCircle$clipLine$ObjectLiteral(this, stream);
  };
  ClipCircle.prototype.interpolate_ak3pfj$ = function (from, to, direction, stream) {
    geoCircle(stream, this.radius, this.delta_0, direction, from, to);
  };
  ClipCircle.prototype.intersect_0 = function (a, b) {
    var pa = cartesian(a);
    var pb = cartesian(b);
    var n1 = new Float64Array([1.0, 0.0, 0.0]);
    var n2 = cartesianCross(pa, pb);
    var n2n2 = cartesianDot(n2, n2);
    var n1n2 = n2[0];
    var determinant = n2n2 - n1n2 * n1n2;
    if (determinant === 0.0)
      return a;
    var c1 = this.cr_0 * n2n2 / determinant;
    var c2 = -this.cr_0 * n1n2 / determinant;
    var n1xn2 = cartesianCross(n1, n2);
    var A = cartesianScale(n1, c1);
    var B = cartesianScale(n2, c2);
    A = cartesianAdd(A, B);
    var u = n1xn2;
    var w = cartesianDot(A, u);
    var uu = cartesianDot(u, u);
    var t2 = w * w - uu * (cartesianDot(A, A) - 1);
    if (t2 < 0)
      return null;
    var t = Math_0.sqrt(t2);
    var q = cartesianScale(u, (-w - t) / uu);
    q = cartesianAdd(q, A);
    q = spherical(q);
    return q;
  };
  ClipCircle.prototype.intersects_0 = function (a, b) {
    var tmp$;
    var pa = cartesian(a);
    var pb = cartesian(b);
    var n1 = new Float64Array([1.0, 0.0, 0.0]);
    var n2 = cartesianCross(pa, pb);
    var n2n2 = cartesianDot(n2, n2);
    var n1n2 = n2[0];
    var determinant = n2n2 - n1n2 * n1n2;
    if (determinant === 0.0)
      return null;
    var c1 = this.cr_0 * n2n2 / determinant;
    var c2 = -this.cr_0 * n1n2 / determinant;
    var n1xn2 = cartesianCross(n1, n2);
    var A = cartesianScale(n1, c1);
    var B = cartesianScale(n2, c2);
    A = cartesianAdd(A, B);
    var u = n1xn2;
    var w = cartesianDot(A, u);
    var uu = cartesianDot(u, u);
    var t2 = w * w - uu * (cartesianDot(A, A) - 1);
    if (t2 < 0)
      return null;
    var t = Math_0.sqrt(t2);
    var q = cartesianScale(u, (-w - t) / uu);
    q = cartesianAdd(q, A);
    q = spherical(q);
    var lambda0 = a[0];
    var lambda1 = b[0];
    var phi0 = a[1];
    var phi1 = b[1];
    if (lambda1 < lambda0) {
      var z = lambda0;
      lambda0 = lambda1;
      lambda1 = z;
    }
    var delta = lambda1 - lambda0;
    var x = delta - math.PI;
    var polar = Math_0.abs(x) < math.EPSILON;
    var meridian = polar || delta < math.EPSILON;
    if (!polar && phi1 < phi0) {
      var z_0 = phi0;
      phi0 = phi1;
      phi1 = z_0;
    }
    if (meridian) {
      if (polar) {
        var tmp$_0 = phi0 + phi1 > 0;
        var tmp$_1 = q[1];
        var x_0 = q[0] - lambda0;
        tmp$ = tmp$_0 ^ tmp$_1 < (Math_0.abs(x_0) < math.EPSILON ? phi0 : phi1);
      }
       else
        tmp$ = rangeTo(phi0, phi1).contains_mef7kx$(q[1]);
    }
     else {
      tmp$ = delta > math.PI ^ rangeTo(lambda0, lambda1).contains_mef7kx$(q[0]);
    }
    var test = tmp$;
    if (test) {
      var q1 = cartesianScale(u, (-w + t) / uu);
      q1 = cartesianAdd(q1, A);
      return [q, spherical(q1)];
    }
    return null;
  };
  ClipCircle.prototype.code_lu1900$ = function (x, y) {
    var r = this.smallRadius_0 ? this.radius : math.PI - this.radius;
    var code = 0;
    if (x < -r)
      code = code | 1;
    else if (y > r)
      code = code | 2;
    if (y < -r)
      code = code | 4;
    else if (y > r)
      code = code | 8;
    return code;
  };
  ClipCircle.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ClipCircle',
    interfaces: [ClippableHasStart]
  };
  var CLIPMAX;
  var CLIPMIN;
  function clipExtent(extent) {
    return getCallableRef('clipLine', function ($receiver, stream) {
      return $receiver.clipLine_k25lbv$(stream);
    }.bind(null, new ClipRectangle(extent)));
  }
  function clipRectangle(x0, y0, x1, y1) {
    return getCallableRef('clipLine', function ($receiver, stream) {
      return $receiver.clipLine_k25lbv$(stream);
    }.bind(null, new ClipRectangle(new Extent(x0, y0, x1, y1))));
  }
  function ClipRectangle(extent) {
    this.extent = extent;
    this.interpolateFunction = new ClipRectangle$interpolateFunction$ObjectLiteral(this);
  }
  ClipRectangle.prototype.pointVisible_lu1900$ = function (x, y) {
    return rangeTo(this.extent.x0, this.extent.x1).contains_mef7kx$(x) && rangeTo(this.extent.y0, this.extent.y1).contains_mef7kx$(y);
  };
  function ClipRectangle$clipLine$ObjectLiteral(closure$stream, this$ClipRectangle) {
    this.closure$stream = closure$stream;
    this.this$ClipRectangle = this$ClipRectangle;
    this.clean_wun4t1$_0 = 0;
    this.activeStream_0 = closure$stream;
    this.bufferStream_0 = new ClipBuffer();
    this.x___0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.y___0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.v___0 = false;
    this.x__0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.y__0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.v__0 = false;
    this.segments_0 = null;
    this.ring_0 = null;
    this.polygon_0 = null;
    this.first_0 = false;
    this.currentPoint_0 = getCallableRef('justPoint', function ($receiver, x, y) {
      return $receiver.justPoint_0(x, y), Unit;
    }.bind(null, this));
  }
  Object.defineProperty(ClipRectangle$clipLine$ObjectLiteral.prototype, 'clean', {
    get: function () {
      return this.clean_wun4t1$_0;
    },
    set: function (clean) {
      this.clean_wun4t1$_0 = clean;
    }
  });
  ClipRectangle$clipLine$ObjectLiteral.prototype.point_yvo9jy$ = function (x, y, z) {
    this.currentPoint_0(x, y);
  };
  ClipRectangle$clipLine$ObjectLiteral.prototype.lineStart = function () {
    this.currentPoint_0 = getCallableRef('linePoint', function ($receiver, x, y) {
      return $receiver.linePoint_0(x, y), Unit;
    }.bind(null, this));
    var poly = this.polygon_0;
    if (poly != null) {
      var r = ArrayList_init();
      this.ring_0 = r;
      poly.add_11rb$(r);
    }
    this.first_0 = true;
    this.v__0 = false;
    this.x__0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.y__0 = kotlin_js_internal_DoubleCompanionObject.NaN;
  };
  ClipRectangle$clipLine$ObjectLiteral.prototype.lineEnd = function () {
    if (this.segments_0 != null) {
      this.linePoint_0(this.x___0, this.y___0);
      if (this.v___0 && this.v__0)
        this.bufferStream_0.rejoin();
      ensureNotNull(this.segments_0).add_11rb$(flatten(this.bufferStream_0.result()));
    }
    this.currentPoint_0 = getCallableRef('justPoint', function ($receiver, x, y) {
      return $receiver.justPoint_0(x, y), Unit;
    }.bind(null, this));
    if (this.v__0)
      this.activeStream_0.lineEnd();
  };
  ClipRectangle$clipLine$ObjectLiteral.prototype.polygonStart = function () {
    this.activeStream_0 = this.bufferStream_0;
    this.segments_0 = ArrayList_init();
    this.polygon_0 = ArrayList_init();
    this.clean = 1;
  };
  function ClipRectangle$clipLine$ObjectLiteral$polygonEnd$lambda(this$ClipRectangle) {
    return function (o1, o2) {
      return this$ClipRectangle.comparePoint_0(o1.point, o2.point);
    };
  }
  function Comparator$ObjectLiteral_1(closure$comparison) {
    this.closure$comparison = closure$comparison;
  }
  Comparator$ObjectLiteral_1.prototype.compare = function (a, b) {
    return this.closure$comparison(a, b);
  };
  Comparator$ObjectLiteral_1.$metadata$ = {kind: Kind_CLASS, interfaces: [Comparator]};
  ClipRectangle$clipLine$ObjectLiteral.prototype.polygonEnd = function () {
    var tmp$, tmp$_0;
    var startInside = this.polygonInside_0() !== 0;
    var cleanInside = this.clean !== 0 && startInside;
    var visible = (tmp$_0 = (tmp$ = this.segments_0) != null ? !tmp$.isEmpty() : null) != null ? tmp$_0 : false;
    if (cleanInside || visible) {
      this.closure$stream.polygonStart();
      if (cleanInside) {
        this.closure$stream.lineStart();
        this.this$ClipRectangle.interpolate_ak3pfj$(null, null, 1, this.closure$stream);
        this.closure$stream.lineEnd();
      }
      if (visible) {
        rejoin(ensureNotNull(this.segments_0), new Comparator$ObjectLiteral_1(ClipRectangle$clipLine$ObjectLiteral$polygonEnd$lambda(this.this$ClipRectangle)), startInside, this.this$ClipRectangle.interpolateFunction, this.closure$stream);
      }
      this.closure$stream.polygonEnd();
    }
    this.activeStream_0 = this.closure$stream;
    this.segments_0 = null;
    this.polygon_0 = null;
    this.ring_0 = null;
  };
  ClipRectangle$clipLine$ObjectLiteral.prototype.justPoint_0 = function (x, y) {
    if (this.this$ClipRectangle.pointVisible_lu1900$(x, y)) {
      this.activeStream_0.point_yvo9jy$(x, y, 0.0);
    }
  };
  ClipRectangle$clipLine$ObjectLiteral.prototype.linePoint_0 = function (x, y) {
    var tmp$;
    var newX = x;
    var newY = y;
    var v = this.this$ClipRectangle.pointVisible_lu1900$(newX, newY);
    if (this.polygon_0 != null)
      (tmp$ = this.ring_0) != null ? tmp$.add_11rb$(new Float64Array([newX, newY])) : null;
    if (this.first_0) {
      this.x___0 = newX;
      this.y___0 = newY;
      this.v___0 = v;
      this.first_0 = false;
      if (v) {
        this.activeStream_0.lineStart();
        this.activeStream_0.point_yvo9jy$(newX, newY, 0.0);
      }
    }
     else {
      if (v && this.v__0)
        this.activeStream_0.point_yvo9jy$(newX, newY, 0.0);
      else {
        this.x__0 = coerceIn(this.x__0, CLIPMIN, CLIPMAX);
        this.y__0 = coerceIn(this.y__0, CLIPMIN, CLIPMAX);
        newX = coerceIn(newX, CLIPMIN, CLIPMAX);
        newY = coerceIn(newY, CLIPMIN, CLIPMAX);
        var a = new Float64Array([this.x__0, this.y__0]);
        var b = new Float64Array([newX, newY]);
        if (this.this$ClipRectangle.clipLine_0(a, b, this.this$ClipRectangle.extent)) {
          if (!this.v__0) {
            this.activeStream_0.lineStart();
            this.activeStream_0.point_yvo9jy$(a[0], a[1], 0.0);
          }
          this.activeStream_0.point_yvo9jy$(b[0], b[1], 0.0);
          if (!v)
            this.activeStream_0.lineEnd();
          this.clean = 0;
        }
         else if (v) {
          this.activeStream_0.lineStart();
          this.activeStream_0.point_yvo9jy$(newX, newY, 0.0);
          this.clean = 0;
        }
      }
    }
    this.x__0 = newX;
    this.y__0 = newY;
    this.v__0 = v;
  };
  ClipRectangle$clipLine$ObjectLiteral.prototype.polygonInside_0 = function () {
    var tmp$;
    var winding = 0;
    var ring;
    var j;
    var m;
    var point;
    var a0;
    var a1;
    var b0;
    var b1;
    tmp$ = this.polygon_0;
    if (tmp$ == null) {
      throw IllegalStateException_init();
    }
    var poly = tmp$;
    for (var i = 0; i !== poly.size; ++i) {
      ring = poly.get_za3lpa$(i);
      j = 1;
      m = ring.size;
      point = ring.get_za3lpa$(0);
      b0 = point[0];
      b1 = point[1];
      while (j < m) {
        a0 = b0;
        a1 = b1;
        point = ring.get_za3lpa$(j);
        b0 = point[0];
        b1 = point[1];
        if (a1 <= this.this$ClipRectangle.extent.y1) {
          if (b1 > this.this$ClipRectangle.extent.y1 && (b0 - a0) * (this.this$ClipRectangle.extent.y1 - a1) > (b1 - a1) * (this.this$ClipRectangle.extent.x0 - a0))
            winding = winding + 1 | 0;
        }
         else {
          if (b1 <= this.this$ClipRectangle.extent.y1 && (b0 - a0) * (this.this$ClipRectangle.extent.y1 - a1) < (b1 - a1) * (this.this$ClipRectangle.extent.x0 - a0))
            winding = winding - 1 | 0;
        }
        j = j + 1 | 0;
      }
    }
    return winding;
  };
  ClipRectangle$clipLine$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [ClipStream]
  };
  ClipRectangle.prototype.clipLine_k25lbv$ = function (stream) {
    return new ClipRectangle$clipLine$ObjectLiteral(stream, this);
  };
  ClipRectangle.prototype.interpolate_ak3pfj$ = function (from, to, direction, stream) {
    var a = from == null ? 0 : this.corner_0(from, direction);
    var a1 = from == null ? 0 : this.corner_0(to, direction);
    if (from == null || a !== a1 || (to != null && this.comparePoint_0(from, to) < 0 ^ direction > 0)) {
      do {
        stream.point_yvo9jy$(a === 0 || a === 3 ? this.extent.x0 : this.extent.x1, a > 1 ? this.extent.y1 : this.extent.y0, 0.0);
        a = (a + direction + 4 | 0) % 4;
      }
       while (a !== a1);
    }
     else if (to != null)
      stream.point_yvo9jy$(to[0], to[1], 0.0);
  };
  ClipRectangle.prototype.corner_0 = function (p, direction) {
    var tmp$;
    if (p == null)
      return direction > 0 ? 3 : 2;
    if (direction > 0) {
      var x = p[0] - this.extent.x0;
      if (Math_0.abs(x) < math.EPSILON)
        tmp$ = 0;
      else {
        var x_0 = p[0] - this.extent.x1;
        if (Math_0.abs(x_0) < math.EPSILON)
          tmp$ = 2;
        else {
          var x_1 = p[1] - this.extent.y1;
          if (Math_0.abs(x_1) < math.EPSILON)
            tmp$ = 1;
          else
            tmp$ = 3;
        }
      }
    }
     else {
      var x_2 = p[0] - this.extent.x0;
      if (Math_0.abs(x_2) < math.EPSILON)
        tmp$ = 0;
      else {
        var x_3 = p[0] - this.extent.x1;
        if (Math_0.abs(x_3) < math.EPSILON)
          tmp$ = 2;
        else {
          var x_4 = p[1] - this.extent.y1;
          if (Math_0.abs(x_4) < math.EPSILON)
            tmp$ = 1;
          else
            tmp$ = 3;
        }
      }
    }
    return tmp$;
  };
  ClipRectangle.prototype.comparePoint_0 = function (a, b) {
    var tmp$;
    var ca = this.corner_0(a, 1);
    var cb = this.corner_0(b, 1);
    if (ca !== cb)
      tmp$ = Kotlin.primitiveCompareTo(ca, cb);
    else if (ca === 0)
      tmp$ = Kotlin.compareTo(b[1], a[1]);
    else if (ca === 1)
      tmp$ = Kotlin.compareTo(a[0], b[0]);
    else if (ca === 2)
      tmp$ = Kotlin.compareTo(a[1], b[1]);
    else
      tmp$ = Kotlin.compareTo(b[0], a[0]);
    return tmp$;
  };
  ClipRectangle.prototype.clipLine_0 = function (a, b, extent) {
    var ax = a[0];
    var ay = a[1];
    var bx = b[0];
    var by = b[1];
    var t0 = 0.0;
    var t1 = 1.0;
    var dx = bx - ax;
    var dy = by - ay;
    var r = extent.x0 - ax;
    if (dx === 0.0 && r > 0)
      return false;
    r /= dx;
    if (dx < 0) {
      if (r < t0)
        return false;
      if (r < t1)
        t1 = r;
    }
     else if (dx > 0) {
      if (r > t1)
        return false;
      if (r > t0)
        t0 = r;
    }
    r = extent.x1 - ax;
    if (dx === 0.0 && r < 0)
      return false;
    r /= dx;
    if (dx < 0) {
      if (r > t1)
        return false;
      if (r > t0)
        t0 = r;
    }
     else if (dx > 0) {
      if (r < t0)
        return false;
      if (r < t1)
        t1 = r;
    }
    r = extent.y0 - ay;
    if (dy === 0.0 && r > 0)
      return false;
    r /= dy;
    if (dy < 0) {
      if (r < t0)
        return false;
      if (r < t1)
        t1 = r;
    }
     else if (dy > 0) {
      if (r > t1)
        return false;
      if (r > t0)
        t0 = r;
    }
    r = extent.y1 - ay;
    if (dy === 0.0 && r < 0)
      return false;
    r /= dy;
    if (dy < 0) {
      if (r > t1)
        return false;
      if (r > t0)
        t0 = r;
    }
     else if (dy > 0) {
      if (r < t0)
        return false;
      if (r < t1)
        t1 = r;
    }
    if (t0 > 0) {
      a[0] = ax + t0 * dx;
      a[1] = ay + t0 * dy;
    }
    if (t1 < 1) {
      b[0] = ax + t1 * dx;
      b[1] = ay + t1 * dy;
    }
    return true;
  };
  function ClipRectangle$interpolateFunction$ObjectLiteral(this$ClipRectangle) {
    this.this$ClipRectangle = this$ClipRectangle;
  }
  ClipRectangle$interpolateFunction$ObjectLiteral.prototype.invoke_3h98hb$ = function (from, to, direction, stream) {
    this.this$ClipRectangle.interpolate_ak3pfj$(from, to, direction, stream);
  };
  ClipRectangle$interpolateFunction$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [InterpolateFunction]
  };
  ClipRectangle.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ClipRectangle',
    interfaces: [Clippable]
  };
  function Intersection(point, points, other, entry, visited, next, previous) {
    if (visited === void 0)
      visited = false;
    if (next === void 0)
      next = null;
    if (previous === void 0)
      previous = null;
    this.point = point;
    this.points = points;
    this.other = other;
    this.entry = entry;
    this.visited = visited;
    this.next = next;
    this.previous = previous;
  }
  Intersection.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Intersection',
    interfaces: []
  };
  Intersection.prototype.component1 = function () {
    return this.point;
  };
  Intersection.prototype.component2 = function () {
    return this.points;
  };
  Intersection.prototype.component3 = function () {
    return this.other;
  };
  Intersection.prototype.component4 = function () {
    return this.entry;
  };
  Intersection.prototype.component5 = function () {
    return this.visited;
  };
  Intersection.prototype.component6 = function () {
    return this.next;
  };
  Intersection.prototype.component7 = function () {
    return this.previous;
  };
  Intersection.prototype.copy_c9i66i$ = function (point, points, other, entry, visited, next, previous) {
    return new Intersection(point === void 0 ? this.point : point, points === void 0 ? this.points : points, other === void 0 ? this.other : other, entry === void 0 ? this.entry : entry, visited === void 0 ? this.visited : visited, next === void 0 ? this.next : next, previous === void 0 ? this.previous : previous);
  };
  Intersection.prototype.toString = function () {
    return 'Intersection(point=' + Kotlin.toString(this.point) + (', points=' + Kotlin.toString(this.points)) + (', other=' + Kotlin.toString(this.other)) + (', entry=' + Kotlin.toString(this.entry)) + (', visited=' + Kotlin.toString(this.visited)) + (', next=' + Kotlin.toString(this.next)) + (', previous=' + Kotlin.toString(this.previous)) + ')';
  };
  Intersection.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.point) | 0;
    result = result * 31 + Kotlin.hashCode(this.points) | 0;
    result = result * 31 + Kotlin.hashCode(this.other) | 0;
    result = result * 31 + Kotlin.hashCode(this.entry) | 0;
    result = result * 31 + Kotlin.hashCode(this.visited) | 0;
    result = result * 31 + Kotlin.hashCode(this.next) | 0;
    result = result * 31 + Kotlin.hashCode(this.previous) | 0;
    return result;
  };
  Intersection.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.point, other.point) && Kotlin.equals(this.points, other.points) && Kotlin.equals(this.other, other.other) && Kotlin.equals(this.entry, other.entry) && Kotlin.equals(this.visited, other.visited) && Kotlin.equals(this.next, other.next) && Kotlin.equals(this.previous, other.previous)))));
  };
  function InterpolateFunction() {
  }
  InterpolateFunction.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'InterpolateFunction',
    interfaces: []
  };
  function rejoin(segments, compareIntersection, startInside, interpolateFunction, stream) {
    var subject = ArrayList_init();
    var clip = ArrayList_init();
    var tmp$;
    tmp$ = segments.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      var n = element.size - 1 | 0;
      if (n <= 0)
        return;
      var p0 = {v: element.get_za3lpa$(0)};
      var p1 = element.get_za3lpa$(n);
      if (pointEqual(p0.v, p1)) {
        stream.lineStart();
        var tmp$_0;
        tmp$_0 = until(0, n).iterator();
        while (tmp$_0.hasNext()) {
          var element_0 = tmp$_0.next();
          p0.v = element.get_za3lpa$(element_0);
          stream.point_yvo9jy$(p0.v[0], p0.v[1], 0.0);
        }
        stream.lineEnd();
        return;
      }
      var x = new Intersection(p0.v, element, null, true);
      subject.add_11rb$(x);
      x.other = new Intersection(p0.v, null, x, false);
      clip.add_11rb$(ensureNotNull(x.other));
      x = new Intersection(p1, element, null, false);
      subject.add_11rb$(x);
      x.other = new Intersection(p1, null, x, true);
      clip.add_11rb$(ensureNotNull(x.other));
    }
    if (subject.isEmpty())
      return;
    sortWith(clip, compareIntersection);
    link(subject);
    link(clip);
    var newStartInside = {v: startInside};
    var tmp$_1;
    tmp$_1 = clip.iterator();
    while (tmp$_1.hasNext()) {
      var element_1 = tmp$_1.next();
      newStartInside.v = !newStartInside.v;
      element_1.entry = newStartInside.v;
    }
    var start = subject.get_za3lpa$(0);
    while (true) {
      var current = start;
      var isSubject = true;
      while (current.visited) {
        current = ensureNotNull(current.next);
        if (current != null ? current.equals(start) : null)
          return;
      }
      var points = current.points;
      stream.lineStart();
      do {
        ensureNotNull(current.other).visited = true;
        current.visited = true;
        if (current.entry) {
          if (isSubject) {
            if (points != null) {
              var tmp$_2;
              tmp$_2 = points.iterator();
              while (tmp$_2.hasNext()) {
                var element_2 = tmp$_2.next();
                stream.point_yvo9jy$(element_2[0], element_2[1], 0.0);
              }
            }
          }
           else {
            interpolateFunction.invoke_3h98hb$(current.point, ensureNotNull(current.next).point, 1, stream);
          }
          current = ensureNotNull(current.next);
        }
         else {
          if (isSubject) {
            points = ensureNotNull(current.previous).points;
            if (points != null) {
              var tmp$_3;
              tmp$_3 = asReversed(points).iterator();
              while (tmp$_3.hasNext()) {
                var element_3 = tmp$_3.next();
                stream.point_yvo9jy$(element_3[0], element_3[1], 0.0);
              }
            }
          }
           else {
            interpolateFunction.invoke_3h98hb$(current.point, ensureNotNull(current.previous).point, -1, stream);
          }
          current = ensureNotNull(current.previous);
        }
        current = ensureNotNull(current.other);
        points = current.points;
        isSubject = !isSubject;
      }
       while (!current.visited);
      stream.lineEnd();
    }
  }
  function link(list) {
    if (list.isEmpty())
      return;
    var a = {v: first(list)};
    var b = {v: null};
    var tmp$;
    tmp$ = until(1, list.size).iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      a.v.next = list.get_za3lpa$(element);
      b.v = list.get_za3lpa$(element);
      b.v.previous = a.v;
      a.v = b.v;
    }
    b.v = first(list);
    a.v.next = b.v;
    b.v.previous = a.v;
  }
  function pointEqual(p0, p1) {
    var x = p0[0] - p1[0];
    var tmp$ = Math_0.abs(x) < math.EPSILON;
    if (tmp$) {
      var x_0 = p0[1] - p1[1];
      tmp$ = Math_0.abs(x_0) < math.EPSILON;
    }
    return tmp$;
  }
  function geoPath(projection, context) {
    if (projection === void 0)
      projection = null;
    if (context === void 0)
      context = null;
    return new GeoPath(projection == null ? identityProjection() : projection, context);
  }
  function GeoPath(projection, context) {
    this.projection = projection;
    this.context = context;
    this.pathArea_0 = new PathArea();
    this.pathBounds_0 = new PathBounds();
    this.pathCentroid_0 = new PathCentroid();
    this.pathMeasure_0 = new PathMeasure();
    this.contextStream_0 = this.context != null ? new PathContext(this.context) : null;
  }
  GeoPath.prototype.path_6ux19g$ = function (geo) {
    if (this.context == null) {
      var message = 'Cannot use GeoPath.svgPath() without a valid context.';
      throw IllegalArgumentException_init(message.toString());
    }
    if (this.contextStream_0 == null) {
      var message_0 = 'Cannot use GeoPath.svgPath() without a valid context.';
      throw IllegalArgumentException_init(message_0.toString());
    }
    stream(geo, this.projection.stream_k25lbv$(this.contextStream_0));
    return this.context;
  };
  GeoPath.prototype.centroid_6ux19g$ = function (geo) {
    stream(geo, this.projection.stream_k25lbv$(this.pathCentroid_0));
    return this.pathCentroid_0.result_8be2vx$();
  };
  GeoPath.prototype.area_6ux19g$ = function (geo) {
    stream(geo, this.projection.stream_k25lbv$(this.pathArea_0));
    return this.pathArea_0.result();
  };
  GeoPath.prototype.bounds_6ux19g$ = function (geo) {
    stream(geo, this.projection.stream_k25lbv$(this.pathBounds_0));
    return this.pathBounds_0.result();
  };
  GeoPath.prototype.measure_6ux19g$ = function (geo) {
    stream(geo, this.projection.stream_k25lbv$(this.pathMeasure_0));
    return this.pathMeasure_0.result();
  };
  GeoPath.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'GeoPath',
    interfaces: []
  };
  function PathArea() {
    this.areaSum_0 = 0.0;
    this.areaRingSum_0 = 0.0;
    this.x00_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.y00_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.x0_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.y0_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.currentPoint_0 = noop2;
    this.currentLineStart_0 = noop;
    this.currentLineEnd_0 = noop;
  }
  PathArea.prototype.result = function () {
    var a = this.areaSum_0 / 2.0;
    this.areaSum_0 = 0.0;
    return a;
  };
  PathArea.prototype.point_yvo9jy$ = function (x, y, z) {
    this.currentPoint_0(x, y);
  };
  PathArea.prototype.lineStart = function () {
    this.currentLineStart_0();
  };
  PathArea.prototype.lineEnd = function () {
    this.currentLineEnd_0();
  };
  PathArea.prototype.polygonStart = function () {
    this.currentLineStart_0 = getCallableRef('areaRingStart', function ($receiver) {
      return $receiver.areaRingStart_0(), Unit;
    }.bind(null, this));
    this.currentLineEnd_0 = getCallableRef('areaRingEnd', function ($receiver) {
      return $receiver.areaRingEnd_0(), Unit;
    }.bind(null, this));
  };
  PathArea.prototype.polygonEnd = function () {
    this.currentLineStart_0 = noop;
    this.currentLineEnd_0 = noop;
    this.currentPoint_0 = noop2;
    var x = this.areaRingSum_0;
    this.areaSum_0 += Math_0.abs(x);
    this.areaRingSum_0 = 0.0;
  };
  PathArea.prototype.areaRingStart_0 = function () {
    this.currentPoint_0 = getCallableRef('areaPointFirst', function ($receiver, x, y) {
      return $receiver.areaPointFirst_0(x, y), Unit;
    }.bind(null, this));
  };
  PathArea.prototype.areaPointFirst_0 = function (x, y) {
    this.currentPoint_0 = getCallableRef('areaPoint', function ($receiver, x, y) {
      return $receiver.areaPoint_0(x, y), Unit;
    }.bind(null, this));
    this.x00_0 = x;
    this.x0_0 = x;
    this.y00_0 = y;
    this.y0_0 = y;
  };
  PathArea.prototype.areaPoint_0 = function (x, y) {
    this.areaRingSum_0 += this.y0_0 * x - this.x0_0 * y;
    this.x0_0 = x;
    this.y0_0 = y;
  };
  PathArea.prototype.areaRingEnd_0 = function () {
    this.areaPoint_0(this.x00_0, this.y00_0);
  };
  PathArea.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'PathArea',
    interfaces: [Stream]
  };
  function PathBounds() {
    this.bounds_0 = new Extent(kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY, kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY, kotlin_js_internal_DoubleCompanionObject.NEGATIVE_INFINITY, kotlin_js_internal_DoubleCompanionObject.NEGATIVE_INFINITY);
  }
  PathBounds.prototype.result = function () {
    var result = this.bounds_0.copy();
    this.bounds_0 = new Extent(kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY, kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY, kotlin_js_internal_DoubleCompanionObject.NEGATIVE_INFINITY, kotlin_js_internal_DoubleCompanionObject.NEGATIVE_INFINITY);
    return result;
  };
  PathBounds.prototype.point_yvo9jy$ = function (x, y, z) {
    if (x < this.bounds_0.x0)
      this.bounds_0.x0 = x;
    if (x > this.bounds_0.x1)
      this.bounds_0.x1 = x;
    if (y < this.bounds_0.y0)
      this.bounds_0.y0 = y;
    if (y > this.bounds_0.y1)
      this.bounds_0.y1 = y;
  };
  PathBounds.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'PathBounds',
    interfaces: [Stream]
  };
  function PathCentroid() {
    this._X0_0 = 0.0;
    this._Y0_0 = 0.0;
    this._Z0_0 = 0.0;
    this._X1_0 = 0.0;
    this._Y1_0 = 0.0;
    this._Z1_0 = 0.0;
    this._X2_0 = 0.0;
    this._Y2_0 = 0.0;
    this._Z2_0 = 0.0;
    this.x00_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.y00_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.x0_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.y0_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.currentPoint_0 = getCallableRef('centroidPoint', function ($receiver, x, y) {
      return $receiver.centroidPoint_0(x, y), Unit;
    }.bind(null, this));
    this.currentLineStart_0 = getCallableRef('centroidLineStart', function ($receiver) {
      return $receiver.centroidLineStart_0(), Unit;
    }.bind(null, this));
    this.currentLineEnd_0 = getCallableRef('centroidLineEnd', function ($receiver) {
      return $receiver.centroidLineEnd_0(), Unit;
    }.bind(null, this));
  }
  PathCentroid.prototype.result_8be2vx$ = function () {
    var tmp$;
    if (this._Z2_0 !== 0.0)
      tmp$ = new Float64Array([this._X2_0 / this._Z2_0, this._Y2_0 / this._Z2_0]);
    else if (this._Z1_0 !== 0.0)
      tmp$ = new Float64Array([this._X1_0 / this._Z1_0, this._Y1_0 / this._Z1_0]);
    else if (this._Z0_0 !== 0.0)
      tmp$ = new Float64Array([this._X0_0 / this._Z0_0, this._Y0_0 / this._Z0_0]);
    else
      tmp$ = new Float64Array([kotlin_js_internal_DoubleCompanionObject.NaN, kotlin_js_internal_DoubleCompanionObject.NaN]);
    var centroid = tmp$;
    this._X0_0 = 0.0;
    this._Y0_0 = 0.0;
    this._Z0_0 = 0.0;
    this._X1_0 = 0.0;
    this._Y1_0 = 0.0;
    this._Z1_0 = 0.0;
    this._X2_0 = 0.0;
    this._Y2_0 = 0.0;
    this._Z2_0 = 0.0;
    return centroid;
  };
  PathCentroid.prototype.point_yvo9jy$ = function (x, y, z) {
    this.currentPoint_0(x, y);
  };
  PathCentroid.prototype.lineStart = function () {
    this.currentLineStart_0();
  };
  PathCentroid.prototype.lineEnd = function () {
    this.currentLineEnd_0();
  };
  PathCentroid.prototype.polygonStart = function () {
    this.currentLineStart_0 = getCallableRef('centroidRingStart', function ($receiver) {
      return $receiver.centroidRingStart_0(), Unit;
    }.bind(null, this));
    this.currentLineEnd_0 = getCallableRef('centroidRingEnd', function ($receiver) {
      return $receiver.centroidRingEnd_0(), Unit;
    }.bind(null, this));
  };
  PathCentroid.prototype.polygonEnd = function () {
    this.currentPoint_0 = getCallableRef('centroidPoint', function ($receiver, x, y) {
      return $receiver.centroidPoint_0(x, y), Unit;
    }.bind(null, this));
    this.currentLineStart_0 = getCallableRef('centroidLineStart', function ($receiver) {
      return $receiver.centroidLineStart_0(), Unit;
    }.bind(null, this));
    this.currentLineEnd_0 = getCallableRef('centroidLineEnd', function ($receiver) {
      return $receiver.centroidLineEnd_0(), Unit;
    }.bind(null, this));
  };
  PathCentroid.prototype.centroidPoint_0 = function (x, y) {
    this._X0_0 += x;
    this._Y0_0 += y;
    this._Z0_0 = this._Z0_0 + 1;
  };
  PathCentroid.prototype.centroidLineStart_0 = function () {
    this.currentPoint_0 = getCallableRef('centroidPointFirstLine', function ($receiver, x, y) {
      return $receiver.centroidPointFirstLine_0(x, y), Unit;
    }.bind(null, this));
  };
  PathCentroid.prototype.centroidPointFirstLine_0 = function (x, y) {
    this.currentPoint_0 = getCallableRef('centroidPointLine', function ($receiver, x, y) {
      return $receiver.centroidPointLine_0(x, y), Unit;
    }.bind(null, this));
    this.x0_0 = x;
    this.y0_0 = y;
    this.centroidPoint_0(x, y);
  };
  PathCentroid.prototype.centroidPointLine_0 = function (x, y) {
    var dx = x - this.x0_0;
    var dy = y - this.y0_0;
    var x_0 = dx * dx + dy * dy;
    var dz = Math_0.sqrt(x_0);
    this._X1_0 += dz * (this.x0_0 + x) / 2.0;
    this._Y1_0 += dz * (this.y0_0 + y) / 2.0;
    this._Z1_0 += dz;
    this.x0_0 = x;
    this.y0_0 = y;
    this.centroidPoint_0(x, y);
  };
  PathCentroid.prototype.centroidLineEnd_0 = function () {
    this.currentPoint_0 = getCallableRef('centroidPoint', function ($receiver, x, y) {
      return $receiver.centroidPoint_0(x, y), Unit;
    }.bind(null, this));
  };
  PathCentroid.prototype.centroidRingStart_0 = function () {
    this.currentPoint_0 = getCallableRef('centroidPointFirstRing', function ($receiver, x, y) {
      return $receiver.centroidPointFirstRing_0(x, y), Unit;
    }.bind(null, this));
  };
  PathCentroid.prototype.centroidRingEnd_0 = function () {
    this.centroidPointRing_0(this.x00_0, this.y00_0);
  };
  PathCentroid.prototype.centroidPointFirstRing_0 = function (x, y) {
    this.currentPoint_0 = getCallableRef('centroidPointRing', function ($receiver, x, y) {
      return $receiver.centroidPointRing_0(x, y), Unit;
    }.bind(null, this));
    this.x00_0 = x;
    this.y00_0 = y;
    this.x0_0 = x;
    this.y0_0 = y;
    this.centroidPoint_0(x, y);
  };
  PathCentroid.prototype.centroidPointRing_0 = function (x, y) {
    var dx = x - this.x0_0;
    var dy = y - this.y0_0;
    var x_0 = dx * dx + dy * dy;
    var dz = Math_0.sqrt(x_0);
    this._X1_0 += dz * (this.x0_0 + x) / 2.0;
    this._Y1_0 += dz * (this.y0_0 + y) / 2.0;
    this._Z1_0 += dz;
    dz = this.y0_0 * x - this.x0_0 * y;
    this._X2_0 += dz * (this.x0_0 + x);
    this._Y2_0 += dz * (this.y0_0 + y);
    this._Z2_0 += dz * 3;
    this.x0_0 = x;
    this.y0_0 = y;
    this.centroidPoint_0(x, y);
  };
  PathCentroid.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'PathCentroid',
    interfaces: [Stream]
  };
  function PathContext(context) {
    this.context_0 = context;
    this.pointRadius = 4.5;
    this.line_0 = false;
    this.point_0 = -1;
  }
  PathContext.prototype.polygonStart = function () {
    this.line_0 = true;
  };
  PathContext.prototype.polygonEnd = function () {
    this.line_0 = false;
  };
  PathContext.prototype.lineStart = function () {
    this.point_0 = 0;
  };
  PathContext.prototype.lineEnd = function () {
    if (this.line_0)
      this.context_0.closePath();
    this.point_0 = -1;
  };
  PathContext.prototype.point_yvo9jy$ = function (x, y, z) {
    switch (this.point_0) {
      case 0:
        this.context_0.moveTo_lu1900$(x, y);
        this.point_0 = 1;
        break;
      case 1:
        this.context_0.lineTo_lu1900$(x, y);
        break;
      default:this.context_0.moveTo_lu1900$(x + this.pointRadius, y);
        this.context_0.arc_6p3vsx$(x, y, this.pointRadius, 0.0, math.TAU, false);
        break;
    }
  };
  PathContext.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'PathContext',
    interfaces: [Stream]
  };
  function PathMeasure() {
    this.lengthSum_0 = 0.0;
    this.lengthRing_0 = false;
    this.x00_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.y00_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.x0_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.y0_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.currentPoint_0 = noop2;
  }
  PathMeasure.prototype.result = function () {
    var result = this.lengthSum_0;
    this.lengthSum_0 = 0.0;
    return result;
  };
  PathMeasure.prototype.point_yvo9jy$ = function (x, y, z) {
    this.currentPoint_0(x, y);
  };
  PathMeasure.prototype.lineStart = function () {
    this.currentPoint_0 = getCallableRef('lengthPointFirst', function ($receiver, x, y) {
      return $receiver.lengthPointFirst_0(x, y), Unit;
    }.bind(null, this));
  };
  PathMeasure.prototype.lineEnd = function () {
    if (this.lengthRing_0)
      this.lengthPoint_0(this.x00_0, this.y00_0);
    this.currentPoint_0 = noop2;
  };
  PathMeasure.prototype.polygonStart = function () {
    this.lengthRing_0 = true;
  };
  PathMeasure.prototype.polygonEnd = function () {
    this.lengthRing_0 = false;
  };
  PathMeasure.prototype.lengthPointFirst_0 = function (x, y) {
    this.currentPoint_0 = getCallableRef('lengthPoint', function ($receiver, x, y) {
      return $receiver.lengthPoint_0(x, y), Unit;
    }.bind(null, this));
    this.x0_0 = x;
    this.x00_0 = x;
    this.y0_0 = y;
    this.y00_0 = y;
  };
  PathMeasure.prototype.lengthPoint_0 = function (x, y) {
    this.x0_0 -= x;
    this.y0_0 -= y;
    var x_0 = this.x0_0 * this.x0_0 + this.y0_0 * this.y0_0;
    this.lengthSum_0 += Math_0.sqrt(x_0);
    this.x0_0 = x;
    this.y0_0 = y;
  };
  PathMeasure.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'PathMeasure',
    interfaces: [Stream]
  };
  function MultiplexStream(streams) {
    this.streams = streams;
  }
  MultiplexStream.prototype.point_yvo9jy$ = function (x, y, z) {
    var tmp$;
    tmp$ = this.streams.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      element.point_yvo9jy$(x, y, z);
    }
  };
  MultiplexStream.prototype.lineStart = function () {
    var tmp$;
    tmp$ = this.streams.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      element.lineStart();
    }
  };
  MultiplexStream.prototype.lineEnd = function () {
    var tmp$;
    tmp$ = this.streams.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      element.lineEnd();
    }
  };
  MultiplexStream.prototype.polygonStart = function () {
    var tmp$;
    tmp$ = this.streams.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      element.polygonStart();
    }
  };
  MultiplexStream.prototype.polygonEnd = function () {
    var tmp$;
    tmp$ = this.streams.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      element.polygonEnd();
    }
  };
  MultiplexStream.prototype.sphere = function () {
    var tmp$;
    tmp$ = this.streams.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      element.sphere();
    }
  };
  MultiplexStream.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'MultiplexStream',
    interfaces: [Stream]
  };
  function alberUSAProjection$lambda($receiver) {
    return Unit;
  }
  function alberUSAProjection() {
    return alberUSAProjection_0(alberUSAProjection$lambda);
  }
  function alberUSAProjection_0(init) {
    var $receiver = new AlberUSAProjection();
    $receiver.scale = 1070.0;
    init($receiver);
    return $receiver;
  }
  function AlberUSAProjection() {
    this.lower48 = albersProjection();
    this.alaska = conicEqualAreaProjection_0(AlberUSAProjection$alaska$lambda);
    this.hawaii = conicEqualAreaProjection_0(AlberUSAProjection$hawaii$lambda);
    this.translateX = 0.0;
    this.translateY = 0.0;
    this.pointStream = new AlberUSAProjection$pointStream$ObjectLiteral(this);
    this.point = new Float64Array([0.0, 0.0]);
    this.pointLower48 = this.lower48.stream_k25lbv$(this.pointStream);
    this.pointHawaii = this.hawaii.stream_k25lbv$(this.pointStream);
    this.pointAlaska = this.alaska.stream_k25lbv$(this.pointStream);
    this.cache_0 = null;
    this.cacheStream_0 = null;
  }
  AlberUSAProjection.prototype.project_lu1900$ = function (x, y) {
    var tmp$;
    var k = this.lower48.scale;
    var t = this.lower48.translate;
    var newX = (x - t[0]) / k;
    var newY = (y - t[1]) / k;
    if (newY >= 0.12 && newY < 0.234 && newX >= -0.425 && newX < -0.214)
      tmp$ = this.alaska;
    else if (newY >= 0.166 && newY < 0.234 && newX >= -0.214 && newX < -0.115)
      tmp$ = this.hawaii;
    else
      tmp$ = this.lower48;
    var projection = tmp$;
    return projection.project_lu1900$(x, y);
  };
  AlberUSAProjection.prototype.projectLambda_lu1900$ = function (lambda, phi) {
    return this.project_lu1900$(lambda, phi)[0];
  };
  AlberUSAProjection.prototype.projectPhi_lu1900$ = function (lambda, phi) {
    return this.project_lu1900$(lambda, phi)[1];
  };
  AlberUSAProjection.prototype.invert_lu1900$ = function (x, y) {
    var tmp$;
    var k = this.lower48.scale;
    var t = this.lower48.translate;
    var newX = (x - t[0]) / k;
    var newY = (y - t[1]) / k;
    if (newY >= 0.12 && newY < 0.234 && newX >= -0.425 && newX < -0.214)
      tmp$ = this.alaska;
    else if (newY >= 0.166 && newY < 0.234 && newX >= -0.214 && newX < -0.115)
      tmp$ = this.hawaii;
    else
      tmp$ = this.lower48;
    var projection = tmp$;
    return projection.invert_lu1900$(x, y);
  };
  Object.defineProperty(AlberUSAProjection.prototype, 'center', {
    get: function () {
      return this.lower48.center;
    },
    set: function (value) {
      this.lower48.center = value;
      this.hawaii.center = value;
      this.alaska.center = value;
    }
  });
  Object.defineProperty(AlberUSAProjection.prototype, 'rotate', {
    get: function () {
      return this.lower48.rotate;
    },
    set: function (value) {
      this.lower48.rotate = value;
      this.hawaii.rotate = value;
      this.alaska.rotate = value;
    }
  });
  Object.defineProperty(AlberUSAProjection.prototype, 'preClip', {
    get: function () {
      return this.lower48.preClip;
    },
    set: function (value) {
      this.lower48.preClip = value;
      this.hawaii.preClip = value;
      this.alaska.preClip = value;
    }
  });
  Object.defineProperty(AlberUSAProjection.prototype, 'postClip', {
    get: function () {
      return this.lower48.postClip;
    },
    set: function (value) {
      this.lower48.postClip = value;
      this.hawaii.postClip = value;
      this.alaska.postClip = value;
    }
  });
  Object.defineProperty(AlberUSAProjection.prototype, 'clipAngle', {
    get: function () {
      return this.lower48.clipAngle;
    },
    set: function (value) {
      this.lower48.clipAngle = value;
      this.hawaii.clipAngle = value;
      this.alaska.clipAngle = value;
    }
  });
  Object.defineProperty(AlberUSAProjection.prototype, 'clipExtent', {
    get: function () {
      return this.lower48.clipExtent;
    },
    set: function (value) {
      this.lower48.clipExtent = value;
      this.hawaii.clipExtent = value;
      this.alaska.clipExtent = value;
    }
  });
  AlberUSAProjection.prototype.recenter = function () {
    this.lower48.recenter();
    this.hawaii.recenter();
    this.alaska.recenter();
  };
  AlberUSAProjection.prototype.getCachedStream_0 = function (stream) {
    return this.cache_0 != null && equals(this.cacheStream_0, stream) ? this.cache_0 : null;
  };
  AlberUSAProjection.prototype.cache_1 = function (stream1, stream2) {
    this.cache_0 = stream2;
    this.cacheStream_0 = stream1;
  };
  Object.defineProperty(AlberUSAProjection.prototype, 'translate', {
    get: function () {
      return this.lower48.translate;
    },
    set: function (value) {
      var k = this.lower48.scale;
      this.translateX += value[0];
      this.translateY += value[1];
      var x = this.translateX;
      var y = this.translateY;
      this.lower48.translate = value;
      this.lower48.clipExtent = new Extent(x - 0.455 * k, y - 0.238 * k, x + 0.455 * k, y + 0.238 * k);
      this.pointLower48 = this.lower48.stream_k25lbv$(this.pointStream);
      this.alaska.translate = new Float64Array([x - 0.307 * k, y + 0.201 * k]);
      this.alaska.clipExtent = new Extent(x - 0.425 * k + math.EPSILON, y + 0.12 * k + math.EPSILON, x - 0.214 * k - math.EPSILON, y + 0.234 * k - math.EPSILON);
      this.pointAlaska = this.alaska.stream_k25lbv$(this.pointStream);
      this.hawaii.translate = new Float64Array([x - 0.205 * k, y + 0.212 * k]);
      this.hawaii.clipExtent = new Extent(x - 0.214 * k + math.EPSILON, y + 0.166 * k + math.EPSILON, x - 0.115 * k - math.EPSILON, y + 0.234 * k - math.EPSILON);
      this.pointHawaii = this.hawaii.stream_k25lbv$(this.pointStream);
      this.reset();
    }
  });
  Object.defineProperty(AlberUSAProjection.prototype, 'scale', {
    get: function () {
      return this.lower48.scale;
    },
    set: function (value) {
      this.lower48.scale = value;
      this.alaska.scale = value * 0.35;
      this.hawaii.scale = value;
      this.reset();
    }
  });
  Object.defineProperty(AlberUSAProjection.prototype, 'precision', {
    get: function () {
      return this.lower48.precision;
    },
    set: function (value) {
      this.lower48.precision = value;
      this.alaska.precision = value * 0.35;
      this.hawaii.precision = value;
      this.reset();
    }
  });
  AlberUSAProjection.prototype.stream_k25lbv$ = function (stream) {
    var cachedStream = this.getCachedStream_0(stream);
    if (cachedStream == null) {
      cachedStream = this.fullCycleStream_k25lbv$(stream);
      this.cache_1(cachedStream, cachedStream);
    }
    return cachedStream;
  };
  AlberUSAProjection.prototype.fullCycleStream_k25lbv$ = function (stream) {
    return new MultiplexStream(listOf([this.lower48.stream_k25lbv$(stream), this.alaska.stream_k25lbv$(stream), this.hawaii.stream_k25lbv$(stream)]));
  };
  AlberUSAProjection.prototype.fitExtent_76k4nv$ = function (extent, geo) {
    return fitExtent(this.lower48, extent, geo);
  };
  AlberUSAProjection.prototype.fitWidth_qy4pci$ = function (width, geo) {
    return fitWidth(this.lower48, width, geo);
  };
  AlberUSAProjection.prototype.fitHeight_qy4pci$ = function (height, geo) {
    return fitHeight(this.lower48, height, geo);
  };
  AlberUSAProjection.prototype.fitSize_gd85ts$ = function (width, height, geo) {
    return fitSize(this.lower48, width, height, geo);
  };
  AlberUSAProjection.prototype.reset = function () {
    this.cache_0 = null;
    this.cacheStream_0 = null;
  };
  function AlberUSAProjection$alaska$lambda($receiver) {
    $receiver.rotate = [get_deg(154.0), get_deg(0.0)];
    $receiver.center = [get_deg(-2.0), get_deg(58.5)];
    $receiver.parallels = [get_deg(55.0), get_deg(65.0)];
    return Unit;
  }
  function AlberUSAProjection$hawaii$lambda($receiver) {
    $receiver.rotate = [get_deg(157.0), get_deg(0.0)];
    $receiver.center = [get_deg(-3.0), get_deg(19.9)];
    $receiver.parallels = [get_deg(8.0), get_deg(18.0)];
    return Unit;
  }
  function AlberUSAProjection$pointStream$ObjectLiteral(this$AlberUSAProjection) {
    this.this$AlberUSAProjection = this$AlberUSAProjection;
  }
  AlberUSAProjection$pointStream$ObjectLiteral.prototype.point_yvo9jy$ = function (x, y, z) {
    this.this$AlberUSAProjection.point = new Float64Array([x, y]);
  };
  AlberUSAProjection$pointStream$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [Stream]
  };
  AlberUSAProjection.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'AlberUSAProjection',
    interfaces: [Projection]
  };
  function albersProjection$lambda($receiver) {
    return Unit;
  }
  function albersProjection() {
    return albersProjection_0(albersProjection$lambda);
  }
  function albersProjection$lambda_0(closure$init) {
    return function ($receiver) {
      $receiver.parallels = [get_deg(29.5), get_deg(45.5)];
      $receiver.scale = 1070.0;
      $receiver.translate = new Float64Array([480.0, 250.0]);
      $receiver.rotate = [get_deg(96.0), get_deg(0.0)];
      $receiver.center = [get_deg(-0.6), get_deg(38.7)];
      closure$init($receiver);
      return Unit;
    };
  }
  function albersProjection_0(init) {
    return conicEqualAreaProjection_0(albersProjection$lambda_0(init));
  }
  function azimuthalEqualAreaProjection$lambda($receiver) {
    return Unit;
  }
  function azimuthalEqualAreaProjection() {
    return azimuthalEqualAreaProjection_0(azimuthalEqualAreaProjection$lambda);
  }
  function azimuthalEqualAreaProjection$lambda_0(closure$init) {
    return function ($receiver) {
      $receiver.scale = 124.75;
      $receiver.clipAngle = 180 - 0.001;
      closure$init($receiver);
      return Unit;
    };
  }
  function azimuthalEqualAreaProjection_0(init) {
    return projection(new AzimuthalEqualArea(), azimuthalEqualAreaProjection$lambda_0(init));
  }
  function scale$lambda(cxcy) {
    var x = 2 / (1 + cxcy);
    return Math_0.sqrt(x);
  }
  var scale;
  function angle$lambda(z) {
    return 2 * get_asin(z / 2);
  }
  var angle;
  function AzimuthalEqualArea() {
    Azimuthal.call(this, scale, angle);
  }
  AzimuthalEqualArea.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'AzimuthalEqualArea',
    interfaces: [Azimuthal]
  };
  function azimuthalEquidistant$lambda($receiver) {
    return Unit;
  }
  function azimuthalEquidistant() {
    return azimuthalEquidistant_0(azimuthalEquidistant$lambda);
  }
  function azimuthalEquidistant$lambda_0(closure$init) {
    return function ($receiver) {
      $receiver.scale = 79.4188;
      $receiver.clipAngle = 180 - 0.001;
      closure$init($receiver);
      return Unit;
    };
  }
  function azimuthalEquidistant_0(init) {
    return projection(new AzimuthalEquidistantProjection(), azimuthalEquidistant$lambda_0(init));
  }
  function scale$lambda_0(cxcy) {
    var c = get_acos(cxcy);
    return c !== 0.0 ? c / Math_0.sin(c) : c;
  }
  var scale_0;
  function angle$lambda_0(z) {
    return z;
  }
  var angle_0;
  function AzimuthalEquidistantProjection() {
    Azimuthal.call(this, scale_0, angle_0);
  }
  AzimuthalEquidistantProjection.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'AzimuthalEquidistantProjection',
    interfaces: [Azimuthal]
  };
  function azimuthalInvert$lambda(closure$angle) {
    return function (x, y) {
      var x_0 = x * x + y * y;
      var z = Math_0.sqrt(x_0);
      var c = closure$angle(z);
      var sc = Math_0.sin(c);
      var cc = Math_0.cos(c);
      var tmp$ = Float64Array;
      var y_0 = x * sc;
      var x_1 = z * cc;
      return new tmp$([Math_0.atan2(y_0, x_1), get_asin(z === 0.0 ? z : y * sc / z)]);
    };
  }
  function azimuthalInvert(angle) {
    return azimuthalInvert$lambda(angle);
  }
  function Azimuthal(scale, angle) {
    this.scale = scale;
    this.angle = angle;
  }
  Azimuthal.prototype.projectLambda_lu1900$ = function (lambda, phi) {
    var cx = Math_0.cos(lambda);
    var cy = Math_0.cos(phi);
    var k = this.scale(cx * cy);
    return k * cy * Math_0.sin(lambda);
  };
  Azimuthal.prototype.projectPhi_lu1900$ = function (lambda, phi) {
    var cx = Math_0.cos(lambda);
    var cy = Math_0.cos(phi);
    var k = this.scale(cx * cy);
    return k * Math_0.sin(phi);
  };
  Azimuthal.prototype.project_lu1900$ = function (lambda, phi) {
    var cx = Math_0.cos(lambda);
    var cy = Math_0.cos(phi);
    var k = this.scale(cx * cy);
    return new Float64Array([k * cy * Math_0.sin(lambda), k * Math_0.sin(phi)]);
  };
  Azimuthal.prototype.invert_lu1900$ = function (x, y) {
    var x_0 = x * x + y * y;
    var z = Math_0.sqrt(x_0);
    var c = this.angle(z);
    var sc = Math_0.sin(c);
    var cc = Math_0.cos(c);
    var tmp$ = Float64Array;
    var y_0 = x * sc;
    var x_1 = z * cc;
    return new tmp$([Math_0.atan2(y_0, x_1), get_asin(z !== 0.0 ? y * sc / z : z)]);
  };
  Azimuthal.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Azimuthal',
    interfaces: [ProjectableInvertable]
  };
  function ConicProjectable() {
  }
  ConicProjectable.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'ConicProjectable',
    interfaces: [Projectable]
  };
  function ConicProjection(conicProjectable) {
    MutableProjection.call(this, conicProjectable);
    this.conicProjectable = conicProjectable;
    this.phi0 = 0.0;
    this.phi1 = math.PI / 3.0;
  }
  Object.defineProperty(ConicProjection.prototype, 'parallels', {
    get: function () {
      return [get_rad(this.phi0), get_rad(this.phi1)];
    },
    set: function (value) {
      this.phi0 = value[0].rad;
      this.phi1 = value[1].rad;
      this.conicProjectable.phi0 = this.phi0;
      this.conicProjectable.phi1 = this.phi1;
    }
  });
  ConicProjection.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ConicProjection',
    interfaces: [MutableProjection]
  };
  function conicProjection(projection, init) {
    var $receiver = new ConicProjection(projection);
    init($receiver);
    return $receiver;
  }
  function tany(y) {
    var x = (math.HALFPI + y) / 2;
    return Math_0.tan(x);
  }
  function ConicConformalProjector() {
    this.phi0_agf7nr$_0 = 0.0;
    this.phi1_agf7mw$_0 = math.PI / 3.0;
    var x = this.phi0;
    this.cy0 = Math_0.cos(x);
    var tmp$;
    if (equals(this.phi0, this.phi1)) {
      var x_0 = this.phi0;
      tmp$ = Math_0.sin(x_0);
    }
     else {
      var tmp$_0 = this.cy0;
      var x_1 = this.phi1;
      tmp$ = log(tmp$_0, Math_0.cos(x_1)) / log(tany(this.phi1), tany(this.phi0));
    }
    this.n = tmp$;
    var tmp$_1 = this.cy0;
    var $receiver = tany(this.phi0);
    var x_2 = this.n;
    this.f = tmp$_1 * Math_0.pow($receiver, x_2) / this.n;
    this.isPossibleToUseBaseProjection_0 = this.n === 0.0 || this.n === kotlin_js_internal_DoubleCompanionObject.NaN;
    this.mercatorProjector = new MercatorProjector();
  }
  Object.defineProperty(ConicConformalProjector.prototype, 'phi0', {
    get: function () {
      return this.phi0_agf7nr$_0;
    },
    set: function (value) {
      this.phi0_agf7nr$_0 = value;
      this.recalculate_0();
    }
  });
  Object.defineProperty(ConicConformalProjector.prototype, 'phi1', {
    get: function () {
      return this.phi1_agf7mw$_0;
    },
    set: function (value) {
      this.phi1_agf7mw$_0 = value;
      this.recalculate_0();
    }
  });
  ConicConformalProjector.prototype.recalculate_0 = function () {
    var tmp$;
    var x = this.phi0;
    this.cy0 = Math_0.cos(x);
    if (equals(this.phi0, this.phi1)) {
      var x_0 = this.phi0;
      tmp$ = Math_0.sin(x_0);
    }
     else {
      var tmp$_0 = this.cy0;
      var x_1 = this.phi1;
      tmp$ = log(tmp$_0, Math_0.cos(x_1)) / log(tany(this.phi1), tany(this.phi0));
    }
    this.n = tmp$;
    var tmp$_1 = this.cy0;
    var $receiver = tany(this.phi0);
    var x_2 = this.n;
    this.f = tmp$_1 * Math_0.pow($receiver, x_2) / this.n;
    this.isPossibleToUseBaseProjection_0 = this.n === 0.0 || this.n === kotlin_js_internal_DoubleCompanionObject.NaN;
  };
  ConicConformalProjector.prototype.invert_lu1900$ = function (x, y) {
    var tmp$;
    if (this.isPossibleToUseBaseProjection_0) {
      tmp$ = this.mercatorProjector.invert_lu1900$(x, y);
    }
     else {
      var fy = this.f - y;
      var x_0 = this.n;
      var tmp$_0 = Math_0.sign(x_0);
      var x_1 = x * x + fy * fy;
      var r = tmp$_0 * Math_0.sqrt(x_1);
      var tmp$_1 = Float64Array;
      var x_2 = Math_0.abs(fy);
      var tmp$_2 = Math_0.atan2(x, x_2) / this.n * Math_0.sign(fy);
      var $receiver = this.f / r;
      var x_3 = 1 / this.n;
      var x_4 = Math_0.pow($receiver, x_3);
      return new tmp$_1([tmp$_2, 2 * Math_0.atan(x_4) - math.HALFPI]);
    }
    return tmp$;
  };
  ConicConformalProjector.prototype.project_lu1900$ = function (x, y) {
    var tmp$, tmp$_0;
    if (this.isPossibleToUseBaseProjection_0) {
      tmp$_0 = this.mercatorProjector.project_lu1900$(x, y);
    }
     else {
      if (this.f > 0) {
        if (y < -math.HALFPI + math.EPSILON) {
          tmp$ = -math.HALFPI + math.EPSILON;
        }
         else {
          tmp$ = y;
        }
      }
       else {
        if (y > math.HALFPI - math.EPSILON) {
          tmp$ = math.HALFPI - math.EPSILON;
        }
         else {
          tmp$ = y;
        }
      }
      var newY = tmp$;
      var tmp$_1 = this.f;
      var $receiver = tany(newY);
      var x_0 = this.n;
      var r = tmp$_1 / Math_0.pow($receiver, x_0);
      var tmp$_2 = Float64Array;
      var x_1 = this.n * x;
      var tmp$_3 = r * Math_0.sin(x_1);
      var tmp$_4 = this.f;
      var x_2 = this.n * x;
      return new tmp$_2([tmp$_3, tmp$_4 - r * Math_0.cos(x_2)]);
    }
    return tmp$_0;
  };
  ConicConformalProjector.prototype.projectLambda_lu1900$ = function (x, y) {
    var tmp$, tmp$_0;
    if (this.isPossibleToUseBaseProjection_0) {
      tmp$_0 = this.mercatorProjector.projectLambda_lu1900$(x, y);
    }
     else {
      if (this.f > 0) {
        if (y < -math.HALFPI + math.EPSILON) {
          tmp$ = -math.HALFPI + math.EPSILON;
        }
         else {
          tmp$ = y;
        }
      }
       else {
        if (y > math.HALFPI - math.EPSILON) {
          tmp$ = math.HALFPI - math.EPSILON;
        }
         else {
          tmp$ = y;
        }
      }
      var newY = tmp$;
      var tmp$_1 = this.f;
      var $receiver = tany(newY);
      var x_0 = this.n;
      var r = tmp$_1 / Math_0.pow($receiver, x_0);
      var x_1 = this.n * x;
      return r * Math_0.sin(x_1);
    }
    return tmp$_0;
  };
  ConicConformalProjector.prototype.projectPhi_lu1900$ = function (x, y) {
    var tmp$, tmp$_0;
    if (this.isPossibleToUseBaseProjection_0) {
      tmp$_0 = this.mercatorProjector.projectPhi_lu1900$(x, y);
    }
     else {
      if (this.f > 0) {
        if (y < -math.HALFPI + math.EPSILON) {
          tmp$ = -math.HALFPI + math.EPSILON;
        }
         else {
          tmp$ = y;
        }
      }
       else {
        if (y > math.HALFPI - math.EPSILON) {
          tmp$ = math.HALFPI - math.EPSILON;
        }
         else {
          tmp$ = y;
        }
      }
      var newY = tmp$;
      var tmp$_1 = this.f;
      var $receiver = tany(newY);
      var x_0 = this.n;
      var r = tmp$_1 / Math_0.pow($receiver, x_0);
      var tmp$_2 = this.f;
      var x_1 = this.n * x;
      return tmp$_2 - r * Math_0.cos(x_1);
    }
    return tmp$_0;
  };
  ConicConformalProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ConicConformalProjector',
    interfaces: [ProjectableInvertable, ConicProjectable]
  };
  function conicConformalProjection$lambda($receiver) {
    return Unit;
  }
  function conicConformalProjection() {
    return conicConformalProjection_0(conicConformalProjection$lambda);
  }
  function conicConformalProjection$lambda_0(closure$init) {
    return function ($receiver) {
      $receiver.scale = 109.5;
      $receiver.parallels = [get_deg(30.0), get_deg(30.0)];
      closure$init($receiver);
      return Unit;
    };
  }
  function conicConformalProjection_0(init) {
    return conicProjection(new ConicConformalProjector(), conicConformalProjection$lambda_0(init));
  }
  function ConicEqualAreaProjector() {
    this.phi0_benbex$_0 = 0.0;
    this.phi1_benbfs$_0 = math.PI / 3.0;
    var x = this.phi0;
    this.sy0_0 = Math_0.sin(x);
    var tmp$ = this.sy0_0;
    var x_0 = this.phi1;
    this.n_0 = (tmp$ + Math_0.sin(x_0)) / 2;
    this.c_0 = 1 + this.sy0_0 * (2 * this.n_0 - this.sy0_0);
    var x_1 = this.c_0;
    this.r0_0 = Math_0.sqrt(x_1) / this.n_0;
    var x_2 = this.n_0;
    this.isPossibleToUseBaseProjection_0 = Math_0.abs(x_2) < math.EPSILON;
    this.cylindricalEqualProjector = CylindricalEqualAreaProjector_init(this.phi0);
  }
  Object.defineProperty(ConicEqualAreaProjector.prototype, 'phi0', {
    get: function () {
      return this.phi0_benbex$_0;
    },
    set: function (value) {
      this.phi0_benbex$_0 = value;
      this.recalculate_0();
    }
  });
  Object.defineProperty(ConicEqualAreaProjector.prototype, 'phi1', {
    get: function () {
      return this.phi1_benbfs$_0;
    },
    set: function (value) {
      this.phi1_benbfs$_0 = value;
      this.recalculate_0();
    }
  });
  ConicEqualAreaProjector.prototype.recalculate_0 = function () {
    var x = this.phi0;
    this.sy0_0 = Math_0.sin(x);
    var tmp$ = this.sy0_0;
    var x_0 = this.phi1;
    this.n_0 = (tmp$ + Math_0.sin(x_0)) / 2;
    this.c_0 = 1 + this.sy0_0 * (2 * this.n_0 - this.sy0_0);
    var x_1 = this.c_0;
    this.r0_0 = Math_0.sqrt(x_1) / this.n_0;
    this.cylindricalEqualProjector.phi0 = this.phi0;
    var x_2 = this.n_0;
    this.isPossibleToUseBaseProjection_0 = Math_0.abs(x_2) < math.EPSILON;
  };
  ConicEqualAreaProjector.prototype.invert_lu1900$ = function (x, y) {
    var tmp$;
    if (this.isPossibleToUseBaseProjection_0) {
      tmp$ = this.cylindricalEqualProjector.invert_lu1900$(x, y);
    }
     else {
      var r0y = this.r0_0 - y;
      var tmp$_0 = Float64Array;
      var x_0 = Math_0.abs(r0y);
      var tmp$_1 = Math_0.atan2(x, x_0) / this.n_0 * Math_0.sign(r0y);
      var x_1 = (this.c_0 - (x * x + r0y * r0y) * this.n_0 * this.n_0) / (2 * this.n_0);
      tmp$ = new tmp$_0([tmp$_1, Math_0.asin(x_1)]);
    }
    return tmp$;
  };
  ConicEqualAreaProjector.prototype.project_lu1900$ = function (lambda, phi) {
    var tmp$;
    if (this.isPossibleToUseBaseProjection_0) {
      tmp$ = this.cylindricalEqualProjector.project_lu1900$(lambda, phi);
    }
     else {
      var x = this.c_0 - 2 * this.n_0 * Math_0.sin(phi);
      var r = Math_0.sqrt(x) / this.n_0;
      var lambdaN = lambda * this.n_0;
      tmp$ = new Float64Array([r * Math_0.sin(lambda), this.r0_0 - r * Math_0.cos(lambdaN)]);
    }
    return tmp$;
  };
  ConicEqualAreaProjector.prototype.projectLambda_lu1900$ = function (lambda, phi) {
    var tmp$;
    if (this.isPossibleToUseBaseProjection_0) {
      tmp$ = this.cylindricalEqualProjector.projectLambda_lu1900$(lambda, phi);
    }
     else {
      var x = this.c_0 - 2 * this.n_0 * Math_0.sin(phi);
      var r = Math_0.sqrt(x) / this.n_0;
      var lambdaN = lambda * this.n_0;
      var x_0 = lambda * this.n_0;
      tmp$ = r * Math_0.sin(x_0);
    }
    return tmp$;
  };
  ConicEqualAreaProjector.prototype.projectPhi_lu1900$ = function (lambda, phi) {
    var tmp$;
    if (this.isPossibleToUseBaseProjection_0) {
      tmp$ = this.cylindricalEqualProjector.projectPhi_lu1900$(lambda, phi);
    }
     else {
      var x = this.c_0 - 2 * this.n_0 * Math_0.sin(phi);
      var r = Math_0.sqrt(x) / this.n_0;
      var lambdaN = lambda * this.n_0;
      tmp$ = this.r0_0 - r * Math_0.cos(lambdaN);
    }
    return tmp$;
  };
  ConicEqualAreaProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ConicEqualAreaProjector',
    interfaces: [ProjectableInvertable, ConicProjectable]
  };
  function conicEqualAreaProjection$lambda($receiver) {
    return Unit;
  }
  function conicEqualAreaProjection() {
    return conicEqualAreaProjection_0(conicEqualAreaProjection$lambda);
  }
  function conicEqualAreaProjection$lambda_0(closure$init) {
    return function ($receiver) {
      $receiver.scale = 155.424;
      $receiver.center = [get_deg(0.0), get_deg(33.6442)];
      closure$init($receiver);
      return Unit;
    };
  }
  function conicEqualAreaProjection_0(init) {
    return conicProjection(new ConicEqualAreaProjector(), conicEqualAreaProjection$lambda_0(init));
  }
  function ConicEquidistantProjector() {
    this.phi0_n8jo5z$_0 = 0.0;
    this.phi1_n8jo54$_0 = math.PI / 3.0;
    var x = this.phi0;
    this.cy0_0 = Math_0.cos(x);
    var tmp$;
    if (this.phi0 === this.phi1) {
      var x_0 = this.phi0;
      tmp$ = Math_0.sin(x_0);
    }
     else {
      var tmp$_0 = this.cy0_0;
      var x_1 = this.phi1;
      tmp$ = (tmp$_0 - Math_0.cos(x_1)) / (this.phi1 - this.phi0);
    }
    this.n_0 = tmp$;
    this.g_0 = this.cy0_0 / this.n_0 + this.phi0;
    var x_2 = this.n_0;
    this.isPossibleToUseBaseProjection = Math_0.abs(x_2) < math.EPSILON;
    this.baseProjector = new EquirectangularProjector();
  }
  Object.defineProperty(ConicEquidistantProjector.prototype, 'phi0', {
    get: function () {
      return this.phi0_n8jo5z$_0;
    },
    set: function (value) {
      this.phi0_n8jo5z$_0 = value;
      this.recalculate_0();
    }
  });
  Object.defineProperty(ConicEquidistantProjector.prototype, 'phi1', {
    get: function () {
      return this.phi1_n8jo54$_0;
    },
    set: function (value) {
      this.phi1_n8jo54$_0 = value;
      this.recalculate_0();
    }
  });
  ConicEquidistantProjector.prototype.recalculate_0 = function () {
    var tmp$;
    var x = this.phi0;
    this.cy0_0 = Math_0.cos(x);
    if (this.phi0 === this.phi1) {
      var x_0 = this.phi0;
      tmp$ = Math_0.sin(x_0);
    }
     else {
      var tmp$_0 = this.cy0_0;
      var x_1 = this.phi1;
      tmp$ = (tmp$_0 - Math_0.cos(x_1)) / (this.phi1 - this.phi0);
    }
    this.n_0 = tmp$;
    this.g_0 = this.cy0_0 / this.n_0 + this.phi0;
    var x_2 = this.n_0;
    this.isPossibleToUseBaseProjection = Math_0.abs(x_2) < math.EPSILON;
  };
  ConicEquidistantProjector.prototype.invert_lu1900$ = function (x, y) {
    var tmp$;
    if (this.isPossibleToUseBaseProjection) {
      tmp$ = this.baseProjector.invert_lu1900$(x, y);
    }
     else {
      var gy = this.g_0 - y;
      var tmp$_0 = Float64Array;
      var x_0 = Math_0.abs(gy);
      var tmp$_1 = Math_0.atan2(x, x_0) / this.n_0 * Math_0.sign(gy);
      var tmp$_2 = this.g_0;
      var x_1 = this.n_0;
      var tmp$_3 = Math_0.sign(x_1);
      var x_2 = x * x + gy * gy;
      return new tmp$_0([tmp$_1, tmp$_2 - tmp$_3 * Math_0.sqrt(x_2)]);
    }
    return tmp$;
  };
  ConicEquidistantProjector.prototype.project_lu1900$ = function (x, y) {
    var tmp$;
    if (this.isPossibleToUseBaseProjection) {
      tmp$ = this.baseProjector.project_lu1900$(x, y);
    }
     else {
      var gy = this.g_0 - y;
      var nx = this.n_0 * x;
      return new Float64Array([gy * Math_0.sin(nx), this.g_0 - gy * Math_0.cos(nx)]);
    }
    return tmp$;
  };
  ConicEquidistantProjector.prototype.projectLambda_lu1900$ = function (x, y) {
    var tmp$;
    if (this.isPossibleToUseBaseProjection) {
      tmp$ = this.baseProjector.projectLambda_lu1900$(x, y);
    }
     else {
      var gy = this.g_0 - y;
      var nx = this.n_0 * x;
      return gy * Math_0.sin(nx);
    }
    return tmp$;
  };
  ConicEquidistantProjector.prototype.projectPhi_lu1900$ = function (x, y) {
    var tmp$;
    if (this.isPossibleToUseBaseProjection) {
      tmp$ = this.baseProjector.projectPhi_lu1900$(x, y);
    }
     else {
      var gy = this.g_0 - y;
      var nx = this.n_0 * x;
      return this.g_0 - gy * Math_0.cos(nx);
    }
    return tmp$;
  };
  ConicEquidistantProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ConicEquidistantProjector',
    interfaces: [ProjectableInvertable, ConicProjectable]
  };
  function conicEquidistantProjection$lambda($receiver) {
    return Unit;
  }
  function conicEquidistantProjection() {
    return conicEquidistantProjection_0(conicEquidistantProjection$lambda);
  }
  function conicEquidistantProjection$lambda_0(closure$init) {
    return function ($receiver) {
      $receiver.scale = 131.154;
      $receiver.center = [get_deg(0.0), get_deg(13.9389)];
      closure$init($receiver);
      return Unit;
    };
  }
  function conicEquidistantProjection_0(init) {
    return conicProjection(new ConicEquidistantProjector(), conicEquidistantProjection$lambda_0(init));
  }
  function CylindricalEqualAreaProjector() {
    this.phi0_ntkqud$_0 = 0.0;
    var x = this.phi0;
    this.cosPhi0 = Math_0.cos(x);
  }
  CylindricalEqualAreaProjector.prototype.invert_lu1900$ = function (x, y) {
    var tmp$ = Float64Array;
    var tmp$_0 = x / this.cosPhi0;
    var x_0 = y * this.cosPhi0;
    return new tmp$([tmp$_0, Math_0.asin(x_0)]);
  };
  Object.defineProperty(CylindricalEqualAreaProjector.prototype, 'phi0', {
    get: function () {
      return this.phi0_ntkqud$_0;
    },
    set: function (value) {
      this.phi0_ntkqud$_0 = value;
      var x = this.phi0;
      this.cosPhi0 = Math_0.cos(x);
    }
  });
  CylindricalEqualAreaProjector.prototype.project_lu1900$ = function (lambda, phi) {
    return new Float64Array([this.projectLambda_lu1900$(lambda, phi), this.projectPhi_lu1900$(lambda, phi)]);
  };
  CylindricalEqualAreaProjector.prototype.projectLambda_lu1900$ = function (lambda, phi) {
    return lambda * this.cosPhi0;
  };
  CylindricalEqualAreaProjector.prototype.projectPhi_lu1900$ = function (lambda, phi) {
    return Math_0.sin(phi) / this.cosPhi0;
  };
  CylindricalEqualAreaProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'CylindricalEqualAreaProjector',
    interfaces: [ProjectableInvertable]
  };
  function CylindricalEqualAreaProjector_init(phi, $this) {
    $this = $this || Object.create(CylindricalEqualAreaProjector.prototype);
    CylindricalEqualAreaProjector.call($this);
    $this.phi0 = phi;
    return $this;
  }
  var A1;
  var A2;
  var A3;
  var A4;
  var M;
  var iterations;
  function equalEarthProjection$lambda($receiver) {
    return Unit;
  }
  function equalEarthProjection() {
    return equalEarthProjection_0(equalEarthProjection$lambda);
  }
  function equalEarthProjection$lambda_0(closure$init) {
    return function ($receiver) {
      $receiver.scale = 177.158;
      closure$init($receiver);
      return Unit;
    };
  }
  function equalEarthProjection_0(init) {
    return projection(new EqualEarthProjector(), equalEarthProjection$lambda_0(init));
  }
  function EqualEarthProjector() {
  }
  EqualEarthProjector.prototype.project_lu1900$ = function (lambda, phi) {
    var x = M * Math_0.sin(phi);
    var l = Math_0.asin(x);
    var l2 = l * l;
    var l6 = l2 * l2 * l2;
    return new Float64Array([lambda * Math_0.cos(l) / (M * (A1 + 3 * A2 * l2 + l6 * (7 * A3 + 9 * A4 * l2))), l * (A1 + A2 * l2 + l6 * (A3 + A4 * l2))]);
  };
  EqualEarthProjector.prototype.invert_lu1900$ = function (x, y) {
    var tmp$;
    var l = y;
    var l2 = l * l;
    var l6 = l2 * l2 * l2;
    tmp$ = iterations;
    for (var i = 0; i < tmp$; i++) {
      var fy = l * (A1 + A2 * l2 + l6 * (A3 + A4 * l2)) - y;
      var fpy = A1 + 3 * A2 * l2 + l6 * (7 * A3 + 9 * A4 * l2);
      var delta = fy / fpy;
      l2 = l * l;
      l6 = l2 * l2 * l2;
      l -= delta;
      if (Math_0.abs(delta) < math.EPSILON2)
        break;
    }
    var tmp$_0 = Float64Array;
    var tmp$_1 = M * x * (A1 + 3 * A2 * l2 + l6 * (7 * A3 + 9 * A4 * l2)) / Math_0.cos(l);
    var x_0 = Math_0.sin(l) / M;
    return new tmp$_0([tmp$_1, Math_0.asin(x_0)]);
  };
  EqualEarthProjector.prototype.projectLambda_lu1900$ = function (lambda, phi) {
    var x = M * Math_0.sin(phi);
    var l = Math_0.asin(x);
    var l2 = l * l;
    var l6 = l2 * l2 * l2;
    return lambda * Math_0.cos(l) / (M * (A1 + 3 * A2 * l2 + l6 * (7 * A3 + 9 * A4 * l2)));
  };
  EqualEarthProjector.prototype.projectPhi_lu1900$ = function (lambda, phi) {
    var x = M * Math_0.sin(phi);
    var l = Math_0.asin(x);
    var l2 = l * l;
    var l6 = l2 * l2 * l2;
    return l * (A1 + A2 * l2 + l6 * (A3 + A4 * l2));
  };
  EqualEarthProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'EqualEarthProjector',
    interfaces: [ProjectableInvertable]
  };
  function equirectangularProjection$lambda($receiver) {
    return Unit;
  }
  function equirectangularProjection() {
    return equirectangularProjection_0(equirectangularProjection$lambda);
  }
  function equirectangularProjection$lambda_0(closure$init) {
    return function ($receiver) {
      $receiver.scale = 152.63;
      closure$init($receiver);
      return Unit;
    };
  }
  function equirectangularProjection_0(init) {
    return projection(new EquirectangularProjector(), equirectangularProjection$lambda_0(init));
  }
  function EquirectangularProjector() {
  }
  EquirectangularProjector.prototype.projectLambda_lu1900$ = function (lambda, phi) {
    return lambda;
  };
  EquirectangularProjector.prototype.projectPhi_lu1900$ = function (lambda, phi) {
    return phi;
  };
  EquirectangularProjector.prototype.project_lu1900$ = function (lambda, phi) {
    return new Float64Array([lambda, phi]);
  };
  EquirectangularProjector.prototype.invert_lu1900$ = function (x, y) {
    return new Float64Array([x, y]);
  };
  EquirectangularProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'EquirectangularProjector',
    interfaces: [ProjectableInvertable]
  };
  function gnomonicProjection$lambda($receiver) {
    return Unit;
  }
  function gnomonicProjection() {
    return gnomonicProjection_0(gnomonicProjection$lambda);
  }
  function gnomonicProjection$lambda_0(closure$init) {
    return function ($receiver) {
      $receiver.clipAngle = 60.0;
      $receiver.scale = 144.049;
      closure$init($receiver);
      return Unit;
    };
  }
  function gnomonicProjection_0(init) {
    return projection(new GnomonicProjector(), gnomonicProjection$lambda_0(init));
  }
  function GnomonicProjector() {
  }
  GnomonicProjector.prototype.project_lu1900$ = function (x, y) {
    var cy = Math_0.cos(y);
    var k = Math_0.cos(x) * cy;
    return new Float64Array([cy * Math_0.sin(x) / k, Math_0.sin(y) / k]);
  };
  GnomonicProjector.prototype.invert_lu1900$ = function (x, y) {
    return azimuthalInvert(getCallableRef('atan', function (x) {
      return Math_0.atan(x);
    }))(x, y);
  };
  GnomonicProjector.prototype.projectLambda_lu1900$ = function (x, y) {
    var cy = Math_0.cos(y);
    var k = Math_0.cos(x) * cy;
    return cy * Math_0.sin(x) / k;
  };
  GnomonicProjector.prototype.projectPhi_lu1900$ = function (x, y) {
    var cy = Math_0.cos(y);
    var k = Math_0.cos(x) * cy;
    return Math_0.sin(y) / k;
  };
  GnomonicProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'GnomonicProjector',
    interfaces: [ProjectableInvertable]
  };
  function identityProjection$lambda($receiver) {
    return Unit;
  }
  function identityProjection() {
    return identityProjection_0(identityProjection$lambda);
  }
  function identityProjection$lambda$lambda(it) {
    return it;
  }
  function identityProjection$lambda$lambda_0(it) {
    return it;
  }
  function identityProjection$lambda_0(closure$init) {
    return function ($receiver) {
      $receiver.preClip = identityProjection$lambda$lambda;
      $receiver.postClip = identityProjection$lambda$lambda_0;
      $receiver.scale = 180 / math.PI;
      closure$init($receiver);
      return Unit;
    };
  }
  function identityProjection_0(init) {
    return projection(new IdentityProjection(), identityProjection$lambda_0(init));
  }
  function IdentityProjection() {
  }
  IdentityProjection.prototype.project_lu1900$ = function (lambda, phi) {
    return new Float64Array([lambda, phi]);
  };
  IdentityProjection.prototype.invert_lu1900$ = function (x, y) {
    return new Float64Array([x, y]);
  };
  IdentityProjection.prototype.projectLambda_lu1900$ = function (lambda, phi) {
    return lambda;
  };
  IdentityProjection.prototype.projectPhi_lu1900$ = function (lambda, phi) {
    return phi;
  };
  IdentityProjection.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'IdentityProjection',
    interfaces: [ProjectableInvertable]
  };
  function mercatorProjection$lambda($receiver) {
    return Unit;
  }
  function mercatorProjection() {
    return mercatorProjection_0(mercatorProjection$lambda);
  }
  function mercatorProjection$lambda_0(closure$init) {
    return function ($receiver) {
      $receiver.scale = 961 / math.TAU;
      closure$init($receiver);
      return Unit;
    };
  }
  function mercatorProjection_0(init) {
    return projection(new MercatorProjector(), mercatorProjection$lambda_0(init));
  }
  function MercatorProjector() {
  }
  MercatorProjector.prototype.projectLambda_lu1900$ = function (lambda, phi) {
    return lambda;
  };
  MercatorProjector.prototype.projectPhi_lu1900$ = function (lambda, phi) {
    var x = (math.HALFPI + phi) / 2;
    var x_0 = Math_0.tan(x);
    return Math_0.log(x_0);
  };
  MercatorProjector.prototype.project_lu1900$ = function (lambda, phi) {
    var tmp$ = Float64Array;
    var x = (math.HALFPI + phi) / 2;
    var x_0 = Math_0.tan(x);
    return new tmp$([lambda, Math_0.log(x_0)]);
  };
  MercatorProjector.prototype.invert_lu1900$ = function (x, y) {
    var tmp$ = Float64Array;
    var x_0 = Math_0.exp(y);
    return new tmp$([x, 2 * Math_0.atan(x_0) - math.HALFPI]);
  };
  MercatorProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'MercatorProjector',
    interfaces: [ProjectableInvertable]
  };
  function MercatorProjection(projector) {
    if (projector === void 0)
      projector = new MercatorProjector();
    MutableProjection.call(this, projector);
    this.innerExtent_x08pep$_0 = null;
  }
  Object.defineProperty(MercatorProjection.prototype, 'scale', {
    get: function () {
      return Kotlin.callGetter(this, MutableProjection.prototype, 'scale');
    },
    set: function (value) {
      Kotlin.callSetter(this, MutableProjection.prototype, 'scale', value);
      this.reclip_p9bhzi$_0();
    }
  });
  Object.defineProperty(MercatorProjection.prototype, 'translate', {
    get: function () {
      return Kotlin.callGetter(this, MutableProjection.prototype, 'translate');
    },
    set: function (value) {
      Kotlin.callSetter(this, MutableProjection.prototype, 'translate', value);
      this.reclip_p9bhzi$_0();
    }
  });
  Object.defineProperty(MercatorProjection.prototype, 'center', {
    get: function () {
      return Kotlin.callGetter(this, MutableProjection.prototype, 'center');
    },
    set: function (value) {
      Kotlin.callSetter(this, MutableProjection.prototype, 'center', value);
      this.reclip_p9bhzi$_0();
    }
  });
  Object.defineProperty(MercatorProjection.prototype, 'clipExtent', {
    get: function () {
      return this.innerExtent_x08pep$_0;
    },
    set: function (value) {
      this.innerExtent_x08pep$_0 = value;
    }
  });
  MercatorProjection.prototype.reclip_p9bhzi$_0 = function () {
    var tmp$;
    var k = math.PI * this.scale;
    var invert = rotation(this.rotate).invert_lu1900$(0.0, 0.0);
    var lambda = invert[0];
    var phi = invert[1];
    var t0 = this.projectLambda_lu1900$(lambda, phi);
    var t1 = this.projectPhi_lu1900$(lambda, phi);
    if (this.clipExtent == null)
      tmp$ = new Extent(t0 - k, t1 - k, k * 2, k * 2);
    else if (Kotlin.isType(this.projection, MercatorProjector)) {
      var a = t0 - k;
      var b = ensureNotNull(this.clipExtent).x0;
      var tmp$_0 = Math_0.max(a, b);
      var tmp$_1 = ensureNotNull(this.clipExtent).y0;
      var a_0 = k * 2;
      var b_0 = ensureNotNull(this.clipExtent).width;
      var b_1 = Math_0.min(a_0, b_0);
      tmp$ = new Extent(tmp$_0, tmp$_1, Math_0.max(0.0, b_1), ensureNotNull(this.clipExtent).height);
    }
     else {
      var tmp$_2 = ensureNotNull(this.clipExtent).x0;
      var a_1 = t1 - k;
      var b_2 = ensureNotNull(this.clipExtent).y0;
      var tmp$_3 = Math_0.max(a_1, b_2);
      var tmp$_4 = ensureNotNull(this.clipExtent).width;
      var a_2 = k * 2;
      var b_3 = ensureNotNull(this.clipExtent).height;
      tmp$ = new Extent(tmp$_2, tmp$_3, tmp$_4, Math_0.min(a_2, b_3));
    }
    Kotlin.callSetter(this, MutableProjection.prototype, 'clipExtent', tmp$);
  };
  MercatorProjection.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'MercatorProjection',
    interfaces: [MutableProjection]
  };
  function naturalEarth1Projection$lambda($receiver) {
    return Unit;
  }
  function naturalEarth1Projection() {
    return naturalEarth1Projection_0(naturalEarth1Projection$lambda);
  }
  function naturalEarth1Projection$lambda_0(closure$init) {
    return function ($receiver) {
      $receiver.scale = 175.295;
      closure$init($receiver);
      return Unit;
    };
  }
  function naturalEarth1Projection_0(init) {
    return projection(new NaturalEarth1Projection(), naturalEarth1Projection$lambda_0(init));
  }
  function NaturalEarth1Projection() {
  }
  NaturalEarth1Projection.prototype.project_lu1900$ = function (lambda, phi) {
    var phi2 = phi * phi;
    var phi4 = phi2 * phi2;
    return new Float64Array([lambda * (0.8707 - 0.131979 * phi2 + phi4 * (-0.013791 + phi4 * (0.003971 * phi2 - 0.001529 * phi4))), phi * (1.007226 + phi2 * (0.015085 + phi4 * (-0.044475 + 0.028874 * phi2 - 0.005916 * phi4)))]);
  };
  NaturalEarth1Projection.prototype.invert_lu1900$ = function (x, y) {
    var phi = y;
    var i = 25;
    var delta;
    do {
      var phi2 = phi * phi;
      var phi4 = phi2 * phi2;
      delta = (phi * (1.007226 + phi2 * (0.015085 + phi4 * (-0.044475 + 0.028874 * phi2 - 0.005916 * phi4))) - y) / (1.007226 + phi2 * (0.015085 * 3 + phi4 * (-0.044475 * 7 + 0.028874 * 9 * phi2 - 0.005916 * 11 * phi4)));
      phi -= delta;
      var abs$result;
      abs$result = Math_0.abs(delta);
    }
     while (abs$result > math.EPSILON && (i = i - 1 | 0, i) > 0);
    var phi2_0 = phi * phi;
    return new Float64Array([x / (0.8707 + phi2_0 * (-0.131979 + phi2_0 * (-0.013791 + phi2_0 * phi2_0 * phi2_0 * (0.003971 - 0.001529 * phi2_0)))), phi]);
  };
  NaturalEarth1Projection.prototype.projectLambda_lu1900$ = function (lambda, phi) {
    var phi2 = phi * phi;
    var phi4 = phi2 * phi2;
    return lambda * (0.8707 - 0.131979 * phi2 + phi4 * (-0.013791 + phi4 * (0.003971 * phi2 - 0.001529 * phi4)));
  };
  NaturalEarth1Projection.prototype.projectPhi_lu1900$ = function (lambda, phi) {
    var phi2 = phi * phi;
    var phi4 = phi2 * phi2;
    return phi * (1.007226 + phi2 * (0.015085 + phi4 * (-0.044475 + 0.028874 * phi2 - 0.005916 * phi4)));
  };
  NaturalEarth1Projection.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'NaturalEarth1Projection',
    interfaces: [ProjectableInvertable]
  };
  function orthographicProjection$lambda($receiver) {
    return Unit;
  }
  function orthographicProjection() {
    return orthographicProjection_0(orthographicProjection$lambda);
  }
  function orthographicProjection$lambda_0(closure$init) {
    return function ($receiver) {
      $receiver.scale = 249.5;
      $receiver.clipAngle = 90 + math.EPSILON;
      closure$init($receiver);
      return Unit;
    };
  }
  function orthographicProjection_0(init) {
    return projection(new OrthographicProjector(), orthographicProjection$lambda_0(init));
  }
  function OrthographicProjector() {
    this.invertFunction_0 = azimuthalInvert(getCallableRef('asin', function (x) {
      return Math_0.asin(x);
    }));
  }
  OrthographicProjector.prototype.projectLambda_lu1900$ = function (lambda, phi) {
    return Math_0.cos(phi) * Math_0.sin(lambda);
  };
  OrthographicProjector.prototype.projectPhi_lu1900$ = function (lambda, phi) {
    return Math_0.sin(phi);
  };
  OrthographicProjector.prototype.project_lu1900$ = function (lambda, phi) {
    return new Float64Array([Math_0.cos(phi) * Math_0.sin(lambda), Math_0.sin(phi)]);
  };
  OrthographicProjector.prototype.invert_lu1900$ = function (x, y) {
    return this.invertFunction_0(x, y);
  };
  OrthographicProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'OrthographicProjector',
    interfaces: [ProjectableInvertable]
  };
  function Stream() {
  }
  Stream.prototype.point_yvo9jy$ = function (x, y, z) {
  };
  Stream.prototype.lineStart = function () {
  };
  Stream.prototype.lineEnd = function () {
  };
  Stream.prototype.polygonStart = function () {
  };
  Stream.prototype.polygonEnd = function () {
  };
  Stream.prototype.sphere = function () {
  };
  Stream.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Stream',
    interfaces: []
  };
  function Projectable() {
  }
  Projectable.prototype.projectLambda_lu1900$ = function (lambda, phi) {
    return this.project_lu1900$(lambda, phi)[0];
  };
  Projectable.prototype.projectPhi_lu1900$ = function (lambda, phi) {
    return this.project_lu1900$(lambda, phi)[1];
  };
  Projectable.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Projectable',
    interfaces: []
  };
  function Invertable() {
  }
  Invertable.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Invertable',
    interfaces: []
  };
  function ProjectableInvertable() {
  }
  ProjectableInvertable.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'ProjectableInvertable',
    interfaces: [Invertable, Projectable]
  };
  function Projection() {
  }
  Projection.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Projection',
    interfaces: [ProjectableInvertable]
  };
  function compose$ObjectLiteral(closure$a, closure$b) {
    this.closure$a = closure$a;
    this.closure$b = closure$b;
  }
  compose$ObjectLiteral.prototype.projectLambda_lu1900$ = function (lambda, phi) {
    var aX = this.closure$a.projectLambda_lu1900$(lambda, phi);
    var aY = this.closure$a.projectPhi_lu1900$(lambda, phi);
    return this.closure$b.projectLambda_lu1900$(aX, aY);
  };
  compose$ObjectLiteral.prototype.projectPhi_lu1900$ = function (lambda, phi) {
    var aX = this.closure$a.projectLambda_lu1900$(lambda, phi);
    var aY = this.closure$a.projectPhi_lu1900$(lambda, phi);
    return this.closure$b.projectPhi_lu1900$(aX, aY);
  };
  compose$ObjectLiteral.prototype.project_lu1900$ = function (lambda, phi) {
    var p = this.closure$a.project_lu1900$(lambda, phi);
    return this.closure$b.project_lu1900$(p[0], p[1]);
  };
  compose$ObjectLiteral.prototype.invert_lu1900$ = function (x, y) {
    var p = this.closure$b.invert_lu1900$(x, y);
    return this.closure$a.invert_lu1900$(p[0], p[1]);
  };
  compose$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [ProjectableInvertable]
  };
  function compose$ObjectLiteral_0(closure$a, closure$b) {
    this.closure$a = closure$a;
    this.closure$b = closure$b;
  }
  compose$ObjectLiteral_0.prototype.projectLambda_lu1900$ = function (lambda, phi) {
    var aX = this.closure$a.projectLambda_lu1900$(lambda, phi);
    var aY = this.closure$a.projectPhi_lu1900$(lambda, phi);
    return this.closure$b.projectLambda_lu1900$(aX, aY);
  };
  compose$ObjectLiteral_0.prototype.projectPhi_lu1900$ = function (lambda, phi) {
    var aX = this.closure$a.projectLambda_lu1900$(lambda, phi);
    var aY = this.closure$a.projectPhi_lu1900$(lambda, phi);
    return this.closure$b.projectPhi_lu1900$(aX, aY);
  };
  compose$ObjectLiteral_0.prototype.project_lu1900$ = function (lambda, phi) {
    var p = this.closure$a.project_lu1900$(lambda, phi);
    return this.closure$b.project_lu1900$(p[0], p[1]);
  };
  compose$ObjectLiteral_0.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [Projectable]
  };
  function compose(a, b) {
    if (Kotlin.isType(a, Invertable) && Kotlin.isType(b, Invertable)) {
      return new compose$ObjectLiteral(a, b);
    }
     else {
      return new compose$ObjectLiteral_0(a, b);
    }
  }
  function TransformRadians(stream) {
    ModifiedStream.call(this, stream);
  }
  TransformRadians.prototype.point_yvo9jy$ = function (x, y, z) {
    this.stream.point_yvo9jy$(toRadians(x), toRadians(y), toRadians(z));
  };
  TransformRadians.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'TransformRadians',
    interfaces: [ModifiedStream]
  };
  function projection(projection, init) {
    var $receiver = new MutableProjection(projection);
    init($receiver);
    return $receiver;
  }
  function MutableProjection(projection) {
    this.projection = projection;
    this.cache = null;
    this.cacheStream = null;
    this.clipAntimeridian_k76cnn$_0 = clipAntimeridian();
    this.noClip = MutableProjection$noClip$lambda;
    this.preClip_3o9rln$_0 = this.clipAntimeridian_k76cnn$_0;
    this.postClip_smn68a$_0 = this.noClip;
    this.theta_ovpdn4$_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.clipExtent_n63cz0$_0 = null;
    this.k_3fxgj$_0 = 150.0;
    this.x_3fxrq$_0 = 480.0;
    this.y_3fxsl$_0 = 250.0;
    this.dx_2yppki$_0 = 0.0;
    this.dy_2yppld$_0 = 0.0;
    this.lambda_befkrj$_0 = 0.0;
    this.phi_kv8btp$_0 = 0.0;
    this.deltaLambda_s16u0f$_0 = 0.0;
    this.deltaPhi_akscb1$_0 = 0.0;
    this.deltaGamma_34b6c7$_0 = 0.0;
    this.rotator_oun95t$_cc5k0k$_0 = this.rotator_oun95t$_cc5k0k$_0;
    this.projectRotate_sef4ti$_z5zs9i$_0 = this.projectRotate_sef4ti$_z5zs9i$_0;
    this.projectTransform_bn5u4z$_0 = new MutableProjection$projectTransform$ObjectLiteral(this);
    this.delta2_x3ojn8$_0 = 0.5;
    this.projectResample_z2k8bc$_0 = resample(this.projectTransform_bn5u4z$_0, this.delta2_x3ojn8$_0);
    this.transformRadians_8tken4$_0 = MutableProjection$transformRadians$lambda;
  }
  MutableProjection.prototype.getCachedStream_k25lbv$ = function (stream) {
    return this.cache != null && equals(this.cacheStream, stream) ? this.cache : null;
  };
  MutableProjection.prototype.cache_kvp71y$ = function (stream1, stream2) {
    this.cache = stream2;
    this.cacheStream = stream1;
  };
  Object.defineProperty(MutableProjection.prototype, 'preClip', {
    get: function () {
      return this.preClip_3o9rln$_0;
    },
    set: function (value) {
      this.preClip_3o9rln$_0 = value;
    }
  });
  Object.defineProperty(MutableProjection.prototype, 'postClip', {
    get: function () {
      return this.postClip_smn68a$_0;
    },
    set: function (value) {
      this.postClip_smn68a$_0 = value;
    }
  });
  Object.defineProperty(MutableProjection.prototype, 'clipAngle', {
    get: function () {
      return this.theta_ovpdn4$_0;
    },
    set: function (value) {
      if (isNaN_0(value)) {
        this.theta_ovpdn4$_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
        this.preClip = clipAntimeridian();
      }
       else {
        this.theta_ovpdn4$_0 = toRadians(value);
        this.preClip = clipCircle(this.theta_ovpdn4$_0);
      }
    }
  });
  Object.defineProperty(MutableProjection.prototype, 'clipExtent', {
    get: function () {
      return this.clipExtent_n63cz0$_0;
    },
    set: function (value) {
      this.clipExtent_n63cz0$_0 = value;
      if (value != null) {
        this.postClip = clipExtent(value);
      }
       else {
        this.postClip = this.noClip;
      }
    }
  });
  MutableProjection.prototype.fitExtent_76k4nv$ = function (extent, geo) {
    return fitExtent(this, extent, geo);
  };
  MutableProjection.prototype.fitWidth_qy4pci$ = function (width, geo) {
    return fitWidth(this, width, geo);
  };
  MutableProjection.prototype.fitHeight_qy4pci$ = function (height, geo) {
    return fitHeight(this, height, geo);
  };
  MutableProjection.prototype.fitSize_gd85ts$ = function (width, height, geo) {
    return fitSize(this, width, height, geo);
  };
  Object.defineProperty(MutableProjection.prototype, 'scale', {
    get: function () {
      return this.k_3fxgj$_0;
    },
    set: function (value) {
      this.k_3fxgj$_0 = value;
      this.recenter();
    }
  });
  Object.defineProperty(MutableProjection.prototype, 'translate', {
    get: function () {
      return new Float64Array([this.x_3fxrq$_0, this.y_3fxsl$_0]);
    },
    set: function (value) {
      this.x_3fxrq$_0 = value[0];
      this.y_3fxsl$_0 = value[1];
      this.recenter();
    }
  });
  Object.defineProperty(MutableProjection.prototype, 'center', {
    get: function () {
      return [get_rad(this.lambda_befkrj$_0), get_rad(this.phi_kv8btp$_0)];
    },
    set: function (value) {
      this.lambda_befkrj$_0 = value[0].rad;
      this.phi_kv8btp$_0 = value[1].rad;
      this.recenter();
    }
  });
  Object.defineProperty(MutableProjection.prototype, 'rotator_oun95t$_0', {
    get: function () {
      if (this.rotator_oun95t$_cc5k0k$_0 == null)
        return throwUPAE('rotator');
      return this.rotator_oun95t$_cc5k0k$_0;
    },
    set: function (rotator) {
      this.rotator_oun95t$_cc5k0k$_0 = rotator;
    }
  });
  Object.defineProperty(MutableProjection.prototype, 'rotate', {
    get: function () {
      return [get_rad(this.deltaLambda_s16u0f$_0), get_rad(this.deltaPhi_akscb1$_0), get_rad(this.deltaGamma_34b6c7$_0)];
    },
    set: function (value) {
      this.deltaLambda_s16u0f$_0 = value[0].rad;
      this.deltaPhi_akscb1$_0 = value[1].rad;
      this.deltaGamma_34b6c7$_0 = value.length > 2 ? value[2].rad : 0.0;
      this.recenter();
    }
  });
  Object.defineProperty(MutableProjection.prototype, 'projectRotate_sef4ti$_0', {
    get: function () {
      if (this.projectRotate_sef4ti$_z5zs9i$_0 == null)
        return throwUPAE('projectRotate');
      return this.projectRotate_sef4ti$_z5zs9i$_0;
    },
    set: function (projectRotate) {
      this.projectRotate_sef4ti$_z5zs9i$_0 = projectRotate;
    }
  });
  Object.defineProperty(MutableProjection.prototype, 'precision', {
    get: function () {
      var x = this.delta2_x3ojn8$_0;
      return Math_0.sqrt(x);
    },
    set: function (value) {
      this.delta2_x3ojn8$_0 = value * value;
      this.projectResample_z2k8bc$_0 = resample(this.projectTransform_bn5u4z$_0, this.delta2_x3ojn8$_0);
      this.reset();
    }
  });
  function MutableProjection$transformRotate$lambda$ObjectLiteral(closure$stream, closure$rotate, stream_0) {
    this.closure$stream = closure$stream;
    this.closure$rotate = closure$rotate;
    ModifiedStream.call(this, stream_0);
  }
  MutableProjection$transformRotate$lambda$ObjectLiteral.prototype.point_yvo9jy$ = function (x, y, z) {
    this.closure$stream.point_yvo9jy$(this.closure$rotate.projectLambda_lu1900$(x, y), this.closure$rotate.projectPhi_lu1900$(x, y), 0.0);
  };
  MutableProjection$transformRotate$lambda$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [ModifiedStream]
  };
  function MutableProjection$transformRotate$lambda(closure$rotate) {
    return function (stream) {
      return new MutableProjection$transformRotate$lambda$ObjectLiteral(stream, closure$rotate, stream);
    };
  }
  MutableProjection.prototype.transformRotate_4m8av5$_0 = function (rotate) {
    return MutableProjection$transformRotate$lambda(rotate);
  };
  MutableProjection.prototype.projectLambda_lu1900$ = function (lambda, phi) {
    return this.projection.projectLambda_lu1900$(toRadians(lambda), toRadians(phi)) * this.k_3fxgj$_0 + this.dx_2yppki$_0;
  };
  MutableProjection.prototype.projectPhi_lu1900$ = function (lambda, phi) {
    return this.dy_2yppld$_0 - this.projection.projectPhi_lu1900$(toRadians(lambda), toRadians(phi)) * this.k_3fxgj$_0;
  };
  MutableProjection.prototype.project_lu1900$ = function (lambda, phi) {
    var p = this.projectRotate_sef4ti$_0.project_lu1900$(toRadians(lambda), toRadians(phi));
    return new Float64Array([p[0] * this.k_3fxgj$_0 + this.dx_2yppki$_0, this.dy_2yppld$_0 - p[1] * this.k_3fxgj$_0]);
  };
  MutableProjection.prototype.invert_lu1900$ = function (x, y) {
    var tmp$;
    if (!Kotlin.isType(this.projectRotate_sef4ti$_0, Invertable)) {
      var message = 'This projection is not invertable.';
      throw IllegalArgumentException_init(message.toString());
    }
    var p = (Kotlin.isType(tmp$ = this.projectRotate_sef4ti$_0, Invertable) ? tmp$ : throwCCE()).invert_lu1900$((x - this.dx_2yppki$_0) / this.k_3fxgj$_0, (this.dy_2yppld$_0 - y) / this.k_3fxgj$_0);
    return new Float64Array([toDegrees(p[0]), toDegrees(p[1])]);
  };
  MutableProjection.prototype.stream_k25lbv$ = function (stream) {
    var cachedStream = this.getCachedStream_k25lbv$(stream);
    if (cachedStream == null) {
      cachedStream = this.fullCycleStream_bygv0c$_0(stream);
      this.cache_kvp71y$(cachedStream, cachedStream);
    }
    return cachedStream;
  };
  MutableProjection.prototype.fullCycleStream_bygv0c$_0 = function (stream) {
    return this.transformRadians_8tken4$_0(this.transformRotate_4m8av5$_0(this.rotator_oun95t$_0)(this.preClip(this.projectResample_z2k8bc$_0(this.postClip(stream)))));
  };
  MutableProjection.prototype.recenter = function () {
    this.rotator_oun95t$_0 = rotateRadians(this.deltaLambda_s16u0f$_0, this.deltaPhi_akscb1$_0, this.deltaGamma_34b6c7$_0);
    this.projectRotate_sef4ti$_0 = compose(this.rotator_oun95t$_0, this.projection);
    this.dx_2yppki$_0 = this.x_3fxrq$_0 - this.projection.projectLambda_lu1900$(this.lambda_befkrj$_0, this.phi_kv8btp$_0) * this.k_3fxgj$_0;
    this.dy_2yppld$_0 = this.y_3fxsl$_0 + this.projection.projectPhi_lu1900$(this.lambda_befkrj$_0, this.phi_kv8btp$_0) * this.k_3fxgj$_0;
  };
  MutableProjection.prototype.reset = function () {
    this.cache = null;
    this.cacheStream = null;
  };
  function MutableProjection$noClip$lambda(it) {
    return it;
  }
  function MutableProjection$projectTransform$ObjectLiteral(this$MutableProjection) {
    this.this$MutableProjection = this$MutableProjection;
  }
  MutableProjection$projectTransform$ObjectLiteral.prototype.projectLambda_lu1900$ = function (lambda, phi) {
    return this.this$MutableProjection.projection.projectLambda_lu1900$(lambda, phi) * this.this$MutableProjection.k_3fxgj$_0 + this.this$MutableProjection.dx_2yppki$_0;
  };
  MutableProjection$projectTransform$ObjectLiteral.prototype.projectPhi_lu1900$ = function (lambda, phi) {
    return this.this$MutableProjection.dy_2yppld$_0 - this.this$MutableProjection.projection.projectPhi_lu1900$(lambda, phi) * this.this$MutableProjection.k_3fxgj$_0;
  };
  MutableProjection$projectTransform$ObjectLiteral.prototype.project_lu1900$ = function (lambda, phi) {
    var p = this.this$MutableProjection.projection.project_lu1900$(lambda, phi);
    return new Float64Array([p[0] * this.this$MutableProjection.k_3fxgj$_0 + this.this$MutableProjection.dx_2yppki$_0, this.this$MutableProjection.dy_2yppld$_0 - p[1] * this.this$MutableProjection.k_3fxgj$_0]);
  };
  MutableProjection$projectTransform$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [Projectable]
  };
  function MutableProjection$transformRadians$lambda$ObjectLiteral(closure$stream, stream_0) {
    this.closure$stream = closure$stream;
    ModifiedStream.call(this, stream_0);
  }
  MutableProjection$transformRadians$lambda$ObjectLiteral.prototype.point_yvo9jy$ = function (x, y, z) {
    this.closure$stream.point_yvo9jy$(toRadians(x), toRadians(y), toRadians(z));
  };
  MutableProjection$transformRadians$lambda$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [ModifiedStream]
  };
  function MutableProjection$transformRadians$lambda(stream) {
    return new MutableProjection$transformRadians$lambda$ObjectLiteral(stream, stream);
  }
  MutableProjection.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'MutableProjection',
    interfaces: [Projection]
  };
  var MAX_DEPTH;
  var COS_MIN_DISTANCE;
  function resample(project, delta2) {
    return delta2 !== 0.0 ? _resample(project, delta2) : resampleNone(project);
  }
  function resampleNone$lambda$ObjectLiteral(closure$stream, closure$project, stream) {
    this.closure$stream = closure$stream;
    this.closure$project = closure$project;
    ModifiedStream.call(this, stream);
  }
  resampleNone$lambda$ObjectLiteral.prototype.point_yvo9jy$ = function (x, y, z) {
    this.closure$stream.point_yvo9jy$(this.closure$project.projectLambda_lu1900$(x, y), this.closure$project.projectPhi_lu1900$(x, y), 0.0);
  };
  resampleNone$lambda$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [ModifiedStream]
  };
  function resampleNone$lambda(closure$project) {
    return function (stream) {
      return new resampleNone$lambda$ObjectLiteral(stream, closure$project, stream);
    };
  }
  function resampleNone(project) {
    return resampleNone$lambda(project);
  }
  function _resample$lambda(closure$project, closure$delta2) {
    return function (stream) {
      return new ReSampledStream(stream, closure$project, closure$delta2);
    };
  }
  function _resample(project, delta2) {
    return _resample$lambda(project, delta2);
  }
  function PointFunction_0() {
  }
  PointFunction_0.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'PointFunction',
    interfaces: []
  };
  function LineStartFunction_0() {
  }
  LineStartFunction_0.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'LineStartFunction',
    interfaces: []
  };
  function LineEndFunction_0() {
  }
  LineEndFunction_0.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'LineEndFunction',
    interfaces: []
  };
  function DefaultPointFunction_0() {
    DefaultPointFunction_instance_0 = this;
  }
  DefaultPointFunction_0.prototype.invoke_827pdu$ = function (reSampledStream, x, y, z) {
    reSampledStream.stream.point_yvo9jy$(reSampledStream.project.projectLambda_lu1900$(x, y), reSampledStream.project.projectPhi_lu1900$(x, y), 0.0);
  };
  DefaultPointFunction_0.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'DefaultPointFunction',
    interfaces: [PointFunction_0]
  };
  var DefaultPointFunction_instance_0 = null;
  function DefaultPointFunction_getInstance_0() {
    if (DefaultPointFunction_instance_0 === null) {
      new DefaultPointFunction_0();
    }
    return DefaultPointFunction_instance_0;
  }
  function LinePointFunction_0() {
    LinePointFunction_instance_0 = this;
  }
  LinePointFunction_0.prototype.invoke_827pdu$ = function (reSampledStream, lambda, phi, z) {
    var cosPhi = Math_0.cos(phi);
    var cart0 = cosPhi * Math_0.cos(lambda);
    var cart1 = cosPhi * Math_0.sin(lambda);
    var cart2 = Math_0.sin(phi);
    var p0 = reSampledStream.project.projectLambda_lu1900$(lambda, phi);
    var p1 = reSampledStream.project.projectPhi_lu1900$(lambda, phi);
    reSampledStream.resampleLineTo_czmypi$(reSampledStream.x0, reSampledStream.y0, reSampledStream.lambda0, reSampledStream.a0, reSampledStream.b0, reSampledStream.c0, p0, p1, lambda, cart0, cart1, cart2, 16, reSampledStream.stream);
    reSampledStream.x0 = p0;
    reSampledStream.y0 = p1;
    reSampledStream.lambda0 = lambda;
    reSampledStream.a0 = cart0;
    reSampledStream.b0 = cart1;
    reSampledStream.c0 = cart2;
    reSampledStream.stream.point_yvo9jy$(reSampledStream.x0, reSampledStream.y0, z);
  };
  LinePointFunction_0.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'LinePointFunction',
    interfaces: [PointFunction_0]
  };
  var LinePointFunction_instance_0 = null;
  function LinePointFunction_getInstance_0() {
    if (LinePointFunction_instance_0 === null) {
      new LinePointFunction_0();
    }
    return LinePointFunction_instance_0;
  }
  function RingPointFunction_0() {
    RingPointFunction_instance_0 = this;
  }
  RingPointFunction_0.prototype.invoke_827pdu$ = function (reSampledStream, lambda, phi, z) {
    reSampledStream.lambda00 = lambda;
    LinePointFunction_getInstance_0().invoke_827pdu$(reSampledStream, lambda, phi, 0.0);
    reSampledStream.x00 = reSampledStream.x0;
    reSampledStream.y00 = reSampledStream.y0;
    reSampledStream.a00 = reSampledStream.a0;
    reSampledStream.b00 = reSampledStream.b0;
    reSampledStream.c00 = reSampledStream.c0;
    reSampledStream.currentPoint = LinePointFunction_getInstance_0();
  };
  RingPointFunction_0.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'RingPointFunction',
    interfaces: [PointFunction_0]
  };
  var RingPointFunction_instance_0 = null;
  function RingPointFunction_getInstance_0() {
    if (RingPointFunction_instance_0 === null) {
      new RingPointFunction_0();
    }
    return RingPointFunction_instance_0;
  }
  function DefaultLineStartFunction_0() {
    DefaultLineStartFunction_instance_0 = this;
  }
  DefaultLineStartFunction_0.prototype.invoke_pr9d1o$ = function (reSampledStream) {
    reSampledStream.x0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    reSampledStream.currentPoint = DefaultPointFunction_getInstance_0();
    reSampledStream.stream.lineStart();
  };
  DefaultLineStartFunction_0.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'DefaultLineStartFunction',
    interfaces: [LineStartFunction_0]
  };
  var DefaultLineStartFunction_instance_0 = null;
  function DefaultLineStartFunction_getInstance_0() {
    if (DefaultLineStartFunction_instance_0 === null) {
      new DefaultLineStartFunction_0();
    }
    return DefaultLineStartFunction_instance_0;
  }
  function DefaultLineEndFunction_0() {
    DefaultLineEndFunction_instance_0 = this;
  }
  DefaultLineEndFunction_0.prototype.invoke_pr9d1o$ = function (reSampledStream) {
    reSampledStream.currentPoint = DefaultPointFunction_getInstance_0();
    reSampledStream.stream.lineEnd();
  };
  DefaultLineEndFunction_0.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'DefaultLineEndFunction',
    interfaces: [LineEndFunction_0]
  };
  var DefaultLineEndFunction_instance_0 = null;
  function DefaultLineEndFunction_getInstance_0() {
    if (DefaultLineEndFunction_instance_0 === null) {
      new DefaultLineEndFunction_0();
    }
    return DefaultLineEndFunction_instance_0;
  }
  function RingLineStartFunction_0() {
    RingLineStartFunction_instance_0 = this;
  }
  RingLineStartFunction_0.prototype.invoke_pr9d1o$ = function (reSampledStream) {
    DefaultLineStartFunction_getInstance_0().invoke_pr9d1o$(reSampledStream);
    reSampledStream.currentPoint = RingPointFunction_getInstance_0();
    reSampledStream.currentLineEnd = RingLineEndFunction_getInstance_0();
  };
  RingLineStartFunction_0.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'RingLineStartFunction',
    interfaces: [LineStartFunction_0]
  };
  var RingLineStartFunction_instance_0 = null;
  function RingLineStartFunction_getInstance_0() {
    if (RingLineStartFunction_instance_0 === null) {
      new RingLineStartFunction_0();
    }
    return RingLineStartFunction_instance_0;
  }
  function RingLineEndFunction_0() {
    RingLineEndFunction_instance_0 = this;
  }
  RingLineEndFunction_0.prototype.invoke_pr9d1o$ = function (reSampledStream) {
    reSampledStream.resampleLineTo_czmypi$(reSampledStream.x0, reSampledStream.y0, reSampledStream.lambda0, reSampledStream.a0, reSampledStream.b0, reSampledStream.c0, reSampledStream.x00, reSampledStream.y00, reSampledStream.lambda00, reSampledStream.a00, reSampledStream.b00, reSampledStream.c00, 16, reSampledStream.stream);
    reSampledStream.currentLineEnd = DefaultLineEndFunction_getInstance_0();
    reSampledStream.lineEnd();
  };
  RingLineEndFunction_0.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'RingLineEndFunction',
    interfaces: [LineEndFunction_0]
  };
  var RingLineEndFunction_instance_0 = null;
  function RingLineEndFunction_getInstance_0() {
    if (RingLineEndFunction_instance_0 === null) {
      new RingLineEndFunction_0();
    }
    return RingLineEndFunction_instance_0;
  }
  function ReSampledStream(stream, project, delta2) {
    this.stream = stream;
    this.project = project;
    this.delta2 = delta2;
    this.lambda00 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.x00 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.y00 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.a00 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.b00 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.c00 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.lambda0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.x0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.y0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.a0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.b0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.c0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.currentPoint = DefaultPointFunction_getInstance_0();
    this.currentLineStart = DefaultLineStartFunction_getInstance_0();
    this.currentLineEnd = DefaultLineEndFunction_getInstance_0();
    this.currentPolygonStart = ReSampledStream$currentPolygonStart$lambda(this);
    this.currentPolygonEnd = ReSampledStream$currentPolygonEnd$lambda(this);
  }
  ReSampledStream.prototype.lineEnd = function () {
    this.currentLineEnd.invoke_pr9d1o$(this);
  };
  ReSampledStream.prototype.lineStart = function () {
    this.currentLineStart.invoke_pr9d1o$(this);
  };
  ReSampledStream.prototype.point_yvo9jy$ = function (x, y, z) {
    this.currentPoint.invoke_827pdu$(this, x, y, z);
  };
  ReSampledStream.prototype.polygonEnd = function () {
    this.currentPolygonEnd();
  };
  ReSampledStream.prototype.polygonStart = function () {
    this.currentPolygonStart();
  };
  ReSampledStream.prototype.resampleLineTo_czmypi$ = function (x0, y0, lambda0, a0, b0, c0, x1, y1, lambda1, a1, b1, c1, depth, stream) {
    var tmp$;
    var newDepth = depth;
    var dx = x1 - x0;
    var dy = y1 - y0;
    var d2 = dx * dx + dy * dy;
    if (d2 > 4 * this.delta2 && newDepth !== 0) {
      newDepth = newDepth - 1 | 0;
      var a = a0 + a1;
      var b = b0 + b1;
      var c = c0 + c1;
      var x = a * a + b * b + c * c;
      var m = Math_0.sqrt(x);
      c /= m;
      var phi2 = get_asin(c);
      var x_0 = Math_0.abs(c) - 1;
      var tmp$_0 = Math_0.abs(x_0) < math.EPSILON;
      if (!tmp$_0) {
        var x_1 = lambda0 - lambda1;
        tmp$_0 = Math_0.abs(x_1) < math.EPSILON;
      }
      if (tmp$_0)
        tmp$ = (lambda0 + lambda1) / 2;
      else {
        tmp$ = Math_0.atan2(b, a);
      }
      var lambda2 = tmp$;
      var x2 = this.project.projectLambda_lu1900$(lambda2, phi2);
      var y2 = this.project.projectPhi_lu1900$(lambda2, phi2);
      var dx2 = x2 - x0;
      var dy2 = y2 - y0;
      var dz = dy * dx2 - dx * dy2;
      var tmp$_1 = dz * dz / d2 > this.delta2;
      if (!tmp$_1) {
        var x_2 = (dx * dx2 + dy * dy2) / d2 - 0.5;
        tmp$_1 = Math_0.abs(x_2) > 0.3;
      }
      if (tmp$_1 || a0 * a1 + b0 * b1 + c0 * c1 < COS_MIN_DISTANCE) {
        a /= m;
        b /= m;
        this.resampleLineTo_czmypi$(x0, y0, lambda0, a0, b0, c0, x2, y2, lambda2, a, b, c, newDepth, stream);
        stream.point_yvo9jy$(x2, y2, 0.0);
        this.resampleLineTo_czmypi$(x2, y2, lambda2, a, b, c, x1, y1, lambda1, a1, b1, c1, newDepth, stream);
      }
    }
  };
  function ReSampledStream$currentPolygonStart$lambda(this$ReSampledStream) {
    return function () {
      this$ReSampledStream.stream.polygonStart();
      this$ReSampledStream.currentLineStart = RingLineStartFunction_getInstance_0();
      return Unit;
    };
  }
  function ReSampledStream$currentPolygonEnd$lambda(this$ReSampledStream) {
    return function () {
      this$ReSampledStream.stream.polygonEnd();
      this$ReSampledStream.currentLineStart = DefaultLineStartFunction_getInstance_0();
      return Unit;
    };
  }
  ReSampledStream.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ReSampledStream',
    interfaces: [Stream]
  };
  function identityProjection_1(x, y) {
    return new Float64Array([identityProjectionX(x), identityProjectionY(y)]);
  }
  function identityProjectionX(x) {
    if (x > math_0.PI)
      return x - math.TAU;
    else if (x < -math_0.PI)
      return x + math.TAU;
    else
      return x;
  }
  function identityProjectionY(y) {
    return y;
  }
  function rotationIdentity$ObjectLiteral() {
  }
  rotationIdentity$ObjectLiteral.prototype.projectLambda_lu1900$ = function (lambda, phi) {
    return identityProjectionX(lambda);
  };
  rotationIdentity$ObjectLiteral.prototype.projectPhi_lu1900$ = function (lambda, phi) {
    return identityProjectionY(phi);
  };
  rotationIdentity$ObjectLiteral.prototype.project_lu1900$ = function (lambda, phi) {
    return identityProjection_1(lambda, phi);
  };
  rotationIdentity$ObjectLiteral.prototype.invert_lu1900$ = function (x, y) {
    return identityProjection_1(x, y);
  };
  rotationIdentity$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [ProjectableInvertable]
  };
  function rotationIdentity() {
    return new rotationIdentity$ObjectLiteral();
  }
  function RotationLambda(deltaLambda) {
    this.deltaLambda = deltaLambda;
  }
  RotationLambda.prototype.projectLambda_lu1900$ = function (lambda, phi) {
    return identityProjectionX(lambda + this.deltaLambda);
  };
  RotationLambda.prototype.projectPhi_lu1900$ = function (lambda, phi) {
    return identityProjectionY(phi);
  };
  RotationLambda.prototype.project_lu1900$ = function (lambda, phi) {
    return identityProjection_1(lambda + this.deltaLambda, phi);
  };
  RotationLambda.prototype.invert_lu1900$ = function (x, y) {
    return identityProjection_1(x - this.deltaLambda, y);
  };
  RotationLambda.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'RotationLambda',
    interfaces: [ProjectableInvertable]
  };
  function RotationPhiGamma(deltaPhi, deltaGamma) {
    this.cosDeltaPhi_0 = Math_0.cos(deltaPhi);
    this.sinDeltaPhi_0 = Math_0.sin(deltaPhi);
    this.cosDeltaGamma_0 = Math_0.cos(deltaGamma);
    this.sinDeltaGamma_0 = Math_0.sin(deltaGamma);
  }
  RotationPhiGamma.prototype.projectLambda_lu1900$ = function (lambda, phi) {
    var cosPhi = Math_0.cos(phi);
    var x = Math_0.cos(lambda) * cosPhi;
    var y = Math_0.sin(lambda) * cosPhi;
    var z = Math_0.sin(phi);
    var k = z * this.cosDeltaPhi_0 + x * this.sinDeltaPhi_0;
    var y_0 = y * this.cosDeltaGamma_0 - k * this.sinDeltaGamma_0;
    var x_0 = x * this.cosDeltaPhi_0 - z * this.sinDeltaPhi_0;
    return Math_0.atan2(y_0, x_0);
  };
  RotationPhiGamma.prototype.projectPhi_lu1900$ = function (lambda, phi) {
    var cosPhi = Math_0.cos(phi);
    var x = Math_0.cos(lambda) * cosPhi;
    var y = Math_0.sin(lambda) * cosPhi;
    var z = Math_0.sin(phi);
    var k = z * this.cosDeltaPhi_0 + x * this.sinDeltaPhi_0;
    var x_0 = k * this.cosDeltaGamma_0 + y * this.sinDeltaGamma_0;
    return Math_0.asin(x_0);
  };
  RotationPhiGamma.prototype.project_lu1900$ = function (lambda, phi) {
    var cosPhi = Math_0.cos(phi);
    var x = Math_0.cos(lambda) * cosPhi;
    var y = Math_0.sin(lambda) * cosPhi;
    var z = Math_0.sin(phi);
    var k = z * this.cosDeltaPhi_0 + x * this.sinDeltaPhi_0;
    var tmp$ = Float64Array;
    var y_0 = y * this.cosDeltaGamma_0 - k * this.sinDeltaGamma_0;
    var x_0 = x * this.cosDeltaPhi_0 - z * this.sinDeltaPhi_0;
    var tmp$_0 = Math_0.atan2(y_0, x_0);
    var x_1 = k * this.cosDeltaGamma_0 + y * this.sinDeltaGamma_0;
    return new tmp$([tmp$_0, Math_0.asin(x_1)]);
  };
  RotationPhiGamma.prototype.invert_lu1900$ = function (x, y) {
    var cosPhi = Math_0.cos(y);
    var newX = Math_0.cos(x) * cosPhi;
    var newY = Math_0.sin(x) * cosPhi;
    var z = Math_0.sin(y);
    var k = z * this.cosDeltaGamma_0 - newY * this.sinDeltaGamma_0;
    var tmp$ = Float64Array;
    var y_0 = newY * this.cosDeltaGamma_0 + z * this.sinDeltaGamma_0;
    var x_0 = newX * this.cosDeltaPhi_0 + k * this.sinDeltaPhi_0;
    var tmp$_0 = Math_0.atan2(y_0, x_0);
    var x_1 = k * this.cosDeltaPhi_0 - newX * this.sinDeltaPhi_0;
    return new tmp$([tmp$_0, Math_0.asin(x_1)]);
  };
  RotationPhiGamma.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'RotationPhiGamma',
    interfaces: [ProjectableInvertable]
  };
  function rotateRadians(deltaLambda, deltaPhi, deltaGamma) {
    var tmp$, tmp$_0;
    var newDeltaLambda = deltaLambda % math.TAU;
    if (newDeltaLambda !== 0.0) {
      if (deltaPhi !== 0.0 || deltaGamma !== 0.0) {
        tmp$_0 = Kotlin.isType(tmp$ = compose(new RotationLambda(deltaLambda), new RotationPhiGamma(deltaPhi, deltaGamma)), ProjectableInvertable) ? tmp$ : throwCCE();
      }
       else
        tmp$_0 = new RotationLambda(deltaLambda);
    }
     else
      tmp$_0 = deltaPhi !== 0.0 || deltaGamma !== 0.0 ? new RotationPhiGamma(deltaPhi, deltaGamma) : rotationIdentity();
    return tmp$_0;
  }
  function rotation$ObjectLiteral(closure$rotator) {
    this.closure$rotator = closure$rotator;
  }
  rotation$ObjectLiteral.prototype.projectLambda_lu1900$ = function (lambda, phi) {
    return toDegrees(this.closure$rotator.projectLambda_lu1900$(toRadians(lambda), toRadians(phi)));
  };
  rotation$ObjectLiteral.prototype.projectPhi_lu1900$ = function (lambda, phi) {
    return toDegrees(this.closure$rotator.projectPhi_lu1900$(toRadians(lambda), toRadians(phi)));
  };
  rotation$ObjectLiteral.prototype.project_lu1900$ = function (lambda, phi) {
    var p = this.closure$rotator.project_lu1900$(toRadians(lambda), toRadians(phi));
    return new Float64Array([toDegrees(p[0]), toDegrees(p[1])]);
  };
  rotation$ObjectLiteral.prototype.invert_lu1900$ = function (x, y) {
    var p = this.closure$rotator.invert_lu1900$(toRadians(x), toRadians(y));
    return new Float64Array([toDegrees(p[0]), toDegrees(p[1])]);
  };
  rotation$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [ProjectableInvertable]
  };
  function rotation(rotate) {
    var rotator = rotateRadians(rotate[0].rad, rotate[1].rad, rotate.length > 2 ? rotate[2].rad : 0.0);
    return new rotation$ObjectLiteral(rotator);
  }
  function stereographicProjection$lambda($receiver) {
    return Unit;
  }
  function stereographicProjection() {
    return stereographicProjection_0(stereographicProjection$lambda);
  }
  function stereographicProjection$lambda_0(closure$init) {
    return function ($receiver) {
      $receiver.scale = 250.0;
      $receiver.clipAngle = 142.0;
      closure$init($receiver);
      return Unit;
    };
  }
  function stereographicProjection_0(init) {
    return projection(new StereographicProjector(), stereographicProjection$lambda_0(init));
  }
  function StereographicProjector() {
  }
  StereographicProjector.prototype.project_lu1900$ = function (x, y) {
    var cy = Math_0.cos(y);
    var k = 1 + Math_0.cos(x) * cy;
    return new Float64Array([cy * Math_0.sin(x) / k, Math_0.sin(y) / k]);
  };
  function StereographicProjector$invert$lambda(it) {
    return 2 * Math_0.atan(it);
  }
  StereographicProjector.prototype.invert_lu1900$ = function (x, y) {
    return azimuthalInvert(StereographicProjector$invert$lambda)(x, y);
  };
  StereographicProjector.prototype.projectLambda_lu1900$ = function (x, y) {
    var cy = Math_0.cos(y);
    var k = 1 + Math_0.cos(x) * cy;
    return cy * Math_0.sin(x) / k;
  };
  StereographicProjector.prototype.projectPhi_lu1900$ = function (x, y) {
    var cy = Math_0.cos(y);
    var k = 1 + Math_0.cos(x) * cy;
    return Math_0.sin(y) / k;
  };
  StereographicProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'StereographicProjector',
    interfaces: [ProjectableInvertable]
  };
  function transverseMercatorProjection$lambda($receiver) {
    return Unit;
  }
  function transverseMercatorProjection() {
    return transverseMercatorProjection_0(transverseMercatorProjection$lambda);
  }
  function transverseMercatorProjection_0(init) {
    var $receiver = new TransverseMercatorProjection();
    $receiver.rotate = [get_deg(0), get_deg(0), get_deg(90)];
    $receiver.scale = 159.155;
    init($receiver);
    return $receiver;
  }
  function TransverseMercatorProjector() {
  }
  TransverseMercatorProjector.prototype.project_lu1900$ = function (lambda, phi) {
    var tmp$ = Float64Array;
    var x = (math.HALFPI + phi) / 2;
    var x_0 = Math_0.tan(x);
    return new tmp$([Math_0.log(x_0), -lambda]);
  };
  TransverseMercatorProjector.prototype.invert_lu1900$ = function (x, y) {
    var tmp$ = Float64Array;
    var tmp$_0 = -y;
    var x_0 = Math_0.exp(x);
    return new tmp$([tmp$_0, 2 * Math_0.atan(x_0) - math.HALFPI]);
  };
  TransverseMercatorProjector.prototype.projectLambda_lu1900$ = function (lambda, phi) {
    return this.project_lu1900$(lambda, phi)[0];
  };
  TransverseMercatorProjector.prototype.projectPhi_lu1900$ = function (lambda, phi) {
    return this.project_lu1900$(lambda, phi)[1];
  };
  TransverseMercatorProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'TransverseMercatorProjector',
    interfaces: [ProjectableInvertable]
  };
  function TransverseMercatorProjection() {
    MercatorProjection.call(this, new TransverseMercatorProjector());
  }
  Object.defineProperty(TransverseMercatorProjection.prototype, 'center', {
    get: function () {
      var it = Kotlin.callGetter(this, MercatorProjection.prototype, 'center');
      var t = it[0];
      it[0] = it[1];
      it[1] = t.unaryMinus();
      return it;
    },
    set: function (value) {
      var it = value;
      var t = it[0];
      it[0] = it[1].unaryMinus();
      it[1] = t;
      Kotlin.callSetter(this, MercatorProjection.prototype, 'center', it);
    }
  });
  Object.defineProperty(TransverseMercatorProjection.prototype, 'rotate', {
    get: function () {
      var tmp$;
      var original = Kotlin.callGetter(this, MercatorProjection.prototype, 'rotate');
      if (original.length > 2) {
        tmp$ = [original[0], original[1], original[2].minus_5t6zck$(get_deg(90.0))];
      }
       else {
        tmp$ = [original[0], original[1], get_deg(-90.0)];
      }
      return tmp$;
    },
    set: function (value) {
      var tmp$;
      var original = value;
      if (original.length > 2) {
        tmp$ = [original[0], original[1], original[2].plus_5t6zck$(get_deg(90.0))];
      }
       else {
        tmp$ = [original[0], original[1], get_deg(+90.0)];
      }
      Kotlin.callSetter(this, MercatorProjection.prototype, 'rotate', tmp$);
    }
  });
  TransverseMercatorProjection.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'TransverseMercatorProjection',
    interfaces: [MercatorProjection]
  };
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$geo = package$data2viz.geo || (package$data2viz.geo = {});
  package$geo.spherical_gf7tl1$ = spherical;
  package$geo.cartesian_gf7tl1$ = cartesian;
  package$geo.cartesianDot_g9g6do$ = cartesianDot;
  package$geo.cartesianCross_g9g6do$ = cartesianCross;
  package$geo.cartesianScale_pw9xcd$ = cartesianScale;
  package$geo.cartesianAdd_g9g6do$ = cartesianAdd;
  package$geo.cartesianNormalize_gf7tl1$ = cartesianNormalize;
  package$geo.contains_p3myhs$ = contains;
  package$geo.toRadians_awc180$ = toRadians_0;
  package$geo.fitSize_ir9ota$ = fitSize;
  package$geo.fitHeight_9t4pdg$ = fitHeight;
  package$geo.fitWidth_9t4pdg$ = fitWidth;
  package$geo.fitExtent_knpnp9$ = fitExtent;
  package$geo.GeoArea = GeoArea;
  package$geo.GeoBounds = GeoBounds;
  package$geo.GeoCentroid = GeoCentroid;
  package$geo.GeoCircle = GeoCircle;
  package$geo.geoCircle_f0c3sv$ = geoCircle;
  package$geo.GeoInterpolate = GeoInterpolate;
  package$geo.haversin_14dthe$ = haversin;
  package$geo.geoInterpolate_g9g6do$ = geoInterpolate;
  package$geo.Sphere = Sphere;
  package$geo.geoDistance_8efa2c$ = geoDistance;
  package$geo.GeoLength = GeoLength;
  package$geo.geoGraticule = geoGraticule;
  package$geo.geoGraticule_x04cjl$ = geoGraticule_0;
  package$geo.Graticule = Graticule;
  package$geo.get_asin_yrwdxr$ = get_asin;
  package$geo.get_acos_yrwdxr$ = get_acos;
  package$geo.polygonContains_5i3gmk$ = polygonContains;
  Object.defineProperty(package$geo, 'noop_8be2vx$', {
    get: function () {
      return noop;
    }
  });
  Object.defineProperty(package$geo, 'noop2_8be2vx$', {
    get: function () {
      return noop2;
    }
  });
  Object.defineProperty(package$geo, 'noop3_8be2vx$', {
    get: function () {
      return noop3;
    }
  });
  package$geo.stream_8o9991$ = stream;
  package$geo.streamGeometry_8o9991$ = streamGeometry;
  package$geo.ModifiedStream = ModifiedStream;
  var package$clip = package$geo.clip || (package$geo.clip = {});
  package$clip.clipAntimeridian = clipAntimeridian;
  package$clip.AntimeridianClip = AntimeridianClip;
  package$clip.ClipStream = ClipStream;
  package$clip.Clippable = Clippable;
  package$clip.ClippableHasStart = ClippableHasStart;
  package$clip.PointFunction = PointFunction;
  package$clip.LineStartFunction = LineStartFunction;
  package$clip.LineEndFunction = LineEndFunction;
  Object.defineProperty(package$clip, 'DefaultPointFunction', {
    get: DefaultPointFunction_getInstance
  });
  Object.defineProperty(package$clip, 'RingPointFunction', {
    get: RingPointFunction_getInstance
  });
  Object.defineProperty(package$clip, 'DefaultLineStartFunction', {
    get: DefaultLineStartFunction_getInstance
  });
  Object.defineProperty(package$clip, 'DefaultLineEndFunction', {
    get: DefaultLineEndFunction_getInstance
  });
  Object.defineProperty(package$clip, 'RingLineStartFunction', {
    get: RingLineStartFunction_getInstance
  });
  Object.defineProperty(package$clip, 'RingLineEndFunction', {
    get: RingLineEndFunction_getInstance
  });
  Object.defineProperty(package$clip, 'LinePointFunction', {
    get: LinePointFunction_getInstance
  });
  Object.defineProperty(package$clip, 'PointRingPointFunction', {
    get: PointRingPointFunction_getInstance
  });
  package$clip.Clip = Clip;
  package$clip.ClipBuffer = ClipBuffer;
  package$clip.clipCircle_14dthe$ = clipCircle;
  package$clip.ClipCircle = ClipCircle;
  package$clip.clipExtent_1sqktx$ = clipExtent;
  package$clip.clipRectangle_6y0v78$ = clipRectangle;
  package$clip.ClipRectangle = ClipRectangle;
  package$clip.Intersection = Intersection;
  package$clip.InterpolateFunction = InterpolateFunction;
  package$clip.rejoin_46bgso$ = rejoin;
  package$clip.link_i8agry$ = link;
  package$clip.pointEqual_6dypfz$ = pointEqual;
  var package$path = package$geo.path || (package$geo.path = {});
  package$path.geoPath_830hqy$ = geoPath;
  package$path.GeoPath = GeoPath;
  package$path.PathArea = PathArea;
  package$path.PathBounds = PathBounds;
  package$path.PathCentroid = PathCentroid;
  package$path.PathContext = PathContext;
  package$path.PathMeasure = PathMeasure;
  var package$projection = package$geo.projection || (package$geo.projection = {});
  package$projection.MultiplexStream = MultiplexStream;
  package$projection.alberUSAProjection = alberUSAProjection;
  package$projection.alberUSAProjection_i7gh7r$ = alberUSAProjection_0;
  package$projection.AlberUSAProjection = AlberUSAProjection;
  package$projection.albersProjection = albersProjection;
  package$projection.albersProjection_2xcl47$ = albersProjection_0;
  package$projection.azimuthalEqualAreaProjection = azimuthalEqualAreaProjection;
  package$projection.azimuthalEqualAreaProjection_n23zc3$ = azimuthalEqualAreaProjection_0;
  package$projection.AzimuthalEqualArea = AzimuthalEqualArea;
  package$projection.azimuthalEquidistant = azimuthalEquidistant;
  package$projection.azimuthalEquidistant_n23zc3$ = azimuthalEquidistant_0;
  package$projection.AzimuthalEquidistantProjection = AzimuthalEquidistantProjection;
  package$projection.azimuthalInvert_7fnk9s$ = azimuthalInvert;
  package$projection.Azimuthal = Azimuthal;
  package$projection.ConicProjectable = ConicProjectable;
  package$projection.ConicProjection = ConicProjection;
  package$projection.conicProjection_49lvsd$ = conicProjection;
  package$projection.tany_14dthe$ = tany;
  package$projection.ConicConformalProjector = ConicConformalProjector;
  package$projection.conicConformalProjection = conicConformalProjection;
  package$projection.conicConformalProjection_2xcl47$ = conicConformalProjection_0;
  package$projection.ConicEqualAreaProjector = ConicEqualAreaProjector;
  package$projection.conicEqualAreaProjection = conicEqualAreaProjection;
  package$projection.conicEqualAreaProjection_2xcl47$ = conicEqualAreaProjection_0;
  package$projection.ConicEquidistantProjector = ConicEquidistantProjector;
  package$projection.conicEquidistantProjection = conicEquidistantProjection;
  package$projection.conicEquidistantProjection_2xcl47$ = conicEquidistantProjection_0;
  package$projection.CylindricalEqualAreaProjector_init_14dthe$ = CylindricalEqualAreaProjector_init;
  package$projection.CylindricalEqualAreaProjector = CylindricalEqualAreaProjector;
  Object.defineProperty(package$projection, 'A1', {
    get: function () {
      return A1;
    }
  });
  Object.defineProperty(package$projection, 'A2', {
    get: function () {
      return A2;
    }
  });
  Object.defineProperty(package$projection, 'A3', {
    get: function () {
      return A3;
    }
  });
  Object.defineProperty(package$projection, 'A4', {
    get: function () {
      return A4;
    }
  });
  Object.defineProperty(package$projection, 'M', {
    get: function () {
      return M;
    }
  });
  Object.defineProperty(package$projection, 'iterations', {
    get: function () {
      return iterations;
    }
  });
  package$projection.equalEarthProjection = equalEarthProjection;
  package$projection.equalEarthProjection_i7gh7r$ = equalEarthProjection_0;
  package$projection.EqualEarthProjector = EqualEarthProjector;
  package$projection.equirectangularProjection = equirectangularProjection;
  package$projection.equirectangularProjection_i7gh7r$ = equirectangularProjection_0;
  package$projection.EquirectangularProjector = EquirectangularProjector;
  package$projection.gnomonicProjection = gnomonicProjection;
  package$projection.gnomonicProjection_i7gh7r$ = gnomonicProjection_0;
  package$projection.GnomonicProjector = GnomonicProjector;
  package$projection.identityProjection = identityProjection;
  package$projection.identityProjection_i7gh7r$ = identityProjection_0;
  package$projection.IdentityProjection = IdentityProjection;
  package$projection.mercatorProjection = mercatorProjection;
  package$projection.mercatorProjection_i7gh7r$ = mercatorProjection_0;
  package$projection.MercatorProjector = MercatorProjector;
  package$projection.MercatorProjection = MercatorProjection;
  package$projection.naturalEarth1Projection = naturalEarth1Projection;
  package$projection.naturalEarth1Projection_i7gh7r$ = naturalEarth1Projection_0;
  package$projection.NaturalEarth1Projection = NaturalEarth1Projection;
  package$projection.orthographicProjection = orthographicProjection;
  package$projection.orthographicProjection_n23zc3$ = orthographicProjection_0;
  package$projection.OrthographicProjector = OrthographicProjector;
  package$projection.Stream = Stream;
  package$projection.Projectable = Projectable;
  package$projection.Invertable = Invertable;
  package$projection.ProjectableInvertable = ProjectableInvertable;
  package$projection.Projection = Projection;
  package$projection.compose_gkepck$ = compose;
  package$projection.TransformRadians = TransformRadians;
  package$projection.projection_mbh45l$ = projection;
  package$projection.MutableProjection = MutableProjection;
  Object.defineProperty(package$projection, 'MAX_DEPTH', {
    get: function () {
      return MAX_DEPTH;
    }
  });
  Object.defineProperty(package$projection, 'COS_MIN_DISTANCE', {
    get: function () {
      return COS_MIN_DISTANCE;
    }
  });
  package$projection.resample_mx159m$ = resample;
  package$projection.PointFunction = PointFunction_0;
  package$projection.LineStartFunction = LineStartFunction_0;
  package$projection.LineEndFunction = LineEndFunction_0;
  Object.defineProperty(package$projection, 'DefaultPointFunction', {
    get: DefaultPointFunction_getInstance_0
  });
  Object.defineProperty(package$projection, 'LinePointFunction', {
    get: LinePointFunction_getInstance_0
  });
  Object.defineProperty(package$projection, 'RingPointFunction', {
    get: RingPointFunction_getInstance_0
  });
  Object.defineProperty(package$projection, 'DefaultLineStartFunction', {
    get: DefaultLineStartFunction_getInstance_0
  });
  Object.defineProperty(package$projection, 'DefaultLineEndFunction', {
    get: DefaultLineEndFunction_getInstance_0
  });
  Object.defineProperty(package$projection, 'RingLineStartFunction', {
    get: RingLineStartFunction_getInstance_0
  });
  Object.defineProperty(package$projection, 'RingLineEndFunction', {
    get: RingLineEndFunction_getInstance_0
  });
  package$projection.ReSampledStream = ReSampledStream;
  package$projection.RotationLambda = RotationLambda;
  package$projection.RotationPhiGamma = RotationPhiGamma;
  package$projection.rotateRadians_yvo9jy$ = rotateRadians;
  package$projection.rotation_2iyfza$ = rotation;
  package$projection.stereographicProjection = stereographicProjection;
  package$projection.stereographicProjection_i7gh7r$ = stereographicProjection_0;
  package$projection.StereographicProjector = StereographicProjector;
  package$projection.transverseMercatorProjection = transverseMercatorProjection;
  package$projection.transverseMercatorProjection_4mndx7$ = transverseMercatorProjection_0;
  package$projection.TransverseMercatorProjector = TransverseMercatorProjector;
  package$projection.TransverseMercatorProjection = TransverseMercatorProjection;
  GeoBounds.prototype.sphere = Stream.prototype.sphere;
  GeoCentroid.prototype.sphere = Stream.prototype.sphere;
  GeoCircle$circleStream$ObjectLiteral.prototype.lineStart = Stream.prototype.lineStart;
  GeoCircle$circleStream$ObjectLiteral.prototype.lineEnd = Stream.prototype.lineEnd;
  GeoCircle$circleStream$ObjectLiteral.prototype.polygonStart = Stream.prototype.polygonStart;
  GeoCircle$circleStream$ObjectLiteral.prototype.polygonEnd = Stream.prototype.polygonEnd;
  GeoCircle$circleStream$ObjectLiteral.prototype.sphere = Stream.prototype.sphere;
  GeoLength.prototype.polygonStart = Stream.prototype.polygonStart;
  GeoLength.prototype.polygonEnd = Stream.prototype.polygonEnd;
  GeoLength.prototype.sphere = Stream.prototype.sphere;
  ClipStream.prototype.lineEnd = Stream.prototype.lineEnd;
  ClipStream.prototype.lineStart = Stream.prototype.lineStart;
  ClipStream.prototype.point_yvo9jy$ = Stream.prototype.point_yvo9jy$;
  ClipStream.prototype.polygonEnd = Stream.prototype.polygonEnd;
  ClipStream.prototype.polygonStart = Stream.prototype.polygonStart;
  ClipStream.prototype.sphere = Stream.prototype.sphere;
  AntimeridianClip$clipLine$ObjectLiteral.prototype.polygonEnd = ClipStream.prototype.polygonEnd;
  AntimeridianClip$clipLine$ObjectLiteral.prototype.polygonStart = ClipStream.prototype.polygonStart;
  AntimeridianClip$clipLine$ObjectLiteral.prototype.sphere = ClipStream.prototype.sphere;
  ClipBuffer.prototype.lineEnd = Stream.prototype.lineEnd;
  ClipBuffer.prototype.polygonEnd = Stream.prototype.polygonEnd;
  ClipBuffer.prototype.polygonStart = Stream.prototype.polygonStart;
  ClipBuffer.prototype.sphere = Stream.prototype.sphere;
  ClipCircle$clipLine$ObjectLiteral.prototype.polygonEnd = ClipStream.prototype.polygonEnd;
  ClipCircle$clipLine$ObjectLiteral.prototype.polygonStart = ClipStream.prototype.polygonStart;
  ClipCircle$clipLine$ObjectLiteral.prototype.sphere = ClipStream.prototype.sphere;
  ClipRectangle$clipLine$ObjectLiteral.prototype.sphere = ClipStream.prototype.sphere;
  PathArea.prototype.sphere = Stream.prototype.sphere;
  PathBounds.prototype.lineStart = Stream.prototype.lineStart;
  PathBounds.prototype.lineEnd = Stream.prototype.lineEnd;
  PathBounds.prototype.polygonStart = Stream.prototype.polygonStart;
  PathBounds.prototype.polygonEnd = Stream.prototype.polygonEnd;
  PathBounds.prototype.sphere = Stream.prototype.sphere;
  PathCentroid.prototype.sphere = Stream.prototype.sphere;
  PathContext.prototype.sphere = Stream.prototype.sphere;
  PathMeasure.prototype.sphere = Stream.prototype.sphere;
  AlberUSAProjection$pointStream$ObjectLiteral.prototype.lineStart = Stream.prototype.lineStart;
  AlberUSAProjection$pointStream$ObjectLiteral.prototype.lineEnd = Stream.prototype.lineEnd;
  AlberUSAProjection$pointStream$ObjectLiteral.prototype.polygonStart = Stream.prototype.polygonStart;
  AlberUSAProjection$pointStream$ObjectLiteral.prototype.polygonEnd = Stream.prototype.polygonEnd;
  AlberUSAProjection$pointStream$ObjectLiteral.prototype.sphere = Stream.prototype.sphere;
  ProjectableInvertable.prototype.projectLambda_lu1900$ = Projectable.prototype.projectLambda_lu1900$;
  ProjectableInvertable.prototype.projectPhi_lu1900$ = Projectable.prototype.projectPhi_lu1900$;
  Projection.prototype.projectLambda_lu1900$ = ProjectableInvertable.prototype.projectLambda_lu1900$;
  Projection.prototype.projectPhi_lu1900$ = ProjectableInvertable.prototype.projectPhi_lu1900$;
  ConicProjectable.prototype.projectLambda_lu1900$ = Projectable.prototype.projectLambda_lu1900$;
  ConicProjectable.prototype.projectPhi_lu1900$ = Projectable.prototype.projectPhi_lu1900$;
  ReSampledStream.prototype.sphere = Stream.prototype.sphere;
  noop = noop$lambda;
  noop2 = noop2$lambda;
  noop3 = noop3$lambda;
  CLIPMAX = 1.0E9;
  CLIPMIN = -CLIPMAX;
  scale = scale$lambda;
  angle = angle$lambda;
  scale_0 = scale$lambda_0;
  angle_0 = angle$lambda_0;
  A1 = 1.340264;
  A2 = -0.081106;
  A3 = 8.93E-4;
  A4 = 0.003796;
  M = Math_0.sqrt(3.0) / 2;
  iterations = 12;
  MAX_DEPTH = 16;
  var x = toRadians(30.0);
  COS_MIN_DISTANCE = Math_0.cos(x);
  Kotlin.defineModule('d2v-geo-js', _);
  return _;
}));

//# sourceMappingURL=d2v-geo-js.js.map
