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
//        when (type(args)) {
//            "Circle" -> Circle().draw(context, size(args))
//            "Cross" -> Cross().draw(context, size(args))
//            "Diamond" -> Diamond().draw(context, size(args))
//            "Square" -> Square().draw(context, size(args))
//            "Star" -> Star().draw(context, size(args))
//            "Triangle" -> Triangle().draw(context, size(args))
//            "Wye" -> Wye().draw(context, size(args))
//            else -> Circle().draw(context, size(args))
//        }
        type(args).draw(context, size(args))

        return context
    }
}