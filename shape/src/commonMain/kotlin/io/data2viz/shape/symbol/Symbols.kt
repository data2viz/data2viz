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

package io.data2viz.shape.symbol

import io.data2viz.geom.Path
import io.data2viz.geom.Point
import io.data2viz.shape.Symbol
import io.data2viz.shape.pi
import io.data2viz.shape.tau
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

public class Circle : Symbol {
    @Deprecated(
        "Use renderArea or renderRadius instead (defaults to renderArea).",
        replaceWith = ReplaceWith("renderArea(path, size, position)")
    )
    override fun <C : Path> render(path: C, size: Double, position: Point): C =
        renderArea(path, size, position)

    override fun <C : Path> renderArea(path: C, area: Double, position: Point): C =
        renderRadius(path, sqrt(area / pi), position)

    override fun <C : Path> renderRadius(path: C, radius: Double, position: Point): C {
        with(path) {
            moveTo(position.x + radius, position.y)
            arc(
                centerX = position.x,
                centerY = position.y,
                radius = radius,
                startAngle = .0,
                endAngle = pi
            )
            arc(
                centerX = position.x,
                centerY = position.y,
                radius = radius,
                startAngle = pi,
                endAngle = tau
            )
            closePath()
        }
        return path
    }
}

public class Cross : Symbol {
    @Deprecated(
        "Use renderArea or renderRadius instead (defaults to renderArea).",
        replaceWith = ReplaceWith("renderArea(path, size, position)")
    )
    override fun <C : Path> render(path: C, size: Double, position: Point): C =
        renderArea(path, size, position)

    override fun <C : Path> renderArea(path: C, area: Double, position: Point): C =
        renderRadius(path, sqrt(area / 5) / 2, position)

    override fun <C : Path> renderRadius(path: C, radius: Double, position: Point): C {
        val r3 = 3 * radius
        with(path) {
            moveTo(position.x - r3, position.y - radius )
            lineTo(position.x - radius , position.y - radius )
            lineTo(position.x - radius , position.y - r3)
            lineTo(position.x + radius , position.y - r3)
            lineTo(position.x + radius , position.y - radius )
            lineTo(position.x + r3, position.y - radius )
            lineTo(position.x + r3, position.y + radius )
            lineTo(position.x + radius , position.y + radius )
            lineTo(position.x + radius , position.y + r3)
            lineTo(position.x - radius , position.y + r3)
            lineTo(position.x - radius , position.y + radius )
            lineTo(position.x - r3, position.y + radius )
            closePath()
        }
        return path
    }
}

public class Diamond : Symbol {

    private val tan30 = sqrt(1 / 3.0)
    private val tan30_2 = tan30 * 2

    @Deprecated(
        "Use renderArea or renderRadius instead (defaults to renderArea).",
        replaceWith = ReplaceWith("renderArea(path, size, position)")
    )
    override fun <C : Path> render(path: C, size: Double, position: Point): C =
        renderArea(path, size, position)

    override fun <C : Path> renderArea(path: C, area: Double, position: Point): C =
        renderRadius(path, sqrt(area / tan30_2), position)

    override fun <C : Path> renderRadius(path: C, radius: Double, position: Point): C {
        val x = radius * tan30
        with(path) {
            moveTo(position.x, position.y - radius)
            lineTo(position.x + x, position.y)
            lineTo(position.x, position.y + radius)
            lineTo(position.x - x, position.y)
            closePath()
        }
        return path
    }
}

public class Square : Symbol {
    @Deprecated(
        "Use renderArea or renderRadius instead (defaults to renderArea).",
        replaceWith = ReplaceWith("renderArea(path, size, position)")
    )
    override fun <C : Path> render(path: C, size: Double, position: Point): C =
        renderArea(path, size, position)

    override fun <C : Path> renderArea(path: C, area: Double, position: Point): C =
        renderRadius(path, sqrt(area), position)

    override fun <C : Path> renderRadius(path: C, radius: Double, position: Point): C {
        val x = -radius / 2.0

        path.rect(position.x + x, position.y + x, radius, radius)
        return path
    }
}

public class Star : Symbol {

    private val ka = 0.89081309152928522810
    private val kr = sin(pi / 10) / sin(7 * pi / 10)
    private val kx = sin(tau / 10) * kr
    private val ky = -cos(tau / 10) * kr

    @Deprecated(
        "Use renderArea or renderRadius instead (defaults to renderArea).",
        replaceWith = ReplaceWith("renderArea(path, size, position)")
    )
    override fun <C : Path> render(path: C, size: Double, position: Point): C =
        renderArea(path, size, position)

    override fun <C : Path> renderArea(path: C, area: Double, position: Point): C =
        renderRadius(path, sqrt(area * ka), position)

    override fun <C : Path> renderRadius(path: C, radius: Double, position: Point): C {
        val x = kx * radius
        val y = ky * radius
        path.moveTo(position.x, position.y - radius)
        path.lineTo(position.x + x, position.y + y)
        for (i in 1 until 5) {
            val a = tau * i / 5.0
            val c = cos(a)
            val s = sin(a)
            path.lineTo(position.x + (s * radius), position.y - (c * radius))
            path.lineTo(position.x + (c * x) - (s * y), position.y + (s * x) + (c * y))
        }
        path.closePath()
        return path
    }
}

public class Triangle : Symbol {

    private val sqrt3 = sqrt(3.0)

    @Deprecated(
        "Use renderArea or renderRadius instead (defaults to renderArea).",
        replaceWith = ReplaceWith("renderArea(path, size, position)")
    )
    override fun <C : Path> render(path: C, size: Double, position: Point): C =
        renderArea(path, size, position)

    override fun <C : Path> renderArea(path: C, area: Double, position: Point): C =
        renderRadius(path, -sqrt(area / (sqrt3 * 3)), position)

    override fun <C : Path> renderRadius(path: C, radius: Double, position: Point): C {
        with(path) {
            moveTo(position.x, position.y + (radius * 2))
            lineTo(position.x - (sqrt3 * radius), position.y - radius)
            lineTo(position.x + (sqrt3 * radius), position.y - radius)
            closePath()
        }
        return path
    }
}



public class Wye : Symbol {

    private val c = -0.5
    private val s = sqrt(3.0) / 2
    private val k = 1 / sqrt(12.0)
    private val a = (k / 2 + 1) * 3

    @Deprecated(
        "Use renderArea or renderRadius instead (defaults to renderArea).",
        replaceWith = ReplaceWith("renderArea(path, size, position)")
    )
    override fun <C : Path> render(path: C, size: Double, position: Point): C =
        renderArea(path, size, position)

    override fun <C : Path> renderArea(path: C, area: Double, position: Point): C =
        renderRadius(path, sqrt(area / a), position)

    override fun <C : Path> renderRadius(path: C, radius: Double, position: Point): C {
        val x0 = radius / 2
        val y0 = radius * k
        val x1 = x0
        val y1 = radius * k + radius
        val x2 = -x1
        val y2 = y1
        with(path) {
            moveTo(position.x + x0, position.y + y0)
            lineTo(position.x + x1, position.y + y1)
            lineTo(position.x + x2, position.y + y2)
            lineTo(position.x + (c * x0) - (s * y0), position.y + (s * x0) + (c * y0))
            lineTo(position.x + (c * x1) - (s * y1), position.y + (s * x1) + (c * y1))
            lineTo(position.x + (c * x2) - (s * y2), position.y + (s * x2) + (c * y2))
            lineTo(position.x + (c * x0) + (s * y0), position.y + (c * y0) - (s * x0))
            lineTo(position.x + (c * x1) + (s * y1), position.y + (c * y1) - (s * x1))
            lineTo(position.x + (c * x2) + (s * y2), position.y + (c * y2) - (s * x2))
            closePath()
        }
        return path
    }
}
