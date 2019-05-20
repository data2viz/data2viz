package io.data2viz.viz.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import io.data2viz.timer.timer
import io.data2viz.viz.VizView

class FPSMeterView : TextView {


    var handlerStarted = false
    var vizView: VizView? = null


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)



    init {


        timer(
            delay = 1000.0,
            callback = { time ->
                text = "Native FPS: ${vizView?.fps}"
            }
        )
    }

}