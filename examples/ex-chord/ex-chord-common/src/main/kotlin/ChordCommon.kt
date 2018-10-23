package io.data2viz.examples.chord


import kotlin.math.*

import io.data2viz.chord.Chord
import io.data2viz.chord.ChordGroup
import io.data2viz.chord.ChordLayout
import io.data2viz.chord.Chords
import io.data2viz.color.Color
import io.data2viz.color.LinearGradient
import io.data2viz.geom.Path
import io.data2viz.shape.arcBuilder
import io.data2viz.viz.Viz
import io.data2viz.viz.viz


data class Movie(val name: String, val avengers: List<Avenger>)

data class Avenger(val name: String)

val blackWidow = Avenger("Black Widow")
val captainAmerica = Avenger("Captain America")
val theHulk = Avenger("the Hulk")
val ironMan = Avenger("Iron Man")
val thor = Avenger("Thor")
val hawkeye = Avenger("hawkeye")

val movies = listOf(
        Movie("Avengers", listOf(ironMan, captainAmerica, theHulk, thor, hawkeye, blackWidow)),
        Movie("Avengers, L'ère d'Ultron", listOf(ironMan, captainAmerica, theHulk, thor, hawkeye, blackWidow)),
        Movie("Avengers, Infinity War", listOf(ironMan, captainAmerica, theHulk, hawkeye, blackWidow)),
        Movie("Captain America, First Avenger", listOf(captainAmerica)),
        Movie("Captain America, Le Soldat de l'hiver", listOf(captainAmerica, blackWidow)),
        Movie("Captain America, Civil War", listOf(captainAmerica, ironMan, hawkeye, blackWidow)),
        Movie("Iron Man 1", listOf(ironMan)),
        Movie("Iron Man 2", listOf(ironMan, blackWidow)),
        Movie("Iron Man 3", listOf(ironMan, theHulk)),
        Movie("Thor", listOf(thor, hawkeye)),
        Movie("Thor, le monde des ténèbres", listOf(thor, captainAmerica)),
        Movie("Thor, Ragnarok", listOf(thor, theHulk))
)

val avengers = listOf(blackWidow, captainAmerica, hawkeye, theHulk, ironMan, thor)
val colors = listOf(0x301E1E, 0x083E77, 0x342350, 0x567235, 0x8B161C, 0xDF7C00).map { Color(it) }

const val chordWidth = 600.0
const val chordHeight = chordWidth
val outer = minOf(chordWidth, chordHeight) * 0.5 - 40.0
val inner = outer - 30

val chord = ChordLayout<Avenger>().apply {
    padAngle = .15
}

fun collaborations(avengers: List<Avenger>) = movies.filter { it.avengers.containsAll(avengers) }.size.toDouble()

val avengersChords: Chords = chord.chord(avengers) { a, b -> if (a == b) .0 else collaborations(listOf(a, b)) }

val avengersArcBuilder = arcBuilder<ChordGroup> {
    innerRadius = { inner + 3 }
    outerRadius = { outer }
    startAngle = { it.startAngle }
    endAngle = { it.endAngle }
}

val ribbon: (Chord, Path) -> Unit = io.data2viz.chord.ribbon(inner)

fun chordViz(): Viz = viz {

    width = chordWidth
    height = chordHeight

    group {
        //todo create a center function
        transform { translate(width / 2, height / 2) }

        //Drawing external groups representing avengers
        avengersChords.groups.forEachIndexed { index, it ->
            path {
                style.fill = io.data2viz.examples.chord.colors[index]
                avengersArcBuilder.buildArcForDatum(it, this)
                style.stroke = null
            }
        }


        //drawing ribbons
        avengersChords.chords.forEach { chord ->
            path {
                style.fill = chord.toGradient()
                style.stroke = null
                ribbon(chord, this)
            }
        }
    }
}


//Todo Move in API
fun Chord.toGradient() = LinearGradient().apply {
    x1 = inner * cos((source.endAngle - source.startAngle) / 2 + source.startAngle - PI / 2)
    y1 = inner * sin((source.endAngle - source.startAngle) / 2 + source.startAngle - PI / 2)
    x2 = inner * cos((target.endAngle - target.startAngle) / 2 + target.startAngle - PI / 2)
    y2 = inner * sin((target.endAngle - target.startAngle) / 2 + target.startAngle - PI / 2)

    //Set the starting color (at 0%)
    addColor(.0, io.data2viz.examples.chord.colors[source.index].withAlpha(.6f))
    addColor(1.0, io.data2viz.examples.chord.colors[target.index].withAlpha(.6f))
}