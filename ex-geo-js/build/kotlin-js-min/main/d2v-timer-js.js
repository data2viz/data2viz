(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'd2v-timer-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'd2v-timer-js'.");
    }
    root['d2v-timer-js'] = factory(typeof this['d2v-timer-js'] === 'undefined' ? {} : this['d2v-timer-js'], kotlin);
  }
}(this, function (_, Kotlin) {
  'use strict';
  var Unit = Kotlin.kotlin.Unit;
  var kotlin_js_internal_DoubleCompanionObject = Kotlin.kotlin.js.internal.DoubleCompanionObject;
  var toString = Kotlin.toString;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var getCallableRef = Kotlin.getCallableRef;
  var numberToInt = Kotlin.numberToInt;
  var StringBuilder = Kotlin.kotlin.text.StringBuilder;
  var throwCCE = Kotlin.throwCCE;
  var timeoutHandle;
  var pokeHandle;
  var frame;
  var pokeDelay;
  var taskHead;
  var taskTail;
  var clockLast;
  var clockNow;
  var clockSkew;
  function timer(delay, startTime, callback) {
    if (delay === void 0)
      delay = 0.0;
    if (startTime === void 0)
      startTime = now();
    var $receiver = new Timer();
    $receiver.restart_k9susy$(delay, startTime, callback);
    return $receiver;
  }
  function timeout$lambda$lambda(closure$callback) {
    return function ($receiver, time) {
      $receiver.stop();
      closure$callback($receiver, time);
      return Unit;
    };
  }
  function timeout(delay, startTime, callback) {
    if (delay === void 0)
      delay = 0.0;
    if (startTime === void 0)
      startTime = now();
    var $receiver = new Timer();
    $receiver.restart_k9susy$(delay, startTime, timeout$lambda$lambda(callback));
    return $receiver;
  }
  function interval$tick(elapsed) {
  }
  function interval$lambda$lambda(closure$callback) {
    return function ($receiver, time) {
      $receiver.stop();
      closure$callback($receiver, time);
      return Unit;
    };
  }
  function interval(delay, startTime, callback) {
    if (delay === void 0)
      delay = 0.0;
    if (startTime === void 0)
      startTime = now();
    var tick = interval$tick;
    var total = delay;
    var timer = new Timer();
    var $receiver = new Timer();
    $receiver.restart_k9susy$(delay, startTime, interval$lambda$lambda(callback));
    return $receiver;
  }
  function Timer() {
    this._time_8be2vx$ = 0.0;
    this._call_8be2vx$ = null;
    this._next_8be2vx$ = null;
  }
  Timer.prototype.restart_k9susy$ = function (delay, startTime, callback) {
    if (delay === void 0)
      delay = 0.0;
    if (startTime === void 0)
      startTime = now();
    var newTime = startTime + delay;
    if (this._next_8be2vx$ == null && taskTail !== this) {
      var tail = taskTail;
      if (tail != null) {
        tail._next_8be2vx$ = this;
      }
       else
        taskHead = this;
      taskTail = this;
    }
    this._call_8be2vx$ = callback;
    this._time_8be2vx$ = newTime;
    sleep();
  };
  Timer.prototype.stop = function () {
    if (this._call_8be2vx$ != null) {
      this._call_8be2vx$ = null;
      this._time_8be2vx$ = kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY;
      sleep();
    }
  };
  Timer.prototype.toString = function () {
    return 'Timer(_time=' + this._time_8be2vx$ + ',) _next=' + toString(this._next_8be2vx$);
  };
  Timer.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Timer',
    interfaces: []
  };
  function now() {
    if (clockNow === 0.0) {
      callInNextFrame(getCallableRef('clearNow', function () {
        return clearNow(), Unit;
      }));
      clockNow = delegateNow() + clockSkew;
    }
    return clockNow;
  }
  function clearNow() {
    clockNow = 0.0;
  }
  function timerFlush() {
    var tmp$;
    log('timerFlush');
    now();
    frame = frame + 1 | 0;
    var t = taskHead;
    var elapsed;
    while (t != null) {
      elapsed = clockNow - t._time_8be2vx$;
      if (elapsed >= 0) {
        (tmp$ = t._call_8be2vx$) != null ? tmp$(t, elapsed) : null;
      }
      t = t._next_8be2vx$;
    }
    frame = frame - 1 | 0;
  }
  function updateTimers() {
    var tmp$;
    var t0 = null;
    var t1 = taskHead;
    var t2;
    var time = kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY;
    var timerCount = 0;
    while (t1 != null) {
      timerCount = timerCount + 1 | 0;
      if (t1._call_8be2vx$ != null) {
        if (time > t1._time_8be2vx$) {
          time = t1._time_8be2vx$;
        }
        t0 = t1;
        t1 = t1._next_8be2vx$;
      }
       else {
        t2 = t1._next_8be2vx$;
        t1._next_8be2vx$ = null;
        if (t0 != null) {
          t0._next_8be2vx$ = t2;
          tmp$ = t2;
        }
         else {
          taskHead = t2;
          tmp$ = taskHead;
        }
        t1 = tmp$;
      }
    }
    taskTail = t0;
    log('after updateTimers, timerCount ' + timerCount);
    return time;
  }
  function sleep(time) {
    if (time === void 0)
      time = null;
    if (frame > 0)
      return;
    if (timeoutHandle != null) {
      clearTimeout(timeoutHandle);
      timeoutHandle = null;
    }
    if (time != null) {
      var delay = time - clockNow;
      if (delay > 24) {
        if (time < kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY) {
          timeoutHandle = setTimeout(getCallableRef('wake', function () {
            return wake(), Unit;
          }), numberToInt(delay));
        }
        if (pokeHandle != null) {
          clearInterval(pokeHandle);
          pokeHandle = null;
        }
        return;
      }
    }
    if (pokeHandle == null) {
      clockLast = clockNow;
      pokeHandle = setInterval(getCallableRef('updateSkew', function () {
        return updateSkew(), Unit;
      }), 1000);
    }
    frame = 1;
    callInNextFrame(getCallableRef('wake', function () {
      return wake(), Unit;
    }));
  }
  function updateSkew() {
    log('updateSkew');
    var now_0 = now();
    var delay = now_0 - clockLast;
    if (delay > 1000) {
      clockSkew -= delay;
      clockLast = now_0;
    }
  }
  function wake() {
    log('wake');
    clockLast = now();
    clockNow = clockLast + clockSkew;
    frame = 0;
    timeoutHandle = null;
    try {
      timerFlush();
    }
    finally {
      frame = 0;
      var time = updateTimers();
      sleep(time);
      clockNow = 0.0;
    }
  }
  function log(msg) {
  }
  function logTimers() {
    var sb = new StringBuilder('');
    var t = taskHead;
    var i = 0;
    while (t != null) {
      sb.append_gw00v9$(' t' + i + '[' + numberToInt(t._time_8be2vx$) + ']');
      t = t._next_8be2vx$;
      i = i + 1 | 0;
    }
    return sb.toString();
  }
  function setTimeout(handler, timeout) {
    return window.setTimeout(handler, timeout);
  }
  function clearTimeout(handle) {
    var tmp$, tmp$_0;
    tmp$_0 = typeof (tmp$ = handle) === 'number' ? tmp$ : throwCCE();
    window.clearTimeout(tmp$_0);
  }
  function setInterval(handler, interval) {
    return window.setInterval(handler, interval);
  }
  function clearInterval(handle) {
    var tmp$, tmp$_0;
    tmp$_0 = typeof (tmp$ = handle) === 'number' ? tmp$ : throwCCE();
    window.clearInterval(tmp$_0);
  }
  function callInNextFrame$lambda(closure$block) {
    return function (it) {
      closure$block();
      return Unit;
    };
  }
  function callInNextFrame(block) {
    window.requestAnimationFrame(callInNextFrame$lambda(block));
  }
  function delegateNow() {
    var tmp$;
    return performanceAvailable ? typeof (tmp$ = performance.now()) === 'number' ? tmp$ : throwCCE() : Date.now();
  }
  var performanceAvailable;
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$timer = package$data2viz.timer || (package$data2viz.timer = {});
  Object.defineProperty(package$timer, 'timeoutHandle_8be2vx$', {
    get: function () {
      return timeoutHandle;
    },
    set: function (value) {
      timeoutHandle = value;
    }
  });
  Object.defineProperty(package$timer, 'pokeHandle_8be2vx$', {
    get: function () {
      return pokeHandle;
    },
    set: function (value) {
      pokeHandle = value;
    }
  });
  Object.defineProperty(package$timer, 'frame_8be2vx$', {
    get: function () {
      return frame;
    },
    set: function (value) {
      frame = value;
    }
  });
  Object.defineProperty(package$timer, 'clockNow_8be2vx$', {
    get: function () {
      return clockNow;
    },
    set: function (value) {
      clockNow = value;
    }
  });
  Object.defineProperty(package$timer, 'clockSkew_8be2vx$', {
    get: function () {
      return clockSkew;
    },
    set: function (value) {
      clockSkew = value;
    }
  });
  package$timer.timer_k9susy$ = timer;
  package$timer.timeout_k9susy$ = timeout;
  package$timer.interval_k9susy$ = interval;
  package$timer.Timer = Timer;
  package$timer.now = now;
  package$timer.timerFlush = timerFlush;
  package$timer.setTimeout_2k6vee$ = setTimeout;
  package$timer.clearTimeout_kcmwxo$ = clearTimeout;
  package$timer.setInterval_2k6vee$ = setInterval;
  package$timer.clearInterval_kcmwxo$ = clearInterval;
  package$timer.callInNextFrame_ls4sck$ = callInNextFrame;
  package$timer.delegateNow_8be2vx$ = delegateNow;
  Object.defineProperty(package$timer, 'performanceAvailable', {
    get: function () {
      return performanceAvailable;
    }
  });
  timeoutHandle = null;
  pokeHandle = null;
  frame = 0;
  pokeDelay = 1000;
  taskHead = null;
  taskTail = null;
  clockLast = 0.0;
  clockNow = 0.0;
  clockSkew = 0.0;
  var tmp$;
  performanceAvailable = typeof (tmp$ = typeof performance === 'object' && performance.now ? true : false) === 'boolean' ? tmp$ : throwCCE();
  Kotlin.defineModule('d2v-timer-js', _);
  return _;
}));

//# sourceMappingURL=d2v-timer-js.js.map
