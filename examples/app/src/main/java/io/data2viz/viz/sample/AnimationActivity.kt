package io.data2viz.viz.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.animation.TimeAnimator
import android.content.Context
import android.graphics.Canvas
import android.view.View
import io.data2viz.viz.AndroidCanvasRenderer


class AnimationActivity : AppCompatActivity() {

    lateinit var view:AnimationView 
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = AnimationView(this)
        setContentView(view)
    }

    override fun onResume() {
        super.onResume()
        view.timeAnimator.resume()
    }

    override fun onPause() {
        super.onPause()
        view.timeAnimator.pause()
    }
}


class AnimationView(context: Context) : View(context) {

    val renderer = AndroidCanvasRenderer(context, Canvas())

    val timeAnimator = TimeAnimator().apply { 
        setTimeListener { animation, totalTime, deltaTime ->
//            loop(totalTime.toDouble())
            invalidate()
        }
        start()
    }

    override fun onDraw(canvas: Canvas) {
        renderer.canvas = canvas
//        renderer.render(circleViz)
    }

}


