package io.data2viz.examples.events


import io.data2viz.color.Colors
import io.data2viz.geom.Size
import io.data2viz.viz.*
import kotlin.math.roundToInt

val vizSize = Size(600.0, 600.0)
const val textFontSize = 14.0
var logMessageTexts = mutableListOf<TextNode>()

val maxRows = vizSize.height / textFontSize
val maxLogMessages = (maxRows - 1).roundToInt()

val allEvents = mutableListOf<Disposable>()

val isEventsAdded get() = allEvents.isNotEmpty()

val addEventsText = "Add events"
val removeEventsText = "Remove events"

fun eventsViz(): Viz = viz {

    size = vizSize
    group {

        rect {
            size = vizSize
            fill = Colors.Web.lightgrey

        }

        text {
            size = vizSize
            x = 300.0
            y = textFontSize
            fontSize = textFontSize
            hAlign = TextHAlign.MIDDLE
            vAlign = TextVAlign.MIDDLE
            textContent = "Events handling area"
        }


        for (i in 0 until maxLogMessages) {
            val logText = text {

                x = 0.0
                y = fontSize * (i + 2)
                fontSize = textFontSize
                hAlign = TextHAlign.LEFT
                vAlign = TextVAlign.HANGING
                textContent = "Log messages will be here"
            }

            logMessageTexts.add(logText)
        }

    }

}

fun Viz.removeEvents() {
	allEvents.forEach { it.dispose() }
    allEvents.clear()
}


@ExperimentalKEvent
fun Viz.addEvents() {

	allEvents += on(KPointerClick) { evt ->
        addToLog(this, "Pointer click:: ${evt.pos}")
    }

    allEvents += on(KPointerDoubleClick) { evt ->
        addToLog(this, "Pointer double click:: ${evt.pos}")
    }

	allEvents += on(KPointerMove) { evt ->
        addToLog(this, "Pointer move:: ${evt.pos}")
    }

	allEvents += on(KPointerEnter) { evt ->
        addToLog(this, "Pointer enter:: ${evt.pos}")
    }

	allEvents += on(KPointerLeave) { evt ->
        addToLog(this, "Pointer leave:: ${evt.pos}")
    }

	allEvents += on(KPointerDown) { evt ->
        addToLog(this, "Pointer down:: ${evt.pos}")
    }

	allEvents += on(KPointerUp) { evt ->
        addToLog(this, "Pointer up:: ${evt.pos}")
    }

	allEvents += on(KPointerDrag) { evt ->
        addToLog(this, "Pointer drag(${evt.action}):: ${evt.pos}")
    }

	allEvents += on(KZoom) { evt ->
        addToLog(this, "Zoom ${evt.startZoomPos} :: ${evt.delta}")
    }

}


private fun addToLog(viz: Viz, str: String) {
    println("addToLog: $str")

    for (currentIndex in 0 until logMessageTexts.size - 1) {
        val nextIndex = currentIndex + 1
        logMessageTexts[currentIndex].textContent = logMessageTexts[nextIndex].textContent
    }

    logMessageTexts.last().textContent = str
    viz.render()


}
