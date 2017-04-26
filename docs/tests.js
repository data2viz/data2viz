define('tests', ['exports', 'kotlin'], function (_, Kotlin) {
  'use strict';
  var lazy = Kotlin.kotlin.lazy_klfg04$;
  var AssertionError = Kotlin.kotlin.AssertionError;
  var NullPointerException = Kotlin.kotlin.NullPointerException;
  var listOf = Kotlin.kotlin.collections.listOf_i5x0yv$;
  var emptyList = Kotlin.kotlin.collections.emptyList_287e2$;
  var listOf_0 = Kotlin.kotlin.collections.listOf_mh5how$;
  var println = Kotlin.kotlin.io.println_s8jyv4$;
  var Exception = Kotlin.kotlin.Exception;
  var get_js = Kotlin.kotlin.js.get_js_1yb8b7$;
  var count = Kotlin.kotlin.collections.count_7wnvza$;
  var zip = Kotlin.kotlin.collections.zip_45mdf7$;
  var contains = Kotlin.kotlin.text.contains_li3zpu$;
  var startsWith = Kotlin.kotlin.text.startsWith_7epoxm$;
  var take = Kotlin.kotlin.text.take_6ic1pp$;
  var endsWith = Kotlin.kotlin.text.endsWith_7epoxm$;
  var takeLast = Kotlin.kotlin.text.takeLast_6ic1pp$;
  StringSpec.prototype = Object.create(TestBase.prototype);
  StringSpec.prototype.constructor = StringSpec;
  TestResult$OK.prototype = Object.create(TestResult.prototype);
  TestResult$OK.prototype.constructor = TestResult$OK;
  TestResult$KO.prototype = Object.create(TestResult.prototype);
  TestResult$KO.prototype.constructor = TestResult$KO;
  ExceptionMatchers.prototype = Object.create(StringSpec.prototype);
  ExceptionMatchers.prototype.constructor = ExceptionMatchers;
  DoubleMatchers.prototype = Object.create(StringSpec.prototype);
  DoubleMatchers.prototype.constructor = DoubleMatchers;
  StringMatchers.prototype = Object.create(StringSpec.prototype);
  StringMatchers.prototype.constructor = StringMatchers;
  IntMatchers.prototype = Object.create(StringSpec.prototype);
  IntMatchers.prototype.constructor = IntMatchers;
  LongMatchers.prototype = Object.create(StringSpec.prototype);
  LongMatchers.prototype.constructor = LongMatchers;
  TestCollectionMatchers.prototype = Object.create(StringSpec.prototype);
  TestCollectionMatchers.prototype.constructor = TestCollectionMatchers;
  function DomUtils() {
    DomUtils$Companion_getInstance();
  }
  function DomUtils$Companion() {
    DomUtils$Companion_instance = this;
    this.body$delegate = lazy(DomUtils$Companion$body$lambda);
  }
  Object.defineProperty(DomUtils$Companion.prototype, 'body', {
    get: function () {
      var $receiver = this.body$delegate;
      new Kotlin.PropertyMetadata('body');
      return $receiver.value;
    }
  });
  function DomUtils$Companion$body$lambda() {
    var tmp$;
    return (tmp$ = document.querySelector('body')) != null ? tmp$ : Kotlin.throwNPE();
  }
  DomUtils$Companion.$metadata$ = {
    kind: Kotlin.Kind.OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var DomUtils$Companion_instance = null;
  function DomUtils$Companion_getInstance() {
    if (DomUtils$Companion_instance === null) {
      new DomUtils$Companion();
    }
    return DomUtils$Companion_instance;
  }
  DomUtils.prototype.append_46n0ku$ = function ($receiver, name) {
    $receiver.appendChild(document.createElement(name));
  };
  DomUtils.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'DomUtils',
    interfaces: []
  };
  function htmlExecution(testBase) {
    var tmp$;
    var body = (tmp$ = window.document.querySelector('body')) != null ? tmp$ : Kotlin.throwNPE();
    var tmp$_0;
    for (tmp$_0 = 0; tmp$_0 !== testBase.length; ++tmp$_0) {
      var element = testBase[tmp$_0];
      var $receiver = document.createElement('h2');
      $receiver.textContent = Kotlin.getKClassFromExpression(element).simpleName;
      body.append($receiver);
      var tmp$_1;
      tmp$_1 = element.tests.iterator();
      while (tmp$_1.hasNext()) {
        var element_0 = tmp$_1.next();
        var tmp$_2;
        var $receiver_0 = document.createElement('span');
        $receiver_0.className = 'resultDescription';
        var resultDescription = $receiver_0;
        var $receiver_1 = document.createElement('div');
        $receiver_1.appendChild(resultDescription);
        var $receiver_2 = document.createElement('span');
        $receiver_2.className = 'testName';
        $receiver_2.textContent = element_0.name;
        $receiver_1.appendChild($receiver_2);
        var divTest = $receiver_1;
        body.appendChild(divTest);
        var result = element_0.execute();
        if (Kotlin.isType(result, TestResult$KO))
          tmp$_2 = 'KO';
        else
          tmp$_2 = ' OK';
        var okOrKo = tmp$_2;
        divTest.className = 'testResult ' + okOrKo;
        resultDescription.textContent = okOrKo;
        if (Kotlin.isType(result, TestResult$KO)) {
          var $receiver_3 = document.createElement('div');
          $receiver_3.className = 'error';
          $receiver_3.textContent = result.message;
          divTest.appendChild($receiver_3);
        }
      }
    }
  }
  function TestBase() {
    this.tests = Kotlin.kotlin.collections.ArrayList_init_ww73n8$();
  }
  TestBase.prototype.shouldThrow_pcz9i6$ = Kotlin.defineInlineFunction('tests.io.data2viz.test.TestBase.shouldThrow_pcz9i6$', function (shouldThrow$T_0, isT, thunk) {
    var tmp$, tmp$_0;
    try {
      thunk();
      tmp$ = null;
    }
     catch (e) {
      if (Kotlin.isType(e, Error)) {
        tmp$ = e;
      }
       else
        throw e;
    }
    var e = tmp$;
    var exceptionClassName = Kotlin.getKClass(shouldThrow$T_0).simpleName;
    if (e == null)
      throw new Kotlin.kotlin.AssertionError('Expected exception ' + Kotlin.toString(Kotlin.getKClass(shouldThrow$T_0).simpleName) + ' but no exception was thrown');
    else if (!Kotlin.equals(Kotlin.getKClassFromExpression(e).simpleName, exceptionClassName))
      throw new Kotlin.kotlin.AssertionError('Expected exception ' + Kotlin.toString(Kotlin.getKClass(shouldThrow$T_0).simpleName) + ' but ' + Kotlin.toString(Kotlin.getKClassFromExpression(e).simpleName) + ' was thrown');
    else
      return isT(tmp$_0 = e) ? tmp$_0 : Kotlin.throwCCE();
  });
  TestBase.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'TestBase',
    interfaces: [Matchers]
  };
  function StringSpec() {
    TestBase.call(this);
  }
  StringSpec.prototype.invoke_79xod4$ = function ($receiver, test) {
    var tc = new TestCase($receiver, test);
    this.tests.add_11rb$(tc);
    return tc;
  };
  StringSpec.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'StringSpec',
    interfaces: [TestBase]
  };
  function TestCase(name, test) {
    this.name = name;
    this.test = test;
  }
  TestCase.prototype.execute = function () {
    try {
      this.test();
      return new TestResult$OK(this.name);
    }
     catch (e) {
      if (Kotlin.isType(e, AssertionError)) {
        return new TestResult$KO(this.name, e.message);
      }
       else
        throw e;
    }
  };
  TestCase.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'TestCase',
    interfaces: []
  };
  function TestResult(name) {
    this.name = name;
  }
  function TestResult$OK(name) {
    TestResult.call(this, name);
  }
  TestResult$OK.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'OK',
    interfaces: [TestResult]
  };
  function TestResult$KO(name, message) {
    TestResult.call(this, name);
    this.message = message;
  }
  TestResult$KO.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'KO',
    interfaces: [TestResult]
  };
  TestResult.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'TestResult',
    interfaces: []
  };
  function ExceptionMatchers() {
    StringSpec.call(this);
    this.invoke_79xod4$('block should throw', ExceptionMatchers_init$lambda(this));
  }
  function ExceptionMatchers_init$lambda(this$ExceptionMatchers) {
    return function () {
      var tmp$;
      try {
        throw new NullPointerException();
      }
       catch (e) {
        if (Kotlin.isType(e, Error)) {
          tmp$ = e;
        }
         else
          throw e;
      }
      var e = tmp$;
      var exceptionClassName = Kotlin.getKClass(NullPointerException).simpleName;
      if (e == null)
        throw new Kotlin.kotlin.AssertionError('Expected exception ' + Kotlin.toString(Kotlin.getKClass(NullPointerException).simpleName) + ' but no exception was thrown');
      else if (!Kotlin.equals(Kotlin.getKClassFromExpression(e).simpleName, exceptionClassName))
        throw new Kotlin.kotlin.AssertionError('Expected exception ' + Kotlin.toString(Kotlin.getKClass(NullPointerException).simpleName) + ' but ' + Kotlin.toString(Kotlin.getKClassFromExpression(e).simpleName) + ' was thrown');
      else {
        Kotlin.isType(e, NullPointerException) || Kotlin.throwCCE();
      }
    };
  }
  ExceptionMatchers.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'ExceptionMatchers',
    interfaces: [StringSpec]
  };
  function DoubleMatchers() {
    StringSpec.call(this);
    this.invoke_79xod4$('double should be exactly equals', DoubleMatchers_init$lambda(this));
    this.invoke_79xod4$('double should be plusOrMinus', DoubleMatchers_init$lambda_0(this));
    this.invoke_79xod4$('listOfNumber', DoubleMatchers_init$lambda_1(this));
  }
  function DoubleMatchers_init$lambda(this$DoubleMatchers) {
    return function () {
      this$DoubleMatchers.shouldBe_3ta935$(1.0, this$DoubleMatchers.exactly_14dthe$(1.0));
    };
  }
  function DoubleMatchers_init$lambda_0(this$DoubleMatchers) {
    return function () {
      this$DoubleMatchers.shouldBe_3ta935$(1.1, this$DoubleMatchers.plusOrMinus_38ydlf$(1.11, 0.011));
    };
  }
  function DoubleMatchers_init$lambda_1(this$DoubleMatchers) {
    return function () {
      this$DoubleMatchers.shouldBe_hvlbab$(listOf([1.0, 2.0]), listOf([1.0000001, 2.0000001]));
    };
  }
  DoubleMatchers.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'DoubleMatchers',
    interfaces: [StringSpec]
  };
  function StringMatchers() {
    StringSpec.call(this);
    this.invoke_79xod4$('b\xE9po\xE8d should start with b\xE9po', StringMatchers_init$lambda(this));
    this.invoke_79xod4$('b\xE9po\xE8^dlj should end with dlj', StringMatchers_init$lambda_0(this));
    this.invoke_79xod4$('auie should  have substring ui', StringMatchers_init$lambda_1(this));
    this.invoke_79xod4$('b\xE9po should match("b.+")', StringMatchers_init$lambda_2(this));
    this.invoke_79xod4$('auie should haveLength(4)', StringMatchers_init$lambda_3(this));
  }
  function StringMatchers_init$lambda(this$StringMatchers) {
    return function () {
      this$StringMatchers.should_3q7p3h$('b\xE9po\xE8^dl', this$StringMatchers.startWith_61zpoe$('b\xE9po'));
    };
  }
  function StringMatchers_init$lambda_0(this$StringMatchers) {
    return function () {
      this$StringMatchers.should_3q7p3h$('b\xE9po\xE8^dlj', this$StringMatchers.endWith_61zpoe$('dlj'));
    };
  }
  function StringMatchers_init$lambda_1(this$StringMatchers) {
    return function () {
      this$StringMatchers.substring_391xfc$(this$StringMatchers.should_rt1iod$('auie', have_getInstance()), 'ui');
    };
  }
  function StringMatchers_init$lambda_2(this$StringMatchers) {
    return function () {
      this$StringMatchers.should_3q7p3h$('b\xE9po', this$StringMatchers.match_61zpoe$('b.+'));
    };
  }
  function StringMatchers_init$lambda_3(this$StringMatchers) {
    return function () {
      this$StringMatchers.should_3q7p3h$('auie', this$StringMatchers.haveLength_za3lpa$(4));
    };
  }
  StringMatchers.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'StringMatchers',
    interfaces: [StringSpec]
  };
  function IntMatchers() {
    StringSpec.call(this);
    this.invoke_79xod4$('1 should be lt 2', IntMatchers_init$lambda(this));
    this.invoke_79xod4$('1 should be lte 1', IntMatchers_init$lambda_0(this));
    this.invoke_79xod4$('2 should be gt 1', IntMatchers_init$lambda_1(this));
    this.invoke_79xod4$('2 should be gte 2', IntMatchers_init$lambda_2(this));
  }
  function IntMatchers_init$lambda(this$IntMatchers) {
    return function () {
      this$IntMatchers.lt_wqnyl5$(this$IntMatchers.should_o3dfsi$(1, be_getInstance()), 2);
    };
  }
  function IntMatchers_init$lambda_0(this$IntMatchers) {
    return function () {
      this$IntMatchers.lte_wqnyl5$(this$IntMatchers.should_o3dfsi$(1, be_getInstance()), 1);
    };
  }
  function IntMatchers_init$lambda_1(this$IntMatchers) {
    return function () {
      this$IntMatchers.gt_wqnyl5$(this$IntMatchers.should_o3dfsi$(2, be_getInstance()), 1);
    };
  }
  function IntMatchers_init$lambda_2(this$IntMatchers) {
    return function () {
      this$IntMatchers.gte_wqnyl5$(this$IntMatchers.should_o3dfsi$(2, be_getInstance()), 2);
    };
  }
  IntMatchers.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'IntMatchers',
    interfaces: [StringSpec]
  };
  function LongMatchers() {
    StringSpec.call(this);
    this.invoke_79xod4$('1 should be lt 2', LongMatchers_init$lambda(this));
    this.invoke_79xod4$('1 should be lte 1', LongMatchers_init$lambda_0(this));
    this.invoke_79xod4$('2 should be gt 1', LongMatchers_init$lambda_1(this));
    this.invoke_79xod4$('2 should be gte 2', LongMatchers_init$lambda_2(this));
  }
  function LongMatchers_init$lambda(this$LongMatchers) {
    return function () {
      this$LongMatchers.lt_btauwj$(this$LongMatchers.should_o3dfsi$(Kotlin.Long.ONE, be_getInstance()), Kotlin.Long.fromInt(2));
    };
  }
  function LongMatchers_init$lambda_0(this$LongMatchers) {
    return function () {
      this$LongMatchers.lte_btauwj$(this$LongMatchers.should_o3dfsi$(Kotlin.Long.ONE, be_getInstance()), Kotlin.Long.ONE);
    };
  }
  function LongMatchers_init$lambda_1(this$LongMatchers) {
    return function () {
      this$LongMatchers.gt_btauwj$(this$LongMatchers.should_o3dfsi$(Kotlin.Long.fromInt(2), be_getInstance()), Kotlin.Long.ONE);
    };
  }
  function LongMatchers_init$lambda_2(this$LongMatchers) {
    return function () {
      this$LongMatchers.gte_btauwj$(this$LongMatchers.should_o3dfsi$(Kotlin.Long.fromInt(2), be_getInstance()), Kotlin.Long.fromInt(2));
    };
  }
  LongMatchers.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'LongMatchers',
    interfaces: [StringSpec]
  };
  function TestCollectionMatchers() {
    StringSpec.call(this);
    this.invoke_79xod4$('empty collection', TestCollectionMatchers_init$lambda(this));
    this.invoke_79xod4$('collection size', TestCollectionMatchers_init$lambda_0(this));
    this.invoke_79xod4$('collection contain', TestCollectionMatchers_init$lambda_1(this));
    this.invoke_79xod4$('collection contains elements', TestCollectionMatchers_init$lambda_2(this));
  }
  function TestCollectionMatchers_init$lambda(this$TestCollectionMatchers) {
    return function () {
      this$TestCollectionMatchers.should_9bxh2u$(emptyList(), this$TestCollectionMatchers.beEmpty());
      var tmp$;
      try {
        var this$TestCollectionMatchers_0 = this$TestCollectionMatchers;
        this$TestCollectionMatchers_0.should_9bxh2u$(listOf_0(1), this$TestCollectionMatchers_0.beEmpty());
        tmp$ = null;
      }
       catch (e) {
        if (Kotlin.isType(e, Error)) {
          tmp$ = e;
        }
         else
          throw e;
      }
      var e = tmp$;
      var exceptionClassName = Kotlin.getKClass(AssertionError).simpleName;
      if (e == null)
        throw new Kotlin.kotlin.AssertionError('Expected exception ' + Kotlin.toString(Kotlin.getKClass(AssertionError).simpleName) + ' but no exception was thrown');
      else if (!Kotlin.equals(Kotlin.getKClassFromExpression(e).simpleName, exceptionClassName))
        throw new Kotlin.kotlin.AssertionError('Expected exception ' + Kotlin.toString(Kotlin.getKClass(AssertionError).simpleName) + ' but ' + Kotlin.toString(Kotlin.getKClassFromExpression(e).simpleName) + ' was thrown');
      else {
        Kotlin.isType(e, AssertionError) || Kotlin.throwCCE();
      }
    };
  }
  function TestCollectionMatchers_init$lambda_0(this$TestCollectionMatchers) {
    return function () {
      this$TestCollectionMatchers.should_3q7p3h$(listOf([1, 2]), this$TestCollectionMatchers.haveSize_ww73n8$(2));
    };
  }
  function TestCollectionMatchers_init$lambda_1(this$TestCollectionMatchers) {
    return function () {
      this$TestCollectionMatchers.should_3q7p3h$(listOf([1, 2]), this$TestCollectionMatchers.contain_mh5how$(2));
    };
  }
  function TestCollectionMatchers_init$lambda_2(this$TestCollectionMatchers) {
    return function () {
      this$TestCollectionMatchers.should_3q7p3h$(listOf([1, 2, 3, 4, 5]), this$TestCollectionMatchers.containInAnyOrder_i5x0yv$([4, 3, 2]));
    };
  }
  TestCollectionMatchers.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'TestCollectionMatchers',
    interfaces: [StringSpec]
  };
  function CollectionMatchers() {
  }
  function CollectionMatchers$beEmpty$lambda(value) {
    if (!value.isEmpty())
      throw new AssertionError('Collection was expected to be empty but has size ' + value.size);
  }
  CollectionMatchers.prototype.beEmpty = function () {
    return CollectionMatchers$beEmpty$lambda;
  };
  CollectionMatchers.prototype.size_cf6rj3$ = function ($receiver, expected) {
    var size = $receiver.value.size;
    if (size !== expected)
      throw new AssertionError('Collection was expected to have size ' + expected + ' but had size ' + size);
  };
  CollectionMatchers.prototype.element_4rg9ei$ = function ($receiver, expected) {
    if (!$receiver.value.contains_11rb$(expected))
      throw new AssertionError('Collection did not have expected element ' + expected);
  };
  function CollectionMatchers$containInAnyOrder$ObjectLiteral(closure$ts) {
    this.closure$ts = closure$ts;
  }
  CollectionMatchers$containInAnyOrder$ObjectLiteral.prototype.test_11rb$ = function (value) {
    var tmp$, tmp$_0;
    tmp$ = this.closure$ts;
    for (tmp$_0 = 0; tmp$_0 !== tmp$.length; ++tmp$_0) {
      var t = tmp$[tmp$_0];
      if (!value.contains_11rb$(t))
        throw new AssertionError('Collection did not contain value ' + t);
    }
  };
  CollectionMatchers$containInAnyOrder$ObjectLiteral.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    interfaces: [Matcher]
  };
  CollectionMatchers.prototype.containInAnyOrder_i5x0yv$ = function (ts) {
    return new CollectionMatchers$containInAnyOrder$ObjectLiteral(ts);
  };
  function CollectionMatchers$haveSize$ObjectLiteral(closure$size) {
    this.closure$size = closure$size;
  }
  CollectionMatchers$haveSize$ObjectLiteral.prototype.test_11rb$ = function (value) {
    if (value.size !== this.closure$size)
      throw new AssertionError('Collection did not have size ' + this.closure$size);
  };
  CollectionMatchers$haveSize$ObjectLiteral.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    interfaces: [Matcher]
  };
  CollectionMatchers.prototype.haveSize_ww73n8$ = function (size) {
    return new CollectionMatchers$haveSize$ObjectLiteral(size);
  };
  function CollectionMatchers$contain$ObjectLiteral(closure$t) {
    this.closure$t = closure$t;
  }
  CollectionMatchers$contain$ObjectLiteral.prototype.test_11rb$ = function (value) {
    if (!value.contains_11rb$(this.closure$t))
      throw new AssertionError('Collection did not contain element ' + this.closure$t);
  };
  CollectionMatchers$contain$ObjectLiteral.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    interfaces: [Matcher]
  };
  CollectionMatchers.prototype.contain_mh5how$ = function (t) {
    return new CollectionMatchers$contain$ObjectLiteral(t);
  };
  CollectionMatchers.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'CollectionMatchers',
    interfaces: []
  };
  function DoubleMatchers_0() {
  }
  DoubleMatchers_0.prototype.plusOrMinus_38ydlf$ = function ($receiver, tolerance) {
    return new ToleranceMatcher($receiver, tolerance);
  };
  function DoubleMatchers$exactly$ObjectLiteral(closure$d) {
    this.closure$d = closure$d;
  }
  DoubleMatchers$exactly$ObjectLiteral.prototype.test_11rb$ = function (value) {
    if (value !== this.closure$d)
      throw new AssertionError(value.toString() + ' is not equal to expected value ' + this.closure$d);
  };
  DoubleMatchers$exactly$ObjectLiteral.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    interfaces: [Matcher]
  };
  DoubleMatchers_0.prototype.exactly_14dthe$ = function (d) {
    return new DoubleMatchers$exactly$ObjectLiteral(d);
  };
  DoubleMatchers_0.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'DoubleMatchers',
    interfaces: []
  };
  function ToleranceMatcher(expected, tolerance) {
    this.expected = expected;
    this.tolerance = tolerance;
  }
  ToleranceMatcher.prototype.test_11rb$ = function (value) {
    if (this.tolerance === 0.0)
      println('[WARN] When comparing doubles consider using tolerance, eg: a shouldBe b plusOrMinus c');
    var diff = Math.abs(value - this.expected);
    if (diff > this.tolerance)
      throw new AssertionError(value.toString() + ' is not equal to ' + this.expected);
  };
  ToleranceMatcher.prototype.plusOrMinus_14dthe$ = function (tolerance) {
    return new ToleranceMatcher(this.expected, tolerance);
  };
  ToleranceMatcher.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'ToleranceMatcher',
    interfaces: [Matcher]
  };
  function ExceptionMatchers_0() {
  }
  ExceptionMatchers_0.prototype.expecting_l9770i$ = function (kclass, thunk) {
    var tmp$;
    try {
      thunk();
      tmp$ = null;
    }
     catch (exception) {
      if (Kotlin.isType(exception, Exception)) {
        tmp$ = exception;
      }
       else
        throw exception;
    }
    var exception = tmp$;
    if (exception == null)
      throw new AssertionError('Expected exception ' + kclass + ' but no exception was thrown');
    else if (!Kotlin.equals(get_js(Kotlin.getKClassFromExpression(exception)), get_js(kclass))) {
      throw new AssertionError('Expected exception ' + kclass + ' but ' + Kotlin.toString(exception) + ' was thrown');
    }
  };
  ExceptionMatchers_0.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'ExceptionMatchers',
    interfaces: []
  };
  function IntMatchers_0() {
  }
  IntMatchers_0.prototype.gt_wqnyl5$ = function ($receiver, expected) {
    if ($receiver.value <= expected)
      throw new AssertionError($receiver.value.toString() + ' is not greater than ' + expected);
  };
  IntMatchers_0.prototype.lt_wqnyl5$ = function ($receiver, expected) {
    if ($receiver.value >= expected)
      throw new AssertionError($receiver.value.toString() + ' is not less than ' + expected);
  };
  IntMatchers_0.prototype.gte_wqnyl5$ = function ($receiver, expected) {
    if ($receiver.value < expected)
      throw new AssertionError($receiver.value.toString() + ' is not greater than or equal to ' + expected);
  };
  IntMatchers_0.prototype.lte_wqnyl5$ = function ($receiver, expected) {
    if ($receiver.value > expected)
      throw new AssertionError($receiver.value.toString() + ' is not less than or equal to ' + expected);
  };
  IntMatchers_0.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'IntMatchers',
    interfaces: []
  };
  function LongMatchers_0() {
  }
  LongMatchers_0.prototype.gt_btauwj$ = function ($receiver, expected) {
    if ($receiver.value.compareTo_11rb$(expected) <= 0)
      throw new AssertionError($receiver.value.toString() + ' is not greater than ' + expected);
  };
  LongMatchers_0.prototype.lt_btauwj$ = function ($receiver, expected) {
    if ($receiver.value.compareTo_11rb$(expected) >= 0)
      throw new AssertionError($receiver.value.toString() + ' is not less than ' + expected);
  };
  LongMatchers_0.prototype.gte_btauwj$ = function ($receiver, expected) {
    if ($receiver.value.compareTo_11rb$(expected) < 0)
      throw new AssertionError($receiver.value.toString() + ' is not greater than or equal to ' + expected);
  };
  LongMatchers_0.prototype.lte_btauwj$ = function ($receiver, expected) {
    if ($receiver.value.compareTo_11rb$(expected) > 0)
      throw new AssertionError($receiver.value.toString() + ' is not less than or equal to ' + expected);
  };
  LongMatchers_0.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'LongMatchers',
    interfaces: []
  };
  function MapMatchers() {
  }
  function MapMatchers$haveKey$ObjectLiteral(closure$key) {
    this.closure$key = closure$key;
  }
  MapMatchers$haveKey$ObjectLiteral.prototype.test_11rb$ = function (value) {
    if (!value.containsKey_11rb$(this.closure$key))
      throw new AssertionError('Map did not contain key ' + this.closure$key);
  };
  MapMatchers$haveKey$ObjectLiteral.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    interfaces: [Matcher]
  };
  MapMatchers.prototype.haveKey_mh5how$ = function (key) {
    return new MapMatchers$haveKey$ObjectLiteral(key);
  };
  function MapMatchers$haveValue$ObjectLiteral(closure$v) {
    this.closure$v = closure$v;
  }
  MapMatchers$haveValue$ObjectLiteral.prototype.test_11rb$ = function (value) {
    if (!value.containsValue_11rc$(this.closure$v))
      throw new AssertionError('Map did not contain value ' + this.closure$v);
  };
  MapMatchers$haveValue$ObjectLiteral.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    interfaces: [Matcher]
  };
  MapMatchers.prototype.haveValue_mh5how$ = function (v) {
    return new MapMatchers$haveValue$ObjectLiteral(v);
  };
  function MapMatchers$contain$ObjectLiteral(closure$key, closure$v) {
    this.closure$key = closure$key;
    this.closure$v = closure$v;
  }
  MapMatchers$contain$ObjectLiteral.prototype.test_11rb$ = function (value) {
    if (!Kotlin.equals(value.get_11rb$(this.closure$key), this.closure$v))
      throw new AssertionError('Map did not contain mapping ' + this.closure$key + '=' + value);
  };
  MapMatchers$contain$ObjectLiteral.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    interfaces: [Matcher]
  };
  MapMatchers.prototype.contain_o5fpdy$ = function (key, v) {
    return new MapMatchers$contain$ObjectLiteral(key, v);
  };
  MapMatchers.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'MapMatchers',
    interfaces: []
  };
  function Keyword() {
  }
  Keyword.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'Keyword',
    interfaces: []
  };
  function have() {
    have_instance = this;
  }
  have.$metadata$ = {
    kind: Kotlin.Kind.OBJECT,
    simpleName: 'have',
    interfaces: [Keyword]
  };
  var have_instance = null;
  function have_getInstance() {
    if (have_instance === null) {
      new have();
    }
    return have_instance;
  }
  function be() {
    be_instance = this;
  }
  be.$metadata$ = {
    kind: Kotlin.Kind.OBJECT,
    simpleName: 'be',
    interfaces: [Keyword]
  };
  var be_instance = null;
  function be_getInstance() {
    if (be_instance === null) {
      new be();
    }
    return be_instance;
  }
  function end() {
    end_instance = this;
  }
  end.$metadata$ = {
    kind: Kotlin.Kind.OBJECT,
    simpleName: 'end',
    interfaces: [Keyword]
  };
  var end_instance = null;
  function end_getInstance() {
    if (end_instance === null) {
      new end();
    }
    return end_instance;
  }
  function start() {
    start_instance = this;
  }
  start.$metadata$ = {
    kind: Kotlin.Kind.OBJECT,
    simpleName: 'start',
    interfaces: [Keyword]
  };
  var start_instance = null;
  function start_getInstance() {
    if (start_instance === null) {
      new start();
    }
    return start_instance;
  }
  function contain() {
    contain_instance = this;
  }
  contain.$metadata$ = {
    kind: Kotlin.Kind.OBJECT,
    simpleName: 'contain',
    interfaces: [Keyword]
  };
  var contain_instance = null;
  function contain_getInstance() {
    if (contain_instance === null) {
      new contain();
    }
    return contain_instance;
  }
  function include() {
    include_instance = this;
  }
  include.$metadata$ = {
    kind: Kotlin.Kind.OBJECT,
    simpleName: 'include',
    interfaces: [Keyword]
  };
  var include_instance = null;
  function include_getInstance() {
    if (include_instance === null) {
      new include();
    }
    return include_instance;
  }
  function Matchers() {
  }
  Matchers.prototype.fail_61zpoe$ = function (msg) {
    throw new AssertionError(msg);
  };
  Matchers.prototype.shouldBe_38ydlf$ = function ($receiver, other) {
    (new ToleranceMatcher(other, 0.0)).test_11rb$($receiver);
  };
  Matchers.prototype.shouldBe_hvlbab$ = function ($receiver, other) {
    if (count($receiver) !== count(other))
      throw new AssertionError($receiver + " doesn't have the same size as " + other);
    var tmp$;
    tmp$ = zip($receiver, other).iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      (new ToleranceMatcher(Kotlin.numberToDouble(element.second), 1.0E-6)).test_11rb$(Kotlin.numberToDouble(element.first));
    }
  };
  Matchers.prototype.shouldBe_3ta935$ = function ($receiver, any) {
    this.shouldEqual_3ta935$($receiver, any);
  };
  Matchers.prototype.shouldEqual_3ta935$ = function ($receiver, any) {
    var tmp$;
    if (Kotlin.isType(any, Matcher))
      (Kotlin.isType(tmp$ = any, Matcher) ? tmp$ : Kotlin.throwCCE()).test_11rb$($receiver);
    else {
      if ($receiver == null && any != null)
        throw new AssertionError($receiver + ' did not equal ' + Kotlin.toString(any));
      if ($receiver != null && any == null)
        throw new AssertionError($receiver + ' did not equal ' + Kotlin.toString(any));
      if (!Kotlin.equals($receiver, any))
        throw new AssertionError($receiver + ' did not equal ' + Kotlin.toString(any));
    }
  };
  Matchers.prototype.should_9bxh2u$ = function ($receiver, matcher) {
    matcher($receiver);
  };
  Matchers.prototype.should_3q7p3h$ = function ($receiver, matcher) {
    matcher.test_11rb$($receiver);
  };
  Matchers.prototype.should_rt1iod$ = function ($receiver, x) {
    return new HaveWrapper($receiver);
  };
  Matchers.prototype.should_ly0i9h$ = function ($receiver, x) {
    return new StartWrapper($receiver);
  };
  Matchers.prototype.should_bdao1w$ = function ($receiver, x) {
    return new EndWrapper($receiver);
  };
  Matchers.prototype.should_o3dfsi$ = function ($receiver, x) {
    return new BeWrapper($receiver);
  };
  Matchers.prototype.should_n5j1vh$ = function ($receiver, x) {
    return new ContainWrapper($receiver);
  };
  Matchers.prototype.should_oa1att$ = function ($receiver, x) {
    return new IncludeWrapper($receiver);
  };
  Matchers.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'Matchers',
    interfaces: [TypeMatchers, MapMatchers, LongMatchers_0, IntMatchers_0, ExceptionMatchers_0, DoubleMatchers_0, CollectionMatchers, StringMatchers_0]
  };
  function Matcher() {
  }
  Matcher.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'Matcher',
    interfaces: []
  };
  function HaveWrapper(value) {
    this.value = value;
  }
  HaveWrapper.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'HaveWrapper',
    interfaces: []
  };
  function BeWrapper(value) {
    this.value = value;
  }
  BeWrapper.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'BeWrapper',
    interfaces: []
  };
  function StartWrapper(value) {
    this.value = value;
  }
  StartWrapper.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'StartWrapper',
    interfaces: []
  };
  function EndWrapper(value) {
    this.value = value;
  }
  EndWrapper.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'EndWrapper',
    interfaces: []
  };
  function IncludeWrapper(value) {
    this.value = value;
  }
  IncludeWrapper.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'IncludeWrapper',
    interfaces: []
  };
  function ContainWrapper(value) {
    this.value = value;
  }
  ContainWrapper.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'ContainWrapper',
    interfaces: []
  };
  function StringMatchers_0() {
  }
  StringMatchers_0.prototype.substring_391xfc$ = function ($receiver, substr) {
    if (!contains($receiver.value, substr))
      throw new AssertionError('String does not have substring ' + substr);
  };
  StringMatchers_0.prototype.with_4kze9q$ = function ($receiver, prefix) {
    if (!startsWith($receiver.value, prefix))
      throw new AssertionError('String does not start with ' + prefix + ' but with ' + take($receiver.value, prefix.length));
  };
  StringMatchers_0.prototype.with_973bph$ = function ($receiver, suffix) {
    if (!endsWith($receiver.value, suffix))
      throw new AssertionError('String does not end with ' + suffix + ' but with ' + takeLast($receiver.value, suffix.length));
  };
  function StringMatchers$startWith$ObjectLiteral(closure$prefix) {
    this.closure$prefix = closure$prefix;
  }
  StringMatchers$startWith$ObjectLiteral.prototype.test_11rb$ = function (value) {
    if (!startsWith(value, this.closure$prefix))
      throw new AssertionError('String ' + value + ' does not start with ' + this.closure$prefix);
  };
  StringMatchers$startWith$ObjectLiteral.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    interfaces: [Matcher]
  };
  StringMatchers_0.prototype.startWith_61zpoe$ = function (prefix) {
    return new StringMatchers$startWith$ObjectLiteral(prefix);
  };
  function StringMatchers$endWith$ObjectLiteral(closure$suffix) {
    this.closure$suffix = closure$suffix;
  }
  StringMatchers$endWith$ObjectLiteral.prototype.test_11rb$ = function (value) {
    if (!endsWith(value, this.closure$suffix))
      throw new AssertionError('String ' + value + ' does not end with with ' + this.closure$suffix);
  };
  StringMatchers$endWith$ObjectLiteral.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    interfaces: [Matcher]
  };
  StringMatchers_0.prototype.endWith_61zpoe$ = function (suffix) {
    return new StringMatchers$endWith$ObjectLiteral(suffix);
  };
  function StringMatchers$match$ObjectLiteral(closure$regex) {
    this.closure$regex = closure$regex;
  }
  StringMatchers$match$ObjectLiteral.prototype.test_11rb$ = function (value) {
    var $receiver = this.closure$regex;
    if (!Kotlin.kotlin.text.Regex_61zpoe$($receiver).matches_6bul2c$(value))
      throw new AssertionError('String ' + value + ' does not match regex ' + this.closure$regex);
  };
  StringMatchers$match$ObjectLiteral.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    interfaces: [Matcher]
  };
  StringMatchers_0.prototype.match_61zpoe$ = function (regex) {
    return new StringMatchers$match$ObjectLiteral(regex);
  };
  function StringMatchers$haveLength$ObjectLiteral(closure$length) {
    this.closure$length = closure$length;
  }
  StringMatchers$haveLength$ObjectLiteral.prototype.test_11rb$ = function (value) {
    if (value.length !== this.closure$length)
      throw new AssertionError('String ' + value + ' does not have length ' + this.closure$length);
  };
  StringMatchers$haveLength$ObjectLiteral.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    interfaces: [Matcher]
  };
  StringMatchers_0.prototype.haveLength_za3lpa$ = function (length) {
    return new StringMatchers$haveLength$ObjectLiteral(length);
  };
  StringMatchers_0.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'StringMatchers',
    interfaces: []
  };
  function TypeMatchers() {
  }
  TypeMatchers.prototype.a_75u09w$ = function ($receiver, expected) {
    this.an_75u09w$($receiver, expected);
  };
  TypeMatchers.prototype.an_75u09w$ = function ($receiver, expected) {
    if (!expected.isInstance_s8jyv4$($receiver.value))
      throw new AssertionError(Kotlin.toString($receiver.value) + ' is not of type ' + expected);
  };
  TypeMatchers.prototype.theSameInstanceAs_9qtuyl$ = function ($receiver, ref) {
    if ($receiver.value !== ref)
      throw new AssertionError($receiver.value + ' is not the same reference as ' + ref);
  };
  function TypeMatchers$beTheSameInstanceAs$ObjectLiteral(closure$ref) {
    this.closure$ref = closure$ref;
  }
  TypeMatchers$beTheSameInstanceAs$ObjectLiteral.prototype.test_11rb$ = function (value) {
    if (value !== this.closure$ref)
      throw new AssertionError(value + ' is not the same reference as ' + this.closure$ref);
  };
  TypeMatchers$beTheSameInstanceAs$ObjectLiteral.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    interfaces: [Matcher]
  };
  TypeMatchers.prototype.beTheSameInstanceAs_mh5how$ = function (ref) {
    return new TypeMatchers$beTheSameInstanceAs$ObjectLiteral(ref);
  };
  TypeMatchers.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'TypeMatchers',
    interfaces: []
  };
  Matchers.prototype.substring_391xfc$ = StringMatchers_0.prototype.substring_391xfc$;
  Matchers.prototype.with_4kze9q$ = StringMatchers_0.prototype.with_4kze9q$;
  Matchers.prototype.with_973bph$ = StringMatchers_0.prototype.with_973bph$;
  Matchers.prototype.startWith_61zpoe$ = StringMatchers_0.prototype.startWith_61zpoe$;
  Matchers.prototype.endWith_61zpoe$ = StringMatchers_0.prototype.endWith_61zpoe$;
  Matchers.prototype.match_61zpoe$ = StringMatchers_0.prototype.match_61zpoe$;
  Matchers.prototype.haveLength_za3lpa$ = StringMatchers_0.prototype.haveLength_za3lpa$;
  Matchers.prototype.beEmpty = CollectionMatchers.prototype.beEmpty;
  Matchers.prototype.size_cf6rj3$ = CollectionMatchers.prototype.size_cf6rj3$;
  Matchers.prototype.element_4rg9ei$ = CollectionMatchers.prototype.element_4rg9ei$;
  Matchers.prototype.containInAnyOrder_i5x0yv$ = CollectionMatchers.prototype.containInAnyOrder_i5x0yv$;
  Matchers.prototype.haveSize_ww73n8$ = CollectionMatchers.prototype.haveSize_ww73n8$;
  Matchers.prototype.contain_mh5how$ = CollectionMatchers.prototype.contain_mh5how$;
  Matchers.prototype.plusOrMinus_38ydlf$ = DoubleMatchers_0.prototype.plusOrMinus_38ydlf$;
  Matchers.prototype.exactly_14dthe$ = DoubleMatchers_0.prototype.exactly_14dthe$;
  Matchers.prototype.expecting_l9770i$ = ExceptionMatchers_0.prototype.expecting_l9770i$;
  Matchers.prototype.gt_wqnyl5$ = IntMatchers_0.prototype.gt_wqnyl5$;
  Matchers.prototype.lt_wqnyl5$ = IntMatchers_0.prototype.lt_wqnyl5$;
  Matchers.prototype.gte_wqnyl5$ = IntMatchers_0.prototype.gte_wqnyl5$;
  Matchers.prototype.lte_wqnyl5$ = IntMatchers_0.prototype.lte_wqnyl5$;
  Matchers.prototype.gt_btauwj$ = LongMatchers_0.prototype.gt_btauwj$;
  Matchers.prototype.lt_btauwj$ = LongMatchers_0.prototype.lt_btauwj$;
  Matchers.prototype.gte_btauwj$ = LongMatchers_0.prototype.gte_btauwj$;
  Matchers.prototype.lte_btauwj$ = LongMatchers_0.prototype.lte_btauwj$;
  Matchers.prototype.haveKey_mh5how$ = MapMatchers.prototype.haveKey_mh5how$;
  Matchers.prototype.haveValue_mh5how$ = MapMatchers.prototype.haveValue_mh5how$;
  Matchers.prototype.contain_o5fpdy$ = MapMatchers.prototype.contain_o5fpdy$;
  Matchers.prototype.a_75u09w$ = TypeMatchers.prototype.a_75u09w$;
  Matchers.prototype.an_75u09w$ = TypeMatchers.prototype.an_75u09w$;
  Matchers.prototype.theSameInstanceAs_9qtuyl$ = TypeMatchers.prototype.theSameInstanceAs_9qtuyl$;
  Matchers.prototype.beTheSameInstanceAs_mh5how$ = TypeMatchers.prototype.beTheSameInstanceAs_mh5how$;
  TestBase.prototype.fail_61zpoe$ = Matchers.prototype.fail_61zpoe$;
  TestBase.prototype.shouldBe_38ydlf$ = Matchers.prototype.shouldBe_38ydlf$;
  TestBase.prototype.shouldBe_hvlbab$ = Matchers.prototype.shouldBe_hvlbab$;
  TestBase.prototype.shouldBe_3ta935$ = Matchers.prototype.shouldBe_3ta935$;
  TestBase.prototype.shouldEqual_3ta935$ = Matchers.prototype.shouldEqual_3ta935$;
  TestBase.prototype.should_9bxh2u$ = Matchers.prototype.should_9bxh2u$;
  TestBase.prototype.should_3q7p3h$ = Matchers.prototype.should_3q7p3h$;
  TestBase.prototype.should_rt1iod$ = Matchers.prototype.should_rt1iod$;
  TestBase.prototype.should_ly0i9h$ = Matchers.prototype.should_ly0i9h$;
  TestBase.prototype.should_bdao1w$ = Matchers.prototype.should_bdao1w$;
  TestBase.prototype.should_o3dfsi$ = Matchers.prototype.should_o3dfsi$;
  TestBase.prototype.should_n5j1vh$ = Matchers.prototype.should_n5j1vh$;
  TestBase.prototype.should_oa1att$ = Matchers.prototype.should_oa1att$;
  TestBase.prototype.substring_391xfc$ = Matchers.prototype.substring_391xfc$;
  TestBase.prototype.with_4kze9q$ = Matchers.prototype.with_4kze9q$;
  TestBase.prototype.with_973bph$ = Matchers.prototype.with_973bph$;
  TestBase.prototype.startWith_61zpoe$ = Matchers.prototype.startWith_61zpoe$;
  TestBase.prototype.endWith_61zpoe$ = Matchers.prototype.endWith_61zpoe$;
  TestBase.prototype.match_61zpoe$ = Matchers.prototype.match_61zpoe$;
  TestBase.prototype.haveLength_za3lpa$ = Matchers.prototype.haveLength_za3lpa$;
  TestBase.prototype.beEmpty = Matchers.prototype.beEmpty;
  TestBase.prototype.size_cf6rj3$ = Matchers.prototype.size_cf6rj3$;
  TestBase.prototype.element_4rg9ei$ = Matchers.prototype.element_4rg9ei$;
  TestBase.prototype.containInAnyOrder_i5x0yv$ = Matchers.prototype.containInAnyOrder_i5x0yv$;
  TestBase.prototype.haveSize_ww73n8$ = Matchers.prototype.haveSize_ww73n8$;
  TestBase.prototype.contain_mh5how$ = Matchers.prototype.contain_mh5how$;
  TestBase.prototype.plusOrMinus_38ydlf$ = Matchers.prototype.plusOrMinus_38ydlf$;
  TestBase.prototype.exactly_14dthe$ = Matchers.prototype.exactly_14dthe$;
  TestBase.prototype.expecting_l9770i$ = Matchers.prototype.expecting_l9770i$;
  TestBase.prototype.gt_wqnyl5$ = Matchers.prototype.gt_wqnyl5$;
  TestBase.prototype.lt_wqnyl5$ = Matchers.prototype.lt_wqnyl5$;
  TestBase.prototype.gte_wqnyl5$ = Matchers.prototype.gte_wqnyl5$;
  TestBase.prototype.lte_wqnyl5$ = Matchers.prototype.lte_wqnyl5$;
  TestBase.prototype.gt_btauwj$ = Matchers.prototype.gt_btauwj$;
  TestBase.prototype.lt_btauwj$ = Matchers.prototype.lt_btauwj$;
  TestBase.prototype.gte_btauwj$ = Matchers.prototype.gte_btauwj$;
  TestBase.prototype.lte_btauwj$ = Matchers.prototype.lte_btauwj$;
  TestBase.prototype.haveKey_mh5how$ = Matchers.prototype.haveKey_mh5how$;
  TestBase.prototype.haveValue_mh5how$ = Matchers.prototype.haveValue_mh5how$;
  TestBase.prototype.contain_o5fpdy$ = Matchers.prototype.contain_o5fpdy$;
  TestBase.prototype.a_75u09w$ = Matchers.prototype.a_75u09w$;
  TestBase.prototype.an_75u09w$ = Matchers.prototype.an_75u09w$;
  TestBase.prototype.theSameInstanceAs_9qtuyl$ = Matchers.prototype.theSameInstanceAs_9qtuyl$;
  TestBase.prototype.beTheSameInstanceAs_mh5how$ = Matchers.prototype.beTheSameInstanceAs_mh5how$;
  Object.defineProperty(DomUtils, 'Companion', {
    get: DomUtils$Companion_getInstance
  });
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$test = package$data2viz.test || (package$data2viz.test = {});
  package$test.DomUtils = DomUtils;
  package$test.htmlExecution_raw9bf$ = htmlExecution;
  package$test.TestBase = TestBase;
  package$test.StringSpec = StringSpec;
  package$test.TestCase = TestCase;
  TestResult.OK = TestResult$OK;
  TestResult.KO = TestResult$KO;
  package$test.TestResult = TestResult;
  package$test.ExceptionMatchers = ExceptionMatchers;
  package$test.DoubleMatchers = DoubleMatchers;
  package$test.StringMatchers = StringMatchers;
  package$test.IntMatchers = IntMatchers;
  package$test.LongMatchers = LongMatchers;
  package$test.TestCollectionMatchers = TestCollectionMatchers;
  var package$matchers = package$test.matchers || (package$test.matchers = {});
  package$matchers.CollectionMatchers = CollectionMatchers;
  package$matchers.DoubleMatchers = DoubleMatchers_0;
  package$matchers.ToleranceMatcher = ToleranceMatcher;
  package$matchers.ExceptionMatchers = ExceptionMatchers_0;
  package$matchers.IntMatchers = IntMatchers_0;
  package$matchers.LongMatchers = LongMatchers_0;
  package$matchers.MapMatchers = MapMatchers;
  package$matchers.Keyword = Keyword;
  Object.defineProperty(package$matchers, 'have', {
    get: have_getInstance
  });
  Object.defineProperty(package$matchers, 'be', {
    get: be_getInstance
  });
  Object.defineProperty(package$matchers, 'end', {
    get: end_getInstance
  });
  Object.defineProperty(package$matchers, 'start', {
    get: start_getInstance
  });
  Object.defineProperty(package$matchers, 'contain', {
    get: contain_getInstance
  });
  Object.defineProperty(package$matchers, 'include', {
    get: include_getInstance
  });
  package$matchers.Matchers = Matchers;
  package$matchers.Matcher = Matcher;
  package$matchers.HaveWrapper = HaveWrapper;
  package$matchers.BeWrapper = BeWrapper;
  package$matchers.StartWrapper = StartWrapper;
  package$matchers.EndWrapper = EndWrapper;
  package$matchers.IncludeWrapper = IncludeWrapper;
  package$matchers.ContainWrapper = ContainWrapper;
  package$matchers.StringMatchers = StringMatchers_0;
  package$matchers.TypeMatchers = TypeMatchers;
  Kotlin.defineModule('tests', _);
  return _;
});

//@ sourceMappingURL=tests.js.map
