(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'd2v-tests-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'd2v-tests-js'.");
    }
    root['d2v-tests-js'] = factory(typeof this['d2v-tests-js'] === 'undefined' ? {} : this['d2v-tests-js'], kotlin);
  }
}(this, function (_, Kotlin) {
  'use strict';
  var $$importsForInline$$ = _.$$importsForInline$$ || (_.$$importsForInline$$ = {});
  var AssertionError_init = Kotlin.kotlin.AssertionError_init_pdl1vj$;
  var Unit = Kotlin.kotlin.Unit;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var Kind_INTERFACE = Kotlin.Kind.INTERFACE;
  var isNaN_0 = Kotlin.kotlin.isNaN_yrwdxr$;
  var println = Kotlin.kotlin.io.println_s8jyv4$;
  var Math_0 = Math;
  var equals = Kotlin.equals;
  var Kind_OBJECT = Kotlin.Kind.OBJECT;
  var toString = Kotlin.toString;
  var count = Kotlin.kotlin.collections.count_7wnvza$;
  var zip = Kotlin.kotlin.collections.zip_45mdf7$;
  var numberToDouble = Kotlin.numberToDouble;
  var throwCCE = Kotlin.throwCCE;
  var contains = Kotlin.kotlin.text.contains_li3zpu$;
  var startsWith = Kotlin.kotlin.text.startsWith_7epoxm$;
  var take = Kotlin.kotlin.text.take_6ic1pp$;
  var endsWith = Kotlin.kotlin.text.endsWith_7epoxm$;
  var takeLast = Kotlin.kotlin.text.takeLast_6ic1pp$;
  var Regex_init = Kotlin.kotlin.text.Regex_init_61zpoe$;
  var toDouble = Kotlin.kotlin.text.toDouble_pdl1vz$;
  var defineInlineFunction = Kotlin.defineInlineFunction;
  var wrapFunction = Kotlin.wrapFunction;
  var AssertionError = Kotlin.kotlin.AssertionError;
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  var StringBuilder_init = Kotlin.kotlin.text.StringBuilder_init_za3lpa$;
  var ensureNotNull = Kotlin.ensureNotNull;
  var lazy = Kotlin.kotlin.lazy_klfg04$;
  var round = Kotlin.kotlin.math.round_14dthe$;
  TestResult$OK.prototype = Object.create(TestResult.prototype);
  TestResult$OK.prototype.constructor = TestResult$OK;
  TestResult$KO.prototype = Object.create(TestResult.prototype);
  TestResult$KO.prototype.constructor = TestResult$KO;
  function CollectionMatchers() {
  }
  function CollectionMatchers$beEmpty$lambda(value) {
    if (!value.isEmpty())
      throw AssertionError_init('Collection was expected to be empty but has size ' + value.size);
    return Unit;
  }
  CollectionMatchers.prototype.beEmpty = function () {
    return CollectionMatchers$beEmpty$lambda;
  };
  CollectionMatchers.prototype.size_cf6rj3$ = function ($receiver, expected) {
    var size = $receiver.value.size;
    if (size !== expected)
      throw AssertionError_init('Collection was expected to have size ' + expected + ' but had size ' + size);
  };
  CollectionMatchers.prototype.element_4rg9ei$ = function ($receiver, expected) {
    if (!$receiver.value.contains_11rb$(expected))
      throw AssertionError_init('Collection did not have expected element ' + expected);
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
        throw AssertionError_init('Collection did not contain value ' + t);
    }
  };
  CollectionMatchers$containInAnyOrder$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
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
      throw AssertionError_init('Collection did not have size ' + this.closure$size);
  };
  CollectionMatchers$haveSize$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
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
      throw AssertionError_init('Collection did not contain element ' + this.closure$t);
  };
  CollectionMatchers$contain$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [Matcher]
  };
  CollectionMatchers.prototype.contain_mh5how$ = function (t) {
    return new CollectionMatchers$contain$ObjectLiteral(t);
  };
  CollectionMatchers.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'CollectionMatchers',
    interfaces: []
  };
  function DoubleMatchers() {
  }
  DoubleMatchers.prototype.plusOrMinus_38ydlf$ = function ($receiver, tolerance) {
    return new ToleranceMatcher($receiver, tolerance);
  };
  function DoubleMatchers$exactly$ObjectLiteral(closure$d) {
    this.closure$d = closure$d;
  }
  DoubleMatchers$exactly$ObjectLiteral.prototype.test_11rb$ = function (value) {
    if (value !== this.closure$d)
      throw AssertionError_init(value.toString() + ' is not equal to expected value ' + this.closure$d);
  };
  DoubleMatchers$exactly$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [Matcher]
  };
  DoubleMatchers.prototype.exactly_14dthe$ = function (d) {
    return new DoubleMatchers$exactly$ObjectLiteral(d);
  };
  DoubleMatchers.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'DoubleMatchers',
    interfaces: []
  };
  function ToleranceMatcher(expected, tolerance) {
    this.expected = expected;
    this.tolerance = tolerance;
  }
  ToleranceMatcher.prototype.test_11rb$ = function (value) {
    if (this.tolerance === 0.0 && (!isNaN_0(this.expected) || !isNaN_0(value)))
      println('[WARN] When comparing doubles (' + this.expected + ', ' + value + ') consider using tolerance, eg: a shouldBe b plusOrMinus c ');
    if (isNaN_0(value) && !isNaN_0(this.expected))
      throw AssertionError_init(value.toString() + ' is not equal to ' + this.expected);
    var x = value - this.expected;
    var diff = Math_0.abs(x);
    if (diff > this.tolerance)
      throw AssertionError_init(value.toString() + ' is not equal to ' + this.expected);
  };
  ToleranceMatcher.prototype.plusOrMinus_14dthe$ = function (tolerance) {
    return new ToleranceMatcher(this.expected, tolerance);
  };
  ToleranceMatcher.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ToleranceMatcher',
    interfaces: [Matcher]
  };
  function IntMatchers() {
  }
  IntMatchers.prototype.gt_kyeuws$ = function ($receiver, expected) {
    if ($receiver.value <= expected)
      throw AssertionError_init($receiver.value.toString() + ' is not greater than ' + expected);
  };
  IntMatchers.prototype.lt_kyeuws$ = function ($receiver, expected) {
    if ($receiver.value >= expected)
      throw AssertionError_init($receiver.value.toString() + ' is not less than ' + expected);
  };
  IntMatchers.prototype.gte_kyeuws$ = function ($receiver, expected) {
    if ($receiver.value < expected)
      throw AssertionError_init($receiver.value.toString() + ' is not greater than or equal to ' + expected);
  };
  IntMatchers.prototype.lte_kyeuws$ = function ($receiver, expected) {
    if ($receiver.value > expected)
      throw AssertionError_init($receiver.value.toString() + ' is not less than or equal to ' + expected);
  };
  IntMatchers.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'IntMatchers',
    interfaces: []
  };
  function LongMatchers() {
  }
  LongMatchers.prototype.gt_jgrkbu$ = function ($receiver, expected) {
    if ($receiver.value.compareTo_11rb$(expected) <= 0)
      throw AssertionError_init($receiver.value.toString() + ' is not greater than ' + expected.toString());
  };
  LongMatchers.prototype.lt_jgrkbu$ = function ($receiver, expected) {
    if ($receiver.value.compareTo_11rb$(expected) >= 0)
      throw AssertionError_init($receiver.value.toString() + ' is not less than ' + expected.toString());
  };
  LongMatchers.prototype.gte_jgrkbu$ = function ($receiver, expected) {
    if ($receiver.value.compareTo_11rb$(expected) < 0)
      throw AssertionError_init($receiver.value.toString() + ' is not greater than or equal to ' + expected.toString());
  };
  LongMatchers.prototype.lte_jgrkbu$ = function ($receiver, expected) {
    if ($receiver.value.compareTo_11rb$(expected) > 0)
      throw AssertionError_init($receiver.value.toString() + ' is not less than or equal to ' + expected.toString());
  };
  LongMatchers.$metadata$ = {
    kind: Kind_INTERFACE,
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
      throw AssertionError_init('Map did not contain key ' + this.closure$key);
  };
  MapMatchers$haveKey$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
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
      throw AssertionError_init('Map did not contain value ' + this.closure$v);
  };
  MapMatchers$haveValue$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
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
    if (!equals(value.get_11rb$(this.closure$key), this.closure$v))
      throw AssertionError_init('Map did not contain mapping ' + this.closure$key + '=' + value);
  };
  MapMatchers$contain$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [Matcher]
  };
  MapMatchers.prototype.contain_o5fpdy$ = function (key, v) {
    return new MapMatchers$contain$ObjectLiteral(key, v);
  };
  MapMatchers.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'MapMatchers',
    interfaces: []
  };
  function Keyword() {
  }
  Keyword.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Keyword',
    interfaces: []
  };
  function have() {
    have_instance = this;
  }
  have.$metadata$ = {
    kind: Kind_OBJECT,
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
    kind: Kind_OBJECT,
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
    kind: Kind_OBJECT,
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
    kind: Kind_OBJECT,
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
    kind: Kind_OBJECT,
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
    kind: Kind_OBJECT,
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
  var epsilon;
  function Matchers() {
  }
  Matchers.prototype.fail_61zpoe$ = function (msg) {
    throw AssertionError_init(msg);
  };
  Matchers.prototype.shouldBe_38ydlf$ = function ($receiver, other) {
    (new ToleranceMatcher(other, 0.0)).test_11rb$($receiver);
  };
  Matchers.prototype.shouldBeClose_38ydlf$ = function ($receiver, other) {
    (new ToleranceMatcher(other, epsilon)).test_11rb$($receiver);
  };
  Matchers.prototype.shouldBeClose_yni7l$ = function ($receiver, other) {
    (new ToleranceMatcher(other, epsilon)).test_11rb$($receiver);
  };
  Matchers.prototype.shouldBe_8980wn$ = function ($receiver, other) {
    if ($receiver == null) {
      if (other != null)
        throw AssertionError_init(toString($receiver) + ' did not equal ' + toString(other));
      return;
    }
    if (other == null)
      throw AssertionError_init(toString($receiver) + ' did not equal ' + toString(other));
    this.shouldBe_w3ol5z$($receiver.length, other.length);
    var tmp$, tmp$_0;
    var index = 0;
    for (tmp$ = 0; tmp$ !== $receiver.length; ++tmp$) {
      var item = $receiver[tmp$];
      var doubleB = other[tmp$_0 = index, index = tmp$_0 + 1 | 0, tmp$_0];
      this.shouldBe_w3ol5z$(item, doubleB);
    }
  };
  Matchers.prototype.shouldBe_48kdv3$ = function ($receiver, other) {
    this.shouldBeClose_48kdv3$($receiver, other);
  };
  Matchers.prototype.shouldBeClose_48kdv3$ = function ($receiver, other) {
    this.shouldBe_w3ol5z$($receiver.length, other.length);
    var tmp$, tmp$_0;
    var index = 0;
    for (tmp$ = 0; tmp$ !== $receiver.length; ++tmp$) {
      var item = $receiver[tmp$];
      var doubleB = other[tmp$_0 = index, index = tmp$_0 + 1 | 0, tmp$_0];
      this.shouldBeClose_38ydlf$(item, doubleB);
    }
  };
  Matchers.prototype.shouldBe_pmvpm9$ = function ($receiver, other) {
    this.shouldBe_w3ol5z$($receiver.length, other.length);
    var tmp$, tmp$_0;
    var index = 0;
    for (tmp$ = 0; tmp$ !== $receiver.length; ++tmp$) {
      var item = $receiver[tmp$];
      var doubleB = other[tmp$_0 = index, index = tmp$_0 + 1 | 0, tmp$_0];
      this.shouldBe_38ydlf$(item, doubleB);
    }
  };
  Matchers.prototype.shouldBeClose_pmvpm9$ = function ($receiver, other) {
    this.shouldBe_w3ol5z$($receiver.length, other.length);
    var tmp$, tmp$_0;
    var index = 0;
    for (tmp$ = 0; tmp$ !== $receiver.length; ++tmp$) {
      var item = $receiver[tmp$];
      var doubleB = other[tmp$_0 = index, index = tmp$_0 + 1 | 0, tmp$_0];
      this.shouldBeClose_38ydlf$(item, doubleB);
    }
  };
  Matchers.prototype.shouldBe_hvlbab$ = function ($receiver, other) {
    if (count($receiver) !== count(other))
      throw AssertionError_init($receiver.toString() + " doesn't have the same size as " + other);
    var tmp$;
    tmp$ = zip($receiver, other).iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      (new ToleranceMatcher(numberToDouble(element.second), 1.0E-6)).test_11rb$(numberToDouble(element.first));
    }
  };
  Matchers.prototype.shouldBe_w3ol5z$ = function ($receiver, any) {
    this.shouldEqual_3ta935$($receiver, any);
  };
  Matchers.prototype.shouldEqual_3ta935$ = function ($receiver, any) {
    var tmp$;
    if (Kotlin.isType(any, Matcher))
      (Kotlin.isType(tmp$ = any, Matcher) ? tmp$ : throwCCE()).test_11rb$($receiver);
    else {
      if ($receiver == null && any != null)
        throw AssertionError_init($receiver.toString() + ' did not equal ' + toString(any));
      if ($receiver != null && any == null)
        throw AssertionError_init($receiver.toString() + ' did not equal ' + toString(any));
      if (!equals($receiver, any))
        throw AssertionError_init($receiver.toString() + ' did not equal ' + toString(any));
    }
  };
  Matchers.prototype.should_9bxh2u$ = function ($receiver, matcher) {
    matcher($receiver);
  };
  Matchers.prototype.should_erswfw$ = function ($receiver, matcher) {
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
    kind: Kind_INTERFACE,
    simpleName: 'Matchers',
    interfaces: [TypeMatchers, MapMatchers, LongMatchers, IntMatchers, DoubleMatchers, CollectionMatchers, StringMatchers]
  };
  function Matcher() {
  }
  Matcher.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Matcher',
    interfaces: []
  };
  function HaveWrapper(value) {
    this.value = value;
  }
  HaveWrapper.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'HaveWrapper',
    interfaces: []
  };
  function BeWrapper(value) {
    this.value = value;
  }
  BeWrapper.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'BeWrapper',
    interfaces: []
  };
  function StartWrapper(value) {
    this.value = value;
  }
  StartWrapper.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'StartWrapper',
    interfaces: []
  };
  function EndWrapper(value) {
    this.value = value;
  }
  EndWrapper.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'EndWrapper',
    interfaces: []
  };
  function IncludeWrapper(value) {
    this.value = value;
  }
  IncludeWrapper.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'IncludeWrapper',
    interfaces: []
  };
  function ContainWrapper(value) {
    this.value = value;
  }
  ContainWrapper.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ContainWrapper',
    interfaces: []
  };
  function StringMatchers() {
  }
  StringMatchers.prototype.substring_5xtn01$ = function ($receiver, substr) {
    if (!contains($receiver.value, substr))
      throw AssertionError_init('String does not have substring ' + substr);
  };
  StringMatchers.prototype.with_ntz9nd$ = function ($receiver, prefix) {
    if (!startsWith($receiver.value, prefix))
      throw AssertionError_init('String does not start with ' + prefix + ' but with ' + take($receiver.value, prefix.length));
  };
  StringMatchers.prototype.with_ot4y9e$ = function ($receiver, suffix) {
    if (!endsWith($receiver.value, suffix))
      throw AssertionError_init('String does not end with ' + suffix + ' but with ' + takeLast($receiver.value, suffix.length));
  };
  function StringMatchers$startWith$ObjectLiteral(closure$prefix) {
    this.closure$prefix = closure$prefix;
  }
  StringMatchers$startWith$ObjectLiteral.prototype.test_11rb$ = function (value) {
    if (!startsWith(value, this.closure$prefix))
      throw AssertionError_init('String ' + value + ' does not start with ' + this.closure$prefix);
  };
  StringMatchers$startWith$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [Matcher]
  };
  StringMatchers.prototype.startWith_61zpoe$ = function (prefix) {
    return new StringMatchers$startWith$ObjectLiteral(prefix);
  };
  function StringMatchers$endWith$ObjectLiteral(closure$suffix) {
    this.closure$suffix = closure$suffix;
  }
  StringMatchers$endWith$ObjectLiteral.prototype.test_11rb$ = function (value) {
    if (!endsWith(value, this.closure$suffix))
      throw AssertionError_init('String ' + value + ' does not end with with ' + this.closure$suffix);
  };
  StringMatchers$endWith$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [Matcher]
  };
  StringMatchers.prototype.endWith_61zpoe$ = function (suffix) {
    return new StringMatchers$endWith$ObjectLiteral(suffix);
  };
  function StringMatchers$match$ObjectLiteral(closure$regex) {
    this.closure$regex = closure$regex;
  }
  StringMatchers$match$ObjectLiteral.prototype.test_11rb$ = function (value) {
    if (!Regex_init(this.closure$regex).matches_6bul2c$(value))
      throw AssertionError_init('String ' + value + ' does not match regex ' + this.closure$regex);
  };
  StringMatchers$match$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [Matcher]
  };
  StringMatchers.prototype.match_61zpoe$ = function (regex) {
    return new StringMatchers$match$ObjectLiteral(regex);
  };
  function StringMatchers$haveLength$ObjectLiteral(closure$length) {
    this.closure$length = closure$length;
  }
  StringMatchers$haveLength$ObjectLiteral.prototype.test_11rb$ = function (value) {
    if (value.length !== this.closure$length)
      throw AssertionError_init('String ' + value + ' does not have length ' + this.closure$length);
  };
  StringMatchers$haveLength$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [Matcher]
  };
  StringMatchers.prototype.haveLength_za3lpa$ = function (length) {
    return new StringMatchers$haveLength$ObjectLiteral(length);
  };
  StringMatchers.$metadata$ = {
    kind: Kind_INTERFACE,
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
      throw AssertionError_init(toString($receiver.value) + ' is not of type ' + expected);
  };
  TypeMatchers.prototype.theSameInstanceAs_17ng80$ = function ($receiver, ref) {
    if ($receiver.value !== ref)
      throw AssertionError_init($receiver.value.toString() + ' is not the same reference as ' + ref);
  };
  function TypeMatchers$beTheSameInstanceAs$ObjectLiteral(closure$ref) {
    this.closure$ref = closure$ref;
  }
  TypeMatchers$beTheSameInstanceAs$ObjectLiteral.prototype.test_11rb$ = function (value) {
    if (value !== this.closure$ref)
      throw AssertionError_init(value.toString() + ' is not the same reference as ' + this.closure$ref);
  };
  TypeMatchers$beTheSameInstanceAs$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [Matcher]
  };
  TypeMatchers.prototype.beTheSameInstanceAs_mh5how$ = function (ref) {
    return new TypeMatchers$beTheSameInstanceAs$ObjectLiteral(ref);
  };
  TypeMatchers.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'TypeMatchers',
    interfaces: []
  };
  function namespace() {
    namespace$Companion_getInstance();
  }
  function namespace$Companion() {
    namespace$Companion_instance = this;
    this.svg = 'http://www.w3.org/2000/svg';
  }
  namespace$Companion.$metadata$ = {
    kind: Kind_OBJECT,
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
    kind: Kind_CLASS,
    simpleName: 'namespace',
    interfaces: []
  };
  function TestBase() {
    this.regex = Regex_init('[-+]?(?:\\d+\\.\\d+|\\d+\\.|\\.\\d+|\\d+)(?:[eE][-]?\\d+)?');
    this.tests = ArrayList_init();
  }
  TestBase.prototype.round_pdl1vz$ = function ($receiver) {
    var regex = this.regex;
    var replace_20wsma$result;
    replace_20wsma$break: do {
      var match = regex.find_905azu$($receiver);
      if (match == null) {
        replace_20wsma$result = $receiver.toString();
        break replace_20wsma$break;
      }
      var lastStart = 0;
      var length = $receiver.length;
      var sb = StringBuilder_init(length);
      do {
        var foundMatch = ensureNotNull(match);
        sb.append_ezbsdh$($receiver, lastStart, foundMatch.range.start);
        sb.append_gw00v9$(toFixed(toDouble(foundMatch.value)));
        lastStart = foundMatch.range.endInclusive + 1 | 0;
        match = foundMatch.next();
      }
       while (lastStart < length && match != null);
      if (lastStart < length) {
        sb.append_ezbsdh$($receiver, lastStart, length);
      }
      replace_20wsma$result = sb.toString();
    }
     while (false);
    return replace_20wsma$result;
  };
  TestBase.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'TestBase',
    interfaces: [Matchers]
  };
  var shouldThrow = defineInlineFunction('d2v-tests-js.io.data2viz.test.shouldThrow_pcz9i6$', wrapFunction(function () {
    var Throwable = Error;
    var getKClass = Kotlin.getKClass;
    var toString = Kotlin.toString;
    var AssertionError_init = Kotlin.kotlin.AssertionError_init_pdl1vj$;
    var equals = Kotlin.equals;
    var throwCCE = Kotlin.throwCCE;
    return function (T_0, isT, thunk) {
      var tmp$, tmp$_0;
      try {
        thunk();
        tmp$ = null;
      }
       catch (e) {
        if (Kotlin.isType(e, Throwable)) {
          tmp$ = e;
        }
         else
          throw e;
      }
      var e_0 = tmp$;
      var exceptionClassName = getKClass(T_0).simpleName;
      if (e_0 == null)
        throw AssertionError_init('Expected exception ' + toString(getKClass(T_0).simpleName) + ' but no exception was thrown');
      else if (!equals(Kotlin.getKClassFromExpression(e_0).simpleName, exceptionClassName))
        throw AssertionError_init('Expected exception ' + toString(getKClass(T_0).simpleName) + ' but ' + toString(Kotlin.getKClassFromExpression(e_0).simpleName) + ' was thrown');
      else
        return isT(tmp$_0 = e_0) ? tmp$_0 : throwCCE();
    };
  }));
  function ExecutionContext() {
  }
  ExecutionContext.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'ExecutionContext',
    interfaces: []
  };
  function TestCase(name, test) {
    this.name = name;
    this.test = test;
  }
  TestCase.prototype.execute_rkl5nw$ = function (executionContext) {
    try {
      this.test(executionContext);
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
    kind: Kind_CLASS,
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
    kind: Kind_CLASS,
    simpleName: 'OK',
    interfaces: [TestResult]
  };
  function TestResult$KO(name, message) {
    TestResult.call(this, name);
    this.message = message;
  }
  TestResult$KO.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'KO',
    interfaces: [TestResult]
  };
  TestResult.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'TestResult',
    interfaces: []
  };
  function DomUtils() {
    DomUtils$Companion_getInstance();
  }
  function DomUtils$Companion() {
    DomUtils$Companion_instance = this;
    this.body_8kuis4$_0 = lazy(DomUtils$Companion$body$lambda);
  }
  Object.defineProperty(DomUtils$Companion.prototype, 'body', {
    get: function () {
      return this.body_8kuis4$_0.value;
    }
  });
  function DomUtils$Companion$body$lambda() {
    return ensureNotNull(document.querySelector('body'));
  }
  DomUtils$Companion.$metadata$ = {
    kind: Kind_OBJECT,
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
    kind: Kind_CLASS,
    simpleName: 'DomUtils',
    interfaces: []
  };
  function toFixed($receiver) {
    var x = $receiver - round($receiver);
    return Math_0.abs(x) < 1.0E-6 ? round($receiver).toString() : $receiver.toFixed(6);
  }
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$test = package$data2viz.test || (package$data2viz.test = {});
  var package$matchers = package$test.matchers || (package$test.matchers = {});
  package$matchers.CollectionMatchers = CollectionMatchers;
  package$matchers.DoubleMatchers = DoubleMatchers;
  package$matchers.ToleranceMatcher = ToleranceMatcher;
  package$matchers.IntMatchers = IntMatchers;
  package$matchers.LongMatchers = LongMatchers;
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
  Object.defineProperty(package$matchers, 'epsilon', {
    get: function () {
      return epsilon;
    }
  });
  package$matchers.Matchers = Matchers;
  package$matchers.Matcher = Matcher;
  package$matchers.HaveWrapper = HaveWrapper;
  package$matchers.BeWrapper = BeWrapper;
  package$matchers.StartWrapper = StartWrapper;
  package$matchers.EndWrapper = EndWrapper;
  package$matchers.IncludeWrapper = IncludeWrapper;
  package$matchers.ContainWrapper = ContainWrapper;
  package$matchers.StringMatchers = StringMatchers;
  package$matchers.TypeMatchers = TypeMatchers;
  Object.defineProperty(namespace, 'Companion', {
    get: namespace$Companion_getInstance
  });
  package$test.namespace = namespace;
  package$test.TestBase = TestBase;
  $$importsForInline$$['d2v-tests-js'] = _;
  package$test.ExecutionContext = ExecutionContext;
  package$test.TestCase = TestCase;
  TestResult.OK = TestResult$OK;
  TestResult.KO = TestResult$KO;
  package$test.TestResult = TestResult;
  Object.defineProperty(DomUtils, 'Companion', {
    get: DomUtils$Companion_getInstance
  });
  package$test.DomUtils = DomUtils;
  package$test.toFixed_yrwdxr$ = toFixed;
  Matchers.prototype.substring_5xtn01$ = StringMatchers.prototype.substring_5xtn01$;
  Matchers.prototype.with_ntz9nd$ = StringMatchers.prototype.with_ntz9nd$;
  Matchers.prototype.with_ot4y9e$ = StringMatchers.prototype.with_ot4y9e$;
  Matchers.prototype.startWith_61zpoe$ = StringMatchers.prototype.startWith_61zpoe$;
  Matchers.prototype.endWith_61zpoe$ = StringMatchers.prototype.endWith_61zpoe$;
  Matchers.prototype.match_61zpoe$ = StringMatchers.prototype.match_61zpoe$;
  Matchers.prototype.haveLength_za3lpa$ = StringMatchers.prototype.haveLength_za3lpa$;
  Matchers.prototype.beEmpty = CollectionMatchers.prototype.beEmpty;
  Matchers.prototype.size_cf6rj3$ = CollectionMatchers.prototype.size_cf6rj3$;
  Matchers.prototype.element_4rg9ei$ = CollectionMatchers.prototype.element_4rg9ei$;
  Matchers.prototype.containInAnyOrder_i5x0yv$ = CollectionMatchers.prototype.containInAnyOrder_i5x0yv$;
  Matchers.prototype.haveSize_ww73n8$ = CollectionMatchers.prototype.haveSize_ww73n8$;
  Matchers.prototype.contain_mh5how$ = CollectionMatchers.prototype.contain_mh5how$;
  Matchers.prototype.contain_o5fpdy$ = MapMatchers.prototype.contain_o5fpdy$;
  Matchers.prototype.plusOrMinus_38ydlf$ = DoubleMatchers.prototype.plusOrMinus_38ydlf$;
  Matchers.prototype.exactly_14dthe$ = DoubleMatchers.prototype.exactly_14dthe$;
  Matchers.prototype.gt_kyeuws$ = IntMatchers.prototype.gt_kyeuws$;
  Matchers.prototype.gt_jgrkbu$ = LongMatchers.prototype.gt_jgrkbu$;
  Matchers.prototype.lt_kyeuws$ = IntMatchers.prototype.lt_kyeuws$;
  Matchers.prototype.lt_jgrkbu$ = LongMatchers.prototype.lt_jgrkbu$;
  Matchers.prototype.gte_kyeuws$ = IntMatchers.prototype.gte_kyeuws$;
  Matchers.prototype.gte_jgrkbu$ = LongMatchers.prototype.gte_jgrkbu$;
  Matchers.prototype.lte_kyeuws$ = IntMatchers.prototype.lte_kyeuws$;
  Matchers.prototype.lte_jgrkbu$ = LongMatchers.prototype.lte_jgrkbu$;
  Matchers.prototype.haveKey_mh5how$ = MapMatchers.prototype.haveKey_mh5how$;
  Matchers.prototype.haveValue_mh5how$ = MapMatchers.prototype.haveValue_mh5how$;
  Matchers.prototype.a_75u09w$ = TypeMatchers.prototype.a_75u09w$;
  Matchers.prototype.an_75u09w$ = TypeMatchers.prototype.an_75u09w$;
  Matchers.prototype.theSameInstanceAs_17ng80$ = TypeMatchers.prototype.theSameInstanceAs_17ng80$;
  Matchers.prototype.beTheSameInstanceAs_mh5how$ = TypeMatchers.prototype.beTheSameInstanceAs_mh5how$;
  TestBase.prototype.fail_61zpoe$ = Matchers.prototype.fail_61zpoe$;
  TestBase.prototype.shouldBe_38ydlf$ = Matchers.prototype.shouldBe_38ydlf$;
  TestBase.prototype.shouldBe_8980wn$ = Matchers.prototype.shouldBe_8980wn$;
  TestBase.prototype.shouldBe_48kdv3$ = Matchers.prototype.shouldBe_48kdv3$;
  TestBase.prototype.shouldBe_pmvpm9$ = Matchers.prototype.shouldBe_pmvpm9$;
  TestBase.prototype.shouldBe_hvlbab$ = Matchers.prototype.shouldBe_hvlbab$;
  TestBase.prototype.shouldBe_w3ol5z$ = Matchers.prototype.shouldBe_w3ol5z$;
  TestBase.prototype.shouldBeClose_38ydlf$ = Matchers.prototype.shouldBeClose_38ydlf$;
  TestBase.prototype.shouldBeClose_yni7l$ = Matchers.prototype.shouldBeClose_yni7l$;
  TestBase.prototype.shouldBeClose_48kdv3$ = Matchers.prototype.shouldBeClose_48kdv3$;
  TestBase.prototype.shouldBeClose_pmvpm9$ = Matchers.prototype.shouldBeClose_pmvpm9$;
  TestBase.prototype.shouldEqual_3ta935$ = Matchers.prototype.shouldEqual_3ta935$;
  TestBase.prototype.should_9bxh2u$ = Matchers.prototype.should_9bxh2u$;
  TestBase.prototype.should_erswfw$ = Matchers.prototype.should_erswfw$;
  TestBase.prototype.should_rt1iod$ = Matchers.prototype.should_rt1iod$;
  TestBase.prototype.should_ly0i9h$ = Matchers.prototype.should_ly0i9h$;
  TestBase.prototype.should_bdao1w$ = Matchers.prototype.should_bdao1w$;
  TestBase.prototype.should_o3dfsi$ = Matchers.prototype.should_o3dfsi$;
  TestBase.prototype.should_n5j1vh$ = Matchers.prototype.should_n5j1vh$;
  TestBase.prototype.should_oa1att$ = Matchers.prototype.should_oa1att$;
  TestBase.prototype.substring_5xtn01$ = Matchers.prototype.substring_5xtn01$;
  TestBase.prototype.with_ntz9nd$ = Matchers.prototype.with_ntz9nd$;
  TestBase.prototype.with_ot4y9e$ = Matchers.prototype.with_ot4y9e$;
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
  TestBase.prototype.contain_o5fpdy$ = Matchers.prototype.contain_o5fpdy$;
  TestBase.prototype.plusOrMinus_38ydlf$ = Matchers.prototype.plusOrMinus_38ydlf$;
  TestBase.prototype.exactly_14dthe$ = Matchers.prototype.exactly_14dthe$;
  TestBase.prototype.gt_kyeuws$ = Matchers.prototype.gt_kyeuws$;
  TestBase.prototype.gt_jgrkbu$ = Matchers.prototype.gt_jgrkbu$;
  TestBase.prototype.lt_kyeuws$ = Matchers.prototype.lt_kyeuws$;
  TestBase.prototype.lt_jgrkbu$ = Matchers.prototype.lt_jgrkbu$;
  TestBase.prototype.gte_kyeuws$ = Matchers.prototype.gte_kyeuws$;
  TestBase.prototype.gte_jgrkbu$ = Matchers.prototype.gte_jgrkbu$;
  TestBase.prototype.lte_kyeuws$ = Matchers.prototype.lte_kyeuws$;
  TestBase.prototype.lte_jgrkbu$ = Matchers.prototype.lte_jgrkbu$;
  TestBase.prototype.haveKey_mh5how$ = Matchers.prototype.haveKey_mh5how$;
  TestBase.prototype.haveValue_mh5how$ = Matchers.prototype.haveValue_mh5how$;
  TestBase.prototype.a_75u09w$ = Matchers.prototype.a_75u09w$;
  TestBase.prototype.an_75u09w$ = Matchers.prototype.an_75u09w$;
  TestBase.prototype.theSameInstanceAs_17ng80$ = Matchers.prototype.theSameInstanceAs_17ng80$;
  TestBase.prototype.beTheSameInstanceAs_mh5how$ = Matchers.prototype.beTheSameInstanceAs_mh5how$;
  epsilon = 1.0E-6;
  Kotlin.defineModule('d2v-tests-js', _);
  return _;
}));

//# sourceMappingURL=d2v-tests-js.js.map
