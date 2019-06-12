(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'd2v-request-js'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'd2v-request-js'.");
    }
    root['d2v-request-js'] = factory(typeof this['d2v-request-js'] === 'undefined' ? {} : this['d2v-request-js'], kotlin);
  }
}(this, function (_, Kotlin) {
  'use strict';
  var Enum = Kotlin.kotlin.Enum;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var throwISE = Kotlin.throwISE;
  var Unit = Kotlin.kotlin.Unit;
  var ensureNotNull = Kotlin.ensureNotNull;
  var LinkedHashMap_init = Kotlin.kotlin.collections.LinkedHashMap_init_q3lmfv$;
  var isBlank = Kotlin.kotlin.text.isBlank_gw00vp$;
  HTTPMethod.prototype = Object.create(Enum.prototype);
  HTTPMethod.prototype.constructor = HTTPMethod;
  function HTTPMethod(name, ordinal) {
    Enum.call(this);
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function HTTPMethod_initFields() {
    HTTPMethod_initFields = function () {
    };
    HTTPMethod$GET_instance = new HTTPMethod('GET', 0);
    HTTPMethod$POST_instance = new HTTPMethod('POST', 1);
  }
  var HTTPMethod$GET_instance;
  function HTTPMethod$GET_getInstance() {
    HTTPMethod_initFields();
    return HTTPMethod$GET_instance;
  }
  var HTTPMethod$POST_instance;
  function HTTPMethod$POST_getInstance() {
    HTTPMethod_initFields();
    return HTTPMethod$POST_instance;
  }
  HTTPMethod.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'HTTPMethod',
    interfaces: [Enum]
  };
  function HTTPMethod$values() {
    return [HTTPMethod$GET_getInstance(), HTTPMethod$POST_getInstance()];
  }
  HTTPMethod.values = HTTPMethod$values;
  function HTTPMethod$valueOf(name) {
    switch (name) {
      case 'GET':
        return HTTPMethod$GET_getInstance();
      case 'POST':
        return HTTPMethod$POST_getInstance();
      default:throwISE('No enum constant io.data2viz.request.HTTPMethod.' + name);
    }
  }
  HTTPMethod.valueOf_61zpoe$ = HTTPMethod$valueOf;
  function request$lambda($receiver) {
    return Unit;
  }
  function request(url, init) {
    if (init === void 0)
      init = request$lambda;
    var $receiver = new Request(url);
    init($receiver);
    return $receiver;
  }
  function Request(url) {
    this.url = url;
    this.httpMethod = HTTPMethod$GET_getInstance();
    this.mimeType = null;
    this.responseType = '';
    this.user = null;
    this.password = null;
    this.headers = LinkedHashMap_init();
    this.timeout = 0;
    this.xhr = new XMLHttpRequest();
    this.xhr.onload = Request_init$lambda(this);
    this.xhr.onerror = Request_init$lambda_0(this);
    this.xhr.ontimeout = Request_init$lambda_1(this);
  }
  Request.prototype.header_puj7f4$ = function (key, value) {
    this.headers.put_xwzc9p$(key, value);
  };
  function Request$send$lambda(closure$callback, this$Request) {
    return function (it) {
      closure$callback(this$Request.xhr);
      return Unit;
    };
  }
  Request.prototype.send_jiaja$ = function (callback) {
    this.xhr.open(this.httpMethod.name, this.url, true, this.user, this.password);
    var $receiver = this.mimeType;
    if (!($receiver == null || isBlank($receiver)) && !this.headers.containsKey_11rb$('accept')) {
      var $receiver_0 = this.headers;
      var value = this.mimeType + ',*/*';
      $receiver_0.put_xwzc9p$('accept', value);
    }
    var tmp$;
    tmp$ = this.headers.entries.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      this.xhr.setRequestHeader(element.key, element.value);
    }
    var $receiver_1 = this.mimeType;
    if (!($receiver_1 == null || isBlank($receiver_1)))
      this.xhr.overrideMimeType(ensureNotNull(this.mimeType));
    this.xhr.responseType = this.responseType;
    if (this.timeout > 0)
      this.xhr.timeout = this.timeout;
    this.xhr.onload = Request$send$lambda(callback, this);
    this.xhr.send();
  };
  Request.prototype.get_jiaja$ = function (callback) {
    this.send_jiaja$(callback);
  };
  Request.prototype.respond_9ojx7i$ = function (event) {
    this.xhr.status;
    return '';
  };
  Request.prototype.abort = function () {
    this.xhr.abort();
  };
  function Request_init$lambda(this$Request) {
    return function (event) {
      return this$Request.respond_9ojx7i$(event);
    };
  }
  function Request_init$lambda_0(this$Request) {
    return function (event) {
      return this$Request.respond_9ojx7i$(event);
    };
  }
  function Request_init$lambda_1(this$Request) {
    return function (event) {
      return this$Request.respond_9ojx7i$(event);
    };
  }
  Request.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Request',
    interfaces: []
  };
  Object.defineProperty(HTTPMethod, 'GET', {
    get: HTTPMethod$GET_getInstance
  });
  Object.defineProperty(HTTPMethod, 'POST', {
    get: HTTPMethod$POST_getInstance
  });
  var package$io = _.io || (_.io = {});
  var package$data2viz = package$io.data2viz || (package$io.data2viz = {});
  var package$request = package$data2viz.request || (package$data2viz.request = {});
  package$request.HTTPMethod = HTTPMethod;
  package$request.request_vi5yqy$ = request;
  package$request.Request = Request;
  Kotlin.defineModule('d2v-request-js', _);
  return _;
}));

//# sourceMappingURL=d2v-request-js.js.map
