package io.data2viz.shape.curve

import io.data2viz.path.PathAdapter
import io.data2viz.shape.Curve
import kotlin.math.cos
import kotlin.math.sin

abstract class AbstractRadial(override val context: PathAdapter, val curve: Curve) : Curve {

    override fun areaEnd() {
        curve.areaEnd()
    }

    override fun lineStart() {
        curve.lineStart()
    }

    override fun lineEnd() {
        curve.lineEnd()
    }

    override fun areaStart() {
        curve.areaStart()
    }

    // TODO : rename a and r instead of x and y ?
    override fun point(x: Number, y: Number) {
        curve.point(y.toDouble() * sin(x.toDouble())+50.0, y.toDouble() * -cos(x.toDouble())+50.0);
    }
}

class RadialLinear(context: PathAdapter) : AbstractRadial(context, Linear(context))
class Radial(context: PathAdapter, curve: Curve) : AbstractRadial(context, curve)