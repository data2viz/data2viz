import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.asList
import kotlin.browser.document
import kotlin.dom.addClass

fun selectAllDivTiti(){
    selectAll<Element>("div.titi") { idx ->
        textContent = "Line $idx"
        addClass("modified")
    }
}
