(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'd2v-random-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'd2v-random-js'.");
    }
    root['d2v-random-js'] = factory(typeof this['d2v-random-js'] === 'undefined' ? {} : this['d2v-random-js'], kotlin);
  }
}(this, function (_, Kotlin) {
  'use strict';
  var Kind_OBJECT = Kotlin.Kind.OBJECT;
  var numberToInt = Kotlin.numberToInt;
  var until = Kotlin.kotlin.ranges.until_dqglrj$;
  var Random = Kotlin.kotlin.random.Random;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var Math_0 = Math;
  function RandomDistribution(random) {
    RandomDistribution$Default_getInstance();
    if (random === void 0)
      random = Random.Default;
    this.random_0 = random;
  }
  function RandomDistribution$Default() {
    RandomDistribution$Default_instance = this;
    this.randomDistribution_0 = new RandomDistribution();
  }
  RandomDistribution$Default.prototype.uniform_lu1900$ = function (min, max) {
    if (min === void 0)
      min = 0.0;
    if (max === void 0)
      max = 1.0;
    return this.randomDistribution_0.uniform_lu1900$(min, max);
  };
  RandomDistribution$Default.prototype.bates_14dthe$ = function (n) {
    if (n === void 0)
      n = 1.0;
    return this.randomDistribution_0.bates_14dthe$(n);
  };
  RandomDistribution$Default.prototype.logNormal_lu1900$ = function (mu, sigma) {
    if (mu === void 0)
      mu = 0.0;
    if (sigma === void 0)
      sigma = 1.0;
    return this.randomDistribution_0.logNormal_lu1900$(mu, sigma);
  };
  RandomDistribution$Default.prototype.exponential_14dthe$ = function (lambda) {
    if (lambda === void 0)
      lambda = 1.0;
    return this.randomDistribution_0.exponential_14dthe$(lambda);
  };
  RandomDistribution$Default.prototype.irwinHall_14dthe$ = function (n) {
    return this.randomDistribution_0.irwinHall_14dthe$(n);
  };
  RandomDistribution$Default.prototype.normal_lu1900$ = function (mu, sigma) {
    if (mu === void 0)
      mu = 0.0;
    if (sigma === void 0)
      sigma = 1.0;
    return this.randomDistribution_0.normal_lu1900$(mu, sigma);
  };
  RandomDistribution$Default.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Default',
    interfaces: []
  };
  var RandomDistribution$Default_instance = null;
  function RandomDistribution$Default_getInstance() {
    if (RandomDistribution$Default_instance === null) {
      new RandomDistribution$Default();
    }
    return RandomDistribution$Default_instance;
  }
  function RandomDistribution$uniform$lambda(this$RandomDistribution, closure$max, closure$min) {
    return function () {
      return this$RandomDistribution.random() * (closure$max - closure$min) + closure$min;
    };
  }
  RandomDistribution.prototype.uniform_lu1900$ = function (min, max) {
    if (min === void 0)
      min = 0.0;
    if (max === void 0)
      max = 1.0;
    return RandomDistribution$uniform$lambda(this, max, min);
  };
  function RandomDistribution$bates$lambda(closure$n, this$RandomDistribution) {
    return function () {
      return this$RandomDistribution.irwinHall_14dthe$(closure$n)() / closure$n;
    };
  }
  RandomDistribution.prototype.bates_14dthe$ = function (n) {
    if (n === void 0)
      n = 1.0;
    return RandomDistribution$bates$lambda(n, this);
  };
  function RandomDistribution$logNormal$lambda(closure$mu, closure$sigma, this$RandomDistribution) {
    return function () {
      var x = this$RandomDistribution.normal_lu1900$(closure$mu, closure$sigma)();
      return Math_0.exp(x);
    };
  }
  RandomDistribution.prototype.logNormal_lu1900$ = function (mu, sigma) {
    if (mu === void 0)
      mu = 0.0;
    if (sigma === void 0)
      sigma = 1.0;
    return RandomDistribution$logNormal$lambda(mu, sigma, this);
  };
  function RandomDistribution$exponential$lambda(this$RandomDistribution, closure$lambda) {
    return function () {
      var x = 1 - this$RandomDistribution.random();
      return -Math_0.log(x) / closure$lambda;
    };
  }
  RandomDistribution.prototype.exponential_14dthe$ = function (lambda) {
    if (lambda === void 0)
      lambda = 1.0;
    return RandomDistribution$exponential$lambda(this, lambda);
  };
  function RandomDistribution$irwinHall$lambda(closure$n, this$RandomDistribution) {
    return function () {
      var sum = {v: 0.0};
      var $receiver = until(0, numberToInt(closure$n));
      var tmp$;
      tmp$ = $receiver.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        sum.v += this$RandomDistribution.random();
      }
      return sum.v;
    };
  }
  RandomDistribution.prototype.irwinHall_14dthe$ = function (n) {
    return RandomDistribution$irwinHall$lambda(n, this);
  };
  function RandomDistribution$normal$lambda(this$RandomDistribution, closure$mu, closure$sigma) {
    return function () {
      var x = null;
      var r = 0.0;
      var y = 0.0;
      if (x != null) {
        y = x;
      }
       else {
        while (r === 0.0 || r > 1) {
          x = this$RandomDistribution.random() * 2 - 1;
          y = this$RandomDistribution.random() * 2 - 1;
          r = x * x + y * y;
        }
      }
      var tmp$ = closure$mu;
      var tmp$_0 = closure$sigma * y;
      var x_0 = r;
      var x_1 = -2 * Math_0.log(x_0) / r;
      return tmp$ + tmp$_0 * Math_0.sqrt(x_1);
    };
  }
  RandomDistribution.prototype.normal_lu1900$ = function (mu, sigma) {
    if (mu === void 0)
      mu = 0.0;
    if (sigma === void 0)
      sigma = 1.0;
    return RandomDistribution$normal$lambda(this, mu, sigma);
  };
  RandomDistribution.prototype.random = function () {
    return this.random_0.nextDouble();
  };
  RandomDistribution.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'RandomDistribution',
    interfaces: []
  };
  Object.defineProperty(RandomDistribution, 'Default', {
    get: RandomDistribution$Default_getInstance
  });
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$random = package$data2viz.random || (package$data2viz.random = {});
  package$random.RandomDistribution = RandomDistribution;
  Kotlin.defineModule('d2v-random-js', _);
  return _;
}));

//# sourceMappingURL=d2v-random-js.js.map
