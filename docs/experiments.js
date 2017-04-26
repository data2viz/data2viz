define('experiments', ['exports', 'kotlin', 'interpolate', 'color', 'core', 'tests', 'svg', 'axis'], function (_, Kotlin, $module$interpolate, $module$color, $module$core, $module$tests, $module$svg, $module$axis) {
  'use strict';
  var NumberTests = $module$interpolate.io.data2viz.interpolate.NumberTests;
  var ColorTests = $module$color.io.data2viz.color.ColorTests;
  var TicksTests = $module$core.io.data2viz.core.TicksTests;
  var ViridisTests = $module$color.io.data2viz.color.ViridisTests;
  var EaseTests = $module$interpolate.io.data2viz.interpolate.EaseTests;
  var ExceptionMatchers = $module$tests.io.data2viz.test.ExceptionMatchers;
  var StringMatchers = $module$tests.io.data2viz.test.StringMatchers;
  var IntMatchers = $module$tests.io.data2viz.test.IntMatchers;
  var LongMatchers = $module$tests.io.data2viz.test.LongMatchers;
  var DoubleMatchers = $module$tests.io.data2viz.test.DoubleMatchers;
  var TestCollectionMatchers = $module$tests.io.data2viz.test.TestCollectionMatchers;
  var htmlExecution = $module$tests.io.data2viz.test.htmlExecution_raw9bf$;
  var DomUtils = $module$tests.io.data2viz.test.DomUtils;
  var IntRange = Kotlin.kotlin.ranges.IntRange;
  var asList = Kotlin.org.w3c.dom.asList_kt9thq$;
  var identity = $module$interpolate.io.data2viz.interpolate.identity_14dthe$;
  var cubicInOut = $module$interpolate.io.data2viz.interpolate.cubicInOut_14dthe$;
  var cubicIn = $module$interpolate.io.data2viz.interpolate.cubicIn_14dthe$;
  var cubicOut = $module$interpolate.io.data2viz.interpolate.cubicOut_14dthe$;
  var quad = $module$interpolate.io.data2viz.interpolate.quad_14dthe$;
  var sin = $module$interpolate.io.data2viz.interpolate.sin_14dthe$;
  var circleIn = $module$interpolate.io.data2viz.interpolate.circleIn_14dthe$;
  var circleOut = $module$interpolate.io.data2viz.interpolate.circleOut_14dthe$;
  var listOf = Kotlin.kotlin.collections.listOf_i5x0yv$;
  var joinToString = Kotlin.kotlin.collections.joinToString_fmv235$;
  var toDouble = Kotlin.kotlin.text.toDouble_pdl1vz$;
  var namespace = $module$core.io.data2viz.core.namespace;
  var Pair = Kotlin.kotlin.Pair;
  var interpolateNumber = $module$interpolate.io.data2viz.interpolate.interpolateNumber_z8e4lc$;
  var uninterpolate = $module$interpolate.io.data2viz.interpolate.uninterpolate_z8e4lc$;
  var lazy = Kotlin.kotlin.lazy_klfg04$;
  var max = Kotlin.kotlin.collections.max_exjks8$;
  var Margins = $module$svg.io.data2viz.svg.Margins;
  var scale = $module$interpolate.io.data2viz.interpolate.scale;
  var linkedTo = $module$interpolate.io.data2viz.interpolate.linkedTo_ujzrz7$;
  var color_0 = $module$color.io.data2viz.color;
  var Axis = $module$axis.io.data2viz.axis.Axis;
  var rgba = $module$color.io.data2viz.color.rgba_1ugm5o$;
  var svg_2 = $module$svg.io.data2viz.svg.svg_9huslq$;
  var coerceAtMost = Kotlin.kotlin.ranges.coerceAtMost_38ydlf$;
  var average = Kotlin.kotlin.collections.average_plj8ka$;
  function allTests() {
    htmlExecution([new NumberTests(), new ColorTests(), new TicksTests(), new ViridisTests(), new EaseTests(), new ExceptionMatchers(), new StringMatchers(), new IntMatchers(), new LongMatchers(), new DoubleMatchers(), new TestCollectionMatchers()]);
  }
  function bindingPerfs() {
    bindingPerfs_0();
  }
  function svgPerfs() {
    svgPerfs_0();
  }
  function chart() {
    chart_0();
  }
  function mouse() {
    mouse_0();
  }
  function animate() {
    animate_0();
  }
  function bindingPerfs$TimeAndResult(time_0, result) {
    this.time = time_0;
    this.result = result;
  }
  bindingPerfs$TimeAndResult.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'TimeAndResult',
    interfaces: []
  };
  bindingPerfs$TimeAndResult.prototype.component1 = function () {
    return this.time;
  };
  bindingPerfs$TimeAndResult.prototype.component2 = function () {
    return this.result;
  };
  bindingPerfs$TimeAndResult.prototype.copy_41hqm1$ = function (time_0, result) {
    return new bindingPerfs$TimeAndResult(time_0 === void 0 ? this.time : time_0, result === void 0 ? this.result : result);
  };
  bindingPerfs$TimeAndResult.prototype.toString = function () {
    return 'TimeAndResult(time=' + Kotlin.toString(this.time) + (', result=' + Kotlin.toString(this.result)) + ')';
  };
  bindingPerfs$TimeAndResult.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.time) | 0;
    result = result * 31 + Kotlin.hashCode(this.result) | 0;
    return result;
  };
  bindingPerfs$TimeAndResult.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.time, other.time) && Kotlin.equals(this.result, other.result)))));
  };
  function bindingPerfs$time(block) {
    var time_0 = (new Date()).getTime();
    var ret = block();
    return new bindingPerfs$TimeAndResult((new Date()).getTime() - time_0, ret);
  }
  function bindingPerfs$lambda$lambda$lambda(closure$it) {
    return function ($receiver) {
      $receiver.__data__ = closure$it;
    };
  }
  function bindingPerfs$lambda(closure$count) {
    return function () {
      var tmp$;
      var svg_4 = (tmp$ = DomUtils.Companion.body.querySelector('svg')) != null ? tmp$ : Kotlin.throwNPE();
      var tmp$_0;
      tmp$_0 = (new IntRange(1, closure$count)).iterator();
      while (tmp$_0.hasNext()) {
        var element = tmp$_0.next();
        append(svg_4, 'g', bindingPerfs$lambda$lambda$lambda(element));
      }
    };
  }
  function bindingPerfs$lambda_0() {
    var $receiver = asList(DomUtils.Companion.body.querySelectorAll('g'));
    var destination = Kotlin.kotlin.collections.ArrayList_init_ww73n8$();
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      var tmp$_0;
      if ((typeof (tmp$_0 = element.__data__) === 'number' ? tmp$_0 : Kotlin.throwCCE()) % 2 === 0) {
        destination.add_11rb$(element);
      }
    }
    return destination.size;
  }
  function bindingPerfs_0() {
    var time = bindingPerfs$time;
    var count = 2500;
    var creationTime = time(bindingPerfs$lambda(count));
    console.log(count.toString() + ' elements with associated  data in ' + creationTime.time + ' ms');
    var selectionTime = time(bindingPerfs$lambda_0);
    console.log('Filtrage de ' + selectionTime.result + ' en ' + selectionTime.time + ' ms');
  }
  function append$lambda($receiver) {
  }
  function append($receiver, elementName, init) {
    if (init === void 0)
      init = append$lambda;
    var newElement = document.createElementNS($receiver.namespaceURI, elementName);
    init(newElement);
    $receiver.appendChild(newElement);
    return newElement;
  }
  function animate$lambda$lambda$lambda$lambda(closure$barHeight, closure$index) {
    return function ($receiver) {
      $receiver.translate_z8e4lc$(void 0, Kotlin.imul(closure$barHeight, closure$index));
    };
  }
  function animate$lambda$lambda$lambda$lambda_0(this$) {
    return function ($receiver) {
      $receiver.width = this$.width;
    };
  }
  function animate$lambda$lambda$lambda(closure$barHeight, closure$index, closure$easeFunction, this$) {
    return function ($receiver) {
      $receiver.height = closure$barHeight;
      $receiver.width = 100;
      $receiver.transform_3fcjn3$(animate$lambda$lambda$lambda$lambda(closure$barHeight, closure$index));
      $receiver.transition_sg0011$(2000, closure$easeFunction, animate$lambda$lambda$lambda$lambda_0(this$));
    };
  }
  function animate$lambda(closure$barHeight, closure$easeFunctions) {
    return function ($receiver) {
      $receiver.width = 1000;
      $receiver.height = Kotlin.imul(closure$barHeight, closure$easeFunctions.size);
      var $receiver_0 = closure$easeFunctions;
      var tmp$, tmp$_0;
      var index = 0;
      tmp$ = $receiver_0.iterator();
      while (tmp$.hasNext()) {
        var item = tmp$.next();
        $receiver.rect_pfcwqj$(animate$lambda$lambda$lambda(closure$barHeight, (tmp$_0 = index, index = tmp$_0 + 1 | 0, tmp$_0), item, $receiver));
      }
    };
  }
  function animate_0() {
    var easeFunctions = listOf([Kotlin.getCallableRef('identity', function (a) {
      return identity(a);
    }), Kotlin.getCallableRef('cubicInOut', function (t) {
      return cubicInOut(t);
    }), Kotlin.getCallableRef('cubicIn', function (x) {
      return cubicIn(x);
    }), Kotlin.getCallableRef('cubicOut', function (t) {
      return cubicOut(t);
    }), Kotlin.getCallableRef('quad', function (x) {
      return quad(x);
    }), Kotlin.getCallableRef('sin', function (x) {
      return sin(x);
    }), Kotlin.getCallableRef('circleIn', function (t) {
      return circleIn(t);
    }), Kotlin.getCallableRef('circleOut', function (t) {
      return circleOut(t);
    })]);
    var barHeight = 20;
    svg_0(animate$lambda(barHeight, easeFunctions));
  }
  function svg$lambda($receiver) {
  }
  function svg_0(init) {
    if (init === void 0)
      init = svg$lambda;
    var tmp$;
    var svgElement = document.querySelector('svg');
    if (svgElement == null) {
      svgElement = svg$Companion_getInstance().createSVGElement_61zpoe$('svg');
      ((tmp$ = document.querySelector('body')) != null ? tmp$ : Kotlin.throwNPE()).append(svgElement);
    }
    return new Selection(new SVGElement(svgElement), init);
  }
  function Transform() {
    this.commands_0 = Kotlin.kotlin.collections.LinkedHashMap_init_q3lmfv$();
  }
  Transform.prototype.translate_z8e4lc$ = function (x, y) {
    if (x === void 0)
      x = 0;
    if (y === void 0)
      y = 0;
    this.commands_0.put_xwzc9p$('translate', 'translate(' + x + ', ' + y + ')');
  };
  Transform.prototype.scale_z8e4lc$ = function (x, y) {
    if (x === void 0)
      x = 1;
    if (y === void 0)
      y = x;
    this.commands_0.put_xwzc9p$('scale', 'scale(' + x + ', ' + y + ')');
  };
  Transform.prototype.skewX_3p81yu$ = function (a) {
    this.commands_0.put_xwzc9p$('skewX', 'skewX(' + a + ')');
  };
  Transform.prototype.skewY_3p81yu$ = function (a) {
    this.commands_0.put_xwzc9p$('skewY', 'skewX(' + a + ')');
  };
  Transform.prototype.rotate_a2j3zq$ = function (angle, x, y) {
    if (x === void 0)
      x = 0;
    if (y === void 0)
      y = 0;
    this.commands_0.put_xwzc9p$('rotate', 'rotate(' + angle + ', ' + x + ', ' + y);
  };
  Transform.prototype.toCommand = function () {
    return joinToString(this.commands_0.values, ' ');
  };
  Transform.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'Transform',
    interfaces: []
  };
  function Selection(element, init) {
    if (init === void 0)
      init = Selection_init$lambda;
    this.element = element;
    init(this.element);
  }
  function Selection_init$lambda($receiver) {
  }
  Selection.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'Selection',
    interfaces: []
  };
  function ElementWrapper() {
  }
  ElementWrapper.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'ElementWrapper',
    interfaces: []
  };
  function SVGElement(element) {
    this.element_yzjlyu$_0 = element;
  }
  Object.defineProperty(SVGElement.prototype, 'element', {
    get: function () {
      return this.element_yzjlyu$_0;
    },
    set: function (element) {
      this.element_yzjlyu$_0 = element;
    }
  });
  Object.defineProperty(SVGElement.prototype, 'width', {
    get: function () {
      var tmp$;
      return toDouble((tmp$ = this.element.getAttribute('width')) != null ? tmp$ : Kotlin.throwNPE());
    },
    set: function (value) {
      this.element.setAttribute('width', value.toString());
    }
  });
  Object.defineProperty(SVGElement.prototype, 'height', {
    get: function () {
      var tmp$;
      return toDouble((tmp$ = this.element.getAttribute('height')) != null ? tmp$ : Kotlin.throwNPE());
    },
    set: function (value) {
      this.element.setAttribute('height', value.toString());
    }
  });
  SVGElement.prototype.rect_pfcwqj$ = function (init) {
    var rect = svg$Companion_getInstance().rect_ayitmc$();
    init(rect);
    this.element.append(rect.element);
  };
  SVGElement.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'SVGElement',
    interfaces: [ElementWrapper, Has2D]
  };
  function HasWidth() {
  }
  HasWidth.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'HasWidth',
    interfaces: []
  };
  function HasHeight() {
  }
  HasHeight.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'HasHeight',
    interfaces: []
  };
  function Has2D() {
  }
  Object.defineProperty(Has2D.prototype, 'center', {
    get: function () {
      return new Point(Kotlin.numberToDouble(this.width) / 2, Kotlin.numberToDouble(this.height) / 2);
    }
  });
  Object.defineProperty(Has2D.prototype, 'topRight', {
    get: function () {
      return new Point(Kotlin.numberToDouble(this.width), 0.0);
    }
  });
  Has2D.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'Has2D',
    interfaces: [HasWidth, HasHeight]
  };
  function svg_1() {
    svg$Companion_getInstance();
  }
  function svg$Companion() {
    svg$Companion_instance = this;
  }
  svg$Companion.prototype.createSVGElement_61zpoe$ = function (name) {
    return document.createElementNS(namespace.Companion.svg, name);
  };
  svg$Companion.prototype.rect_ayitmc$ = function (width, height, stroke, fill) {
    if (width === void 0)
      width = 100.0;
    if (height === void 0)
      height = 100.0;
    if (stroke === void 0)
      stroke = '#555';
    if (fill === void 0)
      fill = '#fff';
    var $receiver = new RectElement(this.createSVGElement_61zpoe$('rect'));
    var closure$width = width;
    var closure$height = height;
    var closure$stroke = stroke;
    var closure$fill = fill;
    $receiver.width = closure$width;
    $receiver.height = closure$height;
    $receiver.stroke = closure$stroke;
    $receiver.fill = closure$fill;
    return $receiver;
  };
  svg$Companion.$metadata$ = {
    kind: Kotlin.Kind.OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var svg$Companion_instance = null;
  function svg$Companion_getInstance() {
    if (svg$Companion_instance === null) {
      new svg$Companion();
    }
    return svg$Companion_instance;
  }
  svg_1.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'svg',
    interfaces: []
  };
  function RectElement(element) {
    RectElement$Companion_getInstance();
    this.element_xla3ym$_0 = element;
    this.stroke_xla3ym$_0 = '';
    this.fill_xla3ym$_0 = '';
    this.stroke = '#555';
    this.fill = '#777';
    this.height_xla3ym$_0 = 1.0;
    this.record_0 = false;
    this.recorded$delegate = lazy(RectElement$recorded$lambda);
  }
  Object.defineProperty(RectElement.prototype, 'element', {
    get: function () {
      return this.element_xla3ym$_0;
    }
  });
  Object.defineProperty(RectElement.prototype, 'stroke', {
    get: function () {
      return this.stroke_xla3ym$_0;
    },
    set: function (value) {
      this.setAttribute_0('stroke', value);
    }
  });
  Object.defineProperty(RectElement.prototype, 'fill', {
    get: function () {
      return this.fill_xla3ym$_0;
    },
    set: function (value) {
      this.setAttribute_0('fill', value);
    }
  });
  function RectElement$Companion() {
    RectElement$Companion_instance = this;
  }
  RectElement$Companion.$metadata$ = {
    kind: Kotlin.Kind.OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var RectElement$Companion_instance = null;
  function RectElement$Companion_getInstance() {
    if (RectElement$Companion_instance === null) {
      new RectElement$Companion();
    }
    return RectElement$Companion_instance;
  }
  Object.defineProperty(RectElement.prototype, 'width', {
    get: function () {
      var tmp$;
      return toDouble((tmp$ = this.element.getAttribute('width')) != null ? tmp$ : Kotlin.throwNPE());
    },
    set: function (value) {
      this.setAttribute_0('width', value.toString());
    }
  });
  Object.defineProperty(RectElement.prototype, 'height', {
    get: function () {
      return this.height_xla3ym$_0;
    },
    set: function (value) {
      this.setAttribute_0('height', value.toString());
    }
  });
  RectElement.prototype.transform_3fcjn3$ = function (init) {
    var tmp$ = this.setAttribute_0;
    var $receiver = new Transform();
    init($receiver);
    tmp$.call(this, 'transform', $receiver.toCommand());
  };
  Object.defineProperty(RectElement.prototype, 'recorded_0', {
    get: function () {
      var $receiver = this.recorded$delegate;
      new Kotlin.PropertyMetadata('recorded');
      return $receiver.value;
    }
  });
  RectElement.prototype.setAttribute_0 = function (name, value) {
    if (this.record_0)
      this.recorded_0.add_11rb$(new Pair(name, value));
    else
      this.element.setAttribute(name, value);
  };
  RectElement.prototype.recorder_0 = function () {
    var recorder = new RectElement(this.element);
    recorder.record_0 = true;
    return recorder;
  };
  function RectElement$transition$animate$lambda(closure$widthTransition, closure$timeFunction, closure$time, closure$currentTime, this$RectElement, closure$animate) {
    return function (it) {
      this$RectElement.width = closure$widthTransition(closure$timeFunction(Kotlin.numberToDouble(closure$time(closure$currentTime))));
      closure$animate();
    };
  }
  function RectElement$transition$animate(closure$endTime, closure$endWidth, this$RectElement, closure$widthTransition, closure$timeFunction, closure$time) {
    return function closure$animate() {
      var currentTime = (new Date()).getTime();
      if (currentTime > closure$endTime) {
        this$RectElement.width = closure$endWidth;
        return;
      }
      window.requestAnimationFrame(RectElement$transition$animate$lambda(closure$widthTransition, closure$timeFunction, closure$time, currentTime, this$RectElement, closure$animate));
    };
  }
  RectElement.prototype.transition_sg0011$ = function (duration, timeFunction, endState) {
    var recorder = this.recorder_0();
    endState(recorder);
    var $receiver = recorder.recorded_0;
    var first$result;
    first$break: {
      var tmp$;
      tmp$ = $receiver.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        if (Kotlin.equals(element.first, 'width')) {
          first$result = element;
          break first$break;
        }
      }
      throw new Kotlin.kotlin.NoSuchElementException('Collection contains no element matching the predicate.');
    }
    var endWidth = toDouble(first$result.second);
    var startTime = (new Date()).getTime();
    var endTime = startTime + duration;
    var widthTransition = interpolateNumber(this.width, endWidth);
    var time_0 = uninterpolate(startTime, endTime);
    var animate_1 = RectElement$transition$animate(endTime, endWidth, this, widthTransition, timeFunction, time_0);
    animate_1();
  };
  function RectElement$recorded$lambda() {
    return Kotlin.kotlin.collections.ArrayList_init_ww73n8$();
  }
  RectElement.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'RectElement',
    interfaces: [ElementWrapper]
  };
  function Point(x, y) {
    Point$Companion_getInstance();
    if (x === void 0)
      x = 0.0;
    if (y === void 0)
      y = 0.0;
    this.x = x;
    this.y = y;
  }
  function Point$Companion() {
    Point$Companion_instance = this;
    this.origin = new Point();
  }
  Point$Companion.$metadata$ = {
    kind: Kotlin.Kind.OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var Point$Companion_instance = null;
  function Point$Companion_getInstance() {
    if (Point$Companion_instance === null) {
      new Point$Companion();
    }
    return Point$Companion_instance;
  }
  Point.prototype.plus_m5hqez$ = function (speed) {
    return new Point(this.x + speed.vx, this.y + speed.vy);
  };
  Point.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'Point',
    interfaces: []
  };
  Point.prototype.component1 = function () {
    return this.x;
  };
  Point.prototype.component2 = function () {
    return this.y;
  };
  Point.prototype.copy_lu1900$ = function (x, y) {
    return new Point(x === void 0 ? this.x : x, y === void 0 ? this.y : y);
  };
  Point.prototype.toString = function () {
    return 'Point(x=' + Kotlin.toString(this.x) + (', y=' + Kotlin.toString(this.y)) + ')';
  };
  Point.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.x) | 0;
    result = result * 31 + Kotlin.hashCode(this.y) | 0;
    return result;
  };
  Point.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.x, other.x) && Kotlin.equals(this.y, other.y)))));
  };
  function Speed(vx, vy) {
    if (vx === void 0)
      vx = 0.0;
    if (vy === void 0)
      vy = 0.0;
    this.vx = vx;
    this.vy = vy;
  }
  Speed.prototype.plus_m5hqez$ = function (speed) {
    return new Speed(this.vx + speed.vx, this.vy + speed.vy);
  };
  Speed.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'Speed',
    interfaces: []
  };
  Speed.prototype.component1 = function () {
    return this.vx;
  };
  Speed.prototype.component2 = function () {
    return this.vy;
  };
  Speed.prototype.copy_lu1900$ = function (vx, vy) {
    return new Speed(vx === void 0 ? this.vx : vx, vy === void 0 ? this.vy : vy);
  };
  Speed.prototype.toString = function () {
    return 'Speed(vx=' + Kotlin.toString(this.vx) + (', vy=' + Kotlin.toString(this.vy)) + ')';
  };
  Speed.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.vx) | 0;
    result = result * 31 + Kotlin.hashCode(this.vy) | 0;
    return result;
  };
  Speed.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.vx, other.vx) && Kotlin.equals(this.vy, other.vy)))));
  };
  function chart$lambda(closure$barPadding, closure$barWidth) {
    return function (i) {
      return Kotlin.imul(i, closure$barPadding + closure$barWidth | 0);
    };
  }
  function chart$lambda$lambda(closure$totalWidth, closure$totalHeight) {
    return function ($receiver) {
      $receiver.width = closure$totalWidth;
      $receiver.height = closure$totalHeight;
      $receiver.stroke = color_0.colors.black;
      $receiver.strokeWidth = '1';
      $receiver.fill = color_0.colors.white;
    };
  }
  function chart$lambda$lambda$lambda($receiver) {
    $receiver.translate_z8e4lc$(47, 10);
  }
  function chart$lambda$lambda_0(closure$maxValue, closure$yLoc) {
    return function ($receiver) {
      $receiver.transform_1ogs0a$(chart$lambda$lambda$lambda);
      (new Axis(0, closure$maxValue, closure$yLoc)).appendTo_3pjutd$($receiver);
    };
  }
  function chart$lambda$lambda$lambda_0(closure$margin) {
    return function ($receiver) {
      $receiver.translate_z8e4lc$(closure$margin.left, closure$margin.top);
    };
  }
  function chart$lambda$lambda$lambda_1(closure$totalWidth, closure$margin, closure$totalHeight) {
    return function ($receiver) {
      $receiver.width = closure$totalWidth - closure$margin.horizontalMargins | 0;
      $receiver.height = closure$totalHeight - closure$margin.verticalMargins | 0;
      $receiver.fill = rgba(0, 0, 0, 0.1);
    };
  }
  function chart$lambda$lambda$lambda$lambda$lambda(closure$xLoc, closure$index, closure$yLoc) {
    return function ($receiver) {
      $receiver.translate_z8e4lc$(closure$xLoc(closure$index), closure$yLoc(0));
    };
  }
  function chart$lambda$lambda$lambda$lambda$lambda$lambda(closure$xLoc, closure$index, closure$yLoc, closure$time, closure$data) {
    return function ($receiver) {
      $receiver.translate_z8e4lc$(closure$xLoc(closure$index), closure$yLoc(closure$time * closure$data));
    };
  }
  function chart$lambda$lambda$lambda$lambda$lambda_0(closure$xLoc, closure$index, closure$yLoc, closure$data, this$) {
    return function (time_0) {
      this$.transform_1ogs0a$(chart$lambda$lambda$lambda$lambda$lambda$lambda(closure$xLoc, closure$index, closure$yLoc, time_0, closure$data));
    };
  }
  function chart$lambda$lambda$lambda$lambda$lambda$lambda_0(closure$yScale, closure$data, this$) {
    return function (time_0) {
      this$.height = time_0 * Kotlin.numberToDouble(closure$yScale(closure$data));
    };
  }
  function chart$lambda$lambda$lambda$lambda$lambda_1(closure$barWidth, closure$animate, closure$yScale, closure$data) {
    return function ($receiver) {
      $receiver.width = closure$barWidth;
      $receiver.fill = color_0.colors.steelblue;
      $receiver.height = 0;
      closure$animate.invoke_huw4wd$(chart$lambda$lambda$lambda$lambda$lambda$lambda_0(closure$yScale, closure$data, $receiver));
    };
  }
  function chart$lambda$lambda$lambda$lambda$lambda$lambda_1(this$) {
    return function (time_0) {
      this$.fill = rgba(255, 255, 255, time_0);
    };
  }
  function chart$lambda$lambda$lambda$lambda$lambda$lambda_2(closure$barWidth) {
    return function ($receiver) {
      $receiver.translate_z8e4lc$(closure$barWidth / 2 | 0);
    };
  }
  function chart$lambda$lambda$lambda$lambda$lambda$lambda_3($receiver) {
    $receiver.fontFamily_61zpoe$('sans-serif');
    $receiver.fontSize_61zpoe$('10px');
  }
  function chart$lambda$lambda$lambda$lambda$lambda_2(closure$animate, closure$data, closure$barWidth) {
    return function ($receiver) {
      $receiver.fill = null;
      closure$animate.invoke_huw4wd$(chart$lambda$lambda$lambda$lambda$lambda$lambda_1($receiver));
      $receiver.text = closure$data.toString();
      $receiver.transform_1ogs0a$(chart$lambda$lambda$lambda$lambda$lambda$lambda_2(closure$barWidth));
      $receiver.setAttribute_jyasbz$('alignment-baseline', 'before-edge');
      $receiver.setAttribute_jyasbz$('text-anchor', 'middle');
      $receiver.style_dv3ovl$(chart$lambda$lambda$lambda$lambda$lambda$lambda_3);
    };
  }
  function chart$lambda$lambda$lambda$lambda(closure$index, closure$xLoc, closure$yLoc, closure$data, closure$barWidth, closure$yScale) {
    return function ($receiver) {
      var animate_3 = animate_2(1000, closure$index * 40 | 0);
      $receiver.transform_1ogs0a$(chart$lambda$lambda$lambda$lambda$lambda(closure$xLoc, closure$index, closure$yLoc));
      animate_3.invoke_huw4wd$(chart$lambda$lambda$lambda$lambda$lambda_0(closure$xLoc, closure$index, closure$yLoc, closure$data, $receiver));
      $receiver.rect_jh2hm2$(chart$lambda$lambda$lambda$lambda$lambda_1(closure$barWidth, animate_3, closure$yScale, closure$data));
      $receiver.text_b0yamr$(chart$lambda$lambda$lambda$lambda$lambda_2(animate_3, closure$data, closure$barWidth));
    };
  }
  function chart$lambda$lambda_1(closure$margin, closure$totalWidth, closure$totalHeight, closure$datas, closure$xLoc, closure$yLoc, closure$barWidth, closure$yScale) {
    return function ($receiver) {
      $receiver.transform_1ogs0a$(chart$lambda$lambda$lambda_0(closure$margin));
      $receiver.rect_jh2hm2$(chart$lambda$lambda$lambda_1(closure$totalWidth, closure$margin, closure$totalHeight));
      var $receiver_0 = closure$datas;
      var tmp$, tmp$_0;
      var index = 0;
      tmp$ = $receiver_0.iterator();
      while (tmp$.hasNext()) {
        var item = tmp$.next();
        var closure$xLoc_0 = closure$xLoc;
        var closure$yLoc_0 = closure$yLoc;
        var closure$barWidth_0 = closure$barWidth;
        var closure$yScale_0 = closure$yScale;
        $receiver.g_pjdmh5$(chart$lambda$lambda$lambda$lambda((tmp$_0 = index, index = tmp$_0 + 1 | 0, tmp$_0), closure$xLoc_0, closure$yLoc_0, item, closure$barWidth_0, closure$yScale_0));
      }
    };
  }
  function chart$lambda_0(closure$totalWidth, closure$totalHeight, closure$maxValue, closure$yLoc, closure$margin, closure$datas, closure$xLoc, closure$barWidth, closure$yScale) {
    return function ($receiver) {
      $receiver.width = closure$totalWidth;
      $receiver.height = closure$totalHeight;
      $receiver.rect_jh2hm2$(chart$lambda$lambda(closure$totalWidth, closure$totalHeight));
      $receiver.g_pjdmh5$(chart$lambda$lambda_0(closure$maxValue, closure$yLoc));
      $receiver.g_pjdmh5$(chart$lambda$lambda_1(closure$margin, closure$totalWidth, closure$totalHeight, closure$datas, closure$xLoc, closure$yLoc, closure$barWidth, closure$yScale));
    };
  }
  function chart_0() {
    var tmp$;
    var datas = listOf([55, 44, 30, 23, 17, 14, 16, 25, 41, 61, 85, 101, 95, 105, 114, 150, 180, 210, 125, 100, 71, 75, 72, 67]);
    var maxValue = (tmp$ = max(datas)) != null ? tmp$ : Kotlin.throwNPE();
    var barWidth = 20;
    var barPadding = 3;
    var graphWidth = Kotlin.imul(datas.size, barPadding + barWidth | 0) - barPadding | 0;
    var margin = new Margins(10, 10, 10, 50);
    var totalWidth = graphWidth + margin.horizontalMargins | 0;
    var totalHeight = 400;
    var xLoc = chart$lambda(barPadding, barWidth);
    var yLoc = scale.linear.numberToNumber_qw4oic$(linkedTo(0, totalHeight - margin.verticalMargins | 0), linkedTo(maxValue, 0));
    var yScale = scale.linear.numberToNumber_qw4oic$(linkedTo(0, 0), linkedTo(maxValue, totalHeight - margin.verticalMargins | 0));
    svg_2(chart$lambda_0(totalWidth, totalHeight, maxValue, yLoc, margin, datas, xLoc, barWidth, yScale));
  }
  function animate_2(duration, delay, timeFunction) {
    if (duration === void 0)
      duration = 250;
    if (delay === void 0)
      delay = 0;
    if (timeFunction === void 0)
      timeFunction = Kotlin.getCallableRef('cubicInOut', function (t) {
        return cubicInOut(t);
      });
    return new Animation(duration, delay, timeFunction);
  }
  function Animation(duration, delay, timeFunction) {
    this.timeFunction = timeFunction;
    this.startTime = (new Date()).getTime() + delay;
    this.endTime = this.startTime + duration;
    this.blocksOfAnimation = Kotlin.kotlin.collections.ArrayList_init_ww73n8$();
    var time_0 = uninterpolate(this.startTime, this.endTime);
    var animate = Animation_init$animate(this, time_0);
    animate();
  }
  Animation.prototype.invoke_huw4wd$ = function (animation) {
    this.blocksOfAnimation.add_11rb$(animation);
  };
  function Animation_init$animate$lambda(closure$animate) {
    return function (it) {
      closure$animate();
    };
  }
  function Animation_init$animate$lambda_0(this$Animation, closure$time, closure$currentTime, closure$animate) {
    return function (it) {
      var $receiver = this$Animation.blocksOfAnimation;
      var tmp$;
      tmp$ = $receiver.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        element(this$Animation.timeFunction(Kotlin.numberToDouble(closure$time(closure$currentTime))));
      }
      closure$animate();
    };
  }
  function Animation_init$animate(this$Animation, closure$time) {
    return function closure$animate() {
      var currentTime = (new Date()).getTime();
      if (currentTime < this$Animation.startTime)
        window.requestAnimationFrame(Animation_init$animate$lambda(closure$animate));
      else if (currentTime > this$Animation.endTime) {
        var tmp$;
        tmp$ = this$Animation.blocksOfAnimation.iterator();
        while (tmp$.hasNext()) {
          var element = tmp$.next();
          element(1.0);
        }
      }
       else {
        window.requestAnimationFrame(Animation_init$animate$lambda_0(this$Animation, closure$time, currentTime, closure$animate));
      }
    };
  }
  Animation.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'Animation',
    interfaces: []
  };
  function mouse$lambda$lambda$lambda$lambda($receiver, it) {
    $receiver.fill = color_0.colors.red;
  }
  function mouse$lambda$lambda$lambda$lambda_0($receiver, it) {
    $receiver.fill = color_0.colors.steelblue;
  }
  function mouse$lambda$lambda$lambda(closure$x, closure$data) {
    return function ($receiver) {
      closure$x.v = closure$x.v + (closure$data + 5) | 0;
      $receiver.cx = closure$x.v;
      $receiver.cy = 40;
      $receiver.r = closure$data / 2 | 0;
      $receiver.fill = color_0.colors.steelblue;
      $receiver.on_ti1ww2$('mouseenter', mouse$lambda$lambda$lambda$lambda);
      $receiver.on_ti1ww2$('mouseout', mouse$lambda$lambda$lambda$lambda_0);
    };
  }
  function mouse$lambda(closure$datas, closure$x) {
    return function ($receiver) {
      $receiver.width = 600;
      $receiver.height = 600;
      var $receiver_0 = closure$datas;
      var tmp$;
      tmp$ = $receiver_0.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        $receiver.circle_dca3qi$(mouse$lambda$lambda$lambda(closure$x, element));
      }
    };
  }
  function mouse_0() {
    var datas = listOf([30, 20, 40]);
    var x = {v: 0};
    svg_2(mouse$lambda(datas, x));
  }
  function svgPerfs$lambda($receiver) {
    $receiver.width = 800;
    $receiver.height = 800;
  }
  function svgPerfs$lambda_0(it) {
    return new RandomParticule();
  }
  function svgPerfs$lambda_1(closure$particules) {
    return function ($receiver) {
      $receiver.text = closure$particules.length.toString();
    };
  }
  function svgPerfs$render$lambda(closure$render) {
    return function (it) {
      closure$render();
    };
  }
  function svgPerfs$render(closure$circles, closure$domainToSvg, closure$fpsCalculator) {
    return function closure$render() {
      var $receiver = closure$circles;
      var tmp$;
      tmp$ = $receiver.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        var closure$domainToSvg_0 = closure$domainToSvg;
        var particule = element.element.__data__;
        particule.updatePositionAndSpeed();
        element.transform_61zpoe$(translate(closure$domainToSvg_0(particule.position)));
        element.radius = coerceAtMost(1.0 + 1000.0 * Math.abs(particule.speed.vx * particule.speed.vy), 10.0);
      }
      closure$fpsCalculator.updateFPS();
      window.requestAnimationFrame(svgPerfs$render$lambda(closure$render));
    };
  }
  function svgPerfs_0() {
    var viz = selectOrCreateSVG(svgPerfs$lambda);
    var particules = Kotlin.newArrayF(2500, svgPerfs$lambda_0);
    var destination = Kotlin.kotlin.collections.ArrayList_init_ww73n8$(particules.length);
    var tmp$;
    for (tmp$ = 0; tmp$ !== particules.length; ++tmp$) {
      var item = particules[tmp$];
      var tmp$_0 = destination.add_11rb$;
      var circle = svg$Companion_getInstance_0().circle_uv6b4u$(void 0, '#555', '#aaa');
      datum(circle, item);
      viz.append_d5diee$(circle);
      tmp$_0.call(destination, circle);
    }
    var circles = destination;
    var domainToSvg = scale$linear_getInstance().pointsToPoints_c9sjbg$(to(Point$Companion_getInstance_0().origin, viz.element.center), to(new Point_0(5.0, 5.0), viz.element.topRight));
    selectTextElement('#num span', svgPerfs$lambda_1(particules));
    var fpsCalculator = new FpsCalculator('#fps span');
    var render = svgPerfs$render(circles, domainToSvg, fpsCalculator);
    render();
  }
  function RandomParticule() {
    this.position = new Point_0();
    this.speed = new Speed_0();
  }
  RandomParticule.prototype.updatePositionAndSpeed = function () {
    this.position = this.position.plus_aqo4ec$(this.speed);
    this.speed = this.speed.plus_aqo4ec$(new Speed_0(0.04 * (Math.random() - 0.5) - 0.05 * this.speed.vx - 5.0E-4 * this.position.x, 0.04 * (Math.random() - 0.5) - 0.05 * this.speed.vy - 5.0E-4 * this.position.y));
  };
  RandomParticule.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'RandomParticule',
    interfaces: []
  };
  var datum_0 = Kotlin.defineInlineFunction('experiments.io.data2viz.experiments.perfs.datum_jh1ccf$', function (datum$T_0, isT, $receiver) {
    return $receiver.element.__data__;
  });
  function datum($receiver, value) {
    $receiver.element.__data__ = value;
  }
  function DomainToViz(domain, viz) {
    this.domain = domain;
    this.viz = viz;
  }
  DomainToViz.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'DomainToViz',
    interfaces: []
  };
  DomainToViz.prototype.component1 = function () {
    return this.domain;
  };
  DomainToViz.prototype.component2 = function () {
    return this.viz;
  };
  DomainToViz.prototype.copy_xwzc9p$ = function (domain, viz) {
    return new DomainToViz(domain === void 0 ? this.domain : domain, viz === void 0 ? this.viz : viz);
  };
  DomainToViz.prototype.toString = function () {
    return 'DomainToViz(domain=' + Kotlin.toString(this.domain) + (', viz=' + Kotlin.toString(this.viz)) + ')';
  };
  DomainToViz.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.domain) | 0;
    result = result * 31 + Kotlin.hashCode(this.viz) | 0;
    return result;
  };
  DomainToViz.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.domain, other.domain) && Kotlin.equals(this.viz, other.viz)))));
  };
  function to($receiver, that) {
    return new DomainToViz($receiver, that);
  }
  function scale_0() {
  }
  function scale$linear() {
    scale$linear_instance = this;
  }
  function scale$linear$pointsToPoints$lambda(closure$start, closure$end, this$linear) {
    return function (pt) {
      return new Point_0(this$linear.doublesToDoubles_doud2s$(to(closure$start.domain.x, closure$start.viz.x), to(closure$end.domain.x, closure$end.viz.x))(pt.x), this$linear.doublesToDoubles_doud2s$(to(closure$start.domain.y, closure$start.viz.y), to(closure$end.domain.y, closure$end.viz.y))(pt.y));
    };
  }
  scale$linear.prototype.pointsToPoints_c9sjbg$ = function (start, end) {
    return scale$linear$pointsToPoints$lambda(start, end, this);
  };
  function scale$linear$doublesToDoubles$lambda(closure$end, closure$start) {
    return function (domain) {
      return domain * (closure$end.viz - closure$start.viz) / (closure$end.domain - closure$start.domain) + closure$start.viz;
    };
  }
  scale$linear.prototype.doublesToDoubles_doud2s$ = function (start, end) {
    return scale$linear$doublesToDoubles$lambda(end, start);
  };
  scale$linear.$metadata$ = {
    kind: Kotlin.Kind.OBJECT,
    simpleName: 'linear',
    interfaces: []
  };
  var scale$linear_instance = null;
  function scale$linear_getInstance() {
    if (scale$linear_instance === null) {
      new scale$linear();
    }
    return scale$linear_instance;
  }
  scale_0.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'scale',
    interfaces: []
  };
  function selectTextElement$lambda($receiver) {
  }
  function selectTextElement(selector, init) {
    if (init === void 0)
      init = selectTextElement$lambda;
    var tmp$;
    var element = (tmp$ = document.querySelector(selector)) != null ? tmp$ : Kotlin.throwNPE();
    return new Selection_0(new TextElement(element), init);
  }
  function selectOrCreateSVG(init) {
    var tmp$;
    var svgElement = document.querySelector('svg');
    if (svgElement == null) {
      console.log('creating element');
      svgElement = document.createElementNS(namespace.Companion.svg, 'svg');
      ((tmp$ = document.querySelector('body')) != null ? tmp$ : Kotlin.throwNPE()).append(svgElement);
    }
    return new Selection_0(new SVGElement_0(svgElement), init);
  }
  function Selection_0(element, init) {
    if (init === void 0)
      init = Selection_init$lambda_0;
    this.element = element;
    init(this.element);
  }
  Selection_0.prototype.append_d5diee$ = function (element) {
    this.element.element.appendChild(element.element);
  };
  function Selection_init$lambda_0($receiver) {
  }
  Selection_0.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'Selection',
    interfaces: []
  };
  function ElementWrapper_0() {
  }
  ElementWrapper_0.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'ElementWrapper',
    interfaces: []
  };
  function TextElement(element) {
    this.element_ozrxxu$_0 = element;
  }
  Object.defineProperty(TextElement.prototype, 'element', {
    get: function () {
      return this.element_ozrxxu$_0;
    }
  });
  Object.defineProperty(TextElement.prototype, 'text', {
    get: function () {
      return this.element.textContent;
    },
    set: function (value) {
      this.element.textContent = value;
    }
  });
  TextElement.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'TextElement',
    interfaces: [ElementWrapper_0, HasText]
  };
  function SVGElement_0(element) {
    this.element_cyfedd$_0 = element;
  }
  Object.defineProperty(SVGElement_0.prototype, 'element', {
    get: function () {
      return this.element_cyfedd$_0;
    }
  });
  Object.defineProperty(SVGElement_0.prototype, 'width', {
    get: function () {
      var tmp$;
      return toDouble((tmp$ = this.element.getAttribute('width')) != null ? tmp$ : Kotlin.throwNPE());
    },
    set: function (value) {
      this.element.setAttribute('width', value.toString());
    }
  });
  Object.defineProperty(SVGElement_0.prototype, 'height', {
    get: function () {
      var tmp$;
      return toDouble((tmp$ = this.element.getAttribute('height')) != null ? tmp$ : Kotlin.throwNPE());
    },
    set: function (value) {
      this.element.setAttribute('height', value.toString());
    }
  });
  SVGElement_0.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'SVGElement',
    interfaces: [ElementWrapper_0, Has2D_0]
  };
  function HasText() {
  }
  HasText.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'HasText',
    interfaces: []
  };
  function HasWidth_0() {
  }
  HasWidth_0.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'HasWidth',
    interfaces: []
  };
  function HasHeight_0() {
  }
  HasHeight_0.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'HasHeight',
    interfaces: []
  };
  function Has2D_0() {
  }
  Object.defineProperty(Has2D_0.prototype, 'center', {
    get: function () {
      return new Point_0(Kotlin.numberToDouble(this.width) / 2, Kotlin.numberToDouble(this.height) / 2);
    }
  });
  Object.defineProperty(Has2D_0.prototype, 'topRight', {
    get: function () {
      return new Point_0(Kotlin.numberToDouble(this.width), 0.0);
    }
  });
  Has2D_0.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'Has2D',
    interfaces: [HasWidth_0, HasHeight_0]
  };
  function svg_3() {
    svg$Companion_getInstance_0();
  }
  function svg$Companion_0() {
    svg$Companion_instance_0 = this;
  }
  svg$Companion_0.prototype.circle_uv6b4u$ = function (radius, stroke, fill) {
    if (radius === void 0)
      radius = 1.0;
    if (stroke === void 0)
      stroke = '#555';
    if (fill === void 0)
      fill = '#fff';
    var $receiver = new CircleElement(document.createElementNS(namespace.Companion.svg, 'circle'));
    var closure$radius = radius;
    var closure$stroke = stroke;
    var closure$fill = fill;
    $receiver.radius = closure$radius;
    $receiver.stroke = closure$stroke;
    $receiver.fill = closure$fill;
    return $receiver;
  };
  svg$Companion_0.$metadata$ = {
    kind: Kotlin.Kind.OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var svg$Companion_instance_0 = null;
  function svg$Companion_getInstance_0() {
    if (svg$Companion_instance_0 === null) {
      new svg$Companion_0();
    }
    return svg$Companion_instance_0;
  }
  svg_3.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'svg',
    interfaces: []
  };
  function CircleElement(element) {
    CircleElement$Companion_getInstance();
    this.element_y4y34h$_0 = element;
    this.stroke_y4y34h$_0 = '';
    this.fill_y4y34h$_0 = '';
    this.stroke = '#555';
    this.fill = '#777';
    this.radius_y4y34h$_0 = 1.0;
  }
  Object.defineProperty(CircleElement.prototype, 'element', {
    get: function () {
      return this.element_y4y34h$_0;
    }
  });
  Object.defineProperty(CircleElement.prototype, 'stroke', {
    get: function () {
      return this.stroke_y4y34h$_0;
    },
    set: function (value) {
      this.setAttribute_0('stroke', value);
    }
  });
  Object.defineProperty(CircleElement.prototype, 'fill', {
    get: function () {
      return this.fill_y4y34h$_0;
    },
    set: function (value) {
      this.setAttribute_0('fill', value);
    }
  });
  function CircleElement$Companion() {
    CircleElement$Companion_instance = this;
  }
  CircleElement$Companion.$metadata$ = {
    kind: Kotlin.Kind.OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var CircleElement$Companion_instance = null;
  function CircleElement$Companion_getInstance() {
    if (CircleElement$Companion_instance === null) {
      new CircleElement$Companion();
    }
    return CircleElement$Companion_instance;
  }
  Object.defineProperty(CircleElement.prototype, 'radius', {
    get: function () {
      return this.radius_y4y34h$_0;
    },
    set: function (value) {
      this.setAttribute_0('r', value.toString());
    }
  });
  CircleElement.prototype.transform_61zpoe$ = function (value) {
    this.setAttribute_0('transform', value);
  };
  CircleElement.prototype.transform_h4ejuu$ = function (transformFunction) {
    this.setAttribute_0('transform', transformFunction());
  };
  CircleElement.prototype.setAttribute_0 = function (name, value) {
    this.element.setAttribute(name, value);
  };
  CircleElement.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'CircleElement',
    interfaces: [ElementWrapper_0]
  };
  function translate(point) {
    return translate_0(point.x, point.y);
  }
  function translate_0(x, y) {
    return 'translate(' + x + ',' + y + ')';
  }
  function Point_0(x, y) {
    Point$Companion_getInstance_0();
    if (x === void 0)
      x = 0.0;
    if (y === void 0)
      y = 0.0;
    this.x = x;
    this.y = y;
  }
  function Point$Companion_0() {
    Point$Companion_instance_0 = this;
    this.origin = new Point_0();
  }
  Point$Companion_0.$metadata$ = {
    kind: Kotlin.Kind.OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var Point$Companion_instance_0 = null;
  function Point$Companion_getInstance_0() {
    if (Point$Companion_instance_0 === null) {
      new Point$Companion_0();
    }
    return Point$Companion_instance_0;
  }
  Point_0.prototype.plus_aqo4ec$ = function (speed) {
    return new Point_0(this.x + speed.vx, this.y + speed.vy);
  };
  Point_0.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'Point',
    interfaces: []
  };
  Point_0.prototype.component1 = function () {
    return this.x;
  };
  Point_0.prototype.component2 = function () {
    return this.y;
  };
  Point_0.prototype.copy_lu1900$ = function (x, y) {
    return new Point_0(x === void 0 ? this.x : x, y === void 0 ? this.y : y);
  };
  Point_0.prototype.toString = function () {
    return 'Point(x=' + Kotlin.toString(this.x) + (', y=' + Kotlin.toString(this.y)) + ')';
  };
  Point_0.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.x) | 0;
    result = result * 31 + Kotlin.hashCode(this.y) | 0;
    return result;
  };
  Point_0.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.x, other.x) && Kotlin.equals(this.y, other.y)))));
  };
  function Speed_0(vx, vy) {
    if (vx === void 0)
      vx = 0.0;
    if (vy === void 0)
      vy = 0.0;
    this.vx = vx;
    this.vy = vy;
  }
  Speed_0.prototype.plus_aqo4ec$ = function (speed) {
    return new Speed_0(this.vx + speed.vx, this.vy + speed.vy);
  };
  Speed_0.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'Speed',
    interfaces: []
  };
  Speed_0.prototype.component1 = function () {
    return this.vx;
  };
  Speed_0.prototype.component2 = function () {
    return this.vy;
  };
  Speed_0.prototype.copy_lu1900$ = function (vx, vy) {
    return new Speed_0(vx === void 0 ? this.vx : vx, vy === void 0 ? this.vy : vy);
  };
  Speed_0.prototype.toString = function () {
    return 'Speed(vx=' + Kotlin.toString(this.vx) + (', vy=' + Kotlin.toString(this.vy)) + ')';
  };
  Speed_0.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.vx) | 0;
    result = result * 31 + Kotlin.hashCode(this.vy) | 0;
    return result;
  };
  Speed_0.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.vx, other.vx) && Kotlin.equals(this.vy, other.vy)))));
  };
  function FpsCalculator(fpsSelector) {
    this.fpsSelector = fpsSelector;
    this.fps_0 = selectTextElement(this.fpsSelector);
    this.average_fps_0 = Kotlin.kotlin.collections.ArrayList_init_ww73n8$();
    this.time0_0 = (new Date()).getTime();
    this.time1_0 = (new Date()).getTime();
  }
  FpsCalculator.prototype.updateFPS = function () {
    this.time1_0 = (new Date()).getTime();
    if (this.time1_0 !== this.time0_0) {
      var curFps = Math.round(1000.0 / (this.time1_0 - this.time0_0));
      this.average_fps_0.add_11rb$(curFps);
      if (this.average_fps_0.size === 10) {
        this.fps_0.element.text = average(this.average_fps_0).toString();
        this.average_fps_0.clear();
      }
    }
    this.time0_0 = this.time1_0;
  };
  FpsCalculator.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'FpsCalculator',
    interfaces: []
  };
  Object.defineProperty(SVGElement.prototype, 'center', Object.getOwnPropertyDescriptor(Has2D.prototype, 'center'));
  Object.defineProperty(SVGElement.prototype, 'topRight', Object.getOwnPropertyDescriptor(Has2D.prototype, 'topRight'));
  Object.defineProperty(SVGElement_0.prototype, 'center', Object.getOwnPropertyDescriptor(Has2D_0.prototype, 'center'));
  Object.defineProperty(SVGElement_0.prototype, 'topRight', Object.getOwnPropertyDescriptor(Has2D_0.prototype, 'topRight'));
  _.allTests = allTests;
  _.bindingPerfs = bindingPerfs;
  _.svgPerfs = svgPerfs;
  _.chart = chart;
  _.mouse = mouse;
  _.animate = animate;
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$experiments = package$data2viz.experiments || (package$data2viz.experiments = {});
  package$experiments.bindingPerfs = bindingPerfs_0;
  package$experiments.append_ldvnw0$ = append;
  var package$animate = package$experiments.animate || (package$experiments.animate = {});
  package$animate.animate = animate_0;
  package$animate.svg_qdm1xv$ = svg_0;
  package$animate.Transform = Transform;
  package$animate.Selection = Selection;
  package$animate.ElementWrapper = ElementWrapper;
  package$animate.SVGElement = SVGElement;
  package$animate.HasWidth = HasWidth;
  package$animate.HasHeight = HasHeight;
  package$animate.Has2D = Has2D;
  Object.defineProperty(svg_1, 'Companion', {
    get: svg$Companion_getInstance
  });
  package$animate.svg = svg_1;
  Object.defineProperty(RectElement, 'Companion', {
    get: RectElement$Companion_getInstance
  });
  package$animate.RectElement = RectElement;
  Object.defineProperty(Point, 'Companion', {
    get: Point$Companion_getInstance
  });
  package$animate.Point = Point;
  package$animate.Speed = Speed;
  var package$chart = package$experiments.chart || (package$experiments.chart = {});
  package$chart.chart = chart_0;
  package$chart.animate_hopgm8$ = animate_2;
  package$chart.Animation = Animation;
  var package$mouse = package$experiments.mouse || (package$experiments.mouse = {});
  package$mouse.mouse = mouse_0;
  var package$perfs = package$experiments.perfs || (package$experiments.perfs = {});
  package$perfs.svgPerfs = svgPerfs_0;
  package$perfs.RandomParticule = RandomParticule;
  package$perfs.datum_g2hjpo$ = datum;
  package$perfs.DomainToViz = DomainToViz;
  package$perfs.to_ujzrz7$ = to;
  Object.defineProperty(scale_0, 'linear', {
    get: scale$linear_getInstance
  });
  package$perfs.scale = scale_0;
  package$perfs.selectTextElement_d71ln7$ = selectTextElement;
  package$perfs.selectOrCreateSVG_t9z470$ = selectOrCreateSVG;
  package$perfs.Selection = Selection_0;
  package$perfs.ElementWrapper = ElementWrapper_0;
  package$perfs.TextElement = TextElement;
  package$perfs.SVGElement = SVGElement_0;
  package$perfs.HasText = HasText;
  package$perfs.HasWidth = HasWidth_0;
  package$perfs.HasHeight = HasHeight_0;
  package$perfs.Has2D = Has2D_0;
  Object.defineProperty(svg_3, 'Companion', {
    get: svg$Companion_getInstance_0
  });
  package$perfs.svg = svg_3;
  Object.defineProperty(CircleElement, 'Companion', {
    get: CircleElement$Companion_getInstance
  });
  package$perfs.CircleElement = CircleElement;
  package$perfs.translate_ap06tp$ = translate;
  package$perfs.translate_lu1900$ = translate_0;
  Object.defineProperty(Point_0, 'Companion', {
    get: Point$Companion_getInstance_0
  });
  package$perfs.Point = Point_0;
  package$perfs.Speed = Speed_0;
  package$perfs.FpsCalculator = FpsCalculator;
  Kotlin.defineModule('experiments', _);
  return _;
});

//@ sourceMappingURL=experiments.js.map
