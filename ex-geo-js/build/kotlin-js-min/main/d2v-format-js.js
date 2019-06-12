(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'd2v-format-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'd2v-format-js'.");
    }
    root['d2v-format-js'] = factory(typeof this['d2v-format-js'] === 'undefined' ? {} : this['d2v-format-js'], kotlin);
  }
}(this, function (_, Kotlin) {
  'use strict';
  var listOf = Kotlin.kotlin.collections.listOf_i5x0yv$;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var equals = Kotlin.equals;
  var coerceIn = Kotlin.kotlin.ranges.coerceIn_e4yvb3$;
  var toDouble = Kotlin.kotlin.text.toDouble_pdl1vz$;
  var CharRange = Kotlin.kotlin.ranges.CharRange;
  var until = Kotlin.kotlin.ranges.until_dqglrj$;
  var slice = Kotlin.kotlin.text.slice_fc3b62$;
  var unboxChar = Kotlin.unboxChar;
  var padStart = Kotlin.kotlin.text.padStart_vrc1nu$;
  var ensureNotNull = Kotlin.ensureNotNull;
  var IntRange = Kotlin.kotlin.ranges.IntRange;
  var getCallableRef = Kotlin.getCallableRef;
  var StringBuilder_init = Kotlin.kotlin.text.StringBuilder_init;
  var coerceIn_0 = Kotlin.kotlin.ranges.coerceIn_nig4hr$;
  var numberToInt = Kotlin.numberToInt;
  var reverse = Kotlin.kotlin.collections.reverse_vvxzk3$;
  var joinToString = Kotlin.kotlin.collections.joinToString_fmv235$;
  var padEnd = Kotlin.kotlin.text.padEnd_vrc1nu$;
  var indexOf = Kotlin.kotlin.text.indexOf_l5u8uk$;
  var toIntOrNull = Kotlin.kotlin.text.toIntOrNull_pdl1vz$;
  var coerceAtLeast = Kotlin.kotlin.ranges.coerceAtLeast_dqglrj$;
  var Math_0 = Math;
  var iterator = Kotlin.kotlin.text.iterator_gw00vp$;
  var toBoxedChar = Kotlin.toBoxedChar;
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  var toString = Kotlin.toString;
  var IllegalArgumentException_init = Kotlin.kotlin.IllegalArgumentException_init_pdl1vj$;
  var toInt = Kotlin.kotlin.text.toInt_pdl1vz$;
  var RegexOption = Kotlin.kotlin.text.RegexOption;
  var Regex_init = Kotlin.kotlin.text.Regex_init_sb3q2$;
  var Enum = Kotlin.kotlin.Enum;
  var throwISE = Kotlin.throwISE;
  var NoSuchElementException_init = Kotlin.kotlin.NoSuchElementException;
  var listOf_0 = Kotlin.kotlin.collections.listOf_mh5how$;
  var Kind_OBJECT = Kotlin.Kind.OBJECT;
  var round = Kotlin.kotlin.math.round_14dthe$;
  Symbol.prototype = Object.create(Enum.prototype);
  Symbol.prototype.constructor = Symbol;
  Type.prototype = Object.create(Enum.prototype);
  Type.prototype.constructor = Type;
  Sign.prototype = Object.create(Enum.prototype);
  Sign.prototype.constructor = Sign;
  Align.prototype = Object.create(Enum.prototype);
  Align.prototype.constructor = Align;
  var prefixes;
  var prefixExponent;
  function CoefficientExponent(coefficient, exponent) {
    this.coefficient = coefficient;
    this.exponent = exponent;
  }
  CoefficientExponent.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'CoefficientExponent',
    interfaces: []
  };
  CoefficientExponent.prototype.component1 = function () {
    return this.coefficient;
  };
  CoefficientExponent.prototype.component2 = function () {
    return this.exponent;
  };
  CoefficientExponent.prototype.copy_bm4lxs$ = function (coefficient, exponent) {
    return new CoefficientExponent(coefficient === void 0 ? this.coefficient : coefficient, exponent === void 0 ? this.exponent : exponent);
  };
  CoefficientExponent.prototype.toString = function () {
    return 'CoefficientExponent(coefficient=' + Kotlin.toString(this.coefficient) + (', exponent=' + Kotlin.toString(this.exponent)) + ')';
  };
  CoefficientExponent.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.coefficient) | 0;
    result = result * 31 + Kotlin.hashCode(this.exponent) | 0;
    return result;
  };
  CoefficientExponent.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.coefficient, other.coefficient) && Kotlin.equals(this.exponent, other.exponent)))));
  };
  function formatter(specify) {
    return formatter_0(new Locale(), specify);
  }
  function formatter_0($receiver, specifier) {
    return formatter_3($receiver, specify(specifier));
  }
  function formatter_1(type, fill, align, sign, symbol, zero, width, group, precision) {
    if (type === void 0)
      type = Type$NONE_getInstance();
    if (fill === void 0)
      fill = 32;
    if (align === void 0)
      align = Align$RIGHT_getInstance();
    if (sign === void 0)
      sign = Sign$MINUS_getInstance();
    if (symbol === void 0)
      symbol = null;
    if (zero === void 0)
      zero = false;
    if (width === void 0)
      width = null;
    if (group === void 0)
      group = false;
    if (precision === void 0)
      precision = null;
    return formatter_3(new Locale(), specify_0(type, fill, align, sign, symbol, zero, width, group, precision));
  }
  function formatter_2($receiver, type, fill, align, sign, symbol, zero, width, groupSeparation, precision) {
    if (type === void 0)
      type = Type$NONE_getInstance();
    if (fill === void 0)
      fill = 32;
    if (align === void 0)
      align = Align$RIGHT_getInstance();
    if (sign === void 0)
      sign = Sign$MINUS_getInstance();
    if (symbol === void 0)
      symbol = null;
    if (zero === void 0)
      zero = false;
    if (width === void 0)
      width = null;
    if (groupSeparation === void 0)
      groupSeparation = false;
    if (precision === void 0)
      precision = null;
    return formatter_3($receiver, specify_0(type, fill, align, sign, symbol, zero, width, groupSeparation, precision));
  }
  function formatter$format(closure$prefix, closure$suffix, closure$formatType, closure$precision, closure$spec, closure$maybeSuffix, this$formatter, closure$groupFunction, closure$width) {
    return function (value) {
      var tmp$;
      var returnValue;
      var valuePrefix = closure$prefix;
      var valueSuffix = closure$suffix;
      var valueNegative = value < 0;
      returnValue = closure$formatType(Math_0.abs(value), closure$precision);
      if (valueNegative && toDouble(returnValue) === 0.0)
        valueNegative = false;
      valuePrefix = (valueNegative ? closure$spec.sign === Sign$PARENTHESES_getInstance() ? closure$spec.sign.c_8be2vx$ : '-' : closure$spec.sign === Sign$MINUS_getInstance() || closure$spec.sign === Sign$PARENTHESES_getInstance() ? '' : closure$spec.sign.c_8be2vx$) + valuePrefix;
      valueSuffix = valueSuffix + (closure$spec.type === Type$DECIMAL_WITH_SI_getInstance() ? prefixes.get_za3lpa$(8 + (prefixExponent / 3 | 0) | 0) : '') + (valueNegative && closure$spec.sign === Sign$PARENTHESES_getInstance() ? ')' : '');
      if (closure$maybeSuffix) {
        var i = -1;
        var n = returnValue.length;
        while ((i = i + 1 | 0, i) < n) {
          var c = returnValue.charCodeAt(i);
          if (!(new CharRange(48, 57)).contains_mef7kx$(c)) {
            valueSuffix = (c === 46 ? this$formatter.decimalSeparator + slice(returnValue, until(i + 1 | 0, returnValue.length)) : slice(returnValue, until(i, returnValue.length))) + valueSuffix;
            returnValue = slice(returnValue, until(0, i));
            break;
          }
        }
      }
      if (closure$spec.groupSeparation && !closure$spec.zero)
        returnValue = closure$groupFunction(returnValue, 9999999);
      var length = valuePrefix.length + returnValue.length + valueSuffix.length | 0;
      var padding = closure$width != null && length < closure$width ? padStart('', closure$width - length | 0, unboxChar(closure$spec.fill)) : '';
      if (closure$spec.groupSeparation && closure$spec.zero) {
        returnValue = closure$groupFunction(padding + returnValue, padding.length > 0 ? ensureNotNull(closure$width) - valueSuffix.length | 0 : 9999999);
        padding = '';
      }
      switch (closure$spec.align.name) {
        case 'LEFT':
          tmp$ = valuePrefix + returnValue + valueSuffix + padding;
          break;
        case 'RIGHT_WITHOUT_SIGN':
          tmp$ = valuePrefix + padding + returnValue + valueSuffix;
          break;
        case 'CENTER':
          var padLength = (padding.length / 2 | 0) - 1 | 0;
          tmp$ = slice(padding, new IntRange(0, padLength)) + valuePrefix + returnValue + valueSuffix + slice(padding, until(0, padding.length - 1 - padLength | 0));
          break;
        case 'RIGTH':
        case 'RIGHT':
          tmp$ = padding + valuePrefix + returnValue + valueSuffix;
          break;
        default:tmp$ = Kotlin.noWhenBranchMatched();
          break;
      }
      returnValue = tmp$;
      return numerals(this$formatter, returnValue);
    };
  }
  function formatter_3($receiver, spec) {
    var tmp$;
    var width = spec.width;
    var groupFunction = formatGroup($receiver.grouping, $receiver.groupSeparator);
    var prefix = equals(spec.symbol, Symbol$CURRENCY_getInstance()) ? $receiver.currency.get_za3lpa$(0) : equals(spec.symbol, Symbol$NUMBER_BASE_getInstance()) && get_isNumberBase(spec.type) ? '0' + spec.type.c_8be2vx$.toLowerCase() : '';
    var suffix = equals(spec.symbol, Symbol$CURRENCY_getInstance()) ? $receiver.currency.get_za3lpa$(1) : get_isPercent(spec.type) ? '%' : '';
    var formatType = formatTypes(spec.type);
    var maybeSuffix = get_maybeSuffix(spec.type);
    if (spec.precision == null) {
      tmp$ = spec.type !== Type$NONE_getInstance() ? 6 : 12;
    }
     else {
      tmp$ = gprs.contains_11rb$(spec.type) ? coerceIn(spec.precision, 1, 21) : coerceIn(spec.precision, 0, 20);
    }
    var precision = tmp$;
    var format = formatter$format(prefix, suffix, formatType, precision, spec, maybeSuffix, $receiver, groupFunction, width);
    return getCallableRef('format', function (value) {
      return format(value);
    });
  }
  function numerals($receiver, valueAsString) {
    var tmp$;
    if ($receiver.numerals == null)
      tmp$ = valueAsString;
    else {
      var tmp$_0;
      var accumulator = StringBuilder_init();
      tmp$_0 = iterator(valueAsString);
      while (tmp$_0.hasNext()) {
        var element = unboxChar(tmp$_0.next());
        var acc = accumulator;
        var c = toBoxedChar(element);
        var intValue = unboxChar(c) | 0;
        accumulator = 48 <= intValue && intValue <= 57 ? acc.append_gw00v9$(ensureNotNull($receiver.numerals)[intValue - 48 | 0]) : acc.append_s8itvh$(unboxChar(c));
      }
      tmp$ = accumulator.toString();
    }
    return tmp$;
  }
  function formatPrefix(specifier, fixedPrefix) {
    return formatPrefix_0(new Locale(), specifier, fixedPrefix);
  }
  function formatPrefix$lambda(closure$f, closure$k, closure$prefix) {
    return function (value) {
      return closure$f(closure$k * value) + closure$prefix;
    };
  }
  function formatPrefix_0($receiver, specifier, fixedPrefix) {
    var formatSpecifier = specify(specifier).copy_xzrjoe$(void 0, void 0, void 0, void 0, void 0, void 0, void 0, void 0, Type$FIXED_POINT_getInstance());
    var f = formatter_3($receiver, formatSpecifier);
    var x = exponent(fixedPrefix) / 3.0;
    var e = coerceIn_0(Math_0.floor(x), -8.0, 8.0) * 3;
    var x_0 = -e;
    var k = Math_0.pow(10.0, x_0);
    var prefix = prefixes.get_za3lpa$(8 + numberToInt(e / 3.0) | 0);
    return formatPrefix$lambda(f, k, prefix);
  }
  function exponent(value) {
    var x = formatDecimal(Math_0.abs(value));
    return x != null ? x.exponent : 0;
  }
  function formatGroup$lambda(closure$group, closure$groupSeparator) {
    return function (value, width) {
      var i = value.length;
      var t = ArrayList_init();
      var j = 0;
      var g = closure$group.get_za3lpa$(0);
      var length = 0;
      while (i > 0 && g > 0) {
        if ((length + g + 1 | 0) > width) {
          var b = width - length | 0;
          g = Math_0.max(1, b);
        }
        i = i - g | 0;
        var b_0 = i;
        var tmp$ = Math_0.max(0, b_0);
        var a = value.length;
        var b_1 = i + g | 0;
        var endIndex = Math_0.min(a, b_1);
        t.add_11rb$(value.substring(tmp$, endIndex));
        length = length + (g + 1) | 0;
        if (length > width)
          break;
        j = (j + 1 | 0) % closure$group.size;
        g = closure$group.get_za3lpa$(j);
      }
      reverse(t);
      return joinToString(t, closure$groupSeparator);
    };
  }
  function formatGroup(group, groupSeparator) {
    return formatGroup$lambda(group, groupSeparator);
  }
  function formatTypes$lambda(x, p) {
    return toFixed(x, p);
  }
  function formatTypes$lambda_0(x, p) {
    return toFixed(x * 100, p);
  }
  function formatTypes$lambda_1(x, p) {
    return formatRounded(x * 100, p);
  }
  function formatTypes$lambda_2(x, f) {
    return toStringDigits(x, 10);
  }
  function formatTypes$lambda_3(x, p) {
    return formatRounded(x, p);
  }
  function formatTypes$lambda_4(x, p) {
    return formatPrefixAuto(x, p);
  }
  function formatTypes$lambda_5(x, p) {
    return toPrecision(x, p);
  }
  function formatTypes$lambda_6(x, p) {
    return toExponential(x, p);
  }
  function formatTypes$lambda_7(x, f) {
    return toStringDigits(x, 2);
  }
  function formatTypes$lambda_8(x, f) {
    return toStringDigits(x, 8);
  }
  function formatTypes$lambda_9(x, f) {
    return toStringDigits(x, 16).toUpperCase();
  }
  function formatTypes$lambda_10(x, f) {
    return toStringDigits(x, 16);
  }
  function formatTypes$lambda_11(x, p) {
    return formatDefault(x, p);
  }
  function formatTypes(type) {
    switch (type.name) {
      case 'FIXED_POINT':
        return formatTypes$lambda;
      case 'PERCENT':
        return formatTypes$lambda_0;
      case 'PERCENT_ROUNDED':
        return formatTypes$lambda_1;
      case 'DECIMAL_ROUNDED':
        return formatTypes$lambda_2;
      case 'DECIMAL':
        return formatTypes$lambda_3;
      case 'DECIMAL_WITH_SI':
        return formatTypes$lambda_4;
      case 'DECIMAL_OR_EXPONENT':
        return formatTypes$lambda_5;
      case 'EXPONENT':
        return formatTypes$lambda_6;
      case 'BINARY':
        return formatTypes$lambda_7;
      case 'OCTAL':
        return formatTypes$lambda_8;
      case 'HEX_UPPERCASE':
        return formatTypes$lambda_9;
      case 'HEX_LOWERCASE':
        return formatTypes$lambda_10;
      case 'NONE':
        return formatTypes$lambda_11;
      default:return Kotlin.noWhenBranchMatched();
    }
  }
  function formatDefault(x, p) {
    var tmp$;
    var newX = toPrecision(x, p);
    var i0 = -1;
    var i1 = 0;
    tmp$ = newX.length;
    loop: for (var i = 1; i < tmp$; i++) {
      var c = newX.charCodeAt(i);
      switch (c) {
        case 46:
          i0 = i;
          i1 = i;
          break;
        case 48:
          if (i0 === 0)
            i0 = i;
          i1 = i;
          break;
        case 101:
          break loop;
        default:if (i0 > 0)
            i0 = 0;
          break;
      }
    }
    return i0 > 0 ? slice(newX, until(0, i0)) + slice(newX, until(i1 + 1 | 0, newX.length)) : newX;
  }
  function formatRounded(x, p) {
    var tmp$, tmp$_0;
    tmp$ = formatDecimal(x, p);
    if (tmp$ == null) {
      return x.toString();
    }
    var ce = tmp$;
    if (ce.exponent < 0) {
      tmp$_0 = padEnd('0.', (-ce.exponent | 0) + 1 | 0, 48) + ce.coefficient;
    }
     else {
      if (ce.coefficient.length > (ce.exponent + 1 | 0)) {
        tmp$_0 = slice(ce.coefficient, new IntRange(0, ce.exponent)) + '.' + slice(ce.coefficient, until(ce.exponent + 1 | 0, ce.coefficient.length));
      }
       else {
        tmp$_0 = ce.coefficient + padStart('', ce.exponent - ce.coefficient.length + 1 | 0, 48);
      }
    }
    return tmp$_0;
  }
  function formatDecimal(x, p) {
    if (p === void 0)
      p = 0;
    var newX = toExponential_0(x);
    if (p !== 0) {
      newX = toExponential(x, p - 1 | 0);
    }
    var index = indexOf(newX, 'e');
    if (index < 0)
      return null;
    var coefficient = slice(newX, until(0, index));
    var tmp$;
    if (coefficient.length > 1) {
      var $receiver = coefficient.charCodeAt(0);
      var other = slice(coefficient, until(2, coefficient.length));
      tmp$ = String.fromCharCode($receiver) + other;
    }
     else
      tmp$ = coefficient;
    var decimal = tmp$;
    var parsePrecision = toIntOrNull(slice(newX, until(index + 1 | 0, newX.length)));
    var precision = parsePrecision != null ? parsePrecision : 0;
    return new CoefficientExponent(decimal, precision);
  }
  function formatPrefixAuto(x, p) {
    if (p === void 0)
      p = 0;
    var tmp$, tmp$_0;
    tmp$ = formatDecimal(x, p);
    if (tmp$ == null) {
      return x.toString();
    }
    var ce = tmp$;
    var x_0 = ce.exponent / 3.0;
    prefixExponent = coerceIn(numberToInt(Math_0.floor(x_0)), -8, 8) * 3 | 0;
    var i = ce.exponent - prefixExponent + 1 | 0;
    var n = ce.coefficient.length;
    if (i === n)
      tmp$_0 = ce.coefficient;
    else if (i > n)
      tmp$_0 = padEnd(ce.coefficient, i, 48);
    else if (i > 0)
      tmp$_0 = slice(ce.coefficient, until(0, i)) + '.' + slice(ce.coefficient, until(i, ce.coefficient.length));
    else {
      var tmp$_1 = padEnd('0.', 2 - i | 0, 48);
      var b = p + i - 1 | 0;
      tmp$_0 = tmp$_1 + ensureNotNull(formatDecimal(x, Math_0.max(0, b))).coefficient;
    }
    return tmp$_0;
  }
  function precisionFixed(step) {
    var b = -exponent(Math_0.abs(step)) | 0;
    return Math_0.max(0, b);
  }
  function precisionPrefix(step, value) {
    var x = exponent(value) / 3.0;
    return coerceAtLeast((coerceIn(numberToInt(Math_0.floor(x)), -8, 8) * 3 | 0) - exponent(Math_0.abs(step)) | 0, 0);
  }
  function precisionRound(step, max) {
    var newStep = Math_0.abs(step);
    var newMax = Math_0.abs(max) - step;
    var b = exponent(newMax) - exponent(newStep) | 0;
    return Math_0.max(0, b) + 1 | 0;
  }
  function FormatSpec(fill, align, sign, symbol, zero, width, groupSeparation, precision, type) {
    if (fill === void 0)
      fill = 32;
    if (align === void 0)
      align = Align$RIGHT_getInstance();
    if (sign === void 0)
      sign = Sign$MINUS_getInstance();
    if (symbol === void 0)
      symbol = null;
    if (zero === void 0)
      zero = false;
    if (width === void 0)
      width = null;
    if (groupSeparation === void 0)
      groupSeparation = false;
    if (precision === void 0)
      precision = null;
    if (type === void 0)
      type = Type$NONE_getInstance();
    this.fill = toBoxedChar(fill);
    this.align = align;
    this.sign = sign;
    this.symbol = symbol;
    this.zero = zero;
    this.width = width;
    this.groupSeparation = groupSeparation;
    this.precision = precision;
    this.type = type;
  }
  FormatSpec.prototype.toString = function () {
    var tmp$ = String.fromCharCode(unboxChar(this.fill)) + this.align + this.sign + (this.symbol == null ? '' : this.symbol.c_8be2vx$) + (this.zero ? '0' : '');
    var tmp$_0;
    if (this.width == null)
      tmp$_0 = '';
    else {
      var b = this.width;
      tmp$_0 = Math_0.max(1, b);
    }
    var tmp$_1 = tmp$ + tmp$_0.toString() + (this.groupSeparation ? ',' : '');
    var tmp$_2;
    if (this.precision == null)
      tmp$_2 = '';
    else {
      var b_0 = this.precision;
      tmp$_2 = '.' + toString(Math_0.max(0, b_0));
    }
    return tmp$_1 + tmp$_2 + this.type.toString();
  };
  FormatSpec.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'FormatSpec',
    interfaces: []
  };
  FormatSpec.prototype.component1 = function () {
    return this.fill;
  };
  FormatSpec.prototype.component2 = function () {
    return this.align;
  };
  FormatSpec.prototype.component3 = function () {
    return this.sign;
  };
  FormatSpec.prototype.component4 = function () {
    return this.symbol;
  };
  FormatSpec.prototype.component5 = function () {
    return this.zero;
  };
  FormatSpec.prototype.component6 = function () {
    return this.width;
  };
  FormatSpec.prototype.component7 = function () {
    return this.groupSeparation;
  };
  FormatSpec.prototype.component8 = function () {
    return this.precision;
  };
  FormatSpec.prototype.component9 = function () {
    return this.type;
  };
  FormatSpec.prototype.copy_xzrjoe$ = function (fill, align, sign, symbol, zero, width, groupSeparation, precision, type) {
    return new FormatSpec(fill === void 0 ? this.fill : fill, align === void 0 ? this.align : align, sign === void 0 ? this.sign : sign, symbol === void 0 ? this.symbol : symbol, zero === void 0 ? this.zero : zero, width === void 0 ? this.width : width, groupSeparation === void 0 ? this.groupSeparation : groupSeparation, precision === void 0 ? this.precision : precision, type === void 0 ? this.type : type);
  };
  FormatSpec.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.fill) | 0;
    result = result * 31 + Kotlin.hashCode(this.align) | 0;
    result = result * 31 + Kotlin.hashCode(this.sign) | 0;
    result = result * 31 + Kotlin.hashCode(this.symbol) | 0;
    result = result * 31 + Kotlin.hashCode(this.zero) | 0;
    result = result * 31 + Kotlin.hashCode(this.width) | 0;
    result = result * 31 + Kotlin.hashCode(this.groupSeparation) | 0;
    result = result * 31 + Kotlin.hashCode(this.precision) | 0;
    result = result * 31 + Kotlin.hashCode(this.type) | 0;
    return result;
  };
  FormatSpec.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.fill, other.fill) && Kotlin.equals(this.align, other.align) && Kotlin.equals(this.sign, other.sign) && Kotlin.equals(this.symbol, other.symbol) && Kotlin.equals(this.zero, other.zero) && Kotlin.equals(this.width, other.width) && Kotlin.equals(this.groupSeparation, other.groupSeparation) && Kotlin.equals(this.precision, other.precision) && Kotlin.equals(this.type, other.type)))));
  };
  function specify$readType(closure$groupSeparation, closure$type, closure$match) {
    return function (string) {
      var tmp$;
      if (equals(string, 'n')) {
        closure$groupSeparation.v = true;
        closure$type.v = Type$DECIMAL_OR_EXPONENT_getInstance();
      }
       else {
        var tmp$_0 = closure$type;
        var $receiver = Type$values();
        var firstOrNull$result;
        firstOrNull$break: do {
          var tmp$_1;
          for (tmp$_1 = 0; tmp$_1 !== $receiver.length; ++tmp$_1) {
            var element = $receiver[tmp$_1];
            if (equals(element.c_8be2vx$, closure$match.get_za3lpa$(9))) {
              firstOrNull$result = element;
              break firstOrNull$break;
            }
          }
          firstOrNull$result = null;
        }
         while (false);
        tmp$_0.v = (tmp$ = firstOrNull$result) != null ? tmp$ : Type$NONE_getInstance();
      }
    };
  }
  function specify(specifier) {
    var fill = 32;
    var align = Align$RIGHT_getInstance();
    var sign = Sign$MINUS_getInstance();
    var symbol = null;
    var zero;
    var width = null;
    var groupSeparation = {v: null};
    var precision = null;
    var type = {v: Type$NONE_getInstance()};
    if (!formatRE.matches_6bul2c$(specifier))
      throw IllegalArgumentException_init('invalid format: ' + specifier);
    var match = ensureNotNull(formatRE.find_905azu$(specifier)).groupValues;
    var readType = specify$readType(groupSeparation, type, match);
    if (match.get_za3lpa$(1).length > 0)
      fill = match.get_za3lpa$(1).charCodeAt(0);
    if (match.get_za3lpa$(2).length > 0) {
      var $receiver = Align$values();
      var first$result;
      first$break: do {
        var tmp$;
        for (tmp$ = 0; tmp$ !== $receiver.length; ++tmp$) {
          var element = $receiver[tmp$];
          if (equals(element.c_8be2vx$, match.get_za3lpa$(2))) {
            first$result = element;
            break first$break;
          }
        }
        throw new NoSuchElementException_init('Array contains no element matching the predicate.');
      }
       while (false);
      align = first$result;
    }
    if (match.get_za3lpa$(3).length > 0) {
      var $receiver_0 = Sign$values();
      var first$result_0;
      first$break: do {
        var tmp$_0;
        for (tmp$_0 = 0; tmp$_0 !== $receiver_0.length; ++tmp$_0) {
          var element_0 = $receiver_0[tmp$_0];
          if (equals(element_0.c_8be2vx$, match.get_za3lpa$(3))) {
            first$result_0 = element_0;
            break first$break;
          }
        }
        throw new NoSuchElementException_init('Array contains no element matching the predicate.');
      }
       while (false);
      sign = first$result_0;
    }
    if (match.get_za3lpa$(4).length > 0) {
      var $receiver_1 = Symbol$values();
      var first$result_1;
      first$break: do {
        var tmp$_1;
        for (tmp$_1 = 0; tmp$_1 !== $receiver_1.length; ++tmp$_1) {
          var element_1 = $receiver_1[tmp$_1];
          if (equals(element_1.c_8be2vx$, match.get_za3lpa$(4))) {
            first$result_1 = element_1;
            break first$break;
          }
        }
        throw new NoSuchElementException_init('Array contains no element matching the predicate.');
      }
       while (false);
      symbol = first$result_1;
    }
    zero = equals(match.get_za3lpa$(5), '0');
    if (match.get_za3lpa$(6).length > 0 && toIntOrNull(match.get_za3lpa$(6)) != null)
      width = toInt(match.get_za3lpa$(6));
    groupSeparation.v = equals(match.get_za3lpa$(7), ',');
    var tmp$_2 = match.get_za3lpa$(8).length > 1;
    if (tmp$_2) {
      tmp$_2 = toIntOrNull(match.get_za3lpa$(8).substring(1)) != null;
    }
    if (tmp$_2) {
      precision = toInt(match.get_za3lpa$(8).substring(1));
    }
    readType(match.get_za3lpa$(9));
    if (zero || (fill === 48 && align === Align$RIGHT_WITHOUT_SIGN_getInstance())) {
      zero = true;
      fill = 48;
      align = Align$RIGHT_WITHOUT_SIGN_getInstance();
    }
    return new FormatSpec(fill, align, sign, symbol, zero, width, groupSeparation.v, precision, type.v);
  }
  var formatRE;
  function specify_0(type, fill, align, sign, symbol, zero, width, groupSeparation, precision) {
    if (type === void 0)
      type = Type$NONE_getInstance();
    if (fill === void 0)
      fill = 32;
    if (align === void 0)
      align = Align$RIGHT_getInstance();
    if (sign === void 0)
      sign = Sign$MINUS_getInstance();
    if (symbol === void 0)
      symbol = null;
    if (zero === void 0)
      zero = false;
    if (width === void 0)
      width = null;
    if (groupSeparation === void 0)
      groupSeparation = false;
    if (precision === void 0)
      precision = null;
    return new FormatSpec(fill, align, sign, symbol, zero, width, groupSeparation, precision, type);
  }
  function Symbol(name, ordinal, c) {
    Enum.call(this);
    this.c_8be2vx$ = c;
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function Symbol_initFields() {
    Symbol_initFields = function () {
    };
    Symbol$CURRENCY_instance = new Symbol('CURRENCY', 0, '$');
    Symbol$NUMBER_BASE_instance = new Symbol('NUMBER_BASE', 1, '#');
  }
  var Symbol$CURRENCY_instance;
  function Symbol$CURRENCY_getInstance() {
    Symbol_initFields();
    return Symbol$CURRENCY_instance;
  }
  var Symbol$NUMBER_BASE_instance;
  function Symbol$NUMBER_BASE_getInstance() {
    Symbol_initFields();
    return Symbol$NUMBER_BASE_instance;
  }
  Symbol.prototype.toString = function () {
    return this.c_8be2vx$;
  };
  Symbol.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Symbol',
    interfaces: [Enum]
  };
  function Symbol$values() {
    return [Symbol$CURRENCY_getInstance(), Symbol$NUMBER_BASE_getInstance()];
  }
  Symbol.values = Symbol$values;
  function Symbol$valueOf(name) {
    switch (name) {
      case 'CURRENCY':
        return Symbol$CURRENCY_getInstance();
      case 'NUMBER_BASE':
        return Symbol$NUMBER_BASE_getInstance();
      default:throwISE('No enum constant io.data2viz.format.Symbol.' + name);
    }
  }
  Symbol.valueOf_61zpoe$ = Symbol$valueOf;
  function Type(name, ordinal, c) {
    Enum.call(this);
    this.c_8be2vx$ = c;
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function Type_initFields() {
    Type_initFields = function () {
    };
    Type$NONE_instance = new Type('NONE', 0, ' ');
    Type$DECIMAL_instance = new Type('DECIMAL', 1, 'r');
    Type$DECIMAL_ROUNDED_instance = new Type('DECIMAL_ROUNDED', 2, 'd');
    Type$DECIMAL_WITH_SI_instance = new Type('DECIMAL_WITH_SI', 3, 's');
    Type$DECIMAL_OR_EXPONENT_instance = new Type('DECIMAL_OR_EXPONENT', 4, 'g');
    Type$EXPONENT_instance = new Type('EXPONENT', 5, 'e');
    Type$FIXED_POINT_instance = new Type('FIXED_POINT', 6, 'f');
    Type$PERCENT_instance = new Type('PERCENT', 7, '%');
    Type$PERCENT_ROUNDED_instance = new Type('PERCENT_ROUNDED', 8, 'p');
    Type$BINARY_instance = new Type('BINARY', 9, 'b');
    Type$OCTAL_instance = new Type('OCTAL', 10, 'o');
    Type$HEX_LOWERCASE_instance = new Type('HEX_LOWERCASE', 11, 'x');
    Type$HEX_UPPERCASE_instance = new Type('HEX_UPPERCASE', 12, 'X');
  }
  var Type$NONE_instance;
  function Type$NONE_getInstance() {
    Type_initFields();
    return Type$NONE_instance;
  }
  var Type$DECIMAL_instance;
  function Type$DECIMAL_getInstance() {
    Type_initFields();
    return Type$DECIMAL_instance;
  }
  var Type$DECIMAL_ROUNDED_instance;
  function Type$DECIMAL_ROUNDED_getInstance() {
    Type_initFields();
    return Type$DECIMAL_ROUNDED_instance;
  }
  var Type$DECIMAL_WITH_SI_instance;
  function Type$DECIMAL_WITH_SI_getInstance() {
    Type_initFields();
    return Type$DECIMAL_WITH_SI_instance;
  }
  var Type$DECIMAL_OR_EXPONENT_instance;
  function Type$DECIMAL_OR_EXPONENT_getInstance() {
    Type_initFields();
    return Type$DECIMAL_OR_EXPONENT_instance;
  }
  var Type$EXPONENT_instance;
  function Type$EXPONENT_getInstance() {
    Type_initFields();
    return Type$EXPONENT_instance;
  }
  var Type$FIXED_POINT_instance;
  function Type$FIXED_POINT_getInstance() {
    Type_initFields();
    return Type$FIXED_POINT_instance;
  }
  var Type$PERCENT_instance;
  function Type$PERCENT_getInstance() {
    Type_initFields();
    return Type$PERCENT_instance;
  }
  var Type$PERCENT_ROUNDED_instance;
  function Type$PERCENT_ROUNDED_getInstance() {
    Type_initFields();
    return Type$PERCENT_ROUNDED_instance;
  }
  var Type$BINARY_instance;
  function Type$BINARY_getInstance() {
    Type_initFields();
    return Type$BINARY_instance;
  }
  var Type$OCTAL_instance;
  function Type$OCTAL_getInstance() {
    Type_initFields();
    return Type$OCTAL_instance;
  }
  var Type$HEX_LOWERCASE_instance;
  function Type$HEX_LOWERCASE_getInstance() {
    Type_initFields();
    return Type$HEX_LOWERCASE_instance;
  }
  var Type$HEX_UPPERCASE_instance;
  function Type$HEX_UPPERCASE_getInstance() {
    Type_initFields();
    return Type$HEX_UPPERCASE_instance;
  }
  Type.prototype.toString = function () {
    return this === Type$NONE_getInstance() ? '' : this.c_8be2vx$;
  };
  Type.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Type',
    interfaces: [Enum]
  };
  function Type$values() {
    return [Type$NONE_getInstance(), Type$DECIMAL_getInstance(), Type$DECIMAL_ROUNDED_getInstance(), Type$DECIMAL_WITH_SI_getInstance(), Type$DECIMAL_OR_EXPONENT_getInstance(), Type$EXPONENT_getInstance(), Type$FIXED_POINT_getInstance(), Type$PERCENT_getInstance(), Type$PERCENT_ROUNDED_getInstance(), Type$BINARY_getInstance(), Type$OCTAL_getInstance(), Type$HEX_LOWERCASE_getInstance(), Type$HEX_UPPERCASE_getInstance()];
  }
  Type.values = Type$values;
  function Type$valueOf(name) {
    switch (name) {
      case 'NONE':
        return Type$NONE_getInstance();
      case 'DECIMAL':
        return Type$DECIMAL_getInstance();
      case 'DECIMAL_ROUNDED':
        return Type$DECIMAL_ROUNDED_getInstance();
      case 'DECIMAL_WITH_SI':
        return Type$DECIMAL_WITH_SI_getInstance();
      case 'DECIMAL_OR_EXPONENT':
        return Type$DECIMAL_OR_EXPONENT_getInstance();
      case 'EXPONENT':
        return Type$EXPONENT_getInstance();
      case 'FIXED_POINT':
        return Type$FIXED_POINT_getInstance();
      case 'PERCENT':
        return Type$PERCENT_getInstance();
      case 'PERCENT_ROUNDED':
        return Type$PERCENT_ROUNDED_getInstance();
      case 'BINARY':
        return Type$BINARY_getInstance();
      case 'OCTAL':
        return Type$OCTAL_getInstance();
      case 'HEX_LOWERCASE':
        return Type$HEX_LOWERCASE_getInstance();
      case 'HEX_UPPERCASE':
        return Type$HEX_UPPERCASE_getInstance();
      default:throwISE('No enum constant io.data2viz.format.Type.' + name);
    }
  }
  Type.valueOf_61zpoe$ = Type$valueOf;
  function get_isNumberBase($receiver) {
    return $receiver != null && (equals($receiver, Type$BINARY_getInstance()) || equals($receiver, Type$OCTAL_getInstance()) || equals($receiver, Type$HEX_UPPERCASE_getInstance()) || equals($receiver, Type$HEX_LOWERCASE_getInstance()));
  }
  function get_isPercent($receiver) {
    return $receiver != null && (equals($receiver, Type$PERCENT_getInstance()) || equals($receiver, Type$PERCENT_ROUNDED_getInstance()));
  }
  function get_maybeSuffix($receiver) {
    return $receiver != null && (equals($receiver, Type$DECIMAL_ROUNDED_getInstance()) || equals($receiver, Type$EXPONENT_getInstance()) || equals($receiver, Type$FIXED_POINT_getInstance()) || equals($receiver, Type$DECIMAL_OR_EXPONENT_getInstance()) || equals($receiver, Type$PERCENT_ROUNDED_getInstance()) || equals($receiver, Type$DECIMAL_getInstance()) || equals($receiver, Type$DECIMAL_WITH_SI_getInstance()) || equals($receiver, Type$PERCENT_getInstance()));
  }
  var gprs;
  function Sign(name, ordinal, c) {
    Enum.call(this);
    this.c_8be2vx$ = c;
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function Sign_initFields() {
    Sign_initFields = function () {
    };
    Sign$MINUS_instance = new Sign('MINUS', 0, '-');
    Sign$PLUS_instance = new Sign('PLUS', 1, '+');
    Sign$PARENTHESES_instance = new Sign('PARENTHESES', 2, '(');
    Sign$SPACE_instance = new Sign('SPACE', 3, ' ');
  }
  var Sign$MINUS_instance;
  function Sign$MINUS_getInstance() {
    Sign_initFields();
    return Sign$MINUS_instance;
  }
  var Sign$PLUS_instance;
  function Sign$PLUS_getInstance() {
    Sign_initFields();
    return Sign$PLUS_instance;
  }
  var Sign$PARENTHESES_instance;
  function Sign$PARENTHESES_getInstance() {
    Sign_initFields();
    return Sign$PARENTHESES_instance;
  }
  var Sign$SPACE_instance;
  function Sign$SPACE_getInstance() {
    Sign_initFields();
    return Sign$SPACE_instance;
  }
  Sign.prototype.toString = function () {
    return this.c_8be2vx$;
  };
  Sign.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Sign',
    interfaces: [Enum]
  };
  function Sign$values() {
    return [Sign$MINUS_getInstance(), Sign$PLUS_getInstance(), Sign$PARENTHESES_getInstance(), Sign$SPACE_getInstance()];
  }
  Sign.values = Sign$values;
  function Sign$valueOf(name) {
    switch (name) {
      case 'MINUS':
        return Sign$MINUS_getInstance();
      case 'PLUS':
        return Sign$PLUS_getInstance();
      case 'PARENTHESES':
        return Sign$PARENTHESES_getInstance();
      case 'SPACE':
        return Sign$SPACE_getInstance();
      default:throwISE('No enum constant io.data2viz.format.Sign.' + name);
    }
  }
  Sign.valueOf_61zpoe$ = Sign$valueOf;
  function Align(name, ordinal, c) {
    Enum.call(this);
    this.c_8be2vx$ = c;
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function Align_initFields() {
    Align_initFields = function () {
    };
    Align$RIGHT_instance = new Align('RIGHT', 0, '>');
    Align$RIGTH_instance = new Align('RIGTH', 1, '>');
    Align$LEFT_instance = new Align('LEFT', 2, '<');
    Align$CENTER_instance = new Align('CENTER', 3, '^');
    Align$RIGHT_WITHOUT_SIGN_instance = new Align('RIGHT_WITHOUT_SIGN', 4, '=');
  }
  var Align$RIGHT_instance;
  function Align$RIGHT_getInstance() {
    Align_initFields();
    return Align$RIGHT_instance;
  }
  var Align$RIGTH_instance;
  function Align$RIGTH_getInstance() {
    Align_initFields();
    return Align$RIGTH_instance;
  }
  var Align$LEFT_instance;
  function Align$LEFT_getInstance() {
    Align_initFields();
    return Align$LEFT_instance;
  }
  var Align$CENTER_instance;
  function Align$CENTER_getInstance() {
    Align_initFields();
    return Align$CENTER_instance;
  }
  var Align$RIGHT_WITHOUT_SIGN_instance;
  function Align$RIGHT_WITHOUT_SIGN_getInstance() {
    Align_initFields();
    return Align$RIGHT_WITHOUT_SIGN_instance;
  }
  Align.prototype.toString = function () {
    return this.c_8be2vx$;
  };
  Align.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Align',
    interfaces: [Enum]
  };
  function Align$values() {
    return [Align$RIGHT_getInstance(), Align$RIGTH_getInstance(), Align$LEFT_getInstance(), Align$CENTER_getInstance(), Align$RIGHT_WITHOUT_SIGN_getInstance()];
  }
  Align.values = Align$values;
  function Align$valueOf(name) {
    switch (name) {
      case 'RIGHT':
        return Align$RIGHT_getInstance();
      case 'RIGTH':
        return Align$RIGTH_getInstance();
      case 'LEFT':
        return Align$LEFT_getInstance();
      case 'CENTER':
        return Align$CENTER_getInstance();
      case 'RIGHT_WITHOUT_SIGN':
        return Align$RIGHT_WITHOUT_SIGN_getInstance();
      default:throwISE('No enum constant io.data2viz.format.Align.' + name);
    }
  }
  Align.valueOf_61zpoe$ = Align$valueOf;
  var arabicNumerals;
  function Locale(decimalSeparator, grouping, groupSeparator, currency, numerals, percent) {
    if (decimalSeparator === void 0)
      decimalSeparator = '.';
    if (grouping === void 0)
      grouping = listOf_0(3);
    if (groupSeparator === void 0)
      groupSeparator = ',';
    if (currency === void 0)
      currency = listOf(['$', '']);
    if (numerals === void 0)
      numerals = null;
    if (percent === void 0)
      percent = '%';
    this.decimalSeparator = decimalSeparator;
    this.grouping = grouping;
    this.groupSeparator = groupSeparator;
    this.currency = currency;
    this.numerals = numerals;
    this.percent = percent;
  }
  Locale.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Locale',
    interfaces: []
  };
  Locale.prototype.component1 = function () {
    return this.decimalSeparator;
  };
  Locale.prototype.component2 = function () {
    return this.grouping;
  };
  Locale.prototype.component3 = function () {
    return this.groupSeparator;
  };
  Locale.prototype.component4 = function () {
    return this.currency;
  };
  Locale.prototype.component5 = function () {
    return this.numerals;
  };
  Locale.prototype.component6 = function () {
    return this.percent;
  };
  Locale.prototype.copy_v9nw71$ = function (decimalSeparator, grouping, groupSeparator, currency, numerals, percent) {
    return new Locale(decimalSeparator === void 0 ? this.decimalSeparator : decimalSeparator, grouping === void 0 ? this.grouping : grouping, groupSeparator === void 0 ? this.groupSeparator : groupSeparator, currency === void 0 ? this.currency : currency, numerals === void 0 ? this.numerals : numerals, percent === void 0 ? this.percent : percent);
  };
  Locale.prototype.toString = function () {
    return 'Locale(decimalSeparator=' + Kotlin.toString(this.decimalSeparator) + (', grouping=' + Kotlin.toString(this.grouping)) + (', groupSeparator=' + Kotlin.toString(this.groupSeparator)) + (', currency=' + Kotlin.toString(this.currency)) + (', numerals=' + Kotlin.toString(this.numerals)) + (', percent=' + Kotlin.toString(this.percent)) + ')';
  };
  Locale.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.decimalSeparator) | 0;
    result = result * 31 + Kotlin.hashCode(this.grouping) | 0;
    result = result * 31 + Kotlin.hashCode(this.groupSeparator) | 0;
    result = result * 31 + Kotlin.hashCode(this.currency) | 0;
    result = result * 31 + Kotlin.hashCode(this.numerals) | 0;
    result = result * 31 + Kotlin.hashCode(this.percent) | 0;
    return result;
  };
  Locale.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.decimalSeparator, other.decimalSeparator) && Kotlin.equals(this.grouping, other.grouping) && Kotlin.equals(this.groupSeparator, other.groupSeparator) && Kotlin.equals(this.currency, other.currency) && Kotlin.equals(this.numerals, other.numerals) && Kotlin.equals(this.percent, other.percent)))));
  };
  function Locales() {
    Locales$Companion_getInstance();
  }
  function Locales$Companion() {
    Locales$Companion_instance = this;
  }
  Locales$Companion.prototype.locale_t1wh6l$ = function (decimalSeparator, groupSeparator, currency, numerals, grouping, percent) {
    if (decimalSeparator === void 0)
      decimalSeparator = '.';
    if (groupSeparator === void 0)
      groupSeparator = ',';
    if (currency === void 0)
      currency = listOf(['$', '']);
    if (numerals === void 0)
      numerals = null;
    if (grouping === void 0)
      grouping = listOf_0(3);
    if (percent === void 0)
      percent = '%';
    return new Locale(decimalSeparator, grouping, groupSeparator, currency, numerals, percent);
  };
  Locales$Companion.prototype.ar_001 = function () {
    return this.locale_t1wh6l$('\u066B', '\u066C', listOf(['', '']), arabicNumerals);
  };
  Locales$Companion.prototype.ar_AE = function () {
    return this.ar_001().copy_v9nw71$(void 0, void 0, void 0, listOf(['', ' \u062F.\u0625.']));
  };
  Locales$Companion.prototype.ar_BH = function () {
    return this.ar_001().copy_v9nw71$(void 0, void 0, void 0, listOf(['', ' \u062F.\u0628.']));
  };
  Locales$Companion.prototype.ar_DJ = function () {
    return this.ar_001().copy_v9nw71$(void 0, void 0, void 0, listOf(['\u200FFdj ', '']));
  };
  Locales$Companion.prototype.ar_DZ = function () {
    return this.locale_t1wh6l$(',', '.', listOf(['\u062F.\u062C. ', '']));
  };
  Locales$Companion.prototype.ar_EG = function () {
    return this.ar_001().copy_v9nw71$(void 0, void 0, void 0, listOf(['', ' \u062C.\u0645.']));
  };
  Locales$Companion.prototype.ar_EH = function () {
    return this.locale_t1wh6l$('.', ',', listOf(['\u062F.\u0645. ', '']));
  };
  Locales$Companion.prototype.ar_ER = function () {
    return this.ar_001().copy_v9nw71$(void 0, void 0, void 0, listOf(['Nfk ', '']));
  };
  Locales$Companion.prototype.ar_IL = function () {
    return this.ar_001().copy_v9nw71$(void 0, void 0, void 0, listOf(['\u20AA ', '']));
  };
  Locales$Companion.prototype.ar_IQ = function () {
    return this.ar_001().copy_v9nw71$(void 0, void 0, void 0, listOf(['', ' \u062F.\u0639.']));
  };
  Locales$Companion.prototype.ar_JO = function () {
    return this.ar_001().copy_v9nw71$(void 0, void 0, void 0, listOf(['', ' \u062F.\u0623.']));
  };
  Locales$Companion.prototype.ar_KM = function () {
    return this.ar_001().copy_v9nw71$(void 0, void 0, void 0, listOf(['', ' \u0641.\u062C.\u0642.']));
  };
  Locales$Companion.prototype.ar_KW = function () {
    return this.ar_001().copy_v9nw71$(void 0, void 0, void 0, listOf(['', ' \u062F.\u0643.']));
  };
  Locales$Companion.prototype.ar_LB = function () {
    return this.ar_001().copy_v9nw71$(void 0, void 0, void 0, listOf(['', ' \u0644.\u0644.']));
  };
  Locales$Companion.prototype.ar_LY = function () {
    return this.ar_001().copy_v9nw71$(',', void 0, '.', listOf(['\u062F.\u0644. ', '']));
  };
  Locales$Companion.prototype.ar_MA = function () {
    return this.locale_t1wh6l$(',', '.', listOf(['\u062F.\u0645. ', '']));
  };
  Locales$Companion.prototype.ar_MR = function () {
    return this.ar_001().copy_v9nw71$(void 0, void 0, void 0, listOf(['', ' \u0623.\u0645.']));
  };
  Locales$Companion.prototype.ar_OM = function () {
    return this.ar_001().copy_v9nw71$(void 0, void 0, void 0, listOf(['', ' \u0631.\u0639.']));
  };
  Locales$Companion.prototype.ar_PS = function () {
    return this.ar_001().copy_v9nw71$(void 0, void 0, void 0, listOf(['\u20AA ', '']));
  };
  Locales$Companion.prototype.ar_QA = function () {
    return this.ar_001().copy_v9nw71$(void 0, void 0, void 0, listOf(['', ' \u0631.\u0642.']));
  };
  Locales$Companion.prototype.ar_SA = function () {
    return this.ar_001().copy_v9nw71$(void 0, void 0, void 0, listOf(['', ' \u0631.\u0633.']));
  };
  Locales$Companion.prototype.ar_SD = function () {
    return this.ar_001().copy_v9nw71$(void 0, void 0, void 0, listOf(['', ' \u062C.\u0633.']));
  };
  Locales$Companion.prototype.ar_SO = function () {
    return this.ar_001().copy_v9nw71$(void 0, void 0, void 0, listOf(['\u200FS ', '']));
  };
  Locales$Companion.prototype.ar_SS = function () {
    return this.ar_001().copy_v9nw71$(void 0, void 0, void 0, listOf(['\xA3 ', '']));
  };
  Locales$Companion.prototype.ar_SY = function () {
    return this.ar_001().copy_v9nw71$(void 0, void 0, void 0, listOf(['', ' \u0644.\u0633.']));
  };
  Locales$Companion.prototype.ar_TD = function () {
    return this.ar_001().copy_v9nw71$(void 0, void 0, void 0, listOf(['\u200FFCFA ', '']));
  };
  Locales$Companion.prototype.ar_TN = function () {
    return this.locale_t1wh6l$(',', '.', listOf(['\u062F.\u062A. ', '']));
  };
  Locales$Companion.prototype.ar_YE = function () {
    return this.ar_001().copy_v9nw71$(void 0, void 0, void 0, listOf(['', ' \u0631.\u0649.']));
  };
  Locales$Companion.prototype.ca_ES = function () {
    return this.locale_t1wh6l$('.', '.', listOf(['', '\xA0\u20AC']));
  };
  Locales$Companion.prototype.cs_CZ = function () {
    return this.locale_t1wh6l$(',', '\xA0', listOf(['', '\xA0K\u010D']));
  };
  Locales$Companion.prototype.de_CH = function () {
    return this.locale_t1wh6l$(',', "'", listOf(['', '\xA0CHF']));
  };
  Locales$Companion.prototype.de_DE = function () {
    return this.locale_t1wh6l$(',', '.', listOf(['', '\xA0\u20AC']));
  };
  Locales$Companion.prototype.en_CA = function () {
    return this.locale_t1wh6l$('.', ',', listOf(['$', '']));
  };
  Locales$Companion.prototype.en_GB = function () {
    return this.locale_t1wh6l$('.', ',', listOf(['\xA3', '']));
  };
  Locales$Companion.prototype.en_IN = function () {
    return this.locale_t1wh6l$('.', ',', listOf(['\u20B9', '']), void 0, listOf([3, 2, 2, 2, 2, 2, 2, 2, 2, 2]));
  };
  Locales$Companion.prototype.en_US = function () {
    return this.locale_t1wh6l$('.', ',', listOf(['$', '']));
  };
  Locales$Companion.prototype.es_ES = function () {
    return this.locale_t1wh6l$(',', '.', listOf(['', '\xA0\u20AC']));
  };
  Locales$Companion.prototype.es_MX = function () {
    return this.locale_t1wh6l$('.', ',', listOf(['$', '']));
  };
  Locales$Companion.prototype.fi_FI = function () {
    return this.locale_t1wh6l$(',', '\xA0', listOf(['', '\xA0\u20AC']));
  };
  Locales$Companion.prototype.fr_CA = function () {
    return this.locale_t1wh6l$(',', '\xA0', listOf(['', '$']));
  };
  Locales$Companion.prototype.fr_FR = function () {
    return this.locale_t1wh6l$(',', '\xA0', listOf(['', '\xA0\u20AC']));
  };
  Locales$Companion.prototype.he_IL = function () {
    return this.locale_t1wh6l$('.', ',', listOf(['\u20AA', '']));
  };
  Locales$Companion.prototype.hu_HU = function () {
    return this.locale_t1wh6l$(',', '\xA0', listOf(['', '\xA0Ft']));
  };
  Locales$Companion.prototype.it_IT = function () {
    return this.locale_t1wh6l$(',', '.', listOf(['\u20AC', '']));
  };
  Locales$Companion.prototype.ja_JP = function () {
    return this.locale_t1wh6l$('.', ',', listOf(['', '\u5186']));
  };
  Locales$Companion.prototype.ko_KR = function () {
    return this.locale_t1wh6l$(',', '', listOf(['\u20A9', '']));
  };
  Locales$Companion.prototype.mk_MK = function () {
    return this.locale_t1wh6l$(',', '.', listOf(['', '\xA0\u0434\u0435\u043D.']));
  };
  Locales$Companion.prototype.nl_NL = function () {
    return this.locale_t1wh6l$(',', '.', listOf(['\u20AC\xA0', '']));
  };
  Locales$Companion.prototype.pl_PL = function () {
    return this.locale_t1wh6l$(',', '.', listOf(['', 'z\u0142']));
  };
  Locales$Companion.prototype.pt_BR = function () {
    return this.locale_t1wh6l$(',', '.', listOf(['R$', '']));
  };
  Locales$Companion.prototype.ru_RU = function () {
    return this.locale_t1wh6l$(',', '\xA0', listOf(['', '\xA0\u0440\u0443\u0431.']));
  };
  Locales$Companion.prototype.sv_SE = function () {
    return this.locale_t1wh6l$(',', '\xA0', listOf(['', 'SEK']));
  };
  Locales$Companion.prototype.uk_UA = function () {
    return this.locale_t1wh6l$(',', '\xA0', listOf(['', '\xA0\u20B4.']));
  };
  Locales$Companion.prototype.zh_CN = function () {
    return this.locale_t1wh6l$('.', ',', listOf(['\xA5', '']));
  };
  Locales$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var Locales$Companion_instance = null;
  function Locales$Companion_getInstance() {
    if (Locales$Companion_instance === null) {
      new Locales$Companion();
    }
    return Locales$Companion_instance;
  }
  Locales.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Locales',
    interfaces: []
  };
  function toStringDigits($receiver, radix) {
    return round($receiver).toString(radix);
  }
  function toFixed($receiver, digits) {
    return $receiver.toFixed(digits);
  }
  function toExponential($receiver, digits) {
    return $receiver.toExponential(digits);
  }
  function toExponential_0($receiver) {
    return $receiver.toExponential();
  }
  function toPrecision($receiver, digits) {
    return $receiver.toPrecision(digits);
  }
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$format = package$data2viz.format || (package$data2viz.format = {});
  package$format.CoefficientExponent = CoefficientExponent;
  package$format.formatter_61zpoe$ = formatter;
  package$format.formatter_g7oit5$ = formatter_0;
  package$format.formatter_nrgtgi$ = formatter_1;
  package$format.formatter_8cygt9$ = formatter_2;
  package$format.numerals_g7oit5$ = numerals;
  package$format.formatPrefix_io5o9c$ = formatPrefix;
  package$format.formatPrefix_rtibat$ = formatPrefix_0;
  package$format.formatRounded_12fank$ = formatRounded;
  package$format.formatDecimal_12fank$ = formatDecimal;
  package$format.precisionFixed_14dthe$ = precisionFixed;
  package$format.precisionPrefix_lu1900$ = precisionPrefix;
  package$format.precisionRound_lu1900$ = precisionRound;
  package$format.FormatSpec = FormatSpec;
  package$format.specify_61zpoe$ = specify;
  package$format.specify_nrgtgi$ = specify_0;
  Object.defineProperty(Symbol, 'CURRENCY', {
    get: Symbol$CURRENCY_getInstance
  });
  Object.defineProperty(Symbol, 'NUMBER_BASE', {
    get: Symbol$NUMBER_BASE_getInstance
  });
  package$format.Symbol = Symbol;
  Object.defineProperty(Type, 'NONE', {
    get: Type$NONE_getInstance
  });
  Object.defineProperty(Type, 'DECIMAL', {
    get: Type$DECIMAL_getInstance
  });
  Object.defineProperty(Type, 'DECIMAL_ROUNDED', {
    get: Type$DECIMAL_ROUNDED_getInstance
  });
  Object.defineProperty(Type, 'DECIMAL_WITH_SI', {
    get: Type$DECIMAL_WITH_SI_getInstance
  });
  Object.defineProperty(Type, 'DECIMAL_OR_EXPONENT', {
    get: Type$DECIMAL_OR_EXPONENT_getInstance
  });
  Object.defineProperty(Type, 'EXPONENT', {
    get: Type$EXPONENT_getInstance
  });
  Object.defineProperty(Type, 'FIXED_POINT', {
    get: Type$FIXED_POINT_getInstance
  });
  Object.defineProperty(Type, 'PERCENT', {
    get: Type$PERCENT_getInstance
  });
  Object.defineProperty(Type, 'PERCENT_ROUNDED', {
    get: Type$PERCENT_ROUNDED_getInstance
  });
  Object.defineProperty(Type, 'BINARY', {
    get: Type$BINARY_getInstance
  });
  Object.defineProperty(Type, 'OCTAL', {
    get: Type$OCTAL_getInstance
  });
  Object.defineProperty(Type, 'HEX_LOWERCASE', {
    get: Type$HEX_LOWERCASE_getInstance
  });
  Object.defineProperty(Type, 'HEX_UPPERCASE', {
    get: Type$HEX_UPPERCASE_getInstance
  });
  package$format.Type = Type;
  package$format.get_isNumberBase_vpro8z$ = get_isNumberBase;
  package$format.get_isPercent_vpro8z$ = get_isPercent;
  package$format.get_maybeSuffix_vpro8z$ = get_maybeSuffix;
  Object.defineProperty(package$format, 'gprs_8be2vx$', {
    get: function () {
      return gprs;
    }
  });
  Object.defineProperty(Sign, 'MINUS', {
    get: Sign$MINUS_getInstance
  });
  Object.defineProperty(Sign, 'PLUS', {
    get: Sign$PLUS_getInstance
  });
  Object.defineProperty(Sign, 'PARENTHESES', {
    get: Sign$PARENTHESES_getInstance
  });
  Object.defineProperty(Sign, 'SPACE', {
    get: Sign$SPACE_getInstance
  });
  package$format.Sign = Sign;
  Object.defineProperty(Align, 'RIGHT', {
    get: Align$RIGHT_getInstance
  });
  Object.defineProperty(Align, 'RIGTH', {
    get: Align$RIGTH_getInstance
  });
  Object.defineProperty(Align, 'LEFT', {
    get: Align$LEFT_getInstance
  });
  Object.defineProperty(Align, 'CENTER', {
    get: Align$CENTER_getInstance
  });
  Object.defineProperty(Align, 'RIGHT_WITHOUT_SIGN', {
    get: Align$RIGHT_WITHOUT_SIGN_getInstance
  });
  package$format.Align = Align;
  Object.defineProperty(package$format, 'arabicNumerals_8be2vx$', {
    get: function () {
      return arabicNumerals;
    }
  });
  package$format.Locale = Locale;
  Object.defineProperty(Locales, 'Companion', {
    get: Locales$Companion_getInstance
  });
  package$format.Locales = Locales;
  package$format.toStringDigits_5fv9zk$ = toStringDigits;
  package$format.toFixed_5fv9zk$ = toFixed;
  package$format.toExponential_5fv9zk$ = toExponential;
  package$format.toExponential_1zw1ma$ = toExponential_0;
  package$format.toPrecision_5fv9zk$ = toPrecision;
  prefixes = listOf(['y', 'z', 'a', 'f', 'p', 'n', '\xB5', 'm', '', 'k', 'M', 'G', 'T', 'P', 'E', 'Z', 'Y']);
  prefixExponent = 0;
  formatRE = Regex_init('^(?:(.)?([<>=^]))?([+\\-( ])?([$#])?(0)?(\\d+)?(,)?(\\.\\d+)?([a-z%])?$', RegexOption.IGNORE_CASE);
  gprs = listOf([Type$DECIMAL_OR_EXPONENT_getInstance(), Type$PERCENT_ROUNDED_getInstance(), Type$DECIMAL_getInstance(), Type$DECIMAL_WITH_SI_getInstance()]);
  arabicNumerals = ['\u0660', '\u0661', '\u0662', '\u0663', '\u0664', '\u0665', '\u0666', '\u0667', '\u0668', '\u0669'];
  Kotlin.defineModule('d2v-format-js', _);
  return _;
}));

//# sourceMappingURL=d2v-format-js.js.map
