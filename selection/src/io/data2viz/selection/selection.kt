package io.data2viz.selection

import org.w3c.dom.Element
import org.w3c.dom.HTMLElement
import org.w3c.dom.asList
import kotlin.browser.document


fun select(selectors: String, init: Selection.() -> Unit = {}): Selection {
    val elements = document.querySelector(selectors)?.let { arrayOf(it) } ?: emptyArray()
    return Selection(arrayOf(Group(elements, document.documentElement!!)), arrayOf(document.documentElement!!), init)
}

fun selectAll(selectors: String, init: Selection.() -> Unit = {}): Selection {
    val elements = document.querySelectorAll(selectors).asList().map { it as Element }.toTypedArray()
    return Selection(arrayOf(Group(elements, document.documentElement!!)), arrayOf(document.documentElement!!), init)
}

fun <D> selectAllAndBind(selectors: String, data: Iterable<D>, init: SelectionWithData<D>.() -> Unit): SelectionWithData<D> {
    val elements = document.querySelectorAll(selectors).asList().map { it as Element }.toTypedArray()
    return SelectionWithData(arrayOf(Group(elements, document.documentElement!!)), arrayOf(document.documentElement!!), data, init)
}

fun Element.append(elementName: String, init: Element.() -> Unit = {}): Element {
    val newElement = document.createElementNS(namespaceURI, elementName)
    this.appendChild(newElement.apply { init() })
    return newElement
}


class Group(val elements : Array<Element>, val parentNode: Element)

open class Selection(val groups: Array<Group>, val parents: Array<Element>, init: Selection.() -> Unit) {
    init {
        init()
    }

    fun selectAll(selectors: String){
        for (group in groups) {
            for (element in group.elements) {
                element.querySelectorAll(selectors)
            }
        }
    }

//    fun select(selectors: String, init: Selection.() -> Unit = {}):Selection{
//
//    }
//
//    fun selectAll(selectors: String, init: Selection.() -> Unit = {}): Selection {
//        val group = document.querySelectorAll(selectors).asList().map { it as Element
// }.toTypedArray()
//        return Selection(arrayOf(group), arrayOf(document.documentElement!!), init)
//    }


    fun style(propertyName: String, propertyValue: String) {
        for (group in groups) {
            for (element in group.elements) {
                (element as HTMLElement).style.setProperty(propertyName, propertyValue)
            }
        }
    }

    fun style(propertyName: String, propertyfromIndex: (Int) -> String) {
        for (group in groups) {
            for (element in group.elements.withIndex()) {
                ((element.value) as HTMLElement).style.setProperty(propertyName, propertyfromIndex(element.index))
            }
        }
    }
}


class SelectionWithData<D>(groups: Array<Group>, parents: Array<Element>, data: Iterable<D>, init: SelectionWithData<D>.() -> Unit) :
        Selection(groups, parents, {}) {

    init {
        init()
    }

    fun enter(enteringFunction: Element.(D) -> Unit) {

    }
}
