package io.data2viz.shape

import io.data2viz.geom.Path
import io.data2viz.shape.symbol.*

interface Symbol {
    fun <C : Path> render(path: C, size: Double): C
}

enum class Symbols {
    Circle, Cross, Diamond, Square, Star, Triangle, Wye
}

val Symbols.symbol: Symbol
    get() = when (this) {
        Symbols.Cross -> Cross()
        Symbols.Diamond -> Diamond()
        Symbols.Square -> Square()
        Symbols.Star -> Star()
        Symbols.Triangle -> Triangle()
        Symbols.Wye -> Wye()
        else -> Circle()
    }

fun <T> render(init: SymbolGenerator<T>.() -> Unit) = SymbolGenerator<T>().apply(init)
class SymbolGenerator<T> {

    var size: (T) -> Double = const(64.0)
    var type: (T) -> Symbol = { Circle() }

    fun <C : Path> render(args: T, path: C): C {
        type(args).render(path, size(args))
        return path
    }
}