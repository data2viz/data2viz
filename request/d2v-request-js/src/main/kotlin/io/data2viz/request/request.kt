package io.data2viz.request

import org.w3c.dom.events.Event
import org.w3c.xhr.EMPTY
import org.w3c.xhr.XMLHttpRequest
import org.w3c.xhr.XMLHttpRequestResponseType


enum class HTTPMethod {
    GET, POST
}


fun request(url: String, init:Request.() -> Unit = {}) = Request(url).apply(init)

class Request(val url: String) {

    var httpMethod = HTTPMethod.GET
    var mimeType: String? = null
    var responseType = XMLHttpRequestResponseType.EMPTY
    var user:String? = null
    var password:String? = null
    val headers = mutableMapOf<String, String>()
    var timeout = 0

    val xhr = XMLHttpRequest()
    // todo For IE 8 and 9 with CORS suppport, we need to use XDomainRequest.

    init {
        xhr.onload = {event -> respond(event)}
        xhr.onerror = {event -> respond(event)}
        xhr.ontimeout = {event -> respond(event)}
    }

    fun header(key:String, value:String){
        headers.put(key, value)
    }

    fun send(callback: (XMLHttpRequest) -> Unit){
        xhr.open(httpMethod.name, url, true, user, password)
        if(!mimeType.isNullOrBlank() && !headers.containsKey("accept")) headers.set("accept", mimeType + ",*/*")
        headers.forEach { xhr.setRequestHeader( it.key, it.value) }
        if(!mimeType.isNullOrBlank())xhr.overrideMimeType(mimeType!!)
        xhr.responseType = responseType
        if (timeout>0 ) xhr.timeout = timeout


        xhr.onload = {
            callback(xhr)
        }
        //todo dispatch event beforesend
        xhr.send()
    }

    fun get(callback: (XMLHttpRequest) -> Unit){
        send(callback)
    }

    fun respond(event: Event): dynamic{
        xhr.status
        return ""
    }

    fun abort() {
        xhr.abort()
    }

}
