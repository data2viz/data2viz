package io.data2viz.viz.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.data2viz.examples.lineOfSight.lineOfSightViz
import io.data2viz.viz.VizView
import io.data2viz.viz.toView


class LineOfSightActivity : AppCompatActivity() {

    lateinit var view:VizView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = lineOfSightViz().toView(this)
        setContentView(view)
    }

    override fun onResume() {
        super.onResume()
//        view.startRendering()
    }

    override fun onPause() {
        super.onPause()
//        view.stopAnimations()
    }

}


