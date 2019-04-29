package io.data2viz.examples.chord


import io.data2viz.chord.Chord
import io.data2viz.chord.ChordGroup
import io.data2viz.chord.ChordLayout
import io.data2viz.chord.Chords
import io.data2viz.color.*
import io.data2viz.geom.Path
import io.data2viz.geom.Point
import io.data2viz.geom.Size
import io.data2viz.math.Percent
import io.data2viz.math.pct
import io.data2viz.shape.arcBuilder
import io.data2viz.viz.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


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
val colors = listOf(0x301E1E, 0x083E77, 0x342350, 0x567235, 0x8B161C, 0xDF7C00).map { Colors.rgb(it) }


val chordSize = Size(600.0, 600.0)
val outer = minOf(chordSize.width, chordSize.height) * 0.5 - 40.0
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

    size = chordSize

    group {
        //todo create a center function
        transform { translate(width / 2, height / 2) }

        //Drawing external groups representing avengers
        avengersChords.groups.forEachIndexed { index, it ->
            path {
                fill = io.data2viz.examples.chord.colors[index]
                avengersArcBuilder.buildArcForDatum(it, this)
                stroke = null
            }
        }


        //drawing ribbons
        avengersChords.chords.forEach { chord ->
            path {
                fill = chord.toGradient(60.pct)
                stroke = null
                ribbon(chord, this)
            }
        }
    }

}

/**
 * Listen to MouseMove event on chord.
 */
fun Viz.addEvents() {
    on(KPointerClick) { evt ->
        println("Pointer click:: ${evt.pos}")
    }
    on(KPointerDoubleClick) { evt ->
        println("Pointer double click:: ${evt.pos}")
    }

    on(KPointerMove) { evt ->
        println("Pointer move:: ${evt.pos}")
    }

    on(KPointerEnter) { evt ->
        println("Pointer enter:: ${evt.pos}")
    }

    on(KPointerLeave) { evt ->
        println("Pointer leave:: ${evt.pos}")
    }

    on(KPointerDown) { evt ->
        println("Pointer down:: ${evt.pos}")
    }

    on(KPointerUp) { evt ->
        println("Pointer up:: ${evt.pos}")
    }

    on(KPointerDrag) { evt ->
        println("Pointer drag(${evt.action}):: ${evt.pos}")
    }

    on(KPointerDoubleClick) { evt ->
        println("DISPOSED Pointer double click:: ${evt.pos}")
    }.dispose()

}


//Todo Move in API
fun Chord.toGradient(alpha:Percent) = Colors.Gradient.linear(
    Point(
        inner * cos((source.endAngle - source.startAngle) / 2 + source.startAngle - PI / 2),
        inner * sin((source.endAngle - source.startAngle) / 2 + source.startAngle - PI / 2)
    ),
    Point(
        inner * cos((target.endAngle - target.startAngle) / 2 + target.startAngle - PI / 2),
        inner * sin((target.endAngle - target.startAngle) / 2 + target.startAngle - PI / 2)
    )
).withColor(colors[source.index].withAlpha(alpha))
    .andColor(colors[target.index].withAlpha(alpha))