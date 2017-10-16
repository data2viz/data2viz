package io.data2viz.shape.curve

import io.data2viz.path.PathAdapter
import io.data2viz.shape.Curve

class Bundle(override val context: PathAdapter, val beta:Double = 0.85) : Curve {

    private val basis = Basis(context)

    private var x = arrayListOf<Double>()
    private var y = arrayListOf<Double>()

    // TODO : not present in D3
    override fun areaStart() {}

    // TODO : not present in D3
    override fun areaEnd() {}

    override fun lineStart() {
        x.clear()
        y.clear()
        basis.lineStart()
    }

    override fun lineEnd() {
        val j = x.size - 1
        if (j > 0) {
            val x0 = x[0]
            val y0 = y[0]
            val dx = x[j] - x0
            val dy = y[j] - y0

            (0..j).forEach { index ->
                val t = index / j
                basis.point(
                        (beta * x[index]) + ((1 - beta) * (x0 + t * dx)),
                        (beta * y[index]) + ((1 - beta) * (y0 + t * dy))
                )
            }
        }
        basis.lineEnd()
    }

    override fun point(x: Number, y: Number) {
        this.x.add(x.toDouble())
        this.y.add(y.toDouble())
    }
}