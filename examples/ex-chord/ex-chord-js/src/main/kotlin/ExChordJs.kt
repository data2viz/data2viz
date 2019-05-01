package io.data2viz.examples.chord

import io.data2viz.viz.KPointerDoubleClick
import io.data2viz.viz.bindRendererOn

@Suppress("unused")
fun main(args: Array<String>) {
    val chordViz = chordViz()
    chordViz.bindRendererOn("chord")
    chordViz.on(KPointerDoubleClick) { evt ->
        println("AFTER INIT Pointer double click:: ${evt.pos}")
    }
}
