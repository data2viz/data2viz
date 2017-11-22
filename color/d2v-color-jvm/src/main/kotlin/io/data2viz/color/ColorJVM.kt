package io.data2viz.color

internal actual fun Int.toString(radix: Int): String = Integer.toString(this, radix)

val javafx.scene.paint.Color.d2vColor: Color
    get() = Color().apply { rgba(red, green, blue, opacity) }

val Color.jfxColor
    get() =  javafx.scene.paint.Color.rgb(r, g, b, alpha.toDouble())