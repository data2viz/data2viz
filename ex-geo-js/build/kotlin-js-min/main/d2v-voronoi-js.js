(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin', 'd2v-core-js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'), require('d2v-core-js'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'd2v-voronoi-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'd2v-voronoi-js'.");
    }
    if (typeof this['d2v-core-js'] === 'undefined') {
      throw new Error("Error loading module 'd2v-voronoi-js'. Its dependency 'd2v-core-js' was not found. Please, check whether 'd2v-core-js' is loaded prior to 'd2v-voronoi-js'.");
    }
    root['d2v-voronoi-js'] = factory(typeof this['d2v-voronoi-js'] === 'undefined' ? {} : this['d2v-voronoi-js'], kotlin, this['d2v-core-js']);
  }
}(this, function (_, Kotlin, $module$d2v_core_js) {
  'use strict';
  var throwUPAE = Kotlin.throwUPAE;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var ensureNotNull = Kotlin.ensureNotNull;
  var Point = $module$d2v_core_js.io.data2viz.geom.Point;
  var mutableListOf = Kotlin.kotlin.collections.mutableListOf_i5x0yv$;
  var kotlin_js_internal_DoubleCompanionObject = Kotlin.kotlin.js.internal.DoubleCompanionObject;
  var Kind_OBJECT = Kotlin.Kind.OBJECT;
  var get_lastIndex = Kotlin.kotlin.collections.get_lastIndex_m7z4lg$;
  var Comparator = Kotlin.kotlin.Comparator;
  var sortWith = Kotlin.kotlin.collections.sortWith_iwcb0m$;
  var IntRange = Kotlin.kotlin.ranges.IntRange;
  var get_lastIndex_0 = Kotlin.kotlin.collections.get_lastIndex_55thoc$;
  var equals = Kotlin.equals;
  var asSequence = Kotlin.kotlin.collections.asSequence_us0mfu$;
  var filterNotNull = Kotlin.kotlin.sequences.filterNotNull_q2m9h7$;
  var filterNotNull_0 = Kotlin.kotlin.collections.filterNotNull_m3lr2h$;
  var map = Kotlin.kotlin.sequences.map_z5avom$;
  var toList = Kotlin.kotlin.sequences.toList_veqyi0$;
  var emptyList = Kotlin.kotlin.collections.emptyList_287e2$;
  var filterNotNull_1 = Kotlin.kotlin.collections.filterNotNull_emfgvx$;
  var sort = Kotlin.kotlin.collections.sort_pbinho$;
  var toMutableList = Kotlin.kotlin.collections.toMutableList_us0mfu$;
  var Enum = Kotlin.kotlin.Enum;
  var throwISE = Kotlin.throwISE;
  var Comparable = Kotlin.kotlin.Comparable;
  RedBlackColor.prototype = Object.create(Enum.prototype);
  RedBlackColor.prototype.constructor = RedBlackColor;
  var epsilon;
  var epsilon2;
  var beaches;
  var beachPool;
  function redBlackNode($receiver) {
    return new RedBlackNode($receiver);
  }
  function Beach() {
    this.site_n9u8vv$_0 = this.site_n9u8vv$_0;
    this.circleNode = null;
    this.edge = null;
  }
  Object.defineProperty(Beach.prototype, 'site', {
    get: function () {
      if (this.site_n9u8vv$_0 == null)
        return throwUPAE('site');
      return this.site_n9u8vv$_0;
    },
    set: function (site) {
      this.site_n9u8vv$_0 = site;
    }
  });
  Object.defineProperty(Beach.prototype, 'circle', {
    get: function () {
      var tmp$;
      return (tmp$ = this.circleNode) != null ? tmp$.node : null;
    }
  });
  Beach.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Beach',
    interfaces: []
  };
  function get_site($receiver) {
    return $receiver.node.site;
  }
  function get_circle($receiver) {
    return $receiver.node.circle;
  }
  function createBeach(site) {
    var beach = beachPool.isEmpty() ? redBlackNode(new Beach()) : ensureNotNull(pop(beachPool));
    beach.node.site = site;
    return beach;
  }
  function detachBeach(beach) {
    detachCircle(beach);
    beaches.remove_g25sww$(beach);
    beachPool.add_11rb$(beach);
    beach.clean();
  }
  function addBeach(site) {
    var x = site.x;
    var directrix = site.y;
    var node = beaches.root;
    var lArc = null;
    var rArc = null;
    var dxl;
    var dxr;
    while (node != null) {
      dxl = leftBreakPoint(node, directrix) - x;
      if (dxl > epsilon) {
        node = node.L;
      }
       else {
        dxr = x - rightBreakPoint(node, directrix);
        if (dxr > epsilon) {
          if (node.R == null) {
            lArc = node;
            break;
          }
          node = node.R;
        }
         else {
          if (dxl > -epsilon) {
            lArc = node.P;
            rArc = node;
          }
           else if (dxr > -epsilon) {
            lArc = node;
            rArc = node.N;
          }
           else {
            lArc = node;
            rArc = node;
          }
          break;
        }
      }
    }
    Cell$Companion_getInstance().createCell_ryhnh2$(site);
    var newArc = createBeach(site);
    beaches.insert_nddyl1$(newArc, lArc);
    if (lArc == null && rArc == null)
      return;
    if (lArc === rArc) {
      detachCircle(ensureNotNull(lArc));
      rArc = createBeach(get_site(lArc));
      beaches.insert_nddyl1$(rArc, newArc);
      newArc.node.edge = Edge$Companion_getInstance().createEdge_op8iyg$(get_site(lArc), get_site(newArc));
      rArc.node.edge = newArc.node.edge;
      attachCircle(lArc);
      attachCircle(rArc);
      return;
    }
    if (rArc == null) {
      newArc.node.edge = Edge$Companion_getInstance().createEdge_op8iyg$(get_site(ensureNotNull(lArc)), get_site(newArc));
      return;
    }
    detachCircle(ensureNotNull(lArc));
    detachCircle(rArc);
    var lSite = get_site(lArc);
    var ax = lSite.x;
    var ay = lSite.y;
    var bx = site.x - ax;
    var by = site.y - ay;
    var rSite = get_site(rArc);
    var cx = rSite.x - ax;
    var cy = rSite.y - ay;
    var d = 2 * (bx * cy - by * cx);
    var hb = bx * by + by * by;
    var hc = cx * cx + cy * cy;
    var vertex = new Point((cy * hb - by * hc) / d + ax, (bx * hc - cx * hb) / d + ay);
    Edge$Companion_getInstance().setEdgeEnd_cf110x$(ensureNotNull(rArc.node.edge), lSite, rSite, vertex);
    newArc.node.edge = Edge$Companion_getInstance().createEdge_op8iyg$(lSite, site, null, vertex);
    rArc.node.edge = Edge$Companion_getInstance().createEdge_op8iyg$(site, rSite, null, vertex);
    attachCircle(lArc);
    attachCircle(rArc);
  }
  var Math_0 = Math;
  function removeBeach(circle) {
    var tmp$, tmp$_0, tmp$_1;
    var beach = ensureNotNull(circle.node.arcNode);
    var x = get_x(circle);
    var y = get_cy(circle);
    var vertex = new Point(x, y);
    var previous = beach.P;
    var next = beach.N;
    var disappearing = mutableListOf([beach]);
    detachBeach(beach);
    var lArc = previous;
    while (true) {
      var tmp$_2 = ((tmp$ = lArc != null ? lArc.node : null) != null ? tmp$.circle : null) != null;
      if (tmp$_2) {
        var x_0 = x - ensureNotNull(get_circle(lArc)).x;
        tmp$_2 = Math_0.abs(x_0) < epsilon;
      }
      var tmp$_3 = tmp$_2;
      if (tmp$_3) {
        var x_1 = y - ensureNotNull(get_circle(lArc)).cy;
        tmp$_3 = Math_0.abs(x_1) < epsilon;
      }
      if (!tmp$_3)
        break;
      previous = lArc.P;
      disappearing.add_wxm5ur$(0, lArc);
      detachBeach(lArc);
      lArc = previous;
    }
    disappearing.add_wxm5ur$(0, ensureNotNull(lArc));
    detachCircle(lArc);
    var rArc = next;
    while (true) {
      var tmp$_4 = ((tmp$_0 = rArc != null ? rArc.node : null) != null ? tmp$_0.circle : null) != null;
      if (tmp$_4) {
        var x_2 = x - ensureNotNull(get_circle(rArc)).x;
        tmp$_4 = Math_0.abs(x_2) < epsilon;
      }
      var tmp$_5 = tmp$_4;
      if (tmp$_5) {
        var x_3 = y - ensureNotNull(get_circle(rArc)).cy;
        tmp$_5 = Math_0.abs(x_3) < epsilon;
      }
      if (!tmp$_5)
        break;
      next = rArc.N;
      disappearing.add_11rb$(rArc);
      detachBeach(rArc);
      rArc = next;
    }
    disappearing.add_11rb$(ensureNotNull(rArc));
    detachCircle(rArc);
    var nArcs = disappearing.size;
    tmp$_1 = nArcs - 1 | 0;
    for (var iArc = 1; iArc <= tmp$_1; iArc++) {
      rArc = disappearing.get_za3lpa$(iArc);
      lArc = disappearing.get_za3lpa$(iArc - 1 | 0);
      Edge$Companion_getInstance().setEdgeEnd_cf110x$(ensureNotNull(rArc.node.edge), get_site(lArc), get_site(rArc), vertex);
    }
    lArc = disappearing.get_za3lpa$(0);
    rArc = disappearing.get_za3lpa$(nArcs - 1 | 0);
    rArc.node.edge = Edge$Companion_getInstance().createEdge_op8iyg$(get_site(lArc), get_site(rArc), null, vertex);
    attachCircle(lArc);
    attachCircle(rArc);
  }
  function leftBreakPoint(arc, directrix) {
    var tmp$;
    var site = arc.node.site;
    var rfocx = site.x;
    var rfocy = site.y;
    var pby2 = rfocy - directrix;
    if (pby2 === 0.0)
      return rfocx;
    tmp$ = arc.P;
    if (tmp$ == null) {
      return kotlin_js_internal_DoubleCompanionObject.NEGATIVE_INFINITY;
    }
    var lArc = tmp$;
    site = lArc.node.site;
    var lfocx = site.x;
    var lfocy = site.y;
    var plby2 = lfocy - directrix;
    if (plby2 === 0.0)
      return lfocx;
    var hl = lfocx - rfocx;
    var aby2 = 1 / pby2 - 1 / plby2;
    var b = hl / plby2;
    var tmp$_0;
    if (aby2 !== 0.0) {
      var tmp$_1 = -b;
      var x = b * b - 2 * aby2 * (hl * hl / (-2 * plby2) - lfocy + plby2 / 2 + rfocy - pby2 / 2);
      tmp$_0 = (tmp$_1 + Math_0.sqrt(x)) / aby2 + rfocx;
    }
     else
      tmp$_0 = (rfocx + lfocx) / 2;
    return tmp$_0;
  }
  function rightBreakPoint(arc, directrix) {
    var rArc = arc.N;
    if (rArc != null)
      return leftBreakPoint(rArc, directrix);
    var site = arc.node.site;
    return site.y === directrix ? site.x : kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY;
  }
  var wCells;
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  function Cell(site) {
    Cell$Companion_getInstance();
    this.site = site;
    this.halfedges = ArrayList_init();
  }
  function Cell$Companion() {
    Cell$Companion_instance = this;
  }
  Cell$Companion.prototype.createCell_ryhnh2$ = function (site) {
    ensureNotNull(wCells)[site.index] = new Cell(site);
  };
  Cell$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var Cell$Companion_instance = null;
  function Cell$Companion_getInstance() {
    if (Cell$Companion_instance === null) {
      new Cell$Companion();
    }
    return Cell$Companion_instance;
  }
  Cell.prototype.halfedgeAngle_ryqpb4$ = function (edge) {
    var tmp$;
    var va = edge.left.pos;
    var vb = (tmp$ = edge.right) != null ? tmp$.pos : null;
    if (this.site.pos === vb) {
      vb = va;
      va = this.site.pos;
    }
    if (vb != null) {
      var y = vb.y - va.y;
      var x = vb.x - va.x;
      return Math_0.atan2(y, x);
    }
    if (this.site.pos === va) {
      va = ensureNotNull(edge.end);
      vb = edge.start;
    }
     else {
      va = ensureNotNull(edge.start);
      vb = edge.end;
    }
    var y_0 = va.x - ensureNotNull(vb).x;
    var x_0 = vb.y - va.y;
    return Math_0.atan2(y_0, x_0);
  };
  Cell.prototype.halfedgeStart_ryqpb4$ = function (edge) {
    return edge.left !== this.site ? edge.end : edge.start;
  };
  Cell.prototype.halfedgeEnd_ryqpb4$ = function (edge) {
    return edge.left === this.site ? edge.end : edge.start;
  };
  Cell.prototype.cellHalfedgeStart_ryqpb4$ = function (edge) {
    return edge.left !== this.site ? edge.end : edge.start;
  };
  Cell.prototype.cellHalfedgeEnd_ryqpb4$ = function (edge) {
    return edge.left === this.site ? edge.end : edge.start;
  };
  Cell.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Cell',
    interfaces: []
  };
  function sortCellHalfedges$ObjectLiteral(closure$angles) {
    this.closure$angles = closure$angles;
  }
  sortCellHalfedges$ObjectLiteral.prototype.compare = function (a, b) {
    return Kotlin.compareTo(this.closure$angles[b], this.closure$angles[a]);
  };
  sortCellHalfedges$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [Comparator]
  };
  var Array_0 = Array;
  function sortCellHalfedges() {
    var tmp$;
    var edgeCount;
    var halfedges = {v: null};
    var cell = {v: null};
    tmp$ = get_lastIndex(ensureNotNull(wCells));
    for (var cellIndex = 0; cellIndex <= tmp$; cellIndex++) {
      cell.v = ensureNotNull(ensureNotNull(wCells)[cellIndex]);
      halfedges.v = cell.v.halfedges;
      edgeCount = halfedges.v.size;
      var array = Array_0(edgeCount);
      var tmp$_0;
      tmp$_0 = array.length - 1 | 0;
      for (var i = 0; i <= tmp$_0; i++) {
        array[i] = i;
      }
      var indexes = array;
      var array_0 = Array_0(edgeCount);
      var tmp$_1;
      tmp$_1 = array_0.length - 1 | 0;
      for (var i_0 = 0; i_0 <= tmp$_1; i_0++) {
        array_0[i_0] = cell.v.halfedgeAngle_ryqpb4$(ensureNotNull(wEdges.get_za3lpa$(halfedges.v.get_za3lpa$(i_0))));
      }
      var angles = array_0;
      sortWith(indexes, new sortCellHalfedges$ObjectLiteral(angles));
      var array_1 = Array_0(edgeCount);
      var tmp$_2;
      tmp$_2 = array_1.length - 1 | 0;
      for (var i_1 = 0; i_1 <= tmp$_2; i_1++) {
        array_1[i_1] = halfedges.v.get_za3lpa$(indexes[i_1]);
      }
      var temp = array_1;
      var tmp$_3;
      tmp$_3 = (new IntRange(0, edgeCount - 1 | 0)).iterator();
      while (tmp$_3.hasNext()) {
        var element = tmp$_3.next();
        halfedges.v.set_wxm5ur$(element, temp[element]);
      }
    }
  }
  var firstCircle;
  var circles;
  var circlePool;
  function Circle() {
    this.x = 0.0;
    this.y = 0.0;
    this.cy = 0.0;
    this.site_3f35m0$_0 = this.site_3f35m0$_0;
    this.arcNode = null;
  }
  Object.defineProperty(Circle.prototype, 'site', {
    get: function () {
      if (this.site_3f35m0$_0 == null)
        return throwUPAE('site');
      return this.site_3f35m0$_0;
    },
    set: function (site) {
      this.site_3f35m0$_0 = site;
    }
  });
  Circle.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Circle',
    interfaces: []
  };
  function get_x($receiver) {
    return $receiver.node.x;
  }
  function set_x($receiver, value) {
    $receiver.node.x = value;
  }
  function get_y($receiver) {
    return $receiver.node.y;
  }
  function set_y($receiver, value) {
    $receiver.node.y = value;
  }
  function get_cy($receiver) {
    return $receiver.node.cy;
  }
  function set_cy($receiver, value) {
    $receiver.node.cy = value;
  }
  function get_site_0($receiver) {
    return $receiver.node.site;
  }
  function set_site($receiver, value) {
    $receiver.node.site = value;
  }
  function attachCircle(arcNode) {
    var lArc = arcNode.P;
    var rArc = arcNode.N;
    if (lArc == null || rArc == null)
      return;
    var lSite = get_site(lArc);
    var cSite = get_site(arcNode);
    var rSite = get_site(rArc);
    if (lSite === rSite)
      return;
    var bx = cSite.x;
    var by = cSite.y;
    var ax = lSite.x - bx;
    var ay = lSite.y - by;
    var cx = rSite.x - bx;
    var cy = rSite.y - by;
    var d = 2 * (ax * cy - ay * cx);
    if (d >= -epsilon2)
      return;
    var ha = ax * ax + ay * ay;
    var hc = cx * cx + cy * cy;
    var x = (cy * ha - ay * hc) / d;
    var y = (ax * hc - cx * ha) / d;
    var circle = circlePool.isEmpty() ? redBlackNode(new Circle()) : ensureNotNull(pop(circlePool));
    circle.node.arcNode = arcNode;
    set_site(circle, cSite);
    set_x(circle, x + bx);
    set_cy(circle, y + by);
    var tmp$ = y + by;
    var x_0 = x * x + y * y;
    set_y(circle, tmp$ + Math_0.sqrt(x_0));
    arcNode.node.circleNode = circle;
    var before = null;
    var node = circles.root;
    while (node != null) {
      if (get_y(circle) < get_y(node) || (get_y(circle) === get_y(node) && get_x(circle) <= get_x(node))) {
        if (node.L != null)
          node = node.L;
        else {
          before = node.P;
          break;
        }
      }
       else {
        if (node.R != null)
          node = node.R;
        else {
          before = node;
          break;
        }
      }
    }
    circles.insert_nddyl1$(circle, before);
    if (before == null)
      firstCircle = circle;
  }
  function detachCircle(arcNode) {
    var circle = arcNode.node.circleNode;
    if (circle != null) {
      if (circle.P == null) {
        firstCircle = circle.N;
      }
      circles.remove_g25sww$(circle);
      circle.clean();
      circlePool.add_11rb$(circle);
      arcNode.node.circleNode = null;
    }
  }
  function pop($receiver) {
    return $receiver.isEmpty() ? null : $receiver.removeAt_za3lpa$(get_lastIndex_0($receiver));
  }
  function Diagram(initialSites, clipStart, clipEnd) {
    if (clipStart === void 0)
      clipStart = new Point(0.0, 0.0);
    if (clipEnd === void 0)
      clipEnd = null;
    this.x_0 = null;
    this.y_0 = null;
    this.site_0 = null;
    this.circle_0 = null;
    sort(initialSites);
    this.sites_0 = toMutableList(initialSites);
    this.edges = null;
    this.cells = null;
    wCells = Kotlin.newArray(initialSites.length, null);
    this.site_0 = pop(this.sites_0);
    while (true) {
      this.circle_0 = firstCircle;
      if (this.site_0 != null && (this.circle_0 == null || ensureNotNull(this.site_0).y < get_y(ensureNotNull(this.circle_0)) || (ensureNotNull(this.site_0).y === get_y(ensureNotNull(this.circle_0)) && ensureNotNull(this.site_0).x < get_x(ensureNotNull(this.circle_0))))) {
        if (ensureNotNull(this.site_0).x !== this.x_0 || ensureNotNull(this.site_0).y !== this.y_0) {
          addBeach(ensureNotNull(this.site_0));
          this.x_0 = ensureNotNull(this.site_0).x;
          this.y_0 = ensureNotNull(this.site_0).y;
        }
        this.site_0 = pop(this.sites_0);
      }
       else if (this.circle_0 != null) {
        removeBeach(ensureNotNull(this.circle_0));
      }
       else {
        break;
      }
    }
    sortCellHalfedges();
    if (clipEnd != null) {
      this.clipEdges_0(clipStart, clipEnd);
      this.clipCells_0(clipStart, clipEnd);
    }
    this.edges = wEdges;
    this.cells = wCells;
    beaches.root = null;
    circles.root = null;
    wEdges = ArrayList_init();
    wCells = null;
    this._found_0 = null;
  }
  Diagram.prototype.clipCells_0 = function (clipStart, clipEnd) {
    var tmp$;
    var nCells = ensureNotNull(wCells).length;
    var cell;
    var site = {v: null};
    var iHalfedge = {v: null};
    var halfedges = {v: null};
    var nHalfedges = {v: null};
    var start = {v: null};
    var end = {v: null};
    var cover = {v: true};
    var x0 = clipStart.x;
    var y0 = clipStart.y;
    var x1 = clipEnd.x;
    var y1 = clipEnd.y;
    var $receiver = ensureNotNull(wCells);
    var tmp$_0;
    for (tmp$_0 = 0; tmp$_0 !== $receiver.length; ++tmp$_0) {
      var element = $receiver[tmp$_0];
      var tmp$_1, tmp$_2, tmp$_3, tmp$_4;
      site.v = ensureNotNull(element).site;
      halfedges.v = element.halfedges;
      iHalfedge.v = halfedges.v.size;
      while ((tmp$_1 = iHalfedge.v, iHalfedge.v = tmp$_1 - 1 | 0, tmp$_1) > 0) {
        if (wEdges.get_za3lpa$(halfedges.v.get_za3lpa$(iHalfedge.v)) == null) {
          halfedges.v.removeAt_za3lpa$(iHalfedge.v);
        }
      }
      iHalfedge.v = 0;
      nHalfedges.v = halfedges.v.size;
      while (iHalfedge.v < nHalfedges.v) {
        end.v = ensureNotNull(element.cellHalfedgeEnd_ryqpb4$(ensureNotNull(wEdges.get_za3lpa$(halfedges.v.get_za3lpa$(iHalfedge.v)))));
        start.v = ensureNotNull(element.cellHalfedgeStart_ryqpb4$(ensureNotNull(wEdges.get_za3lpa$(halfedges.v.get_za3lpa$((iHalfedge.v = iHalfedge.v + 1 | 0, iHalfedge.v) % nHalfedges.v)))));
        var startX = start.v.x;
        var startY = start.v.y;
        var endX = end.v.x;
        var endY = end.v.y;
        var x = end.v.x - start.v.x;
        var tmp$_5 = Math_0.abs(x) > epsilon;
        if (!tmp$_5) {
          var x_0 = end.v.y - start.v.y;
          tmp$_5 = Math_0.abs(x_0) > epsilon;
        }
        if (tmp$_5) {
          tmp$_2 = ensureNotNull(site.v);
          tmp$_3 = end.v;
          var x_1 = endX - x0;
          if (Math_0.abs(x_1) < epsilon && y1 - endY > epsilon) {
            var x_2 = startX - x0;
            tmp$_4 = new Point(x0, Math_0.abs(x_2) < epsilon ? startY : y1);
          }
           else {
            var x_3 = endY - y1;
            if (Math_0.abs(x_3) < epsilon && x1 - endX > epsilon) {
              var x_4 = startY - y1;
              tmp$_4 = new Point(Math_0.abs(x_4) < epsilon ? startX : x1, y1);
            }
             else {
              var x_5 = endX - x1;
              if (Math_0.abs(x_5) < epsilon && endY - y0 > epsilon) {
                var x_6 = startX - x1;
                tmp$_4 = new Point(x1, Math_0.abs(x_6) < epsilon ? startY : y0);
              }
               else {
                var x_7 = endY - y0;
                if (Math_0.abs(x_7) < epsilon && endX - x0 > epsilon) {
                  var x_8 = startY - y0;
                  tmp$_4 = new Point(Math_0.abs(x_8) < epsilon ? startX : x0, y0);
                }
                 else
                  tmp$_4 = null;
              }
            }
          }
          var edge = createBorderEdge(tmp$_2, tmp$_3, tmp$_4);
          wEdges.add_11rb$(edge);
          halfedges.v.add_wxm5ur$(iHalfedge.v, wEdges.size - 1 | 0);
          nHalfedges.v = nHalfedges.v + 1 | 0;
        }
      }
      if (nHalfedges.v > 0)
        cover.v = false;
    }
    if (cover.v) {
      var dx;
      var dy;
      var d2;
      var dc = kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY;
      var coverCel = {v: null};
      tmp$ = nCells - 1 | 0;
      for (var iCell = 0; iCell <= tmp$; iCell++) {
        cell = ensureNotNull(wCells)[iCell];
        if (cell != null) {
          site.v = cell.site;
          dx = ensureNotNull(site.v).x - x0;
          dy = ensureNotNull(site.v).y - y0;
          d2 = dx * dx + dy * dy;
          if (d2 < dc) {
            dc = d2;
            coverCel.v = cell;
          }
        }
      }
      if (coverCel.v != null) {
        var v00 = new Point(x0, y0);
        var v01 = new Point(x0, y1);
        var v11 = new Point(x1, y1);
        var v10 = new Point(x1, y0);
        site.v = coverCel.v.site;
        wEdges.add_11rb$(createBorderEdge(ensureNotNull(site.v), v00, v01));
        wEdges.add_11rb$(createBorderEdge(ensureNotNull(site.v), v01, v11));
        wEdges.add_11rb$(createBorderEdge(ensureNotNull(site.v), v11, v10));
        wEdges.add_11rb$(createBorderEdge(ensureNotNull(site.v), v10, v00));
        var tmp$_6;
        tmp$_6 = (new IntRange(wEdges.size - 5 | 0, wEdges.size - 1 | 0)).iterator();
        while (tmp$_6.hasNext()) {
          var element_0 = tmp$_6.next();
          coverCel.v.halfedges.add_11rb$(element_0);
        }
      }
    }
    var $receiver_0 = ensureNotNull(wCells);
    var tmp$_7, tmp$_0_0;
    var index = 0;
    for (tmp$_7 = 0; tmp$_7 !== $receiver_0.length; ++tmp$_7) {
      var item = $receiver_0[tmp$_7];
      var index_0 = (tmp$_0_0 = index, index = tmp$_0_0 + 1 | 0, tmp$_0_0);
      var tmp$_8;
      if (((tmp$_8 = item != null ? item.halfedges : null) != null ? tmp$_8.size : null) === 0) {
        wEdges.set_wxm5ur$(index_0, null);
      }
    }
  };
  Diagram.prototype.clipEdges_0 = function (start, end) {
    var tmp$;
    var i = wEdges.size;
    var edge;
    while ((tmp$ = i, i = tmp$ - 1 | 0, tmp$) > 0) {
      edge = ensureNotNull(wEdges.get_za3lpa$(i));
      var tmp$_0 = !edge.connect_840z2k$(start, end) || !edge.clip_840z2k$(start, end);
      if (!tmp$_0) {
        var x = ensureNotNull(edge.start).x - ensureNotNull(edge.end).x;
        var tmp$_1 = Math_0.abs(x) > epsilon;
        if (!tmp$_1) {
          var x_0 = ensureNotNull(edge.start).y - ensureNotNull(edge.end).y;
          tmp$_1 = Math_0.abs(x_0) > epsilon;
        }
        tmp$_0 = !tmp$_1;
      }
      if (tmp$_0) {
        wEdges.set_wxm5ur$(i, null);
      }
    }
  };
  Diagram.prototype.squareDistance_15s7n5$ = function ($receiver, point) {
    var vx = $receiver.x - point.x;
    var vy = $receiver.y - point.y;
    return vx * vx + vy * vy;
  };
  function Diagram$find$opposite($receiver, site) {
    if ($receiver.right == null)
      return null;
    return equals($receiver.left, site) ? $receiver.right : $receiver.left;
  }
  Diagram.prototype.find_4gwsdk$ = function (point, radius) {
    if (radius === void 0)
      radius = null;
    var tmp$, tmp$_0, tmp$_1, tmp$_2, tmp$_3;
    var that = this;
    var i0;
    var i1 = {v: (tmp$ = this._found_0) != null ? tmp$ : 0};
    var n = (tmp$_1 = (tmp$_0 = that.cells) != null ? tmp$_0.length : null) != null ? tmp$_1 : 0;
    var cell = {v: ensureNotNull(this.cells)[ensureNotNull(i1.v)]};
    while (cell.v == null) {
      if ((i1.v = i1.v + 1 | 0, i1.v) >= n)
        return null;
      cell.v = (tmp$_2 = that.cells) != null ? tmp$_2[i1.v] : null;
    }
    var d2 = {v: this.squareDistance_15s7n5$(point, cell.v.site.pos)};
    var opposite = Diagram$find$opposite;
    do {
      i0 = ensureNotNull(i1.v);
      cell.v = ensureNotNull(that.cells)[i0];
      i1.v = null;
      var tmp$_4;
      tmp$_4 = ensureNotNull(cell.v).halfedges.iterator();
      loop_label: while (tmp$_4.hasNext()) {
        var element = tmp$_4.next();
        action$break: do {
          var tmp$_5;
          var edge = that.edges.get_za3lpa$(element);
          tmp$_5 = edge != null ? opposite(edge, cell.v.site) : null;
          if (tmp$_5 == null) {
            break action$break;
          }
          var opposite_0 = tmp$_5;
          var v2 = this.squareDistance_15s7n5$(point, opposite_0.pos);
          if (v2 < d2.v) {
            d2.v = v2;
            i1.v = opposite_0.index;
          }
        }
         while (false);
      }
    }
     while (i1.v != null);
    that._found_0 = i0;
    return radius == null || d2.v <= radius * radius ? (tmp$_3 = cell.v) != null ? tmp$_3.site : null : null;
  };
  var collectionSizeOrDefault = Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$;
  var ArrayList_init_0 = Kotlin.kotlin.collections.ArrayList_init_ww73n8$;
  function Diagram$polygons$lambda(this$Diagram) {
    return function (cell) {
      var $receiver = cell.halfedges;
      var destination = ArrayList_init_0(collectionSizeOrDefault($receiver, 10));
      var tmp$;
      tmp$ = $receiver.iterator();
      while (tmp$.hasNext()) {
        var item = tmp$.next();
        var tmp$_0;
        destination.add_11rb$((tmp$_0 = this$Diagram.edges.get_za3lpa$(item)) != null ? cell.halfedgeStart_ryqpb4$(tmp$_0) : null);
      }
      return filterNotNull_0(destination);
    };
  }
  Diagram.prototype.polygons = function () {
    var tmp$, tmp$_0, tmp$_1, tmp$_2, tmp$_3;
    return (tmp$_3 = (tmp$_2 = (tmp$_1 = (tmp$_0 = (tmp$ = this.cells) != null ? asSequence(tmp$) : null) != null ? filterNotNull(tmp$_0) : null) != null ? map(tmp$_1, Diagram$polygons$lambda(this)) : null) != null ? toList(tmp$_2) : null) != null ? tmp$_3 : emptyList();
  };
  function Diagram$Link(source, target) {
    this.source = source;
    this.target = target;
  }
  Diagram$Link.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Link',
    interfaces: []
  };
  Diagram$Link.prototype.component1 = function () {
    return this.source;
  };
  Diagram$Link.prototype.component2 = function () {
    return this.target;
  };
  Diagram$Link.prototype.copy_840z2k$ = function (source, target) {
    return new Diagram$Link(source === void 0 ? this.source : source, target === void 0 ? this.target : target);
  };
  Diagram$Link.prototype.toString = function () {
    return 'Link(source=' + Kotlin.toString(this.source) + (', target=' + Kotlin.toString(this.target)) + ')';
  };
  Diagram$Link.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.source) | 0;
    result = result * 31 + Kotlin.hashCode(this.target) | 0;
    return result;
  };
  Diagram$Link.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.source, other.source) && Kotlin.equals(this.target, other.target)))));
  };
  Diagram.prototype.links = function () {
    var $receiver = this.edges;
    var destination = ArrayList_init();
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      if ((element != null ? element.right : null) != null)
        destination.add_11rb$(element);
    }
    var destination_0 = ArrayList_init_0(collectionSizeOrDefault(destination, 10));
    var tmp$_0;
    tmp$_0 = destination.iterator();
    while (tmp$_0.hasNext()) {
      var item = tmp$_0.next();
      destination_0.add_11rb$(new Diagram$Link(ensureNotNull(item).left.pos, ensureNotNull(item.right).pos));
    }
    return destination_0;
  };
  var checkIndexOverflow = Kotlin.kotlin.collections.checkIndexOverflow_za3lpa$;
  Diagram.prototype.triangles = function () {
    var triangles = ArrayList_init();
    var tmp$, tmp$_0;
    var index = 0;
    tmp$ = filterNotNull_1(ensureNotNull(this.cells)).iterator();
    loop_label: while (tmp$.hasNext()) {
      var item = tmp$.next();
      var i = checkIndexOverflow((tmp$_0 = index, index = tmp$_0 + 1 | 0, tmp$_0));
      action$break: do {
        var halfedges = item.halfedges;
        var m = halfedges.size;
        if (m === 0)
          break action$break;
        var site = item.site;
        var e1 = this.edges.get_za3lpa$(halfedges.get_za3lpa$(m - 1 | 0));
        var s0;
        var s1 = (e1 != null ? e1.left : null) === site ? e1.right : e1 != null ? e1.left : null;
        var j = -1;
        while ((j = j + 1 | 0, j) < m) {
          s0 = s1;
          e1 = this.edges.get_za3lpa$(halfedges.get_za3lpa$(j));
          s1 = (e1 != null ? e1.left : null) === site ? e1.right : e1 != null ? e1.left : null;
          if (s0 != null && s1 != null && i < s0.index && i < s1.index && this.triangleArea_ylm9vf$(site.pos, s0.pos, s1.pos) < 0) {
            triangles.add_11rb$(new Triangle(site, s0, s1));
          }
        }
      }
       while (false);
    }
    return triangles;
  };
  Diagram.prototype.triangleArea_ylm9vf$ = function (a, b, c) {
    return (a.x - c.x) * (b.y - a.y) - (a.x - b.x) * (c.y - a.y);
  };
  Diagram.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Diagram',
    interfaces: []
  };
  function Triangle(a, b, c) {
    this.a = a;
    this.b = b;
    this.c = c;
  }
  Triangle.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Triangle',
    interfaces: []
  };
  Triangle.prototype.component1 = function () {
    return this.a;
  };
  Triangle.prototype.component2 = function () {
    return this.b;
  };
  Triangle.prototype.component3 = function () {
    return this.c;
  };
  Triangle.prototype.copy_l6icba$ = function (a, b, c) {
    return new Triangle(a === void 0 ? this.a : a, b === void 0 ? this.b : b, c === void 0 ? this.c : c);
  };
  Triangle.prototype.toString = function () {
    return 'Triangle(a=' + Kotlin.toString(this.a) + (', b=' + Kotlin.toString(this.b)) + (', c=' + Kotlin.toString(this.c)) + ')';
  };
  Triangle.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.a) | 0;
    result = result * 31 + Kotlin.hashCode(this.b) | 0;
    result = result * 31 + Kotlin.hashCode(this.c) | 0;
    return result;
  };
  Triangle.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.a, other.a) && Kotlin.equals(this.b, other.b) && Kotlin.equals(this.c, other.c)))));
  };
  var wEdges;
  function createBorderEdge(left, v0, v1) {
    var ret = new Edge(left);
    ret.start = v0;
    ret.end = v1;
    return ret;
  }
  function Edge(left, right) {
    Edge$Companion_getInstance();
    if (right === void 0)
      right = null;
    this.left = left;
    this.right = right;
    this.start = null;
    this.end = null;
  }
  function Edge$Companion() {
    Edge$Companion_instance = this;
  }
  Edge$Companion.prototype.createEdge_op8iyg$ = function (left, right, v0, v1) {
    if (v0 === void 0)
      v0 = null;
    if (v1 === void 0)
      v1 = null;
    var edge = new Edge(left, right);
    wEdges.add_11rb$(edge);
    var index = wEdges.size - 1 | 0;
    ensureNotNull(ensureNotNull(wCells)[left.index]).halfedges.add_11rb$(index);
    ensureNotNull(ensureNotNull(wCells)[right.index]).halfedges.add_11rb$(index);
    if (v0 != null)
      this.setEdgeEnd_cf110x$(edge, left, right, v0);
    if (v1 != null)
      this.setEdgeEnd_cf110x$(edge, right, left, v1);
    return edge;
  };
  Edge$Companion.prototype.setEdgeEnd_cf110x$ = function (edge, left, right, vertex) {
    if (edge.start == null && edge.end == null) {
      edge.start = vertex;
      edge.left = left;
      edge.right = right;
    }
     else if (edge.left === right) {
      edge.end = vertex;
    }
     else {
      edge.start = vertex;
    }
  };
  Edge$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var Edge$Companion_instance = null;
  function Edge$Companion_getInstance() {
    if (Edge$Companion_instance === null) {
      new Edge$Companion();
    }
    return Edge$Companion_instance;
  }
  Edge.prototype.clip_840z2k$ = function (clipStart, clipEnd) {
    var a = this.start;
    var b = this.end;
    var ax = ensureNotNull(a).x;
    var ay = a.y;
    var bx = ensureNotNull(b).x;
    var by = b.y;
    var t0 = 0.0;
    var t1 = 1.0;
    var dx = bx - ax;
    var dy = by - ay;
    var r = clipStart.x - ax;
    if (dx === 0.0 && r > 0.0)
      return false;
    r /= dx;
    if (dx < 0) {
      if (r < t0)
        return false;
      if (r < t1)
        t1 = r;
    }
     else if (dx > 0) {
      if (r > t1)
        return false;
      if (r > t0)
        t0 = r;
    }
    r = clipEnd.x - ax;
    if (dx === 0.0 && r < 0)
      return false;
    r /= dx;
    if (dx < 0) {
      if (r > t1)
        return false;
      if (r > t0)
        t0 = r;
    }
     else if (dx > 0) {
      if (r < t0)
        return false;
      if (r < t1)
        t1 = r;
    }
    r = clipStart.y - ay;
    if (dy === 0.0 && r > 0)
      return false;
    r /= dy;
    if (dy < 0) {
      if (r < t0)
        return false;
      if (r < t1)
        t1 = r;
    }
     else if (dy > 0) {
      if (r > t1)
        return false;
      if (r > t0)
        t0 = r;
    }
    r = clipEnd.y - ay;
    if (dy === 0.0 && r < 0)
      return false;
    r /= dy;
    if (dy < 0) {
      if (r > t1)
        return false;
      if (r > t0)
        t0 = r;
    }
     else if (dy > 0) {
      if (r < t0)
        return false;
      if (r < t1)
        t1 = r;
    }
    if (t0 <= 0.0 && t1 >= 1.0)
      return true;
    if (t0 > 0)
      this.start = new Point(ax + t0 * dx, ay + t0 * dy);
    if (t1 < 1)
      this.end = new Point(ax + t1 * dx, ay + t1 * dy);
    return true;
  };
  Edge.prototype.connect_840z2k$ = function (clipStart, clipEnd) {
    var v1 = this.end;
    if (v1 != null)
      return true;
    var x0 = clipStart.x;
    var y0 = clipStart.y;
    var x1 = clipEnd.x;
    var y1 = clipEnd.y;
    var v0 = this.start;
    var lx = this.left.x;
    var ly = this.left.y;
    var rx = ensureNotNull(this.right).x;
    var ry = ensureNotNull(this.right).y;
    var fx = (lx + rx) / 2;
    var fy = (ly + ry) / 2;
    var fm;
    var fb;
    if (ry === ly) {
      if (fx < x0 || fx >= x1)
        return false;
      if (lx > rx) {
        if (v0 == null)
          v0 = new Point(fx, y0);
        else if (v0.y >= y1)
          return false;
        v1 = new Point(fx, y1);
      }
       else {
        if (v0 == null)
          v0 = new Point(fx, y1);
        else if (v0.y < y0)
          return false;
        v1 = new Point(fx, y0);
      }
    }
     else {
      fm = (lx - rx) / (ry - ly);
      fb = fy - fm * fx;
      if (fm < -1 || fm > 1) {
        if (lx > rx) {
          if (v0 == null)
            v0 = new Point((y0 - fb) / fm, y0);
          else if (v0.y >= y1)
            return false;
          v1 = new Point((y1 - fb) / fm, y1);
        }
         else {
          if (v0 == null)
            v0 = new Point((y1 - fb) / fm, y1);
          else if (v0.y < y0)
            return false;
          v1 = new Point((y0 - fb) / fm, y0);
        }
      }
       else {
        if (ly < ry) {
          if (v0 == null)
            v0 = new Point(x0, fm * x0 + fb);
          else if (v0.x >= x1)
            return false;
          v1 = new Point(x1, fm * x1 + fb);
        }
         else {
          if (v0 == null)
            v0 = new Point(x1, fm * x1 + fb);
          else if (v0.x < x0)
            return false;
          v1 = new Point(x0, fm * x0 + fb);
        }
      }
    }
    this.start = v0;
    this.end = v1;
    return true;
  };
  Edge.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Edge',
    interfaces: []
  };
  function RedBlackTree() {
    this.root = null;
  }
  RedBlackTree.prototype.insert_nddyl1$ = function (node, afterNode) {
    if (afterNode === void 0)
      afterNode = null;
    var tmp$;
    var parent;
    var grandpa;
    var uncle;
    var after = afterNode;
    if (after != null) {
      node.P = after;
      node.N = after.N;
      (tmp$ = after.N) != null ? (tmp$.P = node) : null;
      after.N = node;
      if (after.R != null) {
        after = after.R;
        while (ensureNotNull(after).L != null)
          after = ensureNotNull(after.L);
        after.L = node;
      }
       else {
        after.R = node;
      }
      parent = after;
    }
     else if (this.root != null) {
      after = first(ensureNotNull(this.root));
      node.P = null;
      node.N = after;
      after.P = node;
      after.L = node;
      parent = after;
    }
     else {
      node.P = null;
      node.N = null;
      this.root = node;
      parent = null;
    }
    node.L = null;
    node.R = null;
    node.U = parent;
    after = node;
    while (equals(parent != null ? parent.C : null, RedBlackColor$RED_getInstance())) {
      grandpa = parent.U;
      if (parent === ensureNotNull(grandpa).L) {
        uncle = ensureNotNull(grandpa).R;
        if (equals(uncle != null ? uncle.C : null, RedBlackColor$RED_getInstance())) {
          parent.C = RedBlackColor$BLACK_getInstance();
          uncle.C = RedBlackColor$BLACK_getInstance();
          grandpa.C = RedBlackColor$RED_getInstance();
          after = grandpa;
        }
         else {
          if (after === parent.R) {
            this.rotateLeft_g25sww$(parent);
            after = parent;
            parent = after.U;
          }
          ensureNotNull(parent).C = RedBlackColor$BLACK_getInstance();
          grandpa.C = RedBlackColor$RED_getInstance();
          this.rotateRight_g25sww$(grandpa);
        }
      }
       else {
        uncle = ensureNotNull(grandpa).L;
        if (equals(uncle != null ? uncle.C : null, RedBlackColor$RED_getInstance())) {
          parent.C = RedBlackColor$BLACK_getInstance();
          uncle.C = RedBlackColor$BLACK_getInstance();
          grandpa.C = RedBlackColor$RED_getInstance();
          after = grandpa;
        }
         else {
          if (after === parent.L) {
            this.rotateRight_g25sww$(parent);
            after = parent;
            parent = ensureNotNull(after.U);
          }
          parent.C = RedBlackColor$BLACK_getInstance();
          grandpa.C = RedBlackColor$RED_getInstance();
          this.rotateLeft_g25sww$(grandpa);
        }
      }
      parent = ensureNotNull(after).U;
    }
    ensureNotNull(this.root).C = RedBlackColor$BLACK_getInstance();
    return node;
  };
  RedBlackTree.prototype.remove_g25sww$ = function (toRemove) {
    var tmp$, tmp$_0, tmp$_1, tmp$_2, tmp$_3, tmp$_4, tmp$_5, tmp$_6, tmp$_7, tmp$_8;
    var node = toRemove;
    (tmp$ = ensureNotNull(node).N) != null ? (tmp$.P = node.P) : null;
    (tmp$_0 = node.P) != null ? (tmp$_0.N = node.N) : null;
    node.N = null;
    node.P = null;
    var parent = node.U;
    var sibling;
    var left = node.L;
    var right = node.R;
    var next;
    var red;
    if (left == null) {
      next = right;
    }
     else if (right == null) {
      next = left;
    }
     else {
      next = first(right);
    }
    if (parent != null) {
      if (parent.L === node) {
        parent.L = next;
      }
       else {
        parent.R = next;
      }
    }
     else {
      this.root = next;
    }
    if (left != null && right != null) {
      red = ensureNotNull(next).C;
      next.C = node.C;
      next.L = left;
      left.U = next;
      if (next !== right) {
        parent = next.U;
        next.U = node.U;
        node = next.R;
        ensureNotNull(parent).L = node;
        next.R = right;
        right.U = next;
      }
       else {
        next.U = parent;
        parent = next;
        node = next.R;
      }
    }
     else {
      red = node.C;
      node = next;
    }
    if (node != null) {
      node.U = parent;
    }
    if (red === RedBlackColor$RED_getInstance())
      return;
    if (node != null && node.C === RedBlackColor$RED_getInstance()) {
      node.C = RedBlackColor$BLACK_getInstance();
      return;
    }
    do {
      if (node === this.root)
        break;
      if (node === ensureNotNull(parent).L) {
        sibling = ensureNotNull(parent).R;
        if (equals(sibling != null ? sibling.C : null, RedBlackColor$RED_getInstance())) {
          sibling.C = RedBlackColor$BLACK_getInstance();
          parent.C = RedBlackColor$RED_getInstance();
          this.rotateLeft_g25sww$(parent);
          sibling = parent.R;
        }
        if (equals((tmp$_1 = sibling != null ? sibling.L : null) != null ? tmp$_1.C : null, RedBlackColor$RED_getInstance()) || equals((tmp$_2 = sibling != null ? sibling.R : null) != null ? tmp$_2.C : null, RedBlackColor$RED_getInstance())) {
          if (sibling.R == null || equals((tmp$_3 = sibling.R) != null ? tmp$_3.C : null, RedBlackColor$BLACK_getInstance())) {
            ensureNotNull(sibling.L).C = RedBlackColor$BLACK_getInstance();
            sibling.C = RedBlackColor$RED_getInstance();
            this.rotateRight_g25sww$(sibling);
            sibling = parent.R;
          }
          ensureNotNull(sibling).C = parent.C;
          parent.C = RedBlackColor$BLACK_getInstance();
          ensureNotNull(sibling.R).C = RedBlackColor$BLACK_getInstance();
          this.rotateLeft_g25sww$(parent);
          node = this.root;
          break;
        }
      }
       else {
        sibling = ensureNotNull(parent).L;
        if (equals(sibling != null ? sibling.C : null, RedBlackColor$RED_getInstance())) {
          sibling.C = RedBlackColor$BLACK_getInstance();
          parent.C = RedBlackColor$RED_getInstance();
          this.rotateRight_g25sww$(parent);
          sibling = parent.L;
        }
        if (equals((tmp$_4 = sibling != null ? sibling.L : null) != null ? tmp$_4.C : null, RedBlackColor$RED_getInstance()) || equals((tmp$_5 = sibling != null ? sibling.R : null) != null ? tmp$_5.C : null, RedBlackColor$RED_getInstance())) {
          if (sibling.L == null || equals((tmp$_6 = sibling.L) != null ? tmp$_6.C : null, RedBlackColor$BLACK_getInstance())) {
            (tmp$_7 = sibling.R) != null ? (tmp$_7.C = RedBlackColor$BLACK_getInstance()) : null;
            sibling.C = RedBlackColor$RED_getInstance();
            this.rotateLeft_g25sww$(sibling);
            sibling = parent.L;
          }
          sibling != null ? (sibling.C = parent.C) : null;
          parent.C = RedBlackColor$BLACK_getInstance();
          (tmp$_8 = parent.L) != null ? (tmp$_8.C = RedBlackColor$BLACK_getInstance()) : null;
          this.rotateRight_g25sww$(parent);
          node = this.root;
          break;
        }
      }
      sibling != null ? (sibling.C = RedBlackColor$RED_getInstance()) : null;
      node = parent;
      parent = parent.U;
    }
     while (equals(node != null ? node.C : null, RedBlackColor$BLACK_getInstance()));
    if (node != null) {
      node.C = RedBlackColor$BLACK_getInstance();
    }
  };
  RedBlackTree.prototype.rotateLeft_g25sww$ = function (node) {
    var p = node;
    var q = node.R;
    var parent = p.U;
    if (parent != null) {
      if (parent.L === p) {
        parent.L = q;
      }
       else {
        parent.R = q;
      }
    }
     else {
      this.root = q;
    }
    ensureNotNull(q).U = parent;
    p.U = q;
    p.R = q.L;
    if (p.R != null) {
      ensureNotNull(p.R).U = p;
    }
    q.L = p;
  };
  RedBlackTree.prototype.rotateRight_g25sww$ = function (node) {
    var p = node;
    var q = node.L;
    var parent = p.U;
    if (parent != null) {
      if (parent.L === p) {
        parent.L = q;
      }
       else {
        parent.R = q;
      }
    }
     else {
      this.root = q;
    }
    ensureNotNull(q).U = parent;
    p.U = q;
    p.L = q.R;
    if (p.L != null) {
      ensureNotNull(p.L).U = p;
    }
    q.R = p;
  };
  RedBlackTree.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'RedBlackTree',
    interfaces: []
  };
  function first($receiver) {
    var node = $receiver;
    while (node.L != null)
      node = ensureNotNull(node.L);
    return node;
  }
  function RedBlackColor(name, ordinal) {
    Enum.call(this);
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function RedBlackColor_initFields() {
    RedBlackColor_initFields = function () {
    };
    RedBlackColor$RED_instance = new RedBlackColor('RED', 0);
    RedBlackColor$BLACK_instance = new RedBlackColor('BLACK', 1);
  }
  var RedBlackColor$RED_instance;
  function RedBlackColor$RED_getInstance() {
    RedBlackColor_initFields();
    return RedBlackColor$RED_instance;
  }
  var RedBlackColor$BLACK_instance;
  function RedBlackColor$BLACK_getInstance() {
    RedBlackColor_initFields();
    return RedBlackColor$BLACK_instance;
  }
  RedBlackColor.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'RedBlackColor',
    interfaces: [Enum]
  };
  function RedBlackColor$values() {
    return [RedBlackColor$RED_getInstance(), RedBlackColor$BLACK_getInstance()];
  }
  RedBlackColor.values = RedBlackColor$values;
  function RedBlackColor$valueOf(name) {
    switch (name) {
      case 'RED':
        return RedBlackColor$RED_getInstance();
      case 'BLACK':
        return RedBlackColor$BLACK_getInstance();
      default:throwISE('No enum constant io.data2viz.voronoi.RedBlackColor.' + name);
    }
  }
  RedBlackColor.valueOf_61zpoe$ = RedBlackColor$valueOf;
  function RedBlackNode(node) {
    this.node = node;
    this.U = null;
    this.L = null;
    this.R = null;
    this.P = null;
    this.N = null;
    this.C = RedBlackColor$RED_getInstance();
  }
  RedBlackNode.prototype.clean = function () {
    this.U = null;
    this.L = null;
    this.R = null;
    this.P = null;
    this.N = null;
  };
  RedBlackNode.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'RedBlackNode',
    interfaces: []
  };
  function Site(pos, index) {
    this.pos = pos;
    this.index = index;
  }
  Object.defineProperty(Site.prototype, 'x', {
    get: function () {
      return this.pos.x;
    }
  });
  Object.defineProperty(Site.prototype, 'y', {
    get: function () {
      return this.pos.y;
    }
  });
  Site.prototype.compareTo_11rb$ = function (other) {
    if (other.pos.y < this.pos.y)
      return -1;
    else if (other.pos.y > this.pos.y)
      return 1;
    else if (other.pos.x < this.pos.x)
      return -1;
    else if (other.pos.x > this.pos.x)
      return 1;
    else
      return 0;
  };
  Site.prototype.toString = function () {
    return 'Site(pos=' + this.pos + ', index=' + this.index + ')';
  };
  Site.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Site',
    interfaces: [Comparable]
  };
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$voronoi = package$data2viz.voronoi || (package$data2viz.voronoi = {});
  Object.defineProperty(package$voronoi, 'epsilon', {
    get: function () {
      return epsilon;
    }
  });
  Object.defineProperty(package$voronoi, 'epsilon2', {
    get: function () {
      return epsilon2;
    }
  });
  Object.defineProperty(package$voronoi, 'beaches_8be2vx$', {
    get: function () {
      return beaches;
    }
  });
  Object.defineProperty(package$voronoi, 'beachPool_8be2vx$', {
    get: function () {
      return beachPool;
    }
  });
  package$voronoi.redBlackNode_fbcm8g$ = redBlackNode;
  package$voronoi.Beach = Beach;
  package$voronoi.get_site_noaapk$ = get_site;
  package$voronoi.get_circle_noaapk$ = get_circle;
  package$voronoi.createBeach_ryhnh2$ = createBeach;
  package$voronoi.detachBeach_rhr21$ = detachBeach;
  package$voronoi.addBeach_ryhnh2$ = addBeach;
  package$voronoi.removeBeach_6ucwwu$ = removeBeach;
  package$voronoi.leftBreakPoint_cqf8ex$ = leftBreakPoint;
  package$voronoi.rightBreakPoint_cqf8ex$ = rightBreakPoint;
  Object.defineProperty(package$voronoi, 'wCells_8be2vx$', {
    get: function () {
      return wCells;
    },
    set: function (value) {
      wCells = value;
    }
  });
  Object.defineProperty(Cell, 'Companion', {
    get: Cell$Companion_getInstance
  });
  package$voronoi.Cell = Cell;
  package$voronoi.sortCellHalfedges = sortCellHalfedges;
  Object.defineProperty(package$voronoi, 'firstCircle_8be2vx$', {
    get: function () {
      return firstCircle;
    },
    set: function (value) {
      firstCircle = value;
    }
  });
  Object.defineProperty(package$voronoi, 'circles_8be2vx$', {
    get: function () {
      return circles;
    }
  });
  Object.defineProperty(package$voronoi, 'circlePool_8be2vx$', {
    get: function () {
      return circlePool;
    }
  });
  package$voronoi.Circle = Circle;
  package$voronoi.get_x_y7tot$ = get_x;
  package$voronoi.set_x_ginddb$ = set_x;
  package$voronoi.get_y_y7tot$ = get_y;
  package$voronoi.set_y_ginddb$ = set_y;
  package$voronoi.get_cy_y7tot$ = get_cy;
  package$voronoi.set_cy_ginddb$ = set_cy;
  package$voronoi.get_site_y7tot$ = get_site_0;
  package$voronoi.set_site_aeii2x$ = set_site;
  package$voronoi.attachCircle_rhr21$ = attachCircle;
  package$voronoi.detachCircle_rhr21$ = detachCircle;
  package$voronoi.pop_vvxzk3$ = pop;
  Diagram.Link = Diagram$Link;
  package$voronoi.Diagram = Diagram;
  package$voronoi.Triangle = Triangle;
  Object.defineProperty(package$voronoi, 'wEdges_8be2vx$', {
    get: function () {
      return wEdges;
    },
    set: function (value) {
      wEdges = value;
    }
  });
  package$voronoi.createBorderEdge_yqgvg5$ = createBorderEdge;
  Object.defineProperty(Edge, 'Companion', {
    get: Edge$Companion_getInstance
  });
  package$voronoi.Edge = Edge;
  package$voronoi.RedBlackTree = RedBlackTree;
  package$voronoi.first_u76nco$ = first;
  Object.defineProperty(RedBlackColor, 'RED', {
    get: RedBlackColor$RED_getInstance
  });
  Object.defineProperty(RedBlackColor, 'BLACK', {
    get: RedBlackColor$BLACK_getInstance
  });
  package$voronoi.RedBlackColor = RedBlackColor;
  package$voronoi.RedBlackNode = RedBlackNode;
  package$voronoi.Site = Site;
  epsilon = 1.0E-6;
  epsilon2 = 1.0E-12;
  beaches = new RedBlackTree();
  beachPool = ArrayList_init();
  wCells = null;
  firstCircle = null;
  circles = new RedBlackTree();
  circlePool = ArrayList_init();
  wEdges = ArrayList_init();
  Kotlin.defineModule('d2v-voronoi-js', _);
  return _;
}));

//# sourceMappingURL=d2v-voronoi-js.js.map
