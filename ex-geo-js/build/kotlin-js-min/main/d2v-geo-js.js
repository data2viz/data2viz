(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin', 'd2v-core-js', 'geojson-js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'), require('d2v-core-js'), require('geojson-js'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'd2v-geo-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'd2v-geo-js'.");
    }
    if (typeof this['d2v-core-js'] === 'undefined') {
      throw new Error("Error loading module 'd2v-geo-js'. Its dependency 'd2v-core-js' was not found. Please, check whether 'd2v-core-js' is loaded prior to 'd2v-geo-js'.");
    }
    if (typeof this['geojson-js'] === 'undefined') {
      throw new Error("Error loading module 'd2v-geo-js'. Its dependency 'geojson-js' was not found. Please, check whether 'geojson-js' is loaded prior to 'd2v-geo-js'.");
    }
    root['d2v-geo-js'] = factory(typeof this['d2v-geo-js'] === 'undefined' ? {} : this['d2v-geo-js'], kotlin, this['d2v-core-js'], this['geojson-js']);
  }
}(this, function (_, Kotlin, $module$d2v_core_js, $module$geojson_js) {
  'use strict';
  var toDegrees = $module$d2v_core_js.io.data2viz.math.toDegrees_yrwdxr$;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var toRadians = $module$d2v_core_js.io.data2viz.math.toRadians_yrwdxr$;
  var Math_0 = Math;
  var Geometry = $module$geojson_js.io.data2viz.geojson.Geometry;
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
  var math = $module$d2v_core_js.io.data2viz.math;
  var Unit = Kotlin.kotlin.Unit;
  var get_alt = $module$geojson_js.io.data2viz.geojson.get_alt_hb77y9$;
  var get_lon = $module$geojson_js.io.data2viz.geojson.get_lon_hb77y9$;
  var get_lat = $module$geojson_js.io.data2viz.geojson.get_lat_hb77y9$;
  var toDoubleArray = Kotlin.kotlin.collections.toDoubleArray_tcduak$;
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_ww73n8$;
  var ensureNotNull = Kotlin.ensureNotNull;
  var IllegalArgumentException_init = Kotlin.kotlin.IllegalArgumentException_init_pdl1vj$;
  var Extent = $module$d2v_core_js.io.data2viz.geom.Extent;
  var getCallableRef = Kotlin.getCallableRef;
  var kotlin_js_internal_DoubleCompanionObject = Kotlin.kotlin.js.internal.DoubleCompanionObject;
  var mutableListOf = Kotlin.kotlin.collections.mutableListOf_i5x0yv$;
  var IntRange = Kotlin.kotlin.ranges.IntRange;
  var last = Kotlin.kotlin.collections.last_2p1efm$;
  var ArrayList_init_0 = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  var sortWith = Kotlin.kotlin.collections.sortWith_nqfjgj$;
  var wrapFunction = Kotlin.wrapFunction;
  var Comparator = Kotlin.kotlin.Comparator;
  var copyToArray = Kotlin.kotlin.collections.copyToArray;
  var throwUPAE = Kotlin.throwUPAE;
  var asReversed = Kotlin.kotlin.collections.asReversed_2p1efm$;
  var range = $module$d2v_core_js.io.data2viz.math.range_yvo9jy$;
  var collectionSizeOrDefault = Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$;
  var addAll = Kotlin.kotlin.collections.addAll_ipc267$;
  var math_0 = Kotlin.kotlin.math;
  var isNaN_0 = Kotlin.kotlin.isNaN_yrwdxr$;
  var rangeTo = Kotlin.kotlin.ranges.rangeTo_38ydlf$;
  var Kind_INTERFACE = Kotlin.Kind.INTERFACE;
  var Enum = Kotlin.kotlin.Enum;
  var throwISE = Kotlin.throwISE;
  var flatten = Kotlin.kotlin.collections.flatten_u0ad8z$;
  var Kind_OBJECT = Kotlin.Kind.OBJECT;
  var until = Kotlin.kotlin.ranges.until_dqglrj$;
  var coerceIn = Kotlin.kotlin.ranges.coerceIn_nig4hr$;
  var IllegalStateException_init = Kotlin.kotlin.IllegalStateException_init;
  var first = Kotlin.kotlin.collections.first_2p1efm$;
  var get_deg = $module$d2v_core_js.io.data2viz.math.get_deg_rcaex3$;
  var listOf = Kotlin.kotlin.collections.listOf_i5x0yv$;
  var get_rad = $module$d2v_core_js.io.data2viz.math.get_rad_rcaex3$;
  var equals = Kotlin.equals;
  var log = Kotlin.kotlin.math.log_lu1900$;
  ClippableStream$LineStartContext.prototype = Object.create(Enum.prototype);
  ClippableStream$LineStartContext.prototype.constructor = ClippableStream$LineStartContext;
  ClippableStream$LineEndContext.prototype = Object.create(Enum.prototype);
  ClippableStream$LineEndContext.prototype.constructor = ClippableStream$LineEndContext;
  ClippableStream$PointContext.prototype = Object.create(Enum.prototype);
  ClippableStream$PointContext.prototype.constructor = ClippableStream$PointContext;
  PathStream$PathCmd.prototype = Object.create(Enum.prototype);
  PathStream$PathCmd.prototype.constructor = PathStream$PathCmd;
  AlbersUSAProjection.prototype = Object.create(ComposedProjection.prototype);
  AlbersUSAProjection.prototype.constructor = AlbersUSAProjection;
  AzimuthalEquidistantProjection.prototype = Object.create(Azimuthal.prototype);
  AzimuthalEquidistantProjection.prototype.constructor = AzimuthalEquidistantProjection;
  ConicProjection.prototype = Object.create(ProjectorProjection.prototype);
  ConicProjection.prototype.constructor = ConicProjection;
  BaseConditionalProjector.prototype = Object.create(ConditionalProjector.prototype);
  BaseConditionalProjector.prototype.constructor = BaseConditionalProjector;
  ConicConformalBaseConditionalProjector.prototype = Object.create(BaseConditionalProjector.prototype);
  ConicConformalBaseConditionalProjector.prototype.constructor = ConicConformalBaseConditionalProjector;
  ConicEqualAreaBaseConditionalProjector.prototype = Object.create(BaseConditionalProjector.prototype);
  ConicEqualAreaBaseConditionalProjector.prototype.constructor = ConicEqualAreaBaseConditionalProjector;
  ConicEquidistantBaseConditionalProjector.prototype = Object.create(BaseConditionalProjector.prototype);
  ConicEquidistantBaseConditionalProjector.prototype.constructor = ConicEquidistantBaseConditionalProjector;
  MercatorProjection.prototype = Object.create(ProjectorProjection.prototype);
  MercatorProjection.prototype.constructor = MercatorProjection;
  TransverseMercatorProjection.prototype = Object.create(MercatorProjection.prototype);
  TransverseMercatorProjection.prototype.constructor = TransverseMercatorProjection;
  transformRadians$lambda$ObjectLiteral.prototype = Object.create(DelegateStreamAdapter.prototype);
  transformRadians$lambda$ObjectLiteral.prototype.constructor = transformRadians$lambda$ObjectLiteral;
  transformRotate$lambda$ObjectLiteral.prototype = Object.create(DelegateStreamAdapter.prototype);
  transformRotate$lambda$ObjectLiteral.prototype.constructor = transformRotate$lambda$ObjectLiteral;
  ResampleStream$LineStartContext.prototype = Object.create(Enum.prototype);
  ResampleStream$LineStartContext.prototype.constructor = ResampleStream$LineStartContext;
  ResampleStream$LineEndContext.prototype = Object.create(Enum.prototype);
  ResampleStream$LineEndContext.prototype.constructor = ResampleStream$LineEndContext;
  ResampleStream$PointContext.prototype = Object.create(Enum.prototype);
  ResampleStream$PointContext.prototype.constructor = ResampleStream$PointContext;
  resampleNone$lambda$ObjectLiteral.prototype = Object.create(DelegateStreamAdapter.prototype);
  resampleNone$lambda$ObjectLiteral.prototype.constructor = resampleNone$lambda$ObjectLiteral;
  function get_lambda($receiver) {
    return $receiver[0];
  }
  function get_phi($receiver) {
    return $receiver[1];
  }
  function get_alt_0($receiver) {
    return $receiver.length > 2 ? $receiver[2] : null;
  }
  function component1($receiver) {
    return get_lambda($receiver);
  }
  function component2($receiver) {
    return get_phi($receiver);
  }
  function component3($receiver) {
    return get_alt_0($receiver);
  }
  function GeoPoint(lambda, phi, alt) {
    if (alt === void 0)
      alt = null;
    return alt == null ? new Float64Array([lambda, phi]) : new Float64Array([lambda, phi, alt]);
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
  function contains($receiver, point) {
    if (Kotlin.isType($receiver, Point))
      return contains_2(get_pos($receiver), point);
    else if (Kotlin.isType($receiver, MultiPoint)) {
      var $receiver_0 = get_positions($receiver);
      var any$result;
      any$break: do {
        var tmp$;
        for (tmp$ = 0; tmp$ !== $receiver_0.length; ++tmp$) {
          var element = $receiver_0[tmp$];
          if (contains_2(element, point)) {
            any$result = true;
            break any$break;
          }
        }
        any$result = false;
      }
       while (false);
      return any$result;
    }
     else if (Kotlin.isType($receiver, Polygon))
      return contains_0(get_lines($receiver), point);
    else if (Kotlin.isType($receiver, MultiPolygon)) {
      var $receiver_1 = get_surface($receiver);
      var any$result_0;
      any$break: do {
        var tmp$_0;
        for (tmp$_0 = 0; tmp$_0 !== $receiver_1.length; ++tmp$_0) {
          var element_0 = $receiver_1[tmp$_0];
          if (contains_0(element_0, point)) {
            any$result_0 = true;
            break any$break;
          }
        }
        any$result_0 = false;
      }
       while (false);
      return any$result_0;
    }
     else if (Kotlin.isType($receiver, LineString))
      return contains_1(get_positions_0($receiver), point);
    else if (Kotlin.isType($receiver, MultiLineString)) {
      var $receiver_2 = get_lines_0($receiver);
      var any$result_1;
      any$break: do {
        var tmp$_1;
        for (tmp$_1 = 0; tmp$_1 !== $receiver_2.length; ++tmp$_1) {
          var element_1 = $receiver_2[tmp$_1];
          if (contains_1(element_1, point)) {
            any$result_1 = true;
            break any$break;
          }
        }
        any$result_1 = false;
      }
       while (false);
      return any$result_1;
    }
     else if (Kotlin.isType($receiver, Sphere))
      return true;
    else if (Kotlin.isType($receiver, GeometryCollection)) {
      var $receiver_3 = $receiver.geometries;
      var any$result_2;
      any$break: do {
        var tmp$_2;
        for (tmp$_2 = 0; tmp$_2 !== $receiver_3.length; ++tmp$_2) {
          var element_2 = $receiver_3[tmp$_2];
          if (contains(element_2, point)) {
            any$result_2 = true;
            break any$break;
          }
        }
        any$result_2 = false;
      }
       while (false);
      return any$result_2;
    }
     else if (Kotlin.isType($receiver, FeatureCollection)) {
      var $receiver_4 = $receiver.features;
      var any$result_3;
      any$break: do {
        var tmp$_3;
        for (tmp$_3 = 0; tmp$_3 !== $receiver_4.length; ++tmp$_3) {
          var element_3 = $receiver_4[tmp$_3];
          if (contains(element_3, point)) {
            any$result_3 = true;
            break any$break;
          }
        }
        any$result_3 = false;
      }
       while (false);
      return any$result_3;
    }
     else if (Kotlin.isType($receiver, Feature))
      return contains($receiver.geometry, point);
    else
      return false;
  }
  function contains_0($receiver, point) {
    var destination = ArrayList_init($receiver.length);
    var tmp$;
    for (tmp$ = 0; tmp$ !== $receiver.length; ++tmp$) {
      var item = $receiver[tmp$];
      var tmp$_0 = destination.add_11rb$;
      var destination_0 = ArrayList_init(item.length);
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
  function contains_1($receiver, point) {
    var ab = geoDistance($receiver[0], $receiver[1]);
    var ao = geoDistance($receiver[0], point);
    var ob = geoDistance(point, $receiver[1]);
    return ao + ob <= ab + math.EPSILON;
  }
  function contains_2($receiver, point) {
    return geoDistance($receiver, point) === 0.0;
  }
  function get_pos($receiver) {
    return $receiver.coordinates;
  }
  function get_positions($receiver) {
    return $receiver.coordinates;
  }
  function get_positions_0($receiver) {
    return $receiver.coordinates;
  }
  function get_lines($receiver) {
    return $receiver.coordinates;
  }
  function get_lines_0($receiver) {
    return $receiver.coordinates;
  }
  function get_surface($receiver) {
    return $receiver.coordinates;
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
  function stream($receiver, stream_0) {
    if (Kotlin.isType($receiver, FeatureCollection)) {
      var $receiver_0 = $receiver.features;
      var tmp$;
      for (tmp$ = 0; tmp$ !== $receiver_0.length; ++tmp$) {
        var element = $receiver_0[tmp$];
        stream(element, stream_0);
      }
    }
     else if (Kotlin.isType($receiver, Feature))
      stream($receiver.geometry, stream_0);
    else if (Kotlin.isType($receiver, GeometryCollection)) {
      var $receiver_1 = $receiver.geometries;
      var tmp$_0;
      for (tmp$_0 = 0; tmp$_0 !== $receiver_1.length; ++tmp$_0) {
        var element_0 = $receiver_1[tmp$_0];
        streamGeometry(element_0, stream_0);
      }
    }
     else if (Kotlin.isType($receiver, Geometry))
      streamGeometry($receiver, stream_0);
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
    var tmp$;
    var z = (tmp$ = get_alt(coordinates)) != null ? tmp$ : 0.0;
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
  function toRadians_0(position) {
    var destination = ArrayList_init(position.length);
    var tmp$;
    for (tmp$ = 0; tmp$ !== position.length; ++tmp$) {
      var item = position[tmp$];
      destination.add_11rb$(toRadians(item));
    }
    return toDoubleArray(destination);
  }
  function geoPath(projection, path) {
    if (projection === void 0)
      projection = null;
    if (path === void 0)
      path = null;
    return new GeoPath(projection != null ? projection : identityProjection(), path);
  }
  function GeoPath(projection, path) {
    this.projection = projection;
    this.path = path;
    this.areaStream_0 = new AreaStream();
    this.boundsStream_0 = new BoundsStream();
    this.centroidStream_0 = new CentroidStream();
    this.measureStream_0 = new MeasureStream();
    var tmp$;
    this.pathStream_0 = (tmp$ = this.path) != null ? new PathStream(tmp$) : null;
  }
  Object.defineProperty(GeoPath.prototype, 'pointRadius', {
    get: function () {
      return ensureNotNull(this.pathStream_0).pointRadius;
    },
    set: function (value) {
      ensureNotNull(this.pathStream_0).pointRadius;
    }
  });
  GeoPath.prototype.project_6ux19g$ = function (geo) {
    if (this.path == null) {
      var message = 'Cannot use GeoPath.svgPath() without a valid path.';
      throw IllegalArgumentException_init(message.toString());
    }
    if (this.pathStream_0 == null) {
      var message_0 = 'Cannot use GeoPath.svgPath() without a valid path.';
      throw IllegalArgumentException_init(message_0.toString());
    }
    stream(geo, this.projection.stream_enk0m$(this.pathStream_0));
  };
  GeoPath.prototype.centroid_6ux19g$ = function (geo) {
    stream(geo, this.projection.stream_enk0m$(this.centroidStream_0));
    return this.centroidStream_0.result_8be2vx$();
  };
  GeoPath.prototype.area_6ux19g$ = function (geo) {
    stream(geo, this.projection.stream_enk0m$(this.areaStream_0));
    return this.areaStream_0.result();
  };
  GeoPath.prototype.bounds_6ux19g$ = function (geo) {
    stream(geo, this.projection.stream_enk0m$(this.boundsStream_0));
    return this.boundsStream_0.result();
  };
  GeoPath.prototype.measure_6ux19g$ = function (geo) {
    stream(geo, this.projection.stream_enk0m$(this.measureStream_0));
    return this.measureStream_0.result();
  };
  GeoPath.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'GeoPath',
    interfaces: []
  };
  function fit(projection, fitBounds, geo) {
    var clip = get_extentPostClip(projection);
    projection.scale = 150.0;
    projection.translate_lu1900$(0.0, 0.0);
    if (clip != null)
      set_extentPostClip(projection, null);
    var boundsStream = new BoundsStream();
    stream(geo, projection.stream_enk0m$(boundsStream));
    fitBounds(boundsStream.result());
    if (clip != null)
      set_extentPostClip(projection, clip);
    return projection;
  }
  function fitExtent$lambda(closure$extent, this$fitExtent) {
    return function (size) {
      var w = closure$extent.width;
      var h = closure$extent.height;
      var a = w / size.width;
      var b = h / size.height;
      var k = Math_0.min(a, b);
      var x = closure$extent.x0 + (w - k * (size.x1 + size.x0)) / 2;
      var y = closure$extent.y0 + (h - k * (size.y1 + size.y0)) / 2;
      this$fitExtent.scale = k * 150;
      this$fitExtent.translate_lu1900$(x, y);
      return Unit;
    };
  }
  function fitExtent($receiver, extent, geo) {
    var fitBounds = fitExtent$lambda(extent, $receiver);
    return fit($receiver, fitBounds, geo);
  }
  function fitWidth$lambda(closure$width, this$fitWidth) {
    return function (size) {
      var k = closure$width / size.width;
      var x = (closure$width - k * (size.x1 + size.x0)) / 2;
      var y = -k * size.y0;
      this$fitWidth.scale = k * 150;
      this$fitWidth.translate_lu1900$(x, y);
      return Unit;
    };
  }
  function fitWidth($receiver, width, geo) {
    var fitBounds = fitWidth$lambda(width, $receiver);
    return fit($receiver, fitBounds, geo);
  }
  function fitHeight$lambda(closure$height, this$fitHeight) {
    return function (size) {
      var k = closure$height / size.height;
      var x = -k * size.x0;
      var y = (closure$height - k * (size.y1 + size.y0)) / 2;
      this$fitHeight.scale = k * 150;
      this$fitHeight.translate_lu1900$(x, y);
      return Unit;
    };
  }
  function fitHeight($receiver, height, geo) {
    var fitBounds = fitHeight$lambda(height, $receiver);
    return fit($receiver, fitBounds, geo);
  }
  function fitSize($receiver, width, height, geo) {
    return fitExtent($receiver, new Extent(0.0, 0.0, width, height), geo);
  }
  function geoArea(geo) {
    return (new GeoAreaStream()).result_6ux19g$(geo);
  }
  function GeoAreaStream() {
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
  GeoAreaStream.prototype.result_6ux19g$ = function (geo) {
    this.areaSum_0 = 0.0;
    stream(geo, this);
    return this.areaSum_0 * 2;
  };
  GeoAreaStream.prototype.point_yvo9jy$ = function (x, y, z) {
    this.currentPoint_0(x, y);
  };
  GeoAreaStream.prototype.lineStart = function () {
    this.currentLineStart_0();
  };
  GeoAreaStream.prototype.lineEnd = function () {
    this.currentLineEnd_0();
  };
  GeoAreaStream.prototype.polygonStart = function () {
    this.areaRingSum_8be2vx$ = 0.0;
    this.currentLineStart_0 = getCallableRef('areaRingStart', function ($receiver) {
      return $receiver.areaRingStart_0(), Unit;
    }.bind(null, this));
    this.currentLineEnd_0 = getCallableRef('areaRingEnd', function ($receiver) {
      return $receiver.areaRingEnd_0(), Unit;
    }.bind(null, this));
  };
  GeoAreaStream.prototype.polygonEnd = function () {
    this.currentLineStart_0 = noop;
    this.currentLineEnd_0 = noop;
    this.currentPoint_0 = noop2;
    this.areaSum_0 += this.areaRingSum_8be2vx$ + (this.areaRingSum_8be2vx$ < 0 ? math.TAU : 0.0);
  };
  GeoAreaStream.prototype.sphere = function () {
    this.areaSum_0 += math.TAU;
  };
  GeoAreaStream.prototype.areaRingStart_0 = function () {
    this.currentPoint_0 = getCallableRef('areaPointFirst', function ($receiver, x, y) {
      return $receiver.areaPointFirst_0(x, y), Unit;
    }.bind(null, this));
  };
  GeoAreaStream.prototype.areaPointFirst_0 = function (x, y) {
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
  GeoAreaStream.prototype.areaPoint_0 = function (x, y) {
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
  GeoAreaStream.prototype.areaRingEnd_0 = function () {
    this.areaPoint_0(this.lambda00_0, this.phi00_0);
  };
  GeoAreaStream.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'GeoAreaStream',
    interfaces: [Stream]
  };
  function Comparator$ObjectLiteral(closure$comparison) {
    this.closure$comparison = closure$comparison;
  }
  Comparator$ObjectLiteral.prototype.compare = function (a, b) {
    return this.closure$comparison(a, b);
  };
  Comparator$ObjectLiteral.$metadata$ = {kind: Kind_CLASS, interfaces: [Comparator]};
  var compareBy$lambda = wrapFunction(function () {
    var compareValues = Kotlin.kotlin.comparisons.compareValues_s00gnj$;
    return function (closure$selector) {
      return function (a, b) {
        var selector = closure$selector;
        return compareValues(selector(a), selector(b));
      };
    };
  });
  function geoBounds(geo) {
    return (new GeoBoundsStream()).result_6ux19g$(geo);
  }
  function GeoBoundsStream() {
    this.areaStream_0 = new GeoAreaStream();
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
    this.ranges_0 = ArrayList_init_0();
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
  function GeoBoundsStream$result$lambda(it) {
    return it[0];
  }
  GeoBoundsStream.prototype.result_6ux19g$ = function (geo) {
    this.phi0_0 = kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY;
    this.lambda0_0 = this.phi0_0;
    this.phi1_0 = -this.lambda0_0;
    this.lambda1_0 = this.phi1_0;
    this.ranges_0.clear();
    stream(geo, this);
    if (!this.ranges_0.isEmpty()) {
      var $receiver = this.ranges_0;
      if ($receiver.size > 1) {
        sortWith($receiver, new Comparator$ObjectLiteral(compareBy$lambda(GeoBoundsStream$result$lambda)));
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
  GeoBoundsStream.prototype.point_yvo9jy$ = function (x, y, z) {
    this.currentPoint_0(x, y);
  };
  GeoBoundsStream.prototype.lineStart = function () {
    this.currentLineStart_0();
  };
  GeoBoundsStream.prototype.lineEnd = function () {
    this.currentLineEnd_0();
  };
  GeoBoundsStream.prototype.polygonStart = function () {
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
  GeoBoundsStream.prototype.polygonEnd = function () {
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
  GeoBoundsStream.prototype.rangeContains_0 = function (range, x) {
    return range[0] <= range[1] ? range[0] <= x && x <= range[1] : x < range[0] || range[1] < x;
  };
  GeoBoundsStream.prototype.boundsPoint_0 = function (x, y) {
    this.lambda0_0 = x;
    this.lambda1_0 = x;
    this.range_0 = new Float64Array([this.lambda0_0, this.lambda1_0]);
    this.ranges_0.add_11rb$(this.range_0);
    if (y < this.phi0_0)
      this.phi0_0 = y;
    if (y > this.phi1_0)
      this.phi1_0 = y;
  };
  GeoBoundsStream.prototype.linePoint_0 = function (x, y) {
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
  GeoBoundsStream.prototype.boundsLineStart_0 = function () {
    this.currentPoint_0 = getCallableRef('linePoint', function ($receiver, x, y) {
      return $receiver.linePoint_0(x, y), Unit;
    }.bind(null, this));
  };
  GeoBoundsStream.prototype.boundsLineEnd_0 = function () {
    this.range_0[0] = this.lambda0_0;
    this.range_0[1] = this.lambda1_0;
    this.currentPoint_0 = getCallableRef('boundsPoint', function ($receiver, x, y) {
      return $receiver.boundsPoint_0(x, y), Unit;
    }.bind(null, this));
    this.p0_0 = null;
  };
  GeoBoundsStream.prototype.boundsRingPoint_0 = function (x, y) {
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
  GeoBoundsStream.prototype.boundsRingStart_0 = function () {
    this.areaStream_0.lineStart();
  };
  GeoBoundsStream.prototype.boundsRingEnd_0 = function () {
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
  GeoBoundsStream.prototype.angle_0 = function (lambda0, lambda1) {
    var diff = lambda1 - lambda0;
    return diff < 0.0 ? diff + 360.0 : diff;
  };
  GeoBoundsStream.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'GeoBoundsStream',
    interfaces: [Stream]
  };
  function geoCentroid(geo) {
    return (new GeoCentroidStream()).result_6ux19g$(geo);
  }
  function GeoCentroidStream() {
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
  GeoCentroidStream.prototype.result_6ux19g$ = function (geo) {
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
  GeoCentroidStream.prototype.point_yvo9jy$ = function (x, y, z) {
    this.currentPoint_0(x, y);
  };
  GeoCentroidStream.prototype.lineStart = function () {
    this.currentLineStart_0();
  };
  GeoCentroidStream.prototype.lineEnd = function () {
    this.currentLineEnd_0();
  };
  GeoCentroidStream.prototype.polygonStart = function () {
    this.currentLineStart_0 = getCallableRef('centroidRingStart', function ($receiver) {
      return $receiver.centroidRingStart_0(), Unit;
    }.bind(null, this));
    this.currentLineEnd_0 = getCallableRef('centroidRingEnd', function ($receiver) {
      return $receiver.centroidRingEnd_0(), Unit;
    }.bind(null, this));
  };
  GeoCentroidStream.prototype.polygonEnd = function () {
    this.currentLineStart_0 = getCallableRef('centroidLineStart', function ($receiver) {
      return $receiver.centroidLineStart_0(), Unit;
    }.bind(null, this));
    this.currentLineEnd_0 = getCallableRef('centroidLineEnd', function ($receiver) {
      return $receiver.centroidLineEnd_0(), Unit;
    }.bind(null, this));
  };
  GeoCentroidStream.prototype.centroidPoint_0 = function (x, y) {
    var lambda = toRadians(x);
    var phi = toRadians(y);
    var cosPhi = Math_0.cos(phi);
    this.centroidPointCartesian_0(cosPhi * Math_0.cos(lambda), cosPhi * Math_0.sin(lambda), Math_0.sin(phi));
  };
  GeoCentroidStream.prototype.centroidPointCartesian_0 = function (x, y, z) {
    this._W0_0 = this._W0_0 + 1;
    this._X0_0 += (x - this._X0_0) / this._W0_0;
    this._Y0_0 += (y - this._Y0_0) / this._W0_0;
    this._Z0_0 += (z - this._Z0_0) / this._W0_0;
  };
  GeoCentroidStream.prototype.centroidLineStart_0 = function () {
    this.currentPoint_0 = getCallableRef('centroidLinePointFirst', function ($receiver, x, y) {
      return $receiver.centroidLinePointFirst_0(x, y), Unit;
    }.bind(null, this));
  };
  GeoCentroidStream.prototype.centroidLinePointFirst_0 = function (x, y) {
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
  GeoCentroidStream.prototype.centroidLinePoint_0 = function (x, y) {
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
  GeoCentroidStream.prototype.centroidLineEnd_0 = function () {
    this.currentPoint_0 = getCallableRef('centroidPoint', function ($receiver, x, y) {
      return $receiver.centroidPoint_0(x, y), Unit;
    }.bind(null, this));
  };
  GeoCentroidStream.prototype.centroidRingStart_0 = function () {
    this.currentPoint_0 = getCallableRef('centroidRingPointFirst', function ($receiver, x, y) {
      return $receiver.centroidRingPointFirst_0(x, y), Unit;
    }.bind(null, this));
  };
  GeoCentroidStream.prototype.centroidRingEnd_0 = function () {
    this.centroidRingPoint_0(this.lambda00_0, this.phi00_0);
    this.currentPoint_0 = getCallableRef('centroidPoint', function ($receiver, x, y) {
      return $receiver.centroidPoint_0(x, y), Unit;
    }.bind(null, this));
  };
  GeoCentroidStream.prototype.centroidRingPointFirst_0 = function (x, y) {
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
  GeoCentroidStream.prototype.centroidRingPoint_0 = function (x, y) {
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
  GeoCentroidStream.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'GeoCentroidStream',
    interfaces: [Stream]
  };
  function GeoCircle() {
    this.ring_0 = ArrayList_init_0();
    this.rotate_0 = null;
    this.circleStream_0 = new GeoCircle$circleStream$ObjectLiteral(this);
    this.center = GeoCircle$center$lambda;
    this.radius = GeoCircle$radius$lambda;
    this.precision = GeoCircle$precision$lambda;
  }
  GeoCircle.prototype.circle_11rb$ = function (data) {
    if (data === void 0)
      data = null;
    var $receiver = this.center(data);
    var destination = ArrayList_init($receiver.length);
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
    }.bind(null, createRotateRadiansProjector(c[0], c[1], 0.0)));
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
  function geoDistance(from, to) {
    var line = new LineString([from, to]);
    return (new GeoLengthStream()).result_6ux19g$(line);
  }
  function geoLength(geo) {
    return (new GeoLengthStream()).result_6ux19g$(geo);
  }
  function GeoLengthStream() {
    this.lengthSum_0 = 0.0;
    this.lambda0_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.cosPhi0_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.sinPhi0_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.currentPoint_0 = noop2;
    this.currentLineEnd_0 = noop;
  }
  GeoLengthStream.prototype.result_6ux19g$ = function (geo) {
    this.lengthSum_0 = 0.0;
    stream(geo, this);
    return this.lengthSum_0;
  };
  GeoLengthStream.prototype.point_yvo9jy$ = function (x, y, z) {
    this.currentPoint_0(x, y);
  };
  GeoLengthStream.prototype.lineStart = function () {
    this.currentPoint_0 = getCallableRef('lengthPointFirst', function ($receiver, x, y) {
      return $receiver.lengthPointFirst_0(x, y), Unit;
    }.bind(null, this));
    this.currentLineEnd_0 = getCallableRef('lengthLineEnd', function ($receiver) {
      return $receiver.lengthLineEnd_0(), Unit;
    }.bind(null, this));
  };
  GeoLengthStream.prototype.lineEnd = function () {
    this.currentLineEnd_0();
  };
  GeoLengthStream.prototype.lengthPointFirst_0 = function (x, y) {
    var lambda = toRadians(x);
    var phi = toRadians(y);
    this.lambda0_0 = lambda;
    this.sinPhi0_0 = Math_0.sin(phi);
    this.cosPhi0_0 = Math_0.cos(phi);
    this.currentPoint_0 = getCallableRef('lengthPoint', function ($receiver, x, y) {
      return $receiver.lengthPoint_0(x, y), Unit;
    }.bind(null, this));
  };
  GeoLengthStream.prototype.lengthLineEnd_0 = function () {
    this.currentPoint_0 = noop2;
    this.currentLineEnd_0 = noop;
  };
  GeoLengthStream.prototype.lengthPoint_0 = function (x, y) {
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
  GeoLengthStream.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'GeoLengthStream',
    interfaces: [Stream]
  };
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
  function geoGraticule$lambda($receiver) {
    return Unit;
  }
  function geoGraticule(init) {
    if (init === void 0)
      init = geoGraticule$lambda;
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
    this.minorX_twedcp$_0 = this.minorX_twedcp$_0;
    this.minorY_twedbu$_0 = this.minorY_twedbu$_0;
    this.majorX_xqyivp$_0 = this.majorX_xqyivp$_0;
    this.majorY_xqyiuu$_0 = this.majorY_xqyiuu$_0;
    this.precision_uhslqs$_0 = 2.5;
  }
  Object.defineProperty(Graticule.prototype, 'minorX_0', {
    get: function () {
      if (this.minorX_twedcp$_0 == null)
        return throwUPAE('minorX');
      return this.minorX_twedcp$_0;
    },
    set: function (minorX) {
      this.minorX_twedcp$_0 = minorX;
    }
  });
  Object.defineProperty(Graticule.prototype, 'minorY_0', {
    get: function () {
      if (this.minorY_twedbu$_0 == null)
        return throwUPAE('minorY');
      return this.minorY_twedbu$_0;
    },
    set: function (minorY) {
      this.minorY_twedbu$_0 = minorY;
    }
  });
  Object.defineProperty(Graticule.prototype, 'majorX_0', {
    get: function () {
      if (this.majorX_xqyivp$_0 == null)
        return throwUPAE('majorX');
      return this.majorX_xqyivp$_0;
    },
    set: function (majorX) {
      this.majorX_xqyivp$_0 = majorX;
    }
  });
  Object.defineProperty(Graticule.prototype, 'majorY_0', {
    get: function () {
      if (this.majorY_xqyiuu$_0 == null)
        return throwUPAE('majorY');
      return this.majorY_xqyiuu$_0;
    },
    set: function (majorY) {
      this.majorY_xqyiuu$_0 = majorY;
    }
  });
  Object.defineProperty(Graticule.prototype, 'precision', {
    get: function () {
      return this.precision_uhslqs$_0;
    },
    set: function (value) {
      this.precision_uhslqs$_0 = value;
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
  Graticule.prototype.graticule = function () {
    var $receiver = this.buildLines_0();
    var destination = ArrayList_init(collectionSizeOrDefault($receiver, 10));
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      var tmp$_0 = destination.add_11rb$;
      var destination_0 = ArrayList_init(collectionSizeOrDefault(item, 10));
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
    var destination = ArrayList_init(collectionSizeOrDefault($receiver, 10));
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      var tmp$_0 = destination.add_11rb$;
      var destination_0 = ArrayList_init(collectionSizeOrDefault(item, 10));
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
  Graticule.prototype.outline = function () {
    var coordinates = toMutableList(this.majorX_0(this.majorExtent_0.x0));
    addAll(coordinates, this.majorY_0(this.majorExtent_0.y1).subList_vux9f0$(1, get_lastIndex(this.majorY_0(this.majorExtent_0.y1))));
    addAll(coordinates, asReversed(this.majorX_0(this.majorExtent_0.x1)).subList_vux9f0$(1, get_lastIndex(this.majorX_0(this.majorExtent_0.x1))));
    addAll(coordinates, asReversed(this.majorY_0(this.majorExtent_0.y0)).subList_vux9f0$(1, get_lastIndex(this.majorY_0(this.majorExtent_0.y0))));
    var destination = ArrayList_init(collectionSizeOrDefault(coordinates, 10));
    var tmp$;
    tmp$ = coordinates.iterator();
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
    var destination = ArrayList_init(collectionSizeOrDefault($receiver, 10));
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
    var destination_0 = ArrayList_init(collectionSizeOrDefault($receiver_0, 10));
    var tmp$_0;
    tmp$_0 = $receiver_0.iterator();
    while (tmp$_0.hasNext()) {
      var item_0 = tmp$_0.next();
      destination_0.add_11rb$(transform_0(item_0));
    }
    addAll(lines, destination_0);
    var x_1 = this.minorExtent_0.x0 / this.minorStepX_0;
    var $receiver_1 = range(Math_0.ceil(x_1) * this.minorStepX_0, this.minorExtent_0.x1, this.minorStepX_0);
    var destination_1 = ArrayList_init_0();
    var tmp$_1;
    tmp$_1 = $receiver_1.iterator();
    while (tmp$_1.hasNext()) {
      var element = tmp$_1.next();
      var x_2 = element % this.majorStepX_0;
      if (Math_0.abs(x_2) > math.EPSILON)
        destination_1.add_11rb$(element);
    }
    var transform_1 = this.minorX_0;
    var destination_2 = ArrayList_init(collectionSizeOrDefault(destination_1, 10));
    var tmp$_2;
    tmp$_2 = destination_1.iterator();
    while (tmp$_2.hasNext()) {
      var item_1 = tmp$_2.next();
      destination_2.add_11rb$(transform_1(item_1));
    }
    addAll(lines, destination_2);
    var x_3 = this.minorExtent_0.y0 / this.minorStepY_0;
    var $receiver_2 = range(Math_0.ceil(x_3) * this.minorStepY_0, this.minorExtent_0.y1, this.minorStepY_0);
    var destination_3 = ArrayList_init_0();
    var tmp$_3;
    tmp$_3 = $receiver_2.iterator();
    while (tmp$_3.hasNext()) {
      var element_0 = tmp$_3.next();
      var x_4 = element_0 % this.majorStepY_0;
      if (Math_0.abs(x_4) > math.EPSILON)
        destination_3.add_11rb$(element_0);
    }
    var transform_2 = this.minorY_0;
    var destination_4 = ArrayList_init(collectionSizeOrDefault(destination_3, 10));
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
      var destination = ArrayList_init(collectionSizeOrDefault($receiver, 10));
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
      var destination = ArrayList_init(collectionSizeOrDefault($receiver, 10));
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
  function get_limitedAsin($receiver) {
    if ($receiver > 1)
      return math.HALFPI;
    else if ($receiver < -1)
      return -math.HALFPI;
    else
      return get_asin($receiver);
  }
  function get_asin($receiver) {
    return Math_0.asin($receiver);
  }
  function get_acos($receiver) {
    return Math_0.acos($receiver);
  }
  function get_limitedAcos($receiver) {
    if ($receiver > 1)
      return 0.0;
    else if ($receiver < -1)
      return math.PI;
    else
      return get_acos($receiver);
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
      var sinPhi0 = Math_0.sin(phi0);
      var cosPhi0 = Math_0.cos(phi0);
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
        var x = cosPhi0 * cosPhi1 + k * Math_0.cos(absDelta);
        sum += Math_0.atan2(tmp$, x);
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
          var x_0 = cross0 * cross0 + cross1 * cross1 + cross2 * cross2;
          var normalize = Math_0.sqrt(x_0);
          var d0 = cross0 / normalize;
          var d1 = cross1 / normalize;
          var d2 = cross2 / normalize;
          var intersectionD0 = normal1 * d2 - normal2 * d1;
          var intersectionD1 = normal2 * d0 - normal0 * d2;
          var intersectionD2 = normal0 * d1 - normal1 * d0;
          var x_1 = intersectionD0 * intersectionD0 + intersectionD1 * intersectionD1 + intersectionD2 * intersectionD2;
          var intersectionNormalize = Math_0.sqrt(x_1);
          intersectionD2 /= intersectionNormalize;
          var phiArc = (antimeridian ^ delta >= 0 ? -1 : 1) * Math_0.asin(intersectionD2);
          if (phi > phiArc || (phi === phiArc && (d0 !== 0.0 && !isNaN_0(d0) || (d1 !== 0.0 && !isNaN_0(d1))))) {
            winding = winding + (antimeridian ^ delta >= 0 ? 1 : -1) | 0;
          }
        }
        lambda0 = lambda1;
        sinPhi0 = sinPhi1;
        cosPhi0 = cosPhi1;
        point0 = point1;
      }
    }
    return (angle < -math.EPSILON || (angle < math.EPSILON && sum < -math.EPSILON)) ^ (winding & 1) !== 0;
  }
  function quaternion(eulerAngles) {
    var l = eulerAngles[0] / 2 * math.DEG_TO_RAD;
    var sl = Math_0.sin(l);
    var cl = Math_0.cos(l);
    var p = eulerAngles[1] / 2 * math.DEG_TO_RAD;
    var sp = Math_0.sin(p);
    var cp = Math_0.cos(p);
    var g = eulerAngles[2] / 2 * math.DEG_TO_RAD;
    var sg = Math_0.sin(g);
    var cg = Math_0.cos(g);
    return new Float64Array([cl * cp * cg + sl * sp * sg, sl * cp * cg - cl * sp * sg, cl * sp * cg + sl * cp * sg, cl * cp * sg - sl * sp * cg]);
  }
  function eulerRotation(q) {
    var tmp$ = Float64Array;
    var y = 2 * (q[0] * q[1] + q[2] * q[3]);
    var x = 1 - 2 * (q[1] * q[1] + q[2] * q[2]);
    var tmp$_0 = Math_0.atan2(y, x) * math.RAD_TO_DEG;
    var tmp$_1 = -1.0;
    var b = 2 * (q[0] * q[2] - q[3] * q[1]);
    var b_0 = Math_0.min(1.0, b);
    var x_0 = Math_0.max(tmp$_1, b_0);
    var tmp$_2 = Math_0.asin(x_0) * math.RAD_TO_DEG;
    var y_0 = 2 * (q[0] * q[3] + q[1] * q[2]);
    var x_1 = 1 - 2 * (q[2] * q[2] + q[3] * q[3]);
    return new tmp$([tmp$_0, tmp$_2, Math_0.atan2(y_0, x_1) * math.RAD_TO_DEG]);
  }
  function quaternionDelta(v0, v1, alpha) {
    if (alpha === void 0)
      alpha = 1.0;
    var w = cartesianCross(v0, v1);
    var x = cartesianDot(w, w);
    var l = Math_0.sqrt(x);
    if (l === -0.0 || l === 0.0 || isNaN_0(l))
      return new Float64Array([1.0, 0.0, 0.0, 0.0]);
    var tmp$ = -1.0;
    var b = cartesianDot(v0, v1);
    var b_0 = Math_0.min(1.0, b);
    var x_0 = Math_0.max(tmp$, b_0);
    var t = alpha * Math_0.acos(x_0) / 2;
    var s = Math_0.sin(t);
    return new Float64Array([Math_0.cos(t), w[2] / l * s, -w[1] / l * s, w[0] / l * s]);
  }
  function quaternionMultiply(q0, q1) {
    return new Float64Array([q0[0] * q1[0] - q0[1] * q1[1] - q0[2] * q1[2] - q0[3] * q1[3], q0[0] * q1[1] + q0[1] * q1[0] + q0[2] * q1[3] - q0[3] * q1[2], q0[0] * q1[2] - q0[1] * q1[3] + q0[2] * q1[0] + q0[3] * q1[1], q0[0] * q1[3] + q0[1] * q1[2] - q0[2] * q1[1] + q0[3] * q1[0]]);
  }
  function AnglePreClip(angle) {
    this.angle = angle;
    this.clipCircle = new CirclePreClip(this.angle.rad);
  }
  AnglePreClip.prototype.clipStream_enk0m$ = function (stream) {
    return this.clipCircle.clipStream_enk0m$(stream);
  };
  AnglePreClip.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'AnglePreClip',
    interfaces: [StreamClip]
  };
  function get_anglePreClip($receiver) {
    var tmp$, tmp$_0;
    return (tmp$_0 = Kotlin.isType(tmp$ = $receiver.preClip, AnglePreClip) ? tmp$ : null) != null ? tmp$_0.angle : null;
  }
  function set_anglePreClip($receiver, value) {
    if (value != null) {
      $receiver.preClip = new AnglePreClip(value);
    }
     else {
      $receiver.preClip = antimeridianPreClip;
    }
  }
  function antimeridianPreClip$ObjectLiteral() {
    this.antimeridianClip = new AntimeridianClip();
  }
  antimeridianPreClip$ObjectLiteral.prototype.clipStream_enk0m$ = function (stream) {
    return new ClippableStream(this.antimeridianClip, stream);
  };
  antimeridianPreClip$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [StreamClip]
  };
  var antimeridianPreClip;
  function AntimeridianClip() {
    this.start_rw5hgt$_0 = new Float64Array([-math.PI, -math.HALFPI]);
  }
  Object.defineProperty(AntimeridianClip.prototype, 'start', {
    get: function () {
      return this.start_rw5hgt$_0;
    },
    set: function (start) {
      this.start_rw5hgt$_0 = start;
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
  AntimeridianClip.prototype.clipLine_enk0m$ = function (stream) {
    var lambda0 = {v: kotlin_js_internal_DoubleCompanionObject.NaN};
    var phi0 = {v: kotlin_js_internal_DoubleCompanionObject.NaN};
    var sign0 = {v: kotlin_js_internal_DoubleCompanionObject.NaN};
    return new AntimeridianClip$clipLine$ObjectLiteral(stream, lambda0, phi0, sign0);
  };
  AntimeridianClip.prototype.interpolate_tgyo8g$ = function (from, to, direction, stream) {
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
  function CirclePreClip(radius) {
    this.radius = radius;
  }
  CirclePreClip.prototype.clipStream_enk0m$ = function (stream) {
    return new ClippableStream(new ClipCircle(this.radius), stream);
  };
  CirclePreClip.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'CirclePreClip',
    interfaces: [StreamClip]
  };
  function ClipCircle(radius) {
    this.radius = radius;
    var x = this.radius;
    this.cosRadius_0 = Math_0.cos(x);
    this.delta_0 = toRadians(6.0);
    this.smallRadius_0 = this.cosRadius_0 > 0;
    var x_0 = this.cosRadius_0;
    this.notHemisphere_0 = Math_0.abs(x_0) > math.EPSILON;
  }
  Object.defineProperty(ClipCircle.prototype, 'start', {
    get: function () {
      return this.smallRadius_0 ? new Float64Array([0.0, -this.radius]) : new Float64Array([-math.PI, this.radius - math.PI]);
    }
  });
  ClipCircle.prototype.pointVisible_lu1900$ = function (x, y) {
    return Math_0.cos(x) * Math_0.cos(y) > this.cosRadius_0;
  };
  function ClipCircle$clipLine$ObjectLiteral(this$ClipCircle, closure$stream) {
    this.this$ClipCircle = this$ClipCircle;
    this.closure$stream = closure$stream;
    this._clean_0 = 0;
    this.point0_0 = null;
    this.c0_0 = 0;
    this.v0_0 = false;
    this.v00_0 = false;
    this.clean_tc4iz0$_0 = 0;
  }
  Object.defineProperty(ClipCircle$clipLine$ObjectLiteral.prototype, 'clean', {
    get: function () {
      return this._clean_0 | (this.v00_0 && this.v0_0 ? 1 : 0) << 1;
    },
    set: function (clean) {
      this.clean_tc4iz0$_0 = clean;
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
  ClipCircle.prototype.clipLine_enk0m$ = function (stream) {
    return new ClipCircle$clipLine$ObjectLiteral(this, stream);
  };
  ClipCircle.prototype.interpolate_tgyo8g$ = function (from, to, direction, stream) {
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
    var c1 = this.cosRadius_0 * n2n2 / determinant;
    var c2 = -this.cosRadius_0 * n1n2 / determinant;
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
    var c1 = this.cosRadius_0 * n2n2 / determinant;
    var c2 = -this.cosRadius_0 * n1n2 / determinant;
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
  function Comparator$ObjectLiteral_0(closure$comparison) {
    this.closure$comparison = closure$comparison;
  }
  Comparator$ObjectLiteral_0.prototype.compare = function (a, b) {
    return this.closure$comparison(a, b);
  };
  Comparator$ObjectLiteral_0.$metadata$ = {kind: Kind_CLASS, interfaces: [Comparator]};
  function NoClip$ObjectLiteral() {
  }
  NoClip$ObjectLiteral.prototype.clipStream_enk0m$ = function (stream) {
    return stream;
  };
  NoClip$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [StreamClip]
  };
  var NoClip;
  function StreamClip() {
  }
  StreamClip.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'StreamClip',
    interfaces: []
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
  function ClippableStream(clip, sink) {
    this.clip = clip;
    this.sink = sink;
    this.pointContext = ClippableStream$PointContext$DEFAULT_getInstance();
    this.lineStartContext = ClippableStream$LineStartContext$DEFAULT_getInstance();
    this.lineEndContext = ClippableStream$LineEndContext$DEFAULT_getInstance();
    this.line_8be2vx$ = this.clip.clipLine_enk0m$(this.sink);
    this.ringBuffer_8be2vx$ = new ClipBufferStream();
    this.ringSink_8be2vx$ = this.clip.clipLine_enk0m$(this.ringBuffer_8be2vx$);
    this.polygonStarted_8be2vx$ = false;
    this.segments_8be2vx$ = ArrayList_init_0();
    this.polygon_8be2vx$ = ArrayList_init_0();
    this.ring_8be2vx$ = null;
    this.currentPoint_0 = ClippableStream$DefaultPointFunction_getInstance();
    this.currentLineStart_0 = ClippableStream$DefaultLineStartFunction_getInstance();
    this.currentLineEnd_0 = ClippableStream$DefaultLineEndFunction_getInstance();
    this.compareIntersection_0 = new Comparator$ObjectLiteral_0(ClippableStream$compareIntersection$lambda);
    this.interpolateFunction = new ClippableStream$interpolateFunction$ObjectLiteral(this);
  }
  function ClippableStream$LineStartContext(name, ordinal) {
    Enum.call(this);
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function ClippableStream$LineStartContext_initFields() {
    ClippableStream$LineStartContext_initFields = function () {
    };
    ClippableStream$LineStartContext$DEFAULT_instance = new ClippableStream$LineStartContext('DEFAULT', 0);
    ClippableStream$LineStartContext$POLYGON_instance = new ClippableStream$LineStartContext('POLYGON', 1);
  }
  var ClippableStream$LineStartContext$DEFAULT_instance;
  function ClippableStream$LineStartContext$DEFAULT_getInstance() {
    ClippableStream$LineStartContext_initFields();
    return ClippableStream$LineStartContext$DEFAULT_instance;
  }
  var ClippableStream$LineStartContext$POLYGON_instance;
  function ClippableStream$LineStartContext$POLYGON_getInstance() {
    ClippableStream$LineStartContext_initFields();
    return ClippableStream$LineStartContext$POLYGON_instance;
  }
  ClippableStream$LineStartContext.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'LineStartContext',
    interfaces: [Enum]
  };
  function ClippableStream$LineStartContext$values() {
    return [ClippableStream$LineStartContext$DEFAULT_getInstance(), ClippableStream$LineStartContext$POLYGON_getInstance()];
  }
  ClippableStream$LineStartContext.values = ClippableStream$LineStartContext$values;
  function ClippableStream$LineStartContext$valueOf(name) {
    switch (name) {
      case 'DEFAULT':
        return ClippableStream$LineStartContext$DEFAULT_getInstance();
      case 'POLYGON':
        return ClippableStream$LineStartContext$POLYGON_getInstance();
      default:throwISE('No enum constant io.data2viz.geo.geometry.clip.ClippableStream.LineStartContext.' + name);
    }
  }
  ClippableStream$LineStartContext.valueOf_61zpoe$ = ClippableStream$LineStartContext$valueOf;
  function ClippableStream$LineEndContext(name, ordinal) {
    Enum.call(this);
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function ClippableStream$LineEndContext_initFields() {
    ClippableStream$LineEndContext_initFields = function () {
    };
    ClippableStream$LineEndContext$DEFAULT_instance = new ClippableStream$LineEndContext('DEFAULT', 0);
    ClippableStream$LineEndContext$POLYGON_instance = new ClippableStream$LineEndContext('POLYGON', 1);
  }
  var ClippableStream$LineEndContext$DEFAULT_instance;
  function ClippableStream$LineEndContext$DEFAULT_getInstance() {
    ClippableStream$LineEndContext_initFields();
    return ClippableStream$LineEndContext$DEFAULT_instance;
  }
  var ClippableStream$LineEndContext$POLYGON_instance;
  function ClippableStream$LineEndContext$POLYGON_getInstance() {
    ClippableStream$LineEndContext_initFields();
    return ClippableStream$LineEndContext$POLYGON_instance;
  }
  ClippableStream$LineEndContext.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'LineEndContext',
    interfaces: [Enum]
  };
  function ClippableStream$LineEndContext$values() {
    return [ClippableStream$LineEndContext$DEFAULT_getInstance(), ClippableStream$LineEndContext$POLYGON_getInstance()];
  }
  ClippableStream$LineEndContext.values = ClippableStream$LineEndContext$values;
  function ClippableStream$LineEndContext$valueOf(name) {
    switch (name) {
      case 'DEFAULT':
        return ClippableStream$LineEndContext$DEFAULT_getInstance();
      case 'POLYGON':
        return ClippableStream$LineEndContext$POLYGON_getInstance();
      default:throwISE('No enum constant io.data2viz.geo.geometry.clip.ClippableStream.LineEndContext.' + name);
    }
  }
  ClippableStream$LineEndContext.valueOf_61zpoe$ = ClippableStream$LineEndContext$valueOf;
  function ClippableStream$PointContext(name, ordinal) {
    Enum.call(this);
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function ClippableStream$PointContext_initFields() {
    ClippableStream$PointContext_initFields = function () {
    };
    ClippableStream$PointContext$DEFAULT_instance = new ClippableStream$PointContext('DEFAULT', 0);
    ClippableStream$PointContext$POLYGON_instance = new ClippableStream$PointContext('POLYGON', 1);
    ClippableStream$PointContext$LINE_instance = new ClippableStream$PointContext('LINE', 2);
  }
  var ClippableStream$PointContext$DEFAULT_instance;
  function ClippableStream$PointContext$DEFAULT_getInstance() {
    ClippableStream$PointContext_initFields();
    return ClippableStream$PointContext$DEFAULT_instance;
  }
  var ClippableStream$PointContext$POLYGON_instance;
  function ClippableStream$PointContext$POLYGON_getInstance() {
    ClippableStream$PointContext_initFields();
    return ClippableStream$PointContext$POLYGON_instance;
  }
  var ClippableStream$PointContext$LINE_instance;
  function ClippableStream$PointContext$LINE_getInstance() {
    ClippableStream$PointContext_initFields();
    return ClippableStream$PointContext$LINE_instance;
  }
  ClippableStream$PointContext.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'PointContext',
    interfaces: [Enum]
  };
  function ClippableStream$PointContext$values() {
    return [ClippableStream$PointContext$DEFAULT_getInstance(), ClippableStream$PointContext$POLYGON_getInstance(), ClippableStream$PointContext$LINE_getInstance()];
  }
  ClippableStream$PointContext.values = ClippableStream$PointContext$values;
  function ClippableStream$PointContext$valueOf(name) {
    switch (name) {
      case 'DEFAULT':
        return ClippableStream$PointContext$DEFAULT_getInstance();
      case 'POLYGON':
        return ClippableStream$PointContext$POLYGON_getInstance();
      case 'LINE':
        return ClippableStream$PointContext$LINE_getInstance();
      default:throwISE('No enum constant io.data2viz.geo.geometry.clip.ClippableStream.PointContext.' + name);
    }
  }
  ClippableStream$PointContext.valueOf_61zpoe$ = ClippableStream$PointContext$valueOf;
  ClippableStream.prototype.point_yvo9jy$ = function (x, y, z) {
    this.currentPoint_0.point_6mq2ae$(this, x, y, z);
  };
  ClippableStream.prototype.lineStart = function () {
    this.currentLineStart_0.lineStart_i9ka2c$(this);
  };
  ClippableStream.prototype.lineEnd = function () {
    this.currentLineEnd_0.lineEnd_i9ka2c$(this);
  };
  ClippableStream.prototype.polygonStart = function () {
    this.currentPoint_0 = ClippableStream$PointRingPointFunction_getInstance();
    this.currentLineStart_0 = ClippableStream$RingLineStartFunction_getInstance();
    this.currentLineEnd_0 = ClippableStream$RingLineEndFunction_getInstance();
  };
  ClippableStream.prototype.polygonEnd = function () {
    this.currentPoint_0 = ClippableStream$DefaultPointFunction_getInstance();
    this.currentLineStart_0 = ClippableStream$DefaultLineStartFunction_getInstance();
    this.currentLineEnd_0 = ClippableStream$DefaultLineEndFunction_getInstance();
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
      this.clip.interpolate_tgyo8g$(null, null, 1, this.sink);
      this.sink.lineEnd();
    }
    if (this.polygonStarted_8be2vx$) {
      this.sink.polygonEnd();
      this.polygonStarted_8be2vx$ = false;
    }
    this.segments_8be2vx$.clear();
    this.polygon_8be2vx$.clear();
  };
  ClippableStream.prototype.sphere = function () {
    this.sink.polygonStart();
    this.sink.lineStart();
    this.clip.interpolate_tgyo8g$(null, null, 1, this.sink);
    this.sink.lineEnd();
    this.sink.polygonEnd();
  };
  function ClippableStream$PointFunction() {
  }
  ClippableStream$PointFunction.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'PointFunction',
    interfaces: []
  };
  function ClippableStream$LineStartFunction() {
  }
  ClippableStream$LineStartFunction.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'LineStartFunction',
    interfaces: []
  };
  function ClippableStream$LineEndFunction() {
  }
  ClippableStream$LineEndFunction.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'LineEndFunction',
    interfaces: []
  };
  function ClippableStream$DefaultPointFunction() {
    ClippableStream$DefaultPointFunction_instance = this;
  }
  ClippableStream$DefaultPointFunction.prototype.point_6mq2ae$ = function (clip, x, y, z) {
    if (clip.clip.pointVisible_lu1900$(x, y))
      clip.sink.point_yvo9jy$(x, y, z);
  };
  ClippableStream$DefaultPointFunction.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'DefaultPointFunction',
    interfaces: [ClippableStream$PointFunction]
  };
  var ClippableStream$DefaultPointFunction_instance = null;
  function ClippableStream$DefaultPointFunction_getInstance() {
    if (ClippableStream$DefaultPointFunction_instance === null) {
      new ClippableStream$DefaultPointFunction();
    }
    return ClippableStream$DefaultPointFunction_instance;
  }
  function ClippableStream$RingPointFunction() {
    ClippableStream$RingPointFunction_instance = this;
  }
  ClippableStream$RingPointFunction.prototype.point_6mq2ae$ = function (clip, x, y, z) {
    ensureNotNull(clip.ring_8be2vx$).add_11rb$(new Float64Array([x, y]));
    clip.ringSink_8be2vx$.point_yvo9jy$(x, y, z);
  };
  ClippableStream$RingPointFunction.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'RingPointFunction',
    interfaces: [ClippableStream$PointFunction]
  };
  var ClippableStream$RingPointFunction_instance = null;
  function ClippableStream$RingPointFunction_getInstance() {
    if (ClippableStream$RingPointFunction_instance === null) {
      new ClippableStream$RingPointFunction();
    }
    return ClippableStream$RingPointFunction_instance;
  }
  function ClippableStream$DefaultLineStartFunction() {
    ClippableStream$DefaultLineStartFunction_instance = this;
  }
  ClippableStream$DefaultLineStartFunction.prototype.lineStart_i9ka2c$ = function (clip) {
    clip.currentPoint_0 = ClippableStream$LinePointFunction_getInstance();
    clip.line_8be2vx$.lineStart();
  };
  ClippableStream$DefaultLineStartFunction.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'DefaultLineStartFunction',
    interfaces: [ClippableStream$LineStartFunction]
  };
  var ClippableStream$DefaultLineStartFunction_instance = null;
  function ClippableStream$DefaultLineStartFunction_getInstance() {
    if (ClippableStream$DefaultLineStartFunction_instance === null) {
      new ClippableStream$DefaultLineStartFunction();
    }
    return ClippableStream$DefaultLineStartFunction_instance;
  }
  function ClippableStream$DefaultLineEndFunction() {
    ClippableStream$DefaultLineEndFunction_instance = this;
  }
  ClippableStream$DefaultLineEndFunction.prototype.lineEnd_i9ka2c$ = function (clip) {
    clip.currentPoint_0 = ClippableStream$DefaultPointFunction_getInstance();
    clip.line_8be2vx$.lineEnd();
  };
  ClippableStream$DefaultLineEndFunction.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'DefaultLineEndFunction',
    interfaces: [ClippableStream$LineEndFunction]
  };
  var ClippableStream$DefaultLineEndFunction_instance = null;
  function ClippableStream$DefaultLineEndFunction_getInstance() {
    if (ClippableStream$DefaultLineEndFunction_instance === null) {
      new ClippableStream$DefaultLineEndFunction();
    }
    return ClippableStream$DefaultLineEndFunction_instance;
  }
  function ClippableStream$RingLineStartFunction() {
    ClippableStream$RingLineStartFunction_instance = this;
  }
  ClippableStream$RingLineStartFunction.prototype.lineStart_i9ka2c$ = function (clip) {
    clip.ringSink_8be2vx$.lineStart();
    clip.ring_8be2vx$ = ArrayList_init_0();
  };
  ClippableStream$RingLineStartFunction.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'RingLineStartFunction',
    interfaces: [ClippableStream$LineStartFunction]
  };
  var ClippableStream$RingLineStartFunction_instance = null;
  function ClippableStream$RingLineStartFunction_getInstance() {
    if (ClippableStream$RingLineStartFunction_instance === null) {
      new ClippableStream$RingLineStartFunction();
    }
    return ClippableStream$RingLineStartFunction_instance;
  }
  function ClippableStream$RingLineEndFunction() {
    ClippableStream$RingLineEndFunction_instance = this;
  }
  ClippableStream$RingLineEndFunction.prototype.lineEnd_i9ka2c$ = function (clip) {
    if (clip.ring_8be2vx$ == null) {
      var message = "Error on ClippableStream.ringEnd, ring can't be null.";
      throw IllegalArgumentException_init(message.toString());
    }
    var ringList = ensureNotNull(clip.ring_8be2vx$);
    ClippableStream$RingPointFunction_getInstance().point_6mq2ae$(clip, ringList.get_za3lpa$(0)[0], ringList.get_za3lpa$(0)[1], 0.0);
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
    var destination = ArrayList_init_0();
    var tmp$_1;
    tmp$_1 = ringSegments.iterator();
    while (tmp$_1.hasNext()) {
      var element_0 = tmp$_1.next();
      if (element_0.size > 1)
        destination.add_11rb$(element_0);
    }
    tmp$_0.add_11rb$(destination);
  };
  ClippableStream$RingLineEndFunction.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'RingLineEndFunction',
    interfaces: [ClippableStream$LineEndFunction]
  };
  var ClippableStream$RingLineEndFunction_instance = null;
  function ClippableStream$RingLineEndFunction_getInstance() {
    if (ClippableStream$RingLineEndFunction_instance === null) {
      new ClippableStream$RingLineEndFunction();
    }
    return ClippableStream$RingLineEndFunction_instance;
  }
  function ClippableStream$LinePointFunction() {
    ClippableStream$LinePointFunction_instance = this;
  }
  ClippableStream$LinePointFunction.prototype.point_6mq2ae$ = function (clip, x, y, z) {
    clip.line_8be2vx$.point_yvo9jy$(x, y, z);
  };
  ClippableStream$LinePointFunction.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'LinePointFunction',
    interfaces: [ClippableStream$PointFunction]
  };
  var ClippableStream$LinePointFunction_instance = null;
  function ClippableStream$LinePointFunction_getInstance() {
    if (ClippableStream$LinePointFunction_instance === null) {
      new ClippableStream$LinePointFunction();
    }
    return ClippableStream$LinePointFunction_instance;
  }
  function ClippableStream$PointRingPointFunction() {
    ClippableStream$PointRingPointFunction_instance = this;
  }
  ClippableStream$PointRingPointFunction.prototype.point_6mq2ae$ = function (clip, x, y, z) {
    ensureNotNull(clip.ring_8be2vx$).add_11rb$(new Float64Array([x, y]));
    clip.ringSink_8be2vx$.point_yvo9jy$(x, y, z);
  };
  ClippableStream$PointRingPointFunction.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'PointRingPointFunction',
    interfaces: [ClippableStream$PointFunction]
  };
  var ClippableStream$PointRingPointFunction_instance = null;
  function ClippableStream$PointRingPointFunction_getInstance() {
    if (ClippableStream$PointRingPointFunction_instance === null) {
      new ClippableStream$PointRingPointFunction();
    }
    return ClippableStream$PointRingPointFunction_instance;
  }
  function ClippableStream$compareIntersection$lambda(i1, i2) {
    var a = i1.point;
    var b = i2.point;
    var ca = a[0] < 0 ? a[1] - math.HALFPI - math.EPSILON : math.HALFPI - a[1];
    var cb = b[0] < 0 ? b[1] - math.HALFPI - math.EPSILON : math.HALFPI - b[1];
    return Kotlin.compareTo(ca, cb);
  }
  function ClippableStream$interpolateFunction$ObjectLiteral(this$ClippableStream) {
    this.this$ClippableStream = this$ClippableStream;
  }
  ClippableStream$interpolateFunction$ObjectLiteral.prototype.invoke_opcmf4$ = function (from, to, direction, stream) {
    this.this$ClippableStream.clip.interpolate_tgyo8g$(from, to, direction, stream);
  };
  ClippableStream$interpolateFunction$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [InterpolateFunction]
  };
  ClippableStream.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ClippableStream',
    interfaces: [Stream]
  };
  function ClipBufferStream() {
    this.lines_0 = ArrayList_init_0();
    this.line_mf6xo2$_0 = this.line_mf6xo2$_0;
  }
  Object.defineProperty(ClipBufferStream.prototype, 'line_0', {
    get: function () {
      if (this.line_mf6xo2$_0 == null)
        return throwUPAE('line');
      return this.line_mf6xo2$_0;
    },
    set: function (line) {
      this.line_mf6xo2$_0 = line;
    }
  });
  ClipBufferStream.prototype.point_yvo9jy$ = function (x, y, z) {
    this.line_0.add_11rb$(new Float64Array([x, y]));
  };
  ClipBufferStream.prototype.lineStart = function () {
    this.line_0 = ArrayList_init_0();
    this.lines_0.add_11rb$(this.line_0);
  };
  ClipBufferStream.prototype.rejoin = function () {
    if (this.lines_0.size > 1) {
      var l = ArrayList_init_0();
      l.add_11rb$(this.lines_0.removeAt_za3lpa$(get_lastIndex(this.lines_0)));
      l.add_11rb$(this.lines_0.removeAt_za3lpa$(0));
      this.lines_0.addAll_brywnq$(l);
    }
  };
  ClipBufferStream.prototype.result = function () {
    var oldLines = this.lines_0;
    this.lines_0 = ArrayList_init_0();
    return oldLines;
  };
  ClipBufferStream.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ClipBufferStream',
    interfaces: [Stream]
  };
  function ExtentClip(extent) {
    this.extent = extent;
    this.clipRectangle = new ClipRectangle(this.extent);
  }
  ExtentClip.prototype.clipStream_enk0m$ = function (stream) {
    return this.clipRectangle.clipLine_enk0m$(stream);
  };
  ExtentClip.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ExtentClip',
    interfaces: [StreamClip]
  };
  function get_extentPostClip($receiver) {
    var tmp$, tmp$_0;
    return (tmp$_0 = Kotlin.isType(tmp$ = $receiver.postClip, ExtentClip) ? tmp$ : null) != null ? tmp$_0.extent : null;
  }
  function set_extentPostClip($receiver, value) {
    if (value != null) {
      $receiver.postClip = new ExtentClip(value);
    }
     else {
      $receiver.postClip = NoClip;
    }
  }
  function Comparator$ObjectLiteral_1(closure$comparison) {
    this.closure$comparison = closure$comparison;
  }
  Comparator$ObjectLiteral_1.prototype.compare = function (a, b) {
    return this.closure$comparison(a, b);
  };
  Comparator$ObjectLiteral_1.$metadata$ = {kind: Kind_CLASS, interfaces: [Comparator]};
  var CLIPMAX;
  var CLIPMIN;
  function RectangleClip(x0, y0, x1, y1) {
    this.clipRectangle = new ClipRectangle(new Extent(x0, y0, x1, y1));
  }
  RectangleClip.prototype.clipStream_enk0m$ = function (stream) {
    return this.clipRectangle.clipLine_enk0m$(stream);
  };
  RectangleClip.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'RectangleClip',
    interfaces: [StreamClip]
  };
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
    this.clean_3hsv1b$_0 = 0;
    this.activeStream_0 = closure$stream;
    this.bufferStream_0 = new ClipBufferStream();
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
      return this.clean_3hsv1b$_0;
    },
    set: function (clean) {
      this.clean_3hsv1b$_0 = clean;
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
      var r = ArrayList_init_0();
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
    this.segments_0 = ArrayList_init_0();
    this.polygon_0 = ArrayList_init_0();
    this.clean = 1;
  };
  function ClipRectangle$clipLine$ObjectLiteral$polygonEnd$lambda(this$ClipRectangle) {
    return function (o1, o2) {
      return this$ClipRectangle.comparePoint_0(o1.point, o2.point);
    };
  }
  ClipRectangle$clipLine$ObjectLiteral.prototype.polygonEnd = function () {
    var tmp$, tmp$_0;
    var startInside = this.polygonInside_0() !== 0;
    var cleanInside = this.clean !== 0 && startInside;
    var visible = (tmp$_0 = (tmp$ = this.segments_0) != null ? !tmp$.isEmpty() : null) != null ? tmp$_0 : false;
    if (cleanInside || visible) {
      this.closure$stream.polygonStart();
      if (cleanInside) {
        this.closure$stream.lineStart();
        this.this$ClipRectangle.interpolate_tgyo8g$(null, null, 1, this.closure$stream);
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
    var visible = this.this$ClipRectangle.pointVisible_lu1900$(newX, newY);
    if (this.polygon_0 != null)
      (tmp$ = this.ring_0) != null ? tmp$.add_11rb$(new Float64Array([newX, newY])) : null;
    if (this.first_0) {
      this.x___0 = newX;
      this.y___0 = newY;
      this.v___0 = visible;
      this.first_0 = false;
      if (visible) {
        this.activeStream_0.lineStart();
        this.activeStream_0.point_yvo9jy$(newX, newY, 0.0);
      }
    }
     else {
      if (visible && this.v__0)
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
          if (!visible)
            this.activeStream_0.lineEnd();
          this.clean = 0;
        }
         else if (visible) {
          this.activeStream_0.lineStart();
          this.activeStream_0.point_yvo9jy$(newX, newY, 0.0);
          this.clean = 0;
        }
      }
    }
    this.x__0 = newX;
    this.y__0 = newY;
    this.v__0 = visible;
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
  ClipRectangle.prototype.clipLine_enk0m$ = function (stream) {
    return new ClipRectangle$clipLine$ObjectLiteral(stream, this);
  };
  ClipRectangle.prototype.interpolate_tgyo8g$ = function (from, to, direction, stream) {
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
  ClipRectangle$interpolateFunction$ObjectLiteral.prototype.invoke_opcmf4$ = function (from, to, direction, stream) {
    this.this$ClipRectangle.interpolate_tgyo8g$(from, to, direction, stream);
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
  Intersection.prototype.copy_s7274m$ = function (point, points, other, entry, visited, next, previous) {
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
    var subject = ArrayList_init_0();
    var clip = ArrayList_init_0();
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
            interpolateFunction.invoke_opcmf4$(current.point, ensureNotNull(current.next).point, 1, stream);
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
            interpolateFunction.invoke_opcmf4$(current.point, ensureNotNull(current.previous).point, -1, stream);
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
  function AreaStream() {
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
  AreaStream.prototype.result = function () {
    var a = this.areaSum_0 / 2.0;
    this.areaSum_0 = 0.0;
    return a;
  };
  AreaStream.prototype.point_yvo9jy$ = function (x, y, z) {
    this.currentPoint_0(x, y);
  };
  AreaStream.prototype.lineStart = function () {
    this.currentLineStart_0();
  };
  AreaStream.prototype.lineEnd = function () {
    this.currentLineEnd_0();
  };
  AreaStream.prototype.polygonStart = function () {
    this.currentLineStart_0 = getCallableRef('areaRingStart', function ($receiver) {
      return $receiver.areaRingStart_0(), Unit;
    }.bind(null, this));
    this.currentLineEnd_0 = getCallableRef('areaRingEnd', function ($receiver) {
      return $receiver.areaRingEnd_0(), Unit;
    }.bind(null, this));
  };
  AreaStream.prototype.polygonEnd = function () {
    this.currentLineStart_0 = noop;
    this.currentLineEnd_0 = noop;
    this.currentPoint_0 = noop2;
    var x = this.areaRingSum_0;
    this.areaSum_0 += Math_0.abs(x);
    this.areaRingSum_0 = 0.0;
  };
  AreaStream.prototype.areaRingStart_0 = function () {
    this.currentPoint_0 = getCallableRef('areaPointFirst', function ($receiver, x, y) {
      return $receiver.areaPointFirst_0(x, y), Unit;
    }.bind(null, this));
  };
  AreaStream.prototype.areaPointFirst_0 = function (x, y) {
    this.currentPoint_0 = getCallableRef('areaPoint', function ($receiver, x, y) {
      return $receiver.areaPoint_0(x, y), Unit;
    }.bind(null, this));
    this.x00_0 = x;
    this.x0_0 = x;
    this.y00_0 = y;
    this.y0_0 = y;
  };
  AreaStream.prototype.areaPoint_0 = function (x, y) {
    this.areaRingSum_0 += this.y0_0 * x - this.x0_0 * y;
    this.x0_0 = x;
    this.y0_0 = y;
  };
  AreaStream.prototype.areaRingEnd_0 = function () {
    this.areaPoint_0(this.x00_0, this.y00_0);
  };
  AreaStream.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'AreaStream',
    interfaces: [Stream]
  };
  function BoundsStream() {
    this.bounds_0 = new Extent(kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY, kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY, kotlin_js_internal_DoubleCompanionObject.NEGATIVE_INFINITY, kotlin_js_internal_DoubleCompanionObject.NEGATIVE_INFINITY);
  }
  BoundsStream.prototype.result = function () {
    var result = this.bounds_0.copy();
    this.bounds_0 = new Extent(kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY, kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY, kotlin_js_internal_DoubleCompanionObject.NEGATIVE_INFINITY, kotlin_js_internal_DoubleCompanionObject.NEGATIVE_INFINITY);
    return result;
  };
  BoundsStream.prototype.point_yvo9jy$ = function (x, y, z) {
    if (x < this.bounds_0.x0)
      this.bounds_0.x0 = x;
    if (x > this.bounds_0.x1)
      this.bounds_0.x1 = x;
    if (y < this.bounds_0.y0)
      this.bounds_0.y0 = y;
    if (y > this.bounds_0.y1)
      this.bounds_0.y1 = y;
  };
  BoundsStream.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'BoundsStream',
    interfaces: [Stream]
  };
  function CentroidStream() {
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
  CentroidStream.prototype.result_8be2vx$ = function () {
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
  CentroidStream.prototype.point_yvo9jy$ = function (x, y, z) {
    this.currentPoint_0(x, y);
  };
  CentroidStream.prototype.lineStart = function () {
    this.currentLineStart_0();
  };
  CentroidStream.prototype.lineEnd = function () {
    this.currentLineEnd_0();
  };
  CentroidStream.prototype.polygonStart = function () {
    this.currentLineStart_0 = getCallableRef('centroidRingStart', function ($receiver) {
      return $receiver.centroidRingStart_0(), Unit;
    }.bind(null, this));
    this.currentLineEnd_0 = getCallableRef('centroidRingEnd', function ($receiver) {
      return $receiver.centroidRingEnd_0(), Unit;
    }.bind(null, this));
  };
  CentroidStream.prototype.polygonEnd = function () {
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
  CentroidStream.prototype.centroidPoint_0 = function (x, y) {
    this._X0_0 += x;
    this._Y0_0 += y;
    this._Z0_0 = this._Z0_0 + 1;
  };
  CentroidStream.prototype.centroidLineStart_0 = function () {
    this.currentPoint_0 = getCallableRef('centroidPointFirstLine', function ($receiver, x, y) {
      return $receiver.centroidPointFirstLine_0(x, y), Unit;
    }.bind(null, this));
  };
  CentroidStream.prototype.centroidPointFirstLine_0 = function (x, y) {
    this.currentPoint_0 = getCallableRef('centroidPointLine', function ($receiver, x, y) {
      return $receiver.centroidPointLine_0(x, y), Unit;
    }.bind(null, this));
    this.x0_0 = x;
    this.y0_0 = y;
    this.centroidPoint_0(x, y);
  };
  CentroidStream.prototype.centroidPointLine_0 = function (x, y) {
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
  CentroidStream.prototype.centroidLineEnd_0 = function () {
    this.currentPoint_0 = getCallableRef('centroidPoint', function ($receiver, x, y) {
      return $receiver.centroidPoint_0(x, y), Unit;
    }.bind(null, this));
  };
  CentroidStream.prototype.centroidRingStart_0 = function () {
    this.currentPoint_0 = getCallableRef('centroidPointFirstRing', function ($receiver, x, y) {
      return $receiver.centroidPointFirstRing_0(x, y), Unit;
    }.bind(null, this));
  };
  CentroidStream.prototype.centroidRingEnd_0 = function () {
    this.centroidPointRing_0(this.x00_0, this.y00_0);
  };
  CentroidStream.prototype.centroidPointFirstRing_0 = function (x, y) {
    this.currentPoint_0 = getCallableRef('centroidPointRing', function ($receiver, x, y) {
      return $receiver.centroidPointRing_0(x, y), Unit;
    }.bind(null, this));
    this.x00_0 = x;
    this.y00_0 = y;
    this.x0_0 = x;
    this.y0_0 = y;
    this.centroidPoint_0(x, y);
  };
  CentroidStream.prototype.centroidPointRing_0 = function (x, y) {
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
  CentroidStream.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'CentroidStream',
    interfaces: [Stream]
  };
  function MeasureStream() {
    this.lengthSum_0 = 0.0;
    this.lengthRing_0 = false;
    this.x00_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.y00_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.x0_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.y0_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.currentPoint_0 = noop2;
  }
  MeasureStream.prototype.result = function () {
    var result = this.lengthSum_0;
    this.lengthSum_0 = 0.0;
    return result;
  };
  MeasureStream.prototype.point_yvo9jy$ = function (x, y, z) {
    this.currentPoint_0(x, y);
  };
  MeasureStream.prototype.lineStart = function () {
    this.currentPoint_0 = getCallableRef('lengthPointFirst', function ($receiver, x, y) {
      return $receiver.lengthPointFirst_0(x, y), Unit;
    }.bind(null, this));
  };
  MeasureStream.prototype.lineEnd = function () {
    if (this.lengthRing_0)
      this.lengthPoint_0(this.x00_0, this.y00_0);
    this.currentPoint_0 = noop2;
  };
  MeasureStream.prototype.polygonStart = function () {
    this.lengthRing_0 = true;
  };
  MeasureStream.prototype.polygonEnd = function () {
    this.lengthRing_0 = false;
  };
  MeasureStream.prototype.lengthPointFirst_0 = function (x, y) {
    this.currentPoint_0 = getCallableRef('lengthPoint', function ($receiver, x, y) {
      return $receiver.lengthPoint_0(x, y), Unit;
    }.bind(null, this));
    this.x0_0 = x;
    this.x00_0 = x;
    this.y0_0 = y;
    this.y00_0 = y;
  };
  MeasureStream.prototype.lengthPoint_0 = function (x, y) {
    this.x0_0 -= x;
    this.y0_0 -= y;
    var x_0 = this.x0_0 * this.x0_0 + this.y0_0 * this.y0_0;
    this.lengthSum_0 += Math_0.sqrt(x_0);
    this.x0_0 = x;
    this.y0_0 = y;
  };
  MeasureStream.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'MeasureStream',
    interfaces: [Stream]
  };
  function PathStream(path) {
    this.path_0 = path;
    this.pointRadius = 4.5;
    this.line_0 = false;
    this.point_0 = PathStream$PathCmd$POINT_getInstance();
  }
  function PathStream$PathCmd(name, ordinal) {
    Enum.call(this);
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function PathStream$PathCmd_initFields() {
    PathStream$PathCmd_initFields = function () {
    };
    PathStream$PathCmd$MOVE_instance = new PathStream$PathCmd('MOVE', 0);
    PathStream$PathCmd$LINE_instance = new PathStream$PathCmd('LINE', 1);
    PathStream$PathCmd$POINT_instance = new PathStream$PathCmd('POINT', 2);
  }
  var PathStream$PathCmd$MOVE_instance;
  function PathStream$PathCmd$MOVE_getInstance() {
    PathStream$PathCmd_initFields();
    return PathStream$PathCmd$MOVE_instance;
  }
  var PathStream$PathCmd$LINE_instance;
  function PathStream$PathCmd$LINE_getInstance() {
    PathStream$PathCmd_initFields();
    return PathStream$PathCmd$LINE_instance;
  }
  var PathStream$PathCmd$POINT_instance;
  function PathStream$PathCmd$POINT_getInstance() {
    PathStream$PathCmd_initFields();
    return PathStream$PathCmd$POINT_instance;
  }
  PathStream$PathCmd.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'PathCmd',
    interfaces: [Enum]
  };
  function PathStream$PathCmd$values() {
    return [PathStream$PathCmd$MOVE_getInstance(), PathStream$PathCmd$LINE_getInstance(), PathStream$PathCmd$POINT_getInstance()];
  }
  PathStream$PathCmd.values = PathStream$PathCmd$values;
  function PathStream$PathCmd$valueOf(name) {
    switch (name) {
      case 'MOVE':
        return PathStream$PathCmd$MOVE_getInstance();
      case 'LINE':
        return PathStream$PathCmd$LINE_getInstance();
      case 'POINT':
        return PathStream$PathCmd$POINT_getInstance();
      default:throwISE('No enum constant io.data2viz.geo.geometry.path.PathStream.PathCmd.' + name);
    }
  }
  PathStream$PathCmd.valueOf_61zpoe$ = PathStream$PathCmd$valueOf;
  PathStream.prototype.polygonStart = function () {
    this.line_0 = true;
  };
  PathStream.prototype.polygonEnd = function () {
    this.line_0 = false;
  };
  PathStream.prototype.lineStart = function () {
    this.point_0 = PathStream$PathCmd$MOVE_getInstance();
  };
  PathStream.prototype.lineEnd = function () {
    if (this.line_0)
      this.path_0.closePath();
    this.point_0 = PathStream$PathCmd$POINT_getInstance();
  };
  PathStream.prototype.point_yvo9jy$ = function (x, y, z) {
    switch (this.point_0.name) {
      case 'MOVE':
        this.path_0.moveTo_lu1900$(x, y);
        this.point_0 = PathStream$PathCmd$LINE_getInstance();
        break;
      case 'LINE':
        this.path_0.lineTo_lu1900$(x, y);
        break;
      case 'POINT':
        this.path_0.moveTo_lu1900$(x + this.pointRadius, y);
        this.path_0.arc_6p3vsx$(x, y, this.pointRadius, 0.0, math.TAU, false);
        break;
    }
  };
  PathStream.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'PathStream',
    interfaces: [Stream]
  };
  function albersProjection$lambda($receiver) {
    return Unit;
  }
  function albersProjection$lambda_0(closure$init) {
    return function ($receiver) {
      $receiver.parallels_thqdec$(get_deg(29.5), get_deg(45.5));
      $receiver.scale = 1070.0;
      $receiver.translate_lu1900$(480.0, 250.0);
      $receiver.rotate_u9a0y3$(get_deg(96.0), get_deg(0.0));
      $receiver.center_thqdec$(get_deg(-0.6), get_deg(38.7));
      closure$init($receiver);
      return Unit;
    };
  }
  function albersProjection(init) {
    if (init === void 0)
      init = albersProjection$lambda;
    return conicEqualAreaProjection(albersProjection$lambda_0(init));
  }
  function albersUSAProjection$lambda($receiver) {
    return Unit;
  }
  function albersUSAProjection(init) {
    if (init === void 0)
      init = albersUSAProjection$lambda;
    var $receiver = new AlbersUSAProjection();
    $receiver.scale = 1070.0;
    init($receiver);
    return $receiver;
  }
  function AlbersUSAProjection() {
    ComposedProjection.call(this);
    this.lower48_0 = albersProjection();
    this.alaska_0 = conicEqualAreaProjection(AlbersUSAProjection$alaska$lambda);
    this.hawaii_0 = conicEqualAreaProjection(AlbersUSAProjection$hawaii$lambda);
    this.allProjections_nltwi7$_0 = listOf([this.lower48_0, this.alaska_0, this.hawaii_0]);
    this.customTranslateX_0 = 0.0;
    this.customTranslateY_0 = 0.0;
  }
  Object.defineProperty(AlbersUSAProjection.prototype, 'mainProjection', {
    get: function () {
      return this.lower48_0;
    }
  });
  Object.defineProperty(AlbersUSAProjection.prototype, 'allProjections', {
    get: function () {
      return this.allProjections_nltwi7$_0;
    }
  });
  Object.defineProperty(AlbersUSAProjection.prototype, 'scale', {
    get: function () {
      return this.lower48_0.scale;
    },
    set: function (value) {
      this.lower48_0.scale = value;
      this.alaska_0.scale = value * 0.35;
      this.hawaii_0.scale = value;
    }
  });
  Object.defineProperty(AlbersUSAProjection.prototype, 'precision', {
    get: function () {
      return this.lower48_0.precision;
    },
    set: function (value) {
      this.lower48_0.precision = value;
      this.alaska_0.precision = value * 0.35;
      this.hawaii_0.precision = value;
    }
  });
  Object.defineProperty(AlbersUSAProjection.prototype, 'translateX', {
    get: function () {
      return Kotlin.callGetter(this, ComposedProjection.prototype, 'translateX');
    },
    set: function (value) {
      Kotlin.callSetter(this, ComposedProjection.prototype, 'translateX', value);
      this.customTranslateX_0 += value;
      this.translateNestedProjections_0();
    }
  });
  Object.defineProperty(AlbersUSAProjection.prototype, 'translateY', {
    get: function () {
      return Kotlin.callGetter(this, ComposedProjection.prototype, 'translateY');
    },
    set: function (value) {
      Kotlin.callSetter(this, ComposedProjection.prototype, 'translateY', value);
      this.customTranslateY_0 += value;
      this.translateNestedProjections_0();
    }
  });
  AlbersUSAProjection.prototype.translateNestedProjections_0 = function () {
    var k = this.lower48_0.scale;
    var x = this.customTranslateX_0;
    var y = this.customTranslateY_0;
    this.lower48_0.translate_lu1900$(x, y);
    set_extentPostClip(this.lower48_0, new Extent(x - 0.455 * k, y - 0.238 * k, x + 0.455 * k, y + 0.238 * k));
    this.alaska_0.translate_lu1900$(x - 0.307 * k, y + 0.201 * k);
    set_extentPostClip(this.alaska_0, new Extent(x - 0.425 * k + math.EPSILON, y + 0.12 * k + math.EPSILON, x - 0.214 * k - math.EPSILON, y + 0.234 * k - math.EPSILON));
    this.hawaii_0.translate_lu1900$(x - 0.205 * k, y + 0.212 * k);
    set_extentPostClip(this.hawaii_0, new Extent(x - 0.214 * k + math.EPSILON, y + 0.166 * k + math.EPSILON, x - 0.115 * k - math.EPSILON, y + 0.234 * k - math.EPSILON));
  };
  AlbersUSAProjection.prototype.chooseNestedProjection_lu1900$ = function (lambda, phi) {
    var tmp$;
    var k = this.lower48_0.scale;
    var newX = (lambda - this.lower48_0.translateX) / k;
    var newY = (phi - this.lower48_0.translateY) / k;
    if (newY >= 0.12 && newY < 0.234 && newX >= -0.425 && newX < -0.214)
      tmp$ = this.alaska_0;
    else if (newY >= 0.166 && newY < 0.234 && newX >= -0.214 && newX < -0.115)
      tmp$ = this.hawaii_0;
    else
      tmp$ = this.lower48_0;
    return tmp$;
  };
  function AlbersUSAProjection$alaska$lambda($receiver) {
    $receiver.rotate_u9a0y3$(get_deg(154.0), get_deg(0.0));
    $receiver.center_thqdec$(get_deg(-2.0), get_deg(58.5));
    $receiver.parallels_thqdec$(get_deg(55.0), get_deg(65.0));
    return Unit;
  }
  function AlbersUSAProjection$hawaii$lambda($receiver) {
    $receiver.rotate_u9a0y3$(get_deg(157.0), get_deg(0.0));
    $receiver.center_thqdec$(get_deg(-3.0), get_deg(19.9));
    $receiver.parallels_thqdec$(get_deg(8.0), get_deg(18.0));
    return Unit;
  }
  AlbersUSAProjection.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'AlbersUSAProjection',
    interfaces: [ComposedProjection]
  };
  function azimuthalInvert$lambda(closure$angle) {
    return function (x, y) {
      var x_0 = x * x + y * y;
      var z = Math_0.sqrt(x_0);
      var c = closure$angle(z);
      var sc = Math_0.sin(c);
      var tmp$ = Float64Array;
      var tmp$_0 = x * sc;
      var x_1 = z * Math_0.cos(c);
      return new tmp$([Math_0.atan2(tmp$_0, x_1), get_limitedAsin(z === 0.0 ? z : y * sc / z)]);
    };
  }
  function azimuthalInvert(angle) {
    return azimuthalInvert$lambda(angle);
  }
  function Azimuthal(scale, angle) {
    this.scale = scale;
    this.angle = angle;
  }
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
    var tmp$ = Float64Array;
    var tmp$_0 = x * sc;
    var x_1 = z * Math_0.cos(c);
    return new tmp$([Math_0.atan2(tmp$_0, x_1), get_asin(z !== 0.0 ? y * sc / z : z)]);
  };
  Azimuthal.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Azimuthal',
    interfaces: [Projector]
  };
  function scale$lambda(cxcy) {
    var x = 2 / (1 + cxcy);
    return Math_0.sqrt(x);
  }
  var scale;
  function angle$lambda(z) {
    return 2 * get_limitedAsin(z / 2);
  }
  var angle;
  function azimuthalEqualAreaProjection$lambda($receiver) {
    return Unit;
  }
  function azimuthalEqualAreaProjection$lambda_0(closure$init) {
    return function ($receiver) {
      $receiver.scale = 124.75;
      set_anglePreClip($receiver, get_deg(180 - 0.001));
      closure$init($receiver);
      return Unit;
    };
  }
  function azimuthalEqualAreaProjection(init) {
    if (init === void 0)
      init = azimuthalEqualAreaProjection$lambda;
    return projection(new Azimuthal(scale, angle), azimuthalEqualAreaProjection$lambda_0(init));
  }
  function azimuthalEquidistant$lambda($receiver) {
    return Unit;
  }
  function azimuthalEquidistant$lambda_0(closure$init) {
    return function ($receiver) {
      $receiver.scale = 79.4188;
      set_anglePreClip($receiver, get_deg(180 - 0.001));
      closure$init($receiver);
      return Unit;
    };
  }
  function azimuthalEquidistant(init) {
    if (init === void 0)
      init = azimuthalEquidistant$lambda;
    return projection(new AzimuthalEquidistantProjection(), azimuthalEquidistant$lambda_0(init));
  }
  function scale$lambda_0(cxcy) {
    var c = get_limitedAcos(cxcy);
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
  function conicProjection$lambda($receiver) {
    return Unit;
  }
  function conicProjection(projection, init) {
    if (init === void 0)
      init = conicProjection$lambda;
    var $receiver = new ConicProjection(projection);
    init($receiver);
    return $receiver;
  }
  function ConicProjector() {
  }
  ConicProjector.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'ConicProjector',
    interfaces: [Projector]
  };
  function ConicProjection(conicProjector) {
    ProjectorProjection.call(this, conicProjector);
    this.conicProjector = conicProjector;
    this.phi0_0 = 0.0;
    this.phi1_0 = math.PI / 3.0;
  }
  ConicProjection.prototype.parallels_thqdec$ = function (min, max) {
    this.parallelsMin = min;
    this.parallelsMax = max;
  };
  Object.defineProperty(ConicProjection.prototype, 'parallelsMin', {
    get: function () {
      return get_rad(this.phi0_0);
    },
    set: function (value) {
      this.phi0_0 = value.rad;
      this.conicProjector.phi0 = this.phi0_0;
    }
  });
  Object.defineProperty(ConicProjection.prototype, 'parallelsMax', {
    get: function () {
      return get_rad(this.phi1_0);
    },
    set: function (value) {
      this.phi1_0 = value.rad;
      this.conicProjector.phi1 = this.phi1_0;
    }
  });
  ConicProjection.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ConicProjection',
    interfaces: [ProjectorProjection]
  };
  function conicConformalProjection$lambda($receiver) {
    return Unit;
  }
  function conicConformalProjection$lambda_0(closure$init) {
    return function ($receiver) {
      $receiver.scale = 109.5;
      $receiver.parallels_thqdec$(get_deg(30.0), get_deg(30.0));
      closure$init($receiver);
      return Unit;
    };
  }
  function conicConformalProjection(init) {
    if (init === void 0)
      init = conicConformalProjection$lambda;
    return conicProjection(new ConicConformalBaseConditionalProjector(), conicConformalProjection$lambda_0(init));
  }
  function tany(y) {
    var x = (math.HALFPI + y) / 2;
    return Math_0.tan(x);
  }
  function ConicConformalBaseConditionalProjector(conicConformalProjector, mercatorProjector) {
    if (conicConformalProjector === void 0)
      conicConformalProjector = new ConicConformalProjector();
    if (mercatorProjector === void 0)
      mercatorProjector = new MercatorProjector();
    BaseConditionalProjector.call(this);
    this.conicConformalProjector_0 = conicConformalProjector;
    this.mercatorProjector_0 = mercatorProjector;
  }
  Object.defineProperty(ConicConformalBaseConditionalProjector.prototype, 'phi0', {
    get: function () {
      return this.conicConformalProjector_0.phi0;
    },
    set: function (value) {
      this.conicConformalProjector_0.phi0 = value;
    }
  });
  Object.defineProperty(ConicConformalBaseConditionalProjector.prototype, 'phi1', {
    get: function () {
      return this.conicConformalProjector_0.phi1;
    },
    set: function (value) {
      this.conicConformalProjector_0.phi1 = value;
    }
  });
  Object.defineProperty(ConicConformalBaseConditionalProjector.prototype, 'baseProjector', {
    get: function () {
      return this.mercatorProjector_0;
    }
  });
  Object.defineProperty(ConicConformalBaseConditionalProjector.prototype, 'nestedProjector', {
    get: function () {
      return this.conicConformalProjector_0;
    }
  });
  Object.defineProperty(ConicConformalBaseConditionalProjector.prototype, 'isNeedUseBaseProjector', {
    get: function () {
      return this.conicConformalProjector_0.isPossibleToUseProjector;
    }
  });
  ConicConformalBaseConditionalProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ConicConformalBaseConditionalProjector',
    interfaces: [BaseConditionalProjector, ConicProjector]
  };
  function ConicConformalProjector() {
    this.phi0_agf7nr$_0 = 0.0;
    this.phi1_agf7mw$_0 = math.PI / 3.0;
    this.cy0_0 = this.cy0_1();
    this.n_0 = this.n_1();
    this.f_0 = this.f_1();
    this.isPossibleToUseProjector_aasgr7$_0 = this.isPossibleToUse_0();
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
  Object.defineProperty(ConicConformalProjector.prototype, 'isPossibleToUseProjector', {
    get: function () {
      return this.isPossibleToUseProjector_aasgr7$_0;
    },
    set: function (isPossibleToUseProjector) {
      this.isPossibleToUseProjector_aasgr7$_0 = isPossibleToUseProjector;
    }
  });
  ConicConformalProjector.prototype.recalculate_0 = function () {
    this.cy0_0 = this.cy0_1();
    this.n_0 = this.n_1();
    this.f_0 = this.f_1();
    this.isPossibleToUseProjector = this.isPossibleToUse_0();
  };
  ConicConformalProjector.prototype.isPossibleToUse_0 = function () {
    return this.n_0 === 0.0 || this.n_0 === kotlin_js_internal_DoubleCompanionObject.NaN;
  };
  ConicConformalProjector.prototype.f_1 = function () {
    var tmp$ = this.cy0_0;
    var $receiver = tany(this.phi0);
    var x = this.n_0;
    return tmp$ * Math_0.pow($receiver, x) / this.n_0;
  };
  ConicConformalProjector.prototype.n_1 = function () {
    var tmp$;
    if (equals(this.phi0, this.phi1)) {
      var x = this.phi0;
      tmp$ = Math_0.sin(x);
    }
     else {
      var tmp$_0 = this.cy0_0;
      var x_0 = this.phi1;
      tmp$ = log(tmp$_0, Math_0.cos(x_0)) / log(tany(this.phi1), tany(this.phi0));
    }
    return tmp$;
  };
  ConicConformalProjector.prototype.cy0_1 = function () {
    var x = this.phi0;
    return Math_0.cos(x);
  };
  ConicConformalProjector.prototype.invert_lu1900$ = function (x, y) {
    var fy = this.fy_0(y);
    var rInvert = this.rInvert_0(x, fy);
    var tmp$ = Float64Array;
    var x_0 = Math_0.abs(fy);
    var tmp$_0 = Math_0.atan2(x, x_0) / this.n_0 * Math_0.sign(fy);
    var $receiver = this.f_0 / rInvert;
    var x_1 = 1 / this.n_0;
    var x_2 = Math_0.pow($receiver, x_1);
    return new tmp$([tmp$_0, 2 * Math_0.atan(x_2) - math.HALFPI]);
  };
  ConicConformalProjector.prototype.rInvert_0 = function (x, fy) {
    var x_0 = this.n_0;
    var tmp$ = Math_0.sign(x_0);
    var x_1 = x * x + fy * fy;
    return tmp$ * Math_0.sqrt(x_1);
  };
  ConicConformalProjector.prototype.project_lu1900$ = function (lambda, phi) {
    var convertedPhi = this.convertPhi_0(phi);
    var r = this.r_0(convertedPhi);
    var tmp$ = Float64Array;
    var x = this.n_0 * lambda;
    var tmp$_0 = r * Math_0.sin(x);
    var tmp$_1 = this.f_0;
    var x_0 = this.n_0 * lambda;
    return new tmp$([tmp$_0, tmp$_1 - r * Math_0.cos(x_0)]);
  };
  ConicConformalProjector.prototype.fy_0 = function (phi) {
    return this.f_0 - phi;
  };
  ConicConformalProjector.prototype.r_0 = function (convertedPhi) {
    var tmp$ = this.f_0;
    var $receiver = tany(convertedPhi);
    var x = this.n_0;
    return tmp$ / Math_0.pow($receiver, x);
  };
  ConicConformalProjector.prototype.convertPhi_0 = function (phi) {
    var tmp$;
    if (this.f_0 > 0) {
      if (phi < -math.HALFPI + math.EPSILON) {
        tmp$ = -math.HALFPI + math.EPSILON;
      }
       else {
        tmp$ = phi;
      }
    }
     else {
      if (phi > math.HALFPI - math.EPSILON) {
        tmp$ = math.HALFPI - math.EPSILON;
      }
       else {
        tmp$ = phi;
      }
    }
    return tmp$;
  };
  ConicConformalProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ConicConformalProjector',
    interfaces: [ConicProjector, Projector]
  };
  function conicEqualAreaProjection$lambda($receiver) {
    return Unit;
  }
  function conicEqualAreaProjection$lambda_0(closure$init) {
    return function ($receiver) {
      $receiver.scale = 155.424;
      $receiver.center_thqdec$(get_deg(0.0), get_deg(33.6442));
      closure$init($receiver);
      return Unit;
    };
  }
  function conicEqualAreaProjection(init) {
    if (init === void 0)
      init = conicEqualAreaProjection$lambda;
    return conicProjection(new ConicEqualAreaBaseConditionalProjector(), conicEqualAreaProjection$lambda_0(init));
  }
  function ConicEqualAreaBaseConditionalProjector(conicEqualAreaProjector, cylindricalEqualAreaProjector) {
    if (conicEqualAreaProjector === void 0)
      conicEqualAreaProjector = new ConicEqualAreaProjector();
    if (cylindricalEqualAreaProjector === void 0)
      cylindricalEqualAreaProjector = CylindricalEqualAreaProjector_init(conicEqualAreaProjector.phi0);
    BaseConditionalProjector.call(this);
    this.conicEqualAreaProjector_0 = conicEqualAreaProjector;
    this.cylindricalEqualAreaProjector_0 = cylindricalEqualAreaProjector;
  }
  Object.defineProperty(ConicEqualAreaBaseConditionalProjector.prototype, 'phi0', {
    get: function () {
      return this.conicEqualAreaProjector_0.phi0;
    },
    set: function (value) {
      this.conicEqualAreaProjector_0.phi0 = value;
    }
  });
  Object.defineProperty(ConicEqualAreaBaseConditionalProjector.prototype, 'phi1', {
    get: function () {
      return this.conicEqualAreaProjector_0.phi1;
    },
    set: function (value) {
      this.conicEqualAreaProjector_0.phi1 = value;
    }
  });
  Object.defineProperty(ConicEqualAreaBaseConditionalProjector.prototype, 'baseProjector', {
    get: function () {
      return this.cylindricalEqualAreaProjector_0;
    }
  });
  Object.defineProperty(ConicEqualAreaBaseConditionalProjector.prototype, 'nestedProjector', {
    get: function () {
      return this.conicEqualAreaProjector_0;
    }
  });
  Object.defineProperty(ConicEqualAreaBaseConditionalProjector.prototype, 'isNeedUseBaseProjector', {
    get: function () {
      return this.conicEqualAreaProjector_0.isPossibleToUseProjector;
    }
  });
  ConicEqualAreaBaseConditionalProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ConicEqualAreaBaseConditionalProjector',
    interfaces: [BaseConditionalProjector, ConicProjector]
  };
  function ConicEqualAreaProjector() {
    this.phi0_benbex$_0 = 0.0;
    this.phi1_benbfs$_0 = math.PI / 3.0;
    this.sy0_0 = this.sy0_1();
    this.n_0 = this.n_1();
    this.c_0 = this.c_1();
    this.r0_0 = this.r0_1();
    this.isPossibleToUseProjector_odlvzx$_0 = this.isPossibleToUse_0();
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
  Object.defineProperty(ConicEqualAreaProjector.prototype, 'isPossibleToUseProjector', {
    get: function () {
      return this.isPossibleToUseProjector_odlvzx$_0;
    },
    set: function (isPossibleToUseProjector) {
      this.isPossibleToUseProjector_odlvzx$_0 = isPossibleToUseProjector;
    }
  });
  ConicEqualAreaProjector.prototype.recalculate_0 = function () {
    this.sy0_0 = this.sy0_1();
    this.n_0 = this.n_1();
    this.c_0 = this.c_1();
    this.r0_0 = this.r0_1();
    this.isPossibleToUseProjector = this.isPossibleToUse_0();
  };
  ConicEqualAreaProjector.prototype.isPossibleToUse_0 = function () {
    var x = this.n_0;
    return Math_0.abs(x) < math.EPSILON;
  };
  ConicEqualAreaProjector.prototype.r0_1 = function () {
    var x = this.c_0;
    return Math_0.sqrt(x) / this.n_0;
  };
  ConicEqualAreaProjector.prototype.c_1 = function () {
    return 1 + this.sy0_0 * (2 * this.n_0 - this.sy0_0);
  };
  ConicEqualAreaProjector.prototype.n_1 = function () {
    var tmp$ = this.sy0_0;
    var x = this.phi1;
    return (tmp$ + Math_0.sin(x)) / 2;
  };
  ConicEqualAreaProjector.prototype.sy0_1 = function () {
    var x = this.phi0;
    return Math_0.sin(x);
  };
  ConicEqualAreaProjector.prototype.invert_lu1900$ = function (x, y) {
    var r0y = this.r0y_0(y);
    var tmp$ = Float64Array;
    var x_0 = Math_0.abs(r0y);
    var tmp$_0 = Math_0.atan2(x, x_0) / this.n_0 * Math_0.sign(r0y);
    var x_1 = (this.c_0 - (x * x + r0y * r0y) * this.n_0 * this.n_0) / (2 * this.n_0);
    return new tmp$([tmp$_0, Math_0.asin(x_1)]);
  };
  ConicEqualAreaProjector.prototype.project_lu1900$ = function (lambda, phi) {
    var r = this.r_0(phi);
    var tmp$ = Float64Array;
    var x = lambda * this.n_0;
    var tmp$_0 = r * Math_0.sin(x);
    var tmp$_1 = this.r0_0;
    var x_0 = lambda * this.n_0;
    return new tmp$([tmp$_0, tmp$_1 - r * Math_0.cos(x_0)]);
  };
  ConicEqualAreaProjector.prototype.r0y_0 = function (phi) {
    return this.r0_0 - phi;
  };
  ConicEqualAreaProjector.prototype.r_0 = function (phi) {
    var x = this.c_0 - 2 * this.n_0 * Math_0.sin(phi);
    return Math_0.sqrt(x) / this.n_0;
  };
  ConicEqualAreaProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ConicEqualAreaProjector',
    interfaces: [ConicProjector, Projector]
  };
  function conicEquidistantProjection$lambda($receiver) {
    return Unit;
  }
  function conicEquidistantProjection$lambda_0(closure$init) {
    return function ($receiver) {
      $receiver.scale = 131.154;
      $receiver.center_thqdec$(get_deg(0.0), get_deg(13.9389));
      closure$init($receiver);
      return Unit;
    };
  }
  function conicEquidistantProjection(init) {
    if (init === void 0)
      init = conicEquidistantProjection$lambda;
    return conicProjection(new ConicEquidistantBaseConditionalProjector(), conicEquidistantProjection$lambda_0(init));
  }
  function ConicEquidistantBaseConditionalProjector(conicEquidistantProjector, equirectangularProjector) {
    if (conicEquidistantProjector === void 0)
      conicEquidistantProjector = new ConicEquidistantProjector();
    if (equirectangularProjector === void 0)
      equirectangularProjector = new EquirectangularProjector();
    BaseConditionalProjector.call(this);
    this.conicEquidistantProjector_0 = conicEquidistantProjector;
    this.equirectangularProjector_0 = equirectangularProjector;
  }
  Object.defineProperty(ConicEquidistantBaseConditionalProjector.prototype, 'phi0', {
    get: function () {
      return this.conicEquidistantProjector_0.phi0;
    },
    set: function (value) {
      this.conicEquidistantProjector_0.phi0 = value;
    }
  });
  Object.defineProperty(ConicEquidistantBaseConditionalProjector.prototype, 'phi1', {
    get: function () {
      return this.conicEquidistantProjector_0.phi1;
    },
    set: function (value) {
      this.conicEquidistantProjector_0.phi1 = value;
    }
  });
  Object.defineProperty(ConicEquidistantBaseConditionalProjector.prototype, 'baseProjector', {
    get: function () {
      return this.equirectangularProjector_0;
    }
  });
  Object.defineProperty(ConicEquidistantBaseConditionalProjector.prototype, 'nestedProjector', {
    get: function () {
      return this.conicEquidistantProjector_0;
    }
  });
  Object.defineProperty(ConicEquidistantBaseConditionalProjector.prototype, 'isNeedUseBaseProjector', {
    get: function () {
      return this.conicEquidistantProjector_0.isPossibleToUseProjector;
    }
  });
  ConicEquidistantBaseConditionalProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ConicEquidistantBaseConditionalProjector',
    interfaces: [BaseConditionalProjector, ConicProjector]
  };
  function ConicEquidistantProjector() {
    this.phi0_n8jo5z$_0 = 0.0;
    this.phi1_n8jo54$_0 = math.PI / 3.0;
    this.cy0_0 = this.cy0_1();
    this.n_0 = this.n_1();
    this.g_0 = this.g_1();
    this.isPossibleToUseProjector_w9pso3$_0 = this.isPossibleToUse_0();
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
  Object.defineProperty(ConicEquidistantProjector.prototype, 'isPossibleToUseProjector', {
    get: function () {
      return this.isPossibleToUseProjector_w9pso3$_0;
    },
    set: function (isPossibleToUseProjector) {
      this.isPossibleToUseProjector_w9pso3$_0 = isPossibleToUseProjector;
    }
  });
  ConicEquidistantProjector.prototype.recalculate_0 = function () {
    this.cy0_0 = this.cy0_1();
    this.n_0 = this.n_1();
    this.g_0 = this.g_1();
    this.isPossibleToUseProjector = this.isPossibleToUse_0();
  };
  ConicEquidistantProjector.prototype.isPossibleToUse_0 = function () {
    var x = this.n_0;
    return Math_0.abs(x) < math.EPSILON;
  };
  ConicEquidistantProjector.prototype.g_1 = function () {
    return this.cy0_0 / this.n_0 + this.phi0;
  };
  ConicEquidistantProjector.prototype.n_1 = function () {
    var tmp$;
    if (this.phi0 === this.phi1) {
      var x = this.phi0;
      tmp$ = Math_0.sin(x);
    }
     else {
      var tmp$_0 = this.cy0_0;
      var x_0 = this.phi1;
      tmp$ = (tmp$_0 - Math_0.cos(x_0)) / (this.phi1 - this.phi0);
    }
    return tmp$;
  };
  ConicEquidistantProjector.prototype.cy0_1 = function () {
    var x = this.phi0;
    return Math_0.cos(x);
  };
  ConicEquidistantProjector.prototype.invert_lu1900$ = function (x, y) {
    var gy = this.g_0 - y;
    var tmp$ = Float64Array;
    var x_0 = Math_0.abs(gy);
    var tmp$_0 = Math_0.atan2(x, x_0) / this.n_0 * Math_0.sign(gy);
    var tmp$_1 = this.g_0;
    var x_1 = this.n_0;
    var tmp$_2 = Math_0.sign(x_1);
    var x_2 = x * x + gy * gy;
    return new tmp$([tmp$_0, tmp$_1 - tmp$_2 * Math_0.sqrt(x_2)]);
  };
  ConicEquidistantProjector.prototype.project_lu1900$ = function (lambda, phi) {
    var gphi = this.g_0 - phi;
    var nlambda = this.n_0 * lambda;
    return new Float64Array([gphi * Math_0.sin(nlambda), this.g_0 - gphi * Math_0.cos(nlambda)]);
  };
  ConicEquidistantProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ConicEquidistantProjector',
    interfaces: [ConicProjector, Projector]
  };
  function CylindricalEqualAreaProjector() {
    this.phi0_ntkqud$_0 = 0.0;
    this.cosPhi0 = 0.0;
  }
  Object.defineProperty(CylindricalEqualAreaProjector.prototype, 'phi0', {
    get: function () {
      return this.phi0_ntkqud$_0;
    },
    set: function (value) {
      this.phi0_ntkqud$_0 = value;
      this.recalculate_0();
    }
  });
  CylindricalEqualAreaProjector.prototype.recalculate_0 = function () {
    var x = this.phi0;
    this.cosPhi0 = Math_0.cos(x);
  };
  CylindricalEqualAreaProjector.prototype.project_lu1900$ = function (lambda, phi) {
    return new Float64Array([lambda * this.cosPhi0, Math_0.sin(phi) / this.cosPhi0]);
  };
  CylindricalEqualAreaProjector.prototype.invert_lu1900$ = function (x, y) {
    var tmp$ = Float64Array;
    var tmp$_0 = x / this.cosPhi0;
    var x_0 = y * this.cosPhi0;
    return new tmp$([tmp$_0, Math_0.asin(x_0)]);
  };
  CylindricalEqualAreaProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'CylindricalEqualAreaProjector',
    interfaces: [Projector]
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
  function equalEarthProjection$lambda_0(closure$init) {
    return function ($receiver) {
      $receiver.scale = 177.158;
      closure$init($receiver);
      return Unit;
    };
  }
  function equalEarthProjection(init) {
    if (init === void 0)
      init = equalEarthProjection$lambda;
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
    var l = y;
    var l2 = l * l;
    var l6 = l2 * l2 * l2;
    for (var i = 0; i < 12; i++) {
      var fy = l * (A1 + A2 * l2 + l6 * (A3 + A4 * l2)) - y;
      var fpy = A1 + 3 * A2 * l2 + l6 * (7 * A3 + 9 * A4 * l2);
      var delta = fy / fpy;
      l2 = l * l;
      l6 = l2 * l2 * l2;
      l -= delta;
      if (Math_0.abs(delta) < math.EPSILON2)
        break;
    }
    var tmp$ = Float64Array;
    var tmp$_0 = M * x * (A1 + 3 * A2 * l2 + l6 * (7 * A3 + 9 * A4 * l2)) / Math_0.cos(l);
    var x_0 = Math_0.sin(l) / M;
    return new tmp$([tmp$_0, Math_0.asin(x_0)]);
  };
  EqualEarthProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'EqualEarthProjector',
    interfaces: [Projector]
  };
  function equirectangularProjection$lambda($receiver) {
    return Unit;
  }
  function equirectangularProjection$lambda_0(closure$init) {
    return function ($receiver) {
      $receiver.scale = 152.63;
      closure$init($receiver);
      return Unit;
    };
  }
  function equirectangularProjection(init) {
    if (init === void 0)
      init = equirectangularProjection$lambda;
    return projection(new EquirectangularProjector(), equirectangularProjection$lambda_0(init));
  }
  function EquirectangularProjector() {
  }
  EquirectangularProjector.prototype.project_lu1900$ = function (lambda, phi) {
    return new Float64Array([lambda, phi]);
  };
  EquirectangularProjector.prototype.invert_lu1900$ = function (x, y) {
    return new Float64Array([x, y]);
  };
  EquirectangularProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'EquirectangularProjector',
    interfaces: [Projector]
  };
  function gnomonicProjection$lambda($receiver) {
    return Unit;
  }
  function gnomonicProjection$lambda_0(closure$init) {
    return function ($receiver) {
      set_anglePreClip($receiver, get_deg(60.0));
      $receiver.scale = 144.049;
      closure$init($receiver);
      return Unit;
    };
  }
  function gnomonicProjection(init) {
    if (init === void 0)
      init = gnomonicProjection$lambda;
    return projection(new GnomonicProjector(), gnomonicProjection$lambda_0(init));
  }
  function GnomonicProjector() {
  }
  GnomonicProjector.prototype.project_lu1900$ = function (lambda, phi) {
    var cy = Math_0.cos(phi);
    var k = Math_0.cos(lambda) * cy;
    return new Float64Array([cy * Math_0.sin(lambda) / k, Math_0.sin(phi) / k]);
  };
  GnomonicProjector.prototype.invert_lu1900$ = function (x, y) {
    return azimuthalInvert(getCallableRef('atan', function (x) {
      return Math_0.atan(x);
    }))(x, y);
  };
  GnomonicProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'GnomonicProjector',
    interfaces: [Projector]
  };
  function identityProjection$lambda($receiver) {
    return Unit;
  }
  function identityProjection$lambda_0(closure$init) {
    return function ($receiver) {
      $receiver.preClip = NoClip;
      $receiver.postClip = NoClip;
      $receiver.scale = 180 / math.PI;
      closure$init($receiver);
      return Unit;
    };
  }
  function identityProjection(init) {
    if (init === void 0)
      init = identityProjection$lambda;
    return projection(new IdentityProjection(), identityProjection$lambda_0(init));
  }
  function IdentityProjection() {
  }
  IdentityProjection.prototype.invert_lu1900$ = function (x, y) {
    return new Float64Array([x, y]);
  };
  IdentityProjection.prototype.project_lu1900$ = function (lambda, phi) {
    return new Float64Array([lambda, phi]);
  };
  IdentityProjection.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'IdentityProjection',
    interfaces: [Projector]
  };
  function mercatorProjection$lambda($receiver) {
    return Unit;
  }
  function mercatorProjection(init) {
    if (init === void 0)
      init = mercatorProjection$lambda;
    var $receiver = new MercatorProjection(new MercatorProjector());
    $receiver.scale = 961 / math.TAU;
    init($receiver);
    return $receiver;
  }
  function MercatorProjector() {
  }
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
    interfaces: [Projector]
  };
  function MercatorProjection(projector) {
    if (projector === void 0)
      projector = new MercatorProjector();
    ProjectorProjection.call(this, projector);
  }
  Object.defineProperty(MercatorProjection.prototype, 'scale', {
    get: function () {
      return Kotlin.callGetter(this, ProjectorProjection.prototype, 'scale');
    },
    set: function (value) {
      Kotlin.callSetter(this, ProjectorProjection.prototype, 'scale', value);
      this.reclip_p9bhzi$_0();
    }
  });
  Object.defineProperty(MercatorProjection.prototype, 'translateX', {
    get: function () {
      return Kotlin.callGetter(this, ProjectorProjection.prototype, 'translateX');
    },
    set: function (value) {
      Kotlin.callSetter(this, ProjectorProjection.prototype, 'translateX', value);
      this.reclip_p9bhzi$_0();
    }
  });
  Object.defineProperty(MercatorProjection.prototype, 'translateY', {
    get: function () {
      return Kotlin.callGetter(this, ProjectorProjection.prototype, 'translateY');
    },
    set: function (value) {
      Kotlin.callSetter(this, ProjectorProjection.prototype, 'translateY', value);
      this.reclip_p9bhzi$_0();
    }
  });
  MercatorProjection.prototype.translate_lu1900$ = function (x, y) {
    ProjectorProjection.prototype.translate_lu1900$.call(this, x, y);
    this.reclip_p9bhzi$_0();
  };
  Object.defineProperty(MercatorProjection.prototype, 'centerLat', {
    get: function () {
      return Kotlin.callGetter(this, ProjectorProjection.prototype, 'centerLat');
    },
    set: function (value) {
      Kotlin.callSetter(this, ProjectorProjection.prototype, 'centerLat', value);
      this.reclip_p9bhzi$_0();
    }
  });
  Object.defineProperty(MercatorProjection.prototype, 'centerLon', {
    get: function () {
      return Kotlin.callGetter(this, ProjectorProjection.prototype, 'centerLon');
    },
    set: function (value) {
      Kotlin.callSetter(this, ProjectorProjection.prototype, 'centerLon', value);
      this.reclip_p9bhzi$_0();
    }
  });
  MercatorProjection.prototype.center_thqdec$ = function (lat, lon) {
    ProjectorProjection.prototype.center_thqdec$.call(this, lat, lon);
    this.reclip_p9bhzi$_0();
  };
  MercatorProjection.prototype.reclip_p9bhzi$_0 = function () {
    var tmp$;
    var k = math.PI * this.scale;
    var invert = (new RotationProjector(this.rotateLambda, this.rotatePhi, this.rotateGamma)).invert_lu1900$(0.0, 0.0);
    var lambda = invert[0];
    var phi = invert[1];
    var projected = this.projector.project_lu1900$(lambda, phi);
    var t0 = projected[0];
    var t1 = projected[1];
    if (get_extentPostClip(this) == null)
      tmp$ = new Extent(t0 - k, t1 - k, k * 2, k * 2);
    else if (Kotlin.isType(this.projector, MercatorProjector)) {
      var a = t0 - k;
      var b = ensureNotNull(get_extentPostClip(this)).x0;
      var tmp$_0 = Math_0.max(a, b);
      var tmp$_1 = ensureNotNull(get_extentPostClip(this)).y0;
      var a_0 = k * 2;
      var b_0 = ensureNotNull(get_extentPostClip(this)).width;
      var b_1 = Math_0.min(a_0, b_0);
      tmp$ = new Extent(tmp$_0, tmp$_1, Math_0.max(0.0, b_1), ensureNotNull(get_extentPostClip(this)).height);
    }
     else {
      var tmp$_2 = ensureNotNull(get_extentPostClip(this)).x0;
      var a_1 = t1 - k;
      var b_2 = ensureNotNull(get_extentPostClip(this)).y0;
      var tmp$_3 = Math_0.max(a_1, b_2);
      var tmp$_4 = ensureNotNull(get_extentPostClip(this)).width;
      var a_2 = k * 2;
      var b_3 = ensureNotNull(get_extentPostClip(this)).height;
      tmp$ = new Extent(tmp$_2, tmp$_3, tmp$_4, Math_0.min(a_2, b_3));
    }
    set_extentPostClip(this, tmp$);
  };
  MercatorProjection.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'MercatorProjection',
    interfaces: [ProjectorProjection]
  };
  function naturalEarthProjection$lambda($receiver) {
    return Unit;
  }
  function naturalEarthProjection$lambda_0(closure$init) {
    return function ($receiver) {
      $receiver.scale = 175.295;
      closure$init($receiver);
      return Unit;
    };
  }
  function naturalEarthProjection(init) {
    if (init === void 0)
      init = naturalEarthProjection$lambda;
    return projection(new NaturalEarthProjection(), naturalEarthProjection$lambda_0(init));
  }
  function NaturalEarthProjection() {
  }
  NaturalEarthProjection.prototype.invert_lu1900$ = function (x, y) {
    var newPhi = y;
    var i = 25;
    var delta;
    do {
      var phi2 = newPhi * newPhi;
      var phi4 = phi2 * phi2;
      delta = (newPhi * (1.007226 + phi2 * (0.015085 + phi4 * (-0.044475 + 0.028874 * phi2 - 0.005916 * phi4))) - newPhi) / (1.007226 + phi2 * (0.015085 * 3 + phi4 * (-0.044475 * 7 + 0.028874 * 9 * phi2 - 0.005916 * 11 * phi4)));
      newPhi -= delta;
      var abs$result;
      abs$result = Math_0.abs(delta);
    }
     while (abs$result > math.EPSILON && (i = i - 1 | 0, i) > 0);
    var phi2_0 = newPhi * newPhi;
    return new Float64Array([x / (0.8707 + phi2_0 * (-0.131979 + phi2_0 * (-0.013791 + phi2_0 * phi2_0 * phi2_0 * (0.003971 - 0.001529 * phi2_0)))), newPhi]);
  };
  NaturalEarthProjection.prototype.project_lu1900$ = function (lambda, phi) {
    var phi2 = phi * phi;
    var phi4 = phi2 * phi2;
    return new Float64Array([lambda * (0.8707 - 0.131979 * phi2 + phi4 * (-0.013791 + phi4 * (0.003971 * phi2 - 0.001529 * phi4))), phi * (1.007226 + phi2 * (0.015085 + phi4 * (-0.044475 + 0.028874 * phi2 - 0.005916 * phi4)))]);
  };
  NaturalEarthProjection.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'NaturalEarthProjection',
    interfaces: [Projector]
  };
  function orthographicProjection$lambda($receiver) {
    return Unit;
  }
  function orthographicProjection$lambda_0(closure$init) {
    return function ($receiver) {
      $receiver.scale = 249.5;
      set_anglePreClip($receiver, get_deg(90 + math.EPSILON));
      closure$init($receiver);
      return Unit;
    };
  }
  function orthographicProjection(init) {
    if (init === void 0)
      init = orthographicProjection$lambda;
    return projection(new OrthographicProjector(), orthographicProjection$lambda_0(init));
  }
  function OrthographicProjector() {
  }
  OrthographicProjector.prototype.project_lu1900$ = function (lambda, phi) {
    return new Float64Array([Math_0.cos(phi) * Math_0.sin(lambda), Math_0.sin(phi)]);
  };
  OrthographicProjector.prototype.invert_lu1900$ = function (x, y) {
    return azimuthalInvert(getCallableRef('asin', function (x) {
      return Math_0.asin(x);
    }))(x, y);
  };
  OrthographicProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'OrthographicProjector',
    interfaces: [Projector]
  };
  function stereographicProjection$lambda($receiver) {
    return Unit;
  }
  function stereographicProjection$lambda_0(closure$init) {
    return function ($receiver) {
      $receiver.scale = 250.0;
      set_anglePreClip($receiver, get_deg(142.0));
      closure$init($receiver);
      return Unit;
    };
  }
  function stereographicProjection(init) {
    if (init === void 0)
      init = stereographicProjection$lambda;
    return projection(new StereographicProjector(), stereographicProjection$lambda_0(init));
  }
  function doubleAtan(d) {
    return 2 * Math_0.atan(d);
  }
  function StereographicProjector() {
  }
  StereographicProjector.prototype.project_lu1900$ = function (lambda, phi) {
    var cosPhi = Math_0.cos(phi);
    var k = 1 + Math_0.cos(lambda) * cosPhi;
    return new Float64Array([cosPhi * Math_0.sin(lambda) / k, Math_0.sin(phi) / k]);
  };
  StereographicProjector.prototype.invert_lu1900$ = function (x, y) {
    return azimuthalInvert(getCallableRef('doubleAtan', function (d) {
      return doubleAtan(d);
    }))(x, y);
  };
  StereographicProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'StereographicProjector',
    interfaces: [Projector]
  };
  function transverseMercatorProjection$lambda($receiver) {
    return Unit;
  }
  function transverseMercatorProjection(init) {
    if (init === void 0)
      init = transverseMercatorProjection$lambda;
    var $receiver = new TransverseMercatorProjection();
    $receiver.rotate_u9a0y3$(get_deg(0), get_deg(0), get_deg(90));
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
  TransverseMercatorProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'TransverseMercatorProjector',
    interfaces: [Projector]
  };
  function TransverseMercatorProjection() {
    MercatorProjection.call(this, new TransverseMercatorProjector());
  }
  Object.defineProperty(TransverseMercatorProjection.prototype, 'centerLat', {
    get: function () {
      return Kotlin.callGetter(this, MercatorProjection.prototype, 'centerLon');
    },
    set: function (value) {
      Kotlin.callSetter(this, MercatorProjection.prototype, 'centerLon', value);
    }
  });
  Object.defineProperty(TransverseMercatorProjection.prototype, 'centerLon', {
    get: function () {
      return Kotlin.callGetter(this, MercatorProjection.prototype, 'centerLat').unaryMinus();
    },
    set: function (value) {
      Kotlin.callSetter(this, MercatorProjection.prototype, 'centerLat', value.unaryMinus());
    }
  });
  TransverseMercatorProjection.prototype.center_thqdec$ = function (lat, lon) {
    MercatorProjection.prototype.center_thqdec$.call(this, lon.unaryMinus(), lat);
  };
  Object.defineProperty(TransverseMercatorProjection.prototype, 'rotateGamma', {
    get: function () {
      return Kotlin.callGetter(this, MercatorProjection.prototype, 'rotateGamma').minus_5t6zck$(get_deg(90.0));
    },
    set: function (value) {
      Kotlin.callSetter(this, MercatorProjection.prototype, 'rotateGamma', value.plus_5t6zck$(get_deg(90.0)));
    }
  });
  TransverseMercatorProjection.prototype.rotate_u9a0y3$$default = function (lambda, phi, gamma) {
    if (gamma != null) {
      this.rotate_u9a0y3$(lambda, phi, gamma.plus_5t6zck$(get_deg(90.0)), MercatorProjection.prototype.rotate_u9a0y3$$default.bind(this));
    }
     else {
      this.rotate_u9a0y3$(lambda, phi, get_deg(90.0), MercatorProjection.prototype.rotate_u9a0y3$$default.bind(this));
    }
  };
  TransverseMercatorProjection.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'TransverseMercatorProjection',
    interfaces: [MercatorProjection]
  };
  function CachedProjection() {
    this.streamCache = new StreamCache();
  }
  CachedProjection.prototype.reset = function () {
    this.streamCache.reset();
  };
  CachedProjection.prototype.stream_enk0m$ = function (stream) {
    if (!this.streamCache.isCacheValidFor_enk0m$(stream)) {
      var resultStream = this.fullCycleStream_enk0m$(stream);
      this.streamCache.cache_ln4vjc$(stream, resultStream);
    }
    return ensureNotNull(this.streamCache.cachedResultStream);
  };
  CachedProjection.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'CachedProjection',
    interfaces: [Projection]
  };
  function ComposedProjection() {
  }
  Object.defineProperty(ComposedProjection.prototype, 'centerLat', {
    get: function () {
      return this.mainProjection.centerLat;
    },
    set: function (value) {
      var tmp$;
      tmp$ = this.allProjections.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        element.centerLat = value;
      }
    }
  });
  Object.defineProperty(ComposedProjection.prototype, 'centerLon', {
    get: function () {
      return this.mainProjection.centerLon;
    },
    set: function (value) {
      var tmp$;
      tmp$ = this.allProjections.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        element.centerLon = value;
      }
    }
  });
  Object.defineProperty(ComposedProjection.prototype, 'rotateLambda', {
    get: function () {
      return this.mainProjection.rotateLambda;
    },
    set: function (value) {
      var tmp$;
      tmp$ = this.allProjections.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        element.rotateLambda = value;
      }
    }
  });
  Object.defineProperty(ComposedProjection.prototype, 'rotatePhi', {
    get: function () {
      return this.mainProjection.rotatePhi;
    },
    set: function (value) {
      var tmp$;
      tmp$ = this.allProjections.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        element.rotatePhi = value;
      }
    }
  });
  Object.defineProperty(ComposedProjection.prototype, 'rotateGamma', {
    get: function () {
      return this.mainProjection.rotateGamma;
    },
    set: function (value) {
      var tmp$;
      tmp$ = this.allProjections.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        element.rotateGamma = value;
      }
    }
  });
  Object.defineProperty(ComposedProjection.prototype, 'preClip', {
    get: function () {
      return this.mainProjection.preClip;
    },
    set: function (value) {
      var tmp$;
      tmp$ = this.allProjections.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        element.preClip = value;
      }
    }
  });
  Object.defineProperty(ComposedProjection.prototype, 'postClip', {
    get: function () {
      return this.mainProjection.postClip;
    },
    set: function (value) {
      var tmp$;
      tmp$ = this.allProjections.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        element.postClip = value;
      }
    }
  });
  Object.defineProperty(ComposedProjection.prototype, 'precision', {
    get: function () {
      return this.mainProjection.precision;
    },
    set: function (value) {
      var tmp$;
      tmp$ = this.allProjections.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        element.precision = value;
      }
    }
  });
  Object.defineProperty(ComposedProjection.prototype, 'translateX', {
    get: function () {
      return this.mainProjection.translateX;
    },
    set: function (value) {
      var tmp$;
      tmp$ = this.allProjections.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        element.translateX = value;
      }
    }
  });
  Object.defineProperty(ComposedProjection.prototype, 'translateY', {
    get: function () {
      return this.mainProjection.translateY;
    },
    set: function (value) {
      var tmp$;
      tmp$ = this.allProjections.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        element.translateY = value;
      }
    }
  });
  Object.defineProperty(ComposedProjection.prototype, 'scale', {
    get: function () {
      return this.mainProjection.scale;
    },
    set: function (value) {
      var tmp$;
      tmp$ = this.allProjections.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        element.scale = value;
      }
    }
  });
  ComposedProjection.prototype.translate_lu1900$ = function (x, y) {
    var tmp$;
    tmp$ = this.allProjections.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      element.translate_lu1900$(x, y);
    }
  };
  ComposedProjection.prototype.center_thqdec$ = function (lat, lon) {
    var tmp$;
    tmp$ = this.allProjections.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      element.center_thqdec$(lat, lon);
    }
  };
  ComposedProjection.prototype.rotate_u9a0y3$$default = function (lambda, phi, gamma) {
    var tmp$;
    tmp$ = this.allProjections.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      element.rotate_u9a0y3$(lambda, phi, gamma);
    }
  };
  ComposedProjection.prototype.project_lu1900$ = function (lambda, phi) {
    return this.chooseNestedProjection_lu1900$(lambda, phi).project_lu1900$(lambda, phi);
  };
  ComposedProjection.prototype.invert_lu1900$ = function (x, y) {
    return this.chooseNestedProjection_lu1900$(x, y).invert_lu1900$(x, y);
  };
  ComposedProjection.prototype.stream_enk0m$ = function (stream) {
    var $receiver = this.allProjections;
    var destination = ArrayList_init(collectionSizeOrDefault($receiver, 10));
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(item.stream_enk0m$(stream));
    }
    return new MultiplexStream(destination);
  };
  ComposedProjection.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ComposedProjection',
    interfaces: [Projection]
  };
  function ComposedProjector(a, b) {
    this.a = a;
    this.b = b;
  }
  ComposedProjector.prototype.project_lu1900$ = function (lambda, phi) {
    var p = this.a.project_lu1900$(lambda, phi);
    return this.b.project_lu1900$(p[0], p[1]);
  };
  ComposedProjector.prototype.invert_lu1900$ = function (x, y) {
    var p = this.b.invert_lu1900$(x, y);
    return this.a.invert_lu1900$(p[0], p[1]);
  };
  ComposedProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ComposedProjector',
    interfaces: [Projector]
  };
  function ConditionalProjector() {
  }
  ConditionalProjector.prototype.invert_lu1900$ = function (x, y) {
    return this.activeProjector.invert_lu1900$(x, y);
  };
  ConditionalProjector.prototype.project_lu1900$ = function (lambda, phi) {
    return this.activeProjector.project_lu1900$(lambda, phi);
  };
  ConditionalProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ConditionalProjector',
    interfaces: [Projector]
  };
  function BaseConditionalProjector() {
    ConditionalProjector.call(this);
  }
  Object.defineProperty(BaseConditionalProjector.prototype, 'activeProjector', {
    get: function () {
      if (this.isNeedUseBaseProjector) {
        return this.baseProjector;
      }
       else {
        return this.nestedProjector;
      }
    }
  });
  BaseConditionalProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'BaseConditionalProjector',
    interfaces: [ConditionalProjector]
  };
  function Projection() {
  }
  Projection.prototype.rotate_u9a0y3$ = function (lambda, phi, gamma, callback$default) {
    if (gamma === void 0)
      gamma = null;
    callback$default ? callback$default(lambda, phi, gamma) : this.rotate_u9a0y3$$default(lambda, phi, gamma);
  };
  Projection.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Projection',
    interfaces: [Projector]
  };
  function Projectable() {
  }
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
  function Projector() {
  }
  Projector.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Projector',
    interfaces: [Invertable, Projectable]
  };
  function projection(projector, init) {
    var $receiver = new ProjectorProjection(projector);
    init($receiver);
    return $receiver;
  }
  function transformRadians$lambda$ObjectLiteral(closure$stream, delegate_0) {
    this.closure$stream = closure$stream;
    DelegateStreamAdapter.call(this, delegate_0);
  }
  transformRadians$lambda$ObjectLiteral.prototype.point_yvo9jy$ = function (x, y, z) {
    this.closure$stream.point_yvo9jy$(toRadians(x), toRadians(y), toRadians(z));
  };
  transformRadians$lambda$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [DelegateStreamAdapter]
  };
  function transformRadians$lambda(stream) {
    return new transformRadians$lambda$ObjectLiteral(stream, stream);
  }
  var transformRadians;
  function transformRotate$lambda$ObjectLiteral(closure$rotateProjector, closure$stream, delegate_0) {
    this.closure$rotateProjector = closure$rotateProjector;
    this.closure$stream = closure$stream;
    DelegateStreamAdapter.call(this, delegate_0);
  }
  transformRotate$lambda$ObjectLiteral.prototype.point_yvo9jy$ = function (x, y, z) {
    var projection = this.closure$rotateProjector.project_lu1900$(x, y);
    this.closure$stream.point_yvo9jy$(projection[0], projection[1], 0.0);
  };
  transformRotate$lambda$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [DelegateStreamAdapter]
  };
  function transformRotate$lambda(closure$rotateProjector) {
    return function (stream) {
      return new transformRotate$lambda$ObjectLiteral(closure$rotateProjector, stream, stream);
    };
  }
  function transformRotate(rotateProjector) {
    return transformRotate$lambda(rotateProjector);
  }
  function ProjectorProjection(projector) {
    this.projector = projector;
    this._translateX_bniy78$_0 = 480.0;
    this._translateY_bniy6d$_0 = 250.0;
    this._recenterDx_ekovbq$_0 = 0.0;
    this._recenterDy_ekovav$_0 = 0.0;
    this._centerLat_qoy1j8$_0 = 0.0;
    this._centerLon_qoybrs$_0 = 0.0;
    this._scale_5kxa7w$_0 = 150.0;
    this.composedTransformationsProjector_43m79x$_0 = this.composedTransformationsProjector_43m79x$_0;
    this.translateAndScaleProjector = new TranslateAndScaleProjector(this.projector, this._scale_5kxa7w$_0, this._recenterDx_ekovbq$_0, this._recenterDy_ekovav$_0);
    this._precisionDelta2_5zdy8a$_0 = 0.5;
    this._rotationLambda = 0.0;
    this._rotationPhi = 0.0;
    this._rotationGamma = 0.0;
    this.rotator_v71a08$_0 = this.rotator_v71a08$_0;
    this.preClip_inpaeq$_0 = antimeridianPreClip;
    this.postClip_9p1oyn$_0 = NoClip;
    this.resampleProjector_2l2wd6$_0 = resample(this.translateAndScaleProjector, this._precisionDelta2_5zdy8a$_0);
  }
  Object.defineProperty(ProjectorProjection.prototype, 'composedTransformationsProjector', {
    get: function () {
      if (this.composedTransformationsProjector_43m79x$_0 == null)
        return throwUPAE('composedTransformationsProjector');
      return this.composedTransformationsProjector_43m79x$_0;
    },
    set: function (composedTransformationsProjector) {
      this.composedTransformationsProjector_43m79x$_0 = composedTransformationsProjector;
    }
  });
  Object.defineProperty(ProjectorProjection.prototype, 'rotator', {
    get: function () {
      if (this.rotator_v71a08$_0 == null)
        return throwUPAE('rotator');
      return this.rotator_v71a08$_0;
    },
    set: function (rotator) {
      this.rotator_v71a08$_0 = rotator;
    }
  });
  Object.defineProperty(ProjectorProjection.prototype, 'preClip', {
    get: function () {
      return this.preClip_inpaeq$_0;
    },
    set: function (preClip) {
      this.preClip_inpaeq$_0 = preClip;
    }
  });
  Object.defineProperty(ProjectorProjection.prototype, 'postClip', {
    get: function () {
      return this.postClip_9p1oyn$_0;
    },
    set: function (postClip) {
      this.postClip_9p1oyn$_0 = postClip;
    }
  });
  Object.defineProperty(ProjectorProjection.prototype, 'scale', {
    get: function () {
      return this._scale_5kxa7w$_0;
    },
    set: function (value) {
      this._scale_5kxa7w$_0 = value;
      this.recenter_bcrcex$_0();
    }
  });
  Object.defineProperty(ProjectorProjection.prototype, 'translateX', {
    get: function () {
      return this._translateX_bniy78$_0;
    },
    set: function (value) {
      this._translateX_bniy78$_0 = value;
      this.recenter_bcrcex$_0();
    }
  });
  Object.defineProperty(ProjectorProjection.prototype, 'translateY', {
    get: function () {
      return this._translateY_bniy6d$_0;
    },
    set: function (value) {
      this._translateY_bniy6d$_0 = value;
      this.recenter_bcrcex$_0();
    }
  });
  ProjectorProjection.prototype.translate_lu1900$ = function (x, y) {
    this._translateX_bniy78$_0 = x;
    this._translateY_bniy6d$_0 = y;
    this.recenter_bcrcex$_0();
  };
  Object.defineProperty(ProjectorProjection.prototype, 'centerLat', {
    get: function () {
      return get_rad(this._centerLat_qoy1j8$_0);
    },
    set: function (value) {
      this._centerLat_qoy1j8$_0 = value.rad;
      this.recenter_bcrcex$_0();
    }
  });
  Object.defineProperty(ProjectorProjection.prototype, 'centerLon', {
    get: function () {
      return get_rad(this._centerLon_qoybrs$_0);
    },
    set: function (value) {
      this._centerLon_qoybrs$_0 = value.rad;
      this.recenter_bcrcex$_0();
    }
  });
  ProjectorProjection.prototype.center_thqdec$ = function (lat, lon) {
    this._centerLat_qoy1j8$_0 = lat.rad;
    this._centerLon_qoybrs$_0 = lon.rad;
    this.recenter_bcrcex$_0();
  };
  Object.defineProperty(ProjectorProjection.prototype, 'rotateLambda', {
    get: function () {
      return get_rad(this._rotationLambda);
    },
    set: function (value) {
      this._rotationLambda = value.rad;
      this.recenter_bcrcex$_0();
    }
  });
  Object.defineProperty(ProjectorProjection.prototype, 'rotatePhi', {
    get: function () {
      return get_rad(this._rotationPhi);
    },
    set: function (value) {
      this._rotationPhi = value.rad;
      this.recenter_bcrcex$_0();
    }
  });
  Object.defineProperty(ProjectorProjection.prototype, 'rotateGamma', {
    get: function () {
      return get_rad(this._rotationGamma);
    },
    set: function (value) {
      this._rotationGamma = value.rad;
      this.recenter_bcrcex$_0();
    }
  });
  ProjectorProjection.prototype.rotate_u9a0y3$$default = function (lambda, phi, gamma) {
    var tmp$;
    this._rotationLambda = lambda.rad;
    this._rotationPhi = phi.rad;
    this._rotationGamma = (tmp$ = gamma != null ? gamma.rad : null) != null ? tmp$ : 0.0;
    this.recenter_bcrcex$_0();
  };
  Object.defineProperty(ProjectorProjection.prototype, 'precision', {
    get: function () {
      var x = this._precisionDelta2_5zdy8a$_0;
      return Math_0.sqrt(x);
    },
    set: function (value) {
      this._precisionDelta2_5zdy8a$_0 = value * value;
      this.resampleProjector_2l2wd6$_0 = resample(this.translateAndScaleProjector, this._precisionDelta2_5zdy8a$_0);
    }
  });
  ProjectorProjection.prototype.stream_enk0m$ = function (stream) {
    return transformRadians(transformRotate(this.rotator)(this.preClip.clipStream_enk0m$(this.resampleProjector_2l2wd6$_0(this.postClip.clipStream_enk0m$(stream)))));
  };
  ProjectorProjection.prototype.project_lu1900$ = function (lambda, phi) {
    var lambdaRadians = toRadians(lambda);
    var phiRadians = toRadians(phi);
    return this.composedTransformationsProjector.project_lu1900$(lambdaRadians, phiRadians);
  };
  ProjectorProjection.prototype.invert_lu1900$ = function (x, y) {
    var inverted = this.composedTransformationsProjector.invert_lu1900$(x, y);
    return new Float64Array([toDegrees(inverted[0]), toDegrees(inverted[1])]);
  };
  ProjectorProjection.prototype.recenter_bcrcex$_0 = function () {
    this.rotator = createRotateRadiansProjector(this._rotationLambda, this._rotationPhi, this._rotationGamma);
    this.composedTransformationsProjector = new ComposedProjector(this.rotator, this.translateAndScaleProjector);
    var projectedCenter = this.projector.project_lu1900$(this._centerLat_qoy1j8$_0, this._centerLon_qoybrs$_0);
    this._recenterDx_ekovbq$_0 = this.translateX - projectedCenter[0] * this._scale_5kxa7w$_0;
    this._recenterDy_ekovav$_0 = this.translateY + projectedCenter[1] * this._scale_5kxa7w$_0;
    this.translateAndScaleProjector.scale = this._scale_5kxa7w$_0;
    this.translateAndScaleProjector.recenterDx = this._recenterDx_ekovbq$_0;
    this.translateAndScaleProjector.recenterDy = this._recenterDy_ekovav$_0;
  };
  ProjectorProjection.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ProjectorProjection',
    interfaces: [Projection]
  };
  function TranslateAndScaleProjector(projector, scale, recenterDx, recenterDy) {
    this.projector = projector;
    this.scale = scale;
    this.recenterDx = recenterDx;
    this.recenterDy = recenterDy;
  }
  TranslateAndScaleProjector.prototype.project_lu1900$ = function (lambda, phi) {
    var projected = this.projector.project_lu1900$(lambda, phi);
    projected[0] = this.recenterDx + projected[0] * this.scale;
    projected[1] = this.recenterDy - projected[1] * this.scale;
    return projected;
  };
  TranslateAndScaleProjector.prototype.invert_lu1900$ = function (x, y) {
    return this.projector.invert_lu1900$((x - this.recenterDx) / this.scale, -(y - this.recenterDy) / this.scale);
  };
  TranslateAndScaleProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'TranslateAndScaleProjector',
    interfaces: [Projector]
  };
  var MAX_DEPTH;
  var COS_MIN_DISTANCE;
  function resample$lambda(closure$projector, closure$delta2Precision) {
    return function (stream) {
      return new ResampleStream(stream, closure$projector, closure$delta2Precision);
    };
  }
  function resample(projector, delta2Precision) {
    return delta2Precision !== 0.0 ? resample$lambda(projector, delta2Precision) : resampleNone(projector);
  }
  function ResampleStream(stream, projector, delta2Precision) {
    if (delta2Precision === void 0)
      delta2Precision = 0.5;
    this.stream = stream;
    this.projector = projector;
    this.delta2Precision = delta2Precision;
    this.pointContext = ResampleStream$PointContext$DEFAULT_getInstance();
    this.lineStartContext = ResampleStream$LineStartContext$DEFAULT_getInstance();
    this.lineEndContext = ResampleStream$LineEndContext$DEFAULT_getInstance();
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
  }
  function ResampleStream$LineStartContext(name, ordinal) {
    Enum.call(this);
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function ResampleStream$LineStartContext_initFields() {
    ResampleStream$LineStartContext_initFields = function () {
    };
    ResampleStream$LineStartContext$DEFAULT_instance = new ResampleStream$LineStartContext('DEFAULT', 0);
    ResampleStream$LineStartContext$POLYGON_instance = new ResampleStream$LineStartContext('POLYGON', 1);
  }
  var ResampleStream$LineStartContext$DEFAULT_instance;
  function ResampleStream$LineStartContext$DEFAULT_getInstance() {
    ResampleStream$LineStartContext_initFields();
    return ResampleStream$LineStartContext$DEFAULT_instance;
  }
  var ResampleStream$LineStartContext$POLYGON_instance;
  function ResampleStream$LineStartContext$POLYGON_getInstance() {
    ResampleStream$LineStartContext_initFields();
    return ResampleStream$LineStartContext$POLYGON_instance;
  }
  ResampleStream$LineStartContext.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'LineStartContext',
    interfaces: [Enum]
  };
  function ResampleStream$LineStartContext$values() {
    return [ResampleStream$LineStartContext$DEFAULT_getInstance(), ResampleStream$LineStartContext$POLYGON_getInstance()];
  }
  ResampleStream$LineStartContext.values = ResampleStream$LineStartContext$values;
  function ResampleStream$LineStartContext$valueOf(name) {
    switch (name) {
      case 'DEFAULT':
        return ResampleStream$LineStartContext$DEFAULT_getInstance();
      case 'POLYGON':
        return ResampleStream$LineStartContext$POLYGON_getInstance();
      default:throwISE('No enum constant io.data2viz.geo.projection.common.ResampleStream.LineStartContext.' + name);
    }
  }
  ResampleStream$LineStartContext.valueOf_61zpoe$ = ResampleStream$LineStartContext$valueOf;
  function ResampleStream$LineEndContext(name, ordinal) {
    Enum.call(this);
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function ResampleStream$LineEndContext_initFields() {
    ResampleStream$LineEndContext_initFields = function () {
    };
    ResampleStream$LineEndContext$DEFAULT_instance = new ResampleStream$LineEndContext('DEFAULT', 0);
    ResampleStream$LineEndContext$POLYGON_instance = new ResampleStream$LineEndContext('POLYGON', 1);
  }
  var ResampleStream$LineEndContext$DEFAULT_instance;
  function ResampleStream$LineEndContext$DEFAULT_getInstance() {
    ResampleStream$LineEndContext_initFields();
    return ResampleStream$LineEndContext$DEFAULT_instance;
  }
  var ResampleStream$LineEndContext$POLYGON_instance;
  function ResampleStream$LineEndContext$POLYGON_getInstance() {
    ResampleStream$LineEndContext_initFields();
    return ResampleStream$LineEndContext$POLYGON_instance;
  }
  ResampleStream$LineEndContext.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'LineEndContext',
    interfaces: [Enum]
  };
  function ResampleStream$LineEndContext$values() {
    return [ResampleStream$LineEndContext$DEFAULT_getInstance(), ResampleStream$LineEndContext$POLYGON_getInstance()];
  }
  ResampleStream$LineEndContext.values = ResampleStream$LineEndContext$values;
  function ResampleStream$LineEndContext$valueOf(name) {
    switch (name) {
      case 'DEFAULT':
        return ResampleStream$LineEndContext$DEFAULT_getInstance();
      case 'POLYGON':
        return ResampleStream$LineEndContext$POLYGON_getInstance();
      default:throwISE('No enum constant io.data2viz.geo.projection.common.ResampleStream.LineEndContext.' + name);
    }
  }
  ResampleStream$LineEndContext.valueOf_61zpoe$ = ResampleStream$LineEndContext$valueOf;
  function ResampleStream$PointContext(name, ordinal) {
    Enum.call(this);
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function ResampleStream$PointContext_initFields() {
    ResampleStream$PointContext_initFields = function () {
    };
    ResampleStream$PointContext$DEFAULT_instance = new ResampleStream$PointContext('DEFAULT', 0);
    ResampleStream$PointContext$POLYGON_instance = new ResampleStream$PointContext('POLYGON', 1);
    ResampleStream$PointContext$LINE_instance = new ResampleStream$PointContext('LINE', 2);
  }
  var ResampleStream$PointContext$DEFAULT_instance;
  function ResampleStream$PointContext$DEFAULT_getInstance() {
    ResampleStream$PointContext_initFields();
    return ResampleStream$PointContext$DEFAULT_instance;
  }
  var ResampleStream$PointContext$POLYGON_instance;
  function ResampleStream$PointContext$POLYGON_getInstance() {
    ResampleStream$PointContext_initFields();
    return ResampleStream$PointContext$POLYGON_instance;
  }
  var ResampleStream$PointContext$LINE_instance;
  function ResampleStream$PointContext$LINE_getInstance() {
    ResampleStream$PointContext_initFields();
    return ResampleStream$PointContext$LINE_instance;
  }
  ResampleStream$PointContext.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'PointContext',
    interfaces: [Enum]
  };
  function ResampleStream$PointContext$values() {
    return [ResampleStream$PointContext$DEFAULT_getInstance(), ResampleStream$PointContext$POLYGON_getInstance(), ResampleStream$PointContext$LINE_getInstance()];
  }
  ResampleStream$PointContext.values = ResampleStream$PointContext$values;
  function ResampleStream$PointContext$valueOf(name) {
    switch (name) {
      case 'DEFAULT':
        return ResampleStream$PointContext$DEFAULT_getInstance();
      case 'POLYGON':
        return ResampleStream$PointContext$POLYGON_getInstance();
      case 'LINE':
        return ResampleStream$PointContext$LINE_getInstance();
      default:throwISE('No enum constant io.data2viz.geo.projection.common.ResampleStream.PointContext.' + name);
    }
  }
  ResampleStream$PointContext.valueOf_61zpoe$ = ResampleStream$PointContext$valueOf;
  ResampleStream.prototype.polygonStart = function () {
    this.stream.polygonStart();
    this.lineStartContext = ResampleStream$LineStartContext$POLYGON_getInstance();
  };
  ResampleStream.prototype.polygonEnd = function () {
    this.stream.polygonEnd();
    this.lineStartContext = ResampleStream$LineStartContext$DEFAULT_getInstance();
  };
  ResampleStream.prototype.lineStart = function () {
    switch (this.lineStartContext.name) {
      case 'POLYGON':
        this.lineStartPolygon();
        break;
      case 'DEFAULT':
        this.lineStartDefault();
        break;
    }
  };
  ResampleStream.prototype.lineStartPolygon = function () {
    this.lineStartDefault();
    this.pointContext = ResampleStream$PointContext$POLYGON_getInstance();
    this.lineEndContext = ResampleStream$LineEndContext$POLYGON_getInstance();
  };
  ResampleStream.prototype.lineStartDefault = function () {
    this.x0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.pointContext = ResampleStream$PointContext$LINE_getInstance();
    this.stream.lineStart();
  };
  ResampleStream.prototype.lineEnd = function () {
    switch (this.lineEndContext.name) {
      case 'POLYGON':
        this.lineEndPolygon();
        break;
      case 'DEFAULT':
        this.lineEndDefault();
        break;
    }
  };
  ResampleStream.prototype.point_yvo9jy$ = function (x, y, z) {
    switch (this.pointContext.name) {
      case 'POLYGON':
        this.pointPolygon_yvo9jy$(x, y, z);
        break;
      case 'LINE':
        this.pointLine_yvo9jy$(x, y, z);
        break;
      case 'DEFAULT':
        this.pointDefault_yvo9jy$(x, y, z);
        break;
    }
  };
  ResampleStream.prototype.lineEndDefault = function () {
    this.pointContext = ResampleStream$PointContext$DEFAULT_getInstance();
    this.stream.lineEnd();
  };
  ResampleStream.prototype.lineEndPolygon = function () {
    this.resampleLineTo_xx7crp$(this.x0, this.y0, this.lambda0, this.a0, this.b0, this.c0, this.x00, this.y00, this.lambda00, this.a00, this.b00, this.c00, 16, this.stream);
    this.lineEndContext = ResampleStream$LineEndContext$DEFAULT_getInstance();
    this.lineEnd();
  };
  ResampleStream.prototype.pointPolygon_yvo9jy$ = function (lambda, phi, alt) {
    this.lambda00 = lambda;
    this.pointLine_yvo9jy$(lambda, phi, alt);
    this.x00 = this.x0;
    this.y00 = this.y0;
    this.a00 = this.a0;
    this.b00 = this.b0;
    this.c00 = this.c0;
    this.pointContext = ResampleStream$PointContext$LINE_getInstance();
  };
  ResampleStream.prototype.pointLine_yvo9jy$ = function (lambda, phi, alt) {
    var radiusAtLat = Math_0.cos(phi);
    var dz = radiusAtLat * Math_0.cos(lambda);
    var dx = radiusAtLat * Math_0.sin(lambda);
    var dy = Math_0.sin(phi);
    var projected = this.projector.project_lu1900$(lambda, phi);
    var p0 = projected[0];
    var p1 = projected[1];
    this.resampleLineTo_xx7crp$(this.x0, this.y0, this.lambda0, this.a0, this.b0, this.c0, p0, p1, lambda, dz, dx, dy, 16, this.stream);
    this.x0 = p0;
    this.y0 = p1;
    this.lambda0 = lambda;
    this.a0 = dz;
    this.b0 = dx;
    this.c0 = dy;
    this.stream.point_yvo9jy$(this.x0, this.y0, alt);
  };
  ResampleStream.prototype.pointDefault_yvo9jy$ = function (lambda, phi, alt) {
    var projected = this.projector.project_lu1900$(lambda, phi);
    this.stream.point_yvo9jy$(projected[0], projected[1], alt);
  };
  ResampleStream.prototype.resampleLineTo_xx7crp$ = function (x0, y0, lambda0, a0, b0, c0, x1, y1, lambda1, a1, b1, c1, depth, stream) {
    var tmp$;
    var dx = x1 - x0;
    var dy = y1 - y0;
    var d2 = dx * dx + dy * dy;
    if (d2 > 4 * this.delta2Precision && depth > 0) {
      var newDepth = depth - 1 | 0;
      var a = a0 + a1;
      var b = b0 + b1;
      var c = c0 + c1;
      var x = a * a + b * b + c * c;
      var m = Math_0.sqrt(x);
      c /= m;
      var phi2 = get_limitedAsin(c);
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
      var projected2 = this.projector.project_lu1900$(lambda2, phi2);
      var x2 = projected2[0];
      var y2 = projected2[1];
      var dx2 = x2 - x0;
      var dy2 = y2 - y0;
      var dz = dy * dx2 - dx * dy2;
      var tmp$_1 = dz * dz / d2 > this.delta2Precision;
      if (!tmp$_1) {
        var x_2 = (dx * dx2 + dy * dy2) / d2 - 0.5;
        tmp$_1 = Math_0.abs(x_2) > 0.3;
      }
      if (tmp$_1 || a0 * a1 + b0 * b1 + c0 * c1 < COS_MIN_DISTANCE) {
        a /= m;
        b /= m;
        this.resampleLineTo_xx7crp$(x0, y0, lambda0, a0, b0, c0, x2, y2, lambda2, a, b, c, newDepth, stream);
        stream.point_yvo9jy$(x2, y2, 0.0);
        this.resampleLineTo_xx7crp$(x2, y2, lambda2, a, b, c, x1, y1, lambda1, a1, b1, c1, newDepth, stream);
      }
    }
  };
  ResampleStream.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ResampleStream',
    interfaces: [Stream]
  };
  function resampleNone$lambda$ObjectLiteral(closure$projector, closure$stream, delegate) {
    this.closure$projector = closure$projector;
    this.closure$stream = closure$stream;
    DelegateStreamAdapter.call(this, delegate);
  }
  resampleNone$lambda$ObjectLiteral.prototype.point_yvo9jy$ = function (x, y, z) {
    var projected = this.closure$projector.project_lu1900$(x, y);
    this.closure$stream.point_yvo9jy$(projected[0], projected[1], z);
  };
  resampleNone$lambda$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [DelegateStreamAdapter]
  };
  function resampleNone$lambda(closure$projector) {
    return function (stream) {
      return new resampleNone$lambda$ObjectLiteral(closure$projector, stream, stream);
    };
  }
  function resampleNone(projector) {
    return resampleNone$lambda(projector);
  }
  function RotationProjector(lambda, phi, gamma) {
    if (gamma === void 0)
      gamma = null;
    var tmp$;
    this.rotator = createRotateRadiansProjector(lambda.rad, phi.rad, (tmp$ = gamma != null ? gamma.rad : null) != null ? tmp$ : 0.0);
  }
  RotationProjector.prototype.project_lu1900$ = function (lambda, phi) {
    var p = this.rotator.project_lu1900$(toRadians(lambda), toRadians(phi));
    return new Float64Array([toDegrees(p[0]), toDegrees(p[1])]);
  };
  RotationProjector.prototype.invert_lu1900$ = function (x, y) {
    var p = this.rotator.invert_lu1900$(toRadians(x), toRadians(y));
    return new Float64Array([toDegrees(p[0]), toDegrees(p[1])]);
  };
  RotationProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'RotationProjector',
    interfaces: [Projector]
  };
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
  function IdentityRotationProjector() {
    IdentityRotationProjector_instance = this;
  }
  IdentityRotationProjector.prototype.project_lu1900$ = function (lambda, phi) {
    return new Float64Array([identityProjectionX(lambda), identityProjectionY(phi)]);
  };
  IdentityRotationProjector.prototype.invert_lu1900$ = function (x, y) {
    return new Float64Array([identityProjectionX(x), identityProjectionY(y)]);
  };
  IdentityRotationProjector.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'IdentityRotationProjector',
    interfaces: [Projector]
  };
  var IdentityRotationProjector_instance = null;
  function IdentityRotationProjector_getInstance() {
    if (IdentityRotationProjector_instance === null) {
      new IdentityRotationProjector();
    }
    return IdentityRotationProjector_instance;
  }
  function RotationLambdaProjector(deltaLambda) {
    this.deltaLambda = deltaLambda;
  }
  RotationLambdaProjector.prototype.project_lu1900$ = function (lambda, phi) {
    return new Float64Array([identityProjectionX(lambda + this.deltaLambda), identityProjectionY(phi)]);
  };
  RotationLambdaProjector.prototype.invert_lu1900$ = function (x, y) {
    return new Float64Array([identityProjectionX(x - this.deltaLambda), identityProjectionY(y)]);
  };
  RotationLambdaProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'RotationLambdaProjector',
    interfaces: [Projector]
  };
  function RotationPhiGammaProjector(deltaPhi, deltaGamma) {
    this.cosDeltaPhi_0 = Math_0.cos(deltaPhi);
    this.sinDeltaPhi_0 = Math_0.sin(deltaPhi);
    this.cosDeltaGamma_0 = Math_0.cos(deltaGamma);
    this.sinDeltaGamma_0 = Math_0.sin(deltaGamma);
  }
  RotationPhiGammaProjector.prototype.project_lu1900$ = function (lambda, phi) {
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
  RotationPhiGammaProjector.prototype.invert_lu1900$ = function (x, y) {
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
  RotationPhiGammaProjector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'RotationPhiGammaProjector',
    interfaces: [Projector]
  };
  function createRotateRadiansProjector(deltaLambda, deltaPhi, deltaGamma) {
    var tmp$;
    var newDeltaLambda = deltaLambda % math.TAU;
    var atLeastOneSecondaryAngleIsZero = deltaPhi !== 0.0 || deltaGamma !== 0.0;
    if (newDeltaLambda !== 0.0)
      if (atLeastOneSecondaryAngleIsZero) {
        tmp$ = new ComposedProjector(new RotationLambdaProjector(deltaLambda), new RotationPhiGammaProjector(deltaPhi, deltaGamma));
      }
       else
        tmp$ = new RotationLambdaProjector(deltaLambda);
    else if (atLeastOneSecondaryAngleIsZero)
      tmp$ = new RotationPhiGammaProjector(deltaPhi, deltaGamma);
    else
      tmp$ = IdentityRotationProjector_getInstance();
    return tmp$;
  }
  function DelegateStreamAdapter(delegate) {
    this.delegate = delegate;
  }
  DelegateStreamAdapter.prototype.point_yvo9jy$ = function (x, y, z) {
    this.delegate.point_yvo9jy$(x, y, z);
  };
  DelegateStreamAdapter.prototype.lineStart = function () {
    this.delegate.lineStart();
  };
  DelegateStreamAdapter.prototype.lineEnd = function () {
    this.delegate.lineEnd();
  };
  DelegateStreamAdapter.prototype.polygonStart = function () {
    this.delegate.polygonStart();
  };
  DelegateStreamAdapter.prototype.polygonEnd = function () {
    this.delegate.polygonEnd();
  };
  DelegateStreamAdapter.prototype.sphere = function () {
    this.delegate.sphere();
  };
  DelegateStreamAdapter.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'DelegateStreamAdapter',
    interfaces: [Stream]
  };
  function MultiplexStream(streams) {
    this.streams_0 = streams;
  }
  MultiplexStream.prototype.point_yvo9jy$ = function (x, y, z) {
    var tmp$;
    tmp$ = this.streams_0.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      element.point_yvo9jy$(x, y, z);
    }
  };
  MultiplexStream.prototype.lineStart = function () {
    var tmp$;
    tmp$ = this.streams_0.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      element.lineStart();
    }
  };
  MultiplexStream.prototype.lineEnd = function () {
    var tmp$;
    tmp$ = this.streams_0.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      element.lineEnd();
    }
  };
  MultiplexStream.prototype.polygonStart = function () {
    var tmp$;
    tmp$ = this.streams_0.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      element.polygonStart();
    }
  };
  MultiplexStream.prototype.polygonEnd = function () {
    var tmp$;
    tmp$ = this.streams_0.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      element.polygonEnd();
    }
  };
  MultiplexStream.prototype.sphere = function () {
    var tmp$;
    tmp$ = this.streams_0.iterator();
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
  function StreamCache() {
    this.cachedResultStream_qcpxgl$_0 = null;
    this.originalSourceStream_klfcbc$_0 = null;
  }
  StreamCache.prototype.isCacheValidFor_enk0m$ = function (originalStream) {
    return equals(this.originalSourceStream, originalStream);
  };
  Object.defineProperty(StreamCache.prototype, 'cachedResultStream', {
    get: function () {
      return this.cachedResultStream_qcpxgl$_0;
    },
    set: function (cachedResultStream) {
      this.cachedResultStream_qcpxgl$_0 = cachedResultStream;
    }
  });
  Object.defineProperty(StreamCache.prototype, 'originalSourceStream', {
    get: function () {
      return this.originalSourceStream_klfcbc$_0;
    },
    set: function (originalSourceStream) {
      this.originalSourceStream_klfcbc$_0 = originalSourceStream;
    }
  });
  StreamCache.prototype.cache_ln4vjc$ = function (originalStream, resultStream) {
    this.originalSourceStream = originalStream;
    this.cachedResultStream = resultStream;
  };
  StreamCache.prototype.reset = function () {
    this.cachedResultStream = null;
    this.originalSourceStream = null;
  };
  StreamCache.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'StreamCache',
    interfaces: []
  };
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$geo = package$data2viz.geo || (package$data2viz.geo = {});
  package$geo.get_lambda_bvy38s$ = get_lambda;
  package$geo.get_phi_bvy38s$ = get_phi;
  package$geo.get_alt_bvy38s$ = get_alt_0;
  package$geo.component1_bvy38s$ = component1;
  package$geo.component2_bvy38s$ = component2;
  package$geo.component3_bvy38s$ = component3;
  package$geo.GeoPoint_ft7ehv$ = GeoPoint;
  var package$geojson = package$geo.geojson || (package$geo.geojson = {});
  package$geojson.GeoInterpolate = GeoInterpolate;
  package$geojson.geoInterpolate_g9g6do$ = geoInterpolate;
  package$geojson.Sphere = Sphere;
  package$geojson.contains_xahfjx$ = contains;
  package$geojson.get_pos_1johxh$ = get_pos;
  package$geojson.get_positions_qhg4rg$ = get_positions;
  package$geojson.get_positions_7kdjta$ = get_positions_0;
  package$geojson.get_lines_6ve7yp$ = get_lines;
  package$geojson.get_lines_c6hz8z$ = get_lines_0;
  package$geojson.get_surface_oab6gi$ = get_surface;
  Object.defineProperty(package$geojson, 'noop_8be2vx$', {
    get: function () {
      return noop;
    }
  });
  Object.defineProperty(package$geojson, 'noop2_8be2vx$', {
    get: function () {
      return noop2;
    }
  });
  Object.defineProperty(package$geojson, 'noop3_8be2vx$', {
    get: function () {
      return noop3;
    }
  });
  package$geojson.stream_31omfd$ = stream;
  package$geojson.toRadians_awc180$ = toRadians_0;
  package$geojson.geoPath_v6g2nn$ = geoPath;
  package$geojson.GeoPath = GeoPath;
  package$geojson.fitExtent_3ihp2l$ = fitExtent;
  package$geojson.fitWidth_fby6bu$ = fitWidth;
  package$geojson.fitHeight_fby6bu$ = fitHeight;
  package$geojson.fitSize_jkqde0$ = fitSize;
  var package$path = package$geojson.path || (package$geojson.path = {});
  package$path.geoArea_6ux19g$ = geoArea;
  package$path.GeoAreaStream = GeoAreaStream;
  package$path.geoBounds_6ux19g$ = geoBounds;
  package$path.GeoBoundsStream = GeoBoundsStream;
  package$path.geoCentroid_6ux19g$ = geoCentroid;
  package$path.GeoCentroidStream = GeoCentroidStream;
  package$path.GeoCircle = GeoCircle;
  package$path.geoCircle_n5kdao$ = geoCircle;
  package$path.geoDistance_8efa2c$ = geoDistance;
  package$path.geoLength_6ux19g$ = geoLength;
  package$path.GeoLengthStream = GeoLengthStream;
  var package$geometry = package$geo.geometry || (package$geo.geometry = {});
  package$geometry.spherical_gf7tl1$ = spherical;
  package$geometry.cartesian_gf7tl1$ = cartesian;
  package$geometry.cartesianDot_g9g6do$ = cartesianDot;
  package$geometry.cartesianCross_g9g6do$ = cartesianCross;
  package$geometry.cartesianScale_pw9xcd$ = cartesianScale;
  package$geometry.cartesianAdd_g9g6do$ = cartesianAdd;
  package$geometry.cartesianNormalize_gf7tl1$ = cartesianNormalize;
  package$geometry.geoGraticule_2j84pz$ = geoGraticule;
  package$geometry.Graticule = Graticule;
  package$geometry.get_limitedAsin_yrwdxr$ = get_limitedAsin;
  package$geometry.get_asin_yrwdxr$ = get_asin;
  package$geometry.get_acos_yrwdxr$ = get_acos;
  package$geometry.get_limitedAcos_yrwdxr$ = get_limitedAcos;
  package$geometry.polygonContains_5i3gmk$ = polygonContains;
  package$geometry.quaternion_gf7tl1$ = quaternion;
  package$geometry.eulerRotation_gf7tl1$ = eulerRotation;
  package$geometry.quaternionDelta_qxkboe$ = quaternionDelta;
  package$geometry.quaternionMultiply_g9g6do$ = quaternionMultiply;
  var package$clip = package$geometry.clip || (package$geometry.clip = {});
  package$clip.get_anglePreClip_fd4uyw$ = get_anglePreClip;
  package$clip.set_anglePreClip_eri4ml$ = set_anglePreClip;
  Object.defineProperty(package$clip, 'antimeridianPreClip', {
    get: function () {
      return antimeridianPreClip;
    }
  });
  package$clip.CirclePreClip = CirclePreClip;
  package$clip.ClipCircle = ClipCircle;
  Object.defineProperty(package$clip, 'NoClip', {
    get: function () {
      return NoClip;
    }
  });
  package$clip.StreamClip = StreamClip;
  package$clip.ClipStream = ClipStream;
  package$clip.Clippable = Clippable;
  package$clip.ClippableHasStart = ClippableHasStart;
  Object.defineProperty(ClippableStream$LineStartContext, 'DEFAULT', {
    get: ClippableStream$LineStartContext$DEFAULT_getInstance
  });
  Object.defineProperty(ClippableStream$LineStartContext, 'POLYGON', {
    get: ClippableStream$LineStartContext$POLYGON_getInstance
  });
  ClippableStream.LineStartContext = ClippableStream$LineStartContext;
  Object.defineProperty(ClippableStream$LineEndContext, 'DEFAULT', {
    get: ClippableStream$LineEndContext$DEFAULT_getInstance
  });
  Object.defineProperty(ClippableStream$LineEndContext, 'POLYGON', {
    get: ClippableStream$LineEndContext$POLYGON_getInstance
  });
  ClippableStream.LineEndContext = ClippableStream$LineEndContext;
  Object.defineProperty(ClippableStream$PointContext, 'DEFAULT', {
    get: ClippableStream$PointContext$DEFAULT_getInstance
  });
  Object.defineProperty(ClippableStream$PointContext, 'POLYGON', {
    get: ClippableStream$PointContext$POLYGON_getInstance
  });
  Object.defineProperty(ClippableStream$PointContext, 'LINE', {
    get: ClippableStream$PointContext$LINE_getInstance
  });
  ClippableStream.PointContext = ClippableStream$PointContext;
  Object.defineProperty(ClippableStream, 'LinePointFunction', {
    get: ClippableStream$LinePointFunction_getInstance
  });
  Object.defineProperty(ClippableStream, 'PointRingPointFunction', {
    get: ClippableStream$PointRingPointFunction_getInstance
  });
  package$clip.ClippableStream = ClippableStream;
  package$clip.ClipBufferStream = ClipBufferStream;
  package$clip.ExtentClip = ExtentClip;
  package$clip.get_extentPostClip_fd4uyw$ = get_extentPostClip;
  package$clip.set_extentPostClip_jnn3dq$ = set_extentPostClip;
  package$clip.RectangleClip = RectangleClip;
  package$clip.ClipRectangle = ClipRectangle;
  package$clip.Intersection = Intersection;
  package$clip.InterpolateFunction = InterpolateFunction;
  package$clip.rejoin_9tot0n$ = rejoin;
  package$clip.link_m5864$ = link;
  package$clip.pointEqual_6dypfz$ = pointEqual;
  var package$path_0 = package$geometry.path || (package$geometry.path = {});
  package$path_0.AreaStream = AreaStream;
  package$path_0.BoundsStream = BoundsStream;
  package$path_0.CentroidStream = CentroidStream;
  package$path_0.MeasureStream = MeasureStream;
  Object.defineProperty(PathStream$PathCmd, 'MOVE', {
    get: PathStream$PathCmd$MOVE_getInstance
  });
  Object.defineProperty(PathStream$PathCmd, 'LINE', {
    get: PathStream$PathCmd$LINE_getInstance
  });
  Object.defineProperty(PathStream$PathCmd, 'POINT', {
    get: PathStream$PathCmd$POINT_getInstance
  });
  PathStream.PathCmd = PathStream$PathCmd;
  package$path_0.PathStream = PathStream;
  var package$projection = package$geo.projection || (package$geo.projection = {});
  package$projection.albersProjection_2xcl47$ = albersProjection;
  package$projection.albersUSAProjection_zfb0q7$ = albersUSAProjection;
  package$projection.AlbersUSAProjection = AlbersUSAProjection;
  package$projection.azimuthalInvert_7fnk9s$ = azimuthalInvert;
  package$projection.Azimuthal = Azimuthal;
  package$projection.azimuthalEqualAreaProjection_nta9te$ = azimuthalEqualAreaProjection;
  package$projection.azimuthalEquidistant_nta9te$ = azimuthalEquidistant;
  package$projection.AzimuthalEquidistantProjection = AzimuthalEquidistantProjection;
  package$projection.conicProjection_yfhd1y$ = conicProjection;
  package$projection.ConicProjector = ConicProjector;
  package$projection.ConicProjection = ConicProjection;
  package$projection.conicConformalProjection_2xcl47$ = conicConformalProjection;
  package$projection.tany_tq0o01$ = tany;
  package$projection.ConicConformalBaseConditionalProjector = ConicConformalBaseConditionalProjector;
  package$projection.ConicConformalProjector = ConicConformalProjector;
  package$projection.conicEqualAreaProjection_2xcl47$ = conicEqualAreaProjection;
  package$projection.ConicEqualAreaBaseConditionalProjector = ConicEqualAreaBaseConditionalProjector;
  package$projection.ConicEqualAreaProjector = ConicEqualAreaProjector;
  package$projection.conicEquidistantProjection_2xcl47$ = conicEquidistantProjection;
  package$projection.ConicEquidistantBaseConditionalProjector = ConicEquidistantBaseConditionalProjector;
  package$projection.ConicEquidistantProjector = ConicEquidistantProjector;
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
  package$projection.equalEarthProjection_ey98sg$ = equalEarthProjection;
  package$projection.EqualEarthProjector = EqualEarthProjector;
  package$projection.equirectangularProjection_ey98sg$ = equirectangularProjection;
  package$projection.EquirectangularProjector = EquirectangularProjector;
  package$projection.gnomonicProjection_ey98sg$ = gnomonicProjection;
  package$projection.GnomonicProjector = GnomonicProjector;
  package$projection.identityProjection_ey98sg$ = identityProjection;
  package$projection.IdentityProjection = IdentityProjection;
  package$projection.mercatorProjection_ey98sg$ = mercatorProjection;
  package$projection.MercatorProjector = MercatorProjector;
  package$projection.MercatorProjection = MercatorProjection;
  package$projection.naturalEarthProjection_ey98sg$ = naturalEarthProjection;
  package$projection.NaturalEarthProjection = NaturalEarthProjection;
  package$projection.orthographicProjection_ey98sg$ = orthographicProjection;
  package$projection.OrthographicProjector = OrthographicProjector;
  package$projection.stereographicProjection_ey98sg$ = stereographicProjection;
  package$projection.StereographicProjector = StereographicProjector;
  package$projection.transverseMercatorProjection_4mndx7$ = transverseMercatorProjection;
  package$projection.TransverseMercatorProjector = TransverseMercatorProjector;
  package$projection.TransverseMercatorProjection = TransverseMercatorProjection;
  var package$common = package$projection.common || (package$projection.common = {});
  package$common.CachedProjection = CachedProjection;
  package$common.ComposedProjection = ComposedProjection;
  package$common.ComposedProjector = ComposedProjector;
  package$common.ConditionalProjector = ConditionalProjector;
  package$common.BaseConditionalProjector = BaseConditionalProjector;
  package$common.Projection = Projection;
  package$common.Projectable = Projectable;
  package$common.Invertable = Invertable;
  package$common.Projector = Projector;
  package$common.projection_yo5q62$ = projection;
  package$common.ProjectorProjection = ProjectorProjection;
  package$common.TranslateAndScaleProjector = TranslateAndScaleProjector;
  Object.defineProperty(package$common, 'MAX_DEPTH', {
    get: function () {
      return MAX_DEPTH;
    }
  });
  Object.defineProperty(package$common, 'COS_MIN_DISTANCE', {
    get: function () {
      return COS_MIN_DISTANCE;
    }
  });
  package$common.resample_5nr37q$ = resample;
  package$common.RotationProjector = RotationProjector;
  Object.defineProperty(package$common, 'IdentityRotationProjector', {
    get: IdentityRotationProjector_getInstance
  });
  package$common.RotationLambdaProjector = RotationLambdaProjector;
  package$common.RotationPhiGammaProjector = RotationPhiGammaProjector;
  package$common.createRotateRadiansProjector_hln2n9$ = createRotateRadiansProjector;
  var package$stream = package$geo.stream || (package$geo.stream = {});
  package$stream.DelegateStreamAdapter = DelegateStreamAdapter;
  package$stream.MultiplexStream = MultiplexStream;
  package$stream.Stream = Stream;
  package$stream.StreamCache = StreamCache;
  GeoBoundsStream.prototype.sphere = Stream.prototype.sphere;
  GeoCentroidStream.prototype.sphere = Stream.prototype.sphere;
  GeoCircle$circleStream$ObjectLiteral.prototype.lineEnd = Stream.prototype.lineEnd;
  GeoCircle$circleStream$ObjectLiteral.prototype.lineStart = Stream.prototype.lineStart;
  GeoCircle$circleStream$ObjectLiteral.prototype.polygonEnd = Stream.prototype.polygonEnd;
  GeoCircle$circleStream$ObjectLiteral.prototype.polygonStart = Stream.prototype.polygonStart;
  GeoCircle$circleStream$ObjectLiteral.prototype.sphere = Stream.prototype.sphere;
  GeoLengthStream.prototype.polygonStart = Stream.prototype.polygonStart;
  GeoLengthStream.prototype.polygonEnd = Stream.prototype.polygonEnd;
  GeoLengthStream.prototype.sphere = Stream.prototype.sphere;
  ClipStream.prototype.lineEnd = Stream.prototype.lineEnd;
  ClipStream.prototype.lineStart = Stream.prototype.lineStart;
  ClipStream.prototype.point_yvo9jy$ = Stream.prototype.point_yvo9jy$;
  ClipStream.prototype.polygonEnd = Stream.prototype.polygonEnd;
  ClipStream.prototype.polygonStart = Stream.prototype.polygonStart;
  ClipStream.prototype.sphere = Stream.prototype.sphere;
  AntimeridianClip$clipLine$ObjectLiteral.prototype.polygonEnd = ClipStream.prototype.polygonEnd;
  AntimeridianClip$clipLine$ObjectLiteral.prototype.polygonStart = ClipStream.prototype.polygonStart;
  AntimeridianClip$clipLine$ObjectLiteral.prototype.sphere = ClipStream.prototype.sphere;
  ClipCircle$clipLine$ObjectLiteral.prototype.polygonEnd = ClipStream.prototype.polygonEnd;
  ClipCircle$clipLine$ObjectLiteral.prototype.polygonStart = ClipStream.prototype.polygonStart;
  ClipCircle$clipLine$ObjectLiteral.prototype.sphere = ClipStream.prototype.sphere;
  ClipBufferStream.prototype.lineEnd = Stream.prototype.lineEnd;
  ClipBufferStream.prototype.polygonEnd = Stream.prototype.polygonEnd;
  ClipBufferStream.prototype.polygonStart = Stream.prototype.polygonStart;
  ClipBufferStream.prototype.sphere = Stream.prototype.sphere;
  ClipRectangle$clipLine$ObjectLiteral.prototype.sphere = ClipStream.prototype.sphere;
  AreaStream.prototype.sphere = Stream.prototype.sphere;
  BoundsStream.prototype.lineStart = Stream.prototype.lineStart;
  BoundsStream.prototype.lineEnd = Stream.prototype.lineEnd;
  BoundsStream.prototype.polygonStart = Stream.prototype.polygonStart;
  BoundsStream.prototype.polygonEnd = Stream.prototype.polygonEnd;
  BoundsStream.prototype.sphere = Stream.prototype.sphere;
  CentroidStream.prototype.sphere = Stream.prototype.sphere;
  MeasureStream.prototype.sphere = Stream.prototype.sphere;
  PathStream.prototype.sphere = Stream.prototype.sphere;
  ComposedProjection.prototype.rotate_u9a0y3$ = Projection.prototype.rotate_u9a0y3$;
  ProjectorProjection.prototype.rotate_u9a0y3$ = Projection.prototype.rotate_u9a0y3$;
  CachedProjection.prototype.rotate_u9a0y3$ = Projection.prototype.rotate_u9a0y3$;
  ResampleStream.prototype.sphere = Stream.prototype.sphere;
  noop = noop$lambda;
  noop2 = noop2$lambda;
  noop3 = noop3$lambda;
  antimeridianPreClip = new antimeridianPreClip$ObjectLiteral();
  NoClip = new NoClip$ObjectLiteral();
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
  transformRadians = transformRadians$lambda;
  MAX_DEPTH = 16;
  COS_MIN_DISTANCE = get_deg(30).cos;
  Kotlin.defineModule('d2v-geo-js', _);
  return _;
}));

//# sourceMappingURL=d2v-geo-js.js.map
