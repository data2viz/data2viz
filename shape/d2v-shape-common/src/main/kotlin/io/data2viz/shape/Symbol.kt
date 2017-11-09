package io.data2viz.shape

import io.data2viz.path.PathAdapter
import io.data2viz.shape.symbol.*

interface Symbol {
    fun <C : PathAdapter> draw(context: C, size: Double): C
}

val symbols = arrayOf("Circle", "Cross", "Diamond", "Square", "Star", "Triangle", "Wye")

fun <T> symbol(init: SymbolGenerator<T>.() -> Unit) = SymbolGenerator<T>().apply(init)
class SymbolGenerator<T> {

    var size: (T) -> Double = const(64.0)
    var type: (T) -> Symbol = { Circle() }

    fun <C : PathAdapter> symbol(args: T, context: C): C {
        type(args).draw(context, size(args))
        return context
    }
}