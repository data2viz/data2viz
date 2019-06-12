(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin', 'd2v-time-js', 'd2v-core-js', 'd2v-geo-js', 'd2v-color-js', 'd2v-viz-js', 'kotlinx-coroutines-core', 'geojson-js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'), require('d2v-time-js'), require('d2v-core-js'), require('d2v-geo-js'), require('d2v-color-js'), require('d2v-viz-js'), require('kotlinx-coroutines-core'), require('geojson-js'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'ex-geo-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'ex-geo-js'.");
    }
    if (typeof this['d2v-time-js'] === 'undefined') {
      throw new Error("Error loading module 'ex-geo-js'. Its dependency 'd2v-time-js' was not found. Please, check whether 'd2v-time-js' is loaded prior to 'ex-geo-js'.");
    }
    if (typeof this['d2v-core-js'] === 'undefined') {
      throw new Error("Error loading module 'ex-geo-js'. Its dependency 'd2v-core-js' was not found. Please, check whether 'd2v-core-js' is loaded prior to 'ex-geo-js'.");
    }
    if (typeof this['d2v-geo-js'] === 'undefined') {
      throw new Error("Error loading module 'ex-geo-js'. Its dependency 'd2v-geo-js' was not found. Please, check whether 'd2v-geo-js' is loaded prior to 'ex-geo-js'.");
    }
    if (typeof this['d2v-color-js'] === 'undefined') {
      throw new Error("Error loading module 'ex-geo-js'. Its dependency 'd2v-color-js' was not found. Please, check whether 'd2v-color-js' is loaded prior to 'ex-geo-js'.");
    }
    if (typeof this['d2v-viz-js'] === 'undefined') {
      throw new Error("Error loading module 'ex-geo-js'. Its dependency 'd2v-viz-js' was not found. Please, check whether 'd2v-viz-js' is loaded prior to 'ex-geo-js'.");
    }
    if (typeof this['kotlinx-coroutines-core'] === 'undefined') {
      throw new Error("Error loading module 'ex-geo-js'. Its dependency 'kotlinx-coroutines-core' was not found. Please, check whether 'kotlinx-coroutines-core' is loaded prior to 'ex-geo-js'.");
    }
    if (typeof this['geojson-js'] === 'undefined') {
      throw new Error("Error loading module 'ex-geo-js'. Its dependency 'geojson-js' was not found. Please, check whether 'geojson-js' is loaded prior to 'ex-geo-js'.");
    }
    root['ex-geo-js'] = factory(typeof this['ex-geo-js'] === 'undefined' ? {} : this['ex-geo-js'], kotlin, this['d2v-time-js'], this['d2v-core-js'], this['d2v-geo-js'], this['d2v-color-js'], this['d2v-viz-js'], this['kotlinx-coroutines-core'], this['geojson-js']);
  }
}(this, function (_, Kotlin, $module$d2v_time_js, $module$d2v_core_js, $module$d2v_geo_js, $module$d2v_color_js, $module$d2v_viz_js, $module$kotlinx_coroutines_core, $module$geojson_js) {
  'use strict';
  var Unit = Kotlin.kotlin.Unit;
  var Date_init = $module$d2v_time_js.io.data2viz.time.Date_init;
  var get_deg = $module$d2v_core_js.io.data2viz.math.get_deg_rcaex3$;
  var albersProjection = $module$d2v_geo_js.io.data2viz.geo.projection.albersProjection_2xcl47$;
  var to = Kotlin.kotlin.to_ujzrz7$;
  var albersUSAProjection = $module$d2v_geo_js.io.data2viz.geo.projection.albersUSAProjection_zfb0q7$;
  var azimuthalEqualAreaProjection = $module$d2v_geo_js.io.data2viz.geo.projection.azimuthalEqualAreaProjection_nta9te$;
  var azimuthalEquidistant = $module$d2v_geo_js.io.data2viz.geo.projection.azimuthalEquidistant_nta9te$;
  var conicConformalProjection = $module$d2v_geo_js.io.data2viz.geo.projection.conicConformalProjection_2xcl47$;
  var conicEqualAreaProjection = $module$d2v_geo_js.io.data2viz.geo.projection.conicEqualAreaProjection_2xcl47$;
  var conicEquidistantProjection = $module$d2v_geo_js.io.data2viz.geo.projection.conicEquidistantProjection_2xcl47$;
  var equalEarthProjection = $module$d2v_geo_js.io.data2viz.geo.projection.equalEarthProjection_ey98sg$;
  var equirectangularProjection = $module$d2v_geo_js.io.data2viz.geo.projection.equirectangularProjection_ey98sg$;
  var gnomonicProjection = $module$d2v_geo_js.io.data2viz.geo.projection.gnomonicProjection_ey98sg$;
  var identityProjection = $module$d2v_geo_js.io.data2viz.geo.projection.identityProjection_ey98sg$;
  var mercatorProjection = $module$d2v_geo_js.io.data2viz.geo.projection.mercatorProjection_ey98sg$;
  var naturalEarthProjection = $module$d2v_geo_js.io.data2viz.geo.projection.naturalEarthProjection_ey98sg$;
  var orthographicProjection = $module$d2v_geo_js.io.data2viz.geo.projection.orthographicProjection_ey98sg$;
  var stereographicProjection = $module$d2v_geo_js.io.data2viz.geo.projection.stereographicProjection_ey98sg$;
  var transverseMercatorProjection = $module$d2v_geo_js.io.data2viz.geo.projection.transverseMercatorProjection_4mndx7$;
  var hashMapOf = Kotlin.kotlin.collections.hashMapOf_qfcya0$;
  var toList = Kotlin.kotlin.collections.toList_7wnvza$;
  var listOf = Kotlin.kotlin.collections.listOf_i5x0yv$;
  var throwUPAE = Kotlin.throwUPAE;
  var ensureNotNull = Kotlin.ensureNotNull;
  var Colors = $module$d2v_color_js.io.data2viz.color.Colors;
  var roundToInt = Kotlin.kotlin.math.roundToInt_yrwdxr$;
  var viz = $module$d2v_viz_js.io.data2viz.viz.viz_ohegjc$;
  var kotlin_js_internal_DoubleCompanionObject = Kotlin.kotlin.js.internal.DoubleCompanionObject;
  var Kind_OBJECT = Kotlin.Kind.OBJECT;
  var toRadians = $module$d2v_core_js.io.data2viz.math.toRadians_yrwdxr$;
  var cartesian = $module$d2v_geo_js.io.data2viz.geo.geometry.cartesian_gf7tl1$;
  var quaternion = $module$d2v_geo_js.io.data2viz.geo.geometry.quaternion_gf7tl1$;
  var isNaN_0 = Kotlin.kotlin.isNaN_yrwdxr$;
  var quaternionDelta = $module$d2v_geo_js.io.data2viz.geo.geometry.quaternionDelta_qxkboe$;
  var quaternionMultiply = $module$d2v_geo_js.io.data2viz.geo.geometry.quaternionMultiply_g9g6do$;
  var eulerRotation = $module$d2v_geo_js.io.data2viz.geo.geometry.eulerRotation_gf7tl1$;
  var L3000 = Kotlin.Long.fromInt(3000);
  var KPointerDrag = $module$d2v_viz_js.io.data2viz.viz.KPointerDrag;
  var KZoom = $module$d2v_viz_js.io.data2viz.viz.KZoom;
  var Math_0 = Math;
  var geoPath = $module$d2v_geo_js.io.data2viz.geo.geojson.geoPath_v6g2nn$;
  var PathGeom = $module$d2v_core_js.io.data2viz.geom.PathGeom;
  var PathNode = $module$d2v_viz_js.io.data2viz.viz.PathNode;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var createElement = Kotlin.kotlin.dom.createElement_7cgwi1$;
  var coroutines = $module$kotlinx_coroutines_core.kotlinx.coroutines;
  var equals = Kotlin.equals;
  var geoGraticule = $module$d2v_geo_js.io.data2viz.geo.geometry.geoGraticule_2j84pz$;
  var await_0 = $module$kotlinx_coroutines_core.kotlinx.coroutines.await_t11jrl$;
  var toGeoJsonObject = $module$geojson_js.io.data2viz.geojson.toGeoJsonObject_pdl1vz$;
  var bindRendererOn = $module$d2v_viz_js.io.data2viz.viz.bindRendererOn_kezl2e$;
  var COROUTINE_SUSPENDED = Kotlin.kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED;
  var CoroutineImpl = Kotlin.kotlin.coroutines.CoroutineImpl;
  var promise = $module$kotlinx_coroutines_core.kotlinx.coroutines.promise_pda6u4$;
  GeoPathNode.prototype = Object.create(PathNode.prototype);
  GeoPathNode.prototype.constructor = GeoPathNode;
  function geoVizAutoRotate$lambda($receiver, f) {
    if (isProjectionSupportTransformations) {
      rotateByTime(get_geoPathNode());
    }
    return Unit;
  }
  function geoVizAutoRotate(world, projectionName, vizWidth, vizHeight) {
    if (vizWidth === void 0)
      vizWidth = 500.0;
    if (vizHeight === void 0)
      vizHeight = 500.0;
    var viz = geoViz(world, projectionName, vizWidth, vizHeight);
    viz.animation_o5zv02$(geoVizAutoRotate$lambda);
    return viz;
  }
  function rotateByTime(geoPathNode) {
    var projection = geoPathNode.geoProjection;
    var unixTime = Date_init().getTime();
    var fullRotationCyclesPerMinute = 6;
    var minute = 60000;
    var ratio = unixTime % minute / minute;
    var angle = ratio * 360 * fullRotationCyclesPerMinute % 360;
    projection.rotateLambda = get_deg(angle);
    geoPathNode.redrawPath();
  }
  function allProjections$lambda($receiver) {
    $receiver.scale = 500.0;
    return Unit;
  }
  var allProjections;
  var allProjectionsNames;
  var allFiles;
  var projectionsToSingleFile;
  var defaultFileIndex;
  var defaultProjectionIndex;
  function get_projection() {
    return get_geoPathNode().geoProjection;
  }
  var isProjectionSupportTransformations;
  var geoPathNode;
  function get_geoPathNode() {
    if (geoPathNode == null)
      return throwUPAE('geoPathNode');
    return geoPathNode;
  }
  function set_geoPathNode(geoPathNode_0) {
    geoPathNode = geoPathNode_0;
  }
  function geoViz$lambda$lambda($receiver) {
    $receiver.x = 10.0;
    $receiver.y = 15.0;
    $receiver.fill = Colors.Web.red;
    return Unit;
  }
  function geoViz$lambda$lambda_0(closure$projectionName) {
    return function ($receiver) {
      $receiver.x = 10.0;
      $receiver.y = 30.0;
      $receiver.fill = Colors.Web.red;
      $receiver.textContent = closure$projectionName;
      return Unit;
    };
  }
  function geoViz$lambda$lambda_1(closure$fps) {
    return function ($receiver, now) {
      FPS_getInstance().eventuallyUpdate_14dthe$(now);
      if (FPS_getInstance().value >= 0) {
        closure$fps.textContent = 'Internal FPS: ' + roundToInt(FPS_getInstance().value);
      }
      return Unit;
    };
  }
  function geoViz$lambda$lambda_2(this$) {
    return function (newWidth, newHeight) {
      this$.width = newWidth;
      this$.height = newHeight;
      get_geoPathNode().redrawPath();
      return Unit;
    };
  }
  function geoViz$lambda(closure$vizWidth, closure$vizHeight, closure$projection, closure$world, closure$projectionName) {
    return function ($receiver) {
      var tmp$;
      $receiver.width = closure$vizWidth;
      $receiver.height = closure$vizHeight;
      var $receiver_0 = new GeoPathNode();
      var closure$projection_0 = closure$projection;
      var closure$world_0 = closure$world;
      $receiver_0.stroke = Colors.Web.black;
      $receiver_0.strokeWidth = 1.0;
      $receiver_0.fill = Colors.Web.whitesmoke;
      $receiver_0.geoProjection = closure$projection_0;
      $receiver_0.geoData = closure$world_0;
      $receiver_0.redrawPath();
      set_geoPathNode($receiver_0);
      $receiver.add_vetai8$(get_geoPathNode());
      var fps = $receiver.text_6q900q$(geoViz$lambda$lambda);
      $receiver.text_6q900q$(geoViz$lambda$lambda_0(closure$projectionName));
      switch (closure$projectionName) {
        case 'albersUSA':
        case 'identity':
          tmp$ = false;
          break;
        default:tmp$ = true;
          break;
      }
      isProjectionSupportTransformations = tmp$;
      if (isProjectionSupportTransformations) {
        get_geoPathNode().geoProjection.rotate_u9a0y3$(get_deg(0.0), get_deg(0.0), get_deg(0.0));
        get_geoPathNode().redrawPath();
      }
      $receiver.animation_o5zv02$(geoViz$lambda$lambda_1(fps));
      $receiver.onResize_tuav61$(geoViz$lambda$lambda_2($receiver));
      return Unit;
    };
  }
  function geoViz(world, projectionName, vizWidth, vizHeight) {
    if (vizWidth === void 0)
      vizWidth = 500.0;
    if (vizHeight === void 0)
      vizHeight = 500.0;
    var projection = ensureNotNull(allProjections.get_11rb$(projectionName));
    projection.translateX = vizWidth / 2.0;
    projection.translateY = vizHeight / 2.0;
    return viz(geoViz$lambda(vizWidth, vizHeight, projection, world, projectionName));
  }
  function FPS() {
    FPS_instance = this;
    this.averageCount = 10;
    this.value = 0.0;
    this.count = 0;
    this.lastStart = kotlin_js_internal_DoubleCompanionObject.NaN;
  }
  FPS.prototype.eventuallyUpdate_14dthe$ = function (current) {
    var tmp$;
    if (this.lastStart === kotlin_js_internal_DoubleCompanionObject.NaN)
      this.lastStart = current;
    if ((tmp$ = this.count, this.count = tmp$ + 1 | 0, tmp$) === this.averageCount) {
      var totalTime = current - this.lastStart;
      this.value = 1000.0 * this.averageCount / totalTime;
      this.lastStart = current;
      this.count = 0;
    }
  };
  FPS.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'FPS',
    interfaces: []
  };
  var FPS_instance = null;
  function FPS_getInstance() {
    if (FPS_instance === null) {
      new FPS();
    }
    return FPS_instance;
  }
  function geoVizEventsControl(world, projectionName, vizWidth, vizHeight) {
    if (vizWidth === void 0)
      vizWidth = 500.0;
    if (vizHeight === void 0)
      vizHeight = 500.0;
    var viz = geoViz(world, projectionName, vizWidth, vizHeight);
    if (isProjectionSupportTransformations) {
      addGeoControlEvents(viz);
      launchStartRotateAnimation(viz);
      launchEventsControlRedrawAnimation(viz);
    }
    return viz;
  }
  var isNeedRedrawAfterControlEvents;
  var startDragCartesianPoint;
  function get_startDragCartesianPoint() {
    if (startDragCartesianPoint == null)
      return throwUPAE('startDragCartesianPoint');
    return startDragCartesianPoint;
  }
  function set_startDragCartesianPoint(startDragCartesianPoint_0) {
    startDragCartesianPoint = startDragCartesianPoint_0;
  }
  var startDragQuaternion;
  function get_startDragQuaternion() {
    if (startDragQuaternion == null)
      return throwUPAE('startDragQuaternion');
    return startDragQuaternion;
  }
  function set_startDragQuaternion(startDragQuaternion_0) {
    startDragQuaternion = startDragQuaternion_0;
  }
  var startDragRotationAngles;
  function get_startDragRotationAngles() {
    if (startDragRotationAngles == null)
      return throwUPAE('startDragRotationAngles');
    return startDragRotationAngles;
  }
  function set_startDragRotationAngles(startDragRotationAngles_0) {
    startDragRotationAngles = startDragRotationAngles_0;
  }
  var isUserStartControlDuringStartAnimation;
  var minProjectionScale;
  var diffInMillisecondsToDetectZoom;
  var startZoomCartesianPoint;
  function get_startZoomCartesianPoint() {
    if (startZoomCartesianPoint == null)
      return throwUPAE('startZoomCartesianPoint');
    return startZoomCartesianPoint;
  }
  function set_startZoomCartesianPoint(startZoomCartesianPoint_0) {
    startZoomCartesianPoint = startZoomCartesianPoint_0;
  }
  var startZoomQuaternion;
  function get_startZoomQuaternion() {
    if (startZoomQuaternion == null)
      return throwUPAE('startZoomQuaternion');
    return startZoomQuaternion;
  }
  function set_startZoomQuaternion(startZoomQuaternion_0) {
    startZoomQuaternion = startZoomQuaternion_0;
  }
  var startZoomRotationAngles;
  function get_startZoomRotationAngles() {
    if (startZoomRotationAngles == null)
      return throwUPAE('startZoomRotationAngles');
    return startZoomRotationAngles;
  }
  function set_startZoomRotationAngles(startZoomRotationAngles_0) {
    startZoomRotationAngles = startZoomRotationAngles_0;
  }
  function zoomStarted(evt) {
    if (!isUserStartControlDuringStartAnimation) {
      isUserStartControlDuringStartAnimation = true;
    }
    var inverted = get_projection().invert_lu1900$(evt.currentZoomPos.x, evt.currentZoomPos.y);
    set_startZoomCartesianPoint(cartesian(new Float64Array([toRadians(inverted[0]), toRadians(inverted[1])])));
    set_startZoomRotationAngles([get_projection().rotateLambda, get_projection().rotatePhi, get_projection().rotateGamma]);
    set_startZoomQuaternion(quaternion(new Float64Array([get_startZoomRotationAngles()[0].deg, get_startZoomRotationAngles()[1].deg, get_startZoomRotationAngles()[2].deg])));
  }
  function zoomed(evt) {
    var previousRotateLambda = get_projection().rotateLambda;
    var previousRotatePhi = get_projection().rotatePhi;
    var previousRotateGamma = get_projection().rotateGamma;
    var tmp$ = get_projection();
    var a = get_projection().scale + evt.delta;
    var b = minProjectionScale;
    tmp$.scale = Math_0.max(a, b);
    get_projection().rotate_u9a0y3$(get_startZoomRotationAngles()[0], get_startZoomRotationAngles()[1], get_startZoomRotationAngles()[2]);
    var inverted = get_projection().invert_lu1900$(evt.currentZoomPos.x, evt.currentZoomPos.y);
    var currentZoomCartesianPoint = cartesian(new Float64Array([toRadians(inverted[0]), toRadians(inverted[1])]));
    if (!isNaN_0(currentZoomCartesianPoint[0]) && !isNaN_0(currentZoomCartesianPoint[1]) && !isNaN_0(currentZoomCartesianPoint[2])) {
      var currentZoomQuaternion = quaternionMultiply(get_startZoomQuaternion(), quaternionDelta(get_startZoomCartesianPoint(), currentZoomCartesianPoint));
      var rotationAngles = eulerRotation(currentZoomQuaternion);
      rotationAngles[2] = 0.0;
      rotateByAngles(get_deg(rotationAngles[0]), get_deg(rotationAngles[1]), get_deg(rotationAngles[2]));
    }
     else {
      get_projection().rotate_u9a0y3$(previousRotateLambda, previousRotatePhi, previousRotateGamma);
    }
  }
  function launchEventsControlRedrawAnimation$lambda($receiver, it) {
    if (isNeedRedrawAfterControlEvents) {
      get_geoPathNode().redrawPath();
    }
    return Unit;
  }
  function launchEventsControlRedrawAnimation($receiver) {
    $receiver.animation_o5zv02$(launchEventsControlRedrawAnimation$lambda);
  }
  function launchStartRotateAnimation$lambda(closure$endTime, closure$durationInMs, closure$totalLambdaAngleDiffDeg, closure$startRotationLambda) {
    return function ($receiver, it) {
      var diffMilliseconds = closure$endTime.getTime() - Date_init().getTime();
      if (diffMilliseconds > 0 && !isUserStartControlDuringStartAnimation) {
        var percent = 1 - diffMilliseconds / closure$durationInMs.toNumber();
        var deceleratedPercent = sqrtDecelerate(percent);
        var currentFrameRotationDegree = get_deg(deceleratedPercent * closure$totalLambdaAngleDiffDeg);
        get_projection().rotateLambda = closure$startRotationLambda.plus_5t6zck$(currentFrameRotationDegree);
        get_geoPathNode().redrawPath();
      }
       else {
        $receiver.stop();
      }
      return Unit;
    };
  }
  function launchStartRotateAnimation($receiver) {
    var durationInMs = L3000;
    var totalLambdaAngleDiffDeg = 360;
    var startRotationLambda = get_projection().rotateLambda;
    var $receiver_0 = Date_init();
    $receiver_0.plusMilliseconds_s8cxhz$(durationInMs);
    var endTime = $receiver_0;
    isUserStartControlDuringStartAnimation = false;
    $receiver.animation_o5zv02$(launchStartRotateAnimation$lambda(endTime, durationInMs, totalLambdaAngleDiffDeg, startRotationLambda));
  }
  function sqrtDecelerate(originPercent) {
    return 1 - (1 - originPercent) * (1 - originPercent);
  }
  function addGeoControlEvents$lambda(evt) {
    switch (evt.action.name) {
      case 'Start':
        if (!isUserStartControlDuringStartAnimation) {
          isUserStartControlDuringStartAnimation = true;
        }

        var inverted = get_projection().invert_lu1900$(evt.pos.x, evt.pos.y);
        set_startDragCartesianPoint(cartesian(new Float64Array([toRadians(inverted[0]), toRadians(inverted[1])])));
        set_startDragRotationAngles([get_projection().rotateLambda, get_projection().rotatePhi, get_projection().rotateGamma]);
        set_startDragQuaternion(quaternion(new Float64Array([get_startDragRotationAngles()[0].deg, get_startDragRotationAngles()[1].deg, get_startDragRotationAngles()[2].deg])));
        break;
      case 'Dragging':
        var previousRotateLambda = get_projection().rotateLambda;
        var previousRotatePhi = get_projection().rotatePhi;
        var previousRotateGamma = get_projection().rotateGamma;
        get_projection().rotate_u9a0y3$(get_startDragRotationAngles()[0], get_startDragRotationAngles()[1], get_startDragRotationAngles()[2]);
        var inverted_0 = get_projection().invert_lu1900$(evt.pos.x, evt.pos.y);
        var currentDragCartesianPoint = cartesian(new Float64Array([toRadians(inverted_0[0]), toRadians(inverted_0[1])]));
        var currentDragQuaternion = quaternionMultiply(get_startDragQuaternion(), quaternionDelta(get_startDragCartesianPoint(), currentDragCartesianPoint));
        var rotationAngles = eulerRotation(currentDragQuaternion);
        rotateByAngles(get_deg(rotationAngles[0]), get_deg(rotationAngles[1]), get_deg(rotationAngles[2]));
        break;
    }
    return Unit;
  }
  function addGeoControlEvents$lambda_0(closure$lastZoomTime) {
    return function (evt) {
      var inverted = get_projection().invert_lu1900$(evt.currentZoomPos.x, evt.currentZoomPos.y);
      if (isNaN_0(inverted[0]) || isNaN_0(inverted[1])) {
        return;
      }
      var now = Date_init();
      var diffMilliseconds = now.getTime() - closure$lastZoomTime.v.getTime();
      if (diffMilliseconds > diffInMillisecondsToDetectZoom) {
        zoomStarted(evt);
      }
       else {
        zoomed(evt);
      }
      closure$lastZoomTime.v = now;
      return Unit;
    };
  }
  function addGeoControlEvents($receiver) {
    $receiver.on_8o0vxr$(KPointerDrag.PointerDragEventListener, addGeoControlEvents$lambda);
    var lastZoomTime = {v: Date_init()};
    $receiver.on_8o0vxr$(KZoom.ZoomEventListener, addGeoControlEvents$lambda_0(lastZoomTime));
  }
  function rotateByAngles(angleLambda, anglePhi, angleGamma) {
    get_projection().rotate_u9a0y3$(angleLambda, anglePhi, angleGamma);
    isNeedRedrawAfterControlEvents = true;
  }
  function GeoPathNode(geoData, geoProjection, path) {
    if (geoData === void 0)
      geoData = null;
    if (geoProjection === void 0)
      geoProjection = identityProjection();
    if (path === void 0)
      path = new PathGeom();
    PathNode.call(this, path);
    this.geoData = geoData;
    this.geoProjection = geoProjection;
  }
  GeoPathNode.prototype.redrawPath = function () {
    var geoPath_0 = geoPath(this.geoProjection, this.path);
    this.clearPath();
    geoPath_0.project_6ux19g$(ensureNotNull(this.geoData));
  };
  GeoPathNode.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'GeoPathNode',
    interfaces: [PathNode]
  };
  var currentViz;
  var canvaseVizHtmlElementId;
  var selectFileHtmlElementId;
  var selectProjectionHtmlElementId;
  var buttonStartStopHtmlElementId;
  var vizWidth;
  var vizHeight;
  var selectFile;
  function get_selectFile() {
    if (selectFile == null)
      return throwUPAE('selectFile');
    return selectFile;
  }
  function set_selectFile(selectFile_0) {
    selectFile = selectFile_0;
  }
  var selectProjection;
  function get_selectProjection() {
    if (selectProjection == null)
      return throwUPAE('selectProjection');
    return selectProjection;
  }
  function set_selectProjection(selectProjection_0) {
    selectProjection = selectProjection_0;
  }
  var buttonStartStop;
  var isBenchmarkWithD3;
  function get_isNeedAutoRotation() {
    return isBenchmarkWithD3;
  }
  var animationEnabled;
  function main$lambda$lambda(closure$filename) {
    return function ($receiver) {
      $receiver.setAttribute('value', closure$filename);
      $receiver.innerHTML = closure$filename;
      return Unit;
    };
  }
  function main$lambda$lambda_0(closure$projectionName) {
    return function ($receiver) {
      $receiver.setAttribute('value', closure$projectionName);
      $receiver.innerHTML = closure$projectionName;
      return Unit;
    };
  }
  function main$lambda(it) {
    onSelectionChanged();
    return Unit;
  }
  function main$lambda_0(it) {
    onSelectionChanged();
    return Unit;
  }
  function main$lambda_1(it) {
    if (animationEnabled) {
      currentViz != null ? (currentViz.stopAnimations(), Unit) : null;
    }
     else {
      currentViz != null ? (currentViz.startAnimations(), Unit) : null;
    }
    animationEnabled = !animationEnabled;
    return it;
  }
  function main(args) {
    set_selectFile(document.getElementById(selectFileHtmlElementId));
    set_selectProjection(document.getElementById(selectProjectionHtmlElementId));
    buttonStartStop = document.getElementById(buttonStartStopHtmlElementId);
    isBenchmarkWithD3 = document.getElementById('benchmark_with_d3') != null;
    var tmp$;
    tmp$ = allFiles.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      get_selectFile().options.add(createElement(document, 'option', main$lambda$lambda(element)));
    }
    var tmp$_0;
    tmp$_0 = allProjectionsNames.iterator();
    while (tmp$_0.hasNext()) {
      var element_0 = tmp$_0.next();
      get_selectProjection().options.add(createElement(document, 'option', main$lambda$lambda_0(element_0)));
    }
    get_selectFile().selectedIndex = defaultFileIndex;
    get_selectProjection().selectedIndex = defaultProjectionIndex;
    get_selectFile().onchange = main$lambda;
    get_selectProjection().onchange = main$lambda_0;
    buttonStartStop != null ? (buttonStartStop.onclick = main$lambda_1) : null;
    onSelectionChanged();
  }
  function onSelectionChanged() {
    var selectFile = document.getElementById(selectFileHtmlElementId);
    var selectProjection = document.getElementById(selectProjectionHtmlElementId);
    onSettingsChanged_0(selectFile, selectProjection);
  }
  function onSettingsChanged_0(selectFile, selectProjection) {
    var tmp$;
    var projectionValue = ensureNotNull(ensureNotNull(selectProjection.options[selectProjection.selectedIndex]).getAttribute('value'));
    if (projectionsToSingleFile.containsKey_11rb$(projectionValue)) {
      tmp$ = ensureNotNull(projectionsToSingleFile.get_11rb$(projectionValue));
    }
     else {
      tmp$ = ensureNotNull(ensureNotNull(selectFile.options[selectFile.selectedIndex]).getAttribute('value'));
    }
    var fileValue = tmp$;
    loadEventsControlViz(fileValue, projectionValue);
    onSettingsChanged(fileValue, projectionValue);
  }
  function loadEventsControlViz$lambda$lambda(closure$oldCanvas) {
    return function ($receiver) {
      $receiver.id = canvaseVizHtmlElementId;
      var $receiver_0 = closure$oldCanvas.getAttributeNames();
      var tmp$;
      for (tmp$ = 0; tmp$ !== $receiver_0.length; ++tmp$) {
        var element = $receiver_0[tmp$];
        $receiver.setAttribute(element, ensureNotNull(closure$oldCanvas.getAttribute(element)));
      }
      return Unit;
    };
  }
  function Coroutine$loadEventsControlViz$lambda(closure$filename_0, closure$projectionName_0, $receiver_0, controller, continuation_0) {
    CoroutineImpl.call(this, continuation_0);
    this.$controller = controller;
    this.exceptionState_0 = 1;
    this.local$closure$filename = closure$filename_0;
    this.local$closure$projectionName = closure$projectionName_0;
    this.local$tmp$ = void 0;
  }
  Coroutine$loadEventsControlViz$lambda.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: null,
    interfaces: [CoroutineImpl]
  };
  Coroutine$loadEventsControlViz$lambda.prototype = Object.create(CoroutineImpl.prototype);
  Coroutine$loadEventsControlViz$lambda.prototype.constructor = Coroutine$loadEventsControlViz$lambda;
  Coroutine$loadEventsControlViz$lambda.prototype.doResume = function () {
    do
      try {
        switch (this.state_0) {
          case 0:
            if (equals(this.local$closure$filename, 'graticule')) {
              this.local$tmp$ = geoGraticule().graticule();
              this.state_0 = 4;
              continue;
            }
             else {
              this.state_0 = 2;
              this.result_0 = await_0(window.fetch(new Request(this.local$closure$filename)), this);
              if (this.result_0 === COROUTINE_SUSPENDED)
                return COROUTINE_SUSPENDED;
              continue;
            }

          case 1:
            throw this.exception_0;
          case 2:
            this.state_0 = 3;
            this.result_0 = await_0(this.result_0.text(), this);
            if (this.result_0 === COROUTINE_SUSPENDED)
              return COROUTINE_SUSPENDED;
            continue;
          case 3:
            this.local$tmp$ = toGeoJsonObject(this.result_0);
            this.state_0 = 4;
            continue;
          case 4:
            var geoJson = this.local$tmp$;
            var oldCanvas = document.getElementById(canvaseVizHtmlElementId);
            var parent = ensureNotNull(oldCanvas).parentElement;
            ensureNotNull(parent).removeChild(oldCanvas);
            var newCanvas = createElement(document, 'canvas', loadEventsControlViz$lambda$lambda(oldCanvas));
            parent.appendChild(newCanvas);
            currentViz != null ? (currentViz.stopAnimations(), Unit) : null;
            currentViz = loadViz(geoJson, this.local$closure$projectionName);
            bindRendererOn(ensureNotNull(currentViz), newCanvas);
            var anim = animationEnabled;
            if (!anim) {
              return currentViz != null && (currentViz.stopAnimations(), Unit), Unit;
            }
             else {
              this.state_0 = 5;
              continue;
            }

          case 5:
            return Unit;
          default:this.state_0 = 1;
            throw new Error('State Machine Unreachable execution');
        }
      }
       catch (e) {
        if (this.state_0 === 1) {
          this.exceptionState_0 = this.state_0;
          throw e;
        }
         else {
          this.state_0 = this.exceptionState_0;
          this.exception_0 = e;
        }
      }
     while (true);
  };
  function loadEventsControlViz$lambda(closure$filename_0, closure$projectionName_0) {
    return function ($receiver_0, continuation_0, suspended) {
      var instance = new Coroutine$loadEventsControlViz$lambda(closure$filename_0, closure$projectionName_0, $receiver_0, this, continuation_0);
      if (suspended)
        return instance;
      else
        return instance.doResume(null);
    };
  }
  function loadEventsControlViz(filename, projectionName) {
    promise(coroutines.GlobalScope, void 0, void 0, loadEventsControlViz$lambda(filename, projectionName));
  }
  function loadViz(geoJson, projectionName) {
    if (get_isNeedAutoRotation()) {
      return geoVizAutoRotate(geoJson, projectionName, vizWidth, vizHeight);
    }
     else {
      return geoVizEventsControl(geoJson, projectionName, vizWidth, vizHeight);
    }
  }
  _.geoVizAutoRotate_ix6qni$ = geoVizAutoRotate;
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$examples = package$data2viz.examples || (package$data2viz.examples = {});
  var package$geo = package$examples.geo || (package$examples.geo = {});
  Object.defineProperty(package$geo, 'allProjections', {
    get: function () {
      return allProjections;
    }
  });
  Object.defineProperty(package$geo, 'allProjectionsNames', {
    get: function () {
      return allProjectionsNames;
    }
  });
  Object.defineProperty(package$geo, 'allFiles', {
    get: function () {
      return allFiles;
    }
  });
  Object.defineProperty(package$geo, 'projectionsToSingleFile', {
    get: function () {
      return projectionsToSingleFile;
    }
  });
  Object.defineProperty(package$geo, 'defaultFileIndex', {
    get: function () {
      return defaultFileIndex;
    }
  });
  Object.defineProperty(package$geo, 'defaultProjectionIndex', {
    get: function () {
      return defaultProjectionIndex;
    }
  });
  Object.defineProperty(package$geo, 'projection', {
    get: get_projection
  });
  Object.defineProperty(package$geo, 'isProjectionSupportTransformations', {
    get: function () {
      return isProjectionSupportTransformations;
    },
    set: function (value) {
      isProjectionSupportTransformations = value;
    }
  });
  Object.defineProperty(package$geo, 'geoPathNode', {
    get: get_geoPathNode,
    set: set_geoPathNode
  });
  package$geo.geoViz_oqjjcb$ = geoViz;
  Object.defineProperty(package$geo, 'FPS', {
    get: FPS_getInstance
  });
  _.geoVizEventsControl_ix6qni$ = geoVizEventsControl;
  Object.defineProperty(_, 'isNeedRedrawAfterControlEvents', {
    get: function () {
      return isNeedRedrawAfterControlEvents;
    },
    set: function (value) {
      isNeedRedrawAfterControlEvents = value;
    }
  });
  Object.defineProperty(_, 'startDragCartesianPoint', {
    get: get_startDragCartesianPoint,
    set: set_startDragCartesianPoint
  });
  Object.defineProperty(_, 'startDragQuaternion', {
    get: get_startDragQuaternion,
    set: set_startDragQuaternion
  });
  Object.defineProperty(_, 'startDragRotationAngles', {
    get: get_startDragRotationAngles,
    set: set_startDragRotationAngles
  });
  Object.defineProperty(_, 'isUserStartControlDuringStartAnimation', {
    get: function () {
      return isUserStartControlDuringStartAnimation;
    },
    set: function (value) {
      isUserStartControlDuringStartAnimation = value;
    }
  });
  Object.defineProperty(_, 'minProjectionScale', {
    get: function () {
      return minProjectionScale;
    }
  });
  Object.defineProperty(_, 'diffInMillisecondsToDetectZoom', {
    get: function () {
      return diffInMillisecondsToDetectZoom;
    }
  });
  Object.defineProperty(_, 'startZoomCartesianPoint', {
    get: get_startZoomCartesianPoint,
    set: set_startZoomCartesianPoint
  });
  Object.defineProperty(_, 'startZoomQuaternion', {
    get: get_startZoomQuaternion,
    set: set_startZoomQuaternion
  });
  Object.defineProperty(_, 'startZoomRotationAngles', {
    get: get_startZoomRotationAngles,
    set: set_startZoomRotationAngles
  });
  _.zoomStarted_4f9tu2$ = zoomStarted;
  _.zoomed_4f9tu2$ = zoomed;
  _.launchEventsControlRedrawAnimation_veyafq$ = launchEventsControlRedrawAnimation;
  _.launchStartRotateAnimation_veyafq$ = launchStartRotateAnimation;
  _.sqrtDecelerate_14dthe$ = sqrtDecelerate;
  _.addGeoControlEvents_veyafq$ = addGeoControlEvents;
  var package$viz = package$data2viz.viz || (package$data2viz.viz = {});
  package$viz.GeoPathNode = GeoPathNode;
  Object.defineProperty(package$geo, 'currentViz', {
    get: function () {
      return currentViz;
    },
    set: function (value) {
      currentViz = value;
    }
  });
  Object.defineProperty(package$geo, 'canvaseVizHtmlElementId', {
    get: function () {
      return canvaseVizHtmlElementId;
    }
  });
  Object.defineProperty(package$geo, 'selectFileHtmlElementId', {
    get: function () {
      return selectFileHtmlElementId;
    }
  });
  Object.defineProperty(package$geo, 'selectProjectionHtmlElementId', {
    get: function () {
      return selectProjectionHtmlElementId;
    }
  });
  Object.defineProperty(package$geo, 'buttonStartStopHtmlElementId', {
    get: function () {
      return buttonStartStopHtmlElementId;
    }
  });
  Object.defineProperty(package$geo, 'vizWidth', {
    get: function () {
      return vizWidth;
    }
  });
  Object.defineProperty(package$geo, 'vizHeight', {
    get: function () {
      return vizHeight;
    }
  });
  Object.defineProperty(package$geo, 'selectFile', {
    get: get_selectFile,
    set: set_selectFile
  });
  Object.defineProperty(package$geo, 'selectProjection', {
    get: get_selectProjection,
    set: set_selectProjection
  });
  Object.defineProperty(package$geo, 'buttonStartStop', {
    get: function () {
      return buttonStartStop;
    },
    set: function (value) {
      buttonStartStop = value;
    }
  });
  Object.defineProperty(package$geo, 'isBenchmarkWithD3', {
    get: function () {
      return isBenchmarkWithD3;
    },
    set: function (value) {
      isBenchmarkWithD3 = value;
    }
  });
  Object.defineProperty(package$geo, 'isNeedAutoRotation', {
    get: get_isNeedAutoRotation
  });
  Object.defineProperty(package$geo, 'animationEnabled', {
    get: function () {
      return animationEnabled;
    },
    set: function (value) {
      animationEnabled = value;
    }
  });
  package$geo.main_kand9s$ = main;
  allProjections = hashMapOf([to('albers', albersProjection()), to('albersUSA', albersUSAProjection(allProjections$lambda)), to('azimuthalEqualArea', azimuthalEqualAreaProjection()), to('azimuthalEquidistant', azimuthalEquidistant()), to('conicConformal', conicConformalProjection()), to('conicEqual', conicEqualAreaProjection()), to('conicEquidistant', conicEquidistantProjection()), to('equalEarth', equalEarthProjection()), to('equirectangular', equirectangularProjection()), to('gnomonic', gnomonicProjection()), to('identity', identityProjection()), to('mercator', mercatorProjection()), to('naturalEarth', naturalEarthProjection()), to('orthographic', orthographicProjection()), to('stereographic', stereographicProjection()), to('transverseMercator', transverseMercatorProjection())]);
  allProjectionsNames = toList(allProjections.keys);
  allFiles = listOf(['graticule', 'world-110m.geojson', 'world-110m-30percent.json', 'world-110m-50percent.json', 'world-110m-70percent.json']);
  projectionsToSingleFile = hashMapOf([to('albersUSA', 'us-states.json')]);
  defaultFileIndex = allFiles.indexOf_11rb$('world-110m-30percent.json');
  defaultProjectionIndex = allProjectionsNames.indexOf_11rb$('orthographic');
  isProjectionSupportTransformations = true;
  isNeedRedrawAfterControlEvents = false;
  isUserStartControlDuringStartAnimation = false;
  minProjectionScale = 10.0;
  diffInMillisecondsToDetectZoom = 200;
  currentViz = null;
  canvaseVizHtmlElementId = 'viz';
  selectFileHtmlElementId = 'select_file';
  selectProjectionHtmlElementId = 'select_projection';
  buttonStartStopHtmlElementId = 'button_start_stop';
  vizWidth = 500.0;
  vizHeight = 500.0;
  buttonStartStop = null;
  isBenchmarkWithD3 = false;
  animationEnabled = true;
  main([]);
  Kotlin.defineModule('ex-geo-js', _);
  return _;
}));

//# sourceMappingURL=ex-geo-js.js.map
