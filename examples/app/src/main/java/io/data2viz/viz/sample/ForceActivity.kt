package io.data2viz.viz.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import forcesViz
import io.data2viz.viz.VizView
import io.data2viz.viz.toView
import simulation


class ForceActivity : AppCompatActivity() {

    lateinit var view:VizView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = forcesViz.toView(this)
        setContentView(view)
        view.startAnimations()
        simulation.restart()
    }

    override fun onStop() {
        super.onStop()
        view.stopAnimations()
        simulation.stop()
    }

}

