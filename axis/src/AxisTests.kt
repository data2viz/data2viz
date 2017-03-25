import test.StringSpec
import kotlin.browser.document

class AxisTests : StringSpec() {
    init {
        "display x axis" {
            display()
        }
    }

    fun display() {

        fun node(name: String) = document.createElementNS(namespace.svg, name)

        val body = document.querySelector("body")!!
        body.appendChild(
                node("svg").apply {
                    setAttribute("width", "400")
                    setAttribute("height", "20")
                })
    }
}
