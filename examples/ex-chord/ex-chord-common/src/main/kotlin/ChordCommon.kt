package io.data2viz.examples.chord

import io.data2viz.chord.ChordGroup
import io.data2viz.core.random
import io.data2viz.chord.ChordLayout
import io.data2viz.color.colors
import io.data2viz.shape.arc
import io.data2viz.shape.const
import io.data2viz.viz.VizContext


class CityBusses(val cityName: String) {
    private val toCity = mutableMapOf<CityBusses, Double>()
    fun trafficTo(otherCity: CityBusses) =  if (otherCity == this) .0 else toCity.getOrPut(otherCity) { 1000 * random() }
}


val busses = listOf(
    CityBusses("Paris"),
    CityBusses("Madrid"),
    CityBusses("Rome"),
    CityBusses("Bern"),
    CityBusses("Geneva")
)


val width = 600.0
val height = width
val outer = minOf(width, height) * 0.5 - 40.0
val inner = outer - 30

val chord = ChordLayout<CityBusses>().apply {
    padAngle = .05
}

val chords = chord.chord(busses, { a, b -> a.trafficTo(b) })

val arc = arc<ChordGroup> {
    innerRadius = const(inner)
    outerRadius = const(outer)
    startAngle = { it.startAngle }
    endAngle = { it.endAngle }
}

val ribbon = io.data2viz.chord.ribbon(inner)


fun VizContext.chordViz() {
    transform { translate(width / 2, height / 2) }

    chords.groups.forEach {
        path {
            fill = colors.steelblue
            arc.arc(it, this)
        }
    }

    chords.chords.forEach {
        path {
            fill = colors.red.apply { alpha = .6 }
            ribbon(it, this)
        }
    }
}
