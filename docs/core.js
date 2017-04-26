define('core', ['exports', 'kotlin', 'tests'], function (_, Kotlin, $module$tests) {
  'use strict';
  var lazy = Kotlin.kotlin.lazy_klfg04$;
  var IntRange = Kotlin.kotlin.ranges.IntRange;
  var StringSpec = $module$tests.io.data2viz.test.StringSpec;
  var listOf = Kotlin.kotlin.collections.listOf_i5x0yv$;
  var listOf_0 = Kotlin.kotlin.collections.listOf_mh5how$;
  TicksTests.prototype = Object.create(StringSpec.prototype);
  TicksTests.prototype.constructor = TicksTests;
  function Point(x, y) {
    Point$Companion_getInstance();
    if (x === void 0)
      x = 0.0;
    if (y === void 0)
      y = 0.0;
    this.x = x;
    this.y = y;
  }
  function Point$Companion() {
    Point$Companion_instance = this;
    this.origin = new Point();
  }
  Point$Companion.$metadata$ = {
    kind: Kotlin.Kind.OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var Point$Companion_instance = null;
  function Point$Companion_getInstance() {
    if (Point$Companion_instance === null) {
      new Point$Companion();
    }
    return Point$Companion_instance;
  }
  Point.prototype.plus_fmwg8v$ = function (speed) {
    return new Point(Kotlin.numberToDouble(this.x) + speed.vx, Kotlin.numberToDouble(this.y) + speed.vy);
  };
  Point.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'Point',
    interfaces: []
  };
  Point.prototype.component1 = function () {
    return this.x;
  };
  Point.prototype.component2 = function () {
    return this.y;
  };
  Point.prototype.copy_z8e4lc$ = function (x, y) {
    return new Point(x === void 0 ? this.x : x, y === void 0 ? this.y : y);
  };
  Point.prototype.toString = function () {
    return 'Point(x=' + Kotlin.toString(this.x) + (', y=' + Kotlin.toString(this.y)) + ')';
  };
  Point.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.x) | 0;
    result = result * 31 + Kotlin.hashCode(this.y) | 0;
    return result;
  };
  Point.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.x, other.x) && Kotlin.equals(this.y, other.y)))));
  };
  function Speed(vx, vy) {
    if (vx === void 0)
      vx = 0.0;
    if (vy === void 0)
      vy = 0.0;
    this.vx = vx;
    this.vy = vy;
  }
  Speed.prototype.plus_fmwg8v$ = function (speed) {
    return new Speed(this.vx + speed.vx, this.vy + speed.vy);
  };
  Speed.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'Speed',
    interfaces: []
  };
  Speed.prototype.component1 = function () {
    return this.vx;
  };
  Speed.prototype.component2 = function () {
    return this.vy;
  };
  Speed.prototype.copy_lu1900$ = function (vx, vy) {
    return new Speed(vx === void 0 ? this.vx : vx, vy === void 0 ? this.vy : vy);
  };
  Speed.prototype.toString = function () {
    return 'Speed(vx=' + Kotlin.toString(this.vx) + (', vy=' + Kotlin.toString(this.vy)) + ')';
  };
  Speed.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.vx) | 0;
    result = result * 31 + Kotlin.hashCode(this.vy) | 0;
    return result;
  };
  Speed.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.vx, other.vx) && Kotlin.equals(this.vy, other.vy)))));
  };
  function namespace() {
    namespace$Companion_getInstance();
  }
  function namespace$Companion() {
    namespace$Companion_instance = this;
    this.svg = 'http://www.w3.org/2000/svg';
    this.xlink = 'http://www.w3.org/1999/xlink';
    this.xml = 'http://www.w3.org/XML/1998/io.data2viz.core.namespace';
    this.xmlns = 'http://www.w3.org/2000/xmlns/';
  }
  namespace$Companion.$metadata$ = {
    kind: Kotlin.Kind.OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var namespace$Companion_instance = null;
  function namespace$Companion_getInstance() {
    if (namespace$Companion_instance === null) {
      new namespace$Companion();
    }
    return namespace$Companion_instance;
  }
  namespace.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'namespace',
    interfaces: []
  };
  var e10;
  var e5;
  var e2;
  function ln10$lambda() {
    return Math.log(10.0);
  }
  var ln10;
  function get_ln10() {
    new Kotlin.PropertyMetadata('ln10');
    return ln10.value;
  }
  function ticks(start, stop, count) {
    var step = tickStep(Kotlin.numberToDouble(start), Kotlin.numberToDouble(stop), count);
    return range(Math.ceil(Kotlin.numberToDouble(start) / step) * step, Math.floor(Kotlin.numberToDouble(stop) / step) * step + step / 2, step);
  }
  function tickStep(start, stop, count) {
    var step0 = Math.abs(stop - start) / count;
    var step1 = Math.pow(10.0, Math.floor(Math.log(step0) / get_ln10()));
    var error = step0 / step1;
    if (error >= e10)
      step1 *= 10;
    else if (error >= e5)
      step1 *= 5;
    else if (error >= e2)
      step1 *= 2;
    return stop < start ? -step1 : step1;
  }
  function range(start, stop, step) {
    if (step === void 0)
      step = 1.0;
    var n = Math.max(0, Math.ceil((stop - start) / step));
    var $receiver = new IntRange(0, n - 1 | 0);
    var destination = Kotlin.kotlin.collections.ArrayList_init_ww73n8$(Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$($receiver, 10));
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(start + item * step);
    }
    return destination;
  }
  function TicksTests() {
    StringSpec.call(this);
    this.invoke_79xod4$('io.data2viz.core.ticks(0.0, 1.0, 10) io.data2viz.core.ticks(0.0, 1.0, 9) io.data2viz.core.ticks(0.0, 1.0, 8) shouldBe listOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)', TicksTests_init$lambda(this));
    this.invoke_79xod4$('io.data2viz.core.ticks(0.0, 1.0, 7) io.data2viz.core.ticks(0.0, 1.0, 6) io.data2viz.core.ticks(0.0, 1.0, 5) shouldBe listOf(0.0, 0.2, 0.4, 0.6, 0.8, 1.0)', TicksTests_init$lambda_0(this));
    this.invoke_79xod4$('io.data2viz.core.ticks(0.0, 1.0, 3) io.data2viz.core.ticks(0.0, 1.0, 2) shouldBe listOf(0.0, 0.5, 1.0)', TicksTests_init$lambda_1(this));
    this.invoke_79xod4$('io.data2viz.core.ticks(0.0, 1.0, 1) io.data2viz.core.ticks(0.0, 1.0, 2) shouldBe listOf(0.0, 1.0)', TicksTests_init$lambda_2(this));
    this.invoke_79xod4$('io.data2viz.core.ticks(0.0, 10.0, 10) io.data2viz.core.ticks(0.0, 10.0, 9) io.data2viz.core.ticks(0.0, 10.0, 8) shouldBe listOf(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0)', TicksTests_init$lambda_3(this));
    this.invoke_79xod4$('io.data2viz.core.ticks(0.0, 10.0, 7) io.data2viz.core.ticks(0.0, 10.0, 6) io.data2viz.core.ticks(0.0, 10.0, 5) io.data2viz.core.ticks(0.0, 10.0, 4) shouldBe listOf(0.0, 2.0, 4.0, 6.0, 8.0, 10.0)', TicksTests_init$lambda_4(this));
    this.invoke_79xod4$('io.data2viz.core.ticks(0.0, 10.0, 3) io.data2viz.core.ticks(0.0, 10.0, 2) io.data2viz.core.ticks(0.0, 10.0, 5) io.data2viz.core.ticks(0.0, 10.0, 4) shouldBe listOf(0.0, 5.0, 10.0)', TicksTests_init$lambda_5(this));
    this.invoke_79xod4$('io.data2viz.core.ticks(0.0, 10.0, 1) shouldBe listOf(0.0, 10.0)', TicksTests_init$lambda_6(this));
    this.invoke_79xod4$('io.data2viz.core.ticks(-10.0, 10.0, 10) io.data2viz.core.ticks(-10.0, 10.0, 9) io.data2viz.core.ticks(-10.0, 10.0, 8) io.data2viz.core.ticks(-10.0, 10.0, 7) shouldBe listOf(-10, -8.0, -6.0, -4.0, -2.0, 0.0, 2.0, 4.0, 6.0, 8.0, 10.0)', TicksTests_init$lambda_7(this));
    this.invoke_79xod4$('io.data2viz.core.ticks(-10.0, 10.0, 6) io.data2viz.core.ticks(-10.0, 10.0, 5) io.data2viz.core.ticks(-10.0, 10.0, 4) io.data2viz.core.ticks(-10.0, 10.0, 3) shouldBe listOf(-10, -5.0, 0.0, 5.0, 10.0)', TicksTests_init$lambda_8(this));
    this.invoke_79xod4$('io.data2viz.core.ticks(-10.0, 10.0, 2) shouldBe listOf(-10, 0.0, 10.0)', TicksTests_init$lambda_9(this));
    this.invoke_79xod4$('io.data2viz.core.ticks(-10.0, 10.0, 1) shouldBe listOf(-10, 0.0, 10.0)', TicksTests_init$lambda_10(this));
  }
  function TicksTests_init$lambda(this$TicksTests) {
    return function () {
      this$TicksTests.shouldBe_hvlbab$(ticks(0.0, 1.0, 10), listOf([0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0]));
      this$TicksTests.shouldBe_hvlbab$(ticks(0.0, 1.0, 9), listOf([0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0]));
      this$TicksTests.shouldBe_hvlbab$(ticks(0.0, 1.0, 8), listOf([0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0]));
    };
  }
  function TicksTests_init$lambda_0(this$TicksTests) {
    return function () {
      this$TicksTests.shouldBe_hvlbab$(ticks(0.0, 1.0, 7), listOf([0.0, 0.2, 0.4, 0.6, 0.8, 1.0]));
      this$TicksTests.shouldBe_hvlbab$(ticks(0.0, 1.0, 6), listOf([0.0, 0.2, 0.4, 0.6, 0.8, 1.0]));
      this$TicksTests.shouldBe_hvlbab$(ticks(0.0, 1.0, 5), listOf([0.0, 0.2, 0.4, 0.6, 0.8, 1.0]));
      this$TicksTests.shouldBe_hvlbab$(ticks(0.0, 1.0, 4), listOf([0.0, 0.2, 0.4, 0.6, 0.8, 1.0]));
    };
  }
  function TicksTests_init$lambda_1(this$TicksTests) {
    return function () {
      this$TicksTests.shouldBe_hvlbab$(ticks(0.0, 1.0, 3), listOf([0.0, 0.5, 1.0]));
      this$TicksTests.shouldBe_hvlbab$(ticks(0.0, 1.0, 2), listOf([0.0, 0.5, 1.0]));
    };
  }
  function TicksTests_init$lambda_2(this$TicksTests) {
    return function () {
      this$TicksTests.shouldBe_hvlbab$(ticks(0.0, 1.0, 1), listOf([0.0, 1.0]));
    };
  }
  function TicksTests_init$lambda_3(this$TicksTests) {
    return function () {
      this$TicksTests.shouldBe_hvlbab$(ticks(0.0, 10.0, 10), listOf([0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0]));
      this$TicksTests.shouldBe_hvlbab$(ticks(0.0, 10.0, 9), listOf([0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0]));
      this$TicksTests.shouldBe_hvlbab$(ticks(0.0, 10.0, 8), listOf([0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0]));
    };
  }
  function TicksTests_init$lambda_4(this$TicksTests) {
    return function () {
      this$TicksTests.shouldBe_hvlbab$(ticks(0.0, 10.0, 7), listOf([0.0, 2.0, 4.0, 6.0, 8.0, 10.0]));
      this$TicksTests.shouldBe_hvlbab$(ticks(0.0, 10.0, 6), listOf([0.0, 2.0, 4.0, 6.0, 8.0, 10.0]));
      this$TicksTests.shouldBe_hvlbab$(ticks(0.0, 10.0, 5), listOf([0.0, 2.0, 4.0, 6.0, 8.0, 10.0]));
      this$TicksTests.shouldBe_hvlbab$(ticks(0.0, 10.0, 4), listOf([0.0, 2.0, 4.0, 6.0, 8.0, 10.0]));
    };
  }
  function TicksTests_init$lambda_5(this$TicksTests) {
    return function () {
      this$TicksTests.shouldBe_hvlbab$(ticks(0.0, 10.0, 3), listOf([0.0, 5.0, 10.0]));
      this$TicksTests.shouldBe_hvlbab$(ticks(0.0, 10.0, 2), listOf([0.0, 5.0, 10.0]));
    };
  }
  function TicksTests_init$lambda_6(this$TicksTests) {
    return function () {
      this$TicksTests.shouldBe_hvlbab$(ticks(0.0, 10.0, 1), listOf([0.0, 10.0]));
    };
  }
  function TicksTests_init$lambda_7(this$TicksTests) {
    return function () {
      this$TicksTests.shouldBe_3ta935$(ticks(-10.0, 10.0, 10), listOf([-10, -8.0, -6.0, -4.0, -2.0, 0.0, 2.0, 4.0, 6.0, 8.0, 10.0]));
      this$TicksTests.shouldBe_3ta935$(ticks(-10.0, 10.0, 9), listOf([-10, -8.0, -6.0, -4.0, -2.0, 0.0, 2.0, 4.0, 6.0, 8.0, 10.0]));
      this$TicksTests.shouldBe_3ta935$(ticks(-10.0, 10.0, 8), listOf([-10, -8.0, -6.0, -4.0, -2.0, 0.0, 2.0, 4.0, 6.0, 8.0, 10.0]));
      this$TicksTests.shouldBe_3ta935$(ticks(-10.0, 10.0, 7), listOf([-10, -8.0, -6.0, -4.0, -2.0, 0.0, 2.0, 4.0, 6.0, 8.0, 10.0]));
    };
  }
  function TicksTests_init$lambda_8(this$TicksTests) {
    return function () {
      this$TicksTests.shouldBe_3ta935$(ticks(-10.0, 10.0, 6), listOf([-10, -5.0, 0.0, 5.0, 10.0]));
      this$TicksTests.shouldBe_3ta935$(ticks(-10.0, 10.0, 5), listOf([-10, -5.0, 0.0, 5.0, 10.0]));
      this$TicksTests.shouldBe_3ta935$(ticks(-10.0, 10.0, 4), listOf([-10, -5.0, 0.0, 5.0, 10.0]));
      this$TicksTests.shouldBe_3ta935$(ticks(-10.0, 10.0, 3), listOf([-10, -5.0, 0.0, 5.0, 10.0]));
    };
  }
  function TicksTests_init$lambda_9(this$TicksTests) {
    return function () {
      this$TicksTests.shouldBe_3ta935$(ticks(-10.0, 10.0, 2), listOf([-10, 0.0, 10.0]));
    };
  }
  function TicksTests_init$lambda_10(this$TicksTests) {
    return function () {
      this$TicksTests.shouldBe_hvlbab$(ticks(-10.0, 10.0, 1), listOf_0(0.0));
    };
  }
  TicksTests.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'TicksTests',
    interfaces: [StringSpec]
  };
  var EPS;
  var EPS2;
  var PI;
  var halfPI;
  var THETA;
  var THETA_EPS;
  var DEG_TO_RAD;
  var RAD_TO_DEG;
  function Angle(deg) {
    this.deg = deg;
  }
  Object.defineProperty(Angle.prototype, 'cos', {
    get: function () {
      return Math.cos(this.deg);
    }
  });
  Object.defineProperty(Angle.prototype, 'sin', {
    get: function () {
      return Math.cos(this.deg);
    }
  });
  Object.defineProperty(Angle.prototype, 'tan', {
    get: function () {
      return Math.tan(this.deg);
    }
  });
  Angle.prototype.toRad = function () {
    return this.deg * DEG_TO_RAD;
  };
  Angle.prototype.plus_5t6zck$ = function (angle) {
    return new Angle(this.deg + angle.deg);
  };
  Angle.prototype.minus_5t6zck$ = function (angle) {
    return new Angle(this.deg - angle.deg);
  };
  Angle.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'Angle',
    interfaces: []
  };
  Object.defineProperty(Point, 'Companion', {
    get: Point$Companion_getInstance
  });
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$core = package$data2viz.core || (package$data2viz.core = {});
  package$core.Point = Point;
  package$core.Speed = Speed;
  Object.defineProperty(namespace, 'Companion', {
    get: namespace$Companion_getInstance
  });
  package$core.namespace = namespace;
  Object.defineProperty(package$core, 'e10', {
    get: function () {
      return e10;
    }
  });
  Object.defineProperty(package$core, 'e5', {
    get: function () {
      return e5;
    }
  });
  Object.defineProperty(package$core, 'e2', {
    get: function () {
      return e2;
    }
  });
  Object.defineProperty(package$core, 'ln10', {
    get: get_ln10
  });
  package$core.ticks_1stjjm$ = ticks;
  package$core.tickStep_syxxoe$ = tickStep;
  package$core.range_yvo9jy$ = range;
  package$core.TicksTests = TicksTests;
  var package$math = package$data2viz.math || (package$data2viz.math = {});
  Object.defineProperty(package$math, 'EPS', {
    get: function () {
      return EPS;
    }
  });
  Object.defineProperty(package$math, 'EPS2', {
    get: function () {
      return EPS2;
    }
  });
  Object.defineProperty(package$math, 'PI', {
    get: function () {
      return PI;
    }
  });
  Object.defineProperty(package$math, 'halfPI', {
    get: function () {
      return halfPI;
    }
  });
  Object.defineProperty(package$math, 'THETA', {
    get: function () {
      return THETA;
    }
  });
  Object.defineProperty(package$math, 'THETA_EPS', {
    get: function () {
      return THETA_EPS;
    }
  });
  Object.defineProperty(package$math, 'DEG_TO_RAD', {
    get: function () {
      return DEG_TO_RAD;
    }
  });
  Object.defineProperty(package$math, 'RAD_TO_DEG', {
    get: function () {
      return RAD_TO_DEG;
    }
  });
  package$math.Angle = Angle;
  e10 = Math.sqrt(50.0);
  e5 = Math.sqrt(10.0);
  e2 = Math.sqrt(2.0);
  ln10 = lazy(ln10$lambda);
  EPS = 1.0E-6;
  EPS2 = EPS * EPS;
  PI = Math.PI;
  halfPI = PI / 2;
  THETA = PI * 2;
  THETA_EPS = THETA - EPS;
  DEG_TO_RAD = PI / 180;
  RAD_TO_DEG = 180 / PI;
  Kotlin.defineModule('core', _);
  return _;
});

//@ sourceMappingURL=core.js.map
