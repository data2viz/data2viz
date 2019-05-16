(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin', 'd2v-core-js', 'd2v-color-js', 'd2v-timer-js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'), require('d2v-core-js'), require('d2v-color-js'), require('d2v-timer-js'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'd2v-viz-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'd2v-viz-js'.");
    }
    if (typeof this['d2v-core-js'] === 'undefined') {
      throw new Error("Error loading module 'd2v-viz-js'. Its dependency 'd2v-core-js' was not found. Please, check whether 'd2v-core-js' is loaded prior to 'd2v-viz-js'.");
    }
    if (typeof this['d2v-color-js'] === 'undefined') {
      throw new Error("Error loading module 'd2v-viz-js'. Its dependency 'd2v-color-js' was not found. Please, check whether 'd2v-color-js' is loaded prior to 'd2v-viz-js'.");
    }
    if (typeof this['d2v-timer-js'] === 'undefined') {
      throw new Error("Error loading module 'd2v-viz-js'. Its dependency 'd2v-timer-js' was not found. Please, check whether 'd2v-timer-js' is loaded prior to 'd2v-viz-js'.");
    }
    root['d2v-viz-js'] = factory(typeof this['d2v-viz-js'] === 'undefined' ? {} : this['d2v-viz-js'], kotlin, this['d2v-core-js'], this['d2v-color-js'], this['d2v-timer-js']);
  }
}(this, function (_, Kotlin, $module$d2v_core_js, $module$d2v_color_js, $module$d2v_timer_js) {
  'use strict';
  var CircleGeom = $module$d2v_core_js.io.data2viz.geom.CircleGeom;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var Circle = $module$d2v_core_js.io.data2viz.geom.Circle;
  var Unit = Kotlin.kotlin.Unit;
  var Kind_INTERFACE = Kotlin.Kind.INTERFACE;
  var PathGeom = $module$d2v_core_js.io.data2viz.geom.PathGeom;
  var Path = $module$d2v_core_js.io.data2viz.geom.Path;
  var RectGeom = $module$d2v_core_js.io.data2viz.geom.RectGeom;
  var Rect = $module$d2v_core_js.io.data2viz.geom.Rect;
  var Pair = Kotlin.kotlin.Pair;
  var listOf = Kotlin.kotlin.collections.listOf_i5x0yv$;
  var Colors = $module$d2v_color_js.io.data2viz.color.Colors;
  var math = $module$d2v_core_js.io.data2viz.math;
  var get_col = $module$d2v_color_js.io.data2viz.color.get_col_s8ev3n$;
  var get_pct = $module$d2v_core_js.io.data2viz.math.get_pct_rcaex3$;
  var IntRange = Kotlin.kotlin.ranges.IntRange;
  var Enum = Kotlin.kotlin.Enum;
  var throwISE = Kotlin.throwISE;
  var ensureNotNull = Kotlin.ensureNotNull;
  var Kind_OBJECT = Kotlin.Kind.OBJECT;
  var throwUPAE = Kotlin.throwUPAE;
  var mutableListOf = Kotlin.kotlin.collections.mutableListOf_i5x0yv$;
  var HasSize = $module$d2v_core_js.io.data2viz.geom.HasSize;
  var throwCCE = Kotlin.throwCCE;
  var numberToInt = Kotlin.numberToInt;
  var timer = $module$d2v_timer_js.io.data2viz.timer.timer_k9susy$;
  var math_0 = Kotlin.kotlin.math;
  var Color = $module$d2v_color_js.io.data2viz.color.Color;
  var LinearGradient = $module$d2v_color_js.io.data2viz.color.LinearGradient;
  var RadialGradient = $module$d2v_color_js.io.data2viz.color.RadialGradient;
  var MoveTo = $module$d2v_core_js.io.data2viz.geom.MoveTo;
  var LineTo = $module$d2v_core_js.io.data2viz.geom.LineTo;
  var QuadraticCurveTo = $module$d2v_core_js.io.data2viz.geom.QuadraticCurveTo;
  var BezierCurveTo = $module$d2v_core_js.io.data2viz.geom.BezierCurveTo;
  var Arc = $module$d2v_core_js.io.data2viz.geom.Arc;
  var ArcTo = $module$d2v_core_js.io.data2viz.geom.ArcTo;
  var ClosePath = $module$d2v_core_js.io.data2viz.geom.ClosePath;
  var RectCmd = $module$d2v_core_js.io.data2viz.geom.RectCmd;
  CircleNode.prototype = Object.create(Node.prototype);
  CircleNode.prototype.constructor = CircleNode;
  GroupNode.prototype = Object.create(Node.prototype);
  GroupNode.prototype.constructor = GroupNode;
  Layer.prototype = Object.create(GroupNode.prototype);
  Layer.prototype.constructor = Layer;
  LineNode.prototype = Object.create(Node.prototype);
  LineNode.prototype.constructor = LineNode;
  PathNode.prototype = Object.create(Node.prototype);
  PathNode.prototype.constructor = PathNode;
  RectNode.prototype = Object.create(Node.prototype);
  RectNode.prototype.constructor = RectNode;
  StateManagerStatus.prototype = Object.create(Enum.prototype);
  StateManagerStatus.prototype.constructor = StateManagerStatus;
  TextAnchor.prototype = Object.create(Enum.prototype);
  TextAnchor.prototype.constructor = TextAnchor;
  TextAlignmentBaseline.prototype = Object.create(Enum.prototype);
  TextAlignmentBaseline.prototype.constructor = TextAlignmentBaseline;
  TextNode.prototype = Object.create(Node.prototype);
  TextNode.prototype.constructor = TextNode;
  FontWeight.prototype = Object.create(Enum.prototype);
  FontWeight.prototype.constructor = FontWeight;
  FontPosture.prototype = Object.create(Enum.prototype);
  FontPosture.prototype.constructor = FontPosture;
  function CircleNode(circle) {
    if (circle === void 0)
      circle = new CircleGeom();
    Node.call(this);
    this.circle = circle;
    this.transform_p1zd3w$_0 = null;
  }
  Object.defineProperty(CircleNode.prototype, 'transform', {
    get: function () {
      return this.transform_p1zd3w$_0;
    },
    set: function (transform) {
      this.transform_p1zd3w$_0 = transform;
    }
  });
  Object.defineProperty(CircleNode.prototype, 'radius', {
    get: function () {
      return this.circle.radius;
    },
    set: function (tmp$) {
      this.circle.radius = tmp$;
    }
  });
  Object.defineProperty(CircleNode.prototype, 'x', {
    get: function () {
      return this.circle.x;
    },
    set: function (tmp$) {
      this.circle.x = tmp$;
    }
  });
  Object.defineProperty(CircleNode.prototype, 'y', {
    get: function () {
      return this.circle.y;
    },
    set: function (tmp$) {
      this.circle.y = tmp$;
    }
  });
  CircleNode.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'CircleNode',
    interfaces: [HasFill, HasTransform, HasStroke, Circle, Node]
  };
  function TextAlign(horizontal, vertical) {
    this.horizontal = horizontal;
    this.vertical = vertical;
  }
  TextAlign.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'TextAlign',
    interfaces: []
  };
  TextAlign.prototype.component1 = function () {
    return this.horizontal;
  };
  TextAlign.prototype.component2 = function () {
    return this.vertical;
  };
  TextAlign.prototype.copy_ik9my9$ = function (horizontal, vertical) {
    return new TextAlign(horizontal === void 0 ? this.horizontal : horizontal, vertical === void 0 ? this.vertical : vertical);
  };
  TextAlign.prototype.toString = function () {
    return 'TextAlign(horizontal=' + Kotlin.toString(this.horizontal) + (', vertical=' + Kotlin.toString(this.vertical)) + ')';
  };
  TextAlign.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.horizontal) | 0;
    result = result * 31 + Kotlin.hashCode(this.vertical) | 0;
    return result;
  };
  TextAlign.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.horizontal, other.horizontal) && Kotlin.equals(this.vertical, other.vertical)))));
  };
  function get_textAlign($receiver) {
    return textAlign($receiver, $receiver.anchor, $receiver.baseline);
  }
  function set_textAlign($receiver, value) {
    $receiver.anchor = value.horizontal;
    $receiver.baseline = value.vertical;
  }
  function textAlign($receiver, horizontal, vertical) {
    if (horizontal === void 0)
      horizontal = $receiver.anchor;
    if (vertical === void 0)
      vertical = $receiver.baseline;
    return new TextAlign(horizontal, vertical);
  }
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  function GroupNode() {
    Node.call(this);
    this.transform_shx8rt$_0 = null;
    this.children = ArrayList_init();
  }
  Object.defineProperty(GroupNode.prototype, 'transform', {
    get: function () {
      return this.transform_shx8rt$_0;
    },
    set: function (transform) {
      this.transform_shx8rt$_0 = transform;
    }
  });
  GroupNode.prototype.transform_tabxxp$ = function (init) {
    var $receiver = new Transform();
    init($receiver);
    this.transform = $receiver;
  };
  GroupNode.prototype.add_vetai8$ = function (node) {
    this.children.add_11rb$(node);
    node.parent = this;
  };
  GroupNode.prototype.remove_vetai8$ = function (node) {
    node.parent = null;
    this.children.remove_11rb$(node);
  };
  GroupNode.prototype.clear = function () {
    this.children.clear();
  };
  GroupNode.prototype.group_mdx85a$ = function (init) {
    var $receiver = new GroupNode();
    init($receiver);
    this.add_vetai8$($receiver);
    return $receiver;
  };
  GroupNode.prototype.line_e8vvwz$ = function (init) {
    var $receiver = new LineNode();
    init($receiver);
    this.add_vetai8$($receiver);
    return $receiver;
  };
  GroupNode.prototype.circle_107i6h$ = function (init) {
    var $receiver = new CircleNode();
    init($receiver);
    this.add_vetai8$($receiver);
    return $receiver;
  };
  GroupNode.prototype.rect_afayqr$ = function (init) {
    var $receiver = new RectNode();
    init($receiver);
    this.add_vetai8$($receiver);
    return $receiver;
  };
  GroupNode.prototype.text_6q900q$ = function (init) {
    var $receiver = new TextNode();
    init($receiver);
    this.add_vetai8$($receiver);
    return $receiver;
  };
  GroupNode.prototype.path_omiyse$ = function (init) {
    var $receiver = new PathNode();
    init($receiver);
    this.add_vetai8$($receiver);
    return $receiver;
  };
  GroupNode.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'GroupNode',
    interfaces: [HasTransform, HasChildren, Node]
  };
  function HasChildren() {
  }
  HasChildren.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'HasChildren',
    interfaces: [Style]
  };
  function Layer() {
    GroupNode.call(this);
  }
  Layer.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Layer',
    interfaces: [GroupNode]
  };
  function LineNode() {
    Node.call(this);
    this.transform_56wv08$_0 = null;
    this.x1 = 0.0;
    this.y1 = 0.0;
    this.x2 = 0.0;
    this.y2 = 0.0;
  }
  Object.defineProperty(LineNode.prototype, 'transform', {
    get: function () {
      return this.transform_56wv08$_0;
    },
    set: function (transform) {
      this.transform_56wv08$_0 = transform;
    }
  });
  LineNode.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'LineNode',
    interfaces: [HasTransform, HasStroke, Node]
  };
  function Node() {
    this.parent_41e1qq$_0 = null;
    this.visible = true;
    this.style_u8faqh$_0 = new HierarchicalStyle(this.parent);
  }
  Object.defineProperty(Node.prototype, 'parent', {
    get: function () {
      return this.parent_41e1qq$_0;
    },
    set: function (value) {
      this.parent_41e1qq$_0 = value;
      this.style_u8faqh$_0.parent = value;
    }
  });
  Node.prototype.remove = function () {
    var tmp$;
    (tmp$ = this.parent) != null ? (tmp$.remove_vetai8$(this), Unit) : null;
  };
  Object.defineProperty(Node.prototype, 'fill', {
    get: function () {
      return this.style_u8faqh$_0.fill;
    },
    set: function (value) {
      this.style_u8faqh$_0.fill = value;
    }
  });
  Object.defineProperty(Node.prototype, 'stroke', {
    get: function () {
      return this.style_u8faqh$_0.stroke;
    },
    set: function (value) {
      this.style_u8faqh$_0.stroke = value;
    }
  });
  Object.defineProperty(Node.prototype, 'strokeWidth', {
    get: function () {
      return this.style_u8faqh$_0.strokeWidth;
    },
    set: function (value) {
      this.style_u8faqh$_0.strokeWidth = value;
    }
  });
  Object.defineProperty(Node.prototype, 'anchor', {
    get: function () {
      return this.style_u8faqh$_0.anchor;
    },
    set: function (value) {
      this.style_u8faqh$_0.anchor = value;
    }
  });
  Object.defineProperty(Node.prototype, 'baseline', {
    get: function () {
      return this.style_u8faqh$_0.baseline;
    },
    set: function (value) {
      this.style_u8faqh$_0.baseline = value;
    }
  });
  Node.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Node',
    interfaces: [Style]
  };
  function PathNode(path) {
    if (path === void 0)
      path = new PathGeom();
    Node.call(this);
    this.path = path;
    this.transform_mw07dl$_0 = null;
  }
  Object.defineProperty(PathNode.prototype, 'transform', {
    get: function () {
      return this.transform_mw07dl$_0;
    },
    set: function (transform) {
      this.transform_mw07dl$_0 = transform;
    }
  });
  PathNode.prototype.clearPath = function () {
    this.path.clearPath();
  };
  PathNode.prototype.arc_6p3vsx$$default = function (centerX, centerY, radius, startAngle, endAngle, counterClockWise) {
    return this.path.arc_6p3vsx$$default(centerX, centerY, radius, startAngle, endAngle, counterClockWise);
  };
  PathNode.prototype.arcTo_1lq62i$ = function (cpx, cpy, x, y, radius) {
    return this.path.arcTo_1lq62i$(cpx, cpy, x, y, radius);
  };
  PathNode.prototype.bezierCurveTo_15yvbs$ = function (cpx1, cpy1, cpx2, cpy2, x, y) {
    return this.path.bezierCurveTo_15yvbs$(cpx1, cpy1, cpx2, cpy2, x, y);
  };
  PathNode.prototype.closePath = function () {
    return this.path.closePath();
  };
  PathNode.prototype.lineTo_lu1900$ = function (x, y) {
    return this.path.lineTo_lu1900$(x, y);
  };
  PathNode.prototype.moveTo_lu1900$ = function (x, y) {
    return this.path.moveTo_lu1900$(x, y);
  };
  PathNode.prototype.quadraticCurveTo_6y0v78$ = function (cpx, cpy, x, y) {
    return this.path.quadraticCurveTo_6y0v78$(cpx, cpy, x, y);
  };
  PathNode.prototype.rect_6y0v78$ = function (x, y, w, h) {
    return this.path.rect_6y0v78$(x, y, w, h);
  };
  PathNode.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'PathNode',
    interfaces: [Path, HasTransform, HasFill, HasStroke, Node]
  };
  function RectNode(rect) {
    if (rect === void 0)
      rect = new RectGeom();
    Node.call(this);
    this.$delegate_or6qh8$_0 = rect;
  }
  Object.defineProperty(RectNode.prototype, 'bottom', {
    get: function () {
      return this.$delegate_or6qh8$_0.bottom;
    }
  });
  Object.defineProperty(RectNode.prototype, 'bottomLeft', {
    get: function () {
      return this.$delegate_or6qh8$_0.bottomLeft;
    }
  });
  Object.defineProperty(RectNode.prototype, 'bottomRight', {
    get: function () {
      return this.$delegate_or6qh8$_0.bottomRight;
    }
  });
  Object.defineProperty(RectNode.prototype, 'center', {
    get: function () {
      return this.$delegate_or6qh8$_0.center;
    }
  });
  Object.defineProperty(RectNode.prototype, 'height', {
    get: function () {
      return this.$delegate_or6qh8$_0.height;
    },
    set: function (tmp$) {
      this.$delegate_or6qh8$_0.height = tmp$;
    }
  });
  Object.defineProperty(RectNode.prototype, 'left', {
    get: function () {
      return this.$delegate_or6qh8$_0.left;
    }
  });
  Object.defineProperty(RectNode.prototype, 'right', {
    get: function () {
      return this.$delegate_or6qh8$_0.right;
    }
  });
  Object.defineProperty(RectNode.prototype, 'size', {
    get: function () {
      return this.$delegate_or6qh8$_0.size;
    },
    set: function (tmp$) {
      this.$delegate_or6qh8$_0.size = tmp$;
    }
  });
  Object.defineProperty(RectNode.prototype, 'top', {
    get: function () {
      return this.$delegate_or6qh8$_0.top;
    }
  });
  Object.defineProperty(RectNode.prototype, 'topLeft', {
    get: function () {
      return this.$delegate_or6qh8$_0.topLeft;
    }
  });
  Object.defineProperty(RectNode.prototype, 'topRight', {
    get: function () {
      return this.$delegate_or6qh8$_0.topRight;
    }
  });
  Object.defineProperty(RectNode.prototype, 'width', {
    get: function () {
      return this.$delegate_or6qh8$_0.width;
    },
    set: function (tmp$) {
      this.$delegate_or6qh8$_0.width = tmp$;
    }
  });
  Object.defineProperty(RectNode.prototype, 'x', {
    get: function () {
      return this.$delegate_or6qh8$_0.x;
    },
    set: function (tmp$) {
      this.$delegate_or6qh8$_0.x = tmp$;
    }
  });
  Object.defineProperty(RectNode.prototype, 'y', {
    get: function () {
      return this.$delegate_or6qh8$_0.y;
    },
    set: function (tmp$) {
      this.$delegate_or6qh8$_0.y = tmp$;
    }
  });
  RectNode.prototype.contains_mowjvf$ = function (point) {
    return this.$delegate_or6qh8$_0.contains_mowjvf$(point);
  };
  RectNode.prototype.contains_wt5aq9$ = function (rect) {
    return this.$delegate_or6qh8$_0.contains_wt5aq9$(rect);
  };
  RectNode.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'RectNode',
    interfaces: [HasStroke, HasFill, Rect, Node]
  };
  function RenderingTest(name, viz) {
    this.name = name;
    this.viz = viz;
  }
  RenderingTest.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'RenderingTest',
    interfaces: []
  };
  RenderingTest.prototype.component1 = function () {
    return this.name;
  };
  RenderingTest.prototype.component2 = function () {
    return this.viz;
  };
  RenderingTest.prototype.copy_mg2rqt$ = function (name, viz) {
    return new RenderingTest(name === void 0 ? this.name : name, viz === void 0 ? this.viz : viz);
  };
  RenderingTest.prototype.toString = function () {
    return 'RenderingTest(name=' + Kotlin.toString(this.name) + (', viz=' + Kotlin.toString(this.viz)) + ')';
  };
  RenderingTest.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.name) | 0;
    result = result * 31 + Kotlin.hashCode(this.viz) | 0;
    return result;
  };
  RenderingTest.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.name, other.name) && Kotlin.equals(this.viz, other.viz)))));
  };
  function next($receiver) {
    var x = $receiver.first;
    var y = $receiver.second;
    if (x >= 350) {
      x = 25.0;
      y += 50.0;
    }
     else
      x += 50.0;
    return new Pair(x, y);
  }
  var linesOfText;
  var fontSizeValue;
  var fontFamilyValue;
  function allRenderingTests$lambda$lambda(closure$line, closure$index) {
    return function ($receiver) {
      $receiver.fontFamily = FontFamily$Companion_getInstance().SANS_SERIF;
      $receiver.fontSize = 20.0;
      $receiver.fontStyle = FontPosture$ITALIC_getInstance();
      $receiver.fontWeight = FontWeight$BOLD_getInstance();
      $receiver.anchor = TextAnchor$START_getInstance();
      $receiver.baseline = TextAlignmentBaseline$BASELINE_getInstance();
      $receiver.textContent = closure$line;
      $receiver.y = fontSizeValue + fontSizeValue * closure$index;
      $receiver.fill = Colors.Web.black;
      $receiver.fontSize = fontSizeValue;
      return Unit;
    };
  }
  function allRenderingTests$lambda($receiver) {
    var index = 0;
    for (var tmp$ = linesOfText.iterator(); tmp$.hasNext(); ++index) {
      var line = tmp$.next();
      $receiver.text_6q900q$(allRenderingTests$lambda$lambda(line, index));
    }
    return Unit;
  }
  function allRenderingTests$lambda$lambda_0(closure$line, closure$index) {
    return function ($receiver) {
      $receiver.fontFamily = FontFamily$Companion_getInstance().SANS_SERIF;
      $receiver.fontSize = 20.0;
      $receiver.fontStyle = FontPosture$ITALIC_getInstance();
      $receiver.fontWeight = FontWeight$NORMAL_getInstance();
      $receiver.anchor = TextAnchor$START_getInstance();
      $receiver.baseline = TextAlignmentBaseline$BASELINE_getInstance();
      $receiver.textContent = closure$line;
      $receiver.y = fontSizeValue + fontSizeValue * closure$index;
      $receiver.fill = Colors.Web.black;
      $receiver.fontSize = fontSizeValue;
      return Unit;
    };
  }
  function allRenderingTests$lambda_0($receiver) {
    var index = 0;
    for (var tmp$ = linesOfText.iterator(); tmp$.hasNext(); ++index) {
      var line = tmp$.next();
      $receiver.text_6q900q$(allRenderingTests$lambda$lambda_0(line, index));
    }
    return Unit;
  }
  function allRenderingTests$lambda$lambda_1(closure$line, closure$index) {
    return function ($receiver) {
      $receiver.fontFamily = FontFamily$Companion_getInstance().SANS_SERIF;
      $receiver.fontSize = 20.0;
      $receiver.fontStyle = FontPosture$NORMAL_getInstance();
      $receiver.fontWeight = FontWeight$BOLD_getInstance();
      $receiver.anchor = TextAnchor$START_getInstance();
      $receiver.baseline = TextAlignmentBaseline$BASELINE_getInstance();
      $receiver.textContent = closure$line;
      $receiver.y = fontSizeValue + fontSizeValue * closure$index;
      $receiver.fill = Colors.Web.black;
      $receiver.fontSize = fontSizeValue;
      return Unit;
    };
  }
  function allRenderingTests$lambda_1($receiver) {
    var index = 0;
    for (var tmp$ = linesOfText.iterator(); tmp$.hasNext(); ++index) {
      var line = tmp$.next();
      $receiver.text_6q900q$(allRenderingTests$lambda$lambda_1(line, index));
    }
    return Unit;
  }
  function allRenderingTests$lambda$lambda_2(closure$line, closure$index) {
    return function ($receiver) {
      $receiver.fontFamily = FontFamily$Companion_getInstance().SANS_SERIF;
      $receiver.fontSize = 20.0;
      $receiver.fontStyle = FontPosture$NORMAL_getInstance();
      $receiver.fontWeight = FontWeight$NORMAL_getInstance();
      $receiver.anchor = TextAnchor$START_getInstance();
      $receiver.baseline = TextAlignmentBaseline$BASELINE_getInstance();
      $receiver.textContent = closure$line;
      $receiver.y = fontSizeValue + fontSizeValue * closure$index;
      $receiver.fill = Colors.Web.black;
      $receiver.fontSize = fontSizeValue;
      return Unit;
    };
  }
  function allRenderingTests$lambda_2($receiver) {
    var index = 0;
    for (var tmp$ = linesOfText.iterator(); tmp$.hasNext(); ++index) {
      var line = tmp$.next();
      $receiver.text_6q900q$(allRenderingTests$lambda$lambda_2(line, index));
    }
    return Unit;
  }
  function allRenderingTests$lambda$lambda_3(closure$line, closure$index) {
    return function ($receiver) {
      $receiver.fontFamily = FontFamily$Companion_getInstance().SERIF;
      $receiver.fontSize = 20.0;
      $receiver.fontStyle = FontPosture$ITALIC_getInstance();
      $receiver.fontWeight = FontWeight$BOLD_getInstance();
      $receiver.anchor = TextAnchor$START_getInstance();
      $receiver.baseline = TextAlignmentBaseline$BASELINE_getInstance();
      $receiver.textContent = closure$line;
      $receiver.y = fontSizeValue + fontSizeValue * closure$index;
      $receiver.fill = Colors.Web.black;
      $receiver.fontSize = fontSizeValue;
      return Unit;
    };
  }
  function allRenderingTests$lambda_3($receiver) {
    var index = 0;
    for (var tmp$ = linesOfText.iterator(); tmp$.hasNext(); ++index) {
      var line = tmp$.next();
      $receiver.text_6q900q$(allRenderingTests$lambda$lambda_3(line, index));
    }
    return Unit;
  }
  function allRenderingTests$lambda$lambda_4(closure$line, closure$index) {
    return function ($receiver) {
      $receiver.fontFamily = FontFamily$Companion_getInstance().SERIF;
      $receiver.fontSize = 20.0;
      $receiver.fontStyle = FontPosture$ITALIC_getInstance();
      $receiver.fontWeight = FontWeight$NORMAL_getInstance();
      $receiver.anchor = TextAnchor$START_getInstance();
      $receiver.baseline = TextAlignmentBaseline$BASELINE_getInstance();
      $receiver.textContent = closure$line;
      $receiver.y = fontSizeValue + fontSizeValue * closure$index;
      $receiver.fill = Colors.Web.black;
      $receiver.fontSize = fontSizeValue;
      return Unit;
    };
  }
  function allRenderingTests$lambda_4($receiver) {
    var index = 0;
    for (var tmp$ = linesOfText.iterator(); tmp$.hasNext(); ++index) {
      var line = tmp$.next();
      $receiver.text_6q900q$(allRenderingTests$lambda$lambda_4(line, index));
    }
    return Unit;
  }
  function allRenderingTests$lambda$lambda_5(closure$line, closure$index) {
    return function ($receiver) {
      $receiver.fontFamily = FontFamily$Companion_getInstance().SERIF;
      $receiver.fontSize = 20.0;
      $receiver.fontStyle = FontPosture$NORMAL_getInstance();
      $receiver.fontWeight = FontWeight$BOLD_getInstance();
      $receiver.anchor = TextAnchor$START_getInstance();
      $receiver.baseline = TextAlignmentBaseline$BASELINE_getInstance();
      $receiver.textContent = closure$line;
      $receiver.y = fontSizeValue + fontSizeValue * closure$index;
      $receiver.fill = Colors.Web.black;
      $receiver.fontSize = fontSizeValue;
      return Unit;
    };
  }
  function allRenderingTests$lambda_5($receiver) {
    var index = 0;
    for (var tmp$ = linesOfText.iterator(); tmp$.hasNext(); ++index) {
      var line = tmp$.next();
      $receiver.text_6q900q$(allRenderingTests$lambda$lambda_5(line, index));
    }
    return Unit;
  }
  function allRenderingTests$lambda$lambda_6(closure$line, closure$index) {
    return function ($receiver) {
      $receiver.fontFamily = FontFamily$Companion_getInstance().SERIF;
      $receiver.fontSize = 20.0;
      $receiver.fontStyle = FontPosture$NORMAL_getInstance();
      $receiver.fontWeight = FontWeight$NORMAL_getInstance();
      $receiver.anchor = TextAnchor$START_getInstance();
      $receiver.baseline = TextAlignmentBaseline$BASELINE_getInstance();
      $receiver.textContent = closure$line;
      $receiver.y = fontSizeValue + fontSizeValue * closure$index;
      $receiver.fill = Colors.Web.black;
      $receiver.fontSize = fontSizeValue;
      return Unit;
    };
  }
  function allRenderingTests$lambda_6($receiver) {
    var index = 0;
    for (var tmp$ = linesOfText.iterator(); tmp$.hasNext(); ++index) {
      var line = tmp$.next();
      $receiver.text_6q900q$(allRenderingTests$lambda$lambda_6(line, index));
    }
    return Unit;
  }
  function allRenderingTests$lambda$lambda_7(closure$line, closure$index) {
    return function ($receiver) {
      $receiver.fontFamily = FontFamily$Companion_getInstance().MONOSPACE;
      $receiver.fontSize = 20.0;
      $receiver.fontStyle = FontPosture$ITALIC_getInstance();
      $receiver.fontWeight = FontWeight$BOLD_getInstance();
      $receiver.anchor = TextAnchor$START_getInstance();
      $receiver.baseline = TextAlignmentBaseline$BASELINE_getInstance();
      $receiver.textContent = closure$line;
      $receiver.y = fontSizeValue + fontSizeValue * closure$index;
      $receiver.fill = Colors.Web.black;
      $receiver.fontSize = fontSizeValue;
      return Unit;
    };
  }
  function allRenderingTests$lambda_7($receiver) {
    var index = 0;
    for (var tmp$ = linesOfText.iterator(); tmp$.hasNext(); ++index) {
      var line = tmp$.next();
      $receiver.text_6q900q$(allRenderingTests$lambda$lambda_7(line, index));
    }
    return Unit;
  }
  function allRenderingTests$lambda$lambda_8(closure$line, closure$index) {
    return function ($receiver) {
      $receiver.fontFamily = FontFamily$Companion_getInstance().MONOSPACE;
      $receiver.fontSize = 20.0;
      $receiver.fontStyle = FontPosture$ITALIC_getInstance();
      $receiver.fontWeight = FontWeight$NORMAL_getInstance();
      $receiver.anchor = TextAnchor$START_getInstance();
      $receiver.baseline = TextAlignmentBaseline$BASELINE_getInstance();
      $receiver.textContent = closure$line;
      $receiver.y = fontSizeValue + fontSizeValue * closure$index;
      $receiver.fill = Colors.Web.black;
      $receiver.fontSize = fontSizeValue;
      return Unit;
    };
  }
  function allRenderingTests$lambda_8($receiver) {
    var index = 0;
    for (var tmp$ = linesOfText.iterator(); tmp$.hasNext(); ++index) {
      var line = tmp$.next();
      $receiver.text_6q900q$(allRenderingTests$lambda$lambda_8(line, index));
    }
    return Unit;
  }
  function allRenderingTests$lambda$lambda_9(closure$line, closure$index) {
    return function ($receiver) {
      $receiver.fontFamily = FontFamily$Companion_getInstance().MONOSPACE;
      $receiver.fontSize = 20.0;
      $receiver.fontStyle = FontPosture$NORMAL_getInstance();
      $receiver.fontWeight = FontWeight$BOLD_getInstance();
      $receiver.anchor = TextAnchor$START_getInstance();
      $receiver.baseline = TextAlignmentBaseline$BASELINE_getInstance();
      $receiver.textContent = closure$line;
      $receiver.y = fontSizeValue + fontSizeValue * closure$index;
      $receiver.fill = Colors.Web.black;
      $receiver.fontSize = fontSizeValue;
      return Unit;
    };
  }
  function allRenderingTests$lambda_9($receiver) {
    var index = 0;
    for (var tmp$ = linesOfText.iterator(); tmp$.hasNext(); ++index) {
      var line = tmp$.next();
      $receiver.text_6q900q$(allRenderingTests$lambda$lambda_9(line, index));
    }
    return Unit;
  }
  function allRenderingTests$lambda$lambda_10(closure$line, closure$index) {
    return function ($receiver) {
      $receiver.fontFamily = FontFamily$Companion_getInstance().MONOSPACE;
      $receiver.fontSize = 20.0;
      $receiver.fontStyle = FontPosture$NORMAL_getInstance();
      $receiver.fontWeight = FontWeight$NORMAL_getInstance();
      $receiver.anchor = TextAnchor$START_getInstance();
      $receiver.baseline = TextAlignmentBaseline$BASELINE_getInstance();
      $receiver.textContent = closure$line;
      $receiver.y = fontSizeValue + fontSizeValue * closure$index;
      $receiver.fill = Colors.Web.black;
      $receiver.fontSize = fontSizeValue;
      return Unit;
    };
  }
  function allRenderingTests$lambda_10($receiver) {
    var index = 0;
    for (var tmp$ = linesOfText.iterator(); tmp$.hasNext(); ++index) {
      var line = tmp$.next();
      $receiver.text_6q900q$(allRenderingTests$lambda$lambda_10(line, index));
    }
    return Unit;
  }
  function allRenderingTests$lambda$addToParent$lambda$lambda($receiver) {
    $receiver.translate_lu1900$(10.0, 10.0);
    $receiver.rotate_14dthe$(0.1 * math.PI / 2);
    return Unit;
  }
  function allRenderingTests$lambda$addToParent$lambda$lambda_0($receiver) {
    $receiver.height = 10.0;
    $receiver.width = 10.0;
    $receiver.fill = Colors.Web.black;
    return Unit;
  }
  function allRenderingTests$lambda$addToParent(closure$depth) {
    return function closure$addToParent(parent) {
      var tmp$, tmp$_0;
      tmp$ = closure$depth.v;
      closure$depth.v = tmp$ + 1 | 0;
      if (closure$depth.v === 41)
        return;
      var receiver = new GroupNode();
      closure$addToParent;
      var closure$addToParent_0 = closure$addToParent;
      parent.add_vetai8$(receiver);
      receiver.transform_tabxxp$(allRenderingTests$lambda$addToParent$lambda$lambda);
      receiver.rect_afayqr$(allRenderingTests$lambda$addToParent$lambda$lambda_0);
      closure$addToParent_0(receiver);
      tmp$_0 = closure$depth.v;
      closure$depth.v = tmp$_0 - 1 | 0;
    };
  }
  function allRenderingTests$lambda$lambda$lambda($receiver) {
    $receiver.translate_lu1900$(250.0, 125.0);
    return Unit;
  }
  function allRenderingTests$lambda$lambda$lambda_0($receiver) {
    $receiver.height = 10.0;
    $receiver.width = 10.0;
    $receiver.fill = Colors.Web.black;
    return Unit;
  }
  function allRenderingTests$lambda$lambda_11($receiver) {
    $receiver.transform_tabxxp$(allRenderingTests$lambda$lambda$lambda);
    $receiver.rect_afayqr$(allRenderingTests$lambda$lambda$lambda_0);
    return Unit;
  }
  function allRenderingTests$lambda_11($receiver) {
    var depth = {v: 0};
    var addToParent = allRenderingTests$lambda$addToParent(depth);
    addToParent($receiver.group_mdx85a$(allRenderingTests$lambda$lambda_11));
    return Unit;
  }
  function allRenderingTests$lambda$lambda$lambda_1($receiver) {
    $receiver.x = 100.0;
    $receiver.y = 100.0;
    $receiver.width = 20.0;
    $receiver.height = 10.0;
    $receiver.fill = Colors.Web.red;
    return Unit;
  }
  function allRenderingTests$lambda$lambda_12(this$) {
    return function ($receiver) {
      this$.rect_afayqr$(allRenderingTests$lambda$lambda$lambda_1);
      return Unit;
    };
  }
  function allRenderingTests$lambda_12($receiver) {
    $receiver.rect_afayqr$(allRenderingTests$lambda$lambda_12($receiver));
    return Unit;
  }
  function allRenderingTests$lambda$lambda_13($receiver) {
    $receiver.x = 200.0;
    $receiver.y = 200.0;
    $receiver.radius = 100.0;
    $receiver.fill = Colors.Web.red;
    return Unit;
  }
  function allRenderingTests$lambda_13($receiver) {
    $receiver.circle_107i6h$(allRenderingTests$lambda$lambda_13);
    return Unit;
  }
  function allRenderingTests$lambda$lambda_14($receiver) {
    $receiver.x = 200.0;
    $receiver.y = 200.0;
    $receiver.radius = 100.0;
    $receiver.fill = null;
    $receiver.stroke = Colors.Web.red;
    $receiver.strokeWidth = 20.0;
    return Unit;
  }
  function allRenderingTests$lambda_14($receiver) {
    $receiver.circle_107i6h$(allRenderingTests$lambda$lambda_14);
    return Unit;
  }
  function allRenderingTests$lambda$lambda_15($receiver) {
    $receiver.x = 200.0;
    $receiver.y = 200.0;
    $receiver.radius = 100.0;
    $receiver.fill = null;
    $receiver.stroke = Colors.Web.red;
    return Unit;
  }
  function allRenderingTests$lambda_15($receiver) {
    $receiver.circle_107i6h$(allRenderingTests$lambda$lambda_15);
    return Unit;
  }
  function allRenderingTests$lambda$lambda_16($receiver) {
    $receiver.x = 200.0;
    $receiver.y = 200.0;
    $receiver.radius = 100.0;
    $receiver.fill = get_col(16631384);
    $receiver.stroke = get_col(788615).withAlpha_o5f5ne$(get_pct(50));
    $receiver.strokeWidth = 40.0;
    return Unit;
  }
  function allRenderingTests$lambda_16($receiver) {
    $receiver.circle_107i6h$(allRenderingTests$lambda$lambda_16);
    return Unit;
  }
  function allRenderingTests$lambda$lambda$lambda_2(closure$pos, closure$it) {
    return function ($receiver) {
      closure$pos.v = next(closure$pos.v);
      $receiver.moveTo_lu1900$(closure$pos.v.first, closure$pos.v.second);
      $receiver.arc_6p3vsx$(closure$pos.v.first, closure$pos.v.second, 25.0, 0.0, closure$it * (2 * math.PI / 8.0), false);
      $receiver.closePath();
      $receiver.fill = Colors.Web.grey;
      $receiver.stroke = null;
      return Unit;
    };
  }
  function allRenderingTests$lambda_17($receiver) {
    var pos = {v: new Pair(-25.0, 25.0)};
    var tmp$;
    tmp$ = (new IntRange(0, 15)).iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      $receiver.path_omiyse$(allRenderingTests$lambda$lambda$lambda_2(pos, element));
    }
    return Unit;
  }
  function allRenderingTests$lambda$lambda$lambda_3(closure$pos, closure$it) {
    return function ($receiver) {
      closure$pos.v = next(closure$pos.v);
      $receiver.moveTo_lu1900$(closure$pos.v.first, closure$pos.v.second);
      $receiver.arc_6p3vsx$(closure$pos.v.first, closure$pos.v.second, 25.0, 0.0, (-closure$it | 0) * (2 * math.PI / 8.0), false);
      $receiver.closePath();
      $receiver.fill = Colors.Web.grey;
      $receiver.stroke = null;
      return Unit;
    };
  }
  function allRenderingTests$lambda_18($receiver) {
    var pos = {v: new Pair(-25.0, 25.0)};
    var tmp$;
    tmp$ = (new IntRange(0, 15)).iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      $receiver.path_omiyse$(allRenderingTests$lambda$lambda$lambda_3(pos, element));
    }
    return Unit;
  }
  function allRenderingTests$lambda$lambda$lambda_4(closure$pos, closure$it) {
    return function ($receiver) {
      closure$pos.v = next(closure$pos.v);
      $receiver.moveTo_lu1900$(closure$pos.v.first, closure$pos.v.second);
      $receiver.arc_6p3vsx$(closure$pos.v.first, closure$pos.v.second, 25.0, 0.0, closure$it * (2 * math.PI / 8.0), true);
      $receiver.closePath();
      $receiver.fill = Colors.Web.grey;
      $receiver.stroke = null;
      return Unit;
    };
  }
  function allRenderingTests$lambda_19($receiver) {
    var pos = {v: new Pair(-25.0, 25.0)};
    var tmp$;
    tmp$ = (new IntRange(0, 15)).iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      $receiver.path_omiyse$(allRenderingTests$lambda$lambda$lambda_4(pos, element));
    }
    return Unit;
  }
  function allRenderingTests$lambda$lambda$lambda_5(closure$pos, closure$it) {
    return function ($receiver) {
      closure$pos.v = next(closure$pos.v);
      $receiver.moveTo_lu1900$(closure$pos.v.first, closure$pos.v.second);
      $receiver.arc_6p3vsx$(closure$pos.v.first, closure$pos.v.second, 25.0, 0.0, (-closure$it | 0) * (2 * math.PI / 8.0), true);
      $receiver.closePath();
      $receiver.fill = Colors.Web.grey;
      $receiver.stroke = null;
      return Unit;
    };
  }
  function allRenderingTests$lambda_20($receiver) {
    var pos = {v: new Pair(-25.0, 25.0)};
    var tmp$;
    tmp$ = (new IntRange(0, 15)).iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      $receiver.path_omiyse$(allRenderingTests$lambda$lambda$lambda_5(pos, element));
    }
    return Unit;
  }
  function allRenderingTests$lambda$lambda$lambda_6(closure$pos, closure$posNeg, closure$it) {
    return function ($receiver) {
      closure$pos.v = next(closure$pos.v);
      closure$posNeg.v *= -1;
      $receiver.moveTo_lu1900$(closure$pos.v.first, closure$pos.v.second);
      $receiver.arc_6p3vsx$(closure$pos.v.first, closure$pos.v.second, 25.0, closure$it * closure$posNeg.v / 10.0, -closure$posNeg.v * closure$it * (2 * math.PI / 8.0), false);
      $receiver.closePath();
      $receiver.fill = Colors.Web.grey;
      $receiver.stroke = null;
      return Unit;
    };
  }
  function allRenderingTests$lambda_21($receiver) {
    var pos = {v: new Pair(-25.0, 25.0)};
    var posNeg = {v: 1.0};
    var tmp$;
    tmp$ = (new IntRange(0, 15)).iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      $receiver.path_omiyse$(allRenderingTests$lambda$lambda$lambda_6(pos, posNeg, element));
    }
    return Unit;
  }
  function allRenderingTests$lambda$lambda$lambda_7(closure$pos, closure$posNeg, closure$it) {
    return function ($receiver) {
      closure$pos.v = next(closure$pos.v);
      closure$posNeg.v *= -1;
      $receiver.moveTo_lu1900$(closure$pos.v.first, closure$pos.v.second);
      $receiver.arc_6p3vsx$(closure$pos.v.first, closure$pos.v.second, 25.0, closure$it * closure$posNeg.v / 10.0, -closure$posNeg.v * closure$it * (2 * math.PI / 8.0), true);
      $receiver.closePath();
      $receiver.fill = Colors.Web.grey;
      $receiver.stroke = null;
      return Unit;
    };
  }
  function allRenderingTests$lambda_22($receiver) {
    var pos = {v: new Pair(-25.0, 25.0)};
    var posNeg = {v: 1.0};
    var tmp$;
    tmp$ = (new IntRange(0, 15)).iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      $receiver.path_omiyse$(allRenderingTests$lambda$lambda$lambda_7(pos, posNeg, element));
    }
    return Unit;
  }
  function allRenderingTests$lambda$lambda$lambda_8(closure$pos, closure$posNeg, closure$it) {
    return function ($receiver) {
      closure$pos.v = next(closure$pos.v);
      closure$posNeg.v *= -1;
      $receiver.moveTo_lu1900$(closure$pos.v.first - 15.0, closure$pos.v.second - 15.0);
      $receiver.lineTo_lu1900$(closure$pos.v.first - 15.0, closure$pos.v.second - 5.0);
      $receiver.arc_6p3vsx$(closure$pos.v.first, closure$pos.v.second, 25.0, closure$it * closure$posNeg.v / 10.0, -closure$posNeg.v * closure$it * (2 * math.PI / 8.0), false);
      $receiver.lineTo_lu1900$(closure$pos.v.first + 15.0, closure$pos.v.second + 15.0);
      $receiver.closePath();
      $receiver.fill = Colors.Web.grey;
      $receiver.stroke = Colors.Web.blue;
      return Unit;
    };
  }
  function allRenderingTests$lambda_23($receiver) {
    var pos = {v: new Pair(-25.0, 25.0)};
    var posNeg = {v: 1.0};
    var tmp$;
    tmp$ = (new IntRange(0, 15)).iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      $receiver.path_omiyse$(allRenderingTests$lambda$lambda$lambda_8(pos, posNeg, element));
    }
    return Unit;
  }
  function allRenderingTests$lambda$lambda$lambda_9(closure$pos, closure$posNeg, closure$it) {
    return function ($receiver) {
      closure$pos.v = next(closure$pos.v);
      closure$posNeg.v *= -1;
      $receiver.moveTo_lu1900$(closure$pos.v.first - 15.0, closure$pos.v.second - 15.0);
      $receiver.lineTo_lu1900$(closure$pos.v.first - 15.0, closure$pos.v.second - 5.0);
      $receiver.arc_6p3vsx$(closure$pos.v.first, closure$pos.v.second, 25.0, closure$it * closure$posNeg.v / 10.0, -closure$posNeg.v * closure$it * (2 * math.PI / 8.0), true);
      $receiver.lineTo_lu1900$(closure$pos.v.first + 15.0, closure$pos.v.second + 15.0);
      $receiver.closePath();
      $receiver.fill = Colors.Web.grey;
      $receiver.stroke = Colors.Web.blue;
      return Unit;
    };
  }
  function allRenderingTests$lambda_24($receiver) {
    var pos = {v: new Pair(-25.0, 25.0)};
    var posNeg = {v: 1.0};
    var tmp$;
    tmp$ = (new IntRange(0, 15)).iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      $receiver.path_omiyse$(allRenderingTests$lambda$lambda$lambda_9(pos, posNeg, element));
    }
    return Unit;
  }
  function allRenderingTests$lambda$lambda_17($receiver) {
    $receiver.moveTo_lu1900$(0.0, 0.0);
    $receiver.lineTo_lu1900$(20.0, 80.0);
    $receiver.moveTo_lu1900$(60.0, 70.0);
    $receiver.lineTo_lu1900$(20.0, 80.0);
    $receiver.arc_6p3vsx$(0.0, 60.0, 20.0, 0.0, 40.0, false);
    $receiver.lineTo_lu1900$(100.0, 112.0);
    $receiver.lineTo_lu1900$(120.0, 180.0);
    $receiver.moveTo_lu1900$(160.0, 170.0);
    $receiver.lineTo_lu1900$(120.0, 180.0);
    $receiver.arc_6p3vsx$(100.0, 160.0, 20.0, 0.0, -40.0, false);
    $receiver.lineTo_lu1900$(200.0, 212.0);
    $receiver.lineTo_lu1900$(220.0, 280.0);
    $receiver.moveTo_lu1900$(260.0, 270.0);
    $receiver.lineTo_lu1900$(220.0, 280.0);
    $receiver.arc_6p3vsx$(200.0, 260.0, 20.0, 0.0, 2.0, false);
    $receiver.lineTo_lu1900$(300.0, 312.0);
    $receiver.closePath();
    $receiver.fill = Colors.Web.grey;
    $receiver.stroke = Colors.Web.blue;
    return Unit;
  }
  function allRenderingTests$lambda_25($receiver) {
    $receiver.path_omiyse$(allRenderingTests$lambda$lambda_17);
    return Unit;
  }
  function allRenderingTests$lambda$lambda_18($receiver) {
    $receiver.moveTo_lu1900$(20.0, 20.0);
    $receiver.lineTo_lu1900$(40.0, 40.0);
    $receiver.lineTo_lu1900$(60.0, 20.0);
    $receiver.moveTo_lu1900$(80.0, 40.0);
    $receiver.lineTo_lu1900$(100.0, 20.0);
    $receiver.stroke = Colors.Web.red;
    return Unit;
  }
  function allRenderingTests$lambda_26($receiver) {
    $receiver.path_omiyse$(allRenderingTests$lambda$lambda_18);
    return Unit;
  }
  function allRenderingTests$lambda$lambda_19($receiver) {
    $receiver.rect_6y0v78$(10.0, 10.0, 200.0, 100.0);
    $receiver.fill = Colors.Web.red;
    return Unit;
  }
  function allRenderingTests$lambda_27($receiver) {
    $receiver.path_omiyse$(allRenderingTests$lambda$lambda_19);
    return Unit;
  }
  function allRenderingTests$lambda$lambda_20($receiver) {
    $receiver.x = 50.0;
    $receiver.y = 50.0;
    $receiver.radius = 50.0;
    $receiver.fill = Colors.Web.black;
    $receiver.visible = false;
    return Unit;
  }
  function allRenderingTests$lambda$lambda_21($receiver) {
    $receiver.x = 150.0;
    $receiver.y = 50.0;
    $receiver.radius = 50.0;
    $receiver.fill = Colors.Web.black;
    return Unit;
  }
  function allRenderingTests$lambda_28($receiver) {
    $receiver.circle_107i6h$(allRenderingTests$lambda$lambda_20);
    $receiver.circle_107i6h$(allRenderingTests$lambda$lambda_21);
    return Unit;
  }
  function allRenderingTests$lambda$lambda_22($receiver) {
    $receiver.x = 50.0;
    $receiver.y = 50.0;
    $receiver.radius = 50.0;
    $receiver.fill = Colors.Web.black;
    return Unit;
  }
  function allRenderingTests$lambda_29($receiver) {
    $receiver.activeLayer.visible = false;
    $receiver.circle_107i6h$(allRenderingTests$lambda$lambda_22);
    return Unit;
  }
  var allRenderingTests;
  function renderingTest$lambda(closure$init) {
    return function ($receiver) {
      $receiver.width = 400.0;
      $receiver.height = 400.0;
      closure$init($receiver);
      return Unit;
    };
  }
  function renderingTest(name, init) {
    var viz_0 = viz(renderingTest$lambda(init));
    return new RenderingTest(name, viz_0);
  }
  function StateManager() {
    this.status = StateManagerStatus$REST_getInstance();
    this.properties = ArrayList_init();
  }
  StateManager.prototype.addStateProperty_z6ptt4$ = function (property) {
    this.properties.add_11rb$(property);
  };
  StateManager.prototype.percentToState_14dthe$ = function (percent) {
    this.status = StateManagerStatus$UPDATE_PROPERTIES_getInstance();
    var tmp$;
    tmp$ = this.properties.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      element.setPercent_14dthe$(percent);
    }
    this.status = StateManagerStatus$REST_getInstance();
  };
  StateManager.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'StateManager',
    interfaces: []
  };
  function StateManagerStatus(name, ordinal) {
    Enum.call(this);
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function StateManagerStatus_initFields() {
    StateManagerStatus_initFields = function () {
    };
    StateManagerStatus$REST_instance = new StateManagerStatus('REST', 0);
    StateManagerStatus$RECORD_instance = new StateManagerStatus('RECORD', 1);
    StateManagerStatus$UPDATE_PROPERTIES_instance = new StateManagerStatus('UPDATE_PROPERTIES', 2);
  }
  var StateManagerStatus$REST_instance;
  function StateManagerStatus$REST_getInstance() {
    StateManagerStatus_initFields();
    return StateManagerStatus$REST_instance;
  }
  var StateManagerStatus$RECORD_instance;
  function StateManagerStatus$RECORD_getInstance() {
    StateManagerStatus_initFields();
    return StateManagerStatus$RECORD_instance;
  }
  var StateManagerStatus$UPDATE_PROPERTIES_instance;
  function StateManagerStatus$UPDATE_PROPERTIES_getInstance() {
    StateManagerStatus_initFields();
    return StateManagerStatus$UPDATE_PROPERTIES_instance;
  }
  StateManagerStatus.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'StateManagerStatus',
    interfaces: [Enum]
  };
  function StateManagerStatus$values() {
    return [StateManagerStatus$REST_getInstance(), StateManagerStatus$RECORD_getInstance(), StateManagerStatus$UPDATE_PROPERTIES_getInstance()];
  }
  StateManagerStatus.values = StateManagerStatus$values;
  function StateManagerStatus$valueOf(name) {
    switch (name) {
      case 'REST':
        return StateManagerStatus$REST_getInstance();
      case 'RECORD':
        return StateManagerStatus$RECORD_getInstance();
      case 'UPDATE_PROPERTIES':
        return StateManagerStatus$UPDATE_PROPERTIES_getInstance();
      default:throwISE('No enum constant io.data2viz.viz.StateManagerStatus.' + name);
    }
  }
  StateManagerStatus.valueOf_61zpoe$ = StateManagerStatus$valueOf;
  function StateProperty() {
  }
  StateProperty.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'StateProperty',
    interfaces: []
  };
  function Style() {
  }
  Style.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Style',
    interfaces: []
  };
  function StyleImpl() {
    this.fill_l1sct0$_0 = null;
    this.stroke_1w52jt$_0 = null;
    this.strokeWidth_l4w949$_0 = 1.0;
    this.anchor_eyj0fu$_0 = TextAnchor$START_getInstance();
    this.baseline_1osq8m$_0 = TextAlignmentBaseline$BASELINE_getInstance();
  }
  Object.defineProperty(StyleImpl.prototype, 'fill', {
    get: function () {
      return this.fill_l1sct0$_0;
    },
    set: function (fill) {
      this.fill_l1sct0$_0 = fill;
    }
  });
  Object.defineProperty(StyleImpl.prototype, 'stroke', {
    get: function () {
      return this.stroke_1w52jt$_0;
    },
    set: function (stroke) {
      this.stroke_1w52jt$_0 = stroke;
    }
  });
  Object.defineProperty(StyleImpl.prototype, 'strokeWidth', {
    get: function () {
      return this.strokeWidth_l4w949$_0;
    },
    set: function (strokeWidth) {
      this.strokeWidth_l4w949$_0 = strokeWidth;
    }
  });
  Object.defineProperty(StyleImpl.prototype, 'anchor', {
    get: function () {
      return this.anchor_eyj0fu$_0;
    },
    set: function (anchor) {
      this.anchor_eyj0fu$_0 = anchor;
    }
  });
  Object.defineProperty(StyleImpl.prototype, 'baseline', {
    get: function () {
      return this.baseline_1osq8m$_0;
    },
    set: function (baseline) {
      this.baseline_1osq8m$_0 = baseline;
    }
  });
  StyleImpl.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'StyleImpl',
    interfaces: [Style]
  };
  function HierarchicalStyle(parent) {
    this.parent = parent;
    this.style_0 = null;
    this.fillSet_0 = false;
    this.strokeSet_0 = false;
    this.strokeWidthSet_0 = false;
    this.anchorSet_0 = false;
    this.baselineSet_0 = false;
  }
  Object.defineProperty(HierarchicalStyle.prototype, 'fill', {
    get: function () {
      var tmp$;
      return this.fillSet_0 ? ensureNotNull(this.style_0).fill : (tmp$ = this.parent) != null ? tmp$.fill : null;
    },
    set: function (value) {
      var tmp$;
      if (this.style_0 == null)
        this.style_0 = new StyleImpl();
      this.fillSet_0 = true;
      (tmp$ = this.style_0) != null ? (tmp$.fill = value) : null;
    }
  });
  Object.defineProperty(HierarchicalStyle.prototype, 'stroke', {
    get: function () {
      var tmp$;
      return this.strokeSet_0 ? ensureNotNull(this.style_0).stroke : (tmp$ = this.parent) != null ? tmp$.stroke : null;
    },
    set: function (value) {
      var tmp$;
      if (this.style_0 == null)
        this.style_0 = new StyleImpl();
      this.strokeSet_0 = true;
      (tmp$ = this.style_0) != null ? (tmp$.stroke = value) : null;
    }
  });
  Object.defineProperty(HierarchicalStyle.prototype, 'strokeWidth', {
    get: function () {
      var tmp$, tmp$_0;
      return this.strokeWidthSet_0 ? (tmp$ = this.style_0) != null ? tmp$.strokeWidth : null : (tmp$_0 = this.parent) != null ? tmp$_0.strokeWidth : null;
    },
    set: function (value) {
      var tmp$;
      if (this.style_0 == null)
        this.style_0 = new StyleImpl();
      this.strokeWidthSet_0 = true;
      (tmp$ = this.style_0) != null ? (tmp$.strokeWidth = value) : null;
    }
  });
  Object.defineProperty(HierarchicalStyle.prototype, 'anchor', {
    get: function () {
      var tmp$, tmp$_0;
      return this.anchorSet_0 ? ensureNotNull((tmp$ = this.style_0) != null ? tmp$.anchor : null) : ensureNotNull((tmp$_0 = this.parent) != null ? tmp$_0.anchor : null);
    },
    set: function (value) {
      var tmp$;
      if (this.style_0 == null)
        this.style_0 = new StyleImpl();
      this.anchorSet_0 = true;
      (tmp$ = this.style_0) != null ? (tmp$.anchor = value) : null;
    }
  });
  Object.defineProperty(HierarchicalStyle.prototype, 'baseline', {
    get: function () {
      var tmp$, tmp$_0;
      return this.baselineSet_0 ? ensureNotNull((tmp$ = this.style_0) != null ? tmp$.baseline : null) : ensureNotNull((tmp$_0 = this.parent) != null ? tmp$_0.baseline : null);
    },
    set: function (value) {
      var tmp$;
      if (this.style_0 == null)
        this.style_0 = new StyleImpl();
      this.baselineSet_0 = true;
      (tmp$ = this.style_0) != null ? (tmp$.baseline = value) : null;
    }
  });
  HierarchicalStyle.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'HierarchicalStyle',
    interfaces: [Style]
  };
  function TextAnchor(name, ordinal) {
    Enum.call(this);
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function TextAnchor_initFields() {
    TextAnchor_initFields = function () {
    };
    TextAnchor$START_instance = new TextAnchor('START', 0);
    TextAnchor$MIDDLE_instance = new TextAnchor('MIDDLE', 1);
    TextAnchor$END_instance = new TextAnchor('END', 2);
  }
  var TextAnchor$START_instance;
  function TextAnchor$START_getInstance() {
    TextAnchor_initFields();
    return TextAnchor$START_instance;
  }
  var TextAnchor$MIDDLE_instance;
  function TextAnchor$MIDDLE_getInstance() {
    TextAnchor_initFields();
    return TextAnchor$MIDDLE_instance;
  }
  var TextAnchor$END_instance;
  function TextAnchor$END_getInstance() {
    TextAnchor_initFields();
    return TextAnchor$END_instance;
  }
  TextAnchor.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'TextAnchor',
    interfaces: [Enum]
  };
  function TextAnchor$values() {
    return [TextAnchor$START_getInstance(), TextAnchor$MIDDLE_getInstance(), TextAnchor$END_getInstance()];
  }
  TextAnchor.values = TextAnchor$values;
  function TextAnchor$valueOf(name) {
    switch (name) {
      case 'START':
        return TextAnchor$START_getInstance();
      case 'MIDDLE':
        return TextAnchor$MIDDLE_getInstance();
      case 'END':
        return TextAnchor$END_getInstance();
      default:throwISE('No enum constant io.data2viz.viz.TextAnchor.' + name);
    }
  }
  TextAnchor.valueOf_61zpoe$ = TextAnchor$valueOf;
  function TextAlignmentBaseline(name, ordinal) {
    Enum.call(this);
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function TextAlignmentBaseline_initFields() {
    TextAlignmentBaseline_initFields = function () {
    };
    TextAlignmentBaseline$HANGING_instance = new TextAlignmentBaseline('HANGING', 0);
    TextAlignmentBaseline$MIDDLE_instance = new TextAlignmentBaseline('MIDDLE', 1);
    TextAlignmentBaseline$BASELINE_instance = new TextAlignmentBaseline('BASELINE', 2);
  }
  var TextAlignmentBaseline$HANGING_instance;
  function TextAlignmentBaseline$HANGING_getInstance() {
    TextAlignmentBaseline_initFields();
    return TextAlignmentBaseline$HANGING_instance;
  }
  var TextAlignmentBaseline$MIDDLE_instance;
  function TextAlignmentBaseline$MIDDLE_getInstance() {
    TextAlignmentBaseline_initFields();
    return TextAlignmentBaseline$MIDDLE_instance;
  }
  var TextAlignmentBaseline$BASELINE_instance;
  function TextAlignmentBaseline$BASELINE_getInstance() {
    TextAlignmentBaseline_initFields();
    return TextAlignmentBaseline$BASELINE_instance;
  }
  TextAlignmentBaseline.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'TextAlignmentBaseline',
    interfaces: [Enum]
  };
  function TextAlignmentBaseline$values() {
    return [TextAlignmentBaseline$HANGING_getInstance(), TextAlignmentBaseline$MIDDLE_getInstance(), TextAlignmentBaseline$BASELINE_getInstance()];
  }
  TextAlignmentBaseline.values = TextAlignmentBaseline$values;
  function TextAlignmentBaseline$valueOf(name) {
    switch (name) {
      case 'HANGING':
        return TextAlignmentBaseline$HANGING_getInstance();
      case 'MIDDLE':
        return TextAlignmentBaseline$MIDDLE_getInstance();
      case 'BASELINE':
        return TextAlignmentBaseline$BASELINE_getInstance();
      default:throwISE('No enum constant io.data2viz.viz.TextAlignmentBaseline.' + name);
    }
  }
  TextAlignmentBaseline.valueOf_61zpoe$ = TextAlignmentBaseline$valueOf;
  function TextNode() {
    Node.call(this);
    this.transform_eg0kf5$_0 = null;
    this.x = 0.0;
    this.y = 0.0;
    this.textContent = 'Type something';
    this.fontSize = 12.0;
    this.fontFamily = FontFamily$Companion_getInstance().SANS_SERIF;
    this.fontWeight = FontWeight$NORMAL_getInstance();
    this.fontStyle = FontPosture$NORMAL_getInstance();
  }
  Object.defineProperty(TextNode.prototype, 'transform', {
    get: function () {
      return this.transform_eg0kf5$_0;
    },
    set: function (transform) {
      this.transform_eg0kf5$_0 = transform;
    }
  });
  TextNode.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'TextNode',
    interfaces: [HasTransform, HasStroke, HasFill, Node]
  };
  function FontFamily(name) {
    FontFamily$Companion_getInstance();
    this.name = name;
  }
  function FontFamily$Companion() {
    FontFamily$Companion_instance = this;
    this.MONOSPACE = new FontFamily('monospace');
    this.SANS_SERIF = new FontFamily('sans-serif');
    this.SERIF = new FontFamily('serif');
  }
  FontFamily$Companion.prototype.specifiedFont_61zpoe$ = function (name) {
    return new FontFamily(name);
  };
  FontFamily$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var FontFamily$Companion_instance = null;
  function FontFamily$Companion_getInstance() {
    if (FontFamily$Companion_instance === null) {
      new FontFamily$Companion();
    }
    return FontFamily$Companion_instance;
  }
  FontFamily.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'FontFamily',
    interfaces: []
  };
  function FontWeight(name, ordinal) {
    Enum.call(this);
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function FontWeight_initFields() {
    FontWeight_initFields = function () {
    };
    FontWeight$BOLD_instance = new FontWeight('BOLD', 0);
    FontWeight$NORMAL_instance = new FontWeight('NORMAL', 1);
  }
  var FontWeight$BOLD_instance;
  function FontWeight$BOLD_getInstance() {
    FontWeight_initFields();
    return FontWeight$BOLD_instance;
  }
  var FontWeight$NORMAL_instance;
  function FontWeight$NORMAL_getInstance() {
    FontWeight_initFields();
    return FontWeight$NORMAL_instance;
  }
  FontWeight.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'FontWeight',
    interfaces: [Enum]
  };
  function FontWeight$values() {
    return [FontWeight$BOLD_getInstance(), FontWeight$NORMAL_getInstance()];
  }
  FontWeight.values = FontWeight$values;
  function FontWeight$valueOf(name) {
    switch (name) {
      case 'BOLD':
        return FontWeight$BOLD_getInstance();
      case 'NORMAL':
        return FontWeight$NORMAL_getInstance();
      default:throwISE('No enum constant io.data2viz.viz.FontWeight.' + name);
    }
  }
  FontWeight.valueOf_61zpoe$ = FontWeight$valueOf;
  function FontPosture(name, ordinal) {
    Enum.call(this);
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function FontPosture_initFields() {
    FontPosture_initFields = function () {
    };
    FontPosture$ITALIC_instance = new FontPosture('ITALIC', 0);
    FontPosture$NORMAL_instance = new FontPosture('NORMAL', 1);
  }
  var FontPosture$ITALIC_instance;
  function FontPosture$ITALIC_getInstance() {
    FontPosture_initFields();
    return FontPosture$ITALIC_instance;
  }
  var FontPosture$NORMAL_instance;
  function FontPosture$NORMAL_getInstance() {
    FontPosture_initFields();
    return FontPosture$NORMAL_instance;
  }
  FontPosture.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'FontPosture',
    interfaces: [Enum]
  };
  function FontPosture$values() {
    return [FontPosture$ITALIC_getInstance(), FontPosture$NORMAL_getInstance()];
  }
  FontPosture.values = FontPosture$values;
  function FontPosture$valueOf(name) {
    switch (name) {
      case 'ITALIC':
        return FontPosture$ITALIC_getInstance();
      case 'NORMAL':
        return FontPosture$NORMAL_getInstance();
      default:throwISE('No enum constant io.data2viz.viz.FontPosture.' + name);
    }
  }
  FontPosture.valueOf_61zpoe$ = FontPosture$valueOf;
  function Viz(activeLayer) {
    if (activeLayer === void 0)
      activeLayer = new Layer();
    this.activeLayer = activeLayer;
    this.$delegate_jlwl53$_0 = activeLayer;
    this.activeLayer.parent = this;
    this.style_0 = new StyleImpl();
    this.config = new VizConfig();
    this.width_p2f4jt$_0 = 100.0;
    this.height_d5hk3m$_0 = 100.0;
    this.layers = mutableListOf([this.activeLayer]);
    this.resizeBehavior_0 = null;
    this.renderer_9w8zxa$_0 = this.renderer_9w8zxa$_0;
    this.animationTimers_8be2vx$ = ArrayList_init();
  }
  Object.defineProperty(Viz.prototype, 'width', {
    get: function () {
      return this.width_p2f4jt$_0;
    },
    set: function (width) {
      this.width_p2f4jt$_0 = width;
    }
  });
  Object.defineProperty(Viz.prototype, 'height', {
    get: function () {
      return this.height_d5hk3m$_0;
    },
    set: function (height) {
      this.height_d5hk3m$_0 = height;
    }
  });
  Object.defineProperty(Viz.prototype, 'renderer', {
    get: function () {
      if (this.renderer_9w8zxa$_0 == null)
        return throwUPAE('renderer');
      return this.renderer_9w8zxa$_0;
    },
    set: function (renderer) {
      this.renderer_9w8zxa$_0 = renderer;
    }
  });
  Viz.prototype.render = function () {
    this.renderer.render();
  };
  Viz.prototype.startAnimations = function () {
    this.renderer.startAnimations();
  };
  Viz.prototype.stopAnimations = function () {
    this.renderer.stopAnimations();
  };
  Viz.prototype.animation_o5zv02$ = function (block) {
    this.animationTimers_8be2vx$.add_11rb$(block);
  };
  function Viz$onFrame$lambda(closure$block) {
    return function ($receiver, it) {
      closure$block(it);
      return Unit;
    };
  }
  Viz.prototype.onFrame_huw4wd$ = function (block) {
    this.animation_o5zv02$(Viz$onFrame$lambda(block));
  };
  Viz.prototype.onResize_tuav61$ = function (block) {
    this.resizeBehavior_0 = block;
  };
  Viz.prototype.resize_lu1900$ = function (newWidth, newHeight) {
    var tmp$;
    (tmp$ = this.resizeBehavior_0) != null ? tmp$(newWidth, newHeight) : null;
  };
  Viz.prototype.layer = function () {
    var $receiver = new Layer();
    $receiver.parent = this;
    var layer = $receiver;
    this.layers.add_11rb$(layer);
    this.activeLayer = layer;
    return layer;
  };
  Object.defineProperty(Viz.prototype, 'fill', {
    get: function () {
      return this.style_0.fill;
    },
    set: function (value) {
      this.style_0.fill = value;
    }
  });
  Object.defineProperty(Viz.prototype, 'stroke', {
    get: function () {
      return this.style_0.stroke;
    },
    set: function (value) {
      this.style_0.stroke = value;
    }
  });
  Object.defineProperty(Viz.prototype, 'strokeWidth', {
    get: function () {
      return this.style_0.strokeWidth;
    },
    set: function (value) {
      this.style_0.strokeWidth = value;
    }
  });
  Object.defineProperty(Viz.prototype, 'anchor', {
    get: function () {
      return this.style_0.anchor;
    },
    set: function (value) {
      this.style_0.anchor = value;
    }
  });
  Object.defineProperty(Viz.prototype, 'baseline', {
    get: function () {
      return this.style_0.baseline;
    },
    set: function (value) {
      this.style_0.baseline = value;
    }
  });
  Viz.prototype.add_vetai8$ = function (node) {
    return this.$delegate_jlwl53$_0.add_vetai8$(node);
  };
  Viz.prototype.circle_107i6h$ = function (init) {
    return this.$delegate_jlwl53$_0.circle_107i6h$(init);
  };
  Viz.prototype.clear = function () {
    return this.$delegate_jlwl53$_0.clear();
  };
  Viz.prototype.group_mdx85a$ = function (init) {
    return this.$delegate_jlwl53$_0.group_mdx85a$(init);
  };
  Viz.prototype.line_e8vvwz$ = function (init) {
    return this.$delegate_jlwl53$_0.line_e8vvwz$(init);
  };
  Viz.prototype.path_omiyse$ = function (init) {
    return this.$delegate_jlwl53$_0.path_omiyse$(init);
  };
  Viz.prototype.rect_afayqr$ = function (init) {
    return this.$delegate_jlwl53$_0.rect_afayqr$(init);
  };
  Viz.prototype.remove_vetai8$ = function (node) {
    return this.$delegate_jlwl53$_0.remove_vetai8$(node);
  };
  Viz.prototype.text_6q900q$ = function (init) {
    return this.$delegate_jlwl53$_0.text_6q900q$(init);
  };
  Viz.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Viz',
    interfaces: [HasSize, HasChildren]
  };
  function viz(init) {
    var $receiver = new Viz();
    init($receiver);
    return $receiver;
  }
  function StateableElement() {
  }
  StateableElement.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'StateableElement',
    interfaces: []
  };
  function Transform() {
    this.translate = null;
    this.rotate = null;
  }
  Transform.prototype.translate_lu1900$ = function (x, y) {
    if (x === void 0)
      x = 0.0;
    if (y === void 0)
      y = 0.0;
    this.translate = new Translation(x, y);
  };
  Transform.prototype.rotate_14dthe$ = function (delta) {
    this.rotate = new Rotation(delta);
  };
  Transform.prototype.plusAssign_d30a0u$ = function (transform) {
    var tmp$, tmp$_0;
    if ((tmp$ = this.translate) != null) {
      var tmp$_1, tmp$_2, tmp$_3, tmp$_4;
      tmp$.x += (tmp$_2 = (tmp$_1 = transform.translate) != null ? tmp$_1.x : null) != null ? tmp$_2 : 0.0;
      tmp$.y += (tmp$_4 = (tmp$_3 = transform.translate) != null ? tmp$_3.y : null) != null ? tmp$_4 : 0.0;
    }
    if ((tmp$_0 = this.rotate) != null) {
      var tmp$_5, tmp$_6;
      tmp$_0.delta += (tmp$_6 = (tmp$_5 = transform.rotate) != null ? tmp$_5.delta : null) != null ? tmp$_6 : 0.0;
    }
  };
  Transform.prototype.minusAssign_d30a0u$ = function (transform) {
    var tmp$, tmp$_0;
    if ((tmp$ = this.translate) != null) {
      var tmp$_1, tmp$_2, tmp$_3, tmp$_4;
      tmp$.x -= (tmp$_2 = (tmp$_1 = transform.translate) != null ? tmp$_1.x : null) != null ? tmp$_2 : 0.0;
      tmp$.y -= (tmp$_4 = (tmp$_3 = transform.translate) != null ? tmp$_3.y : null) != null ? tmp$_4 : 0.0;
    }
    if ((tmp$_0 = this.rotate) != null) {
      var tmp$_5, tmp$_6;
      tmp$_0.delta -= (tmp$_6 = (tmp$_5 = transform.rotate) != null ? tmp$_5.delta : null) != null ? tmp$_6 : 0.0;
    }
  };
  Transform.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Transform',
    interfaces: []
  };
  function Translation(x, y) {
    if (x === void 0)
      x = 0.0;
    if (y === void 0)
      y = 0.0;
    this.x = x;
    this.y = y;
  }
  Translation.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Translation',
    interfaces: []
  };
  Translation.prototype.component1 = function () {
    return this.x;
  };
  Translation.prototype.component2 = function () {
    return this.y;
  };
  Translation.prototype.copy_lu1900$ = function (x, y) {
    return new Translation(x === void 0 ? this.x : x, y === void 0 ? this.y : y);
  };
  Translation.prototype.toString = function () {
    return 'Translation(x=' + Kotlin.toString(this.x) + (', y=' + Kotlin.toString(this.y)) + ')';
  };
  Translation.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.x) | 0;
    result = result * 31 + Kotlin.hashCode(this.y) | 0;
    return result;
  };
  Translation.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.x, other.x) && Kotlin.equals(this.y, other.y)))));
  };
  function Rotation(delta) {
    if (delta === void 0)
      delta = 0.0;
    this.delta = delta;
  }
  Rotation.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Rotation',
    interfaces: []
  };
  Rotation.prototype.component1 = function () {
    return this.delta;
  };
  Rotation.prototype.copy_14dthe$ = function (delta) {
    return new Rotation(delta === void 0 ? this.delta : delta);
  };
  Rotation.prototype.toString = function () {
    return 'Rotation(delta=' + Kotlin.toString(this.delta) + ')';
  };
  Rotation.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.delta) | 0;
    return result;
  };
  Rotation.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && Kotlin.equals(this.delta, other.delta))));
  };
  function HasStroke() {
  }
  HasStroke.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'HasStroke',
    interfaces: []
  };
  function HasFill() {
  }
  HasFill.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'HasFill',
    interfaces: []
  };
  function Margins(top, right, bottom, left) {
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
    this.hMargins = this.right + this.left;
    this.vMargins = this.top + this.bottom;
  }
  Margins.$metadata$ = {
    kind: Kind_CLASS,
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
  Margins.prototype.copy_6y0v78$ = function (top, right, bottom, left) {
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
  function HasTransform() {
  }
  HasTransform.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'HasTransform',
    interfaces: []
  };
  function VizConfig() {
    this.autoUpdate = true;
  }
  VizConfig.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'VizConfig',
    interfaces: []
  };
  function VizRenderer() {
  }
  VizRenderer.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'VizRenderer',
    interfaces: []
  };
  function bindRendererOn$lambda(closure$canvasId) {
    return function () {
      return 'No canvas in the document corresponding to ' + closure$canvasId;
    };
  }
  var IllegalArgumentException_init = Kotlin.kotlin.IllegalArgumentException_init_pdl1vj$;
  function bindRendererOn($receiver, canvasId) {
    var tmp$;
    var value = (tmp$ = document.getElementById(canvasId)) == null || Kotlin.isType(tmp$, HTMLCanvasElement) ? tmp$ : throwCCE();
    var requireNotNull$result;
    if (value == null) {
      var message = bindRendererOn$lambda(canvasId)();
      throw IllegalArgumentException_init(message.toString());
    }
     else {
      requireNotNull$result = value;
    }
    var canvas = requireNotNull$result;
    bindRendererOn_0($receiver, canvas);
  }
  function bindRendererOnNewCanvas($receiver) {
    var tmp$;
    var canvas = Kotlin.isType(tmp$ = document.createElement('canvas'), HTMLCanvasElement) ? tmp$ : throwCCE();
    var value = document.querySelector('body');
    var requireNotNull$result;
    if (value == null) {
      var message = 'Required value was null.';
      throw IllegalArgumentException_init(message.toString());
    }
     else {
      requireNotNull$result = value;
    }
    var body = requireNotNull$result;
    body.appendChild(canvas);
    bindRendererOn_0($receiver, canvas);
  }
  function bindRendererOn_0($receiver, canvas) {
    var tmp$;
    var context = Kotlin.isType(tmp$ = canvas.getContext('2d'), CanvasRenderingContext2D) ? tmp$ : throwCCE();
    context.canvas.width = numberToInt($receiver.width);
    context.canvas.height = numberToInt($receiver.height);
    var pixelRatio = getPixelRatio();
    if (pixelRatio > 1.0) {
      canvas.style.width = canvas.width.toString() + 'px';
      canvas.style.height = canvas.height.toString() + 'px';
      canvas.width = numberToInt(canvas.width * pixelRatio);
      canvas.height = numberToInt(canvas.height * pixelRatio);
      context.scale(pixelRatio, pixelRatio);
    }
    $receiver.renderer = new JsCanvasRenderer(context, $receiver);
    if ($receiver.config.autoUpdate) {
      $receiver.render();
      $receiver.startAnimations();
    }
  }
  function getPixelRatio() {
    var pixelRatio = 1.0;
    if (typeof window.devicePixelRatio !== 'undefined') {
      pixelRatio = window.devicePixelRatio;
    }
    return pixelRatio;
  }
  function JsCanvasRenderer(context, viz) {
    this.context = context;
    this.viz_4ojvu9$_0 = viz;
    this.animationTimers_0 = ArrayList_init();
  }
  Object.defineProperty(JsCanvasRenderer.prototype, 'viz', {
    get: function () {
      return this.viz_4ojvu9$_0;
    }
  });
  JsCanvasRenderer.prototype.render = function () {
    this.context.clearRect(0.0, 0.0, this.context.canvas.width, this.context.canvas.height);
    var tmp$;
    tmp$ = this.viz.layers.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      if (element.visible)
        render_0(element, this.context);
    }
  };
  function JsCanvasRenderer$startAnimations$lambda$lambda(closure$anim) {
    return function ($receiver, time) {
      closure$anim($receiver, time);
      return Unit;
    };
  }
  function JsCanvasRenderer$startAnimations$lambda(this$JsCanvasRenderer) {
    return function ($receiver, it) {
      this$JsCanvasRenderer.render();
      return Unit;
    };
  }
  JsCanvasRenderer.prototype.startAnimations = function () {
    if (!this.viz.animationTimers_8be2vx$.isEmpty()) {
      var tmp$;
      tmp$ = this.viz.animationTimers_8be2vx$.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        var $receiver = this.animationTimers_0;
        var element_0 = timer(void 0, void 0, JsCanvasRenderer$startAnimations$lambda$lambda(element));
        $receiver.add_11rb$(element_0);
      }
      var $receiver_0 = this.animationTimers_0;
      var element_1 = timer(void 0, void 0, JsCanvasRenderer$startAnimations$lambda(this));
      $receiver_0.add_11rb$(element_1);
    }
  };
  JsCanvasRenderer.prototype.stopAnimations = function () {
    var tmp$;
    tmp$ = this.animationTimers_0.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      element.stop();
    }
  };
  JsCanvasRenderer.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'JsCanvasRenderer',
    interfaces: [VizRenderer]
  };
  function render($receiver, context) {
    context.beginPath();
    context.arc($receiver.x, $receiver.y, $receiver.radius, 0.0, 2 * math_0.PI, false);
    if ($receiver.fill != null) {
      context.fill();
    }
    if ($receiver.stroke != null) {
      context.stroke();
    }
  }
  var IllegalStateException_init = Kotlin.kotlin.IllegalStateException_init_pdl1vj$;
  function toCanvasPaint($receiver, context) {
    if (Kotlin.isType($receiver, Color))
      return $receiver.rgba;
    else if (Kotlin.isType($receiver, LinearGradient))
      return toCanvasGradient($receiver, context);
    else if (Kotlin.isType($receiver, RadialGradient))
      return toCanvasGradient_0($receiver, context);
    else {
      throw IllegalStateException_init(('Unknown type :: ' + Kotlin.getKClassFromExpression($receiver)).toString());
    }
  }
  function toCanvasGradient($receiver, context) {
    var gradient = context.createLinearGradient($receiver.x1, $receiver.y1, $receiver.x2, $receiver.y2);
    var tmp$;
    tmp$ = $receiver.colorStops.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      gradient.addColorStop(element.percent.value, element.color.rgba);
    }
    return gradient;
  }
  function toCanvasGradient_0($receiver, context) {
    var gradient = context.createRadialGradient($receiver.cx, $receiver.cy, 0.0, $receiver.cx, $receiver.cy, $receiver.radius);
    var tmp$;
    tmp$ = $receiver.colorStops.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      gradient.addColorStop(element.percent.value, element.color.rgba);
    }
    return gradient;
  }
  function render_0($receiver, context) {
    var tmp$;
    tmp$ = $receiver.children.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      var tmp$_0, tmp$_1, tmp$_2, tmp$_3, tmp$_4;
      if (Kotlin.isType(element, HasTransform)) {
        if ((tmp$_0 = element.transform) != null) {
          var tmp$_5, tmp$_6, tmp$_7, tmp$_8, tmp$_9, tmp$_10;
          context.translate((tmp$_6 = (tmp$_5 = tmp$_0.translate) != null ? tmp$_5.x : null) != null ? tmp$_6 : 0.0, (tmp$_8 = (tmp$_7 = tmp$_0.translate) != null ? tmp$_7.y : null) != null ? tmp$_8 : 0.0);
          context.rotate((tmp$_10 = (tmp$_9 = tmp$_0.rotate) != null ? tmp$_9.delta : null) != null ? tmp$_10 : 0.0);
        }
      }
      if (Kotlin.isType(element, HasFill)) {
        context.fillStyle = (tmp$_1 = element.fill) != null ? toCanvasPaint(tmp$_1, context) : null;
      }
      if (Kotlin.isType(element, HasStroke)) {
        context.strokeStyle = (tmp$_2 = element.stroke) != null ? toCanvasPaint(tmp$_2, context) : null;
        context.lineWidth = (tmp$_3 = element.strokeWidth) != null ? tmp$_3 : 1.0;
      }
      if (element.visible) {
        if (Kotlin.isType(element, CircleNode))
          render(element, context);
        else if (Kotlin.isType(element, RectNode))
          render_3(element, context);
        else if (Kotlin.isType(element, GroupNode))
          render_0(element, context);
        else if (Kotlin.isType(element, PathNode))
          render_2(element, context);
        else if (Kotlin.isType(element, TextNode))
          render_4(element, context);
        else if (Kotlin.isType(element, LineNode))
          render_1(element, context);
        else {
          throw IllegalStateException_init(('Unknow type ' + Kotlin.getKClassFromExpression(element)).toString());
        }
      }
      if (Kotlin.isType(element, HasTransform)) {
        if ((tmp$_4 = element.transform) != null) {
          var tmp$_11, tmp$_12, tmp$_13, tmp$_14, tmp$_15, tmp$_16;
          context.translate(-((tmp$_12 = (tmp$_11 = tmp$_4.translate) != null ? tmp$_11.x : null) != null ? tmp$_12 : 0.0), -((tmp$_14 = (tmp$_13 = tmp$_4.translate) != null ? tmp$_13.y : null) != null ? tmp$_14 : 0.0));
          context.rotate(-((tmp$_16 = (tmp$_15 = tmp$_4.rotate) != null ? tmp$_15.delta : null) != null ? tmp$_16 : 0.0));
        }
      }
    }
    context.translate(0.0, 0.0);
  }
  function render_1($receiver, context) {
    context.beginPath();
    context.moveTo($receiver.x1, $receiver.y1);
    context.lineTo($receiver.x2, $receiver.y2);
    context.stroke();
  }
  function render_2($receiver, context) {
    var tmp$, tmp$_0;
    context.beginPath();
    var tmp$_1;
    tmp$_1 = $receiver.path.commands.iterator();
    while (tmp$_1.hasNext()) {
      var element = tmp$_1.next();
      if (Kotlin.isType(element, MoveTo))
        context.moveTo(element.x, element.y);
      else if (Kotlin.isType(element, LineTo))
        context.lineTo(element.x, element.y);
      else if (Kotlin.isType(element, QuadraticCurveTo))
        context.quadraticCurveTo(element.cpx, element.cpy, element.x, element.y);
      else if (Kotlin.isType(element, BezierCurveTo))
        context.bezierCurveTo(element.cpx1, element.cpy1, element.cpx2, element.cpy2, element.x, element.y);
      else if (Kotlin.isType(element, Arc))
        context.arc(element.centerX, element.centerY, element.radius, element.startAngle, element.endAngle, element.counterClockWise);
      else if (Kotlin.isType(element, ArcTo))
        context.arcTo(element.fromX, element.fromY, element.x, element.y, element.radius);
      else if (Kotlin.isType(element, ClosePath))
        context.closePath();
      else if (Kotlin.isType(element, RectCmd))
        context.rect(element.x, element.y, element.w, element.h);
      else {
        throw IllegalStateException_init(('Unknown canvas command: ' + Kotlin.getKClassFromExpression(element)).toString());
      }
    }
    if ((tmp$ = $receiver.fill) != null) {
      context.fillStyle = toCanvasPaint(tmp$, context);
      context.fill();
    }
    if ((tmp$_0 = $receiver.stroke) != null) {
      var tmp$_2;
      context.strokeStyle = toCanvasPaint(tmp$_0, context);
      context.lineWidth = (tmp$_2 = $receiver.strokeWidth) != null ? tmp$_2 : 1.0;
      context.stroke();
    }
  }
  function render_3($receiver, context) {
    var tmp$, tmp$_0;
    if ((tmp$ = $receiver.fill) != null) {
      context.fillStyle = toCanvasPaint(tmp$, context);
      context.fillRect($receiver.x, $receiver.y, $receiver.width, $receiver.height);
    }
    if ((tmp$_0 = $receiver.stroke) != null) {
      context.strokeStyle = toCanvasPaint(tmp$_0, context);
      context.strokeRect($receiver.x, $receiver.y, $receiver.width, $receiver.height);
    }
  }
  function render_4($receiver, context) {
    context.textAlign = get_js_0($receiver.anchor);
    context.textBaseline = get_js($receiver.baseline);
    context.font = get_js_2($receiver.fontStyle) + ' ' + get_js_1($receiver.fontWeight) + ' ' + $receiver.fontSize + 'px ' + $receiver.fontFamily.name;
    if ($receiver.fill != null) {
      context.fillText($receiver.textContent, $receiver.x, $receiver.y);
    }
    if ($receiver.stroke != null) {
      context.strokeText($receiver.textContent, $receiver.x, $receiver.y);
    }
  }
  function get_js($receiver) {
    switch ($receiver.name) {
      case 'BASELINE':
        return 'alphabetic';
      case 'HANGING':
        return 'hanging';
      case 'MIDDLE':
        return 'middle';
      default:return Kotlin.noWhenBranchMatched();
    }
  }
  function get_js_0($receiver) {
    switch ($receiver.name) {
      case 'START':
        return 'left';
      case 'END':
        return 'right';
      case 'MIDDLE':
        return 'center';
      default:return Kotlin.noWhenBranchMatched();
    }
  }
  function get_js_1($receiver) {
    switch ($receiver.name) {
      case 'NORMAL':
        return 'normal';
      case 'BOLD':
        return 'bold';
      default:return Kotlin.noWhenBranchMatched();
    }
  }
  function get_js_2($receiver) {
    switch ($receiver.name) {
      case 'ITALIC':
        return 'italic';
      case 'NORMAL':
        return 'normal';
      default:return Kotlin.noWhenBranchMatched();
    }
  }
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$viz = package$data2viz.viz || (package$data2viz.viz = {});
  package$viz.CircleNode = CircleNode;
  package$viz.TextAlign = TextAlign;
  package$viz.get_textAlign_lqwhv2$ = get_textAlign;
  package$viz.set_textAlign_wqzlt4$ = set_textAlign;
  package$viz.textAlign_39dvyb$ = textAlign;
  package$viz.GroupNode = GroupNode;
  package$viz.HasChildren = HasChildren;
  package$viz.Layer = Layer;
  package$viz.LineNode = LineNode;
  package$viz.Node = Node;
  package$viz.PathNode = PathNode;
  package$viz.RectNode = RectNode;
  package$viz.RenderingTest = RenderingTest;
  Object.defineProperty(package$viz, 'linesOfText', {
    get: function () {
      return linesOfText;
    }
  });
  Object.defineProperty(package$viz, 'fontSizeValue', {
    get: function () {
      return fontSizeValue;
    }
  });
  Object.defineProperty(package$viz, 'fontFamilyValue', {
    get: function () {
      return fontFamilyValue;
    }
  });
  Object.defineProperty(package$viz, 'allRenderingTests', {
    get: function () {
      return allRenderingTests;
    }
  });
  package$viz.renderingTest_3decbe$ = renderingTest;
  package$viz.StateManager = StateManager;
  Object.defineProperty(StateManagerStatus, 'REST', {
    get: StateManagerStatus$REST_getInstance
  });
  Object.defineProperty(StateManagerStatus, 'RECORD', {
    get: StateManagerStatus$RECORD_getInstance
  });
  Object.defineProperty(StateManagerStatus, 'UPDATE_PROPERTIES', {
    get: StateManagerStatus$UPDATE_PROPERTIES_getInstance
  });
  package$viz.StateManagerStatus = StateManagerStatus;
  package$viz.StateProperty = StateProperty;
  package$viz.Style = Style;
  package$viz.StyleImpl = StyleImpl;
  package$viz.HierarchicalStyle = HierarchicalStyle;
  Object.defineProperty(TextAnchor, 'START', {
    get: TextAnchor$START_getInstance
  });
  Object.defineProperty(TextAnchor, 'MIDDLE', {
    get: TextAnchor$MIDDLE_getInstance
  });
  Object.defineProperty(TextAnchor, 'END', {
    get: TextAnchor$END_getInstance
  });
  package$viz.TextAnchor = TextAnchor;
  Object.defineProperty(TextAlignmentBaseline, 'HANGING', {
    get: TextAlignmentBaseline$HANGING_getInstance
  });
  Object.defineProperty(TextAlignmentBaseline, 'MIDDLE', {
    get: TextAlignmentBaseline$MIDDLE_getInstance
  });
  Object.defineProperty(TextAlignmentBaseline, 'BASELINE', {
    get: TextAlignmentBaseline$BASELINE_getInstance
  });
  package$viz.TextAlignmentBaseline = TextAlignmentBaseline;
  package$viz.TextNode = TextNode;
  Object.defineProperty(FontFamily, 'Companion', {
    get: FontFamily$Companion_getInstance
  });
  package$viz.FontFamily = FontFamily;
  Object.defineProperty(FontWeight, 'BOLD', {
    get: FontWeight$BOLD_getInstance
  });
  Object.defineProperty(FontWeight, 'NORMAL', {
    get: FontWeight$NORMAL_getInstance
  });
  package$viz.FontWeight = FontWeight;
  Object.defineProperty(FontPosture, 'ITALIC', {
    get: FontPosture$ITALIC_getInstance
  });
  Object.defineProperty(FontPosture, 'NORMAL', {
    get: FontPosture$NORMAL_getInstance
  });
  package$viz.FontPosture = FontPosture;
  package$viz.Viz = Viz;
  package$viz.viz_ohegjc$ = viz;
  package$viz.StateableElement = StateableElement;
  package$viz.Transform = Transform;
  package$viz.Translation = Translation;
  package$viz.Rotation = Rotation;
  package$viz.HasStroke = HasStroke;
  package$viz.HasFill = HasFill;
  package$viz.Margins = Margins;
  package$viz.HasTransform = HasTransform;
  package$viz.VizConfig = VizConfig;
  package$viz.VizRenderer = VizRenderer;
  package$viz.bindRendererOn_zfj2g8$ = bindRendererOn;
  package$viz.bindRendererOnNewCanvas_veyafq$ = bindRendererOnNewCanvas;
  package$viz.bindRendererOn_kezl2e$ = bindRendererOn_0;
  package$viz.JsCanvasRenderer = JsCanvasRenderer;
  package$viz.render_259pyx$ = render;
  package$viz.toCanvasPaint_kswsrv$ = toCanvasPaint;
  package$viz.toCanvasGradient_rq3hm6$ = toCanvasGradient;
  package$viz.toCanvasGradient_f9mw60$ = toCanvasGradient_0;
  package$viz.render_gg89ve$ = render_0;
  package$viz.render_ewmr2t$ = render_1;
  package$viz.render_eb5h8$ = render_2;
  package$viz.render_qc3b1h$ = render_3;
  package$viz.render_6qybup$ = render_4;
  PathNode.prototype.arc_6p3vsx$ = Path.prototype.arc_6p3vsx$;
  Object.defineProperty(Viz.prototype, 'size', Object.getOwnPropertyDescriptor(HasSize.prototype, 'size'));
  linesOfText = listOf(['The quick brown,', 'fox jumps over', 'the lazy dog.']);
  fontSizeValue = 40.0;
  fontFamilyValue = 'Roboto';
  allRenderingTests = listOf([renderingTest('text-SANS_SERIF_ITALIC_BOLD', allRenderingTests$lambda), renderingTest('text-SANS_SERIF_ITALIC_NORMAL', allRenderingTests$lambda_0), renderingTest('text-SANS_SERIF_NORMAL_BOLD', allRenderingTests$lambda_1), renderingTest('text-SANS_SERIF_NORMAL_NORMAL', allRenderingTests$lambda_2), renderingTest('text-SERIF_ITALIC_BOLD', allRenderingTests$lambda_3), renderingTest('text-SERIF_ITALIC_NORMAL', allRenderingTests$lambda_4), renderingTest('text-SERIF_NORMAL_BOLD', allRenderingTests$lambda_5), renderingTest('text-SERIF_NORMAL_NORMAL', allRenderingTests$lambda_6), renderingTest('text-MONOSPACE_ITALIC_BOLD', allRenderingTests$lambda_7), renderingTest('text-MONOSPACE_ITALIC_NORMAL', allRenderingTests$lambda_8), renderingTest('text-MONOSPACE_NORMAL_BOLD', allRenderingTests$lambda_9), renderingTest('text-MONOSPACE_NORMAL_NORMAL', allRenderingTests$lambda_10), renderingTest('transform', allRenderingTests$lambda_11), renderingTest('rect1', allRenderingTests$lambda_12), renderingTest('circle1', allRenderingTests$lambda_13), renderingTest('circle2', allRenderingTests$lambda_14), renderingTest('circle3', allRenderingTests$lambda_15), renderingTest('circle4', allRenderingTests$lambda_16), renderingTest('arc1-positive-clockwise', allRenderingTests$lambda_17), renderingTest('arc2-negative-clockwise', allRenderingTests$lambda_18), renderingTest('arc3-positive-counterclockwise', allRenderingTests$lambda_19), renderingTest('arc4-negative-counterclockwise', allRenderingTests$lambda_20), renderingTest('arc5-positive-negative-clockwise', allRenderingTests$lambda_21), renderingTest('arc6-positive-negative-counterclockwise', allRenderingTests$lambda_22), renderingTest('arc7-checking-points-order-clockwise', allRenderingTests$lambda_23), renderingTest('arc8-checking-points-order-counterclockwise', allRenderingTests$lambda_24), renderingTest('arc9-complex-drawing', allRenderingTests$lambda_25), renderingTest('path1', allRenderingTests$lambda_26), renderingTest('path.rect', allRenderingTests$lambda_27), renderingTest('visible1', allRenderingTests$lambda_28), renderingTest('visible2-layer', allRenderingTests$lambda_29)]);
  Kotlin.defineModule('d2v-viz-js', _);
  return _;
}));

//# sourceMappingURL=d2v-viz-js.js.map
