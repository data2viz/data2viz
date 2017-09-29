package io.data2viz.experiments.perfs

import kotlin.js.Date


data class Foo(val value:Int)

fun jsPerfs() {
    val karr = Array<Foo>(10000000){ Foo(it) }
    var sum = 0
    var time = Date().getTime()

    sum = 0
    karr.forEach { foo ->
        sum += foo.value
    }

    sum = 0
    time = Date().getTime()
    karr.forEach { foo ->
        sum += foo.value
    }
    println("forEach over ${karr.size} elements in ${Date().getTime() - time} ms Result:: $sum")

    sum = 0
    for (foo in karr) {
        sum += foo.value
    }

    time = Date().getTime()
    sum = 0
    for (foo in karr) {
        sum += foo.value
    }
    println("Itar over ${karr.size} elements in ${Date().getTime() - time} ms. Result:: $sum")

    sum = 0
    time = Date().getTime()
    karr.forEach { foo ->
        sum += foo.value
    }
    println("forEach over ${karr.size} elements in ${Date().getTime() - time} ms Result:: $sum")


    karr.fold(0) { acc, foo -> acc + foo.value }

    time = Date().getTime()
    sum = karr.fold(0) { acc, foo -> acc + foo.value }
    println("Fold over ${karr.size} elements in ${Date().getTime() - time} ms Result:: $sum")
}
