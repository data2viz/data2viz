(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin', 'd2v-core-js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'), require('d2v-core-js'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'd2v-hexbin-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'd2v-hexbin-js'.");
    }
    if (typeof this['d2v-core-js'] === 'undefined') {
      throw new Error("Error loading module 'd2v-hexbin-js'. Its dependency 'd2v-core-js' was not found. Please, check whether 'd2v-core-js' is loaded prior to 'd2v-hexbin-js'.");
    }
    root['d2v-hexbin-js'] = factory(typeof this['d2v-hexbin-js'] === 'undefined' ? {} : this['d2v-hexbin-js'], kotlin, this['d2v-core-js']);
  }
}(this, function (_, Kotlin, $module$d2v_core_js) {
  'use strict';
  var IntRange = Kotlin.kotlin.ranges.IntRange;
  var math = $module$d2v_core_js.io.data2viz.math;
  var Point = $module$d2v_core_js.io.data2viz.geom.Point;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var isNaN_0 = Kotlin.kotlin.isNaN_yrwdxr$;
  var round = Kotlin.kotlin.math.round_14dthe$;
  var numberToInt = Kotlin.numberToInt;
  var mutableListOf = Kotlin.kotlin.collections.mutableListOf_i5x0yv$;
  var until = Kotlin.kotlin.ranges.until_dqglrj$;
  var Extent = $module$d2v_core_js.io.data2viz.geom.Extent;
  var Math_0 = Math;
  var hexagon;
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  function Bin(points, x, y) {
    if (points === void 0) {
      points = ArrayList_init();
    }
    this.points = points;
    this.x = x;
    this.y = y;
  }
  Bin.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Bin',
    interfaces: []
  };
  Bin.prototype.component1 = function () {
    return this.points;
  };
  Bin.prototype.component2 = function () {
    return this.x;
  };
  Bin.prototype.component3 = function () {
    return this.y;
  };
  Bin.prototype.copy_4yidlr$ = function (points, x, y) {
    return new Bin(points === void 0 ? this.points : points, x === void 0 ? this.x : x, y === void 0 ? this.y : y);
  };
  Bin.prototype.toString = function () {
    return 'Bin(points=' + Kotlin.toString(this.points) + (', x=' + Kotlin.toString(this.x)) + (', y=' + Kotlin.toString(this.y)) + ')';
  };
  Bin.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.points) | 0;
    result = result * 31 + Kotlin.hashCode(this.x) | 0;
    result = result * 31 + Kotlin.hashCode(this.y) | 0;
    return result;
  };
  Bin.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.points, other.points) && Kotlin.equals(this.x, other.x) && Kotlin.equals(this.y, other.y)))));
  };
  function hexbinGenerator(init) {
    var $receiver = new HexbinGenerator();
    init($receiver);
    return $receiver;
  }
  function hexbinGenerator_0() {
    return new HexbinGenerator();
  }
  function HexbinGenerator() {
    this.x = HexbinGenerator$x$lambda;
    this.y = HexbinGenerator$y$lambda;
    this.extent = new Extent(0.0, 0.0, 1.0, 1.0);
    var x = math.THIRDPI;
    this.dx_0 = 2.0 * Math_0.sin(x);
    this.dy_0 = 1.5;
    this._radius_0 = 1.0;
  }
  Object.defineProperty(HexbinGenerator.prototype, 'width', {
    get: function () {
      return this.extent.x1 - this.extent.x0;
    },
    set: function (value) {
      this.extent.x0 = 0.0;
      this.extent.x1 = value;
    }
  });
  Object.defineProperty(HexbinGenerator.prototype, 'height', {
    get: function () {
      return this.extent.y1 - this.extent.y0;
    },
    set: function (value) {
      this.extent.y0 = 0.0;
      this.extent.y1 = value;
    }
  });
  Object.defineProperty(HexbinGenerator.prototype, 'radius', {
    get: function () {
      return this._radius_0;
    },
    set: function (value) {
      this._radius_0 = value;
      var tmp$ = this._radius_0 * 2;
      var x = math.THIRDPI;
      this.dx_0 = tmp$ * Math_0.sin(x);
      this.dy_0 = this._radius_0 * 1.5;
    }
  });
  var LinkedHashMap_init = Kotlin.kotlin.collections.LinkedHashMap_init_q3lmfv$;
  var checkIndexOverflow = Kotlin.kotlin.collections.checkIndexOverflow_za3lpa$;
  HexbinGenerator.prototype.invoke_ccv9pi$ = function (points) {
    var binsById = LinkedHashMap_init();
    var bins = ArrayList_init();
    var tmp$, tmp$_0;
    var index = 0;
    tmp$ = points.iterator();
    loop_label: while (tmp$.hasNext()) {
      var item = tmp$.next();
      var index_0 = checkIndexOverflow((tmp$_0 = index, index = tmp$_0 + 1 | 0, tmp$_0));
      action$break: do {
        var px = this.x(item, index_0, points);
        var py = this.y(item, index_0, points);
        if (isNaN_0(px) || isNaN_0(py))
          break action$break;
        py /= this.dy_0;
        var pj = numberToInt(round(py));
        px = px / this.dx_0 - (pj & 1) / 2.0;
        var pi = numberToInt(round(px));
        var py1 = py - pj;
        if (Math_0.abs(py1) * 3.0 > 1.0) {
          var px1 = px - pi;
          var pi2 = pi + ((px < pi ? -1 : 1) / 2 | 0) | 0;
          var pj2 = pj + (py < pj ? -1 : 1) | 0;
          var px2 = px - pi2;
          var py2 = py - pj2;
          if (px1 * px1 + py1 * py1 > px2 * px2 + py2 * py2) {
            pi = pi2 + (((pj & 1) !== 0 ? 1 : -1) / 2 | 0) | 0;
            pj = pj2;
          }
        }
        var id = pi.toString() + '-' + pj;
        var bin = binsById.get_11rb$(id);
        if (bin != null)
          bin.points.add_11rb$(item);
        else {
          bin = new Bin(mutableListOf([item]), (pi + (pj & 1) / 2.0) * this.dx_0, pj * this.dy_0);
          var value = bin;
          binsById.put_xwzc9p$(id, value);
          bins.add_11rb$(bin);
        }
      }
       while (false);
    }
    return bins;
  };
  var collectionSizeOrDefault = Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$;
  var ArrayList_init_0 = Kotlin.kotlin.collections.ArrayList_init_ww73n8$;
  HexbinGenerator.prototype.hexagon_0 = function (radius) {
    var $receiver = hexagon;
    var destination = ArrayList_init_0(collectionSizeOrDefault($receiver, 10));
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(item.times_3p81yu$(radius));
    }
    return destination;
  };
  HexbinGenerator.prototype.hexagon_hsprv8$ = function (path, origin, radius) {
    if (radius === void 0)
      radius = null;
    var hex = this.hexagon_0(radius == null ? this._radius_0 : radius);
    path.moveTo_lu1900$(origin.x + hex.get_za3lpa$(0).x, origin.y + hex.get_za3lpa$(0).y);
    var tmp$;
    tmp$ = until(1, hex.size).iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      path.lineTo_lu1900$(origin.x + hex.get_za3lpa$(element).x, origin.y + hex.get_za3lpa$(element).y);
    }
    path.closePath();
  };
  HexbinGenerator.prototype.centers = function () {
    var centers = ArrayList_init();
    var j = numberToInt(round(this.extent.y0 / this.dy_0));
    var i = numberToInt(round(this.extent.x0 / this.dx_0));
    var widthLimit = this.extent.x1 + this.dx_0 / 2;
    var heightLimit = this.extent.y1 + this._radius_0;
    var _y = j * this.dy_0;
    while (_y < heightLimit) {
      var _x = i * this.dx_0 + (j & 1) * (this.dx_0 / 2.0);
      while (_x < widthLimit) {
        centers.add_11rb$(new Point(_x, _y));
        _x += this.dx_0;
      }
      _y += this.dy_0;
      j = j + 1 | 0;
    }
    return centers;
  };
  HexbinGenerator.prototype.mesh_wt6n9s$ = function (path) {
    var fragment = this.hexagon_0(this._radius_0).subList_vux9f0$(0, 4);
    var centers = this.centers();
    var forEach$result;
    var tmp$;
    tmp$ = centers.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      path.moveTo_lu1900$(element.x + fragment.get_za3lpa$(0).x, element.y + fragment.get_za3lpa$(0).y);
      var tmp$_0;
      tmp$_0 = until(1, 4).iterator();
      while (tmp$_0.hasNext()) {
        var element_0 = tmp$_0.next();
        path.lineTo_lu1900$(element.x + fragment.get_za3lpa$(element_0).x, element.y + fragment.get_za3lpa$(element_0).y);
      }
      path.closePath();
    }
    return forEach$result;
  };
  function HexbinGenerator$x$lambda(point, f, f_0) {
    return point.x;
  }
  function HexbinGenerator$y$lambda(point, f, f_0) {
    return point.y;
  }
  HexbinGenerator.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'HexbinGenerator',
    interfaces: []
  };
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$hexbin = package$data2viz.hexbin || (package$data2viz.hexbin = {});
  package$hexbin.Bin = Bin;
  package$hexbin.hexbinGenerator_e66mk5$ = hexbinGenerator;
  package$hexbin.hexbinGenerator = hexbinGenerator_0;
  package$hexbin.HexbinGenerator = HexbinGenerator;
  var $receiver = new IntRange(0, 5);
  var destination = ArrayList_init_0(collectionSizeOrDefault($receiver, 10));
  var tmp$;
  tmp$ = $receiver.iterator();
  while (tmp$.hasNext()) {
    var item = tmp$.next();
    destination.add_11rb$(math.THIRDPI * item);
  }
  var destination_0 = ArrayList_init_0(collectionSizeOrDefault(destination, 10));
  var tmp$_0;
  tmp$_0 = destination.iterator();
  while (tmp$_0.hasNext()) {
    var item_0 = tmp$_0.next();
    destination_0.add_11rb$(new Point(Math_0.sin(item_0), -Math_0.cos(item_0)));
  }
  hexagon = destination_0;
  Kotlin.defineModule('d2v-hexbin-js', _);
  return _;
}));

//# sourceMappingURL=d2v-hexbin-js.js.map
