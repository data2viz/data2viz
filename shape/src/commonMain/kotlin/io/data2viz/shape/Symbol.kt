/*
 * Copyright (c) 2018-2021. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.shape

import io.data2viz.geom.Path
import io.data2viz.geom.Point
import io.data2viz.shape.symbol.*

/**
 * A Symbol is a shape you can render based on whether its radius or its area.
 */
public interface  Symbol {
    @Deprecated("Use renderArea or renderRadius instead (defaults to renderArea).", ReplaceWith("renderArea(path, size, position)"))
    public fun <C : Path> render(path: C, size: Double, position: Point = Point.origin): C

    /**
     * Render the [Symbol] based on the size of its area.
     */
    public fun <C : Path> renderArea(path: C, area: Double, position: Point = Point.origin): C

    /**
     * Render the [Symbol] based on the size of its radius.
     */
    public fun <C : Path> renderRadius(path: C, radius: Double, position: Point = Point.origin): C
}

public enum class Symbols {
    Circle, Cross, Diamond, Square, Star, Triangle, Wye
}

public val Symbols.symbol: Symbol
    get() = when (this) {
        Symbols.Cross -> Cross()
        Symbols.Diamond -> Diamond()
        Symbols.Square -> Square()
        Symbols.Star -> Star()
        Symbols.Triangle -> Triangle()
        Symbols.Wye -> Wye()
        Symbols.Circle -> Circle()
    }

public fun <T> render(init: SymbolGenerator<T>.() -> Unit): SymbolGenerator<T> = SymbolGenerator<T>().apply(init)


public class SymbolGenerator<T> {

    public var size: (T) -> Double = const(64.0)
    public var type: (T) -> Symbol = { Circle() }

    public fun <C : Path> render(args: T, path: C): C {
        type(args).renderArea(path, size(args))
        return path
    }
}
