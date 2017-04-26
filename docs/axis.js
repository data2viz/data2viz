define('axis', ['exports', 'kotlin', 'core', 'tests', 'color'], function (_, Kotlin, $module$core, $module$tests, $module$color) {
  'use strict';
  var listOf = Kotlin.kotlin.collections.listOf_i5x0yv$;
  var max = Kotlin.kotlin.collections.max_exjks8$;
  var namespace = $module$core.io.data2viz.core.namespace;
  var StringSpec = $module$tests.io.data2viz.test.StringSpec;
  var ticks = $module$core.io.data2viz.core.ticks_1stjjm$;
  var color_0 = $module$color.io.data2viz.color;
  AxisTests.prototype = Object.create(StringSpec.prototype);
  AxisTests.prototype.constructor = AxisTests;
  function AxisTests() {
    StringSpec.call(this);
    this.invoke_79xod4$('display x axis', AxisTests_init$lambda(this));
  }
  function AxisTests$display$lambda(closure$barPadding, closure$barWidth) {
    return function (i) {
      return Kotlin.imul(i, closure$barPadding + closure$barWidth | 0);
    };
  }
  function AxisTests$display$lambda_0(closure$max) {
    return function (d) {
      return closure$max - d | 0;
    };
  }
  function AxisTests$display$node(name) {
    return document.createElementNS(namespace.Companion.svg, name);
  }
  AxisTests.prototype.display = function () {
    var tmp$, tmp$_0;
    var data = listOf([55, 44, 30, 23, 17, 14, 16, 25, 41, 61, 85, 101, 95, 105, 114, 150, 180, 210, 125, 100, 71, 75, 72, 67]);
    var max_0 = (tmp$ = max(data)) != null ? tmp$ : Kotlin.throwNPE();
    var barWidth = 15;
    var barPadding = 3;
    var xLoc = AxisTests$display$lambda(barPadding, barWidth);
    var yLoc = AxisTests$display$lambda_0(max_0);
    var node = AxisTests$display$node;
    var body = (tmp$_0 = document.querySelector('body')) != null ? tmp$_0 : Kotlin.throwNPE();
    var $receiver = node('svg');
    $receiver.setAttribute('width', '1000');
    $receiver.setAttribute('height', '250');
    var $receiver_0 = node('g');
    var tmp$_1, tmp$_2;
    var index = 0;
    tmp$_1 = data.iterator();
    while (tmp$_1.hasNext()) {
      var item = tmp$_1.next();
      var index_0 = (tmp$_2 = index, index = tmp$_2 + 1 | 0, tmp$_2);
      var $receiver_1 = node('rect');
      $receiver_1.setAttribute('fill', 'steelblue');
      $receiver_1.setAttribute('width', '15');
      $receiver_1.setAttribute('height', item.toString());
      $receiver_1.setAttribute('transform', 'translate(' + xLoc(index_0) + ', ' + yLoc(item) + ')');
      $receiver_0.appendChild($receiver_1);
    }
    $receiver.appendChild($receiver_0);
    body.appendChild($receiver);
  };
  function AxisTests_init$lambda(this$AxisTests) {
    return function () {
      this$AxisTests.display();
    };
  }
  AxisTests.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'AxisTests',
    interfaces: [StringSpec]
  };
  function Axis(min, max_0, scale) {
    this.min = min;
    this.max = max_0;
    this.scale = scale;
  }
  function Axis$appendTo$lambda$lambda$lambda(this$Axis, closure$tick) {
    return function ($receiver) {
      $receiver.translate_z8e4lc$(void 0, this$Axis.scale(closure$tick));
    };
  }
  function Axis$appendTo$lambda$lambda$lambda$lambda($receiver) {
    $receiver.setStyle_puj7f4$('text-anchor', 'end');
    $receiver.fontFamily_61zpoe$('sans-serif');
    $receiver.fontSize_61zpoe$('12px');
  }
  function Axis$appendTo$lambda$lambda$lambda_0(closure$tick) {
    return function ($receiver) {
      $receiver.style_dv3ovl$(Axis$appendTo$lambda$lambda$lambda$lambda);
      $receiver.x = -9;
      $receiver.y = 0;
      $receiver.setAttribute_jyasbz$('dy', '.32em');
      $receiver.text = closure$tick.toString();
    };
  }
  function Axis$appendTo$lambda$lambda(this$Axis, closure$tick) {
    return function ($receiver) {
      $receiver.transform_1ogs0a$(Axis$appendTo$lambda$lambda$lambda(this$Axis, closure$tick));
      $receiver.line_m8p7mh$(void 0, void 0, -6);
      $receiver.text_b0yamr$(Axis$appendTo$lambda$lambda$lambda_0(closure$tick));
    };
  }
  function Axis$appendTo$lambda$lambda_0(this$Axis) {
    return function ($receiver) {
      $receiver.moveTo_z8e4lc$(-6, this$Axis.scale(this$Axis.max));
      $receiver.horizontalDeltaTo_3p81yu$(0);
      $receiver.verticalLineDeltaTo_3p81yu$(this$Axis.scale(this$Axis.min));
      $receiver.horizontalDeltaTo_3p81yu$(-6);
    };
  }
  function Axis$appendTo$lambda(this$Axis) {
    return function ($receiver) {
      $receiver.strokeWidth = '1';
      $receiver.stroke = color_0.colors.black;
      $receiver.path_ple93z$(Axis$appendTo$lambda$lambda_0(this$Axis));
      $receiver.fill;
      $receiver.setAttribute_jyasbz$('fill', 'none');
    };
  }
  Axis.prototype.appendTo_3pjutd$ = function (parent) {
    var ticks_0 = ticks(this.min, this.max, 10);
    var tmp$;
    tmp$ = ticks_0.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      parent.g_pjdmh5$(Axis$appendTo$lambda$lambda(this, element));
    }
    parent.path_qraab9$(Axis$appendTo$lambda(this));
  };
  Axis.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'Axis',
    interfaces: []
  };
  _.AxisTests = AxisTests;
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$axis = package$data2viz.axis || (package$data2viz.axis = {});
  package$axis.Axis = Axis;
  Kotlin.defineModule('axis', _);
  return _;
});

//@ sourceMappingURL=axis.js.map
