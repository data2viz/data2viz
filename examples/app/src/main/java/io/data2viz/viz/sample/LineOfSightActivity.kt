package io.data2viz.viz.sample

import android.content.Context
import android.graphics.Canvas
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import io.data2viz.examples.lineOfSight.lineOfSightViz
import io.data2viz.timer.Timer
import io.data2viz.viz.AndroidCanvasRenderer
import io.data2viz.viz.Viz


class LineOfSightActivity : AppCompatActivity() {

    lateinit var view:LineOfSightView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = LineOfSightView(this)
        setContentView(view)
    }

    override fun onResume() {
        super.onResume()
        view.startRendering()
    }

    override fun onPause() {
        super.onPause()
        view.stopRendering()
    }

}

class LineOfSightView(context: Context) : View(context) {

    private var timer: Timer? = null
    private val viz: Viz = lineOfSightViz()


    private val renderer = AndroidCanvasRenderer(context, Canvas()).apply {
        scale = 1.35F
    }

    init {

        startRendering()
    }

    fun startRendering(){
        timer = io.data2viz.timer.timer {
            invalidate()
        }
    }

    fun stopRendering(){
        timer?.stop()
    }

    override fun onDraw(canvas: Canvas) {
        println("width::$width height::$height density::${canvas.density}")
        renderer.canvas = canvas
        renderer.render(viz)
    }

}


