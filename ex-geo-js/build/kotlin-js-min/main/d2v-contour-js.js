(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'd2v-contour-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'd2v-contour-js'.");
    }
    root['d2v-contour-js'] = factory(typeof this['d2v-contour-js'] === 'undefined' ? {} : this['d2v-contour-js'], kotlin);
  }
}(this, function (_, Kotlin) {
  'use strict';
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var sortedArray = Kotlin.kotlin.collections.sortedArray_j2hqw1$;
  var mutableListOf = Kotlin.kotlin.collections.mutableListOf_i5x0yv$;
  var Unit = Kotlin.kotlin.Unit;
  var List = Kotlin.kotlin.collections.List;
  var throwCCE = Kotlin.throwCCE;
  var numberToInt = Kotlin.numberToInt;
  var plus = Kotlin.kotlin.collections.plus_mydzjv$;
  var toMutableList = Kotlin.kotlin.collections.toMutableList_4c7yge$;
  var ensureNotNull = Kotlin.ensureNotNull;
  var rangeTo = Kotlin.kotlin.ranges.rangeTo_38ydlf$;
  var IllegalStateException_init = Kotlin.kotlin.IllegalStateException_init_pdl1vj$;
  var copyToArray = Kotlin.kotlin.collections.copyToArray;
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  var Array_0 = Array;
  var ArrayList_init_0 = Kotlin.kotlin.collections.ArrayList_init_ww73n8$;
  var collectionSizeOrDefault = Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$;
  var checkIndexOverflow = Kotlin.kotlin.collections.checkIndexOverflow_za3lpa$;
  function GeoJson(type, value, coordinates) {
    this.type = type;
    this.value = value;
    this.coordinates = coordinates;
  }
  GeoJson.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'GeoJson',
    interfaces: []
  };
  GeoJson.prototype.component1 = function () {
    return this.type;
  };
  GeoJson.prototype.component2 = function () {
    return this.value;
  };
  GeoJson.prototype.component3 = function () {
    return this.coordinates;
  };
  GeoJson.prototype.copy_tn4y3i$ = function (type, value, coordinates) {
    return new GeoJson(type === void 0 ? this.type : type, value === void 0 ? this.value : value, coordinates === void 0 ? this.coordinates : coordinates);
  };
  GeoJson.prototype.toString = function () {
    return 'GeoJson(type=' + Kotlin.toString(this.type) + (', value=' + Kotlin.toString(this.value)) + (', coordinates=' + Kotlin.toString(this.coordinates)) + ')';
  };
  GeoJson.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.type) | 0;
    result = result * 31 + Kotlin.hashCode(this.value) | 0;
    result = result * 31 + Kotlin.hashCode(this.coordinates) | 0;
    return result;
  };
  GeoJson.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.type, other.type) && Kotlin.equals(this.value, other.value) && Kotlin.equals(this.coordinates, other.coordinates)))));
  };
  function contour(init) {
    var $receiver = new Contour();
    init($receiver);
    return $receiver;
  }
  var cases;
  function line(start, end) {
    return [start, end];
  }
  function pt(x, y) {
    return [x, y];
  }
  function Contour() {
    this.thresholds = Contour$thresholds$lambda;
    this.dx = 1;
    this.dy = 1;
  }
  Contour.prototype.size_vux9f0$ = function (dx, dy) {
    if (dx <= 0 || dy <= 0) {
      throw IllegalStateException_init('invalid size'.toString());
    }
    this.dx = dx;
    this.dy = dy;
  };
  function Contour$contours$lambda$lambda(closure$polygons, closure$holes) {
    return function (ring) {
      if (doubleArea(copyToArray(ring)) > 0)
        closure$polygons.add_11rb$(mutableListOf([ring]));
      else
        closure$holes.add_11rb$(ring);
      return Unit;
    };
  }
  Contour.prototype.contours_awc180$ = function (values) {
    if (values.length !== Kotlin.imul(this.dx, this.dy)) {
      throw IllegalStateException_init('Wrong value size'.toString());
    }
    var tz = sortedArray(this.thresholds(values));
    var destination = ArrayList_init_0(tz.length);
    var tmp$;
    for (tmp$ = 0; tmp$ !== tz.length; ++tmp$) {
      var item = tz[tmp$];
      var tmp$_0 = destination.add_11rb$;
      var tmp$_1;
      var polygons = ArrayList_init();
      var holes = ArrayList_init();
      this.isorings_0(values, item, Contour$contours$lambda$lambda(polygons, holes));
      var tmp$_2;
      tmp$_2 = holes.iterator();
      loop_label: while (tmp$_2.hasNext()) {
        var element = tmp$_2.next();
        action$break: do {
          var tmp$_3;
          tmp$_3 = polygons.size;
          for (var i = 0; i < tmp$_3; i++) {
            var polygon = polygons.get_za3lpa$(i);
            if (this.contains_0(polygon.get_za3lpa$(0), element) !== -1) {
              polygon.add_11rb$(element);
              break action$break;
            }
          }
        }
         while (false);
      }
      tmp$_0.call(destination, Kotlin.isType(tmp$_1 = polygons, List) ? tmp$_1 : throwCCE());
    }
    var layers = destination;
    var destination_0 = ArrayList_init_0(collectionSizeOrDefault(layers, 10));
    var tmp$_4, tmp$_0_0;
    var index = 0;
    tmp$_4 = layers.iterator();
    while (tmp$_4.hasNext()) {
      var item_0 = tmp$_4.next();
      var tmp$_5 = destination_0.add_11rb$;
      var index_0 = checkIndexOverflow((tmp$_0_0 = index, index = tmp$_0_0 + 1 | 0, tmp$_0_0));
      var array = Array_0(item_0.size);
      var tmp$_6;
      tmp$_6 = array.length - 1 | 0;
      for (var i_0 = 0; i_0 <= tmp$_6; i_0++) {
        var array_0 = Array_0(item_0.get_za3lpa$(i_0).size);
        var tmp$_7;
        tmp$_7 = array_0.length - 1 | 0;
        for (var i_1 = 0; i_1 <= tmp$_7; i_1++) {
          var array_1 = Array_0(item_0.get_za3lpa$(i_0).get_za3lpa$(i_1).size);
          var tmp$_8;
          tmp$_8 = array_1.length - 1 | 0;
          for (var i_2 = 0; i_2 <= tmp$_8; i_2++) {
            var pt = item_0.get_za3lpa$(i_0).get_za3lpa$(i_1).get_za3lpa$(i_2);
            array_1[i_2] = [get_x(pt), get_y(pt)];
          }
          array_0[i_1] = array_1;
        }
        array[i_0] = array_0;
      }
      var coordinates = array;
      tmp$_5.call(destination_0, new GeoJson('MultiPolygon', tz[index_0], coordinates));
    }
    return destination_0;
  };
  Contour.prototype.contains_0 = function (ring, hole) {
    var i = -1;
    var n = hole.size;
    while ((i = i + 1 | 0, i) < n) {
      var c = ringContains(ring, hole.get_za3lpa$(i));
      if (c !== 0)
        return c;
    }
    return 0;
  };
  function Contour$Fragment(start, end, ring) {
    this.start = start;
    this.end = end;
    this.ring = ring;
  }
  Contour$Fragment.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Fragment',
    interfaces: []
  };
  function Contour$isorings$index(this$Contour) {
    return function (point) {
      return numberToInt(get_x(point) * 2 + get_y(point) * (this$Contour.dx + 1 | 0) * 4);
    };
  }
  function Contour$isorings$threshold(closure$values, closure$thresold) {
    return function (index) {
      return closure$values[index] >= closure$thresold;
    };
  }
  function Contour$isorings$shl($receiver, bitCount) {
    if (bitCount === void 0)
      bitCount = 0;
    return ($receiver ? 1 : 0) << bitCount;
  }
  function Contour$isorings$stitch(closure$x, closure$y, closure$index, closure$fragmentByEnd, closure$fragmentByStart, closure$callback) {
    return function (line) {
      var tmp$, tmp$_0;
      var start = pt(get_x(get_start(line)) + closure$x.v, get_y(get_start(line)) + closure$y.v);
      var end = pt(get_x(get_end(line)) + closure$x.v, get_y(get_end(line)) + closure$y.v);
      var startIndex = closure$index(start);
      var endIndex = closure$index(end);
      var f = {v: closure$fragmentByEnd[startIndex]};
      var g = {v: closure$fragmentByStart[endIndex]};
      if (f.v != null) {
        if (g.v != null) {
          closure$fragmentByEnd[f.v.end] = null;
          closure$fragmentByStart[g.v.start] = null;
          if (f.v === g.v) {
            f.v.ring.add_11rb$(end);
            closure$callback(f.v.ring);
          }
           else {
            var startEnd = new Contour$Fragment(f.v.start, g.v.end, toMutableList(plus(f.v.ring, g.v.ring)));
            closure$fragmentByStart[f.v.start] = startEnd;
            closure$fragmentByStart[g.v.end] = startEnd;
          }
        }
         else {
          closure$fragmentByEnd[f.v.end] = null;
          f.v.ring.add_11rb$(end);
          f.v.end = endIndex;
          closure$fragmentByEnd[endIndex] = f.v;
        }
      }
       else {
        var tmp$_1;
        if ((tmp$ = closure$fragmentByStart[endIndex]) != null) {
          f.v = tmp$;
          tmp$_1 = true;
        }
         else
          tmp$_1 = null;
        if (tmp$_1 === true) {
          var tmp$_2;
          if ((tmp$_0 = closure$fragmentByEnd[startIndex]) != null) {
            g.v = tmp$_0;
            tmp$_2 = true;
          }
           else
            tmp$_2 = null;
          if (tmp$_2 === true) {
            closure$fragmentByStart[ensureNotNull(f.v).start] = null;
            closure$fragmentByEnd[ensureNotNull(g.v).start] = null;
            if (f.v === g.v) {
              ensureNotNull(f.v).ring.add_11rb$(end);
              closure$callback(ensureNotNull(f.v).ring);
            }
             else {
              var startEnd_0 = new Contour$Fragment(ensureNotNull(g.v).start, ensureNotNull(f.v).end, toMutableList(plus(ensureNotNull(g.v).ring, ensureNotNull(f.v).ring)));
              closure$fragmentByStart[ensureNotNull(g.v).start] = startEnd_0;
              closure$fragmentByEnd[ensureNotNull(f.v).end] = startEnd_0;
            }
          }
           else {
            closure$fragmentByStart[ensureNotNull(f.v).start] = null;
            ensureNotNull(f.v).ring.add_wxm5ur$(0, start);
            ensureNotNull(f.v).start = startIndex;
            closure$fragmentByStart[startIndex] = ensureNotNull(f.v);
          }
        }
         else {
          var startEnd_1 = new Contour$Fragment(startIndex, endIndex, mutableListOf([start, end]));
          closure$fragmentByStart[startIndex] = startEnd_1;
          closure$fragmentByEnd[endIndex] = startEnd_1;
        }
      }
    };
  }
  Contour.prototype.isorings_0 = function (values, thresold, callback) {
    var t0;
    var t1;
    var t2;
    var t3;
    var index = Contour$isorings$index(this);
    var maxSize = index([this.dx, this.dy]);
    var fragmentByStart = Kotlin.newArray(maxSize, null);
    var fragmentByEnd = Kotlin.newArray(maxSize, null);
    var x = {v: -1};
    var y = {v: -1};
    var threshold = Contour$isorings$threshold(values, thresold);
    var shl = Contour$isorings$shl;
    var stitch = Contour$isorings$stitch(x, y, index, fragmentByEnd, fragmentByStart, callback);
    t1 = threshold(0);
    var $receiver = cases[shl(t1, 1)];
    var tmp$;
    for (tmp$ = 0; tmp$ !== $receiver.length; ++tmp$) {
      var element = $receiver[tmp$];
      stitch(element);
    }
    while ((x.v = x.v + 1 | 0, x.v) < (this.dx - 1 | 0)) {
      t0 = t1;
      t1 = threshold(x.v + 1 | 0);
      var $receiver_0 = cases[shl(t0) | shl(t1, 1)];
      var tmp$_0;
      for (tmp$_0 = 0; tmp$_0 !== $receiver_0.length; ++tmp$_0) {
        var element_0 = $receiver_0[tmp$_0];
        stitch(element_0);
      }
    }
    var $receiver_1 = cases[shl(t1)];
    var tmp$_1;
    for (tmp$_1 = 0; tmp$_1 !== $receiver_1.length; ++tmp$_1) {
      var element_1 = $receiver_1[tmp$_1];
      stitch(element_1);
    }
    while ((y.v = y.v + 1 | 0, y.v) < (this.dy - 1 | 0)) {
      x.v = -1;
      t1 = threshold(Kotlin.imul(y.v, this.dx) + this.dx | 0);
      t2 = threshold(Kotlin.imul(y.v, this.dx));
      var $receiver_2 = cases[shl(t1, 1) | shl(t2, 2)];
      var tmp$_2;
      for (tmp$_2 = 0; tmp$_2 !== $receiver_2.length; ++tmp$_2) {
        var element_2 = $receiver_2[tmp$_2];
        stitch(element_2);
      }
      while ((x.v = x.v + 1 | 0, x.v) < (this.dx - 1 | 0)) {
        t0 = t1;
        t1 = threshold(Kotlin.imul(y.v, this.dx) + this.dx + x.v + 1 | 0);
        t3 = t2;
        t2 = threshold(Kotlin.imul(y.v, this.dx) + x.v + 1 | 0);
        var $receiver_3 = cases[shl(t0) | shl(t1, 1) | shl(t2, 2) | shl(t3, 3)];
        var tmp$_3;
        for (tmp$_3 = 0; tmp$_3 !== $receiver_3.length; ++tmp$_3) {
          var element_3 = $receiver_3[tmp$_3];
          stitch(element_3);
        }
      }
      var $receiver_4 = cases[shl(t1) | shl(t2, 3)];
      var tmp$_4;
      for (tmp$_4 = 0; tmp$_4 !== $receiver_4.length; ++tmp$_4) {
        var element_4 = $receiver_4[tmp$_4];
        stitch(element_4);
      }
    }
    x.v = -1;
    t2 = threshold(Kotlin.imul(y.v, this.dx));
    var $receiver_5 = cases[shl(t2, 2)];
    var tmp$_5;
    for (tmp$_5 = 0; tmp$_5 !== $receiver_5.length; ++tmp$_5) {
      var element_5 = $receiver_5[tmp$_5];
      stitch(element_5);
    }
    while ((x.v = x.v + 1 | 0, x.v) < (this.dx - 1 | 0)) {
      t3 = t2;
      t2 = threshold(Kotlin.imul(y.v, this.dx) + x.v + 1 | 0);
      var $receiver_6 = cases[shl(t2, 2) | shl(t3, 3)];
      var tmp$_6;
      for (tmp$_6 = 0; tmp$_6 !== $receiver_6.length; ++tmp$_6) {
        var element_6 = $receiver_6[tmp$_6];
        stitch(element_6);
      }
    }
    var $receiver_7 = cases[shl(t2, 3)];
    var tmp$_7;
    for (tmp$_7 = 0; tmp$_7 !== $receiver_7.length; ++tmp$_7) {
      var element_7 = $receiver_7[tmp$_7];
      stitch(element_7);
    }
  };
  Contour.prototype.smoothLinear_ho5km6$ = function (ring, values, value) {
    var tmp$;
    tmp$ = ring.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      var x = element[0];
      var y = element[1];
      var xt = x !== 0.0 ? x : 0.0;
      var yt = y !== 0.0 ? y : 0.0;
    }
  };
  function Contour$thresholds$lambda(it) {
    return [];
  }
  Contour.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Contour',
    interfaces: []
  };
  function doubleArea(ring) {
    var i = 0;
    var n = ring.length;
    var area = ring[n - 1 | 0][1] * ring[0][0] - ring[n - 1 | 0][0] * ring[0][1];
    while ((i = i + 1 | 0, i) < n)
      area += ring[i - 1 | 0][1] * ring[i][0] - ring[i - 1 | 0][0] * ring[i][1];
    return area;
  }
  function ringContains(ring, point) {
    var tmp$;
    var x = point[0];
    var y = point[1];
    var contains = -1;
    var n = ring.size;
    var j = n - 1 | 0;
    var i = 0;
    do {
      var pi = ring.get_za3lpa$(i);
      var xi = pi[0];
      var yi = pi[1];
      var pj = ring.get_za3lpa$(j);
      var xj = pj[0];
      var yj = pj[1];
      if (segmentContains(pi, pj, point))
        return 0;
      if (yi > y !== yj > y && x < (xj - xi) * (y - yi) / (yj - yi) + xi)
        contains = -contains | 0;
      j = (tmp$ = i, i = tmp$ + 1 | 0, tmp$);
    }
     while (i < n);
    return contains;
  }
  function segmentContains(start, end, point) {
    var i = start[0] === end[0] ? 1 : 0;
    return collinear(start, end, point) && within(start[i], point[i], end[i]);
  }
  function within(p, q, r) {
    return rangeTo(p, r).contains_mef7kx$(q) || rangeTo(r, p).contains_mef7kx$(q);
  }
  function collinear(a, b, c) {
    return (b[0] - a[0]) * (c[1] - a[1]) === (c[0] - a[0]) * (b[1] - a[1]);
  }
  function get_x($receiver) {
    return $receiver[0];
  }
  function get_y($receiver) {
    return $receiver[1];
  }
  function get_start($receiver) {
    return $receiver[0];
  }
  function get_end($receiver) {
    return $receiver[1];
  }
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$contour = package$data2viz.contour || (package$data2viz.contour = {});
  package$contour.GeoJson = GeoJson;
  package$contour.contour_h4umdu$ = contour;
  Object.defineProperty(package$contour, 'cases', {
    get: function () {
      return cases;
    }
  });
  package$contour.line_8efa2c$ = line;
  package$contour.pt_lu1900$ = pt;
  package$contour.Contour = Contour;
  package$contour.doubleArea_4p5xvi$ = doubleArea;
  package$contour.ringContains_ufjx6t$ = ringContains;
  package$contour.segmentContains_xzvv3s$ = segmentContains;
  package$contour.within_yvo9jy$ = within;
  package$contour.collinear_xzvv3s$ = collinear;
  cases = [[], [line(pt(1.0, 1.5), pt(0.5, 1.0))], [line(pt(1.5, 1.0), pt(1.0, 1.5))], [line(pt(1.5, 1.0), pt(0.5, 1.0))], [line(pt(1.0, 0.5), pt(1.5, 1.0))], [line(pt(1.0, 0.5), pt(0.5, 1.0)), line(pt(1.0, 1.5), pt(1.5, 1.0))], [line(pt(1.0, 0.5), pt(1.0, 1.5))], [line(pt(1.0, 0.5), pt(0.5, 1.0))], [line(pt(0.5, 1.0), pt(1.0, 0.5))], [line(pt(1.0, 1.5), pt(1.0, 0.5))], [line(pt(0.5, 1.0), pt(1.0, 1.5)), line(pt(1.5, 1.0), pt(1.0, 0.5))], [line(pt(1.5, 1.0), pt(1.0, 0.5))], [line(pt(0.5, 1.0), pt(1.5, 1.0))], [line(pt(1.0, 1.5), pt(1.5, 1.0))], [line(pt(0.5, 1.0), pt(1.0, 1.5))], []];
  Kotlin.defineModule('d2v-contour-js', _);
  return _;
}));

//# sourceMappingURL=d2v-contour-js.js.map
