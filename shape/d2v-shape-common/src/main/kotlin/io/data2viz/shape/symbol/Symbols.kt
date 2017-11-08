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
        context.moveTo(r, .0)
        context.arc(.0, .0, r, .0, tau)
        return context
    }
}

class Cross : Symbol {
    override fun <C : PathAdapter> draw(context: C, size: Double): C {
        val r = sqrt(size / 5) / 2
        val r3 = 3 * r
        context.moveTo(-r3, -r)
        context.lineTo(-r, -r)
        context.lineTo(-r, -r3)
        context.lineTo(r, -r3)
        context.lineTo(r, -r)
        context.lineTo(r3, -r)
        context.lineTo(r3, r)
        context.lineTo(r, r)
        context.lineTo(r, r3)
        context.lineTo(-r, r3)
        context.lineTo(-r, r)
        context.lineTo(-r3, r)
        context.closePath()
        return context
    }
}

class Diamond : Symbol {

    private val tan30 = sqrt(1 / 3.0)
    private val tan30_2 = tan30 * 2;

    override fun <C : PathAdapter> draw(context: C, size: Double): C {
        val y = sqrt(size / tan30_2)
        val x = y * tan30
        context.moveTo(.0, -y)
        context.lineTo(x, .0)
        context.lineTo(.0, y)
        context.lineTo(-x, .0)
        context.closePath()
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
        context.moveTo(.0, y * 2)
        context.lineTo(-sqrt3 * y, -y)
        context.lineTo(sqrt3 * y, -y)
        context.closePath()
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
        context.moveTo(x0, y0)
        context.lineTo(x1, y1)
        context.lineTo(x2, y2)
        context.lineTo(c * x0 - s * y0, s * x0 + c * y0)
        context.lineTo(c * x1 - s * y1, s * x1 + c * y1)
        context.lineTo(c * x2 - s * y2, s * x2 + c * y2)
        context.lineTo(c * x0 + s * y0, c * y0 - s * x0)
        context.lineTo(c * x1 + s * y1, c * y1 - s * x1)
        context.lineTo(c * x2 + s * y2, c * y2 - s * x2)
        context.closePath()
        return context
    }
}