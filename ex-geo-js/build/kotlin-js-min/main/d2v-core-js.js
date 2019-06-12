(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'd2v-core-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'd2v-core-js'.");
    }
    root['d2v-core-js'] = factory(typeof this['d2v-core-js'] === 'undefined' ? {} : this['d2v-core-js'], kotlin);
  }
}(this, function (_, Kotlin) {
  'use strict';
  var Kind_INTERFACE = Kotlin.Kind.INTERFACE;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var StringBuilder_init = Kotlin.kotlin.text.StringBuilder_init;
  var math = Kotlin.kotlin.math;
  var toString = Kotlin.toString;
  var ensureNotNull = Kotlin.ensureNotNull;
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  var IllegalArgumentException_init = Kotlin.kotlin.IllegalArgumentException_init_pdl1vj$;
  var NotImplementedError_init = Kotlin.kotlin.NotImplementedError;
  var Math_0 = Math;
  var Kind_OBJECT = Kotlin.Kind.OBJECT;
  var numberToDouble = Kotlin.numberToDouble;
  var last = Kotlin.kotlin.collections.last_2p1efm$;
  var until = Kotlin.kotlin.ranges.until_dqglrj$;
  var compareBy = Kotlin.kotlin.comparisons.compareBy_bvgy4j$;
  var sortWith = Kotlin.kotlin.collections.sortWith_nqfjgj$;
  var get_lastIndex = Kotlin.kotlin.collections.get_lastIndex_55thoc$;
  var downTo = Kotlin.kotlin.ranges.downTo_dqglrj$;
  var collectionSizeOrDefault = Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$;
  var ArrayList_init_0 = Kotlin.kotlin.collections.ArrayList_init_ww73n8$;
  var defineInlineFunction = Kotlin.defineInlineFunction;
  var wrapFunction = Kotlin.wrapFunction;
  var coerceAtLeast = Kotlin.kotlin.ranges.coerceAtLeast_38ydlf$;
  var coerceAtMost = Kotlin.kotlin.ranges.coerceAtMost_38ydlf$;
  var coerceIn = Kotlin.kotlin.ranges.coerceIn_nig4hr$;
  var lazy = Kotlin.kotlin.lazy_klfg04$;
  var numberToInt = Kotlin.numberToInt;
  var IntRange = Kotlin.kotlin.ranges.IntRange;
  function Circle() {
  }
  Circle.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Circle',
    interfaces: []
  };
  function CircleGeom() {
    this.x_gvmh5z$_0 = 0.0;
    this.y_gvmh54$_0 = 0.0;
    this.radius_g5cryn$_0 = 0.0;
  }
  Object.defineProperty(CircleGeom.prototype, 'x', {
    get: function () {
      return this.x_gvmh5z$_0;
    },
    set: function (x) {
      this.x_gvmh5z$_0 = x;
    }
  });
  Object.defineProperty(CircleGeom.prototype, 'y', {
    get: function () {
      return this.y_gvmh54$_0;
    },
    set: function (y) {
      this.y_gvmh54$_0 = y;
    }
  });
  Object.defineProperty(CircleGeom.prototype, 'radius', {
    get: function () {
      return this.radius_g5cryn$_0;
    },
    set: function (radius) {
      this.radius_g5cryn$_0 = radius;
    }
  });
  CircleGeom.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'CircleGeom',
    interfaces: [Circle]
  };
  function Extent(x0, y0, x1, y1) {
    this.x0 = x0;
    this.y0 = y0;
    this.x1 = x1;
    this.y1 = y1;
  }
  Object.defineProperty(Extent.prototype, 'width', {
    get: function () {
      return this.x1 - this.x0;
    },
    set: function (value) {
      this.x0 = 0.0;
      this.x1 = value;
    }
  });
  Object.defineProperty(Extent.prototype, 'height', {
    get: function () {
      return this.y1 - this.y0;
    },
    set: function (value) {
      this.y0 = 0.0;
      this.y1 = value;
    }
  });
  Extent.prototype.copy = function () {
    return new Extent(this.x0, this.y0, this.x1, this.y1);
  };
  Extent.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Extent',
    interfaces: []
  };
  function Path() {
  }
  Path.prototype.arc_6p3vsx$ = function (centerX, centerY, radius, startAngle, endAngle, counterClockWise, callback$default) {
    if (counterClockWise === void 0)
      counterClockWise = false;
    callback$default ? callback$default(centerX, centerY, radius, startAngle, endAngle, counterClockWise) : this.arc_6p3vsx$$default(centerX, centerY, radius, startAngle, endAngle, counterClockWise);
  };
  Path.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Path',
    interfaces: []
  };
  function PathGeom() {
    this.commands = ArrayList_init();
  }
  PathGeom.prototype.clearPath = function () {
    this.commands.clear();
  };
  PathGeom.prototype.moveTo_lu1900$ = function (x, y) {
    var $receiver = this.commands;
    var element = new MoveTo(x, y);
    $receiver.add_11rb$(element);
  };
  PathGeom.prototype.lineTo_lu1900$ = function (x, y) {
    var $receiver = this.commands;
    var element = new LineTo(x, y);
    $receiver.add_11rb$(element);
  };
  PathGeom.prototype.closePath = function () {
    var $receiver = this.commands;
    var element = new ClosePath();
    $receiver.add_11rb$(element);
  };
  PathGeom.prototype.quadraticCurveTo_6y0v78$ = function (cpx, cpy, x, y) {
    var $receiver = this.commands;
    var element = new QuadraticCurveTo(cpx, cpy, x, y);
    $receiver.add_11rb$(element);
  };
  PathGeom.prototype.bezierCurveTo_15yvbs$ = function (cpx1, cpy1, cpx2, cpy2, x, y) {
    var $receiver = this.commands;
    var element = new BezierCurveTo(cpx1, cpy1, cpx2, cpy2, x, y);
    $receiver.add_11rb$(element);
  };
  PathGeom.prototype.arcTo_1lq62i$ = function (cpx, cpy, x, y, radius) {
    if (!(radius >= 0.0)) {
      var message = 'Negative radius:' + radius;
      throw IllegalArgumentException_init(message.toString());
    }
    var $receiver = this.commands;
    var element = new ArcTo(cpx, cpy, x, y, radius);
    $receiver.add_11rb$(element);
  };
  PathGeom.prototype.arc_6p3vsx$$default = function (centerX, centerY, radius, startAngle, endAngle, counterClockWise) {
    if (!(radius >= 0.0)) {
      var message = 'Negative radius:' + radius;
      throw IllegalArgumentException_init(message.toString());
    }
    var $receiver = this.commands;
    var element = new Arc(centerX, centerY, radius, startAngle, endAngle, counterClockWise);
    $receiver.add_11rb$(element);
  };
  PathGeom.prototype.rect_6y0v78$ = function (x, y, w, h) {
    var $receiver = this.commands;
    var element = new RectCmd(x, y, w, h);
    $receiver.add_11rb$(element);
  };
  PathGeom.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'PathGeom',
    interfaces: [Path]
  };
  function PathCommand() {
  }
  PathCommand.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'PathCommand',
    interfaces: []
  };
  function MoveTo(x, y) {
    this.x_ryrhev$_0 = x;
    this.y_ryrhe0$_0 = y;
  }
  Object.defineProperty(MoveTo.prototype, 'x', {
    get: function () {
      return this.x_ryrhev$_0;
    }
  });
  Object.defineProperty(MoveTo.prototype, 'y', {
    get: function () {
      return this.y_ryrhe0$_0;
    }
  });
  MoveTo.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'MoveTo',
    interfaces: [PathCommand]
  };
  MoveTo.prototype.component1 = function () {
    return this.x;
  };
  MoveTo.prototype.component2 = function () {
    return this.y;
  };
  MoveTo.prototype.copy_lu1900$ = function (x, y) {
    return new MoveTo(x === void 0 ? this.x : x, y === void 0 ? this.y : y);
  };
  MoveTo.prototype.toString = function () {
    return 'MoveTo(x=' + Kotlin.toString(this.x) + (', y=' + Kotlin.toString(this.y)) + ')';
  };
  MoveTo.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.x) | 0;
    result = result * 31 + Kotlin.hashCode(this.y) | 0;
    return result;
  };
  MoveTo.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.x, other.x) && Kotlin.equals(this.y, other.y)))));
  };
  function LineTo(x, y) {
    this.x_4b4cve$_0 = x;
    this.y_4b4cuj$_0 = y;
  }
  Object.defineProperty(LineTo.prototype, 'x', {
    get: function () {
      return this.x_4b4cve$_0;
    }
  });
  Object.defineProperty(LineTo.prototype, 'y', {
    get: function () {
      return this.y_4b4cuj$_0;
    }
  });
  LineTo.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'LineTo',
    interfaces: [PathCommand]
  };
  LineTo.prototype.component1 = function () {
    return this.x;
  };
  LineTo.prototype.component2 = function () {
    return this.y;
  };
  LineTo.prototype.copy_lu1900$ = function (x, y) {
    return new LineTo(x === void 0 ? this.x : x, y === void 0 ? this.y : y);
  };
  LineTo.prototype.toString = function () {
    return 'LineTo(x=' + Kotlin.toString(this.x) + (', y=' + Kotlin.toString(this.y)) + ')';
  };
  LineTo.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.x) | 0;
    result = result * 31 + Kotlin.hashCode(this.y) | 0;
    return result;
  };
  LineTo.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.x, other.x) && Kotlin.equals(this.y, other.y)))));
  };
  function RectCmd(x, y, w, h) {
    this.x_3m272d$_0 = x;
    this.y_3m2738$_0 = y;
    this.w = w;
    this.h = h;
  }
  Object.defineProperty(RectCmd.prototype, 'x', {
    get: function () {
      return this.x_3m272d$_0;
    }
  });
  Object.defineProperty(RectCmd.prototype, 'y', {
    get: function () {
      return this.y_3m2738$_0;
    }
  });
  RectCmd.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'RectCmd',
    interfaces: [PathCommand]
  };
  RectCmd.prototype.component1 = function () {
    return this.x;
  };
  RectCmd.prototype.component2 = function () {
    return this.y;
  };
  RectCmd.prototype.component3 = function () {
    return this.w;
  };
  RectCmd.prototype.component4 = function () {
    return this.h;
  };
  RectCmd.prototype.copy_6y0v78$ = function (x, y, w, h) {
    return new RectCmd(x === void 0 ? this.x : x, y === void 0 ? this.y : y, w === void 0 ? this.w : w, h === void 0 ? this.h : h);
  };
  RectCmd.prototype.toString = function () {
    return 'RectCmd(x=' + Kotlin.toString(this.x) + (', y=' + Kotlin.toString(this.y)) + (', w=' + Kotlin.toString(this.w)) + (', h=' + Kotlin.toString(this.h)) + ')';
  };
  RectCmd.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.x) | 0;
    result = result * 31 + Kotlin.hashCode(this.y) | 0;
    result = result * 31 + Kotlin.hashCode(this.w) | 0;
    result = result * 31 + Kotlin.hashCode(this.h) | 0;
    return result;
  };
  RectCmd.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.x, other.x) && Kotlin.equals(this.y, other.y) && Kotlin.equals(this.w, other.w) && Kotlin.equals(this.h, other.h)))));
  };
  function QuadraticCurveTo(cpx, cpy, x, y) {
    this.cpx = cpx;
    this.cpy = cpy;
    this.x_2emkb1$_0 = x;
    this.y_2emka6$_0 = y;
  }
  Object.defineProperty(QuadraticCurveTo.prototype, 'x', {
    get: function () {
      return this.x_2emkb1$_0;
    }
  });
  Object.defineProperty(QuadraticCurveTo.prototype, 'y', {
    get: function () {
      return this.y_2emka6$_0;
    }
  });
  QuadraticCurveTo.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'QuadraticCurveTo',
    interfaces: [PathCommand]
  };
  QuadraticCurveTo.prototype.component1 = function () {
    return this.cpx;
  };
  QuadraticCurveTo.prototype.component2 = function () {
    return this.cpy;
  };
  QuadraticCurveTo.prototype.component3 = function () {
    return this.x;
  };
  QuadraticCurveTo.prototype.component4 = function () {
    return this.y;
  };
  QuadraticCurveTo.prototype.copy_6y0v78$ = function (cpx, cpy, x, y) {
    return new QuadraticCurveTo(cpx === void 0 ? this.cpx : cpx, cpy === void 0 ? this.cpy : cpy, x === void 0 ? this.x : x, y === void 0 ? this.y : y);
  };
  QuadraticCurveTo.prototype.toString = function () {
    return 'QuadraticCurveTo(cpx=' + Kotlin.toString(this.cpx) + (', cpy=' + Kotlin.toString(this.cpy)) + (', x=' + Kotlin.toString(this.x)) + (', y=' + Kotlin.toString(this.y)) + ')';
  };
  QuadraticCurveTo.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.cpx) | 0;
    result = result * 31 + Kotlin.hashCode(this.cpy) | 0;
    result = result * 31 + Kotlin.hashCode(this.x) | 0;
    result = result * 31 + Kotlin.hashCode(this.y) | 0;
    return result;
  };
  QuadraticCurveTo.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.cpx, other.cpx) && Kotlin.equals(this.cpy, other.cpy) && Kotlin.equals(this.x, other.x) && Kotlin.equals(this.y, other.y)))));
  };
  function BezierCurveTo(cpx1, cpy1, cpx2, cpy2, x, y) {
    this.cpx1 = cpx1;
    this.cpy1 = cpy1;
    this.cpx2 = cpx2;
    this.cpy2 = cpy2;
    this.x_qnsggg$_0 = x;
    this.y_qnsgfl$_0 = y;
  }
  Object.defineProperty(BezierCurveTo.prototype, 'x', {
    get: function () {
      return this.x_qnsggg$_0;
    }
  });
  Object.defineProperty(BezierCurveTo.prototype, 'y', {
    get: function () {
      return this.y_qnsgfl$_0;
    }
  });
  BezierCurveTo.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'BezierCurveTo',
    interfaces: [PathCommand]
  };
  BezierCurveTo.prototype.component1 = function () {
    return this.cpx1;
  };
  BezierCurveTo.prototype.component2 = function () {
    return this.cpy1;
  };
  BezierCurveTo.prototype.component3 = function () {
    return this.cpx2;
  };
  BezierCurveTo.prototype.component4 = function () {
    return this.cpy2;
  };
  BezierCurveTo.prototype.component5 = function () {
    return this.x;
  };
  BezierCurveTo.prototype.component6 = function () {
    return this.y;
  };
  BezierCurveTo.prototype.copy_15yvbs$ = function (cpx1, cpy1, cpx2, cpy2, x, y) {
    return new BezierCurveTo(cpx1 === void 0 ? this.cpx1 : cpx1, cpy1 === void 0 ? this.cpy1 : cpy1, cpx2 === void 0 ? this.cpx2 : cpx2, cpy2 === void 0 ? this.cpy2 : cpy2, x === void 0 ? this.x : x, y === void 0 ? this.y : y);
  };
  BezierCurveTo.prototype.toString = function () {
    return 'BezierCurveTo(cpx1=' + Kotlin.toString(this.cpx1) + (', cpy1=' + Kotlin.toString(this.cpy1)) + (', cpx2=' + Kotlin.toString(this.cpx2)) + (', cpy2=' + Kotlin.toString(this.cpy2)) + (', x=' + Kotlin.toString(this.x)) + (', y=' + Kotlin.toString(this.y)) + ')';
  };
  BezierCurveTo.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.cpx1) | 0;
    result = result * 31 + Kotlin.hashCode(this.cpy1) | 0;
    result = result * 31 + Kotlin.hashCode(this.cpx2) | 0;
    result = result * 31 + Kotlin.hashCode(this.cpy2) | 0;
    result = result * 31 + Kotlin.hashCode(this.x) | 0;
    result = result * 31 + Kotlin.hashCode(this.y) | 0;
    return result;
  };
  BezierCurveTo.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.cpx1, other.cpx1) && Kotlin.equals(this.cpy1, other.cpy1) && Kotlin.equals(this.cpx2, other.cpx2) && Kotlin.equals(this.cpy2, other.cpy2) && Kotlin.equals(this.x, other.x) && Kotlin.equals(this.y, other.y)))));
  };
  function Arc(centerX, centerY, radius, startAngle, endAngle, counterClockWise) {
    this.centerX = centerX;
    this.centerY = centerY;
    this.radius = radius;
    this.startAngle = startAngle;
    this.endAngle = endAngle;
    this.counterClockWise = counterClockWise;
  }
  Object.defineProperty(Arc.prototype, 'x', {
    get: function () {
      throw new NotImplementedError_init('An operation is not implemented: ' + 'not implemented');
    }
  });
  Object.defineProperty(Arc.prototype, 'y', {
    get: function () {
      throw new NotImplementedError_init('An operation is not implemented: ' + 'not implemented');
    }
  });
  Arc.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Arc',
    interfaces: [PathCommand]
  };
  Arc.prototype.component1 = function () {
    return this.centerX;
  };
  Arc.prototype.component2 = function () {
    return this.centerY;
  };
  Arc.prototype.component3 = function () {
    return this.radius;
  };
  Arc.prototype.component4 = function () {
    return this.startAngle;
  };
  Arc.prototype.component5 = function () {
    return this.endAngle;
  };
  Arc.prototype.component6 = function () {
    return this.counterClockWise;
  };
  Arc.prototype.copy_6p3vsx$ = function (centerX, centerY, radius, startAngle, endAngle, counterClockWise) {
    return new Arc(centerX === void 0 ? this.centerX : centerX, centerY === void 0 ? this.centerY : centerY, radius === void 0 ? this.radius : radius, startAngle === void 0 ? this.startAngle : startAngle, endAngle === void 0 ? this.endAngle : endAngle, counterClockWise === void 0 ? this.counterClockWise : counterClockWise);
  };
  Arc.prototype.toString = function () {
    return 'Arc(centerX=' + Kotlin.toString(this.centerX) + (', centerY=' + Kotlin.toString(this.centerY)) + (', radius=' + Kotlin.toString(this.radius)) + (', startAngle=' + Kotlin.toString(this.startAngle)) + (', endAngle=' + Kotlin.toString(this.endAngle)) + (', counterClockWise=' + Kotlin.toString(this.counterClockWise)) + ')';
  };
  Arc.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.centerX) | 0;
    result = result * 31 + Kotlin.hashCode(this.centerY) | 0;
    result = result * 31 + Kotlin.hashCode(this.radius) | 0;
    result = result * 31 + Kotlin.hashCode(this.startAngle) | 0;
    result = result * 31 + Kotlin.hashCode(this.endAngle) | 0;
    result = result * 31 + Kotlin.hashCode(this.counterClockWise) | 0;
    return result;
  };
  Arc.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.centerX, other.centerX) && Kotlin.equals(this.centerY, other.centerY) && Kotlin.equals(this.radius, other.radius) && Kotlin.equals(this.startAngle, other.startAngle) && Kotlin.equals(this.endAngle, other.endAngle) && Kotlin.equals(this.counterClockWise, other.counterClockWise)))));
  };
  function ArcTo(fromX, fromY, x, y, radius) {
    this.fromX = fromX;
    this.fromY = fromY;
    this.x_ou4ir2$_0 = x;
    this.y_ou4irx$_0 = y;
    this.radius = radius;
  }
  Object.defineProperty(ArcTo.prototype, 'x', {
    get: function () {
      return this.x_ou4ir2$_0;
    }
  });
  Object.defineProperty(ArcTo.prototype, 'y', {
    get: function () {
      return this.y_ou4irx$_0;
    }
  });
  ArcTo.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ArcTo',
    interfaces: [PathCommand]
  };
  ArcTo.prototype.component1 = function () {
    return this.fromX;
  };
  ArcTo.prototype.component2 = function () {
    return this.fromY;
  };
  ArcTo.prototype.component3 = function () {
    return this.x;
  };
  ArcTo.prototype.component4 = function () {
    return this.y;
  };
  ArcTo.prototype.component5 = function () {
    return this.radius;
  };
  ArcTo.prototype.copy_1lq62i$ = function (fromX, fromY, x, y, radius) {
    return new ArcTo(fromX === void 0 ? this.fromX : fromX, fromY === void 0 ? this.fromY : fromY, x === void 0 ? this.x : x, y === void 0 ? this.y : y, radius === void 0 ? this.radius : radius);
  };
  ArcTo.prototype.toString = function () {
    return 'ArcTo(fromX=' + Kotlin.toString(this.fromX) + (', fromY=' + Kotlin.toString(this.fromY)) + (', x=' + Kotlin.toString(this.x)) + (', y=' + Kotlin.toString(this.y)) + (', radius=' + Kotlin.toString(this.radius)) + ')';
  };
  ArcTo.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.fromX) | 0;
    result = result * 31 + Kotlin.hashCode(this.fromY) | 0;
    result = result * 31 + Kotlin.hashCode(this.x) | 0;
    result = result * 31 + Kotlin.hashCode(this.y) | 0;
    result = result * 31 + Kotlin.hashCode(this.radius) | 0;
    return result;
  };
  ArcTo.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.fromX, other.fromX) && Kotlin.equals(this.fromY, other.fromY) && Kotlin.equals(this.x, other.x) && Kotlin.equals(this.y, other.y) && Kotlin.equals(this.radius, other.radius)))));
  };
  function ClosePath() {
  }
  Object.defineProperty(ClosePath.prototype, 'x', {
    get: function () {
      return 0.0;
    }
  });
  Object.defineProperty(ClosePath.prototype, 'y', {
    get: function () {
      return 0.0;
    }
  });
  ClosePath.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ClosePath',
    interfaces: [PathCommand]
  };
  function get_svgPath($receiver) {
    var tempX0 = {v: 0.0};
    var tempY0 = {v: 0.0};
    var tempX1 = {v: null};
    var tempY1 = {v: null};
    var sb = StringBuilder_init();
    var tmp$;
    tmp$ = $receiver.commands.iterator();
    loop_label: while (tmp$.hasNext()) {
      var element = tmp$.next();
      action$break: do {
        var tmp$_0, tmp$_1;
        if (Kotlin.isType(element, MoveTo)) {
          tempX0.v = element.x;
          tempY0.v = element.y;
          tempX1.v = element.x;
          tempY1.v = element.y;
          sb.append_gw00v9$('M' + element.x + ',' + element.y);
        }
         else if (Kotlin.isType(element, LineTo)) {
          tempX1.v = element.x;
          tempY1.v = element.y;
          sb.append_gw00v9$('L' + element.x + ',' + element.y);
        }
         else if (Kotlin.isType(element, ClosePath)) {
          if (tempX1.v != null) {
            tempX1.v = tempX0.v;
            tempY1.v = tempY0.v;
            sb.append_gw00v9$('Z');
          }
        }
         else if (Kotlin.isType(element, QuadraticCurveTo)) {
          tempX1.v = element.x;
          tempY1.v = element.y;
          sb.append_gw00v9$('Q' + element.cpx + ',' + element.cpy + ',' + element.x + ',' + element.y);
        }
         else if (Kotlin.isType(element, RectCmd)) {
          tempX0.v = element.x;
          tempX1.v = element.x;
          tempY0.v = element.y;
          tempY1.v = element.y;
          sb.append_gw00v9$('M' + element.x + ',' + element.y + 'h' + element.w + 'v' + element.h + 'h' + -element.w + 'Z');
        }
         else if (Kotlin.isType(element, BezierCurveTo)) {
          tempX1.v = element.x;
          tempY1.v = element.y;
          sb.append_gw00v9$('C' + element.cpx1 + ',' + element.cpy1 + ',' + element.cpx2 + ',' + element.cpy2 + ',' + element.x + ',' + element.y);
        }
         else if (Kotlin.isType(element, ArcTo)) {
          var X0 = (tmp$_0 = tempX1.v) != null ? tmp$_0 : 0.0;
          var Y0 = (tmp$_1 = tempY1.v) != null ? tmp$_1 : 0.0;
          var x21 = element.x - element.fromX;
          var y21 = element.y - element.fromY;
          var x01 = X0 - element.fromX;
          var y01 = Y0 - element.fromY;
          var l01_2 = x01 * x01 + y01 * y01;
          if (tempX1.v == null) {
            tempX1.v = element.fromX;
            tempY1.v = element.fromY;
            sb.append_gw00v9$('M' + element.fromX + ',' + element.fromY);
          }
           else if (l01_2 > EPSILON) {
            var x = y01 * x21 - y21 * x01;
            if (Math_0.abs(x) <= EPSILON || element.radius === 0.0) {
              tempX1.v = element.fromX;
              tempY1.v = element.fromY;
              sb.append_gw00v9$('L' + element.fromX + ',' + element.fromY);
            }
             else {
              var x20 = element.x - X0;
              var y20 = element.y - Y0;
              var l21_2 = x21 * x21 + y21 * y21;
              var l20_2 = x20 * x20 + y20 * y20;
              var l21 = Math_0.sqrt(l21_2);
              var l01 = Math_0.sqrt(l01_2);
              var tmp$_2 = element.radius;
              var tmp$_3 = math.PI;
              var x_0 = (l21_2 + l01_2 - l20_2) / (2 * l21 * l01);
              var x_1 = (tmp$_3 - Math_0.acos(x_0)) / 2;
              var l = tmp$_2 * Math_0.tan(x_1);
              var t01 = l / l01;
              var t21 = l / l21;
              var x_2 = t01 - 1;
              if (Math_0.abs(x_2) > EPSILON) {
                sb.append_gw00v9$('L' + (element.fromX + t01 * x01) + ',' + (element.fromY + t01 * y01));
              }
              tempX1.v = element.fromX + t21 * x21;
              tempY1.v = element.fromY + t21 * y21;
              var yes = y01 * x20 > x01 * y20 ? 1 : 0;
              sb.append_gw00v9$('A' + element.radius + ',' + element.radius + ',0,0,' + yes + ',' + toString(tempX1.v) + ',' + toString(tempY1.v));
            }
          }
        }
         else if (Kotlin.isType(element, Arc)) {
          var tmp$_4 = element.radius;
          var x_3 = element.startAngle;
          var dx = tmp$_4 * Math_0.cos(x_3);
          var tmp$_5 = element.radius;
          var x_4 = element.startAngle;
          var dy = tmp$_5 * Math_0.sin(x_4);
          var x0 = element.centerX + dx;
          var y0 = element.centerY + dy;
          var cw = element.counterClockWise ? 0 : 1;
          var da = element.counterClockWise ? element.startAngle - element.endAngle : element.endAngle - element.startAngle;
          var receiver = tempX1.v;
          if (receiver == null) {
            sb.append_gw00v9$('M' + x0 + ',' + y0);
          }
           else {
            var x_5 = receiver - x0;
            var tmp$_6 = Math_0.abs(x_5) > EPSILON;
            if (!tmp$_6) {
              var x_6 = ensureNotNull(tempY1.v) - y0;
              tmp$_6 = Math_0.abs(x_6) > EPSILON;
            }
            if (tmp$_6) {
              sb.append_gw00v9$('L' + x0 + ',' + y0);
            }
          }
          if (element.radius < EPSILON)
            break action$break;
          if (da < 0)
            da = da % TAU + TAU;
          if (da > TAU_EPSILON) {
            tempX1.v = x0;
            tempY1.v = y0;
            sb.append_gw00v9$('A' + element.radius + ',' + element.radius + ',0,1,' + cw + ',' + (element.centerX - dx) + ',' + (element.centerY - dy) + 'A' + element.radius + ',' + element.radius + ',0,1,' + cw + ',' + x0 + ',' + y0);
          }
           else if (da > EPSILON) {
            var tmp$_7 = element.centerX;
            var tmp$_8 = element.radius;
            var x_7 = element.endAngle;
            tempX1.v = tmp$_7 + tmp$_8 * Math_0.cos(x_7);
            var tmp$_9 = element.centerY;
            var tmp$_10 = element.radius;
            var x_8 = element.endAngle;
            tempY1.v = tmp$_9 + tmp$_10 * Math_0.sin(x_8);
            sb.append_gw00v9$('A' + element.radius + ',' + element.radius + ',0,' + (da >= math.PI ? 1 : 0) + ',' + cw + ',' + toString(tempX1.v) + ',' + toString(tempY1.v));
          }
        }
      }
       while (false);
    }
    return sb.toString();
  }
  function point(x, y) {
    return new Point(x, y);
  }
  function point_0(x, y) {
    return new Point(x, y);
  }
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
    kind: Kind_OBJECT,
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
  Point.prototype.plus_9jrimm$ = function (vector) {
    return new Point(this.x + vector.vx, this.y + vector.vy);
  };
  Point.prototype.plus_mowjvf$ = function (other) {
    return new Point(this.x + other.x, this.y + other.y);
  };
  Point.prototype.minus_mowjvf$ = function (other) {
    return new Point(this.x - other.x, this.y - other.y);
  };
  Point.prototype.div_3p81yu$ = function (value) {
    return new Point(this.x / numberToDouble(value), this.y / numberToDouble(value));
  };
  Point.prototype.times_3p81yu$ = function (value) {
    return new Point(this.x * numberToDouble(value), this.y * numberToDouble(value));
  };
  Point.prototype.unaryMinus = function () {
    return new Point(-this.x, -this.y);
  };
  Point.$metadata$ = {
    kind: Kind_CLASS,
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
  function Polygon(points) {
    this.points = points;
  }
  Polygon.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Polygon',
    interfaces: []
  };
  Polygon.prototype.component1 = function () {
    return this.points;
  };
  Polygon.prototype.copy_ccv9pi$ = function (points) {
    return new Polygon(points === void 0 ? this.points : points);
  };
  Polygon.prototype.toString = function () {
    return 'Polygon(points=' + Kotlin.toString(this.points) + ')';
  };
  Polygon.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.points) | 0;
    return result;
  };
  Polygon.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && Kotlin.equals(this.points, other.points))));
  };
  function get_area($receiver) {
    var i = -1;
    var size = $receiver.points.size;
    var b = last($receiver.points);
    var area = 0.0;
    while ((i = i + 1 | 0, i) < size) {
      var a = b;
      b = $receiver.points.get_za3lpa$(i);
      area += a.y * b.x - a.x * b.y;
    }
    return area / 2;
  }
  function get_centroid($receiver) {
    var i = -1;
    var size = $receiver.points.size;
    var x = 0.0;
    var y = 0.0;
    var b = last($receiver.points);
    var k = 0.0;
    while ((i = i + 1 | 0, i) < size) {
      var a = b;
      b = $receiver.points.get_za3lpa$(i);
      var c = a.x * b.y - b.x * a.y;
      k += c;
      x += (a.x + b.x) * c;
      y += (a.y + b.y) * c;
    }
    k *= 3.0;
    return new Point(x / k, y / k);
  }
  function get_length($receiver) {
    var i = -1;
    var size = $receiver.points.size;
    var b = last($receiver.points);
    var xb = b.x;
    var yb = b.y;
    var perimeter = 0.0;
    while ((i = i + 1 | 0, i) < size) {
      var xa = xb;
      var ya = yb;
      b = $receiver.points.get_za3lpa$(i);
      xb = b.x;
      yb = b.y;
      xa -= xb;
      ya -= yb;
      var x = xa * xa + ya * ya;
      perimeter += Math_0.sqrt(x);
    }
    return perimeter;
  }
  function contains($receiver, point) {
    var size = $receiver.points.size;
    var p = {v: last($receiver.points)};
    var x = point.x;
    var y = point.y;
    var x0 = {v: p.v.x};
    var y0 = {v: p.v.y};
    var inside = {v: false};
    var tmp$;
    tmp$ = until(0, size).iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      p.v = $receiver.points.get_za3lpa$(element);
      var x1 = p.v.x;
      var y1 = p.v.y;
      if (y1 > y !== y0.v > y && x < (x0.v - x1) * (y - y1) / (y0.v - y1) + x1)
        inside.v = !inside.v;
      x0.v = x1;
      y0.v = y1;
    }
    return inside.v;
  }
  function polygonHull$PointIndex(point, index) {
    this.point = point;
    this.index = index;
  }
  polygonHull$PointIndex.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'PointIndex',
    interfaces: []
  };
  polygonHull$PointIndex.prototype.component1 = function () {
    return this.point;
  };
  polygonHull$PointIndex.prototype.component2 = function () {
    return this.index;
  };
  polygonHull$PointIndex.prototype.copy_o4h9ft$ = function (point, index) {
    return new polygonHull$PointIndex(point === void 0 ? this.point : point, index === void 0 ? this.index : index);
  };
  polygonHull$PointIndex.prototype.toString = function () {
    return 'PointIndex(point=' + Kotlin.toString(this.point) + (', index=' + Kotlin.toString(this.index)) + ')';
  };
  polygonHull$PointIndex.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.point) | 0;
    result = result * 31 + Kotlin.hashCode(this.index) | 0;
    return result;
  };
  polygonHull$PointIndex.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.point, other.point) && Kotlin.equals(this.index, other.index)))));
  };
  function polygonHull$lambda(it) {
    return it.point.x;
  }
  function polygonHull$lambda_0(it) {
    return it.point.y;
  }
  function polygonHull(points) {
    var size = points.size;
    if (!(size > 2)) {
      var message = 'A polygon must have at least 3 points';
      throw IllegalArgumentException_init(message.toString());
    }
    var sortedPoints = ArrayList_init();
    var flippedPoints = ArrayList_init();
    var tmp$;
    tmp$ = until(0, size).iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      sortedPoints.add_11rb$(new polygonHull$PointIndex(points.get_za3lpa$(element), element));
    }
    sortWith(sortedPoints, compareBy([polygonHull$lambda, polygonHull$lambda_0]));
    var tmp$_0;
    tmp$_0 = until(0, size).iterator();
    while (tmp$_0.hasNext()) {
      var element_0 = tmp$_0.next();
      flippedPoints.add_11rb$(new Point(sortedPoints.get_za3lpa$(element_0).point.x, -sortedPoints.get_za3lpa$(element_0).point.y));
    }
    var destination = ArrayList_init_0(collectionSizeOrDefault(sortedPoints, 10));
    var tmp$_1;
    tmp$_1 = sortedPoints.iterator();
    while (tmp$_1.hasNext()) {
      var item = tmp$_1.next();
      destination.add_11rb$(item.point);
    }
    var upperIndexes = computeUpperHullIndexes(destination);
    var lowerIndexes = computeUpperHullIndexes(flippedPoints);
    var skipLeft = lowerIndexes.get_za3lpa$(0) === upperIndexes.get_za3lpa$(0);
    var skipRight = lowerIndexes.get_za3lpa$(lowerIndexes.size - 1 | 0) === upperIndexes.get_za3lpa$(upperIndexes.size - 1 | 0);
    var hull = ArrayList_init();
    var tmp$_2;
    tmp$_2 = downTo(get_lastIndex(upperIndexes), 0).iterator();
    while (tmp$_2.hasNext()) {
      var element_1 = tmp$_2.next();
      hull.add_11rb$(points.get_za3lpa$(sortedPoints.get_za3lpa$(upperIndexes.get_za3lpa$(element_1)).index));
    }
    var start = skipLeft ? 1 : 0;
    var end = skipRight ? 1 : 0;
    var tmp$_3;
    tmp$_3 = until(start, lowerIndexes.size - end | 0).iterator();
    while (tmp$_3.hasNext()) {
      var element_2 = tmp$_3.next();
      hull.add_11rb$(points.get_za3lpa$(sortedPoints.get_za3lpa$(lowerIndexes.get_za3lpa$(element_2)).index));
    }
    return new Polygon(hull);
  }
  function computeUpperHullIndexes(points) {
    var size = points.size;
    var list = ArrayList_init_0(size);
    for (var index = 0; index < size; index++) {
      list.add_11rb$(0);
    }
    var indexes = list;
    indexes.set_wxm5ur$(1, 1);
    var size_0 = {v: 2};
    var tmp$;
    tmp$ = until(2, points.size).iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      while (size_0.v > 1 && cross(points.get_za3lpa$(indexes.get_za3lpa$(size_0.v - 2 | 0)), points.get_za3lpa$(indexes.get_za3lpa$(size_0.v - 1 | 0)), points.get_za3lpa$(element)) <= 0) {
        size_0.v = size_0.v - 1 | 0;
      }
      if (size_0.v < get_lastIndex(indexes))
        indexes.set_wxm5ur$(size_0.v, element);
      else
        indexes.add_11rb$(element);
      size_0.v = size_0.v + 1 | 0;
    }
    return indexes.subList_vux9f0$(0, size_0.v);
  }
  function cross(a, b, c) {
    return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
  }
  function Rect() {
  }
  Object.defineProperty(Rect.prototype, 'top', {
    get: function () {
      return this.y;
    }
  });
  Object.defineProperty(Rect.prototype, 'bottom', {
    get: function () {
      return this.y + this.height;
    }
  });
  Object.defineProperty(Rect.prototype, 'left', {
    get: function () {
      return this.x;
    }
  });
  Object.defineProperty(Rect.prototype, 'right', {
    get: function () {
      return this.x + this.width;
    }
  });
  Object.defineProperty(Rect.prototype, 'topLeft', {
    get: function () {
      return new Point(this.x, this.y);
    }
  });
  Object.defineProperty(Rect.prototype, 'topRight', {
    get: function () {
      return new Point(this.x + this.width, this.y);
    }
  });
  Object.defineProperty(Rect.prototype, 'bottomLeft', {
    get: function () {
      return new Point(this.x, this.y + this.height);
    }
  });
  Object.defineProperty(Rect.prototype, 'bottomRight', {
    get: function () {
      return new Point(this.x + this.width, this.y + this.height);
    }
  });
  Object.defineProperty(Rect.prototype, 'center', {
    get: function () {
      return new Point(this.x + 0.5 * this.width, this.y + 0.5 * this.height);
    }
  });
  Rect.prototype.contains_mowjvf$ = function (point) {
    var x = point.x;
    var y = point.y;
    return x >= this.x && y >= this.y && x <= this.x + this.width && y <= this.y + this.height;
  };
  Rect.prototype.contains_wt5aq9$ = function (rect) {
    var x = rect.x;
    var y = rect.y;
    return x >= this.x && y >= this.y && x + rect.width <= this.x + this.width && y + rect.height <= this.y + this.height;
  };
  Rect.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Rect',
    interfaces: [HasSize]
  };
  function RectGeom(x, y, width, height) {
    if (x === void 0)
      x = 0.0;
    if (y === void 0)
      y = 0.0;
    if (width === void 0)
      width = 0.0;
    if (height === void 0)
      height = 0.0;
    this.x_p20m6d$_0 = x;
    this.y_p20m78$_0 = y;
    this.width_wkeybb$_0 = width;
    this.height_690wnk$_0 = height;
  }
  Object.defineProperty(RectGeom.prototype, 'x', {
    get: function () {
      return this.x_p20m6d$_0;
    },
    set: function (x) {
      this.x_p20m6d$_0 = x;
    }
  });
  Object.defineProperty(RectGeom.prototype, 'y', {
    get: function () {
      return this.y_p20m78$_0;
    },
    set: function (y) {
      this.y_p20m78$_0 = y;
    }
  });
  Object.defineProperty(RectGeom.prototype, 'width', {
    get: function () {
      return this.width_wkeybb$_0;
    },
    set: function (width) {
      this.width_wkeybb$_0 = width;
    }
  });
  Object.defineProperty(RectGeom.prototype, 'height', {
    get: function () {
      return this.height_690wnk$_0;
    },
    set: function (height) {
      this.height_690wnk$_0 = height;
    }
  });
  RectGeom.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'RectGeom',
    interfaces: [Rect]
  };
  function RectGeom_init(point, size, $this) {
    $this = $this || Object.create(RectGeom.prototype);
    RectGeom.call($this, point.x, point.y, size.width, size.height);
    return $this;
  }
  function RectGeom_init_0(from, to, $this) {
    $this = $this || Object.create(RectGeom.prototype);
    var a = from.x;
    var b = to.x;
    var tmp$ = Math_0.min(a, b);
    var a_0 = from.y;
    var b_0 = to.y;
    var tmp$_0 = Math_0.min(a_0, b_0);
    var $receiver = to.x - from.x;
    var tmp$_1 = Math_0.abs($receiver);
    var $receiver_0 = to.y - from.y;
    RectGeom.call($this, tmp$, tmp$_0, tmp$_1, Math_0.abs($receiver_0));
    return $this;
  }
  RectGeom.prototype.component1 = function () {
    return this.x;
  };
  RectGeom.prototype.component2 = function () {
    return this.y;
  };
  RectGeom.prototype.component3 = function () {
    return this.width;
  };
  RectGeom.prototype.component4 = function () {
    return this.height;
  };
  RectGeom.prototype.copy_6y0v78$ = function (x, y, width, height) {
    return new RectGeom(x === void 0 ? this.x : x, y === void 0 ? this.y : y, width === void 0 ? this.width : width, height === void 0 ? this.height : height);
  };
  RectGeom.prototype.toString = function () {
    return 'RectGeom(x=' + Kotlin.toString(this.x) + (', y=' + Kotlin.toString(this.y)) + (', width=' + Kotlin.toString(this.width)) + (', height=' + Kotlin.toString(this.height)) + ')';
  };
  RectGeom.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.x) | 0;
    result = result * 31 + Kotlin.hashCode(this.y) | 0;
    result = result * 31 + Kotlin.hashCode(this.width) | 0;
    result = result * 31 + Kotlin.hashCode(this.height) | 0;
    return result;
  };
  RectGeom.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.x, other.x) && Kotlin.equals(this.y, other.y) && Kotlin.equals(this.width, other.width) && Kotlin.equals(this.height, other.height)))));
  };
  function size(x, y) {
    return new Size(x, y);
  }
  function size_0(x, y) {
    return new Size(x, y);
  }
  function Size(width, height) {
    if (height === void 0)
      height = width;
    this.width = width;
    this.height = height;
  }
  Size.prototype.plus_14dthe$ = function (value) {
    return new Size(this.width + value, this.height + value);
  };
  Size.prototype.minus_14dthe$ = function (value) {
    return new Size(this.width - value, this.height - value);
  };
  Size.prototype.times_14dthe$ = function (value) {
    return new Size(this.width * value, this.height * value);
  };
  Size.prototype.div_14dthe$ = function (value) {
    return new Size(this.width / value, this.height / value);
  };
  Size.prototype.rem_14dthe$ = function (value) {
    return new Size(this.width % value, this.height % value);
  };
  Size.prototype.plus_wt4k8k$ = function (size) {
    return new Size(this.width + size.width, this.height + size.height);
  };
  Size.prototype.minus_wt4k8k$ = function (size) {
    return new Size(this.width - size.width, this.height - size.height);
  };
  Size.prototype.times_wt4k8k$ = function (size) {
    return new Size(this.width * size.width, this.height * size.height);
  };
  Size.prototype.div_wt4k8k$ = function (size) {
    return new Size(this.width / size.width, this.height / size.height);
  };
  Size.prototype.rem_wt4k8k$ = function (size) {
    return new Size(this.width % size.width, this.height % size.height);
  };
  Size.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Size',
    interfaces: []
  };
  Size.prototype.component1 = function () {
    return this.width;
  };
  Size.prototype.component2 = function () {
    return this.height;
  };
  Size.prototype.copy_lu1900$ = function (width, height) {
    return new Size(width === void 0 ? this.width : width, height === void 0 ? this.height : height);
  };
  Size.prototype.toString = function () {
    return 'Size(width=' + Kotlin.toString(this.width) + (', height=' + Kotlin.toString(this.height)) + ')';
  };
  Size.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.width) | 0;
    result = result * 31 + Kotlin.hashCode(this.height) | 0;
    return result;
  };
  Size.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.width, other.width) && Kotlin.equals(this.height, other.height)))));
  };
  function HasSize() {
  }
  Object.defineProperty(HasSize.prototype, 'size', {
    get: function () {
      return new Size(this.width, this.height);
    },
    set: function (value) {
      this.width = value.width;
      this.height = value.height;
    }
  });
  HasSize.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'HasSize',
    interfaces: []
  };
  function Vector(vx, vy) {
    if (vx === void 0)
      vx = 0.0;
    if (vy === void 0)
      vy = 0.0;
    this.vx = vx;
    this.vy = vy;
  }
  Vector.prototype.plus_9jrimm$ = function (vector) {
    return new Vector(this.vx + vector.vx, this.vy + vector.vy);
  };
  Vector.prototype.minus_9jrimm$ = function (vector) {
    return new Vector(this.vx - vector.vx, this.vy - vector.vy);
  };
  Vector.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Vector',
    interfaces: []
  };
  Vector.prototype.component1 = function () {
    return this.vx;
  };
  Vector.prototype.component2 = function () {
    return this.vy;
  };
  Vector.prototype.copy_lu1900$ = function (vx, vy) {
    return new Vector(vx === void 0 ? this.vx : vx, vy === void 0 ? this.vy : vy);
  };
  Vector.prototype.toString = function () {
    return 'Vector(vx=' + Kotlin.toString(this.vx) + (', vy=' + Kotlin.toString(this.vy)) + ')';
  };
  Vector.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.vx) | 0;
    result = result * 31 + Kotlin.hashCode(this.vy) | 0;
    return result;
  };
  Vector.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.vx, other.vx) && Kotlin.equals(this.vy, other.vy)))));
  };
  function Angle(rad) {
    this.rad = rad;
  }
  Object.defineProperty(Angle.prototype, 'cos', {
    get: function () {
      var x = this.rad;
      return Math_0.cos(x);
    }
  });
  Object.defineProperty(Angle.prototype, 'sin', {
    get: function () {
      var x = this.rad;
      return Math_0.sin(x);
    }
  });
  Object.defineProperty(Angle.prototype, 'tan', {
    get: function () {
      var x = this.rad;
      return Math_0.tan(x);
    }
  });
  Object.defineProperty(Angle.prototype, 'deg', {
    get: function () {
      return this.rad * RAD_TO_DEG;
    }
  });
  Angle.prototype.normalize = function () {
    return this.rad >= 0 ? new Angle(this.rad % TAU_ANGLE.rad) : new Angle(this.rad % TAU_ANGLE.rad + TAU_ANGLE.rad);
  };
  Angle.prototype.plus_5t6zck$ = function (angle) {
    return new Angle(this.rad + angle.rad);
  };
  Angle.prototype.minus_5t6zck$ = function (angle) {
    return new Angle(this.rad - angle.rad);
  };
  Angle.prototype.times_3p81yu$ = function (d) {
    return new Angle(this.rad * numberToDouble(d));
  };
  Angle.prototype.div_3p81yu$ = function (d) {
    return new Angle(this.rad / numberToDouble(d));
  };
  Angle.prototype.div_5t6zck$ = function (other) {
    return this.rad / other.rad;
  };
  Angle.prototype.unaryMinus = function () {
    return new Angle(-this.rad);
  };
  Angle.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Angle',
    interfaces: []
  };
  Angle.prototype.unbox = function () {
    return this.rad;
  };
  Angle.prototype.toString = function () {
    return 'Angle(rad=' + Kotlin.toString(this.rad) + ')';
  };
  Angle.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.rad) | 0;
    return result;
  };
  Angle.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && Kotlin.equals(this.rad, other.rad))));
  };
  function toRadians($receiver) {
    return $receiver * DEG_TO_RAD;
  }
  function toDegrees($receiver) {
    return $receiver * RAD_TO_DEG;
  }
  function get_deg($receiver) {
    return new Angle(numberToDouble($receiver) * DEG_TO_RAD);
  }
  function get_rad($receiver) {
    return new Angle(numberToDouble($receiver));
  }
  function times($receiver, angle) {
    return new Angle(angle.rad * numberToDouble($receiver));
  }
  var EPSILON;
  var EPSILON2;
  var PI;
  var HALFPI;
  var THIRDPI;
  var QUARTERPI;
  var TAU;
  var TAU_EPSILON;
  var DEG_TO_RAD;
  var RAD_TO_DEG;
  var PI_ANGLE;
  var HALFPI_ANGLE;
  var TAU_ANGLE;
  function Matrix(a, b, c, d, tx, ty) {
    if (a === void 0)
      a = 1.0;
    if (b === void 0)
      b = 0.0;
    if (c === void 0)
      c = 0.0;
    if (d === void 0)
      d = 1.0;
    if (tx === void 0)
      tx = 0.0;
    if (ty === void 0)
      ty = 0.0;
    this.a_8be2vx$ = a;
    this.b_8be2vx$ = b;
    this.c_8be2vx$ = c;
    this.d_8be2vx$ = d;
    this.tx_8be2vx$ = tx;
    this.ty_8be2vx$ = ty;
  }
  Matrix.prototype.reset = function () {
    this.a_8be2vx$ = 1.0;
    this.d_8be2vx$ = 1.0;
    this.b_8be2vx$ = 0.0;
    this.c_8be2vx$ = 0.0;
    this.tx_8be2vx$ = 0.0;
    this.ty_8be2vx$ = 0.0;
    return this;
  };
  Matrix.prototype.isIdentity = function () {
    return this.a_8be2vx$ === 1.0 && this.b_8be2vx$ === 0.0 && this.c_8be2vx$ === 0.0 && this.d_8be2vx$ === 1.0 && this.tx_8be2vx$ === 0.0 && this.ty_8be2vx$ === 0.0;
  };
  Matrix.prototype.append_rgp5mo$ = function (other) {
    var a1 = this.a_8be2vx$;
    var b1 = this.b_8be2vx$;
    var c1 = this.c_8be2vx$;
    var d1 = this.d_8be2vx$;
    var a2 = other.a_8be2vx$;
    var b2 = other.b_8be2vx$;
    var c2 = other.c_8be2vx$;
    var d2 = other.d_8be2vx$;
    var tx2 = other.tx_8be2vx$;
    var ty2 = other.ty_8be2vx$;
    this.a_8be2vx$ = a2 * a1 + c2 * c1;
    this.c_8be2vx$ = b2 * a1 + d2 * c1;
    this.b_8be2vx$ = a2 * b1 + c2 * d1;
    this.d_8be2vx$ = b2 * b1 + d2 * d1;
    this.tx_8be2vx$ += tx2 * a1 + ty2 * c1;
    this.ty_8be2vx$ += tx2 * b1 + ty2 * d1;
    return this;
  };
  Matrix.prototype.prepend_rgp5mo$ = function (mx) {
    var a1 = this.a_8be2vx$;
    var b1 = this.b_8be2vx$;
    var c1 = this.c_8be2vx$;
    var d1 = this.d_8be2vx$;
    var tx1 = this.tx_8be2vx$;
    var ty1 = this.ty_8be2vx$;
    var a2 = mx.a_8be2vx$;
    var b2 = mx.c_8be2vx$;
    var c2 = mx.b_8be2vx$;
    var d2 = mx.d_8be2vx$;
    var tx2 = mx.tx_8be2vx$;
    var ty2 = mx.ty_8be2vx$;
    this.a_8be2vx$ = a2 * a1 + b2 * b1;
    this.c_8be2vx$ = a2 * c1 + b2 * d1;
    this.b_8be2vx$ = c2 * a1 + d2 * b1;
    this.d_8be2vx$ = c2 * c1 + d2 * d1;
    this.tx_8be2vx$ = a2 * tx1 + b2 * ty1 + tx2;
    this.ty_8be2vx$ = c2 * tx1 + d2 * ty1 + ty2;
    return this;
  };
  Matrix.prototype.translate_mowjvf$ = function (pt) {
    return this.translate_lu1900$(pt.x, pt.y);
  };
  Matrix.prototype.translate_lu1900$ = function (x, y) {
    this.tx_8be2vx$ += x * this.a_8be2vx$ + y * this.c_8be2vx$;
    this.ty_8be2vx$ += x * this.b_8be2vx$ + y * this.d_8be2vx$;
    return this;
  };
  Matrix.prototype.scale_nve2j0$ = function (scaleXY, center) {
    if (center === void 0)
      center = null;
    return this.scale_phso0u$(scaleXY, scaleXY, center);
  };
  Matrix.prototype.scale_phso0u$ = function (scaleX, scaleY, center) {
    if (center === void 0)
      center = null;
    if (!(scaleX !== 0.0)) {
      var message = scaleX.toString() + ' should be different than 0.0 to ensure the matrix is invertible ';
      throw IllegalArgumentException_init(message.toString());
    }
    if (!(scaleY !== 0.0)) {
      var message_0 = scaleY.toString() + ' should be different than 0.0 to ensure the matrix is invertible ';
      throw IllegalArgumentException_init(message_0.toString());
    }
    if (center != null) {
      this.translate_mowjvf$(center);
    }
    this.a_8be2vx$ *= scaleX;
    this.b_8be2vx$ *= scaleX;
    this.c_8be2vx$ *= scaleY;
    this.d_8be2vx$ *= scaleY;
    if (center != null) {
      this.translate_mowjvf$(center.unaryMinus());
    }
    return this;
  };
  Matrix.prototype.rotate_m2xpry$ = function (angle, center) {
    if (center === void 0)
      center = null;
    var cos = angle.cos;
    var sin = angle.sin;
    var tempA = this.a_8be2vx$;
    var tempB = this.b_8be2vx$;
    var tempC = this.c_8be2vx$;
    var tempD = this.d_8be2vx$;
    this.a_8be2vx$ = cos * tempA + sin * tempC;
    this.b_8be2vx$ = cos * tempB + sin * tempD;
    this.c_8be2vx$ = -sin * tempA + cos * tempC;
    this.d_8be2vx$ = -sin * tempB + cos * tempD;
    if (center != null) {
      var x = center.x;
      var y = center.y;
      var tempTx = x - x * cos + y * sin;
      var tempTy = y - x * sin - y * cos;
      this.tx_8be2vx$ += tempTx * tempA + tempTy * tempC;
      this.ty_8be2vx$ += tempTx * tempB + tempTy * tempD;
    }
    return this;
  };
  Matrix.prototype.transform_mowjvf$ = function (point_0) {
    return point(point_0.x * this.a_8be2vx$ + point_0.y * this.c_8be2vx$ + this.tx_8be2vx$, point_0.x * this.b_8be2vx$ + point_0.y * this.d_8be2vx$ + this.ty_8be2vx$);
  };
  Matrix.prototype.inverseTransform_mowjvf$ = function (point_0) {
    var x = point_0.x - this.tx_8be2vx$;
    var y = point_0.y - this.ty_8be2vx$;
    var det = this.a_8be2vx$ * this.d_8be2vx$ - this.b_8be2vx$ * this.c_8be2vx$;
    return point((x * this.d_8be2vx$ - y * this.c_8be2vx$) / det, (y * this.a_8be2vx$ - x * this.b_8be2vx$) / det);
  };
  Matrix.prototype.isInvertible_8be2vx$ = defineInlineFunction('d2v-core-js.io.data2viz.math.Matrix.isInvertible_8be2vx$', wrapFunction(function () {
    var isNaN_0 = Kotlin.kotlin.isNaN_yrwdxr$;
    var isFinite = Kotlin.kotlin.isFinite_yrwdxr$;
    return function () {
      var det = this.a_8be2vx$ * this.d_8be2vx$ - this.c_8be2vx$ * this.b_8be2vx$;
      return det !== 0.0 && !isNaN_0(det) && isFinite(this.tx_8be2vx$) && isFinite(this.ty_8be2vx$);
    };
  }));
  Matrix.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Matrix',
    interfaces: []
  };
  Matrix.prototype.component1_8be2vx$ = function () {
    return this.a_8be2vx$;
  };
  Matrix.prototype.component2_8be2vx$ = function () {
    return this.b_8be2vx$;
  };
  Matrix.prototype.component3_8be2vx$ = function () {
    return this.c_8be2vx$;
  };
  Matrix.prototype.component4_8be2vx$ = function () {
    return this.d_8be2vx$;
  };
  Matrix.prototype.component5_8be2vx$ = function () {
    return this.tx_8be2vx$;
  };
  Matrix.prototype.component6_8be2vx$ = function () {
    return this.ty_8be2vx$;
  };
  Matrix.prototype.copy_15yvbs$ = function (a, b, c, d, tx, ty) {
    return new Matrix(a === void 0 ? this.a_8be2vx$ : a, b === void 0 ? this.b_8be2vx$ : b, c === void 0 ? this.c_8be2vx$ : c, d === void 0 ? this.d_8be2vx$ : d, tx === void 0 ? this.tx_8be2vx$ : tx, ty === void 0 ? this.ty_8be2vx$ : ty);
  };
  Matrix.prototype.toString = function () {
    return 'Matrix(a=' + Kotlin.toString(this.a_8be2vx$) + (', b=' + Kotlin.toString(this.b_8be2vx$)) + (', c=' + Kotlin.toString(this.c_8be2vx$)) + (', d=' + Kotlin.toString(this.d_8be2vx$)) + (', tx=' + Kotlin.toString(this.tx_8be2vx$)) + (', ty=' + Kotlin.toString(this.ty_8be2vx$)) + ')';
  };
  Matrix.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.a_8be2vx$) | 0;
    result = result * 31 + Kotlin.hashCode(this.b_8be2vx$) | 0;
    result = result * 31 + Kotlin.hashCode(this.c_8be2vx$) | 0;
    result = result * 31 + Kotlin.hashCode(this.d_8be2vx$) | 0;
    result = result * 31 + Kotlin.hashCode(this.tx_8be2vx$) | 0;
    result = result * 31 + Kotlin.hashCode(this.ty_8be2vx$) | 0;
    return result;
  };
  Matrix.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.a_8be2vx$, other.a_8be2vx$) && Kotlin.equals(this.b_8be2vx$, other.b_8be2vx$) && Kotlin.equals(this.c_8be2vx$, other.c_8be2vx$) && Kotlin.equals(this.d_8be2vx$, other.d_8be2vx$) && Kotlin.equals(this.tx_8be2vx$, other.tx_8be2vx$) && Kotlin.equals(this.ty_8be2vx$, other.ty_8be2vx$)))));
  };
  function Percent(value) {
    this.value = value;
  }
  Percent.prototype.plus_o5f5ne$ = function (other) {
    return new Percent(this.value + other.value);
  };
  Percent.prototype.minus_o5f5ne$ = function (other) {
    return new Percent(this.value - other.value);
  };
  Percent.prototype.times_o5f5ne$ = function (other) {
    return new Percent(this.value * other.value);
  };
  Percent.prototype.div_3p81yu$ = function (d) {
    return new Percent(this.value / numberToDouble(d));
  };
  Percent.prototype.times_3p81yu$ = function (d) {
    return this.value * numberToDouble(d);
  };
  Percent.prototype.unaryMinus = function () {
    return new Percent(-this.value);
  };
  Percent.prototype.unaryPlus = function () {
    return this;
  };
  Percent.prototype.compareTo_o5f5ne$ = function (other) {
    return Kotlin.compareTo(this.value, other.value);
  };
  Percent.prototype.coerceAtLeast_o5f5ne$ = function (min) {
    return new Percent(coerceAtLeast(this.value, min.value));
  };
  Percent.prototype.coerceAtMost_o5f5ne$ = function (max) {
    return new Percent(coerceAtMost(this.value, max.value));
  };
  Percent.prototype.coerceIn_8bp15g$ = function (min, max) {
    return new Percent(coerceIn(this.value, min.value, max.value));
  };
  Percent.prototype.coerceToDefault = function () {
    return new Percent(coerceIn(this.value, 0.0, 1.0));
  };
  Percent.prototype.toString = function () {
    return (this.value * 100).toString() + '%';
  };
  Percent.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Percent',
    interfaces: []
  };
  Percent.prototype.unbox = function () {
    return this.value;
  };
  Percent.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.value) | 0;
    return result;
  };
  Percent.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && Kotlin.equals(this.value, other.value))));
  };
  function get_pct($receiver) {
    return new Percent(numberToDouble($receiver) / 100.0);
  }
  function times_0($receiver, percent) {
    return percent.value * numberToDouble($receiver);
  }
  var e10;
  var e5;
  var e2;
  function ln10$lambda() {
    return Math_0.log(10.0);
  }
  var ln10;
  function get_ln10() {
    return ln10.value;
  }
  function ticks(start, stop, count) {
    var step = tickStep(numberToDouble(start), numberToDouble(stop), count);
    var x = numberToDouble(start) / step;
    var tmp$ = Math_0.ceil(x) * step;
    var x_0 = numberToDouble(stop) / step;
    return range(tmp$, Math_0.floor(x_0) * step + step / 2, step);
  }
  function tickStep(start, stop, count) {
    var x = stop - start;
    var step0 = Math_0.abs(x) / count;
    var x_0 = Math_0.log(step0) / get_ln10();
    var x_1 = Math_0.floor(x_0);
    var step1 = Math_0.pow(10.0, x_1);
    var error = step0 / step1;
    if (error >= e10)
      step1 *= 10;
    else if (error >= e5)
      step1 *= 5;
    else if (error >= e2)
      step1 *= 2;
    return stop < start ? -step1 : step1;
  }
  function range(start, stop, step) {
    if (step === void 0)
      step = 1.0;
    var x = (stop - start) / step;
    var b = numberToInt(Math_0.ceil(x));
    var n = Math_0.max(0, b);
    var $receiver = new IntRange(0, n - 1 | 0);
    var destination = ArrayList_init_0(collectionSizeOrDefault($receiver, 10));
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(start + item * step);
    }
    return destination;
  }
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$geom = package$data2viz.geom || (package$data2viz.geom = {});
  package$geom.Circle = Circle;
  package$geom.CircleGeom = CircleGeom;
  package$geom.Extent = Extent;
  package$geom.Path = Path;
  package$geom.PathGeom = PathGeom;
  package$geom.PathCommand = PathCommand;
  package$geom.MoveTo = MoveTo;
  package$geom.LineTo = LineTo;
  package$geom.RectCmd = RectCmd;
  package$geom.QuadraticCurveTo = QuadraticCurveTo;
  package$geom.BezierCurveTo = BezierCurveTo;
  package$geom.Arc = Arc;
  package$geom.ArcTo = ArcTo;
  package$geom.ClosePath = ClosePath;
  package$geom.get_svgPath_iy468z$ = get_svgPath;
  package$geom.point_lu1900$ = point;
  package$geom.point_vux9f0$ = point_0;
  Object.defineProperty(Point, 'Companion', {
    get: Point$Companion_getInstance
  });
  package$geom.Point = Point;
  package$geom.Polygon = Polygon;
  package$geom.get_area_87qcao$ = get_area;
  package$geom.get_centroid_87qcao$ = get_centroid;
  package$geom.get_length_87qcao$ = get_length;
  package$geom.contains_8x84v9$ = contains;
  package$geom.polygonHull_ccv9pi$ = polygonHull;
  package$geom.Rect = Rect;
  package$geom.RectGeom_init_r8do57$ = RectGeom_init;
  package$geom.RectGeom_init_840z2k$ = RectGeom_init_0;
  package$geom.RectGeom = RectGeom;
  package$geom.size_lu1900$ = size;
  package$geom.size_vux9f0$ = size_0;
  package$geom.Size = Size;
  package$geom.HasSize = HasSize;
  package$geom.Vector = Vector;
  var package$math = package$data2viz.math || (package$data2viz.math = {});
  package$math.Angle = Angle;
  package$math.toRadians_yrwdxr$ = toRadians;
  package$math.toDegrees_yrwdxr$ = toDegrees;
  package$math.get_deg_rcaex3$ = get_deg;
  package$math.get_rad_rcaex3$ = get_rad;
  package$math.times_gcyr0z$ = times;
  Object.defineProperty(package$math, 'EPSILON', {
    get: function () {
      return EPSILON;
    }
  });
  Object.defineProperty(package$math, 'EPSILON2', {
    get: function () {
      return EPSILON2;
    }
  });
  Object.defineProperty(package$math, 'PI', {
    get: function () {
      return PI;
    }
  });
  Object.defineProperty(package$math, 'HALFPI', {
    get: function () {
      return HALFPI;
    }
  });
  Object.defineProperty(package$math, 'THIRDPI', {
    get: function () {
      return THIRDPI;
    }
  });
  Object.defineProperty(package$math, 'QUARTERPI', {
    get: function () {
      return QUARTERPI;
    }
  });
  Object.defineProperty(package$math, 'TAU', {
    get: function () {
      return TAU;
    }
  });
  Object.defineProperty(package$math, 'TAU_EPSILON', {
    get: function () {
      return TAU_EPSILON;
    }
  });
  Object.defineProperty(package$math, 'DEG_TO_RAD', {
    get: function () {
      return DEG_TO_RAD;
    }
  });
  Object.defineProperty(package$math, 'RAD_TO_DEG', {
    get: function () {
      return RAD_TO_DEG;
    }
  });
  Object.defineProperty(package$math, 'PI_ANGLE', {
    get: function () {
      return PI_ANGLE;
    }
  });
  Object.defineProperty(package$math, 'HALFPI_ANGLE', {
    get: function () {
      return HALFPI_ANGLE;
    }
  });
  Object.defineProperty(package$math, 'TAU_ANGLE', {
    get: function () {
      return TAU_ANGLE;
    }
  });
  package$math.Matrix = Matrix;
  package$math.Percent = Percent;
  package$math.get_pct_rcaex3$ = get_pct;
  package$math.times_lans69$ = times_0;
  Object.defineProperty(package$math, 'e10', {
    get: function () {
      return e10;
    }
  });
  Object.defineProperty(package$math, 'e5', {
    get: function () {
      return e5;
    }
  });
  Object.defineProperty(package$math, 'e2', {
    get: function () {
      return e2;
    }
  });
  Object.defineProperty(package$math, 'ln10', {
    get: get_ln10
  });
  package$math.ticks_1stjjm$ = ticks;
  package$math.tickStep_syxxoe$ = tickStep;
  package$math.range_yvo9jy$ = range;
  PathGeom.prototype.arc_6p3vsx$ = Path.prototype.arc_6p3vsx$;
  Object.defineProperty(Rect.prototype, 'size', Object.getOwnPropertyDescriptor(HasSize.prototype, 'size'));
  Object.defineProperty(RectGeom.prototype, 'top', Object.getOwnPropertyDescriptor(Rect.prototype, 'top'));
  Object.defineProperty(RectGeom.prototype, 'bottom', Object.getOwnPropertyDescriptor(Rect.prototype, 'bottom'));
  Object.defineProperty(RectGeom.prototype, 'left', Object.getOwnPropertyDescriptor(Rect.prototype, 'left'));
  Object.defineProperty(RectGeom.prototype, 'right', Object.getOwnPropertyDescriptor(Rect.prototype, 'right'));
  Object.defineProperty(RectGeom.prototype, 'topLeft', Object.getOwnPropertyDescriptor(Rect.prototype, 'topLeft'));
  Object.defineProperty(RectGeom.prototype, 'topRight', Object.getOwnPropertyDescriptor(Rect.prototype, 'topRight'));
  Object.defineProperty(RectGeom.prototype, 'bottomLeft', Object.getOwnPropertyDescriptor(Rect.prototype, 'bottomLeft'));
  Object.defineProperty(RectGeom.prototype, 'bottomRight', Object.getOwnPropertyDescriptor(Rect.prototype, 'bottomRight'));
  Object.defineProperty(RectGeom.prototype, 'center', Object.getOwnPropertyDescriptor(Rect.prototype, 'center'));
  RectGeom.prototype.contains_mowjvf$ = Rect.prototype.contains_mowjvf$;
  RectGeom.prototype.contains_wt5aq9$ = Rect.prototype.contains_wt5aq9$;
  Object.defineProperty(RectGeom.prototype, 'size', Object.getOwnPropertyDescriptor(Rect.prototype, 'size'));
  EPSILON = 1.0E-6;
  EPSILON2 = EPSILON * EPSILON;
  PI = math.PI;
  HALFPI = PI / 2.0;
  THIRDPI = PI / 3.0;
  QUARTERPI = PI / 4.0;
  TAU = PI * 2.0;
  TAU_EPSILON = TAU - EPSILON;
  DEG_TO_RAD = math.PI / 180;
  RAD_TO_DEG = 180 / math.PI;
  PI_ANGLE = new Angle(math.PI);
  HALFPI_ANGLE = PI_ANGLE.div_3p81yu$(2);
  TAU_ANGLE = PI_ANGLE.times_3p81yu$(2);
  e10 = Math_0.sqrt(50.0);
  e5 = Math_0.sqrt(10.0);
  e2 = Math_0.sqrt(2.0);
  ln10 = lazy(ln10$lambda);
  Kotlin.defineModule('d2v-core-js', _);
  return _;
}));

//# sourceMappingURL=d2v-core-js.js.map
