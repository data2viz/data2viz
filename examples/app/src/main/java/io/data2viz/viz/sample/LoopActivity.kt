package io.data2viz.viz.sample

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button

class LoopActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("LoopActivity.create")
        setContentView(LoopView(this))
    }

    override fun onPause() {
        super.onPause()
        stopLoop()
    }

    override fun onResume() {
        super.onResume()
        startLoop()
    }

    fun startLoop(){
        println("LoopActivity.startLoop")
    }

    fun stopLoop(){
        println("LoopActivity.stopLoop")
    }

}

val paint = Paint().apply {
    isAntiAlias = true
    textSize = 60f
}

class LoopView(context: Context) : View(context) {

    var drawCount = -1
    var fps = 0L
    private var startTime = System.currentTimeMillis()

    override fun onDraw(canvas: Canvas) {
        canvas.drawText("${drawCount++}", 30f, 100f, paint)
        if (drawCount == 100){
            val delta = System.currentTimeMillis() - startTime
            fps = 100_000 / delta
            startTime = System.currentTimeMillis()
            drawCount = -1
        }
        canvas.drawText("${fps} FPS", 30f, 200f, paint)
        invalidate()

    }
}
