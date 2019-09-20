package io.data2viz.examples.chord

import io.data2viz.viz.KPointerDoubleClick
import io.data2viz.viz.bindRendererOnNewCanvas

fun main() {
    val chordViz = chordViz()
    chordViz.bindRendererOnNewCanvas()
    chordViz.on(KPointerDoubleClick) { evt ->
        println("AFTER INIT Pointer double click:: ${evt.pos}")
    }
}
