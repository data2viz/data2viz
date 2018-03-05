package io.data2viz.examples.chord


import kotlin.math.*

import io.data2viz.chord.Chord
import io.data2viz.chord.ChordGroup
import io.data2viz.core.random
import io.data2viz.chord.ChordLayout
import io.data2viz.chord.Chords
import io.data2viz.color.Color
import io.data2viz.color.LinearGradient
import io.data2viz.color.colors
import io.data2viz.shape.arc
import io.data2viz.shape.const
import io.data2viz.viz.VizContext


class Film(val name:String, val avengers:List<Avenger>)

class Avenger(val name:String)

val blackWidow = Avenger("Black Widow")
val captainAmerica = Avenger("Captain America")
val theHulk = Avenger("the Hulk")
val ironMan = Avenger("Iron Man")
val thor = Avenger("Thor")
val hawkeye = Avenger("hawkeye")

val filmIronMan1        = Film("Iron Man 1", listOf(ironMan))
val filmIronMan2        = Film("Iron Man 2", listOf(ironMan,blackWidow))
val filmIronMan3        = Film("Iron Man 3", listOf(ironMan, theHulk))
val filmAvengers1       = Film("Avengers", listOf(ironMan, captainAmerica, theHulk, thor, hawkeye, blackWidow))
val filmAvengers2       = Film("Avengers, L'ère d'Ultron", listOf(ironMan, captainAmerica, theHulk, thor, hawkeye, blackWidow))
val filmAvengers3       = Film("Avengers, Infinity War", listOf(ironMan, captainAmerica, theHulk, hawkeye, blackWidow))
val filmThor            = Film("Thor", listOf(thor, hawkeye))
val filmThor2           = Film("Thor, le monde des ténèbres", listOf(thor, captainAmerica))
val filmThor3           = Film("Thor, Ragnakok", listOf(thor))
val filmCaptainAmerica1 = Film("Captain America, First Avenger", listOf(captainAmerica))
val filmCaptainAmerica2 = Film("Captain America, Le Soldat de l'hiver", listOf(captainAmerica, blackWidow))
val filmCaptainAmerica3 = Film("Captain America, Civil War", listOf(captainAmerica, ironMan, hawkeye, blackWidow))
val films = listOf(
    filmAvengers1, filmAvengers2, filmAvengers3,
    filmCaptainAmerica1, filmCaptainAmerica2, filmCaptainAmerica3,
    filmIronMan1, filmIronMan2, filmIronMan3,
    filmThor, filmThor2, filmThor3
    )

val avengers    = listOf (blackWidow,   captainAmerica, hawkeye,    theHulk,    ironMan,    thor)
val colors      = listOf (0x301E1E,     0x083E77,       0x342350,   0x567235,   0x8B161C,   0xDF7C00).map { Color(it) }

const val width = 600.0
const val height = width
val outer = minOf(width, height) * 0.5 - 40.0
val inner = outer - 30

val chord = ChordLayout<Avenger>().apply {
    padAngle = .15
}

fun collaborations(avengers:List<Avenger>) = films.filter { it.avengers.containsAll(avengers) }.size.toDouble()

val chords: Chords = chord.chord(avengers, { a, b -> if (a==b) .0 else collaborations(listOf(a,b))})

val arc = arc<ChordGroup> {
    innerRadius = const(inner + 3)
    outerRadius = const(outer)
    startAngle = { it.startAngle }
    endAngle = { it.endAngle }
}

val ribbon = io.data2viz.chord.ribbon(inner)


fun VizContext.chordViz() {
    transform { translate(width / 2, height / 2) }

    //Drawing external groups representing avengers
    chords.groups.forEachIndexed { index, it ->
        path {
            fill = io.data2viz.examples.chord.colors[index]
            stroke = null
            arc.arc(it, this)
        }
    }

    //Todo Move in API
    fun Chord.toGradient() = LinearGradient().apply {
            x1 = inner * cos((source.endAngle - source.startAngle)/2 + source.startAngle - PI/2)
            y1 = inner * sin((source.endAngle - source.startAngle)/2 + source.startAngle - PI/2)
            x2 = inner * cos((target.endAngle - target.startAngle)/2 + target.startAngle - PI/2)
            y2 = inner * sin((target.endAngle - target.startAngle)/2 + target.startAngle - PI/2)

            //Set the starting color (at 0%)
            addColor(.0, io.data2viz.examples.chord.colors[source.index].apply { alpha = .6})
            addColor(1.0, io.data2viz.examples.chord.colors[target.index].apply { alpha = .6 })
    }

    //drawing ribbons
    chords.chords.forEach { chord ->
        path {
            fill = chord.toGradient()
            stroke = null
            ribbon(chord, this)
        }
    }
}
