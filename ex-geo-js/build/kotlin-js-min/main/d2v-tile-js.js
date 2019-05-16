(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin', 'd2v-core-js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'), require('d2v-core-js'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'd2v-tile-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'd2v-tile-js'.");
    }
    if (typeof this['d2v-core-js'] === 'undefined') {
      throw new Error("Error loading module 'd2v-tile-js'. Its dependency 'd2v-core-js' was not found. Please, check whether 'd2v-core-js' is loaded prior to 'd2v-tile-js'.");
    }
    root['d2v-tile-js'] = factory(typeof this['d2v-tile-js'] === 'undefined' ? {} : this['d2v-tile-js'], kotlin, this['d2v-core-js']);
  }
}(this, function (_, Kotlin, $module$d2v_core_js) {
  'use strict';
  var Point = $module$d2v_core_js.io.data2viz.geom.Point;
  var round = Kotlin.kotlin.math.round_14dthe$;
  var numberToInt = Kotlin.numberToInt;
  var IntRange = Kotlin.kotlin.ranges.IntRange;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var ensureNotNull = Kotlin.ensureNotNull;
  var LN2;
  var INFINITY;
  function tilesLayout(init) {
    var $receiver = new TilesLayout();
    init($receiver);
    return $receiver;
  }
  function TilesLayout() {
    this.stale_0 = true;
    this.origin_0 = new Point(0.0, 0.0);
    this.end_0 = new Point(960.0, 500.0);
    this.translation = this.origin_0.plus_mowjvf$(this.end_0).div_3p81yu$(2);
    this.zoomDelta_3us8dv$_0 = 0.0;
    this.wrap_0 = true;
    this.tilesCount = 256.0;
    this._tileSize_0 = 256.0;
    this.zoom_8r9u35$_0 = 1;
    this.translate = null;
  }
  Object.defineProperty(TilesLayout.prototype, 'zoomDelta', {
    get: function () {
      return this.zoomDelta_3us8dv$_0;
    },
    set: function (value) {
      this.zoomDelta_3us8dv$_0 = value;
      this.stale_0 = true;
    }
  });
  Object.defineProperty(TilesLayout.prototype, 'width', {
    get: function () {
      return this.end_0.x - this.origin_0.x;
    },
    set: function (value) {
      this.origin_0 = new Point(0.0, this.origin_0.y);
      this.end_0 = new Point(value, this.end_0.y);
      this.stale_0 = true;
    }
  });
  Object.defineProperty(TilesLayout.prototype, 'height', {
    get: function () {
      return this.end_0.y - this.origin_0.y;
    },
    set: function (value) {
      this.origin_0 = new Point(this.origin_0.x, 0.0);
      this.end_0 = new Point(this.end_0.x, value);
      this.stale_0 = true;
    }
  });
  Object.defineProperty(TilesLayout.prototype, 'tileSize', {
    get: function () {
      this.eventualyRecompute_0();
      return this._tileSize_0;
    }
  });
  Object.defineProperty(TilesLayout.prototype, 'zoom', {
    get: function () {
      this.eventualyRecompute_0();
      return this.zoom_8r9u35$_0;
    },
    set: function (zoom) {
      this.zoom_8r9u35$_0 = zoom;
    }
  });
  var Math_0 = Math;
  TilesLayout.prototype.eventualyRecompute_0 = function () {
    if (!this.stale_0)
      return;
    this.stale_0 = false;
    var x = this.tilesCount;
    var a = Math_0.log(x) / LN2 - 8.0;
    var z = Math_0.max(a, 0.0);
    this.zoom = numberToInt(round(z + this.zoomDelta));
    var x_0 = z - this.zoom + 8.0;
    this._tileSize_0 = Math_0.pow(2.0, x_0);
  };
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  TilesLayout.prototype.tiles = function () {
    if (this.stale_0)
      this.eventualyRecompute_0();
    var j = 1 << this.zoom;
    var x = this.translation.x - this.tilesCount / 2;
    var y = this.translation.y - this.tilesCount / 2;
    this.translate = (new Point(x, y)).div_3p81yu$(this._tileSize_0);
    var tmp$ = this.wrap_0 ? -INFINITY | 0 : 0;
    var x_0 = (this.origin_0.x - x) / this._tileSize_0;
    var b = numberToInt(Math_0.floor(x_0));
    var minCols = Math_0.max(tmp$, b);
    var x_1 = (this.end_0.x - x) / this._tileSize_0;
    var a = numberToInt(Math_0.ceil(x_1));
    var b_0 = this.wrap_0 ? INFINITY : j;
    var maxCols = Math_0.min(a, b_0);
    var x_2 = (this.origin_0.y - y) / this._tileSize_0;
    var b_1 = numberToInt(Math_0.floor(x_2));
    var minRows = Math_0.max(0, b_1);
    var x_3 = (this.end_0.y - y) / this._tileSize_0;
    var a_0 = numberToInt(Math_0.ceil(x_3));
    var maxRows = Math_0.min(a_0, j);
    var tiles = ArrayList_init();
    var tmp$_0;
    tmp$_0 = (new IntRange(minRows, maxRows)).iterator();
    while (tmp$_0.hasNext()) {
      var element = tmp$_0.next();
      var tmp$_1;
      tmp$_1 = (new IntRange(minCols, maxCols)).iterator();
      while (tmp$_1.hasNext()) {
        var element_0 = tmp$_1.next();
        tiles.add_11rb$(new Tile(this, (element_0 % j + j | 0) % j, element));
      }
    }
    return tiles;
  };
  TilesLayout.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'TilesLayout',
    interfaces: []
  };
  function Tile(layout, tileX, tileY) {
    this.layout = layout;
    this.tileX = tileX;
    this.tileY = tileY;
  }
  Object.defineProperty(Tile.prototype, 'zoom', {
    get: function () {
      return this.layout.zoom;
    }
  });
  Object.defineProperty(Tile.prototype, 'x', {
    get: function () {
      return this.layout.tileSize * (ensureNotNull(this.layout.translate).x + this.tileX);
    }
  });
  Object.defineProperty(Tile.prototype, 'y', {
    get: function () {
      return this.layout.tileSize * (ensureNotNull(this.layout.translate).y + this.tileY);
    }
  });
  Tile.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Tile',
    interfaces: []
  };
  Tile.prototype.component1 = function () {
    return this.layout;
  };
  Tile.prototype.component2 = function () {
    return this.tileX;
  };
  Tile.prototype.component3 = function () {
    return this.tileY;
  };
  Tile.prototype.copy_eztsgm$ = function (layout, tileX, tileY) {
    return new Tile(layout === void 0 ? this.layout : layout, tileX === void 0 ? this.tileX : tileX, tileY === void 0 ? this.tileY : tileY);
  };
  Tile.prototype.toString = function () {
    return 'Tile(layout=' + Kotlin.toString(this.layout) + (', tileX=' + Kotlin.toString(this.tileX)) + (', tileY=' + Kotlin.toString(this.tileY)) + ')';
  };
  Tile.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.layout) | 0;
    result = result * 31 + Kotlin.hashCode(this.tileX) | 0;
    result = result * 31 + Kotlin.hashCode(this.tileY) | 0;
    return result;
  };
  Tile.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.layout, other.layout) && Kotlin.equals(this.tileX, other.tileX) && Kotlin.equals(this.tileY, other.tileY)))));
  };
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$tile = package$data2viz.tile || (package$data2viz.tile = {});
  Object.defineProperty(package$tile, 'LN2', {
    get: function () {
      return LN2;
    }
  });
  Object.defineProperty(package$tile, 'INFINITY', {
    get: function () {
      return INFINITY;
    }
  });
  package$tile.tilesLayout_g0juf3$ = tilesLayout;
  package$tile.TilesLayout = TilesLayout;
  package$tile.Tile = Tile;
  LN2 = Math_0.log(2.0);
  INFINITY = 2147483647;
  Kotlin.defineModule('d2v-tile-js', _);
  return _;
}));

//# sourceMappingURL=d2v-tile-js.js.map
