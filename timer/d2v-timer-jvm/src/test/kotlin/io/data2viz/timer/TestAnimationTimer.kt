package io.data2viz.timer

fun main(args: Array<String>) {

    (1..100).forEach { 
        timer.callInNextFrame { println("Hello $it") }
    }
    
}

