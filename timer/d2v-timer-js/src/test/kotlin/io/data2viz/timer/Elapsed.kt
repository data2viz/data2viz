package io.data2viz.timer


@JsName("elapsed")
fun elapsed(){


    timer(callback = { time ->
        console.log(time)
        stop()
    })

}
