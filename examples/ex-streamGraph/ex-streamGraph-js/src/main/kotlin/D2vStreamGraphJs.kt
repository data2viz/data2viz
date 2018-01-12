import io.data2viz.examples.streamGraph.*
import io.data2viz.viz.selectOrCreateSvg
import io.data2viz.viz.viz
import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLSelectElement
import kotlin.browser.document
import kotlin.dom.appendElement

@Suppress("unused")

fun main(args: Array<String>) {


    fun redraw() {
        val root = selectOrCreateSvg().apply {
            setAttribute("width", "${width + margins.hMargins}")
            setAttribute("height", "${height + margins.vMargins}")
        }
        root.querySelector("g")?.remove()
        root.viz {
            streamGraph()
        }
    }

    fun String.onSelect(selectChange: HTMLSelectElement.() -> Unit) {
        (document.querySelector(this)!! as HTMLSelectElement).apply {
            addEventListener("change", {
                selectChange()
                redraw()
            })

        }
    }

    val body = document.body!!

    body.append.div {
        label { +"Area spline interpolation" }
        select {
            id = "curve"
            curveOptions.forEach { option ->
                option {
                    selected = (option.first == "Basis")
                    +option.first
                }
            }
        }
    }
    body.append.div {

        label { +"Stacks offset" }
        select {
            id = "offset"
            offsetOptions.forEach { option ->
                option {
                    selected = (option.first == "Wigle")
                    +option.first
                }
            }
        }
    }
    body.append.div {
        label { +"Stacks order" }
        select {
            id = "order"
            orderOptions.forEach { option ->
                option {
                    selected = (option.first == "None")
                    +option.first
                }
            }
        }

    }


    "#curve".onSelect { vizConfig.curve = curveOptions[selectedIndex].second }
    "#offset".onSelect { vizConfig.offset = offsetOptions[selectedIndex].second }
    "#order".onSelect { vizConfig.order = orderOptions[selectedIndex].second }

    redraw()
}

