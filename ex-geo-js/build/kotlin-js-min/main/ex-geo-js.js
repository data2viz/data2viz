(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin', 'd2v-geo-js', 'd2v-color-js', 'd2v-viz-js', 'd2v-core-js', 'd2v-time-js', 'kotlinx-coroutines-core', 'geojson-js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'), require('d2v-geo-js'), require('d2v-color-js'), require('d2v-viz-js'), require('d2v-core-js'), require('d2v-time-js'), require('kotlinx-coroutines-core'), require('geojson-js'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'ex-geo-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'ex-geo-js'.");
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
    if (typeof this['d2v-core-js'] === 'undefined') {
      throw new Error("Error loading module 'ex-geo-js'. Its dependency 'd2v-core-js' was not found. Please, check whether 'd2v-core-js' is loaded prior to 'ex-geo-js'.");
    }
    if (typeof this['d2v-time-js'] === 'undefined') {
      throw new Error("Error loading module 'ex-geo-js'. Its dependency 'd2v-time-js' was not found. Please, check whether 'd2v-time-js' is loaded prior to 'ex-geo-js'.");
    }
    if (typeof this['kotlinx-coroutines-core'] === 'undefined') {
      throw new Error("Error loading module 'ex-geo-js'. Its dependency 'kotlinx-coroutines-core' was not found. Please, check whether 'kotlinx-coroutines-core' is loaded prior to 'ex-geo-js'.");
    }
    if (typeof this['geojson-js'] === 'undefined') {
      throw new Error("Error loading module 'ex-geo-js'. Its dependency 'geojson-js' was not found. Please, check whether 'geojson-js' is loaded prior to 'ex-geo-js'.");
    }
    root['ex-geo-js'] = factory(typeof this['ex-geo-js'] === 'undefined' ? {} : this['ex-geo-js'], kotlin, this['d2v-geo-js'], this['d2v-color-js'], this['d2v-viz-js'], this['d2v-core-js'], this['d2v-time-js'], this['kotlinx-coroutines-core'], this['geojson-js']);
  }
}(this, function (_, Kotlin, $module$d2v_geo_js, $module$d2v_color_js, $module$d2v_viz_js, $module$d2v_core_js, $module$d2v_time_js, $module$kotlinx_coroutines_core, $module$geojson_js) {
  'use strict';
  var albersProjection = $module$d2v_geo_js.io.data2viz.geo.projection.albersProjection;
  var to = Kotlin.kotlin.to_ujzrz7$;
  var Unit = Kotlin.kotlin.Unit;
  var alberUSAProjection = $module$d2v_geo_js.io.data2viz.geo.projection.alberUSAProjection_i7gh7r$;
  var azimuthalEqualAreaProjection = $module$d2v_geo_js.io.data2viz.geo.projection.azimuthalEqualAreaProjection;
  var azimuthalEquidistant = $module$d2v_geo_js.io.data2viz.geo.projection.azimuthalEquidistant;
  var conicConformalProjection = $module$d2v_geo_js.io.data2viz.geo.projection.conicConformalProjection;
  var conicEqualAreaProjection = $module$d2v_geo_js.io.data2viz.geo.projection.conicEqualAreaProjection;
  var conicEquidistantProjection = $module$d2v_geo_js.io.data2viz.geo.projection.conicEquidistantProjection;
  var equalEarthProjection = $module$d2v_geo_js.io.data2viz.geo.projection.equalEarthProjection;
  var equirectangularProjection = $module$d2v_geo_js.io.data2viz.geo.projection.equirectangularProjection;
  var gnomonicProjection = $module$d2v_geo_js.io.data2viz.geo.projection.gnomonicProjection;
  var identityProjection = $module$d2v_geo_js.io.data2viz.geo.projection.identityProjection;
  var mercatorProjection = $module$d2v_geo_js.io.data2viz.geo.projection.mercatorProjection;
  var naturalEarth1Projection = $module$d2v_geo_js.io.data2viz.geo.projection.naturalEarth1Projection;
  var orthographicProjection = $module$d2v_geo_js.io.data2viz.geo.projection.orthographicProjection;
  var stereographicProjection = $module$d2v_geo_js.io.data2viz.geo.projection.stereographicProjection;
  var transverseMercatorProjection = $module$d2v_geo_js.io.data2viz.geo.projection.transverseMercatorProjection;
  var hashMapOf = Kotlin.kotlin.collections.hashMapOf_qfcya0$;
  var toList = Kotlin.kotlin.collections.toList_7wnvza$;
  var listOf = Kotlin.kotlin.collections.listOf_i5x0yv$;
  var ensureNotNull = Kotlin.ensureNotNull;
  var Colors = $module$d2v_color_js.io.data2viz.color.Colors;
  var PathNode = $module$d2v_viz_js.io.data2viz.viz.PathNode;
  var geoPath = $module$d2v_geo_js.io.data2viz.geo.path.geoPath_830hqy$;
  var get_deg = $module$d2v_core_js.io.data2viz.math.get_deg_rcaex3$;
  var roundToInt = Kotlin.kotlin.math.roundToInt_yrwdxr$;
  var viz = $module$d2v_viz_js.io.data2viz.viz.viz_ohegjc$;
  var Date_init = $module$d2v_time_js.io.data2viz.time.Date_init;
  var kotlin_js_internal_DoubleCompanionObject = Kotlin.kotlin.js.internal.DoubleCompanionObject;
  var Kind_OBJECT = Kotlin.Kind.OBJECT;
  var throwUPAE = Kotlin.throwUPAE;
  var createElement = Kotlin.kotlin.dom.createElement_7cgwi1$;
  var coroutines = $module$kotlinx_coroutines_core.kotlinx.coroutines;
  var await_0 = $module$kotlinx_coroutines_core.kotlinx.coroutines.await_t11jrl$;
  var toGeoJsonObject = $module$geojson_js.io.data2viz.geojson.toGeoJsonObject_pdl1vz$;
  var bindRendererOn = $module$d2v_viz_js.io.data2viz.viz.bindRendererOn_kezl2e$;
  var COROUTINE_SUSPENDED = Kotlin.kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED;
  var CoroutineImpl = Kotlin.kotlin.coroutines.CoroutineImpl;
  var promise = $module$kotlinx_coroutines_core.kotlinx.coroutines.promise_pda6u4$;
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
  function geoViz$lambda$lambda($receiver) {
    $receiver.x = 10.0;
    $receiver.y = 40.0;
    $receiver.fill = Colors.Web.red;
    return Unit;
  }
  function geoViz$lambda$lambda_0(closure$projectionName) {
    return function ($receiver) {
      $receiver.x = 10.0;
      $receiver.y = 60.0;
      $receiver.fill = Colors.Web.red;
      $receiver.textContent = closure$projectionName;
      return Unit;
    };
  }
  function geoViz$lambda$lambda_1(closure$fps, closure$isNeedRotate, closure$geoPathOuter, closure$pathOuter, closure$world) {
    return function ($receiver, now) {
      FPS_getInstance().eventuallyUpdate_14dthe$(now);
      if (FPS_getInstance().value >= 0) {
        closure$fps.textContent = 'Internal FPS: ' + roundToInt(FPS_getInstance().value);
      }
      if (closure$isNeedRotate) {
        doRotate(closure$geoPathOuter.v, closure$pathOuter, closure$world);
      }
      return Unit;
    };
  }
  function geoViz$lambda$lambda_2(this$, closure$projectionOuter, closure$pathOuter, closure$geoPathOuter) {
    return function (newWidth, newHeight) {
      this$.width = newWidth;
      this$.height = newHeight;
      closure$geoPathOuter.v = geoPath(closure$projectionOuter, closure$pathOuter);
      return Unit;
    };
  }
  function geoViz$lambda(closure$vizWidth, closure$vizHeight, closure$projectionName, closure$projectionOuter, closure$world) {
    return function ($receiver) {
      var tmp$;
      $receiver.width = closure$vizWidth;
      $receiver.height = closure$vizHeight;
      var fps = $receiver.text_6q900q$(geoViz$lambda$lambda);
      $receiver.text_6q900q$(geoViz$lambda$lambda_0(closure$projectionName));
      var $receiver_0 = new PathNode();
      $receiver_0.stroke = Colors.Web.black;
      $receiver_0.strokeWidth = 1.0;
      $receiver_0.fill = Colors.Web.whitesmoke;
      var pathOuter = $receiver_0;
      var geoPathOuter = {v: geoPath(closure$projectionOuter, pathOuter)};
      geoPathOuter.v.path_6ux19g$(closure$world);
      $receiver.add_vetai8$(pathOuter);
      switch (closure$projectionName) {
        case 'albersUSA':
        case 'identity':
          tmp$ = false;
          break;
        default:tmp$ = true;
          break;
      }
      var isNeedRotate = tmp$;
      if (isNeedRotate) {
        closure$projectionOuter.rotate = [get_deg(0.0), get_deg(0.0), get_deg(0.0)];
      }
      $receiver.animation_o5zv02$(geoViz$lambda$lambda_1(fps, isNeedRotate, geoPathOuter, pathOuter, closure$world));
      $receiver.onResize_tuav61$(geoViz$lambda$lambda_2($receiver, closure$projectionOuter, pathOuter, geoPathOuter));
      return Unit;
    };
  }
  function geoViz(world, projectionName, vizWidth, vizHeight) {
    if (vizWidth === void 0)
      vizWidth = 960.0;
    if (vizHeight === void 0)
      vizHeight = 700.0;
    var projectionOuter = allProjections.get_11rb$(projectionName);
    ensureNotNull(projectionOuter).translate = new Float64Array([vizWidth / 2.0, vizHeight / 2.0]);
    return viz(geoViz$lambda(vizWidth, vizHeight, projectionName, projectionOuter, world));
  }
  function doRotate(geoPathOuter, pathOuter, world) {
    var unixTime = Date_init().getTime();
    var rotate = geoPathOuter.projection.rotate;
    var k = 60.0;
    rotate[0] = get_deg(unixTime % (360 * k) / k);
    pathOuter.clearPath();
    geoPathOuter.path_6ux19g$(world);
    geoPathOuter.projection.rotate = rotate;
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
  var currentViz;
  var selectFileId;
  var selectProjectionId;
  var buttonStartStopId;
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
  function get_buttonStartStop() {
    if (buttonStartStop == null)
      return throwUPAE('buttonStartStop');
    return buttonStartStop;
  }
  function set_buttonStartStop(buttonStartStop_0) {
    buttonStartStop = buttonStartStop_0;
  }
  var animationStarted;
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
    if (animationStarted) {
      currentViz != null ? (currentViz.stopAnimations(), Unit) : null;
    }
     else {
      currentViz != null ? (currentViz.startAnimations(), Unit) : null;
    }
    animationStarted = !animationStarted;
    return it;
  }
  function main(args) {
    set_selectFile(document.getElementById(selectFileId));
    set_selectProjection(document.getElementById(selectProjectionId));
    set_buttonStartStop(document.getElementById(buttonStartStopId));
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
    get_buttonStartStop().onclick = main$lambda_1;
    onSelectionChanged();
  }
  function onSelectionChanged() {
    var selectFile = document.getElementById(selectFileId);
    var selectProjection = document.getElementById(selectProjectionId);
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
    loadViz(fileValue, projectionValue);
    onSettingsChanged(fileValue, projectionValue);
  }
  function loadViz$lambda$lambda($receiver) {
    $receiver.id = 'viz';
    return Unit;
  }
  function loadViz$lambda(closure$filename_0, closure$projectionName_0) {
    return function ($receiver, continuation_0, suspended) {
      var instance = new Coroutine$loadViz$lambda(closure$filename_0, closure$projectionName_0, $receiver, this, continuation_0);
      if (suspended)
        return instance;
      else
        return instance.doResume(null);
    };
  }
  function Coroutine$loadViz$lambda(closure$filename_0, closure$projectionName_0, $receiver, controller, continuation_0) {
    CoroutineImpl.call(this, continuation_0);
    this.$controller = controller;
    this.exceptionState_0 = 1;
    this.local$closure$filename = closure$filename_0;
    this.local$closure$projectionName = closure$projectionName_0;
    this.local$newCanvas = void 0;
  }
  Coroutine$loadViz$lambda.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: null,
    interfaces: [CoroutineImpl]
  };
  Coroutine$loadViz$lambda.prototype = Object.create(CoroutineImpl.prototype);
  Coroutine$loadViz$lambda.prototype.constructor = Coroutine$loadViz$lambda;
  Coroutine$loadViz$lambda.prototype.doResume = function () {
    do
      try {
        switch (this.state_0) {
          case 0:
            var request = window.fetch(new Request(this.local$closure$filename));
            this.state_0 = 2;
            this.result_0 = await_0(request, this);
            if (this.result_0 === COROUTINE_SUSPENDED)
              return COROUTINE_SUSPENDED;
            continue;
          case 1:
            throw this.exception_0;
          case 2:
            var response = this.result_0;
            var oldCanvas = document.getElementById('viz');
            var parent = ensureNotNull(oldCanvas).parentElement;
            ensureNotNull(parent).removeChild(oldCanvas);
            this.local$newCanvas = createElement(document, 'canvas', loadViz$lambda$lambda);
            parent.appendChild(this.local$newCanvas);
            currentViz != null ? (currentViz.stopAnimations(), Unit) : null;
            this.state_0 = 3;
            this.result_0 = await_0(response.text(), this);
            if (this.result_0 === COROUTINE_SUSPENDED)
              return COROUTINE_SUSPENDED;
            continue;
          case 3:
            currentViz = geoViz(toGeoJsonObject(this.result_0), this.local$closure$projectionName, 500.0, 500.0);
            bindRendererOn(ensureNotNull(currentViz), this.local$newCanvas);
            var anim = animationStarted;
            if (!anim) {
              return currentViz != null && (currentViz.stopAnimations(), Unit), Unit;
            }
             else {
              this.state_0 = 4;
              continue;
            }

          case 4:
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
  function loadViz(filename, projectionName) {
    console.log('loadViz filename = ' + filename + ' projectionName = ' + projectionName);
    promise(coroutines.GlobalScope, void 0, void 0, loadViz$lambda(filename, projectionName));
  }
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
  package$geo.geoViz_ix6qni$ = geoViz;
  Object.defineProperty(package$geo, 'FPS', {
    get: FPS_getInstance
  });
  Object.defineProperty(package$geo, 'currentViz', {
    get: function () {
      return currentViz;
    },
    set: function (value) {
      currentViz = value;
    }
  });
  Object.defineProperty(package$geo, 'selectFileId', {
    get: function () {
      return selectFileId;
    }
  });
  Object.defineProperty(package$geo, 'selectProjectionId', {
    get: function () {
      return selectProjectionId;
    }
  });
  Object.defineProperty(package$geo, 'buttonStartStopId', {
    get: function () {
      return buttonStartStopId;
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
    get: get_buttonStartStop,
    set: set_buttonStartStop
  });
  Object.defineProperty(package$geo, 'animationStarted', {
    get: function () {
      return animationStarted;
    },
    set: function (value) {
      animationStarted = value;
    }
  });
  package$geo.main_kand9s$ = main;
  package$geo.loadViz = loadViz;
  allProjections = hashMapOf([to('albers', albersProjection()), to('albersUSA', alberUSAProjection(allProjections$lambda)), to('azimuthalEqualArea', azimuthalEqualAreaProjection()), to('azimuthalEquidistant', azimuthalEquidistant()), to('conicConformal', conicConformalProjection()), to('conicEqual', conicEqualAreaProjection()), to('conicEquidistant', conicEquidistantProjection()), to('equalEarth', equalEarthProjection()), to('equirectangular', equirectangularProjection()), to('gnomonic', gnomonicProjection()), to('identity', identityProjection()), to('mercator', mercatorProjection()), to('naturalEarth1', naturalEarth1Projection()), to('orthographic', orthographicProjection()), to('stereographic', stereographicProjection()), to('transverseMercator', transverseMercatorProjection())]);
  allProjectionsNames = toList(allProjections.keys);
  allFiles = listOf(['world-110m.geojson', 'world-110m-30percent.json', 'world-110m-50percent.json', 'world-110m-70percent.json']);
  projectionsToSingleFile = hashMapOf([to('albersUSA', 'us-states.json')]);
  defaultFileIndex = allFiles.indexOf_11rb$('world-110m-30percent.json');
  defaultProjectionIndex = allProjectionsNames.indexOf_11rb$('orthographic');
  currentViz = null;
  selectFileId = 'select_file';
  selectProjectionId = 'select_projection';
  buttonStartStopId = 'button_start_stop';
  animationStarted = true;
  main([]);
  Kotlin.defineModule('ex-geo-js', _);
  return _;
}));

//# sourceMappingURL=ex-geo-js.js.map
