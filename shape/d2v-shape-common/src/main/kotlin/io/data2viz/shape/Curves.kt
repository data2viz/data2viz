package io.data2viz.shape

import io.data2viz.path.PathAdapter
import io.data2viz.shape.curve.*

interface Curve {
    val context: PathAdapter
    fun areaStart()
    fun areaEnd()
    fun lineStart()
    fun lineEnd()
    fun point(x: Double, y: Double)
}

object curves {
    val basis                   = {context: PathAdapter -> Basis(context) }
    val basisClosed             = {context: PathAdapter -> BasisClosed(context) }
    val basisOpen               = {context: PathAdapter -> BasisOpen(context) }
    val bundle                  = {context: PathAdapter -> Bundle(context) }
    val cardinal                = {context: PathAdapter -> Cardinal(context) }
    val cardinalClosed          = {context: PathAdapter -> CardinalClosed(context) }
    val cardinalOpen            = {context: PathAdapter -> CardinalOpen(context) }
    val catmullRom              = {context: PathAdapter -> CatmullRom(context) }
    val catmullRomClosed        = {context: PathAdapter -> CatmullRomClosed(context) }
    val catmullRomOpen          = {context: PathAdapter -> CatmullRomOpen(context) }
    val linear                  = {context: PathAdapter -> Linear(context) }
    val linearClosed            = {context: PathAdapter -> LinearClosed(context) }
    val monotoneX               = {context: PathAdapter -> MonotoneX(context) }
    val monotoneY               = {context: PathAdapter -> MonotoneY(context) }
    val natural                 = {context: PathAdapter -> Natural(context) }
//    val radialLinear            = {context: PathAdapter -> RadialLinear(context) }
//    val radialBasis             = {context: PathAdapter -> Radial(context, Basis(context)) }
//    val radialLinearClosed      = {context: PathAdapter -> Radial(context, LinearClosed(context)) }
    val step                    = {context: PathAdapter -> Step(context) }
    val stepBefore              = {context: PathAdapter -> StepBefore(context) }
    val stepAfter               = {context: PathAdapter -> StepAfter(context) }
}

object areas {
    val default                 = {context: PathAdapter -> Linear(context) }
    val basis                   = {context: PathAdapter -> Basis(context) }
}