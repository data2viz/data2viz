(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'd2v-ease-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'd2v-ease-js'.");
    }
    root['d2v-ease-js'] = factory(typeof this['d2v-ease-js'] === 'undefined' ? {} : this['d2v-ease-js'], kotlin);
  }
}(this, function (_, Kotlin) {
  'use strict';
  var math = Kotlin.kotlin.math;
  var Kind_OBJECT = Kotlin.Kind.OBJECT;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var coerceAtLeast = Kotlin.kotlin.ranges.coerceAtLeast_38ydlf$;
  var tau;
  var halfPi;
  var b1;
  var b2;
  var b3;
  var b4;
  var b5;
  var b6;
  var b7;
  var b8;
  var b9;
  var b0;
  function ease() {
    ease$Companion_getInstance();
  }
  function ease$Companion() {
    ease$Companion_instance = this;
    this.linear = ease$Companion$linear$lambda;
    this.bounceIn = ease$Companion$bounceIn$lambda(this);
    this.bounceOut = ease$Companion$bounceOut$lambda;
    this.bounceInOut = ease$Companion$bounceInOut$lambda(this);
    this.circleIn = ease$Companion$circleIn$lambda;
    this.circleOut = ease$Companion$circleOut$lambda;
    this.circleInOut = ease$Companion$circleInOut$lambda;
    this.cubicIn = ease$Companion$cubicIn$lambda;
    this.cubicOut = ease$Companion$cubicOut$lambda;
    this.cubicInOut = ease$Companion$cubicInOut$lambda;
    this.expIn = ease$Companion$expIn$lambda;
    this.expOut = ease$Companion$expOut$lambda;
    this.expInOut = ease$Companion$expInOut$lambda;
    this.quadIn = ease$Companion$quadIn$lambda;
    this.quadOut = ease$Companion$quadOut$lambda;
    this.quadInOut = ease$Companion$quadInOut$lambda;
    this.sinIn = ease$Companion$sinIn$lambda;
    this.sinOut = ease$Companion$sinOut$lambda;
    this.sinInOut = ease$Companion$sinInOut$lambda;
    this.backIn = new BackIn();
    this.backOut = new BackOut();
    this.backInOut = new BackInOut();
    this.elasticIn = new ElasticIn();
    this.elasticOut = new ElasticOut();
    this.elasticInOut = new ElasticInOut();
    this.polyIn = new PolyIn();
    this.polyOut = new PolyOut();
    this.polyInOut = new PolyInOut();
  }
  function ease$Companion$linear$lambda(it) {
    return it;
  }
  function ease$Companion$bounceIn$lambda(this$ease$) {
    return function (it) {
      return 1 - this$ease$.bounceOut(1 - it);
    };
  }
  function ease$Companion$bounceOut$lambda(t) {
    if (t < b1)
      return b0 * t * t;
    else if (t < b3)
      return b0 * (t - b2) * (t - b2) + b4;
    else if (t < b6)
      return b0 * (t - b5) * (t - b5) + b7;
    else
      return b0 * (t - b8) * (t - b8) + b9;
  }
  function ease$Companion$bounceInOut$lambda(this$ease$) {
    return function (it) {
      var $receiver = it * 2;
      var this$ease$_0 = this$ease$;
      return ($receiver <= 1 ? 1 - this$ease$_0.bounceOut(1 - $receiver) : this$ease$_0.bounceOut($receiver - 1) + 1) / 2;
    };
  }
  var Math_0 = Math;
  function ease$Companion$circleIn$lambda(it) {
    var x = 1 - it * it;
    return 1 - Math_0.sqrt(x);
  }
  function ease$Companion$circleOut$lambda(it) {
    var x = 1.0 - (it - 1) * (it - 1);
    return Math_0.sqrt(x);
  }
  function ease$Companion$circleInOut$lambda(it) {
    var it_0 = it * 2;
    var tmp$;
    if (it_0 <= 1) {
      var x = 1 - it_0 * it_0;
      tmp$ = 1 - Math_0.sqrt(x);
    }
     else {
      var x_0 = 1.0 - (it_0 - 2) * (it_0 - 2);
      tmp$ = Math_0.sqrt(x_0) + 1;
    }
    return tmp$ / 2;
  }
  function ease$Companion$cubicIn$lambda(it) {
    return it * it * it;
  }
  function ease$Companion$cubicOut$lambda(it) {
    return (it - 1) * (it - 1) * (it - 1) + 1;
  }
  function ease$Companion$cubicInOut$lambda(it) {
    var it_0 = it * 2;
    return (it_0 <= 1 ? it_0 * it_0 * it_0 : (it_0 - 2) * (it_0 - 2) * (it_0 - 2) + 2) / 2;
  }
  function ease$Companion$expIn$lambda(it) {
    var x = 10.0 * it - 10;
    return Math_0.pow(2.0, x);
  }
  function ease$Companion$expOut$lambda(it) {
    var x = -10 * it;
    return 1 - Math_0.pow(2.0, x);
  }
  function ease$Companion$expInOut$lambda(it) {
    var it_0 = it * 2;
    var tmp$;
    if (it_0 <= 1) {
      var x = 10.0 * it_0 - 10;
      tmp$ = Math_0.pow(2.0, x);
    }
     else {
      var x_0 = 10 - 10 * it_0;
      tmp$ = 2 - Math_0.pow(2.0, x_0);
    }
    return tmp$ / 2;
  }
  function ease$Companion$quadIn$lambda(it) {
    return it * it;
  }
  function ease$Companion$quadOut$lambda(it) {
    return it * (2 - it);
  }
  function ease$Companion$quadInOut$lambda(it) {
    var it_0 = it * 2;
    return (it_0 <= 1 ? it_0 * it_0 : (it_0 - 1) * (3 - it_0) + 1) / 2;
  }
  function ease$Companion$sinIn$lambda(it) {
    var x = it * halfPi;
    return 1 - Math_0.cos(x);
  }
  function ease$Companion$sinOut$lambda(it) {
    var x = it * halfPi;
    return Math_0.sin(x);
  }
  function ease$Companion$sinInOut$lambda(it) {
    var x = math.PI * it;
    return (1 - Math_0.cos(x)) / 2;
  }
  ease$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var ease$Companion_instance = null;
  function ease$Companion_getInstance() {
    if (ease$Companion_instance === null) {
      new ease$Companion();
    }
    return ease$Companion_instance;
  }
  ease.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ease',
    interfaces: []
  };
  function ElasticIn(amplitude, period) {
    if (amplitude === void 0)
      amplitude = 1.0;
    if (period === void 0)
      period = 0.3;
    this.amplitude_0 = amplitude;
    this.period_0 = period;
    this.a_0 = coerceAtLeast(this.amplitude_0, 1.0);
    this.p_0 = this.period_0 / tau;
    var x = 1 / this.a_0;
    this.s_0 = Math_0.asin(x) * this.p_0;
  }
  ElasticIn.prototype.amplitude_14dthe$ = function (amplitude) {
    return new ElasticIn(amplitude, this.period_0);
  };
  ElasticIn.prototype.period_14dthe$ = function (period) {
    return new ElasticIn(this.amplitude_0, period);
  };
  ElasticIn.prototype.invoke_14dthe$ = function (t) {
    var tmp$ = this.a_0;
    var x = 10 * (t - 1);
    var tmp$_0 = tmp$ * Math_0.pow(2.0, x);
    var x_0 = (this.s_0 - t + 1) / this.p_0;
    return tmp$_0 * Math_0.sin(x_0);
  };
  ElasticIn.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ElasticIn',
    interfaces: []
  };
  function ElasticOut(amplitude, period) {
    if (amplitude === void 0)
      amplitude = 1.0;
    if (period === void 0)
      period = 0.3;
    this.amplitude_0 = amplitude;
    this.period_0 = period;
    this.a_0 = coerceAtLeast(this.amplitude_0, 1.0);
    this.p_0 = this.period_0 / tau;
    var x = 1 / this.a_0;
    this.s_0 = Math_0.asin(x) * this.p_0;
  }
  ElasticOut.prototype.amplitude_14dthe$ = function (amplitude) {
    return new ElasticOut(amplitude, this.period_0);
  };
  ElasticOut.prototype.period_14dthe$ = function (period) {
    return new ElasticOut(this.amplitude_0, period);
  };
  ElasticOut.prototype.invoke_14dthe$ = function (t) {
    var tmp$ = this.a_0;
    var x = -10 * t;
    var tmp$_0 = tmp$ * Math_0.pow(2.0, x);
    var x_0 = (t + this.s_0) / this.p_0;
    return 1 - tmp$_0 * Math_0.sin(x_0);
  };
  ElasticOut.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ElasticOut',
    interfaces: []
  };
  function ElasticInOut(amplitude, period) {
    if (amplitude === void 0)
      amplitude = 1.0;
    if (period === void 0)
      period = 0.3;
    this.amplitude_0 = amplitude;
    this.period_0 = period;
    this.a_0 = coerceAtLeast(this.amplitude_0, 1.0);
    this.p_0 = this.period_0 / tau;
    var x = 1 / this.a_0;
    this.s_0 = Math_0.asin(x) * this.p_0;
  }
  ElasticInOut.prototype.amplitude_14dthe$ = function (amplitude) {
    return new ElasticInOut(amplitude, this.period_0);
  };
  ElasticInOut.prototype.period_14dthe$ = function (period) {
    return new ElasticInOut(this.amplitude_0, period);
  };
  ElasticInOut.prototype.invoke_14dthe$ = function (t) {
    var it = t * 2 - 1;
    var tmp$;
    if (it < 0) {
      var tmp$_0 = this.a_0;
      var x = 10 * it;
      var tmp$_1 = tmp$_0 * Math_0.pow(2.0, x);
      var x_0 = (this.s_0 - it) / this.p_0;
      tmp$ = tmp$_1 * Math_0.sin(x_0);
    }
     else {
      var tmp$_2 = this.a_0;
      var x_1 = -10 * it;
      var tmp$_3 = tmp$_2 * Math_0.pow(2.0, x_1);
      var x_2 = (this.s_0 + it) / this.p_0;
      tmp$ = 2.0 - tmp$_3 * Math_0.sin(x_2);
    }
    return tmp$ / 2;
  };
  ElasticInOut.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ElasticInOut',
    interfaces: []
  };
  function BackIn(overshoot) {
    if (overshoot === void 0)
      overshoot = 1.70158;
    this.overshoot_0 = overshoot;
  }
  BackIn.prototype.overshoot_14dthe$ = function (overshoot) {
    return new BackIn(overshoot);
  };
  BackIn.prototype.invoke_14dthe$ = function (t) {
    return t * t * ((this.overshoot_0 + 1) * t - this.overshoot_0);
  };
  BackIn.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'BackIn',
    interfaces: []
  };
  function BackOut(overshoot) {
    if (overshoot === void 0)
      overshoot = 1.70158;
    this.overshoot_0 = overshoot;
  }
  BackOut.prototype.overshoot_14dthe$ = function (overshoot) {
    return new BackOut(overshoot);
  };
  BackOut.prototype.invoke_14dthe$ = function (t) {
    return (t - 1) * (t - 1) * ((this.overshoot_0 + 1) * (t - 1) + this.overshoot_0) + 1;
  };
  BackOut.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'BackOut',
    interfaces: []
  };
  function BackInOut(overshoot) {
    if (overshoot === void 0)
      overshoot = 1.70158;
    this.overshoot_0 = overshoot;
  }
  BackInOut.prototype.overshoot_14dthe$ = function (overshoot) {
    return new BackInOut(overshoot);
  };
  BackInOut.prototype.invoke_14dthe$ = function (t) {
    var it = t * 2;
    return (it < 1 ? it * it * ((this.overshoot_0 + 1) * it - this.overshoot_0) : (it - 2) * (it - 2) * ((this.overshoot_0 + 1) * (it - 2) + this.overshoot_0) + 2) / 2;
  };
  BackInOut.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'BackInOut',
    interfaces: []
  };
  function PolyIn(exponent) {
    if (exponent === void 0)
      exponent = 3.0;
    this.exponent_0 = exponent;
  }
  PolyIn.prototype.exponent_14dthe$ = function (exponent) {
    return new PolyIn(exponent);
  };
  PolyIn.prototype.invoke_14dthe$ = function (t) {
    var x = this.exponent_0;
    return Math_0.pow(t, x);
  };
  PolyIn.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'PolyIn',
    interfaces: []
  };
  function PolyOut(exponent) {
    if (exponent === void 0)
      exponent = 3.0;
    this.exponent_0 = exponent;
  }
  PolyOut.prototype.exponent_14dthe$ = function (exponent) {
    return new PolyOut(exponent);
  };
  PolyOut.prototype.invoke_14dthe$ = function (t) {
    var $receiver = 1 - t;
    var x = this.exponent_0;
    return 1 - Math_0.pow($receiver, x);
  };
  PolyOut.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'PolyOut',
    interfaces: []
  };
  function PolyInOut(exponent) {
    if (exponent === void 0)
      exponent = 3.0;
    this.exponent_0 = exponent;
  }
  PolyInOut.prototype.exponent_14dthe$ = function (exponent) {
    return new PolyInOut(exponent);
  };
  PolyInOut.prototype.invoke_14dthe$ = function (t) {
    var it = t * 2;
    var tmp$;
    if (it <= 1) {
      var x = this.exponent_0;
      tmp$ = Math_0.pow(it, x);
    }
     else {
      var $receiver = 2 - it;
      var x_0 = this.exponent_0;
      tmp$ = 2 - Math_0.pow($receiver, x_0);
    }
    return tmp$ / 2;
  };
  PolyInOut.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'PolyInOut',
    interfaces: []
  };
  Object.defineProperty(ease, 'Companion', {
    get: ease$Companion_getInstance
  });
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$ease = package$data2viz.ease || (package$data2viz.ease = {});
  package$ease.ease = ease;
  package$ease.ElasticIn = ElasticIn;
  package$ease.ElasticOut = ElasticOut;
  package$ease.ElasticInOut = ElasticInOut;
  package$ease.BackIn = BackIn;
  package$ease.BackOut = BackOut;
  package$ease.BackInOut = BackInOut;
  package$ease.PolyIn = PolyIn;
  package$ease.PolyOut = PolyOut;
  package$ease.PolyInOut = PolyInOut;
  tau = 2 * math.PI;
  halfPi = math.PI / 2;
  b1 = 4.0 / 11;
  b2 = 6.0 / 11;
  b3 = 8.0 / 11;
  b4 = 3.0 / 4;
  b5 = 9.0 / 11;
  b6 = 10.0 / 11;
  b7 = 15.0 / 16;
  b8 = 21.0 / 22;
  b9 = 63.0 / 64;
  b0 = 1.0 / b1 / b1;
  Kotlin.defineModule('d2v-ease-js', _);
  return _;
}));

//# sourceMappingURL=d2v-ease-js.js.map
