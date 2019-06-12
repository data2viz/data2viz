(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin', 'd2v-core-js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'), require('d2v-core-js'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'd2v-color-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'd2v-color-js'.");
    }
    if (typeof this['d2v-core-js'] === 'undefined') {
      throw new Error("Error loading module 'd2v-color-js'. Its dependency 'd2v-core-js' was not found. Please, check whether 'd2v-core-js' is loaded prior to 'd2v-color-js'.");
    }
    root['d2v-color-js'] = factory(typeof this['d2v-color-js'] === 'undefined' ? {} : this['d2v-color-js'], kotlin, this['d2v-core-js']);
  }
}(this, function (_, Kotlin, $module$d2v_core_js) {
  'use strict';
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var Kind_INTERFACE = Kotlin.Kind.INTERFACE;
  var get_deg = $module$d2v_core_js.io.data2viz.math.get_deg_rcaex3$;
  var get_pct = $module$d2v_core_js.io.data2viz.math.get_pct_rcaex3$;
  var Percent = $module$d2v_core_js.io.data2viz.math.Percent;
  var roundToInt = Kotlin.kotlin.math.roundToInt_yrwdxr$;
  var Angle = $module$d2v_core_js.io.data2viz.math.Angle;
  var math = Kotlin.kotlin.math;
  var round = Kotlin.kotlin.math.round_14dthe$;
  var numberToInt = Kotlin.numberToInt;
  var Math_0 = Math;
  var coerceIn = Kotlin.kotlin.ranges.coerceIn_e4yvb3$;
  var Kind_OBJECT = Kotlin.Kind.OBJECT;
  var lazy = Kotlin.kotlin.lazy_klfg04$;
  var listOf = Kotlin.kotlin.collections.listOf_i5x0yv$;
  var coerceAtLeast = Kotlin.kotlin.ranges.coerceAtLeast_dqglrj$;
  var coerceAtMost = Kotlin.kotlin.ranges.coerceAtMost_dqglrj$;
  var IntRange = Kotlin.kotlin.ranges.IntRange;
  var IllegalArgumentException_init = Kotlin.kotlin.IllegalArgumentException_init_pdl1vj$;
  var collectionSizeOrDefault = Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$;
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_ww73n8$;
  var Point = $module$d2v_core_js.io.data2viz.geom.Point;
  var toList = Kotlin.kotlin.collections.toList_7wnvza$;
  var ArrayList_init_0 = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  var toInt = Kotlin.kotlin.text.toInt_6ic1pp$;
  var Regex_init = Kotlin.kotlin.text.Regex_init_61zpoe$;
  function ColorStop(percent, color) {
    this.percent = percent;
    this.color = color;
  }
  ColorStop.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ColorStop',
    interfaces: []
  };
  ColorStop.prototype.component1 = function () {
    return this.percent;
  };
  ColorStop.prototype.component2 = function () {
    return this.color;
  };
  ColorStop.prototype.copy_exe2c5$ = function (percent, color) {
    return new ColorStop(percent === void 0 ? this.percent : percent, color === void 0 ? this.color : color);
  };
  ColorStop.prototype.toString = function () {
    return 'ColorStop(percent=' + Kotlin.toString(this.percent) + (', color=' + Kotlin.toString(this.color)) + ')';
  };
  ColorStop.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.percent) | 0;
    result = result * 31 + Kotlin.hashCode(this.color) | 0;
    return result;
  };
  ColorStop.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.percent, other.percent) && Kotlin.equals(this.color, other.color)))));
  };
  function ColorOrGradient() {
  }
  ColorOrGradient.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'ColorOrGradient',
    interfaces: []
  };
  function Gradient() {
  }
  Gradient.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Gradient',
    interfaces: [ColorOrGradient]
  };
  function Color() {
  }
  Color.prototype.brighten_14dthe$ = function (strength, callback$default) {
    if (strength === void 0)
      strength = 1.0;
    return callback$default ? callback$default(strength) : this.brighten_14dthe$$default(strength);
  };
  Color.prototype.darken_14dthe$ = function (strength, callback$default) {
    if (strength === void 0)
      strength = 1.0;
    return callback$default ? callback$default(strength) : this.darken_14dthe$$default(strength);
  };
  Color.prototype.saturate_14dthe$ = function (strength, callback$default) {
    if (strength === void 0)
      strength = 1.0;
    return callback$default ? callback$default(strength) : this.saturate_14dthe$$default(strength);
  };
  Color.prototype.desaturate_14dthe$ = function (strength, callback$default) {
    if (strength === void 0)
      strength = 1.0;
    return callback$default ? callback$default(strength) : this.desaturate_14dthe$$default(strength);
  };
  Color.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Color',
    interfaces: [ColorOrGradient]
  };
  var Kn;
  var Xn;
  var Yn;
  var Zn;
  var t0;
  var t1;
  var t2;
  var t3;
  var deg60toRad;
  var deg240toRad;
  var angle120deg;
  function toLaba($receiver) {
    var labB = rgb2xyz($receiver.r);
    var labA = rgb2xyz($receiver.g);
    var labL = rgb2xyz($receiver.b);
    var x = xyz2lab((0.4124564 * labB + 0.3575761 * labA + 0.1804375 * labL) / Xn);
    var y = xyz2lab((0.2126729 * labB + 0.7151522 * labA + 0.072175 * labL) / Yn);
    var z = xyz2lab((0.0193339 * labB + 0.119192 * labA + 0.9503041 * labL) / Zn);
    return Colors_getInstance().lab_tuy7uw$(get_pct(116.0 * y - 16), 500.0 * (x - y), 200.0 * (y - z), $receiver.alpha);
  }
  function toHsla($receiver) {
    var tmp$;
    var rPercent = $receiver.r / 255.0;
    var gPercent = $receiver.g / 255.0;
    var bPercent = $receiver.b / 255.0;
    var minPercent = Math_0.min(rPercent, gPercent, bPercent);
    var maxPercent = Math_0.max(rPercent, gPercent, bPercent);
    var h = 0.0;
    var s = maxPercent - minPercent;
    var l = (maxPercent + minPercent) / 2.0;
    if (s !== 0.0) {
      if (rPercent === maxPercent)
        tmp$ = gPercent < bPercent ? (gPercent - bPercent) / s + 6.0 : (gPercent - bPercent) / s;
      else if (gPercent === maxPercent)
        tmp$ = (bPercent - rPercent) / s + 2.0;
      else
        tmp$ = (rPercent - gPercent) / s + 4.0;
      h = tmp$;
      s /= l < 0.5 ? maxPercent + minPercent : 2 - maxPercent - minPercent;
      h *= 60.0;
    }
     else {
      s = l > 0 && l < 1 ? 0.0 : h;
    }
    return Colors_getInstance().hsl_wqq93y$(get_deg(h), new Percent(s), new Percent(l), $receiver.alpha);
  }
  function toRgba($receiver) {
    var y = ($receiver.labL.value * 100.0 + 16) / 116.0;
    var x = y + $receiver.labA / 500.0;
    var z = y - $receiver.labB / 200.0;
    y = Yn * lab2xyz(y);
    x = Xn * lab2xyz(x);
    z = Zn * lab2xyz(z);
    return Colors_getInstance().rgb_o6sw6o$(xyz2rgb(3.2404542 * x - 1.5371385 * y - 0.4985314 * z), xyz2rgb(-0.969266 * x + 1.8760108 * y + 0.041556 * z), xyz2rgb(0.0556434 * x - 0.2040259 * y + 1.0572252 * z), $receiver.alpha);
  }
  function toRgba_0($receiver) {
    if ($receiver.isAchromatic())
      return Colors_getInstance().rgb_o6sw6o$(roundToInt($receiver.l.value * 255), roundToInt($receiver.l.value * 255), roundToInt($receiver.l.value * 255), $receiver.alpha);
    else {
      var q = $receiver.l.compareTo_o5f5ne$(get_pct(50)) < 0 ? $receiver.l.times_o5f5ne$(get_pct(100).plus_o5f5ne$($receiver.s)) : $receiver.l.plus_o5f5ne$($receiver.s).minus_o5f5ne$($receiver.l.times_o5f5ne$($receiver.s));
      var p = new Percent(2 * $receiver.l.value - q.value);
      return Colors_getInstance().rgb_o6sw6o$(roundToInt(hue2rgb(p.value, q.value, $receiver.h.plus_5t6zck$(angle120deg)) * 255), roundToInt(hue2rgb(p.value, q.value, $receiver.h) * 255), roundToInt(hue2rgb(p.value, q.value, $receiver.h.minus_5t6zck$(angle120deg)) * 255), $receiver.alpha);
    }
  }
  function toLaba_0($receiver) {
    return Colors_getInstance().lab_tuy7uw$($receiver.l, $receiver.h.cos * $receiver.c, $receiver.h.sin * $receiver.c, $receiver.alpha);
  }
  function toHcla($receiver) {
    var y = $receiver.labB;
    var x = $receiver.labA;
    var hue = (new Angle(Math_0.atan2(y, x))).normalize();
    var x_0 = $receiver.labA * $receiver.labA + $receiver.labB * $receiver.labB;
    var c = Math_0.sqrt(x_0);
    return Colors_getInstance().hcl_vn5x52$(hue, c, $receiver.labL, $receiver.alpha);
  }
  function hue2rgb(p, q, hue) {
    var tmp$;
    var hd = hue.normalize();
    if (hd.rad < deg60toRad)
      tmp$ = p + (q - p) * (hd.rad / deg60toRad);
    else if (hd.rad < math.PI)
      tmp$ = q;
    else if (hd.rad < deg240toRad)
      tmp$ = p + (q - p) * ((deg240toRad - hd.rad) / deg60toRad);
    else
      tmp$ = p;
    return tmp$;
  }
  function toLuminance$chan2Lumi($receiver) {
    var x = $receiver / 255.0;
    var tmp$;
    if (x <= 0.03928)
      tmp$ = x / 12.92;
    else {
      var $receiver_0 = (x + 0.055) / 1.055;
      tmp$ = Math_0.pow($receiver_0, 2.4);
    }
    return tmp$;
  }
  function toLuminance($receiver) {
    var chan2Lumi = toLuminance$chan2Lumi;
    return (new Percent(0.2126 * chan2Lumi($receiver.r) + 0.7152 * chan2Lumi($receiver.g) + 0.0722 * chan2Lumi($receiver.b))).coerceToDefault();
  }
  function xyz2lab(value) {
    var tmp$;
    if (value > t3) {
      var x = 1 / 3.0;
      tmp$ = Math_0.pow(value, x);
    }
     else
      tmp$ = value / t2 + t0;
    return tmp$;
  }
  function rgb2xyz(value) {
    var percent = value / 255.0;
    var tmp$;
    if (percent <= 0.04045)
      tmp$ = percent / 12.92;
    else {
      var $receiver = (percent + 0.055) / 1.055;
      tmp$ = Math_0.pow($receiver, 2.4);
    }
    return tmp$;
  }
  function lab2xyz(value) {
    return value > t1 ? value * value * value : t2 * (value - t0);
  }
  function xyz2rgb(value) {
    var tmp$;
    if (value <= 0.0031308)
      tmp$ = numberToInt(round(12.92 * value * 255));
    else {
      var x = 1 / 2.4;
      tmp$ = numberToInt(round(255 * (1.055 * Math_0.pow(value, x) - 0.055)));
    }
    return tmp$;
  }
  function Colors() {
    Colors_instance = this;
  }
  Colors.prototype.rgb_g6jfbk$ = function (rgb, alpha) {
    if (alpha === void 0)
      alpha = get_pct(100);
    return new RgbColor(rgb, alpha);
  };
  Colors.prototype.rgb_o6sw6o$ = function (red, green, blue, alpha) {
    if (alpha === void 0)
      alpha = get_pct(100);
    var rgb = (coerceIn(red, 0, 255) << 16) + (coerceIn(green, 0, 255) << 8) + coerceIn(blue, 0, 255) | 0;
    return this.rgb_g6jfbk$(rgb, alpha);
  };
  Colors.prototype.lab_tuy7uw$ = function (lightness, aComponent, bComponent, alpha) {
    if (alpha === void 0)
      alpha = get_pct(100);
    return new LabColor(lightness, aComponent, bComponent, alpha);
  };
  Colors.prototype.lab_hvjaok$ = function (lightness, aComponent, bComponent, alpha) {
    if (alpha === void 0)
      alpha = get_pct(100);
    return new LabColor(lightness, aComponent, bComponent, alpha);
  };
  Colors.prototype.hsl_wqq93y$ = function (hue, saturation, lightness, alpha) {
    if (alpha === void 0)
      alpha = get_pct(100);
    return new HslColor(hue, saturation, lightness, alpha);
  };
  Colors.prototype.hcl_vn5x52$ = function (hue, chroma, luminance, alpha) {
    if (alpha === void 0)
      alpha = get_pct(100);
    return new HclColor(hue, chroma, luminance, alpha);
  };
  Colors.prototype.hcl_i3ymjq$ = function (hue, chroma, luminance, alpha) {
    if (alpha === void 0)
      alpha = get_pct(100);
    return new HclColor(hue, chroma, luminance, alpha);
  };
  Colors.prototype.lch_3vco9i$ = function (luminance, chroma, hue, alpha) {
    if (alpha === void 0)
      alpha = get_pct(100);
    return this.hcl_vn5x52$(hue, chroma, luminance, alpha);
  };
  Colors.prototype.lch_1evxp6$ = function (luminance, chroma, hue, alpha) {
    if (alpha === void 0)
      alpha = get_pct(100);
    return this.hcl_i3ymjq$(hue, chroma, luminance, alpha);
  };
  function Colors$Gradient() {
    Colors$Gradient_instance = this;
  }
  Colors$Gradient.prototype.linear_840z2k$ = function (from, to) {
    return new LinearGradientFirstColorBuilder(from, to);
  };
  Colors$Gradient.prototype.radial_25aop5$ = function (center, radius) {
    return new RadialGradientFirstColorBuilder(center, radius);
  };
  Colors$Gradient.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Gradient',
    interfaces: []
  };
  var Colors$Gradient_instance = null;
  function Colors$Gradient_getInstance() {
    if (Colors$Gradient_instance === null) {
      new Colors$Gradient();
    }
    return Colors$Gradient_instance;
  }
  function Colors$Web() {
    Colors$Web_instance = this;
    this.aliceblue = get_col(15792383);
    this.antiquewhite = get_col(16444375);
    this.aqua = get_col(65535);
    this.aquamarine = get_col(8388564);
    this.azure = get_col(15794175);
    this.beige = get_col(16119260);
    this.bisque = get_col(16770244);
    this.black = get_col(0);
    this.blanchedalmond = get_col(16772045);
    this.blue = get_col(255);
    this.blueviolet = get_col(9055202);
    this.brown = get_col(10824234);
    this.burlywood = get_col(14596231);
    this.cadetblue = get_col(6266528);
    this.chartreuse = get_col(8388352);
    this.chocolate = get_col(13789470);
    this.coral = get_col(16744272);
    this.cornflowerblue = get_col(6591981);
    this.cornsilk = get_col(16775388);
    this.crimson = get_col(14423100);
    this.cyan = get_col(65535);
    this.darkblue = get_col(139);
    this.darkcyan = get_col(35723);
    this.darkgoldenrod = get_col(12092939);
    this.darkgray = get_col(11119017);
    this.darkgreen = get_col(25600);
    this.darkgrey = get_col(11119017);
    this.darkkhaki = get_col(12433259);
    this.darkmagenta = get_col(9109643);
    this.darkolivegreen = get_col(5597999);
    this.darkorange = get_col(16747520);
    this.darkorchid = get_col(10040012);
    this.darkred = get_col(9109504);
    this.darksalmon = get_col(15308410);
    this.darkseagreen = get_col(9419919);
    this.darkslateblue = get_col(4734347);
    this.darkslategray = get_col(3100495);
    this.darkslategrey = get_col(3100495);
    this.darkturquoise = get_col(52945);
    this.darkviolet = get_col(9699539);
    this.deeppink = get_col(16716947);
    this.deepskyblue = get_col(49151);
    this.dimgray = get_col(6908265);
    this.dimgrey = get_col(6908265);
    this.dodgerblue = get_col(2003199);
    this.firebrick = get_col(11674146);
    this.floralwhite = get_col(16775920);
    this.forestgreen = get_col(2263842);
    this.fuchsia = get_col(16711935);
    this.gainsboro = get_col(14474460);
    this.ghostwhite = get_col(16316671);
    this.gold = get_col(16766720);
    this.goldenrod = get_col(14329120);
    this.gray = get_col(8421504);
    this.green = get_col(32768);
    this.greenyellow = get_col(11403055);
    this.grey = get_col(8421504);
    this.honeydew = get_col(15794160);
    this.hotpink = get_col(16738740);
    this.indianred = get_col(13458524);
    this.indigo = get_col(4915330);
    this.ivory = get_col(16777200);
    this.khaki = get_col(15787660);
    this.lavender = get_col(15132410);
    this.lavenderblush = get_col(16773365);
    this.lawngreen = get_col(8190976);
    this.lemonchiffon = get_col(16775885);
    this.lightblue = get_col(11393254);
    this.lightcoral = get_col(15761536);
    this.lightcyan = get_col(14745599);
    this.lightgoldenrodyellow = get_col(16448210);
    this.lightgray = get_col(13882323);
    this.lightgreen = get_col(9498256);
    this.lightgrey = get_col(13882323);
    this.lightpink = get_col(16758465);
    this.lightsalmon = get_col(16752762);
    this.lightseagreen = get_col(2142890);
    this.lightskyblue = get_col(8900346);
    this.lightslategray = get_col(7833753);
    this.lightslategrey = get_col(7833753);
    this.lightsteelblue = get_col(11584734);
    this.lightyellow = get_col(16777184);
    this.lime = get_col(65280);
    this.limegreen = get_col(3329330);
    this.linen = get_col(16445670);
    this.magenta = get_col(16711935);
    this.maroon = get_col(8388608);
    this.mediumaquamarine = get_col(6737322);
    this.mediumblue = get_col(205);
    this.mediumorchid = get_col(12211667);
    this.mediumpurple = get_col(9662683);
    this.mediumseagreen = get_col(3978097);
    this.mediumslateblue = get_col(8087790);
    this.mediumspringgreen = get_col(64154);
    this.mediumturquoise = get_col(4772300);
    this.mediumvioletred = get_col(13047173);
    this.midnightblue = get_col(1644912);
    this.mintcream = get_col(16121850);
    this.mistyrose = get_col(16770273);
    this.moccasin = get_col(16770229);
    this.navajowhite = get_col(16768685);
    this.navy = get_col(128);
    this.oldlace = get_col(16643558);
    this.olive = get_col(8421376);
    this.olivedrab = get_col(7048739);
    this.orange = get_col(16753920);
    this.orangered = get_col(16729344);
    this.orchid = get_col(14315734);
    this.palegoldenrod = get_col(15657130);
    this.palegreen = get_col(10025880);
    this.paleturquoise = get_col(11529966);
    this.palevioletred = get_col(14381203);
    this.papayawhip = get_col(16773077);
    this.peachpuff = get_col(16767673);
    this.peru = get_col(13468991);
    this.pink = get_col(16761035);
    this.plum = get_col(14524637);
    this.powderblue = get_col(11591910);
    this.purple = get_col(8388736);
    this.rebeccapurple = get_col(6697881);
    this.red = get_col(16711680);
    this.rosybrown = get_col(12357519);
    this.royalblue = get_col(4286945);
    this.saddlebrown = get_col(9127187);
    this.salmon = get_col(16416882);
    this.sandybrown = get_col(16032864);
    this.seagreen = get_col(3050327);
    this.seashell = get_col(16774638);
    this.sienna = get_col(10506797);
    this.silver = get_col(12632256);
    this.skyblue = get_col(8900331);
    this.slateblue = get_col(6970061);
    this.slategray = get_col(7372944);
    this.slategrey = get_col(7372944);
    this.snow = get_col(16775930);
    this.springgreen = get_col(65407);
    this.steelblue = get_col(4620980);
    this.tan = get_col(13808780);
    this.teal = get_col(32896);
    this.thistle = get_col(14204888);
    this.tomato = get_col(16737095);
    this.turquoise = get_col(4251856);
    this.violet = get_col(15631086);
    this.wheat = get_col(16113331);
    this.white = get_col(16777215);
    this.whitesmoke = get_col(16119285);
    this.yellow = get_col(16776960);
    this.yellowgreen = get_col(10145074);
  }
  Colors$Web.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Web',
    interfaces: []
  };
  var Colors$Web_instance = null;
  function Colors$Web_getInstance() {
    if (Colors$Web_instance === null) {
      new Colors$Web();
    }
    return Colors$Web_instance;
  }
  Colors.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Colors',
    interfaces: []
  };
  var Colors_instance = null;
  function Colors_getInstance() {
    if (Colors_instance === null) {
      new Colors();
    }
    return Colors_instance;
  }
  function EncodedColors(colorsAsString) {
    EncodedColors$Companion_getInstance();
    if (!(colorsAsString.length % 6 === 0)) {
      var message = 'colorsAsString size should be a multiple of 6';
      throw IllegalArgumentException_init(message.toString());
    }
    this.colors_xs48u0$_0 = lazy(EncodedColors$colors$lambda(colorsAsString));
  }
  function EncodedColors$Companion() {
    EncodedColors$Companion_instance = this;
    this.category10_wu4fg9$_0 = lazy(EncodedColors$Companion$category10$lambda);
    this.category20_wu4g6y$_0 = lazy(EncodedColors$Companion$category20$lambda);
    this.category20b_ni97bs$_0 = lazy(EncodedColors$Companion$category20b$lambda);
    this.category20c_ni97cn$_0 = lazy(EncodedColors$Companion$category20c$lambda);
    this.accents_uv29e3$_0 = lazy(EncodedColors$Companion$accents$lambda);
    this.dark2_17tqde$_0 = lazy(EncodedColors$Companion$dark2$lambda);
    this.paired_n8cpp$_0 = lazy(EncodedColors$Companion$paired$lambda);
    this.pastel1_orqx0m$_0 = lazy(EncodedColors$Companion$pastel1$lambda);
    this.pastel2_orqx1h$_0 = lazy(EncodedColors$Companion$pastel2$lambda);
    this.set1_8w7u2h$_0 = lazy(EncodedColors$Companion$set1$lambda);
    this.set2_8w7u1m$_0 = lazy(EncodedColors$Companion$set2$lambda);
    this.set3_8w7u0r$_0 = lazy(EncodedColors$Companion$set3$lambda);
    this.BrBG3_en8azk$_0 = lazy(EncodedColors$Companion$BrBG3$lambda);
    this.BrBG4_en8ayp$_0 = lazy(EncodedColors$Companion$BrBG4$lambda);
    this.BrBG5_en8axu$_0 = lazy(EncodedColors$Companion$BrBG5$lambda);
    this.BrBG6_en8awz$_0 = lazy(EncodedColors$Companion$BrBG6$lambda);
    this.BrBG7_en8aw4$_0 = lazy(EncodedColors$Companion$BrBG7$lambda);
    this.BrBG8_en8av9$_0 = lazy(EncodedColors$Companion$BrBG8$lambda);
    this.BrBG9_en8aue$_0 = lazy(EncodedColors$Companion$BrBG9$lambda);
    this.BrBG10_rth6fy$_0 = lazy(EncodedColors$Companion$BrBG10$lambda);
    this.BrBG11_rth6f3$_0 = lazy(EncodedColors$Companion$BrBG11$lambda);
    this.BrBG_9mygv3$_0 = lazy(EncodedColors$Companion$BrBG$lambda(this));
    this.PiYG3_85523y$_0 = lazy(EncodedColors$Companion$PiYG3$lambda);
    this.PiYG4_855233$_0 = lazy(EncodedColors$Companion$PiYG4$lambda);
    this.PiYG5_855228$_0 = lazy(EncodedColors$Companion$PiYG5$lambda);
    this.PiYG6_85521d$_0 = lazy(EncodedColors$Companion$PiYG6$lambda);
    this.PiYG7_85520i$_0 = lazy(EncodedColors$Companion$PiYG7$lambda);
    this.PiYG8_8551zn$_0 = lazy(EncodedColors$Companion$PiYG8$lambda);
    this.PiYG9_8551ys$_0 = lazy(EncodedColors$Companion$PiYG9$lambda);
    this.PiYG10_vp3cts$_0 = lazy(EncodedColors$Companion$PiYG10$lambda);
    this.PiYG11_vp3cun$_0 = lazy(EncodedColors$Companion$PiYG11$lambda);
    this.PiYG_9femdt$_0 = lazy(EncodedColors$Companion$PiYG$lambda(this));
    this.PRGn3_8i30lo$_0 = lazy(EncodedColors$Companion$PRGn3$lambda);
    this.PRGn4_8i30kt$_0 = lazy(EncodedColors$Companion$PRGn4$lambda);
    this.PRGn5_8i30jy$_0 = lazy(EncodedColors$Companion$PRGn5$lambda);
    this.PRGn6_8i30j3$_0 = lazy(EncodedColors$Companion$PRGn6$lambda);
    this.PRGn7_8i30i8$_0 = lazy(EncodedColors$Companion$PRGn7$lambda);
    this.PRGn8_8i30hd$_0 = lazy(EncodedColors$Companion$PRGn8$lambda);
    this.PRGn9_8i30gi$_0 = lazy(EncodedColors$Companion$PRGn9$lambda);
    this.PRGn10_kjunke$_0 = lazy(EncodedColors$Companion$PRGn10$lambda);
    this.PRGn11_kjunl9$_0 = lazy(EncodedColors$Companion$PRGn11$lambda);
    this.PRGn_9ftnhv$_0 = lazy(EncodedColors$Companion$PRGn$lambda(this));
    this.PuOR3_7ypop9$_0 = lazy(EncodedColors$Companion$PuOR3$lambda);
    this.PuOR4_7ypooe$_0 = lazy(EncodedColors$Companion$PuOR4$lambda);
    this.PuOR5_7yponj$_0 = lazy(EncodedColors$Companion$PuOR5$lambda);
    this.PuOR6_7ypomo$_0 = lazy(EncodedColors$Companion$PuOR6$lambda);
    this.PuOR7_7ypolt$_0 = lazy(EncodedColors$Companion$PuOR7$lambda);
    this.PuOR8_7ypoky$_0 = lazy(EncodedColors$Companion$PuOR8$lambda);
    this.PuOR9_7ypok3$_0 = lazy(EncodedColors$Companion$PuOR9$lambda);
    this.PuOR10_xss5ht$_0 = lazy(EncodedColors$Companion$PuOR10$lambda);
    this.PuOR11_xss5gy$_0 = lazy(EncodedColors$Companion$PuOR11$lambda);
    this.PuOR_9f75oy$_0 = lazy(EncodedColors$Companion$PuOR$lambda(this));
    this.RdBU3_7a76ls$_0 = lazy(EncodedColors$Companion$RdBU3$lambda);
    this.RdBU4_7a76kx$_0 = lazy(EncodedColors$Companion$RdBU4$lambda);
    this.RdBU5_7a76k2$_0 = lazy(EncodedColors$Companion$RdBU5$lambda);
    this.RdBU6_7a76j7$_0 = lazy(EncodedColors$Companion$RdBU6$lambda);
    this.RdBU7_7a76ic$_0 = lazy(EncodedColors$Companion$RdBU7$lambda);
    this.RdBU8_7a76hh$_0 = lazy(EncodedColors$Companion$RdBU8$lambda);
    this.RdBU9_7a76gm$_0 = lazy(EncodedColors$Companion$RdBU9$lambda);
    this.RdBU10_couki6$_0 = lazy(EncodedColors$Companion$RdBU10$lambda);
    this.RdBU11_coukhb$_0 = lazy(EncodedColors$Companion$RdBU11$lambda);
    this.RdBU_9eeoun$_0 = lazy(EncodedColors$Companion$RdBU$lambda(this));
    this.RdGY3_7a3wpd$_0 = lazy(EncodedColors$Companion$RdGY3$lambda);
    this.RdGY4_7a3woi$_0 = lazy(EncodedColors$Companion$RdGY4$lambda);
    this.RdGY5_7a3wnn$_0 = lazy(EncodedColors$Companion$RdGY5$lambda);
    this.RdGY6_7a3wms$_0 = lazy(EncodedColors$Companion$RdGY6$lambda);
    this.RdGY7_7a3wlx$_0 = lazy(EncodedColors$Companion$RdGY7$lambda);
    this.RdGY8_7a3wl2$_0 = lazy(EncodedColors$Companion$RdGY8$lambda);
    this.RdGY9_7a3wk7$_0 = lazy(EncodedColors$Companion$RdGY9$lambda);
    this.RdGY10_cm11l9$_0 = lazy(EncodedColors$Companion$RdGY10$lambda);
    this.RdGY11_cm11ke$_0 = lazy(EncodedColors$Companion$RdGY11$lambda);
    this.RdGY_9eel1q$_0 = lazy(EncodedColors$Companion$RdGY$lambda(this));
    this.RdYlBu3_qzqj8d$_0 = lazy(EncodedColors$Companion$RdYlBu3$lambda);
    this.RdYlBu4_qzqj7i$_0 = lazy(EncodedColors$Companion$RdYlBu4$lambda);
    this.RdYlBu5_qzqj6n$_0 = lazy(EncodedColors$Companion$RdYlBu5$lambda);
    this.RdYlBu6_qzqj5s$_0 = lazy(EncodedColors$Companion$RdYlBu6$lambda);
    this.RdYlBu7_qzqj4x$_0 = lazy(EncodedColors$Companion$RdYlBu7$lambda);
    this.RdYlBu8_qzqj42$_0 = lazy(EncodedColors$Companion$RdYlBu8$lambda);
    this.RdYlBu9_qzqj37$_0 = lazy(EncodedColors$Companion$RdYlBu9$lambda);
    this.RdYlBu10_fli1tr$_0 = lazy(EncodedColors$Companion$RdYlBu10$lambda);
    this.RdYlBu11_fli1um$_0 = lazy(EncodedColors$Companion$RdYlBu11$lambda);
    this.RdYlBu_cbs7wi$_0 = lazy(EncodedColors$Companion$RdYlBu$lambda(this));
    this.RdYlGn3_qznhhl$_0 = lazy(EncodedColors$Companion$RdYlGn3$lambda);
    this.RdYlGn4_qznhgq$_0 = lazy(EncodedColors$Companion$RdYlGn4$lambda);
    this.RdYlGn5_qznhfv$_0 = lazy(EncodedColors$Companion$RdYlGn5$lambda);
    this.RdYlGn6_qznhf0$_0 = lazy(EncodedColors$Companion$RdYlGn6$lambda);
    this.RdYlGn7_qznhe5$_0 = lazy(EncodedColors$Companion$RdYlGn7$lambda);
    this.RdYlGn8_qznhda$_0 = lazy(EncodedColors$Companion$RdYlGn8$lambda);
    this.RdYlGn9_qznhcf$_0 = lazy(EncodedColors$Companion$RdYlGn9$lambda);
    this.RdYlGn10_fo4jvv$_0 = lazy(EncodedColors$Companion$RdYlGn10$lambda);
    this.RdYlGn11_fo4jwq$_0 = lazy(EncodedColors$Companion$RdYlGn11$lambda);
    this.RdYlGn_cbs4d2$_0 = lazy(EncodedColors$Companion$RdYlGn$lambda(this));
    this.spectral3_t4t2gh$_0 = lazy(EncodedColors$Companion$spectral3$lambda);
    this.spectral4_t4t2fm$_0 = lazy(EncodedColors$Companion$spectral4$lambda);
    this.spectral5_t4t2er$_0 = lazy(EncodedColors$Companion$spectral5$lambda);
    this.spectral6_t4t2dw$_0 = lazy(EncodedColors$Companion$spectral6$lambda);
    this.spectral7_t4t2d1$_0 = lazy(EncodedColors$Companion$spectral7$lambda);
    this.spectral8_t4t2c6$_0 = lazy(EncodedColors$Companion$spectral8$lambda);
    this.spectral9_t4t2bb$_0 = lazy(EncodedColors$Companion$spectral9$lambda);
    this.spectral10_k9fjtf$_0 = lazy(EncodedColors$Companion$spectral10$lambda);
    this.spectral11_k9fjua$_0 = lazy(EncodedColors$Companion$spectral11$lambda);
    this.spectral_q570ry$_0 = lazy(EncodedColors$Companion$spectral$lambda(this));
    this.viridis_ud3spm$_0 = lazy(EncodedColors$Companion$viridis$lambda);
    this.magma_5h1do7$_0 = lazy(EncodedColors$Companion$magma$lambda);
    this.inferno_7fxo9b$_0 = lazy(EncodedColors$Companion$inferno$lambda);
    this.plasma_5qcn5g$_0 = lazy(EncodedColors$Companion$plasma$lambda);
    this.BuGN3_elhl2z$_0 = lazy(EncodedColors$Companion$BuGN3$lambda);
    this.BuGN4_elhl24$_0 = lazy(EncodedColors$Companion$BuGN4$lambda);
    this.BuGN5_elhl19$_0 = lazy(EncodedColors$Companion$BuGN5$lambda);
    this.BuGN6_elhl0e$_0 = lazy(EncodedColors$Companion$BuGN6$lambda);
    this.BuGN7_elhkzj$_0 = lazy(EncodedColors$Companion$BuGN7$lambda);
    this.BuGN8_elhkyo$_0 = lazy(EncodedColors$Companion$BuGN8$lambda);
    this.BuGN9_elhkxt$_0 = lazy(EncodedColors$Companion$BuGN9$lambda);
    this.BuGN_9mwg10$_0 = lazy(EncodedColors$Companion$BuGN$lambda(this));
    this.BuPu3_elb1a5$_0 = lazy(EncodedColors$Companion$BuPu3$lambda);
    this.BuPu4_elb19a$_0 = lazy(EncodedColors$Companion$BuPu4$lambda);
    this.BuPu5_elb18f$_0 = lazy(EncodedColors$Companion$BuPu5$lambda);
    this.BuPu6_elb17k$_0 = lazy(EncodedColors$Companion$BuPu6$lambda);
    this.BuPu7_elb16p$_0 = lazy(EncodedColors$Companion$BuPu7$lambda);
    this.BuPu8_elb15u$_0 = lazy(EncodedColors$Companion$BuPu8$lambda);
    this.BuPu9_elb14z$_0 = lazy(EncodedColors$Companion$BuPu9$lambda);
    this.BuPu_9mw8f6$_0 = lazy(EncodedColors$Companion$BuPu$lambda(this));
    this.GnBu3_cc6fa3$_0 = lazy(EncodedColors$Companion$GnBu3$lambda);
    this.GnBu4_cc6f98$_0 = lazy(EncodedColors$Companion$GnBu4$lambda);
    this.GnBu5_cc6f8d$_0 = lazy(EncodedColors$Companion$GnBu5$lambda);
    this.GnBu6_cc6f7i$_0 = lazy(EncodedColors$Companion$GnBu6$lambda);
    this.GnBu7_cc6f6n$_0 = lazy(EncodedColors$Companion$GnBu7$lambda);
    this.GnBu8_cc6f5s$_0 = lazy(EncodedColors$Companion$GnBu8$lambda);
    this.GnBu9_cc6f4x$_0 = lazy(EncodedColors$Companion$GnBu9$lambda);
    this.GnBu_9ka0qs$_0 = lazy(EncodedColors$Companion$GnBu$lambda(this));
    this.OrRd3_8hcekg$_0 = lazy(EncodedColors$Companion$OrRd3$lambda);
    this.OrRd4_8hcejl$_0 = lazy(EncodedColors$Companion$OrRd4$lambda);
    this.OrRd5_8hceiq$_0 = lazy(EncodedColors$Companion$OrRd5$lambda);
    this.OrRd6_8hcehv$_0 = lazy(EncodedColors$Companion$OrRd6$lambda);
    this.OrRd7_8hceh0$_0 = lazy(EncodedColors$Companion$OrRd7$lambda);
    this.OrRd8_8hceg5$_0 = lazy(EncodedColors$Companion$OrRd8$lambda);
    this.OrRd9_8hcefa$_0 = lazy(EncodedColors$Companion$OrRd9$lambda);
    this.OrRd_9fsslb$_0 = lazy(EncodedColors$Companion$OrRd$lambda(this));
    this.PuBu3_7yx9kt$_0 = lazy(EncodedColors$Companion$PuBu3$lambda);
    this.PuBu4_7yx9jy$_0 = lazy(EncodedColors$Companion$PuBu4$lambda);
    this.PuBu5_7yx9j3$_0 = lazy(EncodedColors$Companion$PuBu5$lambda);
    this.PuBu6_7yx9i8$_0 = lazy(EncodedColors$Companion$PuBu6$lambda);
    this.PuBu7_7yx9hd$_0 = lazy(EncodedColors$Companion$PuBu7$lambda);
    this.PuBu8_7yx9gi$_0 = lazy(EncodedColors$Companion$PuBu8$lambda);
    this.PuBu9_7yx9fn$_0 = lazy(EncodedColors$Companion$PuBu9$lambda);
    this.PuBu_9f7ehu$_0 = lazy(EncodedColors$Companion$PuBu$lambda(this));
    this.PuBuGn3_c2j4i2$_0 = lazy(EncodedColors$Companion$PuBuGn3$lambda);
    this.PuBuGn4_c2j4ix$_0 = lazy(EncodedColors$Companion$PuBuGn4$lambda);
    this.PuBuGn5_c2j4js$_0 = lazy(EncodedColors$Companion$PuBuGn5$lambda);
    this.PuBuGn6_c2j4kn$_0 = lazy(EncodedColors$Companion$PuBuGn6$lambda);
    this.PuBuGn7_c2j4li$_0 = lazy(EncodedColors$Companion$PuBuGn7$lambda);
    this.PuBuGn8_c2j4md$_0 = lazy(EncodedColors$Companion$PuBuGn8$lambda);
    this.PuBuGn9_c2j4n8$_0 = lazy(EncodedColors$Companion$PuBuGn9$lambda);
    this.PuBuGn_xzamvd$_0 = lazy(EncodedColors$Companion$PuBuGn$lambda(this));
    this.PuRd3_7ynee6$_0 = lazy(EncodedColors$Companion$PuRd3$lambda);
    this.PuRd4_7ynedb$_0 = lazy(EncodedColors$Companion$PuRd4$lambda);
    this.PuRd5_7ynecg$_0 = lazy(EncodedColors$Companion$PuRd5$lambda);
    this.PuRd6_7ynebl$_0 = lazy(EncodedColors$Companion$PuRd6$lambda);
    this.PuRd7_7yneaq$_0 = lazy(EncodedColors$Companion$PuRd7$lambda);
    this.PuRd8_7yne9v$_0 = lazy(EncodedColors$Companion$PuRd8$lambda);
    this.PuRd9_7yne90$_0 = lazy(EncodedColors$Companion$PuRd9$lambda);
    this.PuRd_9f731d$_0 = lazy(EncodedColors$Companion$PuRd$lambda(this));
    this.RdPu3_79xl26$_0 = lazy(EncodedColors$Companion$RdPu3$lambda);
    this.RdPu4_79xl1b$_0 = lazy(EncodedColors$Companion$RdPu4$lambda);
    this.RdPu5_79xl0g$_0 = lazy(EncodedColors$Companion$RdPu5$lambda);
    this.RdPu6_79xkzl$_0 = lazy(EncodedColors$Companion$RdPu6$lambda);
    this.RdPu7_79xkyq$_0 = lazy(EncodedColors$Companion$RdPu7$lambda);
    this.RdPu8_79xkxv$_0 = lazy(EncodedColors$Companion$RdPu8$lambda);
    this.RdPu9_79xkx0$_0 = lazy(EncodedColors$Companion$RdPu9$lambda);
    this.RdPu_9eedpd$_0 = lazy(EncodedColors$Companion$RdPu$lambda(this));
    this.YlGn3_3udrmj$_0 = lazy(EncodedColors$Companion$YlGn3$lambda);
    this.YlGn4_3udrlo$_0 = lazy(EncodedColors$Companion$YlGn4$lambda);
    this.YlGn5_3udrkt$_0 = lazy(EncodedColors$Companion$YlGn5$lambda);
    this.YlGn6_3udrjy$_0 = lazy(EncodedColors$Companion$YlGn6$lambda);
    this.YlGn7_3udrj3$_0 = lazy(EncodedColors$Companion$YlGn7$lambda);
    this.YlGn8_3udri8$_0 = lazy(EncodedColors$Companion$YlGn8$lambda);
    this.YlGn9_3udrhd$_0 = lazy(EncodedColors$Companion$YlGn9$lambda);
    this.YlGn_9aewhw$_0 = lazy(EncodedColors$Companion$YlGn$lambda(this));
    this.YlGnbU3_eszzc$_0 = lazy(EncodedColors$Companion$YlGnbU3$lambda);
    this.YlGnbU4_eszyh$_0 = lazy(EncodedColors$Companion$YlGnbU4$lambda);
    this.YlGnbU5_eszxm$_0 = lazy(EncodedColors$Companion$YlGnbU5$lambda);
    this.YlGnbU6_eszwr$_0 = lazy(EncodedColors$Companion$YlGnbU6$lambda);
    this.YlGnbU7_eszvw$_0 = lazy(EncodedColors$Companion$YlGnbU7$lambda);
    this.YlGnbU8_eszv1$_0 = lazy(EncodedColors$Companion$YlGnbU8$lambda);
    this.YlGnbU9_eszu6$_0 = lazy(EncodedColors$Companion$YlGnbU9$lambda);
    this.YlGnbU_mweb3d$_0 = lazy(EncodedColors$Companion$YlGnbU$lambda(this));
    this.YlGnBr3_fcu23$_0 = lazy(EncodedColors$Companion$YlGnBr3$lambda);
    this.YlGnBr4_fcu18$_0 = lazy(EncodedColors$Companion$YlGnBr4$lambda);
    this.YlGnBr5_fcu0d$_0 = lazy(EncodedColors$Companion$YlGnBr5$lambda);
    this.YlGnBr6_fctzi$_0 = lazy(EncodedColors$Companion$YlGnBr6$lambda);
    this.YlGnBr7_fctyn$_0 = lazy(EncodedColors$Companion$YlGnBr7$lambda);
    this.YlGnBr8_fctxs$_0 = lazy(EncodedColors$Companion$YlGnBr8$lambda);
    this.YlGnBr9_fctwx$_0 = lazy(EncodedColors$Companion$YlGnBr9$lambda);
    this.YlGnBr_mwdo24$_0 = lazy(EncodedColors$Companion$YlGnBr$lambda(this));
    this.YlGnRd3_f2wnd$_0 = lazy(EncodedColors$Companion$YlGnRd3$lambda);
    this.YlGnRd4_f2wmi$_0 = lazy(EncodedColors$Companion$YlGnRd4$lambda);
    this.YlGnRd5_f2wln$_0 = lazy(EncodedColors$Companion$YlGnRd5$lambda);
    this.YlGnRd6_f2wks$_0 = lazy(EncodedColors$Companion$YlGnRd6$lambda);
    this.YlGnRd7_f2wjx$_0 = lazy(EncodedColors$Companion$YlGnRd7$lambda);
    this.YlGnRd8_f2wj2$_0 = lazy(EncodedColors$Companion$YlGnRd8$lambda);
    this.YlGnRd9_f2wi7$_0 = lazy(EncodedColors$Companion$YlGnRd9$lambda);
    this.YlGnRd_mwdzl6$_0 = lazy(EncodedColors$Companion$YlGnRd$lambda(this));
    this.blues3_dmvvcs$_0 = lazy(EncodedColors$Companion$blues3$lambda);
    this.blues4_dmvvdn$_0 = lazy(EncodedColors$Companion$blues4$lambda);
    this.blues5_dmvvei$_0 = lazy(EncodedColors$Companion$blues5$lambda);
    this.blues6_dmvvfd$_0 = lazy(EncodedColors$Companion$blues6$lambda);
    this.blues7_dmvvg8$_0 = lazy(EncodedColors$Companion$blues7$lambda);
    this.blues8_dmvvh3$_0 = lazy(EncodedColors$Companion$blues8$lambda);
    this.blues9_dmvvhy$_0 = lazy(EncodedColors$Companion$blues9$lambda);
    this.blues_fu26d$_0 = lazy(EncodedColors$Companion$blues$lambda(this));
    this.greens3_815qyj$_0 = lazy(EncodedColors$Companion$greens3$lambda);
    this.greens4_815qze$_0 = lazy(EncodedColors$Companion$greens4$lambda);
    this.greens5_815r09$_0 = lazy(EncodedColors$Companion$greens5$lambda);
    this.greens6_815r14$_0 = lazy(EncodedColors$Companion$greens6$lambda);
    this.greens7_815r1z$_0 = lazy(EncodedColors$Companion$greens7$lambda);
    this.greens8_815r2u$_0 = lazy(EncodedColors$Companion$greens8$lambda);
    this.greens9_815r3p$_0 = lazy(EncodedColors$Companion$greens9$lambda);
    this.greens_il876u$_0 = lazy(EncodedColors$Companion$greens$lambda(this));
    this.greys3_ill13r$_0 = lazy(EncodedColors$Companion$greys3$lambda);
    this.greys4_ill14m$_0 = lazy(EncodedColors$Companion$greys4$lambda);
    this.greys5_ill15h$_0 = lazy(EncodedColors$Companion$greys5$lambda);
    this.greys6_ill16c$_0 = lazy(EncodedColors$Companion$greys6$lambda);
    this.greys7_ill177$_0 = lazy(EncodedColors$Companion$greys7$lambda);
    this.greys8_ill182$_0 = lazy(EncodedColors$Companion$greys8$lambda);
    this.greys9_ill18x$_0 = lazy(EncodedColors$Companion$greys9$lambda);
    this.greys_2w34qy$_0 = lazy(EncodedColors$Companion$greys$lambda(this));
    this.oranges3_qbxdew$_0 = lazy(EncodedColors$Companion$oranges3$lambda);
    this.oranges4_qbxdfr$_0 = lazy(EncodedColors$Companion$oranges4$lambda);
    this.oranges5_qbxdgm$_0 = lazy(EncodedColors$Companion$oranges5$lambda);
    this.oranges6_qbxdhh$_0 = lazy(EncodedColors$Companion$oranges6$lambda);
    this.oranges7_qbxdic$_0 = lazy(EncodedColors$Companion$oranges7$lambda);
    this.oranges8_qbxdj7$_0 = lazy(EncodedColors$Companion$oranges8$lambda);
    this.oranges9_qbxdk2$_0 = lazy(EncodedColors$Companion$oranges9$lambda);
    this.oranges_nrgbix$_0 = lazy(EncodedColors$Companion$oranges$lambda(this));
    this.purples3_lpagiu$_0 = lazy(EncodedColors$Companion$purples3$lambda);
    this.purples4_lpaghz$_0 = lazy(EncodedColors$Companion$purples4$lambda);
    this.purples5_lpagh4$_0 = lazy(EncodedColors$Companion$purples5$lambda);
    this.purples6_lpagg9$_0 = lazy(EncodedColors$Companion$purples6$lambda);
    this.purples7_lpagfe$_0 = lazy(EncodedColors$Companion$purples7$lambda);
    this.purples8_lpagej$_0 = lazy(EncodedColors$Companion$purples8$lambda);
    this.purples9_lpagdo$_0 = lazy(EncodedColors$Companion$purples9$lambda);
    this.purples_xo3y3r$_0 = lazy(EncodedColors$Companion$purples$lambda(this));
    this.reds3_7weuel$_0 = lazy(EncodedColors$Companion$reds3$lambda);
    this.reds4_7weufg$_0 = lazy(EncodedColors$Companion$reds4$lambda);
    this.reds5_7weugb$_0 = lazy(EncodedColors$Companion$reds5$lambda);
    this.reds6_7weuh6$_0 = lazy(EncodedColors$Companion$reds6$lambda);
    this.reds7_7weui1$_0 = lazy(EncodedColors$Companion$reds7$lambda);
    this.reds8_7weuiw$_0 = lazy(EncodedColors$Companion$reds8$lambda);
    this.reds9_7weujr$_0 = lazy(EncodedColors$Companion$reds9$lambda);
    this.reds_8wrwy4$_0 = lazy(EncodedColors$Companion$reds$lambda(this));
  }
  Object.defineProperty(EncodedColors$Companion.prototype, 'category10', {
    get: function () {
      return this.category10_wu4fg9$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'category20', {
    get: function () {
      return this.category20_wu4g6y$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'category20b', {
    get: function () {
      return this.category20b_ni97bs$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'category20c', {
    get: function () {
      return this.category20c_ni97cn$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'accents', {
    get: function () {
      return this.accents_uv29e3$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'dark2', {
    get: function () {
      return this.dark2_17tqde$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'paired', {
    get: function () {
      return this.paired_n8cpp$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'pastel1', {
    get: function () {
      return this.pastel1_orqx0m$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'pastel2', {
    get: function () {
      return this.pastel2_orqx1h$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'set1', {
    get: function () {
      return this.set1_8w7u2h$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'set2', {
    get: function () {
      return this.set2_8w7u1m$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'set3', {
    get: function () {
      return this.set3_8w7u0r$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'BrBG3_0', {
    get: function () {
      return this.BrBG3_en8azk$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'BrBG4_0', {
    get: function () {
      return this.BrBG4_en8ayp$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'BrBG5_0', {
    get: function () {
      return this.BrBG5_en8axu$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'BrBG6_0', {
    get: function () {
      return this.BrBG6_en8awz$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'BrBG7_0', {
    get: function () {
      return this.BrBG7_en8aw4$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'BrBG8_0', {
    get: function () {
      return this.BrBG8_en8av9$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'BrBG9_0', {
    get: function () {
      return this.BrBG9_en8aue$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'BrBG10_0', {
    get: function () {
      return this.BrBG10_rth6fy$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'BrBG11_0', {
    get: function () {
      return this.BrBG11_rth6f3$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'BrBG', {
    get: function () {
      return this.BrBG_9mygv3$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PiYG3_0', {
    get: function () {
      return this.PiYG3_85523y$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PiYG4_0', {
    get: function () {
      return this.PiYG4_855233$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PiYG5_0', {
    get: function () {
      return this.PiYG5_855228$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PiYG6_0', {
    get: function () {
      return this.PiYG6_85521d$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PiYG7_0', {
    get: function () {
      return this.PiYG7_85520i$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PiYG8_0', {
    get: function () {
      return this.PiYG8_8551zn$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PiYG9_0', {
    get: function () {
      return this.PiYG9_8551ys$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PiYG10_0', {
    get: function () {
      return this.PiYG10_vp3cts$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PiYG11_0', {
    get: function () {
      return this.PiYG11_vp3cun$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PiYG', {
    get: function () {
      return this.PiYG_9femdt$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PRGn3_0', {
    get: function () {
      return this.PRGn3_8i30lo$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PRGn4_0', {
    get: function () {
      return this.PRGn4_8i30kt$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PRGn5_0', {
    get: function () {
      return this.PRGn5_8i30jy$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PRGn6_0', {
    get: function () {
      return this.PRGn6_8i30j3$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PRGn7_0', {
    get: function () {
      return this.PRGn7_8i30i8$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PRGn8_0', {
    get: function () {
      return this.PRGn8_8i30hd$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PRGn9_0', {
    get: function () {
      return this.PRGn9_8i30gi$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PRGn10_0', {
    get: function () {
      return this.PRGn10_kjunke$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PRGn11_0', {
    get: function () {
      return this.PRGn11_kjunl9$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PRGn', {
    get: function () {
      return this.PRGn_9ftnhv$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuOR3_0', {
    get: function () {
      return this.PuOR3_7ypop9$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuOR4_0', {
    get: function () {
      return this.PuOR4_7ypooe$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuOR5_0', {
    get: function () {
      return this.PuOR5_7yponj$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuOR6_0', {
    get: function () {
      return this.PuOR6_7ypomo$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuOR7_0', {
    get: function () {
      return this.PuOR7_7ypolt$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuOR8_0', {
    get: function () {
      return this.PuOR8_7ypoky$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuOR9_0', {
    get: function () {
      return this.PuOR9_7ypok3$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuOR10_0', {
    get: function () {
      return this.PuOR10_xss5ht$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuOR11_0', {
    get: function () {
      return this.PuOR11_xss5gy$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuOR', {
    get: function () {
      return this.PuOR_9f75oy$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdBU3_0', {
    get: function () {
      return this.RdBU3_7a76ls$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdBU4_0', {
    get: function () {
      return this.RdBU4_7a76kx$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdBU5_0', {
    get: function () {
      return this.RdBU5_7a76k2$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdBU6_0', {
    get: function () {
      return this.RdBU6_7a76j7$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdBU7_0', {
    get: function () {
      return this.RdBU7_7a76ic$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdBU8_0', {
    get: function () {
      return this.RdBU8_7a76hh$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdBU9_0', {
    get: function () {
      return this.RdBU9_7a76gm$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdBU10_0', {
    get: function () {
      return this.RdBU10_couki6$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdBU11_0', {
    get: function () {
      return this.RdBU11_coukhb$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdBU', {
    get: function () {
      return this.RdBU_9eeoun$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdGY3_0', {
    get: function () {
      return this.RdGY3_7a3wpd$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdGY4_0', {
    get: function () {
      return this.RdGY4_7a3woi$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdGY5_0', {
    get: function () {
      return this.RdGY5_7a3wnn$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdGY6_0', {
    get: function () {
      return this.RdGY6_7a3wms$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdGY7_0', {
    get: function () {
      return this.RdGY7_7a3wlx$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdGY8_0', {
    get: function () {
      return this.RdGY8_7a3wl2$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdGY9_0', {
    get: function () {
      return this.RdGY9_7a3wk7$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdGY10_0', {
    get: function () {
      return this.RdGY10_cm11l9$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdGY11_0', {
    get: function () {
      return this.RdGY11_cm11ke$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdGY', {
    get: function () {
      return this.RdGY_9eel1q$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdYlBu3_0', {
    get: function () {
      return this.RdYlBu3_qzqj8d$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdYlBu4_0', {
    get: function () {
      return this.RdYlBu4_qzqj7i$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdYlBu5_0', {
    get: function () {
      return this.RdYlBu5_qzqj6n$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdYlBu6_0', {
    get: function () {
      return this.RdYlBu6_qzqj5s$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdYlBu7_0', {
    get: function () {
      return this.RdYlBu7_qzqj4x$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdYlBu8_0', {
    get: function () {
      return this.RdYlBu8_qzqj42$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdYlBu9_0', {
    get: function () {
      return this.RdYlBu9_qzqj37$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdYlBu10_0', {
    get: function () {
      return this.RdYlBu10_fli1tr$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdYlBu11_0', {
    get: function () {
      return this.RdYlBu11_fli1um$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdYlBu', {
    get: function () {
      return this.RdYlBu_cbs7wi$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdYlGn3_0', {
    get: function () {
      return this.RdYlGn3_qznhhl$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdYlGn4_0', {
    get: function () {
      return this.RdYlGn4_qznhgq$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdYlGn5_0', {
    get: function () {
      return this.RdYlGn5_qznhfv$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdYlGn6_0', {
    get: function () {
      return this.RdYlGn6_qznhf0$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdYlGn7_0', {
    get: function () {
      return this.RdYlGn7_qznhe5$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdYlGn8_0', {
    get: function () {
      return this.RdYlGn8_qznhda$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdYlGn9_0', {
    get: function () {
      return this.RdYlGn9_qznhcf$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdYlGn10_0', {
    get: function () {
      return this.RdYlGn10_fo4jvv$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdYlGn11_0', {
    get: function () {
      return this.RdYlGn11_fo4jwq$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdYlGn', {
    get: function () {
      return this.RdYlGn_cbs4d2$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'spectral3_0', {
    get: function () {
      return this.spectral3_t4t2gh$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'spectral4_0', {
    get: function () {
      return this.spectral4_t4t2fm$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'spectral5_0', {
    get: function () {
      return this.spectral5_t4t2er$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'spectral6_0', {
    get: function () {
      return this.spectral6_t4t2dw$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'spectral7_0', {
    get: function () {
      return this.spectral7_t4t2d1$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'spectral8_0', {
    get: function () {
      return this.spectral8_t4t2c6$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'spectral9_0', {
    get: function () {
      return this.spectral9_t4t2bb$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'spectral10_0', {
    get: function () {
      return this.spectral10_k9fjtf$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'spectral11_0', {
    get: function () {
      return this.spectral11_k9fjua$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'spectral', {
    get: function () {
      return this.spectral_q570ry$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'viridis', {
    get: function () {
      return this.viridis_ud3spm$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'magma', {
    get: function () {
      return this.magma_5h1do7$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'inferno', {
    get: function () {
      return this.inferno_7fxo9b$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'plasma', {
    get: function () {
      return this.plasma_5qcn5g$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'BuGN3_0', {
    get: function () {
      return this.BuGN3_elhl2z$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'BuGN4_0', {
    get: function () {
      return this.BuGN4_elhl24$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'BuGN5_0', {
    get: function () {
      return this.BuGN5_elhl19$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'BuGN6_0', {
    get: function () {
      return this.BuGN6_elhl0e$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'BuGN7_0', {
    get: function () {
      return this.BuGN7_elhkzj$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'BuGN8_0', {
    get: function () {
      return this.BuGN8_elhkyo$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'BuGN9_0', {
    get: function () {
      return this.BuGN9_elhkxt$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'BuGN', {
    get: function () {
      return this.BuGN_9mwg10$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'BuPu3_0', {
    get: function () {
      return this.BuPu3_elb1a5$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'BuPu4_0', {
    get: function () {
      return this.BuPu4_elb19a$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'BuPu5_0', {
    get: function () {
      return this.BuPu5_elb18f$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'BuPu6_0', {
    get: function () {
      return this.BuPu6_elb17k$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'BuPu7_0', {
    get: function () {
      return this.BuPu7_elb16p$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'BuPu8_0', {
    get: function () {
      return this.BuPu8_elb15u$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'BuPu9_0', {
    get: function () {
      return this.BuPu9_elb14z$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'BuPu', {
    get: function () {
      return this.BuPu_9mw8f6$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'GnBu3_0', {
    get: function () {
      return this.GnBu3_cc6fa3$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'GnBu4_0', {
    get: function () {
      return this.GnBu4_cc6f98$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'GnBu5_0', {
    get: function () {
      return this.GnBu5_cc6f8d$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'GnBu6_0', {
    get: function () {
      return this.GnBu6_cc6f7i$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'GnBu7_0', {
    get: function () {
      return this.GnBu7_cc6f6n$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'GnBu8_0', {
    get: function () {
      return this.GnBu8_cc6f5s$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'GnBu9_0', {
    get: function () {
      return this.GnBu9_cc6f4x$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'GnBu', {
    get: function () {
      return this.GnBu_9ka0qs$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'OrRd3_0', {
    get: function () {
      return this.OrRd3_8hcekg$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'OrRd4_0', {
    get: function () {
      return this.OrRd4_8hcejl$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'OrRd5_0', {
    get: function () {
      return this.OrRd5_8hceiq$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'OrRd6_0', {
    get: function () {
      return this.OrRd6_8hcehv$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'OrRd7_0', {
    get: function () {
      return this.OrRd7_8hceh0$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'OrRd8_0', {
    get: function () {
      return this.OrRd8_8hceg5$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'OrRd9_0', {
    get: function () {
      return this.OrRd9_8hcefa$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'OrRd', {
    get: function () {
      return this.OrRd_9fsslb$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuBu3_0', {
    get: function () {
      return this.PuBu3_7yx9kt$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuBu4_0', {
    get: function () {
      return this.PuBu4_7yx9jy$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuBu5_0', {
    get: function () {
      return this.PuBu5_7yx9j3$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuBu6_0', {
    get: function () {
      return this.PuBu6_7yx9i8$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuBu7_0', {
    get: function () {
      return this.PuBu7_7yx9hd$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuBu8_0', {
    get: function () {
      return this.PuBu8_7yx9gi$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuBu9_0', {
    get: function () {
      return this.PuBu9_7yx9fn$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuBu', {
    get: function () {
      return this.PuBu_9f7ehu$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuBuGn3_0', {
    get: function () {
      return this.PuBuGn3_c2j4i2$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuBuGn4_0', {
    get: function () {
      return this.PuBuGn4_c2j4ix$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuBuGn5_0', {
    get: function () {
      return this.PuBuGn5_c2j4js$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuBuGn6_0', {
    get: function () {
      return this.PuBuGn6_c2j4kn$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuBuGn7_0', {
    get: function () {
      return this.PuBuGn7_c2j4li$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuBuGn8_0', {
    get: function () {
      return this.PuBuGn8_c2j4md$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuBuGn9_0', {
    get: function () {
      return this.PuBuGn9_c2j4n8$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuBuGn', {
    get: function () {
      return this.PuBuGn_xzamvd$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuRd3_0', {
    get: function () {
      return this.PuRd3_7ynee6$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuRd4_0', {
    get: function () {
      return this.PuRd4_7ynedb$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuRd5_0', {
    get: function () {
      return this.PuRd5_7ynecg$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuRd6_0', {
    get: function () {
      return this.PuRd6_7ynebl$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuRd7_0', {
    get: function () {
      return this.PuRd7_7yneaq$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuRd8_0', {
    get: function () {
      return this.PuRd8_7yne9v$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuRd9_0', {
    get: function () {
      return this.PuRd9_7yne90$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'PuRd', {
    get: function () {
      return this.PuRd_9f731d$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdPu3_0', {
    get: function () {
      return this.RdPu3_79xl26$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdPu4_0', {
    get: function () {
      return this.RdPu4_79xl1b$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdPu5_0', {
    get: function () {
      return this.RdPu5_79xl0g$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdPu6_0', {
    get: function () {
      return this.RdPu6_79xkzl$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdPu7_0', {
    get: function () {
      return this.RdPu7_79xkyq$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdPu8_0', {
    get: function () {
      return this.RdPu8_79xkxv$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdPu9_0', {
    get: function () {
      return this.RdPu9_79xkx0$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'RdPu', {
    get: function () {
      return this.RdPu_9eedpd$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGn3_0', {
    get: function () {
      return this.YlGn3_3udrmj$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGn4_0', {
    get: function () {
      return this.YlGn4_3udrlo$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGn5_0', {
    get: function () {
      return this.YlGn5_3udrkt$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGn6_0', {
    get: function () {
      return this.YlGn6_3udrjy$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGn7_0', {
    get: function () {
      return this.YlGn7_3udrj3$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGn8_0', {
    get: function () {
      return this.YlGn8_3udri8$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGn9_0', {
    get: function () {
      return this.YlGn9_3udrhd$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGn', {
    get: function () {
      return this.YlGn_9aewhw$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGnbU3_0', {
    get: function () {
      return this.YlGnbU3_eszzc$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGnbU4_0', {
    get: function () {
      return this.YlGnbU4_eszyh$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGnbU5_0', {
    get: function () {
      return this.YlGnbU5_eszxm$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGnbU6_0', {
    get: function () {
      return this.YlGnbU6_eszwr$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGnbU7_0', {
    get: function () {
      return this.YlGnbU7_eszvw$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGnbU8_0', {
    get: function () {
      return this.YlGnbU8_eszv1$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGnbU9_0', {
    get: function () {
      return this.YlGnbU9_eszu6$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGnbU', {
    get: function () {
      return this.YlGnbU_mweb3d$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGnBr3_0', {
    get: function () {
      return this.YlGnBr3_fcu23$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGnBr4_0', {
    get: function () {
      return this.YlGnBr4_fcu18$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGnBr5_0', {
    get: function () {
      return this.YlGnBr5_fcu0d$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGnBr6_0', {
    get: function () {
      return this.YlGnBr6_fctzi$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGnBr7_0', {
    get: function () {
      return this.YlGnBr7_fctyn$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGnBr8_0', {
    get: function () {
      return this.YlGnBr8_fctxs$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGnBr9_0', {
    get: function () {
      return this.YlGnBr9_fctwx$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGnBr', {
    get: function () {
      return this.YlGnBr_mwdo24$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGnRd3_0', {
    get: function () {
      return this.YlGnRd3_f2wnd$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGnRd4_0', {
    get: function () {
      return this.YlGnRd4_f2wmi$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGnRd5_0', {
    get: function () {
      return this.YlGnRd5_f2wln$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGnRd6_0', {
    get: function () {
      return this.YlGnRd6_f2wks$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGnRd7_0', {
    get: function () {
      return this.YlGnRd7_f2wjx$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGnRd8_0', {
    get: function () {
      return this.YlGnRd8_f2wj2$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGnRd9_0', {
    get: function () {
      return this.YlGnRd9_f2wi7$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'YlGnRd', {
    get: function () {
      return this.YlGnRd_mwdzl6$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'blues3_0', {
    get: function () {
      return this.blues3_dmvvcs$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'blues4_0', {
    get: function () {
      return this.blues4_dmvvdn$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'blues5_0', {
    get: function () {
      return this.blues5_dmvvei$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'blues6_0', {
    get: function () {
      return this.blues6_dmvvfd$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'blues7_0', {
    get: function () {
      return this.blues7_dmvvg8$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'blues8_0', {
    get: function () {
      return this.blues8_dmvvh3$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'blues9_0', {
    get: function () {
      return this.blues9_dmvvhy$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'blues', {
    get: function () {
      return this.blues_fu26d$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'greens3_0', {
    get: function () {
      return this.greens3_815qyj$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'greens4_0', {
    get: function () {
      return this.greens4_815qze$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'greens5_0', {
    get: function () {
      return this.greens5_815r09$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'greens6_0', {
    get: function () {
      return this.greens6_815r14$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'greens7_0', {
    get: function () {
      return this.greens7_815r1z$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'greens8_0', {
    get: function () {
      return this.greens8_815r2u$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'greens9_0', {
    get: function () {
      return this.greens9_815r3p$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'greens', {
    get: function () {
      return this.greens_il876u$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'greys3_0', {
    get: function () {
      return this.greys3_ill13r$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'greys4_0', {
    get: function () {
      return this.greys4_ill14m$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'greys5_0', {
    get: function () {
      return this.greys5_ill15h$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'greys6_0', {
    get: function () {
      return this.greys6_ill16c$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'greys7_0', {
    get: function () {
      return this.greys7_ill177$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'greys8_0', {
    get: function () {
      return this.greys8_ill182$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'greys9_0', {
    get: function () {
      return this.greys9_ill18x$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'greys', {
    get: function () {
      return this.greys_2w34qy$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'oranges3_0', {
    get: function () {
      return this.oranges3_qbxdew$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'oranges4_0', {
    get: function () {
      return this.oranges4_qbxdfr$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'oranges5_0', {
    get: function () {
      return this.oranges5_qbxdgm$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'oranges6_0', {
    get: function () {
      return this.oranges6_qbxdhh$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'oranges7_0', {
    get: function () {
      return this.oranges7_qbxdic$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'oranges8_0', {
    get: function () {
      return this.oranges8_qbxdj7$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'oranges9_0', {
    get: function () {
      return this.oranges9_qbxdk2$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'oranges', {
    get: function () {
      return this.oranges_nrgbix$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'purples3_0', {
    get: function () {
      return this.purples3_lpagiu$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'purples4_0', {
    get: function () {
      return this.purples4_lpaghz$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'purples5_0', {
    get: function () {
      return this.purples5_lpagh4$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'purples6_0', {
    get: function () {
      return this.purples6_lpagg9$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'purples7_0', {
    get: function () {
      return this.purples7_lpagfe$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'purples8_0', {
    get: function () {
      return this.purples8_lpagej$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'purples9_0', {
    get: function () {
      return this.purples9_lpagdo$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'purples', {
    get: function () {
      return this.purples_xo3y3r$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'reds3_0', {
    get: function () {
      return this.reds3_7weuel$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'reds4_0', {
    get: function () {
      return this.reds4_7weufg$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'reds5_0', {
    get: function () {
      return this.reds5_7weugb$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'reds6_0', {
    get: function () {
      return this.reds6_7weuh6$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'reds7_0', {
    get: function () {
      return this.reds7_7weui1$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'reds8_0', {
    get: function () {
      return this.reds8_7weuiw$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'reds9_0', {
    get: function () {
      return this.reds9_7weujr$_0.value;
    }
  });
  Object.defineProperty(EncodedColors$Companion.prototype, 'reds', {
    get: function () {
      return this.reds_8wrwy4$_0.value;
    }
  });
  function EncodedColors$Companion$category10$lambda() {
    return new EncodedColors('1f77b4ff7f0e2ca02cd627289467bd8c564be377c27f7f7fbcbd2217becf');
  }
  function EncodedColors$Companion$category20$lambda() {
    return new EncodedColors('1f77b4aec7e8ff7f0effbb782ca02c98df8ad62728ff98969467bdc5b0d58c564bc49c94e377c2f7b6d27f7f7fc7c7c7bcbd22dbdb8d17becf9edae5');
  }
  function EncodedColors$Companion$category20b$lambda() {
    return new EncodedColors('393b795254a36b6ecf9c9ede6379398ca252b5cf6bcedb9c8c6d31bd9e39e7ba52e7cb94843c39ad494ad6616be7969c7b4173a55194ce6dbdde9ed6');
  }
  function EncodedColors$Companion$category20c$lambda() {
    return new EncodedColors('3182bd6baed69ecae1c6dbefe6550dfd8d3cfdae6bfdd0a231a35474c476a1d99bc7e9c0756bb19e9ac8bcbddcdadaeb636363969696bdbdbdd9d9d9');
  }
  function EncodedColors$Companion$accents$lambda() {
    return new EncodedColors('7fc97fbeaed4fdc086ffff99386cb0f0027fbf5b17666666');
  }
  function EncodedColors$Companion$dark2$lambda() {
    return new EncodedColors('1b9e77d95f027570b3e7298a66a61ee6ab02a6761d666666');
  }
  function EncodedColors$Companion$paired$lambda() {
    return new EncodedColors('a6cee31f78b4b2df8a33a02cfb9a99e31a1cfdbf6fff7f00cab2d66a3d9affff99b15928');
  }
  function EncodedColors$Companion$pastel1$lambda() {
    return new EncodedColors('fbb4aeb3cde3ccebc5decbe4fed9a6ffffcce5d8bdfddaecf2f2f2');
  }
  function EncodedColors$Companion$pastel2$lambda() {
    return new EncodedColors('b3e2cdfdcdaccbd5e8f4cae4e6f5c9fff2aef1e2cccccccc');
  }
  function EncodedColors$Companion$set1$lambda() {
    return new EncodedColors('e41a1c377eb84daf4a984ea3ff7f00ffff33a65628f781bf999999');
  }
  function EncodedColors$Companion$set2$lambda() {
    return new EncodedColors('66c2a5fc8d628da0cbe78ac3a6d854ffd92fe5c494b3b3b3');
  }
  function EncodedColors$Companion$set3$lambda() {
    return new EncodedColors('8dd3c7ffffb3bebadafb807280b1d3fdb462b3de69fccde5d9d9d9bc80bdccebc5ffed6f');
  }
  function EncodedColors$Companion$BrBG3$lambda() {
    return new EncodedColors('d8b365f5f5f55ab4ac');
  }
  function EncodedColors$Companion$BrBG4$lambda() {
    return new EncodedColors('a6611adfc27d80cdc1018571');
  }
  function EncodedColors$Companion$BrBG5$lambda() {
    return new EncodedColors('a6611adfc27df5f5f580cdc1018571');
  }
  function EncodedColors$Companion$BrBG6$lambda() {
    return new EncodedColors('8c510ad8b365f6e8c3c7eae55ab4ac01665e');
  }
  function EncodedColors$Companion$BrBG7$lambda() {
    return new EncodedColors('8c510ad8b365f6e8c3f5f5f5c7eae55ab4ac01665e');
  }
  function EncodedColors$Companion$BrBG8$lambda() {
    return new EncodedColors('8c510abf812ddfc27df6e8c3c7eae580cdc135978f01665e');
  }
  function EncodedColors$Companion$BrBG9$lambda() {
    return new EncodedColors('8c510abf812ddfc27df6e8c3f5f5f5c7eae580cdc135978f01665e');
  }
  function EncodedColors$Companion$BrBG10$lambda() {
    return new EncodedColors('5430058c510abf812ddfc27df6e8c3c7eae580cdc135978f01665e003c30');
  }
  function EncodedColors$Companion$BrBG11$lambda() {
    return new EncodedColors('5430058c510abf812ddfc27df6e8c3f5f5f5c7eae580cdc135978f01665e003c30');
  }
  function EncodedColors$Companion$BrBG$lambda(this$EncodedColors$) {
    return function () {
      return listOf([this$EncodedColors$.BrBG3_0, this$EncodedColors$.BrBG4_0, this$EncodedColors$.BrBG5_0, this$EncodedColors$.BrBG6_0, this$EncodedColors$.BrBG7_0, this$EncodedColors$.BrBG8_0, this$EncodedColors$.BrBG9_0, this$EncodedColors$.BrBG10_0, this$EncodedColors$.BrBG11_0]);
    };
  }
  function EncodedColors$Companion$PiYG3$lambda() {
    return new EncodedColors('e9a3c9f7f7f7a1d76a');
  }
  function EncodedColors$Companion$PiYG4$lambda() {
    return new EncodedColors('d01c8bf1b6dab8e1864dac26');
  }
  function EncodedColors$Companion$PiYG5$lambda() {
    return new EncodedColors('d01c8bf1b6daf7f7f7b8e1864dac26');
  }
  function EncodedColors$Companion$PiYG6$lambda() {
    return new EncodedColors('c51b7de9a3c9fde0efe6f5d0a1d76a4d9221');
  }
  function EncodedColors$Companion$PiYG7$lambda() {
    return new EncodedColors('c51b7de9a3c9fde0eff7f7f7e6f5d0a1d76a4d9221');
  }
  function EncodedColors$Companion$PiYG8$lambda() {
    return new EncodedColors('c51b7dde77aef1b6dafde0efe6f5d0b8e1867fbc414d9221');
  }
  function EncodedColors$Companion$PiYG9$lambda() {
    return new EncodedColors('c51b7dde77aef1b6dafde0eff7f7f7e6f5d0b8e1867fbc414d9221');
  }
  function EncodedColors$Companion$PiYG10$lambda() {
    return new EncodedColors('8e0152c51b7dde77aef1b6dafde0efe6f5d0b8e1867fbc414d9221276419');
  }
  function EncodedColors$Companion$PiYG11$lambda() {
    return new EncodedColors('8e0152c51b7dde77aef1b6dafde0eff7f7f7e6f5d0b8e1867fbc414d9221276419');
  }
  function EncodedColors$Companion$PiYG$lambda(this$EncodedColors$) {
    return function () {
      return listOf([this$EncodedColors$.PiYG3_0, this$EncodedColors$.PiYG4_0, this$EncodedColors$.PiYG5_0, this$EncodedColors$.PiYG6_0, this$EncodedColors$.PiYG7_0, this$EncodedColors$.PiYG8_0, this$EncodedColors$.PiYG9_0, this$EncodedColors$.PiYG10_0, this$EncodedColors$.PiYG11_0]);
    };
  }
  function EncodedColors$Companion$PRGn3$lambda() {
    return new EncodedColors('af8dc3f7f7f77fbf7b');
  }
  function EncodedColors$Companion$PRGn4$lambda() {
    return new EncodedColors('7b3294c2a5cfa6dba0008837');
  }
  function EncodedColors$Companion$PRGn5$lambda() {
    return new EncodedColors('7b3294c2a5cff7f7f7a6dba0008837');
  }
  function EncodedColors$Companion$PRGn6$lambda() {
    return new EncodedColors('762a83af8dc3e7d4e8d9f0d37fbf7b1b7837');
  }
  function EncodedColors$Companion$PRGn7$lambda() {
    return new EncodedColors('762a83af8dc3e7d4e8f7f7f7d9f0d37fbf7b1b7837');
  }
  function EncodedColors$Companion$PRGn8$lambda() {
    return new EncodedColors('762a839970abc2a5cfe7d4e8d9f0d3a6dba05aae611b7837');
  }
  function EncodedColors$Companion$PRGn9$lambda() {
    return new EncodedColors('762a839970abc2a5cfe7d4e8f7f7f7d9f0d3a6dba05aae611b7837');
  }
  function EncodedColors$Companion$PRGn10$lambda() {
    return new EncodedColors('40004b762a839970abc2a5cfe7d4e8d9f0d3a6dba05aae611b783700441b');
  }
  function EncodedColors$Companion$PRGn11$lambda() {
    return new EncodedColors('40004b762a839970abc2a5cfe7d4e8f7f7f7d9f0d3a6dba05aae611b783700441b');
  }
  function EncodedColors$Companion$PRGn$lambda(this$EncodedColors$) {
    return function () {
      return listOf([this$EncodedColors$.PRGn3_0, this$EncodedColors$.PRGn4_0, this$EncodedColors$.PRGn5_0, this$EncodedColors$.PRGn6_0, this$EncodedColors$.PRGn7_0, this$EncodedColors$.PRGn8_0, this$EncodedColors$.PRGn9_0, this$EncodedColors$.PRGn10_0, this$EncodedColors$.PRGn11_0]);
    };
  }
  function EncodedColors$Companion$PuOR3$lambda() {
    return new EncodedColors('998ec3f7f7f7f1a340');
  }
  function EncodedColors$Companion$PuOR4$lambda() {
    return new EncodedColors('5e3c99b2abd2fdb863e66101');
  }
  function EncodedColors$Companion$PuOR5$lambda() {
    return new EncodedColors('5e3c99b2abd2f7f7f7fdb863e66101');
  }
  function EncodedColors$Companion$PuOR6$lambda() {
    return new EncodedColors('542788998ec3d8daebfee0b6f1a340b35806');
  }
  function EncodedColors$Companion$PuOR7$lambda() {
    return new EncodedColors('542788998ec3d8daebf7f7f7fee0b6f1a340b35806');
  }
  function EncodedColors$Companion$PuOR8$lambda() {
    return new EncodedColors('5427888073acb2abd2d8daebfee0b6fdb863e08214b35806');
  }
  function EncodedColors$Companion$PuOR9$lambda() {
    return new EncodedColors('5427888073acb2abd2d8daebf7f7f7fee0b6fdb863e08214b35806');
  }
  function EncodedColors$Companion$PuOR10$lambda() {
    return new EncodedColors('2d004b5427888073acb2abd2d8daebfee0b6fdb863e08214b358067f3b08');
  }
  function EncodedColors$Companion$PuOR11$lambda() {
    return new EncodedColors('2d004b5427888073acb2abd2d8daebf7f7f7fee0b6fdb863e08214b358067f3b08');
  }
  function EncodedColors$Companion$PuOR$lambda(this$EncodedColors$) {
    return function () {
      return listOf([this$EncodedColors$.PuOR3_0, this$EncodedColors$.PuOR4_0, this$EncodedColors$.PuOR5_0, this$EncodedColors$.PuOR6_0, this$EncodedColors$.PuOR7_0, this$EncodedColors$.PuOR8_0, this$EncodedColors$.PuOR9_0, this$EncodedColors$.PuOR10_0, this$EncodedColors$.PuOR11_0]);
    };
  }
  function EncodedColors$Companion$RdBU3$lambda() {
    return new EncodedColors('ef8a62f7f7f767a9cf');
  }
  function EncodedColors$Companion$RdBU4$lambda() {
    return new EncodedColors('ca0020f4a58292c5de0571b0');
  }
  function EncodedColors$Companion$RdBU5$lambda() {
    return new EncodedColors('ca0020f4a582f7f7f792c5de0571b0');
  }
  function EncodedColors$Companion$RdBU6$lambda() {
    return new EncodedColors('b2182bef8a62fddbc7d1e5f067a9cf2166ac');
  }
  function EncodedColors$Companion$RdBU7$lambda() {
    return new EncodedColors('b2182bef8a62fddbc7f7f7f7d1e5f067a9cf2166ac');
  }
  function EncodedColors$Companion$RdBU8$lambda() {
    return new EncodedColors('b2182bd6604df4a582fddbc7d1e5f092c5de4393c32166ac');
  }
  function EncodedColors$Companion$RdBU9$lambda() {
    return new EncodedColors('b2182bd6604df4a582fddbc7f7f7f7d1e5f092c5de4393c32166ac');
  }
  function EncodedColors$Companion$RdBU10$lambda() {
    return new EncodedColors('67001fb2182bd6604df4a582fddbc7d1e5f092c5de4393c32166ac053061');
  }
  function EncodedColors$Companion$RdBU11$lambda() {
    return new EncodedColors('67001fb2182bd6604df4a582fddbc7f7f7f7d1e5f092c5de4393c32166ac053061');
  }
  function EncodedColors$Companion$RdBU$lambda(this$EncodedColors$) {
    return function () {
      return listOf([this$EncodedColors$.RdBU3_0, this$EncodedColors$.RdBU4_0, this$EncodedColors$.RdBU5_0, this$EncodedColors$.RdBU6_0, this$EncodedColors$.RdBU7_0, this$EncodedColors$.RdBU8_0, this$EncodedColors$.RdBU9_0, this$EncodedColors$.RdBU10_0, this$EncodedColors$.RdBU11_0]);
    };
  }
  function EncodedColors$Companion$RdGY3$lambda() {
    return new EncodedColors('ef8a62ffffff999999');
  }
  function EncodedColors$Companion$RdGY4$lambda() {
    return new EncodedColors('ca0020f4a582bababa404040');
  }
  function EncodedColors$Companion$RdGY5$lambda() {
    return new EncodedColors('ca0020f4a582ffffffbababa404040');
  }
  function EncodedColors$Companion$RdGY6$lambda() {
    return new EncodedColors('b2182bef8a62fddbc7e0e0e09999994d4d4d');
  }
  function EncodedColors$Companion$RdGY7$lambda() {
    return new EncodedColors('b2182bef8a62fddbc7ffffffe0e0e09999994d4d4d');
  }
  function EncodedColors$Companion$RdGY8$lambda() {
    return new EncodedColors('b2182bd6604df4a582fddbc7e0e0e0bababa8787874d4d4d');
  }
  function EncodedColors$Companion$RdGY9$lambda() {
    return new EncodedColors('b2182bd6604df4a582fddbc7ffffffe0e0e0bababa8787874d4d4d');
  }
  function EncodedColors$Companion$RdGY10$lambda() {
    return new EncodedColors('67001fb2182bd6604df4a582fddbc7e0e0e0bababa8787874d4d4d1a1a1a');
  }
  function EncodedColors$Companion$RdGY11$lambda() {
    return new EncodedColors('67001fb2182bd6604df4a582fddbc7ffffffe0e0e0bababa8787874d4d4d1a1a1a');
  }
  function EncodedColors$Companion$RdGY$lambda(this$EncodedColors$) {
    return function () {
      return listOf([this$EncodedColors$.RdGY3_0, this$EncodedColors$.RdGY4_0, this$EncodedColors$.RdGY5_0, this$EncodedColors$.RdGY6_0, this$EncodedColors$.RdGY7_0, this$EncodedColors$.RdGY8_0, this$EncodedColors$.RdGY9_0, this$EncodedColors$.RdGY10_0, this$EncodedColors$.RdGY11_0]);
    };
  }
  function EncodedColors$Companion$RdYlBu3$lambda() {
    return new EncodedColors('fc8d59ffffbf91bfdb');
  }
  function EncodedColors$Companion$RdYlBu4$lambda() {
    return new EncodedColors('d7191cfdae61abd9e92c7bb6');
  }
  function EncodedColors$Companion$RdYlBu5$lambda() {
    return new EncodedColors('d7191cfdae61ffffbfabd9e92c7bb6');
  }
  function EncodedColors$Companion$RdYlBu6$lambda() {
    return new EncodedColors('d73027fc8d59fee090e0f3f891bfdb4575b4');
  }
  function EncodedColors$Companion$RdYlBu7$lambda() {
    return new EncodedColors('d73027fc8d59fee090ffffbfe0f3f891bfdb4575b4');
  }
  function EncodedColors$Companion$RdYlBu8$lambda() {
    return new EncodedColors('d73027f46d43fdae61fee090e0f3f8abd9e974add14575b4');
  }
  function EncodedColors$Companion$RdYlBu9$lambda() {
    return new EncodedColors('d73027f46d43fdae61fee090ffffbfe0f3f8abd9e974add14575b4');
  }
  function EncodedColors$Companion$RdYlBu10$lambda() {
    return new EncodedColors('a50026d73027f46d43fdae61fee090e0f3f8abd9e974add14575b4313695');
  }
  function EncodedColors$Companion$RdYlBu11$lambda() {
    return new EncodedColors('a50026d73027f46d43fdae61fee090ffffbfe0f3f8abd9e974add14575b4313695');
  }
  function EncodedColors$Companion$RdYlBu$lambda(this$EncodedColors$) {
    return function () {
      return listOf([this$EncodedColors$.RdYlBu3_0, this$EncodedColors$.RdYlBu4_0, this$EncodedColors$.RdYlBu5_0, this$EncodedColors$.RdYlBu6_0, this$EncodedColors$.RdYlBu7_0, this$EncodedColors$.RdYlBu8_0, this$EncodedColors$.RdYlBu9_0, this$EncodedColors$.RdYlBu10_0, this$EncodedColors$.RdYlBu11_0]);
    };
  }
  function EncodedColors$Companion$RdYlGn3$lambda() {
    return new EncodedColors('fc8d59ffffbf91cf60');
  }
  function EncodedColors$Companion$RdYlGn4$lambda() {
    return new EncodedColors('d7191cfdae61a6d96a1a9641');
  }
  function EncodedColors$Companion$RdYlGn5$lambda() {
    return new EncodedColors('d7191cfdae61ffffbfa6d96a1a9641');
  }
  function EncodedColors$Companion$RdYlGn6$lambda() {
    return new EncodedColors('d73027fc8d59fee08bd9ef8b91cf601a9850');
  }
  function EncodedColors$Companion$RdYlGn7$lambda() {
    return new EncodedColors('d73027fc8d59fee08bffffbfd9ef8b91cf601a9850');
  }
  function EncodedColors$Companion$RdYlGn8$lambda() {
    return new EncodedColors('d73027f46d43fdae61fee08bd9ef8ba6d96a66bd631a9850');
  }
  function EncodedColors$Companion$RdYlGn9$lambda() {
    return new EncodedColors('d73027f46d43fdae61fee08bffffbfd9ef8ba6d96a66bd631a9850');
  }
  function EncodedColors$Companion$RdYlGn10$lambda() {
    return new EncodedColors('a50026d73027f46d43fdae61fee08bd9ef8ba6d96a66bd631a9850006837');
  }
  function EncodedColors$Companion$RdYlGn11$lambda() {
    return new EncodedColors('a50026d73027f46d43fdae61fee08bffffbfd9ef8ba6d96a66bd631a9850006837');
  }
  function EncodedColors$Companion$RdYlGn$lambda(this$EncodedColors$) {
    return function () {
      return listOf([this$EncodedColors$.RdYlGn3_0, this$EncodedColors$.RdYlGn4_0, this$EncodedColors$.RdYlGn5_0, this$EncodedColors$.RdYlGn6_0, this$EncodedColors$.RdYlGn7_0, this$EncodedColors$.RdYlGn8_0, this$EncodedColors$.RdYlGn9_0, this$EncodedColors$.RdYlGn10_0, this$EncodedColors$.RdYlGn11_0]);
    };
  }
  function EncodedColors$Companion$spectral3$lambda() {
    return new EncodedColors('fc8d59ffffbf99d594');
  }
  function EncodedColors$Companion$spectral4$lambda() {
    return new EncodedColors('d7191cfdae61abdda42b83ba');
  }
  function EncodedColors$Companion$spectral5$lambda() {
    return new EncodedColors('d7191cfdae61ffffbfabdda42b83ba');
  }
  function EncodedColors$Companion$spectral6$lambda() {
    return new EncodedColors('d53e4ffc8d59fee08be6f59899d5943288bd');
  }
  function EncodedColors$Companion$spectral7$lambda() {
    return new EncodedColors('d53e4ffc8d59fee08bffffbfe6f59899d5943288bd');
  }
  function EncodedColors$Companion$spectral8$lambda() {
    return new EncodedColors('d53e4ff46d43fdae61fee08be6f598abdda466c2a53288bd');
  }
  function EncodedColors$Companion$spectral9$lambda() {
    return new EncodedColors('d53e4ff46d43fdae61fee08bffffbfe6f598abdda466c2a53288bd');
  }
  function EncodedColors$Companion$spectral10$lambda() {
    return new EncodedColors('9e0142d53e4ff46d43fdae61fee08be6f598abdda466c2a53288bd5e4fa2');
  }
  function EncodedColors$Companion$spectral11$lambda() {
    return new EncodedColors('9e0142d53e4ff46d43fdae61fee08bffffbfe6f598abdda466c2a53288bd5e4fa2');
  }
  function EncodedColors$Companion$spectral$lambda(this$EncodedColors$) {
    return function () {
      return listOf([this$EncodedColors$.spectral3_0, this$EncodedColors$.spectral4_0, this$EncodedColors$.spectral5_0, this$EncodedColors$.spectral6_0, this$EncodedColors$.spectral7_0, this$EncodedColors$.spectral8_0, this$EncodedColors$.spectral9_0, this$EncodedColors$.spectral10_0, this$EncodedColors$.spectral11_0]);
    };
  }
  function EncodedColors$Companion$viridis$lambda() {
    return new EncodedColors('44015444025645045745055946075a46085c460a5d460b5e470d60470e6147106347116447136548146748166848176948186a481a6c481b6d481c6e481d6f481f70482071482173482374482475482576482677482878482979472a7a472c7a472d7b472e7c472f7d46307e46327e46337f463480453581453781453882443983443a83443b84433d84433e85423f854240864241864142874144874045884046883f47883f48893e49893e4a893e4c8a3d4d8a3d4e8a3c4f8a3c508b3b518b3b528b3a538b3a548c39558c39568c38588c38598c375a8c375b8d365c8d365d8d355e8d355f8d34608d34618d33628d33638d32648e32658e31668e31678e31688e30698e306a8e2f6b8e2f6c8e2e6d8e2e6e8e2e6f8e2d708e2d718e2c718e2c728e2c738e2b748e2b758e2a768e2a778e2a788e29798e297a8e297b8e287c8e287d8e277e8e277f8e27808e26818e26828e26828e25838e25848e25858e24868e24878e23888e23898e238a8d228b8d228c8d228d8d218e8d218f8d21908d21918c20928c20928c20938c1f948c1f958b1f968b1f978b1f988b1f998a1f9a8a1e9b8a1e9c891e9d891f9e891f9f881fa0881fa1881fa1871fa28720a38620a48621a58521a68522a78522a88423a98324aa8325ab8225ac8226ad8127ad8128ae8029af7f2ab07f2cb17e2db27d2eb37c2fb47c31b57b32b67a34b67935b77937b87838b9773aba763bbb753dbc743fbc7340bd7242be7144bf7046c06f48c16e4ac16d4cc26c4ec36b50c46a52c56954c56856c66758c7655ac8645cc8635ec96260ca6063cb5f65cb5e67cc5c69cd5b6ccd5a6ece5870cf5773d05675d05477d1537ad1517cd2507fd34e81d34d84d44b86d54989d5488bd6468ed64590d74393d74195d84098d83e9bd93c9dd93ba0da39a2da37a5db36a8db34aadc32addc30b0dd2fb2dd2db5de2bb8de29bade28bddf26c0df25c2df23c5e021c8e020cae11fcde11dd0e11cd2e21bd5e21ad8e219dae319dde318dfe318e2e418e5e419e7e419eae51aece51befe51cf1e51df4e61ef6e620f8e621fbe723fde725');
  }
  function EncodedColors$Companion$magma$lambda() {
    return new EncodedColors('00000401000501010601010802010902020b02020d03030f03031204041405041606051806051a07061c08071e0907200a08220b09240c09260d0a290e0b2b100b2d110c2f120d31130d34140e36150e38160f3b180f3d19103f1a10421c10441d11471e114920114b21114e22115024125325125527125829115a2a115c2c115f2d11612f116331116533106734106936106b38106c390f6e3b0f703d0f713f0f72400f74420f75440f764510774710784910784a10794c117a4e117b4f127b51127c52137c54137d56147d57157e59157e5a167e5c167f5d177f5f187f601880621980641a80651a80671b80681c816a1c816b1d816d1d816e1e81701f81721f817320817521817621817822817922827b23827c23827e24828025828125818326818426818627818827818928818b29818c29818e2a81902a81912b81932b80942c80962c80982d80992d809b2e7f9c2e7f9e2f7fa02f7fa1307ea3307ea5317ea6317da8327daa337dab337cad347cae347bb0357bb2357bb3367ab5367ab73779b83779ba3878bc3978bd3977bf3a77c03a76c23b75c43c75c53c74c73d73c83e73ca3e72cc3f71cd4071cf4070d0416fd2426fd3436ed5446dd6456cd8456cd9466bdb476adc4869de4968df4a68e04c67e24d66e34e65e44f64e55064e75263e85362e95462ea5661eb5760ec5860ed5a5fee5b5eef5d5ef05f5ef1605df2625df2645cf3655cf4675cf4695cf56b5cf66c5cf66e5cf7705cf7725cf8745cf8765cf9785df9795df97b5dfa7d5efa7f5efa815ffb835ffb8560fb8761fc8961fc8a62fc8c63fc8e64fc9065fd9266fd9467fd9668fd9869fd9a6afd9b6bfe9d6cfe9f6dfea16efea36ffea571fea772fea973feaa74feac76feae77feb078feb27afeb47bfeb67cfeb77efeb97ffebb81febd82febf84fec185fec287fec488fec68afec88cfeca8dfecc8ffecd90fecf92fed194fed395fed597fed799fed89afdda9cfddc9efddea0fde0a1fde2a3fde3a5fde5a7fde7a9fde9aafdebacfcecaefceeb0fcf0b2fcf2b4fcf4b6fcf6b8fcf7b9fcf9bbfcfbbdfcfdbf');
  }
  function EncodedColors$Companion$inferno$lambda() {
    return new EncodedColors('00000401000501010601010802010a02020c02020e03021004031204031405041706041907051b08051d09061f0a07220b07240c08260d08290e092b10092d110a30120a32140b34150b37160b39180c3c190c3e1b0c411c0c431e0c451f0c48210c4a230c4c240c4f260c51280b53290b552b0b572d0b592f0a5b310a5c320a5e340a5f3609613809623909633b09643d09653e0966400a67420a68440a68450a69470b6a490b6a4a0c6b4c0c6b4d0d6c4f0d6c510e6c520e6d540f6d550f6d57106e59106e5a116e5c126e5d126e5f136e61136e62146e64156e65156e67166e69166e6a176e6c186e6d186e6f196e71196e721a6e741a6e751b6e771c6d781c6d7a1d6d7c1d6d7d1e6d7f1e6c801f6c82206c84206b85216b87216b88226a8a226a8c23698d23698f24699025689225689326679526679727669827669a28659b29649d29649f2a63a02a63a22b62a32c61a52c60a62d60a82e5fa92e5eab2f5ead305dae305cb0315bb1325ab3325ab43359b63458b73557b93556ba3655bc3754bd3853bf3952c03a51c13a50c33b4fc43c4ec63d4dc73e4cc83f4bca404acb4149cc4248ce4347cf4446d04545d24644d34743d44842d54a41d74b3fd84c3ed94d3dda4e3cdb503bdd513ade5238df5337e05536e15635e25734e35933e45a31e55c30e65d2fe75e2ee8602de9612bea632aeb6429eb6628ec6726ed6925ee6a24ef6c23ef6e21f06f20f1711ff1731df2741cf3761bf37819f47918f57b17f57d15f67e14f68013f78212f78410f8850ff8870ef8890cf98b0bf98c0af98e09fa9008fa9207fa9407fb9606fb9706fb9906fb9b06fb9d07fc9f07fca108fca309fca50afca60cfca80dfcaa0ffcac11fcae12fcb014fcb216fcb418fbb61afbb81dfbba1ffbbc21fbbe23fac026fac228fac42afac62df9c72ff9c932f9cb35f8cd37f8cf3af7d13df7d340f6d543f6d746f5d949f5db4cf4dd4ff4df53f4e156f3e35af3e55df2e661f2e865f2ea69f1ec6df1ed71f1ef75f1f179f2f27df2f482f3f586f3f68af4f88ef5f992f6fa96f8fb9af9fc9dfafda1fcffa4');
  }
  function EncodedColors$Companion$plasma$lambda() {
    return new EncodedColors('0d088710078813078916078a19068c1b068d1d068e20068f2206902406912605912805922a05932c05942e05952f059631059733059735049837049938049a3a049a3c049b3e049c3f049c41049d43039e44039e46039f48039f4903a04b03a14c02a14e02a25002a25102a35302a35502a45601a45801a45901a55b01a55c01a65e01a66001a66100a76300a76400a76600a76700a86900a86a00a86c00a86e00a86f00a87100a87201a87401a87501a87701a87801a87a02a87b02a87d03a87e03a88004a88104a78305a78405a78606a68707a68808a68a09a58b0aa58d0ba58e0ca48f0da4910ea3920fa39410a29511a19613a19814a099159f9a169f9c179e9d189d9e199da01a9ca11b9ba21d9aa31e9aa51f99a62098a72197a82296aa2395ab2494ac2694ad2793ae2892b02991b12a90b22b8fb32c8eb42e8db52f8cb6308bb7318ab83289ba3388bb3488bc3587bd3786be3885bf3984c03a83c13b82c23c81c33d80c43e7fc5407ec6417dc7427cc8437bc9447aca457acb4679cc4778cc4977cd4a76ce4b75cf4c74d04d73d14e72d24f71d35171d45270d5536fd5546ed6556dd7566cd8576bd9586ada5a6ada5b69db5c68dc5d67dd5e66de5f65de6164df6263e06363e16462e26561e26660e3685fe4695ee56a5de56b5de66c5ce76e5be76f5ae87059e97158e97257ea7457eb7556eb7655ec7754ed7953ed7a52ee7b51ef7c51ef7e50f07f4ff0804ef1814df1834cf2844bf3854bf3874af48849f48948f58b47f58c46f68d45f68f44f79044f79143f79342f89441f89540f9973ff9983ef99a3efa9b3dfa9c3cfa9e3bfb9f3afba139fba238fca338fca537fca636fca835fca934fdab33fdac33fdae32fdaf31fdb130fdb22ffdb42ffdb52efeb72dfeb82cfeba2cfebb2bfebd2afebe2afec029fdc229fdc328fdc527fdc627fdc827fdca26fdcb26fccd25fcce25fcd025fcd225fbd324fbd524fbd724fad824fada24f9dc24f9dd25f8df25f8e125f7e225f7e425f6e626f6e826f5e926f5eb27f4ed27f3ee27f3f027f2f227f1f426f1f525f0f724f0f921');
  }
  function EncodedColors$Companion$BuGN3$lambda() {
    return new EncodedColors('e5f5f999d8c92ca25f');
  }
  function EncodedColors$Companion$BuGN4$lambda() {
    return new EncodedColors('edf8fbb2e2e266c2a4238b45');
  }
  function EncodedColors$Companion$BuGN5$lambda() {
    return new EncodedColors('edf8fbb2e2e266c2a42ca25f006d2c');
  }
  function EncodedColors$Companion$BuGN6$lambda() {
    return new EncodedColors('edf8fbccece699d8c966c2a42ca25f006d2c');
  }
  function EncodedColors$Companion$BuGN7$lambda() {
    return new EncodedColors('edf8fbccece699d8c966c2a441ae76238b45005824');
  }
  function EncodedColors$Companion$BuGN8$lambda() {
    return new EncodedColors('f7fcfde5f5f9ccece699d8c966c2a441ae76238b45005824');
  }
  function EncodedColors$Companion$BuGN9$lambda() {
    return new EncodedColors('f7fcfde5f5f9ccece699d8c966c2a441ae76238b45006d2c00441b');
  }
  function EncodedColors$Companion$BuGN$lambda(this$EncodedColors$) {
    return function () {
      return listOf([this$EncodedColors$.BuGN3_0, this$EncodedColors$.BuGN4_0, this$EncodedColors$.BuGN5_0, this$EncodedColors$.BuGN6_0, this$EncodedColors$.BuGN7_0, this$EncodedColors$.BuGN8_0, this$EncodedColors$.BuGN9_0]);
    };
  }
  function EncodedColors$Companion$BuPu3$lambda() {
    return new EncodedColors('e0ecf49ebcda8856a7');
  }
  function EncodedColors$Companion$BuPu4$lambda() {
    return new EncodedColors('edf8fbb3cde38c96c688419d');
  }
  function EncodedColors$Companion$BuPu5$lambda() {
    return new EncodedColors('edf8fbb3cde38c96c68856a7810f7c');
  }
  function EncodedColors$Companion$BuPu6$lambda() {
    return new EncodedColors('edf8fbbfd3e69ebcda8c96c68856a7810f7c');
  }
  function EncodedColors$Companion$BuPu7$lambda() {
    return new EncodedColors('edf8fbbfd3e69ebcda8c96c68c6bb188419d6e016b');
  }
  function EncodedColors$Companion$BuPu8$lambda() {
    return new EncodedColors('f7fcfde0ecf4bfd3e69ebcda8c96c68c6bb188419d6e016b');
  }
  function EncodedColors$Companion$BuPu9$lambda() {
    return new EncodedColors('f7fcfde0ecf4bfd3e69ebcda8c96c68c6bb188419d810f7c4d004b');
  }
  function EncodedColors$Companion$BuPu$lambda(this$EncodedColors$) {
    return function () {
      return listOf([this$EncodedColors$.BuPu3_0, this$EncodedColors$.BuPu4_0, this$EncodedColors$.BuPu5_0, this$EncodedColors$.BuPu6_0, this$EncodedColors$.BuPu7_0, this$EncodedColors$.BuPu8_0, this$EncodedColors$.BuPu9_0]);
    };
  }
  function EncodedColors$Companion$GnBu3$lambda() {
    return new EncodedColors('e0f3dba8ddb543a2ca');
  }
  function EncodedColors$Companion$GnBu4$lambda() {
    return new EncodedColors('f0f9e8bae4bc7bccc42b8cbe');
  }
  function EncodedColors$Companion$GnBu5$lambda() {
    return new EncodedColors('f0f9e8bae4bc7bccc443a2ca0868ac');
  }
  function EncodedColors$Companion$GnBu6$lambda() {
    return new EncodedColors('f0f9e8ccebc5a8ddb57bccc443a2ca0868ac');
  }
  function EncodedColors$Companion$GnBu7$lambda() {
    return new EncodedColors('f0f9e8ccebc5a8ddb57bccc44eb3d32b8cbe08589e');
  }
  function EncodedColors$Companion$GnBu8$lambda() {
    return new EncodedColors('f7fcf0e0f3dbccebc5a8ddb57bccc44eb3d32b8cbe08589e');
  }
  function EncodedColors$Companion$GnBu9$lambda() {
    return new EncodedColors('f7fcf0e0f3dbccebc5a8ddb57bccc44eb3d32b8cbe0868ac084081');
  }
  function EncodedColors$Companion$GnBu$lambda(this$EncodedColors$) {
    return function () {
      return listOf([this$EncodedColors$.GnBu3_0, this$EncodedColors$.GnBu4_0, this$EncodedColors$.GnBu5_0, this$EncodedColors$.GnBu6_0, this$EncodedColors$.GnBu7_0, this$EncodedColors$.GnBu8_0, this$EncodedColors$.GnBu9_0]);
    };
  }
  function EncodedColors$Companion$OrRd3$lambda() {
    return new EncodedColors('fee8c8fdbb84e34a33');
  }
  function EncodedColors$Companion$OrRd4$lambda() {
    return new EncodedColors('fef0d9fdcc8afc8d59d7301f');
  }
  function EncodedColors$Companion$OrRd5$lambda() {
    return new EncodedColors('fef0d9fdcc8afc8d59e34a33b30000');
  }
  function EncodedColors$Companion$OrRd6$lambda() {
    return new EncodedColors('fef0d9fdd49efdbb84fc8d59e34a33b30000');
  }
  function EncodedColors$Companion$OrRd7$lambda() {
    return new EncodedColors('fef0d9fdd49efdbb84fc8d59ef6548d7301f990000');
  }
  function EncodedColors$Companion$OrRd8$lambda() {
    return new EncodedColors('fff7ecfee8c8fdd49efdbb84fc8d59ef6548d7301f990000');
  }
  function EncodedColors$Companion$OrRd9$lambda() {
    return new EncodedColors('fff7ecfee8c8fdd49efdbb84fc8d59ef6548d7301fb300007f0000');
  }
  function EncodedColors$Companion$OrRd$lambda(this$EncodedColors$) {
    return function () {
      return listOf([this$EncodedColors$.OrRd3_0, this$EncodedColors$.OrRd4_0, this$EncodedColors$.OrRd5_0, this$EncodedColors$.OrRd6_0, this$EncodedColors$.OrRd7_0, this$EncodedColors$.OrRd8_0, this$EncodedColors$.OrRd9_0]);
    };
  }
  function EncodedColors$Companion$PuBu3$lambda() {
    return new EncodedColors('ece7f2a6bddb2b8cbe');
  }
  function EncodedColors$Companion$PuBu4$lambda() {
    return new EncodedColors('f1eef6bdc9e174a9cf0570b0');
  }
  function EncodedColors$Companion$PuBu5$lambda() {
    return new EncodedColors('f1eef6bdc9e174a9cf2b8cbe045a8d');
  }
  function EncodedColors$Companion$PuBu6$lambda() {
    return new EncodedColors('f1eef6d0d1e6a6bddb74a9cf2b8cbe045a8d');
  }
  function EncodedColors$Companion$PuBu7$lambda() {
    return new EncodedColors('f1eef6d0d1e6a6bddb74a9cf3690c00570b0034e7b');
  }
  function EncodedColors$Companion$PuBu8$lambda() {
    return new EncodedColors('fff7fbece7f2d0d1e6a6bddb74a9cf3690c00570b0034e7b');
  }
  function EncodedColors$Companion$PuBu9$lambda() {
    return new EncodedColors('fff7fbece7f2d0d1e6a6bddb74a9cf3690c00570b0045a8d023858');
  }
  function EncodedColors$Companion$PuBu$lambda(this$EncodedColors$) {
    return function () {
      return listOf([this$EncodedColors$.PuBu3_0, this$EncodedColors$.PuBu4_0, this$EncodedColors$.PuBu5_0, this$EncodedColors$.PuBu6_0, this$EncodedColors$.PuBu7_0, this$EncodedColors$.PuBu8_0, this$EncodedColors$.PuBu9_0]);
    };
  }
  function EncodedColors$Companion$PuBuGn3$lambda() {
    return new EncodedColors('ece2f0a6bddb1c9099');
  }
  function EncodedColors$Companion$PuBuGn4$lambda() {
    return new EncodedColors('f6eff7bdc9e167a9cf02818a');
  }
  function EncodedColors$Companion$PuBuGn5$lambda() {
    return new EncodedColors('f6eff7bdc9e167a9cf1c9099016c59');
  }
  function EncodedColors$Companion$PuBuGn6$lambda() {
    return new EncodedColors('f6eff7d0d1e6a6bddb67a9cf1c9099016c59');
  }
  function EncodedColors$Companion$PuBuGn7$lambda() {
    return new EncodedColors('f6eff7d0d1e6a6bddb67a9cf3690c002818a016450');
  }
  function EncodedColors$Companion$PuBuGn8$lambda() {
    return new EncodedColors('fff7fbece2f0d0d1e6a6bddb67a9cf3690c002818a016450');
  }
  function EncodedColors$Companion$PuBuGn9$lambda() {
    return new EncodedColors('fff7fbece2f0d0d1e6a6bddb67a9cf3690c002818a016c59014636');
  }
  function EncodedColors$Companion$PuBuGn$lambda(this$EncodedColors$) {
    return function () {
      return listOf([this$EncodedColors$.PuBuGn3_0, this$EncodedColors$.PuBuGn4_0, this$EncodedColors$.PuBuGn5_0, this$EncodedColors$.PuBuGn6_0, this$EncodedColors$.PuBuGn7_0, this$EncodedColors$.PuBuGn8_0, this$EncodedColors$.PuBuGn9_0]);
    };
  }
  function EncodedColors$Companion$PuRd3$lambda() {
    return new EncodedColors('e7e1efc994c7dd1c77');
  }
  function EncodedColors$Companion$PuRd4$lambda() {
    return new EncodedColors('f1eef6d7b5d8df65b0ce1256');
  }
  function EncodedColors$Companion$PuRd5$lambda() {
    return new EncodedColors('f1eef6d7b5d8df65b0dd1c77980043');
  }
  function EncodedColors$Companion$PuRd6$lambda() {
    return new EncodedColors('f1eef6d4b9dac994c7df65b0dd1c77980043');
  }
  function EncodedColors$Companion$PuRd7$lambda() {
    return new EncodedColors('f1eef6d4b9dac994c7df65b0e7298ace125691003f');
  }
  function EncodedColors$Companion$PuRd8$lambda() {
    return new EncodedColors('f7f4f9e7e1efd4b9dac994c7df65b0e7298ace125691003f');
  }
  function EncodedColors$Companion$PuRd9$lambda() {
    return new EncodedColors('f7f4f9e7e1efd4b9dac994c7df65b0e7298ace125698004367001f');
  }
  function EncodedColors$Companion$PuRd$lambda(this$EncodedColors$) {
    return function () {
      return listOf([this$EncodedColors$.PuRd3_0, this$EncodedColors$.PuRd4_0, this$EncodedColors$.PuRd5_0, this$EncodedColors$.PuRd6_0, this$EncodedColors$.PuRd7_0, this$EncodedColors$.PuRd8_0, this$EncodedColors$.PuRd9_0]);
    };
  }
  function EncodedColors$Companion$RdPu3$lambda() {
    return new EncodedColors('fde0ddfa9fb5c51b8a');
  }
  function EncodedColors$Companion$RdPu4$lambda() {
    return new EncodedColors('feebe2fbb4b9f768a1ae017e');
  }
  function EncodedColors$Companion$RdPu5$lambda() {
    return new EncodedColors('feebe2fbb4b9f768a1c51b8a7a0177');
  }
  function EncodedColors$Companion$RdPu6$lambda() {
    return new EncodedColors('feebe2fcc5c0fa9fb5f768a1c51b8a7a0177');
  }
  function EncodedColors$Companion$RdPu7$lambda() {
    return new EncodedColors('feebe2fcc5c0fa9fb5f768a1dd3497ae017e7a0177');
  }
  function EncodedColors$Companion$RdPu8$lambda() {
    return new EncodedColors('fff7f3fde0ddfcc5c0fa9fb5f768a1dd3497ae017e7a0177');
  }
  function EncodedColors$Companion$RdPu9$lambda() {
    return new EncodedColors('fff7f3fde0ddfcc5c0fa9fb5f768a1dd3497ae017e7a017749006a');
  }
  function EncodedColors$Companion$RdPu$lambda(this$EncodedColors$) {
    return function () {
      return listOf([this$EncodedColors$.RdPu3_0, this$EncodedColors$.RdPu4_0, this$EncodedColors$.RdPu5_0, this$EncodedColors$.RdPu6_0, this$EncodedColors$.RdPu7_0, this$EncodedColors$.RdPu8_0, this$EncodedColors$.RdPu9_0]);
    };
  }
  function EncodedColors$Companion$YlGn3$lambda() {
    return new EncodedColors('f7fcb9addd8e31a354');
  }
  function EncodedColors$Companion$YlGn4$lambda() {
    return new EncodedColors('ffffccc2e69978c679238443');
  }
  function EncodedColors$Companion$YlGn5$lambda() {
    return new EncodedColors('ffffccc2e69978c67931a354006837');
  }
  function EncodedColors$Companion$YlGn6$lambda() {
    return new EncodedColors('ffffccd9f0a3addd8e78c67931a354006837');
  }
  function EncodedColors$Companion$YlGn7$lambda() {
    return new EncodedColors('ffffccd9f0a3addd8e78c67941ab5d238443005a32');
  }
  function EncodedColors$Companion$YlGn8$lambda() {
    return new EncodedColors('ffffe5f7fcb9d9f0a3addd8e78c67941ab5d238443005a32');
  }
  function EncodedColors$Companion$YlGn9$lambda() {
    return new EncodedColors('ffffe5f7fcb9d9f0a3addd8e78c67941ab5d238443006837004529');
  }
  function EncodedColors$Companion$YlGn$lambda(this$EncodedColors$) {
    return function () {
      return listOf([this$EncodedColors$.YlGn3_0, this$EncodedColors$.YlGn4_0, this$EncodedColors$.YlGn5_0, this$EncodedColors$.YlGn6_0, this$EncodedColors$.YlGn7_0, this$EncodedColors$.YlGn8_0, this$EncodedColors$.YlGn9_0]);
    };
  }
  function EncodedColors$Companion$YlGnbU3$lambda() {
    return new EncodedColors('edf8b17fcdbb2c7fb8');
  }
  function EncodedColors$Companion$YlGnbU4$lambda() {
    return new EncodedColors('ffffcca1dab441b6c4225ea8');
  }
  function EncodedColors$Companion$YlGnbU5$lambda() {
    return new EncodedColors('ffffcca1dab441b6c42c7fb8253494');
  }
  function EncodedColors$Companion$YlGnbU6$lambda() {
    return new EncodedColors('ffffccc7e9b47fcdbb41b6c42c7fb8253494');
  }
  function EncodedColors$Companion$YlGnbU7$lambda() {
    return new EncodedColors('ffffccc7e9b47fcdbb41b6c41d91c0225ea80c2c84');
  }
  function EncodedColors$Companion$YlGnbU8$lambda() {
    return new EncodedColors('ffffd9edf8b1c7e9b47fcdbb41b6c41d91c0225ea80c2c84');
  }
  function EncodedColors$Companion$YlGnbU9$lambda() {
    return new EncodedColors('ffffd9edf8b1c7e9b47fcdbb41b6c41d91c0225ea8253494081d58');
  }
  function EncodedColors$Companion$YlGnbU$lambda(this$EncodedColors$) {
    return function () {
      return listOf([this$EncodedColors$.YlGnbU3_0, this$EncodedColors$.YlGnbU4_0, this$EncodedColors$.YlGnbU5_0, this$EncodedColors$.YlGnbU6_0, this$EncodedColors$.YlGnbU7_0, this$EncodedColors$.YlGnbU8_0, this$EncodedColors$.YlGnbU9_0]);
    };
  }
  function EncodedColors$Companion$YlGnBr3$lambda() {
    return new EncodedColors('fff7bcfec44fd95f0e');
  }
  function EncodedColors$Companion$YlGnBr4$lambda() {
    return new EncodedColors('ffffd4fed98efe9929cc4c02');
  }
  function EncodedColors$Companion$YlGnBr5$lambda() {
    return new EncodedColors('ffffd4fed98efe9929d95f0e993404');
  }
  function EncodedColors$Companion$YlGnBr6$lambda() {
    return new EncodedColors('ffffd4fee391fec44ffe9929d95f0e993404');
  }
  function EncodedColors$Companion$YlGnBr7$lambda() {
    return new EncodedColors('ffffd4fee391fec44ffe9929ec7014cc4c028c2d04');
  }
  function EncodedColors$Companion$YlGnBr8$lambda() {
    return new EncodedColors('ffffe5fff7bcfee391fec44ffe9929ec7014cc4c028c2d04');
  }
  function EncodedColors$Companion$YlGnBr9$lambda() {
    return new EncodedColors('ffffe5fff7bcfee391fec44ffe9929ec7014cc4c02993404662506');
  }
  function EncodedColors$Companion$YlGnBr$lambda(this$EncodedColors$) {
    return function () {
      return listOf([this$EncodedColors$.YlGnBr3_0, this$EncodedColors$.YlGnBr4_0, this$EncodedColors$.YlGnBr5_0, this$EncodedColors$.YlGnBr6_0, this$EncodedColors$.YlGnBr7_0, this$EncodedColors$.YlGnBr8_0, this$EncodedColors$.YlGnBr9_0]);
    };
  }
  function EncodedColors$Companion$YlGnRd3$lambda() {
    return new EncodedColors('ffeda0feb24cf03b20');
  }
  function EncodedColors$Companion$YlGnRd4$lambda() {
    return new EncodedColors('ffffb2fecc5cfd8d3ce31a1c');
  }
  function EncodedColors$Companion$YlGnRd5$lambda() {
    return new EncodedColors('ffffb2fecc5cfd8d3cf03b20bd0026');
  }
  function EncodedColors$Companion$YlGnRd6$lambda() {
    return new EncodedColors('ffffb2fed976feb24cfd8d3cf03b20bd0026');
  }
  function EncodedColors$Companion$YlGnRd7$lambda() {
    return new EncodedColors('ffffb2fed976feb24cfd8d3cfc4e2ae31a1cb10026');
  }
  function EncodedColors$Companion$YlGnRd8$lambda() {
    return new EncodedColors('ffffccffeda0fed976feb24cfd8d3cfc4e2ae31a1cb10026');
  }
  function EncodedColors$Companion$YlGnRd9$lambda() {
    return new EncodedColors('ffffccffeda0fed976feb24cfd8d3cfc4e2ae31a1cbd0026800026');
  }
  function EncodedColors$Companion$YlGnRd$lambda(this$EncodedColors$) {
    return function () {
      return listOf([this$EncodedColors$.YlGnRd3_0, this$EncodedColors$.YlGnRd4_0, this$EncodedColors$.YlGnRd5_0, this$EncodedColors$.YlGnRd6_0, this$EncodedColors$.YlGnRd7_0, this$EncodedColors$.YlGnRd8_0, this$EncodedColors$.YlGnRd9_0]);
    };
  }
  function EncodedColors$Companion$blues3$lambda() {
    return new EncodedColors('deebf79ecae13182bd');
  }
  function EncodedColors$Companion$blues4$lambda() {
    return new EncodedColors('eff3ffbdd7e76baed62171b5');
  }
  function EncodedColors$Companion$blues5$lambda() {
    return new EncodedColors('eff3ffbdd7e76baed63182bd08519c');
  }
  function EncodedColors$Companion$blues6$lambda() {
    return new EncodedColors('eff3ffc6dbef9ecae16baed63182bd08519c');
  }
  function EncodedColors$Companion$blues7$lambda() {
    return new EncodedColors('eff3ffc6dbef9ecae16baed64292c62171b5084594');
  }
  function EncodedColors$Companion$blues8$lambda() {
    return new EncodedColors('f7fbffdeebf7c6dbef9ecae16baed64292c62171b5084594');
  }
  function EncodedColors$Companion$blues9$lambda() {
    return new EncodedColors('f7fbffdeebf7c6dbef9ecae16baed64292c62171b508519c08306b');
  }
  function EncodedColors$Companion$blues$lambda(this$EncodedColors$) {
    return function () {
      return listOf([this$EncodedColors$.blues3_0, this$EncodedColors$.blues4_0, this$EncodedColors$.blues5_0, this$EncodedColors$.blues6_0, this$EncodedColors$.blues7_0, this$EncodedColors$.blues8_0, this$EncodedColors$.blues9_0]);
    };
  }
  function EncodedColors$Companion$greens3$lambda() {
    return new EncodedColors('e5f5e0a1d99b31a354');
  }
  function EncodedColors$Companion$greens4$lambda() {
    return new EncodedColors('edf8e9bae4b374c476238b45');
  }
  function EncodedColors$Companion$greens5$lambda() {
    return new EncodedColors('edf8e9bae4b374c47631a354006d2c');
  }
  function EncodedColors$Companion$greens6$lambda() {
    return new EncodedColors('edf8e9c7e9c0a1d99b74c47631a354006d2c');
  }
  function EncodedColors$Companion$greens7$lambda() {
    return new EncodedColors('edf8e9c7e9c0a1d99b74c47641ab5d238b45005a32');
  }
  function EncodedColors$Companion$greens8$lambda() {
    return new EncodedColors('f7fcf5e5f5e0c7e9c0a1d99b74c47641ab5d238b45005a32');
  }
  function EncodedColors$Companion$greens9$lambda() {
    return new EncodedColors('f7fcf5e5f5e0c7e9c0a1d99b74c47641ab5d238b45006d2c00441b');
  }
  function EncodedColors$Companion$greens$lambda(this$EncodedColors$) {
    return function () {
      return listOf([this$EncodedColors$.greens3_0, this$EncodedColors$.greens4_0, this$EncodedColors$.greens5_0, this$EncodedColors$.greens6_0, this$EncodedColors$.greens7_0, this$EncodedColors$.greens8_0, this$EncodedColors$.greens9_0]);
    };
  }
  function EncodedColors$Companion$greys3$lambda() {
    return new EncodedColors('f0f0f0bdbdbd636363');
  }
  function EncodedColors$Companion$greys4$lambda() {
    return new EncodedColors('f7f7f7cccccc969696525252');
  }
  function EncodedColors$Companion$greys5$lambda() {
    return new EncodedColors('f7f7f7cccccc969696636363252525');
  }
  function EncodedColors$Companion$greys6$lambda() {
    return new EncodedColors('f7f7f7d9d9d9bdbdbd969696636363252525');
  }
  function EncodedColors$Companion$greys7$lambda() {
    return new EncodedColors('f7f7f7d9d9d9bdbdbd969696737373525252252525');
  }
  function EncodedColors$Companion$greys8$lambda() {
    return new EncodedColors('fffffff0f0f0d9d9d9bdbdbd969696737373525252252525');
  }
  function EncodedColors$Companion$greys9$lambda() {
    return new EncodedColors('fffffff0f0f0d9d9d9bdbdbd969696737373525252252525000000');
  }
  function EncodedColors$Companion$greys$lambda(this$EncodedColors$) {
    return function () {
      return listOf([this$EncodedColors$.greys3_0, this$EncodedColors$.greys4_0, this$EncodedColors$.greys5_0, this$EncodedColors$.greys6_0, this$EncodedColors$.greys7_0, this$EncodedColors$.greys8_0, this$EncodedColors$.greys9_0]);
    };
  }
  function EncodedColors$Companion$oranges3$lambda() {
    return new EncodedColors('fee6cefdae6be6550d');
  }
  function EncodedColors$Companion$oranges4$lambda() {
    return new EncodedColors('feeddefdbe85fd8d3cd94701');
  }
  function EncodedColors$Companion$oranges5$lambda() {
    return new EncodedColors('feeddefdbe85fd8d3ce6550da63603');
  }
  function EncodedColors$Companion$oranges6$lambda() {
    return new EncodedColors('feeddefdd0a2fdae6bfd8d3ce6550da63603');
  }
  function EncodedColors$Companion$oranges7$lambda() {
    return new EncodedColors('feeddefdd0a2fdae6bfd8d3cf16913d948018c2d04');
  }
  function EncodedColors$Companion$oranges8$lambda() {
    return new EncodedColors('fff5ebfee6cefdd0a2fdae6bfd8d3cf16913d948018c2d04');
  }
  function EncodedColors$Companion$oranges9$lambda() {
    return new EncodedColors('fff5ebfee6cefdd0a2fdae6bfd8d3cf16913d94801a636037f2704');
  }
  function EncodedColors$Companion$oranges$lambda(this$EncodedColors$) {
    return function () {
      return listOf([this$EncodedColors$.oranges3_0, this$EncodedColors$.oranges4_0, this$EncodedColors$.oranges5_0, this$EncodedColors$.oranges6_0, this$EncodedColors$.oranges7_0, this$EncodedColors$.oranges8_0, this$EncodedColors$.oranges9_0]);
    };
  }
  function EncodedColors$Companion$purples3$lambda() {
    return new EncodedColors('efedf5bcbddc756bb1');
  }
  function EncodedColors$Companion$purples4$lambda() {
    return new EncodedColors('f2f0f7cbc9e29e9ac86a51a3');
  }
  function EncodedColors$Companion$purples5$lambda() {
    return new EncodedColors('f2f0f7cbc9e29e9ac8756bb154278f');
  }
  function EncodedColors$Companion$purples6$lambda() {
    return new EncodedColors('f2f0f7dadaebbcbddc9e9ac8756bb154278f');
  }
  function EncodedColors$Companion$purples7$lambda() {
    return new EncodedColors('f2f0f7dadaebbcbddc9e9ac8807dba6a51a34a1486');
  }
  function EncodedColors$Companion$purples8$lambda() {
    return new EncodedColors('fcfbfdefedf5dadaebbcbddc9e9ac8807dba6a51a34a1486');
  }
  function EncodedColors$Companion$purples9$lambda() {
    return new EncodedColors('fcfbfdefedf5dadaebbcbddc9e9ac8807dba6a51a354278f3f007d');
  }
  function EncodedColors$Companion$purples$lambda(this$EncodedColors$) {
    return function () {
      return listOf([this$EncodedColors$.purples3_0, this$EncodedColors$.purples4_0, this$EncodedColors$.purples5_0, this$EncodedColors$.purples6_0, this$EncodedColors$.purples7_0, this$EncodedColors$.purples8_0, this$EncodedColors$.purples9_0]);
    };
  }
  function EncodedColors$Companion$reds3$lambda() {
    return new EncodedColors('fee0d2fc9272de2d26');
  }
  function EncodedColors$Companion$reds4$lambda() {
    return new EncodedColors('fee5d9fcae91fb6a4acb181d');
  }
  function EncodedColors$Companion$reds5$lambda() {
    return new EncodedColors('fee5d9fcae91fb6a4ade2d26a50f15');
  }
  function EncodedColors$Companion$reds6$lambda() {
    return new EncodedColors('fee5d9fcbba1fc9272fb6a4ade2d26a50f15');
  }
  function EncodedColors$Companion$reds7$lambda() {
    return new EncodedColors('fee5d9fcbba1fc9272fb6a4aef3b2ccb181d99000d');
  }
  function EncodedColors$Companion$reds8$lambda() {
    return new EncodedColors('fff5f0fee0d2fcbba1fc9272fb6a4aef3b2ccb181d99000d');
  }
  function EncodedColors$Companion$reds9$lambda() {
    return new EncodedColors('fff5f0fee0d2fcbba1fc9272fb6a4aef3b2ccb181da50f1567000d');
  }
  function EncodedColors$Companion$reds$lambda(this$EncodedColors$) {
    return function () {
      return listOf([this$EncodedColors$.reds3_0, this$EncodedColors$.reds4_0, this$EncodedColors$.reds5_0, this$EncodedColors$.reds6_0, this$EncodedColors$.reds7_0, this$EncodedColors$.reds8_0, this$EncodedColors$.reds9_0]);
    };
  }
  EncodedColors$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var EncodedColors$Companion_instance = null;
  function EncodedColors$Companion_getInstance() {
    if (EncodedColors$Companion_instance === null) {
      new EncodedColors$Companion();
    }
    return EncodedColors$Companion_instance;
  }
  Object.defineProperty(EncodedColors.prototype, 'colors', {
    get: function () {
      return this.colors_xs48u0$_0.value;
    }
  });
  EncodedColors.prototype.color_14dthe$ = function (percent) {
    var tmp$ = this.colors;
    var x = percent * this.colors.size;
    return tmp$.get_za3lpa$(coerceAtMost(coerceAtLeast(numberToInt(Math_0.floor(x)), 0), this.colors.size - 1 | 0));
  };
  function EncodedColors$colors$lambda(closure$colorsAsString) {
    return function () {
      var $receiver = closure$colorsAsString;
      var $receiver_0 = new IntRange(0, ($receiver.length / 6 | 0) - 1 | 0);
      var destination = ArrayList_init(collectionSizeOrDefault($receiver_0, 10));
      var tmp$;
      tmp$ = $receiver_0.iterator();
      while (tmp$.hasNext()) {
        var item = tmp$.next();
        var tmp$_0 = destination.add_11rb$;
        var startIndex = 6 * item | 0;
        var endIndex = (6 * item | 0) + 6 | 0;
        tmp$_0.call(destination, get_col_0('#' + $receiver.substring(startIndex, endIndex)));
      }
      return destination;
    };
  }
  EncodedColors.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'EncodedColors',
    interfaces: []
  };
  function HclColor(h, c, lightness, a) {
    if (a === void 0)
      a = get_pct(100);
    this.h = h;
    this.c = c;
    this.l = lightness;
    this.alpha_ki6hq2$_0 = a.coerceToDefault();
    this.rgb_3glb5j$_0 = this.toRgb().rgb;
    this.rgba_yrvh7o$_0 = this.toRgb().rgba;
    this.r_s0lt82$_0 = this.toRgb().r;
    this.g_s0lsyl$_0 = this.toRgb().g;
    this.b_s0lsua$_0 = this.toRgb().b;
    this.rgbHex_xkrp4u$_0 = this.toRgb().rgbHex;
  }
  Object.defineProperty(HclColor.prototype, 'alpha', {
    get: function () {
      return this.alpha_ki6hq2$_0;
    }
  });
  Object.defineProperty(HclColor.prototype, 'rgb', {
    get: function () {
      return this.rgb_3glb5j$_0;
    }
  });
  Object.defineProperty(HclColor.prototype, 'rgba', {
    get: function () {
      return this.rgba_yrvh7o$_0;
    }
  });
  Object.defineProperty(HclColor.prototype, 'r', {
    get: function () {
      return this.r_s0lt82$_0;
    }
  });
  Object.defineProperty(HclColor.prototype, 'g', {
    get: function () {
      return this.g_s0lsyl$_0;
    }
  });
  Object.defineProperty(HclColor.prototype, 'b', {
    get: function () {
      return this.b_s0lsua$_0;
    }
  });
  Object.defineProperty(HclColor.prototype, 'rgbHex', {
    get: function () {
      return this.rgbHex_xkrp4u$_0;
    }
  });
  HclColor.prototype.luminance = function () {
    return this.toRgb().luminance();
  };
  HclColor.prototype.contrast_eckgxd$ = function (other) {
    return this.toRgb().contrast_eckgxd$(other);
  };
  HclColor.prototype.toRgb = function () {
    return this.toLab().toRgb();
  };
  HclColor.prototype.toLab = function () {
    return toLaba_0(this);
  };
  HclColor.prototype.toHcl = function () {
    return this;
  };
  HclColor.prototype.toHsl = function () {
    return this.toLab().toHsl();
  };
  HclColor.prototype.brighten_14dthe$$default = function (strength) {
    return Colors_getInstance().hcl_vn5x52$(this.h, this.c, this.l.plus_o5f5ne$(get_pct(Kn * strength)), this.alpha);
  };
  HclColor.prototype.darken_14dthe$$default = function (strength) {
    return Colors_getInstance().hcl_vn5x52$(this.h, this.c, this.l.minus_o5f5ne$(get_pct(Kn * strength)), this.alpha);
  };
  HclColor.prototype.saturate_14dthe$$default = function (strength) {
    var tmp$ = Colors_getInstance();
    var tmp$_0 = this.h;
    var b = this.c + Kn * strength;
    return tmp$.hcl_vn5x52$(tmp$_0, Math_0.max(0.0, b), this.l, this.alpha);
  };
  HclColor.prototype.desaturate_14dthe$$default = function (strength) {
    var tmp$ = Colors_getInstance();
    var tmp$_0 = this.h;
    var b = this.c - Kn * strength;
    return tmp$.hcl_vn5x52$(tmp$_0, Math_0.max(0.0, b), this.l, this.alpha);
  };
  HclColor.prototype.withAlpha_o5f5ne$ = function (alpha) {
    return Colors_getInstance().hcl_vn5x52$(this.h, this.c, this.l, alpha);
  };
  HclColor.prototype.withHue_5t6zck$ = function (hue) {
    return Colors_getInstance().hcl_vn5x52$(hue, this.c, this.l, this.alpha);
  };
  HclColor.prototype.isAchromatic = function () {
    return this.c === 0.0 || this.l.value <= 0.0 || this.l.value >= 1.0;
  };
  HclColor.prototype.equals = function (other) {
    var tmp$;
    if (this === other)
      return true;
    if (other == null || !Kotlin.isType(other, Color))
      return false;
    if (this.rgb !== other.rgb)
      return false;
    if (!((tmp$ = this.alpha) != null ? tmp$.equals(other.alpha) : null))
      return false;
    return true;
  };
  HclColor.prototype.hashCode = function () {
    var result = this.rgb;
    result = (31 * result | 0) + this.alpha.hashCode() | 0;
    return result;
  };
  HclColor.prototype.toString = function () {
    return 'HCL(' + this.h.deg + '\xB0, ' + this.c + ', ' + this.l + '%, alpha=' + this.alpha + ')';
  };
  HclColor.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'HclColor',
    interfaces: [Color]
  };
  function HslColor(hue, saturation, lightness, a) {
    if (a === void 0)
      a = get_pct(100);
    this.h = hue.normalize();
    this.s = saturation.coerceToDefault();
    this.l = lightness.coerceToDefault();
    this.alpha_c7wea2$_0 = a.coerceToDefault();
    this.rgb_xevp09$_0 = this.toRgb().rgb;
    this.rgba_tnebe4$_0 = this.toRgb().rgba;
    this.r_nzdnfi$_0 = this.toRgb().r;
    this.g_nzdnoz$_0 = this.toRgb().g;
    this.b_nzdnta$_0 = this.toRgb().b;
    this.rgbHex_a6l3pu$_0 = this.toRgb().rgbHex;
  }
  Object.defineProperty(HslColor.prototype, 'alpha', {
    get: function () {
      return this.alpha_c7wea2$_0;
    }
  });
  Object.defineProperty(HslColor.prototype, 'rgb', {
    get: function () {
      return this.rgb_xevp09$_0;
    }
  });
  Object.defineProperty(HslColor.prototype, 'rgba', {
    get: function () {
      return this.rgba_tnebe4$_0;
    }
  });
  Object.defineProperty(HslColor.prototype, 'r', {
    get: function () {
      return this.r_nzdnfi$_0;
    }
  });
  Object.defineProperty(HslColor.prototype, 'g', {
    get: function () {
      return this.g_nzdnoz$_0;
    }
  });
  Object.defineProperty(HslColor.prototype, 'b', {
    get: function () {
      return this.b_nzdnta$_0;
    }
  });
  Object.defineProperty(HslColor.prototype, 'rgbHex', {
    get: function () {
      return this.rgbHex_a6l3pu$_0;
    }
  });
  HslColor.prototype.luminance = function () {
    return this.toRgb().luminance();
  };
  HslColor.prototype.contrast_eckgxd$ = function (other) {
    return this.toRgb().contrast_eckgxd$(other);
  };
  HslColor.prototype.toRgb = function () {
    return toRgba_0(this);
  };
  HslColor.prototype.toLab = function () {
    return this.toRgb().toLab();
  };
  HslColor.prototype.toHcl = function () {
    return this.toRgb().toHcl();
  };
  HslColor.prototype.toHsl = function () {
    return this;
  };
  HslColor.prototype.brighten_14dthe$$default = function (strength) {
    return this.toRgb().brighten_14dthe$(strength);
  };
  HslColor.prototype.darken_14dthe$$default = function (strength) {
    return this.toRgb().darken_14dthe$(strength);
  };
  HslColor.prototype.saturate_14dthe$$default = function (strength) {
    return this.toRgb().saturate_14dthe$(strength);
  };
  HslColor.prototype.desaturate_14dthe$$default = function (strength) {
    return this.toRgb().desaturate_14dthe$(strength);
  };
  HslColor.prototype.withAlpha_o5f5ne$ = function (alpha) {
    return Colors_getInstance().hsl_wqq93y$(this.h, this.s, this.l, alpha);
  };
  HslColor.prototype.withHue_5t6zck$ = function (hue) {
    return this.toHcl().withHue_5t6zck$(hue);
  };
  HslColor.prototype.isAchromatic = function () {
    return this.s.value === 0.0 || this.l.value <= 0.0 || this.l.value >= 1.0;
  };
  HslColor.prototype.equals = function (other) {
    var tmp$;
    if (this === other)
      return true;
    if (other == null || !Kotlin.isType(other, Color))
      return false;
    if (this.rgb !== other.rgb)
      return false;
    if (!((tmp$ = this.alpha) != null ? tmp$.equals(other.alpha) : null))
      return false;
    return true;
  };
  HslColor.prototype.hashCode = function () {
    var result = this.rgb;
    result = (31 * result | 0) + this.alpha.hashCode() | 0;
    return result;
  };
  HslColor.prototype.toString = function () {
    return 'HSL(' + this.h.deg + '\xB0, ' + this.s + ', ' + this.l + ', alpha=' + this.alpha + ')';
  };
  HslColor.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'HslColor',
    interfaces: [Color]
  };
  function LabColor(lightness, aComponent, bComponent, a) {
    if (a === void 0)
      a = get_pct(100);
    this.labL = lightness;
    this.labA = aComponent;
    this.labB = bComponent;
    this.alpha_2env5u$_0 = a.coerceToDefault();
    this.rgb_75ptvx$_0 = this.toRgb().rgb;
    this.rgba_8twjkw$_0 = this.toRgb().rgba;
    this.r_1gy6lq$_0 = this.toRgb().r;
    this.g_1gy6c9$_0 = this.toRgb().g;
    this.b_1gy67y$_0 = this.toRgb().b;
    this.rgbHex_xlr5fe$_0 = this.toRgb().rgbHex;
  }
  Object.defineProperty(LabColor.prototype, 'alpha', {
    get: function () {
      return this.alpha_2env5u$_0;
    }
  });
  Object.defineProperty(LabColor.prototype, 'rgb', {
    get: function () {
      return this.rgb_75ptvx$_0;
    }
  });
  Object.defineProperty(LabColor.prototype, 'rgba', {
    get: function () {
      return this.rgba_8twjkw$_0;
    }
  });
  Object.defineProperty(LabColor.prototype, 'r', {
    get: function () {
      return this.r_1gy6lq$_0;
    }
  });
  Object.defineProperty(LabColor.prototype, 'g', {
    get: function () {
      return this.g_1gy6c9$_0;
    }
  });
  Object.defineProperty(LabColor.prototype, 'b', {
    get: function () {
      return this.b_1gy67y$_0;
    }
  });
  Object.defineProperty(LabColor.prototype, 'rgbHex', {
    get: function () {
      return this.rgbHex_xlr5fe$_0;
    }
  });
  LabColor.prototype.luminance = function () {
    return this.toRgb().luminance();
  };
  LabColor.prototype.contrast_eckgxd$ = function (other) {
    return this.toRgb().contrast_eckgxd$(other);
  };
  LabColor.prototype.toRgb = function () {
    return toRgba(this);
  };
  LabColor.prototype.toLab = function () {
    return this;
  };
  LabColor.prototype.toHcl = function () {
    return toHcla(this);
  };
  LabColor.prototype.toHsl = function () {
    return this.toRgb().toHsl();
  };
  LabColor.prototype.brighten_14dthe$$default = function (strength) {
    return Colors_getInstance().lab_tuy7uw$(this.labL.plus_o5f5ne$(get_pct(Kn * strength)), this.labA, this.labB, this.alpha);
  };
  LabColor.prototype.darken_14dthe$$default = function (strength) {
    return Colors_getInstance().lab_tuy7uw$(this.labL.minus_o5f5ne$(get_pct(Kn * strength)), this.labA, this.labB, this.alpha);
  };
  LabColor.prototype.saturate_14dthe$$default = function (strength) {
    return this.toHcl().saturate_14dthe$(strength);
  };
  LabColor.prototype.desaturate_14dthe$$default = function (strength) {
    return this.toHcl().desaturate_14dthe$(strength);
  };
  LabColor.prototype.withAlpha_o5f5ne$ = function (alpha) {
    return Colors_getInstance().lab_tuy7uw$(this.labL, this.labA, this.labB, alpha);
  };
  LabColor.prototype.withHue_5t6zck$ = function (hue) {
    return this.toHcl().withHue_5t6zck$(hue);
  };
  LabColor.prototype.equals = function (other) {
    var tmp$;
    if (this === other)
      return true;
    if (other == null || !Kotlin.isType(other, Color))
      return false;
    if (this.rgb !== other.rgb)
      return false;
    if (!((tmp$ = this.alpha) != null ? tmp$.equals(other.alpha) : null))
      return false;
    return true;
  };
  LabColor.prototype.hashCode = function () {
    var result = this.rgb;
    result = (31 * result | 0) + this.alpha.hashCode() | 0;
    return result;
  };
  LabColor.prototype.toString = function () {
    return 'LAB(' + this.labL + '%, ' + this.labA + ', ' + this.labB + ', alpha=' + this.alpha + ')';
  };
  LabColor.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'LabColor',
    interfaces: [Color]
  };
  function HasStartAndEnd() {
  }
  Object.defineProperty(HasStartAndEnd.prototype, 'start', {
    get: function () {
      return new Point(this.x1, this.y1);
    },
    set: function (value) {
      this.x1 = value.x;
      this.y1 = value.y;
    }
  });
  Object.defineProperty(HasStartAndEnd.prototype, 'end', {
    get: function () {
      return new Point(this.x2, this.y2);
    },
    set: function (value) {
      this.x2 = value.x;
      this.y2 = value.y;
    }
  });
  HasStartAndEnd.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'HasStartAndEnd',
    interfaces: []
  };
  function LinearGradientFirstColorBuilder(start, end) {
    this.start = start;
    this.end = end;
  }
  LinearGradientFirstColorBuilder.prototype.withColor_51jtfj$ = function (startColor, percent) {
    if (percent === void 0)
      percent = get_pct(0);
    return new LinearGradientSecondColorBuilder(this, new ColorStop(percent, startColor));
  };
  LinearGradientFirstColorBuilder.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'LinearGradientFirstColorBuilder',
    interfaces: []
  };
  LinearGradientFirstColorBuilder.prototype.component1 = function () {
    return this.start;
  };
  LinearGradientFirstColorBuilder.prototype.component2 = function () {
    return this.end;
  };
  LinearGradientFirstColorBuilder.prototype.copy_840z2k$ = function (start, end) {
    return new LinearGradientFirstColorBuilder(start === void 0 ? this.start : start, end === void 0 ? this.end : end);
  };
  LinearGradientFirstColorBuilder.prototype.toString = function () {
    return 'LinearGradientFirstColorBuilder(start=' + Kotlin.toString(this.start) + (', end=' + Kotlin.toString(this.end)) + ')';
  };
  LinearGradientFirstColorBuilder.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.start) | 0;
    result = result * 31 + Kotlin.hashCode(this.end) | 0;
    return result;
  };
  LinearGradientFirstColorBuilder.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.start, other.start) && Kotlin.equals(this.end, other.end)))));
  };
  function LinearGradientSecondColorBuilder(builder, firstColor) {
    this.builder = builder;
    this.firstColor = firstColor;
  }
  LinearGradientSecondColorBuilder.prototype.andColor_51jtfj$ = function (color, percent) {
    if (percent === void 0)
      percent = get_pct(100);
    var $receiver = new LinearGradient();
    $receiver.x1 = this.builder.start.x;
    $receiver.y1 = this.builder.start.y;
    $receiver.x2 = this.builder.end.x;
    $receiver.y2 = this.builder.end.y;
    $receiver.andColor_51jtfj$(this.firstColor.color, this.firstColor.percent);
    $receiver.andColor_51jtfj$(color, percent);
    return $receiver;
  };
  LinearGradientSecondColorBuilder.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'LinearGradientSecondColorBuilder',
    interfaces: []
  };
  LinearGradientSecondColorBuilder.prototype.component1 = function () {
    return this.builder;
  };
  LinearGradientSecondColorBuilder.prototype.component2 = function () {
    return this.firstColor;
  };
  LinearGradientSecondColorBuilder.prototype.copy_posc8$ = function (builder, firstColor) {
    return new LinearGradientSecondColorBuilder(builder === void 0 ? this.builder : builder, firstColor === void 0 ? this.firstColor : firstColor);
  };
  LinearGradientSecondColorBuilder.prototype.toString = function () {
    return 'LinearGradientSecondColorBuilder(builder=' + Kotlin.toString(this.builder) + (', firstColor=' + Kotlin.toString(this.firstColor)) + ')';
  };
  LinearGradientSecondColorBuilder.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.builder) | 0;
    result = result * 31 + Kotlin.hashCode(this.firstColor) | 0;
    return result;
  };
  LinearGradientSecondColorBuilder.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.builder, other.builder) && Kotlin.equals(this.firstColor, other.firstColor)))));
  };
  function LinearGradient() {
    this.x1_nnxpt2$_0 = 0.0;
    this.y1_nnxp2d$_0 = 0.0;
    this.x2_nnxps7$_0 = 0.0;
    this.y2_nnxp1i$_0 = 0.0;
    this.colors_0 = ArrayList_init_0();
  }
  Object.defineProperty(LinearGradient.prototype, 'x1', {
    get: function () {
      return this.x1_nnxpt2$_0;
    },
    set: function (x1) {
      this.x1_nnxpt2$_0 = x1;
    }
  });
  Object.defineProperty(LinearGradient.prototype, 'y1', {
    get: function () {
      return this.y1_nnxp2d$_0;
    },
    set: function (y1) {
      this.y1_nnxp2d$_0 = y1;
    }
  });
  Object.defineProperty(LinearGradient.prototype, 'x2', {
    get: function () {
      return this.x2_nnxps7$_0;
    },
    set: function (x2) {
      this.x2_nnxps7$_0 = x2;
    }
  });
  Object.defineProperty(LinearGradient.prototype, 'y2', {
    get: function () {
      return this.y2_nnxp1i$_0;
    },
    set: function (y2) {
      this.y2_nnxp1i$_0 = y2;
    }
  });
  Object.defineProperty(LinearGradient.prototype, 'colorStops', {
    get: function () {
      return toList(this.colors_0);
    }
  });
  LinearGradient.prototype.andColor_51jtfj$ = function (color, percent) {
    this.colors_0.add_11rb$(new ColorStop(percent.coerceToDefault(), color));
    return this;
  };
  LinearGradient.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'LinearGradient',
    interfaces: [HasStartAndEnd, Gradient]
  };
  function HasCenter() {
  }
  Object.defineProperty(HasCenter.prototype, 'center', {
    get: function () {
      return new Point(this.cx, this.cy);
    },
    set: function (value) {
      this.cx = value.x;
      this.cy = value.y;
    }
  });
  HasCenter.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'HasCenter',
    interfaces: []
  };
  function RadialGradientFirstColorBuilder(center, radius) {
    this.center = center;
    this.radius = radius;
  }
  RadialGradientFirstColorBuilder.prototype.withColor_51jtfj$ = function (startColor, percent) {
    if (percent === void 0)
      percent = get_pct(0);
    return new RadialGradientSecondColorBuilder(this, new ColorStop(percent, startColor));
  };
  RadialGradientFirstColorBuilder.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'RadialGradientFirstColorBuilder',
    interfaces: []
  };
  RadialGradientFirstColorBuilder.prototype.component1 = function () {
    return this.center;
  };
  RadialGradientFirstColorBuilder.prototype.component2 = function () {
    return this.radius;
  };
  RadialGradientFirstColorBuilder.prototype.copy_25aop5$ = function (center, radius) {
    return new RadialGradientFirstColorBuilder(center === void 0 ? this.center : center, radius === void 0 ? this.radius : radius);
  };
  RadialGradientFirstColorBuilder.prototype.toString = function () {
    return 'RadialGradientFirstColorBuilder(center=' + Kotlin.toString(this.center) + (', radius=' + Kotlin.toString(this.radius)) + ')';
  };
  RadialGradientFirstColorBuilder.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.center) | 0;
    result = result * 31 + Kotlin.hashCode(this.radius) | 0;
    return result;
  };
  RadialGradientFirstColorBuilder.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.center, other.center) && Kotlin.equals(this.radius, other.radius)))));
  };
  function RadialGradientSecondColorBuilder(builder, firstColor) {
    this.builder = builder;
    this.firstColor = firstColor;
  }
  RadialGradientSecondColorBuilder.prototype.andColor_51jtfj$ = function (color, percent) {
    if (percent === void 0)
      percent = get_pct(100);
    var $receiver = new RadialGradient();
    $receiver.cx = this.builder.center.x;
    $receiver.cy = this.builder.center.y;
    $receiver.radius = this.builder.radius;
    $receiver.andColor_51jtfj$(this.firstColor.color, this.firstColor.percent);
    $receiver.andColor_51jtfj$(color, percent);
    return $receiver;
  };
  RadialGradientSecondColorBuilder.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'RadialGradientSecondColorBuilder',
    interfaces: []
  };
  RadialGradientSecondColorBuilder.prototype.component1 = function () {
    return this.builder;
  };
  RadialGradientSecondColorBuilder.prototype.component2 = function () {
    return this.firstColor;
  };
  RadialGradientSecondColorBuilder.prototype.copy_5wqo8i$ = function (builder, firstColor) {
    return new RadialGradientSecondColorBuilder(builder === void 0 ? this.builder : builder, firstColor === void 0 ? this.firstColor : firstColor);
  };
  RadialGradientSecondColorBuilder.prototype.toString = function () {
    return 'RadialGradientSecondColorBuilder(builder=' + Kotlin.toString(this.builder) + (', firstColor=' + Kotlin.toString(this.firstColor)) + ')';
  };
  RadialGradientSecondColorBuilder.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.builder) | 0;
    result = result * 31 + Kotlin.hashCode(this.firstColor) | 0;
    return result;
  };
  RadialGradientSecondColorBuilder.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.builder, other.builder) && Kotlin.equals(this.firstColor, other.firstColor)))));
  };
  function RadialGradient() {
    this.cx_hci148$_0 = 0.0;
    this.cy_hci13d$_0 = 0.0;
    this.radius = 0.0;
    this.colors_0 = ArrayList_init_0();
  }
  Object.defineProperty(RadialGradient.prototype, 'cx', {
    get: function () {
      return this.cx_hci148$_0;
    },
    set: function (cx) {
      this.cx_hci148$_0 = cx;
    }
  });
  Object.defineProperty(RadialGradient.prototype, 'cy', {
    get: function () {
      return this.cy_hci13d$_0;
    },
    set: function (cy) {
      this.cy_hci13d$_0 = cy;
    }
  });
  Object.defineProperty(RadialGradient.prototype, 'colorStops', {
    get: function () {
      return toList(this.colors_0);
    }
  });
  RadialGradient.prototype.andColor_51jtfj$ = function (color, percent) {
    this.colors_0.add_11rb$(new ColorStop(percent.coerceToDefault(), color));
    return this;
  };
  RadialGradient.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'RadialGradient',
    interfaces: [HasCenter, Gradient]
  };
  function RgbColor(rgb, a) {
    if (a === void 0)
      a = get_pct(100);
    this.rgb_ngpjyl$_0 = rgb;
    this.alpha_uswyiq$_0 = a.coerceToDefault();
  }
  Object.defineProperty(RgbColor.prototype, 'rgb', {
    get: function () {
      return this.rgb_ngpjyl$_0;
    }
  });
  Object.defineProperty(RgbColor.prototype, 'alpha', {
    get: function () {
      return this.alpha_uswyiq$_0;
    }
  });
  Object.defineProperty(RgbColor.prototype, 'r', {
    get: function () {
      return this.rgb >> 16 & 255;
    }
  });
  Object.defineProperty(RgbColor.prototype, 'g', {
    get: function () {
      return this.rgb >> 8 & 255;
    }
  });
  Object.defineProperty(RgbColor.prototype, 'b', {
    get: function () {
      return this.rgb & 255;
    }
  });
  RgbColor.prototype.luminance = function () {
    return toLuminance(this);
  };
  RgbColor.prototype.contrast_eckgxd$ = function (other) {
    var lumA = this.luminance();
    var lumB = other.luminance();
    return lumA.compareTo_o5f5ne$(lumB) > 0 ? (lumA.value + 0.05) / (lumB.value + 0.05) : (lumB.value + 0.05) / (lumA.value + 0.05);
  };
  RgbColor.prototype.toRgb = function () {
    return this;
  };
  RgbColor.prototype.toLab = function () {
    return toLaba(this);
  };
  RgbColor.prototype.toHcl = function () {
    return this.toLab().toHcl();
  };
  RgbColor.prototype.toHsl = function () {
    return toHsla(this);
  };
  RgbColor.prototype.brighten_14dthe$$default = function (strength) {
    return this.toLab().brighten_14dthe$(strength);
  };
  RgbColor.prototype.darken_14dthe$$default = function (strength) {
    return this.toLab().darken_14dthe$(strength);
  };
  RgbColor.prototype.saturate_14dthe$$default = function (strength) {
    return this.toLab().saturate_14dthe$(strength);
  };
  RgbColor.prototype.desaturate_14dthe$$default = function (strength) {
    return this.toLab().desaturate_14dthe$(strength);
  };
  RgbColor.prototype.withHue_5t6zck$ = function (hue) {
    return this.toHcl().withHue_5t6zck$(hue);
  };
  RgbColor.prototype.withRed_za3lpa$ = function (red) {
    var rgb = (this.rgb & 65535) + (coerceIn(red, 0, 255) << 16) | 0;
    return Colors_getInstance().rgb_g6jfbk$(rgb, this.alpha);
  };
  RgbColor.prototype.withGreen_za3lpa$ = function (green) {
    var rgb = (this.rgb & 16711935) + (coerceIn(green, 0, 255) << 8) | 0;
    return Colors_getInstance().rgb_g6jfbk$(rgb, this.alpha);
  };
  RgbColor.prototype.withBlue_za3lpa$ = function (blue) {
    var rgb = (this.rgb & 16776960) + coerceIn(blue, 0, 255) | 0;
    return Colors_getInstance().rgb_g6jfbk$(rgb, this.alpha);
  };
  Object.defineProperty(RgbColor.prototype, 'rgbHex', {
    get: function () {
      return '#' + toString(this.rgb >> 20 & 15, 16) + toString(this.rgb >> 16 & 15, 16) + toString(this.rgb >> 12 & 15, 16) + toString(this.rgb >> 8 & 15, 16) + toString(this.rgb >> 4 & 15, 16) + toString(this.rgb & 15, 16);
    }
  });
  Object.defineProperty(RgbColor.prototype, 'rgba', {
    get: function () {
      return 'rgba(' + this.r + ', ' + this.g + ', ' + this.b + ', ' + this.alpha.value + ')';
    }
  });
  RgbColor.prototype.withAlpha_o5f5ne$ = function (alpha) {
    return Colors_getInstance().rgb_g6jfbk$(this.rgb, alpha);
  };
  RgbColor.prototype.equals = function (other) {
    var tmp$;
    if (this === other)
      return true;
    if (other == null || !Kotlin.isType(other, Color))
      return false;
    if (this.rgb !== other.rgb)
      return false;
    if (!((tmp$ = this.alpha) != null ? tmp$.equals(other.alpha) : null))
      return false;
    return true;
  };
  RgbColor.prototype.hashCode = function () {
    var result = this.rgb;
    result = (31 * result | 0) + this.alpha.hashCode() | 0;
    return result;
  };
  RgbColor.prototype.toString = function () {
    return 'RGB(' + this.r + ', ' + this.g + ', ' + this.b + ', alpha=' + this.alpha + ') - ' + this.rgbHex;
  };
  RgbColor.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'RgbColor',
    interfaces: [Color]
  };
  function get_col($receiver) {
    return Colors_getInstance().rgb_g6jfbk$($receiver);
  }
  function get_color($receiver) {
    return get_col($receiver);
  }
  function get_col_0($receiver) {
    var regex = Regex_init('^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$');
    if (!regex.matches_6bul2c$($receiver)) {
      var message = 'Conversion of string to io.data2viz.col.RgbColor works for encoded colors like #12abCD';
      throw IllegalArgumentException_init(message.toString());
    }
    return Colors_getInstance().rgb_g6jfbk$(toInt($receiver.substring(1), 16));
  }
  function get_color_0($receiver) {
    return get_col_0($receiver);
  }
  function toString($receiver, radix) {
    return $receiver.toString(radix);
  }
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$color = package$data2viz.color || (package$data2viz.color = {});
  package$color.ColorStop = ColorStop;
  package$color.ColorOrGradient = ColorOrGradient;
  package$color.Gradient = Gradient;
  package$color.Color = Color;
  Object.defineProperty(package$color, 'Kn_8be2vx$', {
    get: function () {
      return Kn;
    }
  });
  Object.defineProperty(package$color, 'Xn_8be2vx$', {
    get: function () {
      return Xn;
    }
  });
  Object.defineProperty(package$color, 'Yn_8be2vx$', {
    get: function () {
      return Yn;
    }
  });
  Object.defineProperty(package$color, 'Zn_8be2vx$', {
    get: function () {
      return Zn;
    }
  });
  Object.defineProperty(package$color, 't0_8be2vx$', {
    get: function () {
      return t0;
    }
  });
  Object.defineProperty(package$color, 't1_8be2vx$', {
    get: function () {
      return t1;
    }
  });
  Object.defineProperty(package$color, 't2_8be2vx$', {
    get: function () {
      return t2;
    }
  });
  Object.defineProperty(package$color, 't3_8be2vx$', {
    get: function () {
      return t3;
    }
  });
  Object.defineProperty(package$color, 'deg60toRad_8be2vx$', {
    get: function () {
      return deg60toRad;
    }
  });
  Object.defineProperty(package$color, 'deg240toRad_8be2vx$', {
    get: function () {
      return deg240toRad;
    }
  });
  Object.defineProperty(package$color, 'angle120deg_8be2vx$', {
    get: function () {
      return angle120deg;
    }
  });
  package$color.toLaba_5ezr86$ = toLaba;
  package$color.toHsla_5ezr86$ = toHsla;
  package$color.toRgba_zxj56$ = toRgba;
  package$color.toRgba_4tcdwe$ = toRgba_0;
  package$color.toLaba_u5yv82$ = toLaba_0;
  package$color.toHcla_zxj56$ = toHcla;
  package$color.toLuminance_5ezr86$ = toLuminance;
  Object.defineProperty(Colors.prototype, 'Gradient', {
    get: Colors$Gradient_getInstance
  });
  Object.defineProperty(Colors.prototype, 'Web', {
    get: Colors$Web_getInstance
  });
  Object.defineProperty(package$color, 'Colors', {
    get: Colors_getInstance
  });
  Object.defineProperty(EncodedColors, 'Companion', {
    get: EncodedColors$Companion_getInstance
  });
  package$color.EncodedColors = EncodedColors;
  package$color.HclColor = HclColor;
  package$color.HslColor = HslColor;
  package$color.LabColor = LabColor;
  package$color.HasStartAndEnd = HasStartAndEnd;
  package$color.LinearGradientFirstColorBuilder = LinearGradientFirstColorBuilder;
  package$color.LinearGradientSecondColorBuilder = LinearGradientSecondColorBuilder;
  package$color.LinearGradient = LinearGradient;
  package$color.HasCenter = HasCenter;
  package$color.RadialGradientFirstColorBuilder = RadialGradientFirstColorBuilder;
  package$color.RadialGradientSecondColorBuilder = RadialGradientSecondColorBuilder;
  package$color.RadialGradient = RadialGradient;
  package$color.RgbColor = RgbColor;
  package$color.get_col_s8ev3n$ = get_col;
  package$color.get_color_s8ev3n$ = get_color;
  package$color.get_col_pdl1vz$ = get_col_0;
  package$color.get_color_pdl1vz$ = get_color_0;
  package$color.toString_b6l1hq$ = toString;
  HclColor.prototype.brighten_14dthe$ = Color.prototype.brighten_14dthe$;
  HclColor.prototype.darken_14dthe$ = Color.prototype.darken_14dthe$;
  HclColor.prototype.saturate_14dthe$ = Color.prototype.saturate_14dthe$;
  HclColor.prototype.desaturate_14dthe$ = Color.prototype.desaturate_14dthe$;
  HslColor.prototype.brighten_14dthe$ = Color.prototype.brighten_14dthe$;
  HslColor.prototype.darken_14dthe$ = Color.prototype.darken_14dthe$;
  HslColor.prototype.saturate_14dthe$ = Color.prototype.saturate_14dthe$;
  HslColor.prototype.desaturate_14dthe$ = Color.prototype.desaturate_14dthe$;
  LabColor.prototype.brighten_14dthe$ = Color.prototype.brighten_14dthe$;
  LabColor.prototype.darken_14dthe$ = Color.prototype.darken_14dthe$;
  LabColor.prototype.saturate_14dthe$ = Color.prototype.saturate_14dthe$;
  LabColor.prototype.desaturate_14dthe$ = Color.prototype.desaturate_14dthe$;
  Object.defineProperty(LinearGradient.prototype, 'start', Object.getOwnPropertyDescriptor(HasStartAndEnd.prototype, 'start'));
  Object.defineProperty(LinearGradient.prototype, 'end', Object.getOwnPropertyDescriptor(HasStartAndEnd.prototype, 'end'));
  Object.defineProperty(RadialGradient.prototype, 'center', Object.getOwnPropertyDescriptor(HasCenter.prototype, 'center'));
  RgbColor.prototype.brighten_14dthe$ = Color.prototype.brighten_14dthe$;
  RgbColor.prototype.darken_14dthe$ = Color.prototype.darken_14dthe$;
  RgbColor.prototype.saturate_14dthe$ = Color.prototype.saturate_14dthe$;
  RgbColor.prototype.desaturate_14dthe$ = Color.prototype.desaturate_14dthe$;
  Kn = 18.0;
  Xn = 0.95047;
  Yn = 1.0;
  Zn = 1.08883;
  t0 = 4.0 / 29.0;
  t1 = 6.0 / 29.0;
  t2 = 3.0 * t1 * t1;
  t3 = t1 * t1 * t1;
  deg60toRad = 1.047198;
  deg240toRad = 4.18879;
  angle120deg = get_deg(120);
  Kotlin.defineModule('d2v-color-js', _);
  return _;
}));

//# sourceMappingURL=d2v-color-js.js.map
