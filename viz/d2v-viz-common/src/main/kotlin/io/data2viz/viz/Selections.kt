package io.data2viz.viz


expect inline fun <reified E : VizElement, D> VizElement.selectElement(
	selector: TypeSelector<E>,
	data: List<D>,
	init: Selection<E, D>.() -> Unit
)

class SelectionEnterAccessor<E, D>(var index: Int, var element: E, var datum: D)
class SelectionUpdateAccessor<E, D>(var index: Int, var element: E, var datum: D)

class Selection<E : VizElement, D>(
	selectedElements: List<E>,
	val data: List<D>,
	val creator: () -> VizElement
) {


	val elements: MutableList<E> = selectedElements.toMutableList()

	var onEnter: (SelectionEnterAccessor<E, D>.() -> Unit) = {}
	var onUpdate: (SelectionUpdateAccessor<E, D>.() -> Unit) = {}


	/**
	 * Compare the size of the selection and the data.
	 * If data.size > element.size we must process the onEnter function for new data.
	 */
	fun processEnterUpdateExit() {
		val enterdata = if (elements.size < data.size) data.drop(elements.size) else listOf()

		if (enterdata.isNotEmpty()) {
			enterdata.forEachIndexed { index, datum ->
				val element = creator()
				onEnter(SelectionEnterAccessor(index, element as E, datum))
				elements.add(element)
			}
		}

		data.forEachIndexed { i, datum ->
			onUpdate(SelectionUpdateAccessor(i, elements[i] as E, datum))
		}
	}
}
