package io.data2viz.shape

import io.data2viz.path.PathAdapter
import io.data2viz.shape.curve.*

interface Curve {
    val context: PathAdapter
    fun areaStart()
    fun areaEnd()
    fun lineStart()
    fun lineEnd()
    fun point(x: Number, y: Number)
}


object curves {
    val basis = {context: PathAdapter -> Basis(context) }
    val basisClosed = {context: PathAdapter -> BasisClosed(context) }
    val basisOpen = {context: PathAdapter -> BasisOpen(context) }
    val bundle = {context: PathAdapter -> Bundle(context) }
    val cardinal = {context: PathAdapter -> Cardinal(context) }
    val cardinalClosed = {context: PathAdapter -> CardinalClosed(context) }
    val cardinalOpen = {context: PathAdapter -> CardinalOpen(context) }
    val catmullRom = {context: PathAdapter -> CatmullRom(context) }
    val catmullRomClosed = {context: PathAdapter -> CatmullRomClosed(context) }
    val catmullRomOpen = {context: PathAdapter -> CatmullRomOpen(context) }
    val linear = {context: PathAdapter -> Linear(context) }
    val linearClosed = {context: PathAdapter -> LinearClosed(context) }
    val natural = {context: PathAdapter -> Natural(context) }
    val step = {context: PathAdapter -> Step(context) }
    val stepBefore = {context: PathAdapter -> StepBefore(context) }
    val stepAfter = {context: PathAdapter -> StepAfter(context) }
}
