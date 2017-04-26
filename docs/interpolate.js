define('interpolate', ['exports', 'kotlin', 'core', 'tests'], function (_, Kotlin, $module$core, $module$tests) {
  'use strict';
  var math_0 = $module$core.io.data2viz.math;
  var namespace = $module$core.io.data2viz.core.namespace;
  var IntRange = Kotlin.kotlin.ranges.IntRange;
  var joinToString = Kotlin.kotlin.collections.joinToString_fmv235$;
  var StringSpec = $module$tests.io.data2viz.test.StringSpec;
  var Point = $module$core.io.data2viz.core.Point;
  EaseTests.prototype = Object.create(StringSpec.prototype);
  EaseTests.prototype.constructor = EaseTests;
  NumberTests.prototype = Object.create(StringSpec.prototype);
  NumberTests.prototype.constructor = NumberTests;
  function quad(x) {
    return x * x;
  }
  function cubicIn(x) {
    return x * x * x;
  }
  function cubicOut(t) {
    return (t - 1) * (t - 1) * (t - 1) + 1;
  }
  function cubicInOut(t) {
    return (t <= 0.5 ? 8 * t * t * t : (2 * t - 2) * (2 * t - 2) * (2 * t - 2) + 2) / 2;
  }
  function sin(x) {
    return 1 - Math.cos(x * math_0.halfPI);
  }
  function poly$lambda(closure$e) {
    return function (t) {
      return Math.pow(t, closure$e);
    };
  }
  function poly(e) {
    return poly$lambda(e);
  }
  function circleIn(t) {
    return 1 - Math.sqrt(1 - t * t);
  }
  function circleOut(t) {
    return Math.sqrt(1 - (t - 1) * (t - 1));
  }
  function EaseTests() {
    StringSpec.call(this);
    this.invoke_79xod4$('io.data2viz.interpolate.identity', EaseTests_init$lambda(this));
    this.invoke_79xod4$('quad', EaseTests_init$lambda_0(this));
    this.invoke_79xod4$('cubicIn', EaseTests_init$lambda_1(this));
    this.invoke_79xod4$('cubicOut', EaseTests_init$lambda_2(this));
    this.invoke_79xod4$('cubicInOut', EaseTests_init$lambda_3(this));
    this.invoke_79xod4$('sin', EaseTests_init$lambda_4(this));
    this.invoke_79xod4$('circleIn', EaseTests_init$lambda_5(this));
    this.invoke_79xod4$('circleOut', EaseTests_init$lambda_6(this));
  }
  function EaseTests$testAndGraph$node(name) {
    return document.createElementNS(namespace.Companion.svg, name);
  }
  EaseTests.prototype.testAndGraph_7fnk9s$ = function (function_0) {
    if (function_0 === void 0)
      function_0 = Kotlin.getCallableRef('identity', function (a) {
        return identity(a);
      });
    var tmp$;
    this.shouldBe_3ta935$(function_0(0.0), this.plusOrMinus_38ydlf$(0.0, 0.01));
    this.shouldBe_3ta935$(function_0(1.0), this.plusOrMinus_38ydlf$(1.0, 0.01));
    var node = EaseTests$testAndGraph$node;
    var body = (tmp$ = document.querySelector('body')) != null ? tmp$ : Kotlin.throwNPE();
    var $receiver = node('svg');
    var closure$function = function_0;
    $receiver.setAttribute('width', '100');
    $receiver.setAttribute('height', '100');
    var $receiver_0 = node('path');
    $receiver_0.setAttribute('stroke', 'black');
    $receiver_0.setAttribute('fill', 'transparent');
    var $receiver_1 = new IntRange(0, 100);
    var destination = Kotlin.kotlin.collections.ArrayList_init_ww73n8$(Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$($receiver_1, 10));
    var tmp$_0;
    tmp$_0 = $receiver_1.iterator();
    while (tmp$_0.hasNext()) {
      var item = tmp$_0.next();
      var tmp$_1 = destination.add_11rb$;
      var x = item;
      var y = 100 - closure$function(item / 100) * 100;
      tmp$_1.call(destination, 'L ' + x + ' ' + y);
    }
    var path = joinToString(destination, ' ');
    $receiver_0.setAttribute('d', 'M 0 100 ' + path);
    $receiver.appendChild($receiver_0);
    body.appendChild($receiver);
  };
  function EaseTests_init$lambda(this$EaseTests) {
    return function () {
      this$EaseTests.testAndGraph_7fnk9s$();
    };
  }
  function EaseTests_init$lambda_0(this$EaseTests) {
    return function () {
      this$EaseTests.testAndGraph_7fnk9s$(Kotlin.getCallableRef('quad', function (x) {
        return quad(x);
      }));
    };
  }
  function EaseTests_init$lambda_1(this$EaseTests) {
    return function () {
      this$EaseTests.testAndGraph_7fnk9s$(Kotlin.getCallableRef('cubicIn', function (x) {
        return cubicIn(x);
      }));
    };
  }
  function EaseTests_init$lambda_2(this$EaseTests) {
    return function () {
      this$EaseTests.testAndGraph_7fnk9s$(Kotlin.getCallableRef('cubicOut', function (t) {
        return cubicOut(t);
      }));
    };
  }
  function EaseTests_init$lambda_3(this$EaseTests) {
    return function () {
      this$EaseTests.testAndGraph_7fnk9s$(Kotlin.getCallableRef('cubicInOut', function (t) {
        return cubicInOut(t);
      }));
    };
  }
  function EaseTests_init$lambda_4(this$EaseTests) {
    return function () {
      this$EaseTests.testAndGraph_7fnk9s$(Kotlin.getCallableRef('sin', function (x) {
        return sin(x);
      }));
    };
  }
  function EaseTests_init$lambda_5(this$EaseTests) {
    return function () {
      this$EaseTests.testAndGraph_7fnk9s$(Kotlin.getCallableRef('circleIn', function (t) {
        return circleIn(t);
      }));
    };
  }
  function EaseTests_init$lambda_6(this$EaseTests) {
    return function () {
      this$EaseTests.testAndGraph_7fnk9s$(Kotlin.getCallableRef('circleOut', function (t) {
        return circleOut(t);
      }));
    };
  }
  EaseTests.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'EaseTests',
    interfaces: [StringSpec]
  };
  function interpolateNumber$lambda(closure$a, closure$b) {
    return function (t) {
      return Kotlin.numberToDouble(closure$a) + Kotlin.numberToDouble(t) * (Kotlin.numberToDouble(closure$b) - Kotlin.numberToDouble(closure$a));
    };
  }
  function interpolateNumber(a, b) {
    return interpolateNumber$lambda(a, b);
  }
  function uninterpolate$lambda(closure$start, closure$end) {
    return function (x) {
      return (Kotlin.numberToDouble(x) - Kotlin.numberToDouble(closure$start)) / (Kotlin.numberToDouble(closure$end) - Kotlin.numberToDouble(closure$start));
    };
  }
  function uninterpolate(start, end) {
    return uninterpolate$lambda(start, end);
  }
  function identity(a) {
    return a;
  }
  function NumberTests() {
    StringSpec.call(this);
    this.invoke_79xod4$('interpolate', NumberTests_init$lambda(this));
    this.invoke_79xod4$('uninterpolate', NumberTests_init$lambda_0(this));
    this.invoke_79xod4$('scale linear', NumberTests_init$lambda_1(this));
  }
  function NumberTests_init$lambda(this$NumberTests) {
    return function () {
      var f = interpolateNumber(10, 20);
      this$NumberTests.shouldBe_3ta935$(f(0.2), 12.0);
    };
  }
  function NumberTests_init$lambda_0(this$NumberTests) {
    return function () {
      var f = uninterpolate(10, 20);
      this$NumberTests.shouldBe_3ta935$(f(12), 0.2);
    };
  }
  function NumberTests_init$lambda_1(this$NumberTests) {
    return function () {
      var domainToViz = scale$linear_getInstance().numberToNumber_qw4oic$(linkedTo(10, 100), linkedTo(20, 200));
      this$NumberTests.shouldBe_3ta935$(domainToViz(12), 120);
    };
  }
  NumberTests.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'NumberTests',
    interfaces: [StringSpec]
  };
  function DomainToViz(domain, viz) {
    this.domain = domain;
    this.viz = viz;
  }
  DomainToViz.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'DomainToViz',
    interfaces: []
  };
  DomainToViz.prototype.component1 = function () {
    return this.domain;
  };
  DomainToViz.prototype.component2 = function () {
    return this.viz;
  };
  DomainToViz.prototype.copy_xwzc9p$ = function (domain, viz) {
    return new DomainToViz(domain === void 0 ? this.domain : domain, viz === void 0 ? this.viz : viz);
  };
  DomainToViz.prototype.toString = function () {
    return 'DomainToViz(domain=' + Kotlin.toString(this.domain) + (', viz=' + Kotlin.toString(this.viz)) + ')';
  };
  DomainToViz.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.domain) | 0;
    result = result * 31 + Kotlin.hashCode(this.viz) | 0;
    return result;
  };
  DomainToViz.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.domain, other.domain) && Kotlin.equals(this.viz, other.viz)))));
  };
  function linkedTo($receiver, that) {
    return new DomainToViz($receiver, that);
  }
  function scale() {
  }
  function scale$linear() {
    scale$linear_instance = this;
  }
  function scale$linear$pointsToPoints$lambda(closure$start, closure$end, this$linear) {
    return function (pt) {
      return new Point(this$linear.numberToNumber_qw4oic$(linkedTo(closure$start.domain.x, closure$start.viz.x), linkedTo(closure$end.domain.x, closure$end.viz.x))(pt.x), this$linear.numberToNumber_qw4oic$(linkedTo(closure$start.domain.y, closure$start.viz.y), linkedTo(closure$end.domain.y, closure$end.viz.y))(pt.y));
    };
  }
  scale$linear.prototype.pointsToPoints_eqjn84$ = function (start, end) {
    return scale$linear$pointsToPoints$lambda(start, end, this);
  };
  function scale$linear$numberToNumber$lambda(closure$start, closure$end) {
    return function (domain) {
      return interpolateNumber(closure$start.viz, closure$end.viz)(Kotlin.numberToDouble(uninterpolate(closure$start.domain, closure$end.domain)(domain)));
    };
  }
  scale$linear.prototype.numberToNumber_qw4oic$ = function (start, end) {
    return scale$linear$numberToNumber$lambda(start, end);
  };
  scale$linear.$metadata$ = {
    kind: Kotlin.Kind.OBJECT,
    simpleName: 'linear',
    interfaces: []
  };
  var scale$linear_instance = null;
  function scale$linear_getInstance() {
    if (scale$linear_instance === null) {
      new scale$linear();
    }
    return scale$linear_instance;
  }
  scale.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'scale',
    interfaces: []
  };
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$interpolate = package$data2viz.interpolate || (package$data2viz.interpolate = {});
  package$interpolate.quad_14dthe$ = quad;
  package$interpolate.cubicIn_14dthe$ = cubicIn;
  package$interpolate.cubicOut_14dthe$ = cubicOut;
  package$interpolate.cubicInOut_14dthe$ = cubicInOut;
  package$interpolate.sin_14dthe$ = sin;
  package$interpolate.poly_14dthe$ = poly;
  package$interpolate.circleIn_14dthe$ = circleIn;
  package$interpolate.circleOut_14dthe$ = circleOut;
  package$interpolate.EaseTests = EaseTests;
  package$interpolate.interpolateNumber_z8e4lc$ = interpolateNumber;
  package$interpolate.uninterpolate_z8e4lc$ = uninterpolate;
  package$interpolate.identity_14dthe$ = identity;
  package$interpolate.NumberTests = NumberTests;
  package$interpolate.DomainToViz = DomainToViz;
  package$interpolate.linkedTo_ujzrz7$ = linkedTo;
  Object.defineProperty(scale, 'linear', {
    get: scale$linear_getInstance
  });
  package$interpolate.scale = scale;
  Kotlin.defineModule('interpolate', _);
  return _;
});

//@ sourceMappingURL=interpolate.js.map
