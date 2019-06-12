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
  var ensureNotNull = Kotlin.ensureNotNull;
  var Kind_INTERFACE = Kotlin.Kind.INTERFACE;
  var Unit = Kotlin.kotlin.Unit;
  var Enum = Kotlin.kotlin.Enum;
  var throwISE = Kotlin.throwISE;
  var Annotation = Kotlin.kotlin.Annotation;
  var Kind_OBJECT = Kotlin.Kind.OBJECT;
  var IllegalStateException_init = Kotlin.kotlin.IllegalStateException_init_pdl1vj$;
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  var Math_0 = Math;
  var PathGeom = $module$d2v_core_js.io.data2viz.geom.PathGeom;
  var Path = $module$d2v_core_js.io.data2viz.geom.Path;
  var RectGeom = $module$d2v_core_js.io.data2viz.geom.RectGeom;
  var Rect = $module$d2v_core_js.io.data2viz.geom.Rect;
  var replace = Kotlin.kotlin.text.replace_r2fvfm$;
  var Colors = $module$d2v_color_js.io.data2viz.color.Colors;
  var mutableListOf = Kotlin.kotlin.collections.mutableListOf_i5x0yv$;
  var HasSize = $module$d2v_core_js.io.data2viz.geom.HasSize;
  var throwCCE = Kotlin.throwCCE;
  var numberToInt = Kotlin.numberToInt;
  var timer = $module$d2v_timer_js.io.data2viz.timer.timer_k9susy$;
  var IllegalArgumentException_init = Kotlin.kotlin.IllegalArgumentException_init_pdl1vj$;
  var math = Kotlin.kotlin.math;
  var Color = $module$d2v_color_js.io.data2viz.color.Color;
  var LinearGradient = $module$d2v_color_js.io.data2viz.color.LinearGradient;
  var RadialGradient = $module$d2v_color_js.io.data2viz.color.RadialGradient;
  var throwUPAE = Kotlin.throwUPAE;
  var Point = $module$d2v_core_js.io.data2viz.geom.Point;
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
  KMouseEvent.prototype = Object.create(KPointerEvent.prototype);
  KMouseEvent.prototype.constructor = KMouseEvent;
  KDragEvent$KDragAction.prototype = Object.create(Enum.prototype);
  KDragEvent$KDragAction.prototype.constructor = KDragEvent$KDragAction;
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
  TextHAlign.prototype = Object.create(Enum.prototype);
  TextHAlign.prototype.constructor = TextHAlign;
  TextVAlign.prototype = Object.create(Enum.prototype);
  TextVAlign.prototype.constructor = TextVAlign;
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
  TextAlign.prototype.copy_yf6jvm$ = function (horizontal, vertical) {
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
    return textAlign($receiver, $receiver.hAlign, $receiver.vAlign);
  }
  function set_textAlign($receiver, value) {
    $receiver.hAlign = value.horizontal;
    $receiver.vAlign = value.vertical;
  }
  function textAlign($receiver, horizontal, vertical) {
    if (horizontal === void 0)
      horizontal = TextHAlign$LEFT_getInstance();
    if (vertical === void 0)
      vertical = TextVAlign$BASELINE_getInstance();
    return new TextAlign(horizontal, vertical);
  }
  function addEventHandle($receiver, handle) {
    if (handle.isAddedToRenderer) {
      throw IllegalStateException_init("Can't add event handle which already added to Renderer".toString());
    }
    handle.disposable = addNativeEventListenerFromHandle($receiver, handle);
  }
  function removeEventHandle($receiver, handle) {
    if (!handle.isAddedToRenderer) {
      throw IllegalStateException_init(("Can't remove event handle which not added to Renderer. " + handle).toString());
    }
    ensureNotNull(handle.disposable).dispose();
    handle.disposable = null;
  }
  function Disposable() {
  }
  Disposable.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Disposable',
    interfaces: []
  };
  function CompositeDisposable(disposables) {
    if (disposables === void 0) {
      disposables = ArrayList_init();
    }
    this.disposables = disposables;
  }
  CompositeDisposable.prototype.dispose = function () {
    var tmp$;
    tmp$ = this.disposables.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      element.dispose();
    }
    this.disposables.clear();
  };
  CompositeDisposable.prototype.add_5h210y$ = function (disposable) {
    this.disposables.add_11rb$(disposable);
  };
  CompositeDisposable.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'CompositeDisposable',
    interfaces: [Disposable]
  };
  function KEvent() {
  }
  KEvent.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'KEvent',
    interfaces: []
  };
  function KEventHandle(eventListener, listener, onDispose) {
    this.eventListener = eventListener;
    this.listener = listener;
    this.onDispose = onDispose;
    this.disposable = null;
  }
  Object.defineProperty(KEventHandle.prototype, 'isAddedToRenderer', {
    get: function () {
      return this.disposable != null;
    }
  });
  KEventHandle.prototype.dispose = function () {
    this.onDispose(this);
  };
  KEventHandle.prototype.toString = function () {
    return 'KEventHandle(eventListener=' + this.eventListener + ')';
  };
  KEventHandle.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'KEventHandle',
    interfaces: [Disposable]
  };
  function KPointerEvent(pos) {
    this.pos = pos;
  }
  KPointerEvent.prototype.toString = function () {
    return 'KPointerEvent(pos=' + this.pos + ')';
  };
  KPointerEvent.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'KPointerEvent',
    interfaces: [KEvent]
  };
  function KMouseEvent(pos, altKey, ctrlKey, shiftKey, metaKey) {
    KPointerEvent.call(this, pos);
    this.altKey = altKey;
    this.ctrlKey = ctrlKey;
    this.shiftKey = shiftKey;
    this.metaKey = metaKey;
  }
  KMouseEvent.prototype.toString = function () {
    return 'KMouseEvent(pos=' + this.pos + ')';
  };
  KMouseEvent.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'KMouseEvent',
    interfaces: [KPointerEvent]
  };
  function KDragEvent(action, pointerEvent) {
    this.action = action;
    this.pointerEvent = pointerEvent;
  }
  Object.defineProperty(KDragEvent.prototype, 'pos', {
    get: function () {
      return this.pointerEvent.pos;
    }
  });
  KDragEvent.prototype.toString = function () {
    return 'KDragEvent(action=' + this.action + ', pos=' + this.pos + ')';
  };
  function KDragEvent$KDragAction(name, ordinal) {
    Enum.call(this);
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function KDragEvent$KDragAction_initFields() {
    KDragEvent$KDragAction_initFields = function () {
    };
    KDragEvent$KDragAction$Start_instance = new KDragEvent$KDragAction('Start', 0);
    KDragEvent$KDragAction$Dragging_instance = new KDragEvent$KDragAction('Dragging', 1);
    KDragEvent$KDragAction$Finish_instance = new KDragEvent$KDragAction('Finish', 2);
  }
  var KDragEvent$KDragAction$Start_instance;
  function KDragEvent$KDragAction$Start_getInstance() {
    KDragEvent$KDragAction_initFields();
    return KDragEvent$KDragAction$Start_instance;
  }
  var KDragEvent$KDragAction$Dragging_instance;
  function KDragEvent$KDragAction$Dragging_getInstance() {
    KDragEvent$KDragAction_initFields();
    return KDragEvent$KDragAction$Dragging_instance;
  }
  var KDragEvent$KDragAction$Finish_instance;
  function KDragEvent$KDragAction$Finish_getInstance() {
    KDragEvent$KDragAction_initFields();
    return KDragEvent$KDragAction$Finish_instance;
  }
  KDragEvent$KDragAction.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'KDragAction',
    interfaces: [Enum]
  };
  function KDragEvent$KDragAction$values() {
    return [KDragEvent$KDragAction$Start_getInstance(), KDragEvent$KDragAction$Dragging_getInstance(), KDragEvent$KDragAction$Finish_getInstance()];
  }
  KDragEvent$KDragAction.values = KDragEvent$KDragAction$values;
  function KDragEvent$KDragAction$valueOf(name) {
    switch (name) {
      case 'Start':
        return KDragEvent$KDragAction$Start_getInstance();
      case 'Dragging':
        return KDragEvent$KDragAction$Dragging_getInstance();
      case 'Finish':
        return KDragEvent$KDragAction$Finish_getInstance();
      default:throwISE('No enum constant io.data2viz.viz.KDragEvent.KDragAction.' + name);
    }
  }
  KDragEvent$KDragAction.valueOf_61zpoe$ = KDragEvent$KDragAction$valueOf;
  KDragEvent.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'KDragEvent',
    interfaces: [KEvent]
  };
  function ExperimentalKZoomEvent() {
  }
  ExperimentalKZoomEvent.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ExperimentalKZoomEvent',
    interfaces: [Annotation]
  };
  function KZoomEvent(startZoomPos, currentZoomPos, delta) {
    KZoomEvent$Companion_getInstance();
    this.startZoomPos = startZoomPos;
    this.currentZoomPos = currentZoomPos;
    this.delta = delta;
  }
  function KZoomEvent$Companion() {
    KZoomEvent$Companion_instance = this;
    this.diffTimeBetweenZoomEventsToDetectRestart = 500;
    this.minDelta = -100.0;
    this.maxDelta = 100.0;
  }
  KZoomEvent$Companion.prototype.isNewZoom_xg1spd$ = function (currentTime, lastTime) {
    if (lastTime == null) {
      return true;
    }
     else {
      return currentTime - lastTime > 500;
    }
  };
  KZoomEvent$Companion.prototype.isNewZoom_r25hsp$ = function (currentTime, lastTime) {
    if (lastTime == null) {
      return true;
    }
     else {
      return currentTime.subtract(lastTime).toNumber() > 500;
    }
  };
  KZoomEvent$Companion.prototype.scaleDelta_1lq62i$ = function (currentDelta, originMinDelta, originMaxDelta, newMinDelta, newMaxDelta) {
    if (newMinDelta === void 0)
      newMinDelta = this.minDelta;
    if (newMaxDelta === void 0)
      newMaxDelta = this.maxDelta;
    var originBoundsSize = originMaxDelta - originMinDelta;
    var currentDeltaPercentInBounds = (currentDelta - originMinDelta) / originBoundsSize;
    var newBoundsSize = newMaxDelta - newMinDelta;
    var newDeltaValue = newMinDelta + newBoundsSize * currentDeltaPercentInBounds;
    if (newDeltaValue > this.maxDelta) {
      newDeltaValue = this.maxDelta;
    }
    if (newDeltaValue < this.minDelta) {
      newDeltaValue = this.minDelta;
    }
    return newDeltaValue;
  };
  KZoomEvent$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var KZoomEvent$Companion_instance = null;
  function KZoomEvent$Companion_getInstance() {
    if (KZoomEvent$Companion_instance === null) {
      new KZoomEvent$Companion();
    }
    return KZoomEvent$Companion_instance;
  }
  KZoomEvent.prototype.toString = function () {
    return 'KZoomEvent(startZoomPos=' + this.startZoomPos + ', delta=' + this.delta + ')';
  };
  KZoomEvent.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'KZoomEvent',
    interfaces: [KEvent]
  };
  function KEventListener() {
  }
  KEventListener.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'KEventListener',
    interfaces: []
  };
  function KPointerDrag() {
    KPointerDrag$PointerDragEventListener_getInstance();
  }
  function KPointerDrag$PointerDragEventListener() {
    KPointerDrag$PointerDragEventListener_instance = this;
    this.downActionPos_0 = null;
    this.dragInProgress_0 = false;
  }
  function KPointerDrag$PointerDragEventListener$addNativeListener$lambda(this$KPointerDrag$, closure$listener) {
    return function (it) {
      if (this$KPointerDrag$.dragInProgress_0) {
        closure$listener(new KDragEvent(KDragEvent$KDragAction$Dragging_getInstance(), it));
      }
       else {
        var startPos = this$KPointerDrag$.downActionPos_0;
        if (startPos != null) {
          this$KPointerDrag$.dragInProgress_0 = true;
          closure$listener(new KDragEvent(KDragEvent$KDragAction$Start_getInstance(), it));
        }
      }
      return Unit;
    };
  }
  function KPointerDrag$PointerDragEventListener$addNativeListener$lambda_0(closure$listener, this$KPointerDrag$) {
    return function (it) {
      this$KPointerDrag$.onDragNotPossible_0(closure$listener, it);
      return Unit;
    };
  }
  function KPointerDrag$PointerDragEventListener$addNativeListener$lambda_1(this$KPointerDrag$) {
    return function (it) {
      this$KPointerDrag$.downActionPos_0 = it.pos;
      return Unit;
    };
  }
  function KPointerDrag$PointerDragEventListener$addNativeListener$lambda_2(closure$listener, this$KPointerDrag$) {
    return function (it) {
      this$KPointerDrag$.onDragNotPossible_0(closure$listener, it);
      return Unit;
    };
  }
  KPointerDrag$PointerDragEventListener.prototype.addNativeListener_80knxr$ = function (target, listener) {
    var compositeDisposable = new CompositeDisposable();
    compositeDisposable.add_5h210y$(KPointerMove$PointerMoveEventListener_getInstance().addNativeListener_80knxr$(target, KPointerDrag$PointerDragEventListener$addNativeListener$lambda(this, listener)));
    compositeDisposable.add_5h210y$(KPointerLeave$PointerLeaveEventListener_getInstance().addNativeListener_80knxr$(target, KPointerDrag$PointerDragEventListener$addNativeListener$lambda_0(listener, this)));
    compositeDisposable.add_5h210y$(KPointerDown$PointerDownEventListener_getInstance().addNativeListener_80knxr$(target, KPointerDrag$PointerDragEventListener$addNativeListener$lambda_1(this)));
    compositeDisposable.add_5h210y$(KPointerUp$PointerUpEventListener_getInstance().addNativeListener_80knxr$(target, KPointerDrag$PointerDragEventListener$addNativeListener$lambda_2(listener, this)));
    return compositeDisposable;
  };
  KPointerDrag$PointerDragEventListener.prototype.distance_0 = function (pos1, pos2) {
    var $receiver = pos1.x - pos2.x;
    var xSquare = Math_0.pow($receiver, 2.0);
    var $receiver_0 = pos1.y - pos2.y;
    var ySquare = Math_0.pow($receiver_0, 2.0);
    var x = xSquare + ySquare;
    return Math_0.sqrt(x);
  };
  KPointerDrag$PointerDragEventListener.prototype.onDragNotPossible_0 = function (listener, motionEvent) {
    this.downActionPos_0 = null;
    if (this.dragInProgress_0) {
      this.dragInProgress_0 = false;
      listener(new KDragEvent(KDragEvent$KDragAction$Finish_getInstance(), motionEvent));
    }
  };
  KPointerDrag$PointerDragEventListener.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'PointerDragEventListener',
    interfaces: [KEventListener]
  };
  var KPointerDrag$PointerDragEventListener_instance = null;
  function KPointerDrag$PointerDragEventListener_getInstance() {
    if (KPointerDrag$PointerDragEventListener_instance === null) {
      new KPointerDrag$PointerDragEventListener();
    }
    return KPointerDrag$PointerDragEventListener_instance;
  }
  KPointerDrag.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'KPointerDrag',
    interfaces: []
  };
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
  Object.defineProperty(Node.prototype, 'textColor', {
    get: function () {
      return this.style_u8faqh$_0.textColor;
    },
    set: function (value) {
      this.style_u8faqh$_0.textColor = value;
    }
  });
  Object.defineProperty(Node.prototype, 'hAlign', {
    get: function () {
      return this.style_u8faqh$_0.hAlign;
    },
    set: function (value) {
      this.style_u8faqh$_0.hAlign = value;
    }
  });
  Object.defineProperty(Node.prototype, 'anchor', {
    get: function () {
      return this.hAlign;
    },
    set: function (value) {
      this.hAlign = value;
    }
  });
  Object.defineProperty(Node.prototype, 'vAlign', {
    get: function () {
      return this.style_u8faqh$_0.vAlign;
    },
    set: function (value) {
      this.style_u8faqh$_0.vAlign = value;
    }
  });
  Object.defineProperty(Node.prototype, 'baseline', {
    get: function () {
      return this.vAlign;
    },
    set: function (value) {
      this.vAlign = value;
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
    this.textColor_riwbw1$_0 = null;
    this.strokeWidth_l4w949$_0 = 1.0;
    this.hAlign_pi4kky$_0 = TextHAlign$LEFT_getInstance();
    this.anchor_eyj0fu$_0 = this.hAlign;
    this.vAlign_hwcds0$_0 = TextVAlign$BASELINE_getInstance();
    this.baseline_1osq8m$_0 = this.vAlign;
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
  Object.defineProperty(StyleImpl.prototype, 'textColor', {
    get: function () {
      return this.textColor_riwbw1$_0;
    },
    set: function (textColor) {
      this.textColor_riwbw1$_0 = textColor;
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
  Object.defineProperty(StyleImpl.prototype, 'hAlign', {
    get: function () {
      return this.hAlign_pi4kky$_0;
    },
    set: function (hAlign) {
      this.hAlign_pi4kky$_0 = hAlign;
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
  Object.defineProperty(StyleImpl.prototype, 'vAlign', {
    get: function () {
      return this.vAlign_hwcds0$_0;
    },
    set: function (vAlign) {
      this.vAlign_hwcds0$_0 = vAlign;
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
    this.textColorSet_0 = false;
    this.hAlignSet_0 = false;
    this.vAlignSet_0 = false;
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
  Object.defineProperty(HierarchicalStyle.prototype, 'textColor', {
    get: function () {
      var tmp$, tmp$_0;
      return this.textColorSet_0 ? (tmp$ = this.style_0) != null ? tmp$.textColor : null : (tmp$_0 = this.parent) != null ? tmp$_0.textColor : null;
    },
    set: function (value) {
      var tmp$;
      if (this.style_0 == null)
        this.style_0 = new StyleImpl();
      this.textColorSet_0 = true;
      (tmp$ = this.style_0) != null ? (tmp$.textColor = value) : null;
    }
  });
  Object.defineProperty(HierarchicalStyle.prototype, 'hAlign', {
    get: function () {
      var tmp$, tmp$_0;
      return this.hAlignSet_0 ? ensureNotNull((tmp$ = this.style_0) != null ? tmp$.hAlign : null) : ensureNotNull((tmp$_0 = this.parent) != null ? tmp$_0.hAlign : null);
    },
    set: function (value) {
      var tmp$;
      if (this.style_0 == null)
        this.style_0 = new StyleImpl();
      this.hAlignSet_0 = true;
      (tmp$ = this.style_0) != null ? (tmp$.hAlign = value) : null;
    }
  });
  Object.defineProperty(HierarchicalStyle.prototype, 'anchor', {
    get: function () {
      return this.hAlign;
    },
    set: function (value) {
      this.hAlign = value;
    }
  });
  Object.defineProperty(HierarchicalStyle.prototype, 'vAlign', {
    get: function () {
      var tmp$, tmp$_0;
      return this.vAlignSet_0 ? ensureNotNull((tmp$ = this.style_0) != null ? tmp$.vAlign : null) : ensureNotNull((tmp$_0 = this.parent) != null ? tmp$_0.vAlign : null);
    },
    set: function (value) {
      var tmp$;
      if (this.style_0 == null)
        this.style_0 = new StyleImpl();
      this.vAlignSet_0 = true;
      (tmp$ = this.style_0) != null ? (tmp$.vAlign = value) : null;
    }
  });
  Object.defineProperty(HierarchicalStyle.prototype, 'baseline', {
    get: function () {
      return this.vAlign;
    },
    set: function (value) {
      this.vAlign = value;
    }
  });
  HierarchicalStyle.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'HierarchicalStyle',
    interfaces: [Style]
  };
  function TextHAlign(name, ordinal) {
    Enum.call(this);
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function TextHAlign_initFields() {
    TextHAlign_initFields = function () {
    };
    TextHAlign$LEFT_instance = new TextHAlign('LEFT', 0);
    TextHAlign$START_instance = new TextHAlign('START', 1);
    TextHAlign$MIDDLE_instance = new TextHAlign('MIDDLE', 2);
    TextHAlign$RIGHT_instance = new TextHAlign('RIGHT', 3);
    TextHAlign$END_instance = new TextHAlign('END', 4);
  }
  var TextHAlign$LEFT_instance;
  function TextHAlign$LEFT_getInstance() {
    TextHAlign_initFields();
    return TextHAlign$LEFT_instance;
  }
  var TextHAlign$START_instance;
  function TextHAlign$START_getInstance() {
    TextHAlign_initFields();
    return TextHAlign$START_instance;
  }
  var TextHAlign$MIDDLE_instance;
  function TextHAlign$MIDDLE_getInstance() {
    TextHAlign_initFields();
    return TextHAlign$MIDDLE_instance;
  }
  var TextHAlign$RIGHT_instance;
  function TextHAlign$RIGHT_getInstance() {
    TextHAlign_initFields();
    return TextHAlign$RIGHT_instance;
  }
  var TextHAlign$END_instance;
  function TextHAlign$END_getInstance() {
    TextHAlign_initFields();
    return TextHAlign$END_instance;
  }
  TextHAlign.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'TextHAlign',
    interfaces: [Enum]
  };
  function TextHAlign$values() {
    return [TextHAlign$LEFT_getInstance(), TextHAlign$START_getInstance(), TextHAlign$MIDDLE_getInstance(), TextHAlign$RIGHT_getInstance(), TextHAlign$END_getInstance()];
  }
  TextHAlign.values = TextHAlign$values;
  function TextHAlign$valueOf(name) {
    switch (name) {
      case 'LEFT':
        return TextHAlign$LEFT_getInstance();
      case 'START':
        return TextHAlign$START_getInstance();
      case 'MIDDLE':
        return TextHAlign$MIDDLE_getInstance();
      case 'RIGHT':
        return TextHAlign$RIGHT_getInstance();
      case 'END':
        return TextHAlign$END_getInstance();
      default:throwISE('No enum constant io.data2viz.viz.TextHAlign.' + name);
    }
  }
  TextHAlign.valueOf_61zpoe$ = TextHAlign$valueOf;
  function TextVAlign(name, ordinal) {
    Enum.call(this);
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function TextVAlign_initFields() {
    TextVAlign_initFields = function () {
    };
    TextVAlign$HANGING_instance = new TextVAlign('HANGING', 0);
    TextVAlign$MIDDLE_instance = new TextVAlign('MIDDLE', 1);
    TextVAlign$BASELINE_instance = new TextVAlign('BASELINE', 2);
  }
  var TextVAlign$HANGING_instance;
  function TextVAlign$HANGING_getInstance() {
    TextVAlign_initFields();
    return TextVAlign$HANGING_instance;
  }
  var TextVAlign$MIDDLE_instance;
  function TextVAlign$MIDDLE_getInstance() {
    TextVAlign_initFields();
    return TextVAlign$MIDDLE_instance;
  }
  var TextVAlign$BASELINE_instance;
  function TextVAlign$BASELINE_getInstance() {
    TextVAlign_initFields();
    return TextVAlign$BASELINE_instance;
  }
  TextVAlign.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'TextVAlign',
    interfaces: [Enum]
  };
  function TextVAlign$values() {
    return [TextVAlign$HANGING_getInstance(), TextVAlign$MIDDLE_getInstance(), TextVAlign$BASELINE_getInstance()];
  }
  TextVAlign.values = TextVAlign$values;
  function TextVAlign$valueOf(name) {
    switch (name) {
      case 'HANGING':
        return TextVAlign$HANGING_getInstance();
      case 'MIDDLE':
        return TextVAlign$MIDDLE_getInstance();
      case 'BASELINE':
        return TextVAlign$BASELINE_getInstance();
      default:throwISE('No enum constant io.data2viz.viz.TextVAlign.' + name);
    }
  }
  TextVAlign.valueOf_61zpoe$ = TextVAlign$valueOf;
  function TextNode() {
    Node.call(this);
    this.transform_eg0kf5$_0 = null;
    this.x = 0.0;
    this.y = 0.0;
    this.textContent_dmbbup$_0 = 'Type something';
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
  Object.defineProperty(TextNode.prototype, 'textContent', {
    get: function () {
      return this.textContent_dmbbup$_0;
    },
    set: function (value) {
      this.textContent_dmbbup$_0 = makeVizFriendlyText(value);
    }
  });
  TextNode.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'TextNode',
    interfaces: [HasTransform, HasStroke, HasFill, Node]
  };
  function makeVizFriendlyText($receiver) {
    return replaceNewLineWithSpace($receiver);
  }
  function replaceNewLineWithSpace($receiver) {
    return replace($receiver, 10, 32);
  }
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
    this.style_0 = new StyleImpl();
    this.activeLayer.parent = this;
    this.textColor = Colors.Web.black;
    this.config = new VizConfig();
    this.width_p2f4jt$_0 = 100.0;
    this.height_d5hk3m$_0 = 100.0;
    this.layers = mutableListOf([this.activeLayer]);
    this.resizeBehavior_0 = null;
    this.renderer_9w8zxa$_0 = null;
    this.eventListeners = ArrayList_init();
    this.animationTimers_8be2vx$ = ArrayList_init();
  }
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
  Object.defineProperty(Viz.prototype, 'textColor', {
    get: function () {
      return this.style_0.textColor;
    },
    set: function (value) {
      this.style_0.textColor = value;
    }
  });
  Object.defineProperty(Viz.prototype, 'hAlign', {
    get: function () {
      return this.style_0.hAlign;
    },
    set: function (value) {
      this.style_0.hAlign = value;
    }
  });
  Object.defineProperty(Viz.prototype, 'vAlign', {
    get: function () {
      return this.style_0.vAlign;
    },
    set: function (value) {
      this.style_0.vAlign = value;
    }
  });
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
      return this.renderer_9w8zxa$_0;
    },
    set: function (newValue) {
      var oldValue = this.renderer_9w8zxa$_0;
      this.renderer_9w8zxa$_0 = newValue;
      var tmp$;
      tmp$ = this.eventListeners.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        if (oldValue != null) {
          removeEventHandle(oldValue, element);
        }
        if (newValue != null) {
          addEventHandle(newValue, element);
        }
      }
    }
  });
  function Viz$on$lambda(this$Viz) {
    return function (it) {
      var tmp$;
      this$Viz.eventListeners.remove_11rb$(it);
      if ((tmp$ = this$Viz.renderer) != null) {
        removeEventHandle(tmp$, it);
      }
      return Unit;
    };
  }
  Viz.prototype.on_8o0vxr$ = function (eventListener, listener) {
    var tmp$;
    var eventHandle = new KEventHandle(eventListener, listener, Viz$on$lambda(this));
    this.eventListeners.add_11rb$(eventHandle);
    (tmp$ = this.renderer) != null ? (addEventHandle(tmp$, eventHandle), Unit) : null;
    return eventHandle;
  };
  Viz.prototype.render = function () {
    ensureNotNull(this.renderer).render();
  };
  Viz.prototype.startAnimations = function () {
    ensureNotNull(this.renderer).startAnimations();
  };
  Viz.prototype.stopAnimations = function () {
    ensureNotNull(this.renderer).stopAnimations();
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
  Object.defineProperty(Viz.prototype, 'anchor', {
    get: function () {
      return this.$delegate_jlwl53$_0.anchor;
    },
    set: function (tmp$) {
      this.$delegate_jlwl53$_0.anchor = tmp$;
    }
  });
  Object.defineProperty(Viz.prototype, 'baseline', {
    get: function () {
      return this.$delegate_jlwl53$_0.baseline;
    },
    set: function (tmp$) {
      this.$delegate_jlwl53$_0.baseline = tmp$;
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
    context.arc($receiver.x, $receiver.y, $receiver.radius, 0.0, 2 * math.PI, false);
    if ($receiver.fill != null) {
      context.fill();
    }
    if ($receiver.stroke != null) {
      context.stroke();
    }
  }
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
  function KPointerDown() {
    KPointerDown$PointerDownEventListener_getInstance();
  }
  function KPointerDown$PointerDownEventListener() {
    KPointerDown$PointerDownEventListener_instance = this;
  }
  KPointerDown$PointerDownEventListener.prototype.addNativeListener_80knxr$ = function (target, listener) {
    return createJsListener(target, listener, 'mousedown');
  };
  KPointerDown$PointerDownEventListener.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'PointerDownEventListener',
    interfaces: [KEventListener]
  };
  var KPointerDown$PointerDownEventListener_instance = null;
  function KPointerDown$PointerDownEventListener_getInstance() {
    if (KPointerDown$PointerDownEventListener_instance === null) {
      new KPointerDown$PointerDownEventListener();
    }
    return KPointerDown$PointerDownEventListener_instance;
  }
  KPointerDown.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'KPointerDown',
    interfaces: []
  };
  function KPointerUp() {
    KPointerUp$PointerUpEventListener_getInstance();
  }
  function KPointerUp$PointerUpEventListener() {
    KPointerUp$PointerUpEventListener_instance = this;
  }
  KPointerUp$PointerUpEventListener.prototype.addNativeListener_80knxr$ = function (target, listener) {
    return createJsListener(target, listener, 'mouseup');
  };
  KPointerUp$PointerUpEventListener.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'PointerUpEventListener',
    interfaces: [KEventListener]
  };
  var KPointerUp$PointerUpEventListener_instance = null;
  function KPointerUp$PointerUpEventListener_getInstance() {
    if (KPointerUp$PointerUpEventListener_instance === null) {
      new KPointerUp$PointerUpEventListener();
    }
    return KPointerUp$PointerUpEventListener_instance;
  }
  KPointerUp.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'KPointerUp',
    interfaces: []
  };
  function KPointerEnter() {
    KPointerEnter$PointerEnterEventListener_getInstance();
  }
  function KPointerEnter$PointerEnterEventListener() {
    KPointerEnter$PointerEnterEventListener_instance = this;
  }
  KPointerEnter$PointerEnterEventListener.prototype.addNativeListener_80knxr$ = function (target, listener) {
    return createJsListener(target, listener, 'mouseenter');
  };
  KPointerEnter$PointerEnterEventListener.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'PointerEnterEventListener',
    interfaces: [KEventListener]
  };
  var KPointerEnter$PointerEnterEventListener_instance = null;
  function KPointerEnter$PointerEnterEventListener_getInstance() {
    if (KPointerEnter$PointerEnterEventListener_instance === null) {
      new KPointerEnter$PointerEnterEventListener();
    }
    return KPointerEnter$PointerEnterEventListener_instance;
  }
  KPointerEnter.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'KPointerEnter',
    interfaces: []
  };
  function KPointerLeave() {
    KPointerLeave$PointerLeaveEventListener_getInstance();
  }
  function KPointerLeave$PointerLeaveEventListener() {
    KPointerLeave$PointerLeaveEventListener_instance = this;
  }
  KPointerLeave$PointerLeaveEventListener.prototype.addNativeListener_80knxr$ = function (target, listener) {
    return createJsListener(target, listener, 'mouseleave');
  };
  KPointerLeave$PointerLeaveEventListener.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'PointerLeaveEventListener',
    interfaces: [KEventListener]
  };
  var KPointerLeave$PointerLeaveEventListener_instance = null;
  function KPointerLeave$PointerLeaveEventListener_getInstance() {
    if (KPointerLeave$PointerLeaveEventListener_instance === null) {
      new KPointerLeave$PointerLeaveEventListener();
    }
    return KPointerLeave$PointerLeaveEventListener_instance;
  }
  KPointerLeave.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'KPointerLeave',
    interfaces: []
  };
  function KPointerDoubleClick() {
    KPointerDoubleClick$PointerDoubleClickEventListener_getInstance();
  }
  function KPointerDoubleClick$PointerDoubleClickEventListener() {
    KPointerDoubleClick$PointerDoubleClickEventListener_instance = this;
  }
  KPointerDoubleClick$PointerDoubleClickEventListener.prototype.addNativeListener_80knxr$ = function (target, listener) {
    return createJsListener(target, listener, 'dblclick');
  };
  KPointerDoubleClick$PointerDoubleClickEventListener.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'PointerDoubleClickEventListener',
    interfaces: [KEventListener]
  };
  var KPointerDoubleClick$PointerDoubleClickEventListener_instance = null;
  function KPointerDoubleClick$PointerDoubleClickEventListener_getInstance() {
    if (KPointerDoubleClick$PointerDoubleClickEventListener_instance === null) {
      new KPointerDoubleClick$PointerDoubleClickEventListener();
    }
    return KPointerDoubleClick$PointerDoubleClickEventListener_instance;
  }
  KPointerDoubleClick.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'KPointerDoubleClick',
    interfaces: []
  };
  function KPointerMove() {
    KPointerMove$PointerMoveEventListener_getInstance();
  }
  function KPointerMove$PointerMoveEventListener() {
    KPointerMove$PointerMoveEventListener_instance = this;
  }
  KPointerMove$PointerMoveEventListener.prototype.addNativeListener_80knxr$ = function (target, listener) {
    return createJsListener(target, listener, 'mousemove');
  };
  KPointerMove$PointerMoveEventListener.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'PointerMoveEventListener',
    interfaces: [KEventListener]
  };
  var KPointerMove$PointerMoveEventListener_instance = null;
  function KPointerMove$PointerMoveEventListener_getInstance() {
    if (KPointerMove$PointerMoveEventListener_instance === null) {
      new KPointerMove$PointerMoveEventListener();
    }
    return KPointerMove$PointerMoveEventListener_instance;
  }
  KPointerMove.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'KPointerMove',
    interfaces: []
  };
  function KPointerClick() {
    KPointerClick$PointerClickEventListener_getInstance();
  }
  function KPointerClick$PointerClickEventListener() {
    KPointerClick$PointerClickEventListener_instance = this;
  }
  KPointerClick$PointerClickEventListener.prototype.addNativeListener_80knxr$ = function (target, listener) {
    return createJsListener(target, listener, 'click');
  };
  KPointerClick$PointerClickEventListener.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'PointerClickEventListener',
    interfaces: [KEventListener]
  };
  var KPointerClick$PointerClickEventListener_instance = null;
  function KPointerClick$PointerClickEventListener_getInstance() {
    if (KPointerClick$PointerClickEventListener_instance === null) {
      new KPointerClick$PointerClickEventListener();
    }
    return KPointerClick$PointerClickEventListener_instance;
  }
  KPointerClick.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'KPointerClick',
    interfaces: []
  };
  function KZoom() {
    KZoom$ZoomEventListener_getInstance();
  }
  function KZoom$ZoomEventListener() {
    KZoom$ZoomEventListener_instance = this;
    this.minGestureZoomDeltaValue = -10.0;
    this.maxGestureZoomDeltaValue = 10.0;
    this.minWheelZoomDeltaValue = -100.0;
    this.maxWheelZoomDeltaValue = 100.0;
    this.lastZoomTime = null;
    this.zoomStartPoint_zb099k$_0 = this.zoomStartPoint_zb099k$_0;
  }
  Object.defineProperty(KZoom$ZoomEventListener.prototype, 'zoomStartPoint', {
    get: function () {
      if (this.zoomStartPoint_zb099k$_0 == null)
        return throwUPAE('zoomStartPoint');
      return this.zoomStartPoint_zb099k$_0;
    },
    set: function (zoomStartPoint) {
      this.zoomStartPoint_zb099k$_0 = zoomStartPoint;
    }
  });
  function KZoom$ZoomEventListener$addNativeListener$ObjectLiteral(closure$htmlElement, closure$listener) {
    this.closure$htmlElement = closure$htmlElement;
    this.closure$listener = closure$listener;
  }
  KZoom$ZoomEventListener$addNativeListener$ObjectLiteral.prototype.handleEvent = function (event) {
    var tmp$;
    var $receiver = Kotlin.isType(tmp$ = event, WheelEvent) ? tmp$ : throwCCE();
    this.closure$htmlElement;
    this.closure$listener;
    var closure$htmlElement = this.closure$htmlElement;
    var closure$listener = this.closure$listener;
    event.preventDefault();
    var invertedDelta = $receiver.deltaY * -1;
    var currentTime = Date.now();
    var zoomPoint = new Point($receiver.clientX - closure$htmlElement.offsetLeft, $receiver.clientY - closure$htmlElement.offsetTop);
    if (KZoomEvent$Companion_getInstance().isNewZoom_xg1spd$(currentTime, KZoom$ZoomEventListener_getInstance().lastZoomTime)) {
      KZoom$ZoomEventListener_getInstance().zoomStartPoint = zoomPoint;
    }
    if (event.ctrlKey) {
      closure$listener(new KZoomEvent(KZoom$ZoomEventListener_getInstance().zoomStartPoint, zoomPoint, KZoomEvent$Companion_getInstance().scaleDelta_1lq62i$(invertedDelta, KZoom$ZoomEventListener_getInstance().minWheelZoomDeltaValue, KZoom$ZoomEventListener_getInstance().maxWheelZoomDeltaValue)));
    }
     else {
      closure$listener(new KZoomEvent(KZoom$ZoomEventListener_getInstance().zoomStartPoint, zoomPoint, KZoomEvent$Companion_getInstance().scaleDelta_1lq62i$(invertedDelta, KZoom$ZoomEventListener_getInstance().minGestureZoomDeltaValue, KZoom$ZoomEventListener_getInstance().maxGestureZoomDeltaValue)));
    }
    KZoom$ZoomEventListener_getInstance().lastZoomTime = currentTime;
  };
  KZoom$ZoomEventListener$addNativeListener$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: []
  };
  KZoom$ZoomEventListener.prototype.addNativeListener_80knxr$ = function (target, listener) {
    var htmlElement = target;
    var nativeListener = new KZoom$ZoomEventListener$addNativeListener$ObjectLiteral(htmlElement, listener);
    var $receiver = new JsListener(htmlElement, 'wheel', nativeListener);
    $receiver.init();
    return $receiver;
  };
  KZoom$ZoomEventListener.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'ZoomEventListener',
    interfaces: [KEventListener]
  };
  var KZoom$ZoomEventListener_instance = null;
  function KZoom$ZoomEventListener_getInstance() {
    if (KZoom$ZoomEventListener_instance === null) {
      new KZoom$ZoomEventListener();
    }
    return KZoom$ZoomEventListener_instance;
  }
  KZoom.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'KZoom',
    interfaces: []
  };
  function createJsListener$ObjectLiteral(closure$htmlElement, closure$listener) {
    this.closure$htmlElement = closure$htmlElement;
    this.closure$listener = closure$listener;
  }
  createJsListener$ObjectLiteral.prototype.handleEvent = function (event) {
    var nativeEvent = convertToKEvent(event, this.closure$htmlElement);
    this.closure$listener(nativeEvent);
  };
  createJsListener$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: []
  };
  function createJsListener(target, listener, jsEventName) {
    var htmlElement = target;
    var nativeListener = new createJsListener$ObjectLiteral(htmlElement, listener);
    var $receiver = new JsListener(htmlElement, jsEventName, nativeListener);
    $receiver.init();
    return $receiver;
  }
  function JsListener(htmlElement, type, listener) {
    this.htmlElement = htmlElement;
    this.type = type;
    this.listener = listener;
  }
  JsListener.prototype.init = function () {
    this.htmlElement.addEventListener(this.type, this.listener);
  };
  JsListener.prototype.dispose = function () {
    this.htmlElement.removeEventListener(this.type, this.listener);
  };
  JsListener.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'JsListener',
    interfaces: [Disposable]
  };
  JsListener.prototype.component1 = function () {
    return this.htmlElement;
  };
  JsListener.prototype.component2 = function () {
    return this.type;
  };
  JsListener.prototype.component3 = function () {
    return this.listener;
  };
  JsListener.prototype.copy_ub876k$ = function (htmlElement, type, listener) {
    return new JsListener(htmlElement === void 0 ? this.htmlElement : htmlElement, type === void 0 ? this.type : type, listener === void 0 ? this.listener : listener);
  };
  JsListener.prototype.toString = function () {
    return 'JsListener(htmlElement=' + Kotlin.toString(this.htmlElement) + (', type=' + Kotlin.toString(this.type)) + (', listener=' + Kotlin.toString(this.listener)) + ')';
  };
  JsListener.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.htmlElement) | 0;
    result = result * 31 + Kotlin.hashCode(this.type) | 0;
    result = result * 31 + Kotlin.hashCode(this.listener) | 0;
    return result;
  };
  JsListener.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.htmlElement, other.htmlElement) && Kotlin.equals(this.type, other.type) && Kotlin.equals(this.listener, other.listener)))));
  };
  function addNativeEventListenerFromHandle($receiver, handle) {
    var tmp$;
    var jsCanvasRenderer = Kotlin.isType(tmp$ = $receiver, JsCanvasRenderer) ? tmp$ : throwCCE();
    return handle.eventListener.addNativeListener_80knxr$(jsCanvasRenderer.context.canvas, handle.listener);
  }
  function convertToKEvent($receiver, target) {
    var kPointerMoveEvent = new KMouseEvent(new Point($receiver.clientX - target.offsetLeft, $receiver.clientY - target.offsetTop), $receiver.altKey, $receiver.ctrlKey, $receiver.shiftKey, $receiver.metaKey);
    return kPointerMoveEvent;
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
    var tmp$;
    context.textAlign = get_js_0($receiver.hAlign);
    context.textBaseline = get_js($receiver.vAlign);
    context.font = get_js_2($receiver.fontStyle) + ' ' + get_js_1($receiver.fontWeight) + ' ' + $receiver.fontSize + 'px ' + $receiver.fontFamily.name;
    if ((tmp$ = $receiver.textColor) != null) {
      context.fillStyle = toCanvasPaint(tmp$, context);
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
      case 'LEFT':
        return 'left';
      case 'END':
      case 'RIGHT':
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
  package$viz.textAlign_swvb04$ = textAlign;
  package$viz.addEventHandle_2i0bkv$ = addEventHandle;
  package$viz.removeEventHandle_2i0bkv$ = removeEventHandle;
  package$viz.Disposable = Disposable;
  package$viz.CompositeDisposable = CompositeDisposable;
  package$viz.KEvent = KEvent;
  package$viz.KEventHandle = KEventHandle;
  package$viz.KPointerEvent = KPointerEvent;
  package$viz.KMouseEvent = KMouseEvent;
  Object.defineProperty(KDragEvent$KDragAction, 'Start', {
    get: KDragEvent$KDragAction$Start_getInstance
  });
  Object.defineProperty(KDragEvent$KDragAction, 'Dragging', {
    get: KDragEvent$KDragAction$Dragging_getInstance
  });
  Object.defineProperty(KDragEvent$KDragAction, 'Finish', {
    get: KDragEvent$KDragAction$Finish_getInstance
  });
  KDragEvent.KDragAction = KDragEvent$KDragAction;
  package$viz.KDragEvent = KDragEvent;
  package$viz.ExperimentalKZoomEvent = ExperimentalKZoomEvent;
  Object.defineProperty(KZoomEvent, 'Companion', {
    get: KZoomEvent$Companion_getInstance
  });
  package$viz.KZoomEvent = KZoomEvent;
  package$viz.KEventListener = KEventListener;
  Object.defineProperty(KPointerDrag, 'PointerDragEventListener', {
    get: KPointerDrag$PointerDragEventListener_getInstance
  });
  package$viz.KPointerDrag = KPointerDrag;
  package$viz.GroupNode = GroupNode;
  package$viz.HasChildren = HasChildren;
  package$viz.Layer = Layer;
  package$viz.LineNode = LineNode;
  package$viz.Node = Node;
  package$viz.PathNode = PathNode;
  package$viz.RectNode = RectNode;
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
  Object.defineProperty(TextHAlign, 'LEFT', {
    get: TextHAlign$LEFT_getInstance
  });
  Object.defineProperty(TextHAlign, 'START', {
    get: TextHAlign$START_getInstance
  });
  Object.defineProperty(TextHAlign, 'MIDDLE', {
    get: TextHAlign$MIDDLE_getInstance
  });
  Object.defineProperty(TextHAlign, 'RIGHT', {
    get: TextHAlign$RIGHT_getInstance
  });
  Object.defineProperty(TextHAlign, 'END', {
    get: TextHAlign$END_getInstance
  });
  package$viz.TextHAlign = TextHAlign;
  Object.defineProperty(TextVAlign, 'HANGING', {
    get: TextVAlign$HANGING_getInstance
  });
  Object.defineProperty(TextVAlign, 'MIDDLE', {
    get: TextVAlign$MIDDLE_getInstance
  });
  Object.defineProperty(TextVAlign, 'BASELINE', {
    get: TextVAlign$BASELINE_getInstance
  });
  package$viz.TextVAlign = TextVAlign;
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
  Object.defineProperty(KPointerDown, 'PointerDownEventListener', {
    get: KPointerDown$PointerDownEventListener_getInstance
  });
  package$viz.KPointerDown = KPointerDown;
  Object.defineProperty(KPointerUp, 'PointerUpEventListener', {
    get: KPointerUp$PointerUpEventListener_getInstance
  });
  package$viz.KPointerUp = KPointerUp;
  Object.defineProperty(KPointerEnter, 'PointerEnterEventListener', {
    get: KPointerEnter$PointerEnterEventListener_getInstance
  });
  package$viz.KPointerEnter = KPointerEnter;
  Object.defineProperty(KPointerLeave, 'PointerLeaveEventListener', {
    get: KPointerLeave$PointerLeaveEventListener_getInstance
  });
  package$viz.KPointerLeave = KPointerLeave;
  Object.defineProperty(KPointerDoubleClick, 'PointerDoubleClickEventListener', {
    get: KPointerDoubleClick$PointerDoubleClickEventListener_getInstance
  });
  package$viz.KPointerDoubleClick = KPointerDoubleClick;
  Object.defineProperty(KPointerMove, 'PointerMoveEventListener', {
    get: KPointerMove$PointerMoveEventListener_getInstance
  });
  package$viz.KPointerMove = KPointerMove;
  Object.defineProperty(KPointerClick, 'PointerClickEventListener', {
    get: KPointerClick$PointerClickEventListener_getInstance
  });
  package$viz.KPointerClick = KPointerClick;
  Object.defineProperty(KZoom, 'ZoomEventListener', {
    get: KZoom$ZoomEventListener_getInstance
  });
  package$viz.KZoom = KZoom;
  package$viz.JsListener = JsListener;
  package$viz.addNativeEventListenerFromHandle_2i0bkv$ = addNativeEventListenerFromHandle;
  package$viz.convertToKEvent_u7ml53$ = convertToKEvent;
  package$viz.render_gg89ve$ = render_0;
  package$viz.render_ewmr2t$ = render_1;
  package$viz.render_eb5h8$ = render_2;
  package$viz.render_qc3b1h$ = render_3;
  package$viz.render_6qybup$ = render_4;
  PathNode.prototype.arc_6p3vsx$ = Path.prototype.arc_6p3vsx$;
  Object.defineProperty(Viz.prototype, 'size', Object.getOwnPropertyDescriptor(HasSize.prototype, 'size'));
  Kotlin.defineModule('d2v-viz-js', _);
  return _;
}));

//# sourceMappingURL=d2v-viz-js.js.map
