package io.data2viz.viz

import javafx.scene.Node



actual inline fun <reified E : VizElement, D> VizElement.selectElement(
	selector: TypeSelector<E>,
	data: List<D>,
	init: Selection<E, D>.() -> Unit
) {
		val lookupAll = (this as JfxVizElement).jfxElement.lookupAll(selector.toString())
			.map { it.toViz() }

		val factory: () -> VizElement = when (E::class) {
				Circle::class -> { { newCircle()}}
				Line::class -> { { newLine() }}
				Rect::class -> { { newRect() }}
			else -> {
				throw IllegalArgumentException("Unknown type ${E::class}")
			}
		}
		val selection = Selection(lookupAll as List<E>, data, factory)
		selection.init()
		selection.processEnterUpdateExit()

}


fun Node.toViz() =
    when (this) {
        is JfxCircle 		-> CircleJfx(this)
        is JfxRectangle 	-> RectJfx(this)
        is JfxLine 			-> LineJfx(this)
        is JfxText 			-> TextJfx(this)
		is JfxGroup 		-> GroupJfx(this)
        else -> {
            throw IllegalArgumentException("Unknown type as VizElement :: $this")
        }
    }
