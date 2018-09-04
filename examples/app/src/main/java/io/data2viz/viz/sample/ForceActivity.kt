package io.data2viz.viz.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.content.Context
import android.graphics.Canvas
import android.view.View
import forcesViz
import io.data2viz.timer.Timer
import io.data2viz.viz.AndroidCanvasRenderer


class ForceActivity : AppCompatActivity() {

    lateinit var view:ForceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ForceView(this)
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

class ForceView(context: Context) : View(context) {

    private var timer: Timer? = null

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
        renderer.canvas = canvas
        renderer.render(forcesViz)
    }

}


