(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin', 'd2v-ease-js', 'd2v-viz-js', 'd2v-timer-js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'), require('d2v-ease-js'), require('d2v-viz-js'), require('d2v-timer-js'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'd2v-transition-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'd2v-transition-js'.");
    }
    if (typeof this['d2v-ease-js'] === 'undefined') {
      throw new Error("Error loading module 'd2v-transition-js'. Its dependency 'd2v-ease-js' was not found. Please, check whether 'd2v-ease-js' is loaded prior to 'd2v-transition-js'.");
    }
    if (typeof this['d2v-viz-js'] === 'undefined') {
      throw new Error("Error loading module 'd2v-transition-js'. Its dependency 'd2v-viz-js' was not found. Please, check whether 'd2v-viz-js' is loaded prior to 'd2v-transition-js'.");
    }
    if (typeof this['d2v-timer-js'] === 'undefined') {
      throw new Error("Error loading module 'd2v-transition-js'. Its dependency 'd2v-timer-js' was not found. Please, check whether 'd2v-timer-js' is loaded prior to 'd2v-transition-js'.");
    }
    root['d2v-transition-js'] = factory(typeof this['d2v-transition-js'] === 'undefined' ? {} : this['d2v-transition-js'], kotlin, this['d2v-ease-js'], this['d2v-viz-js'], this['d2v-timer-js']);
  }
}(this, function (_, Kotlin, $module$d2v_ease_js, $module$d2v_viz_js, $module$d2v_timer_js) {
  'use strict';
  var $$importsForInline$$ = _.$$importsForInline$$ || (_.$$importsForInline$$ = {});
  var ease = $module$d2v_ease_js.io.data2viz.ease.ease;
  var defineInlineFunction = Kotlin.defineInlineFunction;
  var wrapFunction = Kotlin.wrapFunction;
  var StateManager = $module$d2v_viz_js.io.data2viz.viz.StateManager;
  var StateManagerStatus = $module$d2v_viz_js.io.data2viz.viz.StateManagerStatus;
  var Unit = Kotlin.kotlin.Unit;
  var timer = $module$d2v_timer_js.io.data2viz.timer.timer_k9susy$;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var transitionTo = defineInlineFunction('d2v-transition-js.io.data2viz.transition.transitionTo_ci8gwb$', wrapFunction(function () {
    var ease = _.$$importsForInline$$['d2v-ease-js'].io.data2viz.ease.ease;
    var Transition_init = _.io.data2viz.transition.Transition;
    return function (T_0, isT, $receiver, delay, duration, easing, configure) {
      if (delay === void 0)
        delay = 0.0;
      if (duration === void 0)
        duration = 1000.0;
      if (easing === void 0)
        easing = ease.Companion.cubicInOut;
      return new Transition_init($receiver, configure, true, delay, duration, easing);
    };
  }));
  function Transition(target, configure, rootTransition, delay, duration, easing) {
    if (delay === void 0)
      delay = 0.0;
    if (easing === void 0)
      easing = ease.Companion.cubicInOut;
    this.target_0 = target;
    this.configure_0 = configure;
    this.delay = delay;
    this.duration = duration;
    this.easing = easing;
    this.next_0 = null;
    if (rootTransition)
      this.scheduleTransition_0();
  }
  function Transition$scheduleTransition$lambda(this$Transition, closure$stateManager) {
    return function ($receiver, time) {
      var tmp$;
      var percent = time / this$Transition.duration;
      if (percent > 0.999999) {
        $receiver.stop();
        percent = 1.0;
      }
      closure$stateManager.percentToState_14dthe$(this$Transition.easing(percent));
      if (percent === 1.0) {
        (tmp$ = this$Transition.next_0) != null ? (tmp$.scheduleTransition_0(), Unit) : null;
      }
      return Unit;
    };
  }
  Transition.prototype.scheduleTransition_0 = function () {
    var stateManager = new StateManager();
    this.target_0.stateManager = stateManager;
    stateManager.status = StateManagerStatus.RECORD;
    this.configure_0(this.target_0);
    stateManager.status = StateManagerStatus.REST;
    timer(this.delay, void 0, Transition$scheduleTransition$lambda(this, stateManager));
  };
  Transition.prototype.thenTransitionTo_40nbuy$ = function (delay, duration, easing, configure) {
    if (delay === void 0)
      delay = 0.0;
    if (duration === void 0)
      duration = 1000.0;
    if (easing === void 0)
      easing = ease.Companion.cubicInOut;
    var ret = new Transition(this.target_0, configure, false, delay, duration, easing);
    this.next_0 = ret;
    return ret;
  };
  Transition.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Transition',
    interfaces: []
  };
  $$importsForInline$$['d2v-ease-js'] = $module$d2v_ease_js;
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$transition = package$data2viz.transition || (package$data2viz.transition = {});
  package$transition.Transition = Transition;
  Kotlin.defineModule('d2v-transition-js', _);
  return _;
}));

//# sourceMappingURL=d2v-transition-js.js.map
