package io.data2viz.selection

import org.w3c.dom.Element
import org.w3c.dom.HTMLElement
import org.w3c.dom.asList
import kotlin.browser.document


fun select(selectors: String, init: Selection.() -> Unit = {}): Selection {
    val elements = document.querySelector(selectors)?.let { arrayOf(it) } ?: emptyArray()
    return Selection(arrayOf(Group(elements, document.documentElement!!)), init)
}

fun selectAll(selectors: String, init: Selection.() -> Unit = {}): Selection {
    val elements = document.querySelectorAll(selectors).asList().map { it as Element }.toTypedArray()
    return Selection(arrayOf(Group(elements, document.documentElement!!)), init)
}

fun <D> selectAllAndBind(selectors: String, data: Iterable<D>, init: SelectionWithData<D>.() -> Unit = {}): SelectionWithData<D> {
    val elements = document.querySelectorAll(selectors).asList().map { it as Element }.toTypedArray()
    return SelectionWithData(arrayOf(Group(elements, document.documentElement!!)) as Array<Group?>, data, init)
}

fun Element.append(elementName: String, init: Element.() -> Unit = {}): Element {
    val newElement = document.createElementNS(namespaceURI, elementName)
    this.appendChild(newElement.apply { init() })
    return newElement
}


open class Group(val elements : Array<Element>, val parentNode: Element)
class GroupAndData<D>(elements: Array<Element>, parentNode: Element, val data: Iterable<D>): Group(elements, parentNode) {

}

open class Selection(val groups: Array<Group?>, init: Selection.() -> Unit) {
    init {
        init()
    }

    fun selectAll(selectors: String, init: Selection.() -> Unit = {}): Selection {
        val subSelection = mutableListOf<Group>()
        for (group in groups.filterNotNull()) {
            for (element in group.elements) {
                val elements = element.querySelectorAll(selectors).asList().map { it as Element }.toTypedArray()
                subSelection.add(Group(elements, element))
            }
        }
        return Selection(subSelection.toTypedArray(), init)
    }


    fun style(propertyName: String, propertyValue: String) {
        for (group in groups.filterNotNull()) {
            for (element in group.elements) {
                (element as HTMLElement).style.setProperty(propertyName, propertyValue)
            }
        }
    }

    fun style(propertyName: String, propertyfromIndex: (Int) -> String) {
        for (group in groups.filterNotNull()) {
            for (element in group.elements.withIndex()) {
                ((element.value) as HTMLElement).style.setProperty(propertyName, propertyfromIndex(element.index))
            }
        }
    }
}


class SelectionWithData<D>(groups: Array<Group?>, data: Iterable<D>, init: SelectionWithData<D>.() -> Unit) :
        Selection(groups, {}) {

    init {
        val datas = data.toList()
        console.log(data)
        for ((i, group) in groups.withIndex()) {
            if(group != null){
                console.log(group)
                group.parentNode.asDynamic().__data__ = datas[i]
            } else {
                TODO()
            }
        }
        init()
    }

    fun bindByIndex(parentNode: Element, group: Group, data: Array<D>){
        val dataLength = data.size
        for (i in 0..dataLength-1){
            if(group.elements[i] != null){

            }
        }
    }

    fun enter(enteringFunction: Element.(D) -> Unit) {

    }
}
