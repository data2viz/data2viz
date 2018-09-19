package io.data2viz.viz.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.data2viz.examples.sankey.sankeyViz
import io.data2viz.viz.VizView
import io.data2viz.viz.toView


class SankeyActivity : AppCompatActivity() {

    lateinit var view: VizView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = sankeyViz().toView(this)
        setContentView(view)
    }
}


