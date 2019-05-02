package io.data2viz.examples.chord


import io.data2viz.color.Colors
import io.data2viz.geom.Size
import io.data2viz.viz.*
import kotlin.math.roundToInt

val vizSize = Size(600.0, 600.0)
const val textFontSize = 14.0
var logMessageTexts = mutableListOf<TextNode>()

val maxRows = vizSize.height / textFontSize
val maxLogMessages = (maxRows-1).roundToInt()

var allEventsDisposable: CompositeDisposable? = null

val isEventsAdded get() = allEventsDisposable != null

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
    allEventsDisposable?.dispose()
    allEventsDisposable = null

}

fun Viz.addEvents() {

    val compositeDisposable = CompositeDisposable()
    compositeDisposable.add(on(KPointerClick) { evt ->
        addToLog(this, "Pointer click:: ${evt.pos}")
    })
    compositeDisposable.add(on(KPointerDoubleClick) { evt ->
        addToLog(this, "Pointer double click:: ${evt.pos}")
    })

    compositeDisposable.add(on(KPointerMove) { evt ->
        addToLog(this, "Pointer move:: ${evt.pos}")
    })

    compositeDisposable.add(on(KPointerEnter) { evt ->
        addToLog(this, "Pointer enter:: ${evt.pos}")
    })

    compositeDisposable.add(on(KPointerLeave) { evt ->
        addToLog(this, "Pointer leave:: ${evt.pos}")
    })

    compositeDisposable.add(on(KPointerDown) { evt ->
        addToLog(this, "Pointer down:: ${evt.pos}")
    })

    compositeDisposable.add(on(KPointerUp) { evt ->
        addToLog(this, "Pointer up:: ${evt.pos}")
    })

    compositeDisposable.add(on(KPointerDrag) { evt ->
        addToLog(this, "Pointer drag(${evt.action}):: ${evt.pos}")
    })

    compositeDisposable.add(on(KZoom) { evt ->
        addToLog(this, "Zoom :: ${evt.delta}")
    })

    allEventsDisposable = compositeDisposable

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
