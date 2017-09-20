package io.data2viz.shape

import curve.Basis
import curve.Linear
import curve.LinearClosed
import io.data2viz.path.PathAdapter

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
    val linear = {context: PathAdapter -> Linear(context) }
    val linearClosed = {context: PathAdapter -> LinearClosed(context) }
}
