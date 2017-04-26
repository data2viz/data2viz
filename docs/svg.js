define('svg', ['exports', 'kotlin', 'core', 'color'], function (_, Kotlin, $module$core, $module$color) {
  'use strict';
  var namespace = $module$core.io.data2viz.core.namespace;
  var joinToString = Kotlin.kotlin.collections.joinToString_fmv235$;
  var Annotation = Kotlin.kotlin.Annotation;
  var get_color = $module$color.io.data2viz.color.get_color_pdl1vz$;
  var color_0 = $module$color.io.data2viz.color;
  var Point = $module$core.io.data2viz.core.Point;
  function rect(width, height) {
    if (width === void 0)
      width = 10.0;
    if (height === void 0)
      height = 10.0;
    var $receiver = new RectElement(createSVGElement('rect'));
    var closure$width = width;
    $receiver.width = closure$width;
    $receiver.height = closure$width;
    return $receiver;
  }
  function circle() {
    return new CircleElement(createSVGElement('circle'));
  }
  function g() {
    return new GroupElement(createSVGElement('g'));
  }
  function path() {
    return new PathElement(createSVGElement('path'));
  }
  function text_0() {
    return new TextElement(createSVGElement('text'));
  }
  function createSVGElement(name) {
    return document.createElementNS(namespace.Companion.svg, name);
  }
  function Path() {
    this.commands_0 = Kotlin.kotlin.collections.ArrayList_init_ww73n8$();
  }
  Path.prototype.moveTo_z8e4lc$ = function (x, y) {
    if (x === void 0)
      x = 0;
    if (y === void 0)
      y = 0;
    this.commands_0.add_11rb$('M ' + x + ' ' + y);
  };
  Path.prototype.moveDeltaTo_z8e4lc$ = function (dx, dy) {
    if (dx === void 0)
      dx = 0;
    if (dy === void 0)
      dy = 0;
    this.commands_0.add_11rb$('m ' + dx + ' ' + dy);
  };
  Path.prototype.lineTo_z8e4lc$ = function (x, y) {
    if (x === void 0)
      x = 0;
    if (y === void 0)
      y = 0;
    this.commands_0.add_11rb$('L ' + x + ' ' + y);
  };
  Path.prototype.lineDeltaTo_z8e4lc$ = function (dx, dy) {
    if (dx === void 0)
      dx = 0;
    if (dy === void 0)
      dy = 0;
    this.commands_0.add_11rb$('l ' + dx + ' ' + dy);
  };
  Path.prototype.horizontalTo_3p81yu$ = function (x) {
    if (x === void 0)
      x = 0;
    this.commands_0.add_11rb$('H ' + x);
  };
  Path.prototype.horizontalDeltaTo_3p81yu$ = function (dx) {
    if (dx === void 0)
      dx = 0;
    this.commands_0.add_11rb$('H ' + dx);
  };
  Path.prototype.verticalLineTo_3p81yu$ = function (y) {
    if (y === void 0)
      y = 0;
    this.commands_0.add_11rb$('V ' + y);
  };
  Path.prototype.verticalLineDeltaTo_3p81yu$ = function (dy) {
    if (dy === void 0)
      dy = 0;
    this.commands_0.add_11rb$('v ' + dy);
  };
  Path.prototype.closePath = function () {
    this.commands_0.add_11rb$('Z');
  };
  Path.prototype.toCommand = function () {
    return joinToString(this.commands_0, ' ');
  };
  Path.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'Path',
    interfaces: []
  };
  function Transform() {
    this.commands_0 = Kotlin.kotlin.collections.LinkedHashMap_init_q3lmfv$();
  }
  Transform.prototype.translate_z8e4lc$ = function (x, y) {
    if (x === void 0)
      x = 0;
    if (y === void 0)
      y = 0;
    this.commands_0.put_xwzc9p$('translate', 'translate(' + x + ', ' + y + ')');
  };
  Transform.prototype.scale_z8e4lc$ = function (x, y) {
    if (x === void 0)
      x = 1;
    if (y === void 0)
      y = x;
    this.commands_0.put_xwzc9p$('scale', 'scale(' + x + ', ' + y + ')');
  };
  Transform.prototype.skewX_3p81yu$ = function (a) {
    this.commands_0.put_xwzc9p$('skewX', 'skewX(' + a + ')');
  };
  Transform.prototype.skewY_3p81yu$ = function (a) {
    this.commands_0.put_xwzc9p$('skewY', 'skewX(' + a + ')');
  };
  Transform.prototype.rotate_uq0a60$ = function (angle, x, y) {
    if (x === void 0)
      x = 0;
    if (y === void 0)
      y = 0;
    this.commands_0.put_xwzc9p$('rotate', 'rotate(' + angle.deg + ', ' + x + ', ' + y);
  };
  Transform.prototype.toCommand = function () {
    return joinToString(this.commands_0.values, ' ');
  };
  Transform.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'Transform',
    interfaces: []
  };
  function Style() {
    this.styles_0 = Kotlin.kotlin.collections.LinkedHashMap_init_q3lmfv$();
  }
  Style.prototype.setStyle_puj7f4$ = function (property, value) {
    return this.styles_0.put_xwzc9p$(property, property + ': ' + value);
  };
  Style.prototype.fontFamily_61zpoe$ = function (name) {
    this.setStyle_puj7f4$('font-family', name);
  };
  Style.prototype.fontSize_61zpoe$ = function (size) {
    this.setStyle_puj7f4$('font-size', size);
  };
  Style.prototype.toAttribute = function () {
    return joinToString(this.styles_0.values, '; ');
  };
  Style.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'Style',
    interfaces: []
  };
  function SvgTagMarker() {
  }
  SvgTagMarker.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'SvgTagMarker',
    interfaces: [Annotation]
  };
  function AccessByAttributes() {
  }
  AccessByAttributes.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'AccessByAttributes',
    interfaces: []
  };
  function ElementWrapper() {
  }
  ElementWrapper.prototype.setAttribute_jyasbz$ = function (name, value) {
    if (value != null)
      this.element.setAttribute(name, value);
    else
      this.element.removeAttribute(name);
  };
  ElementWrapper.prototype.getAttribute_61zpoe$ = function (name) {
    return this.element.getAttribute(name);
  };
  ElementWrapper.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'ElementWrapper',
    interfaces: [AccessByAttributes]
  };
  function HasFill() {
  }
  Object.defineProperty(HasFill.prototype, 'fill', {
    get: function () {
      var tmp$;
      return (tmp$ = this.getAttribute_61zpoe$('fill')) != null ? get_color(tmp$) : null;
    },
    set: function (value) {
      var tmp$;
      this.setAttribute_jyasbz$('fill', (tmp$ = value != null ? value.toString() : null) != null ? tmp$ : 'none');
    }
  });
  HasFill.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'HasFill',
    interfaces: [AccessByAttributes]
  };
  function HasStroke() {
  }
  Object.defineProperty(HasStroke.prototype, 'stroke', {
    get: function () {
      var tmp$;
      return (tmp$ = this.getAttribute_61zpoe$('stroke')) != null ? get_color(tmp$) : null;
    },
    set: function (value) {
      this.setAttribute_jyasbz$('stroke', value != null ? value.toString() : null);
    }
  });
  Object.defineProperty(HasStroke.prototype, 'strokeWidth', {
    get: function () {
      return this.getAttribute_61zpoe$('stroke-width');
    },
    set: function (value) {
      this.setAttribute_jyasbz$('stroke-width', value);
    }
  });
  Object.defineProperty(HasStroke.prototype, 'strokeLineCap', {
    get: function () {
      return this.getAttribute_61zpoe$('stroke-linecap');
    },
    set: function (value) {
      this.setAttribute_jyasbz$('stroke-linecap', value);
    }
  });
  Object.defineProperty(HasStroke.prototype, 'strokeDasharray', {
    get: function () {
      return this.getAttribute_61zpoe$('stroke-dasharray');
    },
    set: function (value) {
      this.setAttribute_jyasbz$('stroke-dasharray', value);
    }
  });
  HasStroke.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'HasStroke',
    interfaces: [AccessByAttributes]
  };
  function svg$lambda($receiver) {
  }
  function svg(init) {
    if (init === void 0)
      init = svg$lambda;
    var tmp$;
    var svgElement = document.querySelector('svg');
    if (svgElement == null) {
      svgElement = createSVGElement('svg');
      ((tmp$ = document.querySelector('body')) != null ? tmp$ : Kotlin.throwNPE()).append(svgElement);
    }
    var $receiver = new SVGElement(svgElement);
    init($receiver);
    return $receiver;
  }
  function CircleElement(element) {
    this.element_b45poh$_0 = element;
  }
  Object.defineProperty(CircleElement.prototype, 'element', {
    get: function () {
      return this.element_b45poh$_0;
    }
  });
  function CircleElement$on$lambda(closure$block, this$CircleElement) {
    return function (event) {
      closure$block(this$CircleElement, event);
    };
  }
  CircleElement.prototype.on_ti1ww2$ = function (eventName, block) {
    this.element.addEventListener(eventName, CircleElement$on$lambda(block, this));
  };
  CircleElement.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'CircleElement',
    interfaces: [ParentElement, HasRadius, HasCenter, HasFill, HasStroke, ElementWrapper]
  };
  function RectElement(element) {
    this.element_fglxbh$_0 = element;
  }
  Object.defineProperty(RectElement.prototype, 'element', {
    get: function () {
      return this.element_fglxbh$_0;
    }
  });
  function RectElement$on$lambda(closure$block, this$RectElement) {
    return function (event) {
      closure$block(this$RectElement, event);
    };
  }
  RectElement.prototype.on_fumve2$ = function (eventName, block) {
    this.element.addEventListener(eventName, RectElement$on$lambda(block, this));
  };
  RectElement.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'RectElement',
    interfaces: [ParentElement, Has2D, HasFill, HasStroke, ElementWrapper]
  };
  function GroupElement(element) {
    this.element_e37ys4$_0 = element;
  }
  Object.defineProperty(GroupElement.prototype, 'element', {
    get: function () {
      return this.element_e37ys4$_0;
    }
  });
  GroupElement.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'GroupElement',
    interfaces: [Transformable, ParentElement, HasPosition, Has2D, HasFill, HasStroke, ElementWrapper]
  };
  function PathElement(element) {
    this.element_nl76p8$_0 = element;
  }
  Object.defineProperty(PathElement.prototype, 'element', {
    get: function () {
      return this.element_nl76p8$_0;
    }
  });
  PathElement.prototype.path_ple93z$ = function (init) {
    var path_0 = new Path();
    init(path_0);
    this.setAttribute_jyasbz$('d', path_0.toCommand());
  };
  PathElement.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'PathElement',
    interfaces: [HasFill, HasStroke, ElementWrapper]
  };
  function TextElement(element) {
    this.element_8ers84$_0 = element;
  }
  Object.defineProperty(TextElement.prototype, 'element', {
    get: function () {
      return this.element_8ers84$_0;
    }
  });
  Object.defineProperty(TextElement.prototype, 'text', {
    get: function () {
      return this.element.textContent;
    },
    set: function (value) {
      this.element.textContent = value;
    }
  });
  TextElement.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'TextElement',
    interfaces: [HasStyle, Transformable, HasFill, ElementWrapper, HasPosition, HasText]
  };
  function SVGElement(element) {
    this.element_jnnljl$_0 = element;
  }
  Object.defineProperty(SVGElement.prototype, 'element', {
    get: function () {
      return this.element_jnnljl$_0;
    },
    set: function (element) {
      this.element_jnnljl$_0 = element;
    }
  });
  SVGElement.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'SVGElement',
    interfaces: [ParentElement, ElementWrapper, Has2D]
  };
  function ParentElement() {
  }
  ParentElement.prototype.circle_dca3qi$ = function (init) {
    var circle_0 = circle();
    init(circle_0);
    this.element.append(circle_0.element);
  };
  ParentElement.prototype.rect_jh2hm2$ = function (init) {
    var rect_0 = rect();
    init(rect_0);
    this.element.append(rect_0.element);
  };
  ParentElement.prototype.line_m8p7mh$$default = function (x1, y1, x2, y2, stroke) {
    var tmp$ = this.element;
    var $receiver = createSVGElement('line');
    $receiver.setAttribute('x1', x1.toString());
    $receiver.setAttribute('y1', y1.toString());
    $receiver.setAttribute('x2', x2.toString());
    $receiver.setAttribute('y2', y2.toString());
    $receiver.setAttribute('stroke', stroke.toString());
    tmp$.append($receiver);
  };
  ParentElement.prototype.line_m8p7mh$ = function (x1, y1, x2, y2, stroke, callback$default) {
    if (x1 === void 0)
      x1 = 0;
    if (y1 === void 0)
      y1 = 0;
    if (x2 === void 0)
      x2 = 0;
    if (y2 === void 0)
      y2 = 0;
    if (stroke === void 0)
      stroke = color_0.colors.black;
    callback$default ? callback$default(x1, y1, x2, y2, stroke) : this.line_m8p7mh$$default(x1, y1, x2, y2, stroke);
  };
  ParentElement.prototype.g_pjdmh5$ = function (init) {
    var g_0 = g();
    init(g_0);
    this.element.append(g_0.element);
  };
  ParentElement.prototype.text_b0yamr$ = function (init) {
    var t = text_0();
    init(t);
    this.element.append(t.element);
  };
  ParentElement.prototype.path_qraab9$ = function (init) {
    var p = path();
    init(p);
    this.element.append(p.element);
  };
  ParentElement.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'ParentElement',
    interfaces: [ElementWrapper]
  };
  function Margins(top, right, bottom, left) {
    if (top === void 0)
      top = 0;
    if (right === void 0)
      right = top;
    if (bottom === void 0)
      bottom = top;
    if (left === void 0)
      left = right;
    this.top = top;
    this.right = right;
    this.bottom = bottom;
    this.left = left;
  }
  Object.defineProperty(Margins.prototype, 'horizontalMargins', {
    get: function () {
      return this.right + this.left | 0;
    }
  });
  Object.defineProperty(Margins.prototype, 'verticalMargins', {
    get: function () {
      return this.top + this.bottom | 0;
    }
  });
  Margins.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'Margins',
    interfaces: []
  };
  Margins.prototype.component1 = function () {
    return this.top;
  };
  Margins.prototype.component2 = function () {
    return this.right;
  };
  Margins.prototype.component3 = function () {
    return this.bottom;
  };
  Margins.prototype.component4 = function () {
    return this.left;
  };
  Margins.prototype.copy_tjonv8$ = function (top, right, bottom, left) {
    return new Margins(top === void 0 ? this.top : top, right === void 0 ? this.right : right, bottom === void 0 ? this.bottom : bottom, left === void 0 ? this.left : left);
  };
  Margins.prototype.toString = function () {
    return 'Margins(top=' + Kotlin.toString(this.top) + (', right=' + Kotlin.toString(this.right)) + (', bottom=' + Kotlin.toString(this.bottom)) + (', left=' + Kotlin.toString(this.left)) + ')';
  };
  Margins.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.top) | 0;
    result = result * 31 + Kotlin.hashCode(this.right) | 0;
    result = result * 31 + Kotlin.hashCode(this.bottom) | 0;
    result = result * 31 + Kotlin.hashCode(this.left) | 0;
    return result;
  };
  Margins.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.top, other.top) && Kotlin.equals(this.right, other.right) && Kotlin.equals(this.bottom, other.bottom) && Kotlin.equals(this.left, other.left)))));
  };
  function HasText() {
  }
  HasText.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'HasText',
    interfaces: []
  };
  function Transformable() {
  }
  Transformable.prototype.transform_1ogs0a$ = function (init) {
    var tmp$ = this.setAttribute_jyasbz$;
    var $receiver = new Transform();
    init($receiver);
    tmp$.call(this, 'transform', $receiver.toCommand());
  };
  Transformable.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'Transformable',
    interfaces: [AccessByAttributes]
  };
  function HasStyle() {
  }
  HasStyle.prototype.style_dv3ovl$ = function (init) {
    var tmp$ = this.setAttribute_jyasbz$;
    var $receiver = new Style();
    init($receiver);
    tmp$.call(this, 'style', $receiver.toAttribute());
  };
  HasStyle.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'HasStyle',
    interfaces: [AccessByAttributes]
  };
  function HasPosition() {
  }
  Object.defineProperty(HasPosition.prototype, 'x', {
    get: function () {
      var tmp$, tmp$_0;
      return (tmp$_0 = (tmp$ = this.getAttribute_61zpoe$('x')) != null ? Kotlin.kotlin.text.toDouble_pdl1vz$(tmp$) : null) != null ? tmp$_0 : 0.0;
    },
    set: function (value) {
      this.setAttribute_jyasbz$('x', value.toString());
    }
  });
  Object.defineProperty(HasPosition.prototype, 'y', {
    get: function () {
      var tmp$, tmp$_0;
      return (tmp$_0 = (tmp$ = this.getAttribute_61zpoe$('y')) != null ? Kotlin.kotlin.text.toDouble_pdl1vz$(tmp$) : null) != null ? tmp$_0 : 0.0;
    },
    set: function (value) {
      this.setAttribute_jyasbz$('y', value.toString());
    }
  });
  HasPosition.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'HasPosition',
    interfaces: [AccessByAttributes]
  };
  function HasCenter() {
  }
  Object.defineProperty(HasCenter.prototype, 'cx', {
    get: function () {
      var tmp$, tmp$_0;
      return (tmp$_0 = (tmp$ = this.getAttribute_61zpoe$('cx')) != null ? Kotlin.kotlin.text.toDouble_pdl1vz$(tmp$) : null) != null ? tmp$_0 : 0.0;
    },
    set: function (value) {
      this.setAttribute_jyasbz$('cx', value.toString());
    }
  });
  Object.defineProperty(HasCenter.prototype, 'cy', {
    get: function () {
      var tmp$, tmp$_0;
      return (tmp$_0 = (tmp$ = this.getAttribute_61zpoe$('cy')) != null ? Kotlin.kotlin.text.toDouble_pdl1vz$(tmp$) : null) != null ? tmp$_0 : 0.0;
    },
    set: function (value) {
      this.setAttribute_jyasbz$('cy', value.toString());
    }
  });
  HasCenter.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'HasCenter',
    interfaces: [AccessByAttributes]
  };
  function HasRadius() {
  }
  Object.defineProperty(HasRadius.prototype, 'r', {
    get: function () {
      var tmp$, tmp$_0;
      return (tmp$_0 = (tmp$ = this.getAttribute_61zpoe$('r')) != null ? Kotlin.kotlin.text.toDouble_pdl1vz$(tmp$) : null) != null ? tmp$_0 : 0.0;
    },
    set: function (value) {
      this.setAttribute_jyasbz$('r', value.toString());
    }
  });
  HasRadius.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'HasRadius',
    interfaces: [AccessByAttributes]
  };
  function HasWidth() {
  }
  Object.defineProperty(HasWidth.prototype, 'width', {
    get: function () {
      var tmp$, tmp$_0;
      return (tmp$_0 = (tmp$ = this.getAttribute_61zpoe$('width')) != null ? Kotlin.kotlin.text.toDouble_pdl1vz$(tmp$) : null) != null ? tmp$_0 : 0.0;
    },
    set: function (value) {
      this.setAttribute_jyasbz$('width', value.toString());
    }
  });
  HasWidth.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'HasWidth',
    interfaces: [AccessByAttributes]
  };
  function HasHeight() {
  }
  Object.defineProperty(HasHeight.prototype, 'height', {
    get: function () {
      var tmp$, tmp$_0;
      return (tmp$_0 = (tmp$ = this.getAttribute_61zpoe$('height')) != null ? Kotlin.kotlin.text.toDouble_pdl1vz$(tmp$) : null) != null ? tmp$_0 : 0.0;
    },
    set: function (value) {
      this.setAttribute_jyasbz$('height', value.toString());
    }
  });
  HasHeight.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'HasHeight',
    interfaces: [AccessByAttributes]
  };
  function Has2D() {
  }
  Object.defineProperty(Has2D.prototype, 'center', {
    get: function () {
      return new Point(Kotlin.numberToDouble(this.width) / 2, Kotlin.numberToDouble(this.height) / 2);
    }
  });
  Object.defineProperty(Has2D.prototype, 'topRight', {
    get: function () {
      return new Point(Kotlin.numberToDouble(this.width), 0.0);
    }
  });
  Has2D.$metadata$ = {
    kind: Kotlin.Kind.INTERFACE,
    simpleName: 'Has2D',
    interfaces: [HasWidth, HasHeight]
  };
  ParentElement.prototype.setAttribute_jyasbz$ = ElementWrapper.prototype.setAttribute_jyasbz$;
  ParentElement.prototype.getAttribute_61zpoe$ = ElementWrapper.prototype.getAttribute_61zpoe$;
  CircleElement.prototype.line_m8p7mh$$default = ParentElement.prototype.line_m8p7mh$;
  CircleElement.prototype.setAttribute_jyasbz$ = ElementWrapper.prototype.setAttribute_jyasbz$;
  CircleElement.prototype.getAttribute_61zpoe$ = ElementWrapper.prototype.getAttribute_61zpoe$;
  Object.defineProperty(CircleElement.prototype, 'stroke', Object.getOwnPropertyDescriptor(HasStroke.prototype, 'stroke'));
  Object.defineProperty(CircleElement.prototype, 'strokeWidth', Object.getOwnPropertyDescriptor(HasStroke.prototype, 'strokeWidth'));
  Object.defineProperty(CircleElement.prototype, 'strokeLineCap', Object.getOwnPropertyDescriptor(HasStroke.prototype, 'strokeLineCap'));
  Object.defineProperty(CircleElement.prototype, 'strokeDasharray', Object.getOwnPropertyDescriptor(HasStroke.prototype, 'strokeDasharray'));
  Object.defineProperty(CircleElement.prototype, 'fill', Object.getOwnPropertyDescriptor(HasFill.prototype, 'fill'));
  Object.defineProperty(CircleElement.prototype, 'cx', Object.getOwnPropertyDescriptor(HasCenter.prototype, 'cx'));
  Object.defineProperty(CircleElement.prototype, 'cy', Object.getOwnPropertyDescriptor(HasCenter.prototype, 'cy'));
  Object.defineProperty(CircleElement.prototype, 'r', Object.getOwnPropertyDescriptor(HasRadius.prototype, 'r'));
  CircleElement.prototype.circle_dca3qi$ = ParentElement.prototype.circle_dca3qi$;
  CircleElement.prototype.rect_jh2hm2$ = ParentElement.prototype.rect_jh2hm2$;
  CircleElement.prototype.line_m8p7mh$ = ParentElement.prototype.line_m8p7mh$;
  CircleElement.prototype.line_m8p7mh$$default = ParentElement.prototype.line_m8p7mh$$default;
  CircleElement.prototype.g_pjdmh5$ = ParentElement.prototype.g_pjdmh5$;
  CircleElement.prototype.text_b0yamr$ = ParentElement.prototype.text_b0yamr$;
  CircleElement.prototype.path_qraab9$ = ParentElement.prototype.path_qraab9$;
  Object.defineProperty(Has2D.prototype, 'height', Object.getOwnPropertyDescriptor(HasHeight.prototype, 'height'));
  Object.defineProperty(Has2D.prototype, 'width', Object.getOwnPropertyDescriptor(HasWidth.prototype, 'width'));
  RectElement.prototype.line_m8p7mh$$default = ParentElement.prototype.line_m8p7mh$;
  RectElement.prototype.setAttribute_jyasbz$ = ElementWrapper.prototype.setAttribute_jyasbz$;
  RectElement.prototype.getAttribute_61zpoe$ = ElementWrapper.prototype.getAttribute_61zpoe$;
  Object.defineProperty(RectElement.prototype, 'stroke', Object.getOwnPropertyDescriptor(HasStroke.prototype, 'stroke'));
  Object.defineProperty(RectElement.prototype, 'strokeWidth', Object.getOwnPropertyDescriptor(HasStroke.prototype, 'strokeWidth'));
  Object.defineProperty(RectElement.prototype, 'strokeLineCap', Object.getOwnPropertyDescriptor(HasStroke.prototype, 'strokeLineCap'));
  Object.defineProperty(RectElement.prototype, 'strokeDasharray', Object.getOwnPropertyDescriptor(HasStroke.prototype, 'strokeDasharray'));
  Object.defineProperty(RectElement.prototype, 'fill', Object.getOwnPropertyDescriptor(HasFill.prototype, 'fill'));
  Object.defineProperty(RectElement.prototype, 'center', Object.getOwnPropertyDescriptor(Has2D.prototype, 'center'));
  Object.defineProperty(RectElement.prototype, 'topRight', Object.getOwnPropertyDescriptor(Has2D.prototype, 'topRight'));
  Object.defineProperty(RectElement.prototype, 'height', Object.getOwnPropertyDescriptor(Has2D.prototype, 'height'));
  Object.defineProperty(RectElement.prototype, 'width', Object.getOwnPropertyDescriptor(Has2D.prototype, 'width'));
  RectElement.prototype.circle_dca3qi$ = ParentElement.prototype.circle_dca3qi$;
  RectElement.prototype.rect_jh2hm2$ = ParentElement.prototype.rect_jh2hm2$;
  RectElement.prototype.line_m8p7mh$ = ParentElement.prototype.line_m8p7mh$;
  RectElement.prototype.line_m8p7mh$$default = ParentElement.prototype.line_m8p7mh$$default;
  RectElement.prototype.g_pjdmh5$ = ParentElement.prototype.g_pjdmh5$;
  RectElement.prototype.text_b0yamr$ = ParentElement.prototype.text_b0yamr$;
  RectElement.prototype.path_qraab9$ = ParentElement.prototype.path_qraab9$;
  GroupElement.prototype.line_m8p7mh$$default = ParentElement.prototype.line_m8p7mh$;
  GroupElement.prototype.setAttribute_jyasbz$ = ElementWrapper.prototype.setAttribute_jyasbz$;
  GroupElement.prototype.getAttribute_61zpoe$ = ElementWrapper.prototype.getAttribute_61zpoe$;
  Object.defineProperty(GroupElement.prototype, 'stroke', Object.getOwnPropertyDescriptor(HasStroke.prototype, 'stroke'));
  Object.defineProperty(GroupElement.prototype, 'strokeWidth', Object.getOwnPropertyDescriptor(HasStroke.prototype, 'strokeWidth'));
  Object.defineProperty(GroupElement.prototype, 'strokeLineCap', Object.getOwnPropertyDescriptor(HasStroke.prototype, 'strokeLineCap'));
  Object.defineProperty(GroupElement.prototype, 'strokeDasharray', Object.getOwnPropertyDescriptor(HasStroke.prototype, 'strokeDasharray'));
  Object.defineProperty(GroupElement.prototype, 'fill', Object.getOwnPropertyDescriptor(HasFill.prototype, 'fill'));
  Object.defineProperty(GroupElement.prototype, 'center', Object.getOwnPropertyDescriptor(Has2D.prototype, 'center'));
  Object.defineProperty(GroupElement.prototype, 'topRight', Object.getOwnPropertyDescriptor(Has2D.prototype, 'topRight'));
  Object.defineProperty(GroupElement.prototype, 'height', Object.getOwnPropertyDescriptor(Has2D.prototype, 'height'));
  Object.defineProperty(GroupElement.prototype, 'width', Object.getOwnPropertyDescriptor(Has2D.prototype, 'width'));
  Object.defineProperty(GroupElement.prototype, 'x', Object.getOwnPropertyDescriptor(HasPosition.prototype, 'x'));
  Object.defineProperty(GroupElement.prototype, 'y', Object.getOwnPropertyDescriptor(HasPosition.prototype, 'y'));
  GroupElement.prototype.circle_dca3qi$ = ParentElement.prototype.circle_dca3qi$;
  GroupElement.prototype.rect_jh2hm2$ = ParentElement.prototype.rect_jh2hm2$;
  GroupElement.prototype.line_m8p7mh$ = ParentElement.prototype.line_m8p7mh$;
  GroupElement.prototype.line_m8p7mh$$default = ParentElement.prototype.line_m8p7mh$$default;
  GroupElement.prototype.g_pjdmh5$ = ParentElement.prototype.g_pjdmh5$;
  GroupElement.prototype.text_b0yamr$ = ParentElement.prototype.text_b0yamr$;
  GroupElement.prototype.path_qraab9$ = ParentElement.prototype.path_qraab9$;
  GroupElement.prototype.transform_1ogs0a$ = Transformable.prototype.transform_1ogs0a$;
  PathElement.prototype.setAttribute_jyasbz$ = ElementWrapper.prototype.setAttribute_jyasbz$;
  PathElement.prototype.getAttribute_61zpoe$ = ElementWrapper.prototype.getAttribute_61zpoe$;
  Object.defineProperty(PathElement.prototype, 'stroke', Object.getOwnPropertyDescriptor(HasStroke.prototype, 'stroke'));
  Object.defineProperty(PathElement.prototype, 'strokeWidth', Object.getOwnPropertyDescriptor(HasStroke.prototype, 'strokeWidth'));
  Object.defineProperty(PathElement.prototype, 'strokeLineCap', Object.getOwnPropertyDescriptor(HasStroke.prototype, 'strokeLineCap'));
  Object.defineProperty(PathElement.prototype, 'strokeDasharray', Object.getOwnPropertyDescriptor(HasStroke.prototype, 'strokeDasharray'));
  Object.defineProperty(PathElement.prototype, 'fill', Object.getOwnPropertyDescriptor(HasFill.prototype, 'fill'));
  Object.defineProperty(TextElement.prototype, 'x', Object.getOwnPropertyDescriptor(HasPosition.prototype, 'x'));
  Object.defineProperty(TextElement.prototype, 'y', Object.getOwnPropertyDescriptor(HasPosition.prototype, 'y'));
  TextElement.prototype.setAttribute_jyasbz$ = ElementWrapper.prototype.setAttribute_jyasbz$;
  TextElement.prototype.getAttribute_61zpoe$ = ElementWrapper.prototype.getAttribute_61zpoe$;
  Object.defineProperty(TextElement.prototype, 'fill', Object.getOwnPropertyDescriptor(HasFill.prototype, 'fill'));
  TextElement.prototype.transform_1ogs0a$ = Transformable.prototype.transform_1ogs0a$;
  TextElement.prototype.style_dv3ovl$ = HasStyle.prototype.style_dv3ovl$;
  SVGElement.prototype.line_m8p7mh$$default = ParentElement.prototype.line_m8p7mh$;
  Object.defineProperty(SVGElement.prototype, 'center', Object.getOwnPropertyDescriptor(Has2D.prototype, 'center'));
  Object.defineProperty(SVGElement.prototype, 'topRight', Object.getOwnPropertyDescriptor(Has2D.prototype, 'topRight'));
  Object.defineProperty(SVGElement.prototype, 'height', Object.getOwnPropertyDescriptor(Has2D.prototype, 'height'));
  Object.defineProperty(SVGElement.prototype, 'width', Object.getOwnPropertyDescriptor(Has2D.prototype, 'width'));
  SVGElement.prototype.circle_dca3qi$ = ParentElement.prototype.circle_dca3qi$;
  SVGElement.prototype.rect_jh2hm2$ = ParentElement.prototype.rect_jh2hm2$;
  SVGElement.prototype.line_m8p7mh$ = ParentElement.prototype.line_m8p7mh$;
  SVGElement.prototype.line_m8p7mh$$default = ParentElement.prototype.line_m8p7mh$$default;
  SVGElement.prototype.g_pjdmh5$ = ParentElement.prototype.g_pjdmh5$;
  SVGElement.prototype.text_b0yamr$ = ParentElement.prototype.text_b0yamr$;
  SVGElement.prototype.path_qraab9$ = ParentElement.prototype.path_qraab9$;
  SVGElement.prototype.setAttribute_jyasbz$ = ParentElement.prototype.setAttribute_jyasbz$;
  SVGElement.prototype.getAttribute_61zpoe$ = ParentElement.prototype.getAttribute_61zpoe$;
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$svg = package$data2viz.svg || (package$data2viz.svg = {});
  package$svg.createSVGElement_61zpoe$ = createSVGElement;
  package$svg.Path = Path;
  package$svg.Transform = Transform;
  package$svg.Style = Style;
  package$svg.SvgTagMarker = SvgTagMarker;
  package$svg.AccessByAttributes = AccessByAttributes;
  package$svg.ElementWrapper = ElementWrapper;
  package$svg.HasFill = HasFill;
  package$svg.HasStroke = HasStroke;
  package$svg.svg_9huslq$ = svg;
  package$svg.CircleElement = CircleElement;
  package$svg.RectElement = RectElement;
  package$svg.GroupElement = GroupElement;
  package$svg.PathElement = PathElement;
  package$svg.TextElement = TextElement;
  package$svg.SVGElement = SVGElement;
  package$svg.ParentElement = ParentElement;
  package$svg.Margins = Margins;
  package$svg.HasText = HasText;
  package$svg.Transformable = Transformable;
  package$svg.HasStyle = HasStyle;
  package$svg.HasPosition = HasPosition;
  package$svg.HasCenter = HasCenter;
  package$svg.HasRadius = HasRadius;
  package$svg.HasWidth = HasWidth;
  package$svg.HasHeight = HasHeight;
  package$svg.Has2D = Has2D;
  Kotlin.defineModule('svg', _);
  return _;
});

//@ sourceMappingURL=svg.js.map
