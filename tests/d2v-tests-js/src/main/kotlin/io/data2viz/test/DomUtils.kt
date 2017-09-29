package io.data2viz.test

import org.w3c.dom.Element
import kotlin.browser.document


class DomUtils {
    companion object {
        val body by lazy { document.querySelector("body")!! }
    }

    fun Element.append(name:String){
        appendChild(document.createElement(name))
    }
}
