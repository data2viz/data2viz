package io.data2viz.viz.sample

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.data2viz.examples.chord.addEvents
import io.data2viz.examples.chord.chordViz
import io.data2viz.viz.*


class ChordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val chordViz = chordViz()
        val vizView = chordViz
            .toView(this)
        setContentView(vizView)
    }

}


