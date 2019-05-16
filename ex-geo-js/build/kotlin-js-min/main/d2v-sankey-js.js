(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin', 'd2v-shape-js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'), require('d2v-shape-js'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'd2v-sankey-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'd2v-sankey-js'.");
    }
    if (typeof this['d2v-shape-js'] === 'undefined') {
      throw new Error("Error loading module 'd2v-sankey-js'. Its dependency 'd2v-shape-js' was not found. Please, check whether 'd2v-shape-js' is loaded prior to 'd2v-sankey-js'.");
    }
    root['d2v-sankey-js'] = factory(typeof this['d2v-sankey-js'] === 'undefined' ? {} : this['d2v-sankey-js'], kotlin, this['d2v-shape-js']);
  }
}(this, function (_, Kotlin, $module$d2v_shape_js) {
  'use strict';
  var Enum = Kotlin.kotlin.Enum;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var throwISE = Kotlin.throwISE;
  var Unit = Kotlin.kotlin.Unit;
  var linkBuilderH = $module$d2v_shape_js.io.data2viz.shape.link.linkBuilderH_cz84wk$;
  var linkBuilderV = $module$d2v_shape_js.io.data2viz.shape.link.linkBuilderV_cz84wk$;
  var compareBy = Kotlin.kotlin.comparisons.compareBy_bvgy4j$;
  var sortWith = Kotlin.kotlin.collections.sortWith_nqfjgj$;
  var toList = Kotlin.kotlin.collections.toList_7wnvza$;
  var getCallableRef = Kotlin.getCallableRef;
  var reversed = Kotlin.kotlin.collections.reversed_7wnvza$;
  var ensureNotNull = Kotlin.ensureNotNull;
  var last = Kotlin.kotlin.collections.last_2p1efm$;
  var downTo = Kotlin.kotlin.ranges.downTo_dqglrj$;
  var IntRange = Kotlin.kotlin.ranges.IntRange;
  var min = Kotlin.kotlin.collections.min_l63kqw$;
  SankeyAlignment.prototype = Object.create(Enum.prototype);
  SankeyAlignment.prototype.constructor = SankeyAlignment;
  function SankeyAlignment(name, ordinal) {
    Enum.call(this);
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function SankeyAlignment_initFields() {
    SankeyAlignment_initFields = function () {
    };
    SankeyAlignment$CENTER_instance = new SankeyAlignment('CENTER', 0);
    SankeyAlignment$JUSTIFY_instance = new SankeyAlignment('JUSTIFY', 1);
    SankeyAlignment$RIGHT_instance = new SankeyAlignment('RIGHT', 2);
    SankeyAlignment$LEFT_instance = new SankeyAlignment('LEFT', 3);
  }
  var SankeyAlignment$CENTER_instance;
  function SankeyAlignment$CENTER_getInstance() {
    SankeyAlignment_initFields();
    return SankeyAlignment$CENTER_instance;
  }
  var SankeyAlignment$JUSTIFY_instance;
  function SankeyAlignment$JUSTIFY_getInstance() {
    SankeyAlignment_initFields();
    return SankeyAlignment$JUSTIFY_instance;
  }
  var SankeyAlignment$RIGHT_instance;
  function SankeyAlignment$RIGHT_getInstance() {
    SankeyAlignment_initFields();
    return SankeyAlignment$RIGHT_instance;
  }
  var SankeyAlignment$LEFT_instance;
  function SankeyAlignment$LEFT_getInstance() {
    SankeyAlignment_initFields();
    return SankeyAlignment$LEFT_instance;
  }
  SankeyAlignment.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'SankeyAlignment',
    interfaces: [Enum]
  };
  function SankeyAlignment$values() {
    return [SankeyAlignment$CENTER_getInstance(), SankeyAlignment$JUSTIFY_getInstance(), SankeyAlignment$RIGHT_getInstance(), SankeyAlignment$LEFT_getInstance()];
  }
  SankeyAlignment.values = SankeyAlignment$values;
  function SankeyAlignment$valueOf(name) {
    switch (name) {
      case 'CENTER':
        return SankeyAlignment$CENTER_getInstance();
      case 'JUSTIFY':
        return SankeyAlignment$JUSTIFY_getInstance();
      case 'RIGHT':
        return SankeyAlignment$RIGHT_getInstance();
      case 'LEFT':
        return SankeyAlignment$LEFT_getInstance();
      default:throwISE('No enum constant io.data2viz.sankey.SankeyAlignment.' + name);
    }
  }
  SankeyAlignment.valueOf_61zpoe$ = SankeyAlignment$valueOf;
  function sankeyLinkHorizontal$lambda$lambda(it) {
    return it.source.x1;
  }
  function sankeyLinkHorizontal$lambda$lambda_0(it) {
    return it.y0;
  }
  function sankeyLinkHorizontal$lambda$lambda_1(it) {
    return it.target.x0;
  }
  function sankeyLinkHorizontal$lambda$lambda_2(it) {
    return it.y1;
  }
  function sankeyLinkHorizontal$lambda($receiver) {
    $receiver.x0 = sankeyLinkHorizontal$lambda$lambda;
    $receiver.y0 = sankeyLinkHorizontal$lambda$lambda_0;
    $receiver.x1 = sankeyLinkHorizontal$lambda$lambda_1;
    $receiver.y1 = sankeyLinkHorizontal$lambda$lambda_2;
    return Unit;
  }
  var sankeyLinkHorizontal;
  function sankeyLinkVertical$lambda$lambda(it) {
    return it.y0;
  }
  function sankeyLinkVertical$lambda$lambda_0(it) {
    return it.source.x0;
  }
  function sankeyLinkVertical$lambda$lambda_1(it) {
    return it.y1;
  }
  function sankeyLinkVertical$lambda$lambda_2(it) {
    return it.target.x1;
  }
  function sankeyLinkVertical$lambda($receiver) {
    $receiver.x0 = sankeyLinkVertical$lambda$lambda;
    $receiver.y0 = sankeyLinkVertical$lambda$lambda_0;
    $receiver.x1 = sankeyLinkVertical$lambda$lambda_1;
    $receiver.y1 = sankeyLinkVertical$lambda$lambda_2;
    return Unit;
  }
  var sankeyLinkVertical;
  function SankeyLink(source, target, index, value, y0, y1, width) {
    if (y0 === void 0)
      y0 = 0.0;
    if (y1 === void 0)
      y1 = 0.0;
    if (width === void 0)
      width = 0.0;
    this.source = source;
    this.target = target;
    this.index = index;
    this.value = value;
    this.y0 = y0;
    this.y1 = y1;
    this.width = width;
  }
  SankeyLink.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'SankeyLink',
    interfaces: []
  };
  SankeyLink.prototype.component1 = function () {
    return this.source;
  };
  SankeyLink.prototype.component2 = function () {
    return this.target;
  };
  SankeyLink.prototype.component3 = function () {
    return this.index;
  };
  SankeyLink.prototype.component4 = function () {
    return this.value;
  };
  SankeyLink.prototype.component5 = function () {
    return this.y0;
  };
  SankeyLink.prototype.component6 = function () {
    return this.y1;
  };
  SankeyLink.prototype.component7 = function () {
    return this.width;
  };
  SankeyLink.prototype.copy_iuq4ye$ = function (source, target, index, value, y0, y1, width) {
    return new SankeyLink(source === void 0 ? this.source : source, target === void 0 ? this.target : target, index === void 0 ? this.index : index, value === void 0 ? this.value : value, y0 === void 0 ? this.y0 : y0, y1 === void 0 ? this.y1 : y1, width === void 0 ? this.width : width);
  };
  SankeyLink.prototype.toString = function () {
    return 'SankeyLink(source=' + Kotlin.toString(this.source) + (', target=' + Kotlin.toString(this.target)) + (', index=' + Kotlin.toString(this.index)) + (', value=' + Kotlin.toString(this.value)) + (', y0=' + Kotlin.toString(this.y0)) + (', y1=' + Kotlin.toString(this.y1)) + (', width=' + Kotlin.toString(this.width)) + ')';
  };
  SankeyLink.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.source) | 0;
    result = result * 31 + Kotlin.hashCode(this.target) | 0;
    result = result * 31 + Kotlin.hashCode(this.index) | 0;
    result = result * 31 + Kotlin.hashCode(this.value) | 0;
    result = result * 31 + Kotlin.hashCode(this.y0) | 0;
    result = result * 31 + Kotlin.hashCode(this.y1) | 0;
    result = result * 31 + Kotlin.hashCode(this.width) | 0;
    return result;
  };
  SankeyLink.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.source, other.source) && Kotlin.equals(this.target, other.target) && Kotlin.equals(this.index, other.index) && Kotlin.equals(this.value, other.value) && Kotlin.equals(this.y0, other.y0) && Kotlin.equals(this.y1, other.y1) && Kotlin.equals(this.width, other.width)))));
  };
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  function SankeyNode(data, index, sourceLinks, targetLinks, value, depth, height, x0, x1, y0, y1) {
    if (sourceLinks === void 0) {
      sourceLinks = ArrayList_init();
    }
    if (targetLinks === void 0) {
      targetLinks = ArrayList_init();
    }
    if (value === void 0)
      value = 0.0;
    if (depth === void 0)
      depth = 0;
    if (height === void 0)
      height = 0;
    if (x0 === void 0)
      x0 = 0.0;
    if (x1 === void 0)
      x1 = 0.0;
    if (y0 === void 0)
      y0 = 0.0;
    if (y1 === void 0)
      y1 = 0.0;
    this.data = data;
    this.index = index;
    this.sourceLinks = sourceLinks;
    this.targetLinks = targetLinks;
    this.value = value;
    this.depth = depth;
    this.height = height;
    this.x0 = x0;
    this.x1 = x1;
    this.y0 = y0;
    this.y1 = y1;
  }
  SankeyNode.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'SankeyNode',
    interfaces: []
  };
  SankeyNode.prototype.component1 = function () {
    return this.data;
  };
  SankeyNode.prototype.component2 = function () {
    return this.index;
  };
  SankeyNode.prototype.component3 = function () {
    return this.sourceLinks;
  };
  SankeyNode.prototype.component4 = function () {
    return this.targetLinks;
  };
  SankeyNode.prototype.component5 = function () {
    return this.value;
  };
  SankeyNode.prototype.component6 = function () {
    return this.depth;
  };
  SankeyNode.prototype.component7 = function () {
    return this.height;
  };
  SankeyNode.prototype.component8 = function () {
    return this.x0;
  };
  SankeyNode.prototype.component9 = function () {
    return this.x1;
  };
  SankeyNode.prototype.component10 = function () {
    return this.y0;
  };
  SankeyNode.prototype.component11 = function () {
    return this.y1;
  };
  SankeyNode.prototype.copy_a3oyjl$ = function (data, index, sourceLinks, targetLinks, value, depth, height, x0, x1, y0, y1) {
    return new SankeyNode(data === void 0 ? this.data : data, index === void 0 ? this.index : index, sourceLinks === void 0 ? this.sourceLinks : sourceLinks, targetLinks === void 0 ? this.targetLinks : targetLinks, value === void 0 ? this.value : value, depth === void 0 ? this.depth : depth, height === void 0 ? this.height : height, x0 === void 0 ? this.x0 : x0, x1 === void 0 ? this.x1 : x1, y0 === void 0 ? this.y0 : y0, y1 === void 0 ? this.y1 : y1);
  };
  SankeyNode.prototype.toString = function () {
    return 'SankeyNode(data=' + Kotlin.toString(this.data) + (', index=' + Kotlin.toString(this.index)) + (', sourceLinks=' + Kotlin.toString(this.sourceLinks)) + (', targetLinks=' + Kotlin.toString(this.targetLinks)) + (', value=' + Kotlin.toString(this.value)) + (', depth=' + Kotlin.toString(this.depth)) + (', height=' + Kotlin.toString(this.height)) + (', x0=' + Kotlin.toString(this.x0)) + (', x1=' + Kotlin.toString(this.x1)) + (', y0=' + Kotlin.toString(this.y0)) + (', y1=' + Kotlin.toString(this.y1)) + ')';
  };
  SankeyNode.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.data) | 0;
    result = result * 31 + Kotlin.hashCode(this.index) | 0;
    result = result * 31 + Kotlin.hashCode(this.sourceLinks) | 0;
    result = result * 31 + Kotlin.hashCode(this.targetLinks) | 0;
    result = result * 31 + Kotlin.hashCode(this.value) | 0;
    result = result * 31 + Kotlin.hashCode(this.depth) | 0;
    result = result * 31 + Kotlin.hashCode(this.height) | 0;
    result = result * 31 + Kotlin.hashCode(this.x0) | 0;
    result = result * 31 + Kotlin.hashCode(this.x1) | 0;
    result = result * 31 + Kotlin.hashCode(this.y0) | 0;
    result = result * 31 + Kotlin.hashCode(this.y1) | 0;
    return result;
  };
  SankeyNode.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.data, other.data) && Kotlin.equals(this.index, other.index) && Kotlin.equals(this.sourceLinks, other.sourceLinks) && Kotlin.equals(this.targetLinks, other.targetLinks) && Kotlin.equals(this.value, other.value) && Kotlin.equals(this.depth, other.depth) && Kotlin.equals(this.height, other.height) && Kotlin.equals(this.x0, other.x0) && Kotlin.equals(this.x1, other.x1) && Kotlin.equals(this.y0, other.y0) && Kotlin.equals(this.y1, other.y1)))));
  };
  function SankeyGraph(nodes, links) {
    this.nodes = nodes;
    this.links = links;
  }
  SankeyGraph.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'SankeyGraph',
    interfaces: []
  };
  SankeyGraph.prototype.component1 = function () {
    return this.nodes;
  };
  SankeyGraph.prototype.component2 = function () {
    return this.links;
  };
  SankeyGraph.prototype.copy_6njutg$ = function (nodes, links) {
    return new SankeyGraph(nodes === void 0 ? this.nodes : nodes, links === void 0 ? this.links : links);
  };
  SankeyGraph.prototype.toString = function () {
    return 'SankeyGraph(nodes=' + Kotlin.toString(this.nodes) + (', links=' + Kotlin.toString(this.links)) + ')';
  };
  SankeyGraph.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.nodes) | 0;
    result = result * 31 + Kotlin.hashCode(this.links) | 0;
    return result;
  };
  SankeyGraph.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.nodes, other.nodes) && Kotlin.equals(this.links, other.links)))));
  };
  function SankeyLayout() {
    this.x0_0 = 0.0;
    this.y0_0 = 0.0;
    this.x1_0 = 1.0;
    this.y1_0 = 1.0;
    this.nodeWidth = 24.0;
    this.nodePadding = 8.0;
    this.align = SankeyAlignment$JUSTIFY_getInstance();
    this.iterations = 32;
    this.nodes = ArrayList_init();
    this.links = ArrayList_init();
  }
  Object.defineProperty(SankeyLayout.prototype, 'height', {
    get: function () {
      return this.y1_0 - this.y0_0;
    },
    set: function (value) {
      this.y0_0 = 0.0;
      this.y1_0 = value;
    }
  });
  Object.defineProperty(SankeyLayout.prototype, 'width', {
    get: function () {
      return this.x1_0 - this.x0_0;
    },
    set: function (value) {
      this.x0_0 = 0.0;
      this.x1_0 = value;
    }
  });
  SankeyLayout.prototype.extent_6y0v78$ = function (x0, x1, y0, y1) {
    this.x0_0 = x0;
    this.x1_0 = x1;
    this.y0_0 = y0;
    this.y1_0 = y1;
  };
  SankeyLayout.prototype.sankey_xa38pt$ = function (data, flow) {
    this.nodes.clear();
    this.links.clear();
    this.computeNodeLinks_0(data, flow);
    this.computeNodeValues_0();
    this.computeNodeDepths_0();
    this.computeNodeBreadths_0();
    this.computeLinkBreadths_0();
    return new SankeyGraph(this.nodes, this.links);
  };
  function SankeyLayout$computeLinkBreadths$lambda$lambda(it) {
    return it.target.y0;
  }
  function SankeyLayout$computeLinkBreadths$lambda$lambda_0(it) {
    return it.index;
  }
  function SankeyLayout$computeLinkBreadths$lambda$lambda_1(it) {
    return it.source.y0;
  }
  function SankeyLayout$computeLinkBreadths$lambda$lambda_2(it) {
    return it.index;
  }
  SankeyLayout.prototype.computeLinkBreadths_0 = function () {
    var tmp$;
    tmp$ = this.nodes.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      sortWith(element.sourceLinks, compareBy([SankeyLayout$computeLinkBreadths$lambda$lambda, SankeyLayout$computeLinkBreadths$lambda$lambda_0]));
      sortWith(element.targetLinks, compareBy([SankeyLayout$computeLinkBreadths$lambda$lambda_1, SankeyLayout$computeLinkBreadths$lambda$lambda_2]));
    }
    var tmp$_0;
    tmp$_0 = this.nodes.iterator();
    while (tmp$_0.hasNext()) {
      var element_0 = tmp$_0.next();
      var y0 = {v: element_0.y0};
      var y1 = {v: y0.v};
      var tmp$_1;
      tmp$_1 = element_0.sourceLinks.iterator();
      while (tmp$_1.hasNext()) {
        var element_1 = tmp$_1.next();
        element_1.y0 = y0.v + element_1.width / 2.0;
        y0.v += element_1.width;
      }
      var tmp$_2;
      tmp$_2 = element_0.targetLinks.iterator();
      while (tmp$_2.hasNext()) {
        var element_2 = tmp$_2.next();
        element_2.y1 = y1.v + element_2.width / 2.0;
        y1.v += element_2.width;
      }
    }
  };
  var Math_0 = Math;
  SankeyLayout.prototype.computeNodeDepths_0 = function () {
    var nodeList = toList(this.nodes);
    var next = ArrayList_init();
    var nodeDepth = {v: 0};
    while (!nodeList.isEmpty()) {
      var tmp$;
      tmp$ = nodeList.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        element.depth = nodeDepth.v;
        var tmp$_0;
        tmp$_0 = element.sourceLinks.iterator();
        while (tmp$_0.hasNext()) {
          var element_0 = tmp$_0.next();
          if (next.indexOf_11rb$(element_0.target) < 0)
            next.add_11rb$(element_0.target);
        }
      }
      nodeDepth.v = nodeDepth.v + 1 | 0;
      nodeList = toList(next);
      next.clear();
    }
    nodeList = toList(this.nodes);
    next.clear();
    var nodeHeight = {v: 0};
    while (!nodeList.isEmpty()) {
      var tmp$_1;
      tmp$_1 = nodeList.iterator();
      while (tmp$_1.hasNext()) {
        var element_1 = tmp$_1.next();
        element_1.height = nodeHeight.v;
        var tmp$_2;
        tmp$_2 = element_1.targetLinks.iterator();
        while (tmp$_2.hasNext()) {
          var element_2 = tmp$_2.next();
          if (next.indexOf_11rb$(element_2.source) < 0)
            next.add_11rb$(element_2.source);
        }
      }
      nodeHeight.v = nodeHeight.v + 1 | 0;
      nodeList = toList(next);
      next.clear();
    }
    var kx = (this.width - this.nodeWidth) / (nodeHeight.v - 1 | 0);
    var tmp$_3;
    tmp$_3 = this.nodes.iterator();
    while (tmp$_3.hasNext()) {
      var element_3 = tmp$_3.next();
      var tmp$_4;
      switch (this.align.name) {
        case 'JUSTIFY':
          tmp$_4 = this.justify_0(element_3, nodeHeight.v);
          break;
        case 'CENTER':
          tmp$_4 = this.center_0(element_3, nodeHeight.v);
          break;
        case 'RIGHT':
          tmp$_4 = this.right_0(element_3, nodeHeight.v);
          break;
        case 'LEFT':
          tmp$_4 = this.left_0(element_3, nodeHeight.v);
          break;
        default:tmp$_4 = Kotlin.noWhenBranchMatched();
          break;
      }
      var x = tmp$_4;
      var tmp$_5 = this.x0_0;
      var tmp$_6 = nodeHeight.v - 1.0;
      var b = Math_0.floor(x);
      var b_0 = Math_0.min(tmp$_6, b);
      element_3.x0 = tmp$_5 + Math_0.max(0.0, b_0) * kx;
      element_3.x1 = element_3.x0 + this.nodeWidth;
    }
  };
  SankeyLayout.prototype.relaxLeftToRight_0 = function (columns, alpha) {
    var tmp$;
    tmp$ = columns.entries.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      var tmp$_0;
      tmp$_0 = element.value.iterator();
      while (tmp$_0.hasNext()) {
        var element_0 = tmp$_0.next();
        if (!element_0.targetLinks.isEmpty()) {
          var $receiver = element_0.targetLinks;
          var selector = getCallableRef('weightedSource', function ($receiver, link) {
            return $receiver.weightedSource_0(link);
          }.bind(null, this));
          var tmp$_1;
          var sum = 0.0;
          tmp$_1 = $receiver.iterator();
          while (tmp$_1.hasNext()) {
            var element_1 = tmp$_1.next();
            sum += selector(element_1);
          }
          var tmp$_2;
          var sum_0 = 0.0;
          tmp$_2 = element_0.targetLinks.iterator();
          while (tmp$_2.hasNext()) {
            var element_2 = tmp$_2.next();
            sum_0 += element_2.value;
          }
          var dy = (sum / sum_0 - this.nodeCenter_0(element_0)) * alpha;
          element_0.y0 = element_0.y0 + dy;
          element_0.y1 = element_0.y1 + dy;
        }
      }
    }
  };
  SankeyLayout.prototype.relaxRightToLeft_0 = function (columns, alpha) {
    var tmp$;
    tmp$ = reversed(columns.keys).iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      var nodeList = ensureNotNull(columns.get_11rb$(element));
      var tmp$_0;
      tmp$_0 = nodeList.iterator();
      while (tmp$_0.hasNext()) {
        var element_0 = tmp$_0.next();
        if (!element_0.sourceLinks.isEmpty()) {
          var $receiver = element_0.sourceLinks;
          var selector = getCallableRef('weightedTarget', function ($receiver, link) {
            return $receiver.weightedTarget_0(link);
          }.bind(null, this));
          var tmp$_1;
          var sum = 0.0;
          tmp$_1 = $receiver.iterator();
          while (tmp$_1.hasNext()) {
            var element_1 = tmp$_1.next();
            sum += selector(element_1);
          }
          var sum1 = sum;
          var tmp$_2;
          var sum_0 = 0.0;
          tmp$_2 = element_0.sourceLinks.iterator();
          while (tmp$_2.hasNext()) {
            var element_2 = tmp$_2.next();
            sum_0 += element_2.value;
          }
          var sum2 = sum_0;
          var nodeCenter = this.nodeCenter_0(element_0);
          var dy = (sum1 / sum2 - nodeCenter) * alpha;
          element_0.y0 = element_0.y0 + dy;
          element_0.y1 = element_0.y1 + dy;
        }
      }
    }
  };
  SankeyLayout.prototype.weightedTarget_0 = function (link) {
    return this.nodeCenter_0(link.target) * link.value;
  };
  SankeyLayout.prototype.weightedSource_0 = function (link) {
    return this.nodeCenter_0(link.source) * link.value;
  };
  SankeyLayout.prototype.nodeCenter_0 = function (node) {
    return (node.y0 + node.y1) / 2.0;
  };
  function SankeyLayout$resolveCollisions$lambda$lambda(it) {
    return it.y0;
  }
  var sortedWith = Kotlin.kotlin.collections.sortedWith_eknfly$;
  var wrapFunction = Kotlin.wrapFunction;
  var compareBy$lambda = wrapFunction(function () {
    var compareValues = Kotlin.kotlin.comparisons.compareValues_s00gnj$;
    return function (closure$selector) {
      return function (a, b) {
        var selector = closure$selector;
        return compareValues(selector(a), selector(b));
      };
    };
  });
  var Comparator = Kotlin.kotlin.Comparator;
  function Comparator$ObjectLiteral(closure$comparison) {
    this.closure$comparison = closure$comparison;
  }
  Comparator$ObjectLiteral.prototype.compare = function (a, b) {
    return this.closure$comparison(a, b);
  };
  Comparator$ObjectLiteral.$metadata$ = {kind: Kind_CLASS, interfaces: [Comparator]};
  SankeyLayout.prototype.resolveCollisions_0 = function (columns) {
    var tmp$;
    tmp$ = columns.entries.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      var nodes = sortedWith(element.value, new Comparator$ObjectLiteral(compareBy$lambda(SankeyLayout$resolveCollisions$lambda$lambda)));
      var dy = {v: null};
      var y = {v: this.y0_0};
      var tmp$_0;
      tmp$_0 = nodes.iterator();
      while (tmp$_0.hasNext()) {
        var element_0 = tmp$_0.next();
        dy.v = y.v - element_0.y0;
        if (dy.v > 0) {
          element_0.y0 = element_0.y0 + dy.v;
          element_0.y1 = element_0.y1 + dy.v;
        }
        y.v = element_0.y1 + this.nodePadding;
      }
      dy.v = y.v - this.nodePadding - this.y1_0;
      if (dy.v > 0) {
        var lastNode = last(nodes);
        lastNode.y0 = lastNode.y0 - dy.v;
        y.v = lastNode.y0;
        lastNode.y1 = lastNode.y1 - dy.v;
        var tmp$_1;
        tmp$_1 = downTo(nodes.size - 2 | 0, 0).iterator();
        while (tmp$_1.hasNext()) {
          var element_1 = tmp$_1.next();
          var node = nodes.get_za3lpa$(element_1);
          dy.v = node.y1 + this.nodePadding - y.v;
          if (dy.v > 0) {
            node.y0 = node.y0 - dy.v;
            node.y1 = node.y1 - dy.v;
          }
          y.v = node.y0;
        }
      }
    }
  };
  var LinkedHashMap_init = Kotlin.kotlin.collections.LinkedHashMap_init_q3lmfv$;
  SankeyLayout.prototype.computeNodeBreadths_0 = function () {
    var $receiver = this.nodes;
    var destination = LinkedHashMap_init();
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      var key = element.x0;
      var tmp$_0;
      var value = destination.get_11rb$(key);
      if (value == null) {
        var answer = ArrayList_init();
        destination.put_xwzc9p$(key, answer);
        tmp$_0 = answer;
      }
       else {
        tmp$_0 = value;
      }
      var list = tmp$_0;
      list.add_11rb$(element);
    }
    var columns = destination;
    this.initializeNodeBreadth_0(columns);
    this.resolveCollisions_0(columns);
    var alpha = {v: 1.0};
    var tmp$_1;
    tmp$_1 = (new IntRange(1, this.iterations)).iterator();
    while (tmp$_1.hasNext()) {
      var element_0 = tmp$_1.next();
      alpha.v *= 0.99;
      this.relaxRightToLeft_0(columns, alpha.v);
      this.resolveCollisions_0(columns);
      this.relaxLeftToRight_0(columns, alpha.v);
      this.resolveCollisions_0(columns);
    }
  };
  var checkIndexOverflow = Kotlin.kotlin.collections.checkIndexOverflow_za3lpa$;
  var ArrayList_init_0 = Kotlin.kotlin.collections.ArrayList_init_ww73n8$;
  SankeyLayout.prototype.initializeNodeBreadth_0 = function (columns) {
    var destination = ArrayList_init_0(columns.size);
    var tmp$;
    tmp$ = columns.entries.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      var tmp$_0 = destination.add_11rb$;
      var tmp$_1 = this.height - (item.value.size - 1 | 0) * this.nodePadding;
      var tmp$_2;
      var sum = 0.0;
      tmp$_2 = item.value.iterator();
      while (tmp$_2.hasNext()) {
        var element = tmp$_2.next();
        sum += element.value;
      }
      tmp$_0.call(destination, tmp$_1 / sum);
    }
    var ky = ensureNotNull(min(destination));
    var tmp$_3;
    tmp$_3 = columns.entries.iterator();
    while (tmp$_3.hasNext()) {
      var element_0 = tmp$_3.next();
      var tmp$_4, tmp$_0_0;
      var index = 0;
      tmp$_4 = element_0.value.iterator();
      while (tmp$_4.hasNext()) {
        var item_0 = tmp$_4.next();
        item_0.y0 = checkIndexOverflow((tmp$_0_0 = index, index = tmp$_0_0 + 1 | 0, tmp$_0_0));
        item_0.y1 = item_0.y0 + item_0.value * ky;
      }
    }
    var tmp$_5;
    tmp$_5 = this.links.iterator();
    while (tmp$_5.hasNext()) {
      var element_1 = tmp$_5.next();
      element_1.width = element_1.value * ky;
    }
  };
  SankeyLayout.prototype.computeNodeValues_0 = function () {
    var tmp$;
    tmp$ = this.nodes.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      var tmp$_0;
      var sum = 0.0;
      tmp$_0 = element.sourceLinks.iterator();
      while (tmp$_0.hasNext()) {
        var element_0 = tmp$_0.next();
        sum += element_0.value;
      }
      var tmp$_1;
      var sum_0 = 0.0;
      tmp$_1 = element.targetLinks.iterator();
      while (tmp$_1.hasNext()) {
        var element_1 = tmp$_1.next();
        sum_0 += element_1.value;
      }
      element.value = Math_0.max(sum, sum_0);
    }
  };
  var collectionSizeOrDefault = Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$;
  SankeyLayout.prototype.computeNodeLinks_0 = function (data, flow) {
    var tmp$ = this.nodes;
    var destination = ArrayList_init_0(collectionSizeOrDefault(data, 10));
    var tmp$_0, tmp$_0_0;
    var index = 0;
    tmp$_0 = data.iterator();
    while (tmp$_0.hasNext()) {
      var item = tmp$_0.next();
      destination.add_11rb$(new SankeyNode(item, checkIndexOverflow((tmp$_0_0 = index, index = tmp$_0_0 + 1 | 0, tmp$_0_0))));
    }
    tmp$.addAll_brywnq$(destination);
    var index_0 = {v: 0};
    var tmp$_1, tmp$_0_1;
    var index_1 = 0;
    tmp$_1 = data.iterator();
    while (tmp$_1.hasNext()) {
      var item_0 = tmp$_1.next();
      var index1 = checkIndexOverflow((tmp$_0_1 = index_1, index_1 = tmp$_0_1 + 1 | 0, tmp$_0_1));
      var tmp$_2, tmp$_0_2;
      var index_2 = 0;
      tmp$_2 = data.iterator();
      while (tmp$_2.hasNext()) {
        var item_1 = tmp$_2.next();
        var index2 = checkIndexOverflow((tmp$_0_2 = index_2, index_2 = tmp$_0_2 + 1 | 0, tmp$_0_2));
        var linkValue = flow(item_0, item_1);
        if (linkValue != null && linkValue > 0.0) {
          var node1 = this.nodes.get_za3lpa$(index1);
          var node2 = this.nodes.get_za3lpa$(index2);
          var link = new SankeyLink(node1, node2, index_0.v, linkValue);
          this.links.add_11rb$(link);
          node1.sourceLinks.add_11rb$(link);
          node2.targetLinks.add_11rb$(link);
          index_0.v = index_0.v + 1 | 0;
        }
      }
    }
  };
  SankeyLayout.prototype.justify_0 = function (node, size) {
    return node.sourceLinks.isEmpty() ? size - 1 | 0 : node.depth;
  };
  SankeyLayout.prototype.left_0 = function (node, size) {
    return node.depth;
  };
  SankeyLayout.prototype.right_0 = function (node, size) {
    return size - 1 - node.height | 0;
  };
  SankeyLayout.prototype.center_0 = function (node, size) {
    var tmp$;
    if (node.targetLinks.isEmpty()) {
      var tmp$_0;
      if (node.sourceLinks.isEmpty())
        tmp$_0 = 0;
      else {
        var $receiver = node.sourceLinks;
        var minBy$result;
        minBy$break: do {
          var iterator = $receiver.iterator();
          if (!iterator.hasNext()) {
            minBy$result = null;
            break minBy$break;
          }
          var minElem = iterator.next();
          var minValue = minElem.target.depth;
          while (iterator.hasNext()) {
            var e = iterator.next();
            var v = e.target.depth;
            if (Kotlin.compareTo(minValue, v) > 0) {
              minElem = e;
              minValue = v;
            }
          }
          minBy$result = minElem;
        }
         while (false);
        tmp$_0 = ensureNotNull(minBy$result).target.depth - 1 | 0;
      }
      tmp$ = tmp$_0;
    }
     else
      tmp$ = node.depth;
    return tmp$;
  };
  SankeyLayout.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'SankeyLayout',
    interfaces: []
  };
  Object.defineProperty(SankeyAlignment, 'CENTER', {
    get: SankeyAlignment$CENTER_getInstance
  });
  Object.defineProperty(SankeyAlignment, 'JUSTIFY', {
    get: SankeyAlignment$JUSTIFY_getInstance
  });
  Object.defineProperty(SankeyAlignment, 'RIGHT', {
    get: SankeyAlignment$RIGHT_getInstance
  });
  Object.defineProperty(SankeyAlignment, 'LEFT', {
    get: SankeyAlignment$LEFT_getInstance
  });
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$sankey = package$data2viz.sankey || (package$data2viz.sankey = {});
  package$sankey.SankeyAlignment = SankeyAlignment;
  Object.defineProperty(package$sankey, 'sankeyLinkHorizontal', {
    get: function () {
      return sankeyLinkHorizontal;
    }
  });
  Object.defineProperty(package$sankey, 'sankeyLinkVertical', {
    get: function () {
      return sankeyLinkVertical;
    }
  });
  package$sankey.SankeyLink = SankeyLink;
  package$sankey.SankeyNode = SankeyNode;
  package$sankey.SankeyGraph = SankeyGraph;
  package$sankey.SankeyLayout = SankeyLayout;
  sankeyLinkHorizontal = linkBuilderH(sankeyLinkHorizontal$lambda);
  sankeyLinkVertical = linkBuilderV(sankeyLinkVertical$lambda);
  Kotlin.defineModule('d2v-sankey-js', _);
  return _;
}));

//# sourceMappingURL=d2v-sankey-js.js.map
