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
  var Kind_INTERFACE = Kotlin.Kind.INTERFACE;
  var Unit = Kotlin.kotlin.Unit;
  var Point = $module$d2v_core_js.io.data2viz.geom.Point;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var throwUPAE = Kotlin.throwUPAE;
  var until = Kotlin.kotlin.ranges.until_dqglrj$;
  var quadtree = $module$d2v_quadtree_js.io.data2viz.quadtree.quadtree_tz1x4d$;
  var getCallableRef = Kotlin.getCallableRef;
  var visitAfter = $module$d2v_quadtree_js.io.data2viz.quadtree.visitAfter_690jwc$;
  var visit = $module$d2v_quadtree_js.io.data2viz.quadtree.visit_jsbco0$;
  var LeafNode = $module$d2v_quadtree_js.io.data2viz.quadtree.LeafNode;
  var ensureNotNull = Kotlin.ensureNotNull;
  var InternalNode = $module$d2v_quadtree_js.io.data2viz.quadtree.InternalNode;
  var throwCCE = Kotlin.throwCCE;
  var toList = $module$d2v_quadtree_js.io.data2viz.quadtree.toList_dw9rom$;
  var kotlin_js_internal_DoubleCompanionObject = Kotlin.kotlin.js.internal.DoubleCompanionObject;
  var Random = Kotlin.kotlin.random.Random;
  var math = $module$d2v_core_js.io.data2viz.math;
  var rangeTo = Kotlin.kotlin.ranges.rangeTo_38ydlf$;
  var isNaN_0 = Kotlin.kotlin.isNaN_yrwdxr$;
  var timer = $module$d2v_timer_js.io.data2viz.timer.timer_k9susy$;
  var Enum = Kotlin.kotlin.Enum;
  var throwISE = Kotlin.throwISE;
  SimulationEvent.prototype = Object.create(Enum.prototype);
  SimulationEvent.prototype.constructor = SimulationEvent;
  function Force() {
  }
  Force.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Force',
    interfaces: []
  };
  function forceCenter(center) {
    return new ForceCenter(center);
  }
  var emptyList = Kotlin.kotlin.collections.emptyList_287e2$;
  function ForceCenter(center) {
    if (center === void 0)
      center = new Point(0.0, 0.0);
    this.center = center;
    this._nodes_0 = emptyList();
  }
  ForceCenter.prototype.assignNodes_qipxwu$ = function (nodes) {
    this._nodes_0 = nodes;
  };
  ForceCenter.prototype.applyForceToNodes_14dthe$ = function (alpha) {
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
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  function ForceCollision() {
    this.x_0 = ForceCollision$x$lambda;
    this.y_0 = ForceCollision$y$lambda;
    this.ri_0 = 0.0;
    this.ri2_0 = 0.0;
    this.xi_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.yi_0 = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.currentNode_c4mtyc$_0 = this.currentNode_c4mtyc$_0;
    this.iterations = 1;
    this.strength = 0.7;
    this.radius_z5yoph$_0 = ForceCollision$radius$lambda;
    this.nodes_0 = emptyList();
    this.radiuses_0 = ArrayList_init();
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
  Object.defineProperty(ForceCollision.prototype, 'radius', {
    get: function () {
      return this.radius_z5yoph$_0;
    },
    set: function (value) {
      this.radius_z5yoph$_0 = value;
      this.assignNodes_qipxwu$(this.nodes_0);
    }
  });
  var checkIndexOverflow = Kotlin.kotlin.collections.checkIndexOverflow_za3lpa$;
  ForceCollision.prototype.assignNodes_qipxwu$ = function (nodes) {
    this.nodes_0 = nodes;
    this.radiuses_0.clear();
    var tmp$, tmp$_0;
    var index = 0;
    tmp$ = nodes.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      this.radiuses_0.add_11rb$(this.radius(item, checkIndexOverflow((tmp$_0 = index, index = tmp$_0 + 1 | 0, tmp$_0)), nodes));
    }
  };
  ForceCollision.prototype.applyForceToNodes_14dthe$ = function (alpha) {
    var tmp$;
    tmp$ = until(0, this.iterations).iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      var tree = quadtree(this.x_0, this.y_0, this.nodes_0);
      visitAfter(tree, getCallableRef('prepare', function ($receiver, quad, x0, y0, x1, y1) {
        return $receiver.prepare_0(quad, x0, y0, x1, y1), Unit;
      }.bind(null, this)));
      var tmp$_0, tmp$_0_0;
      var index = 0;
      tmp$_0 = this.nodes_0.iterator();
      while (tmp$_0.hasNext()) {
        var item = tmp$_0.next();
        checkIndexOverflow((tmp$_0_0 = index, index = tmp$_0_0 + 1 | 0, tmp$_0_0));
        this.currentNode_0 = item;
        this.ri_0 = this.radiuses_0.get_za3lpa$(item.index);
        this.ri2_0 = this.ri_0 * this.ri_0;
        this.xi_0 = item.x + item.vx;
        this.yi_0 = item.y + item.vy;
        visit(tree, getCallableRef('applyForce', function ($receiver, quad, x0, y0, x1, y1) {
          return $receiver.applyForce_0(quad, x0, y0, x1, y1);
        }.bind(null, this)));
      }
    }
  };
  var Math_0 = Math;
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
          l = (r - sqrtl) / sqrtl * this.strength;
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
      quad.value = this.radiuses_0.get_za3lpa$(quad.data.index);
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
  function ForceCollision$radius$lambda(f, f_0, f_1) {
    return 100.0;
  }
  ForceCollision.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ForceCollision',
    interfaces: [Force]
  };
  function Link(source, target, _index) {
    if (_index === void 0)
      _index = 0;
    this.source = source;
    this.target = target;
    this._index_8be2vx$ = _index;
  }
  Object.defineProperty(Link.prototype, 'index', {
    get: function () {
      return this._index_8be2vx$;
    }
  });
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
  Link.prototype.component3_8be2vx$ = function () {
    return this._index_8be2vx$;
  };
  Link.prototype.copy_6p19ak$ = function (source, target, _index) {
    return new Link(source === void 0 ? this.source : source, target === void 0 ? this.target : target, _index === void 0 ? this._index_8be2vx$ : _index);
  };
  Link.prototype.toString = function () {
    return 'Link(source=' + Kotlin.toString(this.source) + (', target=' + Kotlin.toString(this.target)) + (', _index=' + Kotlin.toString(this._index_8be2vx$)) + ')';
  };
  Link.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.source) | 0;
    result = result * 31 + Kotlin.hashCode(this.target) | 0;
    result = result * 31 + Kotlin.hashCode(this._index_8be2vx$) | 0;
    return result;
  };
  Link.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.source, other.source) && Kotlin.equals(this.target, other.target) && Kotlin.equals(this._index_8be2vx$, other._index_8be2vx$)))));
  };
  function forceLink$lambda($receiver) {
    return Unit;
  }
  function forceLink(init) {
    if (init === void 0)
      init = forceLink$lambda;
    var $receiver = new ForceLink();
    init($receiver);
    return $receiver;
  }
  function ForceLink() {
    this.nodes_0 = emptyList();
    this._links_0 = emptyList();
    this.distances_0 = emptyList();
    this.strengths_0 = emptyList();
    this.bias_0 = [];
    this.count_0 = [];
    this.iterations = 1;
    this.linksAccessor = ForceLink$linksAccessor$lambda;
    this.strengthsAccessor_wz01rg$_0 = ForceLink$strengthsAccessor$lambda(this);
    this.distancesAccessor_z4gers$_0 = ForceLink$distancesAccessor$lambda;
  }
  Object.defineProperty(ForceLink.prototype, 'links', {
    get: function () {
      return this._links_0;
    }
  });
  Object.defineProperty(ForceLink.prototype, 'strengthsAccessor', {
    get: function () {
      return this.strengthsAccessor_wz01rg$_0;
    },
    set: function (value) {
      this.strengthsAccessor_wz01rg$_0 = value;
      this.initializeStrengths_0();
    }
  });
  Object.defineProperty(ForceLink.prototype, 'distancesAccessor', {
    get: function () {
      return this.distancesAccessor_z4gers$_0;
    },
    set: function (value) {
      this.distancesAccessor_z4gers$_0 = value;
      this.initializeDistances_0();
    }
  });
  var Array_0 = Array;
  ForceLink.prototype.assignNodes_qipxwu$ = function (nodes) {
    this.nodes_0 = nodes;
    this._links_0 = this.linksAccessor(nodes);
    var array = Array_0(nodes.size);
    var tmp$;
    tmp$ = array.length - 1 | 0;
    for (var i = 0; i <= tmp$; i++) {
      array[i] = 0;
    }
    this.count_0 = array;
    var tmp$_0, tmp$_0_0;
    var index = 0;
    tmp$_0 = this._links_0.iterator();
    while (tmp$_0.hasNext()) {
      var item = tmp$_0.next();
      var tmp$_1, tmp$_2, tmp$_3, tmp$_4;
      item._index_8be2vx$ = checkIndexOverflow((tmp$_0_0 = index, index = tmp$_0_0 + 1 | 0, tmp$_0_0));
      tmp$_1 = this.count_0;
      tmp$_2 = item.source.index;
      tmp$_1[tmp$_2] = tmp$_1[tmp$_2] + 1 | 0;
      tmp$_3 = this.count_0;
      tmp$_4 = item.target.index;
      tmp$_3[tmp$_4] = tmp$_3[tmp$_4] + 1 | 0;
    }
    var array_0 = Array_0(this._links_0.size);
    var tmp$_5;
    tmp$_5 = array_0.length - 1 | 0;
    for (var i_0 = 0; i_0 <= tmp$_5; i_0++) {
      array_0[i_0] = 0.0;
    }
    this.bias_0 = array_0;
    var tmp$_6, tmp$_0_1;
    var index_0 = 0;
    tmp$_6 = this._links_0.iterator();
    while (tmp$_6.hasNext()) {
      var item_0 = tmp$_6.next();
      this.bias_0[checkIndexOverflow((tmp$_0_1 = index_0, index_0 = tmp$_0_1 + 1 | 0, tmp$_0_1))] = this.count_0[item_0.source.index] / (this.count_0[item_0.source.index] + this.count_0[item_0.target.index] | 0);
    }
    this.initializeStrengths_0();
    this.initializeDistances_0();
  };
  ForceLink.prototype.initializeDistances_0 = function () {
    this.distances_0 = this.distancesAccessor(this._links_0);
  };
  ForceLink.prototype.initializeStrengths_0 = function () {
    this.strengths_0 = this.strengthsAccessor(this._links_0);
  };
  ForceLink.prototype.applyForceToNodes_14dthe$ = function (alpha) {
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
        l = (l - this.distances_0.get_za3lpa$(index_0)) / l * alpha * this.strengths_0.get_za3lpa$(index_0);
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
  function ForceLink$linksAccessor$lambda(it) {
    return emptyList();
  }
  var collectionSizeOrDefault = Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$;
  var ArrayList_init_0 = Kotlin.kotlin.collections.ArrayList_init_ww73n8$;
  function ForceLink$strengthsAccessor$lambda(this$ForceLink) {
    return function (links) {
      var destination = ArrayList_init_0(collectionSizeOrDefault(links, 10));
      var tmp$;
      tmp$ = links.iterator();
      while (tmp$.hasNext()) {
        var item = tmp$.next();
        var tmp$_0 = destination.add_11rb$;
        var this$ForceLink_0 = this$ForceLink;
        var a = this$ForceLink_0.count_0[item.source.index];
        var b = this$ForceLink_0.count_0[item.target.index];
        tmp$_0.call(destination, 1.0 / Math_0.min(a, b));
      }
      return destination;
    };
  }
  function ForceLink$distancesAccessor$lambda(links) {
    var $receiver = until(0, links.size);
    var destination = ArrayList_init_0(collectionSizeOrDefault($receiver, 10));
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(30.0);
    }
    return destination;
  }
  ForceLink.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ForceLink',
    interfaces: [Force]
  };
  function forceNBody$lambda($receiver) {
    return Unit;
  }
  function forceNBody(init) {
    if (init === void 0)
      init = forceNBody$lambda;
    var $receiver = new ForceNBody();
    init($receiver);
    return $receiver;
  }
  function jiggle() {
    return (Random.Default.nextDouble() - 0.5) * math.EPSILON;
  }
  function ForceNBody() {
    this.theta2_0 = 0.81;
    this.distanceMin2_0 = 1.0;
    this.distanceMax2_0 = 10000.0;
    this.x_0 = ForceNBody$x$lambda;
    this.y_0 = ForceNBody$y$lambda;
    this.currentAlpha_0 = 0.0;
    this.currentNode_rw4rfm$_0 = this.currentNode_rw4rfm$_0;
    this.strength_wsc15m$_0 = ForceNBody$strength$lambda;
    this.nodes_0 = emptyList();
    this.strengths_0 = ArrayList_init();
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
  Object.defineProperty(ForceNBody.prototype, 'strength', {
    get: function () {
      return this.strength_wsc15m$_0;
    },
    set: function (value) {
      this.strength_wsc15m$_0 = value;
      this.assignNodes_qipxwu$(this.nodes_0);
    }
  });
  ForceNBody.prototype.assignNodes_qipxwu$ = function (nodes) {
    this.nodes_0 = nodes;
    this.strengths_0.clear();
    var tmp$, tmp$_0;
    var index = 0;
    tmp$ = nodes.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      this.strengths_0.add_11rb$(this.strength(item, checkIndexOverflow((tmp$_0 = index, index = tmp$_0 + 1 | 0, tmp$_0)), nodes));
    }
  };
  ForceNBody.prototype.applyForceToNodes_14dthe$ = function (alpha) {
    this.currentAlpha_0 = alpha;
    var tree = quadtree(this.x_0, this.y_0, this.nodes_0);
    visitAfter(tree, getCallableRef('accumulate', function ($receiver, quad, x0, y0, x1, y1) {
      return $receiver.accumulate_0(quad, x0, y0, x1, y1), Unit;
    }.bind(null, this)));
    var tmp$, tmp$_0;
    var index = 0;
    tmp$ = this.nodes_0.iterator();
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
        var increment = ensureNotNull(quad.value) * this.currentAlpha_0 / l;
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
        w = this.strengths_0.get_za3lpa$(ensureNotNull(newQuad).data.index) * this.currentAlpha_0 / l;
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
        strength.v += this.strengths_0.get_za3lpa$(ensureNotNull(q).data.index);
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
  function ForceNBody$strength$lambda(f, f_0, f_1) {
    return -30.0;
  }
  ForceNBody.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ForceNBody',
    interfaces: [Force]
  };
  function ForceNode(index, x, y, vx, vy, fixedX, fixedY) {
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
    this.x = x;
    this.y = y;
    this.vx = vx;
    this.vy = vy;
    this.fixedX = fixedX;
    this.fixedY = fixedY;
  }
  ForceNode.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ForceNode',
    interfaces: []
  };
  ForceNode.prototype.component1 = function () {
    return this.index;
  };
  ForceNode.prototype.component2 = function () {
    return this.x;
  };
  ForceNode.prototype.component3 = function () {
    return this.y;
  };
  ForceNode.prototype.component4 = function () {
    return this.vx;
  };
  ForceNode.prototype.component5 = function () {
    return this.vy;
  };
  ForceNode.prototype.component6 = function () {
    return this.fixedX;
  };
  ForceNode.prototype.component7 = function () {
    return this.fixedY;
  };
  ForceNode.prototype.copy_t5b7g6$ = function (index, x, y, vx, vy, fixedX, fixedY) {
    return new ForceNode(index === void 0 ? this.index : index, x === void 0 ? this.x : x, y === void 0 ? this.y : y, vx === void 0 ? this.vx : vx, vy === void 0 ? this.vy : vy, fixedX === void 0 ? this.fixedX : fixedX, fixedY === void 0 ? this.fixedY : fixedY);
  };
  ForceNode.prototype.toString = function () {
    return 'ForceNode(index=' + Kotlin.toString(this.index) + (', x=' + Kotlin.toString(this.x)) + (', y=' + Kotlin.toString(this.y)) + (', vx=' + Kotlin.toString(this.vx)) + (', vy=' + Kotlin.toString(this.vy)) + (', fixedX=' + Kotlin.toString(this.fixedX)) + (', fixedY=' + Kotlin.toString(this.fixedY)) + ')';
  };
  ForceNode.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.index) | 0;
    result = result * 31 + Kotlin.hashCode(this.x) | 0;
    result = result * 31 + Kotlin.hashCode(this.y) | 0;
    result = result * 31 + Kotlin.hashCode(this.vx) | 0;
    result = result * 31 + Kotlin.hashCode(this.vy) | 0;
    result = result * 31 + Kotlin.hashCode(this.fixedX) | 0;
    result = result * 31 + Kotlin.hashCode(this.fixedY) | 0;
    return result;
  };
  ForceNode.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.index, other.index) && Kotlin.equals(this.x, other.x) && Kotlin.equals(this.y, other.y) && Kotlin.equals(this.vx, other.vx) && Kotlin.equals(this.vy, other.vy) && Kotlin.equals(this.fixedX, other.fixedX) && Kotlin.equals(this.fixedY, other.fixedY)))));
  };
  function forceRadial(init) {
    var $receiver = new ForceRadial();
    init($receiver);
    return $receiver;
  }
  function ForceRadial() {
    this.radius_dt8oje$_0 = ForceRadial$radius$lambda;
    this.strength_1pnqwb$_0 = ForceRadial$strength$lambda;
    this.center_8shplj$_0 = ForceRadial$center$lambda(this);
    this.defaultCenter_0 = new Point(0.0, 0.0);
    this.nodes_0 = emptyList();
    this.strengths_0 = ArrayList_init();
    this.centers_0 = ArrayList_init();
    this.radiuses_0 = ArrayList_init();
  }
  Object.defineProperty(ForceRadial.prototype, 'radius', {
    get: function () {
      return this.radius_dt8oje$_0;
    },
    set: function (value) {
      this.radius_dt8oje$_0 = value;
      this.assignNodes_qipxwu$(this.nodes_0);
    }
  });
  Object.defineProperty(ForceRadial.prototype, 'strength', {
    get: function () {
      return this.strength_1pnqwb$_0;
    },
    set: function (value) {
      this.strength_1pnqwb$_0 = value;
      this.assignNodes_qipxwu$(this.nodes_0);
    }
  });
  Object.defineProperty(ForceRadial.prototype, 'center', {
    get: function () {
      return this.center_8shplj$_0;
    },
    set: function (value) {
      this.center_8shplj$_0 = value;
      this.assignNodes_qipxwu$(this.nodes_0);
    }
  });
  ForceRadial.prototype.assignNodes_qipxwu$ = function (nodes) {
    this.nodes_0 = nodes;
    this.radiuses_0.clear();
    this.centers_0.clear();
    this.strengths_0.clear();
    var tmp$, tmp$_0;
    var index = 0;
    tmp$ = nodes.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      var index_0 = checkIndexOverflow((tmp$_0 = index, index = tmp$_0 + 1 | 0, tmp$_0));
      this.radiuses_0.add_11rb$(this.radius(item, index_0, nodes));
      this.centers_0.add_11rb$(this.center(item, index_0, nodes));
      this.strengths_0.add_11rb$(this.strength(item, index_0, nodes));
    }
  };
  ForceRadial.prototype.applyForceToNodes_14dthe$ = function (alpha) {
    var tmp$, tmp$_0;
    var index = 0;
    tmp$ = this.nodes_0.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      var index_0 = checkIndexOverflow((tmp$_0 = index, index = tmp$_0 + 1 | 0, tmp$_0));
      var dx = item.x - this.centers_0.get_za3lpa$(index_0).x;
      if (dx === 0.0)
        dx = math.EPSILON;
      var dy = item.y - this.centers_0.get_za3lpa$(index_0).y;
      if (dy === 0.0)
        dy = math.EPSILON;
      var x = dx * dx + dy * dy;
      var r = Math_0.sqrt(x);
      var k = (this.radiuses_0.get_za3lpa$(index_0) - r) * this.strengths_0.get_za3lpa$(index_0) * alpha / r;
      item.vx = item.vx + dx * k;
      item.vy = item.vy + dy * k;
    }
  };
  function ForceRadial$radius$lambda(f, f_0, f_1) {
    return 100.0;
  }
  function ForceRadial$strength$lambda(f, f_0, f_1) {
    return 0.1;
  }
  function ForceRadial$center$lambda(this$ForceRadial) {
    return function (f, f_0, f_1) {
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
  function forceSimulation(nodes, init) {
    if (nodes === void 0) {
      nodes = emptyList();
    }
    var $receiver = new ForceSimulation();
    $receiver.nodes = nodes;
    init($receiver);
    return $receiver;
  }
  var LinkedHashMap_init = Kotlin.kotlin.collections.LinkedHashMap_init_q3lmfv$;
  function ForceSimulation() {
    this.nodes_4karvz$_0 = emptyList();
    this._forces_0 = LinkedHashMap_init();
    this.tickEvents_0 = LinkedHashMap_init();
    this.endEvents_0 = LinkedHashMap_init();
    this.stepper_0 = timer(void 0, void 0, ForceSimulation$stepper$lambda(this));
    this.initializeNodes_0();
    this.alpha_1mpzdq$_0 = 1.0;
    this.alphaMin_8q204g$_0 = 0.001;
    var $receiver = this.alphaMin;
    var x = 1.0 / 300.0;
    this.alphaDecay_67yqh4$_0 = 1.0 - Math_0.pow($receiver, x);
    this.alphaTarget_upifan$_0 = 0.0;
    this.velocityDecay_ydgohv$_0 = 0.6;
  }
  Object.defineProperty(ForceSimulation.prototype, 'nodes', {
    get: function () {
      return this.nodes_4karvz$_0;
    },
    set: function (value) {
      this.nodes_4karvz$_0 = value;
      this.initializeNodes_0();
      var tmp$;
      tmp$ = this._forces_0.values.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        this.initializeForce_0(element);
      }
    }
  });
  Object.defineProperty(ForceSimulation.prototype, 'forces', {
    get: function () {
      return this._forces_0;
    }
  });
  function ForceSimulation$restart$lambda(this$ForceSimulation) {
    return function ($receiver, it) {
      this$ForceSimulation.step_0();
      return Unit;
    };
  }
  ForceSimulation.prototype.restart = function () {
    this.stepper_0.restart_k9susy$(void 0, void 0, ForceSimulation$restart$lambda(this));
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
    if (this.alpha < this.alphaMin) {
      this.stepper_0.stop();
      var tmp$_0;
      tmp$_0 = this.endEvents_0.values.iterator();
      while (tmp$_0.hasNext()) {
        var element_0 = tmp$_0.next();
        element_0(this);
      }
    }
  };
  var IllegalArgumentException_init = Kotlin.kotlin.IllegalArgumentException_init_pdl1vj$;
  Object.defineProperty(ForceSimulation.prototype, 'alpha', {
    get: function () {
      return this.alpha_1mpzdq$_0;
    },
    set: function (value) {
      if (!rangeTo(0.0, 1.0).contains_mef7kx$(value)) {
        var message = 'Failed requirement.';
        throw IllegalArgumentException_init(message.toString());
      }
      this.alpha_1mpzdq$_0 = value;
    }
  });
  Object.defineProperty(ForceSimulation.prototype, 'alphaMin', {
    get: function () {
      return this.alphaMin_8q204g$_0;
    },
    set: function (value) {
      if (!rangeTo(0.0, 1.0).contains_mef7kx$(value)) {
        var message = 'Failed requirement.';
        throw IllegalArgumentException_init(message.toString());
      }
      this.alphaMin_8q204g$_0 = value;
    }
  });
  Object.defineProperty(ForceSimulation.prototype, 'alphaDecay', {
    get: function () {
      return this.alphaDecay_67yqh4$_0;
    },
    set: function (value) {
      if (!rangeTo(0.0, 1.0).contains_mef7kx$(value)) {
        var message = 'Failed requirement.';
        throw IllegalArgumentException_init(message.toString());
      }
      this.alphaDecay_67yqh4$_0 = value;
    }
  });
  Object.defineProperty(ForceSimulation.prototype, 'alphaTarget', {
    get: function () {
      return this.alphaTarget_upifan$_0;
    },
    set: function (value) {
      if (!rangeTo(0.0, 1.0).contains_mef7kx$(value)) {
        var message = 'Failed requirement.';
        throw IllegalArgumentException_init(message.toString());
      }
      this.alphaTarget_upifan$_0 = value;
    }
  });
  Object.defineProperty(ForceSimulation.prototype, 'velocityDecay', {
    get: function () {
      return 1.0 - this.velocityDecay_ydgohv$_0;
    },
    set: function (value) {
      if (!rangeTo(0.0, 1.0).contains_mef7kx$(value)) {
        var message = 'Failed requirement.';
        throw IllegalArgumentException_init(message.toString());
      }
      this.velocityDecay_ydgohv$_0 = 1.0 - value;
    }
  });
  ForceSimulation.prototype.addForce_i3w8j1$ = function (name, force) {
    this.initializeForce_0(force);
    this._forces_0.put_xwzc9p$(name, force);
  };
  ForceSimulation.prototype.removeForce_61zpoe$ = function (name) {
    this._forces_0.remove_11rb$(name);
  };
  ForceSimulation.prototype.initializeForce_0 = function (force) {
    force.assignNodes_qipxwu$(this.nodes);
  };
  ForceSimulation.prototype.tick = function () {
    this.alpha = this.alpha + (this.alphaTarget - this.alpha) * this.alphaDecay;
    var tmp$;
    tmp$ = this._forces_0.values.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      element.applyForceToNodes_14dthe$(this.alpha);
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
        element_0.vx = element_0.vx * this.velocityDecay;
        element_0.x = element_0.x + element_0.vx;
      }
      if (element_0.fixedY != null) {
        element_0.y = ensureNotNull(element_0.fixedY);
        element_0.vy = 0.0;
      }
       else {
        element_0.vy = element_0.vy * this.velocityDecay;
        element_0.y = element_0.y + element_0.vy;
      }
    }
  };
  ForceSimulation.prototype.initializeNodes_0 = function () {
    var tmp$, tmp$_0;
    var index = 0;
    tmp$ = this.nodes.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      var index_0 = checkIndexOverflow((tmp$_0 = index, index = tmp$_0 + 1 | 0, tmp$_0));
      item.index = index_0;
      if (isNaN_0(item.x) || isNaN_0(item.y)) {
        var radius = initialRadius * Math_0.sqrt(index_0);
        var angle = index_0 * initialAngle;
        item.x = radius * Math_0.cos(angle);
        item.y = radius * Math_0.sin(angle);
      }
      if (isNaN_0(item.vx) || isNaN_0(item.vy)) {
        item.vx = 0.0;
        item.vy = 0.0;
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
  ForceSimulation.prototype.on_dosft2$ = function (type, name, callback) {
    switch (type.name) {
      case 'TICK':
        this.tickEvents_0.put_xwzc9p$(name, callback);
        break;
      case 'END':
        this.endEvents_0.put_xwzc9p$(name, callback);
        break;
    }
  };
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
  function forceX$lambda($receiver) {
    return Unit;
  }
  function forceX(init) {
    if (init === void 0)
      init = forceX$lambda;
    var $receiver = new ForceX();
    init($receiver);
    return $receiver;
  }
  function ForceX() {
    this.x_q1dmc9$_0 = ForceX$x$lambda;
    this.strength_y88cyq$_0 = ForceX$strength$lambda;
    this.nodes_0 = emptyList();
    this.strengths_0 = ArrayList_init();
    this.xz_0 = ArrayList_init();
  }
  Object.defineProperty(ForceX.prototype, 'x', {
    get: function () {
      return this.x_q1dmc9$_0;
    },
    set: function (value) {
      this.x_q1dmc9$_0 = value;
      this.assignNodes_qipxwu$(this.nodes_0);
    }
  });
  Object.defineProperty(ForceX.prototype, 'strength', {
    get: function () {
      return this.strength_y88cyq$_0;
    },
    set: function (value) {
      this.strength_y88cyq$_0 = value;
      this.assignNodes_qipxwu$(this.nodes_0);
    }
  });
  ForceX.prototype.assignNodes_qipxwu$ = function (nodes) {
    this.nodes_0 = nodes;
    this.xz_0.clear();
    this.strengths_0.clear();
    var tmp$, tmp$_0;
    var index = 0;
    tmp$ = nodes.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      var index_0 = checkIndexOverflow((tmp$_0 = index, index = tmp$_0 + 1 | 0, tmp$_0));
      this.xz_0.add_11rb$(this.x(item, index_0, nodes));
      this.strengths_0.add_11rb$(this.strength(item, index_0, nodes));
    }
  };
  ForceX.prototype.applyForceToNodes_14dthe$ = function (alpha) {
    var tmp$, tmp$_0;
    var index = 0;
    tmp$ = this.nodes_0.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      var index_0 = checkIndexOverflow((tmp$_0 = index, index = tmp$_0 + 1 | 0, tmp$_0));
      item.vx = item.vx + (this.xz_0.get_za3lpa$(index_0) - item.x) * this.strengths_0.get_za3lpa$(index_0) * alpha;
    }
  };
  function ForceX$x$lambda(f, f_0, f_1) {
    return 0.0;
  }
  function ForceX$strength$lambda(f, f_0, f_1) {
    return 0.1;
  }
  ForceX.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ForceX',
    interfaces: [Force]
  };
  function forceY$lambda($receiver) {
    return Unit;
  }
  function forceY(init) {
    if (init === void 0)
      init = forceY$lambda;
    var $receiver = new ForceY();
    init($receiver);
    return $receiver;
  }
  function ForceY() {
    this.y_q1e9cn$_0 = ForceY$y$lambda;
    this.strength_4idgf7$_0 = ForceY$strength$lambda;
    this.nodes_0 = emptyList();
    this.strengths_0 = ArrayList_init();
    this.yz_0 = ArrayList_init();
  }
  Object.defineProperty(ForceY.prototype, 'y', {
    get: function () {
      return this.y_q1e9cn$_0;
    },
    set: function (value) {
      this.y_q1e9cn$_0 = value;
      this.assignNodes_qipxwu$(this.nodes_0);
    }
  });
  Object.defineProperty(ForceY.prototype, 'strength', {
    get: function () {
      return this.strength_4idgf7$_0;
    },
    set: function (value) {
      this.strength_4idgf7$_0 = value;
      this.assignNodes_qipxwu$(this.nodes_0);
    }
  });
  ForceY.prototype.assignNodes_qipxwu$ = function (nodes) {
    this.nodes_0 = nodes;
    this.yz_0.clear();
    this.strengths_0.clear();
    var tmp$, tmp$_0;
    var index = 0;
    tmp$ = nodes.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      var index_0 = checkIndexOverflow((tmp$_0 = index, index = tmp$_0 + 1 | 0, tmp$_0));
      this.yz_0.add_11rb$(this.y(item, index_0, nodes));
      this.strengths_0.add_11rb$(this.strength(item, index_0, nodes));
    }
  };
  ForceY.prototype.applyForceToNodes_14dthe$ = function (alpha) {
    var tmp$, tmp$_0;
    var index = 0;
    tmp$ = this.nodes_0.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      var index_0 = checkIndexOverflow((tmp$_0 = index, index = tmp$_0 + 1 | 0, tmp$_0));
      item.vy = item.vy + (this.yz_0.get_za3lpa$(index_0) - item.y) * this.strengths_0.get_za3lpa$(index_0) * alpha;
    }
  };
  function ForceY$y$lambda(f, f_0, f_1) {
    return 0.0;
  }
  function ForceY$strength$lambda(f, f_0, f_1) {
    return 0.1;
  }
  ForceY.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ForceY',
    interfaces: [Force]
  };
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$force = package$data2viz.force || (package$data2viz.force = {});
  package$force.Force = Force;
  package$force.forceCenter_mowjvf$ = forceCenter;
  package$force.ForceCenter = ForceCenter;
  package$force.forceCollision_afsb16$ = forceCollision;
  package$force.ForceCollision = ForceCollision;
  package$force.Link = Link;
  package$force.forceLink_phnvl6$ = forceLink;
  package$force.ForceLink = ForceLink;
  package$force.forceNBody_yo3wig$ = forceNBody;
  package$force.jiggle_8be2vx$ = jiggle;
  package$force.ForceNBody = ForceNBody;
  package$force.ForceNode = ForceNode;
  package$force.forceRadial_qbxk35$ = forceRadial;
  package$force.ForceRadial = ForceRadial;
  package$force.forceSimulation_3aqnqx$ = forceSimulation;
  package$force.ForceSimulation = ForceSimulation;
  Object.defineProperty(SimulationEvent, 'TICK', {
    get: SimulationEvent$TICK_getInstance
  });
  Object.defineProperty(SimulationEvent, 'END', {
    get: SimulationEvent$END_getInstance
  });
  package$force.SimulationEvent = SimulationEvent;
  package$force.forceX_xyu5wg$ = forceX;
  package$force.ForceX = ForceX;
  package$force.forceY_91a3hb$ = forceY;
  package$force.ForceY = ForceY;
  initialRadius = 10.0;
  initialAngle = math.PI * (3.0 - Math_0.sqrt(5.0));
  Kotlin.defineModule('d2v-force-js', _);
  return _;
}));

//# sourceMappingURL=d2v-force-js.js.map
