(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'd2v-hierarchy-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'd2v-hierarchy-js'.");
    }
    root['d2v-hierarchy-js'] = factory(typeof this['d2v-hierarchy-js'] === 'undefined' ? {} : this['d2v-hierarchy-js'], kotlin);
  }
}(this, function (_, Kotlin) {
  'use strict';
  var $$importsForInline$$ = _.$$importsForInline$$ || (_.$$importsForInline$$ = {});
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var ensureNotNull = Kotlin.ensureNotNull;
  var Unit = Kotlin.kotlin.Unit;
  var mutableListOf = Kotlin.kotlin.collections.mutableListOf_i5x0yv$;
  var get_lastIndex = Kotlin.kotlin.collections.get_lastIndex_55thoc$;
  var Kind_INTERFACE = Kotlin.Kind.INTERFACE;
  var getCallableRef = Kotlin.getCallableRef;
  var toList = Kotlin.kotlin.collections.toList_7wnvza$;
  var defineInlineFunction = Kotlin.defineInlineFunction;
  var wrapFunction = Kotlin.wrapFunction;
  var toMutableList = Kotlin.kotlin.collections.toMutableList_4c7yge$;
  var equals = Kotlin.equals;
  var roundToInt = Kotlin.kotlin.math.roundToInt_yrwdxr$;
  var shuffled = Kotlin.kotlin.collections.shuffled_7wnvza$;
  var listOf = Kotlin.kotlin.collections.listOf_mh5how$;
  var listOf_0 = Kotlin.kotlin.collections.listOf_i5x0yv$;
  var RuntimeException_init = Kotlin.kotlin.RuntimeException_init_pdl1vj$;
  var first = Kotlin.kotlin.collections.first_2p1efm$;
  var until = Kotlin.kotlin.ranges.until_dqglrj$;
  var throwCCE = Kotlin.throwCCE;
  var slice = Kotlin.kotlin.collections.slice_6bjbi1$;
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  function ClusterNode(data, depth, height, value, children, parent, x, y) {
    if (children === void 0) {
      children = ArrayList_init();
    }
    if (parent === void 0)
      parent = null;
    if (x === void 0)
      x = 0.0;
    if (y === void 0)
      y = 0.0;
    this.data = data;
    this.depth = depth;
    this.height = height;
    this.value_8fojl1$_0 = value;
    this.children_dcxl9l$_0 = children;
    this.parent_5o2hb2$_0 = parent;
    this.x = x;
    this.y = y;
  }
  Object.defineProperty(ClusterNode.prototype, 'value', {
    get: function () {
      return this.value_8fojl1$_0;
    },
    set: function (value) {
      this.value_8fojl1$_0 = value;
    }
  });
  Object.defineProperty(ClusterNode.prototype, 'children', {
    get: function () {
      return this.children_dcxl9l$_0;
    }
  });
  Object.defineProperty(ClusterNode.prototype, 'parent', {
    get: function () {
      return this.parent_5o2hb2$_0;
    },
    set: function (parent) {
      this.parent_5o2hb2$_0 = parent;
    }
  });
  ClusterNode.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ClusterNode',
    interfaces: [Children, ParentValued]
  };
  ClusterNode.prototype.component1 = function () {
    return this.data;
  };
  ClusterNode.prototype.component2 = function () {
    return this.depth;
  };
  ClusterNode.prototype.component3 = function () {
    return this.height;
  };
  ClusterNode.prototype.component4 = function () {
    return this.value;
  };
  ClusterNode.prototype.component5 = function () {
    return this.children;
  };
  ClusterNode.prototype.component6 = function () {
    return this.parent;
  };
  ClusterNode.prototype.component7 = function () {
    return this.x;
  };
  ClusterNode.prototype.component8 = function () {
    return this.y;
  };
  ClusterNode.prototype.copy_ymwwnj$ = function (data, depth, height, value, children, parent, x, y) {
    return new ClusterNode(data === void 0 ? this.data : data, depth === void 0 ? this.depth : depth, height === void 0 ? this.height : height, value === void 0 ? this.value : value, children === void 0 ? this.children : children, parent === void 0 ? this.parent : parent, x === void 0 ? this.x : x, y === void 0 ? this.y : y);
  };
  ClusterNode.prototype.toString = function () {
    return 'ClusterNode(data=' + Kotlin.toString(this.data) + (', depth=' + Kotlin.toString(this.depth)) + (', height=' + Kotlin.toString(this.height)) + (', value=' + Kotlin.toString(this.value)) + (', children=' + Kotlin.toString(this.children)) + (', parent=' + Kotlin.toString(this.parent)) + (', x=' + Kotlin.toString(this.x)) + (', y=' + Kotlin.toString(this.y)) + ')';
  };
  ClusterNode.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.data) | 0;
    result = result * 31 + Kotlin.hashCode(this.depth) | 0;
    result = result * 31 + Kotlin.hashCode(this.height) | 0;
    result = result * 31 + Kotlin.hashCode(this.value) | 0;
    result = result * 31 + Kotlin.hashCode(this.children) | 0;
    result = result * 31 + Kotlin.hashCode(this.parent) | 0;
    result = result * 31 + Kotlin.hashCode(this.x) | 0;
    result = result * 31 + Kotlin.hashCode(this.y) | 0;
    return result;
  };
  ClusterNode.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.data, other.data) && Kotlin.equals(this.depth, other.depth) && Kotlin.equals(this.height, other.height) && Kotlin.equals(this.value, other.value) && Kotlin.equals(this.children, other.children) && Kotlin.equals(this.parent, other.parent) && Kotlin.equals(this.x, other.x) && Kotlin.equals(this.y, other.y)))));
  };
  function ClusterLayout() {
    this.nodeSize = false;
    this.dx_0 = 1.0;
    this.dy_0 = 1.0;
  }
  var reversed = Kotlin.kotlin.collections.reversed_7wnvza$;
  ClusterLayout.prototype.cluster_ho9wf8$ = function (root) {
    var tmp$;
    var rootCluster = this.makeCluster_0(root);
    var previousNode = {v: null};
    var x = {v: 0.0};
    var nodes = mutableListOf([rootCluster]);
    var next = ArrayList_init();
    while (!nodes.isEmpty()) {
      var node = nodes.removeAt_za3lpa$(get_lastIndex(nodes));
      next.add_11rb$(node);
      var children = node.children;
      if (!children.isEmpty()) {
        var tmp$_0;
        tmp$_0 = children.iterator();
        while (tmp$_0.hasNext()) {
          var element = tmp$_0.next();
          var tmp$_1;
          nodes.add_11rb$(Kotlin.isType(tmp$_1 = element, ClusterNode) ? tmp$_1 : throwCCE());
        }
      }
    }
    var tmp$_2;
    tmp$_2 = reversed(next).iterator();
    loop_label: while (tmp$_2.hasNext()) {
      var element_0 = tmp$_2.next();
      var children_0 = element_0.children;
      if (!children_0.isEmpty()) {
        var tmp$_3;
        var sum = 0.0;
        tmp$_3 = children_0.iterator();
        while (tmp$_3.hasNext()) {
          var element_1 = tmp$_3.next();
          sum += element_1.x;
        }
        element_0.x = sum / children_0.size;
        var maxBy$result;
        maxBy$break: do {
          var iterator = children_0.iterator();
          if (!iterator.hasNext()) {
            maxBy$result = null;
            break maxBy$break;
          }
          var maxElem = iterator.next();
          var maxValue = maxElem.y;
          while (iterator.hasNext()) {
            var e = iterator.next();
            var v = e.y;
            if (Kotlin.compareTo(maxValue, v) < 0) {
              maxElem = e;
              maxValue = v;
            }
          }
          maxBy$result = maxElem;
        }
         while (false);
        element_0.y = ensureNotNull(maxBy$result).y + 1;
      }
       else {
        if (previousNode.v != null) {
          var nodeB = ensureNotNull(previousNode.v);
          x.v += equals(element_0.parent, nodeB.parent) ? 1 : 2;
          element_0.x = x.v;
        }
         else
          element_0.x = 0.0;
        element_0.y = 0.0;
        previousNode.v = element_0;
      }
    }
    var left = this.leafLeft_0(rootCluster);
    var right = this.leafRight_0(rootCluster);
    var x0 = left.x - ((equals(left.parent, right.parent) ? 1 : 2) / 2 | 0);
    var x1 = right.x + ((equals(right.parent, left.parent) ? 1 : 2) / 2 | 0);
    if (this.nodeSize) {
      var nodes_0 = mutableListOf([rootCluster]);
      var next_0 = ArrayList_init();
      while (!nodes_0.isEmpty()) {
        var node_0 = nodes_0.removeAt_za3lpa$(get_lastIndex(nodes_0));
        next_0.add_11rb$(node_0);
        var children_1 = node_0.children;
        if (!children_1.isEmpty()) {
          var tmp$_4;
          tmp$_4 = children_1.iterator();
          while (tmp$_4.hasNext()) {
            var element_2 = tmp$_4.next();
            var tmp$_5;
            nodes_0.add_11rb$(Kotlin.isType(tmp$_5 = element_2, ClusterNode) ? tmp$_5 : throwCCE());
          }
        }
      }
      var tmp$_6;
      tmp$_6 = reversed(next_0).iterator();
      while (tmp$_6.hasNext()) {
        var element_3 = tmp$_6.next();
        element_3.x = (element_3.x - rootCluster.x) * this.dx_0;
        element_3.y = (rootCluster.y - element_3.y) * this.dy_0;
      }
      tmp$ = rootCluster;
    }
     else {
      var nodes_1 = mutableListOf([rootCluster]);
      var next_1 = ArrayList_init();
      while (!nodes_1.isEmpty()) {
        var node_1 = nodes_1.removeAt_za3lpa$(get_lastIndex(nodes_1));
        next_1.add_11rb$(node_1);
        var children_2 = node_1.children;
        if (!children_2.isEmpty()) {
          var tmp$_7;
          tmp$_7 = children_2.iterator();
          while (tmp$_7.hasNext()) {
            var element_4 = tmp$_7.next();
            var tmp$_8;
            nodes_1.add_11rb$(Kotlin.isType(tmp$_8 = element_4, ClusterNode) ? tmp$_8 : throwCCE());
          }
        }
      }
      var tmp$_9;
      tmp$_9 = reversed(next_1).iterator();
      while (tmp$_9.hasNext()) {
        var element_5 = tmp$_9.next();
        element_5.x = (element_5.x - x0) / (x1 - x0) * this.dx_0;
        element_5.y = rootCluster.y === 0.0 ? 0.0 : (1 - element_5.y / rootCluster.y) * this.dy_0;
      }
      tmp$ = rootCluster;
    }
    return tmp$;
  };
  ClusterLayout.prototype.size_lu1900$ = function (width, height) {
    this.nodeSize = false;
    this.dx_0 = width;
    this.dy_0 = height;
  };
  ClusterLayout.prototype.nodeSize_lu1900$ = function (width, height) {
    this.nodeSize = true;
    this.dx_0 = width;
    this.dy_0 = height;
  };
  ClusterLayout.prototype.makeCluster_0 = function (root) {
    var rootCluster = new ClusterNode(root.data, root.depth, root.height, root.value);
    var nodes = mutableListOf([root]);
    var nodesC = mutableListOf([rootCluster]);
    while (!nodes.isEmpty()) {
      var node = nodes.removeAt_za3lpa$(get_lastIndex(nodes));
      var nodeC = nodesC.removeAt_za3lpa$(get_lastIndex(nodesC));
      var tmp$;
      tmp$ = node.children.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        var c = new ClusterNode(element.data, element.depth, element.height, element.value);
        c.parent = nodeC;
        nodeC.children.add_11rb$(c);
        nodes.add_11rb$(element);
        nodesC.add_11rb$(c);
      }
    }
    return rootCluster;
  };
  ClusterLayout.prototype.leafLeft_0 = function (node) {
    var children = node.children;
    var current = node;
    while (!children.isEmpty()) {
      current = children.get_za3lpa$(0);
      children = current.children;
    }
    return current;
  };
  ClusterLayout.prototype.leafRight_0 = function (node) {
    var children = node.children;
    var current = node;
    while (!children.isEmpty()) {
      current = children.get_za3lpa$(get_lastIndex(children));
      children = current.children;
    }
    return current;
  };
  ClusterLayout.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ClusterLayout',
    interfaces: []
  };
  function Valued() {
  }
  Valued.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Valued',
    interfaces: []
  };
  function Children() {
  }
  Children.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Children',
    interfaces: []
  };
  function ParentValued() {
  }
  ParentValued.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'ParentValued',
    interfaces: [Valued]
  };
  function Node(data, depth, height, value, children, parent) {
    if (depth === void 0)
      depth = 0;
    if (height === void 0)
      height = 0;
    if (value === void 0)
      value = null;
    if (children === void 0) {
      children = ArrayList_init();
    }
    if (parent === void 0)
      parent = null;
    this.data = data;
    this.depth = depth;
    this.height = height;
    this.value_l91w8l$_0 = value;
    this.children_eyxzpr$_0 = children;
    this.parent_2kde4k$_0 = parent;
  }
  Object.defineProperty(Node.prototype, 'value', {
    get: function () {
      return this.value_l91w8l$_0;
    },
    set: function (value) {
      this.value_l91w8l$_0 = value;
    }
  });
  Object.defineProperty(Node.prototype, 'children', {
    get: function () {
      return this.children_eyxzpr$_0;
    }
  });
  Object.defineProperty(Node.prototype, 'parent', {
    get: function () {
      return this.parent_2kde4k$_0;
    },
    set: function (parent) {
      this.parent_2kde4k$_0 = parent;
    }
  });
  Node.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Node',
    interfaces: [Children, ParentValued]
  };
  Node.prototype.component1 = function () {
    return this.data;
  };
  Node.prototype.component2 = function () {
    return this.depth;
  };
  Node.prototype.component3 = function () {
    return this.height;
  };
  Node.prototype.component4 = function () {
    return this.value;
  };
  Node.prototype.component5 = function () {
    return this.children;
  };
  Node.prototype.component6 = function () {
    return this.parent;
  };
  Node.prototype.copy_brex91$ = function (data, depth, height, value, children, parent) {
    return new Node(data === void 0 ? this.data : data, depth === void 0 ? this.depth : depth, height === void 0 ? this.height : height, value === void 0 ? this.value : value, children === void 0 ? this.children : children, parent === void 0 ? this.parent : parent);
  };
  Node.prototype.toString = function () {
    return 'Node(data=' + Kotlin.toString(this.data) + (', depth=' + Kotlin.toString(this.depth)) + (', height=' + Kotlin.toString(this.height)) + (', value=' + Kotlin.toString(this.value)) + (', children=' + Kotlin.toString(this.children)) + (', parent=' + Kotlin.toString(this.parent)) + ')';
  };
  Node.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.data) | 0;
    result = result * 31 + Kotlin.hashCode(this.depth) | 0;
    result = result * 31 + Kotlin.hashCode(this.height) | 0;
    result = result * 31 + Kotlin.hashCode(this.value) | 0;
    result = result * 31 + Kotlin.hashCode(this.children) | 0;
    result = result * 31 + Kotlin.hashCode(this.parent) | 0;
    return result;
  };
  Node.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.data, other.data) && Kotlin.equals(this.depth, other.depth) && Kotlin.equals(this.height, other.height) && Kotlin.equals(this.value, other.value) && Kotlin.equals(this.children, other.children) && Kotlin.equals(this.parent, other.parent)))));
  };
  function Link(source, target) {
    this.source = source;
    this.target = target;
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
  Link.prototype.copy_dgxkr1$ = function (source, target) {
    return new Link(source === void 0 ? this.source : source, target === void 0 ? this.target : target);
  };
  Link.prototype.toString = function () {
    return 'Link(source=' + Kotlin.toString(this.source) + (', target=' + Kotlin.toString(this.target)) + ')';
  };
  Link.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.source) | 0;
    result = result * 31 + Kotlin.hashCode(this.target) | 0;
    return result;
  };
  Link.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.source, other.source) && Kotlin.equals(this.target, other.target)))));
  };
  var downTo = Kotlin.kotlin.ranges.downTo_dqglrj$;
  function hierarchy(data, children, value) {
    if (value === void 0)
      value = null;
    var root = new Node(data);
    var nodes = mutableListOf([root]);
    while (nodes.size > 0) {
      var node = nodes.removeAt_za3lpa$(get_lastIndex(nodes));
      var childs = children(node.data);
      if (childs != null) {
        var tmp$;
        tmp$ = childs.iterator();
        while (tmp$.hasNext()) {
          var element = tmp$.next();
          var child = new Node(element);
          child.parent = node;
          child.depth = node.depth + 1 | 0;
          node.children.add_11rb$(child);
          nodes.add_11rb$(child);
        }
      }
    }
    var nodes_0 = mutableListOf([root]);
    while (!nodes_0.isEmpty()) {
      var node_0 = nodes_0.removeAt_za3lpa$(get_lastIndex(nodes_0));
      computeHeight(node_0);
      var children_0 = node_0.children;
      if (!children_0.isEmpty()) {
        var tmp$_0;
        tmp$_0 = downTo(get_lastIndex(children_0), 0).iterator();
        while (tmp$_0.hasNext()) {
          var element_0 = tmp$_0.next();
          var tmp$_1;
          nodes_0.add_11rb$(Kotlin.isType(tmp$_1 = children_0.get_za3lpa$(element_0), Node) ? tmp$_1 : throwCCE());
        }
      }
    }
    return root;
  }
  function count($receiver) {
    var nodes = mutableListOf([$receiver]);
    var next = ArrayList_init();
    while (!nodes.isEmpty()) {
      var node = nodes.removeAt_za3lpa$(get_lastIndex(nodes));
      next.add_11rb$(node);
      var children = node.children;
      if (!children.isEmpty()) {
        var tmp$;
        tmp$ = children.iterator();
        while (tmp$.hasNext()) {
          var element = tmp$.next();
          var tmp$_0;
          nodes.add_11rb$(Kotlin.isType(tmp$_0 = element, Node) ? tmp$_0 : throwCCE());
        }
      }
    }
    var tmp$_1;
    tmp$_1 = reversed(next).iterator();
    while (tmp$_1.hasNext()) {
      var element_0 = tmp$_1.next();
      nodeCount(element_0);
    }
    return $receiver;
  }
  function sum($receiver, value) {
    if (value === void 0)
      value = null;
    var nodes = mutableListOf([$receiver]);
    var next = ArrayList_init();
    while (!nodes.isEmpty()) {
      var node = nodes.removeAt_za3lpa$(get_lastIndex(nodes));
      next.add_11rb$(node);
      var children = node.children;
      if (!children.isEmpty()) {
        var tmp$;
        tmp$ = children.iterator();
        while (tmp$.hasNext()) {
          var element = tmp$.next();
          var tmp$_0;
          nodes.add_11rb$(Kotlin.isType(tmp$_0 = element, Node) ? tmp$_0 : throwCCE());
        }
      }
    }
    var tmp$_1;
    tmp$_1 = reversed(next).iterator();
    while (tmp$_1.hasNext()) {
      var element_0 = tmp$_1.next();
      var sum = {v: value != null ? value(element_0.data) : 0.0};
      var tmp$_2;
      tmp$_2 = element_0.children.iterator();
      while (tmp$_2.hasNext()) {
        var element_1 = tmp$_2.next();
        if (element_1.value != null)
          sum.v += ensureNotNull(element_1.value);
      }
      element_0.value = sum.v;
    }
    return $receiver;
  }
  function ancestors($receiver) {
    var nodes = mutableListOf([$receiver]);
    var node = $receiver;
    while (node != null) {
      nodes.add_11rb$(node);
      node = node.parent;
    }
    return toList(nodes);
  }
  function descendants($receiver) {
    var nodes = ArrayList_init();
    var next = mutableListOf([$receiver]);
    while (next.size > 0) {
      var current = toMutableList(reversed(next));
      next.clear();
      var node = current.removeAt_za3lpa$(get_lastIndex(current));
      nodes.add_11rb$(node);
      var children = node.children;
      if (!children.isEmpty()) {
        var tmp$;
        tmp$ = downTo(get_lastIndex(children), 0).iterator();
        while (tmp$.hasNext()) {
          var element = tmp$.next();
          var tmp$_0;
          next.add_11rb$(Kotlin.isType(tmp$_0 = children.get_za3lpa$(element), Node) ? tmp$_0 : throwCCE());
        }
      }
    }
    return nodes;
  }
  function leaves($receiver) {
    var leaves = ArrayList_init();
    var nodes = mutableListOf([$receiver]);
    while (!nodes.isEmpty()) {
      var node = nodes.removeAt_za3lpa$(get_lastIndex(nodes));
      if (node.children.isEmpty())
        leaves.add_11rb$(node);
      var children = node.children;
      if (!children.isEmpty()) {
        var tmp$;
        tmp$ = downTo(get_lastIndex(children), 0).iterator();
        while (tmp$.hasNext()) {
          var element = tmp$.next();
          var tmp$_0;
          nodes.add_11rb$(Kotlin.isType(tmp$_0 = children.get_za3lpa$(element), Node) ? tmp$_0 : throwCCE());
        }
      }
    }
    return leaves;
  }
  function links($receiver) {
    var root = $receiver;
    var links = ArrayList_init();
    var next = mutableListOf([root]);
    while (next.size > 0) {
      var current = toMutableList(reversed(next));
      next.clear();
      var node = current.removeAt_za3lpa$(get_lastIndex(current));
      if (!(node != null ? node.equals(root) : null))
        links.add_11rb$(new Link(node.parent, node));
      var children = node.children;
      if (!children.isEmpty()) {
        var tmp$;
        tmp$ = downTo(get_lastIndex(children), 0).iterator();
        while (tmp$.hasNext()) {
          var element = tmp$.next();
          var tmp$_0;
          next.add_11rb$(Kotlin.isType(tmp$_0 = children.get_za3lpa$(element), Node) ? tmp$_0 : throwCCE());
        }
      }
    }
    return toList(links);
  }
  var separation = defineInlineFunction('d2v-hierarchy-js.io.data2viz.hierarchy.separation_5rtcnt$', wrapFunction(function () {
    var equals = Kotlin.equals;
    return function (N_0, isN, nodeA, nodeB) {
      return equals(nodeA.parent, nodeB.parent) ? 1 : 2;
    };
  }));
  var each = defineInlineFunction('d2v-hierarchy-js.io.data2viz.hierarchy.each_ej842r$', wrapFunction(function () {
    var mutableListOf = Kotlin.kotlin.collections.mutableListOf_i5x0yv$;
    var reversed = Kotlin.kotlin.collections.reversed_7wnvza$;
    var toMutableList = Kotlin.kotlin.collections.toMutableList_4c7yge$;
    var get_lastIndex = Kotlin.kotlin.collections.get_lastIndex_55thoc$;
    var downTo = Kotlin.kotlin.ranges.downTo_dqglrj$;
    var throwCCE = Kotlin.throwCCE;
    return function (N_0, isN, $receiver, callback) {
      var next = mutableListOf([$receiver]);
      while (next.size > 0) {
        var current = toMutableList(reversed(next));
        next.clear();
        var node = current.removeAt_za3lpa$(get_lastIndex(current));
        callback(node);
        var children = node.children;
        if (!children.isEmpty()) {
          var tmp$;
          tmp$ = downTo(get_lastIndex(children), 0).iterator();
          while (tmp$.hasNext()) {
            var element = tmp$.next();
            var tmp$_0;
            next.add_11rb$(isN(tmp$_0 = children.get_za3lpa$(element)) ? tmp$_0 : throwCCE());
          }
        }
      }
      return $receiver;
    };
  }));
  var eachBefore = defineInlineFunction('d2v-hierarchy-js.io.data2viz.hierarchy.eachBefore_ej842r$', wrapFunction(function () {
    var mutableListOf = Kotlin.kotlin.collections.mutableListOf_i5x0yv$;
    var get_lastIndex = Kotlin.kotlin.collections.get_lastIndex_55thoc$;
    var downTo = Kotlin.kotlin.ranges.downTo_dqglrj$;
    var throwCCE = Kotlin.throwCCE;
    return function (N_0, isN, $receiver, callback) {
      var nodes = mutableListOf([$receiver]);
      while (!nodes.isEmpty()) {
        var node = nodes.removeAt_za3lpa$(get_lastIndex(nodes));
        callback(node);
        var children = node.children;
        if (!children.isEmpty()) {
          var tmp$;
          tmp$ = downTo(get_lastIndex(children), 0).iterator();
          while (tmp$.hasNext()) {
            var element = tmp$.next();
            var tmp$_0;
            nodes.add_11rb$(isN(tmp$_0 = children.get_za3lpa$(element)) ? tmp$_0 : throwCCE());
          }
        }
      }
      return $receiver;
    };
  }));
  var eachAfter = defineInlineFunction('d2v-hierarchy-js.io.data2viz.hierarchy.eachAfter_ej842r$', wrapFunction(function () {
    var mutableListOf = Kotlin.kotlin.collections.mutableListOf_i5x0yv$;
    var get_lastIndex = Kotlin.kotlin.collections.get_lastIndex_55thoc$;
    var throwCCE = Kotlin.throwCCE;
    var reversed = Kotlin.kotlin.collections.reversed_7wnvza$;
    var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_287e2$;
    return function (N_0, isN, $receiver, callback) {
      var nodes = mutableListOf([$receiver]);
      var next = ArrayList_init();
      while (!nodes.isEmpty()) {
        var node = nodes.removeAt_za3lpa$(get_lastIndex(nodes));
        next.add_11rb$(node);
        var children = node.children;
        if (!children.isEmpty()) {
          var tmp$;
          tmp$ = children.iterator();
          while (tmp$.hasNext()) {
            var element = tmp$.next();
            var tmp$_0;
            nodes.add_11rb$(isN(tmp$_0 = element) ? tmp$_0 : throwCCE());
          }
        }
      }
      var tmp$_1;
      tmp$_1 = reversed(next).iterator();
      while (tmp$_1.hasNext()) {
        var element_0 = tmp$_1.next();
        callback(element_0);
      }
      return $receiver;
    };
  }));
  function computeHeight(node) {
    var n = node;
    var height = 0;
    n.height = height;
    height = height + 1 | 0;
    while (n.parent != null && ensureNotNull(n.parent).height < height) {
      ensureNotNull(n.parent).height = height;
      n = ensureNotNull(n.parent);
      height = height + 1 | 0;
    }
  }
  function nodeCount(node) {
    var sum = {v: 0.0};
    var children = node.children;
    if (children.isEmpty())
      sum.v = 1.0;
    else {
      var tmp$;
      tmp$ = children.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        if (element.value != null)
          sum.v += ensureNotNull(element.value);
      }
    }
    node.value = sum.v;
  }
  function CircleValues() {
  }
  CircleValues.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'CircleValues',
    interfaces: []
  };
  function PackNode(data, depth, height, value, children, parent, x, y, r, previous, next) {
    if (children === void 0) {
      children = ArrayList_init();
    }
    if (parent === void 0)
      parent = null;
    if (x === void 0)
      x = 0.0;
    if (y === void 0)
      y = 0.0;
    if (r === void 0)
      r = 0.0;
    if (previous === void 0)
      previous = null;
    if (next === void 0)
      next = null;
    this.data = data;
    this.depth = depth;
    this.height = height;
    this.value_v9gq5g$_0 = value;
    this.children_f4kfig$_0 = children;
    this.parent_8bxdh9$_0 = parent;
    this.x_xr2vbf$_0 = x;
    this.y_xr2vak$_0 = y;
    this.r_xr2vgl$_0 = r;
    this.previous = previous;
    this.next = next;
  }
  Object.defineProperty(PackNode.prototype, 'value', {
    get: function () {
      return this.value_v9gq5g$_0;
    },
    set: function (value) {
      this.value_v9gq5g$_0 = value;
    }
  });
  Object.defineProperty(PackNode.prototype, 'children', {
    get: function () {
      return this.children_f4kfig$_0;
    }
  });
  Object.defineProperty(PackNode.prototype, 'parent', {
    get: function () {
      return this.parent_8bxdh9$_0;
    },
    set: function (parent) {
      this.parent_8bxdh9$_0 = parent;
    }
  });
  Object.defineProperty(PackNode.prototype, 'x', {
    get: function () {
      return this.x_xr2vbf$_0;
    },
    set: function (x) {
      this.x_xr2vbf$_0 = x;
    }
  });
  Object.defineProperty(PackNode.prototype, 'y', {
    get: function () {
      return this.y_xr2vak$_0;
    },
    set: function (y) {
      this.y_xr2vak$_0 = y;
    }
  });
  Object.defineProperty(PackNode.prototype, 'r', {
    get: function () {
      return this.r_xr2vgl$_0;
    },
    set: function (r) {
      this.r_xr2vgl$_0 = r;
    }
  });
  PackNode.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'PackNode',
    interfaces: [CircleValues, Children, ParentValued]
  };
  PackNode.prototype.component1 = function () {
    return this.data;
  };
  PackNode.prototype.component2 = function () {
    return this.depth;
  };
  PackNode.prototype.component3 = function () {
    return this.height;
  };
  PackNode.prototype.component4 = function () {
    return this.value;
  };
  PackNode.prototype.component5 = function () {
    return this.children;
  };
  PackNode.prototype.component6 = function () {
    return this.parent;
  };
  PackNode.prototype.component7 = function () {
    return this.x;
  };
  PackNode.prototype.component8 = function () {
    return this.y;
  };
  PackNode.prototype.component9 = function () {
    return this.r;
  };
  PackNode.prototype.component10 = function () {
    return this.previous;
  };
  PackNode.prototype.component11 = function () {
    return this.next;
  };
  PackNode.prototype.copy_g0gwy3$ = function (data, depth, height, value, children, parent, x, y, r, previous, next) {
    return new PackNode(data === void 0 ? this.data : data, depth === void 0 ? this.depth : depth, height === void 0 ? this.height : height, value === void 0 ? this.value : value, children === void 0 ? this.children : children, parent === void 0 ? this.parent : parent, x === void 0 ? this.x : x, y === void 0 ? this.y : y, r === void 0 ? this.r : r, previous === void 0 ? this.previous : previous, next === void 0 ? this.next : next);
  };
  PackNode.prototype.toString = function () {
    return 'PackNode(data=' + Kotlin.toString(this.data) + (', depth=' + Kotlin.toString(this.depth)) + (', height=' + Kotlin.toString(this.height)) + (', value=' + Kotlin.toString(this.value)) + (', children=' + Kotlin.toString(this.children)) + (', parent=' + Kotlin.toString(this.parent)) + (', x=' + Kotlin.toString(this.x)) + (', y=' + Kotlin.toString(this.y)) + (', r=' + Kotlin.toString(this.r)) + (', previous=' + Kotlin.toString(this.previous)) + (', next=' + Kotlin.toString(this.next)) + ')';
  };
  PackNode.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.data) | 0;
    result = result * 31 + Kotlin.hashCode(this.depth) | 0;
    result = result * 31 + Kotlin.hashCode(this.height) | 0;
    result = result * 31 + Kotlin.hashCode(this.value) | 0;
    result = result * 31 + Kotlin.hashCode(this.children) | 0;
    result = result * 31 + Kotlin.hashCode(this.parent) | 0;
    result = result * 31 + Kotlin.hashCode(this.x) | 0;
    result = result * 31 + Kotlin.hashCode(this.y) | 0;
    result = result * 31 + Kotlin.hashCode(this.r) | 0;
    result = result * 31 + Kotlin.hashCode(this.previous) | 0;
    result = result * 31 + Kotlin.hashCode(this.next) | 0;
    return result;
  };
  PackNode.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.data, other.data) && Kotlin.equals(this.depth, other.depth) && Kotlin.equals(this.height, other.height) && Kotlin.equals(this.value, other.value) && Kotlin.equals(this.children, other.children) && Kotlin.equals(this.parent, other.parent) && Kotlin.equals(this.x, other.x) && Kotlin.equals(this.y, other.y) && Kotlin.equals(this.r, other.r) && Kotlin.equals(this.previous, other.previous) && Kotlin.equals(this.next, other.next)))));
  };
  function packNode(node) {
    return new PackNode(node.data, node.depth, node.height, node.value, node.children, node.parent, node.x, node.y, node.r, node.previous, node.next);
  }
  function PackLayout() {
    this.constantZero_0 = PackLayout$constantZero$lambda;
    this.defaultRadius_0 = PackLayout$defaultRadius$lambda;
    this.dx_0 = 1.0;
    this.dy_0 = 1.0;
    this.radius = null;
    this.padding = this.constantZero_0;
  }
  var Math_0 = Math;
  PackLayout.prototype.pack_zhympb$ = function (root) {
    var rootPack = this.makePack_0(root);
    rootPack.x = this.dx_0 / 2;
    rootPack.y = this.dy_0 / 2;
    if (this.radius != null) {
      var callback = this.radiusLeaf_0(ensureNotNull(this.radius));
      var nodes = mutableListOf([rootPack]);
      while (!nodes.isEmpty()) {
        var node = nodes.removeAt_za3lpa$(get_lastIndex(nodes));
        callback(node);
        var children = node.children;
        if (!children.isEmpty()) {
          var tmp$;
          tmp$ = downTo(get_lastIndex(children), 0).iterator();
          while (tmp$.hasNext()) {
            var element = tmp$.next();
            var tmp$_0;
            nodes.add_11rb$(Kotlin.isType(tmp$_0 = children.get_za3lpa$(element), PackNode) ? tmp$_0 : throwCCE());
          }
        }
      }
      var callback_0 = this.packChildren_0(this.padding, 0.5);
      var nodes_0 = mutableListOf([rootPack]);
      var next = ArrayList_init();
      while (!nodes_0.isEmpty()) {
        var node_0 = nodes_0.removeAt_za3lpa$(get_lastIndex(nodes_0));
        next.add_11rb$(node_0);
        var children_0 = node_0.children;
        if (!children_0.isEmpty()) {
          var tmp$_1;
          tmp$_1 = children_0.iterator();
          while (tmp$_1.hasNext()) {
            var element_0 = tmp$_1.next();
            var tmp$_2;
            nodes_0.add_11rb$(Kotlin.isType(tmp$_2 = element_0, PackNode) ? tmp$_2 : throwCCE());
          }
        }
      }
      var tmp$_3;
      tmp$_3 = reversed(next).iterator();
      while (tmp$_3.hasNext()) {
        var element_1 = tmp$_3.next();
        callback_0(element_1);
      }
      var callback_1 = this.translateChild_0(1.0);
      var nodes_1 = mutableListOf([rootPack]);
      while (!nodes_1.isEmpty()) {
        var node_1 = nodes_1.removeAt_za3lpa$(get_lastIndex(nodes_1));
        callback_1(node_1);
        var children_1 = node_1.children;
        if (!children_1.isEmpty()) {
          var tmp$_4;
          tmp$_4 = downTo(get_lastIndex(children_1), 0).iterator();
          while (tmp$_4.hasNext()) {
            var element_2 = tmp$_4.next();
            var tmp$_5;
            nodes_1.add_11rb$(Kotlin.isType(tmp$_5 = children_1.get_za3lpa$(element_2), PackNode) ? tmp$_5 : throwCCE());
          }
        }
      }
    }
     else {
      var callback_2 = this.radiusLeaf_0(this.defaultRadius_0);
      var nodes_2 = mutableListOf([rootPack]);
      while (!nodes_2.isEmpty()) {
        var node_2 = nodes_2.removeAt_za3lpa$(get_lastIndex(nodes_2));
        callback_2(node_2);
        var children_2 = node_2.children;
        if (!children_2.isEmpty()) {
          var tmp$_6;
          tmp$_6 = downTo(get_lastIndex(children_2), 0).iterator();
          while (tmp$_6.hasNext()) {
            var element_3 = tmp$_6.next();
            var tmp$_7;
            nodes_2.add_11rb$(Kotlin.isType(tmp$_7 = children_2.get_za3lpa$(element_3), PackNode) ? tmp$_7 : throwCCE());
          }
        }
      }
      var callback_3 = this.packChildren_0(this.constantZero_0, 1.0);
      var nodes_3 = mutableListOf([rootPack]);
      var next_0 = ArrayList_init();
      while (!nodes_3.isEmpty()) {
        var node_3 = nodes_3.removeAt_za3lpa$(get_lastIndex(nodes_3));
        next_0.add_11rb$(node_3);
        var children_3 = node_3.children;
        if (!children_3.isEmpty()) {
          var tmp$_8;
          tmp$_8 = children_3.iterator();
          while (tmp$_8.hasNext()) {
            var element_4 = tmp$_8.next();
            var tmp$_9;
            nodes_3.add_11rb$(Kotlin.isType(tmp$_9 = element_4, PackNode) ? tmp$_9 : throwCCE());
          }
        }
      }
      var tmp$_10;
      tmp$_10 = reversed(next_0).iterator();
      while (tmp$_10.hasNext()) {
        var element_5 = tmp$_10.next();
        callback_3(element_5);
      }
      var tmp$_11 = this.padding;
      var tmp$_12 = rootPack.r;
      var a = this.dx_0;
      var b = this.dy_0;
      var callback_4 = this.packChildren_0(tmp$_11, tmp$_12 / Math_0.min(a, b));
      var nodes_4 = mutableListOf([rootPack]);
      var next_1 = ArrayList_init();
      while (!nodes_4.isEmpty()) {
        var node_4 = nodes_4.removeAt_za3lpa$(get_lastIndex(nodes_4));
        next_1.add_11rb$(node_4);
        var children_4 = node_4.children;
        if (!children_4.isEmpty()) {
          var tmp$_13;
          tmp$_13 = children_4.iterator();
          while (tmp$_13.hasNext()) {
            var element_6 = tmp$_13.next();
            var tmp$_14;
            nodes_4.add_11rb$(Kotlin.isType(tmp$_14 = element_6, PackNode) ? tmp$_14 : throwCCE());
          }
        }
      }
      var tmp$_15;
      tmp$_15 = reversed(next_1).iterator();
      while (tmp$_15.hasNext()) {
        var element_7 = tmp$_15.next();
        callback_4(element_7);
      }
      var a_0 = this.dx_0;
      var b_0 = this.dy_0;
      var callback_5 = this.translateChild_0(Math_0.min(a_0, b_0) / (2 * rootPack.r));
      var nodes_5 = mutableListOf([rootPack]);
      while (!nodes_5.isEmpty()) {
        var node_5 = nodes_5.removeAt_za3lpa$(get_lastIndex(nodes_5));
        callback_5(node_5);
        var children_5 = node_5.children;
        if (!children_5.isEmpty()) {
          var tmp$_16;
          tmp$_16 = downTo(get_lastIndex(children_5), 0).iterator();
          while (tmp$_16.hasNext()) {
            var element_8 = tmp$_16.next();
            var tmp$_17;
            nodes_5.add_11rb$(Kotlin.isType(tmp$_17 = children_5.get_za3lpa$(element_8), PackNode) ? tmp$_17 : throwCCE());
          }
        }
      }
    }
    return rootPack;
  };
  PackLayout.prototype.size_lu1900$ = function (width, height) {
    this.dx_0 = width;
    this.dy_0 = height;
  };
  PackLayout.prototype.radius_wrqq3f$ = function (radius) {
    this.radius = radius;
  };
  PackLayout.prototype.padding_jdyfae$ = function (padding) {
    this.padding = padding;
  };
  function PackLayout$radiusLeaf$lambda(closure$radius) {
    return function (node) {
      if (node.children.isEmpty()) {
        var b = closure$radius(node);
        node.r = Math_0.max(0.0, b);
      }
      return Unit;
    };
  }
  PackLayout.prototype.radiusLeaf_0 = function (radius) {
    return PackLayout$radiusLeaf$lambda(radius);
  };
  PackLayout.prototype.makePack_0 = function (root) {
    var rootPack = new PackNode(root.data, root.depth, root.height, root.value);
    var nodes = mutableListOf([root]);
    var nodesP = mutableListOf([rootPack]);
    while (!nodes.isEmpty()) {
      var node = nodes.removeAt_za3lpa$(get_lastIndex(nodes));
      var nodeP = nodesP.removeAt_za3lpa$(get_lastIndex(nodesP));
      var tmp$;
      tmp$ = node.children.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        var c = new PackNode(element.data, element.depth, element.height, element.value);
        c.parent = nodeP;
        nodeP.children.add_11rb$(c);
        nodes.add_11rb$(element);
        nodesP.add_11rb$(c);
      }
    }
    return rootPack;
  };
  function PackLayout$packChildren$lambda(closure$padding, closure$k) {
    return function (node) {
      if (!node.children.isEmpty()) {
        var children = node.children;
        var r = closure$padding(node) * closure$k;
        var tmp$;
        tmp$ = children.iterator();
        while (tmp$.hasNext()) {
          var element = tmp$.next();
          element.r = element.r + r;
        }
        var e = packEnclose(children);
        var tmp$_0;
        tmp$_0 = children.iterator();
        while (tmp$_0.hasNext()) {
          var element_0 = tmp$_0.next();
          element_0.r = element_0.r - r;
        }
        node.r = e + r;
      }
      return Unit;
    };
  }
  PackLayout.prototype.packChildren_0 = function (padding, k) {
    return PackLayout$packChildren$lambda(padding, k);
  };
  function PackLayout$translateChild$lambda(closure$k) {
    return function (node) {
      var parent = node.parent;
      node.r = node.r * closure$k;
      if (parent != null) {
        node.x = parent.x + closure$k * node.x;
        node.y = parent.y + closure$k * node.y;
      }
      return Unit;
    };
  }
  PackLayout.prototype.translateChild_0 = function (k) {
    return PackLayout$translateChild$lambda(k);
  };
  function PackLayout$constantZero$lambda(it) {
    return 0.0;
  }
  function PackLayout$defaultRadius$lambda(it) {
    var x = ensureNotNull(it.value);
    return Math_0.sqrt(x);
  }
  PackLayout.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'PackLayout',
    interfaces: []
  };
  function PartitionLayout() {
    this.round = false;
    this.dx_0 = 1.0;
    this.dy_0 = 1.0;
    this.padding = 0.0;
  }
  PartitionLayout.prototype.partition_ho9wf8$ = function (root) {
    var rootNode = makeTreemap(root);
    var n = rootNode.height + 1 | 0;
    rootNode.x0 = this.padding;
    rootNode.y0 = this.padding;
    rootNode.x1 = this.dx_0;
    rootNode.y1 = this.dy_0 / n;
    var callback = this.positionNode_0(this.dy_0, n);
    var nodes = mutableListOf([rootNode]);
    while (!nodes.isEmpty()) {
      var node = nodes.removeAt_za3lpa$(get_lastIndex(nodes));
      callback(node);
      var children = node.children;
      if (!children.isEmpty()) {
        var tmp$;
        tmp$ = downTo(get_lastIndex(children), 0).iterator();
        while (tmp$.hasNext()) {
          var element = tmp$.next();
          var tmp$_0;
          nodes.add_11rb$(Kotlin.isType(tmp$_0 = children.get_za3lpa$(element), TreemapNode) ? tmp$_0 : throwCCE());
        }
      }
    }
    if (this.round) {
      var nodes_0 = mutableListOf([rootNode]);
      while (!nodes_0.isEmpty()) {
        var node_0 = nodes_0.removeAt_za3lpa$(get_lastIndex(nodes_0));
        roundNode(node_0);
        var children_0 = node_0.children;
        if (!children_0.isEmpty()) {
          var tmp$_1;
          tmp$_1 = downTo(get_lastIndex(children_0), 0).iterator();
          while (tmp$_1.hasNext()) {
            var element_0 = tmp$_1.next();
            var tmp$_2;
            nodes_0.add_11rb$(Kotlin.isType(tmp$_2 = children_0.get_za3lpa$(element_0), TreemapNode) ? tmp$_2 : throwCCE());
          }
        }
      }
    }
    return rootNode;
  };
  function PartitionLayout$positionNode$lambda(closure$dy, closure$n, this$PartitionLayout) {
    return function (node) {
      if (!node.children.isEmpty()) {
        treemapDice(node, node.x0, closure$dy * (node.depth + 1 | 0) / closure$n, node.x1, closure$dy * (node.depth + 2 | 0) / closure$n);
      }
      var x0 = node.x0;
      var y0 = node.y0;
      var x1 = node.x1 - this$PartitionLayout.padding;
      var y1 = node.y1 - this$PartitionLayout.padding;
      if (x1 < x0) {
        x0 = (x0 + x1) / 2;
        x1 = x0;
      }
      if (y1 < y0) {
        y0 = (y0 + y1) / 2;
        y1 = y0;
      }
      node.x0 = x0;
      node.y0 = y0;
      node.x1 = x1;
      node.y1 = y1;
      return Unit;
    };
  }
  PartitionLayout.prototype.positionNode_0 = function (dy, n) {
    return PartitionLayout$positionNode$lambda(dy, n, this);
  };
  PartitionLayout.prototype.size_lu1900$ = function (width, height) {
    this.dx_0 = width;
    this.dy_0 = height;
  };
  PartitionLayout.prototype.makeCluster_0 = function (root) {
    var rootCluster = new ClusterNode(root.data, root.depth, root.height, root.value);
    var nodes = mutableListOf([root]);
    var nodesC = mutableListOf([rootCluster]);
    while (!nodes.isEmpty()) {
      var node = nodes.removeAt_za3lpa$(get_lastIndex(nodes));
      var nodeC = nodesC.removeAt_za3lpa$(get_lastIndex(nodesC));
      var tmp$;
      tmp$ = node.children.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        var c = new ClusterNode(element.data, element.depth, element.height, element.value);
        c.parent = nodeC;
        nodeC.children.add_11rb$(c);
        nodes.add_11rb$(element);
        nodesC.add_11rb$(c);
      }
    }
    return rootCluster;
  };
  PartitionLayout.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'PartitionLayout',
    interfaces: []
  };
  function TreeNode(data, depth, height, value, index, x, y, A, ancestor, z, m, c, s, t, children, parent) {
    if (index === void 0)
      index = 0;
    if (x === void 0)
      x = 0.0;
    if (y === void 0)
      y = 0.0;
    if (A === void 0)
      A = null;
    if (ancestor === void 0)
      ancestor = null;
    if (z === void 0)
      z = 0.0;
    if (m === void 0)
      m = 0.0;
    if (c === void 0)
      c = 0.0;
    if (s === void 0)
      s = 0.0;
    if (t === void 0)
      t = null;
    if (children === void 0) {
      children = ArrayList_init();
    }
    if (parent === void 0)
      parent = null;
    this.data = data;
    this.depth = depth;
    this.height = height;
    this.value_p5jq4p$_0 = value;
    this.index_8be2vx$ = index;
    this.x = x;
    this.y = y;
    this.A_8be2vx$ = A;
    this.ancestor_8be2vx$ = ancestor;
    this.z_8be2vx$ = z;
    this.m_8be2vx$ = m;
    this.c_8be2vx$ = c;
    this.s_8be2vx$ = s;
    this.t_8be2vx$ = t;
    this.children_l3w3f1$_0 = children;
    this.parent_fdzrsu$_0 = parent;
  }
  Object.defineProperty(TreeNode.prototype, 'value', {
    get: function () {
      return this.value_p5jq4p$_0;
    },
    set: function (value) {
      this.value_p5jq4p$_0 = value;
    }
  });
  Object.defineProperty(TreeNode.prototype, 'children', {
    get: function () {
      return this.children_l3w3f1$_0;
    }
  });
  Object.defineProperty(TreeNode.prototype, 'parent', {
    get: function () {
      return this.parent_fdzrsu$_0;
    },
    set: function (parent) {
      this.parent_fdzrsu$_0 = parent;
    }
  });
  TreeNode.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'TreeNode',
    interfaces: [Children, ParentValued]
  };
  TreeNode.prototype.component1 = function () {
    return this.data;
  };
  TreeNode.prototype.component2 = function () {
    return this.depth;
  };
  TreeNode.prototype.component3 = function () {
    return this.height;
  };
  TreeNode.prototype.component4 = function () {
    return this.value;
  };
  TreeNode.prototype.component5_8be2vx$ = function () {
    return this.index_8be2vx$;
  };
  TreeNode.prototype.component6 = function () {
    return this.x;
  };
  TreeNode.prototype.component7 = function () {
    return this.y;
  };
  TreeNode.prototype.component8_8be2vx$ = function () {
    return this.A_8be2vx$;
  };
  TreeNode.prototype.component9_8be2vx$ = function () {
    return this.ancestor_8be2vx$;
  };
  TreeNode.prototype.component10_8be2vx$ = function () {
    return this.z_8be2vx$;
  };
  TreeNode.prototype.component11_8be2vx$ = function () {
    return this.m_8be2vx$;
  };
  TreeNode.prototype.component12_8be2vx$ = function () {
    return this.c_8be2vx$;
  };
  TreeNode.prototype.component13_8be2vx$ = function () {
    return this.s_8be2vx$;
  };
  TreeNode.prototype.component14_8be2vx$ = function () {
    return this.t_8be2vx$;
  };
  TreeNode.prototype.component15 = function () {
    return this.children;
  };
  TreeNode.prototype.component16 = function () {
    return this.parent;
  };
  TreeNode.prototype.copy_hfy7vb$ = function (data, depth, height, value, index, x, y, A, ancestor, z, m, c, s, t, children, parent) {
    return new TreeNode(data === void 0 ? this.data : data, depth === void 0 ? this.depth : depth, height === void 0 ? this.height : height, value === void 0 ? this.value : value, index === void 0 ? this.index_8be2vx$ : index, x === void 0 ? this.x : x, y === void 0 ? this.y : y, A === void 0 ? this.A_8be2vx$ : A, ancestor === void 0 ? this.ancestor_8be2vx$ : ancestor, z === void 0 ? this.z_8be2vx$ : z, m === void 0 ? this.m_8be2vx$ : m, c === void 0 ? this.c_8be2vx$ : c, s === void 0 ? this.s_8be2vx$ : s, t === void 0 ? this.t_8be2vx$ : t, children === void 0 ? this.children : children, parent === void 0 ? this.parent : parent);
  };
  TreeNode.prototype.toString = function () {
    return 'TreeNode(data=' + Kotlin.toString(this.data) + (', depth=' + Kotlin.toString(this.depth)) + (', height=' + Kotlin.toString(this.height)) + (', value=' + Kotlin.toString(this.value)) + (', index=' + Kotlin.toString(this.index_8be2vx$)) + (', x=' + Kotlin.toString(this.x)) + (', y=' + Kotlin.toString(this.y)) + (', A=' + Kotlin.toString(this.A_8be2vx$)) + (', ancestor=' + Kotlin.toString(this.ancestor_8be2vx$)) + (', z=' + Kotlin.toString(this.z_8be2vx$)) + (', m=' + Kotlin.toString(this.m_8be2vx$)) + (', c=' + Kotlin.toString(this.c_8be2vx$)) + (', s=' + Kotlin.toString(this.s_8be2vx$)) + (', t=' + Kotlin.toString(this.t_8be2vx$)) + (', children=' + Kotlin.toString(this.children)) + (', parent=' + Kotlin.toString(this.parent)) + ')';
  };
  TreeNode.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.data) | 0;
    result = result * 31 + Kotlin.hashCode(this.depth) | 0;
    result = result * 31 + Kotlin.hashCode(this.height) | 0;
    result = result * 31 + Kotlin.hashCode(this.value) | 0;
    result = result * 31 + Kotlin.hashCode(this.index_8be2vx$) | 0;
    result = result * 31 + Kotlin.hashCode(this.x) | 0;
    result = result * 31 + Kotlin.hashCode(this.y) | 0;
    result = result * 31 + Kotlin.hashCode(this.A_8be2vx$) | 0;
    result = result * 31 + Kotlin.hashCode(this.ancestor_8be2vx$) | 0;
    result = result * 31 + Kotlin.hashCode(this.z_8be2vx$) | 0;
    result = result * 31 + Kotlin.hashCode(this.m_8be2vx$) | 0;
    result = result * 31 + Kotlin.hashCode(this.c_8be2vx$) | 0;
    result = result * 31 + Kotlin.hashCode(this.s_8be2vx$) | 0;
    result = result * 31 + Kotlin.hashCode(this.t_8be2vx$) | 0;
    result = result * 31 + Kotlin.hashCode(this.children) | 0;
    result = result * 31 + Kotlin.hashCode(this.parent) | 0;
    return result;
  };
  TreeNode.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.data, other.data) && Kotlin.equals(this.depth, other.depth) && Kotlin.equals(this.height, other.height) && Kotlin.equals(this.value, other.value) && Kotlin.equals(this.index_8be2vx$, other.index_8be2vx$) && Kotlin.equals(this.x, other.x) && Kotlin.equals(this.y, other.y) && Kotlin.equals(this.A_8be2vx$, other.A_8be2vx$) && Kotlin.equals(this.ancestor_8be2vx$, other.ancestor_8be2vx$) && Kotlin.equals(this.z_8be2vx$, other.z_8be2vx$) && Kotlin.equals(this.m_8be2vx$, other.m_8be2vx$) && Kotlin.equals(this.c_8be2vx$, other.c_8be2vx$) && Kotlin.equals(this.s_8be2vx$, other.s_8be2vx$) && Kotlin.equals(this.t_8be2vx$, other.t_8be2vx$) && Kotlin.equals(this.children, other.children) && Kotlin.equals(this.parent, other.parent)))));
  };
  function TreeLayout() {
    this.nodeSize_0 = false;
    this.dx_0 = 1.0;
    this.dy_0 = 1.0;
  }
  TreeLayout.prototype.tree_ho9wf8$ = function (root) {
    var tmp$;
    var rootChild = this.makeTree_0(root);
    var callback = getCallableRef('firstWalk', function ($receiver, v) {
      return $receiver.firstWalk_0(v), Unit;
    }.bind(null, this));
    var nodes = mutableListOf([rootChild]);
    var next = ArrayList_init();
    while (!nodes.isEmpty()) {
      var node = nodes.removeAt_za3lpa$(get_lastIndex(nodes));
      next.add_11rb$(node);
      var children = node.children;
      if (!children.isEmpty()) {
        var tmp$_0;
        tmp$_0 = children.iterator();
        while (tmp$_0.hasNext()) {
          var element = tmp$_0.next();
          var tmp$_1;
          nodes.add_11rb$(Kotlin.isType(tmp$_1 = element, TreeNode) ? tmp$_1 : throwCCE());
        }
      }
    }
    var tmp$_2;
    tmp$_2 = reversed(next).iterator();
    while (tmp$_2.hasNext()) {
      var element_0 = tmp$_2.next();
      callback(element_0);
    }
    ensureNotNull(rootChild.parent).m_8be2vx$ = -rootChild.z_8be2vx$;
    var callback_0 = getCallableRef('secondWalk', function ($receiver, v) {
      return $receiver.secondWalk_0(v), Unit;
    }.bind(null, this));
    var nodes_0 = mutableListOf([rootChild]);
    while (!nodes_0.isEmpty()) {
      var node_0 = nodes_0.removeAt_za3lpa$(get_lastIndex(nodes_0));
      callback_0(node_0);
      var children_0 = node_0.children;
      if (!children_0.isEmpty()) {
        var tmp$_3;
        tmp$_3 = downTo(get_lastIndex(children_0), 0).iterator();
        while (tmp$_3.hasNext()) {
          var element_1 = tmp$_3.next();
          var tmp$_4;
          nodes_0.add_11rb$(Kotlin.isType(tmp$_4 = children_0.get_za3lpa$(element_1), TreeNode) ? tmp$_4 : throwCCE());
        }
      }
    }
    if (this.nodeSize_0) {
      var callback_1 = getCallableRef('sizeNode', function ($receiver, node) {
        return $receiver.sizeNode_0(node), Unit;
      }.bind(null, this));
      var nodes_1 = mutableListOf([rootChild]);
      while (!nodes_1.isEmpty()) {
        var node_1 = nodes_1.removeAt_za3lpa$(get_lastIndex(nodes_1));
        callback_1(node_1);
        var children_1 = node_1.children;
        if (!children_1.isEmpty()) {
          var tmp$_5;
          tmp$_5 = downTo(get_lastIndex(children_1), 0).iterator();
          while (tmp$_5.hasNext()) {
            var element_2 = tmp$_5.next();
            var tmp$_6;
            nodes_1.add_11rb$(Kotlin.isType(tmp$_6 = children_1.get_za3lpa$(element_2), TreeNode) ? tmp$_6 : throwCCE());
          }
        }
      }
    }
     else {
      var left = {v: rootChild};
      var right = {v: rootChild};
      var bottom = {v: rootChild};
      var nodes_2 = mutableListOf([rootChild]);
      while (!nodes_2.isEmpty()) {
        var node_2 = nodes_2.removeAt_za3lpa$(get_lastIndex(nodes_2));
        if (node_2.x < left.v.x)
          left.v = node_2;
        if (node_2.x > right.v.x)
          right.v = node_2;
        if (node_2.depth > bottom.v.depth)
          bottom.v = node_2;
        var children_2 = node_2.children;
        if (!children_2.isEmpty()) {
          var tmp$_7;
          tmp$_7 = downTo(get_lastIndex(children_2), 0).iterator();
          while (tmp$_7.hasNext()) {
            var element_3 = tmp$_7.next();
            var tmp$_8;
            nodes_2.add_11rb$(Kotlin.isType(tmp$_8 = children_2.get_za3lpa$(element_3), TreeNode) ? tmp$_8 : throwCCE());
          }
        }
      }
      var tmp$_9;
      if ((tmp$ = left.v) != null ? tmp$.equals(right.v) : null)
        tmp$_9 = 1.0;
      else {
        var nodeA = left.v;
        var nodeB = right.v;
        tmp$_9 = (equals(nodeA.parent, nodeB.parent) ? 1 : 2) / 2.0;
      }
      var s = tmp$_9;
      var tx = s - left.v.x;
      var kx = this.dx_0 / (right.v.x + s + tx);
      var ky = this.dy_0 / (bottom.v.depth === 0 ? 1.0 : bottom.v.depth);
      var nodes_3 = mutableListOf([rootChild]);
      while (!nodes_3.isEmpty()) {
        var node_3 = nodes_3.removeAt_za3lpa$(get_lastIndex(nodes_3));
        node_3.x = (node_3.x + tx) * kx;
        node_3.y = node_3.depth * ky;
        var children_3 = node_3.children;
        if (!children_3.isEmpty()) {
          var tmp$_10;
          tmp$_10 = downTo(get_lastIndex(children_3), 0).iterator();
          while (tmp$_10.hasNext()) {
            var element_4 = tmp$_10.next();
            var tmp$_11;
            nodes_3.add_11rb$(Kotlin.isType(tmp$_11 = children_3.get_za3lpa$(element_4), TreeNode) ? tmp$_11 : throwCCE());
          }
        }
      }
    }
    return rootChild;
  };
  TreeLayout.prototype.size_lu1900$ = function (width, height) {
    this.nodeSize_0 = false;
    this.dx_0 = width;
    this.dy_0 = height;
  };
  TreeLayout.prototype.nodeSize_lu1900$ = function (width, height) {
    this.nodeSize_0 = true;
    this.dx_0 = width;
    this.dy_0 = height;
  };
  TreeLayout.prototype.firstWalk_0 = function (v) {
    var children = v.children;
    var siblings = ensureNotNull(v.parent).children;
    var w = v.index_8be2vx$ !== 0 ? siblings.get_za3lpa$(v.index_8be2vx$ - 1 | 0) : null;
    if (!children.isEmpty()) {
      this.executeShifts_0(v);
      var firstChild = children.get_za3lpa$(0);
      var lastChild = children.get_za3lpa$(get_lastIndex(children));
      var midpoint = (firstChild.z_8be2vx$ + lastChild.z_8be2vx$) / 2.0;
      if (w != null) {
        v.z_8be2vx$ = w.z_8be2vx$ + (equals(v.parent, w.parent) ? 1 : 2);
        v.m_8be2vx$ = v.z_8be2vx$ - midpoint;
      }
       else {
        v.z_8be2vx$ = midpoint;
      }
    }
     else if (w != null) {
      v.z_8be2vx$ = w.z_8be2vx$ + (equals(v.parent, w.parent) ? 1 : 2);
    }
    var parent = ensureNotNull(v.parent);
    var ancestor = parent.A_8be2vx$ != null ? ensureNotNull(parent.A_8be2vx$) : siblings.get_za3lpa$(0);
    parent.A_8be2vx$ = this.apportion_0(v, w, ancestor);
  };
  TreeLayout.prototype.secondWalk_0 = function (v) {
    v.x = v.z_8be2vx$ + ensureNotNull(v.parent).m_8be2vx$;
    v.m_8be2vx$ = v.m_8be2vx$ + ensureNotNull(v.parent).m_8be2vx$;
  };
  TreeLayout.prototype.sizeNode_0 = function (node) {
    node.x = node.x * this.dx_0;
    node.y = node.depth * this.dy_0;
  };
  TreeLayout.prototype.apportion_0 = function (v, w, ancestor) {
    var ancestorNew = ancestor;
    if (w != null) {
      var vip = v;
      var vop = v;
      var vim = w;
      var vom = ensureNotNull(ensureNotNull(vip).parent).children.get_za3lpa$(0);
      var sip = vip.m_8be2vx$;
      var sop = ensureNotNull(vop).m_8be2vx$;
      var sim = ensureNotNull(vim).m_8be2vx$;
      var som = ensureNotNull(vom).m_8be2vx$;
      var shift;
      vim = this.nextRight_0(vim);
      vip = this.nextLeft_0(vip);
      while (vim != null && vip != null) {
        vom = this.nextLeft_0(ensureNotNull(vom));
        vop = this.nextRight_0(ensureNotNull(vop));
        ensureNotNull(vop).ancestor_8be2vx$ = v;
        var tmp$ = vim.z_8be2vx$ + sim - vip.z_8be2vx$ - sip;
        var nodeA = vim;
        var nodeB = vip;
        shift = tmp$ + (equals(nodeA.parent, nodeB.parent) ? 1 : 2);
        if (shift > 0) {
          this.moveSubtree_0(this.nextAncestor_0(vim, v, ancestorNew), v, shift);
          sip += shift;
          sop += shift;
        }
        sim += vim.m_8be2vx$;
        sip += vip.m_8be2vx$;
        if (vom != null)
          som += vom.m_8be2vx$;
        if (vop != null)
          sop += vop.m_8be2vx$;
        vim = this.nextRight_0(vim);
        vip = this.nextLeft_0(vip);
      }
      if (vim != null && this.nextRight_0(ensureNotNull(vop)) == null) {
        vop.t_8be2vx$ = vim;
        vop.m_8be2vx$ = vop.m_8be2vx$ + (sim - sop);
      }
      if (vip != null && this.nextLeft_0(ensureNotNull(vom)) == null) {
        vom.t_8be2vx$ = vip;
        vom.m_8be2vx$ = vom.m_8be2vx$ + (sip - som);
        ancestorNew = v;
      }
    }
    return ancestorNew;
  };
  TreeLayout.prototype.nextAncestor_0 = function (vim, v, ancestor) {
    var tmp$;
    return equals((tmp$ = vim.ancestor_8be2vx$) != null ? tmp$.parent : null, v.parent) ? ensureNotNull(vim.ancestor_8be2vx$) : ancestor;
  };
  TreeLayout.prototype.moveSubtree_0 = function (wm, wp, shift) {
    var change = shift / (wp.index_8be2vx$ - wm.index_8be2vx$ | 0);
    wp.c_8be2vx$ = wp.c_8be2vx$ - change;
    wp.s_8be2vx$ = wp.s_8be2vx$ + shift;
    wm.c_8be2vx$ = wm.c_8be2vx$ + change;
    wp.z_8be2vx$ = wp.z_8be2vx$ + shift;
    wp.m_8be2vx$ = wp.m_8be2vx$ + shift;
  };
  TreeLayout.prototype.nextLeft_0 = function (v) {
    return !v.children.isEmpty() ? v.children.get_za3lpa$(0) : v.t_8be2vx$;
  };
  TreeLayout.prototype.nextRight_0 = function (v) {
    return !v.children.isEmpty() ? v.children.get_za3lpa$(get_lastIndex(v.children)) : v.t_8be2vx$;
  };
  TreeLayout.prototype.executeShifts_0 = function (v) {
    var shift = 0.0;
    var change = 0.0;
    var children = v.children;
    var i = children.size;
    while ((i = i - 1 | 0, i) >= 0) {
      var w = children.get_za3lpa$(i);
      w.z_8be2vx$ = w.z_8be2vx$ + shift;
      w.m_8be2vx$ = w.m_8be2vx$ + shift;
      change += w.c_8be2vx$;
      shift += w.s_8be2vx$ + change;
    }
  };
  var checkIndexOverflow = Kotlin.kotlin.collections.checkIndexOverflow_za3lpa$;
  TreeLayout.prototype.makeTree_0 = function (root) {
    var rootTree = new TreeNode(root.data, root.depth, root.height, root.value);
    rootTree.ancestor_8be2vx$ = rootTree;
    var nodes = mutableListOf([root]);
    var nodesT = mutableListOf([rootTree]);
    while (!nodes.isEmpty()) {
      var node = nodes.removeAt_za3lpa$(get_lastIndex(nodes));
      var nodeT = nodesT.removeAt_za3lpa$(get_lastIndex(nodesT));
      var tmp$, tmp$_0;
      var index = 0;
      tmp$ = node.children.iterator();
      while (tmp$.hasNext()) {
        var item = tmp$.next();
        var index_0 = checkIndexOverflow((tmp$_0 = index, index = tmp$_0 + 1 | 0, tmp$_0));
        var c = new TreeNode(item.data, item.depth, item.height, item.value, index_0);
        c.ancestor_8be2vx$ = c;
        c.parent = nodeT;
        nodeT.children.add_11rb$(c);
        nodes.add_11rb$(item);
        nodesT.add_11rb$(c);
      }
    }
    var treeRoot = new TreeNode(null, 0, 0, null, 0);
    treeRoot.ancestor_8be2vx$ = treeRoot;
    treeRoot.children.add_11rb$(rootTree);
    rootTree.parent = treeRoot;
    return rootTree;
  };
  TreeLayout.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'TreeLayout',
    interfaces: []
  };
  function Row(value, dice, children) {
    this.value_4palb1$_0 = value;
    this.dice = dice;
    this.children_xu786f$_0 = children;
  }
  Object.defineProperty(Row.prototype, 'value', {
    get: function () {
      return this.value_4palb1$_0;
    },
    set: function (value) {
      this.value_4palb1$_0 = value;
    }
  });
  Object.defineProperty(Row.prototype, 'children', {
    get: function () {
      return this.children_xu786f$_0;
    }
  });
  Row.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Row',
    interfaces: [ParentValued]
  };
  Row.prototype.component1 = function () {
    return this.value;
  };
  Row.prototype.component2 = function () {
    return this.dice;
  };
  Row.prototype.component3 = function () {
    return this.children;
  };
  Row.prototype.copy_s60o66$ = function (value, dice, children) {
    return new Row(value === void 0 ? this.value : value, dice === void 0 ? this.dice : dice, children === void 0 ? this.children : children);
  };
  Row.prototype.toString = function () {
    return 'Row(value=' + Kotlin.toString(this.value) + (', dice=' + Kotlin.toString(this.dice)) + (', children=' + Kotlin.toString(this.children)) + ')';
  };
  Row.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.value) | 0;
    result = result * 31 + Kotlin.hashCode(this.dice) | 0;
    result = result * 31 + Kotlin.hashCode(this.children) | 0;
    return result;
  };
  Row.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.value, other.value) && Kotlin.equals(this.dice, other.dice) && Kotlin.equals(this.children, other.children)))));
  };
  function TreemapNode(data, depth, height, value, children, parent, x0, y0, x1, y1) {
    if (children === void 0) {
      children = ArrayList_init();
    }
    if (parent === void 0)
      parent = null;
    if (x0 === void 0)
      x0 = 0.0;
    if (y0 === void 0)
      y0 = 0.0;
    if (x1 === void 0)
      x1 = 0.0;
    if (y1 === void 0)
      y1 = 0.0;
    this.data = data;
    this.depth = depth;
    this.height = height;
    this.value_an2561$_0 = value;
    this.children_tn4jcd$_0 = children;
    this.parent_8cgy76$_0 = parent;
    this.x0 = x0;
    this.y0 = y0;
    this.x1 = x1;
    this.y1 = y1;
  }
  Object.defineProperty(TreemapNode.prototype, 'value', {
    get: function () {
      return this.value_an2561$_0;
    },
    set: function (value) {
      this.value_an2561$_0 = value;
    }
  });
  Object.defineProperty(TreemapNode.prototype, 'children', {
    get: function () {
      return this.children_tn4jcd$_0;
    }
  });
  Object.defineProperty(TreemapNode.prototype, 'parent', {
    get: function () {
      return this.parent_8cgy76$_0;
    },
    set: function (parent) {
      this.parent_8cgy76$_0 = parent;
    }
  });
  TreemapNode.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'TreemapNode',
    interfaces: [Children, ParentValued]
  };
  function roundNode(node) {
    node.x0 = roundToInt(node.x0);
    node.y0 = roundToInt(node.y0);
    node.x1 = roundToInt(node.x1);
    node.y1 = roundToInt(node.y1);
  }
  function makeTreemap(root) {
    var rootTreemap = new TreemapNode(root.data, root.depth, root.height, root.value);
    var nodes = mutableListOf([root]);
    var nodesTM = mutableListOf([rootTreemap]);
    while (!nodes.isEmpty()) {
      var node = nodes.removeAt_za3lpa$(get_lastIndex(nodes));
      var nodeTM = nodesTM.removeAt_za3lpa$(get_lastIndex(nodesTM));
      var tmp$;
      tmp$ = node.children.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        var c = new TreemapNode(element.data, element.depth, element.height, element.value);
        c.parent = nodeTM;
        nodeTM.children.add_11rb$(c);
        nodes.add_11rb$(element);
        nodesTM.add_11rb$(c);
      }
    }
    return rootTreemap;
  }
  function TreemapLayout() {
    this.constantZero_0 = TreemapLayout$constantZero$lambda;
    this.tilingMethod = TreemapLayout$tilingMethod$lambda;
    this.round = false;
    this.width = 1.0;
    this.height = 1.0;
    this.paddingStack_0 = mutableListOf([0.0]);
    this.paddingInner = this.constantZero_0;
    this.paddingTop = this.constantZero_0;
    this.paddingRight = this.constantZero_0;
    this.paddingBottom = this.constantZero_0;
    this.paddingLeft = this.constantZero_0;
    this.paddingOuter_d272ly$_0 = this.constantZero_0;
  }
  Object.defineProperty(TreemapLayout.prototype, 'paddingOuter', {
    get: function () {
      return this.paddingOuter_d272ly$_0;
    },
    set: function (value) {
      this.paddingTop = value;
      this.paddingRight = value;
      this.paddingBottom = value;
      this.paddingLeft = value;
    }
  });
  var ArrayList_init_0 = Kotlin.kotlin.collections.ArrayList_init_ww73n8$;
  TreemapLayout.prototype.treemap_zhympb$ = function (root) {
    var rootTreemap = makeTreemap(root);
    var size = root.height + 1 | 0;
    var list = ArrayList_init_0(size);
    for (var index = 0; index < size; index++) {
      list.add_11rb$(0.0);
    }
    this.paddingStack_0 = list;
    rootTreemap.x0 = 0.0;
    rootTreemap.y0 = 0.0;
    rootTreemap.x1 = this.width;
    rootTreemap.y1 = this.height;
    var callback = getCallableRef('positionNode', function ($receiver, node) {
      return $receiver.positionNode_0(node), Unit;
    }.bind(null, this));
    var nodes = mutableListOf([rootTreemap]);
    while (!nodes.isEmpty()) {
      var node = nodes.removeAt_za3lpa$(get_lastIndex(nodes));
      callback(node);
      var children = node.children;
      if (!children.isEmpty()) {
        var tmp$;
        tmp$ = downTo(get_lastIndex(children), 0).iterator();
        while (tmp$.hasNext()) {
          var element = tmp$.next();
          var tmp$_0;
          nodes.add_11rb$(Kotlin.isType(tmp$_0 = children.get_za3lpa$(element), TreemapNode) ? tmp$_0 : throwCCE());
        }
      }
    }
    this.paddingStack_0 = mutableListOf([0.0]);
    if (this.round) {
      var nodes_0 = mutableListOf([rootTreemap]);
      while (!nodes_0.isEmpty()) {
        var node_0 = nodes_0.removeAt_za3lpa$(get_lastIndex(nodes_0));
        roundNode(node_0);
        var children_0 = node_0.children;
        if (!children_0.isEmpty()) {
          var tmp$_1;
          tmp$_1 = downTo(get_lastIndex(children_0), 0).iterator();
          while (tmp$_1.hasNext()) {
            var element_0 = tmp$_1.next();
            var tmp$_2;
            nodes_0.add_11rb$(Kotlin.isType(tmp$_2 = children_0.get_za3lpa$(element_0), TreemapNode) ? tmp$_2 : throwCCE());
          }
        }
      }
    }
    return rootTreemap;
  };
  TreemapLayout.prototype.positionNode_0 = function (node) {
    var p = this.paddingStack_0.get_za3lpa$(node.depth);
    var x0 = node.x0 + p;
    var y0 = node.y0 + p;
    var x1 = node.x1 - p;
    var y1 = node.y1 - p;
    if (x1 < x0) {
      var mid = (x0 + x1) / 2;
      x0 = mid;
      x1 = mid;
    }
    if (y1 < y0) {
      var mid_0 = (y0 + y1) / 2;
      y0 = mid_0;
      y1 = mid_0;
    }
    node.x0 = x0;
    node.y0 = y0;
    node.x1 = x1;
    node.y1 = y1;
    if (!node.children.isEmpty()) {
      this.paddingStack_0.set_wxm5ur$(node.depth + 1 | 0, this.paddingInner(node) / 2);
      p = this.paddingInner(node) / 2;
      x0 += this.paddingLeft(node) - p;
      y0 += this.paddingTop(node) - p;
      x1 -= this.paddingRight(node) - p;
      y1 -= this.paddingBottom(node) - p;
      if (x1 < x0) {
        var mid_1 = (x0 + x1) / 2;
        x0 = mid_1;
        x1 = mid_1;
      }
      if (y1 < y0) {
        var mid_2 = (y0 + y1) / 2;
        y0 = mid_2;
        y1 = mid_2;
      }
      this.tilingMethod(node, x0, y0, x1, y1);
    }
  };
  function TreemapLayout$constantZero$lambda(it) {
    return 0.0;
  }
  function TreemapLayout$tilingMethod$lambda(parent, x0, y0, x1, y1) {
    return treemapSquarify(parent, x0, y0, x1, y1);
  }
  TreemapLayout.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'TreemapLayout',
    interfaces: []
  };
  function Circle(x, y, r) {
    if (x === void 0)
      x = 0.0;
    if (y === void 0)
      y = 0.0;
    if (r === void 0)
      r = 0.0;
    this.x_l70muj$_0 = x;
    this.y_l70mve$_0 = y;
    this.r_l70mpd$_0 = r;
  }
  Object.defineProperty(Circle.prototype, 'x', {
    get: function () {
      return this.x_l70muj$_0;
    },
    set: function (x) {
      this.x_l70muj$_0 = x;
    }
  });
  Object.defineProperty(Circle.prototype, 'y', {
    get: function () {
      return this.y_l70mve$_0;
    },
    set: function (y) {
      this.y_l70mve$_0 = y;
    }
  });
  Object.defineProperty(Circle.prototype, 'r', {
    get: function () {
      return this.r_l70mpd$_0;
    },
    set: function (r) {
      this.r_l70mpd$_0 = r;
    }
  });
  Circle.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Circle',
    interfaces: [CircleValues]
  };
  Circle.prototype.component1 = function () {
    return this.x;
  };
  Circle.prototype.component2 = function () {
    return this.y;
  };
  Circle.prototype.component3 = function () {
    return this.r;
  };
  Circle.prototype.copy_yvo9jy$ = function (x, y, r) {
    return new Circle(x === void 0 ? this.x : x, y === void 0 ? this.y : y, r === void 0 ? this.r : r);
  };
  Circle.prototype.toString = function () {
    return 'Circle(x=' + Kotlin.toString(this.x) + (', y=' + Kotlin.toString(this.y)) + (', r=' + Kotlin.toString(this.r)) + ')';
  };
  Circle.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.x) | 0;
    result = result * 31 + Kotlin.hashCode(this.y) | 0;
    result = result * 31 + Kotlin.hashCode(this.r) | 0;
    return result;
  };
  Circle.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.x, other.x) && Kotlin.equals(this.y, other.y) && Kotlin.equals(this.r, other.r)))));
  };
  var emptyList = Kotlin.kotlin.collections.emptyList_287e2$;
  function enclose(circles) {
    var i = 0;
    var shuffledCircles = shuffled(circles);
    var n = shuffledCircles.size;
    var B = emptyList();
    var e = null;
    while (i < n) {
      var p = shuffledCircles.get_za3lpa$(i);
      if (e != null && enclosesWeak(e, p)) {
        i = i + 1 | 0;
      }
       else {
        B = extendBasis(B, p);
        e = encloseBasis(B);
        i = 0;
      }
    }
    return e;
  }
  function enclosesWeak(a, b) {
    var dr = a.r - b.r + epsilon;
    var dx = b.x - a.x;
    var dy = b.y - a.y;
    return dr > 0 && dr * dr > dx * dx + dy * dy;
  }
  function enclosesWeakAll(a, B) {
    var tmp$;
    tmp$ = B.size;
    for (var i = 0; i < tmp$; i++) {
      if (!enclosesWeak(a, B.get_za3lpa$(i))) {
        return false;
      }
    }
    return true;
  }
  function enclosesNot(a, b) {
    var dr = a.r - b.r;
    var dx = b.x - a.x;
    var dy = b.y - a.y;
    return dr < 0 || dr * dr < dx * dx + dy * dy;
  }
  function encloseBasis(B) {
    switch (B.size) {
      case 1:
        return encloseBasis1(B.get_za3lpa$(0));
      case 2:
        return encloseBasis2(B.get_za3lpa$(0), B.get_za3lpa$(1));
      default:return encloseBasis3(B.get_za3lpa$(0), B.get_za3lpa$(1), B.get_za3lpa$(2));
    }
  }
  function encloseBasis1(a) {
    return new Circle(a.x, a.y, a.r);
  }
  function encloseBasis2(a, b) {
    var x1 = a.x;
    var y1 = a.y;
    var r1 = a.r;
    var x2 = b.x;
    var y2 = b.y;
    var r2 = b.r;
    var x21 = x2 - x1;
    var y21 = y2 - y1;
    var r21 = r2 - r1;
    var x = x21 * x21 + y21 * y21;
    var l = Math_0.sqrt(x);
    return new Circle((x1 + x2 + x21 / l * r21) / 2, (y1 + y2 + y21 / l * r21) / 2, (l + r1 + r2) / 2);
  }
  function encloseBasis3(a, b, c) {
    var x1 = a.x;
    var y1 = a.y;
    var r1 = a.r;
    var x2 = b.x;
    var y2 = b.y;
    var r2 = b.r;
    var x3 = c.x;
    var y3 = c.y;
    var r3 = c.r;
    var a2 = x1 - x2;
    var a3 = x1 - x3;
    var b2 = y1 - y2;
    var b3 = y1 - y3;
    var c2 = r2 - r1;
    var c3 = r3 - r1;
    var d1 = x1 * x1 + y1 * y1 - r1 * r1;
    var d2 = d1 - x2 * x2 - y2 * y2 + r2 * r2;
    var d3 = d1 - x3 * x3 - y3 * y3 + r3 * r3;
    var ab = a3 * b2 - a2 * b3;
    var xa = (b2 * d3 - b3 * d2) / (ab * 2) - x1;
    var xb = (b3 * c2 - b2 * c3) / ab;
    var ya = (a3 * d2 - a2 * d3) / (ab * 2) - y1;
    var yb = (a2 * c3 - a3 * c2) / ab;
    var A = xb * xb + yb * yb - 1;
    var B = 2 * (r1 + xa * xb + ya * yb);
    var C = xa * xa + ya * ya - r1 * r1;
    var tmp$;
    if (A !== 0.0) {
      var x = B * B - 4 * A * C;
      tmp$ = (B + Math_0.sqrt(x)) / (2 * A);
    }
     else
      tmp$ = C / B;
    var r = -tmp$;
    return new Circle(x1 + xa + xb * r, y1 + ya + yb * r, r);
  }
  function extendBasis(B, p) {
    var tmp$, tmp$_0, tmp$_1;
    if (enclosesWeakAll(p, B))
      return listOf(p);
    tmp$ = B.size;
    for (var i = 0; i < tmp$; i++) {
      if (enclosesNot(p, B.get_za3lpa$(i)) && enclosesWeakAll(encloseBasis2(B.get_za3lpa$(i), p), B)) {
        return listOf_0([B.get_za3lpa$(i), p]);
      }
    }
    tmp$_0 = B.size - 1 | 0;
    for (var i_0 = 0; i_0 < tmp$_0; i_0++) {
      tmp$_1 = B.size;
      for (var j = i_0 + 1 | 0; j < tmp$_1; j++) {
        if (enclosesNot(encloseBasis2(B.get_za3lpa$(i_0), B.get_za3lpa$(j)), p) && enclosesNot(encloseBasis2(B.get_za3lpa$(i_0), p), B.get_za3lpa$(j)) && enclosesNot(encloseBasis2(B.get_za3lpa$(j), p), B.get_za3lpa$(i_0)) && enclosesWeakAll(encloseBasis3(B.get_za3lpa$(i_0), B.get_za3lpa$(j), p), B)) {
          return listOf_0([B.get_za3lpa$(i_0), B.get_za3lpa$(j), p]);
        }
      }
    }
    throw RuntimeException_init('Unable to compute enclosing circle for PackLayout.');
  }
  var epsilon;
  function packEnclose(circles) {
    if (circles.isEmpty())
      return 0.0;
    var n = circles.size;
    var a = {v: first(circles)};
    a.v.x = 0.0;
    a.v.y = 0.0;
    if (n < 2)
      return a.v.r;
    var b = circles.get_za3lpa$(1);
    a.v.x = -b.r;
    b.x = a.v.r;
    b.y = 0.0;
    if (n < 3)
      return a.v.r + b.r;
    var c = circles.get_za3lpa$(2);
    place(b, a.v, c);
    a.v = packNode(a.v);
    b = packNode(b);
    c = packNode(c);
    a.v.next = b;
    c.previous = b;
    b.next = c;
    a.v.previous = c;
    c.next = a.v;
    b.previous = a.v;
    var i = 3;
    pack: while (i < n) {
      c = circles.get_za3lpa$(i);
      i = i + 1 | 0;
      place(a.v, b, c);
      var j = ensureNotNull(b.next);
      var k = ensureNotNull(a.v.previous);
      var sj = b.r;
      var sk = a.v.r;
      do {
        if (sj <= sk) {
          if (intersects(j, c)) {
            b = j;
            a.v.next = b;
            b.previous = a.v;
            i = i - 1 | 0;
            continue pack;
          }
          sj += j.r;
          j = ensureNotNull(j.next);
        }
         else {
          if (intersects(k, c)) {
            a.v = k;
            a.v.next = b;
            b.previous = a.v;
            i = i - 1 | 0;
            continue pack;
          }
          sk += k.r;
          k = ensureNotNull(k.previous);
        }
      }
       while (j !== k.next);
      c.previous = a.v;
      c.next = b;
      b = c;
      b.previous = c;
      a.v.next = c;
      var aa = score(a.v);
      c = ensureNotNull(c.next);
      while (c !== b) {
        var ca = score(c);
        if (ca < aa) {
          a.v = c;
          aa = ca;
        }
        c = ensureNotNull(c.next);
      }
      b = ensureNotNull(a.v.next);
    }
    var chain = mutableListOf([b]);
    c = b;
    c = ensureNotNull(c.next);
    while (!(c != null ? c.equals(b) : null)) {
      chain.add_11rb$(c);
      c = ensureNotNull(c.next);
    }
    var circle = ensureNotNull(enclose(chain));
    var tmp$;
    tmp$ = until(0, n).iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      a.v = circles.get_za3lpa$(element);
      a.v.x = a.v.x - circle.x;
      a.v.y = a.v.y - circle.y;
    }
    return circle.r;
  }
  function score(node) {
    var a = node;
    var b = ensureNotNull(node.next);
    var ab = a.r + b.r;
    var dx = (a.x * b.r + b.x * a.r) / ab;
    var dy = (a.y * b.r + b.y * a.r) / ab;
    return dx * dx + dy * dy;
  }
  function intersects(a, b) {
    var dx = b.x - a.x;
    var dy = b.y - a.y;
    var dr = a.r + b.r;
    return dr * dr - epsilon > dx * dx + dy * dy;
  }
  function place(a, b, c) {
    var ax = a.x;
    var ay = a.y;
    var da = b.r + c.r;
    var db = a.r + c.r;
    var dx = b.x - ax;
    var dy = b.y - ay;
    var dc = dx * dx + dy * dy;
    if (dc !== 0.0) {
      db *= db;
      da *= da;
      var x = 0.5 + (db - da) / (2 * dc);
      var temp1 = 2.0 * da * (db + dc);
      db -= dc;
      var b_0 = temp1 - db * db - da * da;
      var x_0 = Math_0.max(0.0, b_0);
      var y = Math_0.sqrt(x_0) / (2 * dc);
      c.x = ax + x * dx + y * dy;
      c.y = ay + x * dy - y * dx;
    }
     else {
      c.x = ax + db;
      c.y = ay;
    }
  }
  function treemapBinary(parent, x0, y0, x1, y1) {
    (new TreemapBinary()).binary_rsa0s4$(parent, x0, y0, x1, y1);
  }
  function TreemapBinary() {
    this.nodes = ArrayList_init();
    this.sums = ArrayList_init();
  }
  TreemapBinary.prototype.binary_rsa0s4$ = function (parent, x0, y0, x1, y1) {
    this.nodes = toMutableList(parent.children);
    var size = this.nodes.size;
    var size_0 = size + 1 | 0;
    var list = ArrayList_init_0(size_0);
    for (var index = 0; index < size_0; index++) {
      list.add_11rb$(0.0);
    }
    this.sums = list;
    var sum = 0.0;
    for (var i = 0; i < size; i++) {
      sum += ensureNotNull(this.nodes.get_za3lpa$(i).value);
      this.sums.set_wxm5ur$(i + 1 | 0, sum);
    }
    this.partition_0(0, size, ensureNotNull(parent.value), x0, y0, x1, y1);
  };
  TreemapBinary.prototype.partition_0 = function (i, j, value, x0, y0, x1, y1) {
    var tmp$;
    if (i >= (j - 1 | 0)) {
      var node = Kotlin.isType(tmp$ = this.nodes.get_za3lpa$(i), TreemapNode) ? tmp$ : throwCCE();
      node.x0 = x0;
      node.y0 = y0;
      node.x1 = x1;
      node.y1 = y1;
      return;
    }
    var valueOffset = this.sums.get_za3lpa$(i);
    var valueTarget = value / 2 + valueOffset;
    var k = i + 1 | 0;
    var hi = j - 1 | 0;
    while (k < hi) {
      var mid = k + hi >>> 1;
      if (this.sums.get_za3lpa$(mid) < valueTarget)
        k = mid + 1 | 0;
      else
        hi = mid;
    }
    if (valueTarget - this.sums.get_za3lpa$(k - 1 | 0) < this.sums.get_za3lpa$(k) - valueTarget && (i + 1 | 0) < k)
      k = k - 1 | 0;
    var valueLeft = this.sums.get_za3lpa$(k) - valueOffset;
    var valueRight = value - valueLeft;
    if (x1 - x0 > y1 - y0) {
      var xk = (x0 * valueRight + x1 * valueLeft) / value;
      this.partition_0(i, k, valueLeft, x0, y0, xk, y1);
      this.partition_0(k, j, valueRight, xk, y0, x1, y1);
    }
     else {
      var yk = (y0 * valueRight + y1 * valueLeft) / value;
      this.partition_0(i, k, valueLeft, x0, y0, x1, yk);
      this.partition_0(k, j, valueRight, x0, yk, x1, y1);
    }
  };
  TreemapBinary.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'TreemapBinary',
    interfaces: []
  };
  function treemapResquarify(parent, x0, y0, x1, y1) {
  }
  function treemapSlice(parent, x0, y0, x1, y1) {
    var tmp$;
    var newY = y0;
    var nodes = parent.children;
    var i = 0;
    var n = nodes.size;
    var k = parent.value != null && parent.value !== 0.0 ? (y1 - newY) / ensureNotNull(parent.value) : 0.0;
    while (i < n) {
      var node = Kotlin.isType(tmp$ = nodes.get_za3lpa$(i), TreemapNode) ? tmp$ : throwCCE();
      node.x0 = x0;
      node.x1 = x1;
      node.y0 = newY;
      newY += k * ensureNotNull(node.value);
      node.y1 = newY;
      i = i + 1 | 0;
    }
  }
  function treemapDice(parent, x0, y0, x1, y1) {
    var tmp$;
    var newX = x0;
    var nodes = parent.children;
    var i = 0;
    var n = nodes.size;
    var k = parent.value != null && parent.value !== 0.0 ? (x1 - newX) / ensureNotNull(parent.value) : 0.0;
    while (i < n) {
      var node = Kotlin.isType(tmp$ = nodes.get_za3lpa$(i), TreemapNode) ? tmp$ : throwCCE();
      node.y0 = y0;
      node.y1 = y1;
      node.x0 = newX;
      newX += k * ensureNotNull(node.value);
      node.x1 = newX;
      i = i + 1 | 0;
    }
  }
  function treemapSliceDice(parent, x0, y0, x1, y1) {
    var tmp$;
    if ((Kotlin.isType(tmp$ = parent, TreemapNode) ? tmp$ : throwCCE()).depth % 2 === 1)
      treemapSlice(parent, x0, y0, x1, y1);
    else
      treemapDice(parent, x0, y0, x1, y1);
  }
  var phi;
  function treemapSquarify(parent, x0, y0, x1, y1) {
    var tmp$;
    return squarifyRatio(phi, Kotlin.isType(tmp$ = parent, TreemapNode) ? tmp$ : throwCCE(), x0, y0, x1, y1);
  }
  function squarifyRatio(ratio, parent, x0, y0, x1, y1) {
    var tmp$;
    var rows = ArrayList_init();
    var nodes = parent.children;
    var newx = x0;
    var newY = y0;
    var i0 = 0;
    var i1 = 0;
    var size = nodes.size;
    var dx;
    var dy;
    var value = ensureNotNull(parent.value);
    var sumValue;
    while (i0 < size) {
      dx = x1 - newx;
      dy = y1 - newY;
      do {
        sumValue = ensureNotNull(nodes.get_za3lpa$((tmp$ = i1, i1 = tmp$ + 1 | 0, tmp$)).value);
      }
       while (sumValue === 0.0 && i1 < size);
      var minValue = sumValue;
      var maxValue = sumValue;
      var a = dy / dx;
      var b = dx / dy;
      var alpha = Math_0.max(a, b) / (value * ratio);
      var beta = sumValue * sumValue * alpha;
      var a_0 = maxValue / beta;
      var b_0 = beta / minValue;
      var minRatio = Math_0.max(a_0, b_0);
      while (i1 < size) {
        var nodeValue = ensureNotNull(nodes.get_za3lpa$(i1).value);
        sumValue += nodeValue;
        if (nodeValue < minValue)
          minValue = nodeValue;
        if (nodeValue > maxValue)
          maxValue = nodeValue;
        beta = sumValue * sumValue * alpha;
        var a_1 = maxValue / beta;
        var b_1 = beta / minValue;
        var newRatio = Math_0.max(a_1, b_1);
        if (newRatio > minRatio) {
          sumValue -= nodeValue;
          break;
        }
        minRatio = newRatio;
        i1 = i1 + 1 | 0;
      }
      var row = new Row(sumValue, dx < dy, slice(nodes, until(i0, i1)));
      rows.add_11rb$(row);
      if (row.dice) {
        if (value !== 0.0) {
          var temp = newY + dy * sumValue / value;
          treemapDice(row, newx, newY, x1, temp);
          newY = temp;
        }
         else
          treemapDice(row, newx, newY, x1, y1);
      }
       else {
        if (value !== 0.0) {
          var temp_0 = newx + dx * sumValue / value;
          treemapSlice(row, newx, newY, temp_0, y1);
          newx = temp_0;
        }
         else
          treemapSlice(row, newx, newY, x1, y1);
      }
      value -= sumValue;
      i0 = i1;
    }
    return toList(rows);
  }
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$hierarchy = package$data2viz.hierarchy || (package$data2viz.hierarchy = {});
  package$hierarchy.ClusterNode = ClusterNode;
  $$importsForInline$$['d2v-hierarchy-js'] = _;
  package$hierarchy.ClusterLayout = ClusterLayout;
  package$hierarchy.Valued = Valued;
  package$hierarchy.Children = Children;
  package$hierarchy.ParentValued = ParentValued;
  package$hierarchy.Node = Node;
  package$hierarchy.Link = Link;
  package$hierarchy.hierarchy_ehlftx$ = hierarchy;
  package$hierarchy.count_j7dovb$ = count;
  package$hierarchy.sum_xz8b1e$ = sum;
  package$hierarchy.ancestors_j7dovb$ = ancestors;
  package$hierarchy.descendants_j7dovb$ = descendants;
  package$hierarchy.leaves_j7dovb$ = leaves;
  package$hierarchy.links_j7dovb$ = links;
  package$hierarchy.CircleValues = CircleValues;
  package$hierarchy.PackNode = PackNode;
  package$hierarchy.packNode_izpfk3$ = packNode;
  package$hierarchy.PackLayout = PackLayout;
  package$hierarchy.PartitionLayout = PartitionLayout;
  package$hierarchy.TreeNode = TreeNode;
  package$hierarchy.TreeLayout = TreeLayout;
  package$hierarchy.Row = Row;
  package$hierarchy.TreemapNode = TreemapNode;
  package$hierarchy.roundNode_mo31n7$ = roundNode;
  package$hierarchy.makeTreemap_kn2w81$ = makeTreemap;
  package$hierarchy.TreemapLayout = TreemapLayout;
  var package$pack = package$hierarchy.pack || (package$hierarchy.pack = {});
  package$pack.enclose_4lvts7$ = enclose;
  Object.defineProperty(package$pack, 'epsilon', {
    get: function () {
      return epsilon;
    }
  });
  package$pack.packEnclose_rxw7tq$ = packEnclose;
  var package$treemap = package$hierarchy.treemap || (package$hierarchy.treemap = {});
  package$treemap.treemapBinary_28pstf$ = treemapBinary;
  package$treemap.TreemapBinary = TreemapBinary;
  package$treemap.treemapResquarify_l7o3vc$ = treemapResquarify;
  package$treemap.treemapSlice_28pstf$ = treemapSlice;
  package$treemap.treemapDice_28pstf$ = treemapDice;
  package$treemap.treemapSliceDice_28pstf$ = treemapSliceDice;
  Object.defineProperty(package$treemap, 'phi', {
    get: function () {
      return phi;
    }
  });
  package$treemap.treemapSquarify_28pstf$ = treemapSquarify;
  epsilon = 1.0E-6;
  phi = (1 + Math_0.sqrt(5.0)) / 2;
  Kotlin.defineModule('d2v-hierarchy-js', _);
  return _;
}));

//# sourceMappingURL=d2v-hierarchy-js.js.map
