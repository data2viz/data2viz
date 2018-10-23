package io.data2viz.shape.curve

import io.data2viz.geom.Path
import io.data2viz.shape.Curve
import kotlin.math.cos
import kotlin.math.sin

abstract class AbstractRadial(override val path: Path, val curve: Curve) : Curve {

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
    override fun point(x: Double, y: Double) {
        curve.point(y * sin(x), y * -cos(x));
    }
}

class RadialLinear(path: Path) : AbstractRadial(path, Linear(path))
class Radial(path: Path, curve: Curve) : AbstractRadial(path, curve)