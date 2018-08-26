package io.data2viz.viz

import kotlin.reflect.KProperty

// types
val circle  = TypeSelector(newCircle())
val line    = TypeSelector(newLine())
val rect    = TypeSelector(newRect())
val text    = TypeSelector(newText())


fun cssclass() = CssClassDelegate()

class CssClassDelegate {
    operator fun getValue(from: Any?, property: KProperty<*>): CssClass {
        return CssClass(property.name)
    }
}

data class CssClass(val name: String)

abstract class Selector {

    var parent: Pair<Selector, Combinator>? = null

    val selectors = mutableListOf<Selector>()

    abstract fun copy(): Selector

    fun copyParentAndSelectors(to: Selector) {
        parent?.let { to.parent = it.first to it.second }
        selectors.forEach {
            to.selectors.add(it.copy())
        }
    }

    infix fun <E : VizElement> children(rightSelector: TypeSelector<E>): TypeSelector<E> {
        val copy = rightSelector.copy()
        copy.parent = copy() to Combinator.CHILDREN
        return copy as TypeSelector<E>
    }

}

fun c(cssClass: CssClass): ClassSelector = ClassSelector(cssClass.name)

data class ClassSelector(val name: String) : Selector() {

    override fun copy(): Selector {
        val copy = ClassSelector(name)
        copyParentAndSelectors(copy)
        return copy
    }

    override fun toString() = ".$name" + selectors.joinToString("")

    fun c(cssClass: CssClass): ClassSelector {
        val copy = copy()
        copy.selectors.add(ClassSelector(cssClass.name))
        return copy as ClassSelector
    }

}


enum class Combinator(val asText: String) {
    CHILDREN(">"), DESCENDENT(" ")
}


class TypeSelector<E : VizElement>(val prototype: E) : Selector() {
    val name: String
        get() = prototype::class.simpleName!!

    override fun copy(): Selector  {
        val copy = TypeSelector(prototype)
        copyParentAndSelectors(copy)
        return copy
    }

    fun c(cssClass: CssClass): TypeSelector<E> {
        val copy = copy()
        copy.selectors.add(ClassSelector(cssClass.name))
        return copy as TypeSelector<E>
    }

    fun asText() = (parent?.let { "${it.first} ${it.second.asText} " } ?: "") +
            name.dropLast("Jfx".length) + selectors.joinToString("")


    override fun toString(): String {
        return asText()
    }
}
