(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin', 'd2v-core-js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'), require('d2v-core-js'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'd2v-quadtree-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'd2v-quadtree-js'.");
    }
    if (typeof this['d2v-core-js'] === 'undefined') {
      throw new Error("Error loading module 'd2v-quadtree-js'. Its dependency 'd2v-core-js' was not found. Please, check whether 'd2v-core-js' is loaded prior to 'd2v-quadtree-js'.");
    }
    root['d2v-quadtree-js'] = factory(typeof this['d2v-quadtree-js'] === 'undefined' ? {} : this['d2v-quadtree-js'], kotlin, this['d2v-core-js']);
  }
}(this, function (_, Kotlin, $module$d2v_core_js) {
  'use strict';
  var $$importsForInline$$ = _.$$importsForInline$$ || (_.$$importsForInline$$ = {});
  var isNaN_0 = Kotlin.kotlin.isNaN_yrwdxr$;
  var kotlin_js_internal_DoubleCompanionObject = Kotlin.kotlin.js.internal.DoubleCompanionObject;
  var throwCCE = Kotlin.throwCCE;
  var ensureNotNull = Kotlin.ensureNotNull;
  var until = Kotlin.kotlin.ranges.until_dqglrj$;
  var Array_0 = Array;
  var checkIndexOverflow = Kotlin.kotlin.collections.checkIndexOverflow_za3lpa$;
  var mutableListOf = Kotlin.kotlin.collections.mutableListOf_i5x0yv$;
  var get_lastIndex = Kotlin.kotlin.collections.get_lastIndex_55thoc$;
  var IntRange = Kotlin.kotlin.ranges.IntRange;
  var Math_0 = Math;
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  var defineInlineFunction = Kotlin.defineInlineFunction;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var Kind_INTERFACE = Kotlin.Kind.INTERFACE;
  var listOf = Kotlin.kotlin.collections.listOf_i5x0yv$;
  var Extent = $module$d2v_core_js.io.data2viz.geom.Extent;
  var equals = Kotlin.equals;
  var asList = Kotlin.kotlin.collections.asList_us0mfu$;
  function add($receiver, datum) {
    var x = $receiver.x(datum);
    var y = $receiver.y(datum);
    cover($receiver, x, y);
    _add($receiver, x, y, datum);
  }
  function _add($receiver, x, y, datum) {
    var tmp$, tmp$_0;
    if (isNaN_0(x) || isNaN_0(y))
      return;
    var node = $receiver.root;
    var leaf = new LeafNode(datum, null);
    var x0 = $receiver.extent.x0;
    var y0 = $receiver.extent.y0;
    var x1 = $receiver.extent.x1;
    var y1 = $receiver.extent.y1;
    var index = 0;
    var jndex;
    var parent = null;
    var xp = kotlin_js_internal_DoubleCompanionObject.NaN;
    var yp = kotlin_js_internal_DoubleCompanionObject.NaN;
    if (node == null) {
      $receiver.root = leaf;
      return;
    }
    while (Kotlin.isType(node, InternalNode)) {
      var xm = (x0 + x1) / 2;
      var right = x >= xm;
      if (right)
        x0 = xm;
      else
        x1 = xm;
      var ym = (y0 + y1) / 2;
      var bottom = y >= ym;
      if (bottom)
        y0 = ym;
      else
        y1 = ym;
      parent = node;
      index = (bottom ? 1 : 0) << 1 | (right ? 1 : 0);
      node = getNodeFromIndex(node, index);
      if (node == null) {
        setNodeFromIndex(parent, index, leaf);
        return;
      }
    }
    if (Kotlin.isType(node, LeafNode)) {
      xp = $receiver.x(node.data);
      yp = $receiver.y(node.data);
      if (x === xp && y === yp) {
        leaf.next = node;
        if (parent != null)
          setNodeFromIndex(parent, index, leaf);
        else
          $receiver.root = leaf;
        return;
      }
    }
    do {
      if (parent != null) {
        setNodeFromIndex(parent, index, new InternalNode());
        parent = Kotlin.isType(tmp$ = getNodeFromIndex(parent, index), InternalNode) ? tmp$ : throwCCE();
      }
       else {
        $receiver.root = new InternalNode();
        parent = Kotlin.isType(tmp$_0 = $receiver.root, InternalNode) ? tmp$_0 : throwCCE();
      }
      var xm_0 = (x0 + x1) / 2;
      var right_0 = x >= xm_0;
      if (right_0)
        x0 = xm_0;
      else
        x1 = xm_0;
      var ym_0 = (y0 + y1) / 2;
      var bottom_0 = y >= ym_0;
      if (bottom_0)
        y0 = ym_0;
      else
        y1 = ym_0;
      index = (bottom_0 ? 1 : 0) << 1 | (right_0 ? 1 : 0);
      jndex = (yp >= ym_0 ? 1 : 0) << 1 | (xp >= xm_0 ? 1 : 0);
    }
     while (index === jndex);
    setNodeFromIndex(ensureNotNull(parent), jndex, ensureNotNull(node));
    setNodeFromIndex(parent, index, leaf);
  }
  function addAll($receiver, data) {
    var array = Array_0(data.size);
    var tmp$;
    tmp$ = array.length - 1 | 0;
    for (var i = 0; i <= tmp$; i++) {
      array[i] = $receiver.x(data.get_za3lpa$(i));
    }
    var xz = array;
    var array_0 = Array_0(data.size);
    var tmp$_0;
    tmp$_0 = array_0.length - 1 | 0;
    for (var i_0 = 0; i_0 <= tmp$_0; i_0++) {
      array_0[i_0] = $receiver.y(data.get_za3lpa$(i_0));
    }
    var yz = array_0;
    var x0 = {v: kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY};
    var y0 = {v: kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY};
    var x1 = {v: kotlin_js_internal_DoubleCompanionObject.NEGATIVE_INFINITY};
    var y1 = {v: kotlin_js_internal_DoubleCompanionObject.NEGATIVE_INFINITY};
    var tmp$_1;
    tmp$_1 = until(0, data.size).iterator();
    loop_label: while (tmp$_1.hasNext()) {
      var element = tmp$_1.next();
      action$break: do {
        var cx = xz[element];
        var cy = yz[element];
        if (isNaN_0(cx) || isNaN_0(cy))
          break action$break;
        if (cx < x0.v)
          x0.v = cx;
        if (cx > x1.v)
          x1.v = cx;
        if (cy < y0.v)
          y0.v = cy;
        if (cy > y1.v)
          y1.v = cy;
      }
       while (false);
    }
    if (x1.v < x0.v) {
      x0.v = $receiver.extent.x0;
      x1.v = $receiver.extent.x1;
    }
    if (y1.v < y0.v) {
      y0.v = $receiver.extent.y0;
      y1.v = $receiver.extent.y1;
    }
    cover($receiver, x0.v, y0.v);
    cover($receiver, x1.v, y1.v);
    var tmp$_2, tmp$_0_0;
    var index = 0;
    tmp$_2 = data.iterator();
    while (tmp$_2.hasNext()) {
      var item = tmp$_2.next();
      var index_0 = checkIndexOverflow((tmp$_0_0 = index, index = tmp$_0_0 + 1 | 0, tmp$_0_0));
      _add($receiver, xz[index_0], yz[index_0], item);
    }
  }
  function copy($receiver) {
    var tmp$;
    var copy = new Quadtree($receiver.x, $receiver.y);
    copy.extent = $receiver.extent.copy();
    tmp$ = $receiver.root;
    if (tmp$ == null) {
      return copy;
    }
    var node = tmp$;
    if (Kotlin.isType(node, LeafNode)) {
      copy.root = leafCopy(node);
      return copy;
    }
    copy.root = new InternalNode();
    var nodes = mutableListOf([new NodePair(node, ensureNotNull(copy.root))]);
    while (!nodes.isEmpty()) {
      var currentNode = nodes.removeAt_za3lpa$(get_lastIndex(nodes));
      var tmp$_0;
      tmp$_0 = (new IntRange(0, 3)).iterator();
      while (tmp$_0.hasNext()) {
        var element = tmp$_0.next();
        var tmp$_1, tmp$_2, tmp$_3, tmp$_4;
        var child = getNodeFromIndex(Kotlin.isType(tmp$_1 = currentNode.source, InternalNode) ? tmp$_1 : throwCCE(), element);
        if (child != null) {
          if (Kotlin.isType(child, InternalNode)) {
            setNodeFromIndex(Kotlin.isType(tmp$_2 = currentNode.target, InternalNode) ? tmp$_2 : throwCCE(), element, new InternalNode());
            nodes.add_11rb$(new NodePair(child, ensureNotNull(getNodeFromIndex(currentNode.target, element))));
          }
           else {
            setNodeFromIndex(Kotlin.isType(tmp$_3 = currentNode.target, InternalNode) ? tmp$_3 : throwCCE(), element, leafCopy(Kotlin.isType(tmp$_4 = child, LeafNode) ? tmp$_4 : throwCCE()));
          }
        }
      }
    }
    return copy;
  }
  function leafCopy(leaf) {
    var copy = new LeafNode(leaf.data, null);
    var next = copy;
    var newLeaf = leaf;
    while (newLeaf.next != null) {
      newLeaf = ensureNotNull(newLeaf.next);
      next.next = new LeafNode(newLeaf.data, null);
      next = ensureNotNull(next.next);
    }
    return copy;
  }
  function cover($receiver, x, y) {
    if (isNaN_0(x) || isNaN_0(y))
      return;
    var x0 = $receiver.extent.x0;
    var y0 = $receiver.extent.y0;
    var x1 = $receiver.extent.x1;
    var y1 = $receiver.extent.y1;
    if (isNaN_0($receiver.extent.x0)) {
      x0 = Math_0.floor(x);
      y0 = Math_0.floor(y);
      x1 = x0 + 1;
      y1 = y0 + 1;
    }
     else if (x0 > x || x > x1 || y0 > y || y > y1) {
      var z = x1 - x0;
      var node = $receiver.root;
      var parent;
      var vertical = y < (y0 + y1) / 2 ? 1 : 0;
      var horizontal = x < (x0 + x1) / 2 ? 1 : 0;
      var i = vertical << 1 | horizontal;
      switch (i) {
        case 0:
          do {
            parent = new InternalNode(node, null, null, null);
            node = parent;
            z *= 2;
            x1 = x0 + z;
            y1 = y0 + z;
          }
           while (x > x1 || y > y1);
          break;
        case 1:
          do {
            parent = new InternalNode(null, node, null, null);
            node = parent;
            z *= 2;
            x0 = x1 - z;
            y1 = y0 + z;
          }
           while (x0 > x || y > y1);
          break;
        case 2:
          do {
            parent = new InternalNode(null, null, node, null);
            node = parent;
            z *= 2;
            x1 = x0 + z;
            y0 = y1 - z;
          }
           while (x > x1 || y0 > y);
          break;
        case 3:
          do {
            parent = new InternalNode(null, null, null, node);
            node = parent;
            z *= 2;
            x0 = x1 - z;
            y0 = y1 - z;
          }
           while (x0 > x || y0 > y);
          break;
      }
      if ($receiver.root != null && Kotlin.isType($receiver.root, InternalNode))
        $receiver.root = node;
    }
     else
      return;
    $receiver.extent.x0 = x0;
    $receiver.extent.y0 = y0;
    $receiver.extent.x1 = x1;
    $receiver.extent.y1 = y1;
  }
  function data$lambda(closure$data) {
    return function (node, f, f_0, f_1, f_2) {
      var tmp$;
      var newNode = node;
      if (Kotlin.isType(newNode, LeafNode))
        do {
          closure$data.add_11rb$((Kotlin.isType(tmp$ = newNode, LeafNode) ? tmp$ : throwCCE()).data);
          newNode = newNode.next;
        }
         while (newNode != null);
      return false;
    };
  }
  function data($receiver) {
    var data = ArrayList_init();
    visit($receiver, data$lambda(data));
    return data;
  }
  function find($receiver, x, y, radius) {
    if (radius === void 0)
      radius = kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY;
    var tmp$;
    var data = null;
    var newRadius = radius;
    var x0 = $receiver.extent.x0;
    var y0 = $receiver.extent.y0;
    var x3 = $receiver.extent.x1;
    var y3 = $receiver.extent.y1;
    var quads = ArrayList_init();
    var node = $receiver.root;
    if (node != null)
      quads.add_11rb$(new Quad(node, x0, y0, x3, y3));
    if (newRadius < kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY) {
      x0 = x - newRadius;
      y0 = y - newRadius;
      x3 = x + newRadius;
      y3 = y + newRadius;
      newRadius *= newRadius;
    }
    while (!quads.isEmpty()) {
      var quad = quads.removeAt_za3lpa$(get_lastIndex(quads));
      node = quad.node;
      var x1 = quad.x0;
      var y1 = quad.y0;
      var x2 = quad.x1;
      var y2 = quad.y1;
      if (node == null || x1 > x3 || y1 > y3 || x2 < x0 || y2 < y0)
        continue;
      if (Kotlin.isType(node, InternalNode)) {
        var xm = (x1 + x2) / 2;
        var ym = (y1 + y2) / 2;
        quads.add_11rb$(new Quad(node.SW_3, xm, ym, x2, y2));
        quads.add_11rb$(new Quad(node.SE_2, x1, ym, xm, y2));
        quads.add_11rb$(new Quad(node.NW_1, xm, y1, x2, ym));
        quads.add_11rb$(new Quad(node.NE_0, x1, y1, xm, ym));
        var i = (y >= ym ? 1 : 0) << 1 | (x >= xm ? 1 : 0);
        if (i !== 0) {
          quad = quads.get_za3lpa$(get_lastIndex(quads));
          quads.set_wxm5ur$(get_lastIndex(quads), quads.get_za3lpa$(get_lastIndex(quads) - i | 0));
          quads.set_wxm5ur$(get_lastIndex(quads) - i | 0, quad);
        }
      }
       else {
        var dx = x - $receiver.x((Kotlin.isType(tmp$ = node, LeafNode) ? tmp$ : throwCCE()).data);
        var dy = y - $receiver.y(node.data);
        var d2 = dx * dx + dy * dy;
        if (d2 < newRadius) {
          newRadius = d2;
          var x_0 = newRadius;
          var d = Math_0.sqrt(x_0);
          x0 = x - d;
          y0 = y - d;
          x3 = x + d;
          y3 = y + d;
          data = node.data;
        }
      }
    }
    return data;
  }
  var toInt = defineInlineFunction('d2v-quadtree-js.io.data2viz.quadtree.toInt_jthgvr$', function ($receiver) {
    return $receiver ? 1 : 0;
  });
  function NodePair(source, target) {
    this.source = source;
    this.target = target;
  }
  NodePair.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'NodePair',
    interfaces: []
  };
  NodePair.prototype.component1 = function () {
    return this.source;
  };
  NodePair.prototype.component2 = function () {
    return this.target;
  };
  NodePair.prototype.copy_miecbw$ = function (source, target) {
    return new NodePair(source === void 0 ? this.source : source, target === void 0 ? this.target : target);
  };
  NodePair.prototype.toString = function () {
    return 'NodePair(source=' + Kotlin.toString(this.source) + (', target=' + Kotlin.toString(this.target)) + ')';
  };
  NodePair.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.source) | 0;
    result = result * 31 + Kotlin.hashCode(this.target) | 0;
    return result;
  };
  NodePair.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.source, other.source) && Kotlin.equals(this.target, other.target)))));
  };
  function QuadtreeNode() {
  }
  QuadtreeNode.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'QuadtreeNode',
    interfaces: []
  };
  function InternalNode(NE_0, NW_1, SE_2, SW_3, value, x, y) {
    if (NE_0 === void 0)
      NE_0 = null;
    if (NW_1 === void 0)
      NW_1 = null;
    if (SE_2 === void 0)
      SE_2 = null;
    if (SW_3 === void 0)
      SW_3 = null;
    if (value === void 0)
      value = null;
    if (x === void 0)
      x = kotlin_js_internal_DoubleCompanionObject.NaN;
    if (y === void 0)
      y = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.NE_0 = NE_0;
    this.NW_1 = NW_1;
    this.SE_2 = SE_2;
    this.SW_3 = SW_3;
    this.value_78c3h2$_0 = value;
    this.x_rssuen$_0 = x;
    this.y_rssufi$_0 = y;
  }
  Object.defineProperty(InternalNode.prototype, 'value', {
    get: function () {
      return this.value_78c3h2$_0;
    },
    set: function (value) {
      this.value_78c3h2$_0 = value;
    }
  });
  Object.defineProperty(InternalNode.prototype, 'x', {
    get: function () {
      return this.x_rssuen$_0;
    },
    set: function (x) {
      this.x_rssuen$_0 = x;
    }
  });
  Object.defineProperty(InternalNode.prototype, 'y', {
    get: function () {
      return this.y_rssufi$_0;
    },
    set: function (y) {
      this.y_rssufi$_0 = y;
    }
  });
  InternalNode.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'InternalNode',
    interfaces: [QuadtreeNode]
  };
  InternalNode.prototype.component1 = function () {
    return this.NE_0;
  };
  InternalNode.prototype.component2 = function () {
    return this.NW_1;
  };
  InternalNode.prototype.component3 = function () {
    return this.SE_2;
  };
  InternalNode.prototype.component4 = function () {
    return this.SW_3;
  };
  InternalNode.prototype.component5 = function () {
    return this.value;
  };
  InternalNode.prototype.component6 = function () {
    return this.x;
  };
  InternalNode.prototype.component7 = function () {
    return this.y;
  };
  InternalNode.prototype.copy_gtxapr$ = function (NE_0, NW_1, SE_2, SW_3, value, x, y) {
    return new InternalNode(NE_0 === void 0 ? this.NE_0 : NE_0, NW_1 === void 0 ? this.NW_1 : NW_1, SE_2 === void 0 ? this.SE_2 : SE_2, SW_3 === void 0 ? this.SW_3 : SW_3, value === void 0 ? this.value : value, x === void 0 ? this.x : x, y === void 0 ? this.y : y);
  };
  InternalNode.prototype.toString = function () {
    return 'InternalNode(NE_0=' + Kotlin.toString(this.NE_0) + (', NW_1=' + Kotlin.toString(this.NW_1)) + (', SE_2=' + Kotlin.toString(this.SE_2)) + (', SW_3=' + Kotlin.toString(this.SW_3)) + (', value=' + Kotlin.toString(this.value)) + (', x=' + Kotlin.toString(this.x)) + (', y=' + Kotlin.toString(this.y)) + ')';
  };
  InternalNode.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.NE_0) | 0;
    result = result * 31 + Kotlin.hashCode(this.NW_1) | 0;
    result = result * 31 + Kotlin.hashCode(this.SE_2) | 0;
    result = result * 31 + Kotlin.hashCode(this.SW_3) | 0;
    result = result * 31 + Kotlin.hashCode(this.value) | 0;
    result = result * 31 + Kotlin.hashCode(this.x) | 0;
    result = result * 31 + Kotlin.hashCode(this.y) | 0;
    return result;
  };
  InternalNode.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.NE_0, other.NE_0) && Kotlin.equals(this.NW_1, other.NW_1) && Kotlin.equals(this.SE_2, other.SE_2) && Kotlin.equals(this.SW_3, other.SW_3) && Kotlin.equals(this.value, other.value) && Kotlin.equals(this.x, other.x) && Kotlin.equals(this.y, other.y)))));
  };
  function toList($receiver) {
    return listOf([$receiver.NE_0, $receiver.NW_1, $receiver.SE_2, $receiver.SW_3]);
  }
  function LeafNode(data, next, value, x, y) {
    if (value === void 0)
      value = null;
    if (x === void 0)
      x = kotlin_js_internal_DoubleCompanionObject.NaN;
    if (y === void 0)
      y = kotlin_js_internal_DoubleCompanionObject.NaN;
    this.data = data;
    this.next = next;
    this.value_coqfdh$_0 = value;
    this.x_2m5z9u$_0 = x;
    this.y_2m5z8z$_0 = y;
  }
  Object.defineProperty(LeafNode.prototype, 'value', {
    get: function () {
      return this.value_coqfdh$_0;
    },
    set: function (value) {
      this.value_coqfdh$_0 = value;
    }
  });
  Object.defineProperty(LeafNode.prototype, 'x', {
    get: function () {
      return this.x_2m5z9u$_0;
    },
    set: function (x) {
      this.x_2m5z9u$_0 = x;
    }
  });
  Object.defineProperty(LeafNode.prototype, 'y', {
    get: function () {
      return this.y_2m5z8z$_0;
    },
    set: function (y) {
      this.y_2m5z8z$_0 = y;
    }
  });
  LeafNode.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'LeafNode',
    interfaces: [QuadtreeNode]
  };
  LeafNode.prototype.component1 = function () {
    return this.data;
  };
  LeafNode.prototype.component2 = function () {
    return this.next;
  };
  LeafNode.prototype.component3 = function () {
    return this.value;
  };
  LeafNode.prototype.component4 = function () {
    return this.x;
  };
  LeafNode.prototype.component5 = function () {
    return this.y;
  };
  LeafNode.prototype.copy_57bgag$ = function (data, next, value, x, y) {
    return new LeafNode(data === void 0 ? this.data : data, next === void 0 ? this.next : next, value === void 0 ? this.value : value, x === void 0 ? this.x : x, y === void 0 ? this.y : y);
  };
  LeafNode.prototype.toString = function () {
    return 'LeafNode(data=' + Kotlin.toString(this.data) + (', next=' + Kotlin.toString(this.next)) + (', value=' + Kotlin.toString(this.value)) + (', x=' + Kotlin.toString(this.x)) + (', y=' + Kotlin.toString(this.y)) + ')';
  };
  LeafNode.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.data) | 0;
    result = result * 31 + Kotlin.hashCode(this.next) | 0;
    result = result * 31 + Kotlin.hashCode(this.value) | 0;
    result = result * 31 + Kotlin.hashCode(this.x) | 0;
    result = result * 31 + Kotlin.hashCode(this.y) | 0;
    return result;
  };
  LeafNode.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.data, other.data) && Kotlin.equals(this.next, other.next) && Kotlin.equals(this.value, other.value) && Kotlin.equals(this.x, other.x) && Kotlin.equals(this.y, other.y)))));
  };
  function Quad(node, x0, y0, x1, y1) {
    this.node = node;
    this.x0 = x0;
    this.y0 = y0;
    this.x1 = x1;
    this.y1 = y1;
  }
  Quad.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Quad',
    interfaces: []
  };
  Quad.prototype.component1 = function () {
    return this.node;
  };
  Quad.prototype.component2 = function () {
    return this.x0;
  };
  Quad.prototype.component3 = function () {
    return this.y0;
  };
  Quad.prototype.component4 = function () {
    return this.x1;
  };
  Quad.prototype.component5 = function () {
    return this.y1;
  };
  Quad.prototype.copy_87q5xf$ = function (node, x0, y0, x1, y1) {
    return new Quad(node === void 0 ? this.node : node, x0 === void 0 ? this.x0 : x0, y0 === void 0 ? this.y0 : y0, x1 === void 0 ? this.x1 : x1, y1 === void 0 ? this.y1 : y1);
  };
  Quad.prototype.toString = function () {
    return 'Quad(node=' + Kotlin.toString(this.node) + (', x0=' + Kotlin.toString(this.x0)) + (', y0=' + Kotlin.toString(this.y0)) + (', x1=' + Kotlin.toString(this.x1)) + (', y1=' + Kotlin.toString(this.y1)) + ')';
  };
  Quad.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.node) | 0;
    result = result * 31 + Kotlin.hashCode(this.x0) | 0;
    result = result * 31 + Kotlin.hashCode(this.y0) | 0;
    result = result * 31 + Kotlin.hashCode(this.x1) | 0;
    result = result * 31 + Kotlin.hashCode(this.y1) | 0;
    return result;
  };
  Quad.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.node, other.node) && Kotlin.equals(this.x0, other.x0) && Kotlin.equals(this.y0, other.y0) && Kotlin.equals(this.x1, other.x1) && Kotlin.equals(this.y1, other.y1)))));
  };
  function getNodeFromIndex(node, index) {
    var tmp$;
    switch (index) {
      case 0:
        tmp$ = node.NE_0;
        break;
      case 1:
        tmp$ = node.NW_1;
        break;
      case 2:
        tmp$ = node.SE_2;
        break;
      default:tmp$ = node.SW_3;
        break;
    }
    return tmp$;
  }
  function setNodeFromIndex(node, index, value) {
    switch (index) {
      case 0:
        node.NE_0 = value;
        break;
      case 1:
        node.NW_1 = value;
        break;
      case 2:
        node.SE_2 = value;
        break;
      default:node.SW_3 = value;
        break;
    }
  }
  function quadtree(x, y) {
    return new Quadtree(x, y);
  }
  function quadtree_0(x, y, nodes) {
    var $receiver = new Quadtree(x, y);
    addAll($receiver, nodes);
    return $receiver;
  }
  function Quadtree(x, y) {
    this.x = x;
    this.y = y;
    this.root = null;
    this.extent_cop16x$_0 = new Extent(kotlin_js_internal_DoubleCompanionObject.NaN, kotlin_js_internal_DoubleCompanionObject.NaN, kotlin_js_internal_DoubleCompanionObject.NaN, kotlin_js_internal_DoubleCompanionObject.NaN);
  }
  Object.defineProperty(Quadtree.prototype, 'extent', {
    get: function () {
      return this.extent_cop16x$_0;
    },
    set: function (value) {
      cover(this, value.x0, value.y0);
      cover(this, value.x1, value.y1);
    }
  });
  Quadtree.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Quadtree',
    interfaces: []
  };
  function remove($receiver, datum) {
    var tmp$, tmp$_0, tmp$_1, tmp$_2;
    var x = $receiver.x(datum);
    var y = $receiver.y(datum);
    if (isNaN_0(x) || isNaN_0(y))
      return;
    var node = $receiver.root;
    var x0 = $receiver.extent.x0;
    var y0 = $receiver.extent.y0;
    var x1 = $receiver.extent.x1;
    var y1 = $receiver.extent.y1;
    var parent = null;
    var retainer = null;
    var previous = null;
    var index = 0;
    var jndex = 0;
    if (node == null)
      return;
    if (Kotlin.isType(node, InternalNode))
      while (true) {
        var xm = (x0 + x1) / 2;
        var right = x >= xm;
        if (right)
          x0 = xm;
        else
          x1 = xm;
        var ym = (y0 + y1) / 2;
        var bottom = y >= ym;
        if (bottom)
          y0 = ym;
        else
          y1 = ym;
        parent = Kotlin.isType(tmp$ = node, InternalNode) ? tmp$ : throwCCE();
        index = (bottom ? 1 : 0) << 1 | (right ? 1 : 0);
        node = getNodeFromIndex(node, index);
        if (node == null)
          return;
        if (Kotlin.isType(node, LeafNode))
          break;
        if (getNodeFromIndex(parent, index + 1 & 3) != null || getNodeFromIndex(parent, index + 2 & 3) != null || getNodeFromIndex(parent, index + 3 & 3) != null) {
          retainer = parent;
          jndex = index;
        }
      }
    while (!Kotlin.isType(node, LeafNode) || !equals(node.data, datum)) {
      previous = node;
      node = (Kotlin.isType(tmp$_0 = node, LeafNode) ? tmp$_0 : throwCCE()).next;
      if (node == null)
        return;
    }
    var next = node.next;
    if (next != null)
      node.next = null;
    if (previous != null) {
      if (next != null) {
        (Kotlin.isType(tmp$_1 = previous, LeafNode) ? tmp$_1 : throwCCE()).next = next;
      }
       else {
        (Kotlin.isType(tmp$_2 = previous, LeafNode) ? tmp$_2 : throwCCE()).next = null;
      }
      return;
    }
    if (parent == null) {
      $receiver.root = next;
      return;
    }
    if (next != null) {
      setNodeFromIndex(parent, index, next);
    }
     else {
      setNodeFromIndex(parent, index, null);
    }
    var p0 = getNodeFromIndex(parent, 0);
    var p1 = getNodeFromIndex(parent, 1);
    var p2 = getNodeFromIndex(parent, 2);
    var p3 = getNodeFromIndex(parent, 3);
    node = p0 != null ? p0 : p1 != null ? p1 : p2 != null ? p2 : p3;
    if (node != null && equals(node, p3 != null ? p3 : p2 != null ? p2 : p1 != null ? p1 : p0) && Kotlin.isType(node, LeafNode)) {
      if (retainer != null)
        setNodeFromIndex(retainer, jndex, node);
      else
        $receiver.root = node;
    }
  }
  function removeAll($receiver, data) {
    var tmp$;
    tmp$ = data.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      remove($receiver, element);
    }
  }
  function size$lambda(closure$size) {
    return function (node, f, f_0, f_1, f_2) {
      var tmp$;
      var newNode = node;
      if (Kotlin.isType(newNode, LeafNode)) {
        do {
          closure$size.v = closure$size.v + 1 | 0;
          newNode = (Kotlin.isType(tmp$ = newNode, LeafNode) ? tmp$ : throwCCE()).next;
        }
         while (newNode != null);
      }
      return false;
    };
  }
  function size($receiver) {
    var size = {v: 0};
    visit($receiver, size$lambda(size));
    return size.v;
  }
  function visit($receiver, callback) {
    var quads = ArrayList_init();
    var node = $receiver.root;
    if (node != null)
      quads.add_11rb$(new Quad(node, $receiver.extent.x0, $receiver.extent.y0, $receiver.extent.x1, $receiver.extent.y1));
    while (!quads.isEmpty()) {
      var q = quads.removeAt_za3lpa$(get_lastIndex(quads));
      node = q.node;
      var x0 = q.x0;
      var y0 = q.y0;
      var x1 = q.x1;
      var y1 = q.y1;
      if (!callback(ensureNotNull(node), x0, y0, x1, y1) && Kotlin.isType(node, InternalNode)) {
        var xm = (x0 + x1) / 2;
        var ym = (y0 + y1) / 2;
        if (node.SW_3 != null)
          quads.add_11rb$(new Quad(node.SW_3, xm, ym, x1, y1));
        if (node.SE_2 != null)
          quads.add_11rb$(new Quad(node.SE_2, x0, ym, xm, y1));
        if (node.NW_1 != null)
          quads.add_11rb$(new Quad(node.NW_1, xm, y0, x1, ym));
        if (node.NE_0 != null)
          quads.add_11rb$(new Quad(node.NE_0, x0, y0, xm, ym));
      }
    }
  }
  function visitAfter($receiver, callback) {
    var quads = ArrayList_init();
    var next = ArrayList_init();
    if ($receiver.root != null)
      quads.add_11rb$(new Quad($receiver.root, $receiver.extent.x0, $receiver.extent.y0, $receiver.extent.x1, $receiver.extent.y1));
    while (!quads.isEmpty()) {
      var q = quads.removeAt_za3lpa$(get_lastIndex(quads));
      var node = q.node;
      if (Kotlin.isType(node, InternalNode)) {
        var x0 = q.x0;
        var y0 = q.y0;
        var x1 = q.x1;
        var y1 = q.y1;
        var xm = (x0 + x1) / 2;
        var ym = (y0 + y1) / 2;
        if (node.NE_0 != null)
          quads.add_11rb$(new Quad(node.NE_0, x0, y0, xm, ym));
        if (node.NW_1 != null)
          quads.add_11rb$(new Quad(node.NW_1, xm, y0, x1, ym));
        if (node.SE_2 != null)
          quads.add_11rb$(new Quad(node.SE_2, x0, ym, xm, y1));
        if (node.SW_3 != null)
          quads.add_11rb$(new Quad(node.SW_3, xm, ym, x1, y1));
      }
      next.add_11rb$(q);
    }
    while (!next.isEmpty()) {
      var q_0 = next.removeAt_za3lpa$(get_lastIndex(next));
      if (q_0.node != null)
        callback(q_0.node, q_0.x0, q_0.y0, q_0.x1, q_0.y1);
    }
  }
  function quadtree$lambda(d) {
    return d[0];
  }
  function quadtree$lambda_0(d) {
    return d[1];
  }
  function quadtree_1(points) {
    return quadtree_0(quadtree$lambda, quadtree$lambda_0, asList(points));
  }
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$quadtree = package$data2viz.quadtree || (package$data2viz.quadtree = {});
  package$quadtree.add_gikaa6$ = add;
  $$importsForInline$$['d2v-quadtree-js'] = _;
  package$quadtree.addAll_i5o6bx$ = addAll;
  package$quadtree.copy_da41lg$ = copy;
  package$quadtree.cover_uvtpq0$ = cover;
  package$quadtree.data_da41lg$ = data;
  package$quadtree.find_z9s5yu$ = find;
  package$quadtree.toInt_jthgvr$ = toInt;
  package$quadtree.NodePair = NodePair;
  package$quadtree.QuadtreeNode = QuadtreeNode;
  package$quadtree.InternalNode = InternalNode;
  package$quadtree.toList_dw9rom$ = toList;
  package$quadtree.LeafNode = LeafNode;
  package$quadtree.Quad = Quad;
  package$quadtree.getNodeFromIndex_hca9e0$ = getNodeFromIndex;
  package$quadtree.setNodeFromIndex_y4aigg$ = setNodeFromIndex;
  package$quadtree.quadtree_9ae4oi$ = quadtree;
  package$quadtree.quadtree_tz1x4d$ = quadtree_0;
  package$quadtree.Quadtree = Quadtree;
  package$quadtree.remove_gikaa6$ = remove;
  package$quadtree.removeAll_i5o6bx$ = removeAll;
  package$quadtree.size_da41lg$ = size;
  package$quadtree.visit_jsbco0$ = visit;
  package$quadtree.visitAfter_690jwc$ = visitAfter;
  package$quadtree.quadtree = quadtree_1;
  Kotlin.defineModule('d2v-quadtree-js', _);
  return _;
}));

//# sourceMappingURL=d2v-quadtree-js.js.map
