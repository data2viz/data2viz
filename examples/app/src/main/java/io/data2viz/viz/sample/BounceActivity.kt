package io.data2viz.viz.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.data2viz.color.colors
import io.data2viz.viz.VizView
import io.data2viz.viz.toView
import io.data2viz.viz.viz


class BounceActivity : AppCompatActivity() {

    lateinit var view: VizView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = viz.toView(this)
        setContentView(view)
    }

    override fun onPause() {
        super.onPause()
    }
}


val viz = viz {

    width = 100.0
    height = 100.0

    val circle = circle {
        x = 50.0
        y = 50.0
        fill = colors.red
    }


    onFrame { time ->
        circle.x = circle.x + 1
    }
}