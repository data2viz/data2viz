package io.data2viz.viz.sample

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import io.data2viz.color.colors
import io.data2viz.viz.VizView
import io.data2viz.viz.toView
import io.data2viz.viz.viz


class BounceActivity : AppCompatActivity() {

    lateinit private var view: VizView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = viz.toView(this)
        setContentView(view)
        println("onCreate")
        view.startAnimations()
    }

    override fun onStop() {
        super.onStop()
        println("onStop")
        view.stopAnimations()
    }
}


val viz = viz {

    width = 100.0
    height = 100.0

    val circle = circle {
        x = 0.0
        y = 50.0
        fill = colors.red
    }

    onFrame { time ->
        if (circle.x > 100.0)
            circle.x = .0
        circle.x = circle.x + 1
    }
}