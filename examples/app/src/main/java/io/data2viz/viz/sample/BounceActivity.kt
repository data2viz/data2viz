package io.data2viz.viz.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.data2viz.color.colors
import io.data2viz.timer.Timer
import io.data2viz.timer.timer
import io.data2viz.viz.Circle
import io.data2viz.viz.VizView
import io.data2viz.viz.toView
import io.data2viz.viz.viz


class BounceActivity : AppCompatActivity() {

    lateinit private var view: VizView
    lateinit private var timer: Timer
    lateinit var circle: Circle
    val viz = viz {

        width = 100.0
        height = 100.0


        circle = circle {
            x = 0.0
            y = 50.0
            fill = colors.red
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = viz.toView(this)
        setContentView(view)
        timer = timer {
            if (circle.x > 100.0)
                circle.x = .0
            circle.x = circle.x + 1
            view.invalidate()
        }
    }

    override fun onStop() {
        super.onStop()
        timer.stop()
    }
}
