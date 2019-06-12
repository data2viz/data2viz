(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin', 'd2v-core-js', 'd2v-quadtree-js', 'd2v-timer-js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'), require('d2v-core-js'), require('d2v-quadtree-js'), require('d2v-timer-js'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'd2v-force-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'd2v-force-js'.");
    }
    if (typeof this['d2v-core-js'] === 'undefined') {
      throw new Error("Error loading module 'd2v-force-js'. Its dependency 'd2v-core-js' was not found. Please, check whether 'd2v-core-js' is loaded prior to 'd2v-force-js'.");
    }
    if (typeof this['d2v-quadtree-js'] === 'undefined') {
      throw new Error("Error loading module 'd2v-force-js'. Its dependency 'd2v-quadtree-js' was not found. Please, check whether 'd2v-quadtree-js' is loaded prior to 'd2v-force-js'.");
    }
    if (typeof this['d2v-timer-js'] === 'undefined') {
      throw new Error("Error loading module 'd2v-force-js'. Its dependency 'd2v-timer-js' was not found. Please, check whether 'd2v-timer-js' is loaded prior to 'd2v-force-js'.");
    }
    root['d2v-force-js'] = factory(typeof this['d2v-force-js'] === 'undefined' ? {} : this['d2v-force-js'], kotlin, this['d2v-core-js'], this['d2v-quadtree-js'], this['d2v-timer-js']);
  }
}(this, function (_, Kotlin, $module$d2v_core_js, $module$d2v_quadtree_js, $module$d2v_timer_js) {
  'use strict';
  var Random = Kotlin.kotlin.random.Random;
  var math = $module$d2v_core_js.io.data2viz.math;
  var Kind_INTERFACE = Kotlin.Kind.INTERFACE;
  var Point = $module$d2v_core_js.io.data2viz.geom.Point;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var emptyList = Kotlin.kotlin.collections.emptyList_287e2$;
  var throwUPAE = Kotlin.throwUPAE;
  var until = Kotlin.kotlin.ranges.until_dqglrj$;
  var quadtree = $module$d2v_quadtree_js.io.data2viz.quadtree.quadtree_tz1x4d$;
  var Unit = Kotlin.kotlin.Unit;
  var getCallableRef = Kotlin.getCallableRef;
  var visitAfter = $module$d2v_quadtree_js.io.data2viz.quadtree.visitAfter_690jwc$;
  var visit = $module$d2v_quadtree_js.io.data2viz.quadtree.visit_jsbco0$;
  var LeafNode = $module$d2v_quadtree_js.io.data2viz.quadtree.LeafNode;
  var ensureNotNull = Kotlin.ensureNotNull;
  var InternalNode = $module$d2v_quadtree_js.io.data2viz.quadtree.InternalNode;
  var throwCCE = Kotlin.throwCCE;
  var toList = $module$d2v_quadtree_js.io.data2viz.quadtree.toList_dw9rom$;
  var kotlin_js_internal_DoubleCompanionObject = Kotlin.kotlin.js.internal.DoubleCompanionObject;
  var get_pct = $module$d2v_core_js.io.data2viz.math.get_pct_rcaex3$;
  var Math_0 = Math;
  var collectionSizeOrDefault = Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$;
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_ww73n8$;
  var checkIndexOverflow = Kotlin.kotlin.collections.checkIndexOverflow_za3lpa$;
  var flatten = Kotlin.kotlin.collections.flatten_u0ad8z$;
  var isNaN_0 = Kotlin.kotlin.isNaN_yrwdxr$;
  var ArrayList_init_0 = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  var Array_0 = Array;
  var point = $module$d2v_core_js.io.data2viz.geom.point_lu1900$;
  var Vector = $module$d2v_core_js.io.data2viz.geom.Vector;
  var point_0 = $module$d2v_core_js.io.data2viz.geom.point_vux9f0$;
  var math_0 = Kotlin.kotlin.math;
  var Percent = $module$d2v_core_js.io.data2viz.math.Percent;
  var plus = Kotlin.kotlin.collections.plus_qloxvw$;
  var minus = Kotlin.kotlin.collections.minus_2ws7j4$;
  var toList_0 = Kotlin.kotlin.collections.toList_7wnvza$;
  var equals = Kotlin.equals;
  var timer = $module$d2v_timer_js.io.data2viz.timer.timer_k9susy$;
  var Enum = Kotlin.kotlin.Enum;
  var throwISE = Kotlin.throwISE;
  var LinkedHashMap_init = Kotlin.kotlin.collections.LinkedHashMap_init_q3lmfv$;
  SimulationEvent.prototype = Object.create(Enum.prototype);
  SimulationEvent.prototype.constructor = SimulationEvent;
  function forceSimulation(init) {
    var $receiver = new ForceSimulation();
    init($receiver);
    return $receiver;
  }
  function jiggle() {
    return (Random.Default.nextDouble() - 0.5) * math.EPSILON;
  }
  function Force() {
  }
  Force.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Force',
    interfaces: []
  };
  function forceCenter(center) {
    var $receiver = new ForceCenter();
    $receiver.center = center;
    return $receiver;
  }
  function ForceCenter() {
    this._nodes_0 = emptyList();
    this.center = new Point(0.0, 0.0);
  }
  ForceCenter.prototype.assignNodes_oydpol$ = function (nodes) {
    this._nodes_0 = nodes;
  };
  ForceCenter.prototype.applyForceToNodes_14dthe$ = function (intensity) {
    var size = this._nodes_0.size;
    var sx = {v: 0.0};
    var sy = {v: 0.0};
    var tmp$;
    tmp$ = this._nodes_0.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      sx.v += element.x;
      sy.v += element.y;
    }
    sx.v = sx.v / size - this.center.x;
    sy.v = sy.v / size - this.center.y;
    var tmp$_0;
    tmp$_0 = this._nodes_0.iterator();
    while (tmp$_0.hasNext()) {
      var element_0 = tmp$_0.next();
      element_0.x = element_0.x - sx.v;
      element_0.y = element_0.y - sy.v;
    }
  };
  ForceCenter.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ForceCenter',
    interfaces: [Force]
  };
  function forceCollision(init) {
    var $receiver = new ForceCollision();
    init($receiver);
    return $receiver;
  }
  function ForceCollision() {
    this.x_0 = ForceCollision$x$lambda;
    this.y_0 = ForceCollision$y$lambda;
    this.ri_0 = 0.0;
    this.ri2_0 = 0.0;
    this.xi_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.yi_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.currentNode_c4mtyc$_0 = this.currentNode_c4mtyc$_0;
    this.iterations_zciiwp$_0 = 1;
    this.strength_rfnizg$_0 = get_pct(70);
    this.radiusGet_evnjeb$_0 = ForceCollision$radiusGet$lambda;
    this._nodes_0 = emptyList();
    this._radiuses_0 = emptyList();
  }
  Object.defineProperty(ForceCollision.prototype, 'currentNode_0', {
    get: function () {
      if (this.currentNode_c4mtyc$_0 == null)
        return throwUPAE('currentNode');
      return this.currentNode_c4mtyc$_0;
    },
    set: function (currentNode) {
      this.currentNode_c4mtyc$_0 = currentNode;
    }
  });
  Object.defineProperty(ForceCollision.prototype, 'iterations', {
    get: function () {
      return this.iterations_zciiwp$_0;
    },
    set: function (value) {
      this.iterations_zciiwp$_0 = Math_0.max(1, value);
    }
  });
  Object.defineProperty(ForceCollision.prototype, 'strength', {
    get: function () {
      return this.strength_rfnizg$_0;
    },
    set: function (value) {
      this.strength_rfnizg$_0 = value.coerceToDefault();
    }
  });
  Object.defineProperty(ForceCollision.prototype, 'radiusGet', {
    get: function () {
      return this.radiusGet_evnjeb$_0;
    },
    set: function (value) {
      this.radiusGet_evnjeb$_0 = value;
      this.assignNodes_oydpol$(this._nodes_0);
    }
  });
  ForceCollision.prototype.assignNodes_oydpol$ = function (nodes) {
    this._nodes_0 = nodes;
    var transform = this.radiusGet;
    var destination = ArrayList_init(collectionSizeOrDefault(nodes, 10));
    var tmp$;
    tmp$ = nodes.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(transform(item));
    }
    this._radiuses_0 = destination;
  };
  ForceCollision.prototype.applyForceToNodes_14dthe$ = function (intensity) {
    var tmp$;
    tmp$ = until(0, this.iterations).iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      var tree = quadtree(this.x_0, this.y_0, this._nodes_0);
      visitAfter(tree, getCallableRef('prepare', function ($receiver, quad, x0, y0, x1, y1) {
        return $receiver.prepare_0(quad, x0, y0, x1, y1), Unit;
      }.bind(null, this)));
      var tmp$_0, tmp$_0_0;
      var index = 0;
      tmp$_0 = this._nodes_0.iterator();
      while (tmp$_0.hasNext()) {
        var item = tmp$_0.next();
        checkIndexOverflow((tmp$_0_0 = index, index = tmp$_0_0 + 1 | 0, tmp$_0_0));
        this.currentNode_0 = item;
        this.ri_0 = this._radiuses_0.get_za3lpa$(item.index);
        this.ri2_0 = this.ri_0 * this.ri_0;
        this.xi_0 = item.x + item.vx;
        this.yi_0 = item.y + item.vy;
        visit(tree, getCallableRef('applyForce', function ($receiver, quad, x0, y0, x1, y1) {
          return $receiver.applyForce_0(quad, x0, y0, x1, y1);
        }.bind(null, this)));
      }
    }
  };
  ForceCollision.prototype.applyForce_0 = function (quad, x0, y0, x1, y1) {
    var data = Kotlin.isType(quad, LeafNode) ? quad.data : null;
    var rj = ensureNotNull(quad.value);
    var r = this.ri_0 + rj;
    if (data != null) {
      if (data.index > this.currentNode_0.index) {
        var x = this.xi_0 - data.x - data.vx;
        var y = this.yi_0 - data.y - data.vy;
        var l = x * x + y * y;
        if (l < r * r) {
          if (x === 0.0) {
            x = jiggle();
            l += x * x;
          }
          if (y === 0.0) {
            y = jiggle();
            l += y * y;
          }
          var x_0 = l;
          var sqrtl = Math_0.sqrt(x_0);
          l = (r - sqrtl) / sqrtl * this.strength.value;
          x *= l;
          y *= l;
          rj *= rj;
          r = rj / (this.ri2_0 + rj);
          this.currentNode_0.vx = this.currentNode_0.vx + x * r;
          this.currentNode_0.vy = this.currentNode_0.vy + y * r;
          r = 1 - r;
          data.vx = data.vx - x * r;
          data.vy = data.vy - y * r;
        }
      }
      return false;
    }
    return x0 > this.xi_0 + r || x1 < this.xi_0 - r || y0 > this.yi_0 + r || y1 < this.yi_0 - r;
  };
  ForceCollision.prototype.prepare_0 = function (quad, x0, y0, x1, y1) {
    var tmp$;
    if (Kotlin.isType(quad, LeafNode)) {
      quad.value = this._radiuses_0.get_za3lpa$(quad.data.index);
      return;
    }
    quad.value = 0.0;
    var tmp$_0;
    tmp$_0 = toList(Kotlin.isType(tmp$ = quad, InternalNode) ? tmp$ : throwCCE()).iterator();
    while (tmp$_0.hasNext()) {
      var element = tmp$_0.next();
      if ((element != null ? element.value : null) != null && ensureNotNull(element.value) > ensureNotNull(quad.value)) {
        quad.value = element.value;
      }
    }
  };
  function ForceCollision$x$lambda(node) {
    return node.x;
  }
  function ForceCollision$y$lambda(node) {
    return node.y;
  }
  function ForceCollision$radiusGet$lambda($receiver) {
    return 100.0;
  }
  ForceCollision.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ForceCollision',
    interfaces: [Force]
  };
  function Link(source, target, distance, strength) {
    if (distance === void 0)
      distance = 30.0;
    if (strength === void 0)
      strength = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.source = source;
    this.target = target;
    this.distance = distance;
    this.strength = strength;
  }
  Link.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Link',
    interfaces: []
  };
  Link.prototype.component1 = function () {
    return this.source;
  };
  Link.prototype.component2 = function () {
    return this.target;
  };
  Link.prototype.component3 = function () {
    return this.distance;
  };
  Link.prototype.component4 = function () {
    return this.strength;
  };
  Link.prototype.copy_rnylw0$ = function (source, target, distance, strength) {
    return new Link(source === void 0 ? this.source : source, target === void 0 ? this.target : target, distance === void 0 ? this.distance : distance, strength === void 0 ? this.strength : strength);
  };
  Link.prototype.toString = function () {
    return 'Link(source=' + Kotlin.toString(this.source) + (', target=' + Kotlin.toString(this.target)) + (', distance=' + Kotlin.toString(this.distance)) + (', strength=' + Kotlin.toString(this.strength)) + ')';
  };
  Link.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.source) | 0;
    result = result * 31 + Kotlin.hashCode(this.target) | 0;
    result = result * 31 + Kotlin.hashCode(this.distance) | 0;
    result = result * 31 + Kotlin.hashCode(this.strength) | 0;
    return result;
  };
  Link.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.source, other.source) && Kotlin.equals(this.target, other.target) && Kotlin.equals(this.distance, other.distance) && Kotlin.equals(this.strength, other.strength)))));
  };
  function forceLink(init) {
    var $receiver = new ForceLink();
    init($receiver);
    return $receiver;
  }
  function ForceLink() {
    this._nodes_0 = emptyList();
    this._links_0 = emptyList();
    this.bias_0 = [];
    this.count_0 = [];
    this.iterations = 1;
    this.linkGet = ForceLink$linkGet$lambda;
  }
  Object.defineProperty(ForceLink.prototype, 'links', {
    get: function () {
      return this._links_0;
    }
  });
  ForceLink.prototype.assignNodes_oydpol$ = function (nodes) {
    this._nodes_0 = nodes;
    var transform = this.linkGet;
    var destination = ArrayList_init_0();
    var tmp$;
    tmp$ = nodes.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      var tmp$_0;
      if ((tmp$_0 = transform(element)) != null) {
        destination.add_11rb$(tmp$_0);
      }
    }
    this._links_0 = flatten(destination);
    var array = Array_0(nodes.size);
    var tmp$_1;
    tmp$_1 = array.length - 1 | 0;
    for (var i = 0; i <= tmp$_1; i++) {
      array[i] = 0;
    }
    this.count_0 = array;
    var tmp$_2, tmp$_0_0;
    var index = 0;
    tmp$_2 = this._links_0.iterator();
    while (tmp$_2.hasNext()) {
      var item = tmp$_2.next();
      checkIndexOverflow((tmp$_0_0 = index, index = tmp$_0_0 + 1 | 0, tmp$_0_0));
      var tmp$_3, tmp$_4, tmp$_5, tmp$_6;
      tmp$_3 = this.count_0;
      tmp$_4 = item.source.index;
      tmp$_3[tmp$_4] = tmp$_3[tmp$_4] + 1 | 0;
      tmp$_5 = this.count_0;
      tmp$_6 = item.target.index;
      tmp$_5[tmp$_6] = tmp$_5[tmp$_6] + 1 | 0;
    }
    var array_0 = Array_0(this._links_0.size);
    var tmp$_7;
    tmp$_7 = array_0.length - 1 | 0;
    for (var i_0 = 0; i_0 <= tmp$_7; i_0++) {
      array_0[i_0] = 0.0;
    }
    this.bias_0 = array_0;
    var tmp$_8, tmp$_0_1;
    var index_0 = 0;
    tmp$_8 = this._links_0.iterator();
    while (tmp$_8.hasNext()) {
      var item_0 = tmp$_8.next();
      this.bias_0[checkIndexOverflow((tmp$_0_1 = index_0, index_0 = tmp$_0_1 + 1 | 0, tmp$_0_1))] = this.count_0[item_0.source.index] / (this.count_0[item_0.source.index] + this.count_0[item_0.target.index] | 0);
    }
    this.initializeStrengths_0();
  };
  ForceLink.prototype.initializeStrengths_0 = function () {
    var $receiver = this._links_0;
    var destination = ArrayList_init_0();
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      if (isNaN_0(element.strength))
        destination.add_11rb$(element);
    }
    var tmp$_0;
    tmp$_0 = destination.iterator();
    while (tmp$_0.hasNext()) {
      var element_0 = tmp$_0.next();
      var a = this.count_0[element_0.source.index];
      var b = this.count_0[element_0.target.index];
      element_0.strength = 1.0 / Math_0.min(a, b);
    }
  };
  ForceLink.prototype.applyForceToNodes_14dthe$ = function (intensity) {
    var tmp$;
    tmp$ = until(0, this.iterations).iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      var tmp$_0, tmp$_0_0;
      var index = 0;
      tmp$_0 = this._links_0.iterator();
      while (tmp$_0.hasNext()) {
        var item = tmp$_0.next();
        var index_0 = checkIndexOverflow((tmp$_0_0 = index, index = tmp$_0_0 + 1 | 0, tmp$_0_0));
        var source = item.source;
        var target = item.target;
        var x = target.x + target.vx - source.x - source.vx;
        if (x === 0.0)
          x = jiggle();
        var y = target.y + target.vy - source.y - source.vy;
        if (y === 0.0)
          y = jiggle();
        var x_0 = x * x + y * y;
        var l = Math_0.sqrt(x_0);
        l = (l - item.distance) / l * intensity * item.strength;
        x *= l;
        y *= l;
        var b = this.bias_0[index_0];
        target.vx = target.vx - x * b;
        target.vy = target.vy - y * b;
        b = 1 - b;
        source.vx = source.vx + x * b;
        source.vy = source.vy + y * b;
      }
    }
  };
  function ForceLink$linkGet$lambda($receiver) {
    return null;
  }
  ForceLink.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ForceLink',
    interfaces: [Force]
  };
  function forceNBody(init) {
    var $receiver = new ForceNBody();
    init($receiver);
    return $receiver;
  }
  function ForceNBody() {
    this.theta2_0 = 0.81;
    this.distanceMin2_0 = 1.0;
    this.distanceMax2_0 = 10000.0;
    this.x_0 = ForceNBody$x$lambda;
    this.y_0 = ForceNBody$y$lambda;
    this.currentIntensity_0 = 0.0;
    this.currentNode_rw4rfm$_0 = this.currentNode_rw4rfm$_0;
    this.strengthGet_ddcgf8$_0 = ForceNBody$strengthGet$lambda;
    this._nodes_0 = emptyList();
    this._strengths_0 = emptyList();
  }
  Object.defineProperty(ForceNBody.prototype, 'currentNode_0', {
    get: function () {
      if (this.currentNode_rw4rfm$_0 == null)
        return throwUPAE('currentNode');
      return this.currentNode_rw4rfm$_0;
    },
    set: function (currentNode) {
      this.currentNode_rw4rfm$_0 = currentNode;
    }
  });
  Object.defineProperty(ForceNBody.prototype, 'theta', {
    get: function () {
      var x = this.theta2_0;
      return Math_0.sqrt(x);
    },
    set: function (value) {
      this.theta2_0 = value * value;
    }
  });
  Object.defineProperty(ForceNBody.prototype, 'distanceMin', {
    get: function () {
      var x = this.distanceMin2_0;
      return Math_0.sqrt(x);
    },
    set: function (value) {
      this.distanceMin2_0 = value * value;
    }
  });
  Object.defineProperty(ForceNBody.prototype, 'distanceMax', {
    get: function () {
      var x = this.distanceMax2_0;
      return Math_0.sqrt(x);
    },
    set: function (value) {
      this.distanceMax2_0 = value * value;
    }
  });
  Object.defineProperty(ForceNBody.prototype, 'strengthGet', {
    get: function () {
      return this.strengthGet_ddcgf8$_0;
    },
    set: function (value) {
      this.strengthGet_ddcgf8$_0 = value;
      this.assignNodes_oydpol$(this._nodes_0);
    }
  });
  ForceNBody.prototype.assignNodes_oydpol$ = function (nodes) {
    this._nodes_0 = nodes;
    var transform = this.strengthGet;
    var destination = ArrayList_init(collectionSizeOrDefault(nodes, 10));
    var tmp$;
    tmp$ = nodes.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(transform(item));
    }
    this._strengths_0 = destination;
  };
  ForceNBody.prototype.applyForceToNodes_14dthe$ = function (intensity) {
    this.currentIntensity_0 = intensity;
    var tree = quadtree(this.x_0, this.y_0, this._nodes_0);
    visitAfter(tree, getCallableRef('accumulate', function ($receiver, quad, x0, y0, x1, y1) {
      return $receiver.accumulate_0(quad, x0, y0, x1, y1), Unit;
    }.bind(null, this)));
    var tmp$, tmp$_0;
    var index = 0;
    tmp$ = this._nodes_0.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      checkIndexOverflow((tmp$_0 = index, index = tmp$_0 + 1 | 0, tmp$_0));
      this.currentNode_0 = item;
      visit(tree, getCallableRef('applyForce', function ($receiver, quad, x0, y0, x1, y1) {
        return $receiver.applyForce_0(quad, x0, y0, x1, y1);
      }.bind(null, this)));
    }
  };
  ForceNBody.prototype.applyForce_0 = function (quad, x0, y0, x1, y1) {
    var tmp$;
    if (quad.value == null)
      return true;
    var x = quad.x - this.currentNode_0.x;
    var y = quad.y - this.currentNode_0.y;
    var w = x1 - x0;
    var l = x * x + y * y;
    if (w * w / this.theta2_0 < l) {
      if (l < this.distanceMax2_0) {
        if (x === 0.0) {
          x = jiggle();
          l += x * x;
        }
        if (y === 0.0) {
          y = jiggle();
          l += y * y;
        }
        if (l < this.distanceMin2_0) {
          var x_0 = this.distanceMin2_0 * l;
          l = Math_0.sqrt(x_0);
        }
        var increment = ensureNotNull(quad.value) * this.currentIntensity_0 / l;
        this.currentNode_0.vx = this.currentNode_0.vx + x * increment;
        this.currentNode_0.vy = this.currentNode_0.vy + y * increment;
      }
      return true;
    }
     else if (Kotlin.isType(quad, InternalNode) || l >= this.distanceMax2_0)
      return false;
    var newQuad = (tmp$ = quad) == null || Kotlin.isType(tmp$, LeafNode) ? tmp$ : throwCCE();
    if (ensureNotNull(newQuad).data !== this.currentNode_0 || ensureNotNull(newQuad).next == null) {
      if (x === 0.0) {
        x = jiggle();
        l += x * x;
      }
      if (y === 0.0) {
        y = jiggle();
        l += y * y;
      }
      if (l < this.distanceMin2_0) {
        var x_1 = this.distanceMin2_0 * l;
        l = Math_0.sqrt(x_1);
      }
    }
    do {
      if (ensureNotNull(newQuad).data !== this.currentNode_0) {
        w = this._strengths_0.get_za3lpa$(ensureNotNull(newQuad).data.index) * this.currentIntensity_0 / l;
        this.currentNode_0.vx = this.currentNode_0.vx + x * w;
        this.currentNode_0.vy = this.currentNode_0.vy + y * w;
      }
      newQuad = ensureNotNull(newQuad).next;
    }
     while (newQuad != null);
    return false;
  };
  ForceNBody.prototype.accumulate_0 = function (quad, x0, y0, x1, y1) {
    var strength = {v: 0.0};
    var weight = {v: 0.0};
    if (Kotlin.isType(quad, InternalNode)) {
      var x = {v: 0.0};
      var y = {v: 0.0};
      var tmp$;
      tmp$ = toList(quad).iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        if ((element != null ? element.value : null) != null) {
          var x_0 = ensureNotNull(element.value);
          var c = Math_0.abs(x_0);
          strength.v += ensureNotNull(element.value);
          weight.v += c;
          x.v += c * element.x;
          y.v += c * element.y;
        }
      }
      quad.x = x.v / weight.v;
      quad.y = y.v / weight.v;
    }
     else if (Kotlin.isType(quad, LeafNode)) {
      var q = quad;
      ensureNotNull(q).x = q.data.x;
      q.y = q.data.y;
      do {
        strength.v += this._strengths_0.get_za3lpa$(ensureNotNull(q).data.index);
        q = q.next;
      }
       while (q != null);
    }
    quad.value = strength.v;
  };
  function ForceNBody$x$lambda(node) {
    return node.x;
  }
  function ForceNBody$y$lambda(node) {
    return node.y;
  }
  function ForceNBody$strengthGet$lambda($receiver) {
    return -30.0;
  }
  ForceNBody.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ForceNBody',
    interfaces: [Force]
  };
  function ForceNode(index, domain, x, y, vx, vy, fixedX, fixedY) {
    if (x === void 0)
      x = kotlin_js_internal_DoubleCompanionObject.NaN;
    if (y === void 0)
      y = kotlin_js_internal_DoubleCompanionObject.NaN;
    if (vx === void 0)
      vx = kotlin_js_internal_DoubleCompanionObject.NaN;
    if (vy === void 0)
      vy = kotlin_js_internal_DoubleCompanionObject.NaN;
    if (fixedX === void 0)
      fixedX = null;
    if (fixedY === void 0)
      fixedY = null;
    this.index = index;
    this.domain = domain;
    this.x = x;
    this.y = y;
    this.vx = vx;
    this.vy = vy;
    this.fixedX = fixedX;
    this.fixedY = fixedY;
  }
  Object.defineProperty(ForceNode.prototype, 'position', {
    get: function () {
      return point(this.x, this.y);
    },
    set: function (value) {
      this.x = value.x;
      this.y = value.y;
    }
  });
  Object.defineProperty(ForceNode.prototype, 'velocity', {
    get: function () {
      return new Vector(this.vx, this.vy);
    },
    set: function (value) {
      this.vx = value.vx;
      this.vy = value.vy;
    }
  });
  ForceNode.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ForceNode',
    interfaces: []
  };
  ForceNode.prototype.component1 = function () {
    return this.index;
  };
  ForceNode.prototype.component2 = function () {
    return this.domain;
  };
  ForceNode.prototype.component3 = function () {
    return this.x;
  };
  ForceNode.prototype.component4 = function () {
    return this.y;
  };
  ForceNode.prototype.component5 = function () {
    return this.vx;
  };
  ForceNode.prototype.component6 = function () {
    return this.vy;
  };
  ForceNode.prototype.component7 = function () {
    return this.fixedX;
  };
  ForceNode.prototype.component8 = function () {
    return this.fixedY;
  };
  ForceNode.prototype.copy_mydubp$ = function (index, domain, x, y, vx, vy, fixedX, fixedY) {
    return new ForceNode(index === void 0 ? this.index : index, domain === void 0 ? this.domain : domain, x === void 0 ? this.x : x, y === void 0 ? this.y : y, vx === void 0 ? this.vx : vx, vy === void 0 ? this.vy : vy, fixedX === void 0 ? this.fixedX : fixedX, fixedY === void 0 ? this.fixedY : fixedY);
  };
  ForceNode.prototype.toString = function () {
    return 'ForceNode(index=' + Kotlin.toString(this.index) + (', domain=' + Kotlin.toString(this.domain)) + (', x=' + Kotlin.toString(this.x)) + (', y=' + Kotlin.toString(this.y)) + (', vx=' + Kotlin.toString(this.vx)) + (', vy=' + Kotlin.toString(this.vy)) + (', fixedX=' + Kotlin.toString(this.fixedX)) + (', fixedY=' + Kotlin.toString(this.fixedY)) + ')';
  };
  ForceNode.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.index) | 0;
    result = result * 31 + Kotlin.hashCode(this.domain) | 0;
    result = result * 31 + Kotlin.hashCode(this.x) | 0;
    result = result * 31 + Kotlin.hashCode(this.y) | 0;
    result = result * 31 + Kotlin.hashCode(this.vx) | 0;
    result = result * 31 + Kotlin.hashCode(this.vy) | 0;
    result = result * 31 + Kotlin.hashCode(this.fixedX) | 0;
    result = result * 31 + Kotlin.hashCode(this.fixedY) | 0;
    return result;
  };
  ForceNode.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.index, other.index) && Kotlin.equals(this.domain, other.domain) && Kotlin.equals(this.x, other.x) && Kotlin.equals(this.y, other.y) && Kotlin.equals(this.vx, other.vx) && Kotlin.equals(this.vy, other.vy) && Kotlin.equals(this.fixedX, other.fixedX) && Kotlin.equals(this.fixedY, other.fixedY)))));
  };
  function ForcePoint() {
    this.defaultPoint_0 = point_0(0, 0);
    this.pointGet_7dr1bp$_0 = ForcePoint$pointGet$lambda(this);
    this.strengthGet_k93ayk$_0 = ForcePoint$strengthGet$lambda;
    this._nodes_0 = emptyList();
    this._strengths_0 = emptyList();
    this._x_0 = emptyList();
    this._y_0 = emptyList();
  }
  Object.defineProperty(ForcePoint.prototype, 'pointGet', {
    get: function () {
      return this.pointGet_7dr1bp$_0;
    },
    set: function (value) {
      this.pointGet_7dr1bp$_0 = value;
      this.assignNodes_oydpol$(this._nodes_0);
    }
  });
  Object.defineProperty(ForcePoint.prototype, 'strengthGet', {
    get: function () {
      return this.strengthGet_k93ayk$_0;
    },
    set: function (value) {
      this.strengthGet_k93ayk$_0 = value;
      this.assignNodes_oydpol$(this._nodes_0);
    }
  });
  ForcePoint.prototype.assignNodes_oydpol$ = function (nodes) {
    this._nodes_0 = nodes;
    var destination = ArrayList_init(collectionSizeOrDefault(nodes, 10));
    var tmp$;
    tmp$ = nodes.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(this.pointGet(item).x);
    }
    this._x_0 = destination;
    var destination_0 = ArrayList_init(collectionSizeOrDefault(nodes, 10));
    var tmp$_0;
    tmp$_0 = nodes.iterator();
    while (tmp$_0.hasNext()) {
      var item_0 = tmp$_0.next();
      destination_0.add_11rb$(this.pointGet(item_0).y);
    }
    this._y_0 = destination_0;
    var destination_1 = ArrayList_init(collectionSizeOrDefault(nodes, 10));
    var tmp$_1;
    tmp$_1 = nodes.iterator();
    while (tmp$_1.hasNext()) {
      var item_1 = tmp$_1.next();
      destination_1.add_11rb$(this.strengthGet(item_1).value);
    }
    this._strengths_0 = destination_1;
  };
  ForcePoint.prototype.applyForceToNodes_14dthe$ = function (intensity) {
    var tmp$, tmp$_0;
    var index = 0;
    tmp$ = this._nodes_0.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      var index_0 = checkIndexOverflow((tmp$_0 = index, index = tmp$_0 + 1 | 0, tmp$_0));
      item.vx = item.vx + (this._x_0.get_za3lpa$(index_0) - item.x) * this._strengths_0.get_za3lpa$(index_0) * intensity;
      item.vy = item.vy + (this._y_0.get_za3lpa$(index_0) - item.y) * this._strengths_0.get_za3lpa$(index_0) * intensity;
    }
  };
  function ForcePoint$pointGet$lambda(this$ForcePoint) {
    return function ($receiver) {
      return this$ForcePoint.defaultPoint_0;
    };
  }
  function ForcePoint$strengthGet$lambda($receiver) {
    return get_pct(10);
  }
  ForcePoint.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ForcePoint',
    interfaces: [Force]
  };
  function forceRadial(init) {
    var $receiver = new ForceRadial();
    init($receiver);
    return $receiver;
  }
  function ForceRadial() {
    this.radiusGet_15x1ks$_0 = ForceRadial$radiusGet$lambda;
    this.strengthGet_ovue0j$_0 = ForceRadial$strengthGet$lambda;
    this.centerGet_eye7ev$_0 = ForceRadial$centerGet$lambda(this);
    this.defaultCenter_0 = new Point(0.0, 0.0);
    this._nodes_0 = emptyList();
    this._strengths_0 = emptyList();
    this._centers_0 = emptyList();
    this._radiuses_0 = emptyList();
  }
  Object.defineProperty(ForceRadial.prototype, 'radiusGet', {
    get: function () {
      return this.radiusGet_15x1ks$_0;
    },
    set: function (value) {
      this.radiusGet_15x1ks$_0 = value;
      this.assignNodes_oydpol$(this._nodes_0);
    }
  });
  Object.defineProperty(ForceRadial.prototype, 'strengthGet', {
    get: function () {
      return this.strengthGet_ovue0j$_0;
    },
    set: function (value) {
      this.strengthGet_ovue0j$_0 = value;
      this.assignNodes_oydpol$(this._nodes_0);
    }
  });
  Object.defineProperty(ForceRadial.prototype, 'centerGet', {
    get: function () {
      return this.centerGet_eye7ev$_0;
    },
    set: function (value) {
      this.centerGet_eye7ev$_0 = value;
      this.assignNodes_oydpol$(this._nodes_0);
    }
  });
  ForceRadial.prototype.assignNodes_oydpol$ = function (nodes) {
    this._nodes_0 = nodes;
    var transform = this.radiusGet;
    var destination = ArrayList_init(collectionSizeOrDefault(nodes, 10));
    var tmp$;
    tmp$ = nodes.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(transform(item));
    }
    this._radiuses_0 = destination;
    var destination_0 = ArrayList_init(collectionSizeOrDefault(nodes, 10));
    var tmp$_0;
    tmp$_0 = nodes.iterator();
    while (tmp$_0.hasNext()) {
      var item_0 = tmp$_0.next();
      destination_0.add_11rb$(this.strengthGet(item_0).value);
    }
    this._strengths_0 = destination_0;
    var transform_0 = this.centerGet;
    var destination_1 = ArrayList_init(collectionSizeOrDefault(nodes, 10));
    var tmp$_1;
    tmp$_1 = nodes.iterator();
    while (tmp$_1.hasNext()) {
      var item_1 = tmp$_1.next();
      destination_1.add_11rb$(transform_0(item_1));
    }
    this._centers_0 = destination_1;
  };
  ForceRadial.prototype.applyForceToNodes_14dthe$ = function (intensity) {
    var tmp$, tmp$_0;
    var index = 0;
    tmp$ = this._nodes_0.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      var index_0 = checkIndexOverflow((tmp$_0 = index, index = tmp$_0 + 1 | 0, tmp$_0));
      var dx = item.x - this._centers_0.get_za3lpa$(index_0).x;
      if (dx === 0.0)
        dx = math.EPSILON;
      var dy = item.y - this._centers_0.get_za3lpa$(index_0).y;
      if (dy === 0.0)
        dy = math.EPSILON;
      var x = dx * dx + dy * dy;
      var r = Math_0.sqrt(x);
      var k = (this._radiuses_0.get_za3lpa$(index_0) - r) * this._strengths_0.get_za3lpa$(index_0) * intensity / r;
      item.vx = item.vx + dx * k;
      item.vy = item.vy + dy * k;
    }
  };
  function ForceRadial$radiusGet$lambda($receiver) {
    return 100.0;
  }
  function ForceRadial$strengthGet$lambda($receiver) {
    return get_pct(10);
  }
  function ForceRadial$centerGet$lambda(this$ForceRadial) {
    return function ($receiver) {
      return this$ForceRadial.defaultCenter_0;
    };
  }
  ForceRadial.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ForceRadial',
    interfaces: [Force]
  };
  var initialRadius;
  var initialAngle;
  function ForceSimulation() {
    this.started_0 = false;
    this.initForceNode_8rf9tp$_0 = ForceSimulation$initForceNode$lambda;
    this.domainObjects_611u28$_0 = emptyList();
    this._nodes_0 = emptyList();
    this._forces_0 = emptyList();
    this.tickEvents_0 = LinkedHashMap_init();
    this.endEvents_0 = LinkedHashMap_init();
    this.stepper_0 = timer(void 0, void 0, ForceSimulation$stepper$lambda(this));
    this.intensity_5watf1$_0 = get_pct(100);
    this.intensityMin_p4cf91$_0 = get_pct(0.1);
    var $receiver = this.intensityMin.value;
    var x = 1.0 / 300.0;
    this.intensityDecay_dufgj7$_0 = new Percent(1.0 - Math_0.pow($receiver, x));
    this.intensityTarget_h2jct8$_0 = get_pct(0);
    this._friction_0 = 0.6;
  }
  function ForceSimulation$forceX$lambda($receiver) {
    return Unit;
  }
  ForceSimulation.prototype.forceX_2ylmsr$ = function (init) {
    if (init === void 0)
      init = ForceSimulation$forceX$lambda;
    var tmp$;
    var $receiver = new ForceX();
    init($receiver);
    return Kotlin.isType(tmp$ = this.addForce_0($receiver), ForceX) ? tmp$ : throwCCE();
  };
  function ForceSimulation$forceY$lambda($receiver) {
    return Unit;
  }
  ForceSimulation.prototype.forceY_3ssvzq$ = function (init) {
    if (init === void 0)
      init = ForceSimulation$forceY$lambda;
    var tmp$;
    var $receiver = new ForceY();
    init($receiver);
    return Kotlin.isType(tmp$ = this.addForce_0($receiver), ForceY) ? tmp$ : throwCCE();
  };
  function ForceSimulation$forcePoint$lambda($receiver) {
    return Unit;
  }
  ForceSimulation.prototype.forcePoint_b8w4gj$ = function (init) {
    if (init === void 0)
      init = ForceSimulation$forcePoint$lambda;
    var tmp$;
    var $receiver = new ForcePoint();
    init($receiver);
    return Kotlin.isType(tmp$ = this.addForce_0($receiver), ForcePoint) ? tmp$ : throwCCE();
  };
  ForceSimulation.prototype.forceRadial_bv8b6k$ = function (init) {
    var tmp$;
    var $receiver = new ForceRadial();
    init($receiver);
    return Kotlin.isType(tmp$ = this.addForce_0($receiver), ForceRadial) ? tmp$ : throwCCE();
  };
  function ForceSimulation$forceNBody$lambda($receiver) {
    return Unit;
  }
  ForceSimulation.prototype.forceNBody_nma8s3$ = function (init) {
    if (init === void 0)
      init = ForceSimulation$forceNBody$lambda;
    var tmp$;
    var $receiver = new ForceNBody();
    init($receiver);
    return Kotlin.isType(tmp$ = this.addForce_0($receiver), ForceNBody) ? tmp$ : throwCCE();
  };
  ForceSimulation.prototype.forceCollision_kl9ov$ = function (init) {
    var tmp$;
    var $receiver = new ForceCollision();
    init($receiver);
    return Kotlin.isType(tmp$ = this.addForce_0($receiver), ForceCollision) ? tmp$ : throwCCE();
  };
  ForceSimulation.prototype.forceCenter_nwpf16$ = function (init) {
    var tmp$;
    var $receiver = new ForceCenter();
    init($receiver);
    return Kotlin.isType(tmp$ = this.addForce_0($receiver), ForceCenter) ? tmp$ : throwCCE();
  };
  function ForceSimulation$forceLink$lambda($receiver) {
    return Unit;
  }
  ForceSimulation.prototype.forceLink_efbme7$ = function (init) {
    if (init === void 0)
      init = ForceSimulation$forceLink$lambda;
    var tmp$;
    var $receiver = new ForceLink();
    init($receiver);
    return Kotlin.isType(tmp$ = this.addForce_0($receiver), ForceLink) ? tmp$ : throwCCE();
  };
  Object.defineProperty(ForceSimulation.prototype, 'initForceNode', {
    get: function () {
      return this.initForceNode_8rf9tp$_0;
    },
    set: function (value) {
      this.initForceNode_8rf9tp$_0 = value;
      this.initSimulation_0(true);
    }
  });
  Object.defineProperty(ForceSimulation.prototype, 'domainObjects', {
    get: function () {
      return this.domainObjects_611u28$_0;
    },
    set: function (value) {
      this.domainObjects_611u28$_0 = value;
      this.initSimulation_0(true);
    }
  });
  Object.defineProperty(ForceSimulation.prototype, 'nodes', {
    get: function () {
      return this._nodes_0;
    }
  });
  Object.defineProperty(ForceSimulation.prototype, 'forces', {
    get: function () {
      return this._forces_0;
    }
  });
  function ForceSimulation$play$lambda(this$ForceSimulation) {
    return function ($receiver, it) {
      this$ForceSimulation.step_0();
      return Unit;
    };
  }
  ForceSimulation.prototype.play = function () {
    this.stepper_0.restart_k9susy$(void 0, void 0, ForceSimulation$play$lambda(this));
  };
  ForceSimulation.prototype.stop = function () {
    this.stepper_0.stop();
  };
  ForceSimulation.prototype.step_0 = function () {
    this.tick();
    var tmp$;
    tmp$ = this.tickEvents_0.values.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      element(this);
    }
    if (this.intensity.compareTo_o5f5ne$(this.intensityMin) < 0) {
      this.stepper_0.stop();
      var tmp$_0;
      tmp$_0 = this.endEvents_0.values.iterator();
      while (tmp$_0.hasNext()) {
        var element_0 = tmp$_0.next();
        element_0(this);
      }
    }
  };
  Object.defineProperty(ForceSimulation.prototype, 'intensity', {
    get: function () {
      return this.intensity_5watf1$_0;
    },
    set: function (value) {
      this.intensity_5watf1$_0 = value.coerceAtLeast_o5f5ne$(get_pct(0));
    }
  });
  Object.defineProperty(ForceSimulation.prototype, 'intensityMin', {
    get: function () {
      return this.intensityMin_p4cf91$_0;
    },
    set: function (value) {
      this.intensityMin_p4cf91$_0 = value.coerceAtLeast_o5f5ne$(get_pct(0));
    }
  });
  Object.defineProperty(ForceSimulation.prototype, 'intensityDecay', {
    get: function () {
      return this.intensityDecay_dufgj7$_0;
    },
    set: function (value) {
      this.intensityDecay_dufgj7$_0 = value.coerceAtLeast_o5f5ne$(get_pct(0));
    }
  });
  Object.defineProperty(ForceSimulation.prototype, 'intensityTarget', {
    get: function () {
      return this.intensityTarget_h2jct8$_0;
    },
    set: function (value) {
      this.intensityTarget_h2jct8$_0 = value.coerceAtLeast_o5f5ne$(get_pct(0));
    }
  });
  Object.defineProperty(ForceSimulation.prototype, 'friction', {
    get: function () {
      return new Percent(1 - this._friction_0);
    },
    set: function (value) {
      this._friction_0 = 1 - value.coerceToDefault().value;
    }
  });
  ForceSimulation.prototype.addForce_0 = function (force) {
    this.initializeForce_0(force);
    this._forces_0 = plus(this._forces_0, force);
    return force;
  };
  ForceSimulation.prototype.initSimulation_0 = function (updateNodes) {
    this.initializeNodes_0(updateNodes);
    var tmp$;
    tmp$ = this._forces_0.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      this.initializeForce_0(element);
    }
  };
  ForceSimulation.prototype.removeForce_pf4102$ = function (force) {
    this._forces_0 = minus(this._forces_0, force);
  };
  ForceSimulation.prototype.initializeForce_0 = function (force) {
    force.assignNodes_oydpol$(this.nodes);
  };
  ForceSimulation.prototype.tick = function () {
    if (!this.started_0) {
      this.started_0 = true;
      this.initSimulation_0(false);
    }
    this.intensity = this.intensity.plus_o5f5ne$(this.intensityTarget.minus_o5f5ne$(this.intensity).times_o5f5ne$(this.intensityDecay));
    var tmp$;
    tmp$ = this._forces_0.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      element.applyForceToNodes_14dthe$(this.intensity.value);
    }
    var tmp$_0;
    tmp$_0 = this.nodes.iterator();
    while (tmp$_0.hasNext()) {
      var element_0 = tmp$_0.next();
      if (element_0.fixedX != null) {
        element_0.x = ensureNotNull(element_0.fixedX);
        element_0.vx = 0.0;
      }
       else {
        element_0.vx = element_0.vx * this._friction_0;
        element_0.x = element_0.x + element_0.vx;
      }
      if (element_0.fixedY != null) {
        element_0.y = ensureNotNull(element_0.fixedY);
        element_0.vy = 0.0;
      }
       else {
        element_0.vy = element_0.vy * this._friction_0;
        element_0.y = element_0.y + element_0.vy;
      }
    }
  };
  ForceSimulation.prototype.initializeNodes_0 = function (updateNodes) {
    var oldNodes = toList_0(this._nodes_0);
    var oldNodeSize = oldNodes.size;
    var size = this.domainObjects.size;
    var list = ArrayList_init(size);
    for (var index = 0; index < size; index++) {
      list.add_11rb$(new ForceNode(index, this.domainObjects.get_za3lpa$(index)));
    }
    this._nodes_0 = list;
    var tmp$, tmp$_0;
    var index_0 = 0;
    tmp$ = this.domainObjects.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      var index_1 = checkIndexOverflow((tmp$_0 = index_0, index_0 = tmp$_0 + 1 | 0, tmp$_0));
      var node = this._nodes_0.get_za3lpa$(index_1);
      if (updateNodes && index_1 < oldNodeSize && equals(oldNodes.get_za3lpa$(index_1).domain, node.domain)) {
        var oldNode = oldNodes.get_za3lpa$(index_1);
        node.position = oldNode.position;
        node.velocity = oldNode.velocity;
        node.fixedX = oldNode.fixedX;
        node.fixedY = oldNode.fixedY;
      }
       else {
        this.initForceNode(node);
        if (isNaN_0(node.x) || isNaN_0(node.y)) {
          var radius = initialRadius * Math_0.sqrt(index_1);
          var angle = index_1 * initialAngle;
          node.x = radius * Math_0.cos(angle);
          node.y = radius * Math_0.sin(angle);
        }
        if (isNaN_0(node.vx) || isNaN_0(node.vy)) {
          node.vx = 0.0;
          node.vy = 0.0;
        }
      }
    }
  };
  ForceSimulation.prototype.find_25aop5$ = function (point, radius) {
    if (radius === void 0)
      radius = kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY;
    var newRadius = {v: radius < kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY ? radius * radius : radius};
    var closest = {v: null};
    var tmp$;
    tmp$ = this.nodes.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      var dx = point.x - element.x;
      var dy = point.y - element.y;
      var d2 = dx * dx + dy * dy;
      if (d2 < newRadius.v) {
        closest.v = element;
        newRadius.v = d2;
      }
    }
    return closest.v;
  };
  ForceSimulation.prototype.on_npiyv9$ = function (type, name, callback) {
    switch (type.name) {
      case 'TICK':
        this.tickEvents_0.put_xwzc9p$(name, callback);
        break;
      case 'END':
        this.endEvents_0.put_xwzc9p$(name, callback);
        break;
    }
  };
  function ForceSimulation$initForceNode$lambda($receiver) {
    return Unit;
  }
  function ForceSimulation$stepper$lambda(this$ForceSimulation) {
    return function ($receiver, it) {
      this$ForceSimulation.step_0();
      return Unit;
    };
  }
  ForceSimulation.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ForceSimulation',
    interfaces: []
  };
  function SimulationEvent(name, ordinal) {
    Enum.call(this);
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function SimulationEvent_initFields() {
    SimulationEvent_initFields = function () {
    };
    SimulationEvent$TICK_instance = new SimulationEvent('TICK', 0);
    SimulationEvent$END_instance = new SimulationEvent('END', 1);
  }
  var SimulationEvent$TICK_instance;
  function SimulationEvent$TICK_getInstance() {
    SimulationEvent_initFields();
    return SimulationEvent$TICK_instance;
  }
  var SimulationEvent$END_instance;
  function SimulationEvent$END_getInstance() {
    SimulationEvent_initFields();
    return SimulationEvent$END_instance;
  }
  SimulationEvent.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'SimulationEvent',
    interfaces: [Enum]
  };
  function SimulationEvent$values() {
    return [SimulationEvent$TICK_getInstance(), SimulationEvent$END_getInstance()];
  }
  SimulationEvent.values = SimulationEvent$values;
  function SimulationEvent$valueOf(name) {
    switch (name) {
      case 'TICK':
        return SimulationEvent$TICK_getInstance();
      case 'END':
        return SimulationEvent$END_getInstance();
      default:throwISE('No enum constant io.data2viz.force.SimulationEvent.' + name);
    }
  }
  SimulationEvent.valueOf_61zpoe$ = SimulationEvent$valueOf;
  function forceX(init) {
    var $receiver = new ForceX();
    init($receiver);
    return $receiver;
  }
  function ForceX() {
    this.xGet_lwekl7$_0 = ForceX$xGet$lambda;
    this.strengthGet_dagrdw$_0 = ForceX$strengthGet$lambda;
    this._nodes_0 = emptyList();
    this._strengths_0 = emptyList();
    this._x_0 = emptyList();
  }
  Object.defineProperty(ForceX.prototype, 'xGet', {
    get: function () {
      return this.xGet_lwekl7$_0;
    },
    set: function (value) {
      this.xGet_lwekl7$_0 = value;
      this.assignNodes_oydpol$(this._nodes_0);
    }
  });
  Object.defineProperty(ForceX.prototype, 'strengthGet', {
    get: function () {
      return this.strengthGet_dagrdw$_0;
    },
    set: function (value) {
      this.strengthGet_dagrdw$_0 = value;
      this.assignNodes_oydpol$(this._nodes_0);
    }
  });
  ForceX.prototype.assignNodes_oydpol$ = function (nodes) {
    this._nodes_0 = nodes;
    var transform = this.xGet;
    var destination = ArrayList_init(collectionSizeOrDefault(nodes, 10));
    var tmp$;
    tmp$ = nodes.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(transform(item));
    }
    this._x_0 = destination;
    var destination_0 = ArrayList_init(collectionSizeOrDefault(nodes, 10));
    var tmp$_0;
    tmp$_0 = nodes.iterator();
    while (tmp$_0.hasNext()) {
      var item_0 = tmp$_0.next();
      destination_0.add_11rb$(this.strengthGet(item_0).value);
    }
    this._strengths_0 = destination_0;
  };
  ForceX.prototype.applyForceToNodes_14dthe$ = function (intensity) {
    var tmp$, tmp$_0;
    var index = 0;
    tmp$ = this._nodes_0.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      var index_0 = checkIndexOverflow((tmp$_0 = index, index = tmp$_0 + 1 | 0, tmp$_0));
      item.vx = item.vx + (this._x_0.get_za3lpa$(index_0) - item.x) * this._strengths_0.get_za3lpa$(index_0) * intensity;
    }
  };
  function ForceX$xGet$lambda($receiver) {
    return 0.0;
  }
  function ForceX$strengthGet$lambda($receiver) {
    return get_pct(10);
  }
  ForceX.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ForceX',
    interfaces: [Force]
  };
  function forceY(init) {
    var $receiver = new ForceY();
    init($receiver);
    return $receiver;
  }
  function ForceY() {
    this.yGet_77ghrt$_0 = ForceY$yGet$lambda;
    this.strengthGet_lngmx1$_0 = ForceY$strengthGet$lambda;
    this._nodes_0 = emptyList();
    this._strengths_0 = emptyList();
    this._y_0 = emptyList();
  }
  Object.defineProperty(ForceY.prototype, 'yGet', {
    get: function () {
      return this.yGet_77ghrt$_0;
    },
    set: function (value) {
      this.yGet_77ghrt$_0 = value;
      this.assignNodes_oydpol$(this._nodes_0);
    }
  });
  Object.defineProperty(ForceY.prototype, 'strengthGet', {
    get: function () {
      return this.strengthGet_lngmx1$_0;
    },
    set: function (value) {
      this.strengthGet_lngmx1$_0 = value;
      this.assignNodes_oydpol$(this._nodes_0);
    }
  });
  ForceY.prototype.assignNodes_oydpol$ = function (nodes) {
    this._nodes_0 = nodes;
    var transform = this.yGet;
    var destination = ArrayList_init(collectionSizeOrDefault(nodes, 10));
    var tmp$;
    tmp$ = nodes.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(transform(item));
    }
    this._y_0 = destination;
    var destination_0 = ArrayList_init(collectionSizeOrDefault(nodes, 10));
    var tmp$_0;
    tmp$_0 = nodes.iterator();
    while (tmp$_0.hasNext()) {
      var item_0 = tmp$_0.next();
      destination_0.add_11rb$(this.strengthGet(item_0).value);
    }
    this._strengths_0 = destination_0;
  };
  ForceY.prototype.applyForceToNodes_14dthe$ = function (intensity) {
    var tmp$, tmp$_0;
    var index = 0;
    tmp$ = this._nodes_0.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      var index_0 = checkIndexOverflow((tmp$_0 = index, index = tmp$_0 + 1 | 0, tmp$_0));
      item.vy = item.vy + (this._y_0.get_za3lpa$(index_0) - item.y) * this._strengths_0.get_za3lpa$(index_0) * intensity;
    }
  };
  function ForceY$yGet$lambda($receiver) {
    return 0.0;
  }
  function ForceY$strengthGet$lambda($receiver) {
    return get_pct(10);
  }
  ForceY.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ForceY',
    interfaces: [Force]
  };
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$force = package$data2viz.force || (package$data2viz.force = {});
  package$force.forceSimulation_3y4g3l$ = forceSimulation;
  package$force.jiggle_8be2vx$ = jiggle;
  package$force.Force = Force;
  package$force.forceCenter_jkkpf5$ = forceCenter;
  package$force.ForceCenter = ForceCenter;
  package$force.forceCollision_3wu9z8$ = forceCollision;
  package$force.ForceCollision = ForceCollision;
  package$force.Link = Link;
  package$force.forceLink_1a67fw$ = forceLink;
  package$force.ForceLink = ForceLink;
  package$force.forceNBody_mg1xz2$ = forceNBody;
  package$force.ForceNBody = ForceNBody;
  package$force.ForceNode = ForceNode;
  package$force.ForcePoint = ForcePoint;
  package$force.forceRadial_e8eyjr$ = forceRadial;
  package$force.ForceRadial = ForceRadial;
  package$force.ForceSimulation = ForceSimulation;
  Object.defineProperty(SimulationEvent, 'TICK', {
    get: SimulationEvent$TICK_getInstance
  });
  Object.defineProperty(SimulationEvent, 'END', {
    get: SimulationEvent$END_getInstance
  });
  package$force.SimulationEvent = SimulationEvent;
  package$force.forceX_fvwcfq$ = forceX;
  package$force.ForceX = ForceX;
  package$force.forceY_es05p1$ = forceY;
  package$force.ForceY = ForceY;
  initialRadius = 10.0;
  initialAngle = math_0.PI * (3.0 - Math_0.sqrt(5.0));
  Kotlin.defineModule('d2v-force-js', _);
  return _;
}));

//# sourceMappingURL=d2v-force-js.js.map
