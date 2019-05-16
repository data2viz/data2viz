(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'd2v-delaunay-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'd2v-delaunay-js'.");
    }
    root['d2v-delaunay-js'] = factory(typeof this['d2v-delaunay-js'] === 'undefined' ? {} : this['d2v-delaunay-js'], kotlin);
  }
}(this, function (_, Kotlin) {
  'use strict';
  var $$importsForInline$$ = _.$$importsForInline$$ || (_.$$importsForInline$$ = {});
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var Kind_INTERFACE = Kotlin.Kind.INTERFACE;
  var numberToInt = Kotlin.numberToInt;
  var kotlin_js_internal_DoubleCompanionObject = Kotlin.kotlin.js.internal.DoubleCompanionObject;
  var ensureNotNull = Kotlin.ensureNotNull;
  var Error_init = Kotlin.kotlin.Error_init_pdl1vj$;
  var defineInlineFunction = Kotlin.defineInlineFunction;
  var wrapFunction = Kotlin.wrapFunction;
  function Point(x, y) {
    this.x = x;
    this.y = y;
  }
  Point.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Point',
    interfaces: []
  };
  Point.prototype.component1 = function () {
    return this.x;
  };
  Point.prototype.component2 = function () {
    return this.y;
  };
  Point.prototype.copy_lu1900$ = function (x, y) {
    return new Point(x === void 0 ? this.x : x, y === void 0 ? this.y : y);
  };
  Point.prototype.toString = function () {
    return 'Point(x=' + Kotlin.toString(this.x) + (', y=' + Kotlin.toString(this.y)) + ')';
  };
  Point.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.x) | 0;
    result = result * 31 + Kotlin.hashCode(this.y) | 0;
    return result;
  };
  Point.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.x, other.x) && Kotlin.equals(this.y, other.y)))));
  };
  function TypedUIntArray() {
  }
  TypedUIntArray.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'TypedUIntArray',
    interfaces: []
  };
  function TypedIntArray() {
  }
  TypedIntArray.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'TypedIntArray',
    interfaces: []
  };
  var Array_0 = Array;
  var IllegalArgumentException_init = Kotlin.kotlin.IllegalArgumentException_init_pdl1vj$;
  var Math_0 = Math;
  function Delaunator(points) {
    this.getX = Delaunator$getX$lambda;
    this.getY = Delaunator$getY$lambda;
    var array = Array_0(points.length * 2 | 0);
    var tmp$;
    tmp$ = array.length - 1 | 0;
    for (var i = 0; i <= tmp$; i++) {
      array[i] = 0.0;
    }
    this.coords_0 = array;
    this._cx_0 = 0;
    this._cy_0 = 0;
    this._hashSize_0 = 0;
    this.hull = null;
    this._hash_0 = null;
    this.triangles = null;
    this.halfedges_0 = null;
    this.trianglesLen_0 = 0;
    var tmp$_0;
    var minX = kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY;
    var minY = kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY;
    var maxX = kotlin_js_internal_DoubleCompanionObject.NEGATIVE_INFINITY;
    var maxY = kotlin_js_internal_DoubleCompanionObject.NEGATIVE_INFINITY;
    var ids = typedIntArray(points.length);
    for (var i_0 = 0; i_0 < points.length; i_0++) {
      var p = points[i_0];
      var x = this.getX(p);
      var y = this.getY(p);
      ids.set_vux9f0$(i_0, i_0);
      this.coords_0[2 * i_0 | 0] = x;
      this.coords_0[(2 * i_0 | 0) + 1 | 0] = y;
      if (x < minX)
        minX = x;
      if (y < minY)
        minY = y;
      if (x > maxX)
        maxX = x;
      if (y > maxY)
        maxY = y;
    }
    var cx = (minX + maxX) / 2;
    var cy = (minY + maxY) / 2;
    var minDist = kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY;
    var i0 = -1;
    var i1 = -1;
    var i2 = -1;
    for (var i_1 = 0; i_1 < points.length; i_1++) {
      var d = dist(cx, cy, this.coords_0[2 * i_1 | 0], this.coords_0[(2 * i_1 | 0) + 1 | 0]);
      if (d < minDist) {
        i0 = i_1;
        minDist = d;
      }
    }
    minDist = kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY;
    for (var i_2 = 0; i_2 < points.length; i_2++) {
      if (i_2 === i0)
        continue;
      var d_0 = dist(this.coords_0[2 * i0 | 0], this.coords_0[(2 * i0 | 0) + 1 | 0], this.coords_0[2 * i_2 | 0], this.coords_0[(2 * i_2 | 0) + 1 | 0]);
      if (d_0 < minDist && d_0 > 0) {
        i1 = i_2;
        minDist = d_0;
      }
    }
    var minRadius = kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY;
    for (var i_3 = 0; i_3 < points.length; i_3++) {
      if (i_3 === i0 || i_3 === i1)
        continue;
      var r = circumradius(this.coords_0[2 * i0 | 0], this.coords_0[(2 * i0 | 0) + 1 | 0], this.coords_0[2 * i1 | 0], this.coords_0[(2 * i1 | 0) + 1 | 0], this.coords_0[2 * i_3 | 0], this.coords_0[(2 * i_3 | 0) + 1 | 0]);
      if (r < minRadius) {
        i2 = i_3;
        minRadius = r;
      }
    }
    if (!(minRadius !== kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY)) {
      var message = 'No Delaunay triangulation exists for this input.';
      throw IllegalArgumentException_init(message.toString());
    }
    if (area(this.coords_0[2 * i0 | 0], this.coords_0[(2 * i0 | 0) + 1 | 0], this.coords_0[2 * i1 | 0], this.coords_0[(2 * i1 | 0) + 1 | 0], this.coords_0[2 * i2 | 0], this.coords_0[(2 * i2 | 0) + 1 | 0]) < 0) {
      var tmp = i1;
      i1 = i2;
      i2 = tmp;
    }
    var i0x = this.coords_0[2 * i0 | 0];
    var i0y = this.coords_0[(2 * i0 | 0) + 1 | 0];
    var i1x = this.coords_0[2 * i1 | 0];
    var i1y = this.coords_0[(2 * i1 | 0) + 1 | 0];
    var i2x = this.coords_0[2 * i2 | 0];
    var i2y = this.coords_0[(2 * i2 | 0) + 1 | 0];
    var center = circumcenter(i0x, i0y, i1x, i1y, i2x, i2y);
    this._cx_0 = center.x;
    this._cy_0 = center.y;
    quicksort(ids, this.coords_0, 0, ids.length - 1 | 0, center.x, center.y);
    var x_0 = Math_0.sqrt(points.length);
    this._hashSize_0 = numberToInt(Math_0.ceil(x_0));
    this._hash_0 = Kotlin.newArray(this._hashSize_0, null);
    this.hull = insertNode(this.coords_0, i0);
    var e = this.hull;
    this.hashEdge_0(e);
    e.t = 0;
    e = insertNode(this.coords_0, i1, e);
    this.hashEdge_0(e);
    e.t = 1;
    e = insertNode(this.coords_0, i2, e);
    this.hashEdge_0(e);
    e.t = 2;
    var maxTriangles = (2 * points.length | 0) - 5 | 0;
    var triangles = typedIntArray(maxTriangles * 3 | 0);
    var halfedges = typedIntArray(maxTriangles * 3 | 0);
    this.halfedges_0 = halfedges;
    this.triangles = triangles;
    this.trianglesLen_0 = 0;
    this.addTriangle_0(i0, i1, i2, -1, -1, -1);
    var xp = kotlin_js_internal_DoubleCompanionObject.NEGATIVE_INFINITY;
    var yp = kotlin_js_internal_DoubleCompanionObject.NEGATIVE_INFINITY;
    var i_4;
    var x_1;
    var y_0;
    tmp$_0 = ids.length;
    for (var k = 0; k < tmp$_0; k++) {
      i_4 = ids.get_za3lpa$(k);
      x_1 = this.coords_0[2 * i_4 | 0];
      y_0 = this.coords_0[(2 * i_4 | 0) + 1 | 0];
      if (x_1 === xp && y_0 === yp)
        continue;
      xp = x_1;
      yp = y_0;
      if (x_1 === i0x && y_0 === i0y || (x_1 === i1x && y_0 === i1y) || (x_1 === i2x && y_0 === i2y))
        continue;
      var startKey = this.hashKey_0(x_1, y_0);
      var key = startKey;
      var start;
      do {
        start = this._hash_0[key];
        key = (key + 1 | 0) % this._hashSize_0;
      }
       while ((start == null || start.removed) && key !== startKey);
      e = ensureNotNull(start);
      while (area(x_1, y_0, e.x, e.y, ensureNotNull(e.next).x, ensureNotNull(e.next).y) >= 0) {
        e = ensureNotNull(e.next);
        if (e === start) {
          throw Error_init('Something is wrong with the input points.');
        }
      }
      var walkBack = e === start;
      var t = this.addTriangle_0(e.i, i_4, ensureNotNull(e.next).i, -1, -1, e.t);
      e.t = t;
      e = insertNode(this.coords_0, i_4, e);
      e.t = this.legalize_0(t + 2 | 0);
      if (ensureNotNull(ensureNotNull(e.prev).prev).t === halfedges.get_za3lpa$(t + 1 | 0)) {
        ensureNotNull(ensureNotNull(e.prev).prev).t = t + 2 | 0;
      }
      var q = e.next;
      while (area(x_1, y_0, ensureNotNull(q).x, q.y, ensureNotNull(q.next).x, ensureNotNull(q.next).y) < 0) {
        t = this.addTriangle_0(q.i, i_4, ensureNotNull(q.next).i, ensureNotNull(q.prev).t, -1, q.t);
        ensureNotNull(q.prev).t = this.legalize_0(t + 2 | 0);
        this.hull = ensureNotNull(q.removeNode());
        q = q.next;
      }
      if (walkBack) {
        q = e.prev;
        while (area(x_1, y_0, ensureNotNull(ensureNotNull(q).prev).x, ensureNotNull(q.prev).y, q.x, q.y) < 0) {
          t = this.addTriangle_0(ensureNotNull(q.prev).i, i_4, q.i, -1, q.t, ensureNotNull(q.prev).t);
          this.legalize_0(t + 2 | 0);
          ensureNotNull(q.prev).t = t;
          this.hull = ensureNotNull(q.removeNode());
          q = q.prev;
        }
      }
      this.hashEdge_0(e);
      this.hashEdge_0(ensureNotNull(e.prev));
    }
    this.triangles = triangles.subarray_vux9f0$(0, this.trianglesLen_0);
    this.halfedges_0 = halfedges.subarray_vux9f0$(0, this.trianglesLen_0);
  }
  Delaunator.prototype.hashEdge_0 = function (e) {
    this._hash_0[this.hashKey_0(e.x, e.y)] = e;
  };
  Delaunator.prototype.hashKey_0 = function (x, y) {
    var dx = x - this._cx_0;
    var dy = y - this._cy_0;
    var p = 1 - dx / (Math_0.abs(dx) + Math_0.abs(dy));
    var x_0 = (2.0 + (dy < 0 ? -p : p)) / 4 * this._hashSize_0;
    return numberToInt(Math_0.floor(x_0));
  };
  Delaunator.prototype.legalize_0 = function (a) {
    var triangles = this.triangles;
    var coords = this.coords_0;
    var halfedges = this.halfedges_0;
    var b = halfedges.get_za3lpa$(a);
    var a0 = a - a % 3 | 0;
    var b0 = b - b % 3 | 0;
    var al = a0 + (a + 1 | 0) % 3 | 0;
    var ar = a0 + (a + 2 | 0) % 3 | 0;
    var bl = b0 + (b + 2 | 0) % 3 | 0;
    var p0 = triangles.get_za3lpa$(ar);
    var pr = triangles.get_za3lpa$(a);
    var pl = triangles.get_za3lpa$(al);
    var p1 = triangles.get_za3lpa$(bl);
    var illegal = inCircle(coords[2 * p0 | 0], coords[(2 * p0 | 0) + 1 | 0], coords[2 * pr | 0], coords[(2 * pr | 0) + 1 | 0], coords[2 * pl | 0], coords[(2 * pl | 0) + 1 | 0], coords[2 * p1 | 0], coords[(2 * p1 | 0) + 1 | 0]);
    if (illegal) {
      triangles.set_vux9f0$(a, p1);
      triangles.set_vux9f0$(b, p0);
      this.link_0(a, halfedges.get_za3lpa$(bl));
      this.link_0(b, halfedges.get_za3lpa$(ar));
      this.link_0(ar, bl);
      var br = b0 + (b + 1 | 0) % 3 | 0;
      this.legalize_0(a);
      return this.legalize_0(br);
    }
    return ar;
  };
  Delaunator.prototype.link_0 = function (a, b) {
    this.halfedges_0.set_vux9f0$(a, b);
    if (b !== -1)
      this.halfedges_0.set_vux9f0$(b, a);
  };
  Delaunator.prototype.addTriangle_0 = function (i0, i1, i2, a, b, c) {
    var t = this.trianglesLen_0;
    this.triangles.set_vux9f0$(t, i0);
    this.triangles.set_vux9f0$(t + 1 | 0, i1);
    this.triangles.set_vux9f0$(t + 2 | 0, i2);
    this.link_0(t, a);
    this.link_0(t + 1 | 0, b);
    this.link_0(t + 2 | 0, c);
    this.trianglesLen_0 = this.trianglesLen_0 + 3 | 0;
    return t;
  };
  function Delaunator$getX$lambda(point) {
    return point[0];
  }
  function Delaunator$getY$lambda(point) {
    return point[1];
  }
  Delaunator.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Delaunator',
    interfaces: []
  };
  function Node(i, x, y, t, prev, next, removed) {
    this.i = i;
    this.x = x;
    this.y = y;
    this.t = t;
    this.prev = prev;
    this.next = next;
    this.removed = removed;
  }
  Node.prototype.removeNode = function () {
    var tmp$, tmp$_0;
    (tmp$ = this.prev) != null ? (tmp$.next = this.next) : null;
    (tmp$_0 = this.next) != null ? (tmp$_0.prev = this.prev) : null;
    this.removed = true;
    return this.prev;
  };
  Node.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Node',
    interfaces: []
  };
  function insertNode(coords, i, prev) {
    if (prev === void 0)
      prev = null;
    var tmp$;
    var node = new Node(i, coords[2 * i | 0], coords[(2 * i | 0) + 1 | 0], 0, null, null, false);
    if (prev == null) {
      node.prev = node;
      node.next = node;
    }
     else {
      node.next = prev.next;
      node.prev = prev;
      (tmp$ = prev.next) != null ? (tmp$.prev = node) : null;
      prev.next = node;
    }
    return node;
  }
  function area(ax, ay, bx, by, cx, cy) {
    return (by - ay) * (cx - bx) - (bx - ax) * (cy - by);
  }
  function inCircle(ax, ay, bx, by, cx, cy, px, py) {
    var pax = ax - px;
    var pay = ay - py;
    var pbx = bx - px;
    var pby = by - py;
    var pcx = cx - px;
    var pcy = cy - py;
    var ap = pax * pax + pay * pay;
    var bp = pbx * pbx + pby * pby;
    var cp = pcx * pcx + pcy * pcy;
    return pax * (pby * cp - bp * pcy) - pay * (pbx * cp - bp * pcx) + ap * (pbx * pcy - pby * pcx) < 0;
  }
  function circumradius(ax, ay, bx, by, cx, cy) {
    var abx = bx - ax;
    var aby = by - ay;
    var acx = cx - ax;
    var acy = cy - ay;
    var bl = abx * abx + aby * aby;
    var cl = acx * acx + acy * acy;
    if (bl === 0.0 || cl === 0.0)
      return kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY;
    var d = abx * acy - aby * acx;
    if (d === 0.0)
      return kotlin_js_internal_DoubleCompanionObject.POSITIVE_INFINITY;
    var x = (acy * bl - aby * cl) * 0.5 / d;
    var y = (abx * cl - acx * bl) * 0.5 / d;
    return x * x + y * y;
  }
  function circumcenter(ax, ay, bx, by, cx, cy) {
    var abx = bx - ax;
    var aby = by - ay;
    var acx = cx - ax;
    var acy = cy - ay;
    var bl = abx * abx + aby * aby;
    var cl = acx * acx + acy * acy;
    var d = abx * acy - aby * acx;
    var x = (acy * bl - aby * cl) * 0.5 / d;
    var y = (abx * cl - acx * bl) * 0.5 / d;
    return new Point(ax + x, ay + y);
  }
  var isFalsy = defineInlineFunction('d2v-delaunay-js.io.data2viz.delaunay.isFalsy_ck760i$', wrapFunction(function () {
    var isNaN_0 = Kotlin.kotlin.isNaN_yrwdxr$;
    return function ($receiver) {
      return $receiver == null || $receiver === -0.0 || $receiver === 0.0 || isNaN_0($receiver);
    };
  }));
  var orNull = defineInlineFunction('d2v-delaunay-js.io.data2viz.delaunay.orNull_ck760i$', wrapFunction(function () {
    var isNaN_0 = Kotlin.kotlin.isNaN_yrwdxr$;
    return function ($receiver) {
      return $receiver == null || $receiver === -0.0 || $receiver === 0.0 || isNaN_0($receiver) ? null : $receiver;
    };
  }));
  var compare = wrapFunction(function () {
    var isNaN_0 = Kotlin.kotlin.isNaN_yrwdxr$;
    return function (coords, i, j, cx, cy) {
      var tmp$, tmp$_0;
      var d1 = dist(coords[2 * i | 0], coords[(2 * i | 0) + 1 | 0], cx, cy);
      var d2 = dist(coords[2 * j | 0], coords[(2 * j | 0) + 1 | 0], cx, cy);
      var $receiver = d1 - d2;
      return (tmp$_0 = (tmp$ = $receiver == null || $receiver === -0.0 || $receiver === 0.0 || isNaN_0($receiver) ? null : $receiver) != null ? tmp$ : coords[2 * i | 0] - coords[2 * j | 0]) != null ? tmp$_0 : coords[(2 * i | 0) + 1 | 0] - coords[(2 * j | 0) + 1 | 0];
    };
  });
  var isNaN_0 = Kotlin.kotlin.isNaN_yrwdxr$;
  function quicksort(ids, coords, left, right, cx, cy) {
    var tmp$;
    var j;
    var temp;
    if ((right - left | 0) <= 20) {
      for (var i = left + 1 | 0; i <= right; i++) {
        temp = ids.get_za3lpa$(i);
        j = i - 1 | 0;
        while (true) {
          var tmp$_0 = j >= left;
          if (tmp$_0) {
            var i_0 = ids.get_za3lpa$(j);
            var j_0 = temp;
            var tmp$_1, tmp$_2;
            var d1 = dist(coords[2 * i_0 | 0], coords[(2 * i_0 | 0) + 1 | 0], cx, cy);
            var d2 = dist(coords[2 * j_0 | 0], coords[(2 * j_0 | 0) + 1 | 0], cx, cy);
            var $receiver = d1 - d2;
            tmp$_0 = ((tmp$_2 = (tmp$_1 = $receiver == null || $receiver === -0.0 || $receiver === 0.0 || isNaN_0($receiver) ? null : $receiver) != null ? tmp$_1 : coords[2 * i_0 | 0] - coords[2 * j_0 | 0]) != null ? tmp$_2 : coords[(2 * i_0 | 0) + 1 | 0] - coords[(2 * j_0 | 0) + 1 | 0]) > 0;
          }
          if (!tmp$_0)
            break;
          ids.set_vux9f0$(j + 1 | 0, ids.get_za3lpa$((tmp$ = j, j = tmp$ - 1 | 0, tmp$)));
        }
        ids.set_vux9f0$(j + 1 | 0, temp);
      }
    }
     else {
      var i_1 = left + 1 | 0;
      var median = numberToInt(0.5 * (left + right | 0));
      j = right;
      swap(ids, median, i_1);
      var i_2 = ids.get_za3lpa$(left);
      var j_1 = ids.get_za3lpa$(right);
      var tmp$_3, tmp$_4;
      var d1_0 = dist(coords[2 * i_2 | 0], coords[(2 * i_2 | 0) + 1 | 0], cx, cy);
      var d2_0 = dist(coords[2 * j_1 | 0], coords[(2 * j_1 | 0) + 1 | 0], cx, cy);
      var $receiver_0 = d1_0 - d2_0;
      if (((tmp$_4 = (tmp$_3 = $receiver_0 == null || $receiver_0 === -0.0 || $receiver_0 === 0.0 || isNaN_0($receiver_0) ? null : $receiver_0) != null ? tmp$_3 : coords[2 * i_2 | 0] - coords[2 * j_1 | 0]) != null ? tmp$_4 : coords[(2 * i_2 | 0) + 1 | 0] - coords[(2 * j_1 | 0) + 1 | 0]) > 0)
        swap(ids, left, right);
      var i_3 = ids.get_za3lpa$(i_1);
      var j_2 = ids.get_za3lpa$(right);
      var tmp$_5, tmp$_6;
      var d1_1 = dist(coords[2 * i_3 | 0], coords[(2 * i_3 | 0) + 1 | 0], cx, cy);
      var d2_1 = dist(coords[2 * j_2 | 0], coords[(2 * j_2 | 0) + 1 | 0], cx, cy);
      var $receiver_1 = d1_1 - d2_1;
      if (((tmp$_6 = (tmp$_5 = $receiver_1 == null || $receiver_1 === -0.0 || $receiver_1 === 0.0 || isNaN_0($receiver_1) ? null : $receiver_1) != null ? tmp$_5 : coords[2 * i_3 | 0] - coords[2 * j_2 | 0]) != null ? tmp$_6 : coords[(2 * i_3 | 0) + 1 | 0] - coords[(2 * j_2 | 0) + 1 | 0]) > 0)
        swap(ids, i_1, right);
      var i_4 = ids.get_za3lpa$(left);
      var j_3 = ids.get_za3lpa$(i_1);
      var tmp$_7, tmp$_8;
      var d1_2 = dist(coords[2 * i_4 | 0], coords[(2 * i_4 | 0) + 1 | 0], cx, cy);
      var d2_2 = dist(coords[2 * j_3 | 0], coords[(2 * j_3 | 0) + 1 | 0], cx, cy);
      var $receiver_2 = d1_2 - d2_2;
      if (((tmp$_8 = (tmp$_7 = $receiver_2 == null || $receiver_2 === -0.0 || $receiver_2 === 0.0 || isNaN_0($receiver_2) ? null : $receiver_2) != null ? tmp$_7 : coords[2 * i_4 | 0] - coords[2 * j_3 | 0]) != null ? tmp$_8 : coords[(2 * i_4 | 0) + 1 | 0] - coords[(2 * j_3 | 0) + 1 | 0]) > 0)
        swap(ids, left, i_1);
      temp = ids.get_za3lpa$(i_1);
      while (true) {
        do {
          i_1 = i_1 + 1 | 0;
          var i_5 = ids.get_za3lpa$(i_1);
          var j_4 = temp;
          var compare$result;
          var tmp$_9, tmp$_10;
          var d1_3 = dist(coords[2 * i_5 | 0], coords[(2 * i_5 | 0) + 1 | 0], cx, cy);
          var d2_3 = dist(coords[2 * j_4 | 0], coords[(2 * j_4 | 0) + 1 | 0], cx, cy);
          var $receiver_3 = d1_3 - d2_3;
          compare$result = (tmp$_10 = (tmp$_9 = $receiver_3 == null || $receiver_3 === -0.0 || $receiver_3 === 0.0 || isNaN_0($receiver_3) ? null : $receiver_3) != null ? tmp$_9 : coords[2 * i_5 | 0] - coords[2 * j_4 | 0]) != null ? tmp$_10 : coords[(2 * i_5 | 0) + 1 | 0] - coords[(2 * j_4 | 0) + 1 | 0];
        }
         while (compare$result < 0);
        do {
          j = j - 1 | 0;
          var i_6 = ids.get_za3lpa$(j);
          var j_5 = temp;
          var compare$result_0;
          var tmp$_11, tmp$_12;
          var d1_4 = dist(coords[2 * i_6 | 0], coords[(2 * i_6 | 0) + 1 | 0], cx, cy);
          var d2_4 = dist(coords[2 * j_5 | 0], coords[(2 * j_5 | 0) + 1 | 0], cx, cy);
          var $receiver_4 = d1_4 - d2_4;
          compare$result_0 = (tmp$_12 = (tmp$_11 = $receiver_4 == null || $receiver_4 === -0.0 || $receiver_4 === 0.0 || isNaN_0($receiver_4) ? null : $receiver_4) != null ? tmp$_11 : coords[2 * i_6 | 0] - coords[2 * j_5 | 0]) != null ? tmp$_12 : coords[(2 * i_6 | 0) + 1 | 0] - coords[(2 * j_5 | 0) + 1 | 0];
        }
         while (compare$result_0 > 0);
        if (j < i_1)
          break;
        swap(ids, i_1, j);
      }
      ids.set_vux9f0$(left + 1 | 0, ids.get_za3lpa$(j));
      ids.set_vux9f0$(j, temp);
      if ((right - i_1 + 1 | 0) >= (j - left | 0)) {
        quicksort(ids, coords, i_1, right, cx, cy);
        quicksort(ids, coords, left, j - 1 | 0, cx, cy);
      }
       else {
        quicksort(ids, coords, left, j - 1 | 0, cx, cy);
        quicksort(ids, coords, i_1, right, cx, cy);
      }
    }
  }
  function dist(ax, ay, bx, by) {
    var dx = ax - bx;
    var dy = ay - by;
    return dx * dx + dy * dy;
  }
  function swap(arr, i, j) {
    var tmp = arr.get_za3lpa$(i);
    arr.set_vux9f0$(i, arr.get_za3lpa$(j));
    arr.set_vux9f0$(j, tmp);
  }
  function delaunautor(points) {
    return new Delaunator(points);
  }
  function typedIntArray(size) {
    return new TypedIntArrayDelegate(new Int32Array(size));
  }
  function TypedUIntArrayDelegate(delegate) {
    this.delegate = delegate;
  }
  Object.defineProperty(TypedUIntArrayDelegate.prototype, 'length', {
    get: function () {
      return this.delegate.length;
    }
  });
  TypedUIntArrayDelegate.prototype.set_vux9f0$ = function (i, value) {
    this.delegate[i] = value;
  };
  TypedUIntArrayDelegate.prototype.subarray_vux9f0$ = function (start, end) {
    return new TypedUIntArrayDelegate(this.delegate.subarray(start, end));
  };
  TypedUIntArrayDelegate.prototype.get_za3lpa$ = function (ar) {
    return this.delegate[ar];
  };
  TypedUIntArrayDelegate.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'TypedUIntArrayDelegate',
    interfaces: [TypedUIntArray]
  };
  function TypedIntArrayDelegate(delegate) {
    this.delegate = delegate;
  }
  Object.defineProperty(TypedIntArrayDelegate.prototype, 'length', {
    get: function () {
      return this.delegate.length;
    }
  });
  TypedIntArrayDelegate.prototype.set_vux9f0$ = function (i, value) {
    this.delegate[i] = value;
  };
  TypedIntArrayDelegate.prototype.subarray_vux9f0$ = function (start, end) {
    return new TypedIntArrayDelegate(this.delegate.subarray(start, end));
  };
  TypedIntArrayDelegate.prototype.get_za3lpa$ = function (ar) {
    return this.delegate[ar];
  };
  TypedIntArrayDelegate.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'TypedIntArrayDelegate',
    interfaces: [TypedIntArray]
  };
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$delaunay = package$data2viz.delaunay || (package$data2viz.delaunay = {});
  package$delaunay.Point = Point;
  package$delaunay.TypedUIntArray = TypedUIntArray;
  package$delaunay.TypedIntArray = TypedIntArray;
  package$delaunay.Delaunator = Delaunator;
  package$delaunay.Node = Node;
  package$delaunay.insertNode_fr0sa6$ = insertNode;
  package$delaunay.area_15yvbs$ = area;
  package$delaunay.inCircle_eovi6c$ = inCircle;
  package$delaunay.circumradius_15yvbs$ = circumradius;
  package$delaunay.circumcenter_15yvbs$ = circumcenter;
  package$delaunay.isFalsy_ck760i$ = isFalsy;
  $$importsForInline$$['d2v-delaunay-js'] = _;
  package$delaunay.orNull_ck760i$ = orNull;
  package$delaunay.quicksort_kra4lg$ = quicksort;
  package$delaunay.dist_6y0v78$ = dist;
  package$delaunay.delaunator = delaunautor;
  package$delaunay.typedIntArray_za3lpa$ = typedIntArray;
  package$delaunay.TypedUIntArrayDelegate = TypedUIntArrayDelegate;
  package$delaunay.TypedIntArrayDelegate = TypedIntArrayDelegate;
  Kotlin.defineModule('d2v-delaunay-js', _);
  return _;
}));

//# sourceMappingURL=d2v-delaunay-js.js.map
