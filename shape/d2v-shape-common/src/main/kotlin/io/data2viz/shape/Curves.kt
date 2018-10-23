package io.data2viz.shape

import io.data2viz.geom.Path
import io.data2viz.shape.curve.*

interface Curve {
    val context: Path
    fun areaStart()
    fun areaEnd()
    fun lineStart()
    fun lineEnd()
    fun point(x: Double, y: Double)
}

// TODO maybe give an alias name for a "(Path) -> Curve" object
object curves {
    val basis                   = {context: Path -> Basis(context) }
    val basisClosed             = {context: Path -> BasisClosed(context) }
    val basisOpen               = {context: Path -> BasisOpen(context) }
    val bundle                  = {context: Path -> Bundle(context) }
    val cardinal                = {context: Path -> Cardinal(context) }
    val cardinalClosed          = {context: Path -> CardinalClosed(context) }
    val cardinalOpen            = {context: Path -> CardinalOpen(context) }
    val catmullRom              = {context: Path -> CatmullRom(context) }
    val catmullRomClosed        = {context: Path -> CatmullRomClosed(context) }
    val catmullRomOpen          = {context: Path -> CatmullRomOpen(context) }
    val linear                  = {context: Path -> Linear(context) }
    val linearClosed            = {context: Path -> LinearClosed(context) }
    val monotoneX               = {context: Path -> MonotoneX(context) }
    val monotoneY               = {context: Path -> MonotoneY(context) }
    val natural                 = {context: Path -> Natural(context) }
//    val radialLinear            = {context: Path -> RadialLinear(context) }
//    val radialBasis             = {context: Path -> Radial(context, Basis(context)) }
//    val radialLinearClosed      = {context: Path -> Radial(context, LinearClosed(context)) }
    val step                    = {context: Path -> Step(context) }
    val stepBefore              = {context: Path -> StepBefore(context) }
    val stepAfter               = {context: Path -> StepAfter(context) }
}

object areas {
    val default                 = {context: Path -> Linear(context) }
    val basis                   = {context: Path -> Basis(context) }
}