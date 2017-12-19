import io.data2viz.examples.streamGraph.*
import io.data2viz.viz.selectOrCreateSvg
import io.data2viz.viz.viz
import org.w3c.dom.HTMLSelectElement
import kotlin.browser.document
import kotlin.dom.appendElement

@Suppress("unused")

fun main(args: Array<String>) {


    fun draw() {
        val root = selectOrCreateSvg().apply {
            setAttribute("width", "${width + margins.hMargins}")
            setAttribute("height", "${height + margins.vMargins}")
        }
        root.querySelector("g")?.remove()
        root.viz {
            streamGraph()
        }
    }

    htmlSelectElement("#curve").apply {
        curveOptions.forEach { option ->
            appendElement("option") {
                if (option.first == "Basis")
                    setAttribute("selected", "true")
                this.textContent = option.first
            }
        }

        addEventListener("change", {
            vizConfig.curve = curveOptions[selectedIndex].second
            draw()
        })

    }


    htmlSelectElement("#offset").apply {
        offsetOptions.forEach { option ->
            appendElement("option") {
                if (option.first == "Wiggle")
                    setAttribute("selected", "true")
                this.textContent = option.first
            }
        }

        addEventListener("change", {
            vizConfig.offset = offsetOptions[selectedIndex].second
            draw()
        })

    }

    htmlSelectElement("#order").apply {
        orderOptions.forEach { option ->
            appendElement("option") {
                if (option.first == "None")
                    setAttribute("selected", "true")
                textContent = option.first
            }
        }

        addEventListener("change", {
            vizConfig.order = orderOptions[selectedIndex].second
            draw()
        })
    }

    draw()
}

private fun htmlSelectElement(selectionId: String) = document.querySelector(selectionId)!! as HTMLSelectElement
