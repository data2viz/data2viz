(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin', 'd2v-scale-js', 'd2v-color-js', 'd2v-viz-js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'), require('d2v-scale-js'), require('d2v-color-js'), require('d2v-viz-js'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'd2v-axis-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'd2v-axis-js'.");
    }
    if (typeof this['d2v-scale-js'] === 'undefined') {
      throw new Error("Error loading module 'd2v-axis-js'. Its dependency 'd2v-scale-js' was not found. Please, check whether 'd2v-scale-js' is loaded prior to 'd2v-axis-js'.");
    }
    if (typeof this['d2v-color-js'] === 'undefined') {
      throw new Error("Error loading module 'd2v-axis-js'. Its dependency 'd2v-color-js' was not found. Please, check whether 'd2v-color-js' is loaded prior to 'd2v-axis-js'.");
    }
    if (typeof this['d2v-viz-js'] === 'undefined') {
      throw new Error("Error loading module 'd2v-axis-js'. Its dependency 'd2v-viz-js' was not found. Please, check whether 'd2v-viz-js' is loaded prior to 'd2v-axis-js'.");
    }
    root['d2v-axis-js'] = factory(typeof this['d2v-axis-js'] === 'undefined' ? {} : this['d2v-axis-js'], kotlin, this['d2v-scale-js'], this['d2v-color-js'], this['d2v-viz-js']);
  }
}(this, function (_, Kotlin, $module$d2v_scale_js, $module$d2v_color_js, $module$d2v_viz_js) {
  'use strict';
  var Unit = Kotlin.kotlin.Unit;
  var coerceAtLeast = Kotlin.kotlin.ranges.coerceAtLeast_38ydlf$;
  var round = Kotlin.kotlin.math.round_14dthe$;
  var Tickable = $module$d2v_scale_js.io.data2viz.scale.Tickable;
  var List = Kotlin.kotlin.collections.List;
  var throwCCE = Kotlin.throwCCE;
  var BandedScale = $module$d2v_scale_js.io.data2viz.scale.BandedScale;
  var Colors = $module$d2v_color_js.io.data2viz.color.Colors;
  var TextHAlign = $module$d2v_viz_js.io.data2viz.viz.TextHAlign;
  var TextVAlign = $module$d2v_viz_js.io.data2viz.viz.TextVAlign;
  var toString = Kotlin.toString;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var Enum = Kotlin.kotlin.Enum;
  var throwISE = Kotlin.throwISE;
  var emptyList = Kotlin.kotlin.collections.emptyList_287e2$;
  Orient.prototype = Object.create(Enum.prototype);
  Orient.prototype.constructor = Orient;
  function axis$lambda($receiver) {
    return Unit;
  }
  function axis($receiver, orient, scale, init) {
    if (init === void 0)
      init = axis$lambda;
    var $receiver_0 = new AxisElement(orient, scale);
    init($receiver_0);
    $receiver_0.build_surbf1$($receiver);
    return $receiver_0;
  }
  function AxisElement(orient, scale) {
    this.orient = orient;
    this.scale = scale;
    this.tickValues = emptyList();
    this.tickSizeInner = 6.0;
    this.tickSizeOuter = 6.0;
    this.tickPadding = 3.0;
    this.tickFormat = AxisElement$tickFormat$lambda;
    this.k = this.orient === Orient$TOP_getInstance() || this.orient === Orient$LEFT_getInstance() ? -1 : 1;
  }
  function AxisElement$center$lambda(closure$scale, closure$offset) {
    return function (d) {
      return +closure$scale.invoke_11rb$(d) + closure$offset.v;
    };
  }
  AxisElement.prototype.center_stwmyg$ = function (scale) {
    var offset = {v: coerceAtLeast(scale.bandwidth - 1, 0.0) / 2};
    if (scale.round)
      offset.v = round(offset.v);
    return AxisElement$center$lambda(scale, offset);
  };
  function AxisElement$number$lambda(closure$scale) {
    return function (d) {
      return closure$scale.invoke_11rb$(d);
    };
  }
  AxisElement.prototype.number_vw9atz$ = function (scale) {
    return AxisElement$number$lambda(scale);
  };
  function AxisElement$build$lambda$lambda(this$AxisElement, closure$range0, closure$range1) {
    return function ($receiver) {
      $receiver.stroke = Colors.Web.black;
      $receiver.fill = null;
      $receiver.strokeWidth = 1.0;
      if (this$AxisElement.orient.isVertical()) {
        $receiver.moveTo_lu1900$(this$AxisElement.tickSizeOuter * this$AxisElement.k, closure$range0);
        $receiver.lineTo_lu1900$(-0.5 * this$AxisElement.k, closure$range0);
        $receiver.lineTo_lu1900$(-0.5 * this$AxisElement.k, closure$range1);
        $receiver.lineTo_lu1900$(this$AxisElement.tickSizeOuter * this$AxisElement.k, closure$range1);
      }
       else {
        $receiver.moveTo_lu1900$(closure$range0, this$AxisElement.tickSizeOuter * this$AxisElement.k);
        $receiver.lineTo_lu1900$(closure$range0, -0.5 * this$AxisElement.k);
        $receiver.lineTo_lu1900$(closure$range1, -0.5 * this$AxisElement.k);
        $receiver.lineTo_lu1900$(closure$range1, this$AxisElement.tickSizeOuter * this$AxisElement.k);
      }
      return Unit;
    };
  }
  function AxisElement$build$lambda$lambda$lambda$lambda(this$AxisElement, closure$position, closure$it) {
    return function ($receiver) {
      if (this$AxisElement.orient.isHorizontal())
        $receiver.translate_lu1900$(closure$position(closure$it));
      else
        $receiver.translate_lu1900$(void 0, closure$position(closure$it));
      return Unit;
    };
  }
  function AxisElement$build$lambda$lambda$lambda$lambda_0(this$AxisElement) {
    return function ($receiver) {
      $receiver.y2 = this$AxisElement.k * this$AxisElement.tickSizeInner;
      $receiver.stroke = Colors.Web.black;
      return Unit;
    };
  }
  function AxisElement$build$lambda$lambda$lambda$lambda_1(this$AxisElement) {
    return function ($receiver) {
      $receiver.x2 = this$AxisElement.k * this$AxisElement.tickSizeInner;
      $receiver.stroke = Colors.Web.black;
      return Unit;
    };
  }
  function AxisElement$build$lambda$lambda$lambda$lambda_2(this$AxisElement, closure$spacing, closure$it) {
    return function ($receiver) {
      var tmp$, tmp$_0;
      switch (this$AxisElement.orient.name) {
        case 'LEFT':
          tmp$ = TextHAlign.RIGHT;
          break;
        case 'RIGHT':
          tmp$ = TextHAlign.LEFT;
          break;
        default:tmp$ = TextHAlign.MIDDLE;
          break;
      }
      $receiver.hAlign = tmp$;
      switch (this$AxisElement.orient.name) {
        case 'TOP':
          tmp$_0 = TextVAlign.BASELINE;
          break;
        case 'BOTTOM':
          tmp$_0 = TextVAlign.HANGING;
          break;
        default:tmp$_0 = TextVAlign.MIDDLE;
          break;
      }
      $receiver.vAlign = tmp$_0;
      $receiver.fill = Colors.Web.black;
      if (this$AxisElement.orient.isHorizontal())
        $receiver.y = closure$spacing * this$AxisElement.k;
      else
        $receiver.x = closure$spacing * this$AxisElement.k;
      $receiver.textContent = this$AxisElement.tickFormat(closure$it);
      return Unit;
    };
  }
  function AxisElement$build$lambda$lambda$lambda(this$AxisElement, closure$position, closure$it, closure$spacing) {
    return function ($receiver) {
      $receiver.transform_tabxxp$(AxisElement$build$lambda$lambda$lambda$lambda(this$AxisElement, closure$position, closure$it));
      if (this$AxisElement.orient.isHorizontal())
        $receiver.line_e8vvwz$(AxisElement$build$lambda$lambda$lambda$lambda_0(this$AxisElement));
      else
        $receiver.line_e8vvwz$(AxisElement$build$lambda$lambda$lambda$lambda_1(this$AxisElement));
      $receiver.text_6q900q$(AxisElement$build$lambda$lambda$lambda$lambda_2(this$AxisElement, closure$spacing, closure$it));
      return Unit;
    };
  }
  AxisElement.prototype.build_surbf1$ = function (content) {
    var tmp$;
    var values = this.tickValues.isEmpty() && Kotlin.isType(this.scale, Tickable) ? Kotlin.isType(tmp$ = this.scale.ticks_za3lpa$(), List) ? tmp$ : throwCCE() : this.tickValues;
    var spacing = coerceAtLeast(this.tickSizeInner, 0.0) + this.tickPadding;
    var range0 = this.scale.start();
    var range1 = this.scale.end();
    var position = Kotlin.isType(this.scale, BandedScale) ? this.center_stwmyg$(this.scale) : this.number_vw9atz$(this.scale);
    content.path_omiyse$(AxisElement$build$lambda$lambda(this, range0, range1));
    var tmp$_0;
    tmp$_0 = values.iterator();
    while (tmp$_0.hasNext()) {
      var element = tmp$_0.next();
      content.group_mdx85a$(AxisElement$build$lambda$lambda$lambda(this, position, element, spacing));
    }
  };
  function AxisElement$tickFormat$lambda(n) {
    return toString(n);
  }
  AxisElement.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'AxisElement',
    interfaces: []
  };
  function Orient(name, ordinal) {
    Enum.call(this);
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function Orient_initFields() {
    Orient_initFields = function () {
    };
    Orient$TOP_instance = new Orient('TOP', 0);
    Orient$BOTTOM_instance = new Orient('BOTTOM', 1);
    Orient$LEFT_instance = new Orient('LEFT', 2);
    Orient$RIGHT_instance = new Orient('RIGHT', 3);
  }
  var Orient$TOP_instance;
  function Orient$TOP_getInstance() {
    Orient_initFields();
    return Orient$TOP_instance;
  }
  var Orient$BOTTOM_instance;
  function Orient$BOTTOM_getInstance() {
    Orient_initFields();
    return Orient$BOTTOM_instance;
  }
  var Orient$LEFT_instance;
  function Orient$LEFT_getInstance() {
    Orient_initFields();
    return Orient$LEFT_instance;
  }
  var Orient$RIGHT_instance;
  function Orient$RIGHT_getInstance() {
    Orient_initFields();
    return Orient$RIGHT_instance;
  }
  Orient.prototype.isVertical = function () {
    return this === Orient$LEFT_getInstance() || this === Orient$RIGHT_getInstance();
  };
  Orient.prototype.isHorizontal = function () {
    return this === Orient$TOP_getInstance() || this === Orient$BOTTOM_getInstance();
  };
  Orient.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Orient',
    interfaces: [Enum]
  };
  function Orient$values() {
    return [Orient$TOP_getInstance(), Orient$BOTTOM_getInstance(), Orient$LEFT_getInstance(), Orient$RIGHT_getInstance()];
  }
  Orient.values = Orient$values;
  function Orient$valueOf(name) {
    switch (name) {
      case 'TOP':
        return Orient$TOP_getInstance();
      case 'BOTTOM':
        return Orient$BOTTOM_getInstance();
      case 'LEFT':
        return Orient$LEFT_getInstance();
      case 'RIGHT':
        return Orient$RIGHT_getInstance();
      default:throwISE('No enum constant io.data2viz.axis.Orient.' + name);
    }
  }
  Orient.valueOf_61zpoe$ = Orient$valueOf;
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$axis = package$data2viz.axis || (package$data2viz.axis = {});
  package$axis.axis_j0vi0h$ = axis;
  package$axis.AxisElement = AxisElement;
  Object.defineProperty(Orient, 'TOP', {
    get: Orient$TOP_getInstance
  });
  Object.defineProperty(Orient, 'BOTTOM', {
    get: Orient$BOTTOM_getInstance
  });
  Object.defineProperty(Orient, 'LEFT', {
    get: Orient$LEFT_getInstance
  });
  Object.defineProperty(Orient, 'RIGHT', {
    get: Orient$RIGHT_getInstance
  });
  package$axis.Orient = Orient;
  Kotlin.defineModule('d2v-axis-js', _);
  return _;
}));

//# sourceMappingURL=d2v-axis-js.js.map
