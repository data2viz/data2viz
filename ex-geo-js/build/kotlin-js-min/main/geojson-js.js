(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'geojson-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'geojson-js'.");
    }
    root['geojson-js'] = factory(typeof this['geojson-js'] === 'undefined' ? {} : this['geojson-js'], kotlin);
  }
}(this, function (_, Kotlin) {
  'use strict';
  var Kind_INTERFACE = Kotlin.Kind.INTERFACE;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var throwCCE = Kotlin.throwCCE;
  var IllegalStateException_init = Kotlin.kotlin.IllegalStateException_init_pdl1vj$;
  function get_lon($receiver) {
    return $receiver[0];
  }
  function get_lat($receiver) {
    return $receiver[1];
  }
  function get_alt($receiver) {
    return $receiver.length > 2 ? $receiver[2] : null;
  }
  function GeoJsonObject() {
  }
  GeoJsonObject.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'GeoJsonObject',
    interfaces: []
  };
  function Geometry() {
  }
  Geometry.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Geometry',
    interfaces: [GeoJsonObject]
  };
  function FeatureCollection(features) {
    this.features = features;
  }
  FeatureCollection.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'FeatureCollection',
    interfaces: [GeoJsonObject]
  };
  FeatureCollection.prototype.component1 = function () {
    return this.features;
  };
  FeatureCollection.prototype.copy_es8bbo$ = function (features) {
    return new FeatureCollection(features === void 0 ? this.features : features);
  };
  FeatureCollection.prototype.toString = function () {
    return 'FeatureCollection(features=' + Kotlin.toString(this.features) + ')';
  };
  FeatureCollection.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.features) | 0;
    return result;
  };
  FeatureCollection.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && Kotlin.equals(this.features, other.features))));
  };
  function Feature(geometry, id) {
    if (id === void 0)
      id = null;
    this.geometry = geometry;
    this.id = id;
  }
  Feature.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Feature',
    interfaces: [GeoJsonObject]
  };
  Feature.prototype.component1 = function () {
    return this.geometry;
  };
  Feature.prototype.component2 = function () {
    return this.id;
  };
  Feature.prototype.copy_qw2aov$ = function (geometry, id) {
    return new Feature(geometry === void 0 ? this.geometry : geometry, id === void 0 ? this.id : id);
  };
  Feature.prototype.toString = function () {
    return 'Feature(geometry=' + Kotlin.toString(this.geometry) + (', id=' + Kotlin.toString(this.id)) + ')';
  };
  Feature.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.geometry) | 0;
    result = result * 31 + Kotlin.hashCode(this.id) | 0;
    return result;
  };
  Feature.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.geometry, other.geometry) && Kotlin.equals(this.id, other.id)))));
  };
  function Point(coordinates) {
    this.coordinates = coordinates;
  }
  Point.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Point',
    interfaces: [Geometry]
  };
  Point.prototype.component1 = function () {
    return this.coordinates;
  };
  Point.prototype.copy_awc180$ = function (coordinates) {
    return new Point(coordinates === void 0 ? this.coordinates : coordinates);
  };
  Point.prototype.toString = function () {
    return 'Point(coordinates=' + Kotlin.toString(this.coordinates) + ')';
  };
  Point.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.coordinates) | 0;
    return result;
  };
  Point.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && Kotlin.equals(this.coordinates, other.coordinates))));
  };
  function MultiPoint(coordinates) {
    this.coordinates = coordinates;
  }
  MultiPoint.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'MultiPoint',
    interfaces: [Geometry]
  };
  MultiPoint.prototype.component1 = function () {
    return this.coordinates;
  };
  MultiPoint.prototype.copy_4p5xvi$ = function (coordinates) {
    return new MultiPoint(coordinates === void 0 ? this.coordinates : coordinates);
  };
  MultiPoint.prototype.toString = function () {
    return 'MultiPoint(coordinates=' + Kotlin.toString(this.coordinates) + ')';
  };
  MultiPoint.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.coordinates) | 0;
    return result;
  };
  MultiPoint.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && Kotlin.equals(this.coordinates, other.coordinates))));
  };
  function LineString(coordinates) {
    this.coordinates = coordinates;
  }
  LineString.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'LineString',
    interfaces: [Geometry]
  };
  LineString.prototype.component1 = function () {
    return this.coordinates;
  };
  LineString.prototype.copy_4p5xvi$ = function (coordinates) {
    return new LineString(coordinates === void 0 ? this.coordinates : coordinates);
  };
  LineString.prototype.toString = function () {
    return 'LineString(coordinates=' + Kotlin.toString(this.coordinates) + ')';
  };
  LineString.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.coordinates) | 0;
    return result;
  };
  LineString.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && Kotlin.equals(this.coordinates, other.coordinates))));
  };
  function MultiLineString(coordinates) {
    this.coordinates = coordinates;
  }
  MultiLineString.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'MultiLineString',
    interfaces: [Geometry]
  };
  MultiLineString.prototype.component1 = function () {
    return this.coordinates;
  };
  MultiLineString.prototype.copy_xjnpbk$ = function (coordinates) {
    return new MultiLineString(coordinates === void 0 ? this.coordinates : coordinates);
  };
  MultiLineString.prototype.toString = function () {
    return 'MultiLineString(coordinates=' + Kotlin.toString(this.coordinates) + ')';
  };
  MultiLineString.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.coordinates) | 0;
    return result;
  };
  MultiLineString.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && Kotlin.equals(this.coordinates, other.coordinates))));
  };
  function Polygon(coordinates) {
    this.coordinates = coordinates;
    this.hasHoles = this.coordinates.length > 1;
  }
  Polygon.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Polygon',
    interfaces: [Geometry]
  };
  Polygon.prototype.component1 = function () {
    return this.coordinates;
  };
  Polygon.prototype.copy_xjnpbk$ = function (coordinates) {
    return new Polygon(coordinates === void 0 ? this.coordinates : coordinates);
  };
  Polygon.prototype.toString = function () {
    return 'Polygon(coordinates=' + Kotlin.toString(this.coordinates) + ')';
  };
  Polygon.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.coordinates) | 0;
    return result;
  };
  Polygon.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && Kotlin.equals(this.coordinates, other.coordinates))));
  };
  function MultiPolygon(coordinates) {
    this.coordinates = coordinates;
  }
  MultiPolygon.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'MultiPolygon',
    interfaces: [Geometry]
  };
  MultiPolygon.prototype.component1 = function () {
    return this.coordinates;
  };
  MultiPolygon.prototype.copy_gwv4si$ = function (coordinates) {
    return new MultiPolygon(coordinates === void 0 ? this.coordinates : coordinates);
  };
  MultiPolygon.prototype.toString = function () {
    return 'MultiPolygon(coordinates=' + Kotlin.toString(this.coordinates) + ')';
  };
  MultiPolygon.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.coordinates) | 0;
    return result;
  };
  MultiPolygon.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && Kotlin.equals(this.coordinates, other.coordinates))));
  };
  function GeometryCollection(geometries) {
    this.geometries = geometries;
  }
  GeometryCollection.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'GeometryCollection',
    interfaces: [Geometry]
  };
  GeometryCollection.prototype.component1 = function () {
    return this.geometries;
  };
  GeometryCollection.prototype.copy_8xh9kw$ = function (geometries) {
    return new GeometryCollection(geometries === void 0 ? this.geometries : geometries);
  };
  GeometryCollection.prototype.toString = function () {
    return 'GeometryCollection(geometries=' + Kotlin.toString(this.geometries) + ')';
  };
  GeometryCollection.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.geometries) | 0;
    return result;
  };
  GeometryCollection.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && Kotlin.equals(this.geometries, other.geometries))));
  };
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_ww73n8$;
  var copyToArray = Kotlin.kotlin.collections.copyToArray;
  function asGeoJsonObject($receiver) {
    var tmp$;
    switch ($receiver.type) {
      case 'Point':
        return new Point($receiver.coordinates);
      case 'MultiPoint':
        return new MultiPoint($receiver.coordinates);
      case 'LineString':
        return new LineString($receiver.coordinates);
      case 'MultiLineString':
        return new MultiLineString($receiver.coordinates);
      case 'Polygon':
        return new Polygon($receiver.coordinates);
      case 'MultiPolygon':
        return new MultiPolygon($receiver.coordinates);
      case 'GeometryCollection':
        var types = $receiver.geometries;
        var destination = ArrayList_init(types.length);
        var tmp$_0;
        for (tmp$_0 = 0; tmp$_0 !== types.length; ++tmp$_0) {
          var item = types[tmp$_0];
          var tmp$_1;
          destination.add_11rb$(Kotlin.isType(tmp$_1 = asGeoJsonObject(item), Geometry) ? tmp$_1 : throwCCE());
        }

        var geometries = copyToArray(destination);
        return new GeometryCollection(geometries);
      case 'Feature':
        var geometry = $receiver.geometry;
        return new Feature(Kotlin.isType(tmp$ = asGeoJsonObject(geometry), Geometry) ? tmp$ : throwCCE());
      case 'FeatureCollection':
        return asFeatureCollection($receiver);
      default:throw IllegalStateException_init($receiver.type + ' is not known');
    }
  }
  var Array_0 = Array;
  function asFeatureCollection($receiver) {
    var tmp$;
    var dyn = $receiver;
    var featureJs = dyn.features;
    var array = Array_0(0);
    var tmp$_0;
    tmp$_0 = array.length - 1 | 0;
    for (var i = 0; i <= tmp$_0; i++) {
      array[i] = new Point([]);
    }
    var features = array;
    var size = featureJs.length;
    for (var i_0 = 0; i_0 < size; i_0++) {
      var feature = featureJs[i_0];
      var typed = feature.geometry;
      features[i_0] = new Feature(Kotlin.isType(tmp$ = asGeoJsonObject(typed), Geometry) ? tmp$ : throwCCE());
    }
    return new FeatureCollection(features);
  }
  function toGeoJsonObject($receiver) {
    return asGeoJsonObject(JSON.parse($receiver));
  }
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$geojson = package$data2viz.geojson || (package$data2viz.geojson = {});
  package$geojson.get_lon_hb77y9$ = get_lon;
  package$geojson.get_lat_hb77y9$ = get_lat;
  package$geojson.get_alt_hb77y9$ = get_alt;
  package$geojson.GeoJsonObject = GeoJsonObject;
  package$geojson.Geometry = Geometry;
  package$geojson.FeatureCollection = FeatureCollection;
  package$geojson.Feature = Feature;
  package$geojson.Point = Point;
  package$geojson.MultiPoint = MultiPoint;
  package$geojson.LineString = LineString;
  package$geojson.MultiLineString = MultiLineString;
  package$geojson.Polygon = Polygon;
  package$geojson.MultiPolygon = MultiPolygon;
  package$geojson.GeometryCollection = GeometryCollection;
  var package$js = package$geojson.js || (package$geojson.js = {});
  package$js.asGeoJsonObject_qjdlzm$ = asGeoJsonObject;
  package$geojson.toGeoJsonObject_pdl1vz$ = toGeoJsonObject;
  Kotlin.defineModule('geojson-js', _);
  return _;
}));

//# sourceMappingURL=geojson-js.js.map
