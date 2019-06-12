(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin', 'd2v-interpolate-js', 'd2v-core-js', 'd2v-color-js', 'd2v-time-js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'), require('d2v-interpolate-js'), require('d2v-core-js'), require('d2v-color-js'), require('d2v-time-js'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'd2v-scale-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'd2v-scale-js'.");
    }
    if (typeof this['d2v-interpolate-js'] === 'undefined') {
      throw new Error("Error loading module 'd2v-scale-js'. Its dependency 'd2v-interpolate-js' was not found. Please, check whether 'd2v-interpolate-js' is loaded prior to 'd2v-scale-js'.");
    }
    if (typeof this['d2v-core-js'] === 'undefined') {
      throw new Error("Error loading module 'd2v-scale-js'. Its dependency 'd2v-core-js' was not found. Please, check whether 'd2v-core-js' is loaded prior to 'd2v-scale-js'.");
    }
    if (typeof this['d2v-color-js'] === 'undefined') {
      throw new Error("Error loading module 'd2v-scale-js'. Its dependency 'd2v-color-js' was not found. Please, check whether 'd2v-color-js' is loaded prior to 'd2v-scale-js'.");
    }
    if (typeof this['d2v-time-js'] === 'undefined') {
      throw new Error("Error loading module 'd2v-scale-js'. Its dependency 'd2v-time-js' was not found. Please, check whether 'd2v-time-js' is loaded prior to 'd2v-scale-js'.");
    }
    root['d2v-scale-js'] = factory(typeof this['d2v-scale-js'] === 'undefined' ? {} : this['d2v-scale-js'], kotlin, this['d2v-interpolate-js'], this['d2v-core-js'], this['d2v-color-js'], this['d2v-time-js']);
  }
}(this, function (_, Kotlin, $module$d2v_interpolate_js, $module$d2v_core_js, $module$d2v_color_js, $module$d2v_time_js) {
  'use strict';
  var rangeTo = Kotlin.kotlin.ranges.rangeTo_38ydlf$;
  var coerceIn = Kotlin.kotlin.ranges.coerceIn_52zmhz$;
  var round = Kotlin.kotlin.math.round_14dthe$;
  var reverse = Kotlin.kotlin.collections.reverse_4b5429$;
  var addAll = Kotlin.kotlin.collections.addAll_ye1y7v$;
  var kotlin_js_internal_DoubleCompanionObject = Kotlin.kotlin.js.internal.DoubleCompanionObject;
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var Math_0 = Math;
  var Array_0 = Array;
  var interpolateNumber = $module$d2v_interpolate_js.io.data2viz.interpolate.interpolateNumber_lu1900$;
  var uninterpolateNumber = $module$d2v_interpolate_js.io.data2viz.interpolate.uninterpolateNumber_lu1900$;
  var tickStep = $module$d2v_core_js.io.data2viz.math.tickStep_syxxoe$;
  var first = Kotlin.kotlin.collections.first_2p1efm$;
  var last = Kotlin.kotlin.collections.last_2p1efm$;
  var ticks = $module$d2v_core_js.io.data2viz.math.ticks_1stjjm$;
  var naturalOrder = Kotlin.kotlin.comparisons.naturalOrder_dahdeg$;
  var listOf = Kotlin.kotlin.collections.listOf_i5x0yv$;
  var toList = Kotlin.kotlin.collections.toList_7wnvza$;
  var getCallableRef = Kotlin.getCallableRef;
  var IllegalStateException_init = Kotlin.kotlin.IllegalStateException_init;
  var ensureNotNull = Kotlin.ensureNotNull;
  var reversed = Kotlin.kotlin.collections.reversed_7wnvza$;
  var IllegalStateException_init_0 = Kotlin.kotlin.IllegalStateException_init_pdl1vj$;
  var IllegalArgumentException_init = Kotlin.kotlin.IllegalArgumentException_init_pdl1vj$;
  var Percent = $module$d2v_core_js.io.data2viz.math.Percent;
  var get_pct = $module$d2v_core_js.io.data2viz.math.get_pct_rcaex3$;
  var toMutableList = Kotlin.kotlin.collections.toMutableList_4c7yge$;
  var log = Kotlin.kotlin.math.log_lu1900$;
  var isNaN_0 = Kotlin.kotlin.isNaN_yrwdxr$;
  var numberToInt = Kotlin.numberToInt;
  var ArrayList = Kotlin.kotlin.collections.ArrayList;
  var throwCCE = Kotlin.throwCCE;
  var arrayListOf = Kotlin.kotlin.collections.arrayListOf_i5x0yv$;
  var collectionSizeOrDefault = Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$;
  var ArrayList_init_0 = Kotlin.kotlin.collections.ArrayList_init_ww73n8$;
  var HashMap_init = Kotlin.kotlin.collections.HashMap_init_q3lmfv$;
  var emptyList = Kotlin.kotlin.collections.emptyList_287e2$;
  var sorted = Kotlin.kotlin.collections.sorted_exjks8$;
  var Kind_INTERFACE = Kotlin.Kind.INTERFACE;
  var first_0 = Kotlin.kotlin.collections.first_us0mfu$;
  var last_0 = Kotlin.kotlin.collections.last_us0mfu$;
  var Unit = Kotlin.kotlin.Unit;
  var interpolateRound = $module$d2v_interpolate_js.io.data2viz.interpolate.interpolateRound_lu1900$;
  var interpolatePoint = $module$d2v_interpolate_js.io.data2viz.interpolate.interpolatePoint_840z2k$;
  var uninterpolatePointOnX = $module$d2v_interpolate_js.io.data2viz.interpolate.uninterpolatePointOnX_840z2k$;
  var PointComparatorX = $module$d2v_interpolate_js.io.data2viz.interpolate.PointComparatorX;
  var Kind_OBJECT = Kotlin.Kind.OBJECT;
  var rgbLinearInterpolator = $module$d2v_interpolate_js.io.data2viz.interpolate.rgbLinearInterpolator_lu7xrq$;
  var rgbDefaultInterpolator = $module$d2v_interpolate_js.io.data2viz.interpolate.rgbDefaultInterpolator_lu7xrq$;
  var labInterpolator = $module$d2v_interpolate_js.io.data2viz.interpolate.labInterpolator_lu7xrq$;
  var hclInterpolator = $module$d2v_interpolate_js.io.data2viz.interpolate.hclInterpolator_lu7xrq$;
  var hclLongInterpolator = $module$d2v_interpolate_js.io.data2viz.interpolate.hclLongInterpolator_lu7xrq$;
  var hslInterpolator = $module$d2v_interpolate_js.io.data2viz.interpolate.hslInterpolator_lu7xrq$;
  var hslLongInterpolator = $module$d2v_interpolate_js.io.data2viz.interpolate.hslLongInterpolator_lu7xrq$;
  var EncodedColors = $module$d2v_color_js.io.data2viz.color.EncodedColors;
  var rgbBasisInterpolator = $module$d2v_interpolate_js.io.data2viz.interpolate.rgbBasisInterpolator_o5f480$;
  var rgbSineBowInterpolator = $module$d2v_interpolate_js.io.data2viz.interpolate.rgbSineBowInterpolator;
  var equals = Kotlin.equals;
  var listOf_0 = Kotlin.kotlin.collections.listOf_mh5how$;
  var time = $module$d2v_time_js.io.data2viz.time;
  var L0 = Kotlin.Long.ZERO;
  var date = $module$d2v_time_js.io.data2viz.time.date_fw2154$;
  var L1 = Kotlin.Long.ONE;
  var date_0 = $module$d2v_time_js.io.data2viz.time.date_ui44o2$;
  var Comparator = Kotlin.kotlin.Comparator;
  BandScale.prototype = Object.create(BandedScale.prototype);
  BandScale.prototype.constructor = BandScale;
  PointScale.prototype = Object.create(BandedScale.prototype);
  PointScale.prototype.constructor = PointScale;
  LinearScale.prototype = Object.create(ContinuousScale.prototype);
  LinearScale.prototype.constructor = LinearScale;
  LogScale.prototype = Object.create(LinearScale.prototype);
  LogScale.prototype.constructor = LogScale;
  PowerScale.prototype = Object.create(LinearScale.prototype);
  PowerScale.prototype.constructor = PowerScale;
  TimeScale.prototype = Object.create(ContinuousScale.prototype);
  TimeScale.prototype.constructor = TimeScale;
  function BandedScale(indexableDomain) {
    if (indexableDomain === void 0)
      indexableDomain = new IndexableDomain();
    this.indexableDomain_u45y1f$_0 = indexableDomain;
    this.unknown_plmz65$_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this._paddingInner = 0.0;
    this._paddingOuter = 0.0;
    this.range_ypyoio$_0 = intervalOf(0.0, 1.0);
    this.round_yxsewf$_0 = false;
    this.align_qu5nmg$_0 = 0.5;
    this.step_o22tfv$_0 = 1.0;
    this.bandwidth_ciwtgs$_0 = 1.0;
    this.ordinalRange_ft5nn9$_0 = ArrayList_init();
  }
  Object.defineProperty(BandedScale.prototype, 'domain', {
    get: function () {
      return this.indexableDomain_u45y1f$_0._domain_8be2vx$;
    },
    set: function (value) {
      this.indexableDomain_u45y1f$_0.domain = value;
      this.rescale();
    }
  });
  Object.defineProperty(BandedScale.prototype, 'range', {
    get: function () {
      return this.range_ypyoio$_0;
    },
    set: function (value) {
      this.range_ypyoio$_0 = value;
      this.rescale();
    }
  });
  Object.defineProperty(BandedScale.prototype, 'round', {
    get: function () {
      return this.round_yxsewf$_0;
    },
    set: function (value) {
      this.round_yxsewf$_0 = value;
      this.rescale();
    }
  });
  Object.defineProperty(BandedScale.prototype, 'align', {
    get: function () {
      return this.align_qu5nmg$_0;
    },
    set: function (value) {
      this.align_qu5nmg$_0 = coerceIn(value, rangeTo(0.0, 1.0));
      this.rescale();
    }
  });
  Object.defineProperty(BandedScale.prototype, 'step', {
    get: function () {
      return this.step_o22tfv$_0;
    },
    set: function (step) {
      this.step_o22tfv$_0 = step;
    }
  });
  Object.defineProperty(BandedScale.prototype, 'bandwidth', {
    get: function () {
      return this.bandwidth_ciwtgs$_0;
    },
    set: function (bandwidth) {
      this.bandwidth_ciwtgs$_0 = bandwidth;
    }
  });
  BandedScale.prototype.invoke_11rb$ = function (domainValue) {
    var tmp$;
    tmp$ = this.indexableDomain_u45y1f$_0.index_8be2vx$.get_11rb$(domainValue);
    if (tmp$ == null) {
      return this.unknown_plmz65$_0;
    }
    var i = tmp$;
    return this.ordinalRange_ft5nn9$_0.isEmpty() ? this.unknown_plmz65$_0 : this.ordinalRange_ft5nn9$_0.get_za3lpa$(i);
  };
  BandedScale.prototype.ticks_za3lpa$$default = function (count) {
    return this.domain;
  };
  BandedScale.prototype.rescale = function () {
    var n = this.indexableDomain_u45y1f$_0._domain_8be2vx$.size;
    var reverse_0 = this.range.end < this.range.start;
    var start = {v: reverse_0 ? this.range.end : this.range.start};
    var stop = reverse_0 ? this.range.start : this.range.end;
    var tmp$ = stop - start.v;
    var b = n - this._paddingInner + this._paddingOuter * 2;
    this.step = tmp$ / Math_0.max(1.0, b);
    if (this.round) {
      var x = this.step;
      this.step = Math_0.floor(x);
    }
    start.v += (stop - start.v - this.step * (n - this._paddingInner)) * this.align;
    this.bandwidth = this.step * (1 - this._paddingInner);
    if (this.round) {
      start.v = round(start.v);
      this.bandwidth = round(this.bandwidth);
    }
    var array = Array_0(n);
    var tmp$_0;
    tmp$_0 = array.length - 1 | 0;
    for (var i = 0; i <= tmp$_0; i++) {
      array[i] = start.v + this.step * i;
    }
    var values = array;
    if (reverse_0)
      reverse(values);
    this.ordinalRange_ft5nn9$_0.clear();
    addAll(this.ordinalRange_ft5nn9$_0, values);
  };
  BandedScale.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'BandedScale',
    interfaces: [StrictlyContinuousRange, Tickable, DiscreteDomain, Scale]
  };
  function BandScale() {
    BandedScale.call(this);
  }
  Object.defineProperty(BandScale.prototype, 'padding', {
    get: function () {
      return this._paddingInner;
    },
    set: function (value) {
      this._paddingInner = value;
      this._paddingOuter = value;
      this.rescale();
    }
  });
  Object.defineProperty(BandScale.prototype, 'paddingInner', {
    get: function () {
      return this._paddingInner;
    },
    set: function (value) {
      this._paddingInner = coerceIn(value, rangeTo(0.0, 1.0));
      this.rescale();
    }
  });
  Object.defineProperty(BandScale.prototype, 'paddingOuter', {
    get: function () {
      return this._paddingOuter;
    },
    set: function (value) {
      this._paddingOuter = coerceIn(value, rangeTo(0.0, 1.0));
      this.rescale();
    }
  });
  BandScale.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'BandScale',
    interfaces: [BandedScale]
  };
  function PointScale() {
    BandedScale.call(this);
    this._paddingInner = 1.0;
    this.rescale();
  }
  Object.defineProperty(PointScale.prototype, 'padding', {
    get: function () {
      return this._paddingOuter;
    },
    set: function (value) {
      this._paddingOuter = value;
      this.rescale();
    }
  });
  PointScale.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'PointScale',
    interfaces: [BandedScale]
  };
  function LinearScale(interpolateRange, uninterpolateRange, rangeComparator) {
    if (uninterpolateRange === void 0)
      uninterpolateRange = null;
    if (rangeComparator === void 0)
      rangeComparator = null;
    ContinuousScale.call(this, interpolateRange, uninterpolateRange, rangeComparator);
    this.comparator = naturalOrder();
    this._domain.clear();
    this._domain.addAll_brywnq$(listOf([0.0, 1.0]));
  }
  LinearScale.prototype.interpolateDomain_xwzc9q$ = function (from, to) {
    return interpolateNumber(from, to);
  };
  LinearScale.prototype.uninterpolateDomain_xwzc9q$ = function (from, to) {
    return uninterpolateNumber(from, to);
  };
  LinearScale.prototype.domainComparator = function () {
    return this.comparator;
  };
  LinearScale.prototype.invoke_za3lpa$ = function (domainValue) {
    return this.invoke_11rb$(domainValue);
  };
  LinearScale.prototype.nice_za3lpa$$default = function (count) {
    var last = this._domain.size - 1 | 0;
    var step = tickStep(this._domain.get_za3lpa$(0), this._domain.get_za3lpa$(last), count);
    var x = this._domain.get_za3lpa$(0) / step;
    var start = Math_0.floor(x) * step;
    var x_0 = this._domain.get_za3lpa$(last) / step;
    var stop = Math_0.ceil(x_0) * step;
    if (step !== 0.0) {
      step = tickStep(start, stop, count);
      var tmp$ = this._domain;
      var x_1 = start / step;
      tmp$.set_wxm5ur$(0, Math_0.floor(x_1) * step);
      var tmp$_0 = this._domain;
      var x_2 = stop / step;
      tmp$_0.set_wxm5ur$(last, Math_0.ceil(x_2) * step);
      this.rescale();
    }
  };
  LinearScale.prototype.ticks_za3lpa$$default = function (count) {
    return ticks(first(this._domain), last(this._domain), count);
  };
  LinearScale.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'LinearScale',
    interfaces: [NiceableScale, Tickable, ContinuousScale]
  };
  function ContinuousScale(interpolateRange, uninterpolateRange, rangeComparator) {
    if (uninterpolateRange === void 0)
      uninterpolateRange = null;
    if (rangeComparator === void 0)
      rangeComparator = null;
    this.interpolateRange = interpolateRange;
    this.uninterpolateRange = uninterpolateRange;
    this.rangeComparator = rangeComparator;
    this.rangeToDomain_wybcqs$_0 = null;
    this.domainToRange_mi7vq2$_0 = null;
    this._domain = ArrayList_init();
    this._range = ArrayList_init();
    this.clamp_v0itnn$_0 = false;
  }
  Object.defineProperty(ContinuousScale.prototype, 'clamp', {
    get: function () {
      return this.clamp_v0itnn$_0;
    },
    set: function (value) {
      this.clamp_v0itnn$_0 = value;
      this.rescale();
    }
  });
  Object.defineProperty(ContinuousScale.prototype, 'domain', {
    get: function () {
      return toList(this._domain);
    },
    set: function (value) {
      this._domain.clear();
      this._domain.addAll_brywnq$(value);
      this.rescale();
    }
  });
  Object.defineProperty(ContinuousScale.prototype, 'range', {
    get: function () {
      return toList(this._range);
    },
    set: function (value) {
      this._range.clear();
      this._range.addAll_brywnq$(value);
      this.rescale();
    }
  });
  ContinuousScale.prototype.invoke_11rb$ = function (domainValue) {
    var tmp$, tmp$_0;
    if (this.domainToRange_mi7vq2$_0 == null) {
      if (!(this._domain.size === this._range.size)) {
        var message = 'Domains (in) and Ranges (out) must have the same size.';
        throw IllegalStateException_init_0(message.toString());
      }
      var uninterpolateFunc = this.clamp ? this.uninterpolateClamp_mkwol2$_0(getCallableRef('uninterpolateDomain', function ($receiver, from, to) {
        return $receiver.uninterpolateDomain_xwzc9q$(from, to);
      }.bind(null, this))) : getCallableRef('uninterpolateDomain', function ($receiver, from, to) {
        return $receiver.uninterpolateDomain_xwzc9q$(from, to);
      }.bind(null, this));
      this.domainToRange_mi7vq2$_0 = this._domain.size > 2 ? this.polymap_z4navf$_0(uninterpolateFunc) : this.bimap_bn0tgw$_0(uninterpolateFunc);
    }
    tmp$_0 = (tmp$ = this.domainToRange_mi7vq2$_0) != null ? tmp$(domainValue) : null;
    if (tmp$_0 == null) {
      throw IllegalStateException_init();
    }
    return tmp$_0;
  };
  ContinuousScale.prototype.invert_11rc$ = function (rangeValue) {
    var tmp$, tmp$_0;
    if (this.uninterpolateRange == null) {
      var message = 'No de-interpolation function for range has been found for this scale. Invert operation is impossible.';
      throw IllegalStateException_init_0(message.toString());
    }
    if (this.rangeToDomain_wybcqs$_0 == null) {
      if (!(this._domain.size === this._range.size)) {
        var message_0 = 'Domains (in) and Ranges (out) must have the same size.';
        throw IllegalStateException_init_0(message_0.toString());
      }
      var interpolateFunc = this.clamp ? this.interpolateClamp_6w93zt$_0(getCallableRef('interpolateDomain', function ($receiver, from, to) {
        return $receiver.interpolateDomain_xwzc9q$(from, to);
      }.bind(null, this))) : getCallableRef('interpolateDomain', function ($receiver, from, to) {
        return $receiver.interpolateDomain_xwzc9q$(from, to);
      }.bind(null, this));
      this.rangeToDomain_wybcqs$_0 = this._domain.size > 2 || this._range.size > 2 ? this.polymapInvert_1hhkox$_0(interpolateFunc, ensureNotNull(this.uninterpolateRange)) : this.bimapInvert_bfo0f0$_0(interpolateFunc, ensureNotNull(this.uninterpolateRange));
    }
    tmp$_0 = (tmp$ = this.rangeToDomain_wybcqs$_0) != null ? tmp$(rangeValue) : null;
    if (tmp$_0 == null) {
      throw IllegalStateException_init();
    }
    return tmp$_0;
  };
  ContinuousScale.prototype.rescale = function () {
    this.rangeToDomain_wybcqs$_0 = null;
    this.domainToRange_mi7vq2$_0 = null;
  };
  function ContinuousScale$uninterpolateClamp$lambda$lambda(closure$d) {
    return function (value) {
      return closure$d(value).coerceToDefault();
    };
  }
  function ContinuousScale$uninterpolateClamp$lambda(closure$uninterpolateFunction) {
    return function (a, b) {
      var d = closure$uninterpolateFunction(a, b);
      return ContinuousScale$uninterpolateClamp$lambda$lambda(d);
    };
  }
  ContinuousScale.prototype.uninterpolateClamp_mkwol2$_0 = function (uninterpolateFunction) {
    return ContinuousScale$uninterpolateClamp$lambda(uninterpolateFunction);
  };
  function ContinuousScale$interpolateClamp$lambda$lambda(closure$r) {
    return function (value) {
      return closure$r(value.coerceToDefault());
    };
  }
  function ContinuousScale$interpolateClamp$lambda(closure$interpolateFunction) {
    return function (a, b) {
      var r = closure$interpolateFunction(a, b);
      return ContinuousScale$interpolateClamp$lambda$lambda(r);
    };
  }
  ContinuousScale.prototype.interpolateClamp_6w93zt$_0 = function (interpolateFunction) {
    return ContinuousScale$interpolateClamp$lambda(interpolateFunction);
  };
  function ContinuousScale$bimap$lambda(closure$r, closure$d) {
    return function (x) {
      return closure$r(closure$d(x));
    };
  }
  ContinuousScale.prototype.bimap_bn0tgw$_0 = function (deinterpolateDomain) {
    var d0 = this._domain.get_za3lpa$(0);
    var d1 = this._domain.get_za3lpa$(1);
    var r0 = this._range.get_za3lpa$(0);
    var r1 = this._range.get_za3lpa$(1);
    var r;
    var d;
    if (this.domainComparator().compare(d1, d0) < 0) {
      d = deinterpolateDomain(d1, d0);
      r = this.interpolateRange(r1, r0);
    }
     else {
      d = deinterpolateDomain(d0, d1);
      r = this.interpolateRange(r0, r1);
    }
    return ContinuousScale$bimap$lambda(r, d);
  };
  function ContinuousScale$bimapInvert$lambda(closure$d, closure$r) {
    return function (x) {
      return closure$d(closure$r(x));
    };
  }
  ContinuousScale.prototype.bimapInvert_bfo0f0$_0 = function (reinterpolateDomain, deinterpolateRange) {
    if (this.rangeComparator == null) {
      var message = 'No RangeComparator has been found for this scale. Invert operation is impossible.';
      throw IllegalStateException_init_0(message.toString());
    }
    var d0 = this._domain.get_za3lpa$(0);
    var d1 = this._domain.get_za3lpa$(1);
    var r0 = this._range.get_za3lpa$(0);
    var r1 = this._range.get_za3lpa$(1);
    var r;
    var d;
    if (this.rangeComparator.compare(r1, r0) < 0) {
      d = reinterpolateDomain(d1, d0);
      r = deinterpolateRange(r1, r0);
    }
     else {
      d = reinterpolateDomain(d0, d1);
      r = deinterpolateRange(r0, r1);
    }
    return ContinuousScale$bimapInvert$lambda(d, r);
  };
  function ContinuousScale$polymap$lambda(this$ContinuousScale, closure$size, closure$rangeInterpolators, closure$domainInterpolators) {
    return function (x) {
      var index = bisectRight(this$ContinuousScale._domain, x, this$ContinuousScale.domainComparator(), 1, closure$size) - 1 | 0;
      return closure$rangeInterpolators[index](closure$domainInterpolators[index](x));
    };
  }
  ContinuousScale.prototype.polymap_z4navf$_0 = function (uninterpolateDomain) {
    var d0 = first(this._domain);
    var d1 = last(this._domain);
    var domainReversed = this.domainComparator().compare(d1, d0) < 0;
    var domainValues = domainReversed ? reversed(this._domain) : this._domain;
    var rangeValues = domainReversed ? reversed(this._range) : this._range;
    var a = this._domain.size;
    var b = this._range.size;
    var size = Math_0.min(a, b) - 1 | 0;
    var array = Array_0(size);
    var tmp$;
    tmp$ = array.length - 1 | 0;
    for (var i = 0; i <= tmp$; i++) {
      array[i] = uninterpolateDomain(domainValues.get_za3lpa$(i), domainValues.get_za3lpa$(i + 1 | 0));
    }
    var domainInterpolators = array;
    var array_0 = Array_0(size);
    var tmp$_0;
    tmp$_0 = array_0.length - 1 | 0;
    for (var i_0 = 0; i_0 <= tmp$_0; i_0++) {
      array_0[i_0] = this.interpolateRange(rangeValues.get_za3lpa$(i_0), rangeValues.get_za3lpa$(i_0 + 1 | 0));
    }
    var rangeInterpolators = array_0;
    return ContinuousScale$polymap$lambda(this, size, rangeInterpolators, domainInterpolators);
  };
  function ContinuousScale$polymapInvert$lambda(closure$rangeValues, this$ContinuousScale, closure$size, closure$domainInterpolators, closure$rangeInterpolators) {
    return function (y) {
      var index = bisectRight(closure$rangeValues, y, this$ContinuousScale.rangeComparator, 1, closure$size) - 1 | 0;
      return closure$domainInterpolators[index](closure$rangeInterpolators[index](y));
    };
  }
  ContinuousScale.prototype.polymapInvert_1hhkox$_0 = function (interpolateDomain, uninterpolateRange) {
    if (this.rangeComparator == null) {
      var message = 'No RangeComparator has been found for this scale. Invert operation is impossible.';
      throw IllegalStateException_init_0(message.toString());
    }
    var r0 = first(this._range);
    var r1 = last(this._range);
    var rangeReversed = this.rangeComparator.compare(r1, r0) < 0;
    var domainValues = rangeReversed ? reversed(this._domain) : this._domain;
    var rangeValues = rangeReversed ? reversed(this._range) : this._range;
    var a = this._domain.size;
    var b = this._range.size;
    var size = Math_0.min(a, b) - 1 | 0;
    var array = Array_0(size);
    var tmp$;
    tmp$ = array.length - 1 | 0;
    for (var i = 0; i <= tmp$; i++) {
      array[i] = interpolateDomain(domainValues.get_za3lpa$(i), domainValues.get_za3lpa$(i + 1 | 0));
    }
    var domainInterpolators = array;
    var array_0 = Array_0(size);
    var tmp$_0;
    tmp$_0 = array_0.length - 1 | 0;
    for (var i_0 = 0; i_0 <= tmp$_0; i_0++) {
      array_0[i_0] = uninterpolateRange(rangeValues.get_za3lpa$(i_0), rangeValues.get_za3lpa$(i_0 + 1 | 0));
    }
    var rangeInterpolators = array_0;
    return ContinuousScale$polymapInvert$lambda(rangeValues, this, size, domainInterpolators, rangeInterpolators);
  };
  ContinuousScale.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ContinuousScale',
    interfaces: [InvertableScale, ClampableScale, ContinuousRangeScale, ContinuousDomain]
  };
  function bisectRight(list, x, comparator, low, high) {
    if (low === void 0)
      low = 0;
    if (high === void 0)
      high = list.size;
    var lo = low;
    var hi = high;
    while (lo < hi) {
      var mid = (lo + hi | 0) / 2 | 0;
      if (comparator.compare(list.get_za3lpa$(mid), x) > 0)
        hi = mid;
      else
        lo = mid + 1 | 0;
    }
    return lo;
  }
  function bisectLeft(list, x, comparator, low, high) {
    if (low === void 0)
      low = 0;
    if (high === void 0)
      high = list.size;
    var lo = low;
    var hi = high;
    while (lo < hi) {
      var mid = (lo + hi | 0) / 2 | 0;
      if (comparator.compare(list.get_za3lpa$(mid), x) < 0)
        lo = mid + 1 | 0;
      else
        hi = mid;
    }
    return lo;
  }
  function LogScale(base, interpolateRange, uninterpolateRange, rangeComparator) {
    if (base === void 0)
      base = 10.0;
    if (uninterpolateRange === void 0)
      uninterpolateRange = null;
    if (rangeComparator === void 0)
      rangeComparator = null;
    LinearScale.call(this, interpolateRange, uninterpolateRange, rangeComparator);
    this.base_ptcf2s$_0 = base;
    this._domain.clear();
    this._domain.addAll_brywnq$(arrayListOf([1.0, 10.0]));
  }
  Object.defineProperty(LogScale.prototype, 'base', {
    get: function () {
      return this.base_ptcf2s$_0;
    },
    set: function (value) {
      this.base_ptcf2s$_0 = value;
      this.rescale();
    }
  });
  Object.defineProperty(LogScale.prototype, 'domain', {
    get: function () {
      return this._domain;
    },
    set: function (value) {
      if (value.contains_11rb$(0.0))
        throw IllegalArgumentException_init('The domain interval must not contain 0, as log(0) = -\u221E.');
      var destination = ArrayList_init();
      var tmp$;
      tmp$ = value.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        if (element > 0)
          destination.add_11rb$(element);
      }
      var totalPositives = destination.size;
      var destination_0 = ArrayList_init();
      var tmp$_0;
      tmp$_0 = value.iterator();
      while (tmp$_0.hasNext()) {
        var element_0 = tmp$_0.next();
        if (element_0 > 0)
          destination_0.add_11rb$(element_0);
      }
      var totalNegatives = destination_0.size;
      if (totalPositives > 0 && totalPositives < value.size || (totalNegatives > 0 && totalNegatives < value.size))
        throw IllegalArgumentException_init('The domain interval must contain only positive or negative elements.');
      this._domain.clear();
      this._domain.addAll_brywnq$(value);
      this.rescale();
    }
  });
  function LogScale$uninterpolateDomain$lambda(closure$from, closure$diff) {
    return function (t) {
      var x = t / closure$from;
      return new Percent(Math_0.log(x) / closure$diff);
    };
  }
  function LogScale$uninterpolateDomain$lambda_0(f) {
    return get_pct(0);
  }
  LogScale.prototype.uninterpolateDomain_xwzc9q$ = function (from, to) {
    var x = to / from;
    var diff = Math_0.log(x);
    return diff !== 0.0 && diff !== kotlin_js_internal_DoubleCompanionObject.NaN ? LogScale$uninterpolateDomain$lambda(from, diff) : LogScale$uninterpolateDomain$lambda_0;
  };
  function LogScale$interpolateDomain$lambda(closure$to, closure$from) {
    return function (t) {
      var $receiver = closure$to;
      var x = t.value;
      var tmp$ = -Math_0.pow($receiver, x);
      var $receiver_0 = closure$from;
      var x_0 = 1 - t.value;
      return -(tmp$ * -Math_0.pow($receiver_0, x_0));
    };
  }
  function LogScale$interpolateDomain$lambda_0(closure$to, closure$from) {
    return function (t) {
      var $receiver = closure$to;
      var x = t.value;
      var tmp$ = Math_0.pow($receiver, x);
      var $receiver_0 = closure$from;
      var x_0 = 1 - t.value;
      return tmp$ * Math_0.pow($receiver_0, x_0);
    };
  }
  LogScale.prototype.interpolateDomain_xwzc9q$ = function (from, to) {
    return from < 0 ? LogScale$interpolateDomain$lambda(to, from) : LogScale$interpolateDomain$lambda_0(to, from);
  };
  LogScale.prototype.niceLogScale_0 = function (values, floor, ceil) {
    var reversed = last(values) < first(values);
    var first_0 = reversed ? values.size - 1 | 0 : 0;
    var last_0 = reversed ? 0 : values.size - 1 | 0;
    var newDomain = toMutableList(values);
    newDomain.set_wxm5ur$(first_0, floor(values.get_za3lpa$(first_0)));
    newDomain.set_wxm5ur$(last_0, ceil(values.get_za3lpa$(last_0)));
    return newDomain;
  };
  function LogScale$nice$lambda(this$LogScale) {
    return function (x) {
      var tmp$ = this$LogScale.base;
      var x_0 = log(x, this$LogScale.base);
      var x_1 = Math_0.floor(x_0);
      return Math_0.pow(tmp$, x_1);
    };
  }
  function LogScale$nice$lambda_0(this$LogScale) {
    return function (x) {
      var tmp$ = this$LogScale.base;
      var x_0 = log(x, this$LogScale.base);
      var x_1 = Math_0.ceil(x_0);
      return Math_0.pow(tmp$, x_1);
    };
  }
  LogScale.prototype.nice_za3lpa$$default = function (count) {
    this.domain = this.niceLogScale_0(this.domain, LogScale$nice$lambda(this), LogScale$nice$lambda_0(this));
  };
  LogScale.prototype.ticks_za3lpa$$default = function (count) {
    var tmp$, tmp$_0;
    var domainStart = first(this._domain);
    var domainEnd = last(this._domain);
    var domainReversed = domainEnd < domainStart;
    if (domainReversed) {
      domainStart = last(this._domain);
      domainEnd = first(this._domain);
    }
    var i = log(domainStart, this.base);
    var j = log(domainEnd, this.base);
    var tickList = ArrayList_init();
    var test = !(this.base % 1 === 0.0 || isNaN_0(this.base % 1));
    if (test && j - i < count) {
      i = round(i) - 1;
      j = round(j) + 1;
      if (domainStart > 0) {
        while (i < j) {
          var $receiver = this.base;
          var x = i;
          var p = Math_0.pow($receiver, x);
          tmp$ = numberToInt(this.base);
          for (var k = 1; k < tmp$; k++) {
            var t = p * k;
            if (t < domainStart)
              continue;
            if (t > domainEnd)
              break;
            tickList.add_11rb$(t);
          }
          i = i + 1;
        }
      }
       else {
        while (i < j) {
          var $receiver_0 = this.base;
          var x_0 = i;
          var p_0 = Math_0.pow($receiver_0, x_0);
          for (var k_0 = numberToInt(this.base - 1.0); k_0 < 0; k_0++) {
            var t_0 = p_0 * k_0;
            if (t_0 < domainStart)
              continue;
            if (t_0 > domainEnd)
              break;
            tickList.add_11rb$(t_0);
          }
          i = i + 1;
        }
      }
    }
     else {
      var tmp$_1 = i;
      var tmp$_2 = j;
      var a = numberToInt(j - i);
      var $receiver_1 = ticks(tmp$_1, tmp$_2, Math_0.min(a, count));
      var destination = ArrayList_init_0(collectionSizeOrDefault($receiver_1, 10));
      var tmp$_3;
      tmp$_3 = $receiver_1.iterator();
      while (tmp$_3.hasNext()) {
        var item = tmp$_3.next();
        var tmp$_4 = destination.add_11rb$;
        var $receiver_2 = this.base;
        tmp$_4.call(destination, Math_0.pow($receiver_2, item));
      }
      tickList = Kotlin.isType(tmp$_0 = destination, ArrayList) ? tmp$_0 : throwCCE();
    }
    return domainReversed ? reversed(tickList) : tickList;
  };
  LogScale.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'LogScale',
    interfaces: [LinearScale]
  };
  function IndexableDomain() {
    this.index_8be2vx$ = HashMap_init();
    this._domain_8be2vx$ = ArrayList_init();
  }
  Object.defineProperty(IndexableDomain.prototype, 'domain', {
    get: function () {
      return toList(this._domain_8be2vx$);
    },
    set: function (value) {
      this._domain_8be2vx$.clear();
      this.index_8be2vx$.clear();
      var tmp$;
      tmp$ = value.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        if (!this.index_8be2vx$.containsKey_11rb$(element)) {
          this._domain_8be2vx$.add_11rb$(element);
          this.index_8be2vx$.put_xwzc9p$(element, this._domain_8be2vx$.size - 1 | 0);
        }
      }
    }
  });
  IndexableDomain.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'IndexableDomain',
    interfaces: [DiscreteDomain]
  };
  function OrdinalScale(range, indexableDomain) {
    if (range === void 0) {
      range = emptyList();
    }
    if (indexableDomain === void 0)
      indexableDomain = new IndexableDomain();
    this.indexableDomain = indexableDomain;
    this._range = ArrayList_init();
    this._range.addAll_brywnq$(toList(range));
    this._unknown_383taz$_0 = null;
  }
  Object.defineProperty(OrdinalScale.prototype, 'range', {
    get: function () {
      return toList(this._range);
    },
    set: function (value) {
      if (!!value.isEmpty()) {
        var message = "Range can't be empty.";
        throw IllegalArgumentException_init(message.toString());
      }
      this._range.clear();
      this._range.addAll_brywnq$(toList(value));
    }
  });
  Object.defineProperty(OrdinalScale.prototype, 'unknown', {
    get: function () {
      return this._unknown_383taz$_0;
    },
    set: function (value) {
      this._unknown_383taz$_0 = value;
    }
  });
  OrdinalScale.prototype.invoke_11rb$ = function (domainValue) {
    var tmp$, tmp$_0, tmp$_1, tmp$_2;
    if (this._unknown_383taz$_0 == null && !this.indexableDomain.index_8be2vx$.containsKey_11rb$(domainValue)) {
      this.indexableDomain._domain_8be2vx$.add_11rb$(domainValue);
      this.indexableDomain.index_8be2vx$.put_xwzc9p$(domainValue, this.indexableDomain._domain_8be2vx$.size - 1 | 0);
    }
    tmp$_0 = this.indexableDomain.index_8be2vx$.get_11rb$(domainValue);
    if (tmp$_0 == null) {
      tmp$ = this._unknown_383taz$_0;
      if (tmp$ == null) {
        throw IllegalStateException_init();
      }
      return tmp$;
    }
    var index = tmp$_0;
    if (this._range.isEmpty()) {
      tmp$_1 = this._unknown_383taz$_0;
      if (tmp$_1 == null) {
        throw IllegalStateException_init();
      }
      tmp$_2 = tmp$_1;
    }
     else
      tmp$_2 = this._range.get_za3lpa$(index % this._range.size);
    return tmp$_2;
  };
  Object.defineProperty(OrdinalScale.prototype, 'domain', {
    get: function () {
      return this.indexableDomain.domain;
    },
    set: function (tmp$) {
      this.indexableDomain.domain = tmp$;
    }
  });
  OrdinalScale.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'OrdinalScale',
    interfaces: [DiscreteDomain, Scale]
  };
  function PowerScale(exponent, interpolateRange, uninterpolateRange, rangeComparator) {
    if (exponent === void 0)
      exponent = 1.0;
    if (uninterpolateRange === void 0)
      uninterpolateRange = null;
    if (rangeComparator === void 0)
      rangeComparator = null;
    LinearScale.call(this, interpolateRange, uninterpolateRange, rangeComparator);
    this.exponent_dma9hv$_0 = exponent;
  }
  Object.defineProperty(PowerScale.prototype, 'exponent', {
    get: function () {
      return this.exponent_dma9hv$_0;
    },
    set: function (value) {
      this.exponent_dma9hv$_0 = value;
      this.rescale();
    }
  });
  function PowerScale$uninterpolateDomain$lambda(f) {
    return get_pct(0);
  }
  function PowerScale$uninterpolateDomain$lambda_0(this$PowerScale, closure$dFrom, closure$dTo) {
    return function (t) {
      return new Percent((this$PowerScale.raise_0(t, this$PowerScale.exponent) - closure$dFrom) / closure$dTo);
    };
  }
  PowerScale.prototype.uninterpolateDomain_xwzc9q$ = function (from, to) {
    var dFrom = this.raise_0(from, this.exponent);
    var dTo = this.raise_0(to, this.exponent) - dFrom;
    return dTo === 0.0 || isNaN_0(dTo) ? PowerScale$uninterpolateDomain$lambda : PowerScale$uninterpolateDomain$lambda_0(this, dFrom, dTo);
  };
  function PowerScale$interpolateDomain$lambda(closure$ra, closure$rb, this$PowerScale) {
    return function (t) {
      return this$PowerScale.raise_0(closure$ra + closure$rb * t.value, 1.0 / this$PowerScale.exponent);
    };
  }
  PowerScale.prototype.interpolateDomain_xwzc9q$ = function (from, to) {
    var ra = this.raise_0(from, this.exponent);
    var rb = this.raise_0(to, this.exponent) - ra;
    return PowerScale$interpolateDomain$lambda(ra, rb, this);
  };
  PowerScale.prototype.raise_0 = function (x, exponent) {
    var pow = Math_0.pow(x, exponent);
    return x < 0.0 ? -pow : pow;
  };
  PowerScale.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'PowerScale',
    interfaces: [LinearScale]
  };
  function QuantileScale() {
    this.thresholds_0 = ArrayList_init();
    this.domain_tcu4ug$_0 = emptyList();
    this.range_7d1n4b$_0 = emptyList();
  }
  Object.defineProperty(QuantileScale.prototype, 'quantiles', {
    get: function () {
      return toList(this.thresholds_0);
    }
  });
  Object.defineProperty(QuantileScale.prototype, 'domain', {
    get: function () {
      return toList(this.domain_tcu4ug$_0);
    },
    set: function (value) {
      var destination = ArrayList_init();
      var tmp$;
      tmp$ = value.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        if (!isNaN_0(element))
          destination.add_11rb$(element);
      }
      var filteredValue = sorted(destination);
      if (!!filteredValue.isEmpty()) {
        var message = "Domain can't be empty.";
        throw IllegalArgumentException_init(message.toString());
      }
      this.domain_tcu4ug$_0 = filteredValue;
      this.rescale_0();
    }
  });
  Object.defineProperty(QuantileScale.prototype, 'range', {
    get: function () {
      return toList(this.range_7d1n4b$_0);
    },
    set: function (value) {
      if (!!value.isEmpty()) {
        var message = "Range can't be empty.";
        throw IllegalArgumentException_init(message.toString());
      }
      this.range_7d1n4b$_0 = toList(value);
      this.rescale_0();
    }
  });
  QuantileScale.prototype.rescale_0 = function () {
    if (this.domain.isEmpty() || this.range.isEmpty())
      return;
    var i = 0;
    var b = this.range.size;
    var n = Math_0.max(1, b);
    this.thresholds_0 = ArrayList_init();
    while ((i = i + 1 | 0, i) < n) {
      this.thresholds_0.add_wxm5ur$(i - 1 | 0, quantile(this.domain, i / n));
    }
  };
  QuantileScale.prototype.invertExtent_11rb$ = function (rangeValue) {
    var tmp$;
    if (!!this.domain.isEmpty()) {
      var message = "Can't compute a Quantile Scale with an empty Domain";
      throw IllegalStateException_init_0(message.toString());
    }
    if (!!this.range.isEmpty()) {
      var message_0 = "Can't compute a Quantile Scale with an empty Range";
      throw IllegalStateException_init_0(message_0.toString());
    }
    var index = this.range.indexOf_11rb$(rangeValue);
    if (index === -1)
      tmp$ = listOf([kotlin_js_internal_DoubleCompanionObject.NaN, kotlin_js_internal_DoubleCompanionObject.NaN]);
    else
      tmp$ = listOf([index > 0 ? this.thresholds_0.get_za3lpa$(index - 1 | 0) : first(this.domain), index < this.thresholds_0.size ? this.thresholds_0.get_za3lpa$(index) : last(this.domain)]);
    return tmp$;
  };
  QuantileScale.prototype.invoke_11rb$ = function (domainValue) {
    if (!!isNaN_0(domainValue)) {
      var message = "domainValue can't be NaN";
      throw IllegalArgumentException_init(message.toString());
    }
    if (!!this.domain.isEmpty()) {
      var message_0 = "Can't compute a Quantile Scale with an empty Domain";
      throw IllegalStateException_init_0(message_0.toString());
    }
    if (!!this.range.isEmpty()) {
      var message_1 = "Can't compute a Quantile Scale with an empty Range";
      throw IllegalStateException_init_0(message_1.toString());
    }
    return this.range.get_za3lpa$(bisectRight(this.thresholds_0, domainValue, naturalOrder()));
  };
  QuantileScale.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'QuantileScale',
    interfaces: [DiscreteRange, DiscreteDomain, Scale]
  };
  function quantile$lambda(x, f, f_0) {
    return x;
  }
  function quantile(values, p, f) {
    if (f === void 0)
      f = quantile$lambda;
    if (!!values.isEmpty()) {
      var message = 'Values must not be empty.';
      throw IllegalArgumentException_init(message.toString());
    }
    var size = values.size;
    if (p <= 0.0 || size < 2)
      return f(values.get_za3lpa$(0), size - 1 - 1 | 0, values);
    var h = (size - 1 | 0) * p;
    var i = numberToInt(Math_0.floor(h));
    var a = f(values.get_za3lpa$(i), i, values);
    var b = f(values.get_za3lpa$(i + 1 | 0), i + 1 | 0, values);
    return a + (b - a) * (h - i);
  }
  function QuantizeScale() {
    this.quantizedDomain_0 = arrayListOf([0.5]);
    this.range_wdbhml$_0 = emptyList();
    this.domain_n92suu$_0 = intervalOf(0.0, 1.0);
  }
  Object.defineProperty(QuantizeScale.prototype, 'range', {
    get: function () {
      return toList(this.range_wdbhml$_0);
    },
    set: function (value) {
      this.range_wdbhml$_0 = toList(value);
      this.rescale_0();
    }
  });
  Object.defineProperty(QuantizeScale.prototype, 'domain', {
    get: function () {
      return this.domain_n92suu$_0;
    },
    set: function (value) {
      this.domain_n92suu$_0 = value;
      this.rescale_0();
    }
  });
  QuantizeScale.prototype.rescale_0 = function () {
    this.quantizedDomain_0.clear();
    var size = this.range.size - 1 | 0;
    for (var index = 0; index < size; index++) {
      var element = ((index + 1 | 0) * this.domain.end - (index - size | 0) * this.domain.start) / (size + 1 | 0);
      this.quantizedDomain_0.add_11rb$(element);
    }
  };
  QuantizeScale.prototype.invoke_11rb$ = function (domainValue) {
    return this.range.get_za3lpa$(bisectRight(this.quantizedDomain_0, domainValue, naturalOrder(), 0, this.range.size - 1 | 0));
  };
  QuantizeScale.prototype.invertExtent_11rb$ = function (rangeValue) {
    var tmp$;
    var i = this.range.indexOf_11rb$(rangeValue);
    var size = this.range.size - 1 | 0;
    if (i < 0)
      tmp$ = listOf([kotlin_js_internal_DoubleCompanionObject.NaN, kotlin_js_internal_DoubleCompanionObject.NaN]);
    else if (i < 1)
      tmp$ = listOf([this.domain.start, first(this.quantizedDomain_0)]);
    else if (i >= size)
      tmp$ = listOf([this.quantizedDomain_0.get_za3lpa$(size - 1 | 0), this.domain.end]);
    else
      tmp$ = listOf([this.quantizedDomain_0.get_za3lpa$(i - 1 | 0), this.quantizedDomain_0.get_za3lpa$(i)]);
    return tmp$;
  };
  QuantizeScale.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'QuantizeScale',
    interfaces: [DiscreteRange, StrictlyContinuousDomain, Scale]
  };
  function Scale() {
  }
  Scale.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Scale',
    interfaces: []
  };
  function ContinuousDomain() {
  }
  ContinuousDomain.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'ContinuousDomain',
    interfaces: []
  };
  function DiscreteDomain() {
  }
  DiscreteDomain.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'DiscreteDomain',
    interfaces: []
  };
  function StrictlyContinuousDomain() {
  }
  StrictlyContinuousDomain.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'StrictlyContinuousDomain',
    interfaces: []
  };
  function ContinuousRangeScale() {
  }
  ContinuousRangeScale.prototype.start = function () {
    return first(this.range);
  };
  ContinuousRangeScale.prototype.end = function () {
    return last(this.range);
  };
  ContinuousRangeScale.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'ContinuousRangeScale',
    interfaces: [FirstLastRange, Scale]
  };
  function DiscreteRange() {
  }
  DiscreteRange.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'DiscreteRange',
    interfaces: []
  };
  function StrictlyContinuousRange() {
  }
  StrictlyContinuousRange.prototype.start = function () {
    return this.range.start;
  };
  StrictlyContinuousRange.prototype.end = function () {
    return this.range.end;
  };
  StrictlyContinuousRange.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'StrictlyContinuousRange',
    interfaces: [FirstLastRange]
  };
  function FirstLastRange() {
  }
  FirstLastRange.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'FirstLastRange',
    interfaces: [Scale]
  };
  function StrictlyContinuous(start, end) {
    this.start = start;
    this.end = end;
  }
  StrictlyContinuous.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'StrictlyContinuous',
    interfaces: []
  };
  StrictlyContinuous.prototype.component1 = function () {
    return this.start;
  };
  StrictlyContinuous.prototype.component2 = function () {
    return this.end;
  };
  StrictlyContinuous.prototype.copy_xwzc9q$ = function (start, end) {
    return new StrictlyContinuous(start === void 0 ? this.start : start, end === void 0 ? this.end : end);
  };
  StrictlyContinuous.prototype.toString = function () {
    return 'StrictlyContinuous(start=' + Kotlin.toString(this.start) + (', end=' + Kotlin.toString(this.end)) + ')';
  };
  StrictlyContinuous.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.start) | 0;
    result = result * 31 + Kotlin.hashCode(this.end) | 0;
    return result;
  };
  StrictlyContinuous.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.start, other.start) && Kotlin.equals(this.end, other.end)))));
  };
  function intervalOf(start, end) {
    return new StrictlyContinuous(start, end);
  }
  function intervalOf_0(values) {
    return new StrictlyContinuous(first_0(values), last_0(values));
  }
  function ClampableScale() {
  }
  ClampableScale.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'ClampableScale',
    interfaces: []
  };
  function NiceableScale() {
  }
  NiceableScale.prototype.nice_za3lpa$ = function (count, callback$default) {
    if (count === void 0)
      count = 10;
    callback$default ? callback$default(count) : this.nice_za3lpa$$default(count);
  };
  NiceableScale.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'NiceableScale',
    interfaces: [ContinuousDomain]
  };
  function InvertableScale() {
  }
  InvertableScale.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'InvertableScale',
    interfaces: [Scale]
  };
  function Tickable() {
  }
  Tickable.prototype.ticks_za3lpa$ = function (count, callback$default) {
    if (count === void 0)
      count = 10;
    return callback$default ? callback$default(count) : this.ticks_za3lpa$$default(count);
  };
  Tickable.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Tickable',
    interfaces: []
  };
  function Scales() {
    Scales_instance = this;
  }
  function Scales$Continuous() {
    Scales$Continuous_instance = this;
  }
  Scales$Continuous.prototype.identity = function () {
    var $receiver = new LinearScale(getCallableRef('interpolateNumber', function (start, end) {
      return interpolateNumber(start, end);
    }), getCallableRef('uninterpolateNumber', function (start, end) {
      return uninterpolateNumber(start, end);
    }), naturalOrder());
    $receiver.domain = listOf([0.0, 1.0]);
    $receiver.range = listOf([0.0, 1.0]);
    return $receiver;
  };
  function Scales$Continuous$linear$lambda($receiver) {
    return Unit;
  }
  Scales$Continuous.prototype.linear_hv2kdx$ = function (init) {
    if (init === void 0)
      init = Scales$Continuous$linear$lambda;
    var $receiver = new LinearScale(getCallableRef('interpolateNumber', function (start, end) {
      return interpolateNumber(start, end);
    }), getCallableRef('uninterpolateNumber', function (start, end) {
      return uninterpolateNumber(start, end);
    }), naturalOrder());
    init($receiver);
    return $receiver;
  };
  function Scales$Continuous$linearRound$lambda($receiver) {
    return Unit;
  }
  Scales$Continuous.prototype.linearRound_hv2kdx$ = function (init) {
    if (init === void 0)
      init = Scales$Continuous$linearRound$lambda;
    var $receiver = new LinearScale(getCallableRef('interpolateRound', function (start, end) {
      return interpolateRound(start, end);
    }), getCallableRef('uninterpolateNumber', function (start, end) {
      return uninterpolateNumber(start, end);
    }), naturalOrder());
    init($receiver);
    return $receiver;
  };
  function Scales$Continuous$log$lambda($receiver) {
    return Unit;
  }
  Scales$Continuous.prototype.log_19xkcx$ = function (base, init) {
    if (base === void 0)
      base = 10.0;
    if (init === void 0)
      init = Scales$Continuous$log$lambda;
    var $receiver = new LogScale(base, getCallableRef('interpolateNumber', function (start, end) {
      return interpolateNumber(start, end);
    }), getCallableRef('uninterpolateNumber', function (start, end) {
      return uninterpolateNumber(start, end);
    }), naturalOrder());
    init($receiver);
    return $receiver;
  };
  Scales$Continuous.prototype.logRound_14dthe$ = function (base) {
    if (base === void 0)
      base = 10.0;
    return new LogScale(base, getCallableRef('interpolateRound', function (start, end) {
      return interpolateRound(start, end);
    }), getCallableRef('uninterpolateNumber', function (start, end) {
      return uninterpolateNumber(start, end);
    }), naturalOrder());
  };
  function Scales$Continuous$vector$lambda($receiver) {
    return Unit;
  }
  Scales$Continuous.prototype.vector_4k8xie$ = function (init) {
    if (init === void 0)
      init = Scales$Continuous$vector$lambda;
    var $receiver = new LinearScale(getCallableRef('interpolatePoint', function (start, end) {
      return interpolatePoint(start, end);
    }), getCallableRef('uninterpolatePointOnX', function (start, end) {
      return uninterpolatePointOnX(start, end);
    }), new PointComparatorX());
    init($receiver);
    return $receiver;
  };
  function Scales$Continuous$pow$lambda($receiver) {
    return Unit;
  }
  Scales$Continuous.prototype.pow_t2h1zn$ = function (exponent, init) {
    if (exponent === void 0)
      exponent = 1.0;
    if (init === void 0)
      init = Scales$Continuous$pow$lambda;
    var $receiver = new PowerScale(exponent, getCallableRef('interpolateNumber', function (start, end) {
      return interpolateNumber(start, end);
    }), getCallableRef('uninterpolateNumber', function (start, end) {
      return uninterpolateNumber(start, end);
    }), naturalOrder());
    init($receiver);
    return $receiver;
  };
  function Scales$Continuous$powRound$lambda($receiver) {
    return Unit;
  }
  Scales$Continuous.prototype.powRound_t2h1zn$ = function (exponent, init) {
    if (exponent === void 0)
      exponent = 1.0;
    if (init === void 0)
      init = Scales$Continuous$powRound$lambda;
    var $receiver = new PowerScale(exponent, getCallableRef('interpolateRound', function (start, end) {
      return interpolateRound(start, end);
    }), getCallableRef('uninterpolateNumber', function (start, end) {
      return uninterpolateNumber(start, end);
    }), naturalOrder());
    init($receiver);
    return $receiver;
  };
  function Scales$Continuous$sqrt$lambda($receiver) {
    return Unit;
  }
  Scales$Continuous.prototype.sqrt_y19wbf$ = function (init) {
    if (init === void 0)
      init = Scales$Continuous$sqrt$lambda;
    var $receiver = new PowerScale(0.5, getCallableRef('interpolateNumber', function (start, end) {
      return interpolateNumber(start, end);
    }), getCallableRef('uninterpolateNumber', function (start, end) {
      return uninterpolateNumber(start, end);
    }), naturalOrder());
    init($receiver);
    return $receiver;
  };
  function Scales$Continuous$sqrtRound$lambda($receiver) {
    return Unit;
  }
  Scales$Continuous.prototype.sqrtRound_y19wbf$ = function (init) {
    if (init === void 0)
      init = Scales$Continuous$sqrtRound$lambda;
    var $receiver = new PowerScale(0.5, getCallableRef('interpolateRound', function (start, end) {
      return interpolateRound(start, end);
    }), getCallableRef('uninterpolateNumber', function (start, end) {
      return uninterpolateNumber(start, end);
    }), naturalOrder());
    init($receiver);
    return $receiver;
  };
  function Scales$Continuous$time$lambda($receiver) {
    return Unit;
  }
  Scales$Continuous.prototype.time_5n7183$ = function (init) {
    if (init === void 0)
      init = Scales$Continuous$time$lambda;
    var $receiver = new TimeScale(getCallableRef('interpolateNumber', function (start, end) {
      return interpolateNumber(start, end);
    }), getCallableRef('uninterpolateNumber', function (start, end) {
      return uninterpolateNumber(start, end);
    }), naturalOrder());
    init($receiver);
    return $receiver;
  };
  Scales$Continuous.prototype.sequential_745qxq$ = function (interpolator) {
    return new SequentialScale(interpolator);
  };
  Scales$Continuous.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Continuous',
    interfaces: []
  };
  var Scales$Continuous_instance = null;
  function Scales$Continuous_getInstance() {
    if (Scales$Continuous_instance === null) {
      new Scales$Continuous();
    }
    return Scales$Continuous_instance;
  }
  function Scales$Quantized() {
    Scales$Quantized_instance = this;
  }
  function Scales$Quantized$quantile$lambda($receiver) {
    return Unit;
  }
  Scales$Quantized.prototype.quantile_v47b15$ = function (init) {
    if (init === void 0)
      init = Scales$Quantized$quantile$lambda;
    var $receiver = new QuantileScale();
    init($receiver);
    return $receiver;
  };
  function Scales$Quantized$quantize$lambda($receiver) {
    return Unit;
  }
  Scales$Quantized.prototype.quantize_shvox3$ = function (init) {
    if (init === void 0)
      init = Scales$Quantized$quantize$lambda;
    var $receiver = new QuantizeScale();
    init($receiver);
    return $receiver;
  };
  function Scales$Quantized$threshold$lambda($receiver) {
    return Unit;
  }
  Scales$Quantized.prototype.threshold_9bd9ml$ = function (init) {
    if (init === void 0)
      init = Scales$Quantized$threshold$lambda;
    var $receiver = new ThresholdScale();
    init($receiver);
    return $receiver;
  };
  Scales$Quantized.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Quantized',
    interfaces: []
  };
  var Scales$Quantized_instance = null;
  function Scales$Quantized_getInstance() {
    if (Scales$Quantized_instance === null) {
      new Scales$Quantized();
    }
    return Scales$Quantized_instance;
  }
  function Scales$Discrete() {
    Scales$Discrete_instance = this;
  }
  function Scales$Discrete$point$lambda($receiver) {
    return Unit;
  }
  Scales$Discrete.prototype.point_dq500o$ = function (init) {
    if (init === void 0)
      init = Scales$Discrete$point$lambda;
    var $receiver = new PointScale();
    init($receiver);
    return $receiver;
  };
  function Scales$Discrete$band$lambda($receiver) {
    return Unit;
  }
  Scales$Discrete.prototype.band_2gqbrx$ = function (init) {
    if (init === void 0)
      init = Scales$Discrete$band$lambda;
    var $receiver = new BandScale();
    init($receiver);
    return $receiver;
  };
  function Scales$Discrete$ordinal$lambda($receiver) {
    return Unit;
  }
  Scales$Discrete.prototype.ordinal_2idftj$ = function (init) {
    if (init === void 0)
      init = Scales$Discrete$ordinal$lambda;
    var $receiver = new OrdinalScale();
    init($receiver);
    return $receiver;
  };
  Scales$Discrete.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Discrete',
    interfaces: []
  };
  var Scales$Discrete_instance = null;
  function Scales$Discrete_getInstance() {
    if (Scales$Discrete_instance === null) {
      new Scales$Discrete();
    }
    return Scales$Discrete_instance;
  }
  Scales.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Scales',
    interfaces: []
  };
  var Scales_instance = null;
  function Scales_getInstance() {
    if (Scales_instance === null) {
      new Scales();
    }
    return Scales_instance;
  }
  function ScalesChromatic() {
    ScalesChromatic_instance = this;
  }
  function ScalesChromatic$Continuous() {
    ScalesChromatic$Continuous_instance = this;
  }
  function ScalesChromatic$Continuous$linearRGB$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Continuous.prototype.linearRGB_qkuwoo$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Continuous$linearRGB$lambda;
    var $receiver = new LinearScale(getCallableRef('rgbLinearInterpolator', function (start, end) {
      return rgbLinearInterpolator(start, end);
    }));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Continuous$defaultRGB$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Continuous.prototype.defaultRGB_qkuwoo$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Continuous$defaultRGB$lambda;
    var $receiver = new LinearScale(getCallableRef('rgbDefaultInterpolator', function (start, end) {
      return rgbDefaultInterpolator(start, end);
    }));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Continuous$linearLAB$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Continuous.prototype.linearLAB_qkuwoo$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Continuous$linearLAB$lambda;
    var $receiver = new LinearScale(getCallableRef('labInterpolator', function (start, end) {
      return labInterpolator(start, end);
    }));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Continuous$linearHCL$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Continuous.prototype.linearHCL_qkuwoo$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Continuous$linearHCL$lambda;
    var $receiver = new LinearScale(getCallableRef('hclInterpolator', function (start, end) {
      return hclInterpolator(start, end);
    }));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Continuous$linearHCLLong$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Continuous.prototype.linearHCLLong_qkuwoo$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Continuous$linearHCLLong$lambda;
    var $receiver = new LinearScale(getCallableRef('hclLongInterpolator', function (start, end) {
      return hclLongInterpolator(start, end);
    }));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Continuous$linearHSL$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Continuous.prototype.linearHSL_qkuwoo$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Continuous$linearHSL$lambda;
    var $receiver = new LinearScale(getCallableRef('hslInterpolator', function (start, end) {
      return hslInterpolator(start, end);
    }));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Continuous$linearHSLLong$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Continuous.prototype.linearHSLLong_qkuwoo$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Continuous$linearHSLLong$lambda;
    var $receiver = new LinearScale(getCallableRef('hslLongInterpolator', function (start, end) {
      return hslLongInterpolator(start, end);
    }));
    init($receiver);
    return $receiver;
  };
  ScalesChromatic$Continuous.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Continuous',
    interfaces: []
  };
  var ScalesChromatic$Continuous_instance = null;
  function ScalesChromatic$Continuous_getInstance() {
    if (ScalesChromatic$Continuous_instance === null) {
      new ScalesChromatic$Continuous();
    }
    return ScalesChromatic$Continuous_instance;
  }
  function ScalesChromatic$Sequential() {
    ScalesChromatic$Sequential_instance = this;
  }
  function ScalesChromatic$Sequential$SingleHue() {
    ScalesChromatic$Sequential$SingleHue_instance = this;
  }
  function ScalesChromatic$Sequential$SingleHue$blues$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$SingleHue.prototype.blues_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$SingleHue$blues$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(last(EncodedColors.Companion.blues).colors));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Sequential$SingleHue$greens$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$SingleHue.prototype.greens_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$SingleHue$greens$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(last(EncodedColors.Companion.greens).colors));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Sequential$SingleHue$greys$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$SingleHue.prototype.greys_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$SingleHue$greys$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(last(EncodedColors.Companion.greys).colors));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Sequential$SingleHue$oranges$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$SingleHue.prototype.oranges_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$SingleHue$oranges$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(last(EncodedColors.Companion.oranges).colors));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Sequential$SingleHue$purples$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$SingleHue.prototype.purples_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$SingleHue$purples$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(last(EncodedColors.Companion.purples).colors));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Sequential$SingleHue$reds$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$SingleHue.prototype.reds_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$SingleHue$reds$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(last(EncodedColors.Companion.reds).colors));
    init($receiver);
    return $receiver;
  };
  ScalesChromatic$Sequential$SingleHue.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'SingleHue',
    interfaces: []
  };
  var ScalesChromatic$Sequential$SingleHue_instance = null;
  function ScalesChromatic$Sequential$SingleHue_getInstance() {
    if (ScalesChromatic$Sequential$SingleHue_instance === null) {
      new ScalesChromatic$Sequential$SingleHue();
    }
    return ScalesChromatic$Sequential$SingleHue_instance;
  }
  function ScalesChromatic$Sequential$MultiHue() {
    ScalesChromatic$Sequential$MultiHue_instance = this;
  }
  function ScalesChromatic$Sequential$MultiHue$viridis$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$MultiHue.prototype.viridis_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$MultiHue$viridis$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(EncodedColors.Companion.viridis.colors));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Sequential$MultiHue$magma$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$MultiHue.prototype.magma_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$MultiHue$magma$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(EncodedColors.Companion.magma.colors));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Sequential$MultiHue$inferno$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$MultiHue.prototype.inferno_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$MultiHue$inferno$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(EncodedColors.Companion.inferno.colors));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Sequential$MultiHue$plasma$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$MultiHue.prototype.plasma_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$MultiHue$plasma$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(EncodedColors.Companion.plasma.colors));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Sequential$MultiHue$blue_green$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$MultiHue.prototype.blue_green_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$MultiHue$blue_green$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(last(EncodedColors.Companion.BuGN).colors));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Sequential$MultiHue$blue_purple$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$MultiHue.prototype.blue_purple_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$MultiHue$blue_purple$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(last(EncodedColors.Companion.BuPu).colors));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Sequential$MultiHue$green_blue$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$MultiHue.prototype.green_blue_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$MultiHue$green_blue$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(last(EncodedColors.Companion.GnBu).colors));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Sequential$MultiHue$orange_red$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$MultiHue.prototype.orange_red_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$MultiHue$orange_red$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(last(EncodedColors.Companion.OrRd).colors));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Sequential$MultiHue$purple_blue$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$MultiHue.prototype.purple_blue_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$MultiHue$purple_blue$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(last(EncodedColors.Companion.PuBu).colors));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Sequential$MultiHue$purple_blue_green$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$MultiHue.prototype.purple_blue_green_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$MultiHue$purple_blue_green$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(last(EncodedColors.Companion.PuBuGn).colors));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Sequential$MultiHue$purple_red$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$MultiHue.prototype.purple_red_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$MultiHue$purple_red$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(last(EncodedColors.Companion.PuRd).colors));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Sequential$MultiHue$red_purple$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$MultiHue.prototype.red_purple_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$MultiHue$red_purple$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(last(EncodedColors.Companion.RdPu).colors));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Sequential$MultiHue$yellow_green$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$MultiHue.prototype.yellow_green_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$MultiHue$yellow_green$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(last(EncodedColors.Companion.YlGn).colors));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Sequential$MultiHue$yellow_green_blue$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$MultiHue.prototype.yellow_green_blue_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$MultiHue$yellow_green_blue$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(last(EncodedColors.Companion.YlGnbU).colors));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Sequential$MultiHue$yellow_green_brown$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$MultiHue.prototype.yellow_green_brown_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$MultiHue$yellow_green_brown$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(last(EncodedColors.Companion.YlGnBr).colors));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Sequential$MultiHue$yellow_green_red$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$MultiHue.prototype.yellow_green_red_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$MultiHue$yellow_green_red$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(last(EncodedColors.Companion.YlGnRd).colors));
    init($receiver);
    return $receiver;
  };
  ScalesChromatic$Sequential$MultiHue.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'MultiHue',
    interfaces: []
  };
  var ScalesChromatic$Sequential$MultiHue_instance = null;
  function ScalesChromatic$Sequential$MultiHue_getInstance() {
    if (ScalesChromatic$Sequential$MultiHue_instance === null) {
      new ScalesChromatic$Sequential$MultiHue();
    }
    return ScalesChromatic$Sequential$MultiHue_instance;
  }
  function ScalesChromatic$Sequential$Diverging() {
    ScalesChromatic$Sequential$Diverging_instance = this;
  }
  function ScalesChromatic$Sequential$Diverging$brown_blueGreen$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$Diverging.prototype.brown_blueGreen_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$Diverging$brown_blueGreen$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(last(EncodedColors.Companion.BrBG).colors));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Sequential$Diverging$pink_green$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$Diverging.prototype.pink_green_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$Diverging$pink_green$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(last(EncodedColors.Companion.PiYG).colors));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Sequential$Diverging$purple_green$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$Diverging.prototype.purple_green_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$Diverging$purple_green$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(last(EncodedColors.Companion.PRGn).colors));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Sequential$Diverging$purple_orange$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$Diverging.prototype.purple_orange_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$Diverging$purple_orange$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(last(EncodedColors.Companion.PuOR).colors));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Sequential$Diverging$red_blue$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$Diverging.prototype.red_blue_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$Diverging$red_blue$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(last(EncodedColors.Companion.RdBU).colors));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Sequential$Diverging$red_grey$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$Diverging.prototype.red_grey_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$Diverging$red_grey$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(last(EncodedColors.Companion.RdGY).colors));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Sequential$Diverging$red_yelow_blue$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$Diverging.prototype.red_yelow_blue_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$Diverging$red_yelow_blue$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(last(EncodedColors.Companion.RdYlBu).colors));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Sequential$Diverging$red_yellow_green$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$Diverging.prototype.red_yellow_green_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$Diverging$red_yellow_green$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(last(EncodedColors.Companion.RdYlGn).colors));
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Sequential$Diverging$spectral$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$Diverging.prototype.spectral_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$Diverging$spectral$lambda;
    var $receiver = new SequentialScale(rgbBasisInterpolator(last(EncodedColors.Companion.spectral).colors));
    init($receiver);
    return $receiver;
  };
  ScalesChromatic$Sequential$Diverging.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Diverging',
    interfaces: []
  };
  var ScalesChromatic$Sequential$Diverging_instance = null;
  function ScalesChromatic$Sequential$Diverging_getInstance() {
    if (ScalesChromatic$Sequential$Diverging_instance === null) {
      new ScalesChromatic$Sequential$Diverging();
    }
    return ScalesChromatic$Sequential$Diverging_instance;
  }
  function ScalesChromatic$Sequential$Cyclical() {
    ScalesChromatic$Sequential$Cyclical_instance = this;
  }
  function ScalesChromatic$Sequential$Cyclical$sineBow$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Sequential$Cyclical.prototype.sineBow_bhymni$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Sequential$Cyclical$sineBow$lambda;
    var $receiver = new SequentialScale(rgbSineBowInterpolator());
    init($receiver);
    return $receiver;
  };
  ScalesChromatic$Sequential$Cyclical.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Cyclical',
    interfaces: []
  };
  var ScalesChromatic$Sequential$Cyclical_instance = null;
  function ScalesChromatic$Sequential$Cyclical_getInstance() {
    if (ScalesChromatic$Sequential$Cyclical_instance === null) {
      new ScalesChromatic$Sequential$Cyclical();
    }
    return ScalesChromatic$Sequential$Cyclical_instance;
  }
  ScalesChromatic$Sequential.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Sequential',
    interfaces: []
  };
  var ScalesChromatic$Sequential_instance = null;
  function ScalesChromatic$Sequential_getInstance() {
    if (ScalesChromatic$Sequential_instance === null) {
      new ScalesChromatic$Sequential();
    }
    return ScalesChromatic$Sequential_instance;
  }
  function ScalesChromatic$Discrete() {
    ScalesChromatic$Discrete_instance = this;
  }
  function ScalesChromatic$Discrete$accent8$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Discrete.prototype.accent8_kmhvxg$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Discrete$accent8$lambda;
    var $receiver = new OrdinalScale(EncodedColors.Companion.accents.colors);
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Discrete$dark8$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Discrete.prototype.dark8_kmhvxg$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Discrete$dark8$lambda;
    var $receiver = new OrdinalScale(EncodedColors.Companion.dark2.colors);
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Discrete$paired12$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Discrete.prototype.paired12_kmhvxg$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Discrete$paired12$lambda;
    var $receiver = new OrdinalScale(EncodedColors.Companion.paired.colors);
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Discrete$pastel9$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Discrete.prototype.pastel9_kmhvxg$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Discrete$pastel9$lambda;
    var $receiver = new OrdinalScale(EncodedColors.Companion.pastel1.colors);
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Discrete$pastel8$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Discrete.prototype.pastel8_kmhvxg$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Discrete$pastel8$lambda;
    var $receiver = new OrdinalScale(EncodedColors.Companion.pastel2.colors);
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Discrete$vivid9$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Discrete.prototype.vivid9_kmhvxg$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Discrete$vivid9$lambda;
    var $receiver = new OrdinalScale(EncodedColors.Companion.set1.colors);
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Discrete$vivid8$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Discrete.prototype.vivid8_kmhvxg$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Discrete$vivid8$lambda;
    var $receiver = new OrdinalScale(EncodedColors.Companion.set2.colors);
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Discrete$pale12$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Discrete.prototype.pale12_kmhvxg$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Discrete$pale12$lambda;
    var $receiver = new OrdinalScale(EncodedColors.Companion.set3.colors);
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Discrete$category10$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Discrete.prototype.category10_kmhvxg$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Discrete$category10$lambda;
    var $receiver = new OrdinalScale(EncodedColors.Companion.category10.colors);
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Discrete$categoryA20$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Discrete.prototype.categoryA20_kmhvxg$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Discrete$categoryA20$lambda;
    var $receiver = new OrdinalScale(EncodedColors.Companion.category20.colors);
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Discrete$categoryB20$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Discrete.prototype.categoryB20_kmhvxg$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Discrete$categoryB20$lambda;
    var $receiver = new OrdinalScale(EncodedColors.Companion.category20b.colors);
    init($receiver);
    return $receiver;
  };
  function ScalesChromatic$Discrete$categoryC20$lambda($receiver) {
    return Unit;
  }
  ScalesChromatic$Discrete.prototype.categoryC20_kmhvxg$ = function (init) {
    if (init === void 0)
      init = ScalesChromatic$Discrete$categoryC20$lambda;
    var $receiver = new OrdinalScale(EncodedColors.Companion.category20c.colors);
    init($receiver);
    return $receiver;
  };
  ScalesChromatic$Discrete.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Discrete',
    interfaces: []
  };
  var ScalesChromatic$Discrete_instance = null;
  function ScalesChromatic$Discrete_getInstance() {
    if (ScalesChromatic$Discrete_instance === null) {
      new ScalesChromatic$Discrete();
    }
    return ScalesChromatic$Discrete_instance;
  }
  ScalesChromatic.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'ScalesChromatic',
    interfaces: []
  };
  var ScalesChromatic_instance = null;
  function ScalesChromatic_getInstance() {
    if (ScalesChromatic_instance === null) {
      new ScalesChromatic();
    }
    return ScalesChromatic_instance;
  }
  function SequentialScale(interpolator) {
    this.interpolator = interpolator;
    this.domain_8ag30o$_0 = intervalOf(0.0, 1.0);
    this.clamp_gaoomr$_0 = false;
  }
  Object.defineProperty(SequentialScale.prototype, 'domain', {
    get: function () {
      return this.domain_8ag30o$_0;
    },
    set: function (domain) {
      this.domain_8ag30o$_0 = domain;
    }
  });
  Object.defineProperty(SequentialScale.prototype, 'clamp', {
    get: function () {
      return this.clamp_gaoomr$_0;
    },
    set: function (clamp) {
      this.clamp_gaoomr$_0 = clamp;
    }
  });
  SequentialScale.prototype.invoke_za3lpa$ = function (domainValue) {
    return this.invoke_14dthe$(domainValue);
  };
  SequentialScale.prototype.invoke_14dthe$ = function (domainValue) {
    var uninterpolatedDomain = uninterpolateNumber(this.domain.start, this.domain.end)(domainValue);
    if (this.clamp)
      uninterpolatedDomain = uninterpolatedDomain.coerceToDefault();
    return this.interpolator(uninterpolatedDomain);
  };
  SequentialScale.prototype.ticks_za3lpa$$default = function (count) {
    return ticks(this.domain.start, this.domain.end, count);
  };
  SequentialScale.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'SequentialScale',
    interfaces: [StrictlyContinuousDomain, ClampableScale, Tickable]
  };
  function ThresholdScale() {
    this._domain = listOf_0(0.5);
    this._range = emptyList();
  }
  Object.defineProperty(ThresholdScale.prototype, 'range', {
    get: function () {
      return toList(this._range);
    },
    set: function (value) {
      this._range = toList(value);
    }
  });
  Object.defineProperty(ThresholdScale.prototype, 'domain', {
    get: function () {
      return toList(this._domain);
    },
    set: function (value) {
      if (!equals(sorted(value), value)) {
        var message = 'The domain must be sorted in ascending order.';
        throw IllegalArgumentException_init(message.toString());
      }
      this._domain = toList(value);
    }
  });
  ThresholdScale.prototype.invoke_za3lpa$ = function (domainValue) {
    return this.invoke_11rb$(domainValue);
  };
  ThresholdScale.prototype.invoke_11rb$ = function (domainValue) {
    if (!(this._range.size === (this._domain.size + 1 | 0))) {
      var message = 'The range size (actual: ' + this._range.size + ') must be 1 more than the domain size (actual: ' + this._domain.size + ').';
      throw IllegalStateException_init_0(message.toString());
    }
    return this._range.get_za3lpa$(bisectRight(this._domain, domainValue, naturalOrder(), 0, this._domain.size));
  };
  ThresholdScale.prototype.invertExtent_11rb$ = function (rangeValue) {
    var tmp$;
    if (!(this._range.size === (this._domain.size + 1 | 0))) {
      var message = 'The range size (actual: ' + this._range.size + ') must be 1 more than the domain size (actual: ' + this._domain.size + ').';
      throw IllegalStateException_init_0(message.toString());
    }
    var i = this._range.indexOf_11rb$(rangeValue);
    var size = this._range.size - 1 | 0;
    if (i < 0 || i > size)
      tmp$ = listOf([kotlin_js_internal_DoubleCompanionObject.NaN, kotlin_js_internal_DoubleCompanionObject.NaN]);
    else if (i === 0)
      tmp$ = listOf([kotlin_js_internal_DoubleCompanionObject.NaN, first(this._domain)]);
    else if (i === size)
      tmp$ = listOf([last(this.domain), kotlin_js_internal_DoubleCompanionObject.NaN]);
    else
      tmp$ = listOf([this._domain.get_za3lpa$(i - 1 | 0), this._domain.get_za3lpa$(i)]);
    return tmp$;
  };
  ThresholdScale.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ThresholdScale',
    interfaces: [DiscreteDomain, DiscreteRange, Scale]
  };
  function Comparator$ObjectLiteral(closure$comparison) {
    this.closure$comparison = closure$comparison;
  }
  Comparator$ObjectLiteral.prototype.compare = function (a, b) {
    return this.closure$comparison(a, b);
  };
  Comparator$ObjectLiteral.$metadata$ = {kind: Kind_CLASS, interfaces: [Comparator]};
  function dateComparator$lambda(a, b) {
    return a.millisecondsBetween_fw2154$(b).toNumber() > 0 ? -1 : a.millisecondsBetween_fw2154$(b).toNumber() < 0 ? 1 : 0;
  }
  var dateComparator;
  function TickInterval(interval, step, duration) {
    this.interval = interval;
    this.step = step;
    this.duration = duration;
  }
  TickInterval.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'TickInterval',
    interfaces: []
  };
  TickInterval.prototype.component1 = function () {
    return this.interval;
  };
  TickInterval.prototype.component2 = function () {
    return this.step;
  };
  TickInterval.prototype.component3 = function () {
    return this.duration;
  };
  TickInterval.prototype.copy_lpv2mk$ = function (interval, step, duration) {
    return new TickInterval(interval === void 0 ? this.interval : interval, step === void 0 ? this.step : step, duration === void 0 ? this.duration : duration);
  };
  TickInterval.prototype.toString = function () {
    return 'TickInterval(interval=' + Kotlin.toString(this.interval) + (', step=' + Kotlin.toString(this.step)) + (', duration=' + Kotlin.toString(this.duration)) + ')';
  };
  TickInterval.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.interval) | 0;
    result = result * 31 + Kotlin.hashCode(this.step) | 0;
    result = result * 31 + Kotlin.hashCode(this.duration) | 0;
    return result;
  };
  TickInterval.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.interval, other.interval) && Kotlin.equals(this.step, other.step) && Kotlin.equals(this.duration, other.duration)))));
  };
  var tickIntervals;
  function TimeScale(interpolateRange, uninterpolateRange, rangeComparator) {
    if (uninterpolateRange === void 0)
      uninterpolateRange = null;
    if (rangeComparator === void 0)
      rangeComparator = null;
    ContinuousScale.call(this, interpolateRange, uninterpolateRange, rangeComparator);
    this._domain.clear();
    this._domain.addAll_brywnq$(listOf([date_0(2000, 1, 1), date_0(2000, 1, 2)]));
  }
  function TimeScale$uninterpolateDomain$lambda(closure$from, closure$to) {
    return function (date) {
      return !equals(closure$from.millisecondsBetween_fw2154$(closure$to), L0) ? new Percent(closure$from.millisecondsBetween_fw2154$(date).toNumber() / closure$from.millisecondsBetween_fw2154$(closure$to).toNumber()) : get_pct(0);
    };
  }
  TimeScale.prototype.uninterpolateDomain_xwzc9q$ = function (from, to) {
    return TimeScale$uninterpolateDomain$lambda(from, to);
  };
  function TimeScale$interpolateDomain$lambda(closure$from, closure$diff) {
    return function (percent) {
      var date_0 = date(closure$from);
      var milliseconds = Kotlin.Long.fromNumber(percent.value).multiply(closure$diff);
      date_0.plusMilliseconds_s8cxhz$(milliseconds);
      return date_0;
    };
  }
  TimeScale.prototype.interpolateDomain_xwzc9q$ = function (from, to) {
    var diff = from.millisecondsBetween_fw2154$(to);
    return TimeScale$interpolateDomain$lambda(from, diff);
  };
  TimeScale.prototype.domainComparator = function () {
    return dateComparator;
  };
  TimeScale.prototype.nice_za3lpa$$default = function (count) {
    var start = first(this._domain);
    var end = last(this._domain);
    var interval = this.tickInterval_0(count, start, end);
    this.niceDomain_0(end, start, interval);
    this.rescale();
  };
  TimeScale.prototype.tickInterval_0 = function (count, start, end) {
    var target = start.millisecondsBetween_fw2154$(end).div(Kotlin.Long.fromInt(count));
    var $receiver = tickIntervals;
    var destination = ArrayList_init_0(collectionSizeOrDefault($receiver, 10));
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(item.duration);
    }
    var intervalIndex = bisectRight(destination, target, naturalOrder());
    var step;
    var interval = time.timeYear;
    if (intervalIndex === tickIntervals.size) {
      step = numberToInt(tickStep(start.getTime() / time.durationYear.toNumber(), end.getTime() / time.durationYear.toNumber(), count));
    }
     else if (intervalIndex > 0) {
      var l = target.toNumber() / tickIntervals.get_za3lpa$(intervalIndex - 1 | 0).duration.toNumber();
      var l1 = tickIntervals.get_za3lpa$(intervalIndex).duration.toNumber() / target.toNumber();
      var tickInterval = tickIntervals.get_za3lpa$(l < l1 ? intervalIndex - 1 | 0 : intervalIndex);
      step = tickInterval.step;
      interval = tickInterval.interval;
    }
     else {
      step = numberToInt(tickStep(start.getTime(), end.getTime(), count));
      interval = time.timeMillisecond;
    }
    if (step > 0)
      interval = interval.every_za3lpa$(step);
    return interval;
  };
  TimeScale.prototype.niceDomain_0 = function (end, start, interval) {
    var first = 0;
    var last = this._domain.size - 1 | 0;
    if (end.isBefore_fw2154$(start)) {
      first = this.domain.size - 1 | 0;
      last = 0;
    }
    var x0 = this._domain.get_za3lpa$(first);
    var x1 = this._domain.get_za3lpa$(last);
    this._domain.set_wxm5ur$(first, interval.floor_fw2154$(x0));
    this._domain.set_wxm5ur$(last, interval.ceil_fw2154$(x1));
  };
  TimeScale.prototype.ticks_za3lpa$$default = function (count) {
    var first = 0;
    var last = this._domain.size - 1 | 0;
    var start = this._domain.get_za3lpa$(first);
    var end = this._domain.get_za3lpa$(last);
    if (equals(start.millisecondsBetween_fw2154$(end), L0)) {
      return emptyList();
    }
    var reversed_0 = end.isBefore_fw2154$(start);
    if (reversed_0) {
      first = this._domain.size - 1 | 0;
      last = 0;
      start = this._domain.get_za3lpa$(first);
      end = this._domain.get_za3lpa$(last);
    }
    var endPlus = date(end);
    endPlus.plusMilliseconds_s8cxhz$(L1);
    var tickInterval = this.tickInterval_0(count, start, end);
    var ticks = tickInterval.range_4ahvhd$(start, endPlus);
    return reversed_0 ? reversed(ticks) : ticks;
  };
  TimeScale.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'TimeScale',
    interfaces: [Tickable, NiceableScale, ContinuousScale]
  };
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$scale = package$data2viz.scale || (package$data2viz.scale = {});
  package$scale.BandedScale = BandedScale;
  package$scale.BandScale = BandScale;
  package$scale.PointScale = PointScale;
  package$scale.LinearScale = LinearScale;
  package$scale.ContinuousScale = ContinuousScale;
  package$scale.bisectRight_9i1s2q$ = bisectRight;
  package$scale.bisectLeft_9i1s2q$ = bisectLeft;
  package$scale.LogScale = LogScale;
  package$scale.IndexableDomain = IndexableDomain;
  package$scale.OrdinalScale = OrdinalScale;
  package$scale.PowerScale = PowerScale;
  package$scale.QuantileScale = QuantileScale;
  package$scale.quantile_qeacfe$ = quantile;
  package$scale.QuantizeScale = QuantizeScale;
  package$scale.Scale = Scale;
  package$scale.ContinuousDomain = ContinuousDomain;
  package$scale.DiscreteDomain = DiscreteDomain;
  package$scale.StrictlyContinuousDomain = StrictlyContinuousDomain;
  package$scale.ContinuousRangeScale = ContinuousRangeScale;
  package$scale.DiscreteRange = DiscreteRange;
  package$scale.StrictlyContinuousRange = StrictlyContinuousRange;
  package$scale.FirstLastRange = FirstLastRange;
  package$scale.StrictlyContinuous = StrictlyContinuous;
  package$scale.intervalOf_gnx7yi$ = intervalOf;
  package$scale.intervalOf_i5x0yv$ = intervalOf_0;
  package$scale.ClampableScale = ClampableScale;
  package$scale.NiceableScale = NiceableScale;
  package$scale.InvertableScale = InvertableScale;
  package$scale.Tickable = Tickable;
  Object.defineProperty(Scales.prototype, 'Continuous', {
    get: Scales$Continuous_getInstance
  });
  Object.defineProperty(Scales.prototype, 'Quantized', {
    get: Scales$Quantized_getInstance
  });
  Object.defineProperty(Scales.prototype, 'Discrete', {
    get: Scales$Discrete_getInstance
  });
  Object.defineProperty(package$scale, 'Scales', {
    get: Scales_getInstance
  });
  Object.defineProperty(ScalesChromatic.prototype, 'Continuous', {
    get: ScalesChromatic$Continuous_getInstance
  });
  Object.defineProperty(ScalesChromatic$Sequential.prototype, 'SingleHue', {
    get: ScalesChromatic$Sequential$SingleHue_getInstance
  });
  Object.defineProperty(ScalesChromatic$Sequential.prototype, 'MultiHue', {
    get: ScalesChromatic$Sequential$MultiHue_getInstance
  });
  Object.defineProperty(ScalesChromatic$Sequential.prototype, 'Diverging', {
    get: ScalesChromatic$Sequential$Diverging_getInstance
  });
  Object.defineProperty(ScalesChromatic$Sequential.prototype, 'Cyclical', {
    get: ScalesChromatic$Sequential$Cyclical_getInstance
  });
  Object.defineProperty(ScalesChromatic.prototype, 'Sequential', {
    get: ScalesChromatic$Sequential_getInstance
  });
  Object.defineProperty(ScalesChromatic.prototype, 'Discrete', {
    get: ScalesChromatic$Discrete_getInstance
  });
  Object.defineProperty(package$scale, 'ScalesChromatic', {
    get: ScalesChromatic_getInstance
  });
  package$scale.SequentialScale = SequentialScale;
  package$scale.ThresholdScale = ThresholdScale;
  Object.defineProperty(package$scale, 'dateComparator', {
    get: function () {
      return dateComparator;
    }
  });
  package$scale.TimeScale = TimeScale;
  BandedScale.prototype.start = StrictlyContinuousRange.prototype.start;
  BandedScale.prototype.end = StrictlyContinuousRange.prototype.end;
  BandedScale.prototype.ticks_za3lpa$ = Tickable.prototype.ticks_za3lpa$;
  ContinuousScale.prototype.start = ContinuousRangeScale.prototype.start;
  ContinuousScale.prototype.end = ContinuousRangeScale.prototype.end;
  LinearScale.prototype.nice_za3lpa$ = NiceableScale.prototype.nice_za3lpa$;
  LinearScale.prototype.ticks_za3lpa$ = Tickable.prototype.ticks_za3lpa$;
  SequentialScale.prototype.ticks_za3lpa$ = Tickable.prototype.ticks_za3lpa$;
  TimeScale.prototype.nice_za3lpa$ = NiceableScale.prototype.nice_za3lpa$;
  TimeScale.prototype.ticks_za3lpa$ = Tickable.prototype.ticks_za3lpa$;
  dateComparator = new Comparator$ObjectLiteral(dateComparator$lambda);
  tickIntervals = listOf([new TickInterval(time.timeSecond, 1, time.durationSecond), new TickInterval(time.timeSecond, 5, Kotlin.Long.fromInt(5).multiply(time.durationSecond)), new TickInterval(time.timeSecond, 15, Kotlin.Long.fromInt(15).multiply(time.durationSecond)), new TickInterval(time.timeSecond, 30, Kotlin.Long.fromInt(30).multiply(time.durationSecond)), new TickInterval(time.timeMinute, 1, time.durationMinute), new TickInterval(time.timeMinute, 5, Kotlin.Long.fromInt(5).multiply(time.durationMinute)), new TickInterval(time.timeMinute, 15, Kotlin.Long.fromInt(15).multiply(time.durationMinute)), new TickInterval(time.timeMinute, 30, Kotlin.Long.fromInt(30).multiply(time.durationMinute)), new TickInterval(time.timeHour, 1, time.durationHour), new TickInterval(time.timeHour, 3, Kotlin.Long.fromInt(3).multiply(time.durationHour)), new TickInterval(time.timeHour, 6, Kotlin.Long.fromInt(6).multiply(time.durationHour)), new TickInterval(time.timeHour, 12, Kotlin.Long.fromInt(12).multiply(time.durationHour)), new TickInterval(time.timeDay, 1, time.durationDay), new TickInterval(time.timeDay, 2, Kotlin.Long.fromInt(2).multiply(time.durationDay)), new TickInterval(time.timeSunday, 1, time.durationWeek), new TickInterval(time.timeMonth, 1, time.durationMonth), new TickInterval(time.timeMonth, 3, Kotlin.Long.fromInt(3).multiply(time.durationMonth)), new TickInterval(time.timeYear, 1, time.durationYear)]);
  Kotlin.defineModule('d2v-scale-js', _);
  return _;
}));

//# sourceMappingURL=d2v-scale-js.js.map
