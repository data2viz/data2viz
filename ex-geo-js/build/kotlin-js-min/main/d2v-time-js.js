(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'd2v-time-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'd2v-time-js'.");
    }
    root['d2v-time-js'] = factory(typeof this['d2v-time-js'] === 'undefined' ? {} : this['d2v-time-js'], kotlin);
  }
}(this, function (_, Kotlin) {
  'use strict';
  var $$importsForInline$$ = _.$$importsForInline$$ || (_.$$importsForInline$$ = {});
  var L1000 = Kotlin.Long.fromInt(1000);
  var L60000 = Kotlin.Long.fromInt(60000);
  var L3600000 = Kotlin.Long.fromInt(3600000);
  var L86400000 = Kotlin.Long.fromInt(86400000);
  var L604800000 = Kotlin.Long.fromInt(604800000);
  var L2592000000 = new Kotlin.Long(-1702967296, 0);
  var L31536000000 = new Kotlin.Long(1471228928, 7);
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var L1 = Kotlin.Long.ONE;
  var toList = Kotlin.kotlin.collections.toList_7wnvza$;
  var defineInlineFunction = Kotlin.defineInlineFunction;
  Day.prototype = Object.create(Interval.prototype);
  Day.prototype.constructor = Day;
  Hour.prototype = Object.create(Interval.prototype);
  Hour.prototype.constructor = Hour;
  Millisecond.prototype = Object.create(Interval.prototype);
  Millisecond.prototype.constructor = Millisecond;
  Minute.prototype = Object.create(Interval.prototype);
  Minute.prototype.constructor = Minute;
  Month.prototype = Object.create(Interval.prototype);
  Month.prototype.constructor = Month;
  Second.prototype = Object.create(Interval.prototype);
  Second.prototype.constructor = Second;
  Weekday.prototype = Object.create(Interval.prototype);
  Weekday.prototype.constructor = Weekday;
  Year.prototype = Object.create(Interval.prototype);
  Year.prototype.constructor = Year;
  var durationSecond;
  var durationMinute;
  var durationHour;
  var durationDay;
  var durationWeek;
  var durationMonth;
  var durationYear;
  function date(year, month, day, hour, minute, second, millisecond) {
    if (year === void 0)
      year = 0;
    if (month === void 0)
      month = 1;
    if (day === void 0)
      day = 1;
    if (hour === void 0)
      hour = 0;
    if (minute === void 0)
      minute = 0;
    if (second === void 0)
      second = 0;
    if (millisecond === void 0)
      millisecond = 0;
    return Date_init_0(year, month, day, hour, minute, second, millisecond);
  }
  function date_0(date) {
    return Date_init_0(date.year(), date.month(), date.dayOfMonth(), date.hour(), date.minute(), date.second(), date.millisecond());
  }
  function date_1() {
    return Date_init_0(currentYear(), currentMonth(), currentDay(), currentHour(), currentMinute(), currentSecond(), 0);
  }
  function currentYear() {
    return Date_init().year();
  }
  function currentMonth() {
    return Date_init().month();
  }
  function currentDay() {
    return Date_init().dayOfMonth();
  }
  function currentHour() {
    return Date_init().hour();
  }
  function currentMinute() {
    return Date_init().minute();
  }
  function currentSecond() {
    return Date_init().second();
  }
  function Day() {
    Interval.call(this, Day_init$lambda, Day_init$lambda_0, Day_init$lambda_1, Day_init$lambda_2);
  }
  function Day_init$lambda(date) {
    date.setHour_za3lpa$(0);
    date.setMinute_za3lpa$(0);
    date.setSecond_za3lpa$(0);
    date.setMillisecond_za3lpa$(0);
    return date;
  }
  function Day_init$lambda_0(date, step) {
    date.plusDays_s8cxhz$(step);
    return date;
  }
  function Day_init$lambda_1(start, end) {
    return start.daysBetween_fw2154$(end).toInt();
  }
  function Day_init$lambda_2(date) {
    return date.dayOfMonth() - 1 | 0;
  }
  Day.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Day',
    interfaces: [Interval]
  };
  var timeDay;
  function Hour() {
    Interval.call(this, Hour_init$lambda, Hour_init$lambda_0, Hour_init$lambda_1, Hour_init$lambda_2);
  }
  function Hour_init$lambda(date) {
    date.setMinute_za3lpa$(0);
    date.setSecond_za3lpa$(0);
    date.setMillisecond_za3lpa$(0);
    return date;
  }
  function Hour_init$lambda_0(date, step) {
    date.plusHours_s8cxhz$(step);
    return date;
  }
  function Hour_init$lambda_1(start, end) {
    return start.hoursBetween_fw2154$(end).toInt();
  }
  function Hour_init$lambda_2(date) {
    return date.hour();
  }
  Hour.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Hour',
    interfaces: [Interval]
  };
  var timeHour;
  function Interval(floori, offseti, counti, field) {
    if (counti === void 0)
      counti = null;
    if (field === void 0)
      field = null;
    this.floori_jh5yfm$_0 = floori;
    this.offseti_agn825$_0 = offseti;
    this.counti_916a41$_0 = counti;
    this.field_arznjj$_0 = field;
  }
  Interval.prototype.floor_fw2154$ = function (date) {
    return this.floori_jh5yfm$_0(Date_init_2(date));
  };
  Interval.prototype.ceil_fw2154$ = function (date) {
    var newDate = Date_init_2(date.minusMilliseconds_za3lpa$(1));
    newDate = this.floori_jh5yfm$_0(newDate);
    newDate = this.offseti_agn825$_0(newDate, L1);
    newDate = this.floori_jh5yfm$_0(newDate);
    return newDate;
  };
  Interval.prototype.round_fw2154$ = function (date) {
    var d0 = this.floor_fw2154$(date);
    var d1 = this.ceil_fw2154$(date);
    var millisecondsBetween1 = d0.millisecondsBetween_fw2154$(date);
    var millisecondsBetween2 = date.millisecondsBetween_fw2154$(d1);
    return millisecondsBetween1.compareTo_11rb$(millisecondsBetween2) < 0 ? d0 : d1;
  };
  Interval.prototype.offset_1tn091$ = function (date, step) {
    if (step === void 0)
      step = L1;
    return this.offseti_agn825$_0(Date_init_2(date), step);
  };
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  Interval.prototype.range_4ahvhd$ = function (start, stop, step) {
    if (step === void 0)
      step = L1;
    var range = ArrayList_init();
    var current = this.ceil_fw2154$(start);
    if (step.toNumber() > 0) {
      while (current.isBefore_fw2154$(stop)) {
        range.add_11rb$(current);
        current = this.floori_jh5yfm$_0(this.offseti_agn825$_0(Date_init_2(current), step));
      }
    }
    return toList(range);
  };
  function Interval$filter$lambda(this$Interval, closure$test) {
    return function (date) {
      var newDate = date;
      this$Interval.floori_jh5yfm$_0(newDate);
      while (!closure$test(newDate)) {
        newDate = newDate.minusMilliseconds_za3lpa$(1);
        this$Interval.floori_jh5yfm$_0(newDate);
      }
      return newDate;
    };
  }
  function Interval$filter$lambda_0(this$Interval, closure$test) {
    return function (date, step) {
      var newStep = step.subtract(Kotlin.Long.fromInt(1));
      while (newStep.toNumber() >= 0) {
        newStep = newStep.dec();
        this$Interval.offseti_agn825$_0(date, L1);
        while (!closure$test(date)) {
          this$Interval.offseti_agn825$_0(date, L1);
        }
      }
      return date;
    };
  }
  Interval.prototype.filter_63boiv$ = function (test) {
    return new Interval(Interval$filter$lambda(this, test), Interval$filter$lambda_0(this, test));
  };
  function Interval$every$lambda(this$Interval, closure$step) {
    return function (d) {
      return this$Interval.field_arznjj$_0(d) % closure$step === 0;
    };
  }
  function Interval$every$lambda_0(this$Interval, closure$step) {
    return function (d) {
      return this$Interval.count_6v3okc$(date(1970, 1, 1), d) % closure$step === 0;
    };
  }
  var IllegalStateException_init = Kotlin.kotlin.IllegalStateException_init_pdl1vj$;
  var IllegalArgumentException_init = Kotlin.kotlin.IllegalArgumentException_init_pdl1vj$;
  Interval.prototype.every_za3lpa$ = function (step) {
    var tmp$;
    if (this.counti_916a41$_0 == null) {
      var message = 'The given Count function must not be null.';
      throw IllegalStateException_init(message.toString());
    }
    if (!(step > 0)) {
      var message_0 = ' The given Step parameter must be greater than zero.';
      throw IllegalArgumentException_init(message_0.toString());
    }
    if (step === 1)
      return this;
    if (this.field_arznjj$_0 != null) {
      tmp$ = this.filter_63boiv$(Interval$every$lambda(this, step));
    }
     else {
      tmp$ = this.filter_63boiv$(Interval$every$lambda_0(this, step));
    }
    return tmp$;
  };
  Interval.prototype.count_6v3okc$ = function (start, stop) {
    if (this.counti_916a41$_0 == null) {
      var message = 'The given Count function must not be null.';
      throw IllegalStateException_init(message.toString());
    }
    var from = this.floor_fw2154$(start);
    var to = this.floor_fw2154$(stop);
    return this.counti_916a41$_0(from, to);
  };
  Interval.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Interval',
    interfaces: []
  };
  function Millisecond() {
    Interval.call(this, Millisecond_init$lambda, Millisecond_init$lambda_0, Millisecond_init$lambda_1, Millisecond_init$lambda_2);
  }
  function Millisecond_init$lambda(date) {
    return date;
  }
  function Millisecond_init$lambda_0(date, step) {
    date.plusMilliseconds_s8cxhz$(step);
    return date;
  }
  function Millisecond_init$lambda_1(start, end) {
    return start.millisecondsBetween_fw2154$(end).toInt();
  }
  function Millisecond_init$lambda_2(date) {
    return date.millisecond();
  }
  Millisecond.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Millisecond',
    interfaces: [Interval]
  };
  var timeMillisecond;
  function Minute() {
    Interval.call(this, Minute_init$lambda, Minute_init$lambda_0, Minute_init$lambda_1, Minute_init$lambda_2);
  }
  function Minute_init$lambda(date) {
    date.setSecond_za3lpa$(0);
    date.setMillisecond_za3lpa$(0);
    return date;
  }
  function Minute_init$lambda_0(date, step) {
    date.plusMilliseconds_s8cxhz$(step.multiply(durationMinute));
    return date;
  }
  function Minute_init$lambda_1(start, end) {
    return start.millisecondsBetween_fw2154$(end).div(durationMinute).toInt();
  }
  function Minute_init$lambda_2(date) {
    return date.minute();
  }
  Minute.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Minute',
    interfaces: [Interval]
  };
  var timeMinute;
  function Month() {
    Interval.call(this, Month_init$lambda, Month_init$lambda_0, Month_init$lambda_1, Month_init$lambda_2);
  }
  function Month_init$lambda(date) {
    date.setDayOfMonth_za3lpa$(1);
    date.setHour_za3lpa$(0);
    date.setMinute_za3lpa$(0);
    date.setSecond_za3lpa$(0);
    date.setMillisecond_za3lpa$(0);
    return date;
  }
  function Month_init$lambda_0(date, step) {
    date.plusMonths_s8cxhz$(step);
    return date;
  }
  function Month_init$lambda_1(start, end) {
    return ((end.year() - start.year() | 0) * 12 | 0) + (end.month() - start.month()) | 0;
  }
  function Month_init$lambda_2(date) {
    return date.month() - 1 | 0;
  }
  Month.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Month',
    interfaces: [Interval]
  };
  var timeMonth;
  function Second() {
    Interval.call(this, Second_init$lambda, Second_init$lambda_0, Second_init$lambda_1, Second_init$lambda_2);
  }
  function Second_init$lambda(date) {
    date.setMillisecond_za3lpa$(0);
    return date;
  }
  function Second_init$lambda_0(date, step) {
    date.plusMilliseconds_s8cxhz$(step.multiply(durationSecond));
    return date;
  }
  function Second_init$lambda_1(start, end) {
    return start.millisecondsBetween_fw2154$(end).div(durationSecond).toInt();
  }
  function Second_init$lambda_2(date) {
    return date.second();
  }
  Second.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Second',
    interfaces: [Interval]
  };
  var timeSecond;
  function Weekday(day) {
    Interval.call(this, Weekday_init$lambda(day), Weekday_init$lambda_0, Weekday_init$lambda_1);
  }
  function Weekday_init$lambda(closure$day) {
    return function (date) {
      var dayofMonth = date.dayOfMonth() - (date.dayOfWeek() + 7 - closure$day | 0) % 7 + 1 | 0;
      if (dayofMonth >= 1) {
        date.setDayOfMonth_za3lpa$(dayofMonth);
      }
       else {
        date.plusDays_s8cxhz$(Kotlin.Long.fromInt(dayofMonth).subtract(Kotlin.Long.fromInt(2)));
      }
      date.setHour_za3lpa$(0);
      date.setMinute_za3lpa$(0);
      date.setSecond_za3lpa$(0);
      date.setMillisecond_za3lpa$(0);
      return date;
    };
  }
  function Weekday_init$lambda_0(date, step) {
    date.plusDays_s8cxhz$(Kotlin.Long.fromInt(7).multiply(step));
    return date;
  }
  function Weekday_init$lambda_1(start, end) {
    return start.daysBetween_fw2154$(end).toInt() / 7 | 0;
  }
  Weekday.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Weekday',
    interfaces: [Interval]
  };
  var timeMonday;
  var timeTuesday;
  var timeWednesday;
  var timeThursday;
  var timeFriday;
  var timeSaturday;
  var timeSunday;
  function Year() {
    Interval.call(this, Year_init$lambda, Year_init$lambda_0, Year_init$lambda_1, Year_init$lambda_2);
  }
  function Year_init$lambda(date) {
    date.setMonth_za3lpa$(1);
    date.setDayOfMonth_za3lpa$(1);
    date.setHour_za3lpa$(0);
    date.setMinute_za3lpa$(0);
    date.setSecond_za3lpa$(0);
    date.setMillisecond_za3lpa$(0);
    return date;
  }
  function Year_init$lambda_0(date, step) {
    date.plusYears_s8cxhz$(step);
    return date;
  }
  function Year_init$lambda_1(start, end) {
    return end.year() - start.year() | 0;
  }
  function Year_init$lambda_2(date) {
    return date.year();
  }
  Year.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Year',
    interfaces: [Interval]
  };
  var timeYear;
  function Date_0() {
    this.date_0 = null;
  }
  Date_0.prototype.toString = function () {
    return this.date_0.toString();
  };
  Date_0.prototype.minusMilliseconds_za3lpa$ = function (milliseconds) {
    return Date_init_1(new Date(this.date_0.getTime() - 1));
  };
  Date_0.prototype.isBefore_fw2154$ = function (otherDate) {
    return this.date_0.getTime() < otherDate.date_0.getTime();
  };
  Date_0.prototype.millisecondsBetween_fw2154$ = function (otherDate) {
    return Kotlin.Long.fromNumber(otherDate.date_0.getTime() - this.date_0.getTime());
  };
  Date_0.prototype.daysBetween_fw2154$ = function (otherDate) {
    return this.millisecondsBetween_fw2154$(otherDate).subtract(Kotlin.Long.fromInt(otherDate.getTimezoneOffset() - this.date_0.getTimezoneOffset() | 0).multiply(durationMinute)).div(durationDay);
  };
  Date_0.prototype.hoursBetween_fw2154$ = function (otherDate) {
    return this.millisecondsBetween_fw2154$(otherDate).subtract(Kotlin.Long.fromInt(otherDate.getTimezoneOffset() - this.date_0.getTimezoneOffset() | 0).multiply(durationMinute)).div(durationHour);
  };
  Date_0.prototype.getTimezoneOffset = function () {
    return this.date_0.getTimezoneOffset();
  };
  Date_0.prototype.plusMilliseconds_s8cxhz$ = function (milliseconds) {
    this.date_0 = new Date(this.date_0.getTime() + milliseconds.toNumber());
  };
  Date_0.prototype.plusHours_s8cxhz$ = function (hours) {
    this.date_0 = new Date(this.date_0.getTime() + hours.multiply(durationHour).toNumber());
  };
  Date_0.prototype.plusDays_s8cxhz$ = function (days) {
    this.date_0 = new Date(this.date_0.getTime() + days.multiply(durationDay).toNumber());
  };
  Date_0.prototype.plusMonths_s8cxhz$ = function (months) {
    var m = Kotlin.Long.fromInt(this.date_0.getMonth()).add(months).modulo(Kotlin.Long.fromInt(12)).toInt();
    var y = Kotlin.Long.fromInt(this.date_0.getMonth()).add(months).div(Kotlin.Long.fromInt(12)).toInt();
    this.date_0.setFullYear(this.date_0.getFullYear() + y | 0);
    this.date_0.setMonth(m);
  };
  Date_0.prototype.plusYears_s8cxhz$ = function (years) {
    this.date_0.setFullYear(this.date_0.getFullYear() + years.toInt() | 0);
  };
  Date_0.prototype.setMillisecond_za3lpa$ = function (millisecond) {
    this.date_0.setMilliseconds(millisecond);
  };
  Date_0.prototype.setSecond_za3lpa$ = function (second) {
    this.date_0.setSeconds(second);
  };
  Date_0.prototype.setMinute_za3lpa$ = function (minute) {
    this.date_0.setMinutes(minute);
  };
  Date_0.prototype.setHour_za3lpa$ = function (hour) {
    this.date_0.setHours(hour);
  };
  Date_0.prototype.setDayOfMonth_za3lpa$ = function (day) {
    this.date_0.setDate(day);
  };
  Date_0.prototype.setMonth_za3lpa$ = function (month) {
    this.date_0.setMonth(month - 1 | 0);
  };
  Date_0.prototype.setFullYear_za3lpa$ = function (year) {
    this.date_0.setFullYear(year);
  };
  Date_0.prototype.millisecond = function () {
    return this.date_0.getMilliseconds();
  };
  Date_0.prototype.second = function () {
    return this.date_0.getSeconds();
  };
  Date_0.prototype.minute = function () {
    return this.date_0.getMinutes();
  };
  Date_0.prototype.hour = function () {
    return this.date_0.getHours();
  };
  Date_0.prototype.dayOfWeek = function () {
    return this.date_0.getDay();
  };
  Date_0.prototype.dayOfMonth = function () {
    return this.date_0.getDate();
  };
  Date_0.prototype.dayOfYear = function () {
    return 1 + timeDay.count_6v3okc$(timeYear.floor_fw2154$(this), this) | 0;
  };
  Date_0.prototype.month = function () {
    return this.date_0.getMonth() + 1 | 0;
  };
  Date_0.prototype.year = function () {
    return this.date_0.getFullYear();
  };
  Date_0.prototype.getTime = function () {
    return this.date_0.getTime();
  };
  Date_0.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Date',
    interfaces: []
  };
  function Date_init($this) {
    $this = $this || Object.create(Date_0.prototype);
    Date_0.call($this);
    $this.date_0 = new Date();
    return $this;
  }
  function Date_init_0(year, month, day, hour, minute, second, millisecond, $this) {
    $this = $this || Object.create(Date_0.prototype);
    Date_0.call($this);
    if (0 <= year && year <= 99) {
      $this.date_0 = new Date(-1, month - 1 | 0, day, hour, minute, second, millisecond);
      $this.date_0.setFullYear(year);
    }
     else
      $this.date_0 = new Date(year, month - 1 | 0, day, hour, minute, second, millisecond);
    return $this;
  }
  function Date_init_1(date, $this) {
    $this = $this || Object.create(Date_0.prototype);
    Date_0.call($this);
    $this.date_0 = new Date(date.getTime());
    return $this;
  }
  function Date_init_2(date, $this) {
    $this = $this || Object.create(Date_0.prototype);
    Date_0.call($this);
    $this.date_0 = new Date(date.date_0.getTime());
    return $this;
  }
  var dateLocaleOptions = defineInlineFunction('d2v-time-js.io.data2viz.time.js.dateLocaleOptions_tfhmxk$', function (init) {
    var result = new Object();
    init(result);
    return result;
  });
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$time = package$data2viz.time || (package$data2viz.time = {});
  Object.defineProperty(package$time, 'durationSecond', {
    get: function () {
      return durationSecond;
    }
  });
  Object.defineProperty(package$time, 'durationMinute', {
    get: function () {
      return durationMinute;
    }
  });
  Object.defineProperty(package$time, 'durationHour', {
    get: function () {
      return durationHour;
    }
  });
  Object.defineProperty(package$time, 'durationDay', {
    get: function () {
      return durationDay;
    }
  });
  Object.defineProperty(package$time, 'durationWeek', {
    get: function () {
      return durationWeek;
    }
  });
  Object.defineProperty(package$time, 'durationMonth', {
    get: function () {
      return durationMonth;
    }
  });
  Object.defineProperty(package$time, 'durationYear', {
    get: function () {
      return durationYear;
    }
  });
  package$time.date_ui44o2$ = date;
  package$time.date_fw2154$ = date_0;
  package$time.date = date_1;
  package$time.currentYear = currentYear;
  package$time.currentMonth = currentMonth;
  package$time.currentDay = currentDay;
  package$time.currentHour = currentHour;
  package$time.currentMinute = currentMinute;
  package$time.currentSecond = currentSecond;
  package$time.Day = Day;
  Object.defineProperty(package$time, 'timeDay', {
    get: function () {
      return timeDay;
    }
  });
  package$time.Hour = Hour;
  Object.defineProperty(package$time, 'timeHour', {
    get: function () {
      return timeHour;
    }
  });
  package$time.Interval = Interval;
  package$time.Millisecond = Millisecond;
  Object.defineProperty(package$time, 'timeMillisecond', {
    get: function () {
      return timeMillisecond;
    }
  });
  package$time.Minute = Minute;
  Object.defineProperty(package$time, 'timeMinute', {
    get: function () {
      return timeMinute;
    }
  });
  package$time.Month = Month;
  Object.defineProperty(package$time, 'timeMonth', {
    get: function () {
      return timeMonth;
    }
  });
  package$time.Second = Second;
  Object.defineProperty(package$time, 'timeSecond', {
    get: function () {
      return timeSecond;
    }
  });
  package$time.Weekday = Weekday;
  Object.defineProperty(package$time, 'timeMonday', {
    get: function () {
      return timeMonday;
    }
  });
  Object.defineProperty(package$time, 'timeTuesday', {
    get: function () {
      return timeTuesday;
    }
  });
  Object.defineProperty(package$time, 'timeWednesday', {
    get: function () {
      return timeWednesday;
    }
  });
  Object.defineProperty(package$time, 'timeThursday', {
    get: function () {
      return timeThursday;
    }
  });
  Object.defineProperty(package$time, 'timeFriday', {
    get: function () {
      return timeFriday;
    }
  });
  Object.defineProperty(package$time, 'timeSaturday', {
    get: function () {
      return timeSaturday;
    }
  });
  Object.defineProperty(package$time, 'timeSunday', {
    get: function () {
      return timeSunday;
    }
  });
  package$time.Year = Year;
  Object.defineProperty(package$time, 'timeYear', {
    get: function () {
      return timeYear;
    }
  });
  package$time.Date_init = Date_init;
  package$time.Date_init_ui44o2$ = Date_init_0;
  package$time.Date_init_fw2154$ = Date_init_2;
  package$time.Date = Date_0;
  $$importsForInline$$['d2v-time-js'] = _;
  var package$js = package$time.js || (package$time.js = {});
  package$js.dateLocaleOptions_tfhmxk$ = dateLocaleOptions;
  durationSecond = L1000;
  durationMinute = L60000;
  durationHour = L3600000;
  durationDay = L86400000;
  durationWeek = L604800000;
  durationMonth = L2592000000;
  durationYear = L31536000000;
  timeDay = new Day();
  timeHour = new Hour();
  timeMillisecond = new Millisecond();
  timeMinute = new Minute();
  timeMonth = new Month();
  timeSecond = new Second();
  timeMonday = new Weekday(1);
  timeTuesday = new Weekday(2);
  timeWednesday = new Weekday(3);
  timeThursday = new Weekday(4);
  timeFriday = new Weekday(5);
  timeSaturday = new Weekday(6);
  timeSunday = new Weekday(7);
  timeYear = new Year();
  Kotlin.defineModule('d2v-time-js', _);
  return _;
}));

//# sourceMappingURL=d2v-time-js.js.map
