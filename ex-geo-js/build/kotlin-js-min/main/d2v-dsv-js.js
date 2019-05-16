(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'd2v-dsv-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'd2v-dsv-js'.");
    }
    root['d2v-dsv-js'] = factory(typeof this['d2v-dsv-js'] === 'undefined' ? {} : this['d2v-dsv-js'], kotlin);
  }
}(this, function (_, Kotlin) {
  'use strict';
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var Regex_init = Kotlin.kotlin.text.Regex_init_61zpoe$;
  var lazy = Kotlin.kotlin.lazy_klfg04$;
  var unboxChar = Kotlin.unboxChar;
  var toBoxedChar = Kotlin.toBoxedChar;
  Dsv$Token$EOF.prototype = Object.create(Dsv$Token.prototype);
  Dsv$Token$EOF.prototype.constructor = Dsv$Token$EOF;
  Dsv$Token$EOL.prototype = Object.create(Dsv$Token.prototype);
  Dsv$Token$EOL.prototype.constructor = Dsv$Token$EOL;
  Dsv$Token$TextToken.prototype = Object.create(Dsv$Token.prototype);
  Dsv$Token$TextToken.prototype.constructor = Dsv$Token$TextToken;
  function Dsv(delimiterCode) {
    if (delimiterCode === void 0)
      delimiterCode = 44;
    this.delimiterCode = toBoxedChar(delimiterCode);
  }
  function Dsv$Token() {
  }
  function Dsv$Token$EOF() {
    Dsv$Token.call(this);
  }
  Dsv$Token$EOF.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'EOF',
    interfaces: [Dsv$Token]
  };
  function Dsv$Token$EOL() {
    Dsv$Token.call(this);
  }
  Dsv$Token$EOL.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'EOL',
    interfaces: [Dsv$Token]
  };
  function Dsv$Token$TextToken(content) {
    Dsv$Token.call(this);
    this.content = content;
  }
  Dsv$Token$TextToken.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'TextToken',
    interfaces: [Dsv$Token]
  };
  Dsv$Token.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Token',
    interfaces: []
  };
  Dsv.prototype.parse = function () {
  };
  function Dsv$parseRows$lambda() {
    return Regex_init('""');
  }
  function Dsv$parseRows$token(closure$I, closure$N, closure$eol, closure$EOL, closure$text, closure$regexp, this$Dsv) {
    return function () {
      var tmp$, tmp$_0, tmp$_1;
      if (closure$I.v >= closure$N)
        return new Dsv$Token$EOF();
      if (closure$eol.v) {
        closure$eol.v = false;
        return closure$EOL;
      }
      var j = closure$I.v;
      var c;
      if (closure$text.charCodeAt(j) === 34) {
        var i = j;
        while ((tmp$ = i, i = tmp$ + 1 | 0, tmp$) < closure$N) {
          if (closure$text.charCodeAt(i) === 34) {
            if (closure$text.charCodeAt(i + 1 | 0) !== 34)
              break;
            i = i + 1 | 0;
          }
        }
        closure$I.v = i + 2 | 0;
        c = closure$text.charCodeAt(i + 1 | 0);
        if (c === 13) {
          closure$eol.v = true;
          if (closure$text.charCodeAt(i + 2 | 0) === 10) {
            closure$I.v = closure$I.v + 1 | 0;
          }
        }
         else if (c === 10)
          closure$eol.v = true;
        var $receiver = closure$text;
        var startIndex = j + 1 | 0;
        var endIndex = i;
        var tmp$_2 = $receiver.substring(startIndex, endIndex);
        return new Dsv$Token$TextToken(closure$regexp.value.replace_x2uqeu$(tmp$_2, '"'));
      }
      while (closure$I.v < closure$N) {
        var k = 1;
        tmp$_1 = (tmp$_0 = closure$I.v, closure$I.v = tmp$_0 + 1 | 0, tmp$_0);
        c = closure$text.charCodeAt(tmp$_1);
        if (c === 10) {
          closure$eol.v = true;
        }
         else if (c === 13) {
          closure$eol.v = true;
          if (closure$text.charCodeAt(closure$I.v) === 10) {
            closure$I.v = closure$I.v + 1 | 0;
            k = k + 1 | 0;
          }
        }
         else if (c !== unboxChar(this$Dsv.delimiterCode)) {
          continue;
        }
        var $receiver_0 = closure$text;
        var endIndex_0 = closure$I.v - k | 0;
        return new Dsv$Token$TextToken($receiver_0.substring(j, endIndex_0));
      }
      return new Dsv$Token$TextToken(closure$text.substring(j));
    };
  }
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  Dsv.prototype.parseRows_61zpoe$ = function (text) {
    var rows = ArrayList_init();
    var N = text.length;
    var I = {v: 0};
    var n = 0;
    var EOL = new Dsv$Token$EOL();
    var eol = {v: false};
    var regexp = lazy(Dsv$parseRows$lambda);
    var token = Dsv$parseRows$token(I, N, eol, EOL, text, regexp, this);
    var t = token();
    while (!Kotlin.isType(t, Dsv$Token$EOF)) {
      var row = ArrayList_init();
      while (Kotlin.isType(t, Dsv$Token$TextToken)) {
        var element = t.content;
        row.add_11rb$(element);
        t = token();
      }
      rows.add_11rb$(row);
      t = token();
    }
    return rows;
  };
  Dsv.prototype.format = function () {
  };
  Dsv.prototype.formatRows = function () {
  };
  Dsv.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Dsv',
    interfaces: []
  };
  Dsv$Token.EOF = Dsv$Token$EOF;
  Dsv$Token.EOL = Dsv$Token$EOL;
  Dsv$Token.TextToken = Dsv$Token$TextToken;
  Dsv.Token = Dsv$Token;
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$dsv = package$data2viz.dsv || (package$data2viz.dsv = {});
  package$dsv.Dsv = Dsv;
  Kotlin.defineModule('d2v-dsv-js', _);
  return _;
}));

//# sourceMappingURL=d2v-dsv-js.js.map
