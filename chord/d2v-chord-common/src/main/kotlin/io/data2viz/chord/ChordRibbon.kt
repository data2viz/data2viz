package io.data2viz.chord

import io.data2viz.path.PathAdapter
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


private val halfPi = PI / 2


/**
 * Generates a ribbon for a Chord Diagram with the given radius.
 */
fun ribbon(radius: Double): (Chord, PathAdapter) -> PathAdapter = { chord, path ->
    val s = chord.source
    val t = chord.target
    val sa0 = s.startAngle - halfPi
    val sa1 = s.endAngle - halfPi
    val sx0 = radius * cos(sa0)
    val sy0 = radius * sin(sa0)
    val ta0 = t.startAngle - halfPi
    val ta1 = t.endAngle - halfPi

    path.moveTo(sx0, sy0)
    path.arc(.0, .0, radius, sa0, sa1)
    if (sa0 != ta0 || sa1 != ta1) {
        path.quadraticCurveTo(.0, .0, radius * cos(ta0), radius * sin(ta0))
        path.arc(.0, .0, radius, ta0, ta1)
    }
    path.quadraticCurveTo(.0, .0, sx0, sy0)
    path.closePath()
    path
}