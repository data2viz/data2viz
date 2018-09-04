package io.data2viz.viz.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.content.Context
import android.graphics.Canvas
import android.view.View
import io.data2viz.examples.sankey.sankeyViz
import io.data2viz.viz.AndroidCanvasRenderer


class SankeyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(SankeyView(this))
    }
}

class SankeyView(context: Context) : View(context) {

    private val renderer = AndroidCanvasRenderer(context, Canvas()).apply {
        scale = 1F
    }

    override fun onDraw(canvas: Canvas) {
        renderer.canvas = canvas
        renderer.render(sankeyViz())
    }

}


