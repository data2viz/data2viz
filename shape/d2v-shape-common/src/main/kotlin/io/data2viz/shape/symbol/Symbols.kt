package io.data2viz.shape.symbol

import io.data2viz.path.PathAdapter
import io.data2viz.shape.pi
import io.data2viz.shape.tau
import io.data2viz.shape.Symbol
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class Circle : Symbol {
    override fun <C : PathAdapter> draw(context: C, size: Double): C {
        val r = sqrt(size / pi)
        with(context) {
            moveTo(r, .0)
            arc(.0, .0, r, .0, tau)
        }
        return context
    }
}

class Cross : Symbol {
    override fun <C : PathAdapter> draw(context: C, size: Double): C {
        val r = sqrt(size / 5) / 2
        val r3 = 3 * r
        with(context) {
            moveTo(-r3, -r)
            lineTo(-r, -r)
            lineTo(-r, -r3)
            lineTo(r, -r3)
            lineTo(r, -r)
            lineTo(r3, -r)
            lineTo(r3, r)
            lineTo(r, r)
            lineTo(r, r3)
            lineTo(-r, r3)
            lineTo(-r, r)
            lineTo(-r3, r)
            closePath()
        }
        return context
    }
}

class Diamond : Symbol {

    private val tan30 = sqrt(1 / 3.0)
    private val tan30_2 = tan30 * 2;

    override fun <C : PathAdapter> draw(context: C, size: Double): C {
        val y = sqrt(size / tan30_2)
        val x = y * tan30
        with(context) {
            moveTo(.0, -y)
            lineTo(x, .0)
            lineTo(.0, y)
            lineTo(-x, .0)
            closePath()
        }
        return context
    }
}

class Square : Symbol {
    override fun <C : PathAdapter> draw(context: C, size: Double): C {
        val w = sqrt(size)
        val x = -w / 2.0
        context.rect(x, x, w, w)
        return context
    }
}

class Star : Symbol {

    private val ka = 0.89081309152928522810
    private val kr = sin(pi / 10) / sin(7 * pi / 10)
    private val kx = sin(tau / 10) * kr
    private val ky = -cos(tau / 10) * kr

    override fun <C : PathAdapter> draw(context: C, size: Double): C {
        val r = sqrt(size * ka)
        val x = kx * r
        val y = ky * r
        context.moveTo(.0, -r)
        context.lineTo(x, y)
        for (i in 1 until 5) {
            val a = tau * i / 5.0
            val c = cos(a)
            val s = sin(a)
            context.lineTo(s * r, -c * r)
            context.lineTo(c * x - s * y, s * x + c * y)
        }
        context.closePath();
        return context
    }
}

class Triangle : Symbol {

    private val sqrt3 = sqrt(3.0)

    override fun <C : PathAdapter> draw(context: C, size: Double): C {
        val y = -sqrt(size / (sqrt3 * 3))
        with(context) {
            moveTo(.0, y * 2)
            lineTo(-sqrt3 * y, -y)
            lineTo(sqrt3 * y, -y)
            closePath()
        }
        return context
    }
}


class Wye : Symbol {

    private val c = -0.5
    private val s = sqrt(3.0) / 2
    private val k = 1 / sqrt(12.0)
    private val a = (k / 2 + 1) * 3

    override fun <C : PathAdapter> draw(context: C, size: Double): C {
        val r = sqrt(size / a)
        val x0 = r / 2
        val y0 = r * k
        val x1 = x0
        val y1 = r * k + r
        val x2 = -x1
        val y2 = y1
        with(context) {
            moveTo(x0, y0)
            lineTo(x1, y1)
            lineTo(x2, y2)
            lineTo(c * x0 - s * y0, s * x0 + c * y0)
            lineTo(c * x1 - s * y1, s * x1 + c * y1)
            lineTo(c * x2 - s * y2, s * x2 + c * y2)
            lineTo(c * x0 + s * y0, c * y0 - s * x0)
            lineTo(c * x1 + s * y1, c * y1 - s * x1)
            lineTo(c * x2 + s * y2, c * y2 - s * x2)
            closePath()
        }
        return context
    }
}