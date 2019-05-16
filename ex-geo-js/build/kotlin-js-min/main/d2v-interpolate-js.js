(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin', 'd2v-core-js', 'd2v-color-js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'), require('d2v-core-js'), require('d2v-color-js'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'd2v-interpolate-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'd2v-interpolate-js'.");
    }
    if (typeof this['d2v-core-js'] === 'undefined') {
      throw new Error("Error loading module 'd2v-interpolate-js'. Its dependency 'd2v-core-js' was not found. Please, check whether 'd2v-core-js' is loaded prior to 'd2v-interpolate-js'.");
    }
    if (typeof this['d2v-color-js'] === 'undefined') {
      throw new Error("Error loading module 'd2v-interpolate-js'. Its dependency 'd2v-color-js' was not found. Please, check whether 'd2v-color-js' is loaded prior to 'd2v-interpolate-js'.");
    }
    root['d2v-interpolate-js'] = factory(typeof this['d2v-interpolate-js'] === 'undefined' ? {} : this['d2v-interpolate-js'], kotlin, this['d2v-core-js'], this['d2v-color-js']);
  }
}(this, function (_, Kotlin, $module$d2v_core_js, $module$d2v_color_js) {
  'use strict';
  var numberToInt = Kotlin.numberToInt;
  var IntRange = Kotlin.kotlin.ranges.IntRange;
  var coerceIn = Kotlin.kotlin.ranges.coerceIn_nayhkp$;
  var math = $module$d2v_core_js.io.data2viz.math;
  var coerceAtMost = Kotlin.kotlin.ranges.coerceAtMost_38ydlf$;
  var color = $module$d2v_color_js.io.data2viz.color;
  var Angle = $module$d2v_core_js.io.data2viz.math.Angle;
  var Percent = $module$d2v_core_js.io.data2viz.math.Percent;
  var round = Kotlin.kotlin.math.round_14dthe$;
  var get_pct = $module$d2v_core_js.io.data2viz.math.get_pct_rcaex3$;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var Comparator = Kotlin.kotlin.Comparator;
  var roundToInt = Kotlin.kotlin.math.roundToInt_yrwdxr$;
  var getCallableRef = Kotlin.getCallableRef;
  function computeSpline(t1, v0, v1, v2, v3) {
    var t2 = t1 * t1;
    var t3 = t2 * t1;
    return ((1 - 3 * t1 + 3 * t2 - t3) * v0 + (4 - 6 * t2 + 3 * t3) * v1 + (1 + 3 * t1 + 3 * t2 - 3 * t3) * v2 + t3 * v3) / 6;
  }
  var Math_0 = Math;
  function basis$lambda(closure$n, closure$values) {
    return function (percent) {
      var x = percent.times_3p81yu$(closure$n);
      var currentIndex = coerceIn(numberToInt(Math_0.floor(x)), new IntRange(0, closure$n - 1 | 0));
      var v1 = closure$values.get_za3lpa$(currentIndex);
      var v2 = closure$values.get_za3lpa$(currentIndex + 1 | 0);
      var v0 = currentIndex > 0 ? closure$values.get_za3lpa$(currentIndex - 1 | 0) : (2 * v1 | 0) - v2 | 0;
      var v3 = currentIndex < (closure$n - 1 | 0) ? closure$values.get_za3lpa$(currentIndex + 2 | 0) : (2 * v2 | 0) - v1 | 0;
      return computeSpline((percent.coerceToDefault().value - currentIndex / closure$n) * closure$n, v0, v1, v2, v3);
    };
  }
  function basis(values) {
    var n = values.size - 1 | 0;
    return basis$lambda(n, values);
  }
  function basisClosed$lambda(closure$n, closure$values) {
    return function (percent) {
      var newT = percent.value < 0 ? percent.value % 1 : percent.value % 1 + 1;
      var x = newT * closure$n;
      var currentIndex = numberToInt(Math_0.floor(x));
      var v0 = closure$values.get_za3lpa$((currentIndex + closure$n - 1 | 0) % closure$n);
      var v1 = closure$values.get_za3lpa$(currentIndex % closure$n);
      var v2 = closure$values.get_za3lpa$((currentIndex + 1 | 0) % closure$n);
      var v3 = closure$values.get_za3lpa$((currentIndex + 2 | 0) % closure$n);
      return computeSpline((newT - (currentIndex / closure$n | 0)) * closure$n, v0, v1, v2, v3);
    };
  }
  function basisClosed(values) {
    var n = values.size;
    return basisClosed$lambda(n, values);
  }
  function gamma$lambda(closure$gamma) {
    return function (a, b) {
      return closure$gamma === 1.0 ? linearClamped(a, b - a) : exponential(a, b, closure$gamma);
    };
  }
  function gamma(gamma) {
    if (gamma === void 0)
      gamma = 1.0;
    return gamma$lambda(gamma);
  }
  function interpolateHue$lambda(closure$a2, closure$diff, closure$long) {
    return function (t) {
      if (!closure$long && closure$diff < -math.PI)
        return linearClamped(closure$a2.rad, closure$diff + math.TAU)(t);
      else if (!closure$long && closure$diff > math.PI)
        return linearClamped(closure$a2.rad, closure$diff - math.TAU)(t);
      else
        return linearClamped(closure$a2.rad, closure$diff)(t);
    };
  }
  function interpolateHue(from, to, long) {
    if (long === void 0)
      long = false;
    var a2 = from.normalize();
    var b2 = to.normalize();
    var diff = b2.rad - a2.rad;
    return interpolateHue$lambda(a2, diff, long);
  }
  function linearClamped$lambda(closure$a, closure$b) {
    return function (t) {
      return closure$a + t.coerceToDefault().value * closure$b;
    };
  }
  function linearClamped(a, b) {
    return linearClamped$lambda(a, b);
  }
  function exponential$lambda(closure$na, closure$nb, closure$ny) {
    return function (t) {
      var $receiver = closure$na + t.value * closure$nb;
      var x = closure$ny;
      return Math_0.pow($receiver, x);
    };
  }
  function exponential(a, b, y) {
    var ny = 1 / y;
    var na = Math_0.pow(a, y);
    var nb = Math_0.pow(b, y) - na;
    return exponential$lambda(na, nb, ny);
  }
  function getSplineInterpolator$lambda(a) {
    return basisClosed(a);
  }
  function getSplineInterpolator$lambda_0(a) {
    return basis(a);
  }
  function getSplineInterpolator(cyclical) {
    if (cyclical)
      return getSplineInterpolator$lambda;
    else
      return getSplineInterpolator$lambda_0;
  }
  function quad(x) {
    return x * x;
  }
  function cubicIn(x) {
    return x * x * x;
  }
  function cubicOut(t) {
    return (t - 1) * (t - 1) * (t - 1) + 1;
  }
  function cubicInOut(t) {
    return (t <= 0.5 ? 8 * t * t * t : (2 * t - 2) * (2 * t - 2) * (2 * t - 2) + 2) / 2;
  }
  function sin(x) {
    var x_0 = x * math.HALFPI_ANGLE.rad;
    return 1 - Math_0.cos(x_0);
  }
  function poly$lambda(closure$e) {
    return function (t) {
      var x = closure$e;
      return Math_0.pow(t, x);
    };
  }
  function poly(e) {
    return poly$lambda(e);
  }
  function circleIn(t) {
    var x = 1 - coerceAtMost(t * t, 1.0);
    return 1 - Math_0.sqrt(x);
  }
  function circleOut(t) {
    var x = 1 - (t - 1) * (t - 1);
    return Math_0.sqrt(x);
  }
  function interpolateHcl$lambda(closure$h, closure$c, closure$l) {
    return function (percent) {
      return color.Colors.hcl_vn5x52$(new Angle(closure$h(percent)), closure$c(percent), new Percent(closure$l(percent)));
    };
  }
  function interpolateHcl(start, end, long) {
    var startHCL = start.toHcl();
    var endHCL = end.toHcl();
    var colorInterpolator = gamma();
    if (startHCL.isAchromatic())
      startHCL = color.Colors.hcl_vn5x52$(endHCL.h, startHCL.c, startHCL.l, startHCL.alpha);
    if (endHCL.isAchromatic())
      endHCL = color.Colors.hcl_vn5x52$(startHCL.h, endHCL.c, endHCL.l, endHCL.alpha);
    var h = interpolateHue(startHCL.h, endHCL.h, long);
    var c = colorInterpolator(startHCL.c, endHCL.c);
    var l = colorInterpolator(startHCL.l.value, endHCL.l.value);
    return interpolateHcl$lambda(h, c, l);
  }
  function hclLongInterpolator(start, end) {
    return interpolateHcl(start, end, true);
  }
  function hclInterpolator(start, end) {
    return interpolateHcl(start, end, false);
  }
  function interpolateHsl$lambda(closure$h, closure$s, closure$l) {
    return function (percent) {
      return color.Colors.hsl_wqq93y$(new Angle(closure$h(percent)), new Percent(closure$s(percent)), new Percent(closure$l(percent)));
    };
  }
  function interpolateHsl(start, end, long) {
    var startHSL = start.toHsl();
    var endHSL = end.toHsl();
    var colorInterpolator = gamma();
    if (startHSL.isAchromatic())
      startHSL = color.Colors.hsl_wqq93y$(endHSL.h, endHSL.s, startHSL.l, startHSL.alpha);
    if (endHSL.isAchromatic())
      endHSL = color.Colors.hsl_wqq93y$(startHSL.h, startHSL.s, endHSL.l, endHSL.alpha);
    var h = interpolateHue(startHSL.h, endHSL.h, long);
    var s = colorInterpolator(startHSL.s.value, endHSL.s.value);
    var l = colorInterpolator(startHSL.l.value, endHSL.l.value);
    return interpolateHsl$lambda(h, s, l);
  }
  function hslLongInterpolator(start, end) {
    return interpolateHsl(start, end, true);
  }
  function hslInterpolator(start, end) {
    return interpolateHsl(start, end, false);
  }
  function interpolateLab$lambda(closure$l, closure$a, closure$b) {
    return function (percent) {
      return color.Colors.lab_tuy7uw$(new Percent(closure$l(percent)), closure$a(percent), closure$b(percent));
    };
  }
  function interpolateLab(start, end) {
    var startLab = start.toLab();
    var endLab = end.toLab();
    var colorInterpolator = gamma();
    var l = colorInterpolator(startLab.labL.value, endLab.labL.value);
    var a = colorInterpolator(startLab.labA, endLab.labA);
    var b = colorInterpolator(startLab.labB, endLab.labB);
    return interpolateLab$lambda(l, a, b);
  }
  function labInterpolator(start, end) {
    return interpolateLab(start, end);
  }
  function interpolateNumber$lambda(closure$start, closure$diff) {
    return function (percent) {
      return closure$start + percent.value * closure$diff;
    };
  }
  function interpolateNumber(start, end) {
    var diff = end - start;
    return interpolateNumber$lambda(start, diff);
  }
  function interpolateRound$lambda(closure$start, closure$diff) {
    return function (percent) {
      return round(closure$start + percent.value * closure$diff);
    };
  }
  function interpolateRound(start, end) {
    var diff = end - start;
    return interpolateRound$lambda(start, diff);
  }
  function uninterpolateNumber$lambda(closure$start, closure$diff) {
    return function (double) {
      return new Percent((double - closure$start) / closure$diff);
    };
  }
  function uninterpolateNumber$lambda_0(f) {
    return get_pct(0);
  }
  function uninterpolateNumber(start, end) {
    var diff = end - start;
    return diff !== 0.0 ? uninterpolateNumber$lambda(start, diff) : uninterpolateNumber$lambda_0;
  }
  function identity(percent) {
    return percent.value;
  }
  function PointComparatorX() {
  }
  PointComparatorX.prototype.compare = function (a, b) {
    return Kotlin.compareTo(a.x, b.x);
  };
  PointComparatorX.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'PointComparatorX',
    interfaces: [Comparator]
  };
  function PointComparatorY() {
  }
  PointComparatorY.prototype.compare = function (a, b) {
    return Kotlin.compareTo(a.x, b.x);
  };
  PointComparatorY.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'PointComparatorY',
    interfaces: [Comparator]
  };
  function interpolatePoint$lambda(closure$start, closure$diff) {
    return function (percent) {
      return closure$start.plus_mowjvf$(closure$diff.times_3p81yu$(percent.value));
    };
  }
  function interpolatePoint(start, end) {
    var diff = end.minus_mowjvf$(start);
    return interpolatePoint$lambda(start, diff);
  }
  function uninterpolatePointOnX$lambda(closure$start, closure$diff) {
    return function (point) {
      return new Percent((point.x - closure$start.x) / closure$diff);
    };
  }
  function uninterpolatePointOnX$lambda_0(f) {
    return get_pct(0);
  }
  function uninterpolatePointOnX(start, end) {
    var diff = end.x - start.x;
    return diff !== 0.0 ? uninterpolatePointOnX$lambda(start, diff) : uninterpolatePointOnX$lambda_0;
  }
  function uninterpolatePointOnY$lambda(closure$start, closure$diff) {
    return function (point) {
      return new Percent((point.y - closure$start.y) / closure$diff);
    };
  }
  function uninterpolatePointOnY$lambda_0(f) {
    return get_pct(0);
  }
  function uninterpolatePointOnY(start, end) {
    var diff = end.y - start.y;
    return diff !== 0.0 ? uninterpolatePointOnY$lambda(start, diff) : uninterpolatePointOnY$lambda_0;
  }
  var pi_1_3;
  var pi_2_3;
  function interpolateRgb$lambda(closure$r, closure$g, closure$b) {
    return function (percent) {
      return color.Colors.rgb_o6sw6o$(roundToInt(closure$r(percent)), roundToInt(closure$g(percent)), roundToInt(closure$b(percent)));
    };
  }
  function interpolateRgb(start, end, gamma_0) {
    if (gamma_0 === void 0)
      gamma_0 = 1.0;
    var interpolator = gamma(gamma_0);
    var r = interpolator(start.r, end.r);
    var g = interpolator(start.g, end.g);
    var b = interpolator(start.b, end.b);
    return interpolateRgb$lambda(r, g, b);
  }
  function lRGBInterpolator$lambda(closure$start, closure$end) {
    return function (it) {
      var percent = it.coerceToDefault();
      var $receiver = closure$start;
      var tmp$ = Math_0.pow($receiver, 2) * (1 - percent.value);
      var $receiver_0 = closure$end;
      var x = tmp$ + Math_0.pow($receiver_0, 2) * percent.value;
      return Math_0.sqrt(x);
    };
  }
  function lRGBInterpolator(start, end) {
    return lRGBInterpolator$lambda(start, end);
  }
  function interpolateLRgb$lambda(closure$r, closure$g, closure$b) {
    return function (percent) {
      return color.Colors.rgb_o6sw6o$(roundToInt(closure$r(percent)), roundToInt(closure$g(percent)), roundToInt(closure$b(percent)));
    };
  }
  function interpolateLRgb(start, end) {
    var r = lRGBInterpolator(start.r, end.r);
    var g = lRGBInterpolator(start.g, end.g);
    var b = lRGBInterpolator(start.b, end.b);
    return interpolateLRgb$lambda(r, g, b);
  }
  function interpolateRgbBasis$lambda(closure$r, closure$g, closure$b) {
    return function (percent) {
      return color.Colors.rgb_o6sw6o$(roundToInt(closure$r(percent)), roundToInt(closure$g(percent)), roundToInt(closure$b(percent)));
    };
  }
  var collectionSizeOrDefault = Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$;
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_ww73n8$;
  function interpolateRgbBasis(colorsList, cyclical) {
    if (cyclical === void 0)
      cyclical = false;
    var spline = getSplineInterpolator(cyclical);
    var destination = ArrayList_init(collectionSizeOrDefault(colorsList, 10));
    var tmp$;
    tmp$ = colorsList.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(item.r);
    }
    var r = spline(destination);
    var destination_0 = ArrayList_init(collectionSizeOrDefault(colorsList, 10));
    var tmp$_0;
    tmp$_0 = colorsList.iterator();
    while (tmp$_0.hasNext()) {
      var item_0 = tmp$_0.next();
      destination_0.add_11rb$(item_0.g);
    }
    var g = spline(destination_0);
    var destination_1 = ArrayList_init(collectionSizeOrDefault(colorsList, 10));
    var tmp$_1;
    tmp$_1 = colorsList.iterator();
    while (tmp$_1.hasNext()) {
      var item_1 = tmp$_1.next();
      destination_1.add_11rb$(item_1.b);
    }
    var b = spline(destination_1);
    return interpolateRgbBasis$lambda(r, g, b);
  }
  function percentToSinebow(percent) {
    var t = (0.5 - percent.value) * math.PI;
    var x = Math_0.sin(t);
    var r = roundToInt(255 * x * x);
    var x_0 = t + pi_1_3;
    x = Math_0.sin(x_0);
    var g = roundToInt(255 * x * x);
    var x_1 = t + pi_2_3;
    x = Math_0.sin(x_1);
    var b = roundToInt(255 * x * x);
    return color.Colors.rgb_o6sw6o$(r, g, b);
  }
  function rgbBasisInterpolator(colors) {
    return interpolateRgbBasis(colors, false);
  }
  function rgbBasisClosedInterpolator(colors) {
    return interpolateRgbBasis(colors, true);
  }
  function rgbDefaultInterpolator(start, end) {
    return interpolateRgb(start, end);
  }
  function rgbLinearInterpolator(start, end) {
    return interpolateLRgb(start, end);
  }
  function rgbSineBowInterpolator() {
    return getCallableRef('percentToSinebow', function (percent) {
      return percentToSinebow(percent);
    });
  }
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$interpolate = package$data2viz.interpolate || (package$data2viz.interpolate = {});
  package$interpolate.computeSpline_vrk9a6$ = computeSpline;
  package$interpolate.basis_pqoyrt$ = basis;
  package$interpolate.basisClosed_pqoyrt$ = basisClosed;
  package$interpolate.gamma_tq0o01$ = gamma;
  package$interpolate.interpolateHue_totmiw$ = interpolateHue;
  package$interpolate.getSplineInterpolator_vft4zs$ = getSplineInterpolator;
  package$interpolate.quad_14dthe$ = quad;
  package$interpolate.cubicIn_14dthe$ = cubicIn;
  package$interpolate.cubicOut_14dthe$ = cubicOut;
  package$interpolate.cubicInOut_14dthe$ = cubicInOut;
  package$interpolate.sin_14dthe$ = sin;
  package$interpolate.poly_14dthe$ = poly;
  package$interpolate.circleIn_14dthe$ = circleIn;
  package$interpolate.circleOut_14dthe$ = circleOut;
  package$interpolate.hclLongInterpolator_lu7xrq$ = hclLongInterpolator;
  package$interpolate.hclInterpolator_lu7xrq$ = hclInterpolator;
  package$interpolate.hslLongInterpolator_lu7xrq$ = hslLongInterpolator;
  package$interpolate.hslInterpolator_lu7xrq$ = hslInterpolator;
  package$interpolate.labInterpolator_lu7xrq$ = labInterpolator;
  package$interpolate.interpolateNumber_lu1900$ = interpolateNumber;
  package$interpolate.interpolateRound_lu1900$ = interpolateRound;
  package$interpolate.uninterpolateNumber_lu1900$ = uninterpolateNumber;
  package$interpolate.identity_o5f5ne$ = identity;
  package$interpolate.PointComparatorX = PointComparatorX;
  package$interpolate.PointComparatorY = PointComparatorY;
  package$interpolate.interpolatePoint_840z2k$ = interpolatePoint;
  package$interpolate.uninterpolatePointOnX_840z2k$ = uninterpolatePointOnX;
  package$interpolate.uninterpolatePointOnY_840z2k$ = uninterpolatePointOnY;
  package$interpolate.rgbBasisInterpolator_o5f480$ = rgbBasisInterpolator;
  package$interpolate.rgbBasisClosedInterpolator_o5f480$ = rgbBasisClosedInterpolator;
  package$interpolate.rgbDefaultInterpolator_lu7xrq$ = rgbDefaultInterpolator;
  package$interpolate.rgbLinearInterpolator_lu7xrq$ = rgbLinearInterpolator;
  package$interpolate.rgbSineBowInterpolator = rgbSineBowInterpolator;
  pi_1_3 = math.PI / 3.0;
  pi_2_3 = math.PI * 2.0 / 3.0;
  Kotlin.defineModule('d2v-interpolate-js', _);
  return _;
}));

//# sourceMappingURL=d2v-interpolate-js.js.map
