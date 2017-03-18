import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.asList
import kotlin.browser.document

fun <T: Node> selectAll(selectors:String, init: T.(Int) -> Unit = {}){
    document.querySelectorAll(selectors).asList().forEachIndexed { index, node ->
        @Suppress("UNCHECKED_CAST")
        init(node as T, index)
    }
}

fun select(selectors: String, init: Element.() -> Unit = {}) =
        document.querySelector(selectors).apply { this?.init() }

fun Element.appendSVG(elementName: String, init: Element.() -> Unit){
    this.appendChild(kotlin.browser.document.createElementNS("http://www.w3.org/2000/svg", elementName).apply { init() })
}

