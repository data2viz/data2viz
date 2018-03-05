package io.data2viz.viz

import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.asList

actual inline fun <reified E : VizElement, D> VizElement.selectElement(
    selector: TypeSelector<E>,
    data: List<D>,
    init: Selection<E, D>.() -> Unit
) {
    val select = selector.toString().toLowerCase()
    val lookupAll = (this as DOMVizElement).domElement.querySelectorAll(select)
        .asList()
        .map { it.toViz() }


    val creator: () -> VizElement = when (E::class) {
        Circle::class -> { { newCircle()}}
        Line::class -> { { newCircle() }}
        else -> {
            throw IllegalArgumentException("Unknown type ${E::class}")
        }
    }
    val selection = Selection(lookupAll as List<E>, data, creator)
    selection.init()
    selection.processEnterUpdateExit()

}


fun Node.toViz() =
    when ((this as Element).nodeName) {
        "g"         -> ParentElement(this)
        "line"      -> LineDOM(this)
        "rect"      -> RectDOM(this)
        "circle"    -> CircleDOM(this)
        "text"      -> TextDOM(this)
        else -> {
            throw IllegalArgumentException("Unknown type as VizElement :: $this")
        }
    }
