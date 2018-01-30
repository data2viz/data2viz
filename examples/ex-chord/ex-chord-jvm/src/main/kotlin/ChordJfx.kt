package io.data2viz.examples.chord


import io.data2viz.axis.*
import io.data2viz.chord.*
import io.data2viz.color.colors
import io.data2viz.core.*
import io.data2viz.core.*
import io.data2viz.path.SvgPath
//import io.data2viz.scale.*
import io.data2viz.shape.*
import io.data2viz.viz.*
import kotlin.math.ln
import kotlin.math.round
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.stage.Stage


//val margins = Margins(40.5, 30.5, 50.5, 50.5)

class ChordJfx : Application() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(ChordJfx::class.java)
        }
    }


    class CityBusses(val cityName: String) {
        private val toCity = mutableMapOf<CityBusses, Double>()
        fun trafficTo(otherCity: CityBusses) = toCity.getOrPut(otherCity) { 1000 * random() }
    }


    val busses = listOf(
        CityBusses("Paris"),
        CityBusses("Madrid"),
        CityBusses("Rome"),
        CityBusses("Bern"),
        CityBusses("Geneva"),
        CityBusses("Lyon"),
        CityBusses("London")
    )


    val width = 960.0
    val height = width
    val outer = minOf(width, height) * 0.5 - 40.0
    val inner = outer - 30

    val chord = ChordLayout<CityBusses>().apply {
        padAngle = .05
    }

    val chords = chord.chord(busses, { a, b -> a.trafficTo(b) })


    override fun start(stage: Stage?) {
        println("Building viz")
        val root = Group()
        

        val arc = arc<ChordGroup> {
            innerRadius = const(inner)
            outerRadius = const(outer)
            startAngle = {it.startAngle}
            endAngle = {it.endAngle}
        }
        
        val ribbon = ribbon(inner)
        
        root.viz {
            transform { translate(width/2, height/2) }
            
            chords.groups.forEach { 
                path {
                    fill = colors.steelblue
                    arc.arc(it, this)
                }
            }
            
            chords.chords.forEach { 
                path { 
                    fill = colors.red
                    ribbon(it, this)
                }
            }
        }


        stage?.let {
            it.scene = (Scene(root, width, height))
            it.show()
            stage.title = "JavaFx - data2viz - NaturalLogScale.kt"
        }
    }


}
