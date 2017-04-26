define('color', ['exports', 'kotlin', 'tests', 'core'], function (_, Kotlin, $module$tests, $module$core) {
  'use strict';
  var toInt = Kotlin.kotlin.text.toInt_6ic1pp$;
  var lazy = Kotlin.kotlin.lazy_klfg04$;
  var StringSpec = $module$tests.io.data2viz.test.StringSpec;
  var coerceAtLeast = Kotlin.kotlin.ranges.coerceAtLeast_dqglrj$;
  var coerceAtMost = Kotlin.kotlin.ranges.coerceAtMost_dqglrj$;
  var IntRange = Kotlin.kotlin.ranges.IntRange;
  var namespace = $module$core.io.data2viz.core.namespace;
  ColorTests.prototype = Object.create(StringSpec.prototype);
  ColorTests.prototype.constructor = ColorTests;
  ViridisTests.prototype = Object.create(StringSpec.prototype);
  ViridisTests.prototype.constructor = ViridisTests;
  function get_color($receiver) {
    var $receiver_0 = '^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$';
    var regex = Kotlin.kotlin.text.Regex_61zpoe$($receiver_0);
    if (!regex.matches_6bul2c$($receiver)) {
      var message = 'Conversion of string to io.data2viz.color.getColor works for encoded colors like #12abCD';
      throw new Kotlin.kotlin.IllegalStateException(message.toString());
    }
    return new Color(toInt($receiver.substring(1), 16));
  }
  function rgba(r, g, b, a) {
    var $receiver = new Color();
    $receiver.rgba_1ugm5o$(r, g, b, a);
    return $receiver;
  }
  function Color(rgb, _alpha) {
    if (rgb === void 0)
      rgb = 16777215;
    if (_alpha === void 0)
      _alpha = 1.0;
    this.rgb = rgb;
    this._alpha = _alpha;
  }
  Object.defineProperty(Color.prototype, 'r', {
    get: function () {
      return this.rgb >> 16 & 255;
    },
    set: function (value) {
      if (!(value < 256)) {
        var message = 'r should be less or equal to 255';
        throw new Kotlin.kotlin.IllegalArgumentException(message.toString());
      }
      if (!(value > -1)) {
        var message_0 = 'r should be greater or equal to 0';
        throw new Kotlin.kotlin.IllegalArgumentException(message_0.toString());
      }
      this.rgb = (this.rgb & 65535) + (value << 16) | 0;
    }
  });
  Object.defineProperty(Color.prototype, 'g', {
    get: function () {
      return this.rgb >> 8 & 255;
    },
    set: function (value) {
      if (!(value < 256)) {
        var message = 'g should be less or equal to 255';
        throw new Kotlin.kotlin.IllegalArgumentException(message.toString());
      }
      if (!(value > -1)) {
        var message_0 = 'g should be greater or equal to 0';
        throw new Kotlin.kotlin.IllegalArgumentException(message_0.toString());
      }
      this.rgb = (this.rgb & 16711935) + (value << 8) | 0;
    }
  });
  Object.defineProperty(Color.prototype, 'b', {
    get: function () {
      return this.rgb & 255;
    },
    set: function (value) {
      if (!(value < 256)) {
        var message = 'b should be less or equal to 255';
        throw new Kotlin.kotlin.IllegalArgumentException(message.toString());
      }
      if (!(value > -1)) {
        var message_0 = 'b should be greater or equal to 0';
        throw new Kotlin.kotlin.IllegalArgumentException(message_0.toString());
      }
      this.rgb = (this.rgb & 16776960) + value | 0;
    }
  });
  Object.defineProperty(Color.prototype, 'alpha', {
    get: function () {
      return this._alpha;
    },
    set: function (value) {
      this._alpha = Kotlin.numberToDouble(value);
    }
  });
  Color.prototype.rgba_1ugm5o$ = function (r, g, b, a) {
    this.r = Kotlin.numberToInt(r);
    this.g = Kotlin.numberToInt(g);
    this.b = Kotlin.numberToInt(b);
    this.alpha = Kotlin.numberToDouble(a);
  };
  Object.defineProperty(Color.prototype, 'rgbHex', {
    get: function () {
      return '#' + this.toString_dqglrj$(this.rgb >> 20 & 15, 16) + this.toString_dqglrj$(this.rgb >> 16 & 15, 16) + this.toString_dqglrj$(this.rgb >> 12 & 15, 16) + this.toString_dqglrj$(this.rgb >> 8 & 15, 16) + this.toString_dqglrj$(this.rgb >> 4 & 15, 16) + this.toString_dqglrj$(this.rgb & 15, 16);
    }
  });
  Color.prototype.toString_dqglrj$ = function ($receiver, radix) {
    return $receiver.toString(radix);
  };
  Color.prototype.toString = function () {
    return Kotlin.numberToDouble(this.alpha) < 1.0 ? 'rgba(' + this.r + ',' + this.g + ',' + this.b + ',' + this.alpha + ')' : this.rgbHex;
  };
  Color.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'Color',
    interfaces: []
  };
  function colors() {
    colors_instance = this;
    this.aliceblue$delegate = lazy(colors$aliceblue$lambda(this));
    this.antiquewhite$delegate = lazy(colors$antiquewhite$lambda(this));
    this.aqua$delegate = lazy(colors$aqua$lambda(this));
    this.aquamarine$delegate = lazy(colors$aquamarine$lambda(this));
    this.azure$delegate = lazy(colors$azure$lambda(this));
    this.beige$delegate = lazy(colors$beige$lambda(this));
    this.bisque$delegate = lazy(colors$bisque$lambda(this));
    this.black$delegate = lazy(colors$black$lambda(this));
    this.blanchedalmond$delegate = lazy(colors$blanchedalmond$lambda(this));
    this.blue$delegate = lazy(colors$blue$lambda(this));
    this.blueviolet$delegate = lazy(colors$blueviolet$lambda(this));
    this.brown$delegate = lazy(colors$brown$lambda(this));
    this.burlywood$delegate = lazy(colors$burlywood$lambda(this));
    this.cadetblue$delegate = lazy(colors$cadetblue$lambda(this));
    this.chartreuse$delegate = lazy(colors$chartreuse$lambda(this));
    this.chocolate$delegate = lazy(colors$chocolate$lambda(this));
    this.coral$delegate = lazy(colors$coral$lambda(this));
    this.cornflowerblue$delegate = lazy(colors$cornflowerblue$lambda(this));
    this.cornsilk$delegate = lazy(colors$cornsilk$lambda(this));
    this.crimson$delegate = lazy(colors$crimson$lambda(this));
    this.cyan$delegate = lazy(colors$cyan$lambda(this));
    this.darkblue$delegate = lazy(colors$darkblue$lambda(this));
    this.darkcyan$delegate = lazy(colors$darkcyan$lambda(this));
    this.darkgoldenrod$delegate = lazy(colors$darkgoldenrod$lambda(this));
    this.darkgray$delegate = lazy(colors$darkgray$lambda(this));
    this.darkgreen$delegate = lazy(colors$darkgreen$lambda(this));
    this.darkgrey$delegate = lazy(colors$darkgrey$lambda(this));
    this.darkkhaki$delegate = lazy(colors$darkkhaki$lambda(this));
    this.darkmagenta$delegate = lazy(colors$darkmagenta$lambda(this));
    this.darkolivegreen$delegate = lazy(colors$darkolivegreen$lambda(this));
    this.darkorange$delegate = lazy(colors$darkorange$lambda(this));
    this.darkorchid$delegate = lazy(colors$darkorchid$lambda(this));
    this.darkred$delegate = lazy(colors$darkred$lambda(this));
    this.darksalmon$delegate = lazy(colors$darksalmon$lambda(this));
    this.darkseagreen$delegate = lazy(colors$darkseagreen$lambda(this));
    this.darkslateblue$delegate = lazy(colors$darkslateblue$lambda(this));
    this.darkslategray$delegate = lazy(colors$darkslategray$lambda(this));
    this.darkslategrey$delegate = lazy(colors$darkslategrey$lambda(this));
    this.darkturquoise$delegate = lazy(colors$darkturquoise$lambda(this));
    this.darkviolet$delegate = lazy(colors$darkviolet$lambda(this));
    this.deeppink$delegate = lazy(colors$deeppink$lambda(this));
    this.deepskyblue$delegate = lazy(colors$deepskyblue$lambda(this));
    this.dimgray$delegate = lazy(colors$dimgray$lambda(this));
    this.dimgrey$delegate = lazy(colors$dimgrey$lambda(this));
    this.dodgerblue$delegate = lazy(colors$dodgerblue$lambda(this));
    this.firebrick$delegate = lazy(colors$firebrick$lambda(this));
    this.floralwhite$delegate = lazy(colors$floralwhite$lambda(this));
    this.forestgreen$delegate = lazy(colors$forestgreen$lambda(this));
    this.fuchsia$delegate = lazy(colors$fuchsia$lambda(this));
    this.gainsboro$delegate = lazy(colors$gainsboro$lambda(this));
    this.ghostwhite$delegate = lazy(colors$ghostwhite$lambda(this));
    this.gold$delegate = lazy(colors$gold$lambda(this));
    this.goldenrod$delegate = lazy(colors$goldenrod$lambda(this));
    this.gray$delegate = lazy(colors$gray$lambda(this));
    this.green$delegate = lazy(colors$green$lambda(this));
    this.greenyellow$delegate = lazy(colors$greenyellow$lambda(this));
    this.grey$delegate = lazy(colors$grey$lambda(this));
    this.honeydew$delegate = lazy(colors$honeydew$lambda(this));
    this.hotpink$delegate = lazy(colors$hotpink$lambda(this));
    this.indianred$delegate = lazy(colors$indianred$lambda(this));
    this.indigo$delegate = lazy(colors$indigo$lambda(this));
    this.ivory$delegate = lazy(colors$ivory$lambda(this));
    this.khaki$delegate = lazy(colors$khaki$lambda(this));
    this.lavender$delegate = lazy(colors$lavender$lambda(this));
    this.lavenderblush$delegate = lazy(colors$lavenderblush$lambda(this));
    this.lawngreen$delegate = lazy(colors$lawngreen$lambda(this));
    this.lemonchiffon$delegate = lazy(colors$lemonchiffon$lambda(this));
    this.lightblue$delegate = lazy(colors$lightblue$lambda(this));
    this.lightcoral$delegate = lazy(colors$lightcoral$lambda(this));
    this.lightcyan$delegate = lazy(colors$lightcyan$lambda(this));
    this.lightgoldenrodyellow$delegate = lazy(colors$lightgoldenrodyellow$lambda(this));
    this.lightgray$delegate = lazy(colors$lightgray$lambda(this));
    this.lightgreen$delegate = lazy(colors$lightgreen$lambda(this));
    this.lightgrey$delegate = lazy(colors$lightgrey$lambda(this));
    this.lightpink$delegate = lazy(colors$lightpink$lambda(this));
    this.lightsalmon$delegate = lazy(colors$lightsalmon$lambda(this));
    this.lightseagreen$delegate = lazy(colors$lightseagreen$lambda(this));
    this.lightskyblue$delegate = lazy(colors$lightskyblue$lambda(this));
    this.lightslategray$delegate = lazy(colors$lightslategray$lambda(this));
    this.lightslategrey$delegate = lazy(colors$lightslategrey$lambda(this));
    this.lightsteelblue$delegate = lazy(colors$lightsteelblue$lambda(this));
    this.lightyellow$delegate = lazy(colors$lightyellow$lambda(this));
    this.lime$delegate = lazy(colors$lime$lambda(this));
    this.limegreen$delegate = lazy(colors$limegreen$lambda(this));
    this.linen$delegate = lazy(colors$linen$lambda(this));
    this.magenta$delegate = lazy(colors$magenta$lambda(this));
    this.maroon$delegate = lazy(colors$maroon$lambda(this));
    this.mediumaquamarine$delegate = lazy(colors$mediumaquamarine$lambda(this));
    this.mediumblue$delegate = lazy(colors$mediumblue$lambda(this));
    this.mediumorchid$delegate = lazy(colors$mediumorchid$lambda(this));
    this.mediumpurple$delegate = lazy(colors$mediumpurple$lambda(this));
    this.mediumseagreen$delegate = lazy(colors$mediumseagreen$lambda(this));
    this.mediumslateblue$delegate = lazy(colors$mediumslateblue$lambda(this));
    this.mediumspringgreen$delegate = lazy(colors$mediumspringgreen$lambda(this));
    this.mediumturquoise$delegate = lazy(colors$mediumturquoise$lambda(this));
    this.mediumvioletred$delegate = lazy(colors$mediumvioletred$lambda(this));
    this.midnightblue$delegate = lazy(colors$midnightblue$lambda(this));
    this.mintcream$delegate = lazy(colors$mintcream$lambda(this));
    this.mistyrose$delegate = lazy(colors$mistyrose$lambda(this));
    this.moccasin$delegate = lazy(colors$moccasin$lambda(this));
    this.navajowhite$delegate = lazy(colors$navajowhite$lambda(this));
    this.navy$delegate = lazy(colors$navy$lambda(this));
    this.oldlace$delegate = lazy(colors$oldlace$lambda(this));
    this.olive$delegate = lazy(colors$olive$lambda(this));
    this.olivedrab$delegate = lazy(colors$olivedrab$lambda(this));
    this.orange$delegate = lazy(colors$orange$lambda(this));
    this.orangered$delegate = lazy(colors$orangered$lambda(this));
    this.orchid$delegate = lazy(colors$orchid$lambda(this));
    this.palegoldenrod$delegate = lazy(colors$palegoldenrod$lambda(this));
    this.palegreen$delegate = lazy(colors$palegreen$lambda(this));
    this.paleturquoise$delegate = lazy(colors$paleturquoise$lambda(this));
    this.palevioletred$delegate = lazy(colors$palevioletred$lambda(this));
    this.papayawhip$delegate = lazy(colors$papayawhip$lambda(this));
    this.peachpuff$delegate = lazy(colors$peachpuff$lambda(this));
    this.peru$delegate = lazy(colors$peru$lambda(this));
    this.pink$delegate = lazy(colors$pink$lambda(this));
    this.plum$delegate = lazy(colors$plum$lambda(this));
    this.powderblue$delegate = lazy(colors$powderblue$lambda(this));
    this.purple$delegate = lazy(colors$purple$lambda(this));
    this.rebeccapurple$delegate = lazy(colors$rebeccapurple$lambda(this));
    this.red$delegate = lazy(colors$red$lambda(this));
    this.rosybrown$delegate = lazy(colors$rosybrown$lambda(this));
    this.royalblue$delegate = lazy(colors$royalblue$lambda(this));
    this.saddlebrown$delegate = lazy(colors$saddlebrown$lambda(this));
    this.salmon$delegate = lazy(colors$salmon$lambda(this));
    this.sandybrown$delegate = lazy(colors$sandybrown$lambda(this));
    this.seagreen$delegate = lazy(colors$seagreen$lambda(this));
    this.seashell$delegate = lazy(colors$seashell$lambda(this));
    this.sienna$delegate = lazy(colors$sienna$lambda(this));
    this.silver$delegate = lazy(colors$silver$lambda(this));
    this.skyblue$delegate = lazy(colors$skyblue$lambda(this));
    this.slateblue$delegate = lazy(colors$slateblue$lambda(this));
    this.slategray$delegate = lazy(colors$slategray$lambda(this));
    this.slategrey$delegate = lazy(colors$slategrey$lambda(this));
    this.snow$delegate = lazy(colors$snow$lambda(this));
    this.springgreen$delegate = lazy(colors$springgreen$lambda(this));
    this.steelblue$delegate = lazy(colors$steelblue$lambda(this));
    this.tan$delegate = lazy(colors$tan$lambda(this));
    this.teal$delegate = lazy(colors$teal$lambda(this));
    this.thistle$delegate = lazy(colors$thistle$lambda(this));
    this.tomato$delegate = lazy(colors$tomato$lambda(this));
    this.turquoise$delegate = lazy(colors$turquoise$lambda(this));
    this.violet$delegate = lazy(colors$violet$lambda(this));
    this.wheat$delegate = lazy(colors$wheat$lambda(this));
    this.white$delegate = lazy(colors$white$lambda(this));
    this.whitesmoke$delegate = lazy(colors$whitesmoke$lambda(this));
    this.yellow$delegate = lazy(colors$yellow$lambda(this));
    this.yellowgreen$delegate = lazy(colors$yellowgreen$lambda(this));
  }
  colors.prototype.get_col_s8ev3n$ = function ($receiver) {
    return new Color($receiver);
  };
  Object.defineProperty(colors.prototype, 'aliceblue', {
    get: function () {
      var $receiver = this.aliceblue$delegate;
      new Kotlin.PropertyMetadata('aliceblue');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'antiquewhite', {
    get: function () {
      var $receiver = this.antiquewhite$delegate;
      new Kotlin.PropertyMetadata('antiquewhite');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'aqua', {
    get: function () {
      var $receiver = this.aqua$delegate;
      new Kotlin.PropertyMetadata('aqua');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'aquamarine', {
    get: function () {
      var $receiver = this.aquamarine$delegate;
      new Kotlin.PropertyMetadata('aquamarine');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'azure', {
    get: function () {
      var $receiver = this.azure$delegate;
      new Kotlin.PropertyMetadata('azure');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'beige', {
    get: function () {
      var $receiver = this.beige$delegate;
      new Kotlin.PropertyMetadata('beige');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'bisque', {
    get: function () {
      var $receiver = this.bisque$delegate;
      new Kotlin.PropertyMetadata('bisque');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'black', {
    get: function () {
      var $receiver = this.black$delegate;
      new Kotlin.PropertyMetadata('black');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'blanchedalmond', {
    get: function () {
      var $receiver = this.blanchedalmond$delegate;
      new Kotlin.PropertyMetadata('blanchedalmond');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'blue', {
    get: function () {
      var $receiver = this.blue$delegate;
      new Kotlin.PropertyMetadata('blue');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'blueviolet', {
    get: function () {
      var $receiver = this.blueviolet$delegate;
      new Kotlin.PropertyMetadata('blueviolet');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'brown', {
    get: function () {
      var $receiver = this.brown$delegate;
      new Kotlin.PropertyMetadata('brown');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'burlywood', {
    get: function () {
      var $receiver = this.burlywood$delegate;
      new Kotlin.PropertyMetadata('burlywood');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'cadetblue', {
    get: function () {
      var $receiver = this.cadetblue$delegate;
      new Kotlin.PropertyMetadata('cadetblue');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'chartreuse', {
    get: function () {
      var $receiver = this.chartreuse$delegate;
      new Kotlin.PropertyMetadata('chartreuse');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'chocolate', {
    get: function () {
      var $receiver = this.chocolate$delegate;
      new Kotlin.PropertyMetadata('chocolate');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'coral', {
    get: function () {
      var $receiver = this.coral$delegate;
      new Kotlin.PropertyMetadata('coral');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'cornflowerblue', {
    get: function () {
      var $receiver = this.cornflowerblue$delegate;
      new Kotlin.PropertyMetadata('cornflowerblue');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'cornsilk', {
    get: function () {
      var $receiver = this.cornsilk$delegate;
      new Kotlin.PropertyMetadata('cornsilk');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'crimson', {
    get: function () {
      var $receiver = this.crimson$delegate;
      new Kotlin.PropertyMetadata('crimson');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'cyan', {
    get: function () {
      var $receiver = this.cyan$delegate;
      new Kotlin.PropertyMetadata('cyan');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'darkblue', {
    get: function () {
      var $receiver = this.darkblue$delegate;
      new Kotlin.PropertyMetadata('darkblue');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'darkcyan', {
    get: function () {
      var $receiver = this.darkcyan$delegate;
      new Kotlin.PropertyMetadata('darkcyan');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'darkgoldenrod', {
    get: function () {
      var $receiver = this.darkgoldenrod$delegate;
      new Kotlin.PropertyMetadata('darkgoldenrod');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'darkgray', {
    get: function () {
      var $receiver = this.darkgray$delegate;
      new Kotlin.PropertyMetadata('darkgray');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'darkgreen', {
    get: function () {
      var $receiver = this.darkgreen$delegate;
      new Kotlin.PropertyMetadata('darkgreen');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'darkgrey', {
    get: function () {
      var $receiver = this.darkgrey$delegate;
      new Kotlin.PropertyMetadata('darkgrey');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'darkkhaki', {
    get: function () {
      var $receiver = this.darkkhaki$delegate;
      new Kotlin.PropertyMetadata('darkkhaki');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'darkmagenta', {
    get: function () {
      var $receiver = this.darkmagenta$delegate;
      new Kotlin.PropertyMetadata('darkmagenta');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'darkolivegreen', {
    get: function () {
      var $receiver = this.darkolivegreen$delegate;
      new Kotlin.PropertyMetadata('darkolivegreen');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'darkorange', {
    get: function () {
      var $receiver = this.darkorange$delegate;
      new Kotlin.PropertyMetadata('darkorange');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'darkorchid', {
    get: function () {
      var $receiver = this.darkorchid$delegate;
      new Kotlin.PropertyMetadata('darkorchid');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'darkred', {
    get: function () {
      var $receiver = this.darkred$delegate;
      new Kotlin.PropertyMetadata('darkred');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'darksalmon', {
    get: function () {
      var $receiver = this.darksalmon$delegate;
      new Kotlin.PropertyMetadata('darksalmon');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'darkseagreen', {
    get: function () {
      var $receiver = this.darkseagreen$delegate;
      new Kotlin.PropertyMetadata('darkseagreen');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'darkslateblue', {
    get: function () {
      var $receiver = this.darkslateblue$delegate;
      new Kotlin.PropertyMetadata('darkslateblue');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'darkslategray', {
    get: function () {
      var $receiver = this.darkslategray$delegate;
      new Kotlin.PropertyMetadata('darkslategray');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'darkslategrey', {
    get: function () {
      var $receiver = this.darkslategrey$delegate;
      new Kotlin.PropertyMetadata('darkslategrey');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'darkturquoise', {
    get: function () {
      var $receiver = this.darkturquoise$delegate;
      new Kotlin.PropertyMetadata('darkturquoise');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'darkviolet', {
    get: function () {
      var $receiver = this.darkviolet$delegate;
      new Kotlin.PropertyMetadata('darkviolet');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'deeppink', {
    get: function () {
      var $receiver = this.deeppink$delegate;
      new Kotlin.PropertyMetadata('deeppink');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'deepskyblue', {
    get: function () {
      var $receiver = this.deepskyblue$delegate;
      new Kotlin.PropertyMetadata('deepskyblue');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'dimgray', {
    get: function () {
      var $receiver = this.dimgray$delegate;
      new Kotlin.PropertyMetadata('dimgray');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'dimgrey', {
    get: function () {
      var $receiver = this.dimgrey$delegate;
      new Kotlin.PropertyMetadata('dimgrey');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'dodgerblue', {
    get: function () {
      var $receiver = this.dodgerblue$delegate;
      new Kotlin.PropertyMetadata('dodgerblue');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'firebrick', {
    get: function () {
      var $receiver = this.firebrick$delegate;
      new Kotlin.PropertyMetadata('firebrick');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'floralwhite', {
    get: function () {
      var $receiver = this.floralwhite$delegate;
      new Kotlin.PropertyMetadata('floralwhite');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'forestgreen', {
    get: function () {
      var $receiver = this.forestgreen$delegate;
      new Kotlin.PropertyMetadata('forestgreen');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'fuchsia', {
    get: function () {
      var $receiver = this.fuchsia$delegate;
      new Kotlin.PropertyMetadata('fuchsia');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'gainsboro', {
    get: function () {
      var $receiver = this.gainsboro$delegate;
      new Kotlin.PropertyMetadata('gainsboro');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'ghostwhite', {
    get: function () {
      var $receiver = this.ghostwhite$delegate;
      new Kotlin.PropertyMetadata('ghostwhite');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'gold', {
    get: function () {
      var $receiver = this.gold$delegate;
      new Kotlin.PropertyMetadata('gold');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'goldenrod', {
    get: function () {
      var $receiver = this.goldenrod$delegate;
      new Kotlin.PropertyMetadata('goldenrod');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'gray', {
    get: function () {
      var $receiver = this.gray$delegate;
      new Kotlin.PropertyMetadata('gray');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'green', {
    get: function () {
      var $receiver = this.green$delegate;
      new Kotlin.PropertyMetadata('green');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'greenyellow', {
    get: function () {
      var $receiver = this.greenyellow$delegate;
      new Kotlin.PropertyMetadata('greenyellow');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'grey', {
    get: function () {
      var $receiver = this.grey$delegate;
      new Kotlin.PropertyMetadata('grey');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'honeydew', {
    get: function () {
      var $receiver = this.honeydew$delegate;
      new Kotlin.PropertyMetadata('honeydew');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'hotpink', {
    get: function () {
      var $receiver = this.hotpink$delegate;
      new Kotlin.PropertyMetadata('hotpink');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'indianred', {
    get: function () {
      var $receiver = this.indianred$delegate;
      new Kotlin.PropertyMetadata('indianred');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'indigo', {
    get: function () {
      var $receiver = this.indigo$delegate;
      new Kotlin.PropertyMetadata('indigo');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'ivory', {
    get: function () {
      var $receiver = this.ivory$delegate;
      new Kotlin.PropertyMetadata('ivory');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'khaki', {
    get: function () {
      var $receiver = this.khaki$delegate;
      new Kotlin.PropertyMetadata('khaki');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'lavender', {
    get: function () {
      var $receiver = this.lavender$delegate;
      new Kotlin.PropertyMetadata('lavender');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'lavenderblush', {
    get: function () {
      var $receiver = this.lavenderblush$delegate;
      new Kotlin.PropertyMetadata('lavenderblush');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'lawngreen', {
    get: function () {
      var $receiver = this.lawngreen$delegate;
      new Kotlin.PropertyMetadata('lawngreen');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'lemonchiffon', {
    get: function () {
      var $receiver = this.lemonchiffon$delegate;
      new Kotlin.PropertyMetadata('lemonchiffon');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'lightblue', {
    get: function () {
      var $receiver = this.lightblue$delegate;
      new Kotlin.PropertyMetadata('lightblue');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'lightcoral', {
    get: function () {
      var $receiver = this.lightcoral$delegate;
      new Kotlin.PropertyMetadata('lightcoral');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'lightcyan', {
    get: function () {
      var $receiver = this.lightcyan$delegate;
      new Kotlin.PropertyMetadata('lightcyan');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'lightgoldenrodyellow', {
    get: function () {
      var $receiver = this.lightgoldenrodyellow$delegate;
      new Kotlin.PropertyMetadata('lightgoldenrodyellow');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'lightgray', {
    get: function () {
      var $receiver = this.lightgray$delegate;
      new Kotlin.PropertyMetadata('lightgray');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'lightgreen', {
    get: function () {
      var $receiver = this.lightgreen$delegate;
      new Kotlin.PropertyMetadata('lightgreen');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'lightgrey', {
    get: function () {
      var $receiver = this.lightgrey$delegate;
      new Kotlin.PropertyMetadata('lightgrey');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'lightpink', {
    get: function () {
      var $receiver = this.lightpink$delegate;
      new Kotlin.PropertyMetadata('lightpink');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'lightsalmon', {
    get: function () {
      var $receiver = this.lightsalmon$delegate;
      new Kotlin.PropertyMetadata('lightsalmon');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'lightseagreen', {
    get: function () {
      var $receiver = this.lightseagreen$delegate;
      new Kotlin.PropertyMetadata('lightseagreen');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'lightskyblue', {
    get: function () {
      var $receiver = this.lightskyblue$delegate;
      new Kotlin.PropertyMetadata('lightskyblue');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'lightslategray', {
    get: function () {
      var $receiver = this.lightslategray$delegate;
      new Kotlin.PropertyMetadata('lightslategray');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'lightslategrey', {
    get: function () {
      var $receiver = this.lightslategrey$delegate;
      new Kotlin.PropertyMetadata('lightslategrey');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'lightsteelblue', {
    get: function () {
      var $receiver = this.lightsteelblue$delegate;
      new Kotlin.PropertyMetadata('lightsteelblue');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'lightyellow', {
    get: function () {
      var $receiver = this.lightyellow$delegate;
      new Kotlin.PropertyMetadata('lightyellow');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'lime', {
    get: function () {
      var $receiver = this.lime$delegate;
      new Kotlin.PropertyMetadata('lime');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'limegreen', {
    get: function () {
      var $receiver = this.limegreen$delegate;
      new Kotlin.PropertyMetadata('limegreen');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'linen', {
    get: function () {
      var $receiver = this.linen$delegate;
      new Kotlin.PropertyMetadata('linen');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'magenta', {
    get: function () {
      var $receiver = this.magenta$delegate;
      new Kotlin.PropertyMetadata('magenta');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'maroon', {
    get: function () {
      var $receiver = this.maroon$delegate;
      new Kotlin.PropertyMetadata('maroon');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'mediumaquamarine', {
    get: function () {
      var $receiver = this.mediumaquamarine$delegate;
      new Kotlin.PropertyMetadata('mediumaquamarine');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'mediumblue', {
    get: function () {
      var $receiver = this.mediumblue$delegate;
      new Kotlin.PropertyMetadata('mediumblue');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'mediumorchid', {
    get: function () {
      var $receiver = this.mediumorchid$delegate;
      new Kotlin.PropertyMetadata('mediumorchid');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'mediumpurple', {
    get: function () {
      var $receiver = this.mediumpurple$delegate;
      new Kotlin.PropertyMetadata('mediumpurple');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'mediumseagreen', {
    get: function () {
      var $receiver = this.mediumseagreen$delegate;
      new Kotlin.PropertyMetadata('mediumseagreen');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'mediumslateblue', {
    get: function () {
      var $receiver = this.mediumslateblue$delegate;
      new Kotlin.PropertyMetadata('mediumslateblue');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'mediumspringgreen', {
    get: function () {
      var $receiver = this.mediumspringgreen$delegate;
      new Kotlin.PropertyMetadata('mediumspringgreen');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'mediumturquoise', {
    get: function () {
      var $receiver = this.mediumturquoise$delegate;
      new Kotlin.PropertyMetadata('mediumturquoise');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'mediumvioletred', {
    get: function () {
      var $receiver = this.mediumvioletred$delegate;
      new Kotlin.PropertyMetadata('mediumvioletred');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'midnightblue', {
    get: function () {
      var $receiver = this.midnightblue$delegate;
      new Kotlin.PropertyMetadata('midnightblue');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'mintcream', {
    get: function () {
      var $receiver = this.mintcream$delegate;
      new Kotlin.PropertyMetadata('mintcream');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'mistyrose', {
    get: function () {
      var $receiver = this.mistyrose$delegate;
      new Kotlin.PropertyMetadata('mistyrose');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'moccasin', {
    get: function () {
      var $receiver = this.moccasin$delegate;
      new Kotlin.PropertyMetadata('moccasin');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'navajowhite', {
    get: function () {
      var $receiver = this.navajowhite$delegate;
      new Kotlin.PropertyMetadata('navajowhite');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'navy', {
    get: function () {
      var $receiver = this.navy$delegate;
      new Kotlin.PropertyMetadata('navy');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'oldlace', {
    get: function () {
      var $receiver = this.oldlace$delegate;
      new Kotlin.PropertyMetadata('oldlace');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'olive', {
    get: function () {
      var $receiver = this.olive$delegate;
      new Kotlin.PropertyMetadata('olive');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'olivedrab', {
    get: function () {
      var $receiver = this.olivedrab$delegate;
      new Kotlin.PropertyMetadata('olivedrab');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'orange', {
    get: function () {
      var $receiver = this.orange$delegate;
      new Kotlin.PropertyMetadata('orange');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'orangered', {
    get: function () {
      var $receiver = this.orangered$delegate;
      new Kotlin.PropertyMetadata('orangered');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'orchid', {
    get: function () {
      var $receiver = this.orchid$delegate;
      new Kotlin.PropertyMetadata('orchid');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'palegoldenrod', {
    get: function () {
      var $receiver = this.palegoldenrod$delegate;
      new Kotlin.PropertyMetadata('palegoldenrod');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'palegreen', {
    get: function () {
      var $receiver = this.palegreen$delegate;
      new Kotlin.PropertyMetadata('palegreen');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'paleturquoise', {
    get: function () {
      var $receiver = this.paleturquoise$delegate;
      new Kotlin.PropertyMetadata('paleturquoise');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'palevioletred', {
    get: function () {
      var $receiver = this.palevioletred$delegate;
      new Kotlin.PropertyMetadata('palevioletred');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'papayawhip', {
    get: function () {
      var $receiver = this.papayawhip$delegate;
      new Kotlin.PropertyMetadata('papayawhip');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'peachpuff', {
    get: function () {
      var $receiver = this.peachpuff$delegate;
      new Kotlin.PropertyMetadata('peachpuff');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'peru', {
    get: function () {
      var $receiver = this.peru$delegate;
      new Kotlin.PropertyMetadata('peru');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'pink', {
    get: function () {
      var $receiver = this.pink$delegate;
      new Kotlin.PropertyMetadata('pink');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'plum', {
    get: function () {
      var $receiver = this.plum$delegate;
      new Kotlin.PropertyMetadata('plum');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'powderblue', {
    get: function () {
      var $receiver = this.powderblue$delegate;
      new Kotlin.PropertyMetadata('powderblue');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'purple', {
    get: function () {
      var $receiver = this.purple$delegate;
      new Kotlin.PropertyMetadata('purple');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'rebeccapurple', {
    get: function () {
      var $receiver = this.rebeccapurple$delegate;
      new Kotlin.PropertyMetadata('rebeccapurple');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'red', {
    get: function () {
      var $receiver = this.red$delegate;
      new Kotlin.PropertyMetadata('red');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'rosybrown', {
    get: function () {
      var $receiver = this.rosybrown$delegate;
      new Kotlin.PropertyMetadata('rosybrown');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'royalblue', {
    get: function () {
      var $receiver = this.royalblue$delegate;
      new Kotlin.PropertyMetadata('royalblue');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'saddlebrown', {
    get: function () {
      var $receiver = this.saddlebrown$delegate;
      new Kotlin.PropertyMetadata('saddlebrown');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'salmon', {
    get: function () {
      var $receiver = this.salmon$delegate;
      new Kotlin.PropertyMetadata('salmon');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'sandybrown', {
    get: function () {
      var $receiver = this.sandybrown$delegate;
      new Kotlin.PropertyMetadata('sandybrown');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'seagreen', {
    get: function () {
      var $receiver = this.seagreen$delegate;
      new Kotlin.PropertyMetadata('seagreen');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'seashell', {
    get: function () {
      var $receiver = this.seashell$delegate;
      new Kotlin.PropertyMetadata('seashell');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'sienna', {
    get: function () {
      var $receiver = this.sienna$delegate;
      new Kotlin.PropertyMetadata('sienna');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'silver', {
    get: function () {
      var $receiver = this.silver$delegate;
      new Kotlin.PropertyMetadata('silver');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'skyblue', {
    get: function () {
      var $receiver = this.skyblue$delegate;
      new Kotlin.PropertyMetadata('skyblue');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'slateblue', {
    get: function () {
      var $receiver = this.slateblue$delegate;
      new Kotlin.PropertyMetadata('slateblue');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'slategray', {
    get: function () {
      var $receiver = this.slategray$delegate;
      new Kotlin.PropertyMetadata('slategray');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'slategrey', {
    get: function () {
      var $receiver = this.slategrey$delegate;
      new Kotlin.PropertyMetadata('slategrey');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'snow', {
    get: function () {
      var $receiver = this.snow$delegate;
      new Kotlin.PropertyMetadata('snow');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'springgreen', {
    get: function () {
      var $receiver = this.springgreen$delegate;
      new Kotlin.PropertyMetadata('springgreen');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'steelblue', {
    get: function () {
      var $receiver = this.steelblue$delegate;
      new Kotlin.PropertyMetadata('steelblue');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'tan', {
    get: function () {
      var $receiver = this.tan$delegate;
      new Kotlin.PropertyMetadata('tan');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'teal', {
    get: function () {
      var $receiver = this.teal$delegate;
      new Kotlin.PropertyMetadata('teal');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'thistle', {
    get: function () {
      var $receiver = this.thistle$delegate;
      new Kotlin.PropertyMetadata('thistle');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'tomato', {
    get: function () {
      var $receiver = this.tomato$delegate;
      new Kotlin.PropertyMetadata('tomato');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'turquoise', {
    get: function () {
      var $receiver = this.turquoise$delegate;
      new Kotlin.PropertyMetadata('turquoise');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'violet', {
    get: function () {
      var $receiver = this.violet$delegate;
      new Kotlin.PropertyMetadata('violet');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'wheat', {
    get: function () {
      var $receiver = this.wheat$delegate;
      new Kotlin.PropertyMetadata('wheat');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'white', {
    get: function () {
      var $receiver = this.white$delegate;
      new Kotlin.PropertyMetadata('white');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'whitesmoke', {
    get: function () {
      var $receiver = this.whitesmoke$delegate;
      new Kotlin.PropertyMetadata('whitesmoke');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'yellow', {
    get: function () {
      var $receiver = this.yellow$delegate;
      new Kotlin.PropertyMetadata('yellow');
      return $receiver.value;
    }
  });
  Object.defineProperty(colors.prototype, 'yellowgreen', {
    get: function () {
      var $receiver = this.yellowgreen$delegate;
      new Kotlin.PropertyMetadata('yellowgreen');
      return $receiver.value;
    }
  });
  function colors$aliceblue$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(15792383);
    };
  }
  function colors$antiquewhite$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16444375);
    };
  }
  function colors$aqua$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(65535);
    };
  }
  function colors$aquamarine$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(8388564);
    };
  }
  function colors$azure$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(15794175);
    };
  }
  function colors$beige$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16119260);
    };
  }
  function colors$bisque$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16770244);
    };
  }
  function colors$black$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(0);
    };
  }
  function colors$blanchedalmond$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16772045);
    };
  }
  function colors$blue$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(255);
    };
  }
  function colors$blueviolet$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(9055202);
    };
  }
  function colors$brown$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(10824234);
    };
  }
  function colors$burlywood$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(14596231);
    };
  }
  function colors$cadetblue$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(6266528);
    };
  }
  function colors$chartreuse$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(8388352);
    };
  }
  function colors$chocolate$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(13789470);
    };
  }
  function colors$coral$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16744272);
    };
  }
  function colors$cornflowerblue$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(6591981);
    };
  }
  function colors$cornsilk$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16775388);
    };
  }
  function colors$crimson$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(14423100);
    };
  }
  function colors$cyan$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(65535);
    };
  }
  function colors$darkblue$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(139);
    };
  }
  function colors$darkcyan$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(35723);
    };
  }
  function colors$darkgoldenrod$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(12092939);
    };
  }
  function colors$darkgray$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(11119017);
    };
  }
  function colors$darkgreen$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(25600);
    };
  }
  function colors$darkgrey$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(11119017);
    };
  }
  function colors$darkkhaki$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(12433259);
    };
  }
  function colors$darkmagenta$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(9109643);
    };
  }
  function colors$darkolivegreen$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(5597999);
    };
  }
  function colors$darkorange$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16747520);
    };
  }
  function colors$darkorchid$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(10040012);
    };
  }
  function colors$darkred$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(9109504);
    };
  }
  function colors$darksalmon$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(15308410);
    };
  }
  function colors$darkseagreen$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(9419919);
    };
  }
  function colors$darkslateblue$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(4734347);
    };
  }
  function colors$darkslategray$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(3100495);
    };
  }
  function colors$darkslategrey$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(3100495);
    };
  }
  function colors$darkturquoise$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(52945);
    };
  }
  function colors$darkviolet$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(9699539);
    };
  }
  function colors$deeppink$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16716947);
    };
  }
  function colors$deepskyblue$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(49151);
    };
  }
  function colors$dimgray$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(6908265);
    };
  }
  function colors$dimgrey$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(6908265);
    };
  }
  function colors$dodgerblue$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(2003199);
    };
  }
  function colors$firebrick$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(11674146);
    };
  }
  function colors$floralwhite$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16775920);
    };
  }
  function colors$forestgreen$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(2263842);
    };
  }
  function colors$fuchsia$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16711935);
    };
  }
  function colors$gainsboro$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(14474460);
    };
  }
  function colors$ghostwhite$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16316671);
    };
  }
  function colors$gold$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16766720);
    };
  }
  function colors$goldenrod$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(14329120);
    };
  }
  function colors$gray$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(8421504);
    };
  }
  function colors$green$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(32768);
    };
  }
  function colors$greenyellow$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(11403055);
    };
  }
  function colors$grey$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(8421504);
    };
  }
  function colors$honeydew$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(15794160);
    };
  }
  function colors$hotpink$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16738740);
    };
  }
  function colors$indianred$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(13458524);
    };
  }
  function colors$indigo$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(4915330);
    };
  }
  function colors$ivory$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16777200);
    };
  }
  function colors$khaki$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(15787660);
    };
  }
  function colors$lavender$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(15132410);
    };
  }
  function colors$lavenderblush$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16773365);
    };
  }
  function colors$lawngreen$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(8190976);
    };
  }
  function colors$lemonchiffon$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16775885);
    };
  }
  function colors$lightblue$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(11393254);
    };
  }
  function colors$lightcoral$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(15761536);
    };
  }
  function colors$lightcyan$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(14745599);
    };
  }
  function colors$lightgoldenrodyellow$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16448210);
    };
  }
  function colors$lightgray$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(13882323);
    };
  }
  function colors$lightgreen$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(9498256);
    };
  }
  function colors$lightgrey$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(13882323);
    };
  }
  function colors$lightpink$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16758465);
    };
  }
  function colors$lightsalmon$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16752762);
    };
  }
  function colors$lightseagreen$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(2142890);
    };
  }
  function colors$lightskyblue$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(8900346);
    };
  }
  function colors$lightslategray$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(7833753);
    };
  }
  function colors$lightslategrey$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(7833753);
    };
  }
  function colors$lightsteelblue$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(11584734);
    };
  }
  function colors$lightyellow$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16777184);
    };
  }
  function colors$lime$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(65280);
    };
  }
  function colors$limegreen$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(3329330);
    };
  }
  function colors$linen$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16445670);
    };
  }
  function colors$magenta$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16711935);
    };
  }
  function colors$maroon$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(8388608);
    };
  }
  function colors$mediumaquamarine$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(6737322);
    };
  }
  function colors$mediumblue$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(205);
    };
  }
  function colors$mediumorchid$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(12211667);
    };
  }
  function colors$mediumpurple$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(9662683);
    };
  }
  function colors$mediumseagreen$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(3978097);
    };
  }
  function colors$mediumslateblue$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(8087790);
    };
  }
  function colors$mediumspringgreen$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(64154);
    };
  }
  function colors$mediumturquoise$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(4772300);
    };
  }
  function colors$mediumvioletred$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(13047173);
    };
  }
  function colors$midnightblue$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(1644912);
    };
  }
  function colors$mintcream$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16121850);
    };
  }
  function colors$mistyrose$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16770273);
    };
  }
  function colors$moccasin$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16770229);
    };
  }
  function colors$navajowhite$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16768685);
    };
  }
  function colors$navy$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(128);
    };
  }
  function colors$oldlace$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16643558);
    };
  }
  function colors$olive$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(8421376);
    };
  }
  function colors$olivedrab$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(7048739);
    };
  }
  function colors$orange$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16753920);
    };
  }
  function colors$orangered$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16729344);
    };
  }
  function colors$orchid$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(14315734);
    };
  }
  function colors$palegoldenrod$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(15657130);
    };
  }
  function colors$palegreen$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(10025880);
    };
  }
  function colors$paleturquoise$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(11529966);
    };
  }
  function colors$palevioletred$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(14381203);
    };
  }
  function colors$papayawhip$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16773077);
    };
  }
  function colors$peachpuff$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16767673);
    };
  }
  function colors$peru$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(13468991);
    };
  }
  function colors$pink$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16761035);
    };
  }
  function colors$plum$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(14524637);
    };
  }
  function colors$powderblue$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(11591910);
    };
  }
  function colors$purple$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(8388736);
    };
  }
  function colors$rebeccapurple$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(6697881);
    };
  }
  function colors$red$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16711680);
    };
  }
  function colors$rosybrown$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(12357519);
    };
  }
  function colors$royalblue$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(4286945);
    };
  }
  function colors$saddlebrown$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(9127187);
    };
  }
  function colors$salmon$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16416882);
    };
  }
  function colors$sandybrown$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16032864);
    };
  }
  function colors$seagreen$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(3050327);
    };
  }
  function colors$seashell$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16774638);
    };
  }
  function colors$sienna$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(10506797);
    };
  }
  function colors$silver$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(12632256);
    };
  }
  function colors$skyblue$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(8900331);
    };
  }
  function colors$slateblue$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(6970061);
    };
  }
  function colors$slategray$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(7372944);
    };
  }
  function colors$slategrey$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(7372944);
    };
  }
  function colors$snow$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16775930);
    };
  }
  function colors$springgreen$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(65407);
    };
  }
  function colors$steelblue$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(4620980);
    };
  }
  function colors$tan$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(13808780);
    };
  }
  function colors$teal$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(32896);
    };
  }
  function colors$thistle$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(14204888);
    };
  }
  function colors$tomato$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16737095);
    };
  }
  function colors$turquoise$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(4251856);
    };
  }
  function colors$violet$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(15631086);
    };
  }
  function colors$wheat$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16113331);
    };
  }
  function colors$white$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16777215);
    };
  }
  function colors$whitesmoke$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16119285);
    };
  }
  function colors$yellow$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(16776960);
    };
  }
  function colors$yellowgreen$lambda(this$colors) {
    return function () {
      return this$colors.get_col_s8ev3n$(10145074);
    };
  }
  colors.$metadata$ = {
    kind: Kotlin.Kind.OBJECT,
    simpleName: 'colors',
    interfaces: []
  };
  var colors_instance = null;
  function colors_getInstance() {
    if (colors_instance === null) {
      new colors();
    }
    return colors_instance;
  }
  function ColorTests() {
    StringSpec.call(this);
    this.invoke_79xod4$('io.data2viz.color.getColor defaults', ColorTests_init$lambda(this));
    this.invoke_79xod4$('set r, g, b', ColorTests_init$lambda_0(this));
    this.invoke_79xod4$('string to io.data2viz.color.getColor', ColorTests_init$lambda_1(this));
    this.invoke_79xod4$('color to hex should be 7 char', ColorTests_init$lambda_2(this));
  }
  function ColorTests_init$lambda(this$ColorTests) {
    return function () {
      var color = new Color();
      this$ColorTests.shouldBe_3ta935$(color.rgb, 16777215);
      this$ColorTests.shouldBe_3ta935$(color.r, 255);
      this$ColorTests.shouldBe_3ta935$(color.g, 255);
      this$ColorTests.shouldBe_3ta935$(color.b, 255);
    };
  }
  function ColorTests_init$lambda_0(this$ColorTests) {
    return function () {
      var color = new Color();
      color.r = 171;
      this$ColorTests.shouldBe_3ta935$(color.rgb, 11272191);
      color.g = 171;
      this$ColorTests.shouldBe_3ta935$(color.rgb, 11250687);
      color.b = 171;
      this$ColorTests.shouldBe_3ta935$(color.rgb, 11250603);
      this$ColorTests.shouldBe_3ta935$(color.rgbHex, '#ababab');
    };
  }
  function ColorTests_init$lambda_1(this$ColorTests) {
    return function () {
      this$ColorTests.shouldBe_3ta935$(get_color('#0b0b0b').rgb, 723723);
      this$ColorTests.shouldBe_3ta935$(get_color('#0b0b0b').r, 11);
    };
  }
  function ColorTests_init$lambda_2(this$ColorTests) {
    return function () {
      this$ColorTests.shouldBe_3ta935$(colors_getInstance().black.rgbHex, '#000000');
    };
  }
  ColorTests.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'ColorTests',
    interfaces: [StringSpec]
  };
  function EncodedGradient(colorsAsString) {
    EncodedGradient$Companion_getInstance();
    if (!(colorsAsString.length % 6 === 0)) {
      var message = 'colorsAsString size should be a multiple of 6';
      throw new Kotlin.kotlin.IllegalArgumentException(message.toString());
    }
    this.colors$delegate = lazy(EncodedGradient$colors$lambda(colorsAsString));
  }
  function EncodedGradient$Companion() {
    EncodedGradient$Companion_instance = this;
    this.viridis$delegate = lazy(EncodedGradient$Companion$viridis$lambda);
    this.magma$delegate = lazy(EncodedGradient$Companion$magma$lambda);
    this.inferno$delegate = lazy(EncodedGradient$Companion$inferno$lambda);
    this.plasma$delegate = lazy(EncodedGradient$Companion$plasma$lambda);
  }
  Object.defineProperty(EncodedGradient$Companion.prototype, 'viridis', {
    get: function () {
      var $receiver = this.viridis$delegate;
      new Kotlin.PropertyMetadata('viridis');
      return $receiver.value;
    }
  });
  Object.defineProperty(EncodedGradient$Companion.prototype, 'magma', {
    get: function () {
      var $receiver = this.magma$delegate;
      new Kotlin.PropertyMetadata('magma');
      return $receiver.value;
    }
  });
  Object.defineProperty(EncodedGradient$Companion.prototype, 'inferno', {
    get: function () {
      var $receiver = this.inferno$delegate;
      new Kotlin.PropertyMetadata('inferno');
      return $receiver.value;
    }
  });
  Object.defineProperty(EncodedGradient$Companion.prototype, 'plasma', {
    get: function () {
      var $receiver = this.plasma$delegate;
      new Kotlin.PropertyMetadata('plasma');
      return $receiver.value;
    }
  });
  function EncodedGradient$Companion$viridis$lambda() {
    return new EncodedGradient('44015444025645045745055946075a46085c460a5d460b5e470d60470e6147106347116447136548146748166848176948186a481a6c481b6d481c6e481d6f481f70482071482173482374482475482576482677482878482979472a7a472c7a472d7b472e7c472f7d46307e46327e46337f463480453581453781453882443983443a83443b84433d84433e85423f854240864241864142874144874045884046883f47883f48893e49893e4a893e4c8a3d4d8a3d4e8a3c4f8a3c508b3b518b3b528b3a538b3a548c39558c39568c38588c38598c375a8c375b8d365c8d365d8d355e8d355f8d34608d34618d33628d33638d32648e32658e31668e31678e31688e30698e306a8e2f6b8e2f6c8e2e6d8e2e6e8e2e6f8e2d708e2d718e2c718e2c728e2c738e2b748e2b758e2a768e2a778e2a788e29798e297a8e297b8e287c8e287d8e277e8e277f8e27808e26818e26828e26828e25838e25848e25858e24868e24878e23888e23898e238a8d228b8d228c8d228d8d218e8d218f8d21908d21918c20928c20928c20938c1f948c1f958b1f968b1f978b1f988b1f998a1f9a8a1e9b8a1e9c891e9d891f9e891f9f881fa0881fa1881fa1871fa28720a38620a48621a58521a68522a78522a88423a98324aa8325ab8225ac8226ad8127ad8128ae8029af7f2ab07f2cb17e2db27d2eb37c2fb47c31b57b32b67a34b67935b77937b87838b9773aba763bbb753dbc743fbc7340bd7242be7144bf7046c06f48c16e4ac16d4cc26c4ec36b50c46a52c56954c56856c66758c7655ac8645cc8635ec96260ca6063cb5f65cb5e67cc5c69cd5b6ccd5a6ece5870cf5773d05675d05477d1537ad1517cd2507fd34e81d34d84d44b86d54989d5488bd6468ed64590d74393d74195d84098d83e9bd93c9dd93ba0da39a2da37a5db36a8db34aadc32addc30b0dd2fb2dd2db5de2bb8de29bade28bddf26c0df25c2df23c5e021c8e020cae11fcde11dd0e11cd2e21bd5e21ad8e219dae319dde318dfe318e2e418e5e419e7e419eae51aece51befe51cf1e51df4e61ef6e620f8e621fbe723fde725');
  }
  function EncodedGradient$Companion$magma$lambda() {
    return new EncodedGradient('00000401000501010601010802010902020b02020d03030f03031204041405041606051806051a07061c08071e0907200a08220b09240c09260d0a290e0b2b100b2d110c2f120d31130d34140e36150e38160f3b180f3d19103f1a10421c10441d11471e114920114b21114e22115024125325125527125829115a2a115c2c115f2d11612f116331116533106734106936106b38106c390f6e3b0f703d0f713f0f72400f74420f75440f764510774710784910784a10794c117a4e117b4f127b51127c52137c54137d56147d57157e59157e5a167e5c167f5d177f5f187f601880621980641a80651a80671b80681c816a1c816b1d816d1d816e1e81701f81721f817320817521817621817822817922827b23827c23827e24828025828125818326818426818627818827818928818b29818c29818e2a81902a81912b81932b80942c80962c80982d80992d809b2e7f9c2e7f9e2f7fa02f7fa1307ea3307ea5317ea6317da8327daa337dab337cad347cae347bb0357bb2357bb3367ab5367ab73779b83779ba3878bc3978bd3977bf3a77c03a76c23b75c43c75c53c74c73d73c83e73ca3e72cc3f71cd4071cf4070d0416fd2426fd3436ed5446dd6456cd8456cd9466bdb476adc4869de4968df4a68e04c67e24d66e34e65e44f64e55064e75263e85362e95462ea5661eb5760ec5860ed5a5fee5b5eef5d5ef05f5ef1605df2625df2645cf3655cf4675cf4695cf56b5cf66c5cf66e5cf7705cf7725cf8745cf8765cf9785df9795df97b5dfa7d5efa7f5efa815ffb835ffb8560fb8761fc8961fc8a62fc8c63fc8e64fc9065fd9266fd9467fd9668fd9869fd9a6afd9b6bfe9d6cfe9f6dfea16efea36ffea571fea772fea973feaa74feac76feae77feb078feb27afeb47bfeb67cfeb77efeb97ffebb81febd82febf84fec185fec287fec488fec68afec88cfeca8dfecc8ffecd90fecf92fed194fed395fed597fed799fed89afdda9cfddc9efddea0fde0a1fde2a3fde3a5fde5a7fde7a9fde9aafdebacfcecaefceeb0fcf0b2fcf2b4fcf4b6fcf6b8fcf7b9fcf9bbfcfbbdfcfdbf');
  }
  function EncodedGradient$Companion$inferno$lambda() {
    return new EncodedGradient('00000401000501010601010802010a02020c02020e03021004031204031405041706041907051b08051d09061f0a07220b07240c08260d08290e092b10092d110a30120a32140b34150b37160b39180c3c190c3e1b0c411c0c431e0c451f0c48210c4a230c4c240c4f260c51280b53290b552b0b572d0b592f0a5b310a5c320a5e340a5f3609613809623909633b09643d09653e0966400a67420a68440a68450a69470b6a490b6a4a0c6b4c0c6b4d0d6c4f0d6c510e6c520e6d540f6d550f6d57106e59106e5a116e5c126e5d126e5f136e61136e62146e64156e65156e67166e69166e6a176e6c186e6d186e6f196e71196e721a6e741a6e751b6e771c6d781c6d7a1d6d7c1d6d7d1e6d7f1e6c801f6c82206c84206b85216b87216b88226a8a226a8c23698d23698f24699025689225689326679526679727669827669a28659b29649d29649f2a63a02a63a22b62a32c61a52c60a62d60a82e5fa92e5eab2f5ead305dae305cb0315bb1325ab3325ab43359b63458b73557b93556ba3655bc3754bd3853bf3952c03a51c13a50c33b4fc43c4ec63d4dc73e4cc83f4bca404acb4149cc4248ce4347cf4446d04545d24644d34743d44842d54a41d74b3fd84c3ed94d3dda4e3cdb503bdd513ade5238df5337e05536e15635e25734e35933e45a31e55c30e65d2fe75e2ee8602de9612bea632aeb6429eb6628ec6726ed6925ee6a24ef6c23ef6e21f06f20f1711ff1731df2741cf3761bf37819f47918f57b17f57d15f67e14f68013f78212f78410f8850ff8870ef8890cf98b0bf98c0af98e09fa9008fa9207fa9407fb9606fb9706fb9906fb9b06fb9d07fc9f07fca108fca309fca50afca60cfca80dfcaa0ffcac11fcae12fcb014fcb216fcb418fbb61afbb81dfbba1ffbbc21fbbe23fac026fac228fac42afac62df9c72ff9c932f9cb35f8cd37f8cf3af7d13df7d340f6d543f6d746f5d949f5db4cf4dd4ff4df53f4e156f3e35af3e55df2e661f2e865f2ea69f1ec6df1ed71f1ef75f1f179f2f27df2f482f3f586f3f68af4f88ef5f992f6fa96f8fb9af9fc9dfafda1fcffa4');
  }
  function EncodedGradient$Companion$plasma$lambda() {
    return new EncodedGradient('0d088710078813078916078a19068c1b068d1d068e20068f2206902406912605912805922a05932c05942e05952f059631059733059735049837049938049a3a049a3c049b3e049c3f049c41049d43039e44039e46039f48039f4903a04b03a14c02a14e02a25002a25102a35302a35502a45601a45801a45901a55b01a55c01a65e01a66001a66100a76300a76400a76600a76700a86900a86a00a86c00a86e00a86f00a87100a87201a87401a87501a87701a87801a87a02a87b02a87d03a87e03a88004a88104a78305a78405a78606a68707a68808a68a09a58b0aa58d0ba58e0ca48f0da4910ea3920fa39410a29511a19613a19814a099159f9a169f9c179e9d189d9e199da01a9ca11b9ba21d9aa31e9aa51f99a62098a72197a82296aa2395ab2494ac2694ad2793ae2892b02991b12a90b22b8fb32c8eb42e8db52f8cb6308bb7318ab83289ba3388bb3488bc3587bd3786be3885bf3984c03a83c13b82c23c81c33d80c43e7fc5407ec6417dc7427cc8437bc9447aca457acb4679cc4778cc4977cd4a76ce4b75cf4c74d04d73d14e72d24f71d35171d45270d5536fd5546ed6556dd7566cd8576bd9586ada5a6ada5b69db5c68dc5d67dd5e66de5f65de6164df6263e06363e16462e26561e26660e3685fe4695ee56a5de56b5de66c5ce76e5be76f5ae87059e97158e97257ea7457eb7556eb7655ec7754ed7953ed7a52ee7b51ef7c51ef7e50f07f4ff0804ef1814df1834cf2844bf3854bf3874af48849f48948f58b47f58c46f68d45f68f44f79044f79143f79342f89441f89540f9973ff9983ef99a3efa9b3dfa9c3cfa9e3bfb9f3afba139fba238fca338fca537fca636fca835fca934fdab33fdac33fdae32fdaf31fdb130fdb22ffdb42ffdb52efeb72dfeb82cfeba2cfebb2bfebd2afebe2afec029fdc229fdc328fdc527fdc627fdc827fdca26fdcb26fccd25fcce25fcd025fcd225fbd324fbd524fbd724fad824fada24f9dc24f9dd25f8df25f8e125f7e225f7e425f6e626f6e826f5e926f5eb27f4ed27f3ee27f3f027f2f227f1f426f1f525f0f724f0f921');
  }
  EncodedGradient$Companion.$metadata$ = {
    kind: Kotlin.Kind.OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var EncodedGradient$Companion_instance = null;
  function EncodedGradient$Companion_getInstance() {
    if (EncodedGradient$Companion_instance === null) {
      new EncodedGradient$Companion();
    }
    return EncodedGradient$Companion_instance;
  }
  Object.defineProperty(EncodedGradient.prototype, 'colors', {
    get: function () {
      var $receiver = this.colors$delegate;
      new Kotlin.PropertyMetadata('colors');
      return $receiver.value;
    }
  });
  EncodedGradient.prototype.color_14dthe$ = function (percent) {
    return this.colors.get_za3lpa$(coerceAtMost(coerceAtLeast(Math.floor(percent * this.colors.size), 0), this.colors.size - 1 | 0));
  };
  function EncodedGradient$colors$lambda(closure$colorsAsString) {
    return function () {
      var $receiver = closure$colorsAsString;
      var $receiver_0 = new IntRange(0, ($receiver.length / 6 | 0) - 1 | 0);
      var destination = Kotlin.kotlin.collections.ArrayList_init_ww73n8$(Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$($receiver_0, 10));
      var tmp$;
      tmp$ = $receiver_0.iterator();
      while (tmp$.hasNext()) {
        var item = tmp$.next();
        var tmp$_0 = destination.add_11rb$;
        var startIndex = 6 * item | 0;
        var endIndex = (6 * item | 0) + 6 | 0;
        tmp$_0.call(destination, get_color('#' + $receiver.substring(startIndex, endIndex)));
      }
      return destination;
    };
  }
  EncodedGradient.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'EncodedGradient',
    interfaces: []
  };
  function ViridisTests() {
    StringSpec.call(this);
    this.invoke_79xod4$('viridis', ViridisTests_init$lambda(this));
    this.invoke_79xod4$('magma', ViridisTests_init$lambda_0(this));
    this.invoke_79xod4$('inferno', ViridisTests_init$lambda_1(this));
    this.invoke_79xod4$('plasma', ViridisTests_init$lambda_2(this));
  }
  function ViridisTests$testAndGraph$node(name) {
    return document.createElementNS(namespace.Companion.svg, name);
  }
  ViridisTests.prototype.testAndGraph_w7kld8$ = function (gradient) {
    var tmp$;
    var node = ViridisTests$testAndGraph$node;
    var body = (tmp$ = document.querySelector('body')) != null ? tmp$ : Kotlin.throwNPE();
    var $receiver = node('svg');
    $receiver.setAttribute('width', '400');
    $receiver.setAttribute('height', '20');
    var tmp$_0;
    tmp$_0 = (new IntRange(0, 399)).iterator();
    while (tmp$_0.hasNext()) {
      var element = tmp$_0.next();
      var $receiver_0 = node('rect');
      $receiver_0.setAttribute('fill', gradient.color_14dthe$(element / 400).rgbHex);
      $receiver_0.setAttribute('x', element.toString());
      $receiver_0.setAttribute('y', '0');
      $receiver_0.setAttribute('width', '1');
      $receiver_0.setAttribute('height', '20');
      $receiver.appendChild($receiver_0);
    }
    body.appendChild($receiver);
  };
  function ViridisTests_init$lambda(this$ViridisTests) {
    return function () {
      this$ViridisTests.testAndGraph_w7kld8$(EncodedGradient$Companion_getInstance().viridis);
    };
  }
  function ViridisTests_init$lambda_0(this$ViridisTests) {
    return function () {
      this$ViridisTests.testAndGraph_w7kld8$(EncodedGradient$Companion_getInstance().magma);
    };
  }
  function ViridisTests_init$lambda_1(this$ViridisTests) {
    return function () {
      this$ViridisTests.testAndGraph_w7kld8$(EncodedGradient$Companion_getInstance().inferno);
    };
  }
  function ViridisTests_init$lambda_2(this$ViridisTests) {
    return function () {
      this$ViridisTests.testAndGraph_w7kld8$(EncodedGradient$Companion_getInstance().plasma);
    };
  }
  ViridisTests.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'ViridisTests',
    interfaces: [StringSpec]
  };
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$color = package$data2viz.color || (package$data2viz.color = {});
  package$color.get_color_pdl1vz$ = get_color;
  package$color.rgba_1ugm5o$ = rgba;
  package$color.Color = Color;
  Object.defineProperty(package$color, 'colors', {
    get: colors_getInstance
  });
  package$color.ColorTests = ColorTests;
  Object.defineProperty(EncodedGradient, 'Companion', {
    get: EncodedGradient$Companion_getInstance
  });
  package$color.EncodedGradient = EncodedGradient;
  package$color.ViridisTests = ViridisTests;
  Kotlin.defineModule('color', _);
  return _;
});

//@ sourceMappingURL=color.js.map
