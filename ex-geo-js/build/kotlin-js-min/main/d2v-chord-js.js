(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'd2v-chord-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'd2v-chord-js'.");
    }
    root['d2v-chord-js'] = factory(typeof this['d2v-chord-js'] === 'undefined' ? {} : this['d2v-chord-js'], kotlin);
  }
}(this, function (_, Kotlin) {
  'use strict';
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var until = Kotlin.kotlin.ranges.until_dqglrj$;
  var toList = Kotlin.kotlin.collections.toList_7wnvza$;
  var ensureNotNull = Kotlin.ensureNotNull;
  var sortWith = Kotlin.kotlin.collections.sortWith_iwcb0m$;
  var math = Kotlin.kotlin.math;
  var toList_0 = Kotlin.kotlin.collections.toList_us0mfu$;
  var copyToArray = Kotlin.kotlin.collections.copyToArray;
  var Comparator = Kotlin.kotlin.Comparator;
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_ww73n8$;
  var collectionSizeOrDefault = Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$;
  var ArrayList_init_0 = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  var checkIndexOverflow = Kotlin.kotlin.collections.checkIndexOverflow_za3lpa$;
  var Math_0 = Math;
  var Unit = Kotlin.kotlin.Unit;
  function Comparator$ObjectLiteral(closure$comparison) {
    this.closure$comparison = closure$comparison;
  }
  Comparator$ObjectLiteral.prototype.compare = function (a, b) {
    return this.closure$comparison(a, b);
  };
  Comparator$ObjectLiteral.$metadata$ = {kind: Kind_CLASS, interfaces: [Comparator]};
  function ChordSubgroup(index, subIndex, startAngle, endAngle, value) {
    if (index === void 0)
      index = 0;
    if (subIndex === void 0)
      subIndex = 0;
    if (startAngle === void 0)
      startAngle = 0.0;
    if (endAngle === void 0)
      endAngle = 0.0;
    if (value === void 0)
      value = 0.0;
    this.index = index;
    this.subIndex = subIndex;
    this.startAngle = startAngle;
    this.endAngle = endAngle;
    this.value = value;
  }
  ChordSubgroup.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ChordSubgroup',
    interfaces: []
  };
  ChordSubgroup.prototype.component1 = function () {
    return this.index;
  };
  ChordSubgroup.prototype.component2 = function () {
    return this.subIndex;
  };
  ChordSubgroup.prototype.component3 = function () {
    return this.startAngle;
  };
  ChordSubgroup.prototype.component4 = function () {
    return this.endAngle;
  };
  ChordSubgroup.prototype.component5 = function () {
    return this.value;
  };
  ChordSubgroup.prototype.copy_hq5qsi$ = function (index, subIndex, startAngle, endAngle, value) {
    return new ChordSubgroup(index === void 0 ? this.index : index, subIndex === void 0 ? this.subIndex : subIndex, startAngle === void 0 ? this.startAngle : startAngle, endAngle === void 0 ? this.endAngle : endAngle, value === void 0 ? this.value : value);
  };
  ChordSubgroup.prototype.toString = function () {
    return 'ChordSubgroup(index=' + Kotlin.toString(this.index) + (', subIndex=' + Kotlin.toString(this.subIndex)) + (', startAngle=' + Kotlin.toString(this.startAngle)) + (', endAngle=' + Kotlin.toString(this.endAngle)) + (', value=' + Kotlin.toString(this.value)) + ')';
  };
  ChordSubgroup.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.index) | 0;
    result = result * 31 + Kotlin.hashCode(this.subIndex) | 0;
    result = result * 31 + Kotlin.hashCode(this.startAngle) | 0;
    result = result * 31 + Kotlin.hashCode(this.endAngle) | 0;
    result = result * 31 + Kotlin.hashCode(this.value) | 0;
    return result;
  };
  ChordSubgroup.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.index, other.index) && Kotlin.equals(this.subIndex, other.subIndex) && Kotlin.equals(this.startAngle, other.startAngle) && Kotlin.equals(this.endAngle, other.endAngle) && Kotlin.equals(this.value, other.value)))));
  };
  function ChordGroup(index, startAngle, endAngle, value) {
    if (index === void 0)
      index = 0;
    if (startAngle === void 0)
      startAngle = 0.0;
    if (endAngle === void 0)
      endAngle = 0.0;
    if (value === void 0)
      value = 0.0;
    this.index = index;
    this.startAngle = startAngle;
    this.endAngle = endAngle;
    this.value = value;
  }
  ChordGroup.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ChordGroup',
    interfaces: []
  };
  ChordGroup.prototype.component1 = function () {
    return this.index;
  };
  ChordGroup.prototype.component2 = function () {
    return this.startAngle;
  };
  ChordGroup.prototype.component3 = function () {
    return this.endAngle;
  };
  ChordGroup.prototype.component4 = function () {
    return this.value;
  };
  ChordGroup.prototype.copy_fx7aco$ = function (index, startAngle, endAngle, value) {
    return new ChordGroup(index === void 0 ? this.index : index, startAngle === void 0 ? this.startAngle : startAngle, endAngle === void 0 ? this.endAngle : endAngle, value === void 0 ? this.value : value);
  };
  ChordGroup.prototype.toString = function () {
    return 'ChordGroup(index=' + Kotlin.toString(this.index) + (', startAngle=' + Kotlin.toString(this.startAngle)) + (', endAngle=' + Kotlin.toString(this.endAngle)) + (', value=' + Kotlin.toString(this.value)) + ')';
  };
  ChordGroup.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.index) | 0;
    result = result * 31 + Kotlin.hashCode(this.startAngle) | 0;
    result = result * 31 + Kotlin.hashCode(this.endAngle) | 0;
    result = result * 31 + Kotlin.hashCode(this.value) | 0;
    return result;
  };
  ChordGroup.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.index, other.index) && Kotlin.equals(this.startAngle, other.startAngle) && Kotlin.equals(this.endAngle, other.endAngle) && Kotlin.equals(this.value, other.value)))));
  };
  function Chord(source, target) {
    this.source = source;
    this.target = target;
  }
  Chord.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Chord',
    interfaces: []
  };
  Chord.prototype.component1 = function () {
    return this.source;
  };
  Chord.prototype.component2 = function () {
    return this.target;
  };
  Chord.prototype.copy_u64jbs$ = function (source, target) {
    return new Chord(source === void 0 ? this.source : source, target === void 0 ? this.target : target);
  };
  Chord.prototype.toString = function () {
    return 'Chord(source=' + Kotlin.toString(this.source) + (', target=' + Kotlin.toString(this.target)) + ')';
  };
  Chord.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.source) | 0;
    result = result * 31 + Kotlin.hashCode(this.target) | 0;
    return result;
  };
  Chord.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.source, other.source) && Kotlin.equals(this.target, other.target)))));
  };
  function Chords(chords, groups) {
    this.chords = chords;
    this.groups = groups;
  }
  Chords.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Chords',
    interfaces: []
  };
  Chords.prototype.component1 = function () {
    return this.chords;
  };
  Chords.prototype.component2 = function () {
    return this.groups;
  };
  Chords.prototype.copy_c1p08j$ = function (chords, groups) {
    return new Chords(chords === void 0 ? this.chords : chords, groups === void 0 ? this.groups : groups);
  };
  Chords.prototype.toString = function () {
    return 'Chords(chords=' + Kotlin.toString(this.chords) + (', groups=' + Kotlin.toString(this.groups)) + ')';
  };
  Chords.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.chords) | 0;
    result = result * 31 + Kotlin.hashCode(this.groups) | 0;
    return result;
  };
  Chords.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.chords, other.chords) && Kotlin.equals(this.groups, other.groups)))));
  };
  var emptyGroup;
  var emptySubgroup;
  function ChordLayout() {
    this.padAngle = 0.0;
    this.sortGroups = null;
    this.sortSubgroups = null;
    this.sortChords = null;
  }
  function ChordLayout$chord$lambda(this$ChordLayout, closure$groupSums) {
    return function (a, b) {
      return ensureNotNull(this$ChordLayout.sortGroups).compare(closure$groupSums[a], closure$groupSums[b]);
    };
  }
  function ChordLayout$chord$lambda$lambda(this$ChordLayout, closure$flow, closure$data, closure$index) {
    return function (a, b) {
      return ensureNotNull(this$ChordLayout.sortSubgroups).compare(closure$flow(closure$data.get_za3lpa$(closure$index), closure$data.get_za3lpa$(a)), closure$flow(closure$data.get_za3lpa$(closure$index), closure$data.get_za3lpa$(b)));
    };
  }
  ChordLayout.prototype.chord_5nmfia$ = function (data, flow) {
    var n = data.size;
    var sizeRange = until(0, n);
    var list = ArrayList_init(n);
    for (var index = 0; index < n; index++) {
      list.add_11rb$(0.0);
    }
    var groupSums = copyToArray(list);
    var list_0 = ArrayList_init(n);
    for (var index_0 = 0; index_0 < n; index_0++) {
      list_0.add_11rb$(emptyGroup);
    }
    var groups = copyToArray(list_0);
    var size = Kotlin.imul(n, n);
    var list_1 = ArrayList_init(size);
    for (var index_1 = 0; index_1 < size; index_1++) {
      list_1.add_11rb$(emptySubgroup);
    }
    var subgroups = copyToArray(list_1);
    var groupIndex = copyToArray(toList(sizeRange));
    var destination = ArrayList_init(collectionSizeOrDefault(sizeRange, 10));
    var tmp$;
    tmp$ = sizeRange.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(copyToArray(toList(sizeRange)));
    }
    var subgroupIndex = destination;
    var chords = ArrayList_init_0();
    var x = {v: null};
    var k = {v: 0.0};
    var tmp$_0, tmp$_0_0;
    var index_2 = 0;
    tmp$_0 = data.iterator();
    while (tmp$_0.hasNext()) {
      var item_0 = tmp$_0.next();
      var indexa = checkIndexOverflow((tmp$_0_0 = index_2, index_2 = tmp$_0_0 + 1 | 0, tmp$_0_0));
      x.v = 0.0;
      var tmp$_1;
      tmp$_1 = data.iterator();
      while (tmp$_1.hasNext()) {
        var element = tmp$_1.next();
        x.v += flow(item_0, element);
      }
      groupSums[indexa] = x.v;
      k.v += x.v;
    }
    if (this.sortGroups != null) {
      sortWith(groupIndex, new Comparator$ObjectLiteral(ChordLayout$chord$lambda(this, groupSums)));
    }
    if (this.sortSubgroups != null) {
      var tmp$_2, tmp$_0_1;
      var index_3 = 0;
      tmp$_2 = subgroupIndex.iterator();
      while (tmp$_2.hasNext()) {
        var item_1 = tmp$_2.next();
        sortWith(item_1, new Comparator$ObjectLiteral(ChordLayout$chord$lambda$lambda(this, flow, data, checkIndexOverflow((tmp$_0_1 = index_3, index_3 = tmp$_0_1 + 1 | 0, tmp$_0_1)))));
      }
    }
    var b = 2 * math.PI - this.padAngle * n;
    k.v = Math_0.max(0.0, b) / k.v;
    var dx = k.v !== 0.0 ? this.padAngle : 2 * math.PI / n;
    x.v = 0.0;
    var tmp$_3;
    for (tmp$_3 = 0; tmp$_3 !== groupIndex.length; ++tmp$_3) {
      var element_0 = groupIndex[tmp$_3];
      var x0 = x.v;
      var $receiver = subgroupIndex.get_za3lpa$(element_0);
      var tmp$_4;
      for (tmp$_4 = 0; tmp$_4 !== $receiver.length; ++tmp$_4) {
        var element_1 = $receiver[tmp$_4];
        var v = flow(data.get_za3lpa$(element_0), data.get_za3lpa$(element_1));
        var a0 = x.v;
        x.v += v * k.v;
        var a1 = x.v;
        subgroups[Kotlin.imul(element_1, n) + element_0 | 0] = new ChordSubgroup(element_0, element_1, a0, a1, v);
      }
      groups[element_0] = new ChordGroup(element_0, x0, x.v, groupSums[element_0]);
      x.v += dx;
    }
    var tmp$_5;
    tmp$_5 = sizeRange.iterator();
    while (tmp$_5.hasNext()) {
      var element_2 = tmp$_5.next();
      var tmp$_6;
      tmp$_6 = until(element_2, n).iterator();
      while (tmp$_6.hasNext()) {
        var element_3 = tmp$_6.next();
        var source = subgroups[Kotlin.imul(element_3, n) + element_2 | 0];
        var target = subgroups[Kotlin.imul(element_2, n) + element_3 | 0];
        if (source.value !== 0.0 || target.value !== 0.0) {
          if (source.value < target.value)
            chords.add_11rb$(new Chord(target, source));
          else
            chords.add_11rb$(new Chord(source, target));
        }
      }
    }
    var ret = new Chords(chords, toList_0(groups));
    return ret;
  };
  ChordLayout.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ChordLayout',
    interfaces: []
  };
  var halfPi;
  function ribbon$lambda(closure$radius) {
    return function (chord, path) {
      var s = chord.source;
      var t = chord.target;
      var sa0 = s.startAngle - halfPi;
      var sa1 = s.endAngle - halfPi;
      var sx0 = closure$radius * Math_0.cos(sa0);
      var sy0 = closure$radius * Math_0.sin(sa0);
      var ta0 = t.startAngle - halfPi;
      var ta1 = t.endAngle - halfPi;
      path.moveTo_lu1900$(sx0, sy0);
      path.arc_6p3vsx$(0.0, 0.0, closure$radius, sa0, sa1);
      if (sa0 !== ta0 || sa1 !== ta1) {
        path.quadraticCurveTo_6y0v78$(0.0, 0.0, closure$radius * Math_0.cos(ta0), closure$radius * Math_0.sin(ta0));
        path.arc_6p3vsx$(0.0, 0.0, closure$radius, ta0, ta1);
      }
      path.quadraticCurveTo_6y0v78$(0.0, 0.0, sx0, sy0);
      path.closePath();
      return Unit;
    };
  }
  function ribbon(radius) {
    return ribbon$lambda(radius);
  }
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$chord = package$data2viz.chord || (package$data2viz.chord = {});
  package$chord.ChordSubgroup = ChordSubgroup;
  package$chord.ChordGroup = ChordGroup;
  package$chord.Chord = Chord;
  package$chord.Chords = Chords;
  Object.defineProperty(package$chord, 'emptyGroup', {
    get: function () {
      return emptyGroup;
    }
  });
  Object.defineProperty(package$chord, 'emptySubgroup', {
    get: function () {
      return emptySubgroup;
    }
  });
  package$chord.ChordLayout = ChordLayout;
  package$chord.ribbon_14dthe$ = ribbon;
  emptyGroup = new ChordGroup();
  emptySubgroup = new ChordSubgroup();
  halfPi = math.PI / 2;
  Kotlin.defineModule('d2v-chord-js', _);
  return _;
}));

//# sourceMappingURL=d2v-chord-js.js.map
