(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin', 'd2v-core-js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'), require('d2v-core-js'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'd2v-shape-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'd2v-shape-js'.");
    }
    if (typeof this['d2v-core-js'] === 'undefined') {
      throw new Error("Error loading module 'd2v-shape-js'. Its dependency 'd2v-core-js' was not found. Please, check whether 'd2v-core-js' is loaded prior to 'd2v-shape-js'.");
    }
    root['d2v-shape-js'] = factory(typeof this['d2v-shape-js'] === 'undefined' ? {} : this['d2v-shape-js'], kotlin, this['d2v-core-js']);
  }
}(this, function (_, Kotlin, $module$d2v_core_js) {
  'use strict';
  var ensureNotNull = Kotlin.ensureNotNull;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var Math_0 = Math;
  var Array_0 = Array;
  var Kind_INTERFACE = Kotlin.Kind.INTERFACE;
  var Kind_OBJECT = Kotlin.Kind.OBJECT;
  var math = Kotlin.kotlin.math;
  var Enum = Kotlin.kotlin.Enum;
  var throwISE = Kotlin.throwISE;
  var IntRange = Kotlin.kotlin.ranges.IntRange;
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  var Path = $module$d2v_core_js.io.data2viz.geom.Path;
  var isNaN_0 = Kotlin.kotlin.isNaN_yrwdxr$;
  var until = Kotlin.kotlin.ranges.until_dqglrj$;
  var downTo = Kotlin.kotlin.ranges.downTo_dqglrj$;
  var Unit = Kotlin.kotlin.Unit;
  var getCallableRef = Kotlin.getCallableRef;
  var checkIndexOverflow = Kotlin.kotlin.collections.checkIndexOverflow_za3lpa$;
  var copyToArray = Kotlin.kotlin.collections.copyToArray;
  var sortedWith = Kotlin.kotlin.collections.sortedWith_eknfly$;
  var wrapFunction = Kotlin.wrapFunction;
  var Comparator = Kotlin.kotlin.Comparator;
  var reversed = Kotlin.kotlin.collections.reversed_7wnvza$;
  var plus = Kotlin.kotlin.collections.plus_mydzjv$;
  var collectionSizeOrDefault = Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$;
  var ArrayList_init_0 = Kotlin.kotlin.collections.ArrayList_init_ww73n8$;
  Symbols.prototype = Object.create(Enum.prototype);
  Symbols.prototype.constructor = Symbols;
  MonotoneX.prototype = Object.create(AbstractMonotone.prototype);
  MonotoneX.prototype.constructor = MonotoneX;
  MonotoneY.prototype = Object.create(AbstractMonotone.prototype);
  MonotoneY.prototype.constructor = MonotoneY;
  RadialLinear.prototype = Object.create(AbstractRadial.prototype);
  RadialLinear.prototype.constructor = RadialLinear;
  Radial.prototype = Object.create(AbstractRadial.prototype);
  Radial.prototype.constructor = Radial;
  Step.prototype = Object.create(AbstractStep.prototype);
  Step.prototype.constructor = Step;
  StepBefore.prototype = Object.create(AbstractStep.prototype);
  StepBefore.prototype.constructor = StepBefore;
  StepAfter.prototype = Object.create(AbstractStep.prototype);
  StepAfter.prototype.constructor = StepAfter;
  StackOffset.prototype = Object.create(Enum.prototype);
  StackOffset.prototype.constructor = StackOffset;
  StackOrder.prototype = Object.create(Enum.prototype);
  StackOrder.prototype.constructor = StackOrder;
  function arcBuilder(init) {
    var $receiver = new ArcBuilder();
    init($receiver);
    return $receiver;
  }
  function ArcBuilder() {
    this.innerRadius = const_0(0.0);
    this.outerRadius = const_0(100.0);
    this.cornerRadius = const_0(0.0);
    this.padRadius = null;
    this.startAngle = const_0(0.0);
    this.endAngle = const_0(0.0);
    this.padAngle = const_0(0.0);
  }
  ArcBuilder.prototype.centroid_11rb$ = function (datum) {
    var r = this.innerRadius(datum) + this.outerRadius(datum) / 2.0;
    var a = this.startAngle(datum) + this.endAngle(datum) / 2.0 - halfPi;
    return [Math_0.cos(a) * r, Math_0.sin(a) * r];
  };
  ArcBuilder.prototype.buildArcForDatum_lr2v95$ = function (datum, path) {
    var tmp$;
    var r0 = this.innerRadius(datum);
    var r1 = this.outerRadius(datum);
    var a0 = this.startAngle(datum) - halfPi;
    var a1 = this.endAngle(datum) - halfPi;
    var x = a1 - a0;
    var da = Math_0.abs(x);
    var cw = a1 > a0;
    if (r1 < r0) {
      var r = r1;
      r1 = r0;
      r0 = r;
    }
    if (r1 <= epsilon)
      path.moveTo_lu1900$(0.0, 0.0);
    else if (da > tau - epsilon) {
      path.moveTo_lu1900$(r1 * Math_0.cos(a0), r1 * Math_0.sin(a0));
      path.arc_6p3vsx$(0.0, 0.0, r1, a0, a1, !cw);
      if (r0 > epsilon) {
        path.moveTo_lu1900$(r0 * Math_0.cos(a1), r0 * Math_0.sin(a1));
        path.arc_6p3vsx$(0.0, 0.0, r0, a1, a0, cw);
      }
    }
     else {
      var a01 = a0;
      var a11 = a1;
      var a00 = a0;
      var a10 = a1;
      var da0 = da;
      var da1 = da;
      var ap = this.padAngle(datum) / 2.0;
      if (ap <= epsilon)
        tmp$ = 0.0;
      else {
        var tmp$_0;
        if (this.padRadius != null)
          tmp$_0 = ensureNotNull(this.padRadius)(datum);
        else {
          var x_0 = r0 * r0 + r1 * r1;
          tmp$_0 = Math_0.sqrt(x_0);
        }
        var temp = tmp$_0;
        tmp$ = temp !== 0.0 ? 1.0 : 0.0;
      }
      var rp = tmp$;
      var x_1 = r1 - r0;
      var a = Math_0.abs(x_1) / 2;
      var b = this.cornerRadius(datum);
      var rc = Math_0.min(a, b);
      var rc0 = rc;
      var rc1 = rc;
      if (rp > epsilon) {
        var p0 = asin(rp / r0 * ap);
        var p1 = asin(rp / r1 * ap);
        da0 -= p0 * 2;
        if (da0 > epsilon) {
          p0 *= cw ? 1.0 : -1.0;
          a00 += p0;
          a10 -= p0;
        }
         else {
          da0 = 0.0;
          a10 = (a0 + a1) / 2;
          a00 = a10;
        }
        da1 -= p1 * 2;
        if (da1 > epsilon) {
          p1 *= cw ? 1.0 : -1.0;
          a01 += p1;
          a11 -= p1;
        }
         else {
          da1 = 0.0;
          a11 = (a0 + a1) / 2;
          a01 = a11;
        }
      }
      var tmp$_1 = r1;
      var x_2 = a01;
      var x01 = tmp$_1 * Math_0.cos(x_2);
      var tmp$_2 = r1;
      var x_3 = a01;
      var y01 = tmp$_2 * Math_0.sin(x_3);
      var tmp$_3 = r0;
      var x_4 = a10;
      var x10 = tmp$_3 * Math_0.cos(x_4);
      var tmp$_4 = r0;
      var x_5 = a10;
      var y10 = tmp$_4 * Math_0.sin(x_5);
      var tmp$_5 = r1;
      var x_6 = a11;
      var x11 = tmp$_5 * Math_0.cos(x_6);
      var tmp$_6 = r1;
      var x_7 = a11;
      var y11 = tmp$_6 * Math_0.sin(x_7);
      var tmp$_7 = r0;
      var x_8 = a00;
      var x00 = tmp$_7 * Math_0.cos(x_8);
      var tmp$_8 = r0;
      var x_9 = a00;
      var y00 = tmp$_8 * Math_0.sin(x_9);
      if (rc > epsilon) {
        if (da < pi) {
          var oc = da0 > epsilon ? this.intersect_0(x01, y01, x00, y00, x11, y11, x10, y10) : [x10, y10];
          var ax = x01 - oc[0];
          var ay = y01 - oc[1];
          var bx = x11 - oc[0];
          var by = y11 - oc[1];
          var tmp$_9 = ax * bx + ay * by;
          var x_10 = ax * ax + ay * ay;
          var tmp$_10 = Math_0.sqrt(x_10);
          var x_11 = bx * bx + by * by;
          var x_12 = acos(tmp$_9 / (tmp$_10 * Math_0.sqrt(x_11))) / 2;
          var kc = 1 / Math_0.sin(x_12);
          var x_13 = oc[0] * oc[0] + oc[1] * oc[1];
          var lc = Math_0.sqrt(x_13);
          var b_0 = (r0 - lc) / (kc - 1);
          rc0 = Math_0.min(rc, b_0);
          var b_1 = (r1 - lc) / (kc + 1);
          rc1 = Math_0.min(rc, b_1);
        }
      }
      if (!(da1 > epsilon))
        path.moveTo_lu1900$(x01, y01);
      else if (rc1 > epsilon) {
        var t0 = this.cornerTangents_0(x00, y00, x01, y01, r1, rc1, cw);
        var t1 = this.cornerTangents_0(x11, y11, x10, y10, r1, rc1, cw);
        path.moveTo_lu1900$(t0.cx + t0.x01, t0.cy + t0.y01);
        if (rc1 < rc) {
          var tmp$_11 = t0.cx;
          var tmp$_12 = t0.cy;
          var tmp$_13 = rc1;
          var y = t0.y01;
          var x_14 = t0.x01;
          var tmp$_14 = Math_0.atan2(y, x_14);
          var y_0 = t1.y01;
          var x_15 = t1.x01;
          path.arc_6p3vsx$(tmp$_11, tmp$_12, tmp$_13, tmp$_14, Math_0.atan2(y_0, x_15), !cw);
        }
         else {
          var tmp$_15 = t0.cx;
          var tmp$_16 = t0.cy;
          var tmp$_17 = rc1;
          var y_1 = t0.y01;
          var x_16 = t0.x01;
          var tmp$_18 = Math_0.atan2(y_1, x_16);
          var y_2 = t0.y11;
          var x_17 = t0.x11;
          path.arc_6p3vsx$(tmp$_15, tmp$_16, tmp$_17, tmp$_18, Math_0.atan2(y_2, x_17), !cw);
          var tmp$_19 = r1;
          var y_3 = t0.cy + t0.y11;
          var x_18 = t0.cx + t0.x11;
          var tmp$_20 = Math_0.atan2(y_3, x_18);
          var y_4 = t1.cy + t1.y11;
          var x_19 = t1.cx + t1.x11;
          path.arc_6p3vsx$(0.0, 0.0, tmp$_19, tmp$_20, Math_0.atan2(y_4, x_19), !cw);
          var tmp$_21 = t1.cx;
          var tmp$_22 = t1.cy;
          var tmp$_23 = rc1;
          var y_5 = t1.y11;
          var x_20 = t1.x11;
          var tmp$_24 = Math_0.atan2(y_5, x_20);
          var y_6 = t1.y01;
          var x_21 = t1.x01;
          path.arc_6p3vsx$(tmp$_21, tmp$_22, tmp$_23, tmp$_24, Math_0.atan2(y_6, x_21), !cw);
        }
      }
       else {
        path.moveTo_lu1900$(x01, y01);
        path.arc_6p3vsx$(0.0, 0.0, r1, a01, a11, !cw);
      }
      if (!(r0 > epsilon) || !(da0 > epsilon))
        path.lineTo_lu1900$(x10, y10);
      else if (rc0 > epsilon) {
        var t0_0 = this.cornerTangents_0(x10, y10, x11, y11, r0, -rc0, cw);
        var t1_0 = this.cornerTangents_0(x01, y01, x00, y00, r0, -rc0, cw);
        path.lineTo_lu1900$(t0_0.cx + t0_0.x01, t0_0.cy + t0_0.y01);
        if (rc0 < rc) {
          var tmp$_25 = t0_0.cx;
          var tmp$_26 = t0_0.cy;
          var tmp$_27 = rc0;
          var y_7 = t0_0.y01;
          var x_22 = t0_0.x01;
          var tmp$_28 = Math_0.atan2(y_7, x_22);
          var y_8 = t1_0.y01;
          var x_23 = t1_0.x01;
          path.arc_6p3vsx$(tmp$_25, tmp$_26, tmp$_27, tmp$_28, Math_0.atan2(y_8, x_23), !cw);
        }
         else {
          var tmp$_29 = t0_0.cx;
          var tmp$_30 = t0_0.cy;
          var tmp$_31 = rc0;
          var y_9 = t0_0.y01;
          var x_24 = t0_0.x01;
          var tmp$_32 = Math_0.atan2(y_9, x_24);
          var y_10 = t0_0.y11;
          var x_25 = t0_0.x11;
          path.arc_6p3vsx$(tmp$_29, tmp$_30, tmp$_31, tmp$_32, Math_0.atan2(y_10, x_25), !cw);
          var tmp$_33 = r0;
          var y_11 = t0_0.cy + t0_0.y11;
          var x_26 = t0_0.cx + t0_0.x11;
          var tmp$_34 = Math_0.atan2(y_11, x_26);
          var y_12 = t1_0.cy + t1_0.y11;
          var x_27 = t1_0.cx + t1_0.x11;
          path.arc_6p3vsx$(0.0, 0.0, tmp$_33, tmp$_34, Math_0.atan2(y_12, x_27), cw);
          var tmp$_35 = t1_0.cx;
          var tmp$_36 = t1_0.cy;
          var tmp$_37 = rc0;
          var y_13 = t1_0.y11;
          var x_28 = t1_0.x11;
          var tmp$_38 = Math_0.atan2(y_13, x_28);
          var y_14 = t1_0.y01;
          var x_29 = t1_0.x01;
          path.arc_6p3vsx$(tmp$_35, tmp$_36, tmp$_37, tmp$_38, Math_0.atan2(y_14, x_29), !cw);
        }
      }
       else
        path.arc_6p3vsx$(0.0, 0.0, r0, a10, a00, cw);
    }
    path.closePath();
    return path;
  };
  ArcBuilder.prototype.cornerTangents_0 = function (x0, y0, x1, y1, r1, rc, cw) {
    var x01 = x0 - x1;
    var y01 = y0 - y1;
    var tmp$ = cw ? rc : -rc;
    var x = x01 * x01 + y01 * y01;
    var lo = tmp$ / Math_0.sqrt(x);
    var ox = lo * y01;
    var oy = -lo * x01;
    var x11 = x0 + ox;
    var y11 = y0 + oy;
    var x10 = x1 + ox;
    var y10 = y1 + oy;
    var x00 = (x11 + x10) / 2;
    var y00 = (y11 + y10) / 2;
    var dx = x10 - x11;
    var dy = y10 - y11;
    var d2 = dx * dx + dy * dy;
    var r = r1 - rc;
    var D = x11 * y10 - x10 * y11;
    var tmp$_0 = dy < 0 ? -1.0 : 1.0;
    var b = r * r * d2 - D * D;
    var x_0 = Math_0.max(0.0, b);
    var d = tmp$_0 * Math_0.sqrt(x_0);
    var cx0 = (D * dy - dx * d) / d2;
    var cy0 = (-D * dx - dy * d) / d2;
    var cx1 = (D * dy + dx * d) / d2;
    var cy1 = (-D * dx + dy * d) / d2;
    var dx0 = cx0 - x00;
    var dy0 = cy0 - y00;
    var dx1 = cx1 - x00;
    var dy1 = cy1 - y00;
    if (dx0 * dx0 + dy0 * dy0 > dx1 * dx1 + dy1 * dy1) {
      cx0 = cx1;
      cy0 = cy1;
    }
    return new CornerTangentValues(cx0, cy0, -ox, -oy, cx0 * (r1 / r - 1), cy0 * (r1 / r - 1));
  };
  ArcBuilder.prototype.intersect_0 = function (x0, y0, x1, y1, x2, y2, x3, y3) {
    var x10 = x1 - x0;
    var y10 = y1 - y0;
    var x32 = x3 - x2;
    var y32 = y3 - y2;
    var t = (x32 * (y0 - y2) - y32 * (x0 - x2)) / (y32 * x10 - x32 * y10);
    return [x0 + t * x10, y0 + t * y10];
  };
  ArcBuilder.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ArcBuilder',
    interfaces: []
  };
  function ArcParams(startAngle, endAngle, padAngle, value, index, data) {
    this.startAngle = startAngle;
    this.endAngle = endAngle;
    this.padAngle = padAngle;
    this.value = value;
    this.index = index;
    this.data = data;
  }
  ArcParams.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ArcParams',
    interfaces: []
  };
  ArcParams.prototype.component1 = function () {
    return this.startAngle;
  };
  ArcParams.prototype.component2 = function () {
    return this.endAngle;
  };
  ArcParams.prototype.component3 = function () {
    return this.padAngle;
  };
  ArcParams.prototype.component4 = function () {
    return this.value;
  };
  ArcParams.prototype.component5 = function () {
    return this.index;
  };
  ArcParams.prototype.component6 = function () {
    return this.data;
  };
  ArcParams.prototype.copy_2j2mcc$ = function (startAngle, endAngle, padAngle, value, index, data) {
    return new ArcParams(startAngle === void 0 ? this.startAngle : startAngle, endAngle === void 0 ? this.endAngle : endAngle, padAngle === void 0 ? this.padAngle : padAngle, value === void 0 ? this.value : value, index === void 0 ? this.index : index, data === void 0 ? this.data : data);
  };
  ArcParams.prototype.toString = function () {
    return 'ArcParams(startAngle=' + Kotlin.toString(this.startAngle) + (', endAngle=' + Kotlin.toString(this.endAngle)) + (', padAngle=' + Kotlin.toString(this.padAngle)) + (', value=' + Kotlin.toString(this.value)) + (', index=' + Kotlin.toString(this.index)) + (', data=' + Kotlin.toString(this.data)) + ')';
  };
  ArcParams.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.startAngle) | 0;
    result = result * 31 + Kotlin.hashCode(this.endAngle) | 0;
    result = result * 31 + Kotlin.hashCode(this.padAngle) | 0;
    result = result * 31 + Kotlin.hashCode(this.value) | 0;
    result = result * 31 + Kotlin.hashCode(this.index) | 0;
    result = result * 31 + Kotlin.hashCode(this.data) | 0;
    return result;
  };
  ArcParams.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.startAngle, other.startAngle) && Kotlin.equals(this.endAngle, other.endAngle) && Kotlin.equals(this.padAngle, other.padAngle) && Kotlin.equals(this.value, other.value) && Kotlin.equals(this.index, other.index) && Kotlin.equals(this.data, other.data)))));
  };
  function CornerTangentValues(cx, cy, x01, y01, x11, y11) {
    this.cx = cx;
    this.cy = cy;
    this.x01 = x01;
    this.y01 = y01;
    this.x11 = x11;
    this.y11 = y11;
  }
  CornerTangentValues.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'CornerTangentValues',
    interfaces: []
  };
  CornerTangentValues.prototype.component1 = function () {
    return this.cx;
  };
  CornerTangentValues.prototype.component2 = function () {
    return this.cy;
  };
  CornerTangentValues.prototype.component3 = function () {
    return this.x01;
  };
  CornerTangentValues.prototype.component4 = function () {
    return this.y01;
  };
  CornerTangentValues.prototype.component5 = function () {
    return this.x11;
  };
  CornerTangentValues.prototype.component6 = function () {
    return this.y11;
  };
  CornerTangentValues.prototype.copy_15yvbs$ = function (cx, cy, x01, y01, x11, y11) {
    return new CornerTangentValues(cx === void 0 ? this.cx : cx, cy === void 0 ? this.cy : cy, x01 === void 0 ? this.x01 : x01, y01 === void 0 ? this.y01 : y01, x11 === void 0 ? this.x11 : x11, y11 === void 0 ? this.y11 : y11);
  };
  CornerTangentValues.prototype.toString = function () {
    return 'CornerTangentValues(cx=' + Kotlin.toString(this.cx) + (', cy=' + Kotlin.toString(this.cy)) + (', x01=' + Kotlin.toString(this.x01)) + (', y01=' + Kotlin.toString(this.y01)) + (', x11=' + Kotlin.toString(this.x11)) + (', y11=' + Kotlin.toString(this.y11)) + ')';
  };
  CornerTangentValues.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.cx) | 0;
    result = result * 31 + Kotlin.hashCode(this.cy) | 0;
    result = result * 31 + Kotlin.hashCode(this.x01) | 0;
    result = result * 31 + Kotlin.hashCode(this.y01) | 0;
    result = result * 31 + Kotlin.hashCode(this.x11) | 0;
    result = result * 31 + Kotlin.hashCode(this.y11) | 0;
    return result;
  };
  CornerTangentValues.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.cx, other.cx) && Kotlin.equals(this.cy, other.cy) && Kotlin.equals(this.x01, other.x01) && Kotlin.equals(this.y01, other.y01) && Kotlin.equals(this.x11, other.x11) && Kotlin.equals(this.y11, other.y11)))));
  };
  function areaBuilder(init) {
    var $receiver = new AreaBuilder();
    init($receiver);
    return $receiver;
  }
  function AreaBuilder() {
    this.curve = curves_getInstance().linear;
    this.xBaseline = const_0(0.0);
    this.yBaseline = const_0(0.0);
    this.xTopline = null;
    this.yTopline = null;
    this.defined = const_0(true);
  }
  AreaBuilder.prototype.render_pzuqs$ = function (data, path) {
    var tmp$;
    var n = data.size;
    var array = Array_0(n);
    var tmp$_0;
    tmp$_0 = array.length - 1 | 0;
    for (var i = 0; i <= tmp$_0; i++) {
      array[i] = 0.0;
    }
    var x0z = array;
    var array_0 = Array_0(n);
    var tmp$_1;
    tmp$_1 = array_0.length - 1 | 0;
    for (var i_0 = 0; i_0 <= tmp$_1; i_0++) {
      array_0[i_0] = 0.0;
    }
    var y0z = array_0;
    var j = 0;
    var defined0 = false;
    var output = this.curve(path);
    for (var i_1 = 0; i_1 <= n; i_1++) {
      var areaNotEnded = i_1 < n;
      var undefined_0 = !(areaNotEnded && this.defined(data.get_za3lpa$(i_1)));
      if (undefined_0 === defined0) {
        defined0 = !defined0;
        if (defined0) {
          j = i_1;
          output.areaStart();
          output.lineStart();
        }
         else {
          output.lineEnd();
          output.lineStart();
          tmp$ = j;
          for (var k = i_1 - 1 | 0; k >= tmp$; k--) {
            output.point_lu1900$(x0z[k], y0z[k]);
          }
          output.lineEnd();
          output.areaEnd();
        }
      }
      if (defined0) {
        var d = data.get_za3lpa$(i_1);
        x0z[i_1] = this.xBaseline(d);
        y0z[i_1] = this.yBaseline(d);
        var outputX = this.xTopline != null ? ensureNotNull(this.xTopline)(d) : x0z[i_1];
        var outputY = this.yTopline != null ? ensureNotNull(this.yTopline)(d) : y0z[i_1];
        output.point_lu1900$(outputX, outputY);
      }
    }
    return path;
  };
  AreaBuilder.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'AreaBuilder',
    interfaces: []
  };
  function Curve() {
  }
  Curve.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Curve',
    interfaces: []
  };
  function curves() {
    curves_instance = this;
    this.basis = curves$basis$lambda;
    this.basisClosed = curves$basisClosed$lambda;
    this.basisOpen = curves$basisOpen$lambda;
    this.bundle = curves$bundle$lambda;
    this.cardinal = curves$cardinal$lambda;
    this.cardinalClosed = curves$cardinalClosed$lambda;
    this.cardinalOpen = curves$cardinalOpen$lambda;
    this.catmullRom = curves$catmullRom$lambda;
    this.catmullRomClosed = curves$catmullRomClosed$lambda;
    this.catmullRomOpen = curves$catmullRomOpen$lambda;
    this.linear = curves$linear$lambda;
    this.linearClosed = curves$linearClosed$lambda;
    this.monotoneX = curves$monotoneX$lambda;
    this.monotoneY = curves$monotoneY$lambda;
    this.natural = curves$natural$lambda;
    this.step = curves$step$lambda;
    this.stepBefore = curves$stepBefore$lambda;
    this.stepAfter = curves$stepAfter$lambda;
  }
  function curves$basis$lambda(path) {
    return new Basis(path);
  }
  function curves$basisClosed$lambda(path) {
    return new BasisClosed(path);
  }
  function curves$basisOpen$lambda(path) {
    return new BasisOpen(path);
  }
  function curves$bundle$lambda(path) {
    return new Bundle(path);
  }
  function curves$cardinal$lambda(path) {
    return new Cardinal(path);
  }
  function curves$cardinalClosed$lambda(path) {
    return new CardinalClosed(path);
  }
  function curves$cardinalOpen$lambda(path) {
    return new CardinalOpen(path);
  }
  function curves$catmullRom$lambda(path) {
    return new CatmullRom(path);
  }
  function curves$catmullRomClosed$lambda(path) {
    return new CatmullRomClosed(path);
  }
  function curves$catmullRomOpen$lambda(path) {
    return new CatmullRomOpen(path);
  }
  function curves$linear$lambda(path) {
    return new Linear(path);
  }
  function curves$linearClosed$lambda(path) {
    return new LinearClosed(path);
  }
  function curves$monotoneX$lambda(path) {
    return new MonotoneX(path);
  }
  function curves$monotoneY$lambda(path) {
    return new MonotoneY(path);
  }
  function curves$natural$lambda(path) {
    return new Natural(path);
  }
  function curves$step$lambda(path) {
    return new Step(path);
  }
  function curves$stepBefore$lambda(path) {
    return new StepBefore(path);
  }
  function curves$stepAfter$lambda(path) {
    return new StepAfter(path);
  }
  curves.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'curves',
    interfaces: []
  };
  var curves_instance = null;
  function curves_getInstance() {
    if (curves_instance === null) {
      new curves();
    }
    return curves_instance;
  }
  function areas() {
    areas_instance = this;
    this.default = areas$default$lambda;
    this.basis = areas$basis$lambda;
  }
  function areas$default$lambda(path) {
    return new Linear(path);
  }
  function areas$basis$lambda(path) {
    return new Basis(path);
  }
  areas.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'areas',
    interfaces: []
  };
  var areas_instance = null;
  function areas_getInstance() {
    if (areas_instance === null) {
      new areas();
    }
    return areas_instance;
  }
  function line(init) {
    var $receiver = new LineBuilder();
    init($receiver);
    return $receiver;
  }
  function const$lambda(closure$constantValue) {
    return function (it) {
      return closure$constantValue;
    };
  }
  function const_0(constantValue) {
    return const$lambda(constantValue);
  }
  function LineBuilder() {
    this.curve = curves_getInstance().linear;
    this.x = const_0(0.0);
    this.y = const_0(0.0);
    this.defined = const_0(true);
  }
  LineBuilder.prototype.buildLine_pzuqs$ = function (data, path) {
    var dataSize = data.size;
    var defined0 = false;
    var output = this.curve(path);
    for (var i = 0; i <= dataSize; i++) {
      if (!(i < dataSize && this.defined(data.get_za3lpa$(i))) === defined0) {
        defined0 = !defined0;
        if (defined0)
          output.lineStart();
        else
          output.lineEnd();
      }
      if (defined0) {
        var d = data.get_za3lpa$(i);
        output.point_lu1900$(this.x(d), this.y(d));
      }
    }
    return path;
  };
  LineBuilder.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'LineBuilder',
    interfaces: []
  };
  var epsilon;
  var pi;
  var halfPi;
  var tau;
  function acos(x) {
    return x > 1.0 ? 0.0 : x < -1 ? pi : Math_0.acos(x);
  }
  function asin(x) {
    return x >= 1.0 ? halfPi : x <= -1.0 ? -halfPi : Math_0.asin(x);
  }
  function pie(init) {
    var $receiver = new PieGenerator();
    init($receiver);
    return $receiver;
  }
  function PieGenerator() {
    this.value = const_0(0.0);
    this.startAngle = const_0(0.0);
    this.endAngle = const_0(tau);
    this.padAngle = const_0(0.0);
  }
  PieGenerator.prototype.render_tfr4nt$ = function (data) {
    var n = data.length;
    var sum = 0.0;
    var array = Array_0(n);
    var tmp$;
    tmp$ = array.length - 1 | 0;
    for (var i = 0; i <= tmp$; i++) {
      array[i] = 0;
    }
    var index = array;
    var array_0 = Array_0(n);
    var tmp$_0;
    tmp$_0 = array_0.length - 1 | 0;
    for (var i_0 = 0; i_0 <= tmp$_0; i_0++) {
      array_0[i_0] = new ArcParams(0.0, 0.0, 0.0, null, null, null);
    }
    var arcs = array_0;
    var array_1 = Array_0(n);
    var tmp$_1;
    tmp$_1 = array_1.length - 1 | 0;
    for (var i_1 = 0; i_1 <= tmp$_1; i_1++) {
      array_1[i_1] = 0.0;
    }
    var values = array_1;
    var a0 = this.startAngle(data);
    var tmp$_2 = tau;
    var a = -tau;
    var b = this.endAngle(data) - a0;
    var b_0 = Math_0.max(a, b);
    var da = Math_0.min(tmp$_2, b_0);
    var a_0 = Math_0.abs(da) / n;
    var b_1 = this.padAngle(data);
    var p = Math_0.min(a_0, b_1);
    var pa = da < 0.0 ? -p : p;
    for (var i_2 = 0; i_2 < n; i_2++) {
      index[i_2] = i_2;
      var v = this.value(data[i_2]);
      values[i_2] = v;
      if (v > 0)
        sum += v;
    }
    var k = sum > 0.0 ? (da - n * pa) / sum : 0.0;
    for (var i_3 = 0; i_3 < n; i_3++) {
      var j = index[i_3];
      var v_0 = values[j];
      var a1 = a0 + (v_0 > 0.0 ? v_0 * k : 0.0) + pa;
      arcs[j] = new ArcParams(a0, a1, p, v_0, i_3, data[j]);
      a0 = a1;
    }
    return arcs;
  };
  PieGenerator.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'PieGenerator',
    interfaces: []
  };
  function Symbol() {
  }
  Symbol.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Symbol',
    interfaces: []
  };
  function Symbols(name, ordinal) {
    Enum.call(this);
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function Symbols_initFields() {
    Symbols_initFields = function () {
    };
    Symbols$Circle_instance = new Symbols('Circle', 0);
    Symbols$Cross_instance = new Symbols('Cross', 1);
    Symbols$Diamond_instance = new Symbols('Diamond', 2);
    Symbols$Square_instance = new Symbols('Square', 3);
    Symbols$Star_instance = new Symbols('Star', 4);
    Symbols$Triangle_instance = new Symbols('Triangle', 5);
    Symbols$Wye_instance = new Symbols('Wye', 6);
  }
  var Symbols$Circle_instance;
  function Symbols$Circle_getInstance() {
    Symbols_initFields();
    return Symbols$Circle_instance;
  }
  var Symbols$Cross_instance;
  function Symbols$Cross_getInstance() {
    Symbols_initFields();
    return Symbols$Cross_instance;
  }
  var Symbols$Diamond_instance;
  function Symbols$Diamond_getInstance() {
    Symbols_initFields();
    return Symbols$Diamond_instance;
  }
  var Symbols$Square_instance;
  function Symbols$Square_getInstance() {
    Symbols_initFields();
    return Symbols$Square_instance;
  }
  var Symbols$Star_instance;
  function Symbols$Star_getInstance() {
    Symbols_initFields();
    return Symbols$Star_instance;
  }
  var Symbols$Triangle_instance;
  function Symbols$Triangle_getInstance() {
    Symbols_initFields();
    return Symbols$Triangle_instance;
  }
  var Symbols$Wye_instance;
  function Symbols$Wye_getInstance() {
    Symbols_initFields();
    return Symbols$Wye_instance;
  }
  Symbols.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Symbols',
    interfaces: [Enum]
  };
  function Symbols$values() {
    return [Symbols$Circle_getInstance(), Symbols$Cross_getInstance(), Symbols$Diamond_getInstance(), Symbols$Square_getInstance(), Symbols$Star_getInstance(), Symbols$Triangle_getInstance(), Symbols$Wye_getInstance()];
  }
  Symbols.values = Symbols$values;
  function Symbols$valueOf(name) {
    switch (name) {
      case 'Circle':
        return Symbols$Circle_getInstance();
      case 'Cross':
        return Symbols$Cross_getInstance();
      case 'Diamond':
        return Symbols$Diamond_getInstance();
      case 'Square':
        return Symbols$Square_getInstance();
      case 'Star':
        return Symbols$Star_getInstance();
      case 'Triangle':
        return Symbols$Triangle_getInstance();
      case 'Wye':
        return Symbols$Wye_getInstance();
      default:throwISE('No enum constant io.data2viz.shape.Symbols.' + name);
    }
  }
  Symbols.valueOf_61zpoe$ = Symbols$valueOf;
  function get_symbol($receiver) {
    switch ($receiver.name) {
      case 'Cross':
        return new Cross();
      case 'Diamond':
        return new Diamond();
      case 'Square':
        return new Square();
      case 'Star':
        return new Star();
      case 'Triangle':
        return new Triangle();
      case 'Wye':
        return new Wye();
      default:return new Circle();
    }
  }
  function render(init) {
    var $receiver = new SymbolGenerator();
    init($receiver);
    return $receiver;
  }
  function SymbolGenerator() {
    this.size = const_0(64.0);
    this.type = SymbolGenerator$type$lambda;
  }
  SymbolGenerator.prototype.render_lr2v95$ = function (args, path) {
    this.type(args).render_lh6mq8$(path, this.size(args));
    return path;
  };
  function SymbolGenerator$type$lambda(it) {
    return new Circle();
  }
  SymbolGenerator.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'SymbolGenerator',
    interfaces: []
  };
  function Basis(path) {
    this.path_udthta$_0 = path;
    this.x0_0 = -1.0;
    this.y0_0 = -1.0;
    this.x1_0 = -1.0;
    this.y1_0 = -1.0;
    this.lineStatus_0 = -1;
    this.pointStatus_0 = -1;
  }
  Object.defineProperty(Basis.prototype, 'path', {
    get: function () {
      return this.path_udthta$_0;
    }
  });
  Basis.prototype.areaStart = function () {
    this.lineStatus_0 = 0;
  };
  Basis.prototype.areaEnd = function () {
    this.lineStatus_0 = -1;
  };
  Basis.prototype.lineStart = function () {
    this.x0_0 = -1.0;
    this.y0_0 = -1.0;
    this.x1_0 = -1.0;
    this.y1_0 = -1.0;
    this.pointStatus_0 = 0;
  };
  Basis.prototype.lineEnd = function () {
    if (this.pointStatus_0 === 3) {
      this.curve_0(this.x1_0, this.y1_0);
      this.path.lineTo_lu1900$(this.x1_0, this.y1_0);
    }
     else if (this.pointStatus_0 === 2)
      this.path.lineTo_lu1900$(this.x1_0, this.y1_0);
    if (this.lineStatus_0 > 0 || this.pointStatus_0 === 1) {
      this.path.closePath();
    }
    if (this.lineStatus_0 > -1)
      this.lineStatus_0 = 1 - this.lineStatus_0 | 0;
  };
  Basis.prototype.curve_0 = function (x, y) {
    this.path.bezierCurveTo_15yvbs$((2 * this.x0_0 + this.x1_0) / 3, (2 * this.y0_0 + this.y1_0) / 3, (this.x0_0 + 2 * this.x1_0) / 3, (this.y0_0 + 2 * this.y1_0) / 3, (this.x0_0 + 4 * this.x1_0 + x) / 6, (this.y0_0 + 4 * this.y1_0 + y) / 6);
  };
  Basis.prototype.point_lu1900$ = function (x, y) {
    if (this.pointStatus_0 === 0) {
      this.pointStatus_0 = 1;
      if (this.lineStatus_0 > 0)
        this.path.lineTo_lu1900$(x, y);
      else
        this.path.moveTo_lu1900$(x, y);
    }
     else if (this.pointStatus_0 === 1) {
      this.pointStatus_0 = 2;
    }
     else if (this.pointStatus_0 === 2) {
      this.pointStatus_0 = 3;
      this.path.lineTo_lu1900$((5 * this.x0_0 + this.x1_0) / 6, (5 * this.y0_0 + this.y1_0) / 6);
      this.curve_0(x, y);
    }
     else {
      this.curve_0(x, y);
    }
    this.x0_0 = this.x1_0;
    this.x1_0 = x;
    this.y0_0 = this.y1_0;
    this.y1_0 = y;
  };
  Basis.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Basis',
    interfaces: [Curve]
  };
  function BasisClosed(path) {
    this.path_ms4qoy$_0 = path;
    this.x0_0 = -1.0;
    this.y0_0 = -1.0;
    this.x1_0 = -1.0;
    this.y1_0 = -1.0;
    this.x2_0 = -1.0;
    this.y2_0 = -1.0;
    this.x3_0 = -1.0;
    this.y3_0 = -1.0;
    this.x4_0 = -1.0;
    this.y4_0 = -1.0;
    this.pointStatus_0 = -1;
  }
  Object.defineProperty(BasisClosed.prototype, 'path', {
    get: function () {
      return this.path_ms4qoy$_0;
    }
  });
  BasisClosed.prototype.areaStart = function () {
  };
  BasisClosed.prototype.areaEnd = function () {
  };
  BasisClosed.prototype.lineStart = function () {
    this.x0_0 = -1.0;
    this.y0_0 = -1.0;
    this.x1_0 = -1.0;
    this.y1_0 = -1.0;
    this.x2_0 = -1.0;
    this.y2_0 = -1.0;
    this.x3_0 = -1.0;
    this.y3_0 = -1.0;
    this.x4_0 = -1.0;
    this.y4_0 = -1.0;
    this.pointStatus_0 = 0;
  };
  BasisClosed.prototype.lineEnd = function () {
    switch (this.pointStatus_0) {
      case 1:
        this.path.moveTo_lu1900$(this.x2_0, this.y2_0);
        this.path.closePath();
        break;
      case 2:
        this.path.moveTo_lu1900$((this.x2_0 + 2 * this.x3_0) / 3, (this.y2_0 + 2 * this.y3_0) / 3);
        this.path.lineTo_lu1900$((this.x3_0 + 2 * this.x2_0) / 3, (this.y3_0 + 2 * this.y2_0) / 3);
        this.path.closePath();
        break;
      case 3:
        this.point_lu1900$(this.x2_0, this.y2_0);
        this.point_lu1900$(this.x3_0, this.y3_0);
        this.point_lu1900$(this.x4_0, this.y4_0);
        break;
    }
  };
  BasisClosed.prototype.curve_0 = function (x, y) {
    this.path.bezierCurveTo_15yvbs$((2 * this.x0_0 + this.x1_0) / 3, (2 * this.y0_0 + this.y1_0) / 3, (this.x0_0 + 2 * this.x1_0) / 3, (this.y0_0 + 2 * this.y1_0) / 3, (this.x0_0 + 4 * this.x1_0 + x) / 6, (this.y0_0 + 4 * this.y1_0 + y) / 6);
  };
  BasisClosed.prototype.point_lu1900$ = function (x, y) {
    switch (this.pointStatus_0) {
      case 0:
        this.pointStatus_0 = 1;
        this.x2_0 = x;
        this.y2_0 = y;
        break;
      case 1:
        this.pointStatus_0 = 2;
        this.x3_0 = x;
        this.y3_0 = y;
        break;
      case 2:
        this.pointStatus_0 = 3;
        this.x4_0 = x;
        this.y4_0 = y;
        this.path.moveTo_lu1900$((this.x0_0 + 4 * this.x1_0 + x) / 6, (this.y0_0 + 4 * this.y1_0 + y) / 6);
        break;
      default:this.curve_0(x, y);
        break;
    }
    this.x0_0 = this.x1_0;
    this.x1_0 = x;
    this.y0_0 = this.y1_0;
    this.y1_0 = y;
  };
  BasisClosed.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'BasisClosed',
    interfaces: [Curve]
  };
  function BasisOpen(path) {
    this.path_eap3p8$_0 = path;
    this.x0_0 = -1.0;
    this.y0_0 = -1.0;
    this.x1_0 = -1.0;
    this.y1_0 = -1.0;
    this.lineStatus_0 = -1;
    this.pointStatus_0 = -1;
  }
  Object.defineProperty(BasisOpen.prototype, 'path', {
    get: function () {
      return this.path_eap3p8$_0;
    }
  });
  BasisOpen.prototype.areaStart = function () {
    this.lineStatus_0 = 0;
  };
  BasisOpen.prototype.areaEnd = function () {
    this.lineStatus_0 = -1;
  };
  BasisOpen.prototype.lineStart = function () {
    this.x0_0 = -1.0;
    this.y0_0 = -1.0;
    this.x1_0 = -1.0;
    this.y1_0 = -1.0;
    this.pointStatus_0 = 0;
  };
  BasisOpen.prototype.lineEnd = function () {
    if (this.lineStatus_0 > -1) {
      if (this.lineStatus_0 > 0) {
        this.path.closePath();
      }
      this.lineStatus_0 = 1 - this.lineStatus_0 | 0;
    }
  };
  BasisOpen.prototype.curve_0 = function (x, y) {
    this.path.bezierCurveTo_15yvbs$((2 * this.x0_0 + this.x1_0) / 3, (2 * this.y0_0 + this.y1_0) / 3, (this.x0_0 + 2 * this.x1_0) / 3, (this.y0_0 + 2 * this.y1_0) / 3, (this.x0_0 + 4 * this.x1_0 + x) / 6, (this.y0_0 + 4 * this.y1_0 + y) / 6);
  };
  BasisOpen.prototype.point_lu1900$ = function (x, y) {
    switch (this.pointStatus_0) {
      case 0:
        this.pointStatus_0 = 1;
        break;
      case 1:
        this.pointStatus_0 = 2;
        break;
      case 2:
        this.pointStatus_0 = 3;
        var _x = (this.x0_0 + 4 * this.x1_0 + x) / 6;
        var _y = (this.y0_0 + 4 * this.y1_0 + y) / 6;
        if (this.lineStatus_0 > 0)
          this.path.lineTo_lu1900$(_x, _y);
        else
          this.path.moveTo_lu1900$(_x, _y);
        break;
      case 3:
        this.pointStatus_0 = 4;
        this.curve_0(x, y);
        break;
      default:this.curve_0(x, y);
        break;
    }
    this.x0_0 = this.x1_0;
    this.x1_0 = x;
    this.y0_0 = this.y1_0;
    this.y1_0 = y;
  };
  BasisOpen.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'BasisOpen',
    interfaces: [Curve]
  };
  function Bundle(path, beta) {
    if (beta === void 0)
      beta = 0.85;
    this.path_eqdkf8$_0 = path;
    this.beta = beta;
    this.basis_0 = new Basis(this.path);
    this.x_0 = ArrayList_init();
    this.y_0 = ArrayList_init();
  }
  Object.defineProperty(Bundle.prototype, 'path', {
    get: function () {
      return this.path_eqdkf8$_0;
    }
  });
  Bundle.prototype.areaStart = function () {
  };
  Bundle.prototype.areaEnd = function () {
  };
  Bundle.prototype.lineStart = function () {
    this.x_0.clear();
    this.y_0.clear();
    this.basis_0.lineStart();
  };
  Bundle.prototype.lineEnd = function () {
    var j = this.x_0.size - 1 | 0;
    if (j > 0) {
      var x0 = this.x_0.get_za3lpa$(0);
      var y0 = this.y_0.get_za3lpa$(0);
      var dx = this.x_0.get_za3lpa$(j) - x0;
      var dy = this.y_0.get_za3lpa$(j) - y0;
      var tmp$;
      tmp$ = (new IntRange(0, j)).iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        var t = element / j | 0;
        this.basis_0.point_lu1900$(this.beta * this.x_0.get_za3lpa$(element) + (1 - this.beta) * (x0 + t * dx), this.beta * this.y_0.get_za3lpa$(element) + (1 - this.beta) * (y0 + t * dy));
      }
    }
    this.basis_0.lineEnd();
  };
  Bundle.prototype.point_lu1900$ = function (x, y) {
    this.x_0.add_11rb$(x);
    this.y_0.add_11rb$(y);
  };
  Bundle.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Bundle',
    interfaces: [Curve]
  };
  function Cardinal(path, tension) {
    if (tension === void 0)
      tension = 0.0;
    this.path_5hws3q$_0 = path;
    this.x0_0 = -1.0;
    this.y0_0 = -1.0;
    this.x1_0 = -1.0;
    this.y1_0 = -1.0;
    this.x2_0 = -1.0;
    this.y2_0 = -1.0;
    this.lineStatus_0 = -1;
    this.pointStatus_0 = -1;
    this.k_0 = (1.0 - tension) / 6.0;
  }
  Object.defineProperty(Cardinal.prototype, 'path', {
    get: function () {
      return this.path_5hws3q$_0;
    }
  });
  Cardinal.prototype.areaStart = function () {
    this.lineStatus_0 = 0;
  };
  Cardinal.prototype.areaEnd = function () {
    this.lineStatus_0 = -1;
  };
  Cardinal.prototype.lineStart = function () {
    this.x0_0 = -1.0;
    this.y0_0 = -1.0;
    this.x1_0 = -1.0;
    this.y1_0 = -1.0;
    this.x2_0 = -1.0;
    this.y2_0 = -1.0;
    this.pointStatus_0 = 0;
  };
  Cardinal.prototype.lineEnd = function () {
    switch (this.pointStatus_0) {
      case 2:
        this.path.lineTo_lu1900$(this.x2_0, this.y2_0);
        break;
      case 3:
        this.curve_0(this.x1_0, this.y1_0);
        break;
    }
    if (this.lineStatus_0 > -1) {
      if (this.lineStatus_0 > 0) {
        this.path.closePath();
      }
      this.lineStatus_0 = 1 - this.lineStatus_0 | 0;
    }
  };
  Cardinal.prototype.curve_0 = function (x, y) {
    this.path.bezierCurveTo_15yvbs$(this.x1_0 + this.k_0 * (this.x2_0 - this.x0_0), this.y1_0 + this.k_0 * (this.y2_0 - this.y0_0), this.x2_0 + this.k_0 * (this.x1_0 - x), this.y2_0 + this.k_0 * (this.y1_0 - y), this.x2_0, this.y2_0);
  };
  Cardinal.prototype.point_lu1900$ = function (x, y) {
    switch (this.pointStatus_0) {
      case 0:
        this.pointStatus_0 = 1;
        if (this.lineStatus_0 > 0)
          this.path.lineTo_lu1900$(x, y);
        else
          this.path.moveTo_lu1900$(x, y);
        break;
      case 1:
        this.pointStatus_0 = 2;
        this.x1_0 = x;
        this.y1_0 = y;
        break;
      case 2:
        this.pointStatus_0 = 3;
        this.curve_0(x, y);
        break;
      default:this.curve_0(x, y);
        break;
    }
    this.x0_0 = this.x1_0;
    this.x1_0 = this.x2_0;
    this.x2_0 = x;
    this.y0_0 = this.y1_0;
    this.y1_0 = this.y2_0;
    this.y2_0 = y;
  };
  Cardinal.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Cardinal',
    interfaces: [Curve]
  };
  function CardinalClosed(path, tension) {
    if (tension === void 0)
      tension = 0.0;
    this.path_r9vshy$_0 = path;
    this.x0_0 = -1.0;
    this.y0_0 = -1.0;
    this.x1_0 = -1.0;
    this.y1_0 = -1.0;
    this.x2_0 = -1.0;
    this.y2_0 = -1.0;
    this.x3_0 = -1.0;
    this.y3_0 = -1.0;
    this.x4_0 = -1.0;
    this.y4_0 = -1.0;
    this.x5_0 = -1.0;
    this.y5_0 = -1.0;
    this.lineStatus_0 = -1;
    this.pointStatus_0 = -1;
    this.k_0 = (1.0 - tension) / 6.0;
  }
  Object.defineProperty(CardinalClosed.prototype, 'path', {
    get: function () {
      return this.path_r9vshy$_0;
    }
  });
  CardinalClosed.prototype.areaStart = function () {
  };
  CardinalClosed.prototype.areaEnd = function () {
  };
  CardinalClosed.prototype.lineStart = function () {
    this.x0_0 = -1.0;
    this.y0_0 = -1.0;
    this.x1_0 = -1.0;
    this.y1_0 = -1.0;
    this.x2_0 = -1.0;
    this.y2_0 = -1.0;
    this.x3_0 = -1.0;
    this.y3_0 = -1.0;
    this.x4_0 = -1.0;
    this.y4_0 = -1.0;
    this.x5_0 = -1.0;
    this.y5_0 = -1.0;
    this.pointStatus_0 = 0;
  };
  CardinalClosed.prototype.lineEnd = function () {
    switch (this.pointStatus_0) {
      case 1:
        this.path.moveTo_lu1900$(this.x3_0, this.y3_0);
        this.path.closePath();
        break;
      case 2:
        this.path.lineTo_lu1900$(this.x3_0, this.y3_0);
        this.path.closePath();
        break;
      case 3:
        this.point_lu1900$(this.x3_0, this.y3_0);
        this.point_lu1900$(this.x4_0, this.y4_0);
        this.point_lu1900$(this.x5_0, this.y5_0);
        break;
    }
    if (this.lineStatus_0 > 0)
      this.path.closePath();
    this.lineStatus_0 = 1 - this.lineStatus_0 | 0;
  };
  CardinalClosed.prototype.curve_0 = function (x, y) {
    this.path.bezierCurveTo_15yvbs$(this.x1_0 + this.k_0 * (this.x2_0 - this.x0_0), this.y1_0 + this.k_0 * (this.y2_0 - this.y0_0), this.x2_0 + this.k_0 * (this.x1_0 - x), this.y2_0 + this.k_0 * (this.y1_0 - y), this.x2_0, this.y2_0);
  };
  CardinalClosed.prototype.point_lu1900$ = function (x, y) {
    switch (this.pointStatus_0) {
      case 0:
        this.pointStatus_0 = 1;
        this.x3_0 = x;
        this.y3_0 = y;
        break;
      case 1:
        this.pointStatus_0 = 2;
        this.x4_0 = x;
        this.y4_0 = y;
        this.path.moveTo_lu1900$(this.x4_0, this.y4_0);
        break;
      case 2:
        this.pointStatus_0 = 3;
        this.x5_0 = x;
        this.y5_0 = y;
        break;
      default:this.curve_0(x, y);
        break;
    }
    this.x0_0 = this.x1_0;
    this.x1_0 = this.x2_0;
    this.x2_0 = x;
    this.y0_0 = this.y1_0;
    this.y1_0 = this.y2_0;
    this.y2_0 = y;
  };
  CardinalClosed.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'CardinalClosed',
    interfaces: [Curve]
  };
  function CardinalOpen(path, tension) {
    if (tension === void 0)
      tension = 0.0;
    this.path_uvw258$_0 = path;
    this.x0_0 = -1.0;
    this.y0_0 = -1.0;
    this.x1_0 = -1.0;
    this.y1_0 = -1.0;
    this.x2_0 = -1.0;
    this.y2_0 = -1.0;
    this.lineStatus_0 = -1;
    this.pointStatus_0 = -1;
    this.k_0 = (1.0 - tension) / 6.0;
  }
  Object.defineProperty(CardinalOpen.prototype, 'path', {
    get: function () {
      return this.path_uvw258$_0;
    }
  });
  CardinalOpen.prototype.areaStart = function () {
    this.lineStatus_0 = 0;
  };
  CardinalOpen.prototype.areaEnd = function () {
    this.lineStatus_0 = -1;
  };
  CardinalOpen.prototype.lineStart = function () {
    this.x0_0 = -1.0;
    this.y0_0 = -1.0;
    this.x1_0 = -1.0;
    this.y1_0 = -1.0;
    this.x2_0 = -1.0;
    this.y2_0 = -1.0;
    this.pointStatus_0 = 0;
  };
  CardinalOpen.prototype.lineEnd = function () {
    if (this.lineStatus_0 > -1) {
      if (this.lineStatus_0 > 0) {
        this.path.closePath();
      }
      this.lineStatus_0 = 1 - this.lineStatus_0 | 0;
    }
  };
  CardinalOpen.prototype.curve_0 = function (x, y) {
    this.path.bezierCurveTo_15yvbs$(this.x1_0 + this.k_0 * (this.x2_0 - this.x0_0), this.y1_0 + this.k_0 * (this.y2_0 - this.y0_0), this.x2_0 + this.k_0 * (this.x1_0 - x), this.y2_0 + this.k_0 * (this.y1_0 - y), this.x2_0, this.y2_0);
  };
  CardinalOpen.prototype.point_lu1900$ = function (x, y) {
    switch (this.pointStatus_0) {
      case 0:
        this.pointStatus_0 = 1;
        break;
      case 1:
        this.pointStatus_0 = 2;
        break;
      case 2:
        this.pointStatus_0 = 3;
        if (this.lineStatus_0 > 0)
          this.path.lineTo_lu1900$(this.x2_0, this.y2_0);
        else
          this.path.moveTo_lu1900$(this.x2_0, this.y2_0);
        break;
      case 3:
        this.pointStatus_0 = 4;
        this.curve_0(x, y);
        break;
      default:this.curve_0(x, y);
        break;
    }
    this.x0_0 = this.x1_0;
    this.x1_0 = this.x2_0;
    this.x2_0 = x;
    this.y0_0 = this.y1_0;
    this.y1_0 = this.y2_0;
    this.y2_0 = y;
  };
  CardinalOpen.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'CardinalOpen',
    interfaces: [Curve]
  };
  function CatmullRom(path, alpha) {
    if (alpha === void 0)
      alpha = 0.5;
    this.path_cl9sj8$_0 = path;
    this.alpha = alpha;
    this.x0_0 = -1.0;
    this.y0_0 = -1.0;
    this.x1_0 = -1.0;
    this.y1_0 = -1.0;
    this.x2_0 = -1.0;
    this.y2_0 = -1.0;
    this._l01_a_0 = 0.0;
    this._l12_a_0 = 0.0;
    this._l23_a_0 = 0.0;
    this._l01_2a_0 = 0.0;
    this._l12_2a_0 = 0.0;
    this._l23_2a_0 = 0.0;
    this.lineStatus_0 = -1;
    this.pointStatus_0 = -1;
  }
  Object.defineProperty(CatmullRom.prototype, 'path', {
    get: function () {
      return this.path_cl9sj8$_0;
    }
  });
  CatmullRom.prototype.areaStart = function () {
    this.lineStatus_0 = 0;
  };
  CatmullRom.prototype.areaEnd = function () {
    this.lineStatus_0 = -1;
  };
  CatmullRom.prototype.lineStart = function () {
    this.x0_0 = -1.0;
    this.y0_0 = -1.0;
    this.x1_0 = -1.0;
    this.y1_0 = -1.0;
    this.x2_0 = -1.0;
    this.y2_0 = -1.0;
    this._l01_a_0 = 0.0;
    this._l12_a_0 = 0.0;
    this._l23_a_0 = 0.0;
    this._l01_2a_0 = 0.0;
    this._l12_2a_0 = 0.0;
    this._l23_2a_0 = 0.0;
    this.pointStatus_0 = 0;
  };
  CatmullRom.prototype.lineEnd = function () {
    switch (this.pointStatus_0) {
      case 2:
        this.path.lineTo_lu1900$(this.x2_0, this.y2_0);
        break;
      case 3:
        this.point_lu1900$(this.x2_0, this.y2_0);
        break;
    }
    if (this.lineStatus_0 > -1) {
      if (this.lineStatus_0 > 0) {
        this.path.closePath();
      }
      this.lineStatus_0 = 1 - this.lineStatus_0 | 0;
    }
  };
  CatmullRom.prototype.curve_0 = function (x, y) {
    var _x1 = this.x1_0;
    var _y1 = this.y1_0;
    var _x2 = this.x2_0;
    var _y2 = this.y2_0;
    if (this._l01_a_0 > epsilon) {
      var a = 2 * this._l01_2a_0 + 3 * this._l01_a_0 * this._l12_a_0 + this._l12_2a_0;
      var n = 3 * this._l01_a_0 * (this._l01_a_0 + this._l12_a_0);
      _x1 = (this.x1_0 * a - this.x0_0 * this._l12_2a_0 + this.x2_0 * this._l01_2a_0) / n;
      _y1 = (this.y1_0 * a - this.y0_0 * this._l12_2a_0 + this.y2_0 * this._l01_2a_0) / n;
    }
    if (this._l23_a_0 > epsilon) {
      var b = 2 * this._l23_2a_0 + 3 * this._l23_a_0 * this._l12_a_0 + this._l12_2a_0;
      var m = 3 * this._l23_a_0 * (this._l23_a_0 + this._l12_a_0);
      _x2 = (this.x2_0 * b + this.x1_0 * this._l23_2a_0 - x * this._l12_2a_0) / m;
      _y2 = (this.y2_0 * b + this.y1_0 * this._l23_2a_0 - y * this._l12_2a_0) / m;
    }
    this.path.bezierCurveTo_15yvbs$(_x1, _y1, _x2, _y2, this.x2_0, this.y2_0);
  };
  CatmullRom.prototype.point_lu1900$ = function (x, y) {
    if (this.pointStatus_0 > 0) {
      var x23 = this.x2_0 - x;
      var y23 = this.y2_0 - y;
      var $receiver = x23 * x23 + y23 * y23;
      var x_0 = this.alpha;
      this._l23_2a_0 = Math_0.pow($receiver, x_0);
      var x_1 = this._l23_2a_0;
      this._l23_a_0 = Math_0.sqrt(x_1);
    }
    switch (this.pointStatus_0) {
      case 0:
        this.pointStatus_0 = 1;
        if (this.lineStatus_0 > 0)
          this.path.lineTo_lu1900$(x, y);
        else
          this.path.moveTo_lu1900$(x, y);
        break;
      case 1:
        this.pointStatus_0 = 2;
        break;
      case 2:
        this.pointStatus_0 = 3;
        this.curve_0(x, y);
        break;
      default:this.curve_0(x, y);
        break;
    }
    this._l01_a_0 = this._l12_a_0;
    this._l12_a_0 = this._l23_a_0;
    this._l01_2a_0 = this._l12_2a_0;
    this._l12_2a_0 = this._l23_2a_0;
    this.x0_0 = this.x1_0;
    this.x1_0 = this.x2_0;
    this.x2_0 = x;
    this.y0_0 = this.y1_0;
    this.y1_0 = this.y2_0;
    this.y2_0 = y;
  };
  CatmullRom.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'CatmullRom',
    interfaces: [Curve]
  };
  function CatmullRomClosed(path, alpha) {
    if (alpha === void 0)
      alpha = 0.5;
    this.path_k2b4t4$_0 = path;
    this.alpha = alpha;
    this.x0_0 = -1.0;
    this.y0_0 = -1.0;
    this.x1_0 = -1.0;
    this.y1_0 = -1.0;
    this.x2_0 = -1.0;
    this.y2_0 = -1.0;
    this.x3_0 = -1.0;
    this.y3_0 = -1.0;
    this.x4_0 = -1.0;
    this.y4_0 = -1.0;
    this.x5_0 = -1.0;
    this.y5_0 = -1.0;
    this._l01_a_0 = 0.0;
    this._l12_a_0 = 0.0;
    this._l23_a_0 = 0.0;
    this._l01_2a_0 = 0.0;
    this._l12_2a_0 = 0.0;
    this._l23_2a_0 = 0.0;
    this.pointStatus_0 = -1;
  }
  Object.defineProperty(CatmullRomClosed.prototype, 'path', {
    get: function () {
      return this.path_k2b4t4$_0;
    }
  });
  CatmullRomClosed.prototype.areaStart = function () {
  };
  CatmullRomClosed.prototype.areaEnd = function () {
  };
  CatmullRomClosed.prototype.lineStart = function () {
    this.x0_0 = -1.0;
    this.y0_0 = -1.0;
    this.x1_0 = -1.0;
    this.y1_0 = -1.0;
    this.x2_0 = -1.0;
    this.y2_0 = -1.0;
    this._l01_a_0 = 0.0;
    this._l12_a_0 = 0.0;
    this._l23_a_0 = 0.0;
    this._l01_2a_0 = 0.0;
    this._l12_2a_0 = 0.0;
    this._l23_2a_0 = 0.0;
    this.pointStatus_0 = 0;
  };
  CatmullRomClosed.prototype.lineEnd = function () {
    switch (this.pointStatus_0) {
      case 1:
        this.path.moveTo_lu1900$(this.x3_0, this.y3_0);
        this.path.closePath();
        break;
      case 2:
        this.path.lineTo_lu1900$(this.x3_0, this.y3_0);
        this.path.closePath();
        break;
      case 3:
        this.point_lu1900$(this.x3_0, this.y3_0);
        this.point_lu1900$(this.x4_0, this.y4_0);
        this.point_lu1900$(this.x5_0, this.y5_0);
        break;
    }
  };
  CatmullRomClosed.prototype.curve_0 = function (x, y) {
    var _x1 = this.x1_0;
    var _y1 = this.y1_0;
    var _x2 = this.x2_0;
    var _y2 = this.y2_0;
    if (this._l01_a_0 > epsilon) {
      var a = 2 * this._l01_2a_0 + 3 * this._l01_a_0 * this._l12_a_0 + this._l12_2a_0;
      var n = 3 * this._l01_a_0 * (this._l01_a_0 + this._l12_a_0);
      _x1 = (this.x1_0 * a - this.x0_0 * this._l12_2a_0 + this.x2_0 * this._l01_2a_0) / n;
      _y1 = (this.y1_0 * a - this.y0_0 * this._l12_2a_0 + this.y2_0 * this._l01_2a_0) / n;
    }
    if (this._l23_a_0 > epsilon) {
      var b = 2 * this._l23_2a_0 + 3 * this._l23_a_0 * this._l12_a_0 + this._l12_2a_0;
      var m = 3 * this._l23_a_0 * (this._l23_a_0 + this._l12_a_0);
      _x2 = (this.x2_0 * b + this.x1_0 * this._l23_2a_0 - x * this._l12_2a_0) / m;
      _y2 = (this.y2_0 * b + this.y1_0 * this._l23_2a_0 - y * this._l12_2a_0) / m;
    }
    this.path.bezierCurveTo_15yvbs$(_x1, _y1, _x2, _y2, this.x2_0, this.y2_0);
  };
  CatmullRomClosed.prototype.point_lu1900$ = function (x, y) {
    if (this.pointStatus_0 > 0) {
      var x23 = this.x2_0 - x;
      var y23 = this.y2_0 - y;
      var $receiver = x23 * x23 + y23 * y23;
      var x_0 = this.alpha;
      this._l23_2a_0 = Math_0.pow($receiver, x_0);
      var x_1 = this._l23_2a_0;
      this._l23_a_0 = Math_0.sqrt(x_1);
    }
    switch (this.pointStatus_0) {
      case 0:
        this.pointStatus_0 = 1;
        this.x3_0 = x;
        this.y3_0 = y;
        break;
      case 1:
        this.pointStatus_0 = 2;
        this.x4_0 = x;
        this.y4_0 = y;
        this.path.moveTo_lu1900$(this.x4_0, this.y4_0);
        break;
      case 2:
        this.pointStatus_0 = 3;
        this.x5_0 = x;
        this.y5_0 = y;
        break;
      default:this.curve_0(x, y);
        break;
    }
    this._l01_a_0 = this._l12_a_0;
    this._l12_a_0 = this._l23_a_0;
    this._l01_2a_0 = this._l12_2a_0;
    this._l12_2a_0 = this._l23_2a_0;
    this.x0_0 = this.x1_0;
    this.x1_0 = this.x2_0;
    this.x2_0 = x;
    this.y0_0 = this.y1_0;
    this.y1_0 = this.y2_0;
    this.y2_0 = y;
  };
  CatmullRomClosed.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'CatmullRomClosed',
    interfaces: [Curve]
  };
  function CatmullRomOpen(path, alpha) {
    if (alpha === void 0)
      alpha = 0.5;
    this.path_dthufe$_0 = path;
    this.alpha = alpha;
    this.x0_0 = -1.0;
    this.y0_0 = -1.0;
    this.x1_0 = -1.0;
    this.y1_0 = -1.0;
    this.x2_0 = -1.0;
    this.y2_0 = -1.0;
    this._l01_a_0 = 0.0;
    this._l12_a_0 = 0.0;
    this._l23_a_0 = 0.0;
    this._l01_2a_0 = 0.0;
    this._l12_2a_0 = 0.0;
    this._l23_2a_0 = 0.0;
    this.lineStatus_0 = -1;
    this.pointStatus_0 = -1;
  }
  Object.defineProperty(CatmullRomOpen.prototype, 'path', {
    get: function () {
      return this.path_dthufe$_0;
    }
  });
  CatmullRomOpen.prototype.areaStart = function () {
    this.lineStatus_0 = 0;
  };
  CatmullRomOpen.prototype.areaEnd = function () {
    this.lineStatus_0 = -1;
  };
  CatmullRomOpen.prototype.lineStart = function () {
    this.x0_0 = -1.0;
    this.y0_0 = -1.0;
    this.x1_0 = -1.0;
    this.y1_0 = -1.0;
    this.x2_0 = -1.0;
    this.y2_0 = -1.0;
    this._l01_a_0 = 0.0;
    this._l12_a_0 = 0.0;
    this._l23_a_0 = 0.0;
    this._l01_2a_0 = 0.0;
    this._l12_2a_0 = 0.0;
    this._l23_2a_0 = 0.0;
    this.pointStatus_0 = 0;
  };
  CatmullRomOpen.prototype.lineEnd = function () {
    if (this.lineStatus_0 > -1) {
      if (this.lineStatus_0 > 0) {
        this.path.closePath();
      }
      this.lineStatus_0 = 1 - this.lineStatus_0 | 0;
    }
  };
  CatmullRomOpen.prototype.curve_0 = function (x, y) {
    var _x1 = this.x1_0;
    var _y1 = this.y1_0;
    var _x2 = this.x2_0;
    var _y2 = this.y2_0;
    if (this._l01_a_0 > epsilon) {
      var a = 2 * this._l01_2a_0 + 3 * this._l01_a_0 * this._l12_a_0 + this._l12_2a_0;
      var n = 3 * this._l01_a_0 * (this._l01_a_0 + this._l12_a_0);
      _x1 = (this.x1_0 * a - this.x0_0 * this._l12_2a_0 + this.x2_0 * this._l01_2a_0) / n;
      _y1 = (this.y1_0 * a - this.y0_0 * this._l12_2a_0 + this.y2_0 * this._l01_2a_0) / n;
    }
    if (this._l23_a_0 > epsilon) {
      var b = 2 * this._l23_2a_0 + 3 * this._l23_a_0 * this._l12_a_0 + this._l12_2a_0;
      var m = 3 * this._l23_a_0 * (this._l23_a_0 + this._l12_a_0);
      _x2 = (this.x2_0 * b + this.x1_0 * this._l23_2a_0 - x * this._l12_2a_0) / m;
      _y2 = (this.y2_0 * b + this.y1_0 * this._l23_2a_0 - y * this._l12_2a_0) / m;
    }
    this.path.bezierCurveTo_15yvbs$(_x1, _y1, _x2, _y2, this.x2_0, this.y2_0);
  };
  CatmullRomOpen.prototype.point_lu1900$ = function (x, y) {
    if (this.pointStatus_0 > 0) {
      var x23 = this.x2_0 - x;
      var y23 = this.y2_0 - y;
      var $receiver = x23 * x23 + y23 * y23;
      var x_0 = this.alpha;
      this._l23_2a_0 = Math_0.pow($receiver, x_0);
      var x_1 = this._l23_2a_0;
      this._l23_a_0 = Math_0.sqrt(x_1);
    }
    switch (this.pointStatus_0) {
      case 0:
        this.pointStatus_0 = 1;
        break;
      case 1:
        this.pointStatus_0 = 2;
        break;
      case 2:
        this.pointStatus_0 = 3;
        if (this.lineStatus_0 > 0)
          this.path.lineTo_lu1900$(this.x2_0, this.y2_0);
        else
          this.path.moveTo_lu1900$(this.x2_0, this.y2_0);
        break;
      case 3:
        this.pointStatus_0 = 4;
        this.curve_0(x, y);
        break;
      default:this.curve_0(x, y);
        break;
    }
    this._l01_a_0 = this._l12_a_0;
    this._l12_a_0 = this._l23_a_0;
    this._l01_2a_0 = this._l12_2a_0;
    this._l12_2a_0 = this._l23_2a_0;
    this.x0_0 = this.x1_0;
    this.x1_0 = this.x2_0;
    this.x2_0 = x;
    this.y0_0 = this.y1_0;
    this.y1_0 = this.y2_0;
    this.y2_0 = y;
  };
  CatmullRomOpen.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'CatmullRomOpen',
    interfaces: [Curve]
  };
  function Linear(path) {
    this.path_5zpirl$_0 = path;
    this.pointStatus_0 = -1;
    this.lineStatus_0 = -1;
  }
  Object.defineProperty(Linear.prototype, 'path', {
    get: function () {
      return this.path_5zpirl$_0;
    }
  });
  Linear.prototype.areaStart = function () {
    this.lineStatus_0 = 0;
  };
  Linear.prototype.areaEnd = function () {
    this.lineStatus_0 = -1;
  };
  Linear.prototype.lineStart = function () {
    this.pointStatus_0 = 0;
  };
  Linear.prototype.lineEnd = function () {
    if (this.lineStatus_0 > 0 || (this.lineStatus_0 !== 0 && this.pointStatus_0 === 1)) {
      this.path.closePath();
    }
    if (this.lineStatus_0 !== -1)
      this.lineStatus_0 = 1 - this.lineStatus_0 | 0;
  };
  Linear.prototype.point_lu1900$ = function (x, y) {
    if (this.pointStatus_0 === 0) {
      this.pointStatus_0 = 1;
      if (this.lineStatus_0 > 0)
        this.path.lineTo_lu1900$(x, y);
      else
        this.path.moveTo_lu1900$(x, y);
      return;
    }
    if (this.pointStatus_0 === 1) {
      this.pointStatus_0 = 2;
    }
    this.path.lineTo_lu1900$(x, y);
  };
  Linear.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Linear',
    interfaces: [Curve]
  };
  function LinearClosed(path) {
    this.path_efjbg5$_0 = path;
    this.pointStatus_0 = -1;
  }
  Object.defineProperty(LinearClosed.prototype, 'path', {
    get: function () {
      return this.path_efjbg5$_0;
    }
  });
  LinearClosed.prototype.areaStart = function () {
  };
  LinearClosed.prototype.areaEnd = function () {
  };
  LinearClosed.prototype.lineStart = function () {
    this.pointStatus_0 = 0;
  };
  LinearClosed.prototype.lineEnd = function () {
    if (this.pointStatus_0 > 0) {
      this.path.closePath();
    }
  };
  LinearClosed.prototype.point_lu1900$ = function (x, y) {
    if (this.pointStatus_0 > 0) {
      this.path.lineTo_lu1900$(x, y);
    }
     else {
      this.pointStatus_0 = 1;
      this.path.moveTo_lu1900$(x, y);
    }
  };
  LinearClosed.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'LinearClosed',
    interfaces: [Curve]
  };
  function ReflectContext(path) {
    this.path = path;
  }
  ReflectContext.prototype.moveTo_lu1900$ = function (x, y) {
    this.path.moveTo_lu1900$(y, x);
  };
  ReflectContext.prototype.lineTo_lu1900$ = function (x, y) {
    this.path.lineTo_lu1900$(y, x);
  };
  ReflectContext.prototype.closePath = function () {
    this.path.closePath();
  };
  ReflectContext.prototype.bezierCurveTo_15yvbs$ = function (cpx1, cpy1, cpx2, cpy2, x, y) {
    this.path.bezierCurveTo_15yvbs$(cpy1, cpx1, cpy2, cpx2, y, x);
  };
  ReflectContext.prototype.quadraticCurveTo_6y0v78$ = function (cpx, cpy, x, y) {
  };
  ReflectContext.prototype.arcTo_1lq62i$ = function (cpx, cpy, x, y, radius) {
  };
  ReflectContext.prototype.arc_6p3vsx$$default = function (centerX, centerY, radius, startAngle, endAngle, counterClockWise) {
  };
  ReflectContext.prototype.rect_6y0v78$ = function (x, y, w, h) {
  };
  ReflectContext.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ReflectContext',
    interfaces: [Path]
  };
  function AbstractMonotone(path) {
    this.path_vyc50x$_0 = path;
    this.x0_ngp9oe$_0 = -1.0;
    this.y0_ngpaf3$_0 = -1.0;
    this.x1_ngp9p9$_0 = -1.0;
    this.y1_ngpafy$_0 = -1.0;
    this.t0_ngp6pm$_0 = -1.0;
    this.lineStatus_9hjpzk$_0 = -1;
    this.pointStatus_s2hsv8$_0 = -1;
  }
  Object.defineProperty(AbstractMonotone.prototype, 'path', {
    get: function () {
      return this.path_vyc50x$_0;
    }
  });
  AbstractMonotone.prototype.areaStart = function () {
    this.lineStatus_9hjpzk$_0 = 0;
  };
  AbstractMonotone.prototype.areaEnd = function () {
    this.lineStatus_9hjpzk$_0 = -1;
  };
  AbstractMonotone.prototype.lineStart = function () {
    this.x0_ngp9oe$_0 = -1.0;
    this.y0_ngpaf3$_0 = -1.0;
    this.x1_ngp9p9$_0 = -1.0;
    this.y1_ngpafy$_0 = -1.0;
    this.t0_ngp6pm$_0 = -1.0;
    this.pointStatus_s2hsv8$_0 = 0;
  };
  AbstractMonotone.prototype.lineEnd = function () {
    switch (this.pointStatus_s2hsv8$_0) {
      case 2:
        this.path.lineTo_lu1900$(this.x1_ngp9p9$_0, this.y1_ngpafy$_0);
        break;
      case 3:
        this.curve_h2wha9$_0(this.t0_ngp6pm$_0, this.slope2_14dthe$(this.t0_ngp6pm$_0));
        break;
    }
    if (this.lineStatus_9hjpzk$_0 > -1) {
      if (this.lineStatus_9hjpzk$_0 > 0) {
        this.path.closePath();
      }
      this.lineStatus_9hjpzk$_0 = 1 - this.lineStatus_9hjpzk$_0 | 0;
    }
  };
  AbstractMonotone.prototype.point_lu1900$ = function (x, y) {
    var t1 = -1.0;
    if (x === this.x1_ngp9p9$_0 && y === this.y1_ngpafy$_0)
      return;
    switch (this.pointStatus_s2hsv8$_0) {
      case 0:
        this.pointStatus_s2hsv8$_0 = 1;
        if (this.lineStatus_9hjpzk$_0 > 0)
          this.path.lineTo_lu1900$(x, y);
        else
          this.path.moveTo_lu1900$(x, y);
        break;
      case 1:
        this.pointStatus_s2hsv8$_0 = 2;
        break;
      case 2:
        this.pointStatus_s2hsv8$_0 = 3;
        t1 = this.slope3_lu1900$(x, y);
        this.curve_h2wha9$_0(this.slope2_14dthe$(t1), t1);
        break;
      default:t1 = this.slope3_lu1900$(x, y);
        this.curve_h2wha9$_0(this.t0_ngp6pm$_0, t1);
        break;
    }
    this.x0_ngp9oe$_0 = this.x1_ngp9p9$_0;
    this.x1_ngp9p9$_0 = x;
    this.y0_ngpaf3$_0 = this.y1_ngpafy$_0;
    this.y1_ngpafy$_0 = y;
    this.t0_ngp6pm$_0 = t1;
  };
  AbstractMonotone.prototype.curve_h2wha9$_0 = function (t0, t1) {
    var dx = (this.x1_ngp9p9$_0 - this.x0_ngp9oe$_0) / 3.0;
    this.path.bezierCurveTo_15yvbs$(this.x0_ngp9oe$_0 + dx, this.y0_ngpaf3$_0 + dx * t0, this.x1_ngp9p9$_0 - dx, this.y1_ngpafy$_0 - dx * t1, this.x1_ngp9p9$_0, this.y1_ngpafy$_0);
  };
  function AbstractMonotone$slope3$sign(num) {
    return num < 0 ? -1.0 : 1.0;
  }
  AbstractMonotone.prototype.slope3_lu1900$ = function (x2, y2) {
    var tmp$;
    var sign = AbstractMonotone$slope3$sign;
    var h0 = this.x1_ngp9p9$_0 - this.x0_ngp9oe$_0;
    var h1 = x2 - this.x1_ngp9p9$_0;
    var divider0 = h0 !== 0.0 ? h0 : h1 < 0 ? -0.0 : 0.0;
    var divider1 = h1 !== 0.0 ? h1 : h0 < 0 ? -0.0 : 0.0;
    var s0 = (this.y1_ngpafy$_0 - this.y0_ngpaf3$_0) / divider0;
    var s1 = (y2 - this.y1_ngpafy$_0) / divider1;
    var p = (s0 * h1 + s1 * h0) / (h0 + h1);
    var tmp$_0 = sign(s0) + sign(s1);
    var tmp$_1 = Math_0.abs(s0);
    var tmp$_2 = Math_0.abs(s1);
    var b = 0.5 * Math_0.abs(p);
    var b_0 = Math_0.min(tmp$_2, b);
    var value = tmp$_0 * Math_0.min(tmp$_1, b_0);
    if (isNaN_0(value)) {
      tmp$ = 0.0;
    }
     else {
      tmp$ = value;
    }
    return tmp$;
  };
  AbstractMonotone.prototype.slope2_14dthe$ = function (t) {
    var h = this.x1_ngp9p9$_0 - this.x0_ngp9oe$_0;
    return h !== 0.0 ? (3 * (this.y1_ngpafy$_0 - this.y0_ngpaf3$_0) / h - t) / 2 : t;
  };
  AbstractMonotone.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'AbstractMonotone',
    interfaces: [Curve]
  };
  function MonotoneX(path) {
    AbstractMonotone.call(this, path);
  }
  MonotoneX.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'MonotoneX',
    interfaces: [AbstractMonotone]
  };
  function MonotoneY(path) {
    AbstractMonotone.call(this, new ReflectContext(path));
  }
  MonotoneY.prototype.point_lu1900$ = function (x, y) {
    AbstractMonotone.prototype.point_lu1900$.call(this, y, x);
  };
  MonotoneY.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'MonotoneY',
    interfaces: [AbstractMonotone]
  };
  function Natural(path) {
    this.path_68mrct$_0 = path;
    this.x_0 = ArrayList_init();
    this.y_0 = ArrayList_init();
    this.lineStatus_0 = -1;
  }
  Object.defineProperty(Natural.prototype, 'path', {
    get: function () {
      return this.path_68mrct$_0;
    }
  });
  Natural.prototype.areaStart = function () {
    this.lineStatus_0 = 0;
  };
  Natural.prototype.areaEnd = function () {
    this.lineStatus_0 = -1;
  };
  Natural.prototype.lineStart = function () {
    this.x_0.clear();
    this.y_0.clear();
  };
  Natural.prototype.lineEnd = function () {
    var n = this.x_0.size;
    if (n > 0) {
      if (this.lineStatus_0 > 0)
        this.path.lineTo_lu1900$(this.x_0.get_za3lpa$(0), this.y_0.get_za3lpa$(0));
      else
        this.path.moveTo_lu1900$(this.x_0.get_za3lpa$(0), this.y_0.get_za3lpa$(0));
      if (n > 1) {
        if (n === 2)
          this.path.lineTo_lu1900$(this.x_0.get_za3lpa$(1), this.y_0.get_za3lpa$(1));
        else {
          var px = this.controlPoints_0(this.x_0);
          var py = this.controlPoints_0(this.y_0);
          var i0 = {v: 0};
          var i1 = {v: 1};
          var tmp$;
          tmp$ = until(1, n).iterator();
          while (tmp$.hasNext()) {
            var element = tmp$.next();
            this.path.bezierCurveTo_15yvbs$(px[0][i0.v], py[0][i0.v], px[1][i0.v], py[1][i0.v], this.x_0.get_za3lpa$(i1.v), this.y_0.get_za3lpa$(i1.v));
            i0.v = i0.v + 1 | 0;
            i1.v = i1.v + 1 | 0;
          }
        }
      }
    }
    if (this.lineStatus_0 > -1) {
      if (this.lineStatus_0 > 0) {
        this.path.closePath();
      }
      this.lineStatus_0 = 1 - this.lineStatus_0 | 0;
    }
    this.x_0.clear();
    this.y_0.clear();
  };
  Natural.prototype.controlPoints_0 = function (points) {
    var n = points.size - 1 | 0;
    var m = {v: null};
    var array = Array_0(n);
    var tmp$;
    tmp$ = array.length - 1 | 0;
    for (var i = 0; i <= tmp$; i++) {
      array[i] = 0.0;
    }
    var a = array;
    var array_0 = Array_0(n);
    var tmp$_0;
    tmp$_0 = array_0.length - 1 | 0;
    for (var i_0 = 0; i_0 <= tmp$_0; i_0++) {
      array_0[i_0] = 0.0;
    }
    var b = array_0;
    var array_1 = Array_0(n);
    var tmp$_1;
    tmp$_1 = array_1.length - 1 | 0;
    for (var i_1 = 0; i_1 <= tmp$_1; i_1++) {
      array_1[i_1] = 0.0;
    }
    var r = array_1;
    a[0] = 0.0;
    b[0] = 2.0;
    r[0] = points.get_za3lpa$(0) + 2 * points.get_za3lpa$(1);
    var tmp$_2;
    tmp$_2 = until(1, n - 1 | 0).iterator();
    while (tmp$_2.hasNext()) {
      var element = tmp$_2.next();
      a[element] = 1.0;
      b[element] = 4.0;
      r[element] = 4 * points.get_za3lpa$(element) + 2 * points.get_za3lpa$(element + 1 | 0);
    }
    a[n - 1 | 0] = 2.0;
    b[n - 1 | 0] = 7.0;
    r[n - 1 | 0] = 8 * points.get_za3lpa$(n - 1 | 0) + points.get_za3lpa$(n);
    var tmp$_3;
    tmp$_3 = until(1, n).iterator();
    while (tmp$_3.hasNext()) {
      var element_0 = tmp$_3.next();
      m.v = a[element_0] / b[element_0 - 1 | 0];
      b[element_0] = b[element_0] - m.v;
      r[element_0] = r[element_0] - m.v * r[element_0 - 1 | 0];
    }
    a[n - 1 | 0] = r[n - 1 | 0] / b[n - 1 | 0];
    var tmp$_4;
    tmp$_4 = downTo(n - 2 | 0, 0).iterator();
    while (tmp$_4.hasNext()) {
      var element_1 = tmp$_4.next();
      a[element_1] = (r[element_1] - a[element_1 + 1 | 0]) / b[element_1];
    }
    b[n - 1 | 0] = (points.get_za3lpa$(n) + a[n - 1 | 0]) / 2;
    var tmp$_5;
    tmp$_5 = until(0, n - 1 | 0).iterator();
    while (tmp$_5.hasNext()) {
      var element_2 = tmp$_5.next();
      b[element_2] = 2 * points.get_za3lpa$(element_2 + 1 | 0) - a[element_2 + 1 | 0];
    }
    return [a, b];
  };
  Natural.prototype.point_lu1900$ = function (x, y) {
    this.x_0.add_11rb$(x);
    this.y_0.add_11rb$(y);
  };
  Natural.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Natural',
    interfaces: [Curve]
  };
  function AbstractRadial(path, curve) {
    this.path_urgakl$_0 = path;
    this.curve = curve;
  }
  Object.defineProperty(AbstractRadial.prototype, 'path', {
    get: function () {
      return this.path_urgakl$_0;
    }
  });
  AbstractRadial.prototype.areaEnd = function () {
    this.curve.areaEnd();
  };
  AbstractRadial.prototype.lineStart = function () {
    this.curve.lineStart();
  };
  AbstractRadial.prototype.lineEnd = function () {
    this.curve.lineEnd();
  };
  AbstractRadial.prototype.areaStart = function () {
    this.curve.areaStart();
  };
  AbstractRadial.prototype.point_lu1900$ = function (x, y) {
    this.curve.point_lu1900$(y * Math_0.sin(x), y * -Math_0.cos(x));
  };
  AbstractRadial.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'AbstractRadial',
    interfaces: [Curve]
  };
  function RadialLinear(path) {
    AbstractRadial.call(this, path, new Linear(path));
  }
  RadialLinear.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'RadialLinear',
    interfaces: [AbstractRadial]
  };
  function Radial(path, curve) {
    AbstractRadial.call(this, path, curve);
  }
  Radial.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Radial',
    interfaces: [AbstractRadial]
  };
  function AbstractStep(path, changePoint) {
    if (changePoint === void 0)
      changePoint = 0.5;
    this.path_4xxqag$_0 = path;
    this.x_755m7j$_0 = -1.0;
    this.y_755m8e$_0 = -1.0;
    this.lineStatus_rhinjb$_0 = -1;
    this.pointStatus_id918b$_0 = -1;
    this._changePoint_fwu3po$_0 = changePoint;
  }
  Object.defineProperty(AbstractStep.prototype, 'path', {
    get: function () {
      return this.path_4xxqag$_0;
    }
  });
  AbstractStep.prototype.areaStart = function () {
    this.lineStatus_rhinjb$_0 = 0;
  };
  AbstractStep.prototype.areaEnd = function () {
    this.lineStatus_rhinjb$_0 = -1;
  };
  AbstractStep.prototype.lineStart = function () {
    this.x_755m7j$_0 = -1.0;
    this.y_755m8e$_0 = -1.0;
    this.pointStatus_id918b$_0 = 0;
  };
  AbstractStep.prototype.lineEnd = function () {
    if (0 < this._changePoint_fwu3po$_0 && this._changePoint_fwu3po$_0 < 1 && this.pointStatus_id918b$_0 === 2)
      this.path.lineTo_lu1900$(this.x_755m7j$_0, this.y_755m8e$_0);
    if (this.lineStatus_rhinjb$_0 > 0)
      this.path.closePath();
    if (this.lineStatus_rhinjb$_0 >= 0) {
      this._changePoint_fwu3po$_0 = 1 - this._changePoint_fwu3po$_0;
      this.lineStatus_rhinjb$_0 = 1 - this.lineStatus_rhinjb$_0 | 0;
    }
  };
  AbstractStep.prototype.point_lu1900$ = function (x, y) {
    switch (this.pointStatus_id918b$_0) {
      case 0:
        this.pointStatus_id918b$_0 = 1;
        if (this.lineStatus_rhinjb$_0 > 0)
          this.path.lineTo_lu1900$(x, y);
        else
          this.path.moveTo_lu1900$(x, y);
        this.x_755m7j$_0 = x;
        this.y_755m8e$_0 = y;
        return;
      case 1:
        this.pointStatus_id918b$_0 = 2;
        break;
      default:break;
    }
    if (this._changePoint_fwu3po$_0 <= 0) {
      this.path.lineTo_lu1900$(this.x_755m7j$_0, y);
      this.path.lineTo_lu1900$(x, y);
    }
     else {
      var x1 = this.x_755m7j$_0 * (1 - this._changePoint_fwu3po$_0) + x * this._changePoint_fwu3po$_0;
      this.path.lineTo_lu1900$(x1, this.y_755m8e$_0);
      this.path.lineTo_lu1900$(x1, y);
    }
    this.x_755m7j$_0 = x;
    this.y_755m8e$_0 = y;
  };
  AbstractStep.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'AbstractStep',
    interfaces: [Curve]
  };
  function Step(path) {
    AbstractStep.call(this, path, 0.5);
    this.path_1550ka$_0 = path;
  }
  Object.defineProperty(Step.prototype, 'path', {
    get: function () {
      return this.path_1550ka$_0;
    }
  });
  Step.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Step',
    interfaces: [AbstractStep]
  };
  function StepBefore(path) {
    AbstractStep.call(this, path, 0.0);
    this.path_cvf2md$_0 = path;
  }
  Object.defineProperty(StepBefore.prototype, 'path', {
    get: function () {
      return this.path_cvf2md$_0;
    }
  });
  StepBefore.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'StepBefore',
    interfaces: [AbstractStep]
  };
  function StepAfter(path) {
    AbstractStep.call(this, path, 1.0);
    this.path_7q65z8$_0 = path;
  }
  Object.defineProperty(StepAfter.prototype, 'path', {
    get: function () {
      return this.path_7q65z8$_0;
    }
  });
  StepAfter.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'StepAfter',
    interfaces: [AbstractStep]
  };
  function linkBuilderH(init) {
    var $receiver = new LinkBuilder();
    $receiver.curve = getCallableRef('curveHorizontal', function ($receiver, path, x0, y0, x1, y1) {
      return $receiver.curveHorizontal_5nzcg9$(path, x0, y0, x1, y1), Unit;
    }.bind(null, $receiver));
    init($receiver);
    return $receiver;
  }
  function linkBuilderV(init) {
    var $receiver = new LinkBuilder();
    $receiver.curve = getCallableRef('curveVertical', function ($receiver, path, x0, y0, x1, y1) {
      return $receiver.curveVertical_5nzcg9$(path, x0, y0, x1, y1), Unit;
    }.bind(null, $receiver));
    init($receiver);
    return $receiver;
  }
  function LinkBuilder() {
    this.x0 = const_0(0.0);
    this.x1 = const_0(0.0);
    this.y0 = const_0(0.0);
    this.y1 = const_0(0.0);
    this.curve = getCallableRef('curveHorizontal', function ($receiver, path, x0, y0, x1, y1) {
      return $receiver.curveHorizontal_5nzcg9$(path, x0, y0, x1, y1), Unit;
    }.bind(null, this));
  }
  LinkBuilder.prototype.link_lr2v95$ = function (data, path) {
    this.curve(path, this.x0(data), this.y0(data), this.x1(data), this.y1(data));
  };
  LinkBuilder.prototype.curveHorizontal_5nzcg9$ = function (path, x0, y0, x1, y1) {
    path.moveTo_lu1900$(x0, y0);
    var newX0 = (x0 + x1) / 2;
    path.bezierCurveTo_15yvbs$(newX0, y0, newX0, y1, x1, y1);
  };
  LinkBuilder.prototype.curveVertical_5nzcg9$ = function (path, x0, y0, x1, y1) {
    path.moveTo_lu1900$(x0, y0);
    var newY0 = (y0 + y1) / 2;
    path.bezierCurveTo_15yvbs$(x0, newY0, x1, newY0, x1, y1);
  };
  LinkBuilder.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'LinkBuilder',
    interfaces: []
  };
  function StackSpace(from, to, paramIndex, data) {
    this.from = from;
    this.to = to;
    this.paramIndex = paramIndex;
    this.data = data;
  }
  StackSpace.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'StackSpace',
    interfaces: []
  };
  StackSpace.prototype.component1 = function () {
    return this.from;
  };
  StackSpace.prototype.component2 = function () {
    return this.to;
  };
  StackSpace.prototype.component3 = function () {
    return this.paramIndex;
  };
  StackSpace.prototype.component4 = function () {
    return this.data;
  };
  StackSpace.prototype.copy_dyx6fb$ = function (from, to, paramIndex, data) {
    return new StackSpace(from === void 0 ? this.from : from, to === void 0 ? this.to : to, paramIndex === void 0 ? this.paramIndex : paramIndex, data === void 0 ? this.data : data);
  };
  StackSpace.prototype.toString = function () {
    return 'StackSpace(from=' + Kotlin.toString(this.from) + (', to=' + Kotlin.toString(this.to)) + (', paramIndex=' + Kotlin.toString(this.paramIndex)) + (', data=' + Kotlin.toString(this.data)) + ')';
  };
  StackSpace.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.from) | 0;
    result = result * 31 + Kotlin.hashCode(this.to) | 0;
    result = result * 31 + Kotlin.hashCode(this.paramIndex) | 0;
    result = result * 31 + Kotlin.hashCode(this.data) | 0;
    return result;
  };
  StackSpace.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.from, other.from) && Kotlin.equals(this.to, other.to) && Kotlin.equals(this.paramIndex, other.paramIndex) && Kotlin.equals(this.data, other.data)))));
  };
  function StackParam(stackedValues, index) {
    this.stackedValues = stackedValues;
    this.index = index;
  }
  StackParam.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'StackParam',
    interfaces: []
  };
  StackParam.prototype.component1 = function () {
    return this.stackedValues;
  };
  StackParam.prototype.component2 = function () {
    return this.index;
  };
  StackParam.prototype.copy_6am9o3$ = function (stackedValues, index) {
    return new StackParam(stackedValues === void 0 ? this.stackedValues : stackedValues, index === void 0 ? this.index : index);
  };
  StackParam.prototype.toString = function () {
    return 'StackParam(stackedValues=' + Kotlin.toString(this.stackedValues) + (', index=' + Kotlin.toString(this.index)) + ')';
  };
  StackParam.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.stackedValues) | 0;
    result = result * 31 + Kotlin.hashCode(this.index) | 0;
    return result;
  };
  StackParam.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.stackedValues, other.stackedValues) && Kotlin.equals(this.index, other.index)))));
  };
  function stack(init) {
    var $receiver = new StackGenerator();
    init($receiver);
    return $receiver;
  }
  function StackGenerator() {
    this.series = const_0([0.0]);
    this.order = StackOrder$NONE_getInstance();
    this.offset = StackOffset$NONE_getInstance();
  }
  StackGenerator.prototype.stack_4ezy5m$ = function (data) {
    var ret = ArrayList_init();
    var firstValue = this.series(data.get_za3lpa$(0));
    var tmp$, tmp$_0;
    var index = 0;
    for (tmp$ = 0; tmp$ !== firstValue.length; ++tmp$) {
      var item = firstValue[tmp$];
      var index_0 = (tmp$_0 = index, index = tmp$_0 + 1 | 0, tmp$_0);
      var stackedValues = ArrayList_init();
      var stack = new StackParam(stackedValues, index_0);
      ret.add_11rb$(stack);
    }
    var tmp$_1, tmp$_0_0;
    var index_1 = 0;
    tmp$_1 = data.iterator();
    while (tmp$_1.hasNext()) {
      var item_0 = tmp$_1.next();
      var index1 = checkIndexOverflow((tmp$_0_0 = index_1, index_1 = tmp$_0_0 + 1 | 0, tmp$_0_0));
      var $receiver = this.series(item_0);
      var tmp$_2, tmp$_0_1;
      var index_2 = 0;
      for (tmp$_2 = 0; tmp$_2 !== $receiver.length; ++tmp$_2) {
        var item_1 = $receiver[tmp$_2];
        var stack_0 = ret.get_za3lpa$((tmp$_0_1 = index_2, index_2 = tmp$_0_1 + 1 | 0, tmp$_0_1));
        stack_0.stackedValues.add_11rb$(new StackSpace(0.0, item_1, index1, data.get_za3lpa$(index1)));
      }
    }
    var indexes = this.order.sort_mp22wn$(ret);
    var tmp$_3, tmp$_0_2;
    var index_3 = 0;
    tmp$_3 = indexes.iterator();
    while (tmp$_3.hasNext()) {
      var item_2 = tmp$_3.next();
      var realIndex = checkIndexOverflow((tmp$_0_2 = index_3, index_3 = tmp$_0_2 + 1 | 0, tmp$_0_2));
      ret.get_za3lpa$(item_2).index = realIndex;
    }
    this.offset.offset_mp22wn$(ret);
    return copyToArray(ret);
  };
  StackGenerator.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'StackGenerator',
    interfaces: []
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
  function StackOffset(name, ordinal) {
    Enum.call(this);
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function StackOffset_initFields() {
    StackOffset_initFields = function () {
    };
    StackOffset$NONE_instance = new StackOffset('NONE', 0);
    StackOffset$EXPAND_instance = new StackOffset('EXPAND', 1);
    StackOffset$DIVERGING_instance = new StackOffset('DIVERGING', 2);
    StackOffset$SILHOUETTE_instance = new StackOffset('SILHOUETTE', 3);
    StackOffset$WIGGLE_instance = new StackOffset('WIGGLE', 4);
  }
  var StackOffset$NONE_instance;
  function StackOffset$NONE_getInstance() {
    StackOffset_initFields();
    return StackOffset$NONE_instance;
  }
  var StackOffset$EXPAND_instance;
  function StackOffset$EXPAND_getInstance() {
    StackOffset_initFields();
    return StackOffset$EXPAND_instance;
  }
  var StackOffset$DIVERGING_instance;
  function StackOffset$DIVERGING_getInstance() {
    StackOffset_initFields();
    return StackOffset$DIVERGING_instance;
  }
  var StackOffset$SILHOUETTE_instance;
  function StackOffset$SILHOUETTE_getInstance() {
    StackOffset_initFields();
    return StackOffset$SILHOUETTE_instance;
  }
  var StackOffset$WIGGLE_instance;
  function StackOffset$WIGGLE_getInstance() {
    StackOffset_initFields();
    return StackOffset$WIGGLE_instance;
  }
  StackOffset.prototype.offset_mp22wn$ = function (ret) {
    switch (this.name) {
      case 'EXPAND':
        offsetExpand(ret);
        break;
      case 'DIVERGING':
        offsetDiverging(ret);
        break;
      case 'SILHOUETTE':
        offsetSilhouette(ret);
        break;
      case 'WIGGLE':
        offsetWiggle(ret);
        break;
      case 'NONE':
        offsetNone(ret);
        break;
      default:Kotlin.noWhenBranchMatched();
        break;
    }
  };
  StackOffset.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'StackOffset',
    interfaces: [Enum]
  };
  function StackOffset$values() {
    return [StackOffset$NONE_getInstance(), StackOffset$EXPAND_getInstance(), StackOffset$DIVERGING_getInstance(), StackOffset$SILHOUETTE_getInstance(), StackOffset$WIGGLE_getInstance()];
  }
  StackOffset.values = StackOffset$values;
  function StackOffset$valueOf(name) {
    switch (name) {
      case 'NONE':
        return StackOffset$NONE_getInstance();
      case 'EXPAND':
        return StackOffset$EXPAND_getInstance();
      case 'DIVERGING':
        return StackOffset$DIVERGING_getInstance();
      case 'SILHOUETTE':
        return StackOffset$SILHOUETTE_getInstance();
      case 'WIGGLE':
        return StackOffset$WIGGLE_getInstance();
      default:throwISE('No enum constant io.data2viz.shape.stack.StackOffset.' + name);
    }
  }
  StackOffset.valueOf_61zpoe$ = StackOffset$valueOf;
  function offsetNone$lambda(it) {
    return it.index;
  }
  function offsetNone($receiver) {
    var orderedParams = sortedWith($receiver, new Comparator$ObjectLiteral(compareBy$lambda(offsetNone$lambda)));
    var array = Array_0($receiver.get_za3lpa$(0).stackedValues.size);
    var tmp$;
    tmp$ = array.length - 1 | 0;
    for (var i = 0; i <= tmp$; i++) {
      array[i] = 0.0;
    }
    var sums = array;
    var tmp$_0;
    tmp$_0 = orderedParams.iterator();
    while (tmp$_0.hasNext()) {
      var element = tmp$_0.next();
      var tmp$_1, tmp$_0_0;
      var index = 0;
      tmp$_1 = element.stackedValues.iterator();
      while (tmp$_1.hasNext()) {
        var item = tmp$_1.next();
        var index_0 = checkIndexOverflow((tmp$_0_0 = index, index = tmp$_0_0 + 1 | 0, tmp$_0_0));
        item.from = item.from + sums[index_0];
        sums[index_0] = sums[index_0] + item.to;
        item.to = sums[index_0];
      }
    }
    return $receiver;
  }
  function offsetExpand$lambda(it) {
    return it.index;
  }
  function offsetExpand($receiver) {
    var orderedParams = sortedWith($receiver, new Comparator$ObjectLiteral(compareBy$lambda(offsetExpand$lambda)));
    var array = Array_0($receiver.get_za3lpa$(0).stackedValues.size);
    var tmp$;
    tmp$ = array.length - 1 | 0;
    for (var i = 0; i <= tmp$; i++) {
      array[i] = 0.0;
    }
    var sums = array;
    var tmp$_0;
    tmp$_0 = orderedParams.iterator();
    while (tmp$_0.hasNext()) {
      var element = tmp$_0.next();
      var tmp$_1, tmp$_0_0;
      var index = 0;
      tmp$_1 = element.stackedValues.iterator();
      while (tmp$_1.hasNext()) {
        var item = tmp$_1.next();
        var index_0 = checkIndexOverflow((tmp$_0_0 = index, index = tmp$_0_0 + 1 | 0, tmp$_0_0));
        item.from = item.from + sums[index_0];
        sums[index_0] = sums[index_0] + item.to;
        item.to = sums[index_0];
      }
    }
    var tmp$_2;
    tmp$_2 = orderedParams.iterator();
    while (tmp$_2.hasNext()) {
      var element_0 = tmp$_2.next();
      var tmp$_3, tmp$_0_1;
      var index_1 = 0;
      tmp$_3 = element_0.stackedValues.iterator();
      while (tmp$_3.hasNext()) {
        var item_0 = tmp$_3.next();
        var index_2 = checkIndexOverflow((tmp$_0_1 = index_1, index_1 = tmp$_0_1 + 1 | 0, tmp$_0_1));
        if (sums[index_2] !== 0.0) {
          item_0.from = item_0.from / sums[index_2];
          item_0.to = item_0.to / sums[index_2];
        }
      }
    }
    return $receiver;
  }
  function offsetDiverging$lambda(it) {
    return it.index;
  }
  function offsetDiverging($receiver) {
    var orderedParams = sortedWith($receiver, new Comparator$ObjectLiteral(compareBy$lambda(offsetDiverging$lambda)));
    var array = Array_0($receiver.get_za3lpa$(0).stackedValues.size);
    var tmp$;
    tmp$ = array.length - 1 | 0;
    for (var i = 0; i <= tmp$; i++) {
      array[i] = 0.0;
    }
    var sumsPositives = array;
    var array_0 = Array_0($receiver.get_za3lpa$(0).stackedValues.size);
    var tmp$_0;
    tmp$_0 = array_0.length - 1 | 0;
    for (var i_0 = 0; i_0 <= tmp$_0; i_0++) {
      array_0[i_0] = 0.0;
    }
    var sumsNegatives = array_0;
    var tmp$_1;
    tmp$_1 = orderedParams.iterator();
    while (tmp$_1.hasNext()) {
      var element = tmp$_1.next();
      var tmp$_2, tmp$_0_0;
      var index = 0;
      tmp$_2 = element.stackedValues.iterator();
      while (tmp$_2.hasNext()) {
        var item = tmp$_2.next();
        var index_0 = checkIndexOverflow((tmp$_0_0 = index, index = tmp$_0_0 + 1 | 0, tmp$_0_0));
        if (item.to >= 0) {
          item.from = item.from + sumsPositives[index_0];
          sumsPositives[index_0] = sumsPositives[index_0] + item.to;
          item.to = sumsPositives[index_0];
        }
         else {
          item.from = item.to + sumsNegatives[index_0];
          var temp = item.to;
          item.to = sumsNegatives[index_0];
          sumsNegatives[index_0] = sumsNegatives[index_0] + temp;
        }
      }
    }
    return $receiver;
  }
  function offsetSilhouette$lambda(it) {
    return it.index;
  }
  function offsetSilhouette($receiver) {
    var orderedParams = sortedWith($receiver, new Comparator$ObjectLiteral(compareBy$lambda(offsetSilhouette$lambda)));
    var array = Array_0($receiver.get_za3lpa$(0).stackedValues.size);
    var tmp$;
    tmp$ = array.length - 1 | 0;
    for (var i = 0; i <= tmp$; i++) {
      array[i] = 0.0;
    }
    var sums = array;
    var tmp$_0;
    tmp$_0 = orderedParams.iterator();
    while (tmp$_0.hasNext()) {
      var element = tmp$_0.next();
      var tmp$_1, tmp$_0_0;
      var index = 0;
      tmp$_1 = element.stackedValues.iterator();
      while (tmp$_1.hasNext()) {
        var item = tmp$_1.next();
        var index_0 = checkIndexOverflow((tmp$_0_0 = index, index = tmp$_0_0 + 1 | 0, tmp$_0_0));
        item.from = item.from + sums[index_0];
        sums[index_0] = sums[index_0] + item.to;
        item.to = sums[index_0];
      }
    }
    var tmp$_2;
    tmp$_2 = orderedParams.iterator();
    while (tmp$_2.hasNext()) {
      var element_0 = tmp$_2.next();
      var tmp$_3, tmp$_0_1;
      var index_1 = 0;
      tmp$_3 = element_0.stackedValues.iterator();
      while (tmp$_3.hasNext()) {
        var item_0 = tmp$_3.next();
        var index_2 = checkIndexOverflow((tmp$_0_1 = index_1, index_1 = tmp$_0_1 + 1 | 0, tmp$_0_1));
        item_0.from = item_0.from - sums[index_2] / 2.0;
        item_0.to = item_0.to - sums[index_2] / 2.0;
      }
    }
    return $receiver;
  }
  function offsetWiggle$lambda(it) {
    return it.index;
  }
  function offsetWiggle($receiver) {
    var orderedParams = sortedWith($receiver, new Comparator$ObjectLiteral(compareBy$lambda(offsetWiggle$lambda)));
    var sum = 0.0;
    var firstSerie = orderedParams.get_za3lpa$(0).stackedValues;
    var seriesSize = firstSerie.size;
    var dataSize = orderedParams.size;
    for (var serieIndex = 1; serieIndex < seriesSize; serieIndex++) {
      var s1 = {v: 0.0};
      var s2 = {v: 0.0};
      var tmp$, tmp$_0;
      var index = 0;
      tmp$ = orderedParams.iterator();
      while (tmp$.hasNext()) {
        var item = tmp$.next();
        var dataIndex = checkIndexOverflow((tmp$_0 = index, index = tmp$_0 + 1 | 0, tmp$_0));
        var sij0 = item.stackedValues.get_za3lpa$(serieIndex).to;
        var sij1 = item.stackedValues.get_za3lpa$(serieIndex - 1 | 0).to;
        var s3 = (sij0 - sij1) / 2;
        for (var k = 0; k < dataIndex; k++) {
          var sk = orderedParams.get_za3lpa$(k);
          var skj0 = sk.stackedValues.get_za3lpa$(serieIndex).to;
          var skj1 = sk.stackedValues.get_za3lpa$(serieIndex - 1 | 0).to;
          s3 += skj0 - skj1;
        }
        s1.v += sij0;
        s2.v += s3 * sij0;
      }
      firstSerie.get_za3lpa$(serieIndex - 1 | 0).from = sum;
      firstSerie.get_za3lpa$(serieIndex - 1 | 0).to = firstSerie.get_za3lpa$(serieIndex - 1 | 0).to + firstSerie.get_za3lpa$(serieIndex - 1 | 0).from;
      if (s1.v !== 0.0) {
        sum -= s2.v / s1.v;
      }
    }
    firstSerie.get_za3lpa$(seriesSize - 1 | 0).from = sum;
    firstSerie.get_za3lpa$(seriesSize - 1 | 0).to = firstSerie.get_za3lpa$(seriesSize - 1 | 0).to + firstSerie.get_za3lpa$(seriesSize - 1 | 0).from;
    return offsetNone($receiver);
  }
  function Comparator$ObjectLiteral_0(closure$comparison) {
    this.closure$comparison = closure$comparison;
  }
  Comparator$ObjectLiteral_0.prototype.compare = function (a, b) {
    return this.closure$comparison(a, b);
  };
  Comparator$ObjectLiteral_0.$metadata$ = {kind: Kind_CLASS, interfaces: [Comparator]};
  var compareBy$lambda_0 = wrapFunction(function () {
    var compareValues = Kotlin.kotlin.comparisons.compareValues_s00gnj$;
    return function (closure$selector) {
      return function (a, b) {
        var selector = closure$selector;
        return compareValues(selector(a), selector(b));
      };
    };
  });
  function Comparator$ObjectLiteral_1(closure$comparison) {
    this.closure$comparison = closure$comparison;
  }
  Comparator$ObjectLiteral_1.prototype.compare = function (a, b) {
    return this.closure$comparison(a, b);
  };
  Comparator$ObjectLiteral_1.$metadata$ = {kind: Kind_CLASS, interfaces: [Comparator]};
  var compareByDescending$lambda = wrapFunction(function () {
    var compareValues = Kotlin.kotlin.comparisons.compareValues_s00gnj$;
    return function (closure$selector) {
      return function (a, b) {
        var selector = closure$selector;
        return compareValues(selector(b), selector(a));
      };
    };
  });
  function StackOrder(name, ordinal) {
    Enum.call(this);
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function StackOrder_initFields() {
    StackOrder_initFields = function () {
    };
    StackOrder$NONE_instance = new StackOrder('NONE', 0);
    StackOrder$ASCENDING_instance = new StackOrder('ASCENDING', 1);
    StackOrder$DESCENDING_instance = new StackOrder('DESCENDING', 2);
    StackOrder$REVERSE_instance = new StackOrder('REVERSE', 3);
    StackOrder$INSIDEOUT_instance = new StackOrder('INSIDEOUT', 4);
  }
  var StackOrder$NONE_instance;
  function StackOrder$NONE_getInstance() {
    StackOrder_initFields();
    return StackOrder$NONE_instance;
  }
  var StackOrder$ASCENDING_instance;
  function StackOrder$ASCENDING_getInstance() {
    StackOrder_initFields();
    return StackOrder$ASCENDING_instance;
  }
  var StackOrder$DESCENDING_instance;
  function StackOrder$DESCENDING_getInstance() {
    StackOrder_initFields();
    return StackOrder$DESCENDING_instance;
  }
  var StackOrder$REVERSE_instance;
  function StackOrder$REVERSE_getInstance() {
    StackOrder_initFields();
    return StackOrder$REVERSE_instance;
  }
  var StackOrder$INSIDEOUT_instance;
  function StackOrder$INSIDEOUT_getInstance() {
    StackOrder_initFields();
    return StackOrder$INSIDEOUT_instance;
  }
  StackOrder.prototype.sort_mp22wn$ = function (stackParams) {
    switch (this.name) {
      case 'ASCENDING':
        return sortAscending(stackParams);
      case 'DESCENDING':
        return sortDescending(stackParams);
      case 'REVERSE':
        return sortReverse(stackParams);
      case 'INSIDEOUT':
        return sortInsideOut(stackParams);
      case 'NONE':
        return sortNone(stackParams);
      default:return Kotlin.noWhenBranchMatched();
    }
  };
  StackOrder.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'StackOrder',
    interfaces: [Enum]
  };
  function StackOrder$values() {
    return [StackOrder$NONE_getInstance(), StackOrder$ASCENDING_getInstance(), StackOrder$DESCENDING_getInstance(), StackOrder$REVERSE_getInstance(), StackOrder$INSIDEOUT_getInstance()];
  }
  StackOrder.values = StackOrder$values;
  function StackOrder$valueOf(name) {
    switch (name) {
      case 'NONE':
        return StackOrder$NONE_getInstance();
      case 'ASCENDING':
        return StackOrder$ASCENDING_getInstance();
      case 'DESCENDING':
        return StackOrder$DESCENDING_getInstance();
      case 'REVERSE':
        return StackOrder$REVERSE_getInstance();
      case 'INSIDEOUT':
        return StackOrder$INSIDEOUT_getInstance();
      default:throwISE('No enum constant io.data2viz.shape.stack.StackOrder.' + name);
    }
  }
  StackOrder.valueOf_61zpoe$ = StackOrder$valueOf;
  function sortInsideOut($receiver) {
    var ascendingIndexes = sortDescending($receiver);
    var topSum = {v: 0.0};
    var bottomSum = {v: 0.0};
    var top = ArrayList_init();
    var bottom = ArrayList_init();
    var tmp$;
    tmp$ = ascendingIndexes.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      var stackParam = $receiver.get_za3lpa$(element);
      if (topSum.v < bottomSum.v) {
        top.add_11rb$(stackParam.index);
        var tmp$_0;
        var sum = 0.0;
        tmp$_0 = stackParam.stackedValues.iterator();
        while (tmp$_0.hasNext()) {
          var element_0 = tmp$_0.next();
          sum += element_0.to;
        }
        topSum.v += sum;
      }
       else {
        bottom.add_11rb$(stackParam.index);
        var tmp$_1;
        var sum_0 = 0.0;
        tmp$_1 = stackParam.stackedValues.iterator();
        while (tmp$_1.hasNext()) {
          var element_1 = tmp$_1.next();
          sum_0 += element_1.to;
        }
        bottomSum.v += sum_0;
      }
    }
    return plus(reversed(bottom), top);
  }
  function sortAscending$lambda(it) {
    return it.sum;
  }
  function sortAscending($receiver) {
    var $receiver_0 = sortedWith(sumSeries($receiver), new Comparator$ObjectLiteral_0(compareBy$lambda_0(sortAscending$lambda)));
    var destination = ArrayList_init_0(collectionSizeOrDefault($receiver_0, 10));
    var tmp$;
    tmp$ = $receiver_0.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(item.index);
    }
    return destination;
  }
  function sortDescending$lambda(it) {
    return it.sum;
  }
  function sortDescending($receiver) {
    var $receiver_0 = sortedWith(sumSeries($receiver), new Comparator$ObjectLiteral_1(compareByDescending$lambda(sortDescending$lambda)));
    var destination = ArrayList_init_0(collectionSizeOrDefault($receiver_0, 10));
    var tmp$;
    tmp$ = $receiver_0.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(item.index);
    }
    return destination;
  }
  function sortNone($receiver) {
    var $receiver_0 = sumSeries($receiver);
    var destination = ArrayList_init_0(collectionSizeOrDefault($receiver_0, 10));
    var tmp$;
    tmp$ = $receiver_0.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(item.index);
    }
    return destination;
  }
  function sortReverse($receiver) {
    var $receiver_0 = sumSeries($receiver);
    var destination = ArrayList_init_0(collectionSizeOrDefault($receiver_0, 10));
    var tmp$;
    tmp$ = $receiver_0.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(item.index);
    }
    return reversed(destination);
  }
  function sumSeries($receiver) {
    var destination = ArrayList_init_0(collectionSizeOrDefault($receiver, 10));
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      var tmp$_0 = destination.add_11rb$;
      var tmp$_1 = item.index;
      var tmp$_2;
      var sum = 0.0;
      tmp$_2 = item.stackedValues.iterator();
      while (tmp$_2.hasNext()) {
        var element = tmp$_2.next();
        sum += element.to - element.from;
      }
      tmp$_0.call(destination, new SeriesSum(tmp$_1, sum));
    }
    return destination;
  }
  function SeriesSum(index, sum) {
    this.index = index;
    this.sum = sum;
  }
  SeriesSum.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'SeriesSum',
    interfaces: []
  };
  SeriesSum.prototype.component1 = function () {
    return this.index;
  };
  SeriesSum.prototype.component2 = function () {
    return this.sum;
  };
  SeriesSum.prototype.copy_5wr77w$ = function (index, sum) {
    return new SeriesSum(index === void 0 ? this.index : index, sum === void 0 ? this.sum : sum);
  };
  SeriesSum.prototype.toString = function () {
    return 'SeriesSum(index=' + Kotlin.toString(this.index) + (', sum=' + Kotlin.toString(this.sum)) + ')';
  };
  SeriesSum.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.index) | 0;
    result = result * 31 + Kotlin.hashCode(this.sum) | 0;
    return result;
  };
  SeriesSum.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.index, other.index) && Kotlin.equals(this.sum, other.sum)))));
  };
  function Circle() {
  }
  Circle.prototype.render_lh6mq8$ = function (path, size) {
    var x = size / pi;
    var r = Math_0.sqrt(x);
    path.moveTo_lu1900$(r, 0.0);
    path.arc_6p3vsx$(0.0, 0.0, r, 0.0, tau);
    return path;
  };
  Circle.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Circle',
    interfaces: [Symbol]
  };
  function Cross() {
  }
  Cross.prototype.render_lh6mq8$ = function (path, size) {
    var x = size / 5;
    var r = Math_0.sqrt(x) / 2;
    var r3 = 3 * r;
    path.moveTo_lu1900$(-r3, -r);
    path.lineTo_lu1900$(-r, -r);
    path.lineTo_lu1900$(-r, -r3);
    path.lineTo_lu1900$(r, -r3);
    path.lineTo_lu1900$(r, -r);
    path.lineTo_lu1900$(r3, -r);
    path.lineTo_lu1900$(r3, r);
    path.lineTo_lu1900$(r, r);
    path.lineTo_lu1900$(r, r3);
    path.lineTo_lu1900$(-r, r3);
    path.lineTo_lu1900$(-r, r);
    path.lineTo_lu1900$(-r3, r);
    path.closePath();
    return path;
  };
  Cross.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Cross',
    interfaces: [Symbol]
  };
  function Diamond() {
    var x = 1 / 3.0;
    this.tan30_0 = Math_0.sqrt(x);
    this.tan30_2_0 = this.tan30_0 * 2;
  }
  Diamond.prototype.render_lh6mq8$ = function (path, size) {
    var x = size / this.tan30_2_0;
    var y = Math_0.sqrt(x);
    var x_0 = y * this.tan30_0;
    path.moveTo_lu1900$(0.0, -y);
    path.lineTo_lu1900$(x_0, 0.0);
    path.lineTo_lu1900$(0.0, y);
    path.lineTo_lu1900$(-x_0, 0.0);
    path.closePath();
    return path;
  };
  Diamond.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Diamond',
    interfaces: [Symbol]
  };
  function Square() {
  }
  Square.prototype.render_lh6mq8$ = function (path, size) {
    var w = Math_0.sqrt(size);
    var x = -w / 2.0;
    path.rect_6y0v78$(x, x, w, w);
    return path;
  };
  Square.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Square',
    interfaces: [Symbol]
  };
  function Star() {
    this.ka_0 = 0.8908130915292852;
    var x = pi / 10;
    var tmp$ = Math_0.sin(x);
    var x_0 = 7 * pi / 10;
    this.kr_0 = tmp$ / Math_0.sin(x_0);
    var x_1 = tau / 10;
    this.kx_0 = Math_0.sin(x_1) * this.kr_0;
    var x_2 = tau / 10;
    this.ky_0 = -Math_0.cos(x_2) * this.kr_0;
  }
  Star.prototype.render_lh6mq8$ = function (path, size) {
    var x = size * this.ka_0;
    var r = Math_0.sqrt(x);
    var x_0 = this.kx_0 * r;
    var y = this.ky_0 * r;
    path.moveTo_lu1900$(0.0, -r);
    path.lineTo_lu1900$(x_0, y);
    for (var i = 1; i < 5; i++) {
      var a = tau * i / 5.0;
      var c = Math_0.cos(a);
      var s = Math_0.sin(a);
      path.lineTo_lu1900$(s * r, -c * r);
      path.lineTo_lu1900$(c * x_0 - s * y, s * x_0 + c * y);
    }
    path.closePath();
    return path;
  };
  Star.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Star',
    interfaces: [Symbol]
  };
  function Triangle() {
    this.sqrt3_0 = Math_0.sqrt(3.0);
  }
  Triangle.prototype.render_lh6mq8$ = function (path, size) {
    var x = size / (this.sqrt3_0 * 3);
    var y = -Math_0.sqrt(x);
    path.moveTo_lu1900$(0.0, y * 2);
    path.lineTo_lu1900$(-this.sqrt3_0 * y, -y);
    path.lineTo_lu1900$(this.sqrt3_0 * y, -y);
    path.closePath();
    return path;
  };
  Triangle.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Triangle',
    interfaces: [Symbol]
  };
  function Wye() {
    this.c_0 = -0.5;
    this.s_0 = Math_0.sqrt(3.0) / 2;
    this.k_0 = 1 / Math_0.sqrt(12.0);
    this.a_0 = (this.k_0 / 2 + 1) * 3;
  }
  Wye.prototype.render_lh6mq8$ = function (path, size) {
    var x = size / this.a_0;
    var r = Math_0.sqrt(x);
    var x0 = r / 2;
    var y0 = r * this.k_0;
    var x1 = x0;
    var y1 = r * this.k_0 + r;
    var x2 = -x1;
    var y2 = y1;
    path.moveTo_lu1900$(x0, y0);
    path.lineTo_lu1900$(x1, y1);
    path.lineTo_lu1900$(x2, y2);
    path.lineTo_lu1900$(this.c_0 * x0 - this.s_0 * y0, this.s_0 * x0 + this.c_0 * y0);
    path.lineTo_lu1900$(this.c_0 * x1 - this.s_0 * y1, this.s_0 * x1 + this.c_0 * y1);
    path.lineTo_lu1900$(this.c_0 * x2 - this.s_0 * y2, this.s_0 * x2 + this.c_0 * y2);
    path.lineTo_lu1900$(this.c_0 * x0 + this.s_0 * y0, this.c_0 * y0 - this.s_0 * x0);
    path.lineTo_lu1900$(this.c_0 * x1 + this.s_0 * y1, this.c_0 * y1 - this.s_0 * x1);
    path.lineTo_lu1900$(this.c_0 * x2 + this.s_0 * y2, this.c_0 * y2 - this.s_0 * x2);
    path.closePath();
    return path;
  };
  Wye.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Wye',
    interfaces: [Symbol]
  };
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$shape = package$data2viz.shape || (package$data2viz.shape = {});
  package$shape.arcBuilder_fcwrgw$ = arcBuilder;
  package$shape.ArcBuilder = ArcBuilder;
  package$shape.ArcParams = ArcParams;
  package$shape.areaBuilder_n73cab$ = areaBuilder;
  package$shape.AreaBuilder = AreaBuilder;
  package$shape.Curve = Curve;
  Object.defineProperty(package$shape, 'curves', {
    get: curves_getInstance
  });
  Object.defineProperty(package$shape, 'areas', {
    get: areas_getInstance
  });
  package$shape.line_ov83w$ = line;
  package$shape.const_lk9rat$ = const_0;
  package$shape.LineBuilder = LineBuilder;
  Object.defineProperty(package$shape, 'epsilon', {
    get: function () {
      return epsilon;
    }
  });
  Object.defineProperty(package$shape, 'pi', {
    get: function () {
      return pi;
    }
  });
  Object.defineProperty(package$shape, 'halfPi', {
    get: function () {
      return halfPi;
    }
  });
  Object.defineProperty(package$shape, 'tau', {
    get: function () {
      return tau;
    }
  });
  package$shape.acos_14dthe$ = acos;
  package$shape.asin_14dthe$ = asin;
  package$shape.pie_21027i$ = pie;
  package$shape.PieGenerator = PieGenerator;
  package$shape.Symbol = Symbol;
  Object.defineProperty(Symbols, 'Circle', {
    get: Symbols$Circle_getInstance
  });
  Object.defineProperty(Symbols, 'Cross', {
    get: Symbols$Cross_getInstance
  });
  Object.defineProperty(Symbols, 'Diamond', {
    get: Symbols$Diamond_getInstance
  });
  Object.defineProperty(Symbols, 'Square', {
    get: Symbols$Square_getInstance
  });
  Object.defineProperty(Symbols, 'Star', {
    get: Symbols$Star_getInstance
  });
  Object.defineProperty(Symbols, 'Triangle', {
    get: Symbols$Triangle_getInstance
  });
  Object.defineProperty(Symbols, 'Wye', {
    get: Symbols$Wye_getInstance
  });
  package$shape.Symbols = Symbols;
  package$shape.get_symbol_b4he08$ = get_symbol;
  package$shape.render_dv9pls$ = render;
  package$shape.SymbolGenerator = SymbolGenerator;
  var package$curve = package$shape.curve || (package$shape.curve = {});
  package$curve.Basis = Basis;
  package$curve.BasisClosed = BasisClosed;
  package$curve.BasisOpen = BasisOpen;
  package$curve.Bundle = Bundle;
  package$curve.Cardinal = Cardinal;
  package$curve.CardinalClosed = CardinalClosed;
  package$curve.CardinalOpen = CardinalOpen;
  package$curve.CatmullRom = CatmullRom;
  package$curve.CatmullRomClosed = CatmullRomClosed;
  package$curve.CatmullRomOpen = CatmullRomOpen;
  package$curve.Linear = Linear;
  package$curve.LinearClosed = LinearClosed;
  package$curve.AbstractMonotone = AbstractMonotone;
  package$curve.MonotoneX = MonotoneX;
  package$curve.MonotoneY = MonotoneY;
  package$curve.Natural = Natural;
  package$curve.AbstractRadial = AbstractRadial;
  package$curve.RadialLinear = RadialLinear;
  package$curve.Radial = Radial;
  package$curve.AbstractStep = AbstractStep;
  package$curve.Step = Step;
  package$curve.StepBefore = StepBefore;
  package$curve.StepAfter = StepAfter;
  var package$link = package$shape.link || (package$shape.link = {});
  package$link.linkBuilderH_cz84wk$ = linkBuilderH;
  package$link.linkBuilderV_cz84wk$ = linkBuilderV;
  package$link.LinkBuilder = LinkBuilder;
  var package$stack = package$shape.stack || (package$shape.stack = {});
  package$stack.StackSpace = StackSpace;
  package$stack.StackParam = StackParam;
  package$stack.stack_en0e7c$ = stack;
  package$stack.StackGenerator = StackGenerator;
  Object.defineProperty(StackOffset, 'NONE', {
    get: StackOffset$NONE_getInstance
  });
  Object.defineProperty(StackOffset, 'EXPAND', {
    get: StackOffset$EXPAND_getInstance
  });
  Object.defineProperty(StackOffset, 'DIVERGING', {
    get: StackOffset$DIVERGING_getInstance
  });
  Object.defineProperty(StackOffset, 'SILHOUETTE', {
    get: StackOffset$SILHOUETTE_getInstance
  });
  Object.defineProperty(StackOffset, 'WIGGLE', {
    get: StackOffset$WIGGLE_getInstance
  });
  package$stack.StackOffset = StackOffset;
  Object.defineProperty(StackOrder, 'NONE', {
    get: StackOrder$NONE_getInstance
  });
  Object.defineProperty(StackOrder, 'ASCENDING', {
    get: StackOrder$ASCENDING_getInstance
  });
  Object.defineProperty(StackOrder, 'DESCENDING', {
    get: StackOrder$DESCENDING_getInstance
  });
  Object.defineProperty(StackOrder, 'REVERSE', {
    get: StackOrder$REVERSE_getInstance
  });
  Object.defineProperty(StackOrder, 'INSIDEOUT', {
    get: StackOrder$INSIDEOUT_getInstance
  });
  package$stack.StackOrder = StackOrder;
  package$stack.sumSeries_5lc7tm$ = sumSeries;
  package$stack.SeriesSum = SeriesSum;
  var package$symbol = package$shape.symbol || (package$shape.symbol = {});
  package$symbol.Circle = Circle;
  package$symbol.Cross = Cross;
  package$symbol.Diamond = Diamond;
  package$symbol.Square = Square;
  package$symbol.Star = Star;
  package$symbol.Triangle = Triangle;
  package$symbol.Wye = Wye;
  ReflectContext.prototype.arc_6p3vsx$ = Path.prototype.arc_6p3vsx$;
  epsilon = 1.0E-12;
  pi = math.PI;
  halfPi = pi / 2;
  tau = 2 * pi;
  Kotlin.defineModule('d2v-shape-js', _);
  return _;
}));

//# sourceMappingURL=d2v-shape-js.js.map
