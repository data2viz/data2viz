package io.data2viz.viz.sample

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import io.data2viz.examples.chord.*
import io.data2viz.viz.*


class EventsActivity : AppCompatActivity() {

    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val chordViz = eventsViz()


        button = Button(this)
        toggleEventsState(chordViz, button)
        button.setOnClickListener() {
            toggleEventsState(chordViz, button)
        }


        val vizView = chordViz
            .toView(this)


        val linearLayout = LinearLayout(this)

        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.addView(button)
        linearLayout.addView(vizView)

        setContentView(linearLayout)
    }

    private fun toggleEventsState(
        viz: Viz,
        button: Button
    ) {
        if (isEventsAdded) {
            viz.removeEvents()
            button.text = addEventsText
        } else {
            viz.addEvents()
            button.text = removeEventsText
        }
    }
}


