(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin', 'd2v-time-js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'), require('d2v-time-js'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'd2v-timeFormat-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'd2v-timeFormat-js'.");
    }
    if (typeof this['d2v-time-js'] === 'undefined') {
      throw new Error("Error loading module 'd2v-timeFormat-js'. Its dependency 'd2v-time-js' was not found. Please, check whether 'd2v-time-js' is loaded prior to 'd2v-timeFormat-js'.");
    }
    root['d2v-timeFormat-js'] = factory(typeof this['d2v-timeFormat-js'] === 'undefined' ? {} : this['d2v-timeFormat-js'], kotlin, this['d2v-time-js']);
  }
}(this, function (_, Kotlin, $module$d2v_time_js) {
  'use strict';
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var date = $module$d2v_time_js.io.data2viz.time.date_ui44o2$;
  var ensureNotNull = Kotlin.ensureNotNull;
  var time = $module$d2v_time_js.io.data2viz.time;
  var toBoxedChar = Kotlin.toBoxedChar;
  var joinToString = Kotlin.kotlin.collections.joinToString_fmv235$;
  var unboxChar = Kotlin.unboxChar;
  var toInt = Kotlin.kotlin.text.toInt_pdl1vz$;
  var Regex_init = Kotlin.kotlin.text.Regex_init_61zpoe$;
  var abs = Kotlin.kotlin.math.abs_za3lpa$;
  var getCallableRef = Kotlin.getCallableRef;
  var Pair = Kotlin.kotlin.Pair;
  var mutableMapOf = Kotlin.kotlin.collections.mutableMapOf_qfcya0$;
  var mapOf = Kotlin.kotlin.collections.mapOf_qfcya0$;
  var until = Kotlin.kotlin.ranges.until_dqglrj$;
  var replace = Kotlin.kotlin.text.replace_680rmw$;
  var RegexOption = Kotlin.kotlin.text.RegexOption;
  var Regex_init_0 = Kotlin.kotlin.text.Regex_init_sb3q2$;
  var toMap = Kotlin.kotlin.collections.toMap_abgq59$;
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  var Map = Kotlin.kotlin.collections.Map;
  var throwCCE = Kotlin.throwCCE;
  var StringBuilder_init = Kotlin.kotlin.text.StringBuilder_init;
  var collectionSizeOrDefault = Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$;
  var ArrayList_init_0 = Kotlin.kotlin.collections.ArrayList_init_ww73n8$;
  var LinkedHashMap_init = Kotlin.kotlin.collections.LinkedHashMap_init_q3lmfv$;
  var listOf = Kotlin.kotlin.collections.listOf_i5x0yv$;
  var Kind_OBJECT = Kotlin.Kind.OBJECT;
  function ParseDate(year, month, day, hour, minute, second, millisecond, period, weekDay, weekNumberMonday, weekNumberSunday, zone) {
    if (year === void 0)
      year = null;
    if (month === void 0)
      month = null;
    if (day === void 0)
      day = null;
    if (hour === void 0)
      hour = null;
    if (minute === void 0)
      minute = null;
    if (second === void 0)
      second = null;
    if (millisecond === void 0)
      millisecond = null;
    if (period === void 0)
      period = null;
    if (weekDay === void 0)
      weekDay = null;
    if (weekNumberMonday === void 0)
      weekNumberMonday = null;
    if (weekNumberSunday === void 0)
      weekNumberSunday = null;
    if (zone === void 0)
      zone = null;
    this.year = year;
    this.month = month;
    this.day = day;
    this.hour = hour;
    this.minute = minute;
    this.second = second;
    this.millisecond = millisecond;
    this.period = period;
    this.weekDay = weekDay;
    this.weekNumberMonday = weekNumberMonday;
    this.weekNumberSunday = weekNumberSunday;
    this.zone = zone;
  }
  ParseDate.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ParseDate',
    interfaces: []
  };
  ParseDate.prototype.component1 = function () {
    return this.year;
  };
  ParseDate.prototype.component2 = function () {
    return this.month;
  };
  ParseDate.prototype.component3 = function () {
    return this.day;
  };
  ParseDate.prototype.component4 = function () {
    return this.hour;
  };
  ParseDate.prototype.component5 = function () {
    return this.minute;
  };
  ParseDate.prototype.component6 = function () {
    return this.second;
  };
  ParseDate.prototype.component7 = function () {
    return this.millisecond;
  };
  ParseDate.prototype.component8 = function () {
    return this.period;
  };
  ParseDate.prototype.component9 = function () {
    return this.weekDay;
  };
  ParseDate.prototype.component10 = function () {
    return this.weekNumberMonday;
  };
  ParseDate.prototype.component11 = function () {
    return this.weekNumberSunday;
  };
  ParseDate.prototype.component12 = function () {
    return this.zone;
  };
  ParseDate.prototype.copy_75sjqw$ = function (year, month, day, hour, minute, second, millisecond, period, weekDay, weekNumberMonday, weekNumberSunday, zone) {
    return new ParseDate(year === void 0 ? this.year : year, month === void 0 ? this.month : month, day === void 0 ? this.day : day, hour === void 0 ? this.hour : hour, minute === void 0 ? this.minute : minute, second === void 0 ? this.second : second, millisecond === void 0 ? this.millisecond : millisecond, period === void 0 ? this.period : period, weekDay === void 0 ? this.weekDay : weekDay, weekNumberMonday === void 0 ? this.weekNumberMonday : weekNumberMonday, weekNumberSunday === void 0 ? this.weekNumberSunday : weekNumberSunday, zone === void 0 ? this.zone : zone);
  };
  ParseDate.prototype.toString = function () {
    return 'ParseDate(year=' + Kotlin.toString(this.year) + (', month=' + Kotlin.toString(this.month)) + (', day=' + Kotlin.toString(this.day)) + (', hour=' + Kotlin.toString(this.hour)) + (', minute=' + Kotlin.toString(this.minute)) + (', second=' + Kotlin.toString(this.second)) + (', millisecond=' + Kotlin.toString(this.millisecond)) + (', period=' + Kotlin.toString(this.period)) + (', weekDay=' + Kotlin.toString(this.weekDay)) + (', weekNumberMonday=' + Kotlin.toString(this.weekNumberMonday)) + (', weekNumberSunday=' + Kotlin.toString(this.weekNumberSunday)) + (', zone=' + Kotlin.toString(this.zone)) + ')';
  };
  ParseDate.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.year) | 0;
    result = result * 31 + Kotlin.hashCode(this.month) | 0;
    result = result * 31 + Kotlin.hashCode(this.day) | 0;
    result = result * 31 + Kotlin.hashCode(this.hour) | 0;
    result = result * 31 + Kotlin.hashCode(this.minute) | 0;
    result = result * 31 + Kotlin.hashCode(this.second) | 0;
    result = result * 31 + Kotlin.hashCode(this.millisecond) | 0;
    result = result * 31 + Kotlin.hashCode(this.period) | 0;
    result = result * 31 + Kotlin.hashCode(this.weekDay) | 0;
    result = result * 31 + Kotlin.hashCode(this.weekNumberMonday) | 0;
    result = result * 31 + Kotlin.hashCode(this.weekNumberSunday) | 0;
    result = result * 31 + Kotlin.hashCode(this.zone) | 0;
    return result;
  };
  ParseDate.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.year, other.year) && Kotlin.equals(this.month, other.month) && Kotlin.equals(this.day, other.day) && Kotlin.equals(this.hour, other.hour) && Kotlin.equals(this.minute, other.minute) && Kotlin.equals(this.second, other.second) && Kotlin.equals(this.millisecond, other.millisecond) && Kotlin.equals(this.period, other.period) && Kotlin.equals(this.weekDay, other.weekDay) && Kotlin.equals(this.weekNumberMonday, other.weekNumberMonday) && Kotlin.equals(this.weekNumberSunday, other.weekNumberSunday) && Kotlin.equals(this.zone, other.zone)))));
  };
  function date_0(d) {
    var tmp$, tmp$_0, tmp$_1, tmp$_2, tmp$_3, tmp$_4;
    var date_0 = date((tmp$ = d.year) != null ? tmp$ : 0, (tmp$_0 = d.month) != null ? tmp$_0 : 1, 1, (tmp$_1 = d.hour) != null ? tmp$_1 : 0, (tmp$_2 = d.minute) != null ? tmp$_2 : 0, (tmp$_3 = d.second) != null ? tmp$_3 : 0, (tmp$_4 = d.millisecond) != null ? tmp$_4 : 0);
    if (d.day != null)
      date_0.plusDays_s8cxhz$(Kotlin.Long.fromInt(ensureNotNull(d.day)).subtract(Kotlin.Long.fromInt(1)));
    return date_0;
  }
  var defaultLocale;
  function autoFormat() {
    return defaultLocale.autoFormat();
  }
  function format(specifier) {
    return defaultLocale.format_61zpoe$(specifier);
  }
  function parse(specifier) {
    return defaultLocale.parse_61zpoe$(specifier);
  }
  function Locale(timeLocale) {
    if (timeLocale === void 0)
      timeLocale = Locales$Companion_getInstance().defaultLocale();
    this.locale_dateTime = timeLocale.dateTime;
    this.locale_date = timeLocale.date;
    this.locale_time = timeLocale.time;
    this.locale_periods = timeLocale.periods;
    this.locale_weekdays = timeLocale.days;
    this.locale_shortWeekdays = timeLocale.shortDays;
    this.locale_months = timeLocale.months;
    this.locale_shortMonths = timeLocale.shortMonths;
    this.periodRe = formatRe(this.locale_periods);
    this.periodLookup = formatLookup(this.locale_periods);
    this.weekdayRe = formatRe(this.locale_weekdays);
    this.weekdayLookup = formatLookup(this.locale_weekdays);
    this.shortWeekdayRe = formatRe(this.locale_shortWeekdays);
    this.shortWeekdayLookup = formatLookup(this.locale_shortWeekdays);
    this.monthRe = formatRe(this.locale_months);
    this.monthLookup = formatLookup(this.locale_months);
    this.shortMonthRe = formatRe(this.locale_shortMonths);
    this.shortMonthLookup = formatLookup(this.locale_shortMonths);
    this.formats = mutableMapOf([new Pair(toBoxedChar(97), getCallableRef('formatShortWeekday', function ($receiver, d, p) {
      return $receiver.formatShortWeekday_k6ioiu$(d, p);
    }.bind(null, this))), new Pair(toBoxedChar(65), getCallableRef('formatWeekday', function ($receiver, d, p) {
      return $receiver.formatWeekday_k6ioiu$(d, p);
    }.bind(null, this))), new Pair(toBoxedChar(98), getCallableRef('formatShortMonth', function ($receiver, d, p) {
      return $receiver.formatShortMonth_k6ioiu$(d, p);
    }.bind(null, this))), new Pair(toBoxedChar(66), getCallableRef('formatMonth', function ($receiver, d, p) {
      return $receiver.formatMonth_k6ioiu$(d, p);
    }.bind(null, this))), new Pair(toBoxedChar(99), null), new Pair(toBoxedChar(100), getCallableRef('formatDayOfMonth', function ($receiver, d, p) {
      return $receiver.formatDayOfMonth_k6ioiu$(d, p);
    }.bind(null, this))), new Pair(toBoxedChar(101), getCallableRef('formatDayOfMonth', function ($receiver, d, p) {
      return $receiver.formatDayOfMonth_k6ioiu$(d, p);
    }.bind(null, this))), new Pair(toBoxedChar(72), getCallableRef('formatHour24', function ($receiver, d, p) {
      return $receiver.formatHour24_k6ioiu$(d, p);
    }.bind(null, this))), new Pair(toBoxedChar(73), getCallableRef('formatHour12', function ($receiver, d, p) {
      return $receiver.formatHour12_k6ioiu$(d, p);
    }.bind(null, this))), new Pair(toBoxedChar(106), getCallableRef('formatDayOfYear', function ($receiver, d, p) {
      return $receiver.formatDayOfYear_k6ioiu$(d, p);
    }.bind(null, this))), new Pair(toBoxedChar(76), getCallableRef('formatMilliseconds', function ($receiver, d, p) {
      return $receiver.formatMilliseconds_k6ioiu$(d, p);
    }.bind(null, this))), new Pair(toBoxedChar(109), getCallableRef('formatMonthNumber', function ($receiver, d, p) {
      return $receiver.formatMonthNumber_k6ioiu$(d, p);
    }.bind(null, this))), new Pair(toBoxedChar(77), getCallableRef('formatMinutes', function ($receiver, d, p) {
      return $receiver.formatMinutes_k6ioiu$(d, p);
    }.bind(null, this))), new Pair(toBoxedChar(112), getCallableRef('formatPeriod', function ($receiver, d, p) {
      return $receiver.formatPeriod_k6ioiu$(d, p);
    }.bind(null, this))), new Pair(toBoxedChar(83), getCallableRef('formatSeconds', function ($receiver, d, p) {
      return $receiver.formatSeconds_k6ioiu$(d, p);
    }.bind(null, this))), new Pair(toBoxedChar(85), getCallableRef('formatWeekNumberSunday', function ($receiver, d, p) {
      return $receiver.formatWeekNumberSunday_k6ioiu$(d, p);
    }.bind(null, this))), new Pair(toBoxedChar(119), getCallableRef('formatWeekdayNumber', function ($receiver, d, p) {
      return $receiver.formatWeekdayNumber_k6ioiu$(d, p);
    }.bind(null, this))), new Pair(toBoxedChar(87), getCallableRef('formatWeekNumberMonday', function ($receiver, d, p) {
      return $receiver.formatWeekNumberMonday_k6ioiu$(d, p);
    }.bind(null, this))), new Pair(toBoxedChar(120), null), new Pair(toBoxedChar(88), null), new Pair(toBoxedChar(121), getCallableRef('formatYear', function ($receiver, d, p) {
      return $receiver.formatYear_k6ioiu$(d, p);
    }.bind(null, this))), new Pair(toBoxedChar(89), getCallableRef('formatFullYear', function ($receiver, d, p) {
      return $receiver.formatFullYear_k6ioiu$(d, p);
    }.bind(null, this))), new Pair(toBoxedChar(90), getCallableRef('formatZone', function ($receiver, d, p) {
      return $receiver.formatZone_k6ioiu$(d, p);
    }.bind(null, this))), new Pair(toBoxedChar(37), getCallableRef('formatLiteralPercent', function ($receiver, d, p) {
      return $receiver.formatLiteralPercent_k6ioiu$(d, p);
    }.bind(null, this)))]);
    this.parses = mutableMapOf([new Pair(toBoxedChar(97), getCallableRef('parseShortWeekday', function ($receiver, d, string, i) {
      return $receiver.parseShortWeekday_o7y682$(d, string, i);
    }.bind(null, this))), new Pair(toBoxedChar(65), getCallableRef('parseWeekday', function ($receiver, d, string, i) {
      return $receiver.parseWeekday_o7y682$(d, string, i);
    }.bind(null, this))), new Pair(toBoxedChar(98), getCallableRef('parseShortMonth', function ($receiver, d, string, i) {
      return $receiver.parseShortMonth_o7y682$(d, string, i);
    }.bind(null, this))), new Pair(toBoxedChar(66), getCallableRef('parseMonth', function ($receiver, d, string, i) {
      return $receiver.parseMonth_o7y682$(d, string, i);
    }.bind(null, this))), new Pair(toBoxedChar(99), getCallableRef('parseLocaleDateTime', function ($receiver, d, string, i) {
      return $receiver.parseLocaleDateTime_o7y682$(d, string, i);
    }.bind(null, this))), new Pair(toBoxedChar(100), getCallableRef('parseDayOfMonth', function ($receiver, d, string, i) {
      return $receiver.parseDayOfMonth_o7y682$(d, string, i);
    }.bind(null, this))), new Pair(toBoxedChar(101), getCallableRef('parseDayOfMonth', function ($receiver, d, string, i) {
      return $receiver.parseDayOfMonth_o7y682$(d, string, i);
    }.bind(null, this))), new Pair(toBoxedChar(72), getCallableRef('parseHour24', function ($receiver, d, string, i) {
      return $receiver.parseHour24_o7y682$(d, string, i);
    }.bind(null, this))), new Pair(toBoxedChar(73), getCallableRef('parseHour24', function ($receiver, d, string, i) {
      return $receiver.parseHour24_o7y682$(d, string, i);
    }.bind(null, this))), new Pair(toBoxedChar(106), getCallableRef('parseDayOfYear', function ($receiver, d, string, i) {
      return $receiver.parseDayOfYear_o7y682$(d, string, i);
    }.bind(null, this))), new Pair(toBoxedChar(76), getCallableRef('parseMilliseconds', function ($receiver, d, string, i) {
      return $receiver.parseMilliseconds_o7y682$(d, string, i);
    }.bind(null, this))), new Pair(toBoxedChar(109), getCallableRef('parseMonthNumber', function ($receiver, d, string, i) {
      return $receiver.parseMonthNumber_o7y682$(d, string, i);
    }.bind(null, this))), new Pair(toBoxedChar(77), getCallableRef('parseMinutes', function ($receiver, d, string, i) {
      return $receiver.parseMinutes_o7y682$(d, string, i);
    }.bind(null, this))), new Pair(toBoxedChar(112), getCallableRef('parsePeriod', function ($receiver, d, string, i) {
      return $receiver.parsePeriod_o7y682$(d, string, i);
    }.bind(null, this))), new Pair(toBoxedChar(83), getCallableRef('parseSeconds', function ($receiver, d, string, i) {
      return $receiver.parseSeconds_o7y682$(d, string, i);
    }.bind(null, this))), new Pair(toBoxedChar(85), getCallableRef('parseWeekNumberSunday', function ($receiver, d, string, i) {
      return $receiver.parseWeekNumberSunday_o7y682$(d, string, i);
    }.bind(null, this))), new Pair(toBoxedChar(119), getCallableRef('parseWeekdayNumber', function ($receiver, d, string, i) {
      return $receiver.parseWeekdayNumber_o7y682$(d, string, i);
    }.bind(null, this))), new Pair(toBoxedChar(87), getCallableRef('parseWeekNumberMonday', function ($receiver, d, string, i) {
      return $receiver.parseWeekNumberMonday_o7y682$(d, string, i);
    }.bind(null, this))), new Pair(toBoxedChar(120), getCallableRef('parseLocaleDate', function ($receiver, d, string, i) {
      return $receiver.parseLocaleDate_o7y682$(d, string, i);
    }.bind(null, this))), new Pair(toBoxedChar(88), getCallableRef('parseLocaleTime', function ($receiver, d, string, i) {
      return $receiver.parseLocaleTime_o7y682$(d, string, i);
    }.bind(null, this))), new Pair(toBoxedChar(121), getCallableRef('parseYear', function ($receiver, d, string, i) {
      return $receiver.parseYear_o7y682$(d, string, i);
    }.bind(null, this))), new Pair(toBoxedChar(89), getCallableRef('parseFullYear', function ($receiver, d, string, i) {
      return $receiver.parseFullYear_o7y682$(d, string, i);
    }.bind(null, this))), new Pair(toBoxedChar(37), getCallableRef('parseLiteralPercent', function ($receiver, d, string, i) {
      return $receiver.parseLiteralPercent_o7y682$(d, string, i);
    }.bind(null, this)))]);
    this.dateTimeFormat = this.format_61zpoe$(this.locale_dateTime);
    this.dateFormat = this.format_61zpoe$(this.locale_date);
    this.timeFormat = this.format_61zpoe$(this.locale_time);
    var $receiver = this.formats;
    var key = toBoxedChar(99);
    $receiver.put_xwzc9p$(key, Locale_init$lambda(this));
    var $receiver_0 = this.formats;
    var key_0 = toBoxedChar(120);
    $receiver_0.put_xwzc9p$(key_0, Locale_init$lambda_0(this));
    var $receiver_1 = this.formats;
    var key_1 = toBoxedChar(88);
    $receiver_1.put_xwzc9p$(key_1, Locale_init$lambda_1(this));
  }
  function Locale$autoFormat$lambda(closure$formatMonth, closure$formatWeek, closure$formatHour, closure$formatMinute, closure$formatSecond, closure$formatMillisecond, closure$formatYear) {
    return function (date) {
      var tmp$;
      if (time.timeYear.floor_fw2154$(date).month() < date.month())
        tmp$ = closure$formatMonth;
      else {
        if (time.timeMonth.floor_fw2154$(date).dayOfYear() < date.dayOfMonth())
          tmp$ = closure$formatWeek;
        else {
          if (time.timeDay.floor_fw2154$(date).hour() < date.hour())
            tmp$ = closure$formatHour;
          else {
            if (time.timeHour.floor_fw2154$(date).minute() < date.minute())
              tmp$ = closure$formatMinute;
            else {
              if (time.timeMinute.floor_fw2154$(date).second() < date.second())
                tmp$ = closure$formatSecond;
              else {
                tmp$ = time.timeSecond.floor_fw2154$(date).millisecond() < date.millisecond() ? closure$formatMillisecond : closure$formatYear;
              }
            }
          }
        }
      }
      var formatter = tmp$;
      return formatter(date);
    };
  }
  Locale.prototype.autoFormat = function () {
    var formatMillisecond = this.format_61zpoe$('.%L');
    var formatSecond = this.format_61zpoe$(':%S');
    var formatMinute = this.format_61zpoe$('%I:%M');
    var formatHour = this.format_61zpoe$('%I %p');
    var formatWeek = this.format_61zpoe$('%b %d');
    var formatMonth = this.format_61zpoe$('%B');
    var formatYear = this.format_61zpoe$('%Y');
    return Locale$autoFormat$lambda(formatMonth, formatWeek, formatHour, formatMinute, formatSecond, formatMillisecond, formatYear);
  };
  function Locale$format$lambda(closure$specifier, this$Locale) {
    return function (date) {
      var string = ArrayList_init();
      var i = 0;
      var j = 0;
      while (i < closure$specifier.length) {
        if (closure$specifier.charCodeAt(i) === 37) {
          var $receiver = closure$specifier;
          var startIndex = j;
          var endIndex = i;
          string.add_11rb$($receiver.substring(startIndex, endIndex));
          i = i + 1 | 0;
          var c = closure$specifier.charCodeAt(i);
          var pad = pads.get_11rb$(toBoxedChar(c));
          if (pad != null) {
            i = i + 1 | 0;
            c = closure$specifier.charCodeAt(i);
          }
           else {
            pad = c === 101 ? ' ' : '0';
          }
          var format = this$Locale.formats.get_11rb$(toBoxedChar(c));
          if (format != null) {
            string.add_11rb$(format(date, pad));
          }
           else {
            string.add_11rb$(String.fromCharCode(c));
          }
          j = i + 1 | 0;
        }
        i = i + 1 | 0;
      }
      var $receiver_0 = closure$specifier;
      var startIndex_0 = j;
      var endIndex_0 = i;
      string.add_11rb$($receiver_0.substring(startIndex_0, endIndex_0));
      return joinToString(string, '');
    };
  }
  Locale.prototype.format_61zpoe$ = function (specifier) {
    return Locale$format$lambda(specifier, this);
  };
  function Locale$parse$lambda(closure$specifier, this$Locale) {
    return function (dateString) {
      var tmp$, tmp$_0;
      var d = newYear(1900);
      var i = this$Locale.parseSpecifier_0(d, closure$specifier, dateString, 0);
      if (i !== dateString.length)
        return null;
      if (d.period != null) {
        if (d.hour == null)
          d.hour = 0;
        d.hour = ensureNotNull(d.hour) % 12 + (ensureNotNull(d.period) * 12 | 0) | 0;
      }
      if (d.weekNumberMonday != null || d.weekNumberSunday != null) {
        var preValue = d.weekNumberMonday != null ? 1 : 0;
        if (d.weekDay == null)
          d.weekDay = preValue;
        if (d.zone != null) {
          tmp$ = 0;
        }
         else {
          tmp$ = date_0(newYear(d.year)).dayOfWeek();
        }
        var day = tmp$;
        d.month = 1;
        if (d.weekNumberMonday != null) {
          tmp$_0 = (ensureNotNull(d.weekDay) + 6 | 0) % 7 + (ensureNotNull(d.weekNumberMonday) * 7 | 0) - (day + 5 | 0) % 7 | 0;
        }
         else {
          tmp$_0 = ensureNotNull(d.weekDay) + (ensureNotNull(d.weekNumberSunday) * 7 | 0) - (day + 6 | 0) % 7 | 0;
        }
        d.day = tmp$_0;
      }
      return date_0(d);
    };
  }
  Locale.prototype.parse_61zpoe$ = function (specifier) {
    return Locale$parse$lambda(specifier, this);
  };
  Locale.prototype.parseSpecifier_0 = function (d, specifier, dateString, index) {
    var i = 0;
    var newIndex = index;
    while (i < specifier.length) {
      if (newIndex >= dateString.length)
        return -1;
      var c = specifier.charCodeAt(i);
      i = i + 1 | 0;
      if (c === 37) {
        c = specifier.charCodeAt(i);
        i = i + 1 | 0;
        var $receiver = pads;
        var key = toBoxedChar(c);
        var tmp$;
        if ((Kotlin.isType(tmp$ = $receiver, Map) ? tmp$ : throwCCE()).containsKey_11rb$(key)) {
          c = specifier.charCodeAt(i);
          i = i + 1 | 0;
        }
        var parse = this.parses.get_11rb$(toBoxedChar(c));
        if (parse == null)
          return -1;
        var parsedIndex = parse(d, dateString, newIndex);
        if (parsedIndex < 0)
          return -1;
        newIndex = parse(d, dateString, newIndex);
      }
       else {
        if (c !== dateString.charCodeAt(newIndex)) {
          return -1;
        }
        newIndex = newIndex + 1 | 0;
      }
    }
    return newIndex;
  };
  Locale.prototype.parsePeriod_o7y682$ = function (d, string, i) {
    var tmp$;
    var n = this.periodRe.find_905azu$(string.substring(i));
    if (n != null) {
      var tmp$_0 = this.periodLookup;
      var $receiver = n.groupValues.get_za3lpa$(0);
      var destination = StringBuilder_init();
      var tmp$_1;
      tmp$_1 = $receiver.length;
      for (var index = 0; index < tmp$_1; index++) {
        var element = $receiver.charCodeAt(index);
        if (unboxChar(toBoxedChar(element)) !== 32)
          destination.append_s8itvh$(element);
      }
      var period = tmp$_0.get_11rb$(destination.toString().toLowerCase());
      d.period = period != null ? period : 0;
      tmp$ = i + n.groupValues.get_za3lpa$(0).length | 0;
    }
     else
      tmp$ = -1;
    return tmp$;
  };
  Locale.prototype.parseShortWeekday_o7y682$ = function (d, string, i) {
    var tmp$;
    var n = this.shortWeekdayRe.find_905azu$(string.substring(i));
    if (n != null) {
      var tmp$_0 = this.shortWeekdayLookup;
      var $receiver = n.groupValues.get_za3lpa$(0);
      var destination = StringBuilder_init();
      var tmp$_1;
      tmp$_1 = $receiver.length;
      for (var index = 0; index < tmp$_1; index++) {
        var element = $receiver.charCodeAt(index);
        if (unboxChar(toBoxedChar(element)) !== 32)
          destination.append_s8itvh$(element);
      }
      var weekDay = tmp$_0.get_11rb$(destination.toString().toLowerCase());
      d.weekDay = weekDay != null ? weekDay : 0;
      tmp$ = i + n.groupValues.get_za3lpa$(0).length | 0;
    }
     else
      tmp$ = -1;
    return tmp$;
  };
  Locale.prototype.parseWeekday_o7y682$ = function (d, string, i) {
    var tmp$;
    var n = this.weekdayRe.find_905azu$(string.substring(i));
    if (n != null) {
      var tmp$_0 = this.weekdayLookup;
      var $receiver = n.groupValues.get_za3lpa$(0);
      var destination = StringBuilder_init();
      var tmp$_1;
      tmp$_1 = $receiver.length;
      for (var index = 0; index < tmp$_1; index++) {
        var element = $receiver.charCodeAt(index);
        if (unboxChar(toBoxedChar(element)) !== 32)
          destination.append_s8itvh$(element);
      }
      var weekDay = tmp$_0.get_11rb$(destination.toString().toLowerCase());
      d.weekDay = weekDay != null ? weekDay : 0;
      tmp$ = i + n.groupValues.get_za3lpa$(0).length | 0;
    }
     else
      tmp$ = -1;
    return tmp$;
  };
  Locale.prototype.parseShortMonth_o7y682$ = function (d, string, i) {
    var tmp$;
    var n = this.shortMonthRe.find_905azu$(string.substring(i));
    if (n != null) {
      var tmp$_0 = this.shortMonthLookup;
      var $receiver = n.groupValues.get_za3lpa$(0);
      var destination = StringBuilder_init();
      var tmp$_1;
      tmp$_1 = $receiver.length;
      for (var index = 0; index < tmp$_1; index++) {
        var element = $receiver.charCodeAt(index);
        if (unboxChar(toBoxedChar(element)) !== 32)
          destination.append_s8itvh$(element);
      }
      var month = tmp$_0.get_11rb$(destination.toString().toLowerCase());
      d.month = month != null ? month + 1 | 0 : 0;
      tmp$ = i + n.groupValues.get_za3lpa$(0).length | 0;
    }
     else
      tmp$ = -1;
    return tmp$;
  };
  Locale.prototype.parseMonth_o7y682$ = function (d, string, i) {
    var tmp$;
    var n = this.monthRe.find_905azu$(string.substring(i));
    if (n != null) {
      var tmp$_0 = this.monthLookup;
      var $receiver = n.groupValues.get_za3lpa$(0);
      var destination = StringBuilder_init();
      var tmp$_1;
      tmp$_1 = $receiver.length;
      for (var index = 0; index < tmp$_1; index++) {
        var element = $receiver.charCodeAt(index);
        if (unboxChar(toBoxedChar(element)) !== 32)
          destination.append_s8itvh$(element);
      }
      var month = tmp$_0.get_11rb$(destination.toString().toLowerCase());
      d.month = month != null ? month + 1 | 0 : 0;
      tmp$ = i + n.groupValues.get_za3lpa$(0).length | 0;
    }
     else
      tmp$ = -1;
    return tmp$;
  };
  Locale.prototype.parseLocaleDateTime_o7y682$ = function (d, string, i) {
    return this.parseSpecifier_0(d, this.locale_dateTime, string, i);
  };
  Locale.prototype.parseLocaleDate_o7y682$ = function (d, string, i) {
    return this.parseSpecifier_0(d, this.locale_date, string, i);
  };
  Locale.prototype.parseLocaleTime_o7y682$ = function (d, string, i) {
    return this.parseSpecifier_0(d, this.locale_time, string, i);
  };
  Locale.prototype.parseWeekdayNumber_o7y682$ = function (d, string, i) {
    var tmp$;
    var tmp$_0 = numberRe;
    var endIndex = i + 1 | 0;
    var n = tmp$_0.find_905azu$(string.substring(i, endIndex));
    if (n != null) {
      var $receiver = n.groupValues.get_za3lpa$(0);
      var destination = StringBuilder_init();
      var tmp$_1;
      tmp$_1 = $receiver.length;
      for (var index = 0; index < tmp$_1; index++) {
        var element = $receiver.charCodeAt(index);
        if (unboxChar(toBoxedChar(element)) !== 32)
          destination.append_s8itvh$(element);
      }
      d.weekDay = toInt(destination.toString());
      tmp$ = i + n.groupValues.get_za3lpa$(0).length | 0;
    }
     else
      tmp$ = -1;
    return tmp$;
  };
  Locale.prototype.parseWeekNumberSunday_o7y682$ = function (d, string, i) {
    var tmp$;
    var n = numberRe.find_905azu$(string.substring(i));
    if (n != null) {
      var $receiver = n.groupValues.get_za3lpa$(0);
      var destination = StringBuilder_init();
      var tmp$_0;
      tmp$_0 = $receiver.length;
      for (var index = 0; index < tmp$_0; index++) {
        var element = $receiver.charCodeAt(index);
        if (unboxChar(toBoxedChar(element)) !== 32)
          destination.append_s8itvh$(element);
      }
      d.weekNumberSunday = toInt(destination.toString());
      tmp$ = i + n.groupValues.get_za3lpa$(0).length | 0;
    }
     else
      tmp$ = -1;
    return tmp$;
  };
  Locale.prototype.parseWeekNumberMonday_o7y682$ = function (d, string, i) {
    var tmp$;
    var n = numberRe.find_905azu$(string.substring(i));
    if (n != null) {
      var $receiver = n.groupValues.get_za3lpa$(0);
      var destination = StringBuilder_init();
      var tmp$_0;
      tmp$_0 = $receiver.length;
      for (var index = 0; index < tmp$_0; index++) {
        var element = $receiver.charCodeAt(index);
        if (unboxChar(toBoxedChar(element)) !== 32)
          destination.append_s8itvh$(element);
      }
      d.weekNumberMonday = toInt(destination.toString());
      tmp$ = i + n.groupValues.get_za3lpa$(0).length | 0;
    }
     else
      tmp$ = -1;
    return tmp$;
  };
  Locale.prototype.parseFullYear_o7y682$ = function (d, string, i) {
    var tmp$;
    var tmp$_0 = numberRe;
    var endIndex = i + 4 | 0;
    var n = tmp$_0.find_905azu$(string.substring(i, endIndex));
    if (n != null) {
      var $receiver = n.groupValues.get_za3lpa$(0);
      var destination = StringBuilder_init();
      var tmp$_1;
      tmp$_1 = $receiver.length;
      for (var index = 0; index < tmp$_1; index++) {
        var element = $receiver.charCodeAt(index);
        if (unboxChar(toBoxedChar(element)) !== 32)
          destination.append_s8itvh$(element);
      }
      d.year = toInt(destination.toString());
      tmp$ = i + n.groupValues.get_za3lpa$(0).length | 0;
    }
     else
      tmp$ = -1;
    return tmp$;
  };
  Locale.prototype.parseYear_o7y682$ = function (d, string, i) {
    var tmp$;
    var tmp$_0 = numberRe;
    var endIndex = i + 2 | 0;
    var n = tmp$_0.find_905azu$(string.substring(i, endIndex));
    if (n != null) {
      d.year = toInt(n.groupValues.get_za3lpa$(0)) + (toInt(n.groupValues.get_za3lpa$(0)) > 68 ? 1900 : 2000) | 0;
      tmp$ = i + n.groupValues.get_za3lpa$(0).length | 0;
    }
     else
      tmp$ = -1;
    return tmp$;
  };
  Locale.prototype.parseMonthNumber_o7y682$ = function (d, string, i) {
    var tmp$;
    var tmp$_0 = numberRe;
    var endIndex = i + 2 | 0;
    var n = tmp$_0.find_905azu$(string.substring(i, endIndex));
    if (n != null) {
      var $receiver = n.groupValues.get_za3lpa$(0);
      var destination = StringBuilder_init();
      var tmp$_1;
      tmp$_1 = $receiver.length;
      for (var index = 0; index < tmp$_1; index++) {
        var element = $receiver.charCodeAt(index);
        if (unboxChar(toBoxedChar(element)) !== 32)
          destination.append_s8itvh$(element);
      }
      d.month = toInt(destination.toString());
      tmp$ = i + n.groupValues.get_za3lpa$(0).length | 0;
    }
     else
      tmp$ = -1;
    return tmp$;
  };
  Locale.prototype.parseDayOfMonth_o7y682$ = function (d, string, i) {
    var tmp$;
    var tmp$_0 = numberRe;
    var endIndex = i + 2 | 0;
    var n = tmp$_0.find_905azu$(string.substring(i, endIndex));
    if (n != null) {
      var $receiver = n.groupValues.get_za3lpa$(0);
      var destination = StringBuilder_init();
      var tmp$_1;
      tmp$_1 = $receiver.length;
      for (var index = 0; index < tmp$_1; index++) {
        var element = $receiver.charCodeAt(index);
        if (unboxChar(toBoxedChar(element)) !== 32)
          destination.append_s8itvh$(element);
      }
      d.day = toInt(destination.toString());
      tmp$ = i + n.groupValues.get_za3lpa$(0).length | 0;
    }
     else
      tmp$ = -1;
    return tmp$;
  };
  Locale.prototype.parseDayOfYear_o7y682$ = function (d, string, i) {
    var tmp$;
    var tmp$_0 = numberRe;
    var endIndex = i + 3 | 0;
    var n = tmp$_0.find_905azu$(string.substring(i, endIndex));
    if (n != null) {
      d.month = 0;
      var $receiver = n.groupValues.get_za3lpa$(0);
      var destination = StringBuilder_init();
      var tmp$_1;
      tmp$_1 = $receiver.length;
      for (var index = 0; index < tmp$_1; index++) {
        var element = $receiver.charCodeAt(index);
        if (unboxChar(toBoxedChar(element)) !== 32)
          destination.append_s8itvh$(element);
      }
      d.day = toInt(destination.toString());
      tmp$ = i + n.groupValues.get_za3lpa$(0).length | 0;
    }
     else
      tmp$ = -1;
    return tmp$;
  };
  Locale.prototype.parseHour24_o7y682$ = function (d, string, i) {
    var tmp$;
    var tmp$_0 = numberRe;
    var endIndex = i + 2 | 0;
    var n = tmp$_0.find_905azu$(string.substring(i, endIndex));
    if (n != null) {
      var $receiver = n.groupValues.get_za3lpa$(0);
      var destination = StringBuilder_init();
      var tmp$_1;
      tmp$_1 = $receiver.length;
      for (var index = 0; index < tmp$_1; index++) {
        var element = $receiver.charCodeAt(index);
        if (unboxChar(toBoxedChar(element)) !== 32)
          destination.append_s8itvh$(element);
      }
      d.hour = toInt(destination.toString());
      tmp$ = i + n.groupValues.get_za3lpa$(0).length | 0;
    }
     else
      tmp$ = -1;
    return tmp$;
  };
  Locale.prototype.parseMinutes_o7y682$ = function (d, string, i) {
    var tmp$;
    var tmp$_0 = numberRe;
    var endIndex = i + 2 | 0;
    var n = tmp$_0.find_905azu$(string.substring(i, endIndex));
    if (n != null) {
      var $receiver = n.groupValues.get_za3lpa$(0);
      var destination = StringBuilder_init();
      var tmp$_1;
      tmp$_1 = $receiver.length;
      for (var index = 0; index < tmp$_1; index++) {
        var element = $receiver.charCodeAt(index);
        if (unboxChar(toBoxedChar(element)) !== 32)
          destination.append_s8itvh$(element);
      }
      d.minute = toInt(destination.toString());
      tmp$ = i + n.groupValues.get_za3lpa$(0).length | 0;
    }
     else
      tmp$ = -1;
    return tmp$;
  };
  Locale.prototype.parseSeconds_o7y682$ = function (d, string, i) {
    var tmp$;
    var tmp$_0 = numberRe;
    var endIndex = i + 2 | 0;
    var n = tmp$_0.find_905azu$(string.substring(i, endIndex));
    if (n != null) {
      var $receiver = n.groupValues.get_za3lpa$(0);
      var destination = StringBuilder_init();
      var tmp$_1;
      tmp$_1 = $receiver.length;
      for (var index = 0; index < tmp$_1; index++) {
        var element = $receiver.charCodeAt(index);
        if (unboxChar(toBoxedChar(element)) !== 32)
          destination.append_s8itvh$(element);
      }
      d.second = toInt(destination.toString());
      tmp$ = i + n.groupValues.get_za3lpa$(0).length | 0;
    }
     else
      tmp$ = -1;
    return tmp$;
  };
  Locale.prototype.parseMilliseconds_o7y682$ = function (d, string, i) {
    var tmp$;
    var tmp$_0 = numberRe;
    var endIndex = i + 3 | 0;
    var n = tmp$_0.find_905azu$(string.substring(i, endIndex));
    if (n != null) {
      var $receiver = n.groupValues.get_za3lpa$(0);
      var destination = StringBuilder_init();
      var tmp$_1;
      tmp$_1 = $receiver.length;
      for (var index = 0; index < tmp$_1; index++) {
        var element = $receiver.charCodeAt(index);
        if (unboxChar(toBoxedChar(element)) !== 32)
          destination.append_s8itvh$(element);
      }
      d.millisecond = toInt(destination.toString());
      tmp$ = i + n.groupValues.get_za3lpa$(0).length | 0;
    }
     else
      tmp$ = -1;
    return tmp$;
  };
  Locale.prototype.parseLiteralPercent_o7y682$ = function (d, string, i) {
    var percentRe = Regex_init('^%');
    var endIndex = i + 1 | 0;
    var input = string.substring(i, endIndex);
    var n = percentRe.find_905azu$(input);
    return n != null ? i + n.groupValues.get_za3lpa$(0).length | 0 : -1;
  };
  Locale.prototype.formatShortWeekday_k6ioiu$ = function (d, p) {
    return this.locale_shortWeekdays.get_za3lpa$(d.dayOfWeek() % 7);
  };
  Locale.prototype.formatWeekday_k6ioiu$ = function (d, p) {
    return this.locale_weekdays.get_za3lpa$(d.dayOfWeek() % 7);
  };
  Locale.prototype.formatShortMonth_k6ioiu$ = function (d, p) {
    return this.locale_shortMonths.get_za3lpa$(d.month() - 1 | 0);
  };
  Locale.prototype.formatMonth_k6ioiu$ = function (d, p) {
    return this.locale_months.get_za3lpa$(d.month() - 1 | 0);
  };
  Locale.prototype.formatPeriod_k6ioiu$ = function (d, p) {
    return this.locale_periods.get_za3lpa$(d.hour() >= 12 ? 1 : 0);
  };
  Locale.prototype.formatDayOfMonth_k6ioiu$ = function (d, p) {
    return pad(d.dayOfMonth(), p, 2);
  };
  Locale.prototype.formatHour24_k6ioiu$ = function (d, p) {
    return pad(d.hour(), p, 2);
  };
  Locale.prototype.formatHour12_k6ioiu$ = function (d, p) {
    var hour = d.hour() % 12;
    return pad(hour === 0 ? 12 : hour, p, 2);
  };
  Locale.prototype.formatDayOfYear_k6ioiu$ = function (d, p) {
    return pad(d.dayOfYear(), p, 3);
  };
  Locale.prototype.formatMilliseconds_k6ioiu$ = function (d, p) {
    return pad(d.millisecond(), p, 3);
  };
  Locale.prototype.formatMonthNumber_k6ioiu$ = function (d, p) {
    return pad(d.month(), p, 2);
  };
  Locale.prototype.formatMinutes_k6ioiu$ = function (d, p) {
    return pad(d.minute(), p, 2);
  };
  Locale.prototype.formatSeconds_k6ioiu$ = function (d, p) {
    return pad(d.second(), p, 2);
  };
  Locale.prototype.formatWeekNumberSunday_k6ioiu$ = function (d, p) {
    var start = time.timeYear.floor_fw2154$(d);
    var value = time.timeSunday.count_6v3okc$(start, d);
    return pad(value, p, 2);
  };
  Locale.prototype.formatWeekdayNumber_k6ioiu$ = function (d, p) {
    return d.dayOfWeek().toString();
  };
  Locale.prototype.formatWeekNumberMonday_k6ioiu$ = function (d, p) {
    return pad(time.timeMonday.count_6v3okc$(time.timeYear.floor_fw2154$(d), d), p, 2);
  };
  Locale.prototype.formatYear_k6ioiu$ = function (d, p) {
    return pad(d.year() % 100, p, 2);
  };
  Locale.prototype.formatFullYear_k6ioiu$ = function (d, p) {
    return pad(d.year() % 10000, p, 4);
  };
  Locale.prototype.formatZone_k6ioiu$ = function (d, p) {
    var z = d.getTimezoneOffset();
    var sign = z > 0 ? '-' : '+';
    z = abs(z);
    return sign + pad(z / 60 | 0, '0', 2) + pad(z % 60, '0', 2);
  };
  Locale.prototype.formatLiteralPercent_k6ioiu$ = function (d, p) {
    return '%';
  };
  function Locale_init$lambda(this$Locale) {
    return function (date, f) {
      return this$Locale.dateTimeFormat(date);
    };
  }
  function Locale_init$lambda_0(this$Locale) {
    return function (date, f) {
      return this$Locale.dateFormat(date);
    };
  }
  function Locale_init$lambda_1(this$Locale) {
    return function (date, f) {
      return this$Locale.timeFormat(date);
    };
  }
  Locale.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Locale',
    interfaces: []
  };
  function newYear(y) {
    return new ParseDate(y);
  }
  var pads;
  var numberRe;
  function pad(value, fill, width) {
    var sign = value < 0 ? '-' : '';
    var string = abs(value).toString();
    var $receiver = until(0, width - string.length | 0);
    var destination = ArrayList_init_0(collectionSizeOrDefault($receiver, 10));
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(fill);
    }
    return sign + joinToString(destination, '') + string;
  }
  function requote(s) {
    var requoteRe = '/[\\\\^$\\*\\+\\?\\|\\[\\]\\(\\)\\.\\{\\}]/g';
    return replace(s, requoteRe, '\\$&');
  }
  function formatRe(names) {
    var destination = ArrayList_init_0(collectionSizeOrDefault(names, 10));
    var tmp$;
    tmp$ = names.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(requote(item));
    }
    var joinToString_0 = joinToString(destination, '|');
    return Regex_init_0('^(?:' + joinToString_0 + ')', RegexOption.IGNORE_CASE);
  }
  function formatLookup(names) {
    var map = LinkedHashMap_init();
    var i = -1;
    var n = names.size;
    while ((i = i + 1 | 0, i) < n) {
      var key = names.get_za3lpa$(i).toLowerCase();
      var value = i;
      map.put_xwzc9p$(key, value);
    }
    return toMap(map);
  }
  function TimeLocale(dateTime, date, time, periods, days, shortDays, months, shortMonths) {
    this.dateTime = dateTime;
    this.date = date;
    this.time = time;
    this.periods = periods;
    this.days = days;
    this.shortDays = shortDays;
    this.months = months;
    this.shortMonths = shortMonths;
  }
  TimeLocale.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'TimeLocale',
    interfaces: []
  };
  TimeLocale.prototype.component1 = function () {
    return this.dateTime;
  };
  TimeLocale.prototype.component2 = function () {
    return this.date;
  };
  TimeLocale.prototype.component3 = function () {
    return this.time;
  };
  TimeLocale.prototype.component4 = function () {
    return this.periods;
  };
  TimeLocale.prototype.component5 = function () {
    return this.days;
  };
  TimeLocale.prototype.component6 = function () {
    return this.shortDays;
  };
  TimeLocale.prototype.component7 = function () {
    return this.months;
  };
  TimeLocale.prototype.component8 = function () {
    return this.shortMonths;
  };
  TimeLocale.prototype.copy_o23mdh$ = function (dateTime, date, time, periods, days, shortDays, months, shortMonths) {
    return new TimeLocale(dateTime === void 0 ? this.dateTime : dateTime, date === void 0 ? this.date : date, time === void 0 ? this.time : time, periods === void 0 ? this.periods : periods, days === void 0 ? this.days : days, shortDays === void 0 ? this.shortDays : shortDays, months === void 0 ? this.months : months, shortMonths === void 0 ? this.shortMonths : shortMonths);
  };
  TimeLocale.prototype.toString = function () {
    return 'TimeLocale(dateTime=' + Kotlin.toString(this.dateTime) + (', date=' + Kotlin.toString(this.date)) + (', time=' + Kotlin.toString(this.time)) + (', periods=' + Kotlin.toString(this.periods)) + (', days=' + Kotlin.toString(this.days)) + (', shortDays=' + Kotlin.toString(this.shortDays)) + (', months=' + Kotlin.toString(this.months)) + (', shortMonths=' + Kotlin.toString(this.shortMonths)) + ')';
  };
  TimeLocale.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.dateTime) | 0;
    result = result * 31 + Kotlin.hashCode(this.date) | 0;
    result = result * 31 + Kotlin.hashCode(this.time) | 0;
    result = result * 31 + Kotlin.hashCode(this.periods) | 0;
    result = result * 31 + Kotlin.hashCode(this.days) | 0;
    result = result * 31 + Kotlin.hashCode(this.shortDays) | 0;
    result = result * 31 + Kotlin.hashCode(this.months) | 0;
    result = result * 31 + Kotlin.hashCode(this.shortMonths) | 0;
    return result;
  };
  TimeLocale.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.dateTime, other.dateTime) && Kotlin.equals(this.date, other.date) && Kotlin.equals(this.time, other.time) && Kotlin.equals(this.periods, other.periods) && Kotlin.equals(this.days, other.days) && Kotlin.equals(this.shortDays, other.shortDays) && Kotlin.equals(this.months, other.months) && Kotlin.equals(this.shortMonths, other.shortMonths)))));
  };
  function Locales() {
    Locales$Companion_getInstance();
  }
  function Locales$Companion() {
    Locales$Companion_instance = this;
  }
  Locales$Companion.prototype.locale_o23mdh$ = function (dateTime, date, time, periods, days, shortDays, months, shortMonths) {
    if (dateTime === void 0)
      dateTime = '%x, %X';
    if (date === void 0)
      date = '%-m/%-d/%Y';
    if (time === void 0)
      time = '%-I:%M:%S %p';
    if (periods === void 0)
      periods = listOf(['AM', 'PM']);
    if (days === void 0)
      days = listOf(['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday']);
    if (shortDays === void 0)
      shortDays = listOf(['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat']);
    if (months === void 0)
      months = listOf(['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December']);
    if (shortMonths === void 0)
      shortMonths = listOf(['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']);
    return new TimeLocale(dateTime, date, time, periods, days, shortDays, months, shortMonths);
  };
  Locales$Companion.prototype.defaultLocale = function () {
    return this.locale_o23mdh$();
  };
  Locales$Companion.prototype.ca_ES = function () {
    return this.locale_o23mdh$('%A, %e de %B de %Y, %X', '%d/%m/%Y', '%H:%M:%S', listOf(['AM', 'PM']), listOf(['diumenge', 'dilluns', 'dimarts', 'dimecres', 'dijous', 'divendres', 'dissabte']), listOf(['dg.', 'dl.', 'dt.', 'dc.', 'dj.', 'dv.', 'ds.']), listOf(['gener', 'febrer', 'mar\xE7', 'abril', 'maig', 'juny', 'juliol', 'agost', 'setembre', 'octubre', 'novembre', 'desembre']), listOf(['gen.', 'febr.', 'mar\xE7', 'abr.', 'maig', 'juny', 'jul.', 'ag.', 'set.', 'oct.', 'nov.', 'des.']));
  };
  Locales$Companion.prototype.cs_CZ = function () {
    return this.locale_o23mdh$('%A,%e.%B %Y, %X', '%-d.%-m.%Y', '%H:%M:%S', listOf(['AM', 'PM']), listOf(['ned\u011Ble', 'pond\u011Bl\xED', '\xFAter\xFD', 'st\u0159eda', '\u010Dvrtek', 'p\xE1tek', 'sobota']), listOf(['ne.', 'po.', '\xFAt.', 'st.', '\u010Dt.', 'p\xE1.', 'so.']), listOf(['leden', '\xFAnor', 'b\u0159ezen', 'duben', 'kv\u011Bten', '\u010Derven', '\u010Dervenec', 'srpen', 'z\xE1\u0159\xED', '\u0159\xEDjen', 'listopad', 'prosinec']), listOf(['led', '\xFAno', 'b\u0159ez', 'dub', 'kv\u011B', '\u010Der', '\u010Derv', 'srp', 'z\xE1\u0159', '\u0159\xEDj', 'list', 'pros']));
  };
  Locales$Companion.prototype.de_CH = function () {
    return this.locale_o23mdh$('%A, der %e. %B %Y, %X', '%d.%m.%Y', '%H:%M:%S', listOf(['AM', 'PM']), listOf(['Sonntag', 'Montag', 'Dienstag', 'Mittwoch', 'Donnerstag', 'Freitag', 'Samstag']), listOf(['So', 'Mo', 'Di', 'Mi', 'Do', 'Fr', 'Sa']), listOf(['Januar', 'Februar', 'M\xE4rz', 'April', 'Mai', 'Juni', 'Juli', 'August', 'September', 'Oktober', 'November', 'Dezember']), listOf(['Jan', 'Feb', 'Mrz', 'Apr', 'Mai', 'Jun', 'Jul', 'Aug', 'Sep', 'Okt', 'Nov', 'Dez']));
  };
  Locales$Companion.prototype.de_DE = function () {
    return this.locale_o23mdh$('%A, der %e. %B %Y, %X', '%d.%m.%Y', '%H:%M:%S', listOf(['AM', 'PM']), listOf(['Sonntag', 'Montag', 'Dienstag', 'Mittwoch', 'Donnerstag', 'Freitag', 'Samstag']), listOf(['So', 'Mo', 'Di', 'Mi', 'Do', 'Fr', 'Sa']), listOf(['Januar', 'Februar', 'M\xE4rz', 'April', 'Mai', 'Juni', 'Juli', 'August', 'September', 'Oktober', 'November', 'Dezember']), listOf(['Jan', 'Feb', 'Mrz', 'Apr', 'Mai', 'Jun', 'Jul', 'Aug', 'Sep', 'Okt', 'Nov', 'Dez']));
  };
  Locales$Companion.prototype.en_CA = function () {
    return this.locale_o23mdh$('%a %b %e %X %Y', '%Y-%m-%d', '%H:%M:%S', listOf(['AM', 'PM']), listOf(['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday']), listOf(['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat']), listOf(['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December']), listOf(['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']));
  };
  Locales$Companion.prototype.en_GB = function () {
    return this.locale_o23mdh$('%a %e %b %X %Y', '%d/%m/%Y', '%H:%M:%S', listOf(['AM', 'PM']), listOf(['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday']), listOf(['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat']), listOf(['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December']), listOf(['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']));
  };
  Locales$Companion.prototype.en_US = function () {
    return this.locale_o23mdh$('%x, %X', '%-m/%-d/%Y', '%-I:%M:%S %p', listOf(['AM', 'PM']), listOf(['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday']), listOf(['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat']), listOf(['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December']), listOf(['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']));
  };
  Locales$Companion.prototype.es_ES = function () {
    return this.locale_o23mdh$('%A, %e de %B de %Y, %X', '%d/%m/%Y', '%H:%M:%S', listOf(['AM', 'PM']), listOf(['domingo', 'lunes', 'martes', 'mi\xE9rcoles', 'jueves', 'viernes', 's\xE1bado']), listOf(['dom', 'lun', 'mar', 'mi\xE9', 'jue', 'vie', 's\xE1b']), listOf(['enero', 'febrero', 'marzo', 'abril', 'mayo', 'junio', 'julio', 'agosto', 'septiembre', 'octubre', 'noviembre', 'diciembre']), listOf(['ene', 'feb', 'mar', 'abr', 'may', 'jun', 'jul', 'ago', 'sep', 'oct', 'nov', 'dic']));
  };
  Locales$Companion.prototype.es_MX = function () {
    return this.locale_o23mdh$('%x, %X', '%d/%m/%Y', '%-I:%M:%S %p', listOf(['AM', 'PM']), listOf(['domingo', 'lunes', 'martes', 'mi\xE9rcoles', 'jueves', 'viernes', 's\xE1bado']), listOf(['dom', 'lun', 'mar', 'mi\xE9', 'jue', 'vie', 's\xE1b']), listOf(['enero', 'febrero', 'marzo', 'abril', 'mayo', 'junio', 'julio', 'agosto', 'septiembre', 'octubre', 'noviembre', 'diciembre']), listOf(['ene', 'feb', 'mar', 'abr', 'may', 'jun', 'jul', 'ago', 'sep', 'oct', 'nov', 'dic']));
  };
  Locales$Companion.prototype.fi_FI = function () {
    return this.locale_o23mdh$('%A, %-d. %Bta %Y klo %X', '%-d.%-m.%Y', '%H:%M:%S', listOf(['a.m.', 'p.m.']), listOf(['sunnuntai', 'maanantai', 'tiistai', 'keskiviikko', 'torstai', 'perjantai', 'lauantai']), listOf(['Su', 'Ma', 'Ti', 'Ke', 'To', 'Pe', 'La']), listOf(['tammikuu', 'helmikuu', 'maaliskuu', 'huhtikuu', 'toukokuu', 'kes\xE4kuu', 'hein\xE4kuu', 'elokuu', 'syyskuu', 'lokakuu', 'marraskuu', 'joulukuu']), listOf(['Tammi', 'Helmi', 'Maalis', 'Huhti', 'Touko', 'Kes\xE4', 'Hein\xE4', 'Elo', 'Syys', 'Loka', 'Marras', 'Joulu']));
  };
  Locales$Companion.prototype.fr_CA = function () {
    return this.locale_o23mdh$('%a %e %b %Y %X', '%Y-%m-%d', '%H:%M:%S', listOf(['', '']), listOf(['dimanche', 'lundi', 'mardi', 'mercredi', 'jeudi', 'vendredi', 'samedi']), listOf(['dim', 'lun', 'mar', 'mer', 'jeu', 'ven', 'sam']), listOf(['janvier', 'f\xE9vrier', 'mars', 'avril', 'mai', 'juin', 'juillet', 'ao\xFBt', 'septembre', 'octobre', 'novembre', 'd\xE9cembre']), listOf(['jan', 'f\xE9v', 'mar', 'avr', 'mai', 'jui', 'jul', 'ao\xFB', 'sep', 'oct', 'nov', 'd\xE9c']));
  };
  Locales$Companion.prototype.fr_FR = function () {
    return this.locale_o23mdh$('%A, le %e %B %Y, %X', '%d/%m/%Y', '%H:%M:%S', listOf(['AM', 'PM']), listOf(['dimanche', 'lundi', 'mardi', 'mercredi', 'jeudi', 'vendredi', 'samedi']), listOf(['dim.', 'lun.', 'mar.', 'mer.', 'jeu.', 'ven.', 'sam.']), listOf(['janvier', 'f\xE9vrier', 'mars', 'avril', 'mai', 'juin', 'juillet', 'ao\xFBt', 'septembre', 'octobre', 'novembre', 'd\xE9cembre']), listOf(['janv.', 'f\xE9vr.', 'mars', 'avr.', 'mai', 'juin', 'juil.', 'ao\xFBt', 'sept.', 'oct.', 'nov.', 'd\xE9c.']));
  };
  Locales$Companion.prototype.he_IL = function () {
    return this.locale_o23mdh$('%A, %e \u05D1%B %Y %X', '%d.%m.%Y', '%H:%M:%S', listOf(['AM', 'PM']), listOf(['\u05E8\u05D0\u05E9\u05D5\u05DF', '\u05E9\u05E0\u05D9', '\u05E9\u05DC\u05D9\u05E9\u05D9', '\u05E8\u05D1\u05D9\u05E2\u05D9', '\u05D7\u05DE\u05D9\u05E9\u05D9', '\u05E9\u05D9\u05E9\u05D9', '\u05E9\u05D1\u05EA']), listOf(['\u05D0\u05F3', '\u05D1\u05F3', '\u05D2\u05F3', '\u05D3\u05F3', '\u05D4\u05F3', '\u05D5\u05F3', '\u05E9\u05F3']), listOf(['\u05D9\u05E0\u05D5\u05D0\u05E8', '\u05E4\u05D1\u05E8\u05D5\u05D0\u05E8', '\u05DE\u05E8\u05E5', '\u05D0\u05E4\u05E8\u05D9\u05DC', '\u05DE\u05D0\u05D9', '\u05D9\u05D5\u05E0\u05D9', '\u05D9\u05D5\u05DC\u05D9', '\u05D0\u05D5\u05D2\u05D5\u05E1\u05D8', '\u05E1\u05E4\u05D8\u05DE\u05D1\u05E8', '\u05D0\u05D5\u05E7\u05D8\u05D5\u05D1\u05E8', '\u05E0\u05D5\u05D1\u05DE\u05D1\u05E8', '\u05D3\u05E6\u05DE\u05D1\u05E8']), listOf(['\u05D9\u05E0\u05D5\u05F3', '\u05E4\u05D1\u05E8\u05F3', '\u05DE\u05E8\u05E5', '\u05D0\u05E4\u05E8\u05F3', '\u05DE\u05D0\u05D9', '\u05D9\u05D5\u05E0\u05D9', '\u05D9\u05D5\u05DC\u05D9', '\u05D0\u05D5\u05D2\u05F3', '\u05E1\u05E4\u05D8\u05F3', '\u05D0\u05D5\u05E7\u05F3', '\u05E0\u05D5\u05D1\u05F3', '\u05D3\u05E6\u05DE\u05F3']));
  };
  Locales$Companion.prototype.hu_HU = function () {
    return this.locale_o23mdh$('%Y. %B %-e., %A %X', '%Y. %m. %d.', '%H:%M:%S', listOf(['de.', 'du.']), listOf(['vas\xE1rnap', 'h\xE9tf\u0151', 'kedd', 'szerda', 'cs\xFCt\xF6rt\xF6k', 'p\xE9ntek', 'szombat']), listOf(['V', 'H', 'K', 'Sze', 'Cs', 'P', 'Szo']), listOf(['janu\xE1r', 'febru\xE1r', 'm\xE1rcius', '\xE1prilis', 'm\xE1jus', 'j\xFAnius', 'j\xFAlius', 'augusztus', 'szeptember', 'okt\xF3ber', 'november', 'december']), listOf(['jan.', 'feb.', 'm\xE1r.', '\xE1pr.', 'm\xE1j.', 'j\xFAn.', 'j\xFAl.', 'aug.', 'szept.', 'okt.', 'nov.', 'dec.']));
  };
  Locales$Companion.prototype.it_IT = function () {
    return this.locale_o23mdh$('%A %e %B %Y, %X', '%d/%m/%Y', '%H:%M:%S', listOf(['AM', 'PM']), listOf(['Domenica', 'Luned\xEC', 'Marted\xEC', 'Mercoled\xEC', 'Gioved\xEC', 'Venerd\xEC', 'Sabato']), listOf(['Dom', 'Lun', 'Mar', 'Mer', 'Gio', 'Ven', 'Sab']), listOf(['Gennaio', 'Febbraio', 'Marzo', 'Aprile', 'Maggio', 'Giugno', 'Luglio', 'Agosto', 'Settembre', 'Ottobre', 'Novembre', 'Dicembre']), listOf(['Gen', 'Feb', 'Mar', 'Apr', 'Mag', 'Giu', 'Lug', 'Ago', 'Set', 'Ott', 'Nov', 'Dic']));
  };
  Locales$Companion.prototype.ja_JP = function () {
    return this.locale_o23mdh$('%Y %b %e %a %X', '%Y/%m/%d', '%H:%M:%S', listOf(['AM', 'PM']), listOf(['\u65E5\u66DC\u65E5', '\u6708\u66DC\u65E5', '\u706B\u66DC\u65E5', '\u6C34\u66DC\u65E5', '\u6728\u66DC\u65E5', '\u91D1\u66DC\u65E5', '\u571F\u66DC\u65E5']), listOf(['\u65E5', '\u6708', '\u706B', '\u6C34', '\u6728', '\u91D1', '\u571F']), listOf(['\u7766\u6708', '\u5982\u6708', '\u5F25\u751F', '\u536F\u6708', '\u7690\u6708', '\u6C34\u7121\u6708', '\u6587\u6708', '\u8449\u6708', '\u9577\u6708', '\u795E\u7121\u6708', '\u971C\u6708', '\u5E2B\u8D70']), listOf(['1\u6708', '2\u6708', '3\u6708', '4\u6708', '5\u6708', '6\u6708', '7\u6708', '8\u6708', '9\u6708', '10\u6708', '11\u6708', '12\u6708']));
  };
  Locales$Companion.prototype.ko_KR = function () {
    return this.locale_o23mdh$('%Y/%m/%d %a %X', '%Y/%m/%d', '%H:%M:%S', listOf(['\uC624\uC804', '\uC624\uD6C4']), listOf(['\uC77C\uC694\uC77C', '\uC6D4\uC694\uC77C', '\uD654\uC694\uC77C', '\uC218\uC694\uC77C', '\uBAA9\uC694\uC77C', '\uAE08\uC694\uC77C', '\uD1A0\uC694\uC77C']), listOf(['\uC77C', '\uC6D4', '\uD654', '\uC218', '\uBAA9', '\uAE08', '\uD1A0']), listOf(['1\uC6D4', '2\uC6D4', '3\uC6D4', '4\uC6D4', '5\uC6D4', '6\uC6D4', '7\uC6D4', '8\uC6D4', '9\uC6D4', '10\uC6D4', '11\uC6D4', '12\uC6D4']), listOf(['1\uC6D4', '2\uC6D4', '3\uC6D4', '4\uC6D4', '5\uC6D4', '6\uC6D4', '7\uC6D4', '8\uC6D4', '9\uC6D4', '10\uC6D4', '11\uC6D4', '12\uC6D4']));
  };
  Locales$Companion.prototype.mk_MK = function () {
    return this.locale_o23mdh$('%A, %e %B %Y \u0433. %X', '%d.%m.%Y', '%H:%M:%S', listOf(['AM', 'PM']), listOf(['\u043D\u0435\u0434\u0435\u043B\u0430', '\u043F\u043E\u043D\u0435\u0434\u0435\u043B\u043D\u0438\u043A', '\u0432\u0442\u043E\u0440\u043D\u0438\u043A', '\u0441\u0440\u0435\u0434\u0430', '\u0447\u0435\u0442\u0432\u0440\u0442\u043E\u043A', '\u043F\u0435\u0442\u043E\u043A', '\u0441\u0430\u0431\u043E\u0442\u0430']), listOf(['\u043D\u0435\u0434', '\u043F\u043E\u043D', '\u0432\u0442\u043E', '\u0441\u0440\u0435', '\u0447\u0435\u0442', '\u043F\u0435\u0442', '\u0441\u0430\u0431']), listOf(['\u0458\u0430\u043D\u0443\u0430\u0440\u0438', '\u0444\u0435\u0432\u0440\u0443\u0430\u0440\u0438', '\u043C\u0430\u0440\u0442', '\u0430\u043F\u0440\u0438\u043B', '\u043C\u0430\u0458', '\u0458\u0443\u043D\u0438', '\u0458\u0443\u043B\u0438', '\u0430\u0432\u0433\u0443\u0441\u0442', '\u0441\u0435\u043F\u0442\u0435\u043C\u0432\u0440\u0438', '\u043E\u043A\u0442\u043E\u043C\u0432\u0440\u0438', '\u043D\u043E\u0435\u043C\u0432\u0440\u0438', '\u0434\u0435\u043A\u0435\u043C\u0432\u0440\u0438']), listOf(['\u0458\u0430\u043D', '\u0444\u0435\u0432', '\u043C\u0430\u0440', '\u0430\u043F\u0440', '\u043C\u0430\u0458', '\u0458\u0443\u043D', '\u0458\u0443\u043B', '\u0430\u0432\u0433', '\u0441\u0435\u043F', '\u043E\u043A\u0442', '\u043D\u043E\u0435', '\u0434\u0435\u043A']));
  };
  Locales$Companion.prototype.nl_NL = function () {
    return this.locale_o23mdh$('%a %e %B %Y %T', '%d-%m-%Y', '%H:%M:%S', listOf(['AM', 'PM']), listOf(['zondag', 'maandag', 'dinsdag', 'woensdag', 'donderdag', 'vrijdag', 'zaterdag']), listOf(['zo', 'ma', 'di', 'wo', 'do', 'vr', 'za']), listOf(['januari', 'februari', 'maart', 'april', 'mei', 'juni', 'juli', 'augustus', 'september', 'oktober', 'november', 'december']), listOf(['jan', 'feb', 'mrt', 'apr', 'mei', 'jun', 'jul', 'aug', 'sep', 'okt', 'nov', 'dec']));
  };
  Locales$Companion.prototype.pl_PL = function () {
    return this.locale_o23mdh$('%A, %e %B %Y, %X', '%d/%m/%Y', '%H:%M:%S', listOf(['AM', 'PM']), listOf(['Niedziela', 'Poniedzia\u0142ek', 'Wtorek', '\u015Aroda', 'Czwartek', 'Pi\u0105tek', 'Sobota']), listOf(['Niedz.', 'Pon.', 'Wt.', '\u015Ar.', 'Czw.', 'Pt.', 'Sob.']), listOf(['Stycze\u0144', 'Luty', 'Marzec', 'Kwiecie\u0144', 'Maj', 'Czerwiec', 'Lipiec', 'Sierpie\u0144', 'Wrzesie\u0144', 'Pa\u017Adziernik', 'Listopad', 'Grudzie\u0144']), listOf(['Stycz.', 'Luty', 'Marz.', 'Kwie.', 'Maj', 'Czerw.', 'Lipc.', 'Sierp.', 'Wrz.', 'Pa\u017Adz.', 'Listop.', 'Grudz.']));
  };
  Locales$Companion.prototype.pt_BR = function () {
    return this.locale_o23mdh$('%A, %e de %B de %Y. %X', '%d/%m/%Y', '%H:%M:%S', listOf(['AM', 'PM']), listOf(['Domingo', 'Segunda', 'Ter\xE7a', 'Quarta', 'Quinta', 'Sexta', 'S\xE1bado']), listOf(['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'S\xE1b']), listOf(['Janeiro', 'Fevereiro', 'Mar\xE7o', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro']), listOf(['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez']));
  };
  Locales$Companion.prototype.ru_RU = function () {
    return this.locale_o23mdh$('%A, %e %B %Y \u0433. %X', '%d.%m.%Y', '%H:%M:%S', listOf(['AM', 'PM']), listOf(['\u0432\u043E\u0441\u043A\u0440\u0435\u0441\u0435\u043D\u044C\u0435', '\u043F\u043E\u043D\u0435\u0434\u0435\u043B\u044C\u043D\u0438\u043A', '\u0432\u0442\u043E\u0440\u043D\u0438\u043A', '\u0441\u0440\u0435\u0434\u0430', '\u0447\u0435\u0442\u0432\u0435\u0440\u0433', '\u043F\u044F\u0442\u043D\u0438\u0446\u0430', '\u0441\u0443\u0431\u0431\u043E\u0442\u0430']), listOf(['\u0432\u0441', '\u043F\u043D', '\u0432\u0442', '\u0441\u0440', '\u0447\u0442', '\u043F\u0442', '\u0441\u0431']), listOf(['\u044F\u043D\u0432\u0430\u0440\u044F', '\u0444\u0435\u0432\u0440\u0430\u043B\u044F', '\u043C\u0430\u0440\u0442\u0430', '\u0430\u043F\u0440\u0435\u043B\u044F', '\u043C\u0430\u044F', '\u0438\u044E\u043D\u044F', '\u0438\u044E\u043B\u044F', '\u0430\u0432\u0433\u0443\u0441\u0442\u0430', '\u0441\u0435\u043D\u0442\u044F\u0431\u0440\u044F', '\u043E\u043A\u0442\u044F\u0431\u0440\u044F', '\u043D\u043E\u044F\u0431\u0440\u044F', '\u0434\u0435\u043A\u0430\u0431\u0440\u044F']), listOf(['\u044F\u043D\u0432', '\u0444\u0435\u0432', '\u043C\u0430\u0440', '\u0430\u043F\u0440', '\u043C\u0430\u0439', '\u0438\u044E\u043D', '\u0438\u044E\u043B', '\u0430\u0432\u0433', '\u0441\u0435\u043D', '\u043E\u043A\u0442', '\u043D\u043E\u044F', '\u0434\u0435\u043A']));
  };
  Locales$Companion.prototype.sv_SE = function () {
    return this.locale_o23mdh$('%A den %d %B %Y %X', '%Y-%m-%d', '%H:%M:%S', listOf(['fm', 'em']), listOf(['S\xF6ndag', 'M\xE5ndag', 'Tisdag', 'Onsdag', 'Torsdag', 'Fredag', 'L\xF6rdag']), listOf(['S\xF6n', 'M\xE5n', 'Tis', 'Ons', 'Tor', 'Fre', 'L\xF6r']), listOf(['Januari', 'Februari', 'Mars', 'April', 'Maj', 'Juni', 'Juli', 'Augusti', 'September', 'Oktober', 'November', 'December']), listOf(['Jan', 'Feb', 'Mar', 'Apr', 'Maj', 'Jun', 'Jul', 'Aug', 'Sep', 'Okt', 'Nov', 'Dec']));
  };
  Locales$Companion.prototype.uk_UA = function () {
    return this.locale_o23mdh$('%A, %e %B %Y \u0440. %X', '%d.%m.%Y', '%H:%M:%S', listOf(['\u0434\u043F', '\u043F\u043F']), listOf(['\u043D\u0435\u0434\u0456\u043B\u044F', '\u043F\u043E\u043D\u0435\u0434\u0456\u043B\u043E\u043A', '\u0432\u0456\u0432\u0442\u043E\u0440\u043E\u043A', '\u0441\u0435\u0440\u0435\u0434\u0430', '\u0447\u0435\u0442\u0432\u0435\u0440', "\u043F'\u044F\u0442\u043D\u0438\u0446\u044F", '\u0441\u0443\u0431\u043E\u0442\u0430']), listOf(['\u043D\u0434', '\u043F\u043D', '\u0432\u0442', '\u0441\u0440', '\u0447\u0442', '\u043F\u0442', '\u0441\u0431']), listOf(['\u0441\u0456\u0447\u043D\u044F', '\u043B\u044E\u0442\u043E\u0433\u043E', '\u0431\u0435\u0440\u0435\u0437\u043D\u044F', '\u043A\u0432\u0456\u0442\u043D\u044F', '\u0442\u0440\u0430\u0432\u043D\u044F', '\u0447\u0435\u0440\u0432\u043D\u044F', '\u043B\u0438\u043F\u043D\u044F', '\u0441\u0435\u0440\u043F\u043D\u044F', '\u0432\u0435\u0440\u0435\u0441\u043D\u044F', '\u0436\u043E\u0432\u0442\u043D\u044F', '\u043B\u0438\u0441\u0442\u043E\u043F\u0430\u0434\u0430', '\u0433\u0440\u0443\u0434\u043D\u044F']), listOf(['\u0441\u0456\u0447.', '\u043B\u044E\u0442.', '\u0431\u0435\u0440.', '\u043A\u0432\u0456\u0442.', '\u0442\u0440\u0430\u0432.', '\u0447\u0435\u0440\u0432.', '\u043B\u0438\u043F.', '\u0441\u0435\u0440\u043F.', '\u0432\u0435\u0440.', '\u0436\u043E\u0432\u0442.', '\u043B\u0438\u0441\u0442.', '\u0433\u0440\u0443\u0434.']));
  };
  Locales$Companion.prototype.zh_CN = function () {
    return this.locale_o23mdh$('%x %A %X', '%Y\u5E74%-m\u6708%-d\u65E5', '%H:%M:%S', listOf(['\u4E0A\u5348', '\u4E0B\u5348']), listOf(['\u661F\u671F\u65E5', '\u661F\u671F\u4E00', '\u661F\u671F\u4E8C', '\u661F\u671F\u4E09', '\u661F\u671F\u56DB', '\u661F\u671F\u4E94', '\u661F\u671F\u516D']), listOf(['\u5468\u65E5', '\u5468\u4E00', '\u5468\u4E8C', '\u5468\u4E09', '\u5468\u56DB', '\u5468\u4E94', '\u5468\u516D']), listOf(['\u4E00\u6708', '\u4E8C\u6708', '\u4E09\u6708', '\u56DB\u6708', '\u4E94\u6708', '\u516D\u6708', '\u4E03\u6708', '\u516B\u6708', '\u4E5D\u6708', '\u5341\u6708', '\u5341\u4E00\u6708', '\u5341\u4E8C\u6708']), listOf(['\u4E00\u6708', '\u4E8C\u6708', '\u4E09\u6708', '\u56DB\u6708', '\u4E94\u6708', '\u516D\u6708', '\u4E03\u6708', '\u516B\u6708', '\u4E5D\u6708', '\u5341\u6708', '\u5341\u4E00\u6708', '\u5341\u4E8C\u6708']));
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
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$timeFormat = package$data2viz.timeFormat || (package$data2viz.timeFormat = {});
  package$timeFormat.ParseDate = ParseDate;
  Object.defineProperty(package$timeFormat, 'defaultLocale', {
    get: function () {
      return defaultLocale;
    }
  });
  package$timeFormat.autoFormat = autoFormat;
  package$timeFormat.format_61zpoe$ = format;
  package$timeFormat.parse_61zpoe$ = parse;
  package$timeFormat.Locale = Locale;
  package$timeFormat.newYear_s8ev37$ = newYear;
  Object.defineProperty(package$timeFormat, 'pads', {
    get: function () {
      return pads;
    }
  });
  Object.defineProperty(package$timeFormat, 'numberRe', {
    get: function () {
      return numberRe;
    }
  });
  package$timeFormat.pad_jl0c9m$ = pad;
  package$timeFormat.requote_61zpoe$ = requote;
  package$timeFormat.formatRe_mhpeer$ = formatRe;
  package$timeFormat.formatLookup_mhpeer$ = formatLookup;
  package$timeFormat.TimeLocale = TimeLocale;
  Object.defineProperty(Locales, 'Companion', {
    get: Locales$Companion_getInstance
  });
  package$timeFormat.Locales = Locales;
  defaultLocale = new Locale();
  pads = mapOf([new Pair(toBoxedChar(45), ''), new Pair(toBoxedChar(95), ' '), new Pair(toBoxedChar(48), '0')]);
  numberRe = Regex_init('^\\s*\\d+');
  Kotlin.defineModule('d2v-timeFormat-js', _);
  return _;
}));

//# sourceMappingURL=d2v-timeFormat-js.js.map
