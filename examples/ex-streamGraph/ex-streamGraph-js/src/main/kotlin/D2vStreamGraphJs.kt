import io.data2viz.examples.streamGraph.*
import io.data2viz.viz.selectOrCreateSvg
import io.data2viz.viz.viz
import io.data2viz.shape.curves
import io.data2viz.shape.stack.StackOffset
import io.data2viz.shape.stack.StackOrder
import org.w3c.dom.Element
import org.w3c.dom.HTMLSelectElement
import kotlin.browser.document

@Suppress("unused")

fun main(args: Array<String>) {

    val curve = document.querySelector("#curve")!! as HTMLSelectElement
    val offset = document.querySelector("#offset")!! as HTMLSelectElement
    val order = document.querySelector("#order")!! as HTMLSelectElement

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

    curve.addEventListener("change", {
        vizConfig.curve = when(curve.selectedIndex) {
            1 -> curves.linear
            2 -> curves.natural
            3 -> curves.catmullRom
            4 -> curves.cardinal
            5 -> curves.step
            else -> curves.basis
        }
        draw()
    })
    offset.addEventListener("change", {
        vizConfig.offset = when(offset.selectedIndex) {
            1 -> StackOffset.EXPAND
            2 -> StackOffset.DIVERGING
            3 -> StackOffset.SILHOUETTE
            4 -> StackOffset.WIGGLE
            else -> StackOffset.NONE
        }
        draw()
    })
    order.addEventListener("change", {
        vizConfig.order = when(order.selectedIndex) {
            1 -> StackOrder.ASCENDING
            2 -> StackOrder.DESCENDING
            3 -> StackOrder.REVERSE
            4 -> StackOrder.INSIDEOUT
            else -> StackOrder.NONE
        }
        draw()
    })

    draw()
}
